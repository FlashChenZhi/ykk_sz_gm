// $Id: As21Id71.java,v 1.2 2006/10/26 01:14:11 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30294
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM30295
/**
 * Processes the communication message "AccessImpossibleLocationReport" ID=71, report of locations
 * which have no availability of access, according to AS21 communication protcol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:14:11 $
 * @author  $Author: suresh $
 */
public class As21Id71 extends ReceiveIdMessage
{
	//#CM30296
	// Class fields --------------------------------------------------
	//#CM30297
	/**
	 * Length of communication message in initial setting (byte)
	 */
	public int LEN_ID71=0;
	
	//#CM30298
	/**
	 * Defines offset of classification for continuation
	 */
	private static final int OFF_ID71_CONTINU_CLASS = 0;
	//#CM30299
	/**
	 * Length of classification for continuation (byte)
	 */
	private static final int LEN_ID71_CONTINU_CLASS = 1;
	
	//#CM30300
	/**
	 * Defines offset of number of reports
	 */
	private static final int OFF_ID71_REPORTNUM_CLASS = OFF_ID71_CONTINU_CLASS + LEN_ID71_CONTINU_CLASS;
	//#CM30301
	/**
	 * Length of the number of reports (byte)
	 */
	private static final int LEN_ID71_REPORTNUM_CLASS = 2;
	
	//#CM30302
	/**
	 * Defines the offset of status
	 */
	private static final int OFF_ID71_CONDITION = OFF_ID71_REPORTNUM_CLASS + LEN_ID71_REPORTNUM_CLASS;
	//#CM30303
	/**
	 * Length of status (byte)
	 */
	private static final int LEN_ID71_CONDITION = 1;
	
	//#CM30304
	/**
	 * Defines the offset of storage classification
	 */
	private static final int OFF_ID71_STATUS_CLASS = OFF_ID71_CONDITION + LEN_ID71_CONDITION;
	//#CM30305
	/**
	 * Length of storage classification (byte)
	 */
	private static final int LEN_ID71_STATUS_CLASS = 1;
	
	//#CM30306
	/**
	 * Defines the offset of Bank No.
	 */
	private static final int OFF_ID71_BANK_NO = OFF_ID71_STATUS_CLASS + LEN_ID71_STATUS_CLASS;
	//#CM30307
	/**
	 * Length of Bank No.(byte)
	 */
	private static final int LEN_ID71_BANK_NO = 2;
	
	//#CM30308
	/**
	 * Defines the offset of Start Bay
	 */
	private static final int OFF_ID71_START_BAY = OFF_ID71_BANK_NO + LEN_ID71_BANK_NO;
	//#CM30309
	/**
	 * Length of Start Bay (byte)
	 */
	private static final int LEN_ID71_START_BAY = 3;
	
	//#CM30310
	/**
	 * Defines the offset of Start Level no.
	 */
	private static final int OFF_ID71_STLEVEL_NO = OFF_ID71_START_BAY + LEN_ID71_START_BAY;
	//#CM30311
	/**
	 * Length of Start Level no. (byte)
	 */
	private static final int LEN_ID71_STLEVEL_NO = 3;
	
	//#CM30312
	/**
	 * Defines the offset of End Bay
	 */
	private static final int OFF_ID71_END_BAY = OFF_ID71_STLEVEL_NO + LEN_ID71_STLEVEL_NO;
	//#CM30313
	/**
	 * Length ofEnd Bay (byte)
	 */
	private static final int LEN_ID71_END_BAY = 3;
	
	//#CM30314
	/**
	 * Defines the offset of End Level No.
	 */
	private static final int OFF_ID71_ENLEVEL_NO = OFF_ID71_END_BAY + LEN_ID71_END_BAY;
	//#CM30315
	/**
	 * Length of End Level No. (byte)
	 */
	private static final int LEN_ID71_ENLEVEL_NO = 3;
	
	//#CM30316
	/**
	 * Length of the state of availability of each location, per case (byte)
	 */
	private static final int ST=16;
	
	//#CM30317
	/**
	 * Classification of continuation : End and the final of the report text
	 */
	public static final String NO_CONTINU = "2";
	
	//#CM30318
	// Class variables -----------------------------------------------
	//#CM30319
	/**
	 * Variables for the preservation of communication message
	 */
	private byte[] wLocalBuffer;
	
	//#CM30320
	// Class method --------------------------------------------------
	//#CM30321
	/**
	 * Returns the version of this class.
	 * return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:14:11 $");
	}
	
	//#CM30322
	// Constructors --------------------------------------------------
	//#CM30323
	/**
	 * Operates the initialization of this class.
	 */
	public As21Id71()
	{
		super() ;
	}
	
	//#CM30324
	/**
	 * Sets the communication message received from AGC; then implements the initialization of this class.
	 * @param <code>as21Id71</code>  communication message "AccessImpossibleLocationReport"
	 */
	public As21Id71(byte[] as21Id71)
	{
		super();
		setReceiveMessage(as21Id71);
	}

	//#CM30325
	// Public methods ------------------------------------------------9
	//#CM30326
	/**
	 * Acquires the continuation classification (whether/not the message consists of 2 messages or more)
	 * based on the message "AccessImpossibleLocationReport"
	 * @return		 continuation classification<BR>
	 * 			   	 1: the message consists of 2 or more sentences<BR>
	 *				 2: the message consists of 1 sentence
	 */
	public boolean getContinuationClassification()
	{
		String continuationClassification = getContent().substring(OFF_ID71_CONTINU_CLASS, LEN_ID71_CONTINU_CLASS);
		return (continuationClassification.equals(NO_CONTINU));
	}
	
	//#CM30327
	/**
	 * Acquires the number of reports from the message "AccessImpossibleLocationReport".
	 * @return    the number of reports
	 * @throws InvalidProtocolException: Reports if numeric value was not provided for number of reports.
	 */
	public int getNumberOfReports() throws InvalidProtocolException
	{
		int numberOfReports = 0;
		String snumberOfReports = getContent().substring(OFF_ID71_REPORTNUM_CLASS, OFF_ID71_REPORTNUM_CLASS + LEN_ID71_REPORTNUM_CLASS);
		try
		{
			numberOfReports = Integer.parseInt(snumberOfReports);
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Invalid Response:" + snumberOfReports)) ;
		}
		return (numberOfReports);
	}
	
	//#CM30328
	/**
	 * Acquires the availability of the location the message "AccessImpossibleLocationReport".
	 * @param     num    index of status of access availability
	 * @return    Access availability
	 */
	public String getCondition(int num)
	{
		String sCondition = getContent().substring(OFF_ID71_CONDITION + (ST * num), OFF_ID71_CONDITION + (ST * num) + LEN_ID71_CONDITION);
		return(sCondition);
	}
	
	//#CM30329
	/**
	 * Acquires the classification of storage from the message "AccessImpossibleLocationReport".
	 * @param     num    index of status of access availability
	 * @return    classification of storage
	 */
	public String getStatusClass(int num)
	{
		String sStatusClass = getContent().substring(OFF_ID71_STATUS_CLASS + (ST * num), OFF_ID71_STATUS_CLASS + (ST * num) + LEN_ID71_STATUS_CLASS);
		return(sStatusClass);
	}
	
	//#CM30330
	/**
	 * Acquires the Bank No. from the message "AccessImpossibleLocationReport".
	 * @param     num    index of status of access availability
	 * @return    Bank No.
	 */
	public String getBankNo(int num)
	{
		String sBankNo = getContent().substring(OFF_ID71_BANK_NO + (ST * num), OFF_ID71_BANK_NO + (ST * num) + LEN_ID71_BANK_NO);
		return(sBankNo);
	}
	
	//#CM30331
	/**
	 * Acquires the Start Bay No. in the message "AccessImpossibleLocationReport".
	 * @param     num    index of status of access availability
	 * @return    Start Bay No.
	 */
	public String getStartBay(int num)
	{
		String sStartBay = getContent().substring(OFF_ID71_START_BAY + (ST * num), OFF_ID71_START_BAY + (ST * num) + LEN_ID71_START_BAY);
		return(sStartBay);
	}
	
	//#CM30332
	/**
	 * Acquires the Start Level No. the message "AccessImpossibleLocationReport".
	 * @param     num    index of status of access availability
	 * @return    Start Level No.
	 */
	public String getStLevelNo(int num)
	{
		String sStLevelNo = getContent().substring(OFF_ID71_STLEVEL_NO + (ST * num), OFF_ID71_STLEVEL_NO + (ST * num) + LEN_ID71_STLEVEL_NO);
		return(sStLevelNo);
	}
	
	//#CM30333
	/**
	 * Acquires the End Bay No. from the message "AccessImpossibleLocationReport".
	 * @param     num    index of status of access availability
	 * @return    End Bay No.
	 */
	public String getEndBay(int num)
	{
		String sEndBay = getContent().substring(OFF_ID71_END_BAY + (ST * num), OFF_ID71_END_BAY + (ST*num) + LEN_ID71_END_BAY);
		return(sEndBay);
	}
	
	//#CM30334
	/**
	 * Acquires the End Bay No. from the message "AccessImpossibleLocationReport".
	 * @param     num    index of status of access availability
	 * @return    End Level No.
	 */
	public String getEnLevelNo(int num)
	{
		String sEnLevelNo = getContent().substring(OFF_ID71_ENLEVEL_NO + (ST * num), OFF_ID71_ENLEVEL_NO + (ST * num) + LEN_ID71_ENLEVEL_NO);
		return(sEnLevelNo);
	}
	
	//#CM30335
	/**
	 * Acquires the content of message "AccessImpossibleLocationReport".
	 * @return  content of message "AccessImpossibleLocationReport"
	 */
	public String toString()
	{
		return(new String(wLocalBuffer));
	}
	
	//#CM30336
	// Package methods -----------------------------------------------

	//#CM30337
	// Protected methods ---------------------------------------------
	//#CM30338
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg communication message: AccessImpossibleLocationReport
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM30339
		// length of received message
		LEN_ID71=rmsg.length;
		
		//#CM30340
		// Sets data to communication message buffer
		wLocalBuffer = new byte[LEN_ID71];
		for(int i = 0; i < LEN_ID71; i++)
		{
			wLocalBuffer[i] = rmsg[i];
		}
		wDataBuffer = wLocalBuffer;
	}
	//#CM30341
	// Private methods -----------------------------------------------

}
//#CM30342
//end of class

