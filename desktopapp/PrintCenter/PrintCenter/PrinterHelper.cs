using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing.Printing;
using System.Drawing;
using PrintCenterCommon;

namespace PrintCenter
{
    class PrinterHelper
    {
        internal class DecimalSize
        {
            decimal? height = null;
            decimal? width = null;

            public decimal? Width
            {
                get { return width; }
                set { width = value; }
            }

            public decimal? Height
            {
                get { return height; }
                set { height = value; }
            }        
        }

        internal static List<string> getPrinterNameList()
        {            
            List<string> printerNameList = new List<string>();
            //printerNameList.Add(string.Empty);

            //yutao
            Printer.PRINTER_INFO_2[] Info2 = Printer.EnumPrintersByFlag(Printer.PrinterEnumFlags.PRINTER_ENUM_LOCAL);
            for (int i = 0; i < Info2.Length; i++)
            {
                printerNameList.Add(Info2[i].pPrinterName);
            }
            //yutao
            return printerNameList;
        }

        internal static List<string> getPaperNameList(string printerName)
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

        internal static string getDeaultPrinterName()
        {
            return Printer.GetDeaultPrinterName();
        }

        internal static DecimalSize getPaperSize(string printerName, string paperName)
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
