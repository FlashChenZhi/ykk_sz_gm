using System;
using System.Collections.Generic;
using System.Text;

namespace YkkWms
{
    class NoArrivalReportException : Exception
    {
        public NoArrivalReportException(string msg)
            : base(msg)

        { }
    }
}
