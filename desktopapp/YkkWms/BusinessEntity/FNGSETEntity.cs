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
    public class FNGSETEntity : EntityObject
    {
        
        /// <summary>KENSAKBN</summary>
        public const string __KENSAKBN = "KENSAKBN";
        
        /// <summary>TSUMISU</summary>
        public const string __TSUMISU = "TSUMISU";
        
        /// <summary>SYORIFLG</summary>
        public const string __SYORIFLG = "SYORIFLG";
        
        /// <summary>SCHSEQNO</summary>
        public const string __SCHSEQNO = "SCHSEQNO";
        
        /// <summary>BACHINO</summary>
        public const string __BACHINO = "BACHINO";
        
        /// <summary>NYUSYUSAKI</summary>
        public const string __NYUSYUSAKI = "NYUSYUSAKI";
        
        /// <summary>NYOSYOTEIBI</summary>
        public const string __NYOSYOTEIBI = "NYOSYOTEIBI";
        
        /// <summary>NYUSYUDENNO</summary>
        public const string __NYUSYUDENNO = "NYUSYUDENNO";
        
        /// <summary>NSGYONO</summary>
        public const string __NSGYONO = "NSGYONO";
        
        /// <summary>SYUHOMEN</summary>
        public const string __SYUHOMEN = "SYUHOMEN";
        
        /// <summary>SEISANNO</summary>
        public const string __SEISANNO = "SEISANNO";
        
        /// <summary>LINENO</summary>
        public const string __LINENO = "LINENO";
        
        /// <summary>YOKINO</summary>
        public const string __YOKINO = "YOKINO";
        
        /// <summary>NYUKOHI</summary>
        public const string __NYUKOHI = "NYUKOHI";
        
        /// <summary>NYUKOJI</summary>
        public const string __NYUKOJI = "NYUKOJI";
        
        /// <summary>SCHNO</summary>
        public const string __SCHNO = "SCHNO";
        
        /// <summary>SAKUSEIHIJI</summary>
        public const string __SAKUSEIHIJI = "SAKUSEIHIJI";
        
        /// <summary>SOKOKBN</summary>
        public const string __SOKOKBN = "SOKOKBN";
        
        /// <summary>TERMNO</summary>
        public const string __TERMNO = "TERMNO";
        
        /// <summary>SAGYOBA</summary>
        public const string __SAGYOBA = "SAGYOBA";
        
        /// <summary>SAGYOKBN</summary>
        public const string __SAGYOKBN = "SAGYOKBN";
        
        /// <summary>NYUSYUKBN</summary>
        public const string __NYUSYUKBN = "NYUSYUKBN";
        
        /// <summary>FRCHUKEY</summary>
        public const string __FRCHUKEY = "FRCHUKEY";
        
        /// <summary>TOCHUKEY</summary>
        public const string __TOCHUKEY = "TOCHUKEY";
        
        /// <summary>SYUKEY</summary>
        public const string __SYUKEY = "SYUKEY";
        
        /// <summary>ZAIKEY</summary>
        public const string __ZAIKEY = "ZAIKEY";
        
        /// <summary>LOTNO</summary>
        public const string __LOTNO = "LOTNO";
        
        /// <summary>NYUSYUSU</summary>
        public const string __NYUSYUSU = "NYUSYUSU";
        
        /// <summary>LOCATNO</summary>
        public const string __LOCATNO = "LOCATNO";
        
        /// <summary>HARDZONE</summary>
        public const string __HARDZONE = "HARDZONE";
        
        /// <summary>SOFTZONE</summary>
        public const string __SOFTZONE = "SOFTZONE";
        
        /// <summary>MOTOSTNO</summary>
        public const string __MOTOSTNO = "MOTOSTNO";
        
        /// <summary>SAKISTNO</summary>
        public const string __SAKISTNO = "SAKISTNO";
        
        /// <summary>SAGYONO</summary>
        public const string __SAGYONO = "SAGYONO";
        
        /// <summary>YUSENKBN</summary>
        public const string __YUSENKBN = "YUSENKBN";
        
        /// <summary>SAINYUKBN</summary>
        public const string __SAINYUKBN = "SAINYUKBN";
        
        /// <summary>TSUMIKBN</summary>
        public const string __TSUMIKBN = "TSUMIKBN";
        
        /// <summary>SYSTEMID</summary>
        public const string __SYSTEMID = "SYSTEMID";
        
        /// <summary>RETRIEVAL_PLANKEY</summary>
        public const string __RETRIEVAL_PLANKEY = "RETRIEVAL_PLANKEY";
        
        /// <summary>ZAIKOSU</summary>
        public const string __ZAIKOSU = "ZAIKOSU";
        
        /// <summary>SKANOSU</summary>
        public const string __SKANOSU = "SKANOSU";
        
        /// <summary>UNIT_WEIGHT</summary>
        public const string __UNIT_WEIGHT = "UNIT_WEIGHT";
        
        /// <summary>MEMO</summary>
        public const string __MEMO = "MEMO";
        
        /// <summary>MEASURE_WEIGHT</summary>
        public const string __MEASURE_WEIGHT = "MEASURE_WEIGHT";
        
        /// <summary>ENDSTNO</summary>
        public const string __ENDSTNO = "ENDSTNO";
        
        /// <summary>BARCODE</summary>
        public const string __BARCODE = "BARCODE";
        
        /// <summary>MCKEY</summary>
        public const string __MCKEY = "MCKEY";
        
        /// <summary>USERID</summary>
        public const string __USERID = "USERID";
        
        /// <summary>USERNAME</summary>
        public const string __USERNAME = "USERNAME";
        
        /// <summary>BUCKET_NO</summary>
        public const string __BUCKET_NO = "BUCKET_NO";
        
        /// <summary>PACKING_WEIGHT</summary>
        public const string __PACKING_WEIGHT = "PACKING_WEIGHT";
        
        /// <summary>TICKET_NO</summary>
        public const string __TICKET_NO = "TICKET_NO";
        
        /// <summary>COLOR_CODE</summary>
        public const string __COLOR_CODE = "COLOR_CODE";
        
        /// <summary>RETRIEVAL_STATION</summary>
        public const string __RETRIEVAL_STATION = "RETRIEVAL_STATION";
        
        /// <summary>FROM_TICKET_NO</summary>
        public const string __FROM_TICKET_NO = "FROM_TICKET_NO";
        
        /// <summary>TO_TICKET_NO</summary>
        public const string __TO_TICKET_NO = "TO_TICKET_NO";

        public const string __PRINTER_NO = "PRINTER_NO";

        public const string __BAG_FLAG = "BAG_FLAG";
        
        private string m_KENSAKBN;
        
        private System.Decimal m_TSUMISU;
        
        private string m_SYORIFLG;
        
        private System.Decimal m_SCHSEQNO;
        
        private string m_BACHINO;
        
        private string m_NYUSYUSAKI;
        
        private string m_NYOSYOTEIBI;
        
        private string m_NYUSYUDENNO;
        
        private System.Decimal m_NSGYONO;
        
        private string m_SYUHOMEN;
        
        private string m_SEISANNO;
        
        private string m_LINENO;
        
        private string m_YOKINO;
        
        private string m_NYUKOHI;
        
        private string m_NYUKOJI;
        
        private string m_SCHNO;
        
        private string m_SAKUSEIHIJI;
        
        private string m_SOKOKBN;
        
        private System.Decimal m_TERMNO;
        
        private string m_SAGYOBA;
        
        private string m_SAGYOKBN;
        
        private string m_NYUSYUKBN;
        
        private string m_FRCHUKEY;
        
        private string m_TOCHUKEY;
        
        private string m_SYUKEY;
        
        private string m_ZAIKEY;
        
        private string m_LOTNO;
        
        private System.Decimal m_NYUSYUSU;
        
        private string m_LOCATNO;
        
        private string m_HARDZONE;
        
        private string m_SOFTZONE;
        
        private string m_MOTOSTNO;
        
        private string m_SAKISTNO;
        
        private string m_SAGYONO;
        
        private string m_YUSENKBN;
        
        private string m_SAINYUKBN;
        
        private string m_TSUMIKBN;
        
        private string m_SYSTEMID;
        
        private string m_RETRIEVAL_PLANKEY;
        
        private System.Decimal m_ZAIKOSU;
        
        private System.Decimal m_SKANOSU;
        
        private System.Decimal m_UNIT_WEIGHT;
        
        private string m_MEMO;
        
        private System.Decimal m_MEASURE_WEIGHT;
        
        private string m_ENDSTNO;
        
        private string m_BARCODE;
        
        private string m_MCKEY;
        
        private string m_USERID;
        
        private string m_USERNAME;
        
        private string m_BUCKET_NO;
        
        private System.Decimal m_PACKING_WEIGHT;
        
        private string m_TICKET_NO;
        
        private string m_COLOR_CODE;
        
        private string m_RETRIEVAL_STATION;
        
        private string m_FROM_TICKET_NO;
        
        private string m_TO_TICKET_NO;

        private System.Decimal m_PRINTER_NO;

        private string m_BAG_FLAG;

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

        public System.Decimal PRINTER_NO
        {
            get { return m_PRINTER_NO; }
            set { m_PRINTER_NO = value; }
        }
        
        /// <summary>构造函数</summary>
        public FNGSETEntity()
        {
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
        
        /// <summary>属性TSUMISU </summary>
        public System.Decimal TSUMISU
        {
            get
            {
                return this.m_TSUMISU;
            }
            set
            {
                this.m_TSUMISU = value;
            }
        }
        
        /// <summary>属性SYORIFLG </summary>
        public string SYORIFLG
        {
            get
            {
                return this.m_SYORIFLG;
            }
            set
            {
                this.m_SYORIFLG = value;
            }
        }
        
        /// <summary>属性SCHSEQNO </summary>
        public System.Decimal SCHSEQNO
        {
            get
            {
                return this.m_SCHSEQNO;
            }
            set
            {
                this.m_SCHSEQNO = value;
            }
        }
        
        /// <summary>属性BACHINO </summary>
        public string BACHINO
        {
            get
            {
                return this.m_BACHINO;
            }
            set
            {
                this.m_BACHINO = value;
            }
        }
        
        /// <summary>属性NYUSYUSAKI </summary>
        public string NYUSYUSAKI
        {
            get
            {
                return this.m_NYUSYUSAKI;
            }
            set
            {
                this.m_NYUSYUSAKI = value;
            }
        }
        
        /// <summary>属性NYOSYOTEIBI </summary>
        public string NYOSYOTEIBI
        {
            get
            {
                return this.m_NYOSYOTEIBI;
            }
            set
            {
                this.m_NYOSYOTEIBI = value;
            }
        }
        
        /// <summary>属性NYUSYUDENNO </summary>
        public string NYUSYUDENNO
        {
            get
            {
                return this.m_NYUSYUDENNO;
            }
            set
            {
                this.m_NYUSYUDENNO = value;
            }
        }
        
        /// <summary>属性NSGYONO </summary>
        public System.Decimal NSGYONO
        {
            get
            {
                return this.m_NSGYONO;
            }
            set
            {
                this.m_NSGYONO = value;
            }
        }
        
        /// <summary>属性SYUHOMEN </summary>
        public string SYUHOMEN
        {
            get
            {
                return this.m_SYUHOMEN;
            }
            set
            {
                this.m_SYUHOMEN = value;
            }
        }
        
        /// <summary>属性SEISANNO </summary>
        public string SEISANNO
        {
            get
            {
                return this.m_SEISANNO;
            }
            set
            {
                this.m_SEISANNO = value;
            }
        }
        
        /// <summary>属性LINENO </summary>
        public string LINENO
        {
            get
            {
                return this.m_LINENO;
            }
            set
            {
                this.m_LINENO = value;
            }
        }
        
        /// <summary>属性YOKINO </summary>
        public string YOKINO
        {
            get
            {
                return this.m_YOKINO;
            }
            set
            {
                this.m_YOKINO = value;
            }
        }
        
        /// <summary>属性NYUKOHI </summary>
        public string NYUKOHI
        {
            get
            {
                return this.m_NYUKOHI;
            }
            set
            {
                this.m_NYUKOHI = value;
            }
        }
        
        /// <summary>属性NYUKOJI </summary>
        public string NYUKOJI
        {
            get
            {
                return this.m_NYUKOJI;
            }
            set
            {
                this.m_NYUKOJI = value;
            }
        }
        
        /// <summary>属性SCHNO </summary>
        public string SCHNO
        {
            get
            {
                return this.m_SCHNO;
            }
            set
            {
                this.m_SCHNO = value;
            }
        }
        
        /// <summary>属性SAKUSEIHIJI </summary>
        public string SAKUSEIHIJI
        {
            get
            {
                return this.m_SAKUSEIHIJI;
            }
            set
            {
                this.m_SAKUSEIHIJI = value;
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
        
        /// <summary>属性TERMNO </summary>
        public System.Decimal TERMNO
        {
            get
            {
                return this.m_TERMNO;
            }
            set
            {
                this.m_TERMNO = value;
            }
        }
        
        /// <summary>属性SAGYOBA </summary>
        public string SAGYOBA
        {
            get
            {
                return this.m_SAGYOBA;
            }
            set
            {
                this.m_SAGYOBA = value;
            }
        }
        
        /// <summary>属性SAGYOKBN </summary>
        public string SAGYOKBN
        {
            get
            {
                return this.m_SAGYOKBN;
            }
            set
            {
                this.m_SAGYOKBN = value;
            }
        }
        
        /// <summary>属性NYUSYUKBN </summary>
        public string NYUSYUKBN
        {
            get
            {
                return this.m_NYUSYUKBN;
            }
            set
            {
                this.m_NYUSYUKBN = value;
            }
        }
        
        /// <summary>属性FRCHUKEY </summary>
        public string FRCHUKEY
        {
            get
            {
                return this.m_FRCHUKEY;
            }
            set
            {
                this.m_FRCHUKEY = value;
            }
        }
        
        /// <summary>属性TOCHUKEY </summary>
        public string TOCHUKEY
        {
            get
            {
                return this.m_TOCHUKEY;
            }
            set
            {
                this.m_TOCHUKEY = value;
            }
        }
        
        /// <summary>属性SYUKEY </summary>
        public string SYUKEY
        {
            get
            {
                return this.m_SYUKEY;
            }
            set
            {
                this.m_SYUKEY = value;
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
        
        /// <summary>属性NYUSYUSU </summary>
        public System.Decimal NYUSYUSU
        {
            get
            {
                return this.m_NYUSYUSU;
            }
            set
            {
                this.m_NYUSYUSU = value;
            }
        }
        
        /// <summary>属性LOCATNO </summary>
        public string LOCATNO
        {
            get
            {
                return this.m_LOCATNO;
            }
            set
            {
                this.m_LOCATNO = value;
            }
        }
        
        /// <summary>属性HARDZONE </summary>
        public string HARDZONE
        {
            get
            {
                return this.m_HARDZONE;
            }
            set
            {
                this.m_HARDZONE = value;
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
        
        /// <summary>属性MOTOSTNO </summary>
        public string MOTOSTNO
        {
            get
            {
                return this.m_MOTOSTNO;
            }
            set
            {
                this.m_MOTOSTNO = value;
            }
        }
        
        /// <summary>属性SAKISTNO </summary>
        public string SAKISTNO
        {
            get
            {
                return this.m_SAKISTNO;
            }
            set
            {
                this.m_SAKISTNO = value;
            }
        }
        
        /// <summary>属性SAGYONO </summary>
        public string SAGYONO
        {
            get
            {
                return this.m_SAGYONO;
            }
            set
            {
                this.m_SAGYONO = value;
            }
        }
        
        /// <summary>属性YUSENKBN </summary>
        public string YUSENKBN
        {
            get
            {
                return this.m_YUSENKBN;
            }
            set
            {
                this.m_YUSENKBN = value;
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
        
        /// <summary>属性UNIT_WEIGHT </summary>
        public System.Decimal UNIT_WEIGHT
        {
            get
            {
                return this.m_UNIT_WEIGHT;
            }
            set
            {
                this.m_UNIT_WEIGHT = value;
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
        
        /// <summary>属性MEASURE_WEIGHT </summary>
        public System.Decimal MEASURE_WEIGHT
        {
            get
            {
                return this.m_MEASURE_WEIGHT;
            }
            set
            {
                this.m_MEASURE_WEIGHT = value;
            }
        }
        
        /// <summary>属性ENDSTNO </summary>
        public string ENDSTNO
        {
            get
            {
                return this.m_ENDSTNO;
            }
            set
            {
                this.m_ENDSTNO = value;
            }
        }
        
        /// <summary>属性BARCODE </summary>
        public string BARCODE
        {
            get
            {
                return this.m_BARCODE;
            }
            set
            {
                this.m_BARCODE = value;
            }
        }
        
        /// <summary>属性MCKEY </summary>
        public string MCKEY
        {
            get
            {
                return this.m_MCKEY;
            }
            set
            {
                this.m_MCKEY = value;
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
        
        /// <summary>属性PACKING_WEIGHT </summary>
        public System.Decimal PACKING_WEIGHT
        {
            get
            {
                return this.m_PACKING_WEIGHT;
            }
            set
            {
                this.m_PACKING_WEIGHT = value;
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
        
        /// <summary>属性RETRIEVAL_STATION </summary>
        public string RETRIEVAL_STATION
        {
            get
            {
                return this.m_RETRIEVAL_STATION;
            }
            set
            {
                this.m_RETRIEVAL_STATION = value;
            }
        }
        
        /// <summary>属性FROM_TICKET_NO </summary>
        public string FROM_TICKET_NO
        {
            get
            {
                return this.m_FROM_TICKET_NO;
            }
            set
            {
                this.m_FROM_TICKET_NO = value;
            }
        }
        
        /// <summary>属性TO_TICKET_NO </summary>
        public string TO_TICKET_NO
        {
            get
            {
                return this.m_TO_TICKET_NO;
            }
            set
            {
                this.m_TO_TICKET_NO = value;
            }
        }
    }
    
    /// FNGSETEntity执行类
    public abstract class FNGSETEntityAction
    {
        
        private FNGSETEntityAction()
        {
        }
        
        public static void Save(FNGSETEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNGSETEntity RetrieveAFNGSETEntity()
        {
            FNGSETEntity obj=new FNGSETEntity();
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
        public static EntityContainer RetrieveFNGSETEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNGSETEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNGSETEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNGSETEntity));
            return rc.AsDataTable();
        }
    }
}