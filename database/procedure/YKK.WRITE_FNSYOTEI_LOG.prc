DROP PROCEDURE YKK.WRITE_FNSYOTEI_LOG;

CREATE OR REPLACE PROCEDURE YKK.write_fnsyotei_log (
   in_retrieval_plankey   IN       fnsyotei.retrieval_plankey%TYPE,
   in_invoker_name        IN       fnsyotei_log.invoker_name%TYPE,
   in_program_vendor      IN       fnsyotei_log.program_vendor%TYPE,
   io_return_code         IN OUT   NUMBER,
   io_return_message      IN OUT   VARCHAR2
)
IS
   wk_fnsyotei_row   fnsyotei%ROWTYPE;
   wk_log_row        fnsyotei_log%ROWTYPE;
   wk_datetime14     VARCHAR2 (14);
BEGIN
   wk_datetime14 := sysdate14_get ();

   SELECT *
     INTO wk_fnsyotei_row
     FROM fnsyotei
    WHERE retrieval_plankey = in_retrieval_plankey;

   wk_log_row.serial_no := wk_fnsyotei_row.serial_no;
   wk_log_row.order_serial_no := wk_fnsyotei_row.order_serial_no;
   wk_log_row.start_timing_flag := wk_fnsyotei_row.start_timing_flag;
   wk_log_row.complete_timing_flag := wk_fnsyotei_row.complete_timing_flag;
   wk_log_row.line_type := wk_fnsyotei_row.line_type;
   wk_log_row.necessary_weight := wk_fnsyotei_row.necessary_weight;
   wk_log_row.allocation_weight := wk_fnsyotei_row.allocation_weight;
   wk_log_row.retrieval_weight := wk_fnsyotei_row.retrieval_weight;
   wk_log_row.create_datetime := wk_fnsyotei_row.create_datetime;
   wk_log_row.retrieval_no := wk_fnsyotei_row.retrieval_no;
   wk_log_row.retrieval_amount := wk_fnsyotei_row.retrieval_amount;
   wk_log_row.order_no := wk_fnsyotei_row.order_no;
   wk_log_row.order_no_amount := wk_fnsyotei_row.order_no_amount;
   wk_log_row.start_date := wk_fnsyotei_row.start_date;
   wk_log_row.complete_date := wk_fnsyotei_row.complete_date;
   wk_log_row.depot_code := wk_fnsyotei_row.depot_code;
   wk_log_row.section := wk_fnsyotei_row.section;
   wk_log_row.line := wk_fnsyotei_row.line;
   wk_log_row.customer_code := wk_fnsyotei_row.customer_code;
   wk_log_row.customer_name1 := wk_fnsyotei_row.customer_name1;
   wk_log_row.customer_name2 := wk_fnsyotei_row.customer_name2;
   wk_log_row.pr_no := wk_fnsyotei_row.pr_no;
   wk_log_row.zaikey := wk_fnsyotei_row.zaikey;
   wk_log_row.color_code := wk_fnsyotei_row.color_code;
   wk_log_row.necessary_qty := wk_fnsyotei_row.necessary_qty;
   wk_log_row.allocation_qty := wk_fnsyotei_row.allocation_qty;
   wk_log_row.retrieval_qty := wk_fnsyotei_row.retrieval_qty;
   wk_log_row.retrieval_station := wk_fnsyotei_row.retrieval_station;
   wk_log_row.cmt := wk_fnsyotei_row.cmt;
   wk_log_row.retrieval_plankey := wk_fnsyotei_row.retrieval_plankey;
   wk_log_row.retrieval_alloc_qty := wk_fnsyotei_row.retrieval_alloc_qty;
   wk_log_row.proc_flag := wk_fnsyotei_row.proc_flag;
   wk_log_row.update_datetime := sysdate14_get ();
   wk_log_row.invoker_name := in_invoker_name;
   wk_log_row.program_vendor := in_program_vendor;

   INSERT INTO fnsyotei_log
        VALUES wk_log_row;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000110;
      io_return_message := '采集预约数据变化日志时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END write_fnsyotei_log;
/


