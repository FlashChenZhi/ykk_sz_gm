using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;

namespace PrintCenterCommon
{
    [XmlRootAttribute("report_setting", IsNullable = false)]
    public class ReportSetting
    {
        private string reportFilePath = string.Empty;
        private string reportName = string.Empty;
        private string settingFilePath = string.Empty;
        private PrintSetting printSetting = null;

        public ReportSetting(string reportName, string settingFilePath, PrintSetting printSetting)
        {
            this.reportName = reportName;
            this.settingFilePath = settingFilePath;
            this.printSetting = printSetting;
        }

        public ReportSetting()
        {
        }

        [XmlElement("report_name")]
        public string ReportName
        {
            get { return reportName; }
            set { reportName = value; }
        }


        [XmlElement("report_file_path")]
        public string ReportFilePath
        {
            get { return reportFilePath; }
            set { reportFilePath = value; }
        }  

        [XmlElement("print_setting")]
        public PrintSetting PrintSetting
        {
            get { return printSetting; }
            set { printSetting = value; }
        }

        [XmlIgnore]
        public string SettingFilePath
        {
            get { return settingFilePath; }
            set { settingFilePath = value; }
        }
    }
}
