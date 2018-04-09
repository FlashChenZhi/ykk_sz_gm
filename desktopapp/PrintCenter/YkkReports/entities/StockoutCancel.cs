using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class StockoutCancel
    {
        private string retrievalNo;

        public string RetrievalNo
        {
            get { return retrievalNo; }
            set { retrievalNo = value; }
        }
        private string itemCode;

        public string ItemCode
        {
            get { return itemCode; }
            set { itemCode = value; }
        }
        private string retrievalCount;

        public string RetrievalCount
        {
            get { return retrievalCount; }
            set { retrievalCount = value; }
        }
        private string cancelCount;

        public string CancelCount
        {
            get { return cancelCount; }
            set { cancelCount = value; }
        }
        private string startDate;

        public string StartDate
        {
            get { return startDate; }
            set { startDate = value; }
        }
  
        private string completeDate;

        public string CompleteDate
        {
            get { return completeDate; }
            set { completeDate = value; }
        }
  
        private string customerCode;

        public string CustomerCode
        {
            get { return customerCode; }
            set { customerCode = value; }
        }
    }
}
