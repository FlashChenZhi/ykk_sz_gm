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
    public class FHRETRIEVALRESULTEntity : EntityObject
    {
        
        /// <summary>RETRIEVAL_NO</summary>
        public const string __RETRIEVAL_NO = "RETRIEVAL_NO";
        
        /// <summary>SERIAL_NO</summary>
        public const string __SERIAL_NO = "SERIAL_NO";
        
        /// <summary>TICKET_NO</summary>
        public const string __TICKET_NO = "TICKET_NO";
        
        /// <summary>ITEM_CODE</summary>
        public const string __ITEM_CODE = "ITEM_CODE";
        
        /// <summary>COLOR_CODE</summary>
        public const string __COLOR_CODE = "COLOR_CODE";
        
        /// <summary>DEPOT_CODE</summary>
        public const string __DEPOT_CODE = "DEPOT_CODE";
        
        /// <summary>SECTION</summary>
        public const string __SECTION = "SECTION";
        
        /// <summary>LINE</summary>
        public const string __LINE = "LINE";
        
        /// <summary>RESULT_QTY</summary>
        public const string __RESULT_QTY = "RESULT_QTY";
        
        /// <summary>RESULT_WEIGHT</summary>
        public const string __RESULT_WEIGHT = "RESULT_WEIGHT";
        
        /// <summary>RETRIEVAL_PLANKEY</summary>
        public const string __RETRIEVAL_PLANKEY = "RETRIEVAL_PLANKEY";
        
        /// <summary>PROC_FLAG</summary>
        public const string __PROC_FLAG = "PROC_FLAG";
        
        /// <summary>REGISTER_DATE</summary>
        public const string __REGISTER_DATE = "REGISTER_DATE";
        
        /// <summary>REGISTER_TIME</summary>
        public const string __REGISTER_TIME = "REGISTER_TIME";
        
        private string m_RETRIEVAL_NO;
        
        private System.Decimal m_SERIAL_NO;
        
        private string m_TICKET_NO;
        
        private string m_ITEM_CODE;
        
        private string m_COLOR_CODE;
        
        private string m_DEPOT_CODE;
        
        private string m_SECTION;
        
        private string m_LINE;
        
        private System.Decimal m_RESULT_QTY;
        
        private System.Decimal m_RESULT_WEIGHT;
        
        private string m_RETRIEVAL_PLANKEY;
        
        private string m_PROC_FLAG;
        
        private string m_REGISTER_DATE;
        
        private string m_REGISTER_TIME;
        
        /// <summary>构造函数</summary>
        public FHRETRIEVALRESULTEntity()
        {
        }
        
        /// <summary>属性RETRIEVAL_NO </summary>
        public string RETRIEVAL_NO
        {
            get
            {
                return this.m_RETRIEVAL_NO;
            }
            set
            {
                this.m_RETRIEVAL_NO = value;
            }
        }
        
        /// <summary>属性SERIAL_NO </summary>
        public System.Decimal SERIAL_NO
        {
            get
            {
                return this.m_SERIAL_NO;
            }
            set
            {
                this.m_SERIAL_NO = value;
            }
        }
        
        /// <summary>属性TICKET_NO </summary>
        public string TICKET_NO
        {
            get
            {
                return this.m_TICKET_NO;
            }
            set
            {
                this.m_TICKET_NO = value;
            }
        }
        
        /// <summary>属性ITEM_CODE </summary>
        public string ITEM_CODE
        {
            get
            {
                return this.m_ITEM_CODE;
            }
            set
            {
                this.m_ITEM_CODE = value;
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
        
        /// <summary>属性DEPOT_CODE </summary>
        public string DEPOT_CODE
        {
            get
            {
                return this.m_DEPOT_CODE;
            }
            set
            {
                this.m_DEPOT_CODE = value;
            }
        }
        
        /// <summary>属性SECTION </summary>
        public string SECTION
        {
            get
            {
                return this.m_SECTION;
            }
            set
            {
                this.m_SECTION = value;
            }
        }
        
        /// <summary>属性LINE </summary>
        public string LINE
        {
            get
            {
                return this.m_LINE;
            }
            set
            {
                this.m_LINE = value;
            }
        }
        
        /// <summary>属性RESULT_QTY </summary>
        public System.Decimal RESULT_QTY
        {
            get
            {
                return this.m_RESULT_QTY;
            }
            set
            {
                this.m_RESULT_QTY = value;
            }
        }
        
        /// <summary>属性RESULT_WEIGHT </summary>
        public System.Decimal RESULT_WEIGHT
        {
            get
            {
                return this.m_RESULT_WEIGHT;
            }
            set
            {
                this.m_RESULT_WEIGHT = value;
            }
        }
        
        /// <summary>属性RETRIEVAL_PLANKEY </summary>
        public string RETRIEVAL_PLANKEY
        {
            get
            {
                return this.m_RETRIEVAL_PLANKEY;
            }
            set
            {
                this.m_RETRIEVAL_PLANKEY = value;
            }
        }
        
        /// <summary>属性PROC_FLAG </summary>
        public string PROC_FLAG
        {
            get
            {
                return this.m_PROC_FLAG;
            }
            set
            {
                this.m_PROC_FLAG = value;
            }
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
    }
    
    /// FHRETRIEVALRESULTEntity执行类
    public abstract class FHRETRIEVALRESULTEntityAction
    {
        
        private FHRETRIEVALRESULTEntityAction()
        {
        }
        
        public static void Save(FHRETRIEVALRESULTEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FHRETRIEVALRESULTEntity RetrieveAFHRETRIEVALRESULTEntity()
        {
            FHRETRIEVALRESULTEntity obj=new FHRETRIEVALRESULTEntity();
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
        public static EntityContainer RetrieveFHRETRIEVALRESULTEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FHRETRIEVALRESULTEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFHRETRIEVALRESULTEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FHRETRIEVALRESULTEntity));
            return rc.AsDataTable();
        }
    }
}