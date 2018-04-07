using System;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using BusinessEntity;

namespace YkkWms
{

    public partial class Stockin1 : Form
    {
        //��⵽�����ҵ,�л�������ģʽ,�ڲ���ģʽ��,timer��������,�����趨֮��,�л����ǲ���ģʽ,timer���¿�ʼ
        private bool _isOperating;
        private string _preZaiKey;
        private string _preColorCode;
        private decimal _preUnitWeight;

        private FNTOUCYAKUEntity _touCyaKu;
        private FNZAIKOEntity _zaiKo;
        private FMZKEYEntity _zKey;
        private FMBUCKETEntity _bucket;

        public Stockin1()
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
                case Keys.F4:
                    unitWeightLoadBtn.PerformClick();
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

        private void DoTicketNo()
        {
            string ticketNo = ticketNoBox.Text;
            if (string.IsNullOrEmpty(ticketNo.Trim()))
            {
                return;
            }

            _zaiKo = DbAccess.GetZaiKoByTicketNo(ticketNo);
            if (_zaiKo == null)
            {
                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                msgBox.Text = "���δ��¼";
                itemCodeBox.Text = string.Empty;
                itemNameBox1.Text = string.Empty;
                itemNameBox2.Text = string.Empty;
                itemNameBox3.Text = string.Empty;
                colorCodeBox.Text = string.Empty;
                sectionBox.Text = string.Empty;
                lineBox.Text = string.Empty;
                planCountBox.Text = string.Empty;
                planWeightBox.Text = string.Empty;
                unitWeightBox.Text = string.Empty;
                measureFlagBox.Text = string.Empty;
                ticketNoBox.Focus();
                ticketNoBox.SelectAll();
                return;
            }

            if (_zaiKo.STORAGE_PLACE_FLAG == "0") //��ƽ��
            {
                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                msgBox.Text = "����Ѵ���";
                itemCodeBox.Text = string.Empty;
                itemNameBox1.Text = string.Empty;
                itemNameBox2.Text = string.Empty;
                itemNameBox3.Text = string.Empty;
                colorCodeBox.Text = string.Empty;
                sectionBox.Text = string.Empty;
                lineBox.Text = string.Empty;
                planCountBox.Text = string.Empty;
                planWeightBox.Text = string.Empty;
                unitWeightBox.Text = string.Empty;
                measureFlagBox.Text = string.Empty;
                ticketNoBox.Focus();
                ticketNoBox.SelectAll();
                return;
            }

            _zKey = DbAccess.GetManagedZKey(_zaiKo.ZAIKEY);
            if (_zKey == null)
            {
                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                msgBox.Text = "û�д�Item������";
                itemCodeBox.Text = string.Empty;
                itemNameBox1.Text = string.Empty;
                itemNameBox2.Text = string.Empty;
                itemNameBox3.Text = string.Empty;
                colorCodeBox.Text = string.Empty;
                sectionBox.Text = string.Empty;
                lineBox.Text = string.Empty;
                planCountBox.Text = string.Empty;
                planWeightBox.Text = string.Empty;
                unitWeightBox.Text = string.Empty;
                measureFlagBox.Text = string.Empty;
                ticketNoBox.Focus();
                ticketNoBox.SelectAll();
                return;
            }

            msgBox.Clear();

            itemCodeBox.Text = _zKey.ZAIKEY;
            itemNameBox1.Text = _zKey.ZKNAME1;
            itemNameBox2.Text = _zKey.ZKNAME2;
            itemNameBox3.Text = _zKey.ZKNAME3;
            colorCodeBox.Text = _zaiKo.COLOR_CODE;
            sectionBox.Text = _zaiKo.MADE_SECTION;
            lineBox.Text = _zaiKo.MADE_LINE;
            planCountBox.Text = _zaiKo.PLAN_QTY.ToString();
            planWeightBox.Text = _zaiKo.PLAN_WEIGHT.ToString();
            unitWeightBox.Text = (_zKey.MASTER_UNIT_WEIGHT * 1000).ToString();
            checkCountBox.Value = _zKey.MEASURE_QTY;
            measureFlagBox.Text = _zKey.MEASURE_FLAG.Trim() == "0" ? "��Ҫ" : "Ҫ";
            memoBox.Text = _zaiKo.MEMO;
            chkUsingPlasticBag.Checked = _zKey.BAG_FLAG.Trim() == "1";
            fixedWeightBox.Text = (GlobalAccess.FixedWeight * 1000).ToString();

            DoRange(_zaiKo); //����ԭ��λ,������������

            if (_preZaiKey != _zKey.ZAIKEY || _preColorCode != _zaiKo.COLOR_CODE)
            {
                if (_zKey.MEASURE_FLAG.Trim() != "0")
                {
                    unitWeightBox.Text = string.Empty;
                }
            }
            else
            {
                unitWeightBox.Text = _preUnitWeight.ToString();
            }

        }

        private void DoRange(FNZAIKOEntity zaiKo)
        {
            FNRANGEEntity range = DbAccess.GetRange(zaiKo.MADE_SECTION, zaiKo.MADE_LINE);
            if (range == null)
            {
                unitWeightRangeLimitFromBox.Text = "100";
                unitWeightRangeLimitToBox.Text = "100";
                itemWeightRangeLimitFromBox.Text = "100";
                itemWeightRangeLimitToBox.Text = "100";
            }
            else
            {
                unitWeightRangeLimitFromBox.Text = range.UNIT_WEIGHT_LOWER.ToString();
                unitWeightRangeLimitToBox.Text = range.UNIT_WEIGHT_UPPER.ToString();
                itemWeightRangeLimitFromBox.Text = range.STORAGE_LOWER.ToString();
                itemWeightRangeLimitToBox.Text = range.STORAGE_UPPER.ToString();
            }
        }

        private string currentStockInMode = string.Empty;
        private string currentMcKey = string.Empty;

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

                if (!_isOperating)
                {
                    checkStockInMode();

                    _touCyaKu = DbAccess.GetTouCyaKu(GlobalAccess.StationNo);
                    if (_touCyaKu != null)
                    {
                        if (DoTouCyaKu(_touCyaKu) && manCheck.Visible && !manCheck.Checked)
                        {                            
                                weightLoadBtn.PerformClick();
                                if(!isRangeError)
                                {                                    
                                   setBtn.PerformClick();                                   
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

        private void setUIOnOffline()
        {
            ticketNoBox.ReadOnly = true;
            checkCountModifyBtn.Enabled = false;
            itemCountBox.ReadOnly = true;
            memoBox.ReadOnly = true;
            unitWeightLoadBtn.Enabled = false;
            weightLoadBtn.Enabled = false;
            setBtn.Enabled = false;
            exportBtn.Enabled = false;
            otherItemManagerBtn.Enabled = false;
            chkUsingPlasticBag.Enabled = false;

            ticketNoBox.TabStop = false;
            checkCountBox.TabStop = false;
            itemCountBox.TabStop = false;
            memoBox.TabStop = false;
            unitWeightLoadBtn.TabStop = false;
            weightLoadBtn.TabStop = false;
            setBtn.TabStop = false;
            exportBtn.TabStop = false;
            otherItemManagerBtn.TabStop = false;
            chkUsingPlasticBag.TabStop = false;

            ClearAll();
            msgBox.Clear();
        }


        private void setUIOnPass()
        {
            ticketNoBox.ReadOnly = true;            
            checkCountModifyBtn.Enabled = false;
            itemCountBox.ReadOnly = true;
            memoBox.ReadOnly = true;
            unitWeightLoadBtn.Enabled = false;
            weightLoadBtn.Enabled = false;
            setBtn.Enabled = false;
            exportBtn.Enabled = false;
            otherItemManagerBtn.Enabled = false;
            chkUsingPlasticBag.Enabled = false;

            ticketNoBox.TabStop = false;
            checkCountBox.TabStop = false;
            itemCountBox.TabStop = false;
            memoBox.TabStop = false;
            unitWeightLoadBtn.TabStop = false;
            weightLoadBtn.TabStop = false;
            setBtn.TabStop = false;
            exportBtn.TabStop = false;
            otherItemManagerBtn.TabStop = false;
            chkUsingPlasticBag.TabStop = false;

            ClearAll();
            msgBox.Clear();
        }

        private void setUIOnOnline()
        {
            ticketNoBox.ReadOnly = false;            
            checkCountModifyBtn.Enabled = true;
            itemCountBox.ReadOnly = false;
            memoBox.ReadOnly = false;
            unitWeightLoadBtn.Enabled = true;
            weightLoadBtn.Enabled = true;
            setBtn.Enabled = true;
            exportBtn.Enabled = true;
            otherItemManagerBtn.Enabled = true;
            chkUsingPlasticBag.Enabled = true;


            ticketNoBox.TabStop = true;
            checkCountBox.TabStop = true;
            itemCountBox.TabStop = true;
            memoBox.TabStop = true;
            unitWeightLoadBtn.TabStop = true;
            weightLoadBtn.TabStop = true;
            setBtn.TabStop = true;
            exportBtn.TabStop = true;
            otherItemManagerBtn.TabStop = true;
            chkUsingPlasticBag.TabStop = true;

            ClearAll();
            msgBox.Clear();
            setStatusBox(string.Empty);
        }

        private bool DoTouCyaKu(FNTOUCYAKUEntity touCyaKu)
        {
            bucketNoBox.Text = touCyaKu.BUCKET_NO.Trim().ToUpper() == "BR" ? string.Empty : touCyaKu.BUCKET_NO;
            if (touCyaKu.BUCKET_NO.Trim().ToUpper() == "BR")
            {
                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                setStatusBox("����δ��ȡ");
                setUIOnExport();
                _isOperating = true;
                return false;
            }

            FNHANSOEntity hanSo = DbAccess.GetHanSo(touCyaKu.MCKEY);
            if (hanSo == null)
            {
                setStatusBox("�ų�");
                touCyaKu.SYORIFLG = "1";
                touCyaKu.Save();
                setUIOnExport();
                _isOperating = true;
                return false;
            }

            if (hanSo.SAGYOKBN == Sagyokbn.ReInput) //�����
            {
                setStatusBox("ͨ��");
                ShowInfo(hanSo.SYSTEMID);
                return false;
            }

            if (touCyaKu.HEIGHT_FLAG == "3")    //���θߵ��쳣
            {
                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Height_Error);
                setStatusBox("Bucket�߶��쳣");
                setUIOnExport();
                _isOperating = true;
                return false;
            }

            FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
            if (station == null) return false;

            if (station.NYUSYUMODE == Nyusyumode.Empty_Bucket)
            {
                setStatusBox("�����¼ģʽ");
                _isOperating = true;
                return true;
            }

            if (station.NYUSYUMODE == Nyusyumode.Normal)    //���ģʽ
            {
                _bucket = DbAccess.GetBucket(touCyaKu.BUCKET_NO);
                if (_bucket == null)
                {
                    DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                    setStatusBox("����δ��¼");
                    setUIOnExport();
                    _isOperating = true;
                    return false;
                }
                bucketWeightBox.Text = _bucket.PACKING_WEIGHT.ToString();

                if (_bucket.HEIGHT_FLAG != touCyaKu.HEIGHT_FLAG)
                {
                    DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Height_Error);
                    setStatusBox("Bucket�߶��쳣");
                    setUIOnExport();
                    _isOperating = true;
                    return false;
                }

                if (DbAccess.IsBucketInAutoWarehouse(_bucket.BUCKET_NO)
                    || DbAccess.IsBucketInLocation(_bucket.BUCKET_NO))
                {
                    DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                    setStatusBox("Bucket�ظ�");
                    setUIOnExport();
                    _isOperating = true;
                    return false;
                }                

                msgBox.Clear();
                setStatusBox("����");
                _isOperating = true;
            }
            return false;
        }

        private void setUIOnExport()
        {
            ticketNoBox.ReadOnly = true;            
            checkCountModifyBtn.Enabled = false;
            itemCountBox.ReadOnly = true;
            memoBox.ReadOnly = true;
            unitWeightLoadBtn.Enabled = false;
            weightLoadBtn.Enabled = false;
            setBtn.Enabled = false;
            exportBtn.Enabled = true;
            otherItemManagerBtn.Enabled = false;
            chkUsingPlasticBag.Enabled = false;

            ticketNoBox.TabStop = false;
            checkCountBox.TabStop = false;
            itemCountBox.TabStop = false;
            memoBox.TabStop = false;
            unitWeightLoadBtn.TabStop = false;
            weightLoadBtn.TabStop = false;
            setBtn.TabStop = false;
            exportBtn.TabStop = true;
            otherItemManagerBtn.TabStop = false;
            chkUsingPlasticBag.TabStop = false;

            exportBtn.Focus();
        }

        private void setUIOnNormalMode()
        {
            ticketNoBox.ReadOnly = false;            
            checkCountModifyBtn.Enabled = true;
            itemCountBox.ReadOnly = false;
            memoBox.ReadOnly = false;
            unitWeightLoadBtn.Enabled = true;
            weightLoadBtn.Enabled = true;
            setBtn.Enabled = true;
            exportBtn.Enabled = true;
            otherItemManagerBtn.Enabled = true;
            chkUsingPlasticBag.Enabled = true;

            ticketNoBox.TabStop = true;
            checkCountBox.TabStop = true;
            itemCountBox.TabStop = true;
            memoBox.TabStop = true;
            unitWeightLoadBtn.TabStop = true;
            weightLoadBtn.TabStop = true;
            setBtn.TabStop = true;
            exportBtn.TabStop = true;
            otherItemManagerBtn.TabStop = true;
            chkUsingPlasticBag.TabStop = true;

            chkReStockIn.Enabled = true;
            manCheck.Enabled = false;

            ticketNoBox.Focus();
        }

        private void setUIOnEmptyBucketMode()
        {
            ticketNoBox.ReadOnly = true;            
            checkCountModifyBtn.Enabled = false;
            itemCountBox.ReadOnly = true;
            memoBox.ReadOnly = true;
            unitWeightLoadBtn.Enabled = false;
            weightLoadBtn.Enabled = true;
            setBtn.Enabled = true;
            exportBtn.Enabled = true;
            otherItemManagerBtn.Enabled = false;
            chkUsingPlasticBag.Enabled = false;

            ticketNoBox.TabStop = false;
            checkCountBox.TabStop = false;
            itemCountBox.TabStop = false;
            memoBox.TabStop = false;
            unitWeightLoadBtn.TabStop = false;
            weightLoadBtn.TabStop = true;
            setBtn.TabStop = true;
            exportBtn.TabStop = true;
            otherItemManagerBtn.TabStop = false;
            chkUsingPlasticBag.TabStop = false;

            chkReStockIn.Enabled = false;
            manCheck.Enabled = true;

            weightLoadBtn.Focus();
        }

        private void ShowInfo(string systemId)
        {
            FNZAIKOEntity zaiKo = DbAccess.GetZaiKoBySystemId(systemId);
            if (zaiKo == null)
            {
                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Data_Error);
                msgBox.Text = "û�п������";
                return;
            }

            bucketNoBox.Text = zaiKo.BUCKET_NO;
            ticketNoBox.Text = zaiKo.TICKET_NO;
            itemCodeBox.Text = zaiKo.ZAIKEY;
            colorCodeBox.Text = zaiKo.COLOR_CODE;
            lineBox.Text = zaiKo.MADE_LINE;
            sectionBox.Text = zaiKo.MADE_SECTION;

        }

        private void Stockin1_Load(object sender, EventArgs e)
        {
            try
            {
                userIdBox1.Text = GlobalAccess.UserId;
                userIdBox2.Text = GlobalAccess.UserName;

                GlobalAccess.UseMockWeighter = AppConfig.Get("MockWeighter") == "1";

                manCheck.Visible = GlobalAccess.StationNo == "2101";
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void checkStockInMode()
        {
            FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
            if (station == null) return;
            if (station.NYUSYUMODE == Nyusyumode.Empty_Bucket)
            {
                if (statusBox.Text != "�����¼ģʽ")
                {
                    setStatusBox("�����¼ģʽ");
                    setUIOnEmptyBucketMode();
                    ClearAll();
                    msgBox.Text = string.Empty;
                }
            }
            else
            {
                if (statusBox.Text != "����")
                {
                    setStatusBox("����");
                    setUIOnNormalMode();
                    ClearAll();
                    msgBox.Text = string.Empty;
                }
            }
        }

        private decimal GetUnitWeight()
        {
            if (!GlobalAccess.UseMockWeighter)
            {
                for (int i = 0; i < 10; i++)
                {
                    WeightData data = Weighter_Small.Instance.GetWeightData();
                    if (data != null)
                    {
                        if (data.isAnding)
                        {
                            return data.weight;
                        }
                        else
                        {
                            continue;
                        }
                    }
                    return -1;
                }
                return -2;
            }
            else
            {
                return 4m;
            }
        }

        private decimal GetWeight()
        {
            if (!GlobalAccess.UseMockWeighter)
            {
                for (int i = 0; i < 10; i++)
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
                            continue;
                        }
                    }
                    return -1;
                }
                return -2;
                //WeightData data = Weighter_Big.Instance.GetWeightData();
                //if (data != null)
                //{
                //    return data.weight;
                //}
                //return -1;
            }
            else
            {
                return 5.0m;
            }
        }

        private void unitWeightLoadBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (statusBox.Text == "ϵͳOffline")
                {
                    return;
                }

                if (statusBox.Text == "ͨ��")
                {
                    return;
                }

                if (_isOperating == false)
                {
                    return;
                }

                if (_zaiKo == null)
                {
                    msgBox.Text = "������TicketNo";
                    return;
                }
                if (_zKey == null)
                {
                    msgBox.Text = "û�д�Item������";
                    return;
                }

                int count = 0; ;
                try
                {
                    count = Convert.ToInt32(checkCountBox.Value);
                }
                catch (Exception)
                {
                    msgBox.Text = "������������";
                    return;
                }
                unitWeightLoadBtn.Enabled = false;
                decimal weight = GetUnitWeight(); //����,ȡ��λ����,���󷵻�-1
                unitWeightLoadBtn.Enabled = true;
                if (weight == -2)
                {
                    msgBox.Text = "�޷�ȡ�ð����ļ�������ֵ";
                    unitWeightBox.Text = string.Empty;
                    return;
                }

                if (weight != -1)
                {
                    msgBox.Text = "";
                    decimal unitWeight = decimal.Round(weight / count, 7, MidpointRounding.AwayFromZero);
                    unitWeightBox.Text = unitWeight.ToString();
                    //�������
                    if (_zKey.MASTER_UNIT_WEIGHT > 0)
                    {
                        decimal error = decimal.Round((unitWeight * 100 / (_zKey.MASTER_UNIT_WEIGHT * 1000)), 1, MidpointRounding.AwayFromZero);
                        unitWeightErrorBox.Text = error.ToString();
                        if (error > Convert.ToDecimal(unitWeightRangeLimitToBox.Text) || error < Convert.ToDecimal(unitWeightRangeLimitFromBox.Text))
                        {
                            DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Range_Error);
                            OutOfRangeWarning frm = new OutOfRangeWarning("ԭ��λ����", unitWeightErrorBox.Text, unitWeightRangeLimitFromBox.Text, unitWeightRangeLimitToBox.Text);
                            frm.ShowDialog(this);
                            msgBox.Text = "������Χ";
                            this.setBtn.Enabled = false;
                        }
                        else
                        {
                            msgBox.Text = string.Empty;
                            this.setBtn.Enabled = true;
                        }
                    }
                    else
                    {
                        unitWeightErrorBox.Text = "100";
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
                unitWeightLoadBtn.Enabled = true;
                msgBox.Text = ex.Message;
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

                if (statusBox.Text == "ͨ��")
                {
                    return;
                }

                if (_isOperating == false)
                {
                    return;
                }

                if (statusBox.Text == "����")
                {
                    if (_zaiKo == null)
                    {
                        msgBox.Text = "������TicketNo";
                        return;
                    }
                    if (_zKey == null)
                    {
                        msgBox.Text = "û�д�Item������";
                        return;
                    }
                    if (_bucket == null)
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
                    if (_bucket != null)
                    {
                        decimal temp = weight - _bucket.PACKING_WEIGHT - GlobalAccess.FixedWeight;
                        if (chkUsingPlasticBag.Checked)
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
                        int count = Convert.ToInt32(decimal.Truncate(decimal.Parse(itemWeightBox.Text) * 1000 / decimal.Parse(unitWeightBox.Text)));
                        itemCountBox.Value = count;
                        itemCountBox.Focus();
                        //�������
                        if (_zaiKo.PLAN_QTY > 0)
                        {
                            decimal error = decimal.Round((count * 100 / _zaiKo.PLAN_QTY), 1, MidpointRounding.AwayFromZero);
                            itemWeightErrorBox.Text = error.ToString();
                            if (error > Convert.ToDecimal(itemWeightRangeLimitToBox.Text) || error < Convert.ToDecimal(itemWeightRangeLimitFromBox.Text))
                            {
                                DbAccess.callSwitchOnLight(GlobalAccess.StationNo, LightType.Range_Error);
                                OutOfRangeWarning frm = new OutOfRangeWarning("��Ʒ����", itemWeightErrorBox.Text, itemWeightRangeLimitFromBox.Text, itemWeightRangeLimitToBox.Text);
                                frm.ShowDialog(this);
                                msgBox.Text = "������Χ";
                                isRangeError = true;
                                itemCountBox.Select();
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

        private string effectiveTicketNo;

        private void ticketNoBox_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                switch (e.KeyCode)
                {
                    case Keys.Enter:
                        this.effectiveTicketNo = ticketNoBox.Text;
                        DoTicketNo();
                        break;
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }

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

                if (statusBox.Text == "ͨ��")
                {
                    return;
                }

                if (_isOperating == false)
                {
                    return;
                }

                if (_touCyaKu == null)
                {
                    msgBox.Text = "û�е��ﱨ��";
                    return;
                }

                if (statusBox.Text == "�����¼ģʽ" || statusBox.Text == "����")
                {
                    if (statusBox.Text == "����")
                    {
                        FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
                        if (station == null)
                        {
                            msgBox.Text = "�޷�ȡ��վ̨��Ϣ";
                            return;
                        }

                        if (station.NYUSYUMODE == Nyusyumode.Empty_Bucket)
                        {
                            setStatusBox("�����¼ģʽ");
                            setUIOnEmptyBucketMode();
                            return;
                        }

                        if (ticketNoBox.Text.Trim() == string.Empty)
                        {
                            msgBox.Text = "TicketNo����Ϊ��";
                            ticketNoBox.Focus();
                            ticketNoBox.SelectAll();
                            return;
                        }
                        if (effectiveTicketNo != ticketNoBox.Text)
                        {
                            msgBox.Text = "TicketNo��Ч";
                            ticketNoBox.Focus();
                            ticketNoBox.SelectAll();
                            return;
                        }
                        if (string.IsNullOrEmpty(unitWeightBox.Text))
                        {
                            msgBox.Text = "���Ȼ�õ�λ����";
                            unitWeightLoadBtn.Focus();
                            return;
                        }

                        if (decimal.Parse(unitWeightBox.Text) == 0)
                        {
                            msgBox.Text = "��λ�����������0";
                            unitWeightBox.Focus();
                            unitWeightBox.SelectAll();
                            return;
                        }
                        if (string.IsNullOrEmpty(itemWeightBox.Text))
                        {
                            msgBox.Text = "���ȶ�ȡItem����";
                            weightLoadBtn.Focus();
                            return;
                        }
                        if (itemCountBox.Value <= 0)
                        {
                            msgBox.Text = "Item�����������0";
                            itemCountBox.Focus();
                            itemCountBox.Select();
                            return;
                        }
                        if (Encoding.Default.GetByteCount(this.memoBox.Text) > memoBox.MaxLength)
                        {
                            msgBox.Text = "��ע����(�涨������100������ַ���1�����ֵ���2������ַ�)";
                            memoBox.Focus();
                            memoBox.SelectAll();
                            return;
                        }
                    }
                    else if (statusBox.Text == "�����¼ģʽ")
                    {
                        FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
                        if (station == null)
                        {
                            msgBox.Text = "�޷�ȡ��վ̨��Ϣ";
                            return;
                        }

                        if (station.NYUSYUMODE == Nyusyumode.Normal)
                        {
                            setStatusBox("�����¼ģʽ");
                            setUIOnNormalMode();
                            return;
                        }

                        if (string.IsNullOrEmpty(itemWeightBox.Text))
                        {
                            msgBox.Text = "���ȶ�ȡItem����";
                            weightLoadBtn.Focus();
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
                    fngset.MOTOSTNO = GlobalAccess.StationNo;
                    fngset.SYORIFLG = "0";
                    fngset.USERID = GlobalAccess.UserId;
                    fngset.USERNAME = GlobalAccess.UserName;
                    if (statusBox.Text == "����")
                    {
                        fngset.UNIT_WEIGHT = Convert.ToDecimal(unitWeightBox.Text) / 1000;
                        fngset.MEASURE_WEIGHT = Convert.ToDecimal(itemWeightBox.Text);
                        fngset.NYUSYUSU = itemCountBox.Value;
                        fngset.MEMO = string.IsNullOrEmpty(memoBox.Text.Trim()) ? " " : memoBox.Text;
                        fngset.TICKET_NO = effectiveTicketNo;
                        fngset.SAINYUKBN = chkReStockIn.Checked ? "1" : " ";
                        fngset.BAG_FLAG = chkUsingPlasticBag.Checked ? "1" : "0";
                    }
                    else if (statusBox.Text == "�����¼ģʽ")
                    {
                        fngset.PACKING_WEIGHT = Convert.ToDecimal(itemWeightBox.Text);
                    }
                    fngset.Save();
                    DbAccess.callProcedure(schno, "stockin_1");
                    if (statusBox.Text == "����")
                    {
                        FNZAIKOEntity zaikoEntity = DbAccess.GetZaiKoByTicketNo(effectiveTicketNo);
                        _preZaiKey = zaikoEntity.ZAIKEY;
                        _preColorCode = zaikoEntity.COLOR_CODE;
                        _preUnitWeight = decimal.Parse(unitWeightBox.Text);
                        DbAccess.callAfterStockin(zaikoEntity.WEIGHT_REPORT_COMPLETE_FLAG);
                    }
                    else if (statusBox.Text == "�����¼ģʽ")
                    {
                        DbAccess.callAfterStockin(string.Empty);
                    }
                    msgBox.Text = "�趨�ɹ�";
                    ClearAll();
                    ticketNoBox.Focus();
                    ticketNoBox.SelectAll();
                    _isOperating = false;
                    isRangeError = false;
                }
                else
                {
                    exportBtn.PerformClick();
                }
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

        private void ClearAll()
        {
            bucketNoBox.Clear();
            bucketWeightBox.Clear();
            ticketNoBox.Clear();
            itemCodeBox.Clear();
            itemNameBox1.Clear();
            itemNameBox2.Clear();
            itemNameBox3.Clear();
            colorCodeBox.Clear();
            lineBox.Clear();
            sectionBox.Clear();
            planCountBox.Clear();
            planWeightBox.Clear();
            unitWeightBox.Clear();
            measureFlagBox.Clear();
            checkCountBox.Value = checkCountBox.Minimum;
            unitWeightErrorBox.Clear();
            itemWeightErrorBox.Clear();
            unitWeightRangeLimitFromBox.Clear();
            itemWeightRangeLimitFromBox.Clear();
            unitWeightRangeLimitToBox.Clear();
            itemWeightRangeLimitToBox.Clear();
            itemWeightBox.Clear();
            itemCountBox.Value = itemCountBox.Minimum;
            memoBox.Clear();
            chkReStockIn.Checked = false;
//            manCheck.Checked = false;
            chkUsingPlasticBag.Checked = false;
            fixedWeightBox.Clear();

            _touCyaKu = null;
            _zaiKo = null;
            _zKey = null;
            _bucket = null;

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

                ClearAll();
                _isOperating = false;
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void otherItemManagerBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (statusBox.Text == "ϵͳOffline")
                {
                    return;
                }

                if (statusBox.Text == "ͨ��")
                {
                    return;
                }

                if (_isOperating == false)
                {
                    return;
                }

                if (_touCyaKu == null)
                {
                    msgBox.Text = "û�а�������";
                    return;
                }

                if (statusBox.Text != "����")
                {
                    msgBox.Text = "������ģʽ�²��ܽ���û���";
                    return;
                }

                OtherStockin1 form = new OtherStockin1(1, _touCyaKu.BUCKET_NO);
                if (form.ShowDialog(this) == DialogResult.OK)
                {
                    msgBox.Text = "�趨�ɹ�";
                    _isOperating = false;
                    ClearAll();
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void checkCountBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            switch (e.KeyChar)
            {
                case '\r':
                    unitWeightLoadBtn.PerformClick();
                    break;
            }
        }

        private void itemCountBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            try
            {
                switch (e.KeyChar)
                {
                    case '\r':
                        if (_zaiKo == null)
                        {
                            GlobalAccess.ShowDebugInfo("no fnzaiko data");
                            return;
                        }
                        if (_zaiKo.PLAN_QTY > 0)
                        {
                            decimal error = decimal.Round((itemCountBox.Value * 100 / _zaiKo.PLAN_QTY), 1, MidpointRounding.AwayFromZero);
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

        private void exportBtn_Click(object sender, EventArgs e)
        {
            string schno = string.Empty;
            try
            {
                if (statusBox.Text == "ϵͳOffline")
                {
                    return;
                }

                if (statusBox.Text == "ͨ��")
                {
                    return;
                }

                if (_isOperating == false)
                {
                    return;
                }

                if (_touCyaKu == null)
                {
                    msgBox.Text = "û�е��ﱨ��";
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
                msgBox.Text = "�趨�ɹ�";

                ClearAll();
                ticketNoBox.Focus();
                ticketNoBox.SelectAll();
                _isOperating = false;
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

        private void inputBt_Click(object sender, EventArgs e)
        {
            itemCountBox.ReadOnly = false;
            itemCountBox.Focus();
        }

        private void chkUsingPlasticBag_CheckedChanged(object sender, EventArgs e)
        {
            itemWeightBox.Clear();
            itemCountBox.Value = itemCountBox.Minimum;
        }

        private void CountModifyBtn_Click(object sender, EventArgs e)
        {
            try
            {
                bool needLogin = string.IsNullOrEmpty(planCountBox.Text) || Convert.ToInt32(planCountBox.Text) >= 20;
                Login2 login2 = new Login2(checkCountBox.Value, needLogin);
                
                DialogResult dialogResult = login2.ShowDialog(this);

                if (dialogResult == DialogResult.OK)
                {
                    checkCountBox.Value = login2.checkCount;
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }
    }
}