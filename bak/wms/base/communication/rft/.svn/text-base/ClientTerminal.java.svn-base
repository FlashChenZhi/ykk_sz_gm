// $Id: ClientTerminal.java,v 1.2 2006/11/07 06:50:34 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM643747
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.rft.IdMessage;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;

//#CM643748
/**
 * Define information on the client terminal. 
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:50:34 $
 * @author  $Author: suresh $
 */
public class ClientTerminal implements ReturnValue
{
	//#CM643749
	// Class fields --------------------------------------------------
	//#CM643750
	/**
	* Format definition of RFT No.
	*/
	public static final String RFT_FORMAT = "000";

	//#CM643751
	/**
	 * Define the RFT name. 
	 */
	public static final String RFT_NAME = "RFT";

	//#CM643752
	/**
	* Minimum of RFT title machine No.
	*/
	public static final int MIN_RFT_NO = 1;

	//#CM643753
	/**
	* Maximum of RFT title machine No.
	*/
	public static final int MAX_RFT_NO = 999;

	//#CM643754
	/**
	* Message
	*/
	public static final int NEW_MESSAGE = 0;
	public static final int MESSAGE_PROCESSING = 1;
	public static final int MESSAGE_RESPONSED = 2;
	//#CM643755
	// Class variables -----------------------------------------------
	//#CM643756
	/**
	 * Terminal information
	 */
	private class ClientInfo
	{
		Connection conn;
		SeqNoOperator seqNo;
		byte[] recvMsg = null;
		byte[] sendMsg = null;
	}
	
	Hashtable ClientInfoTbl = new Hashtable();
	
	//#CM643757
	// Class method --------------------------------------------------
	//#CM643758
	/**
	 * Check and Return title machine No. which converts RFT title machine No. of receive data into the integer type. <BR>
	 * Cut out RFT title machine No. from receive data by byte row. <BR>
	 * Convert title machine No. cut out into the character string. <BR>
	 * Convert title machine No. of the character string type into the integer type. <BR>
	 * When it is not possible to convert it and when title machine No. is not between minimum value and the maximum values,
	 * Return RET_NG. 
	 * @param rData Receiving transmission message
	 * @return Title machine No. Return RET_NG in case of error. 
	 * @throws	NumberFormatException	It is notified when it is not possible to restore it to the integer value. 
	 */
	public static int checkRftNo(byte[] rData) throws NumberFormatException
	{
		try
		{
			//#CM643759
			// RFT title machine No. while receiving is taken out, and it converts it to Int. 
			byte[] RftNoWk = new byte[IdMessage.LEN_RFTNO];
			for (int i = 0, j = IdMessage.OFF_RFTNO; i < IdMessage.LEN_RFTNO; i++, j++)
			{
				RftNoWk[i] = rData[j];
			}
			String RftNoString = new String(RftNoWk);
			int recvRftNoInt = Integer.parseInt(RftNoString.trim());

			//#CM643760
			// Check RFT title machine No. 
			if ((recvRftNoInt < MIN_RFT_NO) || (recvRftNoInt > MAX_RFT_NO))
			{
				return RET_NG;
			}

			return (recvRftNoInt);
		}
		catch (NumberFormatException e)
		{
			return RET_NG;
		}
		catch (Exception e)
		{
			return RET_NG;
		}
	}

	//#CM643761
	// Constructors --------------------------------------------------
	//#CM643762
	/**
	 * Constructor
	 */
	public ClientTerminal() throws SQLException,ReadWriteException
	{
		RftSearchKey skey = new RftSearchKey();
		skey.setRftNoOrder(1,true);
		RftHandler handler = new RftHandler(WmsParam.getConnection());
		Rft[] rftList = (Rft[])handler.find(skey);
		System.out.print("Entry terminal No. = ");
		for (int i = 0; i < rftList.length; i++)
		{
			ClientInfo cInfo = new ClientInfo();
			cInfo.conn = WmsParam.getConnection();
			cInfo.seqNo = new SeqNoOperator();
			Integer intRft = new Integer(rftList[i].getRftNo());
			ClientInfoTbl.put(intRft,cInfo);
			System.out.print(intRft.toString() + " ");
		}
		System.out.println("");
	}

	//#CM643763
	// Public methods ------------------------------------------------
	//#CM643764
	/**
	 * Return the DB connection for the specified title machine. 
	 * Acquire the connection newly, and return it if it is not set. 
	 * 
	 * @param rftNo		RFT title machine No
	 * @return			DB connection
	 * @throws SQLException It is notified when the data base access error or other errors occur. 
	 */
	public Connection getConnection(int rftNo) throws SQLException
	{
		ClientInfo cInfo = (ClientInfo)ClientInfoTbl.get(new Integer(rftNo));
		if (cInfo != null)
		{
			return cInfo.conn;
		}
		else
		{
			return WmsParam.getConnection();
		}
	}
	
	//#CM643765
	/**
	 * Return the sequence operator for the specified title machine. 
	 * 
	 * @param rftNo		RFT title machine No
	 * @return			Sequence operator
	 */
	public SeqNoOperator getSeqNo(int rftNo)
	{
		ClientInfo cInfo = (ClientInfo)ClientInfoTbl.get(new Integer(rftNo));
		if (cInfo != null)
		{
			return cInfo.seqNo;
		}
		else
		{
			return null;
		}
	}

	//#CM643766
	/**
	 * Check processing of the received message. 
	 * 
	 * @param rftNo		RFT title machine No
	 * @param msg		Receiving transmission message
	 * @return			One when received transmission message and the same transmission message are processed, Two in case of response transmission ending
	 * 					Return 0 for a new transmission message. 
	 */
	public int checkStatus(int rftNo, byte[] msg)
	{
		int ret = NEW_MESSAGE;
		ClientInfo cInfo = (ClientInfo)ClientInfoTbl.get(new Integer(rftNo));
		if (cInfo != null)
		{
			if (cInfo.recvMsg != null)
			{
				for (int i = 0; i < cInfo.recvMsg.length; i ++)
				{
					if (cInfo.recvMsg[i] != msg[i])
					{
						//#CM643767
						// New transmission message(Return because New Message when it differs even by one)
						return NEW_MESSAGE;
					}
				}
	
				if (cInfo.sendMsg == null)
				{
					//#CM643768
					// The same transmission message is being processed. 
					ret = MESSAGE_PROCESSING;
				}
	
				//#CM643769
				// Transmission message has responded in the same way. 
				ret = MESSAGE_RESPONSED;
			}
		}
		else
		{
			ret = NEW_MESSAGE;
		}
		return ret;
	}

	//#CM643770
	/**
	 * Preserve received transmission message of each title machine. 
	 * Clear response transmission message at this time. (Put it into the status under processing. ) 
	 * 
	 * @param rftNo		RFT title machine No
	 * @param msg		Receiving transmission message
	 */
	public void setReceivedMessage(int rftNo, byte[] msg)
	{
		ClientInfo cInfo = (ClientInfo)ClientInfoTbl.get(new Integer(rftNo));
		if (cInfo != null)
		{
			cInfo.recvMsg = msg;
			cInfo.sendMsg = null;
		}
	}

	//#CM643771
	/**
	 * Preserve received transmission message of each title machine. 
	 * 
	 * @param rftNo		RFT title machine No
	 * @param msg		Receiving transmission message
	 */
	public void setSendedMessage(int rftNo, byte[] msg)
	{
		ClientInfo cInfo = (ClientInfo)ClientInfoTbl.get(new Integer(rftNo));
		if (cInfo != null)
		{
			cInfo.sendMsg = msg;
		}
	}

	//#CM643772
	/**
	 * Acquire response transmission message transmitted when processing it last time of the specified title machine. 
	 * 
	 * @param rftNo		RFT title machine No
	 * @return			Response which has been transmitted
	 */
	public byte[] getSendedMessage(int rftNo)
	{
		ClientInfo cInfo = (ClientInfo)ClientInfoTbl.get(new Integer(rftNo));
		if (cInfo != null)
		{
			return cInfo.sendMsg;
		}
		else
		{
			return null;
		}
	}
	
	//#CM643773
	/**
	 * Open the DB connection. 
	 * 
	 */
	public void closeConnections()
	{
		try
		{
			for (Enumeration e = ClientInfoTbl.elements(); e.hasMoreElements();)
			{
				ClientInfo cInfo = (ClientInfo)e.nextElement();
				
				if (cInfo.conn != null)
				{
					cInfo.conn.rollback();
					cInfo.conn.close();
				}
			}
		}
		catch (SQLException e)
		{
		}
	}

	//#CM643774
	// Package methods -----------------------------------------------
	//#CM643775
	// Protected methods ---------------------------------------------
	//#CM643776
	// Private methods -----------------------------------------------
}
//#CM643777
//end of class
