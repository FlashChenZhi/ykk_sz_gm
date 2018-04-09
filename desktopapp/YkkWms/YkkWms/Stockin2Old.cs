using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Data.OleDb;
using PersistenceLayer;

namespace YkkWms
{
    public partial class Stockin2Old : Form
    {
        public Stockin2Old()
        {
            InitializeComponent();
        }

        private void Stockin2_KeyDown(object sender, KeyEventArgs e)
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
                case Keys.F3:
                    unitWeightLoadBtn.PerformClick();
                    break;
                case Keys.F5:
                    pickOutBtn.PerformClick();
                    break;
                case Keys.F6:
                    releaseBtn.PerformClick();
                    break;
                default:
                    break;
            }
        }

        private void pickOutBtn_Click(object sender, EventArgs e)
        {
            IDataParameter[] paras = new IDataParameter[2];

            OleDbParameter para = (OleDbParameter)Query.GetParameter("Ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_return_code";
            para.Value = 0;
            para.Size = 4;
            para.Direction = ParameterDirection.InputOutput;
            paras[0] = para;

            para = (OleDbParameter)Query.GetParameter("Ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_return_message";
            para.Value = string.Empty;
            para.Size = 32;
            para.Direction = ParameterDirection.InputOutput;
            paras[1] = para;

            Query.RunProcedure("stockin_2_cut_away", paras, "Ykk");

            if (Int32.Parse(paras[0].Value.ToString()) != 0)
            {
                throw new Exception(paras[1].Value.ToString());
            }
        }
    }
}