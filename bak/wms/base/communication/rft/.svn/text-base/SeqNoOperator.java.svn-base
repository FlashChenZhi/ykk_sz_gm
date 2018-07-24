// $Id: SeqNoOperator.java,v 1.2 2006/11/07 06:35:13 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM644089
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;

import jp.co.daifuku.wms.base.rft.IdMessage;

//#CM644090
/**
 * Generate and check the transmission and reception Seq No.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>miya</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:35:13 $
 * @author  $Author: suresh $
 */
public class SeqNoOperator
{
	//#CM644091
	// Class fields --------------------------------------------------
	//#CM644092
	/**
	* Length of Sequence  No.
	*/
	private static final int SEQ_NO_LENGTH = 4;
	//#CM644093
	/**
	* Initial value of Sequence No.
	*/
	private static final int SEQ_NO_START = 1;
	//#CM644094
	/**
	* Format definition of Sequence No.
	*/
	private static final String SEQ_NO_FORMAT = "0000";
	//#CM644095
	/**
	 * The maximum value as Sequence Number in communication with RFT
	 */
	public static final int MAX_SEQ_NUMBER = 9999;

	//#CM644096
	// Class variables -----------------------------------------------
	//#CM644097
	/**
	 * Variable of seqNo for reception
	 */
	private int receiveSeqNo;
	//#CM644098
	/**
	 * Variable of seqNo for transmission
	 */
	private int sendSeqNo;

	//#CM644099
	// Class method --------------------------------------------------
	//#CM644100
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:35:13 $");
	}
	//#CM644101
	// Constructors --------------------------------------------------
	//#CM644102
	/**
	 * Constructor of management processing of Seq No. of Communication message. 
	 * Initialize Seq No. for the reception and for the transmission. 
	 * 
	 */
	public SeqNoOperator()
	{
		//#CM644103
		// Initialization of Seq No. for reception
		receiveSeqNo = SEQ_NO_START;
		//#CM644104
		// Initialization of Seq No. for transmission
		sendSeqNo = SEQ_NO_START;
	}

	//#CM644105
	// Public methods ------------------------------------------------
	//#CM644106
	/**
	 * Acquire reception Seq No.
	 * @return Byte array of reception Seq No.
	 */
	public byte[] getByteReceivSeqNo()
	{
		byte[] outSeqNo = new byte[SEQ_NO_LENGTH + 1];
		try
		{
			//#CM644107
			// Format  of Seq No."0000" and Constructor. 
			DecimalFormat dfmt = new DecimalFormat(SEQ_NO_FORMAT);
			String swSeqNo = dfmt.format(receiveSeqNo);
			outSeqNo = swSeqNo.getBytes(IdMessage.ENCODE);

			return outSeqNo;
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			//#CM644108
			// Drop the error to the log file. 
			e.printStackTrace(pw);
			Object[] tobj = new Object[1];
			tobj[0] = sw.toString();
			//#CM644109
			// SeqNo area initialization
			for (int i = 0; i < SEQ_NO_LENGTH; i++)
			{
				outSeqNo[i] = ' ';
			}
		}
		return outSeqNo;
	}
	//#CM644110
	/**
	 * Acquire transmission Seq No.
	 * @return Byte array of transmission Seq No.
	 */
	public byte[] getByteSendSeqNo()
	{
		byte[] outSeqNo = new byte[SEQ_NO_LENGTH + 1];
		try
		{
			//#CM644111
			// Format  of Seq No."0000" and Constructor. 
			DecimalFormat dfmt = new DecimalFormat(SEQ_NO_FORMAT);
			String swSeqNo = dfmt.format(sendSeqNo);
			outSeqNo = swSeqNo.getBytes(IdMessage.ENCODE);
			//#CM644112
			// SeqNo. increment
			int wSSeqNo = setNextSendSeqNo();

			return outSeqNo;
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			//#CM644113
			//Drop the error to the log file. 
			e.printStackTrace(pw);
			Object[] tobj = new Object[1];
			tobj[0] = sw.toString();

			//#CM644114
			// SeqNo area initialization
			for (int i = 0; i < SEQ_NO_LENGTH; i++)
			{
				outSeqNo[i] = ' ';
			}
		}
		return outSeqNo;
	}
	//#CM644115
	/**
	 * Acquire transmission Seq No. Do increment of Seq No.
	 * @return Transmission Seq No. character string
	 */
	public String getStrSendSeqNo()
	{
		//#CM644116
		// Format  of Seq No."0000" and Constructor. 
		DecimalFormat dfmt = new DecimalFormat(SEQ_NO_FORMAT);
		String swSeqNo = dfmt.format(sendSeqNo);

		//#CM644117
		// incrementing SeqNo.
		int wSSeqNo = setNextSendSeqNo();

		return swSeqNo;
	}
	//#CM644118
	/**
	 * Acquire next transmission Seq No.
	 * @return Next transmission Seq No.
	 */
	public int setNextSendSeqNo()
	{
		sendSeqNo++;
		if (sendSeqNo > MAX_SEQ_NUMBER)
		{
			sendSeqNo = SEQ_NO_START;
		}
		return sendSeqNo;
	}
	//#CM644119
	/**
	 * Acquire next reception Seq No.
	 * @return Next reception Seq No.
	 */
	public int setNextReceivSeqNo()
	{
		receiveSeqNo++;
		if (receiveSeqNo > MAX_SEQ_NUMBER)
		{
			receiveSeqNo = SEQ_NO_START;
		}
		return receiveSeqNo;
	}
	//#CM644120
	/**
	 * Check reception Seq No.
	 * @param	inBRSeqNo	Acquisition SeqNo
	 * @return	True when Normal, False in case of repetition reception. 
	 */
	public boolean checkReceiveSeqNo(byte[] inBRSeqNo)
	{
		try
		{
			//#CM644121
			// Acquisition SeqNo is converted to the numerical value. 
			String wsSeqNo = new String(inBRSeqNo, 0, SEQ_NO_LENGTH);
			int intSeqNo = Integer.parseInt(wsSeqNo);
			//#CM644122
			// SeqNo checks the reception and waiting with reception SeqNo. 
			if ((receiveSeqNo - 1) == intSeqNo)
			{
				//#CM644123
				// It is considered the repetition reception at last same SeqNo. 
				return (false);
			}
			else if (receiveSeqNo == intSeqNo)
			{
				//#CM644124
				// Next reception SeqNo set
				int Retinf = setNextReceivSeqNo();
			}
			else
			{
				//#CM644125
				// When SeqNo waiting for Reception  != intSeqNo
				receiveSeqNo = intSeqNo;
				//#CM644126
				// Next reception SeqNo set
				int Retinf = setNextReceivSeqNo();
			}
			return (true);
		}
		catch (Exception e)
		{
			return (false);
		}
	}

	//#CM644127
	// Package methods -----------------------------------------------
	//#CM644128
	// Protected methods ---------------------------------------------
	//#CM644129
	// Private methods -----------------------------------------------
}
//#CM644130
//end of class
