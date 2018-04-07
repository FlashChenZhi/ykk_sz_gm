using System;
using System.Collections.Generic;
using System.Text;

namespace PrintCenterProxy
{
    public interface IPrinterHelper
    {
        

        List<string> getPrinterNameList();
        

        List<string> getPaperNameList(string printerName);
        

        string getDeaultPrinterName();


        DecimalSize getPaperSize(string printerName, string paperName);
        
    }
}
