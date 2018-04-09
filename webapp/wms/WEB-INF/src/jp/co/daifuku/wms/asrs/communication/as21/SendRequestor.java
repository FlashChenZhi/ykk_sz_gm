// $Id: SendRequestor.java,v 1.2 2006/10/26 00:53:27 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31663
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.common.LogMessage;


//#CM31664
/**
 * Designer : K.Mori <BR>
 * Maker : K.Mori <BR>
 * <BR>
 * Using respective Sender class of StorageSender,RetrievalSender,AutomaticModeChangeSender,
 * Carry instruction is sent using RMI message. For AS/RSStorage Setting, Picking Setting Process,
 * if the schedule executes normally, use this class to send instruction request.
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 00:53:27 $
 * @author  $Author: suresh $
 */
public class SendRequestor extends Object
{
	//#CM31665
	// Class fields --------------------------------------------------

	//#CM31666
	// Class variables -----------------------------------------------
	//#CM31667
	/**
	 *used in LogWrite in case of exception
	 */
	public StringWriter wSW = new StringWriter();

	//#CM31668
	/**
	 *used in LogWrite in case of exception
	 */
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM31669
	// Class method --------------------------------------------------
	//#CM31670
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 00:53:27 $") ;
	}

	//#CM31671
	// Constructors --------------------------------------------------
	//#CM31672
	/**
	 * default constructor
	 */
	public SendRequestor()
	{
	}

	//#CM31673
	// Public methods ------------------------------------------------
	//#CM31674
	/**
	 * conveyance instruction process request
	 * call the write method of the following Sender class through RMI
	 * jp.co.daifuku.wms.asrs.communication.as21.StorageSender class
	 * jp.co.daifuku.wms.asrs.communication.as21.AutoMaticModeChangeSender class
	 */
	public void storage()
	{
		try
		{
			RmiSendClient rmiSndC = new RmiSendClient();
			Object[] param = new Object[2];
			param[0] = null;
	
			//#CM31675
			// call write method of AutomaticModeChangeSender
			rmiSndC.write( "AutomaticModeChangeSender", param );
	
			//#CM31676
			// call write method of StorageSender
			rmiSndC.write( "StorageSender", param );
	
			rmiSndC = null;
			param[0] = null ;
		}
		catch(java.lang.Exception e)
		{
			//#CM31677
			// 6026069=Could not submit the send request to the transfer instruction process.
			e.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = wSW.toString() ;
			RmiMsgLogClient.write(6026069, LogMessage.F_ERROR, this.getClass().getName(), tObj);
		}
	}

	//#CM31678
	/**
	 * picking instruction process request
	 * call the write method of the following Sender class through RMI
	 * jp.co.daifuku.wms.asrs.communication.as21.RetrievalSender class
	 * jp.co.daifuku.wms.asrs.communication.as21.AutoMaticModeChangeSender class
	 */
	public void retrieval()
	{
		try
		{
			RmiSendClient rmiSndC = new RmiSendClient();
			Object[] param = new Object[2];
			param[0] = null;
	
			//#CM31679
			// call write method of AutomaticModeChangeSender
			rmiSndC.write( "AutomaticModeChangeSender", param );
	
			//#CM31680
			// call write method of RetrievalSender
			rmiSndC.write( "RetrievalSender", param );
	
			rmiSndC = null;
			param[0] = null ;
		}
		catch(java.lang.Exception e)
		{
			//#CM31681
			// 6026069=Could not submit the send request to the transfer instruction process.
			e.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = wSW.toString() ;
			RmiMsgLogClient.write(6026069, LogMessage.F_ERROR, this.getClass().getName(), tObj);
		}
	}

	//#CM31682
	// Package methods -----------------------------------------------

	//#CM31683
	// Protected methods ---------------------------------------------

	//#CM31684
	// Private methods -----------------------------------------------

}
//#CM31685
//end of class

