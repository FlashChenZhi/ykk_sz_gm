DROP PROCEDURE AWC.CREATE_TRANS_FOR_NEW_BUCKET;

CREATE OR REPLACE PROCEDURE AWC.create_trans_for_new_bucket (
   in_mckey              IN       fnhanso.mckey%TYPE,
   in_hansokey           IN       fnhanso.hansokey%TYPE,
   in_bucket_no          IN       fnhanso.bucket_no%TYPE,
   in_start_station_no   IN       fnhanso.startstno%TYPE,
   in_end_station_no     IN       fnhanso.endstno%TYPE,
   in_prev_station_no    IN       fnhanso.motostno%TYPE,
   in_next_station_no    IN       fnhanso.sakistno%TYPE,
   in_systemid           IN       fnhanso.systemid%TYPE,
   in_trans_work_type    IN       fnhanso.sagyokbn%TYPE,
   io_return_code        IN OUT   NUMBER,
   io_return_message     IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   INSERT INTO fnhanso
               (hansokey, mckey, bucket_no, startstno,
                endstno, motostno, sakistno,
                systemid, sijisyosai,
                nyusyukbn,
                hjyotaiflg, setkbn,
                sagyokbn
               )
        VALUES (in_hansokey, in_mckey, in_bucket_no, in_start_station_no,
                in_end_station_no, in_prev_station_no, in_next_station_no,
                in_systemid, ykk_global_defination.stockout_mode_picking,
                ykk_global_defination.transportation_type_sttost,
                ykk_global_defination.transportation_status_start, '1',
                in_trans_work_type
               );

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000141;
      io_return_message := '生成搬送数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END create_trans_for_new_bucket;
/
