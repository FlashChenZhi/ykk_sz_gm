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
    public class FNZAIKOEntity : EntityObject
    {
        
        /// <summary>TICKET_NO</summary>
        public const string __TICKET_NO = "TICKET_NO";
        
        /// <summary>ZAIKEY</summary>
        public const string __ZAIKEY = "ZAIKEY";
        
        /// <summary>COLOR_CODE</summary>
        public const string __COLOR_CODE = "COLOR_CODE";
        
        /// <summary>DEPOT_CODE</summary>
        public const string __DEPOT_CODE = "DEPOT_CODE";
        
        /// <summary>LOTNO</summary>
        public const string __LOTNO = "LOTNO";
        
        /// <summary>ZAIKOSU</summary>
        public const string __ZAIKOSU = "ZAIKOSU";
        
        /// <summary>NYOTEISU</summary>
        public const string __NYOTEISU = "NYOTEISU";
        
        /// <summary>SKANOSU</summary>
        public const string __SKANOSU = "SKANOSU";
        
        /// <summary>SAINYUSU</summary>
        public const string __SAINYUSU = "SAINYUSU";
        
        /// <summary>NYUKOHIJI</summary>
        public const string __NYUKOHIJI = "NYUKOHIJI";
        
        /// <summary>KAKUNINHIJI</summary>
        public const string __KAKUNINHIJI = "KAKUNINHIJI";
        
        /// <summary>KOSHINHIJI</summary>
        public const string __KOSHINHIJI = "KOSHINHIJI";
        
        /// <summary>SAINYUKBN</summary>
        public const string __SAINYUKBN = "SAINYUKBN";
        
        /// <summary>TSUMIKBN</summary>
        public const string __TSUMIKBN = "TSUMIKBN";
        
        /// <summary>KENSAKBN</summary>
        public const string __KENSAKBN = "KENSAKBN";
        
        /// <summary>SYSTEMID</summary>
        public const string __SYSTEMID = "SYSTEMID";
        
        /// <summary>PLAN_QTY</summary>
        public const string __PLAN_QTY = "PLAN_QTY";
        
        /// <summary>PLAN_WEIGHT</summary>
        public const string __PLAN_WEIGHT = "PLAN_WEIGHT";
        
        /// <summary>MOVE_FLAG</summary>
        public const string __MOVE_FLAG = "MOVE_FLAG";
        
        /// <summary>MADE_SECTION</summary>
        public const string __MADE_SECTION = "MADE_SECTION";
        
        /// <summary>MADE_LINE</summary>
        public const string __MADE_LINE = "MADE_LINE";
        
        /// <summary>REAL_UNIT_WEIGHT</summary>
        public const string __REAL_UNIT_WEIGHT = "REAL_UNIT_WEIGHT";
        
        /// <summary>WEIGHT_REPORT_COMPLETE_FLAG</summary>
        public const string __WEIGHT_REPORT_COMPLETE_FLAG = "WEIGHT_REPORT_COMPLETE_FLAG";
        
        /// <summary>STORAGE_PLACE_FLAG</summary>
        public const string __STORAGE_PLACE_FLAG = "STORAGE_PLACE_FLAG";
        
        /// <summary>BUCKET_NO</summary>
        public const string __BUCKET_NO = "BUCKET_NO";
        
        /// <summary>MEMO</summary>
        public const string __MEMO = "MEMO";
        
        /// <summary>MANAGE_ITEM_FLAG</summary>
        public const string __MANAGE_ITEM_FLAG = "MANAGE_ITEM_FLAG";
        
        /// <summary>RECEPTION_DATETIME</summary>
        public const string __RECEPTION_DATETIME = "RECEPTION_DATETIME";
        
        /// <summary>USERID</summary>
        public const string __USERID = "USERID";
        
        /// <summary>USERNAME</summary>
        public const string __USERNAME = "USERNAME";
        
        /// <summary>REMEASURE_FLAG</summary>
        public const string __REMEASURE_FLAG = "REMEASURE_FLAG";

        public const string __BAG_FLAG = "BAG_FLAG";
        
        private string m_TICKET_NO;
        
        private string m_ZAIKEY;
        
        private string m_COLOR_CODE;
        
        private string m_DEPOT_CODE;
        
        private string m_LOTNO;
        
        private System.Decimal m_ZAIKOSU;
        
        private System.Decimal m_NYOTEISU;
        
        private System.Decimal m_SKANOSU;
        
        private System.Decimal m_SAINYUSU;
        
        private string m_NYUKOHIJI;
        
        private string m_KAKUNINHIJI;
        
        private string m_KOSHINHIJI;
        
        private string m_SAINYUKBN;
        
        private string m_TSUMIKBN;
        
        private string m_KENSAKBN;
        
        private string m_SYSTEMID;
        
        private System.Decimal m_PLAN_QTY;
        
        private System.Decimal m_PLAN_WEIGHT;
        
        private string m_MOVE_FLAG;
        
        private string m_MADE_SECTION;
        
        private string m_MADE_LINE;
        
        private System.Decimal m_REAL_UNIT_WEIGHT;
        
        private string m_WEIGHT_REPORT_COMPLETE_FLAG;
        
        private string m_STORAGE_PLACE_FLAG;
        
        private string m_BUCKET_NO;
        
        private string m_MEMO;
        
        private string m_MANAGE_ITEM_FLAG;
        
        private string m_RECEPTION_DATETIME;
        
        private string m_USERID;
        
        private string m_USERNAME;
        
        private string m_REMEASURE_FLAG;

        private string m_BAG_FLAG;
        
        /// <summary>构造函数</summary>
        public FNZAIKOEntity()
        {
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
        
        /// <summary>属性LOTNO </summary>
        public string LOTNO
        {
            get
            {
                return this.m_LOTNO;
            }
            set
            {
                this.m_LOTNO = value;
            }
        }
        
        /// <summary>属性ZAIKOSU </summary>
        public System.Decimal ZAIKOSU
        {
            get
            {
                return this.m_ZAIKOSU;
            }
            set
            {
                this.m_ZAIKOSU = value;
            }
        }
        
        /// <summary>属性NYOTEISU </summary>
        public System.Decimal NYOTEISU
        {
            get
            {
                return this.m_NYOTEISU;
            }
            set
            {
                this.m_NYOTEISU = value;
            }
        }
        
        /// <summary>属性SKANOSU </summary>
        public System.Decimal SKANOSU
        {
            get
            {
                return this.m_SKANOSU;
            }
            set
            {
                this.m_SKANOSU = value;
            }
        }
        
        /// <summary>属性SAINYUSU </summary>
        public System.Decimal SAINYUSU
        {
            get
            {
                return this.m_SAINYUSU;
            }
            set
            {
                this.m_SAINYUSU = value;
            }
        }
        
        /// <summary>属性NYUKOHIJI </summary>
        public string NYUKOHIJI
        {
            get
            {
                return this.m_NYUKOHIJI;
            }
            set
            {
                this.m_NYUKOHIJI = value;
            }
        }
        
        /// <summary>属性KAKUNINHIJI </summary>
        public string KAKUNINHIJI
        {
            get
            {
                return this.m_KAKUNINHIJI;
            }
            set
            {
                this.m_KAKUNINHIJI = value;
            }
        }
        
        /// <summary>属性KOSHINHIJI </summary>
        public string KOSHINHIJI
        {
            get
            {
                return this.m_KOSHINHIJI;
            }
            set
            {
                this.m_KOSHINHIJI = value;
            }
        }
        
        /// <summary>属性SAINYUKBN </summary>
        public string SAINYUKBN
        {
            get
            {
                return this.m_SAINYUKBN;
            }
            set
            {
                this.m_SAINYUKBN = value;
            }
        }
        
        /// <summary>属性TSUMIKBN </summary>
        public string TSUMIKBN
        {
            get
            {
                return this.m_TSUMIKBN;
            }
            set
            {
                this.m_TSUMIKBN = value;
            }
        }
        
        /// <summary>属性KENSAKBN </summary>
        public string KENSAKBN
        {
            get
            {
                return this.m_KENSAKBN;
            }
            set
            {
                this.m_KENSAKBN = value;
            }
        }
        
        /// <summary>属性SYSTEMID </summary>
        public string SYSTEMID
        {
            get
            {
                return this.m_SYSTEMID;
            }
            set
            {
                this.m_SYSTEMID = value;
            }
        }
        
        /// <summary>属性PLAN_QTY </summary>
        public System.Decimal PLAN_QTY
        {
            get
            {
                return this.m_PLAN_QTY;
            }
            set
            {
                this.m_PLAN_QTY = value;
            }
        }
        
        /// <summary>属性PLAN_WEIGHT </summary>
        public System.Decimal PLAN_WEIGHT
        {
            get
            {
                return this.m_PLAN_WEIGHT;
            }
            set
            {
                this.m_PLAN_WEIGHT = value;
            }
        }
        
        /// <summary>属性MOVE_FLAG </summary>
        public string MOVE_FLAG
        {
            get
            {
                return this.m_MOVE_FLAG;
            }
            set
            {
                this.m_MOVE_FLAG = value;
            }
        }
        
        /// <summary>属性MADE_SECTION </summary>
        public string MADE_SECTION
        {
            get
            {
                return this.m_MADE_SECTION;
            }
            set
            {
                this.m_MADE_SECTION = value;
            }
        }
        
        /// <summary>属性MADE_LINE </summary>
        public string MADE_LINE
        {
            get
            {
                return this.m_MADE_LINE;
            }
            set
            {
                this.m_MADE_LINE = value;
            }
        }
        
        /// <summary>属性REAL_UNIT_WEIGHT </summary>
        public System.Decimal REAL_UNIT_WEIGHT
        {
            get
            {
                return this.m_REAL_UNIT_WEIGHT;
            }
            set
            {
                this.m_REAL_UNIT_WEIGHT = value;
            }
        }
        
        /// <summary>属性WEIGHT_REPORT_COMPLETE_FLAG </summary>
        public string WEIGHT_REPORT_COMPLETE_FLAG
        {
            get
            {
                return this.m_WEIGHT_REPORT_COMPLETE_FLAG;
            }
            set
            {
                this.m_WEIGHT_REPORT_COMPLETE_FLAG = value;
            }
        }
        
        /// <summary>属性STORAGE_PLACE_FLAG </summary>
        public string STORAGE_PLACE_FLAG
        {
            get
            {
                return this.m_STORAGE_PLACE_FLAG;
            }
            set
            {
                this.m_STORAGE_PLACE_FLAG = value;
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
        
        /// <summary>属性MEMO </summary>
        public string MEMO
        {
            get
            {
                return this.m_MEMO;
            }
            set
            {
                this.m_MEMO = value;
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
        
        /// <summary>属性RECEPTION_DATETIME </summary>
        public string RECEPTION_DATETIME
        {
            get
            {
                return this.m_RECEPTION_DATETIME;
            }
            set
            {
                this.m_RECEPTION_DATETIME = value;
            }
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
        
        /// <summary>属性REMEASURE_FLAG </summary>
        public string REMEASURE_FLAG
        {
            get
            {
                return this.m_REMEASURE_FLAG;
            }
            set
            {
                this.m_REMEASURE_FLAG = value;
            }
        }

        /// <summary>属性BAG_FLAG </summary>
        public string BAG_FLAG
        {
            get
            {
                return this.m_BAG_FLAG;
            }
            set
            {
                this.m_BAG_FLAG = value;
            }
        }
    }
    
    /// FNZAIKOEntity执行类
    public abstract class FNZAIKOEntityAction
    {
        
        private FNZAIKOEntityAction()
        {
        }
        
        public static void Save(FNZAIKOEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNZAIKOEntity RetrieveAFNZAIKOEntity()
        {
            FNZAIKOEntity obj=new FNZAIKOEntity();
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
        public static EntityContainer RetrieveFNZAIKOEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNZAIKOEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNZAIKOEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNZAIKOEntity));
            return rc.AsDataTable();
        }
    }
}
