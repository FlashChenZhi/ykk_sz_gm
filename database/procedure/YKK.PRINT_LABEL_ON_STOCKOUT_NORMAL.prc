DROP PROCEDURE YKK.PRINT_LABEL_ON_STOCKOUT_NORMAL;

CREATE OR REPLACE PROCEDURE YKK.print_label_on_stockout_normal (
   in_mckey            IN       fnhanso.mckey%TYPE,
   in_bucket_no        IN       fnlabel.bucket_no%TYPE,
   in_printer_no       IN       fngset.printer_no%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   tmpvar   NUMBER;
BEGIN
   UPDATE fnlabel
      SET bucket_no = in_bucket_no
    WHERE fnlabel.label_key IN (SELECT label_key
                                  FROM fnsiji
                                 WHERE mckey = in_mckey);

   update_label_data_for_printing (in_mckey,
                                   in_printer_no,
                                   io_return_code,
                                   io_return_message
                                  );
EXCEPTION
   WHEN OTHERS
   THEN
      ROLLBACK;
      io_return_code := 8000143;
      io_return_message := '更新打印标签数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END print_label_on_stockout_normal;
/


