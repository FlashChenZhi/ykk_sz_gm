using System;
using System.Collections.Generic;
using System.Text;

namespace PrintCenterCommon
{
    public class GlobalVariable
    {
        public static string getPrintedDirectory(string configFilePath)
        {
            return PrintCenterConfiguration.getInstance(configFilePath).DirectoryToMonitor + @"\printed";
        }

        public static string getPrintedDirectory()
        {
            return PrintCenterConfiguration.getInstance().DirectoryToMonitor + @"\printed";
        }

        public static string getDeletedDirectory(string configFilePath)
        {
            return PrintCenterConfiguration.getInstance(configFilePath).DirectoryToMonitor + @"\deleted";
        }

        public static string getDeletedDirectory()
        {
            return PrintCenterConfiguration.getInstance().DirectoryToMonitor + @"\deleted";
        }

        public static string getPrintingDirectory(string configFilePath)
        {
            return PrintCenterConfiguration.getInstance(configFilePath).DirectoryToMonitor + @"\printing";
        }

        public static string getPrintingDirectory()
        {
            return PrintCenterConfiguration.getInstance().DirectoryToMonitor + @"\printing";
        }
    }
}
