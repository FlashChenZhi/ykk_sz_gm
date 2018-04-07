// $Id: RFTId5533.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 出荷先単位出荷検品実績送信 ID=5533 電文を処理します。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>ID5533の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td></tr>
 * <tr><td>サーバ送信時間</td>	<td>6 byte</td></tr>
 * <tr><td>ハンディ号機No.</td>	<td>3 byte</td></tr>
 * <tr><td>作業者コード</td>	<td>4 byte</td></tr>
 * <tr><td>応答フラグ</td>		<td>1 byte</td></tr>
 * <tr><td>エラー詳細</td>		<td>2 byte</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class RFTId5533 extends SendIdMessage
{
	// Class fields --------------------------------------------------
	/**
	 * 担当者コードのオフセットの定義
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	/**
	 * ID番号
	 */
	public static final String ID = "5533";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $";
	}

	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public RFTId5533()
	{
		super();

		offAnsCode = OFF_WORKER_CODE + LEN_WORKER_CODE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	// Public methods ------------------------------------------------
	/**
	 * 作業者コードを設定します。
	 * @param  workerCode  作業者コード
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
