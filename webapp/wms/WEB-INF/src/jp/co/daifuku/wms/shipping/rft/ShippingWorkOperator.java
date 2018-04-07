// $Id: ShippingWorkOperator.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.sql.Connection;
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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WorkOperator;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;

/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * 出荷検品完了処理を行う共通関数が定義されます。<BR>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class ShippingWorkOperator extends WorkOperator
{

	/**
	 * クラス名を表すフィールド
	 */
	private static final String CLASS_NAME = "ShippingOperate";

	/**
	 * コンストラクタ
	 */
	public ShippingWorkOperator()
	{
		super();
	}

	/**
	 * データベースとの接続
	 */
	public ShippingWorkOperator(Connection c)
	{
		conn = c;
	}

	/**
	 * 作業情報より既にデータが完了しているかを取得します。<BR>
	 * 
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	janCode			JANコード
	 * @param	rftNo			端末No.
	 * @param	workerCode		作業者コード
	 * @param	pname		    処理名
	 * @param	workingInformation	出荷実績情報  作業情報エンティティの配列
	 * @return 完了済みの場合true、なしの場合falseを返す。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException      該当データが見つからない場合に通知されます。
	 * @throws UpdateByOtherTerminalException DB更新時に、先に他端末で更新されていて更新できない場合に通知されます。
	 */
	public boolean alreadyCompleteCheck(
		String consignorCode,
		String planDate,
		String customerCode,
		String janCode,
		String rftNo,
		String workerCode,
		String pname,
		WorkingInformation workingInformation) throws ReadWriteException, NotFoundException, UpdateByOtherTerminalException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		// 再度 状態なしでデータを検索
		// DBを検索する
		skey.KeyClear();
		skey.setConsignorCode(consignorCode);
		skey.setPlanDate(planDate);
		skey.setCustomerCode(customerCode);
		if (!janCode.equals(""))
		{
			skey.setItemCode(janCode);
		}
		skey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);
		// 集約作業No
		skey.setCollectJobNo(workingInformation.getCollectJobNo());
		WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);
		if (wi.length <= 0)
		{
			throw new NotFoundException();
		}
		for (int i = 0; i < wi.length; i++)
		{
			if (wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
				&& !wi[i].getLastUpdatePname().equals(pname))
				throw new UpdateByOtherTerminalException();
			else if (
				!wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
					&& !wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_DELETE))
				throw new UpdateByOtherTerminalException();
			if (!wi[i].getTerminalNo().equals(rftNo) || !wi[i].getWorkerCode().equals(workerCode))
			{
				throw new UpdateByOtherTerminalException();
			}
		}
		return true;

	}

	/**
	 * 集約作業Noを元に作業情報から荷主コード、予定日を取得した上で完了処理を呼び出す。
	 * 出荷のみメソッドのインターフェースが異なるため、それを吸収する。
	 * @param resultData   作業情報エンティティの配列
	 * @param workerCode   作業者コード
	 * @param rftNo        端末No.
	 * @param isShortage   数量不足のときに欠品にするかどうかのフラグ
	 * @throws LockTimeOutException    一定時間データベースのロックが解除されない時に通知されます。
	 * @throws InvalidStatusException  範囲外の状態をセットした場合に使用される例外です。
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されま
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws OverflowException		数値項目の桁数が超過した時に通知されます。
	 */
	public void completeWorkingData(
			jp.co.daifuku.wms.base.rft.WorkingInformation[] resultData,
			String workerCode,
			String rftNo,
			boolean isShortage)
		throws LockTimeOutException, InvalidStatusException, ReadWriteException, NotFoundException, 
		InvalidDefineException, DataExistsException, UpdateByOtherTerminalException, ScheduleException
	{
		String consignorCode = resultData[0].getConsignorCode();
		String planDate = resultData[0].getPlanDate();
		String customerCode = resultData[0].getCustomerCode();
		
		completeWorkingData(
				consignorCode,
				planDate,
				customerCode,
				rftNo,
				workerCode,
				resultData,
				isShortage);
	}

	/**
	 * 出荷先別出荷検品実績データ [確定処理]<BR>
	 * 出荷実績情報について、以下の確定処理を実行します。<BR>
	 * 
	 *  作業情報の更新。該当するデータを検索し、状態フラグを完了に更新し、実績数などの項目をセットします。<BR>
	 *  出荷予定情報の更新。該当するデータを検索し、状態フラグを完了に更新し、実績数などの項目をセットします。<BR>
	 *  在庫情報の更新。該当するデータを検索し、在庫数と引当数を減算します。<BR>
	 *  実績送信情報の作成。検品完了した実績を作成します。<BR>
	 *  作業者実績情報の更新。該当するデータを検索し、作業数、作業回数を加算します。<BR>
	 *  在庫移行処理を実行します。<BR>
	 * 
	 * <BR>
	 * 作業情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 *     <LI>予定日</LI>
	 *     <LI>荷主コード</LI>
	 *     <LI>出荷先コード</LI>
	 *     <LI>商品コード</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>作業No</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0061"</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 *   実際に検品した実績数を、該当レコードの作業実績数に分配します。分配する数量は「作業可能数」です。<BR>
	 *   過剰の場合は、最後のレコードに分配します。<BR>
	 * </DIR>
	 * <BR>
	 * 出荷予定情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>出荷予定一意キー=作業情報.作業No (作業情報に対応して、1件の出荷予定情報があります。)
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0061"</TD></TR>
	 *    </TABLE>
	 *   出荷実績数、出荷欠品数に、対応した作業情報の実績数、欠品数を加算します。<BR>
	 *   状態フラグを更新します。実績数+欠品数 >= 予定数 を満たす時は「完了」、それ以外は｢一部完了｣。 <BR>
	 * </DIR>
	 * <BR>
	 * 在庫情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>在庫ID=作業情報.作業No (作業情報に対応して、1件の在庫情報があります。)
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>在庫数</TD>			<TD>在庫数 - 作業情報.予定数</TD></TR>
	 *      <TR><TD>引当数</TD>			<TD>引当数 - 作業情報.予定数</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0061"</TD></TR>
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
	 *     <LI>作業区分=出荷:05</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>作業終了日時</TD>	<TD>現在のシステム日付</TD></TR>
	 *      <TR><TD>作業数量</TD>		<TD>+ 今回作業した実績数の総数</TD></TR>
	 *      <TR><TD>作業回数</TD>		<TD>+ 検品実績ファイルの有効行（実績数が0以上の行)の行数</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	workingInformation	出荷実績情報  作業情報エンティティの配列
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	*/
	public void completeWorkingData(
		String consignorCode,
		String planDate,
		String customerCode,
		String rftNo,
		String workerCode,
		jp.co.daifuku.wms.base.rft.WorkingInformation[] workingInformation,
		boolean isShortage)
		throws InvalidStatusException, NotFoundException, InvalidDefineException, DataExistsException, UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		String[] planUkeyList = lockUpdateData(
			consignorCode,
			planDate,
			customerCode,
			rftNo,
			workerCode);

		updateWorkingInformation(
			rftNo,
			workerCode,
			workingInformation,
			isShortage);

		updateCompletionStatus(planUkeyList);
		
		updateWorkerResult(workerCode, rftNo, workingInformation, ! isShortage);
	}
	
	/**
	 * 出荷先別出荷検品実績データ [キャンセル処理]<BR>
	 * 作業情報から該当するデータを検索し、状態フラグを未開始に、端末Noと作業者コードを空白に更新します。<BR>
	 * <DIR>
	 * 作業情報の更新<BR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>状態フラグ=2:作業中</LI>
	 *     <LI>予定日</LI>
	 *     <LI>荷主コード</LI>
	 *     <LI>出荷先コード</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>集約作業No</TD>		<TD>作業No</TD></TR>
	 *      <TR><TD>状態フラグ</TD>		<TD>未開始:0</TD></TR>
	 *      <TR><TD>端末No</TD>			<TD>空白</TD></TR>
	 *      <TR><TD>作業者コード</TD>	<TD>空白</TD></TR>
	 *      <TR><TD>作業者名称</TD>		<TD>空白</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 * </DIR>
	 * 
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	workerCode		作業者コード
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			端末No.
	 * @throws InvalidStatusException  範囲外の状態をセットした場合に使用される例外です。
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException    一定時間データベースのロックが解除されない時に通知されます。
	 * @throws UpdateByOtherTerminalException DB更新時に、先に他端末で更新されていて更新できない場合に通知されます。
	 */
	public void cancel(
		String consignorCode,
		String planDate,
		String workerCode,
		String customerCode,
		String rftNo)
		throws InvalidStatusException, NotFoundException, InvalidDefineException, ReadWriteException, 
		LockTimeOutException, UpdateByOtherTerminalException
	{
		cancel(consignorCode, planDate, workerCode, customerCode, null, rftNo);
	}

	/**
	 * 出荷先別出荷検品実績データ [キャンセル処理]<BR>
	 * 作業情報から該当するデータを検索し、状態フラグを未開始に、端末No.と作業者コードを空白に更新します。<BR>
	 * <DIR>
	 * 作業情報の更新<BR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>状態フラグ=2:作業中</LI>
	 *     <LI>予定日</LI>
	 *     <LI>荷主コード</LI>
	 *     <LI>出荷先コード</LI>
	 *     <LI>JANコード（nullが指定された場合は検索条件から外す）</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>状態フラグ</TD>		<TD>未開始:0</TD></TR>
	 *      <TR><TD>端末No</TD>			<TD>空白</TD></TR>
	 *      <TR><TD>作業者コード</TD>	<TD>空白</TD></TR>
	 *      <TR><TD>作業者名称</TD>		<TD>空白</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 * </DIR>
	 * 
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	workerCode		作業者コード
	 * @param	customerCode	出荷先コード
	 * @param	janCode	        JANコード（nullが指定された場合は検索条件から外す）
	 * @param	rftNo			端末No.
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException    一定時間データベースのロックが解除されない時に通知されます。
	 * @throws UpdateByOtherTerminalException DB更新時に、先に他端末で更新されていて更新できない場合に通知されます。
	*/
	public void cancel(
		String consignorCode,
		String planDate,
		String workerCode,
		String customerCode,
		String janCode,
		String rftNo)
		throws  NotFoundException, InvalidDefineException, ReadWriteException, LockTimeOutException, UpdateByOtherTerminalException
	{
		// 更新対象データをロックする
		String[] planUkeyList =
			lockUpdateData(consignorCode, planDate, customerCode, janCode, rftNo, workerCode);

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setConsignorCode(consignorCode);
		skey.setPlanDate(planDate);
		skey.setCustomerCode(customerCode);
		if (janCode != null)
		{
			skey.setItemCode(janCode);
		}
		skey.setTerminalNo(rftNo);
		skey.setWorkerCode(workerCode);
		skey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		skey.setLastUpdatePname(startProcessName);

		WorkingInformationHandler handler = new WorkingInformationHandler(conn);
		WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

		for (int i = 0; i < wi.length; i++)
		{
			WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
			akey.setJobNo(wi[i].getJobNo());

			// 集約作業Noを元に戻す(作業Noに戻す)
			akey.updateCollectJobNo(wi[i].getJobNo());
			akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			akey.updateWorkerCode("");
			akey.updateWorkerName("");
			akey.updateTerminalNo("");
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
		}

		// 作業情報の状態を基に出荷予定情報の状態を元に戻す
		updateCompletionStatus(planUkeyList);
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 作業情報を更新する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 *     <LI>予定日</LI>
	 *     <LI>荷主コード</LI>
	 *     <LI>出荷先コード</LI>
	 *     <LI>商品コード</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>作業No</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0061"</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 *   実際に検品した実績数を、該当レコードの作業実績数に分配します。分配する数量は「作業可能数」です。<BR>
	 *   過剰の場合は、最後のレコードに分配します。<BR>
	 * </DIR>
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	workingInformation	出荷実績情報  作業情報エンティティの配列
	 * @param	isShortage		数量不足のときに欠品にするかどうかのフラグ
	 * @return DB更新時はtrue、既に完了している場合はfalseを返します
	 * @throws InvalidStatusException  範囲外の状態をセットした場合に使用される例外です。
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws DataExistsException     既に同じ情報が登録済みの場合に発生する例外です。
	 * @throws UpdateByOtherTerminalException DB更新時に、先に他端末で更新されていて更新できない場合に通知されます。
	 */
	public void updateWorkingInformation(
		String rftNo,
		String workerCode,
		WorkingInformation[] workingInformation,
		boolean isShortage)
		throws InvalidStatusException, NotFoundException, InvalidDefineException, ReadWriteException, 
		DataExistsException, UpdateByOtherTerminalException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		WorkingInformationHandler handler =
			new WorkingInformationHandler(conn);
			
			
		int alreadycomp = 0;
		boolean alreadycomp_flag = false;

		for (int i = 0; i < workingInformation.length; i ++)
		{
			// DBを検索する
			skey.KeyClear();
			skey.setConsignorCode(workingInformation[0].getConsignorCode());
			skey.setPlanDate(workingInformation[0].getPlanDate());
			skey.setCustomerCode(workingInformation[0].getCustomerCode());
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			// TC->DCの順にデータ取得し、欠品時にTCが優先して完了されるようにする。
			skey.setTcdcFlagOrder(1, false);
			skey.setCollectJobNoOrder(2, true);
			skey.setJobNoOrder(3, true);
			// 集約作業No
			skey.setCollectJobNo(workingInformation[i].getCollectJobNo());
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

			if (wi.length <= 0)
			{
				ShippingOperate shippingOperate = new ShippingOperate();
				shippingOperate.setConnection(conn);

				if (! isMaintenanceCancel)
				{
					if (shippingOperate.alreadyCompleteCheck(
							workingInformation[i].getConsignorCode(),
							workingInformation[i].getPlanDate(),
							workingInformation[i].getCustomerCode(),
							"",
							rftNo,
							workerCode,
							processName,
							workingInformation[i]
							))
					{
						// 既に完了していた作業情報数をカウント
						alreadycomp++;
						alreadycomp_flag = true;
					}
				}
			}
			
			// データが他で更新されていないかどうかをチェックする
			for (int j = 0; j < wi.length; j++)
			{
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

			// 出荷実績情報の実績数を保持
			int workQty = workingInformation[i].getResultQty();

			for (int j = 0; j < wi.length; j++)
			{
				WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
				akey.setJobNo(wi[j].getJobNo());
				akey.setConsignorCode(workingInformation[i].getConsignorCode());
				akey.setPlanDate(workingInformation[i].getPlanDate());
				akey.setCustomerCode(workingInformation[i].getCustomerCode());
				akey.setTerminalNo(rftNo);
				akey.setWorkerCode(workerCode);
				
				if( workQty > 0 || isShortage )
				{
					// 分配する数がある時又は欠品完了の場合

					// 保留分割した場合の新規データ用作業情報エンティティ
					WorkingInformation newWorkinfo = null;

					int resultQty = workQty;
					// 更新する値をセットする
					// 次データある場合で作業可能数より出荷実績情報の実績数が大きい場合
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
						// 実績数より作業可能数が大きい場合(欠品or保留の場合)
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
							// 保留の場合
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
	
					akey.updateResultUseByDate(workingInformation[i].getResultUseByDate());
					// 現在の作業情報エンティティの賞味期限をセット
					wi[j].setResultUseByDate(workingInformation[i].getResultUseByDate());

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
					akey.updateCollectJobNo( wi[j].getJobNo() );
					// DBを更新する
					handler.modify(akey);
				}
			}
		}
		// 全作業情報が同一端末No.、同一作業者コード、同一プロセス名称で完了済みかのチェック
		if (alreadycomp_flag)
		{
			if (workingInformation.length != alreadycomp)
			{
				throw new UpdateByOtherTerminalException();
			}
		}
	}
	
	/**
	 * 更新した作業情報を元に、出荷予定情報を更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>出荷予定一意キー=作業情報.作業No (作業情報に対応して、1件の出荷予定情報があります。)</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0061"</TD></TR>
	 *    </TABLE>
	 *   出荷実績数、出荷欠品数に、対応した作業情報の実績数、欠品数を加算します。<BR>
	 *   状態フラグを更新します。実績数+欠品数 >= 予定数 を満たす時は「完了」、それ以外は｢一部完了｣。 <BR>
	 * </DIR>
	 * 
	 * @param	wi	出荷作業情報  作業情報エンティティ
	 * @throws InvalidStatusException  範囲外の状態をセットした場合に使用される例外です。
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 */
	public void updatePlanInformation(WorkingInformation wi)
		throws
			InvalidStatusException,
			NotFoundException,
			InvalidDefineException,
			ReadWriteException		
	{
		try
		{
			// 該当する出荷予定情報を検索する
			ShippingPlanSearchKey skey = new ShippingPlanSearchKey();
			skey.setShippingPlanUkey(wi.getPlanUkey());
			skey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
			ShippingPlanHandler handler = new ShippingPlanHandler(conn);
			ShippingPlan plan = (ShippingPlan) handler.findPrimaryForUpdate(skey);

			// 検索した出荷予定情報を更新する
			ShippingPlanAlterKey akey = new ShippingPlanAlterKey();
			akey.setShippingPlanUkey(wi.getPlanUkey());
			akey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
			akey.updateResultQty(plan.getResultQty() + wi.getResultQty());
			akey.updateShortageCnt(plan.getShortageCnt() + wi.getShortageCnt());
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DnShippingPlan" + 
								" SHIPPING_PLAN_UKEY = " + wi.getPlanUkey() +"]";
			// 6026020=ユニークキーでの検索に複数件該当しました。{0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			throw new InvalidStatusException();
		}
	}
	
	/**
	 * 更新した作業情報の出荷予定一意キーを元に、出荷予定情報の状態フラグを更新する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>出荷予定一意キー=作業情報.予定一意キー (作業情報に対応して、1件の出荷予定情報があります。)</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0531" or "ID0533"</TD></TR>
	 *    </TABLE>
	 *   状態フラグを更新します。実績数+欠品数 >= 予定数 を満たす時は「完了」、それ以外は｢一部完了｣。 <BR>
	 * </DIR>
	 * 
	 * @param planUkey 出荷予定一意キー
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void updateCompletionStatus(String[] planUkey)
		throws NotFoundException, InvalidDefineException, ReadWriteException
	{
		for (int i = 0; i < planUkey.length; i++)
		{
			WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
			skey.setPlanUkey(planUkey[i]);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
				
			WorkingInformation[] workinfo;
			WorkingInformationHandler wHandler = new WorkingInformationHandler(conn);
			workinfo = (WorkingInformation[]) wHandler.find(skey);

			ShippingPlanHandler sHandler = new ShippingPlanHandler(conn);
			ShippingPlanAlterKey akey = new ShippingPlanAlterKey();
			akey.setShippingPlanUkey(planUkey[i]);
			akey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");

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
	 * 更新した作業情報を元に、在庫情報の在庫数、引当数を更新します。
	 * 
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>出荷予定一意キー=作業情報.作業No (作業情報に対応して、1件の出荷予定情報があります。)</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0531" or "ID0533"</TD></TR>
	 *    </TABLE>
	 *   出荷実績数、出荷欠品数に、対応した作業情報の実績数、欠品数を加算します。<BR>
	 *   状態フラグを更新します。実績数+欠品数 >= 予定数 を満たす時は「完了」、それ以外は｢一部完了｣。 <BR>
	 * </DIR>
	 * 
	 * @param	wi	出荷作業情報  作業情報エンティティ
	 * @throws InvalidStatusException  範囲外の状態をセットした場合に使用される例外です。
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void updateStockQty(WorkingInformation wi)
		throws
			InvalidStatusException,
			NotFoundException,
			InvalidDefineException,
			ReadWriteException
	{
		try
		{
			// 在庫数、引当数から作業数を減算する
			StockSearchKey skey = new StockSearchKey();
			skey.setStockId(wi.getStockId());
			StockHandler handler = new StockHandler(conn);
			Stock stock = (Stock) handler.findPrimaryForUpdate(skey);
			StockAlterKey akey = new StockAlterKey();
			akey.setStockId(wi.getStockId());
			akey.updateStockQty(stock.getStockQty() - wi.getResultQty());
			akey.updateAllocationQty(
				stock.getAllocationQty() - wi.getResultQty() - wi.getShortageCnt());
			if ((stock.getAllocationQty() - wi.getResultQty() - wi.getShortageCnt()) == 0)
			{
				// 在庫ステータス(9:完了)更新
				akey.updateStatusFlag( Stock.STATUS_FLAG_DELETE );
			}
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DmStock" + " STOCK_ID = " + wi.getStockId() +"]";
			// 6026020=ユニークキーでの検索に複数件該当しました。{0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			throw new InvalidStatusException();
		}
	}
	
	// Private methods -----------------------------------------------
	/**
	 * 更新対象データの出荷予定一意キーのリストを取得する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>荷主コード</LI>
	 *     <LI>予定日</LI>
	 *     <LI>出荷先コード</LI>
	 *     <LI>荷主コード</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 * 	   <LI>作業者コード</LI>
	 * 	   <LI>作業区分=05:出荷</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>出荷予定一意キー</LI>
	 *    </UL>
	 * </DIR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param  janCode         JANコード（nullが指定された場合は検索条件から外す
	 * @param	rftNo			端末No.
	 * @param	workerCode		作業者コード
	 * @return					条件に該当する出荷予定一意キーの配列
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected String[] getPlanUkeyList(
		String consignorCode,
		String planDate,
		String customerCode,
		String janCode,
		String rftNo,
		String workerCode)
		throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setConsignorCode(consignorCode);
		skey.setPlanDate(planDate);
		skey.setCustomerCode(customerCode);
		if (janCode != null)
		{
			skey.setItemCode(janCode);
		}
		skey.setTerminalNo(rftNo);
		skey.setWorkerCode(workerCode);
		skey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		skey.setPlanUkeyCollect("DISTINCT");
		skey.setPlanUkeyOrder(1, true);
		WorkingInformationHandler wHandler = new WorkingInformationHandler(conn);
		WorkingInformation[] workinfo = (WorkingInformation[]) wHandler.find(skey);

		String[] planUkey = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i ++)
		{
			planUkey[i] = workinfo[i].getPlanUkey();
		}
		return planUkey;
	}

	/**
	 * 更新対象データをロックし、そのデータの出荷予定一意キーのリストを返す。
	 * 
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			端末No.
	 * @param	workerCode		作業者コード
	 * @return					条件に該当する出荷予定一意キーの配列
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException    一定時間データベースのロックが解除されない時に通知されます。  
	 * @throws UpdateByOtherTerminalException DB更新時に、先に他端末で更新されていて更新できない場合に通知されます。
	 */
	protected String[] lockUpdateData(
		String consignorCode,
		String planDate,
		String customerCode,
		String rftNo,
		String workerCode) throws ReadWriteException, LockTimeOutException, UpdateByOtherTerminalException
	{
		return lockUpdateData(consignorCode, planDate, customerCode, null, rftNo, workerCode);
	}

	/**
	 * 更新対象データをロックし、そのデータの出荷予定一意キーのリストを返す。
	 * 
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param  janCode         JANコード（nullが指定された場合は検索条件から外す）
	 * @param	rftNo			端末No.
	 * @param	workerCode		作業者コード
	 * @return					条件に該当する出荷予定一意キーの配列
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException    一定時間データベースのロックが解除されない時に通知されます。  
	 * @throws UpdateByOtherTerminalException DB更新時に、先に他端末で更新されていて更新できない場合に通知されます。
	 */
	protected String[] lockUpdateData(
		String consignorCode,
		String planDate,
		String customerCode,
		String janCode,
		String rftNo,
		String workerCode)
		throws ReadWriteException, LockTimeOutException, UpdateByOtherTerminalException
	{
		// WorkingInformationをロックする
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setConsignorCode(consignorCode);
		skey.setPlanDate(planDate);
		skey.setCustomerCode(customerCode);
		if (janCode != null)
		{
			skey.setItemCode(janCode);
		}
		skey.setTerminalNo(rftNo);
		skey.setWorkerCode(workerCode);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		skey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);
		WorkingInformation[] wi = null;
		try
		{
			wi = (WorkingInformation[])handler.findForUpdate(skey,timeout);
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
		
		String[] planUkeyList =
			getPlanUkeyList(consignorCode, planDate, customerCode, janCode, rftNo, workerCode);

		try
		{
			// 出荷予定情報をロックする
			lockPlanInformation(planUkeyList);
		}
		catch (LockTimeOutException e)
		{
			// 6026018=一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNSHIPPINGPLAN");
			throw e;
		}

		return planUkeyList;
	}

	/**
     * 引数で指定された出荷予定一意キーを持つ出庫予定情報のうち、状態が削除以外のものをロックする。
     * 
     * @param	planUKeyList[]	出荷予定一意キーの配列
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException    一定時間データベースのロックが解除されない時に通知されます。  
     */
    protected void lockPlanInformation(String[] planUKeyList) throws ReadWriteException, LockTimeOutException
    {
		// 出荷予定情報をロックする
		ShippingPlanHandler sHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey sskey = new ShippingPlanSearchKey();
		
		sskey.setShippingPlanUkey(planUKeyList);
		sskey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");		
		sHandler.findForUpdate(sskey,WmsParam.WMS_DB_LOCK_TIMEOUT);
    }

	/**
	 * 作業者実績を作成する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>作業日 = WMS作業日</LI>
	 *     <LI>作業者コード</LI>
	 *     <LI>端末No.</LI>
	 *     <LI>作業区分</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>作業回数</TD>		<TD>(+) データ件数</TD></TR>
	 *      <TR><TD>作業数</TD>			<TD>(+) 実績数の合計</TD></TR>
	 *      <TR><TD>作業終了日時</TD>	<TD>システム日時</TD></TR>
	 *      <TR><TD>作業時間</TD>		<TD>(+) データ作業時間</TD></TR>
	 *      <TR><TD>ミスカウント</TD>	<TD>(+) データミスカウント</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 該当する作業者実績が存在しなかった場合は作成してから再度更新する。
	 * 
	 * @param	workerCode		作業者コード
	 * @param	rftNo			端末No
	 * @param	workTime		作業時間
	 * @param	missScanCnt		ミスカウント
	 * @param	workinfo		作業実績情報（作業情報エンティティの配列）
	 * @param	isPending		保留かどうかのフラグ
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されます。
	 */
	public void updateWorkerResult(
			String workerCode, 
			String rftNo,
			int workTime,
			int missScanCnt,
			jp.co.daifuku.wms.base.rft.WorkingInformation workinfo,
			boolean isPending) throws ReadWriteException, NotFoundException
	{
		jp.co.daifuku.wms.base.rft.WorkingInformation[] result = {workinfo};
		updateWorkerResult(workerCode, rftNo, workTime, missScanCnt, result, isPending);
	}
}
