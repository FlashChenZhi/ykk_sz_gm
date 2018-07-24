DROP PROCEDURE AWC.STATION_SWITCH_START;

CREATE OR REPLACE PROCEDURE AWC.station_switch_start (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   CURSOR fngset_list (in_schedule_no IN fngset.schno%TYPE)
   IS
      SELECT *
        FROM fngset
       WHERE schno = in_schedule_no
         AND syoriflg = ykk_global_defination.display_info_not_processed;

   fngset_list_iterator   fngset_list%ROWTYPE;

   PROCEDURE update_retrieval_st_data (
      in_from_retrieval_station   IN       fnretrieval_st.retrieval_station%TYPE,
      in_to_retrieval_station     IN       fnretrieval_st.retrieval_station%TYPE,
      io_return_code              IN OUT   NUMBER,
      io_return_message           IN OUT   VARCHAR2
   )
   IS
      wk_fnretrieval_st_row   fnretrieval_st%ROWTYPE;
   BEGIN
      SELECT *
        INTO wk_fnretrieval_st_row
        FROM fnretrieval_st
       WHERE retrieval_station = in_to_retrieval_station;

--      UPDATE fnretrieval_st
--         SET retrieval_st_name = wk_fnretrieval_st_row.default_retrieval_st_name,
--             pick_stno = wk_fnretrieval_st_row.default_pickstno,
--             unit_stno = wk_fnretrieval_st_row.default_unit_stno,
--             remove_stno = wk_fnretrieval_st_row.default_remove_stno,
--             forced_remove_stno =
--                              wk_fnretrieval_st_row.default_forced_remove_stno,
--             rev_pickstno = wk_fnretrieval_st_row.default_rev_pickstno
--       WHERE retrieval_station = in_from_retrieval_station;
      UPDATE fnretrieval_st
         SET retrieval_st_name =
                               wk_fnretrieval_st_row.default_retrieval_st_name,
             retrieval_station_change = in_to_retrieval_station
       WHERE retrieval_station = in_from_retrieval_station;
   EXCEPTION
      WHEN OTHERS
      THEN
         io_return_code := 8000166;
         io_return_message :=
                             '更新出库口管理数据时发生错误' || ' ' || SQLERRM;
         DBMS_OUTPUT.put_line (SQLERRM);
   END update_retrieval_st_data;
BEGIN
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

      update_retrieval_st_data (fngset_list_iterator.from_retrieval_station,
                                fngset_list_iterator.to_retrieval_station,
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

      io_return_code := 8000167;
      io_return_message := '站台切换设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END station_switch_start;
/
