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
    public class FNAKITANAEntity : EntityObject
    {
        
        /// <summary>SOKOKBN</summary>
        public const string __SOKOKBN = "SOKOKBN";
        
        /// <summary>HARDZONE</summary>
        public const string __HARDZONE = "HARDZONE";
        
        /// <summary>SOFTZONE</summary>
        public const string __SOFTZONE = "SOFTZONE";
        
        /// <summary>BANKNO</summary>
        public const string __BANKNO = "BANKNO";
        
        /// <summary>BAYNO</summary>
        public const string __BAYNO = "BAYNO";
        
        /// <summary>LEVELNO</summary>
        public const string __LEVELNO = "LEVELNO";
        
        /// <summary>TANAFLG</summary>
        public const string __TANAFLG = "TANAFLG";
        
        /// <summary>ACCESSFLG</summary>
        public const string __ACCESSFLG = "ACCESSFLG";
        
        /// <summary>SYOZAIKEY</summary>
        public const string __SYOZAIKEY = "SYOZAIKEY";
        
        private string m_SOKOKBN;
        
        private string m_HARDZONE;
        
        private string m_SOFTZONE;
        
        private System.Decimal m_BANKNO;
        
        private System.Decimal m_BAYNO;
        
        private System.Decimal m_LEVELNO;
        
        private string m_TANAFLG;
        
        private string m_ACCESSFLG;
        
        private string m_SYOZAIKEY;
        
        /// <summary>构造函数</summary>
        public FNAKITANAEntity()
        {
        }
        
        /// <summary>属性SOKOKBN </summary>
        public string SOKOKBN
        {
            get
            {
                return this.m_SOKOKBN;
            }
            set
            {
                this.m_SOKOKBN = value;
            }
        }
        
        /// <summary>属性HARDZONE </summary>
        public string HARDZONE
        {
            get
            {
                return this.m_HARDZONE;
            }
            set
            {
                this.m_HARDZONE = value;
            }
        }
        
        /// <summary>属性SOFTZONE </summary>
        public string SOFTZONE
        {
            get
            {
                return this.m_SOFTZONE;
            }
            set
            {
                this.m_SOFTZONE = value;
            }
        }
        
        /// <summary>属性BANKNO </summary>
        public System.Decimal BANKNO
        {
            get
            {
                return this.m_BANKNO;
            }
            set
            {
                this.m_BANKNO = value;
            }
        }
        
        /// <summary>属性BAYNO </summary>
        public System.Decimal BAYNO
        {
            get
            {
                return this.m_BAYNO;
            }
            set
            {
                this.m_BAYNO = value;
            }
        }
        
        /// <summary>属性LEVELNO </summary>
        public System.Decimal LEVELNO
        {
            get
            {
                return this.m_LEVELNO;
            }
            set
            {
                this.m_LEVELNO = value;
            }
        }
        
        /// <summary>属性TANAFLG </summary>
        public string TANAFLG
        {
            get
            {
                return this.m_TANAFLG;
            }
            set
            {
                this.m_TANAFLG = value;
            }
        }
        
        /// <summary>属性ACCESSFLG </summary>
        public string ACCESSFLG
        {
            get
            {
                return this.m_ACCESSFLG;
            }
            set
            {
                this.m_ACCESSFLG = value;
            }
        }
        
        /// <summary>属性SYOZAIKEY </summary>
        public string SYOZAIKEY
        {
            get
            {
                return this.m_SYOZAIKEY;
            }
            set
            {
                this.m_SYOZAIKEY = value;
            }
        }
    }
    
    /// FNAKITANAEntity执行类
    public abstract class FNAKITANAEntityAction
    {
        
        private FNAKITANAEntityAction()
        {
        }
        
        public static void Save(FNAKITANAEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNAKITANAEntity RetrieveAFNAKITANAEntity()
        {
            FNAKITANAEntity obj=new FNAKITANAEntity();
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
        public static EntityContainer RetrieveFNAKITANAEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNAKITANAEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNAKITANAEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNAKITANAEntity));
            return rc.AsDataTable();
        }
    }
}
