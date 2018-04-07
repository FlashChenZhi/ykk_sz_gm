DROP PROCEDURE UPDATE_LABEL_DATA_FOR_PRINTING;

CREATE OR REPLACE PROCEDURE     update_label_data_for_printing (
   in_mckey            IN     fnsiji.mckey%TYPE,
   in_printer_no       IN     fnprinter.printer_no%TYPE,
   in_user_id          IN     fnlabel.userid%TYPE,
   in_user_name        IN     fnlabel.username%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
BEGIN
   UPDATE   fnlabel
      SET   printer_no = in_printer_no,
            userid = in_user_id,
            username = in_user_name,
            printing_flag = ykk_global_defination.label_prt_flag_need_print,
            update_datetime = sysdate14_get ()
    WHERE   label_key IN (SELECT   label_key
                            FROM   fnsiji
                           WHERE   mckey = in_mckey);
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000143;
      io_return_message := '���´�ӡ��ǩ����ʱ��������' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END update_label_data_for_printing; 

/
