DROP PROCEDURE STOCKOUT_RELEASE_SETTING;

CREATE OR REPLACE PROCEDURE     stockout_release_setting (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_fngset_row   fngset%ROWTYPE;
   wk_count        NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT   *
     INTO   wk_fngset_row
     FROM   fngset
    WHERE   schno = in_schedule_no
            AND syoriflg = ykk_global_defination.display_info_not_processed;

   IF TRIM (wk_fngset_row.color_code) IS NOT NULL
   THEN
      SELECT   COUNT ( * )
        INTO   wk_count
        FROM   fnforbidretrieval
       WHERE       zaikey = wk_fngset_row.zaikey
               AND from_ticketno = wk_fngset_row.from_ticket_no
               AND to_ticketno = wk_fngset_row.to_ticket_no
               AND from_stock_datetime = wk_fngset_row.from_stock_datetime
               AND to_stock_datetime = wk_fngset_row.to_stock_datetime
               AND color_code = wk_fngset_row.color_code;
   ELSE
      SELECT   COUNT ( * )
        INTO   wk_count
        FROM   fnforbidretrieval
       WHERE       zaikey = wk_fngset_row.zaikey
               AND from_ticketno = wk_fngset_row.from_ticket_no
               AND to_ticketno = wk_fngset_row.to_ticket_no
               AND from_stock_datetime = wk_fngset_row.from_stock_datetime
               AND to_stock_datetime = wk_fngset_row.to_stock_datetime
               AND TRIM (color_code) IS NULL;
   END IF;

   IF wk_count = 0
   THEN
      io_return_code := 8000174;
      io_return_message := '没有符合条件的出库禁止信息';
   ELSE
      IF TRIM (wk_fngset_row.color_code) IS NOT NULL
      THEN
         DELETE   fnforbidretrieval
          WHERE       zaikey = wk_fngset_row.zaikey
                  AND from_ticketno = wk_fngset_row.from_ticket_no
                  AND to_ticketno = wk_fngset_row.to_ticket_no
                  AND from_stock_datetime = wk_fngset_row.from_stock_datetime
                  AND to_stock_datetime = wk_fngset_row.to_stock_datetime
                  AND color_code = wk_fngset_row.color_code;
      ELSE
         DELETE   fnforbidretrieval
          WHERE       zaikey = wk_fngset_row.zaikey
                  AND from_ticketno = wk_fngset_row.from_ticket_no
                  AND to_ticketno = wk_fngset_row.to_ticket_no
                  AND from_stock_datetime = wk_fngset_row.from_stock_datetime
                  AND to_stock_datetime = wk_fngset_row.to_stock_datetime
                  AND TRIM (color_code) IS NULL;
      END IF;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000170;
      io_return_message := '出库禁止设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockout_release_setting; 

/
