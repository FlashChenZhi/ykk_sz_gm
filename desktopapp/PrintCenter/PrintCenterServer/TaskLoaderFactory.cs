using System;
using System.Collections.Generic;
using System.Text;

namespace PrintCenterServer
{
    public class TaskLoaderFactory
    {
        private static ITaskLoader manager = null;

        private TaskLoaderFactory() { }

        private static ITaskLoader loadTaskManager()
        {
            return new XmlTaskLoader();
        }

        public static ITaskLoader getInstance()
        {
            if (manager == null)
            {
                manager = loadTaskManager();
            }
            return manager;
        }
    }
}
