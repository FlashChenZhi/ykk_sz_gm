DROP PROCEDURE AWC.STOCK_DATA_MESSAGE_SEND;

CREATE OR REPLACE PROCEDURE AWC.stock_data_message_send (
   io_return_code      IN OUT   NUMBER,
   io_return_message   IN OUT   VARCHAR2
)
IS
   wk_return_number   NUMBER;
BEGIN
   rtas30 ('/con/seq',
           'FNMSGFKDO',
           '10011',
           io_return_code,
           wk_return_number,
           io_return_message
          );

   IF io_return_code != 0
   THEN
      io_return_code := 8000180;
      io_return_message :=
                         '库存数据送信时发生错误' || ' ' || io_return_message;
   END IF;
EXCEPTION
   WHEN NO_DATA_FOUND
   THEN
      NULL;
   WHEN OTHERS
   THEN
      -- Consider logging the error and then re-raise
      RAISE;
END stock_data_message_send;
/
