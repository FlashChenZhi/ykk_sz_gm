DROP PROCEDURE YKK.UPDATE_STOCK_DATA_FOR_STOCKIN;

CREATE OR REPLACE PROCEDURE YKK.update_stock_data_for_stockin (
   in_mckey                 IN       VARCHAR2,
   in_bucket_no             IN       VARCHAR2,
   in_stock_num             IN       NUMBER,
   in_available_stock_num   IN       NUMBER,
   in_unit_weight           IN       NUMBER,
   in_memo                  IN       VARCHAR2,
   io_return_code           IN OUT   NUMBER,
   io_return_message        IN OUT   VARCHAR2
)
IS
BEGIN
   UPDATE fnzaiko
      SET bucket_no = in_bucket_no,
          zaikosu = in_stock_num,
          skanosu = in_available_stock_num,
          real_unit_weight = in_unit_weight,
          memo = in_memo,
          weight_report_complete_flag =
                                 ykk_global_defination.weight_report_executing
    WHERE systemid = (SELECT systemid
                        FROM fnhanso
                       WHERE mckey = in_mckey);
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000111;
      io_return_message := ' 感驴獯媸据时发生代？' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_stock_data_for_stockin;
/


