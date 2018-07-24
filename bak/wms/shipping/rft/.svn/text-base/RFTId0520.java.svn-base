// $Id: RFTId0520.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/**
 * ソケット通信での「出荷検品出荷先一覧要求 Id = 0520」電文を処理します。
 *
 * <p>
 * <table border="1">
 * <CAPTION>Id0520の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>				<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td>	<td></td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>0520</td></tr>
 * <tr><td>ハンディ送信時間</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>担当者コード</td>		<td>4 byte</td>	<td></td></tr>
 * <tr><td>作業予定日</td>			<td>8 byte</td>	<td>YYYYMMDD</td></tr>
 * <tr><td>荷主コード</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */

public class RFTId0520 extends RecvIdMessage
{
	// Class field --------------------------------------------------

	/**
	 * 担当者コードのオフセット
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	/**
	 * 作業予定日のオフセット
	 */
	private static final int OFF_PLAN_DATE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	/**
	 * 荷主コードのオフセット
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_PLAN_DATE + LEN_PLAN_DATE;

	/**
	 * ID番号
	 */
	public static final String ID = "0520";
	
	// Class variables ----------------------------------------------

	// Class method
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $Date: 2006/08/17 09:34:30 $");
	}

	//Constructors --------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public RFTId0520()
	{
		super();
		offEtx = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	/**
	 * RFTから受信した電文をコンストラクタに渡します。
	 * @param rftId0520 担当者コード入力 Id = 0520 電文
	 */
	public RFTId0520(byte[] rftId0520)
	{
		super();
		setReceiveMessage(rftId0520);
	}

	// Public methods -----------------------------------------------
	/**
	 * 出荷検品出荷先一覧要求電文から担当者コードを取得します。
	 * @return   Worker Code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}

	/**
	 * 出荷検品出荷先一覧要求電文から作業予定日を取得します。
	 * @return   WorkType
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate;
	}

	/**
	 * 出荷検品出荷先一覧要求電文からの荷主コードを取得します。
	 * @return   ConsignorCode
	 */
	public String getConsignorCode()
	{
		String consignorCode =
			getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}

	// Package methods ----------------------------------------------

	// Protected methods ----------------------------------------------

	// Private methods ----------------------------------------------

}
//end of class
