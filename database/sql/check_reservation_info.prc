DROP PROCEDURE CHECK_RESERVATION_INFO;

CREATE OR REPLACE PROCEDURE     check_reservation_info (
   in_retrieval_plankey   IN       fnsyotei.retrieval_plankey%TYPE,
   io_return_code         IN OUT   NUMBER,
   io_return_message      IN OUT   VARCHAR2
)
IS
   wk_count   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT COUNT (*)
     INTO wk_count
     FROM fnsyotei
    WHERE proc_flag = ykk_global_defination.reservation_info_not_processed
      AND retrieval_plankey = in_retrieval_plankey;

   IF wk_count = 0
   THEN
      io_return_code := 8000115;
      io_return_message := '出库预约数据不存在或已被处理';
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000106;
      io_return_message := '获取出库预约数据时发生错误' || ' ' || SQLERRM;
   DBMS_OUTPUT.PUT_LINE(SQLERRM);
END check_reservation_info; 

/
