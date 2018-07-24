DROP PROCEDURE CREATE_STOCKOUT_RESULT_DATA;

CREATE OR REPLACE PROCEDURE     create_stockout_result_data (
   in_mckey             IN       fnsiji.mckey%TYPE,
   in_systemid          IN       fnzaiko.systemid%TYPE,
   in_station_no        IN       fnjiseki.nyusyustno%TYPE,
   in_trans_work_type   IN       fnhanso.sagyokbn%TYPE,
   in_stockout_num      IN       NUMBER,
   in_user_id           IN       fnjiseki.userid%TYPE,
   in_user_name         IN       fnjiseki.username%TYPE,
   io_return_code       IN OUT   NUMBER,
   io_return_message    IN OUT   VARCHAR2
)
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

   IF wk_fnzaiko_row.zaikosu = in_stockout_num
   THEN
      GOTO endlabel;
   END IF;

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
    WHERE zaikey = wk_fnzaiko_row.zaikey
      AND manage_item_flag = wk_fnzaiko_row.manage_item_flag;

   OPEN indc_list (in_mckey);

   LOOP
      FETCH indc_list
       INTO indc_list_iterator;

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
      wk_fnjiseki_row.measure_unit_weight := wk_fnzaiko_row.real_unit_weight;
      wk_fnjiseki_row.zkname := wk_fmzkey_row.zkname1;
      wk_fnjiseki_row.zkname2 := wk_fmzkey_row.zkname2;
      wk_fnjiseki_row.zkname3 := wk_fmzkey_row.zkname3;
      wk_fnjiseki_row.sakuseihiji := wk_datetime14;
      wk_fnjiseki_row.nyusyukbn := '2';

      IF TRIM (in_trans_work_type) IS NULL
      THEN
         wk_fnjiseki_row.sagyokbn := wk_fnhanso_row.sagyokbn;
      ELSE
         wk_fnjiseki_row.sagyokbn := in_trans_work_type;
      END IF;

      IF in_trans_work_type != 'E'
      THEN
         wk_fnjiseki_row.sakukbn :=
                                  ykk_global_defination.stock_amount_decrease;
         wk_fnjiseki_row.nyusyusu := indc_list_iterator.nyusyusu;
      ELSE
         IF wk_fnzaiko_row.zaikosu > in_stockout_num
         THEN
            wk_fnjiseki_row.sakukbn :=
                                  ykk_global_defination.stock_amount_decrease;
            wk_fnjiseki_row.nyusyusu :=
                                     wk_fnzaiko_row.zaikosu - in_stockout_num;
         ELSE
            wk_fnjiseki_row.sakukbn :=
                                  ykk_global_defination.stock_amount_increase;
            wk_fnjiseki_row.nyusyusu :=
                                     in_stockout_num - wk_fnzaiko_row.zaikosu;
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
      io_return_message := '生成出库实绩数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END create_stockout_result_data; 

/
