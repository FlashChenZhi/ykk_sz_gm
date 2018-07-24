// $Id: As21Id05.java,v 1.2 2006/10/26 01:41:00 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM28866
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;

import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM28867
/**
 * Composes communication message "Transport Command ID=05" according to the communication protocol AS21.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:41:00 $
 * @author	$Author: suresh $
 */
public class As21Id05 extends SendIdMessage
{
	//#CM28868
	// Class fields --------------------------------------------------
	//#CM28869
	/**
	 * Field of storage (transport section)
	 */
	public static final String C_STORAGE				= "1" ;

	//#CM28870
	/**
	 * Field of direct traveling (transport section)
	 */
	public static final String C_DIRECT_TRANSFER		= "3" ;

	//#CM28871
	/**
	 * Field of storage setting type (setting in advance)
	 */
	public static final String C_SETTING_IN_ADVANCE		= "1" ;

	//#CM28872
	/**
	 * Field of storage setting type (Setting to confirm load presence)
	 */
	public static final String C_LOAD_CHECK_SETTING		= "2" ;

	//#CM28873
	/**
	 * Length of Header; fixed for each communication message
	 */
	protected static final int HEDERLENGTH					=  16;

	//#CM28874
	/**
	 * Length of MC Key
	 */
	protected static final int CARRYKEY					=  8;

	//#CM28875
	/**
	 * Length of transport classification
	 */
	protected static final int TRANSPORT_CLASSIFICATION	=  1;

	//#CM28876
	/**
	 *Length of setting classification
	 */
	protected static final int SETTING_CLASSIFICATION		=  1;

	//#CM28877
	/**
	 * Length of Group number
	 */
	protected static final int GROUP_NO					=  3;

	//#CM28878
	/**
	 * Length of Station number
	 */
	protected static final int STATIONNUMBER				=  4;

	//#CM28879
	/**
	 * Length of location number 
	 */
	protected static final int LOCATION_NO					= 12;

	//#CM28880
	/**
	 * Length of load size
	 */
	protected static final int DIMENSION_INFORMATION		=  2;

	//#CM28881
	/**
	 * Length of BC Data
	 */
	protected static final int BC_DATA						= 30;

	//#CM28882
	/**
	 * Length of of work number
	 */
	protected static final int WORK_NO						=  8;

	//#CM28883
	/**
	 * Length of control information
	 */
	protected static final int CONTROL_INFORMATION			= 30;

	//#CM28884
	/**
	 * Length of communication message
	 */
	protected final int LEN_TOTAL = HEDERLENGTH
								+ CARRYKEY
								+ TRANSPORT_CLASSIFICATION
								+ SETTING_CLASSIFICATION
								+ GROUP_NO
								//#CM28885
								// From and To
								+ (STATIONNUMBER * 2)
								+ LOCATION_NO
								+ DIMENSION_INFORMATION
								+ BC_DATA
								+ WORK_NO
								+ CONTROL_INFORMATION ;

	//#CM28886
	// Class variables -----------------------------------------------
	//#CM28887
	/**
	 * Variable which preserves <code>CarryInformation</code>, containing carry information.
	 */
	private CarryInformation wCarryInfo ;

	//#CM28888
	/**
	 * Variable which preserves <code>Connection</code>, so that station instance can be acquired (for 
	 * use of MakeStation).
	 */
	private Connection	wConn = null;

	//#CM28889
	// Class method --------------------------------------------------
     //#CM28890
     /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:41:00 $") ;
	}
	//#CM28891
	// Constructors --------------------------------------------------
	//#CM28892
	/**
	 * Default constructor
	 */
	public As21Id05 ()
	{
		super();
	}
	//#CM28893
	/**
	 * Generates the instance of this class by specifying the instance of <code>CarryInformation</code>,
	 * with information to carry.
	 * Generates the instance of this class.
	 * @param  ci  which has carry information<code>CarryInformation</code>
	 */
	public As21Id05(CarryInformation ci)
	{
		super() ;
		wCarryInfo = ci ;
	}

	//#CM28894
	// Public methods ------------------------------------------------
	//#CM28895
	/**
	 * Creates communication message of carry instruction.
	 * <p></p>required to releasae carry instruction
	 * <ul>
	 * <li>MC Key
	 * <li>transport segment
	 * <li>setting classification
	 * <li>Group number
	 * <li>Sending station number
	 * <li>Receiving station number
	 * <li>Location number
	 * <li>load size
	 * <li>BC Data
	 * <li>work number
	 * <li>control information
	 * </ul>
	 * <p> acquires information out of instance of <code>CarryInformation</code> given in constructor.
	 * </p>
	 * @return	  communication message for carrying instruction
	 * @throws InvalidProtocolException  : Notifies if communication message includes improper contents from the protocol aspect.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM28896
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM28897
		//-------------------------------------------------
		//#CM28898
		// Attention! Order of the contents must be observed!
		//#CM28899
		//-------------------------------------------------
		//#CM28900
		// MC Key
		mbuf.append(getMCKey()) ;
		//#CM28901
		// Transport classification (included in Carry Information)
		mbuf.append(getTransClass()) ;
		//#CM28902
		// Setting classification (included in Station)
		mbuf.append(getSettingClass()) ;
		//#CM28903
		// Group number(included in Carry Information)
		mbuf.append(getGroupNumber()) ;
		//#CM28904
		// Sending station number (included in Carry Information)
		mbuf.append(getFromStationNumber()) ;
		//#CM28905
		// Receiving station number (included in Carry Information)
		mbuf.append(getDestStationNumber()) ;
		//#CM28906
		// Location number (included in Carry Information)
		mbuf.append(getDestLocationNumber()) ;
		//#CM28907
		// Load size (included in Pallet)
		mbuf.append(getDimensionInfo()) ;
		//#CM28908
		// BC Data	(included in Pallet)
		mbuf.append(getBcData()) ;
		//#CM28909
		// Work number (included in Carry Information)
		mbuf.append(getWorkNumber());
		//#CM28910
		// Control information (included in Carry Information)
		mbuf.append(getControlInfo());

		//#CM28911
		//-------------------------------------------------
		//#CM28912
		// Sets as sending message buffer
		//#CM28913
		//-------------------------------------------------
		//#CM28914
		// id
		setID("05") ;
		//#CM28915
		// id segment
		setIDClass("00") ;
		//#CM28916
		// time sent
		setSendDate() ;
		//#CM28917
		// time sent to AGC
		setAGCSendDate() ;
		//#CM28918
		// text contents
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL)) ;
	}

	//#CM28919
	// Package methods -----------------------------------------------

	//#CM28920
	// Protected methods ---------------------------------------------

	//#CM28921
	// Private methods -----------------------------------------------
	//#CM28922
	/**
	 * Retrieves MC Key out of <code>CarryInformation</code>.
	 * @return MC Key
	 * @throws InvalidProtocolException : Reports if MC Key exceeds the allowable length.
	 */
	private String getMCKey() throws InvalidProtocolException
	{
		StringBuffer stbuff = new StringBuffer( CARRYKEY );
		//#CM28923
		// MC Key(included in CarryInformation)
		String carryKey = wCarryInfo.getCarryKey();
		if( carryKey.length() > CARRYKEY )
		{
			throw new InvalidProtocolException("carryKey = " + CARRYKEY + "--->" + carryKey) ;
		}
		else
		{
			stbuff.replace(0, CARRYKEY,  "00000000");
			stbuff.replace(CARRYKEY - carryKey.length(), CARRYKEY,  carryKey);
		}
		return (stbuff.toString()) ;
	}

	//#CM28924
	/**
	 * Retrieves transport classification out of <code>CarryInformation</code>.
	 * @return transport classification
	 * @throws InvalidProtocolException : Reports if transport classification is not the allowable length.
	 */
	private String getTransClass() throws InvalidProtocolException
	{
		//#CM28925
		// Transport classification (included in CarryInformation)
		String transportClassification = "" ;
		int inoutKind = wCarryInfo.getCarryKind();
		if( inoutKind == CarryInformation.CARRYKIND_STORAGE )
		{
			transportClassification = C_STORAGE;
		}
		else if(inoutKind == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
		{
			transportClassification = C_DIRECT_TRANSFER;
		}
		else 
		{
			System.err.println("transportClassification = " + transportClassification);
		}
		if( transportClassification.length() != TRANSPORT_CLASSIFICATION )
		{
			throw new InvalidProtocolException("transportClassification = " + TRANSPORT_CLASSIFICATION + "--->" + transportClassification) ;
		}
		return (transportClassification) ;
	}

	//#CM28926
	/**
	 * Retrieves setting classification out of <code>CarryInformation</code>.
	 * @return setting classification
	 * @throws InvalidProtocolException : Reports if station can not be acquired or if setting classification is not the allowable length.
	 */
	private String getSettingClass() throws InvalidProtocolException
	{
		//#CM28927
		// Setting classification (included in Station)
		String settingClassification = "";
		Station station = null ;
		String stationNumber = wCarryInfo.getSourceStationNumber();

		try
		{
			station = StationFactory.makeStation(getConnectionForMakeStation(), stationNumber);
		}
		catch(Exception e)
		{
			throw new InvalidProtocolException("makeStation error StationNumber = " + stationNumber) ;
		}

		int settingType = station.getSettingType();

		if(settingType == Station.IN_SETTING_PRECEDE) 
		{
			settingClassification = C_SETTING_IN_ADVANCE;
		}
		else if(settingType == Station.IN_SETTING_CONFIRM)
		{
			settingClassification = C_LOAD_CHECK_SETTING;
		}
		
		if( settingClassification.length() != SETTING_CLASSIFICATION )
		{
			throw new InvalidProtocolException("settingClassification = " + SETTING_CLASSIFICATION + "--->" + settingClassification) ;
		}
		return (settingClassification) ;

	}

	//#CM28928
	/**
	 * Retrieves group number out of <code>CarryInformation</code>.
	 * @return group number
	 * @throws InvalidProtocolException : Reports if group number exceeds the allowable value.
	 */
	private String getGroupNumber() throws InvalidProtocolException
	{
		//#CM28929
		// Group No.(included in CarryInformation)
		int groupNo = wCarryInfo.getGroupNumber();
		if (groupNo > 999)
		{
			throw new InvalidProtocolException("group number too large") ;
		}
		DecimalFormat fmt = new DecimalFormat("000") ;
		String c_groupNo = fmt.format(groupNo) ;
		return (c_groupNo) ;
	}

	//#CM28930
	/**
	 * Retrieves sending station number out of <code>CarryInformation</code>.
	 * @return :sending station number
	 * @throws InvalidProtocolException : Reports if sending station number is not the allowable length.
     */
	private String getFromStationNumber() throws InvalidProtocolException
	{
		//#CM28931
		// Sending station number (included in CarryInformation)
		String sendingStationNumber = wCarryInfo.getSourceStationNumber() ;
		if( sendingStationNumber.length() != STATIONNUMBER )
		{
			throw new InvalidProtocolException("sendingStationNumber = " + STATIONNUMBER + "--->" + sendingStationNumber) ;
		}
		return (sendingStationNumber) ;
	}

	//#CM28932
	/**
	 * Retrieves receiving station number out of <code>CarryInformation</code>.
	 * @return Receiving station number
	 * @throws InvalidProtocolException : Reports if station can not be acquired or if receiving station number is not the allowable length.
	 */
	private String getDestStationNumber() throws InvalidProtocolException
	{
		if( wCarryInfo.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
		{

			String dStNum ;
			String toStno = wCarryInfo.getDestStationNumber() ;
			//#CM28933
			// If the receiving station is to be the station (4 digits), set as it is.
			if(toStno.length() != STATIONNUMBER )
			{
				WareHouseSearchKey key = new WareHouseSearchKey();
				WareHouseHandler wWareHouseHandler = new WareHouseHandler(wConn);
				WareHouse[] wareHouse = null;
				
				
				Station toSt = null;
				try
				{
					toSt = StationFactory.makeStation(getConnectionForMakeStation(), toStno);
				}
				catch(Exception e)
				{
					throw new InvalidProtocolException("makeStation error StationNumber = " + toStno) ;
				}

				
				if( toSt instanceof Shelf )
				{
					try
					{
						key.setStationNumber(toSt.getStationNumber());
				
						wareHouse = (WareHouse[])wWareHouseHandler.find(key);
					}
					catch (ReadWriteException e1)
					{
						throw new InvalidProtocolException();
					}
					
					//#CM28934
					// Returns 'XXXX' as a  StationNumber.
					dStNum = Integer.toString(wareHouse[0].getWareHouseNumber());
					
				}
				else
				{
					//#CM28935
					// Transport to the station
					//#CM28936
					// Returns'XXXX' as a station number.
					dStNum = toSt.getStationNumber();
				}
				
				if( dStNum.length() != STATIONNUMBER )
				{
					throw new InvalidProtocolException("Destination = " + STATIONNUMBER + "--->" + dStNum) ;
				}
			}
			else
			{
				dStNum = toStno;
			}

			return (dStNum) ;
		}
		else
		{
			return ("9000") ;
		}
	}

	//#CM28937
	/**
	 * Retrieves location number of the receiving stationout of <code>CarryInformation</code>.
	 * @return location number of receiving station
	 * @throws InvalidProtocolException : Reports if station or location can not be acquired.
	 */
	private String getDestLocationNumber() throws InvalidProtocolException
	{
		Station toSt = null;
		String locationNo ;

		String toStno = wCarryInfo.getDestStationNumber() ;
		//#CM28938
		// If the receiving station number is the station (12 digits), set as it is.
		if(toStno.length() != LOCATION_NO)
		{
			try
			{
				toSt = StationFactory.makeStation(getConnectionForMakeStation(), toStno);
			}
			catch(Exception e)
			{
				throw new InvalidProtocolException("makeStation error StationNumber = " + toStno) ;
			}

			if( toSt instanceof Shelf )
			{
				Shelf shelf = (Shelf)toSt;
				locationNo = shelf.getStationNumber();
				if( locationNo.length() != LOCATION_NO )
				{
					throw new InvalidProtocolException("locationNo = " + LOCATION_NO + "--->" + locationNo) ;
				}
			}
			else
			{
				byte[] wk = new byte[LOCATION_NO] ;
				for (int i=0; i < wk.length; i++)
				{
					wk[i] = '0' ;
				}
				locationNo = new String(wk) ;
			}
		}
		else
		{
			locationNo = toStno;
		}

		return (locationNo) ;
	}

	//#CM28939
	/**
	 * Acquires load size information out of <code>Palette</code>.
	 * @return loar size
	 * @throws InvalidProtocolException : Reports if palette can not be acquired.
	 */
	private String getDimensionInfo() throws InvalidProtocolException
	{
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteHandler pHandle;
		Palette[] palette = null;
		try
		{
			pHandle = new PaletteHandler(getConnectionForMakeStation());
	
			pSKey.setPaletteId(wCarryInfo.getPaletteId());

			palette = (Palette[])pHandle.find(pSKey);
		}
		catch (SQLException e)
		{
			throw new InvalidProtocolException();
		}
		catch (ReadWriteException e)
		{
			throw new InvalidProtocolException();
		}		
		
		//#CM28940
		// load size (included in Palette)
		DecimalFormat fmt1 = new DecimalFormat("00") ;
		return (fmt1.format(palette[0].getHeight())) ;
	}

	//#CM28941
	/**
	 * Acquires work number out of <code>CarryInformation</code>.
	 * @return work number
	 */
	private String getWorkNumber()
	{
		String tmpwnum = wCarryInfo.getWorkNumber();
		if(StringUtil.isBlank(tmpwnum))
		{
			tmpwnum = "";
		}
		String wnum = operateMessage(tmpwnum, WORK_NO) ;
		
		return (wnum) ;
	}

	//#CM28942
	/**
	 * Acquires bar code information out of <code>Palette</code>
	 * @return bar code information
	 * @throws InvalidProtocolException : Reports if palette can not be acquired.
	 */
	private String getBcData() throws InvalidProtocolException
	{
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteHandler pHandle;
		Palette[] palette = null;
		
		try
		{
			pHandle = new PaletteHandler(getConnectionForMakeStation());
		
	
			pSKey.setPaletteId(wCarryInfo.getPaletteId());

			palette = (Palette[])pHandle.find(pSKey);
		}
		catch (SQLException e)
		{
			throw new InvalidProtocolException();
		}
		catch (ReadWriteException e)
		{
			throw  new InvalidProtocolException();
		}
		
		String tmpBcd = palette[0].getBcData();
		if(StringUtil.isBlank(tmpBcd))
		{
			tmpBcd = "";
		}
		String bcd = operateMessage(tmpBcd, BC_DATA) ;
		
		return (bcd) ;

	}

	//#CM28943
	/**
	 * Retrieves control information out of <code>CarryInformation</code>.
	 * @return control information
	 */
	private String getControlInfo()
	{
		String tmpBcd = wCarryInfo.getControlInfo();
		if(StringUtil.isBlank(tmpBcd))
		{
			tmpBcd = "";
		}
		String ci = operateMessage(tmpBcd, CONTROL_INFORMATION) ;
		
		return (ci) ;
	}

	//#CM28944
	/**
	 * Retrieves <code>Connection</code> with <code>DataBase</code>.
	 * @return  <code>Connection</code> from the <code>DataBase</code>
	 * @throws SQLException : Notifies if database error occurrs .
     */
	private Connection getConnectionForMakeStation() throws SQLException
	{
		if(wConn == null)
		{
			wConn = AsrsParam.getConnection() ;
		}

		return(wConn);
	}
}
//#CM28945
//end of class
