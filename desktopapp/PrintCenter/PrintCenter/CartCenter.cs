using System;
using System.Collections.Generic;
using System.Text;
using BusinessEntity;
using System.Data;
using PersistenceLayer;
using YkkReports.entities;
using System.IO;
using System.Windows.Forms;

namespace PrintCenter
{

    class CartCenter
    {
        PrintCenter pc;
        public CartCenter(PrintCenter pc)
        {
            this.pc = pc;
        }

        public void CheckCartTask()
        {
            try
            {
                Do();
            }
            catch (Exception ex)
            {
                WriteLog(ex.Message);
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
        
        private void Do()
        {
            Query q1 = new Query(typeof(FNCART_CTLEntity));
            q1.AddAttribute(FNCART_CTLEntity.__STNO);
            q1.AddAttribute(FNCART_CTLEntity.__SECTION);
            q1.AddAttribute(FNCART_CTLEntity.__GROUPNO);
            Condition c1 = q1.GetQueryCondition();
            c1.AddEqualTo(FNCART_CTLEntity.__REGISTER_FLG, "1");
            q1.GroupBy(FNCART_CTLEntity.__STNO);
            q1.GroupBy(FNCART_CTLEntity.__GROUPNO);
            q1.GroupBy(FNCART_CTLEntity.__SECTION);

            Query q2 = new Query(typeof(FNRETRIEVAL_STEntity));
            q2.AddAttribute(FNRETRIEVAL_STEntity.__RETRIEVAL_STATION);
            q2.AddAttribute(FNRETRIEVAL_STEntity.__CART_TICKET_PRINTFLG);
            q2.AddAttribute(FNRETRIEVAL_STEntity.__PRINTER_NO_CART);
            q2.GroupBy(FNRETRIEVAL_STEntity.__RETRIEVAL_STATION);
            q2.GroupBy(FNRETRIEVAL_STEntity.__PRINTER_NO_CART);
            q2.GroupBy(FNRETRIEVAL_STEntity.__CART_TICKET_PRINTFLG);


            q1.AddJoinQuery(FNCART_CTLEntity.__STNO, q2, FNRETRIEVAL_STEntity.__UNIT_STNO);

            q1.OrderBy(FNCART_CTLEntity.__STNO);
            q1.OrderBy(FNCART_CTLEntity.__GROUPNO);

            DataTable dt = q1.Execute();       //sql1

            if (dt != null && dt.Rows.Count > 0)
            {
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    DataRow dr = dt.Rows[i];
                    string printflag = dr[FNRETRIEVAL_STEntity.__CART_TICKET_PRINTFLG].ToString();
                    decimal printNo = Convert.ToDecimal(dr[FNRETRIEVAL_STEntity.__PRINTER_NO_CART]);
                    string printName = GetPrintName(printNo);
                    string retrievalStation = dr[FNRETRIEVAL_STEntity.__RETRIEVAL_STATION].ToString();
                    string groupNo = dr[FNCART_CTLEntity.__GROUPNO].ToString();
                    string section = dr[FNCART_CTLEntity.__SECTION].ToString();

                    try
                    {
                        if (printflag == "1")   //要打印
                        {
                            DataTable dt1 = GetData(section, groupNo);
                            if (dt1 != null && dt1.Rows.Count > 0)
                            {
                                if (retrievalStation == "24")    //组立
                                {
                                    AssemblyHead head = new AssemblyHead();
                                    head.Section = section;
                                    List<AssemblyDetail> details = new List<AssemblyDetail>();
                                    for (int j = 0; j < dt1.Rows.Count; j++)
                                    {
                                        DataRow dr1 = dt1.Rows[j];
                                        AssemblyDetail detail = new AssemblyDetail();
                                        detail.BucketNo = dr1[FNCART_CTLEntity.__BUCKET_NO].ToString();
                                        detail.ColorCode = dr1[FNCART_CTLEntity.__COLOR_CODE].ToString();
                                        detail.ItemCode = dr1[FNCART_CTLEntity.__ITEM_CODE].ToString();
                                        detail.ItemName = dr1[FMZKEYEntity.__ZKNAME1].ToString();
                                        detail.Qty = dr1[FNCART_CTLEntity.__QTY].ToString();
                                        detail.Weight = dr1[FNCART_CTLEntity.__WEIGHT].ToString();
                                        details.Add(detail);
                                    }
                                    pc.DoPrint(printName, head, details);
                                }
                                else if (retrievalStation == "11" || retrievalStation == "21")   //SI
                                {
                                    SIHead head = new SIHead();
                                    head.Section = section;
                                    List<SIDetail> details = new List<SIDetail>();
                                    for (int j = 0; j < dt1.Rows.Count; j++)
                                    {
                                        DataRow dr1 = dt1.Rows[j];
                                        SIDetail detail = new SIDetail();
                                        detail.BucketNo = dr1[FNCART_CTLEntity.__BUCKET_NO].ToString();
                                        detail.Line = dr1[FNCART_CTLEntity.__LINE].ToString();
                                        detail.ItemCode = dr1[FNCART_CTLEntity.__ITEM_CODE].ToString();
                                        detail.ItemName = dr1[FMZKEYEntity.__ZKNAME1].ToString();
                                        detail.Qty = dr1[FNCART_CTLEntity.__QTY].ToString();
                                        detail.Weight = dr1[FNCART_CTLEntity.__WEIGHT].ToString();
                                        details.Add(detail);
                                    }
                                    pc.DoPrint(printName, head, details);
                                }
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        WriteLog(ex.Message);
                    }
                    //update
                    UpdateCriteria uc = new UpdateCriteria(typeof(FNCART_CTLEntity));
                    Condition c = uc.GetNewCondition();
                    c.AddEqualTo(FNCART_CTLEntity.__SECTION, section);
                    c.AddEqualTo(FNCART_CTLEntity.__GROUPNO, groupNo);
                    c.AddEqualTo(FNCART_CTLEntity.__REGISTER_FLG, "1");
                    uc.AddAttributeForUpdate(FNCART_CTLEntity.__REGISTER_FLG, "3");
                    uc.Perform();
                }                
            }
        }

        private DataTable GetData(string section, string groupNo)
        {
            Query q1 = new Query(typeof(FNCART_CTLEntity));
            q1.AddAttribute(FNCART_CTLEntity.__BUCKET_NO);
            q1.AddAttribute(FNCART_CTLEntity.__LINE);
            q1.AddAttribute(FNCART_CTLEntity.__ITEM_CODE);
            q1.AddAttribute(FNCART_CTLEntity.__QTY);
            q1.AddAttribute(FNCART_CTLEntity.__WEIGHT);
            q1.AddAttribute(FNCART_CTLEntity.__COLOR_CODE);
            Condition c1 = q1.GetQueryCondition();
            c1.AddEqualTo(FNCART_CTLEntity.__REGISTER_FLG, "1");
            c1.AddEqualTo(FNCART_CTLEntity.__SECTION, section);
            c1.AddEqualTo(FNCART_CTLEntity.__GROUPNO, groupNo);

            Query q2 = new Query(typeof(FMZKEYEntity));
            q2.AddAttribute(FMZKEYEntity.__ZAIKEY);
            q2.AddAttribute(FMZKEYEntity.__ZKNAME1);
            Condition c2 = q2.GetQueryCondition();
            c2.AddEqualTo(FMZKEYEntity.__MANAGE_ITEM_FLAG, "0");

            q1.AddJoinQuery(FNCART_CTLEntity.__ITEM_CODE, q2, FMZKEYEntity.__ZAIKEY);
            q1.OrderBy(FNCART_CTLEntity.__SEQ_NO);

            DataTable dt = q1.Execute();
            return dt;          

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
    }
}
