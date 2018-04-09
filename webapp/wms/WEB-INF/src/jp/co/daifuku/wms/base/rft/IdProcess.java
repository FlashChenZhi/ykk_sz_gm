// $Id: IdProcess.java,v 1.2 2006/11/14 06:09:08 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701821
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

//#CM701822
/**
 * A super-class to process information received from RFT. <BR>
 * It is necessary to describe the processing of each Id in <code>processReceivedId() </code> Method. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:08 $
 * @author  $Author: suresh $
 */
public abstract class IdProcess
{
	//#CM701823
	// Class fields----------------------------------------------------

	//#CM701824
	// Class variables -----------------------------------------------
	//#CM701825
	/**
	 * Maintain the Data base connection. 
	 */
	protected Connection wConn;

	//#CM701826
	// Class method --------------------------------------------------
	//#CM701827
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/14 06:09:08 $");
	}

	//#CM701828
	// Constructors --------------------------------------------------

	//#CM701829
	// Protected methods -----------------------------------------------
	//#CM701830
	/**
	 * Method which described the flow of processing. <BR><code>processReceivedId() </code> Method 
	 * should be overridden to detailed processing of each Id.
	 * @param	rdt	Receiving telegram
	 * @param	sdt	Transmission telegram
	 * @throws  Exception  When something abnormality occurs. 
	 */
	public abstract void processReceivedId(byte[] rdt, byte[] sdt)
		throws Exception;

	//#CM701831
	/**
	 * Set DB Connection. 
	 * 
	 * @param connection Database connection
	 */
	public void setConnection(Connection connection)
	{
		wConn = connection;
	}

	//#CM701832
	// Private methods -----------------------------------------------
}
//#CM701833
//end of class
