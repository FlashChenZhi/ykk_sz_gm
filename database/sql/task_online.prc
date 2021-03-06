DROP PROCEDURE TASK_ONLINE;

CREATE OR REPLACE PROCEDURE     task_online (
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   rtas30 ('/con/seq',
           'FNMSGFKDO',
           '00011',
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000175;
      io_return_message := '作业开始时发生错误' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000175;
      io_return_message := '作业开始时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END task_online; 

/
