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
    public class FNLCNTLEntity : EntityObject
    {
        
        /// <summary>TERMNO</summary>
        public const string __TERMNO = "TERMNO";
        
        /// <summary>LISTNO</summary>
        public const string __LISTNO = "LISTNO";
        
        /// <summary>FRPARA</summary>
        public const string __FRPARA = "FRPARA";
        
        /// <summary>TOPARA</summary>
        public const string __TOPARA = "TOPARA";
        
        /// <summary>SYORIFLG</summary>
        public const string __SYORIFLG = "SYORIFLG";
        
        private System.Decimal m_TERMNO;
        
        private System.Decimal m_LISTNO;
        
        private string m_FRPARA;
        
        private string m_TOPARA;
        
        private string m_SYORIFLG;
        
        /// <summary>构造函数</summary>
        public FNLCNTLEntity()
        {
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
        
        /// <summary>属性LISTNO </summary>
        public System.Decimal LISTNO
        {
            get
            {
                return this.m_LISTNO;
            }
            set
            {
                this.m_LISTNO = value;
            }
        }
        
        /// <summary>属性FRPARA </summary>
        public string FRPARA
        {
            get
            {
                return this.m_FRPARA;
            }
            set
            {
                this.m_FRPARA = value;
            }
        }
        
        /// <summary>属性TOPARA </summary>
        public string TOPARA
        {
            get
            {
                return this.m_TOPARA;
            }
            set
            {
                this.m_TOPARA = value;
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
    }
    
    /// FNLCNTLEntity执行类
    public abstract class FNLCNTLEntityAction
    {
        
        private FNLCNTLEntityAction()
        {
        }
        
        public static void Save(FNLCNTLEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNLCNTLEntity RetrieveAFNLCNTLEntity()
        {
            FNLCNTLEntity obj=new FNLCNTLEntity();
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
        public static EntityContainer RetrieveFNLCNTLEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNLCNTLEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNLCNTLEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNLCNTLEntity));
            return rc.AsDataTable();
        }
    }
}
