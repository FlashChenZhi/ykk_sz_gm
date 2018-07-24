using System;
using System.Collections.Generic;
using System.Windows.Forms;
using WavesStatusMonitor;
using System.Diagnostics;

namespace WavesStatusMonitor
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>processName
        [STAThread]
        static void Main()
        {
            if (Process.GetProcessesByName(Process.GetCurrentProcess().ProcessName).Length > 1)
            {
                MessageBox.Show("Program is already started.", "info", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }                        

            AppLogger.initLogger();
            try
            {                
                AppInit.InitAll();
            }
            catch (Exception ex)
            {
                AppLogger.logErrorMessage(ex.Message);
                MessageBox.Show(ex.Message);                
                return;
            }            

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new frmMain());
        }
    }
}
