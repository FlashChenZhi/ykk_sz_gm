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
    public class FNKAKSETEntity : EntityObject
    {
        
        /// <summary>SCHNO</summary>
        public const string __SCHNO = "SCHNO";
        
        /// <summary>SOKOKBN</summary>
        public const string __SOKOKBN = "SOKOKBN";
        
        /// <summary>LOCNO_F</summary>
        public const string __LOCNO_F = "LOCNO_F";
        
        /// <summary>LOCNO_T</summary>
        public const string __LOCNO_T = "LOCNO_T";
        
        /// <summary>ZAIKEY_F</summary>
        public const string __ZAIKEY_F = "ZAIKEY_F";
        
        /// <summary>ZAIKEY_T</summary>
        public const string __ZAIKEY_T = "ZAIKEY_T";
        
        /// <summary>KEY_KBN</summary>
        public const string __KEY_KBN = "KEY_KBN";
        
        /// <summary>SYU_ST</summary>
        public const string __SYU_ST = "SYU_ST";
        
        /// <summary>SAGYOBA</summary>
        public const string __SAGYOBA = "SAGYOBA";
        
        /// <summary>AILENO</summary>
        public const string __AILENO = "AILENO";
        
        /// <summary>KAIFLG</summary>
        public const string __KAIFLG = "KAIFLG";
        
        /// <summary>SAKUSEIHIJI</summary>
        public const string __SAKUSEIHIJI = "SAKUSEIHIJI";
        
        private string m_SCHNO;
        
        private string m_SOKOKBN;
        
        private string m_LOCNO_F;
        
        private string m_LOCNO_T;
        
        private string m_ZAIKEY_F;
        
        private string m_ZAIKEY_T;
        
        private string m_KEY_KBN;
        
        private string m_SYU_ST;
        
        private string m_SAGYOBA;
        
        private System.Decimal m_AILENO;
        
        private string m_KAIFLG;
        
        private string m_SAKUSEIHIJI;
        
        /// <summary>构造函数</summary>
        public FNKAKSETEntity()
        {
        }
        
        /// <summary>属性SCHNO </summary>
        public string SCHNO
        {
            get
            {
                return this.m_SCHNO;
            }
            set
            {
                this.m_SCHNO = value;
            }
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
        
        /// <summary>属性LOCNO_F </summary>
        public string LOCNO_F
        {
            get
            {
                return this.m_LOCNO_F;
            }
            set
            {
                this.m_LOCNO_F = value;
            }
        }
        
        /// <summary>属性LOCNO_T </summary>
        public string LOCNO_T
        {
            get
            {
                return this.m_LOCNO_T;
            }
            set
            {
                this.m_LOCNO_T = value;
            }
        }
        
        /// <summary>属性ZAIKEY_F </summary>
        public string ZAIKEY_F
        {
            get
            {
                return this.m_ZAIKEY_F;
            }
            set
            {
                this.m_ZAIKEY_F = value;
            }
        }
        
        /// <summary>属性ZAIKEY_T </summary>
        public string ZAIKEY_T
        {
            get
            {
                return this.m_ZAIKEY_T;
            }
            set
            {
                this.m_ZAIKEY_T = value;
            }
        }
        
        /// <summary>属性KEY_KBN </summary>
        public string KEY_KBN
        {
            get
            {
                return this.m_KEY_KBN;
            }
            set
            {
                this.m_KEY_KBN = value;
            }
        }
        
        /// <summary>属性SYU_ST </summary>
        public string SYU_ST
        {
            get
            {
                return this.m_SYU_ST;
            }
            set
            {
                this.m_SYU_ST = value;
            }
        }
        
        /// <summary>属性SAGYOBA </summary>
        public string SAGYOBA
        {
            get
            {
                return this.m_SAGYOBA;
            }
            set
            {
                this.m_SAGYOBA = value;
            }
        }
        
        /// <summary>属性AILENO </summary>
        public System.Decimal AILENO
        {
            get
            {
                return this.m_AILENO;
            }
            set
            {
                this.m_AILENO = value;
            }
        }
        
        /// <summary>属性KAIFLG </summary>
        public string KAIFLG
        {
            get
            {
                return this.m_KAIFLG;
            }
            set
            {
                this.m_KAIFLG = value;
            }
        }
        
        /// <summary>属性SAKUSEIHIJI </summary>
        public string SAKUSEIHIJI
        {
            get
            {
                return this.m_SAKUSEIHIJI;
            }
            set
            {
                this.m_SAKUSEIHIJI = value;
            }
        }
    }
    
    /// FNKAKSETEntity执行类
    public abstract class FNKAKSETEntityAction
    {
        
        private FNKAKSETEntityAction()
        {
        }
        
        public static void Save(FNKAKSETEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNKAKSETEntity RetrieveAFNKAKSETEntity()
        {
            FNKAKSETEntity obj=new FNKAKSETEntity();
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
        public static EntityContainer RetrieveFNKAKSETEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNKAKSETEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNKAKSETEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNKAKSETEntity));
            return rc.AsDataTable();
        }
    }
}
