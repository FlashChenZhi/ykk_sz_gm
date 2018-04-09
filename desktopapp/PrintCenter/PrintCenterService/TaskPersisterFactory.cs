using System;
using System.Collections.Generic;
using System.Text;
using PrintCenterProxy;

namespace PrintCenterService
{
    public class TaskPersisterFactory : MarshalByRefObject, ITaskPersisterFactory
    {
        private static ITaskPersister persister = null;

        private ITaskPersister loadTaskManager()
        {
            return new XmlTaskPersister();
        }

        public ITaskPersister getInstance()
        {
            if (persister == null)
            {
                persister = loadTaskManager();
            }
            return persister;
        }
    }
}
