// $Id: GroupControllerInformation.java,v 1.2 2006/10/30 02:33:25 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49483
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

//#CM49484
/**<en>
 * This class is used to control the group controllers.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:25 $
 * @author  $Author: suresh $
 </en>*/
public class GroupControllerInformation extends ToolEntity
{
	//#CM49485
	// Class fields --------------------------------------------------

	//#CM49486
	// Class variables -----------------------------------------------
	//#CM49487
	/**<en>
	 * AGCNo.
	 </en>*/
	protected int wControllerNumber ;
	
	//#CM49488
	/**<en>
	 * status
	 </en>*/
	protected int wStatus ;
	
	//#CM49489
	/**<en>
	 * host name
	 </en>*/
	protected String wIPAddress ;
	
	//#CM49490
	/**<en>
	 * port no.
	 </en>*/
	protected int wPort ;

	//#CM49491
	// Class method --------------------------------------------------
	//#CM49492
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:25 $") ;
	}

	//#CM49493
	// Constructors --------------------------------------------------
	//#CM49494
	/**<en>
	 * Constcut new <CODE>GroupControllerInformation</CODE>.
	 </en>*/
	public GroupControllerInformation()
	{
	}
	
	//#CM49495
	/**<en>
	 * Construct new <CODE>GroupControllerInformation</CODE>.
	 * @param agcno   	AGCNo
	 * @param status  	status
	 * @param ip   	host name
	 * @param port   	port no.
	 </en>*/
	public GroupControllerInformation(int agcno,
									  int status,
									  String ip,
									  int port
									  )
	{
		//#CM49496
		//<en> Set as an instance variable.</en>
		setControllerNumber(agcno);
		setStatus(status);
		setIPAddress(ip);
		setPort(port);
	}

	//#CM49497
	// Public methods ------------------------------------------------
	//#CM49498
	/**<en>
	 * Set the AGCNo.
	 * @param agcnum :AGCNo.
	 </en>*/
	public void setControllerNumber(int agcnum)
	{
		wControllerNumber = agcnum;
	}

	//#CM49499
	/**<en>
	 * Retrieve the AGCNo.
	 * @return :AGCNo.
	 </en>*/
	public int getControllerNumber()
	{
		return wControllerNumber;
	}

	//#CM49500
	/**<en>
	 * Set the status.
	 * @param status :status
	 </en>*/
	public void setStatus(int status)
	{
		wStatus = status ;
	}

	//#CM49501
	/**<en>
	 * Retrieve the status.
	 * @return :status
	 </en>*/
	public int getStatus()
	{
		return wStatus ;
	}

	//#CM49502
	/**<en>
	 * Set the host name.
	 * @param ipaddress :host name
	 </en>*/
	public void setIPAddress(String ipaddress)
	{
		wIPAddress = ipaddress ;
	}

	//#CM49503
	/**<en>
	 * Retrieve the host name.
	 * @return :host name
	 </en>*/
	public String getIPAddress()
	{
		return wIPAddress ;
	}
	
	//#CM49504
	/**<en>
	 * Set the port no.
	 * @param port :port no
	 </en>*/
	public void setPort(int port)
	{
		wPort = port ;
	}

	//#CM49505
	/**<en>
	 * Retrieve the port no.
	 * @return :port no
	 </en>*/
	public int getPort()
	{
		return wPort ;
	}

	//#CM49506
	/**<en>
	 * Return the string representation of GroupControllerInformation.
	 * @return    string representation
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		buf.append("\nControllerNumber:" + wControllerNumber) ;
		buf.append("\nStatus:" + wStatus) ;
		buf.append("\nIPAddress:" + wIPAddress) ;
		buf.append("\nPort:" + wPort) ;
		return buf.toString() ;
	}

	//#CM49507
	// Package methods -----------------------------------------------

	//#CM49508
	// Protected methods ---------------------------------------------

	//#CM49509
	// Private methods -----------------------------------------------

}
//#CM49510
//end of class

