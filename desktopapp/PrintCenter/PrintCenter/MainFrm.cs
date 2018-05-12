using System;
using System.Windows.Forms;
using System.Runtime.Remoting.Channels.Tcp;
using System.Runtime.Remoting.Channels;
using System.Runtime.Remoting;
using System.Configuration;
using PrintCenterServer;
using PrintCenterService;
using System.IO;
using PrintCenterCommon;


namespace PrintCenter
{
    public partial class MainFrm : Form
    {
        Sunisoft.IrisSkin.SkinEngine skinEngine1 = new Sunisoft.IrisSkin.SkinEngine();
        LabelCenter lc;
        ReportCenter rc;
        CartCenter cc;
        public MainFrm()
        {
            InitializeComponent();
            //this.Hide();
        }
        
        private void btnTest_Click(object sender, EventArgs e)
        {
            try
            {
                ITaskLoader ptm = TaskLoaderFactory.getInstance();
                Task task = ptm.loadTask();
                if (task == null) return;
                PrintCenter pc = new PrintCenter();
                pc.DoPrint(task);
            }
            catch (Exception ex)
            {
                ExceptionFrm.Show(ex);
            }
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            this.Close();        
        }

        private void taskMonitorTimer_Tick(object sender, EventArgs e)
        {
            try
            {
                ITaskLoader ptm = TaskLoaderFactory.getInstance();
                Task task = ptm.loadTask();
                if (task == null) return;
                PrintCenter pc = new PrintCenter();
                pc.DoPrint(task);
            }
            catch (Exception ex)
            {
                CommonHelper.WriteLog(ex.Message);
            }   
        }

        private void miReportSetting_Click(object sender, EventArgs e)
        {
            //ReportSettingsFrm frm = new ReportSettingsFrm();
            //frm.Show();
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            if (this.WindowState == FormWindowState.Normal)
            {
                this.WindowState = FormWindowState.Minimized;
            }
            else if (this.WindowState == FormWindowState.Minimized)
            {
                this.WindowState = FormWindowState.Normal;
            }
        }


        private void tsmiSetting_Click(object sender, EventArgs e)
        {
            //ReportSettingsFrm frm = new ReportSettingsFrm();
            //frm.Show();
        }

        private void tsmiExit_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnStartListeningService_Click(object sender, EventArgs e)
        {
            try
            {
                this.lblListeningStatus.Text = "启动中";
                string portString = ConfigurationManager.AppSettings.Get("port");
                portString = portString == null ? "8000" : portString;
                int port = 0;
                if (!Int32.TryParse(portString, out port))
                {
                    port = 8000;
                }
                TcpServerChannel tcpServerChannel = new TcpServerChannel("ReportChannel", port);
                ChannelServices.RegisterChannel(tcpServerChannel, false);
                RemotingConfiguration.RegisterWellKnownServiceType(typeof(TaskPersisterFactory),
                                                                "TaskPersister",
                                                                WellKnownObjectMode.Singleton);

                RemotingConfiguration.RegisterWellKnownServiceType(typeof(PrinterHelperFactory),
                                                                "PrinterHelper",
                                                                WellKnownObjectMode.Singleton);
                btnStartListeningService.Enabled = false;
                btnStopListeningService.Enabled = true;
                this.lblListeningStatus.Text = "已启动";
            }
            catch (Exception ex)
            {                
                btnStartListeningService.Enabled = true;
                btnStopListeningService.Enabled = false;
                this.lblListeningStatus.Text = "已停止";
                CommonHelper.ShowErrorBox("启动监听服务失败。");
                CommonHelper.WriteLog("failed to start service. " + ex.Message);
            }
        }

        private void btnStopListeningService_Click(object sender, EventArgs e)
        {
            try
            {
                this.lblListeningStatus.Text = "停止中";
                foreach (IChannel channel in ChannelServices.RegisteredChannels)
                {
                    if (channel.ChannelName == "ReportChannel")
                    {
                        TcpServerChannel tcpServerChannel = channel as TcpServerChannel;
                        tcpServerChannel.StopListening(null);
                        ChannelServices.UnregisterChannel(tcpServerChannel);
                    }
                }
                btnStopListeningService.Enabled = false;
                btnStartListeningService.Enabled = true;
                this.lblListeningStatus.Text = "已停止";
            }
            catch (Exception ex)
            {
                btnStopListeningService.Enabled = true;
                btnStartListeningService.Enabled = false;
                this.lblListeningStatus.Text = "已启动";
                CommonHelper.ShowErrorBox("停止监听服务失败。");
                CommonHelper.WriteLog("failed to stop service. " + ex.Message);
            }
        }

        private void MainFrm_Load(object sender, EventArgs e)
        {
            this.skinEngine1.SkinFile = "office2007.ssk";
            PrintCenter pc = new PrintCenter();
            lc = new LabelCenter(pc);
            rc = new ReportCenter(pc);
            cc = new CartCenter(pc);

            TimeSpan span = DateTime.Now - DateTime.Now;
            
            //btnStartPrintingService.PerformClick();
            //btnStartListeningService.PerformClick();

            //initDirectory();            
        }

        private void ClearLogs(string path)
        {
            if (!Directory.Exists(path)) return;
            string[] fileList = Directory.GetFiles(path);
            DateTime today = DateTime.Now;
            for (int i = 0; i < fileList.Length; i++)
            {
                FileInfo fileInfo = new FileInfo(fileList[i]);

                int days = 30;
                Int32.TryParse(ConfigurationManager.AppSettings.Get("clear-logs-days"),out days);
                if ((today - fileInfo.CreationTime).Days > days)
                {
                    try
                    {
                        File.Delete(fileList[i]);
                    }
                    catch
                    { 
                    }
                }
            }
        }

        private static void initDirectory()
        {
            if (!Directory.Exists(PrintCenterConfiguration.getInstance().DirectoryToMonitor))
            {
                Directory.CreateDirectory(PrintCenterConfiguration.getInstance().DirectoryToMonitor);
            }

            if (!Directory.Exists(GlobalVariable.getPrintedDirectory()))
            {
                Directory.CreateDirectory(GlobalVariable.getPrintedDirectory());
            }

            if (!Directory.Exists(GlobalVariable.getDeletedDirectory()))
            {
                Directory.CreateDirectory(GlobalVariable.getDeletedDirectory());
            }

            if (!Directory.Exists(GlobalVariable.getPrintingDirectory()))
            {
                Directory.CreateDirectory(GlobalVariable.getPrintingDirectory());
            }
        }

        private void btnStartPrintingService_Click(object sender, EventArgs e)
        {
            this.lblPrintingStatus.Text = "启动中";
            //taskMonitorTimer.Enabled = true;
            btnStartPrintingService.Enabled = false;
            btnStopPrintingService.Enabled = true;
            this.lblPrintingStatus.Text = "已启动";
        }

        private void btnStopPrintingService_Click(object sender, EventArgs e)
        {
            this.lblPrintingStatus.Text = "停止中";
            taskMonitorTimer.Enabled = false;
            btnStartPrintingService.Enabled = true;
            btnStopPrintingService.Enabled = false;
            this.lblPrintingStatus.Text = "已停止";
        }

        private void btnPrint_Click(object sender, EventArgs e)
        {
            try
            {
                lc.CheckLabelTask();
                rc.CheckReportTask();
                cc.CheckCartTask();
            }
            catch (Exception ex)
            {
                ExceptionFrm.Show(ex);
            }

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            try
            {
                lc.CheckLabelTask();
            }
            catch (Exception ex)
            {
                ExceptionFrm.Show(ex);
            }

            try
            {
                rc.CheckReportTask();
            }
            catch (Exception ex)
            {
                ExceptionFrm.Show(ex);
            }

            try
            {
                cc.CheckCartTask();
            }
            catch (Exception ex)
            {
                ExceptionFrm.Show(ex);
            }
        }

        private void startBt_Click(object sender, EventArgs e)
        {
            if (timer1.Enabled)
            {
                timer1.Enabled = false;
                startBt.Text = "开始";
                msgLb.Text = "打印服务已停止";
            }
            else
            {
                timer1.Enabled = true;
                startBt.Text = "停止";
                msgLb.Text = "打印服务运行中...";

            }
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            ClearLogs(Application.StartupPath + @"\logs\");
        }       
    }
}