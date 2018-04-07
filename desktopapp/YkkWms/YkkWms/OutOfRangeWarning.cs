using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace YkkWms
{
    public partial class OutOfRangeWarning : Form
    {
        public OutOfRangeWarning(string title, string errorRate, string errorRateMin, string errorRateMax)
        {
            InitializeComponent();
            this.lblTitle.Text = title;
            this.lblErrorRate.Text = errorRate + "%";
            this.lblErrorRateMin.Text = errorRateMin + "%";
            this.lblErrorRateMax.Text = errorRateMax + "% )";

        }

        private void OutOfRangeWarning_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.F4:
                    btnOK.PerformClick();
                    break;
                default:
                    break;
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
