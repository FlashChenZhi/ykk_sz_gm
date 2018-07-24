//#CM644131
//$Id: TraceFile.java,v 1.3 2006/11/10 01:06:57 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM644132
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.text.DecimalFormat;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RandomAcsFileHandler;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM644133
/**
 * Operate the file which records the communication trace. 
 * 
 * @version $Revision: 1.3 $, $Date: 2006/11/10 01:06:57 $
 * @author  $Author: suresh $
 */
public class TraceFile implements ReturnValue
{
	//#CM644134
	// Class fields --------------------------------------------------
	//#CM644135
	/**
	 * Definition of resource name(Trace ON)
	 */
	public static final String TRACE_ON = "RFT_TRACE_ON";

	//#CM644136
	/**
	 * Definition of resource name(Trace File Name)
	 */
	public static final String TRACE_NAME = "RFT_TRACE_NAME";

	//#CM644137
	/**
	 * Definition of resource name(Trace Pointer File Name)
	 */
	public static final String POINTER_NAME = "RFT_TRACE_POINTER_NAME";

	//#CM644138
	/**
	 * Definition of resource name(Max size of trace file(Byte))
	 */
	public static final String MAX_SIZE = "TRACE_MAX_SIZE";
	
	//#CM644139
	/**
	 * Reception trace
	 */
	public static final String RECIEVE = "R";

	//#CM644140
	/**
	 * Transmission trace
	 */
	public static final String SEND = "S";

	//#CM644141
	// Class variables -----------------------------------------------
	//#CM644142
	/**
	 * Directory which drops trace file
	 */
	protected String LogDir = null;

	//#CM644143
	/**
	 * Flag whether to drop transmission message to RFT to trace file
	 */
	protected boolean sTrOn = false;
	//#CM644144
	/**
	 * Transmission message Trace File Name to RFT
	 */
	protected String sTrName = "RftCommunicationTrace.txt";
	//#CM644145
	/**
	 * Transmission message Trace Pointer File Name to RFT
	 */
	protected String sPntName = "RftCommunicationPointer.dat";
	//#CM644146
	/**
	 * LogHandler for transmission message trace file to RFT
	 */
	protected RandomAcsFileHandler TrcHandler = null;
	//#CM644147
	/**
	 * Parameter for trace file with RFT
	 */
	protected static Object[] trcParam = new Object[1];
	//#CM644148
	/**
	 * Max size of trace file(Byte)
	 */
	protected int maxFileSize = 5120;
	//#CM644149
	/**
	 * One-line size of trace file(byte)
	 */
	protected int lineLength = 512;

	//#CM644150
	// Class method --------------------------------------------------
	//#CM644151
	// Constructors --------------------------------------------------
	//#CM644152
	// Public methods ------------------------------------------------
	//#CM644153
	/**
	 * Make communication trace information. 
	 * @param rftNumber RFT Title No.
	 * @return 0 when Normal (RET_OK), -1 when Error (RET_NG)
	 */
	public int setTraceInfomation(int rftNumber)
	{
		try
		{
			//#CM644154
			// Format  of RFT No."000" and Constructor. 
			DecimalFormat dfmtRFT = new DecimalFormat(ClientTerminal.RFT_FORMAT);
			String swRFTNo = dfmtRFT.format(rftNumber);

			//#CM644155
			// Directory which drops trace file is set. 
			LogDir = WmsParam.WMS_LOGS_PATH;

			//#CM644156
			// Max size of trace file is set. 
			maxFileSize = WmsParam.TRACE_MAX_SIZE;

			//#CM644157
			// The flag whether drop to the RFT communication trace file is set. 
			Boolean sTrOnB = new Boolean(WmsParam.RFT_TRACE_ON);
			sTrOn = sTrOnB.booleanValue();
			if (sTrOn)
			{
				//#CM644158
				// RFT communication Trace File Name is set. 
				sTrName =
					LogDir + ClientTerminal.RFT_NAME + swRFTNo + WmsParam.RFT_TRACE_NAME;
				//#CM644159
				// RFT communication Trace Pointer File Name is set. 

				//#CM644160
				// Do not erase it for enhancing the future though it is not using now. 
				sPntName =
					LogDir
						+ ClientTerminal.RFT_NAME
						+ swRFTNo
						+ WmsParam.RFT_TRACE_POINTER_NAME;
			}

			return RET_OK;
		}
		catch (Exception e)
		{
			return RET_NG;
		}
	}

	//#CM644161
	/**
	 * Trace the content of the communication with RFT. 
	 * @param	type	Trace type S:Send R:Receive
	 * @param	data	Trace content
	 */
	public void write(String type, String data)
	{
		try{
			if (sTrOn && (TrcHandler == null))
			{
				TrcHandler = new RandomAcsFileHandler(sTrName, maxFileSize);
			}
			if (!(TrcHandler == null))
			{
				synchronized (TrcHandler)
				{
					trcParam[0] = data;
					TrcHandler.writeUTF(0, LogMessage.F_INFO, type, trcParam);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Error of Trace Log writeing !! [" + data + "]");
		}
	}
	//#CM644162
	// Package methods -----------------------------------------------
	//#CM644163
	// Protected methods ---------------------------------------------
	//#CM644164
	// Private methods -----------------------------------------------
}
//#CM644165
//end of class
