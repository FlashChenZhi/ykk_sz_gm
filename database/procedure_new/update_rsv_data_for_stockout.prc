DROP PROCEDURE AWC.UPDATE_RSV_DATA_FOR_STOCKOUT;

CREATE OR REPLACE PROCEDURE AWC.update_rsv_data_for_stockout (
   in_retrieval_plan_key    IN     VARCHAR2,
   in_necessary_qty         IN     fnsyotei.necessary_qty%TYPE,
   in_retrieval_alloc_qty   IN     fnsyotei.retrieval_alloc_qty%TYPE,
   in_total_stockout_num    IN     NUMBER,
   io_return_code           IN OUT NUMBER,
   io_return_message        IN OUT VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   IF ykk_global_defination.write_fnsyotei_log_flag != '0'
   THEN
      write_fnsyotei_log (in_retrieval_plan_key,
                          'before stockout_setting',
                          'worgsoft',
                          io_return_code,
                          io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END IF;

   UPDATE   fnsyotei
      SET   proc_flag =
               DECODE (necessary_qty,
                       retrieval_alloc_qty + in_total_stockout_num,
                       ykk_global_defination.reservation_info_processed,
                       ykk_global_defination.reservation_info_not_processed),
            retrieval_alloc_qty = retrieval_alloc_qty + in_total_stockout_num
    WHERE       retrieval_plankey = in_retrieval_plan_key
            AND necessary_qty = in_necessary_qty
            AND retrieval_alloc_qty = in_retrieval_alloc_qty;

   IF sql%ROWCOUNT = 0
   THEN
      io_return_code := 8000202;
      io_return_message :=
         '在设定期间可能有其它操作对预约数据进行了修改，请刷新页面并重新执行设定操作';
      GOTO endlabel;
   END IF;


   IF ykk_global_defination.write_fnsyotei_log_flag != '0'
   THEN
      write_fnsyotei_log (in_retrieval_plan_key,
                          'after stockout_setting',
                          'worgsoft',
                          io_return_code,
                          io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END IF;

  <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000110;
      io_return_message := '更新出库预约数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_rsv_data_for_stockout;
/
