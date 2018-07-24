DROP PROCEDURE YKK.CORE_STOCKOUT_AUTO_SETTING;

CREATE OR REPLACE PROCEDURE YKK.core_stockout_auto_setting (
   in_schedule_no      IN       fngset.schno%TYPE,
   in_stockout_type    IN       CHAR,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_count               NUMBER;
   wk_userid              fngset.userid%TYPE;
   wk_username            fngset.username%TYPE;

   CURSOR fngset_list (c_schedule_no IN fngset.schno%TYPE)
   IS
--      SELECT DISTINCT (retrieval_plankey)
--                 FROM fngset
--                WHERE fngset.schno = c_schedule_no
--                  AND syoriflg =
--                              ykk_global_defination.display_info_not_processed;
      SELECT   fngset.retrieval_plankey, fnsyotei.start_date
          FROM fngset, fnsyotei
         WHERE fngset.schno = c_schedule_no
           AND syoriflg = ykk_global_defination.display_info_not_processed
           AND fngset.retrieval_plankey = fnsyotei.retrieval_plankey
      ORDER BY fnsyotei.start_date;

   fngset_list_iterator   fngset_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT userid
     INTO wk_userid
     FROM fngset
    WHERE fngset.schno = in_schedule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed
      AND ROWNUM = 1;

   SELECT username
     INTO wk_username
     FROM userattribute
    WHERE userid = wk_userid;

   OPEN fngset_list (in_schedule_no);

   LOOP
      FETCH fngset_list
       INTO fngset_list_iterator;

      IF fngset_list%NOTFOUND = TRUE
      THEN
         IF fngset_list%ROWCOUNT = 0
         THEN
            io_return_code := 8000102;
            io_return_message := '数据不存在';
         END IF;

         EXIT;
      END IF;

      check_reservation_info (fngset_list_iterator.retrieval_plankey,
                              io_return_code,
                              io_return_message
                             );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      auto_dispatch_by_plankey (fngset_list_iterator.retrieval_plankey,
                                in_stockout_type,
                                wk_userid,
                                wk_username,
                                io_return_code,
                                io_return_message
                               );

      IF io_return_code != 0
      THEN
         GOTO endlabel;
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

      io_return_code := 8000113;
      io_return_message := '出库自动分配设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END core_stockout_auto_setting;
/


