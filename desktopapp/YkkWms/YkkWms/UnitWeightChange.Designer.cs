namespace YkkWms
{
    partial class UnitWeightChange
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
            this.setBtn = new System.Windows.Forms.Button();
            this.label5 = new System.Windows.Forms.Label();
            this.newUnitWeightBox = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // setBtn
            // 
            this.setBtn.Location = new System.Drawing.Point(25, 163);
            this.setBtn.Margin = new System.Windows.Forms.Padding(4);
            this.setBtn.Name = "setBtn";
            this.setBtn.Size = new System.Drawing.Size(142, 52);
            this.setBtn.TabIndex = 91;
            this.setBtn.Text = "确定(F2)";
            this.setBtn.UseVisualStyleBackColor = true;
            this.setBtn.Click += new System.EventHandler(this.setBtn_Click);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(335, 98);
            this.label5.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(17, 18);
            this.label5.TabIndex = 90;
            this.label5.Text = "g";
            // 
            // newUnitWeightBox
            // 
            this.newUnitWeightBox.Location = new System.Drawing.Point(183, 94);
            this.newUnitWeightBox.Margin = new System.Windows.Forms.Padding(4);
            this.newUnitWeightBox.Name = "newUnitWeightBox";
            this.newUnitWeightBox.Size = new System.Drawing.Size(148, 27);
            this.newUnitWeightBox.TabIndex = 89;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(22, 98);
            this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(134, 18);
            this.label4.TabIndex = 88;
            this.label4.Text = "变更后单位重量";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(22, 57);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(170, 18);
            this.label2.TabIndex = 85;
            this.label2.Text = "请输入新单位重量。";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(22, 23);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(134, 18);
            this.label1.TabIndex = 84;
            this.label1.Text = "修改单位重量。";
            // 
            // UnitWeightChange
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 17F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(386, 228);
            this.Controls.Add(this.setBtn);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.newUnitWeightBox);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Font = new System.Drawing.Font("SimSun", 13F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.KeyPreview = true;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "UnitWeightChange";
            this.Text = "单位重量维护画面";
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.UnitWeightChange_KeyDown);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button setBtn;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox newUnitWeightBox;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
    }
}