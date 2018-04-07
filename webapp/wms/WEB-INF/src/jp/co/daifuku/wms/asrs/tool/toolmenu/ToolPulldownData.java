// $Id: ToolPulldownData.java,v 1.2 2006/10/30 04:04:21 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.toolmenu;

//#CM54451
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
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolBankHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolBankSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Bank;
import jp.co.daifuku.wms.asrs.tool.location.HardZone;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;

//#CM54452
/**<en>
 * This class defines methods used in order to retrieve data reqrueid when displaying 
 * the pull-down for TOOL.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/01/18</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:04:21 $
 * @author  $Author: suresh $
 </en>*/
public class ToolPulldownData extends Object
{
	//#CM54453
	// Class fields --------------------------------------------------

	//#CM54454
	/**<en>
	 * Displays the pull-down which indicates the floor staorage warehouses. (warehouse type: floor storage)
	 * Specified by the parameter of <code>getWareHousePulldownData</code> method.
	 </en>*/
	public static final String WAREHOUSE_FLOOR = "1";
	//#CM54455
	/**<en>
	 * Displays the pull-down which indicates the automated warehouses. (warehouse type: autoamted warehosue)
	 * Specified by the parameter of <code>getWareHousePulldownData</code> method.
	 </en>*/
	public static final String WAREHOUSE_AUTO = "2";
	//#CM54456
	/**<en>
	 * Displays the pull-down which indicates both floor storage warehouses and automated warehouses.
	 * (warehouse type: floor storage, automated )
	 * Specified by the parameter of <code>getWareHousePulldownData</code> method.
	 </en>*/
	public static final String WAREHOUSE_ALL = "3";
	
	//#CM54457
	/**<en>
	 * Displays the pull-down which indicates hard zone. (type: hard zone)
	 * Specified by the parameter of <code>getZonePulldownData</code> method.
	 </en>*/
	public static final String ZONE_HARD = "30";

	//#CM54458
	/**<en>
	 * Displays the pull-down which indicates soft zone. (type: soft zone)
	 * Specified by the parameter of <code>getZonePulldownData</code> method.
	 </en>*/
	public static final String ZONE_SOFT = "31";
	
	//#CM54459
	/**<en>
	 * Displays the pull-down which indicates hard zone and soft zone. (type: hard zone, soft zone)
	 * Specified by the parameter of <code>getZonePulldownData</code> method.
	 </en>*/
	public static final String ZONE_ALL = "32";
	
	//#CM54460
	/**<en>
	 * Station no. which indicates 'unspecified'.
	 * <Specified by the parameter of code>getLinkedNoDummyStationPulldownData</code> method.
	 </en>*/
	public static final String NOSTATION = "888";


	//#CM54461
	// Class variables -----------------------------------------------

	//#CM54462
	/**<en>
	 * <CODE>Locale</CODE> object
	 </en>*/
	protected Locale wLocale = null ;

	//#CM54463
	/**<en>
	 * <CODE>Connection</CODE> object
	 </en>*/
	protected Connection wConn = null;
	
	//#CM54464
	/**<en>
	 * Just in case the different table names are used in each projection,
	 * set the eAWC table name as <CODE>Hashtable</CODE> key and set the table
	 * name for corresponding projection as a value.
	 </en>*/
	protected Hashtable wTableNames = null;

	//#CM54465
	/**<en>
	 * Delimtier
	 </en>*/
	protected String wDelim = MessageResource.DELIM ;

	//#CM54466
	/**<en>
	 * Initial indication flag (indicates items for initial display)
	 </en>*/
	protected final String FIRSTDISP_TRUE = "1";

	//#CM54467
	/**<en>
	 * Initial indication flag (indicate items no to shown in initial display)
	 </en>*/
	protected final String FIRSTDISP_FALSE = "0";


	//#CM54468
	// Class method --------------------------------------------------
	//#CM54469
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 04:04:21 $") ;
	}

	//#CM54470
	// Constructors --------------------------------------------------
	//#CM54471
	/**<en>
	 * When this constructor is called, preserve the locale and terminal no. as 
	 * instance variable and initialize hte HashTable for preservation of pull-down data.
	 * @param conn <CODE>Connection</CODE>
	 * @param locale <CODE>Locale</CODE>
	 * @param tablenames :<code>Hashtable</code> which preserve the table name per product no.
	 </en>*/
	public ToolPulldownData(Connection conn, Locale locale, Hashtable tablenames)
	{
		wLocale = locale;
		wConn = conn;
		wTableNames = tablenames;
	}

	//#CM54472
	// Public methods ------------------------------------------------
	//#CM54473
	/**<en>
	 * Return data, for the display of storage pull-down, in the array of <code>String</code>.
	 * The storage type in initial display will be given for parameter.
	 * <BR>
	 *  -only display the floor storage warehouse. (specify WAREHOUSE_FLOOR.)<BR>
	 *  -only display the automated warehouse. (specifiy WAREHOUSE_AUTO.)<BR>
	 *  -only display both floor storage warehouse and automated warehouse. (specify WAREHOUSE_ALL).<BR>
	 * <BR>
	 * Add the data :"Value+","+Name+","+ parent VALUE +","+ initial display flag (0 or 1) to the ArrayList.
	 * Those data set '1' at initial display will be initially indicated when displaying pull-down.
	 * 
	 * @param tablename :<CODE>Hashtable</CODE> object which preserves the list of table name
	 * @param type      :specifies the pull-down type.
	 * @param selected  :warehouse station no. which carries out the initial display. Please enter ""
	 * if specifing no data. 
	 * @return :return in the array of <code>String</code> which is needed to show pull-down image.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if error is found in definition of information.
	 </en>*/
	public String[] getWarehousePulldownData(String type, String selected) throws ReadWriteException,InvalidDefineException
	{
		return getWarehousePulldownData(type, selected, ZONE_ALL);
	}

	//#CM54474
	/**<en>
	 * Return the data in <code>String</code> array to show the storage type for pull-down.
	 * The storage type in initial display will be given for parameter.
	 * <BR>
	 *  -only display the floor storage warehouse. (specify WAREHOUSE_FLOOR.)<BR>
	 *  -only display the automated warehouse. (specifiy WAREHOUSE_AUTO.)<BR>
	 *  -only display both floor storage warehouse and automated warehouse. (specify WAREHOUSE_ALL).
	 * <BR>
	 * Add the data :"Value+","+Name+","+ parent VALUE +","+ initial display flag (0 or 1) to the ArrayList.
	 * Those data set '1' at initial display will be initially indicated when displaying pull-down
	 * 
	 * @param tablename :<CODE>Hashtable</CODE> object which owns the table lists.
	 * @param type  :]specify the pull-down typr:
	 * @param selected :warehoudse station no. to initially indicates. Please enter "" if there is no specification.
	 * @param zonetype set up the pull-down type.
	 * @return :return the data requried for pull-down images i nofrm of <code>String</code> array.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if error is found in definition of information.
	 </en>*/
	public String[] getWarehousePulldownData(String type, String selected, String zonetype) throws ReadWriteException,InvalidDefineException
	{

		ArrayList pulldownData = new ArrayList();
		//#CM54475
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";
		//#CM54476
		//<en>The array of the Station no. of specified type.</en>
		Warehouse[] warehouseArray = null;

		//#CM54477
		//<en>Pull down data for floor storage.</en>
		if(type.equals(WAREHOUSE_FLOOR))
		{
			warehouseArray = getWarehouseArray(wConn, WAREHOUSE_FLOOR, zonetype);
		}
		//#CM54478
		//<en>Pull down data for automated warehouse</en>
		else if(type.equals(WAREHOUSE_AUTO))
		{
			warehouseArray = getWarehouseArray(wConn, WAREHOUSE_AUTO, zonetype);
		}
		//#CM54479
		//<en>Pull down data for autoamted warehouse and floor storage warehouse</en>
		else if(type.equals(WAREHOUSE_ALL))
		{
			warehouseArray = getWarehouseArray(wConn, WAREHOUSE_ALL, zonetype);
		}
		else
		{
			throw new InvalidDefineException("Argument (Type) is wrong. ");
		}

		//#CM54480
		//<en>**** Add wareahouses to the ArrayList. ****</en>
		for(int i = 0; i < warehouseArray.length; i++)
		{
			String warehouseNo =Integer.toString(warehouseArray[i].getWarehouseNumber());
			//#CM54481
			//<en>Names to show in pull-down  warehouse no.:warehouse name</en>
			String warehouseName = warehouseNo
								+ ":" + warehouseArray[i].getName();
			//#CM54482
			//<en>Data to show at initial display</en>
			if(warehouseNo.equals(selected))
			{
				pulldownData.add(warehouseNo+","+warehouseName+","+ warehouseNo +","+FIRSTDISP_TRUE);
			}
			//#CM54483
			//<en>other data</en>
			else
			{
				pulldownData.add(warehouseNo+","+warehouseName+","+ warehouseNo +","+FIRSTDISP_FALSE);
			}
		}

		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}

	//#CM54484
	/**<en>
	 * Return the data to show the pull-down of warehouse stations in the array of <code>String</code>.
	 * Give warehouse station for initial display for parameter.
	 * Retrieve all data recuired to create pull-down from the WAREHOUSE table.
	 * For the ArrayList which is outputting, <BR>
	 * the data: Value+","+Name+","+ parent VALUE +","+ initial display flag ("0" or "1") should be added to 
	 * the ArrayList.<BR>
	 * Outputting data will be as follows.<BR>
	 *		"1","1","","1"<BR>
	 *		"2","2","","0"<BR>
	 * The data that "1" was set will be initially displayed at the pull-down indication.
	 * @param selected :Set the warehouse station wanted to for display, or set "" if no data is specified.
	 * @return :return in the array of <code>String</code> the required data for pull-down image.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getWarehouseStaionPulldownData(String selected) throws ReadWriteException
	{
		
		ArrayList pulldownData = new ArrayList(5);
		//#CM54485
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";

		//#CM54486
		//<en>Retrieve the array of warehouse station.</en>
		Warehouse[] warehouseArray = getWarehouseArray(wConn);
		
		
		//#CM54487
		//<en>**** Add the warehouse station to the ArrayList. ****</en>
		for(int i = 0; i < warehouseArray.length; i++)
		{
			//#CM54488
			//<en>warehouse station no.</en>
			String warehousestationNo = warehouseArray[i].getNumber();
			//#CM54489
			//<en>name to show in pull-down menu</en>
			String warehouseName = warehousestationNo
								+ ":" + warehouseArray[i].getName();

			//#CM54490
			//<en>data to show as initial display</en>
			if(warehousestationNo.equals(selected))
			{
				pulldownData.add(warehousestationNo + ","+ warehouseName +","+  warehousestationNo + ","+ FIRSTDISP_TRUE);
			}
			//#CM54491
			//<en>other data</en>
			else
			{
				pulldownData.add(warehousestationNo + ","+ warehouseName +","+ warehousestationNo + ","+ FIRSTDISP_FALSE);
			}
		}
		
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}


	//#CM54492
	/**<en>
	 * Return data, for displayinh pull dowm of stations, in form of <code>String</code> array.
	 * @param worktype 0:excluding workshop, 2: excluding workshop and main station
	 * @param selected :station no. for initial display. Or set "" if nothing is specified.
	 * @return :return the required data to create image of pull-down, in form of <code>String</code> array.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getStationPulldownData(int worktype, String selected) throws ReadWriteException
	{
		//#CM54493
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";

		ArrayList pulldownData = new ArrayList();

		//#CM54494
		//<en>Station arrya of specified sendability division.</en>
		Station[] stationArray = getStationArray(wConn, worktype);

		for(int i = 0; i < stationArray.length; i++)
		{
			String stationNo = stationArray[i].getNumber();
			//#CM54495
			//<en>Name to inidicate in pull-down (display names defined in STATION table.)</en>
			//#CM54496
			//<en> "station no. : name"</en>
			String stationName = stationNo + ":" + stationArray[i].getName();

			//#CM54497
			//<en>data for initial display</en>
			if(stationNo.equals(selected))
			{
				pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_TRUE);
			}
			//#CM54498
			//<en>other data</en>
			else
			{
				pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_FALSE);
			}
		}
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);

		return str;
	}

	//#CM54499
	/**<en>
	 * Return the <code>String</code> array of data for displaying stations in pull-down.
	 * All data to create pull-down will be retrieved from STATION table.
	 * @param worktype :whether/not to include workshops - true: included, false: not included
	 * @param selected :station no. for initial display; or set "" if no specification.
	 * @return :return data required to create the pull-down image in <code>String</code> array.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getNoDummyStationPulldownData(boolean worktype, String selected) throws ReadWriteException
	{
		//#CM54500
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";

		ArrayList pulldownData = new ArrayList();

		//#CM54501
		//<en>Station arrya of specified sendability division.</en>
		Station[] stationArray = getNoDummyStationArray(wConn, worktype);

		for(int i = 0; i < stationArray.length; i++)
		{
			String stationNo = stationArray[i].getNumber();
			//#CM54502
			//<en>Name to inidicate in pull-down (display names defined in STATION table.)</en>
			//#CM54503
			//<en> "station no. : name"</en>
			String stationName = stationNo + ":" + stationArray[i].getName();

			//#CM54504
			//<en>data for initial display</en>
			if(stationNo.equals(selected))
			{
				pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_TRUE);
			}
			//#CM54505
			//<en>other data</en>
			else
			{
				pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_FALSE);
			}
		}
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);

		return str;
	}

	//#CM54506
	/**<en>
	 * This is the method for linked pull-down menues. (for area setting screen) 
	 * Return data to show in station pull-downs in form of <code>String</code> array.
	 * All data to create pull-down will be retrieved from the STATION table.
	 * @param worktype :whether/not to include workshops - true:woekshop included, false: not included
	 * @param selected :station no. to show in initial display. Or set "" if no specification.
	 * @return : data required for pull-down image, in form of <code>String</code> array.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getLinkedNoDummyStationPulldownData(boolean worktype, String selected) throws ReadWriteException
	{
		//#CM54507
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";

		ArrayList pulldownData = new ArrayList();

		//#CM54508
		//<en>Station array of specified sendability</en>
		Station[] stationArray = getNoAllWPDummyStationArray(wConn, worktype);
		
		//#CM54509
		//<en>Store index to insert 'no specification'.</en>
		Vector allItemIndexVec = new Vector();
		//#CM54510
		//<en>Store the warehouse station no. when 'no specification' is inserted.</en>
		Vector wareHouseSTNoVec = new Vector();
		//#CM54511
		//<en>For the index search for insertion of 'no specification'</en>
		String wareHouseStationNo_temp = "";

				
		for(int i = 0; i < stationArray.length; i++)
		{
			String stationNo = stationArray[i].getNumber();
			//#CM54512
			//<en>Name to inidicate in pull-down (display names defined in STATION table.)</en>
			//#CM54513
			//<en> "station no. : name"</en>
			String stationName = stationNo + ":" + stationArray[i].getName();
			//#CM54514
			//<en> Warehouse station no.</en>
			String warehousestationNo = stationArray[i].getWarehouseStationNumber();
			
			//#CM54515
			//<en>In case the warehouse station no. differs from previous station no, save the index.</en>
			if(!warehousestationNo.equals(wareHouseStationNo_temp))
			{
				allItemIndexVec.add(new Integer(i));
				wareHouseSTNoVec.add((String)warehousestationNo);
				wareHouseStationNo_temp = warehousestationNo;
			}
			
			//#CM54516
			//<en>data for initial display</en>
			if(stationNo.equals(selected))
			{
				pulldownData.add(stationNo + "," + stationName + "," + warehousestationNo + "," + FIRSTDISP_TRUE);
			}
			//#CM54517
			//<en>other data</en>
			else
			{
				pulldownData.add(stationNo + "," + stationName + "," + warehousestationNo + "," + FIRSTDISP_FALSE);
			}
		}
		
		//#CM54518
		//<en>	**** aAdd 'no specification' to the ArrayList.  ****</en>
		for(int i = 0; i < allItemIndexVec.size(); i++)
		{
			//#CM54519
			//<en>Value of the parent (warehouse station no.)</en>
			String wareHouseStationNumber = (String)wareHouseSTNoVec.get(i);
			//#CM54520
			//<en>Position to insert 'no specification'</en>
			int index = ((Integer)allItemIndexVec.get(i)).intValue() + i;
				
			if(selected.equals(NOSTATION))
			{
				pulldownData.add(index, NOSTATION+","+ "no specification" +"," + wareHouseStationNumber + "," + FIRSTDISP_TRUE);
			}
			else
			{
				pulldownData.add(index,NOSTATION+","+ "no specification" +"," + wareHouseStationNumber + "," + FIRSTDISP_FALSE);
			}
		}

		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);

		return str;
	}


	//#CM54521
	/**<en>
	 * Arrange the data, which is to display in pull-down menu for stations, in the array of 
	 * <code>String</code>, then return.
	 * Return stations other than dummy stations.
	 * The data to create pull-down lists will be retrieved from STATION table.
	 * @param selected :station no. to show in initial display. Set "" if there is no specification.
	 * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getNoDummyStationPulldownData(String selected) throws ReadWriteException
	{
		return getNoDummyStationPulldownData(true, selected);
	}

	//#CM54522
	/**<en>
	 * Arrange the data, which is to display in pull-down menu for aisles, in the array of 
	 * <code>String</code>, then return.
	 * All the data neede to create pull-down lists will be retrieved from AISLE table.
	 * @param selected :aisle no. to show in initial display. Set "" if there is no specification.
	 * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getAislePulldownData(String selected) throws ReadWriteException
	{
		//#CM54523
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";

		ArrayList pulldownData = new ArrayList();

		//#CM54524
		//<en>Station array of specified sendability status</en>
		Station[] aisleArray = getAisleArray(wConn);

		for(int i = 0; i < aisleArray.length; i++)
		{
			String stationNo = aisleArray[i].getNumber();
			String stationName = stationNo + ":" + "RM";

			//#CM54525
			//<en>data for initial display</en>
			if(stationNo.equals(selected))
			{
				pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_TRUE);
			}
			//#CM54526
			//<en>other data</en>
			else
			{
				pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_FALSE);
			}
		}
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);

		return str;
	}

	//#CM54527
	/**<en>
	 * Arrange the data, which is to display in pull-down menu for station + aisles, in the array of 
	 * <code>String</code>, then return.
	 * The data to create pull-down lists will be retrieved from STATION table and AISLE table.
	 * @param selected :station no. to show in initial display. Set "" if there is no specification.
	 * @param type - 0:all stations, 1: workshop excluded, 2: workshop and main station excluded
	 * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getAllStationPulldownData(String selected, int type) throws ReadWriteException
	{
		
		String stations[] = getStationPulldownData(type, selected);
		String aisles[] = getAislePulldownData(selected);

		String[] allstarray = new String[stations.length + aisles.length];
		System.arraycopy(stations, 0, allstarray, 0, stations.length);
		System.arraycopy(aisles, 0, allstarray, stations.length, aisles.length);
		
		return allstarray;
	}

	//#CM54528
	/**<en>
	 * Arrange the data, which is to display in pull-down menu for group controller, in the array of 
	 * <code>String</code>, then return.
	 * Provide the parameter with the group controller for initial display.
	 * All the data neede to create pull-down lists will be retrieved from GROUPCONTROLLER table.
	 * To the ArrayList which will be output, add data created as follows.<BR>
	 * Value+","+Name+","+ parent VALUE +","+initial display flag ("0" or "1")<BR>
	 * Data to output will be as follows:<BR>
	 *		"1","1","","1"<BR>
	 *		"2","2","","0"<BR>
	 * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
	 * @param selected :specifies the ControllerNumber of the group controller to show in initial display.
	 * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getGroupControllerPulldownData(String selected) throws ReadWriteException
	{
		
		ArrayList pulldownData = new ArrayList(5);
		//#CM54529
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";

		//#CM54530
		//<en>Retrieve the array of the group controller.</en>
		GroupController[] gcArray = getGroupControllerArray(wConn);
		
		
		//#CM54531
		//<en>**** Add the group controller to the ArrayList. ****</en>
		for(int i = 0; i < gcArray.length; i++)
		{
			//#CM54532
			//Controller No.
			String controllerNo = Integer.toString(gcArray[i].getNumber());
			//#CM54533
			//<en>Names to show in pull-down list.</en>
			String gcName = controllerNo;

			//#CM54534
			//<en>Data to show in initial display</en>
			if(controllerNo.equals(selected))
			{
				pulldownData.add(controllerNo + "," + gcName + "," + " " + "," + FIRSTDISP_TRUE);
			}
			//#CM54535
			//<en>other data</en>
			else
			{
				pulldownData.add(controllerNo + "," + gcName + "," + " " + "," + FIRSTDISP_FALSE);
			}
		}
		
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}

	//#CM54536
	/**<en>
	 * Arrange the data, which is to display in pull-down menu for machine code, in the array of 
	 * <code>String</code>, then return.
	 * All data used to create the pull-down lists are defined fixed.
	 * To the ArrayList which will be output, add data created as follows.<BR>
	 * Value+","+Name+","+ parent VALUE +","+ initial display flag("0" or "1")<BR>
	 * Data to output will be as follows:<BR>
	 *		"1","1","","1"<BR>
	 *		"2","2","","0"<BR>
	 * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
	 * @param selected :specifies the model code to show in initial display.
	 * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getMachineTypePulldownData(String selected) throws ReadWriteException
	{
		
		ArrayList pulldownData = new ArrayList(5);
		//#CM54537
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";

		ToolFindUtil futil = new ToolFindUtil(wConn);

		String type[] = new String[6] ;
		
		type[0] = "11";
		type[1] = "15";
		type[2] = "21";
		type[3] = "31";
		type[4] = "54";
		type[5] = "55";

		for (int i = 0; i < type.length; i++)
		{
			//#CM54538
			//<en>Data to show in initial display</en>
			if(type[i].equals(selected))
			{
				pulldownData.add(type[i] + ","+ futil.getMachineTypeName(Integer.parseInt(type[i])) +","+ " " + ","+ FIRSTDISP_TRUE);
			}
			else
			{
				pulldownData.add(type[i] + ","+ futil.getMachineTypeName(Integer.parseInt(type[i])) +","+ " " + ","+ FIRSTDISP_FALSE);
			}
		}
		
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}


	//#CM54539
	/**<en>
	 * Arrange the data, which is to display in pull-down menu for zone name with no specific terminal no.,
	 * in the array of <code>String</code>, then return.
	 * Provide the parameter with <code>Connection</code>, pull-down type (only those shown below) and zone ID
	 * for initial display.
	 * In this method, it displays zone of specified type accrding to the HARDZONE definition with no regard of warehouse.
	 * Pull-down of Zone requires DB search every time. It does not preserve the pull-down data in HashTable.
	 * Data will be created as follows and added to ArrayLsit.
	 * Value+","+Name+","+ parent VALUE +","+initial display flag ("0" or "1")
	 * Set the ZONEID as it is for parent VALUE.
	 * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
	 * @param selected :zone ID for initial display. Please set "" if there is no specification.
	 * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getHardZonePulldownData(String selected) throws ReadWriteException
	{
		//#CM54540
		//<en>*** Initial processing***</en>
		//#CM54541
		//<en>Retrieve the array of the warheouse.</en>
//#CM54542
//				Warehouse[] wWarehouseArray = getWareHouseArray(wConn, WAREHOUSE_ALL, ZONE_ALL);

		//#CM54543
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";

		ArrayList pulldownData = new ArrayList();
		//#CM54544
		//<en>array of station no. of specified type</en>
		HardZone[] zoneArray = null;
	
		zoneArray = getHardZoneArray(wConn);
	
		//#CM54545
		//<en>**** Add the station to the ArrayList. ****</en>
		for(int i = 0; i < zoneArray.length; i++)
		{
			int zoneID = zoneArray[i].getHardZoneID();
			//#CM54546
			//<en>Names to show in pull-down list - hard zone ID: hard name</en>
			String zoneName = zoneID
								+ ":" + zoneArray[i].getName();

			//#CM54547
			//<en>data for initial display</en>
			if(Integer.toString(zoneID).equals(selected))
			{
				pulldownData.add(zoneID+","+zoneName+","+ zoneID +","+ FIRSTDISP_TRUE);
			}
			//#CM54548
			//<en>other data</en>
			else
			{
				pulldownData.add(zoneID+","+zoneName+","+ zoneID +","+ FIRSTDISP_FALSE);
			}
		}
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
	}


	//#CM54549
	/**<en>
	 * Arrange the data, which is to display in pull-down menu for bank with no specific terminal no.,
	 * in the array of <code>String</code>, then return.
	 * <font color=#FF0000> This method does not provide multiple warehouse support.</font>
	 * Provide the parameter with <code>Connection</code>, bank and display flag (whether/not to show
	 * all banks) for initial display.
	 * All data will be retrieved from ABNKSELECT table, which is needed to create the pull-down lists.
	 * Data: Value+","+Name+","+ parent VALUE +","+initial display flag("0" or "1") will be added to the
	 * ArrayList.<BR>
	 * If multiple warehouses are handled, data being output will be as below.<BR>
	 *		"9999","all BANK","9000","0"<BR>
	 *		"1"   ,"BANK1" ,"9000","0"<BR>
	 *		"2"   ,"BANK2" ,"9000","0"<BR>
	 *		"9999","all BANK","9100","0"<BR>
	 *		"1"   ,"BANK1" ,"9100","0"<BR>
	 * 		"2"   ,"BANK2" ,"9100","0"<BR>
	 * In this case the parent (warehouse station no.) will be preserved as parameter upon which to determine
	 * which warehouse to show in initial display. 
	 * If handling multiple warehouses, the pull-down menu should be linked and used; therefore the value selected
	 * from warehosues in pull-down list should be entered for the parent.
	 * Or if handling only one warheouse, please set "".
	 * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
	 * @param selected :bank no. for initial display. Please set "" if there is no specification.
	 * @param parent :specifies selected warehouse station no. whne handling multiple warehouses. Or set"" if 
	 * only one warehouse is handled.
	 * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getBankPulldownData(String selected, String parent) throws ReadWriteException, InvalidDefineException
	{
		//#CM54550
		//<en>*** Initial processing***</en>
		//#CM54551
		//<en>Retrieve the array of the warehouse.</en>
		Warehouse[] wWarehouseArray = getWarehouseArray(wConn, WAREHOUSE_ALL, ZONE_ALL);

		//#CM54552
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";
		if(parent == null ) parent = "";
		//#CM54553
		//<en>*** Initial processing ends here. ***</en>
		
		//#CM54554
		//<en>*** Checking (whether/not the pull down data has been searched) ends here  ***</en>
		
		ArrayList pulldownData = new ArrayList();
		//#CM54555
		//<en>Retrieve the array of the bank.</en>
		Bank[] bankArray = getBankArray(wConn);
		//#CM54556
		//<en> Store the index to insert 'all BANK'.</en>
		Vector allItemIndexVec = new Vector();
		//#CM54557
		//<en>Store the warehouse station no. when 'all BANK' is inserted.</en>
		Vector wareHouseSTNoVec = new Vector();
		//#CM54558
		//<en>To be used when searching the index for 'all BANK' insertion</en>
		String wareHouseStationNo_temp = "";

		//#CM54559
		//<en>**** Add the bank to ArrayList. ****</en>
		for(int i = 0; i < bankArray.length; i++)
		{
			//#CM54560
			//<en>Bank no.</en>
			String bankNo = Integer.toString(bankArray[i].getBank());

			//#CM54561
			//<en>Names to show in pull-down list (display as "BANK"+"bankNo")</en>
			String bankName = "BANK" + bankNo;
			//#CM54562
			//<en>warehouse station no.</en>
			String wareHouseStationNo = bankArray[i].getWareHouseStationNumber();
			//#CM54563
			//<en>In case the warehouse station no. differs from the previous no., store the index.</en>
			if(!wareHouseStationNo.equals(wareHouseStationNo_temp))
			{
				allItemIndexVec.add(new Integer(i));
				wareHouseSTNoVec.add((String)wareHouseStationNo);
				wareHouseStationNo_temp = wareHouseStationNo;
			}
			
			//#CM54564
			//<en>data for initial display</en>
			if(bankNo.equals(selected))
			{
				pulldownData.add(bankNo+","+bankName +","+ wareHouseStationNo +","+ FIRSTDISP_TRUE);
			}
			//#CM54565
			//<en>other data</en>
			else
			{
				pulldownData.add(bankNo+","+bankName +","+ wareHouseStationNo +","+ FIRSTDISP_FALSE);
			}
		}
		//#CM54566
		//<en>**** Preserve the pull-down data. ****</en>
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		return str;
		
	}

	//#CM54567
	/**<en>
	 * Arrange the data, which is to display in pull-down menu for bank with no specific terminal no.,
	 * in the array of <code>String</code>, then return.
	 * Provide the parameter with <code>Connection</code>, bank and display flag (whether/not to show
	 * all banks) for initial display.
	 * All data will be retrieved from ABNKSELECT table, which is needed to create the pull-down lists.
	 * Data: Value+","+Name+","+ parent VALUE +","+initial display flag("0" or "1") will be added to the
	 * ArrayList.<BR>
	 * If multiple warehouses are handled, data being output will be as below.<BR>
	 * 		"bank","name",	"storage type",	"0"
	 *		"1"   	,"BANK1" ,	"1",			"0"<BR>
	 *		"2"   	,"BANK2" ,	"2",			"0"<BR>
	 * In this case the parent (warehouse station no.) will be preserved as parameter upon which to determine
	 * which warehouse to show in initial display. 
	 * If handling multiple warehouses, the pull-down menu should be linked and used; therefore the value selected
	 * from warehosues in pull-down list should be entered for the parent.
	 * Or if handling only one warheouse, please set "".
	 * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
	 * @param selected :bank no. for initial display. Please set "" if there is no specification.
	 * @param whnumber :specifies selected storage type if handling multiple warehouses. "" will be set if only
	 * one warehouse is handled.
	 * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public String[] getwhBankPulldownData(String selected, int whnumber) throws ReadWriteException, InvalidDefineException
	{
		//#CM54568
		//<en>*** Initial processing***</en>
		//#CM54569
		//<en>Set "" in case of null.</en>
		if(selected == null ) selected = "";
		//#CM54570
		//<en>*** Initial processing ends here. ***</en>
		
		//#CM54571
		//<en>*** Checking (whether/not the pull down data has been searched) ends here  ***</en>
		
		ArrayList pulldownData = new ArrayList();

		//#CM54572
		//<en> Retrieve the array of all banks.</en>
		Bank[] bankArray = getBankArray(wConn);
		//#CM54573
		//<en> Store the index to insert "BANKK".</en>
		Vector allItemIndexVec = new Vector();
		//#CM54574
		//<en> Store the storage type when "BANK" is inserted.</en>
		Vector wareHouseSTNoVec = new Vector();
		//#CM54575
		//<en> To be used when searching the index of "BANK" insertion</en>
		String warehouseNo_temp = "";

		//#CM54576
		//<en>**** Add banks to the ArrayList. ****</en>
		for(int i = 0; i < bankArray.length; i++)
		{
			//#CM54577
			//<en>Bank no.</en>
			String bankNo = Integer.toString(bankArray[i].getBank());

			//#CM54578
			//<en>Names to show in pull-down list (Display as "BANK"+"bankNo")</en>
			String bankName = "BANK" + bankNo;
			//#CM54579
			//<en>Retrieve the storage type from the warehouse station no. (retrieved from BANKSELECT)</en>
			String warehouseNo = null ;
			String whStationNo = bankArray[i].getWareHouseStationNumber();
			ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(wConn) ;
			ToolWarehouseSearchKey warehousekey  = new ToolWarehouseSearchKey() ;
			warehousekey.setWarehouseStationNumber(whStationNo) ;
			Warehouse[] warehouseArray = (Warehouse[])warehousehandle.find(warehousekey) ;

			if (warehouseArray.length <= 0)
			{
				continue;
			}

			warehouseNo = "" + warehouseArray[0].getWarehouseNumber() ;
			//#CM54580
			//<en>If the storage type differs from the previous data, store the index.</en>
			if(!warehouseNo.equals(warehouseNo_temp))
			{
				allItemIndexVec.add(new Integer(i));
				wareHouseSTNoVec.add((String)warehouseNo);
				warehouseNo_temp = warehouseNo;
			}
			
			//#CM54581
			//<en>data for initial display</en>
			if(bankNo.equals(selected))
			{
				pulldownData.add(bankNo+","+bankName +","+ warehouseNo +","+ FIRSTDISP_TRUE);
			}
			//#CM54582
			//<en>other data</en>
			else
			{
				pulldownData.add(bankNo+","+bankName +","+ warehouseNo +","+ FIRSTDISP_FALSE);
			}
		}
		
		//#CM54583
		//<en>**** Preserve the pull-down data. ****</en>
		String[] str = new String[pulldownData.size()];
		pulldownData.toArray(str);
		
		return str;
	}


	//#CM54584
	// Package methods -----------------------------------------------

	//#CM54585
	// Protected methods ---------------------------------------------


	//#CM54586
	// Private methods -----------------------------------------------

	//#CM54587
	/**<en>
	 * Alter the initial display flag of the item, specified through parameter, to "1".
	 * In case the other item in ArrayList is already "1", alter this item to "0".
	 * In case there is no value, sprcified by 'selected' in the ArrayList, all initial display flags 
	 * to be "0".
	 * In case of linked pull-down, items to display cannot be identified only by a value given by sub pull-down list.
	 * Therefore, set the VALUE of parent item for 'parent'. Or set "" if not handling the linked pull-downs.
	 * If pull-downs for warehouse and stations are linked and in case this method is applied to sub pull-down 
	 * (station pull down lists), 
	 * ex. if one wants to initially display the warehouse 9000 and station 1301, specify as 
	 * selected="1301", parent="9000".
	 * @param array :ArrayList being modified
	 * @param selected :items to show in initial display (specify Value)
	 * @param parent :VALUE of parent pull-down of the items to show on initial display
	 * @return :the array of <code>String</code>
	 </en>*/
	private String[] changeToSelected(ArrayList array, String selected, String parent)
	{
		for(int i = 0; i < array.size(); i++)
		{
			StringTokenizer stk = new StringTokenizer((String)array.get(i), ",", false);
			String value = stk.nextToken();
			String name = stk.nextToken();
			String parentValue = stk.nextToken();
			String first_disp = stk.nextToken();
			if(value.equals(selected))
			{
				//#CM54588
				//<en>In case the parent item is invalid,</en>
				if(parent.equals(""))
				{
					array.set(i, value + "," + name + ","+ parentValue +"," + FIRSTDISP_TRUE);
				}
				//#CM54589
				//<en>In case the parent item is valid,</en>
				else
				{
					if(parentValue.equals(parent))
					{
						array.set(i, value + "," + name + ","+ parentValue +"," + FIRSTDISP_TRUE);
					}
					else
					{
						array.set(i, value + "," + name + ","+ parentValue +"," + FIRSTDISP_FALSE);
					}
				}
			}
			else
			{
				array.set(i, value + "," + name + ","+ parentValue +"," +  FIRSTDISP_FALSE);
			}
		}
		
		String[] str = new String[array.size()];
		array.toArray(str);
		
		return str;
	}

	//#CM54590
	/**<en>
	 * Search warehouses, then reurn the array of warehouse of the types that parameter specified.
	 * @param conn <CODE>Connection</CODE>
	 * @param waraehouse type (WAREHOUSE_FLOOR:floot storage warehouse, WAREHOUSE_AUTO:automated warehouse, 
	 * WAREHOUSE_ALL: both floor storage warehouse and automated warehouse)
	 * @param :zone type (ZONE_HARD:hard zone, ZONE_SOFT: soft zone, ZONE_ALL:both hard and soft)
	 * @return    :the array of warehouse 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if error is found in definition of information.
	 </en>*/
	private Warehouse[] getWarehouseArray(Connection conn, String type, String zonetype) throws ReadWriteException, InvalidDefineException
	{
		ToolWarehouseSearchKey warehouseKey  = new ToolWarehouseSearchKey();
		ToolWarehouseHandler   warehouseHandle  = new ToolWarehouseHandler(conn);
		//#CM54591
		//<en>Sort in asceding order of warehouse station no.</en>
		warehouseKey.setWarehouseStationNumberOrder(2,true);
		warehouseKey.setWarehouseNumberOrder(1,true);
		//#CM54592
		//<en>Pull down data for floor storage.</en>
		if(type.equals(WAREHOUSE_FLOOR))
		{
			warehouseKey.setWarehouseType(Warehouse.CONVENTIONAL_WAREHOUSE);
		}
		//#CM54593
		//<en>Pull down data for automated warehouse</en>
		else if(type.equals(WAREHOUSE_AUTO))
		{
			warehouseKey.setWarehouseType(Warehouse.AUTOMATID_WAREHOUSE);
		}
		//#CM54594
		//<en>Pull down data for autoamted warehouse and floor storage warehouse</en>
		else if(type.equals(WAREHOUSE_ALL))
		{
			//#CM54595
			//<en>No setting here for SearchKey.</en>
		}
		else
		{
			throw new InvalidDefineException("Argument (Type) is wrong. ");
		}
		//#CM54596
		//<en>Pull-down data of hard zone</en>
		if(zonetype.equals(ZONE_HARD))
		{
			int[] znid = {Warehouse.HARD, Warehouse.HARD_ITEM };
			warehouseKey.setZoneType(znid);
		}
		//#CM54597
		//<en>Pull-down data of soft zone</en>
		else if(zonetype.equals(ZONE_SOFT))
		{
			warehouseKey.setZoneType(Warehouse.SOFT);
		}
		//#CM54598
		//<en>Pull-down data of hard zone and soft zone</en>
		else if(zonetype.equals(ZONE_ALL))
		{
			//#CM54599
			//<en>No setting here for SearchKey.</en>
		}
		else
		{
			throw new InvalidDefineException("Argument (ZoneType) is wrong. ");
		}
		
		return (Warehouse[])warehouseHandle.find(warehouseKey);
	}

	//#CM54600
	/**<en>
	 * Retrieve the station array in all warehouses.
	 * Retrieve the station array under condition whether/not to include workshops.
	 * @param conn <CODE>Connection</CODE>
	 * @param worktype -0: all stations, 1: workshop excluded, 2: workshos and main station excluded
	 * @return :the station array that meets the paramter specified condition
	 </en>*/
	private Station[] getStationArray(Connection conn, int worktype) throws ReadWriteException
	{
		ToolStationHandler stationHandle = new ToolStationHandler(conn);
		ToolStationSearchKey stationKey = new ToolStationSearchKey();
		switch (worktype)
		{
			case 0:
				break;
			case 1:
				
				int types[] = {Station.NOT_WORKPLACE, Station.MAIN_STATIONS};
				stationKey.setWorkPlaceType(types);
				break;
				
			case 2:
				int type = Station.NOT_WORKPLACE;
				stationKey.setWorkPlaceType(type);
				break;
				
			default:
		}
		
		//#CM54601
		//<en>Sort in ascending order of station no.</en>
		stationKey.setNumberOrder(1,true);
		Station[] allStationArray = (Station[])stationHandle.find(stationKey);

		return allStationArray;	
	}

	//#CM54602
	/**<en>
	 * Retrieve the station array in all warehouses. Except for dummy stations.
	 * Retrieve the station array under condition whether/not to include workshops.
	 * @param conn <CODE>Connection</CODE>
	 * @param bool :workshop  true: workshop included, false: workshop excluded
	 * @return :the station array that meets the paramter specified condition
	 </en>*/
	private Station[] getNoDummyStationArray(Connection conn, boolean worktype) throws ReadWriteException
	{
		ToolStationHandler stationHandle = new ToolStationHandler(conn);
		ToolStationSearchKey stationKey = new ToolStationSearchKey();
		if (!worktype)
		{
			int types[] = {Station.NOT_WORKPLACE, Station.MAIN_STATIONS};
			stationKey.setWorkPlaceType(types);
		}
		//#CM54603
		//<en>Display all stations but dummy stations in pull-down list. (dummy station has 0 for StationType.)</en>
		int[] stationtype = new int[6];
		stationtype[0] = Station.STATION_TYPE_IN;
		stationtype[1] = Station.STATION_TYPE_OUT;
		stationtype[2] = Station.STATION_TYPE_INOUT;
		stationtype[3] = Station.STATION_TYPE_INOUT_IN;
		stationtype[4] = Station.STATION_TYPE_INOUT_OUT;
		stationtype[5] = Station.STATION_TYPE_INOUT_IN_OUT;
		stationKey.setStationType(stationtype);
		
		//#CM54604
		//<en>SSort in ascending order of station no.</en>
		stationKey.setNumberOrder(1,true);
		Station[] allStationArray = (Station[])stationHandle.find(stationKey);
		
		return allStationArray;	
	}

	//#CM54605
	/**<en>
	 * Retrieve the station array in all warehouses. Except for dummy stations and general workshop..
	 * etrieve the station array under condition whether/not to include workshops.
	 * @param conn <CODE>Connection</CODE>
	 * @param bool :workshop  true: workshop included, false: workshop excluded
	 * @return  :the station array that meets the paramter specified condition
	 </en>*/
	private Station[] getNoAllWPDummyStationArray(Connection conn, boolean worktype) throws ReadWriteException
	{
		ToolStationHandler stationHandle = new ToolStationHandler(conn);
		ToolStationSearchKey stationKey = new ToolStationSearchKey();
		if (!worktype)
		{
			int types[] = {Station.NOT_WORKPLACE, Station.MAIN_STATIONS};
			stationKey.setWorkPlaceType(types);
		}
		
		//#CM54606
		//<en>Display all stations but dummy stations in pull-down list. (dummy station has 0 for StationType.)</en>
		int[] stationtype = new int[6];
		stationtype[0] = Station.STATION_TYPE_IN;
		stationtype[1] = Station.STATION_TYPE_OUT;
		stationtype[2] = Station.STATION_TYPE_INOUT;
		stationtype[3] = Station.STATION_TYPE_INOUT_IN;
		stationtype[4] = Station.STATION_TYPE_INOUT_OUT;
		stationtype[5] = Station.STATION_TYPE_INOUT_IN_OUT;
		stationKey.setStationType(stationtype);			

		//#CM54607
		//<en>Display all stations but general workshop in pull-down list. (general workshop has 5 for WorkPlaceType.)</en>
		int[] workplacetype = new int[5];
		workplacetype[0] = Station.NOT_WORKPLACE;
		workplacetype[1] = Station.STAND_ALONE_STATIONS;
		workplacetype[2] = Station.AISLE_CONMECT_STATIONS;
		workplacetype[3] = Station.MAIN_STATIONS;
		workplacetype[4] = Station.WPTYPE_FLOOR;
		stationKey.setWorkPlaceType(workplacetype);
		
		//#CM54608
		//<en>Sort in ascending order of station no.</en>
		stationKey.setWareHouseStationNumberOrder(1,true);
		stationKey.setNumberOrder(2,true);
		Station[] allStationArray = (Station[])stationHandle.find(stationKey);
		return allStationArray;	
	}

	//#CM54609
	/**<en>
	 * Search the WAREHOUSE table and return the array of warehouse station.
	 * @param conn <CODE>Connection</CODE>
	 * @return    :warehouse station array
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private Warehouse[] getWarehouseArray(Connection conn) throws ReadWriteException
	{
		ToolWarehouseSearchKey warehouseKey  = new ToolWarehouseSearchKey();
		ToolWarehouseHandler   warehouseHandle  = new ToolWarehouseHandler(conn);
		warehouseKey.setWarehouseNumberOrder(1, true);
		
		return (Warehouse[])warehouseHandle.find(warehouseKey);
	}

	//#CM54610
	/**<en>
	 * Retrieve the array of aisles in all warehouses.
	 * @param conn <CODE>Connection</CODE>
	 * @return :the aisle array that meets the paramter specified condition
	 </en>*/
	private Station[] getAisleArray(Connection conn) throws ReadWriteException
	{
		ToolAisleHandler aisleHandle = new ToolAisleHandler(conn);
		ToolAisleSearchKey aisleKey = new ToolAisleSearchKey();

		//#CM54611
		//<en>/Sort in ascending order of station no.</en>
		aisleKey.setNumberOrder(1,true);
		Station[] aisleArray = (Station[])aisleHandle.find(aisleKey);

		return aisleArray;	
	}

	//#CM54612
	/**<en>
	 * Search the GROUPCONTROLLER table and return the array of group controller.
	 * @param conn <CODE>Connection</CODE>
	 * @return    :the array of group controller
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private GroupController[] getGroupControllerArray(Connection conn) throws ReadWriteException
	{
		return GroupController.getInstances(conn);
	}

	//#CM54613
	/**<en>
	 * Search the BANKSELECT table and return the array of BANK of the type specified by parameter.
	 * @param conn <CODE>Connection</CODE>
	 * @return    :the bank array
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private Bank[] getBankArray(Connection conn) throws ReadWriteException
	{
		ToolBankSearchKey bankKey  = new ToolBankSearchKey();
		ToolBankHandler bankHandle = new ToolBankHandler(conn);
		//#CM54614
		//<en>Sort in ascending order of warehouse station no.</en>
		bankKey.setWareHouseStationNumberOrder(1, true);
		
		//#CM54615
		//<en>Sort in ascending order of bank no.</en>
		bankKey.setBankOrder(2, true);
		
		return (Bank[])bankHandle.find(bankKey);
	}

	//#CM54616
	/**<en>
	 * Search the HARDZONE table and return the array of HARDZONE of the type specified by parameter.
	 * @param conn <CODE>Connection</CODE>
	 * @return   :the HardZone array
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private HardZone[] getHardZoneArray(Connection conn) throws ReadWriteException
	{
		ToolHardZoneSearchKey zoneKey  = new ToolHardZoneSearchKey();
		ToolHardZoneHandler   zoneHandle  = new ToolHardZoneHandler(conn);
		//#CM54617
		//<en>Sort in ascending order of zone ID.</en>
		zoneKey.setHardZoneIDOrder(1, true);
		
		return (HardZone[])zoneHandle.find(zoneKey);
	}
}

