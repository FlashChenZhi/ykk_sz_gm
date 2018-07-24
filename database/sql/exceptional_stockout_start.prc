DROP PROCEDURE EXCEPTIONAL_STOCKOUT_START;

CREATE OR REPLACE PROCEDURE     exceptional_stockout_start (
   in_schedule_no      IN       fngset.schno%TYPE,
   in_stockout_type    IN       CHAR,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row          fngset%ROWTYPE;
   wk_station_type        fnstation.station_type%TYPE;
   wk_next_station_no     fnrute.stnoto%TYPE;
   wk_mckey               fnhanso.mckey%TYPE;
   wk_hansokey            fnhanso.hansokey%TYPE;
   wk_fnlocat_row         fnlocat%ROWTYPE;

   CURSOR fngset_list (in_schedule_no IN fngset.schno%TYPE)
   IS
      SELECT *
        FROM fngset
       WHERE schno = in_schedule_no
         AND syoriflg = ykk_global_defination.display_info_not_processed;

   fngset_list_iterator   fngset_list%ROWTYPE;

   PROCEDURE check_location_status (
      in_systemid         IN       fnlocat.systemid%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
      wk_count   NUMBER;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT COUNT (*)
        INTO wk_count
        FROM fnlocat, fnunit
       WHERE fnlocat.systemid = in_systemid
         AND fnlocat.zaijyoflg = ykk_global_defination.locat_status_instock
         AND fnlocat.shikiflg = ykk_global_defination.location_available
         AND fnlocat.hikiflg = ykk_global_defination.location_not_dispatched
         AND fnlocat.accessflg = ykk_global_defination.location_accessable
         AND fnlocat.ailestno = fnunit.ailestno
         AND fnunit.unitstat IN
                (ykk_global_defination.unit_status_operating,
                 ykk_global_defination.unit_status_suspend,
                 ykk_global_defination.unit_status_trouble
                );

      IF wk_count = 0
      THEN
         io_return_code := 8000158;
         io_return_message := '货位不符合出库条件' || ' ' || SQLERRM;
         GOTO endlabel;
      END IF;

      <<endlabel>>
      NULL;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000159;
         io_return_message := '检查货位状态时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END check_location_status;

   PROCEDURE create_trans_data (
      in_mckey              IN       fnhanso.mckey%TYPE,
      in_hansokey           IN       fnhanso.hansokey%TYPE,
      in_start_station_no   IN       fnhanso.startstno%TYPE,
      in_end_station_no     IN       fnhanso.endstno%TYPE,
      in_prev_station_no    IN       fnhanso.motostno%TYPE,
      in_next_station_no    IN       fnhanso.sakistno%TYPE,
      in_systemid           IN       fnlocat.systemid%TYPE,
      in_station_type       IN       fnstation.station_type%TYPE,
      io_return_code        IN OUT   NUMBER,
      io_return_message     IN OUT   VARCHAR2
   )
   IS
      wk_stockout_mode     fnhanso.sijisyosai%TYPE;
      wk_trans_work_type   fnhanso.sagyokbn%TYPE;
      wk_bucket_no         fnhanso.bucket_no%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';
      wk_bucket_no := ' ';

      IF in_station_type = ykk_global_defination.station_type_picking
      THEN
         wk_stockout_mode := ykk_global_defination.stockout_mode_picking;
      ELSE
         wk_stockout_mode := ykk_global_defination.stockout_mode_unit;
      END IF;

      IF in_stockout_type IN ('1', '2', '4')
      THEN
         wk_trans_work_type :=
                           ykk_global_defination.transportation_work_type_key;
      ELSIF in_stockout_type = '3'
      THEN
         wk_trans_work_type :=
                         ykk_global_defination.transportation_work_type_cycle;
      ELSIF in_stockout_type = '5'
      THEN
         wk_trans_work_type :=
                         ykk_global_defination.transportation_work_type_locat;
      END IF;

      IF in_stockout_type IN ('3', '4', '5')
      THEN
         SELECT bucket_no
           INTO wk_bucket_no
           FROM fnlocat
          WHERE systemid = in_systemid;
      END IF;

      INSERT INTO fnhanso
                  (hansokey, mckey, startstno,
                   endstno, motostno, sakistno,
                   systemid, sijisyosai, sagyokbn,
                   bucket_no,
                   nyusyukbn,
                   hjyotaiflg,
                   yusenkbn
                  )
           VALUES (in_hansokey, in_mckey, in_start_station_no,
                   in_end_station_no, in_prev_station_no, in_next_station_no,
                   in_systemid, wk_stockout_mode, wk_trans_work_type,
                   wk_bucket_no,
                   ykk_global_defination.transportation_type_stockout,
                   ykk_global_defination.transportation_status_start,
                   ykk_global_defination.trans_priority_urgency
                  );

      <<endlabel>>
      NULL;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000141;
         io_return_message := '生成搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_trans_data;

   PROCEDURE create_indc_data_by_systemid (
      in_mckey            IN       fnsiji.mckey%TYPE,
      in_hansokey         IN       fnsiji.hansokey%TYPE,
      in_systemid         IN       fnhanso.systemid%TYPE,
      in_station_type     IN       fnstation.station_type%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
      wk_fnzaiko_row    fnzaiko%ROWTYPE;
      wk_stockout_num   fnsiji.nyusyusu%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT *
        INTO wk_fnzaiko_row
        FROM fnzaiko
       WHERE systemid = in_systemid;

--      IF in_station_type = ykk_global_defination.station_type_reject
--      THEN
--         wk_stockout_num := wk_fnzaiko_row.skanosu;
--      ELSE
--         wk_stockout_num := 0;
--      END IF;
      IF in_stockout_type = '3'
      THEN
         wk_stockout_num := 0;
      ELSE
         wk_stockout_num := wk_fnzaiko_row.skanosu;
      END IF;

      INSERT INTO fnsiji
                  (hansokey, mckey, ticket_no,
                   zaikey, color_code,
                   nyusyusu,
                   retrieval_station, depot_code,
                   manage_item_flag
                  )
           VALUES (in_hansokey, in_mckey, wk_fnzaiko_row.ticket_no,
                   wk_fnzaiko_row.zaikey, wk_fnzaiko_row.color_code,
                   wk_stockout_num,
                   ykk_global_defination.retrieval_station_warehouse, '02',
                   wk_fnzaiko_row.manage_item_flag
                  );
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000142;
         io_return_message := '生成指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_indc_data_by_systemid;

   PROCEDURE update_stock_data (
      in_systemid         IN       fnzaiko.systemid%TYPE,
      in_station_type     IN       fnstation.station_type%TYPE,
      in_userid           IN       fnzaiko.userid%TYPE,
      in_username         IN       fnzaiko.username%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      --IF in_station_type = ykk_global_defination.station_type_reject
      IF in_stockout_type != '3'
      THEN
--         UPDATE fnzaiko
--            SET skanosu = 0,
--                userid = in_userid,
--                username = in_username
--          WHERE systemid = in_systemid;

          UPDATE fnzaiko
            SET skanosu = 0
          WHERE systemid = in_systemid;
--      ELSE
--         UPDATE fnzaiko
--            SET userid = in_userid,
--                username = in_username
--          WHERE systemid = in_systemid;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data;

   PROCEDURE update_locat_data (
      in_systemid         IN       fnlocat.systemid%TYPE,
      in_station_type     IN       fnstation.station_type%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      IF in_station_type = ykk_global_defination.station_type_reject
      THEN
         UPDATE fnlocat
            SET zaijyoflg = ykk_global_defination.locat_status_out_reserved,
                hikiflg = ykk_global_defination.location_dispatched
          WHERE systemid = in_systemid;
      ELSE
         UPDATE fnlocat
            SET zaijyoflg = ykk_global_defination.locat_status_out_reserved,
                hikiflg = ykk_global_defination.location_dispatched,
                shikiflg = ykk_global_defination.location_on_cycle
          WHERE systemid = in_systemid;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_locat_data;
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
      AND syoriflg = ykk_global_defination.display_info_not_processed
      AND ROWNUM = 1;

   SELECT station_type
     INTO wk_station_type
     FROM fnstation
    WHERE fnstation.stno = wk_fngset_row.endstno;

   OPEN fngset_list (in_schedule_no);

   LOOP
      FETCH fngset_list
       INTO fngset_list_iterator;

      IF fngset_list%NOTFOUND = TRUE
      THEN
         IF fngset_list%ROWCOUNT = 0
         THEN
            io_return_code := 8000102;
            io_return_message := '数据不存在';
         END IF;

         EXIT;
      END IF;

      SELECT username
        INTO fngset_list_iterator.username
        FROM userattribute
       WHERE userid = fngset_list_iterator.userid;

      check_location_status (fngset_list_iterator.systemid,
                             io_return_code,
                             io_return_message
                            );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      SELECT *
        INTO wk_fnlocat_row
        FROM fnlocat
       WHERE systemid = fngset_list_iterator.systemid;

      get_next_station_no (wk_fnlocat_row.ailestno,
                           fngset_list_iterator.endstno,
                           wk_fnlocat_row.ailestno,
                           wk_next_station_no,
                           io_return_code,
                           io_return_message
                          );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      check_transportation_route (wk_fnlocat_row.ailestno,
                                  fngset_list_iterator.endstno,
                                  io_return_code,
                                  io_return_message
                                 );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      generate_mckey (ykk_global_defination.stock_type_stockout,
                      wk_mckey,
                      io_return_code,
                      io_return_message
                     );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      generate_trans_key (ykk_global_defination.stock_type_stockout,
                          wk_hansokey,
                          io_return_code,
                          io_return_message
                         );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      create_trans_data (wk_mckey,
                         wk_hansokey,
                         wk_fnlocat_row.ailestno,
                         fngset_list_iterator.endstno,
                         wk_fnlocat_row.ailestno,
                         wk_next_station_no,
                         fngset_list_iterator.systemid,
                         wk_station_type,
                         io_return_code,
                         io_return_message
                        );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      create_indc_data_by_systemid (wk_mckey,
                                    wk_hansokey,
                                    fngset_list_iterator.systemid,
                                    wk_station_type,
                                    io_return_code,
                                    io_return_message
                                   );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      IF in_stockout_type = '5'
      THEN
         create_stockout_result_data (wk_mckey,
                                      fngset_list_iterator.systemid,
                                      '0000',
                                      ' ',
                                      0,
                                      fngset_list_iterator.userid,
                                      fngset_list_iterator.username,
                                      io_return_code,
                                      io_return_message
                                     );

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;

      update_stock_data (fngset_list_iterator.systemid,
                         wk_station_type,
                         fngset_list_iterator.userid,
                         fngset_list_iterator.username,
                         io_return_code,
                         io_return_message
                        );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      update_locat_data (fngset_list_iterator.systemid,
                         wk_station_type,
                         io_return_code,
                         io_return_message
                        );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
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

      io_return_code := 8000160;
      io_return_message := '例外出库设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END exceptional_stockout_start;

/
