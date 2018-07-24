// $Id: Id0920Process.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $
package jp.co.daifuku.wms.idm.rft;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.SystemParameter;

/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * RFTからの移動ラック空棚データ要求に対する処理を行います。
 * Id0920Operateクラスの提供する機能を使用し、ロケーション管理情報から対象となる棚Noデータを検索します。
 * RFTに送信する応答電文を生成します。 <BR>
 * <BR>
 * 移動ラック空棚データ要求処理(<CODE>processReceivedId()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *  受信データから、必要な情報を取得します。 <BR>
 *  Id0920Operateクラスの機能でロケーション管理情報を検索し、対象となる棚Noデータを取得します。 <BR>
 *  送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 *  ロケーション管理情報より移動ラック空棚一覧ファイルを作成します。
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/05</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class Id0920Process extends IdProcess
{
	// Class fields----------------------------------------------------
	/**
	 * クラス名
	 */
	private static final String CLASS_NAME = "Id0920Process";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public Id0920Process()
	{
		super();
	}

	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0920Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	// Public methods ------------------------------------------------
	/**
	 * 移動ラック空棚データ要求処理(ID0920)を行います。 <BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5920)をバイト列で作成します。 <BR>
	 * <BR>
	 * 日次処理中で無いことを確認します。
	 * 日次処理中の場合は、5:日次処理中のエラーを返します。 <BR>
	 * ID0920Operateクラスを利用し、要求のあった条件でロケーション管理情報を検索します。<BR>
	 * 対象となる棚が複数件該当した時には、移動ラック空棚一覧ファイルを作成し、応答フラグに0:正常をセットします。<BR>
	 * 対象となる棚が無い時は、応答フラグに「8:該当データ無し」をセットします。<BR>
	 * 送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
	 * <BR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		RFTId0920 rftId0920 = null;
		RFTId5920 rftId5920 = null;
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0920 = (RFTId0920) PackageManager.getObject("RFTId0920");
			rftId0920.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5920 = (RFTId5920) PackageManager.getObject("RFTId5920");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME,e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0920.getRftNo();

		// 受信電文から作業者コードを取得
		String workerCode = rftId0920.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0920.getConsignorCode();

		// 商品コードを保持する変数
		String itemCode = rftId0920.getItemCode();

		// ケース・ピース区分を保持する変数
		String casePieceFlag = rftId0920.getCasePieceFlag();

		// エリアNOを保持する変数
		String areaNo = rftId0920.getAreaNo();
		
		// 空棚区分を保持する変数
		String emptyKind = rftId0920.getEmptyKind();
		
		// 応答フラグを保持する変数
		String ansCode = RFTId5920.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5920.ErrorDetails.NORMAL;
			
		// ファイル名を作成
		String sendpath = WmsParam.RFTSEND;		// wms/rft/send/
		
		// 送信ファイル名
		String sendFileName = sendpath + rftNo + "\\" + Id5920DataFile.ANS_FILE_NAME;

		String className = "";
		
		String[] locate = null;
		
		// ファイルに書き込んだ行数を保持する変数 2006/6/14 滝
		int recCounter = 0;
		
		try
		{
			// BaseOperateのインスタンスを生成
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);
		
			//-----------------
			// 日次処理中チェック
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				// 状態フラグを 5:日次更新処理中 でリターン
				ansCode = RFTId5920.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				// Id0920Operateのインスタンスを生成
				Id0920Operate id0920Operate = (Id0920Operate)PackageManager.getObject("Id0920Operate");
				id0920Operate.setConnection(wConn);

				//ロケーション情報を検索、取得します。
				// 空棚区分にて検索処理の振分けを行います。
				if (emptyKind.equals(RFTId0920.EMPTY_KIND_EMPTY))
				{
					// 空棚要求
					locate =	id0920Operate.getLocateData();
				}
				else if (emptyKind.equals(RFTId0920.EMPTY_KIND_REPLENISH))
				{
					String wCasepieceFlag = "";
					if (!casePieceFlag.equals(RFTId0920.CASE_PIECE_All))
					{
						wCasepieceFlag = casePieceFlag;
					}
					// 補充棚要求
					locate =	id0920Operate.getStockData(
										consignorCode,
										itemCode,
										wCasepieceFlag);
				}
				// データが見つからない場合
				if (locate == null || locate.length == 0)
				{
					throw (new NotFoundException());
				}
				
				className = "Id5920DataFile";
				Id5920DataFile dataFile = (Id5920DataFile) PackageManager.getObject(className);
				dataFile.setFileName(sendFileName);
				recCounter = dataFile.write(locate,SystemParameter.FTP_FILE_MAX_RECORD);
			}
		}
		// stockOperateのインスタンスから情報を取得できなかった場合はエラー
		catch (NotFoundException e)
		{
			// 応答フラグ：該当データなし
			ansCode = RFTId5920.ANS_CODE_NULL;
		}
		// その他のエラーがあった場合
		catch (ReadWriteException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5920.ANS_CODE_ERROR;
			errDetails = RFTId5920.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e) {
			// インスタンス生成に失敗
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5920.ANS_CODE_ERROR;
			errDetails = RFTId5920.ErrorDetails.INSTACIATE_ERROR;
		} 
		catch (IOException e)
		{
		    // 6006020 = ファイルの入出力エラーが発生しました。{0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			// 応答フラグ：エラー
			ansCode = RFTId5920.ANS_CODE_ERROR ; 
			errDetails = RFTId5920.ErrorDetails.I_O_ERROR;
			
		}
		catch (Exception e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5920.ANS_CODE_ERROR;
			errDetails = RFTId5920.ErrorDetails.INTERNAL_ERROR;
		}

		// 応答電文の作成
		// STX
		rftId5920.setSTX();
		
		// SEQ
		rftId5920.setSEQ(0);
		
		// ID
		rftId5920.setID(RFTId5920.ID);
		
		// RFT送信時間
		rftId5920.setRftSendDate(rftId0920.getRftSendDate());
		
		// SERVER送信時間
		rftId5920.setServSendDate();
		
		// RFT号機
		rftId5920.setRftNo(rftNo);

		// 担当者コード
		rftId5920.setWorkerCode(workerCode);

		// 荷主コード
		rftId5920.setConsignorCode(consignorCode);

		// 商品コードをセット
		rftId5920.setItemCode(itemCode);

		// ケース・ピース区分をセット
		rftId5920.setCasePieceFlag(casePieceFlag);
		
		// エリアNOをセット
		rftId5920.setAreaNo (areaNo);

		// 空棚区分をセット
		rftId5920.setEmptyKind(emptyKind);

		// 一覧ファイル名をセット
		rftId5920.setAnsFileName(sendFileName);
		
		// ファイルレコード数をセット
		if (ansCode.equals(RFTId5920.ANS_CODE_NORMAL))
		{
			rftId5920.setFileRecordNumber(recCounter);
		}
		else
		{
			rftId5920.setFileRecordNumber(0);
		}	
		// 応答フラグ
		rftId5920.setAnsCode(ansCode);
		
		// エラー詳細
		rftId5920.setErrDetails(errDetails);

		// ETX
		rftId5920.setETX();

		// 応答電文を獲得する。
		rftId5920.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
