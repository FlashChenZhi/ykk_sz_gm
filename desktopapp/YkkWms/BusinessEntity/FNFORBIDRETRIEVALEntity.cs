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
    public class FNFORBIDRETRIEVALEntity : EntityObject
    {
        
        /// <summary>ZAIKEY</summary>
        public const string __ZAIKEY = "ZAIKEY";
        
        /// <summary>FROM_TICKETNO</summary>
        public const string __FROM_TICKETNO = "FROM_TICKETNO";
        
        /// <summary>TO_TICKETNO</summary>
        public const string __TO_TICKETNO = "TO_TICKETNO";
        
        /// <summary>COLOR_CODE</summary>
        public const string __COLOR_CODE = "COLOR_CODE";
        
        /// <summary>REGISTER_DATE_TIME</summary>
        public const string __REGISTER_DATE_TIME = "REGISTER_DATE_TIME";
        
        private string m_ZAIKEY;
        
        private string m_FROM_TICKETNO;
        
        private string m_TO_TICKETNO;
        
        private string m_COLOR_CODE;
        
        private string m_REGISTER_DATE_TIME;
        
        /// <summary>构造函数</summary>
        public FNFORBIDRETRIEVALEntity()
        {
        }
        
        /// <summary>属性ZAIKEY </summary>
        public string ZAIKEY
        {
            get
            {
                return this.m_ZAIKEY;
            }
            set
            {
                this.m_ZAIKEY = value;
            }
        }
        
        /// <summary>属性FROM_TICKETNO </summary>
        public string FROM_TICKETNO
        {
            get
            {
                return this.m_FROM_TICKETNO;
            }
            set
            {
                this.m_FROM_TICKETNO = value;
            }
        }
        
        /// <summary>属性TO_TICKETNO </summary>
        public string TO_TICKETNO
        {
            get
            {
                return this.m_TO_TICKETNO;
            }
            set
            {
                this.m_TO_TICKETNO = value;
            }
        }
        
        /// <summary>属性COLOR_CODE </summary>
        public string COLOR_CODE
        {
            get
            {
                return this.m_COLOR_CODE;
            }
            set
            {
                this.m_COLOR_CODE = value;
            }
        }
        
        /// <summary>属性REGISTER_DATE_TIME </summary>
        public string REGISTER_DATE_TIME
        {
            get
            {
                return this.m_REGISTER_DATE_TIME;
            }
            set
            {
                this.m_REGISTER_DATE_TIME = value;
            }
        }
    }
    
    /// FNFORBIDRETRIEVALEntity执行类
    public abstract class FNFORBIDRETRIEVALEntityAction
    {
        
        private FNFORBIDRETRIEVALEntityAction()
        {
        }
        
        public static void Save(FNFORBIDRETRIEVALEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNFORBIDRETRIEVALEntity RetrieveAFNFORBIDRETRIEVALEntity()
        {
            FNFORBIDRETRIEVALEntity obj=new FNFORBIDRETRIEVALEntity();
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
        public static EntityContainer RetrieveFNFORBIDRETRIEVALEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNFORBIDRETRIEVALEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNFORBIDRETRIEVALEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNFORBIDRETRIEVALEntity));
            return rc.AsDataTable();
        }
    }
}
