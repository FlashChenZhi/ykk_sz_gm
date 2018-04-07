// $Id: RFTId0133.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 仕入先単位入荷検品(DC･クロス)実績送信 ID=0133 電文を処理します
 * 
 * ID0133の電文長
 * <p>
 * <table border="1">
 * <CAPTION>Id0133の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0133</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>作業者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>		<td>16 byte</td></tr>
 * <tr><td>入荷予定日</td>		<td>8 byte</td>	<td>YYYYMMDD</td></tr>
 * <tr><td>仕入先コード</td>	<td>16 byte</td></tr>
 * <tr><td>作業時間</td>		<td>5 byte</td></tr>
 * <tr><td>誤検回数</td>		<td>5 byte</td>	<td>未使用</td></tr>
 * <tr><td>受付区分</td>		<td>1 byte</td>	<TD>0:正常<BR>1:欠品<BR>2:分納<BR>9:キャンセル</TD></tr>
 * <tr><td>実績ファイル名称</td><td>30 byte</td><TD>受付区分がキャンセルの場合は使用しない</TD></tr>
 * <tr><td>ファイルレコード数</td>	<td>6 byte</td>	<td></td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class RFTId0133 extends RecvIdMessage
{
	// Class fields --------------------------------------------------
	/**
	 * 作業者コードのオフセットの定義
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	/**
	 * 荷主コードのオフセットの定義
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	/**
	 * 入荷予定日のオフセットの定義
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	/**
	 * 仕入先コードのオフセットの定義
	 */
	private static final int OFF_SUPPLIER_CODE = OFF_PLAN_DATE + LEN_PLAN_DATE;
	
	/**
	 * 作業時間のオフセットの定義
	 */
	private static final int OFF_WORK_SECONDS = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;

	/**
	 * 誤検回数のオフセットの定義
	 */
	private static final int OFF_INSPECTION_ERR_COUNT = OFF_WORK_SECONDS + LEN_WORK_TIME;

	/**
	 * 完了フラグのオフセットの定義
	 */
	private static final int OFF_RECEIVE_FLAG = OFF_INSPECTION_ERR_COUNT + LEN_INSPECTION_ERR_COUNT;
	
	/**
	 * 実績ファイル名称のオフセットの定義
	 */
	private static final int OFF_RESULT_FILE_NAME = OFF_RECEIVE_FLAG + LEN_RECEIVE_FLAG;
	
	/**
	 * ファイルレコード数のオフセットの定義
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_RESULT_FILE_NAME + LEN_FILE_NAME;

	/**
	 * ID番号
	 */
	public static final String ID = "0133";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $";
	}

	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public RFTId0133 ()
	{
		super();

		offEtx = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	// Public methods ------------------------------------------------
	/**
	 * 仕入先単位入荷受付実績データ送信電文から担当者コードを取得します。
	 * @return		作業者コード
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	/**
	 * 仕入先単位入荷受付実績データ送信電文から荷主コードを取得します。
	 * @return		荷主コード
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	/**
	 * 仕入先単位入荷受付実績データ送信電文から入荷予定日を取得します。
	 * @return		入荷予定日
	 */
	public String getPlanDate ()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate;
	}
	
	/**
	 * 仕入先単位入荷受付実績データ送信電文から仕入先コードを取得します。
	 * @return		仕入先コード
	 */
	public String getSupplierCode ()
	{
		String supplierCode = getFromBuffer(OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE);
		return supplierCode.trim();
	}
	
	/**
	 * 仕入先単位入荷受付実績データ送信電文から作業時間を取得します。
	 * @return		作業時間
	 */
	public int getWorkSeconds ()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}

	/**
	 * 仕入先単位入荷受付実績データ送信電文から誤検回数を取得します。
	 * @return		誤検回数
	 */
	public int getInspectionErrCount ()
	{
		int count = getIntFromBuffer(OFF_INSPECTION_ERR_COUNT, LEN_INSPECTION_ERR_COUNT);
		return count;
	}

	/**
	 * 仕入先単位入荷受付実績データ送信電文から受付区分を取得します。
	 * @return		受付区分<BR>
	 * 				0:完了<BR>
	 * 				1:欠品<BR>
	 * 				2:分納<BR>
	 * 				9:キャンセル
	 */
	public String getReceiveFlag ()
	{
		String receiveFlag = getFromBuffer(OFF_RECEIVE_FLAG, LEN_RECEIVE_FLAG);
		return receiveFlag.trim();
	}
	
	/**
	 * 仕入先単位入荷受付実績データ送信電文から実績ファイル名称を取得します。
	 * @return		実績ファイル名称
	 */
	public String getResultFileName ()
	{
		String resultFileName = getFromBuffer(OFF_RESULT_FILE_NAME, LEN_FILE_NAME);
		return resultFileName.trim();
	}
	
	/**
	 * 仕入先単位入荷受付実績データ送信電文からファイルレコード数を取得します。
	 * @return		ファイルレコード数
	 */
	public int getFileRecordNumber ()
	{
		int records = getIntFromBuffer(OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
		return records;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

