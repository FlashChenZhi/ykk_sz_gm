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
    public class FNCART_CTLEntity : EntityObject
    {
        
        /// <summary>STNO</summary>
        public const string __STNO = "STNO";
        
        /// <summary>SEQ_NO</summary>
        public const string __SEQ_NO = "SEQ_NO";
        
        /// <summary>GROUPNO</summary>
        public const string __GROUPNO = "GROUPNO";
        
        /// <summary>BUCKET_NO</summary>
        public const string __BUCKET_NO = "BUCKET_NO";
        
        /// <summary>ITEM_CODE</summary>
        public const string __ITEM_CODE = "ITEM_CODE";
        
        /// <summary>ITEM_NAME</summary>
        public const string __ITEM_NAME = "ITEM_NAME";
        
        /// <summary>ITEM_NAME2</summary>
        public const string __ITEM_NAME2 = "ITEM_NAME2";
        
        /// <summary>ITEM_NAME3</summary>
        public const string __ITEM_NAME3 = "ITEM_NAME3";
        
        /// <summary>SECTION</summary>
        public const string __SECTION = "SECTION";
        
        /// <summary>LINE</summary>
        public const string __LINE = "LINE";
        
        /// <summary>QTY</summary>
        public const string __QTY = "QTY";
        
        /// <summary>WEIGHT</summary>
        public const string __WEIGHT = "WEIGHT";
        
        /// <summary>REGISTER_FLG</summary>
        public const string __REGISTER_FLG = "REGISTER_FLG";
        
        /// <summary>REGISTER_DATE_TIME</summary>
        public const string __REGISTER_DATE_TIME = "REGISTER_DATE_TIME";
        
        private string m_STNO;
        
        private string m_SEQ_NO;
        
        private string m_GROUPNO;
        
        private string m_BUCKET_NO;
        
        private string m_ITEM_CODE;
        
        private string m_ITEM_NAME;
        
        private string m_ITEM_NAME2;
        
        private string m_ITEM_NAME3;
        
        private string m_SECTION;
        
        private string m_LINE;
        
        private System.Decimal m_QTY;
        
        private System.Decimal m_WEIGHT;
        
        private string m_REGISTER_FLG;
        
        private string m_REGISTER_DATE_TIME;
        
        /// <summary>构造函数</summary>
        public FNCART_CTLEntity()
        {
        }
        
        /// <summary>属性STNO </summary>
        public string STNO
        {
            get
            {
                return this.m_STNO;
            }
            set
            {
                this.m_STNO = value;
            }
        }
        
        /// <summary>属性SEQ_NO </summary>
        public string SEQ_NO
        {
            get
            {
                return this.m_SEQ_NO;
            }
            set
            {
                this.m_SEQ_NO = value;
            }
        }
        
        /// <summary>属性GROUPNO </summary>
        public string GROUPNO
        {
            get
            {
                return this.m_GROUPNO;
            }
            set
            {
                this.m_GROUPNO = value;
            }
        }
        
        /// <summary>属性BUCKET_NO </summary>
        public string BUCKET_NO
        {
            get
            {
                return this.m_BUCKET_NO;
            }
            set
            {
                this.m_BUCKET_NO = value;
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
        
        /// <summary>属性ITEM_NAME </summary>
        public string ITEM_NAME
        {
            get
            {
                return this.m_ITEM_NAME;
            }
            set
            {
                this.m_ITEM_NAME = value;
            }
        }
        
        /// <summary>属性ITEM_NAME2 </summary>
        public string ITEM_NAME2
        {
            get
            {
                return this.m_ITEM_NAME2;
            }
            set
            {
                this.m_ITEM_NAME2 = value;
            }
        }
        
        /// <summary>属性ITEM_NAME3 </summary>
        public string ITEM_NAME3
        {
            get
            {
                return this.m_ITEM_NAME3;
            }
            set
            {
                this.m_ITEM_NAME3 = value;
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
        
        /// <summary>属性QTY </summary>
        public System.Decimal QTY
        {
            get
            {
                return this.m_QTY;
            }
            set
            {
                this.m_QTY = value;
            }
        }
        
        /// <summary>属性WEIGHT </summary>
        public System.Decimal WEIGHT
        {
            get
            {
                return this.m_WEIGHT;
            }
            set
            {
                this.m_WEIGHT = value;
            }
        }
        
        /// <summary>属性REGISTER_FLG </summary>
        public string REGISTER_FLG
        {
            get
            {
                return this.m_REGISTER_FLG;
            }
            set
            {
                this.m_REGISTER_FLG = value;
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
    
    /// FNCART_CTLEntity执行类
    public abstract class FNCART_CTLEntityAction
    {
        
        private FNCART_CTLEntityAction()
        {
        }
        
        public static void Save(FNCART_CTLEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNCART_CTLEntity RetrieveAFNCART_CTLEntity()
        {
            FNCART_CTLEntity obj=new FNCART_CTLEntity();
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
        public static EntityContainer RetrieveFNCART_CTLEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNCART_CTLEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNCART_CTLEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNCART_CTLEntity));
            return rc.AsDataTable();
        }
    }
}
