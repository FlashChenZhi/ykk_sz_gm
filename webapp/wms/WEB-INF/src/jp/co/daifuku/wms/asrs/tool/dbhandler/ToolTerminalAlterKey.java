// $Id: ToolTerminalAlterKey.java,v 1.2 2006/10/30 02:17:12 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48896
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM48897
/**<en>
 * Defined in this class is the information to update the TERMINAL table.
 * It will be used when updating table by ItemHandler.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/04/10</TD><TD>miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:12 $
 * @author  $Author: suresh $
 </en>*/
public class ToolTerminalAlterKey extends ToolSQLAlterKey
{
	//#CM48898
	// Class fields --------------------------------------------------
	//#CM48899
	//<en> Define here the columns which could be search conditions or the target data of update.</en>
	private static final String TERMINALNUMBER  = "TERMINAL.TERMINALNUMBER";
	private static final String PRINTERNAME		= "TERMINAL.PRINTERNAME";
	
	//#CM48900
	// Class variables -----------------------------------------------
	//#CM48901
	//<en> Set the variable, defined with the declared colunm, in the array. </en>
	private static final String[] Columns =
	{
		TERMINALNUMBER,
		PRINTERNAME
	};

	//#CM48902
	// Class method --------------------------------------------------
	//#CM48903
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM48904
	// Constructors --------------------------------------------------
	//#CM48905
	/**<en>
	 * Conduct the initial setting of declared table column.
	 </en>*/
	public ToolTerminalAlterKey()
	{
		setColumns(Columns);
	}

	//#CM48906
	// Public methods ------------------------------------------------
	//#CM48907
	//<en>=============<Method of update condition settings>==============</en>
	//#CM48908
	/**<en>
	 * Set the search value of TERMINALNUMBER.
	 * @param :search value of TERMINALNUMBER
	 </en>*/
	public void setTerminalNumber(String no)
	{
		setValue(TERMINALNUMBER, no);
	}

	//#CM48909
	/**<en>
	 * Retrieve the TERMINALNUMBER.
	 * @return TERMINALNUMBER
	 </en>*/
	public String getTerminalNumber()
	{
		return (String)getValue(TERMINALNUMBER);
	}

	//#CM48910
	//<en>==============<Method of update value settings>==================</en>

	//#CM48911
	/**<en>
	 * Set the update value of PRINTERNAME.
	 * @param :update value of PRINTERNAME
	 </en>*/
	public void updatePrinterName(String printer)
	{
		setUpdValue(PRINTERNAME, printer);
	}

	//#CM48912
	// Package methods -----------------------------------------------

	//#CM48913
	// Protected methods ---------------------------------------------

	//#CM48914
	// Private methods -----------------------------------------------

	//#CM48915
	// Inner Class ---------------------------------------------------

}
//#CM48916
//end of class

