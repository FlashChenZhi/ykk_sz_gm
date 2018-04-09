package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
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

/**
 * Designer : K.Mori <BR>
 * Maker : K.Mori <BR>
 * <BR>
 * Use the <CODE>StorageSupportParameter</CODE> class to pass the parameter between the screen and the schedule parameter in the storage package.<BR>
 * This class maintains the items (count) to be used in each stock package screen. The screen depends on the items (count).<BR>
 * <BR>
 * <DIR>
 * Allow the <CODE>StorageSupportParameter</CODE> class to maintain the items (count):<BR>
 * <BR>
 *     Worker Code <BR>
 *     Password <BR>
 *     Worker Name <BR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Item Code <BR>
 *     Item Code(list cell) <BR>
 *     Item Name <BR>
 *     Start Storage Date <BR>
 *     End Storage Date <BR>
 *     Start Item Code <BR>
 *     End Item Code <BR>
 *     Case/Piece division <BR>
 *     Case/Piece division name <BR>
 *     Planned storage date <BR>
 *     Storage Location <BR>
 *     Expiry Date <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Total plannned qty <BR>
 *     Planned Case Qty <BR>
 *     Planned Piece Qty <BR>
 *     Storage Case Qty <BR>
 *     Storage Piece Qty <BR>
 *     Shortage Case Qty <BR>
 *     Shortage Piece Qty <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Work Status <BR>
 *     Relocation Source Location <BR>
 *     Relocation Target Location <BR>
 *     Relocation Case Qty <BR>
 *     Relocation Piece Qty <BR>
 *     Printing division for Storage Instruction <BR>
 *     Storage result display division <BR>
 *     Cancel remaining work division <BR>
 *     Relocation Cancel division <BR>
 *     Display Completed Relocation Data division <BR>
 *     Start Location <BR>
 *     End Location <BR>
 *     Inventory Check Case Qty <BR>
 *     Inventory Check Piece Qty <BR>
 *     Total Inventory Check Qty <BR>
 *     Stock Case Qty <BR>
 *     stock piece qty <BR>
 *     Total Stock Qty <BR>
 *     Total Stock Case Qty <BR>
 *     Total stock piece qty <BR>
 *     //added 24 SEP 2004
 *     Planned start storage Date<BR>
 *     Planned end storage Date<BR>
 *     Display order<BR>
 *     Aggregate Display<BR>
 *     Delete Storage Plan List Print Type<BR>
 *     Location(for Inventory Check)<BR>
 *     move status name<BR>
 *     Result Case Qty<BR>
 *     Result Piece Qty<BR>
 *     Work Date<BR>
 *     Start Work Date<BR>
 *     End Work Date<BR>
 *     Case Location<BR>
 *     Piece Location<BR>
 *     button type<BR>
 *     Work Status(list cell)<BR>
 *     Status(Inventory Check Work inquiry)<BR>
 *     Work No.<BR>
 *     Preset line No.<BR>
 *     Add dvision<BR>
 *     Added Date/Time<BR>
 *     Name of Add Process<BR>
 *     Last update date/time<BR>
 *     Last update process name<BR>
 *     total result qty<BR>
 *     result report flag<BR>
 *     result report flag name<BR>
 *     Inventory check work presence<BR>
 *     Inventory check work presence identification<BR>
 *     Shortage division<BR>
 *     Storage Plan unique key<BR>
 *     Picking Worker Code <BR>
 *     Picking Worker Name <BR>
 *     Relocation work list print type <BR>
 *     Inventory control package division <BR>
 *     Aggregation Work No. <BR>
 *     Expiry Date control flag <BR>
 *     Work No.(Vector) <BR>
 *     Last update date/time(Vector) <BR>
 *
 *     Batch No.(Schedule No.) <BR>
 *     Add dvision <BR>
 *     Supplier Code <BR>
 *     Supplier Name <BR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/22</TD><TD>K.Mori</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 06:51:23 $
 * @author  $Author: suresh $
 */
public class StorageSupportParameter extends Parameter
{
	/**
	 * Case/Piece division Case
	 */
	public static final String CASEPIECE_FLAG_CASE = "1" ;
	/**
	 * Case/Piece division Piece
	 */
	public static final String CASEPIECE_FLAG_PIECE = "2" ;

	/**
	 * Case/Piece division None
	 */
	public static final String CASEPIECE_FLAG_NOTHING = "3" ;

	/**
	 * Case/Piece division Mixed
	 */
	public static final String CASEPIECE_FLAG_MIXED = "9" ;

	/**
	 * Case/Piece division All
	 */
	public static final String CASEPIECE_FLAG_ALL = "99" ;

	/**
	 * Work Status flag Standby
	 */
	public static final String STATUS_FLAG_UNSTARTED = "0" ;

	/**
	 * Work Status flag Start
	 */
	public static final String STATUS_FLAG_STARTED = "1" ;

	/**
	 * Work Status flag Processing
	 */
	public static final String STATUS_FLAG_WORKING = "2" ;

	/**
	 * Work Status flag Partially Completed
	 */
	public static final String STATUS_FLAG_PARTIAL_COMPLETION = "3" ;

	/**
	 * Work Status flag completed
	 */
	public static final String STATUS_FLAG_COMPLETION = "4" ;

	/**
	 * Work Status flag Deleted
	 */
	public static final String STATUS_FLAG_DELETE = "9" ;

	/**
	 * Work Status flag All
	 */
	public static final String STATUS_FLAG_ALL = "99" ;

	/**
	 * Display order Order of Storage Date
	 */
	public static final String DISPLAY_ORDER_WORK_DATE = "0" ;

	/**
	 * Display order Order of Planned storage date
	 */
	public static final String DISPLAY_ORDER_PLAN_DATE = "1" ;

	/**
	 * Display order Item Code sequence
	 */
	public static final String DISPLAY_ORDER_ITEM_CODE = "2" ;

	/**
	 * Display order Relocation Source Location sequence
	 */
	public static final String DISPLAY_ORDER_SOURCE_LOCATION = "3" ;

	/**
	 * Aggregate Display Item Code-Storage Location
	 */
	public static final String AGGREGATEDISPLAY_ITEM_AND_LOCATION = "0" ;

	/**
	 * Aggregate Display Planned storage date
	 */
	public static final String AGGREGATEDISPLAY_PLAN_DATE = "1" ;

	/**
	 * button type(correction adding)
	 */
	public static final String BUTTON_MODIFYSUBMIT = "0";

	/**
	 * button type(Releasing one batch Processing)
	 */
	public static final String BUTTON_ALLWORKINGCLEAR = "1";

	/**
	 * button type(Deleted)
	 */
	public static final String BUTTON_DELETESUBMIT = "9";

	/**
	 * result report flag(Not Reported)
	 */
	public static final String REPORT_FLAG_NOT_SENT = "0";

	/**
	 * result report flag(Reported)
	 */
	public static final String REPORT_FLAG_SENT = "1";

	/**
	 * result report flag name
	 */
	private String wReportFlagName;

	/**
	 * System distinct key Flat storage
	 */
	public static final int SYSTEM_DISC_KEY_FLOOR = 0;

	/**
	 * System distinct key
	 */
	public static final int SYSTEM_DISC_KEY_MOVEMENT_RACK = 1;

	/**
	 * System distinct key ASRS
	 */
	public static final int SYSTEM_DISC_KEY_ASRS = 2;

	/**System distinct key name
	 */
	private String wSystemDiscKeyName;

	/**
	 * Status(Inventory Check Work inquiry):All
	 */
	public static final String DISP_STATUS_ALL = "0";

	/**
	 * Status(Inventory Check Work inquiry):Inventory difference
	 */
	public static final String DISP_STATUS_DIFFERENCE = "1";

	/**
	 * Status(Inventory Check Work inquiry):Inventory same qty
	 */
	public static final String DISP_STATUS_EQUAL = "2";

	/**
	 * Inventory check work presence not added
	 */
	public static final String INVENTORY_KIND_NOTHING = "0" ;

	/**
	 * Inventory check work presence added
	 */
	public static final String INVENTORY_KIND_FIND = "1" ;

	/**
	 * search flag(for storage):Plan
	 */
	public static final String SEARCH_STORAGE_PLAN = "0" ;

	/**
	 * search flag(for storage):Stock
	 */
	public static final String SEARCH_STORAGE_STOCK = "1" ;

	/**
	 * search flag(for storage):Work
	 */
	public static final String SEARCH_STORAGE_WORK = "2" ;

	/**
	 * search flag(for storage):result
	 */
	public static final String SEARCH_STORAGE_RESULT = "3" ;

	/**
	 * search flag(for Location check)
	 */
	public static final String SEARCH_INVENTORY = "4" ;

	/**
	 * search flag(for inventory relocation)
	 */
	public static final String SEARCH_STOCKMOVE = "5" ;

	/**
	 * search flag(for Location check(Inventory Check + Inventory Table))
	 */
	public static final String SEARCH_INVENTORY_AND_STOCK = "6" ;

	/**
	 * Area flag(Start)
	 */
	public static final String RANGE_START = "0";

	/**
	 * Area flag(End)
	 */
	public static final String RANGE_END = "1";

	/**
	 * Data modification flag (Without change)
	 */
	public static final String UPDATEFLAG_NO = "0";

	/**
	 * Data modification flag (With change)
	 */
	public static final String UPDATEFLAG_YES = "1";

	/**
	 *  With shortage division
	 */
	public static final String SHORTAGE_FLAG_CHECKED = "1" ;

	/**
	 * Without shortage division
	 */
	public static final String SHORTAGE_FLAG_UNCHECKED = "0" ;

	/**
	 * with Pending/additional storage division
	 */
	public static final String REMNANT_FLAG_CHECKED = "1" ;

	/**
	 * Without Pending/additional storage division
	 */
	public static final String REMNANT_FLAG_UNCHECKED = "0" ;

	/**
	 * Initial Input of Receivable Qty: Enabled
	 */
	public static final String REMNANT_DISPLAY_CHECKED = "1" ;

	/**
	 * Initial Input of Receivable Qty: Disabled
	 */
	public static final String REMNANT_DISPLAY_UNCHECKED = "0" ;

	/**
	 * Add dvision Extract/Add via Screen(other than Linked to Receiving)
	 */
	public static final String REGIST_KIND_NOT_INSTOCK = "1" ;

	/**
	 * Add dvision Extract/Add via Screen(All)
	 */
	public static final String REGIST_KIND_ALL = "99" ;

	/**
	 * area type flag(Except automated warehouse)
	 */
	public static final String AREA_TYPE_FLAG_NOASRS = "1";

	// Class variables -----------------------------------------------
	/**
	 * Worker Code
	 */
	private String wWorkerCode;

	/**
	 * Password
	 */
	private String wPassword;

	/**
	 * Worker Name
	 */
	private String wWorkerName;

	/**
	 * Consignor code
	 */
	private String wConsignorCode;

	/**
	 * Consignor name
	 */
	private String wConsignorName;

	/**
	 * Item Code(for search)
	 */
	private String wItemCodeCondition;

	/**
	 * Item Code(For update)
	 */
	private String wItemCode;

	/**
	 * Item Code(list cell)
	 */
	private String wItemCodeL;

	/**
	 * Item Name
	 */
	private String wItemName;

	/**
	 * Start Storage Date
	 */
	private java.util.Date wFromStorageDate;

	/**
	 * End date
	 */
	private java.util.Date wToStorageDate;

	/**
	 * Start Item Code
	 */
	private String wFromItemCode;

	/**
	 * End Item Code
	 */
	private String wToItemCode;

	/**
	 * Case/Piece division(for search)
	 */
	private String wCasePieceflgCondition;

	/**
	 * Case/Piece division(For update)
	 */
	private String wCasePieceflg;

	/**
	 * Case/Piece division name
	 */
	private String wCasePieceflgName;

	/**
	 * Storage Location
	 */
	private String wStorageLocation;

	/**
	 * Expiry Date
	 */
	private String wUseByDate;

	/**
	 * Packed qty per Case
	 */
	private int wEnteringQty;

	/**
	 * Packed qty per bundle
	 */
	private int wBundleEnteringQty;

	/**
	 * Total plannned qty
	 */
	private int wTotalPlanQty;

	/**
	 * Planned Case Qty
	 */
	private int wPlanCaseQty;

	/**
	 * Planned Piece Qty
	 */
	private int wPlanPieceQty;

	/**
	 * Host plan Qty
	 */
	private int wHostPlanQty;

	/**
	 * Shortage Case Qty
	 */
	private int wShortageQty;

	/**
	 * Shortage Piece Qty
	 */
	private int wShortagePieceQty;

	/**
	 * Case ITF
	 */
	private String wITF;

	/**
	 * Bundle ITF
	 */
	private String wBundleITF;

	/**
	 * Work Status
	 */
	private String wWorkStatus;

	/**
	 * Work status name
	 */
	private String wWorkStatusName;

	/**
	 * relocation source Location No.
	 */
	private String wSourceLocationNo;

	/**
	 * relocation target Location No.
	 */
	private String wDestLocationNo;

	/**
	 * Relocation Case Qty
	 */
	private long wMovementCaseQty;

	/**
	 * Relocation Piece Qty
	 */
	private long wMovementPieceQty;

	/**
	 * Printing division for Storage Instruction
	 */
	private boolean wStorageListFlg = false;

	/**
	 * Storage result display division
	 */
	private boolean wResultDisplayFlg = false;

	/**
	 * Cancel remaining work division
	 */
	private boolean wWorkCancelFlg = false;

	/**
	 * Relocation Cancel division
	 */
	private boolean wMoveCancelFlg = false;

    /**
     * Display Completed Relocation Data division
     */
    private boolean wMoveCompDisplayFlg = false;

	/**
	 * Start Location
	 */
	private String wFromLocation;

	/**
	 * End Location
	 */
	private String wToLocation;

	/**
	 * Inventory Check Case Qty
	 */
	private int wInventoryCheckCaseQty;

	/**
	 * Inventory Check Piece Qty
	 */
	private int wInventoryCheckPieceQty;

	/**
	 * Total Inventory Check Qty
	 */
	private int wTotalInventoryCheckQty;

	/**
	 * Stock Case Qty
	 */
	private int wStockCaseQty;

	/**
	 * stock piece qty
	 */
	private int wStockPieceQty;

	/**
	 * Total Stock Qty
	 */
	private long wTotalStockQty;

	/**
	 * Total Stock Case Qty
	 */
	private long wTotalStockCaseQty;

	/**
	 * Total stock piece qty
	 */
	private long wTotalStockPieceQty;

	/**
	 * Planned start storage Date
	 */
	private String wFromStoragePlanDate ;

	/**
	 * Planned end storage Date
	 */
	private String wToStoragePlanDate  ;

	/**
	 * Planned storage date characters
	 */
	private String wStoragePlanDate  ;

	/**
	 * Display order
	 */
	private String wDisplayOrder ;

	/**
	 * Aggregate Display
	 */
	private String wAggregateDisplay ;

	/**
	 * Delete Storage Plan List Print Type
	 */
	private boolean wDeleteStorageListFlag ;

	/**
	 * Location(for Inventory Check)
	 */
	private String wLocation ;

	/**
	 * move status name
	 */
	private String wMoveStatusName ;

	/**
	 * Work StatusArray
	 */
	private String[] wSearchStatus;

	/**
	 * Result Case Qty
	 */
	private int wResultCaseQty ;

	/**
	 * Result Piece Qty
	 */
	private int wResultPieceQty ;

	/**
	 * Work Date
	 */
	private String wWorkDate ;

	/**
	 * Start Work Date
	 */
	private String wFromWorkDate ;

	/**
	 * End Work Date
	 */
	private String wToWorkDate ;

	/**
	 * Case Location
	 */
	private String wCaseLocation ;

	/**
	 * Piece Location
	 */
	private String wPieceLocation ;

	/**
	 * button type
	 */
	private String wButtonType;

	/**
	 * Work Status(list cell)
	 */
	private String wStatusFlagL;

	/**
	 * Status(Inventory Check Work inquiry)
	 */
	private String wDispStatus;

	/**
	 * Work No.
	 */
	private String wJobNo;

	/**
	 * Stock ID
	 */
	private String wStockID;

	/**
	 * Preset line No.
	 */
	private int wRowNo;

	/**
	 * Add division
	 */
	private String wRegistKbn;

	/**
	 * Added Date/Time
	 */
	private java.util.Date wRegistDate;

	/**
	 * Name of Add Process
	 */
	private String wRegistPName;

	/**
	 * Last update date/time
	 */
	private java.util.Date wLastUpdateDate;

	/**
	 * Last update process name
	 */
	private String wLastUpdatePName;

	/**
	 * total result qty
	 */
	private int wTotalResultQty;

	/**
	 * result report flag
	 */
	private String wReportFlag;

	/**
	 * System distinct key
	 */
	private int wSystemDiscKey;

	/**
	 * Shortage division
	 */
	private boolean wShortage = false;

	/**
	 * Separate delivery division
	 */
	private boolean wRemnantFlag = false ;

	/**
	 * Storage Plan unique key
	 */
	private String wStoragePlanUKey;

	/**
	 * Case Location(search conditions)
	 */
	private String wCaseLocationCondition;

	/**
	 * Piece Location(search conditions)
	 */
	private String wPieceLocationCondition;

	/**
	 * Inventory check work presence
	 */
	private String wInventoryKind;

	/**
	 * Inventory check work presence identification
	 */
	private String wInventoryKindName;

	/**
	 * Picking Worker Code
	 */
	private String wRetrievalWorkerCode;

	/**
	 * Picking Worker Name
	 */
	private String wRetrievalWorkerName;

	/**
	 * Relocation work list print type
	 */
	private boolean wMoveWorkListFlag;

	/**
	 * Inventory control package division
	 */
	private boolean wStockPackageFlag;

	/**
	 * Aggregation Work No.
	 */
	private String wCollectJobNo;

	/**
	 * Expiry Date control flag
	 */
	private boolean wUseByDateFlag;

	/**
	 * Work No.(Vector)
	 */
	private Vector wJobNoList;

	/**
	 * Last update date/time(Vector)
	 */
	private Vector wLastUpdateDateList;

	/**
	 * Storage Date
	 */
	private String wStorageDate;

	/**
	 * Batch No.(Schedule No.)
	 */
	private String wBatchNo;

   /**
	* Add dvision(for repeated search)
	*/
   private String wRegistKbnCondition;


   /**
	* Supplier Code
	*/
   private String wSupplierCode;

   /**
	* Supplier Name
	*/
   private String wSupplierName1;

   /**
	* area type flag
	*/
   private String wAreaTypeFlag;

	/**
	 * Area
	 */
	private String wRetrievalArea;

	/**
	 * Area Name
	 */
	private String wRetrievalAreaName;

	/**
	 * Flag to enable or disable check for the number of digits.
	 */
	private boolean wLineCheckFlag = true;
	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/18 06:51:23 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StorageSupportParameter()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * Set the Worker code.
	 * @param arg Worker Code to be set
	 */
	public void setWorkerCode(String arg)
	{
		wWorkerCode = arg ;
	}

	/**
	 * Obtain the Worker code.
	 * @return Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode ;
	}

	/**
	 * Set the password.
	 * @param arg Password to be set
	 */
	public void setPassword(String arg)
	{
		wPassword = arg ;
	}

	/**
	 * Obtain the password.
	 * @return Password
	 */
	public String getPassword()
	{
		return wPassword ;
	}

	/**
	 * Set the Worker name.
	 * @param arg Worker Name to be set
	 */
	public void setWorkerName(String arg)
	{
		wWorkerName = arg ;
	}

	/**
	 * Obtain the Worker name.
	 * @return Worker Name
	 */
	public String getWorkerName()
	{
		return wWorkerName ;
	}

	/**
	 * Set the Consignor code.
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg ;
	}

	/**
	 * Obtain the Consignor code.
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode ;
	}

	/**
	 * Set the Consignor name.
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		wConsignorName = arg ;
	}

	/**
	 * Obtain the Consignor Name.
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return wConsignorName ;
	}

	/**
	 * Set the item code (for search).
	 * @param arg Item Code to be set(for search)
	 */
	public void setItemCodeCondition(String arg)
	{
		wItemCodeCondition = arg ;
	}

	/**
	 * Obtain the item code (for search).
	 * @return Item Code(for search)
	 */
	public String getItemCodeCondition()
	{
		return wItemCodeCondition ;
	}

	/**
	 * Set the item code (for update).
	 * @param arg Item Code to be set(For update)
	 */
	public void setItemCode(String arg)
	{
		wItemCode = arg ;
	}

	/**
	 * Obtain the item code (for update).
	 * @return Item Code(For update)
	 */
	public String getItemCode()
	{
		return wItemCode ;
	}

	/**
	 * Set the item code (list cell).
	 * @param arg Item Code to be set(list cell)
	 */
	public void setItemCodeL(String arg)
	{
		wItemCodeL = arg;
	}

	/**
	 * Obtain the item code (list cell).
	 * @return Item Code(list cell)
	 */
	public String getItemCodeL()
	{
		return wItemCodeL;
	}

	/**
	 * Set the item name.
	 * @param arg Item Name to be set
	 */
	public void setItemName(String arg)
	{
		wItemName = arg ;
	}

	/**
	 * Obtain the item name.
	 * @return Item Name
	 */
	public String getItemName()
	{
		return wItemName ;
	}

	/**
	 * Start Set the storage date.
	 * @param arg Start Storage Date to be set
	 */
	public void setFromStorageDate(java.util.Date arg)
	{
		wFromStorageDate = arg ;
	}

	/**
	 * Obtain the start storage date.
	 * @return Start Storage Date
	 */
	public java.util.Date getFromStorageDate()
	{
		return wFromStorageDate;
	}

	/**
	 * Set the storage End date.
	 * @param arg End Storage Date to be set
	 */
	public void setToStorageDate(java.util.Date arg)
	{
		wToStorageDate = arg ;
	}

	/**
	 * Obtain the End storage date.
	 * @return End Storage Date
	 */
	public java.util.Date getToStorageDate()
	{
		return wToStorageDate;
	}

	/**
	 * Set the Start item code.
	 * @param arg Start Item Code to be set
	 */
	public void setFromItemCode(String arg)
	{
		wFromItemCode = arg ;
	}

	/**
	 * Obtain the Start item code.
	 * @return Start Item Code
	 */
	public String getFromItemCode()
	{
		return wFromItemCode;
	}

	/**
	 * Set the End item code.
	 * @param arg End Item Code to be set
	 */
	public void setToItemCode(String arg)
	{
		wToItemCode = arg ;
	}

	/**
	 * Obtain the End item code.
	 * @return End Item Code
	 */
	public String getToItemCode()
	{
		return wToItemCode;
	}

	/**
	 * Case-Set the Piece division (for search).
	 * @param arg Case/Piece division to be set(for search)
	 */
	public void setCasePieceflgCondition(String arg)
	{
		wCasePieceflgCondition = arg ;
	}

	/**
	 * Case-Obtain the Piece division (for search).
	 * @return Case/Piece division(for search)
	 */
	public String getCasePieceflgCondition()
	{
		return wCasePieceflgCondition;
	}

	/**
	 * Case-Set the Piece division (for update).
	 * @param arg Case/Piece division to be set(For update)
	 */
	public void setCasePieceflg(String arg)
	{
		wCasePieceflg = arg ;
	}

	/**
	 * Case-Obtain the Piece division (for update).
	 * @return Case/Piece division(For update)
	 */
	public String getCasePieceflg()
	{
		return wCasePieceflg;
	}

	/**
	 * Case-Set the Piece division name.
	 * @param arg Case/Piece division name to be set
	 */
	public void setCasePieceflgName(String arg)
	{
		wCasePieceflgName = arg ;
	}

	/**
	 * Case-Obtain the Piece division name.
	 * @return  Case/Piece division name
	 */
	public String getCasePieceflgName()
	{
		return wCasePieceflgName;
	}

	/**
	 * Set the storage location.
	 * @param arg Storage Location to be set
	 */
	public void setStorageLocation(String arg)
	{
		wStorageLocation = arg ;
	}

	/**
	 * Obtain the storage location.
	 * @return Storage Location
	 */
	public String getStorageLocation()
	{
		return wStorageLocation;
	}

	/**
	 * Set the expiry date.
	 * @param arg Expiry Date to be set
	 */
	public void setUseByDate(String arg)
	{
		wUseByDate = arg ;
	}

	/**
	 * Obtain the expiry date.
	 * @return Expiry Date
	 */
	public String getUseByDate()
	{
		return wUseByDate;
	}

	/**
	 * Set the Packed qty per case.
	 * @param arg Packed qty per Case to be set
	 */
	public void setEnteringQty(int arg)
	{
		wEnteringQty = arg ;
	}

	/**
	 * Obtain the Packed qty per case.
	 * @return Packed qty per Case
	 */
	public int getEnteringQty()
	{
		return wEnteringQty;
	}

	/**
	 * Set the Packed qty per bundle.
	 * @param arg Packed qty per bundle to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		wBundleEnteringQty = arg ;
	}

	/**
	 * Obtain the Packed qty per bundle.
	 * @return Packed qty per bundle
	 */
	public int getBundleEnteringQty()
	{
		return wBundleEnteringQty;
	}

	/**
	 * Set the total planned qty.
	 * @param arg Total plannned qty to be set
	 */
	public void setTotalPlanQty(int arg)
	{
		wTotalPlanQty = arg ;
	}

	/**
	 * Obtain the total planned qty.
	 * @return Total plannned qty
	 */
	public int getTotalPlanQty()
	{
		return wTotalPlanQty;
	}

	/**
	 * Set the planned case qty.
	 * @param arg Planned Case Qty to be set
	 */
	public void setPlanCaseQty(int arg)
	{
		wPlanCaseQty = arg ;
	}

	/**
	 * Obtain the planned case qty.
	 * @return Planned Case Qty
	 */
	public int getPlanCaseQty()
	{
		return wPlanCaseQty;
	}

	/**
	 * Set the planned piece qty.
	 * @param arg Planned Piece Qty to be set
	 */
	public void setPlanPieceQty(int arg)
	{
		wPlanPieceQty = arg ;
	}

	/**
	 * Obtain the planned piece qty.
	 * @return Planned Piece Qty
	 */
	public int getPlanPieceQty()
	{
		return wPlanPieceQty;
	}

	/**
	 * Set the shortage case qty.
	 * @param arg Shortage Case Qty to be set
	 */
	public void setShortageQty(int arg)
	{
		wShortageQty = arg ;
	}

	/**
	 * Obtain the shortage case qty.
	 * @return Shortage Case Qty
	 */
	public int getShortageQty()
	{
		return wShortageQty;
	}

	/**
	 * Set the shortage piece qty.
	 * @param arg Shortage Piece Qty to be set
	 */
	public void setShortagePieceQty(int arg)
	{
		wShortagePieceQty = arg ;
	}

	/**
	 * Obtain the shortage piece qty.
	 * @return Shortage Piece Qty
	 */
	public int getShortagePieceQty()
	{
		return wShortagePieceQty;
	}

	/**
	 * Set the Case ITF.
	 * @param arg Case ITF to be set
	 */
	public void setITF(String arg)
	{
		wITF = arg ;
	}

	/**
	 * Obtain the Case ITF.
	 * @return Case ITF
	 */
	public String getITF()
	{
		return wITF;
	}

	/**
	 * Set the bundle ITF.
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleITF(String arg)
	{
		wBundleITF = arg ;
	}

	/**
	 * Obtain the bundle ITF.
	 * @return Bundle ITF
	 */
	public String getBundleITF()
	{
		return wBundleITF;
	}

    /**
     * Set the relocation source Location No.
     * @param arg relocation source Location No. to be set
     */
    public void setSourceLocationNo(String arg)
    {
		wSourceLocationNo = arg;
    }

    /**
     * Obtain the relocation source Location No.
     * @return relocation source Location No.
     */
    public String getSourceLocationNo()
    {
    	return wSourceLocationNo;
    }

    /**
     * Set the relocation target Location No.
     * @param arg relocation target Location No. to be set
     */
    public void setDestLocationNo(String arg)
    {
		wDestLocationNo = arg;
    }

    /**
     * Obtain the relocation target Location No.
     * @return relocation target Location No.
     */
    public String getDestLocationNo()
    {
    	return wDestLocationNo;
    }

	/**
	 * Set the relocation case qty.
	 * @param arg Relocation Case Qty to be set
	 */
	public void setMovementCaseQty(long arg)
	{
		wMovementCaseQty = arg ;
	}

	/**
	 * Obtain the relocation case qty.
	 * @return Relocation Case Qty
	 */
	public long getMovementCaseQty()
	{
		return wMovementCaseQty;
	}

	/**
	 * Set the relocation piece qty.
	 * @param arg Relocation Piece Qty to be set
	 */
	public void setMovementPieceQty(long arg)
	{
		wMovementPieceQty = arg ;
	}

	/**
	 * Obtain the relocation piece qty.
	 * @return Relocation Piece Qty
	 */
	public long getMovementPieceQty()
	{
		return wMovementPieceQty;
	}

	/**
	 * Set the Storage instruction print division.
	 * @param arg Set Printing division for Storage Instruction
	 */
	public void setStorageListFlg(boolean arg)
	{
		wStorageListFlg = arg ;
	}

	/**
	 * Obtain the storage instruction print division.
	 * @return Printing division for Storage Instruction
	 */
	public boolean getStorageListFlg()
	{
		return wStorageListFlg;
	}

	/**
	 * Set the storage result display division.
	 * @param arg Storage result display division to be set
	 */
	public void setResultDisplayFlg(boolean arg)
	{
		wResultDisplayFlg = arg ;
	}

	/**
	 * Obtain the storage result display division.
	 * @return Storage result display division
	 */
	public boolean getResultDisplayFlg()
	{
		return wResultDisplayFlg;
	}

	/**
	 * Set the Cancel Remaining Work division.
	 * @param arg Cancel remaining work division to be set
	 */
	public void setWorkCancelFlg(boolean arg)
	{
		wWorkCancelFlg = arg ;
	}

	/**
	 * Obtain the Cancel remaining work division.
	 * @return Cancel remaining work division
	 */
	public boolean getWorkCancelFlg()
	{
		return wWorkCancelFlg;
	}


	/**
	 * Set the relocation cancel division.
	 * @param arg Relocation Cancel division to be set
	 */
	public void setMoveCancelFlg(boolean arg)
	{
		wMoveCancelFlg = arg ;
	}

	/**
	 * Obtain the relocation Cancel division.
	 * @return Relocation Cancel division
	 */
	public boolean getMoveCancelFlg()
	{
		return wMoveCancelFlg;
	}

	/**
	 * Set the Display Completed Relocation Data division division.
	 * @param arg Display Completed Relocation Data division to be set
	 */
	public void setMoveCompDisplayFlg(boolean arg)
	{
		wMoveCompDisplayFlg = arg;
	}

    /**
     * Obtain the Display Completed Relocation Data division division.
     * @return Display Completed Relocation Data division
     */
    public boolean getMoveCompDisplayFlg()
    {
    	return wMoveCompDisplayFlg;
    }

	/**
	 * Set the Start Location.
	 * @param arg Start Location to be set
	 */
	public void setFromLocation(String arg)
	{
		wFromLocation = arg ;
	}

	/**
	 * Obtain the Start Location.
	 * @return Set the Start Location
	 */
	public String getFromLocation()
	{
		return wFromLocation;
	}

	/**
	 * Set the End Location.
	 * @param arg End Location to be set
	 */
	public void setToLocation(String arg)
	{
		wToLocation = arg ;
	}

	/**
	 * Obtain the End Location.
	 * @return End Location to be set
	 */
	public String getToLocation()
	{
		return wToLocation;
	}

	/**
	 * Set the inventory case qty.
	 * @param arg Inventory Check Case Qty to be set
	 */
	public void setInventoryCheckCaseQty(int arg)
	{
		wInventoryCheckCaseQty = arg ;
	}

	/**
	 * Obtain the inventory case qty.
	 * @return Inventory Check Case Qty
	 */
	public int getInventoryCheckCaseQty()
	{
		return wInventoryCheckCaseQty;
	}

	/**
	 * Set the inventory piece qty.
	 * @param arg Inventory Check Piece Qty to be set
	 */
	public void setInventoryCheckPieceQty(int arg)
	{
		wInventoryCheckPieceQty = arg ;
	}

	/**
	 * Obtain the inventory piece qty.
	 * @return Inventory Check Piece Qty
	 */
	public int getInventoryCheckPieceQty()
	{
		return wInventoryCheckPieceQty;
	}

	/**
	 * Set the total inventory check qty.
	 * @param arg Total Inventory Check Qty to be set
	 */
	public void setTotalInventoryCheckQty(int arg)
	{
		wTotalInventoryCheckQty = arg ;
	}

	/**
	 * Obtain the total inventory check qty.
	 * @return Total Inventory Check Qty
	 */
	public int getTotalInventoryCheckQty()
	{
		return wTotalInventoryCheckQty;
	}

	/**
	 * Set the Stock Case Qty.
	 * @param arg Stock Case Qty to be set
	 */
	public void setStockCaseQty (int arg)
	{
		wStockCaseQty = arg ;
	}

	/**
	 * Obtain Stock Case Qty
	 * @return Stock Case Qty
	 */
	public int getStockCaseQty()
	{
		return wStockCaseQty;
	}

	/**
	 * Set the stock piece qty.
	 * @param arg stock piece qty to be set
	 */
	public void setStockPieceQty(int arg)
	{
		wStockPieceQty = arg ;
	}

	/**
	 * Obtain the stock piece qty.
	 * @return stock piece qty
	 */
	public int getStockPieceQty()
	{
		return wStockPieceQty;
	}

	/**
	 * Set the Total stock qty.
	 * @param arg Total Stock Qty to be set
	 */
	public void setTotalStockQty(long arg)
	{
		wTotalStockQty = arg ;
	}

	/**
	 * Obtain the Total stock qty.
	 * @return Total Stock Qty
	 */
	public long getTotalStockQty()
	{
		return wTotalStockQty;
	}

	/**
	 * Set the total stock case qty.
	 * @param arg total stock Case Qty to be set
	 */
	public void setTotalStockCaseQty (long arg)
	{
		wTotalStockCaseQty = arg ;
	}

	/**
	 * Obtain the total stock case qty.
	 * @return Total Stock Case Qty
	 */
	public long getTotalStockCaseQty()
	{
		return wTotalStockCaseQty;
	}

	/**
	 * Total Set the stock piece qty.
	 * @param arg total stock piece qty to be set
	 */
	public void setTotalStockPieceQty(long arg)
	{
		wTotalStockPieceQty = arg ;
	}

	/**
	 * Obtain the total stock piece qty.
	 * @return Total stock piece qty
	 */
	public long getTotalStockPieceQty()
	{
		return wTotalStockPieceQty;
	}

	/**
	 * Set the Planned start storage date.
	 * @param startStoragePlanDate
	 */
	public void setFromStoragePlanDate(String fromStoragePlanDate)
	{
		wFromStoragePlanDate = fromStoragePlanDate ;
	}

	/**
	 * Obtain the Planned start storage Date
	 * @return Planned start storage Date
	 */
	public String getFromStoragePlanDate()
	{
		return wFromStoragePlanDate ;
	}

	/**
	 * Set the Planned end storage date.
	 * @param toStoragePlanDate
	 */
	public void setToStoragePlanDate(String toStoragePlanDate)
	{
		wToStoragePlanDate = toStoragePlanDate ;
	}

	/**
	 * Obtain Planned end storage Date
	 * @return Planned end storage Date
	 */
	public String getToStoragePlanDate()
	{
		return wToStoragePlanDate ;
	}

	/**
	 * Planned storage date characters
	 * @param storagePlanDateString
	 */
	public void setStoragePlanDate(String storagePlanDateString)
	{
		wStoragePlanDate = storagePlanDateString ;
	}

	/**
	 * Obtain Planned storage date (string)
	 * @return Storage Plan Date (String)
	 */
	public String getStoragePlanDate()
	{
		return wStoragePlanDate ;
	}

	/**
	 * Obtain Aggregate Display
	 * @return Aggregate Display
	 */
	public String getAggregateDisplay()
	{
		return wAggregateDisplay;
	}

	/**
	 * Obtain Delete Storage Plan List Print Type
	 * @return Delete Storage Plan List Print Type
	 */
	public boolean getDeleteStorageListFlag()
	{
		return wDeleteStorageListFlag;
	}

	/**
	 * Obtain Display order
	 * @return Display order
	 */
	public String getDisplayOrder()
	{
		return wDisplayOrder;
	}

	/**
	 * Obtain Location(for Inventory Check)
	 * @return Location(for Inventory Check)
	 */
	public String getLocation()
	{
		return wLocation;
	}

	/**
	 * Set Aggregate Display
	 * @param String aggregateDisplay
	 */
	public void setAggregateDisplay(String aggregateDisplay)
	{
		wAggregateDisplay = aggregateDisplay;
	}

	/**
	 * Set Delete Storage Plan List Print Type
	 * @param deleteStorageListFlag
	 */
	public void setDeleteStorageListFlag(boolean deleteStorageListFlag)
	{
		wDeleteStorageListFlag = deleteStorageListFlag;
	}

	/**
	 * Set Display order
	 * @param displayOrder
	 */
	public void setDisplayOrder(String displayOrder)
	{
		wDisplayOrder = displayOrder;
	}

	/**
	 * Set Location(for Inventory Check)
	 * @param String Location(for Inventory Check)
	 */
	public void setLocation(String location)
	{
		wLocation = location;
	}

	/**
	 * Obtain move status name
	 * @return move status name
	 */
	public String getMoveStatusName()
	{
		return wMoveStatusName;
	}

	/**
	 * Set move status name
	 * @param String moveStatusName
	 */
	public void setMoveStatusName(String moveStatusName)
	{
		wMoveStatusName = moveStatusName;
	}

	/**
	 * @return Returns the wHostPlanQty.
	 */
	public int getHostPlanQty()
	{
		return wHostPlanQty;
	}
	/**
	 * @param hostPlanQty The wHostPlanQty to set.
	 */
	public void setHostPlanQty(int hostPlanQty)
	{
		wHostPlanQty = hostPlanQty;
	}

	/**
	 * Set Work Status
	 * @param arg Work Status
	 */
	public void setSearchStatus(String[] arg)
	{
		wSearchStatus = arg;
	}

	/**
	 * Obtain Work Status
	 * @return Work Status
	 */
	public String[] getSearchStatus()
	{
		return wSearchStatus;
	}

	/**
	 * Set Result Case Qty
	 * @param resultCaseQty
	 */
	public void setResultCaseQty(int resultCaseQty)
	{
		wResultCaseQty = resultCaseQty ;
	}

	/**
	 * Obtain Result Case Qty
	 * @return Result Case Qty
	 */
	public int getResultCaseQty()
	{
		return wResultCaseQty ;
	}


	/**
	 * Set Result Piece Qty
	 * @param resultPieceQty
	 */
	public void setResultPieceQty(int resultPieceQty)
	{
		wResultPieceQty = resultPieceQty ;
	}

	/**
	 * Obtain Result Piece Qty
	 * @return Result Piece Qty
	 */
	public int getResultPieceQty()
	{
		return wResultPieceQty ;
	}

	/**
	 * Set Work Date
	 * @param workDate
	 */
	public void setWorkDate(String workDate)
	{
		wWorkDate = workDate ;
	}

	/**
	 * Obtain Work Date
	 * @return Work Date
	 */
	public String getWorkDate()
	{
		return wWorkDate ;
	}

	/**
	 * Set Start Work Date
	 * @param String fromWorkDate
	 */
	public void setFromWorkDate(String fromWorkDate)
	{
		wFromWorkDate = fromWorkDate ;
	}

	/**
	 * Obtain Start Work Date
	 * @return Start Work Date
	 */
	public String getFromWorkDate()
	{
		return wFromWorkDate ;
	}
	/**
	 * Set End Work Date
	 * @param String toWorkDate
	 */
	public void setToWorkDate(String toWorkDate)
	{
		wToWorkDate = toWorkDate ;
	}

	/**
	 * Obtain End Work Date
	 * @return String End Work Date
	 */
	public String getToWorkDate()
	{
		return wToWorkDate ;
	}
	/**
	 * Set Work Status Flag
	 * @param String workStatus
	 */
	public void setWorkStatus(String workStatus)
	{
		wWorkStatus = workStatus ;
	}

	/**
	 * Obtain Work Status flag
	 * @return String Work Status
	 */
	public String getWorkStatus()
	{
		return wWorkStatus ;
	}

	/**
	 * Set Work status name
	 * @param String workStatus
	 */
	public void setWorkStatusName(String workStatus)
	{
		wWorkStatusName = workStatus ;
	}

	/**
	 * Obtain Work status name
	 * @return String Work status name
	 */
	public String getWorkStatusName()
	{
		return wWorkStatusName ;
	}

	/**
	 * Set Case Location
	 * @param String caseLocation
	 */
	public void setCaseLocation(String caseLocation)
	{
		wCaseLocation = caseLocation ;
	}

	/**
	 * Obtain Case Location
	 * @return String Case Location
	 */
	public String getCaseLocation()
	{
		return wCaseLocation ;
	}

	/**
	 * Set Piece Location
	 * @param String pieceLocation
	 */
	public void setPieceLocation(String pieceLocation)
	{
		wPieceLocation = pieceLocation ;
	}

	/**
	 * Obtain Piece Location
	 * @return String piece location
	 */
	public String getPieceLocation()
	{
		return wPieceLocation ;
	}

	/**
	 * Set the button type.
	 * @param arg Set the button type
	 * @throws InvalidStatusException Announces this when non defined button type is set.
	 */
	public void setButtonType(String arg) throws InvalidStatusException
	{
		if ((arg.equals(BUTTON_MODIFYSUBMIT)) || (arg.equals(BUTTON_ALLWORKINGCLEAR)) || (arg.equals(BUTTON_DELETESUBMIT)))
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

	/**
	 * Obtain the button type.
	 * @return button type
	 */
	public String getButtonType()
	{
		return wButtonType;
	}

	/**
	 * Set the work status (list cell).
	 * @param arg Set theWork Status(list cell)
	 */
	public void setStatusFlagL(String arg)
	{
		wStatusFlagL = arg;
	}

	/**
	 * Obtain the work status (list cell).
	 * @return Work Status(list cell)
	 */
	public String getStatusFlagL()
	{
		return wStatusFlagL;
	}

	/**
	 * Set the status (Inventory Check Work inquiry).
	 * @param arg Status (Inventory Check Work inquiry) to be set
	 */
	public void setDispStatus(String arg)
	{
		wDispStatus = arg;
	}

	/**
	 * Obtain the status (Inventory Check Work inquiry).
	 * @return Status (Inventory Check Work inquiry)
	 */
	public String getDispStatus()
	{
		return wDispStatus;
	}

	/**
	 * Set the work No.
	 * @param Set the Work No.
	 */
	public void setJobNo(String arg)
	{
		wJobNo = arg;
	}

	/**
	 * Obtain Work No.
	 * @return Work No.
	 */
	public String getJobNo()
	{
		return wJobNo;
	}

	/**
	 * Set the stock ID.
	 * @param Set theStock ID
	 */
	public void setStockID(String arg)
	{
		wStockID = arg;
	}

	/**
	 * Obtain the Stock ID.
	 * @return Stock ID
	 */
	public String getStockID()
	{
		return wStockID;
	}

	/**
	 * Set the preset line No.
	 * @param arg Set thePreset line No.
	 */
	public void setRowNo(int arg)
	{
		wRowNo = arg;
	}

	/**
	 * Obtain the preset line No.
	 * @return Preset line No.
	 */
	public int getRowNo()
	{
		return wRowNo;
	}


	/**
	 * Set the Add division.
	 * @param Set theAdd dvision
	 */
	public void setRegistKbn(String arg)
	{
		wRegistKbn = arg;
	}

	/**
	 * Obtain the Add division.
	 * @return Add dvision
	 */
	public String getRegistKbn()
	{
		return wRegistKbn;
	}

	/**
	 * Set the added date/time.
	 * @param Set theAdded Date/Time
	 */
	public void setRegistDate(java.util.Date arg)
	{
		wRegistDate = arg;
	}

	/**
	 * Obtain the added date/time.
	 * @return Added Date/Time
	 */
	public java.util.Date getRegistDate()
	{
		return wRegistDate;
	}

	/**
	 * Set the Add process name.
	 * @param Set theName of Add Process
	 */
	public void setRegistPName(String arg)
	{
		wRegistPName = arg;
	}

	/**
	 * Obtain the Add process name.
	 * @return Name of Add Process
	 */
	public String getRegistPName()
	{
		return wRegistPName;
	}

	/**
	 * Set the last update date/time.
	 * @param Set theLast update date/time
	 */
	public void setLastUpdateDate(java.util.Date arg)
	{
		wLastUpdateDate = arg;
	}

	/**
	 * Obtain Last update date/time
	 * @return Last update date/time
	 */
	public java.util.Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	/**
	 * Set the last update process name.
	 * @param Set theLast update process name
	 */
	public void setLastUpdatePName(String arg)
	{
		wLastUpdatePName = arg;
	}

	/**
	 * Obtain the last update process name.
	 * @return Last update process name
	 */
	public String getLastUpdatePName()
	{
		return wLastUpdatePName;
	}
	/**
	 * Set the total result qty.
	 * @param arg total result qty to be set
	 */
	public void setTotalResultQty(int arg)
	{
		wTotalResultQty = arg;
	}

	/**
	 * Obtain the total result qty.
	 * @return total result qty
	 */
	public int getTotalResultQty()
	{
		return wTotalResultQty;
	}

	/**
	 * Set the result "Report" flag.
	 * @param arg Set the result report flag
	 */
	public void setReportFlag(String arg)
	{
		wReportFlag = arg;
	}

	/**
	 * Obtain the result "Report" flag.
	 * @return result report flag
	 */
	public String getReportFlag()
	{
		return wReportFlag;
	}

	/**
	 * Set the result "Report" flag name.
	 * @param arg Set the result report flag name
	 */
	public void setReportFlagName(String arg)
	{
		wReportFlagName = arg;
	}

	/**
	 * Obtain the result "Report" flag name.
	 * @return result report flag name
	 */
	public String getReportFlagName()
	{
		return wReportFlagName;
	}

	/**
	 * Set the System distinct key.
	 * @param arg Set theSystem distinct key name
	 */
	public void setSystemDiscKey(int arg)
	{
		wSystemDiscKey = arg;
	}

	/**
	 * Obtain the System distinct key.
	 * @return System distinct key name
	 */
	public int getSystemDiscKey()
	{
		return wSystemDiscKey;
	}

	/**
	 * Set the System distinct key name.
	 * @param arg Set the result report flag name
	 */
	public void setSystemDiscKeyName(String arg)
	{
		wSystemDiscKeyName = arg;
	}

	/**
	 * Obtain the System distinct key name.
	 * @return result report flag name
	 */
	public String getSystemDiscKeyName()
	{
		return wSystemDiscKeyName;
	}

	/**
	 * Set the shortage division.
	 * @param arg Shortage division to be set
	 */
	public void setShortage(boolean arg)
	{
		wShortage = arg;
	}

	/**
	 * Obtain the shortage division.
	 * @return shortage division
	 */
	public boolean getShortage()
	{
		return wShortage;
	}

	/**
	 * Obtain the Pending/Additional delivery division.
	 * @return Returns the wRemnantFlag.
	 */
	public boolean getRemnantFlag()
	{
		return wRemnantFlag;
	}
	/**
	 * Set the Pending/Additional delivery division.
	 * @param remnantFlag The wRemnantFlag to set.
	 */
	public void setRemnantFlag(boolean remnantFlag)
	{
		wRemnantFlag = remnantFlag;
	}

	/**
	 * Obtain the storage plan unique key.
	 * @return	Storage Plan unique key
	 */
	public String getStoragePlanUKey()
	{
		return wStoragePlanUKey;
	}

	/**
	 * Set the storage plan unique key.
	 * @param String arg
	 */
	public void setStoragePlanUKey(String arg)
	{
		wStoragePlanUKey = arg;
	}

	/**
	 * Obtain the case location (search conditions).
	 * @return Case Location(search conditions)
	 */
	public String getCaseLocationCondition()
	{
		return wCaseLocationCondition;
	}

	/**
	 * Set the case location (search conditions).
	 * @param arg
	 */
	public void setCaseLocationCondition(String arg)
	{
		wCaseLocationCondition = arg;
	}

	/**
	 * Obtain the piece location (search conditions).
	 * @return Piece Location(search conditions)
	 */
	public String getPieceLocationCondition()
	{
		return wPieceLocationCondition;
	}

	/**
	 * Set the piece location (search conditions).
	 * @param arg Piece Location(search conditions)
	 */
	public void setPieceLocationCondition(String arg)
	{
		wPieceLocationCondition = arg;
	}

	/**
	 * Set the inventory check work presence.
	 * @param arg Inventory check work presence to be set
	 */
	public void setInventoryKind(String arg)
	{
		wInventoryKind = arg;
	}

	/**
	 * Obtain the inventory check work presence.
	 * @return Inventory check work presence
	 */
	public String getInventoryKind()
	{
		return wInventoryKind;
	}

	/**
	 * Set the inventory check work presence identification.
	 * @param arg Inventory check work presence identification to be set
	 */
	public void setInventoryKindName(String arg)
	{
		wInventoryKindName = arg;
	}

	/**
	 * Obtain the inventory check work presence identification.
	 * @return Inventory check work presence identification
	 */
	public String getInventoryKindName()
	{
		return wInventoryKindName;
	}

	/**
	 * Set the shipping Worker code.
	 * @param arg Picking Worker Code to be set
	 */
	public void setRetrievalWorkerCode(String arg)
	{
		wRetrievalWorkerCode = arg;
	}

	/**
	 * Obtain the picking Worker code.
	 * @return Picking Worker Code
	 */
	public String getRetrievalWorkerCode()
	{
		return wRetrievalWorkerCode;
	}

	/**
	 * Set the picking Worker name.
	 * @param arg Picking Worker Name to be set
	 */
	public void setRetrievalWorkerName(String arg)
	{
		wRetrievalWorkerName = arg;
	}

	/**
	 * Obtain the picking Worker name.
	 * @return Picking Worker Name
	 */
	public String getRetrievalWorkerName()
	{
		return wRetrievalWorkerName;
	}

	/**
	 * Set the Relocation work list print division.
	 * @param arg Relocation work list print type to be set
	 */
	public void setMoveWorkListFlag(boolean arg)
	{
		wMoveWorkListFlag = arg;
	}

	/**
	 * Obtain the Relocation work list print division.
	 * @return Relocation work list print type
	 */
	public boolean getMoveWorkListFlag()
	{
		return wMoveWorkListFlag;
	}

	/**
	 * Set the inventory control package print type.
	 * @param arg Inventory control package division to be set
	 */
	public void setStockPackageFlag(boolean arg)
	{
		wStockPackageFlag = arg;
	}

	/**
	 * Obtain the Inventory control package division<BR>
	 * true:With package false:Without package<BR>
	 * @return Inventory control package division
	 */
	public boolean getStockPackageFlag()
	{
		return wStockPackageFlag;
	}

	/**
	 * Set the aggregation work No.
	 * @param arg Aggregation Work No. to be set
	 */
	public void setCollectJobNo(String arg)
	{
		wCollectJobNo = arg;
	}

	/**
	 * Obtain the aggregation work No. <BR>
	 * @return Aggregation Work No.
	 */
	public String getCollectJobNo()
	{
		return wCollectJobNo;
	}

	/**
	 * Set the expiry date control flag.
	 * @param arg Expiry Date control flag to be set
	 */
	public void setUseByDateFlag(boolean arg)
	{
		wUseByDateFlag = arg;
	}

	/**
	 * Obtain the expiry date control flag.
	 * @return Expiry Date control flag
	 */
	public boolean getUseByDateFlag()
	{
		return wUseByDateFlag;
	}

    /**
     * Set the work No. (Vector).
     * @param arg[] Work No.(Vector) to be set
     */
    public void setJobNoList(Vector arg)
    {
    	wJobNoList = arg;
    }

    /**
     * Obtain the work No. (Vector).
     * @return Work No.(Vector)
     */
    public Vector getJobNoList()
    {
    	return wJobNoList;
    }

    /**
     * Set the last update date/time (Vector).
     * @param arg Last update date/time(Vector) to be set
     */
    public void setLastUpdateDateList(Vector arg)
    {
		wLastUpdateDateList = arg;
    }

    /**
     * Obtain the last update date/time (Vector).
     * @return Last update date/time(Vector)
     */
    public Vector getLastUpdateDateList()
    {
    	return wLastUpdateDateList;
    }
	/**
	 * Obtain the storage date.
	 * @return Storage Date
	 */
	public String getStorageDate()
	{
		return wStorageDate;
	}

	/**
	 * Set the storage date.
	 * @param arg Storage Date to be set
	 */
	public void setStorageDate(String arg)
	{
		wStorageDate = arg;
	}

	/**
	 * Obtain Batch No.(Schedule No.)
	 * @return Returns the wBatchNo.
	 */
	public String getBatchNo()
	{
		return wBatchNo;
	}

	/**
	 * Set the batch No. ( schedule No.).
	 * @param batchNo The wBatchNo to set.
	 */
	public void setBatchNo(String batchNo)
	{
		wBatchNo = batchNo;
	}

	/**
	 * Obtain Add dvision(for repeated search)
	 * @return Returns the wBatchNo.
	 *
	 */
	public String getRegistKbnCondition()
	{
		return wRegistKbnCondition;
	}

	/**
	 * Set the Add division (for repeated search).
	 * @param batchNo The wBatchNo to set.
	 */
	public void setRegistKndCondition(String registKind)
	{
		wRegistKbnCondition = registKind;
	}

	 /**
	  * Obtain the Supplier code.
	  * @return Returns the wSupplierCode
	  */
	 public String getSupplierCode()
	 {
		 return wSupplierCode;
	 }

	 /**
	  * Set the Supplier code.
	  * @param supplierCode The wSupplierCode to set.
	  */
	 public void setSupplierCode(String supplierCode)
	 {
		 wSupplierCode = supplierCode;
	 }

	/**
	 * Obtain the Supplier name.
	 * @return Returns the wSupplierCode
	 */
	public String getSupplierName1()
	{
		return wSupplierName1;
	}

	/**
	 * Set the Supplier name.
	 * @param supplierName1 The wSupplierName1 to set.
	 */
	public void setSupplierName1(String supplierName1)
	{
		wSupplierName1 = supplierName1;
	}

	/**
	 * Obtain the area type flag.
	 * @return Returns the wAreaTypeFlag
	 */
	public String getAreaTypeFlag()
	{
		return wAreaTypeFlag;
	}

	/**
	 * Set the area type flag.
	 * @param supplierName1 The wSupplierName1 to set.
	 */
	public void setAreaTypeFlag(String flag)
	{
		wAreaTypeFlag = flag;
	}
	/**
	 * Set the picking area.
	 * @param arg Area to be set
	 */
	public void setRetrievalArea(String arg)
	{
		wRetrievalArea = arg ;
	}

	/**
	 * Obtain the picking area.
	 * @return Area
	 */
	public String getRetrievalArea()
	{
		return wRetrievalArea;
	}

	/**
	 * Set the area name.
	 * @param arg Area Name to be set
	 */
	public void setRetrievalAreaName(String arg)
	{
		wRetrievalAreaName = arg ;
	}

	/**
	 * Obtain the area name.
	 * @return Area Name
	 */
	public String getRetrievalAreaName()
	{
		return wRetrievalAreaName;
	}

	/**
     * Obtain the check presence flag for number of digits.
     * @return Returns the wLineCheckFlag.
     */
    public boolean getLineCheckFlag()
    {
        return wLineCheckFlag;
    }

    /**
     * Set the "number of digits" check presence flag.
     * @param lineCheckFlag The wLineCheckFlag to set.
     */
    public void setLineCheckFlag(boolean lineCheckFlag)
    {
        wLineCheckFlag = lineCheckFlag;
    }

}

