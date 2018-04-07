// $Id: RftCommunicationControl.java,v 1.2 2006/11/07 06:48:04 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM643955
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.wms.base.rft.IdMessage;

//#CM643956
/**
 * Receive transmission message from RFT, and start a pertinent task of each ID. 
 * Moreover, transmit to RFT. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>miya</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:48:04 $
 * @author  $Author: suresh $
 */
public class RftCommunicationControl extends Thread
									implements ReturnValue
{
	//#CM643957
	// Class fields --------------------------------------------------
	//#CM643958
	/**
	 * Parameter for Error log
	 */
	protected static Object[] aErrParm = new Object[1];

	//#CM643959
	// Class variables -----------------------------------------------
	//#CM643960
	/**
	 * Variable which maintains reference to Utility class for connection with RFT. 
	 */
	private CommunicationRft wRft = null;
	//#CM643961
	/**
	 * Variable which maintains number of RFT
	 */
	private int wRftNumber = 1;

	//#CM643962
	/**
	 * Information on client terminal
	 */
	private ClientTerminal clientManager;
	
	//#CM643963
	// Class method --------------------------------------------------
	//#CM643964
	/**
	* Return the version of this class. 
	* @return Version and date
	*/
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 06:48:04 $";
	}

	//#CM643965
	// Constructors --------------------------------------------------
	//#CM643966
	/**
	 * Make the instance from the watch thread which is the start origin of the RFT communication after it connects it in the status of Accept. 
	 * @param rft Reference to Socet Connection Object <code>CommunicationRft</code >
	 * @param clientManager Reference to Socet Connection Object <code>ClientTerminal</code >
	 */
	public RftCommunicationControl(
		CommunicationRft rft,
		ClientTerminal clientManager)
	{
		this.clientManager = clientManager;
		
		//#CM643967
		// The reference to the Utility class for the connection with RFT is set. 
		wRft = rft;
	}

	//#CM643968
	// Public methods ------------------------------------------------
	//#CM643969
	/**
	 * Start a pertinent task. 
	 */
	public void run()
	{
		try
		{
			byte[] responseData = new byte[CommunicationRft.DEFAULT_LENGTH];
			byte[] rdata = new byte[CommunicationRft.DEFAULT_LENGTH];
			//#CM643970
			// Debug
			System.out.println(
				"DATA_DEFAULT_ LENGTH = " + CommunicationRft.DEFAULT_LENGTH);

			//#CM643971
			// Transmission message is received from RFT. 
			int iDataLen = wRft.recv(rdata);

			//#CM643972
			// RFT Title No. Check
			wRftNumber = ClientTerminal.checkRftNo(rdata);
			if (wRftNumber == RET_NG)
			{
				//#CM643973
				// RFT No. Error log
				RftLogMessage.print(
					6026023,
					LogMessage.F_WARN,
					"RftCommunicationControl",
					"[" + new String(rdata) + "]");
				throw new Exception();
			}
			
			TraceFile traceFile = new TraceFile();
			
			//#CM643974
			// Trace File use beginning setting
			traceFile.setTraceInfomation(wRftNumber);

			//#CM643975
			// Reception trace writing
			String wkRecvstr = new String(rdata, 0, iDataLen);
			traceFile.write(TraceFile.RECIEVE, wkRecvstr);

			//#CM643976
			// Debug
			if (iDataLen > 0)
			{
				System.out.println("RECEIVE DATA = " + wkRecvstr);
				System.out.println("RECEIVE LENGTJH = " + iDataLen);
			}

			//#CM643977
			// Acquire OracleDB Connection. 
			Connection conn = clientManager.getConnection(wRftNumber);
			
			String receivedId = "";

			boolean canSend = false;
			int processStatus = clientManager.checkStatus(wRftNumber, rdata);
			switch (processStatus)
			{
			case ClientTerminal.MESSAGE_PROCESSING:
				//#CM643978
				// Revoke transmission message under processing. 
				canSend = false;
				break;
				
			case ClientTerminal.MESSAGE_RESPONSED:
				//#CM643979
				// Return the transmitted response. 
				responseData = clientManager.getSendedMessage(wRftNumber);
				canSend = true;
				break;
				
			case ClientTerminal.NEW_MESSAGE:
				//#CM643980
				// Preserve Receiving transmission message. 
				clientManager.setReceivedMessage(wRftNumber, rdata);
				
				//#CM643981
				// To judge the type of Receiving transmission message, ID is examined. 
				IdMessage idMes = new IdMessage(rdata);
				receivedId = idMes.getID();

				//#CM643982
				// ID distribution processing
				IdProcessControl IdProcCntl = new IdProcessControl(conn);
				IdProcCntl.executeIdProc(
					idMes.getID(),
					rdata,
					responseData,
					conn);

				//#CM643983
				// Set the Seq No. transmission time in response Communication message. Attention: Return the response even if you fail in this. 
				createSendData(responseData, clientManager.getSeqNo(wRftNumber));

				canSend = true;
				break;
			}

			if (canSend)
			{
				//#CM643984
				// Transmit Communication message. 
				wRft.send(responseData);
				
				//#CM643985
				// Preserve transmission Message while working if it is not data reporting transmission message. 
				//#CM643986
				// (When ID0006 is maintained because ID0006 and system ID results are continuously transmitted when sending it again with the Welcat terminal, system ID results ahead of that do not remain. )  (It is difficult to transmit only results ID when sending it again. )
				if (! receivedId.equals("0006"))
				{
					clientManager.setSendedMessage(wRftNumber, responseData);
				}
				
				//#CM643987
				// Transmission trace writing
				int len = wRft.getDataLength(responseData);
				String wkSendstr = new String(responseData, 0, len);
				traceFile.write(TraceFile.SEND, wkSendstr);
				//#CM643988
				// Debug
				System.out.println("SEND DATA = " + wkSendstr);
				System.out.println("SEND LENGTH = " + len);
			}

			//#CM643989
			// Cut it after confirming Line cutting with RFT. 
			if (wRft != null)
			{
				System.out.println("<<< Disconnect [RftCommunicationControl] RftNo:" + wRftNumber);
				wRft.disconnect();
			}
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(
				6026013,
				LogMessage.F_ERROR,
				"RftCommunicationControl",
				e);
		}
	}

	//#CM643990
	/**
	 * Make the transmission data. 
	 * @param inData Response Communication message
	 * @param inSeqNo SeqNo. of RFT
	 * @return 0 when succeeding in transmission data making, -1 when failing
	 */
	public int createSendData(byte[] inData, SeqNoOperator inSeqNo)
	{
		int i;
		byte[] wbSeqNo = new byte[IdMessage.LEN_SEQ + 1];
		try
		{
			//#CM643991
			// Acquisition of SeqNo
			wbSeqNo = inSeqNo.getByteSendSeqNo();
			if (wbSeqNo[0] == ' ')
			{
				//#CM643992
				// SeqNo area initialization
				for (i = 0; i < IdMessage.LEN_SEQ; i++)
				{
					wbSeqNo[i] = '0';
				}
			}
			//#CM643993
			// Acquisition of system time HHMMSS 
			RftDate date = new RftDate();
			String wsTime = date.getSysTime();
			byte[] wbTime = wsTime.getBytes(IdMessage.ENCODE);

			//#CM643994
			// Set Seq No.
			for (i = 0; i < IdMessage.LEN_SEQ; i++)
			{
				inData[IdMessage.OFF_SEQ + i] = wbSeqNo[i];
			}
			//#CM643995
			// Set Send_TIME
			for (i = 0; i < IdMessage.LEN_SERVSENDDATE; i++)
			{
				inData[IdMessage.OFF_SERVSENDDATE + i] = wbTime[i];
			}

			//#CM643996
			// Set of transmission time
			return RET_OK;
		}
		catch (Exception e)
		{
			return RET_NG;
		}
	}

	//#CM643997
	// Package methods -----------------------------------------------

	//#CM643998
	// Protected methods ---------------------------------------------

	//#CM643999
	// Private methods -----------------------------------------------

}
//#CM644000
//end of class
