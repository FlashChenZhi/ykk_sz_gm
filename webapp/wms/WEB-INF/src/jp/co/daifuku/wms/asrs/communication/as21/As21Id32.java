// $Id: As21Id32.java,v 1.2 2006/10/26 01:35:08 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29641
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29642
/**
 * Processes the communication message "Reponse to the Retrieval Comand", ID=32 acording to AS21 communication protocol.
* 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:35:08 $
 * @author  $Author: suresh $
 */
public class As21Id32 extends ReceiveIdMessage
{
	//#CM29643
	// Class fields --------------------------------------------------
	//#CM29644
	/**
	 * Length of communication message ID32 (excluding STX, SEQ-No, BCC and ETX).
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>sent time to AGC:</td><td>6 byte</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte * 2</td></tr>
	 * <tr><td>response classification:</td><td>2 byte * 2</td></tr>
	 * </table>
	 * </p>
	 */
	public static final int LEN_ID32 = 36 ;

	//#CM29645
	/**
	 * Defines the offset of MC Key.
	 */
	private static final int OFF_ID32_MCKEY = 0 ;

	//#CM29646
	/**
	 * Length of MC Key(in byte)
	 */
	private static final int LEN_ID32_MCKEY = 8 ;

	//#CM29647
	/**
	 * Defines the offset in response classification
	 */
	private static final int OFF_ID32_CLASS = OFF_ID32_MCKEY + LEN_ID32_MCKEY ;

	//#CM29648
	/**
	 * Length of response classification (in byte)
	 */
	private static final int LEN_ID32_CLASS = 2 ;

	//#CM29649
	/**
	 * Position to start 2nd carry data
	 */
	private static final int OFF_ID32_2 = OFF_ID32_CLASS + LEN_ID32_CLASS ;

	//#CM29650
	// Class variables -----------------------------------------------
	//#CM29651
	/**
	 * Communication message buffer
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID32] ;

	//#CM29652
	/**
	 * Number of communication messages
	 */
	private int wCountOfData ;

	//#CM29653
	// Class method --------------------------------------------------
     //#CM29654
     /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:35:08 $") ;
	}

	//#CM29655
	// Constructors --------------------------------------------------
	//#CM29656
	/**
	 * Default constructor
	 */
	public As21Id32 ()
	{
		super();
	}

	//#CM29657
	/**
	 * Passes communication message received from AGC to the constructor.
	 * @param <code>as21Id32</code>  the communication message "Reponse to the Retrieval Command", ID=32 
	 */
	public As21Id32(byte[] as21Id32)
	{
		super() ;
		setReceiveMessage(as21Id32) ;
	}

	//#CM29658
	// Public methods ------------------------------------------------
	//#CM29659
	/**
	 * Acquires MC Key from the communication message "Reponse to the Retrieval Comand".
	 * Response data, up to 2 data, will return according to the message "Reponse to the Retrieval Comand".
	 * @return MC Key
	 * @throws InvalidProtocolException  : Notifies if communication message includes improper contents from the protocol aspect.
	 */
	public String[] getMcKey () throws InvalidProtocolException
	{
		return (getResponseInfo(OFF_ID32_MCKEY, LEN_ID32_MCKEY)) ;
	}

	//#CM29660
	/**
	 * Acquires response data from the message RetrievalCommandResponse.
	 * @return
	 * <table>
	 * <tr><td>0:</td><td>normal</td></tr>
	 * <tr><td>3:</td><td>nultiple set</td></tr>
	 * <tr><td>6:</td><td>off-line</td></tr>
	 * <tr><td>11:</td><td>Buffer Full</td></tr>
	 * <tr><td>99:</td><td>Data Error</td></tr>
	 * </table>
	 * @throws InvalidProtocolException : Reports if numeric value was not provided for response classification.
	 */
	public int[] getResponseData () throws InvalidProtocolException
	{
		String[] responceClassification = getResponseInfo(OFF_ID32_CLASS, LEN_ID32_CLASS);
		int[] rclass = new int[responceClassification.length];
		try
		{
			for (int i = 0 ; i < responceClassification.length ; i++)
				rclass[i] =  Integer.parseInt(responceClassification[i]) ;
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Invalid Response:" + responceClassification)) ;
		}
		return (rclass);
	}

	//#CM29661
	/**
	 * Acquires the content of the message RetrievalCommandResponse("Reponse to the Retrieval Command").
	 * @return  text content of the message RetrievalCommandResponse
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29662
	// Package methods -----------------------------------------------

	//#CM29663
	// Protected methods ---------------------------------------------

	//#CM29664
	// Private methods -----------------------------------------------
	//#CM29665
	/**
	 * Acquires data for 1 or 2 from internal buffer in accordance with the number of response.
	 * @param offset  oFfsetting the 1st data. Offsetting of 2nd data will automatically be calculated.
	 * @param len  Length of acquiring data (in byte)
	 * @return contents of the communication message
	 * @throws InvalidProtocolException  : Notifies if communication message includes improper contents from the protocol aspect.
	 */
	protected String[] getResponseInfo(int offset, int len) throws InvalidProtocolException
	{
		String[] rst = new String[wCountOfData] ;
		try
		{
			for (int i=0; i < wCountOfData; i++)
			{
				int toff = offset + (OFF_ID32_2 * i) ;
				rst[i] = getContent().substring(toff, toff + len);  
			}
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Encode error")) ;
		}
		return (rst) ;
	}

	//#CM29666
	/**
	 * Sets communication message received from AGC to the internal buffer
	 * @param rmsg message received from AGC 
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29667
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID32; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;

		//#CM29668
		// counting response data
		int offset = OFF_ID32_MCKEY ;
		String mckey = getContent().substring(offset, offset + LEN_ID32_MCKEY) ;
		if (mckey.equals(NULL_MC_KEY))
		{
			wCountOfData = 0 ;
		}
		else
		{
			wCountOfData = 1 ;

			offset = OFF_ID32_2 + OFF_ID32_MCKEY ;
			mckey = getContent().substring(offset, offset + LEN_ID32_MCKEY) ;
			if (!mckey.equals(NULL_MC_KEY))
			{
				wCountOfData++ ;
			}
		}
	}
}
//#CM29669
//end of class

