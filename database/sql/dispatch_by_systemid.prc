DROP PROCEDURE DISPATCH_BY_SYSTEMID;

CREATE OR REPLACE PROCEDURE     dispatch_by_systemid (
   in_systemid             IN     fnlocat.systemid%TYPE,
   in_stockout_num         IN     NUMBER,
   in_retrieval_plan_key   IN     fnsyotei.retrieval_plankey%TYPE,
   in_dispatch_type        IN     CHAR,
   in_stockout_type        IN     CHAR,
   in_subdivide_flag       IN     fnsiji.subdivide_flag%TYPE,
   in_userid               IN     fnzaiko.userid%TYPE,
   in_username             IN     fnzaiko.username%TYPE,
   io_return_code          IN OUT NUMBER,
   io_return_message       IN OUT VARCHAR2
)
IS
   wk_stockout_mode   fnhanso.sijisyosai%TYPE;
   wk_mckey           fnhanso.mckey%TYPE;
   wk_endstno         fnhanso.endstno%TYPE;
   wk_next_st         fmpattern.next_st%TYPE;
   wk_label_key       fnsiji.label_key%TYPE;
   wk_rsvinfo_row     fnsyotei%ROWTYPE;
   wk_fnzaiko_row     fnzaiko%ROWTYPE;
   wk_hansokey        fnhanso.hansokey%TYPE;
   wk_fnlocat_row     fnlocat%ROWTYPE;
   wk_label_type      fnlabel.label_type%TYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';
       
   IF in_subdivide_flag = '0'
   THEN
      get_stockout_mode (in_systemid,
                         in_stockout_num,
                         wk_stockout_mode,
                         io_return_code,
                         io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   ELSE
      wk_stockout_mode := ykk_global_defination.stockout_mode_picking;
   END IF;

   generate_mckey (ykk_global_defination.stock_type_stockout,
                   wk_mckey,
                   io_return_code,
                   io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   get_reservation_info (in_retrieval_plan_key,
                         wk_rsvinfo_row,
                         io_return_code,
                         io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   get_end_station_no (wk_rsvinfo_row.retrieval_station,
                       wk_stockout_mode,
                       wk_endstno,
                       io_return_code,
                       io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT   *
     INTO   wk_fnlocat_row
     FROM   fnlocat
    WHERE   systemid = in_systemid;

   get_next_station_no (wk_fnlocat_row.ailestno,
                        wk_endstno,
                        wk_fnlocat_row.ailestno,
                        wk_next_st,
                        io_return_code,
                        io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   generate_hansokey_for_stockout (ykk_global_defination.stock_type_stockout,
                                   wk_hansokey,
                                   io_return_code,
                                   io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT   *
     INTO   wk_fnzaiko_row
     FROM   fnzaiko
    WHERE   systemid = in_systemid;

   INSERT INTO fnhanso (hansokey,
                        systemid,
                        mckey,
                        startstno,
                        endstno,
                        motostno,
                        sakistno,
                        hjyotaiflg,
                        nyusyukbn,
                        yusenkbn,
                        sagyokbn,
                        sijisyosai,
                        bucket_no,
                        setkbn)
     VALUES   (wk_hansokey,
               in_systemid,
               wk_mckey,
               wk_fnlocat_row.ailestno,
               wk_endstno,
               wk_fnlocat_row.ailestno,
               wk_next_st,
               ykk_global_defination.transportation_status_standby,
               ykk_global_defination.transportation_type_stockout,
               ykk_global_defination.trans_priority_normal,
               ykk_global_defination.transportation_work_type_plan,
               wk_stockout_mode,
               wk_fnzaiko_row.bucket_no,
               '2');

   generate_label_key (wk_label_key, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   INSERT INTO fnsiji (hansokey,
                       mckey,
                       ticket_no,
                       zaikey,
                       color_code,
                       section,
                       line,
                       nyusyusu,
                       retrieval_plan_key,
                       label_key,
                       line_type,
                       depot_code,
                       customer_code,
                       manage_item_flag,
                       retrieval_station,
                       subdivide_flag)
     VALUES   (wk_hansokey,
               wk_mckey,
               wk_fnzaiko_row.ticket_no,
               wk_fnzaiko_row.zaikey,
               wk_fnzaiko_row.color_code,
               wk_rsvinfo_row.section,
               wk_rsvinfo_row.line,
               in_stockout_num,
               in_retrieval_plan_key,
               wk_label_key,
               wk_rsvinfo_row.line_type,
               wk_rsvinfo_row.depot_code,
               wk_rsvinfo_row.customer_code,
               wk_fnzaiko_row.manage_item_flag,
               wk_rsvinfo_row.retrieval_station,
               in_subdivide_flag);

   IF in_subdivide_flag = '1'
   THEN
      wk_label_type := ykk_global_defination.label_type_subdivided_stockout;
   ELSE
      wk_label_type := ykk_global_defination.label_type_normal_stockout;
   END IF;

   create_label_data_for_stockout (wk_label_key,
                                   wk_label_type,
                                   in_retrieval_plan_key,
                                   wk_fnzaiko_row.ticket_no,
                                   wk_stockout_mode,
                                   in_stockout_num,
                                   in_stockout_type,
                                   io_return_code,
                                   io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_stock_data_for_stockout (in_systemid,
                                   in_stockout_num,
                                   in_userid,
                                   in_username,
                                   io_return_code,
                                   io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_locat_data_for_stockout (in_systemid,
                                   in_stockout_num,
                                   io_return_code,
                                   io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

  <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      IF in_dispatch_type = ykk_global_defination.dispatch_type_auto
      THEN
         io_return_code := 8000113;
         io_return_message := '出库自动分配设定时发生错误' || ' ' || SQLERRM;
      ELSE
         io_return_code := 8000114;
         io_return_message := '出库手动分配设定时发生错误' || ' ' || SQLERRM;
      END IF;

      DBMS_OUTPUT.put_line (SQLERRM);
END dispatch_by_systemid; 

/
