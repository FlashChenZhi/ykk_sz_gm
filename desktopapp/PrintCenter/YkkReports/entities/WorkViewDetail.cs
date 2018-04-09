using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class WorkViewDetail
    {
        private string colorCode;

        public string ColorCode
        {
            get { return colorCode; }
            set { colorCode = value; }
        }
        private string itemName;

        public string ItemName
        {
            get { return itemName; }
            set { itemName = value; }
        }
        private string workType;

        public string WorkType
        {
            get { return workType; }
            set { workType = value; }
        }
        private string workStatus;

        public string WorkStatus
        {
            get { return workStatus; }
            set { workStatus = value; }
        }
        private string mcKey;

        public string McKey
        {
            get { return mcKey; }
            set { mcKey = value; }
        }
        private string stationNo;

        public string StationNo
        {
            get { return stationNo; }
            set { stationNo = value; }
        }
        private string path;

        public string Path
        {
            get { return path; }
            set { path = value; }
        }
        private string locationNo;

        public string LocationNo
        {
            get { return locationNo; }
            set { locationNo = value; }
        }
        private string bucket;

        public string Bucket
        {
            get { return bucket; }
            set { bucket = value; }
        }
        private string itemCode;

        public string ItemCode
        {
            get { return itemCode; }
            set { itemCode = value; }
        }
        private string qty;

        public string Qty
        {
            get { return qty; }
            set { qty = value; }
        }
    }
}
