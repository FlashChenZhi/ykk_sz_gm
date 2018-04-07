using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;

namespace PrintCenterCommon
{
    [XmlRootAttribute("task", IsNullable = false)]
    public class TaskXmlNode
    {
        private ReportSetting reportSetting = null;

        [XmlElement("report_setting")]
        public ReportSetting ReportSetting
        {
            get { return reportSetting; }
            set { reportSetting = value; }
        }

        //private string reportName = string.Empty;

        //[XmlElement("report_name")]
        //public string ReportName
        //{
        //    get { return reportName; }
        //    set { reportName = value; }
        //}

        private List<DataSourceXmlNode> dataSourceList = new List<DataSourceXmlNode>();

        [XmlArrayAttribute("data_source_list")]
        [XmlArrayItemAttribute("data_source")]
        public List<DataSourceXmlNode> DataSourceList
        {
            get { return dataSourceList; }
            set { dataSourceList = value; }
        }
    }
}
