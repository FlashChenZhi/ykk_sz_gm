namespace YkkWms
{
    partial class OutOfRangeWarning
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
            this.btnOK = new System.Windows.Forms.Button();
            this.lblTitle = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.lblErrorRate = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.lblErrorRateMin = new System.Windows.Forms.Label();
            this.lblErrorRateMax = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(220, 51);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(116, 18);
            this.label1.TabIndex = 0;
            this.label1.Text = "误差超出范围";
            // 
            // btnOK
            // 
            this.btnOK.Location = new System.Drawing.Point(151, 205);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(142, 52);
            this.btnOK.TabIndex = 1;
            this.btnOK.Text = "确认(F4)";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // lblTitle
            // 
            this.lblTitle.AutoSize = true;
            this.lblTitle.Font = new System.Drawing.Font("SimSun", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblTitle.Location = new System.Drawing.Point(93, 45);
            this.lblTitle.Name = "lblTitle";
            this.lblTitle.Size = new System.Drawing.Size(130, 24);
            this.lblTitle.TabIndex = 2;
            this.lblTitle.Text = "原单位重量";
            this.lblTitle.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(161, 99);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(62, 18);
            this.label2.TabIndex = 3;
            this.label2.Text = "误差率";
            // 
            // lblErrorRate
            // 
            this.lblErrorRate.AutoSize = true;
            this.lblErrorRate.Location = new System.Drawing.Point(234, 99);
            this.lblErrorRate.Name = "lblErrorRate";
            this.lblErrorRate.Size = new System.Drawing.Size(62, 18);
            this.lblErrorRate.TabIndex = 4;
            this.lblErrorRate.Text = "99.9 %";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(88, 143);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(116, 18);
            this.label4.TabIndex = 5;
            this.label4.Text = "( 误差上下限";
            // 
            // lblErrorRateMin
            // 
            this.lblErrorRateMin.AutoSize = true;
            this.lblErrorRateMin.Location = new System.Drawing.Point(210, 143);
            this.lblErrorRateMin.Name = "lblErrorRateMin";
            this.lblErrorRateMin.Size = new System.Drawing.Size(44, 18);
            this.lblErrorRateMin.TabIndex = 6;
            this.lblErrorRateMin.Text = "100%";
            this.lblErrorRateMin.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // lblErrorRateMax
            // 
            this.lblErrorRateMax.AutoSize = true;
            this.lblErrorRateMax.Location = new System.Drawing.Point(290, 143);
            this.lblErrorRateMax.Name = "lblErrorRateMax";
            this.lblErrorRateMax.Size = new System.Drawing.Size(62, 18);
            this.lblErrorRateMax.TabIndex = 7;
            this.lblErrorRateMax.Text = "100% )";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(258, 143);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(26, 18);
            this.label3.TabIndex = 8;
            this.label3.Text = "～";
            // 
            // OutOfRangeWarning
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 17F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(444, 281);
            this.ControlBox = false;
            this.Controls.Add(this.label3);
            this.Controls.Add(this.lblErrorRateMax);
            this.Controls.Add(this.lblErrorRateMin);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.lblErrorRate);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.lblTitle);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.label1);
            this.Font = new System.Drawing.Font("SimSun", 13F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.KeyPreview = true;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "OutOfRangeWarning";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "误差超出范围";
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.OutOfRangeWarning_KeyDown);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnOK;
        private System.Windows.Forms.Label lblTitle;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lblErrorRate;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label lblErrorRateMin;
        private System.Windows.Forms.Label lblErrorRateMax;
        private System.Windows.Forms.Label label3;
    }
}