//$Id: ItemFinder.java,v 1.2 2006/11/09 07:52:54 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Item;

/**
 * This class searches Item in the database and maps with TestView
 * This class is used to display the search results in a list screen.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:52:54 $
 * @author  $Author: suresh $
 */
public class ItemFinder extends AbstractDBFinder
{
	// Class filelds -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * Specify database connection and generate instance
	 * @param conn Database connection object
	 */
	public ItemFinder(Connection conn)
	{
		super(conn, Item.TABLE_NAME) ;
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
		return (new Item()) ;
	}

	/**
	 * Returns the class version
	 * @return version and timestamp
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/09 07:52:54 $") ;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

