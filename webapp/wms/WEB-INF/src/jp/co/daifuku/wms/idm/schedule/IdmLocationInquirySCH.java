package jp.co.daifuku.wms.idm.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.idm.schedule.IdmLocationLevelView.IdmLocationBayView;

/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 移動ラック棚状態表示を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、移動ラック棚状態表示処理を行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   -移動ラックバンクNo一覧の情報を編集します。 <BR>  
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理（<CODE>check()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、棚状態を返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   ＜パラメータ＞ *必須入力  +どちらか必須入力 <BR><DIR>
 *   <BR>
 *   バンクNo* <BR></DIR>
 *   <BR>
 *   ＜返却データ＞ <BR><DIR>
 *   <BR>
 *   ・ロケーションNo <BR>
 *   ・バンクNo <BR>
 *   ・ベイNo <BR>
 *   ・レベルNo <BR>
 *   ・状態フラグ <BR></DIR>
 *   <BR>
 *   [処理内容] <BR>
 *   <BR>
 *   -ロケーション管理情報(DNLOCATE)より、指定されたバンクNoの各棚状態を返却します。 <BR>
 *   <BR>
 *   [取得順序] <BR>
 *   1.ロケーションNoの昇順 <BR>
 *   <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/11</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class IdmLocationInquirySCH extends AbstractIdmControlSCH
{
	//	Class variables -----------------------------------------------

	/**
	 * クラス名(予定外入庫)
	 */
	public static String PROCESSNAME = "IdmLocationInquirySCH";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public IdmLocationInquirySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 在庫情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>IdmControlParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)throws ReadWriteException, ScheduleException
	{
		LocateHandler wLocateHandler = new LocateHandler(conn);
		LocateSearchKey wLocateSearchKey = new LocateSearchKey();
		
		wLocateSearchKey.KeyClear();
		// WmsParamより、移動ラック用のエリアNoを取得します。
		wLocateSearchKey.setAreaNo(WmsParam.IDM_AREA_NO);
		wLocateSearchKey.setBankNoGroup(1);
		wLocateSearchKey.setBankNoCollect("DISTINCT");
				
		Locate[] rLocate = (Locate[])wLocateHandler.find(wLocateSearchKey);
		
		if(rLocate.length==0)
		{
			super.doScheduleExceptionHandling(PROCESSNAME);
		}
		String[] bank = new String[rLocate.length];
		for(int i = 0; i < rLocate.length; i++)
		{
				bank[i] = rLocate[i].getBankNo();
		}
		
		IdmControlParameter parameter = new IdmControlParameter();
		parameter.setBankNoArray(bank);		
		return parameter ;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * ロケーション情報取得を取得します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>IdmControlParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public IdmLocationLevelView[] getLevelViewData(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException 
	{
		IdmControlParameter idmControlparam = (IdmControlParameter)searchParam;
		
		LocateHandler wLocateHandler = new LocateHandler(conn);
		LocateSearchKey wLocateSearchKey = new LocateSearchKey();
		LocateSearchKey wLocateCountKey = new LocateSearchKey();
		
		wLocateSearchKey.KeyClear();
		// WmsParamより、移動ラック用のエリアNoを取得します。
		wLocateSearchKey.setAreaNo(WmsParam.IDM_AREA_NO);

		// 指定バンクのみ
		wLocateSearchKey.setBankNo(idmControlparam.getBankNo());

		// レベルNoの降順
		wLocateSearchKey.setLevelNoOrder(1, false);
		// ベイNoの昇順
		wLocateSearchKey.setBayNoOrder(2, true);
		
		// ロケーション情報取得
		Locate[] rLocate = (Locate[])wLocateHandler.find(wLocateSearchKey);
		System.out.println("LOCATE DATA LENGTH*"+rLocate);
		System.out.println("LOCATE DATA LENGTH*"+rLocate.length);
		if (rLocate == null || rLocate.length <= 0)
		{
			super.doScheduleExceptionHandling(AbstractIdmControlSCH.CLASS_NAME);
		}

		// ベイ件数取得
		wLocateCountKey.KeyClear();
		// WmsParamより、移動ラック用のエリアNoを取得します。
		wLocateCountKey.setAreaNo(WmsParam.IDM_AREA_NO);
		// 指定バンクのみ
		wLocateCountKey.setBankNo(idmControlparam.getBankNo());
		// ベイNoにてグループ指定
		wLocateCountKey.setBayNoGroup(1);
		wLocateCountKey.setBayNoCollect("");
		
		int maxBay = wLocateHandler.count(wLocateCountKey);

		// レベル件数取得
		wLocateCountKey.KeyClear();
		// WmsParamより、移動ラック用のエリアNoを取得します。
		wLocateCountKey.setAreaNo(WmsParam.IDM_AREA_NO);
		// 指定バンクのみ
		wLocateCountKey.setBankNo(idmControlparam.getBankNo());
		// レベルNoにてグループ指定
		wLocateCountKey.setLevelNoGroup(1);
		wLocateCountKey.setLevelNoCollect("");
		
		int levelCountMax = wLocateHandler.count(wLocateCountKey);

		String beforeLevel = "";
		int levelCount = -1;
		int bayCount = 0;
		IdmLocationLevelView[] levelViews = new IdmLocationLevelView[levelCountMax];
		for (int lc = 0; lc < rLocate.length; lc++)
		{
			// 項目取得
			String bank = rLocate[lc].getBankNo();
			String bay = rLocate[lc].getBayNo();
			String level = rLocate[lc].getLevelNo();
			String location = rLocate[lc].getLocationNo();
			String status = rLocate[lc].getStatusFlag();
			String hardzone = rLocate[lc].getZone();
			String acsFlg = rLocate[lc].getProhibitionFlag();
			
			// コントロールブレイク判定
			if (!beforeLevel.equals(level))	
			{
				// コントロールブレイク用キーの更新
				beforeLevel = level;
				bayCount = 0;
				levelCount++;					
				// 配列の初期化

				levelViews[levelCount] = new IdmLocationLevelView();
				levelViews[levelCount].setLevel(level);
				IdmLocationBayView[] bayViews = new IdmLocationBayView[maxBay];
				for (int j = 0; j < maxBay; j++)
				{
					bayViews[j] = levelViews[levelCount].new IdmLocationBayView();
				}
				levelViews[levelCount].setBayView(bayViews);
			}
			// 検索結果をセット
			IdmLocationBayView bayView = levelViews[levelCount].getBayView()[bayCount];
			bayView.setBankNo(bank);
			bayView.setLevelNo(level);
			bayView.setBayNo(bay);
			bayView.setLocation(location);
			bayView.setStatus(status);
			bayView.setAccessFlg(acsFlg);
			bayView.setHardzone(hardzone);
			bayCount++;
		}
		
		return levelViews;
	}


}
//end of class
