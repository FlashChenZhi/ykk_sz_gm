DROP PROCEDURE YKK.STOCK_BACK;

CREATE OR REPLACE PROCEDURE YKK.stock_back (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row         fngset%ROWTYPE;
   wk_start_station_no   fnhanso.startstno%TYPE;
   wk_next_station_no    fnrute.stnoto%TYPE;
   wk_location_no        fnlocat.syozaikey%TYPE;
   wk_mckey              fnhanso.mckey%TYPE;
   wk_trans_type         fnhanso.nyusyukbn%TYPE;
   wk_trans_work_type    fnhanso.sagyokbn%TYPE;
   wk_count              NUMBER;
   wk_systemid           fnhanso.systemid%TYPE;

   PROCEDURE update_stock_data_on_back (
      in_mckey            IN       fnhanso.mckey%TYPE,
      in_ticket_no        IN       fnzaiko.ticket_no%TYPE,
      in_bucket_no        IN       fnzaiko.bucket_no%TYPE,
      in_stockback_num    IN       fnzaiko.zaikosu%TYPE,
      in_memo             IN       fnzaiko.memo%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
      wk_count   NUMBER;
   BEGIN
      SELECT COUNT (*)
        INTO wk_count
        FROM fnhanso
       WHERE mckey != in_mckey AND systemid = (SELECT systemid
                                                 FROM fnhanso
                                                WHERE mckey = in_mckey);

      IF wk_count = 0
      THEN
         UPDATE fnzaiko
            SET bucket_no = in_bucket_no,
                zaikosu = in_stockback_num,
                skanosu = in_stockback_num,
                memo = in_memo,
                remeasure_flag = '0'
          WHERE ticket_no = in_ticket_no;
      ELSE
         UPDATE fnzaiko
            SET bucket_no = in_bucket_no
          WHERE ticket_no = in_ticket_no;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_on_back;

   PROCEDURE update_locat_data_on_back (
      in_mckey            IN       fnhanso.mckey%TYPE,
      in_bucket_no        IN       fnzaiko.bucket_no%TYPE,
      in_location_no      IN       fnlocat.syozaikey%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
      wk_count   NUMBER;
   BEGIN
      SELECT COUNT (*)
        INTO wk_count
        FROM fnhanso
       WHERE mckey != in_mckey AND systemid = (SELECT systemid
                                                 FROM fnhanso
                                                WHERE mckey = in_mckey);

      IF wk_count = 0
      THEN
         UPDATE fnlocat
            SET hikiflg = ykk_global_defination.location_not_dispatched
          WHERE systemid = (SELECT systemid
                              FROM fnhanso
                             WHERE mckey = in_mckey);
      END IF;

      UPDATE fnlocat
         SET bucket_no = in_bucket_no,
             syozaikey = in_location_no,
             zaijyoflg = ykk_global_defination.locat_status_in_reserved,
             ailestno =
                TO_CHAR (TRUNC (    (  TO_NUMBER (SUBSTR (in_location_no, 2,
                                                          3)
                                                 )
                                     + 1
                                    )
                                  / 2
                                + 9000
                               )
                        )
       WHERE systemid = (SELECT systemid
                           FROM fnhanso
                          WHERE mckey = in_mckey);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000112;
         io_return_message := '更新货位数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_locat_data_on_back;

   PROCEDURE update_trans_data_on_back (
      in_mckey              IN       fnhanso.mckey%TYPE,
      in_start_station_no   IN       fnhanso.startstno%TYPE,
      in_end_station_no     IN       fnhanso.endstno%TYPE,
      in_prev_station_no    IN       fnhanso.motostno%TYPE,
      in_next_station_no    IN       fnhanso.sakistno%TYPE,
      in_trans_type         IN       fnhanso.nyusyukbn%TYPE,
      in_bucket_no          IN       fnhanso.bucket_no%TYPE,
      io_return_code        IN OUT   NUMBER,
      io_return_message     IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnhanso
         SET startstno = in_start_station_no,
             endstno = in_end_station_no,
             motostno = in_prev_station_no,
             sakistno = in_next_station_no,
             hjyotaiflg = ykk_global_defination.transportation_status_start,
             sagyokbn = ykk_global_defination.transportation_work_type_back,
             nyusyukbn = in_trans_type,
             setkbn = '2'
       WHERE mckey = in_mckey;

      UPDATE fnhanso
         SET bucket_no = in_bucket_no
       WHERE systemid = (SELECT systemid
                           FROM fnhanso
                          WHERE mckey = in_mckey);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000129;
         io_return_message := '更新搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_trans_data_on_back;

   PROCEDURE update_label_data_on_back (
      in_mckey            IN       fnhanso.mckey%TYPE,
      in_bucket_no        IN       fnhanso.bucket_no%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnlabel
         SET bucket_no = in_bucket_no
       WHERE label_key IN (
                    SELECT label_key
                      FROM fnsiji
                     WHERE mckey IN (
                                     SELECT mckey
                                       FROM fnhanso
                                      WHERE systemid =
                                                     (SELECT systemid
                                                        FROM fnhanso
                                                       WHERE mckey = in_mckey)));
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000143;
         io_return_message := '更新打印标签数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_label_data_on_back;

   PROCEDURE create_result_data_on_cycle (
      in_mckey            IN       fnsiji.mckey%TYPE,
      in_stock_num        IN       fnzaiko.zaikosu%TYPE,
      in_station_no       IN       fnjiseki.nyusyustno%TYPE,
      in_user_id          IN       fnjiseki.userid%TYPE,
      in_user_name        IN       fnjiseki.username%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
      wk_fnjiseki_row   fnjiseki%ROWTYPE;
      wk_fnlocat_row    fnlocat%ROWTYPE;
      wk_fnzaiko_row    fnzaiko%ROWTYPE;
      wk_fmzkey_row     fmzkey%ROWTYPE;
      wk_fnhanso_row    fnhanso%ROWTYPE;
      wk_datetime14     VARCHAR2 (14);
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT *
        INTO wk_fnhanso_row
        FROM fnhanso
       WHERE mckey = in_mckey;

      SELECT *
        INTO wk_fnzaiko_row
        FROM fnzaiko
       WHERE systemid = wk_fnhanso_row.systemid;

      SELECT *
        INTO wk_fnlocat_row
        FROM fnlocat
       WHERE systemid = wk_fnhanso_row.systemid;

      SELECT *
        INTO wk_fmzkey_row
        FROM fmzkey
       WHERE zaikey = wk_fnzaiko_row.zaikey
         AND manage_item_flag = wk_fnzaiko_row.manage_item_flag;

      wk_datetime14 := sysdate14_get ();
      wk_fnjiseki_row.mckey := in_mckey;
      wk_fnjiseki_row.zaikey := wk_fnzaiko_row.zaikey;
      wk_fnjiseki_row.zkname := wk_fmzkey_row.zkname1;
      wk_fnjiseki_row.zkname2 := wk_fmzkey_row.zkname2;
      wk_fnjiseki_row.zkname3 := wk_fmzkey_row.zkname3;
      wk_fnjiseki_row.sakuseihiji := wk_datetime14;
      wk_fnjiseki_row.nyusyukbn := '2';
      wk_fnjiseki_row.sagyokbn := wk_fnhanso_row.sagyokbn;

      IF in_stock_num = wk_fnzaiko_row.zaikosu
      THEN
         wk_fnjiseki_row.sakukbn := ykk_global_defination.stock_amount_equal;
         wk_fnjiseki_row.nyusyusu := 0;
         wk_fnjiseki_row.real_work_number := 0;
      ELSIF in_stock_num > wk_fnzaiko_row.zaikosu
      THEN
         wk_fnjiseki_row.sakukbn :=
                                  ykk_global_defination.stock_amount_increase;
         wk_fnjiseki_row.nyusyusu := in_stock_num - wk_fnzaiko_row.zaikosu;
         wk_fnjiseki_row.real_work_number :=
                                        in_stock_num - wk_fnzaiko_row.zaikosu;
      ELSE
         wk_fnjiseki_row.sakukbn :=
                                  ykk_global_defination.stock_amount_decrease;
         wk_fnjiseki_row.nyusyusu := wk_fnzaiko_row.zaikosu - in_stock_num;
         wk_fnjiseki_row.real_work_number :=
                                        wk_fnzaiko_row.zaikosu - in_stock_num;
      END IF;

      wk_fnjiseki_row.nyusyustno := in_station_no;
      wk_fnjiseki_row.ticket_no := wk_fnzaiko_row.ticket_no;
      wk_fnjiseki_row.bucket_no := wk_fnhanso_row.bucket_no;
      wk_fnjiseki_row.color_code := wk_fnzaiko_row.color_code;
      wk_fnjiseki_row.userid := in_user_id;
      wk_fnjiseki_row.username := in_user_name;
      wk_fnjiseki_row.startstno := in_station_no;
      wk_fnjiseki_row.endstno := wk_fnhanso_row.endstno;
      wk_fnjiseki_row.systemid := wk_fnhanso_row.systemid;
      wk_fnjiseki_row.syozaikey := wk_fnhanso_row.syumotoloc;
      wk_fnjiseki_row.manage_item_flag := wk_fnzaiko_row.manage_item_flag;
      wk_fnjiseki_row.backup_flg := '0';
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

      INSERT INTO fnjiseki
           VALUES wk_fnjiseki_row;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000149;
         io_return_message := '生成出库实绩数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_result_data_on_cycle;

   PROCEDURE update_ctl_data_on_back (
      in_station_no       IN       fnpick_ctl.stno%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnpick_ctl
         SET mckey = ' ',
             bucketreading_flg = ' '
       WHERE stno = in_station_no;

      swith_off_light (in_station_no, io_return_code, io_return_message);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000150;
         io_return_message := '更新拣选控制数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_ctl_data_on_back;
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
    WHERE schno = in_schedule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT mckey
     INTO wk_mckey
     FROM fnpick_ctl
    WHERE stno = wk_fngset_row.motostno;

   exists_empty_location (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT endstno
     INTO wk_start_station_no
     FROM fnhanso
    WHERE mckey = wk_mckey;

   get_empty_location (wk_start_station_no,
                       wk_location_no,
                       io_return_code,
                       io_return_message
                      );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT '9' || LPAD (TO_CHAR (TRUNC ((TO_NUMBER (bankno) + 1) / 2)), 3, '0')
     INTO wk_fngset_row.endstno
     FROM fnakitana
    WHERE syozaikey = wk_location_no;

   get_next_station_no (wk_start_station_no,
                        wk_fngset_row.endstno,
                        wk_start_station_no,
                        wk_next_station_no,
                        io_return_code,
                        io_return_message
                       );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   check_transportation_route (wk_start_station_no,
                               wk_fngset_row.endstno,
                               io_return_code,
                               io_return_message
                              );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT sagyokbn
     INTO wk_trans_work_type
     FROM fnhanso
    WHERE mckey = wk_mckey;

   IF wk_trans_work_type =
                          ykk_global_defination.transportation_work_type_cycle
   THEN
      create_result_data_on_cycle (wk_mckey,
                                   wk_fngset_row.nyusyusu,
                                   wk_fngset_row.motostno,
                                   wk_fngset_row.userid,
                                   wk_fngset_row.username,
                                   io_return_code,
                                   io_return_message
                                  );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END IF;

   IF wk_fngset_row.motostno IN ('2206', '2209', '3202', '3205')
   THEN
      wk_trans_type := ykk_global_defination.transportation_type_stockin;
   ELSE
      wk_trans_type := ykk_global_defination.transportation_type_sttost;
   END IF;

   update_trans_data_on_back (wk_mckey,
                              wk_start_station_no,
                              wk_fngset_row.endstno,
                              wk_start_station_no,
                              wk_next_station_no,
                              wk_trans_type,
                              wk_fngset_row.bucket_no,
                              io_return_code,
                              io_return_message
                             );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_indc_data_for_stockin (wk_mckey,
                                 wk_fngset_row.ticket_no,
                                 wk_fngset_row.nyusyusu,
                                 io_return_code,
                                 io_return_message
                                );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_label_data_on_back (wk_mckey,
                              wk_fngset_row.bucket_no,
                              io_return_code,
                              io_return_message
                             );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT COUNT (*)
     INTO wk_count
     FROM fnhanso
    WHERE mckey != wk_mckey AND systemid = (SELECT systemid
                                              FROM fnhanso
                                             WHERE mckey = wk_mckey);

   IF wk_count = 0
   THEN
      create_diff_rpt_for_stockin (wk_fngset_row.ticket_no,
                                   wk_fngset_row.unit_weight,
                                   wk_fngset_row.nyusyusu,
                                   io_return_code,
                                   io_return_message
                                  );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

--      IF wk_trans_work_type !=
--                          ykk_global_defination.transportation_work_type_cycle
--      THEN
      SELECT systemid
        INTO wk_systemid
        FROM fnhanso
       WHERE mckey = wk_mckey;

      create_stockout_result_data (wk_mckey,
                                   wk_systemid,
                                   wk_fngset_row.motostno,
                                   'E',
                                   wk_fngset_row.nyusyusu,
                                   wk_fngset_row.userid,
                                   wk_fngset_row.username,
                                   io_return_code,
                                   io_return_message
                                  );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
--      END IF;
   END IF;

   update_stock_data_on_back (wk_mckey,
                              wk_fngset_row.ticket_no,
                              wk_fngset_row.bucket_no,
                              wk_fngset_row.nyusyusu,
                              wk_fngset_row.memo,
                              io_return_code,
                              io_return_message
                             );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_locat_data_on_back (wk_mckey,
                              wk_fngset_row.bucket_no,
                              wk_location_no,
                              io_return_code,
                              io_return_message
                             );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_ctl_data_on_back (wk_fngset_row.motostno,
                            io_return_code,
                            io_return_message
                           );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   <<endlabel>>
   IF io_return_code != 0
   THEN
      ROLLBACK;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      ROLLBACK;
      io_return_code := 8000157;
      io_return_message := '回库设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stock_back;
/


