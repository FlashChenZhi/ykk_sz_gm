// $Id: As21Id64.java,v 1.2 2006/10/26 01:16:59 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30191
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM30192
/**
 * Processes the communication message "PickUpCompletionReport" ID=64 according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:16:59 $
 * @author  $Author: suresh $
 */
public class As21Id64 extends ReceiveIdMessage
{
	//#CM30193
	// Class fields --------------------------------------------------
	//#CM30194
	/**
	 *Length of the communication message ID64 (excluding STX, SEQ-No, BCC and ETX)
	 */
	public static final int LEN_ID64 = 16 + 22 ;

	//#CM30195
	/**
	 *Defines the offset of station No. of ID64
	 */
	private static final int OFF_ID64_STATION = 0 ;
	//#CM30196
	/**
	 *Length of Station No. of ID64 (byte)
	 */
	private static final int LEN_ID64_STATION = 4 ;

	//#CM30197
	/**
	 *Defines the offset of number of carrying information data reported by ID64.
	 */
	private static final int OFF_ID64_JDATAK = OFF_ID64_STATION + LEN_ID64_STATION ;
	//#CM30198
	/**
	 *Length of the number of carrying information data reported by ID64 (byte)
	 */
	private static final int LEN_ID64_JDATAK = 2 ;

	//#CM30199
	/**
	 *Defines the offset of 1st information from carrying data reported by ID64
	 */
	private static final int OFF_ID64_1ST = OFF_ID64_JDATAK + LEN_ID64_JDATAK ;
	//#CM30200
	/**
	 *IDefines the offset of 2nd information from carrying data reported by ID64
	 */
	private static final int OFF_ID64_2ND = 8 ;

	//#CM30201
	/**
	 *Defines the offset of MC Key of ID64
	 */
	private static final int OFF_ID64_MCKEY = OFF_ID64_JDATAK + LEN_ID64_JDATAK ;
	//#CM30202
	/**
	 *Length of MC Key of ID64 (byte)
	 */
	private static final int LEN_ID64_MCKEY = 8 ;

	//#CM30203
	// Class variables -----------------------------------------------
	//#CM30204
	/**
	 * Variable for the preservation of communicaiton messages
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID64] ;

	//#CM30205
	/**
	 * Variable for the preservation of the number of carrying data reported
	 */
	private int wCountOfData = 0 ;

	//#CM30206
	// Class method --------------------------------------------------
	//#CM30207
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:16:59 $") ;
	}

	//#CM30208
	// Constructors --------------------------------------------------
	//#CM30209
	/**
	 * Sets the communication message received from AGC; then initializes this class.
	 * @param <code>as21Id64</code>  Communication message of PickUpCompletionReport
	 */
	public As21Id64(byte[] as21Id64)
	{
		super() ;
		setReceiveMessage(as21Id64) ;
	}

	//#CM30210
	// Public methods ------------------------------------------------
	//#CM30211
	/**
	 * Acquires the sending station No. from the communication message PickUpCompletionReport.
	 * @return    	sending station No.
	 */
	public String getStationNo ()
	{
		String stationNo = getContent().substring(OFF_ID64_STATION, OFF_ID64_STATION + LEN_ID64_STATION);
		return (stationNo);
	}

	//#CM30212
	/**
	 * Acquires the number of carrying data reported from the communication message PickUpCompletionReport.
	 * PickUpCompletionReport returns up to 2 carrying data reported.
	 * @return    the number of carrying data reported
	 * @throws InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
	 */
	public int getJdatak() throws InvalidProtocolException
	{
		int rclass;

		String jdatak = getContent().substring(OFF_ID64_JDATAK, OFF_ID64_JDATAK + LEN_ID64_JDATAK);
		try
		{
			rclass = Integer.parseInt(jdatak);
		}
		catch( Exception e)
		{
			throw (new InvalidProtocolException("Invalid error")) ;
		}

		if(rclass != wCountOfData)
		{
			throw (new InvalidProtocolException("resarch error"));
		}

		return (rclass);
	}

	//#CM30213
	/**
	 * Acquires carrying data reported (MC Key) from PickUpCompletionReport.
	 * @return    carrying data reported (MC Key)
	 * @throws InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
	 */
	public String[] getVmcKey () throws InvalidProtocolException
	{
		return (getCompInfo(OFF_ID64_MCKEY, LEN_ID64_MCKEY)) ;
	}

	//#CM30214
	/**
	 * Acquires carrying data reported (MC Key) from PickUpCompletionReport.
	 * @param offset :Defined offset number for communication message:PickUpCompletionReport
	 * @param len Length of MC Key in definition (byte)
	 * @return carrying data reported (MC Key)
	 * @throws InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
	 */
	protected String[] getCompInfo(int offset, int len) throws InvalidProtocolException
	{
		String[] rst = new String[wCountOfData] ;
		try
		{
			for(int i=0; i < wCountOfData; i++)
			{
				int toff = offset + (OFF_ID64_2ND * i) ;
				rst[i] = getContent().substring(toff, toff + len) ;
			}

		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Encode error")) ;
		}
		return (rst) ;
	}


	//#CM30215
	// Package methods -----------------------------------------------

	//#CM30216
	// Protected methods ---------------------------------------------
	//#CM30217
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * Also, acquires the number of carrying data reported. Sets 0 if MC Key is invalid.
	 * @param rmsg communication message:PickUpCompletionReport
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		int offset ;
		String mckey ;

		//#CM30218
		// Sets data to communication message buffer
		for (int i = 0 ; (i < rmsg.length) && (i < LEN_ID64) ; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;

		//#CM30219
		// counting carrying data reported
		offset = OFF_ID64_MCKEY ;
		mckey = getContent().substring(offset, offset + LEN_ID64_MCKEY) ;
		if (mckey.equals(NULL_MC_KEY))
		{
			wCountOfData = 0 ;
		}
		else
		{
			wCountOfData = 1 ;
			//#CM30220
			//Examine to see if the received data is the same length as the total length of ID64 data portion and BCC.
			if (rmsg.length == (LEN_ID64 + Bcc.BCC_LENGTH))
			{
				offset = OFF_ID64_2ND + OFF_ID64_MCKEY ;
				mckey = getContent().substring(offset, offset + LEN_ID64_MCKEY) ;
				if (!mckey.equals(NULL_MC_KEY))
				{
					wCountOfData++ ;
				}
			}
		}
	}
	//#CM30221
	// Private methods -----------------------------------------------

}
//#CM30222
// end of class
