DROP PROCEDURE SI_STATION_MODE_CHANGE;

CREATE OR REPLACE PROCEDURE     si_station_mode_change (
   in_io_mode          IN       fnstation.nyusyumode%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   core_station_mode_change ('2101',
                             in_io_mode,
                             io_return_code,
                             io_return_message
                            );

   IF io_return_code != 0
   THEN
      io_return_code := 8000179;
      io_return_message := '切换站台模式时发生错误' || ' ' || SQLERRM;
      GOTO endlabel;
   END IF;

   core_station_mode_change ('2107',
                             in_io_mode,
                             io_return_code,
                             io_return_message
                            );

   IF io_return_code != 0
   THEN
      io_return_code := 8000179;
      io_return_message := '切换站台模式时发生错误' || ' ' || SQLERRM;
      GOTO endlabel;
   END IF;

   core_station_mode_change ('2108',
                             in_io_mode,
                             io_return_code,
                             io_return_message
                            );

   IF io_return_code != 0
   THEN
      io_return_code := 8000179;
      io_return_message := '切换站台模式时发生错误' || ' ' || SQLERRM;
      GOTO endlabel;
   END IF;

   core_station_mode_change ('2109',
                             in_io_mode,
                             io_return_code,
                             io_return_message
                            );

   IF io_return_code != 0
   THEN
      io_return_code := 8000179;
      io_return_message := '切换站台模式时发生错误' || ' ' || SQLERRM;
      GOTO endlabel;
   END IF;

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000179;
      io_return_message := '切换站台模式时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END si_station_mode_change; 

/
