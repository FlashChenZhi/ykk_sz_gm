// $Id: Id0140Operate.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;

/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * 出荷先別入荷検品開始要求に対するデータ処理を行ないます。<BR>
 * 検品データの取得し、そのデータを作業中に更新します。また、検品予定ファイルの作成を行います。<BR>
 * Id0140Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * ！注意！<BR>
 * このクラス内で使用するWorkingInformationクラスとWorkingInformationHandlerクラスは、
 * ＲＦＴ用パッケージ(jp.co.daifuku.wms.base.rftパッケージ)を使用します。<BR>
 * (親クラスのインスタンスを子クラスにキャストできない為、ＲＦＴ用WorkingInformationHandlerを使用する必要があります。)<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0140Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	/**
	 * 処理名（登録処理名、最終更新処理名用）
	 */
	private static final String PROCESS_NAME = "ID0140";
	
	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $";
	}

	// Public methods ------------------------------------------------
	/**
	 * 出荷先別入荷検品開始要求に対するデータを取得し、作業可能であれば、そのデータの状態を作業中にします。<BR>
	 * コミットは本メソッド呼び出し元で行います。<BR>
	 * 最初に作業データを検索します。
	 * 検索条件はInstockReceiveOperateから取得します。<BR>
	 * 
	 * データが取得できない場合、RFTに戻す状態フラグを決定する為に、次の条件で作業データを検索します。<BR>
	 *  データの取得が出来ない場合は、8:該当データ無しをメッセージとしてNotFoundExceptionを返します。<BR>
	 *  取得データに１件でも作業中が含まれている時は、1:他端末作業中をメッセージとしてNotFoundExceptionを返します。<BR>
	 *  全件完了している時は、2:作業完了済みをメッセージとしてNotFoundExceptionを返します。<BR>
	 * <DIR>
	 *   荷主コード、入荷予定日付、仕入先コード、出荷先コード = パラメータより取得<BR>
	 *   作業区分 = 入荷<BR>
	 *   状態フラグ = 9(削除)以外<BR>
	 *   作業開始フラグ = 1(開始済)<BR>
	 *   TC/DC区分 = 2(TC)<BR>
	 * </DIR>
	 * 作業情報を集約します。(collectInstockReceiveData())<BR>
	 * 集約された作業情報を返します。<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	supplierCode	仕入先コード
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @return				集約された入荷予定情報（作業情報エンティティの配列）
	 * @throws NotFoundException 作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws IllegalAccessException	オブジェクトの生成に失敗した場合に通知されます。
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	*/
	public WorkingInformation[] startReceivingByCustomer(
		String consignorCode,
		String planDate,
		String supplierCode,
		String customerCode,
		String rftNo,
		String workerCode)
		throws NotFoundException, InvalidDefineException, ReadWriteException, LockTimeOutException, IllegalAccessException, OverflowException
	{
		// WorkingInfoを検索した結果を保持する変数
		WorkingInformation[] workinfo = null;

		WorkingInformationSearchKey pskey = null;
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);

		//-----------------
		// 作業情報の検索(１回目)
		//-----------------
		// RetrievalOperateクラスのインスタンスを生成
		InstockReceiveOperate instockOperate =
		    (InstockReceiveOperate) PackageManager.getObject("InstockReceiveOperate");
		instockOperate.setConnection(wConn);
		instockOperate.setProcessName(PROCESS_NAME);
		
		pskey = instockOperate.getConditionOfInstockByCustomer(
		        planDate,
		        consignorCode,
		        supplierCode,
		        customerCode,
		        workerCode,
		        rftNo);
		pskey.setItemCodeOrder(1, true);
		pskey.setItfOrder(2, true);
		pskey.setBundleItfOrder(3, true);
		pskey.setCollectJobNoOrder(4, true);
		
		workinfo = (WorkingInformation[]) pObj.findForUpdate(pskey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		
		if (workinfo.length == 0)
		{
			// １回目の検索でデータが該当しない時の処理
			pskey.KeyClear();
			//-----------------
			// 作業情報の検索(２回目)
			//-----------------
			// 作業区分が 出荷 を対象にする。
			pskey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
			// 状態フラグが「削除」以外を対象にする。
			pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
			// 作業開始フラグが「1:開始済」を対象にする。
			pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			pskey.setConsignorCode(consignorCode);
			pskey.setPlanDate(planDate);
			pskey.setSupplierCode(supplierCode);
			pskey.setCustomerCode(customerCode);
			// TC/DC区分が「TC」を対象にする。
			pskey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			pskey.setItemCodeOrder(1, true);
			workinfo = (WorkingInformation[]) pObj.find(pskey);
			// 該当データが見つからない時は、状態フラグを 8:該当データなし でリターン
			if (workinfo.length == 0)
			{
				NotFoundException e = new NotFoundException(RFTId5140.ANS_CODE_NULL);
				throw e;
			}
			// 該当データに、作業中or開始済データが見つかった時は、状態フラグを 1:他端末作業中 でリターン
			for (int i = 0; i < workinfo.length; i ++)
			{
				if (workinfo[i].getStatusFlag()
					.equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
					|| workinfo[i].getStatusFlag().equals(
						WorkingInformation.STATUS_FLAG_START))
				{
					NotFoundException e = new NotFoundException(RFTId5140.ANS_CODE_WORKING);
					throw e;
				}
			}
			// 該当データに、作業中or開始済データが見つからなかった時は、状態フラグを 2:作業完了済み でリターン
			NotFoundException e = new NotFoundException(RFTId5140.ANS_CODE_COMPLETION);
			throw e;
		}

		// 作業集約処理
		WorkingInformation[] collectedData = null;
		collectedData = instockOperate.collectInstockReceiveData(workinfo, workerCode, rftNo);

		return collectedData;
	}
	// Package methods -----------------------------------------------
	// Protected methods ---------------------------------------------
	// Private methods -----------------------------------------------
}
//end of class
