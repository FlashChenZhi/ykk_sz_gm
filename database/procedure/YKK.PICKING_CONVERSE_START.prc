DROP PROCEDURE YKK.PICKING_CONVERSE_START;

CREATE OR REPLACE PROCEDURE YKK.picking_converse_start (
   in_schdule_no       IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row          fngset%ROWTYPE;
   wk_old_mckey           fnhanso.mckey%TYPE;
   wk_retrieval_station   fnretrieval_st.retrieval_station%TYPE;
   wk_retrieval_st_row    fnretrieval_st%ROWTYPE;
   wk_next_station_no     fnhanso.sakistno%TYPE;
   wk_start_station_no    fnhanso.startstno%TYPE;
   wk_systemid            fnhanso.systemid%TYPE;
   wk_new_mckey           fnhanso.mckey%TYPE;
   wk_hansokey            fnhanso.hansokey%TYPE;
   wk_trans_work_type     fnhanso.sagyokbn%TYPE;

   PROCEDURE create_indc_data_by_systemid (
      in_mckey            IN       fnsiji.mckey%TYPE,
      in_hansokey         IN       fnsiji.hansokey%TYPE,
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
           VALUES (in_hansokey, in_mckey, wk_fnzaiko_row.ticket_no,
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

   PROCEDURE update_trans_data_on_converse (
      in_mckey            IN       fnhanso.mckey%TYPE,
      in_bucket_no        IN       fnhanso.bucket_no%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnhanso
         SET bucket_no = in_bucket_no
       WHERE systemid = (SELECT systemid
                           FROM fnhanso
                          WHERE mckey = in_mckey) AND mckey != in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000129;
         io_return_message := '更新搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_trans_data_on_converse;

   PROCEDURE update_label_data_on_converse (
      in_mckey            IN       fnhanso.mckey%TYPE,
      in_bucket_no        IN       fnhanso.bucket_no%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnlabel
         SET bucket_no = in_bucket_no
       WHERE label_key IN (
                SELECT label_key
                  FROM fnsiji
                 WHERE mckey IN (
                          SELECT mckey
                            FROM fnhanso
                           WHERE systemid = (SELECT systemid
                                               FROM fnhanso
                                              WHERE mckey = in_mckey)
                             AND mckey != in_mckey));
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000143;
         io_return_message := '更新打印标签数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_label_data_on_converse;

   PROCEDURE update_locat_data_on_converse (
      in_mckey            IN       fnhanso.mckey%TYPE,
      in_bucket_no        IN       fnhanso.bucket_no%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnlocat
         SET bucket_no = in_bucket_no
       WHERE systemid = (SELECT systemid
                           FROM fnhanso
                          WHERE mckey = in_mckey);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000112;
         io_return_message := '更新货位数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_locat_data_on_converse;

   PROCEDURE update_stock_data_on_converse (
      in_systemid         IN       fnhanso.systemid%TYPE,
      in_bucket_no        IN       fnzaiko.bucket_no%TYPE,
      in_picking_num      IN       NUMBER,
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
         SET bucket_no = in_bucket_no,
             zaikosu = zaikosu - in_picking_num,
             userid = in_userid,
             username = in_username
       WHERE systemid = in_systemid;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_on_converse;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT *
     INTO wk_fngset_row
     FROM fngset
    WHERE schno = in_schdule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   check_bucket_not_instock
                           (wk_fngset_row.bucket_no,
                            ykk_global_defination.storage_place_awc_warehouse,
                            io_return_code,
                            io_return_message
                           );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   check_bucket_not_instock
                          (wk_fngset_row.bucket_no,
                           ykk_global_defination.storage_place_flat_warehouse,
                           io_return_code,
                           io_return_message
                          );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT mckey
     INTO wk_old_mckey
     FROM fnpick_ctl
    WHERE stno = wk_fngset_row.motostno;

   SELECT retrieval_station
     INTO wk_retrieval_station
     FROM fnsiji
    WHERE mckey = wk_old_mckey AND ROWNUM = 1;

   SELECT *
     INTO wk_retrieval_st_row
     FROM fnretrieval_st
    WHERE retrieval_station = wk_retrieval_station;

   get_next_station_no (wk_retrieval_st_row.rev_pickstno,
                        wk_retrieval_st_row.remove_stno,
                        wk_retrieval_st_row.rev_pickstno,
                        wk_next_station_no,
                        io_return_code,
                        io_return_message
                       );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   check_transportation_route (wk_retrieval_st_row.rev_pickstno,
                               wk_retrieval_st_row.remove_stno,
                               io_return_code,
                               io_return_message
                              );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT systemid
     INTO wk_systemid
     FROM fnhanso
    WHERE mckey = wk_old_mckey;

   create_retrieval_result (wk_old_mckey,
                            wk_systemid,
                            io_return_code,
                            io_return_message
                           );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_stockout_result_data (wk_old_mckey,
                                wk_systemid,
                                wk_fngset_row.motostno,
                                ' ',
                                0,
                                wk_fngset_row.userid,
                                wk_fngset_row.username,
                                io_return_code,
                                io_return_message
                               );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_trans_data_on_converse (wk_old_mckey,
                                  wk_fngset_row.bucket_no,
                                  io_return_code,
                                  io_return_message
                                 );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_label_data_on_converse (wk_old_mckey,
                                  wk_fngset_row.bucket_no,
                                  io_return_code,
                                  io_return_message
                                 );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_locat_data_on_converse (wk_old_mckey,
                                  wk_fngset_row.bucket_no,
                                  io_return_code,
                                  io_return_message
                                 );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT startstno
     INTO wk_start_station_no
     FROM fnhanso
    WHERE fnhanso.mckey = wk_old_mckey;

   update_trans_data_for_picking (wk_old_mckey,
                                  wk_retrieval_st_row.rev_pickstno,
                                  wk_retrieval_st_row.remove_stno,
                                  wk_retrieval_st_row.rev_pickstno,
                                  wk_next_station_no,
                                  wk_systemid,
                                  io_return_code,
                                  io_return_message
                                 );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   UPDATE fnhanso
      SET hjyotaiflg = ykk_global_defination.transportation_status_standby
    WHERE mckey = wk_old_mckey;

   get_next_station_no (wk_start_station_no,
                        wk_retrieval_st_row.rev_pickstno,
                        wk_retrieval_st_row.pick_stno,
                        wk_next_station_no,
                        io_return_code,
                        io_return_message
                       );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   check_transportation_route (wk_retrieval_st_row.pick_stno,
                               wk_next_station_no,
                               io_return_code,
                               io_return_message
                              );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   generate_mckey (ykk_global_defination.stock_type_stockout,
                   wk_new_mckey,
                   io_return_code,
                   io_return_message
                  );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   generate_hansokey_for_stockout (ykk_global_defination.stock_type_stockout,
                                   wk_hansokey,
                                   io_return_code,
                                   io_return_message
                                  );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT sagyokbn
     INTO wk_trans_work_type
     FROM fnhanso
    WHERE mckey = wk_old_mckey;

   create_trans_for_new_bucket (wk_new_mckey,
                                wk_hansokey,
                                wk_fngset_row.bucket_no,
                                wk_start_station_no,
                                wk_retrieval_st_row.rev_pickstno,
                                wk_retrieval_st_row.pick_stno,
                                wk_next_station_no,
                                wk_systemid,
                                wk_trans_work_type,
                                io_return_code,
                                io_return_message
                               );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT SUM (nyusyusu)
     INTO wk_fngset_row.nyusyusu
     FROM fnsiji
    WHERE mckey = wk_old_mckey;

   update_stock_data_on_converse (wk_systemid,
                                  wk_fngset_row.bucket_no,
                                  wk_fngset_row.nyusyusu,
                                  wk_fngset_row.userid,
                                  wk_fngset_row.username,
                                  io_return_code,
                                  io_return_message
                                 );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_locat_data_for_picking (wk_systemid,
                                  io_return_code,
                                  io_return_message
                                 );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_indc_data_by_systemid (wk_new_mckey,
                                 wk_hansokey,
                                 wk_systemid,
                                 io_return_code,
                                 io_return_message
                                );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

--   update_label_data_for_printing (wk_old_mckey,
--                                   wk_fngset_row.printer_no,
--                                   io_return_code,
--                                   io_return_message
--                                  );

   --   IF io_return_code != 0
--   THEN
--      GOTO endlabel;
--   END IF;
   update_ctl_data_for_picking (wk_fngset_row.motostno,
                                wk_new_mckey,
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
      io_return_code := 8000152;
      io_return_message := '反拣选设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END picking_converse_start;
/


