using System;
using System.Collections.Generic;
using System.Text;
using PersistenceLayer;
using BusinessEntity;
using System.Windows.Forms;

namespace WavesStatusMonitor
{
    class AppInit
    {
        public static void InitAll()
        {
            InitDb();            
        }

        public static void InitDb()
        {
            try
            {
                string path = Application.StartupPath + @"\configuration\DatabaseMap.xml";
                Setting.Instance().DatabaseMapFile = path;
            }
            catch (Exception ex)
            {
                throw new Exception("初始化SPL失败:" + ex.Message);
            }
        }
    }
}
