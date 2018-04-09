// $Id: SupplierAutoRegister.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $
package jp.co.daifuku.wms.master.autoregist ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection ;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.wms.base.common.AlterKey ;
import jp.co.daifuku.wms.base.common.SearchKey ;
import jp.co.daifuku.wms.base.dbhandler.DatabaseHandler ;
import jp.co.daifuku.wms.base.dbhandler.SupplierAlterKey ;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler ;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey ;
import jp.co.daifuku.wms.base.entity.AbstractEntity ;
import jp.co.daifuku.wms.base.entity.Supplier ;
import jp.co.daifuku.wms.master.operator.AbstractMasterOperator ;
import jp.co.daifuku.wms.master.operator.MasterOperator ;
import jp.co.daifuku.wms.master.operator.SupplierOperator ;


/**
 * 仕入先マスタ用自動登録クラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/11/15</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  ss
 * @author  Last commit: $Author: mori $
 */


public class SupplierAutoRegister
		extends AutoRegister
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
	//	private String	p_Name ;

	//------------------------------------------------------------
	// instance variables (prefix '_')
	//------------------------------------------------------------
	private SupplierOperator _operator = null ;

	private SupplierHandler _handler = null ;
	
	private final String pName = "AutoRegisterWrapper";

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------

	/**
	 * データベースコネクションを指定してインスタンスを生成します。
	 * @param conn
	 * @throws ReadWriteException
	 */
	public SupplierAutoRegister(Connection conn)
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


	//------------------------------------------------------------
	// package methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// protected methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	/* (非 Javadoc)
	 * @see jp.co.daifuku.wms.master.autoregist.AutoRegister#getMasterOperator(java.sql.Connection)
	 */
	protected MasterOperator getMasterOperator(Connection conn)
			throws ReadWriteException
	{
		if (_operator == null)
		{
			_operator = new SupplierOperator(conn) ;
		}
		return _operator ;
	}


	/* (非 Javadoc)
	 * @see jp.co.daifuku.wms.master.autoregist.AutoRegister#getDBHandler(java.sql.Connection)
	 */
	protected DatabaseHandler getDBHandler(Connection conn)
	{
		if (_handler == null)
		{
			_handler = new SupplierHandler(conn) ;
		}
		return _handler ;
	}


	/* (非 Javadoc)
	 * @see jp.co.daifuku.wms.master.autoregist.AutoRegister#createSearchKey(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	protected SearchKey createSearchKey(AbstractEntity ent)
	{
		if (ent instanceof Supplier)
		{
			try
			{
				Supplier actEnt = (Supplier)ent ;
				SupplierSearchKey skey = new SupplierSearchKey() ;

				// 荷主コード
				String code = actEnt.getConsignorCode() ;
				skey.setConsignorCode(code) ;

				// 仕入先コード
				String ccode = actEnt.getSupplierCode() ;
				skey.setSupplierCode(ccode) ;

				return skey ;
			}
			catch (ReadWriteException e)
			{
				// this will not occur.
			}
		}
		return null ;
	}


	/* (非 Javadoc)
	 * @see jp.co.daifuku.wms.master.autoregist.AutoRegister#createAlterKey(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	protected AlterKey createAlterKey(AbstractEntity dbinent)
	{
		if (dbinent instanceof Supplier)
		{
			try
			{
				Supplier actEnt = (Supplier)dbinent ;
				SupplierAlterKey akey = new SupplierAlterKey() ;

				// 荷主コード
				String code = actEnt.getConsignorCode() ;
				akey.setConsignorCode(code) ;

				// 仕入先コード
				String ccode = actEnt.getSupplierCode() ;
				akey.setSupplierCode(ccode) ;

				// 更新値セット
				// 仕入先名称
				String name = actEnt.getSupplierName1() ;
				String colName = AbstractMasterOperator.createColumnName(akey,
						Supplier.SUPPLIERNAME1) ;
				akey.setUpdValue(colName, name) ;

				// 更新クラス
				colName = AbstractMasterOperator.createColumnName(akey, Supplier.LASTUPDATEPNAME) ;
				akey.setUpdValue(colName, pName) ;
				
				// 更新日			
				colName = AbstractMasterOperator.createColumnName(akey, Supplier.LASTUPDATEDATE) ;
				akey.setUpdValue(colName, new Date()) ;

				// 最終使用日時
				colName = AbstractMasterOperator.createColumnName(akey, Supplier.LASTUSEDDATE) ;
				akey.setUpdValue(colName, new Date()) ;
				
				return akey ;
			}
			catch (ReadWriteException e)
			{
				// this will not occur.
			}
		}
		return null ;
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
		return "$Id: SupplierAutoRegister.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $" ;
	}
}
