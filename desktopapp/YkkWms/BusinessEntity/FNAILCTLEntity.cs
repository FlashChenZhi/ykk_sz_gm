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
    public class FNAILCTLEntity : EntityObject
    {
        
        /// <summary>AILENO</summary>
        public const string __AILENO = "AILENO";
        
        /// <summary>AILEORDER</summary>
        public const string __AILEORDER = "AILEORDER";
        
        /// <summary>SOKOKBN</summary>
        public const string __SOKOKBN = "SOKOKBN";
        
        /// <summary>STNO</summary>
        public const string __STNO = "STNO";
        
        /// <summary>AILE_STR_1F</summary>
        public const string __AILE_STR_1F = "AILE_STR_1F";
        
        /// <summary>AILE_STR_2F</summary>
        public const string __AILE_STR_2F = "AILE_STR_2F";
        
        /// <summary>AILE_STR_3F</summary>
        public const string __AILE_STR_3F = "AILE_STR_3F";
        
        /// <summary>SEARCH_BKORDER_1F</summary>
        public const string __SEARCH_BKORDER_1F = "SEARCH_BKORDER_1F";
        
        /// <summary>SEARCH_BKORDER_2F</summary>
        public const string __SEARCH_BKORDER_2F = "SEARCH_BKORDER_2F";
        
        /// <summary>SEARCH_BKORDER_3F</summary>
        public const string __SEARCH_BKORDER_3F = "SEARCH_BKORDER_3F";
        
        /// <summary>AILE_CAPA_SIZE_1F</summary>
        public const string __AILE_CAPA_SIZE_1F = "AILE_CAPA_SIZE_1F";
        
        /// <summary>AILE_CAPA_SIZE_2F</summary>
        public const string __AILE_CAPA_SIZE_2F = "AILE_CAPA_SIZE_2F";
        
        /// <summary>AILE_CAPA_SIZE_3F</summary>
        public const string __AILE_CAPA_SIZE_3F = "AILE_CAPA_SIZE_3F";
        
        /// <summary>BANKORDER</summary>
        public const string __BANKORDER = "BANKORDER";
        
        /// <summary>BANKNO</summary>
        public const string __BANKNO = "BANKNO";
        
        private System.Decimal m_AILENO;
        
        private System.Decimal m_AILEORDER;
        
        private string m_SOKOKBN;
        
        private string m_STNO;
        
        private System.Decimal m_AILE_STR_1F;
        
        private System.Decimal m_AILE_STR_2F;
        
        private System.Decimal m_AILE_STR_3F;
        
        private System.Decimal m_SEARCH_BKORDER_1F;
        
        private System.Decimal m_SEARCH_BKORDER_2F;
        
        private System.Decimal m_SEARCH_BKORDER_3F;
        
        private System.Decimal m_AILE_CAPA_SIZE_1F;
        
        private System.Decimal m_AILE_CAPA_SIZE_2F;
        
        private System.Decimal m_AILE_CAPA_SIZE_3F;
        
        private System.Decimal m_BANKORDER;
        
        private System.Decimal m_BANKNO;
        
        /// <summary>构造函数</summary>
        public FNAILCTLEntity()
        {
        }
        
        /// <summary>属性AILENO </summary>
        public System.Decimal AILENO
        {
            get
            {
                return this.m_AILENO;
            }
            set
            {
                this.m_AILENO = value;
            }
        }
        
        /// <summary>属性AILEORDER </summary>
        public System.Decimal AILEORDER
        {
            get
            {
                return this.m_AILEORDER;
            }
            set
            {
                this.m_AILEORDER = value;
            }
        }
        
        /// <summary>属性SOKOKBN </summary>
        public string SOKOKBN
        {
            get
            {
                return this.m_SOKOKBN;
            }
            set
            {
                this.m_SOKOKBN = value;
            }
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
        
        /// <summary>属性AILE_STR_1F </summary>
        public System.Decimal AILE_STR_1F
        {
            get
            {
                return this.m_AILE_STR_1F;
            }
            set
            {
                this.m_AILE_STR_1F = value;
            }
        }
        
        /// <summary>属性AILE_STR_2F </summary>
        public System.Decimal AILE_STR_2F
        {
            get
            {
                return this.m_AILE_STR_2F;
            }
            set
            {
                this.m_AILE_STR_2F = value;
            }
        }
        
        /// <summary>属性AILE_STR_3F </summary>
        public System.Decimal AILE_STR_3F
        {
            get
            {
                return this.m_AILE_STR_3F;
            }
            set
            {
                this.m_AILE_STR_3F = value;
            }
        }
        
        /// <summary>属性SEARCH_BKORDER_1F </summary>
        public System.Decimal SEARCH_BKORDER_1F
        {
            get
            {
                return this.m_SEARCH_BKORDER_1F;
            }
            set
            {
                this.m_SEARCH_BKORDER_1F = value;
            }
        }
        
        /// <summary>属性SEARCH_BKORDER_2F </summary>
        public System.Decimal SEARCH_BKORDER_2F
        {
            get
            {
                return this.m_SEARCH_BKORDER_2F;
            }
            set
            {
                this.m_SEARCH_BKORDER_2F = value;
            }
        }
        
        /// <summary>属性SEARCH_BKORDER_3F </summary>
        public System.Decimal SEARCH_BKORDER_3F
        {
            get
            {
                return this.m_SEARCH_BKORDER_3F;
            }
            set
            {
                this.m_SEARCH_BKORDER_3F = value;
            }
        }
        
        /// <summary>属性AILE_CAPA_SIZE_1F </summary>
        public System.Decimal AILE_CAPA_SIZE_1F
        {
            get
            {
                return this.m_AILE_CAPA_SIZE_1F;
            }
            set
            {
                this.m_AILE_CAPA_SIZE_1F = value;
            }
        }
        
        /// <summary>属性AILE_CAPA_SIZE_2F </summary>
        public System.Decimal AILE_CAPA_SIZE_2F
        {
            get
            {
                return this.m_AILE_CAPA_SIZE_2F;
            }
            set
            {
                this.m_AILE_CAPA_SIZE_2F = value;
            }
        }
        
        /// <summary>属性AILE_CAPA_SIZE_3F </summary>
        public System.Decimal AILE_CAPA_SIZE_3F
        {
            get
            {
                return this.m_AILE_CAPA_SIZE_3F;
            }
            set
            {
                this.m_AILE_CAPA_SIZE_3F = value;
            }
        }
        
        /// <summary>属性BANKORDER </summary>
        public System.Decimal BANKORDER
        {
            get
            {
                return this.m_BANKORDER;
            }
            set
            {
                this.m_BANKORDER = value;
            }
        }
        
        /// <summary>属性BANKNO </summary>
        public System.Decimal BANKNO
        {
            get
            {
                return this.m_BANKNO;
            }
            set
            {
                this.m_BANKNO = value;
            }
        }
    }
    
    /// FNAILCTLEntity执行类
    public abstract class FNAILCTLEntityAction
    {
        
        private FNAILCTLEntityAction()
        {
        }
        
        public static void Save(FNAILCTLEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNAILCTLEntity RetrieveAFNAILCTLEntity()
        {
            FNAILCTLEntity obj=new FNAILCTLEntity();
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
        public static EntityContainer RetrieveFNAILCTLEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNAILCTLEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNAILCTLEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNAILCTLEntity));
            return rc.AsDataTable();
        }
    }
}
