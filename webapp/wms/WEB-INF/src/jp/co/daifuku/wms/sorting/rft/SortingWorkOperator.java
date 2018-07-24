// $Id: SortingWorkOperator.java,v 1.1.1.1 2006/08/17 09:34:34 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

import java.util.Enumeration;
import java.util.Hashtable;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WorkOperator;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.crossdoc.CrossDocOperator;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;

/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * 仕分け完了処理の共通関数が定義されます。<BR>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingWorkOperator
	extends WorkOperator
{
	// Class fields----------------------------------------------------
	/**
	 * クラス名を表すフィールド
	 */
	private static final String CLASS_NAME = "SortingWorkOperator";
	
	/**
	 * クロスドックで渡す値をセットするハッシュテーブル
	 */
	protected Hashtable crossdocInfo = new Hashtable();

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:34 $";
	}

	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public SortingWorkOperator()
	{
	}

	// Public methods ------------------------------------------------

	/**
	 * JAN指定仕分実績送信 [確定処理]<BR>
	 * 仕分実績情報について、以下の確定処理を実行します。<BR>
	 * 
	 * 作業情報、仕分予定情報、在庫情報の該当データをロックします。(排他処理用)<BR>
	 * 作業情報の更新。該当するデータを検索し、状態フラグを完了に更新し、実績数などの項目をセットします。<BR>
	 * 仕分予定情報の更新。該当するデータを検索し、状態フラグを完了に更新し、実績数などの項目をセットします。<BR>
	 * 在庫情報の更新。該当するデータを検索し、作業予定数を減算します。<BR>
	 * 実績送信情報の作成。仕分完了した実績を作成します。<BR>
	 * 作業者実績情報の更新。該当するデータを検索し、作業数、作業回数を加算します。<BR>
	 * <BR>
	 * 作業情報、仕分予定情報のロック<BR>
	 * <DIR>
	 *    作業情報<BR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>作業区分:04仕分</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 *     <LI>荷主コード=(電文の荷主コード)</LI>
	 *     <LI>仕分予定日=(電文の仕分予定日)</LI>
	 *     <LI>商品コード=(電文のJANコード)</LI>
	 *     <LI>ケース/ピース区分(作業形態)=(電文のケース･ピース区分)</LI>
	 * 	   <LI>作業者コード=作業者コード(電文の担当者コード)</LI>
	 * 	   <LI>端末No=RFT番号(電文のハンディ号機No)</LI>
	 *    </UL>
	 *    仕分予定情報<BR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>仕分予定一意キー=作業情報より取得</LI>
	 *     <LI>状態フラグ!=9:削除</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * 作業情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No(実績ファイルの商品識別コード)</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 * 	   <LI>作業者コード=作業者コード(電文の担当者コード)</LI>
	 * 	   <LI>端末No=RFT番号(電文のハンディ号機No)</LI>
	 * 	   <LI>最終更新処理名='ID0044'</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>作業No</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>実績ファイルの仕分実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>作業実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0045"</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 *   実際に仕分した実績数を、該当レコードの作業実績数に分配します。分配する数量は「作業可能数」です。<BR>
	 *   過剰の場合は、最後のレコードに分配します。<BR>
	 * </DIR>
	 * <BR>
	 * 仕分予定情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>出荷予定一意キー=作業情報.作業No (作業情報に対応して、1件の出荷予定情報があります。)
	 *     <LI>状態フラグ!=9:削除</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>2:作業中 3:一部完了 4:完了</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>作業情報の実績数を加算</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>作業情報の欠品数を加算</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0045"</TD></TR>
	 *    </TABLE>
	 *   状態フラグを更新します。<BR>
	 *   <DIR>
	 *         更新対象の仕分予定一意キーに該当する作業情報を検索します。<BR>
	 *         <UL>
	 *         <LI>該当作業情報が全て完了であれば「4:完了」に更新します。</LI>
	 *         <LI>該当作業情報の一部が作業中であれば「2:作業中」に更新します。</LI>
	 *         <LI>該当作業情報が「未開始」「完了」混在であれば「3:一部完了」に更新します。</LI>
	 *         </UL>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 在庫情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>在庫ID=作業情報.在庫ID (作業情報に対応して、1件の在庫情報があります。)
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>在庫ステータス</TD>	<TD>9:完了</TD></TR>
	 *      <TR><TD>作業予定数</TD>		<TD>作業予定数-(作業情報.予定数+作業情報.欠品数)</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0045"</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * <BR>
	 * 実績送信情報の作成<BR>
	 * <DIR>
	 *   作業日をWareNavi_Systemから取得し、作業日にセットします。<BR>
	 *   登録処理名セットします。<BR>
	 *   その他の項目は、作業情報から取得し、セットします。<BR>
	 * </DIR>
	 * 
	 * 作業者実績情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>作業日=WareNavi_Systemの作業日</LI>
	 *     <LI>作業者コード</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 *     <LI>作業区分=04:仕分</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>作業終了日時</TD>	<TD>現在のシステム日付</TD></TR>
	 *      <TR><TD>作業数量</TD>		<TD>+ 今回作業した実績数の総数</TD></TR>
	 *      <TR><TD>作業回数</TD>		<TD>+ 実績ファイルの有効行(完了フラグが0:未仕分以外の行)の行数</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * <BR>
	 * @param	resultData		仕分実績情報  作業情報エンティティの配列
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws ReadWriteException
	 * @throws ScheduleException
	*/
	public void completeWorkingData(
		jp.co.daifuku.wms.base.rft.WorkingInformation[] resultData,
		String workerCode,
		String rftNo,
		boolean isShortage)
		throws
			InvalidStatusException,
			NotFoundException,
			InvalidDefineException,
			UpdateByOtherTerminalException,
			LockTimeOutException, ScheduleException, ReadWriteException
	{
		String[] planUkeyList = lockUpdateData(resultData, rftNo, workerCode, false);

		updateWorkingInformation(rftNo, workerCode, resultData, isShortage);

		updateCompletionStatus(planUkeyList);

		updateWorkerResult(workerCode, rftNo, resultData, ! isShortage);
		
		crossDocProcess();
		
	}
	
	/**
	 * クロスドック処理を呼び出す
	 * <DIR>
	 *    (呼び出し条件)
	 *    <UL>
	 *     <LI>クロスドック管理ありの場合
	 *    </UL>
	 * </DIR>
	 * 
	 * @param planUkey	作業情報の仕分予定一意キーのリスト
	 * @throws ReadWriteException		データベースとの接続で異常が発生した場合に通知されます
	 * @throws InvalidDefineException	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void crossDocProcess() throws ScheduleException,ReadWriteException
	{
		if(SystemParameter.withCrossDocManagement())
		{
			// クロスドック管理あり
			
			for (Enumeration e = crossdocInfo.keys() ;e.hasMoreElements();)
			{ 
				
				// クロスドック処理	
				CrossDocOperator wCrossDocOperator = new CrossDocOperator();
				
				int[] qty = new int[2];
				
				
				String key = e.nextElement().toString();
				qty = (int[])crossdocInfo.get(key);
				// 予定一意キー,完了数,欠品数
				if (!wCrossDocOperator.complete(conn,key,qty[0],qty[1]))
				{
					// スケジュールエラー
					throw new ScheduleException();
				}
			}
			crossdocInfo.clear();
		}	
	}

	// Package methods -----------------------------------------------
	// Protected methods ---------------------------------------------
	/**
	 * 作業情報およびそれに関連する予定情報、在庫情報、実績送信情報を作成・更新する。
	 * <OL>
	 * <LI>作業情報を更新する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 * 	   <LI>作業者コード</LI>
	 * 	   <LI>作業種別 = 仕分け</LI>
	 * 	   <LI>端末No = RFT番号</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>作業No</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>データファイル.実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>データファイル.実績数が作業可能数に満たない場合、不足分をセットする（欠品時のみ）</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0045"</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 *   実際に検品した実績数を、該当レコードの作業実績数に分配します。分配する数量は「作業可能数」です。<BR>
	 *   過剰の場合は、最後のレコードに分配します。<BR>
	 * </DIR>
	 * </LI>
	 * <LI>仕分予定情報の数量を更新する。
	 * （{@link #updateInstockPlan(WorkingInformation,String) updateInstockPlan()}）</LI>
	 * <LI>在庫情報を更新する。
	 * （{@link #updateStockQty(WorkingInformation[],String) updateStockQty()}）</LI>
	 * <LI>実績送信情報を作成する。
	 * （{@link #createResultData(WorkingInformation[],String) createResultData()}）</LI>
	 * </OL>
	 * 
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	resultData		仕分実績情報  作業情報エンティティの配列
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 */
	public void updateWorkingInformation(
		String rftNo,
		String workerCode,
		WorkingInformation[] resultData,
		boolean isShortage)
		throws
			InvalidStatusException,
			NotFoundException,
			InvalidDefineException,
			ReadWriteException,
			UpdateByOtherTerminalException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		for (int i = 0; i < resultData.length; i++)
		{
			// DBを検索する
			skey.KeyClear();
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			// TC->DCの順にデータ取得し、欠品時にTCを優先して完了されるようにする。
			skey.setTcdcFlagOrder(1, false);
			skey.setCollectJobNoOrder(2, true);
			skey.setJobNoOrder(3, true);
			// 集約作業No
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

			if (wi.length <= 0)
			{
				throw new UpdateByOtherTerminalException();
			}

			// データが他で更新されていないかどうかをチェックする
			for (int j = 0; j < wi.length; j++)
			{
				// 状態が作業中以外であればエラー
				if (!wi[j].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
				{
					throw new UpdateByOtherTerminalException();
				}

				// 最終更新処理名が要求スレッド以外であればエラー
				if (!wi[j].getLastUpdatePname().equals(startProcessName[0]))
				{
					throw new UpdateByOtherTerminalException();
				}
			}

			// 入荷実績情報の実績数を保持
			int workQty = resultData[i].getResultQty();

			for (int j = 0; j < wi.length; j++)
			{
				WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
				akey.setJobNo(wi[j].getJobNo());
				akey.setTerminalNo(rftNo);
				akey.setWorkerCode(workerCode);

				if (resultData[i].getStatusFlag().equals(Id5430DataFile.COMPLETION_FLAG_UNWORK))
				{
					// キャンセル処理を行う
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
					akey.updateWorkerCode("");
					akey.updateWorkerName("");
					akey.updateTerminalNo("");
					akey.updateLastUpdatePname(processName);
					// 集約作業Noを元に戻す(作業Noに戻す)
					akey.updateCollectJobNo(wi[j].getJobNo());
					// DBを更新する
					handler.modify(akey);
				}
				else
				{
					int resultQty = workQty;
					// 更新する値をセットする
					// 次データある場合で作業可能数より仕分実績情報の実績数が大きい場合
					if (j < wi.length - 1 && resultQty > wi[j].getPlanEnableQty())
					{
						// 実績数に作業可能数をセット
						resultQty = wi[j].getPlanEnableQty();
					}
					workQty -= resultQty;

					akey.updateResultQty(resultQty);
					// 現在の作業情報エンティティの実績数をセット
					wi[j].setResultQty(resultQty);
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					// 現在の作業情報エンティティの状態フラグをセット
					wi[j].setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					
					akey.updateResultUseByDate(wi[j].getUseByDate());
					wi[j].setResultUseByDate(wi[j].getUseByDate());
					akey.updateResultLocationNo(resultData[i].getLocationNo());
					wi[j].setResultLocationNo(resultData[i].getLocationNo());
					akey.updateLastUpdatePname(processName);

					if (wi[j].getResultQty() < wi[j].getPlanEnableQty())
					{
						// 実績数より作業可能数が大きい場合(欠品の場合)
						// 残数を取得
						int restQty = wi[j].getPlanEnableQty() - wi[j].getResultQty();
						akey.updateShortageCnt(restQty);
						// 現在の作業情報エンティティの欠品数をセット
						wi[j].setShortageCnt(restQty);
					}

					// DBを更新する
					handler.modify(akey);

					// 仕分予定情報の数量を更新する
					updatePlanInformation(wi[j]);
					// 在庫情報の数量を更新する
					updateStockQty(wi[j]);
					// 実績送信情報を作成する
					createResultData(wi[j]);
				}
			}
		}
	}

	/**
	 * 更新した作業情報を元に、仕分予定情報を更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>仕分予定情報.仕分予定一意キー=作業情報.予定一意キー (作業情報に対応して、1件の仕分予定情報があります。)
	 *     <LI>状態フラグ != 削除</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0045"</TD></TR>
	 *    </TABLE>
	 *   仕分実績数、仕分欠品数に、対応した作業情報の実績数、欠品数を加算します。<BR>
	 *   ここでは状態フラグは更新しません。
	 * </DIR>
	 * 
	 * @param workinfo 作業情報
	 * @throws InvalidStatusException	ユニークキーでの検索に複数件該当した場合に通知されます。
	 * @throws ReadWriteException		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException		更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	public void updatePlanInformation(WorkingInformation workinfo)
		throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		try
		{
			// 該当する仕分予定情報を検索する
			SortingPlanSearchKey skey = new SortingPlanSearchKey();
			skey.setSortingPlanUkey(workinfo.getPlanUkey());
			skey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
			SortingPlan plan = new SortingPlan();
			SortingPlanHandler handler = new SortingPlanHandler(conn);
			plan = (SortingPlan) handler.findPrimaryForUpdate(skey);

			// 検索した仕分予定情報を更新する
			SortingPlanAlterKey akey = new SortingPlanAlterKey();
			akey.setSortingPlanUkey(workinfo.getPlanUkey());
			akey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
			akey.updateResultQty(plan.getResultQty() + workinfo.getResultQty());
			akey.updateShortageCnt(plan.getShortageCnt() + workinfo.getShortageCnt());
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
			
			addCrossDocInfo(workinfo);
					
		}
		catch (NoPrimaryException e)
		{
			String errString =
				"[Table:DnSortingPlan" + " SORTING_PLAN_UKEY = " + workinfo.getPlanUkey() + "]";
			// 6026020=ユニークキーでの検索に複数件該当しました。{0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	/**
	 * ハッシュテーブルへキー毎の完了処理、欠品数処理を追加する
	 * <DIR>
	 *    (呼び出し条件)
	 *    <UL>
	 *     <LI>クロスドック管理ありの場合
	 *    </UL>
	 * </DIR>
	 * 
	 * @param planUkey	作業情報の仕分予定一意キーのリスト
	 * @throws ReadWriteException		データベースとの接続で異常が発生した場合に通知されます
	 * @throws InvalidDefineException	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void addCrossDocInfo(WorkingInformation workinfo) throws ReadWriteException
	{
		if(SystemParameter.withCrossDocManagement())
		{
			// クロスドック管理あり
			
			int[] qty = new int[2];
			
			if(crossdocInfo.containsKey(workinfo.getPlanUkey()))
			{
				//　ハッシュテーブル内にキーが存在する
				
				// 値を取り出す
				qty = (int[])crossdocInfo.get(workinfo.getPlanUkey());
				
				//　加算する
				qty[0] += workinfo.getResultQty();
				qty[1] += workinfo.getShortageCnt();
				
				//　値を格納する
				crossdocInfo.put(workinfo.getPlanUkey(),qty);
			}
			else
			{
				// ハッシュテーブル内にキーが存在しない
				qty[0] = workinfo.getResultQty();
				qty[1] = workinfo.getShortageCnt();
				// 新たなキーでハッシュテーブルに登録する
				crossdocInfo.put(workinfo.getPlanUkey(),qty);
			}
		}	
	}	
	
	

	/**
	 * 更新した作業情報の仕分予定一意キーを元に、仕分予定情報の状態フラグを更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>仕分予定一意キー=作業情報.予定一意キー (作業情報に対応して、1件の仕分予定情報があります。)
	 *     <LI>状態フラグ != 削除</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>作業情報の状態による（未開始 or 一部完了 or 完了）</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0045"</TD></TR>
	 *    </TABLE>
	 *   状態フラグを更新します。実績数+欠品数 >= 予定数 を満たす時は「完了」、それ以外は｢一部完了｣。 <BR>
	 * </DIR>
	 * 
	 * @param planUkey	作業情報の仕分予定一意キーのリスト
	 * @throws ReadWriteException		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException		更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateCompletionStatus(String[] planUkey)
		throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		for (int i = 0; i < planUkey.length; i++)
		{
			WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
			skey.setPlanUkey(planUkey[i]);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"!=");

			WorkingInformation[] workinfo;
			WorkingInformationHandler wHandler = new WorkingInformationHandler(conn);
			workinfo = (WorkingInformation[]) wHandler.find(skey);

			SortingPlanHandler sHandler = new SortingPlanHandler(conn);
			SortingPlanAlterKey akey = new SortingPlanAlterKey();
			akey.setSortingPlanUkey(planUkey[i]);
			akey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");

			boolean existCompleteData = false;
			boolean existUnstartData = false;
			boolean existWorkingData = false;
			for (int j = 0; j < workinfo.length; j++)
			{
				String status = workinfo[j].getStatusFlag();
				if (status.equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					existCompleteData = true;
				}
				else if (status.equals(WorkingInformation.STATUS_FLAG_UNSTART))
				{
					existUnstartData = true;
				}
				else
				{
					existWorkingData = true;
					break;
				}
			}
			if (existWorkingData || (!existCompleteData && !existUnstartData))
			{
				// 作業中データがある場合、または完了、未開始データが共にない場合は
				// 状態フラグは更新しない(作業中のまま)
				continue;
			}
			if (existCompleteData)
			{
				if (existUnstartData)
				{
					// 完了データ、未開始データの両方が存在する場合は一部完了にする
					akey.updateStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART);
				}
				else
				{
					// 完了データのみが存在する場合は完了にする
					akey.updateStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETION);
				}
			}
			else
			{
				// 未開始データのみが存在する場合は未開始にする
				akey.updateStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);
			}
			akey.updateLastUpdatePname(processName);
			sHandler.modify(akey);
		}
	}

	/**
	 * 更新した作業情報を元に、在庫情報の在庫数、引当数を更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>在庫情報.在庫ID=作業情報.在庫ID
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>センター在庫</TD></TR>
	 *      <TR><TD>在庫数</TD>			<TD>(-) 実績送信電文.実績数</TD></TR>
	 *      <TR><TD>引当数</TD>			<TD>(-) (実績送信電文.実績数 + 実績送信電文.欠品数)</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0018" or "ID0015" or "ID0511"</TD></TR>
	 *    </TABLE>
	 *   入荷実績数、入荷欠品数に、対応した作業情報の実績数、欠品数を加算します。<BR>
	 * </DIR>
	 * 
	 * @param workinfo 作業情報
	 * @throws InvalidStatusException	ユニークキーでの検索に複数件該当した場合に通知されます。
	 * @throws ReadWriteException		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException		更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateStockQty(WorkingInformation workinfo)
		throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		try
		{
			// 在庫数、引当数から作業数を減算する
			StockSearchKey skey = new StockSearchKey();
			skey.setStockId(workinfo.getStockId());
			Stock stock = new Stock();
			StockHandler handler = new StockHandler(conn);
			stock = (Stock) handler.findPrimaryForUpdate(skey);
			StockAlterKey akey = new StockAlterKey();
			akey.setStockId(workinfo.getStockId());
			// 在庫数から実績数を減算 (仕分けと出荷は、減算の値に欠品数を含めない）
			akey.updateStockQty(stock.getStockQty() - workinfo.getResultQty());
			// 引当数から実績数と欠品数を減算
			akey.updateAllocationQty(
				stock.getAllocationQty() - workinfo.getResultQty() - workinfo.getShortageCnt());
			if ((stock.getAllocationQty() - workinfo.getResultQty() - workinfo.getShortageCnt())
				== 0)
			{
				// 在庫ステータス(9:完了)更新
				akey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
			}
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DmStock" + " STOCK_ID = " + workinfo.getStockId() + "]";
			// 6026020=ユニークキーでの検索に複数件該当しました。{0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			// 適切なExceptionに変更する
			throw new InvalidStatusException();
		}
	}

	/**
	 * 更新対象データをロックし、そのデータの仕分予定一意キーのリストを返す。
	 * 更新の対象となるテーブルは作業情報、仕分予定情報、
	 * 在庫情報（キャンセルでない場合のみ）である。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>作業区分 = 仕分け</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	workinfo		作業情報の配列
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	isCancel		確定処理がキャンセルかどうかのフラグ
	 * 							（キャンセル時には在庫をロックしない）
	 * @return					条件に該当する仕分予定一意キーの配列
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException		データベースエラー発生
	 * @throws LockTimeOutException	ロックタイムアウト発生
	 */
	protected String[] lockUpdateData(
		WorkingInformation[] workinfo,
		String rftNo,
		String workerCode,
		boolean isCancel)
		throws UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		String[] collectJobNoList = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			collectJobNoList[i] = workinfo[i].getCollectJobNo();
		}

		skey.setCollectJobNo(collectJobNoList);
		skey.setJobType(jobType);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION,"!=");
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		// WorkingInformationをロックする
		WorkingInformation[] wi = null;
		try
		{
		    wi = (WorkingInformation[]) handler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			// 6026018=一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}
		
		// 他端末で更新されていないかどうかをチェックする
		if (wi.length <= 0)
		{
			throw new UpdateByOtherTerminalException();
		}

		for (int i = 0; i < wi.length; i++)
		{
			// 自端末が作業中にしたデータでない場合
			if (! rftNo.equals(wi[i].getTerminalNo()))
			{
				throw new UpdateByOtherTerminalException();
			}

			// 作業中にした作業者が異なる場合
            if (isMaintenanceCancel = false)
            {
                if (! workerCode.equals(wi[i].getWorkerCode()))
			    {
				    throw new UpdateByOtherTerminalException();
			    }
            }

			//　状態が作業中でない場合
			if (! wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				throw new UpdateByOtherTerminalException();
			}

			// 最終更新処理名が対応するデータ要求処理でない場合
			if (! startProcessName[0].equals(wi[i].getLastUpdatePname()))
			{
				throw new UpdateByOtherTerminalException();
			}
		}

		// 仕分予定一意キーのリストを生成する
		String[] planUkeyList = getPlanUkeyList(collectJobNoList, rftNo, workerCode);

		try
		{
			// 仕分予定情報をロックする
			lockPlanInformation(planUkeyList);
		}
		catch (LockTimeOutException e)
		{
			// 6026018=一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNSORTINGPLAN");
			throw e;
		}

		// 更新対象の在庫情報をロックする
		if (! isCancel)
		{
			// 在庫管理ありかつキャンセル処理でない場合のみ在庫をロックする処理を追加する
			lockStockData(wi);
		}

		return planUkeyList;
	}

    /**
     * 引数で指定された仕分予定一意キーを持つ仕分予定情報のうち、状態が削除以外のものをロックする。
     * 
     * @param	planUKeyList[]	仕分予定一意キーの配列
	 * @throws ReadWriteException		データベースエラー発生
	 * @throws LockTimeOutException	ロックタイムアウト発生
     */
	protected void lockPlanInformation(String[] planUKeyList) throws ReadWriteException, LockTimeOutException
	{
		// 仕分予定情報をロックする
		SortingPlanHandler rHandler = new SortingPlanHandler(conn);
		SortingPlanSearchKey rskey = new SortingPlanSearchKey();

		rskey.setSortingPlanUkey(planUKeyList);
		rskey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		rHandler.findForUpdate(rskey, WmsParam.WMS_DB_LOCK_TIMEOUT);
	}

	// Private methods -----------------------------------------------
}
//end of class