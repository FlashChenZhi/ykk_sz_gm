using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PersistenceLayer;
using BusinessEntity;

namespace WavesStatusMonitor
{
    public partial class frmMain : Form
    {
        public frmMain()
        {
            InitializeComponent();
        }

        private void frmMain_Load(object sender, EventArgs e)
        {            
            int intervalSeconds = 300;
            int.TryParse( AppConfig.Get("IntervalSeconds"), out intervalSeconds);
            timer.Interval = intervalSeconds * 1000;
            timer.Start();
            timer_Tick(null, null);            
        }

        private void frmMain_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (e.CloseReason != CloseReason.WindowsShutDown)
            {
                e.Cancel = true;
                tsmiHide.PerformClick();
            }
        }

        private void notifyIcon_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.Visible = !this.Visible;
        }

        private void tsmiShow_Click(object sender, EventArgs e)
        {
            this.TopMost = true;
            this.Visible = true;
            this.TopMost = false;
        }

        private void tsmiHide_Click(object sender, EventArgs e)
        {
            this.Visible = false;
        }

        private void tsmiExit_Click(object sender, EventArgs e)
        {
            this.notifyIcon.Visible = false;
            this.Close();
            this.Dispose();
            Application.Exit();
        }

        //1:normal 2:batch 3:cycle 4:error
        private int lastStatus = 0;

        private void timer_Tick(object sender, EventArgs e)
        {
            try
            {
                FNSYSTEMEntity systemInfo = new RetrieveCriteria(typeof(FNSYSTEMEntity)).AsEntity() as FNSYSTEMEntity;

                int status = 1;
                if (systemInfo.WORK_FILE_FLG.TrimEnd() != "0"
                    || systemInfo.TRX00_FLG.TrimEnd() != "0"
                    || systemInfo.TRY00_FLG.TrimEnd() != "0"
                    || systemInfo.TRZ00_FLG.TrimEnd() != "0"
                    || systemInfo.RETRIEVAL_CANCEL_FLG.TrimEnd() != "0")
                {
                    status = 4;
                }
                else if (systemInfo.BATCH_FLAG == "2")
                {
                    status = 3;
                }
                else if (systemInfo.BATCH_FLAG == "1")
                {
                    status = 2;
                }

                if (status == lastStatus)
                {
                    return;
                }
                else 
                {
                    lastStatus = status;
                }

                switch (status)
                {
                    case 1:
                        lblStatus.Text = "   正常   ";
                        this.lblFixedHead.ForeColor = Color.White;
                        this.lblStatus.ForeColor = Color.White;
                        this.BackColor = Color.Blue;
                        break;
                    case 2:
                        lblStatus.Text = "日次处理中";
                        this.lblFixedHead.ForeColor = Color.Black;
                        this.lblStatus.ForeColor = Color.Black;
                        this.BackColor = Color.Yellow;
                        break;
                    case 3:
                        lblStatus.Text = "  盘点中  ";
                        this.lblFixedHead.ForeColor = Color.Black;
                        this.lblStatus.ForeColor = Color.Black;
                        this.BackColor = Color.Yellow;
                        break;
                    case 4:
                        lblStatus.Text = "   异常   ";
                        this.lblFixedHead.ForeColor = Color.White;
                        this.lblStatus.ForeColor = Color.White;
                        this.BackColor = Color.Red;
                        break;
                }

                tsmiShow.PerformClick();
            }
            catch(Exception ex)
            {
                AppLogger.logErrorMessage(ex.Message);
            }
        }           

        private void notifyIcon_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left && e.Clicks == 0)
            {
                tsmiShow.PerformClick();
            }            
        }
    }
}
