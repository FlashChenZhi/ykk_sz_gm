// $Id: SortingPlanMGWrapper.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $
package jp.co.daifuku.wms.master.merger ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection ;

import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.wms.base.entity.AbstractEntity ;
import jp.co.daifuku.wms.base.entity.SortingPlan ;


/**
 * 仕分予定データを補完するためのラッパクラスです。
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  ss
 * @author  Last commit: $Author: mori $
 */


public class SortingPlanMGWrapper
		extends MergerWrapper
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
	//	private String	_instanceVar ;

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------

	/**
	 * データベースコネクションを指定してインスタンスを生成します。
	 * @param conn
	 * @throws ReadWriteException マスタの定義が読み込めなかったときスローされます。
	 */
	public SortingPlanMGWrapper(Connection conn)
			throws ReadWriteException
	{
		super(conn) ;
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
	 * @see jp.co.daifuku.wms.master.merger.MergerWrapper#getConsignorCode(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	protected String getConsignorCode(AbstractEntity ent)
	{
		if (ent instanceof SortingPlan)
		{
			SortingPlan aent = (SortingPlan)ent ;
			return aent.getConsignorCode() ;
		}
		return null ;
	}

	/**
	 * @see jp.co.daifuku.wms.master.merger.MergerWrapper#getCustomerCode(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	protected String getCustomerCode(AbstractEntity ent)
	{
		if (ent instanceof SortingPlan)
		{
			SortingPlan aent = (SortingPlan)ent ;
			return aent.getCustomerCode() ;
		}
		return null ;
	}

	/**
	 * @see jp.co.daifuku.wms.master.merger.MergerWrapper#getSupplierCode(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	protected String getSupplierCode(AbstractEntity ent)
	{
		if (ent instanceof SortingPlan)
		{
			SortingPlan aent = (SortingPlan)ent ;
			return aent.getSupplierCode() ;
		}
		return null ;
	}

	/**
	 * @see jp.co.daifuku.wms.master.merger.MergerWrapper#getItemCode(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	protected String getItemCode(AbstractEntity ent)
	{
		if (ent instanceof SortingPlan)
		{
			SortingPlan aent = (SortingPlan)ent ;
			return aent.getItemCode() ;
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
		return "$Id: SortingPlanMGWrapper.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $" ;
	}
}
