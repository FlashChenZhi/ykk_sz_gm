// $Id: Id0012Process.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $
package jp.co.daifuku.wms.master.base.rft;

/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.RFTId0012;
import jp.co.daifuku.wms.base.rft.RFTId5012;

/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * 荷主コード問合せ(ID0012)処理を行うためのクラスです。<BR>
 * <CODE>IdProcess</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 受信電文から検索条件を取得し荷主情報の検索を行い、応答電文を生成します。<BR>
 * 検索処理はId0012Operateクラスを使用します。<BR>
 * <BR>
 * 荷主コード問合せ処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信電文をパラメータとして受取り、応答電文を生成します。<BR>
 *   受信電文より荷主コード取得し、検索条件として荷主名称を取得します。<BR>
 *   荷主名称が取得できた場合は、正常応答電文を生成します。<BR>
 *   荷主名称が取得できない場合は原因を判別し、異常応答電文を生成します。<BR>
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
public class Id0012Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0012Process";

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
	public Id0012Process()
	{
		super();
	}

	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public Id0012Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	// Public methods ------------------------------------------------
	/**
	 * 荷主コード問い合わせ処理を行います。
	 * 受信した荷主コードから荷主名を検索し、検索結果を荷主名にセットします。
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0012 rftId0012 = null;
		// 送信電文作成用のインスタンスを生成
		RFTId5012 rftId5012 = null;
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0012 = (RFTId0012) PackageManager.getObject("RFTId0012");
			rftId0012.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5012 = (RFTId5012) PackageManager.getObject("RFTId5012");
		}
		catch (IllegalAccessException e)
		{
        	// 6006003=インスタンスの生成に失敗しました。クラス名={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*012");
			throw e;
		}

		// 応答フラグを保持する変数
		String ansCode = RFTId5012.ANS_CODE_NORMAL;

		// エラー詳細を保持する変数
		String errDetails = RFTId5012.ErrorDetails.NORMAL;

		// 荷主名を保持する変数
		String consignorName = "";

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0012.getConsignorCode();
		
		// 受信電文から作業予定日を取得
		String planDate = rftId0012.getPlanDate();

		try
		{
			if(DisplayText.isPatternMatching(consignorCode))
			{
				errDetails = RFTId5012.ErrorDetails.NULL;
				throw new NotFoundException(RFTId5012.ANS_CODE_ERROR);
			}
			// Id0012Operateのインスタンスを生成
			Id0012Operate id0012Operate =
			    (Id0012Operate) PackageManager.getObject("Id0012Operate");
			id0012Operate.setConnection(wConn);

			// Id0012Operateから荷主名称を取得
			consignorName =	id0012Operate.getConsignorName(
			        rftId0012.getWorkType(),
			        rftId0012.getWorkDetails(),
			        consignorCode,
			        planDate,
			        rftId0012.getRftNo(),
			        rftId0012.getWorkerCode());
		}
		// Id0012Operateから情報を取得できなかった場合
		catch (NotFoundException e)
		{
			ansCode = e.getMessage();
			if (!RFTId5012.checkAnsCode(ansCode))
			{
				// 6026015=ID対応処理で異常が発生しました。{0}
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR,
						CLASS_NAME, e);
				ansCode = RFTId5012.ANS_CODE_ERROR;
			}
			else if(ansCode.equals(RFTId5012.ANS_CODE_ERROR))
			{
				errDetails = RFTId5012.ErrorDetails.NULL;
			}
		}
		// データベースとの接続で異常が発生した場合
		catch (ReadWriteException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.DB_ACCESS_ERROR;
		}
		// 不正な作業区分であった場合
		catch (IllegalArgumentException e)
		{
			// 6006016=定義エラーが発生しました。項目={0}に対し{1}がセットされました。
			RftLogMessage.print(
				6006016,
				LogMessage.F_ERROR,
				CLASS_NAME,
				"WorkType",
				e.getMessage());
			// 応答フラグ：エラー
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.PARAMETER_ERROR;
		}
		// その他のエラーがあった場合
		catch (IllegalAccessException e)
		{
        	// 6006003=インスタンスの生成に失敗しました。クラス名={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*012");
			// 応答フラグ：エラー
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (NoPrimaryException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		catch (ScheduleException e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.SCHEDULE_ERROR;
			
		}
		catch (Exception e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.INTERNAL_ERROR;
		}

		// 応答電文の作成
		// STX
		rftId5012.setSTX();

		// SEQ
		rftId5012.setSEQ(0);

		// ID
		rftId5012.setID(RFTId5012.ID);

		// RFT送信時間
		rftId5012.setRftSendDate(rftId0012.getRftSendDate());

		// SERVER送信時間
		rftId5012.setServSendDate();

		// RFT号機
		rftId5012.setRftNo(rftId0012.getRftNo());

		// 作業者コード
		rftId5012.setWorkerCode(rftId0012.getWorkerCode());

		// 荷主コード
		rftId5012.setConsignorCode(consignorCode);

		// 荷主名
		rftId5012.setConsignorName(consignorName);

		// 応答フラグ
		rftId5012.setAnsCode(ansCode);

		// エラー詳細
		rftId5012.setErrDetails(errDetails);

		// ETX
		rftId5012.setETX();

		// 応答電文を獲得する。
		rftId5012.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
