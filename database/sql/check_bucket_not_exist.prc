DROP PROCEDURE CHECK_BUCKET_NOT_EXIST;

CREATE OR REPLACE PROCEDURE     check_bucket_not_exist (
   in_bucket_no        IN       fmbucket.bucket_no%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_count   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT COUNT (*)
     INTO wk_count
     FROM fmbucket
    WHERE bucket_no = in_bucket_no;

   IF wk_count != 0
   THEN
      io_return_code := 8000124;
      io_return_message := 'BucketNo重复';
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000125;
      io_return_message := '检查BucketNo是否重复时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END check_bucket_not_exist; 

/
