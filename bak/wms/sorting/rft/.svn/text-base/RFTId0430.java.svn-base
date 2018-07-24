// $Id: RFTId0430.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
 /**
  * ソケット通信での「仕分開始要求 ID = 0430」電文を処理します
  *
  * <p>
  * <table border="1">
  * <CAPTION>Id0430の電文の構造</CAPTION>
  * <TR><TH>項目名</TH>				<TH>長さ</TH>	 <TH>内容</TH></TR>
  * <tr><td>STX</td>				<td> 1 byte</td> <td>0x02</td></tr>
  * <tr><td>SEQ NO</td>				<td> 4 byte</td> <td></td></tr>
  * <tr><td>ID</td>					<td> 4 byte</td> <td>0430</td></tr>
  * <tr><td>ハンディ送信時間</td>	<td> 6 byte</td> <td>HHMMSS</td></tr>
  * <tr><td>サーバー送信時間</td>	<td> 6 byte</td> <td>HHMMSS</td></tr>
  * <tr><td>ハンディ号機No</td>		<td> 3 byte</td> <td></td></tr>
  * <tr><td>担当者コード</td>		<td> 4 byte</td> <td></td></tr>
  * <tr><td>荷主コード</td>			<td>16 byte</td> <td></td></tr>
  * <tr><td>仕分予定日</td>			<td> 8 byte</td> <td>YYYMMDD</td></tr>
  * <tr><td>スキャン商品コード</td>	<td>16 byte</td> <td></td></tr>
  * <tr><td>スキャン商品コード2</td><td>16 byte</td> <td></td></tr>
  * <tr><td>作業形態</td>			<td> 1 byte</td> <td>1:ケース 2:ピース</td></tr>
  * <tr><td>ETX</td>				<td> 1 byte</td> <td></td></tr>
  * </table>
  * </p>
  * <BR>
  * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
  * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
  * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
  * @author  $Author: mori $
  */

public class RFTId0430 extends RecvIdMessage
{
	// Class field --------------------------------------------------
	/**
	 * 担当者コードのオフセット
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	/**
	 * 荷主コードのオフセット
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	/**
	 * 仕分予定日のオフセット
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	/**
	 * スキャン商品コードのオフセットの定義
	 */
	private static final int OFF_SCAN_ITEM_CODE1 = OFF_PLAN_DATE + LEN_PLAN_DATE ;
	
	/**
	 * スキャン商品コード2のオフセットの定義
	 */
	private static final int OFF_SCAN_ITEM_CODE2 = OFF_SCAN_ITEM_CODE1  + LEN_ITEM_CODE  ;
	
	/**
	 * 作業形態(ケース・ピース区分)のオフセット
	 */
	private static final int OFF_WORK_FORM = OFF_SCAN_ITEM_CODE2 + LEN_ITEM_CODE;
	
	/**
	 * ID番号
	 */
	public static final String ID = "0430";

	// Class variables ----------------------------------------------

	// Class method -------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $Date: 2006/08/17 09:34:33 $") ;
	}
	
	// Constructors -------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public RFTId0430 ()
	{
		super() ;
		offEtx = OFF_WORK_FORM + LEN_WORK_FORM;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	// Public methods -----------------------------------------------
	/**
	 * 担当者コードを取得します。
	 * @return   Worker Code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	/**
	 * 荷主コードを取得します
	 * @return   Consignor Code
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	/**
	 * 仕分予定日を取得します
	 * @return   Picking Plan Date
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate.trim();
	}
	
	/**
	 * スキャン商品コード1を取得します。
	 * @return		Scan Item Code1
	 */
	public String getScanItemCode1 ()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE1, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}

	/**
	 * スキャン商品コード2を取得します。
	 * @return		Scan Item Code2
	 */
	public String getScanItemCode2 ()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE2, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}
	
	/**
	 * 作業形態（ケース・ピース区分）を取得します
	 * @return   Case Piece Flag
	 */
	public String getWorkForm()
	{
		String workForm = getFromBuffer(OFF_WORK_FORM, LEN_WORK_FORM);
		return workForm.trim();
	}
	

	// Package methods ----------------------------------------------
	
	// Protected methods --------------------------------------------

	// Private methods ----------------------------------------------

}
//end of class