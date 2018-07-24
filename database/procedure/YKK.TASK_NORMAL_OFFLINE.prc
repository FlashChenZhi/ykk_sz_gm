DROP PROCEDURE YKK.TASK_NORMAL_OFFLINE;

CREATE OR REPLACE PROCEDURE YKK.task_normal_offline (
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   rtas30 ('/con/seq',
           'FNMSGFKDO',
           '000311',
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000176;
      io_return_message := '作业终了时发生错误' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000176;
      io_return_message := '作业终了时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END task_normal_offline;
/


