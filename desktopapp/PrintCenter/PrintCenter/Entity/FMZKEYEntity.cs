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
//             Created Time： 2008-1-16 17:40:52
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
    public class FMZKEYEntity : EntityObject
    {
        
        /// <summary>ZAIKEY</summary>
        public const string __ZAIKEY = "ZAIKEY";
        
        /// <summary>ZKNAME1</summary>
        public const string __ZKNAME1 = "ZKNAME1";
        
        /// <summary>ZKKBN</summary>
        public const string __ZKKBN = "ZKKBN";
        
        /// <summary>SEKISAISU</summary>
        public const string __SEKISAISU = "SEKISAISU";
        
        /// <summary>YSHIKIRI</summary>
        public const string __YSHIKIRI = "YSHIKIRI";
        
        /// <summary>YSHUBETSU</summary>
        public const string __YSHUBETSU = "YSHUBETSU";
        
        /// <summary>SOTOSU</summary>
        public const string __SOTOSU = "SOTOSU";
        
        /// <summary>UCHISU</summary>
        public const string __UCHISU = "UCHISU";
        
        /// <summary>MENTEHIJI</summary>
        public const string __MENTEHIJI = "MENTEHIJI";
        
        /// <summary>JOGENSUJ</summary>
        public const string __JOGENSUJ = "JOGENSUJ";
        
        /// <summary>KAGENSUJ</summary>
        public const string __KAGENSUJ = "KAGENSUJ";
        
        /// <summary>SOFTZONE</summary>
        public const string __SOFTZONE = "SOFTZONE";
        
        /// <summary>ZKNAME2</summary>
        public const string __ZKNAME2 = "ZKNAME2";
        
        /// <summary>ZKNAME3</summary>
        public const string __ZKNAME3 = "ZKNAME3";
        
        /// <summary>MASTER_UNIT_WEIGHT</summary>
        public const string __MASTER_UNIT_WEIGHT = "MASTER_UNIT_WEIGHT";
        
        /// <summary>ITEM_RANK</summary>
        public const string __ITEM_RANK = "ITEM_RANK";
        
        /// <summary>LIMIT_QTY</summary>
        public const string __LIMIT_QTY = "LIMIT_QTY";
        
        /// <summary>MEASURE_FLAG</summary>
        public const string __MEASURE_FLAG = "MEASURE_FLAG";
        
        /// <summary>REMOVE_CONVENT_FLAG</summary>
        public const string __REMOVE_CONVENT_FLAG = "REMOVE_CONVENT_FLAG";
        
        /// <summary>MEASURE_QTY</summary>
        public const string __MEASURE_QTY = "MEASURE_QTY";
        
        /// <summary>MANAGE_ITEM_FLAG</summary>
        public const string __MANAGE_ITEM_FLAG = "MANAGE_ITEM_FLAG";
        
        private string m_ZAIKEY;
        
        private string m_ZKNAME1;
        
        private string m_ZKKBN;
        
        private System.Decimal m_SEKISAISU;
        
        private System.Decimal m_YSHIKIRI;
        
        private string m_YSHUBETSU;
        
        private System.Decimal m_SOTOSU;
        
        private System.Decimal m_UCHISU;
        
        private string m_MENTEHIJI;
        
        private System.Decimal m_JOGENSUJ;
        
        private System.Decimal m_KAGENSUJ;
        
        private string m_SOFTZONE;
        
        private string m_ZKNAME2;
        
        private string m_ZKNAME3;
        
        private System.Decimal m_MASTER_UNIT_WEIGHT;
        
        private System.Decimal m_ITEM_RANK;
        
        private System.Decimal m_LIMIT_QTY;
        
        private string m_MEASURE_FLAG;
        
        private string m_REMOVE_CONVENT_FLAG;
        
        private System.Decimal m_MEASURE_QTY;
        
        private string m_MANAGE_ITEM_FLAG;
        
        /// <summary>构造函数</summary>
        public FMZKEYEntity()
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
        
        /// <summary>属性ZKNAME1 </summary>
        public string ZKNAME1
        {
            get
            {
                return this.m_ZKNAME1;
            }
            set
            {
                this.m_ZKNAME1 = value;
            }
        }
        
        /// <summary>属性ZKKBN </summary>
        public string ZKKBN
        {
            get
            {
                return this.m_ZKKBN;
            }
            set
            {
                this.m_ZKKBN = value;
            }
        }
        
        /// <summary>属性SEKISAISU </summary>
        public System.Decimal SEKISAISU
        {
            get
            {
                return this.m_SEKISAISU;
            }
            set
            {
                this.m_SEKISAISU = value;
            }
        }
        
        /// <summary>属性YSHIKIRI </summary>
        public System.Decimal YSHIKIRI
        {
            get
            {
                return this.m_YSHIKIRI;
            }
            set
            {
                this.m_YSHIKIRI = value;
            }
        }
        
        /// <summary>属性YSHUBETSU </summary>
        public string YSHUBETSU
        {
            get
            {
                return this.m_YSHUBETSU;
            }
            set
            {
                this.m_YSHUBETSU = value;
            }
        }
        
        /// <summary>属性SOTOSU </summary>
        public System.Decimal SOTOSU
        {
            get
            {
                return this.m_SOTOSU;
            }
            set
            {
                this.m_SOTOSU = value;
            }
        }
        
        /// <summary>属性UCHISU </summary>
        public System.Decimal UCHISU
        {
            get
            {
                return this.m_UCHISU;
            }
            set
            {
                this.m_UCHISU = value;
            }
        }
        
        /// <summary>属性MENTEHIJI </summary>
        public string MENTEHIJI
        {
            get
            {
                return this.m_MENTEHIJI;
            }
            set
            {
                this.m_MENTEHIJI = value;
            }
        }
        
        /// <summary>属性JOGENSUJ </summary>
        public System.Decimal JOGENSUJ
        {
            get
            {
                return this.m_JOGENSUJ;
            }
            set
            {
                this.m_JOGENSUJ = value;
            }
        }
        
        /// <summary>属性KAGENSUJ </summary>
        public System.Decimal KAGENSUJ
        {
            get
            {
                return this.m_KAGENSUJ;
            }
            set
            {
                this.m_KAGENSUJ = value;
            }
        }
        
        /// <summary>属性SOFTZONE </summary>
        public string SOFTZONE
        {
            get
            {
                return this.m_SOFTZONE;
            }
            set
            {
                this.m_SOFTZONE = value;
            }
        }
        
        /// <summary>属性ZKNAME2 </summary>
        public string ZKNAME2
        {
            get
            {
                return this.m_ZKNAME2;
            }
            set
            {
                this.m_ZKNAME2 = value;
            }
        }
        
        /// <summary>属性ZKNAME3 </summary>
        public string ZKNAME3
        {
            get
            {
                return this.m_ZKNAME3;
            }
            set
            {
                this.m_ZKNAME3 = value;
            }
        }
        
        /// <summary>属性MASTER_UNIT_WEIGHT </summary>
        public System.Decimal MASTER_UNIT_WEIGHT
        {
            get
            {
                return this.m_MASTER_UNIT_WEIGHT;
            }
            set
            {
                this.m_MASTER_UNIT_WEIGHT = value;
            }
        }
        
        /// <summary>属性ITEM_RANK </summary>
        public System.Decimal ITEM_RANK
        {
            get
            {
                return this.m_ITEM_RANK;
            }
            set
            {
                this.m_ITEM_RANK = value;
            }
        }
        
        /// <summary>属性LIMIT_QTY </summary>
        public System.Decimal LIMIT_QTY
        {
            get
            {
                return this.m_LIMIT_QTY;
            }
            set
            {
                this.m_LIMIT_QTY = value;
            }
        }
        
        /// <summary>属性MEASURE_FLAG </summary>
        public string MEASURE_FLAG
        {
            get
            {
                return this.m_MEASURE_FLAG;
            }
            set
            {
                this.m_MEASURE_FLAG = value;
            }
        }
        
        /// <summary>属性REMOVE_CONVENT_FLAG </summary>
        public string REMOVE_CONVENT_FLAG
        {
            get
            {
                return this.m_REMOVE_CONVENT_FLAG;
            }
            set
            {
                this.m_REMOVE_CONVENT_FLAG = value;
            }
        }
        
        /// <summary>属性MEASURE_QTY </summary>
        public System.Decimal MEASURE_QTY
        {
            get
            {
                return this.m_MEASURE_QTY;
            }
            set
            {
                this.m_MEASURE_QTY = value;
            }
        }
        
        /// <summary>属性MANAGE_ITEM_FLAG </summary>
        public string MANAGE_ITEM_FLAG
        {
            get
            {
                return this.m_MANAGE_ITEM_FLAG;
            }
            set
            {
                this.m_MANAGE_ITEM_FLAG = value;
            }
        }
    }
    
    /// FMZKEYEntity执行类
    public abstract class FMZKEYEntityAction
    {
        
        private FMZKEYEntityAction()
        {
        }
        
        public static void Save(FMZKEYEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FMZKEYEntity RetrieveAFMZKEYEntity(string ZAIKEY, string MANAGE_ITEM_FLAG)
        {
            FMZKEYEntity obj=new FMZKEYEntity();
            obj.ZAIKEY=ZAIKEY;
            obj.MANAGE_ITEM_FLAG=MANAGE_ITEM_FLAG;
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
        public static EntityContainer RetrieveFMZKEYEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FMZKEYEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFMZKEYEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FMZKEYEntity));
            return rc.AsDataTable();
        }
    }
}
