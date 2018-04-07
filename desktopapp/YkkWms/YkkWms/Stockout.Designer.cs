namespace YkkWms
{
    partial class Stockout
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
            this.quitBtn = new System.Windows.Forms.Button();
            this.uiClearBtn = new System.Windows.Forms.Button();
            this.setBtn = new System.Windows.Forms.Button();
            this.userNameBox = new System.Windows.Forms.TextBox();
            this.userIdBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.jobStatus = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.jobBeginBtn = new System.Windows.Forms.Button();
            this.jobStopBtn = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label16 = new System.Windows.Forms.Label();
            this.itemName2Box = new System.Windows.Forms.TextBox();
            this.originalBucketWeightBox = new System.Windows.Forms.TextBox();
            this.itemName3Box = new System.Windows.Forms.TextBox();
            this.itemName1Box = new System.Windows.Forms.TextBox();
            this.itemCodeBox = new System.Windows.Forms.TextBox();
            this.colorCodeBox = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.ticketNoBox = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.originalBucketNoBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.label30 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.label22 = new System.Windows.Forms.Label();
            this.receiveMessageBtn = new System.Windows.Forms.Button();
            this.label23 = new System.Windows.Forms.Label();
            this.pickingCountBox = new System.Windows.Forms.TextBox();
            this.rangeLimitToBox = new System.Windows.Forms.TextBox();
            this.label14 = new System.Windows.Forms.Label();
            this.label24 = new System.Windows.Forms.Label();
            this.sendMessageBtn = new System.Windows.Forms.Button();
            this.rangeLimitFromBox = new System.Windows.Forms.TextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.label25 = new System.Windows.Forms.Label();
            this.unitWeightBtn = new System.Windows.Forms.Button();
            this.errorBox = new System.Windows.Forms.TextBox();
            this.newBucketWeightBox = new System.Windows.Forms.TextBox();
            this.label26 = new System.Windows.Forms.Label();
            this.unitWeightBox = new System.Windows.Forms.TextBox();
            this.newBucketNoBox = new System.Windows.Forms.TextBox();
            this.label11 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.jobCountBox = new System.Windows.Forms.TextBox();
            this.totalStockoutCountBox = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.remainJobBox = new System.Windows.Forms.TextBox();
            this.remainJobBtn = new System.Windows.Forms.Button();
            this.jobTypeBox = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.emptyBucketPositionBox = new System.Windows.Forms.TextBox();
            this.cancelBtn = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.bucketMaintenanceBtn = new System.Windows.Forms.Button();
            this.datetimeLb = new System.Windows.Forms.Label();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.msgBox = new System.Windows.Forms.TextBox();
            this.label17 = new System.Windows.Forms.Label();
            this.txtEnterStatus = new System.Windows.Forms.TextBox();
            this.btnProhibitEntrance = new System.Windows.Forms.Button();
            this.timer2 = new System.Windows.Forms.Timer(this.components);
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // quitBtn
            // 
            this.quitBtn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.quitBtn.Location = new System.Drawing.Point(860, 677);
            this.quitBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.quitBtn.Name = "quitBtn";
            this.quitBtn.Size = new System.Drawing.Size(143, 52);
            this.quitBtn.TabIndex = 12;
            this.quitBtn.Text = "退出(F12)";
            this.quitBtn.UseVisualStyleBackColor = true;
            this.quitBtn.Click += new System.EventHandler(this.quitBtn_Click);
            // 
            // uiClearBtn
            // 
            this.uiClearBtn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.uiClearBtn.Location = new System.Drawing.Point(709, 677);
            this.uiClearBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.uiClearBtn.Name = "uiClearBtn";
            this.uiClearBtn.Size = new System.Drawing.Size(143, 52);
            this.uiClearBtn.TabIndex = 11;
            this.uiClearBtn.Text = "取消(F9)";
            this.uiClearBtn.UseVisualStyleBackColor = true;
            this.uiClearBtn.Click += new System.EventHandler(this.uiClearBtn_Click);
            // 
            // setBtn
            // 
            this.setBtn.Location = new System.Drawing.Point(29, 266);
            this.setBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.setBtn.Name = "setBtn";
            this.setBtn.Size = new System.Drawing.Size(114, 32);
            this.setBtn.TabIndex = 8;
            this.setBtn.Text = "确定(F2)";
            this.setBtn.UseVisualStyleBackColor = true;
            this.setBtn.Click += new System.EventHandler(this.setBtn_Click);
            // 
            // userNameBox
            // 
            this.userNameBox.Location = new System.Drawing.Point(281, 9);
            this.userNameBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.userNameBox.Name = "userNameBox";
            this.userNameBox.ReadOnly = true;
            this.userNameBox.Size = new System.Drawing.Size(562, 27);
            this.userNameBox.TabIndex = 62;
            this.userNameBox.TabStop = false;
            // 
            // userIdBox
            // 
            this.userIdBox.Location = new System.Drawing.Point(122, 9);
            this.userIdBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.userIdBox.Name = "userIdBox";
            this.userIdBox.ReadOnly = true;
            this.userIdBox.Size = new System.Drawing.Size(148, 27);
            this.userIdBox.TabIndex = 61;
            this.userIdBox.TabStop = false;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(34, 12);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(80, 18);
            this.label1.TabIndex = 60;
            this.label1.Text = "用户编号";
            // 
            // jobStatus
            // 
            this.jobStatus.BackColor = System.Drawing.SystemColors.Control;
            this.jobStatus.Location = new System.Drawing.Point(122, 46);
            this.jobStatus.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.jobStatus.Name = "jobStatus";
            this.jobStatus.ReadOnly = true;
            this.jobStatus.Size = new System.Drawing.Size(148, 27);
            this.jobStatus.TabIndex = 67;
            this.jobStatus.TabStop = false;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(34, 49);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(80, 18);
            this.label2.TabIndex = 66;
            this.label2.Text = "作业状态";
            // 
            // jobBeginBtn
            // 
            this.jobBeginBtn.Location = new System.Drawing.Point(281, 41);
            this.jobBeginBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.jobBeginBtn.Name = "jobBeginBtn";
            this.jobBeginBtn.Size = new System.Drawing.Size(112, 33);
            this.jobBeginBtn.TabIndex = 1;
            this.jobBeginBtn.Text = "开始";
            this.jobBeginBtn.UseVisualStyleBackColor = true;
            this.jobBeginBtn.Click += new System.EventHandler(this.jobBeginBtn_Click);
            // 
            // jobStopBtn
            // 
            this.jobStopBtn.Location = new System.Drawing.Point(401, 41);
            this.jobStopBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.jobStopBtn.Name = "jobStopBtn";
            this.jobStopBtn.Size = new System.Drawing.Size(112, 33);
            this.jobStopBtn.TabIndex = 2;
            this.jobStopBtn.Text = "中断";
            this.jobStopBtn.UseVisualStyleBackColor = true;
            this.jobStopBtn.Click += new System.EventHandler(this.jobStopBtn_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.label16);
            this.groupBox1.Controls.Add(this.itemName2Box);
            this.groupBox1.Controls.Add(this.originalBucketWeightBox);
            this.groupBox1.Controls.Add(this.itemName3Box);
            this.groupBox1.Controls.Add(this.itemName1Box);
            this.groupBox1.Controls.Add(this.itemCodeBox);
            this.groupBox1.Controls.Add(this.colorCodeBox);
            this.groupBox1.Controls.Add(this.label7);
            this.groupBox1.Controls.Add(this.label6);
            this.groupBox1.Controls.Add(this.ticketNoBox);
            this.groupBox1.Controls.Add(this.label5);
            this.groupBox1.Controls.Add(this.originalBucketNoBox);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Location = new System.Drawing.Point(33, 84);
            this.groupBox1.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Padding = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.groupBox1.Size = new System.Drawing.Size(951, 211);
            this.groupBox1.TabIndex = 70;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "出库料箱情报";
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Location = new System.Drawing.Point(429, 31);
            this.label16.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(26, 18);
            this.label16.TabIndex = 88;
            this.label16.Text = "Kg";
            // 
            // itemName2Box
            // 
            this.itemName2Box.Location = new System.Drawing.Point(273, 100);
            this.itemName2Box.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.itemName2Box.Name = "itemName2Box";
            this.itemName2Box.ReadOnly = true;
            this.itemName2Box.Size = new System.Drawing.Size(409, 27);
            this.itemName2Box.TabIndex = 32;
            this.itemName2Box.TabStop = false;
            // 
            // originalBucketWeightBox
            // 
            this.originalBucketWeightBox.Location = new System.Drawing.Point(273, 28);
            this.originalBucketWeightBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.originalBucketWeightBox.Name = "originalBucketWeightBox";
            this.originalBucketWeightBox.ReadOnly = true;
            this.originalBucketWeightBox.Size = new System.Drawing.Size(148, 27);
            this.originalBucketWeightBox.TabIndex = 87;
            this.originalBucketWeightBox.TabStop = false;
            // 
            // itemName3Box
            // 
            this.itemName3Box.Location = new System.Drawing.Point(273, 137);
            this.itemName3Box.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.itemName3Box.Name = "itemName3Box";
            this.itemName3Box.ReadOnly = true;
            this.itemName3Box.Size = new System.Drawing.Size(409, 27);
            this.itemName3Box.TabIndex = 31;
            this.itemName3Box.TabStop = false;
            // 
            // itemName1Box
            // 
            this.itemName1Box.Location = new System.Drawing.Point(273, 64);
            this.itemName1Box.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.itemName1Box.Name = "itemName1Box";
            this.itemName1Box.ReadOnly = true;
            this.itemName1Box.Size = new System.Drawing.Size(409, 27);
            this.itemName1Box.TabIndex = 30;
            this.itemName1Box.TabStop = false;
            // 
            // itemCodeBox
            // 
            this.itemCodeBox.Location = new System.Drawing.Point(114, 64);
            this.itemCodeBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.itemCodeBox.Name = "itemCodeBox";
            this.itemCodeBox.ReadOnly = true;
            this.itemCodeBox.Size = new System.Drawing.Size(148, 27);
            this.itemCodeBox.TabIndex = 29;
            this.itemCodeBox.TabStop = false;
            // 
            // colorCodeBox
            // 
            this.colorCodeBox.Location = new System.Drawing.Point(114, 174);
            this.colorCodeBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.colorCodeBox.Name = "colorCodeBox";
            this.colorCodeBox.ReadOnly = true;
            this.colorCodeBox.Size = new System.Drawing.Size(148, 27);
            this.colorCodeBox.TabIndex = 28;
            this.colorCodeBox.TabStop = false;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(34, 177);
            this.label7.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(80, 18);
            this.label7.TabIndex = 27;
            this.label7.Text = "颜色代码";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(70, 67);
            this.label6.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(44, 18);
            this.label6.TabIndex = 23;
            this.label6.Text = "品名";
            // 
            // ticketNoBox
            // 
            this.ticketNoBox.Location = new System.Drawing.Point(569, 28);
            this.ticketNoBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.ticketNoBox.Name = "ticketNoBox";
            this.ticketNoBox.ReadOnly = true;
            this.ticketNoBox.Size = new System.Drawing.Size(178, 27);
            this.ticketNoBox.TabIndex = 21;
            this.ticketNoBox.TabStop = false;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(482, 31);
            this.label5.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(80, 18);
            this.label5.TabIndex = 20;
            this.label5.Text = "生产票号";
            // 
            // originalBucketNoBox
            // 
            this.originalBucketNoBox.Location = new System.Drawing.Point(114, 28);
            this.originalBucketNoBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.originalBucketNoBox.Name = "originalBucketNoBox";
            this.originalBucketNoBox.ReadOnly = true;
            this.originalBucketNoBox.Size = new System.Drawing.Size(148, 27);
            this.originalBucketNoBox.TabIndex = 19;
            this.originalBucketNoBox.TabStop = false;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(34, 31);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(80, 18);
            this.label3.TabIndex = 18;
            this.label3.Text = "箱子编号";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label30);
            this.groupBox2.Controls.Add(this.label15);
            this.groupBox2.Controls.Add(this.label22);
            this.groupBox2.Controls.Add(this.receiveMessageBtn);
            this.groupBox2.Controls.Add(this.label23);
            this.groupBox2.Controls.Add(this.pickingCountBox);
            this.groupBox2.Controls.Add(this.rangeLimitToBox);
            this.groupBox2.Controls.Add(this.label14);
            this.groupBox2.Controls.Add(this.label24);
            this.groupBox2.Controls.Add(this.sendMessageBtn);
            this.groupBox2.Controls.Add(this.rangeLimitFromBox);
            this.groupBox2.Controls.Add(this.label12);
            this.groupBox2.Controls.Add(this.label25);
            this.groupBox2.Controls.Add(this.unitWeightBtn);
            this.groupBox2.Controls.Add(this.errorBox);
            this.groupBox2.Controls.Add(this.newBucketWeightBox);
            this.groupBox2.Controls.Add(this.label26);
            this.groupBox2.Controls.Add(this.unitWeightBox);
            this.groupBox2.Controls.Add(this.newBucketNoBox);
            this.groupBox2.Controls.Add(this.label11);
            this.groupBox2.Controls.Add(this.label13);
            this.groupBox2.Controls.Add(this.jobCountBox);
            this.groupBox2.Controls.Add(this.totalStockoutCountBox);
            this.groupBox2.Controls.Add(this.label10);
            this.groupBox2.Controls.Add(this.label9);
            this.groupBox2.Controls.Add(this.remainJobBox);
            this.groupBox2.Controls.Add(this.remainJobBtn);
            this.groupBox2.Controls.Add(this.jobTypeBox);
            this.groupBox2.Controls.Add(this.label8);
            this.groupBox2.Controls.Add(this.emptyBucketPositionBox);
            this.groupBox2.Controls.Add(this.cancelBtn);
            this.groupBox2.Controls.Add(this.label4);
            this.groupBox2.Controls.Add(this.setBtn);
            this.groupBox2.Location = new System.Drawing.Point(33, 305);
            this.groupBox2.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Padding = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.groupBox2.Size = new System.Drawing.Size(951, 310);
            this.groupBox2.TabIndex = 71;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "作业内容";
            // 
            // label30
            // 
            this.label30.AutoSize = true;
            this.label30.Location = new System.Drawing.Point(244, 232);
            this.label30.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label30.Name = "label30";
            this.label30.Size = new System.Drawing.Size(26, 18);
            this.label30.TabIndex = 74;
            this.label30.Text = "％";
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Location = new System.Drawing.Point(555, 105);
            this.label15.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(17, 18);
            this.label15.TabIndex = 86;
            this.label15.Text = "g";
            // 
            // label22
            // 
            this.label22.AutoSize = true;
            this.label22.Location = new System.Drawing.Point(674, 232);
            this.label22.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label22.Name = "label22";
            this.label22.Size = new System.Drawing.Size(26, 18);
            this.label22.TabIndex = 79;
            this.label22.Text = "％";
            // 
            // receiveMessageBtn
            // 
            this.receiveMessageBtn.Location = new System.Drawing.Point(294, 187);
            this.receiveMessageBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.receiveMessageBtn.Name = "receiveMessageBtn";
            this.receiveMessageBtn.Size = new System.Drawing.Size(182, 33);
            this.receiveMessageBtn.TabIndex = 7;
            this.receiveMessageBtn.Text = "计量器接收信息(F5)";
            this.receiveMessageBtn.UseVisualStyleBackColor = true;
            this.receiveMessageBtn.Click += new System.EventHandler(this.receiveMessageBtn_Click);
            // 
            // label23
            // 
            this.label23.AutoSize = true;
            this.label23.Location = new System.Drawing.Point(495, 232);
            this.label23.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label23.Name = "label23";
            this.label23.Size = new System.Drawing.Size(26, 18);
            this.label23.TabIndex = 78;
            this.label23.Text = "％";
            // 
            // pickingCountBox
            // 
            this.pickingCountBox.Location = new System.Drawing.Point(138, 192);
            this.pickingCountBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.pickingCountBox.Name = "pickingCountBox";
            this.pickingCountBox.ReadOnly = true;
            this.pickingCountBox.Size = new System.Drawing.Size(148, 27);
            this.pickingCountBox.TabIndex = 84;
            this.pickingCountBox.TabStop = false;
            // 
            // rangeLimitToBox
            // 
            this.rangeLimitToBox.Location = new System.Drawing.Point(563, 229);
            this.rangeLimitToBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.rangeLimitToBox.Name = "rangeLimitToBox";
            this.rangeLimitToBox.ReadOnly = true;
            this.rangeLimitToBox.Size = new System.Drawing.Size(103, 27);
            this.rangeLimitToBox.TabIndex = 77;
            this.rangeLimitToBox.TabStop = false;
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(33, 195);
            this.label14.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(98, 18);
            this.label14.TabIndex = 83;
            this.label14.Text = "计量后数量";
            // 
            // label24
            // 
            this.label24.AutoSize = true;
            this.label24.Location = new System.Drawing.Point(529, 232);
            this.label24.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label24.Name = "label24";
            this.label24.Size = new System.Drawing.Size(26, 18);
            this.label24.TabIndex = 76;
            this.label24.Text = "～";
            // 
            // sendMessageBtn
            // 
            this.sendMessageBtn.Location = new System.Drawing.Point(484, 150);
            this.sendMessageBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.sendMessageBtn.Name = "sendMessageBtn";
            this.sendMessageBtn.Size = new System.Drawing.Size(180, 33);
            this.sendMessageBtn.TabIndex = 6;
            this.sendMessageBtn.Text = "计量器发送信息(F4)";
            this.sendMessageBtn.UseVisualStyleBackColor = true;
            this.sendMessageBtn.Click += new System.EventHandler(this.sendMessageBtn_Click);
            // 
            // rangeLimitFromBox
            // 
            this.rangeLimitFromBox.Location = new System.Drawing.Point(384, 229);
            this.rangeLimitFromBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.rangeLimitFromBox.Name = "rangeLimitFromBox";
            this.rangeLimitFromBox.ReadOnly = true;
            this.rangeLimitFromBox.Size = new System.Drawing.Size(103, 27);
            this.rangeLimitFromBox.TabIndex = 75;
            this.rangeLimitFromBox.TabStop = false;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(450, 158);
            this.label12.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(26, 18);
            this.label12.TabIndex = 75;
            this.label12.Text = "Kg";
            // 
            // label25
            // 
            this.label25.AutoSize = true;
            this.label25.Location = new System.Drawing.Point(278, 232);
            this.label25.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label25.Name = "label25";
            this.label25.Size = new System.Drawing.Size(98, 18);
            this.label25.TabIndex = 74;
            this.label25.Text = "误差上下限";
            // 
            // unitWeightBtn
            // 
            this.unitWeightBtn.Location = new System.Drawing.Point(579, 97);
            this.unitWeightBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.unitWeightBtn.Name = "unitWeightBtn";
            this.unitWeightBtn.Size = new System.Drawing.Size(130, 33);
            this.unitWeightBtn.TabIndex = 4;
            this.unitWeightBtn.Text = "原单位变更";
            this.unitWeightBtn.UseVisualStyleBackColor = true;
            this.unitWeightBtn.Click += new System.EventHandler(this.unitWeightBtn_Click);
            // 
            // errorBox
            // 
            this.errorBox.Location = new System.Drawing.Point(138, 229);
            this.errorBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.errorBox.Name = "errorBox";
            this.errorBox.ReadOnly = true;
            this.errorBox.Size = new System.Drawing.Size(103, 27);
            this.errorBox.TabIndex = 73;
            this.errorBox.TabStop = false;
            // 
            // newBucketWeightBox
            // 
            this.newBucketWeightBox.Location = new System.Drawing.Point(294, 155);
            this.newBucketWeightBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.newBucketWeightBox.Name = "newBucketWeightBox";
            this.newBucketWeightBox.ReadOnly = true;
            this.newBucketWeightBox.Size = new System.Drawing.Size(148, 27);
            this.newBucketWeightBox.TabIndex = 74;
            this.newBucketWeightBox.TabStop = false;
            // 
            // label26
            // 
            this.label26.AutoSize = true;
            this.label26.Location = new System.Drawing.Point(69, 232);
            this.label26.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label26.Name = "label26";
            this.label26.Size = new System.Drawing.Size(62, 18);
            this.label26.TabIndex = 72;
            this.label26.Text = "误差率";
            // 
            // unitWeightBox
            // 
            this.unitWeightBox.Location = new System.Drawing.Point(415, 102);
            this.unitWeightBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.unitWeightBox.Name = "unitWeightBox";
            this.unitWeightBox.ReadOnly = true;
            this.unitWeightBox.Size = new System.Drawing.Size(140, 27);
            this.unitWeightBox.TabIndex = 80;
            this.unitWeightBox.TabStop = false;
            // 
            // newBucketNoBox
            // 
            this.newBucketNoBox.Location = new System.Drawing.Point(138, 155);
            this.newBucketNoBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.newBucketNoBox.MaxLength = 7;
            this.newBucketNoBox.Name = "newBucketNoBox";
            this.newBucketNoBox.Size = new System.Drawing.Size(148, 27);
            this.newBucketNoBox.TabIndex = 5;
            this.newBucketNoBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this.newBucketNoBox_KeyDown);
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(291, 105);
            this.label11.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(116, 18);
            this.label11.TabIndex = 79;
            this.label11.Text = "原单位检查数";
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(51, 158);
            this.label13.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(80, 18);
            this.label13.TabIndex = 72;
            this.label13.Text = "箱子编号";
            // 
            // jobCountBox
            // 
            this.jobCountBox.Font = new System.Drawing.Font("SimSun", 27F, System.Drawing.FontStyle.Bold);
            this.jobCountBox.Location = new System.Drawing.Point(138, 100);
            this.jobCountBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.jobCountBox.Name = "jobCountBox";
            this.jobCountBox.ReadOnly = true;
            this.jobCountBox.Size = new System.Drawing.Size(140, 49);
            this.jobCountBox.TabIndex = 76;
            this.jobCountBox.TabStop = false;
            // 
            // totalStockoutCountBox
            // 
            this.totalStockoutCountBox.Location = new System.Drawing.Point(610, 65);
            this.totalStockoutCountBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.totalStockoutCountBox.Name = "totalStockoutCountBox";
            this.totalStockoutCountBox.ReadOnly = true;
            this.totalStockoutCountBox.Size = new System.Drawing.Size(140, 27);
            this.totalStockoutCountBox.TabIndex = 78;
            this.totalStockoutCountBox.TabStop = false;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(50, 115);
            this.label10.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(80, 18);
            this.label10.TabIndex = 75;
            this.label10.Text = "需求数量";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(506, 68);
            this.label9.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(98, 18);
            this.label9.TabIndex = 77;
            this.label9.Text = "剩余出库数";
            // 
            // remainJobBox
            // 
            this.remainJobBox.Location = new System.Drawing.Point(406, 65);
            this.remainJobBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.remainJobBox.Name = "remainJobBox";
            this.remainJobBox.ReadOnly = true;
            this.remainJobBox.Size = new System.Drawing.Size(88, 27);
            this.remainJobBox.TabIndex = 76;
            this.remainJobBox.TabStop = false;
            // 
            // remainJobBtn
            // 
            this.remainJobBtn.Location = new System.Drawing.Point(286, 62);
            this.remainJobBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.remainJobBtn.Name = "remainJobBtn";
            this.remainJobBtn.Size = new System.Drawing.Size(112, 33);
            this.remainJobBtn.TabIndex = 3;
            this.remainJobBtn.Text = "剩余作业";
            this.remainJobBtn.UseVisualStyleBackColor = true;
            this.remainJobBtn.Click += new System.EventHandler(this.remainJobBtn_Click);
            // 
            // jobTypeBox
            // 
            this.jobTypeBox.Location = new System.Drawing.Point(138, 65);
            this.jobTypeBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.jobTypeBox.Name = "jobTypeBox";
            this.jobTypeBox.ReadOnly = true;
            this.jobTypeBox.Size = new System.Drawing.Size(140, 27);
            this.jobTypeBox.TabIndex = 74;
            this.jobTypeBox.TabStop = false;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(51, 68);
            this.label8.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(80, 18);
            this.label8.TabIndex = 73;
            this.label8.Text = "作业内容";
            // 
            // emptyBucketPositionBox
            // 
            this.emptyBucketPositionBox.Location = new System.Drawing.Point(139, 28);
            this.emptyBucketPositionBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.emptyBucketPositionBox.Name = "emptyBucketPositionBox";
            this.emptyBucketPositionBox.ReadOnly = true;
            this.emptyBucketPositionBox.Size = new System.Drawing.Size(386, 27);
            this.emptyBucketPositionBox.TabIndex = 30;
            this.emptyBucketPositionBox.TabStop = false;
            // 
            // cancelBtn
            // 
            this.cancelBtn.Location = new System.Drawing.Point(151, 266);
            this.cancelBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.cancelBtn.Name = "cancelBtn";
            this.cancelBtn.Size = new System.Drawing.Size(150, 32);
            this.cancelBtn.TabIndex = 9;
            this.cancelBtn.Text = "出库取消(F8)";
            this.cancelBtn.UseVisualStyleBackColor = true;
            this.cancelBtn.Click += new System.EventHandler(this.cancelBtn_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(15, 31);
            this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(116, 18);
            this.label4.TabIndex = 29;
            this.label4.Text = "空箱放置位置";
            // 
            // bucketMaintenanceBtn
            // 
            this.bucketMaintenanceBtn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.bucketMaintenanceBtn.Location = new System.Drawing.Point(13, 677);
            this.bucketMaintenanceBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.bucketMaintenanceBtn.Name = "bucketMaintenanceBtn";
            this.bucketMaintenanceBtn.Size = new System.Drawing.Size(143, 52);
            this.bucketMaintenanceBtn.TabIndex = 10;
            this.bucketMaintenanceBtn.Text = "箱子信息变更";
            this.bucketMaintenanceBtn.UseVisualStyleBackColor = true;
            this.bucketMaintenanceBtn.Click += new System.EventHandler(this.bucketMaintenanceBtn_Click);
            // 
            // datetimeLb
            // 
            this.datetimeLb.AutoSize = true;
            this.datetimeLb.Font = new System.Drawing.Font("SimSun", 12F);
            this.datetimeLb.Location = new System.Drawing.Point(867, 9);
            this.datetimeLb.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.datetimeLb.Name = "datetimeLb";
            this.datetimeLb.Size = new System.Drawing.Size(136, 16);
            this.datetimeLb.TabIndex = 72;
            this.datetimeLb.Text = "YYYY/MM/DD HH:MM";
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Interval = 1000;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // msgBox
            // 
            this.msgBox.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.msgBox.Location = new System.Drawing.Point(13, 640);
            this.msgBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.msgBox.Name = "msgBox";
            this.msgBox.ReadOnly = true;
            this.msgBox.Size = new System.Drawing.Size(990, 27);
            this.msgBox.TabIndex = 73;
            this.msgBox.TabStop = false;
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.label17.Location = new System.Drawing.Point(539, 49);
            this.label17.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(80, 18);
            this.label17.TabIndex = 66;
            this.label17.Text = "进入状态";
            // 
            // txtEnterStatus
            // 
            this.txtEnterStatus.BackColor = System.Drawing.SystemColors.Control;
            this.txtEnterStatus.Location = new System.Drawing.Point(627, 46);
            this.txtEnterStatus.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.txtEnterStatus.Name = "txtEnterStatus";
            this.txtEnterStatus.ReadOnly = true;
            this.txtEnterStatus.Size = new System.Drawing.Size(56, 27);
            this.txtEnterStatus.TabIndex = 67;
            this.txtEnterStatus.TabStop = false;
            this.txtEnterStatus.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // btnProhibitEntrance
            // 
            this.btnProhibitEntrance.Location = new System.Drawing.Point(691, 41);
            this.btnProhibitEntrance.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.btnProhibitEntrance.Name = "btnProhibitEntrance";
            this.btnProhibitEntrance.Size = new System.Drawing.Size(112, 33);
            this.btnProhibitEntrance.TabIndex = 2;
            this.btnProhibitEntrance.Text = "进入禁止";
            this.btnProhibitEntrance.UseVisualStyleBackColor = true;
            this.btnProhibitEntrance.Click += new System.EventHandler(this.btnProhibitEntrance_Click);
            // 
            // timer2
            // 
            this.timer2.Interval = 30000;
            this.timer2.Tick += new System.EventHandler(this.timer2_Tick);
            // 
            // Stockout
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 17F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1014, 739);
            this.ControlBox = false;
            this.Controls.Add(this.msgBox);
            this.Controls.Add(this.datetimeLb);
            this.Controls.Add(this.bucketMaintenanceBtn);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.btnProhibitEntrance);
            this.Controls.Add(this.jobStopBtn);
            this.Controls.Add(this.jobBeginBtn);
            this.Controls.Add(this.txtEnterStatus);
            this.Controls.Add(this.jobStatus);
            this.Controls.Add(this.label17);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.quitBtn);
            this.Controls.Add(this.uiClearBtn);
            this.Controls.Add(this.userNameBox);
            this.Controls.Add(this.userIdBox);
            this.Controls.Add(this.label1);
            this.Font = new System.Drawing.Font("SimSun", 13F);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.KeyPreview = true;
            this.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Stockout";
            this.ShowIcon = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "出库作业画面";
            this.Load += new System.EventHandler(this.Stockout_Load);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.Stockout_KeyDown);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button quitBtn;
        private System.Windows.Forms.Button uiClearBtn;
        private System.Windows.Forms.Button setBtn;
        private System.Windows.Forms.TextBox userNameBox;
        private System.Windows.Forms.TextBox userIdBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox jobStatus;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button jobBeginBtn;
        private System.Windows.Forms.Button jobStopBtn;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.TextBox colorCodeBox;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox ticketNoBox;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox originalBucketNoBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button bucketMaintenanceBtn;
        private System.Windows.Forms.Button cancelBtn;
        private System.Windows.Forms.TextBox emptyBucketPositionBox;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox remainJobBox;
        private System.Windows.Forms.Button remainJobBtn;
        private System.Windows.Forms.TextBox jobTypeBox;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox totalStockoutCountBox;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox jobCountBox;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.TextBox unitWeightBox;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Button unitWeightBtn;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox newBucketWeightBox;
        private System.Windows.Forms.TextBox newBucketNoBox;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Button sendMessageBtn;
        private System.Windows.Forms.Button receiveMessageBtn;
        private System.Windows.Forms.TextBox pickingCountBox;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.Label label22;
        private System.Windows.Forms.Label label23;
        private System.Windows.Forms.TextBox rangeLimitToBox;
        private System.Windows.Forms.Label label24;
        private System.Windows.Forms.TextBox rangeLimitFromBox;
        private System.Windows.Forms.Label label25;
        private System.Windows.Forms.TextBox errorBox;
        private System.Windows.Forms.Label label26;
        private System.Windows.Forms.Label datetimeLb;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.TextBox msgBox;
        private System.Windows.Forms.TextBox itemName2Box;
        private System.Windows.Forms.TextBox itemName3Box;
        private System.Windows.Forms.TextBox itemName1Box;
        private System.Windows.Forms.TextBox itemCodeBox;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.TextBox originalBucketWeightBox;
        private System.Windows.Forms.Label label30;
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.TextBox txtEnterStatus;
        private System.Windows.Forms.Button btnProhibitEntrance;
        private System.Windows.Forms.Timer timer2;
    }
}