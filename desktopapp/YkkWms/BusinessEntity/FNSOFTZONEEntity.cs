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
    public class FNSOFTZONEEntity : EntityObject
    {
        
        /// <summary>SOKOKBN</summary>
        public const string __SOKOKBN = "SOKOKBN";
        
        /// <summary>SOFTZONE</summary>
        public const string __SOFTZONE = "SOFTZONE";
        
        /// <summary>SOFTZONENAME</summary>
        public const string __SOFTZONENAME = "SOFTZONENAME";
        
        /// <summary>AKIKENKBN</summary>
        public const string __AKIKENKBN = "AKIKENKBN";
        
        private string m_SOKOKBN;
        
        private string m_SOFTZONE;
        
        private string m_SOFTZONENAME;
        
        private string m_AKIKENKBN;
        
        /// <summary>构造函数</summary>
        public FNSOFTZONEEntity()
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
        
        /// <summary>属性SOFTZONENAME </summary>
        public string SOFTZONENAME
        {
            get
            {
                return this.m_SOFTZONENAME;
            }
            set
            {
                this.m_SOFTZONENAME = value;
            }
        }
        
        /// <summary>属性AKIKENKBN </summary>
        public string AKIKENKBN
        {
            get
            {
                return this.m_AKIKENKBN;
            }
            set
            {
                this.m_AKIKENKBN = value;
            }
        }
    }
    
    /// FNSOFTZONEEntity执行类
    public abstract class FNSOFTZONEEntityAction
    {
        
        private FNSOFTZONEEntityAction()
        {
        }
        
        public static void Save(FNSOFTZONEEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNSOFTZONEEntity RetrieveAFNSOFTZONEEntity()
        {
            FNSOFTZONEEntity obj=new FNSOFTZONEEntity();
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
        public static EntityContainer RetrieveFNSOFTZONEEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNSOFTZONEEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNSOFTZONEEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNSOFTZONEEntity));
            return rc.AsDataTable();
        }
    }
}
