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
    public class ROLEEntity : EntityObject
    {
        
        /// <summary>ROLEID</summary>
        public const string __ROLEID = "ROLEID";
        
        /// <summary>ROLENAME</summary>
        public const string __ROLENAME = "ROLENAME";
        
        /// <summary>FAILEDLOGINATTEMPTS</summary>
        public const string __FAILEDLOGINATTEMPTS = "FAILEDLOGINATTEMPTS";
        
        /// <summary>UPDATEDATE</summary>
        public const string __UPDATEDATE = "UPDATEDATE";
        
        private string m_ROLEID;
        
        private string m_ROLENAME;
        
        private System.Decimal m_FAILEDLOGINATTEMPTS;
        
        private System.DateTime m_UPDATEDATE = DateTime.MinValue;
        
        /// <summary>构造函数</summary>
        public ROLEEntity()
        {
        }
        
        /// <summary>属性ROLEID </summary>
        public string ROLEID
        {
            get
            {
                return this.m_ROLEID;
            }
            set
            {
                this.m_ROLEID = value;
            }
        }
        
        /// <summary>属性ROLENAME </summary>
        public string ROLENAME
        {
            get
            {
                return this.m_ROLENAME;
            }
            set
            {
                this.m_ROLENAME = value;
            }
        }
        
        /// <summary>属性FAILEDLOGINATTEMPTS </summary>
        public System.Decimal FAILEDLOGINATTEMPTS
        {
            get
            {
                return this.m_FAILEDLOGINATTEMPTS;
            }
            set
            {
                this.m_FAILEDLOGINATTEMPTS = value;
            }
        }
        
        /// <summary>属性UPDATEDATE </summary>
        public System.DateTime UPDATEDATE
        {
            get
            {
                return this.m_UPDATEDATE;
            }
            set
            {
                this.m_UPDATEDATE = value;
            }
        }
    }
    
    /// ROLEEntity执行类
    public abstract class ROLEEntityAction
    {
        
        private ROLEEntityAction()
        {
        }
        
        public static void Save(ROLEEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static ROLEEntity RetrieveAROLEEntity(string ROLEID)
        {
            ROLEEntity obj=new ROLEEntity();
            obj.ROLEID=ROLEID;
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
        public static EntityContainer RetrieveROLEEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(ROLEEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetROLEEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(ROLEEntity));
            return rc.AsDataTable();
        }
    }
}
