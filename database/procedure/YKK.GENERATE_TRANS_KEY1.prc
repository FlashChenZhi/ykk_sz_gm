DROP PROCEDURE YKK.GENERATE_TRANS_KEY1;

CREATE OR REPLACE PROCEDURE YKK.generate_trans_key1 (
   in_stock_type       IN       CHAR,
   io_trans_key         IN OUT   fnhanso.hansokey%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   rtasf0 (in_stock_type,
           io_trans_key,
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000131;
      io_return_message := '生成搬送Key时发生错误' || ' ' || io_return_message;
   END IF;

EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000131;
      io_return_message := '生成搬送Key时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END generate_trans_key1;
/


