using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Xml;
using System.Reflection;
using System.Windows.Forms;
using PrintCenterCommon;
using System.Xml.Serialization;

namespace PrintCenterProxy
{
    [Serializable]
    public class DataSourceList
    {
        private IList<IList<object>> list = new List<IList<object>>();

        public IList<IList<object>> List
        {
            get { return list; }
        }

        public void addDataSource<T>(IList<T> dataSource)
        {
            List<object> objDataSource = new List<object>();
            foreach (T record in dataSource)
            {
                objDataSource.Add((object)record);
            }
            list.Add(objDataSource);
        }

        public void addDataSource<T>(T dataSource)
        {
            IList<object> objDataSource = new List<object>();
            objDataSource.Add((object)dataSource);
            list.Add(objDataSource);
        }

        public string toXml(string reportName, string rdlcName)
        {
            XmlWriter writer = null;
            Stream stream = null;
            XmlSerializer serializer = null;
            try
            {
                if (!Directory.Exists(Application.StartupPath + @"/PrintSetting/ReportSetting"))
                {
                    Directory.CreateDirectory(Application.StartupPath + @"/PrintSetting/ReportSetting");
                }

                string[] fileList = Directory.GetFiles(Application.StartupPath + @"/PrintSetting/ReportSetting");
                bool hasCustomizedPrintSetting = false;
                ReportSetting reportSetting = null;

                for (int i = 0; i < fileList.Length; ++i)
                {
                    try
                    {
                        serializer = new XmlSerializer(typeof(ReportSetting));
                        stream = File.Open(fileList[i], FileMode.Open, FileAccess.Read);
                        reportSetting = serializer.Deserialize(stream) as ReportSetting;
                        if (reportSetting.ReportName == reportName)
                        {
                            hasCustomizedPrintSetting = true;
                            break;
                        }
                    }
                    catch
                    {
                        throw new Exception(string.Format("读取自定义打印配置文件'[0]'时发生错误。", fileList[i]));
                    }
                }

                if (!hasCustomizedPrintSetting)
                {
                    PrintSetting printSetting = null;
                    if (!File.Exists(Application.StartupPath + @"/PrintSetting/DefaultPrintSetting.xml"))
                    {
                        throw new Exception("未配置打印机。");
                    }

                    try
                    {
                        serializer = new XmlSerializer(typeof(PrintSetting));
                        stream = File.Open(Application.StartupPath + @"/PrintSetting/DefaultPrintSetting.xml", FileMode.Open, FileAccess.Read);
                        printSetting = serializer.Deserialize(stream) as PrintSetting;
                    }
                    catch
                    {
                        throw new Exception(string.Format("读取默认打印配置文件'[0]'时发生错误。", Application.StartupPath + @"/PrintSetting/DefaultPrintSetting.xml"));
                    }

                    reportSetting = new ReportSetting();
                    reportSetting.ReportName = reportName;
                    reportSetting.PrintSetting = printSetting;
                }

                TaskXmlNode taskXmlNode = new TaskXmlNode();

                FileInfo info = new FileInfo(Application.ExecutablePath);
                string applicationName = info.Name.Substring(0, info.Name.LastIndexOf('.'));
                reportSetting.ReportFilePath = string.Format(@"\{0}\{1}", applicationName, rdlcName);
                taskXmlNode.ReportSetting = reportSetting;
                

                foreach (IList<object> objList in this.list)
                {
                    if (objList.Count == 0) continue;

                    DataSourceXmlNode dataSourceXmlNode = new DataSourceXmlNode();
                    taskXmlNode.DataSourceList.Add(dataSourceXmlNode);

                    Type type = objList[0].GetType();
                    dataSourceXmlNode.AssemblyPath = string.Format(@"\{0}\{1}", applicationName, new FileInfo(type.Assembly.Location).Name);
                    dataSourceXmlNode.TypeFullName = type.FullName;

                    foreach (object obj in objList)
                    {
                        RecordXmlNode recordXmlNode = new RecordXmlNode();
                        dataSourceXmlNode.RecordList.Add(recordXmlNode);

                        List<PropertyXmlNode> propList = readProperty(obj, type);

                        foreach (PropertyXmlNode prop in propList)
                        {
                            recordXmlNode.PropertyList.Add(prop);
                        }
                    }
                }

                serializer = new XmlSerializer(typeof(TaskXmlNode));
                StringBuilder builder = new StringBuilder();
                writer = XmlWriter.Create(builder);
                serializer.Serialize(writer, taskXmlNode);
                return builder.ToString().Replace("utf-16","utf-8");
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message + " 无法生成打印所需的XML文件。");
            }
            finally
            {
                if (writer != null)
                {
                    writer.Close();
                }
                if (stream != null)
                {
                    stream.Close();
                }
            }
        }

        private List<PropertyXmlNode> readProperty(object obj, Type type)
        {
            List<PropertyXmlNode> propList = new List<PropertyXmlNode>();
            string methodName = string.Empty;
            try
            {
                PropertyInfo[] propInfoList = type.GetProperties();
                MethodInfo[] test = type.GetMethods();
                foreach (PropertyInfo propInfo in propInfoList)
                {
                    methodName = "get_" + propInfo.Name;
                    MethodInfo methodInfo = type.GetMethod(methodName);
                    string propType = propInfo.PropertyType.Name;
                    string propValue = string.Empty;
                    if (propType.ToLower() == "byte[]")
                    {
                        byte[] byteArray = methodInfo.Invoke(obj, null) as byte[];
                        propValue = byteArray != null ? Convert.ToBase64String(byteArray) : null;
                    }
                    else
                    {
                        object result = methodInfo.Invoke(obj, null);
                        propValue = result != null ? result.ToString() : null;
                    }

                    if (propValue != null)
                    {
                        propList.Add(new PropertyXmlNode(propInfo.Name, propValue, propType));
                    }
                }
                return propList;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message + string.Format(" Failed to invoke getter '{0}' of class '{1}'.", methodName, type.FullName));
            }
        }
    }
}
