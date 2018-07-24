// $Id: RFTId5131.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 商品単位入荷検品(DC･クロス)実績応答 ID=5131 電文を処理します
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id5131の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>5131</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>作業者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>総アイテム数</td>	<td>9 byte</td></tr>
 * <tr><td>残アイテム数</td>	<td>9 byte</td></tr>
 * <tr><td>応答フラグ</td>		<td>1 byte</td>
 *     <TD>0:正常<BR>6:他端末でメンテナンス中<BR>9:エラー</TD></tr>
 * <tr><td>エラー詳細</td>		<td>2 byte</td></tr>
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
public class RFTId5131 extends SendIdMessage
{
	// Class fields --------------------------------------------------
	/**
	 * 担当者コードのオフセットの定義
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	/**
	 * 総アイテム数のオフセットの定義
	 */
	private static final int OFF_TOTAL_ITEM_COUNT = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	/**
	 * 残アイテム数のオフセットの定義
	 */
	private static final int OFF_REMAINING_ITEM_COUNT = OFF_TOTAL_ITEM_COUNT + LEN_TOTAL_ITEM_COUNT;
	
	/**
	 * 応答フラグのオフセットの定義
	 */
	private static final int OFF_ANS_CODE = OFF_REMAINING_ITEM_COUNT + LEN_REMAINING_ITEM_COUNT;
	
	/**
	 * ETXのオフセットの定義
	 */
	private static final int OFF_ETX = OFF_ANS_CODE + LEN_ANS_CODE;
	
	/**
	 * ID1015のバイト総数
	 */
	public static final int LEN_ID1015 = OFF_ETX + LEN_ETX;
	
	/**
	 * ID番号
	 */
	public static final String ID = "5131";
	
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
	 * コンストラクタ
	 */
	public RFTId5131()
	{
		super();

		offAnsCode = OFF_REMAINING_ITEM_COUNT + LEN_REMAINING_ITEM_COUNT;
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
	 * 総アイテム数を設定します。
	 * @param  totalItemCount  総アイテム数
	 */
	public void setTotalItemCount(int totalItemCount)
	{
		setToBufferRight(totalItemCount, OFF_TOTAL_ITEM_COUNT, LEN_TOTAL_ITEM_COUNT);
	}

	/**
	 * 残アイテム数を設定します。
	 * @param  remainingItemCount  残アイテム数
	 */
	public void setRemainingItemCount (int remainingItemCount)
	{
		setToBufferRight(remainingItemCount, OFF_REMAINING_ITEM_COUNT, LEN_REMAINING_ITEM_COUNT);
	}
	

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

