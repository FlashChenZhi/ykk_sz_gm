DROP PROCEDURE YKK.TASK_ALL_START;

CREATE OR REPLACE PROCEDURE YKK.task_all_start (
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   rtas30 ('/con/seq',
           'FNMSGFKDO',
           '001611',
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000177;
      io_return_message := '��ҵȫ����ʼʱ��������' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000177;
      io_return_message := '��ҵȫ����ʼʱ��������' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END task_all_start;
/


