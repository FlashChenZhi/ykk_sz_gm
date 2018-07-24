DROP PROCEDURE AFTER_STOCKIN;

CREATE OR REPLACE PROCEDURE     after_stockin (
   in_weight_report_complete_flag   IN       fnzaiko.weight_report_complete_flag%TYPE,
   io_return_code                   IN OUT   NUMBER,
   io_return_message                IN OUT   VARCHAR2
)
IS
BEGIN
   io_return_code := 0;
   io_return_message := '';
   call_fkick ('FNNSJKDO1', io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   IF in_weight_report_complete_flag =
                             ykk_global_defination.weight_report_not_completed
   THEN
      call_fkick ('FNKEIRIOKDO', io_return_code, io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   ELSIF in_weight_report_complete_flag =
                                 ykk_global_defination.weight_report_completed
   THEN
      call_fkick ('FNSAIKDO', io_return_code, io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END IF;

   <<endlabel>>
   IF io_return_code != 0
   THEN
      ROLLBACK;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      ROLLBACK;
END after_stockin; 

/
