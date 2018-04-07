DROP PROCEDURE YKK.EXTERNAL_STOCKOUT_START;

CREATE OR REPLACE PROCEDURE YKK.external_stockout_start (
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_count               NUMBER;
   wk_mckey               fnhanso.mckey%TYPE;
   wk_stockout_mode       fnhanso.sijisyosai%TYPE;

   CURSOR fngset_list
   IS
      SELECT   fnhanso.systemid, fnhanso.endstno
          FROM fnhanso
         WHERE fnhanso.mckey IN (
                                SELECT DISTINCT (mckey)
                                           FROM fnsiji
                                          WHERE retrieval_station IN
                                                          ('23', '25', '26'))
           AND fnhanso.nyusyukbn =
                            ykk_global_defination.transportation_type_stockout
           AND fnhanso.hjyotaiflg =
                           ykk_global_defination.transportation_status_standby
      GROUP BY fnhanso.systemid, fnhanso.endstno
      ORDER BY endstno;

   fngset_list_iterator   fngset_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   OPEN fngset_list;

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

      SELECT COUNT (*)
        INTO wk_count
        FROM fnhanso
       WHERE systemid = fngset_list_iterator.systemid
         AND nyusyukbn = ykk_global_defination.transportation_type_stockout
         AND hjyotaiflg = ykk_global_defination.transportation_status_standby;

      IF wk_count > 0
      THEN
         external_append_trans_data (fngset_list_iterator.systemid,
                                     fngset_list_iterator.endstno,
                                     io_return_code,
                                     io_return_message
                                    );

         IF io_return_code = 8000102
         THEN
            io_return_code := 0;
            io_return_message := '';
         END IF;

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;

         external_merge_and_start_trans (fngset_list_iterator.systemid,
                                         fngset_list_iterator.endstno,
                                         '000000',
                                         wk_stockout_mode,
                                         io_return_code,
                                         io_return_message
                                        );

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

      io_return_code := 8000122;
      io_return_message := '出库开始设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END external_stockout_start;
/


