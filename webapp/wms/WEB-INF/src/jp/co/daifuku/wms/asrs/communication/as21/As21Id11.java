// $Id: As21Id11.java,v 1.2 2006/10/26 01:40:59 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29016
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29017
/**
 * Composes "alternative location command ID=11" according to communication protocol AS21.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:40:59 $
 * @author  $Author: suresh $
 */
public class As21Id11 extends SendIdMessage
{
	//#CM29018
	// Class fields --------------------------------------------------

	//#CM29019
	/**
	 * Fieled of command, directing new location due to location duplicate
	 */
	public static final String DUP_NEW_LOCATION = "01";
	
	//#CM29020
	/**
	 * Field of command, sending out to the next station due to location duplicate
	 */
	public static final String DUP_PAID = "02";
	
	//#CM29021
	/**
	 * Field of command, no alternative location for location duplicate
	 */
	public static final String DUP_NO_SUBSHELF = "03";
	
	//#CM29022
	/**
	 * Field of command, directing new location after empty retrieval
	 */
	public static final String EMPTY_NEW_LOCATION = "11";
	
	//#CM29023
	/**
	 * Field of command, data to be canceled (after empty retrieval)
	 */
	public static final String EMPTY_DATA_CANCEL = "12";

	//#CM29024
	/**
	 * Field of command, directing dew location (load size mismatch).
	 */
	public static final String DIM_NEW_LOCATION = "21";
	
	//#CM29025
	/**
	 * Field of command, (transfer out to the station due to the mismatch of load size
	 */
	public static final String DIM_PAID = "22";
	
	//#CM29026
	/**
	 * Field of command (no alternative location, load size mismatch)
	 */
	public static final String DIM_NO_SUBSHELF = "23";
	
	//#CM29027
	/**
	 * Length of the request classification 
	 */
	protected static final int REQUEST_CLASSIFICATION = 2;
	
	//#CM29028
	/**
	 * Length of MC Key
	 */
	protected static final int CARRYKEY = 8;
	
	//#CM29029
	/**
	 * Length of Location number
	 */
	protected static final int LOCATION_NO = 12;
	
	//#CM29030
	/**
	 * Length of Station number
	 */
	protected static final int STATION_NUMBER = 4;
	
	//#CM29031
	/**
	 * Height of load
	 */
	protected static final int DIMENSION_INFORMATION = 2;
	
	//#CM29032
	/**
	 * Length of BC Data
	 */
	protected static final int BC_DATA = 30;
	
	//#CM29033
	/**
	 * Length of work numbers
	 */
	protected static final int WORK_NO = 8;
	
	//#CM29034
	/**
	 * Length of control information
	 */
	protected static final int CONTROL_INFORMATION = 30;
	
	//#CM29035
	/**
	 * Length of message data
	 */
	protected final int LEN_TOTAL = REQUEST_CLASSIFICATION
								+ CARRYKEY
								+ LOCATION_NO
								+ STATION_NUMBER
								+ DIMENSION_INFORMATION
								+ BC_DATA
								+ WORK_NO
								+ CONTROL_INFORMATION ;
	//#CM29036
	// Class variables -----------------------------------------------
	//#CM29037
	/**
	 * Variable that preserves classification of instruction
	 */
	private String wCommand;

	//#CM29038
	/**
	 * Variable that preserves MC Key
	 */
	private String wMcKey;

	//#CM29039
	/**
	 * Variable that preserves location No.
	 */
	private String wLocationNo;

	//#CM29040
	/**
	 * Variable that preserves station No.
	 */
	private String wStationNo;

	//#CM29041
	/**
	 * Variable that preserves load size
	 */ 
	private String wDimInfo;

	//#CM29042
	/**
	 * Variable that preserves BC Data
	 */
	private String wBcData;

	//#CM29043
	/**
	 * Variable that preserves work number
	 */
	private String wWorkNumber;

	//#CM29044
	/**
	 * Variable that preserves control information
	 */
	private String wControlInfo;
	
	//#CM29045
	// Class method --------------------------------------------------
	//#CM29046
	/**
	 * Returns the version of this class.
	 * @return Version adn the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:40:59 $");
	}

	//#CM29047
	// Constructors --------------------------------------------------
	//#CM29048
	/**
	 * Default constructor
	 */
	public As21Id11 ()
	{
		super();
	}
	
	//#CM29049
	// Public methods ------------------------------------------------
	//#CM29050
	/**
	 * Creates communication message of alternative location command. <BR>
	 * <p><code>
	 * Contents of the message should include the following;<br>
	 * <table>
	 * <tr><td>Request claffification</td><td>2 byte</td></tr>
	 * <tr><td>MC Key</td><td>8 byte</td></tr>
	 * <tr><td>Location No.</td><td>12 byte</td></tr>
	 * <tr><td>Station No.</td><td>4 byte</td></tr>
	 * <tr><td>Load size</td><td>2 byte</td></tr>
	 * <tr><td>BC Data</td><td>30 byte</td></tr>
	 * <tr><td>Work number</td><td>8 byte</td></tr>
	 * <tr><td>Control information</td><td>30 byte</td></tr>
	 * </table></code></p>
	 * @return    Communication message directing the changes of receiving station
	 * @throws InvalidProtocolException  : Notifies if improper information from protocole aspt.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM29051
		// Text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM29052
		//-------------------------------------------------
		//#CM29053
		// Attention! Order of the contents must be observed!
		//#CM29054
		//-------------------------------------------------
		//#CM29055
		// Request claffification
		mbuf.append(wCommand) ;
		//#CM29056
		// MC Key
		mbuf.append(wMcKey) ;
		//#CM29057
		// Location No.
		mbuf.append(wLocationNo) ;
		//#CM29058
		// Station No.
		mbuf.append(wStationNo) ;
		//#CM29059
		// Load size
		mbuf.append(wDimInfo) ;
		//#CM29060
		// BC Data
		mbuf.append(wBcData) ;
		//#CM29061
		// work number
		mbuf.append(wWorkNumber);
		//#CM29062
		// control information
		mbuf.append(wControlInfo);

		//#CM29063
		//-------------------------------------------------
		//#CM29064
		// Sets as sending message buffer.
		//#CM29065
		//-------------------------------------------------
		//#CM29066
		// id
		setID("11") ;
		//#CM29067
		// id segment
		setIDClass("00") ;
		//#CM29068
		// time sent
		setSendDate() ;
		//#CM29069
		// time sent to AGC
		setAGCSendDate("000000") ;
		//#CM29070
		// text contents
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL+OFF_CONTENT)) ;
	}

	//#CM29071
	// Package methods -----------------------------------------------

	//#CM29072
	// Protected methods ---------------------------------------------

	//#CM29073
	// Private methods -----------------------------------------------

	//#CM29074
	/**
	 * Sets classification of request.
	 * @param command classification
	 * @param <code>command</code> command classification 01: directing new Location(location duplicate)<BR>
	 *                                                     02:transfer out to another Station(location duplicate)<BR>
	 *                                                     03:No alternative location (location duplicate)<BR>
	 *                                                     11:directing New Location (empty retrieval)<BR>
	 *                                                     12:directing Data Cancel (empty retrieval)<BR>
	 *                                                     21:New Location directed (load size mismatch)<BR>
	 *                                                     22:transfer out to Station (load size mismatch)<BR>
	 *                                                     23:no alternative location (load size mismatch)
	 * @throws InvalidProtocolException : Reports if entered command classification is invalid.
	 */                                  
	public void setRequestClass(String command) throws InvalidProtocolException
	{
		wCommand = command;
		if(wCommand == DUP_NEW_LOCATION)
		{
			//#CM29075
			// directing to new location (location duplicate)
		}
		else if(wCommand == DUP_PAID)
		{
			//#CM29076
			// transfer out to the next Station (location duplicate)
		}
		else if(wCommand == DUP_NO_SUBSHELF)
		{
			//#CM29077
			// no alternative location (location duplicate )
		}
		else if(wCommand == EMPTY_NEW_LOCATION)
		{
			//#CM29078
			// directing New Location (after empty retrieval made)
		}
		else if(wCommand == EMPTY_DATA_CANCEL)
		{
			//#CM29079
			// Data Cancel (at empty retrival)
		}
		else if(wCommand == DIM_NEW_LOCATION)
		{
			//#CM29080
			// directing New Location (load size mismatch)
		}
		else if(wCommand == DIM_PAID)
		{
			//#CM29081
			// Transfer out to another sation (load sizet mismatch)
		}
		else if(wCommand == DIM_NO_SUBSHELF)
		{
			//#CM29082
			// No alternative location (load size mismatch)
		}
		else
		{
			throw new InvalidProtocolException("\n" + "was exception. request classificaiton = " + wCommand) ;
		}
	}
	
	//#CM29083
	/**
	 * Sets MC Key.
	 * @param <code>mckey</code> MC Key
	 * @throws InvalidProtocolException : Reports if MC Key is not the allowable length.
	 */
	public void setMcKey(String mckey) throws InvalidProtocolException
	{
		wMcKey = mckey;
		//#CM29084
		// Checks the length of string
		if(wMcKey.length() != CARRYKEY)
		{
			throw new InvalidProtocolException("\n" + "MC-KEY = " + CARRYKEY + "--->" + wMcKey) ;
		}
	}
	
	//#CM29085
	/**
	 * Sets location number
	 * @param locationno location number(valid when directing new location)
	 * @throws InvalidProtocolException : Reports if location number is not the allowable length.
	 */
	public void setLocationNo(String locationno) throws InvalidProtocolException
	{
		wLocationNo = locationno;
		//#CM29086
		// Checks the length of string
		if(wLocationNo.length() != LOCATION_NO)
		{
			throw new InvalidProtocolException("\n" + "Location No. = " + LOCATION_NO + "--->" + wLocationNo) ;
		}
		else if(wCommand == DUP_NEW_LOCATION || wCommand == EMPTY_NEW_LOCATION || wCommand == DIM_NEW_LOCATION)
		{
			//#CM29087
			// Valid when directing the new Location.
		}
		else
		{
			//#CM29088
			// Otherwise, fill out with 0(30H).
			wLocationNo = "000000000000";
		}
	}
	
	//#CM29089
	/**
	 * Sets station number.
	 * @param <code>stationno</code>  station number(valid whebe directing new station)
	 * @throws InvalidProtocolException : Reports if station number is not the allowable length.
	 */
	public void setStationNo(String stationno) throws InvalidProtocolException
	{
		wStationNo = stationno;
		//#CM29090
		// checks the length of the string
		if(wStationNo.length() != STATION_NUMBER)
		{
			throw new InvalidProtocolException("\n" + "Station No.  = " + STATION_NUMBER + "--->" + wStationNo) ;
		}
		else if(wCommand == DUP_PAID || wCommand == DIM_PAID)
		{
			//#CM29091
			// valid when directing new satation
		}
		else
		{
			//#CM29092
			// otherwise, fil out with 0(30H).
			wStationNo = "0000";
		}
	}
	
	//#CM29093
	/**
	 * Sets load size.
	 * @param <code>diminfo</code> load size
	 * @throws InvalidProtocolException : Reports if load size is not the allowable length.
	 */
	public void setDimensionInfo(String diminfo) throws InvalidProtocolException
	{
		wDimInfo = diminfo;
		//#CM29094
		// Check the string length.
		if(wDimInfo.length() != DIMENSION_INFORMATION)
		{
			throw new InvalidProtocolException("\n"+"load height = " + DIMENSION_INFORMATION + "--->" + wDimInfo) ;
		}
		else if(wCommand == DIM_NEW_LOCATION)
		{
			//#CM29095
			// Valid only if the instruction classification is 21 (New Location- load size mismatch)
		}
		else{
			//#CM29096
			// If invalid, fill out with 0.
			wDimInfo = "00";
		}
	}
	
	//#CM29097
	/**
	 * Setes bar code information
	 * @param <code>bcdata</code> bar code information
	 * @throws InvalidProtocolException : Reports if bar code information is not the allowable length.
	 */
	public void setBcData(String bcdata) throws InvalidProtocolException
	{
		wBcData = bcdata;
		//#CM29098
		// checks length of the string
		if(wBcData.length() != BC_DATA)
		{
			throw new InvalidProtocolException("\n"+"bar code data = " + BC_DATA + "--->" + wBcData) ;
		}
	}
	
	//#CM29099
	/**
	 * Sets work number.
	 * @param <code>worknumber</code> work number
	 * @throws InvalidProtocolException : Reports if work number is not the allowable length.
     */
	public void setWorkNumber(String worknumber) throws InvalidProtocolException
	{
		wWorkNumber = worknumber;
		//#CM29100
		// checks the length of string
		if(wWorkNumber.length() != WORK_NO)
		{
			throw new InvalidProtocolException("\n"+"work number = " + WORK_NO + "--->" + wWorkNumber);
		}
	}
	
	//#CM29101
	/**
	 * Sets control information.
	 * @param <code>controlinfo</code> control information
	 * @throws InvalidProtocolException : Reports if control information is not the allowable length.
	 */
	public void setControlInfo(String controlinfo) throws InvalidProtocolException
	{
		wControlInfo = controlinfo;
		//#CM29102
		// Checks length of string 
		if(wControlInfo.length() != CONTROL_INFORMATION)
		{
			throw new InvalidProtocolException("\n"+"donctrol data = " + CONTROL_INFORMATION + "--->" + wControlInfo) ;
		}
	}
}
//#CM29103
//end of class
