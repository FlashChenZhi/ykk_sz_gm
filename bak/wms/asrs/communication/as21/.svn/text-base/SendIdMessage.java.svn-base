// $Id: SendIdMessage.java,v 1.2 2006/10/26 00:53:53 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31643
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM31644
/**
 * This class provides the utility method which can be used to compose the common part of sending messages 
 * according to AS21 communication protocol. <BR>
 * Please use the subclasses of each ID for actual composition of the messages.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 00:53:53 $
 * @author  $Author: suresh $
 */
public abstract class SendIdMessage extends IdMessage
{
	//#CM31645
	// Class fields --------------------------------------------------
	//#CM31646
	/**
	 * BC data (blank)
	 */
	 protected static final String BLANK_BCDATA = "                              " ;

	//#CM31647
	/**
	 * Control data (blank)
	 */
	 protected static final String BLANK_CONTROL_INFO = "                              " ;

	//#CM31648
	// Class variables -----------------------------------------------

	//#CM31649
	// Class method --------------------------------------------------
     //#CM31650
     /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 00:53:53 $") ;
	}

	//#CM31651
	// Constructors --------------------------------------------------
	//#CM31652
     /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public SendIdMessage()
	{
		super() ;
	}

	//#CM31653
	// Public methods ------------------------------------------------
	//#CM31654
	/**
	 * Get the communication message to send. The detail needs to be implemented under each subclass.
	 * @return    Communication message composed
	 * @throws  InvalidProtocolException : Notifies if there is any data violating the protocol regulations
	 */
	public abstract String getSendMessage() throws InvalidProtocolException ;

	//#CM31655
	// Package methods -----------------------------------------------

	//#CM31656
	// Protected methods ---------------------------------------------
	//#CM31657
	/**
	 * Adjust the message length to the size specified.
	 * Cut off the original message if its length is over sthe specified size. If shorter, use space to fill
	 * to the required length.
	 * @param   src   oroginal message
	 * @param   len   Specifying the length of the string
	 * @return  String after adjustment
	 */
	protected String operateMessage(String src, int len)
	{
		int  dif = len - src.length() ;
		if (dif <= 0)
		{
			//#CM31658
			// src too long
			return (src.substring(0, len)) ;
		}
		else
		{
			//#CM31659
			// src too short
			byte[]  wb = new byte[dif] ;
			for (int i=0; i < dif; i++)
			{
				wb[i] = ' ' ;
			}
			return (src + new String(wb)) ;
		}
	}

	//#CM31660
	/**
	 * Set the src contents to the offset which is indicated by 'off' in the byte array of buffer
	 * @param  buff : buffer to be set
	 * @param  off  : offset the buffer to be set
	 * @param  src  : data to set
	 * @param  len  : length of the data to set
	 */
	protected void setByteArray(byte[] buff, int off, byte[] src, int len)
	{
		for (int i=0; i < len; i++)
		{
			buff[off + i] = src[i] ;
		}
	}

	//#CM31661
	// Private methods -----------------------------------------------
}
//#CM31662
//end of class

