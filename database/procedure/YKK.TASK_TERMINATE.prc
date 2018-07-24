DROP PROCEDURE YKK.TASK_TERMINATE;

CREATE OR REPLACE PROCEDURE YKK.task_terminate (
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

   IF wk_fnhanso_row.nyusyukbn != 1
   THEN
      io_return_code := 8000184;
      io_return_message := '作业不符合强制完了要求';
      GOTO endlabel;
   END IF;

   rtas30 ('/con/seq',
           'FNMSGFKDO',
           '00041' || wk_fngset_row.mckey,
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000185;
      io_return_message :=
                         '作业维护强制完了时发生错误' || ' ' || io_return_message;
   END IF;

   <<endlabel>>
   NULL;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000185;
      io_return_message := '作业维护强制完了时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END task_terminate;
/


