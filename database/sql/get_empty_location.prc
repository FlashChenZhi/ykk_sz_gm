DROP PROCEDURE GET_EMPTY_LOCATION;

CREATE OR REPLACE PROCEDURE     get_empty_location (
   --in_zone_no           IN       fnsoftzone.softzone%TYPE,
   in_from_station_no   IN       fnstation.stno%TYPE,
   io_location_no       IN OUT   fnlocat.syozaikey%TYPE,
   io_return_code       IN OUT   NUMBER,
   io_return_message    IN OUT   VARCHAR2
)
IS
   wk_zone_no         fnsoftzone.softzone%TYPE;
   wk_to_station_no   fnstation.stno%TYPE;
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   rtas80 ('00' || SUBSTR (in_from_station_no, 0, 1),
           in_from_station_no,
           wk_zone_no,
           wk_to_station_no,
           io_location_no,
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000195;
      io_return_message := '没有空货位' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000156;
      io_return_message := '空货位检查时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END get_empty_location; 

/
