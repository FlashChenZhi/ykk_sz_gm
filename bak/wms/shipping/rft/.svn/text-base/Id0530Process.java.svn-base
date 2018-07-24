// $Id: Id0530Process.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.WorkingInformation;

/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * RFTからの商品単位出荷検品開始要求(ID0530)に対する処理を行います。 <BR>
 * Id0530Operateクラスの提供する機能を使用し、作業情報から該当する
 * 出荷予定情報を検索し、RFTに送信する応答電文を生成します。 <BR>
 * <BR>
 * 商品単位出荷検品開始要求処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <OL>
 *   <LI>受信データから、必要な情報を取得します。</LI>
 *   <LI>ID0530Operateクラスの機能で作業情報を検索し、
 *       データの状態(応答フラグ)と出荷検品データを取得します。 </LI>
 *   <LI>正常時は、出荷検品データから検索結果を送信バッファにセットします。</LI>
 *   <LI>正常時は、生成した応答電文の内容でRFT作業情報の電文項目を更新します。<BR>
 *       [更新条件]
 *       <UL><LI>RFTNo</LI></UL>
 *       [更新内容]
 *       <UL><LI>応答電文：生成した応答電文</LI>
 *           <LI>最終更新日時：システム日時</LI>
 *           <LI>最終更新処理名：ID0530</LI>
 *       </UL>
 *   	 更新中にエラーが発生した場合はログを出力します。</LI>
 * </OL>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0530Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0530Process";
	
	private static final String PROCESS_NAME = "ID0530";

	// Class variables -----------------------------------------------
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $";
	}
	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public Id0530Process()
	{
		super();
	}
	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0530Process(Connection conn)
	{
		super();
		wConn = conn;
	}
	// Public methods ------------------------------------------------
	/**
	 * 商品単位出荷検品開始要求(ID0530)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5530)をバイト列で作成します。<BR>
	 * <BR>
	 * <OL>
	 *   <LI>日次処理中で無いことを確認します。
	 *       日次処理中の場合は、日次処理中のエラーを返します。</LI>
	 *   <LI>作業者別実績の対象レコードがある事を確認します。
	 *       無かった場合は、新規作成します。</LI>
	 *   <LI>電文から取得した値に基づいて作業情報を検索し、出荷検品作業予定を取得します。</LI>
	 *   <LI>作業予定を送信バッファにセットします。</LI>
	 *   <LI>正常時は、生成した応答電文の内容でRFT作業情報の電文項目を更新します。<BR>
	 *       更新中にエラーが発生した場合はログを出力します。</LI>
	 * </OL>
	 * <BR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0530 rftId0530 = null;
		// 送信電文作成用のインスタンスを生成
		RFTId5530 rftId5530 = null;
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0530 = (RFTId0530) PackageManager.getObject("RFTId0530");
			rftId0530.setReceiveMessage(rdt);
			// 送信電文作成用のインスタンスを生成
			rftId5530 = (RFTId5530) PackageManager.getObject("RFTId5530");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*530", e.getMessage());
			throw e;
		}
		// 受信電文からRFT号機を取得
		String rftNo = rftId0530.getRftNo();
		// 受信電文から荷主コードを取得
		String consignorCode = rftId0530.getConsignorCode();
		// 受信電文から出荷予定日を取得
		String planDate = rftId0530.getShippingPlanDate();
		// 受信電文から出荷先コードを取得
		String customerCode = rftId0530.getCustomerCode();
		// 受信電文からスキャン商品コード1を取得
		String itemCode = rftId0530.getScanItemCode1();
		// 受信電文からスキャン商品コード2を取得
		String convertedJanCode = rftId0530.getScanItemCode2();
		// 応答フラグを保持する変数
		String ansCode = RFTId5530.ANS_CODE_NORMAL;
		// エラー詳細を保持する変数
		String errDetails = RFTId5532.ErrorDetails.NORMAL;
		// 残アイテム数を保持する変数
		int remainingItemCount = 0;
		// 総アイテム数を保持する変数
		int totalItemCount = 0;

		// 取得した出荷予定データを保持する変数
		WorkingInformation shippingWorkData = null;

		try
		{
			if(DisplayText.isPatternMatching(itemCode))
			{
				throw new NotFoundException(RFTId5530.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(convertedJanCode))
			{
				throw new NotFoundException(RFTId5530.ANS_CODE_NULL);
			}


			// BaseOperateのインスタンスを生成
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);
			//-----------------
			// 日次処理中チェック
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				// 状態フラグを 7:日次更新処理中 でリターン
				ansCode = RFTId5530.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//-----------------
				// 作業者別実績の存在チェック
				//-----------------
				WorkerResult[] workerResult =
					baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_SHIPINSPECTION,
						rftId0530.getWorkerCode(),
						rftNo);
				if (workerResult.length == 0)
				{
					// 作業者実績の新規作成
					baseOperate.createWorkerResult(
						WorkingInformation.JOB_TYPE_SHIPINSPECTION,
						rftId0530.getWorkerCode(),
						rftNo);
					// commit
					wConn.commit();
				}
				// Id0062Operateのインスタンスを生成
				Id0530Operate id0530Operate = (Id0530Operate) PackageManager.getObject("Id0530Operate");
				id0530Operate.setConnection(wConn);

				// Id0530OperateのshippingStartOnCustomerメソッドを使って、作業情報にあるデータを取得
				shippingWorkData =
					id0530Operate.shippingStartOnCustomer(
						consignorCode,
						planDate,
						customerCode,
						rftNo,
						rftId0530.getWorkerCode(),
						itemCode,
						convertedJanCode);

				// 応答フラグが0の場合だけ処理を継続
				// 送信電文に送るデータをセット
				if (shippingWorkData != null)
				{
					// Id0062Operateのインスタンスを生成
					ShippingOperate shippingOperate = (ShippingOperate) PackageManager.getObject("ShippingOperate");
					shippingOperate.setConnection(wConn);

					// 残アイテム数取得
					// これから作業をするデータは除くため、1件減らす。
					remainingItemCount =
							shippingOperate.countRemainingItem(
									consignorCode,
									planDate,
									customerCode,
									rftNo,
									rftId0530.getWorkerCode()) - 1;

					// 総アイテム数取得
					totalItemCount =
							shippingOperate.countTotalItem(
									consignorCode,
									planDate,
									customerCode);
				}
				// commit
				wConn.commit();
			}
		}
		// ShippingPlanOperateのインスタンスから情報を取得できなかった場合
		catch (NotFoundException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = e.getMessage();
			if (! RFTId5530.checkAnsCode(ansCode))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5530.ANS_CODE_ERROR;
				errDetails = RFTId5530.ErrorDetails.NULL;
			}
			else if (ansCode.equals(RFTId5530.ANS_CODE_ERROR))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5530.ANS_CODE_ERROR;
				errDetails = RFTId5530.ErrorDetails.NULL;
			}
				
		}
		// 時間内にデータのロックが解除されなかった場合
		catch (LockTimeOutException e)
		{
			// SELECT FOR UPDATE タイムアウト
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5530.ANS_CODE_MAINTENANCE;
		}
		// その他のエラーがあった場合
		catch (ReadWriteException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5530.ANS_CODE_ERROR;
			errDetails = RFTId5530.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5530.ANS_CODE_ERROR;
			errDetails = RFTId5530.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (DataExistsException e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5530.ANS_CODE_ERROR;
			errDetails = RFTId5530.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		catch (SQLException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5530.ANS_CODE_ERROR;
			errDetails = RFTId5530.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (InvalidDefineException e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5530.ANS_CODE_ERROR;
			errDetails = RFTId5530.ErrorDetails.PARAMETER_ERROR;
		}
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
			" PlanDate:" + planDate +
			" CustomerCode:" + customerCode +
			" ItemCode:" + itemCode +
			" RftNo:" + rftNo +
			" WorkerCode:" + rftId0530.getWorkerCode() +"]";
			// 6026027=オーバーフロー発生のため、処理を中止しました。{0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5530.ANS_CODE_ERROR;
			errDetails = RFTId5530.ErrorDetails.COLLECTION_OVERFLOW;
		}
		catch (Exception e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5530.ANS_CODE_ERROR;
			errDetails = RFTId5530.ErrorDetails.INTERNAL_ERROR;
		}
		// 応答電文の作成
		// STX
		rftId5530.setSTX();
		// SEQ
		rftId5530.setSEQ(0);
		// ID
		rftId5530.setID(RFTId5530.ID);
		// RFT送信時間
		rftId5530.setRftSendDate(rftId0530.getRftSendDate());
		// SERVER送信時間
		rftId5530.setServSendDate();
		// RFT号機
		rftId5530.setRftNo(rftNo);
		// 担当者コード
		rftId5530.setWorkerCode(rftId0530.getWorkerCode());
		// 荷主コード
		rftId5530.setConsignorCode(rftId0530.getConsignorCode());
		// 出荷先コード
		rftId5530.setCustomerCode(rftId0530.getCustomerCode());
		if (shippingWorkData == null)
		{
			// 荷主名
			rftId5530.setConsignorName("");
			// 出荷先名
			rftId5530.setCustomerName("");
		}
		else
		{
			// 荷主名
			rftId5530.setConsignorName(shippingWorkData.getConsignorName());
			// 出荷先名
			rftId5530.setCustomerName(shippingWorkData.getCustomerName1());
		}
		// 出荷予定日
		rftId5530.setPlanDate(planDate);
		// バッチNo.
		rftId5530.setBatchNo("");
		// バッチNo.
		rftId5530.setBatch2No("");
		// 商品コード
		rftId5530.setItemCode("");
		if (shippingWorkData == null)
		{
			// 伝票No.
			rftId5530.setTicketNo("");
			// 伝票行No.
			rftId5530.setTicketLineNo("0");
			// 集約作業No.
			rftId5530.setCollectJobNo("");
			// JANコード
			rftId5530.setJanCode("");
			// ボールITF
			rftId5530.setBundleItf("");
			// ケースITF
			rftId5530.setItf("");
			// 商品名称
			rftId5530.setItemName("");
			// ボール入数
			rftId5530.setBundleEnteringQty(0);
			// ケース入数
			rftId5530.setEnteringQty(0);
			// 賞味期限
			rftId5530.setUseByDate("");
			// 出荷予定数
			rftId5530.setShippingPlanQty(0);
			// 出荷実績数
			rftId5530.setShippingResultQty(0);
		}
		else
		{
			// 伝票No.
			rftId5530.setTicketNo(shippingWorkData.getShippingTicketNo());
			// 伝票行No.
			rftId5530.setTicketLineNo(Integer.toString(shippingWorkData.getShippingLineNo()));
			// 集約作業No.
			rftId5530.setCollectJobNo(shippingWorkData.getCollectJobNo());
			// JANコード
			rftId5530.setJanCode(shippingWorkData.getItemCode());
			// ボールITF
			rftId5530.setBundleItf(shippingWorkData.getBundleItf());
			// ケースITF
			rftId5530.setItf(shippingWorkData.getItf());
			// 商品名称
			rftId5530.setItemName(shippingWorkData.getItemName1());
			// ボール入数
			rftId5530.setBundleEnteringQty(shippingWorkData.getBundleEnteringQty());
			// ケース入数
			rftId5530.setEnteringQty(shippingWorkData.getEnteringQty());
			// 賞味期限
			rftId5530.setUseByDate(shippingWorkData.getUseByDate());
			// 出荷予定数
			rftId5530.setShippingPlanQty(shippingWorkData.getPlanEnableQty());
			// 出荷実績数
			rftId5530.setShippingResultQty(shippingWorkData.getResultQty());
		}
		// 商品分類
		rftId5530.setItemCategoryCode("");
		// 単位
		rftId5530.setUnit("");
		// ロットNo.
		rftId5530.setLotNo("");
		// 製造日
		rftId5530.setManufactureDate("");
		// 品質区分
		rftId5530.setQuality("0");
		// 保留
		// 0:保留無し 1:保留
		if (shippingWorkData != null && shippingWorkData.getPendingQty() > 0)
		{
			rftId5530.setReserve("1");
		}
		else
		{
			rftId5530.setReserve("0");
		}
		// 総アイテム数
		rftId5530.setTotalItemCount(totalItemCount);
		// 残アイテム数
		rftId5530.setRemainingItemCount(remainingItemCount);
		// 応答フラグ
		rftId5530.setAnsCode(ansCode);
		// エラー詳細
		rftId5530.setErrDetails(errDetails);
		
		// ETX
		rftId5530.setETX();
		// 応答電文を獲得する。
		rftId5530.getSendMessage(sdt);

		// 応答フラグが0:正常の時応答電文を更新する
		try
		{
			if (ansCode.equals(RFTId5530.ANS_CODE_NORMAL))
			{
				rftId5530.saveResponseId(rftNo, PROCESS_NAME, wConn);
				wConn.commit();
			}
		}
		catch (Exception e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
		}
	}
	// Package methods -----------------------------------------------
	// Protected methods ---------------------------------------------
	// Private methods -----------------------------------------------
}
//end of class
