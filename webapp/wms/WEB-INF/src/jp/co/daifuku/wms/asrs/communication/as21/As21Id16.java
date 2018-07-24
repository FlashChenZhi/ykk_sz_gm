// $Id: As21Id16.java,v 1.2 2006/10/26 01:37:46 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29214
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29215
/**
 *Composes communication message "command for overall start/stop, ID=16" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:37:46 $
 * @author  $Author: suresh $
 */
public class As21Id16 extends SendIdMessage
{

	 //#CM29216
	 //Class fields --------------------------------------------------
	//#CM29217
	/**
	 * Field of all start/all stop calssification (all start)
	 */
	public static final String ALLMOVE				= "1" ;

	//#CM29218
	/**
	 * Field of all start/all stop calssification (all stop)
	 */
	public static final String ALLSTOP				= "2" ;

	//#CM29219
	/**
	 * Length of start/stop calssification
	 */
	public static final int LEN_MOVE_STOP_CLASS			=  1;

	//#CM29220
	/**
	 * Length of communication message
	 */
	protected final int LEN_TOTAL = OFF_CONTENT 
								+LEN_MOVE_STOP_CLASS;

	//#CM29221
	// Class variables -----------------------------------------------
	//#CM29222
	/**
	 * Declares the boolean oriented wboo variables.
	 */
	private boolean  wboo ;

	//#CM29223
	// Class method --------------------------------------------------
	//#CM29224
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:37:46 $") ;
	}

	//#CM29225
	// Constructors --------------------------------------------------
	//#CM29226
	/**
	 * Default constructor
	 */
	public As21Id16()
	{
	}

	//#CM29227
	// Public methods ------------------------------------------------
	//#CM29228
	/**
	 * Generates instance of all start/all stop class.
	 * @param  b  contains information related to start/stop segments
	 */
	public As21Id16(boolean b)
	{
		super() ;
		setBoo(b);
	}

	//#CM29229
	/**
	 * Creates communication message directing to all start/ all stop.
	 * @return    Direction message to all start/ all stop
	 */
	public String getSendMessage() 
	{
		//#CM29230
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM29231
		// Start/stop classification
		mbuf.append(getMovestopclass(wboo)) ;
	 
		//#CM29232
		//-------------------------------------------------
		//#CM29233
		// Setting for sending message buffer
		//#CM29234
		//-------------------------------------------------
		//#CM29235
		// id
		setID("16") ;
		//#CM29236
		// id segment
		setIDClass("00") ;
		//#CM29237
		// time sent
		setSendDate() ;
		//#CM29238
		// time sent to AGC
		setAGCSendDate("000000") ;
		//#CM29239
		// contents of text
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL)) ;
	}

	//#CM29240
	/**
	 * Sets start/stop classification.
	 * @param st  Returns 'TRUE' for all start, 'FALSE' for all stop.
	 * @return start/stop classification
	 */
	public String getMovestopclass(boolean st) 
	{

		String movestopclass = "";

		if(st == true)
		{
			 movestopclass = ALLMOVE;
		}
		else
		{
			 movestopclass = ALLSTOP;
		}
		return(movestopclass);
		
	}

	//#CM29241
	/**
	 * Sets value of Boo	
	 * @param b  information related with start/stop classification
	 */
	private void setBoo(boolean b)
	{
		wboo = b;
	}

	//#CM29242
	// Package methods -----------------------------------------------

	//#CM29243
	// Protected methods ---------------------------------------------

	//#CM29244
	// Private methods -----------------------------------------------
}
//#CM29245
//end of class
