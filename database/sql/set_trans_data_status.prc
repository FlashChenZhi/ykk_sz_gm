DROP PROCEDURE SET_TRANS_DATA_STATUS;

CREATE OR REPLACE PROCEDURE     set_trans_data_status (
   in_schedule_no      IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_count               number;
   wk_fnhanso_row         fnhanso%ROWTYPE;
   wk_new_mckey           fnhanso.mckey%TYPE;
   wk_new_hansokey        fnhanso.hansokey%TYPE;
   wk_end_st              fnhanso.endstno%TYPE;
   wk_next_st             fnhanso.sakistno%TYPE;
   wk_printer_no          fnretrieval_st.printer_no_picking%TYPE;

   CURSOR fngset_list (
      c_schedule_no   IN            fngset.schno%TYPE
   )
   IS
      SELECT   DISTINCT fnhanso.mckey
        FROM   fngset, fnhanso
       WHERE   fngset.schno = c_schedule_no
               AND fngset.syoriflg =
                     ykk_global_defination.display_info_not_processed
               AND fngset.mckey = fnhanso.mckey
               AND fnhanso.hjyotaiflg =
                     ykk_global_defination.transportation_status_start;

   CURSOR fnsiji_list (c_mckey IN fnsiji.mckey%TYPE)
   IS
      SELECT   ROWID AS r, retrieval_station
        FROM   fnsiji
       WHERE   mckey = c_mckey;

   fngset_list_iterator   fngset_list%ROWTYPE;
   fnsiji_list_iterator   fnsiji_list%ROWTYPE;

   PROCEDURE create_trans_data (in_fnhanso_row      IN     fnhanso%ROWTYPE,
                                io_return_code      IN OUT NUMBER,
                                io_return_message   IN OUT VARCHAR2)
   IS
   BEGIN
      io_return_code := 0;
      io_return_message := '';

      INSERT INTO fnhanso
        VALUES   in_fnhanso_row;

     <<endlabel>>
      NULL;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000141;
         io_return_message := '生成搬送数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END create_trans_data;
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
        FROM   fnsiji
       WHERE   mckey = fngset_list_iterator.mckey;

      IF wk_count > 1
      THEN
         SELECT   *
           INTO   wk_fnhanso_row
           FROM   fnhanso
          WHERE   mckey = fngset_list_iterator.mckey;

         wk_fnhanso_row.sijisyosai :=
            ykk_global_defination.stockout_mode_picking;

         OPEN fnsiji_list (fngset_list_iterator.mckey);

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

            generate_mckey (ykk_global_defination.stock_type_stockout,
                            wk_new_mckey,
                            io_return_code,
                            io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;

            generate_hansokey_for_stockout (
               ykk_global_defination.stock_type_stockout,
               wk_new_hansokey,
               io_return_code,
               io_return_message
            );

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;

            get_end_station_no (fnsiji_list_iterator.retrieval_station,
                                wk_fnhanso_row.sijisyosai,
                                wk_end_st,
                                io_return_code,
                                io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;

            get_next_station_no (wk_fnhanso_row.startstno,
                                 wk_end_st,
                                 wk_fnhanso_row.startstno,
                                 wk_next_st,
                                 io_return_code,
                                 io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;

            wk_fnhanso_row.mckey := wk_new_mckey;
            wk_fnhanso_row.hansokey := wk_new_hansokey;
            wk_fnhanso_row.endstno := wk_end_st;
            wk_fnhanso_row.sakistno := wk_next_st;
            wk_fnhanso_row.groupno := ykk_global_defination.default_group_no;
            wk_fnhanso_row.hjyotaiflg :=
               ykk_global_defination.transportation_status_standby;

            create_trans_data (wk_fnhanso_row,
                               io_return_code,
                               io_return_message);

            IF io_return_code != 0
            THEN
               GOTO endlabel;
            END IF;

            UPDATE   fnsiji
               SET   mckey = wk_new_mckey, hansokey = wk_new_hansokey
             WHERE   ROWID = fnsiji_list_iterator.r;

            SELECT   printer_no_picking
              INTO   wk_printer_no
              FROM   fnretrieval_st
             WHERE   retrieval_station =
                        fnsiji_list_iterator.retrieval_station;

            UPDATE   fnlabel
               SET   printer_no = wk_printer_no
             WHERE   label_key = (SELECT   label_key
                                    FROM   fnsiji
                                   WHERE   ROWID = fnsiji_list_iterator.r);
         END LOOP;

         IF fnsiji_list%ISOPEN = TRUE
         THEN
            CLOSE fnsiji_list;
         END IF;

         DELETE FROM   fnhanso
               WHERE   mckey = fngset_list_iterator.mckey;
      ELSE
         UPDATE   fnhanso
            SET   groupno = ykk_global_defination.default_group_no,
                  hjyotaiflg =
                     ykk_global_defination.transportation_status_standby
          WHERE   mckey = fngset_list_iterator.mckey;
      END IF;
   END LOOP;

  <<endlabel>>
   IF fngset_list%ISOPEN = TRUE
   THEN
      CLOSE fngset_list;
   END IF;

   IF fnsiji_list%ISOPEN = TRUE
   THEN
      CLOSE fnsiji_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF fngset_list%ISOPEN = TRUE
      THEN
         CLOSE fngset_list;
      END IF;

      IF fnsiji_list%ISOPEN = TRUE
      THEN
         CLOSE fnsiji_list;
      END IF;

      io_return_code := 8000201;
      io_return_message := '设置搬送数据状态时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END set_trans_data_status; 

/
