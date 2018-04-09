using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;

namespace PrintCenterProxy
{
    public interface ITaskPersister
    {
        string persistTask(string dataSourceListXml);
    }
}
