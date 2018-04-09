using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;

namespace PrintCenterCommon
{
    public class RecordXmlNode
    {
        private List<PropertyXmlNode> propertyList = new List<PropertyXmlNode>();

        [XmlArrayAttribute("property_list")]
        [XmlArrayItemAttribute("property")]
        public List<PropertyXmlNode> PropertyList
        {
            get { return propertyList; }
            set { propertyList = value; }
        }
    }
}
