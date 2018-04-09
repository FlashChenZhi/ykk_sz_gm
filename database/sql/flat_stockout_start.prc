DROP PROCEDURE FLAT_STOCKOUT_START;

CREATE OR REPLACE PROCEDURE     flat_stockout_start (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_real_instock_qty        fnzaiko.zaikosu%TYPE;
   wk_available_instock_qty   fnzaiko.skanosu%TYPE;
   wk_total_stockout_qty      number;
   wk_username                userattribute.username%TYPE;

   CURSOR fngset_list (
      in_schedule_no   IN            fngset.schno%TYPE
   )
   IS
      SELECT   fnzaiko.ticket_no,
               fngset.retrieval_plankey,
               fngset.nyusyusu AS actual_stockout_qty,
               fngset.zaikosu AS real_instock_qty,
               fngset.skanosu AS available_instock_qty,
               fngset.retrieval_alloc_qty,
               fngset.userid
        FROM   fnzaiko, fngset
       WHERE   schno = in_schedule_no
               AND syoriflg =
                     ykk_global_defination.display_info_not_processed
               AND fngset.ticket_no = fnzaiko.ticket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_flat_warehouse
               AND weight_report_complete_flag =
                     ykk_global_defination.weight_report_completed
               AND manage_item_flag =
                     ykk_global_defination.manage_item_flag_managed;

   fngset_list_iterator       fngset_list%ROWTYPE;

   PROCEDURE update_rsv_data_on_flat (
      in_retrieval_plankey     IN     fnsyotei.retrieval_plankey%TYPE,
      in_retrieval_alloc_qty   IN     fnsyotei.retrieval_alloc_qty%TYPE,
      in_total_stockout_qty    IN     number,
      io_return_code           IN OUT NUMBER,
      io_return_message        IN OUT VARCHAR2
   )
   IS
      wk_expected_stockout_qty   number;
      wk_proc_flag               fnsyotei.proc_flag%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   retrieval_qty - retrieval_alloc_qty
        INTO   wk_expected_stockout_qty
        FROM   fnsyotei
       WHERE   retrieval_plankey = in_retrieval_plankey;

      IF wk_expected_stockout_qty = in_total_stockout_qty
      THEN
         wk_proc_flag := ykk_global_defination.reservation_info_processed;
      ELSIF wk_expected_stockout_qty > in_total_stockout_qty
      THEN
         wk_proc_flag := ykk_global_defination.reservation_info_not_processed;
      ELSE
         io_return_code := 8000205;
         io_return_message := '出库数量大于出库需求数';
         GOTO endlabel;
      END IF;


      IF ykk_global_defination.write_fnsyotei_log_flag != '0'
      THEN
         write_fnsyotei_log (in_retrieval_plankey,
                             'before flat_stockout_start',
                             'worgsoft',
                             io_return_code,
                             io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;

      UPDATE   fnsyotei
         SET   retrieval_alloc_qty =
                  retrieval_alloc_qty + in_total_stockout_qty,
               proc_flag = wk_proc_flag
       WHERE   retrieval_plankey = in_retrieval_plankey
               AND retrieval_alloc_qty = in_retrieval_alloc_qty;

      IF sql%ROWCOUNT = 0
      THEN
         io_return_code := 8000202;
         io_return_message :=
            '在设定期间可能有其它操作对预约数据进行了修改，请刷新页面并重新执行设定操作';
      END IF;

      IF ykk_global_defination.write_fnsyotei_log_flag != '0'
      THEN
         write_fnsyotei_log (in_retrieval_plankey,
                             'after flat_stockout_start',
                             'worgsoft',
                             io_return_code,
                             io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;

     <<endlabel>>
      NULL;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000110;
         io_return_message := '更新出库预约数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_rsv_data_on_flat;

   PROCEDURE update_stock_data_on_flat (
      in_ticket_no               IN     fnzaiko.ticket_no%TYPE,
      in_actual_stockout_qty     IN     fnzaiko.skanosu%TYPE,
      in_real_instock_qty        IN     fnzaiko.zaikosu%TYPE,
      in_available_instock_qty          fnzaiko.skanosu%TYPE,
      io_return_code             IN OUT NUMBER,
      io_return_message          IN OUT VARCHAR2
   )
   IS
      wk_sum   NUMBER;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE   fnzaiko
         SET   zaikosu = zaikosu - in_actual_stockout_qty,
               skanosu = skanosu - in_actual_stockout_qty
       WHERE   storage_place_flag =
                  ykk_global_defination.storage_place_flat_warehouse
               AND weight_report_complete_flag =
                     ykk_global_defination.weight_report_completed
               AND manage_item_flag =
                     ykk_global_defination.manage_item_flag_managed
               AND ticket_no = in_ticket_no
               AND zaikosu = in_real_instock_qty
               AND skanosu = in_available_instock_qty;

      IF sql%ROWCOUNT = 0
      THEN
         io_return_code := 8000206;
         io_return_message :=
            '在设定期间可能有其它操作对库存数据进行了修改，请刷新页面并重新执行设定操作';
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_on_flat;

   PROCEDURE delete_stock_data_on_flat (
      in_ticket_no               IN     fnzaiko.ticket_no%TYPE,
      in_real_instock_qty        IN     fnzaiko.zaikosu%TYPE,
      in_available_instock_qty          fnzaiko.skanosu%TYPE,
      io_return_code             IN OUT NUMBER,
      io_return_message          IN OUT VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      DELETE FROM   fnzaiko
            WHERE   storage_place_flag =
                       ykk_global_defination.storage_place_flat_warehouse
                    AND weight_report_complete_flag =
                          ykk_global_defination.weight_report_completed
                    AND manage_item_flag =
                          ykk_global_defination.manage_item_flag_managed
                    AND ticket_no = in_ticket_no
                    AND zaikosu = in_real_instock_qty
                    AND skanosu = in_available_instock_qty;

      IF sql%ROWCOUNT = 0
      THEN
         io_return_code := 8000206;
         io_return_message :=
            '在设定期间可能有其它操作对库存数据进行了修改，请刷新页面并重新执行设定操作';
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000144;
         io_return_message := '删除库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END delete_stock_data_on_flat;

   PROCEDURE create_ret_result_on_flat (
      in_retrieval_plankey   IN     fnsyotei.retrieval_plankey%TYPE,
      in_ticket_no           IN     fnzaiko.ticket_no%TYPE,
      in_result_qty          IN     fhretrievalresult.result_qty%TYPE,
      io_return_code         IN OUT NUMBER,
      io_return_message      IN OUT VARCHAR2
   )
   IS
      wk_fhretrievalresult_row   fhretrievalresult%ROWTYPE;
      wk_fnsyotei_row            fnsyotei%ROWTYPE;
      wk_fnzaiko_row             fnzaiko%ROWTYPE;
      wk_datetime14              VARCHAR2 (14);
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   *
        INTO   wk_fnsyotei_row
        FROM   fnsyotei
       WHERE   retrieval_plankey = in_retrieval_plankey;

      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   storage_place_flag =
                  ykk_global_defination.storage_place_flat_warehouse
               AND weight_report_complete_flag =
                     ykk_global_defination.weight_report_completed
               AND manage_item_flag =
                     ykk_global_defination.manage_item_flag_managed
               AND ticket_no = in_ticket_no;

      wk_fhretrievalresult_row.retrieval_no := wk_fnsyotei_row.retrieval_no;
      wk_fhretrievalresult_row.serial_no := wk_fnsyotei_row.serial_no;
      wk_fhretrievalresult_row.ticket_no := wk_fnzaiko_row.ticket_no;
      wk_fhretrievalresult_row.item_code := wk_fnzaiko_row.zaikey;
      wk_fhretrievalresult_row.color_code := wk_fnzaiko_row.color_code;
      wk_fhretrievalresult_row.depot_code := wk_fnsyotei_row.depot_code;
      wk_fhretrievalresult_row.section := wk_fnsyotei_row.section;
      wk_fhretrievalresult_row.line := wk_fnsyotei_row.line;
      wk_fhretrievalresult_row.result_qty := in_result_qty;
      wk_fhretrievalresult_row.result_weight :=
         wk_fnzaiko_row.real_unit_weight * in_result_qty;
      wk_fhretrievalresult_row.retrieval_plankey := in_retrieval_plankey;
      wk_fhretrievalresult_row.proc_flag :=
         ykk_global_defination.retrieval_result_not_processed;
      wk_datetime14 := sysdate14_get ();
      wk_fhretrievalresult_row.register_date := SUBSTR (wk_datetime14, 0, 8);
      wk_fhretrievalresult_row.register_time := SUBSTR (wk_datetime14, 9, 6);

      INSERT INTO fhretrievalresult
        VALUES   wk_fhretrievalresult_row;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000148;
         io_return_message :=
            '生成Host出库实绩数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_ret_result_on_flat;

   PROCEDURE create_stockout_result_on_flat (
      in_retrieval_plankey   IN     fnsyotei.retrieval_plankey%TYPE,
      in_ticket_no           IN     fnzaiko.ticket_no%TYPE,
      in_stockout_num        IN     fnzaiko.zaikosu%TYPE,
      in_user_id             IN     fnjiseki.userid%TYPE,
      in_user_name           IN     fnjiseki.username%TYPE,
      io_return_code         IN OUT NUMBER,
      io_return_message      IN OUT VARCHAR2
   )
   IS
      wk_fnjiseki_row   fnjiseki%ROWTYPE;
      wk_fnlocat_row    fnlocat%ROWTYPE;
      wk_fnsyotei_row   fnsyotei%ROWTYPE;
      wk_fnzaiko_row    fnzaiko%ROWTYPE;
      wk_fmzkey_row     fmzkey%ROWTYPE;
      wk_fnhanso_row    fnhanso%ROWTYPE;
      wk_count          NUMBER;
      wk_datetime14     VARCHAR2 (14);
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   storage_place_flag =
                  ykk_global_defination.storage_place_flat_warehouse
               AND weight_report_complete_flag =
                     ykk_global_defination.weight_report_completed
               AND manage_item_flag =
                     ykk_global_defination.manage_item_flag_managed
               AND ticket_no = in_ticket_no;

      SELECT   *
        INTO   wk_fnlocat_row
        FROM   fnlocat
       WHERE   systemid = wk_fnzaiko_row.systemid;

      SELECT   *
        INTO   wk_fmzkey_row
        FROM   fmzkey
       WHERE   zaikey = wk_fnzaiko_row.zaikey
               AND manage_item_flag = wk_fnzaiko_row.manage_item_flag;

      wk_datetime14 := sysdate14_get ();
      wk_fnjiseki_row.mckey := ' ';
      wk_fnjiseki_row.zaikey := wk_fnzaiko_row.zaikey;
      wk_fnjiseki_row.zkname := wk_fmzkey_row.zkname1;
      wk_fnjiseki_row.zkname2 := wk_fmzkey_row.zkname2;
      wk_fnjiseki_row.zkname3 := wk_fmzkey_row.zkname3;
      wk_fnjiseki_row.sakuseihiji := wk_datetime14;
      wk_fnjiseki_row.nyusyukbn := ykk_global_defination.stock_type_stockout;
      wk_fnjiseki_row.sagyokbn :=
         ykk_global_defination.transportation_work_type_plan;


      IF wk_fnzaiko_row.zaikosu > in_stockout_num
      THEN
         wk_fnjiseki_row.sakukbn :=
            ykk_global_defination.stock_amount_decrease;
         wk_fnjiseki_row.nyusyusu := wk_fnzaiko_row.zaikosu - in_stockout_num;
      ELSE
         wk_fnjiseki_row.sakukbn :=
            ykk_global_defination.stock_amount_increase;
         wk_fnjiseki_row.nyusyusu := in_stockout_num - wk_fnzaiko_row.zaikosu;
      END IF;

      wk_fnjiseki_row.nyusyustno := ' ';
      wk_fnjiseki_row.ticket_no := wk_fnzaiko_row.ticket_no;
      wk_fnjiseki_row.bucket_no := ' ';
      wk_fnjiseki_row.color_code := wk_fnzaiko_row.color_code;
      wk_fnjiseki_row.real_work_number := in_stockout_num;
      wk_fnjiseki_row.userid := in_user_id;
      wk_fnjiseki_row.username := in_user_name;
      wk_fnjiseki_row.startstno := wk_fnhanso_row.startstno;
      wk_fnjiseki_row.endstno := wk_fnhanso_row.endstno;
      wk_fnjiseki_row.systemid := wk_fnzaiko_row.systemid;
      wk_fnjiseki_row.syozaikey := wk_fnlocat_row.syozaikey;
      wk_fnjiseki_row.manage_item_flag := wk_fnzaiko_row.manage_item_flag;
      wk_fnjiseki_row.backup_flg := '0';

      SELECT   COUNT ( * )
        INTO   wk_count
        FROM   fnsyotei
       WHERE   retrieval_plankey = in_retrieval_plankey;

      IF wk_count > 0
      THEN
         SELECT   *
           INTO   wk_fnsyotei_row
           FROM   fnsyotei
          WHERE   retrieval_plankey = in_retrieval_plankey;

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
         wk_fnjiseki_row.retrieval_qty := in_stockout_num;
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
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000149;
         io_return_message := '生成出库实绩数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_stockout_result_on_flat;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_total_stockout_qty := 0;

   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   OPEN fngset_list (in_schedule_no);

   LOOP
      FETCH fngset_list INTO   fngset_list_iterator;

      IF fngset_list%NOTFOUND = TRUE
      THEN
         IF fngset_list%ROWCOUNT = 0
         THEN
            io_return_code := 8000102;
            io_return_message := '数据不存在';
         END IF;

         EXIT;
      END IF;

      create_ret_result_on_flat (fngset_list_iterator.retrieval_plankey,
                                 fngset_list_iterator.ticket_no,
                                 fngset_list_iterator.actual_stockout_qty,
                                 io_return_code,
                                 io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      SELECT   username
        INTO   wk_username
        FROM   userattribute
       WHERE   userid = fngset_list_iterator.userid;

      create_stockout_result_on_flat (
         fngset_list_iterator.retrieval_plankey,
         fngset_list_iterator.ticket_no,
         fngset_list_iterator.actual_stockout_qty,
         fngset_list_iterator.userid,
         wk_username,
         io_return_code,
         io_return_message
      );


      wk_total_stockout_qty :=
         wk_total_stockout_qty + fngset_list_iterator.actual_stockout_qty;

      SELECT   zaikosu, skanosu
        INTO   wk_real_instock_qty, wk_available_instock_qty
        FROM   fnzaiko
       WHERE   storage_place_flag =
                  ykk_global_defination.storage_place_flat_warehouse
               AND weight_report_complete_flag =
                     ykk_global_defination.weight_report_completed
               AND manage_item_flag =
                     ykk_global_defination.manage_item_flag_managed
               AND ticket_no = fngset_list_iterator.ticket_no;

      IF wk_real_instock_qty != fngset_list_iterator.real_instock_qty
         OR wk_available_instock_qty !=
              fngset_list_iterator.available_instock_qty
      THEN
         io_return_code := 8000206;
         io_return_message :=
            '在设定期间可能有其它操作对库存数据进行了修改，请刷新页面并重新执行设定操作';
         GOTO endlabel;
      END IF;

      IF wk_real_instock_qty = fngset_list_iterator.actual_stockout_qty
         AND wk_available_instock_qty =
               fngset_list_iterator.actual_stockout_qty
      THEN
         delete_stock_data_on_flat (
            fngset_list_iterator.ticket_no,
            fngset_list_iterator.real_instock_qty,
            fngset_list_iterator.available_instock_qty,
            io_return_code,
            io_return_message
         );

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      ELSIF wk_real_instock_qty > fngset_list_iterator.actual_stockout_qty AND wk_available_instock_qty >= fngset_list_iterator.actual_stockout_qty
      THEN
         update_stock_data_on_flat (
            fngset_list_iterator.ticket_no,
            fngset_list_iterator.actual_stockout_qty,
            fngset_list_iterator.real_instock_qty,
            fngset_list_iterator.available_instock_qty,
            io_return_code,
            io_return_message
         );

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      ELSE
         io_return_code := 8000204;
         io_return_message := '出库数量大于实际库存数';
         GOTO endlabel;
      END IF;
   END LOOP;

   IF fngset_list%ISOPEN = TRUE
   THEN
      CLOSE fngset_list;
   END IF;


   IF wk_total_stockout_qty != 0
   THEN
      update_rsv_data_on_flat (fngset_list_iterator.retrieval_plankey,
                               fngset_list_iterator.retrieval_alloc_qty,
                               wk_total_stockout_qty,
                               io_return_code,
                               io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END IF;

  <<endlabel>>
   IF fngset_list%ISOPEN = TRUE
   THEN
      CLOSE fngset_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF fngset_list%ISOPEN = TRUE
      THEN
         CLOSE fngset_list;
      END IF;

      io_return_code := 8000203;
      io_return_message := '平库出库设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END flat_stockout_start; 

/
