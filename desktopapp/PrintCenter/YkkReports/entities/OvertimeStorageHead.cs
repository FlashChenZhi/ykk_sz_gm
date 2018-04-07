using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class OvertimeStorageHead
    {
        private string warehouseType;

        public string WarehouseType
        {
            get { return warehouseType; }
            set { warehouseType = value; }
        }
        private string overtimeDate;

        public string OvertimeDate
        {
            get { return overtimeDate; }
            set { overtimeDate = value; }
        }
        private string overtimeKey;

        public string OvertimeKey
        {
            get { return overtimeKey; }
            set { overtimeKey = value; }
        }
        private string orderKey;

        public string OrderKey
        {
            get { return orderKey; }
            set { orderKey = value; }
        }
    }
}
