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
    public class FNUNITEntity : EntityObject
    {
        
        /// <summary>STNO</summary>
        public const string __STNO = "STNO";
        
        /// <summary>UNITCODE</summary>
        public const string __UNITCODE = "UNITCODE";
        
        /// <summary>UNITNO</summary>
        public const string __UNITNO = "UNITNO";
        
        /// <summary>UNITSTAT</summary>
        public const string __UNITSTAT = "UNITSTAT";
        
        /// <summary>CONTROLNAME</summary>
        public const string __CONTROLNAME = "CONTROLNAME";
        
        /// <summary>AILESTNO</summary>
        public const string __AILESTNO = "AILESTNO";
        
        private string m_STNO;
        
        private string m_UNITCODE;
        
        private string m_UNITNO;
        
        private string m_UNITSTAT;
        
        private string m_CONTROLNAME;
        
        private string m_AILESTNO;
        
        /// <summary>构造函数</summary>
        public FNUNITEntity()
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
        
        /// <summary>属性UNITCODE </summary>
        public string UNITCODE
        {
            get
            {
                return this.m_UNITCODE;
            }
            set
            {
                this.m_UNITCODE = value;
            }
        }
        
        /// <summary>属性UNITNO </summary>
        public string UNITNO
        {
            get
            {
                return this.m_UNITNO;
            }
            set
            {
                this.m_UNITNO = value;
            }
        }
        
        /// <summary>属性UNITSTAT </summary>
        public string UNITSTAT
        {
            get
            {
                return this.m_UNITSTAT;
            }
            set
            {
                this.m_UNITSTAT = value;
            }
        }
        
        /// <summary>属性CONTROLNAME </summary>
        public string CONTROLNAME
        {
            get
            {
                return this.m_CONTROLNAME;
            }
            set
            {
                this.m_CONTROLNAME = value;
            }
        }
        
        /// <summary>属性AILESTNO </summary>
        public string AILESTNO
        {
            get
            {
                return this.m_AILESTNO;
            }
            set
            {
                this.m_AILESTNO = value;
            }
        }
    }
    
    /// FNUNITEntity执行类
    public abstract class FNUNITEntityAction
    {
        
        private FNUNITEntityAction()
        {
        }
        
        public static void Save(FNUNITEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNUNITEntity RetrieveAFNUNITEntity()
        {
            FNUNITEntity obj=new FNUNITEntity();
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
        public static EntityContainer RetrieveFNUNITEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNUNITEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNUNITEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNUNITEntity));
            return rc.AsDataTable();
        }
    }
}