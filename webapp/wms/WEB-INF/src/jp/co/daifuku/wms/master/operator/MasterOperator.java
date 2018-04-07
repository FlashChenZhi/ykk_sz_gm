// $Id: MasterOperator.java,v 1.2 2006/11/10 00:35:34 suresh Exp $
package jp.co.daifuku.wms.master.operator ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.wms.base.dbhandler.FieldName ;
import jp.co.daifuku.wms.base.entity.AbstractEntity ;

/**
 * マスタの登録・修正・削除のためのインターフェースです。
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
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:35:34 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */
public interface MasterOperator
{
	//------------------------------------------------------------
	// class variables (prefix '$')
	//------------------------------------------------------------
	//	private String	$classVar ;

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	/** 処理結果 (OK) */
	public static final int RET_OK = 0 ;

	/** 処理結果 (NG) */
	public static final int RET_NG = -1 ;
	
	/** 処理結果 (すでに登録済み) */
	public static final int RET_EXIST = 1 ;

	/** 処理結果 (該当のデータなし) */
	public static final int RET_NOT_EXIST = 2 ;

	/** 処理結果 (整合性エラー: 名称の重複) */
	public static final int RET_CONSIST_NAME_EXIST = 3 ;
	
	/** 処理結果（引数不足：検索に必要なパラメータが空白またはnull） */
	public static final int RET_PARAM_IS_BLANK = 4;

	/** 処理結果 (想定外のエラー) */
	public static final int RET_FATAL_ERROR = 99 ;


	/** 削除フラグ値 (使用中) */
	public static final String DELETE_FLAG_LIVE = "0" ;

	/** 削除フラグ値 (削除済み) */
	public static final String DELETE_FLAG_DELETED = "9" ;


	/** カラム定義 (DELETEFLAG) */
	public static final FieldName DELETEFLAG = new FieldName("DELETE_FLAG") ;

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
			throws ReadWriteException ;

	/**
	 * マスタの更新を行います。
	 * @param ent 更新するマスタのエンティティ
	 * @return 更新の結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public int modify(AbstractEntity ent)
			throws ReadWriteException ;

	/**
	 * マスタの削除を行います。<br>
	 * 実際にテーブルから削除されます。削除フラグのみを更新する場合は
	 * drop()を使用してください。
	 * @param ent 削除するマスタのエンティティ
	 * @return 削除の結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public int actualDrop(AbstractEntity ent)
			throws ReadWriteException ;

	/**
	 * マスタの削除を行います。<br>
	 * 削除フラグを削除済みに更新します。実際にテーブルから削除する場合は
	 * actualDrop()を使用してください。
	 * @param ent 削除するマスタのエンティティ
	 * @return 削除の結果
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public int drop(AbstractEntity ent)
			throws ReadWriteException ;

	/**
	 * 該当データがすでに存在するかどうかチェックします。
	 * @param ent チェックするマスタのエンティティ
	 * @return 登録済みの時 true.
	 * @throws ReadWriteException データベースへのアクセスに失敗したときスローされます。
	 */
	public boolean exist(AbstractEntity ent)
			throws ReadWriteException ;

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


	//------------------------------------------------------------
	// private methods
	//------------------------------------------------------------


	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
}
