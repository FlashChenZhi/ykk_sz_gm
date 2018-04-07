// $Id: ToolGroupControllerSearchKey.java,v 1.2 2006/10/30 02:17:18 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47691
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM47692
/**<en>
 * This is a key class which is used to search CONTROLLERNUMBER table using the handler class
 * and to generate the instance of CONTROLLERNUMBER class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:18 $
 * @author  $Author: suresh $
 </en>*/
public class ToolGroupControllerSearchKey extends ToolSQLSearchKey
{
	//#CM47693
	// Class fields --------------------------------------------------
	//#CM47694
	//<en> Define here the column which may be used as a search condition or which may be sorted. </en>
	private static final String CONTROLLERNUMBER = "CONTROLLERNUMBER";
	private static final String IPADDRESS     	 = "IPADDRESS";

	//#CM47695
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		CONTROLLERNUMBER,
		IPADDRESS
	};

	//#CM47696
	// Class method --------------------------------------------------
	//#CM47697
	/**<en>
	 * Returns the veriosn of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM47698
	// Constructors --------------------------------------------------
	//#CM47699
	/**<en>
	 * Set teh column definition.
	 </en>*/
	public ToolGroupControllerSearchKey()
	{
		setColumns(Columns);
	}

	//#CM47700
	// Public methods ------------------------------------------------

	//#CM47701
	/**<en>
	 * Set the search value of CONTROLLERNUMBER.
	 * @param :AGCNo. which will be the target data of search
	 </en>*/
	public void setControllerNumber(int agcnum)
	{
		setValue(CONTROLLERNUMBER, agcnum);
	}

	//#CM47702
	/**<en>
	 * Retrieve the search value of CONTROLLERNUMBER.
	 * @return AGCNo.
	 </en>*/
	public int getControllerNumber()
	{
		Integer intobj = (Integer)getValue(CONTROLLERNUMBER);
		return (intobj.intValue());
	}

	//#CM47703
	/**<en>
	 * Set the sort order of CONTROLLERNUMBER.
	 </en>*/
	public void setControllerNumberOrder(int num, boolean bool)
	{
		setOrder(CONTROLLERNUMBER, num, bool);
	}

	//#CM47704
	/**<en>
	 * Retrieve the sort order of CONTROLLERNUMBER.
	 </en>*/
	public int getControllerNumberOrder()
	{
		return (getOrder(CONTROLLERNUMBER));
	}

	//#CM47705
	/**<en>
	 * Set the search value of IPADDRESS.
	 * @param ipaddress :IP address to be searched
	 </en>*/
	public void setIPAddress(String ipaddress)
	{
		setValue(IPADDRESS, ipaddress);
	}

	//#CM47706
	/**<en>
	 * Retrieve the search value of IPADDRESS.
	 * @return :IP address
	 </en>*/
	public String getIPAddress()
	{
		return (String)getValue(IPADDRESS);
	}
	
	//#CM47707
	// Package methods -----------------------------------------------

	//#CM47708
	// Protected methods ---------------------------------------------

	//#CM47709
	// Private methods -----------------------------------------------

	//#CM47710
	// Inner Class ---------------------------------------------------

}
//#CM47711
//end of class

