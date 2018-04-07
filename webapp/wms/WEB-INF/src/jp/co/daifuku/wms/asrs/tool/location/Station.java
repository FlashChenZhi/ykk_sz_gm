// $Id: Station.java,v 1.2 2006/10/30 02:33:24 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49626
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;

//#CM49627
/**<en>
 * This class is used to indicate the starting point/ ending point of the carry.
 * It preserves information such as the current quantity of carrying load, scheduled quantity 
 * of arrival, whether/not they are available, and etc. <BR>
 * Station have unique numbers. These numbers consist of any alphanumeric and are handled as strings.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:24 $
 * @author  $Author: suresh $
 </en>*/
public class Station extends ToolEntity
{
	//#CM49628
	// Class fields --------------------------------------------------

	//#CM49629
	/**<en>
	 * StationNumber Length
	 </en>*/
	public static final int STATIONNUMBER_LEN = 4 ;

	//#CM49630
	/**<en>
	 * Field of station status (off-line). This is used in carry route check.
	 </en>*/
	public static final int STATION_NG = 0 ;

	//#CM49631
	/**<en>
	 * Field of station status (normal). This is used in carry route check.
	 </en>*/
	public static final int STATION_OK = 1 ;

	//#CM49632
	/**<en>
	 * Field of station status (error). This is used in carry route check.
	 </en>*/
	public static final int STATION_FAIL = 2 ;

	//#CM49633
	/**<en>
	 * Field of storage setting type (other)
	 * Used in <code>getInSettingType,setInSettingType</code>.
	 </en>*/
	public static final int IN_SETTING_OTHER = 0 ;

	//#CM49634
	/**<en>
	 * Field of storage setting type (advanced setting)
	 * Used in <code>getInSettingType,setInSettingType</code>.
	 </en>*/
	public static final int IN_SETTING_PRECEDE = 0x100 ;

	//#CM49635
	/**<en>
	 * Field of storage setting type (set to check the load presence)
	 * Used in <code>getInSettingType,setInSettingType</code>.
	 </en>*/
	public static final int IN_SETTING_CONFIRM = 0x200 ;

	//#CM49636
	/**<en>
	 * Field of the station type (other)
	 </en>*/
	public static final int STATION_TYPE_OTHER = 0 ;

	//#CM49637
	/**<en>
	 * Field of the station type (storage)
	 </en>*/
	public static final int STATION_TYPE_IN = 0x001 ;

	//#CM49638
	/**<en>
	 * Field of the station type (retrieval)
	 </en>*/
	public static final int STATION_TYPE_OUT = 0x002 ;

	//#CM49639
	/**<en>
	 * Field of the station type (storage/retrieval available)
	 </en>*/
	public static final int STATION_TYPE_INOUT =  STATION_TYPE_IN | STATION_TYPE_OUT ;

	//#CM49640
	/**<en>
	 * Field of the station type (storage/retrieval available + storage)
	 </en>*/
	public static final int STATION_TYPE_INOUT_IN = 0x004;

	//#CM49641
	/**<en>
	 * Field of the station type (storage/retrieval available + retrieval)
	 </en>*/
	public static final int STATION_TYPE_INOUT_OUT = 0x005;

	//#CM49642
	/**<en>
	 * Field of the station type (storage/retrieval available + storage + retrieval)
	 </en>*/
	public static final int STATION_TYPE_INOUT_IN_OUT = 0x006;

	//#CM49643
	/**<en>
	 * Field which represents the type of workplace (undesignated)
	 </en>*/
	public static final int NOT_WORKPLACE = 0;

	//#CM49644
	/**<en>
	 * Field of workshop type (group of stand alone stations)
	 </en>*/
	public static final int STAND_ALONE_STATIONS = 1;

	//#CM49645
	/**<en>
	 * Field of workshop type (group of aisle connected stations)
	 </en>*/
	public static final int AISLE_CONMECT_STATIONS = 2;

	//#CM49646
	/**<en>
	 * Field of workshop type (group of main stations)
	 </en>*/
	public static final int MAIN_STATIONS = 3;

	//#CM49647
	/**<en>
	 * Field of workshop type (group of floor stations)
	 </en>*/
	public static final int WPTYPE_FLOOR = 4 ;
	
	//#CM49648
	/**<en>
	 * Field of workshop type (general workshop)
	 </en>*/
	public static final int WPTYPE_ALL = 5 ;

	//#CM49649
	/**<en>
	 * Field of on-line indication type ( no on-line indication )
	 </en>*/
	public static final int NOT_OPERATIONDISPLAY = 0;

	//#CM49650
	/**<en>
	 * Field of on-line indication type (on-line indication operated, without completion button)
	 </en>*/
	public static final int OPERATIONDISPLAY = 1;

	//#CM49651
	/**<en>
	 * Field of on-line indication type (on-line indication operated, with completion button)
	 </en>*/
	public static final int OPERATIONINSTRUCTION = 2;

	//#CM49652
	/**<en>
	 * Field of sendable/not sendable (sendable)
	 </en>*/
	public static final int SENDABLE = 1 ;

	//#CM49653
	/**<en>
	 * Field of sendable/not sendable (not sendable)
	 </en>*/
	public static final int NOT_SENDABLE = 0 ;

	//#CM49654
	/**<en>
	 * Field of station suspention (suspended)
	 </en>*/
	public static final int SUSPEND = 1;

	//#CM49655
	/**<en>
	 * Field of station suspention (active)
	 </en>*/
	public static final int NOT_SUSPEND = 0;

	//#CM49656
	/**<en>
	 * Field of arrival checks - whether/not the check is operated (checked) 
	 </en>*/
	public static final int ARRIVALCHECK = 1;

	//#CM49657
	/**<en>
	 * Field of arrival checks - whether/not the check is operated (no check)
	 </en>*/
	public static final int NOT_ARRIVALCHECK = 0;

	//#CM49658
	/**<en>
	 * Field of load size check - whether/not the check is operated (checked))
	 </en>*/
	public static final int LOADSIZECHECK = 1;

	//#CM49659
	/**<en>
	 * Field of load size check - whether/not the check is operated (no check)
	 </en>*/
	public static final int NOT_LOADSIZECHECK = 0;

	//#CM49660
	/**<en>
	 * Field of the availability for removal (available)
	 </en>*/
	public static final int PAYOUT_OK = 0;

	//#CM49661
	/**<en>
	 * Field of the availability for removal (not available)
	 </en>*/
	public static final int PAYOUT_NG = 1;

	//#CM49662
	/**<en>
	 * Field of inventory checking flag (checking the empty locations)
	 </en>*/
	public static final int EMPTY_LOCATION_CHECK = 2;

	//#CM49663
	/**<en>
	 * Field of inventory checking flag (checking the inventory checking)
	 </en>*/
	public static final int INVENTORYCHECK = 1;

	//#CM49664
	/**<en>
	 * Field of inventory checking flag (inventory check unprocessed)
	 </en>*/
	public static final int NOT_INVENTORYCHECK = 0;

	//#CM49665
	/**<en>
	 * Field of scheduled restorage data creation (not created)
	 </en>*/
	public static final int NOT_CREATE_RESTORING = 0;

	//#CM49666
	/**<en>
	 *  Field of scheduled restorage data creation (created)
	 </en>*/
	public static final int CREATE_RESTORING = 1;

	//#CM49667
	/**<en>
	 * Field which indicates whether/not the PaletteOperarion class of the station is called
	 * (PaletteOperarion class will be called)
	 </en>*/
	public static final int POPERATIONNEED = 1;

	//#CM49668
	/**<en>
	 * Field which indicates whether/not the PaletteOperarion class of the station is called
	 * (PaletteOperarion class will not be called)
	 </en>*/
	public static final int NOT_POPERATIONNEED = 0;

	//#CM49669
	/**<en>
	 * Field which indicates whether/not the carry instruction is send when storing the returned 
	 * load from the pick retrieval. 
	 * (AGC automatically stores the returned load. no instruction necessary)
	 </en>*/
	public static final int AGC_STORAGE_SEND = 0;

	//#CM49670
	/**<en>
	 * Field which indicates whether/not the carry instruction is send when storing the returned 
	 * load from the pick retrieval.
	 * (eWareNavi requies the carry instruction)
	 </en>*/
	public static final int AWC_STORAGE_SEND = 1;

	//#CM49671
	/**<en>
	 * Field of mode switch type (no mode switching)
	 </en>*/
	public static final int NO_MODE_CHANGE = 0;

	//#CM49672
	/**<en>
	 * Field of mode switch type (switch to AWC mode)
	 </en>*/
	public static final int AWC_MODE_CHANGE = 1;

	//#CM49673
	/**<en>
	 * Field of mode switch type (switch to AGC mode)
	 </en>*/
	public static final int AGC_MODE_CHANGE = 2;

	//#CM49674
	/**<en>
	 * Field of mode switch type (automatic mode switch)
	 </en>*/
	public static final int AUTOMATIC_MODE_CHANGE = 3;

	//#CM49675
	/**<en>
	 *  Field of work mode (neutral)
	 </en>*/
	public static final int NEUTRAL = 0;

	//#CM49676
	/**<en>
	 *  Field of work mode (storage mode)
	 </en>*/
	public static final int STORAGE_MODE = 1;

	//#CM49677
	/**<en>
	 *  Field of work mode (retrieval mode)
	 </en>*/
	public static final int RETRIEVAL_MODE = 2;

	//#CM49678
	/**<en>
	 * Field of mode switch requests (no request for mode switch)
	 </en>*/
	public static final int NO_REQUEST = 0;

	//#CM49679
	/**<en>
	 * Field of mode switch requests  (request for switching to storage mode)
	 </en>*/
	public static final int STORAGE_REQUEST = 1;

	//#CM49680
	/**<en>
	 * Field of mode switch requests (request for switching to retrieval mode)
	 </en>*/
	public static final int RETRIEVAL_REQUEST = 2;

	//#CM49681
	/**<en>
	 * Acceptable range of off-line machines. If the number of off-line machines exceeded this 
	 * number, the Status will be altered to 'unacceptable'.
	 </en>*/
	protected static final int STATION_NG_JUDGMENT = 0;

	//#CM49682
	/**<en>
	 * Wait time for the response to the mode switch request (second)
	 * If the difference between mode switch requested time and current time is less than this value,
	 * the mode switch request cannot be sent.
	 </en>*/
	protected static final int MODECHANGE_REQUEST_WAITTIME = 20;

	//#CM49683
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM49684
	// Class variables -----------------------------------------------
	//#CM49685
	/**<en>
	 * Preserve the station no.
	 </en>*/
	protected String wNumber = "" ;

	//#CM49686
	/**<en>
	 * Preserve the Group Controller to support.
	 </en>*/
	protected GroupController wGC = null ;

	//#CM49687
	/**<en>
	 * Preserve the status of the station.
	 </en>*/
	protected int wStatus = STATION_NG ;

	//#CM49688
	/**<en>
	 * Max number of pallet preserved.
	 </en>*/
	protected int wMaxPaletteQuantity = 0 ;
	
	//#CM49689
	/**<en>
	 * Max. number of carry instruction sendable
	 </en>*/
	protected int wMaxInstruction = 0 ;

	//#CM49690
	/**<en>
	 * Command sendable/not sendable
	 </en>*/
	protected boolean wSendable = false ;

	//#CM49691
	/**<en>
	 * Storage setting type
	 </en>*/
	protected int wInSettingType = 0 ;

	//#CM49692
	/**<en>
	 * Operation type (storage/retrieval)
	 </en>*/
	protected int wType = 0 ;

	//#CM49693
	/**<en>
	 * Type of workshop
	 </en>*/
	protected int wWorkPlaceType = 0 ;

	//#CM49694
	/**<en>
	 * Type of on-line indication
	 </en>*/
	protected int wOperataionDisplay = NOT_OPERATIONDISPLAY;

	//#CM49695
	/**<en>
	 * Name of the station
	 </en>*/
	protected String wName = ""  ;

	//#CM49696
	/**<en>
	 * Carry Key
	 </en>*/
	protected String wCarryKey  = ""  ;

	//#CM49697
	/**<en>
	 * Height of the pallet
	 </en>*/
	protected int wHeight = 0 ;

	//#CM49698
	/**<en>
	 * Bar code data
	 </en>*/
	protected String wBCData  = ""  ;

	//#CM49699
	/**<en>
	 * Suspention flag
	 </en>*/
	protected boolean wSuspend = false;

	//#CM49700
	/**<en>
	 * Arrival reporting check
	 </en>*/
	protected boolean wArrivalCheck = false;

	//#CM49701
	/**<en>
	 * Load height check
	 </en>*/
	protected boolean wLoadSizeCheck = false;

	//#CM49702
	/**<en>
	 * Distinguish the availability of removal.
	 * true = available, false = unavaileble
	 * @deprecated type has been replaced with int type.
	 </en>*/
	protected boolean wRemove = true;
	
	//#CM49703
	/**<en>
	 * Distinguish the availability of removal.
	 * 0 = available, 1 = unavailable
	 </en>*/
	protected int wReMove = 0;

	//#CM49704
	/**<en>
	 * Indicate whether/not the scheduled restorage data is created.
	 </en>*/
	protected boolean wReStoringOperation = false;

	//#CM49705
	/**<en>
	 * Indicate whether/not the carry instruction is sent when re-storing
	 * the load from pick-retrieval.
	 </en>*/
	protected int wReStoringInstruction = 0;

	//#CM49706
	/**<en>
	 * Inventory checking flag
	 </en>*/
	protected int wInventoryCheckFlag = NOT_INVENTORYCHECK;

	//#CM49707
	/**<en>
	 * Indicates whether/not the PaletteOperation class is called.
	 </en>*/
	protected boolean wPoperationNeed = false;

	//#CM49708
	/**<en>
	 * Warehouse that the station belongs to 
	 </en>*/
	protected Warehouse wWarehouse = null;

	//#CM49709
	/**<en>
	 * Warehouse no. that the station belongs to
	 </en>*/
	protected String wWarehouseStationNumber  = "" ;

	//#CM49710
	/**<en>
	 * Parent station
	 </en>*/
	protected Station wParentStation = null ;

	//#CM49711
	/**<en>
	 * Parent station no.
	 </en>*/
	protected String wParentStationNumber = "" ;

	//#CM49712
	/**<en>
	 * Aisle station available of storage/retrieval. Valid only with the aisle connected Stations.
	 * Null will be set in case with aisle connected Station.
	 </en>*/
	protected Station wAisleStation = null;

	//#CM49713
	/**<en>
	 * Aisle station no. available of storage/retrieval. Valid only with the aisle connected Stations.
	 * Null will be set in case with aisle connected Station.
	 </en>*/
	protected String wAisleStationNumber = "" ;

	//#CM49714
	/**<en>
	 * Next carry station (only valid for relay stations)
	 </en>*/
	protected Station wNextStation = null;

	//#CM49715
	/**<en>
	 * Next carry station no. (only valid for relay stations)
	 </en>*/
	protected String wNextStationNumber = "" ;

	//#CM49716
	/**<en>
	 * End use station (valid only with workshops)
	 </en>*/
	protected Station wLastUsedStation = null;

	//#CM49717
	/**<en>
	 * Next carry station no.(only valid for relay stations)
	 </en>*/
	protected String wLastUsedStationNumber = "" ;

	//#CM49718
	/**<en>
	 * Reject station
	 </en>*/
	protected Station wRejectStation = null ;

	//#CM49719
	/**<en>
	 * Reject station no.
	 </en>*/
	protected String wRejectStationNumber  = "" ;

	//#CM49720
	/**<en>
	 * Type of mode switch
	 </en>*/
	protected int wModeType = NO_MODE_CHANGE;

	//#CM49721
	/**<en>
	 * Work mode
	 </en>*/
	protected int wCurrentMode = NEUTRAL;

	//#CM49722
	/**<en>
	 * Mode change request 
	 </en>*/
	protected int wChangeModeRequest = NO_REQUEST;

	//#CM49723
	/**<en>
	 * Starting time of the mode switch request
	 </en>*/
	protected java.util.Date wChangeModeRequestTime = null ;

	//#CM49724
	/**<en>
	 * name of the class
	 </en>*/
	protected String wClassName  = "" ;

	//#CM49725
	// Class method --------------------------------------------------
	//#CM49726
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:24 $") ;
	}

	//#CM49727
	// Constructors --------------------------------------------------
	//#CM49728
	/**<en>
	 * Construct new <CODE>Station</CODE>.
	 </en>*/
	public Station()
	{
	}
	
	//#CM49729
	/**<en>
	 * Create the new instance of <code>Station</code>. Please use <code>StationFactory</code> 
	 * class if the instance which already has the  defined station is required.
	 * @param snum  : station no. preserved
	 * @see StationFactory
	 </en>*/
	public Station(String snum)
	{
		wNumber = snum; 
	}

	//#CM49730
	// Public methods ------------------------------------------------
	//#CM49731
	/**<en>
	 * Retrieve the station no.
	 * @return    :the station no.
	 </en>*/
	public String getNumber()
	{
		return(wNumber) ;
	}
	
	//#CM49732
	/**<en>
	 * Set the station no.
	 * @param  arg : the station no.
	 </en>*/
	public void setStationNumber(String arg)
	{
		wNumber = arg;
	}

	//#CM49733
	/**<en>
	 * Set the max number of carry isntructions sendable.
	 * @param nm :max number of carry instructions sendable
	 </en>*/
	public void setMaxInstruction(int nm)
	{
		wMaxInstruction = nm ;
	}

	//#CM49734
	/**<en>
	 * Retrieve the max number of carry instructions sendable.
	 * @return    :max number of carry instructions sendable
	 </en>*/
	public int getMaxInstruction()
	{
		return(wMaxInstruction) ;
	}
	
	//#CM49735
	/**<en>
	 * Check whether/not the station is dedicated for retrieval operation.
	 * This method cheks the operation based on the station type.
	 * @return   :true if the station is dedicated for unit retrieval, or false if not.
	 </en>*/
	public boolean isUnitOnly()
	{
		if (wType == STATION_TYPE_OUT)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//#CM49736
	/**<en>
	 * Set the station status.
	 * @param sts :station status
	 * @thrwos InvalidStatusException :Notifies if incorrect station status has been set.
	 </en>*/
	public void setStatus(int sts) throws InvalidStatusException
	{
		//#CM49737
		//<en> Check the status of the station.</en>
		switch(sts)
		{
			//#CM49738
			//<en> List of the correct station status.</en>
			case STATION_OK:
			case STATION_NG:
			case STATION_FAIL:
				break ;
				
			//#CM49739
			//<en> If incorrect station status were to set, it lets the exception occur and will not modify the station status.</en>
			default:
				//#CM49740
				//<en> This station status is undefined.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "STATUS";
				//#CM49741
				//<en> 6126009=Undefined {0} is set.</en>
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}
		
		//#CM49742
		//<en> Modify the station status.</en>
		wStatus = sts ;
	}

	//#CM49743
	/**<en>
	 * Retrieve the station status. List of station status is defined as fields 
	 * of this class.
	 * @return    station status
	 </en>*/
	public int getStatus()
	{
		return(wStatus) ;
	}

	//#CM49744
	/**<en>
	 * Set the station name.
	 * @param nm :the station name to set
	 </en>*/
	public void setName(String nm)
	{
		wName = nm ;
	}

	//#CM49745
	/**<en>
	 * Retrieve the the station name.
	 * @return    station name
	 </en>*/
	public String getName()
	{
		return(wName) ;
	}

	//#CM49746
	/**<en>
	 * Set the carry key.
	 * This will be preserved as pallet arrival information.
	 * @param ckey :carry key
	 </en>*/
	public void setCarryKey(String ckey)
	{
		wCarryKey = ckey ;
	}

	//#CM49747
	/**<en>
	 * Retrieve the carry key.
	 * @return    :carry key
	 </en>*/
	public String getCarryKey()
	{
		return(wCarryKey) ;
	}

	//#CM49748
	/**<en>
	 * Set the height of the pallet.
	 * This will be stored as cache before the arrival of the pallet.
	 * @param ht :height of the pallet. Meaning of specific value differs depending on the system.
	 </en>*/
	public void setHeight(int ht)
	{
		wHeight = ht ;
	}

	//#CM49749
	/**<en>
	 * Retrieve the height of the pallet.
	 * @return    :height of hte pallet. Meaning of specific value differs depending on the system.
	 </en>*/
	public int getHeight()
	{
		return(wHeight) ;
	}

	//#CM49750
	/**<en>
	 * Set the bar code data.
	 * This will be stored as cache before the arrival of the pallet.
	 * @param bcd :bar code data. Meaning of specific value differs depending on the system.
	 </en>*/
	public void setBCData(String bcd)
	{
		wBCData = bcd ;
	}

	//#CM49751
	/**<en>
	 * Retrieve the bar code data.
	 * @return    :bar code data. Meaning of specific value differs depending on the system.
	 </en>*/
	public String getBCData()
	{
		return(wBCData) ;
	}

	//#CM49752
	/**<en>
	 * Set the suspention flag.
	 * @param sus :suspention flag
	 </en>*/
	public void setSuspend(boolean sus)
	{
		wSuspend = sus ;
	}

	//#CM49753
	/**<en>
	 * Return whether/not this station is in suspention.
	 * @return    true  :suspended
	 * @return    false :available (active)
	 </en>*/
	public boolean isSuspend()
	{
		return(wSuspend) ;
	}

	//#CM49754
	/**<en>
	 * Return the suspention flag of this station.
	 * @return    :value of the suspention flag
	 </en>*/
	public int getSuspend()
	{
		if(wSuspend)
		{
			return SUSPEND;
		}
		
		return(NOT_SUSPEND) ;
	}
	
	//#CM49755
	/**<en>
	 * Set whether/not the arrival reporting check is operated.
	 * @param ari :whether/not the arrival reporting check is operated
	 </en>*/
	public void setArrivalCheck(boolean ari)
	{
		wArrivalCheck = ari ;
	}

	//#CM49756
	/**<en>
	 * Return whether/not the arrival reporting check is operated.
	 * @return    true  :arrival check in operation
	 * @return    false :no arrival check
	 </en>*/
	public boolean isArrivalCheck()
	{
		return(wArrivalCheck) ;
	}

	//#CM49757
	/**<en>
	 * Return whether/not the arrival reporting check is operated.
	 * @return    :value of whether/not the arrival reporting check is operated
	 </en>*/
	public int getArrivalCheck()
	{
		if(wArrivalCheck)
		{
			return ARRIVALCHECK;
		}
		
		return(NOT_ARRIVALCHECK) ;
	}

	//#CM49758
	/**<en>
	 * Set whether/not the load size is checked.
	 * @param load :whether/not the load size is checked
	 </en>*/
	public void setLoadSizeCheck(boolean load)
	{
		wLoadSizeCheck = load ;
	}

	//#CM49759
	/**<en>
	 * Return whether/not this station is available of removal.
	 * @return    true  :available of removal
	 * @return    false unavailable of removal
	 </en>*/
	public boolean isRemove()
	{
		return(wRemove) ;
	}

	//#CM49760
	/**<en>
	 * Return whether/not this station is available of removal.
	 * @param rem :hether/not this station is available of removal.
	 </en>*/
	public void setRemove(boolean rem) 
	{
		wRemove = rem ;
	}

	//#CM49761
	/**<en>
	 * Return whether/not the load size is checked.
	 * @return    true  :load size checked
	 * @return    false :no check
	 </en>*/
	public boolean isLoadSizeCheck()
	{
		return(wLoadSizeCheck) ;
	}

	//#CM49762
	/**<en>
	 * Set whether/not the load size is checked in this station.
	 * @return    :value of whether/not the arrival reporting is checked.
	 </en>*/
	public int getLoadSizeCheck()
	{
		if(wLoadSizeCheck)
		{
			return LOADSIZECHECK;
		}
		
		return(NOT_LOADSIZECHECK) ;
	}

	//#CM49763
	/**<en>
	 * Set the inventory checking flag.
	 * @param flag :inventory checking flag
	 * @thrwos InvalidStatusException :Notifies if incorrect value of inventory checking flag is set.
	 </en>*/
	public void setInventoryCheckFlag(int flag) throws InvalidStatusException
	{
		//#CM49764
		//<en> Check teh inventory checking flag.</en>
		switch(flag)
		{
			//#CM49765
			//<en> List of the correct inventory checking flag</en>
			case EMPTY_LOCATION_CHECK:
			case INVENTORYCHECK:
			case NOT_INVENTORYCHECK:
				break ;
				
			//#CM49766
			//<en> If incorrect inventory checking flag were to set, it lets the exception occur and will not modify the inventory checking flag.</en>
			default:
				//#CM49767
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "INVENTORYCHECKFLAG";
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}
		
		//#CM49768
		//<en> Modify the inventory checking flag.</en>
		wInventoryCheckFlag = flag ;
	}

	//#CM49769
	/**<en>
	 * Retrieve the inventory checking flag.
	 * @return    :nventory checking flag
	 </en>*/
	public int getInventoryCheckFlag()
	{
		return(wInventoryCheckFlag) ;
	}

	//#CM49770
	/**<en>
	 * Check teh value of inventory checking flag, then return true if inventory check or 
	 * empty location check is in process, or return false if inventory check is unprocessed.
	 * @return    :true if inventory/empty location checking or false if these checks are unprocessed.
	 </en>*/
	public boolean isInventoryCheck()
	{
		switch (wInventoryCheckFlag)
		{
			case EMPTY_LOCATION_CHECK:
			case INVENTORYCHECK:
				return true;
				
			case NOT_INVENTORYCHECK:
				return false;
				
			default:
				return false;
		}
	}

	//#CM49771
	/**<en>
	 * Check the valud of inventory checking flag, then return true if empty location is being checked
	 * or return false for other status.
	 * @return    :true if empty lcoation is checked, or false for all other cases.
	 </en>*/
	public boolean isEmptyLocationCheck()
	{
		switch (wInventoryCheckFlag)
		{
			case EMPTY_LOCATION_CHECK:
				return true;
				
			case NOT_INVENTORYCHECK:
			case INVENTORYCHECK:
				return false;
				
			default:
				return false;
		}
	}

	//#CM49772
	/**<en>
	 * Set whether/not the scheduled restorage data is created.
	 * @param type :whether/not the scheduled restorage data is created
	 *   true  : the scheduled restorage data is created
	 *   false : the scheduled restorage data is not created
	 </en>*/
	public void setReStoringOperation(boolean type)
	{
		wReStoringOperation = type ;
	}

	//#CM49773
	/**<en>
	 * Retrieve whether/not the scheduled restorage data is created.
	 * @return :whether/not the scheduled restorage data is created
	 *   true  : the scheduled restorage data is created
	 *   false : the scheduled restorage data is not created
	 </en>*/
	public boolean isReStoringOperation()
	{
		return(wReStoringOperation) ;
	}

	//#CM49774
	/**<en>
	 * Retrieve whether/not the scheduled restorage data is created.
	 * @return :whether/not the scheduled restorage data is created
	 </en>*/
	public int getReStoringOperation()
	{
		if(wReStoringOperation)
		{
			return CREATE_RESTORING;
		}
		
		return(NOT_CREATE_RESTORING) ;
	}

	//#CM49775
	/**<en>
	 * Set whether/not the carry instruction should be sent when restoring the retunrd load from pick-retrieval.
	 * @param type :whether/not the carry instruction shuold be sent
	 * @thrwos InvalidStatusException :Notifies if it set the status of whether/not to send the carry 
	 * instruction incorrectly.
	 </en>*/
	public void setReStoringInstruction(int type) throws InvalidStatusException
	{
		//#CM49776
		//<en>  whether/not the carry instruction should be sent </en>
		switch (type)
		{
			//#CM49777
			//<en> List of correct whether/not the carry instruction shuold be sent</en>
			case AGC_STORAGE_SEND:
			case AWC_STORAGE_SEND:
				break ;
				
			//#CM49778
			//<en> If incorrect whether/not the carry instruction shuold be sent were to set, </en>
			//<en> it lets the exception occur and will not modify the whether/not the carry instruction shuold be sent.</en>
			default:
				//#CM49779
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "RESTORINGINSTRUCTION";
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}
		
		//#CM49780
		//<en> Modify the whether/not the carry instruction shuold be sent.</en>
		wReStoringInstruction = type ;
	}

	//#CM49781
	/**<en>
	 * Retrieve whether/not the carry instruction should be sent
	 * @return    :whether/not the carry instruction should be sent
	 </en>*/
	public int getReStoringInstruction()
	{
		return(wReStoringInstruction) ;
	}

	//#CM49782
	/**<en>
	 * Set whether/not the PaletteOperation class should be called.
	 * @param po :whether/not the PaletteOperation class should be called
	 </en>*/
	public void setPoperationNeed(boolean po)
	{
		wPoperationNeed = po ;
	}

	//#CM49783
	/**<en>
	 * Return whether/not the PaletteOperation class should be called.
	 * @return    true  :PaletteOperation class should be called
	 * @return    false PaletteOperation class will not be called
	 </en>*/
	public boolean isPoperationNeed()
	{
		return(wPoperationNeed) ;
	}

	//#CM49784
	/**<en>
	 * Set the warehouse no. that this station belongs to.
	 * @param whnum :warehouse no, that this station belongs to
	 </en>*/
	public void setWarehouseStationNumber(String whnum)
	{
		wWarehouseStationNumber = whnum;
	}

	//#CM49785
	/**<en>
	 * Retrieve the warehouse no. that this station belongs to.
	 * @return :warehouse no.
	 </en>*/
	public String getWarehouseStationNumber()
	{
		return wWarehouseStationNumber;
	}

	//#CM49786
	/**<en>
	 * Set the parent station no.
	 * @param pnum :the parent station no.
	 </en>*/
	public void setParentStationNumber(String pnum)
	{
		wParentStationNumber = pnum;
	}

	//#CM49787
	/**<en>
	 * Retrieve the parent station no.
	 * @return :the parent station no.
	 </en>*/
	public String getParentStationNumber()
	{
		return wParentStationNumber;
	}

	//#CM49788
	/**<en>
	 * Set the parent station.
	 * Specifically, the parent station no. should be set.
	 * @param pst :the parent station to set
	 </en>*/
	public void setParentStation(Station pst)
	{
		setParentStationNumber(pst.getNumber());
	}

	//#CM49789
	/**<en>
	 * Set the aisle station no. available for storage/retrieval.
	 * @param anum :the aisle station no. available
	 </en>*/
	public void setAisleStationNumber(String anum)
	{
		wAisleStationNumber = anum;
	}

	//#CM49790
	/**<en>
	 * Retrieve the aisle station no. available.
	 * @return :the aisle station no. available
	 </en>*/
	public String getAisleStationNumber()
	{
		return wAisleStationNumber;
	}

	//#CM49791
	/**<en>
	 * Set the aisle station no. available of storage/retrieval.
	 * Specifically set the aisle station no.
	 * @param ast :the aisle station no. available of storage/retrieval
	 </en>*/
	public void setAisleStation(Station ast)
	{
		setAisleStationNumber(ast.getNumber());
	}

	//#CM49792
	/**<en>
	 * Set the carry-to station no.
	 * @param nnum :the carry-to station no.
	 </en>*/
	public void setNextStationNumber(String nnum)
	{
		wNextStationNumber = nnum;
	}

	//#CM49793
	/**<en>
	 * Retrieve the carry-to station no.
	 * @return :the carry-to station no.
	 </en>*/
	public String getNextStationNumber()
	{
		return wNextStationNumber;
	}

	//#CM49794
	/**<en>
	 * Set the end-use station no.
	 * @param lnum :end-use station no.
	 </en>*/
	public void setLastUsedStationNumber(String lnum)
	{
		wLastUsedStationNumber = lnum;
	}

	//#CM49795
	/**<en>
	 * Retrieve the end-use station no.
	 * @return :end-use station no.
	 </en>*/
	public String getLastUsedStationNumber()
	{
		return wLastUsedStationNumber;
	}

	//#CM49796
	/**<en>
	 * Set the reject station no.
	 * @param rnum :reject station no.
	 </en>*/
	public void setRejectStationNumber(String rnum)
	{
		wRejectStationNumber = rnum;
	}

	//#CM49797
	/**<en>
	 * Retrieve the reject station no.
	 * Specifically set the reject station no.
	 * @return :reject station no.
	 </en>*/
	public String getRejectStationNumber()
	{
		return wRejectStationNumber;
	}

	//#CM49798
	/**<en>
	 * Set the mode switch type.
	 * @param mt :the mode switch type
	 </en>*/
	public void setModeType(int mt)
	{
		wModeType = mt;
	}

	//#CM49799
	/**<en>
	 * Retrieve the mode switch type.
	 * @return    :the mode switch type
	 </en>*/
	public int getModeType()
	{
		return wModeType;
	}

	//#CM49800
	/**<en>
	 * Set the current work mode.
	 * @param mode   :work mode
	 </en>*/
	public void setCurrentMode(int mode)
	{
		wCurrentMode = mode;
	}

	//#CM49801
	/**<en>
	 * Retrieve the current work mode.
	 * @return   :work mode
	 </en>*/
	public int getCurrentMode()
	{
		return wCurrentMode;
	}

	//#CM49802
	/**<en>
	 * Set the segment of mode switch request.
	 * @param request   :segment of mode switch request
	 </en>*/
	public void setChangeModeRequest(int request)
	{
		wChangeModeRequest = request;
	}

	//#CM49803
	/**<en>
	 * Retrieve the segment of mode switch request.
	 * @return   :segment of mode switch request
	 </en>*/
	public int getChangeModeRequest()
	{
		return wChangeModeRequest;
	}
	//#CM49804
	/**<en>
	 * Set the start time of mode switch request.
	 * @param time  :start time of mode switch request
	 </en>*/
	public void setChangeModeRequestTime(java.util.Date time)
	{
		wChangeModeRequestTime = time;
	}
	
	//#CM49805
	/**<en>
	 * Retrieve the start time of mode switch request.
	 * @return time  :start time of mode switch request
	 </en>*/
	public java.util.Date getChangeModeRequestTime()
	{
		return wChangeModeRequestTime;
	}

	//#CM49806
	/**<en>
	 * Set the supporting group controller.
	 * @param gctl :group controller to set
	 </en>*/
	public void setGroupController(GroupController gctl)
	{
		wGC = gctl ;
	}

	//#CM49807
	/**<en>
	 * Retrieve the supporting group controller.
	 * @return    :the supporting group controller
	 </en>*/
	public GroupController getGroupController()
	{
		return(wGC) ;
	}

	//#CM49808
	/**<en>
	 * Set the max pallet quantity preserved.
	 * @param pnum :max pallet quantity preserved to set
	 </en>*/
	public void setMaxPaletteQuantity(int pnum)
	{
		wMaxPaletteQuantity = pnum ;
	}

	//#CM49809
	/**<en>
	 * Retrieve the max pallet quantity preserved.
	 * @return    :max pallet quantity preserved
	 </en>*/
	public int getMaxPaletteQuantity()
	{
		return(wMaxPaletteQuantity) ;
	}

	//#CM49810
	/**<en>
	 * Set the station type.<BR>
	 * The list of station type (storage/retrieval) is defined in the field of this class.
	 * @param type :station type to set
	 * @thrwos InvalidStatusException :Notifies if incorrect station type has been set.
	 </en>*/
	public void setType(int type) throws InvalidStatusException
	{
		//#CM49811
		//<en> Check the station type</en>
		switch(type)
		{
			//#CM49812
			//<en> List of corect types</en>
			case STATION_TYPE_OTHER:
			case STATION_TYPE_IN:
			case STATION_TYPE_OUT:
			case STATION_TYPE_INOUT:
				break ;
				
			//#CM49813
			//<en> If incorrect station type were to set, it lets the exception occur and will not modify the station type.</en>
			default:
				//#CM49814
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "STATIONTYPE";
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}

		//#CM49815
		//<en> Modify the station type.</en>
		wType = type ;
	}

	//#CM49816
	/**<en>
	 * Retrieve the station type.<BR>
	 * The list of station types (storage/retrieval) is defined in the field of this class.
	 * @return    :station type
	 </en>*/
	public int getType()
	{
		return(wType) ;
	}

	//#CM49817
	/**<en>
	 * Retrieve the storage setting type.
	 * The list of the storage setting type is defined in the field of this class.
	 * @return    :the storage setting type
	 </en>*/
	public int getInSettingType()
	{
		return(wInSettingType) ;
	}

	//#CM49818
	/**<en>
	 * Set the the storage setting type.
	 * The list of the storage setting type is defined in the field of this class.
	 * @param type :the storage setting type to set
	 * @thrwos InvalidStatusException :Notifies if incorrect storage setting type is set.
	 </en>*/
	public void setInSettingType(int type) throws InvalidStatusException
	{
		//#CM49819
		//<en> Check the storage setting type</en>
		switch(type)
		{
			//#CM49820
			//<en> List of correct setting type</en>
			case IN_SETTING_OTHER:
			case IN_SETTING_CONFIRM:
			case IN_SETTING_PRECEDE:
				break ;
				
			//#CM49821
			//<en> If incorrect storage setting type were to set, it lets the exception occur and will not modify the storage setting type.</en>
			default:
				//#CM49822
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "SETTINGTYPE";
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}

		//#CM49823
		//<en> Modify the storage setting type.</en>
		wInSettingType = type ;
	}

	//#CM49824
	/**<en>
	 * Retrieve the type of workshop.
	 * List of workshop types is defined in the field of this class.
	 * @return    :type of workshop
	 </en>*/
	public int getWorkPlaceType()
	{
		return (wWorkPlaceType) ;
	}

	//#CM49825
	/**<en>
	 * Set the type of workshop.<BR>
	 * List of workshop types is defined in the field of this class.
	 * @param type :type of workshop to set
	 * @thrwos InvalidStatusException :Notifies if incorrect workshop type is set.
	 </en>*/
	public void setWorkPlaceType(int type) throws InvalidStatusException
	{
		//#CM49826
		//<en> Check the type of workshop.</en>
		switch(type)
		{
			//#CM49827
			//<en> List of correct type of workshop</en>
			case NOT_WORKPLACE:
			case STAND_ALONE_STATIONS:
			case AISLE_CONMECT_STATIONS:
			case MAIN_STATIONS:
			case WPTYPE_FLOOR:
			case WPTYPE_ALL:
				break ;
			//#CM49828
			//<en> If incorrect type of workshop were to set, it lets the exception occur and will not modify the type of workshop.</en>
			default:
				//#CM49829
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "WorkPlaceType";
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}

		//#CM49830
		//<en> Modify the type of workshop.</en>
		wWorkPlaceType = type ;
	}

	//#CM49831
	/**<en>
	 * Set the type of on-line indication of this station.
	 * List of on-line indication types is defined in the field of this class.
	 * @param disp :type of on-line indication to set
	 * @thrwos InvalidStatusException :Notifies if incorrect on-line indication type is set.
	 </en>*/
	public void setOperationDisplay(int disp) throws InvalidStatusException
	{
		//#CM49832
		//<en> Check the type of on-line indications.</en>
		switch(disp)
		{
			//#CM49833
			//<en> List of correct types of on-line indication</en>
			case NOT_OPERATIONDISPLAY:
			case OPERATIONDISPLAY:
			case OPERATIONINSTRUCTION:
				break ;
				
			//#CM49834
			//<en> If incorrect on-line indication types were to set, it lets the exception occur and will not modify the type of on-line indication.</en>
			default:
				//#CM49835
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = "OperataionDisplay";
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "Station", tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
		}

		//#CM49836
		//<en> Modify the type of on-line indication.</en>
		wOperataionDisplay = disp ;
	}

	//#CM49837
	/**<en>
	 * Return whether/not the on-line indication is operated in this station.
	 * @return    whether/not the on-line indication is operated
	 </en>*/
	public int getOperationDisplay()
	{
		return wOperataionDisplay ;
	}

	//#CM49838
	/**<en>
	 * Set whether/not the carry instruction should be sendable to this station.
	 * @param    snd :true if setting the station sendable
	 </en>*/
	public void setSendable(boolean snd)
	{
		wSendable = snd ;
	}

	//#CM49839
	/**<en>
	 * Return whether/not the carry instruction should be sendable to this station.
	 * @return    true  :sendable
	 * @return    false :not sendable
	 </en>*/
	public boolean isSendable()
	{
		return(wSendable) ;
	}

	//#CM49840
	/**<en>
	 * Return hether/not the carry instruction should be sendable to this station.
	 * @param    snd :true if sendable
	 </en>*/
	public boolean getSendable()
	{
		return wSendable ;
	}

	//#CM49841
	/**<en>
	 * Return whether/not this station is a storage station.
	 * Return trud for storage/retrieval stations.
	 * @return    true  :storage station
	 * @return    false :this is not the storage station
	 </en>*/
	public boolean isInStation()
	{
		boolean itype = true ;

		switch(wType)
		{
			//#CM49842
			//<en> Check the type</en>
			case STATION_TYPE_IN:
			case STATION_TYPE_INOUT:
				itype = true ;
				break ;
				
			case STATION_TYPE_OUT:
				itype = false ;
				break ;
		}
		
		return(itype) ;
	}

	//#CM49843
	/**<en>
	 * Return whether/not this station is a retrieval station.
	 * Return true for storage/retrieval stations.
	 * @return    true  :retrieval station
	 * @return    false :this is not a retrieval station.
	 </en>*/
	public boolean isOutStation()
	{
		boolean itype = true ;

		switch(wType)
		{
			//#CM49844
			//<en> Check the type</en>
			case STATION_TYPE_OUT:
			case STATION_TYPE_INOUT:
				itype = true ;
				break ;
				
			case STATION_TYPE_IN:
				itype = false ;
				break ;
		}
		return(itype) ;
	}

	//#CM49845
	/**<en>
	 * Set the name of the class.
	 * @param cls :the name of the class
	 </en>*/
	public void setClassName(String cls)
	{
		wClassName = cls ;
	}

	//#CM49846
	/**<en>
	 * Retrieve the name of the class
	 * @return    :the name of the class
	 </en>*/
	public String getClassName()
	{
		return(wClassName) ;
	}

	//#CM49847
	/**<en>
	 * Return the string representation.
	 * @return    string representation
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		try
		{
			buf.append("\nStation Number:" + wNumber) ;
			buf.append("\nMax Palette Quantity:" + Integer.toString(wMaxPaletteQuantity)) ;
			buf.append("\nSendable:" + wSendable) ;
			buf.append("\nStation Status:" + getStatus()) ;
			if (wGC == null)
				buf.append("\nGroup Controller: null") ;
			else
				buf.append("\n------Group Controller-------");
				buf.append("\nGroup Controller:" + wGC.toString() ) ;
				buf.append("\n------Group Controller-------");
			buf.append("\nType:" + Integer.toString(wType)) ;
			buf.append("\nInSettingType:" + Integer.toString(wInSettingType)) ;
			buf.append("\nWorkPlaceType:" + Integer.toString(wWorkPlaceType)) ;
			buf.append("\nOperataionDisplay:" + wOperataionDisplay) ;
			buf.append("\nName:" + wName ) ;
			buf.append("\nSuspend:" + wSuspend) ;
			buf.append("\nArrivalCheck:" + wArrivalCheck) ;
			buf.append("\nLoadSizeCheck:" + wLoadSizeCheck) ;
			buf.append("\nInventoryCheckFlag:" + wInventoryCheckFlag) ;
			buf.append("\nReStoringOperation:" + wReStoringOperation) ;
			buf.append("\nReStoringInstruction:" + wReStoringInstruction) ;
			buf.append("\nPoperationNeed:" + wPoperationNeed) ;
			buf.append("\nWarehouse:" + wWarehouseStationNumber) ;
			buf.append("\nParent Station:" + wParentStationNumber) ;
			buf.append("\nAisle Station:" + wAisleStationNumber) ;
			buf.append("\nNext Station:" + wNextStationNumber) ;
			buf.append("\nLastUsed Station:" + wLastUsedStationNumber) ;
			buf.append("\nReject Station:" + wRejectStationNumber) ;
			buf.append("\nModeType:" + wModeType) ;
			buf.append("\nCurrentMode:" + wCurrentMode);
			buf.append("\nChangeModeRequest:" + wChangeModeRequest);
			buf.append("\nChangeModeRequestTime:" + wChangeModeRequestTime);
			buf.append("\nCarryKey:" + wCarryKey ) ;
			buf.append("\nHeight:" + Integer.toString(wHeight) ) ;
			buf.append("\nBCdata:" + wBCData ) ;
			buf.append("\nClass Name:" + wClassName ) ;
		}
		catch (Exception e)
		{
		}
		
		return (buf.toString()) ;
	}

	//#CM49848
	// Package methods -----------------------------------------------

	//#CM49849
	// Protected methods ---------------------------------------------

	//#CM49850
	// Private methods -----------------------------------------------

}
//#CM49851
//end of class

