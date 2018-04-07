// $Id: Id0120Process.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdProcess;

/**
 * RFTからの仕入先一覧要求に対する処理を行う。<BR>
 * Id0120Operateクラスの提供する機能を使用して、仕入先の一覧を検索し、応答電文を生成します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0120Process extends IdProcess
{
	// Class fields----------------------------------------------------
	/**
	 * クラス名を表すフィールド
	 */
	private static final String CLASS_NAME = "Id0120Process";

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
	 * 仕入先一覧要求処理を行います。
	 * 受信した作業予定日、荷主コード、入荷作業種別から仕入先一覧を検索し、検索結果をファイルに書きこみます。
	 * また、仕入先一覧ファイル名を送信電文にセットします。
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0120 rftId0120 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5120 rftId5120 = null;

		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0120 = (RFTId0120) PackageManager.getObject("RFTId0120");
			rftId0120.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5120 = (RFTId5120) PackageManager.getObject("RFTId5120");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME,e.getMessage());
			throw e;
		}
		// 受信電文からRFT号機を取得
		String rftNo = rftId0120.getRftNo();
		
		// 受信電文から作業者コードを取得
		String workerCode = rftId0120.getWorkerCode();
		
		// 受信電文から作業予定日を取得
		String planDate = rftId0120.getWorkDay();
		
		// 受信電文から荷主コードを取得
		String consignorCode = rftId0120.getConsignorCode();
		
		// 受信電文から入荷作業種別を取得する
		String workType = rftId0120.getWorkType();
		
		// 応答フラグを保持する変数
		String ansCode = RFTId5120.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5120.ErrorDetails.NORMAL;
		
		// 取得した出庫オーダー情報を格納する配列
		WorkingInformation[] workinfo = null;

		// ファイル名を作成
		String sendpath = WmsParam.RFTSEND;		// wms/rft/send/
		
		// 送信ファイル名
		String sendFileName = sendpath + rftNo + "\\" + Id5120DataFile.ANS_FILE_NAME;
		
		String className = "";
		try
		{
			// Id0120Operateのインスタンスを生成
		    className = "Id0120Operate";
			Id0120Operate id0120Operate = (Id0120Operate) PackageManager.getObject(className);
			id0120Operate.setConnection(wConn);

			// オーダー一覧情報を取得
			workinfo = id0120Operate.getSupplierList(planDate, consignorCode, workType, workerCode, rftNo);

			// オ－ダー一覧ファイルを作成
			className = "Id5120DataFile";
			Id5120DataFile dataFile = (Id5120DataFile) PackageManager.getObject(className);
			dataFile.setFileName(sendFileName);
			dataFile.write(workinfo);

		}
		// WorkingInformationOperateのインスタンスから情報を取得できなかった場合はエラー
		catch (NotFoundException e)
		{
			// 応答フラグ：該当データ無し
			ansCode = RFTId5120.ANS_CODE_NULL;
		}
		// その他のエラーがあった場合
		catch (ReadWriteException e)
		{
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5120.ANS_CODE_ERROR;
			errDetails = RFTId5120.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e)
        {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, className, e.getMessage());
			// 応答フラグ：エラー
			ansCode = RFTId5120.ANS_CODE_ERROR;
			errDetails = RFTId5120.ErrorDetails.INSTACIATE_ERROR;
        }
		catch (IOException e)
        {
		    // 6006020 = ファイルの入出力エラーが発生しました。{0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			// 応答フラグ：エラー
			ansCode = RFTId5120.ANS_CODE_ERROR;
			errDetails = RFTId5120.ErrorDetails.I_O_ERROR;
        }
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5120.ANS_CODE_ERROR;
			errDetails = RFTId5120.ErrorDetails.INTERNAL_ERROR;
			
		}

		// 応答電文の作成
		// STX
		rftId5120.setSTX();
		
		// SEQ
		rftId5120.setSEQ(0);
		
		// ID
		rftId5120.setID(RFTId5120.ID);
		
		// RFT送信時間
		rftId5120.setRftSendDate(rftId0120.getRftSendDate());
		
		// SERVER送信時間
		rftId5120.setServSendDate();
		
		// RFT号機
		rftId5120.setRftNo(rftNo);

		// 担当者コード
		rftId5120.setWorkerCode(rftId0120.getWorkerCode());
		
		// 出庫オーダー一覧ファイル名
		rftId5120.setTableFileName(sendFileName);
		
		// ファイルレコード数
		if (ansCode.equals(RFTId5121.ANS_CODE_NORMAL))
		{
			rftId5120.setFileRecordNumber(workinfo.length);
		}
		else
		{
			rftId5120.setFileRecordNumber(0);
		}
		
		
		// 応答フラグ
		rftId5120.setAnsCode(ansCode);
		
		// エラー詳細
		rftId5120.setErrDetails(errDetails);
		
		// ETX
		rftId5120.setETX();

		// 応答電文を獲得する。
		rftId5120.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class
