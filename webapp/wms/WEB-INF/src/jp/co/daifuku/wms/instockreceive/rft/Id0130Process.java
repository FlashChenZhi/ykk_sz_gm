// $Id: Id0130Process.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import java.sql.SQLException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.TcdcFlag;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : Y.Taki <BR>
 * Maker :  E.Takeda <BR>
 * <BR>
 * RFTからの商品単位入荷検品開始要求(ID0130)に対する処理を行います。 <BR>
 * Id0130Operateクラスの提供する機能を使用し、作業情報から該当する
 * 入荷予定情報を検索し、RFTに送信する応答電文を生成します。 <BR>
 * <BR>
 * 商品単位入荷検品開始要求処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   ID0130Operateクラスの機能で作業情報を検索し、入荷検品データを取得します。 <BR>
 * 	 また、InstockReceiveOperateクラスの機能で残作業数を取得します。<BR>
 *   正常時は入荷検品データから送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 *   応答フラグが0：正常の場合、RFT管理情報の電文項目を生成した応答電文の内容で更新します。<BR>
 *   [更新条件]<BR>
 *   <UL><LI>RFTNo</LI></UL>
 *   [更新内容]<BR>
 *   <UL><LI>電文項目：生成した応答電文</LI>
 *       <LI>最終更新日時：システム日時</LI>
 *       <LI>最終更新処理名：ID0130</LI>
 *   </UL>
 *   更新中にエラーが発生した場合はログを出力します。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0130Process extends IdProcess
{
	// Class fields----------------------------------------------------
	/**
	 * クラス名を表すフィールド
	 */
	private static final String CLASS_NAME = "Id0130Process";
	
	/**
	 * 更新処理名
	 */
	private static final String PROCESS_NAME = "ID0130";
	
	// Class variables -----------------------------------------------
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $";
	}

	// Constructors --------------------------------------------------
	
	// Public methods ------------------------------------------------
	/**
	 * 商品単位入荷検品開始要求(ID0130)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5130)をバイト列で作成します。<BR>
	 * <BR>
	 * 最初に、日次処理中で無いことを確認します。<BR>
	 * 日次処理中の場合は、日次処理中のエラーを返します。<BR>
	 * 次に、作業者別実績の対象レコードがある事を確認します。無かった場合は、新規作成します。<BR>
	 * <BR>
	 * 要求のあった条件で作業情報を検索し、入荷検品データを取得します。<BR>
	 * 入荷総アイテム数、入荷残アイテム数を取得します。<BR>
	 * 正常時は入荷検品データから送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
     * 応答フラグが0：正常の場合、RFT管理情報の電文項目を生成した応答電文の内容で更新します。<BR>
     * [更新条件]<BR>
     * <UL><LI>RFTNo</LI></UL>
     * [更新内容]<BR>
     * <UL><LI>電文項目:生成した応答電文</LI>
     *     <LI>最終更新日時：システム日時</LI>
     *     <LI>最終更新処理名：ID0130</LI>
     * </UL>
     * 
     * 更新中にエラーが発生した場合はログを出力します。<BR>
	 * 
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0130 rftId0130 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5130 rftId5130 = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0130 = (RFTId0130) PackageManager.getObject("RFTId0130");
			rftId0130.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5130 = (RFTId5130) PackageManager.getObject("RFTId5130");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*130", e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0130.getRftNo();
		
		// 受信電文から作業者コードを取得
		String workerCode = rftId0130.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0130.getConsignorCode();

		// 受信電文から入荷予定日を取得
		String planDate = rftId0130.getPlanDate();

		// 受信電文から仕入先コードを取得
		String supplierCode = rftId0130.getSupplierCode();
		if (supplierCode.equals(""))
		{
		    supplierCode = null;
		}
		// 受信電文の入力仕入先コードを取得
		String inputSupplierCode = rftId0130.getSupplierCode();
		
		// 受信電文から商品コードを取得
		String itemCode = rftId0130.getScanItemCode1();
		// 受信電文からスキャン商品コード2を取得
		String convertedJanCode = rftId0130.getScanItemCode2();

		// 応答フラグを保持する変数
		String ansCode = RFTId5130.ANS_CODE_NORMAL;
		// エラー詳細を保持する変数
		String errDetails = RFTId5130.ErrorDetails.NORMAL;

		// 入荷作業予定情報
		WorkingInformation instockWorkData = null;
		
		// 総アイテム数
		int totalCount = 0;

		// 残アイテム数
		int remainingCount = 0;
		
		// TC/DC区分
		String tcdcBySupplier = "";

		try
		{
			if(DisplayText.isPatternMatching(itemCode))
			{
				throw new NotFoundException(RFTId5130.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(convertedJanCode))
			{
				throw new NotFoundException(RFTId5130.ANS_CODE_NULL);
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
				ansCode = RFTId5130.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//-----------------
				// 作業者別実績の存在チェック
				//-----------------
				WorkerResult[] workerResult = baseOperate.getWorkerResult(
				        WorkingInformation.JOB_TYPE_INSTOCK,
				        workerCode,
				        rftNo);
				if( workerResult.length == 0 )
				{
					// 作業者実績の新規作成

					baseOperate.createWorkerResult(
					        WorkingInformation.JOB_TYPE_INSTOCK,
					        workerCode,
					        rftNo);
					wConn.commit();

				}
				// Id0130Operateのインスタンスを生成
				Id0130Operate id0130Operate =
				    (Id0130Operate) PackageManager.getObject("Id0130Operate");
				id0130Operate.setConnection(wConn);

				// Id0130OperateのstartReceivingByItemメソッドを使って、作業情報にあるデータを取得する
				instockWorkData = id0130Operate.startReceivingByItem(
				        consignorCode,
						planDate, 
						supplierCode,
						itemCode,
						convertedJanCode,
						rftNo,
						rftId0130.getWorkerCode());
				
				// InstockReceiveOperateのインスタンスを生成
				InstockReceiveOperate instockOperate =
				    (InstockReceiveOperate) PackageManager.getObject("InstockReceiveOperate");
				instockOperate.setConnection(wConn);

				// 総数を取得する
				totalCount = instockOperate.countTotalItem(
				        planDate,
				        consignorCode,
				        supplierCode,
				        null);
				
				// 残数を取得する
				remainingCount = instockOperate.countRemainingItem(
				        planDate,
				        consignorCode,
				        supplierCode,
				        null,
				        workerCode,
				        rftNo) - 1;
				
				tcdcBySupplier = TcdcFlag.convertTcdcBySupplier(instockWorkData.getTcdcFlag());			
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
			if (! RFTId5130.checkAnsCode(ansCode))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5130.ANS_CODE_ERROR;
				errDetails = RFTId5130.ErrorDetails.NULL;				
			}
			else if(ansCode.equals(RFTId5130.ANS_CODE_ERROR))
			{
				errDetails = RFTId5130.ErrorDetails.NULL;				
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
			ansCode = RFTId5130.ANS_CODE_MAINTENANCE;
		}
		// オーバーフローが発生した場合
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
							" SupplierCode:" + supplierCode +
							" ItemCode:" + itemCode +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
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
			ansCode = RFTId5130.ANS_CODE_ERROR;
			errDetails = RFTId5130.ErrorDetails.COLLECTION_OVERFLOW;
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
			ansCode = RFTId5130.ANS_CODE_ERROR;
			errDetails = RFTId5130.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5130.ANS_CODE_ERROR;
			errDetails = RFTId5130.ErrorDetails.INSTACIATE_ERROR;
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
			ansCode = RFTId5130.ANS_CODE_ERROR;
			errDetails = RFTId5130.ErrorDetails.DB_UNIQUE_KEY_ERROR;
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
			ansCode = RFTId5130.ANS_CODE_ERROR;
			errDetails = RFTId5130.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5130.ANS_CODE_ERROR;
			errDetails = RFTId5130.ErrorDetails.PARAMETER_ERROR;
        }
		catch (InvalidStatusException e)
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
			ansCode = RFTId5130.ANS_CODE_ERROR;
			errDetails = RFTId5130.ErrorDetails.DB_INVALID_VALUE;
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
			ansCode = RFTId5130.ANS_CODE_ERROR;
			errDetails = RFTId5130.ErrorDetails.INTERNAL_ERROR;
		}
		// 応答電文の作成
		// STX
		rftId5130.setSTX();
		// SEQ
		rftId5130.setSEQ(0);
		// ID
		rftId5130.setID(RFTId5130.ID);
		// RFT送信時間
		rftId5130.setRftSendDate(rftId0130.getRftSendDate());
		// SERVER送信時間
		rftId5130.setServSendDate();
		// RFT号機
		rftId5130.setRftNo(rftNo);

		// 担当者コード
		rftId5130.setWorkerCode(workerCode);
		// 荷主コード
		rftId5130.setConsignorCode(consignorCode);
		// 入力仕入先コード
		rftId5130.setInputSupplierCode(inputSupplierCode);
		// 出荷予定日
		rftId5130.setPlanDate(planDate);
		if (ansCode.equals(RFTId5130.ANS_CODE_NORMAL))
		{
			// 仕入先コード
			rftId5130.setSupplierCode(instockWorkData.getSupplierCode());
			// 荷主名
			rftId5130.setConsignorName(instockWorkData.getConsignorName());
			// 仕入先名
			rftId5130.setSupplierName(instockWorkData.getSupplierName1());
			// 伝票番号
			rftId5130.setTicketNo(instockWorkData.getInstockTicketNo());
			// 伝票行番号
			rftId5130.setTicketLineNo(Integer.toString(instockWorkData.getInstockLineNo()));
			// 集約作業No
			rftId5130.setCollectJobNo(instockWorkData.getCollectJobNo());
			// 商品コード
			rftId5130.setJANCode(instockWorkData.getItemCode());
			// ボールITF
			rftId5130.setBundleITF(instockWorkData.getBundleItf());
			// ケースITF
			rftId5130.setITF(instockWorkData.getItf());
			rftId5130.setItemName(instockWorkData.getItemName1());
			rftId5130.setBundleEnteringQty(instockWorkData.getBundleEnteringQty());
			rftId5130.setEnteringQty(instockWorkData.getEnteringQty());
			rftId5130.setLotNo(instockWorkData.getLotNo());
			rftId5130.setUseByDate(instockWorkData.getUseByDate());
			rftId5130.setPlanQty(instockWorkData.getPlanEnableQty());
			rftId5130.setResultQty(instockWorkData.getResultQty());
		}
		else
		{
		    if (supplierCode == null)
		    {
		        supplierCode = "";
		    }
			// 仕入先コード
			rftId5130.setSupplierCode(supplierCode);
			// 荷主名
			rftId5130.setConsignorName("");
			// 仕入先名
			rftId5130.setSupplierName("");
			// 伝票番号
			rftId5130.setTicketNo("");
			// 伝票行番号
			rftId5130.setTicketLineNo("0");
			// 集約作業No
			rftId5130.setCollectJobNo("");
			// 商品コード
			rftId5130.setJANCode("");
			// ボールITF
			rftId5130.setBundleITF("");
			// ケースITF
			rftId5130.setITF("");
			rftId5130.setItemName("");
			rftId5130.setBundleEnteringQty(0);
			rftId5130.setEnteringQty(0);
			rftId5130.setLotNo("");
			rftId5130.setUseByDate("");
			rftId5130.setPlanQty(0);
			rftId5130.setResultQty(0);
		}
		rftId5130.setTCDCFlag(tcdcBySupplier);
		rftId5130.setTotalItemCount(totalCount);
		rftId5130.setRemainingItemCount(remainingCount);
		rftId5130.setDivideFlag("0");
		rftId5130.setQualityClassification("0");
		
		// 応答フラグ
		rftId5130.setAnsCode(ansCode);
		// エラー詳細
		rftId5130.setErrDetails(errDetails);
		
		// ETX
		rftId5130.setETX();

		// 応答電文を獲得する。
		rftId5130.getSendMessage(sdt);
		
		// 応答フラグが0:正常の時応答電文をRFT管理情報の電文項目に保存する
		try
		{
			if (ansCode.equals(RFTId5130.ANS_CODE_NORMAL))
			{
				rftId5130.saveResponseId(rftNo, PROCESS_NAME, wConn);
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

