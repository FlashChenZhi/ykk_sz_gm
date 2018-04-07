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

namespace YkkWms
{
    public partial class Stockin2 : Form
    {
        //��⵽�����ҵ,�л�������ģʽ,�ڲ���ģʽ��,timer��������,�����趨֮��,�л����ǲ���ģʽ,timer���¿�ʼ
        private string _preZaiKey;
        private string _preColorCode;
        private decimal _preUnitWeight;

        private FNZAIKOEntity _zaiKo;
        private FMZKEYEntity _zKey;
        private FMBUCKETEntity _bucket;

        public Stockin2()
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
                    button1.PerformClick();
                    break;
                case Keys.F6:
                    button2.PerformClick();
                    break;
                default:
                    break;
            }
        }

        private void DoTicketNo()
        {
            try
            {
                string ticketNo = ticketNoBox.Text;
                if (string.IsNullOrEmpty(ticketNo.Trim()))
                {
                    return;
                }

                _zaiKo = DbAccess.GetZaiKoByTicketNo(ticketNo);
                if (_zaiKo == null)
                {
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
                chkUsingPlasticBag.Checked = _zKey.BAG_FLAG.Trim() == "1";


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
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
                ticketNoBox.Focus();
                ticketNoBox.SelectAll();
            }
        }

        private void DoRange(FNZAIKOEntity zaiKo)
        {
            FNRANGEEntity range = DbAccess.GetRange(zaiKo.MADE_SECTION, zaiKo.MADE_LINE);
            if (range == null)
            {
                rangeLimitFromBox1.Text = "100";
                rangeLimitToBox1.Text = "100";
            }
            else
            {
                rangeLimitFromBox1.Text = range.UNIT_WEIGHT_LOWER.ToString();
                rangeLimitToBox1.Text = range.UNIT_WEIGHT_UPPER.ToString();
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

                FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
                if (station == null) return;

                if (station.NYUSYUMODE == Nyusyumode.Empty_Bucket)
                {
                    setStatusBox("�����¼ģʽ");
                }
                else if (station.NYUSYUMODE == Nyusyumode.Normal)    //���ģʽ
                {
                    if (string.IsNullOrEmpty(statusBox.Text.Trim()) || statusBox.Text == "�����¼ģʽ")
                    {
                        setStatusBox("����");
                    }
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }


        //private bool IsBucketExist(string bucketNo)
        //{
        //    RetrieveCriteria rc = new RetrieveCriteria(typeof(FNZAIKOEntity));
        //    Condition c = rc.GetNewCondition();
        //    c.AddEqualTo(FNZAIKOEntity.__BUCKET_NO, bucketNo);
        //    return (rc.AsEntity() != null);
        //}

        private void ShowInfo(string systemId)
        {
            FNZAIKOEntity zaiKo = DbAccess.GetZaiKoBySystemId(systemId);
            if (zaiKo != null)
            {
                bucketNoBox.Text = zaiKo.BUCKET_NO;
                ticketNoBox.Text = zaiKo.TICKET_NO;
                itemCodeBox.Text = zaiKo.ZAIKEY;
                colorCodeBox.Text = zaiKo.COLOR_CODE;
                lineBox.Text = zaiKo.MADE_LINE;
                sectionBox.Text = zaiKo.MADE_SECTION;
            }
        }

        private void Stockin1_Load(object sender, EventArgs e)
        {
            try
            {
                userIdBox1.Text = GlobalAccess.UserId;
                userIdBox2.Text = GlobalAccess.UserName;
                GlobalAccess.UseMockWeighter = AppConfig.Get("MockWeighter") == "1";
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
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
                WeightData data = Weighter_Small.Instance.GetWeightData();
                if (data != null)
                {
                    return data.weight;
                }
                return -1;
            }
            else
            {
                return 30.0m;
            }
        }

        private void unitWeightLoadBtn_Click(object sender, EventArgs e)
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
            try
            {
                int count;
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
                    decimal unitWeight = decimal.Round(weight / count, 7, MidpointRounding.AwayFromZero);
                    unitWeightBox.Text = unitWeight.ToString();
                    //�������
                    if (_zKey.MASTER_UNIT_WEIGHT > 0)
                    {
                        decimal error = decimal.Round((unitWeight * 100 / (_zKey.MASTER_UNIT_WEIGHT * 1000)), 1, MidpointRounding.AwayFromZero);
                        errorBox1.Text = error.ToString();
                        if (error > Convert.ToDecimal(rangeLimitToBox1.Text) || error < Convert.ToDecimal(rangeLimitFromBox1.Text))
                        {
                            OutOfRangeWarning frm = new OutOfRangeWarning("ԭ��λ����", errorBox1.Text, rangeLimitFromBox1.Text, rangeLimitToBox1.Text);
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
                        errorBox1.Text = "100";
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


        private void quitBtn_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private string effectiveTicketNo;

        private void ticketNoBox_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.Enter:
                    this.effectiveTicketNo = ticketNoBox.Text;
                    DoTicketNo();
                    break;
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

                if (!DoBucketNo())
                {
                    bucketNoBox.Focus();
                    bucketNoBox.SelectAll();
                    return;
                }

                if (statusBox.Text == "����")
                {

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
                        msgBox.Text = "��λ��������Ϊ��";
                        unitWeightLoadBtn.Focus();
                        return;
                    }

                    if (Encoding.Default.GetByteCount(this.memoBox.Text) > memoBox.MaxLength)
                    {
                        msgBox.Text = "��ע����(�涨������100������ַ���1�����ֵ���2������ַ�)";
                        memoBox.Focus();
                        memoBox.SelectAll();
                        return;
                    }

                    schno = DbAccess.generateScheduleNo();
                    FNGSETEntity fngset = new FNGSETEntity();
                    fngset.SCHNO = schno;
                    fngset.MOTOSTNO = GlobalAccess.StationNo;
                    fngset.UNIT_WEIGHT = Convert.ToDecimal(unitWeightBox.Text) / 1000;
                    fngset.MEMO = string.IsNullOrEmpty(memoBox.Text.Trim()) ? " " : memoBox.Text;
                    fngset.USERID = GlobalAccess.UserId;
                    fngset.USERNAME = GlobalAccess.UserName;
                    fngset.SYORIFLG = "0";
                    fngset.TICKET_NO = effectiveTicketNo;
                    fngset.BUCKET_NO = bucketNoBox.Text;
                    fngset.SAINYUKBN = chkReStockIn.Checked ? "1" : " ";
                    fngset.BAG_FLAG = chkUsingPlasticBag.Checked ? "1" : "0";
                    fngset.Save();

                    DbAccess.callProcedure(schno, "stockin_2");
                    DbAccess.callAfterStockin(string.Empty);
                    
                    msgBox.Text = "�趨�ɹ�";
                    bucketNoBox.Focus();
                    bucketNoBox.SelectAll();

                    FNZAIKOEntity zaikoEntity = DbAccess.GetZaiKoByTicketNo(effectiveTicketNo);
                    _preZaiKey = _zKey.ZAIKEY;
                    _preColorCode = zaikoEntity.COLOR_CODE;
                    _preUnitWeight = decimal.Parse(unitWeightBox.Text);
                    ClearAll();
                    bucketNoBox.Focus();
                    bucketNoBox.SelectAll();
                }
                else
                {
                    msgBox.Text = "������ģʽ�²��ܽ����趨";
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
            errorBox1.Clear();
            memoBox.Clear();
            //msgBox.Clear();
            chkReStockIn.Checked = false;
            chkUsingPlasticBag.Checked = false;


            setStatusBox(string.Empty);

            _zaiKo = null;
            _zKey = null;
            _bucket = null;
        }

        private void cancelBtn_Click(object sender, EventArgs e)
        {
            try
            {
                ClearAll();
                bucketNoBox.Focus();
                bucketNoBox.SelectAll();

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

                if (!DoBucketNo())
                {
                    bucketNoBox.Focus();
                    bucketNoBox.SelectAll();
                    return;
                }

                if (statusBox.Text != "����")
                {
                    msgBox.Text = "������ģʽ�²��ܽ���û���";
                    return;
                }

                if (string.IsNullOrEmpty(bucketNoBox.Text.Trim()))
                {
                    msgBox.Text = "������BucketNo";
                    bucketNoBox.Focus();
                    bucketNoBox.SelectAll();
                    return;
                }

                OtherStockin1 form = new OtherStockin1(2, bucketNoBox.Text);
                if (form.ShowDialog(this) == DialogResult.OK)
                {
                    msgBox.Text = "�趨�ɹ�";
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
            try
            {
                switch (e.KeyChar)
                {
                    case '\r':
                        unitWeightLoadBtn.PerformClick();
                        break;
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }
        
        private bool DoBucketNo()
        {
            if (string.IsNullOrEmpty(bucketNoBox.Text))
            {
                msgBox.Text = "BucketNo����Ϊ��";
                return false;
            }

            FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
            if (station == null) return false;

            if (station.NYUSYUMODE == Nyusyumode.Empty_Bucket)
            {
                setStatusBox("�����¼ģʽ");
                bucketWeightBox.Clear();
                return true;
            }
            else if (station.NYUSYUMODE == Nyusyumode.Normal)    //���ģʽ
            {
                _bucket = DbAccess.GetBucket(bucketNoBox.Text);
                if (_bucket == null)
                {
                    setStatusBox("����δ��¼");
                    bucketWeightBox.Clear();
                    return false;
                }

                if (DbAccess.IsBucketInAutoWarehouse(_bucket.BUCKET_NO)
                    || DbAccess.IsBucketInLocation(_bucket.BUCKET_NO))
                {
                    setStatusBox("Bucket�ظ�");
                    bucketWeightBox.Clear();
                    return false;
                }

                setStatusBox("����");
                bucketWeightBox.Text = _bucket.PACKING_WEIGHT.ToString();
                ticketNoBox.Focus();
                ticketNoBox.SelectAll();
                return true;
            }
            return false;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                if (statusBox.Text == "ϵͳOffline")
                {
                    return;
                }

                IDataParameter[] paras = new IDataParameter[3];

                OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");
                para.ParameterName = "@in_station_no";
                para.OleDbType = OleDbType.Char;
                para.Value = GlobalAccess.StationNo;
                para.Size = 4;
                para.Direction = ParameterDirection.Input;
                paras[0] = para;

                para = (OleDbParameter)Query.GetParameter("ykk");
                para.OleDbType = OleDbType.Integer;
                para.ParameterName = "@io_return_code";
                para.Value = 0;
                para.Size = 9;
                para.Direction = ParameterDirection.InputOutput;
                paras[1] = para;

                para = (OleDbParameter)Query.GetParameter("ykk");
                para.OleDbType = OleDbType.VarChar;
                para.ParameterName = "@io_return_message";
                para.Value = string.Empty;
                para.Size = 255;
                para.Direction = ParameterDirection.InputOutput;
                paras[2] = para;

                Query.RunProcedure("stockin_2_cut_away", paras, "ykk");

                if (Int32.Parse(paras[1].Value.ToString()) != 0)
                {
                    msgBox.Text = paras[2].Value.ToString();
                }
                else
                {
                    ClearAll();
                    msgBox.Text = "�趨�ɹ�";
                    bucketNoBox.Focus();
                    bucketNoBox.SelectAll();
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                if (statusBox.Text == "ϵͳOffline")
                {
                    return;
                }

                IDataParameter[] paras = new IDataParameter[3];

                OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");
                para.ParameterName = "@in_station_no";
                para.OleDbType = OleDbType.Char;
                para.Value = GlobalAccess.StationNo;
                para.Size = 4;
                para.Direction = ParameterDirection.Input;
                paras[0] = para;

                para = (OleDbParameter)Query.GetParameter("ykk");
                para.OleDbType = OleDbType.Integer;
                para.ParameterName = "@io_return_code";
                para.Value = 0;
                para.Size = 9;
                para.Direction = ParameterDirection.InputOutput;
                paras[1] = para;

                para = (OleDbParameter)Query.GetParameter("ykk");
                para.OleDbType = OleDbType.VarChar;
                para.ParameterName = "@io_return_message";
                para.Value = string.Empty;
                para.Size = 255;
                para.Direction = ParameterDirection.InputOutput;
                paras[2] = para;

                Query.RunProcedure("stockin_2_release", paras, "ykk");

                if (Int32.Parse(paras[1].Value.ToString()) != 0)
                {
                    msgBox.Text = paras[2].Value.ToString();
                }
                else
                {
                    ClearAll();
                    msgBox.Text = "�趨�ɹ�";
                    bucketNoBox.Focus();
                    bucketNoBox.SelectAll();
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void bucketNoBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            try
            {
                switch (e.KeyChar)
                {
                    case '\r':
                        DoBucketNo();
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
    }
}