//$Id: DispResourceHelper.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.util;
import jp.co.daifuku.bluedog.util.DispResources;
/** <jp>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/16</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/16</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </en> */
public class DispResourceHelper
{

	/**<jp>
	 * 表名、列名、値(int)をキーにしてパラメータの内容を取得します。
	 * @param tableName  取得するパラメータのキー(表名)
	 * @param colName  取得するパラメータのキー(列名)
	 * @param val  取得するパラメータのキー(値)
	 * @return       パラメータの文字列表現
	 </jp>*/
	/**<en>
	 * Getting contents of parameter using keys according to the name of list, line and value(int)
	 * @param tableName  Key of parameter to get (list name)
	 * @param colName  Key of parameter (column name)
	 * @param val  Key of getting parameter(value)
	 * @return       String representation of parameter
	 </en>*/
	public static String getText(String tableName, String colName, int val)
	{
		String key = tableName + "_" + colName + "_" + Integer.toString(val);
		return DispResources.getText(key);
	}
	
	/**<jp>
	 * 表名、列名、値(String)をキーにしてパラメータの内容を取得します。
	 * @param tableName  取得するパラメータのキー(表名)
	 * @param keyName  取得するパラメータのキー(列名)
	 * @param val  取得するパラメータのキー(値)
	 * @return       パラメータの文字列表現
	 </jp>*/
	/**<en>
	 * Getting contents of parameter using keys according to the name of list, line and value(int).
	 * @param tableName  Key of parameter to get (list name)
	 * @param colName  Key of parameter (column name))
	 * @param val  Key of getting parameter(value)
	 * @return       String representation of parameter
	 </en>*/
	public static String getText(String tableName, String colName, String val)
	{
		String key = tableName + "_" + colName + "_" + val;
		return DispResources.getText(key);
	}
	
}
