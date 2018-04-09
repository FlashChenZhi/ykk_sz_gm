// $Id: RFTId5132.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 仕入先単位入荷検品(DC･クロス)開始応答 ID=5132 電文を処理します
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id5132の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>				<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>5132</td></tr>
 * <tr><td>ハンディ送信時間</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>担当者コード</td>		<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>			<td>16 byte</td></tr>
 * <tr><td>入荷予定日</td>			<td>8 byte</td>	<TD>YYYYMMDD</TD></tr>
 * <tr><td>仕入先コード</td>		<td>16 byte</td></tr>
 * <tr><td>荷主名</td>				<td>40 byte</td></tr>
 * <tr><td>仕入先名</td>			<td>40 byte</td></tr>
 * <tr><td>応答ファイル名</td>		<td>30 byte</td></tr>
 * <tr><td>ファイルレコード数</td>	<td>6 byte</td></tr>
 * <tr><td>応答フラグ</td>		<td>1 byte</td>
 *     <TD>0:正常<BR>1:他端末で作業中<BR>2:作業完了済み<BR>5:日時更新処理中<BR>
 *         6:システムメンテナンス中<BR>8:該当データ無し<BR>9:エラー</TD></tr>
 * <tr><td>エラー詳細</td>			<td>2 byte</td>	<td></td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
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
public class RFTId5132 extends SendIdMessage
{
	// Class fields --------------------------------------------------
	/**
	 * 担当者コードのオフセットの定義
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
	 * 荷主名のオフセットの定義
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;
	
	/**
	 * 仕入先名のオフセットの定義
	 */
	private static final int OFF_SUPPLIER_NAME = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	
	/**
	 * 応答ファイル名のオフセットの定義
	 */
	private static final int OFF_ANS_FILE_NAME = OFF_SUPPLIER_NAME + LEN_SUPPLIER_NAME;
	
	/**
	 * ファイルレコード数のオフセットの定義
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_ANS_FILE_NAME + LEN_FILE_NAME;
	
	/**
	 * ID番号
	 */
	public static final String ID = "5132";
	
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
	public RFTId5132()
	{
		super();

		offAnsCode = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}


	// Public methods ------------------------------------------------
	/**
	 * 担当者コードを設定します。
	 * @param  workerCode  担当者コード
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}
	
	/**
	 * 荷主コードを設定します。
	 * @param  consignorCode  荷主コード
	 */
	public void setConsignorCode (String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE);
	}
	
	/**
	 * 入荷予定日を設定します。
	 * @param  planDate  入荷予定日
	 */
	public void setPlanDate (String planDate)
	{
		setToBuffer(planDate, OFF_PLAN_DATE);
	}
	
	/**
	 * 仕入先コードを設定します。
	 * @param  supplierCode  仕入先コード
	 */
	public void setSupplierCode (String supplierCode)
	{
		setToBuffer(supplierCode, OFF_SUPPLIER_CODE);
	}
	
	/**
	 * 荷主名を設定します。
	 * @param  consignorName  荷主名
	 */
	public void setConsignorName (String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME);
	}
	
	/**
	 * 仕入先名を設定します。
	 * @param  supplierName  仕入先名
	 */
	public void setSupplierName (String supplierName)
	{
		setToBuffer(supplierName, OFF_SUPPLIER_NAME);
	}
	
	/**
	 * 応答ファイル名を設定します。
	 * @param  ansFileName  応答ファイル名
	 */
	public void setAnsFileName (String ansFileName)
	{
		setToBuffer(ansFileName, OFF_ANS_FILE_NAME);
	}
	
	/**
	 * ファイルレコード数を設定します。
	 * @param  records  ファイルレコード数
	 */
	public void setFileRecordNumber (int records)
	{
		setToBufferRight(records, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

