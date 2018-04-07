DROP PROCEDURE STOCKIN_2_RELEASE;

CREATE OR REPLACE PROCEDURE     stockin_2_release (
   in_station_no       IN       fngset.motostno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   rtas30 ('/con/seq',
           'FNMSGFKDO',
           '00111' || in_station_no || '2',
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000172;
      io_return_message := '解除时发生错误' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000172;
      io_return_message := '解除时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockin_2_release; 

/
