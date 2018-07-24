// $Id: As21Id30.java,v 1.2 2006/10/26 01:35:09 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29564
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29565
/**
 * Processes comunication message "Machine Status Report, ID=30" according to AS21 communication protocol.
 * Acquires the status of machines from the communication message "Machine State Report".<BR>
 * Internally, it generates instance of MachineState class and sets as many machine states as numbers of reports.<BR>
 * In detail, following will be set: machine code, state of each machine per code (operating/stopped/down/cutting off)
 * and the error codes.
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
public class As21Id30 extends ReceiveIdMessage
{
	//#CM29566
	// Class fields --------------------------------------------------
	//#CM29567
	/**
	 * Initial setting for the length of communication message (in byte)
	 */
	public int LEN_ID30=0;

	//#CM29568
	/**
	 * Defines the offset of continuation classification
	 */
	private static final int OFF_ID30_CONTINU_CLASS = 0;

	//#CM29569
	/**
	 * Length of continuation classsification (in byte)
	 */
	private static final int LEN_ID30_CONTINU_CLASS = 1;
	
	//#CM29570
	/**
	 * Defines the offset of number of reports
	 */
	private static final int OFF_ID30_REPORTNUM_CLASS = OFF_ID30_CONTINU_CLASS + LEN_ID30_CONTINU_CLASS;

	//#CM29571
	/**
	 * Length of number of reports (in byte)
	 */
	private static final int LEN_ID30_REPORTNUM_CLASS = 2;
	
	//#CM29572
	/**
	 * Defines the offset of machine code
	 */
	private static final int OFF_ID30_MODEL_CODE = OFF_ID30_REPORTNUM_CLASS + LEN_ID30_REPORTNUM_CLASS;

	//#CM29573
	/**
	 * Length of machine code (in byte)
	 */
	private static final int LEN_ID30_MODEL_CODE = 2;
	
	//#CM29574
	/**
	 * Defines the offset of machine code number
	 */
	private static final int OFF_ID30_MACHINE_NO = OFF_ID30_MODEL_CODE + LEN_ID30_MODEL_CODE;

	//#CM29575
	/**
	 * Length of machine code number(in byte)
	 */
	private static final int LEN_ID30_MACHINE_NO = 4;
	
	//#CM29576
	/**
	 * Defines the offset of state
	 */
	private static final int OFF_ID30_CONDITION = OFF_ID30_MACHINE_NO + LEN_ID30_MACHINE_NO;

	//#CM29577
	/**
	 * Length of state (in byte)
	 */
	private static final int LEN_ID30_CONDITION = 1;
	
	//#CM29578
	/**
	 * Defines the offset of error code
	 */
	private static final int OFF_ID30_ABNORMAL_CODE = OFF_ID30_CONDITION + LEN_ID30_CONDITION;

	//#CM29579
	/**
	 * Length of error code (in byte)
	 */
	private static final int LEN_ID30_ABNORMAL_CODE = 7;
	
	//#CM29580
	/**
	 * Length of machine status of individual report (in byte)
	 */
	private static final int FT = 14;
	
	//#CM29581
	/**
	 * Continuation classification : indicates the end of report text and the final
	 */
	public static final String NO_CONTINU = "2";
	
	//#CM29582
	/**
	 * State : operating
	 */
	public static final int STATE_ACTIVE = 0;

	//#CM29583
	/**
	 * State : stopped
	 */
	public static final int STATE_STOP = 1;

	//#CM29584
	/**
	 * State : down
	 */
	public static final int STATE_FAIL = 2;

	//#CM29585
	/**
	 * State : separated
	 */
	public static final int STATE_OFFLINE = 3;
	
	//#CM29586
	// Class variables -----------------------------------------------
	//#CM29587
	/**
	 * Communication message buffer
	 */
	private byte[] wLocalBuffer;

	//#CM29588
	// Class method --------------------------------------------------
	//#CM29589
	/**
	 * Returns the version of this class.
	 * return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:35:09 $");
	}

	//#CM29590
	// Constructors --------------------------------------------------
	//#CM29591
	/**
	 * Default constructor
	 */
	public As21Id30 ()
	{
		super();
	}
	//#CM29592
	/**APasses communication message received from AGC to the constructor.
	 * @param <code>as21Id30</code>  the communication message "Machine Status Report, ID=30"
	 * Updates the states of machines.
	 */
	public As21Id30(byte[] as21Id30)
	{
		super();
		setReceiveMessage(as21Id30);
	}

	//#CM29593
	// Public methods ------------------------------------------------
	//#CM29594
	/**
	 * Acquires continuation classification (whether/not the communication message consists of 2 sentences or more)
	 * from the message Machine Status Report.
	 * @return    	True: continues<BR>
	 *				False: complete in just one sentence.
	 */
	public boolean getContinuationClassification ()
	{
		String continuationClassification = getContent().substring(OFF_ID30_CONTINU_CLASS, LEN_ID30_CONTINU_CLASS);
		return (continuationClassification.equals(NO_CONTINU));
	}
	
	//#CM29595
	/**
	 * Acquires the number of reports from the communication message Machine Status Report.
	 * @return    number of reports
	 * @throws InvalidProtocolException: Reports if numeric value was not provided for number of reports.
	 */
	public int getNumberOfReports() throws InvalidProtocolException
	{
		int numberOfReports = 0;
		String snumberOfReports = getContent().substring(OFF_ID30_REPORTNUM_CLASS, OFF_ID30_REPORTNUM_CLASS + LEN_ID30_REPORTNUM_CLASS);
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

	//#CM29596
	/**
	 * Acquires machine code from the communication message Machine Status Report.
	 * @param	num		index of status of machines
	 * @return			machine code
	 */
	public String getModelCode(int num)
	{
		String sModelCode = getContent().substring(OFF_ID30_MODEL_CODE + (FT * num), OFF_ID30_MODEL_CODE + (FT * num) + LEN_ID30_MODEL_CODE);
		return(sModelCode);
	}
	
	//#CM29597
	/**
	 * Acquires machine code number from the communication message Machine Status Report.
	 * @param	num		index of status of machines
	 * @return			machine code number
	 */
	public String getMachineNo(int num)
	{
		String sMachineNo = getContent().substring(OFF_ID30_MACHINE_NO + (FT * num), OFF_ID30_MACHINE_NO + (FT * num) + LEN_ID30_MACHINE_NO);
		return(sMachineNo);
	}
	
	//#CM29598
	/**
	 * Acquires the state from the communication message Machine Status Report.
	 * @param	num		index of status of machines
	 * @return			state
	 * @throws InvalidProtocolException:Reports if numeric value was not provided for state.
	 */
	public int getCondition(int num) throws InvalidProtocolException
	{
		int iCondition = 0;
		String condition = getContent().substring(OFF_ID30_CONDITION + (FT * num), OFF_ID30_CONDITION + (FT * num) + LEN_ID30_CONDITION);
		try
		{
			iCondition = Integer.parseInt(condition);
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Invalid Response:" + condition)) ;
		}
		return(iCondition);
	}
	
	//#CM29599
	/**
	 * Acquires error code from the communication message Machine Status Report.
	 * @param	num		index of status of machines
	 * @return			error Code
	 */
	public String getAbnormalCode(int num)
	{
		String sAbnormalCode = getContent().substring(OFF_ID30_ABNORMAL_CODE + (FT * num), OFF_ID30_ABNORMAL_CODE + (FT * num) + LEN_ID30_ABNORMAL_CODE);
		return(sAbnormalCode);
	}
	
	//#CM29600
	/**
	 * Acquires the content of communication message Mahicne Status Report.
	 * @return  text content of machine status report
	 */
	public String toString()
	{
		return(new String(wLocalBuffer));
	}

	//#CM29601
	// Package methods -----------------------------------------------

	//#CM29602
	// Protected methods ---------------------------------------------
	//#CM29603
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param	rmsg	communication message received from AGC
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29604
		// length of received message
		LEN_ID30 = rmsg.length;
		
		//#CM29605
		// Sets data to communication message buffer
		wLocalBuffer = new byte[LEN_ID30];
		for(int i = 0; i < LEN_ID30; i++)
		{
			wLocalBuffer[i] = rmsg[i];
		}
		wDataBuffer = wLocalBuffer;
	}

	//#CM29606
	// Private methods -----------------------------------------------

}
//#CM29607
//end of class

