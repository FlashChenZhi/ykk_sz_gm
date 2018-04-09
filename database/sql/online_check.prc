DROP PROCEDURE ONLINE_CHECK;

CREATE OR REPLACE PROCEDURE     online_check (
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_count   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT COUNT (*)
     INTO wk_count
     FROM fnarea
    WHERE arcno = ykk_global_defination.arc_number
      AND systemflg = ykk_global_defination.system_online;

   IF wk_count = 0
   THEN
      io_return_code := 8000101;
      io_return_message := '系统不在线';
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000100;
      io_return_message := '检查系统是否在线时发生错误' || ' ' || SQLERRM;
   DBMS_OUTPUT.put_line (SQLERRM);
END online_check; 

/
