namespace PrintCenter
{
    partial class MainFrm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainFrm));
            this.btnExit = new System.Windows.Forms.Button();
            this.taskMonitorTimer = new System.Windows.Forms.Timer(this.components);
            this.notifyIcon1 = new System.Windows.Forms.NotifyIcon(this.components);
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.tsmiSetting = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmiExit = new System.Windows.Forms.ToolStripMenuItem();
            this.btnStartListeningService = new System.Windows.Forms.Button();
            this.btnStopListeningService = new System.Windows.Forms.Button();
            this.btnStartPrintingService = new System.Windows.Forms.Button();
            this.btnStopPrintingService = new System.Windows.Forms.Button();
            this.ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.miReportSetting = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.lblPrintingStatus = new System.Windows.Forms.Label();
            this.lblListeningStatus = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.btnPrint = new System.Windows.Forms.Button();
            this.startBt = new System.Windows.Forms.Button();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.msgLb = new System.Windows.Forms.Label();
            this.timer2 = new System.Windows.Forms.Timer(this.components);
            this.label3 = new System.Windows.Forms.Label();
            this.contextMenuStrip1.SuspendLayout();
            this.menuStrip1.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnExit
            // 
            this.btnExit.Location = new System.Drawing.Point(349, 359);
            this.btnExit.Name = "btnExit";
            this.btnExit.Size = new System.Drawing.Size(132, 50);
            this.btnExit.TabIndex = 5;
            this.btnExit.Text = "退出(&X)";
            this.btnExit.UseVisualStyleBackColor = true;
            this.btnExit.Click += new System.EventHandler(this.btnExit_Click);
            // 
            // taskMonitorTimer
            // 
            this.taskMonitorTimer.Interval = 2000;
            this.taskMonitorTimer.Tick += new System.EventHandler(this.taskMonitorTimer_Tick);
            // 
            // notifyIcon1
            // 
            this.notifyIcon1.ContextMenuStrip = this.contextMenuStrip1;
            this.notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.Icon")));
            this.notifyIcon1.Text = "报表打印服务";
            this.notifyIcon1.Visible = true;
            this.notifyIcon1.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.notifyIcon1_MouseDoubleClick);
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.tsmiSetting,
            this.tsmiExit});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.RenderMode = System.Windows.Forms.ToolStripRenderMode.System;
            this.contextMenuStrip1.ShowImageMargin = false;
            this.contextMenuStrip1.Size = new System.Drawing.Size(92, 48);
            // 
            // tsmiSetting
            // 
            this.tsmiSetting.Name = "tsmiSetting";
            this.tsmiSetting.Size = new System.Drawing.Size(91, 22);
            this.tsmiSetting.Text = "设定";
            this.tsmiSetting.Visible = false;
            this.tsmiSetting.Click += new System.EventHandler(this.tsmiSetting_Click);
            // 
            // tsmiExit
            // 
            this.tsmiExit.Name = "tsmiExit";
            this.tsmiExit.Size = new System.Drawing.Size(91, 22);
            this.tsmiExit.Text = "退出(&X)";
            this.tsmiExit.Click += new System.EventHandler(this.tsmiExit_Click);
            // 
            // btnStartListeningService
            // 
            this.btnStartListeningService.Location = new System.Drawing.Point(201, 25);
            this.btnStartListeningService.Name = "btnStartListeningService";
            this.btnStartListeningService.Size = new System.Drawing.Size(54, 22);
            this.btnStartListeningService.TabIndex = 1;
            this.btnStartListeningService.Text = "启动";
            this.btnStartListeningService.UseVisualStyleBackColor = true;
            this.btnStartListeningService.Click += new System.EventHandler(this.btnStartListeningService_Click);
            // 
            // btnStopListeningService
            // 
            this.btnStopListeningService.Location = new System.Drawing.Point(261, 25);
            this.btnStopListeningService.Name = "btnStopListeningService";
            this.btnStopListeningService.Size = new System.Drawing.Size(54, 22);
            this.btnStopListeningService.TabIndex = 2;
            this.btnStopListeningService.Text = "停止";
            this.btnStopListeningService.UseVisualStyleBackColor = true;
            this.btnStopListeningService.Click += new System.EventHandler(this.btnStopListeningService_Click);
            // 
            // btnStartPrintingService
            // 
            this.btnStartPrintingService.Location = new System.Drawing.Point(201, 67);
            this.btnStartPrintingService.Name = "btnStartPrintingService";
            this.btnStartPrintingService.Size = new System.Drawing.Size(54, 22);
            this.btnStartPrintingService.TabIndex = 3;
            this.btnStartPrintingService.Text = "启动";
            this.btnStartPrintingService.UseVisualStyleBackColor = true;
            this.btnStartPrintingService.Click += new System.EventHandler(this.btnStartPrintingService_Click);
            // 
            // btnStopPrintingService
            // 
            this.btnStopPrintingService.Location = new System.Drawing.Point(261, 67);
            this.btnStopPrintingService.Name = "btnStopPrintingService";
            this.btnStopPrintingService.Size = new System.Drawing.Size(54, 22);
            this.btnStopPrintingService.TabIndex = 4;
            this.btnStopPrintingService.Text = "停止";
            this.btnStopPrintingService.UseVisualStyleBackColor = true;
            this.btnStopPrintingService.Click += new System.EventHandler(this.btnStopPrintingService_Click);
            // 
            // ToolStripMenuItem
            // 
            this.ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.miReportSetting});
            this.ToolStripMenuItem.Name = "ToolStripMenuItem";
            this.ToolStripMenuItem.Size = new System.Drawing.Size(44, 20);
            this.ToolStripMenuItem.Text = "设定";
            // 
            // miReportSetting
            // 
            this.miReportSetting.Name = "miReportSetting";
            this.miReportSetting.Size = new System.Drawing.Size(124, 22);
            this.miReportSetting.Text = "报表设定";
            this.miReportSetting.Click += new System.EventHandler(this.miReportSetting_Click);
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.ToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(493, 24);
            this.menuStrip1.TabIndex = 3;
            this.menuStrip1.Text = "menuStrip1";
            this.menuStrip1.Visible = false;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.lblPrintingStatus);
            this.groupBox1.Controls.Add(this.lblListeningStatus);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.btnStartPrintingService);
            this.groupBox1.Controls.Add(this.btnStopPrintingService);
            this.groupBox1.Controls.Add(this.btnStartListeningService);
            this.groupBox1.Controls.Add(this.btnStopListeningService);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(34, 34);
            this.groupBox1.TabIndex = 6;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "服务";
            this.groupBox1.Visible = false;
            // 
            // lblPrintingStatus
            // 
            this.lblPrintingStatus.AutoSize = true;
            this.lblPrintingStatus.Location = new System.Drawing.Point(125, 72);
            this.lblPrintingStatus.Name = "lblPrintingStatus";
            this.lblPrintingStatus.Size = new System.Drawing.Size(41, 12);
            this.lblPrintingStatus.TabIndex = 6;
            this.lblPrintingStatus.Text = "已停止";
            // 
            // lblListeningStatus
            // 
            this.lblListeningStatus.AutoSize = true;
            this.lblListeningStatus.Location = new System.Drawing.Point(125, 30);
            this.lblListeningStatus.Name = "lblListeningStatus";
            this.lblListeningStatus.Size = new System.Drawing.Size(41, 12);
            this.lblListeningStatus.TabIndex = 6;
            this.lblListeningStatus.Text = "已停止";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(19, 72);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(53, 12);
            this.label2.TabIndex = 5;
            this.label2.Text = "打印服务";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(19, 30);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(53, 12);
            this.label1.TabIndex = 5;
            this.label1.Text = "监听服务";
            // 
            // btnPrint
            // 
            this.btnPrint.Location = new System.Drawing.Point(211, 359);
            this.btnPrint.Name = "btnPrint";
            this.btnPrint.Size = new System.Drawing.Size(132, 50);
            this.btnPrint.TabIndex = 7;
            this.btnPrint.Text = "打印";
            this.btnPrint.UseVisualStyleBackColor = true;
            this.btnPrint.Click += new System.EventHandler(this.btnPrint_Click);
            // 
            // startBt
            // 
            this.startBt.Font = new System.Drawing.Font("NSimSun", 97F);
            this.startBt.Location = new System.Drawing.Point(73, 37);
            this.startBt.Name = "startBt";
            this.startBt.Size = new System.Drawing.Size(349, 174);
            this.startBt.TabIndex = 8;
            this.startBt.Text = "开始";
            this.startBt.UseVisualStyleBackColor = true;
            this.startBt.Click += new System.EventHandler(this.startBt_Click);
            // 
            // timer1
            // 
            this.timer1.Interval = 1000;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // msgLb
            // 
            this.msgLb.Font = new System.Drawing.Font("SimSun", 27F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.msgLb.ForeColor = System.Drawing.Color.Blue;
            this.msgLb.Location = new System.Drawing.Point(137, 281);
            this.msgLb.Name = "msgLb";
            this.msgLb.Size = new System.Drawing.Size(344, 53);
            this.msgLb.TabIndex = 9;
            this.msgLb.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // timer2
            // 
            this.timer2.Enabled = true;
            this.timer2.Interval = 60000;
            this.timer2.Tick += new System.EventHandler(this.timer2_Tick);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(12, 397);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(71, 12);
            this.label3.TabIndex = 10;
            this.label3.Text = "v20151125.1";
            // 
            // MainFrm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(493, 421);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.msgLb);
            this.Controls.Add(this.startBt);
            this.Controls.Add(this.btnPrint);
            this.Controls.Add(this.btnExit);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.menuStrip1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.menuStrip1;
            this.MaximizeBox = false;
            this.Name = "MainFrm";
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "打印服务器";
            this.Load += new System.EventHandler(this.MainFrm_Load);
            this.contextMenuStrip1.ResumeLayout(false);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnExit;
        private System.Windows.Forms.Timer taskMonitorTimer;
        private System.Windows.Forms.NotifyIcon notifyIcon1;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem tsmiSetting;
        private System.Windows.Forms.ToolStripMenuItem tsmiExit;
        private System.Windows.Forms.Button btnStartListeningService;
        private System.Windows.Forms.Button btnStopListeningService;
        private System.Windows.Forms.Button btnStartPrintingService;
        private System.Windows.Forms.Button btnStopPrintingService;
        private System.Windows.Forms.ToolStripMenuItem ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem miReportSetting;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblPrintingStatus;
        private System.Windows.Forms.Label lblListeningStatus;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button btnPrint;
        private System.Windows.Forms.Button startBt;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.Label msgLb;
        private System.Windows.Forms.Timer timer2;
        private System.Windows.Forms.Label label3;
    }
}