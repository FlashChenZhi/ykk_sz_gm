using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace YkkWms
{
    public partial class LablePublish : Form
    {
        public LablePublish()
        {
            InitializeComponent();
        }

        private void LablePublish_KeyDown(object sender, KeyEventArgs e)
        {

            switch (e.KeyCode)
            {
                case Keys.F2:
                    okBtn.PerformClick();
                    break;
                case Keys.F5:
                    reprintBtn.PerformClick();
                    break;
                default:
                    break;
            }


        }
    }
}