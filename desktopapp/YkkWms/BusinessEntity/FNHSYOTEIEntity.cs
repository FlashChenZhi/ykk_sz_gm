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
    public class FNHSYOTEIEntity : EntityObject
    {
        
        /// <summary>SYUHOMEN</summary>
        public const string __SYUHOMEN = "SYUHOMEN";
        
        /// <summary>SEISANNO</summary>
        public const string __SEISANNO = "SEISANNO";
        
        /// <summary>LINENO</summary>
        public const string __LINENO = "LINENO";
        
        /// <summary>YOKINO</summary>
        public const string __YOKINO = "YOKINO";
        
        /// <summary>TORIFLG</summary>
        public const string __TORIFLG = "TORIFLG";
        
        /// <summary>ERRCODE</summary>
        public const string __ERRCODE = "ERRCODE";
        
        /// <summary>SAKUSEIHIJI</summary>
        public const string __SAKUSEIHIJI = "SAKUSEIHIJI";
        
        /// <summary>SCHNO</summary>
        public const string __SCHNO = "SCHNO";
        
        /// <summary>MENTEKBN</summary>
        public const string __MENTEKBN = "MENTEKBN";
        
        /// <summary>CHUKEY</summary>
        public const string __CHUKEY = "CHUKEY";
        
        /// <summary>YUSENKBN</summary>
        public const string __YUSENKBN = "YUSENKBN";
        
        /// <summary>SYUSAKI</summary>
        public const string __SYUSAKI = "SYUSAKI";
        
        /// <summary>SYOTEIBI</summary>
        public const string __SYOTEIBI = "SYOTEIBI";
        
        /// <summary>BACHINO</summary>
        public const string __BACHINO = "BACHINO";
        
        /// <summary>SYUDENNO</summary>
        public const string __SYUDENNO = "SYUDENNO";
        
        /// <summary>SGYONO</summary>
        public const string __SGYONO = "SGYONO";
        
        /// <summary>ZAIKEY</summary>
        public const string __ZAIKEY = "ZAIKEY";
        
        /// <summary>LOTNO</summary>
        public const string __LOTNO = "LOTNO";
        
        /// <summary>LOCATNO</summary>
        public const string __LOCATNO = "LOCATNO";
        
        /// <summary>SYUKASU</summary>
        public const string __SYUKASU = "SYUKASU";
        
        /// <summary>KAKUTEISU</summary>
        public const string __KAKUTEISU = "KAKUTEISU";
        
        /// <summary>JISEKISU</summary>
        public const string __JISEKISU = "JISEKISU";
        
        /// <summary>SYORIFLG</summary>
        public const string __SYORIFLG = "SYORIFLG";
        
        /// <summary>SERIALNO</summary>
        public const string __SERIALNO = "SERIALNO";
        
        /// <summary>TERMNO</summary>
        public const string __TERMNO = "TERMNO";
        
        /// <summary>SYUKEY</summary>
        public const string __SYUKEY = "SYUKEY";
        
        private string m_SYUHOMEN;
        
        private string m_SEISANNO;
        
        private string m_LINENO;
        
        private string m_YOKINO;
        
        private string m_TORIFLG;
        
        private string m_ERRCODE;
        
        private string m_SAKUSEIHIJI;
        
        private string m_SCHNO;
        
        private string m_MENTEKBN;
        
        private string m_CHUKEY;
        
        private string m_YUSENKBN;
        
        private string m_SYUSAKI;
        
        private string m_SYOTEIBI;
        
        private string m_BACHINO;
        
        private string m_SYUDENNO;
        
        private System.Decimal m_SGYONO;
        
        private string m_ZAIKEY;
        
        private string m_LOTNO;
        
        private string m_LOCATNO;
        
        private System.Decimal m_SYUKASU;
        
        private System.Decimal m_KAKUTEISU;
        
        private System.Decimal m_JISEKISU;
        
        private string m_SYORIFLG;
        
        private System.Decimal m_SERIALNO;
        
        private System.Decimal m_TERMNO;
        
        private string m_SYUKEY;
        
        /// <summary>构造函数</summary>
        public FNHSYOTEIEntity()
        {
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
        
        /// <summary>属性TORIFLG </summary>
        public string TORIFLG
        {
            get
            {
                return this.m_TORIFLG;
            }
            set
            {
                this.m_TORIFLG = value;
            }
        }
        
        /// <summary>属性ERRCODE </summary>
        public string ERRCODE
        {
            get
            {
                return this.m_ERRCODE;
            }
            set
            {
                this.m_ERRCODE = value;
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
        
        /// <summary>属性MENTEKBN </summary>
        public string MENTEKBN
        {
            get
            {
                return this.m_MENTEKBN;
            }
            set
            {
                this.m_MENTEKBN = value;
            }
        }
        
        /// <summary>属性CHUKEY </summary>
        public string CHUKEY
        {
            get
            {
                return this.m_CHUKEY;
            }
            set
            {
                this.m_CHUKEY = value;
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
        
        /// <summary>属性SYUSAKI </summary>
        public string SYUSAKI
        {
            get
            {
                return this.m_SYUSAKI;
            }
            set
            {
                this.m_SYUSAKI = value;
            }
        }
        
        /// <summary>属性SYOTEIBI </summary>
        public string SYOTEIBI
        {
            get
            {
                return this.m_SYOTEIBI;
            }
            set
            {
                this.m_SYOTEIBI = value;
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
        
        /// <summary>属性SYUDENNO </summary>
        public string SYUDENNO
        {
            get
            {
                return this.m_SYUDENNO;
            }
            set
            {
                this.m_SYUDENNO = value;
            }
        }
        
        /// <summary>属性SGYONO </summary>
        public System.Decimal SGYONO
        {
            get
            {
                return this.m_SGYONO;
            }
            set
            {
                this.m_SGYONO = value;
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
        
        /// <summary>属性SYUKASU </summary>
        public System.Decimal SYUKASU
        {
            get
            {
                return this.m_SYUKASU;
            }
            set
            {
                this.m_SYUKASU = value;
            }
        }
        
        /// <summary>属性KAKUTEISU </summary>
        public System.Decimal KAKUTEISU
        {
            get
            {
                return this.m_KAKUTEISU;
            }
            set
            {
                this.m_KAKUTEISU = value;
            }
        }
        
        /// <summary>属性JISEKISU </summary>
        public System.Decimal JISEKISU
        {
            get
            {
                return this.m_JISEKISU;
            }
            set
            {
                this.m_JISEKISU = value;
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
        
        /// <summary>属性SERIALNO </summary>
        public System.Decimal SERIALNO
        {
            get
            {
                return this.m_SERIALNO;
            }
            set
            {
                this.m_SERIALNO = value;
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
    }
    
    /// FNHSYOTEIEntity执行类
    public abstract class FNHSYOTEIEntityAction
    {
        
        private FNHSYOTEIEntityAction()
        {
        }
        
        public static void Save(FNHSYOTEIEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNHSYOTEIEntity RetrieveAFNHSYOTEIEntity()
        {
            FNHSYOTEIEntity obj=new FNHSYOTEIEntity();
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
        public static EntityContainer RetrieveFNHSYOTEIEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNHSYOTEIEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNHSYOTEIEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNHSYOTEIEntity));
            return rc.AsDataTable();
        }
    }
}
