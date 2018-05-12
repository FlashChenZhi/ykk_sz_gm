DROP PROCEDURE AWC.PICKING_ABNORMAL_START;

CREATE OR REPLACE PROCEDURE AWC.picking_abnormal_start (
   in_schdule_no       IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_fngset_row            fngset%ROWTYPE;
   wk_mckey                 fnhanso.mckey%TYPE;
   wk_retrieval_station     fnretrieval_st.retrieval_station%TYPE;
   wk_retrieval_st_row      fnretrieval_st%ROWTYPE;
   wk_next_station_no       fnhanso.sakistno%TYPE;
   wk_systemid              fnhanso.systemid%TYPE;
   wk_bucket_reading_flag   fnpick_ctl.bucketreading_flg%TYPE;

   PROCEDURE update_stock_data_on_abnormal (
      in_systemid         IN     fnhanso.systemid%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE   fnzaiko
         SET   skanosu = zaikosu,
               bucket_no = ' ',
               storage_place_flag =
                  ykk_global_defination.storage_place_flat_warehouse
       WHERE   systemid = in_systemid;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_on_abnormal;

   PROCEDURE create_result_data_on_abnormal (
      in_mckey              IN     fnsiji.mckey%TYPE,
      in_systemid           IN     fnzaiko.systemid%TYPE,
      in_start_station_no   IN     fnjiseki.startstno%TYPE,
      in_end_station_no     IN     fnjiseki.endstno%TYPE,
      in_moto_station_no    IN     fnjiseki.nyusyustno%TYPE,
      in_user_id            IN     fnjiseki.userid%TYPE,
      in_user_name          IN     fnjiseki.username%TYPE,
      io_return_code        IN OUT NUMBER,
      io_return_message     IN OUT VARCHAR2
   )
   IS
      CURSOR indc_list (c_mckey IN fnsiji.mckey%TYPE)
      IS
         SELECT   *
           FROM   fnsiji
          WHERE   mckey = c_mckey;

      indc_list_iterator   indc_list%ROWTYPE;
      wk_fnjiseki_row      fnjiseki%ROWTYPE;
      wk_fnlocat_row       fnlocat%ROWTYPE;
      wk_fnsyotei_row      fnsyotei%ROWTYPE;
      wk_fnzaiko_row       fnzaiko%ROWTYPE;
      wk_fmzkey_row        fmzkey%ROWTYPE;
      wk_fnhanso_row       fnhanso%ROWTYPE;
      --wk_systemid          fnjiseki.systemid%TYPE;
      wk_datetime14        VARCHAR2 (14);
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   systemid = in_systemid;

      SELECT   *
        INTO   wk_fnlocat_row
        FROM   fnlocat
       WHERE   systemid = in_systemid;

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

         SELECT   *
           INTO   wk_fnsyotei_row
           FROM   fnsyotei
          WHERE   retrieval_plankey = indc_list_iterator.retrieval_plan_key;

         wk_datetime14 := sysdate14_get ();
         wk_fnjiseki_row.mckey := in_mckey;
         wk_fnjiseki_row.zaikey := wk_fnzaiko_row.zaikey;
         wk_fnjiseki_row.measure_unit_weight :=
            wk_fnzaiko_row.real_unit_weight;
         wk_fnjiseki_row.zkname := wk_fmzkey_row.zkname1;
         wk_fnjiseki_row.zkname2 := wk_fmzkey_row.zkname2;
         wk_fnjiseki_row.zkname3 := wk_fmzkey_row.zkname3;
         wk_fnjiseki_row.sakuseihiji := wk_datetime14;
         wk_fnjiseki_row.nyusyukbn := wk_fnhanso_row.nyusyukbn;
         wk_fnjiseki_row.sagyokbn := 'D';
         wk_fnjiseki_row.sakukbn :=
            ykk_global_defination.stock_amount_decrease;
         wk_fnjiseki_row.nyusyustno := in_moto_station_no;
         wk_fnjiseki_row.ticket_no := wk_fnzaiko_row.ticket_no;
         wk_fnjiseki_row.bucket_no := wk_fnhanso_row.bucket_no;
         wk_fnjiseki_row.color_code := wk_fnzaiko_row.color_code;
         wk_fnjiseki_row.nyusyusu := indc_list_iterator.nyusyusu;
         wk_fnjiseki_row.retrieval_no := wk_fnsyotei_row.retrieval_no;
         wk_fnjiseki_row.real_work_number := indc_list_iterator.nyusyusu;
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
         wk_fnjiseki_row.retrieval_qty := wk_fnsyotei_row.retrieval_qty;
         wk_fnjiseki_row.userid := in_user_id;
         wk_fnjiseki_row.username := in_user_name;
         wk_fnjiseki_row.startstno := in_start_station_no;
         wk_fnjiseki_row.endstno := in_end_station_no;
         wk_fnjiseki_row.systemid := in_systemid;
         wk_fnjiseki_row.syozaikey := wk_fnlocat_row.syozaikey;
         wk_fnjiseki_row.manage_item_flag := wk_fnzaiko_row.manage_item_flag;
         wk_fnjiseki_row.backup_flg := '0';

         INSERT INTO fnjiseki
           VALUES   wk_fnjiseki_row;
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
         io_return_message := '生成出库实绩数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_result_data_on_abnormal;

   PROCEDURE update_rsv_data_on_abnormal (
      in_mckey            IN     fnsiji.mckey%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      --声明游标，拿到对应一条搬送的所有的预约的retrieval_plan_key
      CURSOR plankey_list (in_mckey IN fnsiji.mckey%TYPE)
      IS
         SELECT   DISTINCT (retrieval_plan_key)
           FROM   fnsiji
          WHERE   mckey = in_mckey;

      plankey_list_iterator   plankey_list%ROWTYPE;
      wk_count                NUMBER;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      --打开游标
            OPEN plankey_list (in_mckey);

      --开始循环
      LOOP
         --得到一条数据
         FETCH plankey_list INTO   plankey_list_iterator;

         --没有数据时退出循环
         IF plankey_list%NOTFOUND = TRUE
         THEN
            EXIT;
         END IF;

         --写log
         IF ykk_global_defination.write_fnsyotei_log_flag != '0'
         THEN
            write_fnsyotei_log (plankey_list_iterator.retrieval_plan_key,
                                'before picking_abnormal',
                                'worgsoft',
                                io_return_code,
                                io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;
         END IF;

         --sum搬运数
         SELECT   SUM (nyusyusu)
           INTO   wk_count
           FROM   fnsiji
          WHERE   mckey = in_mckey
                  AND retrieval_plan_key =
                        plankey_list_iterator.retrieval_plan_key;

         -- 更新预约数据中的已分配数
         UPDATE   fnsyotei
            SET   retrieval_alloc_qty = retrieval_alloc_qty - wk_count,
                  proc_flag =
                     ykk_global_defination.reservation_info_not_processed
          WHERE   retrieval_plankey =
                     plankey_list_iterator.retrieval_plan_key;

         --写log
         IF ykk_global_defination.write_fnsyotei_log_flag != '0'
         THEN
            write_fnsyotei_log (plankey_list_iterator.retrieval_plan_key,
                                'after picking_abnormal',
                                'worgsoft',
                                io_return_code,
                                io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;
         END IF;
      END LOOP;

     --关闭游标
     <<endlabel>>
      IF plankey_list%ISOPEN = TRUE
      THEN
         CLOSE plankey_list;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         IF plankey_list%ISOPEN = TRUE
         THEN
            CLOSE plankey_list;
         END IF;

         io_return_code := 8000110;
         io_return_message := '更新出库预约数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_rsv_data_on_abnormal;

   PROCEDURE delete_trans_data_on_abnormal (
      in_mckey            IN     fnhanso.mckey%TYPE,
      in_systemid         IN     fnhanso.systemid%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      DELETE   fnhanso
       WHERE   systemid = in_systemid AND mckey != in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000187;
         io_return_message := '删除搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END delete_trans_data_on_abnormal;

   PROCEDURE update_trans_data_on_abnormal (
      in_mckey              IN     fnhanso.mckey%TYPE,
      in_start_station_no   IN     fnhanso.startstno%TYPE,
      in_end_station_no     IN     fnhanso.endstno%TYPE,
      in_prev_station_no    IN     fnhanso.motostno%TYPE,
      in_next_station_no    IN     fnhanso.sakistno%TYPE,
      io_systemid           IN OUT fnhanso.systemid%TYPE,
      io_return_code        IN OUT NUMBER,
      io_return_message     IN OUT VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   systemid
        INTO   io_systemid
        FROM   fnhanso
       WHERE   mckey = in_mckey;

      UPDATE   fnhanso
         SET   startstno = in_start_station_no,
               endstno = in_end_station_no,
               motostno = in_prev_station_no,
               sakistno = in_next_station_no,
               hjyotaiflg = ykk_global_defination.transportation_status_start
       WHERE   mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000129;
         io_return_message := '更新搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_trans_data_on_abnormal;

   PROCEDURE update_ctl_data_on_abnormal (
      in_station_no       IN     fnpick_ctl.stno%TYPE,
      in_mckey            IN     fnpick_ctl.mckey%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE   fnpick_ctl
         SET   mckey = in_mckey, bucketreading_flg = '0'
       WHERE   stno = in_station_no;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000150;
         io_return_message := '更新拣选控制数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_ctl_data_on_abnormal;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT   *
     INTO   wk_fngset_row
     FROM   fngset
    WHERE   schno = in_schdule_no
            AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT   mckey, bucketreading_flg
     INTO   wk_mckey, wk_bucket_reading_flag
     FROM   fnpick_ctl
    WHERE   stno = wk_fngset_row.motostno;

   SELECT   retrieval_station
     INTO   wk_retrieval_station
     FROM   fnsiji
    WHERE   mckey = wk_mckey AND ROWNUM = 1;

   SELECT   *
     INTO   wk_retrieval_st_row
     FROM   fnretrieval_st
    WHERE   retrieval_station = wk_retrieval_station;

   get_next_station_no (wk_retrieval_st_row.rev_pickstno,
                        'RJCT',
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

   SELECT   systemid
     INTO   wk_systemid
     FROM   fnhanso
    WHERE   mckey = wk_mckey;

   IF wk_bucket_reading_flag != '2'
   THEN
      create_result_data_on_abnormal (wk_mckey,
                                      wk_systemid,
                                      wk_retrieval_st_row.rev_pickstno,
                                      wk_retrieval_st_row.remove_stno,
                                      wk_retrieval_st_row.rev_pickstno,
                                      wk_fngset_row.userid,
                                      wk_fngset_row.username,
                                      io_return_code,
                                      io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END IF;

   update_trans_data_on_abnormal (wk_mckey,
                                  wk_retrieval_st_row.rev_pickstno,
                                  'RJCT',
                                  wk_retrieval_st_row.rev_pickstno,
                                  wk_next_station_no,
                                  wk_systemid,
                                  io_return_code,
                                  io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   IF wk_bucket_reading_flag != '2'
   THEN
      delete_trans_data_on_abnormal (wk_mckey,
                                     wk_systemid,
                                     io_return_code,
                                     io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      --   update_stock_data_on_abnormal (wk_systemid,
      --                                  io_return_code,
      --                                  io_return_message
      --                                 );

      --   IF io_return_code != 0
      --   THEN
      --      GOTO endlabel;
      --   END IF;

      --   delete_locat_data_for_picking (wk_systemid,
      --                                  io_return_code,
      --                                  io_return_message
      --                                 );

      --   IF io_return_code != 0
      --   THEN
      --      GOTO endlabel;
      --   END IF;
      delete_label_data_for_picking (wk_mckey,
                                     io_return_code,
                                     io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      update_rsv_data_on_abnormal (wk_mckey,
                                   io_return_code,
                                   io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END IF;

   --   delete_indc_data_for_picking (wk_mckey, io_return_code, io_return_message);

   --   IF io_return_code != 0
   --   THEN
   --      GOTO endlabel;
   --   END IF;
   update_ctl_data_on_abnormal (wk_fngset_row.motostno,
                                ' ',
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
      io_return_code := 8000154;
      io_return_message := '拣选异常设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END picking_abnormal_start;
/
