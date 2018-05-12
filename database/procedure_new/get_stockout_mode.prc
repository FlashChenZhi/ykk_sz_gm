DROP PROCEDURE AWC.GET_STOCKOUT_MODE;

CREATE OR REPLACE PROCEDURE AWC.get_stockout_mode (
   in_systemid         IN     fnlocat.systemid%TYPE,
   in_stockout_num     IN     NUMBER,
   io_stockout_mode    IN OUT fnhanso.sijisyosai%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_dispatch_flag     fnlocat.hikiflg%TYPE;
   wk_total_stock_num   fnzaiko.zaikosu%TYPE;
   wk_available_flag    fnlocat.shikiflg%TYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';


   SELECT   fnlocat.shikiflg, fnlocat.hikiflg, fnzaiko.zaikosu
     INTO   wk_available_flag, wk_dispatch_flag, wk_total_stock_num
     FROM   fnlocat, fnzaiko
    WHERE       fnlocat.systemid = in_systemid
            AND fnzaiko.systemid = in_systemid
            AND fnzaiko.systemid = fnlocat.systemid;

   IF wk_dispatch_flag = ykk_global_defination.location_not_dispatched
   THEN
      IF in_stockout_num = wk_total_stock_num
         AND wk_available_flag = ykk_global_defination.location_available
      THEN
         io_stockout_mode := ykk_global_defination.stockout_mode_unit;
      ELSE
         io_stockout_mode := ykk_global_defination.stockout_mode_picking;
      END IF;
   ELSE
      io_stockout_mode := ykk_global_defination.stockout_mode_picking;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000103;
      io_return_message := '判断出库分类时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END get_stockout_mode;
/
