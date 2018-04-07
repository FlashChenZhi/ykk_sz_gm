using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Windows.Forms;

namespace PrintCenterCommon
{
    public class CommonHelper
    {
        public static void WriteLog(string log, string file)
        {
            try
            {
                StreamWriter stream = new StreamWriter(file, true, System.Text.Encoding.Default);
                stream.WriteLine(DateTime.Now.ToString() + "  " + log);
                stream.Flush();
                stream.Close();
            }
            catch
            {
                //ignore exceptions
            }
        }

        public static void WriteLog(string log)
        {
            try
            {
                string fileDirectory = Application.StartupPath + @"\logs";
                if(!Directory.Exists(fileDirectory))
                {
                    Directory.CreateDirectory(fileDirectory);
                }

                string filePath = fileDirectory + @"\" + DateTime.Now.ToString("yyyyMMdd") + ".log";

                StreamWriter stream = new StreamWriter(filePath, true, System.Text.Encoding.Default);
                stream.WriteLine(DateTime.Now.ToString() + "  " + log);
                stream.Flush();
                stream.Close();
            }
            catch
            {
                //ignore exceptions
            }
        }

        public static bool ShowConfirmBox(string message, string title)
        {
            if (MessageBox.Show(message, title, MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button2) == DialogResult.No)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        public static void ShowErrorBox(string message)
        {
            MessageBox.Show(message, "´íÎó", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }

        public static void ShowWarningBox(string message)
        {
            MessageBox.Show(message, "¾¯¸æ", MessageBoxButtons.OK, MessageBoxIcon.Warning);
        }

        public static void ShowSuccessBox(string message)
        {
            MessageBox.Show(message, "³É¹¦", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        public static void Run(string filename)
        {
            System.Diagnostics.Process proc = new System.Diagnostics.Process();
            proc.EnableRaisingEvents = false;
            proc.StartInfo.FileName = filename;
            proc.Start();
        }
    }
}
