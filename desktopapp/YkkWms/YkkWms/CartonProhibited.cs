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
    public partial class CartonProhibited : Form
    {
        public CartonProhibited()
        {
            InitializeComponent();
        }

        private void CartonProhibited_Load(object sender, EventArgs e)
        {
            setUI();
        }

        private void setUI()
        {
            FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
            if (station == null)
            {
                MessageBox.Show("无法取得站台信息", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (station.INTO_FLAG == "0")
            {
                txtCurrentStatus.Text = "进入许可";
                txtCurrentStatus.BackColor = Color.Lime;
                rdoAllowed.Select();
            }
            else
            {
                txtCurrentStatus.Text = "进入禁止";
                txtCurrentStatus.BackColor = Color.Red;
                rdoProhibited.Select();
            }
        }

        private void CartonProhibited_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.F2:
                    btnOK.PerformClick();
                    break;

                case Keys.F12:
                    btnExit.PerformClick();
                    break;
                default:
                    break;
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
                if (station == null)
                {
                    MessageBox.Show("无法取得站台信息", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                station.INTO_FLAG = rdoAllowed.Checked ? "0" : "1";
                station.Save();

                FNJISEKIEntity jiseki = new FNJISEKIEntity();
                jiseki.SAGYOKBN = rdoAllowed.Checked ? "F" : "G";
                jiseki.USERID = GlobalAccess.UserId;
                jiseki.USERNAME = GlobalAccess.UserName;
                jiseki.SAKUSEIHIJI = DateTime.Now.ToString("yyyyMMddHHmmss");
                jiseki.ENDSTNO = GlobalAccess.StationNo;
                jiseki.Save();

                MessageBox.Show("设定成功", "信息", MessageBoxButtons.OK, MessageBoxIcon.Information);
                setUI();
                this.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "错误信息", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
