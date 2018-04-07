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
    public partial class BucketMaintenance : Form
    {
        string bucketNo;
        FMBUCKETEntity bucket;
        public BucketMaintenance(string bucketNo)
        {
            this.bucketNo = bucketNo;
            InitializeComponent();
        }

        private void BucketMaintenance_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.F2:
                    setBtn.PerformClick();
                    break;
                default:
                    break;
            }
        }

        private void setBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (!string.IsNullOrEmpty(newBucketWeightBox.Text))
                {
                    decimal weight;
                    try
                    {
                        weight = decimal.Parse(newBucketWeightBox.Text);
                    }
                    catch (Exception)
                    {
                        MessageBox.Show("请输入合法的bucket重量");
                        newBucketWeightBox.Focus();
                        newBucketWeightBox.SelectAll();
                        return;
                    }
                    if (bucket != null)
                    {
                        bucket.PACKING_WEIGHT = weight;
                        bucket.Save();
                        this.DialogResult = DialogResult.OK;
                        this.Close();
                    }
                }
                else
                {
                    MessageBox.Show("请输入bucket重量");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }

        private void BucketMaintenance_Load(object sender, EventArgs e)
        {
            try
            {
                bucketNoBox1.Text = bucketNo;
                bucket = DbAccess.GetBucket(bucketNo);
                if (bucket != null)
                {
                    newBucketWeightBox.Text = bucket.PACKING_WEIGHT.ToString();
                }
                else
                {
                    setBtn.Enabled = false;
                    MessageBox.Show("无此BucketNo");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}