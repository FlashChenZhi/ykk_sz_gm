//#CM698297
//$Id: ExternalDataLoader.java,v 1.2 2006/11/13 06:03:08 suresh Exp $

//#CM698298
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

//#CM698299
/**
 * Designer : T.Yamashita <BR>
 * Maker :  <BR>
 * <BR>
 * Allow the <CODE>ExternalDataLoader</CODE> interface to define the operation of process for loading data.<BR>
 * Allow the class for loading data to execute the schedule process via this interface.
 * Allow each schedule process to <CODE>implement</CODE> this interface and implement the process.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/02</TD><TD>T.Yamashita</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:08 $
 * @author  $Author: suresh $
 */
public interface ExternalDataLoader
{
	//#CM698300
	/**
	 * Execute the process for loading data. Implement the process for loading data depending on the class that implements this class.<BR>
	 * Return true when the schedule process completed successfully.<BR>
	 * Return false if failed to schedule due to condition error or other causes.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.<BR>
	 * @param conn Database connection object
	 * @param wns  WareNaviSystem object
	 * @param startParam A class that inherits the <CODE>Parameter</CODE> class.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean load(Connection conn, WareNaviSystem wns, Parameter startParam) throws ReadWriteException, ScheduleException;

	//#CM698301
	/**
	 * If <CODE>load()</CODE> method returns false, obtain a message to display the reason.<BR>
	 * Use this method to obtain the content to be displayed in the Message area on the screen.<BR>
	 * @return Details of message.
	 */
	public String getMessage();
	
	//#CM698302
	/**
	 * When the <CODE>load</CODE> method skips data, return true.<BR>
	 * @return Details of message.
	 */
	public boolean isSkipFlag();

	//#CM698303
	/**
	 * When the <CODE>load</CODE> method overwrites data, return true.<BR>
	 * @return Details of message.
	 */
	public boolean isOverWriteFlag();

	//#CM698304
	/**
	 * When the <CODE>load</CODE> method adds data, return true.<BR>
	 * @return Details of message.
	 */
	public boolean isRegistFlag();
}
//#CM698305
//end of class
