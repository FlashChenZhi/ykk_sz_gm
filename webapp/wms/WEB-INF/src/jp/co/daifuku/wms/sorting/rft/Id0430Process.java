// $Id: Id0430Process.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

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
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.WorkingInformation;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * RFTからの仕分開始要求(ID0430)に対する処理を行います。 <BR>
 * Id0430Operateクラスの提供する機能を使用し、作業情報から該当する
 * 仕分予定情報を検索し、RFTに送信する応答電文を生成します。 <BR>
 * <BR>
 * 仕分開始要求(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   ID0430Operateクラスの機能で作業情報を検索し、データの状態(応答フラグ)と仕分データを取得します。 <BR>
 *   正常時は、仕分データから検索結果をファイルを作成します。<BR>
 *   同時に、作業ファイルの内容で作業中データ保存情報にレコードを作成します。<BR>
 *   作業中データがすでに存在していた場合は更新、存在しない場合は新規作成する。
 *   [更新条件]
 *   <UL><LI>RFTNo</LI>
 *       <LI>行No</LI>
 *   </UL>
 *   [更新/作成内容]
 *   <UL><LI>RFTNo（新規作成の場合)</LI>
 *       <LI>行No(新規作成の場合）</LI>
 *       <LI>ファイル名:ID5430.txt</LI>
 *       <LI>データ内容</LI>
 *   </UL>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 *   正常に処理が終了した場合は生成した応答電文の内容でRFT作業情報の電文項目を更新します。<BR>
 *   更新中にエラーが発生した場合は、ログを出力し作成します。<BR>
 *   1.RFT作業情報を更新します。<BR>
 *   [更新条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   [更新内容]
 *   <UL><LI>応答電文：生成した応答電文</LI>
 *       <LI>最終更新日時：システム日時</LI>
 *       <LI>最終更新処理名：ID0430</LI>
 *   </UL>  
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class Id0430Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0430Process";
	
	private static final String PROCESS_NAME = "ID0430";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:33 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public Id0430Process()
	{
		super();
	}

	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public Id0430Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	// Public methods ------------------------------------------------
	/**
	 * 仕分開始要求(ID0430)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5430)をバイト列で作成します。<BR>
	 * <BR>
	 * 最初に、日次処理中で無いことを確認します。<BR>
	 * 日次処理中の場合は、5:日次処理中のエラーを返します。<BR>
	 * 次に、作業者別実績の対象レコードがある事を確認します。<BR>
	 * 無かった場合は、新規作成します。<BR>
	 * <BR>
	 * 要求のあった条件で作業情報を検索し、作業予定ファイルを作成します。<BR>
 　　* 同時に、作業ファイルの内容で作業中データ保存情報にレコードを作成します。<BR>
	 *   作業中データがすでに存在していた場合は更新、存在しない場合は新規作成する。
	 *   [更新条件]
	 *   <UL><LI>RFTNo</LI>
	 *       <LI>行No</LI>
	 *   </UL>
	 *   [更新/作成内容]
	 *   <UL><LI>RFTNo（新規作成の場合)</LI>
	 *       <LI>行No(新規作成の場合）</LI>
	 *       <LI>ファイル名:ID5430.txt</LI>
	 *       <LI>データ内容</LI>
	 *   </UL>
　　 * 正常に処理が終了した場合は生成した応答電文の内容でRFT作業情報の電文項目を更新します。<BR>
	 *   RFT作業情報を更新します。<BR>
	 *   [更新条件]
	 *   <UL><LI>RFTNo</LI></UL>
	 *   [更新内容]
	 *   <UL><LI>応答電文：生成した応答電文</LI>
	 *       <LI>最終更新日時：システム日時</LI>
	 *       <LI>最終更新処理名：ID0430</LI>
	 *   </UL>  
 　　* 更新中にエラーが発生した場合は、ログを出力します。<BR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0430 rftId0430 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5430 rftId5430 = null;

		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0430 = (RFTId0430) PackageManager.getObject("RFTId0430");
			rftId0430.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5430 = (RFTId5430) PackageManager.getObject("RFTId5430");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*430",e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0430.getRftNo();

		// 受信電文から担当者コードを取得
		String workerCode = rftId0430.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0430.getConsignorCode();

		// 受信電文から仕分予定日を取得
		String planDate = rftId0430.getPlanDate();

		// 受信電文からスキャン商品コード1を取得
		String itemCode = rftId0430.getScanItemCode1();
		
		// 受信電文からスキャン商品コード2を取得
		String convertedJanCode = rftId0430.getScanItemCode2();
		
		// ボールITFを保持する変数
		String bundleITF = "";
		
		// ケースITFを保持する変数
		String ITF = "";
		
		// 受信電文から作業形態(ケース・ピース区分)を取得
		String workForm = rftId0430.getWorkForm();

		// 商品名称を保持する変数
		String itemName = "";

		// ボール入数を保持する変数
		String bundleEnteringQty = "";

		// ケース入数を保持する変数
		String enteringQty = "";

		// 送信ファイル名を保持する変数
		String sendFileName = "";
			
		// 総仕分先数を保持する変数
		int totalSortCount = 0;

		// 総仕分数を保持する変数
		int totalSortQty = 0;

		// 応答フラグを保持する変数
		String ansCode = RFTId5430.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5430.ErrorDetails.NORMAL;
		
		// 取得した仕分け情報を格納する配列
		WorkingInformation[] sortingWorkData = null;
		
		// ファイル名を作成
		String sendpath = WmsParam.RFTSEND;		//wms/rft/send/
		
		// 送信ファイル名
		sendFileName = sendpath + rftNo + "\\" + Id5430DataFile.FILE_NAME ;
		
		// データファイルオブジェクト変数
		Id5430DataFile id5430DataFile = null;

		try
		{
			if(DisplayText.isPatternMatching(itemCode))
			{
				throw new NotFoundException(RFTId5430.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(convertedJanCode))
			{
				throw new NotFoundException(RFTId5430.ANS_CODE_NULL);
			}
			// BaseOperateのインスタンスを生成
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//-----------------
			// 日次処理中チェック
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				// 状態フラグを 5:日次更新処理中 でリターン
				ansCode = RFTId5430.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//-----------------
				// 作業者別実績の存在チェック
				//-----------------
				WorkerResult[] workerResult = baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_SORTING,
						workerCode, rftNo);
				if (workerResult.length == 0)
				{
					// 作業者実績の新規作成
					baseOperate.createWorkerResult(
						WorkingInformation.JOB_TYPE_SORTING,
						workerCode, rftNo);
					// commit
					wConn.commit();
				}

				// Id0430Operateのインスタンスを生成
				Id0430Operate id0430Operate = (Id0430Operate) PackageManager.getObject("Id0430Operate");
				id0430Operate.setConnection(wConn);
				// Id0430OperateのstartSortingByItemメソッドを使って、作業情報にあるデータを取得
				sortingWorkData = id0430Operate.startSorting(
						consignorCode,
						planDate,
						workForm,
						itemCode,
						convertedJanCode,
						rftNo,
						workerCode);

				// Id5430DataFileのインスタンスを生成し、仕分け作業データファイルを作成
				id5430DataFile = (Id5430DataFile) PackageManager.getObject("Id5430DataFile");
				id5430DataFile.setFileName(sendFileName);
				id5430DataFile.write(sortingWorkData);

				// 仕分作業データファイルを作成
				// 作業データファイル履歴保存
				id5430DataFile.saveHistoryFile();

				// 作業中データを保存する。
				id5430DataFile.saveWorkingDataFile(rftNo, sendFileName, sortingWorkData.length, wConn);

				// 送信電文に送るデータをセット
				// 商品名称をセット
				itemName = sortingWorkData[0].getItemName1();
				// JANコードをセット
				itemCode = sortingWorkData[0].getItemCode();
				// ボールITFをセット
				bundleITF = sortingWorkData[0].getBundleItf();
				// ケースITFをセット
				ITF = sortingWorkData[0].getItf();
				// ボール入数をセット
				bundleEnteringQty = Integer.toString(sortingWorkData[0].getBundleEnteringQty());
				// ケース入数をセット
				enteringQty = Integer.toString(sortingWorkData[0].getEnteringQty());
				// 総仕分先数をセット
				totalSortCount = (sortingWorkData.length);
				// 
				int tempTotalSortQty = 0;
				for (int i = 0; i < sortingWorkData.length; i++)
				{
					tempTotalSortQty += sortingWorkData[i].getPlanEnableQty();
				}
				totalSortQty = tempTotalSortQty;
				// commit
				wConn.commit();

			}
		}
		// 情報を取得できなかった場合
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
			if (! RFTId5430.checkAnsCode(ansCode))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5430.ANS_CODE_ERROR;
				errDetails = RFTId5430.ErrorDetails.NULL;
			}
			else if (ansCode.equals(RFTId5430.ANS_CODE_ERROR))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				errDetails = RFTId5430.ErrorDetails.NULL;				
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
			ansCode = RFTId5430.ANS_CODE_MAINTENANCE;
		}
		// オーバーフローが発生した場合
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
							" WorkForm:" + workForm +
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
			// 応答フラグ：エラー
			ansCode = RFTId5430.ANS_CODE_ERROR;
			errDetails = RFTId5430.ErrorDetails.COLLECTION_OVERFLOW;
		}
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
			// 応答フラグ：エラー
			ansCode = RFTId5430.ANS_CODE_ERROR;
			errDetails = RFTId5430.ErrorDetails.DB_ACCESS_ERROR;
		}
        catch (IllegalAccessException e)
        {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "BaseOperate",e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5430.ANS_CODE_ERROR;
			errDetails = RFTId5430.ErrorDetails.INSTACIATE_ERROR;
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
			ansCode = RFTId5430.ANS_CODE_ERROR;
			errDetails = RFTId5430.ErrorDetails.DB_UNIQUE_KEY_ERROR;
        }
        catch (SQLException e)
        {
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
			ansCode = RFTId5430.ANS_CODE_ERROR;
			errDetails = RFTId5430.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5430.ANS_CODE_ERROR;
			errDetails = RFTId5430.ErrorDetails.PARAMETER_ERROR;
        }
        catch (IOException e)
        {
		    // 6006020 = ファイルの入出力エラーが発生しました。{0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			// 応答フラグ：エラー
			ansCode = RFTId5430.ANS_CODE_ERROR;
			errDetails = RFTId5430.ErrorDetails.I_O_ERROR;
        }
		// その他のエラーがあった場合
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
			// 応答フラグ：エラー
			ansCode = RFTId5430.ANS_CODE_ERROR;
			errDetails = RFTId5430.ErrorDetails.INTERNAL_ERROR;
		}
		// 応答電文の作成
		// STX
		rftId5430.setSTX();
		// SEQ
		rftId5430.setSEQ(0);
		// ID
		rftId5430.setID(RFTId5430.ID);
		// RFT送信時間
		rftId5430.setRftSendDate(rftId0430.getRftSendDate());
		// SERVER送信時間
		rftId5430.setServSendDate();
		// RFT号機
		rftId5430.setRftNo(rftNo);
		// 担当者コード
		rftId5430.setWorkerCode(workerCode);
		// 荷主コード
		rftId5430.setConsignorCode(consignorCode);
		// 仕分予定日
		rftId5430.setPlanDate(planDate);
		// JANコード
		rftId5430.setJanCode(itemCode);
		// ボールITFをセット
		rftId5430.setBundleITF(bundleITF);
		// ケースITFをセット
		rftId5430.setITF(ITF);
		// 作業形態
		rftId5430.setWorkForm(workForm);
		// 商品名
		rftId5430.setItemName(itemName);
		// ボール入数
		rftId5430.setBundleEnteringQty(bundleEnteringQty);
		// ケース入数
		rftId5430.setEnteringQty(enteringQty);
		// 作業ファイル名
		rftId5430.setAnsFileName(sendFileName);

		// ファイルレコード数
		if (ansCode.equals(RFTId5430.ANS_CODE_NORMAL))
		{
			rftId5430.setFileRecordNumber(sortingWorkData.length);
		}
		else
		{
			rftId5430.setFileRecordNumber(0);
		}

		// 総仕分先数
		rftId5430.setTotalSortingCount(totalSortCount);
		// 総仕分数
		rftId5430.setTotalSortingQty(totalSortQty);
		// 応答フラグ
		rftId5430.setAnsCode(ansCode);
		// エラー詳細
		rftId5430.setErrDetails(errDetails);
		// ETX
		rftId5430.setETX();

		// 応答電文を獲得する。
		rftId5430.getSendMessage(sdt);

		// 応答フラグが0:正常の時応答電文をファイルに保存する
		try
		{
			if (ansCode.equals(RFTId5430.ANS_CODE_NORMAL))
			{
				rftId5430.saveResponseId(rftNo, PROCESS_NAME, wConn);
				wConn.commit();
			}
			else
			{
				// 履歴ファイルを削除する。
				if (id5430DataFile != null)
				{
					try
					{
						id5430DataFile.deleteHistoryFile();						
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
				RFTId5430.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
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
