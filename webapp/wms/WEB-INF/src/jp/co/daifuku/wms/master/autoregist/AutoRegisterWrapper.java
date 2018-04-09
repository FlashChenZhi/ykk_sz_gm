// $Id: AutoRegisterWrapper.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $
package jp.co.daifuku.wms.master.autoregist ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection ;
import java.util.Date;

import jp.co.daifuku.common.InvalidStatusException ;
import jp.co.daifuku.common.NoPrimaryException ;
import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.wms.base.entity.AbstractEntity ;
import jp.co.daifuku.wms.base.entity.Consignor ;
import jp.co.daifuku.wms.base.entity.Customer ;
import jp.co.daifuku.wms.base.entity.Item ;
import jp.co.daifuku.wms.base.entity.Supplier ;
import jp.co.daifuku.wms.master.MasterPrefs ;
import jp.co.daifuku.wms.master.merger.EntityMerger ;


/**
 * マスタ自動登録のラッパ・スーパークラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/11/09</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  ss
 * @author  Last commit: $Author: mori $
 */


public class AutoRegisterWrapper
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

	private ConsignorAutoRegister p_consignorAutoRegister = null ;

	private CustomerAutoRegister p_customerAutoRegister = null ;

	private SupplierAutoRegister p_supplierAutoRegister = null ;

	private ItemAutoRegister p_itemAutoRegister = null ;

	private EntityMerger p_merger = null ;

	private final String pName = "AutoRegisterWrapper";
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
	 */
	public AutoRegisterWrapper(Connection conn)
	{
		setConnection(conn) ;
	}

	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * 予定データから各マスタへデータを登録します。
	 * @param ent 予定データのエンティティ
	 * @throws ReadWriteException  
	 * @throws NoPrimaryException 
	 * @throws InvalidStatusException 
	 */
	public void registMaster(AbstractEntity ent)
			throws ReadWriteException,
				NoPrimaryException,
				InvalidStatusException
	{
		// 荷主マスタ登録
		Consignor rent = createConsignor(ent) ;
		if(rent.getConsignorCode().length() != 0){ 
		    getConsignorAutoRegister().autoRegist(rent) ;
		}
		// 出荷先マスタ登録
		Customer cuent = createCustomer(ent) ;
		if(cuent.getCustomerCode().length() != 0){ 
		    getCustomerAutoRegister().autoRegist(cuent) ;
		}
		
		// 入荷先マスタ登録
		Supplier suent = createSupplier(ent) ;
		if(suent.getSupplierCode().length() != 0){ 
		    getSupplierAutoRegister().autoRegist(suent) ;
		}
		
		// 商品マスタ登録
		Item itent = createItem(ent) ;
		if(itent.getItemCode().length() != 0){ 
		    getItemAutoRegister().autoRegist(itent) ;
		}
	}

	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------

	/**
	 * @return consignorAutoRegisterを返します。
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	protected ConsignorAutoRegister getConsignorAutoRegister()
			throws ReadWriteException
	{
		if (p_consignorAutoRegister == null)
		{
			p_consignorAutoRegister = new ConsignorAutoRegister(getConnection()) ;
		}
		return p_consignorAutoRegister ;
	}


	/**
	 * @return CustomerAutoRegisterを返します。
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	protected CustomerAutoRegister getCustomerAutoRegister()
			throws ReadWriteException
	{
		if (p_customerAutoRegister == null)
		{
			p_customerAutoRegister = new CustomerAutoRegister(getConnection()) ;
		}
		return p_customerAutoRegister ;
	}

	/**
	 * @return SupplierAutoRegisterを返します。
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	protected SupplierAutoRegister getSupplierAutoRegister()
			throws ReadWriteException
	{
		if (p_supplierAutoRegister == null)
		{
			p_supplierAutoRegister = new SupplierAutoRegister(getConnection()) ;
		}
		return p_supplierAutoRegister ;
	}

	/**
	 * @return ItemAutoRegisterを返します。
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	protected ItemAutoRegister getItemAutoRegister()
			throws ReadWriteException
	{
		if (p_itemAutoRegister == null)
		{
			p_itemAutoRegister = new ItemAutoRegister(getConnection()) ;
		}
		return p_itemAutoRegister ;
	}

	/**
	 * @return mergerを返します。
	 */
	protected EntityMerger getMerger()
	{
		if (p_merger == null)
		{
			// 予定データからマスタエンティティにセットするためのマージャ
			// すべて上書き固定
			p_merger = new EntityMerger(MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE) ;
		}
		return p_merger ;
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


	protected Consignor createConsignor(AbstractEntity ent)
	{
		Consignor rent = new Consignor() ;
		getMerger().merge(rent, ent) ;
		rent.setLastUpdatePname(pName) ;
		rent.setLastUsedDate(new Date());
		return rent ;
	}

	protected Customer createCustomer(AbstractEntity ent)
	{
		Customer rent = new Customer() ;
		getMerger().merge(rent, ent) ;
		rent.setLastUpdatePname(pName) ;
		rent.setLastUsedDate(new Date());
		return rent ;
	}

	protected Item createItem(AbstractEntity ent)
	{
		Item rent = new Item() ;
		getMerger().merge(rent, ent) ;
		rent.setLastUpdatePname(pName) ;
		rent.setLastUsedDate(new Date());
		return rent ;
	}

	protected Supplier createSupplier(AbstractEntity ent)
	{
		Supplier rent = new Supplier() ;
		getMerger().merge(rent, ent) ;
		rent.setLastUpdatePname(pName) ;
		rent.setLastUsedDate(new Date());
		return rent ;
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
		return "$Id: AutoRegisterWrapper.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $" ;
	}
}
