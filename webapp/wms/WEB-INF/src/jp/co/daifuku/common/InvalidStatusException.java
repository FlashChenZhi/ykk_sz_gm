// $Id: InvalidStatusException.java,v 1.1.1.1 2006/08/17 09:33:43 mori Exp $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * 状態を持つオブジェクトに対して、範囲外の状態をセットした場合に使用される例外です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:43 $
 * @author  $Author: mori $
 </jp>*/
/**<en>
 * This exception is used when status is set beyond the acceptable range while the object 
   already has own status.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:43 $
 * @author  $Author: mori $
 </en>*/
public class InvalidStatusException extends java.lang.Exception
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returns the version of this class.
	 * @return version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:33:43 $") ;
	}

	// Constructors --------------------------------------------------
	/**<jp>
	 * 詳細メッセージを指定しないで Exception を構築します
	 </jp>*/
	/**<en>
	 * Constructs the Exception without specifying the detail message.
	 </en>*/
	public InvalidStatusException()
	{
		super() ;
	}

	/**<jp>
	 * メッセージ付きの例外を作成します。
	 * @param msg  詳細メッセージ
	 </jp>*/
	/**<en>
	 * Creates the exception with message attached.
	 * @param msg  detail message
	 </en>*/
	public InvalidStatusException(String msg)
	{
		super(msg) ;
	}
}
//end of class

