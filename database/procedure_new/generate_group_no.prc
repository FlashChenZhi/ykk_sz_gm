DROP PROCEDURE AWC.GENERATE_GROUP_NO;

CREATE OR REPLACE PROCEDURE AWC.generate_group_no (
   io_group_no         IN OUT   VARCHAR2,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   rtasi0 (io_group_no, io_return_code, wk_return_number, io_return_message);

   IF io_return_code != 0
   THEN
      io_return_code := 8000108;
      io_return_message :=
                         '����GroupNoʱ��������' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000123;
      io_return_message := '����GroupNoʱ��������' || ' ' || SQLERRM;
   DBMS_OUTPUT.put_line (SQLERRM);
END generate_group_no;
/
