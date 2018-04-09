// $Id: AutoRegister.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $
package jp.co.daifuku.wms.master.autoregist ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection ;

import jp.co.daifuku.common.InvalidDefineException ;
import jp.co.daifuku.common.InvalidStatusException ;
import jp.co.daifuku.common.NoPrimaryException ;
import jp.co.daifuku.common.NotFoundException ;
import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.wms.base.common.AlterKey ;
import jp.co.daifuku.wms.base.common.Entity ;
import jp.co.daifuku.wms.base.common.SearchKey ;
import jp.co.daifuku.wms.base.dbhandler.DatabaseHandler ;
import jp.co.daifuku.wms.base.entity.AbstractEntity ;
import jp.co.daifuku.wms.master.MasterPrefs ;
import jp.co.daifuku.wms.master.merger.EntityMerger ;
import jp.co.daifuku.wms.master.operator.MasterOperator ;


/**
 * マスタ自動登録のためのスーパークラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/11/08</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  ss
 * @author  Last commit: $Author: mori $
 */


public abstract class AutoRegister
{

	//------------------------------------------------------------
	// class variables (prefix '$')
	//------------------------------------------------------------
	private static MasterPrefs $masterPrefs = null ;

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
	 * データベースコネクションを指定してインスタンスを生成します。
	 * @param conn データベースコネクション
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	public AutoRegister(Connection conn)
			throws ReadWriteException
	{
		setConnection(conn) ;
		if ($masterPrefs == null)
		{
			$masterPrefs = new MasterPrefs() ;
		}
	}


	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * 予定データなどから作成された、各マスタ用のエンティティから
	 * マスタに登録またはマスタデータの更新を行います。
	 * @param ent 予定データから作成された各マスタ用のエンティティ
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 * @throws NoPrimaryException 検索キーから一意に絞り込みが出来なかったときスローされます。
	 * @throws InvalidStatusException ステータス異常が発生したときスローされます。
	 */
	public void autoRegist(AbstractEntity ent)
			throws ReadWriteException,
				NoPrimaryException,
				InvalidStatusException
	{
		if (ent == null)
		{
			return ;
		}

		int mgType = getMasterPrefs().getAutoRegistType() ;
		// 自動登録でない場合は何もしないで下さい。
		if (mgType == MasterPrefs.MERGE_REGIST_TYPE_NONE)
			return ;


		// 該当データを検索
		DatabaseHandler handler = getDBHandler(getConnection()) ;
		SearchKey skey = createSearchKey(ent) ;

		AbstractEntity dbinent = null ;
		Entity tmpent = handler.findPrimaryForUpdate(skey) ;
		// "instanceof"のチェックはv3のハンドラでは削除します。
		if (tmpent instanceof AbstractEntity)
		{
			dbinent = (AbstractEntity)tmpent ;
		}

		// 登録しなかった場合は、新しいレコードを作成します。
		if (dbinent == null)
		{
			MasterOperator operator = getMasterOperator(getConnection()) ;
			operator.create(ent) ;
			return ;
		}

		// マージ・更新の場合
		switch (mgType)
		{
			case MasterPrefs.MERGE_REGIST_TYPE_FILL_EMPTY:
			case MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE:
				// マージエンティティでレコードを更新します。
				EntityMerger mger = new EntityMerger(mgType) ;
				mger.merge(dbinent, ent) ;

				AlterKey akey = createAlterKey(dbinent) ;
				try
				{
					handler.modify(akey) ;
				}
				catch (NotFoundException e)
				{
					// 発生しません。
				}
				catch (InvalidDefineException e)
				{
					// 発生しません。
				}
				break ;
		}
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
	 * マスタ用のオペレータを返します。
	 * @param conn データベースコネクション
	 * 
	 * @return マスタオペレータ
	 * @throws ReadWriteException 
	 */
	protected abstract MasterOperator getMasterOperator(Connection conn)
			throws ReadWriteException ;

	/**
	 * マスタ用のデータベースハンドラを返します。
	 * 
	 * @param conn データベースコネクション
	 * @return データベースハンドラ
	 */
	protected abstract DatabaseHandler getDBHandler(Connection conn) ;

	/**
	 * マスタ用の検索キーを返します。
	 * 
	 * @param ent 登録しようとしているマスタエンティティ
	 * @return 検索キー
	 */
	protected abstract SearchKey createSearchKey(AbstractEntity ent) ;

	/**
	 * マスタ用の更新キーを返します。
	 * 
	 * @param dbinent 登録しようとしているマスタエンティティ
	 * @return 更新キー
	 */
	protected abstract AlterKey createAlterKey(AbstractEntity dbinent) ;


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
		return "$Id: AutoRegister.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $" ;
	}
}
