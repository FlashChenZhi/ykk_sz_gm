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
    public class FNHANSOEntity : EntityObject
    {
        
        /// <summary>SEIGYOJOHO</summary>
        public const string __SEIGYOJOHO = "SEIGYOJOHO";
        
        /// <summary>HANSOBI</summary>
        public const string __HANSOBI = "HANSOBI";
        
        /// <summary>SAGYONO</summary>
        public const string __SAGYONO = "SAGYONO";
        
        /// <summary>BUCKET_NO</summary>
        public const string __BUCKET_NO = "BUCKET_NO";
        
        /// <summary>ENDSTNO</summary>
        public const string __ENDSTNO = "ENDSTNO";
        
        /// <summary>HJYOTAIFLG</summary>
        public const string __HJYOTAIFLG = "HJYOTAIFLG";
        
        /// <summary>SAGYOBA</summary>
        public const string __SAGYOBA = "SAGYOBA";
        
        /// <summary>YUSENKBN</summary>
        public const string __YUSENKBN = "YUSENKBN";
        
        /// <summary>SCHNO</summary>
        public const string __SCHNO = "SCHNO";
        
        /// <summary>SYSTEMID</summary>
        public const string __SYSTEMID = "SYSTEMID";
        
        /// <summary>SETKBN</summary>
        public const string __SETKBN = "SETKBN";
        
        /// <summary>SAINYUFLG</summary>
        public const string __SAINYUFLG = "SAINYUFLG";
        
        /// <summary>BCRDT</summary>
        public const string __BCRDT = "BCRDT";
        
        /// <summary>ERRCODE</summary>
        public const string __ERRCODE = "ERRCODE";
        
        /// <summary>SYUMOTOLOC</summary>
        public const string __SYUMOTOLOC = "SYUMOTOLOC";
        
        /// <summary>HANSOKEY</summary>
        public const string __HANSOKEY = "HANSOKEY";
        
        /// <summary>MCKEY</summary>
        public const string __MCKEY = "MCKEY";
        
        /// <summary>SOFTZONE</summary>
        public const string __SOFTZONE = "SOFTZONE";
        
        /// <summary>SAGYOKBN</summary>
        public const string __SAGYOKBN = "SAGYOKBN";
        
        /// <summary>MOTOSTNO</summary>
        public const string __MOTOSTNO = "MOTOSTNO";
        
        /// <summary>SAKISTNO</summary>
        public const string __SAKISTNO = "SAKISTNO";
        
        /// <summary>NYUSYUKBN</summary>
        public const string __NYUSYUKBN = "NYUSYUKBN";
        
        /// <summary>SIJISYOSAI</summary>
        public const string __SIJISYOSAI = "SIJISYOSAI";
        
        /// <summary>GROUPNO</summary>
        public const string __GROUPNO = "GROUPNO";
        
        /// <summary>SAKITANANO</summary>
        public const string __SAKITANANO = "SAKITANANO";
        
        /// <summary>STARTSTNO</summary>
        public const string __STARTSTNO = "STARTSTNO";
        
        /// <summary>CROSS_FLG</summary>
        public const string __CROSS_FLG = "CROSS_FLG";
        
        private string m_SEIGYOJOHO;
        
        private string m_HANSOBI;
        
        private string m_SAGYONO;
        
        private string m_BUCKET_NO;
        
        private string m_ENDSTNO;
        
        private string m_HJYOTAIFLG;
        
        private string m_SAGYOBA;
        
        private string m_YUSENKBN;
        
        private string m_SCHNO;
        
        private string m_SYSTEMID;
        
        private string m_SETKBN;
        
        private string m_SAINYUFLG;
        
        private string m_BCRDT;
        
        private string m_ERRCODE;
        
        private string m_SYUMOTOLOC;
        
        private string m_HANSOKEY;
        
        private string m_MCKEY;
        
        private string m_SOFTZONE;
        
        private string m_SAGYOKBN;
        
        private string m_MOTOSTNO;
        
        private string m_SAKISTNO;
        
        private string m_NYUSYUKBN;
        
        private string m_SIJISYOSAI;
        
        private string m_GROUPNO;
        
        private string m_SAKITANANO;
        
        private string m_STARTSTNO;
        
        private string m_CROSS_FLG;
        
        /// <summary>构造函数</summary>
        public FNHANSOEntity()
        {
        }
        
        /// <summary>属性SEIGYOJOHO </summary>
        public string SEIGYOJOHO
        {
            get
            {
                return this.m_SEIGYOJOHO;
            }
            set
            {
                this.m_SEIGYOJOHO = value;
            }
        }
        
        /// <summary>属性HANSOBI </summary>
        public string HANSOBI
        {
            get
            {
                return this.m_HANSOBI;
            }
            set
            {
                this.m_HANSOBI = value;
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
        
        /// <summary>属性HJYOTAIFLG </summary>
        public string HJYOTAIFLG
        {
            get
            {
                return this.m_HJYOTAIFLG;
            }
            set
            {
                this.m_HJYOTAIFLG = value;
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
        
        /// <summary>属性SETKBN </summary>
        public string SETKBN
        {
            get
            {
                return this.m_SETKBN;
            }
            set
            {
                this.m_SETKBN = value;
            }
        }
        
        /// <summary>属性SAINYUFLG </summary>
        public string SAINYUFLG
        {
            get
            {
                return this.m_SAINYUFLG;
            }
            set
            {
                this.m_SAINYUFLG = value;
            }
        }
        
        /// <summary>属性BCRDT </summary>
        public string BCRDT
        {
            get
            {
                return this.m_BCRDT;
            }
            set
            {
                this.m_BCRDT = value;
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
        
        /// <summary>属性SYUMOTOLOC </summary>
        public string SYUMOTOLOC
        {
            get
            {
                return this.m_SYUMOTOLOC;
            }
            set
            {
                this.m_SYUMOTOLOC = value;
            }
        }
        
        /// <summary>属性HANSOKEY </summary>
        public string HANSOKEY
        {
            get
            {
                return this.m_HANSOKEY;
            }
            set
            {
                this.m_HANSOKEY = value;
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
        
        /// <summary>属性SIJISYOSAI </summary>
        public string SIJISYOSAI
        {
            get
            {
                return this.m_SIJISYOSAI;
            }
            set
            {
                this.m_SIJISYOSAI = value;
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
        
        /// <summary>属性SAKITANANO </summary>
        public string SAKITANANO
        {
            get
            {
                return this.m_SAKITANANO;
            }
            set
            {
                this.m_SAKITANANO = value;
            }
        }
        
        /// <summary>属性STARTSTNO </summary>
        public string STARTSTNO
        {
            get
            {
                return this.m_STARTSTNO;
            }
            set
            {
                this.m_STARTSTNO = value;
            }
        }
        
        /// <summary>属性CROSS_FLG </summary>
        public string CROSS_FLG
        {
            get
            {
                return this.m_CROSS_FLG;
            }
            set
            {
                this.m_CROSS_FLG = value;
            }
        }
    }
    
    /// FNHANSOEntity执行类
    public abstract class FNHANSOEntityAction
    {
        
        private FNHANSOEntityAction()
        {
        }
        
        public static void Save(FNHANSOEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNHANSOEntity RetrieveAFNHANSOEntity(string HANSOKEY)
        {
            FNHANSOEntity obj=new FNHANSOEntity();
            obj.HANSOKEY=HANSOKEY;
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
        public static EntityContainer RetrieveFNHANSOEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNHANSOEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNHANSOEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNHANSOEntity));
            return rc.AsDataTable();
        }
    }
}
