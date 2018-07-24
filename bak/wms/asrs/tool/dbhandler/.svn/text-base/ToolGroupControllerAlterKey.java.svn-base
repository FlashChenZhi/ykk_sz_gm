// $Id: ToolGroupControllerAlterKey.java,v 1.2 2006/10/30 02:17:19 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47606
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM47607
/**<en>
 * This class defines the information which will be used to update the GROUPCONTROLLER table.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/04/10</TD><TD>miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:19 $
 * @author  $Author: suresh $
 </en>*/
public class ToolGroupControllerAlterKey extends ToolSQLAlterKey
{
	//#CM47608
	// Class fields --------------------------------------------------
	//#CM47609
	//<en> Define here the columns which could be search conditions or the target data of update. </en>
	private static final String CONTROLLERNUMBER  = "CONTROLLERNUMBER";
	private static final String STATUS  = "STATUS";
	private static final String IPADDRESS  = "IPADDRESS";
	private static final String PORT  = "PORT";
	
	//#CM47610
	// Class variables -----------------------------------------------
	//#CM47611
	//<en> Set the variable defined with declared column to the array.</en>
	private static final String[] Columns =
	{
		CONTROLLERNUMBER,
		STATUS,
		IPADDRESS,
		PORT
	};

	//#CM47612
	// Class method --------------------------------------------------
	//#CM47613
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM47614
	// Constructors --------------------------------------------------
	//#CM47615
	/**<en>
	 * Initialize the declared table column.
	 </en>*/
	public ToolGroupControllerAlterKey()
	{
		setColumns(Columns);
	}

	//#CM47616
	// Public methods ------------------------------------------------
	//#CM47617
	//<en>============<Method of update condition settings>===============</en>
	//#CM47618
	/**<en>
	 * Set the search value of CONTROLLERNUMBER.
	 * @param :the search value of CONTROLLERNUMBER
	 </en>*/
	public void setControllerNumber(int no)
	{
		setValue(CONTROLLERNUMBER, no);
	}

	//#CM47619
	//<en>========<Method of update value settings>================</en>
	//#CM47620
	/**<en>
	 * Set the update value of STATUS.
	 * @param :update value of STATUS
	 </en>*/
	public void updateStatus(String status)
	{
		setUpdValue(STATUS, status);
	}
	
	//#CM47621
	/**<en>
	 * Set the update value of IPADDRESS.
	 * @param :update value of IPADDRESS
	 </en>*/
	public void updateIPAddress(String ipaddress)
	{
		setUpdValue(IPADDRESS, ipaddress);
	}
	
	//#CM47622
	/**<en>
	 * Set the update value of PORT.
	 * @param :update value of PORT
	 </en>*/
	public void updatePort(String port)
	{
		setUpdValue(PORT, port);
	}

	//#CM47623
	// Package methods -----------------------------------------------

	//#CM47624
	// Protected methods ---------------------------------------------

	//#CM47625
	// Private methods -----------------------------------------------

	//#CM47626
	// Inner Class ---------------------------------------------------

}
//#CM47627
//end of class

