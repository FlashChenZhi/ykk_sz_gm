// $Id: AbstractSQLSearchKey.java,v 1.2 2006/11/15 04:25:36 kamala Exp $
package jp.co.daifuku.wms.base.dbhandler ;

//#CM708410
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.entity.AbstractEntity;

//#CM708411
/**
 * A common class which sets the key to retrieve the table. 
 *
 * <BR>
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Y.Kato</b></td><td><b>New making</b></td></tr>
 *
 * <!-- Change history -->
 * <tr><td nowrap>2004/10/28</td><td nowrap>Y.Kato created this file.</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision, $Date: 2006/11/15 04:25:36 $
 * @author  Y.Kato
 * @author  Last commit: $Author: kamala $
 */
public abstract class AbstractSQLSearchKey
		extends SQLSearchKey
{
	//#CM708412
	// Class fields --------------------------------------------------

	//#CM708413
	// Class variables -----------------------------------------------
	//#CM708414
	/**
	 * Reference table name
	 */
	private String p_TableName = "" ;

	//#CM708415
	// Class method --------------------------------------------------
	//#CM708416
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM708417
	// Constructors --------------------------------------------------
	//#CM708418
	/**
	 * Prepare Table Name of the object and the list of the column, and 
	 * generate the instance. 
	 * @param ent Table entity to be managed
	 */
	public AbstractSQLSearchKey(AbstractEntity ent)
	{
		setTableName(ent.getTablename()) ;
		setColumns(ent.getTableColumnArray()) ;
	}

	//#CM708419
	// Public methods ------------------------------------------------
	//#CM708420
	/**
	 * Set Reference table name
	 * @param name Table Name
	 */
	public void setTableName(String name)
	{
		p_TableName = name ;
	}

	//#CM708421
	/**
	 * Acquire Reference table name
	 * @return Table Name
	 */
	public String getTableName()
	{
		return p_TableName ;
	}

	//#CM708422
	/**
	 * Clear processing of search condition and the order of sorting
	 */
	public void KeyClear()
	{
		clearConditions() ;
	}

	//#CM708423
	// Package methods -----------------------------------------------

	//#CM708424
	// Protected methods ---------------------------------------------

	//#CM708425
	// Private methods -----------------------------------------------

	//#CM708426
	// Inner Class ---------------------------------------------------

}
//#CM708427
//end of class
