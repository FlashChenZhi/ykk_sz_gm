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
    public class USERTERMINALEntity : EntityObject
    {
        
        /// <summary>USERID</summary>
        public const string __USERID = "USERID";
        
        /// <summary>TERMINALNUMBER</summary>
        public const string __TERMINALNUMBER = "TERMINALNUMBER";
        
        private string m_USERID;
        
        private string m_TERMINALNUMBER;
        
        /// <summary>构造函数</summary>
        public USERTERMINALEntity()
        {
        }
        
        /// <summary>属性USERID </summary>
        public string USERID
        {
            get
            {
                return this.m_USERID;
            }
            set
            {
                this.m_USERID = value;
            }
        }
        
        /// <summary>属性TERMINALNUMBER </summary>
        public string TERMINALNUMBER
        {
            get
            {
                return this.m_TERMINALNUMBER;
            }
            set
            {
                this.m_TERMINALNUMBER = value;
            }
        }
    }
    
    /// USERTERMINALEntity执行类
    public abstract class USERTERMINALEntityAction
    {
        
        private USERTERMINALEntityAction()
        {
        }
        
        public static void Save(USERTERMINALEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static USERTERMINALEntity RetrieveAUSERTERMINALEntity()
        {
            USERTERMINALEntity obj=new USERTERMINALEntity();
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
        public static EntityContainer RetrieveUSERTERMINALEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(USERTERMINALEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetUSERTERMINALEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(USERTERMINALEntity));
            return rc.AsDataTable();
        }
    }
}
