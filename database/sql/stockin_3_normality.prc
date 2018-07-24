DROP PROCEDURE STOCKIN_3_NORMALITY;

CREATE OR REPLACE PROCEDURE     stockin_3_normality (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_fngset_row                    fngset%ROWTYPE;
   --wk_bucket_no                     fntoucyaku.bucket_no%TYPE;
   wk_mckey                         fntoucyaku.mckey%TYPE;
   wk_start_station_no              fnhanso.startstno%TYPE;
   wk_next_station_no               fnrute.stnoto%TYPE;
   wk_weight_report_complete_flag   fnzaiko.weight_report_complete_flag%TYPE;

   PROCEDURE update_stock_data_for_stockin3 (
      in_mckey            IN     fnhanso.mckey%TYPE,
      in_bucket_no        IN     fnzaiko.bucket_no%TYPE,
      in_stockin_num      IN     fngset.nyusyusu%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      wk_weight_report_complete_flag   fnzaiko.weight_report_complete_flag%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   weight_report_complete_flag
        INTO   wk_weight_report_complete_flag
        FROM   fnzaiko
       WHERE   bucket_no = in_bucket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_flat_warehouse;

      IF wk_weight_report_complete_flag =
            ykk_global_defination.weight_report_not_completed
      THEN
         wk_weight_report_complete_flag :=
            ykk_global_defination.weight_report_executing;
      END IF;

      UPDATE   fnzaiko
         SET   zaikosu = in_stockin_num,
               skanosu = in_stockin_num,
               storage_place_flag =
                  ykk_global_defination.storage_place_awc_warehouse,
               weight_report_complete_flag = wk_weight_report_complete_flag
       WHERE   bucket_no = in_bucket_no;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_for_stockin3;

   PROCEDURE update_trans_data_for_stockin3 (
      in_mckey             IN     fnhanso.mckey%TYPE,
      in_bucket_no         IN     fnzaiko.bucket_no%TYPE,
      in_work_type         IN     fnhanso.sagyokbn%TYPE,
      in_from_station_no   IN     fnhanso.motostno%TYPE,
      in_to_station_no     IN     fnhanso.sakistno%TYPE,
      in_end_station_no    IN     fnhanso.endstno%TYPE,
      io_return_code       IN OUT NUMBER,
      io_return_message    IN OUT VARCHAR2
   )
   IS
      wk_systemid   fnhanso.systemid%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   systemid
        INTO   wk_systemid
        FROM   fnzaiko
       WHERE   bucket_no = in_bucket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_awc_warehouse;

      UPDATE   fnhanso
         SET   systemid = wk_systemid,
               sagyokbn = in_work_type,
               motostno = in_from_station_no,
               sakistno = in_to_station_no,
               endstno = in_end_station_no,
               hjyotaiflg = ykk_global_defination.transportation_status_start,
               nyusyukbn = ykk_global_defination.transportation_type_sttost
       WHERE   mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000129;
         io_return_message := '更新搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_trans_data_for_stockin3;

   PROCEDURE create_weight_rpt_for_stockin3 (
      in_bucket_no        IN     fnzaiko.bucket_no%TYPE,
      in_unit_weight      IN     fnzaiko.real_unit_weight%TYPE,
      in_measure_weight   IN     fngset.measure_weight%TYPE,
      in_stockin_num      IN     fngset.nyusyusu%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      wk_fnzaiko_row          fnzaiko%ROWTYPE;
      wk_fhweightreport_row   fhweightreport%ROWTYPE;
      --   wk_move_flag            fnzaiko.move_flag%TYPE;
      --   wk_item_code            fnzaiko.zaikey%TYPE;
      wk_master_unit_weight   fmzkey.master_unit_weight%TYPE;
      wk_datetime14           VARCHAR2 (14);
   --wk_manage_item_flag     fnzaiko.manage_item_flag%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   fnzaiko.bucket_no = in_bucket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_flat_warehouse;

      SELECT   master_unit_weight
        INTO   wk_master_unit_weight
        FROM   fmzkey
       WHERE   zaikey = wk_fnzaiko_row.zaikey
               AND manage_item_flag = wk_fnzaiko_row.manage_item_flag;

      IF wk_master_unit_weight = in_unit_weight
      THEN
         wk_fhweightreport_row.weight_flag := '0';
      ELSE
         wk_fhweightreport_row.weight_flag := '1';
      END IF;

      IF TRIM (wk_fnzaiko_row.ticket_no) IS NULL
      THEN
         wk_fnzaiko_row.ticket_no := ' ';
      END IF;

      wk_fhweightreport_row.ticket_no := wk_fnzaiko_row.ticket_no;
      wk_fhweightreport_row.stock_unit_weight := in_unit_weight;
      wk_fhweightreport_row.measure_qty := in_stockin_num;
      wk_fhweightreport_row.measure_weight := in_measure_weight;
      wk_fhweightreport_row.move_flag := wk_fnzaiko_row.move_flag;
      wk_fhweightreport_row.pending_flag := '0';
      wk_fhweightreport_row.proc_flag := '0';
      wk_datetime14 := sysdate14_get ();
      wk_fhweightreport_row.register_date := SUBSTR (wk_datetime14, 0, 8);
      wk_fhweightreport_row.register_time := SUBSTR (wk_datetime14, 9, 6);

      INSERT INTO fhweightreport
        VALUES   wk_fhweightreport_row;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000132;
         io_return_message := '生成称重报告数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_weight_rpt_for_stockin3;

   PROCEDURE create_diff_rpt_for_stockin3 (
      in_bucket_no        IN     fnzaiko.bucket_no%TYPE,
      in_unit_weight      IN     fnzaiko.real_unit_weight%TYPE,
      in_stockin_num      IN     fngset.nyusyusu%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      wk_fnzaiko_row             fnzaiko%ROWTYPE;
      wk_fhdifferencestock_row   fhdifferencestock%ROWTYPE;
      --   wk_item_code               fnzaiko.zaikey%TYPE;
      --   wk_color_code              fnzaiko.color_code%TYPE;
      --   wk_stock_qty               fnzaiko.zaikosu%TYPE;
      wk_datetime14              VARCHAR2 (14);
      wk_diff_weight             NUMBER (6, 4);
   BEGIN
      io_return_code := 0;
      io_return_message := '';
      wk_diff_weight := 99.9999;

      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   fnzaiko.bucket_no = in_bucket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_flat_warehouse;

      IF TRIM (wk_fnzaiko_row.ticket_no) IS NULL
      THEN
         wk_fnzaiko_row.ticket_no := ' ';
      END IF;

      wk_fhdifferencestock_row.ticket_no := wk_fnzaiko_row.ticket_no;
      wk_fhdifferencestock_row.difference_flag := '1';
      wk_fhdifferencestock_row.item_code := wk_fnzaiko_row.zaikey;
      wk_fhdifferencestock_row.color_code := wk_fnzaiko_row.color_code;

      IF in_stockin_num != wk_fnzaiko_row.zaikosu
      THEN
         IF in_stockin_num < wk_fnzaiko_row.zaikosu
         THEN
            wk_fhdifferencestock_row.difference_type :=
               ykk_global_defination.difference_type_sub;
            wk_fhdifferencestock_row.difference_qty :=
               wk_fnzaiko_row.zaikosu - in_stockin_num;
         ELSE
            wk_fhdifferencestock_row.difference_type :=
               ykk_global_defination.difference_type_add;
            wk_fhdifferencestock_row.difference_qty :=
               in_stockin_num - wk_fnzaiko_row.zaikosu;
         END IF;

         IF wk_diff_weight >
               in_unit_weight * wk_fhdifferencestock_row.difference_qty
         THEN
            wk_fhdifferencestock_row.difference_weight :=
               in_unit_weight * wk_fhdifferencestock_row.difference_qty;
         ELSE
            wk_fhdifferencestock_row.difference_weight := wk_diff_weight;
         END IF;

         wk_fhdifferencestock_row.proc_flag := '0';
         wk_datetime14 := sysdate14_get ();
         wk_fhdifferencestock_row.register_date :=
            SUBSTR (wk_datetime14, 0, 8);
         wk_fhdifferencestock_row.register_time :=
            SUBSTR (wk_datetime14, 9, 6);

         INSERT INTO fhdifferencestock
           VALUES   wk_fhdifferencestock_row;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000133;
         io_return_message := '生成在库差异数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_diff_rpt_for_stockin3;

   PROCEDURE create_locat_data_for_stockin3 (
      in_bucket_no        IN     fngset.bucket_no%TYPE,
      in_mckey            IN     fnhanso.mckey%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      wk_systemid   fnhanso.systemid%TYPE;
      wk_hansokey   fnhanso.hansokey%TYPE;
   BEGIN
      SELECT   hansokey
        INTO   wk_hansokey
        FROM   fnhanso
       WHERE   mckey = in_mckey;

      SELECT   systemid
        INTO   wk_systemid
        FROM   fnzaiko
       WHERE   bucket_no = in_bucket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_awc_warehouse;

      INSERT INTO fnlocat (syozaikey,
                           hansokey,
                           sokokbn,
                           bucket_no,
                           zaijyoflg,
                           shikiflg,
                           hikiflg,
                           accessflg,
                           systemid)
        VALUES   (' ',
                  wk_hansokey,
                  ykk_global_defination.warehouse_type_auto,
                  in_bucket_no,
                  ykk_global_defination.locat_status_in_reserved,
                  ykk_global_defination.location_available,
                  ykk_global_defination.location_not_dispatched,
                  ykk_global_defination.location_accessable,
                  wk_systemid);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000128;
         io_return_message := '生成货位数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_locat_data_for_stockin3;

   PROCEDURE update_indc_data_for_stockin3 (
      in_mckey            IN     fnhanso.mckey%TYPE,
      in_bucket_no        IN     fnzaiko.bucket_no%TYPE,
      in_stockin_num      IN     fnsiji.nyusyusu%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      wk_fnzaiko_row   fnzaiko%ROWTYPE;
      wk_hansokey      fnsiji.hansokey%TYPE;
   BEGIN
      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   bucket_no = in_bucket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_awc_warehouse;

      SELECT   hansokey
        INTO   wk_hansokey
        FROM   fnhanso
       WHERE   mckey = in_mckey;

      IF TRIM (wk_fnzaiko_row.ticket_no) IS NULL
      THEN
         wk_fnzaiko_row.ticket_no := ' ';
      END IF;

      UPDATE   fnsiji
         SET   hansokey = wk_hansokey,
               ticket_no = wk_fnzaiko_row.ticket_no,
               zaikey = wk_fnzaiko_row.zaikey,
               color_code = wk_fnzaiko_row.color_code,
               nyusyusu = in_stockin_num,
               section = wk_fnzaiko_row.made_section,
               line = wk_fnzaiko_row.made_line,
               manage_item_flag = wk_fnzaiko_row.manage_item_flag
       WHERE   mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000130;
         io_return_message := '更新指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_indc_data_for_stockin3;

   PROCEDURE create_result_data_on_stockin3 (
      in_mckey             IN     fnsiji.mckey%TYPE,
      in_bucket_no         IN     fnzaiko.bucket_no%TYPE,
      in_station_no        IN     fnjiseki.nyusyustno%TYPE,
      in_trans_work_type   IN     fnhanso.sagyokbn%TYPE,
      in_unit_weight       IN     fnzaiko.real_unit_weight%TYPE,
      in_stockin_num       IN     NUMBER,
      in_user_id           IN     fnjiseki.userid%TYPE,
      in_user_name         IN     fnjiseki.username%TYPE,
      io_return_code       IN OUT NUMBER,
      io_return_message    IN OUT VARCHAR2
   )
   IS
      CURSOR indc_list (c_mckey IN fnsiji.mckey%TYPE)
      IS
         SELECT   *
           FROM   fnsiji
          WHERE   mckey = c_mckey;

      indc_list_iterator   indc_list%ROWTYPE;
      wk_fnjiseki_row      fnjiseki%ROWTYPE;
      wk_fnsyotei_row      fnsyotei%ROWTYPE;
      wk_fnzaiko_row       fnzaiko%ROWTYPE;
      wk_fmzkey_row        fmzkey%ROWTYPE;
      wk_fnhanso_row       fnhanso%ROWTYPE;
      wk_count             NUMBER;
      wk_datetime14        VARCHAR2 (14);
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   bucket_no = in_bucket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_flat_warehouse;

      IF wk_fnzaiko_row.zaikosu != in_stockin_num
      THEN
         GOTO endlabel;
      END IF;

      SELECT   *
        INTO   wk_fnhanso_row
        FROM   fnhanso
       WHERE   mckey = in_mckey;

      SELECT   *
        INTO   wk_fmzkey_row
        FROM   fmzkey
       WHERE   zaikey = wk_fnzaiko_row.zaikey
               AND manage_item_flag = wk_fnzaiko_row.manage_item_flag;

      OPEN indc_list (in_mckey);

      LOOP
         FETCH indc_list INTO   indc_list_iterator;

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

         SELECT   COUNT ( * )
           INTO   wk_count
           FROM   fnsyotei
          WHERE   retrieval_plankey = indc_list_iterator.retrieval_plan_key;

         IF wk_count > 0
         THEN
            SELECT   *
              INTO   wk_fnsyotei_row
              FROM   fnsyotei
             WHERE   retrieval_plankey =
                        indc_list_iterator.retrieval_plan_key;

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
           VALUES   wk_fnjiseki_row;
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
         io_return_message := '生成实绩数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_result_data_on_stockin3;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT   *
     INTO   wk_fngset_row
     FROM   fngset
    WHERE   schno = in_schedule_no
            AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT   mckey
     INTO   wk_mckey
     FROM   fntoucyaku
    WHERE   stno = wk_fngset_row.motostno
            AND syoriflg =
                  ykk_global_defination.arrival_report_processed_stg1;

   --    check_bucket_not_instock (wk_bucket_no, io_return_code, io_return_message);
   --
   --    IF io_return_code != 0
   --    THEN
   --       GOTO endlabel;
   --    END IF;
   exists_empty_location (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT   startstno
     INTO   wk_start_station_no
     FROM   fnhanso
    WHERE   mckey = wk_mckey;

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

   SELECT   real_unit_weight, weight_report_complete_flag
     INTO   wk_fngset_row.unit_weight, wk_weight_report_complete_flag
     FROM   fnzaiko
    WHERE   bucket_no = wk_fngset_row.bucket_no
            AND storage_place_flag =
                  ykk_global_defination.storage_place_flat_warehouse;

   IF wk_weight_report_complete_flag !=
         ykk_global_defination.weight_report_executing
   THEN
      IF wk_weight_report_complete_flag =
            ykk_global_defination.weight_report_not_completed
      THEN
         create_weight_rpt_for_stockin3 (wk_fngset_row.bucket_no,
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
         create_diff_rpt_for_stockin3 (wk_fngset_row.bucket_no,
                                       wk_fngset_row.unit_weight,
                                       wk_fngset_row.nyusyusu,
                                       io_return_code,
                                       io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;

         create_result_data_on_stockin3 (wk_mckey,
                                         wk_fngset_row.bucket_no,
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

   update_stock_data_for_stockin3 (wk_mckey,
                                   wk_fngset_row.bucket_no,
                                   wk_fngset_row.nyusyusu,
                                   io_return_code,
                                   io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_locat_data_for_stockin3 (wk_fngset_row.bucket_no,
                                   wk_mckey,
                                   io_return_code,
                                   io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_trans_data_for_stockin3 (
      wk_mckey,
      wk_fngset_row.bucket_no,
      ykk_global_defination.transportation_work_type_plan,
      wk_fngset_row.motostno,
      wk_next_station_no,
      wk_fngset_row.endstno,
      io_return_code,
      io_return_message
   );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_indc_data_for_stockin3 (wk_mckey,
                                  wk_fngset_row.bucket_no,
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
      io_return_message := '入库正常模式设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockin_3_normality; 

/
