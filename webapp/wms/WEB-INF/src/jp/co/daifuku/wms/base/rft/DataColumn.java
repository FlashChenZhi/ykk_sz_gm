// $Id: DataColumn.java,v 1.2 2006/11/14 06:08:57 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700330
/*
 * Copyright 2004-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM700331
/**
 * Define the length of the every particular item telegram and in the data file. <BR>
 * The base class of telegram succeeds to this interface. 
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:57 $
 * @author $Author: suresh $
 */
public interface DataColumn
{
	//#CM700332
	// Class fields --------------------------------------------------
	//#CM700333
	/**
	 * Length of Person in charge code(byte)
	 */
	static final int LEN_WORKER_CODE = 4 ;

	//#CM700334
	/**
	 * Length of person in charge name
	 */
	static final int LEN_WORKER_NAME = 20;

	//#CM700335
	/**
	 * Length of Work type(byte)
	 */
	static final int LEN_WORK_TYPE = 2;

	//#CM700336
	/**
	 * Length of detailed Work type(byte)
	 */
	static final int LEN_WORK_DETAILS = 1;

	//#CM700337
	/**
	 * Length of SEQ(Byte)
	 */
	public static final int LEN_SEQ = 4;

	//#CM700338
	/**
	 * Length of RFT machine(Byte)
	 */
	public static final int LEN_RFTNO = 3;

	//#CM700339
	/**
	 * Length of Telegram ID(Byte)
	 */
	static final int LEN_ID = 4;

	//#CM700340
	/**
	 * Length of RFT transmission time(Byte)
	 */
	public static final int LEN_RFTSENDDATE = 6;

	//#CM700341
	/**
	 * Length of SERVSEND transmission time(Byte)
	 */
	public static final int LEN_SERVSENDDATE = 6;
	//#CM700342
	/**
	 * Length of date item(byte)
	 */
	static final int LEN_PLAN_DATE = 8 ;

	//#CM700343
	/**
	 * Length of Consignor Code(byte)
	 */
	static final int LEN_CONSIGNOR_CODE = 16 ;
	
	//#CM700344
	/**
	 * Length of Consignor Name(byte)
	 */
	static final int LEN_CONSIGNOR_NAME = 40 ;

	//#CM700345
	/**
	 * Length of Supplier Code(byte)
	 */
	static final int LEN_SUPPLIER_CODE = 16 ;
	
	//#CM700346
	/**
	 * Length of Supplier Name(byte)
	 */
	static final int LEN_SUPPLIER_NAME = 40 ;

	//#CM700347
	/**
	 * Length of Customer Code(byte)
	 */
	static final int LEN_CUSTOMER_CODE = 16 ;
	
	//#CM700348
	/**
	 * Length of Customer Name(byte)
	 */
	static final int LEN_CUSTOMER_NAME = 40 ;

	//#CM700349
	/**
	 * Length of Order No.(byte)
	 */
	static final int LEN_ORDER_NO = 16 ;
	
	//#CM700350
	/**
	 * Length of slip number(byte)
	 */
	static final int LEN_TICKET_NO = 16 ;
	
	//#CM700351
	/**
	 * Length of slip line No.(byte)
	 */
	static final int LEN_TICKET_LINE_NO = 3 ;
	
	//#CM700352
	/**
	 * Length of item identification Code(byte)
	 */
	static final int LEN_ITEM_ID = 16 ;
	
	//#CM700353
	/**
	 * Length of item Code(byte)
	 */
	static final int LEN_ITEM_CODE = 16 ;
	
	//#CM700354
	/**
	 * Length of JAN Code(byte)
	 */
	static final int LEN_JAN_CODE = 16 ;
	
	//#CM700355
	/**
	 * Length of Bundle ITF(byte)
	 */
	static final int LEN_BUNDLE_ITF = 16 ;
	
	//#CM700356
	/**
	 * Length of Case ITF(byte)
	 */
	static final int LEN_ITF = 16 ;
	
	//#CM700357
	/**
	 * Length of Item Name(byte)
	 */
	static final int LEN_ITEM_NAME = 40 ;
	
	//#CM700358
	/**
	 * Length of Item classification(byte)
	 */
	static final int LEN_ITEM_CATEGORY_CODE = 4 ;
	
	//#CM700359
	/**
	 * Length of Packed qty per bundle(byte)
	 */
	static final int LEN_BUNDLE_ENTERING_QTY = 6 ;
	
	//#CM700360
	/**
	 * Length of Packed qty per Case(byte)
	 */
	static final int LEN_ENTERING_QTY = 6 ;
	
	//#CM700361
	/**
	 * Length of unit(byte)
	 */
	static final int LEN_UNIT = 6 ;
	
	//#CM700362
	/**
	 * Length of Lot No.(byte)
	 */
	static final int LEN_LOT_NO = 10 ;
	
	//#CM700363
	/**
	 * Length of Expiry date(byte)
	 */
	static final int LEN_USE_BY_DATE = 8 ;
	
	//#CM700364
	/**
	 * Length of manufacturing day(byte)
	 */
	static final int LEN_MANUFACTURE_DATE = 8 ;
	
	//#CM700365
	/**
	 * Length of quality(byte)
	 */
	static final int LEN_QUALITY = 1 ;
	
	//#CM700366
	/**
	 * Lengths of planned qty(byte)
	 */
	static final int LEN_PLAN_QTY = 9 ;
	
	//#CM700367
	/**
	 * Length of actual qty(byte)
	 */
	static final int LEN_RESULT_QTY = 9 ;
	
	//#CM700368
	/**
	 * Length of reservation(byte)
	 */
	static final int LEN_RESERVE = 1 ;

	//#CM700369
	/**
	 * Length of response flag
	 */
	static final int LEN_ANS_CODE = 1 ;

	//#CM700370
	/**
	 * Length of ETX
	 */
	static final int LEN_ETX = 1 ;

	//#CM700371
	/**
	 * Length of system Work date item(byte)
	 */
	static final int LEN_WORKING_DATE = LEN_PLAN_DATE;

	//#CM700372
	/**
	 * Length of File name
	 */
	static final int LEN_FILE_NAME = 30 ;

	//#CM700373
	/**
	 * Lengths of File code qty
	 */
	static final int LEN_FILE_RECORD_NUMBER = 6 ;

	//#CM700374
	/**
	 * Lengths of Batch No.(byte)
	 */
	static final int LEN_BATCH_NO = 3 ;
	
	//#CM700375
	/**
	 * Length of service No.(byte)
	 */
	static final int LEN_BATCH2_NO = 3 ;

	//#CM700376
	/**
	 * Length of work form (Case/Piece flag)
	 */
	static final int LEN_WORK_FORM = 1 ;
	 
	//#CM700377
	/**
	 * Length of mode of packing (Case/Piece flag)(byte)
	 */
	static final int LEN_CASE_PIECE_FLAG = 1 ;
	
	//#CM700378
	/**
	 * Length of Location No (Location)(byte)
	 */
	static final int LEN_LOCATION = 16;
	
	//#CM700379
	/**
	 * Length of stock qty(byte)
	 */
	static final int LEN_STOCK_QTY = 9 ;

	//#CM700380
	/**
	 * Length of stock flag qty(byte)
	 */
	static final int LEN_STOCK_FLAG = 1 ;

	//#CM700381
	/**
	 * Length of set unit key
	 */
	static final int LEN_SET_UNIT_KEY = 16;
	
	//#CM700382
	/**
	 * Length of Picking  No.
	 */
	static final int LEN_PICKING_NO = 8;
	
	//#CM700383
	/**
	 * Length of Area No.
	 */
	static final int LEN_AREA_NO = 3;
	
	//#CM700384
	/**
	 * Length of zone No.
	 */
	static final int LEN_ZONE_NO = 3;

	//#CM700385
	/**
	 * Length of Area Name
	 */
	static final int LEN_AREA_NAME = 40;
	
	//#CM700386
	/**
	 * Length of Zone Name
	 */
	static final int LEN_ZONE_NAME = 40;
	
	//#CM700387
	/**
	 * Length of flag when being replenished
	 */
	static final int LEN_REPLENISHING_FLAG = 1;
	
	//#CM700388
	/**
	 * Length of Customer honorific title
	 */
	static final int LEN_CUSTOMER_PREFIX = 20;
	
	//#CM700389
	/**
	 * Length of block No.
	 */
	static final int LEN_BLOCK_NO = 3;
	
	//#CM700390
	/**
	 * Length of sort frontage
	 */
	static final int LEN_SORT_USE_BLOCK_NO = 16;
	
	//#CM700391
	/**
	 * Length of load arrangement No.
	 */
	static final int LEN_GROUPING_NO = 8;
	
	//#CM700392
	/**
	 * Length of packing No.
	 */
	static final int LEN_PACK_NO = 4;

	//#CM700393
	/**
	 * Length of movement work No(byte)
	 */
	static final int LEN_MOVE_JOB_NO = 8 ;

	//#CM700394
	/**
	 * Length of input flag
	 */
	static final int LEN_INPUT_TYPE = 1;
	
	//#CM700395
	/**
	 * Receiving Length of Work type
	 */
	static final int LEN_INSTOCK_WORK_TYPE = 1;
	
	//#CM700396
	/**
	 * Length of Supplier / order number
	 */
	static final int LEN_SUPL_ORDER_NO = 16;
	
	//#CM700397
	/**
	 * Length of TC/DC flag(byte)
	 */
	static final int LEN_TCDC_FLAG = 1;

	//#CM700398
	/**
	 * Length of acceptance flag(byte)
	 */
	static final int LEN_RECEIVE_FLAG = 1;

	//#CM700399
	/**
	 * Length of flag of paying in installments
	 */
	static final int LEN_DIVIDE_FLAG = 1;
	
	//#CM700400
	/**
	 * Lengths of work number
	 */
	static final int LEN_WORK_COUNT = 9;
	
	//#CM700401
	/**
	 * Length of Completion flag(byte)
	 */
	static final int LEN_COMPLETION_FLAG = 1;

	//#CM700402
	/**
	 * Length of list selection flag(byte)
	 */
	static final int LEN_LIST_SELECTION_FLAG = 1;

	//#CM700403
	/**
	 * Length of new stock making flag(byte)
	 */
	static final int LEN_ENABLE_CREATE_NEW_STOCK = 1;
	
	//#CM700404
	/**
	 * Lengths of remainder item qty(byte)
	 */
	static final int 	LEN_REMAINING_ITEM_COUNT = 9;
	
	//#CM700405
	/**
	 * Lengths of total item qty(byte)
	 */
	static final int 	LEN_TOTAL_ITEM_COUNT = 9;
	
	//#CM700406
	/**
	 * Lengths of Working time(byte)
	 */
	static final int  LEN_WORK_TIME = 5;
	
	//#CM700407
	/**
	 * Length of passage No.(byte)
	 */
	static final int  LEN_LANE_NO = 4;
	
	//#CM700408
	/**
	 * Length of Location position(byte)
	 */
	static final int  LEN_LOCATION_SIDE = 1;
	
	//#CM700409
	/**
	 * Length of box substitution order(byte)
	 */
	static final int LEN_BOX_INDEX = 1;
	
	//#CM700410
	/**
	 * Length of server date (byte)
	 */
	static final int LEN_SERVER_DATE = 14;
	
	//#CM700411
	/**
	 * Length of Work flag (byte) 
	 */
	static final int LEN_JOB_TYPE = 1;
	
	//#CM700412
	/**
	 * Length of report flag (byte)
	 */
	static final int LEN_REPORT_FLAG = 1;
	
	//#CM700413
	/**
	 * Length of terminal flag (byte)
	 */
	static final int LEN_TERMINAL_TYPE = 2;
	
	//#CM700414
	/**
	 * Length of terminal Internet Protocol address (byte)
	 */
	static final int LEN_IP_ADDRESS = 15;

	//#CM700415
	//#CM700416
	/** Added for movable rack Package. 
	/**
	 * Length of empty Location flag(byte)
	 */
	static final int 	LEN_EMPTY_KIND = 1;
	
	//#CM700417
	//#CM700418
	/** Added for the work data report. 2005/07/09
	/**
	 * Length of Work File name(byte)
	 */
	static final int LEN_WORK_FILE_NAME = 30;
	
	//#CM700419
	/**
	 * Length of Row No.(byte)
	 */
	static final int LEN_LINE_NO = 4;
	
	//#CM700420
	/**
	 * Data size(byte)
	 */
	static final int LEN_DATA_SIZE = 3;
	
	//#CM700421
	/**
	 * Content Data(byte)
	 */
	static final int LEN_DATA_CONTENTS = 512;
	
	//#CM700422
	/**
	 * Length of CRLF(byte)
	 */
	static final int LEN_CRLF = 2;
	
	//#CM700423
	/**
	 * Length of ID when being working(byte)
	 */
	static final int LEN_WORKING_ID = 4;
	
	//#CM700424
	/**
	 * Length of direction of going into(byte)
	 */
	static final int LEN_APPROACH_DIRECTION = 1;
	
	//#CM700425
	/**
	 * Detailed length for error(byte)
	 */
	static final int LEN_ERROR_DETAILS = 2;

	//#CM700426
	/**
	 * Length of consolidating work No(byte)
	 */
	static final int LEN_COLLECT_JOB_NO = 16;
	
	//#CM700427
	/**
	 * Length of mis-frequency(byte)
	 */
	static final int LEN_INSPECTION_ERR_COUNT = 5;

	//#CM700428
	/**
	 * Length of scanning mistake frequency(byte)
	 */
	static final int LEN_MISS_COUNT = 5;

	//#CM700429
	/**
	 * Length of retrieval object flag(byte)
	 */
	static final int LEN_SEARCH_TABLES = 1;

	//#CM700430
	/**
	 * Length of movement rack existence flag(byte)
	 */
	static final int LEN_MOBILE_RACK_FLAG = 1;
	
	//#CM700431
	/**
	 * Lengths of total sort destination qty(byte)
	 */
	static final int LEN_TOTAL_SORTING_COUNT = 9;
	
	//#CM700432
	/**
	 * Lengths of total sorts qty(byte)
	 */
	static final int LEN_TOTAL_SORTING_QTY = 9;
	
	//#CM700433
	/**
	 * Length of the order with crack(byte)
	 */
	static final int LEN_ALLOTMENT_NO = 4;

	//#CM700434
	/**
	 * Picking Length of Work flag(byte)
	 */
	static final int LEN_RETRIEVAL_WORK_TYPE = 1;
}
//#CM700435
//end of interface
