using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using BusinessEntity;
using System.Data.OleDb;
using PersistenceLayer;

namespace YkkWms
{
    public partial class Stockout : Form
    {
        private bool isOperating;
        private decimal planCount;
        List<FNSIJIEntity> sijis;
        private FMZKEYEntity zKey;
        FNPICK_CTLEntity pick_ctl;
        bool isWeighted;
        private string pickingType;
        private int remainCount;
        private int remainPickingQty;
        private string mcKey;

        public Stockout()
        {
            InitializeComponent();
        }

        protected override void OnClosing(CancelEventArgs e)
        {
            base.OnClosing(e);
            timer1.Stop();
        }

        private void Stockout_KeyDown(object sender, KeyEventArgs e)
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
                    uiClearBtn.PerformClick();
                    break;
                case Keys.F8:
                    cancelBtn.PerformClick();
                    break;
                case Keys.F4:
                    sendMessageBtn.PerformClick();
                    break;
                case Keys.F5:
                    receiveMessageBtn.PerformClick();
                    break;
                default:
                    break;
            }
        }

        private void Stockout_Load(object sender, EventArgs e)
        {
            try
            {
                timer2.Start();
                timer2_Tick(null, null);

                userIdBox.Text = GlobalAccess.UserId;
                userNameBox.Text = GlobalAccess.UserName;
                GlobalAccess.UseMockWeighter = AppConfig.Get("MockWeighter") == "1";
                FNSTATIONEntity stationEntity = DbAccess.GetStation(GlobalAccess.StationNo);
                if (stationEntity == null)
                {
                    GlobalAccess.ShowDebugInfo("cannot find fnstation data");
                    return;
                }

                if (stationEntity.CHUDANFLG == null)
                {
                    GlobalAccess.ShowDebugInfo("fnstation -> chudanflg is null");
                    return;
                }

                if (stationEntity.CHUDANFLG == "0")
                {
                    setJobStatus("作业开始");
                }
                else
                {
                    setJobStatus("作业中断");
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            try
            {
                datetimeLb.Text = DateTime.Now.ToString("yyyy/MM/dd HH:mm");
                if (!isOperating)   //非操作状态
                {
                    CheckTask();
                }
            }
            catch (Exception ex)
            {
                WriteLog(ex.Message);
                msgBox.Text = ex.Message;
            }
        }

        private void quitBtn_Click(object sender, EventArgs e)
        {
            try
            {
                FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
                if (station == null)
                {
                    MessageBox.Show("无法取得站台信息", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                station.INTO_FLAG = "1";
                station.Save();

                FNJISEKIEntity jiseki = new FNJISEKIEntity();
                jiseki.SAGYOKBN = "G";
                jiseki.USERID = GlobalAccess.UserId;
                jiseki.USERNAME = GlobalAccess.UserName;
                jiseki.SAKUSEIHIJI = DateTime.Now.ToString("yyyyMMddHHmmss");
                jiseki.ENDSTNO = GlobalAccess.StationNo;
                jiseki.Save();

                this.Close();
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private FNZAIKOEntity zaikoEntity = null;
        private void CheckTask()
        {
            pick_ctl = DbAccess.GetPick_Ctl(GlobalAccess.TermNo);
            if (pick_ctl == null || string.IsNullOrEmpty(pick_ctl.MCKEY == null ? pick_ctl.MCKEY : pick_ctl.MCKEY.Trim()))   //无拣选预定
            {
                ClearAll();
                return;
            }

            FNHANSOEntity hanso = DbAccess.GetHanSo(pick_ctl.MCKEY.Trim());
            if (hanso == null)
            {
                msgBox.Text = "没有搬送数据";
                WriteLog("无法找到Hanso数据,Mckey为" + pick_ctl.MCKEY.Trim());
                return;
            }

            if (hanso.HJYOTAIFLG != "6") //Bucket未到达
            {
                return;
            }

            isOperating = true; //操作状态,不再刷数据库        

            cancelBtn.Enabled = hanso.SAGYOKBN != "7"; //直行搬送,取消按钮不可按下


            try
            {
                mcKey = GetTaskMckey();  //取得MCKEY,PickingType,剩余作业数,剩余拣选数
            }
            catch (Exception ex)
            {
                throw new Exception("GetTaskMckey出错:" + ex.Message);
            }

            if (string.IsNullOrEmpty(mcKey))
            {
                msgBox.Text = "无法获得作业内容";
                WriteLog("GetTaskMckey无法找到对应数据");
                return;
            }

            if (pickingType == PickingType.Return)  //如果是回库作业,弹出回库窗口
            {
                StockBack frm = new StockBack(mcKey);
                frm.ShowDialog(this);
                isOperating = false;    //回库结束,开启刷数据库
                return;
            }

            sijis = DbAccess.GetSiJis(mcKey);
            if (sijis == null || sijis.Count == 0)
            {
                msgBox.Text = "没有指示数据";
                WriteLog(string.Format("无法找到siji数据,mckey{0}", mcKey));
                return;
            }

            if (sijis[0].ZAIKEY == null)
            {
                msgBox.Text = "无法找到siji.zaikey";
                WriteLog("无法找到siji.zaikey");
                return;
            }

            zKey = DbAccess.GetManagedZKey(sijis[0].ZAIKEY);
            if (zKey == null)
            {
                msgBox.Text = "无法找到ZAIKEY数据";
                WriteLog(string.Format("无法找到ZAIKEY数据,zkey{0}", zKey));
                return;
            }

            originalBucketNoBox.Text = hanso.BUCKET_NO;
            FMBUCKETEntity originalBucket = DbAccess.GetBucket(hanso.BUCKET_NO);
            originalBucketWeightBox.Text = originalBucket == null ? string.Empty : originalBucket.PACKING_WEIGHT.ToString();
            ticketNoBox.Text = sijis[0].TICKET_NO;
            colorCodeBox.Text = sijis[0].COLOR_CODE;
            itemCodeBox.Text = zKey.ZAIKEY;
            itemName1Box.Text = zKey.ZKNAME1;
            itemName2Box.Text = zKey.ZKNAME2;
            itemName3Box.Text = zKey.ZKNAME3;

            zaikoEntity = DbAccess.GetZaiKoBySystemId(hanso.SYSTEMID);
            unitWeightBox.Text = zaikoEntity == null ? string.Empty : (zaikoEntity.REAL_UNIT_WEIGHT * 1000).ToString();
            remainJobBox.Text = remainCount.ToString();
            totalStockoutCountBox.Text = remainPickingQty.ToString();

            planCount = 0;
            foreach (FNSIJIEntity siji in sijis)    //作业数量
            {
                planCount += siji.NYUSYUSU;
            }

            //全拣选时不可输入BucketNo
            newBucketNoBox.ReadOnly = (pickingType == PickingType.Total);


            //收、发计量报告按钮在盘库时不可用
            sendMessageBtn.Enabled = (pickingType != PickingType.Cycle);
            receiveMessageBtn.Enabled = (pickingType != PickingType.Cycle);

            //BucketNo不一致时,出库取消按钮不可用
            cancelBtn.Enabled = pick_ctl.BUCKETREADING_FLG != "2";

            if (pickingType == PickingType.Total)
            {
                msgBox.Clear();
                emptyBucketPositionBox.Text = "无设置";
                jobTypeBox.Text = "全拣选";
                jobCountBox.Text = planCount.ToString();
                newBucketNoBox.Text = hanso.BUCKET_NO;
                //全拣选时自动读出重量，计量器自动送信          
                if (DoBucketNoOnTotal())
                {
                    sendMessageBtn.PerformClick();
                }
            }
            if (pickingType == PickingType.Reverse)
            {
                msgBox.Clear();
                emptyBucketPositionBox.Text = "设置在称重机前";
                jobTypeBox.Text = "反拣选";
                jobCountBox.Text = planCount.ToString();
                newBucketNoBox.Focus();
                newBucketNoBox.SelectAll();
            }
            if (pickingType == PickingType.Normal || pickingType == PickingType.Subdivided)
            {
                msgBox.Clear();
                emptyBucketPositionBox.Text = "设置于称重机上";
                jobTypeBox.Text = pickingType == PickingType.Normal ? "拣选" : "拣选(细分)";
                jobCountBox.Text = planCount.ToString();
                newBucketNoBox.Focus();
                newBucketNoBox.SelectAll();
            }
            if (pickingType == PickingType.Abnormal)
            {
                msgBox.Clear();
                emptyBucketPositionBox.Text = "无设置";
                if (pick_ctl.BUCKETREADING_FLG == "2")
                {
                    jobTypeBox.Text = "Bucket不一致";
                }
            }
            if (pickingType == PickingType.Cycle)
            {
                msgBox.Clear();
                jobCountBox.Text = "0";
                newBucketNoBox.Text = hanso.BUCKET_NO;
                FMBUCKETEntity bucketEntity = DbAccess.GetBucket(hanso.BUCKET_NO);
                newBucketWeightBox.Text = bucketEntity == null ? string.Empty : bucketEntity.PACKING_WEIGHT.ToString();
                emptyBucketPositionBox.Text = "无设置";
                jobTypeBox.Text = "盘库";
            }
        }

        private string GetTaskMckey()
        {
            IDataParameter[] paras = new IDataParameter[10];
            OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");
            para.ParameterName = "@in_motostno";
            para.OleDbType = OleDbType.Char;
            para.Value = GlobalAccess.StationNo;
            para.Size = 4;
            para.Direction = ParameterDirection.Input;
            paras[0] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@in_printer_no";
            para.Value = Convert.ToInt32(GlobalAccess.PrinterNo);
            para.Size = 4;
            para.Direction = ParameterDirection.Input;
            paras[1] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Char;
            para.ParameterName = "@in_user_id";
            para.Value = GlobalAccess.UserId;
            para.Size = 20;
            para.Direction = ParameterDirection.Input;
            paras[2] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Char;
            para.ParameterName = "@in_user_name";
            para.Value = GlobalAccess.UserName;
            para.Size = 20;
            para.Direction = ParameterDirection.Input;
            paras[3] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Char;
            para.ParameterName = "@io_picking_type";
            para.Value = string.Empty;
            para.Size = 1;
            para.Direction = ParameterDirection.InputOutput;
            paras[4] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Char;
            para.ParameterName = "@io_new_mckey";
            para.Value = string.Empty;
            para.Size = 8;
            para.Direction = ParameterDirection.InputOutput;
            paras[5] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_remaining_job_num";
            para.Value = 0;
            para.Size = 4;
            para.Direction = ParameterDirection.InputOutput;
            paras[6] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_remaining_picking_num";
            para.Value = 0;
            para.Size = 4;
            para.Direction = ParameterDirection.InputOutput;
            paras[7] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_return_code";
            para.Value = 0;
            para.Size = 4;
            para.Direction = ParameterDirection.InputOutput;
            paras[8] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_return_message";
            para.Value = string.Empty;
            para.Size = 32;
            para.Direction = ParameterDirection.InputOutput;
            paras[9] = para;
            Query.RunProcedure("pre_picking", paras, "ykk");

            if (Int32.Parse(paras[8].Value.ToString()) != 0)
            {
                return null;
            }

            pickingType = Convert.ToString(paras[4].Value);
            remainCount = Convert.ToInt32(paras[6].Value);
            remainPickingQty = Convert.ToInt32(paras[7].Value);
            return Convert.ToString(paras[5].Value);
        }

        private void receiveMessageBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (!GlobalAccess.UseMockWeighter)
                {
                    if (!isWeighted)
                    {
                        msgBox.Text = "请先执行计量器送信";
                        return;
                    }

                    receiveMessageBtn.Enabled = false;
                    WeightData data = Weighter_Big.Instance.GetWeightData();
                    receiveMessageBtn.Enabled = true;
                    if (data != null)
                    {
                        if (data.isAnding)
                        {
                            msgBox.Clear();
                            decimal count = data.count;
                            SetRange(count);
                            pickingCountBox.Text = count.ToString("F0");
                        }
                        else
                        {
                            msgBox.Text = "计量数值未安定,请重新读取";
                        }
                    }
                    else
                    {
                        msgBox.Text = "通信错误";
                    }


                }
                else
                {

                    pickingCountBox.Text = "30";
                    if (planCount == 0) planCount = 1;
                    SetRange(30);
                }
            }
            catch (Exception ex)
            {
                receiveMessageBtn.Enabled = true;
                msgBox.Text = ex.Message;
            }
        }

        private void SetRange(decimal count)
        {
            decimal error = decimal.Round(count * 100 / planCount, 2);
            errorBox.Text = error.ToString();
            if (sijis == null || sijis.Count == 0)
            {
                msgBox.Text = "siji信息错误";
                WriteLog("SetRange时,siji信息错误");
                return;
            }

            if (sijis[0].SECTION == null || sijis[0].LINE == null)
            {
                msgBox.Text = "siji信息错误";
                WriteLog("SetRange时,siji信息错误");
                rangeLimitFromBox.Text = "100";
                rangeLimitToBox.Text = "100";
                return;
            }

            FNRANGEEntity range = DbAccess.GetRange(GlobalAccess.TermNo);
            if (range == null)
            {
                rangeLimitFromBox.Text = "100";
                rangeLimitToBox.Text = "100";
            }
            else
            {
                rangeLimitFromBox.Text = range.UNIT_WEIGHT_LOWER.ToString();
                rangeLimitToBox.Text = range.UNIT_WEIGHT_UPPER.ToString();
            }

            if (error > Convert.ToDecimal(rangeLimitToBox.Text) || error < Convert.ToDecimal(rangeLimitFromBox.Text))
            {
                OutOfRangeWarning frm = new OutOfRangeWarning("产品重量", errorBox.Text, rangeLimitFromBox.Text, rangeLimitToBox.Text);
                frm.ShowDialog(this);
                msgBox.Text = "超出范围";
            }
            else
            {
                msgBox.Text = string.Empty;
            }
        }

        private void setBtn_Click(object sender, EventArgs e)
        {
            string schno = string.Empty;
            try
            {
                if (isOperating == false)
                {
                    msgBox.Text = "当前没有作业";
                    return;
                }

                FNPICK_CTLEntity pickCtlEntity = DbAccess.GetPick_Ctl(GlobalAccess.TermNo);
                if (pickCtlEntity == null)
                {
                    msgBox.Text = "当前没有作业";
                    return;
                }

                FNHANSOEntity hansoEntity = DbAccess.GetHanSo(pickCtlEntity.MCKEY);
                if (hansoEntity == null)
                {
                    msgBox.Text = "没有搬送数据";
                    return;
                }

                if (hansoEntity.HJYOTAIFLG != "6")
                {
                    msgBox.Text = "Bucket还没有到达";
                    return;
                }

                if (pickingType != PickingType.Abnormal)
                {
                    if (pickingType != PickingType.Total)
                    {
                        if (string.IsNullOrEmpty(this.newBucketNoBox.Text))
                        {
                            msgBox.Text = "请输入BucketNo";
                            newBucketNoBox.Focus();
                            newBucketNoBox.SelectAll();
                            return;
                        }
                        else if (DbAccess.GetBucket(this.newBucketNoBox.Text) == null)
                        {
                            msgBox.Text = "空箱未登录";
                            newBucketNoBox.Focus();
                            newBucketNoBox.SelectAll();
                            return;
                        }
                    }

                    if (pickingType != PickingType.Cycle)
                    {
                        if (string.IsNullOrEmpty(this.pickingCountBox.Text))
                        {
                            msgBox.Text = "Picking数不能为空";
                            receiveMessageBtn.Focus();
                            return;
                        }
                    }
                }

                schno = DbAccess.generateScheduleNo();
                FNGSETEntity fngset = new FNGSETEntity();
                fngset.SCHNO = schno;
                fngset.MOTOSTNO = GlobalAccess.StationNo;
                if (pickingType != PickingType.Abnormal)
                {
                    fngset.BUCKET_NO = newBucketNoBox.Text;
                    if (pickingType != PickingType.Cycle)
                    {
                        fngset.NYUSYUSU = Decimal.Parse(this.pickingCountBox.Text);
                    }
                }
                fngset.SYORIFLG = "0";
                fngset.USERID = GlobalAccess.UserId;
                fngset.USERNAME = GlobalAccess.UserName;
                fngset.PRINTER_NO = GlobalAccess.PrinterNo;
                fngset.Save();

                DbAccess.callProcedure(schno, "picking_start");
                DbAccess.callAfterStockin(string.Empty);
                msgBox.Text = "设定成功";

                if (pickingType != PickingType.Abnormal)
                {
                    LabelPublish frm = new LabelPublish(sijis);
                    frm.ShowDialog(this);
                }
                ClearAll();
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

        private void ClearAll()
        {
            originalBucketNoBox.Clear();
            originalBucketWeightBox.Clear();
            ticketNoBox.Clear();
            itemCodeBox.Clear();
            itemName1Box.Clear();
            itemName2Box.Clear();
            itemName3Box.Clear();
            colorCodeBox.Clear();
            emptyBucketPositionBox.Clear();
            jobTypeBox.Clear();
            remainJobBox.Clear();
            totalStockoutCountBox.Clear();
            jobCountBox.Clear();
            unitWeightBox.Clear();
            newBucketNoBox.Clear();
            newBucketWeightBox.Clear();
            pickingCountBox.Clear();
            errorBox.Clear();
            rangeLimitFromBox.Clear();
            rangeLimitToBox.Clear();

            isWeighted = false;
            pick_ctl = null;
            planCount = 0;
            sijis = null;
            zKey = null;

            pickingType = string.Empty;
            remainCount = 0;
            remainPickingQty = 0;
        }       

        private bool DoBucketNoOnTotal()
        {
            FMBUCKETEntity bucket = DbAccess.GetBucket(newBucketNoBox.Text);
            if (bucket == null)
            {
                msgBox.Text = "空箱未登录";
                newBucketNoBox.Focus();
                newBucketNoBox.SelectAll();
                return false;
            }
            else
            {
                msgBox.Text = string.Empty;
                newBucketWeightBox.Text = (bucket.PACKING_WEIGHT).ToString();
                return true;
            }
        }

        private bool DoBucketNo()
        {
            if (DbAccess.IsBucketInAutoWarehouse(newBucketNoBox.Text)
                || DbAccess.IsBucketInFlatWarehouse(newBucketNoBox.Text)
                || DbAccess.IsBucketInLocation(newBucketNoBox.Text)
                || DbAccess.IsBucketInTransportation(newBucketNoBox.Text))
            {
                msgBox.Text = "Bucket重复";
                newBucketNoBox.Focus();
                newBucketNoBox.SelectAll();
                return false;
            }

            FMBUCKETEntity bucket = DbAccess.GetBucket(newBucketNoBox.Text);
            if (bucket == null)
            {
                msgBox.Text = "空箱未登录";
                newBucketNoBox.Focus();
                newBucketNoBox.SelectAll();
                return false;
            }
            else
            {
                msgBox.Text = string.Empty;
                newBucketWeightBox.Text = (bucket.PACKING_WEIGHT).ToString();
                return true;
            }
        }

        private void bucketMaintenanceBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (!string.IsNullOrEmpty(newBucketNoBox.Text))
                {
                    msgBox.Text = string.Empty;
                    BucketMaintenance frm = new BucketMaintenance(newBucketNoBox.Text);
                    if (frm.ShowDialog(this) == DialogResult.OK)
                    {
                        FMBUCKETEntity bucket = DbAccess.GetBucket(newBucketNoBox.Text);
                        newBucketWeightBox.Text = bucket.PACKING_WEIGHT.ToString();
                    }
                }
                else
                {
                    msgBox.Text = "请输入BucketNo";
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void uiClearBtn_Click(object sender, EventArgs e)
        {
            ClearAll();
            isOperating = false;
        }

        private void cancelBtn_Click(object sender, EventArgs e)
        {
            string schno = string.Empty;
            try
            {
                if (isOperating == false)
                {
                    msgBox.Text = "当前无作业";
                    return;
                }
                schno = DbAccess.generateScheduleNo();
                FNGSETEntity fngset = new FNGSETEntity();
                fngset.SCHNO = schno;
                fngset.MOTOSTNO = GlobalAccess.StationNo;
                fngset.SYORIFLG = "0";
                fngset.USERID = GlobalAccess.UserId;
                fngset.USERNAME = GlobalAccess.UserName;
                fngset.Save();

                DbAccess.callProcedure(schno, "picking_cancel");
                DbAccess.callAfterStockin(string.Empty);
                msgBox.Text = "设定成功";
                uiClearBtn.PerformClick();
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

        private void jobBeginBtn_Click(object sender, EventArgs e)
        {
            try
            {
                DbAccess.SetChudan("0");
                setJobStatus("作业开始");
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void jobStopBtn_Click(object sender, EventArgs e)
        {
            try
            {
                DbAccess.SetChudan("1");
                setJobStatus("作业中断");
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void remainJobBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (pick_ctl != null)
                {
                    List<FNSIJIEntity> remainSijis = DbAccess.GetSiJis(pick_ctl.MCKEY);
                    if (sijis != null)
                    {
                        foreach (FNSIJIEntity siji in sijis)
                        {
                            foreach (FNSIJIEntity rSiji in remainSijis)
                            {
                                if (siji.LABEL_KEY == rSiji.LABEL_KEY)
                                {
                                    remainSijis.Remove(rSiji);
                                    break;
                                }
                            }
                        }
                    }
                    RemainTaskFrm frm = new RemainTaskFrm(remainSijis);
                    frm.ShowDialog(this);
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }

        }

        private void newBucketNoBox_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                switch (e.KeyCode)
                {
                    case Keys.Enter:
                        //if (jobTypeBox.Text.Trim() == "拣选" || jobTypeBox.Text.Trim() == "反拣选")
                        //{
                        //    if (originalBucketNoBox.Text == newBucketNoBox.Text)
                        //    {
                        //        msgBox.Text = "请读取正确的新箱号";
                        //        newBucketNoBox.Focus();
                        //        newBucketNoBox.SelectAll();
                        //    }
                        //}

                        if (DoBucketNo())
                        {
                            sendMessageBtn.PerformClick();

                            if (jobTypeBox.Text.Trim() == "拣选" || jobTypeBox.Text.Trim() == "拣选(细分)")
                            {
                                printLabelOnNormal(mcKey, newBucketNoBox.Text);
                            }
                        }
                        break;
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
                newBucketNoBox.Focus();
                newBucketNoBox.SelectAll();
            }
        }

        private void printLabelOnNormal(string mcKey, string bucketNo)
        {
            IDataParameter[] paras = new IDataParameter[7];

            OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");
            para.ParameterName = "@in_mckey";
            para.OleDbType = OleDbType.Char;
            para.Value = mcKey;
            para.Size = 8;
            para.Direction = ParameterDirection.Input;
            paras[0] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Char;
            para.ParameterName = "@in_bucket_no";
            para.Value = bucketNo;
            para.Size = 7;
            para.Direction = ParameterDirection.Input;
            paras[1] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@in_printer_no";
            para.Value = Convert.ToInt32(GlobalAccess.PrinterNo);
            para.Size = 4;
            para.Direction = ParameterDirection.Input;
            paras[2] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Char;
            para.ParameterName = "@in_user_id";
            para.Value = GlobalAccess.UserId;
            para.Size = 20;
            para.Direction = ParameterDirection.Input;
            paras[3] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Char;
            para.ParameterName = "@in_user_name";
            para.Value = GlobalAccess.UserName;
            para.Size = 20;
            para.Direction = ParameterDirection.Input;
            paras[4] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_return_code";
            para.Value = 0;
            para.Size = 4;
            para.Direction = ParameterDirection.InputOutput;
            paras[5] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_return_message";
            para.Value = string.Empty;
            para.Size = 32;
            para.Direction = ParameterDirection.InputOutput;
            paras[6] = para;

            Query.RunProcedure("print_label_on_stockout_normal", paras, "ykk");

            if (Int32.Parse(paras[5].Value.ToString()) != 0)
            {
                throw new Exception(paras[6].Value.ToString());
            }
        }

        private void unitWeightBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (string.IsNullOrEmpty(ticketNoBox.Text))
                {
                    msgBox.Text = "没有对应的作业数据";
                    return;
                }
                FNZAIKOEntity zaikoEntity = DbAccess.GetZaiKoByTicketNo(ticketNoBox.Text);
                if (zaikoEntity == null)
                {
                    msgBox.Text = "库存数据不存在，不能更改单位重量";
                    return;
                }
                else
                {
                    msgBox.Text = string.Empty;
                    UnitWeightChange uwc = new UnitWeightChange(zaikoEntity);
                    if (uwc.ShowDialog(this) == DialogResult.OK)
                    {
                        zaikoEntity = DbAccess.GetZaiKoByTicketNo(ticketNoBox.Text);
                        unitWeightBox.Text = (zaikoEntity.REAL_UNIT_WEIGHT * 1000).ToString();
                        sendMessageBtn.PerformClick();
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
                if (string.IsNullOrEmpty(unitWeightBox.Text))
                {
                    msgBox.Text = "单位重量为空";
                    return;
                }
                if (string.IsNullOrEmpty(newBucketWeightBox.Text))
                {
                    msgBox.Text = "Bucket重量为空";
                    return;
                }
                decimal unitWeight = decimal.Parse(unitWeightBox.Text);
                decimal bucketWeight = 0;
                if (pickingType == PickingType.Normal || pickingType == PickingType.Subdivided)
                {
                    bucketWeight = decimal.Parse(newBucketWeightBox.Text) * 1000;
                }
                else
                {
                    bucketWeight = decimal.Parse(originalBucketWeightBox.Text) * 1000;

                    if (zaikoEntity != null && zaikoEntity.BAG_FLAG == "1")
                    {
                        bucketWeight += GlobalAccess.BagWeight * 1000;
                    }
                }
                sendMessageBtn.Enabled = false;
                Weighter_Big.Instance.SendBucketWeight(bucketWeight);
                Weighter_Big.Instance.SendItemWeight(unitWeight);
                sendMessageBtn.Enabled = true;
                msgBox.Text = string.Empty;
                isWeighted = true;
            }
            catch (Exception ex)
            {
                sendMessageBtn.Enabled = true;
                msgBox.Text = ex.Message;
            }
        }

        private void WriteLog(string log)
        {
            LogWriter.WriteLog(log, "Stockout");
        }

        private void setJobStatus(string status)
        {
            jobStatus.Text = status;
            if (status == "作业开始")
            {
                jobStatus.BackColor = Color.Aqua;
            }
            else if (status == "作业中断")
            {
                jobStatus.BackColor = Color.Red;
            }
            else
            {
                jobStatus.BackColor = Color.FromKnownColor(KnownColor.Control);
            }
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            FNSTATIONEntity station = DbAccess.GetStation(GlobalAccess.StationNo);
            if (station == null) return;
            txtEnterStatus.Text = station.INTO_FLAG == "0" ? "许可" : "禁止";
            txtEnterStatus.BackColor = station.INTO_FLAG == "0" ? Color.Lime : Color.Red;
        }

        private void btnProhibitEntrance_Click(object sender, EventArgs e)
        {
            try
            {
                CartonProhibited frm = new CartonProhibited();
                frm.ShowDialog(this);
                timer2_Tick(null, null);
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }
    }
}