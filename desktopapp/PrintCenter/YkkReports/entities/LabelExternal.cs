using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class LabelExternal
    {
        private string ticketNo = string.Empty;

        private string bucketNo = string.Empty;

        private string itemCode = string.Empty;

        private string itemName = string.Empty;

        private string colorCode = string.Empty;

        private string count = string.Empty;

        private string weight = string.Empty;

        private string unit = string.Empty;

        private string customerCode = string.Empty;

        private string customerName = string.Empty;

        private List<string> opnoList = new List<string>();

        private string userName = string.Empty;

        public string UserName
        {
            get { return "操作员:" + userName; }
            set { userName = value; }
        }

        private string getOPNO(int rowNum)
        {
            if (opnoList.Count < rowNum)
            {
                return string.Empty;
            }
            else
            {
                return opnoList[rowNum - 1];
            }
        }

        public string OPNO1
        {
            get { return getOPNO(1); }
        }

        public string OPNO2
        {
            get { return getOPNO(2); }
        }

        public string OPNO3
        {
            get { return getOPNO(3); }
        }

        public string OPNO4
        {
            get { return getOPNO(4); }
        }

        public string OPNO5
        {
            get { return getOPNO(5); }
        }

        public string OPNO6
        {
            get { return getOPNO(6); }
        }

        public string OPNO7
        {
            get { return getOPNO(7); }
        }

        //public string OPNO8
        //{
        //    get { return getOPNO(8); }
        //}

        //public string OPNO9
        //{
        //    get { return getOPNO(9); }
        //}

        //public string OPNO10
        //{
        //    get { return getOPNO(10); }
        //}

        //public string OPNO11
        //{
        //    get { return getOPNO(11); }
        //}

        //public string OPNO12
        //{
        //    get { return getOPNO(12); }
        //}

        //public string OPNO13
        //{
        //    get { return getOPNO(13); }
        //}

        //public string OPNO14
        //{
        //    get { return getOPNO(14); }
        //}

        private List<string> countList = new List<string>();

        private string getCount(int rowNum)
        {
            if (countList.Count < rowNum)
            {
                return string.Empty;
            }
            else
            {
                return countList[rowNum - 1];
            }
        }

        public string Count1
        {
            get { return getCount(1); }
        }

        public string Count2
        {
            get { return getCount(2); }
        }

        public string Count3
        {
            get { return getCount(3); }
        }

        public string Count4
        {
            get { return getCount(4); }
        }

        public string Count5
        {
            get { return getCount(5); }
        }

        public string Count6
        {
            get { return getCount(6); }
        }

        public string Count7
        {
            get { return getCount(7); }
        }

        //public string Count8
        //{
        //    get { return getCount(8); }
        //}

        //public string Count9
        //{
        //    get { return getCount(9); }
        //}

        //public string Count10
        //{
        //    get { return getCount(10); }
        //}

        //public string Count11
        //{
        //    get { return getCount(11); }
        //}

        //public string Count12
        //{
        //    get { return getCount(12); }
        //}

        //public string Count13
        //{
        //    get { return getCount(13); }
        //}

        //public string Count14
        //{
        //    get { return getCount(14); }
        //}

        private List<string> weightList = new List<string>();

        private string getWeight(int rowNum)
        {
            if (weightList.Count < rowNum)
            {
                return string.Empty;
            }
            else
            {
                return weightList[rowNum - 1];
            }
        }

        public string Weight1
        {
            get { return getWeight(1); }
        }

        public string Weight2
        {
            get { return getWeight(2); }
        }

        public string Weight3
        {
            get { return getWeight(3); }
        }

        public string Weight4
        {
            get { return getWeight(4); }
        }

        public string Weight5
        {
            get { return getWeight(5); }
        }

        public string Weight6
        {
            get { return getWeight(6); }
        }

        public string Weight7
        {
            get { return getWeight(7); }
        }

        //public string Weight8
        //{
        //    get { return getWeight(8); }
        //}

        //public string Weight9
        //{
        //    get { return getWeight(9); }
        //}

        //public string Weight10
        //{
        //    get { return getWeight(10); }
        //}

        //public string Weight11
        //{
        //    get { return getWeight(11); }
        //}

        //public string Weight12
        //{
        //    get { return getWeight(12); }
        //}

        //public string Weight13
        //{
        //    get { return getWeight(13); }
        //}

        //public string Weight14
        //{
        //    get { return getWeight(14); }
        //}

        private List<string> retrievalNoList = new List<string>();

        private string getRetrievalNo(int rowNum)
        {
            if (retrievalNoList.Count < rowNum)
            {
                return string.Empty;
            }
            else
            {
                return retrievalNoList[rowNum - 1];
            }
        }

        public string RetrievalNo1
        {
            get { return getRetrievalNo(1); }
        }

        public string RetrievalNo2
        {
            get { return getRetrievalNo(2); }
        }

        public string RetrievalNo3
        {
            get { return getRetrievalNo(3); }
        }

        public string RetrievalNo4
        {
            get { return getRetrievalNo(4); }
        }

        public string RetrievalNo5
        {
            get { return getRetrievalNo(5); }
        }

        public string RetrievalNo6
        {
            get { return getRetrievalNo(6); }
        }

        public string RetrievalNo7
        {
            get { return getRetrievalNo(7); }
        }

        private List<string> serialNoList = new List<string>();

        private string getSerialNo(int rowNum)
        {
            if (serialNoList.Count < rowNum)
            {
                return string.Empty;
            }
            else
            {
                return serialNoList[rowNum - 1];
            }
        }

        public string SerialNo1
        {
            get { return getSerialNo(1); }
        }

        public string SerialNo2
        {
            get { return getSerialNo(2); }
        }

        public string SerialNo3
        {
            get { return getSerialNo(3); }
        }

        public string SerialNo4
        {
            get { return getSerialNo(4); }
        }

        public string SerialNo5
        {
            get { return getSerialNo(5); }
        }

        public string SerialNo6
        {
            get { return getSerialNo(6); }
        }

        public string SerialNo7
        {
            get { return getSerialNo(7); }
        }

        private List<string> necessaryQtyList = new List<string>();

        private string getNecessaryQty(int rowNum)
        {
            if (necessaryQtyList.Count < rowNum)
            {
                return string.Empty;
            }
            else
            {
                return necessaryQtyList[rowNum - 1];
            }
        }

        public string NecessaryQty1
        {
            get { return getNecessaryQty(1); }
        }

        public string NecessaryQty2
        {
            get { return getNecessaryQty(2); }
        }

        public string NecessaryQty3
        {
            get { return getNecessaryQty(3); }
        }

        public string NecessaryQty4
        {
            get { return getNecessaryQty(4); }
        }

        public string NecessaryQty5
        {
            get { return getNecessaryQty(5); }
        }

        public string NecessaryQty6
        {
            get { return getNecessaryQty(6); }
        }

        public string NecessaryQty7
        {
            get { return getNecessaryQty(7); }
        }

        public string CustomerName
        {
            get { return customerName; }
            set { customerName = value; }
        }

        public string CustomerCode
        {
            get { return customerCode; }
            set { customerCode = value; }
        }

        public string Unit
        {
            get { return "(原单位重量" + unit + " g)"; }
            set { unit = value; }
        }

        public string Weight
        {
            get { return weight; }
            set { weight = value; }
        }

        public string Count
        {
            get { return count + " Pcs"; }
            set { count = value; }
        }

        public string ColorCode
        {
            get { return colorCode + "#"; }
            set { colorCode = value; }
        }

        public string ItemName
        {
            get { return itemName; }
            set { itemName = value; }
        }

        public string ItemCode
        {
            get { return itemCode; }
            set { itemCode = value; }
        }

        public string BucketNo
        {
            get { return bucketNo; }
            set { bucketNo = value; }
        }

        public string TicketNo
        {
            get { return ticketNo.ToUpper(); }
            set { ticketNo = value.ToUpper(); }
        }

        public string UnitBarcode
        {
            get
            { return "*" + this.unit + "*" + "\r\n" + "*" + this.unit + "*"; }
        }

        public string TicketBarcode
        {
            get
            { return "*" + this.ticketNo + "*" + "\r\n" + "*" + this.ticketNo + "*" + "\r\n" + "*" + this.ticketNo + "*"; }
        }

        public void AddDetails(string opno, string count, string weight, string retrievalNo, string serialNo, string necessaryQty)
        {
            this.opnoList.Add(opno);
            this.countList.Add(count);
            this.weightList.Add(weight);
            this.retrievalNoList.Add(retrievalNo);
            this.serialNoList.Add(serialNo);
            this.necessaryQtyList.Add(necessaryQty);
        }
    }
}
