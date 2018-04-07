using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.IO;
using PrintCenterCommon;
using System.Xml.Serialization;
using System.Collections;

namespace PrintCenterProxy
{
    public partial class ReportSettingForm : Form
    {
        private IList<ReportSetting> reportSettingList = new List<ReportSetting>();
        private int lastSelectedIndex = -1;

        private IPrinterHelper printerHelper = null;

        public ReportSettingForm(IPrinterHelper printerHelper)
        {
            InitializeComponent();
            this.printerHelper = printerHelper;
        }

        private void ReportSettingsFrm_Load(object sender, EventArgs e)
        {
            loadReportSettingList();
            loadReportNameCombo();
            loadPrinterCombo();
            clearControls();
            this.btnDelete.Enabled = false;
            this.btnSelectDefault.PerformClick();
            //this.btnSelectDefault.PerformClick();

        }

        private void loadReportNameCombo()
        {
            cmbReportName.Items.Clear();
            ReportMappingCollection collection = getReportMappingCollection();
            IList<report_mapping> tempList = new List<report_mapping>();
            foreach (ReportSetting reportSetting in this.reportSettingList)
            {
                foreach (report_mapping mapping in collection)
                {
                    if (mapping.ExternalName == reportSetting.ReportName)
                    {
                        tempList.Add(mapping);
                    }
                }
            }

            foreach (report_mapping mapping in tempList)
            {
                collection.Remove(mapping);
            }

            foreach (report_mapping mapping in collection)
            {
                cmbReportName.Items.Add(mapping.ExternalName);
            }
        }

        private static ReportMappingCollection getReportMappingCollection()
        {
            Stream stream = null;
            try
            {
                XmlSerializer serializer = new XmlSerializer(typeof(ReportMappingCollection));
                stream = File.Open(Application.StartupPath + @"/report_mapping_list.xml", FileMode.Open, FileAccess.Read);
                ReportMappingCollection collection = serializer.Deserialize(stream) as ReportMappingCollection;
                return collection;
            }
            finally
            {
                if (stream != null)
                {
                    stream.Close();
                }
            }
        }

        private void loadReportSettingList()
        {
            string reportSettingsDir = Application.StartupPath + @"\PrintSetting\ReportSetting";
            if (!Directory.Exists(reportSettingsDir))
            {
                Directory.CreateDirectory(reportSettingsDir);
            }
            string[] fileList = Directory.GetFiles(reportSettingsDir, "report*.xml");
            reportSettingList = getReportSettingList(fileList);
            foreach (ReportSetting item in reportSettingList)
            {
                this.lstvReportName.Items.Add(item.ReportName);
            }
        }

        private IList<ReportSetting> getReportSettingList(string[] fileList)
        {
            List<ReportSetting> tempList = new List<ReportSetting>();
            XmlDocument doc = new XmlDocument();

            for (int i = 0; i < fileList.Length; ++i)
            {
                string reportName = string.Empty;
                Stream stream = null;
                try
                {
                    XmlSerializer serializer = new XmlSerializer(typeof(ReportSetting));
                    stream = File.Open(fileList[i], FileMode.Open, FileAccess.Read);
                    ReportSetting reportSetting = serializer.Deserialize(stream) as ReportSetting;
                    reportSetting.SettingFilePath = fileList[i];
                    tempList.Add(reportSetting);
                }
                catch
                {
                    //ignore exception
                }
            }

            ReportMappingCollection collection = getReportMappingCollection();
            IList<ReportSetting> reportSettingList = new List<ReportSetting>();
            foreach (report_mapping mapping in collection)
            {
                foreach (ReportSetting reportSetting in tempList)
                {
                    if (reportSetting.ReportName == mapping.ExternalName)
                    {
                        reportSettingList.Add(reportSetting);
                    }
                }
            }

            //foreach (ReportSetting reportSetting in reportSettingList)
            //{
            //    tempList.Remove(reportSetting);
            //}

            //foreach (ReportSetting reportSetting in tempList)
            //{
            //    File.Delete(reportSetting.SettingFilePath);
            //}

            return reportSettingList;
        }

        private void loadPrinterCombo()
        {
            this.cmbPrinter.DataSource = null;
            cmbPrinter.DataSource = printerHelper.getPrinterNameList();
        }

        private void cmbPrinter_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.printerError.Clear();
            this.cmbPaper.DataSource = null;
            if (this.cmbPrinter.Text == string.Empty)
            {
                return;
            }
            this.cmbPaper.DataSource = printerHelper.getPaperNameList(this.cmbPrinter.Text);
        }

        private void btnSelectDefault_Click(object sender, EventArgs e)
        {
            Stream stream = null;
            try
            {
                XmlSerializer serializer = new XmlSerializer(typeof(PrintSetting));
                stream = File.Open(Application.StartupPath + @"/PrintSetting/DefaultPrintSetting.xml", FileMode.Open, FileAccess.Read);
                PrintSetting defaultReportSetting = serializer.Deserialize(stream) as PrintSetting;
                
                fillControls(defaultReportSetting);
                this.lblWarning.Visible = false;
            }
            catch
            {
                this.lblWarning.Visible = true;
                this.cmbPrinter.Text = printerHelper.getDeaultPrinterName();
                this.cmbPrinter_SelectedIndexChanged(this.cmbPrinter, null);
            }
            finally
            {
                if (stream != null)
                {
                    stream.Close();
                }
            }
        }

        private void cmbPaper_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.paperError.Clear();
            DecimalSize size = printerHelper.getPaperSize(cmbPrinter.Text, cmbPaper.Text);
            this.txtPaperHeight.Text = size.Height.HasValue ? size.Height.Value.ToString("#.00") : string.Empty;
            this.txtPaperWidth.Text = size.Width.HasValue ? size.Width.Value.ToString("#.00") : string.Empty;
        }

        private void btnBrowse_Click(object sender, EventArgs e)
        {

        }

        private void lstvReportName_SelectedIndexChanged(object sender, EventArgs e)
        {
            PrintSetting printSetting = null;

            if (lstvReportName.SelectedItems.Count == 0)
            {
                if (lastSelectedIndex >= 0)
                {
                    printSetting = getPrintSetting(lstvReportName.Items[lastSelectedIndex].Text);
                    initPrintSettingEntity(printSetting);
                }
                clearControls();
                this.btnDelete.Enabled = false;
                return;
            }

            this.btnDelete.Enabled = true;

            //if (lastSelectedIndex == lstvReportName.SelectedItems[0].Index) return;

            lastSelectedIndex = lstvReportName.SelectedItems[0].Index;
            printSetting = getPrintSetting(lstvReportName.SelectedItems[0].Text);
            if (printSetting == null)
            {
                clearControls();
            }
            else
            {
                fillControls(printSetting);
            }
        }

        private void initPrintSettingEntity(PrintSetting printSetting)
        {
            printSetting.PrinterName = this.cmbPrinter.Text;
            printSetting.PaperName = this.cmbPaper.Text;
            printSetting.PaperWidth = this.txtPaperWidth.Text;
            printSetting.PaperHeight = this.txtPaperHeight.Text;
            printSetting.Orientation = this.radHorizontal.Checked ? "H" : "V";
            printSetting.MarginTop = this.nudMarginTop.Value.ToString();
            printSetting.MarginBottom = this.nudMarginBottom.Value.ToString();
            printSetting.MarginLeft = this.nudMarginLeft.Value.ToString();
            printSetting.MarginRight = this.nudMarginRight.Value.ToString();
        }

        private PrintSetting getPrintSetting(string reportName)
        {
            PrintSetting printSetting = null;
            foreach (ReportSetting item in reportSettingList)
            {
                if (item.ReportName == reportName)
                {
                    //settingFilePath = item.SettingFilePath;
                    printSetting = item.PrintSetting;
                    break;
                }
            }
            return printSetting;
        }

        private void fillControls(PrintSetting setting)
        {
            //if (!fillCmbPrinter(setting)) return;
            //if (!fillCmbPaper(setting)) return;

            //fillCmbPrinter(setting);
            //fillCmbPaper(setting);

            this.cmbPrinter.Text = string.Empty;
            this.cmbPrinter.Text = setting.PrinterName;
            this.cmbPaper.Text = string.Empty;
            this.cmbPaper.Text = setting.PaperName;
            this.radVertical.Checked = setting.Orientation == "V" ? true : false;
            this.radHorizontal.Checked = !this.radVertical.Checked;

            setNumericUpDown(this.nudMarginTop, setting.MarginTop);
            setNumericUpDown(this.nudMarginBottom, setting.MarginBottom);
            setNumericUpDown(this.nudMarginLeft, setting.MarginLeft);
            setNumericUpDown(this.nudMarginRight, setting.MarginRight);

        }

        private void setNumericUpDown(NumericUpDown numericUpDown, string decimalValue)
        {
            try
            {
                numericUpDown.Value = decimal.Parse(decimalValue);
            }
            catch
            {
                numericUpDown.Value = 0;
            }
        }

        private bool fillCmbPaper(PrintSetting setting)
        {
            int index = isTextInComboBox(this.cmbPaper, setting.PaperName);

            if (index < 0)
            {
                this.cmbPaper.Text = setting.PaperName.Trim();
                if (cmbPaper.Text == string.Empty)
                {
                    this.paperError.SetError(this.cmbPaper, "纸张类型未设定！");
                }
                else
                {
                    this.paperError.SetError(this.cmbPaper, "纸张类型不存在！");
                }
                return false;
            }

            this.cmbPaper.Text = setting.PaperName;
            if (this.cmbPaper.SelectedIndex == index)
            {
                this.cmbPaper_SelectedIndexChanged(null, null);
            }
            else
            {
                this.cmbPaper.SelectedIndex = index;
            }
            return true;
        }

        private bool fillCmbPrinter(PrintSetting setting)
        {
            int index = isTextInComboBox(this.cmbPrinter, setting.PrinterName);

            this.cmbPrinter.Text = setting.PrinterName;
            if (this.cmbPrinter.SelectedIndex == index)
            {
                this.cmbPrinter_SelectedIndexChanged(null, null);
            }
            else
            {
                this.cmbPrinter.SelectedIndex = index;
            }
            return true;
        }

        private int isTextInComboBox(ComboBox comboBox, string text)
        {
            int index = -1;
            for (int i = 0; i < comboBox.Items.Count; ++i)
            {
                string tempText = comboBox.Items[i] as string;
                if (text == tempText)
                {
                    index = i;
                    break;
                }
            }
            return index;
        }

        private void clearControls()
        {
            this.cmbPrinter.Text = string.Empty;
            this.cmbPrinter_SelectedIndexChanged(null, null);
            this.txtPaperHeight.Text = string.Empty;
            this.txtPaperWidth.Text = string.Empty;
            this.radHorizontal.Checked = false;
            this.radVertical.Checked = true;
            this.nudMarginTop.Value = 0;
            this.nudMarginBottom.Value = 0;
            this.nudMarginLeft.Value = 0;
            this.nudMarginRight.Value = 0;
        }

        private string readInnerText(XmlDocument doc, string tagName)
        {
            try
            {
                return doc.SelectSingleNode("/report_setting/" + tagName).InnerText;
            }
            catch (Exception ex)
            {
                throw new Exception(string.Format("failed to read inner text of tag '{0}'.", tagName), ex);
            }
        }

        private string writeToXml(XmlDocument doc, string filePath)
        {
            XmlWriter writer = null;
            FileStream stream = null;
            try
            {
                string filepath = filePath;
                while (filepath == string.Empty)
                {
                    filepath = generateReportSettingFileName();
                }
                stream = new FileStream(filepath, FileMode.Create);
                writer = XmlWriter.Create(stream);
                doc.WriteContentTo(writer);
                return filepath;
            }
            catch
            {
                throw new Exception("Failed to create report setting file.");
            }
            finally
            {
                if (writer != null)
                {
                    writer.Flush();
                    writer.Close();
                }

                if (stream != null)
                {
                    stream.Close();
                }
            }
        }

        private string generateReportSettingFileName()
        {
            DateTime now = DateTime.Now;
            string reportSettingsDir = Application.StartupPath + @"\PrintSetting\ReportSetting";
            if (!Directory.Exists(reportSettingsDir))
            {
                Directory.CreateDirectory(reportSettingsDir);
            }
            StringBuilder builder = new StringBuilder("report");
            builder.Append(now.Year);
            builder.Append(now.Month.ToString().PadLeft(2, '0'));
            builder.Append(now.Day.ToString().PadLeft(2, '0'));
            builder.Append(now.Hour.ToString().PadLeft(2, '0'));
            builder.Append(now.Minute.ToString().PadLeft(2, '0'));
            builder.Append(now.Second.ToString().PadLeft(2, '0'));
            builder.Append(now.Millisecond.ToString().PadLeft(3, '0'));
            string filename = builder.ToString();
            return reportSettingsDir + @"\" + filename + ".xml";
        }

        private XmlDocument createXmlDocument(ReportSetting item)
        {
            XmlDocument doc = new XmlDocument();
            doc.AppendChild(doc.CreateXmlDeclaration("1.0", "utf-8", null));
            XmlNode root = doc.AppendChild(doc.CreateElement("report_setting"));
            root.AppendChild(doc.CreateElement("report_name")).InnerText = item.ReportName;
            root.AppendChild(doc.CreateElement("report_file_path")).InnerText = item.PrintSetting.ReportFilePath;
            root.AppendChild(doc.CreateElement("printer_name")).InnerText = item.PrintSetting.PrinterName;
            root.AppendChild(doc.CreateElement("paper_name")).InnerText = item.PrintSetting.PaperName;
            root.AppendChild(doc.CreateElement("paper_width")).InnerText = item.PrintSetting.PaperWidth;
            root.AppendChild(doc.CreateElement("paper_height")).InnerText = item.PrintSetting.PaperHeight;
            root.AppendChild(doc.CreateElement("margin_top")).InnerText = item.PrintSetting.MarginTop;
            root.AppendChild(doc.CreateElement("margin_bottom")).InnerText = item.PrintSetting.MarginBottom;
            root.AppendChild(doc.CreateElement("margin_left")).InnerText = item.PrintSetting.MarginLeft;
            root.AppendChild(doc.CreateElement("margin_right")).InnerText = item.PrintSetting.MarginRight;
            root.AppendChild(doc.CreateElement("orientation")).InnerText = item.PrintSetting.Orientation;
            return doc;
        }

        private void cmbPrinter_TextChanged(object sender, EventArgs e)
        {
            int index = isTextInComboBox(this.cmbPrinter, this.cmbPrinter.Text);

            if (index < 0)
            {
                if (cmbPrinter.Text == string.Empty)
                {
                    this.printerError.SetError(this.cmbPrinter, "打印机未设定！");
                }
                else
                {
                    this.printerError.SetError(this.cmbPrinter, "打印机不存在！");
                }
            }
            else
            {
                this.cmbPrinter.SelectedIndex = index;
                this.cmbPrinter_SelectedIndexChanged(null, null);
            }
        }

        private void cmbPaper_TextChanged(object sender, EventArgs e)
        {
            int index = isTextInComboBox(this.cmbPaper, this.cmbPaper.Text);

            if (index < 0)
            {
                if (cmbPaper.Text == string.Empty)
                {
                    this.paperError.SetError(this.cmbPaper, "纸张类型未设定！");
                }
                else
                {
                    this.paperError.SetError(this.cmbPaper, "纸张类型不存在！");
                }
            }
            else
            {
                this.cmbPaper.SelectedIndex = index;
                this.cmbPaper_SelectedIndexChanged(null, null);
            }
        }

        private void btnSave_Click(object sender, EventArgs e)
        {
            try
            {
                if (lstvReportName.SelectedItems.Count > 0)
                {
                    PrintSetting printSetting = getPrintSetting(lstvReportName.SelectedItems[0].Text);
                    initPrintSettingEntity(printSetting);
                }

                foreach (ReportSetting reportSetting in reportSettingList)
                {
                    if (reportSetting.PrintSetting.PrinterName == string.Empty || reportSetting.PrintSetting.PaperName == string.Empty)
                    {
                        CommonHelper.ShowWarningBox(string.Format("请为报表'[0]'选择打印机和纸张。"));
                        return;
                    }
                    saveToXml(reportSetting);
                }

                CommonHelper.ShowSuccessBox("成功保存报表设定文件。");
            }
            catch (Exception ex)
            {
                CommonHelper.ShowErrorBox("保存报表设定文件是发生错误。" + ex.Message);
            }
        }

        private void saveToXml(ReportSetting reportSetting)
        {
            Stream stream = null;
            try
            {
                if (reportSetting.SettingFilePath == string.Empty)
                {
                    reportSetting.SettingFilePath = generateReportSettingFileName();
                }
                XmlSerializer serializer = new XmlSerializer(typeof(ReportSetting));
                stream = File.Open(reportSetting.SettingFilePath, FileMode.Create, FileAccess.Write);
                serializer.Serialize(stream, reportSetting);
            }
            finally
            {
                if (stream != null)
                {
                    stream.Close();
                }
            }
        }

        private void lstvReportName_AfterLabelEdit(object sender, LabelEditEventArgs e)
        {
            if (e.Label == null) return;
            ReportSetting item = reportSettingList[e.Item];
            item.ReportName = e.Label;
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            string reportName = this.cmbReportName.Text;
            if (reportName == string.Empty)
            {
                return;
            }

            bool doesExist = false;
            foreach (ReportSetting item in reportSettingList)
            {
                if (item.ReportName == reportName)
                {
                    doesExist = true;
                    break;
                }
            }

            if (doesExist)
            {
                CommonHelper.ShowWarningBox(string.Format("已经存在名为'{0}'的报表设定文件", reportName));
                return;
            }

            try
            {
                ReportSetting reportSetting = new ReportSetting(reportName, string.Empty, new PrintSetting());
                saveToXml(reportSetting);
                this.reportSettingList.Add(reportSetting);
                this.lstvReportName.Items.Add(reportName);
                foreach (ListViewItem listViewItem in this.lstvReportName.SelectedItems)
                {
                    listViewItem.Selected = false;
                }
                this.lstvReportName.Items[this.lstvReportName.Items.Count - 1].Selected = true;
                this.cmbReportName.Text = string.Empty;
                loadReportNameCombo();
            }
            catch (Exception ex)
            {
                CommonHelper.ShowErrorBox("创建新的报表设定文件时发生错误。" + ex.Message);
            }
        }

        private void btnDelete_Click(object sender, EventArgs e)
        {
            if (this.lstvReportName.SelectedItems.Count > 0)
            {

                ReportSetting itemToDelete = null;
                foreach (ReportSetting item in reportSettingList)
                {
                    if (item.ReportName == lstvReportName.SelectedItems[0].Text)
                    {
                        itemToDelete = item;
                    }
                }

                if (itemToDelete == null) return;

                if (CommonHelper.ShowConfirmBox(string.Format("是否删除报表'{0}'的设定文件？", itemToDelete.ReportName), "确认删除"))
                {
                    try
                    {
                        reportSettingList.Remove(itemToDelete);
                        File.Delete(itemToDelete.SettingFilePath);
                        this.lastSelectedIndex = -1;
                        lstvReportName.Items.Remove(lstvReportName.SelectedItems[0]);
                        loadReportNameCombo();
                    }
                    catch (Exception ex)
                    {
                        CommonHelper.ShowErrorBox("删除报表设定文件时发生错误。" + ex.Message);
                    }
                }
            }
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnSaveAsDefault_Click(object sender, EventArgs e)
        {
            Stream stream = null;
            try
            {
                PrintSetting defaultSetting = new PrintSetting();
                defaultSetting.PrinterName = this.cmbPrinter.Text;
                defaultSetting.PaperName = this.cmbPaper.Text;
                defaultSetting.PaperWidth = this.txtPaperWidth.Text;
                defaultSetting.PaperHeight = this.txtPaperHeight.Text;
                defaultSetting.Orientation = this.radHorizontal.Checked ? "H" : "V";
                defaultSetting.MarginTop = this.nudMarginTop.Value.ToString();
                defaultSetting.MarginBottom = this.nudMarginBottom.Value.ToString();
                defaultSetting.MarginLeft = this.nudMarginLeft.Value.ToString();
                defaultSetting.MarginRight = this.nudMarginRight.Value.ToString();

                XmlSerializer serializer = new XmlSerializer(typeof(PrintSetting));
                string defaultSettingFileDirectory = Application.StartupPath + @"/PrintSetting";
                if (!Directory.Exists(defaultSettingFileDirectory))
                {
                    Directory.CreateDirectory(defaultSettingFileDirectory);
                }
                string defaultSettingFilePath = defaultSettingFileDirectory + @"/DefaultPrintSetting.xml";
                stream = File.Open(defaultSettingFilePath, FileMode.Create, FileAccess.Write);
                serializer.Serialize(stream, defaultSetting);
                this.lblWarning.Visible = false;
                CommonHelper.ShowSuccessBox("成功保存默认打印机。");
            }
            catch (Exception ex)
            {
                CommonHelper.ShowErrorBox("保存默认打印机时发生错误。" + ex.Message);
            }
            finally
            {
                if (stream != null)
                {
                    stream.Close();
                }
            }
        }
    }
}