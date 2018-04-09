package jp.co.daifuku.wms.retrieval.schedule;


//#CM725565
/*
 * Created on Oct 5, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.Parameter;

//#CM725566
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR>
 * Use <CODE>RetrievalSupportParameter</CODE> class to pass parameters between the screen within the Picking package and the schedule. <BR>
 * Allow this class to maintain the field items to be used in each screen of the Picking package. Use a field item depending on each screen. <BR>
 * <BR>
 * <DIR>
 * Field item which the <CODE>RetrievalSupportParameter</CODE> class maintains <BR>
 * <BR>
 *     Worker Code <BR>
 *     Password  <BR>
 *     Worker Name <BR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Item Code <BR>
 *     Item Code (listcell)  <BR>
 *     Item Name <BR>
 *     Picking Date<BR>
 *     Start Picking Date <BR>
 *     End Picking Date <BR>
 *     Start Item Code  <BR>
 *     End Item Code  <BR>
 *     Case/Piece division <BR>
 *     Case/Piece division name  <BR>
 *     Planned Picking Date <BR>
 *     Picking Location  <BR>
 *     Expiry Date <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned total qty <BR>
 *     Planned Case Qty  <BR>
 *     Planned Piece Qty  <BR>
 *     Picking Case Qty  <BR>
 *     Picking Piece Qty  <BR>
 *     Shortage Case Qty <BR>
 *     Shortage Piece Qty <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Work status: <BR>
 *     Work status name  <BR>
 *     Relocation Case Qty  <BR>
 *     Relocation Piece Qty  <BR>
 *     Print Picking Instruction division  <BR>
 *     Picking Result display type  <BR>
 *     Cancel Remaining Work type  <BR>
 *     "Cancel Relocation" division  <BR>
 *     Start Location  <BR>
 *     End Location  <BR>
 *     Inventory Check Case Qty  <BR>
 *     Inventory Check Piece Qty  <BR>
 *     Stock Case Qty  <BR>
 *     Stock Piece Qty  <BR>
 *     Start Planned Picking Date<BR>
 *     End Planned Picking Date<BR>
 *     Display order <BR>
 *     Aggregate the display.<BR>
 *     Print type of Deleted Picking Plan list report<BR>
 *     Relocation status name <BR>
 *     Result Case Qty <BR>
 *     Result Piece Qty <BR>
 *     Work date<BR>
 *     Start work date <BR>
 *     End Work Date <BR>
 *     Case Location<BR>
 *     Piece Location <BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 * 	   Ticket No. <BR>
 *     Line No. <BR>
 *     Work No. <BR>
 *     Last update date/time <BR>
 *     Preset line No.  <BR>
 *     Picking Result Date <BR>
 *     Start Picking Result Date <BR>
 * 	   End Picking Result Date <BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *     Case Location (for display) <BR>
 *     Piece Location (for display) <BR>
 *     Case Order No. (for display) <BR>
 *     Piece Order No. (for display) <BR>
 *     Customer Code (for display) <BR>
 *     Picking Plan unique key <BR>
 *     Batch No. (Schedule No.) <BR>
 *     Entry type <BR>
 *     Work No. (vector)  <BR>
 *     Last update date/time (vector)  <BR>
 *     Work status (listcell)  <BR>
 *     Button type  <BR>
 *     Total Picking qty  <BR>
 *     Picking Case Qty  <BR>
 *     Picking Piece Qty  <BR>
 *     Quantity display type  <BR>
 *     Start Order No.  <BR>
 *     End Order No.  <BR>
 *     Start Item Code  <BR>
 *     End Item Code  <BR>
 *     Allocation target, storage place  <BR>
 *     Shortage evidence  <BR>
 *     Request for printing Shortage list  <BR>
 *     Plan unique key (vector)  <BR>
 *     System identification key  <BR>
 *     System identification key (Array)  <BR>
 *     Host Aggregated Batch No. (listcell)  <BR>
 *     Case/Piece division (listcell)  <BR>
 *     Order No. (listcell)  <BR>
 *     Host Aggregated Batch No.  <BR>
 *     Flag to enable or disable to check length. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>Muneendra</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:20:04 $
 * @author  $Author: suresh $
 */
public class RetrievalSupportParameter extends Parameter
{
	//#CM725567
	/**
	 * Case/Piece division "None" 
	 */
	public static final String CASEPIECE_FLAG_NOTHING = "0" ;
	
	//#CM725568
	/**
	 * Case/Piece division Case 
	 */
	public static final String CASEPIECE_FLAG_CASE = "1" ;
	
	//#CM725569
	/**
	 * Case/Piece division Piece 
	 */
	public static final String CASEPIECE_FLAG_PIECE = "2" ;

	//#CM725570
	/**
	 * Case/Piece division "Mixed" 
	 */
	public static final String CASEPIECE_FLAG_MIXED = "3" ;
	//#CM725571
	/**
	 * Case/Piece division: All 
	 */
	public static final String CASEPIECE_FLAG_ALL = "4" ;

	//#CM725572
	/**
	 * Work Status flag "Standby" 
	 */
	public static final String STATUS_FLAG_UNSTARTED = "0" ;
	//#CM725573
	/**
	 * Work Status flag "Started" 
	 */
	public static final String STATUS_FLAG_STARTED = "1" ;

	//#CM725574
	/**
	 * Work Status flag "Working" 
	 */
	public static final String STATUS_FLAG_WORKING = "2" ;

	//#CM725575
	/**
	 * Work Completed Status flag "Partially Completed" 
	 */
	public static final String STATUS_FLAG_PARTIAL_COMPLETION = "3" ;

	//#CM725576
	/**
	 * Work Completed Status flag "Completed" 
	 */
	public static final String STATUS_FLAG_COMPLETION = "4" ;

	//#CM725577
	/**
	 * Work Status flag "Deleted" 
	 */
	public static final String STATUS_FLAG_DELETE = "9" ;

	//#CM725578
	/**
	 * Work Status flag "All" 
	 */
	public static final String STATUS_FLAG_ALL = "99" ;

	//#CM725579
	/**
	 * Display order  In the order of Picking Date 
	 */
	public static final String DISPLAY_ORDER_WORK_DATE = "0" ;

	//#CM725580
	/**
	 * Display order  In the order of Planned Picking Date 
	 */
	public static final String DISPLAY_ORDER_PLAN_DATE = "1" ;

	//#CM725581
	/**
	 * Display order  In the order of Item Code 
	 */
	public static final String DISPLAY_ORDER_ITEM_CODE = "2" ;

	//#CM725582
	/**
	 * Display order  In the order of Relocation Source Location 
	 */
	public static final String DISPLAY_ORDER_SOURCE_LOCATION = "3" ;

	//#CM725583
	/**
	 * Aggregated display by: Item Code- Picking Location 
	 */
	public static final String AGGREGATEDISPLAY_ITEM_AND_LOCATION = "0" ;

	//#CM725584
	/**
	 * Aggregated display by: Planned Picking Date 
	 */
	public static final String AGGREGATEDISPLAY_PLAN_DATE = "1" ;

	//#CM725585
	/**
	 * Display of Quantity: Disabled to display 
	 */
	public static final String QTY_DISPLAY_OFF = "0" ;

	//#CM725586
	/**
	 * Display of Quantity: Enabled to display
	 */
	public static final String QTY_DISPLAY_ON = "1" ;

	//#CM725587
	/**
	 * "Search" flag (Plan Info) 
	 */
	public static final String SEARCHFLAG_PLAN = "1";

	//#CM725588
	/**
	 * "Search" flag (Result info) 
	 */
	public static final String SEARCHFLAG_RESULT = "2";

	//#CM725589
	/**
	 * "Search" flag (Work Status Info)
	 */
	public static final String SEARCHFLAG_WORKINFO = "3";

	//#CM725590
	/**
	 * Range flag (Start) 
	 */
	public static final String RANGE_START = "0";

	//#CM725591
	/**
	 * Range flag (End) 
	 */
	public static final String RANGE_END = "1";

	//#CM725592
	/**
	 * Button type (Modify/Add) 
	 */
	public static final String BUTTON_MODIFYSUBMIT = "0";

	//#CM725593
	/**
	 * Button type (Cancel All Working) 
	 */
	public static final String BUTTON_ALLWORKINGCLEAR = "1";

	//#CM725594
	/**
	 * Case/Piece flag for searching through listbox. (None) 
	 */
	public static final String LISTBOX_RETRIEVAL = "0";

	//#CM725595
	/**
	 * Case/Piece flag for searching through listbox. (Case) 
	 */
	public static final String LISTBOX_CASE = "1";

	//#CM725596
	/**
	 * Case/Piece flag for searching through listbox. (Piece) 
	 */
	public static final String LISTBOX_PIECE = "2";

	//#CM725597
	/**
	 * Item/Order (Item) 
	 */
	public static final String ITEMORDERFLAG_ITEM = "0";

	//#CM725598
	/**
	 * Item/Order (Order No.) 
	 */
	public static final String ITEMORDERFLAG_ORDER = "1";

	//#CM725599
	/**
	 * Item/Order (All) 
	 */
	public static final String ITEMORDERFLAG_ALL = "9";

	//#CM725600
	/**
	 * Modify Data flag(Not Modified)
	 */
	public static final String UPDATEFLAG_NO = "0";

	//#CM725601
	/**
	 * Modify Data flag (Modified) 
	 */
	public static final String UPDATEFLAG_YES = "1";

	//#CM725602
	/**
	 * Schedule process flag (Not Processed) 
	 */
	public static final String SCHEDULE_UNSTART = "0";

	//#CM725603
	/**
	 * Schedule flag (Case Processed) 
	 */
	public static final String SCHEDULE_CASE_COMPLETION = "1";

	//#CM725604
	/**
	 * Schedule flag (Piece Processed) 
	 */
	public static final String SCHEDULE_PIECE_COMPLETION = "2";

	//#CM725605
	/**
	 * Schedule flag (Processed) 
	 */
	public static final String SCHEDULE_COMPLETION = "3";

	//#CM725606
	/**
	 * System identification key  Flat Storage 
	 */
	public static final int SYSTEM_DISC_KEY_FLOOR = 0;
	
	//#CM725607
	/**
	 * System identification key  IDM 
	 */
	public static final int SYSTEM_DISC_KEY_IDM = 1;
	
	//#CM725608
	/**
	 * System identification key  ASRS 
	 */
	public static final int SYSTEM_DISC_KEY_ASRS = 2;
	
	//#CM725609
	/**
	 * Shortage Restoration division (Detailed Unit) 
	 */
	public static final int SHORTAGE_RECOVERY_DETAIL = 1 ;

	//#CM725610
	/**
	 * Shortage Restoration division (by Order or by Item) 
	 */
	public static final int SHORTAGE_RECOVERY_COLLECT = 2 ;

	//#CM725611
	/**
	 * Shortage Restoration division (Setting Unit) 
	 */
	public static final int SHORTAGE_RECOVERY_SETTING = 3 ;

	//#CM725612
	/**
	 * Allocation Process Division (Order-specified)
	 */
	public static final String SCHEDULE_PROCESS_ORDER = "1" ;

	//#CM725613
	/**
	 * Allocation Process division (Item-specified) 
	 */
	public static final String SCHEDULE_PROCESS_ITEM = "2" ;

	//#CM725614
	/**
	 * Area Type flag (exclude Automated Warehouse) 
	 */
	public static final String AREA_TYPE_FLAG_NOASRS = "1";

	//#CM725615
	/**
	 * Allocation flag (Not Allocated) 
	 */
	public static final String ALLOCATION_UNSTART = "0" ;

	//#CM725616
	/**
	 * Allocation flag (Allocated) 
	 */
	public static final String ALLOCATION_COMPLETION = "1";
	
	//#CM725617
	/**
	 * Screen name: Order PickingStart
	 */
	public static final String FUNCTION_NAME_ORDER_SCH = "FUNCTION_NAME_ORDER_SCH";

	//#CM725618
	/**
	 * Screen name: Start Item Picking 
	 */
	public static final String FUNCTION_NAME_ITEM_SCH = "FUNCTION_NAME_ITEM_SCH";

	//#CM725619
	// Instance variables -----------------------------------------------
	//#CM725620
	/**
	 * Worker Code
	 */
	private String wWorkerCode;

	//#CM725621
	/**
	 * Password 
	 */
	private String wPassword;

	//#CM725622
	/**
	 * Worker Name
	 */
	private String wWorkerName;

	//#CM725623
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM725624
	/**
	 * Consignor Name
	 */
	private String wConsignorName;

	//#CM725625
	/**
	 * Item Code
	 */
	private String wItemCode;

	//#CM725626
	/**
	 * Item Code (listcell) 
	 */
	private String wItemCodeL;

	//#CM725627
	/**
	 * Item Name
	 */
	private String wItemName;

	//#CM725628
	/**
	 * Picking Date
	 */
	private String wRetrievalDate;

	//#CM725629
	/**
	 * Start Picking Date
	 */
	private String wFromRetrievalDate;

	//#CM725630
	/**
	 * End Picking Date
	 */
	private String wToRetrievalDate;

	//#CM725631
	/**
	 * Start Item Code 
	 */
	private String wFromItemCode;

	//#CM725632
	/**
	 * End Item Code 
	 */
	private String wToItemCode;

	//#CM725633
	/**
	 * Case/Piece division
	 */
	private String wCasePieceflg;

	//#CM725634
	/**
	 * Case/Piece division name 
	 */
	private String wCasePieceflgName;

	//#CM725635
	/**
	 * Planned Picking Date
	 */
	private String wRetrievalPlanDate;
	
	//#CM725636
	/**
	 * Picking Location 
	 */
	private String wRetrievalLocation;
	
	//#CM725637
	/**
	 * Picking area 
	 */
	private String wRetrievalArea;
	
	//#CM725638
	/**
	 * Picking Area Name 
	 */
	private String wRetrievalAreaName;

	//#CM725639
	/**
	 * Expiry Date
	 */
	private String wUseByDate;

	//#CM725640
	/**
	 * Packed Qty per Case
	 */
	private int wEnteringQty;

	//#CM725641
	/**
	 * Packed qty per bundle
	 */
	private int wBundleEnteringQty;

	//#CM725642
	/**
	 * Planned total qty
	 */
	private int wTotalPlanQty;

	//#CM725643
	/**
	 * Planned Case Qty 
	 */
	private int wPlanCaseQty;

	//#CM725644
	/**
	 * Planned Piece Qty 
	 */
	private int wPlanPieceQty;

	//#CM725645
	/**
	 * Host plan Qty
	 */
	private int wHostPlanQty;

	//#CM725646
	/**
	 * Shortage Case Qty
	 */
	private int wShortageCaseQty;

	//#CM725647
	/**
	 * Shortage Piece Qty
	 */
	private int wShortagePieceQty;

	//#CM725648
	/**
	 * Case ITF
	 */
	private String wITF;

	//#CM725649
	/**
	 * Bundle ITF
	 */
	private String wBundleITF;

	//#CM725650
	/**
	 * Work status:
	 */
	private String wWorkStatus;

	//#CM725651
	/**
	 * Work status name 
	 */
	private String wWorkStatusName;

	//#CM725652
	/**
	 * Relocation Case Qty 
	 */
	private int wMovementCaseQty;

	//#CM725653
	/**
	 * Relocation Piece Qty 
	 */
	private int wMovementQty;

	//#CM725654
	/**
	 * Print Picking Instruction division 
	 */
	private boolean wRetrievalListFlg = false;

	//#CM725655
	/**
	 * Picking Result display type 
	 */
	private boolean wResultDisplayFlg = false;

	//#CM725656
	/**
	 * Cancel Remaining Work type 
	 */
	private boolean wWorkCancelFlg = false;

	//#CM725657
	/**
	 * "Cancel Relocation" division 
	 */
	private boolean wMoveCancelFlg = false;

	//#CM725658
	/**
	 * Start Location 
	 */
	private String wFromLocation;

	//#CM725659
	/**
	 * End Location 
	 */
	private String wToLocation;

	//#CM725660
	/**
	 * Inventory Check Case Qty 
	 */
	private int wInventoryCheckCaseQty;

	//#CM725661
	/**
	 * Stock Case Qty 
	 */
	private int wStockCaseQty;

	//#CM725662
	/**
	 * Stock Piece Qty 
	 */
	private int wStockQty;

	//#CM725663
	/**
	 * Start Planned Picking Date
	 */
	private String wFromRetrievalPlanDate ;

	//#CM725664
	/**
	 * End Planned Picking Date
	 */
	private String wToRetrievalPlanDate  ;

	//#CM725665
	/**
	 * Display order 
	 */
	private String wDisplayOrder ;

	//#CM725666
	/**
	 * Aggregate the display.
	 */
	private String wAggregateDisplay ;

	//#CM725667
	/**
	 * Print type of Deleted Picking Plan list report
	 */
	private boolean wDeleteRetrievalListFlag ;

	//#CM725668
	/**
	 * Relocation status name 
	 */
	private String wMoveStatusName ;

	//#CM725669
	/**
	 * Array of work status 
	 */
	private String[] wSearchStatus;
	
	//#CM725670
	/**
	 * Result Case Qty 
	 */
	private int wResultCaseQty ;

	//#CM725671
	/**
	 * Result Piece Qty 
	 */
	private int wResultPieceQty ;

	//#CM725672
	/**
	 * Work date
	 */
	private String wWorkDate ;

	//#CM725673
	/**
	 * Start work date 
	 */
	private String wFromWorkDate ;

	//#CM725674
	/**
	 * End Work Date 
	 */
	private String wToWorkDate ;
	
	//#CM725675
	/**
	 * Case Location
	 */
	private String wCaseLocation ;

	//#CM725676
	/**
	 * Piece Location 
	 */
	private String wPieceLocation ;

	//#CM725677
	/**
	 * Customer Code
	 */
	private String wCustomerCode;

	//#CM725678
	/**
	 * Customer Name
	 */
	private String wCustomerName;

	//#CM725679
	/**
	 * Ticket No. 
	 */
	private String wShippingTicketNo;

	//#CM725680
	/**
	 * Line No. 
	 */
	private int wShippingLineNo;

	//#CM725681
	/**
	 * Order No.
	 */
	private String wOrderNo;

	//#CM725682
	/**
	 * Work No.
	 */
	private String wJobNo;

	//#CM725683
	/**
	 * Last update date/time
	 */
	private java.util.Date wLastUpdateDate;

	//#CM725684
	/**
	 * Preset line No. 
	 */
	private int wRowNo;

	//#CM725685
	/**
	 * Case Order No.
	 */
	private String wCaseOrderNo ;

	//#CM725686
	/**
	 * Piece Order No.
	 */
	private String wPieceOrderNo ;

	//#CM725687
	/**
	 * Case Order No. (for display) 
	 */
	private String wCaseOrderNoDisp ;

	//#CM725688
	/**
	 * Piece Order No. (for display) 
	 */
	private String wPieceOrderNoDisp ;

	//#CM725689
	/**
	 * Case Location (for display) 
	 */
	private String wCaseLocationDisp ;

	//#CM725690
	/**
	 * Piece Location (for display) 
	 */
	private String wPieceLocationDisp ;

	//#CM725691
	/**
	 * Customer Code (for display) 
	 */
	private String wCustomerCodeDisp ;

	//#CM725692
	/**
	 * Picking Plan unique key
	 */
	private String wRetrievalPlanUkey;

	//#CM725693
	/**
	 * Batch No. (Schedule No.) 
	 */
	private String wBatchNo;

	//#CM725694
	/**
	 * Entry type 
	 */
	private String wRegistKind;

	//#CM725695
	/**
	 * Work No. (vector) 
	 */
	private Vector wJobNoList;

	//#CM725696
	/**
	 * Last update date/time (vector) 
	 */
	private Vector wLastUpdateDateList;

	//#CM725697
	/**
	 * Planned Picking Date (Pulldown) 
	 */
	private String[] wRetrievalPlanDateArray ;

	//#CM725698
	/**
	 * Work status (listcell) 
	 */
	private String wStatusFlagL;

	//#CM725699
	/**
	 * Button type 
	 */
	private String wButtonType;

	//#CM725700
	/**
	 * Item/Order 
	 */
	private String wItemOrderFlag;

	//#CM725701
	/**
	 * Data count
	 */
	private int wDataCount;

	//#CM725702
	/**
	 * Total Picking qty 
	 */
	private int wTotalRetrievalQty ;

	//#CM725703
	/**
	 * Picking Case Qty 
	 */
	private int wRetrievalCaseQty ;

	//#CM725704
	/**
	 * Picking Piece Qty 
	 */
	private int wRetrievalPieceQty ;

	//#CM725705
	/**
	 * Quantity display type 
	 */
	private String wQtyDisplayKind;
	
	//#CM725706
	/**
	 * Result Report Division 
	 */
	private String wReportFlag;

	//#CM725707
	/**
	 * Result Report Division Name 
	 */
	private String wReportFlagName;

	//#CM725708
	/**
	 * Start Order No. 
	 */
	private String wStartOrderNo;

	//#CM725709
	/**
	 * End Order No. 
	 */
	private String wEndOrderNo;

	//#CM725710
	/**
	 * Start Item Code 
	 */
	private String wStartItemCode;

	//#CM725711
	/**
	 * End Item Code 
	 */
	private String wEndItemCode;

	//#CM725712
	/**
	 * Allocation target, storage place 
	 */
	private String[] wAllocateField;

	//#CM725713
	/**
	 * Shortage evidence 
	 */
	private boolean wShortageFlag;

	//#CM725714
	/**
	 * Request for printing Shortage list 
	 */
	private String wShortageListRequestFlag;

	//#CM725715
	/**
	 * Schedule flag 
	 */
	private String wScheduleFlag;

	//#CM725716
	/**
	 * Array of the schedule flag 
	 */
	private String[] wScheduleFlagArray;

	//#CM725717
	/**
	 * Plan unique key (vector) 
	 */
	private Vector wPlanUkeyList;

	//#CM725718
	/**
	 * System identification key 
	 */
	private int wSystemDiscKey;
	
	//#CM725719
	/**
	 * System identification key (Array) 
	 */
	private int[] wSystemDiscKeyArray;
	
	//#CM725720
	/**
	 * System identification key name 
	 */
	private String wSystemDiscKeyName;

	//#CM725721
	/**
	 * Area Type flag 
	 */
	private String wAreaTypeFlag;
	
	//#CM725722
	/**
	 * Host Aggregated Batch No. 
	 */
	private String wHostCollectBatchNo;
	
	//#CM725723
	/**
	 * Host Aggregated Batch No. (listcell) 
	 */
	private String wHostCollectBatchNoL;
	
	//#CM725724
	/**
	 * Case/Piece division (listcell) 
	 */
	private String wCasePieceFlagL;
	
	//#CM725725
	/**
	 * Order No. (listcell) 
	 */
	private String wOrderNoL;
	
	//#CM725726
	/**
	 * Allocation flag 
	 */
	private String wAllocationFlag;

	//#CM725727
	/**
	 * Flag to enable or disable to check length. 
	 */
	private boolean wLineCheckFlag = true;

	//#CM725728
	// Class method --------------------------------------------------
	//#CM725729
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:20:04 $");
	}

	//#CM725730
	// Constructors --------------------------------------------------
	//#CM725731
	/**
	 * Initialize this class. 
	 */
	public RetrievalSupportParameter()
	{

	}

	//#CM725732
	// Public methods ------------------------------------------------
	//#CM725733
	/**
	 * Set the Worker code. 
	 * @param arg Worker code to be set 
	 */
	public void setWorkerCode(String arg)
	{
		wWorkerCode = arg ;
	}

	//#CM725734
	/**
	 * Obtain the Worker code. 
	 * @return Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode ;
	}

	//#CM725735
	/**
	 * Set the password. 
	 * @param arg Password to be set
	 */
	public void setPassword(String arg)
	{
		wPassword = arg ;
	}

	//#CM725736
	/**
	 * Obtain the password. 
	 * @return Password 
	 */
	public String getPassword()
	{
		return wPassword ;
	}

	//#CM725737
	/**
	 * Set the Worker name. 
	 * @param arg Worker Name to be set
	 */
	public void setWorkerName(String arg)
	{
		wWorkerName = arg ;
	}

	//#CM725738
	/**
	 * Obtain the Worker name. 
	 * @return Worker Name
	 */
	public String getWorkerName()
	{
		return wWorkerName ;
	}

	//#CM725739
	/**
	 * Set the Consignor code.
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg ;
	}

	//#CM725740
	/**
	 * Obtain the Consignor code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode ;
	}

	//#CM725741
	/**
	 * Set the Consignor name. 
	 * @param arg Consignor Name to be set
	 */
	public void setConsignorName(String arg)
	{
		wConsignorName = arg ;
	}

	//#CM725742
	/**
	 * Obtain the Consignor Name. 
	 * @return Consignor Name
	 */
	public String getConsignorName()
	{
		return wConsignorName ;
	}

	//#CM725743
	/**
	 * Set the item code. 
	 * @param arg Item Code to be set
	 */
	public void setItemCode(String arg)
	{
		wItemCode = arg ;
	}

	//#CM725744
	/**
	 * Obtain the item code.
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return wItemCode ;
	}

	//#CM725745
	/**
	 * Set the item code (listcell). 
	 * @param arg Item Code to be set (listcell) 
	 */
	public void setItemCodeL(String arg)
	{
		wItemCodeL = arg ;
	}

	//#CM725746
	/**
	 * Obtain the item code (listcell). 
	 * @return Item Code (listcell) 
	 */
	public String getItemCodeL()
	{
		return wItemCodeL ;
	}

	//#CM725747
	/**
	 * Set the item name. 
	 * @param arg Item Name to be set
	 */
	public void setItemName(String arg)
	{
		wItemName = arg ;
	}

	//#CM725748
	/**
	 * Obtain the item name. 
	 * @return Item Name
	 */
	public String getItemName()
	{
		return wItemName ;
	}

	//#CM725749
	/**
	 * Set the Picking Date. 
	 * @param arg Picking Date to be set
	 */
	public void setRetrievalDate(String arg)
	{
		wRetrievalDate = arg ;
	}

	//#CM725750
	/**
	 * Obtain the Picking Date. 
	 * @return Picking Date
	 */
	public String getRetrievalDate()
	{
		return wRetrievalDate;
	}

	//#CM725751
	/**
	 * Set the Start Picking Date. 
	 * @param arg Start Picking Date to be set
	 */
	public void setFromRetrievalDate(String arg)
	{
		wFromRetrievalDate = arg ;
	}

	//#CM725752
	/**
	 * Obtain the Start Picking Date.
	 * @return Start Picking Date
	 */
	public String getFromRetrievalDate()
	{
		return wFromRetrievalDate;
	}

	//#CM725753
	/**
	 * Set the End Picking Date. 
	 * @param arg End Picking Date to be set
	 */
	public void setToRetrievalDate(String arg)
	{
		wToRetrievalDate = arg ;
	}

	//#CM725754
	/**
	 * Obtain the End Picking Date.
	 * @return End Picking Date
	 */
	public String getToRetrievalDate()
	{
		return wToRetrievalDate;
	}

	//#CM725755
	/**
	 * Set the Start item code. 
	 * @param arg Start Item Code to be set
	 */
	public void setFromItemCode(String arg)
	{
		wFromItemCode = arg ;
	}

	//#CM725756
	/**
	 * Obtain the Start item code. 
	 * @return Start Item Code 
	 */
	public String getFromItemCode()
	{
		return wFromItemCode;
	}

	//#CM725757
	/**
	 * Set the End item code. 
	 * @param arg End Item Code to be set
	 */
	public void setToItemCode(String arg)
	{
		wToItemCode = arg ;
	}

	//#CM725758
	/**
	 * Obtain the End item code. 
	 * @return End Item Code 
	 */
	public String getToItemCode()
	{
		return wToItemCode;
	}

	//#CM725759
	/**
	 * Set the Case/Piece division. 
	 * @param arg Case/Piece division to be set
	 */
	public void setCasePieceflg(String arg)
	{
		wCasePieceflg = arg ;
	}

	//#CM725760
	/**
	 * Obtain the Case/Piece division. 
	 * @return Case/Piece division
	 */
	public String getCasePieceflg()
	{
		return wCasePieceflg;
	}

	//#CM725761
	/**
	 * Set the Case/Piece division name. 
	 * @param arg Case/Piece division name to be set
	 */
	public void setCasePieceflgName(String arg)
	{
		wCasePieceflgName = arg ;
	}

	//#CM725762
	/**
	 * Obtain the Case/Piece division name. 
	 * @return  Case/Piece division name 
	 */
	public String getCasePieceflgName()
	{
		return wCasePieceflgName;
	}
	
	//#CM725763
	/**
	 * Set the Planned Picking date.
	 * @param arg Planned Picking Date to be set
	 */
	public void setRetrievalPlanDate(String arg)
	{
		wRetrievalPlanDate = arg ;
	}

	//#CM725764
	/**
	 * Obtain the Planned Picking Date.
	 * @return Planned Picking Date
	 */
	public String getRetrievalPlanDate()
	{
		return wRetrievalPlanDate;
	}

	//#CM725765
	/**
	 * Set the picking area. 
	 * @param arg Picking area to be set
	 */
	public void setRetrievalArea(String arg)
	{
		wRetrievalArea = arg ;
	}

	//#CM725766
	/**
	 * Obtain the picking area. 
	 * @return Picking area 
	 */
	public String getRetrievalArea()
	{
		return wRetrievalArea;
	}

	//#CM725767
	/**
	 * Set the Picking area name. 
	 * @param arg Picking Area Name to be set
	 */
	public void setRetrievalAreaName(String arg)
	{
		wRetrievalAreaName = arg ;
	}

	//#CM725768
	/**
	 * Obtain the Picking area name. 
	 * @return Picking Area Name 
	 */
	public String getRetrievalAreaName()
	{
		return wRetrievalAreaName;
	}
	//#CM725769
	/**
	 * Set the Picking Location. 
	 * @param arg Picking Location to be set
	 */
	public void setRetrievalLocation(String arg)
	{
		wRetrievalLocation = arg ;
	}

	//#CM725770
	/**
	 * Obtain the Picking Location. 
	 * @return Picking Location 
	 */
	public String getRetrievalLocation()
	{
		return wRetrievalLocation;
	}

	//#CM725771
	/**
	 * Set the expiry date. 
	 * @param arg Expiry Date to be set
	 */
	public void setUseByDate(String arg)
	{
		wUseByDate = arg ;
	}

	//#CM725772
	/**
	 * Obtain the expiry date. 
	 * @return Expiry Date
	 */
	public String getUseByDate()
	{
		return wUseByDate;
	}

	//#CM725773
	/**
	 * Set the Packed qty per case. 
	 * @param arg Packed Qty per Case to be set
	 */
	public void setEnteringQty(int arg)
	{
		wEnteringQty = arg ;
	}

	//#CM725774
	/**
	 * Obtain the Packed qty per case. 
	 * @return Packed Qty per Case
	 */
	public int getEnteringQty()
	{
		return wEnteringQty;
	}

	//#CM725775
	/**
	 * Set the Packed qty per bundle. 
	 * @param arg Packed qty per bundle to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		wBundleEnteringQty = arg ;
	}

	//#CM725776
	/**
	 * Obtain the Packed qty per bundle. 
	 * @return Packed qty per bundle
	 */
	public int getBundleEnteringQty()
	{
		return wBundleEnteringQty;
	}

	//#CM725777
	/**
	 * Set the total planned qty. 
	 * @param arg Planned total qty to be set
	 */
	public void setTotalPlanQty(int arg)
	{
		wTotalPlanQty = arg ;
	}

	//#CM725778
	/**
	 * Obtain the total planned qty. 
	 * @return Planned total qty
	 */
	public int getTotalPlanQty()
	{
		return wTotalPlanQty;
	}

	//#CM725779
	/**
	 * Set the planned case qty. 
	 * @param arg Planned Case Qty to be set
	 */
	public void setPlanCaseQty(int arg)
	{
		wPlanCaseQty = arg ;
	}

	//#CM725780
	/**
	 * Obtain the planned case qty. 
	 * @return Planned Case Qty 
	 */
	public int getPlanCaseQty()
	{
		return wPlanCaseQty;
	}

	//#CM725781
	/**
	 * Set the planned piece qty. 
	 * @param arg Planned Piece Qty to be set
	 */
	public void setPlanPieceQty(int arg)
	{
		wPlanPieceQty = arg ;
	}

	//#CM725782
	/**
	 * Obtain the planned piece qty. 
	 * @return Planned Piece Qty 
	 */
	public int getPlanPieceQty()
	{
		return wPlanPieceQty;
	}

	//#CM725783
	/**
	 * Set the shortage case qty. 
	 * @param arg Shortage Case Qty to be set
	 */
	public void setShortageCaseQty(int arg)
	{
		wShortageCaseQty = arg ;
	}

	//#CM725784
	/**
	 * Obtain the shortage case qty. 
	 * @return Shortage Case Qty
	 */
	public int getShortageCaseQty()
	{
		return wShortageCaseQty;
	}

	//#CM725785
	/**
	 * Set the shortage piece qty. 
	 * @param arg Shortage Piece Qty to be set
	 */
	public void setShortagePieceQty(int arg)
	{
		wShortagePieceQty = arg ;
	}

	//#CM725786
	/**
	 * Obtain the shortage piece qty. 
	 * @return Shortage Piece Qty
	 */
	public int getShortagePieceQty()
	{
		return wShortagePieceQty;
	}

	//#CM725787
	/**
	 * Set the Case ITF. 
	 * @param arg Case ITF to be set
	 */
	public void setITF(String arg)
	{
		wITF = arg ;
	}

	//#CM725788
	/**
	 * Obtain the Case ITF. 
	 * @return Case ITF
	 */
	public String getITF()
	{
		return wITF;
	}

	//#CM725789
	/**
	 * Set the bundle ITF. 
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleITF(String arg)
	{
		wBundleITF = arg ;
	}

	//#CM725790
	/**
	 * Obtain the bundle ITF. 
	 * @return Bundle ITF
	 */
	public String getBundleITF()
	{
		return wBundleITF;
	}

	//#CM725791
	/**
	 * Set the relocation case qty. 
	 * @param arg Relocation Case Qty to be set
	 */
	public void setMovementCaseQty(int arg)
	{
		wMovementCaseQty = arg ;
	}

	//#CM725792
	/**
	 * Obtain the relocation case qty. 
	 * @return Relocation Case Qty 
	 */
	public int getMovementCaseQty()
	{
		return wMovementCaseQty;
	}

	//#CM725793
	/**
	 * Set the relocation piece qty. 
	 * @param arg Relocation Piece Qty to be set
	 */
	public void setMovementQty(int arg)
	{
		wMovementQty = arg ;
	}

	//#CM725794
	/**
	 * Obtain the relocation piece qty. 
	 * @return Relocation Piece Qty 
	 */
	public int getMovementQty()
	{
		return wMovementQty;
	}

	//#CM725795
	/**
	 * Set the Print Picking Instruction division. 
	 * @param arg Print Picking Instruction division to be set
	 */
	public void setRetrievalListFlg(boolean arg)
	{
		wRetrievalListFlg = arg ;
	}

	//#CM725796
	/**
	 * Obtain the Print Picking Instruction division. 
	 * @return Print Picking Instruction division 
	 */
	public boolean getRetrievalListFlg()
	{
		return wRetrievalListFlg;
	}

	//#CM725797
	/**
	 * Set the display type of Picking Result. 
	 * @param arg Picking Result Display type to be set
	 */
	public void setResultDisplayFlg(boolean arg)
	{
		wResultDisplayFlg = arg ;
	}

	//#CM725798
	/**
	 * Obtain the Picking Result display type. 
	 * @return Picking Result display type 
	 */
	public boolean getResultDisplayFlg()
	{
		return wResultDisplayFlg;
	}

	//#CM725799
	/**
	 * Set the Cancel Remaining Work type. 
	 * @param arg Remaining Work Cancel division to be set
	 */
	public void setWorkCancelFlg(boolean arg)
	{
		wWorkCancelFlg = arg ;
	}

	//#CM725800
	/**
	 * Obtain the Cancel Remaining Work division. 
	 * @return Cancel Remaining Work division 
	 */
	public boolean getWorkCancelFlg()
	{
		return wWorkCancelFlg;
	}


	//#CM725801
	/**
	 * Set the Cancel Relocation division. 
	 * @param arg Relocation Cancel division to be set
	 */
	public void getMoveCancelFlg(boolean arg)
	{
		wMoveCancelFlg = arg ;
	}

	//#CM725802
	/**
	 * Obtain the Cancel Relocation division. 
	 * @return "Cancel Relocation" division 
	 */
	public boolean getMoveCancelFlg()
	{
		return wMoveCancelFlg;
	}

	//#CM725803
	/**
	 * Set the Start Location. 
	 * @param arg Start Location to be set
	 */
	public void setFromLocation(String arg)
	{
		wFromLocation = arg ;
	}

	//#CM725804
	/**
	 * Obtain the Start Location. 
	 * @return Start Location to be set
	 */
	public String getFromLocation()
	{
		return wFromLocation;
	}

	//#CM725805
	/**
	 * Set the End Location. 
	 * @param arg End Location to be set
	 */
	public void setToLocation(String arg)
	{
		wToLocation = arg ;
	}

	//#CM725806
	/**
	 * Obtain the End Location. 
	 * @return End Location to be set
	 */
	public String getToLocation()
	{
		return wToLocation;
	}

	//#CM725807
	/**
	 * Set the inventory piece qty. 
	 * @param arg Inventory Check Piece Qty to be set
	 */
	public void setInventoryCheckCaseQty(int arg)
	{
		wInventoryCheckCaseQty = arg ;
	}

	//#CM725808
	/**
	 * Obtain the inventory piece qty. 
	 * @return Inventory Check Piece Qty 
	 */
	public int getInventoryCheckCaseQty()
	{
		return wInventoryCheckCaseQty;
	}

	//#CM725809
	/**
	 * Set the Stock Case Qty. 
	 * @param arg Stock Case Qty to be set
	 */
	public void setStockCaseQty (int arg)
	{
		wStockCaseQty = arg ;
	}

	//#CM725810
	/**
	 * Obtain the Stock Case Qty. 
	 * @return Stock Case Qty 
	 */
	public int getStockCaseQty()
	{
		return wStockCaseQty;
	}


	//#CM725811
	/**
	 * Set the stock piece qty. 
	 * @param arg Piece Inventory Qty to be set
	 */
	public void setStockQty(int arg)
	{
		wStockQty = arg ;
	}

	//#CM725812
	/**
	 * Obtain the stock piece qty. 
	 * @return Stock Piece Qty 
	 */
	public int getStockQty()
	{
		return wStockQty;
	}

	//#CM725813
	/**
	 * Set the Start Planned Picking Date. 
	 * @param fromRetrievalPlanDate Start Planned Picking Date to be set
	 */
	public void setFromRetrievalPlanDate(String fromRetrievalPlanDate)
	{
		wFromRetrievalPlanDate = fromRetrievalPlanDate ;
	}

	//#CM725814
	/**
	 * Obtain the Start Planned Picking Date. 
	 * @return Start Planned Picking Date
	 */
	public String getFromRetrievalPlanDate()
	{
		return wFromRetrievalPlanDate ;
	}

	//#CM725815
	/**
	 * Set the End Planned Picking date.
	 * @param toRetrievalPlanDate End Planned Picking Date to be set
	 */
	public void setToRetrievalPlanDate(String toRetrievalPlanDate)
	{
		wToRetrievalPlanDate = toRetrievalPlanDate ;
	}

	//#CM725816
	/**
	 * Obtain the End Planned Picking Date.
	 * @return End Planned Picking Date
	 */
	public String getToRetrievalPlanDate()
	{
		return wToRetrievalPlanDate ;
	}
	
	//#CM725817
	/**
	 * Set the aggregated display 
	 * @param aggregateDisplay Aggregated display to be set
	 */
	public void setAggregateDisplay(String aggregateDisplay)
	{
		wAggregateDisplay = aggregateDisplay;
	}
	
	//#CM725818
	/**
	 * Obtain the Aggregated display.
	 * @return Aggregate the display.
	 */
	public String getAggregateDisplay()
	{
		return wAggregateDisplay;
	}

	//#CM725819
	/**
	 * Set the Print type of Deleted Picking Plan list report.
	 * @param deleteRetrievalListFlag Print type of Deleted Picking Plan list report to be set
	 */
	public void setDeleteRetrievalListFlag(boolean deleteRetrievalListFlag)
	{
		wDeleteRetrievalListFlag = deleteRetrievalListFlag;
	}
	
	//#CM725820
	/**
	 * Obtain the Print type of Deleted Picking Plan list report.
	 * @return Print type of Deleted Picking Plan list report
	 */
	public boolean getDeleteRetrievalListFlag()
	{
		return wDeleteRetrievalListFlag;
	}

	//#CM725821
	/**
	 * Set the display order. 
	 * @param displayOrder Display order to be set
	 */
	public void setDisplayOrder(String displayOrder)
	{
		wDisplayOrder = displayOrder;
	}
	
	//#CM725822
	/**
	 * Obtain the display order.
	 * @return Display order 
	 */
	public String getDisplayOrder()
	{
		return wDisplayOrder;
	}

	//#CM725823
	/**
	 * Set the Relocation status name. 
	 * @param moveStatusName Relocation status name to be set
	 */
	public void setMoveStatusName(String moveStatusName)
	{
		wMoveStatusName = moveStatusName;
	}
	
	//#CM725824
	/**
	 * Obtain the Relocation status name. 
	 * @return Relocation status name 
	 */
	public String getMoveStatusName()
	{
		return wMoveStatusName;
	}

	//#CM725825
	/**
	 * @return Returns the wHostPlanQty.
	 */
	public int getHostPlanQty()
	{
		return wHostPlanQty;
	}
	//#CM725826
	/**
	 * @param hostPlanQty The wHostPlanQty to set.
	 */
	public void setHostPlanQty(int hostPlanQty)
	{
		wHostPlanQty = hostPlanQty;
	}

	//#CM725827
	/**
	 * Set the work status.
	 * @param arg Work Status to be set
	 */
	public void setSearchStatus(String[] arg)
	{
		wSearchStatus = arg;
	}

	//#CM725828
	/**
	 * Obtain the work status.
	 * @return Work status:
	 */
	public String[] getSearchStatus()
	{
		return wSearchStatus;
	}

	//#CM725829
	/**
	 * Set the result case qty. 
	 * @param resultCaseQty Result Case Qty to be set
	 */
	public void setResultCaseQty(int resultCaseQty)
	{
		wResultCaseQty = resultCaseQty ;
	}

	//#CM725830
	/**
	 * Obtain the result case qty. 
	 * @return Result Case Qty 
	 */
	public int getResultCaseQty()
	{
		return wResultCaseQty ;
	}


	//#CM725831
	/**
	 * Set the result piece qty. 
	 * @param resultPieceQty Result Piece Qty 
	 */
	public void setResultPieceQty(int resultPieceQty)
	{
		wResultPieceQty = resultPieceQty ;
	}

	//#CM725832
	/**
	 * Obtain the result piece qty. 
	 * @return Result Piece Qty 
	 */
	public int getResultPieceQty()
	{
		return wResultPieceQty ;
	}

	//#CM725833
	/**
	 * Set the Work Date. 
	 * @param workDate Work date to be set
	 */
	public void setWorkDate(String workDate)
	{
		wWorkDate = workDate ;
	}

	//#CM725834
	/**
	 * Obtain the Work Date. 
	 * @return Work date
	 */
	public String getWorkDate()
	{
		return wWorkDate ;
	}

	//#CM725835
	/**
	 * Set the Start work date. 
	 * @param fromWorkDate Start work date to be set 
	 */
	public void setFromWorkDate(String fromWorkDate)
	{
		wFromWorkDate = fromWorkDate ;
	}

	//#CM725836
	/**
	 * Obtain the start work date. 
	 * @return Start work date 
	 */
	public String getFromWorkDate()
	{
		return wFromWorkDate ;
	}
	//#CM725837
	/**
	 * Set the End work date. 
	 * @param toWorkDate End work date to be set 
	 */
	public void setToWorkDate(String toWorkDate)
	{
		wToWorkDate = toWorkDate ;
	}

	//#CM725838
	/**
	 * Obtain the End work date. 
	 * @return End work date to be set 
	 */
	public String getToWorkDate()
	{
		return wToWorkDate ;
	}

	//#CM725839
	/**
	 * Set the work status.
	 * @param workStatus Work Status to be set
	 */
	public void setWorkStatus(String workStatus)
	{
		wWorkStatus = workStatus ;
	}

	//#CM725840
	/**
	 * Obtain the work status.
	 * @return Work status:
	 */
	public String getWorkStatus()
	{
		return wWorkStatus ;
	}

	//#CM725841
	/**
	 * Set the work status name. 
	 * @param workStatusName Work status name to be set
	 */
	public void setWorkStatusName(String workStatusName)
	{
		wWorkStatusName = workStatusName ;
	}

	//#CM725842
	/**
	 * Obtain the Work status name.
	 * @return Work status name 
	 */
	public String getWorkStatusName()
	{
		return wWorkStatusName ;
	}
	
	//#CM725843
	/**
	 * Set the Case Location. 
	 * @param caseLocation Case Location to be set
	 */
	public void setCaseLocation(String caseLocation)
	{
		wCaseLocation = caseLocation ;
	}

	//#CM725844
	/**
	 * Obtain the Case Location. 
	 * @return Case Location
	 */
	public String getCaseLocation()
	{
		return wCaseLocation ;
	}

	//#CM725845
	/**
	 * Set the Piece Location. 
	 * @param pieceLocation Piece Location to be set
	 */
	public void setPieceLocation(String pieceLocation)
	{
		wPieceLocation = pieceLocation ;
	}

	//#CM725846
	/**
	 * Obtain the Piece Location. 
	 * @return Piece Location 
	 */
	public String getPieceLocation()
	{
		return wPieceLocation ;
	}

	//#CM725847
	/**
	 * Set the customer code.
	 * @param arg Customer Code to be set
	 */
	public void setCustomerCode(String arg)
	{
		wCustomerCode = arg;
	}

	//#CM725848
	/**
	 * Obtain the customer code.
	 * @return Customer Code
	 */
	public String getCustomerCode()
	{
		return wCustomerCode;
	}

	//#CM725849
	/**
	 * Set the customer name. 
	 * @param arg Customer Name to be set
	 */
	public void setCustomerName(String arg)
	{
		wCustomerName = arg;
	}

	//#CM725850
	/**
	 * Obtain the customer name. 
	 * @return Customer Name
	 */
	public String getCustomerName()
	{
		return wCustomerName;
	}

	//#CM725851
	/**
	 * Set the Ticket No. 
	 * @param arg Ticket No. to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		wShippingTicketNo = arg;
	}

	//#CM725852
	/**
	 * Obtain the Ticket No. 
	 * @return Ticket No. 
	 */
	public String getShippingTicketNo()
	{
		return wShippingTicketNo;
	}

	//#CM725853
	/**
	 * Set the Line No. 
	 * @param arg Line No. to be set
	 */
	public void setShippingLineNo(int arg)
	{
		wShippingLineNo = arg;
	}

	//#CM725854
	/**
	 * Obtain the Line No. 
	 * @return Line No. 
	 */
	public int getShippingLineNo()
	{
		return wShippingLineNo;
	}

	//#CM725855
	/**
	 * Set the Order No.l
	 * @param arg Order No. to be set
	 */
	public void setOrderNo(String arg)
	{
		wOrderNo = arg;
	}

	//#CM725856
	/**
	 * Obtain the sorting order.
	 * @return Order No.
	 */
	public String getOrderNo()
	{
		return wOrderNo;
	}

	//#CM725857
	/**
	 * Set the work No. 
	 * @param arg Work No. to be set
	 */
	public void setJobNo(String arg)
	{
		wJobNo = arg;
	}

	//#CM725858
	/**
	 * Obtain the Work No.
	 * @return Work No.
	 */
	public String getJobNo()
	{
		return wJobNo;
	}

	//#CM725859
	/**
	 * Set the last update date/time. 
	 * @param arg Last update date/time to be set
	 */
	public void setLastUpdateDate(java.util.Date arg)
	{
		wLastUpdateDate = arg;
	}

	//#CM725860
	/**
	 * Obtain the Last update date/time. 
	 * @return Last update date/time
	 */
	public java.util.Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	//#CM725861
	/**
	 * Set the Total Picking qty. 
	 * @param arg Total Picking qty to be set
	 */
	public void setTotalRetrievalQty(int arg)
	{
		wTotalRetrievalQty = arg ;
	}

	//#CM725862
	/**
	 * Obtain the Total Picking qty. 
	 * @return Total Picking qty 
	 */
	public int getTotalRetrievalQty()
	{
		return wTotalRetrievalQty;
	}

	//#CM725863
	/**
	 * Set the preset line No. 
	 * @param arg Preset line No. to be set
	 */
	public void setRowNo(int arg)
	{
		wRowNo = arg;
	}

	//#CM725864
	/**
	 * Obtain the preset line No. 
	 * @return Preset line No. 
	 */
	public int getRowNo()
	{
		return wRowNo;
	}

    //#CM725865
    /**
     * Set the Case Order No.
     * @param caseOrderNo Case Order No. to be set
     */
	public void setCaseOrderNo(String caseOrderNo)
	{
	    wCaseOrderNo = caseOrderNo ;
	}
	//#CM725866
	/**
	 * Obtain the Case Order No. 
	 * @return Case Order No.
	 */
    public String getCaseOrderNo()
    {
        return wCaseOrderNo ;
    }

    //#CM725867
    /**
     * Set the Piece Order No. 
     * @param pieceOrderNo Piece Order No. to be set
     */
    public void setPieceOrderNo(String pieceOrderNo)
    {
        wPieceOrderNo = pieceOrderNo ;
    }

    //#CM725868
    /**
     * Obtain the Piece Order No. 
     * @return Piece Order No.
     */
    public String getPieceOrderNo()
    {
        return wPieceOrderNo ;
    }

	//#CM725869
	/**
	 * Set the Case Order No. (for display). 
	 * @param caseOrderNo Case Order No. to be set (for display) 
	 */
	public void setCaseOrderNoDisp(String caseOrderNo)
	{
		wCaseOrderNoDisp = caseOrderNo;
	}

	//#CM725870
	/**
	 * Obtain the Case Order No. (for display). 
	 * @return Case Order No. (for display) 
	 */
	public String getCaseOrderNoDisp()
	{
		return wCaseOrderNoDisp;
	}

	//#CM725871
	/**
	 * Set the Piece Order No. (for display). 
	 * @param pieceOrderNo Piece Order No. (for display) to be set
	 */
	public void setPieceOrderNoDisp(String pieceOrderNo)
	{
		wPieceOrderNoDisp = pieceOrderNo;
	}

	//#CM725872
	/**
	 * Obtain the Piece Order No. (for display). 
	 * @return Piece Order No. (for display) 
	 */
	public String getPieceOrderNoDisp()
	{
		return wPieceOrderNoDisp;
	}

	//#CM725873
	/**
	 * Set the Case Location (for display). 
	 * @param caseLocation Case Location to be set (for display) 
	 */
	public void setCaseLocationDisp(String caseLocation)
	{
		wCaseLocationDisp = caseLocation;
	}

	//#CM725874
	/**
	 * Obtain the Case Location (for display). 
	 * @return Case Location (for display) 
	 */
	public String getCaseLocationDisp()
	{
		return wCaseLocationDisp;
	}

	//#CM725875
	/**
	 * Set the Piece Location (for display). 
	 * @param pieceLocation Piece Location (for display) to be set
	 */
	public void setPieceLocationDisp(String pieceLocation)
	{
		wPieceLocationDisp = pieceLocation;
	}

	//#CM725876
	/**
	 * Obtain the Piece Location (for display). 
	 * @return Piece Location (for display) 
	 */
	public String getPieceLocationDisp()
	{
		return wPieceLocationDisp;
	}

	//#CM725877
	/**
	 * Set the Customer Code (for display). 
	 * @param customerCode Customer Code (for display) to be set
	 */
	public void setCustomerCodeDisp(String customerCode)
	{
		wCustomerCodeDisp = customerCode;
	}

	//#CM725878
	/**
	 * Obtain the Customer Code (for display). 
	 * @return Customer Code (for display) 
	 */
	public String getCustomerCodeDisp()
	{
		return wCustomerCodeDisp;
	}

	//#CM725879
	/**
	 * Set the Picking Plan unique key.
	 * @param arg Picking Plan unique key to be set
	 */
	public void setRetrievalPlanUkey(String arg)
	{
		wRetrievalPlanUkey = arg;
	}

	//#CM725880
	/**
	 * Obtain the Picking Plan unique key.
	 * @return Picking Plan unique key
	 */
	public String getRetrievalPlanUkey()
	{
		return wRetrievalPlanUkey;
	}

	//#CM725881
	/**
	 * Set the Batch No. (Schedule No.). 
	 * @param arg Batch No. (Schedule No.) to be set
	 */
	public void setBatchNo(String arg)
	{
		wBatchNo = arg;
	}

	//#CM725882
	/**
	 * Obtain the Batch No. (Schedule No.). 
	 * @return Batch No. (Schedule No.) 
	 */
	public String getBatchNo()
	{
		return wBatchNo;
	}

	//#CM725883
	/**
	 * Set the Entry type. 
	 * @param arg Entry Type to be set
	 */
	public void setRegistKind(String arg)
	{
		wRegistKind = arg;
	}

	//#CM725884
	/**
	 * Obtain the Entry type. 
	 * @return Entry type 
	 */
	public String getRegistKind()
	{
		return wRegistKind;
	}

	//#CM725885
	/**
	 * Set the work No. (vector) . 
	 * @param arg Work No. to be set (vector) 
	 */
	public void setJobNoList(Vector arg)
	{
		wJobNoList = arg;
	}

	//#CM725886
	/**
	 * Obtain the work No. (vector) . 
	 * @return Work No. (vector) 
	 */
	public Vector getJobNoList()
	{
		return wJobNoList;
	}

	//#CM725887
	/**
	 * Set the last update date/time (vector) . 
	 * @param arg Last update date/time (vector) to be set
	 */
	public void setLastUpdateDateList(Vector arg)
	{
		wLastUpdateDateList = arg;
	}

	//#CM725888
	/**
	 * Obtain the last update date/time (vector) . 
	 * @return Last update date/time (vector) 
	 */
	public Vector getLastUpdateDateList()
	{
		return wLastUpdateDateList;
	}

    //#CM725889
    /**
     * Set the Planned Picking Date (Pulldown). 
     * @param retrievalPlanDateArray Planned Picking Date to be set (Pulldown) 
     */
    public void setRetrievalPlanDateArray(String[] retrievalPlanDateArray)
    {
        wRetrievalPlanDateArray = retrievalPlanDateArray;
    }

    //#CM725890
    /**
     * Obtain the Planned Picking Date (Pulldown). 
     * @return Planned Picking Date (Pulldown) 
     */
    public String[] getRetrievalPlanDateArray()
    {
        return wRetrievalPlanDateArray;
    }

	//#CM725891
	/**
	 * Set the work status (listcell). 
	 * @param arg Work Status (listcell) to be set
	 */
	public void setStatusFlagL(String arg)
	{
		wStatusFlagL = arg;
	}

	//#CM725892
	/**
	 * Obtain the work status (listcell). 
	 * @return Work status (listcell) 
	 */
	public String getStatusFlagL()
	{
		return wStatusFlagL;
	}

	//#CM725893
	/**
	 * Set the button type. 
	 * @param arg Button type to be set
	 * @throws InvalidStatusException Setting undefined button type causes to announce this.
	 */
	public void setButtonType(String arg) throws InvalidStatusException
	{
		if ((arg.equals(BUTTON_MODIFYSUBMIT)) || (arg.equals(BUTTON_ALLWORKINGCLEAR)))
		{
			wButtonType = arg;
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wBeginningFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6006009" + MessageResource.DELIM + tObj[0] + MessageResource.DELIM + tObj[1] + MessageResource.DELIM + tObj[2]));
		}
	}

	//#CM725894
	/**
	 * Obtain the button type. 
	 * @return Button type 
	 */
	public String getButtonType()
	{
		return wButtonType;
	}

	//#CM725895
	/**
	 * Set the Data count. 
	 * @param arg Data count to be set
	 */
	public void setDataCount(int arg)
	{
		wDataCount = arg;
	}

	//#CM725896
	/**
	 * Obtain the count of data. 
	 * @return	Data count
	 */
	public int getDataCount()
	{
		return wDataCount;
	}

	//#CM725897
	/**
	 * Set the Item/Order. 
	 * @param arg Item/Order to be set
	 */
	public void setItemOrderFlag(String arg)
	{
		wItemOrderFlag = arg;
	}

	//#CM725898
	/**
	 * Obtain the Item/Order. 
	 * @return	 Item/Order 
	 */
	public String getItemOrderFlag()
	{
		return wItemOrderFlag;
	}

	//#CM725899
	/**
	 * Set the picking case qty. 
	 * @param arg Picking Case qty to be set
	 */
	public void setRetrievalCaseQty(int arg)
	{
		wRetrievalCaseQty = arg ;
	}

	//#CM725900
	/**
	 * Obtain the picking case qty. 
	 * @return Picking Case Qty 
	 */
	public int getRetrievalCaseQty()
	{
		return wRetrievalCaseQty;
	}

	//#CM725901
	/**
	 * Set the picking piece qty. 
	 * @param arg Picking Piece Qty to be set
	 */
	public void setRetrievalPieceQty(int arg)
	{
		wRetrievalPieceQty = arg ;
	}

	//#CM725902
	/**
	 * Obtain the picking piece qty. 
	 * @return Picking Piece Qty 
	 */
	public int getRetrievalPieceQty()
	{
		return wRetrievalPieceQty;
	}
	
	//#CM725903
	/**
	 * Set the Quantity display type. 
	 * @param arg Quantity Display Type to be set
	 */
	public void setQtyDisplayKind(String arg)
	{
		wQtyDisplayKind = arg ;
	}

	//#CM725904
	/**
	 * Obtain the Quantity display type 
	 * @return Quantity display type 
	 */
	public String getQtyDisplayKind()
	{
		return wQtyDisplayKind ;
	}

	//#CM725905
	/**
	 * Set the Result Report Division. 
	 * @param arg Result Report Division to be set
	 */
	public void setReportFlag(String arg)
	{
		wReportFlag = arg;
	}

	//#CM725906
	/**
	 * Obtain the Result Report Division. 
	 * @return Result Report Division 
	 */
	public String getReportFlag()
	{
		return wReportFlag;
	}

	//#CM725907
	/**
	 * Set the Result Report Division Name. 
	 * @param arg Result Report Division Name to be set
	 */
	public void setReportFlagName(String arg)
	{
		wReportFlagName = arg;
	}

	//#CM725908
	/**
	 * Obtain the Result Report Division Name. 
	 * @return Result Report Division Name 
	 */
	public String getReportFlagName()
	{
		return wReportFlagName;
	}

	//#CM725909
	/**
	 * Set the Start Order No. 
	 * @param arg Start Order No. to be set
	 */
	public void setStartOrderNo(String arg)
	{
		wStartOrderNo = arg;
	}

	//#CM725910
	/**
	 * Obtain the Start Order No. 
	 * @return Start Order No. 
	 */
	public String getStartOrderNo()
	{
		return wStartOrderNo;
	}

	//#CM725911
	/**
	 * Set the End Order No. 
	 * @param arg End Order No. to be set
	 */
	public void setEndOrderNo(String arg)
	{
		wEndOrderNo = arg;
	}

	//#CM725912
	/**
	 * Obtain the End Order No. 
	 * @return End Order No. 
	 */
	public String getEndOrderNo()
	{
		return wEndOrderNo;
	}

	//#CM725913
	/**
	 * Set the Start item code. 
	 * @param arg Start Item Code to be set
	 */
	public void setStartItemCode(String arg)
	{
		wStartItemCode = arg;
	}

	//#CM725914
	/**
	 * Obtain the Start item code. 
	 * @return Start Item Code 
	 */
	public String getStartItemCode()
	{
		return wStartItemCode;
	}

	//#CM725915
	/**
	 * Set the End item code. 
	 * @param arg End Item Code to be set
	 */
	public void setEndItemCode(String arg)
	{
		wEndItemCode = arg;
	}

	//#CM725916
	/**
	 * Obtain the End item code. 
	 * @return End Item Code 
	 */
	public String getEndItemCode()
	{
		return wEndItemCode;
	}

	//#CM725917
	/**
	 * Set the allocation target, storage place 
	 * @param arg Allocation target, storage place to be set
	 */
	public void setAllocateField(String[] arg)
	{
		wAllocateField = arg;
	}

	//#CM725918
	/**
	 * Obtain the allocation target, storage place 
	 * @return Allocation target, storage place 
	 */
	public String[] getAllocateField()
	{
		return wAllocateField;
	}

	//#CM725919
	/**
	 * Set the Shortage evidence. 
	 * @param arg Shortage evidence to be set
	 */
	public void setShortageFlag(boolean arg)
	{
		wShortageFlag = arg;
	}

	//#CM725920
	/**
	 * Obtain the Shortage evidence. 
	 * @return Shortage evidence 
	 */
	public boolean getShortageFlag()
	{
		return wShortageFlag;
	}

	//#CM725921
	/**
	 * Set the request for printing Shortage list. 
	 * @param arg Request for printing Shortage list to be set
	 */
	public void setShortageListRequestFlag(String arg)
	{
		wShortageListRequestFlag = arg;
	}

	//#CM725922
	/**
	 * Obtain the request for printing Shortage list. 
	 * @return Request for printing Shortage list 
	 */
	public String getShortageListRequestFlag()
	{
		return wShortageListRequestFlag;
	}

	//#CM725923
	/**
	 * Set the schedule flag. 
	 * @param arg Schedule flag to be set
	 */
	public void setScheduleFlag(String arg)
	{
		wScheduleFlag = arg;
	}

	//#CM725924
	/**
	 * Obtain the schedule flag. 
	 * @return Schedule flag 
	 */
	public String getScheduleFlag()
	{
		return wScheduleFlag;
	}

	//#CM725925
	/**
	 * Set the array of the schedule flag. 
	 * @param arg Array of schedule flag to be set
	 */
	public void setScheduleFlagArray(String arg[])
	{
		wScheduleFlagArray = arg;
	}

	//#CM725926
	/**
	 * Obtain the array of the schedule flag. 
	 * @return Array of the schedule flag 
	 */
	public String[] getScheduleFlagArray()
	{
		return wScheduleFlagArray;
	}

	//#CM725927
	/**
	 * Set the Plan unique Key (vector) . 
	 * @param arg Plan unique key (vector) to be set
	 */
	public void setPlanUkeyList(Vector arg)
	{
		wPlanUkeyList = arg;
	}

	//#CM725928
	/**
	 * Obtain the Plan unique Key (vector) . 
	 * @return Plan unique key (vector) 
	 */
	public Vector getPlanUkeyList()
	{
		return wPlanUkeyList;
	}

	//#CM725929
	/**
	 * Set the System distinct key. 
	 * @param arg System identification key to be set
	 */
	public void setSystemDiscKey(int arg)
	{
		wSystemDiscKey = arg;
	}

	//#CM725930
	/**
	 * Obtain the System distinct key. 
	 * @return System identification key 
	 */
	public int getSystemDiscKey()
	{
		return wSystemDiscKey;
	}
	
	//#CM725931
	/**
	 * Set the System identification key (Array). 
	 * @param arg System identification key to be set (Array) 
	 */
	public void setSystemDiscKeyArray(int[] arg)
	{
		wSystemDiscKeyArray = arg;
	}

	//#CM725932
	/**
	 * Obtain the System identification key (Array). 
	 * @return System identification key (Array) 
	 */
	public int[] getSystemDiscKeyArray()
	{
		return wSystemDiscKeyArray;
	}
	
	//#CM725933
	/**
	 * Set the System distinct key name. 
	 * @param arg System identification key name to be set
	 */
	public void setSystemDiscKeyName(String arg)
	{
		wSystemDiscKeyName = arg;
	}

	//#CM725934
	/**
	 * Obtain the System distinct key name. 
	 * @return System identification key name 
	 */
	public String getSystemDiscKeyName()
	{
		return wSystemDiscKeyName;
	}
	
	//#CM725935
	/**
	 * Set the area type flag. 
	 * @param flag Area Type flag to be set
	 */
	public void setAreaTypeFlag(String flag)
	{
		wAreaTypeFlag = flag;
	}
	
	//#CM725936
	/**
	 * Obtain the area type flag. 
	 * @return Area Type flag 
	 */
	public String getAreaTypeFlag()
	{
		return wAreaTypeFlag;
	}
	
	//#CM725937
	/**
	 * Set the Host Aggregated the batch No. 
	 * @param arg Host Aggregated Batch No. to be set
	 */
	public void setHostCollectBatchNo(String arg)
	{
		wHostCollectBatchNo = arg ;
	}

	//#CM725938
	/**
	 * Obtain the Host Aggregated the batch No. 
	 * @return Host Aggregated Batch No. 
	 */
	public String getHostCollectBatchNo()
	{
		return wHostCollectBatchNo;
	}

	//#CM725939
	/**
	 * Set the Host aggregated Batch No. (listcell). 
	 * @param arg Host Aggregated Batch No. to be set (listcell) 
	 */
	public void setHostCollectBatchNoL(String arg)
	{
		wHostCollectBatchNoL = arg ;
	}

	//#CM725940
	/**
	 * Obtain the Host Aggregated Batch No. (listcell). 
	 * @return Host Aggregated Batch No. (listcell) 
	 */
	public String getHostCollectBatchNoL()
	{
		return wHostCollectBatchNoL;
	}

	//#CM725941
	/**
	 * Set the Case/Piece division (listcell). 
	 * @param arg Case/Piece division (listcell) to be set
	 */
	public void setCasePieceFlagL(String arg)
	{
		wCasePieceFlagL = arg ;
	}

	//#CM725942
	/**
	 * Obtain the Case/Piece division (listcell). 
	 * @return Case/Piece division (listcell) 
	 */
	public String getCasePieceFlagL()
	{
		return wCasePieceFlagL;
	}

	//#CM725943
	/**
	 * Set the Order No. (listcell). 
	 * @param arg Order No. (listcell) to be set
	 */
	public void setOrderNoL(String arg)
	{
		wOrderNoL = arg ;
	}

	//#CM725944
	/**
	 * Obtain the Order No. (listcell). 
	 * @return Order No. (listcell) 
	 */
	public String getOrderNoL()
	{
		return wOrderNoL;
	}

	//#CM725945
	/**
	 * Set the allocation flag. 
	 * @param arg Allocation flag to be set
	 */
	public void setAllocationFlag(String arg)
	{
		wAllocationFlag = arg ;
	}

	//#CM725946
	/**
	 * Obtain the allocation flag 
	 * @return Allocation flag 
	 */
	public String getAllocationFlag()
	{
		return wAllocationFlag;
	}

    //#CM725947
    /**
     * Set the flag enabled/disabled to check length.
     * @param lineCheckFlag Flag to enable or disable to check length to be set
     */
    public void setLineCheckFlag(boolean lineCheckFlag)
    {
        wLineCheckFlag = lineCheckFlag;
    }
	
	//#CM725948
	/**
     * Obtain the Flag to enable or disable to check length.
     * @return Flag to enable or disable to check length. 
     */
    public boolean getLineCheckFlag()
    {
        return wLineCheckFlag;
    }

}
//#CM725949
//end of class
