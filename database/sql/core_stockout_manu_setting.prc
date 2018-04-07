DROP PROCEDURE CORE_STOCKOUT_MANU_SETTING;

CREATE OR REPLACE PROCEDURE     core_stockout_manu_setting (
   in_schedule_no      IN     fngset.schno%TYPE,
   in_stockout_type    IN     CHAR,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_retrieval_plankey        fnsyotei.retrieval_plankey%TYPE;
   wk_expected_stockout_num    fnsyotei.retrieval_qty%TYPE;
   wk_available_stockout_num   fnzaiko.skanosu%TYPE;
   wk_actual_stockout_num      NUMBER;
   wk_stockout_number          NUMBER;
   wk_count                    NUMBER;
   wk_fnzaiko_row              fnzaiko%ROWTYPE;
   wk_userid                   fngset.userid%TYPE;
   wk_username                 fngset.username%TYPE;
   wk_necessary_qty            fnsyotei.necessary_qty%TYPE;
   wk_retrieval_alloc_qty      fnsyotei.retrieval_alloc_qty%TYPE;
   wk_pr_no                    fnsyotei.pr_no%TYPE;

   CURSOR fngset_list (
      c_schedule_no   IN            fngset.schno%TYPE
   )
   IS
      SELECT   DISTINCT systemid, subdivide_flag
        FROM   fngset
       WHERE   fngset.schno = c_schedule_no
               AND syoriflg =
                     ykk_global_defination.display_info_not_processed;

   fngset_list_iterator        fngset_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_expected_stockout_num := 0;
   wk_actual_stockout_num := 0;
   wk_available_stockout_num := 0;
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT   userid
     INTO   wk_userid
     FROM   fngset
    WHERE       fngset.schno = in_schedule_no
            AND syoriflg = ykk_global_defination.display_info_not_processed
            AND ROWNUM = 1;

   SELECT   username
     INTO   wk_username
     FROM   userattribute
    WHERE   userid = wk_userid;

   SELECT   DISTINCT retrieval_plankey, necessary_qty, retrieval_alloc_qty
     INTO   wk_retrieval_plankey, wk_necessary_qty, wk_retrieval_alloc_qty
     FROM   fngset
    WHERE   fngset.schno = in_schedule_no
            AND syoriflg = ykk_global_defination.display_info_not_processed;

   check_reservation_info (wk_retrieval_plankey,
                           io_return_code,
                           io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT   retrieval_qty - retrieval_alloc_qty
     INTO   wk_expected_stockout_num
     FROM   fnsyotei
    WHERE   retrieval_plankey = wk_retrieval_plankey;

   OPEN fngset_list (in_schedule_no);

   LOOP
      FETCH fngset_list INTO   fngset_list_iterator;

      IF fngset_list%NOTFOUND = TRUE
      THEN
         IF fngset_list%ROWCOUNT = 0
         THEN
            io_return_code := 8000102;
            io_return_message := '数据不存在';
            GOTO endlabel;
         END IF;

         EXIT;
      END IF;

      EXIT WHEN wk_expected_stockout_num = 0;

      SELECT   *
        INTO   wk_fnzaiko_row
        FROM   fnzaiko
       WHERE   systemid = fngset_list_iterator.systemid;

      --      SELECT   COUNT ( * )
      --        INTO   wk_count
      --        FROM   fnforbidretrieval
      --       WHERE   zaikey = wk_fnzaiko_row.zaikey
      --               AND NVL (TRIM (color_code), ' ') =
      --                     NVL (TRIM (wk_fnzaiko_row.color_code), ' ');

      --      IF wk_count > 0
      --      THEN
      --         io_return_code := 8000117;
      --         io_return_message := '货品或货位不符合出库条件';
      --         GOTO endlabel;
      --      END IF;

      SELECT   COUNT ( * )
        INTO   wk_count
        FROM   (  SELECT   fnzaiko.zaikey AS item_code1,
                           fnzaiko.color_code AS color_code1,
                           fnzaiko.ticket_no AS ticket_no1,
                           fnzaiko.nyukohiji AS stockin_date
                    FROM   fnzaiko, fnlocat, fnunit
                   WHERE   fnzaiko.systemid = fngset_list_iterator.systemid
                           AND fnzaiko.systemid = fnlocat.systemid
                           AND fnzaiko.manage_item_flag =
                                 ykk_global_defination.manage_item_flag_managed
                           AND fnzaiko.weight_report_complete_flag =
                                 ykk_global_defination.weight_report_completed
                           AND fnzaiko.storage_place_flag =
                                 ykk_global_defination.storage_place_awc_warehouse
                           AND fnzaiko.skanosu > 0
                           AND fnlocat.shikiflg =
                                 ykk_global_defination.location_available
                           AND fnlocat.accessflg =
                                 ykk_global_defination.location_accessable
                           AND fnlocat.zaijyoflg IN
                                    (ykk_global_defination.locat_status_in_reserved,
                                     ykk_global_defination.locat_status_instock,
                                     ykk_global_defination.locat_status_out_reserved,
                                     ykk_global_defination.locat_status_out_transporting)
                           AND fnunit.ailestno = fnlocat.ailestno
                           AND fnunit.unitstat IN
                                    (ykk_global_defination.unit_status_operating,
                                     ykk_global_defination.unit_status_suspend,
                                     ykk_global_defination.unit_status_trouble)
                GROUP BY   fnzaiko.skanosu,
                           fnzaiko.systemid,
                           fnzaiko.zaikey,
                           fnzaiko.color_code,
                           fnzaiko.ticket_no,
                           fnzaiko.nyukohiji)
       WHERE   0 =
                  (SELECT   COUNT ( * )
                     FROM   fnforbidretrieval
                    WHERE   fnforbidretrieval.zaikey = item_code1
                            AND (fnforbidretrieval.color_code = color_code1
                                 OR TRIM (fnforbidretrieval.color_code) IS NULL)
                            AND from_ticketno <= ticket_no1
                            AND to_ticketno >= ticket_no1
                            AND from_stock_datetime <= stockin_date
                            AND to_stock_datetime >= stockin_date);

      IF wk_count = 0
      THEN
         io_return_code := 8000117;
         io_return_message := '货品或货位不符合出库条件';
         GOTO endlabel;
      END IF;

      SELECT   skanosu
        INTO   wk_available_stockout_num
        FROM   fnzaiko
       WHERE   systemid = fngset_list_iterator.systemid;

      IF wk_available_stockout_num < wk_expected_stockout_num
      THEN
         wk_stockout_number := wk_available_stockout_num;
      ELSE
         wk_stockout_number := wk_expected_stockout_num;
      END IF;

      wk_expected_stockout_num :=
         wk_expected_stockout_num - wk_stockout_number;
      wk_actual_stockout_num := wk_actual_stockout_num + wk_stockout_number;



      SELECT   pr_no
        INTO   wk_pr_no
        FROM   fnsyotei
       WHERE   retrieval_plankey = wk_retrieval_plankey;

      IF TRIM (wk_pr_no) IS NULL
      THEN
         fngset_list_iterator.subdivide_flag := '0';
      END IF;


      dispatch_by_systemid (fngset_list_iterator.systemid,
                            wk_stockout_number,
                            wk_retrieval_plankey,
                            ykk_global_defination.dispatch_type_menu,
                            in_stockout_type,
                            fngset_list_iterator.subdivide_flag,
                            wk_userid,
                            wk_username,
                            io_return_code,
                            io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END LOOP;

   update_rsv_data_for_stockout (wk_retrieval_plankey,
                                 wk_necessary_qty,
                                 wk_retrieval_alloc_qty,
                                 wk_actual_stockout_num,
                                 io_return_code,
                                 io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

  <<endlabel>>
   IF fngset_list%ISOPEN = TRUE
   THEN
      CLOSE fngset_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF fngset_list%ISOPEN = TRUE
      THEN
         CLOSE fngset_list;
      END IF;

      io_return_code := 8000114;
      io_return_message := '出库手动分配设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END core_stockout_manu_setting; 

/
