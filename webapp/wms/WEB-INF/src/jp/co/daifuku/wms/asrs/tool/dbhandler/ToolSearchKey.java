// $Id: ToolSearchKey.java,v 1.2 2006/10/30 02:17:17 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47861
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM47862
/**<en>
 * This is an interface for the key information which will be specified
 * when retrieving the stored information.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:17 $
 * @author  $Author: suresh $
 </en>*/
public interface ToolSearchKey
{
	//#CM47863
	// Public methods ------------------------------------------------

	//#CM47864
	//<en> Set the string type search value in the specified column.</en>
	public void setValue(String column, String value);

	//#CM47865
	//<en> Set the numeric type search value in the specified column.</en>
	public void setValue(String column, int intval);

	//#CM47866
	//<en> Retrieve the search value of the specified column.</en>
	public Object getValue(String column);

	//#CM47867
	//<en> Set the sort order to the specified column.</en>
	public void setOrder(String column, int num, boolean bool);

	//#CM47868
	//<en> Retrieve the sort order of the specified column.</en>
	public int getOrder(String column);

	//#CM47869
	//<en> Generate the conditional statement according to the set key.</en>
	//<en> The handler will conduct search based on its contents.</en>
	public String ReferenceCondition();

	//#CM47870
	//<en> Generate the sort order according to the specified key.</en>
	//<en> The handler will conduct search based on its contents.</en>
	public String SortCondition();

	//#CM47871
	// Package methods -----------------------------------------------

	//#CM47872
	// Protected methods ---------------------------------------------

	//#CM47873
	// Private methods -----------------------------------------------

}
//#CM47874
//end of interface

