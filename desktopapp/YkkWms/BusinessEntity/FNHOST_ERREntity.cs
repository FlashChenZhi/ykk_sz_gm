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
    public class FNHOST_ERREntity : EntityObject
    {
        
        /// <summary>REGISTER_DATE</summary>
        public const string __REGISTER_DATE = "REGISTER_DATE";
        
        /// <summary>REGISTER_TIME</summary>
        public const string __REGISTER_TIME = "REGISTER_TIME";
        
        /// <summary>HOST_TABLE</summary>
        public const string __HOST_TABLE = "HOST_TABLE";
        
        /// <summary>TEXT_ID</summary>
        public const string __TEXT_ID = "TEXT_ID";
        
        /// <summary>RECV_DATA</summary>
        public const string __RECV_DATA = "RECV_DATA";
        
        /// <summary>ERR_DATA</summary>
        public const string __ERR_DATA = "ERR_DATA";
        
        /// <summary>SEND_FLAG</summary>
        public const string __SEND_FLAG = "SEND_FLAG";
        
        private string m_REGISTER_DATE;
        
        private string m_REGISTER_TIME;
        
        private string m_HOST_TABLE;
        
        private string m_TEXT_ID;
        
        private string m_RECV_DATA;
        
        private string m_ERR_DATA;
        
        private string m_SEND_FLAG;
        
        /// <summary>构造函数</summary>
        public FNHOST_ERREntity()
        {
        }
        
        /// <summary>属性REGISTER_DATE </summary>
        public string REGISTER_DATE
        {
            get
            {
                return this.m_REGISTER_DATE;
            }
            set
            {
                this.m_REGISTER_DATE = value;
            }
        }
        
        /// <summary>属性REGISTER_TIME </summary>
        public string REGISTER_TIME
        {
            get
            {
                return this.m_REGISTER_TIME;
            }
            set
            {
                this.m_REGISTER_TIME = value;
            }
        }
        
        /// <summary>属性HOST_TABLE </summary>
        public string HOST_TABLE
        {
            get
            {
                return this.m_HOST_TABLE;
            }
            set
            {
                this.m_HOST_TABLE = value;
            }
        }
        
        /// <summary>属性TEXT_ID </summary>
        public string TEXT_ID
        {
            get
            {
                return this.m_TEXT_ID;
            }
            set
            {
                this.m_TEXT_ID = value;
            }
        }
        
        /// <summary>属性RECV_DATA </summary>
        public string RECV_DATA
        {
            get
            {
                return this.m_RECV_DATA;
            }
            set
            {
                this.m_RECV_DATA = value;
            }
        }
        
        /// <summary>属性ERR_DATA </summary>
        public string ERR_DATA
        {
            get
            {
                return this.m_ERR_DATA;
            }
            set
            {
                this.m_ERR_DATA = value;
            }
        }
        
        /// <summary>属性SEND_FLAG </summary>
        public string SEND_FLAG
        {
            get
            {
                return this.m_SEND_FLAG;
            }
            set
            {
                this.m_SEND_FLAG = value;
            }
        }
    }
    
    /// FNHOST_ERREntity执行类
    public abstract class FNHOST_ERREntityAction
    {
        
        private FNHOST_ERREntityAction()
        {
        }
        
        public static void Save(FNHOST_ERREntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNHOST_ERREntity RetrieveAFNHOST_ERREntity()
        {
            FNHOST_ERREntity obj=new FNHOST_ERREntity();
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
        public static EntityContainer RetrieveFNHOST_ERREntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNHOST_ERREntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNHOST_ERREntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNHOST_ERREntity));
            return rc.AsDataTable();
        }
    }
}
