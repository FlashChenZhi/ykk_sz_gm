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
    public class FMTORIHIEntity : EntityObject
    {
        
        /// <summary>TORICODE</summary>
        public const string __TORICODE = "TORICODE";
        
        /// <summary>TORINAME</summary>
        public const string __TORINAME = "TORINAME";
        
        /// <summary>YUBINNO</summary>
        public const string __YUBINNO = "YUBINNO";
        
        /// <summary>ADDR1</summary>
        public const string __ADDR1 = "ADDR1";
        
        /// <summary>ADDR2</summary>
        public const string __ADDR2 = "ADDR2";
        
        /// <summary>TELNO</summary>
        public const string __TELNO = "TELNO";
        
        /// <summary>NOTE1</summary>
        public const string __NOTE1 = "NOTE1";
        
        /// <summary>NOTE2</summary>
        public const string __NOTE2 = "NOTE2";
        
        private string m_TORICODE;
        
        private string m_TORINAME;
        
        private string m_YUBINNO;
        
        private string m_ADDR1;
        
        private string m_ADDR2;
        
        private string m_TELNO;
        
        private string m_NOTE1;
        
        private string m_NOTE2;
        
        /// <summary>构造函数</summary>
        public FMTORIHIEntity()
        {
        }
        
        /// <summary>属性TORICODE </summary>
        public string TORICODE
        {
            get
            {
                return this.m_TORICODE;
            }
            set
            {
                this.m_TORICODE = value;
            }
        }
        
        /// <summary>属性TORINAME </summary>
        public string TORINAME
        {
            get
            {
                return this.m_TORINAME;
            }
            set
            {
                this.m_TORINAME = value;
            }
        }
        
        /// <summary>属性YUBINNO </summary>
        public string YUBINNO
        {
            get
            {
                return this.m_YUBINNO;
            }
            set
            {
                this.m_YUBINNO = value;
            }
        }
        
        /// <summary>属性ADDR1 </summary>
        public string ADDR1
        {
            get
            {
                return this.m_ADDR1;
            }
            set
            {
                this.m_ADDR1 = value;
            }
        }
        
        /// <summary>属性ADDR2 </summary>
        public string ADDR2
        {
            get
            {
                return this.m_ADDR2;
            }
            set
            {
                this.m_ADDR2 = value;
            }
        }
        
        /// <summary>属性TELNO </summary>
        public string TELNO
        {
            get
            {
                return this.m_TELNO;
            }
            set
            {
                this.m_TELNO = value;
            }
        }
        
        /// <summary>属性NOTE1 </summary>
        public string NOTE1
        {
            get
            {
                return this.m_NOTE1;
            }
            set
            {
                this.m_NOTE1 = value;
            }
        }
        
        /// <summary>属性NOTE2 </summary>
        public string NOTE2
        {
            get
            {
                return this.m_NOTE2;
            }
            set
            {
                this.m_NOTE2 = value;
            }
        }
    }
    
    /// FMTORIHIEntity执行类
    public abstract class FMTORIHIEntityAction
    {
        
        private FMTORIHIEntityAction()
        {
        }
        
        public static void Save(FMTORIHIEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FMTORIHIEntity RetrieveAFMTORIHIEntity(string TORICODE)
        {
            FMTORIHIEntity obj=new FMTORIHIEntity();
            obj.TORICODE=TORICODE;
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
        public static EntityContainer RetrieveFMTORIHIEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FMTORIHIEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFMTORIHIEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FMTORIHIEntity));
            return rc.AsDataTable();
        }
    }
}
