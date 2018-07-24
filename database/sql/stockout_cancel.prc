DROP PROCEDURE STOCKOUT_CANCEL;

CREATE OR REPLACE PROCEDURE     stockout_cancel (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_fnhanso_row         fnhanso%ROWTYPE;

   CURSOR fngset_list (
      c_schedule_no   IN            fngset.schno%TYPE
   )
   IS
      SELECT   DISTINCT (fnhanso.mckey)
        FROM   fngset, fnhanso
       WHERE   fngset.schno = c_schedule_no
               AND fngset.syoriflg =
                     ykk_global_defination.display_info_not_processed
               AND fngset.systemid = fnhanso.systemid
               AND fnhanso.hjyotaiflg =
                     ykk_global_defination.transportation_status_standby
               AND fnhanso.mckey IN
                        (SELECT   mckey
                           FROM   fngset
                          WHERE   fngset.schno = c_schedule_no
                                  AND fngset.syoriflg =
                                        ykk_global_defination.display_info_not_processed);

   fngset_list_iterator   fngset_list%ROWTYPE;

   PROCEDURE update_stock_data_on_cancel (
      in_mckey            IN     fnsiji.mckey%TYPE,
      in_systemid         IN     fnzaiko.systemid%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      wk_sum   NUMBER;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   SUM (nyusyusu)
        INTO   wk_sum
        FROM   fnsiji
       WHERE   mckey = in_mckey;

      UPDATE   fnzaiko
         SET   skanosu = skanosu + wk_sum
       WHERE   systemid = in_systemid;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000111;
         io_return_message := '更新库存数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_stock_data_on_cancel;

   PROCEDURE update_locat_data_on_cancel (
      in_mckey            IN     fnsiji.mckey%TYPE,
      in_systemid         IN     fnzaiko.systemid%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
      wk_count   NUMBER;
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      SELECT   zaikosu - skanosu
        INTO   wk_count
        FROM   fnzaiko
       WHERE   systemid = in_systemid;

      IF wk_count = 0
      THEN
         UPDATE   fnlocat
            SET   hikiflg = ykk_global_defination.location_not_dispatched
          WHERE   systemid = in_systemid;
      END IF;

      SELECT   COUNT ( * )
        INTO   wk_count
        FROM   fnhanso
       WHERE   systemid = in_systemid AND mckey != in_mckey;

      IF wk_count = 0
      THEN
         UPDATE   fnlocat
            SET   zaijyoflg = ykk_global_defination.locat_status_instock
          WHERE   systemid = in_systemid;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000112;
         io_return_message := '更新货位数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_locat_data_on_cancel;

   PROCEDURE update_rsv_data_on_cancel (in_mckey            IN     fnsiji.mckey%TYPE,
                                        io_return_code      IN OUT NUMBER,
                                        io_return_message   IN OUT VARCHAR2)
   IS
      CURSOR fnsiji_list (c_mckey IN fnsiji.mckey%TYPE)
      IS
           SELECT   retrieval_plan_key, SUM (nyusyusu) AS stock_out_qty
             FROM   fnsiji
            WHERE   mckey = c_mckey
         GROUP BY   retrieval_plan_key;

      fnsiji_list_iterator   fnsiji_list%ROWTYPE;
   BEGIN
      io_return_code := 0;
      io_return_message := '';


      OPEN fnsiji_list (in_mckey );

      LOOP
         FETCH fnsiji_list INTO   fnsiji_list_iterator;

         IF fnsiji_list%NOTFOUND = TRUE
         THEN
            IF fnsiji_list%ROWCOUNT = 0
            THEN
               io_return_code := 8000102;
               io_return_message := '数据不存在';
            END IF;

            EXIT;
         END IF;

         IF ykk_global_defination.write_fnsyotei_log_flag != '0'
         THEN
            write_fnsyotei_log (fnsiji_list_iterator.retrieval_plan_key,
                                'before stockout_cancel',
                                'worgsoft',
                                io_return_code,
                                io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;
         END IF;

         UPDATE   fnsyotei
            SET   retrieval_alloc_qty =
                     retrieval_alloc_qty - fnsiji_list_iterator.stock_out_qty,
                  proc_flag =
                     ykk_global_defination.reservation_info_not_processed
          WHERE   retrieval_plankey = fnsiji_list_iterator.retrieval_plan_key;

         IF ykk_global_defination.write_fnsyotei_log_flag != '0'
         THEN
            write_fnsyotei_log (fnsiji_list_iterator.retrieval_plan_key,
                                'after stockout_cancel',
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
      IF fnsiji_list%ISOPEN = TRUE
      THEN
         CLOSE fnsiji_list;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         IF fnsiji_list%ISOPEN = TRUE
         THEN
            CLOSE fnsiji_list;
         END IF;

         io_return_code := 8000110;
         io_return_message := '更新出库预约数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_rsv_data_on_cancel;

   PROCEDURE delete_label_data_on_cancel (
      in_mckey            IN     fnsiji.mckey%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      DELETE   fnlabel
       WHERE   label_key IN (SELECT   label_key
                               FROM   fnsiji
                              WHERE   mckey = in_mckey);
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000147;
         io_return_message := '删除打印标签数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END delete_label_data_on_cancel;

   PROCEDURE delete_indc_data_on_cancel (in_mckey            IN     fnsiji.mckey%TYPE,
                                         io_return_code      IN OUT NUMBER,
                                         io_return_message   IN OUT VARCHAR2)
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      DELETE   fnsiji
       WHERE   mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000146;
         io_return_message := '删除指示数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END delete_indc_data_on_cancel;

   PROCEDURE delete_trans_data_on_cancel (
      in_mckey            IN     fnsiji.mckey%TYPE,
      io_return_code      IN OUT NUMBER,
      io_return_message   IN OUT VARCHAR2
   )
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      DELETE   fnhanso
       WHERE   mckey = in_mckey;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000187;
         io_return_message := '删除搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END delete_trans_data_on_cancel;
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

      SELECT   *
        INTO   wk_fnhanso_row
        FROM   fnhanso
       WHERE   mckey = fngset_list_iterator.mckey;

      update_stock_data_on_cancel (wk_fnhanso_row.mckey,
                                   wk_fnhanso_row.systemid,
                                   io_return_code,
                                   io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      update_locat_data_on_cancel (wk_fnhanso_row.mckey,
                                   wk_fnhanso_row.systemid,
                                   io_return_code,
                                   io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      update_rsv_data_on_cancel (wk_fnhanso_row.mckey,
                                 io_return_code,
                                 io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      delete_label_data_on_cancel (wk_fnhanso_row.mckey,
                                   io_return_code,
                                   io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      delete_trans_data_on_cancel (wk_fnhanso_row.mckey,
                                   io_return_code,
                                   io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;

      delete_indc_data_on_cancel (wk_fnhanso_row.mckey,
                                  io_return_code,
                                  io_return_message);

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

      io_return_code := 8000186;
      io_return_message := '出库取消时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END stockout_cancel; 

/
