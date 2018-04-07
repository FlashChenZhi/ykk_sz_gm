using System;
using System.Collections.Generic;
using System.Text;
using PrintCenterCommon;

namespace PrintCenter
{
    class UIHelper
    {
        internal static void loadPrinterCombo(System.Windows.Forms.ComboBox cmbPrinter)
        {
            System.Collections.ArrayList allPrinters = Printer.GetPrinterList();
            for (int i = 0; i < allPrinters.Count; i++)
                cmbPrinter.Items.Add(allPrinters[i].ToString());
            allPrinters.Clear();
            allPrinters = null;
        }
    }
}
