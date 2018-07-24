// $Id: ReadWriteException.java,v 1.1.1.1 2006/08/17 09:33:43 mori Exp $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * 外部記憶装置へのアクセスにおいて、異常が発生した場合に使用する例外です。
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
 * This exception is used for errors occurred during the access to the external storage device.
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
public class ReadWriteException extends java.lang.Exception
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
	 * Constructs Exception without assigning the detail message.
	 </en>*/
	public ReadWriteException()
	{
		super() ;
	}

	/**<jp>
	 * メッセージ付きの例外を作成します。
	 * @param msg  詳細メッセージ
	 </jp>*/
	/**<en>
	 * Creates the exception with message attached.
	 * @param msg  Detail message
	 </en>*/
	public ReadWriteException(String msg)
	{
		super(msg) ;
	}
}
//end of class

