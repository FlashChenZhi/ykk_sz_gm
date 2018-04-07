DROP PROCEDURE EXTERNAL_STOCKOUT_MANU_SETTING;

CREATE OR REPLACE PROCEDURE     external_stockout_manu_setting (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';
   core_stockout_manu_setting (in_schedule_no,
                               ykk_global_defination.external_stockout,
                               io_return_code,
                               io_return_message
                              );

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000114;
      io_return_message := '出库手动分配设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END external_stockout_manu_setting; 

/
