namespace YkkWms
{
    partial class Stockin2
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
            this.label1 = new System.Windows.Forms.Label();
            this.userIdBox1 = new System.Windows.Forms.TextBox();
            this.userIdBox2 = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.bucketNoBox = new System.Windows.Forms.TextBox();
            this.bucketWeightBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.statusBox = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.ticketNoBox = new System.Windows.Forms.TextBox();
            this.itemCodeBox = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.itemNameBox1 = new System.Windows.Forms.TextBox();
            this.itemNameBox3 = new System.Windows.Forms.TextBox();
            this.itemNameBox2 = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.colorCodeBox = new System.Windows.Forms.TextBox();
            this.sectionBox = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.lineBox = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.planCountBox = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.planWeightBox = new System.Windows.Forms.TextBox();
            this.label11 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.unitWeightBox = new System.Windows.Forms.TextBox();
            this.label13 = new System.Windows.Forms.Label();
            this.measureFlagBox = new System.Windows.Forms.TextBox();
            this.unitWeightLoadBtn = new System.Windows.Forms.Button();
            this.errorBox1 = new System.Windows.Forms.TextBox();
            this.label15 = new System.Windows.Forms.Label();
            this.label16 = new System.Windows.Forms.Label();
            this.rangeLimitFromBox1 = new System.Windows.Forms.TextBox();
            this.label14 = new System.Windows.Forms.Label();
            this.rangeLimitToBox1 = new System.Windows.Forms.TextBox();
            this.label17 = new System.Windows.Forms.Label();
            this.label18 = new System.Windows.Forms.Label();
            this.label19 = new System.Windows.Forms.Label();
            this.memoBox = new System.Windows.Forms.TextBox();
            this.label28 = new System.Windows.Forms.Label();
            this.quitBtn = new System.Windows.Forms.Button();
            this.cancelBtn = new System.Windows.Forms.Button();
            this.setBtn = new System.Windows.Forms.Button();
            this.otherItemManagerBtn = new System.Windows.Forms.Button();
            this.msgBox = new System.Windows.Forms.TextBox();
            this.datetimeLb = new System.Windows.Forms.Label();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.label29 = new System.Windows.Forms.Label();
            this.checkCountBox = new System.Windows.Forms.NumericUpDown();
            this.panel2 = new System.Windows.Forms.Panel();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.label30 = new System.Windows.Forms.Label();
            this.chkReStockIn = new System.Windows.Forms.CheckBox();
            this.chkUsingPlasticBag = new System.Windows.Forms.CheckBox();
            ((System.ComponentModel.ISupportInitialize)(this.checkCountBox)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(78, 43);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(80, 18);
            this.label1.TabIndex = 0;
            this.label1.Text = "用户编号";
            // 
            // userIdBox1
            // 
            this.userIdBox1.Location = new System.Drawing.Point(161, 39);
            this.userIdBox1.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.userIdBox1.Name = "userIdBox1";
            this.userIdBox1.ReadOnly = true;
            this.userIdBox1.Size = new System.Drawing.Size(148, 27);
            this.userIdBox1.TabIndex = 1;
            this.userIdBox1.TabStop = false;
            // 
            // userIdBox2
            // 
            this.userIdBox2.Location = new System.Drawing.Point(320, 39);
            this.userIdBox2.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.userIdBox2.Name = "userIdBox2";
            this.userIdBox2.ReadOnly = true;
            this.userIdBox2.Size = new System.Drawing.Size(519, 27);
            this.userIdBox2.TabIndex = 2;
            this.userIdBox2.TabStop = false;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(78, 82);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(80, 18);
            this.label2.TabIndex = 3;
            this.label2.Text = "箱子编号";
            // 
            // bucketNoBox
            // 
            this.bucketNoBox.Location = new System.Drawing.Point(161, 78);
            this.bucketNoBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.bucketNoBox.MaxLength = 7;
            this.bucketNoBox.Name = "bucketNoBox";
            this.bucketNoBox.Size = new System.Drawing.Size(148, 27);
            this.bucketNoBox.TabIndex = 1;
            this.bucketNoBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.bucketNoBox_KeyPress);
            // 
            // bucketWeightBox
            // 
            this.bucketWeightBox.Location = new System.Drawing.Point(320, 78);
            this.bucketWeightBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.bucketWeightBox.Name = "bucketWeightBox";
            this.bucketWeightBox.ReadOnly = true;
            this.bucketWeightBox.Size = new System.Drawing.Size(148, 27);
            this.bucketWeightBox.TabIndex = 5;
            this.bucketWeightBox.TabStop = false;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(478, 82);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(26, 18);
            this.label3.TabIndex = 6;
            this.label3.Text = "Kg";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("SimSun", 12F);
            this.label4.Location = new System.Drawing.Point(576, 82);
            this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(72, 16);
            this.label4.TabIndex = 7;
            this.label4.Text = "状态显示";
            // 
            // statusBox
            // 
            this.statusBox.Font = new System.Drawing.Font("SimSun", 19F);
            this.statusBox.Location = new System.Drawing.Point(580, 110);
            this.statusBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.statusBox.Name = "statusBox";
            this.statusBox.ReadOnly = true;
            this.statusBox.Size = new System.Drawing.Size(259, 36);
            this.statusBox.TabIndex = 8;
            this.statusBox.TabStop = false;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(78, 125);
            this.label5.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(80, 18);
            this.label5.TabIndex = 9;
            this.label5.Text = "生产票号";
            // 
            // ticketNoBox
            // 
            this.ticketNoBox.Location = new System.Drawing.Point(161, 120);
            this.ticketNoBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.ticketNoBox.MaxLength = 10;
            this.ticketNoBox.Name = "ticketNoBox";
            this.ticketNoBox.Size = new System.Drawing.Size(148, 27);
            this.ticketNoBox.TabIndex = 2;
            this.ticketNoBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this.ticketNoBox_KeyDown);
            // 
            // itemCodeBox
            // 
            this.itemCodeBox.Location = new System.Drawing.Point(161, 170);
            this.itemCodeBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.itemCodeBox.Name = "itemCodeBox";
            this.itemCodeBox.ReadOnly = true;
            this.itemCodeBox.Size = new System.Drawing.Size(148, 27);
            this.itemCodeBox.TabIndex = 11;
            this.itemCodeBox.TabStop = false;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(114, 175);
            this.label6.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(44, 18);
            this.label6.TabIndex = 12;
            this.label6.Text = "品名";
            // 
            // itemNameBox1
            // 
            this.itemNameBox1.Location = new System.Drawing.Point(320, 170);
            this.itemNameBox1.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.itemNameBox1.Name = "itemNameBox1";
            this.itemNameBox1.ReadOnly = true;
            this.itemNameBox1.Size = new System.Drawing.Size(409, 27);
            this.itemNameBox1.TabIndex = 13;
            this.itemNameBox1.TabStop = false;
            // 
            // itemNameBox3
            // 
            this.itemNameBox3.Location = new System.Drawing.Point(320, 243);
            this.itemNameBox3.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.itemNameBox3.Name = "itemNameBox3";
            this.itemNameBox3.ReadOnly = true;
            this.itemNameBox3.Size = new System.Drawing.Size(409, 27);
            this.itemNameBox3.TabIndex = 14;
            this.itemNameBox3.TabStop = false;
            // 
            // itemNameBox2
            // 
            this.itemNameBox2.Location = new System.Drawing.Point(320, 206);
            this.itemNameBox2.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.itemNameBox2.Name = "itemNameBox2";
            this.itemNameBox2.ReadOnly = true;
            this.itemNameBox2.Size = new System.Drawing.Size(409, 27);
            this.itemNameBox2.TabIndex = 15;
            this.itemNameBox2.TabStop = false;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(78, 294);
            this.label7.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(80, 18);
            this.label7.TabIndex = 16;
            this.label7.Text = "颜色代码";
            // 
            // colorCodeBox
            // 
            this.colorCodeBox.Location = new System.Drawing.Point(160, 290);
            this.colorCodeBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.colorCodeBox.Name = "colorCodeBox";
            this.colorCodeBox.ReadOnly = true;
            this.colorCodeBox.Size = new System.Drawing.Size(148, 27);
            this.colorCodeBox.TabIndex = 17;
            this.colorCodeBox.TabStop = false;
            // 
            // sectionBox
            // 
            this.sectionBox.Location = new System.Drawing.Point(425, 290);
            this.sectionBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.sectionBox.Name = "sectionBox";
            this.sectionBox.ReadOnly = true;
            this.sectionBox.Size = new System.Drawing.Size(148, 27);
            this.sectionBox.TabIndex = 19;
            this.sectionBox.TabStop = false;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(373, 294);
            this.label8.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(44, 18);
            this.label8.TabIndex = 18;
            this.label8.Text = "部门";
            // 
            // lineBox
            // 
            this.lineBox.Location = new System.Drawing.Point(691, 290);
            this.lineBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.lineBox.Name = "lineBox";
            this.lineBox.ReadOnly = true;
            this.lineBox.Size = new System.Drawing.Size(148, 27);
            this.lineBox.TabIndex = 21;
            this.lineBox.TabStop = false;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(626, 294);
            this.label9.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(62, 18);
            this.label9.TabIndex = 20;
            this.label9.Text = "生产线";
            // 
            // planCountBox
            // 
            this.planCountBox.Location = new System.Drawing.Point(160, 328);
            this.planCountBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.planCountBox.Name = "planCountBox";
            this.planCountBox.ReadOnly = true;
            this.planCountBox.Size = new System.Drawing.Size(148, 27);
            this.planCountBox.TabIndex = 23;
            this.planCountBox.TabStop = false;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(78, 333);
            this.label10.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(80, 18);
            this.label10.TabIndex = 22;
            this.label10.Text = "预定数量";
            // 
            // planWeightBox
            // 
            this.planWeightBox.Location = new System.Drawing.Point(425, 328);
            this.planWeightBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.planWeightBox.Name = "planWeightBox";
            this.planWeightBox.ReadOnly = true;
            this.planWeightBox.Size = new System.Drawing.Size(148, 27);
            this.planWeightBox.TabIndex = 25;
            this.planWeightBox.TabStop = false;
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(337, 333);
            this.label11.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(80, 18);
            this.label11.TabIndex = 24;
            this.label11.Text = "预定重量";
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(584, 333);
            this.label12.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(26, 18);
            this.label12.TabIndex = 26;
            this.label12.Text = "Kg";
            // 
            // unitWeightBox
            // 
            this.unitWeightBox.Location = new System.Drawing.Point(161, 377);
            this.unitWeightBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.unitWeightBox.Name = "unitWeightBox";
            this.unitWeightBox.ReadOnly = true;
            this.unitWeightBox.Size = new System.Drawing.Size(148, 27);
            this.unitWeightBox.TabIndex = 28;
            this.unitWeightBox.TabStop = false;
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(60, 382);
            this.label13.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(98, 18);
            this.label13.TabIndex = 27;
            this.label13.Text = "原单位重量";
            // 
            // measureFlagBox
            // 
            this.measureFlagBox.Location = new System.Drawing.Point(346, 377);
            this.measureFlagBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.measureFlagBox.Name = "measureFlagBox";
            this.measureFlagBox.ReadOnly = true;
            this.measureFlagBox.Size = new System.Drawing.Size(52, 27);
            this.measureFlagBox.TabIndex = 29;
            this.measureFlagBox.TabStop = false;
            // 
            // unitWeightLoadBtn
            // 
            this.unitWeightLoadBtn.Location = new System.Drawing.Point(410, 374);
            this.unitWeightLoadBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.unitWeightLoadBtn.Name = "unitWeightLoadBtn";
            this.unitWeightLoadBtn.Size = new System.Drawing.Size(194, 33);
            this.unitWeightLoadBtn.TabIndex = 3;
            this.unitWeightLoadBtn.Text = "读取原单位重量(F4)";
            this.unitWeightLoadBtn.UseVisualStyleBackColor = true;
            this.unitWeightLoadBtn.Click += new System.EventHandler(this.unitWeightLoadBtn_Click);
            // 
            // errorBox1
            // 
            this.errorBox1.Location = new System.Drawing.Point(409, 416);
            this.errorBox1.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.errorBox1.Name = "errorBox1";
            this.errorBox1.ReadOnly = true;
            this.errorBox1.Size = new System.Drawing.Size(95, 27);
            this.errorBox1.TabIndex = 34;
            this.errorBox1.TabStop = false;
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Location = new System.Drawing.Point(346, 420);
            this.label15.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(62, 18);
            this.label15.TabIndex = 33;
            this.label15.Text = "误差率";
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Location = new System.Drawing.Point(42, 420);
            this.label16.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(116, 18);
            this.label16.TabIndex = 31;
            this.label16.Text = "原单位检查数";
            // 
            // rangeLimitFromBox1
            // 
            this.rangeLimitFromBox1.Location = new System.Drawing.Point(645, 416);
            this.rangeLimitFromBox1.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.rangeLimitFromBox1.Name = "rangeLimitFromBox1";
            this.rangeLimitFromBox1.ReadOnly = true;
            this.rangeLimitFromBox1.Size = new System.Drawing.Size(103, 27);
            this.rangeLimitFromBox1.TabIndex = 36;
            this.rangeLimitFromBox1.TabStop = false;
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(539, 420);
            this.label14.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(98, 18);
            this.label14.TabIndex = 35;
            this.label14.Text = "误差上下限";
            // 
            // rangeLimitToBox1
            // 
            this.rangeLimitToBox1.Location = new System.Drawing.Point(828, 416);
            this.rangeLimitToBox1.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.rangeLimitToBox1.Name = "rangeLimitToBox1";
            this.rangeLimitToBox1.ReadOnly = true;
            this.rangeLimitToBox1.Size = new System.Drawing.Size(103, 27);
            this.rangeLimitToBox1.TabIndex = 38;
            this.rangeLimitToBox1.TabStop = false;
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.label17.Location = new System.Drawing.Point(793, 420);
            this.label17.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(26, 18);
            this.label17.TabIndex = 37;
            this.label17.Text = "～";
            // 
            // label18
            // 
            this.label18.AutoSize = true;
            this.label18.Location = new System.Drawing.Point(759, 420);
            this.label18.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label18.Name = "label18";
            this.label18.Size = new System.Drawing.Size(26, 18);
            this.label18.TabIndex = 39;
            this.label18.Text = "％";
            // 
            // label19
            // 
            this.label19.AutoSize = true;
            this.label19.Location = new System.Drawing.Point(942, 420);
            this.label19.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label19.Name = "label19";
            this.label19.Size = new System.Drawing.Size(26, 18);
            this.label19.TabIndex = 40;
            this.label19.Text = "％";
            // 
            // memoBox
            // 
            this.memoBox.Location = new System.Drawing.Point(160, 453);
            this.memoBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.memoBox.MaxLength = 100;
            this.memoBox.Name = "memoBox";
            this.memoBox.Size = new System.Drawing.Size(816, 27);
            this.memoBox.TabIndex = 5;
            // 
            // label28
            // 
            this.label28.AutoSize = true;
            this.label28.Location = new System.Drawing.Point(114, 457);
            this.label28.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label28.Name = "label28";
            this.label28.Size = new System.Drawing.Size(44, 18);
            this.label28.TabIndex = 55;
            this.label28.Text = "记录";
            // 
            // quitBtn
            // 
            this.quitBtn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.quitBtn.Location = new System.Drawing.Point(887, 680);
            this.quitBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.quitBtn.Name = "quitBtn";
            this.quitBtn.Size = new System.Drawing.Size(111, 52);
            this.quitBtn.TabIndex = 11;
            this.quitBtn.Text = "退出(F12)";
            this.quitBtn.UseVisualStyleBackColor = true;
            this.quitBtn.Click += new System.EventHandler(this.quitBtn_Click);
            // 
            // cancelBtn
            // 
            this.cancelBtn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.cancelBtn.Location = new System.Drawing.Point(768, 680);
            this.cancelBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.cancelBtn.Name = "cancelBtn";
            this.cancelBtn.Size = new System.Drawing.Size(111, 52);
            this.cancelBtn.TabIndex = 10;
            this.cancelBtn.Text = "取消(F9)";
            this.cancelBtn.UseVisualStyleBackColor = true;
            this.cancelBtn.Click += new System.EventHandler(this.cancelBtn_Click);
            // 
            // setBtn
            // 
            this.setBtn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.setBtn.Location = new System.Drawing.Point(6, 680);
            this.setBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.setBtn.Name = "setBtn";
            this.setBtn.Size = new System.Drawing.Size(125, 52);
            this.setBtn.TabIndex = 6;
            this.setBtn.Text = "确定(F2)";
            this.setBtn.UseVisualStyleBackColor = true;
            this.setBtn.Click += new System.EventHandler(this.setBtn_Click);
            // 
            // otherItemManagerBtn
            // 
            this.otherItemManagerBtn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.otherItemManagerBtn.Location = new System.Drawing.Point(606, 680);
            this.otherItemManagerBtn.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.otherItemManagerBtn.Name = "otherItemManagerBtn";
            this.otherItemManagerBtn.Size = new System.Drawing.Size(125, 52);
            this.otherItemManagerBtn.TabIndex = 9;
            this.otherItemManagerBtn.Text = "管理外品";
            this.otherItemManagerBtn.UseVisualStyleBackColor = true;
            this.otherItemManagerBtn.Click += new System.EventHandler(this.otherItemManagerBtn_Click);
            // 
            // msgBox
            // 
            this.msgBox.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.msgBox.Location = new System.Drawing.Point(6, 643);
            this.msgBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.msgBox.Name = "msgBox";
            this.msgBox.ReadOnly = true;
            this.msgBox.Size = new System.Drawing.Size(992, 27);
            this.msgBox.TabIndex = 48;
            this.msgBox.TabStop = false;
            // 
            // datetimeLb
            // 
            this.datetimeLb.AutoSize = true;
            this.datetimeLb.Font = new System.Drawing.Font("SimSun", 12F);
            this.datetimeLb.Location = new System.Drawing.Point(867, 9);
            this.datetimeLb.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.datetimeLb.Name = "datetimeLb";
            this.datetimeLb.Size = new System.Drawing.Size(136, 16);
            this.datetimeLb.TabIndex = 7;
            this.datetimeLb.Text = "YYYY/MM/DD HH:MM";
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Interval = 1000;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // label29
            // 
            this.label29.AutoSize = true;
            this.label29.Location = new System.Drawing.Point(311, 382);
            this.label29.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label29.Name = "label29";
            this.label29.Size = new System.Drawing.Size(17, 18);
            this.label29.TabIndex = 62;
            this.label29.Text = "g";
            // 
            // checkCountBox
            // 
            this.checkCountBox.InterceptArrowKeys = false;
            this.checkCountBox.Location = new System.Drawing.Point(161, 416);
            this.checkCountBox.Maximum = new decimal(new int[] {
            999999999,
            0,
            0,
            0});
            this.checkCountBox.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.checkCountBox.Name = "checkCountBox";
            this.checkCountBox.Size = new System.Drawing.Size(176, 27);
            this.checkCountBox.TabIndex = 4;
            this.checkCountBox.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.checkCountBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.checkCountBox_KeyPress);
            // 
            // panel2
            // 
            this.panel2.Location = new System.Drawing.Point(307, 415);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(30, 30);
            this.panel2.TabIndex = 66;
            // 
            // button1
            // 
            this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.button1.Location = new System.Drawing.Point(301, 680);
            this.button1.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(143, 52);
            this.button1.TabIndex = 7;
            this.button1.Text = "传送带起动(F5)";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.button2.Location = new System.Drawing.Point(453, 680);
            this.button2.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(143, 52);
            this.button2.TabIndex = 8;
            this.button2.Text = "传送带停止(F6)";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // label30
            // 
            this.label30.AutoSize = true;
            this.label30.Location = new System.Drawing.Point(505, 420);
            this.label30.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label30.Name = "label30";
            this.label30.Size = new System.Drawing.Size(26, 18);
            this.label30.TabIndex = 67;
            this.label30.Text = "％";
            // 
            // chkReStockIn
            // 
            this.chkReStockIn.AutoSize = true;
            this.chkReStockIn.Location = new System.Drawing.Point(176, 680);
            this.chkReStockIn.Name = "chkReStockIn";
            this.chkReStockIn.Size = new System.Drawing.Size(81, 22);
            this.chkReStockIn.TabIndex = 69;
            this.chkReStockIn.Text = "再入库";
            this.chkReStockIn.UseVisualStyleBackColor = true;
            // 
            // chkUsingPlasticBag
            // 
            this.chkUsingPlasticBag.AutoSize = true;
            this.chkUsingPlasticBag.Location = new System.Drawing.Point(631, 378);
            this.chkUsingPlasticBag.Name = "chkUsingPlasticBag";
            this.chkUsingPlasticBag.Size = new System.Drawing.Size(117, 22);
            this.chkUsingPlasticBag.TabIndex = 70;
            this.chkUsingPlasticBag.Text = "塑料袋使用";
            this.chkUsingPlasticBag.UseVisualStyleBackColor = true;
            // 
            // Stockin2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 17F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1014, 739);
            this.ControlBox = false;
            this.Controls.Add(this.chkUsingPlasticBag);
            this.Controls.Add(this.chkReStockIn);
            this.Controls.Add(this.label30);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.checkCountBox);
            this.Controls.Add(this.label29);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.otherItemManagerBtn);
            this.Controls.Add(this.quitBtn);
            this.Controls.Add(this.cancelBtn);
            this.Controls.Add(this.setBtn);
            this.Controls.Add(this.memoBox);
            this.Controls.Add(this.label28);
            this.Controls.Add(this.msgBox);
            this.Controls.Add(this.label19);
            this.Controls.Add(this.label18);
            this.Controls.Add(this.rangeLimitToBox1);
            this.Controls.Add(this.label17);
            this.Controls.Add(this.rangeLimitFromBox1);
            this.Controls.Add(this.label14);
            this.Controls.Add(this.errorBox1);
            this.Controls.Add(this.label15);
            this.Controls.Add(this.label16);
            this.Controls.Add(this.unitWeightLoadBtn);
            this.Controls.Add(this.measureFlagBox);
            this.Controls.Add(this.unitWeightBox);
            this.Controls.Add(this.label13);
            this.Controls.Add(this.label12);
            this.Controls.Add(this.planWeightBox);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.planCountBox);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.lineBox);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.sectionBox);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.colorCodeBox);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.itemNameBox2);
            this.Controls.Add(this.itemNameBox3);
            this.Controls.Add(this.itemNameBox1);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.itemCodeBox);
            this.Controls.Add(this.ticketNoBox);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.statusBox);
            this.Controls.Add(this.datetimeLb);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.bucketWeightBox);
            this.Controls.Add(this.bucketNoBox);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.userIdBox2);
            this.Controls.Add(this.userIdBox1);
            this.Controls.Add(this.label1);
            this.Font = new System.Drawing.Font("SimSun", 13F);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.KeyPreview = true;
            this.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Stockin2";
            this.ShowIcon = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "入库设定2";
            this.Load += new System.EventHandler(this.Stockin1_Load);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.Stockin1_KeyDown);
            ((System.ComponentModel.ISupportInitialize)(this.checkCountBox)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox userIdBox1;
        private System.Windows.Forms.TextBox userIdBox2;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox bucketNoBox;
        private System.Windows.Forms.TextBox bucketWeightBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox statusBox;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox ticketNoBox;
        private System.Windows.Forms.TextBox itemCodeBox;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox itemNameBox1;
        private System.Windows.Forms.TextBox itemNameBox3;
        private System.Windows.Forms.TextBox itemNameBox2;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox colorCodeBox;
        private System.Windows.Forms.TextBox sectionBox;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox lineBox;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox planCountBox;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.TextBox planWeightBox;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox unitWeightBox;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.TextBox measureFlagBox;
        private System.Windows.Forms.Button unitWeightLoadBtn;
        private System.Windows.Forms.TextBox errorBox1;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.TextBox rangeLimitFromBox1;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.TextBox rangeLimitToBox1;
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.Label label18;
        private System.Windows.Forms.Label label19;
        private System.Windows.Forms.TextBox memoBox;
        private System.Windows.Forms.Label label28;
        private System.Windows.Forms.Button quitBtn;
        private System.Windows.Forms.Button cancelBtn;
        private System.Windows.Forms.Button setBtn;
        private System.Windows.Forms.Button otherItemManagerBtn;
        private System.Windows.Forms.TextBox msgBox;
        private System.Windows.Forms.Label datetimeLb;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.Label label29;
        private System.Windows.Forms.NumericUpDown checkCountBox;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Label label30;
        private System.Windows.Forms.CheckBox chkReStockIn;
        private System.Windows.Forms.CheckBox chkUsingPlasticBag;
    }
}