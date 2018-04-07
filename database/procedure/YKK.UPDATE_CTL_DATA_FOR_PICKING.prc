DROP PROCEDURE YKK.UPDATE_CTL_DATA_FOR_PICKING;

CREATE OR REPLACE PROCEDURE YKK.update_ctl_data_for_picking (
   in_station_no       IN       fnpick_ctl.stno%TYPE,
   in_mckey            IN       fnpick_ctl.mckey%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   UPDATE fnpick_ctl
      SET mckey = in_mckey
    WHERE stno = in_station_no;

   IF TRIM (in_mckey) IS NULL
   THEN
      swith_off_light (in_station_no, io_return_code, io_return_message);
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000150;
      io_return_message := '更新拣选控制数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_ctl_data_for_picking;
/


