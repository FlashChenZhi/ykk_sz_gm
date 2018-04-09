using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;

namespace PrintCenterCommon
{
    [XmlRootAttribute("print_setting", IsNullable = false)]
    public class PrintSetting
    {
        private string reportFilePath = string.Empty;      
        private string printerName = string.Empty;
        private string paperName = string.Empty;
        private string paperWidth = string.Empty;
        private string paperHeight = string.Empty;
        private string marginTop = string.Empty;
        private string marginBottom = string.Empty;
        private string marginLeft = string.Empty;
        private string marginRight = string.Empty;
        private string orientation = "V";
        //private string reportName = string.Empty;

        //[XmlElement("report_name")]
        //public string ReportName
        //{
        //    get { return reportName; }
        //    set { reportName = value; }
        //}

        [XmlElement("printer_name")]
        public string PrinterName
        {
            get { return printerName; }
            set { printerName = value; }
        }

        [XmlElement("paper_name")]
        public string PaperName
        {
            get { return paperName; }
            set { paperName = value; }
        }

        [XmlElement("paper_width")]
        public string PaperWidth
        {
            get { return paperWidth; }
            set { paperWidth = value; }
        }

        [XmlElement("paper_height")]
        public string PaperHeight
        {
            get { return paperHeight; }
            set { paperHeight = value; }
        }

        [XmlElement("orientation")]
        public string Orientation
        {
            get { return orientation; }
            set { orientation = value; }
        }

        [XmlElement("margin_right")]
        public string MarginRight
        {
            get { return marginRight; }
            set { marginRight = value; }
        }

        [XmlElement("margin_left")]
        public string MarginLeft
        {
            get { return marginLeft; }
            set { marginLeft = value; }
        }

        [XmlElement("margin_bottom")]
        public string MarginBottom
        {
            get { return marginBottom; }
            set { marginBottom = value; }
        }

        [XmlElement("margin_top")]
        public string MarginTop
        {
            get { return marginTop; }
            set { marginTop = value; }
        }

        [XmlElement("report_file_path")]
        public string ReportFilePath
        {
            get { return reportFilePath; }
            set { reportFilePath = value; }
        }  
    }
}
