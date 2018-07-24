DROP PROCEDURE AWC.SET_TRANS_DATA_PRIORITY;

CREATE OR REPLACE PROCEDURE AWC.set_trans_data_priority (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_count               number;
   wk_endstno             fnhanso.endstno%TYPE;
   wk_next_st             fmpattern.next_st%TYPE;
   wk_printer_no          fnlabel.printer_no%TYPE;

   CURSOR fngset_list (
      c_schedule_no   IN            fngset.schno%TYPE
   )
   IS
      SELECT   fnhanso.mckey,
               fnhanso.systemid,
               fnhanso.startstno AS ailestno,
               fngset.retrieval_station
        FROM   fngset, fnhanso
       WHERE   fngset.schno = c_schedule_no
               AND fngset.syoriflg =
                     ykk_global_defination.display_info_not_processed
               AND fngset.mckey = fnhanso.mckey
               AND fnhanso.HJYOTAIFLG =
                     ykk_global_defination.transportation_status_start;

   fngset_list_iterator   fngset_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

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

      SELECT   COUNT ( * )
        INTO   wk_count
        FROM   fnhanso
       WHERE   sijisyosai = ykk_global_defination.stockout_mode_unit
               AND mckey = fngset_list_iterator.mckey;

      IF wk_count > 0
      THEN
         get_end_station_no (fngset_list_iterator.retrieval_station,
                             ykk_global_defination.stockout_mode_unit,
                             wk_endstno,
                             io_return_code,
                             io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;

         get_next_station_no (fngset_list_iterator.ailestno,
                              wk_endstno,
                              fngset_list_iterator.ailestno,
                              wk_next_st,
                              io_return_code,
                              io_return_message);

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;

         UPDATE   fnhanso
            SET   endstno = wk_endstno,
                  sakistno = wk_next_st,
                  yusenkbn = ykk_global_defination.trans_priority_urgency,
                  groupno = ykk_global_defination.default_group_no
          WHERE   mckey = fngset_list_iterator.mckey;

         UPDATE   fnsiji
            SET   retrieval_station = fngset_list_iterator.retrieval_station
          WHERE   mckey = fngset_list_iterator.mckey;

         SELECT   printer_no_unit
           INTO   wk_printer_no
           FROM   fnretrieval_st
          WHERE   retrieval_station = fngset_list_iterator.retrieval_station;

         UPDATE   fnlabel
            SET   retrieval_station = fngset_list_iterator.retrieval_station,
                  printer_no = wk_printer_no
          WHERE   label_key IN (SELECT   DISTINCT (label_key)
                                  FROM   fnsiji
                                 WHERE   mckey = fngset_list_iterator.mckey);
      ELSE
         UPDATE   fnhanso
            SET   yusenkbn = ykk_global_defination.trans_priority_urgency
          WHERE   mckey = fngset_list_iterator.mckey;
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

      io_return_code := 8000200;
      io_return_message := '设置搬送数据优先级时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END set_trans_data_priority;
/
