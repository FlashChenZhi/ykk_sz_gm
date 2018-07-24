using System;
using System.Windows.Forms;
using System.Diagnostics;
using BusinessEntity;
using System.Collections.Generic;
using YkkReports.entities;

namespace PrintCenter
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            //string itemCode = "item";
            //string colorCode = "aaa";
            //string trimmedColorCode = colorCode.Trim();
            //string formattedColorCode = trimmedColorCode;
            //if (string.IsNullOrEmpty(formattedColorCode))
            //{
            //    formattedColorCode = string.Empty.PadLeft(7, ' ');
            //}
            //else if (formattedColorCode.Length == 2)
            //{
            //    formattedColorCode = string.Empty.PadLeft(3, ' ') + formattedColorCode + string.Empty.PadLeft(2, ' ');
            //}
            //else if (formattedColorCode.Length == 3)
            //{
            //    formattedColorCode = string.Empty.PadLeft(2, ' ') + formattedColorCode + string.Empty.PadLeft(2, ' ');
            //}
            //else if (formattedColorCode.Length == 4)
            //{
            //    formattedColorCode = string.Empty.PadLeft(1, ' ') + formattedColorCode + string.Empty.PadLeft(2, ' ');
            //}
            //else if (formattedColorCode.Length == 5)
            //{
            //    formattedColorCode = formattedColorCode + string.Empty.PadLeft(2, ' ');
            //}
            //string barcode = "*" + itemCode + formattedColorCode + "*";
            //string bbb = barcode;


            if (Process.GetProcessesByName(Process.GetCurrentProcess().ProcessName).Length > 1)
            {
                MessageBox.Show("Program is already started.", "info", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }  

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            try
            {
                AppInit.InitAll();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return;
            }
            Application.Run(new MainFrm());
        }
    }
}