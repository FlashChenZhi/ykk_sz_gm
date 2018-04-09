DROP PROCEDURE YKK.UPDATE_LOCAT_DATA_FOR_STOCKOUT;

CREATE OR REPLACE PROCEDURE YKK.update_locat_data_for_stockout (
   in_systemid         IN       fnlocat.systemid%TYPE,
   in_stockout_mode    IN       CHAR,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_locat_status    fnlocat.zaijyoflg%TYPE;
   wk_dispatch_flag   fnlocat.hikiflg%TYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT zaijyoflg
     INTO wk_locat_status
     FROM fnlocat
    WHERE systemid = in_systemid;

   IF wk_locat_status = ykk_global_defination.locat_status_instock
   THEN
      wk_locat_status := ykk_global_defination.locat_status_out_reserved;
   END IF;

   /*IF in_stockout_mode = ykk_global_defination.stockout_mode_unit
   THEN
      wk_dispatch_flag := ykk_global_defination.location_not_dispatched;
   ELSE
      wk_dispatch_flag := ykk_global_defination.location_dispatched;
   END IF;*/
   UPDATE fnlocat
      SET zaijyoflg = wk_locat_status,
          hikiflg = ykk_global_defination.location_dispatched
    WHERE systemid = in_systemid;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000112;
      io_return_message := '更新货位数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_locat_data_for_stockout;
/


