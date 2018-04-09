using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class StorageHead 
    {
        private string itemCode;

        public string ItemCode
        {
            get { return itemCode; }
            set { itemCode = value; }
        }
        private string colorCode;

        public string ColorCode
        {
            get { return colorCode; }
            set { colorCode = value; }
        }
    }
}
