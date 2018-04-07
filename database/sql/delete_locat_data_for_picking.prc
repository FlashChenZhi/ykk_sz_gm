DROP PROCEDURE DELETE_LOCAT_DATA_FOR_PICKING;

CREATE OR REPLACE PROCEDURE     delete_locat_data_for_picking (
   in_systemid          IN       fnlocat.systemid%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   DELETE      fnlocat
         WHERE systemid = in_systemid;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000145;
      io_return_message := '删除货位数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END delete_locat_data_for_picking; 

/
