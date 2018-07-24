// $Id: As21Id04.java,v 1.2 2006/10/26 01:41:01 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM28815
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;

//#CM28816
/**
 * Composes communication message "Request for transport data cancelation ID=04" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:41:01 $
 * @author  $Author: suresh $
 */
public class As21Id04 extends SendIdMessage
{
	//#CM28817
	// Class fields --------------------------------------------------
	//#CM28818
	/**
	 * Length of MC Key
	 */
	protected static final int LEN_CARRYKEY 			=  8 ;

	//#CM28819
	/**
	 * Length of Station number
	 */
	protected static final int LEN_STATIONNUMBER		=  4 ;

	//#CM28820
	/**
	 * Length of Location number
	 */
	protected static final int LEN_LOCATIONNO			= 12 ;

	//#CM28821
	/**
	 * Length of the communication message
	 */
	protected final int LEN_TOTAL = OFF_CONTENT
								+ LEN_CARRYKEY
								+ (LEN_STATIONNUMBER * 2) // From and To
								+ LEN_LOCATIONNO ;

	//#CM28822
	// Class variables -----------------------------------------------
	//#CM28823
	/**
	 * Variable which preserves <code>CarryInformation</code> having the transport data
	 */
	private CarryInformation wCarryInfo ;
	
	//#CM28824
	/**
	 * Variable which preserves <code>Connection</code>, so that station instance can be acquired (for 
	 * use of MakeStation).
	 */
	private Connection	wConn = null;

	//#CM28825
	// Class method --------------------------------------------------
	//#CM28826
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:41:01 $") ;
	}

	//#CM28827
	// Constructors --------------------------------------------------
	//#CM28828
	/**
	 * Default constructor
	 */
	public As21Id04()
	{
		super() ;
	}

	//#CM28829
	/**
	 * By specifying the instance of <code>CarryInformation</code> which has the tranport information
	 * to cancel, generates hte instance of this class.
	 * @param  ci  <code>CarryInformation</code> preserving the transport information
	 */
	public As21Id04(CarryInformation ci)
	{
		super() ;
		setCarryInformation(ci) ;
	}

	//#CM28830
	// Public methods ------------------------------------------------
	//#CM28831
	/**
	 * Creates the communication message requesting the cancelation of tramsport data.
	 * <p></p> required for transport data to be canceled
	 * <ul>
	 * <li>MC Key
	 * <li>Sending station number
	 * <li>Receiving station number
	 * <li>Location No.
	 * </ul>
	 * <p> acquires the information from the instance of <code>CarryInformation</code> given in constructor.
	 * </p>
	 * @return    communication message requesting the cancelation of Transport Data
	 * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM28832
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM28833
		//-------------------------------------------------
		//#CM28834
		// Attention! Order of the contents must be observed!
		//#CM28835
		//-------------------------------------------------
		//#CM28836
		// MC Key
		mbuf.append(getMCKey()) ;
		//#CM28837
		// Sending station number (included in CarryInformation)
		mbuf.append(getFromStationNumber()) ;
		//#CM28838
		// Receiving station number (included in CarryInformation)
		mbuf.append(getDestStationNumber()) ;
		//#CM28839
		// Location No.
		mbuf.append(getDestLocationNumber()) ;

		//#CM28840
		//-------------------------------------------------
		//#CM28841
		// Sets as sending message buffer.
		//#CM28842
		//-------------------------------------------------
		//#CM28843
		// id
		setID("04") ;
		//#CM28844
		// id segment
		setIDClass("00") ;
		//#CM28845
		// time sent
		setSendDate() ;
		//#CM28846
		// time sent to AGC
		setAGCSendDate("000000") ;
		//#CM28847
		// contents of text
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL)) ;
	}

	//#CM28848
	// Package methods -----------------------------------------------

	//#CM28849
	// Protected methods ---------------------------------------------

	//#CM28850
	// Private methods -----------------------------------------------
	//#CM28851
	/**
	 * Take out tje MC Key out of <code>CarryInformation</code>.
	 * @return		MC Key
	 * @throws InvalidProtocolException : Reports if MC Key exceeds the allowable length.
	 */ 
	private String getMCKey() throws InvalidProtocolException
	{
		StringBuffer stbuff = new StringBuffer( LEN_CARRYKEY );
		//#CM28852
		// MC Key (included in CarryInformation)
		String carryKey = wCarryInfo.getCarryKey();
		if( carryKey.length() > LEN_CARRYKEY )
		{
			throw new InvalidProtocolException("carryKey = " + LEN_CARRYKEY + "--->" + carryKey) ;
		}
		else
		{
			stbuff.replace(0, LEN_CARRYKEY,  "00000000");
			stbuff.replace(LEN_CARRYKEY - carryKey.length(), LEN_CARRYKEY,  carryKey);
		}
		return (stbuff.toString()) ;
	}
	//#CM28853
	/**
	 * Sending station number to be taken out of <code>CarryInformation</code>.
	 * @return    Sending station number
	 * @throws InvalidProtocolException : Reports if transport section is invalid or if station exceeds the allowable length.
	 */
	private String getFromStationNumber() throws InvalidProtocolException
	{
		String stnum = null;
		//#CM28854
		// Sets sending station based on the transport section.
		switch (wCarryInfo.getCarryKind())
		{
			//#CM28855
			// Acquires sending information of CarryInfomation for storage and direct traveling.
			case CarryInformation.CARRYKIND_STORAGE:
			case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
				stnum = wCarryInfo.getSourceStationNumber() ;
				break;
				
			//#CM28856
			// Value is fixed 9000 for retrieval and location to location move
			case CarryInformation.CARRYKIND_RETRIEVAL:
			case CarryInformation.CARRYKIND_RACK_TO_RACK:
				stnum = "9000";
				break;
				
			default:
				throw new InvalidProtocolException("carryKind invalid = " + wCarryInfo.getCarryKind()) ;
		}
		
		if (stnum.length() != LEN_STATIONNUMBER)
		{
			throw new InvalidProtocolException("FromStationNumber = " + LEN_STATIONNUMBER + "--->" + stnum) ;
		}
		return (stnum) ;
	}

	//#CM28857
	/**
	 * Takes receiving station number out of <code>CarryInformation</code>.
	 * @return    receicing station number
	 * @throws InvalidProtocolException : Reports if transport section is invalid or if station exceeds the allowable length.
	 */
	private String getDestStationNumber() throws InvalidProtocolException
	{
		String stnum = null;
		//#CM28858
		// Sets receiving station according to the transport section.
		switch (wCarryInfo.getCarryKind())
		{
			//#CM28859
			// Value is fixed 9000 for storage and location to location move
			case CarryInformation.CARRYKIND_STORAGE:
			case CarryInformation.CARRYKIND_RACK_TO_RACK:
				stnum = "9000";
				break;
				
			//#CM28860
			// Acquires receiving station from CarryInfomation for storage and direct traveling.
			case CarryInformation.CARRYKIND_RETRIEVAL:
			case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
				stnum = wCarryInfo.getDestStationNumber() ;
				break;
				
			default:
				throw new InvalidProtocolException("carryKind invalid = " + wCarryInfo.getCarryKind()) ;
		}
		
		if (stnum.length() != LEN_STATIONNUMBER)
		{
			throw new InvalidProtocolException("DestStationNumber = " + LEN_STATIONNUMBER + "--->" + stnum) ;
		}
		return (stnum) ;
	}

	//#CM28861
	/**
	 * Gain location number of receiving station from <code>CarryInformation</code>.
	 * @return    location number of receiving station
	 * @throws InvalidProtocolException : Reports if transport section is invalid or if location exceeds the allowable length.
	 */
	private String getDestLocationNumber() throws InvalidProtocolException
	{
		
		String locationNo = null ;
		
		try
		{
			PaletteSearchKey pSKey = new PaletteSearchKey();
			PaletteHandler pHandle = new PaletteHandler(getConnectionForMakeStation());
		
			pSKey.setPaletteId(wCarryInfo.getPaletteId());

			Palette[] palette = (Palette[])pHandle.find(pSKey);


			//#CM28862
			// Sets location number of receiving station according to the transport section.
			switch (wCarryInfo.getCarryKind())
			{
				case CarryInformation.CARRYKIND_STORAGE:
					locationNo = wCarryInfo.getDestStationNumber() ;
					break;
					
				case CarryInformation.CARRYKIND_RETRIEVAL:
					locationNo = palette[0].getCurrentStationNumber() ;				
					break;
					
				case CarryInformation.CARRYKIND_RACK_TO_RACK:
					locationNo = palette[0].getCurrentStationNumber() ;
					break;
					
				case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
					byte[] wk = new byte[LEN_LOCATIONNO] ;
					for (int i=0; i < wk.length; i++)
					{
						wk[i] = '0' ;
					}
					locationNo = new String(wk) ;
					break;
					
				default:
					throw new InvalidProtocolException("carryKind invalid = " + wCarryInfo.getCarryKind()) ;
			}
			
			if (locationNo.length() != LEN_LOCATIONNO)
			{
				throw new InvalidProtocolException("DestLocationNumber = " + LEN_LOCATIONNO + "--->" + locationNo) ;
			}
		}
		catch (ReadWriteException e)
		{
			throw new InvalidProtocolException();
		}
		catch (SQLException e)
		{
			throw new InvalidProtocolException();
		}
		return (locationNo) ;
	}

	//#CM28863
	/**
	 * Sets carry information <code>CarryInformation</code>.
	 * @param carryinfo carruy information
	 */
	private void setCarryInformation(CarryInformation carryinfo)
	{
		wCarryInfo = carryinfo ;
	}
	
	//#CM28864
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
//#CM28865
//end of class
