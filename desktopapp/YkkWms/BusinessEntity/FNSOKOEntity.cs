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
    public class FNSOKOEntity : EntityObject
    {
        
        /// <summary>SOKOKBN</summary>
        public const string __SOKOKBN = "SOKOKBN";
        
        /// <summary>SOKONAME</summary>
        public const string __SOKONAME = "SOKONAME";
        
        /// <summary>AILEORDER</summary>
        public const string __AILEORDER = "AILEORDER";
        
        /// <summary>KONSAIMAX</summary>
        public const string __KONSAIMAX = "KONSAIMAX";
        
        /// <summary>SAILE</summary>
        public const string __SAILE = "SAILE";
        
        /// <summary>EAILE</summary>
        public const string __EAILE = "EAILE";
        
        /// <summary>SBANK</summary>
        public const string __SBANK = "SBANK";
        
        /// <summary>EBANK</summary>
        public const string __EBANK = "EBANK";
        
        /// <summary>SBAY</summary>
        public const string __SBAY = "SBAY";
        
        /// <summary>EBAY</summary>
        public const string __EBAY = "EBAY";
        
        /// <summary>SLEVEL</summary>
        public const string __SLEVEL = "SLEVEL";
        
        /// <summary>ELEVEL</summary>
        public const string __ELEVEL = "ELEVEL";
        
        /// <summary>SAINYUSYUBETU</summary>
        public const string __SAINYUSYUBETU = "SAINYUSYUBETU";
        
        /// <summary>SOKOSYUBETU</summary>
        public const string __SOKOSYUBETU = "SOKOSYUBETU";
        
        /// <summary>STZAIKOKBN</summary>
        public const string __STZAIKOKBN = "STZAIKOKBN";
        
        /// <summary>SYSTEMKBN</summary>
        public const string __SYSTEMKBN = "SYSTEMKBN";
        
        /// <summary>HIRATYPE</summary>
        public const string __HIRATYPE = "HIRATYPE";
        
        /// <summary>AILEORDER2</summary>
        public const string __AILEORDER2 = "AILEORDER2";
        
        /// <summary>AILEORDER3</summary>
        public const string __AILEORDER3 = "AILEORDER3";
        
        private string m_SOKOKBN;
        
        private string m_SOKONAME;
        
        private System.Decimal m_AILEORDER;
        
        private System.Decimal m_KONSAIMAX;
        
        private System.Decimal m_SAILE;
        
        private System.Decimal m_EAILE;
        
        private System.Decimal m_SBANK;
        
        private System.Decimal m_EBANK;
        
        private System.Decimal m_SBAY;
        
        private System.Decimal m_EBAY;
        
        private System.Decimal m_SLEVEL;
        
        private System.Decimal m_ELEVEL;
        
        private string m_SAINYUSYUBETU;
        
        private string m_SOKOSYUBETU;
        
        private string m_STZAIKOKBN;
        
        private string m_SYSTEMKBN;
        
        private string m_HIRATYPE;
        
        private System.Decimal m_AILEORDER2;
        
        private System.Decimal m_AILEORDER3;
        
        /// <summary>构造函数</summary>
        public FNSOKOEntity()
        {
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
        
        /// <summary>属性SOKONAME </summary>
        public string SOKONAME
        {
            get
            {
                return this.m_SOKONAME;
            }
            set
            {
                this.m_SOKONAME = value;
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
        
        /// <summary>属性KONSAIMAX </summary>
        public System.Decimal KONSAIMAX
        {
            get
            {
                return this.m_KONSAIMAX;
            }
            set
            {
                this.m_KONSAIMAX = value;
            }
        }
        
        /// <summary>属性SAILE </summary>
        public System.Decimal SAILE
        {
            get
            {
                return this.m_SAILE;
            }
            set
            {
                this.m_SAILE = value;
            }
        }
        
        /// <summary>属性EAILE </summary>
        public System.Decimal EAILE
        {
            get
            {
                return this.m_EAILE;
            }
            set
            {
                this.m_EAILE = value;
            }
        }
        
        /// <summary>属性SBANK </summary>
        public System.Decimal SBANK
        {
            get
            {
                return this.m_SBANK;
            }
            set
            {
                this.m_SBANK = value;
            }
        }
        
        /// <summary>属性EBANK </summary>
        public System.Decimal EBANK
        {
            get
            {
                return this.m_EBANK;
            }
            set
            {
                this.m_EBANK = value;
            }
        }
        
        /// <summary>属性SBAY </summary>
        public System.Decimal SBAY
        {
            get
            {
                return this.m_SBAY;
            }
            set
            {
                this.m_SBAY = value;
            }
        }
        
        /// <summary>属性EBAY </summary>
        public System.Decimal EBAY
        {
            get
            {
                return this.m_EBAY;
            }
            set
            {
                this.m_EBAY = value;
            }
        }
        
        /// <summary>属性SLEVEL </summary>
        public System.Decimal SLEVEL
        {
            get
            {
                return this.m_SLEVEL;
            }
            set
            {
                this.m_SLEVEL = value;
            }
        }
        
        /// <summary>属性ELEVEL </summary>
        public System.Decimal ELEVEL
        {
            get
            {
                return this.m_ELEVEL;
            }
            set
            {
                this.m_ELEVEL = value;
            }
        }
        
        /// <summary>属性SAINYUSYUBETU </summary>
        public string SAINYUSYUBETU
        {
            get
            {
                return this.m_SAINYUSYUBETU;
            }
            set
            {
                this.m_SAINYUSYUBETU = value;
            }
        }
        
        /// <summary>属性SOKOSYUBETU </summary>
        public string SOKOSYUBETU
        {
            get
            {
                return this.m_SOKOSYUBETU;
            }
            set
            {
                this.m_SOKOSYUBETU = value;
            }
        }
        
        /// <summary>属性STZAIKOKBN </summary>
        public string STZAIKOKBN
        {
            get
            {
                return this.m_STZAIKOKBN;
            }
            set
            {
                this.m_STZAIKOKBN = value;
            }
        }
        
        /// <summary>属性SYSTEMKBN </summary>
        public string SYSTEMKBN
        {
            get
            {
                return this.m_SYSTEMKBN;
            }
            set
            {
                this.m_SYSTEMKBN = value;
            }
        }
        
        /// <summary>属性HIRATYPE </summary>
        public string HIRATYPE
        {
            get
            {
                return this.m_HIRATYPE;
            }
            set
            {
                this.m_HIRATYPE = value;
            }
        }
        
        /// <summary>属性AILEORDER2 </summary>
        public System.Decimal AILEORDER2
        {
            get
            {
                return this.m_AILEORDER2;
            }
            set
            {
                this.m_AILEORDER2 = value;
            }
        }
        
        /// <summary>属性AILEORDER3 </summary>
        public System.Decimal AILEORDER3
        {
            get
            {
                return this.m_AILEORDER3;
            }
            set
            {
                this.m_AILEORDER3 = value;
            }
        }
    }
    
    /// FNSOKOEntity执行类
    public abstract class FNSOKOEntityAction
    {
        
        private FNSOKOEntityAction()
        {
        }
        
        public static void Save(FNSOKOEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNSOKOEntity RetrieveAFNSOKOEntity()
        {
            FNSOKOEntity obj=new FNSOKOEntity();
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
        public static EntityContainer RetrieveFNSOKOEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNSOKOEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNSOKOEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNSOKOEntity));
            return rc.AsDataTable();
        }
    }
}
