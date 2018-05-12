DROP PROCEDURE AWC.DELETE_INDC_DATA_FOR_PICKING;

CREATE OR REPLACE PROCEDURE AWC.delete_indc_data_for_picking (
   in_mckey            IN       fnsiji.mckey%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   DELETE      fnsiji
         WHERE mckey = in_mckey;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000146;
      io_return_message := '删除指示数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END delete_indc_data_for_picking;
/
