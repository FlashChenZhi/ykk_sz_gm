// $Id: As21Id03.java,v 1.2 2006/10/26 01:41:01 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM28777
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM28778
/**
 * Composes communication message "Request for cork completion ID=03" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:41:01 $
 * @author  $Author: suresh $
 */
public class As21Id03 extends SendIdMessage
{
	//#CM28779
	// Class fields --------------------------------------------------
	//#CM28780
	/**
	 * Field of request segment (normal termination)
	 */
	public static final String GENERAL_END = "0";
	
	//#CM28781
	/**
	 * Field of request segment (forced termination 1)
	 */
	public static final String EXTRAORDINARY_END_ONE = "1";
	
	//#CM28782
	/**
	 * Field of request segment (forced termination 2)
	 */
	public static final String EXTRAORDINARY_END_TWO = "2";
	
	//#CM28783
	/**
	 * Length of request classification
	 */
	public static final int REQUEST_CLASSIFICATION = 1;
	
	//#CM28784
	/**
	 * Length of communication message data
	 */
	protected final int LEN_TOTAL = REQUEST_CLASSIFICATION;

	//#CM28785
	// Class variables -----------------------------------------------
	//#CM28786
	/**
	 * Variable that preserves the request classification
	 */
	public String wReqinfo;
	
	//#CM28787
	// Class method --------------------------------------------------
	//#CM28788
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:41:01 $");
	}

	//#CM28789
	// Constructors --------------------------------------------------
	//#CM28790
	/**
	 * Constructor
	 */
	public As21Id03 ()
	{
		super();
	}
	
	//#CM28791
	// Public methods ------------------------------------------------
	//#CM28792
	/**
	 * Creates the communication message requesting the work to terminate.
	 * <p><code>
	 * Communication message should contain the following;<br>
	 * <table>
	 * <tr><td>request classification</td><td>1 byte</td></tr>
	 * </table></code></p>
	 * @return    communication Message of work completion
	 * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
	 */	 
	public String getSendMessage()throws InvalidProtocolException
	{
		//#CM28793
		// Text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM28794
		//-------------------------------------------------
		//#CM28795
		// Attention! The order of contents must be observed!
		//#CM28796
		//-------------------------------------------------
		//#CM28797
		// Request classfication
		mbuf.append(wReqinfo) ;
		
		//#CM28798
		//-------------------------------------------------
		//#CM28799
		// Sets as sending message buffer
		//#CM28800
		//-------------------------------------------------
		//#CM28801
		// id
		setID("03");
		//#CM28802
		// id segment
		setIDClass("00");
		//#CM28803
		// time sent
		setSendDate();
		//#CM28804
		// time sent to AGC
		setAGCSendDate("000000");
		//#CM28805
		// contents of text
		setContent(mbuf.toString());
		
		return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
	}
	//#CM28806
	// Package methods -----------------------------------------------

	//#CM28807
	// Protected methods ---------------------------------------------

	//#CM28808
	// Private methods -----------------------------------------------
	//#CM28809
	/**
	 * Sets the termination of work.
	 * @param <code>reqinfo</code> request classification<BR>
	 *		0:forced termination (terminated with no remaining job)<BR>
	 *      1:forced termination 1 (terminated regardless of remaining jobs)<BR>
	 *      2:forced termination 2 (terminated ; having deleted any remaining jobs)<BR>
	 * @throws InvalidProtocolException : Reports if entered request classification is invalid.
	 */
	public void setRequestClass(String reqinfo) throws InvalidProtocolException
	{
		wReqinfo = reqinfo;
		if(wReqinfo == GENERAL_END)
		{
			//#CM28810
			// Normal termination
		}
		else if(wReqinfo == EXTRAORDINARY_END_ONE)
		{
			//#CM28811
			// Forced termination 1
		}
		else if(wReqinfo == EXTRAORDINARY_END_TWO)
		{
			//#CM28812
			// Forced termination 2
		}
		else
		{
			//#CM28813
			// Entered request classification is invalid.
			throw new InvalidProtocolException("\n" + "Exeption. Request classification = " + wReqinfo) ;
		}
	}
}
//#CM28814
// end of class
