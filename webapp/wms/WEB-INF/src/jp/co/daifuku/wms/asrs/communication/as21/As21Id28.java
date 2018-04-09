// $Id: As21Id28.java,v 1.2 2006/10/26 01:35:10 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29522
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29523
/**
 * Processes communication message "Response to the command to change the receiving station, ID=28"
 * according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:35:10 $
 * @author  $Author: suresh $
 */
public class As21Id28 extends ReceiveIdMessage
{
	//#CM29524
	// Class fields --------------------------------------------------
	//#CM29525
	/**
	 * Length of the communication message ID28 (excluding STX, SEQ-No, BCC and ETX).
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte</td></tr>
	 * <tr><td>instruction classification:</td><td>1 byte</td></tr>
	 * <tr><td>location No.:</td><td>12 byte</td></tr>
	 * <tr><td>station No.:</td><td>4 byte</td></tr>
	 * <tr><td>response classification:</td><td>2 byte</td></tr>
	 * </table>
	 * </p>
	 */
	public static final int LEN_ID28 = 43 ;

	//#CM29526
	/**
	 * Defines offset of MC Key.
	 */
	private static final int OFF_ID28_MCKEY = 0 ;

	//#CM29527
	/**
	 * Length of MC Key (in byte)
	 */
	private static final int LEN_ID28_MCKEY = 8 ;

	//#CM29528
	/**
	 * Defines offset of instruction classification.
	 */
	private static final int OFF_ID28_INS_CLASS = OFF_ID28_MCKEY + LEN_ID28_MCKEY ;

	//#CM29529
	/**
	 * Length of instruction classification (in buyte)
	 */
	private static final int LEN_ID28_INS_CLASS = 1 ;

	//#CM29530
	/**
	 * Defines offset of location No.
	 */
	private static final int OFF_ID28_LOCATION = OFF_ID28_INS_CLASS + LEN_ID28_INS_CLASS ;

	//#CM29531
	/**
	 * Length of location (in byte)
	 */
	private static final int LEN_ID28_LOCATION = 12 ;

	//#CM29532
	/**
	 * Defines offset of station No.
	 */
	private static final int OFF_ID28_STATION = OFF_ID28_LOCATION + LEN_ID28_LOCATION ;

	//#CM29533
	/**
     * Length of station No. (in byte)
	 */
	private static final int LEN_ID28_STATION = 4 ;

	//#CM29534
	/**
	 * Defines offset of response classification
	 */
	private static final int OFF_ID28_CLASS = OFF_ID28_STATION + LEN_ID28_STATION ;

	//#CM29535
	/**
	 * Length of response classification (in byte)
	 */
	private static final int LEN_ID28_CLASS = 2 ;

	//#CM29536
	/**
	 * Field that indicates the response classification (normaly received)
	 */
	public static final String CLASS_NORMAL_RECEIVE = "00" ;

	//#CM29537
	/**
	 * Field that indicates the response classification (no corresponding carry data is found)
	 */
	public static final String CLASS_NOT_DATA = "01" ;

	//#CM29538
	/**
	 * Field that indicates the response classification (no corresponding location)
	 */
	public static final String CLASS_NOT_LOCATION = "02" ;

	//#CM29539
	/**
	 * Field that indicates the response classification (there is no corresponding station)	
	 */
	public static final String CLASS_NOT_STATION = "03" ;

	//#CM29540
	/**
	 * Field that indicates the response classification (No carrying route to the corresponding location)
	 */
	public static final String CLASS_NOT_ROUTE_LOCATION = "04" ;

	//#CM29541
	/**
	 * Field to indicates response classification ( no route to convey load to the corresponding station)
	 */
	public static final String CLASS_NOT_ROUTE_STATION = "05" ;

	//#CM29542
	/**
	 * Field to indicates response classification (NOT able to access the corresponding location)
	 */
	public static final String CLASS_NOT_ACCESS = "06" ;

	//#CM29543
	/**
	 * Field to indicates response classification (Data Error)
	 */
	public static final String CLASS_DATA_ERROR = "99" ;

	//#CM29544
	// Class variables -----------------------------------------------
	//#CM29545
	/**
	 * communication message buffer
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID28] ;

	//#CM29546
	// Class method --------------------------------------------------
	//#CM29547
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:35:10 $") ;
	}

	//#CM29548
	// Constructors --------------------------------------------------
	//#CM29549
	/**
	 * Default constructor
	 */
	public As21Id28()
	{
		super() ;
	}

	//#CM29550
	/**
	 * Passes communication message received from AGC to the constructor.
	 * @param <code>as21Id28</code>  message "Response to the Command to change Receiving Station" ID=28
     */
	public As21Id28(byte[] as21Id28)
	{
		super() ;
		setReceiveMessage(as21Id28) ;
	}

	//#CM29551
	// Public methods ------------------------------------------------
	//#CM29552
	/**
	 * Acquires MC Key from message "Response to the Command to change Receiving Station".
	 * @return    MC Key
	 */
	public String getMcKey ()
	{
		String mcKey = getContent().substring(OFF_ID28_MCKEY, OFF_ID28_MCKEY + LEN_ID28_MCKEY) ;
		return (mcKey);
	}

	//#CM29553
	/**
	 * Acquires instruction classification from the message "Response to the Command to change Receiving Station".
	 * @return    instruction classification
	 * @throws InvalidProtocolException : Reports if numeric value was not provided for instruction classification.
	 */
	public int getInstructionClassification () throws InvalidProtocolException
	{
		int rclass = 9;
		String instructionClassification = getContent().substring(OFF_ID28_INS_CLASS, OFF_ID28_INS_CLASS + LEN_ID28_INS_CLASS) ;
		try
		{
			rclass = Integer.parseInt(instructionClassification) ;
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Invalid Response:" + instructionClassification)) ;
		}
		return (rclass);
	}

	//#CM29554
	/**
	 * Acquires location No. from the message "Response to the Command to change Receiving Station".
	 * @return    Location No.
	 */
	public String getLocationNumber()
	{
		String locationNo = getContent().substring(OFF_ID28_LOCATION, OFF_ID28_LOCATION + LEN_ID28_LOCATION) ;
		return (locationNo);
	}

	//#CM29555
	/**
	 * Acquires station No. from the message "Response to the Command to change Receiving Station".
	 * @return    Station No.
	 */
	public String getStationNumber ()
	{
		String stationNo = getContent().substring(OFF_ID28_STATION, OFF_ID28_STATION + LEN_ID28_STATION) ;
		return (stationNo);
	}

	//#CM29556
	/**
	 * Acquires the response classification from the message "Response to the Command to change Receiving Station".
	 * @return    	00:normaly received<BR>
	 *				01:No such carry data is found<BR>
	 *				02:No such location<BR>
	 *				03:No such station<BR>
	 *				04:No carry route to the selected location<BR>
	 *				05:No carry route to the selected station<BR>
	 *				06:Not able to access to the selected location<BR>
	 *				99:Data Error
	 */
	public String getResponseClassification () 
	{
		String responseClassification = getContent().substring(OFF_ID28_CLASS, OFF_ID28_CLASS + LEN_ID28_CLASS) ;
		return (responseClassification);
	}

	//#CM29557
	/**
	 * Returns string representation of the message "Response to the Command to change Receiving Station".
	 * @return string representation of the message "Response to the Command to change Receiving Station"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29558
	// Package methods -----------------------------------------------

	//#CM29559
	// Protected methods ---------------------------------------------
	//#CM29560
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg communication message received from AGC
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29561
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID28; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM29562
	// Private methods -----------------------------------------------

}
//#CM29563
//end of class

