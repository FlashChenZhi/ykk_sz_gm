using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class MessageHead
    {
        private string messageType;

        public string MessageType
        {
            get { return messageType; }
            set { messageType = value; }
        }
        private string timeRange;

        public string TimeRange
        {
            get { return timeRange; }
            set { timeRange = value; }
        }
    }
}
