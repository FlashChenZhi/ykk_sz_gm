using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using BusinessEntity;

namespace YkkWms
{
    public partial class OtherStockin1 : Form
    {
        string bucketNo;
        private FMZKEYEntity zKey;
        private int parentWindowType = 0; //1: Stockin1   2: Stockin2

        public OtherStockin1(int parentWindowType, string bucketNo)
        {
            this.parentWindowType = parentWindowType;
            this.bucketNo = bucketNo;
            InitializeComponent();
        }

        private void OtherStockin1_Load(object sender, EventArgs e)
        {
            try
            {
                userIdBox1.Text = GlobalAccess.UserId;
                userIdBox2.Text = GlobalAccess.UserName;
                GlobalAccess.UseMockWeighter = AppConfig.Get("MockWeighter") == "1";
                FMBUCKETEntity bucket = DbAccess.GetBucket(bucketNo);
                bucketNoBox.Text = bucket.BUCKET_NO;
                bucketWeightBox.Text = bucket.PACKING_WEIGHT.ToString();
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void quitBtn_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void cancelBtn_Click(object sender, EventArgs e)
        {
            ClearAll();
        }

        private void ClearAll()
        {
            //statusBox.Clear();
            //bucketNoBox.Clear();
            //bucketWeightBox.Clear();
            itemNameBox1.Clear();
            itemNameBox2.Clear();
            itemNameBox3.Clear();
            itemCodeBox.Clear();
            colorCodeBox.Clear();
            itemCountBox.Value = 0;

            zKey = null;
        }

        private FNTOUCYAKUEntity touCyaKu = null;

        private void setBtn_Click(object sender, EventArgs e)
        {
            string schno = string.Empty;
            try
            {
                if (parentWindowType == 1)
                {
                    touCyaKu = DbAccess.GetTouCyaKu(GlobalAccess.StationNo);
                    if (touCyaKu == null)
                    {
                        msgBox.Text = "没有到达报告";
                        return;
                    }
                }
                if (string.IsNullOrEmpty(itemCodeBox.Text))
                {
                    msgBox.Text = "Item Name不能为空";
                    itemCodeBox.Focus();
                    itemCodeBox.SelectAll();
                    return;
                }
                else if (DoItemCode(itemCodeBox.Text) == false)
                {
                    return;
                }
                else if (string.IsNullOrEmpty(colorCodeBox.Text))
                {
                    msgBox.Text = "Color Code不能为空";
                    colorCodeBox.Focus();
                    colorCodeBox.SelectAll();
                    return;
                }
                else if (itemCountBox.Value == 0)
                {
                    msgBox.Text = "Item个数不能为0";
                    itemCountBox.Focus();
                    itemCountBox.Select();
                    return;
                }

                schno = DbAccess.generateScheduleNo();
                FNGSETEntity fngset = new FNGSETEntity();
                fngset.SCHNO = schno;
                fngset.MOTOSTNO = GlobalAccess.StationNo;
                fngset.BUCKET_NO = bucketNoBox.Text;
                fngset.ZAIKEY = itemCodeBox.Text;
                fngset.COLOR_CODE = colorCodeBox.Text;
                fngset.NYUSYUSU = itemCountBox.Value;
                fngset.USERID = GlobalAccess.UserId;
                fngset.USERNAME = GlobalAccess.UserName;
                fngset.SYORIFLG = "0";
                fngset.Save();

                if (parentWindowType == 1)
                {
                    DbAccess.callProcedure(schno, "unmanaged_stockin_1_start");
                }
                else if (parentWindowType == 2)
                {
                    DbAccess.callProcedure(schno, "unmanaged_stockin_2_start");
                }
                DbAccess.callAfterStockin(string.Empty);
                msgBox.Text = "设定成功";

                ClearAll();

                this.DialogResult = DialogResult.OK;
                this.Close();
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
            finally
            {
                DbAccess.UpdateFngset(schno);
            }
        }

        private bool DoItemCode(string itemCode)
        {
            if (string.IsNullOrEmpty(itemCode)) return false;

            zKey = DbAccess.GetUnmanagedZKey(itemCode);
            if (zKey != null)
            {
                itemNameBox1.Text = zKey.ZKNAME1;
                itemNameBox2.Text = zKey.ZKNAME2;
                itemNameBox3.Text = zKey.ZKNAME3;
                msgBox.Text = string.Empty;
                return true;
            }
            else
            {
                msgBox.Text = "没有此Item主数据";
                itemCodeBox.Focus();
                itemCodeBox.SelectAll();
                return false;
            }
        }

        private void itemCodeBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            try
            {
                switch (e.KeyChar)
                {
                    case '\r':
                        DoItemCode(itemCodeBox.Text);
                        break;
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void OtherStockin1_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.F2:
                    setBtn.PerformClick();
                    break;
                case Keys.F12:
                    quitBtn.PerformClick();
                    break;
                case Keys.F9:
                    cancelBtn.PerformClick();
                    break;
                default:
                    break;
            }
        }
    }
}