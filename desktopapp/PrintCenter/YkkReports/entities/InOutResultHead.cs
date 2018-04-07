using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class InOutResultHead
    {
        private string workType;

        public string WorkType
        {
            get { return workType; }
            set { workType = value; }
        }
        private string timeRange;

        public string TimeRange
        {
            get { return timeRange; }
            set { timeRange = value; }
        }
        private string itemRange;

        public string ItemRange
        {
            get { return itemRange; }
            set { itemRange = value; }
        }
        private string userId;

        public string UserId
        {
            get { return userId; }
            set { userId = value; }
        }
    }
}
