DROP PROCEDURE AWC.PICKING_TOTAL_START;

CREATE OR REPLACE PROCEDURE AWC.picking_total_start (
   in_schdule_no       IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row          fngset%ROWTYPE;
   wk_mckey               fnhanso.mckey%TYPE;
   wk_retrieval_station   fnretrieval_st.retrieval_station%TYPE;
   wk_retrieval_st_row    fnretrieval_st%ROWTYPE;
   wk_next_station_no     fnhanso.sakistno%TYPE;
   wk_systemid            fnhanso.systemid%TYPE;

   PROCEDURE delete_stock_data_on_total (
      p_systemid          IN       fnzaiko.systemid%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      DELETE      fnzaiko
            WHERE systemid = p_systemid;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000144;
         io_return_message := '删除库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END delete_stock_data_on_total;

   PROCEDURE update_trans_data_on_total (
      in_mckey            IN       fnhanso.mckey%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      UPDATE fnhanso
         SET hjyotaiflg = ykk_global_defination.transportation_status_standby
       WHERE mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000129;
         io_return_message := '更新搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_trans_data_on_total;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT *
     INTO wk_fngset_row
     FROM fngset
    WHERE schno = in_schdule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT mckey
     INTO wk_mckey
     FROM fnpick_ctl
    WHERE stno = wk_fngset_row.motostno;

   SELECT retrieval_station
     INTO wk_retrieval_station
     FROM fnsiji
    WHERE mckey = wk_mckey AND ROWNUM = 1;

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
    WHERE mckey = wk_mckey;

   create_retrieval_result (wk_mckey,
                            wk_systemid,
                            io_return_code,
                            io_return_message
                           );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_stockout_result_data (wk_mckey,
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

   update_trans_data_for_picking (wk_mckey,
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

   update_trans_data_on_total (wk_mckey, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   delete_stock_data_on_total (wk_systemid, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   delete_locat_data_for_picking (wk_systemid,
                                  io_return_code,
                                  io_return_message
                                 );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

--   update_label_data_for_printing (wk_mckey,
--                                   wk_fngset_row.printer_no,
--                                   io_return_code,
--                                   io_return_message
--                                  );

   --   IF io_return_code != 0
--   THEN
--      GOTO endlabel;
--   END IF;
   update_ctl_data_for_picking (wk_fngset_row.motostno,
                                ' ',
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
      io_return_code := 8000153;
      io_return_message := '全拣选设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END picking_total_start;
/
