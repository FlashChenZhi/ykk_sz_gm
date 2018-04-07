DROP PROCEDURE UPDATE_STOCK_DATA_FOR_STOCKOUT;

CREATE OR REPLACE PROCEDURE     update_stock_data_for_stockout (
   in_systemid         IN       fnzaiko.systemid%TYPE,
   in_stockout_num     IN       NUMBER,
   in_userid           IN       fnzaiko.userid%TYPE,
   in_username         IN       fnzaiko.username%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

--  UPDATE fnzaiko
--      SET skanosu = skanosu - in_stockout_num,
--          userid = in_userid,
--          username = in_username
--    WHERE systemid = in_systemid;

   UPDATE fnzaiko
      SET skanosu = skanosu - in_stockout_num
    WHERE systemid = in_systemid;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000111;
      io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_stock_data_for_stockout;

/
