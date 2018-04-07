// $Id: Id0022Process.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $
package jp.co.daifuku.wms.master.base.rft;
/*
 * Copyright 2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.RftConsignor;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.RFTId0022;
import jp.co.daifuku.wms.base.rft.RFTId5022;
import jp.co.daifuku.wms.base.rft.Id5022DataFile;

/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * 荷主一覧要求(ID0022)処理を行うためのクラスです。<BR>
 * <CODE>IdProcess</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 受信電文から検索条件を取得し荷主一覧情報の検索を行い<BR>
 * 荷主一覧(荷主コード昇順)ファイルを作成し、応答電文の生成を行います。<BR>
 * 検索及びファイル作成処理はId0022Operateクラスを使用します。<BR>
 * <BR>
 * 荷主一覧要求処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信電文をパラメータとして受取り、応答電文を生成します。<BR>
 *   荷主マスタを検索し荷主一覧情報を取得します。<BR>
 *   荷主一覧情報が取得できた場合は荷主一覧ファイルの作成を行い、正常応答電文を生成します。<BR>
 *   荷主一覧情報が取得できない場合は原因を判別し、異常応答電文を生成します。<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class Id0022Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0022Process";

	// Class variables -----------------------------------------------
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:15 $";
	}

	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public Id0022Process()
	{
		super();
	}

	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	// 未使用
	public Id0022Process(Connection conn)
	{
		this();
		wConn = conn;
	}

	// Public methods ------------------------------------------------
	/**
	 * 荷主一覧要求処理を行います。
	 * 受信した作業種別から荷主一覧を検索し、検索結果をファイルに書きこみます。
	 * また、荷主一覧ファイル名を送信電文にセットします。
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0022 rftId0022 = null;
		// 送信電文作成用のインスタンスを生成
		RFTId5022 rftId5022 = null;
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0022 = (RFTId0022) PackageManager.getObject("RFTId0022");
			rftId0022.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5022 = (RFTId5022) PackageManager.getObject("RFTId5022");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		// 受信電文から作業予定日を取得
		String planDate = rftId0022.getPlanDate();

		// 受信電文から作業種別を取得
		String workType = rftId0022.getWorkType();

		// 受信電文から作業種別詳細を取得
		String workDetails = rftId0022.getWorkDetails();

		// 受信電文からRFT号機を取得
		String rftNo = rftId0022.getRftNo();

		// 応答フラグを保持する変数
		String ansCode = RFTId5022.ANS_CODE_NORMAL;

		// エラー詳細を保持する変数
		String errDetails = RFTId5022.ErrorDetails.NORMAL;

		// 取得した荷主情報を保持する配列
		RftConsignor[] consignor = null;

		// ファイル名を作成
		String sendpath = WmsParam.RFTSEND; // wms/rft/send/

		// 送信ファイル名
		String sendFileName = sendpath + rftNo + "\\" + Id5022DataFile.ANS_FILE_NAME;

		String className = "";

		try
		{
			// Id0111Operateのインスタンスを生成
		    className = "Id0022Operate";
			Id0022Operate id0022Operate =
			    (Id0022Operate) PackageManager.getObject(className);
			id0022Operate.setConnection(wConn);

			// 荷主一覧情報を取得
			consignor = id0022Operate.findConsignorCode(planDate, workType, workDetails, rftNo, rftId0022.getWorkerCode());

			// 荷主一覧ファイルを作成
			className = "Id5022DataFile";
			Id5022DataFile listFile = (Id5022DataFile) PackageManager.getObject(className);
			listFile.setFileName(sendFileName);
			listFile.write(consignor);
		}
		// Id0111Operateのインスタンスから情報を取得できなかった場合はエラー
		catch (NotFoundException e)
		{
			// 応答フラグ：該当荷主無し
			ansCode = RFTId5022.ANS_CODE_NULL;
		}
		// データベースとの接続で異常が発生した場合
		catch (ReadWriteException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5022.ANS_CODE_ERROR;
			errDetails = RFTId5022.ErrorDetails.DB_ACCESS_ERROR;
		}
		// その他異常が発生した場合
		catch (IllegalAccessException e)
		{
		    // 6006003 = インスタンスの生成に失敗しました。クラス名={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, className, e.getMessage());
			// 応答フラグ：エラー
			ansCode = RFTId5022.ANS_CODE_ERROR ;
			errDetails = RFTId5022.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (IOException e)
		{
		    // 6006020 = ファイルの入出力エラーが発生しました。{0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			// 応答フラグ：エラー
			ansCode = RFTId5022.ANS_CODE_ERROR ;
			errDetails = RFTId5022.ErrorDetails.I_O_ERROR;
		}
		catch (Exception e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5022.ANS_CODE_ERROR;
			errDetails = RFTId5022.ErrorDetails.INTERNAL_ERROR;
		}

		// 応答電文の作成
		// STX
		rftId5022.setSTX();

		// SEQ
		rftId5022.setSEQ(0);

		// ID
		rftId5022.setID(RFTId5022.ID);

		// RFT送信時間
		rftId5022.setRftSendDate(rftId0022.getRftSendDate());

		// SERVER送信時間
		rftId5022.setServSendDate();

		// RFT号機
		rftId5022.setRftNo(rftNo);

		// 作業者コード
		rftId5022.setWorkerCode(rftId0022.getWorkerCode());

		// 荷主一覧ファイル名
		rftId5022.setListFileName(sendFileName);

		// ファイルレコード数
		if (consignor != null)
		{
			rftId5022.setFileRecordNumber(consignor.length);
		}
		else
		{
			rftId5022.setFileRecordNumber(0);
		}

		// 応答フラグ
		rftId5022.setAnsCode(ansCode);

		// エラー詳細
		rftId5022.setErrDetails(errDetails);

		// ETX
		rftId5022.setETX();

		// 応答電文を獲得する。
		rftId5022.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class
