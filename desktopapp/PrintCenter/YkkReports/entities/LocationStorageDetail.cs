using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class LocationStorageDetail
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

        private string ticketNo;

        public string TicketNo
        {
            get { return ticketNo; }
            set { ticketNo = value; }
        }
        private string bucket;

        public string Bucket
        {
            get { return bucket; }
            set { bucket = value; }
        }
        private string locationNo;

        public string LocationNo
        {
            get { return locationNo; }
            set { locationNo = value; }
        }
        private string locationStatus;

        public string LocationStatus
        {
            get { return locationStatus; }
            set { locationStatus = value; }
        }
        private string storageCount;

        public string StorageCount
        {
            get { return storageCount; }
            set { storageCount = value; }
        }
        private string stockinDate;

        public string StockinDate
        {
            get { return stockinDate; }
            set { stockinDate = value; }
        }

        private string itemName;

        public string ItemName
        {
            get { return itemName; }
            set { itemName = value; }
        }

        private string weightReportFlag;

        public string WeightReportFlag
        {
            get { return weightReportFlag; }
            set { weightReportFlag = value; }
        }
    }
}
