// $Id: ToolParam.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;


/** <jp>
 * TOOL用のプロパティファイルを読み込むためのクラスです。
 * リソース名称のデフォルトは、<code>ToolParam</code>となっています。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/17</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * This class is to get parameters of Tool system resource.
 * Default resource name is <code>ToolParam</code>.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/17</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </en> */

public class ToolParam
{
	// Class fields --------------------------------------------------
	//Delimが連続している場合に、それを識別するための区切り文字
	private static final String E_DELIM = "   ";
	
	/**<jp> デフォルトのリソース名
	 </jp>*/
	/**<en>
	 * Default resource name
	 </en>*/
	public static final String DEFAULT_RESOURCE = "/MenuTool.properties" ;
	
	private static Properties prop = null;

	// Class private fields ------------------------------------------

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returns version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:33:11 $") ;
	}

	// Constructors --------------------------------------------------
	// No Constructors! all of method is static.

	// Public methods ------------------------------------------------
	
	/**<jp>
	 * Propertiesに格納されているキーのSet ビューを返します。
	 * @return   マップに含まれているキーのセットビュー
	 </jp>*/
	/**<en>
	 * Returns a Set view of the keys contained in this Properties.
	 * @return   a set view of the keys contained in this map.
	 </en>*/
	public static Set keySet()
	{
		if(prop == null)
		{
			load();
		}
		return prop.keySet();
	}


	/**<jp>
	 * キーから、パラメータの内容を取得します。
	 * @param key  取得するパラメータのキー
	 * @return   パラメータの文字列表現
	 </jp>*/
	/**<en>
	 * Gets contents of parameter by key.
	 * @param key  Key of parameter to get
	 * @return   Parameter in String
	 </en>*/
	public static String getProperty(String key)
	{
		return getProperty(key, null);
	}
	/**<jp>
	 * キーから、パラメータの内容を取得します。
	 * @param key  取得するパラメータのキー
	 * @param defaultValue  デフォルト値
	 * @return   パラメータの文字列表現
	 </jp>*/
	/**<en>
	 * Gets contents of parameter by key.
	 * @param key  Key of parameter to get
	 * @param defaultValue  default value
	 * @return   Parameter in String
	 </en>*/
	public static String getProperty(String key, String defaultValue)
	{
		if(prop == null)
		{
			load();
		}
		return (prop.getProperty(key, defaultValue)) ;
	}
	
	
	/**<jp>
	 * プロパティファイルに定義されたカンマ区切りの値を取得し、Listとして返します。
	 * @param connString  カンマ区切りの文字列
	 * @return   <code>List</code>オブジェクト
	 </jp>*/
	/**<en>
	 * The value of the comma separated defined by a properties file is acquired and returned as List.
	 * @param connString  
	 * @return   <code>List</code> Object
	 </en>*/
	public static List getList(String connString)
	{
		String delim = ",";
		
		if(connString == null)
		{
			return null;
		}
		connString = delimiterCheck(connString, delim);
		StringTokenizer token = new StringTokenizer(connString, delim);
		List list = new ArrayList();
		while(token.hasMoreTokens())
		{
			String buf = token.nextToken();
			if(buf.equals(E_DELIM))
			{
				list.add("");
			}
			else
			{
				list.add(buf);
			}
		}
		return list;
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**<jp>
	 * 引数として受け取ったStringでデリミタが連続しているところをひとつスペースを空ける。<BR>
	 * Ex item0001,100,200,,300, -> item0001,100,200, ,300,
	 </jp>*/
	/**<en>
	 * Providing a space in between the consecutive delimiters in the String accepted as argument<BR>
	 * Ex item0001,100,200,,300, -> item0001,100,200, ,300,
	 </en>*/
	private static String delimiterCheck(String str, String delim)
	{
		if(str == null || str.equals("")) return str;
		
		//先頭に区切り文字がくる場合は、先頭にE_DELIMを付加
		if(str.startsWith(delim))
		{
			str = E_DELIM + str;
		}
		
		StringBuffer sb = new StringBuffer(str) ;
		int len = sb.length() ;
		int i   = 0 ;
		for ( i = 0; i < len; i++)
		{
			if ( i < len - 1 )
			{
				if (sb.substring(i, i+2).equals( delim + delim ))
				{
					sb.replace(i, i+2, delim + E_DELIM + delim) ;
				}
			}
			len = sb.length() ;
		}
		if (sb.substring(len-1, len).equals(delim))
		{
			sb = sb.append(E_DELIM) ;
		}
		return (sb.toString()) ;
	}
	
	//Load the Property file.
	private static void load()
	{
		try
		{
			prop = new Properties();
			InputStream in =(ToolParam.class).getClassLoader().getResourceAsStream(DEFAULT_RESOURCE);
			//MenuTool.properties Not found.
			if(in == null)
			{
				throw new FileNotFoundException("MenuTool.properties Not found.");
			}
			else
			{
				prop.load(in);
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
//end of class