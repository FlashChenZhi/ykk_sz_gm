using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class LabelSubdivided
    {
        private string ticketNo = string.Empty;

        private string bucketNo = string.Empty;

        private string itemCode = string.Empty;

        private string itemName = string.Empty;

        private string colorCode = string.Empty;

        private string count = string.Empty;

        private string weight = string.Empty;

        private string unit = string.Empty;

        private string userName = string.Empty;

        private string section = string.Empty;

        private string line = string.Empty;

        private string retrievalNo = string.Empty;

        private string necessaryQty = string.Empty;

        private string prNo = string.Empty;

        private string optionFlag = string.Empty;

        private string startDate = string.Empty;

        private string page = string.Empty;

        public string Page
        {
            get { return page; }
            set { page = value; }
        }

        public string StartDate
        {
            get { return startDate; }
            set { startDate = value; }
        }

        public string OptionFlag
        {
            get { return optionFlag; }
            set { optionFlag = value; }
        }

        public string PrNo
        {
            get { return prNo; }
            set { prNo = value; }
        }

        public string NecessaryQty
        {
            //get { return necessaryQty + " Pcs"; }
            get { return necessaryQty; }
            set { necessaryQty = value; }
        }

        public string RetrievalNo
        {
            get { return retrievalNo; }
            set { retrievalNo = value; }
        }

        public string Line
        {
            get { return line; }
            set { line = value; }
        }

        public string Section
        {
            get { return section; }
            set { section = value; }
        }

        public string UserName
        {
            get { return "操作员:" + userName; }
            set { userName = value; }
        }

        //public string Unit
        //{
        //    get { return "(原单位重量" + unit + " g)"; }
        //    set { unit = value; }
        //}

        public string Unit
        {
            get { return unit + " g"; }
            set { unit = value; }
        }

        public string Weight
        {
            get { return weight + " Kg"; }
            set { weight = value; }
        }

        public string Count
        {
            //get { return count + " Pcs"; }
            get { return count; }
            set { count = value; }
        }

        public string ColorCode
        {
            get { return colorCode + "#"; }
            set { colorCode = value; }
        }

        public string ItemName
        {
            get { return itemName; }
            set { itemName = value; }
        }

        public string ItemCode
        {
            get { return itemCode; }
            set { itemCode = value; }
        }

        public string BucketNo
        {
            get { return bucketNo; }
            set { bucketNo = value; }
        }

        public string TicketNo
        {
            get { return ticketNo.ToUpper(); }
            set { ticketNo = value.ToUpper(); }
        }

        public string UnitBarcode
        {
            get
            { return "*" + this.unit + "*" + "\r\n" + "*" + this.unit + "*"; }
        }

        public string RetrievalNoBarcode
        {
            get
            { return "*" + this.retrievalNo + "*" + "\r\n" + "*" + this.retrievalNo + "*"; }
        }

        public string TicketBarcode
        {
            get
            { return "*" + this.ticketNo + "*" + "\r\n" + "*" + this.ticketNo + "*" + "\r\n" + "*" + this.ticketNo + "*"; }
        }
    }
}
