// $Id: As21Id10.java,v 1.2 2006/10/26 01:41:00 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM28993
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.entity.CarryInformation;

//#CM28994
/**
 * Composes "Request for machine status, ID=10" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:41:00 $
 * @author  $Author: suresh $
 */
public class As21Id10 extends SendIdMessage
{
	//#CM28995
	// Class fields --------------------------------------------------
	//#CM28996
	/**
	 * Length of each communication message requesting for respective machine status
	 */
	protected static final int LEN_TOTAL=0;

	//#CM28997
	// Class variables -----------------------------------------------
    //#CM28998
    /**
	 * Variable which preserves <code>CarryInformation</code> with carry information.
	 */
	protected CarryInformation wCarryInfo ;

	//#CM28999
	// Class method --------------------------------------------------
	//#CM29000
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:41:00 $") ;
	}

	//#CM29001
	// Constructors --------------------------------------------------
	//#CM29002
	/**
	 * Default constructor
	 */
	public As21Id10 ()
	{
		super();
	}
	
	//#CM29003
	// Public methods ------------------------------------------------
	//#CM29004
	/**
	 * Creates the request for the status of machine.<BR>
	 * @return    request for the status of machine
	 */
	public String getSendMessage() 
	{
		//#CM29005
		//-------------------------------------------------
		//#CM29006
		// Sets as sending message buffer
		//#CM29007
		//-------------------------------------------------
		//#CM29008
		// id
		setID("10") ;
		//#CM29009
		// id segmentaion
		setIDClass("00") ;
		//#CM29010
		// time sent
		setSendDate() ;
		//#CM29011
		// time sent to AGC
		setAGCSendDate("000000") ;
		return (getFromBuffer(0, LEN_TOTAL+OFF_CONTENT)) ;
	}

	//#CM29012
	// Package methods -----------------------------------------------

	//#CM29013
	// Protected methods ---------------------------------------------

	//#CM29014
	// Private methods -----------------------------------------------

}
//#CM29015
//end of class
