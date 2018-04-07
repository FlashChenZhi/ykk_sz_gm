DROP PROCEDURE AFTER_EXPORT;

CREATE OR REPLACE PROCEDURE     after_export (
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

   <<endlabel>>
   IF io_return_code != 0
   THEN
      ROLLBACK;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      ROLLBACK;
END after_export; 

/
