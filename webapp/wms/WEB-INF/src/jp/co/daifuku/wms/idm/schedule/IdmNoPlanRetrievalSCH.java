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

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.idm.dbhandler.IdmStockHandler;
import jp.co.daifuku.wms.idm.report.IdmNoPlanRetrievalWriter;

/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono  <BR>
 * <BR>
 * WEB移動ラック出庫設定を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、移動ラック出庫設定処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR><BR><DIR>
 *   在庫情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR><DIR>
 *     在庫ステータス：センター在庫(2) <BR>
 *     在庫数：1以上 </DIR></DIR>
 * <BR>
 * 2.表示ボタン押下チェック処理（<CODE>check()</CODE>メソッド）<BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   商品コード、ケース/ピース区分、ロケーションNo.順に表示を行います。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力<BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     商品コード <BR>
 *     ケース/ピース区分* <BR>
 *     開始棚No. <BR>
 *     終了棚No. <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     出荷先コード <BR>
 *     出荷先名称 <BR></DIR>
 * <BR>
 *   ＜返却データ＞ <BR><DIR>
 *     荷主コード <BR>
 *     荷主名称 <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     ケース/ピース区分 <BR>
 *     ロケーションNo. <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     引当可能ケース数((在庫数-引当数)/ケース入数) <BR>
 *     引当可能ピース数((在庫数-引当数)%ケース入数) <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     賞味期限 <BR>
 *     在庫ID <BR>
 *     最終更新日時 <BR></DIR></DIR>
 * <BR>
 * 3.出庫開始ボタン押下処理（<CODE>startSCHgetParams()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、移動ラック出庫設定処理を行います。 <BR>
 *   また、処理完了時にパラメータの移動ラック出庫作業リスト発行区分がTrueの場合、移動ラック出庫作業リスト発行クラスを起動します。 <BR>
 *   処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   画面側よりパラメータとして受け取るデータは、更新対象のデータのみとする。 <BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力 <BR><DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主コード* <BR>
 *     荷主名称* <BR>
 *     商品コード* <BR>
 *     商品名称* <BR>
 *     ケース/ピース区分* <BR>
 *     ロケーションNo.* <BR>
 *     ケース入数* <BR>
 *     ボール入数* <BR>
 *     ケース出庫数* <BR>
 *     ピース出庫数* <BR>
 *     ケースITF* <BR>
 *     ボールITF* <BR>
 *     賞味期限 <BR>
 *     移動ラック出庫作業リスト発行区分* <BR>
 *     全数区分* <BR>
 *     在庫ID* <BR>
 *     行No <BR>
 *     最終更新日時* <BR></DIR>
 * <BR>
 *   ＜返却データ＞ <BR><DIR>
 *     荷主コード <BR>
 *     荷主名称 <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     ケース/ピース区分 <BR>
 *     ロケーションNo. <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     引当可能ケース数((在庫数-引当数)/ケース入数) <BR>
 *     引当可能ピース数((在庫数-引当数)%ケース入数) <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     賞味期限 <BR>
 *     在庫ID <BR>
 *     最終更新日時 <BR></DIR></DIR>
 * <BR>
 *   ＜出庫開始処理＞ <BR>
 * <DIR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.在庫IDの在庫情報テーブルがデータベースに存在すること。 <BR>
 *     3.パラメータの最終更新日時と在庫情報テーブルの最終更新日時の値が一致すること。（排他チェック） <BR>
 *     4.入力値チェック処理(<CODE>AbstructIdmControlSCH()</CODE>クラス) <BR>
 *     5.出庫数は引当可能数以下の値を入力すること。<BR>
 * <BR>
 *   ＜更新登録処理＞ <BR>
 *     -在庫情報テーブル(DMSTOCK)の更新 <BR>
 *       パラメータの在庫IDに紐づく在庫情報を受け取ったパラメータの内容をもとに更新する。 <BR>
 *       パラメータの全数区分がTrueの場合、引当可能数分、出庫設定処理を行う。 <BR>
 *       1.在庫数を出庫数減算した値に更新する。 <BR>
 *       2.最終更新処理名を更新する。 <BR>
 *       3.最終出庫日を更新する。（システム日付をセットします。） <BR>
 * <BR>
 *     -ロケーション管理情報<DMLOCATE)の更新 <BR>
 *      ロケーションNoにて、対象の在庫情報（センター在庫で在庫数が１以上）が存在しない場合のみ、更新処理を行います。 <BR>
 *        1.状態フラグを空棚に更新します。 <BR>
 *        2.最終更新処理名を更新する。 <BR>
 * <BR>
 *     -実績送信情報テーブル(DNHOSTSEND)の登録 <BR>
 *       今回更新した作業情報の内容をもとに実績送信情報を登録する。 <BR>
 * <BR>
 *     -作業者実績情報テーブル(DNWORKERRESULT)の更新登録 <BR>
 *       パラメータの内容をもとに作業者実績情報を更新登録する。 <BR></DIR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/08</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class IdmNoPlanRetrievalSCH extends AbstractIdmControlSCH
{

	// Class variables -----------------------------------------------
	/**
	 * クラス名(出荷検品)
	 */
	public static String CLASS_RETRIEVAL = "IdmNoPlanRetrievalSCH";

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
	public IdmNoPlanRetrievalSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 在庫情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
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
		IdmStockHandler stockHandler = new IdmStockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();

		// データの検索
		// 在庫ステータス(センター在庫)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 在庫数が1以上であること
		searchKey.setStockQty(1, ">=");

		searchKey.setConsignorCodeCollect(" ");
		searchKey.setConsignorCodeGroup(1);

		int count = stockHandler.count(searchKey);

		IdmControlParameter dispData = new IdmControlParameter();

		if (count == 1)
		{
			try
			{
				Stock[] stock = (Stock[]) stockHandler.find(searchKey);
				dispData.setConsignorCode(stock[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new IdmControlParameter();
			}
		}
		return dispData;

	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>IdmControlParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>IdmControlParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が1000件を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		IdmControlParameter param = (IdmControlParameter) searchParam;

		// 作業者コード、パスワードのチェック
		if (!checkWorker(conn, param))
		{
			return null;
		}

		IdmStockHandler stockHandler = new IdmStockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();
		StockSearchKey namesearchKey = new StockSearchKey();

		// データの検索
		// 在庫ステータス(センター在庫)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 在庫数が1以上であること
		searchKey.setStockQty(1, ">=");

		// 荷主コード
		searchKey.setConsignorCode(param.getConsignorCodeDisp());
		namesearchKey.setConsignorCode(param.getConsignorCodeDisp());
		// 商品コード
		String itemcode = param.getItemCodeDisp();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
			namesearchKey.setItemCode(itemcode);
		}
		// ケース/ピース区分(作業形態)
		// 全て以外
		if (!param.getCasePieceFlagDisp().equals(IdmControlParameter.CASEPIECE_FLAG_ALL))
		{
			// ケース
			if (param.getCasePieceFlagDisp().equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			// ピース
			else if (param.getCasePieceFlagDisp().equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
			// 指定なし
			else if (
				param.getCasePieceFlagDisp().equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
		}

		IdmOperate iOpe = new IdmOperate();

		// 開始棚
		String fromlocation =
			iOpe.importFormatIdmLocation(
				param.getStartBankNo(),
				param.getStartBayNo(),
				param.getStartLevelNo());
		if (!StringUtil.isBlank(param.getStartBankNo()))
		{
			searchKey.setLocationNo(fromlocation, ">=");
			namesearchKey.setLocationNo(fromlocation, ">=");
		}
		// 終了棚
		String tolocation =
			iOpe.importFormatIdmLocation(
				param.getEndBankNo(),
				param.getEndBayNo(),
				param.getEndLevelNo());
		if (!StringUtil.isBlank(param.getEndBankNo()))
		{
			searchKey.setLocationNo(tolocation, "<=");
			namesearchKey.setLocationNo(tolocation, "<=");
		}
		// ケースＩＴＦ
		String itf = param.getITFDisp();
		if (!StringUtil.isBlank(itf))
		{
			searchKey.setItf(itf);
			namesearchKey.setItf(itf);
		}
		// ボールＩＴＦ
		String bundleitf = param.getBundleITFDisp();
		if (!StringUtil.isBlank(bundleitf))
		{
			searchKey.setBundleItf(bundleitf);
			namesearchKey.setBundleItf(bundleitf);
		}

		// DMSTOCKとDMLOCATEの結合表からDMSTOCKの項目を取得する。
		searchKey.setLastUpdateDateCollect();
		searchKey.setConsignorCodeCollect();
		searchKey.setItemCodeCollect();
		searchKey.setItemName1Collect();
		searchKey.setCasePieceFlagCollect();
		searchKey.setLocationNoCollect();
		searchKey.setEnteringQtyCollect();
		searchKey.setBundleEnteringQtyCollect();
		searchKey.setStockQtyCollect();
		searchKey.setUseByDateCollect();
		searchKey.setItfCollect();
		searchKey.setBundleItfCollect();
		searchKey.setStockIdCollect();
		searchKey.setAllocationQtyCollect();
		
		// 商品コード、ケースピース区分／ロケーション順に表示
		searchKey.setItemCodeOrder(1, true);
		searchKey.setCasePieceFlagOrder(2, true);
		searchKey.setLocationNoOrder(3, true);
	
		// ためうち表示件数のチェックを行う
		if(!canLowerDisplay(stockHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}

		// ためうちに表示するデータを取得する
		Stock[] resultEntity = (Stock[]) stockHandler.find(searchKey);

		// 登録日時の新しい荷主名称を取得します。
		namesearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 在庫数が1以上であること
		namesearchKey.setStockQty(1, ">=");
		namesearchKey.setLastUpdateDateOrder(1, false);
		Stock[] stock = (Stock[]) stockHandler.find(namesearchKey);
		String consignorname = "";
		if (stock != null && stock.length != 0)
		{
			consignorname = stock[0].getConsignorName();
		}

		Vector vec = new Vector();

		for (int i = 0; i < resultEntity.length; i++)
		{
			IdmControlParameter dispData = new IdmControlParameter();
			// 荷主コード
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			// 荷主名称(登録日時の新しい荷主名称) 
			dispData.setConsignorName(consignorname);
			// 商品コード
			dispData.setItemCode(resultEntity[i].getItemCode());
			// 商品名称
			dispData.setItemName(resultEntity[i].getItemName1());
			// ケース/ピース区分
			dispData.setCasePieceFlag(resultEntity[i].getCasePieceFlag());
			// ケース/ピース区分名称
			// ケース/ピース区分(作業形態)からケース/ピース区分名称を取得します。
			String casepiecename =
				DisplayUtil.getPieceCaseValue(resultEntity[i].getCasePieceFlag());
			dispData.setCasePieceFlagNameDisp(casepiecename);
			// 出庫棚
			dispData.setLocationNo(resultEntity[i].getLocationNo());
			// ケース入数
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			// ボール入数
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());
			// 引当可能ケース数
			dispData.setAllocateCaseQty(
				DisplayUtil.getCaseQty(
					resultEntity[i].getAllocationQty(),
					resultEntity[i].getEnteringQty(),
					resultEntity[i].getCasePieceFlag()));
			// 引当可能ピース数
			dispData.setAllocatePieceQty(
				DisplayUtil.getPieceQty(
					resultEntity[i].getAllocationQty(),
					resultEntity[i].getEnteringQty(),
					resultEntity[i].getCasePieceFlag()));
			// ケースＩＴＦ
			dispData.setITF(resultEntity[i].getItf());
			// ボールＩＴＦ
			dispData.setBundleITF(resultEntity[i].getBundleItf());
			// 賞味期限
			dispData.setUseByDate(resultEntity[i].getUseByDate());
			// 在庫ＩＤ
			dispData.setStockId(resultEntity[i].getStockId());
			// 最終更新日時
			dispData.setLastUpdateDate(resultEntity[i].getLastUpdateDate());

			vec.addElement(dispData);
		}

		IdmControlParameter[] paramArray = new IdmControlParameter[vec.size()];
		vec.copyInto(paramArray);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		return paramArray;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、予定外出庫設定のスケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         IdmControlParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return 検索結果を持つ<CODE>IdmControlParameter</CODE>インスタンスの配列。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			// 作業者コード、パスワードのチェック
			IdmControlParameter workparam = (IdmControlParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return null;
			}

			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return null;
			}

			// ためうちの入力チェック
			if (!checkList(startParams))
			{
				return null;
			}

			// 作業者情報取得
			String workercode = workparam.getWorkerCode();
			String workerName = getWorkerName(conn, workercode);
			// 作業日(システム定義日付)
			String sysdate = getWorkDate(conn);

			// 実績情報作成用パラメータ 
			// 端末№
			String terminalno = workparam.getTerminalNumber();
			// 作業区分(予定外出庫)
			String jobtype = SystemDefine.JOB_TYPE_EX_RETRIEVAL;
			// バッチNo.(1設定共通)
			SequenceHandler sequence = new SequenceHandler(conn);
			String batch_seqno = sequence.nextNoPlanBatchNo();

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
				return null;
			}

			// パラメータ抽出件数分処理を行います。
			for (int i = 0; i < startParams.length; i++)
			{
				IdmControlParameter param = (IdmControlParameter) startParams[i];

				String stockid = param.getStockId();
				int enteringqty = param.getEnteringQty();

				// WmsParamより、移動ラック用のエリアNoを取得します。
				param.setAreaNo(WmsParam.IDM_AREA_NO);

				// 出庫数を求める
				int retrievalqty = 0;
				// パラメータの全数区分がTrueの場合、引当可能数分、出庫設定処理を行う。
				if (param.getTotalFlag() == true)
				{
					retrievalqty =
						param.getAllocateCaseQty() * enteringqty + param.getAllocatePieceQty();
				}
				else
				{
					retrievalqty =
						param.getRetrievalCaseQty() * enteringqty + param.getRetrievalPieceQty();
				}
				
				// 作業数量のオーバーフローチェック
				workqty = addWorkQty(workqty, retrievalqty);

				// 在庫情報テーブルを更新する。
				if (!updateStock(conn, stockid, retrievalqty, param.getLastUpdateDate(), param.getRowNo()))
				{
					return null;
				}

				// ロケーション情報の更新を行います。
				LocateOperator wLocateOperator = new LocateOperator(conn);
				wLocateOperator.modifyLocateStatus(param.getLocationNo(), CLASS_RETRIEVAL);

				// 実績送信情報テーブル(DNHOSTSEND)の登録
				if (!createHostsend(conn,
					param,
					stockid,
					workercode,
					workerName,
					sysdate,
					terminalno,
					jobtype,
					CLASS_RETRIEVAL,
                    batch_seqno,
					retrievalqty))
				{
					return null;
				}
			}

			// 作業者実績情報テーブル(DNWORKERRESULT)の登録
			updateWorkerResult(conn, workercode, workerName, sysdate, terminalno, jobtype, workqty);

			// 再表示用のデータを取得する
			IdmControlParameter[] viewParam = (IdmControlParameter[]) query(conn, workparam);

			// 予定外入庫リストファイル発行を行う
			if (workparam.getListFlg() == true)
			{
				IdmNoPlanRetrievalWriter writer = new IdmNoPlanRetrievalWriter(conn);
				writer.setBatchNumber(batch_seqno);

				if (writer.startPrint())
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

			return viewParam;

		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	// Protected methods ---------------------------------------------
	/** 
	 * ためうち入力チェックを行います。<BR>
	 * @param  searchParam 表示データ取得条件を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>IdmControlParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return true：入力内容が正常な場合  false：そうでない場合
	 */
	protected boolean checkList(Parameter[] searchParams)
	{
		for (int i = 0; i < searchParams.length; i++)
		{
			IdmControlParameter param = (IdmControlParameter) searchParams[i];

			// パラメータからケース入数、在庫数、出庫数（画面入力データ）を取得する。
			String casepieceflag = param.getCasePieceFlag();
			int enteringqty = param.getEnteringQty();
			int caseqty = param.getRetrievalCaseQty();
			int pieceqty = param.getRetrievalPieceQty();

			long allocaionqty =
				(long)param.getAllocateCaseQty() * (long)enteringqty + (long)param.getAllocatePieceQty();

			// 引当可能数が0の場合
			if (allocaionqty == 0)
			{
				// 6023380=No.{0} 引当可能数が0の場合は、作業できません。
				wMessage = "6023380" + wDelim + param.getRowNo();
				return false;
			}

			// 全数出庫にチェックが入っていない場合のみチェックを行います。
			if (param.getTotalFlag())
			{
				continue;
			}

			// 入力値チェック
			if (!stockRetrievalInputCheck(casepieceflag, enteringqty, caseqty, pieceqty, param.getRowNo()))
			{
				return false;
			}

			// オーバーフローチェック
			long retrievalqty =
				(long) param.getRetrievalCaseQty() * (long) param.getEnteringQty()
					+ param.getRetrievalPieceQty();
			// 出庫数が1以上設定の場合
			if (retrievalqty > 0)
			{
				// 出庫数（入力データ）が引当可能数より大きい
				if (retrievalqty > allocaionqty)
				{
					// 6023216=No.{0} 出庫時、出庫数は引当可能数以下の値を入力してください。
					wMessage = "6023216" + wDelim + param.getRowNo();
					return false;
				}
			}
			
			if (retrievalqty > WmsParam.MAX_STOCK_QTY)
			{
				// 6023272 = No.{0} {1}には{2}以下の値を入力してください。
				wMessage =
					"6023272"
						+ wDelim
						+ param.getRowNo()
						+ wDelim
						+ DisplayText.getText("LBL-W0420")
						+ wDelim
						+ MAX_STOCK_QTY_DISP;
				return false;
			}

		}

		return true;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	/**
	 * 更新対象の在庫情報をロックする。
	 * 引数で指定された作業情報の配列から在庫IDの配列を生成し、
	 * 該当する在庫情報をロックする。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>在庫ID</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param 設定内容を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンスの配列。 <BR>
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

		IdmStockHandler shandler = new IdmStockHandler(conn);
		StockSearchKey skey = new StockSearchKey();

		LocateHandler locateHandler = new LocateHandler(conn);
		LocateSearchKey locatesearchkey = new LocateSearchKey();

		// WmsParamより、移動ラック用のエリアNoを取得します。
		String w_AreaNo = WmsParam.IDM_AREA_NO;

		// 画面からの入力情報が既に在庫情報に存在する場合、該当する在庫IDを取得する。
		for (int i = 0; i < param.length; i++)
		{
			skey.KeyClear();

			// 既存在庫情報の検索
			skey.setStockId(param[i].getStockId());
			Stock stock = (Stock) shandler.findPrimary(skey);

			// Vectorに確保
			if (stock != null)
			{
				vec.addElement(stock.getStockId());

				// ロケーション情報の検索
				locatesearchkey.KeyClear();
				locatesearchkey.setAreaNo(w_AreaNo);
				locatesearchkey.setLocationNo(stock.getLocationNo());
				// 既存ロケーション情報の検索
				Locate locate = (Locate) locateHandler.findPrimary(locatesearchkey);

				// Vectorに確保
				if (locate != null)
				{
					vec2.addElement(locate.getLocationNo());
					vec3.addElement(locate.getAreaNo());
				}
			}
		}

		// 既存の在庫情報が存在する場合
		if (vec.size() > 0)
		{
			stockIdList = new String[vec.size()];
			vec.copyInto(stockIdList);

			skey.KeyClear();
			skey.setStockId(stockIdList);

			// Vectorに確保した在庫情報全てをロック
			shandler.findForUpdateNowait(skey);
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

	// Private methods -----------------------------------------------
	/**
	 * 在庫情報テーブルを更新します。<BR>
	 * @param conn データベースとのコネクションオブジェクト<BR>
	 * @param stockid        在庫ＩＤ
	 * @param retrievalqty   出庫数
	 * @param lastupdatedate 最終更新日時
	 * @param rowno		      リストセルの行No.
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws ScheduleException このメソッドが呼び出された場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	private boolean updateStock(
		Connection conn,
		String stockid,
		int retrievalqty,
		Date lastupdatedate,
		int rowno)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			IdmStockHandler stockHandler = new IdmStockHandler(conn);
			StockSearchKey stocksearchKey = new StockSearchKey();

			// 該当在庫の他端末チェックを行う。
			stocksearchKey.setStockId(stockid);
			stocksearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stocksearchKey.setStockQty(1, ">=");

			Stock stock = (Stock) stockHandler.findPrimary(stocksearchKey);

			if (!stock.getLastUpdateDate().equals(lastupdatedate))
			{
				// 他端末エラーが発生した場合、処理終了
				// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
				wMessage = "6023209" + wDelim + rowno;
				return false;
			}
			
			// 受け取ったパラメータの内容をもとに在庫数を更新する。 
			// 在庫情報の在庫数からパラメータの出庫数を減算し、出庫後の在庫数を求める
			int resultqty = stock.getStockQty() - retrievalqty;

			// 0在庫になる場合、在庫情報を削除します。
			if (resultqty == 0)
			{
				stocksearchKey.KeyClear();
				stocksearchKey.setStockId(stock.getStockId());
				stockHandler.drop(stocksearchKey);
			}
			// 在庫数が1以上の場合、在庫情報を更新します。
			else
			{
				StockAlterKey stockAlterKey = new StockAlterKey();
				stockAlterKey.setStockId(stockid);
				stockAlterKey.updateStockQty(resultqty);
				stockAlterKey.updateAllocationQty(stock.getAllocationQty() - retrievalqty);
				stockAlterKey.updateLastUpdatePname(CLASS_RETRIEVAL);

				stockHandler.modify(stockAlterKey);
			}

			return true;

		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
}
//end of class
