using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class ErrorMessageDetail
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
        private string erroMessage;

        public string ErroMessage
        {
            get { return erroMessage; }
            set { erroMessage = value; }
        }
    }
}
