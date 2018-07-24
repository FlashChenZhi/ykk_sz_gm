//$Id: ShelfInvalidityException.java,v 1.1.1.1 2006/08/17 09:33:43 mori Exp $
package jp.co.daifuku.common;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * 入力ロケーション無効の場合に通知される例外です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version 
 * @author  
 </jp>*/
public class ShelfInvalidityException extends java.lang.Exception 
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returns the version of this class
	 * @return version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $") ;
	}
	
	// Constructors --------------------------------------------------
	
	/**<jp>
	 * 詳細メッセージを指定しないで Exception を構築します
	 </jp>*/
	/**<en>
	 * Constructs the Exception without specifying the detail message.
	 </en>*/
	public ShelfInvalidityException()
	{
		super() ;
	}

	/**<jp>
	 * メッセージ付きの例外を作成します。
	 * @param msg  詳細メッセージ
	 </jp>*/
	/**<en>
	 * Creates the exception with message attached
	 * @param msg  Detail message
	 </en>*/
	public ShelfInvalidityException(String msg)
	{
		super(msg) ;
	}
}
