DROP PROCEDURE YKK.CREATE_DIFF_RPT_FOR_STOCKIN;

CREATE OR REPLACE PROCEDURE YKK.create_diff_rpt_for_stockin (
   in_ticket_no        IN       fnzaiko.ticket_no%TYPE,
   in_unit_weight      IN       fnzaiko.real_unit_weight%TYPE,
   in_stockin_num      IN       fngset.nyusyusu%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fhdifferencestock_row   fhdifferencestock%ROWTYPE;
   wk_item_code               fnzaiko.zaikey%TYPE;
   wk_color_code              fnzaiko.color_code%TYPE;
   wk_stock_qty               fnzaiko.zaikosu%TYPE;
   wk_datetime14              VARCHAR2 (14);
   wk_diff_weight             NUMBER (6, 4);
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_diff_weight := 99.9999;

   SELECT zaikey, color_code, zaikosu
     INTO wk_item_code, wk_color_code, wk_stock_qty
     FROM fnzaiko
    WHERE fnzaiko.ticket_no = in_ticket_no AND ROWNUM = 1;

   wk_fhdifferencestock_row.difference_flag := '1';
   wk_fhdifferencestock_row.ticket_no := in_ticket_no;
   wk_fhdifferencestock_row.item_code := wk_item_code;
   wk_fhdifferencestock_row.color_code := wk_color_code;

   IF in_stockin_num != wk_stock_qty
   THEN
      IF in_stockin_num < wk_stock_qty
      THEN
         wk_fhdifferencestock_row.difference_type :=
                                    ykk_global_defination.difference_type_sub;
         wk_fhdifferencestock_row.difference_qty :=
                                                wk_stock_qty - in_stockin_num;
      ELSE
         wk_fhdifferencestock_row.difference_type :=
                                    ykk_global_defination.difference_type_add;
         wk_fhdifferencestock_row.difference_qty :=
                                                in_stockin_num - wk_stock_qty;
      END IF;

      IF wk_diff_weight >
                      in_unit_weight * wk_fhdifferencestock_row.difference_qty
      THEN
         wk_fhdifferencestock_row.difference_weight :=
                     in_unit_weight * wk_fhdifferencestock_row.difference_qty;
      ELSE
         wk_fhdifferencestock_row.difference_weight := wk_diff_weight;
      END IF;

      wk_fhdifferencestock_row.proc_flag := '0';
      wk_datetime14 := sysdate14_get ();
      wk_fhdifferencestock_row.register_date := SUBSTR (wk_datetime14, 0, 8);
      wk_fhdifferencestock_row.register_time := SUBSTR (wk_datetime14, 9, 6);

      INSERT INTO fhdifferencestock
           VALUES wk_fhdifferencestock_row;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000133;
      io_return_message := '生成在库差异数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END create_diff_rpt_for_stockin;
/


