DROP PROCEDURE AWC.STOCKIN_1_EXPORT;

CREATE OR REPLACE PROCEDURE AWC.stockin_1_export (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row         fngset%ROWTYPE;
   wk_mckey              fntoucyaku.mckey%TYPE;
   wk_start_station_no   fnhanso.startstno%TYPE;
   wk_next_station_no    fnrute.stnoto%TYPE;
   wk_count              NUMBER;
   wk_hansokey           fnhanso.hansokey%TYPE;

   PROCEDURE create_trans_data_for_export (
      in_hansokey           IN       fnhanso.hansokey%TYPE,
      in_mckey              IN       fnhanso.mckey%TYPE,
      in_start_station_no   IN       fnhanso.startstno%TYPE,
      io_return_code        IN OUT   NUMBER,
      io_return_message     IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      INSERT INTO fnhanso
                  (hansokey, mckey, startstno
                  )
           VALUES (in_hansokey, in_mckey, in_start_station_no
                  );
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000141;
         io_return_message := '生成搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_trans_data_for_export;

   PROCEDURE create_indc_data_for_export (
      in_hansokey         IN       fnhanso.hansokey%TYPE,
      in_mckey            IN       fnhanso.mckey%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      INSERT INTO fnsiji
                  (hansokey, mckey
                  )
           VALUES (in_hansokey, in_mckey
                  );
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000142;
         io_return_message := '生成指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_indc_data_for_export;

   PROCEDURE update_indc_data_for_export (
      in_mckey            IN       fnhanso.mckey%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnsiji
         SET hansokey = (SELECT hansokey
                           FROM fnhanso
                          WHERE mckey = in_mckey AND ROWNUM = 1)
       WHERE mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000130;
         io_return_message := '更新指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_indc_data_for_export;

   PROCEDURE update_stock_data_for_export (
      in_mckey            IN       fnhanso.mckey%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
      wk_ticket_no   fnsiji.ticket_no%TYPE;
      wk_count       number;    
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      select count(*)
      into wk_count
      from fnsiji
      where mckey = in_mckey;

      if wk_count = 0 then
         goto endlabel;
      end if;

      SELECT ticket_no
        INTO wk_ticket_no
        FROM fnsiji
       WHERE mckey = in_mckey AND ROWNUM = 1;

      IF TRIM (wk_ticket_no) IS NOT NULL
      THEN
         UPDATE fnzaiko
            SET storage_place_flag =
                            ykk_global_defination.storage_place_flat_warehouse
          WHERE ticket_no = wk_ticket_no;
      END IF;
      <<endlabel>>
      null;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_for_export;
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
      wk_fngset_row.sakistno := '1101';
      wk_fngset_row.endstno := '1207';
   ELSIF wk_fngset_row.motostno = '2105'
   THEN
      wk_fngset_row.sakistno := '2215';
      wk_fngset_row.endstno := '2215';
   ELSIF wk_fngset_row.motostno = '2101'
   THEN
      wk_fngset_row.sakistno := '2101';
      wk_fngset_row.endstno := '2201';
   ELSE
      wk_fngset_row.endstno := ' ';
   END IF;

   SELECT mckey
     INTO wk_mckey
     FROM fntoucyaku
    WHERE stno = wk_fngset_row.motostno
      AND syoriflg = ykk_global_defination.arrival_report_processed_stg1;

   SELECT COUNT (*)
     INTO wk_count
     FROM fnhanso
    WHERE mckey = wk_mckey;

   IF wk_count = 0
   THEN
      generate_hansokey_for_stockout
                                  (ykk_global_defination.stock_type_stockout,
                                   wk_hansokey,
                                   io_return_code,
                                   io_return_message
                                  );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      create_trans_data_for_export (wk_hansokey,
                                    wk_mckey,
                                    wk_fngset_row.sakistno,
                                    io_return_code,
                                    io_return_message
                                   );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      create_indc_data_for_export (wk_hansokey,
                                   wk_mckey,
                                   io_return_code,
                                   io_return_message
                                  );
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

   update_indc_data_for_export (wk_mckey, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_stock_data_for_export (wk_mckey, io_return_code, io_return_message);

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
      io_return_code := 8000136;
      io_return_message := '入库排出模式设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockin_1_export;
/
