DROP PROCEDURE EXTERNAL_MERGE_TRANS_DATA;

CREATE OR REPLACE PROCEDURE     external_merge_trans_data (
   in_schedule_no          IN     fngset.schno%TYPE,
   in_systemid             IN     fnhanso.systemid%TYPE,
   in_endstno              IN     fnhanso.endstno%TYPE,
   in_force_picking_flag   IN     fngset.force_picking_flag%TYPE,
   io_return_code          IN OUT NUMBER,
   io_return_message       IN OUT VARCHAR2
)
IS
   wk_mckey                  fnhanso.mckey%TYPE;
   wk_hansokey               fnhanso.hansokey%TYPE;
   wk_sum                    NUMBER;
   wk_retrieval_station      fnsiji.retrieval_station%TYPE;
   wk_endstno                fnhanso.endstno%TYPE;
   wk_next_st                fmpattern.next_st%TYPE;
   wk_printer_no             fnretrieval_st.printer_no_unit%TYPE;
   wk_fnlocat_row            fnlocat%ROWTYPE;
   wk_count                  NUMBER;
   wk_stockout_mode          fnhanso.sijisyosai%TYPE;

   CURSOR transdata_list (
      c_schedule_no   IN            fngset.schno%TYPE,
      c_systemid      IN            fnhanso.systemid%TYPE,
      c_endstno       IN            fnhanso.endstno%TYPE
   )
   IS
      SELECT   DISTINCT endstno
        FROM   fnhanso
       WHERE   systemid = c_systemid AND endstno = c_endstno
               AND nyusyukbn =
                     ykk_global_defination.transportation_type_stockout
               AND hjyotaiflg =
                     ykk_global_defination.transportation_status_standby
               AND mckey IN
                        (SELECT   DISTINCT fngset.mckey
                           FROM   fngset, fnsiji, fnsyotei
                          WHERE   fngset.schno = c_schedule_no
                                  AND fngset.syoriflg =
                                        ykk_global_defination.display_info_not_processed
                                  AND fngset.mckey = fnsiji.mckey
                                  AND fnsiji.retrieval_plan_key =
                                        fnsyotei.retrieval_plankey
                                  AND fnsyotei.retrieval_station IN
                                           (ykk_global_defination.retrieval_station_fuyong,
                                            ykk_global_defination.retrieval_station_external,
                                            ykk_global_defination.retrieval_station_customer));

   transdata_list_iterator   transdata_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   OPEN transdata_list (in_schedule_no,in_systemid, in_endstno);

   LOOP
      FETCH transdata_list INTO   transdata_list_iterator;

      IF transdata_list%NOTFOUND = TRUE
      THEN
         IF transdata_list%ROWCOUNT = 0
         THEN
            GOTO endlabel;
         END IF;

         EXIT;
      END IF;

      SELECT   mckey, hansokey, sijisyosai
        INTO   wk_mckey, wk_hansokey, wk_stockout_mode
        FROM   fnhanso
       WHERE   systemid = in_systemid
               AND endstno = transdata_list_iterator.endstno
               AND nyusyukbn =
                     ykk_global_defination.transportation_type_stockout
               AND hjyotaiflg =
                     ykk_global_defination.transportation_status_standby
               AND mckey IN
                        (SELECT   DISTINCT fngset.mckey
                           FROM   fngset, fnsiji, fnsyotei
                          WHERE   fngset.schno = in_schedule_no
                                  AND fngset.syoriflg =
                                        ykk_global_defination.display_info_not_processed
                                  AND fngset.mckey = fnsiji.mckey
                                  AND fnsiji.retrieval_plan_key =
                                        fnsyotei.retrieval_plankey
                                  AND fnsyotei.retrieval_station IN
                                           (ykk_global_defination.retrieval_station_fuyong,
                                            ykk_global_defination.retrieval_station_external,
                                            ykk_global_defination.retrieval_station_customer))
               AND ROWNUM = 1;

      SELECT   retrieval_station
        INTO   wk_retrieval_station
        FROM   fnsiji
       WHERE   mckey = wk_mckey;

      UPDATE   fnsiji
         SET   mckey = wk_mckey, hansokey = wk_hansokey
       WHERE   mckey IN
                     (SELECT   mckey
                        FROM   fnhanso
                       WHERE   systemid = in_systemid
                               AND endstno = transdata_list_iterator.endstno
                               AND hjyotaiflg =
                                     ykk_global_defination.transportation_status_standby
                               AND mckey != wk_mckey);

      DELETE   fnhanso
       WHERE   mckey IN
                     (SELECT   mckey
                        FROM   fnhanso
                       WHERE   systemid = in_systemid
                               AND endstno = transdata_list_iterator.endstno
                               AND hjyotaiflg =
                                     ykk_global_defination.transportation_status_standby
                               AND mckey != wk_mckey);

      UPDATE   fnlocat
         SET   hikiflg = ykk_global_defination.location_not_dispatched
       WHERE   systemid = in_systemid;


      IF in_force_picking_flag = '1'
      THEN
         wk_stockout_mode := ykk_global_defination.stockout_mode_picking;
      ELSE
         SELECT   COUNT ( * )
           INTO   wk_count
           FROM   fnsiji
          WHERE   mckey = wk_mckey AND subdivide_flag = '1';

         IF wk_count = 0
            AND wk_stockout_mode != ykk_global_defination.stockout_mode_unit
         THEN
            SELECT   SUM (nyusyusu)
              INTO   wk_sum
              FROM   fnsiji
             WHERE   mckey = wk_mckey;

            get_stockout_mode (in_systemid,
                               wk_sum,
                               wk_stockout_mode,
                               io_return_code,
                               io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;
         END IF;
      END IF;

      SELECT   *
        INTO   wk_fnlocat_row
        FROM   fnlocat
       WHERE   systemid = in_systemid;

      update_locat_data_for_stockout (in_systemid,
                                      wk_stockout_mode,
                                      io_return_code,
                                      io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      get_end_station_no (wk_retrieval_station,
                          wk_stockout_mode,
                          wk_endstno,
                          io_return_code,
                          io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      get_next_station_no (wk_fnlocat_row.ailestno,
                           wk_endstno,
                           wk_fnlocat_row.ailestno,
                           wk_next_st,
                           io_return_code,
                           io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      IF wk_stockout_mode = ykk_global_defination.stockout_mode_picking
      THEN
         UPDATE   fnhanso
            SET   sijisyosai = wk_stockout_mode,
                  endstno = wk_endstno,
                  sakistno = wk_next_st,
                  hjyotaiflg =
                     ykk_global_defination.transportation_status_start
          WHERE   mckey = wk_mckey;
      ELSE
         UPDATE   fnhanso
            SET   sijisyosai = wk_stockout_mode,
                  endstno = wk_endstno,
                  sakistno = wk_next_st
          WHERE   mckey = wk_mckey;

         SELECT   printer_no_unit
           INTO   wk_printer_no
           FROM   fnretrieval_st
          WHERE   retrieval_station =
                     (SELECT   retrieval_station
                        FROM   fnsiji
                       WHERE   mckey = wk_mckey AND ROWNUM = 1);

         UPDATE   fnlabel
            SET   printer_no = wk_printer_no
          WHERE   label_key IN (SELECT   label_key
                                  FROM   fnsiji
                                 WHERE   mckey = wk_mckey);
      END IF;
   END LOOP;

  <<endlabel>>
   IF transdata_list%ISOPEN = TRUE
   THEN
      CLOSE transdata_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF transdata_list%ISOPEN = TRUE
      THEN
         CLOSE transdata_list;
      END IF;

      io_return_code := 8000121;
      io_return_message := '合并指令数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END external_merge_trans_data; 

/
