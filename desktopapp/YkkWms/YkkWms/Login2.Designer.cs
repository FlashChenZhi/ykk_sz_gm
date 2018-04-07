namespace YkkWms
{
    partial class Login2
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
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.userIdBox = new System.Windows.Forms.TextBox();
            this.passwordBox = new System.Windows.Forms.TextBox();
            this.loginBtn = new System.Windows.Forms.Button();
            this.cancelBtn = new System.Windows.Forms.Button();
            this.msgBox = new System.Windows.Forms.TextBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.checkCountBox = new System.Windows.Forms.NumericUpDown();
            this.label16 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.checkCountBox)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(65, 16);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(62, 18);
            this.label1.TabIndex = 0;
            this.label1.Text = "用户名";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(83, 51);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(44, 18);
            this.label2.TabIndex = 1;
            this.label2.Text = "密码";
            // 
            // userIdBox
            // 
            this.userIdBox.Location = new System.Drawing.Point(135, 13);
            this.userIdBox.Margin = new System.Windows.Forms.Padding(4);
            this.userIdBox.Name = "userIdBox";
            this.userIdBox.Size = new System.Drawing.Size(148, 27);
            this.userIdBox.TabIndex = 0;
            this.userIdBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.userIdBox_KeyPress);
            // 
            // passwordBox
            // 
            this.passwordBox.Location = new System.Drawing.Point(135, 48);
            this.passwordBox.Margin = new System.Windows.Forms.Padding(4);
            this.passwordBox.Name = "passwordBox";
            this.passwordBox.PasswordChar = '●';
            this.passwordBox.Size = new System.Drawing.Size(148, 27);
            this.passwordBox.TabIndex = 1;
            this.passwordBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.passwordBox_KeyPress);
            // 
            // loginBtn
            // 
            this.loginBtn.Location = new System.Drawing.Point(20, 156);
            this.loginBtn.Margin = new System.Windows.Forms.Padding(4);
            this.loginBtn.Name = "loginBtn";
            this.loginBtn.Size = new System.Drawing.Size(142, 52);
            this.loginBtn.TabIndex = 3;
            this.loginBtn.Text = "确定(F2)";
            this.loginBtn.UseVisualStyleBackColor = true;
            this.loginBtn.Click += new System.EventHandler(this.loginBtn_Click);
            // 
            // cancelBtn
            // 
            this.cancelBtn.Location = new System.Drawing.Point(187, 156);
            this.cancelBtn.Margin = new System.Windows.Forms.Padding(4);
            this.cancelBtn.Name = "cancelBtn";
            this.cancelBtn.Size = new System.Drawing.Size(142, 52);
            this.cancelBtn.TabIndex = 4;
            this.cancelBtn.Text = "取消(F9)";
            this.cancelBtn.UseVisualStyleBackColor = true;
            this.cancelBtn.Click += new System.EventHandler(this.cancelBtn_Click);
            // 
            // msgBox
            // 
            this.msgBox.Location = new System.Drawing.Point(2, 120);
            this.msgBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.msgBox.Name = "msgBox";
            this.msgBox.ReadOnly = true;
            this.msgBox.Size = new System.Drawing.Size(344, 27);
            this.msgBox.TabIndex = 49;
            this.msgBox.TabStop = false;
            // 
            // panel1
            // 
            this.panel1.Location = new System.Drawing.Point(282, 82);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(30, 30);
            this.panel1.TabIndex = 68;
            // 
            // checkCountBox
            // 
            this.checkCountBox.InterceptArrowKeys = false;
            this.checkCountBox.Location = new System.Drawing.Point(135, 82);
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
            this.checkCountBox.Size = new System.Drawing.Size(164, 27);
            this.checkCountBox.TabIndex = 2;
            this.checkCountBox.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.checkCountBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.checkCountBox_KeyPress);
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Location = new System.Drawing.Point(11, 86);
            this.label16.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(116, 18);
            this.label16.TabIndex = 67;
            this.label16.Text = "原单位检查数";
            // 
            // Login2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 17F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(348, 221);
            this.ControlBox = false;
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.checkCountBox);
            this.Controls.Add(this.label16);
            this.Controls.Add(this.msgBox);
            this.Controls.Add(this.cancelBtn);
            this.Controls.Add(this.loginBtn);
            this.Controls.Add(this.passwordBox);
            this.Controls.Add(this.userIdBox);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Font = new System.Drawing.Font("SimSun", 13F);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.KeyPreview = true;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Login2";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "原单位检查数修改";
            this.Load += new System.EventHandler(this.Login2_Load);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.LoginFrm_KeyDown);
            ((System.ComponentModel.ISupportInitialize)(this.checkCountBox)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox userIdBox;
        private System.Windows.Forms.TextBox passwordBox;
        private System.Windows.Forms.Button loginBtn;
        private System.Windows.Forms.Button cancelBtn;
        private System.Windows.Forms.TextBox msgBox;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.NumericUpDown checkCountBox;
        private System.Windows.Forms.Label label16;
    }
}

