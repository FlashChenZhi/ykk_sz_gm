DROP PROCEDURE YKK.STOCKIN_1_EMPTY_BUCKET;

CREATE OR REPLACE PROCEDURE YKK.stockin_1_empty_bucket (
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
   wk_height_flag        fntoucyaku.height_flag%TYPE;
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
      wk_fngset_row.endstno := '1207';
   ELSIF wk_fngset_row.motostno = '2105'
   THEN
      wk_fngset_row.endstno := '2215';
   ELSIF wk_fngset_row.motostno = '2101'
   THEN
      wk_fngset_row.endstno := '2201';
   ELSE
      wk_fngset_row.endstno := ' ';
   END IF;

   SELECT bucket_no, mckey, height_flag
     INTO wk_bucket_no, wk_mckey, wk_height_flag
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

   check_bucket_not_exist (wk_bucket_no, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      UPDATE fmbucket
         SET height_flag = wk_height_flag,
             packing_weight = wk_fngset_row.packing_weight,
             lastupdate_datetime = sysdate14_get ()
       WHERE bucket_no = wk_bucket_no;
   ELSE
      create_bucket_data_for_stockin (wk_bucket_no,
                                      wk_height_flag,
                                      wk_fngset_row.packing_weight,
                                      io_return_code,
                                      io_return_message
                                     );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
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

   update_trans_data_for_stockin
                         (wk_mckey,
                          ykk_global_defination.transportation_work_type_auto,
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

   update_arrival_rpt_for_stockin (wk_fngset_row.motostno,
                                   io_return_code,
                                   io_return_message
                                  );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000138;
      io_return_message :=
                       '入库空Bucket登记模式设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockin_1_empty_bucket;
/


