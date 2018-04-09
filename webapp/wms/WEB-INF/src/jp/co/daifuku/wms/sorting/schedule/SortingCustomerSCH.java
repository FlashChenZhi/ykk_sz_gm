package jp.co.daifuku.wms.sorting.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.crossdoc.CrossDocOperator;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.sorting.report.SortingWorkWriter;

/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * 出荷先別仕分設定処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷先別仕分設定処理を行います。<BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、<BR>
 * トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *	作業情報に未開始荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
 *	該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *	<BR>
 *	[検索条件] <BR> 
 *	<BR> 
 *	作業区分が仕分<BR> 
 *	状態フラグが未開始<BR> 
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *	画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *	該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *	エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *	検索するテーブルは作業情報テーブル(DNWORKINFO)。<BR>
 *	検索対象が1000件(WMSParamに定義されたMAX_NUMBER_OF_DISP)を超えた場合、表示は行いません。<BR>
 *	リストセルのヘッダに表示する荷主名称は登録日時の新しい値を取得します。<BR>
 *	作業No.と最終更新日時は一つの配列に集約された件数分Vectorに格納します。<BR>
 *	<BR>
 *	[検索条件] <BR> 
 *	<BR>
 *	作業区分が仕分<BR>
 *	状態フラグが未開始<BR>
 *	<BR>
 *	[パラメータ] *必須入力<BR>
 *	<BR>
 *	作業者コード		：WorkerCode * <BR>
 *	パスワード			：PassWord * <BR>
 *	荷主コード			：ConsignorCode * <BR>
 *	仕分予定日			：PlanDate * <BR>
 *	商品コード			：ItemCode <BR>
 *	ケース/ピース区分	：CasePieceFlag <BR>
 *	クロス/ＤＣ			：TcdcFlag <BR>
 *	<BR>
 *	[処理条件チェック] <BR>
 *	<BR>
 *	-作業者コードチェック処理-(<CODE>AbstructSortingSCH()</CODE>クラス) <BR>
 *	<BR>
 *	-表示件数チェック- (<CODE>check()</CODE>メソッド)<BR>
 *	<BR>
 *	-仕分総数のオーバーフローチェック-<BR>
 *	<DIR>
 *		999,999,999を超える場合は999,999,999を表示<BR>
 *	</DIR>
 *	<BR>
 *	[返却データ] +集約条件<BR>
 *	<BR>
 *	荷主コード										：ConsignorCode <BR>
 *	荷主名称(最新のものを表示する)					：ConsignorName <BR>
 *	仕分予定日										：PlanDate <BR>
 *	商品コード										：ItemCode + <BR>
 *	商品名称(集約されたうち最新のものを表示する)	：ItemName <BR>
 *	ケース/ピース区分名称							：CasePieceName + <BR>
 *	クロス／ＤＣ名称								：TcdcName + <BR>
 *	仕分総数										：TotalPlanQty <BR>
 *	ケース入数										：EnteringQty <BR>
 *	ボール入数										：BundleEnteringQty <BR>
 *	仕分ケース数(作業可能数/ケース入数)				：PlanCaseQty <BR>
 *	仕分ピース数(作業可能数%ケース入数)				：PlanPieceQty <BR>
 *	仕分先数										：SortingQty <BR>
 *	作業No.											：JobNo[] <BR>
 *	最終更新日時									：LastUpdateDate[] <BR>
 * </DIR>
 * <BR>
 * 3.仕分開始ボタン押下処理(<CODE>startSCH()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *	ためうちエリアに表示されている内容をパラメータとして受け取り、仕分開始処理を行います。 <BR>
 *	処理完了時にパラメータの仕分作業リスト発行区分がtrueの場合、仕分作業リストファイル作成クラスを起動します。<BR>
 *	処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *	エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *	作業No.と最終更新日時はVectorで渡るので、一つづつ取得し、データベースと比較します。<BR>
 *	<BR>
 *	[パラメータ] *必須入力 +どちらか必須入力 <BR>
 *	<BR>
 *	作業者コード			：WorkerCode * <BR>
 *	パスワード				：PassWord * <BR>
 *	仕分作業リスト発行区分	：SortingWorkListFlg * <BR>
 *	ためうち行No.			：RowNo * <BR>
 *	作業No.					：JobNo[] * <BR>
 *	最終更新日時			：LastUpdateDate[] * <BR>
 *	端末No.					：TerminalNumber * <BR>
 *	<BR>
 *	[処理条件チェック] <BR>
 *	<BR>
 *	-作業者コードチェック処理-(<CODE>AbstructSortingSCH()</CODE>クラス)<BR>
 *	<BR>
 *	-日次更新処理中チェック処理-(<CODE>AbstructSortingSCH()</CODE>クラス) <BR>
 *	<BR> 
 *	[更新登録処理] <BR>
 *	<BR>
 *	-排他チェック<BR>
 *	デッドロックを防ぐため、設定する作業情報を全てロックする。<BR>
 *	作業No.、作業区分(仕分)にてデータを検索し、データをロックする。<BR>
 *	<BR>
 *	-データの更新<BR>
 *	<BR>
 *	デッドロックを防ぐため、作業情報、仕分予定情報、在庫情報の順番でテーブルの更新を行う。<BR>
 *	1設定分の仕分完了処理までが終わってから、作業者実績情報の登録または更新を行う。<BR>
 *	<BR>
 *	-作業情報テーブル(DNWORKINFO)の更新<BR> 
 *	<BR>
 *	1.作業情報の状態フラグを完了に更新する。(完了、欠品どちらの場合も完了とする。)<BR>
 *	2.最終更新処理名を更新する。<BR>  
 *	3.受け取ったパラメータの内容をもとに作業実績数、作業欠品数を更新する。<BR> 
 *	<DIR>
 *		パラメータの仕分数を作業情報の作業実績数に更新する。<BR> 
 *		欠品の場合、作業欠品数を作業可能数からパラメータの仕分数を減算した値に更新する。<BR>
 *	</DIR>
 *	4.受け取ったパラメータの内容をもとに作業結果ロケーションNo.、作業結果賞味期限を更新する。<BR> 
 *	5.受け取ったパラメータの内容をもとに作業者コード、作業者名、端末No.を更新する。<BR>
 *	作業者名の検索時、削除区分が使用可能かどうかは検索条件には含みません。<BR> 
 *	<BR>
 *	-仕分予定情報テーブル(DNSORTINGPLAN)の更新 <BR>
 *	<BR>
 *	状態フラグ(未開始、一部完了)にてデータを検索すること。
 *	1.仕分予定情報の状態フラグを更新する。<BR>
 *	<DIR>
 *		予定情報を予定一意キーにてデータを検索し、仕分予定情報の状態フラグを更新する。<BR>
 *		取得データが全て完了の場合、完了に更新する。<BR>
 *		全て完了でなければ、一部完了に更新する。<BR>
 *	</DIR>  
 *	2.最終更新処理名を更新する。<BR> 
 *	3.受け取ったパラメータの内容をもとに仕分実績数、仕分欠品数を更新する。<BR> 
 *	<DIR>
 *		パラメータの仕分数を仕分予定情報の仕分実績数に加算する。<BR>
 *		欠品の場合、仕分欠品数を作業情報の仕分可能数からパラメータの仕分数を減算した値に更新する。<BR> 
 *	</DIR>
 *	<BR>
 *	-仕分完了処理-(<CODE>SortingCompleteOperator()</CODE>クラス)<BR>  
 *	<BR>
 *	作業No.、処理名をセットする。<BR>
 *	作業No.をもとに作業情報の検索を行い、在庫情報の更新を行う。<BR>
 *	また、実績送信情報テーブルに実績を作成する。<BR> 
 *	処理名は各テーブル登録用に使用する。<BR> 
 *	<BR>
 *	-作業者実績情報テーブル(DNWORKERRESULT)の登録または更新-(<CODE>AbstructSortingSCH()</CODE>クラス)<BR>  
 *	<BR>
 *	受け取ったパラメータの内容をもとに作業者実績情報を登録または更新します。<BR>
 *	<BR>
 *	[印刷処理] <BR>
 *	<BR>
 *	仕分作業リスト発行処理クラスに作業No.を渡します。<BR>
 *	印刷データがない場合は印刷処理を行いません。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/08</TD><TD>T.Hondo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingCustomerSCH extends AbstractSortingSCH
{
	// Class variables -----------------------------------------------
	/**
	 * クラス名(仕分)
	 */
	public static String PROCESSNAME = "SortingCustomerSCH";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:34 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 * @param conn データベースとのコネクションオブジェクト
	 */
	public SortingCustomerSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>SortingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// データの検索
		// 作業区分(仕分)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 状態フラグ(未開始)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		SortingParameter dispData = new SortingParameter();

		if (workingHandler.count(searchKey) == 1)
		{
			try
			{
				WorkingInformation working = (WorkingInformation) workingHandler.findPrimary(searchKey);
				dispData.setConsignorCode(working.getConsignorCode());
			}
			catch (NoPrimaryException e)
			{
				return new SortingParameter();
			}
		}

		return dispData;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。<BR>
	 * @param searchParam 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>SortingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>SortingParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が1000件を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。<BR>
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		SortingParameter param = (SortingParameter) searchParam;

		// 作業者コード、パスワードのチェック
		if (!checkWorker(conn, param))
		{
			return null;
		}

		// 必須入力チェック
		if (StringUtil.isBlank(param.getWorkerCode()) || StringUtil.isBlank(param.getPassword())
			|| StringUtil.isBlank(param.getConsignorCode())	|| StringUtil.isBlank(param.getPlanDate()))
		{
			throw (new ScheduleException("mandatory error!"));
		}

		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey countsearchKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey consignorKey = new WorkingInformationSearchKey();

		// データの検索条件・表示順をセット
		setWorkingInformationSearchKey(param, searchKey, countsearchKey, consignorKey);

		// タメうちエリアに対象データを表示できるかチェック
		if(!canLowerDisplay(workingHandler.count(countsearchKey)))
		{
			return returnNoDisplayParameter();
		}

		// 集約表示を行う画面のため、WMSParamの表示件数だけではなく
		// WMSParamのSQL検索の最大件数でチェックを行います。
		int selectCount = workingHandler.count(searchKey);
		if (selectCount > WmsParam.MAX_NUMBER_OF_SQL_FIND)
		{
			// 6023469=作業件数が{0}件該当しました。件数が{1}件を超えるため、検索条件を絞り込んでください。
			String msg = "6023469" 
					+ wDelim + WmsFormatter.getNumFormat(selectCount)
					+ wDelim + MAX_NUMBER_OF_SQL_FIND_DISP;
			wMessage = msg;
			return null;
		}

		WorkingInformation[] resultEntity = (WorkingInformation[]) workingHandler.find(searchKey);
		
		// 登録日時の新しい荷主名称を取得する。
		consignorKey.setRegistDateOrder(1, false);
		WorkingInformation[] workinfo = (WorkingInformation[]) workingHandler.find(consignorKey);
		String consignorName = workinfo[0].getConsignorName();		

		Vector vec = new Vector();
		String tempItemCode = "";
		String tempWorkFormFlag = "";
		String tempTcdcFlag = "";
		Vector jobnoVec = new Vector();
		Vector lastUpdateVec = new Vector();
		int tempPlanqty = 0;
		int tempPlanCaseqty = 0;
		int tempPlanPieceqty = 0;
		int tempCaseEnteringQty = 0;
		int tempEnteringQty = 0;
		int tempBundleEnteringQty = 0;
		int casePieceFlag = 0;

		Date tempLastUpdate = null;
		
		//仕分先数のカウント用です。
		Collection locationCount = new TreeSet();
		
		int i = 0;
		for (; i < resultEntity.length; i++)
		{
			// １件目の商品コード、ケースピース区分、ＴＣＤＣ区分を格納
			if (i == 0)
			{
				tempItemCode = resultEntity[i].getItemCode();
				tempWorkFormFlag = resultEntity[i].getWorkFormFlag();
				tempTcdcFlag = resultEntity[i].getTcdcFlag();
				tempLastUpdate = resultEntity[i].getLastUpdateDate();
				tempCaseEnteringQty = resultEntity[i].getEnteringQty();
				tempEnteringQty = resultEntity[i].getEnteringQty();
				tempBundleEnteringQty = resultEntity[i].getBundleEnteringQty();
			}
			// 前回までのデータと商品コード、ケースピース区分、ＴＣＤＣ区分を比較し、
			// 変更されている場合、パラメータにＳＥＴ
			// 全て(ケースピース同一)にチェックがある場合
			if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_ALL_SAME))
			{
				if (!tempItemCode.equals(resultEntity[i].getItemCode()) || !tempTcdcFlag.equals(resultEntity[i].getTcdcFlag()))
				{
					SortingParameter dispData = new SortingParameter();
					// パラメーターへセットする共通項目のセット
					setCommonParamater(locationCount, resultEntity, consignorName, jobnoVec, lastUpdateVec, tempPlanqty, tempBundleEnteringQty, i, dispData);
					// ケース／ピース区分(名称)とケース入り数のセット
					setCasePieceName( tempCaseEnteringQty, tempEnteringQty, casePieceFlag, dispData);
					// 仕分ケース数
					dispData.setPlanCaseQty(tempPlanCaseqty);
					// 仕分ピース数
					dispData.setPlanPieceQty(tempPlanPieceqty);

					// 初期化
					tempPlanqty = 0;
					tempPlanCaseqty = 0;
					tempPlanPieceqty = 0;
					tempCaseEnteringQty = resultEntity[i].getEnteringQty();
					tempEnteringQty = resultEntity[i].getEnteringQty();
					tempBundleEnteringQty = resultEntity[i].getBundleEnteringQty();
					casePieceFlag = 0;
					tempLastUpdate = resultEntity[i].getLastUpdateDate();

					// 仕分先数のクリア。
					if(!locationCount.isEmpty())
					{
						locationCount.clear();
					}
					
					vec.addElement(dispData);
					jobnoVec.clear();
					lastUpdateVec.clear();
				}
				// 仕分予定ケース数のカウント
				tempPlanCaseqty += DisplayUtil.getCaseQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag());
				// 仕分予定ピース数のカウント
				tempPlanPieceqty += DisplayUtil.getPieceQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getWorkFormFlag());	
			}
			// 全て（ケースピース同一）以外｛全て（ケースピース別）、ケース、ピース｝の場合
			else
			{
				// 集約条件の項目に変更があればタメうちエリアに表示用パラメーターへセット
				if (!tempItemCode.equals(resultEntity[i].getItemCode())
					|| !tempWorkFormFlag.equals(resultEntity[i].getWorkFormFlag())
					|| !tempTcdcFlag.equals(resultEntity[i].getTcdcFlag()))
				{
					SortingParameter dispData = new SortingParameter();
					// パラメーターへセットする共通項目のセット
					setCommonParamater(locationCount, resultEntity, consignorName, jobnoVec, lastUpdateVec, tempPlanqty, tempBundleEnteringQty, i, dispData);
					// ケース／ピース区分(名称)
					dispData.setCasePieceName(DisplayUtil.getPieceCaseValue(resultEntity[i - 1].getWorkFormFlag()));
					// ケース入数
					dispData.setEnteringQty(tempEnteringQty);
					// 仕分ケース数
					dispData.setPlanCaseQty(DisplayUtil.getCaseQty(tempPlanqty, resultEntity[i - 1].getEnteringQty(), resultEntity[i - 1].getWorkFormFlag()));
					// 仕分ピース数
					dispData.setPlanPieceQty(DisplayUtil.getPieceQty(tempPlanqty, resultEntity[i - 1].getEnteringQty(), resultEntity[i - 1].getWorkFormFlag()));

					// 初期化
					tempPlanqty = 0;
					tempPlanCaseqty = 0;
					tempPlanPieceqty = 0;
					tempCaseEnteringQty = resultEntity[i].getEnteringQty();
					tempEnteringQty = resultEntity[i].getEnteringQty();
					tempBundleEnteringQty = resultEntity[i].getBundleEnteringQty();
					casePieceFlag = 0;
					tempLastUpdate = resultEntity[i].getLastUpdateDate();

					// 仕分先数のクリア
					if(!locationCount.isEmpty())
					{
						locationCount.clear();
					}
					
					vec.addElement(dispData);
					jobnoVec.clear();
					lastUpdateVec.clear();
				}
			}

			// 予定数のカウント
			tempPlanqty += resultEntity[i].getPlanEnableQty();
			//仕分先数のカウント
			locationCount.add(resultEntity[i].getLocationNo());

			// 最新のｹｰｽ入数、ﾎﾞｰﾙ入数の取得
			// 最終更新日時がまえのものよりも新しければ入数を取得する
			if (tempLastUpdate.before(resultEntity[i].getLastUpdateDate()))
			{
				// ケースピース同一の場合、ｹｰｽ入数はケース品から取得する(ピース品しかない場合はピース品から取得する)
				if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_ALL_SAME)
				&& resultEntity[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_CASE))
				{
					tempCaseEnteringQty = resultEntity[i].getEnteringQty();
				}
				else
				{
					tempEnteringQty = resultEntity[i].getEnteringQty();
				}
				tempBundleEnteringQty = resultEntity[i].getBundleEnteringQty();
			}

			// 次データとの比較用に現在の商品コード、ケース/ピースフラグ、TC/DCフラグを保持
			tempItemCode = resultEntity[i].getItemCode();
			tempWorkFormFlag = resultEntity[i].getWorkFormFlag();
			tempTcdcFlag = resultEntity[i].getTcdcFlag();

			// 全て(ケースピース同一)の場合、ケース／ピース区分(名称)を判別するためのフラグ
			if( tempPlanCaseqty > 0 && tempPlanPieceqty > 0 )
			{
				casePieceFlag = Integer.parseInt(WorkingInformation.CASEPIECE_FLAG_MIX);
			}
			else
			{
				casePieceFlag = Integer.parseInt(tempWorkFormFlag);
			}

			tempLastUpdate = resultEntity[i].getLastUpdateDate();

			// 作業No.
			jobnoVec.addElement(resultEntity[i].getJobNo());
			// 最終更新日時
			lastUpdateVec.addElement(resultEntity[i].getLastUpdateDate());
		}

		// 最後の1件分を登録
		SortingParameter dispData = new SortingParameter();
		// パラメーターへセットする共通項目のセット
		setCommonParamater(locationCount, resultEntity, consignorName, jobnoVec, lastUpdateVec, tempPlanqty, tempBundleEnteringQty, i, dispData);

		// ボール入数
		dispData.setBundleEnteringQty(tempBundleEnteringQty);		
				
		// 全て(ケースピース同一)にチェックがある場合
		if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_ALL_SAME))
		{
			// ケース／ピース区分(名称)とケース入数のセット
			setCasePieceName( tempCaseEnteringQty, tempEnteringQty, casePieceFlag, dispData);
		}
		else
		{
			// ケース／ピース区分(名称)
			dispData.setCasePieceName(DisplayUtil.getPieceCaseValue(resultEntity[i - 1].getWorkFormFlag()));
			dispData.setEnteringQty(tempEnteringQty);
		}

		// 全て(ケースピース同一)
		if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_ALL_SAME))
		{
			// 仕分ケース数
			dispData.setPlanCaseQty(tempPlanCaseqty);
			// 仕分ピース数
			dispData.setPlanPieceQty(tempPlanPieceqty);
		}
		else
		{
			// 仕分ケース数
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(tempPlanqty, resultEntity[i - 1].getEnteringQty(), resultEntity[i - 1].getWorkFormFlag()));
			// 仕分ピース数
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(tempPlanqty, resultEntity[i - 1].getEnteringQty(), resultEntity[i - 1].getWorkFormFlag()));
		}

		vec.addElement(dispData);
		jobnoVec.clear();
		lastUpdateVec.clear();

		SortingParameter[] paramArray = new SortingParameter[vec.size()];
		vec.copyInto(paramArray);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		return paramArray;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、仕分設定スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        SortingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			SortingParameter[] param = (SortingParameter[]) startParams;

			// 作業者コード、パスワードのチェック
			SortingParameter workparam = (SortingParameter) startParams[0];

			if (!checkWorker(conn, workparam))
			{
				return false;
			}

			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return false;
			}

			// 対象データ全件の排他チェックを行う。
			if (!lockSummarilyAll(conn, startParams))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return false;
			}
			// 作業区分(仕分)
			String jobtype = WorkingInformation.JOB_TYPE_SORTING;
			// 処理名
			String processname = PROCESSNAME;
			// 作業者コード
			String workercode = workparam.getWorkerCode();
			// 作業者名称
			String workername = getWorkerName(conn, workercode);
			// 作業日(システム定義日付)
			String sysdate = getWorkDate(conn);

			// クロスドックパッケージ有りの場合、クロスドック処理クラス生成	
			CrossDocOperator crossdoc = null;		
			if (isCrossDockPack(conn))
			{
				crossdoc = new CrossDocOperator();					
			}

			// 端末№
			String terminalno = workparam.getTerminalNumber();

			// リスト発行の有無
			int wListFlg = 0;

			Vector vecJobno = new Vector();

			int workqty = 0;

			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey worksearchKey = new WorkingInformationSearchKey();

			for (int i = 0; i < param.length; i++)
			{
				String jobNoArray[] = new String[param[i].getJobNoList().size()];
				param[i].getJobNoList().copyInto(jobNoArray);

				Date LastUpdateArray[] = new Date[param[i].getLastUpdateDateList().size()];
				param[i].getLastUpdateDateList().copyInto(LastUpdateArray);
				for (int j = 0; j < jobNoArray.length; j++)
				{
					// ロック＆排他チェック
					if (!lock(conn, jobNoArray[j], LastUpdateArray[j]))
					{
						// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
						wMessage = "6023209" + wDelim + param[i].getRowNo();
						return false;
					}

					int planqty = 0;
					String location = "";
					String usebydate = "";

					// キークリア
					worksearchKey.KeyClear();

					// 作業No.をセット
					worksearchKey.setJobNo(jobNoArray[j]);

					WorkingInformation resultEntity = (WorkingInformation) workingHandler.findPrimary(worksearchKey);

					planqty = resultEntity.getPlanEnableQty();
					location = resultEntity.getLocationNo();
					usebydate = resultEntity.getUseByDate();

					int inputqty = planqty;

					// 作業数量(1設定分の作業情報の実績数をトータルする)
					workqty = addWorkQty(workqty, inputqty);
															
					// 作業開始フラグが未開始の時
					if(resultEntity.getBeginningFlag().equals(SystemDefine.BEGINNING_FLAG_NOT_STARTED))
					{
						// 前工程の処理が完了していないため、完了処理できません。
						wMessage = "6023376" + wDelim + param[i].getRowNo();
						return false;
					}

					// 作業情報テーブル(DNWORKINFO)の更新
					if (!updateWorkinginfo(conn, jobNoArray[j], planqty, inputqty, location, usebydate, workercode, workername, terminalno))
					{
						return false;
					}

					// 仕分予定情報テーブル(DNSORTINGPLAN)の更新
					if (!updateSortingPlan(conn, jobNoArray[j], planqty, inputqty))
					{
						return false;
					}

					// 仕分完了処理(DMSTOCK, DNHOSTSEND)
					SortingCompleteOperator Sorting = new SortingCompleteOperator();
					if (!Sorting.complete(conn, jobNoArray[j], jobtype, processname))
					{
						return false;
					}
								
					// クロスドックパッケージ有りの運用であれば、クロスドック完了処理を実行する。
					if (isCrossDockPack(conn))
					{		
						// 仕分予定一意キー						
						String planUkey = resultEntity.getPlanUkey();
						// 完了(実績)数：作業情報の可能数をセットする。
						int completeQty = resultEntity.getPlanEnableQty();
						// クロスドック処理の実行、欠品数は画面入力がないので0固定
						crossdoc.complete(conn, planUkey, completeQty, 0);
					}
					
					// 仕分作業リスト発行する場合
					if (param[i].getSortingWorkListFlg() == true)
					{
						vecJobno.addElement(jobNoArray[j]);

						wListFlg++;
					}
				}
			
				// 仕分作業リストファイルの作成
				if (wListFlg > 0)
				{

					// for文の最後のループ時のみ印刷を実行する
					if (i == param.length - 1)
					{
						String wCasePieceFlag = workparam.getCasePieceName();
						String wTcdcFlag = workparam.getTcdcFlag();
						String[] wlJobno = new String[vecJobno.size()];
						vecJobno.copyInto(wlJobno);
					
						if (startPrint(conn, wlJobno, wCasePieceFlag, wTcdcFlag))
						{
							// 6021012 = 設定後、印刷は正常に終了しました。
							wMessage = "6021012";
						}
						else
						{
							// 6007042=設定後、印刷に失敗しました。ログを参照してください。
							wMessage = "6007042";
						}
						vecJobno.clear();
					}
				}
				else
				{
					// 6001006 = 設定しました。
					wMessage = "6001006";
				}
			}

			// 作業者実績情報テーブル(DNWORKERRESULT)の登録または更新
			// 1設定分の在庫情報テーブルの登録または更新までが終わってから、作業者実績情報の登録または更新を行います。
			updateWorkerResult(conn, workercode, workername, sysdate, terminalno, jobtype, workqty);

			return true;
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	/**
	 * 他の端末で既に変更されたかどうかの確認を行います。
	 * パラメータにセットされている最終更新日時と現在のDBから取得した最終更新日時を比較します。
	 * 比較の結果、双方の最終更新日時が等しい場合は他の端末で変更されていないとし、
	 * 等しくない場合は他の端末で既に変更されていると見なします。
	 * 対象データは、作業情報テーブル(DNWORKINFO)とします。
	 * @param conn       データベースとのコネクションオブジェクト
	 * @param jobno      作業No.
	 * @param lastupdate 最終更新日時
	 * @return 他の端末ですでに変更されている場合はtrue、まだ変更されていない場合はfalseを返します。
	 * 呼び出された場合、<CODE>ScheduleException</CODE>をスローします。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean lock(Connection conn, String jobno, Date lastupdate) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		// データのロック
		// 作業No.
		searchKey.setJobNo(jobno);
		WorkingInformation[] working = (WorkingInformation[]) workingHandler.findForUpdate(searchKey);

		// 作業No.がデータに存在しない(データが削除された場合)
		if (working == null || working.length == 0)
		{
			return false;
		}
		// 状態フラグが未開始以外(取込が行われた場合／メンテナンスで状態がかわる場合)
		if (!(working[0].getStatusFlag()).equals(WorkingInformation.STATUS_FLAG_UNSTART))
		{
			return false;
		}

		// パラメータにセットされている最終更新日時と現在のDBから取得した最終更新日時を比較します。
		// 等しくない場合は他の端末で既に変更されていると見なします。
		if (!working[0].getLastUpdateDate().equals(lastupdate))
		{
			return false;
		}

		return true;
	}

	/**
	 * 仕分作業リスト発行処理クラスに作業No.を渡します。<BR>
	 * 印刷データがない場合は印刷処理を行いません。<BR>
	 * 印刷に成功した場合は、仕分作業リスト発行処理クラスからtrueを、失敗した場合はfalseを受け取り、<BR>
	 * その結果を返します。<BR>
	 * エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
	 * @param  conn Connection データベースとのコネクションオブジェクト
	 * @param  batchno バッチNo.
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	protected boolean startPrint(Connection conn, String[] batchno, String wCasePieceFlag, String wTcdcFlag) throws ReadWriteException, ScheduleException
	{
		SortingWorkWriter writer = new SortingWorkWriter(conn);
		writer.setJobNo(batchno);
		writer.setCasePieceFlag(wCasePieceFlag);
		writer.setTcdcFlag(wTcdcFlag);

		// 印刷処理を開始する。
		if (writer.startPrint())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 作業情報テーブルの更新を行う。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param jobno      作業No.
	 * @param planqty    作業可能数
	 * @param inputqty   作業実績数
	 * @param location   ロケーションNo.
	 * @param usebydate  賞味期限
	 * @param workercode 作業者コード
	 * @param workername 作業者名
	 * @param terminalno 端末No.
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean updateWorkinginfo(Connection conn, String jobno, int planqty, int inputqty, String location, String usebydate, String workercode, String workername, String terminalno)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationAlterKey alterKey = new WorkingInformationAlterKey();

			// 作業No.、作業区分(仕分)をセット
			alterKey.setJobNo(jobno);
			alterKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);

			// 1.作業情報の状態フラグを完了に更新する。(完了、欠品どちらの場合も完了とする。)
			alterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);

			// 2.最終更新処理名を更新する。  
			alterKey.updateLastUpdatePname(PROCESSNAME);

			// 3.受け取ったパラメータの内容をもとに作業実績数、作業欠品数を更新する。 
			// パラメータの仕分数を作業情報の作業実績数に更新する。
			alterKey.updateResultQty(inputqty);

			// 欠品の場合、作業欠品数を作業可能数からパラメータの仕分数を減算した値に更新する。
			if (planqty != inputqty)
			{
				int shortage = planqty - inputqty;
				alterKey.updateShortageCnt(shortage);
			}

			// 4.ロケーションNo.を作業結果ロケーションにSET,
			//   賞味期限を作業結果賞味期限にSET
			alterKey.updateResultLocationNo(location);
			alterKey.updateResultUseByDate(usebydate);

			// 5.受け取ったパラメータの内容をもとに作業者コード、作業者名、端末No.を更新する。
			alterKey.updateWorkerCode(workercode);
			alterKey.updateWorkerName(workername);
			alterKey.updateTerminalNo(terminalno);

			// データの更新
			workingHandler.modify(alterKey);

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
	}

	/**
	 * 仕分予定情報テーブルの更新を行う。<BR>
	 * @param conn データベースとのコネクションオブジェクト<BR>
	 * @param jobno     作業No.
	 * @param planqty   作業可能数
	 * @param inputqty  作業実績数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean updateSortingPlan(Connection conn, String jobno, int planqty, int inputqty) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workingsearchKey = new WorkingInformationSearchKey();

			// データの検索
			// 作業No.
			workingsearchKey.setJobNo(jobno);
			// 作業区分(仕分)
			workingsearchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);

			WorkingInformation working = (WorkingInformation) workingHandler.findPrimary(workingsearchKey);

			if (working != null)
			{
				String planukey = working.getPlanUkey();

				SortingPlanHandler sortingHandler = new SortingPlanHandler(conn);
				SortingPlanSearchKey sortingsearchKey = new SortingPlanSearchKey();
				SortingPlanAlterKey alterKey = new SortingPlanAlterKey();

				// データの検索
				// 仕分予定一意キー
				sortingsearchKey.setSortingPlanUkey(planukey);
				// 状態フラグ(未開始、一部完了、作業中)にてデータを検索すること。
				String[] statusflg = { SortingPlan.STATUS_FLAG_UNSTART, SortingPlan.STATUS_FLAG_COMPLETE_IN_PART, SortingPlan.STATUS_FLAG_NOWWORKING };
				sortingsearchKey.setStatusFlag(statusflg);

				SortingPlan soritng = (SortingPlan) sortingHandler.findPrimary(sortingsearchKey);

				if (soritng != null)
				{
					// 入庫予定一意キーをセット
					alterKey.setSortingPlanUkey(planukey);

					// 1.作業情報を予定一意キーにてデータを検索し、仕分予定情報の状態フラグを更新する。
					//   取得データが全て完了の場合、完了に更新する。
					//   1件も作業中がない場合、一部完了に更新する。
					workingsearchKey.KeyClear();
					workingsearchKey.setPlanUkey(planukey);

					WorkingInformation[] workingstatus = (WorkingInformation[]) workingHandler.find(workingsearchKey);

					int wStatusFlg = 0;
					boolean nowflg = false;
					for (int i = 0; i < workingstatus.length; i++)
					{
						if (workingstatus[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
						{
							wStatusFlg++;
						}
						else if (workingstatus[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
						{
							nowflg = true;
						}
					}
					if (wStatusFlg == workingstatus.length)
					{
						// 完了
						alterKey.updateStatusFlag(SortingPlan.STATUS_FLAG_COMPLETION);
					}
					else if (nowflg == false)
					{
						// 一部完了
						alterKey.updateStatusFlag(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART);
					}

					// 2.最終更新処理名を更新する。 
					alterKey.updateLastUpdatePname(PROCESSNAME);

					// 3.受け取ったパラメータの内容をもとに仕分実績数、仕分欠品数を更新する。 
					// パラメータの仕分数を仕分予定情報の仕分実績数に加算する。			
					int resultqty = soritng.getResultQty() + inputqty;
					alterKey.updateResultQty(resultqty);

					// 欠品の場合、仕分欠品数を作業情報の仕分可能数からパラメータの仕分数を減算した値に更新する。 
					if (planqty != inputqty)
					{
						int shortage = planqty - inputqty;
						alterKey.updateShortageCnt(shortage);
					}

					// データの更新
					sortingHandler.modify(alterKey);

					return true;
				}
				else
				{
					// 6006040 = データの不整合が発生しました。ログを参照してください。{0}
					RmiMsgLogClient.write("6006040" + wDelim + planukey, "SortingCustomerSCH");
					// ここで、ScheduleExceptionをthrowする。(エラーメッセージはセット不要です)
					throw (new ScheduleException());
				}
			}
			else
			{
				// 6006040 = データの不整合が発生しました。ログを参照してください。{0}
				RmiMsgLogClient.write("6006040" + wDelim + jobno, "SortingCustomerSCH");
				// ここで、ScheduleExceptionをthrowする。(エラーメッセージはセット不要です)
				throw (new ScheduleException());
			}
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	// Private methods -----------------------------------------------
	
	/**
	 * queryから呼ばれ、各キーに検索条件をセットします。
	 * @param param 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。
	 * @param searchKey タメうちエリア出力用のデータを検索するキーです。
	 * @param countsearchKey データ件数カウント用のキーです。
	 * @param consignorKey 最新荷主名称取得用のキーです。
	 * @throws ReadWriteException
	 */
	private void setWorkingInformationSearchKey(SortingParameter param, WorkingInformationSearchKey searchKey,
		WorkingInformationSearchKey countsearchKey, WorkingInformationSearchKey consignorKey) throws ReadWriteException
	{
		// 作業区分(仕分)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		countsearchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		consignorKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 状態フラグ(未開始)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		countsearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		consignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		// 開始済フラグ(開始済)
		searchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		countsearchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		consignorKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		// 荷主コード
		searchKey.setConsignorCode(param.getConsignorCode());
		countsearchKey.setConsignorCode(param.getConsignorCode());
		consignorKey.setConsignorCode(param.getConsignorCode());
		// 仕分予定日
		searchKey.setPlanDate(param.getPlanDate());
		countsearchKey.setPlanDate(param.getPlanDate());
		consignorKey.setPlanDate(param.getPlanDate());
		// 商品コード
		String itemcode = param.getItemCode();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
			countsearchKey.setItemCode(itemcode);
			consignorKey.setItemCode(itemcode);
		}
		// 商品コードの集約
		countsearchKey.setItemCodeGroup(1);
		countsearchKey.setItemCodeCollect("");
		
		// ケース/ピース区分(作業形態)
		// 全て(ケースピース同一)以外
		if (!param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_ALL_SAME))
		{
			// ケースピース別
			if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_ALL_DISTINCTION))
			{
				// ケース/ピース区分(作業形態)の集約
				countsearchKey.setWorkFormFlagGroup(2);
				countsearchKey.setWorkFormFlagCollect("");
			}
			// ケース
			else if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				countsearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				consignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			// ピース
			else if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				countsearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				consignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
		}
		
		// クロス／ＤＣ
		// 全て以外
		if (!param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_ALL))
		{
			// クロス
			if (param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
			{
				searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
				countsearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
				consignorKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
			}
			// DC
			else if (param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
			{
				searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
				countsearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
				consignorKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			}
		}
		else
		{
			// 全ての場合は集約する
			countsearchKey.setTcdcFlagGroup(3);
		}
		// 全て(ケースピース同一)の場合、商品コード、クロス／ＤＣ、ケース/ピース区分(作業形態)、最終更新日時の昇順でソート
		// 全て(ケースピース同一)以外の場合、商品コード、ケース/ピース区分(作業形態)、クロス／ＤＣ、最終更新日時の昇順でソート
		searchKey.setItemCodeOrder(1, true);
		if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_ALL_SAME))
		{
			searchKey.setTcdcFlagOrder(2, false);
			searchKey.setWorkFormFlagOrder(3, true);
		}
		else
		{
			searchKey.setWorkFormFlagOrder(2, true);
			searchKey.setTcdcFlagOrder(3, false);
		}
		searchKey.setRegistDateOrder(4, true);
	}
	
	/**
	 * queryから呼ばれ条件ごとのためうちエリア出力項目の共通部分をセットします。
	 * @param locationCount 仕分先数
	 * @param resultEntity 検索結果
	 * @param consignorName 最新の荷主名称
	 * @param jobnoVec 作業№
	 * @param lastUpdateVec 最終更新日時
	 * @param tempPlanqty 仕分総数
	 * @param i 
	 * @param dispData タメうちエリア表示用パラメーター
	 */
	private void setCommonParamater(Collection locationCount, WorkingInformation[] resultEntity, String consignorName,
		Vector jobnoVec, Vector lastUpdateVec, int tempPlanqty, int tempBundleEnteringQty, int i, SortingParameter dispData)
	{
		// 荷主コード
		dispData.setConsignorCode(resultEntity[i - 1].getConsignorCode());
		// 荷主名称(登録日時の新しい荷主名称)
		dispData.setConsignorName(consignorName);
		// 仕分予定日
		dispData.setPlanDate(resultEntity[i - 1].getPlanDate());
		// 商品コード
		dispData.setItemCode(resultEntity[i - 1].getItemCode());
		// 商品名称
		dispData.setItemName(resultEntity[i - 1].getItemName1());
		// クロス／ＤＣ(名称)
		dispData.setTcdcName(DisplayUtil.getTcDcValue(resultEntity[i - 1].getTcdcFlag()));
		// 仕分総数のＭＡＸチェック
		if (tempPlanqty > WmsParam.WORKER_MAX_TOTAL_QTY)
		{
			// 999,999,999を超える場合は999,999,999を表示
			tempPlanqty = WmsParam.WORKER_MAX_TOTAL_QTY;
		}
		// 仕分総数
		dispData.setTotalPlanQty(tempPlanqty);
		// ボール入数
		dispData.setBundleEnteringQty(tempBundleEnteringQty);		
		// 仕分先数
		dispData.setSortingQty(locationCount.size());
		// 作業No
		Vector cpyJobNo = new Vector();
		cpyJobNo = (Vector) jobnoVec.clone();
		dispData.setJobNoList(cpyJobNo);
		// 最終更新日時
		Vector cpyLUDate = new Vector();
		cpyLUDate = (Vector)lastUpdateVec.clone();
		dispData.setLastUpdateDateList(cpyLUDate);
	}
	
	/**
	 * queryから呼ばれ、全て(ケースピース同一)にチェックがある場合に区分名称とケース入数をセットします。
	 * @param tempCaseEnteringQty ケース入数
	 * @param tempEnteringQty ピース品のみの場合のケース入数
	 * @param casePieceflag ケースピース区分
	 * @param dispData タメうちエリア表示用パラメーター
	 */
	private void setCasePieceName(int tempCaseEnteringQty, int tempEnteringQty, int casePieceFlag, SortingParameter dispData)
	{
		//ケースピース混合の場合
		if (casePieceFlag == Integer.parseInt(WorkingInformation.CASEPIECE_FLAG_MIX))
		{
			dispData.setCasePieceName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_MIX));
			dispData.setEnteringQty(tempCaseEnteringQty);
		}
		// ケース品の場合
		else if (casePieceFlag == Integer.parseInt(WorkingInformation.CASEPIECE_FLAG_CASE))
		{
			dispData.setCasePieceName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_CASE));
			dispData.setEnteringQty(tempCaseEnteringQty);
		}
		// ピース品の場合
		else if (casePieceFlag == Integer.parseInt(WorkingInformation.CASEPIECE_FLAG_PIECE))
		{
			dispData.setCasePieceName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_PIECE));
			dispData.setEnteringQty(tempEnteringQty);
		}
	}
}
//end of class