using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Microsoft.Reporting.WinForms;
using System.IO;
using System.Drawing.Printing;
using System.Drawing.Imaging;
using System.Collections;
using PrintCenterServer;
using PrintCenter;
using PrintCenterCommon;

namespace PrintCenter
{
    public partial class ReportViewerFrm : System.Windows.Forms.Form
    {
        List<DataSource> dataSourceList = new List<DataSource>();
        //string printerName;
        ReportSetting reportSetting = null;

        public ReportViewerFrm(ReportSetting reportSetting)
        {
            this.reportSetting = reportSetting;
            InitializeComponent();
            //this.rptViewer.LocalReport.ReportEmbeddedResource = "WareBrowserReports.reports." + reportSetting.ReportFilePath;
            this.rptViewer.LocalReport.ReportPath = reportSetting.ReportFilePath;
        }

        /// <summary>
        /// DataSource of the Main Report
        /// </summary>
        private object m_MainDataSet = null;
        public object MainDataSet
        {
            get
            {
                return this.m_MainDataSet;
            }
            set
            {
                this.m_MainDataSet = value;
            }
        }

        /// <summary>
        /// DataSource of the DrillThrough Report
        /// </summary>
        private object m_DrillDataSet = null;
        public object DrillDataSet
        {
            get
            {
                return this.m_DrillDataSet;
            }
            set
            {
                this.m_DrillDataSet = value;
            }
        }

        //Report Path
        //public string ReportPath
        //{
        //    get
        //    {
        //        return this.rptViewer.LocalReport.ReportPath;
        //    }
        //    set
        //    {
        //        this.rptViewer.LocalReport.ReportPath = value;
        //    }
        //}

        public string ReportPathEmbedded
        {
            get
            {
                return this.rptViewer.LocalReport.ReportEmbeddedResource;
            }
            set
            {
                this.rptViewer.LocalReport.ReportEmbeddedResource = value;
            }
        }

        /// <summary>
        /// Data Source Name of the DrillThrough Report
        /// </summary>
        private string m_DrillDataSourceName = string.Empty;
        public string DrillDataSourceName
        {
            get
            {
                return this.m_DrillDataSourceName;
            }
            set
            {
                this.m_DrillDataSourceName = value;
            }
        }

        /// <summary>
        /// Data Source Name of the Main Report
        /// </summary>
        private string m_MainDataSourceName = string.Empty;
        public string MainDataSourceName
        {
            get
            {
                return this.m_MainDataSourceName;
            }
            set
            {
                this.m_MainDataSourceName = value;
            }
        }

        /// <summary>
        /// Set the DataSource for the report using a strong-typed dataset
        /// Call it in Form_Load event
        /// </summary>
        /// 


        public void AddDataSource(string name, object dataSource)
        {
            dataSourceList.Add(new DataSource(name, dataSource));
        }

        private void AddReportDataSource(object ReportSource, string ReportDataSetName)
        {
            System.Type type = ReportSource.GetType();

            if (type == null)
            {
                System.Windows.Forms.MessageBox.Show("Report Viewr: \r\n   The datasource is of the wrong type!");
                return;
            }
            else
            {
                System.Reflection.PropertyInfo[] picData = type.GetProperties();
                bool bolExist = false;
                foreach (System.Reflection.PropertyInfo piData in picData)
                {
                    if (piData.Name == "Tables")
                    {
                        bolExist = true;

                        if (ReportDataSetName == string.Empty)
                        {
                            System.Windows.Forms.MessageBox.Show("Report Viewr: \r\n    The dataset name for the report does not exist or is empty!");
                            return;
                        }

                        this.rptViewer.LocalReport.DataSources.Add(
                            new Microsoft.Reporting.WinForms.ReportDataSource(ReportDataSetName,
                            (piData.GetValue(ReportSource, null) as System.Data.DataTableCollection)[0])
                            );
                        this.rptViewer.RefreshReport();

                        break;
                    }
                }

                if (!bolExist)
                {
                    System.Windows.Forms.MessageBox.Show("Report Viewr: \r\n    The datasource is of the wrong type!");
                    return;
                }
            }
        }

        private System.Data.DataTableCollection GetTableCollection(object ReportSource)
        {
            System.Type type = ReportSource.GetType();

            if (type == null)
            {
                System.Windows.Forms.MessageBox.Show("Report Viewr: \r\n     The datasource is of the wrong type!");
                return null;
            }
            else
            {
                System.Reflection.PropertyInfo[] picData = type.GetProperties();
                bool bolExist = false;
                foreach (System.Reflection.PropertyInfo piData in picData)
                {
                    if (piData.Name == "Tables")
                    {
                        bolExist = true;
                        return piData.GetValue(ReportSource, null) as System.Data.DataTableCollection;
                    }
                }

                if (!bolExist)
                {
                    System.Windows.Forms.MessageBox.Show("Report Viewr: \r\n    The datasource is of the wrong type!");
                    return null;
                }
            }
            return null;
        }

        private void ReportViewer_Load(object sender, EventArgs e)
        {
            //InitReport();
            this.rptViewer.RefreshReport();
        }

        public void InitReport()
        {
            foreach (DataSource dataSource in dataSourceList)
            {
                this.rptViewer.LocalReport.DataSources.Add(
                new Microsoft.Reporting.WinForms.ReportDataSource(dataSource.DataSourceName, dataSource.DataSourceValue)
                );
            }
            System.Drawing.Printing.PrintDocument pd = new PrintDocument();
        }

        private void rptViewer_Drillthrough(object sender, Microsoft.Reporting.WinForms.DrillthroughEventArgs e)
        {
            if (this.m_DrillDataSet == null)
            {
                System.Windows.Forms.MessageBox.Show("Report Viewr: \r\n    The dataset name for the report does not exist or is empty!");
                return;
            }
            else
            {
                if (this.m_DrillDataSourceName == string.Empty)
                {
                    System.Windows.Forms.MessageBox.Show("Report Viewr: \r\n    The datasource is of the wrong type!");
                    return;
                }
                else
                {
                    Microsoft.Reporting.WinForms.LocalReport report = e.Report as Microsoft.Reporting.WinForms.LocalReport;
                    report.DataSources.Add(new Microsoft.Reporting.WinForms.ReportDataSource(this.m_DrillDataSourceName, this.GetTableCollection(this.m_DrillDataSet)[0]));
                }
            }
        }

        private void toolPrint_Click(object sender, EventArgs e)
        {
            try
            {
                Print();
            }
            catch (Exception ex)
            {
                System.Windows.Forms.MessageBox.Show("Report Viewer:\r\n    " + ex.Message);
            }
        }

        public void Print()
        {
            if (this.printDoc == null)
            {
                this.printDoc = new EMFStreamPrintDocument(this.rptViewer.LocalReport, reportSetting.PrintSetting);
                this.printDoc.PrinterSettings.PrinterName = reportSetting.PrintSetting.PrinterName;

                //if (this.printDoc.ErrorMessage != string.Empty)
                //{
                //    System.Windows.Forms.MessageBox.Show("Report Viewer: \r\n    " + this.printDoc.ErrorMessage);
                //    this.printDoc = null;
                //    return;
                //}
            }

            this.printDoc.Print();
            this.printDoc = null;

        }

        private EMFStreamPrintDocument printDoc = null;

        private void toolPreview_Click(object sender, EventArgs e)
        {
            try
            {
                if (this.printDoc == null)
                {
                    this.printDoc = new EMFStreamPrintDocument(this.rptViewer.LocalReport, reportSetting.PrintSetting);
                    this.printDoc.PrinterSettings.PrinterName = reportSetting.PrintSetting.PrinterName;
                    //if (this.printDoc.ErrorMessage != string.Empty)
                    //{
                    //    System.Windows.Forms.MessageBox.Show("Report Viewer:\r\n    " + this.printDoc.ErrorMessage);
                    //    this.printDoc = null;
                    //    return;
                    //}
                }

                this.PreviewDialog.Document = this.printDoc;
                this.PreviewDialog.ShowDialog();
                this.printDoc = null;
            }
            catch (Exception ex)
            {
                System.Windows.Forms.MessageBox.Show("Report Viewer:\r\n    " + ex.Message);
            }

        }

        private string GetTimeStamp()
        {
            string strRet = string.Empty;
            System.DateTime dtNow = System.DateTime.Now;
            strRet += dtNow.Year.ToString() +
                        dtNow.Month.ToString("00") +
                        dtNow.Day.ToString("00") +
                        dtNow.Hour.ToString("00") +
                        dtNow.Minute.ToString("00") +
                        dtNow.Second.ToString("00") +
                        System.DateTime.Now.Millisecond.ToString("000");
            return strRet;

        }

        private void toolExcel_Click(object sender, EventArgs e)
        {

            Microsoft.Reporting.WinForms.Warning[] Warnings;
            string[] strStreamIds;
            string strMimeType;
            string strEncoding;
            string strFileNameExtension;

            byte[] bytes = this.rptViewer.LocalReport.Render("Excel", null, out strMimeType, out strEncoding, out strFileNameExtension, out strStreamIds, out Warnings);

            string strFilePath = @"D:\" + this.GetTimeStamp() + ".xls";

            using (System.IO.FileStream fs = new FileStream(strFilePath, FileMode.Create))
            {
                fs.Write(bytes, 0, bytes.Length);
            }

            if (System.Windows.Forms.MessageBox.Show("Report Viewer: \r\n    Succeed to export the excel file!" + strFilePath + "\r\n    Do you want to open the file" + strFilePath + "?", "", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
            {
                System.Diagnostics.Process.Start(strFilePath);
            }

        }

        private void toolRefresh_Click(object sender, EventArgs e)
        {
            this.rptViewer.RefreshReport();
        }

        private void toolStop_Click(object sender, EventArgs e)
        {
            this.rptViewer.CancelRendering(0);
        }

        private void toolBack_Click(object sender, EventArgs e)
        {
            if (this.rptViewer.LocalReport.IsDrillthroughReport)
                this.rptViewer.PerformBack();
        }

        private void toolPageSettings_Click(object sender, EventArgs e)
        {
            PageSettingsFrm frm = new PageSettingsFrm("aa");
            frm.ShowDialog();
        }

        private void toolFirst_Click(object sender, EventArgs e)
        {
            this.rptViewer.CurrentPage = 1;
        }

        private void toolLast_Click(object sender, EventArgs e)
        {
            this.rptViewer.CurrentPage = this.rptViewer.LocalReport.GetTotalPages();
        }

        private void tool25_Click(object sender, EventArgs e)
        {
            this.rptViewer.ZoomMode = ZoomMode.Percent;
            this.rptViewer.ZoomPercent = 25;
        }

        private void tool50_Click(object sender, EventArgs e)
        {
            this.rptViewer.ZoomMode = ZoomMode.Percent;
            this.rptViewer.ZoomPercent = 50;
        }

        private void tool100_Click(object sender, EventArgs e)
        {
            this.rptViewer.ZoomMode = ZoomMode.Percent;
            this.rptViewer.ZoomPercent = 100;
        }

        private void tool200_Click(object sender, EventArgs e)
        {
            this.rptViewer.ZoomMode = ZoomMode.Percent;
            this.rptViewer.ZoomPercent = 200;
        }

        private void tool400_Click(object sender, EventArgs e)
        {
            this.rptViewer.ZoomMode = ZoomMode.Percent;
            this.rptViewer.ZoomPercent = 400;
        }

        private void toolWhole_Click(object sender, EventArgs e)
        {
            this.rptViewer.ZoomMode = ZoomMode.FullPage;
        }

        private void toolPageWidth_Click(object sender, EventArgs e)
        {
            this.rptViewer.ZoomMode = ZoomMode.PageWidth;
        }

        private void toolPrevious_Click(object sender, EventArgs e)
        {
            if (this.rptViewer.CurrentPage != 1)
                this.rptViewer.CurrentPage--;
        }

        private void toolNext_Click(object sender, EventArgs e)
        {
            if (this.rptViewer.CurrentPage != this.rptViewer.LocalReport.GetTotalPages())
                this.rptViewer.CurrentPage++;
        }

        private void tspbPrint_ButtonClick(object sender, EventArgs e)
        {
            try
            {
                if (this.printDoc == null)
                {
                    this.printDoc = new EMFStreamPrintDocument(this.rptViewer.LocalReport, reportSetting.PrintSetting);
                    this.printDoc.PrinterSettings.PrinterName = reportSetting.PrintSetting.PrinterName;
                    //if (this.printDoc.ErrorMessage != string.Empty)
                    //{
                    //    System.Windows.Forms.MessageBox.Show("Report Viewer: \r\n    " + this.printDoc.ErrorMessage);
                    //    this.printDoc = null;
                    //    return;
                    //}
                }
                

                this.printDoc.Print();
                this.printDoc = null;
            }
            catch (Exception ex)
            {
                System.Windows.Forms.MessageBox.Show("Report Viewer:\r\n    " + ex.Message);
            }
        }


    }
}