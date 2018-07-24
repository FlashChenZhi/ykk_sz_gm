DROP PROCEDURE EXTERNAL_STOCKOUT_START;

CREATE OR REPLACE PROCEDURE     external_stockout_start (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_count                NUMBER;
   wk_mckey                fnhanso.mckey%TYPE;
   wk_endstno              fnhanso.endstno%TYPE;
   wk_group_size           NUMBER;
   wk_group_no             fnhanso.groupno%TYPE;
   max_group_size          fnretrieval_st.group_max_num%TYPE;
   wk_retrieval_station    fnsiji.retrieval_station%TYPE;
   wk_stockout_mode        fnhanso.sijisyosai%TYPE;
   wk_fngset_row           fngset%ROWTYPE;
   wk_end_st               fnhanso.endstno%TYPE;
   wk_next_st              fnhanso.sakistno%TYPE;
   wk_printer_no           fnlabel.printer_no%TYPE;

   CURSOR fngset_list (
      c_schedule_no   IN            fngset.schno%TYPE
   )
   IS
        SELECT   DISTINCT fngset.systemid, fnhanso.endstno
          FROM   fngset,
                 fnhanso,
                 fnsiji,
                 fnsyotei
         WHERE   fngset.schno = c_schedule_no
                 AND fngset.syoriflg =
                       ykk_global_defination.display_info_not_processed
                 AND fngset.systemid = fnhanso.systemid
                 AND fngset.mckey = fnhanso.mckey
                 AND fnhanso.nyusyukbn =
                       ykk_global_defination.transportation_type_stockout
                 AND fnhanso.hjyotaiflg =
                       ykk_global_defination.transportation_status_standby
                 AND fnhanso.mckey = fnsiji.mckey
                 AND fnsiji.retrieval_plan_key = fnsyotei.retrieval_plankey
                 AND fnsyotei.retrieval_station IN
                          (ykk_global_defination.retrieval_station_fuyong,
                           ykk_global_defination.retrieval_station_external,
                           ykk_global_defination.retrieval_station_customer)
      ORDER BY   fnhanso.endstno;

   CURSOR fnhanso_list (
      c_schedule_no   IN            fngset.schno%TYPE
   )
   IS
      SELECT   fnhanso.mckey,
               fnhanso.sijisyosai AS stockout_mode,
               fnhanso.startstno AS ailestno
        FROM   fngset,
               fnhanso,
               fnsiji,
               fnsyotei
       WHERE   fngset.schno = c_schedule_no
               AND fngset.syoriflg =
                     ykk_global_defination.display_info_not_processed
               AND fngset.systemid = fnhanso.systemid
               AND fngset.mckey = fnhanso.mckey
               AND fnhanso.nyusyukbn =
                     ykk_global_defination.transportation_type_stockout
               AND fnhanso.hjyotaiflg =
                     ykk_global_defination.transportation_status_standby
               AND fnhanso.mckey = fnsiji.mckey
               AND fnsiji.retrieval_plan_key = fnsyotei.retrieval_plankey
               AND fnsyotei.retrieval_station IN
                        (ykk_global_defination.retrieval_station_fuyong,
                         ykk_global_defination.retrieval_station_external,
                         ykk_global_defination.retrieval_station_customer);

   fngset_list_iterator    fngset_list%ROWTYPE;
   fnhanso_list_iterator   fnhanso_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_endstno := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT   *
     INTO   wk_fngset_row
     FROM   fngset
    WHERE   fngset.schno = in_schedule_no
            AND fngset.syoriflg =
                  ykk_global_defination.display_info_not_processed
            AND ROWNUM = 1;

   IF TRIM (wk_fngset_row.retrieval_station) IS NOT NULL
   THEN
      OPEN fnhanso_list (in_schedule_no);

      LOOP
         FETCH fnhanso_list INTO   fnhanso_list_iterator;

         IF fnhanso_list%NOTFOUND = TRUE
         THEN
            IF fnhanso_list%ROWCOUNT = 0
            THEN
               io_return_code := 8000102;
               io_return_message := '数据不存在';
            END IF;

            EXIT;
         END IF;

         get_end_station_no (wk_fngset_row.retrieval_station,
                             fnhanso_list_iterator.stockout_mode,
                             wk_end_st,
                             io_return_code,
                             io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;

         get_next_station_no (fnhanso_list_iterator.ailestno,
                              wk_end_st,
                              fnhanso_list_iterator.ailestno,
                              wk_next_st,
                              io_return_code,
                              io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;

         UPDATE   fnhanso
            SET   endstno = wk_end_st, sakistno = wk_next_st
          WHERE   mckey = fnhanso_list_iterator.mckey;

         UPDATE   fnsiji
            SET   retrieval_station = wk_fngset_row.retrieval_station
          WHERE   mckey = fnhanso_list_iterator.mckey;

         IF fnhanso_list_iterator.stockout_mode =
               ykk_global_defination.stockout_mode_unit
         THEN
            SELECT   printer_no_unit
              INTO   wk_printer_no
              FROM   fnretrieval_st
             WHERE   retrieval_station = wk_fngset_row.retrieval_station;
         ELSE
            SELECT   printer_no_picking
              INTO   wk_printer_no
              FROM   fnretrieval_st
             WHERE   retrieval_station = wk_fngset_row.retrieval_station;
         END IF;

         UPDATE   fnlabel
            SET   retrieval_station = wk_fngset_row.retrieval_station,
                  printer_no = wk_printer_no
          WHERE   label_key IN (SELECT   DISTINCT (label_key)
                                  FROM   fnsiji
                                 WHERE   mckey = fnhanso_list_iterator.mckey);
      END LOOP;

      IF fnhanso_list%ISOPEN = TRUE
      THEN
         CLOSE fnhanso_list;
      END IF;
   END IF;

   OPEN fngset_list(in_schedule_no);

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

      external_append_trans_data (in_schedule_no,
                                  fngset_list_iterator.systemid,
                                  fngset_list_iterator.endstno,
                                  io_return_code,
                                  io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END LOOP;

   IF fngset_list%ISOPEN = TRUE
   THEN
      CLOSE fngset_list;
   END IF;

   OPEN fngset_list(in_schedule_no);

   LOOP
      FETCH fngset_list INTO   fngset_list_iterator;

      IF fngset_list%NOTFOUND = TRUE
      THEN
         IF fngset_list%ROWCOUNT = 0
         THEN
            GOTO endlabel;
         END IF;

         EXIT;
      END IF;

      external_merge_trans_data (in_schedule_no,
                                 fngset_list_iterator.systemid,
                                 fngset_list_iterator.endstno,
                                 wk_fngset_row.force_picking_flag,
                                 io_return_code,
                                 io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END LOOP;

   IF fngset_list%ISOPEN = TRUE
   THEN
      CLOSE fngset_list;
   END IF;

   OPEN fngset_list (in_schedule_no);

   LOOP
      FETCH fngset_list INTO   fngset_list_iterator;

      IF fngset_list%NOTFOUND = TRUE
      THEN
         IF fngset_list%ROWCOUNT = 0
         THEN
            GOTO endlabel;
         END IF;

         EXIT;
      END IF;

      IF wk_endstno != fngset_list_iterator.endstno
      THEN
         wk_endstno := fngset_list_iterator.endstno;
         wk_group_size := 0;

         SELECT   retrieval_station
           INTO   wk_retrieval_station
           FROM   fnsiji
          WHERE   mckey =
                     (SELECT   mckey
                        FROM   fnhanso
                       WHERE   systemid = fngset_list_iterator.systemid
                               AND nyusyukbn =
                                     ykk_global_defination.transportation_type_stockout
                               AND hjyotaiflg =
                                     ykk_global_defination.transportation_status_standby
                               AND ROWNUM = 1)
                  AND ROWNUM = 1;

         IF wk_retrieval_station IN
                  (ykk_global_defination.retrieval_station_plating,
                   ykk_global_defination.retrieval_station_si,
                   ykk_global_defination.retrieval_station_assembling)
         THEN
            generate_group_no (wk_group_no,
                               io_return_code,
                               io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;
         ELSE
            wk_group_no := ykk_global_defination.default_group_no;
         END IF;
      END IF;

      UPDATE   fnhanso
         SET   hjyotaiflg = ykk_global_defination.transportation_status_start,
               groupno = wk_group_no
       WHERE   systemid = fngset_list_iterator.systemid
               AND endstno = fngset_list_iterator.endstno
               AND nyusyukbn =
                     ykk_global_defination.transportation_type_stockout
               AND hjyotaiflg =
                     ykk_global_defination.transportation_status_standby;

      wk_group_size := wk_group_size + 1;

      SELECT   group_max_num
        INTO   max_group_size
        FROM   fnretrieval_st
       WHERE   retrieval_station = wk_retrieval_station;

      IF wk_group_size = max_group_size
      THEN
         wk_group_size := 0;
         generate_group_no (wk_group_no, io_return_code, io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END IF;
   END LOOP;

  <<endlabel>>
   IF fnhanso_list%ISOPEN = TRUE
   THEN
      CLOSE fnhanso_list;
   END IF;

   IF fngset_list%ISOPEN = TRUE
   THEN
      CLOSE fngset_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF fnhanso_list%ISOPEN = TRUE
      THEN
         CLOSE fnhanso_list;
      END IF;

      IF fngset_list%ISOPEN = TRUE
      THEN
         CLOSE fngset_list;
      END IF;

      io_return_code := 8000122;
      io_return_message := '出库开始设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END external_stockout_start; 

/
