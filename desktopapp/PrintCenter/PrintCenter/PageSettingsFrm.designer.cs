namespace PrintCenter
{
    partial class PageSettingsFrm
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
            this.cmbForm = new System.Windows.Forms.ComboBox();
            this.grpForm = new System.Windows.Forms.GroupBox();
            this.btnDeleteForm = new System.Windows.Forms.Button();
            this.btnAddForm = new System.Windows.Forms.Button();
            this.txtFormHeight = new System.Windows.Forms.TextBox();
            this.txtFormWidth = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.txtForm = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.grpOrientation = new System.Windows.Forms.GroupBox();
            this.radZ = new System.Windows.Forms.RadioButton();
            this.radH = new System.Windows.Forms.RadioButton();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.txtBottom = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.txtTop = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.txtRight = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.txtLeft = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.btnStatus = new System.Windows.Forms.Button();
            this.btnDefault = new System.Windows.Forms.Button();
            this.cmbPrinter = new System.Windows.Forms.ComboBox();
            this.label10 = new System.Windows.Forms.Label();
            this.btnCancel = new System.Windows.Forms.Button();
            this.btnOK = new System.Windows.Forms.Button();
            this.label11 = new System.Windows.Forms.Label();
            this.epError = new System.Windows.Forms.ErrorProvider(this.components);
            this.grpForm.SuspendLayout();
            this.grpOrientation.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.epError)).BeginInit();
            this.SuspendLayout();
            // 
            // cmbForm
            // 
            this.cmbForm.FormattingEnabled = true;
            this.cmbForm.Location = new System.Drawing.Point(75, 20);
            this.cmbForm.Name = "cmbForm";
            this.cmbForm.Size = new System.Drawing.Size(198, 20);
            this.cmbForm.TabIndex = 2;
            this.cmbForm.SelectedValueChanged += new System.EventHandler(this.cmbForm_SelectedValueChanged);
            // 
            // grpForm
            // 
            this.grpForm.Controls.Add(this.btnDeleteForm);
            this.grpForm.Controls.Add(this.btnAddForm);
            this.grpForm.Controls.Add(this.txtFormHeight);
            this.grpForm.Controls.Add(this.txtFormWidth);
            this.grpForm.Controls.Add(this.label5);
            this.grpForm.Controls.Add(this.label4);
            this.grpForm.Controls.Add(this.label3);
            this.grpForm.Controls.Add(this.txtForm);
            this.grpForm.Controls.Add(this.label2);
            this.grpForm.Controls.Add(this.cmbForm);
            this.grpForm.Controls.Add(this.label1);
            this.grpForm.Location = new System.Drawing.Point(12, 96);
            this.grpForm.Name = "grpForm";
            this.grpForm.Size = new System.Drawing.Size(363, 111);
            this.grpForm.TabIndex = 3;
            this.grpForm.TabStop = false;
            this.grpForm.Text = "Form";
            // 
            // btnDeleteForm
            // 
            this.btnDeleteForm.Location = new System.Drawing.Point(279, 79);
            this.btnDeleteForm.Name = "btnDeleteForm";
            this.btnDeleteForm.Size = new System.Drawing.Size(75, 23);
            this.btnDeleteForm.TabIndex = 13;
            this.btnDeleteForm.Text = "&Delete";
            this.btnDeleteForm.UseVisualStyleBackColor = true;
            this.btnDeleteForm.Click += new System.EventHandler(this.btnDelete_Click);
            // 
            // btnAddForm
            // 
            this.btnAddForm.Location = new System.Drawing.Point(279, 49);
            this.btnAddForm.Name = "btnAddForm";
            this.btnAddForm.Size = new System.Drawing.Size(75, 23);
            this.btnAddForm.TabIndex = 10;
            this.btnAddForm.Text = "&Add Form";
            this.btnAddForm.UseVisualStyleBackColor = true;
            this.btnAddForm.Click += new System.EventHandler(this.btnAdd_Click);
            // 
            // txtFormHeight
            // 
            this.txtFormHeight.Location = new System.Drawing.Point(223, 80);
            this.txtFormHeight.Name = "txtFormHeight";
            this.txtFormHeight.Size = new System.Drawing.Size(50, 21);
            this.txtFormHeight.TabIndex = 9;
            // 
            // txtFormWidth
            // 
            this.txtFormWidth.Location = new System.Drawing.Point(116, 80);
            this.txtFormWidth.Name = "txtFormWidth";
            this.txtFormWidth.Size = new System.Drawing.Size(50, 21);
            this.txtFormWidth.TabIndex = 8;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(182, 84);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(47, 12);
            this.label5.TabIndex = 7;
            this.label5.Text = "Height:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(75, 84);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(41, 12);
            this.label4.TabIndex = 6;
            this.label4.Text = "Width:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(10, 84);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(71, 12);
            this.label3.TabIndex = 5;
            this.label3.Text = "Paper Size:";
            // 
            // txtForm
            // 
            this.txtForm.Location = new System.Drawing.Point(75, 50);
            this.txtForm.Name = "txtForm";
            this.txtForm.Size = new System.Drawing.Size(198, 21);
            this.txtForm.TabIndex = 4;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(10, 54);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(65, 12);
            this.label2.TabIndex = 3;
            this.label2.Text = "Form Name:";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(10, 24);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(65, 12);
            this.label1.TabIndex = 1;
            this.label1.Text = "Form Name:";
            // 
            // grpOrientation
            // 
            this.grpOrientation.Controls.Add(this.radZ);
            this.grpOrientation.Controls.Add(this.radH);
            this.grpOrientation.Location = new System.Drawing.Point(12, 213);
            this.grpOrientation.Name = "grpOrientation";
            this.grpOrientation.Size = new System.Drawing.Size(363, 48);
            this.grpOrientation.TabIndex = 4;
            this.grpOrientation.TabStop = false;
            this.grpOrientation.Text = "Orientation:";
            // 
            // radZ
            // 
            this.radZ.AutoSize = true;
            this.radZ.Location = new System.Drawing.Point(184, 20);
            this.radZ.Name = "radZ";
            this.radZ.Size = new System.Drawing.Size(71, 16);
            this.radZ.TabIndex = 1;
            this.radZ.TabStop = true;
            this.radZ.Text = "&Vertical";
            this.radZ.UseVisualStyleBackColor = true;
            // 
            // radH
            // 
            this.radH.AutoSize = true;
            this.radH.Checked = true;
            this.radH.Location = new System.Drawing.Point(80, 20);
            this.radH.Name = "radH";
            this.radH.Size = new System.Drawing.Size(83, 16);
            this.radH.TabIndex = 0;
            this.radH.TabStop = true;
            this.radH.Text = "&Horizontal";
            this.radH.UseVisualStyleBackColor = true;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.txtBottom);
            this.groupBox1.Controls.Add(this.label9);
            this.groupBox1.Controls.Add(this.txtTop);
            this.groupBox1.Controls.Add(this.label8);
            this.groupBox1.Controls.Add(this.txtRight);
            this.groupBox1.Controls.Add(this.label7);
            this.groupBox1.Controls.Add(this.txtLeft);
            this.groupBox1.Controls.Add(this.label6);
            this.groupBox1.Location = new System.Drawing.Point(12, 267);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(363, 48);
            this.groupBox1.TabIndex = 5;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Margins£º";
            // 
            // txtBottom
            // 
            this.txtBottom.Location = new System.Drawing.Point(314, 20);
            this.txtBottom.Name = "txtBottom";
            this.txtBottom.Size = new System.Drawing.Size(33, 21);
            this.txtBottom.TabIndex = 16;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(273, 24);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(47, 12);
            this.label9.TabIndex = 15;
            this.label9.Text = "Bottom:";
            // 
            // txtTop
            // 
            this.txtTop.Location = new System.Drawing.Point(222, 20);
            this.txtTop.Name = "txtTop";
            this.txtTop.Size = new System.Drawing.Size(33, 21);
            this.txtTop.TabIndex = 14;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(181, 24);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(29, 12);
            this.label8.TabIndex = 13;
            this.label8.Text = "Top:";
            // 
            // txtRight
            // 
            this.txtRight.Location = new System.Drawing.Point(129, 20);
            this.txtRight.Name = "txtRight";
            this.txtRight.Size = new System.Drawing.Size(33, 21);
            this.txtRight.TabIndex = 12;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(100, 24);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(41, 12);
            this.label7.TabIndex = 11;
            this.label7.Text = "Right:";
            // 
            // txtLeft
            // 
            this.txtLeft.Location = new System.Drawing.Point(49, 20);
            this.txtLeft.Name = "txtLeft";
            this.txtLeft.Size = new System.Drawing.Size(33, 21);
            this.txtLeft.TabIndex = 10;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(20, 24);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(35, 12);
            this.label6.TabIndex = 9;
            this.label6.Text = "Left:";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.btnStatus);
            this.groupBox2.Controls.Add(this.btnDefault);
            this.groupBox2.Controls.Add(this.cmbPrinter);
            this.groupBox2.Controls.Add(this.label10);
            this.groupBox2.Location = new System.Drawing.Point(12, 9);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(363, 82);
            this.groupBox2.TabIndex = 6;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Printer";
            // 
            // btnStatus
            // 
            this.btnStatus.Location = new System.Drawing.Point(197, 48);
            this.btnStatus.Name = "btnStatus";
            this.btnStatus.Size = new System.Drawing.Size(75, 23);
            this.btnStatus.TabIndex = 15;
            this.btnStatus.Text = "&Status";
            this.btnStatus.UseVisualStyleBackColor = true;
            this.btnStatus.Click += new System.EventHandler(this.btnStatus_Click);
            // 
            // btnDefault
            // 
            this.btnDefault.Location = new System.Drawing.Point(279, 48);
            this.btnDefault.Name = "btnDefault";
            this.btnDefault.Size = new System.Drawing.Size(75, 23);
            this.btnDefault.TabIndex = 14;
            this.btnDefault.Text = "&Default";
            this.btnDefault.UseVisualStyleBackColor = true;
            this.btnDefault.Click += new System.EventHandler(this.btnDefault_Click);
            // 
            // cmbPrinter
            // 
            this.cmbPrinter.FormattingEnabled = true;
            this.cmbPrinter.Location = new System.Drawing.Point(75, 22);
            this.cmbPrinter.Name = "cmbPrinter";
            this.cmbPrinter.Size = new System.Drawing.Size(279, 20);
            this.cmbPrinter.TabIndex = 3;
            this.cmbPrinter.SelectedIndexChanged += new System.EventHandler(this.cmbPrinter_SelectedValueChanged);
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(10, 26);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(53, 12);
            this.label10.TabIndex = 2;
            this.label10.Text = "Printer:";
            // 
            // btnCancel
            // 
            this.btnCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnCancel.Location = new System.Drawing.Point(300, 332);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(75, 23);
            this.btnCancel.TabIndex = 15;
            this.btnCancel.Text = "&Cancel";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // btnOK
            // 
            this.btnOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnOK.Location = new System.Drawing.Point(219, 332);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(75, 23);
            this.btnOK.TabIndex = 16;
            this.btnOK.Text = "&OK";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // label11
            // 
            this.label11.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(10, 337);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(185, 12);
            this.label11.TabIndex = 17;
            this.label11.Text = "Note:All the legth unit is cm.";
            // 
            // epError
            // 
            this.epError.ContainerControl = this;
            // 
            // frmPageSettings
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(387, 369);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.grpOrientation);
            this.Controls.Add(this.grpForm);
            this.Controls.Add(this.label11);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.MaximizeBox = false;
            this.Name = "frmPageSettings";
            this.Text = "Page Settings";
            this.Load += new System.EventHandler(this.frmPageSettings_Load);
            this.grpForm.ResumeLayout(false);
            this.grpForm.PerformLayout();
            this.grpOrientation.ResumeLayout(false);
            this.grpOrientation.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.epError)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox cmbForm;
        private System.Windows.Forms.GroupBox grpForm;
        private System.Windows.Forms.TextBox txtForm;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtFormHeight;
        private System.Windows.Forms.TextBox txtFormWidth;
        private System.Windows.Forms.Button btnAddForm;
        private System.Windows.Forms.GroupBox grpOrientation;
        private System.Windows.Forms.RadioButton radZ;
        private System.Windows.Forms.RadioButton radH;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.TextBox txtBottom;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox txtTop;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox txtRight;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox txtLeft;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.ComboBox cmbPrinter;
        private System.Windows.Forms.Button btnDefault;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.Button btnOK;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Button btnStatus;
        private System.Windows.Forms.Button btnDeleteForm;
        private System.Windows.Forms.ErrorProvider epError;


    }
}