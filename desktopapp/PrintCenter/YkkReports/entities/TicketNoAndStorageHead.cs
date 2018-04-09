using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class TicketNoAndStorageHead
    {
        private string depo;

        public string Depo
        {
            get { return depo; }
            set { depo = value; }
        }
        private string searchClass;

        public string SearchClass
        {
            get { return searchClass; }
            set { searchClass = value; }
        }
        private string searchRange;

        public string SearchRange
        {
            get { return searchRange; }
            set { searchRange = value; }
        }

        private string itemCodeColorCode;

        public string ItemCodeColorCode
        {
            get { return itemCodeColorCode; }
            set { itemCodeColorCode = value; }
        }

        private string bucketNo;

        public string BucketNo
        {
            get { return bucketNo; }
            set { bucketNo = value; }
        }
    }
}
