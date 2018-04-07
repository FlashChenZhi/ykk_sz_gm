// $Id: As21Id21.java,v 1.2 2006/10/26 01:37:45 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29305
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29306
/**
 * Composes communication message "Response to work start, ID=21" according to AS21 communication protocol.
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
public class As21Id21 extends ReceiveIdMessage
{
	//#CM29307
	// Class fields --------------------------------------------------
	//#CM29308
	/**
	* Length of communication message ID21 (except STX, SEQ-NO, BCC and ETX)
	* <p>
	* <table>
	* <tr><td>ID:</td><td>2 byte</td></tr>
	* <tr><td>ID segment:</td><td>2 byte</td></tr>
	* <tr><td>MC sending time:</td><td>6 byte</td></tr>
	* <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	* <tr><td>Response classification:</td><td>2 byte</td></tr>
	* <tr><td>Error detail:</td><td>2 byte</td</tr>
	* <tr><td>System Recovery report:</td><td>1 byte</td></tr>
	* </table>
	* </p>
	*/

	//#CM29309
	/**
	 * Total byte numbers of ID21 (excluding STX, SEQ-NO, BCC and ETX)
	 */
    protected static final int LEN_ID21 = 21;

    //#CM29310
    /**
     * Defines offset as a response classification.
     */
   protected static final int OFF_ID21_RESPONSE_CLASS = 0;

    //#CM29311
    /**
     * Length of response classification (in byte)
     */
    protected static final int LEN_ID21_RESPONSE_CLASS = 2;

    //#CM29312
    /**
     * Defines offset in error detail.
     */
    protected static final int OFF_ID21_ERRDETAILS = OFF_ID21_RESPONSE_CLASS + LEN_ID21_RESPONSE_CLASS;
    //#CM29313
    /**
     * Length of Error detail (in byte)
     */
    protected static final int LEN_ID21_ERRDETAILS = 2;
    
    //#CM29314
    /** 
     * Defines offset in System Recovery report.
     */
    protected static final int OFF_ID21_SYSTEMRECOVERYREPORT = OFF_ID21_ERRDETAILS + LEN_ID21_ERRDETAILS;
    
    //#CM29315
    /**
     * Length of System Recovery report (in byte)
     */
    protected static final int LEN_ID21_SYSTEMRECOVERYREPORT = 1;
	
	//#CM29316
	/**
	 * Field of System Recovery implementation
	 */
	public static final String S_Recovery = "1" ;
	
	//#CM29317
	/**
	 * Field to indicate there is no System Recovery report
	 */
	public static final String SYS_Recovery = "0" ;
	
	//#CM29318
	/**
	 * Field of normal response classifications.
	 */
	 public static final String NORMAL = "00";
	 
	 //#CM29319
	 /**
	 * Field for error in AGC status of response classification
	 */
	 public static final String AGC_CONDITION_ERR = "03"; 
	 
	 //#CM29320
	 /**
	 * Field to indicate data error in response classification
	 */
	 public static final String DATA_ERR = "99";
	 
	//#CM29321
	// Class variables -----------------------------------------------
	//#CM29322
	/**
	 * Communication message buffer
	 */
    private byte[] wLocalBuffer = new byte[LEN_ID21];
	//#CM29323
	// Class method --------------------------------------------------
	//#CM29324
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:37:45 $");
    }
	//#CM29325
	// Constructors --------------------------------------------------
	//#CM29326
	/**
	 * Default constructor
	 */
	public As21Id21 ()
	{
		super();
	}
	//#CM29327
	/**
	 * Passes the communication message received from AGC to the constructor.
	 * @param <code>as21Id21</code>  communication message for Response to work start ID=21
	 */
	public As21Id21(byte[] as21Id21)
	{
		super();
		setReceiveMessage(as21Id21);
	}

	//#CM29328
	// Public methods ------------------------------------------------
	//#CM29329
	/**
	 * Acquires response classification from communication message "Response to work start".
	 * @return		00:Normal<BR>
	 *				03:AGC abnormal<BR>
	 *				99:data error
	 */
	 public String getResponseClassification ()
	 {
		String responseClassification = DATA_ERR ;
		responseClassification = getContent().substring(OFF_ID21_RESPONSE_CLASS, OFF_ID21_RESPONSE_CLASS + LEN_ID21_RESPONSE_CLASS) ;
		return (responseClassification);
	}

	//#CM29330
	/**
	 * Acquires error detail from the communication message "Response to work start"
	 * @return    AGC# of abnormal state
	 */
	 public String getErrorDetails  ()
	 {
		String errorDetails = "00";
		if(getResponseClassification().equals(AGC_CONDITION_ERR))
		{
			errorDetails = getContent().substring(OFF_ID21_ERRDETAILS, OFF_ID21_ERRDETAILS + LEN_ID21_ERRDETAILS) ;
		}
		return (errorDetails);
	}

	//#CM29331
	/**
	 * Acquires System Recovery report from the communication message "Response to work start".
	 * @return		True:No report<BR>
	 *				False:System Recovery iimplementation (carry data deleted)
	 */
	public boolean getSystemRecoveryReport ()
	{
		String systemRecoveryReport =getContent().substring(OFF_ID21_SYSTEMRECOVERYREPORT, OFF_ID21_SYSTEMRECOVERYREPORT + LEN_ID21_SYSTEMRECOVERYREPORT);
		return (systemRecoveryReport.equals(S_Recovery));
	}

	//#CM29332
	/**
	 * Acquires the content of communication message "Response to work start".
	 * @return  Content of text : communication message "Response to work start"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}


	//#CM29333
	// Package methods -----------------------------------------------

	//#CM29334
	// Protected methods ---------------------------------------------
	//#CM29335
	/**
	 * Sets the communication message received from AGC to the internal buffer.
	 * @param rmsg communication message received from AGC
	 */
    protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29336
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID21; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM29337
	// Private methods -----------------------------------------------

}
//#CM29338
//end of class

