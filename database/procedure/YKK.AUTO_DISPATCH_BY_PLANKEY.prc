DROP PROCEDURE YKK.AUTO_DISPATCH_BY_PLANKEY;

CREATE OR REPLACE PROCEDURE YKK.auto_dispatch_by_plankey (
   in_retrieval_plan_key   IN       fnsyotei.retrieval_plankey%TYPE,
   in_stockout_type        IN       CHAR,
   in_userid               IN       fnzaiko.userid%TYPE,
   in_username             IN       fnzaiko.username%TYPE,
   io_return_code          IN OUT   NUMBER,
   io_return_message       IN OUT   VARCHAR2
)
IS
   wk_number                       NUMBER;
   wk_count                        NUMBER;
   wk_expected_stockout_num        fnsyotei.retrieval_qty%TYPE;
   wk_actual_stockout_num          NUMBER;
   wk_rsvinfo_row                  fnsyotei%ROWTYPE;
   wk_temp_number                  NUMBER;
   wk_temp_expected_stockout_num   fnsyotei.retrieval_qty%TYPE;
   wk_temp_actual_stockout_num     NUMBER;

   CURSOR item_list (
      c_item_code    IN   fnzaiko.zaikey%TYPE,
      c_color_code   IN   fnzaiko.color_code%TYPE
   )
   IS
      SELECT   *
          FROM (SELECT   fnzaiko.skanosu AS available_stock_num,
                         fnzaiko.systemid, fnzaiko.zaikey AS item_code1,
                         fnzaiko.color_code AS color_code1,
                         fnzaiko.ticket_no AS ticket_no1,
                         fnzaiko.nyukohiji AS stockin_date, fnzaiko.sainyukbn
                    FROM fnzaiko, fnlocat, fnunit
                   WHERE fnzaiko.systemid = fnlocat.systemid
                     AND fnzaiko.manage_item_flag =
                                ykk_global_defination.manage_item_flag_managed
                     AND fnzaiko.zaikey = c_item_code
                     AND fnzaiko.color_code = c_color_code
                     AND fnzaiko.weight_report_complete_flag =
                                 ykk_global_defination.weight_report_completed
                     AND fnzaiko.storage_place_flag =
                             ykk_global_defination.storage_place_awc_warehouse
                     AND fnzaiko.skanosu > 0
                     AND TRIM (fnzaiko.memo) IS NULL
                     AND fnlocat.shikiflg =
                                      ykk_global_defination.location_available
                     AND fnlocat.accessflg =
                                     ykk_global_defination.location_accessable
                     AND fnlocat.zaijyoflg IN
                            (ykk_global_defination.locat_status_in_reserved,
                             ykk_global_defination.locat_status_instock,
                             ykk_global_defination.locat_status_out_reserved,
                             ykk_global_defination.locat_status_out_transporting
                            )
                     AND fnunit.ailestno = fnlocat.ailestno
                     AND fnunit.unitstat IN
                            (ykk_global_defination.unit_status_operating,
                             ykk_global_defination.unit_status_suspend,
                             ykk_global_defination.unit_status_trouble
                            )
                GROUP BY fnzaiko.skanosu,
                         fnzaiko.systemid,
                         fnzaiko.zaikey,
                         fnzaiko.color_code,
                         fnzaiko.ticket_no,
                         fnzaiko.nyukohiji,
                         fnzaiko.sainyukbn)
         WHERE 0 =
                  (SELECT COUNT (*)
                     FROM fnforbidretrieval
                    WHERE fnforbidretrieval.zaikey = item_code1
                      AND fnforbidretrieval.color_code = color_code1
                      AND from_ticketno < ticket_no1
                      AND to_ticketno > ticket_no1)
      ORDER BY sainyukbn DESC, stockin_date ASC;

   item_list_iterator              item_list%ROWTYPE;
   next_loop_exception             EXCEPTION;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_actual_stockout_num := 0;
   get_reservation_info (in_retrieval_plan_key,
                         wk_rsvinfo_row,
                         io_return_code,
                         io_return_message
                        );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   wk_expected_stockout_num :=
             wk_rsvinfo_row.retrieval_qty - wk_rsvinfo_row.retrieval_alloc_qty;

   SELECT COUNT (*)
     INTO wk_count
     FROM fnforbidretrieval
    WHERE zaikey = wk_rsvinfo_row.zaikey AND TRIM (color_code) IS NULL;

   IF wk_count > 0
   THEN
      io_return_code := 8000173;
      io_return_message := '没有符合出库条件的货品';
      GOTO endlabel;
   END IF;

   OPEN item_list (wk_rsvinfo_row.zaikey, wk_rsvinfo_row.color_code);

   LOOP
      FETCH item_list
       INTO item_list_iterator;

      IF item_list%NOTFOUND = TRUE
      THEN
         IF item_list%ROWCOUNT = 0
         THEN
            io_return_code := 8000173;
            io_return_message := '没有符合出库条件的货品';
            GOTO endlabel;
         END IF;

         EXIT;
      END IF;

      EXIT WHEN wk_expected_stockout_num = 0;
      wk_number := 0;
      wk_temp_number := wk_number;
      wk_temp_expected_stockout_num := wk_expected_stockout_num;
      wk_temp_actual_stockout_num := wk_actual_stockout_num;

      BEGIN
         IF wk_expected_stockout_num > item_list_iterator.available_stock_num
         THEN
            wk_number := item_list_iterator.available_stock_num;
            wk_expected_stockout_num := wk_expected_stockout_num - wk_number;
            wk_actual_stockout_num := wk_actual_stockout_num + wk_number;
         ELSE
            wk_number := wk_expected_stockout_num;
            wk_expected_stockout_num := 0;
            wk_actual_stockout_num := wk_actual_stockout_num + wk_number;
         END IF;

         dispatch_by_systemid (item_list_iterator.systemid,
                               wk_number,
                               in_retrieval_plan_key,
                               ykk_global_defination.dispatch_type_auto,
                               in_stockout_type,
                               in_userid,
                               in_username,
                               io_return_code,
                               io_return_message
                              );

         IF io_return_code = 8000105  --搬送路径不存在或检索搬送路径时发生错误
         THEN
            wk_number := wk_temp_number;
            wk_expected_stockout_num := wk_temp_expected_stockout_num;
            wk_actual_stockout_num := wk_temp_actual_stockout_num;
            RAISE next_loop_exception;
         ELSIF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      EXCEPTION
         WHEN next_loop_exception
         THEN
            io_return_code := 0;
            io_return_message := '';
      END;
   END LOOP;

   update_rsv_data_for_stockout (in_retrieval_plan_key,
                                 wk_actual_stockout_num,
                                 in_stockout_type,
                                 io_return_code,
                                 io_return_message
                                );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   <<endlabel>>
   IF item_list%ISOPEN = TRUE
   THEN
      CLOSE item_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF item_list%ISOPEN = TRUE
      THEN
         CLOSE item_list;
      END IF;

      io_return_code := 8000113;
      io_return_message := '出库自动分配设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END auto_dispatch_by_plankey;
/


