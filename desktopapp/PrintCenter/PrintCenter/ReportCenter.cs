using System;
using System.Collections.Generic;
using System.Text;
using PersistenceLayer;
using BusinessEntity;
using System.IO;
using YkkReports.entities;
using PrintCenterServer;
using System.Windows.Forms;

namespace PrintCenter
{
    class ReportCenter
    {
        PrintCenter pc;
        public ReportCenter(PrintCenter pc)
        {
            this.pc = pc;
        }

        public void CheckReportTask()
        {
            try
            {
                RetrieveCriteria rc = new RetrieveCriteria(typeof(FNPRINTHEADEntity));
                Condition c = rc.GetNewCondition();
                c.AddEqualTo(FNPRINTHEADEntity.__PROC_FLAG, "0");
                EntityContainer ec = rc.AsEntityContainer();
                for (int i = 0; i < ec.Count; i++)
                {
                    FNPRINTHEADEntity head = ec[i] as FNPRINTHEADEntity;
                    head.PROC_FLAG = "1";
                    head.Save();
                    Print(head);
                }
            }
            catch (Exception ex)
            {
                WriteLog(ex.Message);
            }
        }

        private List<FNPRINTBODYEntity> GetBodys(string listKey)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNPRINTBODYEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNPRINTBODYEntity.__LISTKEY, listKey);
            EntityContainer ec = rc.AsEntityContainer();
            List<FNPRINTBODYEntity> bodys = new List<FNPRINTBODYEntity>();
            for (int i = 0; i < ec.Count; i++)
            {
                FNPRINTBODYEntity body = ec[i] as FNPRINTBODYEntity;
                bodys.Add(body);
            }
            return bodys;
        }

        private void Print(FNPRINTHEADEntity head)
        {
            List<FNPRINTBODYEntity> bodys = GetBodys(head.LISTKEY);
            switch (head.LISTTYPE)
            {
                case ListType.ErrorMessage:
                    ErrorMessageHead emHead = new ErrorMessageHead();
                    List<ErrorMessageDetail> emDetails = new List<ErrorMessageDetail>();

                    emHead.MessageType = head.RANGE1;
                    emHead.TimeRange = head.RANGE2;

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        ErrorMessageDetail emDetail = new ErrorMessageDetail();

                        emDetail.Time = body.RANGE1;
                        emDetail.MessageType = body.RANGE2;
                        emDetail.ErroMessage = body.RANGE3;

                        emDetails.Add(emDetail);
                    }

                    pc.DoPrint(head.PRINTER_NAME, emHead, emDetails);
                    break;
                case ListType.InOutRelust:
                    InOutResultHead iorHead = new InOutResultHead();
                    List<InOutResultDetail> iorDetails = new List<InOutResultDetail>();

                    iorHead.WorkType = head.RANGE1;
                    iorHead.ItemRange = head.RANGE2;
                    iorHead.TimeRange = head.RANGE3;
                    iorHead.UserId = head.RANGE4;

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        InOutResultDetail iorDetail = new InOutResultDetail();

                        iorDetail.ResultDate = body.RANGE1;
                        iorDetail.WorkTypeStationNo = body.RANGE2.Replace("\\r\\n","\r\n");
                        iorDetail.ItemCode = body.RANGE3;
                        iorDetail.ItemName = body.RANGE4.Replace("\\r\\n", "\r\n");
                        iorDetail.ColorCode = body.RANGE5;
                        iorDetail.TicketNoRetrievalNo = body.RANGE6.Replace("\\r\\n", "\r\n");
                        iorDetail.LocationNoBucketNo = body.RANGE7.Replace("\\r\\n", "\r\n");
                        iorDetail.Qty = body.RANGE8;
                        iorDetail.UserIdUserName = body.RANGE9.Replace("\\r\\n", "\r\n");

                        iorDetails.Add(iorDetail);
                    }

                    pc.DoPrint(head.PRINTER_NAME, iorHead, iorDetails);
                    break;
                case ListType.LocationStorage:
                    LocationStorageHead lsHead = new LocationStorageHead();
                    List<LocationStorageDetail> lsDetails = new List<LocationStorageDetail>();

                    lsHead.Depo = head.RANGE1;
                    lsHead.LocationStatus = head.RANGE2;
                    lsHead.LocationNo = head.RANGE3;
                    lsHead.WeightReportFlag = head.RANGE4;

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        LocationStorageDetail lsDetail = new LocationStorageDetail();

                        lsDetail.LocationNo = body.RANGE1;
                        lsDetail.ItemCode = body.RANGE2;
                        lsDetail.ItemName = body.RANGE3.Replace("\\r\\n", "\r\n");
                        lsDetail.ColorCode = body.RANGE4;
                        lsDetail.TicketNo = body.RANGE5;
                        lsDetail.Bucket = body.RANGE6;
                        lsDetail.StorageCount = body.RANGE7;
                        lsDetail.StockinDate = body.RANGE8;
                        lsDetail.WeightReportFlag = body.RANGE9;

                        lsDetails.Add(lsDetail);
                    }

                    pc.DoPrint(head.PRINTER_NAME, lsHead, lsDetails);
                    break;
                case ListType.Message:
                    MessageHead mHead = new MessageHead();
                    List<MessageDetail> mDetails = new List<MessageDetail>();

                    mHead.MessageType = head.RANGE1;
                    mHead.TimeRange = head.RANGE2;

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        MessageDetail mDetail = new MessageDetail();

                        mDetail.Time = body.RANGE1;
                        mDetail.MessageType = body.RANGE2;
                        mDetail.Message = body.RANGE3;

                        mDetails.Add(mDetail);
                    }

                    pc.DoPrint(head.PRINTER_NAME, mHead, mDetails);
                    break;
                case ListType.OvertimeStorage:
                    OvertimeStorageHead osHead = new OvertimeStorageHead();
                    List<OvertimeStorageDetail> osDetails = new List<OvertimeStorageDetail>();

                    osHead.WarehouseType = head.RANGE1;
                    osHead.OvertimeDate = head.RANGE2;
                    osHead.OvertimeKey = head.RANGE3;
                    osHead.OrderKey = head.RANGE4;

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        OvertimeStorageDetail osDetail = new OvertimeStorageDetail();

                        osDetail.Date = body.RANGE1;
                        osDetail.ItemCode = body.RANGE2;
                        osDetail.ItemName = body.RANGE3.Replace("\\r\\n", "\r\n");
                        osDetail.ColorCode = body.RANGE4;
                        osDetail.TicketNo = body.RANGE5;
                        osDetail.Bucket = body.RANGE6;
                        osDetail.LocationNo = body.RANGE7;
                        osDetail.StorageCount = body.RANGE8;

                        osDetails.Add(osDetail);
                    }

                    pc.DoPrint(head.PRINTER_NAME, osHead, osDetails);
                    break;
                case ListType.StockoutCancel:
                    List<StockoutCancel> scs = new List<StockoutCancel>();

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        StockoutCancel sc = new StockoutCancel();

                        sc.RetrievalNo = body.RANGE1;
                        sc.ItemCode = body.RANGE2;
                        sc.RetrievalCount = body.RANGE3;
                        sc.CancelCount = body.RANGE4;
                        sc.StartDate = body.RANGE5;
                        sc.CompleteDate = body.RANGE6;
                        sc.CustomerCode = body.RANGE7;

                        scs.Add(sc);
                    }

                    pc.DoPrint(head.PRINTER_NAME, null, scs);
                    break;
                case ListType.Storage:
                    StorageHead sHead = new StorageHead();
                    List<StorageDetail> sDetails = new List<StorageDetail>();

                    sHead.ItemCode = head.RANGE1;
                    sHead.ColorCode = head.RANGE2;

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        StorageDetail sDetail = new StorageDetail();

                        sDetail.ItemCode = body.RANGE1;
                        sDetail.ItemName = body.RANGE2.Replace("\\r\\n", "\r\n");
                        sDetail.ColorCode = body.RANGE3;
                        sDetail.AutoStorageCount = body.RANGE4;
                        sDetail.FlatStorageCount = body.RANGE5;
                        sDetail.TotalStorageCount = body.RANGE6;
                        sDetail.NotStockinStorageCount = body.RANGE7;

                        sDetails.Add(sDetail);
                    }

                    pc.DoPrint(head.PRINTER_NAME, sHead, sDetails);
                    break;
                case ListType.TicketNoAndStorage:
                    TicketNoAndStorageHead tsHead = new TicketNoAndStorageHead();
                    List<TicketNoAndStorageDetail> tsDetails = new List<TicketNoAndStorageDetail>();

                    tsHead.Depo = head.RANGE1;
                    tsHead.SearchClass = head.RANGE2;
                    tsHead.SearchRange = head.RANGE3;
                    tsHead.ItemCodeColorCode = head.RANGE4;
                    tsHead.BucketNo = head.RANGE5;

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        TicketNoAndStorageDetail tsDetail = new TicketNoAndStorageDetail();

                        tsDetail.TicketNo = body.RANGE1;
                        tsDetail.ItemCode = body.RANGE2;
                        tsDetail.ItemName = body.RANGE3.Replace("\\r\\n", "\r\n");
                        tsDetail.ColorCode = body.RANGE4;
                        tsDetail.LocationNo = body.RANGE5;
                        tsDetail.Bucket = body.RANGE6;
                        tsDetail.StorageCount = body.RANGE7;
                        tsDetail.ReceiveMessageDate = body.RANGE8;

                        tsDetails.Add(tsDetail);
                    }

                    pc.DoPrint(head.PRINTER_NAME, tsHead, tsDetails);
                    break;
                case ListType.WorkView:
                    WorkViewHead wvHead = new WorkViewHead();
                    List<WorkViewDetail> wvDetails = new List<WorkViewDetail>();

                    wvHead.WorkType = head.RANGE1;
                    wvHead.StationNo = head.RANGE2;
                    wvHead.StationType = head.RANGE3;

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        WorkViewDetail wvDetail = new WorkViewDetail();

                        wvDetail.WorkType = body.RANGE1;
                        wvDetail.WorkStatus = body.RANGE2;
                        wvDetail.McKey = body.RANGE3;
                        wvDetail.StationNo = body.RANGE4;
                        wvDetail.Path = body.RANGE5;
                        wvDetail.LocationNo = body.RANGE6;
                        wvDetail.Bucket = body.RANGE7;
                        wvDetail.ItemCode = body.RANGE8;
                        wvDetail.ItemName = body.RANGE9.Replace("\\r\\n", "\r\n");
                        wvDetail.ColorCode = body.RANGE10;
                        wvDetail.Qty = body.RANGE11;

                        wvDetails.Add(wvDetail);
                    }

                    pc.DoPrint(head.PRINTER_NAME, wvHead, wvDetails);
                    break;
                case ListType.RetrievalOrder:
                    RetrievalOrderHead roHead = new RetrievalOrderHead();
                    List<RetrievalOrderDetail> roDetails = new List<RetrievalOrderDetail>();

                    roHead.BucketNo = head.RANGE1;
                    roHead.UserId = head.RANGE2;

                    foreach (FNPRINTBODYEntity body in bodys)
                    {
                        RetrievalOrderDetail roDetail = new RetrievalOrderDetail();

                        roDetail.SectionAndLine = body.RANGE1;
                        roDetail.StartDate = body.RANGE2;
                        roDetail.ItemNo = body.RANGE3;
                        roDetail.ItemName = body.RANGE4;
                        roDetail.ColorCode = body.RANGE5;
                        roDetail.QtyPcs = body.RANGE6;
                        roDetail.QtyKg = body.RANGE7;
                        roDetail.BucketNo = body.RANGE8;
                        roDetail.RetrievalNo = body.RANGE9;
                        roDetail.RetrievalTime = body.RANGE10;

                        roDetails.Add(roDetail);
                    }

                    pc.DoPrint(head.PRINTER_NAME, roHead, roDetails);
                    break;
                default:
                    break;

            }
        }

        private void WriteLog(string log)
        {
            try
            {
                string logPath = Application.StartupPath + @"\logs\";
                if(!Directory.Exists(logPath))
                {
                    Directory.CreateDirectory(logPath);
                }
                StreamWriter stream = new StreamWriter(logPath + string.Format("LabelPrintErrorLog{0}.txt", DateTime.Now.ToString("yyyyMMdd")), true, System.Text.Encoding.Default);
                stream.WriteLine(DateTime.Now.ToString() + "  " + log);
                stream.Flush();
                stream.Close();
            }
            catch
            {
            }
        }
    }

    class ListType
    {
        public const string ErrorMessage = "6";
        public const string InOutRelust = "5";
        public const string Message = "7";
        public const string OvertimeStorage = "2";
        public const string StockoutCancel = "9";
        public const string Storage = "1";
        public const string TicketNoAndStorage = "4";
        public const string WorkView = "8";
        public const string LocationStorage = "3";
        public const string RetrievalOrder = "0";
    }
}
