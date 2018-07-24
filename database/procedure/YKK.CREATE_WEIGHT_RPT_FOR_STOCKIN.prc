DROP PROCEDURE YKK.CREATE_WEIGHT_RPT_FOR_STOCKIN;

CREATE OR REPLACE PROCEDURE YKK.create_weight_rpt_for_stockin (
   in_ticket_no        IN       fnzaiko.ticket_no%TYPE,
   in_unit_weight      IN       fnzaiko.real_unit_weight%TYPE,
   in_measure_weight   IN       fngset.measure_weight%TYPE,
   in_stockin_num      IN       fngset.nyusyusu%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fhweightreport_row   fhweightreport%ROWTYPE;
   wk_move_flag            fnzaiko.move_flag%TYPE;
   wk_item_code            fnzaiko.zaikey%TYPE;
   wk_master_unit_weight   fmzkey.master_unit_weight%TYPE;
   wk_datetime14           VARCHAR2 (14);
   wk_manage_item_flag     fnzaiko.manage_item_flag%TYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT move_flag, zaikey, manage_item_flag
     INTO wk_move_flag, wk_item_code, wk_manage_item_flag
     FROM fnzaiko
    WHERE fnzaiko.ticket_no = in_ticket_no AND ROWNUM = 1;

   SELECT master_unit_weight
     INTO wk_master_unit_weight
     FROM fmzkey
    WHERE zaikey = wk_item_code AND manage_item_flag = wk_manage_item_flag;

   wk_fhweightreport_row.ticket_no := in_ticket_no;

   IF wk_master_unit_weight = in_unit_weight
   THEN
      wk_fhweightreport_row.weight_flag := '0';
   ELSE
      wk_fhweightreport_row.weight_flag := '1';
   END IF;

   wk_fhweightreport_row.stock_unit_weight := in_unit_weight;
   wk_fhweightreport_row.measure_qty := in_stockin_num;
   wk_fhweightreport_row.measure_weight := in_measure_weight;
   wk_fhweightreport_row.move_flag := wk_move_flag;
   wk_fhweightreport_row.pending_flag := '0';
   wk_fhweightreport_row.proc_flag := '0';
   wk_datetime14 := sysdate14_get ();
   wk_fhweightreport_row.register_date := SUBSTR (wk_datetime14, 0, 8);
   wk_fhweightreport_row.register_time := SUBSTR (wk_datetime14, 9, 6);

   INSERT INTO fhweightreport
        VALUES wk_fhweightreport_row;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000132;
      io_return_message := '生成称重报告数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END create_weight_rpt_for_stockin;
/


