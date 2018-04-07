DROP PROCEDURE YKK.GET_SYSTEM_DATETIME;

CREATE OR REPLACE PROCEDURE YKK.get_system_datetime (
   io_datetime         IN OUT   VARCHAR2,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_datetime := sysdate14_get ();
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000198;
      io_return_message := '获取服务器时间时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END get_system_datetime;
/


