using System;
using System.Collections.Generic;
using System.Text;

namespace PrintCenterProxy
{
    public interface ITaskPersisterFactory
    {
        ITaskPersister getInstance();
    }
}
