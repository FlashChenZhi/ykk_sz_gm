DROP PROCEDURE AWC.CREATE_BUCKET_DATA_FOR_STOCKIN;

CREATE OR REPLACE PROCEDURE AWC.create_bucket_data_for_stockin (
   in_bucket_no        IN       fmbucket.bucket_no%TYPE,
   in_height_flag      IN       fmbucket.height_flag%TYPE,
   in_packing_weight   IN       fmbucket.packing_weight%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fmbucket_row   fmbucket%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   wk_fmbucket_row.bucket_no := in_bucket_no;
   wk_fmbucket_row.packing_weight := in_packing_weight;
   wk_fmbucket_row.height_flag := in_height_flag;
   wk_fmbucket_row.lastupdate_datetime := sysdate14_get ();

   INSERT INTO fmbucket
        VALUES wk_fmbucket_row;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000137;
      io_return_message := '生成Bucket数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END create_bucket_data_for_stockin;
/
