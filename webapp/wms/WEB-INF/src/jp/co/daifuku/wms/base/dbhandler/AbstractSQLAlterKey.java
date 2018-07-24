// $Id: AbstractSQLAlterKey.java,v 1.2 2006/11/15 04:25:36 kamala Exp $
package jp.co.daifuku.wms.base.dbhandler ;

//#CM708392
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.entity.AbstractEntity;

//#CM708393
/**
 * A common class which sets the key to renew the table. 
 *
 * <BR>
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Y.Kato</b></td><td><b>New making</b></td></tr>
 *
 * <!-- Change history -->
 * <tr><td nowrap>2004/11/09</td><td nowrap>Y.Kato created this file.</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision, $Date: 2006/11/15 04:25:36 $
 * @author  Y.Kato
 * @author  Last commit: $Author: kamala $
 */
public abstract class AbstractSQLAlterKey
		extends SQLAlterKey
{
	//#CM708394
	// Class fields --------------------------------------------------

	//#CM708395
	// Class variables -----------------------------------------------
	//#CM708396
	/**
	 * Reference table name
	 */
	private String p_TableName = "" ;

	//#CM708397
	// Class method --------------------------------------------------
	//#CM708398
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM708399
	// Constructors --------------------------------------------------
	//#CM708400
	/**
	 * Prepare the table name of the object, the list of the column, and the list of the row for an automatic update, 
	 * and generate the instance. 
	 * @param ent Table entity to be managed
	 */
	public AbstractSQLAlterKey(AbstractEntity ent)
	{
		setTableName(ent.getTablename()) ;
		setColumns(ent.getTableColumnArray()) ;
		setAutoColumns(ent.getAutoUpdateColumnArray());
	}

	//#CM708401
	// Public methods ------------------------------------------------
	//#CM708402
	/**
	 * Set Reference table name
	 * @param name Table Name
	 */
	public void setTableName(String name)
	{
		p_TableName = name ;
	}

	//#CM708403
	/**
	 * Acquire Reference table name
	 * @return Table Name
	 */
	public String getTableName()
	{
		return p_TableName ;
	}

	//#CM708404
	/**
	 * Clear processing of search condition and the sorting order area
	 *
	 */
	public void KeyClear()
	{
		clearConditions() ;
	}

	//#CM708405
	// Package methods -----------------------------------------------

	//#CM708406
	// Protected methods ---------------------------------------------

	//#CM708407
	// Private methods -----------------------------------------------

	//#CM708408
	// Inner Class ---------------------------------------------------

}
//#CM708409
//end of class
