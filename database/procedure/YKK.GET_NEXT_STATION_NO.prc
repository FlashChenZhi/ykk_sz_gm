DROP PROCEDURE YKK.GET_NEXT_STATION_NO;

CREATE OR REPLACE PROCEDURE YKK.get_next_station_no (
   in_start_station_no      IN       fmpattern.start_st%TYPE,
   in_end_station_no        IN       fmpattern.end_st%TYPE,
   in_previous_station_no   IN       fmpattern.prev_st%TYPE,
   io_next_station_no       IN OUT   fmpattern.next_st%TYPE,
   io_return_code           IN OUT   NUMBER,
   io_return_message        IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT next_st
     INTO io_next_station_no
     FROM fmpattern
    WHERE start_st = in_start_station_no
      AND prev_st = in_previous_station_no
      AND end_st = in_end_station_no;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000105;
      io_return_message := '°áËÍÂ·¾¶²»´æÔÚ' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END get_next_station_no;
/


