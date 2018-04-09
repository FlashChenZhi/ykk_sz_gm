DROP PROCEDURE YKK.UPDATE_RSV_DATA_FOR_STOCKOUT;

CREATE OR REPLACE PROCEDURE YKK.update_rsv_data_for_stockout (
   in_retrieval_plan_key   IN       VARCHAR2,
   in_total_stockout_num   IN       NUMBER,
   in_stockout_type        IN       CHAR,
   io_return_code          IN OUT   NUMBER,
   io_return_message       IN OUT   VARCHAR2
)
IS
   wk_expected_qty   fnsyotei.retrieval_qty%TYPE;
   wk_actual_qty     fnsyotei.allocation_qty%TYPE;
   wk_proc_flag      fnsyotei.proc_flag%TYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT retrieval_qty, retrieval_alloc_qty
     INTO wk_expected_qty, wk_actual_qty
     FROM fnsyotei
    WHERE retrieval_plankey = in_retrieval_plan_key;

   wk_actual_qty := wk_actual_qty + in_total_stockout_num;

   IF wk_expected_qty > wk_actual_qty
   THEN
      wk_proc_flag := ykk_global_defination.reservation_info_not_processed;
   ELSE
      wk_proc_flag := ykk_global_defination.reservation_info_processed;
   END IF;

   IF ykk_global_defination.write_fnsyotei_log_flag != '0'
   THEN
      write_fnsyotei_log (in_retrieval_plan_key,
                          'before stockout_setting',
                          'worgsoft',
                          io_return_code,
                          io_return_message
                         );
   END IF;

   UPDATE fnsyotei
      SET proc_flag = wk_proc_flag,
          retrieval_alloc_qty = wk_actual_qty
    WHERE retrieval_plankey = in_retrieval_plan_key;

   IF ykk_global_defination.write_fnsyotei_log_flag != '0'
   THEN
      write_fnsyotei_log (in_retrieval_plan_key,
                          'after stockout_setting',
                          'worgsoft',
                          io_return_code,
                          io_return_message
                         );
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000110;
      io_return_message := '更新出库预约数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_rsv_data_for_stockout;
/


