// $Id: Id0532Process.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.io.IOException;
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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;

/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * 出荷先単位出荷検品開始要求(ID0532)処理を行うためのクラスです。<BR>
 * <CODE>IdProcess</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * Id0532Operateクラスの提供する機能を使用し、作業情報から該当する
 * 出荷予定情報を検索し、RFTに送信する応答電文を生成します。 <BR>
 * <BR>
 * 出荷先単位出荷検品開始要求処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   検品予定ファイル名を作成します。<BR>
 *   ID0532Operateクラスの機能で作業情報を検索し、データの状態(応答フラグ)と出荷検品データを取得します。 <BR>
 *   正常時は、出荷検品データから検索結果をファイルを作成します。<BR>
 *   同時に作業ファイルの内容で作業中データ保存情報にレコードを作成します。<BR>
 *   作業中データ保存情報に該当データが存在した場合は更新します。存在しない場合は新規作成します。<BR>
 *   [更新条件]
 *   <UL><LI>RFTNo</LI>
 *       <LI>行No</LI>
 *   </UL>
 *   [更新・作成内容]
 *   <UL><LI>RFTNo(作成する場合）</LI>
 *       <LI>行No（作成する場合）</LI>
 *       <LI>ファイル名：ID5532.txt</LI>
 *       <LI>データ：1行分のデータ</LI>
 *   </UL>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 *   RFT作業情報の電文項目を生成した応答電文の内容で更新します。
 *   [更新条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   [更新内容]
 *   <UL><LI>応答電文：生成した応答電文</LI>
 *       <LI>最終更新日時：システム日時</LI>
 *       <LI>最終更新処理名：ID0532</LI>
 *   </UL>
 *   更新中にエラーが発生した場合はログを出力します。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0532Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0532Process";
	
	private static final String PROCESS_NAME = "ID0532";
	
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
	public Id0532Process()
	{
		super() ;
	}
	
	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0532Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	// Public methods ------------------------------------------------
	/**
	 * 出荷先単位出荷検品開始要求(ID0532)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5532)をバイト列で作成します。<BR>
	 * <BR>
	 * 最初に、日次処理中で無いことを確認します。<BR>
	 * 日次処理中の場合は、日次処理中のエラーを返します。<BR>
	 * 次に、作業者別実績の対象レコードがある事を確認します。<BR>
	 * 無かった場合は、新規作成します。<BR>
	 * <BR>
	 * 要求のあった条件で作業情報を検索し、作業予定ファイルを作成します。<BR>
	 * 同時に作業ファイルの内容で作業中データ保存情報にレコードを作成します。<BR>
	 * 応答フラグ0：正常の場合はRFT作業情報の電文項目を生成した応答電文の内容で更新します。<BR>
	 * 更新中にエラーが発生した場合はログを出力します。<BR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0532 rftId0532 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5532 rftId5532 = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0532 = (RFTId0532)PackageManager.getObject("RFTId0532");
			rftId0532.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5532 = (RFTId5532)PackageManager.getObject("RFTId5532");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*532", e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0532.getRftNo() ;

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0532.getConsignorCode() ;

		// 受信電文から出荷予定日を取得
		String shippingPlanDate = rftId0532.getShippingPlanDate() ;

		// 受信電文から出荷先コードを取得
		String customerCode = rftId0532.getCustomerCode() ;

		// 応答フラグを保持する変数
		String ansCode = RFTId5532.ANS_CODE_NORMAL;

		// エラー詳細を保持する変数
		String errDetails = RFTId5532.ErrorDetails.NORMAL;

		// 荷主名を保持する変数
		String consignorName = "" ;

		// 出荷先名を保持する変数
		String customerName = "" ;

		// ファイル名を作成
		String sendpath = WmsParam.RFTSEND;
		// 送信ファイル名
		String sendFileName = sendpath + rftNo + "\\" + Id5532DataFile.ANS_FILE_NAME;
	
		// ファイルレコード数を保持する変数
		int numRecords = 0;
		
		// データファイルオブジェクト変数
		Id5532DataFile dataFile = null;

		try
		{
			if(DisplayText.isPatternMatching(customerCode))
			{
				throw new NotFoundException(RFTId5532.ANS_CODE_NULL);
			}
			// BaseOperateのインスタンスを生成
			BaseOperate baseOperate = (BaseOperate)PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);
			
			// 日次処理中チェック
			if(baseOperate.isLoadingDailyUpdate())
			{
				// 状態フラグを 5:日次更新処理中 でリターン
				ansCode = RFTId5532.ANS_CODE_DAILY_UPDATING ;
			}
			else
			{
				// 作業者別実績の存在チェック
				WorkerResult[] workerResult = baseOperate.getWorkerResult(WorkingInformation.JOB_TYPE_SHIPINSPECTION, rftId0532.getWorkerCode(), rftNo);
				if( workerResult.length == 0 )
				{
					// 作業者実績の新規作成
					baseOperate.createWorkerResult(WorkingInformation.JOB_TYPE_SHIPINSPECTION, rftId0532.getWorkerCode(), rftNo);
					// commit
					wConn.commit();

				}
				
				// Id0060Operateのインスタンスを生成
				Id0532Operate id0532Operate = (Id0532Operate)PackageManager.getObject("Id0532Operate");
				id0532Operate.setConnection(wConn);
				//
				WorkingInformation[] shippingWorkData = null ;
				// Id0060OperateのshippingStartOnCustomerメソッドを使って、作業情報にあるデータを取得
				shippingWorkData = id0532Operate.shippingStartOnCustomer(consignorCode, 
																	shippingPlanDate, 
																	customerCode,
																	rftNo,
																	rftId0532.getWorkerCode()) ;

				// 出荷先別出荷検品作業中データファイルを作成
				dataFile = new Id5532DataFile(sendFileName);
				dataFile.write(shippingWorkData);

				// レコード数をセットする
				numRecords = shippingWorkData.length;
				
				// 作業データファイル履歴保存
				dataFile.saveHistoryFile();

				// 作業中データを保存する。
				dataFile.saveWorkingDataFile(rftNo, sendFileName, numRecords, wConn);

				// 送信電文に送るデータをセット
				consignorName = shippingWorkData[0].getConsignorName() ;
				customerName = shippingWorkData[0].getCustomerName1() ;
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
			if (! RFTId5532.checkAnsCode(ansCode))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5532.ANS_CODE_ERROR ;
				errDetails = RFTId5532.ErrorDetails.NULL;
			}
			else if (ansCode.equals(RFTId5532.ANS_CODE_ERROR))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				errDetails = RFTId5532.ErrorDetails.NULL;
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
			ansCode = RFTId5532.ANS_CODE_MAINTENANCE;
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
			ansCode = RFTId5532.ANS_CODE_ERROR;
			errDetails = RFTId5532.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IOException e)
		{
		    // 6006020=ファイルの入出力エラーが発生しました。{0}
		    RftLogMessage.printStackTrace(6006020, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5532.ANS_CODE_ERROR;
			errDetails = RFTId5532.ErrorDetails.I_O_ERROR;
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
			ansCode = RFTId5532.ANS_CODE_ERROR;
			errDetails = RFTId5532.ErrorDetails.INSTACIATE_ERROR;
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
			ansCode = RFTId5532.ANS_CODE_ERROR;
			errDetails = RFTId5532.ErrorDetails.PARAMETER_ERROR;
		}
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
			" PlanDate:" + shippingPlanDate +
			" CustomerCode:" + customerCode +
			" RftNo:" + rftNo +
			" WorkerCode:" + rftId0532.getWorkerCode() +"]";
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
			ansCode = RFTId5532.ANS_CODE_ERROR;
			errDetails = RFTId5532.ErrorDetails.COLLECTION_OVERFLOW;
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
			ansCode = RFTId5532.ANS_CODE_ERROR;
			errDetails = RFTId5532.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5532.ANS_CODE_ERROR;
			errDetails = RFTId5532.ErrorDetails.DB_UNIQUE_KEY_ERROR;
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
			ansCode = RFTId5532.ANS_CODE_ERROR ;
			errDetails = RFTId5532.ErrorDetails.INTERNAL_ERROR;
		}

		// 応答電文の作成
		// STX
		rftId5532.setSTX() ;
		// SEQ
		rftId5532.setSEQ(0) ;
		// ID
		rftId5532.setID(RFTId5532.ID) ;
		// RFT送信時間
		rftId5532.setRftSendDate(rftId0532.getRftSendDate()) ;
		// SERVER送信時間
		rftId5532.setServSendDate() ;
		// RFT号機
		rftId5532.setRftNo(rftNo) ;

		// 担当者コード
		rftId5532.setWorkerCode(rftId0532.getWorkerCode()) ;
		// 荷主コード
		rftId5532.setConsignorCode(consignorCode) ;
		// 出荷予定日
		rftId5532.setShippingPlanDate(shippingPlanDate) ;
		// 出荷先コード
		rftId5532.setCustomerCode(customerCode) ;
		// 荷主名
		rftId5532.setConsignorName(consignorName) ;
		// 出荷先名
		rftId5532.setCustomerName(customerName) ;
		// 出荷先単位出荷検品ファイル名
		rftId5532.setAnsFileName(sendFileName) ;
		// ファイルレコード数
		rftId5532.setFileRecordNumber(numRecords);
		// 応答フラグ
		rftId5532.setAnsCode(ansCode) ;
		// エラー詳細
		rftId5532.setErrDetails(errDetails);
		
		// ETX
		rftId5532.setETX() ;

		// 応答電文を獲得する。
		rftId5532.getSendMessage(sdt) ;

		// 応答フラグが0:正常の時応答電文をファイルに保存する
		try
		{
			if (ansCode.equals(RFTId5532.ANS_CODE_NORMAL))
			{
				rftId5532.saveResponseId(rftNo, PROCESS_NAME, wConn);
				wConn.commit();
			}
			else
			{
				// 履歴ファイルを削除する
				if (dataFile != null)
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
				// 作業中データを削除する
				RFTId5532.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
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

