// $Id: As21Id35.java,v 1.2 2006/10/26 01:35:08 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29732
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29733
/**
 * Processes the communication message "TransportDataDeletionReport", ID=35 according to AS21 communicaiton protocol.
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
public class As21Id35 extends ReceiveIdMessage
{
	//#CM29734
	// Class fields --------------------------------------------------
	//#CM29735
	/**
	 * Length of communication message ID35 (excluding STX, SEQ-No, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>factor of deletion:</td><td>1 byte</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte</td></tr>
	 * <tr><td>receiving station No.:</td><td>4 byte</td></tr>
	 * <tr><td>control data:</td><td>30 byte</td></tr>
	 * </table>
	 * </p>
	 */

	//#CM29736
	/**
	 * Length of communication message ID35 (excluding STX, SEQ-No, BCC and ETX)
	 */
	public static final int LEN_ID35 = 59 ;
	
	//#CM29737
	/**
	 * Defines offset in factor of deletion with ID35
	 */
	private static final int OFF_ID35_CASE_OF_DELETION = 0 ;

	//#CM29738
	/**
	 * Length of deletion factor with ID35 (in byte)
	 */
	private static final int LEN_ID35_CASE_OF_DELETION = 1 ;
	
	//#CM29739
	/**
	 * Defines offset of MC Key with ID35
	 */
	private static final int OFF_ID35_MCKEY = OFF_ID35_CASE_OF_DELETION + LEN_ID35_CASE_OF_DELETION ;

	//#CM29740
	/**
	 * Length of MC Key with ID35 (in byte)
	 */
	private static final int LEN_ID35_MCKEY = 8 ;
	
	//#CM29741
	/**
	 * Defines offset of receiving station with ID35
	 */
	private static final int OFF_ID35_TO_STATION_NO = OFF_ID35_MCKEY + LEN_ID35_MCKEY ;

	//#CM29742
	/**
	 * Length of receiving station No. with ID35 (in byte)
	 */
	private static final int LEN_ID35_TO_STATION_NO = 4 ;
	
	//#CM29743
	/**
	 * Defines offset of control data with D35
	 */
	private static final int OFF_ID35_CONTROL_INFO = OFF_ID35_TO_STATION_NO + LEN_ID35_TO_STATION_NO ;

	//#CM29744
	/**
	 * Length of control data with ID35 (in byte)
	 */
	private static final int LEN_ID35_CONTROL_INFO = 30 ;

	//#CM29745
	/**
	 * Facto of deletion ( deleted by the deleting opereration) 
	 */
	public static final String C_PRESENCE = "1" ;

	//#CM29746
	// Class variables -----------------------------------------------
	//#CM29747
	/**
	 * Variables that preserves the communication message
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID35] ;

	//#CM29748
	// Class method --------------------------------------------------
	//#CM29749
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Data: 2001/05/11 02:33:53 $");
	}
	
	//#CM29750
	// Constructors --------------------------------------------------
	//#CM29751
	/**
	 * Default constructor
	 */
	public As21Id35()
	{
		super() ;
	}

	//#CM29752
	/**
	 * Sets the communication messages received from the AGC, thne intializes this class.
	 * @param <code>as21Id35</code>  the communication message "TransportDataDeletionReport"
	 */
	public As21Id35(byte[] as21Id35)
	{	
		super() ;
		setReceiveMessage(as21Id35) ;
	}

	//#CM29753
	// Public methods ------------------------------------------------
	//#CM29754
	/**
	 * Acquires the MC Key from the the communication message "TransportDataDeletionReport"
	 * @return    MC Key
	 */
	public String getMcKey ()
	{
		String mcKey = getContent().substring(OFF_ID35_MCKEY, OFF_ID35_MCKEY + LEN_ID35_MCKEY) ;
		return (mcKey);
		
	}

	//#CM29755
	/**
	 * Acquires the factor of deletion from the communication message "TransportDataDeletionReport"
	 * @return		factor of deletion<BR>
	 * 		    	(True): deleted in Operation<BR>
	 *				(Fauls): deleted by MV Down 
	 */
	public boolean getTheCaseOfDeletion ()
	{
		String wp = getContent().substring(OFF_ID35_CASE_OF_DELETION, OFF_ID35_CASE_OF_DELETION + LEN_ID35_CASE_OF_DELETION) ;
		return (wp.equals(C_PRESENCE)) ;
	}

	//#CM29756
	/**
	 * Acquires receiving station No. from the communication message "TransportDataDeletionReport"
	 * @return    	Receiving station No.
	 */
	public String getReceivingStationNo ()
	{
		String receivingStationNo = getContent().substring(OFF_ID35_TO_STATION_NO, OFF_ID35_TO_STATION_NO + LEN_ID35_TO_STATION_NO) ;
		return (receivingStationNo);
	}

	//#CM29757
	/**
	 * Acquires the control data from the communication message "TransportDataDeletionReport"
	 * Sets MC Key which returned by "getMcKey", then checks up to 2 data.
	 * @return    	control data
	 */
	public String getControlInformation ()
	{
		String controlInformation = getContent().substring(OFF_ID35_CONTROL_INFO, OFF_ID35_CONTROL_INFO + LEN_ID35_CONTROL_INFO) ;
		return (controlInformation);
		
	}
	
	//#CM29758
	/**
	 * Acquires the contents of the communication message "TransportDataDeletionReport".
	 * @return  contents of the communication message "TransportDataDeletionReport"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29759
	// Package methods -----------------------------------------------

	//#CM29760
	// Protected methods ---------------------------------------------

	//#CM29761
	// Private methods -----------------------------------------------
	//#CM29762
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg the communication message "TransportDataDeletionReport"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29763
		// Sets data to communication message buffer
		for (int i = 0; i < LEN_ID35; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}
}
//#CM29764
//end of class

