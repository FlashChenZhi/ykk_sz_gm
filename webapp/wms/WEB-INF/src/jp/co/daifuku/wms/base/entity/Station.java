//#CM706549
//$Id: Station.java,v 1.5 2006/11/16 02:15:37 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM706550
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Station;

//#CM706551
/**
 * Entity class of STATION
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- update history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:37 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Station
		extends AbstractEntity
{
	//#CM706552
	//------------------------------------------------------------
	//#CM706553
	// class variables (prefix '$')
	//#CM706554
	//------------------------------------------------------------
	//#CM706555
	//	private String	$classVar ;

//#CM706556
/**<en>
	 * Field of sendability (Not sendable)
	 </en>*/
	public static final int NOT_SENDABLE = 0 ;

	//#CM706557
	/**<jp>
	 * Field of sendability : sendable
	 </jp>*/
	//#CM706558
	/**<en>
	 * Field of sendability (sendable)
	 </en>*/
	public static final int SENDABLE_TRUE = 1 ;


	//#CM706559
	/**
	 * Status : NG
	 */
	public static final int STATION_NG = 0 ;

	//#CM706560
	/**
	 * Status : OK
	 */
	public static final int STATION_OK = 1 ;

	//#CM706561
	/**
	 * Status : FAIL
	 */
	public static final int STATION_FAIL = 2 ;

	//#CM706562
	/**
	 * Setting Type : OTHER
	 */
	public static final int IN_SETTING_OTHER = 0 ;

	//#CM706563
	/**
	 * Setting Type : PRECEDE
	 */
	public static final int IN_SETTING_PRECEDE = 0x100 ;

	//#CM706564
	/**<jp>
	 * Setting Type : CONFIRM
	 </jp>*/
	public static final int IN_SETTING_CONFIRM = 0x200 ;

	//#CM706565
	/**<jp>
	 * Station Type : OTHER
	 </jp>*/
	public static final int STATION_TYPE_OTHER = 0 ;

	//#CM706566
	/**<jp>
	 * Station Type : STORAGE
	 </jp>*/
	public static final int STATION_TYPE_IN = 1 ;

	//#CM706567
	/**<jp>
	 * Station Type : PICKING
	 </jp>*/
	public static final int STATION_TYPE_OUT = 2 ;

	//#CM706568
	/**<jp>
	 * Station Type : STORAGE and PICKING
	 </jp>*/
	public static final int STATION_TYPE_INOUT = 3;


	//#CM706569
	/**<jp>
	 * Work Place Type : Unspecified
	 </jp>*/
	public static final int NOT_WORKPLACE = 0;

	//#CM706570
	/**<jp>
	 * Work Place Type : Set independent stations in aisle
	 </jp>*/
	public static final int STAND_ALONE_STATIONS = 1;

	//#CM706571
	/**<jp>
	 * Work Place Type : Set aisle unit Stations
	 </jp>*/
	public static final int AISLE_CONMECT_STATIONS = 2;

	//#CM706572
	/**<jp>
	 * Work Place Type : Main Station
	 </jp>*/
	public static final int MAIN_STATION = 3;

	//#CM706573
	/**<jp>
	 * Work Place Type : Floor type
	 </jp>*/
	public static final int WPTYPE_FLOOR = 4 ;

	//#CM706574
	/**<jp>
	 * Work Place Type : All types
	 </jp>*/
	public static final int WPTYPE_ALL = 5 ;


	//#CM706575
	/**<jp>
	 * Work Place Type : No work operation screen
	 </jp>*/
	public static final int NOT_OPERATIONDISPLAY = 0;

	//#CM706576
	/**<jp>
	 * Work Place Type : Completion button not available for Work operation display
	 </jp>*/
	public static final int OPERATIONDISPONLY = 1;

	//#CM706577
	/**<jp>
	 * Work Place Type : Completion button available for Work operation display
	 </jp>*/
	public static final int OPERATIONINSTRUCTION = 2;


	//#CM706578
	/**<jp>
	 * Suspension flag : Suspended
	 </jp>*/
	//#CM706579
	/**<en>
	 * Field of supention (station suspended)
	 </en>*/
	public static final int SUSPENDING = 1;

	//#CM706580
	/**<jp>
	 * Suspension flag : Available
	 </jp>*/
	//#CM706581
	/**<en>
	 * Field of supention (staion is available)
	 </en>*/
	public static final int NOT_SUSPEND = 0;

	//#CM706582
	/**<jp>
	 * Arrival report check : No check
	 </jp>*/
	//#CM706583
	/**<en>
	 * Field of arrival check operation at station (arrival check unavailable)
	 </en>*/
	public static final int ARRIVALCHECK_OFF = 0;

	//#CM706584
	/**<jp>
	 * Arrival report check : Check available
	 </jp>*/
	//#CM706585
	/**<en>
	 * Field of arrival check operation at station (arrival check is handled)
	 </en>*/
	public static final int ARRIVALCHECK_ON = 1;

	//#CM706586
	/**<jp>
	 * Mode of packing check : No check
	 </jp>*/
	//#CM706587
	/**<en>
	 * Field of load size check operation at station (load size check unavailable)
	 </en>*/
	public static final int NOT_LOADSIZECHECK = 0;

	//#CM706588
	/**<jp>
	 * Mode of packing check : Check available
	 </jp>*/
	//#CM706589
	/**<en>
	 * Field of load size check operation at station (load size check is handled)
	 </en>*/
	public static final int LOADSIZECHECK_ON = 1;

	//#CM706590
	/**<jp>
	 * :Removal flag : Removable
	 </jp>*/
	//#CM706591
	/**<en>
	 * Field of transfer section (transfer out available)
	 </en>*/
	public static final int REMOVE_OK = 0;

	//#CM706592
	/**<jp>
	 * Removal flag : Not removable
	 </jp>*/
	//#CM706593
	/**<en>
	 * Field of transfer section (transfer out unavailable)
	 </en>*/
	public static final int REMOVE_NG = 1;

	//#CM706594
	/**<jp>
	 * Stock confirmation check : No inventory check
	 </jp>*/
	//#CM706595
	/**<en>
	 * Field of inventory checking flag (inventory check has not been done)
	 </en>*/
	public static final int NOT_INVENTORYCHECK = 0;

	//#CM706596
	/**<jp>
	 * Stock confirmation check : Inventory check available
	 </jp>*/
	//#CM706597
	/**<en>
	 * Field of inventory checking flag (inventory is being checked)
	 </en>*/
	public static final int INVENTORYCHECK = 1;

	//#CM706598
	/**<jp>
	 * Stock confirmation check : empty location is being checked
	 </jp>*/
	//#CM706599
	/**<en>
	 * Field of inventory checking flag (empty location is being checked)
	 </en>*/
	public static final int EMPTY_LOCATION_CHECK = 2;

	//#CM706600
	/**<jp>
	 * Existence of restorage work : Restorage data not created
	 </jp>*/
	//#CM706601
	/**<en>
	 * Field of scheduled re-storage data creation  (not created)
	 </en>*/
	public static final int NOT_CREATE_RESTORING = 0;

	//#CM706602
	/**<jp>
	 * Existence of restorage work : Restorage data created
	 </jp>*/
	//#CM706603
	/**<en>
	 * Field of scheduled re-storage data creation  (created)
	 </en>*/
	public static final int CREATE_RESTORING = 1;

	//#CM706604
	/**<jp>
	 * Transportation of restorage instruction transmission existence : The transportation instruction is unnecessary (Return storage automatically on the AGC side)
	 </jp>*/
	//#CM706605
	/**<en>
	 * Carry instruction for return storage at pick retrieval
	 * (AGC handles return storage automatically - carry instruction NOT necessary)
	 </en>*/
	public static final int AGC_STORAGE_SEND = 0;

	//#CM706606
	/**<jp>
	 * Transportation of restorage instruction transmission existence : The transportation instruction is necessary (Return storage on the AWC side)
	 </jp>*/
	//#CM706607
	/**<en>
	 * Carry instruction for return storage at pick retrieval
	 * (AGC requires the carry instructions)
	 </en>*/
	public static final int AWC_STORAGE_SEND = 1;

	//#CM706608
	/**<jp>
	 * Mode change type : No mode change
	 </jp>*/
	//#CM706609
	/**<en>
	 * Field of mode switch type (no mode switch)
	 </en>*/
	public static final int NO_MODE_CHANGE = 0;

	//#CM706610
	/**<jp>
	 * Mode change type : AWC mode change
	 </jp>*/
	//#CM706611
	/**<en>
	 * Field of mode switch type  (AWC mode switch)
	 </en>*/
	public static final int AWC_MODE_CHANGE = 1;

	//#CM706612
	/**<jp>
	 * Mode change type : AGC mode change
	 </jp>*/
	//#CM706613
	/**<en>
	 * Field of mode switch type (AGC mode switch)
	 </en>*/
	public static final int AGC_MODE_CHANGE = 2;

	//#CM706614
	/**<jp>
	 * Mode change type : Automatic mode change
	 </jp>*/
	//#CM706615
	/**<en>
	 * Field of mode switch type (automatic mode switch)
	 </en>*/
	public static final int AUTOMATIC_MODE_CHANGE = 3;

	//#CM706616
	/**<jp>
	 *  Field which shows work mode (Neutral)
	 </jp>*/
	//#CM706617
	/**<en>
	 *  Field of work mode (neutral)
	 </en>*/
	public static final int NEUTRAL = 0;

	//#CM706618
	/**<jp>
	 *  Current work mode : Storage mode
	 </jp>*/
	//#CM706619
	/**<en>
	 *  Field of work mode (storage mode)
	 </en>*/
	public static final int STORAGE_MODE = 1;

	//#CM706620
	/**<jp>
	 *  Current work mode : Picking mode
	 </jp>*/
	//#CM706621
	/**<en>
	 *  Field of work mode (retrieval mode)
	 </en>*/
	public static final int RETRIEVAL_MODE = 2;

	//#CM706622
	/**<jp>
	 * Mode switch request division : No request for mode switch
	 </jp>*/
	//#CM706623
	/**<en>
	 * Field of mode switch request classification (no mode switch request)
	 </en>*/
	public static final int NO_REQUEST = 0;

	//#CM706624
	/**<jp>
	 * Mode switch request division : Storage mode change request
	 </jp>*/
	//#CM706625
	/**<en>
	 * Field of mode switch request classification (request switching to the storage mode)
	 </en>*/
	public static final int STORAGE_REQUEST = 1;

	//#CM706626
	/**<jp>
	 * Mode switch request division : Picking mode change request
	 </jp>*/
	//#CM706627
	/**<en>
	 * Field of mode switch request classification (request switching to the retrieval mode)
	 </en>*/
	public static final int RETRIEVAL_REQUEST = 2;

	//#CM706628
	/**<jp>
	 * Mode switch request response waiting time (Seconds).
	 * The mode switch request cannot be transmitted if the difference at a mode switch request date and present time is below this value.
	 </jp>*/
	//#CM706629
	/**<en>
	 * Wait time for the reply to mode switch request (second).
	 * If the difference between time mode switch was requested and present time is below this value,
	 * mode switch request cannot be submit.
	 </en>*/
	protected static final int MODECHANGE_REQUEST_WAITTIME = 20;

	//#CM706630
	/**<jp>
	 * Automatic flag Station No.
	 </jp>*/
	public static final String AUTO_SELECT_STATIONNO = "9999";

	//#CM706631
	/**
	 * Range of machine status. Make Status NG when cutting off the equipment exceeding this number is generated.
	 */
	public static final int STATION_NG_JUDGMENT = 0;
	//#CM706632
	//------------------------------------------------------------
	//#CM706633
	// fields (upper case only)
	//#CM706634
	//------------------------------------------------------------
	//#CM706635
	/*
	 *  * Table name : STATION
	 * station number :                STATIONNUMBER       varchar2(16)
	 * maximum palette qty :           MAXPALETTEQUANTITY  number
	 * maximum instructions :          MAXINSTRUCTION      number
	 * transmission type :             SENDABLE            number
	 * status :                        STATUS              number
	 * controller number :             CONTROLLERNUMBER    number
	 * station type :                  STATIONTYPE         number
	 * setting type :                  SETTINGTYPE         number
	 * work place type :               WORKPLACETYPE       number
	 * operation display :             OPERATIONDISPLAY    number
	 * station name :                  STATIONNAME         varchar2(60)
	 * suspend flag :                  SUSPEND             number
	 * arrival check :                 ARRIVALCHECK        number
	 * load size check :               LOADSIZECHECK       number
	 * removal flag :                  REMOVE              number
	 * inventory check flag :          INVENTORYCHECKFLAG  number
	 * re-storage flag :               RESTORINGOPERATION  number
	 * re-storage instruction flag :   RESTORINGINSTRUCTIONnumber
	 * palette operation :             POPERATIONNEED      number
	 * warehouse station :             WHSTATIONNUMBER     varchar2(16)
	 * parent station :                PARENTSTATIONNUMBER varchar2(16)
	 * aisle station :                 AISLESTATIONNUMBER  varchar2(16)
	 * next station number :           NEXTSTATIONNUMBER   varchar2(16)
	 * last used station number :      LASTUSEDSTATIONNUMBERvarchar2(16)
	 * rejected station number :       REJECTSTATIONNUMBER varchar2(16)
	 * mode type :                     MODETYPE            number
	 * current mode :                  CURRENTMODE         number
	 * mode request flag :             MODEREQUEST         number
	 * mode request time :             MODEREQUESTTIME     date
	 * carry key :                     CARRYKEY            varchar2(16)
	 * load height :                   HEIGHT              number
	 * bar code data :                 BCDATA              varchar2(30)
	 * class name :                    CLASSNAME           varchar2(128)
	 */

	//#CM706636
	/**Table name definition*/

	public static final String TABLE_NAME = "STATION";

	//#CM706637
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM706638
	/** Column Definition (MAXPALETTEQUANTITY) */

	public static final FieldName MAXPALETTEQUANTITY = new FieldName("MAXPALETTEQUANTITY");

	//#CM706639
	/** Column Definition (MAXINSTRUCTION) */

	public static final FieldName MAXINSTRUCTION = new FieldName("MAXINSTRUCTION");

	//#CM706640
	/** Column Definition (SENDABLE) */

	public static final FieldName SENDABLE = new FieldName("SENDABLE");

	//#CM706641
	/** Column Definition (STATUS) */

	public static final FieldName STATUS = new FieldName("STATUS");

	//#CM706642
	/** Column Definition (CONTROLLERNUMBER) */

	public static final FieldName CONTROLLERNUMBER = new FieldName("CONTROLLERNUMBER");

	//#CM706643
	/** Column Definition (STATIONTYPE) */

	public static final FieldName STATIONTYPE = new FieldName("STATIONTYPE");

	//#CM706644
	/** Column Definition (SETTINGTYPE) */

	public static final FieldName SETTINGTYPE = new FieldName("SETTINGTYPE");

	//#CM706645
	/** Column Definition (WORKPLACETYPE) */

	public static final FieldName WORKPLACETYPE = new FieldName("WORKPLACETYPE");

	//#CM706646
	/** Column Definition (OPERATIONDISPLAY) */

	public static final FieldName OPERATIONDISPLAY = new FieldName("OPERATIONDISPLAY");

	//#CM706647
	/** Column Definition (STATIONNAME) */

	public static final FieldName STATIONNAME = new FieldName("STATIONNAME");

	//#CM706648
	/** Column Definition (SUSPEND) */

	public static final FieldName SUSPEND = new FieldName("SUSPEND");

	//#CM706649
	/** Column Definition (ARRIVALCHECK) */

	public static final FieldName ARRIVALCHECK = new FieldName("ARRIVALCHECK");

	//#CM706650
	/** Column Definition (LOADSIZECHECK) */

	public static final FieldName LOADSIZECHECK = new FieldName("LOADSIZECHECK");

	//#CM706651
	/** Column Definition (REMOVE) */

	public static final FieldName REMOVE = new FieldName("REMOVE");

	//#CM706652
	/** Column Definition (INVENTORYCHECKFLAG) */

	public static final FieldName INVENTORYCHECKFLAG = new FieldName("INVENTORYCHECKFLAG");

	//#CM706653
	/** Column Definition (RESTORINGOPERATION) */

	public static final FieldName RESTORINGOPERATION = new FieldName("RESTORINGOPERATION");

	//#CM706654
	/** Column Definition (RESTORINGINSTRUCTION) */

	public static final FieldName RESTORINGINSTRUCTION = new FieldName("RESTORINGINSTRUCTION");

	//#CM706655
	/** Column Definition (POPERATIONNEED) */

	public static final FieldName POPERATIONNEED = new FieldName("POPERATIONNEED");

	//#CM706656
	/** Column Definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM706657
	/** Column Definition (PARENTSTATIONNUMBER) */

	public static final FieldName PARENTSTATIONNUMBER = new FieldName("PARENTSTATIONNUMBER");

	//#CM706658
	/** Column Definition (AISLESTATIONNUMBER) */

	public static final FieldName AISLESTATIONNUMBER = new FieldName("AISLESTATIONNUMBER");

	//#CM706659
	/** Column Definition (NEXTSTATIONNUMBER) */

	public static final FieldName NEXTSTATIONNUMBER = new FieldName("NEXTSTATIONNUMBER");

	//#CM706660
	/** Column Definition (LASTUSEDSTATIONNUMBER) */

	public static final FieldName LASTUSEDSTATIONNUMBER = new FieldName("LASTUSEDSTATIONNUMBER");

	//#CM706661
	/** Column Definition (REJECTSTATIONNUMBER) */

	public static final FieldName REJECTSTATIONNUMBER = new FieldName("REJECTSTATIONNUMBER");

	//#CM706662
	/** Column Definition (MODETYPE) */

	public static final FieldName MODETYPE = new FieldName("MODETYPE");

	//#CM706663
	/** Column Definition (CURRENTMODE) */

	public static final FieldName CURRENTMODE = new FieldName("CURRENTMODE");

	//#CM706664
	/** Column Definition (MODEREQUEST) */

	public static final FieldName MODEREQUEST = new FieldName("MODEREQUEST");

	//#CM706665
	/** Column Definition (MODEREQUESTTIME) */

	public static final FieldName MODEREQUESTTIME = new FieldName("MODEREQUESTTIME");

	//#CM706666
	/** Column Definition (CARRYKEY) */

	public static final FieldName CARRYKEY = new FieldName("CARRYKEY");

	//#CM706667
	/** Column Definition (HEIGHT) */

	public static final FieldName HEIGHT = new FieldName("HEIGHT");

	//#CM706668
	/** Column Definition (BCDATA) */

	public static final FieldName BCDATA = new FieldName("BCDATA");

	//#CM706669
	/** Column Definition (CLASSNAME) */

	public static final FieldName CLASSNAME = new FieldName("CLASSNAME");


	//#CM706670
	//------------------------------------------------------------
	//#CM706671
	// properties (prefix 'p_')
	//#CM706672
	//------------------------------------------------------------
	//#CM706673
	//	private String	p_Name ;


	//#CM706674
	//------------------------------------------------------------
	//#CM706675
	// instance variables (prefix '_')
	//#CM706676
	//------------------------------------------------------------
	//#CM706677
	//	private String	_instanceVar ;

	//#CM706678
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM706679
	//------------------------------------------------------------
	//#CM706680
	// constructors
	//#CM706681
	//------------------------------------------------------------

	//#CM706682
	/**
	 * Prepare class name list and generate instance
	 */
	public Station()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM706683
	//------------------------------------------------------------
	//#CM706684
	// accessors
	//#CM706685
	//------------------------------------------------------------

	//#CM706686
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM706687
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(Station.STATIONNUMBER).toString();
	}

	//#CM706688
	/**
	 * Set value to maximum palette qty
	 * @param arg maximum palette qty to be set
	 */
	public void setMaxPaletteQuantity(int arg)
	{
		setValue(MAXPALETTEQUANTITY, new Integer(arg));
	}

	//#CM706689
	/**
	 * Fetch maximum palette qty
	 * @return maximum palette qty
	 */
	public int getMaxPaletteQuantity()
	{
		return getBigDecimal(Station.MAXPALETTEQUANTITY).intValue();
	}

	//#CM706690
	/**
	 * Set value to maximum instructions
	 * @param arg maximum instructions to be set
	 */
	public void setMaxInstruction(int arg)
	{
		setValue(MAXINSTRUCTION, new Integer(arg));
	}

	//#CM706691
	/**
	 * Fetch maximum instructions
	 * @return maximum instructions
	 */
	public int getMaxInstruction()
	{
		return getBigDecimal(Station.MAXINSTRUCTION).intValue();
	}

	//#CM706692
	/**
	 * Set value to transmission type
	 * @param arg transmission type to be set
	 */
	public void setSendable(int arg)
	{
		setValue(SENDABLE, new Integer(arg));
	}

	//#CM706693
	/**
	 * Fetch transmission type
	 * @return transmission type
	 */
	public int getSendable()
	{
		return getBigDecimal(Station.SENDABLE).intValue();
	}

	//#CM706694
	/**
	 * Set value to status
	 * @param arg status to be set
	 */
	public void setStatus(int arg)
	{
		setValue(STATUS, new Integer(arg));
	}

	//#CM706695
	/**
	 * Fetch status
	 * @return status
	 */
	public int getStatus()
	{
		return getBigDecimal(Station.STATUS).intValue();
	}

	//#CM706696
	/**
	 * Set value to controller number
	 * @param arg controller number to be set
	 */
	public void setControllerNumber(int arg)
	{
		setValue(CONTROLLERNUMBER, new Integer(arg));
	}

	//#CM706697
	/**
	 * Fetch controller number
	 * @return controller number
	 */
	public int getControllerNumber()
	{
		return getBigDecimal(Station.CONTROLLERNUMBER).intValue();
	}

	//#CM706698
	/**
	 * Set value to station type
	 * @param arg station type to be set
	 */
	public void setStationType(int arg)
	{
		setValue(STATIONTYPE, new Integer(arg));
	}

	//#CM706699
	/**
	 * Fetch station type
	 * @return station type
	 */
	public int getStationType()
	{
		return getBigDecimal(Station.STATIONTYPE).intValue();
	}

	//#CM706700
	/**
	 * Set value to setting type
	 * @param arg setting type to be set
	 */
	public void setSettingType(int arg)
	{
		setValue(SETTINGTYPE, new Integer(arg));
	}

	//#CM706701
	/**
	 * Fetch setting type
	 * @return setting type
	 */
	public int getSettingType()
	{
		return getBigDecimal(Station.SETTINGTYPE).intValue();
	}

	//#CM706702
	/**
	 * Set value to work place type
	 * @param arg work place type to be set
	 */
	public void setWorkPlaceType(int arg)
	{
		setValue(WORKPLACETYPE, new Integer(arg));
	}

	//#CM706703
	/**
	 * Fetch work place type
	 * @return work place type
	 */
	public int getWorkPlaceType()
	{
		return getBigDecimal(Station.WORKPLACETYPE).intValue();
	}

	//#CM706704
	/**
	 * Set value to operation display
	 * @param arg operation display to be set
	 */
	public void setOperationDisplay(int arg)
	{
		setValue(OPERATIONDISPLAY, new Integer(arg));
	}

	//#CM706705
	/**
	 * Fetch operation display
	 * @return operation display
	 */
	public int getOperationDisplay()
	{
		return getBigDecimal(Station.OPERATIONDISPLAY).intValue();
	}

	//#CM706706
	/**
	 * Set value to station name
	 * @param arg station name to be set
	 */
	public void setStationName(String arg)
	{
		setValue(STATIONNAME, arg);
	}

	//#CM706707
	/**
	 * Fetch station name
	 * @return station name
	 */
	public String getStationName()
	{
		return getValue(Station.STATIONNAME).toString();
	}

	//#CM706708
	/**
	 * Set value to suspend flag
	 * @param arg suspend flag to be set
	 */
	public void setSuspend(int arg)
	{
		setValue(SUSPEND, new Integer(arg));
	}

	//#CM706709
	/**
	 * Fetch suspend flag
	 * @return suspend flag
	 */
	public int getSuspend()
	{
		return getBigDecimal(Station.SUSPEND).intValue();
	}

	//#CM706710
	/**
	 * Set value to arrival check
	 * @param arg arrival check to be set
	 */
	public void setArrivalCheck(int arg)
	{
		setValue(ARRIVALCHECK, new Integer(arg));
	}

	//#CM706711
	/**
	 * Fetch arrival check
	 * @return arrival check
	 */
	public int getArrivalCheck()
	{
		return getBigDecimal(Station.ARRIVALCHECK).intValue();
	}

	//#CM706712
	/**
	 * Set value to load size check
	 * @param arg load size check to be set
	 */
	public void setLoadSizeCheck(int arg)
	{
		setValue(LOADSIZECHECK, new Integer(arg));
	}

	//#CM706713
	/**
	 * Fetch load size check
	 * @return load size check
	 */
	public int getLoadSizeCheck()
	{
		return getBigDecimal(Station.LOADSIZECHECK).intValue();
	}

	//#CM706714
	/**
	 * Set value to removal flag
	 * @param arg removal flag to be set
	 */
	public void setRemove(int arg)
	{
		setValue(REMOVE, new Integer(arg));
	}

	//#CM706715
	/**
	 * Fetch removal flag
	 * @return removal flag
	 */
	public int getRemove()
	{
		return getBigDecimal(Station.REMOVE).intValue();
	}

	//#CM706716
	/**
	 * Set value to inventory check flag
	 * @param arg inventory check flag to be set
	 */
	public void setInventoryCheckFlag(int arg)
	{
		setValue(INVENTORYCHECKFLAG, new Integer(arg));
	}

	//#CM706717
	/**
	 * Fetch inventory check flag
	 * @return inventory check flag
	 */
	public int getInventoryCheckFlag()
	{
		return getBigDecimal(Station.INVENTORYCHECKFLAG).intValue();
	}

	//#CM706718
	/**
	 * Set value to re-storage flag
	 * @param arg re-storage flag to be set
	 */
	public void setReStoringOperation(int arg)
	{
		setValue(RESTORINGOPERATION, new Integer(arg));
	}

	//#CM706719
	/**
	 * Fetch re-storage flag
	 * @return re-storage flag
	 */
	public int getReStoringOperation()
	{
		return getBigDecimal(Station.RESTORINGOPERATION).intValue();
	}

	//#CM706720
	/**
	 * Set value to re-storage instruction flag
	 * @param arg re-storage instruction flag to be set
	 */
	public void setReStoringInstruction(int arg)
	{
		setValue(RESTORINGINSTRUCTION, new Integer(arg));
	}

	//#CM706721
	/**
	 * Fetch re-storage instruction flag
	 * @return re-storage instruction flag
	 */
	public int getReStoringInstruction()
	{
		return getBigDecimal(Station.RESTORINGINSTRUCTION).intValue();
	}

	//#CM706722
	/**
	 * Set value to palette operation
	 * @param arg palette operation to be set
	 */
	public void setPoperationNeed(int arg)
	{
		setValue(POPERATIONNEED, new Integer(arg));
	}

	//#CM706723
	/**
	 * Fetch palette operation
	 * @return palette operation
	 */
	public int getPoperationNeed()
	{
		return getBigDecimal(Station.POPERATIONNEED).intValue();
	}

	//#CM706724
	/**
	 * Set value to warehouse station
	 * @param arg warehouse station to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM706725
	/**
	 * Fetch warehouse station
	 * @return warehouse station
	 */
	public String getWHStationNumber()
	{
		return getValue(Station.WHSTATIONNUMBER).toString();
	}

	//#CM706726
	/**
	 * Set value to parent station
	 * @param arg parent station to be set
	 */
	public void setParentStationNumber(String arg)
	{
		setValue(PARENTSTATIONNUMBER, arg);
	}

	//#CM706727
	/**
	 * Fetch parent station
	 * @return parent station
	 */
	public String getParentStationNumber()
	{
		return getValue(Station.PARENTSTATIONNUMBER).toString();
	}

	//#CM706728
	/**
	 * Set value to aisle station
	 * @param arg aisle station to be set
	 */
	public void setAisleStationNumber(String arg)
	{
		setValue(AISLESTATIONNUMBER, arg);
	}

	//#CM706729
	/**
	 * Fetch aisle station
	 * @return aisle station
	 */
	public String getAisleStationNumber()
	{
		String aisleStation = getValue(Station.AISLESTATIONNUMBER).toString();

		//#CM706730
		// When nothing is set at the aisle station with Shelf, the instance returns parents station No.
		if ((this instanceof Shelf)
		&& ((aisleStation == null)
		|| (aisleStation.trim().length() == 0)))
		{
			return getParentStationNumber();
		}
		else
		{
			return aisleStation;
		}
	}

	//#CM706731
	/**
	 * Set value to next station number
	 * @param arg next station number to be set
	 */
	public void setNextStationNumber(String arg)
	{
		setValue(NEXTSTATIONNUMBER, arg);
	}

	//#CM706732
	/**
	 * Fetch next station number
	 * @return next station number
	 */
	public String getNextStationNumber()
	{
		return getValue(Station.NEXTSTATIONNUMBER).toString();
	}

	//#CM706733
	/**
	 * Set value to last used station number
	 * @param arg last used station number to be set
	 */
	public void setLastUsedStationNumber(String arg)
	{
		setValue(LASTUSEDSTATIONNUMBER, arg);
	}

	//#CM706734
	/**
	 * Fetch last used station number
	 * @return last used station number
	 */
	public String getLastUsedStationNumber()
	{
		return getValue(Station.LASTUSEDSTATIONNUMBER).toString();
	}

	//#CM706735
	/**
	 * Set value to rejected station number
	 * @param arg rejected station number to be set
	 */
	public void setRejectStationNumber(String arg)
	{
		setValue(REJECTSTATIONNUMBER, arg);
	}

	//#CM706736
	/**
	 * Fetch rejected station number
	 * @return rejected station number
	 */
	public String getRejectStationNumber()
	{
		return getValue(Station.REJECTSTATIONNUMBER).toString();
	}

	//#CM706737
	/**
	 * Set value to mode type
	 * @param arg mode type to be set
	 */
	public void setModeType(int arg)
	{
		setValue(MODETYPE, new Integer(arg));
	}

	//#CM706738
	/**
	 * Fetch mode type
	 * @return mode type
	 */
	public int getModeType()
	{
		return getBigDecimal(Station.MODETYPE).intValue();
	}

	//#CM706739
	/**
	 * Set value to current mode
	 * @param arg current mode to be set
	 */
	public void setCurrentMode(int arg)
	{
		setValue(CURRENTMODE, new Integer(arg));
	}

	//#CM706740
	/**
	 * Fetch current mode
	 * @return current mode
	 */
	public int getCurrentMode()
	{
		return getBigDecimal(Station.CURRENTMODE).intValue();
	}

	//#CM706741
	/**
	 * Set value to mode request flag
	 * @param arg mode request flag to be set
	 */
	public void setModeRequest(int arg)
	{
		setValue(MODEREQUEST, new Integer(arg));
	}

	//#CM706742
	/**
	 * Fetch mode request flag
	 * @return mode request flag
	 */
	public int getModeRequest()
	{
		return getBigDecimal(Station.MODEREQUEST).intValue();
	}

	//#CM706743
	/**
	 * Set value to mode request time
	 * @param arg mode request time to be set
	 */
	public void setModeRequestTime(Date arg)
	{
		setValue(MODEREQUESTTIME, arg);
	}

	//#CM706744
	/**
	 * Fetch mode request time
	 * @return mode request time
	 */
	public Date getModeRequestTime()
	{
		return (Date)getValue(Station.MODEREQUESTTIME);
	}

	//#CM706745
	/**
	 * Set value to carry key
	 * @param arg carry key to be set
	 */
	public void setCarryKey(String arg)
	{
		setValue(CARRYKEY, arg);
	}

	//#CM706746
	/**
	 * Fetch carry key
	 * @return carry key
	 */
	public String getCarryKey()
	{
		return getValue(Station.CARRYKEY).toString();
	}

	//#CM706747
	/**
	 * Set value to load height
	 * @param arg load height to be set
	 */
	public void setHeight(int arg)
	{
		setValue(HEIGHT, new Integer(arg));
	}

	//#CM706748
	/**
	 * Fetch load height
	 * @return load height
	 */
	public int getHeight()
	{
		return getBigDecimal(Station.HEIGHT).intValue();
	}

	//#CM706749
	/**
	 * Set value to bar code data
	 * @param arg bar code data to be set
	 */
	public void setBCData(String arg)
	{
		setValue(BCDATA, arg);
	}

	//#CM706750
	/**
	 * Fetch bar code data
	 * @return bar code data
	 */
	public String getBCData()
	{
		return getValue(Station.BCDATA).toString();
	}

	//#CM706751
	/**
	 * Set value to class name
	 * @param arg class name to be set
	 */
	public void setClassName(String arg)
	{
		setValue(CLASSNAME, arg);
	}

	//#CM706752
	/**
	 * Fetch class name
	 * @return class name
	 */
	public String getClassName()
	{
		return getValue(Station.CLASSNAME).toString();
	}


	//#CM706753
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

//#CM706754
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(MAXPALETTEQUANTITY, new Integer(0));
		setValue(MAXINSTRUCTION, new Integer(0));
		setValue(SENDABLE, new Integer(0));
		setValue(STATUS, new Integer(0));
		setValue(CONTROLLERNUMBER, new Integer(0));
		setValue(STATIONTYPE, new Integer(0));
		setValue(SETTINGTYPE, new Integer(0));
		setValue(WORKPLACETYPE, new Integer(0));
		setValue(OPERATIONDISPLAY, new Integer(0));
		setValue(SUSPEND, new Integer(0));
		setValue(ARRIVALCHECK, new Integer(0));
		setValue(LOADSIZECHECK, new Integer(0));
		setValue(REMOVE, new Integer(0));
		setValue(INVENTORYCHECKFLAG, new Integer(0));
		setValue(RESTORINGOPERATION, new Integer(0));
		setValue(RESTORINGINSTRUCTION, new Integer(0));
		setValue(POPERATIONNEED, new Integer(0));
		setValue(MODETYPE, new Integer(0));
		setValue(CURRENTMODE, new Integer(0));
		setValue(MODEREQUEST, new Integer(0));
		setValue(HEIGHT, new Integer(0));
	}
/**<jp>
	 * Return whether this station can be transmitted as instruction information.
	 * @return Flag which can be transmitted
	 *   true : Can be transmitted
	 *   false : Cannot be transmitted
	 </jp>*/
	//#CM706755
	/**<en>
	 * Returns whether/not this station is sendable of instruction data.
	 * @return :sendable flag
	 *   true  : sendable
	 *   false : not sendable
	 </en>*/
	public boolean isSendable()
	{
		if (getSendable() == Station.SENDABLE_TRUE)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//#CM706756
	/**<jp>
	 * Check it whether this station is only unit delivery operation.
	 * This method checks operation based on the type of the station.
	 * @return True only if the station is for the unit delivery operation. False otherwise.
	 </jp>*/
	//#CM706757
	/**<en>
	 * Checks whether this station is dedicated for unit retrieval operation.
	 * In this method, it checks the operation based on the stataion type.
	 * @return :true if the station is dedicated for unit retrievals only, or false if not.
	 </en>*/
	public boolean isUnitOnly()
	{
		if (getStationType() == Station.STATION_TYPE_OUT)
		{
			//#CM706758
			// Return false for the side of the delivery of the character of piece.
			if (FreeRetrievalStationOperator.class.getName().equals(getClassName()))
			{
				return false;
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	//#CM706759
	/**<jp>
	 * Return whether this station is suspended.
	 * @return Suspension flag
	 *    true: In case of suspension
	 *    false: In case of not suspended
	 </jp>*/
	//#CM706760
	/**<en>
	 * Returns whether/not the station is suspended.
	 * @return suspention flag
	 *    true : suspended
	 *    false: available
	 </en>*/
	public boolean isSuspend()
	{
		if (getSuspend() == Station.SUSPENDING)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//#CM706761
	/**<jp>
	 * Returns whether/not the arrival report check is done by this station.
	 * @return flag with arriaval check
	 *    true : arrival check is done
	 *    false: no check at arrival
	 </jp>*/
	//#CM706762
	/**<en>
	 * Returns whether/not the arrival report check is done by this station.
	 * @return flag with arriaval check
	 *    true : arrival check is done
	 *    false: no check at arrival
	 </en>*/
	public boolean isArrivalCheck()
	{
		if (getArrivalCheck() == Station.ARRIVALCHECK_ON)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//#CM706763
	/**<jp>
	 * Returns if this staion is available for transfer.
	 * @return flag for transfer
	 *    true : transfer available
	 *    false: transfer unavailable
	 </jp>*/
	//#CM706764
	/**<en>
	 * Returns if this staion is available for transfer.
	 * @return flag for transfer
	 *    true : transfer available
	 *    false: transfer unavailable
	 </en>*/
	public boolean isRemove()
	{
		if (getRemove() == Station.REMOVE_OK)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//#CM706765
	/**<jp>
	 * Returns whether/not this station checks the load size.
	 * @return flag of load size checki
	 *    true: load size check is done
	 *    false:load size is not checked
	 </jp>*/
	//#CM706766
	/**<en>
	 * Returns whether/not this station checks the load size.
	 * @return flag of load size checki
	 *    true: load size check is done
	 *    false:load size is not checked
	 </en>*/
	public boolean isLoadSizeCheck()
	{
		if (getLoadSizeCheck() == Station.LOADSIZECHECK_ON)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//#CM706767
	/**<jp>
	 * Gets whether/not the data of scheduled re-strage is created.
	 * @return whether/not the data of scheduled re-strage is created
	 *   true  : scheduled re-strage data is created
	 *   false : scheduled re-strage data is not created
	 </jp>*/
	//#CM706768
	/**<en>
	 * Gets whether/not the data of scheduled re-strage is created.
	 * @return whether/not the data of scheduled re-strage is created
	 *   true  : scheduled re-strage data is created
	 *   false : scheduled re-strage data is not created
	 </en>*/
	public boolean isReStoringOperation()
	{
		if (getReStoringOperation() == Station.CREATE_RESTORING)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//#CM706769
	/**<jp>
	 * Returns whether/not this station is the storage station.
	 * It returns true for storage/retrieval staion.
	 * @return :storage station flag
	 *    true : storage station
	 *    false : not storage station
	 </jp>*/
	//#CM706770
	/**<en>
	 * Returns whether/not this station is the storage station.
	 * It returns true for storage/retrieval staion.
	 * @return :storage station flag
	 *    true : storage station
	 *    false : not storage station
	 </en>*/
	public boolean isInStation()
	{
		switch(getStationType())
		{
			//#CM706771
			//<jp> Type check</jp>
			//#CM706772
			//<en> Checks the type.</en>
			case Station.STATION_TYPE_IN:
			case Station.STATION_TYPE_INOUT:
				return true ;
			case Station.STATION_TYPE_OUT:
				return false ;
		}
		//#CM706773
		// Return false if everything does not apply.
		return false ;
	}

	//#CM706774
	/**<jp>
	 * Returns whether/not this is the retrieval station.
	 * It returns true for storage/retrieval staion.
	 * @return :retrieval station flag
	 *    true :retrieval station
	 *    false : not the retrieval station
	 </jp>*/
	//#CM706775
	/**<en>
	 * Returns whether/not this is the retrieval station.
	 * It returns true for storage/retrieval staion.
	 * @return :retrieval station flag
	 *    true :retrieval station
	 *    false : not the retrieval station
	 </en>*/
	public boolean isOutStation()
	{
		switch(getStationType())
		{
			//#CM706776
			//<jp> Type check</jp>
			//#CM706777
			//<en> Checks the type.</en>
			case Station.STATION_TYPE_OUT:
			case Station.STATION_TYPE_INOUT:
				return true ;
			case Station.STATION_TYPE_IN:
				return false ;
		}
		return false ;
	}

	//#CM706778
	/**<jp>
	 * It is judged whether the mode of the input station is a storage mode. <BR>
	 * The mode of the station must be a stock mode, must switch the mode, judge the stock mode while not requesting, and return true.
	 * Return always true in case of Station only for Storage, Station without mode switch and Automatic mode of operation switch station.
	 * Do not do the station type check of storage/picking.
	 * @return If the mode of the input station is a stock, return true, otherwise false.
	 </jp>*/
	//#CM706779
	/**<en>
	 * Determines whether/not the entered station is in storage mode.<BR>
	 * If the station is in storage mode and not requesting for mode switch, it determines the station
	 * is in storage mode and returns true.
	 * It always returns true for storage dedicated station, station with no mode switch and automatic mode
	 * switching station.
	 * @param st :station to check
	 * @return :it returns 'true' if entered station is is storage mode, or returns 'false' for all other cases.
	 </en>*/
	public boolean isStorageMode()
	{
		//#CM706780
		//<jp> Storage and retrieval station</jp>
		//#CM706781
		//<en> storage/retrieval ready station</en>
		if (getStationType() == Station.STATION_TYPE_INOUT)
		{
			//#CM706782
			//<jp> The mode switch function is provided. </jp>
			//#CM706783
			//<en> Mode switch functions. </en>
			if (getModeType() == Station.AGC_MODE_CHANGE
			 || getModeType() == Station.AWC_MODE_CHANGE)
			{
				//#CM706784
				//<jp> Is the storage mode?</jp>
				//#CM706785
				//<en> If the station is in storage mode</en>
				if (getCurrentMode() == Station.STORAGE_MODE)
				{
					//#CM706786
					//<jp> Is the mode switched and not requesting?</jp>
					//#CM706787
					//<en> If the station is in storage mode</en>
					if (getModeRequest() == Station.NO_REQUEST)
					{
						//#CM706788
						//<jp> Storage mode</jp>
						//#CM706789
						//<en> storage mode</en>
						return true;
					}
				}
				return false;
			}
		}
		return true;
	}

	//#CM706790
	/**<jp>
	 * It is judged whether the mode of the input station is a picking mode. <BR>
	 * Return true when the mode of the station must be a picking mode, must switch the mode, judge the delivery mode while not requesting.
	 * @return If the mode of the input station is picking, return true and false if other.
	 </jp>*/
	//#CM706791
	/**<en>
	 * Determines whether/not the entered station is in retrieval mode.<BR>
	 * It checks the mode only when the mode switch type is controlled by mode selection switch on AGC side.<BR>
	 * If the station is in retrieval mode and not requesting for mode switch, it determines the station is
	 * in retrieval mode and returns true.
	 * @param st :station to check
	 * @return :true if the entered station is in retrieval mode; or false for any other cases.
	 </en>*/
	public boolean isRetrievalMode()
	{
		if (getStationType() == Station.STATION_TYPE_INOUT)
		{
			//#CM706792
			//<jp> The mode switch function is provided. </jp>
			//#CM706793
			//<en> Mode switch functions.</en>
			if (getModeType() == Station.AGC_MODE_CHANGE
			 || getModeType() == Station.AWC_MODE_CHANGE)
			{
				//#CM706794
				//<jp> Picking mode or check</jp>
				//#CM706795
				//<en> Checks if the station is in retrieval mode.</en>
				if (getCurrentMode() == Station.RETRIEVAL_MODE)
				{
					//#CM706796
					//<jp> The mode is switched and it is not requesting. </jp>
					//#CM706797
					//<en> If the station is not requesting the mode switch.</en>
					if (getModeRequest() == Station.NO_REQUEST)
					{
						//#CM706798
						//<jp> Picking mode</jp>
						//#CM706799
						//<en> Retrieval mode</en>
						return true;
					}
				}
				//#CM706800
				//<jp> The mode is switched excluding the picking mode and requesting. </jp>
				//#CM706801
				//<en> Station is any mode other tahn retrieval, or it is requesting for mode switch.</en>
				return false;
			}
		}
		//#CM706802
		//<jp> No mode management.</jp>
		//#CM706803
		//<en> No mode controls.</en>
		return true;
	}
	//#CM706804
	//------------------------------------------------------------
	//#CM706805
	// package methods
	//#CM706806
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706807
	//------------------------------------------------------------


	//#CM706808
	//------------------------------------------------------------
	//#CM706809
	// protected methods
	//#CM706810
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706811
	//------------------------------------------------------------


	//#CM706812
	//------------------------------------------------------------
	//#CM706813
	// private methods
	//#CM706814
	//------------------------------------------------------------
	//#CM706815
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + MAXPALETTEQUANTITY);
		lst.add(prefix + MAXINSTRUCTION);
		lst.add(prefix + SENDABLE);
		lst.add(prefix + STATUS);
		lst.add(prefix + CONTROLLERNUMBER);
		lst.add(prefix + STATIONTYPE);
		lst.add(prefix + SETTINGTYPE);
		lst.add(prefix + WORKPLACETYPE);
		lst.add(prefix + OPERATIONDISPLAY);
		lst.add(prefix + STATIONNAME);
		lst.add(prefix + SUSPEND);
		lst.add(prefix + ARRIVALCHECK);
		lst.add(prefix + LOADSIZECHECK);
		lst.add(prefix + REMOVE);
		lst.add(prefix + INVENTORYCHECKFLAG);
		lst.add(prefix + RESTORINGOPERATION);
		lst.add(prefix + RESTORINGINSTRUCTION);
		lst.add(prefix + POPERATIONNEED);
		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + PARENTSTATIONNUMBER);
		lst.add(prefix + AISLESTATIONNUMBER);
		lst.add(prefix + NEXTSTATIONNUMBER);
		lst.add(prefix + LASTUSEDSTATIONNUMBER);
		lst.add(prefix + REJECTSTATIONNUMBER);
		lst.add(prefix + MODETYPE);
		lst.add(prefix + CURRENTMODE);
		lst.add(prefix + MODEREQUEST);
		lst.add(prefix + MODEREQUESTTIME);
		lst.add(prefix + CARRYKEY);
		lst.add(prefix + HEIGHT);
		lst.add(prefix + BCDATA);
		lst.add(prefix + CLASSNAME);
	}


	//#CM706816
	//------------------------------------------------------------
	//#CM706817
	// utility methods
	//#CM706818
	//------------------------------------------------------------
	//#CM706819
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Station.java,v 1.5 2006/11/16 02:15:37 suresh Exp $" ;
	}
}
