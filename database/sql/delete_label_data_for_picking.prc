DROP PROCEDURE DELETE_LABEL_DATA_FOR_PICKING;

CREATE OR REPLACE PROCEDURE     delete_label_data_for_picking (
   in_mckey            IN       fnsiji.mckey%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   DELETE      fnlabel
         WHERE label_key IN (SELECT label_key
                               FROM fnsiji
                              WHERE mckey = in_mckey);
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000147;
      io_return_message := '删除打印标签数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END delete_label_data_for_picking; 

/
