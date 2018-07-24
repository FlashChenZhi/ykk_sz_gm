// $Id: SupplierOperator.java,v 1.2 2006/11/10 00:35:33 suresh Exp $
package jp.co.daifuku.wms.master.operator ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Date;
import java.util.Map;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AlterKey;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.DatabaseHandler;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.dbhandler.SupplierAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

/**
 * 仕入先マスタ用オペレータクラスです。
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
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:35:33 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class SupplierOperator
		extends AbstractMasterOperator
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
	//	private String	p_Name ;

	//------------------------------------------------------------
	// instance variables (prefix '_')
	//------------------------------------------------------------
	//	private String	_instanceVar ;

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
	public SupplierOperator(Connection conn)
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
	/**
	 * @see jp.co.daifuku.wms.master.operator.AbstractMasterOperator#getDBHandler(Connection)
	 */
	protected DatabaseHandler getDBHandler(Connection conn)
	{
		return new SupplierHandler(conn) ;
	}


	/**
	 * @see jp.co.daifuku.wms.master.operator.AbstractMasterOperator#checkCreateConsistent(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	protected int checkCreateConsistent(AbstractEntity ent)
			throws ReadWriteException
	{
		// 仕入先名称重複チェック		
		String name = ent.getValue(Supplier.SUPPLIERNAME1).toString() ;

		// 空白への変更は重複チェックなし
		if (name == null || name.length() == 0)
		{
			return RET_OK ;
		}

		// システム定義チェック
		int uniqType = getMasterPrefs().getUniqNameType() ;

		// ユニークチェックなし
		if (uniqType == MasterPrefs.UNIQ_NAME_TYPE_NONE)
		{
			return RET_OK ;
		}

		String consignor = ent.getValue(Supplier.CONSIGNORCODE).toString() ;
		// マスタ内ユニークチェック
		if (uniqType == MasterPrefs.UNIQ_NAME_TYPE_LOCAL)
		{
			DatabaseHandler handler = getDatabaseHandler() ;
			SupplierSearchKey skey = new SupplierSearchKey() ;

			skey.setSupplierName1(name) ;
			skey.setConsignorCode(consignor) ;

			int cnt = handler.count(skey) ;
			if (cnt > 0)
			{
				return RET_CONSIST_NAME_EXIST ;
			}
		}
		return RET_OK ;
	}


	/**
	 * @see jp.co.daifuku.wms.master.operator.AbstractMasterOperator#checkModifyConsistent(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	public int checkModifyConsistent(AbstractEntity ent)
			throws ReadWriteException
	{
		// 仕入先名称重複チェック
		String code = ent.getValue(Supplier.SUPPLIERCODE).toString() ;
		String name = ent.getValue(Supplier.SUPPLIERNAME1).toString() ;

		// 空白への変更は重複チェックなし
		if (name == null || name.length() == 0)
		{
			return RET_OK ;
		}

		// システム定義チェック
		int uniqType = getMasterPrefs().getUniqNameType() ;

		// ユニークチェックなし
		if (uniqType == MasterPrefs.UNIQ_NAME_TYPE_NONE)
		{
			return RET_OK ;
		}

		String consignor = ent.getValue(Supplier.CONSIGNORCODE).toString() ;
		// マスタ内ユニークチェック
		if (uniqType == MasterPrefs.UNIQ_NAME_TYPE_LOCAL)
		{
			DatabaseHandler handler = getDatabaseHandler() ;
			SupplierSearchKey skey = new SupplierSearchKey() ;

			// 該当データ以外に同じ名称がある場合はNG.
			skey.setSupplierName1(name) ;
			skey.setSupplierCode(code, "!=") ;
			skey.setConsignorCode(consignor) ;
			skey.setDeleteFlag(Supplier.DELETE_FLAG_LIVE);
			
			int cnt = handler.count(skey) ;
			if (cnt > 0)
			{
				return RET_CONSIST_NAME_EXIST ;
			}
		}
		return RET_OK ;
	}


	/**
	 * @see jp.co.daifuku.wms.master.operator.AbstractMasterOperator#checkDropConsistent(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	protected int checkDropConsistent(AbstractEntity key)
			throws ReadWriteException
	{
		// 特別なチェックなし
		return RET_OK ;
	}


	/**
	 * @see jp.co.daifuku.wms.master.operator.AbstractMasterOperator#createAlterKey(jp.co.daifuku.wms.base.entity.AbstractEntity)
	 */
	protected AlterKey createAlterKey(AbstractEntity key)
	{
		SupplierAlterKey akey = new SupplierAlterKey() ;
		try
		{
			Map valueMap = key.getValueMap() ;

			// 更新検索キー:荷主コード,仕入先コード
			FieldName consignor = Supplier.CONSIGNORCODE ;
			FieldName updateKey = Supplier.SUPPLIERCODE ;

			// 検索キーのセット
			String keyValue = key.getValue(updateKey).toString() ;
			akey.setSupplierCode(keyValue) ;

			// 荷主コード
			String cValue = key.getValue(consignor).toString() ;
			akey.setConsignorCode(cValue) ;

			akey.setDeleteFlag(Supplier.DELETE_FLAG_LIVE) ;

			FieldName[] fields = (FieldName[])valueMap.keySet().toArray(new FieldName[0]) ;

			// 更新値のセット
			for (int i = 0; i < fields.length; i++)
			{
				FieldName fld = fields[i] ;
				if (!updateKey.equals(fld))
				{
					Object newValue = key.getValue(fld) ;
					if (newValue != null)
					{
						setUpdateValueToAlterKey(akey, fld, newValue) ;
					}
				}
			}
			// 固定更新値
			String colName = createColumnName(akey, Supplier.LASTUPDATEPNAME) ;
			akey.setUpdValue(colName,key.getValue(Supplier.LASTUPDATEPNAME).toString() );
			colName = createColumnName(akey, Supplier.LASTUPDATEDATE) ;
			akey.setUpdValue(colName, new Date()) ;
			colName = createColumnName(akey, Supplier.LASTUSEDDATE) ;
			akey.setUpdValue(colName, new Date()) ;
		}
		catch (ReadWriteException e)
		{
			// 発生しません。
			e.printStackTrace() ;
		}

		return akey ;
	}

	/**
	 * @see jp.co.daifuku.wms.master.operator.AbstractMasterOperator#createSearchKey(jp.co.daifuku.wms.base.entity.AbstractEntity, boolean)
	 */
	protected SearchKey createSearchKey(AbstractEntity key, boolean liveOnly)
	{
		SupplierSearchKey skey = new SupplierSearchKey() ;
		try
		{
			// 更新検索キー:荷主コード,仕入先コード
			FieldName consignor = Supplier.CONSIGNORCODE ;
			FieldName updateKey = Supplier.SUPPLIERCODE ;

			// 検索キーのセット
			String keyValue = key.getValue(updateKey).toString() ;
			if (!StringUtil.isBlank(keyValue))
				skey.setSupplierCode(keyValue) ;

			// 荷主コード
			String cValue = key.getValue(consignor).toString() ;
			skey.setConsignorCode(cValue) ;
			if (liveOnly)
			{
				skey.setDeleteFlag(Supplier.DELETE_FLAG_LIVE) ;
			}
		}
		catch (ReadWriteException e)
		{
			// 発生しません。
			e.printStackTrace() ;
		}

		return skey ;
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
		return "$Id: SupplierOperator.java,v 1.2 2006/11/10 00:35:33 suresh Exp $" ;
	}
	
	/**
	 * 入力情報より仕入先マスタに該当する商品があるかチェックします
	 * 
	 * @param  param 入力情報
	 * @return Boolean
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */	

	public int checkSupplierMaster(MasterParameter param) throws ReadWriteException
	{
		if (StringUtil.isBlank(param.getConsignorCode())
		 || StringUtil.isBlank(param.getSupplierCode()))
		{
			return RET_PARAM_IS_BLANK;
		}
		
	    // 仕入先マスタ存在チェック
		Supplier ent = new Supplier();
		
		ent.setConsignorCode(param.getConsignorCode());
		ent.setSupplierCode(param.getSupplierCode());
		ent.setSupplierName1(param.getSupplierName());
		
		if (getMasterPrefs().getAutoRegistType() == MasterPrefs.MERGE_REGIST_TYPE_NONE)
		{

			if (!exist(ent))
			{
				return RET_NG;
			}
		}
		
		if (getMasterPrefs().getAutoRegistType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE 
			&& getMasterPrefs().getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_NONE){
			if(checkModifyConsistent(ent) != 0){
				return RET_CONSIST_NAME_EXIST;	    
			}
		}
		return RET_OK;
	}
	
	/**
	 * 仕入先名称の重複チェック
	 * @return 処理結果 OK/NG
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public int checkSupplierName(AbstractEntity ent)
			throws ReadWriteException
	{
		// 仕入先名称重複チェック
		String name = ent.getValue(Supplier.SUPPLIERNAME1).toString() ;

		// 空白への変更は重複チェックなし
		if (name == null || name.length() == 0)
		{
			return RET_OK ;
		}

		// システム定義チェック
		int uniqType = getMasterPrefs().getUniqNameType() ;

		// ユニークチェックなし
		if (uniqType == MasterPrefs.UNIQ_NAME_TYPE_NONE)
		{
			return RET_OK ;
		}

		// マスタ内ユニークチェック
		if (uniqType == MasterPrefs.UNIQ_NAME_TYPE_LOCAL)
		{
			DatabaseHandler handler = getDatabaseHandler() ;
			SupplierSearchKey skey = new SupplierSearchKey() ;

			// 該当データ以外に同じ名称がある場合はNG.
			skey.setSupplierName1(name) ;
			skey.setDeleteFlag(Supplier.DELETE_FLAG_LIVE);
			
			int cnt = handler.count(skey) ;
			if (cnt > 0)
			{
				return RET_CONSIST_NAME_EXIST ;
			}
		}
		return RET_OK ;
	}
	
	/**
	 * 入力情報の仕入先コードが更新されていないかチェックします
	 * 
	 * @param  param 入力情報
	 * @return Boolean
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public boolean isModify(MasterParameter param) throws ReadWriteException
	{
		DatabaseHandler handler = getDatabaseHandler() ;
		SupplierSearchKey suplKey = new SupplierSearchKey();
		suplKey.setConsignorCode(param.getConsignorCode());
		suplKey.setSupplierCode(param.getSupplierCode());
		suplKey.setLastUpdateDate(param.getLastUpdateDate());
		if (handler.count(suplKey) == 1)
		{
			return false;
		}
		else
		{
			return true;
		}

	}

	/**
	 * 入力情報に合致する仕入先情報の最終更新日時を取得します。
	 * 荷主または仕入先が入力されていない場合は、nullを返します。
	 * 
	 * @param  param 入力情報
	 * @return Date 最終更新日時
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public Date getLastUpdateDate(MasterParameter param) throws ReadWriteException
	{
		// 対象データなし
		if (StringUtil.isBlank(param.getConsignorCode())
		 || StringUtil.isBlank(param.getSupplierCode()))
		{
			return null;
		}
		
		DatabaseHandler handler = getDatabaseHandler() ;
		SupplierSearchKey suplKey = new SupplierSearchKey();
		suplKey.setConsignorCode(param.getConsignorCode());
		suplKey.setSupplierCode(param.getSupplierCode());
		suplKey.setLastUpdateDateCollect();
		try
		{
			Supplier supl = (Supplier) handler.findPrimary(suplKey);
			if (supl == null)
			{
				return null;
			}
			return supl.getLastUpdateDate();
			
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

}
