// $Id: AbstractMasterOperator.java,v 1.2 2006/11/10 00:35:36 suresh Exp $
package jp.co.daifuku.wms.master.operator ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal ;
import java.sql.Connection ;
import java.util.Date ;

import jp.co.daifuku.common.DataExistsException ;
import jp.co.daifuku.common.InvalidDefineException ;
import jp.co.daifuku.common.NotFoundException ;
import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.wms.base.common.AlterKey ;
import jp.co.daifuku.wms.base.common.SearchKey ;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey ;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey ;
import jp.co.daifuku.wms.base.dbhandler.DatabaseHandler ;
import jp.co.daifuku.wms.base.dbhandler.FieldName ;
import jp.co.daifuku.wms.base.entity.AbstractEntity ;
import jp.co.daifuku.wms.base.entity.AbstractMaster ;
import jp.co.daifuku.wms.master.MasterPrefs ;


/**
 * マスタ操作共通の仮想クラスです。マスタ操作の概要を記述し、
 * 各マスタ用のサブクラス共通部分のコードをまとめます。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/11/01</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:35:36 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public abstract class AbstractMasterOperator
		implements MasterOperator
{
	//------------------------------------------------------------
	// class variables (prefix '$')
	//------------------------------------------------------------
	//	private String	$classVar ;

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	//	public static final int FIELD_VALUE = 1 ;

	//------------------------------------------------------------
	// properties (prefix 'p_')
	//------------------------------------------------------------
	private Connection p_connection ;

	private DatabaseHandler p_databaseHandler ;

	private MasterPrefs p_masterPrefs ;

	//------------------------------------------------------------
	// instance variables (prefix '_')
	//------------------------------------------------------------


	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * データベースコネクションを指定してインスタンスを生成します。<br>
	 * このコンストラクタ内でデータベースハンドラを生成し、
	 * マスタパッケージの設定ファイルを読み込みますので、
	 * ループの内部でこのインスタンスをコンストラクトしないでください。<br>
	 * インスタンス生成のオーバヘッドにより、パフォーマンスに悪影響があります。
	 * @param conn データベースコネクション
	 * @throws ReadWriteException 初期設定の読み込みに失敗したときスローされます。
	 */
	public AbstractMasterOperator(Connection conn)
			throws ReadWriteException
	{
		setConnection(conn) ;
		setDatabaseHandler(getDBHandler(conn)) ;
		setMasterPrefs(new MasterPrefs()) ;
	}


	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * マスタの新規登録を行います。
	 * @param ent 作成するマスタのエンティティ
	 * @return 新規登録の結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public int create(AbstractEntity ent)
			throws ReadWriteException
	{
		DatabaseHandler handler = getDatabaseHandler() ;

		try
		{
			// コード登録済みチェック
			if (exist(ent))
			{
				return RET_EXIST ;
			}

			int ret = checkCreateConsistent(ent) ;
			if (ret != RET_OK)
			{
				return ret ;
			}
			handler.create(ent) ;
		}
		catch (DataExistsException e)
		{
			return RET_EXIST ;
		}
		return RET_OK ;
	}

	/**
	 * マスタの更新を行います。
	 * @param ent 更新するマスタのエンティティ
	 * @return 更新の結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public int modify(AbstractEntity ent)
			throws ReadWriteException
	{
		DatabaseHandler handler = getDatabaseHandler() ;
		try
		{
			// コード登録済みチェック
			if (!exist(ent))
			{
				return RET_NOT_EXIST ;
			}

			int ret = checkModifyConsistent(ent) ;
			if (ret != RET_OK)
			{
				return ret ;
			}
			AlterKey akey = createAlterKey(ent) ;
			handler.modify(akey) ;
		}
		catch (NotFoundException e)
		{
			return RET_NOT_EXIST ;
		}
		catch (InvalidDefineException e)
		{
			return RET_FATAL_ERROR ;
		}
		return RET_OK ;
	}

	/**
	 * マスタの削除を行います。<br>
	 * 実際にテーブルから削除されます。削除フラグのみを更新する場合は
	 * drop()を使用してください。
	 * @param ent 削除するマスタのエンティティ
	 * @return 削除の結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public int actualDrop(AbstractEntity ent)
			throws ReadWriteException
	{
		DatabaseHandler handler = getDatabaseHandler() ;
		try
		{
			int ret = checkDropConsistent(ent) ;
			if (ret != RET_OK)
			{
				return ret ;
			}
			SearchKey skey = createSearchKey(ent, false) ;
			handler.drop(skey) ;
		}
		catch (NotFoundException e)
		{
			return RET_NOT_EXIST ;
		}
		return RET_OK ;
	}


	/**
	 * マスタの削除を行います。<br>
	 * 削除フラグを削除済みに更新します。実際にテーブルから削除する場合は
	 * actualDrop()を使用してください。
	 * @param ent 削除するマスタのエンティティ
	 * @return 削除の結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public int drop(AbstractEntity ent)
			throws ReadWriteException
	{
		int ret = checkDropConsistent(ent) ;
		if (ret != RET_OK)
		{
			return ret ;
		}
		ent.setValue(AbstractMaster.DELETEFLAG, AbstractMaster.DELETE_FLAG_DELETED) ;
		return modify(ent) ;
	}

	/**
	 * 該当データがすでに存在するかどうかチェックします。
	 * @param ent チェックするマスタのエンティティ
	 * @return 登録済みの時 true.
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public boolean exist(AbstractEntity ent)
			throws ReadWriteException
	{
		DatabaseHandler handler = getDatabaseHandler() ;
		SearchKey skey = createSearchKey(ent, true) ;
		int cnt = handler.count(skey) ;
		return (cnt > 0) ;
	}


	/**
	 * SearchKey(AbstractSQLSearchKey)から、setValue()用の
	 * カラム名を生成します。(テーブル名 . カラム名)
	 * 
	 * @param skey 対象のSearchKey
	 * @param fld 対象のフィールド
	 * @return カラム名
	 */
	public static String createColumnName(SearchKey skey, FieldName fld)
	{
		if (skey instanceof AbstractSQLSearchKey)
		{
			AbstractSQLSearchKey key = (AbstractSQLSearchKey)skey ;
			String prefix = key.getTableName() + "." ;
			return prefix + fld.getName() ;
		}
		return null ;
	}


	/**
	 * AlterKey(AbstractSQLAlterKey)から、setValue()用の
	 * カラム名を生成します。(テーブル名 . カラム名)
	 * 
	 * @param akey 対象のAlterKey
	 * @param fld 対象のフィールド
	 * @return カラム名
	 */
	public static String createColumnName(AlterKey akey, FieldName fld)
	{
		if (akey instanceof AbstractSQLAlterKey)
		{
			AbstractSQLAlterKey key = (AbstractSQLAlterKey)akey ;
			String prefix = key.getTableName() + "." ;
			return prefix + fld.getName() ;
		}
		return null ;
	}

	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------

	/**
	 * @return masterPrefsを返します。
	 */
	public MasterPrefs getMasterPrefs()
	{
		return p_masterPrefs ;
	}


	/**
	 * @param masterPrefs masterPrefsを設定します。
	 */
	protected void setMasterPrefs(MasterPrefs masterPrefs)
	{
		p_masterPrefs = masterPrefs ;
	}


	/**
	 * 内部に保持しているConnectionを返します。<br>
	 * 新たにセッションを取るわけではありません。
	 * @return Connectionを返します。
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


	/**
	 * @return databaseHandlerを返します。
	 */
	public DatabaseHandler getDatabaseHandler()
	{
		return p_databaseHandler ;
	}


	/**
	 * @param databaseHandler databaseHandlerを設定します。
	 */
	protected void setDatabaseHandler(DatabaseHandler databaseHandler)
	{
		p_databaseHandler = databaseHandler ;
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
	 * マスタ用のデータベースハンドラを返します。
	 * @param conn データベースコネクション
	 * @return マスタ用のデータベースハンドラ
	 */
	protected abstract DatabaseHandler getDBHandler(Connection conn) ;

	/**
	 * 作成時のチェックを行います。
	 * @param ent 作成しようとするデータのエンティティ
	 * 
	 * @return チェック結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。 
	 */
	protected abstract int checkCreateConsistent(AbstractEntity ent)
			throws ReadWriteException ;

	/**
	 * 更新時のチェックを行います。
	 * @param ent 更新しようとするデータのエンティティ
	 * 
	 * @return チェック結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected abstract int checkModifyConsistent(AbstractEntity ent)
			throws ReadWriteException ;

	/**
	 * 削除時のチェックを行います。
	 * @param key 削除しようとするデータのエンティティ
	 * 
	 * @return チェック結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected abstract int checkDropConsistent(AbstractEntity key)
			throws ReadWriteException ;

	/**
	 * 更新用のAlterKeyを作成して返します。
	 * 
	 * @param key 更新対象データ
	 * @return 更新用AlterKey
	 */
	protected abstract AlterKey createAlterKey(AbstractEntity key) ;

	/**
	 * 削除・検索用検索キーを作成して返します。
	 * 
	 * @param key 削除・検索対象データ
	 * @param liveOnly 削除フラグが 有効の物のみ対象とするときtrue.
	 * @return 削除・検索用検索キー
	 */
	protected abstract SearchKey createSearchKey(AbstractEntity key, boolean liveOnly) ;

	//------------------------------------------------------------
	// private methods
	//------------------------------------------------------------


	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
	/**
	 * オブジェクトの型に従って、AlterKeyのUpdateValueへのセットを行います。
	 * @param akey セット先のAlterKey
	 * @param fld 対象のフィールド
	 * @param newValue セットする値
	 */
	protected static void setUpdateValueToAlterKey(AlterKey akey, FieldName fld, Object newValue)
	{
		String colName = createColumnName(akey, fld) ;
		if (newValue instanceof Date)
		{
			akey.setUpdValue(colName, (Date)newValue) ;
		}
		else if (newValue instanceof String)
		{
			akey.setUpdValue(colName, (String)newValue) ;
		}
		else if (newValue instanceof Integer)
		{
			Integer nv = (Integer)newValue ;
			akey.setUpdValue(colName, nv.intValue()) ;
		}
		else if (newValue instanceof BigDecimal)
		{
			BigDecimal bd = (BigDecimal)newValue ;
			if (bd.scale() == 0)
			{
				akey.setUpdValue(colName, bd.longValue()) ;
			}
			else
			{
				akey.setUpdValue(colName, bd.floatValue()) ;
			}
		}
	}

	/**
	 * このクラスのリビジョンを返します。
	 * @return リビジョン文字列。
	 */
	public static String getVersion()
	{
		return "$Id: AbstractMasterOperator.java,v 1.2 2006/11/10 00:35:36 suresh Exp $" ;
	}


}
