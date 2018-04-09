DROP PROCEDURE YKK.UPDATE_TRANS_DATA_FOR_PICKING;

CREATE OR REPLACE PROCEDURE YKK.update_trans_data_for_picking (
   in_mckey              IN       fnhanso.mckey%TYPE,
   in_start_station_no   IN       fnhanso.startstno%TYPE,
   in_end_station_no     IN       fnhanso.endstno%TYPE,
   in_prev_station_no    IN       fnhanso.motostno%TYPE,
   in_next_station_no    IN       fnhanso.sakistno%TYPE,
   io_systemid           IN OUT   fnhanso.systemid%TYPE,
   io_return_code        IN OUT   NUMBER,
   io_return_message     IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT systemid
     INTO io_systemid
     FROM fnhanso
    WHERE mckey = in_mckey;

   UPDATE fnhanso
      SET startstno = in_start_station_no,
          endstno = in_end_station_no,
          motostno = in_prev_station_no,
          sakistno = in_next_station_no,
          systemid = ' ',
          hjyotaiflg = ykk_global_defination.transportation_status_start
    WHERE mckey = in_mckey;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000129;
      io_return_message := '更新搬送数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_trans_data_for_picking;
/


