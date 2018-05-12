using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class LabelCustomer
    {
        private string ticketNo = string.Empty;

        private string bucketNo = string.Empty;

        private string itemCode = string.Empty;

        private string itemName = string.Empty;

        private string colorCode = string.Empty;

        private string count = string.Empty;

        private string weight = string.Empty;

        private string unit = string.Empty;

        private string customerCode = string.Empty;

        private string customerName = string.Empty;

        private string userName = string.Empty;

        public string UserName
        {
            get { return "操作员:" + userName; }
            set { userName = value; }
        }

        public string CustomerName
        {
            get { return customerName; }
            set { customerName = value; }
        }

        public string CustomerCode
        {
            get { return customerCode; }
            set { customerCode = value; }
        }

        public string Unit
        {
            get { return "(原单位重量" + unit + " g)"; }
            set { unit = value; }
        }

        public string Weight
        {
            get { return weight + " Kg"; }
            set { weight = value; }
        }

        public string Count
        {
            get { return count + " Pcs"; }
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

        public string TicketBarcode
        {
            get
            { return "*" + this.ticketNo + "*" + "\r\n" + "*" + this.ticketNo + "*" + "\r\n" + "*" + this.ticketNo + "*"; }
        }
    }
}
