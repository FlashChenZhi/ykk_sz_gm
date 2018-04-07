namespace PrintCenter
{
    partial class ReportViewerFrm : System.Windows.Forms.Form
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ReportViewerFrm));
            this.dnReport = new System.Windows.Forms.BindingNavigator(this.components);
            this.tspbPrint = new System.Windows.Forms.ToolStripSplitButton();
            this.toolPageSettings = new System.Windows.Forms.ToolStripMenuItem();
            this.toolPreview = new System.Windows.Forms.ToolStripMenuItem();
            this.toolPrint = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.toolRefresh = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator4 = new System.Windows.Forms.ToolStripSeparator();
            this.toolExport = new System.Windows.Forms.ToolStripSplitButton();
            this.toolExcel = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator5 = new System.Windows.Forms.ToolStripSeparator();
            this.tspbZoom = new System.Windows.Forms.ToolStripSplitButton();
            this.tool25 = new System.Windows.Forms.ToolStripMenuItem();
            this.tool50 = new System.Windows.Forms.ToolStripMenuItem();
            this.tool100 = new System.Windows.Forms.ToolStripMenuItem();
            this.tool200 = new System.Windows.Forms.ToolStripMenuItem();
            this.tool400 = new System.Windows.Forms.ToolStripMenuItem();
            this.toolWhole = new System.Windows.Forms.ToolStripMenuItem();
            this.toolPageWidth = new System.Windows.Forms.ToolStripMenuItem();
            this.tspbNavigation = new System.Windows.Forms.ToolStripSplitButton();
            this.toolFirst = new System.Windows.Forms.ToolStripMenuItem();
            this.toolLast = new System.Windows.Forms.ToolStripMenuItem();
            this.toolPrevious = new System.Windows.Forms.ToolStripMenuItem();
            this.toolNext = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripButton1 = new System.Windows.Forms.ToolStripSeparator();
            this.rptViewer = new Microsoft.Reporting.WinForms.ReportViewer();
            this.PreviewDialog = new System.Windows.Forms.PrintPreviewDialog();
            ((System.ComponentModel.ISupportInitialize)(this.dnReport)).BeginInit();
            this.dnReport.SuspendLayout();
            this.SuspendLayout();
            // 
            // dnReport
            // 
            this.dnReport.AddNewItem = null;
            this.dnReport.CountItem = null;
            this.dnReport.DeleteItem = null;
            this.dnReport.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.tspbPrint,
            this.toolStripSeparator1,
            this.toolRefresh,
            this.toolStripSeparator4,
            this.toolExport,
            this.toolStripSeparator5,
            this.tspbZoom,
            this.tspbNavigation,
            this.toolStripButton1});
            this.dnReport.Location = new System.Drawing.Point(0, 0);
            this.dnReport.MoveFirstItem = null;
            this.dnReport.MoveLastItem = null;
            this.dnReport.MoveNextItem = null;
            this.dnReport.MovePreviousItem = null;
            this.dnReport.Name = "dnReport";
            this.dnReport.PositionItem = null;
            this.dnReport.Size = new System.Drawing.Size(698, 25);
            this.dnReport.TabIndex = 0;
            this.dnReport.Text = "bindingNavigator1";
            // 
            // tspbPrint
            // 
            this.tspbPrint.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.tspbPrint.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolPageSettings,
            this.toolPreview,
            this.toolPrint});
            this.tspbPrint.Image = ((System.Drawing.Image)(resources.GetObject("tspbPrint.Image")));
            this.tspbPrint.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.tspbPrint.Name = "tspbPrint";
            this.tspbPrint.Size = new System.Drawing.Size(45, 22);
            this.tspbPrint.Text = "打印";
            this.tspbPrint.ButtonClick += new System.EventHandler(this.tspbPrint_ButtonClick);
            // 
            // toolPageSettings
            // 
            this.toolPageSettings.Name = "toolPageSettings";
            this.toolPageSettings.Size = new System.Drawing.Size(118, 22);
            this.toolPageSettings.Text = "页面设置";
            this.toolPageSettings.Click += new System.EventHandler(this.toolPageSettings_Click);
            // 
            // toolPreview
            // 
            this.toolPreview.Name = "toolPreview";
            this.toolPreview.Size = new System.Drawing.Size(118, 22);
            this.toolPreview.Text = "打印预览";
            this.toolPreview.Click += new System.EventHandler(this.toolPreview_Click);
            // 
            // toolPrint
            // 
            this.toolPrint.Name = "toolPrint";
            this.toolPrint.Size = new System.Drawing.Size(118, 22);
            this.toolPrint.Text = "打印";
            this.toolPrint.Click += new System.EventHandler(this.toolPrint_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(6, 25);
            // 
            // toolRefresh
            // 
            this.toolRefresh.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolRefresh.Name = "toolRefresh";
            this.toolRefresh.Size = new System.Drawing.Size(33, 22);
            this.toolRefresh.Text = "刷新";
            this.toolRefresh.Click += new System.EventHandler(this.toolRefresh_Click);
            // 
            // toolStripSeparator4
            // 
            this.toolStripSeparator4.Name = "toolStripSeparator4";
            this.toolStripSeparator4.Size = new System.Drawing.Size(6, 25);
            // 
            // toolExport
            // 
            this.toolExport.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolExcel});
            this.toolExport.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolExport.Name = "toolExport";
            this.toolExport.Size = new System.Drawing.Size(45, 22);
            this.toolExport.Text = "导出";
            // 
            // toolExcel
            // 
            this.toolExcel.Name = "toolExcel";
            this.toolExcel.Size = new System.Drawing.Size(136, 22);
            this.toolExcel.Text = "导出至Excel";
            this.toolExcel.Click += new System.EventHandler(this.toolExcel_Click);
            // 
            // toolStripSeparator5
            // 
            this.toolStripSeparator5.Name = "toolStripSeparator5";
            this.toolStripSeparator5.Size = new System.Drawing.Size(6, 25);
            // 
            // tspbZoom
            // 
            this.tspbZoom.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.tspbZoom.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.tool25,
            this.tool50,
            this.tool100,
            this.tool200,
            this.tool400,
            this.toolWhole,
            this.toolPageWidth});
            this.tspbZoom.Image = ((System.Drawing.Image)(resources.GetObject("tspbZoom.Image")));
            this.tspbZoom.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.tspbZoom.Name = "tspbZoom";
            this.tspbZoom.Size = new System.Drawing.Size(45, 22);
            this.tspbZoom.Text = "缩放";
            // 
            // tool25
            // 
            this.tool25.Name = "tool25";
            this.tool25.Size = new System.Drawing.Size(130, 22);
            this.tool25.Text = "25%";
            this.tool25.Click += new System.EventHandler(this.tool25_Click);
            // 
            // tool50
            // 
            this.tool50.Name = "tool50";
            this.tool50.Size = new System.Drawing.Size(130, 22);
            this.tool50.Text = "50%";
            this.tool50.Click += new System.EventHandler(this.tool50_Click);
            // 
            // tool100
            // 
            this.tool100.Name = "tool100";
            this.tool100.Size = new System.Drawing.Size(130, 22);
            this.tool100.Text = "100%";
            this.tool100.Click += new System.EventHandler(this.tool100_Click);
            // 
            // tool200
            // 
            this.tool200.Name = "tool200";
            this.tool200.Size = new System.Drawing.Size(130, 22);
            this.tool200.Text = "200%";
            this.tool200.Click += new System.EventHandler(this.tool200_Click);
            // 
            // tool400
            // 
            this.tool400.Name = "tool400";
            this.tool400.Size = new System.Drawing.Size(130, 22);
            this.tool400.Text = "400%";
            this.tool400.Click += new System.EventHandler(this.tool400_Click);
            // 
            // toolWhole
            // 
            this.toolWhole.Name = "toolWhole";
            this.toolWhole.Size = new System.Drawing.Size(130, 22);
            this.toolWhole.Text = "&Whole Page";
            this.toolWhole.Click += new System.EventHandler(this.toolWhole_Click);
            // 
            // toolPageWidth
            // 
            this.toolPageWidth.Name = "toolPageWidth";
            this.toolPageWidth.Size = new System.Drawing.Size(130, 22);
            this.toolPageWidth.Text = "&Page Width";
            this.toolPageWidth.Click += new System.EventHandler(this.toolPageWidth_Click);
            // 
            // tspbNavigation
            // 
            this.tspbNavigation.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.tspbNavigation.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolFirst,
            this.toolLast,
            this.toolPrevious,
            this.toolNext});
            this.tspbNavigation.Image = ((System.Drawing.Image)(resources.GetObject("tspbNavigation.Image")));
            this.tspbNavigation.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.tspbNavigation.Name = "tspbNavigation";
            this.tspbNavigation.Size = new System.Drawing.Size(45, 22);
            this.tspbNavigation.Text = "导航";
            // 
            // toolFirst
            // 
            this.toolFirst.Name = "toolFirst";
            this.toolFirst.Size = new System.Drawing.Size(118, 22);
            this.toolFirst.Text = "第一页";
            this.toolFirst.Click += new System.EventHandler(this.toolFirst_Click);
            // 
            // toolLast
            // 
            this.toolLast.Name = "toolLast";
            this.toolLast.Size = new System.Drawing.Size(118, 22);
            this.toolLast.Text = "最后一页";
            this.toolLast.Click += new System.EventHandler(this.toolLast_Click);
            // 
            // toolPrevious
            // 
            this.toolPrevious.Name = "toolPrevious";
            this.toolPrevious.Size = new System.Drawing.Size(118, 22);
            this.toolPrevious.Text = "上页";
            this.toolPrevious.Click += new System.EventHandler(this.toolPrevious_Click);
            // 
            // toolNext
            // 
            this.toolNext.Name = "toolNext";
            this.toolNext.Size = new System.Drawing.Size(118, 22);
            this.toolNext.Text = "下页";
            this.toolNext.Click += new System.EventHandler(this.toolNext_Click);
            // 
            // toolStripButton1
            // 
            this.toolStripButton1.Name = "toolStripButton1";
            this.toolStripButton1.Size = new System.Drawing.Size(6, 25);
            // 
            // rptViewer
            // 

            this.rptViewer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rptViewer.LocalReport.ReportEmbeddedResource = "RDLCPrint.rptWuLiao.rdlc";
            this.rptViewer.Location = new System.Drawing.Point(0, 25);
            this.rptViewer.Name = "rptViewer";
            this.rptViewer.ShowToolBar = false;
            this.rptViewer.Size = new System.Drawing.Size(698, 486);
            this.rptViewer.TabIndex = 1;
            this.rptViewer.Drillthrough += new Microsoft.Reporting.WinForms.DrillthroughEventHandler(this.rptViewer_Drillthrough);
            this.rptViewer.LocalReport.SubreportProcessing += new Microsoft.Reporting.WinForms.SubreportProcessingEventHandler(this.rptViewer_SubreportProcessing);
            // 
            // PreviewDialog
            // 
            this.PreviewDialog.AutoScrollMargin = new System.Drawing.Size(0, 0);
            this.PreviewDialog.AutoScrollMinSize = new System.Drawing.Size(0, 0);
            this.PreviewDialog.ClientSize = new System.Drawing.Size(400, 300);
            this.PreviewDialog.Enabled = true;
            this.PreviewDialog.Icon = ((System.Drawing.Icon)(resources.GetObject("PreviewDialog.Icon")));
            this.PreviewDialog.Name = "printPreviewDialog1";
            this.PreviewDialog.Visible = false;
            // 
            // ReportViewerFrm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(698, 511);
            this.Controls.Add(this.rptViewer);
            this.Controls.Add(this.dnReport);
            this.Name = "ReportViewerFrm";
            this.Text = "打印预览";
            this.Load += new System.EventHandler(this.ReportViewer_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dnReport)).EndInit();
            this.dnReport.ResumeLayout(false);
            this.dnReport.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        void rptViewer_SubreportProcessing(object sender, Microsoft.Reporting.WinForms.SubreportProcessingEventArgs e)
        {
            int i = 0;
            i++;
        }

        #endregion

        private System.Windows.Forms.BindingNavigator dnReport;
        private Microsoft.Reporting.WinForms.ReportViewer rptViewer;
        private System.Windows.Forms.PrintPreviewDialog PreviewDialog;
        private System.Windows.Forms.ToolStripSplitButton toolExport;
        private System.Windows.Forms.ToolStripMenuItem toolExcel;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripButton toolRefresh;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator4;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator5;
        private System.Windows.Forms.ToolStripSplitButton tspbNavigation;
        private System.Windows.Forms.ToolStripMenuItem toolFirst;
        private System.Windows.Forms.ToolStripMenuItem toolLast;
        private System.Windows.Forms.ToolStripMenuItem toolPrevious;
        private System.Windows.Forms.ToolStripMenuItem toolNext;
        private System.Windows.Forms.ToolStripSplitButton tspbZoom;
        private System.Windows.Forms.ToolStripMenuItem tool25;
        private System.Windows.Forms.ToolStripMenuItem tool50;
        private System.Windows.Forms.ToolStripMenuItem tool100;
        private System.Windows.Forms.ToolStripMenuItem tool200;
        private System.Windows.Forms.ToolStripMenuItem tool400;
        private System.Windows.Forms.ToolStripSplitButton tspbPrint;
        private System.Windows.Forms.ToolStripMenuItem toolPageSettings;
        private System.Windows.Forms.ToolStripMenuItem toolPreview;
        private System.Windows.Forms.ToolStripMenuItem toolPrint;
        private System.Windows.Forms.ToolStripMenuItem toolWhole;
        private System.Windows.Forms.ToolStripMenuItem toolPageWidth;
        private System.Windows.Forms.ToolStripSeparator toolStripButton1;

       

    }
}