// $Id: As21Sender.java,v 1.3 2006/11/13 08:28:21 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30513
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import jp.co.daifuku.rmi.RmiServAbstImpl;


//#CM30514
/**
 * This task conducts sending of communication messages to AGC. Practically, it will
 * be registered in RMI server then called by remote.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/13 08:28:21 $
 * @author  $Author: suresh $
 */
public class As21Sender extends RmiServAbstImpl
{
	//#CM30515
	// Class fields --------------------------------------------------
	//#CM30516
	/**
	 * Name of the object which bind to remote object.
	 */
	public static final String OBJECT_NAME = "AGC" ;

	//#CM30517
	// Class variables -----------------------------------------------
	//#CM30518
	/**
	 * Variables which preserves the reference to Utility class for the connection with AGC.
	 */
	private CommunicationAgc wAgc = null;

	//#CM30519
	/**
	 * Sequence Number for the communication with AGC
	 */
	private int wSeqNum = CommunicationAgc.INITIAL_NUMBER;

	//#CM30520
	/**
	 * Variables which performs the stop control over the communication with AGC
	 */
	//private  boolean thStop = false;

	//#CM30521
	// Class method --------------------------------------------------
 	//#CM30522
 	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/13 08:28:21 $") ;
	}

	//#CM30523
	// Constructors --------------------------------------------------
	//#CM30524
	/**
	 * Sets the reference to the Unitility class for the connection with AGC; then implements the initialization of this class.
	 * @param agc         : reference to the <code>CommunicationAgc</code>, which is the Unitility class for the connection with AGC 
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public As21Sender(CommunicationAgc agc) throws java.rmi.RemoteException
	{
		//#CM30525
		// Sets the reference to the Utility class for the connection with AGC.
		wAgc = agc;
	}

	//#CM30526
	/**
	 * Be called by CarryInformationController, then sends messages to AGC.
	 * @param params Sending message to AGC
	 * @throws IOException            : Notifies if file I/O error occured.
	 */
	public void write(Object[] params) throws IOException
	{
		//#CM30527
		//If the connection with AGC is already established,
		if (wAgc.isConnected()) 
		{
			String text = params[0].toString();
			synchronized (wAgc)
			{
				wAgc.send( text, wSeqNum);
				wSeqNum++;
				if( wSeqNum > CommunicationAgc.MAX_SEQ_NUMBER ) wSeqNum = CommunicationAgc.LOOP_START_NUMBER;
			}
		}
		else
		{
			throw (new IOException("Connection Refuesed : " + wAgc.host)) ;
		}
	}

	//#CM30528
	/**
	 * This method is not for use. This is prepared in order to provisionally implement the method
	 * declared in upper class.
	 */
	public synchronized void stop()
	{
	}

	//#CM30529
	// Package methods -----------------------------------------------

	//#CM30530
	// Protected methods ---------------------------------------------

	//#CM30531
	// Private methods -----------------------------------------------

}
//#CM30532
//end of class
