using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using System.IO;

namespace PrintCenterCommon
{
    static public class XmlHelper
    {
        static public XmlDocument loadDocument(string taskFilePath)
        {
            XmlDocument document = new XmlDocument();
            document.Load(taskFilePath);
            return document;
        }
    }
}
