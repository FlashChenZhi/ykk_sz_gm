DROP PROCEDURE YKK.STOCKOUT_RM_CANCEL;

CREATE OR REPLACE PROCEDURE YKK.stockout_rm_cancel (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row           fngset%ROWTYPE;

   CURSOR fnhanso_list (in_aile_station_no IN fnlocat.ailestno%TYPE)
   IS
      SELECT DISTINCT (mckey)
                 FROM fnhanso
                WHERE nyusyukbn =
                           ykk_global_defination.transportation_type_stockout
                  AND hjyotaiflg IN
                         (ykk_global_defination.transportation_status_standby,
                          ykk_global_defination.transportation_status_start
                         )
                  AND systemid IN (SELECT systemid
                                     FROM fnlocat
                                    WHERE ailestno = in_aile_station_no);

   fnhanso_list_iterator   fnhanso_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT *
     INTO wk_fngset_row
     FROM fngset
    WHERE schno = in_schedule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   OPEN fnhanso_list (wk_fngset_row.motostno);

   LOOP
      FETCH fnhanso_list
       INTO fnhanso_list_iterator;

      IF fnhanso_list%NOTFOUND = TRUE
      THEN
         IF fnhanso_list%ROWCOUNT = 0
         THEN
            --io_return_code := 8000102;
            --io_return_message := '数据不存在';
            goto endlabel;
         END IF;

         EXIT;
      END IF;

      rtcs30 (fnhanso_list_iterator.mckey, io_return_code, io_return_message);

      IF io_return_code != 0
      THEN
         io_return_code := 8000186;
         io_return_message :=
                             '出库取消时发生错误' || ' ' || io_return_message;
         GOTO endlabel;
      END IF;
   END LOOP;

   <<endlabel>>
   IF fnhanso_list%ISOPEN = TRUE
   THEN
      CLOSE fnhanso_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF fnhanso_list%ISOPEN = TRUE
      THEN
         CLOSE fnhanso_list;
      END IF;

      io_return_code := 8000186;
      io_return_message := '出库取消时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockout_rm_cancel;
/


