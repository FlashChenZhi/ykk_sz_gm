DROP PROCEDURE YKK.PICKING_CANCEL;

CREATE OR REPLACE PROCEDURE YKK.picking_cancel (
   in_schdule_no       IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row          fngset%ROWTYPE;
   wk_mckey               fnhanso.mckey%TYPE;
   wk_picking_type        CHAR (1);
   wk_retrieval_station   fnretrieval_st.retrieval_station%TYPE;
   wk_retrieval_st_row    fnretrieval_st%ROWTYPE;
   wk_next_station_no     fnhanso.sakistno%TYPE;
   wk_systemid            fnhanso.systemid%TYPE;
   wk_fnlocat_row         fnlocat%ROWTYPE;
   wk_fnhanso_row         fnhanso%ROWTYPE;

   PROCEDURE update_stock_data_on_cancel (
      in_mckey            IN       VARCHAR2,
      in_userid           IN       fnzaiko.userid%TYPE,
      in_username         IN       fnzaiko.username%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
      wk_sum   NUMBER;
   BEGIN
      SELECT SUM (nyusyusu)
        INTO wk_sum
        FROM fnsiji
       WHERE mckey = in_mckey;

      UPDATE fnzaiko
         SET skanosu = skanosu + wk_sum,
             userid = in_userid,
             username = in_username
       WHERE systemid = (SELECT systemid
                           FROM fnhanso
                          WHERE mckey = in_mckey);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_on_cancel;

   PROCEDURE update_trans_data_on_cancel (
      in_mckey              IN       fnhanso.mckey%TYPE,
      in_start_station_no   IN       fnhanso.startstno%TYPE,
      in_end_station_no     IN       fnhanso.endstno%TYPE,
      in_prev_station_no    IN       fnhanso.motostno%TYPE,
      in_next_station_no    IN       fnhanso.sakistno%TYPE,
      io_return_code        IN OUT   NUMBER,
      io_return_message     IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnhanso
         SET startstno = in_start_station_no,
             endstno = in_end_station_no,
             motostno = in_prev_station_no,
             sakistno = in_next_station_no,
             hjyotaiflg = ykk_global_defination.transportation_status_start,
             sagyokbn = ykk_global_defination.transportation_work_type_back
       WHERE mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000129;
         io_return_message := '更新搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_trans_data_on_cancel;

   PROCEDURE create_indc_data_on_normal (
      in_mckey            IN       fnhanso.mckey%TYPE,
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
       WHERE systemid = (SELECT systemid
                           FROM fnhanso
                          WHERE mckey = in_mckey);

      SELECT hansokey
        INTO wk_hansokey
        FROM fnhanso
       WHERE mckey = in_mckey;

      INSERT INTO fnsiji
                  (hansokey, mckey, ticket_no,
                   zaikey, color_code,
                   nyusyusu,
                   retrieval_station,
                   depot_code, manage_item_flag
                  )
           VALUES (wk_hansokey, in_mckey, wk_fnzaiko_row.ticket_no,
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
   END create_indc_data_on_normal;

   PROCEDURE delete_indc_data_on_cancel (
      in_mckey            IN       fnsiji.mckey%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      DELETE      fnsiji
            WHERE mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000146;
         io_return_message := '删除指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END delete_indc_data_on_cancel;

   PROCEDURE delete_trans_data_on_cancel (
      in_mckey            IN       fnhanso.mckey%TYPE,
      in_systemid         IN       fnhanso.systemid%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      DELETE      fnhanso
            WHERE systemid = in_systemid AND mckey != in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000187;
         io_return_message := '删除搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END delete_trans_data_on_cancel;
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
    WHERE schno = in_schdule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT mckey
     INTO wk_mckey
     FROM fnpick_ctl
    WHERE stno = wk_fngset_row.motostno;

   SELECT *
     INTO wk_fnhanso_row
     FROM fnhanso
    WHERE mckey = wk_mckey;

   IF wk_fnhanso_row.nyusyukbn = '7'
   THEN
      io_return_code := 8000199;
      io_return_message := '不能取消直行搬送';
      GOTO endlabel;
   END IF;

   update_rsv_data_for_picking (wk_mckey, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_stock_data_on_cancel (wk_mckey,
                                wk_fngset_row.userid,
                                wk_fngset_row.username,
                                io_return_code,
                                io_return_message
                               );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   delete_label_data_for_picking (wk_mckey, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   get_picking_type (wk_mckey,
                     wk_picking_type,
                     io_return_code,
                     io_return_message
                    );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT retrieval_station
     INTO wk_retrieval_station
     FROM fnsiji
    WHERE mckey = wk_mckey AND ROWNUM = 1;

   delete_indc_data_on_cancel (wk_mckey, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT systemid
     INTO wk_systemid
     FROM fnhanso
    WHERE mckey = wk_mckey;

   delete_trans_data_on_cancel (wk_mckey,
                                wk_systemid,
                                io_return_code,
                                io_return_message
                               );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   IF wk_picking_type = ykk_global_defination.picking_type_normal
   THEN
      SELECT *
        INTO wk_fnlocat_row
        FROM fnlocat
       WHERE systemid = wk_systemid;

      SELECT *
        INTO wk_retrieval_st_row
        FROM fnretrieval_st
       WHERE retrieval_station = wk_retrieval_station;

      get_next_station_no (wk_fnlocat_row.ailestno,
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

      update_trans_data_on_cancel (wk_mckey,
                                   wk_fnlocat_row.ailestno,
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
   END IF;

   create_indc_data_on_normal (wk_mckey, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000188;
      io_return_message := '拣选取消时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END picking_cancel;
/


