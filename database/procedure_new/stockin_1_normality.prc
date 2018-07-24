DROP PROCEDURE AWC.STOCKIN_1_NORMALITY;

CREATE OR REPLACE PROCEDURE AWC.stockin_1_normality (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2)
IS
   wk_fngset_row                    fngset%ROWTYPE;
   wk_bucket_no                     fntoucyaku.bucket_no%TYPE;
   wk_mckey                         fntoucyaku.mckey%TYPE;
   wk_start_station_no              fnhanso.startstno%TYPE;
   wk_next_station_no               fnrute.stnoto%TYPE;
   wk_weight_report_complete_flag   fnzaiko.weight_report_complete_flag%TYPE;
   wk_systemid                      fnzaiko.systemid%TYPE;

   PROCEDURE update_stock_data_for_stockin1 (
      in_ticket_no        IN     fnzaiko.ticket_no%TYPE,
      in_bucket_no        IN     fnzaiko.bucket_no%TYPE,
      in_unit_weight      IN     fnzaiko.real_unit_weight%TYPE,
      in_stockin_num      IN     fngset.nyusyusu%TYPE,
      in_memo             IN     fnzaiko.memo%TYPE,
      in_restockin_flag   IN     fnzaiko.sainyukbn%TYPE,
      in_bag_flag         IN     fnzaiko.bag_flag%TYPE,
      in_userid           IN     fnzaiko.userid%TYPE,
      in_username         IN     fnzaiko.username%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2)
   IS
      wk_weight_report_complete_flag   fnzaiko.weight_report_complete_flag%TYPE;
      wk_restockin_flag                fnzaiko.sainyukbn%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT weight_report_complete_flag
        INTO wk_weight_report_complete_flag
        FROM fnzaiko
       WHERE ticket_no = in_ticket_no;

      IF wk_weight_report_complete_flag =
            ykk_global_defination.weight_report_not_completed
      THEN
         wk_weight_report_complete_flag :=
            ykk_global_defination.weight_report_executing;
      END IF;

      IF TRIM (in_restockin_flag) IS NULL
      THEN
         wk_restockin_flag := ' ';
      ELSE
         wk_restockin_flag := in_restockin_flag;
      END IF;

      UPDATE fnzaiko
         SET bucket_no = in_bucket_no,
             zaikosu = in_stockin_num,
             skanosu = in_stockin_num,
             real_unit_weight = in_unit_weight,
             memo = in_memo,
             sainyukbn = wk_restockin_flag,
             bag_flag = in_bag_flag,
             userid = in_userid,
             username = in_username,
             storage_place_flag =
                ykk_global_defination.storage_place_awc_warehouse,
             weight_report_complete_flag = wk_weight_report_complete_flag,
             remark = NULL
       WHERE ticket_no = in_ticket_no;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message :=
            '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_for_stockin1;

   PROCEDURE update_trans_data_for_stockin1 (
      in_mckey             IN     fnhanso.mckey%TYPE,
      in_ticket_no         IN     fnzaiko.ticket_no%TYPE,
      in_work_type         IN     fnhanso.sagyokbn%TYPE,
      in_from_station_no   IN     fnhanso.motostno%TYPE,
      in_to_station_no     IN     fnhanso.sakistno%TYPE,
      in_end_station_no    IN     fnhanso.endstno%TYPE,
      io_return_code       IN OUT NUMBER,
      io_return_message    IN OUT VARCHAR2)
   IS
      wk_systemid   fnhanso.systemid%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT systemid
        INTO wk_systemid
        FROM fnzaiko
       WHERE ticket_no = in_ticket_no;

      UPDATE fnhanso
         SET systemid = wk_systemid,
             sagyokbn = in_work_type,
             motostno = in_from_station_no,
             sakistno = in_to_station_no,
             endstno = in_end_station_no,
             hjyotaiflg = ykk_global_defination.transportation_status_start,
             nyusyukbn = ykk_global_defination.transportation_type_sttost
       WHERE mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000129;
         io_return_message :=
            '更新搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_trans_data_for_stockin1;

   PROCEDURE create_result_data_on_stockin1 (
      in_mckey             IN     fnsiji.mckey%TYPE,
      in_ticket_no         IN     fnzaiko.ticket_no%TYPE,
      in_station_no        IN     fnjiseki.nyusyustno%TYPE,
      in_trans_work_type   IN     fnhanso.sagyokbn%TYPE,
      in_unit_weight       IN     fnzaiko.real_unit_weight%TYPE,
      in_stockin_num       IN     NUMBER,
      in_user_id           IN     fnjiseki.userid%TYPE,
      in_user_name         IN     fnjiseki.username%TYPE,
      io_return_code       IN OUT NUMBER,
      io_return_message    IN OUT VARCHAR2)
   IS
      CURSOR indc_list (c_mckey IN fnsiji.mckey%TYPE)
      IS
         SELECT *
           FROM fnsiji
          WHERE mckey = c_mckey;

      indc_list_iterator   indc_list%ROWTYPE;
      wk_fnjiseki_row      fnjiseki%ROWTYPE;
      wk_fnsyotei_row      fnsyotei%ROWTYPE;
      wk_fnzaiko_row       fnzaiko%ROWTYPE;
      wk_fmzkey_row        fmzkey%ROWTYPE;
      wk_fnhanso_row       fnhanso%ROWTYPE;
      wk_count             NUMBER;
      --wk_systemid          fnjiseki.systemid%TYPE;
      wk_datetime14        VARCHAR2 (14);
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT *
        INTO wk_fnzaiko_row
        FROM fnzaiko
       WHERE ticket_no = in_ticket_no;

      IF wk_fnzaiko_row.zaikosu != in_stockin_num
      THEN
         GOTO endlabel;
      END IF;

      SELECT *
        INTO wk_fnhanso_row
        FROM fnhanso
       WHERE mckey = in_mckey;

      SELECT *
        INTO wk_fmzkey_row
        FROM fmzkey
       WHERE     zaikey = wk_fnzaiko_row.zaikey
             AND manage_item_flag = wk_fnzaiko_row.manage_item_flag;

      OPEN indc_list (in_mckey);

      LOOP
         FETCH indc_list INTO indc_list_iterator;

         IF indc_list%NOTFOUND = TRUE
         THEN
            IF indc_list%ROWCOUNT = 0
            THEN
               io_return_code := 8000102;
               io_return_message := '数据不存在';
            END IF;

            EXIT;
         END IF;

         wk_datetime14 := sysdate14_get ();
         wk_fnjiseki_row.mckey := in_mckey;
         wk_fnjiseki_row.zaikey := wk_fnzaiko_row.zaikey;
         wk_fnjiseki_row.measure_unit_weight := in_unit_weight;
         wk_fnjiseki_row.zkname := wk_fmzkey_row.zkname1;
         wk_fnjiseki_row.zkname2 := wk_fmzkey_row.zkname2;
         wk_fnjiseki_row.zkname3 := wk_fmzkey_row.zkname3;
         wk_fnjiseki_row.sakuseihiji := wk_datetime14;
         wk_fnjiseki_row.nyusyukbn := '1';

         IF TRIM (in_trans_work_type) IS NULL
         THEN
            wk_fnjiseki_row.sagyokbn := wk_fnhanso_row.sagyokbn;
         ELSE
            wk_fnjiseki_row.sagyokbn := in_trans_work_type;
         END IF;

         IF in_trans_work_type != 'E'
         THEN
            wk_fnjiseki_row.sakukbn :=
               ykk_global_defination.stock_amount_increase;
            wk_fnjiseki_row.nyusyusu := indc_list_iterator.nyusyusu;
         ELSE
            IF wk_fnzaiko_row.zaikosu > in_stockin_num
            THEN
               wk_fnjiseki_row.sakukbn :=
                  ykk_global_defination.stock_amount_decrease;
               wk_fnjiseki_row.nyusyusu :=
                  wk_fnzaiko_row.zaikosu - in_stockin_num;
            ELSE
               wk_fnjiseki_row.sakukbn :=
                  ykk_global_defination.stock_amount_increase;
               wk_fnjiseki_row.nyusyusu :=
                  in_stockin_num - wk_fnzaiko_row.zaikosu;
            END IF;
         END IF;

         wk_fnjiseki_row.nyusyustno := in_station_no;
         wk_fnjiseki_row.ticket_no := wk_fnzaiko_row.ticket_no;
         wk_fnjiseki_row.bucket_no := wk_fnhanso_row.bucket_no;
         wk_fnjiseki_row.color_code := wk_fnzaiko_row.color_code;
         wk_fnjiseki_row.real_work_number := indc_list_iterator.nyusyusu;
         wk_fnjiseki_row.userid := in_user_id;
         wk_fnjiseki_row.username := in_user_name;
         wk_fnjiseki_row.startstno := wk_fnhanso_row.startstno;
         wk_fnjiseki_row.endstno := wk_fnhanso_row.endstno;
         wk_fnjiseki_row.systemid := wk_fnzaiko_row.systemid;
         wk_fnjiseki_row.syozaikey := wk_fnhanso_row.syumotoloc;
         wk_fnjiseki_row.manage_item_flag := wk_fnzaiko_row.manage_item_flag;
         wk_fnjiseki_row.backup_flg := '0';

         SELECT COUNT (*)
           INTO wk_count
           FROM fnsyotei
          WHERE retrieval_plankey = indc_list_iterator.retrieval_plan_key;

         IF wk_count > 0
         THEN
            SELECT *
              INTO wk_fnsyotei_row
              FROM fnsyotei
             WHERE retrieval_plankey = indc_list_iterator.retrieval_plan_key;

            wk_fnjiseki_row.retrieval_no := wk_fnsyotei_row.retrieval_no;
            wk_fnjiseki_row.serial_no := wk_fnsyotei_row.serial_no;
            wk_fnjiseki_row.order_no := wk_fnsyotei_row.order_no;
            wk_fnjiseki_row.order_serial_no := wk_fnsyotei_row.order_serial_no;
            wk_fnjiseki_row.start_date := wk_fnsyotei_row.start_date;
            wk_fnjiseki_row.start_timing_flag :=
               wk_fnsyotei_row.start_timing_flag;
            wk_fnjiseki_row.complete_date := wk_fnsyotei_row.complete_date;
            wk_fnjiseki_row.complete_timing_flag :=
               wk_fnsyotei_row.complete_timing_flag;
            wk_fnjiseki_row.depot_code := wk_fnsyotei_row.depot_code;
            wk_fnjiseki_row.section := wk_fnsyotei_row.section;
            wk_fnjiseki_row.line := wk_fnsyotei_row.line;
            wk_fnjiseki_row.line_type := wk_fnsyotei_row.line_type;
            wk_fnjiseki_row.customer_code := wk_fnsyotei_row.customer_code;
            wk_fnjiseki_row.customer_name1 := wk_fnsyotei_row.customer_name1;
            wk_fnjiseki_row.customer_name2 := wk_fnsyotei_row.customer_name2;
            wk_fnjiseki_row.pr_no := wk_fnsyotei_row.pr_no;
            wk_fnjiseki_row.retrieval_plankey :=
               wk_fnsyotei_row.retrieval_plankey;
            wk_fnjiseki_row.retrieval_qty := indc_list_iterator.nyusyusu;
         ELSE
            wk_fnjiseki_row.retrieval_no := ' ';
            wk_fnjiseki_row.serial_no := 0;
            wk_fnjiseki_row.order_no := ' ';
            wk_fnjiseki_row.order_serial_no := 0;
            wk_fnjiseki_row.start_date := ' ';
            wk_fnjiseki_row.start_timing_flag := ' ';
            wk_fnjiseki_row.complete_date := ' ';
            wk_fnjiseki_row.complete_timing_flag := ' ';
            wk_fnjiseki_row.depot_code := ' ';
            wk_fnjiseki_row.section := ' ';
            wk_fnjiseki_row.line := ' ';
            wk_fnjiseki_row.line_type := ' ';
            wk_fnjiseki_row.customer_code := ' ';
            wk_fnjiseki_row.customer_name1 := ' ';
            wk_fnjiseki_row.customer_name2 := ' ';
            wk_fnjiseki_row.pr_no := ' ';
            wk_fnjiseki_row.retrieval_plankey := ' ';
            wk_fnjiseki_row.retrieval_qty := 0;
         END IF;

         INSERT INTO fnjiseki
              VALUES wk_fnjiseki_row;
      END LOOP;

     <<endlabel>>
      IF indc_list%ISOPEN = TRUE
      THEN
         CLOSE indc_list;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         IF indc_list%ISOPEN = TRUE
         THEN
            CLOSE indc_list;
         END IF;

         io_return_code := 8000149;
         io_return_message :=
            '生成出库实绩数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_result_data_on_stockin1;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT *
     INTO wk_fngset_row
     FROM fngset
    WHERE     schno = in_schedule_no
          AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT bucket_no, mckey
     INTO wk_bucket_no, wk_mckey
     FROM (  SELECT bucket_no, mckey
               FROM fntoucyaku
              WHERE     stno = wk_fngset_row.motostno
                    AND syoriflg =
                           ykk_global_defination.arrival_report_processed_stg1
           ORDER BY toucykuhiji ASC)
    WHERE ROWNUM = 1;

   check_bucket_not_instock (
      wk_bucket_no,
      ykk_global_defination.storage_place_awc_warehouse,
      io_return_code,
      io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   exists_empty_location (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT startstno
     INTO wk_start_station_no
     FROM fnhanso
    WHERE mckey = wk_mckey;

   get_next_station_no (wk_start_station_no,
                        wk_fngset_row.endstno,
                        wk_fngset_row.motostno,
                        wk_next_station_no,
                        io_return_code,
                        io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   check_transportation_route (wk_start_station_no,
                               wk_fngset_row.endstno,
                               io_return_code,
                               io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT weight_report_complete_flag
     INTO wk_weight_report_complete_flag
     FROM fnzaiko
    WHERE fnzaiko.ticket_no = wk_fngset_row.ticket_no;

   IF wk_weight_report_complete_flag != '1'
   THEN
      IF wk_weight_report_complete_flag =
            ykk_global_defination.weight_report_not_completed
      THEN
         create_weight_rpt_for_stockin (wk_fngset_row.ticket_no,
                                        wk_fngset_row.unit_weight,
                                        wk_fngset_row.measure_weight,
                                        wk_fngset_row.nyusyusu,
                                        io_return_code,
                                        io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      ELSE
         create_diff_rpt_for_stockin (wk_fngset_row.ticket_no,
                                      wk_fngset_row.unit_weight,
                                      wk_fngset_row.nyusyusu,
                                      io_return_code,
                                      io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;

         create_result_data_on_stockin1 (wk_mckey,
                                         wk_fngset_row.ticket_no,
                                         wk_fngset_row.motostno,
                                         'E',
                                         wk_fngset_row.unit_weight,
                                         wk_fngset_row.nyusyusu,
                                         wk_fngset_row.userid,
                                         wk_fngset_row.username,
                                         io_return_code,
                                         io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;
   END IF;

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_stock_data_for_stockin1 (wk_fngset_row.ticket_no,
                                   wk_bucket_no,
                                   wk_fngset_row.unit_weight,
                                   wk_fngset_row.nyusyusu,
                                   wk_fngset_row.memo,
                                   wk_fngset_row.sainyukbn,
                                   wk_fngset_row.bag_flag,
                                   wk_fngset_row.userid,
                                   wk_fngset_row.username,
                                   io_return_code,
                                   io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_locat_data_for_stockin (wk_fngset_row.ticket_no,
                                  wk_mckey,
                                  wk_bucket_no,
                                  io_return_code,
                                  io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_trans_data_for_stockin1 (
      wk_mckey,
      wk_fngset_row.ticket_no,
      ykk_global_defination.transportation_work_type_auto,
      wk_fngset_row.motostno,
      wk_next_station_no,
      wk_fngset_row.endstno,
      io_return_code,
      io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_indc_data_for_stockin (wk_mckey,
                                 wk_fngset_row.ticket_no,
                                 wk_fngset_row.nyusyusu,
                                 io_return_code,
                                 io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_arrival_rpt_for_stockin (wk_fngset_row.motostno,
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
      io_return_code := 8000134;
      io_return_message :=
         '入库正常模式设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockin_1_normality;
/
