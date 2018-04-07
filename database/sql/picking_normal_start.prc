DROP PROCEDURE PICKING_NORMAL_START;

CREATE OR REPLACE PROCEDURE     picking_normal_start (
   in_schdule_no       IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2)
IS
   wk_fngset_row          fngset%ROWTYPE;
   wk_retrieval_station   fnretrieval_st.retrieval_station%TYPE;
   wk_retrieval_st_row    fnretrieval_st%ROWTYPE;
   wk_next_station_no     fnhanso.sakistno%TYPE;
   wk_old_mckey           fnhanso.mckey%TYPE;
   wk_new_mckey           fnhanso.mckey%TYPE;
   wk_picking_type        CHAR (1);
   wk_systemid            fnhanso.systemid%TYPE;
   wk_new_systemid        fnhanso.systemid%TYPE;
   wk_hansokey            fnhanso.hansokey%TYPE;
   wk_count               NUMBER;
   --wk_fnlocat_row         fnlocat%ROWTYPE;
   wk_bucket_no           fnhanso.bucket_no%TYPE;
   wk_trans_work_type     fnhanso.sagyokbn%TYPE;
   wk_start_station_no    fnhanso.startstno%TYPE;

   PROCEDURE update_indc_data_on_normal (
      in_retrieval_station   IN     fnsiji.retrieval_station%TYPE,
      in_old_mckey           IN     fnsiji.mckey%TYPE,
      in_new_mckey           IN     fnsiji.mckey%TYPE,
      in_hansokey            IN     fnsiji.hansokey%TYPE,
      io_return_code         IN OUT NUMBER,
      io_return_message      IN OUT VARCHAR2)
   IS
      wk_retrieval_station   fnretrieval_st.retrieval_station%TYPE;
      wk_section             fnsiji.section%TYPE;
      wk_line                fnsiji.line%TYPE;
      wk_customer_code       fnsiji.customer_code%TYPE;
      wk_order_no            fnsyotei.order_no%TYPE;
      wk_picking_num         NUMBER;
   BEGIN
      io_return_code := 0;
      io_return_message := '';


      SELECT COUNT (*)
        INTO wk_count
        FROM fnsiji
       WHERE mckey = wk_old_mckey AND subdivide_flag = '1';

      IF wk_count > 0
      THEN
         UPDATE fnsiji
            SET mckey = in_new_mckey, hansokey = in_hansokey
          WHERE ROWID = (SELECT *
                           FROM (  SELECT ROWID
                                     FROM fnsiji
                                    WHERE     mckey = wk_old_mckey
                                          AND subdivide_flag = '1'
                                 ORDER BY nyusyusu)
                          WHERE ROWNUM = 1);
      ELSE
         IF in_retrieval_station IN
               (ykk_global_defination.retrieval_station_plating,
                ykk_global_defination.retrieval_station_si,
                ykk_global_defination.retrieval_station_painting)
         THEN
            SELECT section,
                   line,
                   picking_num,
                   retrieval_station
              INTO wk_section,
                   wk_line,
                   wk_picking_num,
                   wk_retrieval_station
              FROM (  SELECT section,
                             line,
                             retrieval_station,
                             SUM (fnsiji.nyusyusu) AS picking_num
                        FROM fnsiji
                       WHERE mckey = in_old_mckey
                    GROUP BY section, line, retrieval_station
                    ORDER BY picking_num ASC)
             WHERE ROWNUM = 1;

            UPDATE fnsiji
               SET mckey = in_new_mckey, hansokey = in_hansokey
             WHERE     mckey = in_old_mckey
                   AND section = wk_section
                   AND line = wk_line
                   AND retrieval_station = wk_retrieval_station;
         ELSIF in_retrieval_station =
                  ykk_global_defination.retrieval_station_assembling
         THEN
            SELECT section, retrieval_station, picking_num
              INTO wk_section, wk_retrieval_station, wk_picking_num
              FROM (  SELECT section,
                             retrieval_station,
                             SUM (fnsiji.nyusyusu) AS picking_num
                        FROM fnsiji
                       WHERE mckey = in_old_mckey
                    GROUP BY section, retrieval_station
                    ORDER BY picking_num ASC)
             WHERE ROWNUM = 1;

            UPDATE fnsiji
               SET mckey = in_new_mckey, hansokey = in_hansokey
             WHERE     mckey = in_old_mckey
                   AND section = wk_section
                   AND retrieval_station = wk_retrieval_station;
         ELSIF in_retrieval_station IN
                  (ykk_global_defination.retrieval_station_completing31,
                   ykk_global_defination.retrieval_station_completing32,
                   ykk_global_defination.retrieval_station_completing41,
                   ykk_global_defination.retrieval_station_completing42,
                   ykk_global_defination.retrieval_station_MF,
                   ykk_global_defination.retrieval_station_PF,
                   ykk_global_defination.retrieval_station_VF)
         THEN
            SELECT retrieval_station, section, picking_num
              INTO wk_retrieval_station, wk_section, wk_picking_num
              FROM (  SELECT retrieval_station,
                             section,
                             SUM (fnsiji.nyusyusu) AS picking_num
                        FROM fnsiji
                       WHERE mckey = in_old_mckey
                    GROUP BY retrieval_station, section
                    ORDER BY picking_num ASC)
             WHERE ROWNUM = 1;

            UPDATE fnsiji
               SET mckey = in_new_mckey, hansokey = in_hansokey
             WHERE     mckey = in_old_mckey
                   AND retrieval_station = wk_retrieval_station
                   AND section = wk_section;
         ELSIF in_retrieval_station IN
                  (ykk_global_defination.retrieval_station_fuyong,
                   ykk_global_defination.retrieval_station_external)
         THEN
            SELECT customer_code, retrieval_station, picking_num
              INTO wk_customer_code, wk_retrieval_station, wk_picking_num
              FROM (  SELECT customer_code,
                             retrieval_station,
                             SUM (fnsiji.nyusyusu) AS picking_num
                        FROM fnsiji
                       WHERE mckey = in_old_mckey
                    GROUP BY customer_code, retrieval_station
                    ORDER BY picking_num ASC)
             WHERE ROWNUM = 1;

            UPDATE fnsiji
               SET mckey = in_new_mckey, hansokey = in_hansokey
             WHERE     mckey = in_old_mckey
                   AND customer_code = wk_customer_code
                   AND retrieval_station = wk_retrieval_station;
         ELSE
            SELECT customer_code,
                   retrieval_station,
                   order_no,
                   picking_num
              INTO wk_customer_code,
                   wk_retrieval_station,
                   wk_order_no,
                   wk_picking_num
              FROM (  SELECT fnsiji.customer_code,
                             fnsiji.retrieval_station,
                             fnsyotei.order_no,
                             SUM (fnsiji.nyusyusu) AS picking_num
                        FROM fnsiji, fnsyotei
                       WHERE     fnsiji.retrieval_plan_key =
                                    fnsyotei.retrieval_plankey
                             AND mckey = in_old_mckey
                    GROUP BY fnsiji.customer_code,
                             fnsiji.retrieval_station,
                             fnsyotei.order_no
                    ORDER BY picking_num ASC)
             WHERE ROWNUM = 1;

            UPDATE fnsiji
               SET mckey = in_new_mckey, hansokey = in_hansokey
             WHERE     mckey = in_old_mckey
                   AND customer_code = wk_customer_code
                   AND retrieval_station = wk_retrieval_station
                   AND wk_order_no =
                          (SELECT order_no
                             FROM fnsyotei
                            WHERE fnsyotei.retrieval_plankey =
                                     fnsiji.retrieval_plan_key);
         END IF;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000130;
         io_return_message :=
            '更新指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_indc_data_on_normal;

   PROCEDURE update_trans_data_on_normal (
      in_mckey              IN     fnhanso.mckey%TYPE,
      in_start_station_no   IN     fnhanso.startstno%TYPE,
      in_end_station_no     IN     fnhanso.endstno%TYPE,
      in_prev_station_no    IN     fnhanso.motostno%TYPE,
      in_next_station_no    IN     fnhanso.sakistno%TYPE,
      io_return_code        IN OUT NUMBER,
      io_return_message     IN OUT VARCHAR2)
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnhanso
         SET startstno = in_start_station_no,
             endstno = in_end_station_no,
             motostno = in_prev_station_no,
             sakistno = in_next_station_no,
             hjyotaiflg = ykk_global_defination.transportation_status_start
       WHERE mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000129;
         io_return_message :=
            '更新搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_trans_data_on_normal;

   PROCEDURE create_indc_data_on_normal (
      in_mckey            IN     fnhanso.mckey%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2)
   IS
      wk_fnzaiko_row   fnzaiko%ROWTYPE;
      wk_hansokey      fnsiji.hansokey%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT *
        INTO wk_fnzaiko_row
        FROM fnzaiko
       WHERE systemid = (SELECT systemid
                           FROM fnhanso
                          WHERE mckey = in_mckey);

      SELECT hansokey
        INTO wk_hansokey
        FROM fnhanso
       WHERE mckey = in_mckey;

      INSERT INTO fnsiji (hansokey,
                          mckey,
                          ticket_no,
                          zaikey,
                          color_code,
                          nyusyusu,
                          retrieval_station,
                          depot_code,
                          manage_item_flag)
           VALUES (wk_hansokey,
                   in_mckey,
                   wk_fnzaiko_row.ticket_no,
                   wk_fnzaiko_row.zaikey,
                   wk_fnzaiko_row.color_code,
                   wk_fnzaiko_row.zaikosu,
                   ykk_global_defination.retrieval_station_warehouse,
                   wk_fnzaiko_row.depot_code,
                   wk_fnzaiko_row.manage_item_flag);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000142;
         io_return_message :=
            '生成指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_indc_data_on_normal;

   PROCEDURE update_label_data_on_normal (
      in_mckey            IN     fnhanso.mckey%TYPE,
      in_bucket_no        IN     fnhanso.bucket_no%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2)
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnlabel
         SET bucket_no = in_bucket_no
       WHERE label_key IN (SELECT label_key
                             FROM fnsiji
                            WHERE mckey = in_mckey);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000143;
         io_return_message :=
            '更新打印标签数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_label_data_on_normal;

   PROCEDURE update_stock_data_for_normal (
      in_systemid         IN     fnhanso.systemid%TYPE,
      in_picking_num      IN     NUMBER,
      in_userid           IN     fnzaiko.userid%TYPE,
      in_username         IN     fnzaiko.username%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2)
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnzaiko
         SET zaikosu = zaikosu - in_picking_num,
             userid = in_userid,
             username = in_username
       WHERE systemid = in_systemid;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message :=
            '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_for_normal;

   PROCEDURE create_out_result_on_normal (
      in_mckey            IN     fnsiji.mckey%TYPE,
      in_systemid         IN     fnzaiko.systemid%TYPE,
      in_station_no       IN     fnjiseki.nyusyustno%TYPE,
      in_bucket_no        IN     fnhanso.bucket_no%TYPE,
      in_user_id          IN     fnjiseki.userid%TYPE,
      in_user_name        IN     fnjiseki.username%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2)
   IS
      CURSOR indc_list (c_mckey IN fnsiji.mckey%TYPE)
      IS
         SELECT *
           FROM fnsiji
          WHERE mckey = c_mckey;

      indc_list_iterator   indc_list%ROWTYPE;
      wk_fnjiseki_row      fnjiseki%ROWTYPE;
      wk_fnlocat_row       fnlocat%ROWTYPE;
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
       WHERE systemid = in_systemid;

      SELECT *
        INTO wk_fnlocat_row
        FROM fnlocat
       WHERE systemid = in_systemid;

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
         wk_fnjiseki_row.measure_unit_weight :=
            wk_fnzaiko_row.real_unit_weight;
         wk_fnjiseki_row.zkname := wk_fmzkey_row.zkname1;
         wk_fnjiseki_row.zkname2 := wk_fmzkey_row.zkname2;
         wk_fnjiseki_row.zkname3 := wk_fmzkey_row.zkname3;
         wk_fnjiseki_row.sakuseihiji := wk_datetime14;
         wk_fnjiseki_row.nyusyukbn := '2';
         wk_fnjiseki_row.sagyokbn := wk_fnhanso_row.sagyokbn;
         wk_fnjiseki_row.sakukbn :=
            ykk_global_defination.stock_amount_decrease;
         wk_fnjiseki_row.nyusyustno := in_station_no;
         wk_fnjiseki_row.ticket_no := wk_fnzaiko_row.ticket_no;
         wk_fnjiseki_row.bucket_no := in_bucket_no;
         wk_fnjiseki_row.color_code := wk_fnzaiko_row.color_code;
         wk_fnjiseki_row.nyusyusu := indc_list_iterator.nyusyusu;
         wk_fnjiseki_row.real_work_number := indc_list_iterator.nyusyusu;
         wk_fnjiseki_row.userid := in_user_id;
         wk_fnjiseki_row.username := in_user_name;
         wk_fnjiseki_row.startstno := wk_fnhanso_row.startstno;
         wk_fnjiseki_row.endstno := wk_fnhanso_row.endstno;
         wk_fnjiseki_row.systemid := in_systemid;
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
   END create_out_result_on_normal;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT *
     INTO wk_fngset_row
     FROM fngset
    WHERE     schno = in_schdule_no
          AND syoriflg = ykk_global_defination.display_info_not_processed;

   check_bucket_not_instock (
      wk_fngset_row.bucket_no,
      ykk_global_defination.storage_place_awc_warehouse,
      io_return_code,
      io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   check_bucket_not_instock (
      wk_fngset_row.bucket_no,
      ykk_global_defination.storage_place_flat_warehouse,
      io_return_code,
      io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT mckey
     INTO wk_old_mckey
     FROM fnpick_ctl
    WHERE stno = wk_fngset_row.motostno;

   SELECT systemid
     INTO wk_systemid
     FROM fnhanso
    WHERE mckey = wk_old_mckey;

   SELECT retrieval_station
     INTO wk_retrieval_station
     FROM fnsiji
    WHERE mckey = 'TERM' || wk_fngset_row.motostno AND ROWNUM = 1;

   SELECT *
     INTO wk_retrieval_st_row
     FROM fnretrieval_st
    WHERE retrieval_station = wk_retrieval_station;

   get_next_station_no (wk_retrieval_st_row.rev_pickstno,
                        wk_retrieval_st_row.remove_stno,
                        wk_retrieval_st_row.rev_pickstno,
                        wk_next_station_no,
                        io_return_code,
                        io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   check_transportation_route (wk_retrieval_st_row.rev_pickstno,
                               wk_retrieval_st_row.remove_stno,
                               io_return_code,
                               io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   generate_mckey (ykk_global_defination.stock_type_stockout,
                   wk_new_mckey,
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

   SELECT sagyokbn
     INTO wk_trans_work_type
     FROM fnhanso
    WHERE mckey = wk_old_mckey;

   generate_system_id (wk_new_systemid, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_trans_for_new_bucket (wk_new_mckey,
                                wk_hansokey,
                                wk_fngset_row.bucket_no,
                                wk_retrieval_st_row.rev_pickstno,
                                wk_retrieval_st_row.remove_stno,
                                wk_retrieval_st_row.rev_pickstno,
                                wk_next_station_no,
                                wk_new_systemid,
                                wk_trans_work_type,
                                io_return_code,
                                io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   UPDATE fnhanso
      SET hjyotaiflg = ykk_global_defination.transportation_status_standby
    WHERE mckey = wk_new_mckey;

   update_indc_data_on_normal (wk_retrieval_station,
                               wk_old_mckey,
                               wk_new_mckey,
                               wk_hansokey,
                               io_return_code,
                               io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT systemid
     INTO wk_systemid
     FROM fnhanso
    WHERE mckey = wk_old_mckey;

   SELECT SUM (nyusyusu)
     INTO wk_fngset_row.nyusyusu
     FROM fnsiji
    WHERE mckey = wk_new_mckey;

   update_stock_data_for_normal (wk_systemid,
                                 wk_fngset_row.nyusyusu,
                                 wk_fngset_row.userid,
                                 wk_fngset_row.username,
                                 io_return_code,
                                 io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_locat_data_for_picking (wk_systemid,
                                  io_return_code,
                                  io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_retrieval_result (wk_new_mckey,
                            wk_systemid,
                            io_return_code,
                            io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT bucket_no
     INTO wk_bucket_no
     FROM fnhanso
    WHERE mckey = wk_old_mckey;

   create_out_result_on_normal (wk_new_mckey,
                                wk_systemid,
                                wk_fngset_row.motostno,
                                wk_bucket_no,
                                wk_fngset_row.userid,
                                wk_fngset_row.username,
                                io_return_code,
                                io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   --   update_label_data_for_printing (wk_new_mckey,
   --                                   wk_fngset_row.printer_no,
   --                                   io_return_code,
   --                                   io_return_message
   --                                  );

   --   IF io_return_code != 0
   --   THEN
   --      GOTO endlabel;
   --   END IF;
   update_label_data_on_normal (wk_new_mckey,
                                wk_fngset_row.bucket_no,
                                io_return_code,
                                io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   get_picking_type (wk_old_mckey,
                     wk_picking_type,
                     io_return_code,
                     io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   IF    wk_picking_type = ykk_global_defination.picking_type_converse
      OR wk_picking_type = ykk_global_defination.picking_type_total
      OR wk_picking_type = ykk_global_defination.picking_type_back
   THEN
      --      SELECT *
      --        INTO wk_fnlocat_row
      --        FROM fnlocat
      --       WHERE systemid = wk_systemid;
      SELECT startstno
        INTO wk_start_station_no
        FROM fnhanso
       WHERE mckey = wk_old_mckey;

      get_next_station_no (wk_start_station_no,
                           wk_retrieval_st_row.rev_pickstno,
                           wk_retrieval_st_row.pick_stno,
                           wk_next_station_no,
                           io_return_code,
                           io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      check_transportation_route (wk_retrieval_st_row.pick_stno,
                                  wk_next_station_no,
                                  io_return_code,
                                  io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      update_trans_data_on_normal (wk_old_mckey,
                                   wk_start_station_no,
                                   wk_retrieval_st_row.rev_pickstno,
                                   wk_retrieval_st_row.pick_stno,
                                   wk_next_station_no,
                                   io_return_code,
                                   io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      IF wk_picking_type = ykk_global_defination.picking_type_back
      THEN
         create_indc_data_on_normal (wk_old_mckey,
                                     io_return_code,
                                     io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;
   END IF;

  <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000151;
      io_return_message := '正拣选设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END picking_normal_start;

/
