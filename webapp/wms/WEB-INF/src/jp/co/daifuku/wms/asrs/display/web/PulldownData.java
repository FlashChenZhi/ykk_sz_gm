// $Id: PulldownData.java,v 1.2 2006/10/26 03:11:48 suresh Exp $
package jp.co.daifuku.wms.asrs.display.web ;

//#CM40858
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.dbhandler.ASHardZoneHandler;
import jp.co.daifuku.wms.asrs.dbhandler.ASStationHandler;
import jp.co.daifuku.wms.asrs.dbhandler.ASWareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareHouse;
//#CM40859
/**
 * Defined in this class is the method by which the data for pull-down display will be retrieved.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/01/18</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/08</TD><TD>torigaki</TD><TD>mehod for on-line indication added.<BR>
 * A method has been added so that the pull-down list of stations on on-line indication screen
 * only shows the stations that operate the on-line indications.
 * </TD></TR>
 * <TR><TD>2003/12/10</TD><TD>M.INOUE</TD><TD>storage stations added except for workshops.</TD></TR>
 * <TR><TD>2003/12/12</TD><TD>M.KAWASHIMA</TD><TD>Modified so that data in pull-downm for warehouses
 * should be sorted.</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>M.INOUE</TD><TD>Added a pull-down type which returns the area including only one automated warehouse.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:11:48 $
 * @author  $Author: suresh $
 */
public class PulldownData extends Object
{
	//#CM40860
	// Class fields --------------------------------------------------
	//#CM40861
	/**
	 * pull-down delimiter
	 */
	public static final String PDDELIM = "," ;
	//#CM40862
	/**
	 * Represents the pull-down for storage setting. (station type: storage, for storage/retrieval)<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method (terminal no. specified)
	 */
	public static final String STATION_STORAGE = "10";

	//#CM40863
	/**
	 * Represents the pull-down for retrieval setting. (station type: retrieval, for storage/retrieval)<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method (terminal no. specified)
	 */
	public static final String STATION_RETRIEVAL = "11";

	//#CM40864
	/**
	 * Represents the pull-down for replenishment storage. (station type: for storage/retrieval, 
	 * storage station of U-shaped )<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method.(terminal no.specified)
	 */
	public static final String STATION_ADD_STORAGE = "12";
	
	//#CM40865
	/**
	 * Represents the pull-down for restorage data maintenance. (station type: retrieval, for storage/retrieval,
	 * SENDABLE stations)<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method.(no specification of terminal no.)
	 */
	public static final String STATION_RE_STORAGE_MNT = "13";

	//#CM40866
	/**
	 * Represents the pull-down for work maintenance. (station type must be SENDABLE)<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method.(no specification of terminal no.)
	 */
	public static final String STATION_WORK_MNT = "14";

	//#CM40867
	/**
	 * Represents the pull-down for direct travel (destination). (station type: retrieval, for storage/retrieval,
	 * SENDABLE stations)<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method.(no specification of terminal no.)
	 */
	public static final String STATION_DIRECT_TRANS_TO = "15";
	
	//#CM40868
	/**
	 * Represents the pull-down for on-line indications. (station type: SENDABLE)<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method. (terminal no. specified)
	 */
	public static final String SATAION_WORK_DISPLAY = "16";
	//#CM40869
	/**
	 * Represents the pull-down for station mode switching. (station type: for storage/retrieval, SENDABLE)<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method. (terminal no. specified)
	 */
	public static final String STATION_MODE_MNT = "17";
	
	//#CM40870
	/**
	 * Represents the pull-down for inventory checking. (station type: for storage/retrieval), retrieval station 
	 * for U-schaped)<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method. (terminal no. specified)
	 */
	public static final String STATION_INVENTORY_CHECK = "18";

	//#CM40871
	/**
	 * Represents the pull-down for stations in empty location.
	 */
	public static final String STATION_EMPTYLOCATION_CHECK = "19";

	//#CM40872
	/**
	 * Represents the pull-down for stations in floor storage warehouses.
	 */
	public static final String STATION_FLOOR = "20";
	//#CM40873
	/**
	 * Represents the pull-down for stations in automated warehouses.
	 */
	public static final String STATION_AUTO = "21";
	
	//#CM40874
	/**
	 * Represents the pull-down for stations regardless of floor storage/automated distinctions.  (station type:
	 * storage stations, for storage/retrieval stations, SENDABLE)<BR>
	 * Specified through the parameter of <code>getWareHousePulldownData</code> method.
	 */
	public static final String STATION_ALL = "22";

	//#CM40875
	/**
	 * Represents the pull-down for stations available for storage (except for workshops).<BR>
	 * Specified through the parameter of <code>getStationPulldownData</code> method.
	 */
	public static final String STATION_STORAGE_SENDABLE = "23";

	//#CM40876
	/**
	 * Represents the pull-down for hard zones. (type: hard zone)<BR>
	 * Specified through the parameter of <code>getZonePulldownData</code> method.
	 */
	public static final String ZONE_HARD = "30";

	//#CM40877
	/**
	 * Represents the pull-down for soft zones. (type:soft zone)<BR>
	 * Specified through the parameter of <code>getZonePulldownData</code> method.
	 */
	public static final String ZONE_SOFT = "31";
	
	//#CM40878
	/**
	 * Represents the pull-down for hard zones and soft zones. (type: hard zone, soft zone)<BR>
	 * Specified through the parameter of <code>getZonePulldownData</code> method.
	 */
	public static final String ZONE_ALL = "32";

	//#CM40879
	/**
	 * Represents the pull-down for aisles.
	 */
	public static final String AISLE = "40";

	//#CM40880
	/**
	 * Represents the pull-down for banks.
	 */
	public static final String BANK = "50";
	//#CM40881
	/**
	 * Represents the pull-down for group controllers.
	 */
	public static final String GROUPCONTROLLER = "60";


	//#CM40882
	// Class variables -----------------------------------------------

	//#CM40883
	/**
	 * <CODE>Locale</CODE> object
	 */
	protected Locale wLocale = null ;

	//#CM40884
	/**
	 * <CODE>Hashtable</CODE> to preserve the searched pull-down data
	 */
	protected Hashtable wPulldownDataTable = null;

	//#CM40885
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM ;

	//#CM40886
	/**
	 * The array which preserves all the warehouses.
	 */
	protected WareHouse[] wWareHouseArray = null;

	//#CM40887
	/**
	 * The array which preserves all the storage area.
	 */
	protected Area[] wAreaArray = null;

	//#CM40888
	/**
	 * Preserves the terminal no.
	 */
	protected String wTerminalNo = "";

	//#CM40889
	/**
	 * Station no. which represents all stations.<BR>
	 * It is required to get this no. from ASRSParam resource.
	 */
	protected String ALLSTATION = AsrsParam.ALL_RETRIEVAL_STATION;

	//#CM40890
	/**
	 * Station no. which represents all warehouses.<BR>
	 * It is required to get this data from ASRSParam resource.
	 */
	protected String ALLWAREHOUSE = AsrsParam.ALL_WH_NO;

	//#CM40891
	/**
	 * Aisle no. which represents all the aisle.<BR>
	 * It is required to get this data from ASRSParam resource.
	 */
	protected String ALLAISLE = AsrsParam.ALL_AISLE_NO;

	//#CM40892
	/**
	 * Bank no. which represents all the bank.<BR>
	 * It is required to get this data from ASRSParam resource.
	 */
	protected String ALLBANK = AsrsParam.ALL_BANK_NO;

	//#CM40893
	/**
	 * Controller no. which represents all the group controller.<BR>
	 * It is required to get this data from ASRSParam resource.
	 */
	protected String ALLGCNUMBER = AsrsParam.ALL_GROUPCONTROLLER_NO;

	//#CM40894
	/**
	 * This value is used when retrieving the station list with status SENDABLE.
	 */
	protected final int ON_SENDABLE = Station.SENDABLE_TRUE;

	//#CM40895
	/**
	 * This value is used when retrieving the station list with status NOT_SENDABLE.
	 */
	protected final int NOT_SENDABLE= Station.NOT_SENDABLE;

	//#CM40896
	/**
	 * Initial display flag (to show the items for initial display)
	 */
	protected final String FIRSTDISP_TRUE = "1";

	//#CM40897
	/**
	 * Initial display flag (to show the items for initial display)
	 */
	protected final String FIRSTDISP_FALSE = "0";

	//#CM40898
	/**
	 * Include the all- item (to be the key for the preservation of pull-down data.)
	 */
	protected final String ALLITEM_TRUE = "1";

	//#CM40899
	/**
	 * Not include the all- item (to be the key for the preservation of pull-down data.)
	 */
	protected final String ALLITEM_FALSE = "0";

	//#CM40900
	/**
	 * Not include the all- item (to be the key for the preservation of pull-down data.)
	 */
	protected final String ALLWAREHOUSE_TRUE = "3";



	//#CM40901
	// Class method --------------------------------------------------
	//#CM40902
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:11:48 $") ;
	}

	//#CM40903
	// Constructors --------------------------------------------------
	//#CM40904
	/**
	 * Calling this constructor saves locale and terminal no. 
	 * and initialize pulldown data storing hashtable
	 * @param locale <CODE>Locale</CODE>
	 * @param terminalNo terminal no.
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	//#CM40905
	/**
	 * If this constructor is called, preserve the locale and terminal no. as instance variables 
	 * and initialize the HashTable for the preservation of pull-down data.
	 * @param locale <CODE>Locale</CODE>
	 * @param terminalNo :terminal no.
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 */
	public PulldownData(Locale locale, String terminalNo)throws ReadWriteException
	{
		wLocale = locale;
		wTerminalNo = terminalNo;
		//#CM40906
		//Initialize the pull-down data.
		wPulldownDataTable = new Hashtable();
	}

	//#CM40907
	// Public methods ------------------------------------------------

	//#CM40908
	/**
	 * With no specification of terminal no., return data to show in warehouse pull-down list in
	 * form of <code>String</code> array.<BR>
	 * <code>Connection</code>, type of pull-down list (only the following accepted), warehouse 
	 * station no. to show on initial display and a flag which determines whether/not to include 
	 * 'all warehouses' will be provided as paramters.
	 * <BR>
	 *  - display floor storage warehouse only. (specify: WAREHOUSE_FLOOR)<BR>
	 *  - display automated warehouse only. (specify: WAREHOUSE_AUTO)<BR>
	 *  - display both automated warehouse and floor storage warehouse. (specify: WAREHOUSE_ALL)<BR>
	 * <BR>
	 * Add to ArrayList the data : Value+PDDELIM+Name+PDDELIM+ parentVALUE +PDDELIM+ initial display 
	 * flag ("0" or "1").<BR>
	 * Data set with "1" for initial display will be shown initially at the display of pull-down list.<BR>
	 * Let the HashTable preserve the data to use with pull-down type as a key when this method is 
	 * firstly called.
	 * @param conn <CODE>Connection</CODE>
	 * @param type :specifies the pull-down type.
	 * @param selected  :warehouse station no. to show at initial display. Please enter "" for no specification.
	 * @param isAllItem :true if display 'all warehouse'
	 * @return :returns required data in form of <code>String</code> array to dray pull-down.
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 */
	public String[] getWareHousePulldownData(Connection conn, int type, String selected, boolean isAllItem) throws ReadWriteException
	{
		//#CM40909
		//Set "" in case it is null.
		if(selected == null ) selected = "";
		//#CM40910
		//*** Initial processing ends here. ***

		//#CM40911
		//*** Check whether/not the pull-down data has been searched. ***
		String allItem = ALLITEM_FALSE;
		String key = "";
		if(isAllItem)
		{
			allItem = ALLITEM_TRUE;
		}
		//#CM40912
		//Create the key of HashTable.
		key = "getWareHousePulldownData" + type + allItem;
		//#CM40913
		//In case the data is already searched,
		if(wPulldownDataTable.containsKey(key))
		{
			//#CM40914
			//Set the data for initial display and return.
			return changeToSelected((ArrayList)wPulldownDataTable.get(key), selected, "");
		}
		//#CM40915
		//*** Check of whehter/not pull-down data is already searched ends here. ***

		ArrayList pulldownData = new ArrayList();
		//#CM40916
		//The array of station no. of specified type.
		
		//#CM40917
		//Pull-down data for floor storage warehouse
		if(type  == WareHouse.CONVENTIONAL_WAREHOUSE)
		{
		}
		//#CM40918
		//Pull-down data for automated warehouse
		else if(type == WareHouse.AUTOMATID_WAREHOUSE)
		{
		}
		//#CM40919
		//Pull-down data for automated warehouse and floor storage warehouse
		else if(type == WareHouse.ALL_WAREHOUSE)
		{
		}
		else
		{
			System.out.println("This pull-down type cannot be specified in this method!!");
			return null;
		}

		ASWareHouseHandler wAwhHandler = new ASWareHouseHandler(conn);
		WareHouse[] rWareHouse = wAwhHandler.getWareHouseNo(wTerminalNo, type);

		//#CM40920
		//**** Add 'all warehouse' to the ArrayList. ****
		if(isAllItem)
		{
			//#CM40921
			// 'All warehouse' is to be retrieved from resource.
			String allWarehouse = DisplayText.getText("ALLWAREHOUSE_TEXT");
			//#CM40922
			// In case showing all warehouse at initial display,
			if(selected.equals(ALLWAREHOUSE))
			{
				pulldownData.add(0,ALLWAREHOUSE+PDDELIM+ allWarehouse +PDDELIM+ALLWAREHOUSE+PDDELIM+  "1");
			}
			else
			{
				pulldownData.add(0,ALLWAREHOUSE+PDDELIM+ allWarehouse +PDDELIM+ALLWAREHOUSE+PDDELIM+  "0");
			}
		}
		//#CM40923
		//**** Add warehouse to ArrayList. ****
		for (int lc=0; lc<rWareHouse.length; lc++)
		{

			String wareHouseStationNo = rWareHouse[lc].getStationNumber();
			//#CM40924
			//Name to show in pull-down is 'warehouse no. : warehouse name'.
			String wareHouseName = Integer.toString(rWareHouse[lc].getWareHouseNumber())
								+ ":" + rWareHouse[lc].getWareHouseName();
			//#CM40925
			//Data to show at initial display
			if(wareHouseStationNo.equals(selected))
			{
				pulldownData.add(wareHouseStationNo+PDDELIM+wareHouseName+PDDELIM+ wareHouseStationNo +PDDELIM+FIRSTDISP_TRUE);
			}
			//#CM40926
			//All other data
			else
			{
				pulldownData.add(wareHouseStationNo+PDDELIM+wareHouseName+PDDELIM+ wareHouseStationNo +PDDELIM+FIRSTDISP_FALSE);
			}
		}
		//#CM40927
		//**** Preserve the pull-down data. ****
		wPulldownDataTable.put(key, pulldownData);
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}

	//#CM40928
	/**
	 * the data to display hardzone pulldown (warehouse) is returned as <code>String</code> array<BR>
	 * <code>Connection</code>, pulldown type in argument (only the following possible)
	 * initialize hardzone
	 * <BR>
	 * Value+PDDELIM+Name+PDDELIM+ new VALUE +PDDELIM+data with initial flag ("0 or "1")  is added to an arraylist<BR>
	 * the data set as "1" during initial display is initialized in the pull down<BR>
	 * the data used first time by this method is stored in a hashtable. after teh second time, 
	 * return the stored data as it is
	 * @param conn <CODE>Connection</CODE>
	 * @param selected hardzone initial display. enter "" if not specified
	 * @return return mandatory pulldown data in a <CODE>String</CODE> array
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public String[] getHardZonePulldownData(Connection conn, String selected) throws ReadWriteException
	{
		//#CM40929
		//Set "" in case it is null.
		if(selected == null ) selected = "";
		//#CM40930
		//*** Initial processing ends here. ***

		//#CM40931
		//Create the key of HashTable.
		String key = "getHardZonePulldownData" + wTerminalNo;
		//#CM40932
		//In case the data is already searched,
		if(wPulldownDataTable.containsKey(key))
		{
			//#CM40933
			//Set the data for initial display and return.
			return changeToSelected((ArrayList)wPulldownDataTable.get(key), selected, "");
		}
		//#CM40934
		//*** Check of whehter/not pull-down data is already searched ends here. ***

		ArrayList pulldownData = new ArrayList();

		ASHardZoneHandler wAhzHandler = new ASHardZoneHandler(conn);
		HardZone[] rHardZone = wAhzHandler.getHardZoneNo(wTerminalNo);

		//#CM40935
		//**** add hard zone to arraylist ****
		for (int lc=0; lc<rHardZone.length; lc++)
		{
			String wHardZone = Integer.toString(rHardZone[lc].getHardZoneID());
			String wareHouseStationNo = rHardZone[lc].getWHStationNumber();
			//#CM40936
			//Name to show in pull-down is 'hardzoneid'.
			String wHardZoneDisp = Integer.toString(rHardZone[lc].getHardZoneID());
			//#CM40937
			//Data to show at initial display
			if(wHardZone.equals(selected))
			{
				pulldownData.add(wHardZone+PDDELIM+wHardZoneDisp+PDDELIM+ wareHouseStationNo +PDDELIM+FIRSTDISP_TRUE);
			}
			//#CM40938
			//All other data
			else
			{
				pulldownData.add(wHardZone+PDDELIM+wHardZoneDisp+PDDELIM+ wareHouseStationNo +PDDELIM+FIRSTDISP_FALSE);
			}
		}
		//#CM40939
		//**** Preserve the pull-down data. ****
		wPulldownDataTable.put(key, pulldownData);
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}

	//#CM40940
	/**
	 * Array the data to display pull-down of storage type in form of <code>String</code> then return.
	 * @param selected :storage type for initial display. Please enter "" if there is no specification.
	 * @return :return data in form of <code>String</code> array which is required to draw the pull-down.
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 */
	public String[] getReStoringPullDownData(String selected) throws ReadWriteException
	{

		String[] restoring = new String[2];
		restoring[0] = DisplayText.getText("RESTORING_RESTORING_0");
		restoring[1] = DisplayText.getText("RESTORING_RESTORING_1");

		String[] value = new String[2];
		value[0] = Integer.toString(Stock.STOCK_RESTORING_NEWSTORAGE);
		value[1] = Integer.toString(Stock.STOCK_RESTORING_RESTORAGE);

		if(selected == null ) selected = "";

		ArrayList pulldownData = new ArrayList();

		for(int i = 0; i < value.length; i++)
		{

			String val = value[i];
			String resto = restoring[i];
			//#CM40941
			//Data to show at initial display
			if(val.equals(selected))
			{
				pulldownData.add(val+PDDELIM+resto+PDDELIM+resto+PDDELIM+ FIRSTDISP_TRUE);
			}
			//#CM40942
			//All other data
			else
			{
				pulldownData.add(val+PDDELIM+resto+PDDELIM+resto+PDDELIM+ FIRSTDISP_FALSE);
			}
		}
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}

	//#CM40943
	/**
	 * Array the data, to display the pull-down of stations(workshop) available for specified terminals, 
	 * in form of <code>String</code> then return.<BR>
	 * Data required to create the pull-down should all be retreieved from STATION table.<BR>
	 * Type of warehouse (automated or floor storage) should be specified also in this method.
	 * As parameters, <code>Connection</code>, type of pull-down (only the following accepted), warehouse type
	 * and the stations for initial display should be provided.
	 *<BR>
	 *	<B>Pull-down for storage (specify STATION_STORAGE)</B><BR>
	 *		- station type in STATION table : storage, storage/retrieval<BR>
 	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR><BR>
 	 *	<B>Pull-down for storage(specify STATION_STORAGE_SENDABLE)</B><BR>
	 *		- station type in STATION table :storage. storage/retrieval<BR>
 	 *		- sendability : SENDABLE only <BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR><BR>
	 *	<B>Pull-down for retrieval (specify STATION_RETRIEVAL)</B><BR>
	 *		- station type in STATION table : storage, storage/retrieval<BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR><BR>
	 *
	 * 	<B>Pull-down for replenishment storage, inventory checking (specify STATION_ADD_STORAGE)</B><BR>
	 *		- station type in STATION table : retrieval side of U-shaped station,storage/retrieval<BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR>
	 * 	<B>Pull-down for on-line indications (specify STAION_WORK_DISPLAY)</B><BR>
	 *		- sendability : SENDABLE only <BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR>
	 * Add to ArrayList the data: Value+ PDDELIM+Name+ PDDELIM+ parent VALUE + PDDELIM+Initial display flag ("0" or "1").<BR>
	 * Data which is set with "1" will be shown at initial display of pull-down.<BR>
	 * Preserve the data to be used when this method is called for the first time in HashTable, with 
	 * pull-down type as a key. For the 2nd and further use, return the preserved data as it is.
	 * @param conn <CODE>Connection</CODE>
	 * @param type :specifies the pull-down type.
	 * @param autoType :specifies the warehouse type. (automated warehouse:true, floor storage:false)	 
	 * @param selected :station no. to show at initial dispaly. Please set "" if there is no specification.
	 * @return :return data in form of <code>String</code> array which is required to draw the pull-down.
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 */
	public String[] getSagyobaPulldownData(Connection conn, String type, boolean autoType, String selected) throws ReadWriteException
	{
		//#CM40944
		//Set "" in case it is null.
		if(selected == null ) selected = "";
		//#CM40945
		//*** Initial processing ends here. ***

		//#CM40946
		//*** Check whether/not the pull-down data has been searched. ***
		//#CM40947
		//Create the key of HashTable.
		String key = "getSagyobaPulldownData" + type + autoType + wTerminalNo;
		//#CM40948
		//In case the data is already searched,
		if(wPulldownDataTable.containsKey(key))
		{
			//#CM40949
			//Set the data for initial display and return.
			return changeToSelected((ArrayList)wPulldownDataTable.get(key), selected, "");
		}
		//#CM40950
		//*** Check of whehter/not pull-down data is already searched ends here. ***
		ArrayList pulldownData = new ArrayList();
		//#CM40951
		//The array of station no. of specified type
		Station[] stationArray = null;
		ASStationHandler wAstHandler = new ASStationHandler(conn);

		//#CM40952
		//Pull-down data for storage setting
		if(type.equals(STATION_STORAGE))
		{
			stationArray = wAstHandler.getStorageSagyoba(wTerminalNo);
		}
		//#CM40953
		//Pull-down data for retrieval setting 
		else if(type.equals(STATION_RETRIEVAL))
		{
			stationArray = wAstHandler.getRetrievalSagyoba(wTerminalNo);
		}
		//#CM40954
		//Pull-down data for replenishment storage
		else if(type.equals(STATION_ADD_STORAGE))
		{
			stationArray = wAstHandler.getAddStorageSagyoba(wTerminalNo);
		}
		//#CM40955
		//Pull-down data for inventory checking
		else if(type.equals(STATION_INVENTORY_CHECK))
		{
			stationArray = wAstHandler.getInventoryCheckSagyoba(wTerminalNo);
		}
		//#CM40956
		//Pull-down data for on-line indication
		else if(type.equals(SATAION_WORK_DISPLAY))
		{
			stationArray = wAstHandler.getSagyobaWorkDisplay(wTerminalNo, ON_SENDABLE);
		}
		//#CM40957
		//pulldown for work maintenance use
		else if(type.equals(STATION_WORK_MNT))
		{
			stationArray = wAstHandler.getSagyobaMntDisplay(wTerminalNo);
		}
		else
		{
			System.out.println("This pull-down type cannot be specified in this method!!");
			return null;
		}


		//#CM40958
		//Retrieve the array of station no. available for specified terminals.
		for (int lc=0; lc<stationArray.length; lc++)
		{

			//#CM40959
			// application return code
			String wRetKey = stationArray[lc].getStationNumber();
			//#CM40960
			// pulldown display code
			String wDispCode = "";
			wDispCode = stationArray[lc].getStationNumber() + ":" +
						stationArray[lc].getStationName();
			//#CM40961
			// connection key
			String wConnectKey = stationArray[lc].getWHStationNumber();

			//#CM40962
			//Data to show at initial display
			if(wRetKey.equals(selected))
			{
				pulldownData.add(wRetKey+ PDDELIM+wDispCode+ PDDELIM+ wConnectKey + PDDELIM+ FIRSTDISP_TRUE);
			}
			//#CM40963
			//All other data
			else
			{
				pulldownData.add(wRetKey+ PDDELIM+wDispCode+ PDDELIM+ wConnectKey + PDDELIM+ FIRSTDISP_FALSE);
			}
		}
		//#CM40964
		//**** Preserve the pull-down data. ****
		wPulldownDataTable.put(key, pulldownData);
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);

		return str;
	}
	
	//#CM40965
	/**
	 * Array the data, to display the pull-down of stations(workshop) available for specified terminals, 
	 * in form of <code>String</code> then return.<BR>
	 * Data required to create the pull-down should all be retreieved from STATION table.<BR>
	 * Type of warehouse (automated or floor storage) should be specified also in this method.
	 * As parameters, <code>Connection</code>, type of pull-down (only the following accepted), terminal no.
	 * and the stations for initial display should be provided.<BR>
	 * This method will be used in storage/retrieval (in/out) screen.
	 *<BR>
	 *	<B>Pull-down for storage (specify STATION_STORAGE) </B><BR>
	 *		- station type in STATION table : storage, storage/retrieval<BR>
 	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- station available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed. <BR><BR>
 	 *
	 *	<B>Pull-down for retrieval  (specify STATION_RETRIEVAL) </B><BR>
	 *		- station type in STATION table : retrieval, storage/retrieval<BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- station available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR><BR>
	 *
	 * 	<B>Pull-down for replenishing storage, inventory checking  (specify STATION_ADD_STORAGE)</B><BR>
	 *		- station type in STATION table : retrieval side of U-shaped station, storage/retrieval<BR>
	 *		- sendability : SENDABLE only <BR>
	 *		- station available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR>
	 *
	 * 	<B>Pull-down for on-line indication (specify SATAION_WORK_DISPLAY)</B><BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- station available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR>
	 * Add to ArryList the data : Value+ PDDELIM+Name+ PDDELIM+ parent VALUE + PDDELIM+ Initial display flag ("0" or "1").<BR>
	 * Data which is set with "1" will be shown at initial display of pull-down.<BR>
	 * Preserve the data to be used when this method is called for the first time in HashTable, with 
	 * pull-down type as a key. For the 2nd and further use, return the preserved data as it is.
	 * @param conn <CODE>Connection</CODE>
	 * @param type :specifies the type of pull-down.
	 * @param selected :station no. to show on initialdispaly. Set "" if there is no specification.
	 * @return :return data in form of <code>String</code> array which is required to draw the pull-down.
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 * @deprecated : Please use public String[] getStationPulldownData(Connection conn, String type, boolean autoType, 
	 * String selected).
	 */
	public String[] getSagyobaPulldownData(Connection conn, String type, String selected) throws ReadWriteException
	{
		return getSagyobaPulldownData(conn, type, true, selected);
	}


	//#CM40966
	/**
	 * Array the data, to display the pull-down of stations(workshop) available for specified terminals, 
	 * in form of <code>String</code> then return.<BR>
	 * Data required to create the pull-down should all be retreieved from STATION table.<BR>
	 * Type of warehouse (automated or floor storage) should be specified also in this method.
	 * As parameters, <code>Connection</code>, type of pull-down (only the following accepted), warehouse type
	 * and the stations for initial display should be provided.
	 *<BR>
	 *	<B>Pull-down for storage (specify STATION_STORAGE)</B><BR>
	 *		- station type in STATION table : storage, storage/retrieval<BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR><BR>
	 *	<B>Pull-down for storage(specify STATION_STORAGE_SENDABLE)</B><BR>
	 *		- station type in STATION table :storage. storage/retrieval<BR>
	 *		- sendability : SENDABLE only <BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR><BR>
	 *	<B>Pull-down for retrieval (specify STATION_RETRIEVAL)</B><BR>
	 *		- station type in STATION table : storage, storage/retrieval<BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR><BR>
	 *
	 * 	<B>Pull-down for replenishment storage, inventory checking (specify STATION_ADD_STORAGE)</B><BR>
	 *		- station type in STATION table : retrieval side of U-shaped station,storage/retrieval<BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR>
	 * 	<B>Pull-down for on-line indications (specify STAION_WORK_DISPLAY)</B><BR>
	 *		- sendability : SENDABLE only <BR>
	 *		- stations available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR>
	 * Add to ArrayList the data: Value+ PDDELIM+Name+ PDDELIM+ parent VALUE + PDDELIM+Initial display flag ("0" or "1").<BR>
	 * Data which is set with "1" will be shown at initial display of pull-down.<BR>
	 * Preserve the data to be used when this method is called for the first time in HashTable, with 
	 * pull-down type as a key. For the 2nd and further use, return the preserved data as it is.
	 * @param conn <CODE>Connection</CODE>
	 * @param type :specifies the pull-down type.
	 * @param autoType :specifies the warehouse type. (automated warehouse:true, floor storage:false)	 
	 * @param selected :station no. to show at initial dispaly. Please set "" if there is no specification.
	 * @return :return data in form of <code>String</code> array which is required to draw the pull-down.
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 */
	public String[] getStationPulldownData(Connection conn, String type, boolean autoType, String selected) throws ReadWriteException
	{
		//#CM40967
		//Set "" in case it is null.
		if(selected == null ) selected = "";
		//#CM40968
		//*** Initial processing ends here. ***

		//#CM40969
		//*** Check whether/not the pull-down data has been searched. ***
		//#CM40970
		//Create the key of HashTable.
		String key = "getStationPulldownData" + type + autoType + wTerminalNo;
		//#CM40971
		//In case the data is already searched,
		if(wPulldownDataTable.containsKey(key))
		{
			//#CM40972
			//Set the data for initial display and return.
			return changeToSelected((ArrayList)wPulldownDataTable.get(key), selected, "");
		}
		//#CM40973
		//*** Check of whehter/not pull-down data is already searched ends here. ***
		ArrayList pulldownData = new ArrayList();
		//#CM40974
		//The array of station no. of specified type
		Station[] stationArray = null;
		ASStationHandler wAstHandler = new ASStationHandler(conn);

		//#CM40975
		//Pull-down data for storage setting
		if(type.equals(STATION_STORAGE))
		{
			stationArray = wAstHandler.getStorageStation(wTerminalNo, ASStationHandler.SENDABLE_BOTH);
		}
		//#CM40976
		/*  2003/12/10  INSERT  M.INOUE START  */

		//#CM40977
		//Pull-down data for storage setting (except for workshops)
		else if(type.equals(STATION_STORAGE_SENDABLE))
		{
			stationArray = wAstHandler.getStorageStation(wTerminalNo, ON_SENDABLE);
		}
		//#CM40978
		/*  2003/12/10  INSERT  M.INOUE END  */

		//#CM40979
		//Pull-down data for retrieval setting 
		else if(type.equals(STATION_RETRIEVAL))
		{
			stationArray = wAstHandler.getRetrievalStation(wTerminalNo, ASStationHandler.SENDABLE_BOTH);
		}
		//#CM40980
		//Pull-down data for replenishment storage
		else if(type.equals(STATION_ADD_STORAGE))
		{
			stationArray = wAstHandler.getAddStorageStation(wTerminalNo, ASStationHandler.SENDABLE_BOTH);
		}
		//#CM40981
		//Pull-down data for inventory checking
		else if(type.equals(STATION_INVENTORY_CHECK))
		{
			stationArray = wAstHandler.getInventoryCheckStation(wTerminalNo, ASStationHandler.SENDABLE_BOTH);
		}
		//#CM40982
		//Pull-down data for on-line indication
		else if(type.equals(SATAION_WORK_DISPLAY))
		{
			stationArray = wAstHandler.getStationWorkDisplay(wTerminalNo, ON_SENDABLE);
		}
		//#CM40983
		//pulldown for work maintenance use
		else if(type.equals(STATION_WORK_MNT))
		{
			stationArray = wAstHandler.getStationMntDisplay(wTerminalNo, ASStationHandler.SENDABLE_BOTH);
		}
		else
		{
			System.out.println("This pull-down type cannot be specified in this method!!");
			return null;
		}

		//#CM40984
		//Retrieve the array of station no. available for specified terminals.
		String w_SvParentNumber = "";
		for (int lc=0; lc<stationArray.length; lc++)
		{

			//#CM40985
			// application return code
			String wRetKey = stationArray[lc].getStationNumber();
			//#CM40986
			// pulldown display code
			String wDispCode = "";
			wDispCode = stationArray[lc].getStationNumber() + ":" +
						stationArray[lc].getStationName();
			//#CM40987
			// connection key
			String wConnectKey = stationArray[lc].getParentStationNumber();

			if (!wConnectKey.equals(w_SvParentNumber))
			{
				//#CM40988
				//**** [add automated selection to arraylist ****
				if(autoType)
				{
					//#CM40989
					//fetch [automated selection] based on resouce
					//#CM40990
					// application return code
					String wAllRetKey = Station.AUTO_SELECT_STATIONNO;
					//#CM40991
					// pulldown display code
					String wAllDispCode = "";
					if (!type.equals(SATAION_WORK_DISPLAY))
					{
						wAllDispCode = DisplayText.getText("AUTO_STATION_SELECT");
					}
					else
					{
						wAllDispCode = Station.AUTO_SELECT_STATIONNO + ":" +
									DisplayText.getText("AUTO_STATION_SELECT");
					}
					//#CM40992
					// connection key
					w_SvParentNumber = wConnectKey;
					//#CM40993
					// In case showing all warehouse at initial display,
					if(selected.equals(ALLWAREHOUSE))
					{
						pulldownData.add(wAllRetKey+ PDDELIM+wAllDispCode+ PDDELIM+ w_SvParentNumber + PDDELIM+ FIRSTDISP_TRUE);
					}
					//#CM40994
					//All other data
					else
					{
						pulldownData.add(wAllRetKey+ PDDELIM+wAllDispCode+ PDDELIM+ w_SvParentNumber + PDDELIM+ FIRSTDISP_FALSE);
					}
				}
			}
			//#CM40995
			//Data to show at initial display
			if(wRetKey.equals(selected))
			{
				pulldownData.add(wRetKey+ PDDELIM+wDispCode+ PDDELIM+ wConnectKey + PDDELIM+ FIRSTDISP_TRUE);
			}
			//#CM40996
			//All other data
			else
			{
				pulldownData.add(wRetKey+ PDDELIM+wDispCode+ PDDELIM+ wConnectKey + PDDELIM+ FIRSTDISP_FALSE);
			}
		}
		//#CM40997
		//**** Preserve the pull-down data. ****
		wPulldownDataTable.put(key, pulldownData);
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);

		return str;
	}

	//#CM40998
	/**
	 * Array the data, to display the pull-down of stations(workshop) available for specified terminals, 
	 * in form of <code>String</code> then return.<BR>
	 * Data required to create the pull-down should all be retreieved from STATION table.<BR>
	 * Type of warehouse (automated or floor storage) should be specified also in this method.
	 * As parameters, <code>Connection</code>, type of pull-down (only the following accepted), terminal no.
	 * and the stations for initial display should be provided.<BR>
	 * This method will be used in storage/retrieval (in/out) screen.
	 *<BR>
	 *	<B>Pull-down for storage (specify STATION_STORAGE) </B><BR>
	 *		- station type in STATION table : storage, storage/retrieval<BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- station available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed. <BR><BR>
	 *
	 *	<B>Pull-down for retrieval  (specify STATION_RETRIEVAL) </B><BR>
	 *		- station type in STATION table : retrieval, storage/retrieval<BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- station available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR><BR>
	 *
	 * 	<B>Pull-down for replenishing storage, inventory checking  (specify STATION_ADD_STORAGE)</B><BR>
	 *		- station type in STATION table : retrieval side of U-shaped station, storage/retrieval<BR>
	 *		- sendability : SENDABLE only <BR>
	 *		- station available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR>
	 *
	 * 	<B>Pull-down for on-line indication (specify SATAION_WORK_DISPLAY)</B><BR>
	 *		- sendability : both SENDABLE and NOT SENDABLE<BR>
	 *		- station available for specified terminals<BR>
	 *		Stations that meet all these conditions will be displayed.<BR>
	 * Add to ArryList the data : Value+ PDDELIM+Name+ PDDELIM+ parent VALUE + PDDELIM+ Initial display flag ("0" or "1").<BR>
	 * Data which is set with "1" will be shown at initial display of pull-down.<BR>
	 * Preserve the data to be used when this method is called for the first time in HashTable, with 
	 * pull-down type as a key. For the 2nd and further use, return the preserved data as it is.
	 * @param conn <CODE>Connection</CODE>
	 * @param type :specifies the type of pull-down.
	 * @param selected :station no. to show on initialdispaly. Set "" if there is no specification.
	 * @return :return data in form of <code>String</code> array which is required to draw the pull-down.
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 * @deprecated : Please use public String[] getStationPulldownData(Connection conn, String type, boolean autoType, 
	 * String selected).
	 */
	public String[] getStationPulldownData(Connection conn, String type, String selected) throws ReadWriteException
	{
		return getStationPulldownData(conn, type, true, selected);
	}

	//#CM40999
	/**
	 * Array the data, to display the pull-down of aisles with no specifiacation of terminal no., 
	 * in form of <code>String</code> then return.<BR>
	 * As parameters, <code>Connection</code>, aisle to show at initial display, warehouse station no. and 
	 * the flag which indicates whether/not to show 'all RM'.<BR>
	 * Data required to create the pull-down should all be retreieved from AISLE table.<BR>
	 * For ArrayList to be output, <BR>
	 * add data: Value+PDDELIM+Name+PDDELIM+ parent VALUE +PDDELIM+Initial display flag ("0" or "1").<BR>
	 * If there are 2 or more warehouses, data to be output will be as follows.<BR>
	 *		"9999","all RM","9000","0"<BR>
	 *		"9001","RM1" ,"9000","0"<BR>
	 *		"9002","RM2" ,"9000","0"<BR>
	 *		"9999","all RM","9100","0"<BR>
	 *		"9101","RM1" ,"9100","0"<BR>
	 * 		"9102","RM2" ,"9100","0"<BR>
	 * In this case, parent(warehouse station no.) will be preserved as a parameter in order to determine
	 * all RM of which warehouse should be shown at initial display.<BR>
	 * If there are 2 or more warehouses, they will be used as interlocked pull-downs. Therefire, the value
	 * must be selected from warehouse pull-down then input for parent.<BR>
	 * Or if there is just one warehouse, please set "".<BR>
	 * Data which is set with "1" will be shown at initial display of pull-down.
	 * @param conn <CODE>Connection</CODE>
	 * @param selected :specifies aisle station no. to show at initial display.
	 * @param parent :specifies the warehouse station no. which has been selected due to 2 or more warehouses.
	 * Or set "" if there is just one warehouse.
	 * @return :return data in form of <code>String</code> array which is required to draw the pull-down.
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 */
	public String[] getAislePulldownData(Connection conn, String selected, String parent, boolean isAllItem) throws ReadWriteException
	{
		return getAislePulldownData(conn, selected, parent, isAllItem, false);
	}

	//#CM41000
	/**
	 * Array the data, to display the pull-down of aisles with no specifiacation of terminal no., 
	 * in form of <code>String</code> then return.<BR>
	 * This will be used when 'all warehouse' is included in warehouse pull-down list and if it is desired to
	 * display the corresponding all RM.<BR>
	 * As parameter, <code>Connection</code>, aisles in initial dispaly, warehouse station no. and the flag which 
	 * indicates whether/not to show 'all RM'.<BR>
	 * Data required to create the pull-down should all be retreieved from AISLE table.<BR>
	 * For ArrayList to be output,<BR>
	 * add data: Value+PDDELIM+Name+PDDELIM+ parent VALUE +PDDELIM+Initial display flag ("0" or "1").<BR>
	 * If there are 2 or more warehouses, data to be output will be as follows.<BR>
	 *		"9999","all RM","9000","0"<BR>
	 *		"9001","RM1" ,"9000","0"<BR>
	 *		"9002","RM2" ,"9000","0"<BR>
	 *		"9999","all RM","9100","0"<BR>
	 *		"9101","RM1" ,"9100","0"<BR>
	 * 		"9102","RM2" ,"9100","0"<BR>
	 * In this case, parent(warehouse station no.) will be preserved as a parameter in order to determine
	 * all RM of which warehouse should be shown at initial display.<BR>
	 * If there are 2 or more warehouses, they will be used as interlocked pull-downs. Therefire, the value
	 * must be selected from warehouse pull-down then input for parent.<BR>
	 * Or if there is just one warehouse, please set "".<BR>
	 * Data which is set with "1" will be shown at initial display of pull-down.
	 * @param conn <CODE>Connection</CODE>
	 * @param selected :specifies aisle station no. to show at initial display.
	 * @param parent :specifies the warehouse station no. which has been selected due to 2 or more warehouses.
	 * Or set "" if there is just one warehouse.
	 * @return :return data in form of <code>String</code> array which is required to draw the pull-down.
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 */
	public String[] getAislePulldownData(Connection conn, String selected, String parent, boolean isAllItem, boolean isAllWareHouse) throws ReadWriteException
	{
		//#CM41001
		//*** Initial processing ***
		//#CM41002
		//Set "" in case it is null.
		if(selected == null ) selected = "";
		if(parent == null ) parent = "";
		
		//#CM41003
		//*** Initial processing ends here. ***
		
		//#CM41004
		//*** Check whether/not the pull-down data has been searched. ***
		String allItem = ALLITEM_FALSE;
		String key = "";
		if(isAllItem)
		{
			allItem = ALLITEM_TRUE;
		}
		
		
		//#CM41005
		//Create the key of HashTable.
		key = "getAislePulldownData" + allItem + new Boolean(isAllWareHouse).toString();
		//#CM41006
		//In case the data is already searched,
		if(wPulldownDataTable.containsKey(key))
		{
			//#CM41007
			//Set the data for initial display and return.
			return changeToSelected((ArrayList)wPulldownDataTable.get(key),selected, parent);
		}
		//#CM41008
		//*** Check of whehter/not pull-down data is already searched ends here. ***

		ArrayList pulldownData = new ArrayList();

		//#CM41009
		//Retrieve the array of the aisles.
		Aisle[] aisleArray = getAisleArray(conn);
		
		//#CM41010
		//For the search of index to insert all RM
		String wareHouseStationNo_temp = "";
		String allAisleName = DisplayText.getText("ALLAISLENO_TEXT");
		
		//#CM41011
		//**** Add aisle to ArrayList. ****
		for(int i = 0; i < aisleArray.length; i++)
		{
			//#CM41012
			//Aisle station no.
			String aisleSTNo = aisleArray[i].getStationNumber();
			//#CM41013
			//Name to display in pull-down list. (represent as "RM"+"aisleNo")
			
			//#CM41014
			// Name of "RM" to display
			String aisleName = DisplayText.getText("LBL-A0039") + Integer.toString(aisleArray[i].getAisleNumber());
			//#CM41015
			// Warehouse station no.
			String wareHouseStationNo = aisleArray[i].getWHStationNumber();
			//#CM41016
			//Store the index in case the warehouse station no. differs from the previous one.
			if(!wareHouseStationNo.equals(wareHouseStationNo_temp))
			{
				//#CM41017
				//**** Add "all RM" to all the warehouse
				//#CM41018
				//		"9999","all RM","9999","0"
				if (isAllWareHouse)
				{
					if(selected.equals(ALLAISLE) && parent.equals(ALLWAREHOUSE))
					{
						pulldownData.add(0, ALLAISLE + PDDELIM+allAisleName + PDDELIM + wareHouseStationNo + PDDELIM + "1");
					}
					else
					{
						pulldownData.add(0, ALLAISLE + PDDELIM+allAisleName + PDDELIM + wareHouseStationNo + PDDELIM + "0");
					}
				}
				wareHouseStationNo_temp = wareHouseStationNo;
			}
			
			//#CM41019
			//Data to show at initial display
			if(aisleSTNo.equals(selected))
			{
				pulldownData.add(aisleSTNo + PDDELIM + aisleName + PDDELIM + wareHouseStationNo + PDDELIM + FIRSTDISP_TRUE);
			}
			//#CM41020
			//All other data
			else
			{
				pulldownData.add(aisleSTNo + PDDELIM + aisleName + PDDELIM + wareHouseStationNo + PDDELIM + FIRSTDISP_FALSE);
			}
		}

		//#CM41021
		//**** Preserve the pull-down data. ****
		wPulldownDataTable.put(key, pulldownData);
		
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}

	//#CM41022
	/**
	 * job type list edit process
	 * @param carrykind conveyance type
	 * @return    job type list
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public String[] getWorkType(int carrykind) throws ReadWriteException
	{
		String key = "";
		//#CM41023
		//Create the key of HashTable.
		key = "getWorkType";

		//#CM41024
		// storage use : storage + unplanned storage
		String[] storagekey = {"02", "21"};
		//#CM41025
		// picking use : order picking + item picking + unplanned picking
		String[] retrievalkey = {"030", "031", "22"};

		ArrayList pulldownData = new ArrayList();

		if (carrykind == CarryInformation.CARRYKIND_STORAGE)
		{
			for (int plc=0; plc<storagekey.length; plc++)
			{
				pulldownData.add(storagekey[plc] + PDDELIM + 
					DisplayText.getText("WORKING","JOBTYPE", storagekey[plc]) + 
					PDDELIM + "" + PDDELIM + FIRSTDISP_FALSE);
			}
		}
		else if (carrykind == CarryInformation.CARRYKIND_RETRIEVAL)
		{
			for (int plc=0; plc<retrievalkey.length; plc++)
			{
				pulldownData.add(retrievalkey[plc] + PDDELIM + 
					DisplayText.getText("WORKING","JOBTYPE", retrievalkey[plc]) + 
					PDDELIM + "" + PDDELIM + FIRSTDISP_FALSE);
			}
		}
		//#CM41026
		//**** Preserve the pull-down data. ****
		wPulldownDataTable.put(key, pulldownData);
		
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}

	//#CM41027
	/**
	 * Search in AISLE table and return the array of AISLE.
	 * @param conn <CODE>Connection</CODE>
	 * @return    :arrya of the aisle
	 * @throws ReadWriteException :Notifies if error ocurred in connection with database.
	 */
	private Aisle[] getAisleArray(Connection conn) throws ReadWriteException
	{
		AisleSearchKey aisleKey  = new AisleSearchKey();
		AisleHandler aisleHandle = new AisleHandler(conn);
		//#CM41028
		//Sort in ascending order of warehouse station no.
		aisleKey.setWHStationNumberOrder(1, true);
		//#CM41029
		//Sort in ascending order of aisle no.
		aisleKey.setAisleNumberOrder(2, true);
		
		return (Aisle[])aisleHandle.find(aisleKey);
	}

	//#CM41030
	// Package methods -----------------------------------------------

	//#CM41031
	// Protected methods ---------------------------------------------

	//#CM41032
	// Private methods -----------------------------------------------

	//#CM41033
	/**
	 * Modify the initial display flag of the item, which the parameter specified, to "1".<BR>
	 * In case the other items in ArrayList are already "1", they should be modified to "0".<BR>
	 * If there are no value which has been specified by selected in ArrayList, all the initial display
	 * flags should be "0".<BR>
	 * For interlocked pull-down lists, it is impossible to identify items to display onluy by the VALUE
	 * of child pul-down. In such case, set the value of parent item to parent.<BR>
	 * Or set "" in case the pull-downs are not interlocked.<BR>
	 * If applying this method to the child (station pull-down) when pull-downs are interlocked between
	 * warehouse and station, 
	 * in order to specify station 1301 of the warehouse 9000 to appear at initial display, please 
	 * specify as 'selected="1301", parent="9000"'.
	 * @param array :ArrayList to be modified
	 * @param selected :item to show at initial display (specify Value)
	 * @param parent   :value of parent pull-down for the item to show in initial display
	 * @return :the array of <code>String</code>
	 */
	private String[] changeToSelected(ArrayList array, String selected, String parent)
	{
		for(int i = 0; i < array.size(); i++)
		{
			StringTokenizer stk = new StringTokenizer((String)array.get(i), ",", false);
			String value = stk.nextToken();
			String name = stk.nextToken();
			String parentValue = stk.nextToken();
			if(value.equals(selected))
			{
				//#CM41034
				//In case the parent item is invalid,
				if(parent.equals(""))
				{
					array.set(i, value + PDDELIM + name + PDDELIM+ parentValue +PDDELIM + FIRSTDISP_TRUE);
				}
				//#CM41035
				//In case the parent item is valid,
				else
				{
					if(parentValue.equals(parent))
					{
						array.set(i, value + PDDELIM + name + PDDELIM+ parentValue +PDDELIM + FIRSTDISP_TRUE);
					}
					else
					{
						array.set(i, value + PDDELIM + name + PDDELIM+ parentValue +PDDELIM + FIRSTDISP_FALSE);
					}
				}
			}
			else
			{
				array.set(i, value + PDDELIM + name + PDDELIM+ parentValue +PDDELIM +  FIRSTDISP_FALSE);
			}
		}
		
		String[] str = new String[array.size()];
		array.toArray(str);
		return str;
	}
		
}

