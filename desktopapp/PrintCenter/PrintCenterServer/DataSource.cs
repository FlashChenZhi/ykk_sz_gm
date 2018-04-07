using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace PrintCenterServer
{
    public class DataSource
    {
        private string dataSourceName;
        private BindingSource dataSourceValue;

        public BindingSource DataSourceValue
        {
            get { return dataSourceValue; }
        }

        public string DataSourceName
        {
            get { return dataSourceName; }
        }

        public DataSource(string dataSourceName, object dataSourceValue)
        {
            this.dataSourceName = dataSourceName;
            this.dataSourceValue = new BindingSource(dataSourceValue,"");
        }
    }
}
