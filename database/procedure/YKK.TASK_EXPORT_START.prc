DROP PROCEDURE YKK.TASK_EXPORT_START;

CREATE OR REPLACE PROCEDURE YKK.task_export_start (
   in_schdule_no       IN       fngset.schno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_fngset_row      fngset%ROWTYPE;
   wk_fnhanso_row     fnhanso%ROWTYPE;
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT *
     INTO wk_fngset_row
     FROM fngset
    WHERE schno = in_schdule_no
      AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT *
     INTO wk_fnhanso_row
     FROM fnhanso
    WHERE mckey = wk_fngset_row.mckey;

   IF wk_fnhanso_row.hjyotaiflg IN ('0', '1')
   THEN
      io_return_code := 8000183;
      io_return_message := '作业不符合排出要求';
      GOTO endlabel;
   END IF;

   rtas30 ('/con/seq',
           'FNMSGFKDO',
              '00051'
           || TRIM (wk_fngset_row.mckey)
           || TRIM (wk_fnhanso_row.sakistno),
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000182;
      io_return_message :=
                         '作业维护排出时发生错误' || ' ' || io_return_message;
   END IF;

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000182;
      io_return_message := '作业维护排出时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END task_export_start;
/


