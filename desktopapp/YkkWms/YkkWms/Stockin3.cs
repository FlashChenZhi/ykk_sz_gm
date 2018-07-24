using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PersistenceLayer;
using BusinessEntity;
using System.Data.OleDb;
using System.Threading;

namespace YkkWms
{
    public partial class Stockin3 : Form
    {
        //��⵽�����ҵ,�л�������ģʽ,�ڲ���ģʽ��,timer��������,�����趨֮��,�л����ǲ���ģʽ,timer���¿�ʼ
        private bool isOperating;
        private FNTOUCYAKUEntity touCyaKu;
        private FNZAIKOEntity zaiKo;
        private FMZKEYEntity zKey;
        private FMBUCKETEntity bucket;


        public Stockin3()
        {
            InitializeComponent();

        }

        private void Stockin1_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.F2:
                    setBtn.PerformClick();
                    break;
                case Keys.F12:
                    quitBtn.PerformClick();
                    break;
                case Keys.F9:
                    cancelBtn.PerformClick();
                    break;
                case Keys.F5:
                    weightLoadBtn.PerformClick();
                    break;
                case Keys.F7:
                    exportBtn.PerformClick();
                    break;
                case Keys.F8:
                    inputBt.PerformClick();
                    break;
                default:
                    break;
            }
        }

        private bool DoBucketNo()
        {
            string bucketNo = bucketNoBox.Text;
            zaiKo = DbAccess.GetZaiKoByBucketNo(bucketNo);
            if (zaiKo == null)
            {
                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                msgBox.Text = "û�п������";
                itemCodeBox.Text = string.Empty;
                itemName1Box.Text = string.Empty;
                itemName2Box.Text = string.Empty;
                itemName3Box.Text = string.Empty;
                colorCodeBox.Text = string.Empty;
                planCountBox.Text = string.Empty;
                planWeightBox.Text = string.Empty;
                unitWeightBox.Text = string.Empty;
                return false;
            }
            else
            {
                if (zaiKo.STORAGE_PLACE_FLAG == "0") //��ƽ��
                {
                    DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                    msgBox.Text = "����Ѵ���";
                    return false;
                }

                zKey = DbAccess.GetManagedZKey(zaiKo.ZAIKEY);
                if (zKey == null)
                {
                    DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                    msgBox.Text = "û�д�Item������";
                    return false;
                }
                else
                {
                    msgBox.Clear();

                    itemCodeBox.Text = zKey.ZAIKEY;
                    itemName1Box.Text = zKey.ZKNAME1;
                    itemName2Box.Text = zKey.ZKNAME2;
                    itemName3Box.Text = zKey.ZKNAME3;
                    colorCodeBox.Text = zaiKo.COLOR_CODE;
                    planCountBox.Text = zaiKo.PLAN_QTY.ToString();
                    planWeightBox.Text = zaiKo.PLAN_WEIGHT.ToString();
                    unitWeightBox.Text = zaiKo.REAL_UNIT_WEIGHT.ToString();
                    plasticBagStatusBox.Text = zaiKo.BAG_FLAG.Trim() == "1" ? "��" : "��";
                    fixedWeightBox.Text = (GlobalAccess.FixedWeight * 1000).ToString(); 

                    DoRange(zaiKo); //����ԭ��λ,������������
                    return true;
                }
            }
        }

        private void DoRange(FNZAIKOEntity zaiKo)
        {
            FNRANGEEntity range = DbAccess.GetRange(zaiKo.MADE_SECTION, zaiKo.MADE_LINE);
            if (range == null)
            {
                itemWeightRangeLimitFromBox.Text = "100";
                itemWeightRangeLimitToBox.Text = "100";
            }
            else
            {
                itemWeightRangeLimitFromBox.Text = range.STORAGE_LOWER.ToString();
                itemWeightRangeLimitToBox.Text = range.STORAGE_UPPER.ToString();
            }
        }

        private void setUIOnNormalMode()
        {
            itemCountBox.ReadOnly = false;
            weightLoadBtn.Enabled = true;
            setBtn.Enabled = true;
            exportBtn.Enabled = true;

            itemCountBox.TabStop = true;
            weightLoadBtn.TabStop = true;
            setBtn.TabStop = true;
            exportBtn.TabStop = true;

            weightLoadBtn.Focus();
        }

        private void setUIOnEmptyBucketMode()
        {
            itemCountBox.ReadOnly = true;
            weightLoadBtn.Enabled = true;
            setBtn.Enabled = true;
            exportBtn.Enabled = true;

            itemCountBox.TabStop = false;
            weightLoadBtn.TabStop = true;
            setBtn.TabStop = true;
            exportBtn.TabStop = true;

            weightLoadBtn.Focus();
        }

        private void checkStockInMode()
        {
            FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
            if (station != null && station.NYUSYUMODE == Nyusyumode.Empty_Bucket)
            {
                if (statusBox.Text != "�����¼ģʽ")
                {
                    ClearAll();
                    setStatusBox("�����¼ģʽ");
                    setUIOnEmptyBucketMode();
                }
            }
            else
            {
                if (statusBox.Text != "����")
                {
                    ClearAll();
                    setStatusBox("����");
                    setUIOnNormalMode();
                }
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            try
            {
                datetimeLb.Text = DateTime.Now.ToString("yyyy/MM/dd HH:mm");
                if (!DbAccess.IsSystemOnline())
                {
                    setStatusBox("ϵͳOffline");
                    return;
                }
                else
                {
                    if (statusBox.Text == "ϵͳOffline")
                    {
                        setStatusBox(string.Empty);
                    }
                }

                if (!isOperating)
                {
                    checkStockInMode();

                    touCyaKu = DbAccess.GetTouCyaKu(GlobalAccess.StationNo);
                    if (touCyaKu != null)
                    {
                        if (DoTouCyaKu(touCyaKu))
                        {
                            weightLoadBtn.PerformClick();
                            if (!manCheck.Checked && !isRangeError)
                            {
                                if (statusBox.Text == "�����¼ģʽ" || itemCountBox.Value > 0)
                                {
                                    setBtn.PerformClick();
                                }
                                else 
                                {
                                    exportBtn.PerformClick();
                                }
                            }
                        }
                    }
                    else
                    {
                        ClearAll();
                    }
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private bool IsBucketExist(string bucketNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNZAIKOEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNZAIKOEntity.__BUCKET_NO, bucketNo);
            return (rc.AsEntity() != null);
        }

        private void setUIOnExport()
        {
            itemCountBox.ReadOnly = true;
            weightLoadBtn.Enabled = false;
            setBtn.Enabled = false;
            exportBtn.Enabled = true;

            itemCountBox.TabStop = false;
            weightLoadBtn.TabStop = false;
            setBtn.TabStop = false;
            exportBtn.TabStop = true;

            exportBtn.Focus();
        }   

        private bool DoTouCyaKu(FNTOUCYAKUEntity touCyaKu)
        {
            bucketNoBox.Text = touCyaKu.BUCKET_NO.Trim().ToUpper() == "BR" ? string.Empty : touCyaKu.BUCKET_NO;

            if (touCyaKu.BUCKET_NO.Trim().ToUpper() == "BR")
            {
                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                setStatusBox("����δ��ȡ");
                setUIOnExport();
                isOperating = true;
                return false;
            }

            FNHANSOEntity hanSo = DbAccess.GetHanSo(touCyaKu.MCKEY);
            if (hanSo == null)
            {
                setStatusBox("�ų�");
                touCyaKu.SYORIFLG = "1";
                touCyaKu.Save();
                setUIOnExport();
                isOperating = true;
                return false;
            }

            if (touCyaKu.HEIGHT_FLAG == "3")    //���θߵ��쳣
            {
                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Height_Error);
                setStatusBox("Bucket�߶��쳣");
                setUIOnExport();
                isOperating = true;
                return false;
            }

            FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
            if (station == null) return false;

            if (station.NYUSYUMODE == Nyusyumode.Empty_Bucket)
            {
                setStatusBox("�����¼ģʽ");
                isOperating = true;
                return true;
            }

            if (station.NYUSYUMODE == Nyusyumode.Normal)    //���ģʽ
            {
                bucket = DbAccess.GetBucket(touCyaKu.BUCKET_NO);
                if (bucket == null)
                {
                    DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                    setStatusBox("����δ��¼");
                    setUIOnExport();
                    isOperating = true;
                    return false;
                }

                bucketWeightBox.Text = bucket.PACKING_WEIGHT.ToString();
                if (bucket.HEIGHT_FLAG != touCyaKu.HEIGHT_FLAG)
                {
                    DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Height_Error);
                    setStatusBox("Bucket�߶��쳣");
                    setUIOnExport();
                    isOperating = true;
                    return false;
                }

                if (DbAccess.IsBucketInLocation(bucket.BUCKET_NO))
                {
                    DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                    setStatusBox("Bucket�ظ�");
                    setUIOnExport();
                    isOperating = true;
                    return false;
                }

                zaiKo = DbAccess.GetZaiKoByBucketNo(bucket.BUCKET_NO);
                if (zaiKo == null)
                {
                    DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                    setStatusBox("Bucketδ�趨");
                    setUIOnExport();
                    isOperating = true;
                    return false;
                }

                msgBox.Clear();
                setStatusBox("����");
                isOperating = true;
                ticketNoBox.Text = zaiKo.TICKET_NO;
                return DoBucketNo();
            }
            return false;
        }

        private void ShowInfo(string systemId)
        {
            FNZAIKOEntity zaiKo = DbAccess.GetZaiKoBySystemId(systemId);
            if (zaiKo != null)
            {
                bucketNoBox.Text = zaiKo.BUCKET_NO;
                ticketNoBox.Text = zaiKo.TICKET_NO;
                itemCodeBox.Text = zaiKo.ZAIKEY;
                colorCodeBox.Text = zaiKo.COLOR_CODE;
            }
        }

        private decimal GetUnitWeight()
        {
            if (!GlobalAccess.UseMockWeighter)
            {
                WeightData data = Weighter_Small.Instance.GetWeightData();
                if (data != null)
                {
                    return data.weight;
                }
                return -1;
            }
            else
            {
                return 0.4m;
            }
        }

        private decimal GetWeight()
        {
            int retryTimes = 1;
            int retryInterval = 10000;
            try
            {
                retryTimes = Int32.Parse(AppConfig.Get("RetryTimes"));
                retryInterval = Int32.Parse(AppConfig.Get("RetryInterval")) * 1000;
            }
            catch (Exception)
            {
            }
            if (!GlobalAccess.UseMockWeighter)
            {
                for (int i = 0; i < retryTimes; i++)
                {
                    for (int j = 0; j < 10; j++)
                    {
                        WeightData data = Weighter_Big.Instance.GetWeightData();
                        if (data != null)
                        {
                            if (data.isAnding)
                            {
                                return data.weight;
                            }
                            else
                            {
                                if (j == 9)
                                    return -2;
                                continue;
                            }
                        }
                        break;
                    }
                    Thread.Sleep(retryInterval);
                }
                return -1;
            }
            else
            {
                return 5.0m;
            }
        }

        private bool isRangeError = false;

        private void weightLoadBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (statusBox.Text == "ϵͳOffline")
                {
                    return;
                }

                if (isOperating == false)
                {
                    return;
                }

                if (statusBox.Text == "����")
                {
                    if (zaiKo == null)
                    {
                        msgBox.Text = "������TicketNo";
                        return;
                    }
                    if (zKey == null)
                    {
                        msgBox.Text = "û�д�Item������";
                        return;
                    }
                    if (bucket == null)
                    {
                        msgBox.Text = "Bucketδ��¼";
                        return;
                    }
                    if (string.IsNullOrEmpty(unitWeightBox.Text))
                    {
                        msgBox.Text = "���Ȼ�õ�λ����";
                        return;
                    }
                    if (decimal.Parse(unitWeightBox.Text) == 0)
                    {
                        msgBox.Text = "��λ�����������0";
                        return;
                    }
                }
                weightLoadBtn.Enabled = false;
                decimal weight = GetWeight();   //����,���󷵻�-1
                weightLoadBtn.Enabled = true;
                if (weight == -2)
                {
                    msgBox.Text = "�޷�ȡ�ð����ļ�������ֵ";
                    itemWeightBox.Text = string.Empty;
                    itemCountBox.Value = itemCountBox.Minimum;
                    return;
                }
                if (weight != -1)
                {
                    if (bucket != null)
                    {
                        decimal temp = weight - bucket.PACKING_WEIGHT - GlobalAccess.FixedWeight;
                        if (plasticBagStatusBox.Text == "��")
                        {
                            temp -= GlobalAccess.BagWeight;
                        }
                        itemWeightBox.Text = temp < 0 ? "0" : temp.ToString();
                    }
                    else
                    {
                        itemWeightBox.Text = weight < 0 ? "0" : weight.ToString();
                    }

                    if (statusBox.Text == "����")
                    {
                        int count = Convert.ToInt32(decimal.Truncate(decimal.Parse(itemWeightBox.Text) / decimal.Parse(unitWeightBox.Text)));
                        itemCountBox.Value = count;
                        //�������
                        if (zaiKo.PLAN_QTY > 0)
                        {
                            decimal error = decimal.Round((count * 100 / zaiKo.PLAN_QTY), 1, MidpointRounding.AwayFromZero);
                            itemWeightErrorBox.Text = error.ToString();
                            if (error > Convert.ToDecimal(itemWeightRangeLimitToBox.Text) || error < Convert.ToDecimal(itemWeightRangeLimitFromBox.Text))
                            {
                                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Range_Error);
                                OutOfRangeWarning frm = new OutOfRangeWarning("��Ʒ����", itemWeightErrorBox.Text, itemWeightRangeLimitFromBox.Text, itemWeightRangeLimitToBox.Text);
                                frm.ShowDialog(this);
                                msgBox.Text = "������Χ";
                                isRangeError = true;
                                //manCheck.Checked = true;
                            }
                            else
                            {
                                msgBox.Text = string.Empty;
                            }

                        }
                        else
                        {
                            itemWeightErrorBox.Text = "100";
                        }
                    }
                }
                else
                {
                    msgBox.Text = "ͨ�Ŵ���";
                    return;
                }
            }
            catch (Exception ex)
            {
                weightLoadBtn.Enabled = true;
                msgBox.Text = ex.Message;
            }
        }

        private void quitBtn_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void setBtn_Click(object sender, EventArgs e)
        {
            string schno = string.Empty;
            try
            {
                if (statusBox.Text == "ϵͳOffline")
                {
                    return;
                }

                if (isOperating == false)
                {
                    return;
                }

                if (statusBox.Text == "�����¼ģʽ" || statusBox.Text == "����")
                {
                    if (statusBox.Text == "����")
                    {
                        if (string.IsNullOrEmpty(unitWeightBox.Text))
                        {
                            msgBox.Text = "���Ȼ�õ�λ����";
                            unitWeightBox.Focus();
                            unitWeightBox.SelectAll();
                            return;
                        }

                        if (string.IsNullOrEmpty(itemWeightBox.Text))
                        {
                            msgBox.Text = "���ȶ�ȡItem����";
                            itemWeightBox.Focus();
                            itemWeightBox.SelectAll();
                            return;
                        }

                        if (itemCountBox.Value <= 0)
                        {
                            msgBox.Text = "Item�����������0";
                            weightLoadBtn.Focus();
                            return;
                        }
                    }
                    else if (statusBox.Text == "�����¼ģʽ")
                    {
                        if (string.IsNullOrEmpty(itemWeightBox.Text))
                        {
                            msgBox.Text = "���ȶ�ȡItem����";
                            return;
                        }

                        if (decimal.Parse(itemWeightBox.Text) == 0)
                        {
                            msgBox.Text = "Item��������Ϊ0";
                            weightLoadBtn.Focus();
                            return;
                        }
                    }

                    schno = DbAccess.generateScheduleNo();
                    FNGSETEntity fngset = new FNGSETEntity();
                    fngset.SCHNO = schno;
                    fngset.SYORIFLG = "0";
                    fngset.MOTOSTNO = GlobalAccess.StationNo;
                    fngset.USERID = GlobalAccess.UserId;
                    fngset.USERNAME = GlobalAccess.UserName;
                    if (statusBox.Text == "����")
                    {
                        fngset.UNIT_WEIGHT = Convert.ToDecimal(unitWeightBox.Text) / 1000;
                        fngset.MEASURE_WEIGHT = Convert.ToDecimal(itemWeightBox.Text);
                        fngset.NYUSYUSU = itemCountBox.Value;
                        //fngset.TICKET_NO = ticketNoBox.Text;
                        fngset.BUCKET_NO = bucketNoBox.Text;
                    }
                    else if (statusBox.Text == "�����¼ģʽ")
                    {
                        fngset.PACKING_WEIGHT = Convert.ToDecimal(itemWeightBox.Text);
                    }
                    fngset.Save();
                    DbAccess.callProcedure(schno, "stockin_3");
                    if (statusBox.Text == "����")
                    {
                        FNZAIKOEntity zaikoEntity = DbAccess.GetZaiKoByBucketNo(bucketNoBox.Text);
                        DbAccess.callAfterStockin(zaikoEntity.WEIGHT_REPORT_COMPLETE_FLAG);
                    }
                    else if (statusBox.Text == "�����¼ģʽ")
                    {
                        DbAccess.callAfterStockin(string.Empty);
                    }
                    msgBox.Text = "�趨�ɹ�";
                    ClearAll();
                    weightLoadBtn.Focus();
                    isOperating = false;
                    isRangeError = false;
                }
                else
                {
                    exportBtn.PerformClick();
                }
            }
            catch (Exception ex)
            {
                manCheck.Checked = true;
                msgBox.Text = ex.Message;
            }
            finally
            {
                DbAccess.UpdateFngset(schno);
            }
        }

        private void ClearAll()
        {
            bucketNoBox.Clear();
            bucketWeightBox.Clear();
            ticketNoBox.Clear();
            itemCodeBox.Clear();
            itemName1Box.Clear();
            itemName2Box.Clear();
            itemName3Box.Clear();
            colorCodeBox.Clear();
            planCountBox.Clear();
            planWeightBox.Clear();
            unitWeightBox.Clear();
            itemWeightErrorBox.Clear();
            itemWeightRangeLimitFromBox.Clear();
            itemWeightRangeLimitToBox.Clear();
            itemWeightBox.Clear();
            itemCountBox.Value = itemCountBox.Minimum;
            fixedWeightBox.Clear();
            plasticBagStatusBox.Clear();

            touCyaKu = null;
            zaiKo = null;
            zKey = null;
            bucket = null;

            itemCountBox.ReadOnly = true;
        }

        private void cancelBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (statusBox.Text == "ϵͳOffline")
                {
                    return;
                }

                if (isOperating == false)
                {
                    return;
                }

                ClearAll();
                isOperating = false;
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void itemCountBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            try
            {
                switch (e.KeyChar)
                {
                    case '\r':
                        if (zaiKo == null)
                        {
                            DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                            msgBox.Text = "û�п������";
                            return;
                        }
                        if (zaiKo.PLAN_QTY > 0)
                        {
                            decimal error = decimal.Round((itemCountBox.Value * 100 / zaiKo.PLAN_QTY), 1, MidpointRounding.AwayFromZero);
                            itemWeightErrorBox.Text = error.ToString();
                            if (error > Convert.ToDecimal(itemWeightRangeLimitToBox.Text) || error < Convert.ToDecimal(itemWeightRangeLimitFromBox.Text))
                            {
                                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Range_Error);
                                OutOfRangeWarning frm = new OutOfRangeWarning("��Ʒ����", itemWeightErrorBox.Text, itemWeightRangeLimitFromBox.Text, itemWeightRangeLimitToBox.Text);
                                frm.ShowDialog(this);
                                msgBox.Text = "������Χ";
                            }
                            else
                            {
                                msgBox.Text = string.Empty;
                            }
                        }
                        else
                        {
                            itemWeightErrorBox.Text = "100";
                        }
                        break;
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void exportBtn_Click(object sender, EventArgs e)
        {
            string schno = string.Empty;
            try
            {
                if (statusBox.Text == "ϵͳOffline")
                {
                    return;
                }

                if (isOperating == false)
                {
                    return;
                }

                schno = DbAccess.generateScheduleNo();
                FNGSETEntity fngset = new FNGSETEntity();
                fngset.SCHNO = schno;
                fngset.MOTOSTNO = GlobalAccess.StationNo;
                fngset.SYORIFLG = "0";
                fngset.Save();

                DbAccess.callProcedure(schno, "stockin_1_export");
                DbAccess.callAfterExport();

                DbAccess.UpdateFngset(schno);
                msgBox.Text = "�趨�ɹ�";

                ClearAll();
                weightLoadBtn.Focus();
                isOperating = false;
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
            finally
            {
                DbAccess.UpdateFngset(schno);
            }
        }

        private void Stockin3_Load(object sender, EventArgs e)
        {
            try
            {
                GlobalAccess.UseMockWeighter = AppConfig.Get("MockWeighter") == "1";
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void setStatusBox(string message)
        {
            statusBox.Text = message;
            if (message == "ϵͳOffline")
            {
                statusBox.BackColor = Color.Yellow;
            }
            else if (message == "�����¼ģʽ")
            {
                statusBox.BackColor = Color.LawnGreen;
            }
            else if (message == "�ų�" || message == "ͨ��" || message == "����")
            {
                statusBox.BackColor = Color.Aqua;
            }
            else if (message == "����δ��ȡ" || message == "Bucket�߶��쳣" || message == "����δ��¼" || message == "Bucket�ظ�")
            {
                statusBox.BackColor = Color.Red;
            }
            else
            {
                statusBox.BackColor = Color.FromKnownColor(KnownColor.Control);
            }
        }

        private void inputBt_Click(object sender, EventArgs e)
        {
            itemCountBox.ReadOnly = false;
            itemCountBox.Focus();
        }
    }
}