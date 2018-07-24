DROP PROCEDURE YKK.GET_END_STATION_NO;

CREATE OR REPLACE PROCEDURE YKK.get_end_station_no (
   in_retrieval_station   IN       fnretrieval_st.retrieval_station%TYPE,
   in_stockout_mode       IN       CHAR,
   io_end_station_no      IN OUT   fnretrieval_st.unit_stno%TYPE,
   io_return_code         IN OUT   NUMBER,
   io_return_message      IN OUT   VARCHAR2
)
IS
   wk_pick_stno   fnretrieval_st.pick_stno%TYPE;
   wk_unit_stno   fnretrieval_st.unit_stno%TYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT pick_stno, unit_stno
     INTO wk_pick_stno, wk_unit_stno
     FROM fnretrieval_st
    WHERE retrieval_station = in_retrieval_station;

   IF in_stockout_mode = ykk_global_defination.stockout_mode_unit
   THEN
      io_end_station_no := wk_unit_stno;
   ELSE
      io_end_station_no := wk_pick_stno;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000104;
      io_return_message :=
                   '出库站台不存在或查找出库站台时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END get_end_station_no;
/


