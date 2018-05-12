using System;
using System.Collections.Generic;
using PrintCenterCommon;
using System.Xml;
using System.IO;
using System.Reflection;
using System.Windows.Forms;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace PrintCenterServer
{
    public class XmlTaskLoader : ITaskLoader
    {
        public Task loadTask()
        {
            string taskFilePath = string.Empty;
            FileStream stream = null;
            try
            {                
                taskFilePath = getNextTaskFile();
                if (taskFilePath == string.Empty) return null;

                //XmlSerializer serializer = new XmlSerializer(typeof(TaskXmlNode));
                //stream = new FileStream(taskFilePath, FileMode.Open);
                //TaskXmlNode taskXmlNode = serializer.Deserialize(stream) as TaskXmlNode;
                //stream.Close();

                TaskXmlNode taskXmlNode = XmlDeserializer.deserialize<TaskXmlNode>(taskFilePath);

                Task task = new Task();
                task.ReportName = taskXmlNode.ReportSetting.ReportName;
                taskXmlNode.ReportSetting.ReportFilePath = Application.StartupPath + @"\Report" + taskXmlNode.ReportSetting.ReportFilePath;
                task.ReportSetting = taskXmlNode.ReportSetting;
                
                foreach(DataSourceXmlNode dataSourceXmlNode in taskXmlNode.DataSourceList)
                {
                    List<object> dataSourceValue = new List<object>();
                    string prefix = Application.StartupPath + @"\Assembly";
                    Type type = ReflectionHelper.getType(prefix + dataSourceXmlNode.AssemblyPath, dataSourceXmlNode.TypeFullName);

                    foreach(RecordXmlNode recordXmlNode in dataSourceXmlNode.RecordList)
                    {                        
                        Object obj = createEntity(type);
                        initEntity(type, obj, recordXmlNode.PropertyList);
                        dataSourceValue.Add(obj);
                    }
                    string dataSourceName = generateDataSourceName(dataSourceXmlNode.TypeFullName);
                    DataSource dataSource = new DataSource(dataSourceName, dataSourceValue);
                    task.addDataSource(dataSource);
                }

                task.CurrentFilePath = handleProcessedTask(taskFilePath);
                return task;
            }
            catch (Exception ex)
            {
                if (stream != null)
                {
                    stream.Close();
                }

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
                    throw new Exception(ex.Message + string.Format(" Failed to load task. Task file '{0}' has been moved to directory '{1}'.", new FileInfo(taskFilePath).Name, dir));
                }
                else
                {
                    throw new Exception(ex.Message + " Failed to load task.");
                }
            }
            finally
            {
                if (stream != null)
                {
                    stream.Close();
                }
            }
        }

        public class XmlDeserializer
        {
            public static T deserialize<T>(string xmlFilePath)
            {
                FileStream stream = null;
                try
                {
                    XmlSerializer serializer = new XmlSerializer(typeof(T));
                    stream = new FileStream(xmlFilePath, FileMode.Open);
                    T t = (T)serializer.Deserialize(stream);
                    stream.Close();
                    return t;
                }
                finally
                {
                    if (stream != null)
                    {
                        stream.Close();
                    }
                }
            }
        }

        private string handleProcessedTask(string taskFilePath)
        {
            if (File.Exists(taskFilePath))
            {
                string dir = GlobalVariable.getPrintingDirectory();
                FileInfo info = new FileInfo(taskFilePath);
                string destFilePath = dir + @"\" + info.Name;
                if (File.Exists(destFilePath))
                {
                    File.Delete(destFilePath);
                }
                File.Move(taskFilePath, destFilePath);

                string printedDirectory = GlobalVariable.getPrintedDirectory();
                removeExpiredTask(printedDirectory);
                string deletedDirectory = GlobalVariable.getDeletedDirectory();
                removeExpiredTask(deletedDirectory);

                return destFilePath;
            }
            return string.Empty;
        }

        private void removeExpiredTask(string directory)
        {
            string[] fileList = Directory.GetFiles(directory);
            int count = fileList.Length - PrintCenterConfiguration.getInstance().NumberOfFileToKeep;
            if (count > 0)
            {
                for (int i = 0; i < count; ++i)
                {
                    File.Delete(fileList[i]);
                }
            }
        }

        private string generateDataSourceName(string typeFullName)
        {
            return typeFullName.Replace(".", "_");
        }

        private string getNextTaskFile()
        {
            string dirToMonitor = PrintCenterConfiguration.getInstance().DirectoryToMonitor;
            if (!Directory.Exists(dirToMonitor))
            {
                throw new Exception(string.Format("Failed to monitor task directory '{0}' doesn't exsit.", dirToMonitor));
            }
            string[] fileList = Directory.GetFiles(dirToMonitor, "task*.xml", SearchOption.TopDirectoryOnly);
            if (fileList.Length > 0)
            {
                return fileList[0];
            }
            else
            {
                return string.Empty;
            }
        }   

        private void initEntity(Type type, object obj, List<PropertyXmlNode> propPairList)
        {
            string methodName = string.Empty;
            try
            {
                foreach (PropertyXmlNode pair in propPairList)
                {
                    methodName = "set_" + pair.Name;
                    MethodInfo methodInfo = type.GetMethod(methodName);
                    if (pair.Type.ToLower() == "byte[]")
                    {
                        byte[] byteArray = Convert.FromBase64String(pair.Value);
                        methodInfo.Invoke(obj, new Object[] { byteArray });
                    }
                    else
                    {
                        methodInfo.Invoke(obj, new Object[] { pair.Value });
                    }
                }
            }
            catch
            {
                throw new Exception(string.Format("Failed to get setter '{0}' of class '{1}'.", methodName, type.FullName));
            }
        }

        private Object createEntity(Type type)
        {
            try
            {
                return Activator.CreateInstance(type);
            }
            catch
            {
                throw new Exception(string.Format("Failed to create entity of class '{0}'.", type.FullName));
            }
        }
    }
}
