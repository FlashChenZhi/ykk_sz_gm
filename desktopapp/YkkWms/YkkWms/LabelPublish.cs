using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Forms;
using PersistenceLayer;
using BusinessEntity;

namespace YkkWms
{
    public partial class LabelPublish : Form
    {
        List<FNSIJIEntity> sijis;
        public LabelPublish(List<FNSIJIEntity> sijis)
        {
            this.sijis = sijis;
            InitializeComponent();
        }

        private void LablePublish_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.F4:
                    okBtn.PerformClick();
                    break;
                case Keys.F5:
                    reprintBtn.PerformClick();
                    break;
                default:
                    break;
            }
        }

        protected override void OnClosing(CancelEventArgs e)
        {
            base.OnClosing(e);
            this.DialogResult = DialogResult.OK;
        }

        private void okBtn_Click(object sender, EventArgs e)
        {
            try
            {
                string stationNo = string.Empty;

                if (GlobalAccess.StationNo == "1202")
                {
                    stationNo = "1105";
                }
                else if (GlobalAccess.StationNo == "2209")
                {
                    stationNo = "2104";
                }
                else if (GlobalAccess.StationNo == "2206")
                {
                    stationNo = "2103";
                }
                else if (GlobalAccess.StationNo == "3202")
                {
                    stationNo = "3101";
                }
                else if (GlobalAccess.StationNo == "3205")
                {
                    stationNo = "3102";
                }
                else if (GlobalAccess.StationNo == "1212")
                {
                    stationNo = "1106";
                }
                else if (GlobalAccess.StationNo == "1215")
                {
                    stationNo = "1107";
                }
                else if (GlobalAccess.StationNo == "1218")
                {
                    stationNo = "1108";
                }
                UpdateCriteria uc = new UpdateCriteria(typeof(FNHANSOEntity));
                Condition c = uc.GetNewCondition();
                c.AddEqualTo(FNHANSOEntity.__MOTOSTNO, stationNo);
                c.AddEqualTo(FNHANSOEntity.__HJYOTAIFLG, "0");
                uc.AddAttributeForUpdate(FNHANSOEntity.__HJYOTAIFLG, "1");
                uc.Perform();
                DbAccess.callAfterStockin(string.Empty);
                this.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void reprintBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (sijis != null)
                {
                    if (sijis.Count > 0)
                    {
                        UpdateCriteria uc = new UpdateCriteria(typeof(FNLABELEntity));
                        foreach (FNSIJIEntity siji in sijis)
                        {
                            Condition c = uc.GetNewCondition();
                            c.AddEqualTo(FNLABELEntity.__LABEL_KEY, siji.LABEL_KEY);
                        }
                        uc.AddAttributeForUpdate(FNLABELEntity.__PRINTING_FLAG, "1");
                        uc.AddAttributeForUpdate(FNLABELEntity.__UPDATE_DATETIME, DateTime.Now.ToString("yyyyMMddHHmmss"));
                        uc.Perform();
                        MessageBox.Show("设定成功");
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}