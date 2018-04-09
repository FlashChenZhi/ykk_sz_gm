using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class LocationStorageHead
    {
        private string depo;

        public string Depo
        {
            get { return depo; }
            set { depo = value; }
        }
        private string locationStatus;

        public string LocationStatus
        {
            get { return locationStatus; }
            set { locationStatus = value; }
        }
        private string locationNo;

        public string LocationNo
        {
            get { return locationNo; }
            set { locationNo = value; }
        }
        private string weightReportFlag;

        public string WeightReportFlag
        {
            get { return weightReportFlag; }
            set { weightReportFlag = value; }
        }
    }
}
