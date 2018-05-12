DROP PROCEDURE AWC.UPDATE_INDC_DATA_FOR_STOCKIN;

CREATE OR REPLACE PROCEDURE AWC.update_indc_data_for_stockin (
   in_mckey            IN       fnhanso.mckey%TYPE,
   in_ticket_no        IN       fnzaiko.ticket_no%TYPE,
   in_stockin_num      IN       fnsiji.nyusyusu%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fnzaiko_row   fnzaiko%ROWTYPE;
   wk_hansokey      fnsiji.hansokey%TYPE;
BEGIN
   SELECT *
     INTO wk_fnzaiko_row
     FROM fnzaiko
    WHERE ticket_no = in_ticket_no AND ROWNUM = 1;

   SELECT hansokey
     INTO wk_hansokey
     FROM fnhanso
    WHERE mckey = in_mckey;

   UPDATE fnsiji
      SET hansokey = wk_hansokey,
          ticket_no = wk_fnzaiko_row.ticket_no,
          zaikey = wk_fnzaiko_row.zaikey,
          color_code = wk_fnzaiko_row.color_code,
          nyusyusu = in_stockin_num,
          section = wk_fnzaiko_row.made_section,
          line = wk_fnzaiko_row.made_line,
          manage_item_flag = wk_fnzaiko_row.manage_item_flag
    WHERE mckey = in_mckey;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000130;
      io_return_message := '更新指示数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_indc_data_for_stockin;
/
