// $Id: AbstractAutoRemover.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $
package jp.co.daifuku.wms.master.remover ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection ;

import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.wms.base.dbhandler.DatabaseHandler ;
import jp.co.daifuku.wms.master.MasterPrefs ;


/**
 * マスタ自動削除の共通スーパークラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/11/02</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  ss
 * @author  Last commit: $Author: mori $
 */


public abstract class AbstractAutoRemover
		implements AutoRemover
{
	//------------------------------------------------------------
	// class variables (prefix '$')
	//------------------------------------------------------------
	private static MasterPrefs $masterPrefs ;

	/**
	 * 処理名
	 */
	protected String wClassName = "";

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	//	public static final int FIELD_VALUE = 1 ;

	//------------------------------------------------------------
	// properties (prefix 'p_')
	//------------------------------------------------------------
	private Connection p_connection ;

	//------------------------------------------------------------
	// instance variables (prefix '_')
	//------------------------------------------------------------
	//	private String	_instanceVar ;

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * マスタ定義を読み込んでインスタンスを生成します。
	 * @param conn データベースコネクション
	 * @throws ReadWriteException マスタ定義の読み込みに失敗したときスローされます。
	 */
	public AbstractAutoRemover(Connection conn)
			throws ReadWriteException
	{
		$masterPrefs = new MasterPrefs() ;
		setConnection(conn) ;
	}


	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * クラス名に値をセットします。
	 * @param セットするクラス名
	 */
	public void setClassName(String className)
	{
		wClassName = className;
	}

	/**
	 * クラス名の値の値を取得します。
	 * @return クラス名
	 */
	public String getClassName()
	{
		return wClassName;
	}

	/**
	 * 最終使用日時を更新し、使用されていないマスタデータを削除します。
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public void suppress(int holdDays)
			throws ReadWriteException
	{
		Connection conn = getConnection() ;
		
		// 最新使用日時を更新する 
		updateLastUsed(conn) ;

		// 保持日数使用していないマスタを削除する
		removeOld(conn, holdDays) ;
	}

	/**
	 * 最終使用日時を更新し、使用されていないマスタデータを削除します。
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public void suppress()
			throws ReadWriteException
	{
		int holdDays = (new MasterPrefs()).getHoldDays() ;
		suppress(holdDays) ;
	}

	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * @return MasterPrefsを返します。
	 */
	public MasterPrefs getMasterPrefs()
	{
		return $masterPrefs ;
	}


	/**
	 * @return connectionを返します。
	 */
	public Connection getConnection()
	{
		return p_connection ;
	}


	/**
	 * @param connection connectionを設定します。
	 */
	public void setConnection(Connection connection)
	{
		p_connection = connection ;
	}

	//------------------------------------------------------------
	// package methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// protected methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * 最終使用日時を更新します。
	 * @param conn データベースコネクション
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected abstract void updateLastUsed(Connection conn)
			throws ReadWriteException ;

	protected abstract void removeOld(Connection conn, int holddays)
			throws ReadWriteException ;

	protected abstract DatabaseHandler getDBHandler(Connection conn) ;

	//------------------------------------------------------------
	// private methods
	//------------------------------------------------------------

	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
	/**
	 * このクラスのリビジョンを返します。
	 * @return リビジョン文字列。
	 */
	public static String getVersion()
	{
		return "$Id: AbstractAutoRemover.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $" ;
	}
}
