using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class MessageDetail
    {
        private string time;

        public string Time
        {
            get { return time; }
            set { time = value; }
        }
        private string messageType;

        public string MessageType
        {
            get { return messageType; }
            set { messageType = value; }
        }
        private string message;

        public string Message
        {
            get { return message; }
            set { message = value; }
        }
    }
}
