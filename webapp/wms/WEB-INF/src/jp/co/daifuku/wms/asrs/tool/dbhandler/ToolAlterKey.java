// $Id: ToolAlterKey.java,v 1.2 2006/10/30 02:17:22 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47319
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date;

//#CM47320
/**<en>
 * This is an interface for the key information which will be specified when modifing the 
 * stored information.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:22 $
 * @author  $Author: suresh $
 </en>*/
public interface ToolAlterKey
{
	//#CM47321
	// Public methods ------------------------------------------------
	//#CM47322
	/**<en>
	 * Set the string type search value to the specified column.
	 </en>*/
	public void setValue(String column, String value);

	//#CM47323
	/**<en>
	 * Set the numeric type search value to the specified column.
	 </en>*/
	public void setValue(String column, int intval);

	//#CM47324
	/**<en>
	 * Set the date format search value to the specified column.
	 </en>*/
	public void setValue(String column, Date dtval);

	//#CM47325
	/**<en>
	 * Retrieve the search value of the specified column.
	 </en>*/
	public Object getValue(String column);

	//#CM47326
	/**<en>
	 * Set the string type update value to the specified column.
	 </en>*/
	public void setUpdValue(String column, String value);

	//#CM47327
	/**<en>
	 * Set the numeric update value to the specified column.
	 </en>*/
	public void setUpdValue(String column, int intval);

	//#CM47328
	/**<en>
	 * Generate the condition sentence based on the set key. 
	 * For handler to conduct search based on the contents.
	 * @param tablename :name of target table to modify
	 </en>*/
	public String ReferenceCondition(String tablename);

	//#CM47329
	/**<en>
	 * Generate the update sentence based on the set key.
	 * For handler to conduct update based on the contents.
	 * @param tablename :name of target table to update
	 </en>*/
	public String ModifyContents(String tablename);


	//#CM47330
	// Package methods -----------------------------------------------

	//#CM47331
	// Protected methods ---------------------------------------------

	//#CM47332
	// Private methods -----------------------------------------------

}
//#CM47333
//end of interface

