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
//             Created Time： 2008-1-19 11:30:38
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
    public class FNPRINTEREntity : EntityObject
    {
        
        /// <summary>PRINTER_NO</summary>
        public const string __PRINTER_NO = "PRINTER_NO";
        
        /// <summary>PRINTER_NAME</summary>
        public const string __PRINTER_NAME = "PRINTER_NAME";
        
        /// <summary>PRINTER_TYPE</summary>
        public const string __PRINTER_TYPE = "PRINTER_TYPE";
        
        /// <summary>PRINTER_IP</summary>
        public const string __PRINTER_IP = "PRINTER_IP";
        
        /// <summary>ENABLE_FLAG</summary>
        public const string __ENABLE_FLAG = "ENABLE_FLAG";
        
        private System.Decimal m_PRINTER_NO;
        
        private string m_PRINTER_NAME;
        
        private string m_PRINTER_TYPE;
        
        private string m_PRINTER_IP;
        
        private string m_ENABLE_FLAG;
        
        /// <summary>构造函数</summary>
        public FNPRINTEREntity()
        {
        }
        
        /// <summary>属性PRINTER_NO </summary>
        public System.Decimal PRINTER_NO
        {
            get
            {
                return this.m_PRINTER_NO;
            }
            set
            {
                this.m_PRINTER_NO = value;
            }
        }
        
        /// <summary>属性PRINTER_NAME </summary>
        public string PRINTER_NAME
        {
            get
            {
                return this.m_PRINTER_NAME;
            }
            set
            {
                this.m_PRINTER_NAME = value;
            }
        }
        
        /// <summary>属性PRINTER_TYPE </summary>
        public string PRINTER_TYPE
        {
            get
            {
                return this.m_PRINTER_TYPE;
            }
            set
            {
                this.m_PRINTER_TYPE = value;
            }
        }
        
        /// <summary>属性PRINTER_IP </summary>
        public string PRINTER_IP
        {
            get
            {
                return this.m_PRINTER_IP;
            }
            set
            {
                this.m_PRINTER_IP = value;
            }
        }
        
        /// <summary>属性ENABLE_FLAG </summary>
        public string ENABLE_FLAG
        {
            get
            {
                return this.m_ENABLE_FLAG;
            }
            set
            {
                this.m_ENABLE_FLAG = value;
            }
        }
    }
    
    /// FNPRINTEREntity执行类
    public abstract class FNPRINTEREntityAction
    {
        
        private FNPRINTEREntityAction()
        {
        }
        
        public static void Save(FNPRINTEREntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNPRINTEREntity RetrieveAFNPRINTEREntity()
        {
            FNPRINTEREntity obj=new FNPRINTEREntity();
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
        public static EntityContainer RetrieveFNPRINTEREntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNPRINTEREntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNPRINTEREntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNPRINTEREntity));
            return rc.AsDataTable();
        }
    }
}
