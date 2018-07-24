DROP PROCEDURE AWC.GENERATE_HANSOKEY_FOR_STOCKOUT;

CREATE OR REPLACE PROCEDURE AWC.generate_hansokey_for_stockout (
   in_stock_type       IN     CHAR,
   io_hansokey         IN OUT fnhanso.hansokey%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   rtasf0 (in_stock_type,
           io_hansokey,
           io_return_code,
           wk_return_number,
           io_return_message);

   IF io_return_code != 0
   THEN
      io_return_code := 8000131;
      io_return_message := '���ɰ���Keyʱ��������' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000131;
      io_return_message := '���ɰ���Keyʱ��������' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END generate_hansokey_for_stockout;
/
