DROP PROCEDURE YKK.STOCKIN_1;

CREATE OR REPLACE PROCEDURE YKK.stockin_1 (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_stockin_mode     fnstation.nyusyumode%TYPE;
   wk_station_no       fngset.motostno%TYPE;
   wk_end_station_no   fngset.endstno%TYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT motostno
     INTO wk_station_no
     FROM fngset
    WHERE schno = in_schedule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT nyusyumode
     INTO wk_stockin_mode
     FROM fnstation
    WHERE stno = wk_station_no;

   IF wk_stockin_mode = ykk_global_defination.stockin_mode_normal
   THEN
      IF wk_station_no = '1103'
      THEN
         wk_end_station_no := '1104';
      ELSIF wk_station_no = '2105'
      THEN
         wk_end_station_no := '2106';
      ELSE
         wk_end_station_no := ' ';
      END IF;

      UPDATE fngset
         SET endstno = wk_end_station_no
       WHERE schno = in_schedule_no
         AND syoriflg = ykk_global_defination.display_info_not_processed;

      stockin_1_normality (in_schedule_no, io_return_code, io_return_message);
   ELSE
      IF wk_station_no = '1103'
      THEN
         wk_end_station_no := '1207';
      ELSIF wk_station_no = '2105'
      THEN
         wk_end_station_no := '2215';
      ELSE
         wk_end_station_no := ' ';
      END IF;

      UPDATE fngset
         SET endstno = wk_end_station_no
       WHERE schno = in_schedule_no
         AND syoriflg = ykk_global_defination.display_info_not_processed;

      IF wk_stockin_mode = ykk_global_defination.stockin_mode_empty_bucket
      THEN
         stockin_1_empty_bucket (in_schedule_no,
                                 io_return_code,
                                 io_return_message
                                );
      ELSIF wk_stockin_mode != 0
      THEN
         stockin_1_export (in_schedule_no, io_return_code, io_return_message);
      END IF;
   END IF;

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
      io_return_code := 8000139;
      io_return_message := '入库设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockin_1;
/


