using System;
using System.Collections.Generic;
using System.Text;

namespace PrintCenterServer
{
    public interface ITaskLoader
    {
        Task loadTask();
    }
}
