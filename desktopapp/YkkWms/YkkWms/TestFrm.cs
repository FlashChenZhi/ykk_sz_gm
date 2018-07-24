using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;

namespace YkkWms
{
    public partial class TestFrm : Form
    {
        SerialPortHelper s = new SerialPortHelper("COM5");
        public TestFrm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {

            try
            {
                Weighter_Small.Instance.SendItemWeight(7.77m);
                Weighter_Small.Instance.SendBucketWeight(99.9m);
                WeightData data = Weighter_Small.Instance.GetWeightData();
                textBox1.AppendText(data.weight.ToString());
                textBox1.AppendText("\r\n");
                textBox1.AppendText(data.count.ToString());
                textBox1.AppendText("\r\n");
            }
            catch (Exception ex)
            {
                textBox1.AppendText(ex.Message + "\r\n");
            }

       
        }

        private string GetString(byte[] bytes,int length)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++)
            {
                sb.Append(bytes[i].ToString());
            }
            return sb.ToString();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.serialPort1.Open();
        }
    }
}
