using System;
using System.Collections.Generic;
using System.Text;

namespace PrintCenterCommon
{
    [Serializable]
    public class TaskManagerException1 : Exception
    {
        public TaskManagerException1(string message) : base(message)
        {            
        }
    }
}
