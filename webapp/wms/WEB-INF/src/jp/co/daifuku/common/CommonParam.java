// $Id: CommonParam.java,v 1.1.1.1 2006/08/17 09:33:43 mori Exp $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import java.util.ResourceBundle;

/**<jp>
 * システムのパラメータをリソースから取得するためのクラスです。
 * リソース名称のデフォルトは、<code>CommonParam</code>となっています。
 * また、データベース接続のための<code>Connection</code>の取得が可能です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:43 $
 * @author  $Author: mori $
 </jp>*/
/**<en>
 * This class retrieves the system parameters from the resource.
 * Default resource name is <code>CommonParam</code>.
 * It also enables the retrieval of <code>Connection</code> for database connection.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:43 $
 * @author  $Author: mori $
 </en>*/
public class CommonParam extends Object
{
	// Class fields --------------------------------------------------

	/**<jp>
	 * デフォルトのリソース名
	 </jp>*/
	/**<en>
	 * Default resource name
	 </en>*/
	public static final String DEFAULT_RESOURCE = "CommonParam" ;

	// Class private fields ------------------------------------------

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returns the version of this class, 
	 * @return version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:33:43 $") ;
	}

	// Constructors --------------------------------------------------
	// No Constructors! all of method is static.

	// Public methods ------------------------------------------------
	/**<jp>
	 * キーから、パラメータの内容を取得します。
	 * @param key  取得するパラメータのキー
	 * @return   パラメータの文字列表現
	 </jp>*/
	/**<en>
	 * Gets the contents of parameter on a key basis.
	    * @param key  key of the retrieving parameter
	    * @return   string representation of parameter
	 </en>*/
	public static String getParam(String key)
	{
		ResourceBundle rb = getBundle(DEFAULT_RESOURCE, Locale.getDefault()) ;
		return (rb.getString(key)) ;
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	private static ResourceBundle getBundle(String res, Locale locale)
	{
		return (ResourceBundle.getBundle(res, locale)) ;
	}

	// debug methods -----------------------------------------------

}
//end of class

