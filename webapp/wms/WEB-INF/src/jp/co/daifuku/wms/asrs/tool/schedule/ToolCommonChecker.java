// $Id: ToolCommonChecker.java,v 1.2 2006/10/30 02:52:00 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM51693
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.wms.asrs.tool.common.ToolMenuText;
import jp.co.daifuku.wms.asrs.tool.common.ToolMenuTextHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.IniFileOperation;


//#CM51694
/**<en>
 * This class implements the common checks in maintenance processing.
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/16</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>okamura</TD><TD>change setMessage of isExistMenuId method</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:00 $
 * @author  $Author: suresh $
 </en>*/
public class ToolCommonChecker extends Object
{
	//#CM51695
	// Class fields --------------------------------------------------
	//#CM51696
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM51697
	// Class variables -----------------------------------------------

	//#CM51698
	/**<en>
	 * Store the detail message for the problems that occurred during the check process.
	 </en>*/
	protected String wMessage = "";
	
	//#CM51699
	/**<en>
	 * Connection with database.
	 </en>*/
	protected Connection wConn ;
	
	
	private ToolGroupControllerHandler wGCHandler = null;
	private ToolWarehouseHandler wWarehouseHandler = null;
	private ToolAisleHandler wAisleHandler = null;
	private ToolStationHandler wStationHandler = null;
	private ToolShelfHandler wShelfHandler = null;
	private ToolTerminalAreaHandler wTerminalAreaHandler = null;
	
	//#CM51700
	// Class method --------------------------------------------------
	//#CM51701
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:00 $") ;
	}

	//#CM51702
	// Constructors --------------------------------------------------
	public ToolCommonChecker(Connection conn)
	{
		wConn = conn;
		wGCHandler = new ToolGroupControllerHandler(conn);
		wWarehouseHandler = new ToolWarehouseHandler(conn);
		wAisleHandler = new ToolAisleHandler(conn);
		wStationHandler = new ToolStationHandler(conn);
		wShelfHandler = new ToolShelfHandler(conn);
	}

	//#CM51703
	// Public methods ------------------------------------------------
	//#CM51704
	/**<en>
	 * Return the message that check preserves.
	 * This will be used to retrieve the detail in case the checked contents was unacceptable.
	 * @return  :contents of the message
	 </en>*/
	public String getMessage()
	{
		return wMessage;
	}


	//#CM51705
	/**<en>
	 * Check whether/not the station no., specified through parameter, exist in route text.
	 * @return :return true if it exists.
	 </en>*/
	public boolean isExistStationNo_RouteText(String filepath, String stationNo)
	{
		String defaultRouteText = ToolParam.getParam("DEFAULT_ROUTETEXT_PATH");
		File routepath = new File(defaultRouteText);

		IniFileOperation ifo = null;
		try
		{
			ifo = new IniFileOperation( filepath + "/" + routepath.getName(), RouteCreater.wSeparate );
		}
		catch(ReadWriteException e)
		{
			return false;
		}
		String[] fromStations = ifo.getKeys();
		String[] toStations = ifo.getValues();

		for(int i=0;i<fromStations.length;i++)
		{
			if( fromStations[i].length() == stationNo.length() 
			 && fromStations[i].indexOf(stationNo) != -1 )
			{
				return true;
			}
		}
		
		for(int i=0;i<toStations.length;i++)
		{
			if( toStations[i].length() == stationNo.length() 
			 && toStations[i].indexOf(stationNo) != -1)
			{
				return true;
			}
		}
		return false;
	}

	//#CM51706
	/**<en>
	 * Check whether/not the group controller no. specified through parameter exist in
	 * the group controller table.
	 * @return :return true if it exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistControllerNo(int arg) throws ScheduleException
	{
		try
		{
			ToolGroupControllerSearchKey key = new ToolGroupControllerSearchKey();
			key.setControllerNumber(arg);
			if(getGCHandler().count(key) == 0)
			{
				//#CM51707
				//<en>6123121=The CONTROLLERNO={0} does not exist in GROUPCONTROLLER table.</en>
				setMessage("6123121" + wDelim + Integer.toString(arg));
				
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}
	
	//#CM51708
	/**<en>
	 * Check whether/not the warehouse station no., specified through parameter, exist
	 * in WAREHOUSE table.
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistWarehouseStationNo(String arg) throws ScheduleException
	{
		try
		{
			ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
			key.setWarehouseStationNumber(arg);
			if(getWarehouseHandler().count(key) == 0)
			{
				//#CM51709
				//<en>6123138	There is no STATIONNUMBER={0} in WAREHOUSE table.</en>
				setMessage("6123138" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM51710
	/**
	 * Warehouse Station No specified by the argument is [1 : Warehouse Type], confirm whether automatic operation Warehouse 
	 * and to exist in the WAREHOUSE table.
	 * @return Return true when existing. 
	 * @throws ScheduleException 
	 */
	public boolean isExistAutoWarehouseStationNo(String arg) throws ScheduleException
	{
		try
		{
			ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
			key.setWarehouseStationNumber(arg);
			key.setWarehouseType(Warehouse.AUTOMATID_WAREHOUSE);
			if(getWarehouseHandler().count(key) == 0)
			{
				//#CM51711
				// 6123284	Automatic operation Warehouse StationNUMBER={0} does not exist in the WAREHOUSE table. 
				setMessage("6123284" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM51712
	/**
	 * Warehouse Station No specified by the argument is [2 : Warehouse Type], confirm whether flat putting Warehouse
	 * and to exist in the WAREHOUSE table. 
	 * @return Return true when existing. 
	 * @throws ScheduleException 
	 */
	public boolean isExistFloorWarehouseStationNo(String arg) throws ScheduleException
	{
		try
		{
			ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
			key.setWarehouseStationNumber(arg);
			key.setWarehouseType(Warehouse.CONVENTIONAL_WAREHOUSE);
			if(getWarehouseHandler().count(key) == 0)
			{
				//#CM51713
				//6123285	Flat putting Warehouse StationNUMBER={0} does not exist in the WAREHOUSE table. 
				setMessage("6123285" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM51714
	/**<en>
	 * Check whether/not the aisle station no. specified through parameter exist in AISLE table.
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistAisleStationNo(String arg) throws ScheduleException
	{
		try
		{
			ToolAisleSearchKey key = new ToolAisleSearchKey();
			key.setNumber(arg);
			if(getAisleHandler().count(key) == 0)
			{
				//#CM51715
				//<en>6123128	There is no STATIONNUMBER={0} in AISLE table.</en>
				setMessage("6123128" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		
		return true;
	}

	//#CM51716
	/**<en>
	 * Check whether/not the station no, specified through parameter exist in STATIONTYPE table.
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistStationType(String arg) throws ScheduleException
	{
		try
		{
			if(!getStationHandler().isStationType(arg))
			{
				//#CM51717
				//<en>6123134 = There is no STATIONNUMBER={0} in STATIONTYPE table.</en>
				setMessage("6123134" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		
		return true;
	}

	//#CM51718
	/**<en>
	 * Check whether/not the station no. specified through parameter exist in STATION tabl
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistStationNo(String arg) throws ScheduleException
	{
		try
		{
			ToolStationSearchKey key = new ToolStationSearchKey();
			key.setNumber(arg);
			if(getStationHandler().count(key) == 0)
			{
				//#CM51719
				//<en>6123148 = There is no STATIONNUMBER={0} in STATION table.</en>
				setMessage("6123148" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM51720
	/**
	 * Confirm whether Station No specified by the argument exists in the STATION table. 
	 * As for WorkshopType, the data of unspecification and representative Station becomes an object. 
	 * @return Return true when existing. 
	 * @throws ScheduleException 
	 */
	public boolean isExistRoutStationNo(String arg) throws ScheduleException
	{
		try
		{
			int[] workptype = {
								Station.NOT_WORKPLACE,
								Station.MAIN_STATIONS
								};
			ToolStationSearchKey key = new ToolStationSearchKey();
			key.setNumber(arg);
			key.setWorkPlaceType(workptype);			
			if(getStationHandler().count(key) == 0)
			{
				//#CM51721
				//<en>6123148 = There is no STATIONNUMBER={0} in STATION table.</en>
				setMessage("6123148" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}
	
	//#CM51722
	/**
	 * Confirm whether Station No specified by the argument exists in the STATION table. 
	 * As for WorkshopType, unspecified data becomes an object. 
	 * @return Return true when existing. 
	 * @throws ScheduleException 
	 */
	public boolean isExistMachineStationNo(String arg) throws ScheduleException
	{
		try
		{
			ToolStationSearchKey key = new ToolStationSearchKey();
			key.setNumber(arg);
			key.setWorkPlaceType(Station.NOT_WORKPLACE);			
			if(getStationHandler().count(key) == 0)
			{
				//#CM51723
				//<en>6123148 = There is no STATIONNUMBER={0} in STATION table.</en>
				setMessage("6123148" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM51724
	/**
	 * Confirm whether Station No specified by the argument exists in the STATION table. 
	 * StationType becomes one of the [ 1:Storage, 2:Picking, 3:Storage or Picking ] objects.
	 * @return Return true when existing. 
	 * @throws ScheduleException 
	 */
	public boolean isExistTerminalAreaStationNo(String arg) throws ScheduleException
	{
		try
		{
			int[] stationtype = {
								Station.STATION_TYPE_IN,
								Station.STATION_TYPE_OUT,
								Station.STATION_TYPE_INOUT
								};
			ToolStationSearchKey key = new ToolStationSearchKey();
			key.setNumber(arg);
			key.setStationType(stationtype);			
			if(getStationHandler().count(key) == 0)
			{
				//#CM51725
				//<en>6123148 = There is no STATIONNUMBER={0} in STATION table.</en>
				setMessage("6123148" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM51726
	/**<en>
	 * Check whether/not the station no. specified through parameter exist in TERMINALAREA table.
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistTAStationNo(String arg) throws ScheduleException
	{
		try
		{
			ToolTerminalAreaSearchKey key = new ToolTerminalAreaSearchKey();
			key.setStationNumber(arg);
			if(getTerminalAreaHandler().count(key) == 0)
			{
				//#CM51727
				//<en>6123232 = There is no STATIONNUMBER={0} in TERMINALAREA table.</en>
				setMessage("6123232" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM51728
	/**<en>
	 * Check whether/not the station no. specified through parameter exist either in STATION table
	 * or in AISLE table.
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistAllStationNo(String arg) throws ScheduleException
	{
		if (!isExistStationNo(arg))
		{
			if (!isExistAisleStationNo(arg))
			{
				//#CM51729
				//<en>6123147 = There is no STATIONNUMBER={0} either in STATION table or in AISLE table.</en>
				setMessage("6123147" + wDelim + arg);
				return false;
			}
		}

		return true;
	}

	//#CM51730
	/**
	 * Confirm whether Station No specified by the argument exist in the STATION table or the AISLE table. 
	 * For transportation Routing. 
	 * @return Return true when existing. 
	 * @throws ScheduleException 
	 */
	public boolean isExistAllRoutStationNo(String arg) throws ScheduleException
	{
		if (!isExistRoutStationNo(arg))
		{
			if (!isExistAisleStationNo(arg))
			{
				//#CM51731
				//<en>6123147 = There is no STATIONNUMBER={0} either in STATION table or in AISLE table.</en>
				setMessage("6123147" + wDelim + arg);
				return false;
			}
		}

		return true;
	}

	//#CM51732
	/**
	 * Confirm whether Station No specified by the argument exist in the STATION table or the AISLE table. 
	 * For equipment information setting. 
	 * @return Return true when existing. 
	 * @throws ScheduleException 
	 */
	public boolean isExistAllMachiniStationNo(String arg) throws ScheduleException
	{
		if (!isExistMachineStationNo(arg))
		{
			if (!isExistAisleStationNo(arg))
			{
				//#CM51733
				//<en>6123147 = There is no STATIONNUMBER={0} either in STATION table or in AISLE table.</en>
				setMessage("6123147" + wDelim + arg);
				return false;
			}
		}

		return true;
	}

	//#CM51734
	/**<en>
	 * Check whether/not the terminal no. specified through parameter exist in TERMINALAREA table.
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistTATerminalNumber(String arg) throws ScheduleException
	{
		try
		{
			ToolTerminalAreaSearchKey key = new ToolTerminalAreaSearchKey();
			key.setTerminalNumber(arg);
			if(getTerminalAreaHandler().count(key) == 0)
			{
				//#CM51735
				//<en>6123230=There is no TERMINALNUMBER={0} in TERMINALAREA table.</en>
				setMessage("6123230" + wDelim + arg);
				
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM51736
	/**<en>
	 * Check whether/not the area ID specified through parameter exist in TERMINALAREA table.
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistAreaId(int arg) throws ScheduleException
	{
		try
		{
			ToolTerminalAreaSearchKey key = new ToolTerminalAreaSearchKey();
			key.setAreaId(arg);
			if(getTerminalAreaHandler().count(key) == 0)
			{
				//#CM51737
				//<en>6123231=There is no AREAID={0} in TERMINALAREA table.</en>
				setMessage("6123231" + wDelim + arg);
				
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}
	
	//#CM51738
	/**<en>
	 * Check whether/not the menu ID specified through parameter exist in AWCMENU table.
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistMenuId(String arg, String filename, Locale locale) throws ScheduleException
	{
		try
		{
			String filepath = filename + "/MenuText_" + locale;
			ToolMenuTextHandler menutextHandle  = new ToolMenuTextHandler(filepath);
			String[] category = menutextHandle.getCategorys();
			boolean flag = false;
			for(int i=0; i<category.length; i++)
			{
				String[] key = menutextHandle.getKeys(category[i]);
				for(int j=0; j<key.length; j++)
				{
					
					ToolMenuText ToolMenuText = menutextHandle.findMenuText(category[i], key[j]);
					String menuid = ToolMenuText.getKey();
					if(!menuid.substring(4,5).equals("0"))
					{
						if(arg.equals(menuid))
						{
							flag = true;
						}
					}
				}
			}
			if(flag = false)
			{
				//#CM51739
				//<en>6123160=The menu ID {0} is not in awcmenu table exists in Menu_Text.</en>
				setMessage("6123160" + wDelim + arg);
				return false;
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		
		return true;
	}

	//#CM51740
	/**<en>
	 * Check whether/not the zone ID specified through parameter exist in SHELF table.
	 * @param arg  :zone ID
	 * @param type :soft zone ID/hard zone ID
	 * @return :return true if data exists.
	 * @throws ScheduleException 
	 </en>*/
	public boolean isExistShelf(int arg, int type) throws ScheduleException
	{
		try
		{
			ToolShelfSearchKey key = new ToolShelfSearchKey();
			if( type == Shelf.HARD )
			{
				key.setHardZone(arg);
			}
			else if( type == Shelf.SOFT )
			{
				key.setSoftZone(arg);
			}

			if(getShelfHandler().count(key) == 0)
			{
				if(type == Shelf.HARD)
				{
					//#CM51741
					//<en>6123243	There is no HARDZONEID={0} in SHELF table.</en>
					setMessage("6123243" + wDelim + arg);
					return false;
				}
				else if( type == Shelf.SOFT )
				{
					//#CM51742
					//<en>There is no SOFTZONEID={0} in SHELF table.</en>
					setMessage("6123268" + wDelim + arg);
					return false ;
				}
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}	

	//#CM51743
	/**<en>
	 * Check the AGCNo.  In this method, a check will be done for below;
	 *  -the value should be 1 or greater.<br>
	 * @param stArg  :specifies the bank to check.
	 * @return       :returns the check result. Return true if all are correct.
	 </en>*/
	public boolean checkAgcNo( int agc )
	{
		if(agc < 1)
		{
			//#CM51744
			//<en>6123214	Please enter 1 or greater value for AGCNo.</en>
			setMessage("6123214");
			return false;
		}
		return true;
	}
	
	//#CM51745
	/**<en>
	 * Check the aisle no. In this method, a check will be done for below;
	 *  -the value should be 1 or greater.<br>
	 * @param stArg  :specifies the bank to check.
	 * @return       :returns the check result. Return true if all are correct.
	 </en>*/
	public boolean checkAisleNo( int aisle )
	{
		if(aisle < 1)
		{
			//#CM51746
			//<en>6123120	Please enter 1 or greater valud for the aisle no.</en>
			setMessage("6123120");
			return false;
		}
		return true;
	}
	
	//#CM51747
	/**<en>
	 * Check the max. mix-load quantity. In this method, a check will be done for below;
	 *  -the value should be 1 or greater.<br>
	 * @param stArg  :specify the max. mix-load quantity to be checked.
	 * @return    :return hte checked results. It return true if all are correct.
	 </en>*/
	public boolean checkMaxMixedQuantity(int qty)
	{
		if(qty < 1)
		{
			//#CM51748
			//<en>6123207 = Please enter 1 or greater value for max. mix-load quantity.</en>
			setMessage("6123207");
			return false;
		}
		
		return true;
	}

	//#CM51749
	/**<en>
	 * Check the bank (range). In this method, the following will be checked.<br>
	 *  -the start bank should be smaller than end bank in the value.<br>
	 *  -the value should be 1 or greater.<br>
	 * @param stArg  :specifies the bank to be checked.
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkBank( int startloc, int endloc )
	{
		if(startloc < 1)
		{
			//#CM51750
			//<en>6123071 = Please specify 1 or greater value for the range.</en>
			setMessage("6123071");			
			return false;
		}
		if(endloc < 1)
		{
			//#CM51751
			//<en>6123071 = Please specify 1 or greater value for the range.</en>
			setMessage("6123071");
			return false;
		}
		if(startloc > endloc)
		{
			//#CM51752
			//<en>6123068 = Please set the smaller value for start bank than end bank.</en>
			setMessage("6123068");
			return false;
		}

		return true;
	}

	//#CM51753
	/**<en>
	 * Check the bay (range). In this method, the following will be checked.<br>
	 *  -the start bay should be smaller than end bay in the value.<br>
	 *  -the value should be 1 or greater.<br>
	 * @param stArg  :specifies the bay to be checked.
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkBay( int startloc, int endloc )
	{
		if(startloc < 1)
		{
			//#CM51754
			//<en>6123071 = Please specify 1 or greater value for the range.</en>
			setMessage("6123071");			
			return false;
		}
		if(endloc < 1)
		{
			//#CM51755
			//<en>6123071 = Please specify 1 or greater value for the range.</en>
			setMessage("6123071");
			return false;
		}
		if(startloc > endloc)
		{
			//#CM51756
			//<en>6123069 = Please set the smaller value for start bay than the value of end bay.</en>
			setMessage("6123069");
			return false;
		}
		
		return true;
	}

	//#CM51757
	/**<en>
	 * Check the level (range). In this method, the following will be checked.<br>
	 *  -the start level should be smaller than end level in the value.<br>
	 *  -the value should be 1 or greater.<br>
	 * @param stArg  :specifies the level to be checked.
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkLevel( int startloc, int endloc )
	{
		if(startloc < 1)
		{
			//#CM51758
			//<en>6123071 = Please specify 1 or greater value for the range.</en>
			setMessage("6123071");			
			return false;
		}
		if(endloc < 1)
		{
			//#CM51759
			//<en>6123071 = Please specify 1 or greater value for the range.</en>
			setMessage("6123071");
			return false;
		}
		if(startloc > endloc)
		{
			//#CM51760
			//<en>6123070 = Please set the smaller value for start level than the value of end level.</en>
			setMessage("6123070");
			return false;
		}
		
		return true;
	}

	//#CM51761
	/**
	 * Check Aisle position (range). StartAisle position must check that 
	 * smaller value than End Aisle position is a value of one or 
	 * more in this Method. 
	 * @param stArg  Specify Aisle position which checks it. 
	 * @return    Return the result of the check. Return True when it is all correct. 
	 */
	public boolean checkAislePosition( int startpos, int endpos )
	{
		if(startpos < 1)
		{
			//#CM51762
			//<en>6123071 = Please specify 1 or greater value for the range.</en>
			setMessage("6123071");			
			return false;
		}
		if(endpos < 1)
		{
			//#CM51763
			//<en>6123071 = Please specify 1 or greater value for the range.</en>
			setMessage("6123071");
			return false;
		}
		if(startpos > endpos)
		{
			//#CM51764
			//6123280= Specify smaller value than End Aisle position for StartAisle position. 
			setMessage("6123280");
			return false;
		}

		if(startpos != endpos - 1)
		{
			//#CM51765
			//6123281= The input of Aisle position is illegal. 
			setMessage("6123281");
			return false;
		}

		return true;
	}

	//#CM51766
	/**<en>
	 * Check the station no. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the station no. to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkStationNumber( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51767
			//<en>6123009 = The station no. contains the unacceptable letters in system.</en>
			setMessage("6123009");
			return false;
		}
		
		return true;
	}

	//#CM51768
	/**<en>
	 * Check the station name. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the station name to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkStationName( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51769
			//<en>6123167 = The station nome contains the unacceptable letters in system.</en>
			setMessage("6123167");
			return false;
		}
		
		return true;
	}

	//#CM51770
	/**<en>
	 * Check the warehouse name. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the warehouse name to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkWarehouseName( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51771
			//<en>6123017 = The warehouse name contains the unacceptable letters in system.</en>
			setMessage("6123017");
			return false;
		}
		return true;
	}

	//#CM51772
	/**<en>
	 * Check the user name. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the user name to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkUserName( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51773
			//<en>6123022 = The user name contains the unacceptable letters in system.</en>
			setMessage("6123022");
			return false;
		}
		
		return true;
	}

	//#CM51774
	/**<en>
	 * Check the password. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the password to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/  
	public boolean checkPassword( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51775
			//<en>6123023 = The password contains the unacceptable letters in system.</en>
			setMessage("6123023");
			return false;
		}
		return true;
	}

	//#CM51776
	/**<en>
	 * Check the terminal no. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the terminal no. to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkTerminalNumber( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51777
			//<en>6123104 = The terminal no. contains the unacceptable letters in system.</en>
			setMessage("6123104");
			return false;
		}
		if(stArg.equals("0"))
		{
			//#CM51778
			//<en>6123212 = Please enter 1 or greater number for the terminal no.</en>
			setMessage("6123212");
			return false;
		}
		
		return true;
	}

	//#CM51779
	/**<en>
	 * Check the terminal name. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the terminal name to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkTerminalName( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51780
			//<en>6123105 = The terminal name contains the unacceptable letters in system.</en>
			setMessage("6123105");
			return false;
		}
		
		return true;
	}

	//#CM51781
	/**<en>
	 * Check the zone name. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the zone name to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkZoneName( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51782
			//<en>6123072 = The zone name contains the unacceptable letters in system.</en>
			setMessage("6123072");
			return false;
		}
		
		return true;
	}

	//#CM51783
	/**<en>
	 * Check the name of the load size. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the name of the load size to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkLoadName( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51784
			//<en>6123084 = The load size name contains the unacceptable letters in system.</en>
			setMessage("6123084");
			return false;
		}
		
		return true;
	}
	
	//#CM51785
	/**<en>
	 * Check the host name. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the host name to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkHostName( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51786
			//<en>6123169 = The host name contains the unacceptable letters in system.</en>
			setMessage("6123169");
			return false;
		}
		return true;
	}
	
	//#CM51787
	/**<en>
	 * Check the IP address. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the IP address to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkIPAdress( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51788
			//<en>6123200 = The IP address contains the unacceptable letters in system.</en>
			setMessage("6123200");
			return false;
		}
		return true;
	}

	//#CM51789
	/**<en>
	 * Check the printer (name). In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the printer (name) to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkPrinterName( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51790
			//<en>6003106 = {0} contains the unacceptable letters in system.</en>
			setMessage("6003106" + MessageResource.DELIM + DisplayText.getText("TLBL-A0129"));
			return false;
		}
		
		return true;
	}

	//#CM51791
	/**<en>
	 * Check the role ID. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg  :specifies the role ID to be checked
	 * @return       :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkRoleId( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51792
			//<en>6003106 = {0} contains the unacceptable letters in system.</en>
			setMessage("6003106" + MessageResource.DELIM + DisplayText.getText("TLBL-A0128"));
			return false;
		}
		
		return true;
	}

	//#CM51793
	/**<en>
	 * Check the classification name. In this method, a check will be done for below;<br>
	 *  -unacceptable letters and symbols<br>
	 * @param stArg :specifies the name of the classification to be checked.
	 * @return      :returns the check result. Returns true if all are correct.
	 </en>*/
	public boolean checkClassificationName( String stArg )
	{
		if(isUndefinedChar(stArg))
		{
			//#CM51794
			//<en>6123023 = The classification name contains the unacceptable letters in system.</en>
			setMessage("6123023");
			return false;
		}
		
		return true;
	}

	//#CM51795
	// Package methods -----------------------------------------------

	//#CM51796
	// Protected methods ---------------------------------------------
	
	protected ToolGroupControllerHandler getGCHandler()
	{
		return wGCHandler;
	}

	protected ToolWarehouseHandler getWarehouseHandler()
	{
		return wWarehouseHandler;
	}
	
	protected ToolAisleHandler getAisleHandler()
	{
		return wAisleHandler;
	}
	
	protected ToolStationHandler getStationHandler()
	{
		return wStationHandler;
	}
	
	protected ToolShelfHandler getShelfHandler()
	{
		return wShelfHandler;
	}
	
	protected ToolTerminalAreaHandler getTerminalAreaHandler()
	{
		return wTerminalAreaHandler;
	}

	//#CM51797
	/**<en>
	 * Set to the message that check preserves.
	 * In case the checked content was unacceptable, the detail will be set.
	 * @param :contents of the message
	 </en>*/
	protected void setMessage(String msg)
	{
		wMessage = msg;
	}

	//#CM51798
	/**<en>
	 * Check the unacceptable letters and symbols.
	 * @param  param :parameter to be checked
	 * @return :return true if unacceptable letters and symbols was included.
	 </en>*/
	private boolean isUndefinedChar(String param)
	{
		//#CM51799
		//<en>Check for the unacceptable letters and symbols.</en>
		return (isPatternMatching(param));
	}
	
	//#CM51800
	/**<en>
	 * Examine whether/not the unacceptable letters and symbols defined by system are contained
	 * in specified string.
	 * Definition of unacceptable letters and symbols can be specified by CommonParam.
	 * @param pattern :specifies the target string.
	 * @return :retutn true if the string contains unacceptable letters and symbols, or false if
	 * unacceptable letters and symbols are not contained.
	 </en>*/
	private static boolean isPatternMatching(String pattern)
	{
		return (isPatternMatching(pattern, ToolParam.getParam("NG_PARAMETER_TEXT"))) ;
	}

	//#CM51801
	/**<en>
	 * Examine whether/not the system defined unacceptable letters and symbols are contained
	 * in specified string.
	 * Definition of unacceptable letters and symbols can be specified by CommonParam.
	 * @param pattern :specifies the target string.
	 * @param ngshars :unacceptable letters and symbols
	 * @return :retutn true if the string contains unacceptable letters and symbols, or false if
	 * unacceptable letters and symbols are not contained.
	 </en>*/
	private static boolean isPatternMatching(String pattern, String ngshars)
	{
		if (pattern != null && !pattern.equals(""))
		{
			for (int i = 0; i < ngshars.length() ; i++)
			{
				if (pattern.indexOf(ngshars.charAt(i)) > -1)
				{
					return true ;
				}
			}
		}
		return false ;
	}

	//#CM51802
	// Private methods -----------------------------------------------

}
//#CM51803
//end of class
