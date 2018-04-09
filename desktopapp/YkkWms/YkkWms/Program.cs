using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Diagnostics;
using BusinessEntity;
using System.Net;

namespace YkkWms
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
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
               
//                if (AppConfig.Get("StationNo") == "2101")
//                {
//                    TERMINALEntity term = null;
//                    IPAddress[] ipList = getIPAddress();
//
//                    for (int i = 0; i < ipList.Length; ++i)
//                    {
//                        term = DbAccess.GetTermByIP(ipList[i].ToString());
//                        if (term != null) break;
//                    }
//
//                    if (term == null)
//                    {
//                        MessageBox.Show("无法找到对应的终端信息");
//                        return;
//
//                    }
//                    else
//                    {
//                        GlobalAccess.StationNo = term.STNO;
//                        GlobalAccess.Proc = term.PROC;
//                        GlobalAccess.TermNo = term.TERMINALNUMBER;
//                        GlobalAccess.PrinterNo = term.PRINTER_NO;
//                        GlobalAccess.FixedWeight = term.FIXED_WEIGHT;
//
//                    }
//
//                    FNSYSTEMEntity systemInfo = DbAccess.GetSystemInfo();
//                    GlobalAccess.BagWeight = systemInfo.BAG_WEIGHT;
// 
//                    GlobalAccess.StationNo = "2101";
//                    GlobalAccess.UserId = "2101";
//                    GlobalAccess.UserName = "2101";
//                    Application.Run(new Stockin3());
//                }
//                else
                {                    
                    Application.Run(new Login());
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return;
            }
        }

        private static IPAddress[] getIPAddress()
        {
            return Dns.GetHostEntry(Dns.GetHostName()).AddressList;
        }

    }
}