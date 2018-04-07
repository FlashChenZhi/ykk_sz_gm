// $Id: Id0532Operate.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;
/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * 出荷先単位出荷検品データ要求(ID0532)処理を行うためのクラスです。<BR>
 * <CODE>IdProcess</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 検品データの取得し、そのデータを作業中に更新します。また、検品予定ファイルの作成を行います。<BR>
 * Id0532Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0532Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	private static final String PROCESS_NAME = "ID0532";
	private static final String CLASS_NAME = "Id0532Operate";
	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public Id0532Operate()
	{
		super();
	}

	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0532Operate(Connection conn)
	{
		super();
		wConn = conn;
	}
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $";
	}

	/**
	 * 出荷先単位出荷検品開始要求に対するデータを取得し、作業可能であれば、そのデータの状態を作業中にします。<BR>
	 * コミットは本メソッド呼び出し元で行います。<BR>
	 * 最初に次の条件で作業データを検索します。<BR>
	 * <DIR>
	 *   荷主コード、出荷予定日付、出荷先コード = パラメータより取得<BR>
	 *   作業区分=05(出荷)<BR>
	 *   状態フラグ=0:未開始 or (2:作業中、但し作業者コードとRFTNoが同じであること)<BR>
	 *   作業開始フラグ=1(開始済)<BR>
	 * </DIR>
	 * データが取得できない場合、RFTに戻す状態フラグを決定する為に、次の条件で作業データを検索します。<BR>
	 *  データの取得が出来ない場合は、8:該当データ無しをメッセージとしてNotFoundExceptionを返します。<BR>
	 *  取得データに１件でも作業中が含まれている時は、1:他端末作業中をメッセージとしてNotFoundExceptionを返します。<BR>
	 *  全件完了している時は、2:作業完了済みをメッセージとしてNotFoundExceptionを返します。<BR>
	 * <DIR>
	 *   荷主コード、出荷予定日付、出荷先コード = パラメータより取得<BR>
	 *   作業区分=05(出荷)<BR>
	 *   状態フラグ=9(削除)以外<BR>
	 *   作業開始フラグ=1(開始済)<BR>
	 * </DIR>
	 * 集約の処理を呼び出します。<BR>
	 * <DIR>
	 * 集約の有無をパラメタファイルから取得し、集約を行なう場合は、集約作業Noを振り直します。
	 * (集約する中の１件目の集約作業Noを使用します。)<BR>
	 * 振り直した結果は、DB更新します。<BR>
	 * </DIR>
	 * 作業可能なデータが１件以上の場合は、DBから取得したデータを、配列にセットします。<BR>
	 * この時、集約作業Noで集約して配列にセットします。<BR>
	 * またこのデータは、次の更新を行います。<BR>
	 * <DIR>
	 *  作業情報の状態フラグを作業中に更新します。<BR>
	 *  作業情報の端末NoにRFT号機Noを、作業者コードにRFT作業者コードをセットします。<BR>
	 *  出荷予定情報の状態フラグを作業中に更新します。(作業情報の予定一意キーで、出荷予定情報を検索します)<BR>
	 * </DIR>
	 * 更新後、配列にセットした作業情報の一覧を戻り値として、返します。<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード<BR>
	 * @param	planDate		予定日<BR>
	 * @param	customerCode	出荷先コード<BR>
	 * @param	rftNo			RFT号機番号<BR>
	 * @param	workerCode		作業者コード<BR>
	 * @return	出荷予定情報  作業情報エンティティの配列<BR>
	 * @throws LockTimeOutException   他の処理がロック中でロックの獲得に失敗した場合に通知されます。
	 * @throws IllegalAccessException 指定されたクラスの定義にアクセスできない場合に通知されます。
	 * @throws NotFoundException      該当データが見つからない場合に通知されます。
	 * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
	 * @throws ReadWriteException     データベースとの接続で異常が発生した場合に通知されます。
	 * @throws OverflowException      数量がオーバーフローした場合に通知されます。
	*/
	public WorkingInformation[] shippingStartOnCustomer(
		String consignorCode,
		String planDate,
		String customerCode,
		String rftNo,
		String workerCode)
		throws LockTimeOutException, IllegalAccessException, NotFoundException, InvalidDefineException, ReadWriteException, OverflowException
	{
		// WorkingInfoを検索した結果を保持する変数
		WorkingInformation[] workableData = null;

		ShippingOperate shippingOperate;
		try
		{
			shippingOperate = (ShippingOperate) PackageManager.getObject("ShippingOperate");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "ShippingOperate", e.getMessage());
			throw e;
		}
		shippingOperate.setConnection(wConn);
		shippingOperate.setProcessName(PROCESS_NAME);

		WorkingInformationSearchKey pskey = shippingOperate.getBaseCondition(
				consignorCode,
				planDate,
				customerCode,
				rftNo,
				workerCode);
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);

		// 作業情報の検索(１回目)作業区分が 出荷 を対象にする。
		pskey.setItemCodeOrder(1, true);
		pskey.setCollectJobNoOrder(2, true);
		int timeout =WmsParam.WMS_DB_LOCK_TIMEOUT;
		try
		{
			workableData = (WorkingInformation[]) pObj.findForUpdate(pskey, timeout);
		}
		catch (LockTimeOutException e)
		{
			// 6026018=一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}
		if (workableData.length == 0)
		{
			// １回目の検索でデータが該当しない時の処理
			pskey.KeyClear();
			
			// 作業区分が 出荷 を対象にする。
			pskey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
			// 状態フラグが「削除」以外を対象にする。
			pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
			// 作業開始フラグが「1:開始済」を対象にする。
			pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			pskey.setConsignorCode(consignorCode);
			pskey.setPlanDate(planDate);
			pskey.setCustomerCode(customerCode);
			pskey.setItemCodeOrder(1, true);
			workableData = (WorkingInformation[]) pObj.find(pskey);
			// 該当データが見つからない時は、状態フラグを 8:該当データなし でリターン
			if (workableData.length == 0)
			{
				NotFoundException e = new NotFoundException(RFTId5532.ANS_CODE_NULL);
				throw e;
			}
			// 該当データに、作業中or開始済データが見つかった時は、状態フラグを 1:他端末作業中 でリターン
			for (int i = 0; i < workableData.length; i++)
			{
				if (workableData[i].getStatusFlag()
					.equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
					|| workableData[i].getStatusFlag().equals(
						WorkingInformation.STATUS_FLAG_START))
				{
					NotFoundException e = new NotFoundException(RFTId5532.ANS_CODE_WORKING);
					throw e;
				}
			}
			// 該当データに、作業中or開始済データが見つからなかった時は、状態フラグを 2:作業完了済み でリターン
			NotFoundException e = new NotFoundException(RFTId5532.ANS_CODE_COMPLETION);
			throw e;
		}
		
		// 作業集約処理を行う。
		WorkingInformation[] target = shippingOperate.collectShippingData(workableData, workerCode, rftNo, true);
		return target;
		
	}
	// Package methods -----------------------------------------------
	// Protected methods ---------------------------------------------
	// Private methods -----------------------------------------------
}
//end of class
