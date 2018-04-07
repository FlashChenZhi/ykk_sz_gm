DROP PROCEDURE PICKING_CYCLE_START;

CREATE OR REPLACE PROCEDURE     picking_cycle_start (
   in_schdule_no       IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row   fngset%ROWTYPE;
   wk_mckey        fnhanso.mckey%TYPE;

   PROCEDURE create_indc_data_on_cycle (
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
   END create_indc_data_on_cycle;
BEGIN
   SELECT *
     INTO wk_fngset_row
     FROM fngset
    WHERE schno = in_schdule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT mckey
     INTO wk_mckey
     FROM fnpick_ctl
    WHERE stno = wk_fngset_row.motostno;

   delete_indc_data_for_picking (wk_mckey, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_indc_data_on_cycle (wk_mckey, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000189;
      io_return_message := '拣选盘库设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END picking_cycle_start; 

/
