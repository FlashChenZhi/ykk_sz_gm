DROP PROCEDURE GENERATE_MCKEY;

CREATE OR REPLACE PROCEDURE     generate_mckey (
   in_stock_type       IN     CHAR,
   io_mckey            IN OUT fnhanso.mckey%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   rtass0 (in_stock_type,
           io_mckey,
           io_return_code,
           wk_return_number,
           io_return_message);

   IF io_return_code != 0
   THEN
      io_return_code := 8000109;
      io_return_message := '生成MCKey时发生错误' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000109;
      io_return_message := '生成MCKey时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END generate_mckey; 

/
