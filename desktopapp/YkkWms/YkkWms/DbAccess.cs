using System;
using System.Collections.Generic;
using System.Text;
using PersistenceLayer;
using BusinessEntity;
using System.Data;
using System.Data.OleDb;

namespace YkkWms
{
    class DbAccess
    {

        public static string generateScheduleNo()
        {
            IDataParameter[] paras = new IDataParameter[3];
            OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");
            para.ParameterName = "@io_schedule_no";
            para.OleDbType = OleDbType.Char;
            para.Value = string.Empty;
            para.Size = 18;
            para.Direction = ParameterDirection.InputOutput;
            paras[0] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_return_code";
            para.Value = 0;
            para.Size = 9;
            para.Direction = ParameterDirection.InputOutput;
            paras[1] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_return_message";
            para.Value = string.Empty;
            para.Size = 255;
            para.Direction = ParameterDirection.InputOutput;
            paras[2] = para;

            Query.RunProcedure("generate_schedule_no", paras, "ykk");

            if (Int32.Parse(paras[1].Value.ToString()) != 0)
            {
                throw new Exception(paras[2].Value.ToString());
            }

            return paras[0].Value.ToString().Trim();
        }

        public static void callProcedure(string scheduleNo, string procedureName)
        {
            IDataParameter[] paras = new IDataParameter[3];
            OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");
            para.ParameterName = "@in_schedule_no";
            para.OleDbType = OleDbType.Char;
            para.Value = scheduleNo;
            para.Size = 18;
            para.Direction = ParameterDirection.Input;
            paras[0] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_return_code";
            para.Value = 0;
            para.Size = 9;
            para.Direction = ParameterDirection.InputOutput;
            paras[1] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_return_message";
            para.Value = string.Empty;
            para.Size = 255;
            para.Direction = ParameterDirection.InputOutput;
            paras[2] = para;

            Query.RunProcedure(procedureName, paras, "ykk");

            if (Int32.Parse(paras[1].Value.ToString()) != 0)
            {
                throw new Exception(paras[2].Value.ToString());
            }
        }

        public static void callAfterExport()
        {
            IDataParameter[] paras = new IDataParameter[2];

            OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_return_code";
            para.Value = 0;
            para.Size = 9;
            para.Direction = ParameterDirection.InputOutput;
            paras[0] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_return_message";
            para.Value = string.Empty;
            para.Size = 255;
            para.Direction = ParameterDirection.InputOutput;
            paras[1] = para;

            Query.RunProcedure("after_export", paras, "ykk");

            if (Int32.Parse(paras[0].Value.ToString()) != 0)
            {
                throw new Exception(paras[1].Value.ToString());
            }
        }

        public static void callAfterStockin(string weightRportCompleteFlag)
        {
            IDataParameter[] paras = new IDataParameter[3];
            OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");
            para.ParameterName = "@in_weight_report_complete_flag";
            para.OleDbType = OleDbType.Char;
            para.Value = weightRportCompleteFlag;
            para.Size = 1;
            para.Direction = ParameterDirection.Input;
            paras[0] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_return_code";
            para.Value = 0;
            para.Size = 9;
            para.Direction = ParameterDirection.InputOutput;
            paras[1] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_return_message";
            para.Value = string.Empty;
            para.Size = 255;
            para.Direction = ParameterDirection.InputOutput;
            paras[2] = para;

            Query.RunProcedure("after_stockin", paras, "ykk");

            if (Int32.Parse(paras[1].Value.ToString()) != 0)
            {
                throw new Exception(paras[2].Value.ToString());
            }
        }

        public static void callSwitchOnLight(string stationNo, string ligthType)
        {
            IDataParameter[] paras = new IDataParameter[4];
            OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");

            para.ParameterName = "@in_station_no";
            para.OleDbType = OleDbType.Char;
            para.Value = stationNo;
            para.Size = 4;
            para.Direction = ParameterDirection.Input;
            paras[0] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.ParameterName = "@in_light_type";
            para.OleDbType = OleDbType.VarChar;
            para.Value = ligthType;
            para.Size = 2;
            para.Direction = ParameterDirection.Input;
            paras[1] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_return_code";
            para.Value = 0;
            para.Size = 9;
            para.Direction = ParameterDirection.InputOutput;
            paras[2] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_return_message";
            para.Value = string.Empty;
            para.Size = 255;
            para.Direction = ParameterDirection.InputOutput;
            paras[3] = para;

            Query.RunProcedure("switch_on_light", paras, "ykk");

            if (Int32.Parse(paras[2].Value.ToString()) != 0)
            {
                throw new Exception(paras[3].Value.ToString());
            }
        }

        
        public static LOGINUSEREntity GetUser(string userId, string password)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(LOGINUSEREntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(LOGINUSEREntity.__USERID, userId);
            c.AddEqualTo(LOGINUSEREntity.__PASSWORD, password);
            return rc.AsEntity() as LOGINUSEREntity;
        }

        public static FNTOUCYAKUEntity GetTouCyaKu(string stNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNTOUCYAKUEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNTOUCYAKUEntity.__STNO, stNo);
            c.AddEqualTo(FNTOUCYAKUEntity.__SYORIFLG, "1");
            return rc.AsEntity() as FNTOUCYAKUEntity;
        }

        public static FNHANSOEntity GetHanSo(string mckey)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNHANSOEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNHANSOEntity.__MCKEY, mckey);
            return rc.AsEntity() as FNHANSOEntity;
        }

        public static FNZAIKOEntity GetZaiKoBySystemId(string systemId)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNZAIKOEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNZAIKOEntity.__SYSTEMID, systemId);
            return rc.AsEntity() as FNZAIKOEntity;
        }

        public static FNZAIKOEntity GetZaiKoByBucketNo(string bucketNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNZAIKOEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNZAIKOEntity.__BUCKET_NO, bucketNo);
            return rc.AsEntity() as FNZAIKOEntity;
        }

        public static FNZAIKOEntity GetZaiKoByTicketNo(string ticketNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNZAIKOEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNZAIKOEntity.__TICKET_NO, ticketNo);
            return rc.AsEntity() as FNZAIKOEntity;
        }

        public static FNSTATIONEntity GetStation(string stNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNSTATIONEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNSTATIONEntity.__STNO, stNo);
            return rc.AsEntity() as FNSTATIONEntity;
        }

        public static FMBUCKETEntity GetBucket(string bucketNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FMBUCKETEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FMBUCKETEntity.__BUCKET_NO, bucketNo);
            return rc.AsEntity() as FMBUCKETEntity;
        }

        public static FMZKEYEntity GetManagedZKey(string zaikey)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FMZKEYEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FMZKEYEntity.__ZAIKEY, zaikey);
            c.AddEqualTo(FMZKEYEntity.__MANAGE_ITEM_FLAG, '0');
            return rc.AsEntity() as FMZKEYEntity;
        }

        public static FMZKEYEntity GetUnmanagedZKey(string zaikey)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FMZKEYEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FMZKEYEntity.__ZAIKEY, zaikey);
            c.AddEqualTo(FMZKEYEntity.__MANAGE_ITEM_FLAG, '1');
            return rc.AsEntity() as FMZKEYEntity;
        }

        public static FNRANGEEntity GetRange(string section, string line)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNRANGEEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNRANGEEntity.__MADE_SECTION, section);
            c.AddEqualTo(FNRANGEEntity.__MADE_LINE, line);
            return rc.AsEntity() as FNRANGEEntity;
        }

        public static TERMINALEntity GetTermByIP(string ipAddress)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(TERMINALEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(TERMINALEntity.__IPADDRESS, ipAddress);
            return rc.AsEntity() as TERMINALEntity;
        }

        public static FNPICK_CTLEntity GetPick_Ctl(string termNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNPICK_CTLEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNPICK_CTLEntity.__TERMNO, termNo);
            return rc.AsEntity() as FNPICK_CTLEntity;
        }

        public static List<FNSIJIEntity> GetSiJis(string mckey)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNSIJIEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNSIJIEntity.__MCKEY, mckey);
            EntityContainer ec = rc.AsEntityContainer();
            List<FNSIJIEntity> sijis = new List<FNSIJIEntity>();
            for (int i = 0; i < ec.Count; i++)
            {
                sijis.Add(ec[i] as FNSIJIEntity);
            }
            return sijis;
        }

        public static void SetChudan(string flag)
        {
            UpdateCriteria uc = new UpdateCriteria(typeof(FNSTATIONEntity));
            Condition c = uc.GetNewCondition();
            c.AddEqualTo(FNSTATIONEntity.__STNO, GlobalAccess.StationNo);
            uc.AddAttributeForUpdate(FNSTATIONEntity.__CHUDANFLG, flag);
            uc.Perform();
        }

        public static USERATTRIBUTEEntity GetUserAttributeByUserId(string userId)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(USERATTRIBUTEEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(USERATTRIBUTEEntity.__USERID, userId);
            return rc.AsEntity() as USERATTRIBUTEEntity;
        }

        public static FNLOCATEntity GetLocationBySystemid(string systemid)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNLOCATEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNLOCATEntity.__SYSTEMID, systemid);
            return rc.AsEntity() as FNLOCATEntity;
        }

        public static FNLOCATEntity GetLocationByBucketNo(string bucketNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNLOCATEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNLOCATEntity.__BUCKET_NO, bucketNo);
            return rc.AsEntity() as FNLOCATEntity;
        }

        public static bool IsSystemOnline()
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNAREAEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNAREAEntity.__ARCNO, "1");
            c.AddEqualTo(FNAREAEntity.__SYSTEMFLG, "1");
            return (rc.AsEntity() != null);
        }

        public static string GetStockInMode(string stationNo)
        {
            FNSTATIONEntity station = DbAccess.GetStation(stationNo);
            if (station == null)
            {
                return null;                
            }

            if (station.NYUSYUMODE == Nyusyumode.Normal)
            {
                return StockInMode.Normal;
            }
            else if (station.NYUSYUMODE == Nyusyumode.Empty_Bucket)
            {
                return StockInMode.EmptyBucket;
            }
            return null;
        }

        public static FNTOUCYAKUEntity GetArrivalReport(string stationNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNTOUCYAKUEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNTOUCYAKUEntity.__STNO, stationNo);
            c.AddEqualTo(FNTOUCYAKUEntity.__SYORIFLG, "1");                    
            return rc.AsEntity() as FNTOUCYAKUEntity;        
        }

        public static FNHANSOEntity GetTransData(string mcKey)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNHANSOEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNHANSOEntity.__MCKEY, mcKey);
            return rc.AsEntity() as FNHANSOEntity;
        }

        public static FNZAIKOEntity GetZaiKoInAutoWarehouseByBucketNo(string bucketNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNZAIKOEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNZAIKOEntity.__BUCKET_NO, bucketNo);
            c.AddEqualTo(FNZAIKOEntity.__STORAGE_PLACE_FLAG, "0");//自动仓库
            return rc.AsEntity() as FNZAIKOEntity;
        }

        public static FNZAIKOEntity GetZaiKoInFlatWarehouseByBucketNo(string bucketNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNZAIKOEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNZAIKOEntity.__BUCKET_NO, bucketNo);
            c.AddEqualTo(FNZAIKOEntity.__STORAGE_PLACE_FLAG, "1");//平库
            return rc.AsEntity() as FNZAIKOEntity;
        }

        public static FNHANSOEntity GetHansoByBucketNo(string bucketNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNHANSOEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNHANSOEntity.__BUCKET_NO, bucketNo);
            return rc.AsEntity() as FNHANSOEntity;
        }

        public static void UpdateFngset(string scheduleNo)
        {
            IDataParameter[] paras = new IDataParameter[3];

            OleDbParameter para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_datetime";
            para.Value = 0;
            para.Size = 14;
            para.Direction = ParameterDirection.InputOutput;
            paras[0] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.Integer;
            para.ParameterName = "@io_return_code";
            para.Value = 0;
            para.Size = 9;
            para.Direction = ParameterDirection.InputOutput;
            paras[1] = para;

            para = (OleDbParameter)Query.GetParameter("ykk");
            para.OleDbType = OleDbType.VarChar;
            para.ParameterName = "@io_return_message";
            para.Value = string.Empty;
            para.Size = 255;
            para.Direction = ParameterDirection.InputOutput;
            paras[2] = para;

            Query.RunProcedure("get_system_datetime", paras, "ykk");

            if (Int32.Parse(paras[1].Value.ToString()) != 0)
            {
                throw new Exception(paras[2].Value.ToString());
            }

            string datetime = paras[0].Value.ToString();

            if (String.IsNullOrEmpty(scheduleNo)) return;
            UpdateCriteria uc = new UpdateCriteria(typeof(FNGSETEntity));
            Condition c = uc.GetNewCondition();
            c.AddEqualTo(FNGSETEntity.__SCHNO, scheduleNo);
            uc.AddAttributeForUpdate(FNGSETEntity.__SYORIFLG, "1");
            uc.AddAttributeForUpdate(FNGSETEntity.__SAKUSEIHIJI, datetime);
            uc.Perform();
        }

        public static FNRANGEEntity GetRange(string termNo)
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNRANGEEntity));
            Condition c = rc.GetNewCondition();
            c.AddEqualTo(FNRANGEEntity.__TERMNO, termNo);
            return rc.AsEntity() as FNRANGEEntity;
        }

        public static FNSYSTEMEntity GetSystemInfo()
        {
            RetrieveCriteria rc = new RetrieveCriteria(typeof(FNSYSTEMEntity));
            return rc.AsEntity() as FNSYSTEMEntity;
        }

        public static bool IsBucketInLocation(string bucketNo)
        {
            return DbAccess.GetLocationByBucketNo(bucketNo) == null ? false : true;
        }

        public static bool IsBucketInAutoWarehouse(string bucketNo)
        {
            return DbAccess.GetZaiKoInAutoWarehouseByBucketNo(bucketNo) == null ? false : true;
        }

        public static bool IsBucketInFlatWarehouse(string bucketNo)
        {
            return DbAccess.GetZaiKoInFlatWarehouseByBucketNo(bucketNo) == null ? false : true;
        }

        public static bool IsBucketInTransportation(string bucketNo)
        {
            return DbAccess.GetHansoByBucketNo(bucketNo) == null ? false : true;
        }
    }
}
