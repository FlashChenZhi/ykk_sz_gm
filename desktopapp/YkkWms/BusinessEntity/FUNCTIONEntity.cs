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
    public class FUNCTIONEntity : EntityObject
    {
        
        /// <summary>MAINFUNCTIONID</summary>
        public const string __MAINFUNCTIONID = "MAINFUNCTIONID";
        
        /// <summary>FUNCTIONDISPNUMBER</summary>
        public const string __FUNCTIONDISPNUMBER = "FUNCTIONDISPNUMBER";
        
        /// <summary>FUNCTIONRESOURCEKEY</summary>
        public const string __FUNCTIONRESOURCEKEY = "FUNCTIONRESOURCEKEY";
        
        /// <summary>MENUID</summary>
        public const string __MENUID = "MENUID";
        
        /// <summary>UPDATEDATE</summary>
        public const string __UPDATEDATE = "UPDATEDATE";
        
        private string m_MAINFUNCTIONID;
        
        private System.Decimal m_FUNCTIONDISPNUMBER;
        
        private string m_FUNCTIONRESOURCEKEY;
        
        private string m_MENUID;
        
        private System.DateTime m_UPDATEDATE = DateTime.MinValue;
        
        /// <summary>构造函数</summary>
        public FUNCTIONEntity()
        {
        }
        
        /// <summary>属性MAINFUNCTIONID </summary>
        public string MAINFUNCTIONID
        {
            get
            {
                return this.m_MAINFUNCTIONID;
            }
            set
            {
                this.m_MAINFUNCTIONID = value;
            }
        }
        
        /// <summary>属性FUNCTIONDISPNUMBER </summary>
        public System.Decimal FUNCTIONDISPNUMBER
        {
            get
            {
                return this.m_FUNCTIONDISPNUMBER;
            }
            set
            {
                this.m_FUNCTIONDISPNUMBER = value;
            }
        }
        
        /// <summary>属性FUNCTIONRESOURCEKEY </summary>
        public string FUNCTIONRESOURCEKEY
        {
            get
            {
                return this.m_FUNCTIONRESOURCEKEY;
            }
            set
            {
                this.m_FUNCTIONRESOURCEKEY = value;
            }
        }
        
        /// <summary>属性MENUID </summary>
        public string MENUID
        {
            get
            {
                return this.m_MENUID;
            }
            set
            {
                this.m_MENUID = value;
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
    
    /// FUNCTIONEntity执行类
    public abstract class FUNCTIONEntityAction
    {
        
        private FUNCTIONEntityAction()
        {
        }
        
        public static void Save(FUNCTIONEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FUNCTIONEntity RetrieveAFUNCTIONEntity(string MAINFUNCTIONID)
        {
            FUNCTIONEntity obj=new FUNCTIONEntity();
            obj.MAINFUNCTIONID=MAINFUNCTIONID;
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
        public static EntityContainer RetrieveFUNCTIONEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FUNCTIONEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFUNCTIONEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FUNCTIONEntity));
            return rc.AsDataTable();
        }
    }
}
