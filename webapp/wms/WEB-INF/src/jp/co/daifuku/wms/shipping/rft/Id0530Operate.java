// $Id: Id0530Operate.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
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
 * 出荷検品JAN問合せ(ID0530)処理を行うためのクラスです。<BR>
 * <CODE>IdOperate</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 検品データの取得し、そのデータを作業中に更新します。
 * また、送信バッファに格納するため、検品予定データを１件取得します。<BR>
 * Id0530Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0530Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	private static final String PROCESS_NAME = "ID0530";
	private static final String CLASS_NAME = "Id0530Operate";
	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public Id0530Operate()
	{
		super();
	}

	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0530Operate(Connection conn)
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
	 * 商品単位出荷検品開始要求に対するデータを取得し、
	 * 作業可能であればそのデータの状態を作業中にします。<BR>
	 * コミットは本メソッド呼び出し元で行います。<BR>
	 * 
	 * <OL>
	 * <LI>作業可能な出荷作業データを検索します。<BR>
	 *   データが見つかった場合は3以降の処理を行います。
	 *   見つからなかった場合は2の処理を行います。</LI>
	 * 
	 * <LI>データが取得できない場合、RFTに戻す状態フラグを決定する為に、次の条件で作業データを検索します。<BR>
	 *   データの取得が出来ない場合は、8:該当データ無しをメッセージとしてNotFoundExceptionを返します。<BR>
	 *   取得データに１件でも作業中が含まれている時は、1:他端末作業中をメッセージとしてNotFoundExceptionを返します。<BR>
	 *   全件完了している時は、2:作業完了済みをメッセージとしてNotFoundExceptionを返します。<BR>
	 *   <DIR>
	 *     荷主コード、出荷予定日付、出荷先コード = パラメータより取得<BR>
	 *     作業区分=05(出荷)<BR>
	 *     状態フラグ=9(削除)以外<BR>
	 *     作業開始フラグ=1(開始済)<BR>
	 *   </DIR></LI>
	 * <LI>集約の処理を呼び出します。作業情報、予定情報の状態を更新する処理を行います。</LI>
	 * </OL>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @param	scanCode		スキャン商品コード
	 * @param	convertedJanCode	ITFtoJAN変換されたJANコード
	 * @return	出荷予定情報  作業情報エンティティの配列<BR>
	 * @throws LockTimeOutException   他の処理がロック中でロックの獲得に失敗した場合に通知されます。
	 * @throws NotFoundException      該当データが見つからない場合に通知されます。
	 * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
	 * @throws ReadWriteException     データベースとの接続で異常が発生した場合に通知されます。
	 * @throws OverflowException      数量がオーバーフローした場合に通知されます。
	 * @throws IllegalAccessException 指定されたクラスの定義にアクセスできない場合に通知されます。
	*/
	public WorkingInformation shippingStartOnCustomer(
		String consignorCode,
		String planDate,
		String customerCode,
		String rftNo,
		String workerCode,
		String scanCode,
		String convertedJanCode)
		throws LockTimeOutException, NotFoundException, InvalidDefineException, ReadWriteException, OverflowException, IllegalAccessException
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

		// 作業可能な出荷予定データを検索します。
		workableData = shippingOperate.getWorkableData(
				consignorCode,
				planDate,
				customerCode,
				rftNo,
				workerCode,
				scanCode,
				convertedJanCode);
		if (workableData == null || workableData.length == 0)
		{
			// 作業可能なデータが見つからなかった時の処理
			WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
			WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
			//-----------------
			// 作業情報の検索(２回目)
			//-----------------
			// 作業区分が 出荷 を対象にする。
			skey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
			// 状態フラグが「削除」以外を対象にする。
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
			// 作業開始フラグが「1:開始済」を対象にする。
			skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			skey.setConsignorCode(consignorCode);
			skey.setPlanDate(planDate);
			skey.setCustomerCode(customerCode);
			skey.setItemCode(scanCode, "=", "(", "", "OR");
			skey.setItemCode(convertedJanCode, "=", "", "", "OR");
			skey.setItf(scanCode, "=", "", "", "OR");
			skey.setBundleItf(scanCode, "=", "", ")", "AND");
			skey.setItemCodeOrder(1, true);
			workableData = (WorkingInformation[]) pObj.find(skey);

			// 該当データが見つからない時は、状態フラグを 8:該当データなし でリターン
			if (workableData.length == 0)
			{
				NotFoundException e = new NotFoundException(RFTId5530.ANS_CODE_NULL);
				throw e;
			}
			// 該当データに、作業中or開始済データが見つかった時は、状態フラグを 1:他端末作業中 でリターン
			for (int i = 0; i < workableData.length; i ++)
			{
				if (workableData[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
					|| workableData[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
				{
					throw new NotFoundException(RFTId5530.ANS_CODE_WORKING);
				}
			}

			// 該当データに、作業中or開始済データが見つからなかった時は、状態フラグを 2:作業完了済み でリターン
			throw new NotFoundException(RFTId5530.ANS_CODE_COMPLETION);
		}

		// 作業集約処理を行う。
		WorkingInformation target = shippingOperate.collectOneShippingData(workableData, workerCode, rftNo);
		return target;
	}


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class
