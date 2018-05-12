DROP PROCEDURE AWC.RESERVATION_DATA_DELETE_START;

CREATE OR REPLACE PROCEDURE AWC.reservation_data_delete_start (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_fnsyotei_cancel_row   fnsyotei_cancel%ROWTYPE;

   CURSOR fngset_list (
      c_schedule_no   IN            fngset.schno%TYPE
   )
   IS
      SELECT   fngset.retrieval_plankey,
               fngset.retrieval_qty,
               fngset.retrieval_alloc_qty,
               fngset.necessary_qty
        FROM   fngset
       WHERE   fngset.schno = c_schedule_no
               AND fngset.syoriflg =
                     ykk_global_defination.display_info_not_processed;

   fngset_list_iterator     fngset_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   wk_fnsyotei_cancel_row.proc_flag := '2';
   wk_fnsyotei_cancel_row.send_flag := '0';
   wk_fnsyotei_cancel_row.create_datetime := sysdate14_get ();

   OPEN fngset_list (in_schedule_no);

   LOOP
      FETCH fngset_list INTO   fngset_list_iterator;

      IF fngset_list%NOTFOUND = TRUE
      THEN
         IF fngset_list%ROWCOUNT = 0
         THEN
            io_return_code := 8000102;
            io_return_message := '数据不存在';
         END IF;

         EXIT;
      END IF;

      SELECT   retrieval_no,
               serial_no,
               zaikey,
               color_code,
               necessary_qty,
               necessary_qty - retrieval_alloc_qty
        INTO   wk_fnsyotei_cancel_row.retrieval_no,
               wk_fnsyotei_cancel_row.serial_no,
               wk_fnsyotei_cancel_row.zaikey,
               wk_fnsyotei_cancel_row.color_code,
               wk_fnsyotei_cancel_row.cancel_qty,
               wk_fnsyotei_cancel_row.cancel_alloc_qty
        FROM   fnsyotei
       WHERE   retrieval_plankey = fngset_list_iterator.retrieval_plankey;

      INSERT INTO fnsyotei_cancel
        VALUES   wk_fnsyotei_cancel_row;



      IF ykk_global_defination.write_fnsyotei_log_flag != '0'
      THEN
         write_fnsyotei_log (fngset_list_iterator.retrieval_plankey,
                             'before reservation_data_delete_start',
                             'worgsoft',
                             io_return_code,
                             io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;

      UPDATE   FNSYOTEI
         SET   fnsyotei.proc_flag =
                  ykk_global_defination.reservation_info_processed,
               fnsyotei.retrieval_alloc_qty = necessary_qty,
               fnsyotei.retrieval_qty = necessary_qty
       WHERE   retrieval_plankey = fngset_list_iterator.retrieval_plankey
               AND retrieval_qty = fngset_list_iterator.retrieval_qty
               AND retrieval_alloc_qty =
                     fngset_list_iterator.retrieval_alloc_qty
               AND necessary_qty = fngset_list_iterator.necessary_qty;


      IF sql%ROWCOUNT = 0
      THEN
         io_return_code := 8000202;
         io_return_message :=
            '在设定期间可能有其它操作对预约数据进行了修改，请刷新页面并重新执行设定操作';
         GOTO endlabel;
      END IF;

      IF ykk_global_defination.write_fnsyotei_log_flag != '0'
      THEN
         write_fnsyotei_log (fngset_list_iterator.retrieval_plankey,
                             'after reservation_data_delete_start',
                             'worgsoft',
                             io_return_code,
                             io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;
   END LOOP;

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

      io_return_code := 8000207;
      io_return_message := '数据删除时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END reservation_data_delete_start;
/
