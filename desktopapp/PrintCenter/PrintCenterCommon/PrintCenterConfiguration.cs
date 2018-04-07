using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;
using System.IO;
using System.Windows.Forms;

namespace PrintCenterCommon
{
    [XmlRoot("setting")]
    public class PrintCenterConfiguration
    {
        private static PrintCenterConfiguration config = null;

        private PrintCenterConfiguration(){}

        public static PrintCenterConfiguration getInstance()
        {
            return getInstance(Application.StartupPath + @"/print_center_config.xml");
        }

        public static PrintCenterConfiguration getInstance(string configFilePath)
        {
            Stream stream = null;
            try
            {
                if (config == null)
                {
                    XmlSerializer serializer = new XmlSerializer(typeof(PrintCenterConfiguration));
                    stream = File.Open(configFilePath, FileMode.Open, FileAccess.Read);
                    config = serializer.Deserialize(stream) as PrintCenterConfiguration;
                }
                return config;
            }
            finally
            {
                if (stream != null)
                {
                    stream.Close();
                }
            }
        }

        private string directoryToMonitor = string.Empty;

        [XmlElement("directory_to_monitor")]
        public string DirectoryToMonitor
        {
            get { return directoryToMonitor; }
            set { directoryToMonitor = value; }
        }

        private int numberOfFileToKeep = 99;

        [XmlElement("number_of_file_to_keep")]
        public int NumberOfFileToKeep
        {
            get { return numberOfFileToKeep; }
            set { numberOfFileToKeep = value; }
        }
    }
}
