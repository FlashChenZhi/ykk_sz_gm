using System;
using System.Collections.Generic;
using System.IO;
using PrintCenterServer;
using PrintCenterCommon;
using YkkReports.entities;
using System.Configuration;

namespace PrintCenter
{
    public class PrintCenter
    {
        public void DoPrint<T>(string printerName, object head, List<T> bodyList)
        {
            if (head == null && (bodyList == null || bodyList.Count == 0)) return;
            if (bodyList.Count == 0) return;
            object body = bodyList[0];

            ReportSetting rs = new ReportSetting();
            PrintSetting ps = new PrintSetting();
            ps.MarginBottom = "0";
            ps.MarginLeft = "0";
            ps.MarginRight = "0";
            ps.MarginTop = "0";
            ps.Orientation = "H";
            ps.PaperHeight = "29.69";
            ps.PaperName = "A4";
            ps.PaperWidth = "21.01";
            ps.PrinterName = printerName;
            rs.PrintSetting = ps;

            Type type = body.GetType();
            if (type == typeof(ErrorMessageDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\ErrorMessageReport.rdlc";
            }
            else if (type == typeof(InOutResultDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\InOutRelustReport.rdlc";
            }
            else if (type == typeof(LocationStorageDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\LocationStorageReport.rdlc";
            }
            else if (type == typeof(MessageDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\MessageReport.rdlc";
            }

            else if (type == typeof(OvertimeStorageDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\OvertimeStorageReport.rdlc";
            }
            else if (type == typeof(StockoutCancel))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\StockoutCancelReport.rdlc";
            }
            else if (type == typeof(StorageDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\StorageReport.rdlc";
            }
            else if (type == typeof(TicketNoAndStorageDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\TicketNoAndStorageReport.rdlc";
            }
            else if (type == typeof(WorkViewDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\WorkViewReport.rdlc";
            }
            else if (type == typeof(SIDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\SIReport.rdlc";
            }
            else if (type == typeof(AssemblyDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\AssemblyReport.rdlc";
            }
            else if (type == typeof(RetrievalOrderDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\RetrievalOrder.rdlc";
            }

            ReportViewerFrm frm = new ReportViewerFrm(rs);
            if (head != null)
            {
                frm.AddDataSource(head.GetType().FullName.Replace('.', '_'), head);
            }

            if (bodyList != null && bodyList.Count > 0)
            {
                frm.AddDataSource(body.GetType().FullName.Replace('.', '_'), bodyList);
            }
            frm.InitReport();
            frm.Print();
            //frm.Show();
        }

        public void DoPrint(string printerName, object obj)
        {
            ReportSetting rs = new ReportSetting();
            PrintSetting ps = new PrintSetting();
            ps.MarginBottom = "0";
            ps.MarginLeft = "0";
            ps.MarginRight = "0";
            ps.MarginTop = "0";
            ps.Orientation = "V";            
            ps.PaperName = "Card";
            ps.PaperWidth = "11.00";
            ps.PaperHeight = "15.00";
            ps.PrinterName = printerName;

            string labelPaperName = ConfigurationManager.AppSettings.Get("label-paper-name");
            if (labelPaperName == "A4")
            {
                ps.PaperName = labelPaperName;
                ps.PaperWidth = "21.00";
                ps.PaperHeight = "29.70";
                
            }
            else if (labelPaperName == "A5")
            {
                ps.PaperName = labelPaperName;
                ps.PaperWidth = "14.80";
                ps.PaperHeight = "21.00";                
                
            }
            else if (labelPaperName == "A6")
            {
                ps.PaperName = labelPaperName;
                ps.PaperWidth = "10.50";
                ps.PaperHeight = "14.80";
            }

            rs.PrintSetting = ps;

            Type type = obj.GetType();
            if (type == typeof(LabelNormal))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\LabelNormalReport.rdlc";
            }
            else if (type == typeof(LabelDetail))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\LabelDetailReport.rdlc";
            }
            else if (type == typeof(LabelCustomer))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\LabelCustomerReport.rdlc";
            }
            else if (type == typeof(LabelFuYong))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\LabelFuYongReport.rdlc";
            }
            else if (type == typeof(LabelExternal))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\LabelExternalReport.rdlc";
            }
            else if (type == typeof(LabelSubdivided))
            {
                rs.ReportFilePath = System.Windows.Forms.Application.StartupPath + @"\reports\LabelSubdividedReport.rdlc";
            }
            ReportViewerFrm frm = new ReportViewerFrm(rs);
            frm.AddDataSource(type.FullName.Replace('.', '_'), obj);
            frm.InitReport();
            frm.Print();
            //frm.Show();
        }
       
        public void DoPrint(Task task)
        {
            try
            {
                //PrintSetting printSetting = getReportSetting(task.ReportName);
                //ReportSetting reportSetting = task.ReportSetting;
                ReportViewerFrm frm = new ReportViewerFrm(task.ReportSetting);
                foreach (DataSource dataSource in task.DataSourceList)
                {
                    frm.AddDataSource(dataSource.DataSourceName, dataSource.DataSourceValue);
                }

                frm.InitReport();
                //frm.Show();
                frm.Print();
                handlePrintedTask(task.CurrentFilePath);
            }
            catch (Exception ex)
            {
                string taskFilePath = task.CurrentFilePath;
                if (File.Exists(taskFilePath))
                {
                    FileInfo info = new FileInfo(taskFilePath);
                    string dir = GlobalVariable.getDeletedDirectory();
                    string destFilePath = dir + @"\" + info.Name;
                    if (File.Exists(destFilePath))
                    {
                        File.Delete(destFilePath);
                    }
                    File.Move(taskFilePath, destFilePath);                    
                }
                throw ex;
            }
        }

        private void handlePrintedTask(string taskFilePath)
        {
            if (File.Exists(taskFilePath))
            {
                FileInfo info = new FileInfo(taskFilePath);
                string dir = GlobalVariable.getPrintedDirectory();
                string destFilePath = dir + @"\" + info.Name;
                if (File.Exists(destFilePath))
                {
                    File.Delete(destFilePath);
                }
                File.Move(taskFilePath, destFilePath);
            }
        }

        //private PrintSetting getReportSetting(string reportName)
        //{
        //    string reportSettingFile = getReportSettingFile(reportName);
        //    XmlDocument doc = new XmlDocument();
        //    doc.Load(reportSettingFile);
        //    PrintSetting reportSetting = new PrintSetting();
        //    reportSetting.ReportFilePath = readInnerText(doc, "report_file_path");
        //    reportSetting.PrinterName = readInnerText(doc, "printer_name");
        //    reportSetting.PaperName = readInnerText(doc, "paper_name");            
        //    reportSetting.PaperWidth = readInnerText(doc, "paper_width");
        //    reportSetting.PaperHeight = readInnerText(doc, "paper_height");
        //    reportSetting.MarginTop = readInnerText(doc, "margin_top");
        //    reportSetting.MarginBottom = readInnerText(doc, "margin_bottom");
        //    reportSetting.MarginLeft = readInnerText(doc, "margin_left");
        //    reportSetting.MarginRight = readInnerText(doc, "margin_right");
        //    reportSetting.Orientation = readInnerText(doc, "orientation");
        //    return reportSetting;
        //}


        //private string getReportSettingFile(string reportName)
        //{
        //    string[] fileList = getReportSettingFileList();
        //    string reportSettingFile = string.Empty;
        //    for (int i = 0; i < fileList.Length; ++i)
        //    {
        //        XmlDocument doc = new XmlDocument();
        //        doc.Load(fileList[i]);
        //        if (readInnerText(doc, "report_name") == reportName)
        //        {
        //            reportSettingFile = fileList[i];
        //            break;
        //        }
        //    }

        //    if (reportSettingFile == string.Empty)
        //    {
        //        throw new Exception(string.Format("Cannot find report setting file for report '{0}'", reportName));
        //    }

        //    return reportSettingFile;
        //}

        private string[] getReportSettingFileList()
        {
            string reportSettingsDir = System.Windows.Forms.Application.StartupPath + @"\ReportSettings";
            try
            {
                if (!Directory.Exists(reportSettingsDir))
                {
                    Directory.CreateDirectory(reportSettingsDir);
                }

                return Directory.GetFiles(reportSettingsDir, "report*.xml");
            }
            catch (Exception ex)
            {
                throw new Exception(string.Format("Filed to get report setting file(s) in directory {0}. ", reportSettingsDir), ex);
            }
        }

        //private string readInnerText(XmlDocument doc, string tagName)
        //{
        //    try
        //    {
        //        return doc.SelectSingleNode("/report_setting/" + tagName).InnerText;
        //    }
        //    catch (Exception ex)
        //    {
        //        throw new Exception(string.Format("failed to read inner text of tag '{0}'.", tagName), ex);
        //    }
        //}       
    }
}
