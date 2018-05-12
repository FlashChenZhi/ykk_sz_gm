using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class WorkViewHead
    {
        private string workType;

        public string WorkType
        {
            get { return workType; }
            set { workType = value; }
        }
        private string stationNo;

        public string StationNo
        {
            get { return stationNo; }
            set { stationNo = value; }
        }
        private string stationType;

        public string StationType
        {
            get { return stationType; }
            set { stationType = value; }
        }
    }
}
