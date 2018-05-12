DROP PROCEDURE AWC.TICKET_NO_FORCE_DELETE_START;

CREATE OR REPLACE PROCEDURE AWC.ticket_no_force_delete_start (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   CURSOR fngset_list (
      in_schedule_no   IN            fngset.schno%TYPE
   )
   IS
          SELECT   fngset.ticket_no, fngset.userid
            FROM   fngset, fnzaiko
           WHERE   schno = in_schedule_no
                   AND syoriflg =
                         ykk_global_defination.display_info_not_processed
                   AND fngset.ticket_no = fnzaiko.ticket_no
                   AND fnzaiko.storage_place_flag =
                         ykk_global_defination.storage_place_flat_warehouse
                   AND fnzaiko.weight_report_complete_flag IN
                            (ykk_global_defination.weight_report_not_completed,
                             ykk_global_defination.weight_report_completed)
      FOR UPDATE   OF fnzaiko.ticket_no NOWAIT;

   fngset_list_iterator             fngset_list%ROWTYPE;
   wk_username                      userattribute.username%TYPE;
   wk_weight_report_complete_flag   fnzaiko.weight_report_complete_flag%TYPE;
   wk_count                         number;

   PROCEDURE create_diff_rpt (in_ticket_no        IN     fnzaiko.ticket_no%TYPE,
                              io_return_code      IN OUT NUMBER,
                              io_return_message   IN OUT VARCHAR2)
   IS
      wk_fnzaiko_row             fnzaiko%ROWTYPE;
      wk_fhdifferencestock_row   fhdifferencestock%ROWTYPE;
      wk_datetime14              VARCHAR2 (14);
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   fnzaiko.ticket_no = in_ticket_no;

      wk_fhdifferencestock_row.difference_flag := '2';
      wk_fhdifferencestock_row.ticket_no := wk_fnzaiko_row.ticket_no;
      wk_fhdifferencestock_row.item_code := wk_fnzaiko_row.zaikey;
      wk_fhdifferencestock_row.color_code := wk_fnzaiko_row.color_code;
      wk_fhdifferencestock_row.difference_type :=
         ykk_global_defination.difference_type_sub;
      wk_fhdifferencestock_row.difference_qty := wk_fnzaiko_row.zaikosu;
      wk_fhdifferencestock_row.difference_weight :=
         wk_fnzaiko_row.real_unit_weight * wk_fnzaiko_row.zaikosu;
      wk_fhdifferencestock_row.proc_flag := '0';
      wk_datetime14 := sysdate14_get ();
      wk_fhdifferencestock_row.register_date := SUBSTR (wk_datetime14, 0, 8);
      wk_fhdifferencestock_row.register_time := SUBSTR (wk_datetime14, 9, 6);

      INSERT INTO fhdifferencestock
        VALUES   wk_fhdifferencestock_row;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000133;
         io_return_message := '生成在库差异数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_diff_rpt;

   PROCEDURE create_stockout_result (
      in_ticket_no        IN     fnzaiko.ticket_no%TYPE,
      in_user_id          IN     fnjiseki.userid%TYPE,
      in_user_name        IN     fnjiseki.username%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      wk_fnjiseki_row   fnjiseki%ROWTYPE;
      wk_fnzaiko_row    fnzaiko%ROWTYPE;
      wk_fmzkey_row     fmzkey%ROWTYPE;
      wk_datetime14     VARCHAR2 (14);
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   ticket_no = in_ticket_no;

      SELECT   *
        INTO   wk_fmzkey_row
        FROM   fmzkey
       WHERE   zaikey = wk_fnzaiko_row.zaikey
               AND manage_item_flag = wk_fnzaiko_row.manage_item_flag;

      IF TRIM (wk_fnzaiko_row.systemid) IS NULL
      THEN
         wk_fnzaiko_row.systemid := ' ';
      END IF;

      wk_datetime14 := sysdate14_get ();
      wk_fnjiseki_row.mckey := ' ';
      wk_fnjiseki_row.zaikey := wk_fnzaiko_row.zaikey;
      wk_fnjiseki_row.measure_unit_weight := wk_fnzaiko_row.real_unit_weight;
      wk_fnjiseki_row.zkname := wk_fmzkey_row.zkname1;
      wk_fnjiseki_row.zkname2 := wk_fmzkey_row.zkname2;
      wk_fnjiseki_row.zkname3 := wk_fmzkey_row.zkname3;
      wk_fnjiseki_row.sakuseihiji := wk_datetime14;
      wk_fnjiseki_row.nyusyukbn := '2';
      wk_fnjiseki_row.sagyokbn := 'E';
      wk_fnjiseki_row.sakukbn := ykk_global_defination.stock_amount_decrease;
      wk_fnjiseki_row.nyusyustno := ' ';
      wk_fnjiseki_row.ticket_no := wk_fnzaiko_row.ticket_no;
      wk_fnjiseki_row.bucket_no := wk_fnzaiko_row.bucket_no;
      wk_fnjiseki_row.color_code := wk_fnzaiko_row.color_code;
      wk_fnjiseki_row.nyusyusu := wk_fnzaiko_row.zaikosu;
      wk_fnjiseki_row.real_work_number := wk_fnzaiko_row.zaikosu;
      wk_fnjiseki_row.userid := in_user_id;
      wk_fnjiseki_row.username := in_user_name;
      wk_fnjiseki_row.startstno := ' ';
      wk_fnjiseki_row.endstno := ' ';
      wk_fnjiseki_row.systemid := wk_fnzaiko_row.systemid;
      wk_fnjiseki_row.syozaikey := ' ';
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
        VALUES   wk_fnjiseki_row;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000149;
         io_return_message := '生成出库实绩数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_stockout_result;
BEGIN
   io_return_code := 0;
   io_return_message := '';
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

      SELECT   weight_report_complete_flag
        INTO   wk_weight_report_complete_flag
        FROM   fnzaiko
       WHERE   storage_place_flag =
                  ykk_global_defination.storage_place_flat_warehouse
               AND ticket_no = fngset_list_iterator.ticket_no;

      IF wk_weight_report_complete_flag =
            ykk_global_defination.weight_report_completed
      THEN
         create_diff_rpt (fngset_list_iterator.ticket_no,
                          io_return_code,
                          io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;

      IF TRIM (fngset_list_iterator.userid) IS NOT NULL
      THEN
         SELECT   username
           INTO   wk_username
           FROM   userattribute
          WHERE   userid = fngset_list_iterator.userid;
      ELSE
         fngset_list_iterator.userid := ' ';
         wk_username := ' ';
      END IF;

      create_stockout_result (fngset_list_iterator.ticket_no,
                              fngset_list_iterator.userid,
                              wk_username,
                              io_return_code,
                              io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      DELETE   fnzaiko
       WHERE   storage_place_flag =
                  ykk_global_defination.storage_place_flat_warehouse
               AND ticket_no = fngset_list_iterator.ticket_no;
   END LOOP;

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

      io_return_code := 8000168;
      io_return_message := '生产传票强制删除时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END ticket_no_force_delete_start;
/
