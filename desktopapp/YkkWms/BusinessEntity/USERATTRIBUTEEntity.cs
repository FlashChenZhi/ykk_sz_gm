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
    public class USERATTRIBUTEEntity : EntityObject
    {
        
        /// <summary>USERID</summary>
        public const string __USERID = "USERID";
        
        /// <summary>USERNAME</summary>
        public const string __USERNAME = "USERNAME";
        
        /// <summary>BELONGING</summary>
        public const string __BELONGING = "BELONGING";
        
        /// <summary>BIRTHDATE</summary>
        public const string __BIRTHDATE = "BIRTHDATE";
        
        /// <summary>SEX</summary>
        public const string __SEX = "SEX";
        
        /// <summary>NOTE</summary>
        public const string __NOTE = "NOTE";
        
        /// <summary>UPDATEDATE</summary>
        public const string __UPDATEDATE = "UPDATEDATE";
        
        private string m_USERID;
        
        private string m_USERNAME;
        
        private string m_BELONGING;
        
        private System.DateTime m_BIRTHDATE = DateTime.MinValue;
        
        private System.Decimal m_SEX;
        
        private string m_NOTE;
        
        private System.DateTime m_UPDATEDATE = DateTime.MinValue;
        
        /// <summary>构造函数</summary>
        public USERATTRIBUTEEntity()
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
        
        /// <summary>属性USERNAME </summary>
        public string USERNAME
        {
            get
            {
                return this.m_USERNAME;
            }
            set
            {
                this.m_USERNAME = value;
            }
        }
        
        /// <summary>属性BELONGING </summary>
        public string BELONGING
        {
            get
            {
                return this.m_BELONGING;
            }
            set
            {
                this.m_BELONGING = value;
            }
        }
        
        /// <summary>属性BIRTHDATE </summary>
        public System.DateTime BIRTHDATE
        {
            get
            {
                return this.m_BIRTHDATE;
            }
            set
            {
                this.m_BIRTHDATE = value;
            }
        }
        
        /// <summary>属性SEX </summary>
        public System.Decimal SEX
        {
            get
            {
                return this.m_SEX;
            }
            set
            {
                this.m_SEX = value;
            }
        }
        
        /// <summary>属性NOTE </summary>
        public string NOTE
        {
            get
            {
                return this.m_NOTE;
            }
            set
            {
                this.m_NOTE = value;
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
    
    /// USERATTRIBUTEEntity执行类
    public abstract class USERATTRIBUTEEntityAction
    {
        
        private USERATTRIBUTEEntityAction()
        {
        }
        
        public static void Save(USERATTRIBUTEEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static USERATTRIBUTEEntity RetrieveAUSERATTRIBUTEEntity(string USERID)
        {
            USERATTRIBUTEEntity obj=new USERATTRIBUTEEntity();
            obj.USERID=USERID;
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
        public static EntityContainer RetrieveUSERATTRIBUTEEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(USERATTRIBUTEEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetUSERATTRIBUTEEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(USERATTRIBUTEEntity));
            return rc.AsDataTable();
        }
    }
}
