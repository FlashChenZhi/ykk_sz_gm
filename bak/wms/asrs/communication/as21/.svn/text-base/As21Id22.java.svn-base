// $Id: As21Id22.java,v 1.2 2006/10/26 01:37:45 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29339
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29340
/**
 * Processes the communicarion message "Date and Time Request, ID=22" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:37:45 $
 * @author  $Author: suresh $
 */
public class As21Id22 extends ReceiveIdMessage
{
	//#CM29341
	// Class fields --------------------------------------------------
	//#CM29342
	/**
	 * Length of communication message ID22 (excluding STX,SEQ-NO,BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>request classification:</td><td>1 byte</td></tr>
	 * <tr><td>System Recovery report:</td><td>1 byte</td></tr>
	 * </table>
	 * </p>
	 */

	//#CM29343
	/**
	 * Total byte numbers of ID22 (excluding STX,SEQ-NO,BCC and ETX)
	 */
    protected static final int LEN_ID22 = 18;

    //#CM29344
    /**
     * Defines offset in request classification.
     */
   protected static final int OFF_ID22_REQUEST_CLASS = 0;

    //#CM29345
    /**
     * Length of request classification (in byte)
     */
    protected static final int LEN_ID22_REQUEST_CLASS = 1;

    //#CM29346
    /**
     * Defines offset in System Recovery report.
     */
    protected static final int OFF_ID22_SYSTEMRECOVERYREPORT = OFF_ID22_REQUEST_CLASS + LEN_ID22_REQUEST_CLASS;

    //#CM29347
    /**
     * Length of System Recovery report (in byte)
     */
    protected static final int LEN_ID22_SYSTEMRECOVERYREPORT = 1;

	//#CM29348
	// Class variables -----------------------------------------------
	//#CM29349
	/**
	 * Communication message buffer
	 */
    protected byte[] wLocalBuffer = new byte[LEN_ID22] ;

	//#CM29350
	/**
	 * Indicates correction of time in request classification
	 */
	public static final int TIME_CORRECT = 0;

	//#CM29351
	/**
     * Indicates work start in request classification
     */
	public static final int W_START = 1;

	//#CM29352
	/**
	 * Field of System Recovery implementation
	 */
    public static final String S_R_REPORT = "1";

	//#CM29353
	/**
	 * Field to indicate there is no System Recovery report
	 */
    public static final String SYS_R_REPORT = "0";

	//#CM29354
	// Class method --------------------------------------------------
    //#CM29355
    /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:37:45 $");
    }

	//#CM29356
	// Constructors --------------------------------------------------
	//#CM29357
	/**
	 * Default constructor
	 */
	public As21Id22 ()
	{
		super();
	}

	//#CM29358
	/**
	 * Passes the communication message received from AGC to the constructor.
	 * @param <code>as21Id22</code>  communication message of Date and Time request ID=22
	 */
	public As21Id22(byte[] as21Id22)
	{
		super();
		setReceiveMessage(as21Id22);
	}

	//#CM29359
	// Public methods ------------------------------------------------
	//#CM29360
	/**
	 * Acquires request classification from the communication message of Date and Time request.
	 * @return    	0: time correction<BR>
	 *				1: work start
	 * @throws InvalidProtocolException : Reports if numeric value was not provided for request classification.
	 */
  	public int getRequestClassification () throws InvalidProtocolException
	{
		int rclass;
		String requestClassification = getContent().substring(OFF_ID22_REQUEST_CLASS, OFF_ID22_REQUEST_CLASS + LEN_ID22_REQUEST_CLASS) ;
		try
		{
			rclass =Integer.parseInt(requestClassification) ;
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Invalid Response:" + requestClassification)) ;
		}
		return (rclass );
	}

	//#CM29361
	/** 
	 * Acquires System Recovery report from the communication message of Date and Time request.
	 *				False:System Recovery implemented (carry data deleted)
	 */
	public boolean isSystemRecoveryReports ()
	{
		String sReport=getContent().substring(OFF_ID22_SYSTEMRECOVERYREPORT,OFF_ID22_SYSTEMRECOVERYREPORT + LEN_ID22_SYSTEMRECOVERYREPORT) ; 
		return (sReport.equals(S_R_REPORT));
	}

	//#CM29362
	/**
	 * Acquires the content of communication message of Date and Time request.
	 * @return text content of Date and Time request
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29363
	// Package methods -----------------------------------------------

	//#CM29364
	// Protected methods ---------------------------------------------
	//#CM29365
	/**
	 * Sets the communication message from AGC to the internal buffer.
	 * @param rmsg communication message received from AGC
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29366
		// Sets data to communication message buffer
		for (int i = 0; i < LEN_ID22; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}
	//#CM29367
	// Private methods -----------------------------------------------

}
//#CM29368
//end of class

