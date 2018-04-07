DROP PROCEDURE YKK.UNMANAGED_STOCKIN_1_START;

CREATE OR REPLACE PROCEDURE YKK.unmanaged_stockin_1_start (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row         fngset%ROWTYPE;
   wk_bucket_no          fntoucyaku.bucket_no%TYPE;
   wk_mckey              fntoucyaku.mckey%TYPE;
   wk_start_station_no   fnhanso.startstno%TYPE;
   wk_next_station_no    fnrute.stnoto%TYPE;
   wk_systemid           fnzaiko.systemid%TYPE;

   PROCEDURE create_stock_data (
      in_systemid         IN       fnzaiko.systemid%TYPE,
      in_bucket_no        IN       fnzaiko.bucket_no%TYPE,
      in_item_code        IN       fnzaiko.zaikey%TYPE,
      in_color_code       IN       fnzaiko.color_code%TYPE,
      in_stockin_num      IN       fnzaiko.zaikosu%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      UPDATE fnzaiko
         SET bucket_no = ' '
       WHERE bucket_no = in_bucket_no
         AND storage_place_flag =
                            ykk_global_defination.storage_place_flat_warehouse;

      INSERT INTO fnzaiko
                  (systemid, bucket_no, zaikey, color_code,
                   zaikosu, skanosu,
                   manage_item_flag
                  )
           VALUES (in_systemid, in_bucket_no, in_item_code, in_color_code,
                   in_stockin_num, in_stockin_num,
                   ykk_global_defination.manage_item_flag_unmanaged
                  );
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000163;
         io_return_message := '生成库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_stock_data;

   PROCEDURE create_locat_data (
      in_systemid         IN       fnzaiko.systemid%TYPE,
      in_bucket_no        IN       fnzaiko.bucket_no%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      INSERT INTO fnlocat
                  (syozaikey, sokokbn,
                   bucket_no,
                   zaijyoflg,
                   shikiflg,
                   hikiflg,
                   accessflg, systemid, konsaisu
                  )
           VALUES (' ', ykk_global_defination.warehouse_type_auto,
                   in_bucket_no,
                   ykk_global_defination.locat_status_in_reserved,
                   ykk_global_defination.location_available,
                   ykk_global_defination.location_not_dispatched,
                   ykk_global_defination.location_accessable, wk_systemid, 1
                  );
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000128;
         io_return_message := '生成货位数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_locat_data;

   PROCEDURE update_trans_data (
      in_mckey             IN       fnhanso.mckey%TYPE,
      in_prev_station_no   IN       fnhanso.motostno%TYPE,
      in_next_station_no   IN       fnhanso.sakistno%TYPE,
      in_end_station_no    IN       fnhanso.endstno%TYPE,
      in_systemid          IN       fnhanso.systemid%TYPE,
      io_return_code       IN OUT   NUMBER,
      io_return_message    IN OUT   VARCHAR2
   )
   IS
   BEGIN
      UPDATE fnhanso
         SET motostno = in_prev_station_no,
             sakistno = in_next_station_no,
             endstno = in_end_station_no,
             hjyotaiflg = ykk_global_defination.transportation_status_start,
             nyusyukbn = ykk_global_defination.transportation_type_sttost,
             sagyokbn = ykk_global_defination.transportation_work_type_key,
             systemid = in_systemid
       WHERE mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000128;
         io_return_message := '生成货位数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_trans_data;

   PROCEDURE update_indc_data_by_systemid (
      in_mckey            IN       fnsiji.mckey%TYPE,
      in_systemid         IN       fnhanso.systemid%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
      wk_fnzaiko_row   fnzaiko%ROWTYPE;
      wk_hansokey      fnsiji.hansokey%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT *
        INTO wk_fnzaiko_row
        FROM fnzaiko
       WHERE systemid = in_systemid;

      SELECT hansokey
        INTO wk_hansokey
        FROM fnhanso
       WHERE mckey = in_mckey;

      UPDATE fnsiji
         SET hansokey = wk_hansokey,
             ticket_no = wk_fnzaiko_row.ticket_no,
             zaikey = wk_fnzaiko_row.zaikey,
             color_code = wk_fnzaiko_row.color_code,
             nyusyusu = wk_fnzaiko_row.zaikosu
       WHERE mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000130;
         io_return_message := '更新指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_indc_data_by_systemid;
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

   IF wk_fngset_row.motostno = '1103'
   THEN
      wk_fngset_row.endstno := '1104';
   ELSIF wk_fngset_row.motostno = '2105'
   THEN
      wk_fngset_row.endstno := '2106';
   ELSE
      wk_fngset_row.endstno := ' ';
   END IF;

   SELECT bucket_no, mckey
     INTO wk_bucket_no, wk_mckey
     FROM fntoucyaku
    WHERE stno = wk_fngset_row.motostno
      AND syoriflg = ykk_global_defination.arrival_report_processed_stg1;

   check_bucket_not_instock
                           (wk_bucket_no,
                            ykk_global_defination.storage_place_awc_warehouse,
                            io_return_code,
                            io_return_message
                           );

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

   generate_system_id (wk_systemid, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_stock_data (wk_systemid,
                      wk_bucket_no,
                      wk_fngset_row.zaikey,
                      wk_fngset_row.color_code,
                      wk_fngset_row.nyusyusu,
                      io_return_code,
                      io_return_message
                     );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_locat_data (wk_systemid,
                      wk_bucket_no,
                      io_return_code,
                      io_return_message
                     );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_trans_data (wk_mckey,
                      wk_fngset_row.motostno,
                      wk_next_station_no,
                      wk_fngset_row.endstno,
                      wk_systemid,
                      io_return_code,
                      io_return_message
                     );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_indc_data_by_systemid (wk_mckey,
                                 wk_systemid,
                                 io_return_code,
                                 io_return_message
                                );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_arrival_rpt_for_stockin (wk_fngset_row.motostno,
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
      io_return_code := 8000164;
      io_return_message := '管理外入库设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END unmanaged_stockin_1_start;
/


