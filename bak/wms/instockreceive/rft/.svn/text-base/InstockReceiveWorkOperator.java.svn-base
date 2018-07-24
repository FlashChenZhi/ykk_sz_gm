// $Id: InstockReceiveWorkOperator.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ScheduleException;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WorkOperator;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.crossdoc.CrossDocOperator;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.coordinated.instockstorage.InstockStoragePlanCreator;

/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * 入荷作業の共通処理が定義されます。<BR>
 * <BR>
 * ！注意！<BR>
 * このクラス内で使用するWorkingInformationクラスとWorkingInformationHandlerクラスは、
 * ＲＦＴ用パッケージ(jp.co.daifuku.wms.base.rftパッケージ)を使用します。<BR>
 * (親クラスのインスタンスを子クラスにキャストできない為、ＲＦＴ用WorkingInformationHandlerを使用する必要があります。)<BR>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class InstockReceiveWorkOperator
		extends WorkOperator
{
	// Class fields --------------------------------------------------
    /**
	 * クラス名を表すフィールド
	 */
    private static final String CLASS_NAME = "InstockReceiveWorkOperator";

	// Class variables -----------------------------------------------   
    /**
     * 情報を保持しておく為のHashtable
     */
	protected Hashtable crossdocInfo = new Hashtable();

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $";
	}

	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
    public InstockReceiveWorkOperator()
    {
    	super();
    }
    
	// Public methods ------------------------------------------------
    /**
     * 作業開始処理名をセットする
     * 
     * @param name		作業開始処理名
     */
    public void setStartProcessName(String[] name)
    {
        startProcessName = name;
    }

    /**
	 * 入荷検品確定処理<BR>
	 * 入荷実績情報について、以下の確定処理を実行します。<BR>
	 * (Web画面の処理から呼び出される)<BR>
	 * <OL>
	 *  <LI>更新対象データをロックする。
	 * （{@link #lockUpdateData(Connection,WorkingInformation[],String,String,boolean,String) lockUpdateData()}）</LI>
	 *  <LI>作業情報を完了状態に更新し、
	 * それに関連する予定情報、在庫情報、実績送信情報を作成・更新する。
	 * （{@link #updateWorkingInformation(Connection,String,String,WorkingInformation[]) updateWorkingInformation()}）
	 * また入庫予定作成処理(InstockStoragePlanCreator)の呼出しを行う。 </LI>
	 *  <LI>入荷予定情報を完了状態に更新する。
	 * （{@link #updateCompletionStatus(Connection,String[],String) updateCompletionStatus()}）</LI>
	 *  <LI>作業者実績情報を更新する。
	 * （{@link #updateWorkerResult(Connection,String,String,WorkingInformation[]) updateWorkerResult()}）</LI>
	 *  <LI>在庫移行処理を実行する。（未実装）</LI>
	 * </OL>
	 * 
	 * @param	resultData	入荷実績情報  作業情報エンティティの配列
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	isShortage		数量不足のときに欠品にするかどうかのフラグ
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws DataExistsException 既に同じ情報が登録済みの場合に通知されます。
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
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
			DataExistsException, 
			UpdateByOtherTerminalException, 
			ReadWriteException, 
			LockTimeOutException, 
			ScheduleException
	{		
		String[] planUkeyList = lockUpdateData(
			resultData,
			rftNo,
			workerCode,
			false);

		updateWorkingInformation(
			rftNo,
			workerCode,
			resultData,
			isShortage);

		updateCompletionStatus(planUkeyList);
			
		updateWorkerResult(workerCode, rftNo, resultData, ! isShortage);
		
		// クロスドック処理を行います
		crossDocProcess();
	}

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
	 * 	   <LI>作業種別 = 入荷</LI>
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
	 *      <TR><TD>保留数</TD>			<TD>データファイル.実績数が作業可能数に満たない場合、不足分をセットする（分納時のみ）</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>データファイル.賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 *   実際に検品した実績数を、該当レコードの作業実績数に分配します。分配する数量は「作業可能数」です。<BR>
	 *   過剰の場合は、最後のレコードに分配します。<BR>
	 *   また、分納の場合は納品分を完了とし、未納分の作業情報を新たに作成します。
	 *   作成するレコードの内容は以下のとおりで、これ以外のデータは元作業情報と同じとする。
	 *    (作成内容)
	 *    <TABLE>
	 *      <TR><TD>作業No</TD>			<TD>採番</TD></TR>
	 *      <TR><TD>集約作業No</TD>		<TD>作業No</TD></TR>
	 *      <TR><TD>状態フラグ</TD>		<TD>未作業</TD></TR>
	 *      <TR><TD>予定数</TD>			<TD>元作業可能数 - データファイル.実績数</TD></TR>
	 *      <TR><TD>作業可能数</TD>		<TD>元作業可能数 - データファイル.実績数</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>0</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>0</TD></TR>
	 *      <TR><TD>保留数</TD>			<TD>0</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>空白</TD></TR>
	 *      <TR><TD>登録処理名</TD>		<TD>呼び出し元の処理名</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * </LI>
	 * <LI>出荷予定情報の数量を更新する。
	 * （{@link #updateInstockPlan(WorkingInformation,String) updateInstockPlan()}）</LI>
	 * <LI>在庫情報を更新する。
	 * （{@link #updateStockQty(WorkingInformation[],String) updateStockQty()}）</LI>
	 * <LI>実績送信情報を作成する。
	 * （{@link #createResultData(WorkingInformation[],String) createResultData()}）</LI>
	 * </OL>
	 * 
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	resultData	入荷実績情報  作業情報エンティティの配列
	 * @param	isShortage		数量不足のときに欠品にするかどうかのフラグ
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws DataExistsException 既に同じ情報が登録済みの場合に通知されます。
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
			DataExistsException, 
			UpdateByOtherTerminalException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		WorkingInformationHandler handler =
			new WorkingInformationHandler(conn);

		for (int i = 0; i < resultData.length; i++)
		{
			// DBを検索する
			skey.KeyClear();
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			// TC->DCの順にデータ取得し、超過時にDCに加算されるようにする。
			skey.setTcdcFlagOrder(1,false);
			skey.setCollectJobNoOrder(2, true);
			skey.setJobNoOrder(3, true);
			// 集約作業No
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

			if (wi.length <= 0)
			{
				throw new NotFoundException();
			}
			
			// データが他で更新されていないかどうかをチェックする
			for (int j = 0; j < wi.length; j ++)
			{
			    // 状態が作業中以外であればエラー
				if (! wi[j].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
				{
					throw new UpdateByOtherTerminalException();
				}

				// 最終更新処理名が対応するデータ要求処理でない場合
				Vector tempVec = new Vector();
				for (int k = 0; k < startProcessName.length; k++)
				{
					tempVec.addElement(startProcessName[k]);
				}
				if (!tempVec.contains(wi[j].getLastUpdatePname()))
				{
					throw new UpdateByOtherTerminalException();
				}
			}

			// 入荷実績情報の実績数を保持
			int workQty = resultData[i].getResultQty();

			for (int j = 0; j < wi.length; j ++)
			{
				WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
				akey.setJobNo(wi[j].getJobNo());
				akey.setTerminalNo(rftNo);
				akey.setWorkerCode(workerCode);
				
				if( workQty > 0 || isShortage )
				{
					// 分配する数がある時又は欠品完了の場合

					// 分納した場合の新規データ用作業情報エンティティ
					WorkingInformation newWorkinfo = null;

					int resultQty = workQty;
					// 更新する値をセットする
					// 次データある場合で作業可能数より入荷実績情報の実績数が大きい場合
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
					akey.updateLastUpdatePname(processName);

					if (wi[j].getResultQty() < wi[j].getPlanEnableQty())
					{
						// 実績数より作業可能数が大きい場合(欠品or分納の場合)
						// 残数を取得
						int restQty = wi[j].getPlanEnableQty() - wi[j].getResultQty();
						if (isShortage)
						{
							// 欠品の場合
							akey.updateShortageCnt(restQty);
							// 現在の作業情報エンティティの欠品数をセット
							wi[j].setShortageCnt(restQty);
						}
						else
						{
							// 分納の場合
							akey.updatePendingQty(restQty);
							// 現在の作業情報エンティティの保留数をセット
							wi[j].setPendingQty(restQty);

							// 分割する新しい方のレコードの値をセットする
							newWorkinfo = (WorkingInformation) wi[j].clone();
							newWorkinfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
							newWorkinfo.setPlanQty(restQty);
							newWorkinfo.setPlanEnableQty(restQty);
							newWorkinfo.setResultQty(0);
							newWorkinfo.setPendingQty(0);
							newWorkinfo.setTerminalNo("");
							newWorkinfo.setWorkerCode("");
							newWorkinfo.setWorkerName("");
							newWorkinfo.setRegistPname(processName);
							newWorkinfo.setLastUpdatePname(processName);
							// 新しい作業Noを割り当てる
							SequenceHandler sh = new SequenceHandler(conn);
							newWorkinfo.setJobNo(sh.nextJobNo());
							// 集約作業Noに作業Noをセットする
							newWorkinfo.setCollectJobNo(newWorkinfo.getJobNo());
						}
					}
	
					akey.updateResultUseByDate(resultData[i].getResultUseByDate());
					// 現在の作業情報エンティティの賞味期限をセット
					wi[j].setResultUseByDate(resultData[i].getResultUseByDate());

					// DBを更新する
					handler.modify(akey);
					if (newWorkinfo != null)
					{
						// 分割レコード作成
						handler.create(newWorkinfo);
					}
	
					// 出荷予定情報の数量を更新する
					updatePlanInformation(wi[j]);
					// 在庫情報の数量を更新する
					updateStockQty(wi[j]);					
					// 実績送信情報を作成する
					createResultData(wi[j]);
				}
				else
				{
					// 保留処理で分配する数がない場合
					
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
				 // 入庫予定作成処理（入荷入庫連携）
				 InstockStoragePlanCreator planCre = new InstockStoragePlanCreator(conn);
				 planCre.startMakePlan(conn, wi[j]);
			}			
		}
	}

	/**
	 * 更新した作業情報を元に、入荷予定情報を更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>入荷予定情報.入荷予定一意キー=作業情報.予定一意キー (作業情報に対応して、1件の入荷予定情報があります。)
	 *     <LI>状態フラグ != 削除</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名</TD></TR>
	 *    </TABLE>
	 *   入荷実績数、入荷欠品数に、対応した作業情報の実績数、欠品数を加算します。<BR>
	 *   ここでは状態フラグは更新しません。
	 * </DIR>
	 * 
	 * @param workinfo	作業情報
	 * @throws InvalidStatusException ユニークキーでの検索に複数件該当した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	public void updatePlanInformation(WorkingInformation workinfo)
		throws
			InvalidStatusException,
			ReadWriteException,
			NotFoundException,
			InvalidDefineException
	{
		try
		{
			// 該当する入荷予定情報を検索する
			InstockPlanSearchKey skey = new InstockPlanSearchKey();
			skey.setInstockPlanUkey(workinfo.getPlanUkey());
			skey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			InstockPlan plan = new InstockPlan();
			InstockPlanHandler handler = new InstockPlanHandler(conn);
			plan = (InstockPlan) handler.findPrimaryForUpdate(skey);

			// 検索した入荷予定情報を更新する
			InstockPlanAlterKey akey = new InstockPlanAlterKey();
			akey.setInstockPlanUkey(workinfo.getPlanUkey());
			akey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			akey.updateResultQty(plan.getResultQty() + workinfo.getResultQty());
			akey.updateShortageCnt(plan.getShortageCnt() + workinfo.getShortageCnt());
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
			
			addCrossDocInfo(workinfo);
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DnInStockPlan" + 
								" INSTOCK_PLAN_UKEY = " + workinfo.getPlanUkey() +"]";
			// 6026020=ユニークキーでの検索に複数件該当しました。{0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			throw new ReadWriteException(e.getMessage());
		}
	}

	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 更新した作業情報の入荷予定一意キーを元に、入荷予定情報の状態フラグを更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>入荷予定一意キー=作業情報.予定一意キー (作業情報に対応して、1件の入荷予定情報があります。)
	 *     <LI>状態フラグ != 削除</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>作業情報の状態による（未開始 or 一部完了 or 完了）</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名</TD></TR>
	 *    </TABLE>
	 *   状態フラグを更新します。実績数+欠品数 >= 予定数 を満たす時は「完了」、それ以外は｢一部完了｣。 <BR>
	 * </DIR>
	 * 
	 * @param	planUkey	作業情報の入荷予定一意キーの配列
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateCompletionStatus(String[] planUkey)
		throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		for (int i = 0; i < planUkey.length; i++)
		{
			WorkingInformationSearchKey skey =
				new WorkingInformationSearchKey();
			skey.setPlanUkey(planUkey[i]);
				
			WorkingInformation[] workinfo;
			WorkingInformationHandler wHandler =
				new WorkingInformationHandler(conn);
			workinfo = (WorkingInformation[]) wHandler.find(skey);

			InstockPlanHandler sHandler = new InstockPlanHandler(conn);
			InstockPlanAlterKey akey = new InstockPlanAlterKey();
			akey.setInstockPlanUkey(planUkey[i]);
			akey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");

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
			if (existWorkingData || (! existCompleteData && ! existUnstartData))
			{
				// 作業中データがある場合、または完了、未開始データが共にない場合は
				// 状態フラグは更新しない
				// （通常はありえない）
				continue;
			}
			if (existCompleteData)
			{
				if (existUnstartData)
				{
					// 完了データ、未開始データの両方が存在する場合は一部完了にする
					akey.updateStatusFlag(
					        InstockPlan.STATUS_FLAG_COMPLETE_IN_PART);
				}
				else
				{
					// 完了データのみが存在する場合は完了にする
					akey.updateStatusFlag(
					        InstockPlan.STATUS_FLAG_COMPLETION);
				}
			}
			else
			{
				// 未開始データのみが存在する場合は未開始にする
				akey.updateStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
			}
			akey.updateLastUpdatePname(processName);
			sHandler.modify(akey);
		}
	}

	/**
	 * 更新した作業情報を元に、在庫情報の在庫数、作業予定数を更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>在庫情報.在庫ID=作業情報.在庫ID
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>センター在庫</TD></TR>
	 *      <TR><TD>在庫数</TD>			<TD>(+)実績送信電文.実績数</TD></TR>
	 *      <TR><TD>作業予定数</TD>		<TD>(-)実績送信電文.予定数(マイナス値になる時は0をセット)</TD></TR>
	 *      <TR><TD>在庫ステータス</TD>	<TD>作業予定数が0の時は、9:完了をセット</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 
	 * @param workinfo	作業情報
	 * @throws InvalidStatusException ユニークキーでの検索に複数件該当した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateStockQty(WorkingInformation workinfo)
		throws
			InvalidStatusException,
			ReadWriteException,
			NotFoundException,
			InvalidDefineException
	{
		try
		{
			// 在庫数、引当数から作業数を減算する
			StockSearchKey skey = new StockSearchKey();
			skey.setStockId(workinfo.getStockId());
			Stock stock = new Stock();
			StockHandler handler = new StockHandler(conn);
			stock = (Stock) handler.findPrimary(skey);
			StockAlterKey akey = new StockAlterKey();
			akey.setStockId(workinfo.getStockId());
			// 入荷した数量分在庫を増やす
			akey.updateStockQty(stock.getStockQty() + workinfo.getResultQty());
			int planQty = workinfo.getResultQty() + workinfo.getShortageCnt();
			if ((stock.getPlanQty() - planQty) < 0)
			{
				akey.updatePlanQty(0);
			}
			else
			{
				akey.updatePlanQty(stock.getPlanQty() - planQty);
			}
			if ((stock.getPlanQty() - planQty) <= 0)
			{
				// 在庫ステータス(9:完了)更新
				akey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
			}
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DmStock" + 
								" STOCK_ID = " + workinfo.getStockId() +"]";
			// 6026020=ユニークキーでの検索に複数件該当しました。{0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	/**
	 * 更新対象データをロックし、そのデータの入荷予定一意キーのリストを返す。<BR>
	 * 更新の対象となるテーブルは作業情報、入荷予定情報、
	 * 在庫情報（キャンセルでない場合のみ）である。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>作業区分 = 入荷</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	workinfo	出荷実績情報  作業情報エンティティの配列
	 * @param	rftNo		RFT番号
	 * @param	workerCode	作業者コード
	 * @param	isCancel	確定処理がキャンセルかどうかのフラグ
	 * 							（キャンセル時には在庫をロックしない）
	 * @return				条件に該当する入荷予定一意キーの配列
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException		データベースエラー発生
	 * @throws LockTimeOutException	ロックタイムアウト発生
	 */
	protected String[] lockUpdateData(
		WorkingInformation[] workinfo,
		String rftNo,
		String workerCode,
		boolean isCancel) throws UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		String[] collectJobNoList = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i ++)
		{
		    collectJobNoList[i] = workinfo[i].getCollectJobNo();
		}

		skey.setCollectJobNo(collectJobNoList);
		skey.setJobType(jobType);
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		// WorkingInformationをロックする
		WorkingInformation[] wi = null;
		try
		{
		    wi = (WorkingInformation[])handler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
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
		
		for (int i = 0; i < wi.length; i ++)
		{
		    // 自端末が作業中にしたデータでない場合
		    if (! rftNo.equals(wi[i].getTerminalNo()))
		    {
		        throw new UpdateByOtherTerminalException();
		    }

		    // 作業中にした作業者が異なる場合
            if (! isMaintenanceCancel)
            {
                if (! workerCode.equals(wi[i].getWorkerCode()))
                {
                    throw new UpdateByOtherTerminalException();
                }
            }
		    
		    // 状態が作業中でない場合
		    if (! wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
		    {
		        throw new UpdateByOtherTerminalException();
		    }

			// 最終更新処理名が対応するデータ要求処理でない場合
			Vector tempVec = new Vector();
			for (int j = 0; j < startProcessName.length; j++)
			{
				tempVec.addElement(startProcessName[j]);
			}
			if (!tempVec.contains(wi[i].getLastUpdatePname()))
			{
				throw new UpdateByOtherTerminalException();
			}
		}
		
		// 入荷予定一意キーのリストを生成する
		String[] planUkeyList = getPlanUkeyList(collectJobNoList, rftNo, workerCode);

		// 入荷予定情報をロックする
		lockPlanInformation(planUkeyList);
		
		// 更新対象の在庫情報をロックする
		if (! isCancel)
		{
		    // 在庫管理ありかつキャンセル処理でない場合のみ在庫をロックする
		    lockStockData(wi);
		}
		
		return planUkeyList;
	}

	/**
	 * ハッシュテーブルに値を保持します。 <BR>
	 * 
	 * @param workinfo 保持する実績情報です。
	 * @throws ReadWriteException		データベースエラー発生
	 */
	protected void addCrossDocInfo(WorkingInformation workinfo) throws ReadWriteException
	{
		// クロスドックパッケージが入っていた場合、ハッシュテーブルに値を追加します
		if (SystemParameter.withCrossDocManagement())
		{
			int[] wQty = new int[2]; 
			// 入荷予定一意キーをキーに実績数と欠品数をHashTableに保持しておく
			if (crossdocInfo.containsKey(workinfo.getPlanUkey()))
			{
				// 予定一意キーがハッシュテーブルに存在する場合
				
				// ハッシュテーブルから値を取得します
				wQty = (int[])crossdocInfo.get(workinfo.getPlanUkey());				
				// 実績数をハッシュテーブルの実績数に加算します
				wQty[0] += workinfo.getResultQty();
				// 欠品数をハッシュテーブルの欠品数に加算します
				wQty[1] += workinfo.getShortageCnt();
						
				crossdocInfo.remove(workinfo.getPlanUkey());
				// ハッシュテーブルに値を保持します
				crossdocInfo.put(workinfo.getPlanUkey(), wQty);
			}
			else
			{
				// 予定一意キーがハッシュテーブルに存在しない場合
						
				// 実績数
				wQty[0] += workinfo.getResultQty();
				// 欠品数
				wQty[1] += workinfo.getShortageCnt();
						
				crossdocInfo.remove(workinfo.getPlanUkey());
				// ハッシュテーブルに値を保持します
				crossdocInfo.put(workinfo.getPlanUkey(), wQty);
			}
		}
	}
	
	/**
	 * CrossDocOperatorに値を渡してクロスドック処理を行います。 <BR>
	 * 
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected void crossDocProcess() throws ReadWriteException, ScheduleException
	{
		// クロスドックパッケージが入っていた場合、クロスドック処理を行う
		if (SystemParameter.withCrossDocManagement())
		{
			CrossDocOperator wCrossDocOperator = new CrossDocOperator();
			
			for( Enumeration e = crossdocInfo.keys(); e.hasMoreElements();)
			{
				// ハッシュテーブルから値を取得します
				String key = e.nextElement().toString();
				
				int[] wQty = new int[2];
				
				wQty = (int[])crossdocInfo.get(key);
				
				// CrossDocOperatorに値を渡しますコネクション、予定一意キー、実績数、欠品数)
				if (!wCrossDocOperator.complete(conn, key, wQty[0], wQty[1]))
				{
					throw new ScheduleException();
				}
			}
			// ハッシュテーブルをクリアします
			crossdocInfo.clear();
		}
	}

    /**
     * 引数で指定された入荷予定一意キーを持つ入荷予定情報のうち、状態が削除以外のものをロックする。
     * 
     * @param	planUKeyList[]	入荷予定一意キーの配列
	 * @throws ReadWriteException		データベースエラー発生
     */
	protected void lockPlanInformation(String[] planUKeyList) throws ReadWriteException
	{
		// 入荷予定情報をロックする
		InstockPlanHandler iHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey iskey = new InstockPlanSearchKey();
		
		iskey.setInstockPlanUkey(planUKeyList);
		iskey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");		
		iHandler.findForUpdate(iskey);
	}

	// Private methods -----------------------------------------------
}
//end of class
