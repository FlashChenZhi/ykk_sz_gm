DROP PROCEDURE YKK.STOCKOUT_START;

CREATE OR REPLACE PROCEDURE YKK.stockout_start (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_count               NUMBER;
   wk_mckey               fnhanso.mckey%TYPE;
   wk_endstno             fnhanso.endstno%TYPE;
   wk_group_size          NUMBER;
   wk_group_no            fnhanso.groupno%TYPE;
   --wk_stockout_mode       fnhanso.sijisyosai%TYPE;
   max_group_size         fnretrieval_st.group_max_num%TYPE;
   wk_retrieval_station   fnsiji.retrieval_station%TYPE;

   CURSOR fngset_list (c_schedule_no IN fngset.schno%TYPE)
   IS
      SELECT   fngset.systemid, fnhanso.endstno  --, fnsiji.retrieval_station
          FROM fngset, fnhanso                                     --, fnsiji
         WHERE fngset.schno = c_schedule_no
           AND fngset.syoriflg =
                              ykk_global_defination.display_info_not_processed
           AND fngset.systemid = fnhanso.systemid
           AND fnhanso.nyusyukbn =
                            ykk_global_defination.transportation_type_stockout
           AND fnhanso.hjyotaiflg =
                           ykk_global_defination.transportation_status_standby
           --AND fnhanso.mckey = fnsiji.mckey
           AND fnhanso.mckey IN (
                  SELECT mckey
                    FROM fngset
                   WHERE fngset.schno = c_schedule_no
                     AND fngset.syoriflg =
                              ykk_global_defination.display_info_not_processed)
      GROUP BY fngset.systemid, fnhanso.endstno   --, fnsiji.retrieval_station
      ORDER BY endstno;

   fngset_list_iterator   fngset_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_endstno := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

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

      SELECT COUNT (*)
        INTO wk_count
        FROM fnhanso
       WHERE systemid = fngset_list_iterator.systemid
         AND nyusyukbn = ykk_global_defination.transportation_type_stockout
         AND hjyotaiflg = ykk_global_defination.transportation_status_standby;

      IF wk_count > 0
      THEN
         append_data_to_started_trans (in_schedule_no,
                                       fngset_list_iterator.systemid,
                                       fngset_list_iterator.endstno,
                                       io_return_code,
                                       io_return_message
                                      );

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;
   END LOOP;

   IF fngset_list%ISOPEN = TRUE
   THEN
      CLOSE fngset_list;
   END IF;

   OPEN fngset_list (in_schedule_no);

   LOOP
      FETCH fngset_list
       INTO fngset_list_iterator;

      IF fngset_list%NOTFOUND = TRUE
      THEN
         IF fngset_list%ROWCOUNT = 0
         THEN
            --io_return_code := 8000102;
            --io_return_message := '数据不存在';
			goto endlabel;
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
         merge_trans (in_schedule_no,
                      fngset_list_iterator.systemid,
                      fngset_list_iterator.endstno,
                      io_return_code,
                      io_return_message
                     );

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;
   END LOOP;

   IF fngset_list%ISOPEN = TRUE
   THEN
      CLOSE fngset_list;
   END IF;

   OPEN fngset_list (in_schedule_no);

   LOOP
      FETCH fngset_list
       INTO fngset_list_iterator;

      IF fngset_list%NOTFOUND = TRUE
      THEN
         IF fngset_list%ROWCOUNT = 0
         THEN
            --io_return_code := 8000102;
            --io_return_message := '数据不存在';
			goto endlabel;
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
         IF wk_endstno != fngset_list_iterator.endstno
         THEN
            wk_endstno := fngset_list_iterator.endstno;
            wk_group_size := 0;

            SELECT retrieval_station
              INTO wk_retrieval_station
              FROM fnsiji
             WHERE mckey =
                      (SELECT mckey
                         FROM fnhanso
                        WHERE systemid = fngset_list_iterator.systemid
                          AND nyusyukbn =
                                 ykk_global_defination.transportation_type_stockout
                          AND hjyotaiflg =
                                 ykk_global_defination.transportation_status_standby
                          AND ROWNUM = 1)
               AND ROWNUM = 1;

            IF wk_retrieval_station IN ('11', '21', '24')
            THEN
               generate_group_no (wk_group_no,
                                  io_return_code,
                                  io_return_message
                                 );

               IF io_return_code != 0
               THEN
                  GOTO endlabel;
               END IF;
            ELSE
               wk_group_no := '000000';
            END IF;
         END IF;

         UPDATE fnhanso
            SET hjyotaiflg = ykk_global_defination.transportation_status_start,
                groupno = wk_group_no
          WHERE systemid = fngset_list_iterator.systemid
            AND endstno = fngset_list_iterator.endstno
            AND nyusyukbn = ykk_global_defination.transportation_type_stockout
            AND hjyotaiflg =
                           ykk_global_defination.transportation_status_standby;

         wk_group_size := wk_group_size + 1;

         SELECT group_max_num
           INTO max_group_size
           FROM fnretrieval_st
          WHERE retrieval_station = wk_retrieval_station;

         IF wk_group_size = max_group_size
         THEN
            wk_group_size := 0;
            generate_group_no (wk_group_no, io_return_code,
                               io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;
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
END stockout_start;
/


