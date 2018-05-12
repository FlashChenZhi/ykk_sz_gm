using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class RetrievalOrderDetail
    {
        private string sectionAndLine = string.Empty;

        public string SectionAndLine
        {
            get { return sectionAndLine; }
            set { sectionAndLine = value; }
        }

        private string startDate = string.Empty;

        public string StartDate
        {
            get { return startDate; }
            set { startDate = value; }
        }

        private string itemNo = string.Empty;

        public string ItemNo
        {
            get { return itemNo; }
            set { itemNo = value; }
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

        private string qtyPcs = string.Empty;

        public string QtyPcs
        {
            get { return qtyPcs; }
            set { qtyPcs = value; }
        }

        private string qtyKg = string.Empty;

        public string QtyKg
        {
            get { return qtyKg; }
            set { qtyKg = value; }
        }

        private string bucketNo = string.Empty;

        public string BucketNo
        {
            get { return bucketNo; }
            set { bucketNo = value; }
        }

        private string retrievalNo = string.Empty;

        public string RetrievalNo
        {
            get { return retrievalNo; }
            set { retrievalNo = value; }
        }

        private string retrievalTime = string.Empty;

        public string RetrievalTime
        {
            get { return retrievalTime; }
            set { retrievalTime = value; }
        }
    }
}
