DROP PROCEDURE YKK.CORE_STATION_MODE_CHANGE;

CREATE OR REPLACE PROCEDURE YKK.core_station_mode_change (
   in_station_no       IN       fnstation.stno%TYPE,
   in_io_mode          IN       fnstation.nyusyumode%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_io_mode         fnstation.nyusyumode%TYPE;
   wk_return_number   NUMBER;
   wk_station_mode    CHAR (1);
BEGIN
   SELECT nyusyumode
     INTO wk_io_mode
     FROM fnstation
    WHERE stno = in_station_no;

   IF wk_io_mode != in_io_mode
   THEN
      UPDATE fnstation
         SET nyusyumode = in_io_mode
       WHERE stno = in_station_no;

      IF in_station_no = '2109'
      THEN
         IF TRIM (in_io_mode) = '1'
         THEN
            wk_station_mode := '2';
         ELSE
            wk_station_mode := '1';
         END IF;

         rtas30 ('/con/seq',
                 'FNMSGFKDO',
                 '00131' || wk_station_mode,
                 io_return_code,
                 wk_return_number,
                 io_return_message
                );
      END IF;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000179;
      io_return_message := '切换站台模式时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END core_station_mode_change;
/


