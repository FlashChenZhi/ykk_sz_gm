using System;
using System.Collections.Generic;
using System.Xml;
using System.IO;
using PrintCenterCommon;
using PrintCenterProxy;
using System.Reflection;
using System.Text;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

namespace PrintCenterService
{
    public class XmlTaskPersister : MarshalByRefObject, ITaskPersister
    {
        public string persistTask(string dataSourceListXml)
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(dataSourceListXml);
                return writeToXml(doc);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message + " Failed to persist task.");
            }
        }

        private string writeToXml(XmlDocument doc)
        {
            XmlWriter writer = null;
            FileStream stream = null;
            try
            {
                string filepath = string.Empty;
                do
                {
                    filepath = generateTakeFilePath();
                }
                while (filepath == string.Empty || File.Exists(filepath));
                stream = new FileStream(filepath, FileMode.Create);
                writer = XmlWriter.Create(stream);
                doc.WriteContentTo(writer);
                return filepath;
            }
            catch
            {
                throw new Exception("Failed to create task file.");
            }
            finally
            {
                if (writer != null)
                {

                    writer.Flush();
                    writer.Close();
                }

                if (stream != null)
                {
                    stream.Close();
                }
            }
        }

        private string generateTakeFilePath()
        {
            string dirToMonitor = PrintCenterConfiguration.getInstance().DirectoryToMonitor;
            DateTime now = DateTime.Now;
            StringBuilder builder = new StringBuilder("task");
            builder.Append(now.Year);
            builder.Append(now.Month.ToString().PadLeft(2, '0'));
            builder.Append(now.Day.ToString().PadLeft(2, '0'));
            builder.Append(now.Hour.ToString().PadLeft(2, '0'));
            builder.Append(now.Minute.ToString().PadLeft(2, '0'));
            builder.Append(now.Second.ToString().PadLeft(2, '0'));
            builder.Append(now.Millisecond.ToString().PadLeft(3, '0'));
            string filename = builder.ToString();
            return dirToMonitor + @"\" + filename + ".xml";
        }
    }
}
