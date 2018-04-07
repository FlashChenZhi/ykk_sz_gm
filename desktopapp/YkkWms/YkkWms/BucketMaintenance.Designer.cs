namespace YkkWms
{
    partial class BucketMaintenance
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
            this.bucketNoBox1 = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.newBucketWeightBox = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.setBtn = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(24, 28);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(134, 18);
            this.label1.TabIndex = 0;
            this.label1.Text = "修改料箱净重。";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(24, 62);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(170, 18);
            this.label2.TabIndex = 1;
            this.label2.Text = "请输入新料箱净重。";
            // 
            // bucketNoBox1
            // 
            this.bucketNoBox1.Location = new System.Drawing.Point(185, 96);
            this.bucketNoBox1.Margin = new System.Windows.Forms.Padding(4);
            this.bucketNoBox1.Name = "bucketNoBox1";
            this.bucketNoBox1.ReadOnly = true;
            this.bucketNoBox1.Size = new System.Drawing.Size(148, 27);
            this.bucketNoBox1.TabIndex = 63;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(24, 100);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(80, 18);
            this.label3.TabIndex = 62;
            this.label3.Text = "箱子编号";
            // 
            // newBucketWeightBox
            // 
            this.newBucketWeightBox.Location = new System.Drawing.Point(185, 134);
            this.newBucketWeightBox.Margin = new System.Windows.Forms.Padding(4);
            this.newBucketWeightBox.Name = "newBucketWeightBox";
            this.newBucketWeightBox.Size = new System.Drawing.Size(148, 27);
            this.newBucketWeightBox.TabIndex = 65;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(24, 138);
            this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(134, 18);
            this.label4.TabIndex = 64;
            this.label4.Text = "变更后料箱净重";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(344, 138);
            this.label5.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(26, 18);
            this.label5.TabIndex = 66;
            this.label5.Text = "Kg";
            // 
            // setBtn
            // 
            this.setBtn.Location = new System.Drawing.Point(13, 201);
            this.setBtn.Margin = new System.Windows.Forms.Padding(4);
            this.setBtn.Name = "setBtn";
            this.setBtn.Size = new System.Drawing.Size(142, 52);
            this.setBtn.TabIndex = 83;
            this.setBtn.Text = "确定(F2)";
            this.setBtn.UseVisualStyleBackColor = true;
            this.setBtn.Click += new System.EventHandler(this.setBtn_Click);
            // 
            // BucketMaintenance
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 17F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(392, 266);
            this.Controls.Add(this.setBtn);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.newBucketWeightBox);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.bucketNoBox1);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Font = new System.Drawing.Font("SimSun", 13F);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.KeyPreview = true;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "BucketMaintenance";
            this.Text = "料箱维护画面";
            this.Load += new System.EventHandler(this.BucketMaintenance_Load);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.BucketMaintenance_KeyDown);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox bucketNoBox1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox newBucketWeightBox;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Button setBtn;
    }
}