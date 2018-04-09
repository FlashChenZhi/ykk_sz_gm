// $Id: ToolWarehouseAlterKey.java,v 1.2 2006/10/30 02:17:11 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM49003
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM49004
/**<en>
 * Defined in this class is hte information to update the WAREHOUSE table.
 * This is used when WareHouseHandler class updates the WAREHOUSE table.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:11 $
 * @author  $Author: suresh $
 </en>*/
public class ToolWarehouseAlterKey extends ToolSQLAlterKey
{
	//#CM49005
	// Class fields --------------------------------------------------

	//#CM49006
	//<en> Define here the columns which could be search conditions or the target data of update</en>
	private static final String STATIONNUMBER	 		= "STATIONNUMBER";
	private static final String LASTUSEDSTATIONNUMBER	= "LASTUSEDSTATIONNUMBER";

	//#CM49007
	// Class variables -----------------------------------------------

	//#CM49008
	//<en> Set the variable, defined with the declared colunm, in the array. </en>
	private static final String[] Columns =
	{
		STATIONNUMBER,
		LASTUSEDSTATIONNUMBER
	};

	//#CM49009
	// Class method --------------------------------------------------
	//#CM49010
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date 
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM49011
	// Constructors --------------------------------------------------
	//#CM49012
	/**<en>
	 * Conduct the initial setting of declared table column.
	 </en>*/
	public ToolWarehouseAlterKey()
	{
		setColumns(Columns);
	}

	//#CM49013
	// Public methods ------------------------------------------------
	//#CM49014
	//<en>============<Method of update condition settings>===============</en>

	//#CM49015
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 * @param :search station no.
	 </en>*/
	public void setNumber(String stnum)
	{
		setValue(STATIONNUMBER, stnum);
	}

	//#CM49016
	/**<en>
	 * Retrieve the search value of STATIONNUMBER.
	 * @return :search station no.
	 </en>*/
	public String getNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM49017
	//<en>============<Method of update value settings>==================</en>

	//#CM49018
	/**<en>
	 * Set the update value of LASTUSEDSTATIONNUMBER.
	 * @param :update value of LASTUSEDSTATIONNUMBER
	 </en>*/
	public void updateLastUsedStationNumber(String lst)
	{
		setUpdValue(LASTUSEDSTATIONNUMBER, lst);
	}

	//#CM49019
	// Package methods -----------------------------------------------

	//#CM49020
	// Protected methods ---------------------------------------------

	//#CM49021
	// Private methods -----------------------------------------------

	//#CM49022
	// Inner Class ---------------------------------------------------

}
//#CM49023
//end of class

