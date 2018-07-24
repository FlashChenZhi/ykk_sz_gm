//#CM46592
//$Id: AwcMenu.java,v 1.2 2006/10/30 01:40:57 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.common ;

//#CM46593
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM46594
/**<en>
 * This class is used to switch items to display according to the user and terminal.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/09/18</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:57 $
 * @author  $Author: suresh $
 </en>*/
public class AwcMenu extends ToolEntity
{
	//#CM46595
	// Class fields --------------------------------------------------

	//#CM46596
	/**<en>
	 * Field which shows the registration has been made via user's menu.
	 </en>*/
	public static final int USERMENU = 0 ;

	//#CM46597
	/**<en>
	 * Field which shows the registration has been made via termianl menu.
	 </en>*/
	public static final int TERMINALMENU = 1 ;

	//#CM46598
	// Class variables -----------------------------------------------
	//#CM46599
	/**<en>
	 * Menu ID
	 </en>*/
	protected String wMenuId;

	//#CM46600
	/**<en>
	 * User name
	 </en>*/
	protected String wUserName ;

	//#CM46601
	/**<en>
	 * Terminal no.
	 </en>*/
	protected String wTerminalNumber ;

	//#CM46602
	/**<en>
	 * Menu Flag
	 </en>*/
	protected int wMenuFlag ;

	//#CM46603
	// Class method --------------------------------------------------

	//#CM46604
	// Constructors --------------------------------------------------
	//#CM46605
	/**<en>
	 * Construct a new <CODE>AwcMenu</CODE>.
	 </en>*/
	public AwcMenu()
	{
	}
	
	//#CM46606
	/**<en>
	 * Construct a new <CODE>AwcMenu</CODE>.
	 * @param menuid    Menu ID
	 * @param user      User name
	 * @param terminal  Terminal no.
	 * @param menuflag  Menu Flag
	 </en>*/
	public AwcMenu (
				 String 		menuid, 
				 String 		user,
				 String			terminal,
				 int			menuflag
			    )
	{
		wMenuId   = menuid;
		wUserName     = user;
		wTerminalNumber = terminal;
		wMenuFlag = menuflag;
	}
	
	//#CM46607
	// Public methods ------------------------------------------------
	//#CM46608
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:40:57 $") ;
	}

	//#CM46609
	/**<en>
	 * Set the value of MenuID.
	 * @param menuid :menu ID to set
	 </en>*/
	public void setMenuId(String menuid)
	{
		wMenuId = menuid ;
	}

	//#CM46610
	/**<en>
	 * Retrieve the MenuID.
	 * @return :menu ID
	 </en>*/
	public String getMenuId()
	{
		return wMenuId ;
	}

	//#CM46611
	/**<en>
	 * Set the value of user name.
	 * @param user :user name to set
	 </en>*/
	public void setUserName(String user)
	{
		wUserName = user ;
	}

	//#CM46612
	/**<en>
	 * Retrieve the user name.
	 * @return :user name
	 </en>*/
	public String getUserName()
	{
		return wUserName ;
	}

	//#CM46613
	/**<en>
	 * Set the value of terminal no.
	 * @param terminal :terminal no. to set
	 </en>*/
	public void setTerminalNumber(String terminal)
	{
		wTerminalNumber = terminal ;
	}

	//#CM46614
	/**<en>
	 * Retriebe the terminal no.
	 * @return :terminal no.
	 </en>*/
	public String getTerminalNumber()
	{
		return wTerminalNumber ;
	}

	//#CM46615
	/**<en>
	 * Set the value of MenuFlag.
	 * @param menuflag MenuFlag
	 </en>*/
	public void setMenuFlag(int menuflag)
	{
		wMenuFlag = menuflag ;
	}

	//#CM46616
	/**<en>
	 * Retrieve the MenuFlag.
	 * @return MenuFlag
	 </en>*/
	public int getMenuFlag()
	{
		return wMenuFlag ;
	}

	//#CM46617
	// Package methods -----------------------------------------------

	//#CM46618
	// Protected methods ---------------------------------------------

	//#CM46619
	// Private methods -----------------------------------------------

}
//#CM46620
//end of class

