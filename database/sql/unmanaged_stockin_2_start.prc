DROP PROCEDURE UNMANAGED_STOCKIN_2_START;

CREATE OR REPLACE PROCEDURE     unmanaged_stockin_2_start (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row   fngset%ROWTYPE;
   wk_systemid     fnzaiko.systemid%TYPE;

   PROCEDURE create_stock_data (
      in_systemid         IN       fnzaiko.systemid%TYPE,
      in_bucket_no        IN       fnzaiko.bucket_no%TYPE,
      in_item_code        IN       fnzaiko.zaikey%TYPE,
      in_color_code       IN       fnzaiko.color_code%TYPE,
      in_stockin_num      IN       fnzaiko.zaikosu%TYPE,
      io_return_code      IN OUT   NUMBER,
      io_return_message   IN OUT   VARCHAR2
   )
   IS
   BEGIN
      UPDATE fnzaiko
         SET bucket_no = ' '
       WHERE bucket_no = in_bucket_no
         AND storage_place_flag =
                            ykk_global_defination.storage_place_flat_warehouse;

      INSERT INTO fnzaiko
                  (systemid, bucket_no, zaikey, color_code,
                   zaikosu, skanosu,
                   manage_item_flag,
                   storage_place_flag
                  )
           VALUES (in_systemid, in_bucket_no, in_item_code, in_color_code,
                   in_stockin_num, in_stockin_num,
                   ykk_global_defination.manage_item_flag_unmanaged,
                   ykk_global_defination.storage_place_flat_warehouse
                  );
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000163;
         io_return_message := '生成库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_stock_data;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT *
     INTO wk_fngset_row
     FROM fngset
    WHERE schno = in_schedule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   check_bucket_not_instock
                           (wk_fngset_row.bucket_no,
                            ykk_global_defination.storage_place_awc_warehouse,
                            io_return_code,
                            io_return_message
                           );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   generate_system_id (wk_systemid, io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   create_stock_data (wk_systemid,
                      wk_fngset_row.bucket_no,
                      wk_fngset_row.zaikey,
                      wk_fngset_row.color_code,
                      wk_fngset_row.nyusyusu,
                      io_return_code,
                      io_return_message
                     );

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
      io_return_code := 8000164;
      io_return_message := '管理外入库设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END unmanaged_stockin_2_start; 

/
