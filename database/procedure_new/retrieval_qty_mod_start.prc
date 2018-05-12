DROP PROCEDURE AWC.RETRIEVAL_QTY_MOD_START;

CREATE OR REPLACE PROCEDURE AWC.retrieval_qty_mod_start (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   CURSOR fngset_list (
      c_schedule_no   IN            fngset.schno%TYPE
   )
   IS
      SELECT   fngset.retrieval_plankey,
               fngset.necessary_qty,
               fngset.nyusyusu AS new_retrieval_qty,
               fngset.retrieval_qty AS old_retrieval_qty,
               fngset.retrieval_alloc_qty
        FROM   fngset
       WHERE   fngset.schno = c_schedule_no
               AND fngset.syoriflg =
                     ykk_global_defination.display_info_not_processed;

   fngset_list_iterator   fngset_list%ROWTYPE;
BEGIN
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


      IF ykk_global_defination.write_fnsyotei_log_flag != '0'
      THEN
         write_fnsyotei_log (fngset_list_iterator.retrieval_plankey,
                             'before retrieval_qty_mod_start',
                             'worgsoft',
                             io_return_code,
                             io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;

      UPDATE   fnsyotei
         SET   retrieval_qty = fngset_list_iterator.new_retrieval_qty,
               proc_flag =
                  DECODE (
                     BITAND (
                        DECODE (fngset_list_iterator.new_retrieval_qty,
                                fnsyotei.necessary_qty, 1,
                                0),
                        DECODE (fngset_list_iterator.new_retrieval_qty,
                                fnsyotei.retrieval_alloc_qty, 1,
                                0)
                     ),
                     1,
                     ykk_global_defination.reservation_info_processed,
                     ykk_global_defination.reservation_info_not_processed
                  )
       WHERE   fnsyotei.retrieval_plankey =
                  fngset_list_iterator.retrieval_plankey
               AND fnsyotei.necessary_qty =
                     fngset_list_iterator.necessary_qty
               AND fnsyotei.retrieval_qty =
                     fngset_list_iterator.old_retrieval_qty
               AND fnsyotei.retrieval_alloc_qty =
                     fngset_list_iterator.retrieval_alloc_qty;

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
                             'after retrieval_qty_mod_start',
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

      io_return_code := 8000208;
      io_return_message :=
         '生产管理指示数变更设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END retrieval_qty_mod_start;
/
