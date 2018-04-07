using System;
using System.Collections.Generic;
using System.Text;
using PrintCenterCommon;

namespace PrintCenterServer
{
    public class Task
    {
        private List<DataSource> dataSourceList = new List<DataSource>();
        private string reportName = string.Empty;
        private ReportSetting reportSetting = null;      
        private string currentFilePath = string.Empty;

        public ReportSetting ReportSetting
        {
            get { return reportSetting; }
            set { reportSetting = value; }
        }

        public string CurrentFilePath
        {
            get { return currentFilePath; }
            set { currentFilePath = value; }
        }

        public List<DataSource> DataSourceList
        {
            get { return dataSourceList; }
        }

        internal void addDataSource(DataSource dataSource)
        {
            this.dataSourceList.Add(dataSource);
        }

        public string ReportName
        {
            get { return reportName; }
            set { reportName = value; }
        }
    }
}
