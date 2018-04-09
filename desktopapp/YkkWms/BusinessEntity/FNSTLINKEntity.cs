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
    public class FNSTLINKEntity : EntityObject
    {
        
        /// <summary>STNO</summary>
        public const string __STNO = "STNO";
        
        /// <summary>NPATERNNO</summary>
        public const string __NPATERNNO = "NPATERNNO";
        
        /// <summary>SPATERNNO</summary>
        public const string __SPATERNNO = "SPATERNNO";
        
        private string m_STNO;
        
        private System.Decimal m_NPATERNNO;
        
        private System.Decimal m_SPATERNNO;
        
        /// <summary>构造函数</summary>
        public FNSTLINKEntity()
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
        
        /// <summary>属性NPATERNNO </summary>
        public System.Decimal NPATERNNO
        {
            get
            {
                return this.m_NPATERNNO;
            }
            set
            {
                this.m_NPATERNNO = value;
            }
        }
        
        /// <summary>属性SPATERNNO </summary>
        public System.Decimal SPATERNNO
        {
            get
            {
                return this.m_SPATERNNO;
            }
            set
            {
                this.m_SPATERNNO = value;
            }
        }
    }
    
    /// FNSTLINKEntity执行类
    public abstract class FNSTLINKEntityAction
    {
        
        private FNSTLINKEntityAction()
        {
        }
        
        public static void Save(FNSTLINKEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNSTLINKEntity RetrieveAFNSTLINKEntity(string STNO)
        {
            FNSTLINKEntity obj=new FNSTLINKEntity();
            obj.STNO=STNO;
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
        public static EntityContainer RetrieveFNSTLINKEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNSTLINKEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNSTLINKEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNSTLINKEntity));
            return rc.AsDataTable();
        }
    }
}
