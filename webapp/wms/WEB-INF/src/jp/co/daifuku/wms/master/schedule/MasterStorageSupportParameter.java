package jp.co.daifuku.wms.master.schedule;

import java.util.Date;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : S.Yoshdia <BR>
 * Maker : S.Yoshida <BR>
 * <BR>
 * <CODE>MasterStorageSupportParameter</CODE>クラスは、マスタパッケージ導入時の
 * 入庫パッケージ内の画面～スケジュール間のパラメータの受渡しに使用します。<BR>
 * <BR>
 * <DIR>
 * <CODE>MasterStorageSupportParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     補完タイプ<BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/05/23</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterStorageSupportParameter extends jp.co.daifuku.wms.storage.schedule.StorageSupportParameter
{
	// Class feilds ------------------------------------------------
	/**
	 * 補完タイプ (すべて上書き)
	 */
	public static final int MERGE_TYPE_OVERWRITE = 1 ;

	/**
	 * 補完タイプ (未設定項目に書き込み:デフォルト)
	 */
	public static final int MERGE_TYPE_FILL_EMPTY = 2 ;

	/**
	 * 補完タイプ (なし)
	 */
	public static final int MERGE_TYPE_NONE = 3 ;
	
	/**
	 * 検索モード（補完データ検索）
	 */
	public static final int SEARCH_MODE_MERGE = 0;
	
	/**
	 * 検索モード（ためうちデータ検索）
	 */
	public static final int SEARCH_MODE_LIST = 1;

	// Class variables -----------------------------------------------
	/**
	 * 補完タイプ
	 */
	private int wMergeType;
	
	/**
	 * 荷主マスタ最終更新日時
	 */
	private Date wConsignorLastUpdateDate; 

	/**
	 * 商品マスタ最終更新日時
	 */
	private Date wItemLastUpdateDate; 
	
	/**
	 * 検索モード
	 */
	private int wSearchMode;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public MasterStorageSupportParameter()
	{
	}

	// Public method -------------------------------------------------
	/**
	 * 補完タイプをセットします。
	 * @param arg セットする補完タイプ
	 */
	public void setMergeType(int arg)
	{
		wMergeType = arg;
	}
	/**
	 * 補完タイプを取得します。
	 * @return 補完タイプ
	 */
	public int getMergeType()
	{
		return wMergeType;
	}

	/**
	 * 荷主マスタ最終更新日時をセットします。
	 * @param arg セットする荷主マスタ最終更新日時
	 */
	public void setConsignorLastUpdateDate(Date arg)
	{
		wConsignorLastUpdateDate = arg;
	}
	/**
	 * 荷主マスタ最終更新日時を取得します。
	 * @return 荷主マスタ最終更新日時
	 */
	public Date getConsignorLastUpdateDate()
	{
		return wConsignorLastUpdateDate;
	}

	/**
	 * 商品マスタ最終更新日時をセットします。
	 * @param arg セットする商品マスタ最終更新日時
	 */
	public void setItemLastUpdateDate(Date arg)
	{
		wItemLastUpdateDate = arg;
	}
	/**
	 * 商品マスタ最終更新日時を取得します。
	 * @return 商品マスタ最終更新日時
	 */
	public Date getItemLastUpdateDate()
	{
		return wItemLastUpdateDate;
	}

	/**
	 * 検索モードをセットします。
	 * @param arg セットする検索モード
	 */
	public void setSearchMode(int arg)
	{
		wSearchMode = arg;
	}
	
	/**
	 * 検索モードを取得します。
	 * @return 検索モード
	 */
	public int getSearchMode()
	{
		return wSearchMode;
	}
}
