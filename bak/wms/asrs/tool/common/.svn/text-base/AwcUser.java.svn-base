// $Id: AwcUser.java,v 1.2 2006/10/30 01:40:57 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.common ;

//#CM46621
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM46622
/**<en>
 * This class is used to control the user information.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/02/18</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:57 $
 * @author  $Author: suresh $
 </en>*/
public class AwcUser extends ToolEntity
{
	//#CM46623
	// Class fields --------------------------------------------------

	//#CM46624
	// Class variables -----------------------------------------------
	//#CM46625
	/**<en>
	 * AwcUserKey (user name)
	 </en>*/
	protected String wUserName = "" ;

	//#CM46626
	/**<en>
	 * Password
	 </en>*/
	protected String wPassword = "" ;

	//#CM46627
	/**<en>
	 * Default terminal no.
	 </en>*/
	protected String wTerminal = "" ;

	//#CM46628
	// Class method --------------------------------------------------
	//#CM46629
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:40:57 $") ;
	}

	//#CM46630
	// Constructors --------------------------------------------------
	//#CM46631
	/**<en>
	 * Construct a new <CODE>AwcUser</CODE>.
	 </en>*/
	public AwcUser()
	{
	}

	//#CM46632
	/**<en>
	 * Constructs a new <CODE>AwcUser</CODE>.
	 * @param username   :user name
	 * @param password   :password
	 * @param terminal   :terminal no.
	 </en>*/
	public AwcUser( String            username, 
					String            password,
					String 	          terminal
			      )
	{
		//#CM46633
		//<en> Set for instance variables.</en>
		setUserName(username) ;
		setPassword(password) ;
		setTerminalNumber(terminal) ;
	}

	//#CM46634
	// Public methods ------------------------------------------------
	//#CM46635
	/**<en>
	 * Set the value of user name.
	 * @param awcuserKey :user name to set
	 </en>*/
	public void setUserName(String awcuserKey)
	{
		wUserName = awcuserKey ;
	}

	//#CM46636
	/**<en>
	 * Retrieve the user name.
	 * @return user name
	 </en>*/
	public String getUserName()
	{
		return wUserName ;
	}

	//#CM46637
	/**<en>
	 * Set the value of password.
	 * @param password :password to set
	 </en>*/
	public void setPassword(String password) 
	{
		wPassword = password ;
	}

	//#CM46638
	/**<en>
	 * Retrieve the password.
	 * @return password
	 </en>*/
	public String getPassword()
	{
		return wPassword ;
	}

	//#CM46639
	/**<en>
	 * Set the value of terminal.
	 * @param terminal :terminal to set
	 </en>*/
	public void setTerminalNumber(String terminal) 
	{
		wTerminal = terminal ;
	}

	//#CM46640
	/**<en>
	 * Retrieve the terminal.
	 * @return terminal
	 </en>*/
	public String getTerminalNumber()
	{
		return wTerminal ;
	}

	//#CM46641
	// Package methods -----------------------------------------------

	//#CM46642
	// Protected methods ---------------------------------------------

	//#CM46643
	// Private methods -----------------------------------------------
}
//#CM46644
//end of class
