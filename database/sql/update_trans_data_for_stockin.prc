DROP PROCEDURE UPDATE_TRANS_DATA_FOR_STOCKIN;

CREATE OR REPLACE PROCEDURE     update_trans_data_for_stockin (
   in_mckey             IN       fnhanso.mckey%TYPE,
   in_work_type         IN       fnhanso.sagyokbn%TYPE,
   in_from_station_no   IN       fnhanso.motostno%TYPE,
   in_to_station_no     IN       fnhanso.sakistno%TYPE,
   in_end_station_no    IN       fnhanso.endstno%TYPE,
   io_return_code       IN OUT   NUMBER,
   io_return_message    IN OUT   VARCHAR2
)
IS
BEGIN
   UPDATE fnhanso
      SET sagyokbn = in_work_type,
          motostno = in_from_station_no,
          sakistno = in_to_station_no,
          endstno = in_end_station_no,
          hjyotaiflg = ykk_global_defination.transportation_status_start,
          nyusyukbn = ykk_global_defination.transportation_type_sttost,
          setkbn = '2'
    WHERE mckey = in_mckey;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000129;
      io_return_message := '更新搬送数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_trans_data_for_stockin; 

/
