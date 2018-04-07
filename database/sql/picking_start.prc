DROP PROCEDURE PICKING_START;

CREATE OR REPLACE PROCEDURE     picking_start (
   in_schdule_no       IN     fngset.schno%TYPE,
   io_return_code      IN OUT NUMBER,
   io_return_message   IN OUT VARCHAR2
)
IS
   wk_fngset_row     fngset%ROWTYPE;
   wk_mckey          fnpick_ctl.mckey%TYPE;
   wk_picking_type   CHAR (1);
BEGIN
   io_return_code := 0;
   io_return_message := '';
   online_check (io_return_code, io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   SELECT   *
     INTO   wk_fngset_row
     FROM   fngset
    WHERE   schno = in_schdule_no
            AND syoriflg = ykk_global_defination.display_info_not_processed;

   SELECT   mckey
     INTO   wk_mckey
     FROM   fnpick_ctl
    WHERE   stno = wk_fngset_row.motostno;

   get_picking_type (wk_mckey,
                     wk_picking_type,
                     io_return_code,
                     io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   IF wk_picking_type IN
            (ykk_global_defination.picking_type_normal,
             ykk_global_defination.picking_type_subdivided)
   THEN
      picking_normal_start (in_schdule_no, io_return_code, io_return_message);
   ELSIF wk_picking_type = ykk_global_defination.picking_type_converse
   THEN
      picking_converse_start (in_schdule_no,
                              io_return_code,
                              io_return_message);
   ELSIF wk_picking_type = ykk_global_defination.picking_type_total
   THEN
      picking_total_start (in_schdule_no, io_return_code, io_return_message);
   ELSIF wk_picking_type = ykk_global_defination.picking_type_abnormal
   THEN
      picking_abnormal_start (in_schdule_no,
                              io_return_code,
                              io_return_message);
   ELSIF wk_picking_type = ykk_global_defination.picking_type_cycle
   THEN
      picking_cycle_start (in_schdule_no, io_return_code, io_return_message);
   END IF;

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
      io_return_code := 8000155;
      io_return_message := '拣选设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END picking_start; 

/
