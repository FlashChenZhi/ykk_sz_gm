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
    public class ROLEMAPEntity : EntityObject
    {
        
        /// <summary>ROLEID</summary>
        public const string __ROLEID = "ROLEID";
        
        /// <summary>FUNCTIONID</summary>
        public const string __FUNCTIONID = "FUNCTIONID";
        
        /// <summary>UPDATEDATE</summary>
        public const string __UPDATEDATE = "UPDATEDATE";
        
        private string m_ROLEID;
        
        private string m_FUNCTIONID;
        
        private System.DateTime m_UPDATEDATE = DateTime.MinValue;
        
        /// <summary>构造函数</summary>
        public ROLEMAPEntity()
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
        
        /// <summary>属性FUNCTIONID </summary>
        public string FUNCTIONID
        {
            get
            {
                return this.m_FUNCTIONID;
            }
            set
            {
                this.m_FUNCTIONID = value;
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
    
    /// ROLEMAPEntity执行类
    public abstract class ROLEMAPEntityAction
    {
        
        private ROLEMAPEntityAction()
        {
        }
        
        public static void Save(ROLEMAPEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static ROLEMAPEntity RetrieveAROLEMAPEntity(string ROLEID, string FUNCTIONID)
        {
            ROLEMAPEntity obj=new ROLEMAPEntity();
            obj.ROLEID=ROLEID;
            obj.FUNCTIONID=FUNCTIONID;
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
        public static EntityContainer RetrieveROLEMAPEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(ROLEMAPEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetROLEMAPEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(ROLEMAPEntity));
            return rc.AsDataTable();
        }
    }
}