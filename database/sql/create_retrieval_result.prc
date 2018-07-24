DROP PROCEDURE CREATE_RETRIEVAL_RESULT;

CREATE OR REPLACE PROCEDURE     create_retrieval_result (
   in_mckey            IN       fnsiji.mckey%TYPE,
   in_systemid         IN       fnzaiko.systemid%TYPE,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   CURSOR indc_list (c_mckey IN fnsiji.mckey%TYPE)
   IS
      SELECT *
        FROM fnsiji
       WHERE mckey = c_mckey;

   indc_list_iterator         indc_list%ROWTYPE;
   wk_fhretrievalresult_row   fhretrievalresult%ROWTYPE;
   wk_fnsyotei_row            fnsyotei%ROWTYPE;
   wk_fnzaiko_row             fnzaiko%ROWTYPE;
   wk_datetime14              VARCHAR2 (14);
   wk_count                   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';

   SELECT COUNT (*)
     INTO wk_count
     FROM fnhanso
    WHERE mckey = in_mckey AND sagyokbn = '7';

   IF wk_count > 0
   THEN
      GOTO endlabel;
   END IF;

   OPEN indc_list (in_mckey);

   LOOP
      FETCH indc_list
       INTO indc_list_iterator;

      IF indc_list%NOTFOUND = TRUE
      THEN
         IF indc_list%ROWCOUNT = 0
         THEN
            io_return_code := 8000102;
            io_return_message := '数据不存在';
         END IF;

         EXIT;
      END IF;

      IF TRIM (indc_list_iterator.retrieval_plan_key) IS NOT NULL
      THEN
         SELECT *
           INTO wk_fnsyotei_row
           FROM fnsyotei
          WHERE retrieval_plankey = indc_list_iterator.retrieval_plan_key;

         SELECT *
           INTO wk_fnzaiko_row
           FROM fnzaiko
          WHERE systemid = in_systemid;

         wk_fhretrievalresult_row.retrieval_no := wk_fnsyotei_row.retrieval_no;
         wk_fhretrievalresult_row.serial_no := wk_fnsyotei_row.serial_no;
         wk_fhretrievalresult_row.ticket_no := wk_fnzaiko_row.ticket_no;
         wk_fhretrievalresult_row.item_code := wk_fnzaiko_row.zaikey;
         wk_fhretrievalresult_row.color_code := wk_fnzaiko_row.color_code;
         wk_fhretrievalresult_row.depot_code := wk_fnsyotei_row.depot_code;
         wk_fhretrievalresult_row.section := wk_fnsyotei_row.section;
         wk_fhretrievalresult_row.line := wk_fnsyotei_row.line;
         wk_fhretrievalresult_row.result_qty := indc_list_iterator.nyusyusu;
         wk_fhretrievalresult_row.result_weight :=
                 wk_fnzaiko_row.real_unit_weight * indc_list_iterator.nyusyusu;
         wk_fhretrievalresult_row.retrieval_plankey :=
                                         indc_list_iterator.retrieval_plan_key;
         wk_fhretrievalresult_row.proc_flag :=
                          ykk_global_defination.retrieval_result_not_processed;
         wk_datetime14 := sysdate14_get ();
         wk_fhretrievalresult_row.register_date :=
                                                  SUBSTR (wk_datetime14, 0, 8);
         wk_fhretrievalresult_row.register_time :=
                                                  SUBSTR (wk_datetime14, 9, 6);

         INSERT INTO fhretrievalresult
              VALUES wk_fhretrievalresult_row;
      END IF;
   END LOOP;

   <<endlabel>>
   IF indc_list%ISOPEN = TRUE
   THEN
      CLOSE indc_list;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF indc_list%ISOPEN = TRUE
      THEN
         CLOSE indc_list;
      END IF;

      io_return_code := 8000148;
      io_return_message := '生成Host出库实绩数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END create_retrieval_result; 

/
