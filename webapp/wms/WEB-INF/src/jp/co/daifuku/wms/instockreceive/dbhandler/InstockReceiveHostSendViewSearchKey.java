// $Id: InstockReceiveHostSendViewSearchKey.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $
package jp.co.daifuku.wms.instockreceive.dbhandler;

import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.HostSendViewSearchKey;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * DVHOSTSENDVIEW表を検索するためのキーをセットするクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class InstockReceiveHostSendViewSearchKey extends HostSendViewSearchKey
{
	// Class fields --------------------------------------------------
	// テーブル名
	private static final String TABLE = "DVHOSTSENDVIEW"; 

	// ここに結合される可能性のあるカラムを定義します。
	private static final String PLANUKEY					= "PLAN_UKEY";
	
	/**
	 * テーブル結合のテーブル名 保存用
	 */
	protected String JoinTable = "JoinTable";

	/**
	 * テーブル結合のSelect句 保存用配列
	 */
	protected Vector JoinColumnsVec = new Vector() ;

	/**
	 * テーブル結合のWhere句 保存用配列
	 */
	protected Vector JoinWhereVec = new Vector() ;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョン
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $") ;
	}

	// Constructors --------------------------------------------------
	/**
	 * Comment for constructor (コンストラクタのコメント）
	 * @param ap  Comment for parameter
	 */
	public InstockReceiveHostSendViewSearchKey()
	{
		super();
	}

	// Public methods ------------------------------------------------

	/**
	 * テーブル結合のカラムをセット
	 */
	public void setPlanUkeyJoinCollect()
	{
		setJoinColumns(PLANUKEY);
	}

	/**
	 * テーブル結合のテーブル名をセット
	 */
	public void setJoinTable(String table)
	{
		JoinTable = table;
	}

	/**
	 * テーブル結合のテーブル名を取得します。
	 */
	public String getJoinTable()
	{
		return JoinTable;
	}

	/**
	 * 一意キーの結合値をセット
	 */
	public void setPlanUkeyJoin()
	{
		setJoinWhere(PLANUKEY, PLANUKEY);
	}

	/**
	 * 設定されたキーよりSQLのSELECT句のカラムを生成を行います。
	 */
	public String ReferenceJoinColumns() throws ReadWriteException
	{
		StringBuffer stbf = new StringBuffer(512) ;
		boolean existFlg = false;

		JoinKey[] columns = new JoinKey[JoinColumnsVec.size()];
		JoinColumnsVec.copyInto(columns);

		for (int i = 0 ; i < columns.length ; i++)
		{
			if(existFlg)
			{
				stbf.append(", ");
			}
			stbf.append(JoinTable + "." + columns[i].getColumn1());
			existFlg = true;
		}

		if(!existFlg)
		{
			return null;
		}

		return stbf.toString().substring(0, stbf.length());
	}

	/**
	 * 設定されたキーよりSQLのWHERE句の結合条件を生成を行います。
	 */
	public String ReferenceJoinWhere() throws ReadWriteException
	{
		StringBuffer stbf = new StringBuffer(512) ;
		boolean existFlg = false;

		JoinKey[] columns = new JoinKey[JoinWhereVec.size()];
		JoinWhereVec.copyInto(columns);

		for (int i = 0 ; i < columns.length ; i++)
		{
			if(existFlg)
			{
				stbf.append(" AND ");
			}
			stbf.append(TABLE+"."+columns[i].getColumn1()+" = "+ JoinTable + "."+columns[i].getColumn2());
			existFlg = true;
		}

		if(!existFlg)
		{
			return null;
		}

		return stbf.toString().substring(0, stbf.length());
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * 結合したテーブルのSELECT句にセットするカラムのセットを行います。
	 */
	private void setJoinColumns(String key)
	{
		JoinKey jkey = new JoinKey();
		jkey.setColumn1(key);

		JoinColumnsVec.addElement(jkey);
	}

	/**
	 * テーブル結合するWhere句のセットを行います。
	 */
	private void setJoinWhere(String key1, String key2)
	{
		JoinKey jkey = new JoinKey();
		jkey.setColumn1(key1);
		jkey.setColumn2(key2);

		JoinWhereVec.addElement(jkey);
	}

	// Inner Class ---------------------------------------------------
	/**
	 * 結合のカラムを保持する内部クラスです。
	 */
	protected class JoinKey
	{
		private String  Column1 ;    // カラム
		private String  Column2 ;    // カラム

		/**
		 * カラムをセットします。
		 */
		protected void setColumn1(String column)
		{
			Column1 = column ;
		}
		/**
		 * カラムを取得します。
		 */
		protected String getColumn1()
		{
			return Column1 ;
		}

		/**
		 * カラムをセットします。
		 */
		protected void setColumn2(String column)
		{
			Column2 = column ;
		}
		/**
		 * カラムを取得します。
		 */
		protected String getColumn2()
		{
			return Column2 ;
		}
	}

}
//end of class

