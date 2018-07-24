package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM11439
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.common.Parameter;

//#CM11440
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida <BR>
 * <BR>
 * Use the <CODE>StockControlParameter</CODE> class to pass the parameter between the screen and the schedule parameter in the inventory package.<BR>
 * This class maintains the items (count) to be used in each inventory package screen. The screen depends on the items (count).<BR>
 * <BR>
 * <DIR>
 * Allow the<CODE>StockControlParameter</CODE> class to maintain the items (count):<BR>
 * <BR>
 *     Worker Code <BR>
 *     Password <BR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Item Code <BR>
 *     Start Item Code <BR>
 *     End Item Code <BR>
 *     Item Name <BR>
 *     Selected Case/Piece division <BR>
 *     Case/Piece division <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Storage Case Qty <BR>
 *     Storage Piece Qty <BR>
 *     Shipping Case Qty <BR>
 *     picking piece qty <BR>
 *     Stock Case Qty <BR>
 *     stock piece qty <BR>
 *     Allocatable Packed qty per Case <BR>
 *     Allocatable Packed qty per Piece <BR>
 *     Result Case Qty <BR>
 *     Result Piece Qty <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry Date <BR>
 *     List Print Type <BR>
 *     Location No. <BR>
 *     Start Location No.. <BR>
 *     End Location No.. <BR>
 *     customer code <BR>
 *     customer name <BR>
 *     division All <BR>
 *     Storage Date/Time <BR>
 *     picking Date/Time <BR>
 *     Display order <BR>
 *     print condition 1 <BR>
 *     print condition 2 <BR>
 *     Preset line No. <BR>
 *     Stock ID <BR>
 *     Last update date/time <BR>
 *     Selection BoxON/OFF <BR>
 *     Worker Name <BR>
 *     Start Work Date <BR>
 *     End Work Date <BR>
 *     Area No. <BR>
 *     area type flag <BR>
 *     Area No.Array <BR>
 *     System type <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>S.Yoshida</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/10 05:36:59 $
 * @author  $Author: suresh $
 */
public class StockControlParameter extends Parameter
{
	//#CM11441
	// Class feilds ------------------------------------------------
	//#CM11442
	/**
	 * Display order(Item Code-Location sequence)
	 */
	public static final String DSPORDER_ITEM_LOCATION = "0";

	//#CM11443
	/**
	 * Display order(Item Code-Order of Storage Date)
	 */
	public static final String DSPORDER_ITEM_STORAGEDATE = "1";

	//#CM11444
	/**
	 * Display order(Location sequence)
	 */
	public static final String DSPORDER_LOCATION = "2";
	
	//#CM11445
	/**
	 * Display order(Item Code sequence)
	 */
	public static final String DSPORDER_ITEM = "3";

	//#CM11446
	/**
	 * Display order(customer code-Item Code sequence)
	 */
	public static final String DSPORDER_CUSTOMER_ITEM = "4";
	
	//#CM11447
	/**
	 * Print condition(Aggregate Item in Same Location)
	 */
	public static final String PRINTINGCONDITION_INTENSIVEPRINTING_ON = "0";

	//#CM11448
	/**
	 * Print condition(Not aggregated)
	 */
	public static final String PRINTINGCONDITION_INTENSIVEPRINTING_OFF = "1";

	//#CM11449
	/**
	 * Print condition(Print Qty)
	 */
	public static final String PRINTINGCONDITION_QTYPRINTING_ON = "0";

	//#CM11450
	/**
	 * Print condition(Not Print Qty)
	 */
	public static final String PRINTINGCONDITION_QTYPRINTING_OFF = "1";

	//#CM11451
	/**
	 * Case/Piece division(All)
	 */
	public static final String CASEPIECE_FLAG_ALL = "99";

	//#CM11452
	/**
	 * Case/Piece division(Case)
	 */
	public static final String CASEPIECE_FLAG_CASE = "1";

	//#CM11453
	/**
	 * Case/Piece division(Piece)
	 */
	public static final String CASEPIECE_FLAG_PIECE = "2";

	//#CM11454
	/**
	 * Case/Piece division(None)
	 */
	public static final String CASEPIECE_FLAG_NOTHING = "3";

	//#CM11455
	/**
	 * search flag(Plan)
	 */
	public static final String SEARCHFLAG_PLAN = "1";

	//#CM11456
	/**
	 * search flag(result)
	 */
	public static final String SEARCHFLAG_RESULT = "2";
	
	//#CM11457
	/**
	 * search flag(Unplanned storage)
	 */
	public static final String SEARCHFLAG_EX_STORAGE = "3";
	
	//#CM11458
	/**
	 * search flag(Unplanned storage)
	 */
	public static final String SEARCHFLAG_EX_RETRIEVAL = "4";

	//#CM11459
	/**
	 * Selection BoxEnabled/Disabled flag(Enabled)
	 */
	public static final boolean SELECT_BOX_ENABLE = true;

	//#CM11460
	/**
	 * Selection BoxEnabled/Disabled flag(Disabled)
	 */
	public static final boolean SELECT_BOX_DISNABLE = false;

	//#CM11461
	/**
	 * Selection BoxON/OFF(ON)
	 */
	public static final boolean SELECT_BOX_ON = true;

	//#CM11462
	/**
	 * Selection BoxON/OFF(OFF)
	 */
	public static final boolean SELECT_BOX_OFF = false;

	//#CM11463
	/**
	 * Area flag(Start)
	 */
	public static final String RANGE_START = "0";

	//#CM11464
	/**
	 * Area flag(End)
	 */
	public static final String RANGE_END = "1";

	//#CM11465
	/**
	 * Data modification flag (Without change)
	 */
	public static final String UPDATEFLAG_NO = "0";

	//#CM11466
	/**
	 * Data modification flag (With change)
	 */
	public static final String UPDATEFLAG_YES = "1";

	//#CM11467
	/**
	 * area type flag(Except automated warehouse)
	 */
	public static final String AREA_TYPE_FLAG_NOASRS = "1";
	
	//#CM11468
	/**
	 * Other System distinct key(WMS:Floor storage)
	 */
	public static final int SYSTEM_DISC_KEY_WMS = 0;

	//#CM11469
	/**
	 * Other System distinct key(relocation rack)
	 */
	public static final int SYSTEM_DISC_KEY_IDM = 1;

	//#CM11470
	/**
	 * Other System distinct key(ASRS)
	 */
	public static final int SYSTEM_DISC_KEY_ASRS = 2;

	//#CM11471
	// Class variables -----------------------------------------------
	//#CM11472
	/**
	 * Worker Code
	 */
	private String wWorkerCode;

	//#CM11473
	/**
	 * Password
	 */
	private String wPassword;

	//#CM11474
	/**
	 * Consignor code
	 */
	private String wConsignorCode;

	//#CM11475
	/**
	 * Consignor name
	 */
	private String wConsignorName;

	//#CM11476
	/**
	 * Item Code
	 */
	private String wItemCode;

	//#CM11477
	/**
	 * Start Item Code
	 */
	private String wFromItemCode;

	//#CM11478
	/**
	 * End Item Code
	 */
	private String wToItemCode;

	//#CM11479
	/**
	 * Item Name
	 */
	private String wItemName;

	//#CM11480
	/**
	 * Case/Piece division
	 */
	private String wCasePieceFlag;

	//#CM11481
	/**
	 * Selected Case/Piece division
	 */
	private String wSelectCasePieceFlag;

	//#CM11482
	/**
	 * Case/Piece division name
	 */
	private String wCasePieceFlagName;

	//#CM11483
	/**
	 * Packed qty per Case
	 */
	private int wEnteringQty;

	//#CM11484
	/**
	 * Packed qty per bundle
	 */
	private int wBundleEnteringQty;

	//#CM11485
	/**
	 * Storage Case Qty
	 */
	private int wStorageCaseQty;

	//#CM11486
	/**
	 * Storage Piece Qty
	 */
	private int wStoragePieceQty;

	//#CM11487
	/**
	 * Shipping Case Qty
	 */
	private int wRetrievalCaseQty;

	//#CM11488
	/**
	 * picking piece qty
	 */
	private int wRetrievalPieceQty;

	//#CM11489
	/**
	 * Stock Case Qty
	 */
	private int wStockCaseQty;
	
	//#CM11490
	/**
	 * stock piece qty
	 */
	private int wStockPieceQty;

	//#CM11491
	/**
	 * Allocatable Packed qty per Case
	 */
	private int wAllocateCaseQty;

	//#CM11492
	/**
	 * Allocatable Packed qty per Piece
	 */
	private int wAllocatePieceQty;

	//#CM11493
	/**
	 * Result Case Qty
	 */
	private int wResultCaseQty;

	//#CM11494
	/**
	 * Result Piece Qty
	 */
	private int wResultPieceQty;

	//#CM11495
	/**
	 * Case ITF
	 */
	private String wITF;

	//#CM11496
	/**
	 * Bundle ITF
	 */
	private String wBundleITF;

	//#CM11497
	/**
	 * Expiry Date
	 */
	private String wUseByDate;

	//#CM11498
	/**
	 * List Print Type
	 */
	private boolean wListFlg = false;

	//#CM11499
	/**
	 * Location No.
	 */
	private String wLocationNo;

	//#CM11500
	/**
	 * Start Location No..
	 */
	private String wFromLocationNo;

	//#CM11501
	/**
	 * End Location No..
	 */
	private String wToLocationNo;

	//#CM11502
	/**
	 * customer code
	 */
	private String wCustomerCode;

	//#CM11503
	/**
	 * customer name
	 */
	private String wCustomerName;

	//#CM11504
	/**
	 * division All
	 */
	private boolean wTotalFlag;

	//#CM11505
	/**
	 * Storage Date/Time
	 */
	private java.util.Date wStorageDate;
	
	//#CM11506
	/**
	 * Storage Date characters
	 */
	private String wStorageDateString;

	//#CM11507
	/**
	 * picking date characters
	 */
	private String wRetrievalDateString;

	//#CM11508
	/**
	 * Display order
	 */
	private String wDspOrder;

	//#CM11509
	/**
	 * print condition 1
	 */
	private String wPrintCondition1;

	//#CM11510
	/**
	 * print condition 2
	 */
	private String wPrintCondition2;

	//#CM11511
	/**
	 * Preset line No.
	 */
	private int wRowNo;

	//#CM11512
	/**
	 * Stock ID
	 */
	private String wStockId;

	//#CM11513
	/**
	 * Last update date/time
	 */
	private java.util.Date wLastUpdateDate;

	//#CM11514
	/**
	 * search flag (Plan/Result)
	 */
	private String wSearchFlag;

	//#CM11515
	/**
	 * Selection BoxEnabled/Disabled flag
	 */
	private boolean wSelectBoxFlag = true;

	//#CM11516
	/**
	 * Selection BoxON/OFF
	 */
	private boolean wSelectBoxCheck = true;

	//#CM11517
	/**
	 * Stock Qty
	 */
	private int wStockQty ;
    
	//#CM11518
	/**
	 * allocated qty
	 */
	private int wAllocateQty ;
    
	//#CM11519
	/**
	 * Consignor code(for display)
	 */
	private String wConsignorCodeDisp ;
    
	//#CM11520
	/**
	 * Item Code(for display)
	 */
	private String wItemCodeDisp ;
    
	//#CM11521
	/**
	 * Case/Piece division(for display)
	 */
	private String wCasePieceFlagDisp ;
    
	//#CM11522
	/**
	 * Start Location(for display)
	 */
	private String wFromLocationNoDisp ;
    
	//#CM11523
	/**
	 * End Location(for display)
	 */
	private String wToLocationNoDisp ;
    
	//#CM11524
	/**
	 * Case ITF(for display)
	 */
	private String wITFDisp ;
    
	//#CM11525
	/**
	 * Bundle ITF(for display)
	 */
	private String wBundleITFDisp ;

	//#CM11526
	/**
	 * Worker Name
	 */
	private String wWorkerName;
	
	//#CM11527
	/**
	 * Start Work Date<BR>
	 */
	private String wFromWorkDate;
	
	//#CM11528
	/**
	 * End Work Date<BR>
	 */
	private String wToWorkDate;

	//#CM11529
	/**
	 * Area No.<BR>
	 */
	private String wAreaNo;
	//#CM11530
	/**
	 * area type flag<BR>
	 */
	private String wAreaTypeFlag;
	//#CM11531
	/**
	 * Area No.Array<BR>
	 */
	private String[] wAreaNoArray;

	//#CM11532
	/**
	 * Area Name<BR>
	 */
	private String wAreaName;
	
	//#CM11533
	/**
	 * System type<BR>
	 */
	private int wSystemDiskKey;

	//#CM11534
	/**
	 * Total Stock Case Qty
	 */
	private long wTotalStockCaseQty;
	
	//#CM11535
	/**
	 * Total stock piece qty
	 */
	private long wTotalStockPieceQty;

	//#CM11536
	/**
	 * Total Allocatable Packed qty per Case
	 */
	private long wTotalAllocateCaseQty ;
    
	//#CM11537
	/**
	 * Total allocatable piece qty
	 */
	private long wTotalAllocatePieceQty ;

	//#CM11538
	// Class method --------------------------------------------------
	//#CM11539
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.2 $,$Date: 2006/10/10 05:36:59 $" );
	}

	//#CM11540
	// Constructors --------------------------------------------------
	//#CM11541
	/**
	 * Initialize this class.
	 */
	public StockControlParameter()
	{
	}

	//#CM11542
	// Public methods ------------------------------------------------
	//#CM11543
	/**
	 * Set the Worker code.
	 * @param arg Set theWorker Code
	 */
	public void setWorkerCode(String arg)
	{
		wWorkerCode = arg;
	}

	//#CM11544
	/**
	 * Obtain the Worker code.
	 * @return Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM11545
	/**
	 * Set the password.
	 * @param arg Set thePassword
	 */
	public void setPassword(String arg)
	{
		wPassword = arg;
	}

	//#CM11546
	/**
	 * Obtain the password.
	 * @return Password
	 */
	public String getPassword()
	{
		return wPassword;
	}

	//#CM11547
	/**
	 * Set the Consignor code.
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg;
	}

	//#CM11548
	/**
	 * Obtain the Consignor code.
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM11549
	/**
	 * Set the Consignor name.
	 * @param arg Set theConsignor name
	 */
	public void setConsignorName(String arg)
	{
		wConsignorName = arg;
	}

	//#CM11550
	/**
	 * Obtain the Consignor Name.
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return wConsignorName;
	}

	//#CM11551
	/**
	 * Set the item code.
	 * @param arg Set theItem Code
	 */
	public void setItemCode(String arg)
	{
		wItemCode = arg;
	}

	//#CM11552
	/**
	 * Obtain the item code.
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM11553
	/**
	 * Set the Start item code.
	 * @param arg Start item code to be set
	 */
	public void setFromItemCode(String arg)
	{
		wFromItemCode = arg;
	}

	//#CM11554
	/**
	 * Obtain the Start item code.
	 * @return Start Item Code
	 */
	public String getFromItemCode()
	{
		return wFromItemCode;
	}

	//#CM11555
	/**
	 * Set the End item code.
	 * @param arg End item code to be set
	 */
	public void setToItemCode(String arg)
	{
		wToItemCode = arg;
	}

	//#CM11556
	/**
	 * Obtain the End item code.
	 * @return End Item Code
	 */
	public String getToItemCode()
	{
		return wToItemCode;
	}

	//#CM11557
	/**
	 * Set the item name.
	 * @param arg Set theItem Name
	 */
	public void setItemName(String arg)
	{
		wItemName = arg;
	}

	//#CM11558
	/**
	 * Obtain the item name.
	 * @return Item Name
	 */
	public String getItemName()
	{
		return wItemName;
	}

	//#CM11559
	/**
	 * Set the Case/Piece division.
	 * @param Set theCase/Piece division
	 */
	public void setCasePieceFlag(String arg)
	{
		wCasePieceFlag = arg;
	}

	//#CM11560
	/**
	 * Obtain the Case/Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	//#CM11561
	/**
	 * Set the Selected Case/Piece division.
	 * @param Set the Selected Case/Piece division
	 */
	public void setSelectCasePieceFlag(String arg)
	{
		wSelectCasePieceFlag = arg;
	}

	//#CM11562
	/**
	 * Obtain the Selected Case/Piece division.
	 * @return Selected Case/Piece division
	 */
	public String getSelectCasePieceFlag()
	{
		return wSelectCasePieceFlag;
	}

	//#CM11563
	/**
	 * Case/Set the Piece division name.
	 * @param Case/Piece division name to be set
	 */
	public void setCasePieceFlagName(String arg)
	{
		wCasePieceFlagName = arg;
	}

	//#CM11564
	/**
	 * Case/Obtain the Piece division name.
	 * @return Case/Piece division name
	 */
	public String getCasePieceFlagName()
	{
		return wCasePieceFlagName;
	}

	//#CM11565
	/**
	 * Set the Packed qty per case.
	 * @param arg Packed qty per Case to be set
	 */
	public void setEnteringQty(int arg)
	{
		wEnteringQty = arg;
	}

	//#CM11566
	/**
	 * Obtain the Packed qty per case.
	 * @return Packed qty per Case
	 */
	public int getEnteringQty()
	{
		return wEnteringQty;
	}

	//#CM11567
	/**
	 * Set the Packed qty per bundle.
	 * @param arg Packed qty per bundle to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		wBundleEnteringQty = arg;
	}

	//#CM11568
	/**
	 * Obtain the Packed qty per bundle.
	 * @return Packed qty per bundle
	 */
	public int getBundleEnteringQty()
	{
		return wBundleEnteringQty;
	}

	//#CM11569
	/**
	 * Set the storage case qty.
	 * @param arg Storage Case Qty to be set
	 */
	public void setStorageCaseQty(int arg)
	{
		wStorageCaseQty = arg;
	}

	//#CM11570
	/**
	 * Obtain the storage case qty.
	 * @return Storage Case Qty
	 */
	public int getStorageCaseQty()
	{
		return wStorageCaseQty;
	}

	//#CM11571
	/**
	 * Set the storage piece qty.
	 * @param arg Storage Piece Qty to be set
	 */
	public void setStoragePieceQty(int arg)
	{
		wStoragePieceQty = arg;
	}

	//#CM11572
	/**
	 * Obtain the storage piece qty.
	 * @return Storage Piece Qty
	 */
	public int getStoragePieceQty()
	{
		return wStoragePieceQty;
	}

	//#CM11573
	/**
	 * Set the picking case qty.
	 * @param arg Shipping Case Qty to be set
	 */
	public void setRetrievalCaseQty(int arg)
	{
		wRetrievalCaseQty = arg;
	}

	//#CM11574
	/**
	 * Obtain the picking case qty.
	 * @return Shipping Case Qty
	 */
	public int getRetrievalCaseQty()
	{
		return wRetrievalCaseQty;
	}

	//#CM11575
	/**
	 * Set the picking piece qty.
	 * @param arg Picking piece qty to be set
	 */
	public void setRetrievalPieceQty(int arg)
	{
		wRetrievalPieceQty = arg;
	}

	//#CM11576
	/**
	 * Obtain the picking piece qty.
	 * @return picking piece qty
	 */
	public int getRetrievalPieceQty()
	{
		return wRetrievalPieceQty;
	}

	//#CM11577
	/**
	 * Set the Stock Case Qty
	 * @param arg Stock Case Qty to be set
	 */
	public void setStockCaseQty(int arg)
	{
		wStockCaseQty = arg;
	}

	//#CM11578
	/**
	 * Obtain Stock Case Qty
	 * @return Stock Case Qty
	 */
	public int getStockCaseQty()
	{
		return wStockCaseQty;
	}

	//#CM11579
	/**
	 * Set the stock piece qty.
	 * @param arg stock piece qty to be set
	 */
	public void setStockPieceQty(int arg)
	{
		wStockPieceQty = arg;
	}

	//#CM11580
	/**
	 * Obtain the stock piece qty.
	 * @return stock piece qty
	 */
	public int getStockPieceQty()
	{
		return wStockPieceQty;
	}

	//#CM11581
	/**
	 * Set the allocatable case qty.
	 * @param arg Allocatable Packed qty per Case to be set
	 */
	public void setAllocateCaseQty(int arg)
	{
		wAllocateCaseQty = arg;
	}

	//#CM11582
	/**
	 * Obtain the allocatable case qty.
	 * @return Allocatable Packed qty per Case
	 */
	public int getAllocateCaseQty()
	{
		return wAllocateCaseQty;
	}

	//#CM11583
	/**
	 * Set the allocatable piece qty.
	 * @param arg Allocatable Packed qty per Piece to be set
	 */
	public void setAllocatePieceQty(int arg)
	{
		wAllocatePieceQty = arg;
	}

	//#CM11584
	/**
	 * Obtain the allocatable piece qty.
	 * @return Allocatable Packed qty per Piece
	 */
	public int getAllocatePieceQty()
	{
		return wAllocatePieceQty;
	}

	//#CM11585
	/**
	 * Set the result case qty.
	 * @param arg result case qty to be set
	 */
	public void setResultCaseQty(int arg)
	{
		wResultCaseQty = arg;
	}

	//#CM11586
	/**
	 * Obtain the result case qty.
	 * @return Result Case Qty
	 */
	public int getResultCaseQty()
	{
		return wResultCaseQty;
	}

	//#CM11587
	/**
	 * Set the result piece qty.
	 * @param arg Result Piece Qty to be set
	 */
	public void setResultPieceQty(int arg)
	{
		wResultPieceQty = arg;
	}

	//#CM11588
	/**
	 * Obtain the result piece qty.
	 * @return Result Piece Qty
	 */
	public int getResultPieceQty()
	{
		return wResultPieceQty;
	}

	//#CM11589
	/**
	 * Set the Case ITF.
	 * @param arg Case ITF to be set
	 */
	public void setITF(String arg)
	{
		wITF = arg;
	}

	//#CM11590
	/**
	 * Obtain the Case ITF.
	 * @return Case ITF
	 */
	public String getITF()
	{
		return wITF;
	}

	//#CM11591
	/**
	 * Set the bundle ITF.
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleITF(String arg)
	{
		wBundleITF = arg;
	}

	//#CM11592
	/**
	 * Obtain the bundle ITF.
	 * @return Bundle ITF
	 */
	public String getBundleITF()
	{
		return wBundleITF;
	}

	//#CM11593
	/**
	 * Set the expiry date.
	 * @param arg Expiry Date to be set
	 */
	public void setUseByDate(String arg)
	{
		wUseByDate = arg;
	}

	//#CM11594
	/**
	 * Obtain the expiry date.
	 * @return Expiry Date
	 */
	public String getUseByDate()
	{
		return wUseByDate;
	}

	//#CM11595
	/**
	 * Set the list print division.
	 * @param arg List Print Type to be set
	 */
	public void setListFlg(boolean arg)
	{
		wListFlg = arg;
	}

	//#CM11596
	/**
	 * Obtain the list print division.
	 * @return List Print Type
	 */
	public boolean getListFlg()
	{
		return wListFlg;
	}

	//#CM11597
	/**
	 * Set the Location No.
	 * @param arg Location No. to be set
	 */
	public void setLocationNo(String arg)
	{
		wLocationNo = arg;
	}

	//#CM11598
	/**
	 * Obtain the Location No.
	 * @return Location No.
	 */
	public String getLocationNo()
	{
		return wLocationNo;
	}

	//#CM11599
	/**
	 * Set the Start Location No.
	 * @param arg Start Location No. to be set
	 */
	public void setFromLocationNo(String arg)
	{
		wFromLocationNo = arg;
	}

	//#CM11600
	/**
	 * Obtain the Start Location No.
	 * @return Start Location No.
	 */
	public String getFromLocationNo()
	{
		return wFromLocationNo;
	}

	//#CM11601
	/**
	 * Set the End Location No.
	 * @param arg End Location No. to be set
	 */
	public void setToLocationNo(String arg)
	{
		wToLocationNo = arg;
	}

	//#CM11602
	/**
	 * Obtain the End Location No.
	 * @return End Location No..
	 */
	public String getToLocationNo()
	{
		return wToLocationNo;
	}

	//#CM11603
	/**
	 * Set the customer code.
	 * @param arg customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		wCustomerCode = arg;
	}

	//#CM11604
	/**
	 * Obtain the customer code.
	 * @return customer code
	 */
	public String getCustomerCode()
	{
		return wCustomerCode;
	}

	//#CM11605
	/**
	 * Set the customer name.
	 * @param arg Consignor name to be set
	 */
	public void setCustomerName(String arg)
	{
		wCustomerName = arg;
	}

	//#CM11606
	/**
	 * Obtain the customer name.
	 * @return Consignor name
	 */
	public String getCustomerName()
	{
		return wCustomerName;
	}

	//#CM11607
	/**
	 * Set the All division.
	 * @param arg division All to be set
	 */
	public void setTotalFlag(boolean arg)
	{
		wTotalFlag = arg;
	}

	//#CM11608
	/**
	 * Obtain the division All.
	 * @return division All
	 */
	public boolean getTotalFlag()
	{
		return wTotalFlag;
	}

	//#CM11609
	/**
	 * Set the storage date/time.
	 * @param arg Storage Date/time to be set
	 */
	public void setStorageDate(java.util.Date arg)
	{
		wStorageDate = arg;
	}

	//#CM11610
	/**
	 * Obtain the storage date/time.
	 * @return Storage Date/time
	 */
	public java.util.Date getStorageDate()
	{
		return wStorageDate;
	}
	
	//#CM11611
	/**
	 * Storage Date characters
	 * @param Storage Date characters to be set
	 */
	public void setStorageDateString(String arg)
	{
		wStorageDateString = arg ;
	}

	//#CM11612
	/**
	 * Storage Date characters
	 * @return Storage Date characters
	 */
	public String getStorageDateString()
	{
		return wStorageDateString ;
	}

	//#CM11613
	/**
	 * picking date characters
	 * @param Picking date characters to be set
	 */
	public void setRetrievalDateString(String arg)
	{
		wRetrievalDateString = arg ;
	}

	//#CM11614
	/**
	 * picking date characters
	 * @return picking date characters
	 */
	public String getRetrievalDateString()
	{
		return wRetrievalDateString ;
	}

	//#CM11615
	/**
	 * Set the display sequence.
	 * @param arg Display order to be set
	 */
	public void setDspOrder(String arg)
	{
		wDspOrder = arg;
	}

	//#CM11616
	/**
	 * Obtain the display sequence.
	 * @return Display order
	 */
	public String getDspOrder()
	{
		return wDspOrder;
	}

	//#CM11617
	/**
	 * Set the print condition1.
	 * @param arg Print condition 1 to be set
	 */
	public void setPrintCondition1(String arg)
	{
		wPrintCondition1 = arg;
	}

	//#CM11618
	/**
	 * Obtain the print condition 1.
	 * @return Print condition
	 */
	public String getPrintCondition1()
	{
		return wPrintCondition1;
	}

	//#CM11619
	/**
	 * Set the print condition2.
	 * @param arg Print condition 2 to be set
	 */
	public void setPrintCondition2(String arg)
	{
		wPrintCondition2 = arg;
	}

	//#CM11620
	/**
	 * Obtain the print condition 2.
	 * @return print condition 2
	 */
	public String getPrintCondition2()
	{
		return wPrintCondition2;
	}

	//#CM11621
	/**
	 * Set the preset line No.
	 * @param arg Set thePreset line No.
	 */
	public void setRowNo(int arg)
	{
		wRowNo = arg;
	}

	//#CM11622
	/**
	 * Obtain the preset line No.
	 * @return Preset line No.
	 */
	public int getRowNo()
	{
		return wRowNo;
	}

	//#CM11623
	/**
	 * Set the stock ID.
	 * @param Set theStock ID
	 */
	public void setStockId(String arg)
	{
		wStockId = arg;
	}

	//#CM11624
	/**
	 * Obtain the Stock ID.
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return wStockId;
	}

	//#CM11625
	/**
	 * Set the last update date/time.
	 * @param Set the Last update date/time
	 */
	public void setLastUpdateDate(java.util.Date arg)
	{
		wLastUpdateDate = arg;
	}

	//#CM11626
	/**
	 * Obtain Last update date/time.
	 * @return Last update date/time
	 */
	public java.util.Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	//#CM11627
	/**
	 * Set the search flag (Plan/Result).
	 * @param search flag to be set
	 */
	public void setSearchFlag(String arg)
	{
		wSearchFlag = arg;
	}

	//#CM11628
	/**
	 * Obtain search flag (Plan/Result)
	 * @return search flag
	 */
	public String getSearchFlag()
	{
		return wSearchFlag;
	}

	//#CM11629
	/**
	 * Set the select Box Enabled/Disabled flag.
	 * @param arg Enabled/Disabled flag to be set
	 */
	public void setSelectBoxFlag(boolean arg)
	{
		wSelectBoxFlag = arg;
	}

	//#CM11630
	/**
	 * Obtain the selected Box On/Off flag.
	 * @return Enabled/Disabled flag
	 */
	public boolean getSelectBoxFlag()
	{
		return wSelectBoxFlag;
	}

	//#CM11631
	/**
	 * Set the selected BoXON/OFF.
	 * @param arg Set theON/OFF
	 */
	public void setSelectBoxCheck(boolean arg)
	{
		wSelectBoxCheck = arg;
	}

	//#CM11632
	/**
	 * Obtain the selected BoxON/OFF.
	 * @return ON/OFF
	 */
	public boolean getSelectBoxCheck()
	{
		return wSelectBoxCheck;
	}

	
	//#CM11633
	/**
	 * @return Returns the wAllocateQty.
	 */
	public int getAllocateQty()
	{
		return wAllocateQty;
	}
	//#CM11634
	/**
	 * @param allocateQty The wAllocateQty to set.
	 */
	public void setAllocateQty(int allocateQty)
	{
		wAllocateQty = allocateQty;
	}
	//#CM11635
	/**
	 * @return Returns the wStockQty.
	 */
	public int getStockQty()
	{
		return wStockQty;
	}
	//#CM11636
	/**
	 * @param stockQty The wStockQty to set.
	 */
	public void setStockQty(int stockQty)
	{
		wStockQty = stockQty;
	}
    

	//#CM11637
	/**
	 * Set the Consignor code (for display).
	 * @param arg Consignor code to be set(for display)
	 */
	public void setConsignorCodeDisp(String arg)
	{
		wConsignorCodeDisp = arg;
	}

	//#CM11638
	/**
	 * Obtain the Consignor code (for display).
	 * @return Consignor code(for display)
	 */
	public String getConsignorCodeDisp()
	{
		return wConsignorCodeDisp;
	}
	
	//#CM11639
	/**
	 * Set the item code (for display).
	 * @param arg Set theItem Code(for display)
	 */
	public void setItemCodeDisp(String arg)
	{
		wItemCodeDisp = arg;
	}

	//#CM11640
	/**
	 * Obtain the item code (for display).
	 * @return Item Code(for display)
	 */
	public String getItemCodeDisp()
	{
		return wItemCodeDisp;
	}
	
	//#CM11641
	/**
	 * Set the Case/Piece division (for display).
	 * @param arg Set theCase/Piece division(for display)
	 */
	public void setCasePieceFlagDisp(String arg)
	{
		wCasePieceFlagDisp = arg;
	}

	//#CM11642
	/**
	 * Obtain the Case/Piece division (for display).
	 * @return Case/Piece division(for display)
	 */
	public String getCasePieceFlagDisp()
	{
		return wCasePieceFlagDisp;
	}
	
	//#CM11643
	/**
	 * Set the Start Location (for display).
	 * @param arg Set the Start Location(for display)
	 */
	public void setFromLocationNoDisp(String arg)
	{
		wFromLocationNoDisp = arg;
	}

	//#CM11644
	/**
	 * Obtain the Start Location (for display).
	 * @return Start Location(for display)
	 */
	public String getFromLocationNoDisp()
	{
		return wFromLocationNoDisp;
	}
	
	//#CM11645
	/**
	 * Set the End Location (for display).
	 * @param arg Set the End Location(for display)
	 */
	public void setToLocationNoDisp(String arg)
	{
		wToLocationNoDisp = arg;
	}

	//#CM11646
	/**
	 * Obtain the End Location (for display).
	 * @return End Location(for display)
	 */
	public String getToLocationNoDisp()
	{
		return wToLocationNoDisp;
	}
	
	//#CM11647
	/**
	 * Set the Case ITF(for display).
	 * @param arg Set theCase ITF(for display)
	 */
	public void setITFDisp(String arg)
	{
		wITFDisp = arg;
	}

	//#CM11648
	/**
	 * Obtain the Case ITF(for display).
	 * @return Case ITF(for display)
	 */
	public String getITFDisp()
	{
		return wITFDisp;
	}
	
	//#CM11649
	/**
	 * Set the bundle ITF (for display).
	 * @param arg Set theBundle ITF(for display)
	 */
	public void setBundleITFDisp(String arg)
	{
		wBundleITFDisp = arg;
	}

	//#CM11650
	/**
	 * Obtain the bundle ITF (for display).
	 * @return Bundle ITF(for display)
	 */
	public String getBundleITFDisp()
	{
		return wBundleITFDisp;
	}
	
	//#CM11651
	/**
	 * Set the Worker name.
	 * @param arg Worker Name to be set
	 */
	public void setWorkerName(String arg)
	{
		wWorkerName = arg;
	}

	//#CM11652
	/**
	 * Obtain the Worker name.
	 * @return Worker Name
	 */
	public String getWorkerName()
	{
		return wWorkerName;
	}
	
	//#CM11653
	/**
	 * Set the Start work date.
	 * @param arg Set theStart Work Date
	 */
	public void setFromWorkDate(String arg)
	{
		wFromWorkDate = arg;
	}

	//#CM11654
	/**
	 * Obtain the start work date.
	 * @return Start Work Date
	 */
	public String getFromWorkDate()
	{
		return wFromWorkDate;
	}
	
	//#CM11655
	/**
	 * Set the End work date.
	 * @param arg Set theEnd Work Date
	 */
	public void setToWorkDate(String arg)
	{
		wToWorkDate = arg;
	}

	//#CM11656
	/**
	 * Obtain the End work date.
	 * @return End Work Date
	 */
	public String getToWorkDate()
	{
		return wToWorkDate;
	}
	
	//#CM11657
	/**
	 * Set the Area No.
	 * @param arg Area No.
	 */
	public void setAreaNo(String arg)
	{
	    wAreaNo = arg;
	    
	}
	//#CM11658
	/**
	 * Obtain the Area No.
	 * @return Area No.
	 */
	public String getAreaNo()
	{
		return wAreaNo;
	}
	
	//#CM11659
	/**
	 * Set the area type flag
	 * @param arg area type flag to be set
	 */
	public void setAreaTypeFlag(String arg)
	{
		wAreaTypeFlag = arg;
	}
	//#CM11660
	/**
	 * Obtain the area type flag.
	 * @return area type flag
	 */
	public String getAreaTypeFlag()
	{
		return wAreaTypeFlag;
	}

	//#CM11661
	/**
	 * Set the Area No. array.
	 * @param arg Area No.Array
	 */
	public void setAreaNoArray(String[] arg)
	{
		wAreaNoArray = arg;
	    
	}
	//#CM11662
	/**
	 * Obtain the Area No. array.
	 * @return Area No.Array
	 */
	public String[] getAreaNoArray()
	{
		return wAreaNoArray;
	}
	
	//#CM11663
	/**
	 * Set the area name.
	 * @param arg Area No.Array
	 */
	public void setAreaName(String arg)
	{
		wAreaName = arg;
	    
	}
	//#CM11664
	/**
	 * Obtain the area name.
	 * @return Area No.Array
	 */
	public String getAreaName()
	{
		return wAreaName;
	}
	
	
	//#CM11665
	/**
	 * Set the System type.
	 * @param arg Set theSystem type
	 */
	public void setSystemDiskKey(int arg)
	{
		wSystemDiskKey = arg;
	}
	//#CM11666
	/**
	 * Obtain the System type.
	 * @return System type
	 */
	public int getSystemDiskKey()
	{
		return wSystemDiskKey;
	}
	//#CM11667
	/**
	 * Set the total stock case qty.
	 * @param arg Set the total stock Case Qty
	 */
	public void setTotalStockCaseQty(long arg)
	{
		wTotalStockCaseQty = arg;
	}

	//#CM11668
	/**
	 * Obtain the total stock case qty.
	 * @return Total Stock Case Qty
	 */
	public long getTotalStockCaseQty()
	{
		return wTotalStockCaseQty;
	}

	//#CM11669
	/**
	 * Total Set the stock piece qty.
	 * @param arg Set the total stock piece qty
	 */
	public void setTotalStockPieceQty(long arg)
	{
		wTotalStockPieceQty = arg;
	}

	//#CM11670
	/**
	 * Obtain the total stock piece qty.
	 * @return Total stock piece qty
	 */
	public long getTotalStockPieceQty()
	{
		return wTotalStockPieceQty;
	}

	//#CM11671
	/**
	 * Total Set the allocatable case qty.
	 * @param arg Set the total allocatable Packed qty per Case
	 */
	public void setTotalAllocateCaseQty(long arg)
	{
		wTotalAllocateCaseQty = arg;
	}

	//#CM11672
	/**
	 * Total Obtain the allocatable case qty.
	 * @return Total Allocatable Packed qty per Case
	 */
	public long getTotalAllocateCaseQty()
	{
		return wTotalAllocateCaseQty;
	}

	//#CM11673
	/**
	 * Total Set the allocatable piece qty.
	 * @param arg Set theTotal allocatable piece qty
	 */
	public void setTotalAllocatePieceQty(long arg)
	{
		wTotalAllocatePieceQty = arg;
	}

	//#CM11674
	/**
	 * Total Obtain the allocatable piece qty.
	 * @return Total allocatable piece qty
	 */
	public long getTotalAllocatePieceQty()
	{
		return wTotalAllocatePieceQty;
	}
}
