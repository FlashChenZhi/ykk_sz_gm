using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using BusinessEntity;
using System.Threading;

namespace YkkWms
{
    public partial class StockBack : Form
    {
        private string _mckey;
        FMBUCKETEntity _bucket;
        FNHANSOEntity _hanso;
        FMZKEYEntity _zKey;
        public static bool IsOpen = false;
        public bool alreadySendUnitWeight = false;
        //private bool _isWeighted = false;

        public StockBack(string mckey)
        {
            this._mckey = mckey;
            InitializeComponent();
            IsOpen = true;
        }

        protected override void OnClosed(EventArgs e)
        {
            base.OnClosed(e);
            IsOpen = false;
        }

        private void StockBack_KeyDown(object sender, KeyEventArgs e)
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
                    pickOutBtn.PerformClick();
                    break;
                case Keys.F4:
                    sendMessageBtn.PerformClick();
                    break;
                case Keys.F8:
                    inputBt.PerformClick();
                    break;
                default:
                    break;
            }
        }

        void sendMessageProc(Object stateInfo)
        {
            sendMessageBtn.PerformClick();
        }

        private void StockBack_Load(object sender, EventArgs e)
        {
            try
            {
                userIdBox.Text = GlobalAccess.UserId;
                userNameBox.Text = GlobalAccess.UserName;
                GlobalAccess.UseMockWeighter = AppConfig.Get("MockWeighter") == "1";
                DoMckey();
//                ThreadPool.QueueUserWorkItem(new WaitCallback(sendMessageProc));  
                sendMessageBtn.PerformClick();
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void DoMckey()
        {
            _hanso = DbAccess.GetHanSo(_mckey);
            if (_hanso == null)
            {
                msgBox.Text = "û�а�������";
                return;
            }

            if (_hanso.SAGYOKBN == "5")
            {
                setStatusBox("�̿�");
            }

            _bucket = DbAccess.GetBucket(_hanso.BUCKET_NO);
            if (_bucket == null)
            {
                setStatusBox("����δ��¼");
                return;
            }

            List<FNSIJIEntity> sijis = DbAccess.GetSiJis(_mckey);
            if (sijis != null && sijis.Count > 0)
            {
                _zKey = DbAccess.GetManagedZKey(sijis[0].ZAIKEY);
                bucketNoBox.Text = _bucket.BUCKET_NO;
                bucketWeightBox.Text = _bucket.PACKING_WEIGHT.ToString();

                ticketNoBox.Text = sijis[0].TICKET_NO;
                //colorCodeBox.Text = sijis[0].COLOR_CODE;

                itemCodeBox.Text = _zKey.ZAIKEY;
                itemNameBox1.Text = _zKey.ZKNAME1;
                itemNameBox2.Text = _zKey.ZKNAME2;
                itemNameBox3.Text = _zKey.ZKNAME3;

                FNZAIKOEntity zaiKo = DbAccess.GetZaiKoByTicketNo(sijis[0].TICKET_NO);
                if (zaiKo == null)
                {
                    msgBox.Text = "û�п������";
                    return;
                }
                unitWeightBox.Text = (zaiKo.REAL_UNIT_WEIGHT * 1000).ToString();
                sectionBox.Text = zaiKo.MADE_SECTION;
                lineBox.Text = zaiKo.MADE_LINE;
                colorCodeBox.Text = zaiKo.COLOR_CODE;
                planCountBox.Text = zaiKo.ZAIKOSU.ToString();
                planWeightBox.Text = (zaiKo.ZAIKOSU * zaiKo.REAL_UNIT_WEIGHT).ToString();// zaiKo.PLAN_WEIGHT.ToString();
                plasticBagStatusBox.Text = zaiKo.BAG_FLAG == "1" ? "��" : "��";
                chkUsingPlasticBag.Checked = zaiKo.BAG_FLAG == "1";
                fixedWeightBox.Text = (GlobalAccess.FixedWeight * 1000).ToString();
                setStatusBox("����");

            }
            else
            {
                msgBox.Text = "������";
                return;
            }
        }

        private void DoBucketNo(string bucketNo)
        {
            FMBUCKETEntity bucket = DbAccess.GetBucket(bucketNo);
        }

        private void quitBtn_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void setBtn_Click(object sender, EventArgs e)
        {
            if (!matchMckey())
            {
                this.Close();
            }
            else
            {
                string schno = string.Empty;
                try
                {
                    FNPICK_CTLEntity pickCtlEntity = DbAccess.GetPick_Ctl(GlobalAccess.TermNo);
                    if (pickCtlEntity == null)
                    {
                        msgBox.Text = "��ǰû����ҵ";
                        return;
                    }

                    FNHANSOEntity hansoEntity = DbAccess.GetHanSo(pickCtlEntity.MCKEY);
                    if (hansoEntity == null)
                    {
                        msgBox.Text = "û�а�������";
                        return;
                    }

                    if (hansoEntity.HJYOTAIFLG != "6")
                    {
                        msgBox.Text = "Bucket��û�е���";
                        return;
                    }

                    if (!DoBucket())
                    {
                        return;
                    }

                    if (itemCountBox.Value == 0)
                    {
                        msgBox.Text = "���ȳ��ػ�����Item����";
                        itemCountBox.Focus();
                        itemCountBox.Select();
                        return;
                    }

                    schno = DbAccess.generateScheduleNo();
                    FNGSETEntity fngset = new FNGSETEntity();
                    fngset.SCHNO = schno;
                    fngset.MOTOSTNO = GlobalAccess.StationNo;
                    fngset.UNIT_WEIGHT = Convert.ToDecimal(unitWeightBox.Text) / 1000;
                    fngset.MEASURE_WEIGHT = (itemCountBox.Value * Convert.ToDecimal(unitWeightBox.Text)) / 1000 > 99.9999m ? 99.9999m : (itemCountBox.Value * Convert.ToDecimal(unitWeightBox.Text)) / 1000;
                    fngset.NYUSYUSU = itemCountBox.Value;
                    fngset.MEMO = string.IsNullOrEmpty(remarkBox.Text) ? " " : remarkBox.Text.Trim();
                    fngset.USERID = GlobalAccess.UserId;
                    fngset.USERNAME = GlobalAccess.UserName;
                    fngset.SYORIFLG = "0";
                    fngset.TICKET_NO = ticketNoBox.Text;
                    fngset.BUCKET_NO = bucketNoBox.Text;
                    fngset.BAG_FLAG = chkUsingPlasticBag.Checked ? "1" : "0";
                    fngset.Save();
                    DbAccess.callProcedure(schno, "stock_back");
                    DbAccess.callAfterStockin(string.Empty);
                    msgBox.Text = "�趨�ɹ�";
                    //ClearAll();
                    this.Close();
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
        }

        private bool matchMckey()
        {
            FNPICK_CTLEntity pick_ctl = DbAccess.GetPick_Ctl(GlobalAccess.TermNo);
            if (pick_ctl == null || pick_ctl.MCKEY.Trim() != _mckey.Trim())
            {
                return false;
            }

            return true;
        }

        private void ClearAll()
        {
            itemWeightBox.Clear();
            itemCountBox.Value = itemCountBox.Minimum;
            remarkBox.Clear();
            setStatusBox(string.Empty);

            itemCountBox.ReadOnly = true;
        }

        private void cancelBtn_Click(object sender, EventArgs e)
        {
            if (!matchMckey())
            {
                this.Close();
            }
            else
            {
                try
                {
                    ClearAll();

                    FNPICK_CTLEntity pickCtlEntity = DbAccess.GetPick_Ctl(GlobalAccess.TermNo);
                    if (pickCtlEntity == null)
                    {
                        return;
                    }

                    FNHANSOEntity hansoEntity = DbAccess.GetHanSo(pickCtlEntity.MCKEY);
                    if (hansoEntity == null)
                    {
                        return;
                    }

                    FMBUCKETEntity bucketEntity = DbAccess.GetBucket(hansoEntity.BUCKET_NO);
                    if (_bucket == null)
                    {
                        return;
                    }
                    bucketNoBox.Text = bucketEntity.BUCKET_NO;
                    bucketWeightBox.Text = bucketEntity.PACKING_WEIGHT.ToString();
                }
                catch (Exception ex)
                {
                    msgBox.Text = ex.Message;
                }
            }
        }

        private void pickOutBtn_Click(object sender, EventArgs e)
        {
            if (!matchMckey())
            {
                this.Close();
            }
            else
            {

                string schno = string.Empty;
                try
                {
                    FNPICK_CTLEntity pickCtlEntity = DbAccess.GetPick_Ctl(GlobalAccess.TermNo);
                    if (pickCtlEntity == null)
                    {
                        msgBox.Text = "��ǰû����ҵ";
                        return;
                    }

                    FNHANSOEntity hansoEntity = DbAccess.GetHanSo(pickCtlEntity.MCKEY);
                    if (hansoEntity == null)
                    {
                        msgBox.Text = "û�а�������";
                        return;
                    }

                    if (hansoEntity.HJYOTAIFLG != "6")
                    {
                        msgBox.Text = "Bucket��û�е���";
                        return;
                    }

                    schno = DbAccess.generateScheduleNo();
                    FNGSETEntity fngset = new FNGSETEntity();
                    fngset.SCHNO = schno;
                    fngset.SYORIFLG = "0";
                    fngset.MOTOSTNO = GlobalAccess.StationNo;
                    fngset.USERID = GlobalAccess.UserId;
                    fngset.USERNAME = GlobalAccess.UserName;
                    fngset.Save();

                    DbAccess.callProcedure(schno, "stock_back_export");
                    DbAccess.callAfterStockin(string.Empty);
                    msgBox.Text = "�趨�ɹ�";

                    //ClearAll();
                    this.Close();
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
        }

        private void bucketMaintenanceBtn_Click(object sender, EventArgs e)
        {
            if (!matchMckey())
            {
                this.Close();
            }
            else
            {
                try
                {
                    if (!string.IsNullOrEmpty(bucketNoBox.Text))
                    {
                        msgBox.Text = string.Empty;
                        BucketMaintenance frm = new BucketMaintenance(bucketNoBox.Text);
                        if (frm.ShowDialog(this) == DialogResult.OK)
                        {
                            FMBUCKETEntity bucket = DbAccess.GetBucket(bucketNoBox.Text);
                            bucketWeightBox.Text = bucket.PACKING_WEIGHT.ToString();
                        }
                    }
                    else
                    {
                        msgBox.Text = "������BucketNo";
                    }
                }
                catch (Exception ex)
                {
                    msgBox.Text = ex.Message;
                }
            }
        }

        private void weightLoadBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (GlobalAccess.UseMockWeighter)
                {
                    itemCountBox.Value = 100;
                    itemWeightBox.Text = Convert.ToString(30.0m);
                    return;
                }

                if (!alreadySendUnitWeight)
                {
                    msgBox.Text = "�����·���ԭ��λ����";
                    return;
                }

                weightLoadBtn.Enabled = false;
                sendMessageBtn.Enabled = false;
                WeightData data = Weighter_Big.Instance.GetWeightData();
                weightLoadBtn.Enabled = true;
                sendMessageBtn.Enabled = true;
                if (data != null)
                {
                    if (data.isAnding)
                    {
                        itemCountBox.Value = Convert.ToInt32(data.count);
                        itemWeightBox.Text = data.weight.ToString("F4");
                        msgBox.Clear();
                        itemCountBox.Focus();
                        itemCountBox.Select();
                    }
                    else
                    {
                        msgBox.Text = "������ֵδ����,�����¶�ȡ";
                        itemCountBox.Value = itemCountBox.Minimum;
                        itemWeightBox.Text = string.Empty;
                    }
                }
                else
                {
                    msgBox.Text = "ͨ�Ŵ���";
                }
            }
            catch (Exception ex)
            {
                weightLoadBtn.Enabled = true;
                sendMessageBtn.Enabled = true;
                msgBox.Text = ex.Message;
            }
        }

        private void oldUnitWeightBtn_Click(object sender, EventArgs e)
        {
            try
            {
                FNZAIKOEntity zaikoEntity = DbAccess.GetZaiKoByTicketNo(ticketNoBox.Text);
                if (zaikoEntity == null)
                {
                    msgBox.Text = "������ݲ����ڣ����ܸ��ĵ�λ����";
                    return;
                }
                else
                {
                    msgBox.Text = string.Empty;
                    UnitWeightChange uwc = new UnitWeightChange(zaikoEntity);
                    if (uwc.ShowDialog(this) == DialogResult.OK)
                    {
                        zaikoEntity = DbAccess.GetZaiKoByTicketNo(ticketNoBox.Text);
                        //FMBUCKETEntity bucket = DbAccess.GetBucket(newBucketNoBox.Text);
                        unitWeightBox.Text = (zaikoEntity.REAL_UNIT_WEIGHT * 1000).ToString();
                        planWeightBox.Text = (zaikoEntity.ZAIKOSU * zaikoEntity.REAL_UNIT_WEIGHT).ToString();
                    }
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void sendMessageBtn_Click(object sender, EventArgs e)
        {
            try
            {
                FNPICK_CTLEntity pickCtlEntity = DbAccess.GetPick_Ctl(GlobalAccess.TermNo);
                if (pickCtlEntity == null)
                {
                    msgBox.Text = "��ǰû����ҵ";
                    return;
                }

                FNHANSOEntity hansoEntity = DbAccess.GetHanSo(pickCtlEntity.MCKEY);
                if (hansoEntity == null)
                {
                    msgBox.Text = "û�а�������";
                    return;
                }

                if (string.IsNullOrEmpty(unitWeightBox.Text))
                {
                    msgBox.Text = "���Ȼ�õ�λ����";
                    return;
                }
                if (string.IsNullOrEmpty(bucketWeightBox.Text))
                {
                    msgBox.Text = "Bucket����Ϊ��";
                    return;
                }
                decimal unitWeight = decimal.Parse(unitWeightBox.Text);
                decimal bucketWeight = decimal.Parse(bucketWeightBox.Text) * 1000;
                decimal offsetWeight = GlobalAccess.FixedWeight * 1000;
                if (chkUsingPlasticBag.Checked)
                {
                    offsetWeight += GlobalAccess.BagWeight * 1000;
                }

                sendMessageBtn.Enabled = false;
                weightLoadBtn.Enabled = false;
                Weighter_Big.Instance.SendBucketWeight(Decimal.Round((bucketWeight + offsetWeight)/5m, 0, MidpointRounding.AwayFromZero) * 5m);
                Weighter_Big.Instance.SendItemWeight(unitWeight);
                sendMessageBtn.Enabled = true;
                weightLoadBtn.Enabled = true;

                msgBox.Text = string.Empty;
                alreadySendUnitWeight = true;                
            }
            catch (Exception ex)
            {
                sendMessageBtn.Enabled = true;
                weightLoadBtn.Enabled = true;
                msgBox.Text = ex.Message;
            }
        }

        private void bucketNoBox_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                switch (e.KeyCode)
                {
                    case Keys.Enter:
                        DoBucket();
                        break;
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private bool DoBucket()
        {
            FNHANSOEntity hansoEntity = DbAccess.GetHanSo(_mckey);
            if (hansoEntity == null)
            {
                msgBox.Text = "�ް�������";
                bucketWeightBox.Clear();
                return false;
            }

            if (hansoEntity.BUCKET_NO != bucketNoBox.Text)
            {
                if (DbAccess.IsBucketInAutoWarehouse(bucketNoBox.Text)
                    || DbAccess.IsBucketInFlatWarehouse(bucketNoBox.Text)
                    || DbAccess.IsBucketInLocation(bucketNoBox.Text)
                    || DbAccess.IsBucketInTransportation(bucketNoBox.Text))
                {
                    setStatusBox("Bucket�ظ�");
                    bucketNoBox.Focus();
                    bucketNoBox.SelectAll();
                    bucketWeightBox.Clear();
                    return false;
                }
            }

            FMBUCKETEntity bucket = DbAccess.GetBucket(bucketNoBox.Text);
            if (bucket == null)
            {
                setStatusBox("����δ��¼");
                bucketNoBox.Focus();
                bucketNoBox.SelectAll();
                bucketWeightBox.Clear();
                return false;
            }
            else
            {
                msgBox.Text = string.Empty;
                bucketWeightBox.Text = (bucket.PACKING_WEIGHT).ToString();
                return true;
            }
        }        

        private void setStatusBox(string message)
        {
            statusBox.Text = message;
            if (message == "ϵͳOffline")
            {
                statusBox.BackColor = Color.Yellow;
            }
            else if (message == "�����¼ģʽ" || message == "�̿�")
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

        private void timer1_Tick(object sender, EventArgs e)
        {
            datetimeLb.Text = DateTime.Now.ToString("yyyy/MM/dd HH:mm");
        }

        private void inputBt_Click(object sender, EventArgs e)
        {
            if (!matchMckey())
            { 
                this.Close(); 
            }
            else
            {
                itemCountBox.ReadOnly = false;
                itemCountBox.Focus();
            }
        }

        private void chkUsingPlasticBag_CheckedChanged(object sender, EventArgs e)
        {
            itemWeightBox.Clear();
            itemCountBox.Value = itemCountBox.Minimum;
            alreadySendUnitWeight = false;
        }
    }
}