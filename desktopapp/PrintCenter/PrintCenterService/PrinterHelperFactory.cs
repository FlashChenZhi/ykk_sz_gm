using System;
using System.Collections.Generic;
using System.Text;
using PrintCenterProxy;

namespace PrintCenterService
{
    public class PrinterHelperFactory : MarshalByRefObject, IPrinterHelperFactory
    {
        private static IPrinterHelper helper = null;

        public IPrinterHelper getInstance()
        {
            if (helper == null)
            {
                helper = new PrinterHelper();
            }
            return helper;
        }
    }
}
