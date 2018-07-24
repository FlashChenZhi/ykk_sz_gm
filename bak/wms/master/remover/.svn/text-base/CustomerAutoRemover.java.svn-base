// $Id: CustomerAutoRemover.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $
package jp.co.daifuku.wms.master.remover ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection ;
import java.util.Date ;

import jp.co.daifuku.common.InvalidDefineException ;
import jp.co.daifuku.common.NotFoundException ;
import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.wms.base.dbhandler.CustomerAlterKey ;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler ;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey ;
import jp.co.daifuku.wms.base.dbhandler.DatabaseHandler ;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.Customer ;
import jp.co.daifuku.wms.base.entity.Result;
import jp.co.daifuku.wms.base.system.dbhandler.SystemResultViewReportFinder;
import jp.co.daifuku.wms.master.MasterPrefs ;


/**
 * 出荷先マスタの自動削除クラスです。
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


public class CustomerAutoRemover
		extends AbstractAutoRemover
{
	//	------------------------------------------------------------
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
	private CustomerHandler p_handler = null ;

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
	public CustomerAutoRemover(Connection conn)
			throws ReadWriteException
	{
		super(conn) ;
	}

	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------

	/**
	 * @see jp.co.daifuku.wms.master.remover.AbstractAutoRemover#getDBHandler(java.sql.Connection)
	 */
	protected DatabaseHandler getDBHandler(Connection conn)
	{
		if (p_handler == null)
		{
			p_handler = new CustomerHandler(conn) ;
		}
		return p_handler ;
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
	 * @see jp.co.daifuku.wms.master.remover.AbstractAutoRemover#updateLastUsed(Connection)
	 * 実績を検索し、最終更新日時で出荷先マスタの使用日時を更新します。
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected void updateLastUsed(Connection conn)
			throws ReadWriteException
	{
		// 実績とマスタから荷主コード、出荷先コード一覧を取得
		SystemResultViewReportFinder rFinder = new SystemResultViewReportFinder(getConnection()) ;
		ResultViewSearchKey rKey = new ResultViewSearchKey() ;

		// 取得項目セット
		// 荷主コード
		rKey.setConsignorCodeCollect();
		// 出荷先コード
		rKey.setCustomerCodeCollect();
		// 最新更新日時
		rKey.setLastUpdateDateCollect("MAX");

		// グループ条件セット
		// 荷主コード
		rKey.setConsignorCodeGroup(1);
		// 出荷先コード
		rKey.setCustomerCodeGroup(2);

		// DNRESULTと荷主コード、出荷先コードで結合
		String[] relationalSql = { Result.CONSIGNORCODE.getName(), Result.CUSTOMERCODE.getName() };
		
		// データ検索
		if(rFinder.searchMaster(rKey, Customer.TABLE_NAME, relationalSql) <= 0)
		{
			return;
		}

		DatabaseHandler handler = getDBHandler(conn) ;

		Result result[] = null ;
		while (rFinder.isNext())
		{
			// 検索結果を100件づつ取得します。
			// 検索結果から、100件まで取得
			result = (Result[]) rFinder.getEntities(100);
			for (int i = 0; i < result.length; i++)
			{
				// 出荷先マスタ更新
				updateUsed(handler, result[i].getConsignorCode(), result[i].getCustomerCode(), result[i].getLastUpdateDate());
			}
		}
	}

	/**
	 * 最終使用日時が保持期間以上前のものを削除します。
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param holddays 最終使用日時
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected void removeOld(Connection conn, int holddays)
			throws ReadWriteException
	{
		Date term = MasterPrefs.getTermDate(holddays) ;
		DatabaseHandler handler = getDBHandler(conn) ;

		// 保持期間が設定されているときだけ削除処理
		if (term == null)
		{
			return ;
		}

		CustomerSearchKey skey = new CustomerSearchKey() ;
		
		// 検索条件セット
		// 最終使用日時
		skey.setLastUsedDate(term, "<=") ;

		try
		{
			// 該当データがあれば削除処理を行う
			if(handler.count(skey) > 0)
			{
				handler.drop(skey) ;
			}
		}
		catch (NotFoundException e)
		{
			// 何も行いません。
		}
	}

	/**
	 * 最終使用日時を更新します。
	 * 
	 * @param handler データベースハンドラ
	 * @param code			対象荷主コード
	 * @param cuCode		対象出荷先コード
	 * @param lastUsedDate	最終使用日時
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected void updateUsed(DatabaseHandler handler, String code, String cuCode, Date lastUsedDate)
			throws ReadWriteException
	{
		CustomerAlterKey akey = new CustomerAlterKey() ;
		
		// 検索条件セット
		// 荷主コード
		akey.setConsignorCode(code) ;
		// 出荷先コード
		akey.setCustomerCode(cuCode) ;

		// 更新内容セット
		// 最終更新処理名
		akey.updateLastUpdatePname(getClassName());
		// 最終使用日時
		akey.updateLastUsedDate(lastUsedDate);

		try
		{
			handler.modify(akey) ;
		}
		catch (NotFoundException e)
		{
			// 何も行いません。
		}
		catch (InvalidDefineException e)
		{
			// 発生しません。
		}
	}

	/**
	 * 最終使用日時を現在日時で更新します。
	 * 
	 * @param handler		データベースハンドラ
	 * @param code			対象荷主コード
	 * @param cuCode		対象出荷先コード
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected void updateUsed(DatabaseHandler handler, String code, String cuCode)
			throws ReadWriteException
	{
		updateUsed(handler, code, cuCode, new Date());
	}


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
		return "$Id: CustomerAutoRemover.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $" ;
	}
}
