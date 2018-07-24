DROP PROCEDURE AWC.GENERATE_SYSTEM_ID;

CREATE OR REPLACE PROCEDURE AWC.generate_system_id (
   io_system_id        IN OUT   fnzaiko.systemid%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   RTASE0 (io_system_id, io_return_code, wk_return_number, io_return_message);

   IF io_return_code != 0
   THEN
      io_return_code := 8000162;
      io_return_message :=
                         '生成SystemId时发生错误' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000162;
      io_return_message := '生成SystemId时发生错误' || ' ' || SQLERRM;
   DBMS_OUTPUT.put_line (SQLERRM);
END generate_system_id;
/
