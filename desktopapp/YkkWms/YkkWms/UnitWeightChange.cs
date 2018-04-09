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
    public partial class UnitWeightChange : Form
    {
        private FNZAIKOEntity zaikoEntity = null;

        public UnitWeightChange(FNZAIKOEntity zaikoEntity)
        {
            InitializeComponent();
            this.zaikoEntity = zaikoEntity;
        }

        private void setBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (!string.IsNullOrEmpty(newUnitWeightBox.Text))
                {
                    decimal weight;
                    try
                    {
                        weight = decimal.Parse(newUnitWeightBox.Text)/1000;
                    }
                    catch (Exception)
                    {
                        MessageBox.Show("请输入合法的单位重量");
                        newUnitWeightBox.Focus();
                        newUnitWeightBox.SelectAll();
                        return;
                    }
                    if (zaikoEntity != null)
                    {
                        zaikoEntity.REAL_UNIT_WEIGHT = weight;
                        zaikoEntity.Save();
                        this.DialogResult = DialogResult.OK;
                        this.Close();
                    }
                }
                else
                {
                    MessageBox.Show("请输入新单位重量");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void UnitWeightChange_KeyDown(object sender, KeyEventArgs e)
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
    }
}
