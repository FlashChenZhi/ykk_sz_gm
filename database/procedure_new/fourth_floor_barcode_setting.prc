DROP PROCEDURE AWC.FOURTH_FLOOR_BARCODE_SETTING;

CREATE OR REPLACE PROCEDURE AWC.fourth_floor_barcode_setting (
   in_schedule_no      IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row   fngset%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT *
     INTO wk_fngset_row
     FROM fngset
    WHERE schno = in_schedule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   UPDATE fntoucyaku
      SET bucket_no = wk_fngset_row.barcode,
          SYORIFLG  = '0' -- 2008.12.18 ADD By Y.Yamasaki
    WHERE stno = wk_fngset_row.motostno;

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000165;
      io_return_message := '4FBarcode设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END fourth_floor_barcode_setting;
/
