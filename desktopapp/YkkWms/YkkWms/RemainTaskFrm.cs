using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using BusinessEntity;

namespace YkkWms
{
    public partial class RemainTaskFrm : Form
    {
        List<FNSIJIEntity> sijis;
        public RemainTaskFrm(List<FNSIJIEntity> sijis)
        {
            this.sijis = sijis;
            if (sijis == null)
            {
                sijis = new List<FNSIJIEntity>();
            }
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void RemainTaskFrm_Load(object sender, EventArgs e)
        {
            for (int i = 0; i < sijis.Count; i++)
            {
                ListViewItem item = new ListViewItem(new string[] { sijis[i].SECTION,sijis[i].LINE,sijis[i].NYUSYUSU.ToString("F") });
                listView1.Items.Add(item);
            }
        }

    }
}
