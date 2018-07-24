// $Id: DatabaseFinder.java,v 1.2 2006/11/15 04:25:37 kamala Exp $
package jp.co.daifuku.wms.base.dbhandler ;

//#CM708447
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.EntityFinder;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM708448
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
 * @version $Revision: 1.2 $, $Date: 2006/11/15 04:25:37 $
 * @author  $Author: kamala $
 */
public interface DatabaseFinder
			extends EntityFinder
{
	//#CM708449
	// Class fields --------------------------------------------------
	//#CM708450
	/**
	 * Number of LISTBOX retrieval result upper bounds
	 */
	public static final int MAXDISP = WmsParam.MAX_NUMBER_OF_DISP_LISTBOX;

	//#CM708451
	// Public methods ------------------------------------------------
	//#CM708452
	/**
	 * Generate the statement, and open the cursor. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void open() throws ReadWriteException ;

	//#CM708453
	/**
	 * Close the statement. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void close() throws ReadWriteException ;


	//#CM708454
	// Package methods -----------------------------------------------

	//#CM708455
	// Protected methods ---------------------------------------------

	//#CM708456
	// Private methods -----------------------------------------------

}
//#CM708457
//end of interface

