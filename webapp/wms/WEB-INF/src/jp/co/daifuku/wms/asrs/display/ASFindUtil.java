// $Id: ASFindUtil.java,v 1.2 2006/10/26 03:10:25 suresh Exp $
package jp.co.daifuku.wms.asrs.display ;

//#CM34479
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.dbhandler.ASWorkPlaceHandler;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationTypeHandler;
import jp.co.daifuku.wms.base.dbhandler.StationTypeSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.StationType;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkingInformation;


//#CM34480
/**
 * This class provides the general search method to be used in screens and printing reports.<BR>
 * Purpose of this class is to prevent the duplicate use of same search method in different classes,<BR>
 * in order to facilitate the debugging by limiting location to the least as possible.<BR>
 * As the methods in this class increase, they will be devided into groups according to each purpose <BR>
 * and taken out to different classes.<BR>
 * Please ensure not to implement in different classes. And if a method needs to be added, please check <BR>
 * if the method has been implemented anywhere else before implementation.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/06/27</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:10:25 $
 * @author  $Author: suresh $
 */
public class ASFindUtil extends Object
{
	//#CM34481
	// Class fields --------------------------------------------------
	//#CM34482
	/**
	 * Delimiter of parameter for MessageDef message. <BR>
	 * Ex. setMessage("9000000" + DELIM + "Palette" + DELIM + "Stock") ;
	 * @see jp.co.daifuku.common.MessageResource
	 */
	protected static final String DELIM = MessageResource.DELIM ;

	//#CM34483
	// Class variables -----------------------------------------------
	//#CM34484
	/**
	 * <CODE>Connection</CODE>
	 */
	private Connection wConn = null ;

	//#CM34485
	// Class method --------------------------------------------------
	//#CM34486
	/**
	 * Return the version of this class.
	 * @return :Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:10:25 $") ;
	}

	//#CM34487
	// Constructors --------------------------------------------------
	//#CM34488
	/**
	 * Construct the <CODE>FindUtil</CODE> object by getting <CODE>Connection</CODE>.
	 * @param conn <CODE>Connection</CODE>
	 */
	public ASFindUtil(Connection conn)
	{
		wConn = conn ;
	}

	//#CM34489
	// Public methods ------------------------------------------------


	//#CM34490
	/**
	 * The type Date that a date and time were made an argument and it was connected is returned.
	 * When a date is null : present date + time
	 * When time is null : the time of the date + present
	 * When both are null's : null
	 * It uses when input of a date and input of time are input separately.
	 * @param  date
	 * @param  time
	 * @return connected date
	 */	
	public Date getDate(Date date, Date time)
	{
		try
		{
			String dateFormat = "yyyyMMdd";
			String timeFormat = "HHmmss";
			DateFormat dfDate = new SimpleDateFormat(dateFormat);
			DateFormat dfTime = new SimpleDateFormat(timeFormat);
			
			Date now = new Date();
			
			String dateString = "";
			String timeString = "";
			
			if(date == null && time != null)
			{
				dateString = dfDate.format(now);
				timeString = dfTime.format(time);
			}
			else if(date != null && time == null)
			{
				dateString = dfDate.format(date);
				timeString = dfTime.format(now);
			}
			else if(date != null && time != null)
			{
				dateString = dfDate.format(date);
				timeString = dfTime.format(time);
			}
			else
			{
				return null;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat();

			sdf.applyPattern( dateFormat + timeFormat );

			return sdf.parse( dateString + timeString );
		}
		catch(Exception e)
		{
			return null;
		}
	}

	//#CM34491
	/**
	 * It returns the hard zone name using the HardZoneId as a search key. 
	 * In case the hard zone name is not defined, it returns string array of 0 byte.
	 * @param  clsid :HardZone ID
	 * @return  :HardZone name
	 * @throws ReadWriteException :Notify the exception as it is that occurred in connection with database.
	 */
	public String getHardZoneName(int clsid) throws ReadWriteException
	{
		HardZoneHandler   hh = new HardZoneHandler(wConn) ;
		HardZoneSearchKey hk = new HardZoneSearchKey() ;

		hk.setHardZoneID(clsid) ;
		HardZone[] zone = (HardZone[])hh.find(hk) ;
		if ( zone.length != 0 )
		{
			return zone[0].getName() ;
		}
		return "" ;
	}

	//#CM34492
	/**
	 * It returns the aisle No. using the aisle station No. as a search key. 
	 * @param aisleStationNumber :aisle station No.
	 * @return  :aisle No.
	 * @throws ReadWriteException :Notify the exception as it is that occurred in connection with database.
	 */
	public int getAisleNumber(String aisleStationNumber ) throws ReadWriteException
	{
		AisleHandler   aisleHandle = new AisleHandler(wConn) ;
		AisleSearchKey aisleKey = new AisleSearchKey() ;

		aisleKey.setStationNumber(aisleStationNumber) ;
		Aisle[] aisle = (Aisle[])aisleHandle.find(aisleKey) ;
		if ( aisle.length != 0 )
		{
			return aisle[0].getAisleNumber() ;
		}
		return 0 ;
	}

	//#CM34493
	/**
	 * aisle station no. returns "RM-" + aisle terminal no.
	 * warehouse station no, location no. returns warehouse name
	 * station no., workplace no. returns station name
	 * @param  stationno aisle station, warehouse station, station, workplace, location no.
	 * @return warehouse name or station name
	 * @throws ReadWriteException Throw exception that occurs during database connection
	 */
	public String getAsStationName(String stationno) throws ReadWriteException
	{
		String stName = "";
		String stno = stationno.trim();

		try
		{
			//#CM34494
			// Confirm whether station no. exist in station type table
			StationTypeHandler typHandler = new StationTypeHandler(wConn);
			StationTypeSearchKey typKey = new StationTypeSearchKey();
			typKey.setStationnumber(stno.trim());
			StationType stype = (StationType) typHandler.findPrimary(typKey);
			if( stype != null )
			{
				//#CM34495
				// Fetch the station type table handler class
				String strType = stype.getHandlerclass();

				//#CM34496
				//Refer station type table handler class
				if( strType.equals(WareHouseHandler.class.getName()) )
				{
					//#CM34497
					//Fetch the warehouse name with refer to warehouse station no. from warehouse table
					stName = getWareHouseName(stno);
				}
				else if( strType.equals(AisleHandler.class.getName()) )
				{
					AisleHandler   aisleHandle = new AisleHandler(wConn);
					AisleSearchKey aisleKey = new AisleSearchKey();
					aisleKey.setStationNumber(stno) ;
					Aisle aisle = (Aisle) aisleHandle.findPrimary(aisleKey) ;
					//#CM34498
					// LBL-A0039=RM-
					stName = stno + ":" + DisplayText.getText("LBL-A0039") + Integer.toString(aisle.getAisleNumber());
				}
				else if( strType.equals(StationHandler.class.getName()) ||
						  strType.equals(ASWorkPlaceHandler.class.getName()) )
				{
					//#CM34499
					// Fetch station name from station table 
					stName = getStationName(stno);
				}
				else if( strType.equals(ShelfHandler.class.getName()) )
				{
					//#CM34500
					// Fetch aisle station no. from shelf table
					ShelfHandler shfHandler = new ShelfHandler(wConn);
					ShelfSearchKey shfKey = new ShelfSearchKey();
					shfKey.setStationNumber(stno);
					Shelf shlf = (Shelf) shfHandler.findPrimary(shfKey);

					AisleHandler   aisleHandle = new AisleHandler(wConn);
					AisleSearchKey aisleKey = new AisleSearchKey();
					aisleKey.setStationNumber(shlf.getAisleStationNumber()) ;
					Aisle aisle = (Aisle) aisleHandle.findPrimary(aisleKey) ;
					//#CM34501
					// LBL-A0039=RM-
					stName = DisplayText.getText("LBL-A0039") + Integer.toString(aisle.getAisleNumber());
				}
			}
			else
			{
				//#CM34502
				// Return as it is since station no. won't exist in station type table
				stName = stno;
			}

			return stName;
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM34503
	/**
	 * It returns the warehosue name using the warehouse station No. as a search key.
	 * @param  whstno :warehouse station No.
	 * @return  :warehouse name
	 * @throws ReadWriteException :Notify the exception as it is that occurred in connection with database.
	 */
	public String getWareHouseName(String whstno) throws ReadWriteException
	{
		return getWareHouseName(whstno, null);
	}

	//#CM34504
	/**
	 * It returns the warehouse name using the warehouse station No. as a search key.
	 * @param  whstno :warehouse station No.
	 * @param  locale :locale
	 * @return  :warehouse name
	 * @throws ReadWriteException :Notify the exception as it is that occurred in connection with database.
	 */
	public String getWareHouseName(String whstno, Locale locale) throws ReadWriteException
	{
		//#CM34505
		// If all warehouses are applicable,
		if (AsrsParam.ALL_WH_NO.equals(whstno))
		{
			 return DisplayText.getText("ALLWAREHOUSE_TEXT");
		}
		WareHouseHandler   whh = new WareHouseHandler(wConn) ;
		WareHouseSearchKey wk = new WareHouseSearchKey() ;
		wk.setStationNumber(whstno) ;
		WareHouse[] wh = (WareHouse[])whh.find(wk) ;
		if ( wh.length != 0 )
		{
			return wh[0].getWareHouseNumber()+":"+wh[0].getWareHouseName() ;
		}
		return "";
	}

	//#CM34506
	/**
	 * It returns the station name using the station No. as a search key.
	 * @param  stno :station No.
	 * @return :station name
	 * @throws ReadWriteException :Notify the exception as it is that occurred in connection with database.
	 * @throws InvalidDefineException :Notify if generation of Station instance failed.
	 * @throws NotFoundException :Notify if StationNo is undefined.
	 */
	public String getStationName(String stno) throws ReadWriteException,InvalidDefineException,NotFoundException
	{
		return getStationName(stno, null);
	}

	//#CM34507
	/**
	 * It returns the station name using the station No. as a search key.
	 * @param  stno :station No.
	 * @param  locale :loale
	 * @return :station name
	 * @throws ReadWriteException :Notify the exception as it is that occurred in connection with database.
	 * @throws InvalidDefineException :Notify if generation of Station instance failed.
	 * @throws NotFoundException :Notify if StationNo is undefined.
	 */
	public String getStationName(String stno, Locale locale) throws ReadWriteException,InvalidDefineException,NotFoundException
	{
		try
		{
			//#CM34508
			// If all stations are applicable,
			if (AsrsParam.ALL_RETRIEVAL_STATION.equals(stno))
			{
				 return DisplayText.getText("ALLSTATION_TEXT");
			}
			StationHandler wStHandler = new StationHandler(wConn);
			StationSearchKey wStSearchKey = new StationSearchKey();
			
			wStSearchKey.KeyClear();
			wStSearchKey.setStationNumber(stno);
			
			Station st = (Station)wStHandler.findPrimary(wStSearchKey);
			return stno+":"+st.getStationName() ;
		}
		catch (ReadWriteException e)
		{
			//#CM34509
			// Database error
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			return "";
		}
	}

	//#CM34510
	/**
	 * Check the status of the location specified by location No. then return the result.<BR>
	 * @param location :location No.
	 * @return   2: Specified location No. is empty location<BR>
	 *   		 1: Specified location No. is occupied<BR>
	 *   		 0: Specified location No. is reserved<BR>
	 *   		-1: Specified location No. exists<BR>
	 *			-2: Specified location No. is inaccessible<BR>
	 *  		-3: Specified location No. is unusable<BR>
	 * @throws ReadWriteException :Notify if an error occurred in connection with database.
	 */
	public int isNormalShelf(String location) throws ReadWriteException
	{
		Station st = null;
		ShelfHandler wShHandler = new ShelfHandler(wConn);
		ShelfSearchKey wShSearchKey = new ShelfSearchKey();

		try
		{
				
			wShSearchKey.KeyClear();
			//#CM34511
			// warehouse type + input location no.
			wShSearchKey.setStationNumber(location);
			st = (Shelf)wShHandler.findPrimary(wShSearchKey);
		}
		catch (ReadWriteException e)
		{
			//#CM34512
			// If the generation of Station instance failed,
			return -1;
		}
		catch (NoPrimaryException e)
		{
			//#CM34513
			// If Station is not found, it returns null.
			return -1;
		}

		if (st == null)
		{
			return -1;
		}

		if (st instanceof Shelf)
		{
			Shelf shf = (Shelf)st;
			if (shf.getAccessNgFlag() == Shelf.ACCESS_NG)
			{
				//#CM34514
				// Location is inaccessible.
				return -2;
			}
			if (shf.getStatus() == Station.STATION_NG)
			{
				//#CM34515
				// Location is restricted.
				return -3;
			}
			switch (shf.getPresence())
			{
				//#CM34516
				// Location is empty.
				case Shelf.PRESENCE_EMPTY:
					return 2 ;
				//#CM34517
				// Location is reserved.
				case Shelf.PRESENCE_RESERVATION:
					return 0 ;
			}
		}
		else
		{
			//#CM34518
			// Location does not exist.
			return -1;
		}

		//#CM34519
		// Location is occupied.
		return 1;
	}

	//#CM34520
	/**
	 * It returns the group of sendable <CODE>Station</CODE>.
	 * @return :returns the <CODE>Station</CODE> as a group of <CODE>Entity</CODE>.
	 * @throws ReadWriteException :Notify if reading from storage system failed.
	 */
	public Entity[] getStationArray() throws ReadWriteException
	{
		StationHandler   sh    = new StationHandler(wConn) ;
		StationSearchKey key   = new StationSearchKey() ;
		key.setSendable(Station.SENDABLE_TRUE) ;
		key.setStationNumberOrder(1, true) ;
		return (sh.find(key)) ;
	}

	//#CM34521
	/**
	 * Check wheather the system status is on-line or not.<BR>
	 * It returns false if the status is off-line, reserved for termination or unknown (other than on-line status).
	 * <FONT COLOR="RED">This method will be relocated to the class that checks system and station status in future.</FONT>
	 * @return :return TRUE if <CODE>GroupController</CODE> is on-line, FALSE if off-line.
	 * @throws ReadWriteException :Notify if an error occurred in connection with database.
	 */
	public boolean isSystemOnLine() throws ReadWriteException
	{
		//#CM34522
		// Get the sendable station group.
		Station[] station = (Station[])getStationArray() ;
		for (int i = 0 ; i < station.length ; i++)
		{
			GroupControllerHandler wGcHandler = new GroupControllerHandler(wConn);
			GroupControllerSearchKey wGcSearchKey = new GroupControllerSearchKey();
			
			wGcSearchKey.KeyClear();
			wGcSearchKey.setControllerNumber(station[i].getControllerNumber());
			GroupController[] rGcData = (GroupController[])wGcHandler.find(wGcSearchKey);
			 
			if (rGcData[0].getStatus() != GroupController.STATUS_ONLINE)
			{
				return false ;
			}
		}
		return true ;
	}

	//#CM34523
	/**
	 * Get the warehouse No. from the specified warehouse station No.
	 * Ex. 9000 --> 1
	 * @param  whStNo :warehouse station No.
	 * @return :warehouse No.
	 * @throws InvalidDefineException :Notify if definition of the class is incorrect.
	 * @throws ReadWriteException :Notify if an error occurred in connection with database.
	 * @throws NotFoundException :Notify if corresponding class cannot be found.
	 */
	public int getWHNumber(String whStNo) throws ReadWriteException, InvalidDefineException, NotFoundException
	{
		try
		{
			WareHouseHandler wWhHandler = new WareHouseHandler(wConn);
			WareHouseSearchKey wWhSearchKey = new WareHouseSearchKey();
			
			wWhSearchKey.KeyClear();
			wWhSearchKey.setStationNumber(whStNo);
			
			WareHouse wWhData = (WareHouse)wWhHandler.findPrimary(wWhSearchKey);

			return wWhData.getWareHouseNumber();
		}
		//#CM34524
		//As makeStation throws SQLException,
		catch(NoPrimaryException e)
		{
			throw new ReadWriteException();
		}
	}

	//#CM34525
	/**
	 * Search the Palette based on the specified location No., then return the status of stocks.
	 * @param  location :location No.
	 * @return Staus of Palette :returns -1 if no data was found.
	 * @throws ReadWriteException :Notify if an error occurred in connection with database.
	 * @throws NotFoundException :Notify if corresponding data was not found.
	 */
	public int getPaletteStatus(String location) throws ReadWriteException, NotFoundException
	{
		PaletteHandler paletteHandle = new PaletteHandler(wConn) ;
		PaletteSearchKey paletteKey  = new PaletteSearchKey() ;

		paletteKey.setCurrentStationNumber(location) ;

		Palette[] palette = (Palette[])paletteHandle.find(paletteKey) ;

		//#CM34526
		// Target data was not found.
		if (palette == null || palette.length <= 0)
		{
			//#CM34527
			// 6003018 = The data was not found.
			return -1;
		}

		return (palette[0].getStatus()) ;
	}

	//#CM34528
	/**
	 * Decide the system identification key based on storage location no.
	 * @param  locationnumber location no.(101001001000 type)
	 * @return System identification key
	 * @throws ReadWriteException Throw exception that occurs during database connection
	 */
	public int getSystemDiskKey(String locationnumber) throws ReadWriteException
	{
		try
		{
			ShelfSearchKey  skey  = new ShelfSearchKey() ;
			ShelfHandler    shdle = new ShelfHandler(wConn) ;
			//#CM34529
			// set station no.
			skey.setStationNumber(locationnumber) ;
			//#CM34530
			// Search Palette table
			int asrsCount = shdle.count( skey );

			//#CM34531
			// ASRS
			if ( asrsCount > 0)			return WorkingInformation.SYSTEM_DISC_KEY_ASRS;

			LocateSearchKey  idmkey  = new LocateSearchKey() ;
			LocateHandler    idmhdle = new LocateHandler(wConn) ;
			//#CM34532
			// set station no.
			idmkey.setLocationNo(locationnumber) ;
			//#CM34533
			// Search Palette table
			int idmCount = idmhdle.count( idmkey );

			//#CM34534
			// ASRS
			if ( idmCount > 0)			return WorkingInformation.SYSTEM_DISC_KEY_WMS;

			return WorkingInformation.SYSTEM_DISC_KEY_WMS ;

		}
		catch (ReadWriteException nfe )
		{
			return -1 ;
		}
	}

	//#CM34535
	/**
	 * Return station no.<BR>
	 * If location no. is specified, return warehouse station no.
	 * @param  stationno station
	 * @return station no.
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public String getDispStationNo(String stationno) throws ReadWriteException
	{
		try
		{
			int waresz   = WmsParam.WAREHOUSE_DISP_LENGTH;
			int banksz   = WmsParam.BANK_DISP_LENGTH;
			int baysz    = WmsParam.BAY_DISP_LENGTH;
			int levelsz  = WmsParam.LEVEL_DISP_LENGTH;
			int areasz   = WmsParam.AREA_DISP_LENGTH;
			int stlen = waresz + banksz + baysz + levelsz + areasz ;

			String stno = stationno.trim();
			if( stno.length() >= stlen )
			{
				//#CM34536
				// Confirm whether station no. exist in station type table
				ShelfHandler slfHandler = new ShelfHandler(wConn);
				ShelfSearchKey slfKey = new ShelfSearchKey();
				slfKey.setStationNumber(stno);
				Shelf shlf = (Shelf) slfHandler.findPrimary(slfKey);
				if( shlf != null )
				{
					return shlf.getAisleStationNumber();
				}
			}
			return stno;
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM34537
	// Package methods -----------------------------------------------

	//#CM34538
	// Protected methods ---------------------------------------------

	//#CM34539
	// Private methods -----------------------------------------------

	//#CM34540
	// Internal class ------------------------------------------------
}
//#CM34541
//end of class

