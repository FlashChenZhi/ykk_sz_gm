// $Id: As21Id36.java,v 1.2 2006/10/26 01:35:07 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29765
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29766
/**
 * Processes communication message "TheReportOfTheNotAcceptedSystemStartUp" (report that all system start up was not accepted),
 * ID=36, according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:35:07 $
 * @author  $Author: suresh $
 */
public class As21Id36 extends ReceiveIdMessage 
{
	//#CM29767
	// Class fields --------------------------------------------------
	//#CM29768
	/**
	 * Length of the communication message for ReportOfTheNotAcceptedSystemStartUp
	 * in byte
	 */
	public static final int LEN_ID36 = 18;
	
	//#CM29769
	/**
	 * Defines offset for the reason for not being able to sart up
	 */
	private static final int OFF_ID36_NSTRES_CLASS = 0;

	//#CM29770
	/**
	 * Length of the reason for not being able to start up (in byte)
	 */
	private static final int LEN_ID36_NSTRES_CLASS = 2;
	
	//#CM29771
	/**
	 * the reason for not being able to start up (not in condition to start working)
	 */
	public static final String NOT_START_CONDITION = "01";
	
	//#CM29772
	// Class variables -----------------------------------------------
	//#CM29773
	/**
	 * Variable which preserves the communication message
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID36];

	//#CM29774
	// Class method --------------------------------------------------
	//#CM29775
	/**
	 * Returns the version of this class.
	 * return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:35:07 $");
	}
	
	//#CM29776
	// Constructors --------------------------------------------------
	//#CM29777
	/**
	 * Default constructor
	 */
	public As21Id36()
	{
		super() ;
	}

	//#CM29778
	/**
	 * Sets the communication message from AGC, then initialize this class.
	 * @param <code>as21Id36</code>  communication message for ReportOfTheNotAcceptedSystemStartUp
	 */
	public As21Id36(byte[] as21Id36)
	{
		super();
		setReceiveMessage(as21Id36);
	}

	//#CM29779
	// Public methods ------------------------------------------------
	//#CM29780
	/**
	 * Acquires the reason for not being able to start up from the message for 
	 * ReportOfTheNotAcceptedSystemStartUp.
	 * @return    reason for not being able to start up
	 */
	public String getReason()
	{
		String reason = getContent().substring(OFF_ID36_NSTRES_CLASS, LEN_ID36_NSTRES_CLASS);
		return(reason);
	}
	
	//#CM29781
	/**
	 * Acquires the contents of message for ReportOfTheNotAcceptedSystemStartUp.
	 * @return  contents of message for ReportOfTheNotAcceptedSystemStartUp
	 */
	public String toString()
	{
		return(new String(wLocalBuffer));
	}

	//#CM29782
	// Package methods -----------------------------------------------

	//#CM29783
	// Protected methods ---------------------------------------------
	//#CM29784
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg message for ReportOfTheNotAcceptedSystemStartUp
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29785
		// Sets data to communication message buffer
		for (int i = 0; i < LEN_ID36; i++)
		{
			wLocalBuffer[i] = rmsg[i];
		}
		wDataBuffer = wLocalBuffer;
	}
	
	//#CM29786
	// Private methods -----------------------------------------------
	
}
//#CM29787
//end of class

