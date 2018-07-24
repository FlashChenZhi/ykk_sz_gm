DROP PROCEDURE CALL_FKICK;

CREATE OR REPLACE PROCEDURE     call_fkick (
   in_fname            IN       VARCHAR2,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_cmpcode   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_cmpcode := fkick ('/con/seq', in_fname, ' ', 0, wk_cmpcode);
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000181;
      io_return_message := '调用FKICK时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END call_fkick; 

/
