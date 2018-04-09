using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using System.Xml.Serialization;

namespace PrintCenterCommon
{
    public class PropertyXmlNode
    {
        public PropertyXmlNode()
        {
        }

        public PropertyXmlNode(string name, string value, string type)
        {
            this.name = name;
            this.value = value;
            this.type = type;
        }

        string type = string.Empty;
        string name = string.Empty;
        string value = string.Empty;

        [XmlAttribute("type")]
        public string Type
        {
            get { return type; }
            set { type = value; }
        }

        [XmlAttribute("name")]
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        [XmlAttribute("value")]
        public string Value
        {
            get { return value; }
            set { this.value = value; }
        }
    }
}
