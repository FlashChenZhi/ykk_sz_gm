using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PrintCenter;
using PrintCenterCommon;

namespace PrintCenter
{
    public partial class PageSettingsFrm : System.Windows.Forms.Form
    {
        public PageSettingsFrm(string ReportName)
        {
            InitializeComponent();
            this.m_ReportName = ReportName;
        }
        
        /// <summary>
        /// ReportName
        /// </summary>
        private string m_ReportName = string.Empty;

        /// <summary>
        /// Dataset used to read and write the config file in XML format
        /// </summary>
        private System.Data.DataSet dsReportSetting = new DataSet();

        /// <summary>
        /// Datatable saved the information of Current Report
        /// </summary>
        private System.Data.DataTable dtReportSetting = new DataTable();

        /// <summary>
        /// Datarow used to add new config item
        /// </summary>
        private System.Data.DataRow drReportSetting = null;


        #region Custom Function


        /// <summary>
        /// Get the list of available printers
        /// </summary>
        private void GetPrinters()
        {
            UIHelper.loadPrinterCombo(cmbPrinter);
            //System.Collections.ArrayList alPrinters = Printer.GetPrinterList();
            //for (int i = 0; i < alPrinters.Count; i++)
            //    this.cmbPrinter.Items.Add(alPrinters[i].ToString());
            //alPrinters.Clear();
            //alPrinters = null;
        }

        /// <summary>
        /// Use the selected form to set the pageheight and pagewidth
        /// </summary>
        /// <param name="FormName">the selected form</param>
        private void GetForm(string FormName)
        {
            System.Drawing.Printing.PrintDocument pd = new System.Drawing.Printing.PrintDocument();

            pd.PrinterSettings.PrinterName = this.cmbPrinter.Text;

            foreach (System.Drawing.Printing.PaperSize ps in pd.PrinterSettings.PaperSizes)
            {
                if (ps.PaperName == FormName)
                {
                    //Convert inch to cm
                    this.txtFormHeight.Text = Printer.FromInchToCM(System.Convert.ToDecimal(ps.Height.ToString())).ToString("#.00");
                    this.txtFormWidth.Text = Printer.FromInchToCM(System.Convert.ToDecimal(ps.Width.ToString())).ToString("#.00");
                }
            }            

            pd.Dispose();
        }
        
        /// <summary>
        /// Get the form list of a printer
        /// </summary>
        private void GetForms()
        {
            this.cmbForm.Items.Clear();

            if (this.cmbPrinter.Text.Trim() == string.Empty)
                return;

            System.Drawing.Printing.PrintDocument pd = new System.Drawing.Printing.PrintDocument();

            pd.PrinterSettings.PrinterName = this.cmbPrinter.Text;

            foreach (System.Drawing.Printing.PaperSize ps in pd.PrinterSettings.PaperSizes)
                this.cmbForm.Items.Add(ps.PaperName);

            pd.Dispose();
        }

        /// <summary>
        /// Read the config file
        /// </summary>
        private void ReadSetting()
        {

            this.dsReportSetting.ReadXml(System.Windows.Forms.Application.StartupPath + @"\ReportSettings.xml");

            this.GetPrinters();

            foreach( System.Data.DataTable dt in this.dsReportSetting.Tables )
            {
                if (dt.TableName == this.m_ReportName)
                {
                    this.dtReportSetting = dt;
                    break;
                }
            }

            if (this.dtReportSetting.Rows.Count != 0)
            {
                this.drReportSetting = this.dtReportSetting.Rows[0];

                if (! Printer.PrinterInList(this.drReportSetting["PrinterName"].ToString()))
                {
                    this.epError.SetError(this.cmbPrinter, " There is not an available printer in system!");
                    return;
                }
                else
                {
                    this.cmbPrinter.Text = this.drReportSetting["PrinterName"].ToString();
                    this.GetForms();

                    if (!Printer.FormInPrinter(this.cmbPrinter.Text.Trim(), this.drReportSetting["PaperName"].ToString()))
                    {
                        this.epError.SetError(this.cmbForm, " There is not a form in the specific printer!");
                        return;
                    }
                    else
                    {
                        this.cmbForm.Text = this.drReportSetting["PaperName"].ToString();
                        this.GetForm(this.cmbForm.Text);

                        if (!Printer.FormSameSize(this.cmbPrinter.Text.Trim(), this.cmbForm.Text.Trim(), System.Convert.ToDecimal(this.txtFormWidth.Text.Trim()), System.Convert.ToDecimal(this.txtFormHeight.Text.Trim())))
                        {
                            this.epError.SetError(this.txtFormHeight, " Pageheight should be the same with the one in the system!");
                            this.epError.SetError(this.txtFormWidth, " Pagewidth should be the same with the one in the system!");
                        }
                    }

                }

                if (dtReportSetting.Rows[0]["Orientation"].ToString() == "H")
                    this.radH.Checked = true;
                else if (dtReportSetting.Rows[0]["Orientation"].ToString() == "Z")
                    this.radZ.Checked = true;

                this.txtLeft.Text = this.drReportSetting["MarginLeft"].ToString();
                this.txtRight.Text = this.drReportSetting["MarginRight"].ToString();
                this.txtTop.Text = this.drReportSetting["MarginTop"].ToString();
                this.txtBottom.Text = this.drReportSetting["MarginBottom"].ToString();

            }
            else
            {
                this.dtReportSetting.TableName = this.m_ReportName;
                this.dtReportSetting.Columns.Add("ReportName", System.Type.GetType("System.String"));
                this.dtReportSetting.Columns.Add("PrinterName", System.Type.GetType("System.String"));
                this.dtReportSetting.Columns.Add("PaperName", System.Type.GetType("System.String"));
                this.dtReportSetting.Columns.Add("PageWidth", System.Type.GetType("System.String"));
                this.dtReportSetting.Columns.Add("PageHeight", System.Type.GetType("System.String"));
                this.dtReportSetting.Columns.Add("MarginTop", System.Type.GetType("System.String"));
                this.dtReportSetting.Columns.Add("MarginBottom", System.Type.GetType("System.String"));
                this.dtReportSetting.Columns.Add("MarginLeft", System.Type.GetType("System.String"));
                this.dtReportSetting.Columns.Add("MarginRight", System.Type.GetType("System.String"));
                this.dtReportSetting.Columns.Add("Orientation", System.Type.GetType("System.String"));
                this.drReportSetting = this.dtReportSetting.NewRow();                
            }

        }

        /// <summary>
        /// Write the config file.
        /// </summary>
        private bool WriteSetting()
        {

            bool bolRet = false;

            if (this.cmbPrinter.Text.Trim() == string.Empty)
            {
                this.epError.SetError(this.cmbPrinter, "Pls select a printer before you save the setting!");
                return false;
            }

            if (!Printer.PrinterInList(this.cmbPrinter.Text.Trim()))
            {
                this.epError.SetError(this.cmbPrinter, " The selected printer should be in your system!");
                return false;
            }

            if (this.cmbForm.Text.Trim() == string.Empty)
            {
                this.epError.SetError(this.cmbForm, "Pls select a form before you save the setting!");
                return false;
            }

            if (!Printer.FormInPrinter(this.cmbPrinter.Text.Trim(), this.cmbForm.Text.Trim()))
            {
                this.epError.SetError(this.cmbForm, "The form should be in the printer you select!");
                return false;
            }

            decimal decOut = 0;

            if (this.txtFormWidth.Text.Trim() != string.Empty)
            {
                if (!decimal.TryParse(this.txtFormWidth.Text, out decOut))
                {
                    this.epError.SetError(this.txtFormWidth, "Pagewidth should be a decimal with two decimals!");
                    return false;
                }
            }
            else
            {
                this.epError.SetError(this.txtFormWidth, "Pagewidth should be empty!");
                return false;
            }

            if (this.txtFormHeight.Text.Trim() != string.Empty)
            {
                if (!decimal.TryParse(this.txtFormHeight.Text, out decOut))
                {
                    this.epError.SetError(this.txtFormHeight, "Pageheight should be a decimal with two decimals!");
                    return false;
                }
            }
            else
            {
                this.epError.SetError(this.txtFormHeight, "Pagewidth should be empty!");
                return false;
            }

            if (!Printer.FormSameSize(this.cmbPrinter.Text.Trim(), this.cmbForm.Text.Trim(), System.Convert.ToDecimal(this.txtFormWidth.Text.Trim()), System.Convert.ToDecimal(this.txtFormHeight.Text.Trim())))
            {
                this.epError.SetError(this.txtFormHeight, "Pagewidth should be same with the selected form's height!");
                this.epError.SetError(this.txtFormWidth, "Pageheight should be same with the selected form's width!");
                return false;
            }

            if (!decimal.TryParse(this.txtLeft.Text, out decOut))
            {
                this.epError.SetError(this.txtLeft, "Left margin should be a decimal with two decimals!");
                return false;
            }

            if (!decimal.TryParse(this.txtRight.Text, out decOut))
            {
                this.epError.SetError(this.txtRight, "Right margin should be a decimal with two decimals!");
                return false;
            }

            if (!decimal.TryParse(this.txtTop.Text, out decOut))
            {
                this.epError.SetError(this.txtTop, "Top margin should be a decimal with two decimals!");
                return false;
            }

            if (!decimal.TryParse(this.txtBottom.Text, out decOut))
            {
                this.epError.SetError(this.txtBottom, "Bottom margin should be a decimal with two decimals!");
                return false;
            }
            
            this.drReportSetting["ReportName"] = this.m_ReportName;
            this.drReportSetting["PrinterName"] = this.cmbPrinter.Text.Trim();
            this.drReportSetting["PaperName"] = this.cmbForm.Text.Trim();
            this.drReportSetting["PageWidth"] = this.txtFormWidth.Text.Trim();
            this.drReportSetting["PageHeight"] = this.txtFormHeight.Text.Trim();

            if (this.txtTop.Text.Trim() != string.Empty)
                this.drReportSetting["MarginTop"] = this.txtTop.Text.Trim();
            else
                this.drReportSetting["MarginTop"] = "0";

            if (this.txtBottom.Text.Trim() != string.Empty)
                this.drReportSetting["MarginBottom"] = this.txtBottom.Text.Trim();
            else
                this.drReportSetting["MarginBottom"] = "0";

            if (this.txtLeft.Text.Trim() != string.Empty)
                this.drReportSetting["MarginLeft"] = this.txtLeft.Text.Trim();
            else
                this.drReportSetting["MarginLeft"] = "0";

            if (this.txtRight.Text.Trim() != string.Empty)
                this.drReportSetting["MarginRight"] = this.txtRight.Text.Trim();
            else
                this.drReportSetting["MarginRight"] = "0";

            if (this.radH.Checked)
                this.drReportSetting["Orientation"] = "H";
            else
                this.drReportSetting["Orientation"] = "Z";

            if (this.dtReportSetting.Rows.IndexOf(this.drReportSetting) == -1)
                this.dtReportSetting.Rows.Add(this.drReportSetting);

            if (this.dsReportSetting.Tables.IndexOf(this.dtReportSetting) == -1)
                this.dsReportSetting.Tables.Add(this.dtReportSetting);

            this.dsReportSetting.WriteXml(System.Windows.Forms.Application.StartupPath + @"\ReportSettings.xml");

            bolRet = true;

            return bolRet;

        }
        

        #endregion


        #region System Event


        private void frmPageSettings_Load(object sender, EventArgs e)
        {
            this.ReadSetting();
        }
        
        private void cmbPrinter_SelectedValueChanged(object sender, EventArgs e)
        {
            this.GetForms();
        }

        private void btnStatus_Click(object sender, EventArgs e)
        {
            System.Windows.Forms.MessageBox.Show("Printer\"" + this.cmbPrinter.Text + "\" Status: \r\n    " + Printer.GetPrinterStatus(this.cmbPrinter.Text));
        }

        private void btnDefault_Click(object sender, EventArgs e)
        {
            this.cmbPrinter.Text = Printer.GetDeaultPrinterName();
            this.GetForms();
            this.cmbForm.Text = string.Empty;
            this.txtFormWidth.Text = string.Empty;
            this.txtFormHeight.Text = string.Empty;
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            if (this.cmbPrinter.Text.Trim() != string.Empty && this.txtForm.Text.Trim() != string.Empty && this.txtFormWidth.Text.Trim() != string.Empty && this.txtFormHeight.Text.Trim() != string.Empty)
            {

                System.Drawing.Printing.PrintDocument pd = new System.Drawing.Printing.PrintDocument();

                pd.PrinterSettings.PrinterName = this.cmbPrinter.Text.Trim();

                bool bolExist = false;

                foreach (System.Drawing.Printing.PaperSize ps in pd.PrinterSettings.PaperSizes)
                {
                    if (ps.PaperName == this.txtForm.Text.Trim())
                    {
                        bolExist = true;
                        break;
                    }
                }

                pd.Dispose();

                if (bolExist)
                {
                    System.Windows.Forms.MessageBox.Show("The added form already exists!Pls delete it first!");
                    return;
                }

                Printer.AddCustomPaperSize(this.cmbPrinter.Text, this.txtForm.Text.Trim(), System.Convert.ToSingle(this.txtFormWidth.Text.Trim()) * 10, System.Convert.ToSingle(this.txtFormHeight.Text.Trim()) * 10);
                this.GetForms();
                this.cmbForm.Text = this.txtForm.Text.Trim();
            }
            else
            {
                decimal decOut = 0;
                if (this.cmbPrinter.Text.Trim() == string.Empty)
                    this.epError.SetError(this.cmbPrinter, "Pls select a printer!");
                if (this.txtForm.Text.Trim() == string.Empty)
                    this.epError.SetError(this.txtForm, "Pls set a proper form!");
                if (this.txtFormHeight.Text.Trim() == string.Empty || !System.Decimal.TryParse(this.txtFormHeight.Text.Trim(), out decOut))
                    this.epError.SetError(this.txtFormHeight, "Pls set the value of pageheight!");
                if (this.txtFormWidth.Text.Trim() == string.Empty || !System.Decimal.TryParse(this.txtFormWidth.Text.Trim(), out decOut))
                    this.epError.SetError(this.txtFormWidth, "Pls set the value of pagewidth!");
            }
        }

        private void btnDelete_Click(object sender, EventArgs e)
        {
            if (this.cmbForm.Text.Trim() != string.Empty && this.cmbPrinter.Text.Trim() != string.Empty)
            {
                if (System.Windows.Forms.MessageBox.Show("Do you really wanna delete the selected form?", "", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
                {
                    Printer.DeleteCustomPaperSize(this.cmbPrinter.Text.Trim(), this.cmbForm.Text.Trim());
                    this.GetForms();
                    this.cmbForm.Text = string.Empty;
                }
            }
            else
            {
                if (this.cmbForm.Text.Trim() == string.Empty)
                    this.epError.SetError(this.cmbForm, "Pls select a form!");
                if (this.cmbPrinter.Text.Trim() == string.Empty)
                    this.epError.SetError(this.cmbPrinter, "Pls select a printer!");
            }
        }

        private void cmbForm_SelectedValueChanged(object sender, EventArgs e)
        {
            this.GetForm(this.cmbForm.Text);
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            if (!this.WriteSetting())
                return;
            
            this.dtReportSetting.Dispose();

            this.dsReportSetting.Dispose();

            this.Close();

        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }


        #endregion

    }
}