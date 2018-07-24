// $Id: Id0431Process.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.CompletionClass;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.WorkDataFile;
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * RFTからの仕分実績送信(ID0431)に対する処理を行います。<BR>
 * Id0431Operateクラスの提供する機能を使用し、作業情報の該当する仕分データの更新を行い、
 * RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 仕分実績送信処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   完了フラグが正常か欠品の場合は、ファイルからデータを取得します。<BR>
 *   作業中データを使用する場合は作業中データ保存情報より該当データを取得し、recvフォルダに作業ファイルを作成します。
 *   ID0431Operateクラスの機能で、キャンセル又は確定の処理を呼び出し、応答フラグを取得します。<BR>
 *   応答フラグが0：正常の場合、作業中データ保存情報の該当データを削除し、RFT管理情報の電文項目をNULLに更新します。<BR>
 *   1.作業中データ保存情報を削除します。<BR>
 *   [削除条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   2.RFT作業情報を更新します。<BR>
 *   [更新条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   [更新内容]
 *   <UL><LI>応答電文：NULL</LI>
 *       <LI>最終更新日時：システム日時</LI>
 *       <LI>最終更新処理名：ID0431</LI>
 *   </UL>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class Id0431Process extends IdProcess
{
	// Class fields----------------------------------------------------

	// Class variables -----------------------------------------------
	private static final String CLASS_NAME = "Id0431Process";
	
	private static final String PROCESS_NAME = "ID0431";
	
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
	public Id0431Process()
	{
		super() ;
	}
	
	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public Id0431Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	// Public methods ------------------------------------------------
	/**
	 * 仕分実績送信(ID0431)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5431)をバイト列で作成します。<BR>
	 * 完了区分によって、キャンセル又は確定の処理を呼び出し、結果のテキストを作成します。<BR>
	 * 応答フラグが0：正常の場合、作業中データ保存情報の該当データを削除し、RFT管理情報の電文項目をNULLに更新します。<BR>
	 *   1.作業中データ保存情報を削除します。<BR>
	 *   [削除条件]
	 *   <UL><LI>RFTNo</LI></UL>
	 *   2.RFT作業情報を更新します。<BR>
	 *   [更新条件]
	 *   <UL><LI>RFTNo</LI></UL>
	 *   [更新内容]
	 *   <UL><LI>応答電文：NULL</LI>
	 *       <LI>最終更新日時：システム日時</LI>
	 *       <LI>最終更新処理名：ID0431</LI>
	 *   </UL>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//受信電文分解用インスタンスを生成
		RFTId0431 rftId0431 = null;
		
		//送信電文作成用インスタンスを生成
		RFTId5431 rftId5431 = null;

		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0431 = (RFTId0431) PackageManager.getObject("RFTId0431");
			rftId0431.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5431 = (RFTId5431) PackageManager.getObject("RFTId5431");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*431", e.getMessage());
			throw e;
		}
		
		// 受信電文からRFT号機を取得
		String rftNo = rftId0431.getRftNo();
		
		// 受信電文から担当者コードを取得
		String workerCode = rftId0431.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0431.getConsignorCode();

		// 受信電文から仕分予定日を取得
		String planDate = rftId0431.getPlanDate();
		
		// 受信電文からJANコードを取得
		String janCode = rftId0431.getJANCode();

		// 受信電文から作業形態(ケース・ピース区分)を取得
		String workForm = rftId0431.getWorkForm();
		
		// 受信電文から完了区分を取得
		String completionFlag = rftId0431.getCompletionFlag();
		
		// 受信電文から実績ファイル名を取得
		String resultFileName;
		
		// 応答フラグを保持する変数
		String ansCode = RFTId5431.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5431.ErrorDetails.NORMAL;
		
		Id0431Operate id0431Operate = null;
		
		try
		{
			//作業時間を取得
			int workerTime = rftId0431.getWorkSeconds();
			
			//ミススキャン数を取得
			int missScanCount = rftId0431.getMissCount();
			
			//ファイルレコード数を取得
			int fileRecordNumber = rftId0431.getFileRecordNumber();

			
			if (completionFlag.equals(CompletionClass.CANCEL))
			{
				// 受付区分がキャンセルの場合は送信したファイルの内容を使用する
				String sendpath = WmsParam.RFTSEND;		// wms/rft/send/
				resultFileName = sendpath + rftNo + "\\" + Id5430DataFile.FILE_NAME;
			}
			else
			{
				resultFileName = rftId0431.getResultFileName();
				if(resultFileName.trim().equals(""))
				{
					resultFileName = WorkDataFile.restoreDataFile(rftNo, false, wConn);
				}
			}
				
			// Id0431Operateクラスのインスタンスを生成
			id0431Operate = (Id0431Operate) PackageManager.getObject("Id0431Operate");
			id0431Operate.setConnection(wConn);

			// 仕分けの完了処理を行う
			ansCode = id0431Operate.doComplete(workerCode, rftNo, consignorCode, planDate, janCode, workForm,workerTime, missScanCount, fileRecordNumber, completionFlag, resultFileName);

			if(!completionFlag.equals(CompletionClass.CANCEL))
			{
			    // 作業データファイル履歴保存
				Id5430DataFile dataFile = (Id5430DataFile)PackageManager.getObject("Id5430DataFile");
				dataFile.setFileName(resultFileName);
				dataFile.saveHistoryFile();
			}
			if(ansCode.equals(RFTId5431.ANS_CODE_NORMAL))
			{
			    // 応答フラグが正常の場合は作業中データを削除する
			    RFTId5431.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
			    wConn.commit();
			}
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "Id0431Operate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5431.ANS_CODE_ERROR;
			errDetails = RFTId5431.ErrorDetails.INSTACIATE_ERROR;
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
		    ansCode = RFTId5431.ANS_CODE_ERROR;
		    errDetails = RFTId5430.ErrorDetails.I_O_ERROR;
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
			ansCode = RFTId5431.ANS_CODE_ERROR;
			errDetails = RFTId5431.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5431.ANS_CODE_ERROR;
			errDetails = RFTId5431.ErrorDetails.PARAMETER_ERROR;
		}
		catch (NumberFormatException e)
		{
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
			ansCode = RFTId5431.ANS_CODE_ERROR;
			errDetails = RFTId5431.ErrorDetails.PARAMETER_ERROR;
			
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
			// 応答フラグ：エラー
			ansCode = RFTId5431.ANS_CODE_ERROR;
			errDetails = RFTId5431.ErrorDetails.NULL;
			
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			// 応答フラグ：エラー
			ansCode = RFTId5431.ANS_CODE_ERROR;
			errDetails = RFTId5431.ErrorDetails.INTERNAL_ERROR;
		}
		
		//RFT5431インスタンスを使用して送信電文を作成
		//STX
		rftId5431.setSTX();
		//SEQ
		rftId5431.setSEQ(0);
		//ID
		rftId5431.setID(RFTId5431.ID);
		//ハンディ送信時間
		rftId5431.setRftSendDate(rftId0431.getRftSendDate());
		//サーバー送信時間
		rftId5431.setServSendDate();
		//RFT号機
		rftId5431.setRftNo(rftNo);
		//担当者コード
		rftId5431.setWorkerCode(workerCode);
		//応答フラグ
		rftId5431.setAnsCode(ansCode);
		// エラー詳細
		if (ansCode.equals(RFTId5431.ANS_CODE_ERROR)
				&& errDetails.equals(RFTId5431.ErrorDetails.NORMAL))
		{
			errDetails = id0431Operate.getErrorDetails();
		}
		rftId5431.setErrDetails(errDetails);
		
		//ETX
		rftId5431.setETX();
		
		// 応答電文を獲得する。
		rftId5431.getSendMessage(sdt);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------


}
//end of class
