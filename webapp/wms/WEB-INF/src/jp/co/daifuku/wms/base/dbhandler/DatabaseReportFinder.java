// $Id: DatabaseReportFinder.java,v 1.2 2006/11/15 04:25:38 kamala Exp $
package jp.co.daifuku.wms.base.dbhandler ;

//#CM708476
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.EntityReportFinder;

//#CM708477
/**
 * The interface to keep the class in the data base, and to acquire information from the data base and to generate the instance. 
 * Implements this class when you do the list display on the screen and mount the class which returns <CODE>Entity</CODE>. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/15 04:25:38 $
 * @author  $Author: kamala $
 */
public interface DatabaseReportFinder 
			extends EntityReportFinder
{
	//#CM708478
	// Class fields --------------------------------------------------

	//#CM708479
	// Public methods ------------------------------------------------
	//#CM708480
	/**
	 * Generate the statement, and open the cursor. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void open() throws ReadWriteException ;

	//#CM708481
	/**
	 * Close the statement. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void close() throws ReadWriteException ;


	//#CM708482
	// Package methods -----------------------------------------------

	//#CM708483
	// Protected methods ---------------------------------------------

	//#CM708484
	// Private methods -----------------------------------------------

}
//#CM708485
//end of interface

