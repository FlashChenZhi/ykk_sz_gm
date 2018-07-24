// $Id: ShippingOperate.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2004-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;

/**
 * Designer : T.Yamashita <BR>
 * Maker :   <BR>
 * <BR>
 * RFTからの出荷検品完了処理から呼び出される共通関数が定義されます。<BR>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class ShippingOperate
{

	/**
	 * クラス名を表すフィールド
	 */
	private static final String CLASS_NAME = "ShippingOperate";

	/**
	 * 処理名（登録処理名、最終更新処理名用）
	 */
    protected String processName = "";

    /**
	 * データベースとの接続
	 */
	protected Connection conn = null;

	/**
	 * コンストラクタ
	 */
	public ShippingOperate()
	{
		super();
	}

	/**
	 * データベースとの接続
	 */
	public ShippingOperate(Connection c)
	{
		this();

		conn = c;
	}

	/**
	 * データベースとの接続
	 */
	public void setConnection(Connection c)
	{
		conn = c;
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
	 * 作業情報より既にデータが完了しているかを取得します。<BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	janCode			JANコード
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	pname		    処理名
	 * @param	workingInformation	出荷実績情報  作業情報エンティティの配列
	 * @return 完了済みの場合true、なしの場合falseを返す。
	 * @throws ReadWriteException     データベースとの接続で異常が発生した場合に通知されます。
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
		jp.co.daifuku.wms.base.entity.WorkingInformation workingInformation) throws ReadWriteException, NotFoundException, 
																						UpdateByOtherTerminalException
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
	 * <P>
	 * パラメータとして受け取った作業情報エンティティ配列に対し、
	 * 集約可能なデータを全て集約し、新しい作業情報エンティティを返します。<BR>
	 * 集約する過程において、集約作業Noを振りなおし、DBの更新も行ないます。<BR>
	 * この時、作業状態等も同時に更新します。
	 * さらにその結果を入荷予定情報に反映させます。<BR>
	 * </P>
	 * <OL>
	 *   <LI>作業者情報から作業者名を取得します。</LI>
	 *   <LI>パラメータとして受け取った作業情報エンティティ配列の最初のデータを
	 *       作業対象データとしてコピーを作成します。</LI>
	 *   <LI>集約を行うかどうかを判定します。
	 *   <LI>配列の2番目以降のデータに対し、作業対象データと集約キーが同じかどうかを判定します。<BR>
	 *       集約キー項目を、商品コードで集約するパターンと、商品コード＋ITF＋ボールITFで集約するパターンの、
	 *       ２パターン用意し、どちらにするのかパラメタファイルから取得します。</LI>
	 *       キーが同じ場合はデータを集約します。
	 *       集約を行なう場合は、集約作業Noを振り直します。
	 *       (集約する中で商品コードが最も小さいデータの作業Noを使用します。)<BR>
	 *       キーが異なる場合は集約処理を終了します。</LI>
	 *   <LI>集約した各データに対し、作業情報を作業中に更新します。</LI>
	 *   <LI>出荷予定情報を作業中に更新します。</LI>
	 * </OL>
	 * <DIR>
	 * </DIR>
	 * @param  workableData 作業情報エンティティの配列(商品コード順にソートされている事)の配列は空であってはなりません。
	 * @param	workerCode	 作業者コード
	 * @param	rftNo		 端末No
	 * @param	isAllData	 全ての作業を対象とするか
	 * @return			     集約後の作業情報エンティティ配列
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws OverflowException		数値項目の桁数が超過した時に通知されます。
	 */
	public WorkingInformation[] collectShippingData(
	        WorkingInformation[] workableData,
	        String workerCode,
	        String rftNo,
			boolean isAllData) 
		throws NotFoundException, InvalidDefineException, ReadWriteException, OverflowException
	{
        
		// 作業者名称取得
		BaseOperate bo = new BaseOperate(conn);
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
			NotFoundException ex = new NotFoundException(RFTId5530.ANS_CODE_ERROR);
			throw ex;
		}

        int jobType = SystemParameter.getJobType(workableData[0].getJobType());

        WorkingInformation[] collectedData;
        
        // 集約作業Noを付け直しする場合（集約する場合）
		WorkingInformation targetData = (WorkingInformation) workableData[0].clone();
		workableData[0].setWorkerCode(workerCode);
		workableData[0].setWorkerName(workerName);
		workableData[0].setTerminalNo(rftNo);
		int collectIndex = 1;
		if (SystemParameter.isRftWorkCollect(jobType))
        {
			ArrayList collectedDataArray = new ArrayList();

			for (; collectIndex < workableData.length; collectIndex ++)
			{
		        if (targetData.hasSameKey(workableData[collectIndex]))
				{
					try
					{
				    	// 作業情報を集約する
				    	targetData.collect(workableData[collectIndex]); 
				    	// 集約する場合は集約作業Noを更新する
				    	workableData[collectIndex].setCollectJobNo(targetData.getCollectJobNo());
					}
					catch (OverflowException e)
					{
						// 6026028=集約処理でオーバーフローが発生しました。テーブル名:{0}
						RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DNWORKINFO");
						throw e;
					}
				}
				else
				{
					// 集約したデータを配列に追加する
					collectedDataArray.add(targetData);

					if (isAllData)
					{
						// 基準データを更新する
						targetData = (WorkingInformation) workableData[collectIndex].clone();
					}
					else
					{
					    // キーが変わったら集約処理を終了する
					    break;
					}
				}

				workableData[collectIndex].setWorkerCode(workerCode);
				workableData[collectIndex].setWorkerName(workerName);
				workableData[collectIndex].setTerminalNo(rftNo);
			}
			
			// 最後の集約データを配列に追加する
			collectedDataArray.add(targetData);

			collectedData = new WorkingInformation[collectedDataArray.size()];
			for (int i = 0; i < collectedData.length; i ++)
			{
				collectedData[i] = (WorkingInformation) collectedDataArray.get(i);
			}
        }
		else if(isAllData)
		{
			collectedData = workableData;
			for (int i = 0; i < collectedData.length; i++)
			{
				collectedData[i].setWorkerCode(workerCode);
				collectedData[i].setWorkerName(workerName);
				collectedData[i].setTerminalNo(rftNo);
			}
			collectedData = workableData;
			collectIndex = collectedData.length;
		}
		else
		{
			collectedData = new WorkingInformation[1];
			collectedData[0] = targetData;			
		}
		
		ArrayList planUkeyArray = new ArrayList();
		
	    // 状態を更新する
		for (int j = 0; j < collectIndex; j ++)
		{
			// 作業情報を作業中に更新する
			updateWorkInfoStatusToNowWorking(workableData[j]);
			
			// 出荷予定一意キーのリストを生成する
			planUkeyArray.add(workableData[j].getPlanUkey());
		}

		String[] planUkeyList = new String[planUkeyArray.size()];
		for (int i = 0; i < planUkeyList.length; i ++)
		{
			planUkeyList[i] = (String) planUkeyArray.get(i);
		}
		// 出荷予定情報を作業中に更新する
		updateShippingPlanStatusToNowWorking(planUkeyList);

		return collectedData;
	}

	/**
	 * パラメータとして受け取った作業情報エンティティ配列に対し、
	 * 集約可能なデータを全て集約し、新しい作業情報エンティティを返します。<BR>
	 * 
	 * @param workableData 作業情報エンティティの配列
	 * @param workerCode   作業者コード
	 * @param rftNo        端末No.
	 * @return  集約後の作業情報エンティティ配列
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されま
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws OverflowException		数値項目の桁数が超過した時に通知されます。
	 */
	public WorkingInformation collectOneShippingData(
	        WorkingInformation[] workableData,
	        String workerCode,
	        String rftNo) 
		throws NotFoundException, InvalidDefineException, ReadWriteException, OverflowException
	{
		WorkingInformation[] collectedData = collectShippingData(workableData, workerCode, rftNo, false);
		return collectedData[0];
	}
	

	/**
	 * 作業情報を作業中に更新する。<BR>
	 * RFTに送信するデータが集約されている場合、集約作業Noも更新する。
	 * 
	 * (更新内容)
	 * <TABLE border="1">
	 *   <TR><TD>集約作業No</TD>		<TD>集約する中で商品コードが最も小さいデータの作業No</TD></TR>
	 *   <TR><TD>状態フラグ</TD>		<TD>作業中</TD></TR>
	 *   <TR><TD>作業者コード</TD>		<TD>作業者コード</TD></TR>
	 *   <TR><TD>作業者名称</TD>		<TD>作業者マスタ.作業者名称</TD></TR>
	 *   <TR><TD>端末No</TD>			<TD>RFT No</TD></TR>
	 *   <TR><TD>最終更新処理名</TD>	<TD>"ID0530"</TD></TR>
	 * </TABLE>
	 * 
	 * @param shippingData	作業情報のエンティティ
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateWorkInfoStatusToNowWorking(WorkingInformation shippingData) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		// 作業情報の更新 (集約作業Noを変更したレコードを更新)
		WorkingInformationAlterKey akey = new WorkingInformationAlterKey();

		// 作業情報を作業中に更新する
		// 更新条件セット
		akey.setJobNo(shippingData.getJobNo());
		// 更新内容セット
		akey.updateCollectJobNo(shippingData.getCollectJobNo());
		akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		akey.updateTerminalNo(shippingData.getTerminalNo());
		akey.updateWorkerCode(shippingData.getWorkerCode());
		akey.updateWorkerName(shippingData.getWorkerName());
		akey.updateLastUpdatePname(processName);

		WorkingInformationHandler handler = new WorkingInformationHandler(conn);
		handler.modify(akey);
	}
	
	/**
	 * 出荷予定情報を作業中に更新する。
	 * 
	 * (更新内容)
	 * <TABLE border="1">
	 *   <TR><TD>出荷予定一意キー</TD>	<TD>パラメータから取得</TD></TR>
	 *   <TR><TD>状態フラグ</TD>		<TD>作業中</TD></TR>
	 *   <TR><TD>最終更新処理名</TD>	<TD>"ID0530"</TD></TR>
	 * </TABLE>
	 * 
	 * @param planUkeyList	作業中に更新する出荷予定一意キーのリスト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateShippingPlanStatusToNowWorking(String[] planUkeyList) throws ReadWriteException, InvalidDefineException
	{
		// 入荷予定情報を作業中に更新する
		ShippingPlanAlterKey akey = new ShippingPlanAlterKey();
		// 更新条件セット
		akey.setShippingPlanUkey(planUkeyList);
		// 更新内容セット
		akey.updateStatusFlag(InstockPlan.STATUS_FLAG_NOWWORKING);
		akey.updateLastUpdatePname(processName);

		ShippingPlanHandler handler = new ShippingPlanHandler(conn);
		try
		{
			handler.modify(akey);
		}
		catch (NotFoundException e)
		{
		    // 6006005=更新対象データがありません。テーブル名:{0}
			RftLogMessage.print(6006005, LogMessage.F_ERROR, CLASS_NAME, "DnShippingPlan");
		    throw new ReadWriteException("6006005\tDnShippingPlan");
		}
	}

	/**
	 * パラメータで指定された荷主コード、予定日、出荷先コードに該当する
	 * 作業可能な出荷作業情報の件数を取得します。
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @return 未作業残アイテム数
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。<BR>
	 */
	public int countRemainingItem(
			String consignorCode,
			String planDate,
			String customerCode,
			String rftNo,
			String workerCode) throws ReadWriteException
	{
		WorkingInformationSearchKey pskey = getBaseCondition(
				consignorCode,
				planDate,
				customerCode,
				rftNo,
				workerCode);
		WorkingInformationHandler pObj = new WorkingInformationHandler(conn);

		pskey.setItemCodeCollect("");
		pskey.setItemCodeGroup(1);

		return pObj.count(pskey);
	}

	/**
	 * パラメータで指定された荷主コード、予定日、出荷先コードに該当する
	 * 出荷作業情報の件数を取得します。
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @return 未作業残アイテム数
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。<BR>
	 */
	public int countTotalItem(
			String consignorCode,
			String planDate,
			String customerCode) throws ReadWriteException
	{
		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		WorkingInformationHandler pObj = new WorkingInformationHandler(conn);

		// 作業区分が 出荷 を対象にする。
		pskey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		// 状態フラグが「削除」以外を対象にする。
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		// 作業開始フラグが「1:開始済」を対象にする。
		pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		pskey.setConsignorCode(consignorCode);
		pskey.setPlanDate(planDate);
		pskey.setCustomerCode(customerCode);

		pskey.setItemCodeCollect("");
		pskey.setItemCodeGroup(1);

		return pObj.count(pskey);
	}

	/**
	 * 商品単位出荷検品開始要求に対する作業可能データを検索する際の基本となる検索条件を生成します。
	 * 検索の基本条件は以下のとおりとします。
	 * <DIR>
	 *   荷主コード、予定日、出荷先コード = パラメータより取得<BR>
	 *   作業区分=05(出荷)<BR>
	 *   状態フラグ=0:未開始 or (2:作業中...但し作業者コードとRFTNoが同じであること)<BR>
	 *   作業開始フラグ=1(開始済)<BR>
	 * </DIR>
	 * 
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @return	作業情報検索キー	作業情報サーチキー<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 */
	protected WorkingInformationSearchKey getBaseCondition(
			String consignorCode,
			String planDate,
			String customerCode,
			String rftNo,
			String workerCode) throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		
		// 作業区分が 出荷 を対象にする。
		skey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		// 状態フラグが「未開始」又は、「作業中」(但し、作業者コードとRFTNoが同じに限る)を対象にする。 
		//  SQL文・・・ (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo, "=", "", "))", "AND");
		// 作業開始フラグが「1:開始済」を対象にする。
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		skey.setConsignorCode(consignorCode);
		skey.setPlanDate(planDate);
		skey.setCustomerCode(customerCode);

		return skey;
	}
	
	/**
	 * 商品単位出荷検品開始要求に対する作業可能データを検索します。
	 * 検索の基本条件は以下のとおりとします。
	 * <DIR>
	 *   荷主コード、予定日、出荷先コード = パラメータより取得<BR>
	 *   作業区分=05(出荷)<BR>
	 *   状態フラグ=0:未開始 or (2:作業中...但し作業者コードとRFTNoが同じであること)<BR>
	 *   作業開始フラグ=1(開始済)<BR>
	 * </DIR>
	 * 
	 * この条件に加え、スキャンコードをJANコード、ケースITF、ボールITFの順に当てはめて検索します。<BR>
	 * 順に検索し、作業可能なデータが見つかった段階で検索を中断し、
	 * 取得したデータを返します。<BR>
	 * この3回の検索のいずれでも作業可能データが見つからなかった場合かつ、ITFtoJAN変換されたコードが
	 * 空でない場合、<CODE>convertedJanCode</CODE>をJANコードとして扱い、検索します。
	 * <BR>
	 * 計4回の検索で作業可能データが見つからなかった場合はnullを返します。
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @param	scanCode		スキャン商品コード
	 * @param	convertedJanCode	ITFtoJAN変換されたJANコード
	 * @return	出荷予定情報  作業情報エンティティの配列<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 */
	protected WorkingInformation[] getWorkableData(
			String consignorCode,
			String planDate,
			String customerCode,
			String rftNo,
			String workerCode,
			String scanCode,
			String convertedJanCode) throws ReadWriteException, LockTimeOutException
	{
		try
		{
			// WorkingInfoを検索した結果を保持する変数
			WorkingInformation[] workingDbShippingData = null;
			WorkingInformationHandler pObj = new WorkingInformationHandler(conn);

			// 作業情報の検索(１回目)
			// スキャンされたコードをJANコードとして検索を行う
			WorkingInformationSearchKey skey = getBaseCondition(
					consignorCode,
					planDate,
					customerCode,
					rftNo,
					workerCode);
			skey.setItemCode(scanCode);
			skey.setItemCodeOrder(1, true);
			skey.setCollectJobNoOrder(2, true);
			int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;

			workingDbShippingData = (WorkingInformation[]) pObj.findForUpdate(skey, timeout);

			if (workingDbShippingData != null && workingDbShippingData.length > 0)
			{
				return workingDbShippingData;
			}
			
			// 作業情報の検索(２回目)
			// スキャンされたコードをケースITFとして検索を行う
			skey = getBaseCondition(
					consignorCode,
					planDate,
					customerCode,
					rftNo,
					workerCode);
			skey.setItf(scanCode);
			skey.setItemCodeOrder(1, true);
			skey.setCollectJobNoOrder(2, true);
			workingDbShippingData = (WorkingInformation[]) pObj.findForUpdate(skey, timeout);

			if (workingDbShippingData != null && workingDbShippingData.length > 0)
			{
				return workingDbShippingData;
			}

			// 作業情報の検索(３回目)
			// スキャンされたコードをボールITFとして検索を行う
			skey = getBaseCondition(
					consignorCode,
					planDate,
					customerCode,
					rftNo,
					workerCode);
			skey.setBundleItf(scanCode);
			skey.setItemCodeOrder(1, true);
			skey.setCollectJobNoOrder(2, true);
			workingDbShippingData = (WorkingInformation[]) pObj.findForUpdate(skey, timeout);

			if (workingDbShippingData != null && workingDbShippingData.length > 0)
			{
				return workingDbShippingData;
			}

			if (! StringUtil.isBlank(convertedJanCode))
			{
				// 作業情報の検索(４回目)
				// ITFtoJAN変換されたコードがセットされている場合、
				// それをJANコードとして検索を行う
				skey = getBaseCondition(
						consignorCode,
						planDate,
						customerCode,
						rftNo,
						workerCode);
				skey.setItemCode(convertedJanCode);
				skey.setItemCodeOrder(1, true);
				skey.setCollectJobNoOrder(2, true);
				workingDbShippingData = (WorkingInformation[]) pObj.findForUpdate(skey, timeout);

				if (workingDbShippingData != null && workingDbShippingData.length > 0)
				{
					return workingDbShippingData;
				}
			}
		}
		catch (LockTimeOutException e)
		{
			// 6026018=一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}
		
		return null;
	}

	/**
	 * 集約対象データが他端末で更新されているかをチェックします。
	 * RFTが保持する予定数と、集約作業No.で検索した作業情報データの予定数の合計
	 * で判断します。
	 * 予定数が一致しない場合は、他端末で更新されたものとして、
	 * UpdateByOtherTerminalExceptionをthrowします。
	 * 
	 * @param collectDataOnRft RFTが保持するデータ
	 * @param collectDataOnDB	現在の作業情報
	 * @param startProcessName	開始処理名
	 * @throws UpdateByOtherTerminalException 先に他端末で更新されていて更新できない場合に通知されます。
	 */
	protected void checkCollectData(
			WorkingInformation collectDataOnRft,
			WorkingInformation[] collectDataOnDB,
			String startProcessName) 
		throws UpdateByOtherTerminalException
	{
		// DB上の予定数の合計を算出
		int count = 0;
		for(int i=0; i < collectDataOnDB.length; i++)
		{
			count += collectDataOnDB[i].getPlanEnableQty();
		}
		
		// 予定数がRFTで持っている数と一致しない場合、他端末で更新された
		if(collectDataOnRft.getPlanEnableQty() != count)
		{
			throw new UpdateByOtherTerminalException();
		}
		
		// 状態をチェックする
		// 全て作業中で、最終更新処理名がこのクラスの場合のみOK
		for (int j = 0; j < collectDataOnDB.length; j++)
		{
			if (! collectDataOnDB[j].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				throw new UpdateByOtherTerminalException();
			}

			if (! collectDataOnDB[j].getLastUpdatePname().equals(startProcessName))
			{
				throw new UpdateByOtherTerminalException();
			}
		}
	}
}
