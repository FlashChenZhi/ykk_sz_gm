DROP PROCEDURE GENERATE_LABEL_KEY;

CREATE OR REPLACE PROCEDURE     generate_label_key (
   io_label_key        IN OUT   fnlabel.label_key%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   rtbs20 (io_label_key, io_return_code, wk_return_number, io_return_message);

   IF io_return_code != 0
   THEN
      io_return_code := 8000108;
      io_return_message :=
                         '生成LableKey时发生错误' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000108;
      io_return_message := '生成LableKey时发生错误' || ' ' || SQLERRM;
   DBMS_OUTPUT.put_line (SQLERRM);
END generate_label_key; 

/
