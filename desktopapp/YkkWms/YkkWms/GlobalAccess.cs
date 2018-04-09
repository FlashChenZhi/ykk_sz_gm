using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using System.Data.OleDb;
using PersistenceLayer;
using System.Windows.Forms;

namespace YkkWms
{
    class GlobalAccess
    {
        public static string UserId { get; set; }
        public static string UserName { get; set; }
        public static bool IsAuth { get; set; }
        public static string StationNo { get; set; }
        public static string Proc { get; set; }
        public static string TermNo { get; set; }
        public static bool UseMockWeighter { get; set; }
        public static decimal PrinterNo { get; set; }
        public static decimal FixedWeight { get; set; }
        public static decimal BagWeight { get; set; }
      
        internal static void ShowDebugInfo(string debugInfo)
        {
#if DEBUG
            if (debugInfo.Length > 0)
            {
                MessageBox.Show(debugInfo, "Debug Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
#endif
        }
    }
}
