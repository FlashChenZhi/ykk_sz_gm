using System;
using System.Windows.Forms;
using BusinessEntity;

namespace YkkWms
{
    public partial class Login2 : Form
    {
        public decimal checkCount = 0;
        private readonly decimal originalCheckCount;
        private bool needLogin;


        public Login2(decimal originalCheckCount, bool needLogin)
        {
            InitializeComponent();
            this.originalCheckCount = originalCheckCount;
            this.needLogin = needLogin;
        }

        private void Login2_Load(object sender, EventArgs e)
        {
            checkCountBox.Value = originalCheckCount;

            userIdBox.Enabled = needLogin;
            passwordBox.Enabled = needLogin;

            userIdBox.TabStop = needLogin;
            passwordBox.TabStop = needLogin;

            if (!needLogin)
            {
                checkCountBox.Focus();                
                checkCountBox.Select(0, checkCountBox.Value.ToString("0").Length);
            }
        }

        private void LoginFrm_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.F2:
                    loginBtn.PerformClick();
                    break;
                case Keys.F9:
                    cancelBtn.PerformClick();
                    break;
            }
        }

        private void cancelBtn_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
            Close();
        }

        private void loginBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (needLogin)
                {
                    if (string.IsNullOrEmpty(userIdBox.Text))
                    {
                        msgBox.Text = "请输入用户名";
                        userIdBox.Focus();
                        userIdBox.SelectAll();
                        return;
                    }

                    if (string.IsNullOrEmpty(passwordBox.Text))
                    {
                        msgBox.Text = "请密码";
                        passwordBox.Focus();
                        passwordBox.SelectAll();
                        return;
                    }

                    LOGINUSEREntity user = DbAccess.GetUser(userIdBox.Text, passwordBox.Text);
                    if (user == null)
                    {
                        msgBox.Text = "用户名或密码错误,请重新输入";
                        userIdBox.Focus();
                        userIdBox.SelectAll();
                        return;
                    }
                }

                checkCount = checkCountBox.Value;

                DialogResult = DialogResult.OK;
                Close();
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
                if (e.KeyChar == Convert.ToChar(Keys.Enter))
                {
                    loginBtn.PerformClick();
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void userIdBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            try
            {
                if (e.KeyChar == Convert.ToChar(Keys.Enter))
                {
                    passwordBox.SelectAll();
                    passwordBox.Focus();
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }

        private void passwordBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            try
            {
                if (e.KeyChar == Convert.ToChar(Keys.Enter))
                {
                    checkCountBox.Focus();
                    checkCountBox.Select(0, checkCountBox.Value.ToString("0").Length);
                }
            }
            catch (Exception ex)
            {
                msgBox.Text = ex.Message;
            }
        }
    }
}