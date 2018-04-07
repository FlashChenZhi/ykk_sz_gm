namespace YkkWms
{
    partial class LablePublish
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
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.okBtn = new System.Windows.Forms.Button();
            this.reprintBtn = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(25, 60);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(161, 12);
            this.label2.TabIndex = 3;
            this.label2.Text = "请将印刷好的List放入Bucket";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(25, 36);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(77, 12);
            this.label1.TabIndex = 2;
            this.label1.Text = "List已印刷。";
            // 
            // okBtn
            // 
            this.okBtn.Location = new System.Drawing.Point(27, 109);
            this.okBtn.Name = "okBtn";
            this.okBtn.Size = new System.Drawing.Size(95, 37);
            this.okBtn.TabIndex = 84;
            this.okBtn.Text = "OK(F2)";
            this.okBtn.UseVisualStyleBackColor = true;
            // 
            // reprintBtn
            // 
            this.reprintBtn.Location = new System.Drawing.Point(148, 109);
            this.reprintBtn.Name = "reprintBtn";
            this.reprintBtn.Size = new System.Drawing.Size(95, 37);
            this.reprintBtn.TabIndex = 85;
            this.reprintBtn.Text = "再印刷(F5)";
            this.reprintBtn.UseVisualStyleBackColor = true;
            // 
            // LablePublish
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(272, 176);
            this.Controls.Add(this.reprintBtn);
            this.Controls.Add(this.okBtn);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.KeyPreview = true;
            this.Name = "LablePublish";
            this.Text = "Lable发行";
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.LablePublish_KeyDown);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button okBtn;
        private System.Windows.Forms.Button reprintBtn;
    }
}