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
    public class FMUSEREntity : EntityObject
    {
        
        /// <summary>USERID</summary>
        public const string __USERID = "USERID";
        
        /// <summary>USERNAME</summary>
        public const string __USERNAME = "USERNAME";
        
        /// <summary>PASSWORD</summary>
        public const string __PASSWORD = "PASSWORD";
        
        /// <summary>ACCOUNT</summary>
        public const string __ACCOUNT = "ACCOUNT";
        
        /// <summary>CLASS</summary>
        public const string __CLASS = "CLASS";
        
        /// <summary>HOMETERMNO</summary>
        public const string __HOMETERMNO = "HOMETERMNO";
        
        private System.Decimal m_USERID;
        
        private string m_USERNAME;
        
        private System.Decimal m_PASSWORD;
        
        private System.Decimal m_ACCOUNT;
        
        private System.Decimal m_CLASS;
        
        private string m_HOMETERMNO;
        
        /// <summary>构造函数</summary>
        public FMUSEREntity()
        {
        }
        
        /// <summary>属性USERID </summary>
        public System.Decimal USERID
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
        
        /// <summary>属性PASSWORD </summary>
        public System.Decimal PASSWORD
        {
            get
            {
                return this.m_PASSWORD;
            }
            set
            {
                this.m_PASSWORD = value;
            }
        }
        
        /// <summary>属性ACCOUNT </summary>
        public System.Decimal ACCOUNT
        {
            get
            {
                return this.m_ACCOUNT;
            }
            set
            {
                this.m_ACCOUNT = value;
            }
        }
        
        /// <summary>属性CLASS </summary>
        public System.Decimal CLASS
        {
            get
            {
                return this.m_CLASS;
            }
            set
            {
                this.m_CLASS = value;
            }
        }
        
        /// <summary>属性HOMETERMNO </summary>
        public string HOMETERMNO
        {
            get
            {
                return this.m_HOMETERMNO;
            }
            set
            {
                this.m_HOMETERMNO = value;
            }
        }
    }
    
    /// FMUSEREntity执行类
    public abstract class FMUSEREntityAction
    {
        
        private FMUSEREntityAction()
        {
        }
        
        public static void Save(FMUSEREntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FMUSEREntity RetrieveAFMUSEREntity(System.Decimal USERID)
        {
            FMUSEREntity obj=new FMUSEREntity();
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
        public static EntityContainer RetrieveFMUSEREntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FMUSEREntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFMUSEREntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FMUSEREntity));
            return rc.AsDataTable();
        }
    }
}
