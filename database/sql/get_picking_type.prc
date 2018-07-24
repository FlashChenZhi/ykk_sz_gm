DROP PROCEDURE GET_PICKING_TYPE;

CREATE OR REPLACE PROCEDURE     get_picking_type (
   in_mckey            IN       fnhanso.mckey%TYPE,
   io_picking_type     IN OUT   VARCHAR2,
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   rtcs10 (in_mckey,
           io_picking_type,
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000140;
      io_return_message :=
                         '获取拣选类型时发生错误' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      io_return_code := 8000140;
      io_return_message := '获取拣选类型时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END get_picking_type; 

/
