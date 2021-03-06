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
    public class MAINMENUEntity : EntityObject
    {
        
        /// <summary>MENUID</summary>
        public const string __MENUID = "MENUID";
        
        /// <summary>MENUDISPNUMBER</summary>
        public const string __MENUDISPNUMBER = "MENUDISPNUMBER";
        
        /// <summary>MENURESOURCEKEY</summary>
        public const string __MENURESOURCEKEY = "MENURESOURCEKEY";
        
        /// <summary>SHOWTYPE</summary>
        public const string __SHOWTYPE = "SHOWTYPE";
        
        /// <summary>UPDATEDATE</summary>
        public const string __UPDATEDATE = "UPDATEDATE";
        
        private string m_MENUID;
        
        private System.Decimal m_MENUDISPNUMBER;
        
        private string m_MENURESOURCEKEY;
        
        private System.Decimal m_SHOWTYPE;
        
        private System.DateTime m_UPDATEDATE = DateTime.MinValue;
        
        /// <summary>构造函数</summary>
        public MAINMENUEntity()
        {
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
        
        /// <summary>属性MENUDISPNUMBER </summary>
        public System.Decimal MENUDISPNUMBER
        {
            get
            {
                return this.m_MENUDISPNUMBER;
            }
            set
            {
                this.m_MENUDISPNUMBER = value;
            }
        }
        
        /// <summary>属性MENURESOURCEKEY </summary>
        public string MENURESOURCEKEY
        {
            get
            {
                return this.m_MENURESOURCEKEY;
            }
            set
            {
                this.m_MENURESOURCEKEY = value;
            }
        }
        
        /// <summary>属性SHOWTYPE </summary>
        public System.Decimal SHOWTYPE
        {
            get
            {
                return this.m_SHOWTYPE;
            }
            set
            {
                this.m_SHOWTYPE = value;
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
    
    /// MAINMENUEntity执行类
    public abstract class MAINMENUEntityAction
    {
        
        private MAINMENUEntityAction()
        {
        }
        
        public static void Save(MAINMENUEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static MAINMENUEntity RetrieveAMAINMENUEntity(string MENUID)
        {
            MAINMENUEntity obj=new MAINMENUEntity();
            obj.MENUID=MENUID;
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
        public static EntityContainer RetrieveMAINMENUEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(MAINMENUEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetMAINMENUEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(MAINMENUEntity));
            return rc.AsDataTable();
        }
    }
}
