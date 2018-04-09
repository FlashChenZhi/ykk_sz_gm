using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;

namespace PrintCenterProxy
{
    public class report_mapping
    {
        private string internalName = string.Empty;

        [XmlElement("internal_name")]
        public string InternalName
        {
            get { return internalName; }
            set { internalName = value; }
        }

        private string externalName = string.Empty;

        [XmlElement("external_name")]
        public string ExternalName
        {
            get { return externalName; }
            set { externalName = value; }
        }

        private string rdlcFileName = string.Empty;

        [XmlElement("rdlc_file_name")]
        public string RdlcFileName
        {
            get { return rdlcFileName; }
            set { rdlcFileName = value; }
        }
    }
}
