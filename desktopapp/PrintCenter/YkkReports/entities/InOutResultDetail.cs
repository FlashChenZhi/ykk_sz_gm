using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class InOutResultDetail
    {
        private string resultDate;

        public string ResultDate
        {
            get { return resultDate; }
            set { resultDate = value; }
        }
        private string itemCode;

        public string ItemCode
        {
            get { return itemCode; }
            set { itemCode = value; }
        }

        private string itemName;

        public string ItemName
        {
            get { return itemName; }
            set { itemName = value; }
        }
        private string colorCode;

        public string ColorCode
        {
            get { return colorCode; }
            set { colorCode = value; }
        }

        private string ticketNoRetrievalNo;

        public string TicketNoRetrievalNo
        {
            get { return ticketNoRetrievalNo; }
            set { ticketNoRetrievalNo = value; }
        }

        private string locationNoBucketNo;

        public string LocationNoBucketNo
        {
            get { return locationNoBucketNo; }
            set { locationNoBucketNo = value; }
        }
 
        private string qty;

        public string Qty
        {
            get { return qty; }
            set { qty = value; }
        }
        private string workTypeStationNo;

        public string WorkTypeStationNo
        {
            get { return workTypeStationNo; }
            set { workTypeStationNo = value; }
        }

        private string userIdUserName;

        public string UserIdUserName
        {
            get { return userIdUserName; }
            set { userIdUserName = value; }
        }
    }
}
