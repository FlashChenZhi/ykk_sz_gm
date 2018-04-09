using System;
using PersistenceLayer;

namespace PrintCenter
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
                Setting.Instance().DatabaseMapFile = "DatabaseMap.xml";
            }
            catch (Exception ex)
            {
                throw new Exception("初始化SPL失败:" + ex.Message);
            }
        }
    }
}
