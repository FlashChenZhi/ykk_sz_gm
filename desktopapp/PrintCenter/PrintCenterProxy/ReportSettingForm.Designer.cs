namespace PrintCenterProxy
{
    partial class ReportSettingForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ReportSettingForm));
            this.btnSave = new System.Windows.Forms.Button();
            this.btnClose = new System.Windows.Forms.Button();
            this.label10 = new System.Windows.Forms.Label();
            this.cmbPrinter = new System.Windows.Forms.ComboBox();
            this.grpPrinter = new System.Windows.Forms.GroupBox();
            this.btnSelectDefault = new System.Windows.Forms.Button();
            this.btnSaveAsDefault = new System.Windows.Forms.Button();
            this.grpPaper = new System.Windows.Forms.GroupBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.txtPaperHeight = new System.Windows.Forms.TextBox();
            this.txtPaperWidth = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.cmbPaper = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.grpOrientation = new System.Windows.Forms.GroupBox();
            this.radVertical = new System.Windows.Forms.RadioButton();
            this.radHorizontal = new System.Windows.Forms.RadioButton();
            this.grpMargin = new System.Windows.Forms.GroupBox();
            this.nudMarginBottom = new System.Windows.Forms.NumericUpDown();
            this.nudMarginTop = new System.Windows.Forms.NumericUpDown();
            this.nudMarginRight = new System.Windows.Forms.NumericUpDown();
            this.nudMarginLeft = new System.Windows.Forms.NumericUpDown();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.lstvReportName = new System.Windows.Forms.ListView();
            this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
            this.printerError = new System.Windows.Forms.ErrorProvider(this.components);
            this.paperError = new System.Windows.Forms.ErrorProvider(this.components);
            this.btnAdd = new System.Windows.Forms.Button();
            this.grpReportName = new System.Windows.Forms.GroupBox();
            this.cmbReportName = new System.Windows.Forms.ComboBox();
            this.btnDelete = new System.Windows.Forms.Button();
            this.lblWarning = new System.Windows.Forms.Label();
            this.grpPrinter.SuspendLayout();
            this.grpPaper.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.grpOrientation.SuspendLayout();
            this.grpMargin.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudMarginBottom)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudMarginTop)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudMarginRight)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudMarginLeft)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.printerError)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.paperError)).BeginInit();
            this.grpReportName.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnSave
            // 
            this.btnSave.Location = new System.Drawing.Point(535, 365);
            this.btnSave.Name = "btnSave";
            this.btnSave.Size = new System.Drawing.Size(75, 23);
            this.btnSave.TabIndex = 1;
            this.btnSave.Text = "保存(&S)";
            this.btnSave.UseVisualStyleBackColor = true;
            this.btnSave.Click += new System.EventHandler(this.btnSave_Click);
            // 
            // btnClose
            // 
            this.btnClose.Location = new System.Drawing.Point(619, 365);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(75, 23);
            this.btnClose.TabIndex = 2;
            this.btnClose.Text = "关闭(&C)";
            this.btnClose.UseVisualStyleBackColor = true;
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(18, 26);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(35, 12);
            this.label10.TabIndex = 2;
            this.label10.Text = "名称:";
            // 
            // cmbPrinter
            // 
            this.cmbPrinter.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbPrinter.FormattingEnabled = true;
            this.cmbPrinter.Location = new System.Drawing.Point(65, 22);
            this.cmbPrinter.Name = "cmbPrinter";
            this.cmbPrinter.Size = new System.Drawing.Size(279, 20);
            this.cmbPrinter.TabIndex = 3;
            this.cmbPrinter.SelectedIndexChanged += new System.EventHandler(this.cmbPrinter_SelectedIndexChanged);
            this.cmbPrinter.TextChanged += new System.EventHandler(this.cmbPrinter_TextChanged);
            // 
            // grpPrinter
            // 
            this.grpPrinter.Controls.Add(this.lblWarning);
            this.grpPrinter.Controls.Add(this.btnSelectDefault);
            this.grpPrinter.Controls.Add(this.cmbPrinter);
            this.grpPrinter.Controls.Add(this.label10);
            this.grpPrinter.Controls.Add(this.btnSaveAsDefault);
            this.grpPrinter.Location = new System.Drawing.Point(331, 12);
            this.grpPrinter.Name = "grpPrinter";
            this.grpPrinter.Size = new System.Drawing.Size(363, 82);
            this.grpPrinter.TabIndex = 7;
            this.grpPrinter.TabStop = false;
            this.grpPrinter.Text = "打印机";
            // 
            // btnSelectDefault
            // 
            this.btnSelectDefault.Location = new System.Drawing.Point(259, 48);
            this.btnSelectDefault.Name = "btnSelectDefault";
            this.btnSelectDefault.Size = new System.Drawing.Size(85, 23);
            this.btnSelectDefault.TabIndex = 14;
            this.btnSelectDefault.Text = "选择默认(&C)";
            this.btnSelectDefault.UseVisualStyleBackColor = true;
            this.btnSelectDefault.Click += new System.EventHandler(this.btnSelectDefault_Click);
            // 
            // btnSaveAsDefault
            // 
            this.btnSaveAsDefault.Location = new System.Drawing.Point(168, 48);
            this.btnSaveAsDefault.Name = "btnSaveAsDefault";
            this.btnSaveAsDefault.Size = new System.Drawing.Size(85, 23);
            this.btnSaveAsDefault.TabIndex = 1;
            this.btnSaveAsDefault.Text = "设为默认(&D)";
            this.btnSaveAsDefault.UseVisualStyleBackColor = true;
            this.btnSaveAsDefault.Click += new System.EventHandler(this.btnSaveAsDefault_Click);
            // 
            // grpPaper
            // 
            this.grpPaper.Controls.Add(this.groupBox1);
            this.grpPaper.Controls.Add(this.cmbPaper);
            this.grpPaper.Controls.Add(this.label1);
            this.grpPaper.Location = new System.Drawing.Point(331, 98);
            this.grpPaper.Name = "grpPaper";
            this.grpPaper.Size = new System.Drawing.Size(363, 119);
            this.grpPaper.TabIndex = 8;
            this.grpPaper.TabStop = false;
            this.grpPaper.Text = "纸张";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.txtPaperHeight);
            this.groupBox1.Controls.Add(this.txtPaperWidth);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.label5);
            this.groupBox1.Location = new System.Drawing.Point(12, 46);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(342, 57);
            this.groupBox1.TabIndex = 10;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "大小";
            // 
            // txtPaperHeight
            // 
            this.txtPaperHeight.Location = new System.Drawing.Point(223, 18);
            this.txtPaperHeight.Name = "txtPaperHeight";
            this.txtPaperHeight.ReadOnly = true;
            this.txtPaperHeight.Size = new System.Drawing.Size(67, 21);
            this.txtPaperHeight.TabIndex = 9;
            // 
            // txtPaperWidth
            // 
            this.txtPaperWidth.Location = new System.Drawing.Point(87, 18);
            this.txtPaperWidth.Name = "txtPaperWidth";
            this.txtPaperWidth.ReadOnly = true;
            this.txtPaperWidth.Size = new System.Drawing.Size(67, 21);
            this.txtPaperWidth.TabIndex = 8;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(52, 22);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(35, 12);
            this.label4.TabIndex = 6;
            this.label4.Text = "宽度:";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(188, 22);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(35, 12);
            this.label5.TabIndex = 7;
            this.label5.Text = "高度:";
            // 
            // cmbPaper
            // 
            this.cmbPaper.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbPaper.FormattingEnabled = true;
            this.cmbPaper.Location = new System.Drawing.Point(65, 20);
            this.cmbPaper.Name = "cmbPaper";
            this.cmbPaper.Size = new System.Drawing.Size(279, 20);
            this.cmbPaper.TabIndex = 2;
            this.cmbPaper.SelectedIndexChanged += new System.EventHandler(this.cmbPaper_SelectedIndexChanged);
            this.cmbPaper.TextChanged += new System.EventHandler(this.cmbPaper_TextChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(18, 23);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(35, 12);
            this.label1.TabIndex = 1;
            this.label1.Text = "类型:";
            // 
            // grpOrientation
            // 
            this.grpOrientation.Controls.Add(this.radVertical);
            this.grpOrientation.Controls.Add(this.radHorizontal);
            this.grpOrientation.Location = new System.Drawing.Point(331, 221);
            this.grpOrientation.Name = "grpOrientation";
            this.grpOrientation.Size = new System.Drawing.Size(113, 117);
            this.grpOrientation.TabIndex = 9;
            this.grpOrientation.TabStop = false;
            this.grpOrientation.Text = "打印方向";
            // 
            // radVertical
            // 
            this.radVertical.AutoSize = true;
            this.radVertical.Checked = true;
            this.radVertical.Location = new System.Drawing.Point(24, 33);
            this.radVertical.Name = "radVertical";
            this.radVertical.Size = new System.Drawing.Size(65, 16);
            this.radVertical.TabIndex = 1;
            this.radVertical.TabStop = true;
            this.radVertical.Text = "垂直(&V)";
            this.radVertical.UseVisualStyleBackColor = true;
            // 
            // radHorizontal
            // 
            this.radHorizontal.AutoSize = true;
            this.radHorizontal.Location = new System.Drawing.Point(24, 68);
            this.radHorizontal.Name = "radHorizontal";
            this.radHorizontal.Size = new System.Drawing.Size(65, 16);
            this.radHorizontal.TabIndex = 0;
            this.radHorizontal.Text = "水平(&H)";
            this.radHorizontal.UseVisualStyleBackColor = true;
            // 
            // grpMargin
            // 
            this.grpMargin.Controls.Add(this.nudMarginBottom);
            this.grpMargin.Controls.Add(this.nudMarginTop);
            this.grpMargin.Controls.Add(this.nudMarginRight);
            this.grpMargin.Controls.Add(this.nudMarginLeft);
            this.grpMargin.Controls.Add(this.label2);
            this.grpMargin.Controls.Add(this.label3);
            this.grpMargin.Controls.Add(this.label11);
            this.grpMargin.Controls.Add(this.label12);
            this.grpMargin.Location = new System.Drawing.Point(450, 221);
            this.grpMargin.Name = "grpMargin";
            this.grpMargin.Size = new System.Drawing.Size(244, 117);
            this.grpMargin.TabIndex = 10;
            this.grpMargin.TabStop = false;
            this.grpMargin.Text = "页边距";
            // 
            // nudMarginBottom
            // 
            this.nudMarginBottom.DecimalPlaces = 2;
            this.nudMarginBottom.Location = new System.Drawing.Point(175, 66);
            this.nudMarginBottom.Name = "nudMarginBottom";
            this.nudMarginBottom.Size = new System.Drawing.Size(57, 21);
            this.nudMarginBottom.TabIndex = 20;
            // 
            // nudMarginTop
            // 
            this.nudMarginTop.DecimalPlaces = 2;
            this.nudMarginTop.Location = new System.Drawing.Point(60, 66);
            this.nudMarginTop.Name = "nudMarginTop";
            this.nudMarginTop.Size = new System.Drawing.Size(57, 21);
            this.nudMarginTop.TabIndex = 19;
            // 
            // nudMarginRight
            // 
            this.nudMarginRight.DecimalPlaces = 2;
            this.nudMarginRight.Location = new System.Drawing.Point(175, 29);
            this.nudMarginRight.Name = "nudMarginRight";
            this.nudMarginRight.Size = new System.Drawing.Size(57, 21);
            this.nudMarginRight.TabIndex = 18;
            // 
            // nudMarginLeft
            // 
            this.nudMarginLeft.DecimalPlaces = 2;
            this.nudMarginLeft.Location = new System.Drawing.Point(60, 29);
            this.nudMarginLeft.Name = "nudMarginLeft";
            this.nudMarginLeft.Size = new System.Drawing.Size(57, 21);
            this.nudMarginLeft.TabIndex = 17;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(128, 69);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(47, 12);
            this.label2.TabIndex = 15;
            this.label2.Text = "下边距:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(12, 69);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(47, 12);
            this.label3.TabIndex = 13;
            this.label3.Text = "上边距:";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(128, 34);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(47, 12);
            this.label11.TabIndex = 11;
            this.label11.Text = "右边距:";
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(12, 34);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(47, 12);
            this.label12.TabIndex = 9;
            this.label12.Text = "左边距:";
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(13, 365);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(137, 12);
            this.label13.TabIndex = 11;
            this.label13.Text = "* 所有尺寸单位均为厘米";
            // 
            // lstvReportName
            // 
            this.lstvReportName.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1});
            this.lstvReportName.FullRowSelect = true;
            this.lstvReportName.HideSelection = false;
            this.lstvReportName.Location = new System.Drawing.Point(15, 49);
            this.lstvReportName.Name = "lstvReportName";
            this.lstvReportName.Size = new System.Drawing.Size(280, 241);
            this.lstvReportName.TabIndex = 16;
            this.lstvReportName.UseCompatibleStateImageBehavior = false;
            this.lstvReportName.View = System.Windows.Forms.View.Details;
            this.lstvReportName.SelectedIndexChanged += new System.EventHandler(this.lstvReportName_SelectedIndexChanged);
            this.lstvReportName.AfterLabelEdit += new System.Windows.Forms.LabelEditEventHandler(this.lstvReportName_AfterLabelEdit);
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "名称";
            this.columnHeader1.Width = 274;
            // 
            // printerError
            // 
            this.printerError.ContainerControl = this;
            // 
            // paperError
            // 
            this.paperError.ContainerControl = this;
            // 
            // btnAdd
            // 
            this.btnAdd.Location = new System.Drawing.Point(220, 19);
            this.btnAdd.Name = "btnAdd";
            this.btnAdd.Size = new System.Drawing.Size(75, 23);
            this.btnAdd.TabIndex = 15;
            this.btnAdd.Text = "添加(&A)";
            this.btnAdd.UseVisualStyleBackColor = true;
            this.btnAdd.Click += new System.EventHandler(this.btnAdd_Click);
            // 
            // grpReportName
            // 
            this.grpReportName.Controls.Add(this.cmbReportName);
            this.grpReportName.Controls.Add(this.btnDelete);
            this.grpReportName.Controls.Add(this.lstvReportName);
            this.grpReportName.Controls.Add(this.btnAdd);
            this.grpReportName.Location = new System.Drawing.Point(15, 12);
            this.grpReportName.Name = "grpReportName";
            this.grpReportName.Size = new System.Drawing.Size(310, 326);
            this.grpReportName.TabIndex = 17;
            this.grpReportName.TabStop = false;
            this.grpReportName.Text = "报表名称";
            // 
            // cmbReportName
            // 
            this.cmbReportName.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbReportName.FormattingEnabled = true;
            this.cmbReportName.ImeMode = System.Windows.Forms.ImeMode.Off;
            this.cmbReportName.Location = new System.Drawing.Point(15, 20);
            this.cmbReportName.Name = "cmbReportName";
            this.cmbReportName.Size = new System.Drawing.Size(199, 20);
            this.cmbReportName.TabIndex = 17;
            // 
            // btnDelete
            // 
            this.btnDelete.Location = new System.Drawing.Point(220, 296);
            this.btnDelete.Name = "btnDelete";
            this.btnDelete.Size = new System.Drawing.Size(75, 23);
            this.btnDelete.TabIndex = 16;
            this.btnDelete.Text = "删除(&D)";
            this.btnDelete.UseVisualStyleBackColor = true;
            this.btnDelete.Click += new System.EventHandler(this.btnDelete_Click);
            // 
            // lblWarning
            // 
            this.lblWarning.AutoSize = true;
            this.lblWarning.ForeColor = System.Drawing.Color.Red;
            this.lblWarning.Location = new System.Drawing.Point(36, 53);
            this.lblWarning.Name = "lblWarning";
            this.lblWarning.Size = new System.Drawing.Size(101, 12);
            this.lblWarning.TabIndex = 15;
            this.lblWarning.Text = "未设置默认打印机";
            // 
            // ReportSettingForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(709, 401);
            this.Controls.Add(this.grpReportName);
            this.Controls.Add(this.label13);
            this.Controls.Add(this.grpMargin);
            this.Controls.Add(this.grpOrientation);
            this.Controls.Add(this.grpPaper);
            this.Controls.Add(this.grpPrinter);
            this.Controls.Add(this.btnClose);
            this.Controls.Add(this.btnSave);
            this.ForeColor = System.Drawing.SystemColors.ControlText;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "ReportSettingForm";
            this.Text = "报表打印配置";
            this.Load += new System.EventHandler(this.ReportSettingsFrm_Load);
            this.grpPrinter.ResumeLayout(false);
            this.grpPrinter.PerformLayout();
            this.grpPaper.ResumeLayout(false);
            this.grpPaper.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.grpOrientation.ResumeLayout(false);
            this.grpOrientation.PerformLayout();
            this.grpMargin.ResumeLayout(false);
            this.grpMargin.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudMarginBottom)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudMarginTop)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudMarginRight)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudMarginLeft)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.printerError)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.paperError)).EndInit();
            this.grpReportName.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnSave;
        private System.Windows.Forms.Button btnClose;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.ComboBox cmbPrinter;
        private System.Windows.Forms.GroupBox grpPrinter;
        private System.Windows.Forms.Button btnSelectDefault;
        private System.Windows.Forms.GroupBox grpPaper;
        private System.Windows.Forms.TextBox txtPaperHeight;
        private System.Windows.Forms.TextBox txtPaperWidth;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ComboBox cmbPaper;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.GroupBox grpOrientation;
        private System.Windows.Forms.RadioButton radVertical;
        private System.Windows.Forms.RadioButton radHorizontal;
        private System.Windows.Forms.GroupBox grpMargin;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.ListView lstvReportName;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ErrorProvider printerError;
        private System.Windows.Forms.ErrorProvider paperError;
        private System.Windows.Forms.NumericUpDown nudMarginTop;
        private System.Windows.Forms.NumericUpDown nudMarginRight;
        private System.Windows.Forms.NumericUpDown nudMarginLeft;
        private System.Windows.Forms.NumericUpDown nudMarginBottom;
        private System.Windows.Forms.GroupBox grpReportName;
        private System.Windows.Forms.Button btnAdd;
        private System.Windows.Forms.Button btnDelete;
        private System.Windows.Forms.Button btnSaveAsDefault;
        private System.Windows.Forms.ComboBox cmbReportName;
        private System.Windows.Forms.Label lblWarning;
    }
}