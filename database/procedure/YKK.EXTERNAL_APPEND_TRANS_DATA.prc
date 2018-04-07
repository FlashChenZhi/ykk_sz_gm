DROP PROCEDURE YKK.EXTERNAL_APPEND_TRANS_DATA;

CREATE OR REPLACE PROCEDURE YKK.external_append_trans_data (
   in_systemid         IN       fnhanso.systemid%TYPE,
   in_endstno          IN       fnhanso.endstno%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_mckey                  fnhanso.mckey%TYPE;
   wk_hansokey               fnhanso.hansokey%TYPE;

   CURSOR transdata_list (
      c_systemid   IN   fnhanso.systemid%TYPE,
      c_endstno    IN   fnhanso.endstno%TYPE
   )
   IS
      SELECT systemid, mckey, endstno
        FROM fnhanso
       WHERE systemid = c_systemid
         AND nyusyukbn = ykk_global_defination.transportation_type_stockout
         AND hjyotaiflg = ykk_global_defination.transportation_status_standby
         AND endstno =
                (SELECT DISTINCT (endstno)
                            FROM fnhanso
                           WHERE systemid = c_systemid
                             AND endstno = c_endstno
                             AND nyusyukbn =
                                    ykk_global_defination.transportation_type_stockout
                             AND hjyotaiflg IN ('1', '2', '3', '4', '5'));

   transdata_list_iterator   transdata_list%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   OPEN transdata_list (in_systemid, in_endstno);

   LOOP
      FETCH transdata_list
       INTO transdata_list_iterator;

      IF transdata_list%NOTFOUND = TRUE
      THEN
         IF transdata_list%ROWCOUNT = 0
         THEN
            goto endlabel;
         END IF;

         EXIT;
      END IF;

      SELECT mckey, hansokey
        INTO wk_mckey, wk_hansokey
        FROM fnhanso
       WHERE systemid = transdata_list_iterator.systemid
         AND endstno = transdata_list_iterator.endstno
         AND hjyotaiflg IN ('1', '2', '3', '4', '5');

      UPDATE fnsiji
         SET mckey = wk_mckey,
             hansokey = wk_hansokey
       WHERE mckey = transdata_list_iterator.mckey;

      DELETE      fnhanso
            WHERE mckey = transdata_list_iterator.mckey;
   END LOOP;

   <<endlabel>>
   IF transdata_list%ISOPEN = TRUE
   THEN
      CLOSE transdata_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF transdata_list%ISOPEN = TRUE
      THEN
         CLOSE transdata_list;
      END IF;

      io_return_code := 8000120;
      io_return_message := '追加指令数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END external_append_trans_data;
/


