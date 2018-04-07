using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using System.Xml.Serialization;

namespace PrintCenterCommon
{
    public class DataSourceXmlNode
    {
        private string assemblyPath = string.Empty;

        [XmlElement("assembly_path")]
        public string AssemblyPath
        {
            get { return assemblyPath; }
            set { assemblyPath = value; }
        }

        private string typeFullName = string.Empty;

        [XmlElement("type_fullname")]
        public string TypeFullName
        {
            get { return typeFullName; }
            set { typeFullName = value; }
        }

        private List<RecordXmlNode> recordList = new List<RecordXmlNode>();

        [XmlArrayAttribute("record_set")]
        [XmlArrayItemAttribute("record")]
        public List<RecordXmlNode> RecordList
        {
            get { return recordList; }
            set { recordList = value; }
        }
    }
}
