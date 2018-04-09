DROP PROCEDURE UPDATE_ARRIVAL_RPT_FOR_STOCKIN;

CREATE OR REPLACE PROCEDURE     update_arrival_rpt_for_stockin (
   in_station_no       IN       fntoucyaku.stno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   UPDATE fntoucyaku
      SET syoriflg = ykk_global_defination.arrival_report_processed_stg2
    WHERE stno = in_station_no;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000135;
      io_return_message := '更新到达报告数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_arrival_rpt_for_stockin; 

/
