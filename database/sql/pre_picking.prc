DROP PROCEDURE PRE_PICKING;

CREATE OR REPLACE PROCEDURE     pre_picking (
   in_motostno                IN     fngset.motostno%TYPE,
   in_printer_no              IN     fngset.printer_no%TYPE,
   in_user_id                 IN     fnlabel.userid%TYPE,
   in_user_name               IN     fnlabel.username%TYPE,
   io_picking_type            IN OUT CHAR,
   io_new_mckey               IN OUT VARCHAR2,
   io_remaining_job_num       IN OUT NUMBER,
   io_remaining_picking_num   IN OUT NUMBER,
   io_return_code             IN OUT NUMBER,
   io_return_message          IN OUT VARCHAR2)
IS
   wk_old_mckey                fnpick_ctl.mckey%TYPE;
   wk_retrieval_station        fnsiji.retrieval_station%TYPE;
   wk_retrieval_station_temp   fnsiji.retrieval_station%TYPE;
   wk_section                  fnsiji.section%TYPE;
   wk_line                     fnsiji.line%TYPE;
   wk_customer_code            fnsiji.customer_code%TYPE;
   wk_order_no                 fnsyotei.order_no%TYPE;
   wk_picking_num              NUMBER;
   wk_count                    NUMBER;
   wk_fnsiji_row               fnsiji%ROWTYPE;

   CURSOR fnsiji_list_1 (
      in_mckey               IN fnsiji.mckey%TYPE,
      in_section             IN fnsiji.section%TYPE,
      in_line                IN fnsiji.line%TYPE,
      in_retrieval_station   IN fnsiji.retrieval_station%TYPE)
   IS
      SELECT *
        FROM fnsiji
       WHERE     mckey = in_mckey
             AND section = in_section
             AND line = in_line
             AND retrieval_station = in_retrieval_station;

   CURSOR fnsiji_list_2 (
      in_mckey               IN fnsiji.mckey%TYPE,
      in_section             IN fnsiji.section%TYPE,
      in_retrieval_station   IN fnsiji.retrieval_station%TYPE)
   IS
      SELECT *
        FROM fnsiji
       WHERE     mckey = in_mckey
             AND section = in_section
             AND retrieval_station = in_retrieval_station;

   CURSOR fnsiji_list_3 (
      in_mckey               IN fnsiji.mckey%TYPE,
      in_retrieval_station   IN fnsiji.retrieval_station%TYPE,
      in_section             IN fnsiji.section%TYPE)
   IS
      SELECT *
        FROM fnsiji
       WHERE     mckey = in_mckey
             AND retrieval_station = in_retrieval_station
             AND section = in_section;

   CURSOR fnsiji_list_4 (
      in_mckey               IN fnsiji.mckey%TYPE,
      in_customer_code       IN fnsiji.customer_code%TYPE,
      in_retrieval_station   IN fnsiji.retrieval_station%TYPE)
   IS
      SELECT *
        FROM fnsiji
       WHERE     mckey = in_mckey
             AND customer_code = in_customer_code
             AND retrieval_station = in_retrieval_station;

   CURSOR fnsiji_list_5 (
      in_mckey               IN fnsiji.mckey%TYPE,
      in_customer_code       IN fnsiji.customer_code%TYPE,
      in_retrieval_station   IN fnsiji.retrieval_station%TYPE,
      in_order_no            IN fnsyotei.order_no%TYPE)
   IS
      SELECT *
        FROM fnsiji
       WHERE     mckey = in_mckey
             AND customer_code = in_customer_code
             AND retrieval_station = in_retrieval_station
             AND in_order_no =
                    (SELECT order_no
                       FROM fnsyotei
                      WHERE fnsyotei.retrieval_plankey =
                               fnsiji.retrieval_plan_key);

   fnsiji_list_1_iterator      fnsiji_list_1%ROWTYPE;
   fnsiji_list_2_iterator      fnsiji_list_2%ROWTYPE;
   fnsiji_list_3_iterator      fnsiji_list_3%ROWTYPE;
   fnsiji_list_4_iterator      fnsiji_list_4%ROWTYPE;
   fnsiji_list_5_iterator      fnsiji_list_5%ROWTYPE;
BEGIN
   io_return_code := 0;
   io_return_message := '';
   io_new_mckey := 'TERM' || in_motostno;

   DELETE fnsiji
    WHERE mckey = io_new_mckey;

   SELECT mckey
     INTO wk_old_mckey
     FROM fnpick_ctl
    WHERE stno = in_motostno;

   get_picking_type (wk_old_mckey,
                     io_picking_type,
                     io_return_code,
                     io_return_message);

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   IF io_picking_type = ykk_global_defination.picking_type_subdivided
   THEN
      SELECT *
        INTO wk_fnsiji_row
        FROM (  SELECT *
                  FROM fnsiji
                 WHERE mckey = wk_old_mckey AND subdivide_flag = '1'
              ORDER BY nyusyusu)
       WHERE ROWNUM = 1;

      wk_fnsiji_row.mckey := 'TERM' || in_motostno;
      wk_fnsiji_row.hansokey := 'TERM' || in_motostno;

      INSERT INTO fnsiji
           VALUES wk_fnsiji_row;

      SELECT SUM (nyusyusu) - wk_fnsiji_row.nyusyusu
        INTO io_remaining_picking_num
        FROM fnsiji
       WHERE mckey = wk_old_mckey;
   ELSIF io_picking_type = ykk_global_defination.picking_type_normal
   THEN
      SELECT SUM (nyusyusu)
        INTO io_remaining_picking_num
        FROM fnsiji
       WHERE mckey = wk_old_mckey;

      SELECT retrieval_station
        INTO wk_retrieval_station
        FROM fnsiji
       WHERE mckey = wk_old_mckey AND ROWNUM = 1;


      IF wk_retrieval_station IN
            (ykk_global_defination.retrieval_station_plating,
             ykk_global_defination.retrieval_station_si,
             ykk_global_defination.retrieval_station_painting)
      THEN
         SELECT COUNT (*) - 1
           INTO io_remaining_job_num
           FROM (  SELECT section, line, retrieval_station
                     FROM fnsiji
                    WHERE mckey = wk_old_mckey
                 GROUP BY section, line, retrieval_station);

         SELECT section,
                line,
                retrieval_station,
                picking_num
           INTO wk_section,
                wk_line,
                wk_retrieval_station_temp,
                wk_picking_num
           FROM (  SELECT section,
                          line,
                          retrieval_station,
                          SUM (fnsiji.nyusyusu) AS picking_num
                     FROM fnsiji
                    WHERE mckey = wk_old_mckey
                 GROUP BY section, line, retrieval_station
                 ORDER BY picking_num ASC)
          WHERE ROWNUM = 1;

         OPEN fnsiji_list_1 (wk_old_mckey,
                             wk_section,
                             wk_line,
                             wk_retrieval_station_temp);

         LOOP
            FETCH fnsiji_list_1 INTO fnsiji_list_1_iterator;

            IF fnsiji_list_1%NOTFOUND = TRUE
            THEN
               IF fnsiji_list_1%ROWCOUNT = 0
               THEN
                  io_return_code := 8000102;
                  io_return_message := '数据不存在';
               END IF;

               EXIT;
            END IF;

            fnsiji_list_1_iterator.mckey := 'TERM' || in_motostno;
            fnsiji_list_1_iterator.hansokey := 'TERM' || in_motostno;

            INSERT INTO fnsiji
                 VALUES fnsiji_list_1_iterator;
         END LOOP;
      ELSIF wk_retrieval_station =
               ykk_global_defination.retrieval_station_assembling
      THEN
         SELECT COUNT (*) - 1
           INTO io_remaining_job_num
           FROM (  SELECT section, retrieval_station
                     FROM fnsiji
                    WHERE mckey = wk_old_mckey
                 GROUP BY section, retrieval_station);

         SELECT section, retrieval_station, picking_num
           INTO wk_section, wk_retrieval_station_temp, wk_picking_num
           FROM (  SELECT section,
                          retrieval_station,
                          SUM (fnsiji.nyusyusu) AS picking_num
                     FROM fnsiji
                    WHERE mckey = wk_old_mckey
                 GROUP BY section, retrieval_station
                 ORDER BY picking_num ASC)
          WHERE ROWNUM = 1;

         OPEN fnsiji_list_2 (wk_old_mckey,
                             wk_section,
                             wk_retrieval_station_temp);

         LOOP
            FETCH fnsiji_list_2 INTO fnsiji_list_2_iterator;

            IF fnsiji_list_2%NOTFOUND = TRUE
            THEN
               IF fnsiji_list_2%ROWCOUNT = 0
               THEN
                  io_return_code := 8000102;
                  io_return_message := '数据不存在';
               END IF;

               EXIT;
            END IF;

            fnsiji_list_2_iterator.mckey := 'TERM' || in_motostno;
            fnsiji_list_2_iterator.hansokey := 'TERM' || in_motostno;

            INSERT INTO fnsiji
                 VALUES fnsiji_list_2_iterator;
         END LOOP;
      ELSIF wk_retrieval_station IN
               (ykk_global_defination.retrieval_station_completing31,
                ykk_global_defination.retrieval_station_completing32,
                ykk_global_defination.retrieval_station_completing41,
                ykk_global_defination.retrieval_station_completing42,
                ykk_global_defination.retrieval_station_MF,
                ykk_global_defination.retrieval_station_PF,
                ykk_global_defination.retrieval_station_VF)
      THEN
         SELECT COUNT (*) - 1
           INTO io_remaining_job_num
           FROM (  SELECT retrieval_station, section
                     FROM fnsiji
                    WHERE mckey = wk_old_mckey
                 GROUP BY retrieval_station, section);

         SELECT retrieval_station, section, picking_num
           INTO wk_retrieval_station_temp, wk_section, wk_picking_num
           FROM (  SELECT retrieval_station,
                          section,
                          SUM (fnsiji.nyusyusu) AS picking_num
                     FROM fnsiji
                    WHERE mckey = wk_old_mckey
                 GROUP BY retrieval_station, section
                 ORDER BY picking_num ASC)
          WHERE ROWNUM = 1;

         OPEN fnsiji_list_3 (wk_old_mckey,
                             wk_retrieval_station_temp,
                             wk_section);

         LOOP
            FETCH fnsiji_list_3 INTO fnsiji_list_3_iterator;

            IF fnsiji_list_3%NOTFOUND = TRUE
            THEN
               IF fnsiji_list_3%ROWCOUNT = 0
               THEN
                  io_return_code := 8000102;
                  io_return_message := '数据不存在';
               END IF;

               EXIT;
            END IF;

            fnsiji_list_3_iterator.mckey := 'TERM' || in_motostno;
            fnsiji_list_3_iterator.hansokey := 'TERM' || in_motostno;

            INSERT INTO fnsiji
                 VALUES fnsiji_list_3_iterator;
         END LOOP;
      ELSIF wk_retrieval_station IN
               (ykk_global_defination.retrieval_station_fuyong,
                ykk_global_defination.retrieval_station_external)
      THEN
         SELECT COUNT (*) - 1
           INTO io_remaining_job_num
           FROM (  SELECT customer_code, retrieval_station
                     FROM fnsiji
                    WHERE mckey = wk_old_mckey
                 GROUP BY customer_code, retrieval_station);

         SELECT customer_code, retrieval_station, picking_num
           INTO wk_customer_code, wk_retrieval_station_temp, wk_picking_num
           FROM (  SELECT customer_code,
                          retrieval_station,
                          SUM (fnsiji.nyusyusu) AS picking_num
                     FROM fnsiji
                    WHERE mckey = wk_old_mckey
                 GROUP BY customer_code, retrieval_station
                 ORDER BY picking_num ASC)
          WHERE ROWNUM = 1;

         OPEN fnsiji_list_4 (wk_old_mckey,
                             wk_customer_code,
                             wk_retrieval_station_temp);

         LOOP
            FETCH fnsiji_list_4 INTO fnsiji_list_4_iterator;

            IF fnsiji_list_4%NOTFOUND = TRUE
            THEN
               IF fnsiji_list_4%ROWCOUNT = 0
               THEN
                  io_return_code := 8000102;
                  io_return_message := '数据不存在';
               END IF;

               EXIT;
            END IF;

            fnsiji_list_4_iterator.mckey := 'TERM' || in_motostno;
            fnsiji_list_4_iterator.hansokey := 'TERM' || in_motostno;

            INSERT INTO fnsiji
                 VALUES fnsiji_list_4_iterator;
         END LOOP;
      ELSE
         SELECT COUNT (*) - 1
           INTO io_remaining_job_num
           FROM (  SELECT fnsiji.customer_code,
                          fnsiji.retrieval_station,
                          fnsyotei.order_no
                     FROM fnsiji, fnsyotei
                    WHERE     fnsiji.mckey = wk_old_mckey
                          AND fnsiji.retrieval_plan_key =
                                 fnsyotei.retrieval_plankey
                 GROUP BY fnsiji.customer_code,
                          fnsiji.retrieval_station,
                          fnsyotei.order_no);

         SELECT customer_code,
                retrieval_station,
                order_no,
                picking_num
           INTO wk_customer_code,
                wk_retrieval_station_temp,
                wk_order_no,
                wk_picking_num
           FROM (  SELECT fnsiji.customer_code,
                          fnsiji.retrieval_station,
                          fnsyotei.order_no,
                          SUM (fnsiji.nyusyusu) AS picking_num
                     FROM fnsiji, fnsyotei
                    WHERE     fnsiji.retrieval_plan_key =
                                 fnsyotei.retrieval_plankey
                          AND mckey = wk_old_mckey
                 GROUP BY fnsiji.customer_code,
                          fnsiji.retrieval_station,
                          fnsyotei.order_no
                 ORDER BY picking_num ASC)
          WHERE ROWNUM = 1;

         OPEN fnsiji_list_5 (wk_old_mckey,
                             wk_customer_code,
                             wk_retrieval_station_temp,
                             wk_order_no);

         LOOP
            FETCH fnsiji_list_5 INTO fnsiji_list_5_iterator;

            IF fnsiji_list_5%NOTFOUND = TRUE
            THEN
               IF fnsiji_list_5%ROWCOUNT = 0
               THEN
                  io_return_code := 8000102;
                  io_return_message := '数据不存在';
               END IF;

               EXIT;
            END IF;

            fnsiji_list_5_iterator.mckey := 'TERM' || in_motostno;
            fnsiji_list_5_iterator.hansokey := 'TERM' || in_motostno;

            INSERT INTO fnsiji
                 VALUES fnsiji_list_5_iterator;
         END LOOP;
      END IF;


      io_remaining_picking_num := io_remaining_picking_num - wk_picking_num;
   ELSE
      IF    io_picking_type = ykk_global_defination.picking_type_converse
         OR io_picking_type = ykk_global_defination.picking_type_total
         OR io_picking_type = ykk_global_defination.picking_type_cycle
      THEN
         SELECT fnsiji.retrieval_station
           INTO wk_retrieval_station
           FROM fnsiji
          WHERE fnsiji.mckey = wk_old_mckey AND ROWNUM = 1;

         IF wk_retrieval_station =
               ykk_global_defination.retrieval_station_warehouse
         THEN
            io_picking_type := ykk_global_defination.picking_type_back;
         END IF;
      END IF;

      io_new_mckey := wk_old_mckey;
      io_remaining_job_num := 0;
      io_remaining_picking_num := 0;
   END IF;

   IF io_return_code != 0
   THEN
      GOTO endlabel;
   END IF;

   IF io_picking_type IN
         (ykk_global_defination.picking_type_converse,
          ykk_global_defination.picking_type_total)
   THEN
      update_label_data_for_printing (io_new_mckey,
                                      in_printer_no,
                                      in_user_id,
                                      in_user_name,
                                      io_return_code,
                                      io_return_message);

      IF io_return_code != 0
      THEN
         GOTO endlabel;
      END IF;
   END IF;

  <<endlabel>>
   IF fnsiji_list_1%ISOPEN = TRUE
   THEN
      CLOSE fnsiji_list_1;
   END IF;

   IF fnsiji_list_2%ISOPEN = TRUE
   THEN
      CLOSE fnsiji_list_2;
   END IF;

   IF fnsiji_list_3%ISOPEN = TRUE
   THEN
      CLOSE fnsiji_list_3;
   END IF;

   IF fnsiji_list_4%ISOPEN = TRUE
   THEN
      CLOSE fnsiji_list_4;
   END IF;

   IF fnsiji_list_5%ISOPEN = TRUE
   THEN
      CLOSE fnsiji_list_5;
   END IF;

   IF io_return_code != 0
   THEN
      ROLLBACK;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      IF fnsiji_list_1%ISOPEN = TRUE
      THEN
         CLOSE fnsiji_list_1;
      END IF;

      IF fnsiji_list_2%ISOPEN = TRUE
      THEN
         CLOSE fnsiji_list_2;
      END IF;

      IF fnsiji_list_3%ISOPEN = TRUE
      THEN
         CLOSE fnsiji_list_3;
      END IF;

      IF fnsiji_list_4%ISOPEN = TRUE
      THEN
         CLOSE fnsiji_list_4;
      END IF;

      IF fnsiji_list_5%ISOPEN = TRUE
      THEN
         CLOSE fnsiji_list_5;
      END IF;

      ROLLBACK;
      io_return_code := 8000155;
      io_return_message := '拣选设定时发生错误' || ' ' || SQLERRM;
      DBMS_OUTPUT.put_line (SQLERRM);
END pre_picking;

/
