using System;
using System.Collections.Generic;
using System.Text;
using BusinessEntity;
using PersistenceLayer;
using System.Data;
using YkkReports.entities;
using System.IO;
using System.Windows.Forms;

namespace PrintCenter
{
    class LabelCenter
    {
        PrintCenter pc;
        public LabelCenter(PrintCenter pc)
        {
            this.pc = pc;
        }
        private List<FNLABELEntity> GetNextLabel()
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNLABELEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNLABELEntity.__PRINTING_FLAG, "1");
            OrGroup og = c.GetNewOrGroup();
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "11");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "12");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "13");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "14");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "15");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "16");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "17");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "18");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "19");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "20");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "21");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "22");
            og.AddEqualTo(FNLABELEntity.__PRINTER_NO, "31");
            rc.OrderBy(FNLABELEntity.__BUCKET_NO);
            rc.OrderBy(FNLABELEntity.__PRINTER_NO);
            rc.OrderBy(FNLABELEntity.__RETRIEVAL_STATION);
            rc.OrderBy(FNLABELEntity.__LINE);
            EntityContainer ec = rc.AsEntityContainer();
            if (ec.Count > 0)
            {
                List<FNLABELEntity> labels = new List<FNLABELEntity>();
                for (int i = 0; i < ec.Count; i++)
                {
                    FNLABELEntity label = ec[i] as FNLABELEntity;
                    if (i > 0)
                    {
                        if (label.BUCKET_NO != labels[i - 1].BUCKET_NO || label.PRINTER_NO != labels[i - 1].PRINTER_NO)
                        {
                            break;  //不同的箱号或打印机
                        }
                    }
                    labels.Add(label);
                }
                return labels;
            }
            else
            {
                return null;
            }
        }

        private FNRETRIEVAL_STEntity GetRetrievalStation(string retrieval_station)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNRETRIEVAL_STEntity));
            rc.GetNewCondition().AddEqualTo(FNRETRIEVAL_STEntity.__RETRIEVAL_STATION, retrieval_station);
            return (FNRETRIEVAL_STEntity)rc.AsEntity();
        }

        public void CheckLabelTask()
        {
            try
            {
                do
                {
                    List<FNLABELEntity> labels = GetNextLabel();
                    if (labels == null)
                    {
                        break;
                    }
                    else
                    {
                        DoLabels(labels);
                    }
                } while (true);
            }
            catch (Exception ex)
            {
                WriteLog(ex.Message);
            }
        }

        private void DoLabels(List<FNLABELEntity> labels)    //一个作业，可能有多张条码
        {
            if (labels != null && labels.Count > 0)
            {
                string label_type = labels[0].LABEL_TYPE;
                try
                {
                    switch (label_type)
                    {
                        case LabelType.Normal:
                            PrintNormal(labels);
                            break;
                        case LabelType.FuYong:
                            PrintFuYong(labels);
                            break;
                        case LabelType.External:
                            PrintExternal(labels);
                            break;
                        case LabelType.Customer:
                            PrintCustomer(labels);
                            break;
                        case LabelType.Subdivided:
                            this.PrintSubdivided(labels);
                            break;
                        default:
                            throw new Exception(string.Format("Unknown Label Type: {0}", label_type));
                    }
                }
                catch (Exception ex)
                {
                    WriteLog(ex.Message);
                }


                try
                {
                    foreach (FNLABELEntity label in labels)
                    {
                        label.PRINTING_FLAG = "3";
                        label.Save();
                    }
                }
                catch (Exception ex)
                {
                    WriteLog(ex.Message);
                }
            }
        }

        private void PrintNormal(List<FNLABELEntity> labels)
        {
            List<LineEntity> lines = new List<LineEntity>();
            LineEntity line = new LineEntity();
            for (int i = 0; i < labels.Count; i++)
            {
                if (i == 0)
                {
                    line.Line = labels[i].LINE;
                    line.Count = labels[i].RETRIEVAL_QTY;
                    line.Weight = labels[i].RETRIEVAL_WEIGHT;
                    line.RetrievalNo = labels[i].RETRIEVAL_NO.Trim();
                    line.SerialNo = labels[i].SERIAL_NO;
                    line.NecessaryQty = labels[i].NECESSARY_QTY;
                    lines.Add(line);
                }
                else
                {
                    if (line.Line == labels[i].LINE)
                    {
                        line.Count += labels[i].RETRIEVAL_QTY;
                        line.Weight += labels[i].RETRIEVAL_WEIGHT;
                    }
                    else
                    {
                        line = new LineEntity();
                        line.Line = labels[i].LINE;
                        line.Count = labels[i].RETRIEVAL_QTY;
                        line.Weight = labels[i].RETRIEVAL_WEIGHT;
                        line.RetrievalNo = labels[i].RETRIEVAL_NO.Trim();
                        line.SerialNo = labels[i].SERIAL_NO;
                        line.NecessaryQty = labels[i].NECESSARY_QTY;
                        lines.Add(line);
                    }
                }

                string date = (labels[i].START_DATE.Length == 8 ? labels[i].START_DATE.Insert(4, "/").Insert(7, "/") : labels[i].START_DATE) + (labels[i].START_TIMING_FLAG == "1" ? "AM" : labels[i].START_TIMING_FLAG == "2" ? "PM" : "");
                line.LineDetail.Add(new LineDetailEntity(date, labels[i].RETRIEVAL_QTY, labels[i].RETRIEVAL_WEIGHT, labels[i].RETRIEVAL_NO.Trim(), labels[i].SERIAL_NO, labels[i].NECESSARY_QTY));
            }


            List<LabelNormal> lns = new List<LabelNormal>();
            List<LabelDetail> lds = new List<LabelDetail>();
            foreach (LineEntity l in lines)
            {
                decimal count = 0, weight = 0;
                for (int i = 0; i < l.LineDetail.Count; i++)
                {
                    count += l.LineDetail[i].Count;
                    weight += l.LineDetail[i].Weight;
                }

                while (l.LineDetail.Count > 0)
                {
                    LabelDetail ld = GetDetail(labels[0], l);
                    ld.Weight = weight.ToString("F4");
                    ld.Count = count.ToString("F0");
                    lds.Add(ld);
                }
            }
            while (lines.Count > 0)
            {
                LabelNormal ln = GetNormal(labels, lines);
                lns.Add(ln);
            }

            if (GetRetrievalStation(labels[0].RETRIEVAL_STATION).UNIFY_TICKET_PRINTFLG == "1")
            {
                foreach (LabelNormal ln in lns)
                {
                    pc.DoPrint(GetPrintName(labels[0].PRINTER_NO), ln);
                }
            }

            if (GetRetrievalStation(labels[0].RETRIEVAL_STATION).TICKET_PRINTFLG == "1")
            {
                foreach (LabelDetail ld in lds)
                {
                    pc.DoPrint(GetPrintName(labels[0].PRINTER_NO), ld);
                }
            }
        }

        private LabelNormal GetNormal(List<FNLABELEntity> labels, List<LineEntity> lines)
        {
            LabelNormal ln = new LabelNormal();
            ln.TicketNo = labels[0].TICKET_NO;
            ln.BucketNo = labels[0].BUCKET_NO;
            ln.ItemCode = labels[0].ZAIKEY;
            ln.ItemName = GetItemName(ln.ItemCode);
            ln.ColorCode = labels[0].COLOR_CODE;
            ln.Unit = (labels[0].MASTER_UNIT_WEIGHT * 1000).ToString("F4");
            ln.Section = labels[0].SECTION;
            ln.UserName = labels[0].USERNAME;
            if (labels[0].OPTION_FLAG.Equals("1"))
            {
                ln.OptionFlag = "(直行)";
            }

            decimal count = 0, weight = 0;
            foreach (FNLABELEntity label in labels)
            {
                count += label.RETRIEVAL_QTY;
                weight += label.RETRIEVAL_WEIGHT;
            }
            ln.Count = count.ToString("F0");
            ln.Weight = weight.ToString();

            for (int i = 0; lines.Count > 0 && i < 14; i++)
            {
                ln.AddDetails(lines[0].Line, lines[0].Count.ToString("F0"), lines[0].Weight.ToString());
                lines.RemoveAt(0);
            }
            return ln;
        }

        private LabelDetail GetDetail(FNLABELEntity label, LineEntity line)
        {
            LabelDetail ld = new LabelDetail();
            ld.TicketNo = label.TICKET_NO;
            ld.BucketNo = label.BUCKET_NO;
            ld.ItemCode = label.ZAIKEY;
            string itemCode = GetItemName(ld.ItemCode);
            ld.ItemName = itemCode;
            ld.ColorCode = label.COLOR_CODE;
            ld.Unit = (label.MASTER_UNIT_WEIGHT * 1000).ToString("F4");
            ld.Section = label.SECTION;
            ld.Line = line.Line;
            ld.UserName = label.USERNAME;
            if (label.OPTION_FLAG.Equals("1"))
            {
                ld.OptionFlag = "(直行)";
            }
            for (int j = 0; line.LineDetail.Count > 0 && j < 7; j++)
            {
                ld.AddDetails(line.LineDetail[0].Date, line.LineDetail[0].Count.ToString("F0"), line.LineDetail[0].Weight.ToString(), line.LineDetail[0].RetrievalNo, line.LineDetail[0].SerialNo.ToString("F0"), line.LineDetail[0].NecessaryQty.ToString("F0"));
                line.LineDetail.RemoveAt(0);
            }
            return ld;
        }

        private void PrintFuYong(List<FNLABELEntity> labels)
        {
            LabelFuYong lf = new LabelFuYong();
            lf.TicketNo = labels[0].TICKET_NO;
            lf.BucketNo = labels[0].BUCKET_NO;
            lf.ItemCode = labels[0].ZAIKEY;
            string itemName = GetItemName(lf.ItemCode);
            lf.ItemName = itemName;
            lf.ColorCode = labels[0].COLOR_CODE;
            lf.Unit = (labels[0].MASTER_UNIT_WEIGHT * 1000).ToString("F4");
            lf.RetrievalNo = labels[0].RETRIEVAL_NO.Trim();
            lf.NecessaryQty = labels[0].NECESSARY_QTY.ToString("F0");
            lf.UserName = labels[0].USERNAME;
            decimal count = 0, weight = 0;
            foreach (FNLABELEntity label in labels)
            {
                count += label.RETRIEVAL_QTY;
                weight += label.RETRIEVAL_WEIGHT;
            }
            lf.Count = count.ToString("F0");
            lf.Weight = weight.ToString();
            pc.DoPrint(GetPrintName(labels[0].PRINTER_NO), lf);
        }

        private void PrintExternal(List<FNLABELEntity> labels)
        {
            List<LineEntity> lines = new List<LineEntity>();
            LineEntity line = new LineEntity();
            for (int i = 0; i < labels.Count; i++)
            {
                if (i == 0)
                {
                    line.Line = labels[i].LINE;
                    line.Count = labels[i].RETRIEVAL_QTY;
                    line.Weight = labels[i].RETRIEVAL_WEIGHT;
                    line.RetrievalNo = labels[i].RETRIEVAL_NO.Trim();
                    line.SerialNo = labels[i].SERIAL_NO;
                    line.NecessaryQty = labels[i].NECESSARY_QTY;
                    lines.Add(line);
                }
                else
                {
                    if (line.Line == labels[i].LINE)
                    {
                        line.Count += labels[i].RETRIEVAL_QTY;
                        line.Weight += labels[i].RETRIEVAL_WEIGHT;
                    }
                    else
                    {
                        line = new LineEntity();
                        line.Line = labels[i].LINE;
                        line.Count = labels[i].RETRIEVAL_QTY;
                        line.Weight = labels[i].RETRIEVAL_WEIGHT;
                        line.RetrievalNo = labels[i].RETRIEVAL_NO.Trim();
                        line.SerialNo = labels[i].SERIAL_NO;
                        line.NecessaryQty = labels[i].NECESSARY_QTY;
                        lines.Add(line);
                    }
                }
                string date = (labels[i].START_DATE.Length == 8 ? labels[i].START_DATE.Insert(4, "/").Insert(7, "/") : labels[i].START_DATE) + (labels[i].START_TIMING_FLAG == "1" ? "AM" : labels[i].START_TIMING_FLAG == "2" ? "PM" : "");
                line.LineDetail.Add(new LineDetailEntity(date, labels[i].RETRIEVAL_QTY, labels[i].RETRIEVAL_WEIGHT, labels[i].RETRIEVAL_NO.Trim(), labels[i].SERIAL_NO, labels[i].NECESSARY_QTY));
            }

            List<LabelExternal> les = new List<LabelExternal>();
            while (lines.Count > 0)
            {
                LabelExternal le = GetExternal(labels, lines);
                les.Add(le);
            }

            foreach (LabelExternal le in les)
            {
                pc.DoPrint(GetPrintName(labels[0].PRINTER_NO), le);
            }
        }

        private LabelExternal GetExternal(List<FNLABELEntity> labels, List<LineEntity> lines)
        {
            LabelExternal le = new LabelExternal();
            le.TicketNo = labels[0].TICKET_NO;
            le.BucketNo = labels[0].BUCKET_NO;
            le.ItemCode = labels[0].ZAIKEY;
            string itemCode = GetItemName(le.ItemCode);
            le.ItemName = itemCode;
            le.ColorCode = labels[0].COLOR_CODE;
            le.Unit = (labels[0].MASTER_UNIT_WEIGHT * 1000).ToString("F4");
            le.CustomerCode = labels[0].CUSTOMER_CODE;
            le.CustomerName = labels[0].CUSTOMER_NAME1;
            le.UserName = labels[0].USERNAME;

            decimal count = 0, weight = 0;
            foreach (FNLABELEntity label in labels)
            {
                count += label.RETRIEVAL_QTY;
                weight += label.RETRIEVAL_WEIGHT;
            }
            le.Count = count.ToString("F0");
            le.Weight = weight.ToString();

            for (int i = 0; lines.Count > 0 && i < 7; i++)
            {
                le.AddDetails(lines[0].Line, lines[0].Count.ToString("F0"), lines[0].Weight.ToString(), lines[0].RetrievalNo, lines[0].SerialNo.ToString("F0"), lines[0].NecessaryQty.ToString("F0"));
                lines.RemoveAt(0);
            }
            return le;
        }

        private void PrintCustomer(List<FNLABELEntity> labels)
        {
            LabelCustomer lc = new LabelCustomer();
            lc.TicketNo = labels[0].TICKET_NO;
            lc.BucketNo = labels[0].BUCKET_NO;
            lc.ItemCode = labels[0].ZAIKEY;
            string itemName = GetItemName(lc.ItemCode);
            lc.ItemName = itemName;
            lc.ColorCode = labels[0].COLOR_CODE;
            lc.Unit = (labels[0].MASTER_UNIT_WEIGHT * 1000).ToString("F4");
            lc.UserName = labels[0].USERNAME;

            //lc.CustomerCode = labels[0].CUSTOMER_CODE;
            lc.CustomerCode = labels[0].ORDER_NO;
            lc.CustomerName = labels[0].CUSTOMER_NAME1;

            decimal count = 0, weight = 0;
            foreach (FNLABELEntity label in labels)
            {
                count += label.RETRIEVAL_QTY;
                weight += label.RETRIEVAL_WEIGHT;
            }
            lc.Count = count.ToString("F0");
            lc.Weight = weight.ToString();
            pc.DoPrint(GetPrintName(labels[0].PRINTER_NO), lc);
        }

        private void PrintSubdivided(List<FNLABELEntity> labels)
        {
            LabelSubdivided ls = new LabelSubdivided();
            ls.TicketNo = labels[0].TICKET_NO;
            ls.BucketNo = labels[0].BUCKET_NO;
            ls.Section = labels[0].SECTION;
            ls.Line = labels[0].LINE;
            ls.PrNo = labels[0].PR_NO;
            ls.ItemCode = labels[0].ZAIKEY;
            ls.ItemName = GetItemName(ls.ItemCode);
            ls.ColorCode = labels[0].COLOR_CODE;
            decimal count = 0, weight = 0;
            foreach (FNLABELEntity label in labels)
            {
                count += label.RETRIEVAL_QTY;
                weight += label.RETRIEVAL_WEIGHT;
            }
            ls.Count = count.ToString("F0");
            ls.Weight = weight.ToString();
            ls.Unit = (labels[0].MASTER_UNIT_WEIGHT * 1000).ToString("F4");
            ls.RetrievalNo = labels[0].RETRIEVAL_NO.Trim();
            ls.NecessaryQty = labels[0].NECESSARY_QTY.ToString("F0");
            ls.UserName = labels[0].USERNAME;
            if (labels[0].OPTION_FLAG.Equals("1"))
            {
                ls.OptionFlag = "(直行)";
            }

            ls.StartDate = (labels[0].START_DATE.Length == 8 ? labels[0].START_DATE.Insert(4, "/").Insert(7, "/") : labels[0].START_DATE) + (labels[0].START_TIMING_FLAG == "1" ? "AM" : labels[0].START_TIMING_FLAG == "2" ? "PM" : "");

            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNLABELEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNLABELEntity.__LABEL_TYPE, '5');
            c.AddEqualTo(FNLABELEntity.__RETRIEVAL_NO, labels[0].RETRIEVAL_NO);
            c.AddEqualTo(FNLABELEntity.__SERIAL_NO, labels[0].SERIAL_NO);
            c.AddEqualTo(FNLABELEntity.__PRINTING_FLAG, "3");
            int currentPage = rc.AsEntityContainer().Count + 1;

            rc = new RetrieveCriteria(typeof(FNLABELEntity));
            c = rc.GetNewCondition();
            c.AddEqualTo(FNLABELEntity.__LABEL_TYPE, '5');
            c.AddEqualTo(FNLABELEntity.__RETRIEVAL_NO, labels[0].RETRIEVAL_NO);
            c.AddEqualTo(FNLABELEntity.__SERIAL_NO, labels[0].SERIAL_NO);
            int totalPage = rc.AsEntityContainer().Count;

            ls.Page = string.Format("{0}/{1}", currentPage, totalPage);

            pc.DoPrint(GetPrintName(labels[0].PRINTER_NO), ls);
        }

        private string GetPrintName(decimal printNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNPRINTEREntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNPRINTEREntity.__PRINTER_NO, printNo);
            FNPRINTEREntity printer = rc.AsEntity() as FNPRINTEREntity;
            if (printer != null)
            {
                return printer.PRINTER_NAME.Trim();
            }
            else
            {
                return null;
            }
        }

        private void WriteLog(string log)
        {
            try
            {
                string logPath = Application.StartupPath + @"\logs\";
                if (!Directory.Exists(logPath))
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

        private string GetItemName(string itemcode)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FMZKEYEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FMZKEYEntity.__ZAIKEY, itemcode);
            FMZKEYEntity zkey = rc.AsEntity() as FMZKEYEntity;
            if (zkey != null)
            {
                return zkey.ZKNAME1;
            }
            else
            {
                return string.Empty;
            }
        }
    }

    public class LabelType
    {
        public const string Normal = "1";
        public const string FuYong = "2";
        public const string External = "3";
        public const string Customer = "4";
        public const string Subdivided = "5";
    }

    public class LineEntity
    {
        public string Line;
        public decimal Count;
        public decimal Weight;
        public string RetrievalNo;
        public decimal SerialNo;
        public decimal NecessaryQty;
        public List<LineDetailEntity> LineDetail = new List<LineDetailEntity>();
    }

    public class LineDetailEntity
    {
        public LineDetailEntity(string data, decimal count, decimal weight, string retrievalNo, decimal serialNo, decimal necessaryQty)
        {
            this.Date = data;
            this.Count = count;
            this.Weight = weight;
            this.RetrievalNo = retrievalNo;
            this.SerialNo = serialNo;
            this.NecessaryQty = necessaryQty;
        }
        public string Date;
        public decimal Count;
        public decimal Weight;
        public string RetrievalNo;
        public decimal SerialNo;
        public decimal NecessaryQty;
    }



    //1:通常出庫ラベル　２：福永出庫ラベル
    //３：外部出庫ラベル　４：お客さん出庫ラベル
}
