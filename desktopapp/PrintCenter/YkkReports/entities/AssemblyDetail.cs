using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class AssemblyDetail
    {
        private string bucketNo = string.Empty;

        public string BucketNo
        {
            get { return bucketNo; }
            set { bucketNo = value; }
        }

        private string itemCode = string.Empty;

        public string ItemCode
        {
            get { return itemCode; }
            set { itemCode = value; }
        }

        private string itemName = string.Empty;

        public string ItemName
        {
            get { return itemName; }
            set { itemName = value; }
        }

        private string colorCode = string.Empty;

        public string ColorCode
        {
            get { return colorCode; }
            set { colorCode = value; }
        }

        public string ItemCodeName
        {
            get { return itemCode + "\r\n" + itemName; }
            set { }
        }

        private string qty = string.Empty;

        public string Qty
        {
            get { return qty; }
            set { qty = value; }
        }

        private string weight = string.Empty;

        public string Weight
        {
            get { return weight; }
            set { weight = value; }
        }

        public string QtyWeight
        {
            get { return qty + "\r\n" + weight; }
            set { }
        }
    }
}
