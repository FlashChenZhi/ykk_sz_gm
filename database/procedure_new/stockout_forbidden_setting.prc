DROP PROCEDURE AWC.STOCKOUT_FORBIDDEN_SETTING;

CREATE OR REPLACE PROCEDURE AWC.stockout_forbidden_setting (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_fngset_row           fngset%ROWTYPE;

   CURSOR fnzaiko_list (in_item_code IN fnzaiko.zaikey%TYPE)
   IS
      SELECT   *
        FROM   fnzaiko
       WHERE   zaikey = in_item_code;

   fnzaiko_list_iterator   fnzaiko_list%ROWTYPE;

   PROCEDURE create_stock_forbidden_data (
      in_item_code             IN     fnforbidretrieval.zaikey%TYPE,
      in_color_code            IN     fnforbidretrieval.color_code%TYPE,
      in_from_ticket_no        IN     fnforbidretrieval.from_ticketno%TYPE,
      in_to_ticket_no          IN     fnforbidretrieval.to_ticketno%TYPE,
      in_from_stock_datetime   IN     fnforbidretrieval.from_stock_datetime%TYPE,
      in_to_stock_datetime     IN     fnforbidretrieval.to_stock_datetime%TYPE,
      io_return_code           IN OUT NUMBER,
      io_return_message        IN OUT VARCHAR2
   )
   IS
      wk_color_code   fnforbidretrieval.color_code%TYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      IF TRIM (in_color_code) IS NULL
      THEN
         wk_color_code := ' ';
      ELSE
         wk_color_code := in_color_code;
      END IF;

      INSERT INTO fnforbidretrieval (zaikey,
                                     color_code,
                                     from_ticketno,
                                     to_ticketno,
                                     register_date_time,
                                     from_stock_datetime,
                                     to_stock_datetime)
        VALUES   (in_item_code,
                  wk_color_code,
                  in_from_ticket_no,
                  in_to_ticket_no,
                  sysdate14_get (),
                  in_from_stock_datetime,
                  in_to_stock_datetime);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000169;
         io_return_message := '生成出库禁止数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_stock_forbidden_data;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT   *
     INTO   wk_fngset_row
     FROM   fngset
    WHERE   schno = in_schedule_no
            AND syoriflg = ykk_global_defination.display_info_not_processed;

   create_stock_forbidden_data (wk_fngset_row.zaikey,
                                wk_fngset_row.color_code,
                                wk_fngset_row.from_ticket_no,
                                wk_fngset_row.to_ticket_no,
                                wk_fngset_row.from_stock_datetime,
                                wk_fngset_row.to_stock_datetime,
                                io_return_code,
                                io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

  <<endlabel>>
   IF fnzaiko_list%ISOPEN = TRUE
   THEN
      CLOSE fnzaiko_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF fnzaiko_list%ISOPEN = TRUE
      THEN
         CLOSE fnzaiko_list;
      END IF;

      io_return_code := 8000170;
      io_return_message := '出库禁止设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockout_forbidden_setting;
/
