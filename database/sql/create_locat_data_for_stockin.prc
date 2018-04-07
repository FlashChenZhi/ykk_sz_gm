DROP PROCEDURE CREATE_LOCAT_DATA_FOR_STOCKIN;

CREATE OR REPLACE PROCEDURE     create_locat_data_for_stockin (
   in_ticket_no        IN       fngset.ticket_no%TYPE,
   in_mckey            IN       fnhanso.mckey%TYPE,
   in_bucket_no        IN       VARCHAR2,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_systemid   fnhanso.systemid%TYPE;
   wk_hansokey   fnhanso.hansokey%TYPE;
BEGIN
   SELECT hansokey
     INTO wk_hansokey
     FROM fnhanso
    WHERE mckey = in_mckey;

   SELECT systemid
     INTO wk_systemid
     FROM fnzaiko
    WHERE ticket_no = in_ticket_no;

   INSERT INTO fnlocat
               (syozaikey, hansokey, sokokbn,
                bucket_no, zaijyoflg,
                shikiflg,
                hikiflg,
                accessflg, systemid
               )
        VALUES (' ', wk_hansokey, ykk_global_defination.warehouse_type_auto,
                in_bucket_no, ykk_global_defination.locat_status_in_reserved,
                ykk_global_defination.location_available,
                ykk_global_defination.location_not_dispatched,
                ykk_global_defination.location_accessable, wk_systemid
               );
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000128;
      io_return_message := '生成货位数据时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END create_locat_data_for_stockin; 

/
