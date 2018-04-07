using System;
using System.Collections.Generic;
using System.Text;
using log4net;
using System.IO;
using log4net.Config;
using System.Windows.Forms;

namespace WavesStatusMonitor
{
    static class AppLogger
    {
        private static ILog developmentLogger = null;
        private static ILog applicationLogger = null;

        public static void initLogger()
        {
            try
            {
                string path = Application.StartupPath + @"\configuration\log4net.xml";
                XmlConfigurator.Configure(new FileInfo(path));

                applicationLogger = LogManager.Exists("application_logger");
                developmentLogger = LogManager.Exists("development_logger");

                if (applicationLogger == null)
                {
                    string message = "logger for application info was not found. if you want to log application info, please config a logger naming 'application_logger'.";
                    MessageBox.Show(message, "applogger warning", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                }

                if (developmentLogger == null)
                {
                    string message = "logger for development info was not found. if you want to log development info, please config a logger naming 'development_logger'.";
                    logWarnMessage(message);                    
                }
            }
            catch (Exception ex)
            {
                logErrorMessage("error occurs when initiating logger." + ex.Message);
            }
        }

        public static void logDebugMessage(String message)
        {
            if (developmentLogger != null)
            {
                developmentLogger.Debug(message);
            }
        }

        public static void logErrorMessage(String message)
        {
            if (applicationLogger != null)
            {
                applicationLogger.Error(message);
            }
        }

        public static void logInfoMessage(String message)
        {
            if (applicationLogger != null)
            {
                applicationLogger.Info(message);
            }
        }

        public static void logWarnMessage(String message)
        {
            if (applicationLogger != null)
            {
                applicationLogger.Warn(message);
            }
        }
    }
}
