using System;
using System.Text;
using System.Windows.Forms;
using BusinessEntity;
using System.Net;

namespace YkkWms
{
    public partial class Login : Form
    {
        public Login()
        {
            InitializeComponent();
        }

        private void LoginFrm_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.F2:
                    loginBtn.PerformClick();
                    break;
                case Keys.F12:
                    quitBtn.PerformClick();
                    break;
                case Keys.F9:
                    cancelBtn.PerformClick();
                    break;                 
                default:
                    break;
            }
        }

        private void quitBtn_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void cancelBtn_Click(object sender, EventArgs e)
        {
            clearAll();
        }

        private void clearAll()
        {
            userIdBox.Text = string.Empty;
            passwordBox.Text = string.Empty;
            userIdBox.Focus();
            userIdBox.SelectAll();
        }
        private static IPAddress[] getIPAddress()
        {
            return Dns.GetHostEntry(Dns.GetHostName()).AddressList;
        }

        private void loginBtn_Click(object sender, EventArgs e)
        {           
            try
            {
                if (string.IsNullOrEmpty(userIdBox.Text))
                {
                    GlobalAccess.IsAuth = false;
                    msgBox.Text = "请输入用户名";
                    userIdBox.Focus();
                    userIdBox.SelectAll();
                    return;
                }
                if (string.IsNullOrEmpty(passwordBox.Text))
                {
                    passwordBox.Text = " ";
                }


                LOGINUSEREntity user = DbAccess.GetUser(userIdBox.Text, passwordBox.Text);
                if (user == null)
                {
                    GlobalAccess.IsAuth = false;
                    msgBox.Text = "用户名或密码错误,请重新输入";
                    userIdBox.Focus();
                    userIdBox.SelectAll();
                    return;
                }
                else
                {
                    GlobalAccess.UserId = user.USERID.ToString().Trim();
                    USERATTRIBUTEEntity userAttri = DbAccess.GetUserAttributeByUserId(GlobalAccess.UserId);
                    GlobalAccess.UserName = (userAttri == null || string.IsNullOrEmpty(userAttri.USERNAME)) ? string.Empty : userAttri.USERNAME.Trim();
                }

                TERMINALEntity term = null;
                IPAddress[] ipList = getIPAddress();

                for (int i = 0; i < ipList.Length; ++i)
                {
                    term = DbAccess.GetTermByIP(ipList[i].ToString());
                    if (term != null) break;
                }

                if (term == null)
                {
                    MessageBox.Show("无法找到对应的终端信息");
                    return;

                }
                else
                {
                    GlobalAccess.StationNo = term.STNO;
                    GlobalAccess.Proc = term.PROC;
                    GlobalAccess.TermNo = term.TERMINALNUMBER;
                    GlobalAccess.PrinterNo = term.PRINTER_NO;
                    GlobalAccess.FixedWeight = term.FIXED_WEIGHT;

                }

                FNSYSTEMEntity systemInfo = DbAccess.GetSystemInfo();
                GlobalAccess.BagWeight = systemInfo.BAG_WEIGHT;

#if DEBUG
                StringBuilder sb = new StringBuilder();
                sb.AppendLine("Station No:" + GlobalAccess.StationNo);
                sb.AppendLine("Proc Id:" + GlobalAccess.Proc);
                sb.AppendLine("Term No:" + GlobalAccess.TermNo);
                sb.AppendLine("IP Address:" + term.IPADDRESS.Trim());
                GlobalAccess.ShowDebugInfo(sb.ToString());                
#endif

                switch (GlobalAccess.Proc)
                {
                    case "1":
                        Stockin1 frm1 = new Stockin1();
                        clearAll();
                        frm1.ShowDialog(this);
                        break;
                    case "2":
                        Stockin2 frm2 = new Stockin2();
                        clearAll();
                        frm2.ShowDialog(this);
                        break;
                    case "3":
                        Stockin3 frm3 = new Stockin3();
                        clearAll();
                        frm3.ShowDialog(this);
                        break;
                    case "4":
                        Stockout frm4 = new Stockout();
                        clearAll();
                        frm4.ShowDialog(this);
                        break;
                    default:
                        MessageBox.Show("无效的Program区分");
                        break;
                }


            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void passwordBox_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.Enter:
                    loginBtn.PerformClick();
                    break;
            }
        }

        private void userIdBox_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.Enter:
                    loginBtn.PerformClick();
                    break;
            }
        }
    }
}