DROP PROCEDURE AWC.UPDATE_LOCAT_DATA_FOR_PICKING;

CREATE OR REPLACE PROCEDURE AWC.update_locat_data_for_picking (
   in_systemid         IN       fnlocat.systemid%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_count           NUMBER;
   wk_dispatch_flag   fnlocat.hikiflg%TYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT zaikosu - skanosu
     INTO wk_count
     FROM fnzaiko
    WHERE systemid = in_systemid;

   IF wk_count = 0
   THEN
      wk_dispatch_flag := ykk_global_defination.location_not_dispatched;
   ELSE
      wk_dispatch_flag := ykk_global_defination.location_dispatched;
   END IF;

   UPDATE fnlocat
      SET hikiflg = wk_dispatch_flag
    WHERE systemid = in_systemid;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000112;
      io_return_message := '更新货位数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_locat_data_for_picking;
/
