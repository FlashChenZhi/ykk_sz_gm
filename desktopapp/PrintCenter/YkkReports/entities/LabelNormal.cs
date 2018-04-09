using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;

namespace YkkReports.entities
{
    public class LabelNormal
    {
        private string ticketNo = string.Empty;

        private string bucketNo = string.Empty;

        private string itemCode = string.Empty;

        private string itemName = string.Empty;

        private string colorCode = string.Empty;

        private string count = string.Empty;

        private string weight = string.Empty;

        private string section = string.Empty;

        private string unit = string.Empty;

        private List<string> lineList = new List<string>();

        private string optionFlag = string.Empty;

        private string userName = string.Empty;

        public string UserName
        {
            get { return "操作员:" + userName; }
            set { userName = value; }
        }

        public string OptionFlag
        {
            get { return optionFlag; }
            set { optionFlag = value; }
        }

        private string getLine(int rowNum)
        {
            if (lineList.Count < rowNum)
            {
                return string.Empty;
            }
            else
            {
                return lineList[rowNum - 1];
            }
        }

        public string Line1
        {
            get { return getLine(1); }
        }

        public string Line2
        {
            get { return getLine(2); }
        }

        public string Line3
        {
            get { return getLine(3); }
        }

        public string Line4
        {
            get { return getLine(4); }
        }

        public string Line5
        {
            get { return getLine(5); }
        }

        public string Line6
        {
            get { return getLine(6); }
        }

        public string Line7
        {
            get { return getLine(7); }
        }

        public string Line8
        {
            get { return getLine(8); }
        }

        public string Line9
        {
            get { return getLine(9); }
        }

        public string Line10
        {
            get { return getLine(10); }
        }

        public string Line11
        {
            get { return getLine(11); }
        }

        public string Line12
        {
            get { return getLine(12); }
        }

        public string Line13
        {
            get { return getLine(13); }
        }

        public string Line14
        {
            get { return getLine(14); }
        }

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

        public string Count8
        {
            get { return getCount(8); }
        }

        public string Count9
        {
            get { return getCount(9); }
        }

        public string Count10
        {
            get { return getCount(10); }
        }

        public string Count11
        {
            get { return getCount(11); }
        }

        public string Count12
        {
            get { return getCount(12); }
        }

        public string Count13
        {
            get { return getCount(13); }
        }

        public string Count14
        {
            get { return getCount(14); }
        }

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

        public string Weight8
        {
            get { return getWeight(8); }
        }

        public string Weight9
        {
            get { return getWeight(9); }
        }

        public string Weight10
        {
            get { return getWeight(10); }
        }

        public string Weight11
        {
            get { return getWeight(11); }
        }

        public string Weight12
        {
            get { return getWeight(12); }
        }

        public string Weight13
        {
            get { return getWeight(13); }
        }

        public string Weight14
        {
            get { return getWeight(14); }
        }

        public string Unit
        {
            get { return "(原单位重量 " + unit + " g)"; }
            set { unit = value; }
        }

        public string Section
        {
            get { return section; }
            set { section = value; }
        }

        public string Weight
        {
            get { return weight + " Kg"; }
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

        public void AddDetails(string line, string count, string weight)
        {
            this.lineList.Add(line);
            this.countList.Add(count);
            this.weightList.Add(weight);
        }
    }
}
