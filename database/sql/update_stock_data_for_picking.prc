DROP PROCEDURE UPDATE_STOCK_DATA_FOR_PICKING;

CREATE OR REPLACE PROCEDURE     update_stock_data_for_picking (
   in_systemid         IN       fnhanso.systemid%TYPE,
   in_picking_num      IN       NUMBER,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   UPDATE fnzaiko
      SET zaikosu = zaikosu - in_picking_num
    WHERE systemid = in_systemid;
    
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000111;
      io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_stock_data_for_picking; 

/
