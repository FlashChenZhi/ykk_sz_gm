DROP PACKAGE YKK_GLOBAL_DEFINATION;

CREATE OR REPLACE PACKAGE     ykk_global_defination
AS
   /***********************************************************************************/
   --warehouse_station_no             CONSTANT CHAR (4) := '9001';
   /***********************************************************************************/
   arc_number CONSTANT                       CHAR (1) := '1';
   /***********************************************************************************/
   system_online CONSTANT                    CHAR (1) := '1';
   /***********************************************************************************/
   weight_report_not_completed CONSTANT      CHAR (1) := '0';
   weight_report_executing CONSTANT          CHAR (1) := '1';
   weight_report_completed CONSTANT          CHAR (1) := '2';
   /***********************************************************************************/
   location_available CONSTANT               CHAR (1) := '0';
   location_transporting CONSTANT            CHAR (1) := '1';
   location_on_cycle CONSTANT                CHAR (1) := '2';
   /***********************************************************************************/
   location_not_dispatched CONSTANT          CHAR (1) := '0';
   location_dispatched CONSTANT              CHAR (1) := '1';
   /***********************************************************************************/
   location_accessable CONSTANT              CHAR (1) := '0';
   /***********************************************************************************/
   locat_status_in_reserved CONSTANT         CHAR (1) := '1';
   locat_status_instock CONSTANT             CHAR (1) := '2';
   locat_status_out_reserved CONSTANT        CHAR (1) := '3';
   locat_status_out_transporting CONSTANT    CHAR (1) := '4';
   /***********************************************************************************/
   stockout_mode_unit CONSTANT               CHAR (1) := '1';
   stockout_mode_picking CONSTANT            CHAR (1) := '2';
   /***********************************************************************************/
   transportation_status_standby CONSTANT    CHAR (1) := '0';
   transportation_status_start CONSTANT      CHAR (1) := '1';
   /***********************************************************************************/
   transportation_type_stockin CONSTANT      CHAR (1) := '1';
   transportation_type_stockout CONSTANT     CHAR (1) := '2';
   transportation_type_sttost CONSTANT       CHAR (1) := '3';
   /***********************************************************************************/
   label_type_normal_stockout CONSTANT       CHAR (1) := '1';
   label_type_fuyong_stockout CONSTANT       CHAR (1) := '2';
   label_type_external_stockout CONSTANT     CHAR (1) := '3';
   label_type_customer_stockout CONSTANT     CHAR (1) := '4';
   label_type_subdivided_stockout CONSTANT   CHAR (1) := '5';
   /***********************************************************************************/
   label_option_flag_stockout CONSTANT       CHAR (1) := '0';
   label_option_flag_sttost CONSTANT         CHAR (1) := '1';
   /***********************************************************************************/
   label_prt_flag_not_printed CONSTANT       CHAR (1) := '0';
   label_prt_flag_need_print CONSTANT        CHAR (1) := '1';
   label_prt_flag_printing CONSTANT          CHAR (1) := '2';
   label_prt_flag_printed CONSTANT           CHAR (1) := '4';
   /***********************************************************************************/
   manage_item_flag_managed CONSTANT         CHAR (1) := '0';
   manage_item_flag_unmanaged CONSTANT       CHAR (1) := '1';
   /***********************************************************************************/
   item_not_forbidden CONSTANT               CHAR (1) := '0';
   item_forbidden CONSTANT                   CHAR (1) := '1';
   /***********************************************************************************/
   unit_status_operating CONSTANT            CHAR (1) := '1';
   unit_status_suspend CONSTANT              CHAR (1) := '2';
   unit_status_trouble CONSTANT              CHAR (1) := '4';
   /***********************************************************************************/
   stock_type_stockin CONSTANT               CHAR (1) := '1';
   stock_type_stockout CONSTANT              CHAR (1) := '2';
   /***********************************************************************************/
   reservation_info_not_processed CONSTANT   CHAR (1) := '0';
   reservation_info_processed CONSTANT       CHAR (1) := '1';
   /***********************************************************************************/
   dispatch_type_menu CONSTANT               CHAR (1) := '0';
   dispatch_type_auto CONSTANT               CHAR (1) := '1';
   /***********************************************************************************/
   display_info_not_processed CONSTANT       CHAR (1) := '0';
   display_info_processed CONSTANT           CHAR (1) := '1';
   /***********************************************************************************/
   group_size CONSTANT                       NUMBER := 15;
   /***********************************************************************************/
   retrieval_station_plating CONSTANT        CHAR (2) := '11';
   retrieval_station_painting CONSTANT       CHAR (2) := '22';
   retrieval_station_assembling CONSTANT     CHAR (2) := '24';
   retrieval_station_si CONSTANT             CHAR (2) := '21';
   retrieval_station_completing31 CONSTANT   CHAR (2) := '31';
   retrieval_station_completing32 CONSTANT   CHAR (2) := '32';
   retrieval_station_completing41 CONSTANT   CHAR (2) := '41';
   retrieval_station_completing42 CONSTANT   CHAR (2) := '42';
   retrieval_station_external CONSTANT       CHAR (2) := '25';
   retrieval_station_fuyong CONSTANT         CHAR (2) := '23';
   retrieval_station_customer CONSTANT       CHAR (2) := '26';
   retrieval_station_warehouse CONSTANT      CHAR (2) := '90';
   retrieval_station_MF CONSTANT             CHAR (2) := '12';
   retrieval_station_PF CONSTANT             CHAR (2) := '13';
   retrieval_station_VF CONSTANT             CHAR (2) := '14';
   /***********************************************************************************/
   arrival_report_processed_stg1 CONSTANT    CHAR (1) := '1';
   arrival_report_processed_stg2 CONSTANT    CHAR (1) := '2';
   /***********************************************************************************/
   warehouse_type_auto CONSTANT              CHAR (1) := '1';
   /***********************************************************************************/
   transportation_work_type_plan CONSTANT    CHAR (1) := '1';
   transportation_work_type_auto CONSTANT    CHAR (1) := '2';
   transportation_work_type_key CONSTANT     CHAR (1) := '3';
   transportation_work_type_locat CONSTANT   CHAR (1) := '4';
   transportation_work_type_cycle CONSTANT   CHAR (1) := '5';
   transportation_work_type_back CONSTANT    CHAR (1) := '6';
   /***********************************************************************************/
   difference_type_add CONSTANT              CHAR (1) := '0';
   difference_type_sub CONSTANT              CHAR (1) := '1';
   /***********************************************************************************/
   stockin_mode_normal CONSTANT              CHAR (1) := '1';
   stockin_mode_empty_bucket CONSTANT        CHAR (1) := '2';
   /***********************************************************************************/
   picking_type_normal CONSTANT              CHAR (1) := '0';
   picking_type_converse CONSTANT            CHAR (1) := '1';
   picking_type_total CONSTANT               CHAR (1) := '2';
   picking_type_back CONSTANT                CHAR (1) := '3';
   picking_type_abnormal CONSTANT            CHAR (1) := '4';
   picking_type_cycle CONSTANT               CHAR (1) := '5';
   picking_type_subdivided CONSTANT          CHAR (1) := '6';
   /***********************************************************************************/
   storage_place_awc_warehouse CONSTANT      CHAR (1) := '0';
   storage_place_flat_warehouse CONSTANT     CHAR (1) := '1';
   /***********************************************************************************/
   retrieval_result_not_processed CONSTANT   CHAR (1) := '0';
   /***********************************************************************************/
   stock_amount_equal CONSTANT               CHAR (1) := '0';
   stock_amount_increase CONSTANT            CHAR (1) := '1';
   stock_amount_decrease CONSTANT            CHAR (1) := '2';
   /***********************************************************************************/
   station_type_picking CONSTANT             CHAR (1) := '1';
   station_type_reject CONSTANT              CHAR (1) := '3';
   /***********************************************************************************/
   trans_priority_urgency CONSTANT           CHAR (1) := '1';
   trans_priority_normal CONSTANT            CHAR (1) := '2';
   trans_priority_empty_confirm CONSTANT     CHAR (1) := '9';
   /***********************************************************************************/
   normal_stockout CONSTANT                  CHAR (1) := '0';
   external_stockout CONSTANT                CHAR (1) := '1';
   /***********************************************************************************/
   write_fnsyotei_log_flag CONSTANT          CHAR (1) := '1';
   /***********************************************************************************/
   carton_entrance_prohibited CONSTANT       CHAR (1) := '1';
   /***********************************************************************************/
   default_group_no CONSTANT                 CHAR (6) := '000000';
END ykk_global_defination;

/
