using System;
using System.Collections.Generic;
using System.Text;
using System.IO;

namespace YkkWms
{
    public class LogWriter
    {
        public static void WriteLog(string log,string filename)
        {
            try
            {
                StreamWriter stream = new StreamWriter(filename + DateTime.Now.ToString("yyyyMMdd"),true, System.Text.Encoding.Default);
                stream.WriteLine(DateTime.Now.ToString() + "  " + log);
                stream.Flush();
                stream.Close();
            }
            catch
            {
            }
        }
    }
}
