DROP PROCEDURE YKK.UNMANAGED_STOCKOUT_START;

CREATE OR REPLACE PROCEDURE YKK.unmanaged_stockout_start (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_next_station_no     fnrute.stnoto%TYPE;
   wk_mckey               fnhanso.mckey%TYPE;
   wk_trans_key           fnhanso.hansokey%TYPE;
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
      in_trans_key          IN       fnhanso.hansokey%TYPE,
      in_start_station_no   IN       fnhanso.startstno%TYPE,
      in_end_station_no     IN       fnhanso.endstno%TYPE,
      in_prev_station_no    IN       fnhanso.motostno%TYPE,
      in_next_station_no    IN       fnhanso.sakistno%TYPE,
      in_systemid           IN       fnlocat.systemid%TYPE,
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

      SELECT bucket_no
        INTO wk_bucket_no
        FROM fnzaiko
       WHERE systemid = in_systemid;

      INSERT INTO fnhanso
                  (hansokey, mckey, startstno,
                   endstno, motostno, sakistno,
                   systemid, bucket_no,
                   sijisyosai,
                   sagyokbn,
                   nyusyukbn,
                   hjyotaiflg,
                   yusenkbn
                  )
           VALUES (in_trans_key, in_mckey, in_start_station_no,
                   in_end_station_no, in_prev_station_no, in_next_station_no,
                   in_systemid, wk_bucket_no,
                   ykk_global_defination.stockout_mode_unit,
                   ykk_global_defination.transportation_work_type_plan,
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
      in_trans_key        IN       fnsiji.hansokey%TYPE,
      in_systemid         IN       fnhanso.systemid%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
      wk_fnzaiko_row   fnzaiko%ROWTYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT *
        INTO wk_fnzaiko_row
        FROM fnzaiko
       WHERE systemid = in_systemid;

      INSERT INTO fnsiji
                  (hansokey, mckey, ticket_no,
                   zaikey, color_code,
                   nyusyusu,
                   retrieval_station,
                   depot_code, manage_item_flag
                  )
           VALUES (in_trans_key, in_mckey, wk_fnzaiko_row.ticket_no,
                   wk_fnzaiko_row.zaikey, wk_fnzaiko_row.color_code,
                   wk_fnzaiko_row.zaikosu,
                   ykk_global_defination.retrieval_station_warehouse,
                   wk_fnzaiko_row.depot_code, wk_fnzaiko_row.manage_item_flag
                  );
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000142;
         io_return_message := '生成指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_indc_data_by_systemid;

   PROCEDURE update_locat_data (
      in_systemid         IN       fnlocat.systemid%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnlocat
         SET zaijyoflg = ykk_global_defination.locat_status_out_reserved,
             hikiflg = ykk_global_defination.location_dispatched,
             shikiflg = ykk_global_defination.location_transporting
       WHERE systemid = in_systemid;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000112;
         io_return_message := '更新货位数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_locat_data;

   PROCEDURE update_stock_data (
      in_systemid         IN       fnzaiko.systemid%TYPE,
      in_userid           IN       fnzaiko.userid%TYPE,
      in_username         IN       fnzaiko.username%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnzaiko
         SET userid = in_userid,
             username = in_username
       WHERE systemid = in_systemid;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data;
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
                          wk_trans_key,
                          io_return_code,
                          io_return_message
                         );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      create_trans_data (wk_mckey,
                         wk_trans_key,
                         wk_fnlocat_row.ailestno,
                         fngset_list_iterator.endstno,
                         wk_fnlocat_row.ailestno,
                         wk_next_station_no,
                         fngset_list_iterator.systemid,
                         io_return_code,
                         io_return_message
                        );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      create_indc_data_by_systemid (wk_mckey,
                                    wk_trans_key,
                                    fngset_list_iterator.systemid,
                                    io_return_code,
                                    io_return_message
                                   );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

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

      update_locat_data (fngset_list_iterator.systemid,
                         io_return_code,
                         io_return_message
                        );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      update_stock_data (fngset_list_iterator.systemid,
                         fngset_list_iterator.userid,
                         fngset_list_iterator.username,
                         io_return_code,
                         io_return_message
                        );
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

      io_return_code := 8000161;
      io_return_message := '管理外出库设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END unmanaged_stockout_start;
/


