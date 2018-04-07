// $Id: CommunicationRft.java,v 1.2 2006/11/07 06:50:14 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM643778
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;

import jp.co.daifuku.common.InvalidProtocolException;

//#CM643779
/**
 * Transmit to RFT by the RFT protocol. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>miya</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:50:14 $
 * @author  $Author: suresh $
 */
public class CommunicationRft
{
	//#CM643780
	// Class fields --------------------------------------------------
	//#CM643781
	/**
	 * Define communication transmission message length in RFT. 
	 */
	public static final int DEFAULT_LENGTH = 1024;
	//#CM643782
	/**
	 * Define the lowest communication transmission message length in RFT. 
	 */
	public static final int MINIMAM_LENGTH = 24;
	//#CM643783
	/**
	 * Define beginning mark STX of transmission message with RFT. 
	 */
	public static final int STX = 0x02;
	//#CM643784
	/**
	 * Define terminal mark ETX of transmission message with RFT. 
	 */
	public static final int ETX = 0x03;
	//#CM643785
	/**
	 * Seqence  No. in RFT Define Format. 
	 */
	public static final String SEQ_FORMAT = "0000";
	//#CM643786
	/**
	* Definition of resource name(Communication operation log ON)
	*/
	public static final String A_LOG_ON = "RFT_ACTION_LOG_ON";
	//#CM643787
	/**
	* Definition of resource name(File name of communication operation log)
	*/
	public static final String A_LOG_NAME = "RFT_ACTION_LOG_NAME";
	//#CM643788
	/**
	 * Value at Initial as Sequence Number in communication with RFT
	 */
	public static final int INITIAL_NUMBER = 0;
	//#CM643789
	/**
	 * Start value at LOOP as Sequence Number in communication with RFT
	 */
	public static final int LOOP_START_NUMBER = 1;

	//#CM643790
	// Class variables -----------------------------------------------
	//#CM643791
	/**
	 * RFT Server Name.
	 */
	protected String host;
	//#CM643792
	/**
	 * Connection Socket with RFT side. 
	 */
	protected Socket serversock = null;
	//#CM643793
	/**
	 * Output and data stream to RFT
	 */
	protected DataOutputStream wDataOut;
	//#CM643794
	/**
	 * Input and data stream from RFT
	 */
	protected DataInputStream wDataIn;
	//#CM643795
	/**
	 * Parameter for operation log file with RFT
	 */
	protected static Object[] aLogParam = new Object[1];
	//#CM643796
	/**
	 * Port number format for communication operation log file with RFT
	 */
	protected static DecimalFormat fmt = new DecimalFormat("000000");
	//#CM643797
	/**
	 * Operation details description Buffer for communication operation log with RFT
	 */
	String action = "";

	//#CM643798
	// Class method --------------------------------------------------
	//#CM643799
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:50:14 $");
	}
	//#CM643800
	// Constructors --------------------------------------------------
	//#CM643801
	/**
	 * Establishing the TCP/IP connection from RFT is prepared like RFT Server.<BR>
	 * Port No. : It behaves so that the server may start with LOCALHOST. 
	 */
	public CommunicationRft()
	{
	}

	//#CM643802
	// Public methods ------------------------------------------------

	//#CM643803
	/**
	 * Do connected standby to RFT as a server. 
	 * @param 	server ServerSocket
	 * @return Socket to RFT at connection destination
	 * @throws	IOException It is notified when file I/O error occurs. 
	 */
	public Socket connect(ServerSocket server) throws IOException
	{
		//#CM643804
		// Connected waiting from client
		serversock = server.accept();
		//#CM643805
		// Reception stream making after connecting
		wDataIn =
			new DataInputStream(
				new BufferedInputStream(serversock.getInputStream()));
		//#CM643806
		// Sending stream making after connecting
		wDataOut =
			new DataOutputStream(
				new BufferedOutputStream(serversock.getOutputStream()));
		return (serversock);
	}
	//#CM643807
	/**
	 * Cut it into RFT. 
	 * @throws	IOException It is notified when file I/O error occurs. 
	 */
	public void disconnect() throws IOException
	{
		serversock.close();
	}
	//#CM643808
	/**
	 * Confirm whether to complete the connection. 
	 * @return  True if it has connected. 
	 */
	public boolean isConnected()
	{
		return (serversock != null);
	}

	//#CM643809
	/**
	 * Return the stream for the input to RFT. 
	 * @return  TCP/IP DataInputStream to Socket
	 */
	public DataInputStream getInStream()
	{
		return (wDataIn);
	}

	//#CM643810
	/**
	 * Return the stream for the output to RFT. 
	 * @return  TCP/IP DataOutputStream to Socket
	 */
	public DataOutputStream getOutStream()
	{
		return (wDataOut);
	}
	//#CM643811
	/**
	 * Transmit transmission message to RFT. 
	 * @param	msg	Transmission transmission message
	 * @throws	IOException It is notified when file I/O error occurs. 
	 */
	public void send(byte[] msg) throws IOException
	{
		//#CM643812
		// The length of transmission message is acquired. 
		int iSendDataLength = getDataLength(msg);

		//#CM643813
		// The information statement body is transmitted. 
		wDataOut.write(msg, 0, iSendDataLength);
		//#CM643814
		// It transmits really here. 
		wDataOut.flush();
	}
	//#CM643815
	/**
	 * Receive transmission message from RFT. 
	 * @param	recvData Receiving data
	 * @return      out RecvData STX and ETX contain Receiving data. 
	 *              ret = 0:Line cutting  ret > 0:Receiving data length
	 *              ret < 0:ERROR
	 */
	public int recv(byte[] recvData)
	{
		byte[] inByte = new byte[DEFAULT_LENGTH];
		byte ret = 0;
		int ii = 0;
		int rcvLength = 0;
		int waitLoopCount = 0;

		try
		{
			while (true)
			{

				//#CM643816
				// Check whether readByte is possible or not.
				int len = wDataIn.available();
				if (len > 0)
				{
					ret = wDataIn.readByte();
					waitLoopCount = 0;
				}
				else
				{
					waitLoopCount++;
					Thread.sleep(10);
					if (waitLoopCount > 300)
					{
						//#CM643817
						// When data is received for three seconds, it is assumed as an error. 
						throw new EOFException();
					}
					continue;
				}

				rcvLength++;
				if (ret == STX)
				{
					//#CM643818
					// Transmission message of only STX is disregarded. 
					inByte[ii++] = ret;
					rcvLength = 1;
					continue;
				}
				else if (ret == ETX)
				{
					if (rcvLength == 1)
					{
						rcvLength = 0;
						continue;
					}
					else
					{
						//#CM643819
						// Revoke transmission message when length is below the lowest of the transmission message. 
						if (rcvLength > MINIMAM_LENGTH)
						{
							inByte[ii++] = ret;
							break;
						}
						else
						{
							rcvLength = 0;
							ii = 0;
							continue;
						}
					}
				}
				else
				{
					//#CM643820
					// Transmission message reception normal
					if (rcvLength >= DEFAULT_LENGTH)
					{
						throw new InvalidProtocolException("6026013");
					}
					else if (rcvLength != 1)
					{
						inByte[ii++] = ret;
						continue;
					}
					//#CM643821
					// Revoked until STX is received. 
					else
					{
						rcvLength = 0;
						continue;
					}
				}
			}
		}
		catch (SocketException e)
		{
			System.out.println("CommunicationRft ::: SocketException = " + e);
			return (0);
		}
		catch (EOFException e)
		{
			System.out.println("CommunicationRft ::: EOFException = " + e);
			return (0);
		}
		catch (InvalidProtocolException e)
		{
			System.out.println(
				"CommunicationRft ::: InvalidProtocolException = " + e);
		}
		catch (IOException e)
		{
			System.out.println("RFTWatecher Exception e = " + e);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			//#CM643822
			//Drop the error to the log file. 
			e.printStackTrace(pw);
			System.out.println(
				"CommunicationRft ::: IOException = " + sw.toString());

			return (0);

		}
		catch (Exception e)
		{
			System.out.println("CommunicationRft ::: Exception = " + e);
		}
		//#CM643823
		//Write Receiving data in the trace. 
		for (int jj = 0; jj < ii; jj++)
		{
			recvData[jj] = inByte[jj];
		}
		//#CM643824
		// Drop the log if it is a setting which drops the operation log, and satisfies the condition. 
		action = "RFT Receive ";

		return (rcvLength);
	}

	//#CM643825
	/**
	 * Logging the communication action with RFT. 
	 * @param	action	Content of Logging
	 */
	public void actionLogWrite(String action)
	{
		aLogParam[0] = action;
	}

	//#CM643826
	/**
	 * Acquisition of length of message. 
	 * @param	inData	Content of Communication message
	 * @return      Length of Communication message
	 */
	public int getDataLength(byte[] inData)
	{
		int dataLength = 0;
		int i = 0;
		while (true)
		{
			if (inData[i] == ETX)
			{
				dataLength = i + 1;
				break;
			}
			else if (i >= DEFAULT_LENGTH)
			{
				return (0);
			}
			//#CM643827
			// Length of Communication message increment
			i++;
		}
		return (dataLength);
	}

	//#CM643828
	// Package methods -----------------------------------------------

	//#CM643829
	// Protected methods ---------------------------------------------

	//#CM643830
	// Private methods -----------------------------------------------

}
//#CM643831
//end of class
