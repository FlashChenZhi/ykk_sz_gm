// $Id: GroupControllerParameter.java,v 1.2 2006/10/30 02:52:05 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;
//#CM50750
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

//#CM50751
/**<en>
 * This is an entity class which will be used in group controller.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:05 $
 * @author  $Author: suresh $
 </en>*/
public class GroupControllerParameter extends Parameter
{
	//#CM50752
	// Class fields --------------------------------------------------

	//#CM50753
	// Class variables -----------------------------------------------
	
    //#CM50754
    /** AGCNo. */

    protected int wControllerNumber = 0;
    
    //#CM50755
    /**<en> host name </en>*/

    protected String wIPAddress = "";

	//#CM50756
	// Class method --------------------------------------------------
	 //#CM50757
	 /**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:05 $") ;
	}

	//#CM50758
	// Constructors --------------------------------------------------
	//#CM50759
	/**<en>
	 * This consructor will be used.
	 * @param conn <CODE>Connection</CODE>
	 </en>*/
	public GroupControllerParameter()   
	{
	}

	//#CM50760
	// Public methods ------------------------------------------------

	//#CM50761
	/**<en>
	 * Retrieve the AGCNo.
	 * @return wControllerNumber
	 </en>*/
	public int getControllerNumber()
	{
		return wControllerNumber;
	}
	
	//#CM50762
	/**<en>
	 * Set the AGCNo.
	 * @param ControllerNumber
	 </en>*/
	public void setControllerNumber(int arg)
	{
		wControllerNumber = arg;
	}
	
	//#CM50763
	/**<en>
	 * Retrieve the host name.
	 * @return wIPAddress
	 </en>*/
	public String getIPAddress()
	{
		return wIPAddress;
	}
	
	//#CM50764
	/**<en>
	 * Set the host name.
	 * @param IPAddress
	 </en>*/
	public void setIPAddress(String arg)
	{
		wIPAddress = arg;
	}

	//#CM50765
	// Package methods -----------------------------------------------

	//#CM50766
	// Protected methods ---------------------------------------------

	//#CM50767
	// Private methods -----------------------------------------------
	
}
//#CM50768
//end of class
