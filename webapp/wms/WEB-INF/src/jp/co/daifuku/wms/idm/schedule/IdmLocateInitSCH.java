package jp.co.daifuku.wms.idm.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AreaAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Locate;

/**
 * <FONT COLOR="BLUE">
 * Designer : Y.Kawai <BR>
 * Maker : Y.Kawai <BR>
 * <BR>
 * 移動ラック棚状態初期設定を行うクラスです <BR>
 * 画面から入力された内容をパラメータとして受け取り、移動ラック棚状態表示処理を行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.登録選択時設定ボタン押下処理（<CODE>startSCH()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、エリア情報の更新登録と棚情報の登録を行います。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   ＜パラメータ＞ *必須入力  +どちらか必須入力 <BR><DIR>
 *   <BR>
 *   開始棚No* <BR>
 *   終了棚№*<BR>
 *   空棚検索*<BR></DIR>
 *   <BR>
 *   ＜返却データ＞ <BR><DIR>
 *   <BR>
 *   なし <BR></DIR>
 *   <BR>
 *   ＜登録設定処理＞ <BR>
 * <DIR>
 *   ＜処理条件チェック＞ <BR>
 *     1.登録する棚情報と同一の棚№を持つ在庫情報が存在しないこと<BR>
 *     2.登録する棚情報と同一の棚№を持つ棚情報が存在しないこと <BR>
 * <BR>
 *   ＜更新登録処理＞ <BR>
 *     -エリア情報テーブル(DMAREA)の更新登録 <BR>
 *       パラメータの内容をもとに作業者実績情報を更新登録する。 <BR>
 * <BR>
 *     -ロケーション管理情報(DMLOCATE)の登録 <BR>
 *       パラメータの内容をもとに作業者実績情報を更新登録する。 <BR>
 * <BR>
 * </DIR>
 *  * <BR>
 * 2.削除選択時設定ボタン押下処理（<CODE>startSCH()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、棚情報の削除を行います。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   ＜パラメータ＞ *必須入力  +どちらか必須入力 <BR><DIR>
 *   <BR>
 *   削除棚No* <BR></DIR>
 *   <BR>
 *   ＜返却データ＞ <BR><DIR>
 *   <BR>
 *   なし <BR></DIR>
 *   <BR>
 *   ＜削除設定処理＞ <BR>
 * <DIR>
 *   ＜処理条件チェック＞ <BR>
 *     1.棚情報に削除棚№と同一の棚№を持つデータが存在すること<BR>
 *     2.棚情報の同一棚№のデータが実棚でないこと<BR>
 * <BR>
 *   ＜削除処理＞ <BR>
 *     -棚情報テーブル(DMLOCATE)の削除 <BR>
 *       パラメータの内容をもとに棚情報を削除する。 <BR>
 * <BR>
 * </DIR>
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
public class IdmLocateInitSCH extends AbstractIdmControlSCH
{
	private int registFlag = 0;

	// Class variables -----------------------------------------------	
	/**
	 * 処理名
	 */
	public static String wProcessName = "IdmLocateInitSCH";
	
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
	public IdmLocateInitSCH()
	{
		wMessage = null;
	}

	/**
	 * 棚情報の削除を行います。削除チェック時の設定ボタン押下の操作に対応するメソッドです。<BR>
	 * 検索条件は<CODE>Parameter</CODE>クラスを継承したクラスを渡します。<BR>
	 * 削除結果はbooleanで返します。<BR>
	 * 該当データが見つからなかった場合、falseを返します。<BR>
	 * 入力条件エラーなどで検索処理が失敗した場合、falseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean deleteLocate(Connection conn, Parameter searchParam)
		throws ScheduleException, ReadWriteException
	{
		// 削除処理
		ToolParamater param = (ToolParamater) searchParam;

		LocateHandler locateHandler = new LocateHandler(conn);
		LocateSearchKey locateSearchKey = new LocateSearchKey();

		// データの検索
		IdmOperate iOpe = new IdmOperate();
		// 開始棚
		String fromlocation =
			iOpe.importFormatIdmLocation(
				param.getStartBankNo(), param.getStartBayNo(), param.getStartLevelNo());

		if (!StringUtil.isBlank(param.getStartBankNo()))
		{
			locateSearchKey.setLocationNo(fromlocation, "=");
		}

		// 棚情報のLOCK
		locateHandler.lock(locateSearchKey);

		// 削除対象の棚№を持つ棚情報があるか？
		if(locateHandler.count(locateSearchKey) != 0)
		{ 
			// あれば検索条件に棚状態フラグ(空棚)を加えて検索する。
			locateSearchKey.setStatusFlag(Locate.Locate_StatusFlag_EMPTY);

			try
			{
				locateHandler.drop(locateSearchKey);
			}
			catch (NotFoundException e)
			{
				// 6023408=実棚の棚情報があるため削除できません。
				wMessage = "6023408";
				return false;
			}
			// 6001005=削除しました。
			wMessage = "6001005";
			return true; 	
		}
		else
		{
			// 6003014=削除対象データがありませんでした。
			wMessage = "6003014";
			return false;
		}
	}
	
	/**
	 * DBへ棚の新規登録を行います。登録チェック時の設定ボタン押下の操作に対応するメソッドです。<BR>
	 * 検索条件は<CODE>Parameter</CODE>クラスを継承したクラスを渡します。<BR>
	 * 登録結果はbooleanで返します。<BR>
	 * 該当データが見つからなかった場合、falseを返します。<BR>
	 * 入力条件エラーなどで検索処理が失敗した場合、falseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean startSCH(Connection conn, Parameter searchParam)
		throws ScheduleException, ReadWriteException
	{
		// 日次更新処理中のチェック
		if (isDailyUpdate(conn))
		{
			return false;
		}	
		
		ToolParamater param = (ToolParamater) searchParam;
		IdmOperate idmOperate = new IdmOperate();
		try
		{
			LocateHandler locateHandler = new LocateHandler(conn);
			LocateSearchKey locateSearchKey = new LocateSearchKey();
			Locate locate = new Locate();
			StockSearchKey stockSearchKey = new StockSearchKey();
			StockHandler stockHandler = new StockHandler(conn);
			
			// 開始棚
			String startLocationNo = 
				idmOperate.importFormatIdmLocation(param.getStartBankNo(), param.getStartBayNo(), param.getStartLevelNo());
			// 終了棚
			String endLocationNo =
				idmOperate.importFormatIdmLocation(param.getEndBankNo(), param.getEndBayNo(), param.getEndLevelNo());

			// 在庫情報の検索条件のセット
			stockSearchKey.setLocationNo(startLocationNo, ">=");
			stockSearchKey.setLocationNo(endLocationNo, "<=");
			stockSearchKey.setStockQty(0, ">");
			// 棚情報の検索条件のセット
			locateSearchKey.setLocationNo(startLocationNo, ">=");
			locateSearchKey.setLocationNo(endLocationNo, "<=");	
				
			// 入力された範囲内に在庫情報がないかのチェック
			if(stockHandler.count(stockSearchKey) > 0)
			{
				// 6023405=同一棚№を持つ在庫情報が存在するため登録できません。
				wMessage = "6023405";	
				return false; 
			}
			
			// 入力された範囲内にすでに棚情報が存在しないか？
			if(locateHandler.count(locateSearchKey) > 0)
			{
				// 6023406=既に棚情報が存在するため登録できません。
				wMessage = "6023406";
				return false;
			}
			
			// エリア情報のロックと登録または更新処理
			processAreaData(conn, param);
			
			// DMLOCATEのLOCK
			locateHandler.lock(locateSearchKey);

			// DMLOCATEを登録
			int strtBankNo = Integer.parseInt(param.getStartBankNo());
			int strtBayNo = Integer.parseInt(param.getStartBayNo());
			int strtLevelNo = Integer.parseInt(param.getStartLevelNo());			
			// 終了ロケーション№のバンク№、ベイ№、レベル№を取得
			int endBankNo = Integer.parseInt(param.getEndBankNo());
			int endBayNo = Integer.parseInt(param.getEndBayNo());
			int endLevelNo = Integer.parseInt(param.getEndLevelNo());
			
			
			// 開始ロケーション№から終了ロケーション№までまわす。
			for (int i = strtBankNo; i <= endBankNo; i++)
			{
				for (int j = strtBayNo; j <= endBayNo; j++)
				{
					for (int k = strtLevelNo; k <= endLevelNo; k++)
					{
						String bankNo = Integer.toString(i);
						String bayNo = Integer.toString(j);
						String levelNo = Integer.toString(k);
						
						// 1～9までの場合はロケーション№のフォーマットに合わせるために0を付ける。
						if (i < 10)
						{
							bankNo = "0" + bankNo;
						}
						if (j < 10)
						{
							bayNo = "0" + bayNo;
						}
						if (k < 10)
						{
							levelNo = "0" + levelNo;
						}

						// 新規登録棚№
						String locationNo =
							idmOperate.importFormatIdmLocation(bankNo, bayNo, levelNo);

						// AREA_NO
						locate.setAreaNo(WmsParam.IDM_AREA_NO);
						// ロケーション№
						locate.setLocationNo(locationNo);
						// AISLE_NO
						// BANK_NO
						locate.setBankNo(bankNo);
						// BAY_NO
						locate.setBayNo(bayNo);
						// LEVEL_NO
						locate.setLevelNo(levelNo);
						// 空棚実棚区分
						// 空棚の場合
						locate.setStatusFlag(Locate.Locate_StatusFlag_EMPTY);
						// PROHIBITION_FLAG(使用可能)
						locate.setProhibitionFlag(Locate.Locate_ProhibitionFlag_NOPROHIBITED);
						// MIXED_LOAD_CNT
						// ZONE
						// REGIST_FLAG
						// エリア情報がない場合(システム登録)
						if (registFlag == 0)
						{
							locate.setRegistFlag(Locate.Locate_RegistFlag_SYSTEM);							
						}
						// エリア情報が存在する場合(メンテナンス登録)
						else
						{
							locate.setRegistFlag(Locate.Locate_RegistFlag_MAINTE);
						}
						// 登録処理名
						locate.setRegistPname(wProcessName);
						// 最終更新処理名
						locate.setLastUpdatePname(wProcessName);

						//ロケーション情報の登録
						locateHandler.create(locate);
					}
				}
			}
			
			// 6001003 = 登録しました。
			wMessage = "6001003";
			return true;

		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}



	/**
	 * エリア情報の更新または登録を行います。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param param 画面から入力された内容を持つtoolParamaterクラスのインスタンス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void processAreaData(Connection conn,ToolParamater param)
		throws ReadWriteException
	{
		// DMAREAの更新処理
		AreaHandler areaHandler = new AreaHandler(conn);
		AreaSearchKey areaSearchKey = new AreaSearchKey();
		
		// DMAERAのロック
		areaHandler.lock(areaSearchKey);
		
		// 検索条件のセット
		// エリア№
		areaSearchKey.setAreaNo(WmsParam.IDM_AREA_NO);
		
		// エリア情報がない場合
		if (areaHandler.count(areaSearchKey) == 0)
		{
			registFlag = 0;
			createArea(conn, param);
			
		}
		else
		{
			registFlag = 1;
			updateArea(conn, param);
		}

	}


	/**
	 * エリア情報テーブルを更新します。
	 * @param  conn        データベースとのコネクションを保持するインスタンス。
	 * @param  param       画面から入力された内容を持つtoolParamaterクラスのインスタンス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void updateArea(Connection conn, ToolParamater param)
		throws ReadWriteException
	{
		AreaAlterKey wAreaKey = new AreaAlterKey();
		AreaHandler areaHandler = new AreaHandler(conn);
		
		// 更新条件のセット
		// エリア№
		wAreaKey.setAreaNo(WmsParam.IDM_AREA_NO);
		
		// 更新内容セット
		// 空棚検索方法
		wAreaKey.updateVacantSearchType(param.getBankAisleFlag());
		// 最終更新処理名
		wAreaKey.updateLastUpdatePname(wProcessName);
		
		try
		{
			// 更新する。
			areaHandler.modify(wAreaKey);

		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());		
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());		
		}
	}


	/**
	 * エリア情報テーブルを登録します。
	 * @param  conn        データベースとのコネクションを保持するインスタンス。
	 * @param  param       画面から入力された内容を持つtoolParamaterクラスのインスタンス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createArea(Connection conn, ToolParamater param) throws ReadWriteException
	{
		AreaHandler areaHandler = new AreaHandler(conn);
		Area area = new Area();
		try
		{
			// エリア№
			area.setAreaNo(WmsParam.IDM_AREA_NO);
			// エリア種別
			area.setAreaType(Integer.toString(Area.SYSTEM_DISC_KEY_IDM));
			// 空棚検索方法
			area.setVacantSearchType(param.getBankAisleFlag());
			// 登録処理名
			area.setRegistPname(wProcessName);
			// 最終更新処理名
			area.setLastUpdatePname(wProcessName);
			
			// 新規エリア情報の登録
			areaHandler.create(area);
			
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());		
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());		
		}
	}

}