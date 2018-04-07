//#CM695531
//$Id: AbstractExternalReportDataCreator.java,v 1.2 2006/11/13 06:03:15 suresh Exp $

//#CM695532
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.Parameter;

//#CM695533
/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai <BR>
 * <BR>
 * Allow this abstract <CODE>AbstractExternalReportDataCreator</CODE> class to execute the process for reporting data.<BR>
 * Implement the <CODE>ExternalReportDataCreator</CODE> interface and also implement the process required to implement this interface.<BR>
 * Allow this class to implement the common method. Allow the class that inherits this class to implement the individual behavior of the actual maintenance process.<BR>
 * <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/02</TD><TD>T.Nakai</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:15 $
 * @author  $Author: suresh $
 */
public abstract class AbstractExternalReportDataCreator implements ExternalReportDataCreator
{

	//#CM695534
	// Class fields --------------------------------------------------

	//#CM695535
	// Class variables -----------------------------------------------
	//#CM695536
	/**
	 * Connection for database connection<BR>
	 */
	protected Connection wConn;

	//#CM695537
	/**
	 * Area to maintain the message<BR>
	 * Use this to maintain the contents when condition error etc occurs by invoking a method.
	 */
	private String wMessage;

	//#CM695538
	/**
	 * Report Data Count<BR>
	 */
	private int wReportCount = 0;

	//#CM695539
	// Class method --------------------------------------------------
	//#CM695540
	/**
	 * Return the version of this class.<BR>
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:15 $");
	}

	//#CM695541
	// Constructors --------------------------------------------------

	//#CM695542
	// Public methods ------------------------------------------------
	//#CM695543
	/**
	 * Obtain the count of report data.<BR>
	 * @return  Count of Added Data
	 */
	public int getReportCount()
	{
		return wReportCount;
	}

	//#CM695544
	/**
	 * Set the Report Data Count.<BR>
	 * @param reportcnt Value to be set for Count of Report Data
	 */
	public void setReportCount(int reportcnt)
	{
		wReportCount = reportcnt;
	}

	//#CM695545
	/**
	 * If <CODE>report()</CODE> method returns false,<BR>
	 * obtain a message to display the reason.<BR>
	 * Use this method to obtain the content to be displayed in the Message area on the screen.
	 */
	public String getMessage()
	{
		return wMessage;
	}

	//#CM695546
	/**
	 * Execute the process for reporting data.<BR>
	 * Allow the class that inherits the <code>AbstractExternalReportDataCreator</code> class to implement this method because the process for reporting data depends on the package.
	 * Return true if succeeded in checking for parameter or
	 * return false if failed.
	 * If failed to check for parameter, obtain the reason by means of <code>getMessage</code>.
	 * @param conn Database connection object
	 * @param startParam A class that inherits the <CODE>Parameter</CODE> class.
	 * @return Return true if reporting succeeded. Return false if failed.
	 * @throws IOException Announce it when I/O error occurs.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public abstract boolean report(Connection conn, Parameter startParam) throws IOException, ReadWriteException;

	//#CM695547
	// Package methods -----------------------------------------------

	//#CM695548
	// Protected methods ---------------------------------------------
	//#CM695549
	/**
	 * Set for Message maintained by the check.<BR>
	 * Set the details if the content of the ticked field is wrong.<BR>
	 * @param msg Content of message.
	 */
	protected void setMessage(String msg)
	{
		wMessage = msg;
	}

	//#CM695550
	// Private methods -----------------------------------------------

}
//#CM695551
//end of class
