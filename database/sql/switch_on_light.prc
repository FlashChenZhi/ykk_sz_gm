DROP PROCEDURE SWITCH_ON_LIGHT;

CREATE OR REPLACE PROCEDURE     switch_on_light (
   in_station_no       IN       fnstation.stno%TYPE,
   in_light_type       IN       VARCHAR2,
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
             '01011' || in_station_no || in_light_type || '1',
             12,
             wk_cmpcode
            );
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000194;
      io_return_message := '点灯时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END switch_on_light; 

/
