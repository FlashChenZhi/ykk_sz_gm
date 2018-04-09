using System;
using System.Collections.Generic;
using System.Text;
using System.Configuration;

namespace WavesStatusMonitor
{
    public static class AppConfig
    {
        private static Configuration config = ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
        public static void Set(string name, string value)
        {
            config.AppSettings.Settings[name].Value = value;
            config.Save();
        }

        public static string Get(string name)
        {
            return config.AppSettings.Settings[name].Value;
        }

    }
}
