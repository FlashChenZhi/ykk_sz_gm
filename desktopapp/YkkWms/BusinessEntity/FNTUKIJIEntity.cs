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
    public class FNTUKIJIEntity : EntityObject
    {
        
        /// <summary>NENGETSU</summary>
        public const string __NENGETSU = "NENGETSU";
        
        /// <summary>ZAIKEY</summary>
        public const string __ZAIKEY = "ZAIKEY";
        
        /// <summary>NYUKOSU</summary>
        public const string __NYUKOSU = "NYUKOSU";
        
        /// <summary>SYUKOSU</summary>
        public const string __SYUKOSU = "SYUKOSU";
        
        /// <summary>MENTEPLS</summary>
        public const string __MENTEPLS = "MENTEPLS";
        
        /// <summary>MENTEMIN</summary>
        public const string __MENTEMIN = "MENTEMIN";
        
        /// <summary>ZAIKAKUSU</summary>
        public const string __ZAIKAKUSU = "ZAIKAKUSU";
        
        /// <summary>ZKNAME</summary>
        public const string __ZKNAME = "ZKNAME";
        
        private string m_NENGETSU;
        
        private string m_ZAIKEY;
        
        private System.Decimal m_NYUKOSU;
        
        private System.Decimal m_SYUKOSU;
        
        private System.Decimal m_MENTEPLS;
        
        private System.Decimal m_MENTEMIN;
        
        private System.Decimal m_ZAIKAKUSU;
        
        private string m_ZKNAME;
        
        /// <summary>构造函数</summary>
        public FNTUKIJIEntity()
        {
        }
        
        /// <summary>属性NENGETSU </summary>
        public string NENGETSU
        {
            get
            {
                return this.m_NENGETSU;
            }
            set
            {
                this.m_NENGETSU = value;
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
        
        /// <summary>属性NYUKOSU </summary>
        public System.Decimal NYUKOSU
        {
            get
            {
                return this.m_NYUKOSU;
            }
            set
            {
                this.m_NYUKOSU = value;
            }
        }
        
        /// <summary>属性SYUKOSU </summary>
        public System.Decimal SYUKOSU
        {
            get
            {
                return this.m_SYUKOSU;
            }
            set
            {
                this.m_SYUKOSU = value;
            }
        }
        
        /// <summary>属性MENTEPLS </summary>
        public System.Decimal MENTEPLS
        {
            get
            {
                return this.m_MENTEPLS;
            }
            set
            {
                this.m_MENTEPLS = value;
            }
        }
        
        /// <summary>属性MENTEMIN </summary>
        public System.Decimal MENTEMIN
        {
            get
            {
                return this.m_MENTEMIN;
            }
            set
            {
                this.m_MENTEMIN = value;
            }
        }
        
        /// <summary>属性ZAIKAKUSU </summary>
        public System.Decimal ZAIKAKUSU
        {
            get
            {
                return this.m_ZAIKAKUSU;
            }
            set
            {
                this.m_ZAIKAKUSU = value;
            }
        }
        
        /// <summary>属性ZKNAME </summary>
        public string ZKNAME
        {
            get
            {
                return this.m_ZKNAME;
            }
            set
            {
                this.m_ZKNAME = value;
            }
        }
    }
    
    /// FNTUKIJIEntity执行类
    public abstract class FNTUKIJIEntityAction
    {
        
        private FNTUKIJIEntityAction()
        {
        }
        
        public static void Save(FNTUKIJIEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNTUKIJIEntity RetrieveAFNTUKIJIEntity()
        {
            FNTUKIJIEntity obj=new FNTUKIJIEntity();
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
        public static EntityContainer RetrieveFNTUKIJIEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNTUKIJIEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNTUKIJIEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNTUKIJIEntity));
            return rc.AsDataTable();
        }
    }
}
