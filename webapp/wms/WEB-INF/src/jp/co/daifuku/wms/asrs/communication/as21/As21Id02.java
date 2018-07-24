// $Id: As21Id02.java,v 1.2 2006/10/26 01:41:02 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM28744
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.common.DateOperator;
import jp.co.daifuku.common.InvalidProtocolException;

//#CM28745
/**
 * Composes communication message "Data of date and time) ID=02" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:41:02 $
 * @author  $Author: suresh $
 */
public class As21Id02 extends SendIdMessage
{
	//#CM28746
	// Class fields --------------------------------------------------
	//#CM28747
	/**
	 * Length of the date
	 */
	protected static final int DATE=8;

	//#CM28748
	/**
	 * Length of the time
	 */	
	protected static final int TIME=6;

	//#CM28749
	/**
	 * Data length of each date and time in the communication message
	 */	
	protected static final int LEN_TOTAL=DATE+TIME;

	//#CM28750
	// Class variables -----------------------------------------------

	//#CM28751
	// Class method --------------------------------------------------
	//#CM28752
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:41:02 $") ;
	}

	//#CM28753
	// Constructors --------------------------------------------------
	//#CM28754
	/**
	 * Constructor
	 */
	public As21Id02 ()
	{
		super();
	}

	//#CM28755
	// Public methods ------------------------------------------------
	//#CM28756
	/**
	 * Creates the communication message of date and time data.
	 * <p><code>
	 * Communication message should contain the following;<br>
	 * <table>
	 * <tr><td>date</td><td>8 byte</td></tr>
	 * <tr><td>time</td><td>6 byte</td></tr>
	 * </table></code></p>
	 * @return	  date and time Data
	 * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM28757
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM28758
		//-------------------------------------------------
		//#CM28759
		// Attention: the order of contents must be observed!
		//#CM28760
		//-------------------------------------------------
		//#CM28761
		// Date
		mbuf.append(getDate()) ;
		//#CM28762
		// Time
		mbuf.append(getTime()) ;
		//#CM28763
		//-------------------------------------------------
		//#CM28764
		// Sets as sending message buffer
		//#CM28765
		//-------------------------------------------------
		//#CM28766
		// id
		setID("02") ;
		//#CM28767
		// id segment
		setIDClass("00") ;
		//#CM28768
		// time sent
		setSendDate() ;
		//#CM28769
		// time sent to AGC
		setAGCSendDate("000000") ;
		//#CM28770
		// contents of text
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL+OFF_CONTENT)) ;
	}

	//#CM28771
	/**
	 * Creates the date.
	 * @return    year.month.day
	 */
	public String getDate()
	{
		return(DateOperator.getSysdate());
	}
	
	//#CM28772
	/**
	 * Creates time.
	 * @return    hour.minute. second
	 */
	public String getTime()
	{
		
		return(DateOperator.getSysdateTime().substring(8,14));
	}
	//#CM28773
	// Package methods -----------------------------------------------

	//#CM28774
	// Protected methods ---------------------------------------------

	//#CM28775
	// Private methods -----------------------------------------------

}
//#CM28776
// end of class
