DROP PROCEDURE UPDATE_RSV_DATA_FOR_PICKING;

CREATE OR REPLACE PROCEDURE     update_rsv_data_for_picking (
   in_mckey            IN       fnsiji.mckey%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   CURSOR plankey_list (in_mckey IN fnsiji.mckey%TYPE)
   IS
      SELECT DISTINCT (retrieval_plan_key)
                 FROM fnsiji
                WHERE mckey = in_mckey;

   plankey_list_iterator   plankey_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   IF ykk_global_defination.write_fnsyotei_log_flag != '0'
   THEN
      OPEN plankey_list (in_mckey);

      LOOP
         FETCH plankey_list
          INTO plankey_list_iterator;

         IF plankey_list%NOTFOUND = TRUE
         THEN
            EXIT;
         END IF;

         write_fnsyotei_log (plankey_list_iterator.retrieval_plan_key,
                             'before picking_cancel',
                             'worgsoft',
                             io_return_code,
                             io_return_message
                            );

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END LOOP;

      IF plankey_list%ISOPEN = TRUE
      THEN
         CLOSE plankey_list;
      END IF;
   END IF;

   UPDATE fnsyotei
      SET retrieval_alloc_qty =
               retrieval_alloc_qty
             - (SELECT SUM (fnsiji.nyusyusu)
                  FROM fnsiji
                 WHERE fnsiji.retrieval_plan_key = fnsyotei.retrieval_plankey
                   AND mckey = in_mckey),
          proc_flag = ykk_global_defination.reservation_info_not_processed
    WHERE retrieval_plankey IN (SELECT DISTINCT (retrieval_plan_key)
                                           FROM fnsiji
                                          WHERE mckey = in_mckey);

   IF ykk_global_defination.write_fnsyotei_log_flag != '0'
   THEN
      OPEN plankey_list (in_mckey);

      LOOP
         FETCH plankey_list
          INTO plankey_list_iterator;

         IF plankey_list%NOTFOUND = TRUE
         THEN
            EXIT;
         END IF;

         write_fnsyotei_log (plankey_list_iterator.retrieval_plan_key,
                             'after picking_cancel',
                             'worgsoft',
                             io_return_code,
                             io_return_message
                            );

         IF io_return_code != 0
         THEN
            GOTO endlabel;
         END IF;
      END LOOP;

      IF plankey_list%ISOPEN = TRUE
      THEN
         CLOSE plankey_list;
      END IF;
   END IF;

   <<endlabel>>
   IF plankey_list%ISOPEN = TRUE
   THEN
      CLOSE plankey_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF plankey_list%ISOPEN = TRUE
      THEN
         CLOSE plankey_list;
      END IF;

      io_return_code := 8000110;
      io_return_message := '更新出库预约数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_rsv_data_for_picking; 

/
