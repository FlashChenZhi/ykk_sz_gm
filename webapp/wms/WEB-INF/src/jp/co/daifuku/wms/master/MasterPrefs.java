// $Id: MasterPrefs.java,v 1.2 2006/11/21 04:22:47 suresh Exp $
package jp.co.daifuku.wms.master ;

import java.util.Calendar ;
import java.util.Date ;

import jp.co.daifuku.common.ReadWriteException ;
import jp.co.daifuku.common.text.IniFileOperation ;
import jp.co.daifuku.wms.base.common.WmsParam ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * マスタパッケージ用の定義について、読み書きを行うためのクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/10/26</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:22:47 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class MasterPrefs
{
	//------------------------------------------------------------
	// class variables (prefix '$')
	//------------------------------------------------------------
	private static String $semaphore = "" ;

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	/** 環境定義ファイルのパス */
	private static final String ENVFILE_PATH = WmsParam.ENVIRONMENT ;

	/** 定義ファイル内のセクション名 */
	public static final String MASTER_SECTION_NAME = "MASTER_PACKAGE" ;

	//------- KEYS
	/** 自動削除用の保持期間 (int) */
	public static final String DEFKEY_HOLD_DAYS = "HOLD_DAYS" ;

	/** 自動削除設定用の最低保持期間 (int) */
	public static final String DEFKEY_MIN_HOLD_DAYS = "MIN_HOLD_DAYS" ;

	/** 名称の一意性タイプ (int) */
	public static final String DEFKEY_UNIQ_NAME_TYPE = "UNIQ_NAME_TYPE" ;

	/** 自動登録タイプ (int) */
	public static final String DEFKEY_AUTO_REGIST_TYPE = "AUTO_REGIST_TYPE" ;

	/** 予定データ受信時のマージタイプ (int) */
	public static final String DEFKEY_MERGE_TYPE = "MERGE_TYPE" ;

	//------- VALUES
	/** 各名称のユニークチェックタイプ (チェックなし) */
	public static final int UNIQ_NAME_TYPE_NONE = 0 ;

	/** 各名称のユニークチェックタイプ (マスタ内でユニーク:デフォルト) */
	public static final int UNIQ_NAME_TYPE_LOCAL = 1 ;

	/** 各名称のユニークチェックタイプ (マスタ間でユニーク) */
	public static final int UNIQ_NAME_TYPE_WIDE = 2 ;


	/** マージ・自動登録タイプ (マージ・自動登録無し) */
	public static final int MERGE_REGIST_TYPE_NONE = 0 ;

	/** マージ・自動登録タイプ (すべて上書き) */
	public static final int MERGE_REGIST_TYPE_OVERWRITE = 1 ;

	/** マージ・自動登録タイプ (未設定項目に書き込み:デフォルト) */
	public static final int MERGE_REGIST_TYPE_FILL_EMPTY = 2 ;

	/** マージ・自動登録タイプ (新規レコードのみ) */
	public static final int MERGE_REGIST_TYPE_NEW_RECORD = 3 ;

	/** 保持期間 (無制限) */
	public static final int HOLD_DAYS_UNLIMITED = 0 ;

	//------------------------------------------------------------
	// properties (prefix 'p_')
	//------------------------------------------------------------
	private int p_autoRegistType = MERGE_REGIST_TYPE_FILL_EMPTY ;

	private int p_mergeType = MERGE_REGIST_TYPE_FILL_EMPTY ;

	private int p_holdDays = HOLD_DAYS_UNLIMITED ;

	private int p_minHoldDays = HOLD_DAYS_UNLIMITED ;

	private int p_uniqNameType = UNIQ_NAME_TYPE_LOCAL ;

	//------------------------------------------------------------
	// instance variables (prefix '_')
	//------------------------------------------------------------
	private boolean _needFileUpdate = false ;

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * 定義内容を読み込んで、インスタンスを初期化します。
	 * @throws ReadWriteException IniFileOperationでの読み込み失敗時にスローされます。
	 */
	public MasterPrefs()
			throws ReadWriteException
	{
		load() ;
	}


	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * 定義内容を読み込みます。<br>
	 * このインスタンス内にセットされていた内容は破棄されます。
	 * @throws ReadWriteException IniFileOperationでの読み込み失敗時にスローされます。
	 */
	public void load()
			throws ReadWriteException
	{
		synchronized ($semaphore)
		{
			IniFileOperation iniOp = new IniFileOperation(ENVFILE_PATH) ;

			// auto regist type
			int intValue = loadIntValue(DEFKEY_AUTO_REGIST_TYPE, MERGE_REGIST_TYPE_FILL_EMPTY,
					iniOp) ;
			setAutoRegistType(intValue) ;

			// mearge type
			intValue = loadIntValue(DEFKEY_MERGE_TYPE, MERGE_REGIST_TYPE_FILL_EMPTY, iniOp) ;
			setMergeType(intValue) ;

			// hold days
			intValue = loadIntValue(DEFKEY_HOLD_DAYS, HOLD_DAYS_UNLIMITED, iniOp) ;
			setHoldDays(intValue) ;

			// minimum hold days
			intValue = loadIntValue(DEFKEY_MIN_HOLD_DAYS, HOLD_DAYS_UNLIMITED, iniOp) ;
			setMinHoldDays(intValue) ;

			// unique name type
			intValue = loadIntValue(DEFKEY_UNIQ_NAME_TYPE, UNIQ_NAME_TYPE_LOCAL, iniOp) ;
			setUniqNameType(intValue) ;


			// update ini file, if value not defined.
			if (_needFileUpdate)
			{
				iniOp.flush() ;
				_needFileUpdate = false ;
			}
		}
	}

	/**
	 * このインスタンスに設定されている内容で、定義ファイルに書き込みを行います。
	 * 
	 * @throws ReadWriteException IniFileOperationでの読み込み失敗時にスローされます。
	 */
	public void save()
			throws ReadWriteException
	{
		synchronized ($semaphore)
		{
			IniFileOperation iniOp = new IniFileOperation(ENVFILE_PATH) ;

			saveIntValue(DEFKEY_AUTO_REGIST_TYPE, getAutoRegistType(), iniOp) ;
			saveIntValue(DEFKEY_HOLD_DAYS, getHoldDays(), iniOp) ;
			saveIntValue(DEFKEY_MERGE_TYPE, getMergeType(), iniOp) ;
			saveIntValue(DEFKEY_MIN_HOLD_DAYS, getMinHoldDays(), iniOp) ;
			saveIntValue(DEFKEY_UNIQ_NAME_TYPE, getUniqNameType(), iniOp) ;

			iniOp.flush() ;
		}
	}


	/**
	 * 現在の日から、保持期間を減算した日を返します。
	 * @param holddays 保持期間
	 * @return 保持期限を減算した日<br>
	 * 保持期間が0の時は、nullを返します。
	 */
	public static Date getTermDate(int holddays)
	{
		int hold = holddays ;
		if (hold == 0)
		{
			return null ;
		}
		Calendar cal = Calendar.getInstance() ;
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY)) ;
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE)) ;
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND)) ;
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND)) ;
		cal.add(Calendar.DATE, hold * -1) ;

		return cal.getTime() ;
	}

	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * @return autoRegistTypeを返します。
	 */
	public int getAutoRegistType()
	{
		return p_autoRegistType ;
	}


	/**
	 * @param autoRegistType autoRegistTypeを設定します。<br>
	 * MERGE_REGIST_TYPE_XXX のいずれかを指定します。
	 */
	public void setAutoRegistType(int autoRegistType)
	{
		switch (autoRegistType)
		{
			case MasterPrefs.MERGE_REGIST_TYPE_NONE:
			case MasterPrefs.MERGE_REGIST_TYPE_FILL_EMPTY:
			case MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE:
			case MasterPrefs.MERGE_REGIST_TYPE_NEW_RECORD:
				break ;
			default:
				throw new InvalidPropertyException(
						"Invalid auto regist type property. Use 'MasterPrefs.MERGE_REGIST_TYPE_XXXX'.") ;
		}
		p_autoRegistType = autoRegistType ;
	}


	/**
	 * @return holdDaysを返します。
	 */
	public int getHoldDays()
	{
		return p_holdDays ;
	}


	/**
	 * @param holdDays holdDaysを設定します。
	 */
	public void setHoldDays(int holdDays)
	{
		p_holdDays = holdDays ;
	}


	/**
	 * @return mergeTypeを返します。
	 */
	public int getMergeType()
	{
		return p_mergeType ;
	}


	/**
	 * @param mergeType mergeTypeを設定します。<br>
	 * MERGE_REGIST_TYPE_NEW_RECORD 以外の、
	 * MERGE_REGIST_TYPE_XXXX のいずれかを指定します。
	 */
	public void setMergeType(int mergeType)
	{
		switch (mergeType)
		{
			case MasterPrefs.MERGE_REGIST_TYPE_NONE:
			case MasterPrefs.MERGE_REGIST_TYPE_FILL_EMPTY:
			case MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE:
				break ;
			case MasterPrefs.MERGE_REGIST_TYPE_NEW_RECORD:
				throw new InvalidPropertyException(
						"Invalid merge type property. Merge does not support MERGE_REGIST_TYPE_NEW_RECORD.") ;
			default:
				throw new InvalidPropertyException(
						"Invalid merge type property. Use 'MasterPrefs.MERGE_REGIST_TYPE_XXXX'.") ;
		}
		p_mergeType = mergeType ;
	}


	/**
	 * @return minHoldDaysを返します。
	 */
	public int getMinHoldDays()
	{
		return p_minHoldDays ;
	}


	/**
	 * @param minHoldDays minHoldDaysを設定します。
	 */
	public void setMinHoldDays(int minHoldDays)
	{
		p_minHoldDays = minHoldDays ;
	}


	/**
	 * @return uniqNameTypeを返します。
	 */
	public int getUniqNameType()
	{
		return p_uniqNameType ;
	}


	/**
	 * @param uniqNameType uniqNameTypeを設定します。
	 */
	public void setUniqNameType(int uniqNameType)
	{
		p_uniqNameType = uniqNameType ;
	}


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
	/**
	 * 整数値を定義ファイルから読み込みます。<br>
	 * 読み込めなかったときは、指定されたデフォルト値が返され、定義ファイルへ
	 * デフォルト値をセットします。
	 * @param key 定義のキー
	 * @param defaultValue デフォルト値
	 * @param iniOp 定義ファイルのハンドラ
	 * @return 読み込んだ整数値
	 */
	private int loadIntValue(String key, int defaultValue, IniFileOperation iniOp)
	{
		String value = iniOp.get(MASTER_SECTION_NAME, key) ;
		if (value == null)
		{
			value = String.valueOf(defaultValue) ;
			iniOp.set(MASTER_SECTION_NAME, key, value) ;

			_needFileUpdate = true ;
		}
		return Integer.parseInt(value) ;
	}

	private void saveIntValue(String key, int value, IniFileOperation iniOp)
	{
		iniOp.set(MASTER_SECTION_NAME, key, String.valueOf(value)) ;
	}

	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
	/**
	 * このクラスのリビジョンを返します。
	 * @return リビジョン文字列。
	 */
	public static String getVersion()
	{
		return "$Id: MasterPrefs.java,v 1.2 2006/11/21 04:22:47 suresh Exp $" ;
	}
}
