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
    public class FMCATEGORYEntity : EntityObject
    {
        
        /// <summary>CATEGORY</summary>
        public const string __CATEGORY = "CATEGORY";
        
        /// <summary>CATEGORYNAME</summary>
        public const string __CATEGORYNAME = "CATEGORYNAME";
        
        private System.Decimal m_CATEGORY;
        
        private string m_CATEGORYNAME;
        
        /// <summary>构造函数</summary>
        public FMCATEGORYEntity()
        {
        }
        
        /// <summary>属性CATEGORY </summary>
        public System.Decimal CATEGORY
        {
            get
            {
                return this.m_CATEGORY;
            }
            set
            {
                this.m_CATEGORY = value;
            }
        }
        
        /// <summary>属性CATEGORYNAME </summary>
        public string CATEGORYNAME
        {
            get
            {
                return this.m_CATEGORYNAME;
            }
            set
            {
                this.m_CATEGORYNAME = value;
            }
        }
    }
    
    /// FMCATEGORYEntity执行类
    public abstract class FMCATEGORYEntityAction
    {
        
        private FMCATEGORYEntityAction()
        {
        }
        
        public static void Save(FMCATEGORYEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FMCATEGORYEntity RetrieveAFMCATEGORYEntity(System.Decimal CATEGORY)
        {
            FMCATEGORYEntity obj=new FMCATEGORYEntity();
            obj.CATEGORY=CATEGORY;
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
        public static EntityContainer RetrieveFMCATEGORYEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FMCATEGORYEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFMCATEGORYEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FMCATEGORYEntity));
            return rc.AsDataTable();
        }
    }
}