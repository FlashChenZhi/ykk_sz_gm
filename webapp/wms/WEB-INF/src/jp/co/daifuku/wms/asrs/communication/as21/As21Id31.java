// $Id: As21Id31.java,v 1.2 2006/10/26 01:35:09 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29608
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29609
/**
 * Processes tje communication message "Response to the command: alternative location", ID-31 according to 
 * AS21 communciation protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:35:09 $
 * @author  $Author: suresh $
 */
public class As21Id31 extends ReceiveIdMessage
{
	//#CM29610
	// Class fields --------------------------------------------------
	//#CM29611
	/**
	 * Length of the communication message ID31 (excluding STX, SEQ-No, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte</td></tr>
	 * <tr><td>response classification:</td><td>2 byte</td></tr>
	 * </table>
	 * </p>
	 */
	public static final int LEN_ID31 = 26 ;
	
	//#CM29612
	/**
	 * Defines offset of MC Key.
	 */
	private static final int OFF_ID31_MCKEY = 0 ;

	//#CM29613
	/**
	 * Length of MC Key (in byte)
	 */
	private static final int LEN_ID31_MCKEY = 8 ;

	//#CM29614
	/**
 	 * Defines offset of response classification
	 */
	private static final int OFF_ID31_CLASS = OFF_ID31_MCKEY + LEN_ID31_MCKEY ;

	//#CM29615
	/**
	 * Length of response classification (in byte)
	 */
	private static final int LEN_ID31_CLASS = 2 ;

	//#CM29616
	/**
	 * Field of response classification (normaly received)
	 */
	public static final String CLASS_NORMAL_RECEIVE = "00" ;

	//#CM29617
	/**
	 * Field of response classification (no such carry data is found)
	 */
	public static final String CLASS_NOT_DATA = "01" ;

	//#CM29618
	/**
	 * Field of response classification (instruction classification error)
	 */
	public static final String CLASS_INSTRUCT_ERROR = "02" ;

	//#CM29619
	/**
	 * Field of response classification  (status error)
	 */
	public static final String CLASS_STATE_ERROR = "03" ;

	//#CM29620
	/**
	 * Field of response classification (there is no carry route to the selected location)
	 */
	public static final String CLASS_NOT_ROUTE_LOCATION = "04" ;

	//#CM29621
	/**
	 * Field of response classification (there is no carry route to the selected station)
	 */
	public static final String CLASS_NOT_ROUTE_STATION = "05" ;

	//#CM29622
	/**
	 * Field of response classification (NOT able to access to the selected location)
	 */
	public static final String CLASS_NOT_ACCESS = "06" ;

	//#CM29623
	/**
	 * Field of response classification (Data Error)
	 */
	public static final String CLASS_DATA_ERROR = "99" ;

	//#CM29624
	// Class variables -----------------------------------------------
	//#CM29625
	/**
	 * Communication message
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID31] ;

	//#CM29626
	// Class method --------------------------------------------------
	//#CM29627
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:35:09 $") ;
	}

	//#CM29628
	// Constructors --------------------------------------------------
	//#CM29629
	/**
	 * Default constructor
     */
	public As21Id31()
	{
		super() ;
	}

	//#CM29630
	/**
	 * Passes communication message received from AGC to the constructor.
	 * @param <code>as21Id31</code>  communication message "Response to the command: alternative location", ID-31
	 */
	public As21Id31(byte[] as21Id31)
	{
		super() ;
		setReceiveMessage(as21Id31) ;
	}

	//#CM29631
	// Public methods ------------------------------------------------
	//#CM29632
	/**
	 * Acquires MC Key from the communication message "Response to the command: alternative location".
	 * @return    MC Key
	 */
	public String getMcKey ()
	{
		String mcKey = getContent().substring(OFF_ID31_MCKEY, OFF_ID31_MCKEY + LEN_ID31_MCKEY) ;
		return (mcKey);
	}

	//#CM29633
	/**
	 * Acquires response classification from communication message "Response to the command: alternative location"
	 * @return    	00:normaly received<BR>
	 *				01:No such carry data is found<BR>
	 *				02:instruction classification error<BR>
	 *				03:Status error<BR>
	 *				04:there is no route to the selected location<BR>
	 *				05:there is no route to the selected station<BR>
	 *				06:NOT able to access to the selected location<BR>
	 *				99:Data Error
	 */
	public String getResponseClassification () 
	{
		String responseClassification = getContent().substring(OFF_ID31_CLASS, OFF_ID31_CLASS + LEN_ID31_CLASS);
		return (responseClassification);
	}

	//#CM29634
	/**
	 * Returns string representation of the communication message "Response to the command: alternative location"
	 * @return string representation of the communication message "Response to the command: alternative location"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29635
	// Package methods -----------------------------------------------

	//#CM29636
	// Protected methods ---------------------------------------------
	//#CM29637
	/**
	 * ets communication message received from AGC to the internal buffer.
	 * @param rmsg message received from AGC
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29638
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID31; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM29639
	// Private methods -----------------------------------------------

}
//#CM29640
//end of class

