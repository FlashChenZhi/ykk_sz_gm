//#CM702706
//$Id: WorkingInformationHandler.java,v 1.2 2006/11/14 06:09:23 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702707
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.wms.base.entity.AbstractEntity;

//#CM702708
/**
 * The class to operate work information (WorkingInformation). <BR>
 * Because WorkingInformation is enhanced for RFT, the class of the object 
 * generated with new has been changed. <BR>
 * There is no other correction. 
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:23 $
 * @author  $Author: suresh $
 */
public class WorkingInformationHandler extends jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler
{
	//#CM702709
	// Class feilds ------------------------------------------------

	//#CM702710
	// Class variables -----------------------------------------------

	//#CM702711
	// Class method --------------------------------------------------
	//#CM702712
	/**
	 * Return version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:23 $";
	}

	//#CM702713
	// Constructors --------------------------------------------------
	//#CM702714
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public WorkingInformationHandler(Connection conn)
	{
		super(conn);
	}
	
	//#CM702715
	/**
	 * @see dbhandler.AbstractDBHandler#createEntity()
	 */
	protected AbstractEntity createEntity()
	{
		return (new jp.co.daifuku.wms.base.rft.WorkingInformation()) ;
	}

	//#CM702716
	// Public methods ------------------------------------------------

	//#CM702717
	// Package methods -----------------------------------------------

	//#CM702718
	// Protected methods ---------------------------------------------

	//#CM702719
	// Private methods -----------------------------------------------
}
//#CM702720
//end of class
