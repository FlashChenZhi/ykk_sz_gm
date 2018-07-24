DROP PROCEDURE STOCKIN_2;

CREATE OR REPLACE PROCEDURE     stockin_2 (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_fngset_row   fngset%ROWTYPE;

   PROCEDURE update_stock_data_for_stockin2 (
      in_ticket_no        IN     fnzaiko.ticket_no%TYPE,
      in_bucket_no        IN     fnzaiko.bucket_no%TYPE,
      in_unit_weight      IN     fnzaiko.real_unit_weight%TYPE,
      in_memo             IN     fnzaiko.memo%TYPE,
      in_restockin_flag   IN     fnzaiko.sainyukbn%TYPE,
      in_bag_flag         IN     fnzaiko.bag_flag%TYPE,
      in_userid           IN     fnzaiko.userid%TYPE,
      in_username         IN     fnzaiko.username%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      wk_restockin_flag   fnzaiko.sainyukbn%TYPE;
   BEGIN
      UPDATE   fnzaiko
         SET   bucket_no = ' '
       WHERE   bucket_no = in_bucket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_flat_warehouse;

      IF TRIM (in_restockin_flag) IS NULL
      THEN
         wk_restockin_flag := ' ';
      ELSE
         wk_restockin_flag := in_restockin_flag;
      END IF;

      UPDATE   fnzaiko
         SET   bucket_no = in_bucket_no,
               real_unit_weight = in_unit_weight,
               memo = in_memo,
               sainyukbn = wk_restockin_flag,
               bag_flag = in_bag_flag,
               userid = in_userid,
               username = in_username
       WHERE   ticket_no = in_ticket_no
               AND storage_place_flag =
                     ykk_global_defination.storage_place_flat_warehouse;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_for_stockin2;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT   *
     INTO   wk_fngset_row
     FROM   fngset
    WHERE   schno = in_schedule_no
            AND syoriflg = ykk_global_defination.display_info_not_processed;

   check_bucket_not_instock (
      wk_fngset_row.bucket_no,
      ykk_global_defination.storage_place_awc_warehouse,
      io_return_code,
      io_return_message
   );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   update_stock_data_for_stockin2 (wk_fngset_row.ticket_no,
                                   wk_fngset_row.bucket_no,
                                   wk_fngset_row.unit_weight,
                                   wk_fngset_row.memo,
                                   wk_fngset_row.sainyukbn,
                                   wk_fngset_row.bag_flag,
                                   wk_fngset_row.userid,
                                   wk_fngset_row.username,
                                   io_return_code,
                                   io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   stockin_2_cut_away (wk_fngset_row.motostno,
                       io_return_code,
                       io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

  <<endlabel>>
   IF io_return_code != 0
   THEN
      ROLLBACK;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      ROLLBACK;
      io_return_code := 8000139;
      io_return_message := '入库设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockin_2; 

/
