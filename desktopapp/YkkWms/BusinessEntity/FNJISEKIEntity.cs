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
    public class FNJISEKIEntity : EntityObject
    {

        /// <summary>MCKEY</summary>
        public const string __MCKEY = "MCKEY";

        /// <summary>ZAIKEY</summary>
        public const string __ZAIKEY = "ZAIKEY";

        /// <summary>ZKNAME</summary>
        public const string __ZKNAME = "ZKNAME";

        /// <summary>ZKNAME2</summary>
        public const string __ZKNAME2 = "ZKNAME2";

        /// <summary>ZKNAME3</summary>
        public const string __ZKNAME3 = "ZKNAME3";

        /// <summary>SAKUSEIHIJI</summary>
        public const string __SAKUSEIHIJI = "SAKUSEIHIJI";

        /// <summary>NYUSYUKBN</summary>
        public const string __NYUSYUKBN = "NYUSYUKBN";

        /// <summary>SAGYOKBN</summary>
        public const string __SAGYOKBN = "SAGYOKBN";

        /// <summary>SAKUKBN</summary>
        public const string __SAKUKBN = "SAKUKBN";

        /// <summary>NYUSYUSTNO</summary>
        public const string __NYUSYUSTNO = "NYUSYUSTNO";

        /// <summary>TICKET_NO</summary>
        public const string __TICKET_NO = "TICKET_NO";

        /// <summary>BUCKET_NO</summary>
        public const string __BUCKET_NO = "BUCKET_NO";

        /// <summary>COLOR_CODE</summary>
        public const string __COLOR_CODE = "COLOR_CODE";

        /// <summary>NYUSYUSU</summary>
        public const string __NYUSYUSU = "NYUSYUSU";

        /// <summary>RETRIEVAL_NO</summary>
        public const string __RETRIEVAL_NO = "RETRIEVAL_NO";

        /// <summary>REAL_WORK_NUMBER</summary>
        public const string __REAL_WORK_NUMBER = "REAL_WORK_NUMBER";

        /// <summary>SERIAL_NO</summary>
        public const string __SERIAL_NO = "SERIAL_NO";

        /// <summary>ORDER_NO</summary>
        public const string __ORDER_NO = "ORDER_NO";

        /// <summary>ORDER_SERIAL_NO</summary>
        public const string __ORDER_SERIAL_NO = "ORDER_SERIAL_NO";

        /// <summary>START_DATE</summary>
        public const string __START_DATE = "START_DATE";

        /// <summary>START_TIMING_FLAG</summary>
        public const string __START_TIMING_FLAG = "START_TIMING_FLAG";

        /// <summary>COMPLETE_DATE</summary>
        public const string __COMPLETE_DATE = "COMPLETE_DATE";

        /// <summary>COMPLETE_TIMING_FLAG</summary>
        public const string __COMPLETE_TIMING_FLAG = "COMPLETE_TIMING_FLAG";

        /// <summary>DEPOT_CODE</summary>
        public const string __DEPOT_CODE = "DEPOT_CODE";

        /// <summary>SECTION</summary>
        public const string __SECTION = "SECTION";

        /// <summary>LINE</summary>
        public const string __LINE = "LINE";

        /// <summary>LINE_TYPE</summary>
        public const string __LINE_TYPE = "LINE_TYPE";

        /// <summary>CUSTOMER_CODE</summary>
        public const string __CUSTOMER_CODE = "CUSTOMER_CODE";

        /// <summary>CUSTOMER_NAME1</summary>
        public const string __CUSTOMER_NAME1 = "CUSTOMER_NAME1";

        /// <summary>CUSTOMER_NAME2</summary>
        public const string __CUSTOMER_NAME2 = "CUSTOMER_NAME2";

        /// <summary>PR_NO</summary>
        public const string __PR_NO = "PR_NO";

        /// <summary>RETRIEVAL_PLANKEY</summary>
        public const string __RETRIEVAL_PLANKEY = "RETRIEVAL_PLANKEY";

        /// <summary>RETRIEVAL_QTY</summary>
        public const string __RETRIEVAL_QTY = "RETRIEVAL_QTY";

        /// <summary>USERID</summary>
        public const string __USERID = "USERID";

        /// <summary>USERNAME</summary>
        public const string __USERNAME = "USERNAME";

        /// <summary>STARTSTNO</summary>
        public const string __STARTSTNO = "STARTSTNO";

        /// <summary>ENDSTNO</summary>
        public const string __ENDSTNO = "ENDSTNO";

        /// <summary>SYSTEMID</summary>
        public const string __SYSTEMID = "SYSTEMID";

        /// <summary>SYOZAIKEY</summary>
        public const string __SYOZAIKEY = "SYOZAIKEY";

        /// <summary>BACKUP_FLG</summary>
        public const string __BACKUP_FLG = "BACKUP_FLG";

        private string m_MCKEY = " ";

        private string m_ZAIKEY = " ";

        private string m_ZKNAME = " ";

        private string m_ZKNAME2 = " ";

        private string m_ZKNAME3 = " ";

        private string m_SAKUSEIHIJI = " ";

        private string m_NYUSYUKBN = " ";

        private string m_SAGYOKBN = " ";    

        private string m_SAKUKBN = " ";

        private string m_NYUSYUSTNO = " ";

        private string m_TICKET_NO = " ";

        private string m_BUCKET_NO = " ";

        private string m_COLOR_CODE = " ";

        private System.Decimal m_NYUSYUSU;

        private string m_RETRIEVAL_NO = " ";

        private System.Decimal m_REAL_WORK_NUMBER;

        private System.Decimal m_SERIAL_NO;

        private string m_ORDER_NO = " ";

        private System.Decimal m_ORDER_SERIAL_NO;

        private string m_START_DATE = " ";

        private string m_START_TIMING_FLAG = " ";

        private string m_COMPLETE_DATE = " ";

        private string m_COMPLETE_TIMING_FLAG = " ";

        private string m_DEPOT_CODE = " ";

        private string m_SECTION = " ";

        private string m_LINE = " ";

        private string m_LINE_TYPE = " ";

        private string m_CUSTOMER_CODE = " ";

        private string m_CUSTOMER_NAME1 = " ";

        private string m_CUSTOMER_NAME2 = " ";

        private string m_PR_NO = " ";

        private string m_RETRIEVAL_PLANKEY = " ";

        private System.Decimal m_RETRIEVAL_QTY;

        private string m_USERID = " ";

        private string m_USERNAME = " ";

        private string m_STARTSTNO = " ";

        private string m_ENDSTNO = " ";

        private string m_SYSTEMID = " ";

        private string m_SYOZAIKEY = " ";

        private string m_BACKUP_FLG = " ";

        /// <summary>构造函数</summary>
        public FNJISEKIEntity()
        {
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

        /// <summary>属性ZKNAME </summary>
        public string ZKNAME
        {
            get
            {
                return this.m_ZKNAME;
            }
            set
            {
                this.m_ZKNAME = value;
            }
        }

        /// <summary>属性ZKNAME2 </summary>
        public string ZKNAME2
        {
            get
            {
                return this.m_ZKNAME2;
            }
            set
            {
                this.m_ZKNAME2 = value;
            }
        }

        /// <summary>属性ZKNAME3 </summary>
        public string ZKNAME3
        {
            get
            {
                return this.m_ZKNAME3;
            }
            set
            {
                this.m_ZKNAME3 = value;
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

        /// <summary>属性SAKUKBN </summary>
        public string SAKUKBN
        {
            get
            {
                return this.m_SAKUKBN;
            }
            set
            {
                this.m_SAKUKBN = value;
            }
        }

        /// <summary>属性NYUSYUSTNO </summary>
        public string NYUSYUSTNO
        {
            get
            {
                return this.m_NYUSYUSTNO;
            }
            set
            {
                this.m_NYUSYUSTNO = value;
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

        /// <summary>属性RETRIEVAL_NO </summary>
        public string RETRIEVAL_NO
        {
            get
            {
                return this.m_RETRIEVAL_NO;
            }
            set
            {
                this.m_RETRIEVAL_NO = value;
            }
        }

        /// <summary>属性REAL_WORK_NUMBER </summary>
        public System.Decimal REAL_WORK_NUMBER
        {
            get
            {
                return this.m_REAL_WORK_NUMBER;
            }
            set
            {
                this.m_REAL_WORK_NUMBER = value;
            }
        }

        /// <summary>属性SERIAL_NO </summary>
        public System.Decimal SERIAL_NO
        {
            get
            {
                return this.m_SERIAL_NO;
            }
            set
            {
                this.m_SERIAL_NO = value;
            }
        }

        /// <summary>属性ORDER_NO </summary>
        public string ORDER_NO
        {
            get
            {
                return this.m_ORDER_NO;
            }
            set
            {
                this.m_ORDER_NO = value;
            }
        }

        /// <summary>属性ORDER_SERIAL_NO </summary>
        public System.Decimal ORDER_SERIAL_NO
        {
            get
            {
                return this.m_ORDER_SERIAL_NO;
            }
            set
            {
                this.m_ORDER_SERIAL_NO = value;
            }
        }

        /// <summary>属性START_DATE </summary>
        public string START_DATE
        {
            get
            {
                return this.m_START_DATE;
            }
            set
            {
                this.m_START_DATE = value;
            }
        }

        /// <summary>属性START_TIMING_FLAG </summary>
        public string START_TIMING_FLAG
        {
            get
            {
                return this.m_START_TIMING_FLAG;
            }
            set
            {
                this.m_START_TIMING_FLAG = value;
            }
        }

        /// <summary>属性COMPLETE_DATE </summary>
        public string COMPLETE_DATE
        {
            get
            {
                return this.m_COMPLETE_DATE;
            }
            set
            {
                this.m_COMPLETE_DATE = value;
            }
        }

        /// <summary>属性COMPLETE_TIMING_FLAG </summary>
        public string COMPLETE_TIMING_FLAG
        {
            get
            {
                return this.m_COMPLETE_TIMING_FLAG;
            }
            set
            {
                this.m_COMPLETE_TIMING_FLAG = value;
            }
        }

        /// <summary>属性DEPOT_CODE </summary>
        public string DEPOT_CODE
        {
            get
            {
                return this.m_DEPOT_CODE;
            }
            set
            {
                this.m_DEPOT_CODE = value;
            }
        }

        /// <summary>属性SECTION </summary>
        public string SECTION
        {
            get
            {
                return this.m_SECTION;
            }
            set
            {
                this.m_SECTION = value;
            }
        }

        /// <summary>属性LINE </summary>
        public string LINE
        {
            get
            {
                return this.m_LINE;
            }
            set
            {
                this.m_LINE = value;
            }
        }

        /// <summary>属性LINE_TYPE </summary>
        public string LINE_TYPE
        {
            get
            {
                return this.m_LINE_TYPE;
            }
            set
            {
                this.m_LINE_TYPE = value;
            }
        }

        /// <summary>属性CUSTOMER_CODE </summary>
        public string CUSTOMER_CODE
        {
            get
            {
                return this.m_CUSTOMER_CODE;
            }
            set
            {
                this.m_CUSTOMER_CODE = value;
            }
        }

        /// <summary>属性CUSTOMER_NAME1 </summary>
        public string CUSTOMER_NAME1
        {
            get
            {
                return this.m_CUSTOMER_NAME1;
            }
            set
            {
                this.m_CUSTOMER_NAME1 = value;
            }
        }

        /// <summary>属性CUSTOMER_NAME2 </summary>
        public string CUSTOMER_NAME2
        {
            get
            {
                return this.m_CUSTOMER_NAME2;
            }
            set
            {
                this.m_CUSTOMER_NAME2 = value;
            }
        }

        /// <summary>属性PR_NO </summary>
        public string PR_NO
        {
            get
            {
                return this.m_PR_NO;
            }
            set
            {
                this.m_PR_NO = value;
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

        /// <summary>属性RETRIEVAL_QTY </summary>
        public System.Decimal RETRIEVAL_QTY
        {
            get
            {
                return this.m_RETRIEVAL_QTY;
            }
            set
            {
                this.m_RETRIEVAL_QTY = value;
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

        /// <summary>属性SYOZAIKEY </summary>
        public string SYOZAIKEY
        {
            get
            {
                return this.m_SYOZAIKEY;
            }
            set
            {
                this.m_SYOZAIKEY = value;
            }
        }

        /// <summary>属性BACKUP_FLG </summary>
        public string BACKUP_FLG
        {
            get
            {
                return this.m_BACKUP_FLG;
            }
            set
            {
                this.m_BACKUP_FLG = value;
            }
        }
    }

    /// FNJISEKIEntity执行类
    public abstract class FNJISEKIEntityAction
    {

        private FNJISEKIEntityAction()
        {
        }

        public static void Save(FNJISEKIEntity obj)
        {
            if (obj != null)
            {
                obj.Save();
            }
        }

        /// <summary>根据主键获取一个实体</summary>
        public static FNJISEKIEntity RetrieveAFNJISEKIEntity()
        {
            FNJISEKIEntity obj = new FNJISEKIEntity();
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
        public static EntityContainer RetrieveFNJISEKIEntity()
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNJISEKIEntity));
            return rc.AsEntityContainer();
        }

        /// <summary>获取所有实体(EntityContainer)</summary>
        public static DataTable GetFNJISEKIEntity()
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNJISEKIEntity));
            return rc.AsDataTable();
        }
    }
}