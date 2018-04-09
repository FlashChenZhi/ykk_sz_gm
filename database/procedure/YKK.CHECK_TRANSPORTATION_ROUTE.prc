DROP PROCEDURE YKK.CHECK_TRANSPORTATION_ROUTE;

CREATE OR REPLACE PROCEDURE YKK.check_transportation_route (
   in_from_station_no   IN       fmpattern.start_st%TYPE,
   in_to_station_no     IN       VARCHAR2,
   io_return_code       IN OUT   NUMBER,
   io_return_message    IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   stasa2 (in_from_station_no,
           in_to_station_no,
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      IF io_return_code = 19
      THEN
         io_return_code := 8000105;
         io_return_message := '搬送路径不存在' || ' ' || io_return_message;
      ELSIF io_return_code = 14
      THEN
         io_return_code := 8000192;
         io_return_message :=
                 '搬送路径上存在离线或异常的设备' || ' ' || io_return_message;
      ELSE
         io_return_code := 8000193;
         io_return_message :=
                         '检索搬送路径时发生错误' || ' ' || io_return_message;
      END IF;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000193;
      io_return_message := '检索搬送路径时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END check_transportation_route;
/


