DROP PROCEDURE YKK.GET_RESERVATION_INFO;

CREATE OR REPLACE PROCEDURE YKK.get_reservation_info (
   in_retrieval_plan_key   IN       fnsyotei.retrieval_plankey%TYPE,
   io_rsvinfo_row          IN OUT   fnsyotei%ROWTYPE,
   io_return_code          IN OUT   NUMBER,
   io_return_message       IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT *
     INTO io_rsvinfo_row
     FROM fnsyotei
    WHERE retrieval_plankey = in_retrieval_plan_key;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000106;
      io_return_message := '获取出库预约数据时发生错误' || ' ' || SQLERRM;
   DBMS_OUTPUT.PUT_LINE(SQLERRM);
END get_reservation_info;
/


