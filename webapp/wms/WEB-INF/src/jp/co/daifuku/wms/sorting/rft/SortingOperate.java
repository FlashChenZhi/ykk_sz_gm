// $Id: SortingOperate.java,v 1.1.1.1 2006/08/17 09:34:34 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.crossdoc.CrossDocOperator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * RFT仕分け処理から呼び出される共通関数が定義されます。<BR>
 * <BR>
 * ！注意！<BR>
 * このクラス内で使用するWorkingInformationクラスとWorkingInformationHandlerクラスは、
 * ＲＦＴ用パッケージ(jp.co.daifuku.wms.base.rftパッケージ)を使用します。<BR>
 * (親クラスのインスタンスを子クラスにキャストできない為、ＲＦＴ用WorkingInformationHandlerを使用する必要があります。)<BR>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingOperate
{
	// Class fields----------------------------------------------------
	/**
	 * クラス名を表すフィールド
	 */
	private static final String CLASS_NAME = "SortingOperate";
	
	/**
	 * クロスドックで渡す値をセットするハッシュテーブル
	 */
	protected Hashtable crossdocTable = new Hashtable();

	// Class variables -----------------------------------------------
	/**
	 * 処理名（登録処理名、最終更新処理名用）
	 */
	protected String processName = "";

	/**
	 * 作業開始処理名（登録処理名、最終更新処理名用）
	 */
	protected String startProcessName = "";

	/**
	 * データベースとの接続
	 */
	protected Connection wConn = null;

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
	public SortingOperate()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>をセットします。<BR>
	 * 
	 * @param c データベース接続用 Connection
	 */
	public void setConnection(Connection c)
	{
		wConn = c;
	}

	/**
	 * 更新処理名をセットする
	 * 
	 * @param name		更新処理名
	 */
	public void setProcessName(String name)
	{
		processName = name;
	}

	/**
	 * 作業開始処理名をセットする
	 * 
	 * @param name		作業開始処理名
	 */
	public void setStartProcessName(String name)
	{
		startProcessName = name;
	}

	/**
	 * 仕分実績送信 [確定処理]<BR>
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
	 * 	   <LI>最終更新処理名='ID0430'</LI>
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
	 * @param	workTime		作業時間
	 * @param	missScanCnt		ミス回数
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	*/
	public void sortingComplete(
		WorkingInformation[] resultData,
		String rftNo,
		String workerCode,
		int workTime,
		int missScanCnt)
		throws
			InvalidStatusException,
			NotFoundException,
			InvalidDefineException,
			UpdateByOtherTerminalException,
			ReadWriteException,
			LockTimeOutException,
			ScheduleException
	{
		String[] planUkeyList = lockUpdateData(resultData, rftNo, workerCode, false);

		updateWorkingInformation(rftNo, workerCode, resultData);

		updateCompletionStatus(planUkeyList);

		createWorkerResult(workerCode, rftNo, workTime, missScanCnt, resultData);
		
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
			
			for (Enumeration e = crossdocTable.keys() ;e.hasMoreElements();)
			{ 
				
				// クロスドック処理	
				CrossDocOperator wCrossDocOperator = new CrossDocOperator();
				
				int[] qty = new int[2];
				
				
				String key = e.nextElement().toString();
				qty = (int[])crossdocTable.get(key);
				// 予定一意キー,完了数,欠品数
				if (!wCrossDocOperator.complete(wConn,key,qty[0],qty[1]))
				{
					// スケジュールエラー
					throw new ScheduleException();
				}
			}
			crossdocTable.clear();
		}	
	}

	/**
	 * 仕分実績送信 [キャンセル処理]<BR>
	 * 作業情報、入庫予定情報の該当データをロックします。(排他処理用)<BR>
	 * 作業情報から該当するデータを検索し、状態フラグを未開始に、端末NoRFTNoと作業者コードを空白に更新します。<BR>
	 * 仕分予定情報の更新。該当するデータを検索し、状態フラグを更新(作業情報から判定)します。<BR>
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
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>集約作業No</TD>		<TD>作業No</TD></TR>
	 *      <TR><TD>状態フラグ</TD>		<TD>0:未開始</TD></TR>
	 *      <TR><TD>作業者コード</TD>	<TD>空白</TD></TR>
	 *      <TR><TD>作業者名</TD>		<TD>空白</TD></TR>
	 *      <TR><TD>端末No</TD>			<TD>空白</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0045"</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 * </DIR>
	 * <BR>
	 * 仕分予定情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>仕分予定一意キー=作業情報より取得</LI>
	 *     <LI>状態フラグ!=9:削除</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>0:未作業 2:作業中 3:一部完了</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0045"</TD></TR>
	 *    </TABLE>
	 *   状態フラグを更新します。<BR>
	 *   更新対象の仕分予定一意キーに該当する作業情報を検索します。<BR>
	 *   <UL>
	 *   <LI>該当作業情報が全て未開始であれば「0:未開始」に更新します。</LI>
	 *   <LI>該当作業情報の一部が作業中であれば「2:作業中」に更新します。</LI>
	 *   <LI>該当作業情報が「未開始」「完了」混在であれば「3:一部完了」に更新します。</LI>
	 *   </UL>
	 * </DIR>
	 * 
	 * @param	resultData		仕分実績情報  作業情報エンティティの配列
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	*/
	public void sortingCancel(WorkingInformation[] resultData, String rftNo, String workerCode)
		throws
			NotFoundException,
			InvalidDefineException,
			UpdateByOtherTerminalException,
			ReadWriteException,
			LockTimeOutException
	{
		// 更新対象データをロックする
		String[] planUkeyList = lockUpdateData(resultData, rftNo, workerCode, true);

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationAlterKey akey = new WorkingInformationAlterKey();

		for (int i = 0; i < resultData.length; i++)
		{
			skey.KeyClear();
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			skey.setLastUpdatePname(startProcessName);

			WorkingInformationHandler handler = new WorkingInformationHandler(wConn);
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

			for (int j = 0; j < wi.length; j++)
			{
				akey.KeyClear();
				akey.setJobNo(wi[j].getJobNo());

				// 集約作業Noを元に戻す(作業Noに戻す)
				akey.updateCollectJobNo(wi[j].getJobNo());
				akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				akey.updateWorkerCode("");
				akey.updateWorkerName("");
				akey.updateTerminalNo("");
				akey.updateLastUpdatePname(processName);
				handler.modify(akey);
			}
		}

		// 作業情報の状態を基に仕分予定情報の状態を元に戻す
		updateCompletionStatus(planUkeyList);
	}

	/**
	 * <P>
	 * パラメータとして受け取った作業情報エンティティ配列に対し、
	 * 集約可能なデータを全て集約し、新しい作業情報エンティティを返します。<BR>
	 * 集約する過程において、集約作業Noを振りなおし、DBの更新も行ないます。<BR>
	 * この時、作業状態等も同時に更新します。<BR>
	 * さらにその結果を仕分予定情報に反映させます。
	 * </P>
	 * 
	 * 集約を行うかどうかはパラメタファイル(WMSParam.properties)から取得します。<BR>
	 * <DIR>
	 * 集約を行なう場合は、集約作業Noを振り直します。
	 * (集約する中で商品コードが最も小さいデータの作業Noを使用します。)<BR>
	 * 振り直した結果で、DBを更新します。<BR>
	 * (更新内容)
	 * <TABLE border="1">
	 *   <TR><TD>集約作業No</TD>		<TD>集約する中で商品コードが最も小さいデータの作業No</TD></TR>
	 *   <TR><TD>状態フラグ</TD>		<TD>作業中</TD></TR>
	 *   <TR><TD>作業者コード</TD>		<TD>作業者コード</TD></TR>
	 *   <TR><TD>作業者名称</TD>		<TD>作業者マスタ.作業者名称</TD></TR>
	 *   <TR><TD>端末No</TD>			<TD>RFT No</TD></TR>
	 *   <TR><TD>最終更新処理名</TD>	<TD>"ID0044"</TD></TR>
	 * </TABLE>
	 * </DIR>
	 * @param  sortingData	作業情報エンティティの配列(ロケーション順にソートされている事)
	 * 						この配列は空であってはなりません。
	 * @param	workerCode	作業者コード
	 * @param	rftNo		端末No
	 * @return			集約後の作業情報エンティティ配列
	 * @throws NotFoundException 		作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws OverflowException		数値項目の桁数が超過した時に通知されます。
	 */
	public WorkingInformation[] collectSortingData(
			WorkingInformation[] sortingData,
			String workerCode,
			String rftNo) 
		throws NotFoundException, InvalidDefineException, ReadWriteException, OverflowException
	{
        
		// 作業者名称取得
		BaseOperate bo = new BaseOperate(wConn);
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e)
		{
			// 作業者コードなし 開始～データ要求までの間に作業者マスターを使用停止にされた場合
			// 6026019=作業者マスタテーブルに該当作業者が見つかりません。作業者コード:{0}
			RftLogMessage.print(6026019, LogMessage.F_ERROR, CLASS_NAME, workerCode);
			NotFoundException ex = new NotFoundException(RFTId5430.ANS_CODE_ERROR);
			throw ex;
		}

		// 作業集約処理
		Vector v = new Vector();
	    
		int jobType = SystemParameter.getJobType(sortingData[0].getJobType());

		// 集約作業Noを付け直しする場合（集約する場合）
		WorkingInformation currentData = (WorkingInformation) sortingData[0].clone();
		sortingData[0].setWorkerCode(workerCode);
		sortingData[0].setWorkerName(workerName);
		sortingData[0].setTerminalNo(rftNo);
		for (int i = 1; i < sortingData.length; i++)
		{
			if (SystemParameter.isRftWorkCollect(jobType))
			{
				if (currentData.hasSameKeyForSort(sortingData[i]))
				{
					try
					{
						// 作業情報を集約する
						currentData.collect(sortingData[i]);
					}
					catch (OverflowException e)
					{
						// 6026028=集約処理でオーバーフローが発生しました。テーブル名:{0}
						RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DNWORKINFO");
						throw e;
					}
				    
					// 集約する場合は集約作業Noを更新する
					sortingData[i].setCollectJobNo(currentData.getCollectJobNo());
				}
				else
				{
					// キーが変わったらVectorに登録する
					v.addElement(currentData);

					// 集約結果保持用に作業情報をコピーする
					currentData = (WorkingInformation) sortingData[i].clone();
				}
			}

			sortingData[i].setWorkerCode(workerCode);
			sortingData[i].setWorkerName(workerName);
			sortingData[i].setTerminalNo(rftNo);
		}
		
		// 最後のデータをVectorに登録する
		v.addElement(currentData);

		// 状態を更新する
		for (int i = 0; i < sortingData.length; i ++)
		{
			// 作業情報を作業中に更新する
			updateWorkInfoStatusToNowWorking(sortingData[i]);
			
			// 仕分予定情報を作業中に更新する
			updateSortingPlanStatusToNowWorking(sortingData[i]);
		}
		
		if (SystemParameter.isRftWorkCollect(jobType))
		{
			// 集約する場合は集約されたデータを返す
			WorkingInformation[] collectedData = new WorkingInformation[v.size()];
			v.copyInto(collectedData);

			return collectedData;
		}
		else
		{
			// 集約しない場合は元のデータをそのまま返す
			return sortingData;
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
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0431"</TD></TR>
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
	protected void updateWorkingInformation(
		String rftNo,
		String workerCode,
		WorkingInformation[] resultData)
		throws
			InvalidStatusException,
			NotFoundException,
			InvalidDefineException,
			ReadWriteException,
			UpdateByOtherTerminalException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);

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
				if (!wi[j].getLastUpdatePname().equals(startProcessName))
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
				updateSortingPlan(wi[j]);
				// 在庫情報の数量を更新する
				updateStockQty(wi[j]);
				// 実績送信情報を作成する
				createResultData(wi[j]);
			}
		}
	}

	/**
	 * 実績送信情報の作成<BR>
	 * <DIR>
	 *   作業日をWareNavi_Systemから取得し、作業日にセットします。<BR>
	 *   登録処理名セットします。<BR>
	 *   その他の項目は、作業情報から取得し、セットします。<BR>
	 * </DIR>
	 * 
	 * @param workinfo	作業情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createResultData(WorkingInformation workinfo) throws ReadWriteException
	{
		HostSend result;
		BaseOperate baseOperate = new BaseOperate(wConn);
		try
		{
			result = new HostSend(workinfo, baseOperate.getWorkingDate(), processName);
			HostSendHandler handler = new HostSendHandler(wConn);
			handler.create(result);
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw new ReadWriteException();
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
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0431"</TD></TR>
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
	protected void updateSortingPlan(WorkingInformation workinfo)
		throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		try
		{
			// 該当する仕分予定情報を検索する
			SortingPlanSearchKey skey = new SortingPlanSearchKey();
			skey.setSortingPlanUkey(workinfo.getPlanUkey());
			skey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
			SortingPlan plan = new SortingPlan();
			SortingPlanHandler handler = new SortingPlanHandler(wConn);
			plan = (SortingPlan) handler.findPrimaryForUpdate(skey);

			// 検索した仕分予定情報を更新する
			SortingPlanAlterKey akey = new SortingPlanAlterKey();
			akey.setSortingPlanUkey(workinfo.getPlanUkey());
			akey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
			akey.updateResultQty(plan.getResultQty() + workinfo.getResultQty());
			akey.updateShortageCnt(plan.getShortageCnt() + workinfo.getShortageCnt());
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
			
			addCrossDocTable(workinfo);
					
		}
		catch (NoPrimaryException e)
		{
			String errString =
				"[Table:DnSortingPlan" + " SORTING_PLAN_UKEY = " + workinfo.getPlanUkey() + "]";
			// 6026020=ユニークキーでの検索に複数件該当しました。{0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			// 適切なExceptionに変更する
			throw new InvalidStatusException();
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
	protected void addCrossDocTable(WorkingInformation workinfo) throws ReadWriteException
	{
		if(SystemParameter.withCrossDocManagement())
		{
			// クロスドック管理あり
			
			int[] qty = new int[2];
			
			if(crossdocTable.containsKey(workinfo.getPlanUkey()))
			{
				//　ハッシュテーブル内にキーが存在する
				
				// 値を取り出す
				qty = (int[])crossdocTable.get(workinfo.getPlanUkey());
				
				//　加算する
				qty[0] += workinfo.getResultQty();
				qty[1] += workinfo.getShortageCnt();
				
				//　値を格納する
				crossdocTable.put(workinfo.getPlanUkey(),qty);
			}
			else
			{
				// ハッシュテーブル内にキーが存在しない
				qty[0] = workinfo.getResultQty();
				qty[1] = workinfo.getShortageCnt();
				// 新たなキーでハッシュテーブルに登録する
				crossdocTable.put(workinfo.getPlanUkey(),qty);
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
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0431"</TD></TR>
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
			WorkingInformationHandler wHandler = new WorkingInformationHandler(wConn);
			workinfo = (WorkingInformation[]) wHandler.find(skey);

			SortingPlanHandler sHandler = new SortingPlanHandler(wConn);
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
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0141" or "ID0131" or "ID0133"</TD></TR>
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
			StockHandler handler = new StockHandler(wConn);
			stock = (Stock) handler.findPrimaryForUpdate(skey);
			StockAlterKey akey = new StockAlterKey();
			akey.setStockId(workinfo.getStockId());
			// 在庫数から実績数を減算 (仕分けと出荷は、減算の値に欠品数を含めない）
			akey.updateStockQty(stock.getStockQty() - workinfo.getResultQty() );
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
	 * 更新対象データの仕分予定一意キーのリストを取得する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>作業者コード</LI>
	 *     <LI>RFTNo</LI>
	 *     <LI>作業区分 = 仕分け</LI>
	 *     <LI>状態フラグ = 作業中</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	collectJobNoList	集約作業Noのリスト
	 * @param	rftNo				RFT番号
	 * @param	workerCode			作業者コード
	 * @return					条件に該当する仕分予定一意キーの配列
	 * @throws ReadWriteException		データベースエラー発生
	 */
	protected String[] getPlanUkeyList(String[] collectJobNoList, String rftNo, String workerCode)
		throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setCollectJobNo(collectJobNoList);
		skey.setTerminalNo(rftNo);
		skey.setWorkerCode(workerCode);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		skey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		skey.setPlanUkeyCollect("DISTINCT");
		skey.setPlanUkeyOrder(1, true);
		WorkingInformationHandler wHandler = new WorkingInformationHandler(wConn);
		WorkingInformation[] workinfo = (WorkingInformation[]) wHandler.find(skey);

		String[] planUkey = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			planUkey[i] = workinfo[i].getPlanUkey();
		}
		return planUkey;
	}

	/**
	 * 作業者実績を作成する。
	 * 
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	workerTime			作業時間
	 * @param	missScanCount			ミス回数
	 * @param	workinfo		仕分実績情報  作業情報エンティティの配列
	 * @throws ReadWriteException		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException		更新対象データが存在しない場合に通知されます。
	 */
	protected void createWorkerResult(
		String workerCode,
		String rftNo,
		int workTime,
		int missScanCnt,
		WorkingInformation[] workinfo)
		throws ReadWriteException, NotFoundException
	{
		int workQty = 0;
		int workCnt = 0;
		int realWorkTime = 0;
		for (int i = 0; i < workinfo.length; i++)
		{
			workQty += workinfo[i].getResultQty();		
			realWorkTime += workinfo[i].getWorkTime();
			
			workCnt++;
		}
		
		
		

		WorkerResult wr = new WorkerResult();
		BaseOperate bo = new BaseOperate(wConn);

		wr.setWorkDate(bo.getWorkingDate());
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_SORTING);
		wr.setWorkCnt(workCnt);
		wr.setOrderCnt(1);
		wr.setWorkQty(workQty);
		wr.setMissScanCnt(missScanCnt);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(realWorkTime);

		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			String errData = "[RftNo:" + workerCode
							+ " WorkerCode:" + rftNo
							+ " JobType:" + WorkerResult.JOB_TYPE_SORTING + "]";
			// 6026016=更新対象データが見つかりません。{0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				bo.createWorkerResult(wr.getJobType(), wr.getWorkerCode(), wr.getTerminalNo());
				bo.alterWorkerResult(wr);
			}
			catch (NotFoundException e1)
			{
				//6006002 = データベースエラーが発生しました。{0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
				throw new ReadWriteException("6006002");
			}
			catch (DataExistsException e1)
			{
				//6006002 = データベースエラーが発生しました。{0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
				throw new ReadWriteException("6006002");
			}
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
		throws NotFoundException, UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);

		String[] collectJobNoList = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			collectJobNoList[i] = workinfo[i].getCollectJobNo();
		}

		skey.setCollectJobNo(collectJobNoList);
		skey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		// WorkingInformationをロックする
		int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;
		WorkingInformation[] wi = null;
		try
		{
			wi = (WorkingInformation[]) handler.findForUpdate(skey, timeout);
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
			if (!rftNo.equals(wi[i].getTerminalNo()))
			{
				throw new UpdateByOtherTerminalException();
			}

			// 作業中にした作業者が異なる場合
			if (!workerCode.equals(wi[i].getWorkerCode()))
			{
				throw new UpdateByOtherTerminalException();
			}

			//　状態が作業中でない場合
			if (!wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				throw new UpdateByOtherTerminalException();
			}

			// 最終更新処理名が対応するデータ要求処理でない場合
			if (!startProcessName.equals(wi[i].getLastUpdatePname()))
			{
				throw new UpdateByOtherTerminalException();
			}
		}

		// 仕分予定一意キーのリストを生成する
		String[] planUkeyList = getPlanUkeyList(collectJobNoList, rftNo, workerCode);

		// 仕分予定情報をロックする
		SortingPlanHandler rHandler = new SortingPlanHandler(wConn);
		SortingPlanSearchKey rskey = new SortingPlanSearchKey();

		rskey.setSortingPlanUkey(planUkeyList);
		rskey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		try
		{
			rHandler.findForUpdate(rskey, timeout);
		}
		catch (LockTimeOutException e)
		{
			// 6026018=一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNSORTINGPLAN");
			throw e;
		}

		// 更新対象の在庫情報をロックする
		if (!isCancel)
		{
			// 在庫管理ありかつキャンセル処理でない場合のみ在庫をロックする処理を追加する
			lockStockData(wi);
		}

		return planUkeyList;
	}

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
	 * @param	workinfo		作業情報の配列
	 * @throws ReadWriteException		データベースエラー発生
	 * @throws LockTimeOutException	ロックタイムアウト発生
	 */
	protected void lockStockData(WorkingInformation[] workinfo)
		throws ReadWriteException, LockTimeOutException
	{
		String[] stockIdList = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			stockIdList[i] = workinfo[i].getStockId();
		}
		StockSearchKey skey = new StockSearchKey();
		skey.setStockId(stockIdList);

		StockHandler shandler = new StockHandler(wConn);
		int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;
		shandler.findForUpdate(skey, timeout);
	}

	/**
	 * 作業情報を作業中に更新する。
	 * RFTに送信するデータが集約されている場合、集約作業Noも更新する。
	 * 
	 * @param workinfo 作業情報
	 * @throws ReadWriteException		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException		更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateWorkInfoStatusToNowWorking(WorkingInformation workinfo) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		// 作業情報の更新 (集約作業Noを変更したレコードを更新)
		WorkingInformationAlterKey workingAlterKey = new WorkingInformationAlterKey();

		// 作業情報を作業中に更新する
		// 更新条件セット
		workingAlterKey.setJobNo(workinfo.getJobNo());
		// 更新内容セット
		workingAlterKey.updateCollectJobNo(workinfo.getCollectJobNo());
		workingAlterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		workingAlterKey.updateTerminalNo(workinfo.getTerminalNo());
		workingAlterKey.updateWorkerCode(workinfo.getWorkerCode());
		workingAlterKey.updateWorkerName(workinfo.getWorkerName());
		workingAlterKey.updateLastUpdatePname(processName);

		WorkingInformationHandler workingHandler = new WorkingInformationHandler(wConn);
		workingHandler.modify(workingAlterKey);
	}
	
	/**
	 * 仕分予定情報を作業中に更新する。
	 * 
	 * @param workinfo 作業情報
	 * @throws ReadWriteException		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException		更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateSortingPlanStatusToNowWorking(WorkingInformation workinfo) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		// 仕分予定情報を作業中に更新する
		SortingPlanAlterKey sortingAlterKey = new SortingPlanAlterKey();
		// 更新条件セット
		sortingAlterKey.setSortingPlanUkey(workinfo.getPlanUkey());
		// 更新内容セット
		sortingAlterKey.updateStatusFlag(SortingPlan.STATUS_FLAG_NOWWORKING);
		sortingAlterKey.updateLastUpdatePname(processName);

		SortingPlanHandler sortingHandler = new SortingPlanHandler(wConn);
		sortingHandler.modify(sortingAlterKey);
	}

	
	
	// Private methods -----------------------------------------------
}
//end of class