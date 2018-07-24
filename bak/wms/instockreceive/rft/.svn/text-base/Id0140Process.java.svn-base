// $Id: Id0140Process.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import java.io.IOException;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;


/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : Y.Taki <BR>
 * Maker : E.Takeda  <BR>
 * <BR>
 * RFTからの出荷先別入荷検品開始要求(ID0140)に対する処理を行います。 <BR>
 * Id0140Operateクラスの提供する機能を使用し、作業情報から該当する
 * 入荷予定情報を検索し、RFTに送信する応答電文を生成します。 <BR>
 * <BR>
 * 出荷先別入荷検品開始要求処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   検品予定ファイル名を作成します。<BR>
 *   ID0140Operateクラスの機能で作業情報を検索し、入荷検品データを取得します。 <BR>
 *   正常時は、入荷検品データから検索結果を取得し作業ファイルを作成します。<BR>
 *   同時に、作業ファイルの内容で作業中データ保存情報(DNWorkingData)にレコードを作成します。<BR>
 *  <p>
 *  作業中データ保存情報を作成します。すでにデータが存在する場合は更新します。<BR>
 *  [更新条件]
 *  <UL><LI>RFTNo</LI>
 *      <LI>行No</LI>
 *  </UL>
 *  [更新・作成内容]
 *  <UL><LI>RFTNo(作成の場合）</LI>
 *  	<LI>ファイル名:ID5140.txt</Li>
 *  	<LI>行No</LI>
 *  	<LI>作業データ（1データ）</LI>
 *  </UL>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 *   正常に処理が終了した場合は生成した応答電文の内容でRFT管理情報(DNRft)の電文項目を更新します。<BR>
 * [更新条件]
 *  <UL><LI>RFTNo</LI></UL>
 * [更新内容]
 *  <UL><LI>電文：生成した応答電文</LI>
 *      <LI>最終更新日時：システム日時</LI>
 *      <LI>最終更新処理名：ID0140</LI>
 *  </UL>
 *   更新中にエラーが発生した場合は、ログを出力し作成します。<BR>
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
public class Id0140Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0140Process";
	
	private static final String PROCESS_NAME = "ID0140";
	
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
	 * 出荷先別入荷検品開始要求(ID0140)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5140)をバイト列で作成します。<BR>
	 * <BR>
	 * 最初に、日次処理中で無いことを確認します。<BR>
	 * 日次処理中の場合は、日次処理中のエラーを返します。<BR>
	 * 次に、作業者別実績の対象レコードがある事を確認します。<BR>
	 * 無かった場合は、新規作成します。<BR>
	 * <BR>
	 * 要求のあった条件で作業情報を検索し、作業予定ファイルを作成します。<BR>
	 * 同時に、作業ファイルの内容で作業中データ保存情報にレコードを作成します。<BR>
	 *  <p>
	 *  作業中データ保存情報を作成します。すでにデータが存在する場合は更新します。<BR>
	 *  [更新条件]
	 *  <UL><LI>RFTNo</LI>
	 *      <LI>行No</LI>
	 *  </UL>
	 *  [更新・作成内容]
	 *  <UL><LI>RFTNo(作成の場合）</LI>
	 *  	<LI>ファイル名:ID5140.txt</LI>
	 *  	<LI>行No</LI>
	 *  	<LI>作業データ（1データ）</LI>
	 *  </UL>
	 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
	 *   正常に処理が終了した場合は生成した応答電文の内容でRFT管理情報の電文項目を更新します。<BR>
	 * [更新条件]
	 *  <UL><LI>RFTNo</LI></UL>
	 * [更新内容]
	 *  <UL><LI>電文：生成した応答電文</LI>
	 *      <LI>最終更新日時：システム日時</LI>
	 *      <LI>最終更新処理名：ID0140</LI>
	 *  </UL>
	 *   更新中にエラーが発生した場合は、ログを出力します。<BR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0140 rftId0140 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5140 rftId5140 = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0140 = (RFTId0140) PackageManager.getObject("RFTId0140");
			rftId0140.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5140 = (RFTId5140) PackageManager.getObject("RFTId5140");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*140", e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0140.getRftNo();
		
		String workerCode = rftId0140.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0140.getConsignorCode();

		// 受信電文から入荷予定日を取得
		String planDate = rftId0140.getPlanDate();

		// 受信電文から仕入先コードを取得
		String supplierCode = rftId0140.getSupplierCode();
		
		// 受信電文から出荷先コードを取得
		String customerCode = rftId0140.getCustomerCode();

		// 応答フラグを保持する変数
		String ansCode = RFTId5140.ANS_CODE_NORMAL;

		// エラー詳細を保持する変数
		String errDetails = RFTId5140.ErrorDetails.NORMAL;

		// 荷主名を保持する変数
		String consignorName = "";
		// 仕入先名を保持する変数
		String supplierName = "";
		// 出荷先名を保持する変数
		String customerName = "";

		// ファイル名を作成
		String sendpath = WmsParam.RFTSEND;
		// 送信ファイル名
		String sendFileName = sendpath + rftNo + "\\" + Id5140DataFile.ANS_FILE_NAME;
	
		// ファイルレコード数を保持する変数
		int numRecords = 0;
		
		// データファイルオブジェクト変数
		Id5140DataFile dataFile  = null;
		try
		{
			if(DisplayText.isPatternMatching(customerCode))
			{
				throw new NotFoundException(RFTId5140.ANS_CODE_NULL);
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
				ansCode = RFTId5140.ANS_CODE_DAILY_UPDATING;
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
				
				// Id0140Operateのインスタンスを生成
				Id0140Operate id0140Operate = (Id0140Operate) PackageManager.getObject("Id0140Operate");
				id0140Operate.setConnection(wConn);
				//
				WorkingInformation[] instockWorkData = null;
				// Id0140OperateのshippingStartOnCustomerメソッドを使って、作業情報にあるデータを取得
				instockWorkData = id0140Operate.startReceivingByCustomer(
				        consignorCode,
						planDate, 
						supplierCode,
						customerCode,
						rftNo,
						workerCode);

				// 出荷先別入荷検品作業データファイルを作成
				dataFile = new Id5140DataFile(sendFileName);
				dataFile.write(instockWorkData);

				// レコード数をセットする
				numRecords = instockWorkData.length;

				// 作業データファイル履歴保存
				dataFile.saveHistoryFile();

				// 作業中データを保存する。
				dataFile.saveWorkingDataFile(rftNo, sendFileName, numRecords, wConn);
				
				// 送信電文に送るデータをセット
				consignorName = instockWorkData[0].getConsignorName();
				supplierName = instockWorkData[0].getSupplierName1();
				customerName = instockWorkData[0].getCustomerName1();

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
			if (! RFTId5140.checkAnsCode(ansCode))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5140.ANS_CODE_ERROR;
				errDetails = RFTId5140.ErrorDetails.NULL;
			}
			else if (ansCode.equals(RFTId5140.ANS_CODE_ERROR))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				errDetails = RFTId5140.ErrorDetails.NULL;				
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
			ansCode = RFTId5140.ANS_CODE_MAINTENANCE;
		}
		// オーバーフローが発生した場合
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
							" SupplierCode:" + supplierCode +
							" CustomerCode:" + customerCode +
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
			ansCode = RFTId5140.ANS_CODE_ERROR;
			errDetails = RFTId5140.ErrorDetails.COLLECTION_OVERFLOW;
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
			ansCode = RFTId5140.ANS_CODE_ERROR;
			errDetails = RFTId5140.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5140.ANS_CODE_ERROR;
			errDetails = RFTId5140.ErrorDetails.INSTACIATE_ERROR;
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
			ansCode = RFTId5140.ANS_CODE_ERROR;
			errDetails = RFTId5140.ErrorDetails.DB_UNIQUE_KEY_ERROR;
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
			ansCode = RFTId5140.ANS_CODE_ERROR;
			errDetails = RFTId5140.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5140.ANS_CODE_ERROR;
			errDetails = RFTId5140.ErrorDetails.PARAMETER_ERROR;
        }
        catch (IOException e)
        {
		    // 6006020 = ファイルの入出力エラーが発生しました。{0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			try
			{
				wConn.rollback();
			}
			catch (SQLException e1)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e1);
			
			}
			// 応答フラグ：エラー
			ansCode = RFTId5140.ANS_CODE_ERROR;
			errDetails = RFTId5140.ErrorDetails.I_O_ERROR;
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
			ansCode = RFTId5140.ANS_CODE_ERROR;
			errDetails = RFTId5140.ErrorDetails.INTERNAL_ERROR;
		}
		// 応答電文の作成
		// STX
		rftId5140.setSTX();
		// SEQ
		rftId5140.setSEQ(0);
		// ID
		rftId5140.setID(RFTId5140.ID);
		// RFT送信時間
		rftId5140.setRftSendDate(rftId0140.getRftSendDate());
		// SERVER送信時間
		rftId5140.setServSendDate();
		// RFT号機
		rftId5140.setRftNo(rftNo);

		// 担当者コード
		rftId5140.setWorkerCode(workerCode);
		// 荷主コード
		rftId5140.setConsignorCode(consignorCode);
		// 出荷予定日
		rftId5140.setPlanDate(planDate);
		// 仕入先コード
		rftId5140.setSupplierCode(supplierCode);
		// 出荷先コード
		rftId5140.setCustomerCode(customerCode);
		// 荷主名
		rftId5140.setConsignorName(consignorName);
		// 仕入先名
		rftId5140.setSupplierName(supplierName);
		// 出荷先名
		rftId5140.setCustomerName(customerName);
		// 出荷先別入荷検品ファイル名
		rftId5140.setAnsFileName(sendFileName);
		// ファイルレコード数
		rftId5140.setFileRecordNumber(numRecords);
		// 応答フラグ
		rftId5140.setAnsCode(ansCode);
		// エラー詳細
		rftId5140.setErrDetails(errDetails);
		
		// ETX
		rftId5140.setETX();

		// 応答電文を獲得する。
		rftId5140.getSendMessage(sdt);
		
		// 応答フラグが0:正常の時応答電文をRFT管理情報の電文項目に保存する
		try
		{
			if(ansCode.equals(RFTId5140.ANS_CODE_NORMAL))
			{
				rftId5140.saveResponseId(rftNo, PROCESS_NAME, wConn);	
				wConn.commit();
			}
			else
			{
				// 履歴ファイルを削除する
				if(dataFile != null)
				{
					try
					{
						dataFile.deleteHistoryFile();						
					}
					catch(Exception e)
					{
					}
				}
			}
		}
		catch (Exception e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
				// 作業中データを全て削除する。
				RFTId5140.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
				wConn.commit();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
				wConn.rollback();
			}
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

