// $Id: DispResourceMap.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

/** <jp>
 * 各プロダクトのDispResourcesを扱うためのクラスです。
 * リソース一覧などで使用します。
 * リソースの場所はToolParamで指定する必要があります。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/04</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * It is the class to handle DispResources of each product.
 * It uses by the resource list and so on.
 * You must specify the place of the resource with ToolParam.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/04</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </en> */

public class DispResourceMap
{
	// Class fields --------------------------------------------------


	// Class private fields ------------------------------------------
	private final static String RESOURCE_FILE = "DispResourceFileName";
	
	//Instance of this class.
	private static DispResourceMap instance = null;
	
	private Properties wResource;

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
	
	//Private constructors.
	private DispResourceMap()
	{
		super();
	}

	// Public methods ------------------------------------------------
	
	/**<jp>
	 * DispResourcesのキーをリストで返します。
	 * リストは昇順でソートしたものを返します。
	 * @return キーのリスト
	 </jp>*/
	/**<en>
	 * Returns the list of the keys.
	 * Returns the list what was sorted in the ascending order.
	 * @return List of the keys.
	 </en>*/	
	public static ArrayList getKeyList() throws Exception
	{
		if(instance == null)
		{
			instance = new DispResourceMap();
			instance.load(ToolParam.getProperty(RESOURCE_FILE));
		}
		
		ArrayList ret = new ArrayList();
		Iterator itr = instance.wResource.keySet().iterator();
		while(itr.hasNext())
		{
			ret.add((String)itr.next());
		}
		Collections.sort(ret);
		return ret;
	}

	/**<jp>
	 * DispResourcesのキーをリストで返します。
	 * リストは昇順でソートしたものを返します。
	 * @param regex 絞り込みのキーを正規表現で指定します。
	 * @return キーのリスト
	 </jp>*/
	/**<en>
	 * Returns the list of the keys.
	 * Returns the list what was sorted in the ascending order.
	 * @param regex The key of focusing is specified with a regular expression.
	 * 	 * @return List of the keys.
	 </en>*/	
	public static ArrayList getKeyList(String regex) throws FileNotFoundException, IOException
	{
		if(instance == null)
		{
			instance = new DispResourceMap();
			instance.load(ToolParam.getProperty(RESOURCE_FILE));
		}
		ArrayList ret = new ArrayList();
		Iterator itr = instance.wResource.keySet().iterator();
		while(itr.hasNext())
		{
			String key = (String)itr.next();
			if(key.matches(regex))	
			{
				ret.add(key);
			}
		}
		Collections.sort(ret);
		return ret;
	}
	
	
	/**<jp>
	 * DispResourcesのキーをリストで返します。このとき、sortKeyを含むキーを先頭にして並び替えます。
	 * @param sortKey ソートを行うためのキー
	 * @return キーのリスト
	 </jp>*/
	/**<en>
	 * The key of DispResources is returned by the list. At this time, the key which contains sortKey 
	 * is moved to the head.
	 * @param sortKey The key for sort.
	 * @return List of the keys.
	 </en>*/	
//	public static ArrayList getSortedKeyList(String sortKey) throws Exception
//	{
//		if(instance == null)
//		{
//			instance = new DispResourceMap();
//			instance.load(ToolParam.getProperty(RESOURCE_FILE));
//		}
//
//		ArrayList keylist = new ArrayList();
//		ArrayList otherlist = new ArrayList();
//
//		Iterator itr = instance.wResource.keySet().iterator();
//		while(itr.hasNext())
//		{
//			String key = (String)itr.next();
//			if(key.indexOf(sortKey) >= 0)
//			{
//				keylist.add(key);
//			}
//			else
//			{
//				otherlist.add(key);
//			}
//		}
//
//		Collections.sort(keylist);
//		Collections.sort(otherlist);
//		keylist.addAll(otherlist);
//		return keylist;
//	}

	
	/**<jp>
	 * keyを指定して、DispResourcesの値を取得します。
	 * @param key
	 * @return 値
	 </jp>*/
	/**<en>
	 * Return the value of DispResources. 
	 * @param key 
	 * @return value
	 </en>*/	
	public static String getText(String key) throws Exception
	{
		if(key == null) return "";

		if(instance == null)
		{
			instance = new DispResourceMap();
			instance.load(ToolParam.getProperty(RESOURCE_FILE));
		}
		
		return instance.wResource.getProperty(key);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	private void load(String path) throws FileNotFoundException, IOException
	{
		Properties prop = new Properties();
		prop.load(new FileInputStream(path));
		instance.wResource = prop;

	}
}
//end of class