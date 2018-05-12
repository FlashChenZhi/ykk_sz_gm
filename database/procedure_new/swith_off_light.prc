DROP PROCEDURE AWC.SWITH_OFF_LIGHT;

CREATE OR REPLACE PROCEDURE AWC.swith_off_light (
   in_station_no       IN       fnstation.stno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_cmpcode   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_cmpcode :=
      fkick ('/con/seq',
             'FNMSGFKDO',
             '01011' || in_station_no || '010',
             12,
             wk_cmpcode
            );
   wk_cmpcode :=
      fkick ('/con/seq',
             'FNMSGFKDO',
             '01011' || in_station_no || '020',
             12,
             wk_cmpcode
            );
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000194;
      io_return_message := '关灯时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END swith_off_light;
/
