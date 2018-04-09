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
using System.Xml;
using PrintCenter;
using PrintCenterCommon;

namespace PrintCenter
{
    public partial class EMFStreamPrintDocument : System.Drawing.Printing.PrintDocument
    {
        private int m_CurrentPageIndex;

        private IList<Stream> m_EMFStreams;
        private Microsoft.Reporting.WinForms.LocalReport m_LocalReport = null;
        private System.Drawing.Imaging.Metafile m_PageImage = null;
        private PrintSetting printSetting = null;
        private string m_ErrorMessage = string.Empty;

        public EMFStreamPrintDocument(Microsoft.Reporting.WinForms.LocalReport localReport, PrintSetting printSetting)
            : base()
        {
            this.printSetting = printSetting;
            this.m_LocalReport = localReport;            

            setPrinterSettings(this.PrinterSettings, this.printSetting);
            string retMessage = invalidatePrintSetting(this.printSetting);
            if (retMessage != string.Empty)
            {
                throw new Exception(retMessage);
            }

            setPageSettings(this.PrinterSettings.DefaultPageSettings, this.printSetting);
            setPageSettings(this.DefaultPageSettings, this.printSetting);             
        }

        private void setPrinterSettings(PrinterSettings printerSettings, PrintSetting printSetting)
        {
            printerSettings.PrinterName = printSetting.PrinterName;
            //setPageSettings(printerSettings.DefaultPageSettings, printSetting);
        }

        private void setPageSettings(PageSettings pageSettings, PrintSetting printSetting)
        {
            foreach (System.Drawing.Printing.PaperSize ps in base.PrinterSettings.PaperSizes)
            {
                if (ps.PaperName == printSetting.PaperName)
                {
                    pageSettings.PaperSize = ps;
                    break;
                }
            }
            pageSettings.Landscape = printSetting.Orientation == "V" ? false : true;            
        }

        private string invalidatePrintSetting(PrintSetting printSetting)
        {
            //yutao
            if (!Printer.PrinterInList(printSetting.PrinterName))
            {
                return "Printer defined in the config file is not in the available printer list!";
            }
            //yutao

            // base.PrinterSettings.PrinterName = printSetting.PrinterName;

            if (!Printer.FormInPrinter(printSetting.PrinterName, printSetting.PaperName))
            {
                return "Printer defined in the config file does not support the customized papersize!";
            }

            if (!Printer.FormSameSize(printSetting.PrinterName, printSetting.PaperName, System.Convert.ToDecimal(printSetting.PaperWidth), System.Convert.ToDecimal(printSetting.PaperHeight)))
            {
                return "Papersize defined in the config file is not the same with the one in the system!";
            }

            bool doesExist = false;

            foreach (System.Drawing.Printing.PaperSize ps in base.PrinterSettings.PaperSizes)
            {
                if (ps.PaperName == printSetting.PaperName)
                {
                    base.PrinterSettings.DefaultPageSettings.PaperSize = ps;
                    base.DefaultPageSettings.PaperSize = ps;
                    doesExist = true;
                    break;
                }
            }

            if (!doesExist)
            {
                return "Can not use the customized paper, because the printer selected does not the customized papersize!";
            }

            return string.Empty;
        }

        private EMFDeviceInfo getEMFDeviceInfo(PrintSetting printSetting)
        {
            EMFDeviceInfo emfdi = new EMFDeviceInfo();

            emfdi.Landscape = printSetting.Orientation == "V" ? false : true;
            emfdi.PageWidth = System.Convert.ToDecimal(printSetting.PaperWidth);
            emfdi.PageHeight = System.Convert.ToDecimal(printSetting.PaperHeight);
            emfdi.MarginTop = System.Convert.ToDecimal(printSetting.MarginTop);
            emfdi.MarginBottom = System.Convert.ToDecimal(printSetting.MarginBottom);
            emfdi.MarginLeft = System.Convert.ToDecimal(printSetting.MarginLeft);
            emfdi.MarginRight = System.Convert.ToDecimal(printSetting.MarginRight);

            return emfdi;
        }        

        private Stream CreateStream(string Name, string FileNameExtension, Encoding Encoding, string MimeType, bool WillSeek)
        {
            //System.IO.Stream streamRet = new System.IO.MemoryStream();
            //this.m_EMFStreams.Add(streamRet);
            //return streamRet;


            Stream stream = new FileStream(@"..\..\" + Name +
           "." + FileNameExtension, FileMode.Create);
            this.m_EMFStreams.Add(stream);
            return stream;
        }

        private void Export(LocalReport Report)
        {
            EMFDeviceInfo emfdi = this.getEMFDeviceInfo(this.printSetting);

            string strDeviceInfo = emfdi.DeviceInfoString;

            emfdi = null;
            Microsoft.Reporting.WinForms.Warning[] Warnings;

            this.m_EMFStreams = new System.Collections.Generic.List<System.IO.Stream>();

            Report.Render("Image", strDeviceInfo, this.CreateStream, out Warnings);

            foreach (System.IO.Stream s in this.m_EMFStreams)
                s.Position = 0;
        }

        protected override void OnBeginPrint(PrintEventArgs ev)
        {
            base.OnBeginPrint(ev);
        }

        protected override void OnPrintPage(PrintPageEventArgs ev)
        {
            base.OnPrintPage(ev);

            if (this.m_EMFStreams == null || this.m_EMFStreams.Count == 0)
            {
                
                this.Export(this.m_LocalReport);
                this.m_CurrentPageIndex = 0;
                if (this.m_EMFStreams.Count == 0 || this.m_EMFStreams == null)
                    return;
            }

            this.m_PageImage = new Metafile(this.m_EMFStreams[this.m_CurrentPageIndex]);           
            ev.Graphics.DrawImageUnscaledAndClipped(this.m_PageImage, ev.PageBounds);

            this.m_CurrentPageIndex++;

            ev.HasMorePages = (this.m_CurrentPageIndex < this.m_EMFStreams.Count);            

            if (this.m_CurrentPageIndex == this.m_EMFStreams.Count)
            {
                this.m_CurrentPageIndex = 0;
                this.m_EMFStreams.Clear();
                this.m_EMFStreams = null;
                this.m_PageImage.Dispose();
                base.Dispose();
            }
        }
    }
}