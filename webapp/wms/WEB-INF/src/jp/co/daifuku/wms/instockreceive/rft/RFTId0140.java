// $Id: RFTId0140.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 出荷先単位入荷検品(TC)開始要求 ID=0140 電文を処理します
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id0140の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0140</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>作業者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>		<td>16 byte</td></tr>
 * <tr><td>入荷予定日</td>		<td>8 byte</td>	<TD>YYYYMMDD</TD></tr>
 * <tr><td>仕入先コード</td>	<td>16 byte</td></tr>
 * <tr><td>出荷先コード</td>	<td>16 byte</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
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
public class RFTId0140 extends RecvIdMessage
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
	 * 出荷先コードのオフセットの定義
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;
	
	/**
	 * ID番号
	 */
	public static final String ID = "0140";

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
	public RFTId0140 ()
	{
		super();

		offEtx = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	/**
	 * RFTから受信した電文をコンストラクタに渡します。
	 * @param rftId0140 作業開始報告 ID = 0140 電文
	 */
	public RFTId0140 (byte[] rftId0140)
	{
		this();

		setReceiveMessage(rftId0140);
	}
	
	// Public methods ------------------------------------------------
	/**
	 * 出荷先別入荷受付データ問合せ()電文から作業者コードを取得します。
	 * @return		作業者コード
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	/**
	 * 出荷先別入荷受付データ問合せ()電文から荷主コードを取得します。
	 * @return		荷主コード
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	/**
	 * 出荷先別入荷受付データ問合せ()電文から入荷予定日を取得します。
	 * @return		入荷予定日
	 */
	public String getPlanDate ()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate;
	}
	
	/**
	 * 出荷先別入荷受付データ問合せ()電文から仕入先コードを取得します。
	 * @return		仕入先コード
	 */
	public String getSupplierCode ()
	{
		String supplierCode = getFromBuffer(OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE);
		return supplierCode.trim();
	}
	
	/**
	 * 出荷先別入荷受付データ問合せ()電文から出荷先コードを取得します。
	 * @return		出荷先コード
	 */
	public String getCustomerCode ()
	{
		String customerCode = getFromBuffer(OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
		return customerCode.trim();
	}
	

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

