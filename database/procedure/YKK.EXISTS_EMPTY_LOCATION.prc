DROP PROCEDURE YKK.EXISTS_EMPTY_LOCATION;

CREATE OR REPLACE PROCEDURE YKK.exists_empty_location (
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_count           NUMBER;
   wk_return_number   NUMBER;
BEGIN
   rtcs20 (wk_count, io_return_code, wk_return_number, io_return_message);

   IF io_return_code != 0
   THEN
      io_return_code := 8000126;
      io_return_message := '检查空货位时发生错误' || ' ' || io_return_message;
   END IF;

   IF wk_count <= 0
   THEN
      io_return_code := 8000127;
      io_return_message := '没有空货位';
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000126;
      io_return_message := '检查空货位时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END exists_empty_location;
/


