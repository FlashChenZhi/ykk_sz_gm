DROP PROCEDURE NORMAL_STOCKOUT_AUTO_SETTING;

CREATE OR REPLACE PROCEDURE     normal_stockout_auto_setting (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';

   core_stockout_auto_setting (in_schedule_no,
                               ykk_global_defination.normal_stockout,
                               io_return_code,
                               io_return_message
                              );

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000113;
      io_return_message := '出库自动分配设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END normal_stockout_auto_setting; 

/
