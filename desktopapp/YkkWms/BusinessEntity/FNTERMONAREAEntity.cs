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
    public class FNTERMONAREAEntity : EntityObject
    {
        
        /// <summary>TERMNO</summary>
        public const string __TERMNO = "TERMNO";
        
        /// <summary>SAGYOBA</summary>
        public const string __SAGYOBA = "SAGYOBA";
        
        /// <summary>DFLTKBN</summary>
        public const string __DFLTKBN = "DFLTKBN";
        
        private System.Decimal m_TERMNO;
        
        private string m_SAGYOBA;
        
        private string m_DFLTKBN;
        
        /// <summary>构造函数</summary>
        public FNTERMONAREAEntity()
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
        
        /// <summary>属性DFLTKBN </summary>
        public string DFLTKBN
        {
            get
            {
                return this.m_DFLTKBN;
            }
            set
            {
                this.m_DFLTKBN = value;
            }
        }
    }
    
    /// FNTERMONAREAEntity执行类
    public abstract class FNTERMONAREAEntityAction
    {
        
        private FNTERMONAREAEntityAction()
        {
        }
        
        public static void Save(FNTERMONAREAEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNTERMONAREAEntity RetrieveAFNTERMONAREAEntity()
        {
            FNTERMONAREAEntity obj=new FNTERMONAREAEntity();
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
        public static EntityContainer RetrieveFNTERMONAREAEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNTERMONAREAEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNTERMONAREAEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNTERMONAREAEntity));
            return rc.AsDataTable();
        }
    }
}
