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
    public class FNRUTESTEntity : EntityObject
    {
        
        /// <summary>RUTEID</summary>
        public const string __RUTEID = "RUTEID";
        
        /// <summary>STNO</summary>
        public const string __STNO = "STNO";
        
        /// <summary>STATUS</summary>
        public const string __STATUS = "STATUS";
        
        private string m_RUTEID;
        
        private string m_STNO;
        
        private string m_STATUS;
        
        /// <summary>构造函数</summary>
        public FNRUTESTEntity()
        {
        }
        
        /// <summary>属性RUTEID </summary>
        public string RUTEID
        {
            get
            {
                return this.m_RUTEID;
            }
            set
            {
                this.m_RUTEID = value;
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
        
        /// <summary>属性STATUS </summary>
        public string STATUS
        {
            get
            {
                return this.m_STATUS;
            }
            set
            {
                this.m_STATUS = value;
            }
        }
    }
    
    /// FNRUTESTEntity执行类
    public abstract class FNRUTESTEntityAction
    {
        
        private FNRUTESTEntityAction()
        {
        }
        
        public static void Save(FNRUTESTEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNRUTESTEntity RetrieveAFNRUTESTEntity()
        {
            FNRUTESTEntity obj=new FNRUTESTEntity();
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
        public static EntityContainer RetrieveFNRUTESTEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNRUTESTEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNRUTESTEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNRUTESTEntity));
            return rc.AsDataTable();
        }
    }
}
