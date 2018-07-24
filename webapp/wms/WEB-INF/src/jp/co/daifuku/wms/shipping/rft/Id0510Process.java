// $Id: Id0510Process.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;

/**
 * Designer : T.Yamashita <BR>
 * Maker :   <BR>
 * <BR>
 * 出荷先問合せ(ID0510)処理を行うためのクラスです。<BR>
 * <CODE>IdProcess</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 受信電文から検索条件を取得し出荷先の検索を行い、応答電文を生成します。<BR>
 * 検索処理はBaseOperateクラスを使用します。<BR>
 * <BR>
 * 出荷先問合せ処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信電文をパラメータとして受取り、応答電文を生成します。<BR>
 *   受信電文より荷主コード、出荷予定日を検索条件として出荷先名称を取得します。<BR>
 *   出荷先名称が取得できた場合は、正常応答電文を生成します。<BR>
 *   出荷先名称が取得できない場合は原因を判別し、異常応答電文を生成します。<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0510Process extends IdProcess
{
	// Class fields----------------------------------------------------

	// Class variables -----------------------------------------------
	private static final String CLASS_NAME = "Id0510Process";

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
	public Id0510Process()
	{
		super();
	}

	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0510Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	// Public methods ------------------------------------------------
	/**
	 * 出荷先検品出荷先問い合わせ処理を行います。<BR>
	 * 受信した条件から出荷先名を検索し、検索結果を出荷先名にセットします。<BR>
	 *   送信電文を作成し、送信バッファに送信するテキストをセットします。 <BR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		// 受信電文解析用のインスタンスを生成
		RFTId0510 rftId0510 = null;
		// 送信電文作成用のインスタンスを生成
		RFTId5510 rftId5510 = null;
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0510 = (RFTId0510) PackageManager.getObject("RFTId0510");
			rftId0510.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5510 = (RFTId5510) PackageManager.getObject("RFTId5510");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(
				6006003,
				LogMessage.F_ERROR,
				CLASS_NAME,
				"RFTId*510",
				e.getMessage());
			throw e;
		}

		// 出荷先名を保持する変数
		String name = "";

		// 受信電文からRFT号機No.を取得
		String rftNo = rftId0510.getRftNo();

		// 受信電文から作業者コードを取得
		String workerCode = rftId0510.getWorkerCode();

		// 受信電文から作業予定日を取得
		String planDate = rftId0510.getShippingPlanDate();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0510.getConsignorCode();

		// 受信電文から出荷先コードを取得
		String customerCode = rftId0510.getCustomerCode();
		
		// 応答フラグを保持する変数
		String ansCode = RFTId5510.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5510.ErrorDetails.NORMAL;

		// 残アイテム数を保持する変数
		int remainingItemCount = 0;

		try
		{
			if(DisplayText.isPatternMatching(customerCode))
			{
				throw new NotFoundException(RFTId5510.ANS_CODE_NULL);
			}

			// BaseOperateのインスタンスを生成
			Id0510Operate id0510Operate = (Id0510Operate) PackageManager.getObject("Id0510Operate");
			id0510Operate.setConnection(wConn);

			// 作業情報より出荷先名称を取得する
			name =id0510Operate.getCustomerName(planDate,consignorCode,customerCode,workerCode,rftNo);

		}
		// WorkerOperateインスタンスから情報を取得できなかった場合
		catch (NotFoundException e)
		{
			// 応答フラグ：該当作業者無し
			ansCode = RFTId5510.ANS_CODE_NULL;
		}
		// その他のエラーがあった場合
		catch (ReadWriteException e)
		{
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5510.ANS_CODE_ERROR;
			errDetails = RFTId5510.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e)
		{
			// インスタンス生成に失敗
			RftLogMessage.print(6006003,LogMessage.F_ERROR,CLASS_NAME,"BaseOperate",e.getMessage());
			// 応答フラグ：エラー
			ansCode = RFTId5510.ANS_CODE_ERROR;
			errDetails = RFTId5510.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5510.ANS_CODE_ERROR;
			errDetails = RFTId5510.ErrorDetails.INTERNAL_ERROR;
		}

		// 応答電文の作成
		// STX
		rftId5510.setSTX();
		
		// SEQ
		rftId5510.setSEQ(0);
		
		// ID
		rftId5510.setID(RFTId5510.ID);
		
		// RFT送信時間
		rftId5510.setRftSendDate(rftId0510.getRftSendDate());
		
		// SERVER送信時間
		rftId5510.setServSendDate();
		
		// RFT号機
		rftId5510.setRftNo(rftNo);
		
		// 作業者コード
		rftId5510.setWorkerCode(workerCode);
		
		// 荷主コード
		rftId5510.setConsignorCode(consignorCode );
		
		// 出荷予定日
		rftId5510.setShippingPlanDate(planDate);
		
		// 出荷先コード
		rftId5510.setCustomerCode(customerCode);
		
		// 出荷先名
		rftId5510.setCustomerName(name);
		
		// 残アイテム数
		rftId5510.setRemainingItemCount(remainingItemCount);
		
		// 応答フラグ
		rftId5510.setAnsCode(ansCode);
		
		// エラー詳細
		rftId5510.setErrDetails(errDetails);
		
		// ETX
		rftId5510.setETX();

		// 応答電文を獲得する。
		rftId5510.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
