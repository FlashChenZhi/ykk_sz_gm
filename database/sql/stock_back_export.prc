DROP PROCEDURE STOCK_BACK_EXPORT;

CREATE OR REPLACE PROCEDURE     stock_back_export (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2)
IS
   wk_fngset_row        fngset%ROWTYPE;
   wk_count             NUMBER;
   wk_mckey             fnhanso.mckey%TYPE;
   wk_next_station_no   fnhanso.sakistno%TYPE;
   wk_systemid          fnhanso.systemid%TYPE;
   wk_motostno          fngset.motostno%TYPE;

   PROCEDURE delete_stock_data_on_export (
      in_systemid         IN     fnzaiko.systemid%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2)
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      DELETE fnzaiko
       WHERE systemid = in_systemid;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000144;
         io_return_message :=
            '删除库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END delete_stock_data_on_export;

   PROCEDURE create_diff_rpt_on_export (
      in_systemid         IN     fnzaiko.systemid%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2)
   IS
      wk_fhdifferencestock_row   fhdifferencestock%ROWTYPE;
      wk_fnzaiko_row             fnzaiko%ROWTYPE;
      wk_datetime14              VARCHAR2 (14);
      wk_diff_weight             NUMBER (6, 4);
   BEGIN
      io_return_code := 0;
      io_return_message := '';
      wk_diff_weight := 99.9999;

      SELECT *
        INTO wk_fnzaiko_row
        FROM fnzaiko
       WHERE fnzaiko.systemid = in_systemid;

      wk_fhdifferencestock_row.difference_flag := '1';
      wk_fhdifferencestock_row.ticket_no := wk_fnzaiko_row.ticket_no;
      wk_fhdifferencestock_row.item_code := wk_fnzaiko_row.zaikey;
      wk_fhdifferencestock_row.color_code := wk_fnzaiko_row.color_code;
      wk_fhdifferencestock_row.difference_type :=
         ykk_global_defination.difference_type_sub;
      wk_fhdifferencestock_row.difference_qty := wk_fnzaiko_row.skanosu;

      IF wk_diff_weight >
            wk_fnzaiko_row.real_unit_weight * wk_fnzaiko_row.skanosu
      THEN
         wk_fhdifferencestock_row.difference_weight :=
            wk_fnzaiko_row.real_unit_weight * wk_fnzaiko_row.skanosu;
      ELSE
         wk_fhdifferencestock_row.difference_weight := wk_diff_weight;
      END IF;

      wk_fhdifferencestock_row.proc_flag := '0';
      wk_datetime14 := sysdate14_get ();
      wk_fhdifferencestock_row.register_date := SUBSTR (wk_datetime14, 0, 8);
      wk_fhdifferencestock_row.register_time := SUBSTR (wk_datetime14, 9, 6);

      INSERT INTO fhdifferencestock
           VALUES wk_fhdifferencestock_row;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000133;
         io_return_message :=
            '生成在库差异数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_diff_rpt_on_export;
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

   wk_motostno := wk_fngset_row.motostno;

   SELECT mckey
     INTO wk_mckey
     FROM fnpick_ctl
    WHERE stno = wk_fngset_row.motostno;

   SELECT COUNT (*)
     INTO wk_count
     FROM fnhanso
    WHERE     mckey != wk_mckey
          AND systemid = (SELECT systemid
                            FROM fnhanso
                           WHERE mckey = wk_mckey);

   IF wk_count > 0
   THEN
      io_return_code := '8000190';
      io_return_message := '有未完成的作业，不能排出';
      GOTO endlabel;
   END IF;

   IF wk_fngset_row.motostno = '1202'
   THEN
      wk_fngset_row.motostno := '1105';
      wk_fngset_row.endstno := '1203';
   ELSIF wk_fngset_row.motostno = '2206'
   THEN
      wk_fngset_row.motostno := '2103';
      wk_fngset_row.endstno := '2207';
   ELSIF wk_fngset_row.motostno = '2209'
   THEN
      wk_fngset_row.motostno := '2104';
      wk_fngset_row.endstno := '2210';
   ELSIF wk_fngset_row.motostno = '3202'
   THEN
      wk_fngset_row.motostno := '3101';
      wk_fngset_row.endstno := '3203';
   ELSIF wk_fngset_row.motostno = '3205'
   THEN
      wk_fngset_row.motostno := '3102';
      wk_fngset_row.endstno := '3206';
   ELSIF wk_fngset_row.motostno = '1212'
   THEN
      wk_fngset_row.motostno := '1106';
      wk_fngset_row.endstno := '1213';
   ELSIF wk_fngset_row.motostno = '1215'
   THEN
      wk_fngset_row.motostno := '1107';
      wk_fngset_row.endstno := '1216';
   ELSIF wk_fngset_row.motostno = '1218'
   THEN
      wk_fngset_row.motostno := '1108';
      wk_fngset_row.endstno := '1219';
   END IF;

   SELECT systemid
     INTO wk_systemid
     FROM fnhanso
    WHERE mckey = wk_mckey;

   get_next_station_no (wk_fngset_row.motostno,
                        wk_fngset_row.endstno,
                        wk_fngset_row.motostno,
                        wk_next_station_no,
                        io_return_code,
                        io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   check_transportation_route (wk_fngset_row.motostno,
                               wk_next_station_no,
                               io_return_code,
                               io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_trans_data_for_picking (wk_mckey,
                                  wk_fngset_row.motostno,
                                  wk_fngset_row.endstno,
                                  wk_fngset_row.motostno,
                                  wk_next_station_no,
                                  wk_systemid,
                                  io_return_code,
                                  io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_stockout_result_data (wk_mckey,
                                wk_systemid,
                                wk_motostno,
                                'D',
                                0,
                                wk_fngset_row.userid,
                                wk_fngset_row.username,
                                io_return_code,
                                io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_diff_rpt_on_export (wk_systemid, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_stockout_result_data (wk_mckey,
                                wk_systemid,
                                wk_motostno,
                                'E',
                                0,
                                wk_fngset_row.userid,
                                wk_fngset_row.username,
                                io_return_code,
                                io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   delete_locat_data_for_picking (wk_systemid,
                                  io_return_code,
                                  io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   delete_stock_data_on_export (wk_systemid,
                                io_return_code,
                                io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_ctl_data_for_picking (wk_motostno,
                                ' ',
                                io_return_code,
                                io_return_message);

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
      io_return_code := 8000191;
      io_return_message :=
         '回库排出模式设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stock_back_export;

/
