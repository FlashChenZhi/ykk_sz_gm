// $Id: Id0533Process.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
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

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.WorkDataFile;
/**
 * Designer : Y.Taki <BR>
 * Maker : E.Takeda  <BR>
 * <BR>
 * RFTからの出荷先単位出荷検品実績送信(ID0533)に対する処理を行います。<BR>
 * Id0533Operateクラスの提供する機能を使用し、作業情報の該当する検品データの更新を行い、
 * RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 出荷先単位出荷検品実績送信処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   完了フラグが保留か確定の場合は、ファイルからデータを取得します。<BR>
 *   作業中データを使用する場合は作業中データ保存情報より該当データを取得し、recvフォルダに作業ファイルを作成します。<BR>
 *   <OL><LI>作業中データ保存情報を検索します。<BR>
 *   [検索条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   [ソート順]
 *   <UL><LI>行No</LI></UL>
 *   [取得内容]
 *   <UL><LI>RFTNo</LI>
 *       <LI>ファイル名  ID5532.txt</LI>
 *       <LI>行No</LI>
 *       <LI>データ内容：1行のデータ内容</LI>
 *   </LI></UL>
 *   <LI>取得した内容で作業ファイルをrecvフォルダに作成します。</LI>
 *   </OL>
 *   <BR>
 *   ID0533Operateクラスの機能で、キャンセル、保留又は確定の処理を呼び出し、応答フラグを取得します。<BR>
 *   応答フラグが0：正常の場合はRFT作業情報の電文項目をNULLに更新し、作業中データ保存情報の該当レコードを削除します。<BR>
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
 *       <LI>最終更新処理名：ID0533</LI>
 *   </UL>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0533Process extends IdProcess
{
	// Class fields----------------------------------------------------

	// Class variables -----------------------------------------------
	private static final String CLASS_NAME = "Id0533Process";
	
	private static final String PROCESS_NAME = "ID0533";
	
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
	public Id0533Process()
	{
		super() ;
	}
	
	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0533Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	// Public methods ------------------------------------------------
	/**
	 * 出荷先単位出荷検品実績送信(ID0533)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5533)をバイト列で作成します。<BR>
	 * 完了区分によって、キャンセル、保留又は確定の処理を呼び出し、結果のテキストを作成します。<BR>
     * 作業中データを使用する場合は作業中データ保存情報より該当データを取得し、recvフォルダに作業ファイルを作成します。<BR>
     * 応答フラグが0：正常の場合はRFT作業情報の電文項目をNULLに更新し、作業中データ保存情報の該当レコードを削除します。<BR>	
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0533 rftId0533 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5533 rftId5533 = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0533 = (RFTId0533) PackageManager.getObject("RFTId0533");
			rftId0533.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5533 = (RFTId5533) PackageManager.getObject("RFTId5533");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*533", e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0533.getRftNo();

		// 受信電文から担当者コードを取得
		String workerCode = rftId0533.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0533.getConsignorCode();

		// 受信電文から出荷予定日を取得
		String shippingPlanDate = rftId0533.getShippingPlanDate();

		// 受信電文から出荷先コードを取得
		String customerCode = rftId0533.getCustomerCode();

		// 受信電文から完了区分を取得
		String completionFlag = rftId0533.getCompletionFlag();

		// 受信電文から実績ファイル名を取得
		String resultFileName = rftId0533.getResultFileName();

		// 応答フラグを保持する変数
		String ansCode = RFTId5533.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5533.ErrorDetails.NORMAL;

		Id0533Operate id0533Operate = null;

		try
		{
			if(resultFileName.trim().equals(""))
			{
			    // 作業中データファイルをrecvフォルダにリストアする
				resultFileName = WorkDataFile.restoreDataFile(rftNo, false, wConn);
			}

			// ファイルのレコード数を取得する
			int numRecords = rftId0533.getFileRecordNumber();
			// 作業時間を取得する
			int workTime = rftId0533.getWorkSeconds();
			// 誤検回数を取得する
			int missScanCnt = rftId0533.getInspectionErrCount();;

			// Id0061Operateのインスタンスを生成
			id0533Operate = (Id0533Operate) PackageManager.getObject("Id0533Operate");
			id0533Operate.setConnection(wConn);

			ansCode = id0533Operate.doComplete(
					consignorCode,
					shippingPlanDate,
					customerCode,
					rftNo,
					workerCode,
					completionFlag,
					resultFileName,
					numRecords,
					workTime,
					missScanCnt);
		    if(!completionFlag.equals(RFTId0533.COMPLETION_FLAG_CANCEL))
		    {
		        // 作業データファイル履歴保存
				Id5532DataFile dataFile = (Id5532DataFile)PackageManager.getObject("Id5532DataFile");
				dataFile.setFileName(resultFileName);
				dataFile.saveHistoryFile();
		    }
			if(ansCode.equals(RFTId5533.ANS_CODE_NORMAL))
			{
			    // 応答フラグが正常の場合は作業中データを削除する
			    RFTId5533.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
			    wConn.commit();
			}	    
		}
		catch (IllegalAccessException e)
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
			ansCode = RFTId5533.ANS_CODE_ERROR;
			errDetails = RFTId5533.ErrorDetails.INSTACIATE_ERROR;
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
			ansCode = RFTId5533.ANS_CODE_ERROR;
			errDetails = RFTId5533.ErrorDetails.I_O_ERROR;
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
			ansCode = RFTId5533.ANS_CODE_ERROR;
			errDetails = RFTId5533.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5533.ANS_CODE_ERROR;
			errDetails = RFTId5533.ErrorDetails.PARAMETER_ERROR;
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
			ansCode = RFTId5533.ANS_CODE_ERROR;
			errDetails = RFTId5533.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (NumberFormatException e)
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
			ansCode = RFTId5533.ANS_CODE_ERROR;
			errDetails = RFTId5533.ErrorDetails.PARAMETER_ERROR;
			
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
			ansCode = RFTId5533.ANS_CODE_ERROR;
			errDetails = RFTId5533.ErrorDetails.NULL;
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
			ansCode = RFTId5533.ANS_CODE_ERROR;
			errDetails = RFTId5533.ErrorDetails.INTERNAL_ERROR;
		}
		
		// 応答電文の作成
		// STX
		rftId5533.setSTX();
		// SEQ
		rftId5533.setSEQ(0);
		// ID
		rftId5533.setID(RFTId5533.ID);
		// RFT送信時間
		rftId5533.setRftSendDate(rftId0533.getRftSendDate());
		// SERVER送信時間
		rftId5533.setServSendDate();
		// RFT号機
		rftId5533.setRftNo(rftNo);

		// 担当者コード
		rftId5533.setWorkerCode(workerCode);
		// 応答フラグ
		rftId5533.setAnsCode(ansCode);
		// エラー詳細
		if (ansCode.equals(RFTId5533.ANS_CODE_ERROR)
				&& errDetails.equals(RFTId5533.ErrorDetails.NORMAL))
		{
			errDetails = id0533Operate.getErrorDetails();
		}
		rftId5533.setErrDetails(errDetails);
		
		// ETX
		rftId5533.setETX();

		// 応答電文を獲得する。
		rftId5533.getSendMessage(sdt);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

