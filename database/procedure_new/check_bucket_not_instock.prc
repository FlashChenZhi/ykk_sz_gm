DROP PROCEDURE AWC.CHECK_BUCKET_NOT_INSTOCK;

CREATE OR REPLACE PROCEDURE AWC.check_bucket_not_instock (
   in_bucket_no            IN       fnzaiko.bucket_no%TYPE,
   in_storage_place_flag   IN       fnzaiko.storage_place_flag%TYPE,
   io_return_code          IN OUT   NUMBER,
   io_return_message       IN OUT   VARCHAR2
)
IS
   wk_count   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT COUNT (*)
     INTO wk_count
     FROM fnzaiko
    WHERE bucket_no = in_bucket_no
      AND storage_place_flag = in_storage_place_flag;

   IF wk_count != 0
   THEN
      io_return_code := 8000124;
      io_return_message := 'BucketNo�ظ�';
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000125;
      io_return_message := '���BucketNo�Ƿ��ظ�ʱ��������' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END check_bucket_not_instock;
/
