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
    public class FNSAIBANEntity : EntityObject
    {
        
        /// <summary>SAIBANID</summary>
        public const string __SAIBANID = "SAIBANID";
        
        /// <summary>TOPSAINO</summary>
        public const string __TOPSAINO = "TOPSAINO";
        
        /// <summary>ENDSAINO</summary>
        public const string __ENDSAINO = "ENDSAINO";
        
        /// <summary>SAIBANNO</summary>
        public const string __SAIBANNO = "SAIBANNO";
        
        /// <summary>SAIBANHIJI</summary>
        public const string __SAIBANHIJI = "SAIBANHIJI";
        
        /// <summary>SAIBANIDNAME</summary>
        public const string __SAIBANIDNAME = "SAIBANIDNAME";
        
        private System.Decimal m_SAIBANID;
        
        private System.Decimal m_TOPSAINO;
        
        private System.Decimal m_ENDSAINO;
        
        private System.Decimal m_SAIBANNO;
        
        private string m_SAIBANHIJI;
        
        private string m_SAIBANIDNAME;
        
        /// <summary>构造函数</summary>
        public FNSAIBANEntity()
        {
        }
        
        /// <summary>属性SAIBANID </summary>
        public System.Decimal SAIBANID
        {
            get
            {
                return this.m_SAIBANID;
            }
            set
            {
                this.m_SAIBANID = value;
            }
        }
        
        /// <summary>属性TOPSAINO </summary>
        public System.Decimal TOPSAINO
        {
            get
            {
                return this.m_TOPSAINO;
            }
            set
            {
                this.m_TOPSAINO = value;
            }
        }
        
        /// <summary>属性ENDSAINO </summary>
        public System.Decimal ENDSAINO
        {
            get
            {
                return this.m_ENDSAINO;
            }
            set
            {
                this.m_ENDSAINO = value;
            }
        }
        
        /// <summary>属性SAIBANNO </summary>
        public System.Decimal SAIBANNO
        {
            get
            {
                return this.m_SAIBANNO;
            }
            set
            {
                this.m_SAIBANNO = value;
            }
        }
        
        /// <summary>属性SAIBANHIJI </summary>
        public string SAIBANHIJI
        {
            get
            {
                return this.m_SAIBANHIJI;
            }
            set
            {
                this.m_SAIBANHIJI = value;
            }
        }
        
        /// <summary>属性SAIBANIDNAME </summary>
        public string SAIBANIDNAME
        {
            get
            {
                return this.m_SAIBANIDNAME;
            }
            set
            {
                this.m_SAIBANIDNAME = value;
            }
        }
    }
    
    /// FNSAIBANEntity执行类
    public abstract class FNSAIBANEntityAction
    {
        
        private FNSAIBANEntityAction()
        {
        }
        
        public static void Save(FNSAIBANEntity obj)
        {
            if (obj!=null)
            {
                obj.Save();
            }
        }
        
        /// <summary>根据主键获取一个实体</summary>
        public static FNSAIBANEntity RetrieveAFNSAIBANEntity()
        {
            FNSAIBANEntity obj=new FNSAIBANEntity();
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
        public static EntityContainer RetrieveFNSAIBANEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNSAIBANEntity));
            return rc.AsEntityContainer();
        }
        
        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNSAIBANEntity()
        {
            RetrieveCriteria rc=new RetrieveCriteria(typeof(FNSAIBANEntity));
            return rc.AsDataTable();
        }
    }
}
