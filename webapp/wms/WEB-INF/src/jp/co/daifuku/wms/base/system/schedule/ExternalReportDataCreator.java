//#CM698306
//$Id: ExternalReportDataCreator.java,v 1.2 2006/11/13 06:03:08 suresh Exp $

//#CM698307
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.Parameter;

//#CM698308
/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai <BR>
 * <BR>
 * Allow the <CODE>ExternalReportDataCreator</CODE> interface to define the operation of process for reporting data.<BR>
 * Allow the class that executes the process for reporting data to execute the process for schedule via this interface.
 * Allow each schedule process to <CODE>implement</CODE> this interface and implement the process.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/02</TD><TD>T.Nakai</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:08 $
 * @author  $Author: suresh $
 */
public interface ExternalReportDataCreator
{
	//#CM698309
	/**
	 * Execute the process for reporting data.Implement the process for reporting data depending on the class that implements this interface.<BR>
	 * Return true when the schedule process completed successfully.<BR>
	 * Return false if failed to schedule due to condition error or other causes.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.<BR>
	 * @param conn Database connection object
	 * @param startParam A class that inherits the <CODE>Parameter</CODE> class.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws IOException Announce it when I/O error occurs.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public boolean report(Connection conn, Parameter startParam) throws IOException, ReadWriteException;

	//#CM698310
	/**
	 * If <CODE>report()</CODE> method returns false, obtain a message to display the reason.<BR>
	 * Use this method to obtain the content to be displayed in the Message area on the screen.<BR>
	 * @return Details of message.
	 */
	public String getMessage();

}
//#CM698311
//end of class
