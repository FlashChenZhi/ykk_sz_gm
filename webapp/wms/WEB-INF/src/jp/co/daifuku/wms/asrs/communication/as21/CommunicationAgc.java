// $Id: CommunicationAgc.java,v 1.3 2006/11/13 08:30:58 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30992
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
import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;

import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RandomAcsFileHandler;

//#CM30993
/**
 * This class sends/receives texts according to AS21 ptrotocol.
 * Connection is made with specified group controller, then text is sent/received.
 * Send/receive txet are done according to TCP/IP protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/13 08:30:58 $
 * @author  $Author: suresh $
 */
public class CommunicationAgc extends Object
{
	//#CM30994
	// Class fields --------------------------------------------------
	//#CM30995
	/**
	 * Defines the default server name on AGC side for AS21.
	 */
	 public static final String DEFAULT_NAME = "AGC01";

	//#CM30996
	/**
	 * Defines the default server port on AGC side for AS21.
	 */
	 public static final int DEFAULT_PORT = 2000;

	//#CM30997
	/**
	 * Defines the length of communication message of AS21.
	 */
	 public static final int DEFAULT_LENGTH = 512;

	//#CM30998
	/**
	 * Defines the least length of communication message of AS 21.
	 */
	 public static final int MINIMAM_LENGTH = 24;

	//#CM30999
	/**
	 * Defines the start mark STX of the communication message according to AS21.
	 */
	 public static final int STX = 0x02;

	//#CM31000
	/**
	 * Defines the end mark ETX of the communication message according to AS21.
	 */
	 public static final int ETX = 0x03;

	//#CM31001
	/**
	 * Defines the Seqence No. Format according to AS21.
	 */
	 public static final String SEQ_FORMAT = "0000";

	//#CM31002
	/**
	 * Defines the character coding of communication message according to AS21.
	 */
	 public static final String CODE = "Shift_JIS";

	//#CM31003
 	/**
	 * Defines the name of resource (trace directory)
	 */
	public static final String LOGS_PATH = "LOGS_PATH" ;

	//#CM31004
 	/**
	 * Defines the name of resource (file name of Send trace pointer)
	 */
	public static final String S_POINTER_NAME = "AS21_SEND_TRACE_POINTER_NAME" ;

	//#CM31005
 	/**
	 * Defines the name of resource (file name of Receive file pointer)
	 */
	public static final String R_POINTER_NAME = "AS21_RECEIVE_TRACE_POINTER_NAME" ;

	//#CM31006
 	/**
	 * Defines the name of resource (log-on the action of communication )
	 */
	public static final String A_LOG_ON = "AS21_ACTION_LOG_ON" ;

	//#CM31007
 	/**
	 * Defines the name of resource (file name of the operation log of communication)
	 */
	public static final String A_LOG_NAME = "AS21_ACTION_LOG_NAME" ;

	//#CM31008
 	/**
	 * Defines the name of resource (MAX. of the trace file size, in byte)
	 */
	public static final String MAX_SIZE = "TRACE_MAX_SIZE" ;

	//#CM31009
	/**
	 * Maximal value as a sequence number in communicating with AGC
	 */
	public static final int MAX_SEQ_NUMBER = 9999;

	//#CM31010
	/**
	 * Initial value as a sequesnce number in communicating with AGC
	 */
	public static final int INITIAL_NUMBER = 0;

	//#CM31011
	/**
	 * Start value of the loop as a sequence number in communicating with AGC
	 */
	public static final int LOOP_START_NUMBER = 1;

	//#CM31012
	// Class variables -----------------------------------------------
	//#CM31013
	/**
	 * Name of AGC server
	 */
	protected String host;

	//#CM31014
	/**
	 * AGC's server port
	 */
	protected int port;

	//#CM31015
	/**
	 * COnnection socket with AGC
	 */
	protected Socket socket = null ;

	//#CM31016
	/**
	 * Output data stream to AGC
	 */
	protected DataOutputStream wDataOut ;

	//#CM31017
	/**
	 * INput data stream from AGC
	 */
	protected DataInputStream  wDataIn ;

	//#CM31018
	/**
	 * Directory to record the trace file
	 */
	protected String LogDir = null ;

	//#CM31019
	/**
	 * Flag to indicate whether/not to record messages sent to AGC in the trace file
	 */
	protected boolean sTrOn = false ;

	//#CM31020
	/**
	 * Trace file of messages sent to AGC
	 */
	protected String sTrName = "sendTrace.txt" ;

	//#CM31021
	/**
	 * Log handler for the trace file of messages sent to AGC
	 */
	protected RandomAcsFileHandler sTrHandler = null;

	//#CM31022
	/**
	 * Flag to indicate whether/not to record messages recived from AGC in the trace file
	 */
	protected boolean rTrOn = false ;

	//#CM31023
	/**
	 * Trace file of messages received from AGC
	 */
	protected String rTrName = "receiveTrace.txt" ;

	//#CM31024
	/**
	 * Log handler for the trace file of messages received from AGC
	 */
	protected RandomAcsFileHandler rTrHandler = null;

	//#CM31025
	/**
	 * Parameter for the AGC operation log file 
	 */
	protected static Object[] aLogParam = new Object[1];

	//#CM31026
	/**
	 * Parameter for the Send trace file to AGC 
	 */
	protected static Object[] sTrcParam = new Object[1];

	//#CM31027
	/**
	 * Parameter for the Receive trace file with AGC
	 */
	protected static Object[] rTrcParam = new Object[1];

	//#CM31028
	/**
	 * Format the port number for the operation log file of AGC communication
	 */
	protected static DecimalFormat fmt = new DecimalFormat("000000");

	//#CM31029
	/**
	 * Buffer for detailed description of the opreration for the opration log of AGC communciation
	 */
	String action = ""; 

	//#CM31030
	/**
	 * Maximal size of the trace file (byte)
	 */
	protected int FileSize = 1000000 ;

	//#CM31031
	/**
	 * Size of a line in the trace file (byte)
	 */
	protected int LineLength = 512 ;

	//#CM31032
	// Class method --------------------------------------------------
     //#CM31033
     /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/13 08:30:58 $") ;
	}

	//#CM31034
	// Constructors --------------------------------------------------
	//#CM31035
	/**
	 * Prepares to establish the connection of AGC in lower class controller and TCP/IP.
	 * Behaves by default to connect with the host AGC 01, at port number 2000, as a client.
	 */
	public CommunicationAgc()
	{
		this(DEFAULT_NAME, DEFAULT_PORT) ;
	}

	//#CM31036
	/**
	 * Prepares to establish the connection of AGC in lower class controller and TCP/IP.
	 * It behaves as a cliant that connect with the specified Host, to the specified port number.
	 * @param host : name of the host connecting to
	 * @param port : port number of the connecting host
	 */
	public CommunicationAgc( String host, int port )
	{

		this.host = host;
		this.port = port;
		
		//#CM31037
		// Sets the directory to record trace file
		LogDir = CommonParam.getParam(LOGS_PATH);

		//#CM31038
		// Sets the MAX. of size of trace file
		FileSize = AsrsParam.TRACE_MAX_SIZE;
		//#CM31039
		// Sets the flag to indicate whether/not to record the sending message in trace file
		if( AsrsParam.AS21_SEND_TRACE_ON  )
		{
			//#CM31040
			// Sets the trace file which the sending message should be recorded
			sTrName = LogDir + host + AsrsParam.AS21_SEND_TRACE_NAME;
			sTrOn = true;
		}
		//#CM31041
		// Sets the flag to indicate whether/not to record the receiving message in trace file
		if( AsrsParam.AS21_RECEIVE_TRACE_ON ) 
		{
			//#CM31042
			// Sets the trace file which the receiving message should be recorded
			rTrName = LogDir + host + AsrsParam.AS21_RECEIVE_TRACE_NAME;
			rTrOn = true;
		}
	}

	//#CM31043
	// Public methods ------------------------------------------------
	//#CM31044
	/**
	 * Connects with AGC as a cliant 
	 * @return  : socket to the connecting AGC
	 * @throws IOException  : Notifies if file I/O error occured.
	 */
	public Socket connect() throws IOException 
	{
		socket = new Socket( host, port );
		wDataIn = new DataInputStream( new BufferedInputStream( socket.getInputStream() ) ) ;
		wDataOut = new DataOutputStream( new BufferedOutputStream( socket.getOutputStream() ) ) ;
		//#CM31045
		// Check whether/not the operation log to be recorded; records log accordingly.
		action = "AGC Connect HostName =" + host + " PortNumber = " + fmt.format(port) ;
		this.actionLogWrite(action);

		return( socket ) ;
	}

	//#CM31046
	/**
	 * Disconnect with AGC as a cliant.
	 * @throws IOException  : Notifies if file I/O error occured.
	 */
	public void disconnect() throws IOException 
	{
		socket.close();
	}

	//#CM31047
	/**
	 * Confirms that connection has comleted.
	 * @return  Returns 'true' if connection is complete.
	 */
	public boolean isConnected()
	{
		return(socket != null) ;
	}

	//#CM31048
	/**
	 * Returns input stream to AGC
	 * @return  DataInputStream to the TCP/IP Socket
	 */
	public DataInputStream getInStream() 
	{
		return( wDataIn ) ;
	}

	//#CM31049
	/**
	 * Returns output stream to AGC
	 * @return  DataOutputStream to the TCP/IP Socket
	 */
	public DataOutputStream getOutStream() 
	{
		return( wDataOut ) ;
	}

	//#CM31050
	/**
	 * Sends the communication message to AGC.
	 * @param	msg   : Communication message (ID, ID classification, MC send time, AGC send time, data)
	 * @param	seqNo : Sequesnce no. of the sent message
	 * @throws IOException  : Notifies if file I/O error occured.
	 */
	public void send(String msg, int seqNo) throws IOException 
	{
		StringBuffer wkstbuf = new StringBuffer();
		//#CM31051
		// Editing the Seq no.
		DecimalFormat fmt1 = new DecimalFormat(SEQ_FORMAT) ;
		String c_seqNo = fmt1.format(seqNo) ;

		//#CM31052
		// Sequense No. is merged with the communication message. 
		String wkstr = wkstbuf.append(c_seqNo).append(msg).toString();
		
		//#CM31053
		// Sending data must be Shift_JIS (according to the regulation of AS21 communication)
		byte[] s_byteMsg = wkstr.getBytes(CODE);

		//#CM31054
		// Creates Bcc.
		Bcc bcc = new Bcc();
		String sBcc = bcc.make(s_byteMsg, s_byteMsg.length);
		byte[] s_byteBcc = sBcc.getBytes(CODE);

		//#CM31055
		// Sends STX.
		wDataOut.writeByte( STX );
		//#CM31056
		// Sends the communication message body.
		wDataOut.write( s_byteMsg, 0, s_byteMsg.length);
		//#CM31057
		// Sends BCC.
		wDataOut.write( s_byteBcc, 0, s_byteBcc.length);
		//#CM31058
		// Sends ETX.
		wDataOut.writeByte( ETX );
		//#CM31059
		// Sends as an actual process
		wDataOut.flush();
		//#CM31060
		// Check whether/not the operation log to be recorded; records log accordingly
		action = "AGC Send ";
		this.actionLogWrite(action);
		//#CM31061
		// Check whether/not the receiving trace to be recorded; records trace accordingly.
		this.sendTraceWrite(wkstr);
	}

	//#CM31062
	/**
	 * Receive communication message from AGC.
	 * @return  Receiving message should be a message with STX, ETX and BCC omitted.
	 * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
	 * @throws IOException  : Notifies if file I/O error occured.
	 */
	public byte[] recv() throws InvalidProtocolException, IOException 
	{
		byte[] inByte = new byte[DEFAULT_LENGTH];
		byte ret = 0;
		int ii = 0;
		int rcvLength = 0;

		while( true )
		{
			ret = wDataIn.readByte();
			rcvLength++ ;
			if( ret == STX )
			{
				//#CM31063
				// Ignore message of STX only.
				 rcvLength = 1;
				 continue;
			} 
			else if ( ret == ETX )
			{
				if( rcvLength == 1 )
				{ 
				 	rcvLength = 0;
					continue;
				} 
				else 
				{
					if( rcvLength > MINIMAM_LENGTH ) 
					{
						break;
					} 
					//#CM31064
					// Break the message if its length is less than the pre-defined least message length.
					else 
					{
				 		rcvLength = 0;
				 		ii = 0;
						continue;
					}
				}
			} 
			//#CM31065
			// Receive normal message
			else 
			{
				if( rcvLength > DEFAULT_LENGTH )
				{ 
					//#CM31066
					// 6026059=database error occurred message={0}
					throw new InvalidProtocolException("6026059");
				} 
				else if( rcvLength != 1 )
				{
					 inByte[ii++] = ret;
					 continue;
				} 
				//#CM31067
				// Keep breaking until STX is received
				else 
				{
				 	rcvLength = 0;
					continue;
				}
			}
		}

		Bcc bcc = new Bcc();
		//#CM31068
		// Check the BCC value.
		boolean cBcc = bcc.check( inByte, ii );
		//#CM31069
		// Transfer the required message length to the different array; then convert to string
		//#CM31070
		// In so doing, omit BCC part. (2 byte portion)
		if( cBcc )
		{
			byte[] retbyte = new byte[ii];
			for(int jj=0; jj < ii-2 ; jj++)
			{
				retbyte[jj] = inByte[jj];
			}
			//#CM31071
			// Check whether/not the operation log to be recorded; records log accordingly
			action = "AGC Receive ";
			this.actionLogWrite(action);
			//#CM31072
			// Check whether/not the receiving trace to be recorded; records trace accordingly
			this.receiveTraceWrite(new String (retbyte) );
			return( retbyte );
		}
		else
		{
			throw new InvalidProtocolException("FTTB 6025131");
		}
	}

	//#CM31073
	/**
	 * Logging of operation over communication with AGC
	 * @param	Content of logging
	 */
	public void actionLogWrite(String action1)
	{
		aLogParam[0] = action1;
	}

	//#CM31074
	/**
	 * Trace the content sent to AGC
	 * @param	content of the trace
	 * @throws IOException  : Notifies if file I/O error occured.
	 */
	public void sendTraceWrite(String trdata) throws IOException
	{
		if( sTrOn && (sTrHandler == null) )
		{
			sTrHandler = new RandomAcsFileHandler(sTrName, FileSize) ;
		}
		if( !(sTrHandler == null))
		{
			synchronized ( sTrHandler )
			{
				sTrcParam[0] =  trdata;
				sTrHandler.write(0, LogMessage.F_INFO, "CommunicatinAgc", sTrcParam);
			}
		}
	}

	//#CM31075
	/**
	 * Trace the content received from AGC
	 * @param	content of the trace
	 * @throws IOException  : Notifies if file I/O error occured.
	 */
	public void receiveTraceWrite(String trdata) throws IOException
	{
		if( rTrOn && (rTrHandler == null) )
		{
			rTrHandler = new RandomAcsFileHandler(rTrName, FileSize) ;
		}
		if( !(rTrHandler == null))
		{
			synchronized ( rTrHandler )
			{
				rTrcParam[0] = trdata;
				rTrHandler.write(1, LogMessage.F_INFO, "CommunicatinAgc", rTrcParam);
			}
		}
	}

	//#CM31076
	// Package methods -----------------------------------------------

	//#CM31077
	// Protected methods ---------------------------------------------

	//#CM31078
	// Private methods -----------------------------------------------

}
//#CM31079
//end of class
