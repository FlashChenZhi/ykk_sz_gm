using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class StorageDetail
    {
        private string itemCode;

        public string ItemCode
        {
            get { return itemCode; }
            set { itemCode = value; }
        }
 
        private string colorCode;

        public string ColorCode
        {
            get { return colorCode; }
            set { colorCode = value; }
        }

        private string autoStorageCount;

        public string AutoStorageCount
        {
            get { return autoStorageCount; }
            set { autoStorageCount = value; }
        }
        private string flatStorageCount;

        public string FlatStorageCount
        {
            get { return flatStorageCount; }
            set { flatStorageCount = value; }
        }
        private string totalStorageCount;

        public string TotalStorageCount
        {
            get { return totalStorageCount; }
            set { totalStorageCount = value; }
        }
        private string notStockinStorageCount;

        public string NotStockinStorageCount
        {
            get { return notStockinStorageCount; }
            set { notStockinStorageCount = value; }
        }

        private string itemName;

        public string ItemName
        {
            get { return itemName; }
            set { itemName = value; }
        }
    }
}
