//------------------------------------------------------------------------------
// <autogenerated>
//     This code was generated by a tool.
//     Runtime Version: 1.1.4322.2407
//
//     Changes to this file may cause incorrect behavior and will be lost if 
//     the code is regenerated.
// </autogenerated>
//------------------------------------------------------------------------------

// -------------------------------------------------------------
// 
//             Powered By： SR3.1(SmartRobot For SmartPersistenceLayer 3.1) 听棠
//             Created By： Administrator
//             Created Time： 2008-1-7 11:31:43
// 
// -------------------------------------------------------------
namespace BusinessEntity
{
    using System;
    using System.Collections;
    using System.Data;
    using PersistenceLayer;
    
    
    /// <summary>该类的摘要说明</summary>
    [Serializable()]
    public class FNLINEEntity : EntityObject
    {
        
        /// <summary>LINENO</summary>
        public const string __LINENO = "LINENO";
        
        /// <summary>ZAIKEY</summary>
        public const string __ZAIKEY = "ZAIKEY";
        
        /// <summary>LOTNO</summary>
        public const string __LOTNO = "LOTNO";
        
        /// <summary>NYUSYUSU</summary>
        public const string __NYUSYUSU = "NYUSYUSU";
        
        /// <summary>JISEKISU</summary>
        public const string __JISEKISU = "JISEKISU";
        
        /// <summary>SEKISAISU</summary>
        public const string __SEKISAISU = "SEKISAISU";
        
        /// <summary>STNO</summary>
        public const string __STNO = "STNO";
        
        /// <summary>LINESCHNO</summary>
        public const string __LINESCHNO = "LINESCHNO";
        
        /// <summary>CHUKEY</summary>
        public const string __CHUKEY = "CHUKEY";
        
        /// <summary>SYORIFLG</summary>
        public const string __SYORIFLG = "SYORIFLG";
        
        private string m_LINENO;
        
        private string m_ZAIKEY;
        
        private string m_LOTNO;
        
        private System.Decimal m_NYUSYUSU;
        
        private System.Decimal m_JISEKISU;
        
        private System.Decimal m_SEKISAISU;
        
        private string m_STNO;
        
        private string m_LINESCHNO;
        
        private string m_CHUKEY;
        
        private string m_SYORIFLG;
        
        /// <summary>构造函数</summary>
        public FNLINEEntity()
        {
        }
        
        /// <summary>属性LINENO </summary>
        public string LINENO
        {
            get
            {
                return this.m_LINENO;
            }
            set
            {
                this.m_LINENO = value;
            }
        }
        
        /// <summary>属性ZAIKEY </summary>
        public string ZAIKEY
        {
            get
            {
                return this.m_ZAIKEY;
            }
            set
            {
                this.m_ZAIKEY = value;
            }
        }
        
        /// <summary>属性LOTNO </summary>
        public string LOTNO
        {
            get
            {
                return this.m_LOTNO;
            }
            set
            {
                this.m_LOTNO = value;
            }
        }
        
        /// <summary>属性NYUSYUSU </summary>
        public System.Decimal NYUSYUSU
        {
            get
            {
                return this.m_NYUSYUSU;
            }
            set
            {
                this.m_NYUSYUSU = value;
            }
        }
        
        /// <summary>属性JISEKISU </summary>
        public System.Decimal JISEKISU
        {
            get
            {
                return this.m_JISEKISU;
            }
            set
            {
                this.m_JISEKISU = value;
            }
        }
        
        /// <summary>属性SEKISAISU </summary>
        public System.Decimal SEKISAISU
        {
            get
            {
                return this.m_SEKISAISU;
            }
            set
            {
                this.m_SEKISAISU = value;
            }
        }
        
        /// <summary>属性STNO </summary>
        public string STNO
        {
            get
            {
                return this.m_STNO;
            }
            set
            {
                this.m_STNO = value;
            }
        }
        
        /// <summary>属性LINESCHNO </summary>
        public string LINESCHNO
        {
            get
            {
                return this.m_LINESCHNO;
            }
            set
            {
                this.m_LINESCHNO = value;
            }
        }
        
        /// <summary>属性CHUKEY </summary>
        public string CHUKEY
        {
            get
            {
                return this.m_CHUKEY;
            }
            set
            {
                this.m_CHUKEY = value;
            }
        }
        
        /// <summary>属性SYORIFLG </summary>
        public string SYORIFLG
        {
            get
            {
                return this.m_SYORIFLG;
            }
            set
            {
                this.m_SYORIFLG = value;
            }
        }
    }
    
    /// FNLINEEntity执行类
    public abstract class FNLINEEntityAction
    {
        
        private FNLINEEntityAction()
        {
        }
        
        public static void Save(FNLINEEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNLINEEntity RetrieveAFNLINEEntity()
        {
            FNLINEEntity obj=new FNLINEEntity();
            obj.Retrieve();
            if (obj.IsPersistent)
            {
                return obj;
            }
            else
            {
                return null;
            }
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static EntityContainer RetrieveFNLINEEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNLINEEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNLINEEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNLINEEntity));
            return rc.AsDataTable();
        }
    }
}
