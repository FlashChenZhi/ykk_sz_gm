// $Id: ToolTerminalAreaSearchKey.java,v 1.2 2006/10/30 02:17:11 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48975
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM48976
/**<en>
 * This key class is used to search TERMINALAREA table using handler class and to generate the 
 * instance of TERMINALAREA class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/12/12</TD><TD>INOUE</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:11 $
 * @author  $Author: suresh $
 </en>*/
public class ToolTerminalAreaSearchKey extends ToolSQLSearchKey
{
	//#CM48977
	// Class fields --------------------------------------------------
	//#CM48978
	//<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
	private static final String STATIONNUMBER  = "STATIONNUMBER";
	private static final String AREAID  = "AREAID";
	private static final String TERMINALNUMBER = "TERMINALNUMBER";

	//#CM48979
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		STATIONNUMBER,
		AREAID,
		TERMINALNUMBER
	};

	//#CM48980
	// Class method --------------------------------------------------
	//#CM48981
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM48982
	// Constructors --------------------------------------------------
	//#CM48983
	/**<en>
	 * Set the column definition.
	 </en>*/
	public ToolTerminalAreaSearchKey()
	{
		setColumns(Columns);
	}

	//#CM48984
	// Public methods ------------------------------------------------
	//#CM48985
	/**<en>
	 * Set the search value of SERIALNUMBER.
	 * @param snum :serial no. to serach
	 </en>*/
	//#CM48986
	/**<en>
	 * Set the search value of TERMINALNUMBER.
	 * @param terminalnum :terminal no. to be searched
	 </en>*/
	public void setTerminalNumber(String terminalnum)
	{
		setValue(TERMINALNUMBER, terminalnum);
	}

	//#CM48987
	/**<en>
	 * Retrieve the search value of TERMINALNUMBER.
	 * @return :terminal no.
	 </en>*/
	public String getTerminalNumber()
	{
		return (String)getValue(TERMINALNUMBER);
	}

	//#CM48988
	/**<en>
	 * Set the sort order of TERMINALNUMBER.
	 </en>*/
	public void setTerminalNumberOrder(int num, boolean bool)
	{
		setOrder(TERMINALNUMBER, num, bool);
	}

	//#CM48989
	/**<en>
	 * Retrieve the sort order of TERMINALNUMBER.
	 </en>*/
	public int getTerminalNumberOrder()
	{
		return (getOrder(TERMINALNUMBER));
	}
	
	//#CM48990
	/**<en>
	 * Set the search value of AREAID.
	 * @param areaid :area ID to be searched
	 </en>*/
	public void setAreaId(int areaid)
	{
		setValue(AREAID, areaid);
	}

	//#CM48991
	/**<en>
	 * Retrieve the search value of AREAID.
	 * @return :area ID
	 </en>*/
	public int getAreaId()
	{
		Integer intobj = (Integer)getValue(AREAID);
		return(intobj.intValue());
	}

	//#CM48992
	/**<en>
	 * Set the sort order of AREAID.
	 </en>*/
	public void setAreaIdOrder(int num, boolean bool)
	{
		setOrder(AREAID, num, bool);
	}

	//#CM48993
	/**<en>
	 * Retrieve the sort order of AREAID.
	 </en>*/
	public int getAreaIdOrder()
	{
		return (getOrder(AREAID));
	}

	
	//#CM48994
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 * @param num :STATIONNUMBER to be searched
	 </en>*/
	public void setStationNumber(String num)
	{
		setValue(STATIONNUMBER, num);
	}

	//#CM48995
	/**<en>
	 * Retrieve the search value of STATIONNUMBER.
	 * @return STATIONNUMBER
	 </en>*/
	public String getStationNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM48996
	/**<en>
	 * Set the sort order of STATIONNUMBER.
	 </en>*/
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(STATIONNUMBER, num, bool);
	}

	//#CM48997
	/**<en>
	 * Retrieve the sort order of STATIONNUMBER.
	 </en>*/
	public int getStationNumberOrder()
	{
		return (getOrder(STATIONNUMBER));
	}

	//#CM48998
	// Package methods -----------------------------------------------

	//#CM48999
	// Protected methods ---------------------------------------------

	//#CM49000
	// Private methods -----------------------------------------------

	//#CM49001
	// Inner Class ---------------------------------------------------

}
//#CM49002
//end of class

