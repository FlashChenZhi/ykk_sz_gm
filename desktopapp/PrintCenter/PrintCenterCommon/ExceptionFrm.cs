using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace PrintCenterCommon
{
    public partial class ExceptionFrm : Form
    {
        private Exception ex;
        public static void Show(Exception ex)
        {
            ExceptionFrm frm = new ExceptionFrm(ex);
            frm.StartPosition = FormStartPosition.CenterScreen;
            frm.Show();
        }
        public static void Show(Exception ex, Form parent)
        {
            ExceptionFrm frm = new ExceptionFrm(ex);
            frm.StartPosition = FormStartPosition.CenterScreen;
            frm.ShowDialog(parent);
        }

        public ExceptionFrm()
        {
            InitializeComponent();
        }
        public ExceptionFrm(Exception ex)
        {

            InitializeComponent();
            this.ex = ex;
            exBox.Text = ex.Message;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            MessageBox.Show(ex.Source);
        }
    }
}