using System;
using System.Collections.Generic;
using System.Text;
using PrintCenterProxy;
using System.Drawing.Printing;
using PrintCenterCommon;

namespace PrintCenterService
{
    public class PrinterHelper : MarshalByRefObject, IPrinterHelper
    {        
        public List<string> getPrinterNameList()
        {
            List<string> printerNameList = new List<string>();
            //printerNameList.Add(string.Empty);
            Printer.PRINTER_INFO_2[] Info2 = Printer.EnumPrintersByFlag(Printer.PrinterEnumFlags.PRINTER_ENUM_LOCAL);
            for (int i = 0; i < Info2.Length; i++)
            {
                printerNameList.Add(Info2[i].pPrinterName);
            }
            return printerNameList;
        }

        public List<string> getPaperNameList(string printerName)
        {
            PrintDocument printDocument = new PrintDocument();
            printDocument.PrinterSettings.PrinterName = printerName;
            List<string> paperNameList = new List<string>();
            foreach (PaperSize paperSize in printDocument.PrinterSettings.PaperSizes)
            {
                paperNameList.Add(paperSize.PaperName);
            }
            printDocument.Dispose();
            return paperNameList;
        }

        public string getDeaultPrinterName()
        {
            return Printer.GetDeaultPrinterName();
        }

        public DecimalSize getPaperSize(string printerName, string paperName)
        {
            PrintDocument printDocument = new PrintDocument();

            printDocument.PrinterSettings.PrinterName = printerName;
            DecimalSize size = new DecimalSize();
            foreach (PaperSize paperSize in printDocument.PrinterSettings.PaperSizes)
            {
                if (paperSize.PaperName == paperName)
                {
                    size.Height = Printer.FromInchToCM(System.Convert.ToDecimal(paperSize.Height.ToString()));
                    size.Width = Printer.FromInchToCM(System.Convert.ToDecimal(paperSize.Width.ToString()));
                }
            }
            printDocument.Dispose();
            return size;
        }
    }
}
