// $Id: As21Id33.java,v 1.2 2006/10/26 01:35:08 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29670
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29671
/**
 * Processes communication message "Work Completion Report", ID=33 according to the AS21 communication protocol.
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
public class As21Id33 extends ReceiveIdMessage
{
	//#CM29672
	// Class fields --------------------------------------------------
	//#CM29673
	/**
	 * Length of communication message ID33 (excluding STX, SEQ-No, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>repeating the following for 2 cycle</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte</td></tr>
	 * <tr><td>transport section:</td><td>1 byte</td></tr>
	 * <tr><td>type:</td><td>1 byte</td></tr>
	 * <tr><td>completion classificaiton:</td><td>1 byte</td></tr>
	 * <tr><td>sending station number:</td><td>4 byte</td></tr>
	 * <tr><td>receiving station number:</td><td>4 byte</td></tr>
	 * <tr><td>location number:</td><td>12 byte</td></tr>
	 * <tr><td>location number at the location to location move:</td><td>12 byte</td></tr>
	 * <tr><td>load size:</td><td>2 byte</td></tr>
	 * <tr><td>BC Data:</td><td>30 byte</td></tr>
	 * <tr><td>work number:</td><td>8 byte</td></tr>
	 * <tr><td>control information:</td><td>30 byte</td></tr>
	 * </table>
	 * </p>
	 */

	//#CM29674
	/**
	 * Length of communication message ID33 (excluding STX, SEQ-No, BCC and ETX)
	 */
	static final int LEN_ID33 = 226 + 16 ;

	//#CM29675
	/**
	 * Defines offset of Retrieval data of with ID33.
	 */
	private static final int OFF_ID33_1ST = 0 ;

	//#CM29676
	/**
	 * Length of Retrieval data with ID33(in byte)
	 */
	private static final int OFF_ID33_2ND = 113 ;

	//#CM29677
	/**
	 * Defines offset of MC Key with ID33.
	 */
	private static final int OFF_ID33_MCKEY = 0 ;

	//#CM29678
	/**
	 * Length of MC Key with ID33 (in byte)
	 */
	private static final int LEN_ID33_MCKEY = 8 ;

	//#CM29679
	/**
	 * Defines offset of transport section with ID33
	 */
	private static final int OFF_ID33_TRANS_CLASS = OFF_ID33_MCKEY + LEN_ID33_MCKEY ;

	//#CM29680
	/**
	 * Length of transport section with ID33 (in byte)
	 */
	private static final int LEN_ID33_TRANS_CLASS = 1 ;

	//#CM29681
	/**
	 * Defines offset of type with ID33
	 */
	private static final int OFF_ID33_CLASS = OFF_ID33_TRANS_CLASS + LEN_ID33_TRANS_CLASS ;

	//#CM29682
	/**
	 * Length of type with ID33 (in byte)
	 */
	private static final int LEN_ID33_CLASS = 1 ;

	//#CM29683
	/**
	 * Defines offset of completion (classification) with ID33
	 */
	private static final int OFF_ID33_COMP_CLASS = OFF_ID33_CLASS + LEN_ID33_CLASS ;

	//#CM29684
	/**
	 * Length of completion (classification) with ID33 (in byte)
	 */
	private static final int LEN_ID33_COMP_CLASS = 1 ;

	//#CM29685
	/**
	 * Defines offset of sending station No. with ID33
	 */
	private static final int OFF_ID33_FROM_ST = OFF_ID33_COMP_CLASS + LEN_ID33_COMP_CLASS ;

	//#CM29686
	/**
	 * Length of sending station No. with ID33 (in byte)
	 */
	private static final int LEN_ID33_FROM_ST = 4 ;

	//#CM29687
	/**
	 * Defines offset of receiving station No. with ID33.
	 */
	private static final int OFF_ID33_TO_ST = OFF_ID33_FROM_ST + LEN_ID33_FROM_ST ;

	//#CM29688
	/**
	 * Length of receiving station No. ID33 (in byte)
	 */
	private static final int LEN_ID33_TO_ST = 4 ;

	//#CM29689
	/**
	 * Defines offset of LocationNo. with ID33
	 */
	private static final int OFF_ID33_LOCATION = OFF_ID33_TO_ST + LEN_ID33_TO_ST ;

	//#CM29690
	/**
	 * Length of LocationNo. with ID33(in byte)
	 */
	private static final int LEN_ID33_LOCATION = 12 ;

	//#CM29691
	/**
	 * Defines offset of LocationNo. when moving from location to location with ID33
	 */
	private static final int OFF_ID33_MOV_LOCATION = OFF_ID33_LOCATION + LEN_ID33_LOCATION ;

	//#CM29692
	/**
	 * Length of LocationNo. when moving from location to location with ID33 (in byte)
	 */
	private static final int LEN_ID33_MOV_LOCATION = 12 ;

	//#CM29693
	/**
	 * Defines offset of load size with ID33
	 */
	private static final int OFF_ID33_DIMENSION = OFF_ID33_MOV_LOCATION + LEN_ID33_MOV_LOCATION ;

	//#CM29694
	/**
	 * Length of load size with ID33 (in byte)
	 */
	private static final int LEN_ID33_DIMENSION = 2 ;

	//#CM29695
	/**
	 * Defines offset of BC data with ID33
	 */
	private static final int OFF_ID33_BCDATA = OFF_ID33_DIMENSION + LEN_ID33_DIMENSION ;

	//#CM29696
	/**
	 * Length of BC data with ID33 (in byte)
	 */
	private static final int LEN_ID33_BCDATA = 30 ;

	//#CM29697
	/**
	 * Defines offset of work No. with ID33
	 */
	private static final int OFF_ID33_WORKNO = OFF_ID33_BCDATA + LEN_ID33_BCDATA ;

	//#CM29698
	/**
	 * Length of work No. with ID33 (in byte)
	 */
	private static final int LEN_ID33_WORKNO = 8 ;

	//#CM29699
	/**
	 * Defines offset of control data with ID33
	 */
	private static final int OFF_ID33_CONTROL_INFO = OFF_ID33_WORKNO + LEN_ID33_WORKNO ;

	//#CM29700
	/**
	 * Length of control data with ID33 (in byte)
	 */
	private static final int LEN_ID33_CONTROL_INFO = 30 ;

	//#CM29701
	// Class variables -----------------------------------------------
	//#CM29702
	/**
	 * Variable which preserves the communiation message.
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID33] ;

	//#CM29703
	/**
	 * Variable for purpose of preserving the number of retrieval data
	 */
	private int wCountOfData = 0 ;

	//#CM29704
	// Class method --------------------------------------------------
    //#CM29705
    /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:35:08 $") ;
	}

	//#CM29706
	// Constructors --------------------------------------------------
	//#CM29707
	/**
	 * Default constructor
	 */
	public As21Id33()
	{
		super() ;
	}

	//#CM29708
	/**
	 * Sets the communication message received from AGC and initialize this class.
	 * @param <code>as21Id33</code>  communication message "Work Completion Report"
	 */
	public As21Id33(byte[] as21Id33)
	{
		super() ;
		setReceiveMessage(as21Id33) ;
	}

	//#CM29709
	// Public methods ------------------------------------------------
	//#CM29710
	/**
	 * Acquires the MC Key from the communication message "Work Completion Report"
	 * @return    array of MC Key. If there are 2 completion data, then elements should be 2 accordingly.
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public String[] getMcKey ()  throws InvalidProtocolException
	{
		return (getCompInfo(OFF_ID33_MCKEY, LEN_ID33_MCKEY)) ;
	}

	//#CM29711
	/**
	 * Acquires transport section from the communication message "Work Completion Report".
	 * @return    	transport section<BR>
	 * 				1:storage<BR>
	 *				2:retrieval<BR>
	 *				4:location to location move (output)<BR>
	 *				5:location to location move (intput)
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public int[] getTransportationClassification () throws InvalidProtocolException
	{
		return (getIntCompInfo(OFF_ID33_TRANS_CLASS, LEN_ID33_TRANS_CLASS)) ;
	}

	//#CM29712
	/**
	 * Acquires type from the communication message "Work Completion Report"
	 * Is valid only if the transport section specifies retrieval.
	 * @return    	type<BR>
	 * 				1:Urgent retrieval<BR>
	 *				2:Planned retrieval<BR>
	 *				9:Confirmation of empty location
	 * @throws  InvalidProtocolException  : Notifies if the value used is not following the communication message protocol.
	 */
	public int[] getCategory ()  throws InvalidProtocolException
	{
		return (getIntCompInfo(OFF_ID33_CLASS, LEN_ID33_CLASS)) ;
	}

	//#CM29713
	/**
	 * Acquires the completion (classification ) from the communication message "Work Completion Report"
	 * @return    	cpompletion (classification)<BR>
	 * 				0:normal completion<BR>
	 *				1:multiple set<BR>
	 *				2:empty retrieval<BR>
	 *				3:load size mismatch<BR>
	 *				7:empty location complete (when type is confirming the empty locations)<BR>
	 *				8:result location complete (when type is confirming the empty locations)<BR>
	 *				9:Cancel
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public int[] getCompletionClassification ()  throws InvalidProtocolException
	{
		return (getIntCompInfo(OFF_ID33_COMP_CLASS, LEN_ID33_COMP_CLASS)) ;
	}

	//#CM29714
	/**
	 * Acquires sending station No. from the communication message "Work Completion Report".
	 * @return    	sending station No.
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public String[] getSendingStationNo ()  throws InvalidProtocolException
	{
		return (getCompInfo(OFF_ID33_FROM_ST, LEN_ID33_FROM_ST)) ;
	}

	//#CM29715
	/**
	 * Acquires receiving station No. from the communication message "Work Completion Report".
	 * @return    	receiving stationNo.
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public String[] getReceivingStationNo ()  throws InvalidProtocolException
	{
		return (getCompInfo(OFF_ID33_TO_ST, LEN_ID33_TO_ST)) ;
	}

	//#CM29716
	/**
	 * Acquire location No. from the communication message "Work Completion Report".
	 * @return    	LocationNo.
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public String[] getLocationNo ()  throws InvalidProtocolException
	{
		return (getCompInfo(OFF_ID33_LOCATION, LEN_ID33_LOCATION)) ;
	}

	//#CM29717
	/**
	 * Acquires location No. at the location to location move from the communication message "Work Completion Report"
	 * @return    	location No. at location to location move
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public String[] getLtoLocationNo ()  throws InvalidProtocolException
	{
		return (getCompInfo(OFF_ID33_MOV_LOCATION, LEN_ID33_MOV_LOCATION)) ;
	}

	//#CM29718
	/**
	 * Acquires load size from the communication message "Work Completion Report".
	 * @return    	load size
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public int[] getDimension ()  throws InvalidProtocolException
	{
		return (getIntCompInfo(OFF_ID33_DIMENSION, LEN_ID33_DIMENSION)) ;
	}

	//#CM29719
	/**
	 * Acquires BC data from communication message "Work Completion Report".
	 * @return    	BC Data
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public String[] getBcData ()  throws InvalidProtocolException
	{
		return (getCompInfo(OFF_ID33_BCDATA, LEN_ID33_BCDATA)) ;
	}

	//#CM29720
	/**
	 * Acquires work No. from communication message "Work Completion Report".
	 * @return    	work No.
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public String[] getWorkNo ()  throws InvalidProtocolException
	{
		return (getCompInfo(OFF_ID33_WORKNO, LEN_ID33_WORKNO)) ;
	}

	//#CM29721
	/**
	 * Acquires control information from the communication message "Work Completion Report".
	 * @return    	control data
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	public String[] getControlInformation ()  throws InvalidProtocolException
	{
		return (getCompInfo(OFF_ID33_CONTROL_INFO, LEN_ID33_CONTROL_INFO)) ;
	}

	//#CM29722
	/**
	 * Acquires contents of communication message "Work Completion Report".
	 * @return  contents of communication message "Work Completion Report".
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29723
	// Package methods -----------------------------------------------

	//#CM29724
	// Protected methods ---------------------------------------------
	//#CM29725
	/**
	 * Acquires 1 or 2 data, according to the number of work completion cases, from internal buffer.
	 * @param offset  Offsetting the 1st data. OFfsetting of 2nd data will wutomatically be calculated.
	 * @param len  length of data to acquire (in byte)
	 * @return the data specified by offset acquired from communication message(String type).
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	protected String[] getCompInfo(int offset, int len) throws InvalidProtocolException
	{
		String[] rst = new String[wCountOfData] ;
		try
		{
			for (int i=0; i < wCountOfData; i++)
			{
				int toff = offset + (OFF_ID33_2ND * i) ;
				rst[i] = getContent().substring(toff, toff + len) ;
			}
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Encode error")) ;
		}
		return (rst) ;
	}

	//#CM29726
	/**
	 * Acquires 1 or 2 numeric datas from the internal buffer, according to the number of work completion data.
	 * @param offset  Offset the 1st data; for 2nd data the offsetting will be automatically calculated.
	 * @param len  Length of acquiring data (in byte)
	 * @return the data specified by offset, which was acquired from the communication message(int type).
	 * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
	 */
	protected int[] getIntCompInfo(int offset, int len) throws InvalidProtocolException
	{
		String[] wtc = getCompInfo(offset, len) ;
		int [] idt = new int[wCountOfData] ;
		try
		{
			for (int i=0; i < wCountOfData; i++)
			{
				idt[i] = Integer.parseInt(wtc[i]) ;
			}
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException()) ;
		}
		return (idt);
	}
	
	//#CM29727
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * Also counts and sets the number of retrieval data.
	 * @param rmsg the communication message "Work Completion Report"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		int offset ;
		String mckey ;

		//#CM29728
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID33; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;

		//#CM29729
		// counting retrieval data
		offset = OFF_ID33_MCKEY ;
		mckey = getContent().substring(offset, offset + LEN_ID33_MCKEY) ;
		if (mckey.equals(NULL_MC_KEY))
		{
			wCountOfData = 0 ;
		}
		else
		{
			wCountOfData = 1 ;

			offset = OFF_ID33_2ND + OFF_ID33_MCKEY ;
			mckey = getContent().substring(offset, offset + LEN_ID33_MCKEY) ;
			if (!mckey.equals(NULL_MC_KEY))
			{
				wCountOfData++ ;
			}
		}
	}

	//#CM29730
	// Private methods -----------------------------------------------

}
//#CM29731
//end of class

