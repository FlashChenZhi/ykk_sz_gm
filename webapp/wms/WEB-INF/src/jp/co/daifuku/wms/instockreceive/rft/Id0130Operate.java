// $Id: Id0130Operate.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
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
 * 商品単位入荷検品データ要求に対するデータ処理を行ないます。<BR>
 * 検品データの取得し、そのデータを作業中に更新します。また、検品予定ファイルの作成を行います。<BR>
 * Id0130Processから呼び出されるビジネスロジックが実装されます。<BR>
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
public class Id0130Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	/**
	 * 処理名（登録処理名、最終更新処理名用）
	 */
	private static final String PROCESS_NAME = "ID0130";
	private static final String CLASS_NAME = "Id0130Operate";

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

	/**
	 * 商品単位入荷検品データ要求に対するデータを取得し、作業可能であれば、そのデータの状態を作業中にします。<BR>
	 * コミットは本メソッド呼び出し元で行います。<BR>
	 * 最初に作業データを検索します。
	 * 検索条件はInstockReceiveOperateから取得します。<BR>
	 * 
	 * データが取得できない場合、RFTに戻す状態フラグを決定する為に、次の条件で作業データを検索します。<BR>
	 *  データの取得が出来ない場合は、8:該当データ無しをメッセージとしてNotFoundExceptionを返します。<BR>
	 *  取得データに１件でも作業中が含まれている時は、1:他端末作業中をメッセージとしてNotFoundExceptionを返します。<BR>
	 *  全件完了している時は、2:作業完了済みをメッセージとしてNotFoundExceptionを返します。<BR>
	 * <DIR>
	 *   荷主コード、入荷予定日付、仕入先コード = パラメータより取得<BR>
	 *   作業区分 = 入荷<BR>
	 *   TC/DC区分 = DC またはクロスTC<BR>
	 *   状態フラグ = 9(削除)以外<BR>
	 *   作業開始フラグ = 1(開始済)<BR>
	 * </DIR>
	 * 作業情報を集約します。(collectInstockReceiveDataメソッド)<BR>
	 * 集約された作業情報を返します。<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード<BR>
	 * @param	planDate		予定日<BR>
	 * @param	supplierCode	仕入先コード<BR>
	 * @param	itemCode		スキャン商品コード<BR>
	 * @param	convertedJanCode	ITFtoJAN変換されたJANコード<BR>
	 * @param	rftNo			RFT号機番号<BR>
	 * @param	workerCode		作業者コード<BR>
	 * @return				集約された入荷予定情報（作業情報エンティティの配列）<BR>
	 * @throws NotFoundException 作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws IllegalAccessException	オブジェクトの生成に失敗した場合に通知されます。
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	*/
	public WorkingInformation startReceivingByItem(
		String consignorCode,
		String planDate,
		String supplierCode,
		String itemCode,
		String convertedJanCode,
		String rftNo,
		String workerCode)
		throws NotFoundException, InvalidDefineException, ReadWriteException, LockTimeOutException, IllegalAccessException, OverflowException
	{
		// WorkingInfoを検索した結果を保持する変数
		WorkingInformation[] workinfo = null;

		//-----------------
		// 作業情報の検索(１回目)
		//-----------------
		// InstockReceiveOperateクラスのインスタンスを生成
		InstockReceiveOperate instockOperate = (InstockReceiveOperate) PackageManager.getObject("InstockReceiveOperate");
		instockOperate.setConnection(wConn);
		instockOperate.setProcessName(PROCESS_NAME);
		
		workinfo = getWorkableData(
				consignorCode,
				planDate,
				supplierCode,
				rftNo,
				workerCode,
				itemCode,
				convertedJanCode);
		
		if (workinfo == null || workinfo.length == 0)
		{
			WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
			WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

			// １回目の検索でデータが該当しない時の処理
			skey.KeyClear();
			//-----------------
			// 作業情報の検索(２回目)
			//-----------------
			// 作業区分が 入荷 を対象にする。
			skey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
			// 状態フラグが「削除」以外を対象にする。
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
			// DCまたはクロスTC
			skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
			skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
			// 作業開始フラグが「1:開始済」を対象にする。
			skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			skey.setConsignorCode(consignorCode);
			skey.setPlanDate(planDate);
		    if (supplierCode != null)
		    {
		    	skey.setSupplierCode(supplierCode);
		    }
			skey.setItemCode(itemCode, "=", "(", "", "OR");
			skey.setItemCode(convertedJanCode, "=", "", "", "OR");
			skey.setItf(itemCode, "=", "", "", "OR");
			skey.setBundleItf(itemCode, "=", "", ")", "AND");
			skey.setItemCodeOrder(1, true);
			workinfo = (WorkingInformation[]) pObj.find(skey);
			// 該当データが見つからない時は、状態フラグを 8:該当データなし でリターン
			if (workinfo.length == 0)
			{
				NotFoundException e = new NotFoundException(RFTId5130.ANS_CODE_NULL);
				throw e;
				//return Id0130Process.ANS_CODE_NULL;
			}
			// 該当データに、作業中もしくは開始済データが見つかった時は、状態フラグを 1:他端末作業中 でリターン
			for (int i = 0; i < workinfo.length; i++)
			{
				if (workinfo[i].getStatusFlag()
					.equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
					|| workinfo[i].getStatusFlag().equals(
						WorkingInformation.STATUS_FLAG_START))
				{
					NotFoundException e = new NotFoundException(RFTId5130.ANS_CODE_WORKING);
					throw e;
				}
			}
			// 該当データに、作業中もしくは開始済データが見つからなかった時は、状態フラグを 2:作業完了済み でリターン
			NotFoundException e = new NotFoundException(RFTId5130.ANS_CODE_COMPLETION);
			throw e;
		}

		// 仕入先がひとつだけかどうかをチェックする
		String currentSupplierCode = workinfo[0].getSupplierCode();
		for (int i = 1; i < workinfo.length; i ++)
		{
		    if (! currentSupplierCode.equals(workinfo[i].getSupplierCode()))
		    {
		        // 複数存在した場合はNotFoundExceptionを投げて、
		        // 呼び出し側で処理する
		        throw new NotFoundException(RFTId5130.ANS_CODE_SUPPLIERS);
		    }
		}
		
		// 作業集約処理
		WorkingInformation[] collectedData = null;
		collectedData = instockOperate.collectInstockReceiveData(workinfo, workerCode, rftNo);

		return collectedData[0];
	}
	
	// Package methods -----------------------------------------------
	// Protected methods ---------------------------------------------
	/**
	 * 商品単位入荷検品開始要求に対する作業可能データを検索します。
	 * 検索の基本条件は以下のとおりとします。
	 * <DIR>
	 *   荷主コード、予定日、仕入先コード = パラメータより取得<BR>
	 *   作業区分=01(入荷)<BR>
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
	 * @param	supplierCode	仕入先コード
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @param	scanCode		スキャン商品コード
	 * @param	convertedJanCode	ITFtoJAN変換されたJANコード
	 * @return	入荷予定情報  作業情報エンティティの配列<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws IllegalAccessException オブジェクトの生成に失敗した場合に通知されます。
	 */
	protected WorkingInformation[] getWorkableData(
			String consignorCode,
			String planDate,
	        String supplierCode,
			String rftNo,
			String workerCode,
			String scanCode,
			String convertedJanCode) throws ReadWriteException, LockTimeOutException, IllegalAccessException
	{
		try
		{
			// WorkingInfoを検索した結果を保持する変数
			WorkingInformation[] workinfo = null;

			WorkingInformationSearchKey skey = null;
			WorkingInformationHandler obj = new WorkingInformationHandler(wConn);
			//-----------------
			// 作業情報の検索(１回目)
			// スキャンされたコードをJANコードとして検索を行う
			//-----------------
			// InstockReceiveOperateクラスのインスタンスを生成
			InstockReceiveOperate instockOperate = (InstockReceiveOperate) PackageManager.getObject("InstockReceiveOperate");
			instockOperate.setConnection(wConn);
			instockOperate.setProcessName(PROCESS_NAME);
			
			skey = instockOperate.getConditionOfInstockBySupplier(
			        planDate,
			        consignorCode,
			        supplierCode,
			        scanCode,
			        workerCode,
			        rftNo);
			skey.setItemCodeOrder(1, true);
			skey.setItfOrder(2, true);
			skey.setBundleItfOrder(3, true);
			skey.setCollectJobNoOrder(4, true);
			int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;
			
			workinfo = (WorkingInformation[]) obj.findForUpdate(skey, timeout);
			// WorkingInfoを検索した結果を保持する変数

			skey.setItemCodeOrder(1, true);
			skey.setCollectJobNoOrder(2, true);

			if (workinfo != null && workinfo.length > 0)
			{
				return workinfo;
			}
			
			//-----------------
			// 作業情報の検索(２回目)
			// スキャンされたコードをケースITFとして検索を行う
			//-----------------
			skey = instockOperate.getConditionOfInstockBySupplier(
			        planDate,
			        consignorCode,
			        supplierCode,
			        null,
			        workerCode,
			        rftNo);
			skey.setItf(scanCode);
			skey.setItemCodeOrder(1, true);
			skey.setItfOrder(2, true);
			skey.setBundleItfOrder(3, true);
			skey.setCollectJobNoOrder(4, true);
			workinfo = (WorkingInformation[]) obj.findForUpdate(skey, timeout);

			if (workinfo != null && workinfo.length > 0)
			{
				return workinfo;
			}

			//-----------------
			// 作業情報の検索(３回目)
			// スキャンされたコードをボールITFとして検索を行う
			//-----------------
			skey = instockOperate.getConditionOfInstockBySupplier(
			        planDate,
			        consignorCode,
			        supplierCode,
			        null,
			        workerCode,
			        rftNo);
			skey.setBundleItf(scanCode);
			skey.setItemCodeOrder(1, true);
			skey.setItfOrder(2, true);
			skey.setBundleItfOrder(3, true);
			skey.setCollectJobNoOrder(4, true);
			workinfo = (WorkingInformation[]) obj.findForUpdate(skey, timeout);

			if (workinfo != null && workinfo.length > 0)
			{
				return workinfo;
			}

			if (! StringUtil.isBlank(convertedJanCode))
			{
				//-----------------
				// 作業情報の検索(４回目)
				// ITFtoJAN変換されたコードがセットされている場合、
				// それをJANコードとして検索を行う
				//-----------------
				skey = instockOperate.getConditionOfInstockBySupplier(
				        planDate,
				        consignorCode,
				        supplierCode,
				        convertedJanCode,
				        workerCode,
				        rftNo);
				skey.setItemCodeOrder(1, true);
				skey.setItfOrder(2, true);
				skey.setBundleItfOrder(3, true);
				skey.setCollectJobNoOrder(4, true);
				workinfo = (WorkingInformation[]) obj.findForUpdate(skey, timeout);

				if (workinfo != null && workinfo.length > 0)
				{
					return workinfo;
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

	// Private methods -----------------------------------------------
}
//end of class
