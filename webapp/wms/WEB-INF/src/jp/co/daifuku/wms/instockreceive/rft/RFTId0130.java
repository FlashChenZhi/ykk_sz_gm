// $Id: RFTId0130.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
/**
 * 商品単位入荷検品(DC･クロス)開始要求 ID=0130 電文を処理します
 *
 * <p>
 * <table border="1">
 * <CAPTION>Id0130の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>				<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>0130</td></tr>
 * <tr><td>ハンディ送信時間</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>作業者コード</td>		<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>入荷予定日</td>			<td>8 byte</td>	<td>YYYYMMDD</td></tr>
 * <tr><td>入力仕入先コード</td>	<td>16 byte</td><td>空白の場合検索条件から外す</td></tr>
 * <tr><td>バッチNo</td>			<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>便No</td>				<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>スキャン商品コード1</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>スキャン商品コード2</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
  
public class RFTId0130 extends RecvIdMessage
{
	// Class field --------------------------------------------------
	/**
	 * 担当者コードのオフセット
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	/**
	 * 荷主コードのオフセット
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	/**
	 * 入荷予定日のオフセット
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	/**
	 * 仕入先コードのオフセット
	 */
	private static final int OFF_SUPPLIER_CODE = OFF_PLAN_DATE + LEN_PLAN_DATE;
	
	/**
	 * バッチNoのオフセット
	 */
	private static final int OFF_BATCH_NO = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;
	
	/**
	 * 便Noのオフセット
	 */
	private static final int OFF_BATCH2_NO = OFF_BATCH_NO + LEN_BATCH_NO;
	
	/**
	 * スキャン商品コード1のオフセットの定義
	 */
	private static final int OFF_SCAN_ITEM_CODE1 = OFF_BATCH2_NO + LEN_BATCH2_NO;

	/**
	 * スキャン商品コード1のオフセットの定義
	 */
	private static final int OFF_SCAN_ITEM_CODE2 = OFF_SCAN_ITEM_CODE1 + LEN_ITEM_CODE;
	
	/**
	 * ID番号
	 */
	public static final String ID = "0130";

	// Class method ------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $Date: 2006/08/17 09:34:14 $";
	}
	
	// Constructors -------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public RFTId0130 ()
	{
		super();

		offEtx = OFF_SCAN_ITEM_CODE2 + LEN_ITEM_CODE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	/**
	 * RFTから受信した電文をコンストラクタに渡します。
	 * @param rftId0130 商品単位入荷検品開始要求 ID = 0130 電文
	 */
	public RFTId0130 (byte[] rftId0132)
	{
		this();

		setReceiveMessage(rftId0132);
	}
	

	// Public methods -----------------------------------------------
	/**
	 * 商品単位入荷検品開始要求電文から担当者コードを取得します。
	 * @return   担当者コード
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	/**
	 * 商品単位入荷検品開始要求電文から荷主コードを取得します
	 * @return   荷主コード
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	/**
	 * 商品単位入荷検品開始要求電文から入荷予定日を取得します
	 * @return   入荷予定日
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate;
	}
	
	/**
	 * 商品単位入荷検品開始要求電文から仕入先コードを取得します
	 * @return   仕入先コード
	 */
	public String getSupplierCode()
	{
		String supplierCode = getFromBuffer(OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE);
		return supplierCode.trim();
	}
	
	/**
	 * 商品単位入荷検品開始要求電文からバッチNoを取得します
	 * @return   バッチNo
	 */
	public String getBatchNo()
	{
		String batchNo = getFromBuffer(OFF_BATCH_NO, LEN_BATCH_NO);
		return batchNo.trim();
	}
	
	/**
	 * 商品単位入荷検品開始要求電文から便Noを取得します
	 * @return   便No
	 */
	public String getBatch2No()
	{
		String batch2No = getFromBuffer(OFF_BATCH2_NO, LEN_BATCH2_NO);
		return batch2No.trim();
	}
	
	/**
	 * 商品単位入荷検品開始要求電文から商品コードを取得します。
	 * @return		商品コード
	 */
	public String getScanItemCode1()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE1, LEN_ITEM_CODE);
		return itemCode.trim();
	}

	/**
	 * 商品単位入荷検品開始要求電文から商品コードを取得します。
	 * @return		ITFtoJAN変換された商品コード
	 */
	public String getScanItemCode2()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE2, LEN_ITEM_CODE);
		return itemCode.trim();
	}
	
	// Package methods ----------------------------------------------
	
	// Protected methods --------------------------------------------
	
	// Private methods ----------------------------------------------
	
}
//end of class
