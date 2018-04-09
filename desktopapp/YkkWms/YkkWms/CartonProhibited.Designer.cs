namespace YkkWms
{
    partial class CartonProhibited
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
            this.txtCurrentStatus = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.rdoAllowed = new System.Windows.Forms.RadioButton();
            this.rdoProhibited = new System.Windows.Forms.RadioButton();
            this.btnOK = new System.Windows.Forms.Button();
            this.btnExit = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(59, 58);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(80, 18);
            this.label1.TabIndex = 0;
            this.label1.Text = "现在状态";
            // 
            // txtCurrentStatus
            // 
            this.txtCurrentStatus.BackColor = System.Drawing.SystemColors.Control;
            this.txtCurrentStatus.Location = new System.Drawing.Point(165, 55);
            this.txtCurrentStatus.Name = "txtCurrentStatus";
            this.txtCurrentStatus.ReadOnly = true;
            this.txtCurrentStatus.Size = new System.Drawing.Size(214, 27);
            this.txtCurrentStatus.TabIndex = 1;
            this.txtCurrentStatus.TabStop = false;
            this.txtCurrentStatus.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(59, 126);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(80, 18);
            this.label2.TabIndex = 0;
            this.label2.Text = "状态修改";
            // 
            // rdoAllowed
            // 
            this.rdoAllowed.AutoSize = true;
            this.rdoAllowed.Location = new System.Drawing.Point(165, 123);
            this.rdoAllowed.Name = "rdoAllowed";
            this.rdoAllowed.Size = new System.Drawing.Size(98, 22);
            this.rdoAllowed.TabIndex = 2;
            this.rdoAllowed.TabStop = true;
            this.rdoAllowed.Text = "进入许可";
            this.rdoAllowed.UseVisualStyleBackColor = true;
            // 
            // rdoProhibited
            // 
            this.rdoProhibited.AutoSize = true;
            this.rdoProhibited.Location = new System.Drawing.Point(281, 123);
            this.rdoProhibited.Name = "rdoProhibited";
            this.rdoProhibited.Size = new System.Drawing.Size(98, 22);
            this.rdoProhibited.TabIndex = 2;
            this.rdoProhibited.TabStop = true;
            this.rdoProhibited.Text = "进入禁止";
            this.rdoProhibited.UseVisualStyleBackColor = true;
            // 
            // btnOK
            // 
            this.btnOK.Location = new System.Drawing.Point(62, 197);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(142, 52);
            this.btnOK.TabIndex = 3;
            this.btnOK.Text = "设定(F2)";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // btnExit
            // 
            this.btnExit.Location = new System.Drawing.Point(237, 197);
            this.btnExit.Name = "btnExit";
            this.btnExit.Size = new System.Drawing.Size(142, 52);
            this.btnExit.TabIndex = 4;
            this.btnExit.Text = "退出(12)";
            this.btnExit.UseVisualStyleBackColor = true;
            this.btnExit.Click += new System.EventHandler(this.btnExit_Click);
            // 
            // CartonProhibited
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 17F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(444, 275);
            this.ControlBox = false;
            this.Controls.Add(this.btnExit);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.rdoProhibited);
            this.Controls.Add(this.rdoAllowed);
            this.Controls.Add(this.txtCurrentStatus);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Font = new System.Drawing.Font("SimSun", 13F);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.KeyPreview = true;
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "CartonProhibited";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "料箱进入禁止设定画面";
            this.Load += new System.EventHandler(this.CartonProhibited_Load);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.CartonProhibited_KeyDown);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtCurrentStatus;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.RadioButton rdoAllowed;
        private System.Windows.Forms.RadioButton rdoProhibited;
        private System.Windows.Forms.Button btnOK;
        private System.Windows.Forms.Button btnExit;
    }
}