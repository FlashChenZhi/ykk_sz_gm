// $Id: As21Id12.java,v 1.2 2006/10/26 01:40:59 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29104
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;

import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
//#CM29105
/**
 * Composes message for "Retrieval Command ID=12" according to AS21 communication protocol.
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
public class As21Id12 extends SendIdMessage
{
	//#CM29106
	// Class fields --------------------------------------------------
	//#CM29107
	/**
	 * Field of retrieval (carry segment)
	 */
	public static final String C_RETRIEVAL				= "2" ;

	//#CM29108
	/**
	 * Field of location to location move (carry segment)
	 */
	public static final String C_MOVE		= "5" ;

	//#CM29109
	/**
	 * Field of retrieval type (urgent retrieval)
	 */
	public static final String RETRIEVAL_CLASS_EMG		= "1" ;

	//#CM29110
	/**
	 * Field of retrieval type (planned retrieval)
	 */
	public static final String RETRIEVAL_CLASS_NORMAL		= "2" ;

	//#CM29111
	/**
	 * Field of retrieval type (confirmation for the empty location)
	 */
	public static final String RETRIEVAL_CLASS_LOC_CONFIRM	= "9" ;

	//#CM29112
	/**
	 * Field of re-storing (no restorage)
	 */
	public static final String RETRIEVAL_NO_RETURN	= "0" ;

	//#CM29113
	/**
	 * Field of re-storing (re-storing to the same location)
	 */
	public static final String RETRIEVAL_RETURN	= "1" ;

	//#CM29114
	/**
	 * Field of retrieval instruction in detail (confirmation of stocks)
	 */
	public static final String RETRIEVAL_DETAIL_CONFIRM	= "0" ;

	//#CM29115
	/**
	 * Field of retrieval instruction in detail (unit retrieval)
	 */
	public static final String RETRIEVAL_DETAIL_UNIT	= "1" ;

	//#CM29116
	/**
	 * Field of retrieval instruction in detail (pick retrieval)
	 */
	public static final String RETRIEVAL_DETAIL_PICK	= "2" ;

	//#CM29117
	/**
	 * Field of retrieval instruction in detail (adding storing)
	 */
	public static final String RETRIEVAL_DETAIL_ADDIN	= "3" ;

	//#CM29118
	/**
	 * Length of MC Key
	 */
	private static final int LEN_CARRYKEY					=  8;

	//#CM29119
	/**
	 * Length of transport classification
	 */
	private static final int LEN_TRANSPORT_CLASSIFICATION	=  1;

	//#CM29120
	/**
	 * Length of type
	 */
	private static final int LEN_RETRIEVAL_CLASS		=  1;

	//#CM29121
	/**
	 * Length of re-storing flag
	 */
	private static final int LEN_RETURN_FLAG		=  1;

	//#CM29122
	/**
	 * Length of retrieval detailed instruction 
	 */
	private static final int LEN_RETRIEVAL_DETAIL					=  1;

	//#CM29123
	/**
	 * Length of group number
	 */
	private static final int LEN_GROUP_NO					=  3;

	//#CM29124
	/**
	 * Length of Station number
	 */
	private static final int LEN_STATION_NO				=  4;

	//#CM29125
	/**
	 * Length of Location number
	 */
	private static final int LEN_LOCATION_NO					= 12;

	//#CM29126
	/**
	 * Length of load size
	 */
	private static final int LEN_DIMENSION_INFORMATION		=  2;

	//#CM29127
	/**
	 * Length of Bar Code Data
	 */
	private static final int LEN_BC_DATA						= 30;

	//#CM29128
	/**
	 * Length of work number
	 */
	private static final int LEN_WORK_NO						=  8;

	//#CM29129
	/**
	 * Length of control information
	 */
	private static final int LEN_CONTROL_INFORMATION			= 30;

	//#CM29130
	/**
	 * Length of each message for retrieval instruction 
	 */
	private static final int LEN_PIECE = LEN_CARRYKEY
								+ LEN_TRANSPORT_CLASSIFICATION
								+ LEN_RETRIEVAL_CLASS
								+ LEN_RETURN_FLAG
								+ LEN_RETRIEVAL_DETAIL
								+ LEN_GROUP_NO
								+ (LEN_STATION_NO * 2) // From and To
								+ (LEN_LOCATION_NO * 2) // From and To
								+ LEN_DIMENSION_INFORMATION
								+ LEN_BC_DATA
								+ LEN_WORK_NO
								+ LEN_CONTROL_INFORMATION ;

	//#CM29131
	/**
	 * Number of information available to set
	 */
	private static final int CNT_OF_DATA = 2 ;

	//#CM29132
	// Class variables -----------------------------------------------
	//#CM29133
	/**
	 * Variable which preserves <code>CarryInformation</code> containing carry information
	 */
	private CarryInformation[] wCarryInfo ;

	//#CM29134
	/**
	 * Variable which preserves <code>Connection</code>, for purpose of getting station instance ( for MakeStation use)
	 */
	private Connection	wConn = null;

	//#CM29135
	// Class method --------------------------------------------------
     //#CM29136
     /**
	 * Returns version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:40:59 $") ;
	}
	//#CM29137
	// Constructors --------------------------------------------------
	//#CM29138
	/**
	 * Default constructor
	 */
	public As21Id12 ()
	{
		super();
	}
	//#CM29139
	/**
	 * Generates instance of this class by specifying the instance <code>CarryInformation</code>,
	 * which owns information to carry.
	 * @param  ci  <code>CarryInformation</code> which owns carry information
	 */
	public As21Id12(CarryInformation ci[])
	{
		super() ;
		wCarryInfo = ci ;
	}
	//#CM29140
	// Public methods ------------------------------------------------
	//#CM29141
	/**
	 * Creates message of retrieval instruction.
	 * Necessary information to create the retrieval instruction can be acquired from the instance of 
	 * CarryInformation.
	 * <p><code>
	 * Contents of the message should include the following;<br>
	 * <table>
	 * <tr><td>Mckey</td><td>8 byte</td></tr>
	 * <tr><td>Carry segment</td><td>1 byte</td></tr>
	 * <tr><td>Type</td><td>1 byte</td></tr>
	 * <tr><td>Re-storing flag</td><td>1 byte</td></tr>
	 * <tr><td>Retrieval detailed instruction</td><td>1 byte</td></tr>
	 * <tr><td>Group number</td><td>3 byte</td></tr>
	 * <tr><td>Sending station</td><td>4 byte</td></tr>
	 * <tr><td>Receiving station</td><td>4 byte</td></tr>
	 * <tr><td>origination location number</td><td>12 byte</td></tr>
	 * <tr><td>destination location number</td><td>12 byte</td></tr>
	 * <tr><td>Load size</td><td>2 byte</td></tr>
	 * <tr><td>BC Data</td><td>30 byte</td></tr>
	 * <tr><td>Work number</td><td>8 byte</td></tr>
	 * <tr><td>Control information</td><td>30 byte</td></tr>
	 * </table></code></p>
	 * @return    Retrieval instruction message
	 * @throws InvalidProtocolException  : Notifies if communication message includes improper contents in protocol aspect.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		String rstr ;
		byte[] mBuffB = new byte[LEN_PIECE * CNT_OF_DATA] ;
		for (int i=0; i < mBuffB.length; i++)
		{
			mBuffB[i] = '0' ;
		}

		int bidx = 0 ;
		StringWriter sw = new StringWriter();
    	PrintWriter  pw = new PrintWriter(sw);

		try
		{
			for (int i=0; i < wCarryInfo.length; i++)
			{
				if ( wCarryInfo[i] != null )
				{ 
					//#CM29142
					// MC key
					setByteArray(mBuffB, bidx, getMCKey(i), LEN_CARRYKEY) ;
					bidx += LEN_CARRYKEY ;
					//#CM29143
					// transport section
					setByteArray(mBuffB, bidx, getTransClass(i), LEN_TRANSPORT_CLASSIFICATION) ;
					bidx += LEN_TRANSPORT_CLASSIFICATION ;
					//#CM29144
					// type
					setByteArray(mBuffB, bidx, getRetrievalClass(i), LEN_RETRIEVAL_CLASS) ;
					bidx += LEN_RETRIEVAL_CLASS ;
					//#CM29145
					// re-storing flag
					setByteArray(mBuffB, bidx, getReturnFlag(i), LEN_RETURN_FLAG) ;
					bidx += LEN_RETURN_FLAG ;
					//#CM29146
					// retrieval indication in detail
					setByteArray(mBuffB, bidx, getRetrievalDetail(i), LEN_RETRIEVAL_DETAIL) ;
					bidx += LEN_RETRIEVAL_DETAIL ;
					//#CM29147
					// group number
					setByteArray(mBuffB, bidx, getGroupNumber(i), LEN_GROUP_NO) ;
					bidx += LEN_GROUP_NO ;
					//#CM29148
					// Sending station
					setByteArray(mBuffB, bidx, getFromStationNumber(i), LEN_STATION_NO) ;
					bidx += LEN_STATION_NO ;
					//#CM29149
					// Receiving station
					setByteArray(mBuffB, bidx, getDestStationNumber(i), LEN_STATION_NO) ;
					bidx += LEN_STATION_NO ;
					//#CM29150
					// originating location number
					setByteArray(mBuffB, bidx, getFromLocationNumber(i), LEN_LOCATION_NO) ;
					bidx += LEN_LOCATION_NO ;
					//#CM29151
					// destined location number
					setByteArray(mBuffB, bidx, getDestLocationNumber(i), LEN_LOCATION_NO) ;
					bidx += LEN_LOCATION_NO ;
					//#CM29152
					// load size
					setByteArray(mBuffB, bidx, getDimensionInfo(i), LEN_DIMENSION_INFORMATION) ;
					bidx += LEN_DIMENSION_INFORMATION ;
					//#CM29153
					// BC Data
					setByteArray(mBuffB, bidx, getBcData(i), LEN_BC_DATA) ;
					bidx += LEN_BC_DATA ;
					//#CM29154
					// work number
					setByteArray(mBuffB, bidx, getWorkNumber(i), LEN_WORK_NO) ;
					bidx += LEN_WORK_NO ;
					//#CM29155
					// control information
					setByteArray(mBuffB, bidx, getControlInfo(i), LEN_CONTROL_INFORMATION) ;
					bidx += LEN_CONTROL_INFORMATION ;
				}
			}
			//#CM29156
			//-------------------------------------------------
			//#CM29157
			// Setting for sending message buffer
			//#CM29158
			//-------------------------------------------------
			//#CM29159
			// id
			setID("12") ;
			//#CM29160
			// id segment
			setIDClass("00") ;
			//#CM29161
			// time sent
			setSendDate() ;
			//#CM29162
			// time sent to AGC
			setAGCSendDate() ;
			rstr = new String(mBuffB, CommunicationAgc.CODE) ;
			//#CM29163
			// contents of text
			setContent(rstr.toString()) ;
			return (getFromBuffer(0, LEN_PIECE*2 + OFF_CONTENT)) ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			//#CM29164
			//Records errors in the log file
			//#CM29165
			// 6026041=conveyance instruction, picking instruction startup failed
			e.printStackTrace(pw);
			Object[] tobj = new Object[1];
			tobj[0] = stcomment + sw.toString();
			RmiMsgLogClient.write(6026041, LogMessage.F_ERROR, "As21Id12", tobj);
			throw (new InvalidProtocolException(e.toString())) ;
		}
	}

	//#CM29166
	// Package methods -----------------------------------------------

	//#CM29167
	// Protected methods ---------------------------------------------

	//#CM29168
	// Private methods -----------------------------------------------
	//#CM29169
	/**
	 * Takes MC Key from <code>CarryInformation</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return MC Key
	 * @throws InvalidProtocolException : Reports if MC Key exceeds the allowable length.
	 * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getMCKey(int i) throws InvalidProtocolException, UnsupportedEncodingException
	{
		//#CM29170
		// MC Key (included in CarryInformation)
		byte[] carryKey = wCarryInfo[i].getCarryKey().getBytes(CommunicationAgc.CODE) ;

		if (carryKey.length != LEN_CARRYKEY)
		{
			throw new InvalidProtocolException("Invalid carryKey:" + new String(carryKey, CommunicationAgc.CODE)) ;
		}
		return (carryKey) ;
	}

	//#CM29171
	/**
	 * Takes transport section from <code>CarryInformation</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return transport section
	 * @throws InvalidProtocolException : Reports if entered transport section is invalid.
	 * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getTransClass(int i) throws InvalidProtocolException, UnsupportedEncodingException
	{
		byte[] tc ;
		//#CM29172
		// Transport section (included in CarryInformation)
		int inoutKind = wCarryInfo[i].getCarryKind() ;
		switch (inoutKind)
		{
			//#CM29173
			// Retrieval
			case CarryInformation.CARRYKIND_RETRIEVAL :
				tc = C_RETRIEVAL.getBytes(CommunicationAgc.CODE) ;
				break ;

			//#CM29174
			// Location to location move
			case CarryInformation.CARRYKIND_RACK_TO_RACK :
				tc = C_MOVE.getBytes(CommunicationAgc.CODE) ;
				break ;

			//#CM29175
			// Invalid transport section
			default:
				throw new InvalidProtocolException("Invalid Transport Class:" + Integer.toString(inoutKind)) ;
		}
		
		return (tc) ;
	}

	//#CM29176
	/**
	 * Takes type from <code>CarryInformation</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return type 
	 * @throws InvalidProtocolException : Reports if entered type is invalid.
	 * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getRetrievalClass(int i) throws InvalidProtocolException, UnsupportedEncodingException
	{
		byte[] rc ;
		//#CM29177
		// Type (included in CarryInformation)
		int rclass = wCarryInfo[i].getPriority() ;
		switch (rclass)
		{
			//#CM29178
			// Urgent
			case CarryInformation.PRIORITY_EMERGENCY :
				rc = RETRIEVAL_CLASS_EMG.getBytes(CommunicationAgc.CODE) ;
				break ;
				
	 		//#CM29179
	 		// Planned retrieval
			case CarryInformation.PRIORITY_NORMAL :
				rc = RETRIEVAL_CLASS_NORMAL.getBytes(CommunicationAgc.CODE) ;
				break ;
				
			//#CM29180
			// Confirmation of empty location
	 		case CarryInformation.PRIORITY_CHECK_EMPTY :
				rc = RETRIEVAL_CLASS_LOC_CONFIRM.getBytes(CommunicationAgc.CODE) ;
				break ;

			//#CM29181
			// Invalid transport section
			default:
				throw new InvalidProtocolException("Invalid Retrieval Class:" + Integer.toString(rclass)) ;
		}
		
		return (rc) ;
	}

	//#CM29182
	/**
	 * Takes re-storing flag from <code>CarryInformation</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return Re-storing flag
	 * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getReturnFlag(int i) throws UnsupportedEncodingException
	{
		//#CM29183
		// re-storing flag (included in CarryInformation)
		if(wCarryInfo[i].getReStoringFlag() == CarryInformation.RESTORING_SAME_LOC)
		{
			return ("1".getBytes(CommunicationAgc.CODE)) ;
		}
		else
		{
			return ("0".getBytes(CommunicationAgc.CODE)) ;
		}
	}
	
	//#CM29184
	/**
	 * Takes retrieval instruction in detail from <code>CarryInformation</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return retrieval instruction in detail
	 * @throws InvalidProtocolException : Reports if entered retrieval instruction in detail is invalid.
	 * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getRetrievalDetail(int i) throws InvalidProtocolException, UnsupportedEncodingException
	{
		byte[] rd ;
		//#CM29185
		// retrieval instruction in detail (included in CarryInformation)
		int rdetail = wCarryInfo[i].getRetrievalDetail() ;
		switch (rdetail)
		{
			//#CM29186
			// Confirmation for inventory
			case CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK :
				rd = RETRIEVAL_DETAIL_CONFIRM.getBytes(CommunicationAgc.CODE) ;
				break ;

			//#CM29187
			// Unit retrieval
			case CarryInformation.RETRIEVALDETAIL_UNIT :
				rd = RETRIEVAL_DETAIL_UNIT.getBytes(CommunicationAgc.CODE) ;
				break ;

			//#CM29188
			// Pick retrieval
			case CarryInformation.RETRIEVALDETAIL_PICKING :
				rd = RETRIEVAL_DETAIL_PICK.getBytes(CommunicationAgc.CODE) ;
				break ;

			//#CM29189
			// Adding storing
			case CarryInformation.RETRIEVALDETAIL_ADD_STORING :
				rd = RETRIEVAL_DETAIL_ADDIN.getBytes(CommunicationAgc.CODE) ;
				break ;

			default:
				throw new InvalidProtocolException("Invalid Retrieval Detail:" + Integer.toString(rdetail)) ;
		}
		
		return (rd) ;
	}

	//#CM29190
	/**
	 * Takes group number from <code>CarryInformation</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return Group number 
	 * @throws InvalidProtocolException : Reports if group number exceeds the allowable value.
	 * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getGroupNumber(int i) throws InvalidProtocolException, UnsupportedEncodingException
	{
		//#CM29191
		// Group number (included in CarryInformation)
		int groupNo = wCarryInfo[i].getGroupNumber();
		if (groupNo > 999)
		{
			throw new InvalidProtocolException("group number too large") ;
		}
		DecimalFormat fmt = new DecimalFormat("000") ;
		byte[] c_groupNo = fmt.format(groupNo).getBytes(CommunicationAgc.CODE) ;
		
		return (c_groupNo) ;
	}

	//#CM29192
	/**
	 * Takes originating sending station number from <code>CarryInformation</code>.
	 * Returns parent station (warehouse) of current station (location) the pallet belongs to.
	 * @param	index of <code>CarryInformation</code>
	 * @return Sending station number
	 * @throws InvalidProtocolException : Reports if originating sending station number is not the allowable length.
	 * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getFromStationNumber(int i) throws InvalidProtocolException, UnsupportedEncodingException
	{
		//#CM29193
		// Fix 9000 as sending station for retrieval and location to location moves.
		String pst = "9000";
		byte[] ssn = pst.getBytes(CommunicationAgc.CODE) ;
		if (ssn.length != LEN_STATION_NO)
		{
			throw new InvalidProtocolException("Invalid From Station Number:" + pst) ;
		}
		
		return (ssn) ;
	}

	//#CM29194
	/**
	 * Takes receiving station number from <code>CarryInformation</code>.
	 * If the load is destined to rack, it regards as location to location move and returns parent station (warehouse).
	 * @param	index of <code>CarryInformation</code>
	 * @return Receiving station number
	 * @throws InvalidProtocolException : Reports if station can not be acquired or if receiving station number is not the allowable length.
	 * @throws UnsupportedEncodingException: Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getDestStationNumber(int i) throws InvalidProtocolException, UnsupportedEncodingException
	{
		Station dSt = null;
		String dStNum = null;
		byte[] ds ;
		String dStNumber = wCarryInfo[i].getDestStationNumber() ;

		if(dStNumber.length() != LEN_STATION_NO)
		{
			try
			{
				dSt = StationFactory.makeStation(getConnectionForMakeStation(), dStNumber);
			}
			catch(Exception e)
			{
				throw new InvalidProtocolException("makeStation error StationNumber = " + dStNumber) ;
			}


			//#CM29195
			// If the load is destined to rack, it should be a location to location move; therefore
			// the receiving station should be the warehouse.
			//#CM29196
			//2004/09/02 (Double deep correspondence)
			//#CM29197
			//If ParentStationNumber is set,�@the process will contradict with conveyance origin location or 9000 for ID04
			//#CM29198
			//set 9000 as static value
			if (dSt instanceof Shelf)
			{
				dStNum = "9000" ;
			}
			else
			{
				dStNum = dSt.getStationNumber() ;
			}
		}
		else
		{
			dStNum = dStNumber;
		}

		ds = dStNum.getBytes(CommunicationAgc.CODE) ;
		if (ds.length != LEN_STATION_NO)
		{
			throw new InvalidProtocolException("Invalid Destination Station:" + dStNum) ;
		}
		
		return (ds) ;
	}

	//#CM29199
	/**
	 * Takes origination location number from <code>CarryInformation</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return origination location number
	 * @throws InvalidProtocolException : Reports if palette can not be acquired or if origination location number is not the allowable length.
	 * @throws UnsupportedEncodingException: Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getFromLocationNumber(int i) throws InvalidProtocolException, UnsupportedEncodingException
	{
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteHandler pHandle;
		Palette[] palette = null;
		try
		{
			pHandle = new PaletteHandler(getConnectionForMakeStation());

		
			pSKey.setPaletteId(wCarryInfo[i].getPaletteId());

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
	
		
		String dStNum = null;
		String stationNumber = palette[0].getCurrentStationNumber() ;
		
		if(stationNumber.length() != LEN_LOCATION_NO)
		{
			throw new InvalidProtocolException("Invalid From Location(Station) Number:" + stationNumber) ;
		}
		else
		{
			dStNum =  stationNumber ;
		}
		
		byte[] ssn = dStNum.getBytes(CommunicationAgc.CODE) ;
		if (ssn.length != LEN_LOCATION_NO)
		{
			throw new InvalidProtocolException("Invalid From Location Number:" + dStNum) ;
		}

		return (ssn) ;

	}
	//#CM29200
	/**
	 * Takes destined location number from <code>CarryInformation</code>.
	 * Only if it is a location to location move, the actual location number should be input. Otherwise, fill out with 
	 * 000000000000.
	 * @param	index of <code>CarryInformation</code>
	 * @return destined location number
	 * @throws InvalidProtocolException : Reports if destined location number is not the allowable length.
	 * @throws UnsupportedEncodingException: Notifies if it encontered characters for which it does not support encoding.
     */
	private byte[] getDestLocationNumber(int i) throws InvalidProtocolException, UnsupportedEncodingException
	{
		String dStNum ;
		byte[] ssn ;
		String dStStno = wCarryInfo[i].getDestStationNumber() ;

		//#CM29201
		// Location to location move
		if (wCarryInfo[i].getCarryKind() == CarryInformation.CARRYKIND_RACK_TO_RACK)
		{
			dStNum = dStStno ;
		}
		else
		{
			dStNum = "000000000000" ;
		}
		ssn = dStNum.getBytes(CommunicationAgc.CODE) ;
		if (ssn.length != LEN_LOCATION_NO)
		{
			throw new InvalidProtocolException("Invalid Destination Location Number:" + dStNum) ;
		}
		
		return (ssn) ;
	}

	//#CM29202
	/**
	 * Takes load size information from <code>Palette</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return load size information
	 * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getDimensionInfo(int i) throws UnsupportedEncodingException
	{
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteHandler pHandle;
		Palette[] palette = null;
		
		try
		{
			pHandle = new PaletteHandler(getConnectionForMakeStation());

			pSKey.setPaletteId(wCarryInfo[i].getPaletteId());

			palette = (Palette[])pHandle.find(pSKey);
			
		}
		catch (SQLException e)
		{
			throw new UnsupportedEncodingException();
		} 
		catch (ReadWriteException e)
		{
			throw new UnsupportedEncodingException();
		}
		
		//#CM29203
		// Load size (included in Palette)
		DecimalFormat fmt1 = new DecimalFormat("00") ;
		String sdim = fmt1.format(palette[0].getHeight()) ;

		byte[] bdim = sdim.getBytes(CommunicationAgc.CODE) ;
		
		return (bdim) ;

	}

	//#CM29204
	/**
	 * Takes work number from <code>CarryInformation</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return work number
	 * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getWorkNumber(int i) throws UnsupportedEncodingException
	{
		//#CM29205
		// work number (included in CarryInformation)
		String tmpWn = wCarryInfo[i].getWorkNumber();
		if(StringUtil.isBlank(tmpWn))
		{
			tmpWn = "";
		}
		byte[] wnum = operateMessage(tmpWn, LEN_WORK_NO).getBytes(CommunicationAgc.CODE) ;
		
		return (wnum) ;
	}

	//#CM29206
	/**
	 * Takes bar code data from <code>Palette</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return bar code data
	 * @throws UnsupportedEncodingException: Notifies if it encontered characters for which it does not support encoding.
	 */
	private byte[] getBcData(int i) throws UnsupportedEncodingException
	{
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteHandler pHandle;
		Palette[] palette = null;
		try
		{
			pHandle = new PaletteHandler(getConnectionForMakeStation());
		
			pSKey.setPaletteId(wCarryInfo[i].getPaletteId());

			palette = (Palette[])pHandle.find(pSKey);
		}
		catch (SQLException e)
		{
			throw new UnsupportedEncodingException();
		} 
		catch (ReadWriteException e)
		{
			throw new UnsupportedEncodingException();
		}

		//#CM29207
		// bar code data (included in Palette)
		String tmpBcd = palette[0].getBcData();
		if(StringUtil.isBlank(tmpBcd))
		{
			tmpBcd = "";
		}
		byte[] bcd = operateMessage(tmpBcd, LEN_BC_DATA).getBytes(CommunicationAgc.CODE) ;
		
		return (bcd) ;		
	}

	//#CM29208
	/**
	 * Takes control information from <code>CarryInformation</code>.
	 * @param	index of <code>CarryInformation</code>
	 * @return control information
	 * @throws UnsupportedEncodingException: Notifies if it encontered characters for which it does not support encoding. 
	 */
	private byte[] getControlInfo(int i) throws UnsupportedEncodingException
	{
		//#CM29209
		// control information (included in CarryInformation)
		String cinfo = wCarryInfo[i].getControlInfo();
		if(StringUtil.isBlank(cinfo))
		{
			cinfo = "";
		}
		byte[] ci = operateMessage(cinfo, LEN_CONTROL_INFORMATION).getBytes(CommunicationAgc.CODE) ;
		
		return (ci) ;
	}

	//#CM29210
	/**
	 * Takes <code>Connection</code> with <code>DataBase</code>.
	 * @return <code>DataBase</code> with <code>Connection</code>
	 * @throws SQLException : Notifies if database error occurs.
	 */
	private Connection getConnectionForMakeStation() throws SQLException
	{
		if(wConn == null)
		{
			wConn = AsrsParam.getConnection() ;
		}

		return(wConn);
	}

	//#CM29211
	// debug method --------------------------------------------------
	//#CM29212
	/**
	 * Outputs the length of individual retrieval instruction message.
	 */
	public static void main(String[] args)
	{
		System.out.println(LEN_PIECE) ;
	}

}
//#CM29213
//end of class
