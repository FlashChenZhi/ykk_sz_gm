// $Id: MergerWrapper.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $
package jp.co.daifuku.wms.master.merger ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection ;

import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey ;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey ;
import jp.co.daifuku.wms.base.dbhandler.DatabaseHandler ;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey ;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey ;
import jp.co.daifuku.wms.base.entity.AbstractEntity ;
import jp.co.daifuku.wms.master.MasterPrefs ;
import jp.co.daifuku.wms.master.operator.ConsignorOperator ;
import jp.co.daifuku.wms.master.operator.CustomerOperator ;
import jp.co.daifuku.wms.master.operator.ItemOperator ;
import jp.co.daifuku.wms.master.operator.SupplierOperator ;


/**
 * 補完のための仮想ラッパークラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/11/14</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  ss
 * @author  Last commit: $Author: mori $
 */


public abstract class MergerWrapper
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

	private EntityMerger p_entityMerger ;

	private ConsignorOperator p_consignorOperator ;

	private CustomerOperator p_customerOperator ;

	private SupplierOperator p_supplierOperator ;

	private ItemOperator p_itemOperator ;

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
	public MergerWrapper(Connection conn)
			throws ReadWriteException
	{
		setConnection(conn) ;

		int mgType = getMasterPrefs().getMergeType() ;
		setEntityMerger(new EntityMerger(mgType)) ;
	}

	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * 入荷予定データに対して、マスタからデータを補完します。
	 * @param ent 元データ
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	public void complete(AbstractEntity ent)
			throws ReadWriteException
	{
		// 荷主マスタ補完
		completeConsignor(ent) ;

		// 出荷先マスタ補完
		completeCustomer(ent) ;

		// 入荷先マスタ補完
		completeSupplier(ent) ;

		// 商品マスタ補完
		completeItem(ent) ;
	}


	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
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


	/**
	 * @return entityMergerを返します。
	 */
	public EntityMerger getEntityMerger()
	{
		return p_entityMerger ;
	}

	/**
	 * @param entityMerger entityMergerを設定します。
	 */
	public void setEntityMerger(EntityMerger entityMerger)
	{
		p_entityMerger = entityMerger ;
	}


	/**
	 * @return $masterPrefsを返します。
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	protected static MasterPrefs getMasterPrefs()
			throws ReadWriteException
	{
		if ($masterPrefs == null)
		{
			$masterPrefs = new MasterPrefs() ;
		}
		return $masterPrefs ;
	}

	/**
	 * @return consignorOperatorを返します。
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	protected ConsignorOperator getConsignorOperator()
			throws ReadWriteException
	{
		if (p_consignorOperator == null)
		{
			p_consignorOperator = new ConsignorOperator(getConnection()) ;
		}
		return p_consignorOperator ;
	}


	/**
	 * @return CustomerOperatorを返します。
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	protected CustomerOperator getCustomerOperator()
			throws ReadWriteException
	{
		if (p_customerOperator == null)
		{
			p_customerOperator = new CustomerOperator(getConnection()) ;
		}
		return p_customerOperator ;
	}

	/**
	 * @return SupplierOperatorを返します。
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	protected SupplierOperator getSupplierOperator()
			throws ReadWriteException
	{
		if (p_supplierOperator == null)
		{
			p_supplierOperator = new SupplierOperator(getConnection()) ;
		}
		return p_supplierOperator ;
	}

	/**
	 * @return ItemOperatorを返します。
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	protected ItemOperator getItemOperator()
			throws ReadWriteException
	{
		if (p_itemOperator == null)
		{
			p_itemOperator = new ItemOperator(getConnection()) ;
		}
		return p_itemOperator ;
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
	 * 商品マスタからの補完を行います。
	 * 
	 * @param ent 補完先の予定データ
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected void completeItem(AbstractEntity ent)
			throws ReadWriteException
	{
		// 商品マスタ 取得
		DatabaseHandler handler = getItemOperator().getDatabaseHandler() ;
		ItemSearchKey skey = new ItemSearchKey() ;

		// 荷主コード取得
		String concode = getConsignorCode(ent) ;
		if (concode == null || concode.length() == 0)
		{
			return ;
		}
		skey.setConsignorCode(concode) ;

		// 商品コード取得
		String abcode = getItemCode(ent) ;
		if (abcode == null || abcode.length() == 0)
		{
			return ;
		}
		skey.setItemCode(abcode) ;
		AbstractEntity[] entArr = (AbstractEntity[])handler.find(skey) ;

		if (entArr == null || entArr.length == 0)
		{
			return ;
		}
		getEntityMerger().merge(ent, entArr[0]) ;
	}

	/**
	 * 仕入先マスタからの補完を行います。
	 * 
	 * @param ent 補完先の予定データ
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected void completeSupplier(AbstractEntity ent)
			throws ReadWriteException
	{
		// 入荷先マスタ 取得
		DatabaseHandler handler = getSupplierOperator().getDatabaseHandler() ;
		SupplierSearchKey skey = new SupplierSearchKey() ;

		// 荷主コード取得
		String concode = getConsignorCode(ent) ;
		if (concode == null || concode.length() == 0)
		{
			return ;
		}
		skey.setConsignorCode(concode) ;

		// 仕入先コード取得
		String abcode = getSupplierCode(ent) ;
		if (abcode == null || abcode.length() == 0)
		{
			return ;
		}
		skey.setSupplierCode(abcode) ;
		AbstractEntity[] entArr = (AbstractEntity[])handler.find(skey) ;

		if (entArr == null || entArr.length == 0)
		{
			return ;
		}
		getEntityMerger().merge(ent, entArr[0]) ;
	}

	/**
	 * 出荷先マスタからの補完を行います。
	 * 
	 * @param ent 補完先の予定データ
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected void completeCustomer(AbstractEntity ent)
			throws ReadWriteException
	{
		// 出荷先マスタ 取得
		DatabaseHandler handler = getCustomerOperator().getDatabaseHandler() ;
		CustomerSearchKey skey = new CustomerSearchKey() ;

		// 荷主コード取得
		String concode = getConsignorCode(ent) ;
		if (concode == null || concode.length() == 0)
		{
			return ;
		}
		skey.setConsignorCode(concode) ;

		// 出荷先コード取得
		String abcode = getCustomerCode(ent) ;
		if (abcode == null || abcode.length() == 0)
		{
			return ;
		}
		skey.setCustomerCode(abcode) ;
		AbstractEntity[] entArr = (AbstractEntity[])handler.find(skey) ;

		if (entArr == null || entArr.length == 0)
		{
			return ;
		}
		getEntityMerger().merge(ent, entArr[0]) ;
	}

	/**
	 * 荷主マスタからの補完を行います。
	 * 
	 * @param ent 補完先の予定データ
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	protected void completeConsignor(AbstractEntity ent)
			throws ReadWriteException
	{
		// 荷主マスタ 取得
		DatabaseHandler handler = getConsignorOperator().getDatabaseHandler() ;
		ConsignorSearchKey skey = new ConsignorSearchKey() ;

		String abcode = getConsignorCode(ent) ;
		if (abcode == null || abcode.length() == 0)
		{
			return ;
		}
		skey.setConsignorCode(abcode) ;
		AbstractEntity[] entArr = (AbstractEntity[])handler.find(skey) ;

		if (entArr == null || entArr.length == 0)
		{
			return ;
		}
		getEntityMerger().merge(ent, entArr[0]) ;
	}

	/**
	 * 荷主コードをエンティティから取得して返します。
	 * 
	 * @param ent 予定データ等のエンティティ
	 * @return 荷主コード,取得できないときはnull.
	 */
	protected abstract String getConsignorCode(AbstractEntity ent) ;

	/**
	 * 出荷先コードをエンティティから取得して返します。
	 * 
	 * @param ent 予定データ等のエンティティ
	 * @return 出荷先コード,取得できないときはnull.
	 */
	protected abstract String getCustomerCode(AbstractEntity ent) ;

	/**
	 * 仕入先コードをエンティティから取得して返します。
	 * 
	 * @param ent 予定データ等のエンティティ
	 * @return 仕入先コード,取得できないときはnull.
	 */
	protected abstract String getSupplierCode(AbstractEntity ent) ;

	/**
	 * 商品コードをエンティティから取得して返します。
	 * 
	 * @param ent 予定データ等のエンティティ
	 * @return 商品コード,取得できないときはnull.
	 */
	protected abstract String getItemCode(AbstractEntity ent) ;


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
		return "$Id: MergerWrapper.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $" ;
	}
}
