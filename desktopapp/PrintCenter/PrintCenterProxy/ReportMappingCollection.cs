using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;
using System.IO;
using System.Windows.Forms;

namespace PrintCenterProxy
{
    [XmlRootAttribute("report_mapping_list")]
    public class ReportMappingCollection : List<report_mapping>
    {
        private static ReportMappingCollection collection = null;

        public static string getReportExternalName(string reportInternalName)
        {
            foreach (report_mapping mapping in getInstance())
            {
                if (mapping.InternalName == reportInternalName)
                {
                    return mapping.ExternalName;
                }
            }

            throw new Exception(string.Format("Failed to find report external name by report internal name '[0]'.", reportInternalName));
        }

        public static string getReportRdlcFileName(string reportInternalName)
        {
            foreach (report_mapping mapping in getInstance())
            {
                if (mapping.InternalName == reportInternalName)
                {
                    return mapping.RdlcFileName;
                }
            }

            throw new Exception(string.Format("Failed to find report rdlc file name by report internal name '[0]'.", reportInternalName));
        }

        private static ReportMappingCollection getInstance()
        {
            if (collection == null)
            {
                Stream stream = null;
                try
                {
                    XmlSerializer serializer = new XmlSerializer(typeof(ReportMappingCollection));
                    stream = File.Open(Application.StartupPath + @"/report_mapping_list.xml", FileMode.Open, FileAccess.Read);
                    collection = serializer.Deserialize(stream) as ReportMappingCollection;
                }
                finally
                {
                    if (stream != null)
                    {
                        stream.Close();
                    }
                }
            }
            return collection;
        }
    }
}
