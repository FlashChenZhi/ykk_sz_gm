using System;
using System.Collections.Generic;
using System.Text;

namespace PrintCenterProxy
{
    public interface IPrinterHelperFactory
    {
        IPrinterHelper getInstance();
    }
}
