DROP PROCEDURE AWC.CREATE_LABEL_DATA_FOR_STOCKOUT;

CREATE OR REPLACE PROCEDURE AWC.create_label_data_for_stockout (
   in_label_key            IN     fnlabel.label_key%TYPE,
   in_label_type           IN     fnlabel.label_type%TYPE,
   in_retrieval_plan_key   IN     fnsyotei.retrieval_plankey%TYPE,
   in_ticket_no            IN     fnzaiko.ticket_no%TYPE,
   in_stockout_mode        IN     CHAR,
   in_stockout_num         IN     NUMBER,
   in_stockout_type        IN     CHAR,
   io_return_code          IN OUT NUMBER,
   io_return_message       IN OUT VARCHAR2
)
IS
   wk_fnlabel_row   fnlabel%ROWTYPE;
   wk_diff_weight   NUMBER (6, 4);
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_diff_weight := 99.9999;

   SELECT   fnzaiko.ticket_no,
            fnzaiko.bucket_no,
            fnzaiko.zaikey,
            fnzaiko.color_code,
            fnsyotei.section,
            fnsyotei.line,
            fnzaiko.made_section,
            fnzaiko.made_line,
            fnsyotei.start_date,
            fnsyotei.start_timing_flag,
            fnzaiko.real_unit_weight,
            fnsyotei.depot_code,
            fnsyotei.customer_code,
            fnsyotei.customer_name1,
            fnsyotei.customer_name2,
            fnsyotei.pr_no,
            fnsyotei.retrieval_station,
            fnsyotei.order_no,
            fnsyotei.retrieval_no,
            fnsyotei.serial_no,
            fnsyotei.necessary_qty
     INTO   wk_fnlabel_row.ticket_no,
            wk_fnlabel_row.bucket_no,
            wk_fnlabel_row.zaikey,
            wk_fnlabel_row.color_code,
            wk_fnlabel_row.section,
            wk_fnlabel_row.line,
            wk_fnlabel_row.made_section,
            wk_fnlabel_row.made_line,
            wk_fnlabel_row.start_date,
            wk_fnlabel_row.start_timing_flag,
            wk_fnlabel_row.master_unit_weight,
            wk_fnlabel_row.depot_code,
            wk_fnlabel_row.customer_code,
            wk_fnlabel_row.customer_name1,
            wk_fnlabel_row.customer_name2,
            wk_fnlabel_row.pr_no,
            wk_fnlabel_row.retrieval_station,
            wk_fnlabel_row.order_no,
            wk_fnlabel_row.retrieval_no,
            wk_fnlabel_row.serial_no,
            wk_fnlabel_row.necessary_qty
     FROM   fnsyotei, fnzaiko, fmzkey
    WHERE       fnsyotei.retrieval_plankey = in_retrieval_plan_key
            AND fnzaiko.ticket_no = in_ticket_no
            AND fnzaiko.zaikey = fnsyotei.zaikey
            AND fmzkey.zaikey = fnsyotei.zaikey
            AND fmzkey.manage_item_flag = fnzaiko.manage_item_flag;

   wk_fnlabel_row.create_datetime := sysdate14_get ();
   wk_fnlabel_row.update_datetime := sysdate14_get ();
   wk_fnlabel_row.label_type := in_label_type;
   wk_fnlabel_row.userid := ' ';
   wk_fnlabel_row.username := ' ';

   IF in_stockout_type = ykk_global_defination.external_stockout
   THEN
      IF wk_fnlabel_row.retrieval_station = '25'
      THEN
         wk_fnlabel_row.label_type :=
            ykk_global_defination.label_type_fuyong_stockout;
      ELSIF wk_fnlabel_row.retrieval_station = '26'
      THEN
         wk_fnlabel_row.label_type :=
            ykk_global_defination.label_type_customer_stockout;
      ELSIF wk_fnlabel_row.retrieval_station = '23'
      THEN
         wk_fnlabel_row.label_type :=
            ykk_global_defination.label_type_external_stockout;
      END IF;
   END IF;

   wk_fnlabel_row.option_flag :=
      ykk_global_defination.label_option_flag_stockout;
   wk_fnlabel_row.printing_flag :=
      ykk_global_defination.label_prt_flag_not_printed;
   wk_fnlabel_row.label_key := in_label_key;
   wk_fnlabel_row.retrieval_qty := in_stockout_num;

   IF wk_fnlabel_row.master_unit_weight * wk_fnlabel_row.retrieval_qty >
         wk_diff_weight
   THEN
      wk_fnlabel_row.retrieval_weight := wk_diff_weight;
   ELSE
      wk_fnlabel_row.retrieval_weight :=
         wk_fnlabel_row.master_unit_weight * wk_fnlabel_row.retrieval_qty;
   END IF;

   IF in_stockout_mode = ykk_global_defination.stockout_mode_unit
   THEN
      SELECT   printer_no_unit
        INTO   wk_fnlabel_row.printer_no
        FROM   fnretrieval_st
       WHERE   retrieval_station = wk_fnlabel_row.retrieval_station;
   ELSE
      SELECT   printer_no_picking
        INTO   wk_fnlabel_row.printer_no
        FROM   fnretrieval_st
       WHERE   retrieval_station = wk_fnlabel_row.retrieval_station;
   END IF;

   INSERT INTO fnlabel
     VALUES   wk_fnlabel_row;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000107;
      io_return_message := '生成打印标签数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END create_label_data_for_stockout;
/
