// $Id: Id0141Process.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.WorkDataFile;
/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * RFTからの出荷先単位入荷検品実績送信(ID0141)に対する処理を行います。<BR>
 * Id0141Operateクラスの提供する機能を使用し、作業情報の該当する検品データの更新を行い、
 * RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 出荷先単位入荷検品実績送信処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   分納又は確定の場合は、受信データから実績ファイル名を取得します。キャンセルの場合は、送信ファイルを実績ファイル名とします。<BR>
 *   作業中データを使用する場合は作業中データ保存情報より該当データを取得し、recvフォルダに作業ファイルを作成します。<BR>
 *   (<CODE>restoreDataFile()</CODE>)<BR>
 *   <OL><LI>作業中データ保存情報を検索します。<BR>
 *   [検索条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   [ソート順]
 *   <UL><LI>行No</LI></UL>
 *   [取得内容]
 *   <UL><LI>RFTNo</LI>
 *       <LI>ファイル名 ID5140.txt</LI>
 *       <LI>行No</LI>
 *       <LI>データ内容：1行のデータ内容</LI>
 *   </LI></UL>
 *   <LI>取得した内容で作業ファイルをrecvフォルダに作成します。</LI>
 *   </OL>
 *   <BR>
 *   ID0141Operateクラスの機能で、キャンセル、保留又は確定の処理を呼び出し、応答フラグを取得します。<BR>
 *   応答フラグが0：正常の場合、作業中データ保存情報の該当データを削除し、RFT管理情報の電文項目をNULLに更新します。<BR>
 *   <OL>
 *   <LI>作業中データ保存情報を検索します。<BR>
 *   [検索条件]
 *   <UL><LI>RFTNo</LI></UL></LI>
 *   <LI>該当レコードを削除します。</LI>
 *   <LI>RFT作業情報を検索し、該当情報を更新します。<BR>
 *   [更新条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   [更新内容]
 *   <UL><LI>応答電文：NULL</LI>
 *       <LI>最終更新日時：システム日時</LI>
 *       <LI>最終更新処理名：ID0141</LI>
 *   </UL>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0141Process extends IdProcess
{
	// Class fields----------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * クラス名（ログ出力用）
	 */
	private static final String CLASS_NAME = "Id0141Process";
	
	private static final String PROCESS_NAME = "ID0141";
	
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
	 * 出荷先単位入荷検品実績送信(ID0141)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5141)をバイト列で作成します。<BR>
	 * 受付区分が分納又は確定の場合は、受信データから実績ファイル名を取得します。キャンセルの場合は、送信したファイルの内容を使用します。<BR>
     * 作業中データを使用する場合は作業中データ保存情報より該当データを取得し、recvフォルダに作業ファイルを作成します。<BR>
	 *   <OL><LI>作業中データ保存情報を検索します。<BR>
	 *   [検索条件]
	 *   <UL><LI>RFTNo</LI></UL>
	 *   [ソート順]
	 *   <UL><LI>行No</LI></UL>
	 *   [取得内容]
	 *   <UL><LI>RFTNo</LI>
	 *       <LI>ファイル名 ID0141.txt</LI>
	 *       <LI>行No</LI>
	 *       <LI>データ内容：1行のデータ内容</LI>
	 *   </LI></UL>
	 *   <LI>取得した内容で作業ファイルをrecvフォルダに作成します。</LI>
	 *   </OL>
	 *   <BR>
     * 応答フラグが0：正常の場合、作業中データ保存情報の該当データを削除し、RFT管理情報の電文項目をNULLに更新します。<BR>
	 *   <OL>
	 *   <LI>作業中データ保存情報を検索します。<BR>
	 *   [検索条件]
	 *   <UL><LI>RFTNo</LI></UL></LI>
	 *   <LI>該当レコードを削除します。</LI>
	 *   <LI>RFT管理情報を検索し、該当情報を更新します。<BR>
	 *   [更新条件]
	 *   <UL><LI>RFTNo</LI></UL>
	 *   [更新内容]
	 *   <UL><LI>応答電文：NULL</LI>
	 *       <LI>最終更新日時：システム日時</LI>
	 *       <LI>最終更新処理名：ID0141</LI>
	 *   </UL>	
 	 * 受付区分によって、キャンセル、分納又は確定の処理を呼び出し、結果のテキストを作成します。<BR>
 	 * 
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0141 rftId0141 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5141 rftId5141 = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0141 = (RFTId0141) PackageManager.getObject("RFTId0141");
			rftId0141.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5141 = (RFTId5141) PackageManager.getObject("RFTId5141");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*141", e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0141.getRftNo();

		// 受信電文から作業者コードを取得
		String workerCode = rftId0141.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0141.getConsignorCode();

		// 受信電文から入荷予定日を取得
		String planDate = rftId0141.getPlanDate();

		// 受信電文から仕入れ先コードを取得
		String supplierCode = rftId0141.getSupplierCode();

		// 受信電文から出荷先コードを取得
		String customerCode = rftId0141.getCustomerCode();

		// 受信電文から受付区分を取得
		String receiptClass = rftId0141.getReceiveFlag();

		// 応答フラグを保持する変数
		String ansCode = "";
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5141.ErrorDetails.NORMAL;

		Id0141Operate id0141Operate= null;

		try
		{
			// 受信電文から実績ファイル名を取得
			String resultFileName;
			if (receiptClass.equals(ReceiptClass.CANCEL))
			{
				// 受付区分がキャンセルの場合は送信したファイルの内容を使用する
				String sendpath = WmsParam.RFTSEND;
				resultFileName = sendpath + rftNo + "\\" + Id5140DataFile.ANS_FILE_NAME;
			}
			else
			{
				resultFileName = rftId0141.getResultFileName();
				if(resultFileName.trim().equals(""))
				{
				    // 作業中データファイルをrecvフォルダにリストアする
					resultFileName = WorkDataFile.restoreDataFile(rftNo, false, wConn);
				}
			}

			// ファイルのレコード数を取得する
			int numRecords = rftId0141.getFileRecordNumber();
			// 作業時間を取得する
			int workTime = rftId0141.getWorkSeconds();
			// 誤検回数を取得する
			int missScanCnt = rftId0141.getInspectionErrCount();
			
			// Id0141Operateのインスタンスを生成
			id0141Operate = (Id0141Operate) PackageManager.getObject("Id0141Operate");
			id0141Operate.setConnection(wConn);

			ansCode = id0141Operate.doComplete(
			        consignorCode,
			        planDate,
			        supplierCode,
			        customerCode,
			        rftNo,
			        workerCode,
			        receiptClass,
			        resultFileName,
					numRecords,
					workTime,
					missScanCnt);
			
			if(!receiptClass.equals(ReceiptClass.CANCEL))
			{
			    // 作業データファイル履歴保存
				Id5140DataFile dataFile = (Id5140DataFile)PackageManager.getObject("Id5140DataFile");
				dataFile.setFileName(resultFileName);
				dataFile.saveHistoryFile();
			}
			if(ansCode.equals(RFTId5141.ANS_CODE_NORMAL))
			{
			    // 応答フラグが正常の場合は作業中データ保存情報より対象データを削除する
			    RFTId5141.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
			    wConn.commit();
			}
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "Id0141Operate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}		
			ansCode = RFTId5141.ANS_CODE_ERROR;
			errDetails = RFTId5141.ErrorDetails.INSTACIATE_ERROR;
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
			ansCode = RFTId5141.ANS_CODE_ERROR;
			errDetails = RFTId5141.ErrorDetails.I_O_ERROR;
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
			ansCode = RFTId5141.ANS_CODE_ERROR;
			errDetails = RFTId5141.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5141.ANS_CODE_ERROR;
			errDetails = RFTId5141.ErrorDetails.PARAMETER_ERROR;
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
			ansCode = RFTId5141.ANS_CODE_ERROR;
			errDetails = RFTId5141.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch(NumberFormatException e)
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
			ansCode = RFTId5141.ANS_CODE_ERROR;
			errDetails = RFTId5141.ErrorDetails.PARAMETER_ERROR;
		}
		catch (NotFoundException e)
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
			ansCode = RFTId5141.ANS_CODE_ERROR;
			errDetails = RFTId5141.ErrorDetails.NULL;
		}		
		catch(Exception e)
		{
			// 6006001=予期しないエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5141.ANS_CODE_ERROR;
			errDetails = RFTId5141.ErrorDetails.INTERNAL_ERROR;			
		}
		
		// 応答電文の作成
		// STX
		rftId5141.setSTX();
		// SEQ
		rftId5141.setSEQ(0);
		// ID
		rftId5141.setID(RFTId5141.ID);
		// RFT送信時間
		rftId5141.setRftSendDate(rftId0141.getRftSendDate());
		// SERVER送信時間
		rftId5141.setServSendDate();
		// RFT号機
		rftId5141.setRftNo(rftNo);

		// 作業者コード
		rftId5141.setWorkerCode(workerCode);
		// 応答フラグ
		rftId5141.setAnsCode(ansCode);
		// エラー詳細
		if (ansCode.equals(RFTId5141.ANS_CODE_ERROR)
				&& errDetails.equals(RFTId5141.ErrorDetails.NORMAL))
		{
			errDetails = id0141Operate.getErrorDetails();
		}
		rftId5141.setErrDetails(errDetails);
		
		// ETX
		rftId5141.setETX();

		// 応答電文を獲得する。
		rftId5141.getSendMessage(sdt);
		
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

