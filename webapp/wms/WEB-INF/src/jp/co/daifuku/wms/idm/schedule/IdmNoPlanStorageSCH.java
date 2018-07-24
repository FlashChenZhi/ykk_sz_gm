package jp.co.daifuku.wms.idm.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.idm.dbhandler.IdmStockReportFinder;
import jp.co.daifuku.wms.idm.report.IdmNoPlanStorageWriter;

/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 移動ラック入庫設定処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、移動ラック入庫設定処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理を行いますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   -初期表示処理-(<CODE>AbstructIdmControlSCH()</CODE>クラス)<BR>  
 * </DIR>
 * <BR>
 * 2.入力ボタン押下処理（<CODE>check()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
 *   在庫情報に同一行No.の該当データが存在しなかった場合はtrueを、条件エラーが発生した場合や該当データが存在した場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] *必須入力  +どちらか必須入力 <BR>
 *   <BR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード* <BR>
 *   荷主名称 <BR>
 *   商品コード* <BR>
 *   商品名称 <BR>
 *   ケース/ピース区分* <BR>
 *   入庫棚* <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   入庫ケース数+ <BR>
 *   入庫ピース数+ <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   賞味期限 <BR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructIdmControlSCH()</CODE>クラス) <BR>
 *   <BR>
 *   -入力値チェック処理-(<CODE>AbstructIdmControlSCH()</CODE>クラス) <BR>
 *   <BR>
 *   -オーバーフローチェック- <BR>
 *   <BR>
 *   -表示件数チェック- <BR>
 *   <BR>
 *   -重複チェック- <BR>
 *   <BR>
 *     <DIR>
 *     荷主コード、ロケーションNo.、商品コード、ケース/ピース区分、賞味期限をキーにして重複チェックを行います。<BR>
 *     賞味期限は、在庫を一意にする項目としてWmsParamに定義されている場合、重複チェックのキーに含みます。 <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * 3.入庫開始ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、移動ラック入庫設定処理を行います。 <BR>
 *   処理完了時にパラメータの移動ラック入庫作業リスト発行区分がtrueの場合、移動ラック入庫作業リスト発行クラスを起動します。<BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [パラメータ] <BR>
 *   <BR>
 *   作業者コード <BR>
 *   パスワード <BR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   商品コード <BR>
 *   商品名称 <BR>
 *   ケース/ピース区分 <BR>
 *   入庫棚 <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   入庫ケース数 <BR>
 *   入庫ピース数 <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   賞味期限 <BR>
 *   移動ラック入庫作業リスト発行区分 <BR>
 *   端末No. <BR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   -作業者コードチェック処理-(<CODE>AbstructIdmControlSCH()</CODE>クラス)<BR>
 *   <BR>
 *   -日次更新処理中チェック処理-(<CODE>AbstructStorageSCH()</CODE>クラス) <BR>
 *   <BR>
 *   [更新登録処理] <BR>
 *   <BR>
 *   -在庫情報テーブル(DMSTOCK)の登録または更新-<BR>
 *   <BR>
 *   <DIR>
 *     荷主コード、ロケーションNo.、商品コード、在庫ステータス(センター在庫)、ケース/ピース区分、賞味期限にてデータを検索し、在庫情報が存在する場合は対象レコードをロックする。<BR>
 *     賞味期限は、在庫を一意にする項目としてWmsParamに定義されている場合、検索条件に含みます。 <BR>
 *     <BR>
 *     データが存在する場合、<BR>
 *     商品名称、在庫数、入庫日時、荷主名称、ケース入数、ボール入数、ケースITF、ボールITF、最終更新処理名を更新します。<BR>
 *	   引当中の在庫の更新が可能である為、引当数、入庫予定数の更新は行いません。<BR>
 *     <BR>
 *     [処理条件チェック] <BR>
 *     <BR>
 *     -オーバーフローチェック-<BR>  
 *     <BR>
 *     データが存在しない場合、<BR>
 *     受け取ったパラメータの内容をもとに在庫情報を登録します。<BR>
 *     <BR>
 *   </DIR>
 *   -ロケーション管理情報テーブル(DMLOCATE)の更新- <BR>
 *   <BR>
 *   <DIR>
 *     1.状態フラグを実棚に更新します。 <BR>
 *     2.最終更新処理名を更新する。 <BR>
 *     <BR>
 *   </DIR>
 *   -実績送信情報テーブル(DNHOSTSEND)の登録-(<CODE>AbstructIdmControlSCH()</CODE>クラス)<BR>  
 *   <BR>
 *   <DIR>
 *     受け取ったパラメータの内容をもとに実績送信情報を登録します。<BR>
 *   </DIR>  
 *   <BR>
 *   -作業者実績情報テーブル(DNWORKERRESULT)の登録または更新-(<CODE>AbstructIdmControlSCH()</CODE>クラス)<BR>  
 *   <BR>
 *   <DIR>
 *     受け取ったパラメータの内容をもとに作業者実績情報を登録または更新します。<BR>
 *   </DIR>
 *   <BR>
 *   [印刷処理] <BR>
 *   <BR>
 *   移動ラック入庫リスト発行処理クラスにバッチNo.を渡します。<BR>
 *	 印刷データがない場合は印刷処理を行いません。<BR>
 *</FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/07</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class IdmNoPlanStorageSCH extends AbstractIdmControlSCH
{
	//	Class variables -----------------------------------------------

	/**
	 * クラス名(予定外入庫)
	 */
	public static String PROCESSNAME = "IdmNoPlanStorageSCH";

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
	public IdmNoPlanStorageSCH()
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
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		IdmStockReportFinder stockFinder = new IdmStockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		// データの検索
		// 在庫ステータス(センター在庫)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 在庫数が1以上であること
		searchKey.setStockQty(1, ">=");

		searchKey.setConsignorCodeCollect();
		searchKey.setConsignorCodeGroup(1);

		IdmControlParameter dispData = new IdmControlParameter();

		if (stockFinder.search(searchKey) == 1)
		{
			// データの検索			
			searchKey.KeyClear();
			// 在庫ステータス(センター在庫)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			// 在庫数が1以上であること
			searchKey.setStockQty(1, ">=");
			// 最新更新日時の新しい荷主名称を取得します。
			searchKey.setLastUpdateDateOrder(1, false);

			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (stockFinder.search(searchKey) > 0)
			{				
				Stock[] consignorname = (Stock[]) stockFinder.getEntities(1);

				dispData.setConsignorCode(consignorname[0].getConsignorCode());
				dispData.setConsignorName(consignorname[0].getConsignorName());
			}
		}
		stockFinder.close();

		return dispData;

	}

	/** 
	 * 画面から入力された溜めうちエリアの内容をパラメータとして受け取り、 <BR>
	 * 必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
	 * 在庫情報に同一行No.の該当データが存在しなかった場合はtrueを、 <BR>
	 * 条件エラーが発生した場合や該当データが存在した場合は排他エラーとし、falseを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンス。 <BR>
	 *        IdmControlParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        IdmControlParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @return true：入力内容が正常な場合  false：そうでない場合
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams)
		throws ScheduleException, ReadWriteException
	{
		try
		{
			// 入力エリアの内容
			IdmControlParameter param = (IdmControlParameter) checkParam;
			// ためうちエリアの内容
			IdmControlParameter[] paramlist = (IdmControlParameter[]) inputParams;

			// 作業者コード、パスワードのチェック
			if (!checkWorker(conn, param))
			{
				return false;
			}

			String casepieceflag = param.getCasePieceFlag();
			int enteringqty = param.getEnteringQty();
			int caseqty = param.getStorageCaseQty();
			int pieceqty = param.getStoragePieceQty();

			// 入力値チェック
			if (!storageInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
			{
				return false;
			}

			IdmOperate iOpe = new IdmOperate();
			String pLocation =
				iOpe.importFormatIdmLocation(
					param.getBankNo(),
					param.getBayNo(),
					param.getLevelNo());
			if (pLocation == null)
			{
				// 6023389 = 入庫棚には、移動ラックの棚を指定して下さい。
				wMessage = "6023389";
				return false;
			}
			LocateHandler wLocateHandler = new LocateHandler(conn);
			LocateSearchKey wLocateSearchKey = new LocateSearchKey();

			// 入力されたバンク・ベイ・レベルNoにて移動ラック棚としてロケーション情報に登録されているか判定します。
			// 検索条件を編集します
			wLocateSearchKey.KeyClear();
			// WmsParamより、移動ラック用のエリアNoを取得し、検索条件にセットします。
			wLocateSearchKey.setAreaNo(WmsParam.IDM_AREA_NO);
			// バンクNo
			wLocateSearchKey.setBankNo(param.getBankNo());
			// ベイNo
			wLocateSearchKey.setBayNo(param.getBayNo());
			// レベルNo
			wLocateSearchKey.setLevelNo(param.getLevelNo());

			// 対象件数にて、登録されているかを判定します
			if (wLocateHandler.count(wLocateSearchKey) <= 0)
			{
				// 6023390 = 移動ラックの棚に登録されていない入庫棚です。
				wMessage = "6023390";
				return false;
			}

			// オーバーフローチェック
			long inputqty =
				(long) param.getStorageCaseQty() * (long) param.getEnteringQty()
					+ param.getStoragePieceQty();
			if (inputqty > WmsParam.MAX_STOCK_QTY)
			{
				// 6023058 = {0}には{1}以下の値を入力してください。入庫数(バラ総数)でチェックする。
				wMessage =
					"6023058"
						+ wDelim
						+ DisplayText.getText("LBL-W0377")
						+ wDelim
						+ MAX_STOCK_QTY_DISP;
				return false;
			}

			// 表示件数チェック
			if (paramlist != null && paramlist.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
			{
				// 6023096 = 件数が{0}件を超えるため、入力できません。
				wMessage = "6023096" + wDelim + MAX_NUMBER_OF_DISP_DISP;
				return false;
			}

			// 重複チェック
			// 荷主コード、商品コード、ケース/ピース区分、賞味期限をキーにして重複チェックを行います。
			// 賞味期限は、在庫を一意にする項目としてWmsParamに定義されている場合、重複チェックのキーに含みます。
			if (paramlist != null)
			{
				for (int i = 0; i < paramlist.length; i++)
				{
					if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode())
							&& paramlist[i].getItemCode().equals(param.getItemCode())
							&& paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag())
							&& paramlist[i].getUseByDate().equals(param.getUseByDate()))
						{
							// 6023090 = 既に同一データが存在するため、入力できません。
							wMessage = "6023090";
							return false;
						}
					}
					else if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode())
							&& paramlist[i].getItemCode().equals(param.getItemCode())
							&& paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag()))
						{
							// 6023090 = 既に同一データが存在するため、入力できません。
							wMessage = "6023090";
							return false;
						}

					}
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		return true;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、予定外入庫設定スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        IdmControlParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			// 作業者コード、パスワードのチェック
			IdmControlParameter workparam = (IdmControlParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return false;
			}

			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return false;
			}

			IdmControlParameter[] param = (IdmControlParameter[]) startParams;

			if (param != null)
			{
				// 作業者コード
				String workercode = param[0].getWorkerCode();
				// 作業者名称
				String workerName = getWorkerName(conn, workercode);

				// 作業日(システム定義日付)
				String sysdate = getWorkDate(conn);

				// 端末№
				String terminalno = param[0].getTerminalNumber();
				// 作業区分(予定外入庫)
				String jobtype = Stock.JOB_TYPE_EX_STORAGE;
				// 登録する各一意キーを取得する。
				SequenceHandler sequence = new SequenceHandler(conn);
				// バッチNo.(1設定共通)
				String batch_seqno = sequence.nextNoPlanBatchNo();
				// 入庫日時に、現在日時をセットする。(1設定共通)
				Date instockdate = new Date();
				
				// 作業数量
				int workqty = 0;
				
				try
				{
					// ロック処理（在庫情報,ロケーション情報）
					lockStockData(conn, (IdmControlParameter[]) startParams);
				}
				catch(LockTimeOutException e)
				{
					// 6027008 = このデータは他の端末で更新中のため、処理できません。
					wMessage = "6027008";
					return false;
				}
				
				for (int i = 0; i < param.length; i++)
				{
					// エリアNoをパラメータにセットします。
					// WmsParamより、移動ラック用のエリアNoを取得します。
					param[i].setAreaNo(WmsParam.IDM_AREA_NO);

					// 入庫数
					int inputqty =
						param[i].getStorageCaseQty() * param[i].getEnteringQty()
							+ param[i].getStoragePieceQty();
					
					// 作業数量のオーバーフローチェック
					workqty = addWorkQty(workqty, inputqty);
					
					// 在庫情報テーブル(DMSTOCK)の登録または更新
					String newstockid = "";
					try
					{
						// 在庫情報を登録します。
						// 在庫データが存在する場合、在庫数を足します。(積増入庫)
						// 在庫データが存在しない場合、在庫情報を新規に作成します。(新規入庫)
						newstockid = processStockData(conn, param[i], inputqty, instockdate);
					}
					catch(OverflowException e)
					{
						// 6023348 = 在庫数が{0}をこえるため設定できません。
						wMessage = "6023348" + wDelim + MAX_STOCK_QTY_DISP;
						return false;	
					}
					// 実績送信情報テーブル(DNHOSTSEND)の登録
					if (!createHostsend(conn,
						param[i],
						newstockid,
						workercode,
						workerName,
						sysdate,
						terminalno,
						jobtype,
						PROCESSNAME,
                        batch_seqno,
						inputqty))
					{
						return false;
					}

				}
				
				// 作業者実績情報テーブル(DNWORKERRESULT)の登録
				updateWorkerResult(conn,workercode,workerName,sysdate,terminalno,jobtype,workqty);

				// 予定外入庫リストファイルの作成
				if (param[0].getListFlg() == true)
				{
					if (startPrint(conn, batch_seqno))
					{
						// 6021012 = 設定後、印刷は正常に終了しました。
						wMessage = "6021012";
					}
					else
					{
						// 6007042=設定後、印刷に失敗しました。ログを参照してください。
						wMessage = "6007042";
					}
				}
				else
				{
					// 6001006 = 設定しました。
					wMessage = "6001006";
				}

				return true;

			}
				return false;
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 予定外入庫リスト発行処理クラスにバッチNo.を渡します。<BR>
	 * 印刷データがない場合は印刷処理を行いません。<BR>
	 * 印刷に成功した場合は、予定外入庫発行処理クラスからtrueを、失敗した場合はfalseを受け取り、<BR>
	 * その結果を返します。<BR>
	 * エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
	 * @param  conn Connection データベースとのコネクションオブジェクト
	 * @param  batchno バッチNo.
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	protected boolean startPrint(Connection conn, String batchno)
	{
		IdmNoPlanStorageWriter writer = new IdmNoPlanStorageWriter(conn);
		writer.setBatchNumber(batchno);

		// 印刷処理を開始する。
		if (writer.startPrint())
		{
			return true;
		}
		
		return false;
	}

	/**
	 * 在庫情報テーブルを登録または更新します。
	 * @param  conn        データベースとのコネクションを保持するインスタンス。
	 * @param  param       画面から入力された内容を持つIdmControlParameterクラスのインスタンス。
	 * @param  inputqty    入庫数
	 * @param  instockdate 入庫日時
	 * @throws NoPrimaryException 定義情報が異常な場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 * @return 在庫ID
	 */
	private String processStockData(
		Connection conn,
		IdmControlParameter param,
		int inputqty,
		Date instockdate)
		throws
			NoPrimaryException,
			ReadWriteException,
			ScheduleException,
			OverflowException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();

		// データの検索
		// 荷主コード
		searchKey.setConsignorCode(param.getConsignorCode());
		// エリアNo.
		searchKey.setAreaNo(param.getAreaNo());
		// ロケーションNo.
		searchKey.setLocationNo(param.getLocationNo());
		// 商品コード
		searchKey.setItemCode(param.getItemCode());
		// 在庫ステータス(センター在庫)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// ケース/ピース区分(荷姿)
		// 指定なし
		if (param.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		}
		// ケース
		else if (param.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (param.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
		}
		// 在庫情報の検索条件に、賞味期限を含む場合
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			if (!StringUtil.isBlank(param.getUseByDate()))
			{
				searchKey.setUseByDate(param.getUseByDate());
			}
			else
			{
				searchKey.setUseByDate("", "");
			}
		}

		// データのロック
		Stock stock = (Stock) stockHandler.findPrimaryForUpdate(searchKey);

		// 在庫ID
		String stockid = "";
		// 在庫データが存在しない場合(新規入庫)
		if (stock == null)
		{
			// 在庫情報テーブル(DMSTOCK)の登録
			stockid = createStock(conn, param, inputqty, instockdate);

		}
		// 在庫データが存在する場合(積増入庫)
		else
		{
			stockid = stock.getStockId();
			
			// オーバーフローチェック
			int totalstockqty = stock.getStockQty() + inputqty;
			if (totalstockqty > WmsParam.MAX_STOCK_QTY)
			{
				// 6026025 = 在庫数が{0}をこえるため設定できませんでした。
				RmiMsgLogClient.write("6026025" + wDelim + WmsParam.MAX_STOCK_QTY, "IdmNoPlanStorageSCH");
				// ここで、OverflowExceptionをthrowする。(エラーメッセージはセット不要です)
				throw (new OverflowException());
			}			
			
			//引き当て可能数
			int allocationQty = stock.getAllocationQty() + inputqty;
			
			// 在庫情報テーブル(DMSTOCK)の更新
			updateStock(conn, param, stockid, totalstockqty, allocationQty, instockdate);

		}

		// ロケーション情報の更新を行います。
		LocateOperator wLocateOperator = new LocateOperator(conn);
		wLocateOperator.modifyLocateStatus(param.getLocationNo(), PROCESSNAME);

		return stockid;
	}

	/**
	 * 在庫情報テーブルを登録します。
	 * @param  conn        データベースとのコネクションを保持するインスタンス。
	 * @param  param       画面から入力された内容を持つIdmControlParameterクラスのインスタンス。
	 * @param  inputqty    入庫数
	 * @param  instockdate 入庫日時
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @return 在庫ID
	 */
	protected String createStock(
		Connection conn,
		IdmControlParameter param,
		int inputqty,
		Date instockdate)
		throws ReadWriteException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			Stock stock = new Stock();

			// 登録する各一意キーを取得する。
			SequenceHandler sequence = new SequenceHandler(conn);
			// 在庫ID
			String stockId_seqno = sequence.nextStockId();
			stock.setStockId(stockId_seqno);
			// 予定一意キー
			stock.setPlanUkey("");
			// エリアNo.
			try
			{
				// ロケーション情報よりエリアNoの取得を行います。
				LocateOperator lOperator = new LocateOperator(conn);
				stock.setAreaNo(lOperator.getAreaNo(param.getLocationNo()));
			}
			catch (ScheduleException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
			// ロケーションNo.
			stock.setLocationNo(param.getLocationNo());
			// 商品コード
			stock.setItemCode(param.getItemCode());
			// 商品名称
			stock.setItemName1(param.getItemName());
			// 在庫ステータス(センター在庫)
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			// 在庫数(入庫数)
			stock.setStockQty(inputqty);
			// 引当可能数
			stock.setAllocationQty(inputqty);
			// 入庫予定数
			stock.setPlanQty(0);
			// ケース/ピース区分(荷姿)
			// 指定なし
			if (param.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			// ケース
			else if (param.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			// ピース
			else if (param.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}

			// 入庫日時
			stock.setInstockDate(instockdate);
			// 最終出庫日
			stock.setLastShippingDate("");
			// 賞味期限
			stock.setUseByDate(param.getUseByDate());
			// ロットNo.
			stock.setLotNo("");
			// 予定情報コメント
			stock.setPlanInformation("");
			// 荷主コード
			stock.setConsignorCode(param.getConsignorCode());
			// 荷主名称
			stock.setConsignorName(param.getConsignorName());
			// 仕入先コード
			stock.setSupplierCode("");
			// 仕入先名称
			stock.setSupplierName1("");
			// ケース入数
			stock.setEnteringQty(param.getEnteringQty());
			// ボール入数
			stock.setBundleEnteringQty(param.getBundleEnteringQty());
			// ケースITF
			stock.setItf(param.getITF());
			// ボールITF
			stock.setBundleItf(param.getBundleITF());
			// 登録処理名
			stock.setRegistPname(PROCESSNAME);
			// 最終更新処理名
			stock.setLastUpdatePname(PROCESSNAME);

			// データの登録
			stockHandler.create(stock);

			return stockId_seqno;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	/**
	 * 在庫情報テーブルを更新します。
	 * @param  conn        		データベースとのコネクションを保持するインスタンス。
	 * @param  param       		画面から入力された内容を持つIdmControlParameterクラスのインスタンス。
	 * @param  stockid     		在庫ID
	 * @param  totalstockqty   在庫数
	 * @param  allocationqty 	引当可能数
	 * @param  instockdate 		入庫日時
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	protected boolean updateStock(
		Connection conn,
		IdmControlParameter param,
		String stockid,
		int totalstockqty,
		int allocationqty, 
		Date instockdate)
		throws ReadWriteException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			StockAlterKey alterKey = new StockAlterKey();

			// 商品名称、在庫数、入庫日時、荷主名称、ケース入数、ボール入数、
			// ケースITF、ボールITF、最終更新処理名を更新します。
			// 在庫IDをセット
			alterKey.setStockId(stockid);

			// 商品名称
			alterKey.updateItemName1(param.getItemName());
			// 在庫数
			alterKey.updateStockQty(totalstockqty);
			// 引当可能数
			alterKey.updateAllocationQty(allocationqty);
			// 入庫日時
			alterKey.updateInstockDate(instockdate);
			// 在庫情報の検索条件に、賞味期限を含まない場合
			if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				alterKey.updateUseByDate(param.getUseByDate());
			}
			// 荷主名称
			alterKey.updateConsignorName(param.getConsignorName());
			// ケース入数
			alterKey.updateEnteringQty(param.getEnteringQty());
			// ボール入数
			alterKey.updateBundleEnteringQty(param.getBundleEnteringQty());
			// ケースITF
			alterKey.updateItf(param.getITF());
			// ボールITF
			alterKey.updateBundleItf(param.getBundleITF());
			// 最終更新処理名
			alterKey.updateLastUpdatePname(PROCESSNAME);

			// データの更新
			stockHandler.modify(alterKey);

			return true;

		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	/**
	 * 更新対象の在庫情報をロックする。
	 * 引数で指定されたパラメータの配列から在庫IDの配列を生成し、
	 * 該当する在庫情報をロックする。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>荷主コード</LI>
	 *     <LI>ロケーションNo.</LI>
	 *     <LI>商品コード</LI>
	 *     <LI>ケースピース区分</LI>
	 *     <LI>在庫ステータス[センター在庫]</LI>
	 *     <LI>賞味期限（賞味期限管理ありの場合）</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param  conn        	データベースとのコネクションを保持するインスタンス。
	 * @param	param		画面情報の配列
	 * @throws NoPrimaryException 定義情報が異常な場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 */
	protected void lockStockData(Connection conn, IdmControlParameter[] param)
		throws NoPrimaryException, ReadWriteException, LockTimeOutException
	{
		String[] stockIdList = null;
		Vector vec = new Vector();
		String[] LocationNoList = null;
		Vector vec2 = new Vector();
		String[] AreaNoList = null;
		Vector vec3 = new Vector();

		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();

		LocateHandler locateHandler = new LocateHandler(conn);
		LocateSearchKey locatesearchkey = new LocateSearchKey();

		// WmsParamより、移動ラック用のエリアNoを取得します。
		String w_AreaNo = WmsParam.IDM_AREA_NO;

		// 画面からの入力情報が既に在庫情報に存在する場合、該当する在庫IDを取得する。
		for (int i = 0; i < param.length; i++)
		{

			searchKey.KeyClear();

			// データの検索
			// 荷主コード
			searchKey.setConsignorCode(param[i].getConsignorCode());
			// エリアNo
			// WmsParamより、移動ラック用のエリアNoを取得します。
			searchKey.setAreaNo(WmsParam.IDM_AREA_NO);
			// ロケーションNo.
			searchKey.setLocationNo(param[i].getLocationNo());
			// 商品コード
			searchKey.setItemCode(param[i].getItemCode());
			// 在庫ステータス(センター在庫)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			// ケース/ピース区分(荷姿)
			// 指定なし
			if (param[i].getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			// ケース
			else if (param[i].getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			// ピース
			else if (param[i].getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
			// 在庫情報の検索条件に、賞味期限を含む場合
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				if (!StringUtil.isBlank(param[i].getUseByDate()))
				{
					searchKey.setUseByDate(param[i].getUseByDate());
				}
				else
				{
					searchKey.setUseByDate("", "");
				}
			}

			// 既存在庫情報の検索
			Stock stock = (Stock) stockHandler.findPrimary(searchKey);

			// Vectorに確保
			if (stock != null)
			{
				vec.addElement(stock.getStockId());
			}
			// ロケーション情報の検索
			locatesearchkey.KeyClear();
			locatesearchkey.setAreaNo(w_AreaNo);
			locatesearchkey.setLocationNo(param[i].getLocationNo());
			
			// 既存ロケーション情報の検索
			Locate locate = (Locate) locateHandler.findPrimary(locatesearchkey);

			// Vectorに確保
			if (locate != null)
			{
				vec2.addElement(locate.getLocationNo());
				vec3.addElement(locate.getAreaNo());
			}

		}

		// 既存の在庫情報が存在する場合
		if (vec.size() > 0)
		{
			stockIdList = new String[vec.size()];
			vec.copyInto(stockIdList);

			searchKey.KeyClear();
			searchKey.setStockId(stockIdList);

			// Vectorに確保した在庫情報全てをロック
			stockHandler.findForUpdateNowait(searchKey);
		}
		// 既存のロケーション情報が存在する場合
		if (vec2.size() > 0)
		{
			LocationNoList = new String[vec2.size()];
			vec2.copyInto(LocationNoList);
			AreaNoList = new String[vec3.size()];
			vec3.copyInto(AreaNoList);

			locatesearchkey.KeyClear();
			locatesearchkey.setAreaNo(AreaNoList);
			locatesearchkey.setLocationNo(LocationNoList);

			// Vectorに確保したロケーション情報全てをロック
			locateHandler.findForUpdateNowait(locatesearchkey);
		}

	}

}
//end of class
