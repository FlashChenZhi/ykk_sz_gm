// $Id: Id0133Process.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
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
 * Maker :  E.Takeda <BR>
 * <BR>
 * RFTからの仕入先単位入荷検品実績データ(ID0133)に対する処理を行います。<BR>
 * Id5133Operateクラスの提供する機能を使用し、作業情報の該当する検品データの更新を行い、
 * RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 仕入先単位入荷検品実績データ処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   受付区分が分納又は確定の場合は、受信データから実績ファイル名を取得します。キャンセルの場合は、送信したファイルの内容を使用します。<BR>
 *   作業中データを使用する場合は作業中データ保存情報(DNWorkingData)より該当データを取得し、recvフォルダに作業ファイルを作成します。
 *   (<CODE>restoreDataFile()</CODE>)<BR>
 *   <OL><LI>作業中データ保存情報を検索します。<BR>
 *   [検索条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   [ソート順]
 *   <UL><LI>行No</LI></UL>
 *   [取得内容]
 *   <UL><LI>RFTNo</LI>
 *       <LI>ファイル名 ID5132.txt</LI>
 *       <LI>行No</LI>
 *       <LI>データ内容：1行のデータ内容</LI>
 *   </LI></UL>
 *   <LI>取得した内容で作業ファイルをrecvフォルダに作成します。</LI>
 *   </OL>
 *   <BR>
 *   ID5133Operateクラスの機能で、キャンセル、保留又は確定の処理を呼び出し、応答フラグを取得します。<BR>
 *   応答フラグが0：正常の場合、作業中データ保存情報の該当データを削除し、RFT管理情報の電文項目をNULLに更新します。<BR>
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
 *       <LI>最終更新処理名：ID0133</LI>
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
public class Id0133Process extends IdProcess
{
	// Class fields----------------------------------------------------

	// Class variables -----------------------------------------------
	private static final String CLASS_NAME = "Id0133Process";
	
	private static final String PROCESS_NAME = "ID0133";
	
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
	 * 仕入先一括入荷検品実績データ(ID0133)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5133)をバイト列で作成します。<BR>
	 * 作業中データを使用する場合は作業中データ保存情報より該当データを取得し、recvフォルダに作業ファイルを作成します。<BR>
	 * (<CODE>restoreDataFile()</CODE>)<BR>
	 * 受付区分が分納又は確定の場合は、受信データから実績ファイル名を取得します。キャンセルの場合は、送信ファイルを実績ファイル名とします。<BR>
	 * 受付区分によって、キャンセル、分納又は確定の処理を呼び出し、結果のテキストを作成します。<BR>
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
	 *       <LI>最終更新処理名：ID0133</LI>
	 *   </UL>
     * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0133 rftId0133 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5133 rftId5133 = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0133 = (RFTId0133) PackageManager.getObject("RFTId0133");
			rftId0133.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5133 = (RFTId5133) PackageManager.getObject("RFTId5133");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*133", e.getMessage());
			throw e;
		}
		
		//	応答フラグを保持する変数
		String ansCode = "";
		// エラー詳細を保持する変数
		String errDetails = RFTId5133.ErrorDetails.NORMAL;

		// 受信電文からRFT号機を取得
		String rftNo = rftId0133.getRftNo();

		// 受信電文から担当者コードを取得
		String workerCode = rftId0133.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0133.getConsignorCode();

		// 受信電文から入荷予定日を取得
		String planDate = rftId0133.getPlanDate();

		// 受信電文から仕入れ先コードを取得
		String supplierCode = rftId0133.getSupplierCode();

		// 受信電文から受付区分を取得
		String receiptClass = rftId0133.getReceiveFlag();
		
		// 受信電文から実績ファイル名を取得
		String resultFileName;
				
		Id0133Operate id0133Operate = null;
		
		try
		{
			int numRecords =rftId0133.getFileRecordNumber();
			// 作業時間を取得する
			int workTime = rftId0133.getWorkSeconds();
			// 誤検回数を取得する
			int missScanCnt = rftId0133.getInspectionErrCount();
			
			if (receiptClass.equals(ReceiptClass.CANCEL))
			{
				// 受付区分がキャンセルの場合は送信したファイルの内容を使用する
				String sendpath = WmsParam.RFTSEND;
				resultFileName = sendpath + rftNo + "\\" + Id5132DataFile.ANS_FILE_NAME;
			}
			else
			{
				resultFileName = rftId0133.getResultFileName();
			    if (resultFileName.trim().equals(""))
				{
			        // 作業中データファイルをrecvフォルダにリストアする
			    	resultFileName = WorkDataFile.restoreDataFile(rftNo, false, wConn);
				}				
			}			
			// Id0133Operateのインスタンスを生成
			id0133Operate = (Id0133Operate) PackageManager.getObject("Id0133Operate");
			id0133Operate.setConnection(wConn);

			ansCode = id0133Operate.doComplete(
					consignorCode,
					planDate,
					supplierCode,
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
				Id5132DataFile dataFile = (Id5132DataFile)PackageManager.getObject("Id5132DataFile");
				dataFile.setFileName(resultFileName);
				dataFile.saveHistoryFile();
			}
			if(ansCode.equals(RFTId5133.ANS_CODE_NORMAL))
			{
			    // 応答フラグが正常の場合は作業中データ保存情報より対象データを削除する
			    RFTId5133.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
			    wConn.commit();
			}
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "Id0133Operate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			ansCode = RFTId5133.ANS_CODE_ERROR;
			errDetails = RFTId5133.ErrorDetails.INSTACIATE_ERROR;
		}
		catch(IOException e)
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

		    ansCode = RFTId5133.ANS_CODE_ERROR;
			errDetails = RFTId5133.ErrorDetails.I_O_ERROR;
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
			ansCode = RFTId5133.ANS_CODE_ERROR;
			errDetails = RFTId5133.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5133.ANS_CODE_ERROR;
			errDetails = RFTId5133.ErrorDetails.PARAMETER_ERROR;

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
			ansCode = RFTId5133.ANS_CODE_ERROR;
			errDetails = RFTId5133.ErrorDetails.DB_ACCESS_ERROR;
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

			ansCode = RFTId5133.ANS_CODE_ERROR;
			errDetails = RFTId5133.ErrorDetails.PARAMETER_ERROR;
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

			ansCode = RFTId5133.ANS_CODE_ERROR;
			errDetails = RFTId5133.ErrorDetails.NULL;

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

			ansCode = RFTId5133.ANS_CODE_ERROR;
			errDetails = RFTId5133.ErrorDetails.INTERNAL_ERROR;			
		}
		
		// 応答電文の作成
		// STX
		rftId5133.setSTX();
		// SEQ
		rftId5133.setSEQ(0);
		// ID
		rftId5133.setID(RFTId5133.ID);
		// RFT送信時間
		rftId5133.setRftSendDate(rftId0133.getRftSendDate());
		// SERVER送信時間
		rftId5133.setServSendDate();
		// RFT号機
		rftId5133.setRftNo(rftNo);

		// 担当者コード
		rftId5133.setWorkerCode(workerCode);
		// 応答フラグ
		rftId5133.setAnsCode(ansCode);
		// エラー詳細
		if (ansCode.equals(RFTId5133.ANS_CODE_ERROR)
				&& errDetails.equals(RFTId5133.ErrorDetails.NORMAL))
		{
			errDetails = id0133Operate.getErrorDetails();
		}
		rftId5133.setErrDetails(errDetails);
		
		// ETX
		rftId5133.setETX();

		// 応答電文を獲得する。
		rftId5133.getSendMessage(sdt);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

