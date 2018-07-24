DROP PROCEDURE AWC.GENERATE_SCHEDULE_NO;

CREATE OR REPLACE PROCEDURE AWC.generate_schedule_no (
   io_schedule_no      IN OUT   fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   rtasm0 (io_schedule_no,
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000118;
      io_return_message :=
                        '生成SchduleNo时发生错误' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000118;
      io_return_message := '生成SchduleNo时发生错误' || ' ' || SQLERRM;
   DBMS_OUTPUT.put_line (SQLERRM);
END generate_schedule_no;
/
