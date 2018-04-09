// $Id: EntityMerger.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $
package jp.co.daifuku.wms.master.merger ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.dbhandler.FieldName ;
import jp.co.daifuku.wms.base.entity.AbstractEntity ;
import jp.co.daifuku.wms.master.MasterPrefs ;


/**
 * 二つのEntityオブジェクトの内容をマージします。<br>
 * マージ方法は、コンストラクタおよびsetMergeType()にて
 * セットしてください。
 * 
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/10/28</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  ss
 * @author  Last commit: $Author: mori $
 */


public class EntityMerger
{
	//------------------------------------------------------------
	// class variables (prefix '$')
	//------------------------------------------------------------

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	//	public static final int FIELD_VALUE = 1 ;

	//------------------------------------------------------------
	// properties (prefix 'p_')
	//------------------------------------------------------------
	private int p_mergeType ;

	//------------------------------------------------------------
	// instance variables (prefix '_')
	//------------------------------------------------------------

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * パラメータに応じたマージタイプのインスタンスを生成します。
	 * @param mergeType マージタイプ
	 * @see MasterPrefs
	 */
	public EntityMerger(int mergeType)
	{
		setMergeType(mergeType) ;
	}

	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * 二つのエンティティをマージします。
	 * @param orgEntity マージ(データ追加)されるエンティティ
	 * @param appendEntity 追加もとデータを持つエンティティ
	 */
	public void merge(AbstractEntity orgEntity, AbstractEntity appendEntity)
	{
		// ハンドラのバージョンをv2からv3へ更新する場合は、パラメタをAbstractEntityからEntityへ変更してください

		int mgType = getMergeType() ;
		if (mgType == MasterPrefs.MERGE_REGIST_TYPE_NONE)
		{
			return ;
		}

		String[] aColumns = appendEntity.getColumnArray() ;
		for (int i = 0; i < aColumns.length; i++)
		{
			if (hasColumn(orgEntity, aColumns[i]))
			{
				FieldName column = new FieldName(aColumns[i]) ;
				switch (mgType)
				{
					case MasterPrefs.MERGE_REGIST_TYPE_FILL_EMPTY:
						// 空の列を埋めます。
						fillEmpty(orgEntity, appendEntity, column) ;
						break ;

					case MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE:
						// 列をオーバーライドします。
						overWrite(orgEntity, appendEntity, column) ;
						break ;
				}
			}
		}

		return ;
	}

	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------

	/**
	 * @return mergeTypeを返します。
	 * @see MasterPrefs
	 */
	public int getMergeType()
	{
		return p_mergeType ;
	}

	/**
	 * @param mergeType mergeTypeを設定します。<br>
	 * MasterPrefs.MEAGE_REGIST_TYPE_XXXX のうち、MERGE_REGIST_TYPE_NEW_RECORD
	 * 以外のいずれかを使用してください。
	 * @see MasterPrefs
	 */
	public void setMergeType(int mergeType)
	{
		p_mergeType = mergeType ;
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
	 * appendEntityの値をorgEntityに上書きします。<br>
	 * v3ハンドラへアップデートしたときには、パラメータに使用されている
	 * AbstractEntityをEntityに変更すること。
	 * 
	 * @param orgEntity マージ(データ追加)されるエンティティ
	 * @param appendEntity 追加もとデータを持つエンティティ
	 * @param column 更新対象のカラム
	 * @return マージ結果(データ追加されたorgEntity)
	 */
	protected static AbstractEntity overWrite(AbstractEntity orgEntity, AbstractEntity appendEntity,
			FieldName column)
	{
		// ハンドラのバージョンをv2からv3へ更新する場合は、パラメタをAbstractEntityからEntityへ変更してください

		Object value = appendEntity.getValue(column) ;
		orgEntity.setValue(column, value) ;
		return orgEntity ;
	}

	/**
	 * appendEntityの値をorgEntityに追加します。<br>
	 * orgEntityのカラム値がnullの時にだけ追加されます。<br>
	 * v3ハンドラへアップデートしたときには、パラメータに使用されている
	 * AbstractEntityをEntityに変更すること。
	 * 
	 * @param orgEntity マージ(データ追加)されるエンティティ
	 * @param appendEntity 追加もとデータを持つエンティティ
	 * @param column 更新対象のカラム
	 * @return マージ結果(データ追加されたorgEntity)
	 */
	protected static AbstractEntity fillEmpty(AbstractEntity orgEntity, AbstractEntity appendEntity,
			FieldName column)
	{
		// ハンドラのバージョンをv2からv3へ更新する場合は、パラメタをAbstractEntityからEntityへ変更してください
		Object oValue = orgEntity.getValue(column, null) ;
		if (oValue instanceof String)
		{
			if (oValue.toString().length() == 0)
			{
				oValue = null ;
			}
		}
		if (oValue == null)
		{
			Object value = appendEntity.getValue(column, null) ;
			orgEntity.setValue(column, value) ;
		}
		return orgEntity ;
	}

	/**
	 * エンティティにカラムが存在するかどうかチェックします。
	 * @param ent 調査対象エンティティ
	 * @param columnName 調査対象カラム名
	 * @return 存在するときtrue.
	 */
	protected static boolean hasColumn(AbstractEntity ent, String columnName)
	{
		// ハンドラのバージョンをv2からv3へ更新する場合は、パラメタをAbstractEntityからEntityへ変更してください
		String[] columns = ent.getColumnArray() ;
		String fn = columnName.toUpperCase() ;
		for (int i = 0; i < columns.length; i++)
		{
			String tn = columns[i].toUpperCase() ;
			if (fn.equals(tn))
			{
				return true ;
			}
		}
		return false ;
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
		return "$Id: EntityMerger.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $" ;
	}
}
