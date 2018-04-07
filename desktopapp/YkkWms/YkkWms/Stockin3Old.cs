using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace YkkWms
{
    public partial class Stockin3Old : Form
    {
        public Stockin3Old()
        {
            InitializeComponent();
        }

        private void Stockin3_KeyDown(object sender, KeyEventArgs e)
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
                case Keys.F5:
                    weightLoadBtn.PerformClick();
                    break;
                case Keys.F7:
                    pickOutBtn.PerformClick();
                    break;
                default:
                    break;
            }
        }
    }
}