// $Id: SubMenuBusiness.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.menu;
import java.util.ArrayList;
import java.util.Iterator;

import jp.co.daifuku.UserInfo;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.tools.util.ToolParam;

/** <jp>
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * This screen class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </en> */
public class SubMenuBusiness extends SubMenu
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/** <jp>
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 </jp> */
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		String userid = this.request.getParameter( "UserID" );
		if(userid == null)
		{
			userid = "N/A";
		}
		UserInfo user = new UserInfo();
		user.setUserId(userid);
		setUserInfo(user);
		
	}
	//FTTB
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}
	
	public static boolean isDisplayFunction(String function, String menuType)
	{
		if(menuType.equals("0"))
		{
			return true;
		}
		
		
		Iterator itr = ToolParam.keySet().iterator();
		ArrayList list = new ArrayList();
		String sKey = "Menu." + function;

		while(itr.hasNext())
		{
			String key = (String)itr.next();
			if(key.indexOf(sKey) >= 0)
			{
				String value = ToolParam.getProperty(key);
				if(value.equals("true"))
				{
					return true;
				}
			}
		}
		return false;
	}
	public static boolean isDisplayButton(String function, String menuType)
	{
		if(menuType.equals("0"))
		{
			return true;
		}
		Iterator itr = ToolParam.keySet().iterator();
		ArrayList list = new ArrayList();
		String sKey = "Menu." + function;

		String value = ToolParam.getProperty(sKey, "");
		if(value.equals("true"))
		{
			return true;
		}
		return false;			
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Event handler methods -----------------------------------------

}
//end of class
