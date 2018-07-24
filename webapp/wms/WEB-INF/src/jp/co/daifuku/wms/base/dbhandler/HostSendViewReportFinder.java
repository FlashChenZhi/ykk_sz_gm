//$Id: HostSendViewReportFinder.java,v 1.2 2006/11/09 07:53:01 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.wms.base.dbhandler.AbstractDBReportFinder;
import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.base.entity.HostSendView;

/**
 * This class searches the Dvhostsendview in the database and map it with TestView
 * This class displays the search result in a list screen
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:53:01 $
 * @author  $Author: suresh $
 */
public class HostSendViewReportFinder extends AbstractDBReportFinder
{
	// Class filelds -----------------------------------------------

	// Class method --------------------------------------------------
	// Constructors --------------------------------------------------
	/**
	 * Specify database connection and generate instance
	 * @param conn Database connection object
	 */
	public HostSendViewReportFinder(Connection conn)
	{
		super(conn, HostSendView.TABLE_NAME) ;
	}

	//------------------------------------------------------------
	// protected methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * @see AbstractDBFinder#createEntity()
	 */
	protected AbstractEntity createEntity()
	{
		return (new HostSendView()) ;
	}

	/**
	 * Returns the class version
	 * @return version and timestamp
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/09 07:53:01 $") ;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

