//$Id: NextProcessInfoFinder.java,v 1.2 2006/11/09 07:51:46 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.wms.base.dbhandler.AbstractDBFinder;
import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;

/**
 * This class searches Nextproc in the database and maps with TestView
 * This class is used to display the search results in a list screen.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:51:46 $
 * @author  $Author: suresh $
 */
public class NextProcessInfoFinder extends AbstractDBFinder
{
	// Class filelds -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * Specify database connection and generate instance
	 * @param conn Database connection object
	 */
	public NextProcessInfoFinder(Connection conn)
	{
		super(conn, NextProcessInfo.TABLE_NAME) ;
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
		return (new NextProcessInfo()) ;
	}

	/**
	 * Returns the class version
	 * @return version and timestamp
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/09 07:51:46 $") ;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

