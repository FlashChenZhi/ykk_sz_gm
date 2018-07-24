// $Id: As21Id01.java,v 1.2 2006/10/26 01:41:02 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;


//#CM28711
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.common.DateOperator;
import jp.co.daifuku.common.InvalidProtocolException;

//#CM28712
/**
 * Composes communication message "Request of work start ID=01" according to AS21 communication protocol.
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
public class As21Id01 extends SendIdMessage
{
	//#CM28713
	// Class fields --------------------------------------------------
	//#CM28714
	/**
	 * Length of date 
	 */
	protected static final int DATE=8;
	
	//#CM28715
	/**
	 * Length of time 
	 */
	protected static final int TIME=6;
	
	//#CM28716
	/**
	 * Length of message contents
	 */
	protected final int LEN_TOTAL=DATE+TIME;

	//#CM28717
	// Class variables -----------------------------------------------
	
	//#CM28718
	// Class method --------------------------------------------------
	//#CM28719
	/**
	 * Returns the version of this class.
	 * @return Version adn the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:41:02 $") ;
	}

	//#CM28720
	// Constructors --------------------------------------------------
	//#CM28721
	/**
	 * Constructor
	 */
	public As21Id01 ()
	{
		super();
	}

	//#CM28722
	// Public methods ------------------------------------------------
	//#CM28723
	/**
	 * Creates the communication message reqesting the work to start.
	 * <p><code>
	 * Communication message should contain the following;<br>
	 * <table>
	 * <tr><td>date</td><td>8 byte</td></tr>
	 * <tr><td>time</td><td>6 byte</td></tr>
	 * </table></code></p>
	 * @return    Message requesting the work start
	 * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM28724
		// Text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM28725
		//-------------------------------------------------
		//#CM28726
		// Attention: the order of contents must be observed!
		//#CM28727
		//-------------------------------------------------
		//#CM28728
		// Date
		mbuf.append(getDate());
		//#CM28729
		// Time
		mbuf.append(getTime());

		//#CM28730
		//-------------------------------------------------
		//#CM28731
		// Sets up as sending message buffer
		//#CM28732
		//-------------------------------------------------
		//#CM28733
		// id
		setID("01") ;
		//#CM28734
		// id segment
		setIDClass("00") ;
		//#CM28735
		// time sent
		setSendDate() ;
		//#CM28736
		// sent time to AGC
		setAGCSendDate("000000") ;
		//#CM28737
		// contents of text
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL+OFF_CONTENT)) ;
	}
	//#CM28738
	// Package methods -----------------------------------------------

	//#CM28739
	// Protected methods ---------------------------------------------

	//#CM28740
	// Private methods -----------------------------------------------
	//#CM28741
	/**
	 * Requests current date (year, month, date)
	 * @return year.month. date
	 */
	private String getDate()
	{
		return(DateOperator.getSysdate());
	}

	//#CM28742
	/**
	 * Requests current hour, minute, second
	 * @return time.minute. second
	 */
	private String getTime()
	{
		return(DateOperator.getSysdateTime().substring(8,14));
	}

}
//#CM28743
// end of class
