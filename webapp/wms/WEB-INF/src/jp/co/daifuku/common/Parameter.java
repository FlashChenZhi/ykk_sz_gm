//$Id: Parameter.java,v 1.1.1.1 2006/08/17 09:33:43 mori Exp $

package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**<jp>
 * パラメータを規定するためのインターフェースです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:43 $
 * @author  $Author: mori $
 </jp>*/
/**<en>
 * This is the interface that regulates the parameters.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:43 $
 * @author  $Author: mori $
 </en>*/
public class Parameter 
{

	// Class fields --------------------------------------------------
	/**<jp>
	 * ユーザ名
	 </jp>*/
	/**<en>
	 * User's name
	 </en>*/
	protected String wUserName;

	/**<jp>
	 * 端末No
	 </jp>*/
	/**<en>
	 * Terminal number
	 </en>*/
	protected String wTerminalNumber;

	// Class variables -----------------------------------------------
	/**<jp>
	 * デリミタ
	 </jp>*/
	/**<en>
	 * Delimiter
	 </en>*/
	public final String wDelim = MessageResource.DELIM ;

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**<jp>
	 * ユーザ名を設定します。
	 * @param name ユーザ名
	 </jp>*/
	/**<en>
	 * Sets the user's name
	 * @param name User's name
	 </en>*/
	public void setUserName(String name)
	{
		wUserName = name ;
	}

	/**<jp>
	 * ユーザ名を取得します。
	 * @return ユーザ名
	 </jp>*/
	/**<en>
	 * Retrieves the user's name.
	 * @return User's name
	 </en>*/
	public String getUserName()
	{
		return wUserName ;
	}

	/**<jp>
	 * 端末Noを設定します。
	 * @param terminalno 端末No
	 </jp>*/
	/**<en>
	 * Sets the terminal number.
	 * @param terminalno terminal number
	 </en>*/
	public void setTerminalNumber(String terminalno)
	{
		wTerminalNumber = terminalno ;
	}

	/**<jp>
	 * 端末Noを取得します。
	 * @return 端末No
	 </jp>*/
	/**<en>
	 * Retrieves the terminal number.
	 * @return terminal number
	 </en>*/
	public String getTerminalNumber()
	{
		return wTerminalNumber ;
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

