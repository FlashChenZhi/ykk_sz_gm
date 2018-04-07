// $Id: IdMessage.java,v 1.2 2006/10/26 00:56:49 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31336
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

//#CM31337
/**
 * This is the superclass provides the utility method which composes and/or decompose the common parts of 
 * communication messages according to AS21 communication protocol. 
 * Please use subclasses of each ID for the actual compositions and/or decompositions of messages.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 00:56:49 $
 * @author  $Author: suresh $
 */
public class IdMessage extends Object
{
	//#CM31338
	// Class fields --------------------------------------------------
	//#CM31339
	/**
	 * Definition of the offset of communcation message ID
	 */
	static final int OFF_ID = 0 ;

	//#CM31340
	/**
	 * Length of the communication message (byte)
	 */
	static final int LEN_ID = 2 ;

	//#CM31341
	/**
	 * Definition of offset of communication ID segments
	 */
	static final int OFF_IDCLASS = OFF_ID + LEN_ID ;

	//#CM31342
	/**
	 * Length of communication message ID segment(byte)
	 */
	static final int LEN_IDCLASS = 2 ;

	//#CM31343
	/**
	 * Definition of offset of MC send time
	 */
	static final int OFF_SENDDATE = OFF_IDCLASS + LEN_IDCLASS ;

	//#CM31344
	/**
	 * Length of the MC send time (byte)
	 */
	static final int LEN_SENDDATE = 6 ;

	//#CM31345
	/**
	 * Definition of offset of AGCSEND send time
	 */
	static final int OFF_AGCSENDDATE = OFF_SENDDATE + LEN_SENDDATE ;

	//#CM31346
	/**
	 * Length of AGCSEND send time (byte)
	 */
	static final int LEN_AGCSENDDATE = 6 ;

	//#CM31347
	/**
	 * Definition of offset of the content 
	 */
	static final int OFF_CONTENT = OFF_AGCSENDDATE + LEN_AGCSENDDATE ;

	//#CM31348
	/**
	 * Maximum length of data
	 */
	static final int LEN_MAX_CONTENT = 488 ;

	//#CM31349
	/**
	 * Maximum length of the communication message (except for STX, ETX, and BCC)
	 */
	static final int LEN_MAX_PACKET = 512 - 4 ;

	//#CM31350
	// Class variables -----------------------------------------------
	//#CM31351
	/**
	 * Communication message buffer
	 */
	protected byte[] wDataBuffer = new byte[LEN_MAX_PACKET] ;

	//#CM31352
	/**
	 * Definition of date format
	 */
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HHmmss") ;

	//#CM31353
	// Class method --------------------------------------------------
     //#CM31354
     /**
	 * Returns the version of this class
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 00:56:49 $") ;
	}

	//#CM31355
	// Constructors --------------------------------------------------
	//#CM31356
	/**
	 * Create instance. Internal buffer can be cleared by ' '.                                                                                                                                               
	 */
	public IdMessage()
	{
		//#CM31357
		// cleanup buffer
		for (int i=0; i < wDataBuffer.length; i++)
		{
			wDataBuffer[i] = ' ' ;
		}
	}

	//#CM31358
	/**
	 * Set the received message from AGC to internal buffer and generate the instance.
	 * @param  dt   Received text buffer to set
	 */
	public IdMessage(byte[] dt)
	{
		super() ;
		setReceiveMessage(dt) ;
	}

	//#CM31359
	// Public methods ------------------------------------------------
	//#CM31360
	/**
	 * Get the ID.
	 * @return   communication message ID
	 */
	public String getID()
	{
		return (getFromBuffer(OFF_ID, LEN_ID)) ;
	}

	//#CM31361
	/**
	 * Get the ID segment
	 * @return communication message ID segment
	 */
	public String getIDClass()
	{
		return (getFromBuffer(OFF_IDCLASS, LEN_IDCLASS)) ;
	}

	//#CM31362
	/**
	 * Get the send time.
	 * @return send time
	 */
	public Date getSendDate()
	{
		String wdt = getFromBuffer(OFF_SENDDATE, LEN_SENDDATE) ;
		return (DATE_FORMAT.parse(wdt, new ParsePosition(0))) ;
	}

	//#CM31363
	/**
	 * Get AGC send time
	 * @return AGC send time
	 */
	public Date getAGCSendDate()
	{
		String wdt = getFromBuffer(OFF_AGCSENDDATE, LEN_AGCSENDDATE) ;
		return (DATE_FORMAT.parse(wdt, new ParsePosition(0))) ;
	}

	//#CM31364
	// Package methods -----------------------------------------------

	//#CM31365
	// Protected methods ---------------------------------------------
	//#CM31366
	/**
	 * Set ID.
	 * @param id   communicatin message ID
	 */
	protected void setID(String id)
	{
		setToBuffer(id, OFF_ID) ;
	}

	//#CM31367
	/**
	 * Set the ID segmet.
	 * @param idclass   ID segment of the communication message
	 */
	protected void setIDClass(String idclass)
	{
		setToBuffer(idclass, OFF_IDCLASS) ;
	}

	//#CM31368
	/**
	 * Set the send time. Current time is used.
	 */
	protected void setSendDate()
	{
		setSendDate(new Date()) ;
	}

	//#CM31369
	/**
	 * Set send time.
	 * @param  sdate Time to set
	 */
	protected void setSendDate(Date sdate)
	{
		String wdt = DATE_FORMAT.format(sdate) ;
		setToBuffer(wdt, OFF_SENDDATE) ;
	}

	//#CM31370
	/**
	 * Set the AGC send time. Current time is used.
	 * This part will have no meaning in send text.
	 */
	protected void setAGCSendDate()
	{
		setAGCSendDate(new Date()) ;
	}

	//#CM31371
	/**
	 * Set the AGC send time. 
	 * his part will have no meaning in send text.
	 * @param  adate time to set
	 */
	protected void setAGCSendDate(Date adate)
	{
		String wdt = DATE_FORMAT.format(adate) ;
		setToBuffer(wdt, OFF_AGCSENDDATE) ;
	}

	//#CM31372
	/**
	 * Set the AGC send time. 
	 * his part will have no meaning in send text.
	 * @param  st time to set
	 */
	protected void setAGCSendDate(String st)
	{
		setToBuffer(st, OFF_AGCSENDDATE) ;
	}

	//#CM31373
	/**
	 * Set the cotent of the communication message.
	 * Check of the contents is not done.
	 * @param content  Communication message to set
	 */
	protected void setContent(String content)
	{
		setToBuffer(content, OFF_CONTENT) ;
	}

	//#CM31374
	/**
	 * Get the content of communication message
	 * Check of the content is not done.
	 * @return content of received message
	 */
	protected String getContent()
	{
		int wlen = wDataBuffer.length - OFF_CONTENT ;
		return (getFromBuffer(OFF_CONTENT, wlen)) ;
	}

	//#CM31375
	/**
	 * Set data to the internal buffer
	 * @param  src   data to set
	 * @param  offset   offset the buffer to set
	 */
	protected void setToBuffer(String src, int offset)
	{
		byte[] wkb = src.getBytes() ;
		for (int i=0; i < wkb.length ; i++)
		{
			wDataBuffer[i+offset] = wkb[i] ;
		}
	}

	//#CM31376
	/**
	 * Set the communication message received from AGC to the internal buffer
	 * @param  rmsg   communication message
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		wDataBuffer = rmsg ;
	}

	//#CM31377
	// Private methods -----------------------------------------------
	//#CM31378
	/**
	 * Get data from internal buffer
	 * @param  offset  Offset of data getting from buffer
	 * @param  len     Length of data getting from buffer (byte)
	 */
	protected String getFromBuffer(int offset, int len)
	{
		return (new String(wDataBuffer, offset, len)) ;
	}

}
//#CM31379
//end of class
