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
    public class FHDIFFERENCESTOCKEntity : EntityObject
    {
        
        /// <summary>DIFFERENCE_FLAG</summary>
        public const string __DIFFERENCE_FLAG = "DIFFERENCE_FLAG";
        
        /// <summary>TICKET_NO</summary>
        public const string __TICKET_NO = "TICKET_NO";
        
        /// <summary>ITEM_CODE</summary>
        public const string __ITEM_CODE = "ITEM_CODE";
        
        /// <summary>COLOR_CODE</summary>
        public const string __COLOR_CODE = "COLOR_CODE";
        
        /// <summary>DIFFERENCE_TYPE</summary>
        public const string __DIFFERENCE_TYPE = "DIFFERENCE_TYPE";
        
        /// <summary>DIFFERENCE_QTY</summary>
        public const string __DIFFERENCE_QTY = "DIFFERENCE_QTY";
        
        /// <summary>DIFFERENCE_WEIGHT</summary>
        public const string __DIFFERENCE_WEIGHT = "DIFFERENCE_WEIGHT";
        
        /// <summary>PROC_FLAG</summary>
        public const string __PROC_FLAG = "PROC_FLAG";
        
        /// <summary>REGISTER_DATE</summary>
        public const string __REGISTER_DATE = "REGISTER_DATE";
        
        /// <summary>REGISTER_TIME</summary>
        public const string __REGISTER_TIME = "REGISTER_TIME";
        
        private string m_DIFFERENCE_FLAG;
        
        private string m_TICKET_NO;
        
        private string m_ITEM_CODE;
        
        private string m_COLOR_CODE;
        
        private string m_DIFFERENCE_TYPE;
        
        private System.Decimal m_DIFFERENCE_QTY;
        
        private System.Decimal m_DIFFERENCE_WEIGHT;
        
        private string m_PROC_FLAG;
        
        private string m_REGISTER_DATE;
        
        private string m_REGISTER_TIME;
        
        /// <summary>构造函数</summary>
        public FHDIFFERENCESTOCKEntity()
        {
        }
        
        /// <summary>属性DIFFERENCE_FLAG </summary>
        public string DIFFERENCE_FLAG
        {
            get
            {
                return this.m_DIFFERENCE_FLAG;
            }
            set
            {
                this.m_DIFFERENCE_FLAG = value;
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
        
        /// <summary>属性DIFFERENCE_TYPE </summary>
        public string DIFFERENCE_TYPE
        {
            get
            {
                return this.m_DIFFERENCE_TYPE;
            }
            set
            {
                this.m_DIFFERENCE_TYPE = value;
            }
        }
        
        /// <summary>属性DIFFERENCE_QTY </summary>
        public System.Decimal DIFFERENCE_QTY
        {
            get
            {
                return this.m_DIFFERENCE_QTY;
            }
            set
            {
                this.m_DIFFERENCE_QTY = value;
            }
        }
        
        /// <summary>属性DIFFERENCE_WEIGHT </summary>
        public System.Decimal DIFFERENCE_WEIGHT
        {
            get
            {
                return this.m_DIFFERENCE_WEIGHT;
            }
            set
            {
                this.m_DIFFERENCE_WEIGHT = value;
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
    
    /// FHDIFFERENCESTOCKEntity执行类
    public abstract class FHDIFFERENCESTOCKEntityAction
    {
        
        private FHDIFFERENCESTOCKEntityAction()
        {
        }
        
        public static void Save(FHDIFFERENCESTOCKEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FHDIFFERENCESTOCKEntity RetrieveAFHDIFFERENCESTOCKEntity()
        {
            FHDIFFERENCESTOCKEntity obj=new FHDIFFERENCESTOCKEntity();
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
        public static EntityContainer RetrieveFHDIFFERENCESTOCKEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FHDIFFERENCESTOCKEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFHDIFFERENCESTOCKEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FHDIFFERENCESTOCKEntity));
            return rc.AsDataTable();
        }
    }
}
