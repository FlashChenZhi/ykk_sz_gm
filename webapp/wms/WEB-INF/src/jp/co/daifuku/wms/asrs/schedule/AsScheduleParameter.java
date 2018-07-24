// $Id: AsScheduleParameter.java,v 1.2 2006/10/30 00:53:01 suresh Exp $

//#CM44961
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import jp.co.daifuku.wms.base.common.Parameter;

//#CM44962
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Use the <CODE>AsScheduleParameter</CODE> Class to hand over parameter between schedules screen-in the ASRS package. <BR>
 * Maintain the item used on each screen in the ASRS package in this Class. The item used is different according to the screen. <BR>
 * <BR>
 * <CODE>AsScheduleParameter</CODE>Parameter to store class<BR>
 * <DIR>
 *     Worker code <BR>
 *     Password <BR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece flag <BR>
 *     Qty per case <BR>
 *     Qty per bundle <BR>
 *     Storage Case qty <BR>
 *     Storage Piece qty <BR>
 *     Retrieval Case qty <BR>
 *     Retrieval Piece qty <BR>
 *     Work plan Case qty <BR>
 *     Work plan Piece qty <BR>
 *     Stock case qty <BR>
 *     Stock piece qty <BR>
 *     Possible drawing case qty <BR>
 *     Possible drawing piece qty <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry date <BR>
 *     ListPrintFlag <BR>
 *     Area No. <BR>
 *     Shelf No.. <BR>
 *     Beginning shelfNo. <BR>
 *     End shelfNo. <BR>
 *     BankNo. <BR>
 *     BayNo. <BR>
 *     LevelNo. <BR>
 *     StartBankNo. <BR>
 *     StartBayNo. <BR>
 *     StartLevelNo. <BR>
 *     EndBankNo. <BR>
 *     EndBayNo. <BR>
 *     EndLevelNo. <BR>
 *     Shipper code <BR>
 *     Shipper name <BR>
 *     All qty Flag <BR>
 *     Filtered Line No. <BR>
 *     Stock ID <BR>
 *     Last updated date and time <BR>
 *     Stock qty  <BR>
 *     Drawing qty <BR>
 *     Warehouse <BR>
 *     Hard Zone(Mode of packing) <BR>
 *     Workshop <BR>
 *     Station No <BR>
 *     Plan Date <BR>
 *     Plan unique key <BR>
 *     Batch No. <BR>
 *     Order No. <BR>
 *     Start Order No. <BR>
 *     End Order No. <BR>
 *     Start Item Code <BR>
 *     End Item Code <BR>
 *     Delivery total <BR>
 *     Drawing object keeping place <BR>
 *     Shortage Authorisation <BR>
 *     Keeping place selection <BR>
 *     ListPrint demand <BR>
 *     Process Flag <BR>
 *     Lot No. <BR>
 *     WorkshopName <BR>
 *     Button Type <BR>
 *     Location Status selection <BR>
 *     Location Status Name <BR>
 *     Storage Flag <BR>
 *     Storage date <BR>
 *     Storage time <BR>
 *     WarehouseName <BR>
 *     AGC-No <BR>
 *     System Status <BR>
 *     Work qty <BR>
 *     Selection mode <BR>
 *     Operation mode selection <BR>
 *     Work mode selection <BR>
 *     StationName(For display) <BR>
 *     Status of transportation <BR>
 *     Work status <BR>
 *     Work mode switch request <BR>
 *     Operation mode <BR>
 *     Work mode <BR>
 *     Status of transportationName(For display) <BR>
 *     Work statusName(For display) <BR>
 *     Work mode switch requestName(For display) <BR>
 *     Operation modeName(For display) <BR>
 *     Work modeName(For display) <BR>
 *     Machine Status (For display) <BR>
 *     RMTitle machine No <BR>
 *     Actual Location Qty <BR>
 *     Empty Location Qty <BR>
 *     Empty PB Location Qty <BR>
 *     Abnormal Location Qty <BR>
 *     Restricted Location Qty <BR>
 *     Total Location Qty <BR>
 *     Storage rate <BR>
 *     Work No <BR>
 *     Work type <BR>
 *     The delivery instruction is detailed.  <BR>
 *     The delivery instruction is detailed. (For display) <BR>
 *     Transportation originStation No <BR>
 *     Transportation originStationName <BR>
 *     At the transportation destinationStation No <BR>
 *     At the transportation destinationStationName <BR>
 *     Work Flag <BR>
 *     Work FlagName <BR>
 *     Work type <BR>
 *     Work typeName <BR>
 *     Transportation Key <BR>
 *     Start day <BR>
 *     Start time <BR>
 *     End day <BR>
 *     End time <BR>
 *     Total Operation Qty <BR>
 *     Storage Operation Qty <BR>
 *     Picking Operation Qty <BR>
 *     Storage Item Qty <BR>
 *     Picking Item Qty <BR>
 *     Consignor Code(For display) <BR>
 *     Item Code(For display) <BR>
 *     CasePieceFlag(For display) <BR>
 *     Case/Piece flagName(For display)<BR>
 *     Case ITF(For display) <BR>
 *     Bundle ITF(For display) <BR>
 *     Expiry date(For display) <BR>
 *     Transportation route(For display) <BR>
 *     Transportation Flag <BR>
 *     Transportation Flag(For display) <BR>
 *     Work displayType <BR>
 *     Priority Flag(For display) <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/02</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:53:01 $
 * @author  $Author: suresh $
 */
public class AsScheduleParameter extends Parameter
{
	//#CM44963
	// Class feilds ------------------------------------------------
	//#CM44964
	/**
	 * Case/Piece flag(All)
	 */
	public static final String CASEPIECE_FLAG_ALL = "99";

	//#CM44965
	/**
	 * Case/Piece flag(Case)
	 */
	public static final String CASEPIECE_FLAG_CASE = "1";

	//#CM44966
	/**
	 * Case/Piece flag(Piece)
	 */
	public static final String CASEPIECE_FLAG_PIECE = "2";

	//#CM44967
	/**
	 * Case/Piece flag(Unspecified)
	 */
	public static final String CASEPIECE_FLAG_NOTHING = "3";

	//#CM44968
	/**
	 * Selection Box ON/OFF(ON)
	 */
	public static final boolean SELECT_BOX_ON = true;

	//#CM44969
	/**
	 * Selection Box ON/OFF(OFF)
	 */
	public static final boolean SELECT_BOX_OFF = false;

	//#CM44970
	/**
	 * Range flag(Start)
	 */
	public static final String RANGE_START = "0";

	//#CM44971
	/**
	 * Range flag(End)
	 */
	public static final String RANGE_END = "1";

	//#CM44972
	/**
	 * Retrieval flag(Inventory information)
	 */
	public static final String SEARCHFLAG_STOCK = "1";

	//#CM44973
	/**
	 * Retrieval flag(Work information)
	 */
	public static final String SEARCHFLAG_WORKINFO = "2";

	//#CM44974
	/**
	 * Retrieval flag(Picking Plan Information)
	 */
	public static final String SEARCHFLAG_RETRIEVALPLAN = "3";

	//#CM44975
	/**
	 * Batch No.
	 */
	public static boolean wBachino_flag = false  ;

	//#CM44976
	/**
	 * Work mode(Start)
	 */
	public static final String ASRS_WORKING_START = "0";

	//#CM44977
	/**
	 * Work mode(End)
	 */
	public static final String ASRS_WORKING_END = "1";

	//#CM44978
	/**
	 * Work mode(End:Data maintenance)
	 */
	public static final String ASRS_WORKING_END_DATAKEEP = "4";

	//#CM44979
	/**
	 * Work mode(Force Termination)
	 */
	public static final String ASRS_WORKING_ONLYEND = "5";

	//#CM44980
	/**
	 * Location Status (Can be used)
	 */
	public static final String ASRS_LOCATIONSTATUS_POSSIBLE = "0";

	//#CM44981
	/**
	 * Location Status (Restricted)
	 */
	public static final String ASRS_LOCATIONSTATUS_PROHIBITION = "1";

	//#CM44982
	/**
	 * Transportation Flag(Storage)
	 */
	public static final int CARRY_KIND_STORAGE = 1;

	//#CM44983
	/**
	 * Transportation Flag(Picking)
	 */
	public static final int CARRY_KIND_RETRIEVAL = 2;

	//#CM44984
	/**
	 * Transportation Flag(Going directly)
	 */
	public static final int CARRY_KIND_DIRECT_TRAVEL = 3;

	//#CM44985
	/**
	 * Transportation Flag(Rack to rack movement)
	 */
	public static final int CARRY_KIND_RACK_TO_RACK = 5;

	//#CM44986
	/**
	 * Work displayType(No Work display screen)
	 */
	public static final int OPERATION_DISPLAY_NO = 0;

	//#CM44987
	/**
	 * Work displayType(Work display operation, End Button Not available)
	 */
	public static final int OPERATION_DISP_ONLY = 1;

	//#CM44988
	/**
	 * Work displayType(Work display operation, End Button Available)
	 */
	public static final int OPERATION_INSTRUCTION = 2;
	
	//#CM44989
	/**
	 * Button Type(End)
	 */
	public static final String BUTTON_COMPLETE = "0";
	
	//#CM44990
	/**
	 * Button Type(Disbursement End)
	 */
	public static final String BUTTON_UNIT_COMPLETE =  "1";

	//#CM44991
	/**
	 * Field actual location which shows Location Status
	 */
	public static final int STATUS_STORAGED = 0;

	//#CM44992
	/**
	 * Field empty location which shows Location Status
	 */
	public static final int STATUS_EMPTY = 1;

	//#CM44993
	/**
	 * Field abnormal location which shows Location Status
	 */
	public static final int STATUS_IRREGULAR = 2;

	//#CM44994
	/**
	 * Field work location which shows Location Status
	 */
	public static final int STATUS_WORK = 3;

	//#CM44995
	/**
	 * Field empty palette which shows Location Status
	 */
	public static final int STATUS_EMPTYPALETTE = 4;

	//#CM44996
	/**
	 * Field restricted location which shows Location Status
	 */
	public static final int STATUS_UNAVAILABLE = 5;

	//#CM44997
	/**
	 * The delivery instruction is detailed. :Stock confirmation
	 */
	public static final String RETRIEVALDETAIL_INVENTORY_CHECK = "0";

	//#CM44998
	/**
	 * The delivery instruction is detailed. :Unit Picking
	 */
	public static final String RETRIEVALDETAIL_UNIT = "1";

	//#CM44999
	/**
	 * The delivery instruction is detailed. :Partial Picking
	 */
	public static final String RETRIEVALDETAIL_PICKING = "2";

	//#CM45000
	/**
	 * The delivery instruction is detailed. :Additional Storage
	 */
	public static final String RETRIEVALDETAIL_ADD_STORING = "3";

	//#CM45001
	/**
	 * The delivery instruction is detailed. :Unspecified
	 */
	public static final String RETRIEVALDETAIL_UNKNOWN = "9";

	//#CM45002
	// Class variables -----------------------------------------------
	//#CM45003
	/**
	 * Worker code
	 */
	private String wWorkerCode;

	//#CM45004
	/**
	 * Password
	 */
	private String wPassword;

	//#CM45005
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM45006
	/**
	 * Consignor Name
	 */
	private String wConsignorName;

	//#CM45007
	/**
	 * Item Code
	 */
	private String wItemCode;

	//#CM45008
	/**
	 * Item Name
	 */
	private String wItemName;

	//#CM45009
	/**
	 * Case/Piece flag
	 */
	private String wCasePieceFlag;

	//#CM45010
	/**
	 * Qty per case
	 */
	private int wEnteringQty;

	//#CM45011
	/**
	 * Qty per bundle
	 */
	private int wBundleEnteringQty;

	//#CM45012
	/**
	 * Storage Case qty
	 */
	private int wStorageCaseQty;

	//#CM45013
	/**
	 * Storage Piece qty
	 */
	private int wStoragePieceQty;

	//#CM45014
	/**
	 * Retrieval Case qty
	 */
	private int wRetrievalCaseQty;

	//#CM45015
	/**
	 * Retrieval Piece qty
	 */
	private int wRetrievalPieceQty;

	//#CM45016
	/**
	 * Work plan Case qty
	 */
	private int wPlanCaseQty;

	//#CM45017
	/**
	 * Work plan Piece qty
	 */
	private int wPlanPieceQty;

	//#CM45018
	/**
	 * Stock case qty
	 */
	private int wStockCaseQty;
	
	//#CM45019
	/**
	 * Stock piece qty
	 */
	private int wStockPieceQty;

	//#CM45020
	/**
	 * Possible drawing case qty
	 */
	private int wAllocateCaseQty;

	//#CM45021
	/**
	 * Possible drawing piece qty
	 */
	private int wAllocatePieceQty;

	//#CM45022
	/**
	 * Case ITF
	 */
	private String wITF;

	//#CM45023
	/**
	 * Bundle ITF
	 */
	private String wBundleITF;

	//#CM45024
	/**
	 * Expiry date
	 */
	private String wUseByDate;

	//#CM45025
	/**
	 * ListPrintFlag
	 */
	private boolean wListFlg = false;

	//#CM45026
	/**
	 * Area No.
	 */
	private String wAreaNo;

	//#CM45027
	/**
	 * Shelf No..
	 */
	private String wLocationNo;

	//#CM45028
	/**
	 * Beginning shelfNo.
	 */
	private String wFromLocationNo;

	//#CM45029
	/**
	 * End shelfNo.
	 */
	private String wToLocationNo;

	//#CM45030
	/**
	 * BankNo.
	 */
	private String wBankNo;
	
	//#CM45031
	/**
	 * Array of location information
	 */
	private String[] wBankNoArray;	

	//#CM45032
	/**
	 * BayNo.
	 */
	private String wBayNo;

	//#CM45033
	/**
	 * LevelNo.
	 */
	private String wLevelNo;

	//#CM45034
	/**
	 * StartBankNo.
	 */
	private String wStartBankNo;

	//#CM45035
	/**
	 * StartBayNo.
	 */
	private String wStartBayNo;

	//#CM45036
	/**
	 * StartLevelNo.
	 */
	private String wStartLevelNo;

	//#CM45037
	/**
	 * EndBankNo.
	 */
	private String wEndBankNo;

	//#CM45038
	/**
	 * EndBayNo.
	 */
	private String wEndBayNo;

	//#CM45039
	/**
	 * EndLevelNo.
	 */
	private String wEndLevelNo;

	//#CM45040
	/**
	 * Shipper code
	 */
	private String wCustomerCode;

	//#CM45041
	/**
	 * Shipper name
	 */
	private String wCustomerName;

	//#CM45042
	/**
	 * All qty Flag
	 */
	private boolean wTotalFlag;

	//#CM45043
	/**
	 * Filtered Line No.
	 */
	private int wRowNo;

	//#CM45044
	/**
	 * Stock ID
	 */
	private String wStockId;

	//#CM45045
	/**
	 * Last updated date and time
	 */
	private java.util.Date wLastUpdateDate;

	//#CM45046
	/**
	 * Storage date & time
	 */
	private java.util.Date wInStockDate;

	//#CM45047
	/**
	 * Stock qty 
	 */
    private int wStockQty ;
    
    //#CM45048
    /**
     * Drawing qty
     */
    private int wAllocateQty ;

	//#CM45049
	/**
	 * Warehouse
	 */
	private String wWareHouseNo;

	//#CM45050
	/**
	 * Hard Zone(Mode of packing)
	 */
	private String wHardZone;

	//#CM45051
	/**
	 * Workshop
	 */
	private String wSagyoba;

	//#CM45052
	/**
	 * Station No
	 */
	private String wStationNo;

	//#CM45053
	/**
	 * Plan Date
	 */
	private String wPlanDate;

	//#CM45054
	/**
	 * Plan unique key
	 */
	private String wPlanUkey;

	//#CM45055
	/**
	 * Batch No.
	 */
	private String wBatchNo;

	//#CM45056
	/**
	 * Order No.
	 */
	private String wOrderNo;

	//#CM45057
	/**
	 * Start Order No.
	 */
	private String wStartOrderNo;

	//#CM45058
	/**
	 * End Order No.
	 */
	private String wEndOrderNo;

	//#CM45059
	/**
	 * Start Item Code
	 */
	private String wStartItemCode;

	//#CM45060
	/**
	 * End Item Code
	 */
	private String wEndItemCode;

	//#CM45061
	/**
	 * Delivery total
	 */
	private int wTotalRetrievalQty;

	//#CM45062
	/**
	 * Drawing object keeping place
	 */
	private String[] wAllocateWHNo;

	//#CM45063
	/**
	 * Shortage Authorisation
	 */
	private String wShortageFlag;

	//#CM45064
	/**
	 * Keeping place selection
	 */
	private String wSelectWHNo;

	//#CM45065
	/**
	 * ListPrint demand
	 */
	private String wListRequestFlag;

	//#CM45066
	/**
	 * Process Flag
	 */
	private String wProcessStatus;

	//#CM45067
	/**
	 * Lot No.
	 */
	private String wLotNo;

	//#CM45068
	/**
	 * WorkshopName
	 */
	private String wWorkingPlaceName;

	//#CM45069
	/**
	 * Button Type
	 */
	private String wButtonType;

	//#CM45070
	/**
	 * Location Status selection
	 */
	private String wSelectLocationStatus;

	//#CM45071
	/**
	 * Location Status Name
	 */
	private String wLocationStatusName;

	//#CM45072
	/**
	 * Storage Flag
	 */
	private String wStoringStatus;

	//#CM45073
	/**
	 * Storage date
	 */
	private String wStoringDate;

	//#CM45074
	/**
	 * Storage time
	 */
	private String wStoringTime;

	//#CM45075
	/**
	 * WarehouseName
	 */
	private String wWareHouseName;

	//#CM45076
	/**
	 * AGC-No
	 */
	private String wAgcNo;

	//#CM45077
	/**
	 * System Status
	 */
	private String wSystemStatus;

	//#CM45078
	/**
	 * Work qty
	 */
	private int wWorkingCount;

	//#CM45079
	/**
	 * Selection mode
	 */
	private String wSelectMode;

	//#CM45080
	/**
	 * Operation mode selection
	 */
	private String wSelectModeType;

	//#CM45081
	/**
	 * Work mode selection
	 */
	private String wSelectCurrentMode;

	//#CM45082
	/**
	 * StationName(For display)
	 */
	private String wDispStationName;

	//#CM45083
	/**
	 * Status of transportation
	 */
	private String wCarringStatus;

	//#CM45084
	/**
	 * Work status
	 */
	private String wWorkingStatus;

	//#CM45085
	/**
	 * Work mode switch request
	 */
	private String wWorkingModeRequest;

	//#CM45086
	/**
	 * Operation mode
	 */
	private String wModeType;

	//#CM45087
	/**
	 * Work mode
	 */
	private String wCurrentMode;

	//#CM45088
	/**
	 * Status of transportationName(For display)
	 */
	private String wDispCarringStatusName;

	//#CM45089
	/**
	 * Work statusName(For display)
	 */
	private String wDispWorkingStatusName;

	//#CM45090
	/**
	 * Work mode switch requestName(For display)
	 */
	private String wDispWorkingModeRequestName;

	//#CM45091
	/**
	 * Operation modeName(For display)
	 */
	private String wDispModeTypeName;

	//#CM45092
	/**
	 * Work modeName(For display)
	 */
	private String wDispCurrentModeName;

	//#CM45093
	/**
	 * Machine Status (For display)
	 */
	private String wDispControllerStatusName;

	//#CM45094
	/**
	 * RMTitle machine No
	 */
	private String wRackmasterNo;

	//#CM45095
	/**
	 * Actual Location Qty
	 */
	private int wRealLocationCount;

	//#CM45096
	/**
	 * Empty Location Qty
	 */
	private int wVacantLocationCount;

	//#CM45097
	/**
	 * Empty PB Location Qty
	 */
	private int wEmptyPBLocationCount;

	//#CM45098
	/**
	 * Abnormal Location Qty
	 */
	private int wAbnormalLocationCount;

	//#CM45099
	/**
	 * Restricted Location Qty
	 */
	private int wProhibitionLocationCount;

	
	//#CM45100
	/**
	 * Inaccessible location qty
	 */
	private int wNotAccessLocationCount;
	
	
	//#CM45101
	/**
	 * Total Location Qty
	 */
	private int wTotalLocationCount;

	//#CM45102
	/**
	 * Storage rate
	 */
	private String wLocationRate;

	//#CM45103
	/**
	 * Work No
	 */
	private String wWorkingNo;

	//#CM45104
	/**
	 * Work type
	 */
	private String wWorkingType;

	//#CM45105
	/**
	 * The delivery instruction is detailed. 
	 */
	private String wRetrievalDetail;

	//#CM45106
	/**
	 * The delivery instruction is detailed. (For display)
	 */
	private String wDispRetrievalDetail;

	//#CM45107
	/**
	 * Transportation originStation No
	 */
	private String wFromStationNo;

	//#CM45108
	/**
	 * Transportation originStationName
	 */
	private String wFromStationName;

	//#CM45109
	/**
	 * At the transportation destinationStation No
	 */
	private String wToStationNo;

	//#CM45110
	/**
	 * At the transportation destinationStationName
	 */
	private String wToStationName;

	//#CM45111
	/**
	 * Work Flag
	 */
	private String wJobType;

	//#CM45112
	/**
	 * Work FlagName
	 */
	private String wJobTypeName;

	//#CM45113
	/**
	 * Work type(For display)
	 */
	private String wDispWorkType;

	//#CM45114
	/**
	 * Transportation Key
	 */
	private String wCarryKey;

	//#CM45115
	/**
	 * Start day
	 */
	private String wStartDate;

	//#CM45116
	/**
	 * Start time
	 */
	private String wStartTime;

	//#CM45117
	/**
	 * End day
	 */
	private String wEndDate;

	//#CM45118
	/**
	 * End time
	 */
	private String wEndTime;

	//#CM45119
	/**
	 * Total Operation Qty
	 */
	private int wTotalInOutResultCount;

	//#CM45120
	/**
	 * Storage Operation Qty
	 */
	private int wStorageResultCount;

	//#CM45121
	/**
	 * Picking Operation Qty
	 */
	private int wRetrievalResultCount;

	//#CM45122
	/**
	 * Storage Item Qty
	 */
	private int wItemStorageCount;

	//#CM45123
	/**
	 * Picking Item Qty
	 */
	private int wItemRetrievalCount;
   
	//#CM45124
	/**
	 * Consignor Code(For display)
	 */
	private String wConsignorCodeDisp ;
   
	//#CM45125
	/**
	 * Consignor Name(For display)
	 */
	private String wConsignorNameDisp ;
   
	//#CM45126
	/**
	 * Item Code(For display)
	 */
	private String wItemCodeDisp ;
    
	//#CM45127
	/**
	 * Item Name(For display)
	 */
	private String wItemNameDisp ;
    
	//#CM45128
	/**
	 * CasePieceFlag(For display)
	 */
	private String wCasePieceFlagDisp ;

	//#CM45129
	/**
	 * Case/Piece flagName(For display)
	 */
	private String wCasePieceFlagNameDisp;
    
	//#CM45130
	/**
	 * Case ITF(For display)
	 */
	private String wITFDisp ;
    
	//#CM45131
	/**
	 * Bundle ITF(For display)
	 */
	private String wBundleITFDisp ;

	//#CM45132
	/**
	 * Expiry date(For display)
	 */
	private String wUseByDateDisp;

	//#CM45133
	/**
	 * Transportation route(For display)
	 */
	private String wDispCarringRoute;

	//#CM45134
	/**
	 * Transportation Flag
	 */
	private int wCarryKind;
	
	//#CM45135
	/**
	 * Transportation Flag(For display)
	 */
	private String wDispCarryKind;
	
	//#CM45136
	/**
	 * Work displayType
	 */
	private int wOperationDisplay;
	
	//#CM45137
	/**
	 * RMNo.(Array)
	 */
	private String[] wRackmasterNoArray;
	
	//#CM45138
	/**
	* Aisle Station No.
	*/
	private String wAisleStationNumber = "";
 
	//#CM45139
	/**
	 * Status Array
	 */
	private String[] wSearchStatus;

	//#CM45140
	/**
	 * Status 
	 */
	private String wStatusFlag;

	//#CM45141
	/**
	 * Start day & time
	 */
	private java.util.Date wFromDate;

	//#CM45142
	/**
	 * End day & time
	 */
	private java.util.Date wToDate;

	//#CM45143
	/**
	 * Number of total operation Results
	 */
	private long wTotalInOutResultQty;

	//#CM45144
	/**
	 * Priority Flag(For display)
	 */
	private String wDispPriority;

	//#CM45145
	// Class method --------------------------------------------------
	//#CM45146
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.2 $,$Date: 2006/10/30 00:53:01 $" );
	}

	//#CM45147
	// Constructors --------------------------------------------------
	//#CM45148
	/**
	 * Initialize this Class. 
	 */
	public AsScheduleParameter()
	{
	}

	//#CM45149
	// Public methods ------------------------------------------------
	//#CM45150
	/**
	 * Set Worker code.
	 * @param arg Worker code to be Set.
	 */
	public void setWorkerCode(String arg)
	{
		wWorkerCode = arg;
	}

	//#CM45151
	/**
	 * Acquire Worker code.
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM45152
	/**
	 * Set Password.
	 * @param arg Password to be Set.
	 */
	public void setPassword(String arg)
	{
		wPassword = arg;
	}

	//#CM45153
	/**
	 * Acquire Password.
	 * @return Password
	 */
	public String getPassword()
	{
		return wPassword;
	}

	//#CM45154
	/**
	 * Set Consignor Code.
	 * @param arg Consignor Code to be set
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg;
	}

	//#CM45155
	/**
	 * Acquire Consignor Code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM45156
	/**
	 * Set Consignor Name.
	 * @param arg Consignor Name to be set.
	 */
	public void setConsignorName(String arg)
	{
		wConsignorName = arg;
	}

	//#CM45157
	/**
	 * Acquire Consignor Name.
	 * @return Consignor Name
	 */
	public String getConsignorName()
	{
		return wConsignorName;
	}

	//#CM45158
	/**
	 * Set Item Code..
	 * @param arg Item Code to be Set.
	 */
	public void setItemCode(String arg)
	{
		wItemCode = arg;
	}

	//#CM45159
	/**
	 * Acquire Item Code.
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM45160
	/**
	 * Set Item Name..
	 * @param arg Item Name to be Set.
	 */
	public void setItemName(String arg)
	{
		wItemName = arg;
	}

	//#CM45161
	/**
	 * Acquire Item Name.
	 * @return Item Name
	 */
	public String getItemName()
	{
		return wItemName;
	}

	//#CM45162
	/**
	 * Set Case/Piece flag.
	 * @param arg Case/Piece flag to be Set.
	 */
	public void setCasePieceFlag(String arg)
	{
		wCasePieceFlag = arg;
	}

	//#CM45163
	/**
	 * Get Case/Piece flag.
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	//#CM45164
	/**
	 * Set Qty per case.
	 * @param arg Qty per case to be Set.
	 */
	public void setEnteringQty(int arg)
	{
		wEnteringQty = arg;
	}

	//#CM45165
	/**
	 * Acquire Qty per case.
	 * @return Qty per case
	 */
	public int getEnteringQty()
	{
		return wEnteringQty;
	}

	//#CM45166
	/**
	 * Set Qty per bundle.
	 * @param arg Qty per bundle to be set.
	 */
	public void setBundleEnteringQty(int arg)
	{
		wBundleEnteringQty = arg;
	}

	//#CM45167
	/**
	 * Acquire Qty per bundle.
	 * @return Qty per bundle
	 */
	public int getBundleEnteringQty()
	{
		return wBundleEnteringQty;
	}

	//#CM45168
	/**
	 * Set Storage Case qty.
	 * @param arg Storage Case qty to be set.
	 */
	public void setStorageCaseQty(int arg)
	{
		wStorageCaseQty = arg;
	}

	//#CM45169
	/**
	 * Acquire Storage Case qty.
	 * @return Storage Case qty
	 */
	public int getStorageCaseQty()
	{
		return wStorageCaseQty;
	}

	//#CM45170
	/**
	 * Set Storage Piece qty.
	 * @param arg Storage Piece qty to be Set.
	 */
	public void setStoragePieceQty(int arg)
	{
		wStoragePieceQty = arg;
	}

	//#CM45171
	/**
	 * Acquire Storage Piece qty.
	 * @return Storage Piece qty
	 */
	public int getStoragePieceQty()
	{
		return wStoragePieceQty;
	}

	//#CM45172
	/**
	 * Set Retrieval Case qty.
	 * @param arg Retrieval Case qty to be Set.
	 */
	public void setRetrievalCaseQty(int arg)
	{
		wRetrievalCaseQty = arg;
	}

	//#CM45173
	/**
	 * Acquire Retrieval Case qty.
	 * @return Retrieval Case qty
	 */
	public int getRetrievalCaseQty()
	{
		return wRetrievalCaseQty;
	}

	//#CM45174
	/**
	 * Set Retrieval Piece qty.
	 * @param arg Retrieval Piece qty to be set.
	 */
	public void setRetrievalPieceQty(int arg)
	{
		wRetrievalPieceQty = arg;
	}

	//#CM45175
	/**
	 * Acquire Retrieval Piece qty.
	 * @return Retrieval Piece qty
	 */
	public int getRetrievalPieceQty()
	{
		return wRetrievalPieceQty;
	}

	//#CM45176
	/**
	 * Set Work plan Case qty.
	 * @param arg Work plan Case qty to be set.
	 */
	public void setPlanCaseQty(int arg)
	{
		wPlanCaseQty = arg;
	}

	//#CM45177
	/**
	 * Acquire Work plan Case qty.
	 * @return Work plan Case qty
	 */
	public int getPlanCaseQty()
	{
		return wPlanCaseQty;
	}

	//#CM45178
	/**
	 * Set Work plan Piece qty.
	 * @param arg Work plan Piece qty to be Set.
	 */
	public void setPlanPieceQty(int arg)
	{
		wPlanPieceQty = arg;
	}

	//#CM45179
	/**
	 * Acquire Work plan Piece qty.
	 * @return Work plan Piece qty
	 */
	public int getPlanPieceQty()
	{
		return wPlanPieceQty;
	}

	//#CM45180
	/**
	 * Set Stock case qty.
	 * @param arg Stock case qty to be Set.
	 */
	public void setStockCaseQty(int arg)
	{
		wStockCaseQty = arg;
	}

	//#CM45181
	/**
	 * Acquire Stock case qty.
	 * @return Stock case qty
	 */
	public int getStockCaseQty()
	{
		return wStockCaseQty;
	}

	//#CM45182
	/**
	 * Set Stock piece qty.
	 * @param arg Stock piece qty to be set.
	 */
	public void setStockPieceQty(int arg)
	{
		wStockPieceQty = arg;
	}

	//#CM45183
	/**
	 * Acquire Stock piece qty.
	 * @return Stock piece qty
	 */
	public int getStockPieceQty()
	{
		return wStockPieceQty;
	}

	//#CM45184
	/**
	 * Set Possible drawing case qty.
	 * @param arg Possible drawing case qty to be Set.
	 */
	public void setAllocateCaseQty(int arg)
	{
		wAllocateCaseQty = arg;
	}

	//#CM45185
	/**
	 * Acquire Possible drawing case qty.
	 * @return Possible drawing case qty
	 */
	public int getAllocateCaseQty()
	{
		return wAllocateCaseQty;
	}

	//#CM45186
	/**
	 * Set Possible drawing piece qty.
	 * @param arg Possible drawing piece qty to be Set.
	 */
	public void setAllocatePieceQty(int arg)
	{
		wAllocatePieceQty = arg;
	}

	//#CM45187
	/**
	 * Acquire Possible drawing piece qty.
	 * @return Possible drawing piece qty
	 */
	public int getAllocatePieceQty()
	{
		return wAllocatePieceQty;
	}

	//#CM45188
	/**
	 * Set Case ITF.
	 * @param arg Case ITF to be set.
	 */
	public void setITF(String arg)
	{
		wITF = arg;
	}

	//#CM45189
	/**
	 * Acquire Case ITF.
	 * @return Case ITF
	 */
	public String getITF()
	{
		return wITF;
	}

	//#CM45190
	/**
	 * Set Bundle ITF.
	 * @param arg Bundle ITF to be set.
	 */
	public void setBundleITF(String arg)
	{
		wBundleITF = arg;
	}

	//#CM45191
	/**
	 * Acquire Bundle ITF.
	 * @return Bundle ITF
	 */
	public String getBundleITF()
	{
		return wBundleITF;
	}

	//#CM45192
	/**
	 * Set Expiry date.
	 * @param arg Expiry date to be set.
	 */
	public void setUseByDate(String arg)
	{
		wUseByDate = arg;
	}

	//#CM45193
	/**
	 * Acquire Expiry date.
	 * @return Expiry date
	 */
	public String getUseByDate()
	{
		return wUseByDate;
	}

	//#CM45194
	/**
	 * Set List Print Flag.
	 * @param arg List Print Flag to be set.
	 */
	public void setListFlg(boolean arg)
	{
		wListFlg = arg;
	}

	//#CM45195
	/**
	 * Acquire List Print Flag.
	 * @return ListPrintFlag
	 */
	public boolean getListFlg()
	{
		return wListFlg;
	}

	//#CM45196
	/**
	 * Set Area No.
	 * @param arg Area No to be set.
	 */
	public void setAreaNo(String arg)
	{
		wAreaNo = arg;
	}

	//#CM45197
	/**
	 * Acquire Area No.
	 * @return Area No.
	 */
	public String getAreaNo()
	{
		return wAreaNo;
	}

	//#CM45198
	/**
	 * Set Shelf No..
	 * @param arg Shelf No. to be set.
	 */
	public void setLocationNo(String arg)
	{
		wLocationNo = arg;
	}

	//#CM45199
	/**
	 * Acquire Shelf No.
	 * @return Shelf No.
	 */
	public String getLocationNo()
	{
		return wLocationNo;
	}

	//#CM45200
	/**
	 * Set Beginning shelfNo.
	 * @param arg Beginning shelfNo. to be set.
	 */
	public void setFromLocationNo(String arg)
	{
		wFromLocationNo = arg;
	}

	//#CM45201
	/**
	 * Acquire Beginning shelfNo.
	 * @return Beginning shelfNo.
	 */
	public String getFromLocationNo()
	{
		return wFromLocationNo;
	}

	//#CM45202
	/**
	 * Set End shelfNo.
	 * @param arg End shelfNo. to be set.
	 */
	public void setToLocationNo(String arg)
	{
		wToLocationNo = arg;
	}

	//#CM45203
	/**
	 * Acquire End shelfNo.
	 * @return End shelfNo.
	 */
	public String getToLocationNo()
	{
		return wToLocationNo;
	}

	//#CM45204
	/**
	 * Set BankNo.
	 * @param arg Bank No. to be set.
	 */
	public void setBankNo(String arg)
	{
		wBankNo = arg;
	}

	//#CM45205
	/**
	 * Acquire BankNo.
	 * @return BankNo.
	 */
	public String getBankNo()
	{
		return wBankNo;
	}

	//#CM45206
	/**
	 * Set BayNo.
	 * @param arg Set the value in Bay No.
	 */
	public void setBayNo(String arg)
	{
		wBayNo = arg;
	}

	//#CM45207
	/**
	 * Acquire BayNo.
	 * @return BayNo.
	 */
	public String getBayNo()
	{
		return wBayNo;
	}

	//#CM45208
	/**
	 * Set LevelNo.
	 * @param arg LevelNo to be set.
	 */
	public void setLevelNo(String arg)
	{
		wLevelNo = arg;
	}

	//#CM45209
	/**
	 * Acquire LevelNo.
	 * @return LevelNo.
	 */
	public String getLevelNo()
	{
		return wLevelNo;
	}

	//#CM45210
	/**
	 * Set Start BankNo.
	 * @param arg Start BankNo. to be set.
	 */
	public void setStartBankNo(String arg)
	{
		wStartBankNo = arg;
	}

	//#CM45211
	/**
	 * Acquire Start BankNo.
	 * @return Start BankNo.
	 */
	public String getStartBankNo()
	{
		return wStartBankNo;
	}

	//#CM45212
	/**
	 * Set Start BayNo.
	 * @param arg Start BayNo.
	 */
	public void setStartBayNo(String arg)
	{
		wStartBayNo = arg;
	}

	//#CM45213
	/**
	 * Acquire Start BayNo.
	 * @return Start BayNo.
	 */
	public String getStartBayNo()
	{
		return wStartBayNo;
	}

	//#CM45214
	/**
	 * Set Start LevelNo.
	 * @param arg Start LevelNo. to be set.
	 */
	public void setStartLevelNo(String arg)
	{
		wStartLevelNo = arg;
	}

	//#CM45215
	/**
	 * Acquire Start LevelNo.
	 * @return Start LevelNo.
	 */
	public String getStartLevelNo()
	{
		return wStartLevelNo;
	}

	//#CM45216
	/**
	 * Set End BankNo.
	 * @param arg End BankNo. to be set.
	 */
	public void setEndBankNo(String arg)
	{
		wEndBankNo = arg;
	}

	//#CM45217
	/**
	 * Acquire End BankNo.
	 * @return End BankNo.
	 */
	public String getEndBankNo()
	{
		return wEndBankNo;
	}

	//#CM45218
	/**
	 * Set End BayNo.
	 * @param arg End BayNo. to be set.
	 */
	public void setEndBayNo(String arg)
	{
		wEndBayNo = arg;
	}

	//#CM45219
	/**
	 * Acquire End BayNo.
	 * @return End BayNo.
	 */
	public String getEndBayNo()
	{
		return wEndBayNo;
	}

	//#CM45220
	/**
	 * Set End LevelNo.
	 * @param arg End LevelNo. to be set.
	 */
	public void setEndLevelNo(String arg)
	{
		wEndLevelNo = arg;
	}

	//#CM45221
	/**
	 * Acquire End LevelNo.
	 * @return End LevelNo.
	 */
	public String getEndLevelNo()
	{
		return wEndLevelNo;
	}

	//#CM45222
	/**
	 * Set Shipper code.
	 * @param arg Shipper Code to be set
	 */
	public void setCustomerCode(String arg)
	{
		wCustomerCode = arg;
	}

	//#CM45223
	/**
	 * Acquire Shipper code.
	 * @return Shipper code
	 */
	public String getCustomerCode()
	{
		return wCustomerCode;
	}

	//#CM45224
	/**
	 * Set Shipper name.
	 * @param arg Shipper Name to be set.
	 */
	public void setCustomerName(String arg)
	{
		wCustomerName = arg;
	}

	//#CM45225
	/**
	 * Acquire Shipper name.
	 * @return Shipper Name
	 */
	public String getCustomerName()
	{
		return wCustomerName;
	}

	//#CM45226
	/**
	 * Set All qty Flag.
	 * @param arg All qty Flag to be set.
	 */
	public void setTotalFlag(boolean arg)
	{
		wTotalFlag = arg;
	}

	//#CM45227
	/**
	 * Acquire All qty Flag.
	 * @return All qty Flag
	 */
	public boolean getTotalFlag()
	{
		return wTotalFlag;
	}

	//#CM45228
	/**
	 * Set Filtered Line No.
	 * @param arg Filtered Line No. to be set.
	 */
	public void setRowNo(int arg)
	{
		wRowNo = arg;
	}

	//#CM45229
	/**
	 * Acquire Filtered Line No.
	 * @return Filtered Line No.
	 */
	public int getRowNo()
	{
		return wRowNo;
	}

	//#CM45230
	/**
	 * Set Stock ID.
	 * @param arg Stock ID to be set.
	 */
	public void setStockId(String arg)
	{
		wStockId = arg;
	}

	//#CM45231
	/**
	 * Acquire Stock ID.
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return wStockId;
	}

	//#CM45232
	/**
	 * Set Last updated date and time.
	 * @param arg Last updated date and time to be set.
	 */
	public void setLastUpdateDate(java.util.Date arg)
	{
		wLastUpdateDate = arg;
	}

	//#CM45233
	/**
	 * Acquire Last updated date and time.
	 * @return Last updated date and time
	 */
	public java.util.Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	//#CM45234
	/**
	 * Set Storage date & time.
	 * @param arg Storage date & time to be set.
	 */
	public void setInStockDate(java.util.Date arg)
	{
		wInStockDate = arg;
	}

	//#CM45235
	/**
	 * Acquire Storage date & time.
	 * @return Storage date & time
	 */
	public java.util.Date getInStockDate()
	{
		return wInStockDate;
	}

	//#CM45236
	/**
	 * Set Stock qty.
	 * @param allocateQty Stock qty to be set.
	 */
   public void setAllocateQty(int allocateQty)
	{
		wAllocateQty = allocateQty;
	}
	
	//#CM45237
	/**
	 * Acquire Stock qty.
	 * @return Stock qty 
	 */
    public int getAllocateQty()
    {
        return wAllocateQty;
    }
    
	//#CM45238
	/**
	 * Set Drawing qty.
	 * @param stockQty Drawing qty to be set.
	 */
	public void setStockQty(int stockQty)
	{
		wStockQty = stockQty;
	}
    
	//#CM45239
	/**
	 * Acquire Drawing qty.
	 * @return Drawing qty
	 */
    public int getStockQty()
    {
        return wStockQty;
    }

	//#CM45240
	/**
	 * Set Warehouse.
	 * @param arg Warehouse to be set.
	 */
	public void setWareHouseNo(String arg)
	{
		wWareHouseNo = arg;
	}

	//#CM45241
	/**
	 * Acquire Warehouse.
	 * @return Warehouse
	 */
	public String getWareHouseNo()
	{
		return wWareHouseNo;
	}

	//#CM45242
	/**
	 * Set Hard Zone.
	 * @param arg Hard Zone to be set.
	 */
	public void setHardZone(String arg)
	{
		wHardZone = arg;
	}

	//#CM45243
	/**
	 * Acquire Hard Zone.
	 * @return Hard Zone
	 */
	public String getHardZone()
	{
		return wHardZone;
	}

	//#CM45244
	/**
	 * Set Workshop.
	 * @param arg Workshop to be set.
	 */
	public void setSagyoba(String arg)
	{
		wSagyoba = arg;
	}

	//#CM45245
	/**
	 * Acquire Workshop.
	 * @return Workshop
	 */
	public String getSagyoba()
	{
		return wSagyoba;
	}

	//#CM45246
	/**
	 * Set Station No.
	 * @param arg Station No to be set
	 */
	public void setStationNo(String arg)
	{
		wStationNo = arg;
	}

	//#CM45247
	/**
	 * Acquire station No.
	 * @return Station No
	 */
	public String getStationNo()
	{
		return wStationNo;
	}

	//#CM45248
	/**
	 * Set Plan Date.
	 * @param arg Plan Date to be set.
	 */
	public void setPlanDate(String arg)
	{
		wPlanDate = arg;
	}

	//#CM45249
	/**
	 * Acquire Plan Date.
	 * @return Plan Date
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	//#CM45250
	/**
	 * Set Plan unique key.
	 * @param arg Plan unique key to be set.
	 */
	public void setPlanUkey(String arg)
	{
		wPlanUkey = arg;
	}

	//#CM45251
	/**
	 * Acquire Plan unique key.
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return wPlanUkey;
	}

	//#CM45252
	/**
	 * Set Batch No.
	 * @param arg Batch No to be set
	 */
	public void setBatchNo(String arg)
	{
		wBatchNo = arg;
	}

	//#CM45253
	/**
	 * Acquire Batch No.
	 * @return Batch No.
	 */
	public String getBatchNo()
	{
		return wBatchNo;
	}

	//#CM45254
	/**
	 * Set Order No.
	 * @param arg Order No. to be set.
	 */
	public void setOrderNo(String arg)
	{
		wOrderNo = arg;
	}

	//#CM45255
	/**
	 * Acquire Order No.
	 * @return Order No.
	 */
	public String getOrderNo()
	{
		return wOrderNo;
	}

	//#CM45256
	/**
	 * Set Start Order No.
	 * @param arg Start Order No. to be set.
	 */
	public void setStartOrderNo(String arg)
	{
		wStartOrderNo = arg;
	}

	//#CM45257
	/**
	 * Acquire Start Order No.
	 * @return Start Order No.
	 */
	public String getStartOrderNo()
	{
		return wStartOrderNo;
	}

	//#CM45258
	/**
	 * Set End Order No.
	 * @param arg End Order No. to be set.
	 */
	public void setEndOrderNo(String arg)
	{
		wEndOrderNo = arg;
	}

	//#CM45259
	/**
	 * Acquire End Order No.
	 * @return End Order No.
	 */
	public String getEndOrderNo()
	{
		return wEndOrderNo;
	}

	//#CM45260
	/**
	 * Start Set Item Code.
	 * @param arg Start Item Code to be set.
	 */
	public void setStartItemCode(String arg)
	{
		wStartItemCode = arg;
	}

	//#CM45261
	/**
	 * Start Acquire Item Code.
	 * @return Start Item Code
	 */
	public String getStartItemCode()
	{
		return wStartItemCode;
	}

	//#CM45262
	/**
	 * End Set Item Code.
	 * @param arg End Item Code to be set.
	 */
	public void setEndItemCode(String arg)
	{
		wEndItemCode = arg;
	}

	//#CM45263
	/**
	 * End Acquire Item Code.
	 * @return End Item Code
	 */
	public String getEndItemCode()
	{
		return wEndItemCode;
	}

	//#CM45264
	/**
	 * Set Delivery total.
	 * @param arg Delivery total to be set.
	 */
	public void setTotalRetrievalQty(int arg)
	{
		wTotalRetrievalQty = arg;
	}

	//#CM45265
	/**
	 * Acquire Delivery total.
	 * @return Delivery total
	 */
	public int getTotalRetrievalQty()
	{
		return wTotalRetrievalQty;
	}

	//#CM45266
	/**
	 * Set Drawing object keeping place.
	 * @param arg Drawing object keeping place to be set.
	 */
	public void setAllocateWHNo(String[] arg)
	{
		wAllocateWHNo = arg;
	}

	//#CM45267
	/**
	 * Acquire Drawing object keeping place.
	 * @return Drawing object keeping place
	 */
	public String[] getAllocateWHNo()
	{
		return wAllocateWHNo;
	}

	//#CM45268
	/**
	 * Set Shortage Authorisation.
	 * @param arg Shortage Authorisation to be set.
	 */
	public void setShortageFlag(String arg)
	{
		wShortageFlag = arg;
	}

	//#CM45269
	/**
	 * Acquire Shortage Authorisation.
	 * @return Shortage Authorisation
	 */
	public String getShortageFlag()
	{
		return wShortageFlag;
	}

	//#CM45270
	/**
	 * Set Keeping place selection.
	 * @param arg Keeping place selection to be set.
	 */
	public void setSelectWHNo(String arg)
	{
		wSelectWHNo = arg;
	}

	//#CM45271
	/**
	 * Acquire Keeping place selection.
	 * @return Keeping place selection
	 */
	public String getSelectWHNo()
	{
		return wSelectWHNo;
	}

	//#CM45272
	/**
	 * Set ListPrint demand.
	 * @param arg ListPrint demand to be set.
	 */
	public void setListRequestFlag(String arg)
	{
		wListRequestFlag = arg;
	}

	//#CM45273
	/**
	 * Acquire ListPrint demand.
	 * @return ListPrint demand
	 */
	public String getListRequestFlag()
	{
		return wListRequestFlag;
	}

	//#CM45274
	/**
	 * Set Process Flag.
	 * @param arg Process Flag to be set.
	 */
	public void setProcessStatus(String arg)
	{
		wProcessStatus = arg;
	}

	//#CM45275
	/**
	 * Acquire Process Flag.
	 * @return Process Flag
	 */
	public String getProcessStatus()
	{
		return wProcessStatus;
	}

	//#CM45276
	/**
	 * Set Lot No.
	 * @param arg Lot No. to be set.
	 */
	public void setLotNo(String arg)
	{
		wLotNo = arg;
	}

	//#CM45277
	/**
	 * Acquire Lot No.
	 * @return Lot No.
	 */
	public String getLotNo()
	{
		return wLotNo;
	}

	//#CM45278
	/**
	 * Set WorkshopName.
	 * @param arg WorkshopName to be set.
	 */
	public void setWorkingPlaceName(String arg)
	{
		wWorkingPlaceName = arg;
	}

	//#CM45279
	/**
	 * Acquire WorkshopName.
	 * @return WorkshopName
	 */
	public String getWorkingPlaceName()
	{
		return wWorkingPlaceName;
	}

	//#CM45280
	/**
	 * Set Button Type.
	 * @param arg Button Type to be set.
	 */
	public void setButtonType(String arg)
	{
		wButtonType = arg;
	}

	//#CM45281
	/**
	 * Acquire Button Type.
	 * @return Button Type
	 */
	public String getButtonType()
	{
		return wButtonType;
	}

	//#CM45282
	/**
	 * Set Location Status selection.
	 * @param arg Location Status selection to be set.
	 */
	public void setSelectLocationStatus(String arg)
	{
		wSelectLocationStatus = arg;
	}

	//#CM45283
	/**
	 * Acquire Location Status selection.
	 * @return Location Status selection
	 */
	public String getSelectLocationStatus()
	{
		return wSelectLocationStatus;
	}

	//#CM45284
	/**
	 * Set Location Status Name.
	 * @param arg Location Status Name to be set.
	 */
	public void setLocationStatusName(String arg)
	{
		wLocationStatusName = arg;
	}

	//#CM45285
	/**
	 * Acquire Location Status Name.
	 * @return Location Status Name
	 */
	public String getLocationStatusName()
	{
		return wLocationStatusName;
	}

	//#CM45286
	/**
	 * Set Storage Flag.
	 * @param arg Storage Flag to be set.
	 */
	public void setStoringStatus(String arg)
	{
		wStoringStatus = arg;
	}

	//#CM45287
	/**
	 * Acquire Storage Flag.
	 * @return Storage Flag
	 */
	public String getStoringStatus()
	{
		return wStoringStatus;
	}

	//#CM45288
	/**
	 * Set Storage date.
	 * @param arg Storage date to be set.
	 */
	public void setStoringDate(String arg)
	{
		wStoringDate = arg;
	}

	//#CM45289
	/**
	 * Acquire Storage date.
	 * @return Storage date
	 */
	public String getStoringDate()
	{
		return wStoringDate;
	}

	//#CM45290
	/**
	 * Set Storage time.
	 * @param arg Storage time to be set.
	 */
	public void setStoringTime(String arg)
	{
		wStoringTime = arg;
	}

	//#CM45291
	/**
	 * Acquire Storage time.
	 * @return Storage time
	 */
	public String getStoringTime()
	{
		return wStoringTime;
	}

	//#CM45292
	/**
	 * Set WarehouseName.
	 * @param arg WarehouseName to be set.
	 */
	public void setWareHouseName(String arg)
	{
		wWareHouseName = arg;
	}

	//#CM45293
	/**
	 * Acquire WarehouseName.
	 * @return WarehouseName
	 */
	public String getWareHouseName()
	{
		return wWareHouseName;
	}

	//#CM45294
	/**
	 * Set AGC-No.
	 * @param arg AGC-No to be set.
	 */
	public void setAgcNo(String arg)
	{
		wAgcNo = arg;
	}

	//#CM45295
	/**
	 * Acquire AGC-No.
	 * @return AGC-No
	 */
	public String getAgcNo()
	{
		return wAgcNo;
	}

	//#CM45296
	/**
	 * Set System Status.
	 * @param arg System Status to be set.
	 */
	public void setSystemStatus(String arg)
	{
		wSystemStatus = arg;
	}

	//#CM45297
	/**
	 * Acquire System Status.
	 * @return System Status
	 */
	public String getSystemStatus()
	{
		return wSystemStatus;
	}

	//#CM45298
	/**
	 * Set Work qty.
	 * @param arg Work qty to be set.
	 */
	public void setWorkingCount(int arg)
	{
		wWorkingCount = arg;
	}

	//#CM45299
	/**
	 * Acquire Work qty.
	 * @return Work qty
	 */
	public int getWorkingCount()
	{
		return wWorkingCount;
	}

	//#CM45300
	/**
	 * Set Selection mode.
	 * @param arg Selection mode to be set.
	 */
	public void setSelectMode(String arg)
	{
		wSelectMode = arg;
	}

	//#CM45301
	/**
	 * Acquire Selection mode.
	 * @return Selection mode
	 */
	public String getSelectMode()
	{
		return wSelectMode;
	}

	//#CM45302
	/**
	 * Set Operation mode selection.
	 * @param arg Operation mode selection to be set.
	 */
	public void setSelectModeType(String arg)
	{
		wSelectModeType = arg;
	}

	//#CM45303
	/**
	 * Acquire Operation mode selection.
	 * @return Operation mode selection
	 */
	public String getSelectModeType()
	{
		return wSelectModeType;
	}

	//#CM45304
	/**
	 * Set Work mode selection.
	 * @param arg Work mode selection to be set.
	 */
	public void setSelectCurrentMode(String arg)
	{
		wSelectCurrentMode = arg;
	}

	//#CM45305
	/**
	 * Acquire Work mode selection.
	 * @return Work mode selection
	 */
	public String getSelectCurrentMode()
	{
		return wSelectCurrentMode;
	}

	//#CM45306
	/**
	 * Set StationName(For display).
	 * @param arg StationName(For display) to be set.
	 */
	public void setDispStationName(String arg)
	{
		wDispStationName = arg;
	}

	//#CM45307
	/**
	 * Acquire StationName(For display).
	 * @return StationName(For display)
	 */
	public String getDispStationName()
	{
		return wDispStationName;
	}

	//#CM45308
	/**
	 * Set Status of transportation.
	 * @param arg Status of transportation to be set.
	 */
	public void setCarringStatus(String arg)
	{
		wCarringStatus = arg;
	}

	//#CM45309
	/**
	 * Acquire Status of transportation.
	 * @return Status of transportation
	 */
	public String getCarringStatus()
	{
		return wCarringStatus;
	}

	//#CM45310
	/**
	 * Set Work status.
	 * @param arg Work status to be set.
	 */
	public void setWorkingStatus(String arg)
	{
		wWorkingStatus = arg;
	}

	//#CM45311
	/**
	 * Acquire Work status.
	 * @return Work status
	 */
	public String getWorkingStatus()
	{
		return wWorkingStatus;
	}

	//#CM45312
	/**
	 * Set Work mode switch request.
	 * @param arg Work mode switch request to be set.
	 */
	public void setWorkingModeRequest(String arg)
	{
		wWorkingModeRequest = arg;
	}

	//#CM45313
	/**
	 * Acquire Work mode switch request.
	 * @return Work mode switch request
	 */
	public String getWorkingModeRequest()
	{
		return wWorkingModeRequest;
	}

	//#CM45314
	/**
	 * Set Operation mode.
	 * @param arg Operation mode to be set.
	 */
	public void setModeType(String arg)
	{
		wModeType = arg;
	}

	//#CM45315
	/**
	 * Acquire Operation mode.
	 * @return Operation mode
	 */
	public String getModeType()
	{
		return wModeType;
	}

	//#CM45316
	/**
	 * Set Work mode.
	 * @param arg Work mode to be set.
	 */
	public void setCurrentMode(String arg)
	{
		wCurrentMode = arg;
	}

	//#CM45317
	/**
	 * Acquire Work mode.
	 * @return Work mode
	 */
	public String getCurrentMode()
	{
		return wCurrentMode;
	}

	//#CM45318
	/**
	 * Set Status of transportationName(For display).
	 * @param arg Status of transportationName(For display) to be set.
	 */
	public void setDispCarringStatusName(String arg)
	{
		wDispCarringStatusName = arg;
	}

	//#CM45319
	/**
	 * Acquire Status of transportationName(For display).
	 * @return Status of transportationName(For display)
	 */
	public String getDispCarringStatusName()
	{
		return wDispCarringStatusName;
	}

	//#CM45320
	/**
	 * Set Work statusName(For display).
	 * @param arg Work statusName(For display) to be set.
	 */
	public void setDispWorkingStatusName(String arg)
	{
		wDispWorkingStatusName = arg;
	}

	//#CM45321
	/**
	 * Acquire Work statusName(For display).
	 * @return Work statusName(For display)
	 */
	public String getDispWorkingStatusName()
	{
		return wDispWorkingStatusName;
	}

	//#CM45322
	/**
	 * Set Work mode switch requestName(For display).
	 * @param arg Work mode switch requestName(For display) to be set.
	 */
	public void setDispWorkingModeRequestName(String arg)
	{
		wDispWorkingModeRequestName = arg;
	}

	//#CM45323
	/**
	 * Acquire Work mode switch requestName(For display).
	 * @return Work mode switch requestName(For display)
	 */
	public String getDispWorkingModeRequestName()
	{
		return wDispWorkingModeRequestName;
	}

	//#CM45324
	/**
	 * Set Operation modeName(For display).
	 * @param arg Operation modeName(For display) to be set.
	 */
	public void setDispModeTypeName(String arg)
	{
		wDispModeTypeName = arg;
	}

	//#CM45325
	/**
	 * Acquire Operation modeName(For display).
	 * @return Operation modeName(For display)
	 */
	public String getDispModeTypeName()
	{
		return wDispModeTypeName;
	}

	//#CM45326
	/**
	 * Set Work modeName(For display).
	 * @param arg Work modeName(For display) to be set.
	 */
	public void setDispCurrentModeName(String arg)
	{
		wDispCurrentModeName = arg;
	}

	//#CM45327
	/**
	 * Acquire Work modeName(For display).
	 * @return Work modeName(For display)
	 */
	public String getDispCurrentModeName()
	{
		return wDispCurrentModeName;
	}

	//#CM45328
	/**
	 * Set Machine Status (For display).
	 * @param arg Machine Status (For display) to be set.
	 */
	public void setDispControllerStatusName(String arg)
	{
		wDispControllerStatusName = arg;
	}

	//#CM45329
	/**
	 * Acquire Machine Status (For display).
	 * @return Machine Status (For display)
	 */
	public String getDispControllerStatusName()
	{
		return wDispControllerStatusName;
	}

	//#CM45330
	/**
	 * Set RMTitle machine No.
	 * @param arg RMTitle machine No to be set.
	 */
	public void setRackmasterNo(String arg)
	{
		wRackmasterNo = arg;
	}

	//#CM45331
	/**
	 * Acquire RMTitle machine No.
	 * @return RMTitle machine No
	 */
	public String getRackmasterNo()
	{
		return wRackmasterNo;
	}

	//#CM45332
	/**
	 * Set RMTitle machine No(Array)
	 * @param arg	RMTitle machine No(Array)
	 */
	public void setRegistDateArray(String[] arg)
	{
		wRackmasterNoArray = arg;
	}

	//#CM45333
	/**
	 * Acquire RMTitle machine No.
	 * @return RMTitle machine No
	 */
	public String[] getRackmasterNoArray()
	{
		return wRackmasterNoArray;
	}

	//#CM45334
	/**
	 * Set Actual Location Qty.
	 * @param arg Actual Location Qty to be set.
	 */
	public void setRealLocationCount(int arg)
	{
		wRealLocationCount = arg;
	}

	//#CM45335
	/**
	 * Acquire Actual Location Qty.
	 * @return Actual Location Qty
	 */
	public int getRealLocationCount()
	{
		return wRealLocationCount;
	}

	//#CM45336
	/**
	 * Set Empty Location Qty.
	 * @param arg Empty Location Qty to be set.
	 */
	public void setVacantLocationCount(int arg)
	{
		wVacantLocationCount = arg;
	}

	//#CM45337
	/**
	 * Acquire Empty Location Qty.
	 * @return Empty Location Qty
	 */
	public int getVacantLocationCount()
	{
		return wVacantLocationCount;
	}

	//#CM45338
	/**
	 * Set Empty PB Location Qty.
	 * @param arg Empty PB Location Qty to be set.
	 */
	public void setEmptyPBLocationCount(int arg)
	{
		wEmptyPBLocationCount = arg;
	}

	//#CM45339
	/**
	 * Acquire Empty PB Location Qty.
	 * @return Empty PB Location Qty
	 */
	public int getEmptyPBLocationCount()
	{
		return wEmptyPBLocationCount;
	}

	//#CM45340
	/**
	 * Set Abnormal Location Qty.
	 * @param arg Abnormal Location Qty to be set.
	 */
	public void setAbnormalLocationCount(int arg)
	{
		wAbnormalLocationCount = arg;
	}

	//#CM45341
	/**
	 * Acquire Abnormal Location Qty.
	 * @return Abnormal Location Qty
	 */
	public int getAbnormalLocationCount()
	{
		return wAbnormalLocationCount;
	}

	//#CM45342
	/**
	 * Set Restricted Location Qty.
	 * @param arg Restricted Location Qty to be set.
	 */
	public void setProhibitionLocationCount(int arg)
	{
		wProhibitionLocationCount = arg;
	}

	//#CM45343
	/**
	 * Acquire Restricted Location Qty.
	 * @return Restricted Location Qty
	 */
	public int getProhibitionLocationCount()
	{
		return wProhibitionLocationCount;
	}
	
	//#CM45344
	/**
	 * Set Restricted Location Qty.
	 * @param arg Restricted Location Qty to be set.
	 */
	public void setNotAccessLocationCount(int arg)
	{
	    wNotAccessLocationCount = arg;
	}

	//#CM45345
	/**
	 * Acquire Restricted Location Qty.
	 * @return Restricted Location Qty
	 */
	public int getNotAccessLocationCount()
	{
		return wNotAccessLocationCount;
	}
	
	//#CM45346
	/**
	 * Set Total Location Qty.
	 * @param arg Total Location Qty to be set.
	 */
	public void setTotalLocationCount(int arg)
	{
		wTotalLocationCount = arg;
	}

	//#CM45347
	/**
	 * Acquire Total Location Qty.
	 * @return Total Location Qty
	 */
	public int getTotalLocationCount()
	{
		return wTotalLocationCount;
	}

	//#CM45348
	/**
	 * Set Storage rate.
	 * @param arg Storage rate to be set.
	 */
	public void setLocationRate(String arg)
	{
		wLocationRate = arg;
	}

	//#CM45349
	/**
	 * Acquire Storage rate.
	 * @return Storage rate
	 */
	public String getLocationRate()
	{
		return wLocationRate;
	}

	//#CM45350
	/**
	 * Set Work No.
	 * @param arg Work No to be set.
	 */
	public void setWorkingNo(String arg)
	{
		wWorkingNo = arg;
	}

	//#CM45351
	/**
	 * Acquire Work No.
	 * @return Work No
	 */
	public String getWorkingNo()
	{
		return wWorkingNo;
	}

	//#CM45352
	/**
	 * Set Work type.
	 * @param arg Work type to be set.
	 */
	public void setWorkingType(String arg)
	{
		wWorkingType = arg;
	}

	//#CM45353
	/**
	 * Acquire Work type.
	 * @return Work type
	 */
	public String getWorkingType()
	{
		return wWorkingType;
	}

	//#CM45354
	/**
	 * Set the detailed delivery instruction.
	 * @param arg the detailed delivery instruction to be set.
	 */
	public void setRetrievalDetail(String arg)
	{
		wRetrievalDetail = arg;
	}

	//#CM45355
	/**
	 * Acquire detailed delivery instruction.
	 * @return detialed delivery instruction. 
	 */
	public String getRetrievalDetail()
	{
		return wRetrievalDetail;
	}

	//#CM45356
	/**
	 * Set the detailed delivery instruction. (For display).
	 * @param arg the detailed delivery instruction. (For display) to be set.
	 */
	public void setDispRetrievalDetail(String arg)
	{
		wDispRetrievalDetail = arg;
	}

	//#CM45357
	/**
	 * Acquire the detailed delivery instruction. (For display).
	 * @return the detailed delivery instruction. (For display)
	 */
	public String getDispRetrievalDetail()
	{
		return wDispRetrievalDetail;
	}

	//#CM45358
	/**
	 * Set Transportation originStation No.
	 * @param arg Transportation originStation No to be set.
	 */
	public void setFromStationNo(String arg)
	{
		wFromStationNo = arg;
	}

	//#CM45359
	/**
	 * Transportation originAcquire station No.
	 * @return Transportation originStation No
	 */
	public String getFromStationNo()
	{
		return wFromStationNo;
	}

	//#CM45360
	/**
	 * Set Transportation originStationName.
	 * @param arg Transportation originStationName to be set.
	 */
	public void setFromStationName(String arg)
	{
		wFromStationName = arg;
	}

	//#CM45361
	/**
	 * Acquire Transportation originStationName.
	 * @return Transportation originStationName
	 */
	public String getFromStationName()
	{
		return wFromStationName;
	}

	//#CM45362
	/**
	 * Set the transportation destinationStation No.
	 * @param arg the transportation destinationStation No to be set.
	 */
	public void setToStationNo(String arg)
	{
		wToStationNo = arg;
	}

	//#CM45363
	/**
	 * At the transportation destinationAcquire station No.
	 * @return the transportation destinationStation No
	 */
	public String getToStationNo()
	{
		return wToStationNo;
	}

	//#CM45364
	/**
	 * Set the transportation destinationStationName.
	 * @param arg the transportation destinationStationName to be set.
	 */
	public void setToStationName(String arg)
	{
		wToStationName = arg;
	}

	//#CM45365
	/**
	 * Acquire the transportation destinationStationName.
	 * @return the transportation destinationStationName
	 */
	public String getToStationName()
	{
		return wToStationName;
	}

	//#CM45366
	/**
	 * Set Work Flag.
	 * @param arg Work Flag to be set.
	 */
	public void setJobType(String arg)
	{
		wJobType = arg;
	}

	//#CM45367
	/**
	 * Acquire Work Flag.
	 * @return Work Flag
	 */
	public String getJobType()
	{
		return wJobType;
	}

	//#CM45368
	/**
	 * Set Work FlagName.
	 * @param arg Work FlagName to be set.
	 */
	public void setJobTypeName(String arg)
	{
		wJobTypeName = arg;
	}

	//#CM45369
	/**
	 * Acquire Work FlagName.
	 * @return Work FlagName
	 */
	public String getJobTypeName()
	{
		return wJobTypeName;
	}

	//#CM45370
	/**
	 * Set Work type(For display).
	 * @param arg Work type(For display) to be set.
	 */
	public void setDispWorkType(String arg)
	{
		wDispWorkType = arg;
	}

	//#CM45371
	/**
	 * Acquire Work type(For display).
	 * @return Work type(For display)
	 */
	public String getDispWorkType()
	{
		return wDispWorkType;
	}

	//#CM45372
	/**
	 * Set Transportation Key.
	 * @param arg Transportation Key to be set.
	 */
	public void setCarryKey(String arg)
	{
		wCarryKey = arg;
	}

	//#CM45373
	/**
	 * Acquire Transportation Key.
	 * @return Transportation Key
	 */
	public String getCarryKey()
	{
		return wCarryKey;
	}

	//#CM45374
	/**
	 * Set Start day.
	 * @param arg Start day to be set.
	 */
	public void setStartDate(String arg)
	{
		wStartDate = arg;
	}

	//#CM45375
	/**
	 * Acquire Start day.
	 * @return Start day
	 */
	public String getStartDate()
	{
		return wStartDate;
	}

	//#CM45376
	/**
	 * Set Start time.
	 * @param arg Start time to be set.
	 */
	public void setStartTime(String arg)
	{
		wStartTime = arg;
	}

	//#CM45377
	/**
	 * Acquire Start time.
	 * @return Start time
	 */
	public String getStartTime()
	{
		return wStartTime;
	}

	//#CM45378
	/**
	 * Set End day
	 * @param arg End day to be set.
	 */
	public void setEndDate(String arg)
	{
		wEndDate = arg;
	}

	//#CM45379
	/**
	 * Acquire End day.
	 * @return End day
	 */
	public String getEndDate()
	{
		return wEndDate;
	}

	//#CM45380
	/**
	 * Set End time.
	 * @param arg End time to be set.
	 */
	public void setEndTime(String arg)
	{
		wEndTime = arg;
	}

	//#CM45381
	/**
	 * Acquire End time.
	 * @return End time
	 */
	public String getEndTime()
	{
		return wEndTime;
	}

	//#CM45382
	/**
	 * Set Total Operation Qty.
	 * @param arg Total Operation Qty to be set.
	 */
	public void setTotalInOutResultCount(int arg)
	{
		wTotalInOutResultCount = arg;
	}

	//#CM45383
	/**
	 * Acquire Total Operation Qty.
	 * @return Total Operation Qty
	 */
	public int getTotalInOutResultCount()
	{
		return wTotalInOutResultCount;
	}

	//#CM45384
	/**
	 * Set Storage Operation Qty.
	 * @param arg Storage Operation Qty to be set.
	 */
	public void setStorageResultCount(int arg)
	{
		wStorageResultCount = arg;
	}

	//#CM45385
	/**
	 * Acquire Storage Operation Qty.
	 * @return Storage Operation Qty
	 */
	public int getStorageResultCount()
	{
		return wStorageResultCount;
	}

	//#CM45386
	/**
	 * Set Picking Operation Qty.
	 * @param arg Picking Operation Qty to be set.
	 */
	public void setRetrievalResultCount(int arg)
	{
		wRetrievalResultCount = arg;
	}

	//#CM45387
	/**
	 * Acquire Picking Operation Qty.
	 * @return Picking Operation Qty
	 */
	public int getRetrievalResultCount()
	{
		return wRetrievalResultCount;
	}

	//#CM45388
	/**
	 * Set Storage Item Qty.
	 * @param arg Storage Item Qty to be set.
	 */
	public void setItemStorageCount(int arg)
	{
		wItemStorageCount = arg;
	}

	//#CM45389
	/**
	 * Acquire Storage Item Qty.
	 * @return Storage Item Qty
	 */
	public int getItemStorageCount()
	{
		return wItemStorageCount;
	}

	//#CM45390
	/**
	 * Set Picking Item Qty.
	 * @param arg Picking Item Qty to be set.
	 */
	public void setItemRetrievalCount(int arg)
	{
		wItemRetrievalCount = arg;
	}

	//#CM45391
	/**
	 * Acquire Picking Item Qty.
	 * @return Picking Item Qty
	 */
	public int getItemRetrievalCount()
	{
		return wItemRetrievalCount;
	}

	//#CM45392
	/**
	 * Set Consignor Code(For display).
	 * @param arg Consignor Code to be set(For display)
	 */
	public void setConsignorCodeDisp(String arg)
	{
		wConsignorCodeDisp = arg;
	}

	//#CM45393
	/**
	 * Acquire Consignor Code(For display).
	 * @return Consignor Code(For display)
	 */
	public String getConsignorCodeDisp()
	{
		return wConsignorCodeDisp;
	}
	
	//#CM45394
	/**
	 * Set Consignor Name(For display).
	 * @param arg Consignor Name(For display) to be set.
	 */
	public void setConsignorNameDisp(String arg)
	{
		wConsignorNameDisp = arg;
	}

	//#CM45395
	/**
	 * Acquire Consignor Name(For display).
	 * @return Consignor Name(For display)
	 */
	public String getConsignorNameDisp()
	{
		return wConsignorNameDisp;
	}
	
	//#CM45396
	/**
	 * Set Item Code(For display).
	 * @param arg Item Code to be Set.(For display)
	 */
	public void setItemCodeDisp(String arg)
	{
		wItemCodeDisp = arg;
	}

	//#CM45397
	/**
	 * Acquire Item Code(For display).
	 * @return Item Code(For display)
	 */
	public String getItemCodeDisp()
	{
		return wItemCodeDisp;
	}
	
	//#CM45398
	/**
	 * Set Item Name(For display).
	 * @param arg Item Name to be Set.(For display)
	 */
	public void setItemNameDisp(String arg)
	{
		wItemNameDisp = arg;
	}

	//#CM45399
	/**
	 * Acquire Item Name(For display).
	 * @return Item Name(For display)
	 */
	public String getItemNameDisp()
	{
		return wItemNameDisp;
	}
	
	//#CM45400
	/**
	 * Set CasePieceFlag(For display).
	 * @param arg CasePieceFlag(For display) to be set.
	 */
	public void setCasePieceFlagDisp(String arg)
	{
		wCasePieceFlagDisp = arg;
	}

	//#CM45401
	/**
	 * Acquire CasePieceFlag(For display).
	 * @return CasePieceFlag(For display)
	 */
	public String getCasePieceFlagDisp()
	{
		return wCasePieceFlagDisp;
	}

	//#CM45402
	/**
	 * Set Case/Piece flagName(For display).
	 * @param arg Case/Piece flag to be Set.Name(For display)
	 */
	public void setCasePieceFlagNameDisp(String arg)
	{
		wCasePieceFlagNameDisp = arg;
	}

	//#CM45403
	/**
	 * Acquire Case/Piece flagName(For display).
	 * @return Case/Piece flagName(For display)
	 */
	public String getCasePieceFlagNameDisp()
	{
		return wCasePieceFlagNameDisp;
	}
	
	//#CM45404
	/**
	 * Set Case ITF(For display).
	 * @param arg Case ITF(For display) to be set.
	 */
	public void setITFDisp(String arg)
	{
		wITFDisp = arg;
	}

	//#CM45405
	/**
	 * Acquire Case ITF(For display).
	 * @return Case ITF(For display)
	 */
	public String getITFDisp()
	{
		return wITFDisp;
	}
	
	//#CM45406
	/**
	 * Set Bundle ITF(For display).
	 * @param arg Bundle ITF(For display) to be set.
	 */
	public void setBundleITFDisp(String arg)
	{
		wBundleITFDisp = arg;
	}

	//#CM45407
	/**
	 * Acquire Bundle ITF(For display).
	 * @return Bundle ITF(For display)
	 */
	public String getBundleITFDisp()
	{
		return wBundleITFDisp;
	}
	
	//#CM45408
	/**
	 * Set Expiry date(For display).
	 * @param arg Expiry date(For display) to be set.
	 */
	public void setUseByDateDisp(String arg)
	{
		wUseByDateDisp = arg;
	}

	//#CM45409
	/**
	 * Acquire Expiry date(For display).
	 * @return Expiry date(For display)
	 */
	public String getUseByDateDisp()
	{
		return wUseByDateDisp;
	}

	//#CM45410
	/**
	 * Acquire Transportation route(For display).
	 * @return Transportation route(For display)
	 */
	public String getDispCarringRoute()
	{
		return wDispCarringRoute;
	}

	//#CM45411
	/**
	 * Set Transportation route(For display).
	 * @param arg Transportation route(For display) to be set.
	 */
	public void setDispCarringRoute(String arg)
	{
		wDispCarringRoute = arg;
	}

	//#CM45412
	/**
	 * Acquire Array of location information.
	 * @return Array of location information
	 */
	public String[] getBankNoArray()
	{
		return wBankNoArray;
	}
	//#CM45413
	/**
	 * Set Array of location information.
	 * @param bankNoArray Array of location information to be set.
	 */
	public void setBankNoArray(String[] bankNoArray)
	{
		wBankNoArray = bankNoArray;
	}

	//#CM45414
	/**
	 * Acquire transportation Flag. 
	 * @return Transportation Flag
	 */
	public int getCarryKind()
	{
		return wCarryKind;
	}

	//#CM45415
	/**
	 * Set Transportation Flag.
	 * @param arg Transportation Flag to be set.
	 */
	public void setCarryKind(int arg)
	{
		wCarryKind = arg;
	}
	
	//#CM45416
	/**
	 * Acquire Transportation Flag(For display).
	 * @return Transportation Flag(For display)
	 */
	public String getDispCarryKind()
	{
		return wDispCarryKind;
	}

	//#CM45417
	/**
	 * Set Transportation Flag(For display).
	 * @param arg Transportation Flag(For display) to be set.
	 */
	public void setDispCarryKind(String arg)
	{
		wDispCarryKind = arg;
	}
	
	//#CM45418
	/**
	 * Acquire Work displayType.
	 * @return Work displayType
	 */
	public int getOperationDisplay()
	{
		return wOperationDisplay;
	}

	//#CM45419
	/**
	 * Set Work displayType.
	 * @param arg Work displayType to be set.
	 */
	public void setOperationDisplay(int arg)
	{
		wOperationDisplay = arg;
	}
	
	//#CM45420
	/**
	 * Aisle Acquire station No.
	 * @return Aisle Station No.
	 */
	public String getAisleStationNumber()
	{
		return wAisleStationNumber;
	}

	//#CM45421
	/**
	 * Set Aisle Station No.
	 * @param arg Aisle Station No.
	 */
	public void setAisleStationNumber(String arg)
	{
		wAisleStationNumber = arg;
	}
	
	//#CM45422
	/**
	 * Set Status Array.
	 * @param arg Status Array to be set.
	 */
	public void setSearchStatus(String[] arg)
	{
		wSearchStatus = arg;
	}

	//#CM45423
	/**
	 * Acquire Status Array.
	 * @return Status Array
	 */
	public String[] getSearchStatus()
	{
		return wSearchStatus;
	}
	
	//#CM45424
	/**
	 * Set Status.
	 * @param arg Status  to be set.
	 */
	public void setStatusFlag(String arg)
	{
		wStatusFlag = arg;
	}

	//#CM45425
	/**
	 * Acquire Status .
	 * @return Status 
	 */
	public String getStatusFlag()
	{
		return wStatusFlag;
	}

	//#CM45426
	/**
	 * Set Start day & time.
	 * @param arg Start day & time to be set.
	 */
	public void setFromDate(java.util.Date arg)
	{
		wFromDate = arg;
	}

	//#CM45427
	/**
	 * Acquire Start day & time.
	 * @return Start day & time
	 */
	public java.util.Date getFromDate()
	{
		return wFromDate;
	}

	//#CM45428
	/**
	 * Set End day & time.
	 * @param arg End day & time to be set.
	 */
	public void setToDate(java.util.Date arg)
	{
		wToDate = arg;
	}

	//#CM45429
	/**
	 * Acquire End day & time.
	 * @return End day & time
	 */
	public java.util.Date getToDate()
	{
		return wToDate;
	}

	//#CM45430
	/**
	 * Set Number of total operation Results.
	 * @param arg Number of total operation Results to be set.
	 */
	public void setTotalInOutResultQty(long arg)
	{
		wTotalInOutResultQty = arg;
	}

	//#CM45431
	/**
	 * Acquire Number of total operation Results.
	 * @return Number of total operation Results
	 */
	public long getTotalInOutResultQty()
	{
		return wTotalInOutResultQty;
	}

	//#CM45432
	/**
	 * Set Priority Flag(For display).
	 * @param arg Priority Flag(For display) to be set.
	 */
	public void setDispPriority(String arg)
	{
		wDispPriority = arg;
	}

	//#CM45433
	/**
	 * Acquire Priority Flag(For display).
	 * @return Priority Flag(For display)
	 */
	public String getDispPriority()
	{
		return wDispPriority;
	}

}
//#CM45434
//end of class
