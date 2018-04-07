// $Id: RFTId5530.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 出荷検品JAN応答 ID=5530 電文を処理します。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>ID5530の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>				<TH>長さ</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>ハンディ送信時間</td>	<td>6 byte</td></tr>
 * <tr><td>サーバ送信時間</td>		<td>6 byte</td></tr>
 * <tr><td>ハンディ号機No.</td>	<td>3 byte</td></tr>
 * <tr><td>担当者コード</td>	<td>4 byte</td></tr>
 * <tr><td>荷主コード</td>		<td>16 byte</td></tr>
 * <tr><td>予定日</td>				<td>8 byte</td></tr>
 * <tr><td>出荷先コード</td>	<td>16 byte</td></tr>
 * <tr><td>バッチNo.</td>		<td>3 byte</td></tr>
 * <tr><td>便No.</td>           <td>3 byte</td></tr>
 * <tr><td>荷主名称</td>		<td>40 byte</td></tr>
 * <tr><td>出荷先名称</td>		<td>40 byte</td></tr>
 * <tr><td>伝票番号</td>			<td>16 byte</td></tr>
 * <tr><td>伝票行番号</td>			<td>3 byte</td></tr>
 * <tr><td>集約作業No</td>			<td>16 byte</td></tr>
 * <tr><td>商品コード</td>      <td>16 byte</td></tr>
 * <tr><td>JANコード</td>       <td>16 byte</td></tr>
 * <tr><td>ボールITF</td>       <td>16 byte</td></tr>
 * <tr><td>ケースITF</td>       <td>16 byte</td></tr>
 * <tr><td>商品名称</td>        <td>40 byte</td></tr>
 * <tr><td>商品分類</td>        <td>16 byte</td></tr>
 * <tr><td>ボール入数</td>      <td>6 byte</td></tr>
 * <tr><td>ケース入数</td>      <td>6 byte</td></tr>
 * <tr><td>単位</td>            <td>6 byte</td></tr>
 * <tr><td>ロットNo.</td>       <td>10 byte</td></tr>
 * <tr><td>賞味期限</td>        <td>8 byte</td></tr>
 * <tr><td>製造日</td>          <td>8 byte</td></tr>
 * <tr><td>品質区分</td>        <td>1 byte</td></tr>
 * <tr><td>出荷予定数量</td>    <td>9 byte</td></tr>
 * <tr><td>出荷実績数量</td>    <td>9 byte</td></tr>
 * <tr><td>保留</td>            <td>1 byte</td></tr>
 * <tr><td>総アイテム数</td>    <td>9 byte</td></tr>
 * <tr><td>残アイテム数</td>    <td>9 byte</td></tr>
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
public class RFTId5530 extends SendIdMessage
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
	 * 予定日のオフセットの定義
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	/**
	 * 出荷先コードのオフセットの定義
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_PLAN_DATE + LEN_PLAN_DATE;

	/**
	 * バッチNo.のオフセットの定義
	 */
	private static final int OFF_BATCH_NO = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;

	/**
	 * 便No.のオフセットの定義
	 */
	private static final int OFF_BATCH2_NO = OFF_BATCH_NO + LEN_BATCH_NO;

	/**
	 * 荷主名称のオフセットの定義
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_BATCH2_NO + LEN_BATCH2_NO;

	/**
	 * 出荷先名称のオフセットの定義
	 */
	private static final int OFF_CUSTOMER_NAME = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;

	/**
	 * 伝票番号のオフセット
	 */
	private static final int OFF_TICKET_NO = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;

	/**
	 * 伝票行番号のオフセット
	 */
	private static final int OFF_TICKET_LINE_NO = OFF_TICKET_NO + LEN_TICKET_NO;

	/**
	 * 集約作業Noのオフセットの定義
	 */
	private static final int OFF_COLLECT_JOB_NO = OFF_TICKET_LINE_NO + LEN_TICKET_LINE_NO;

	/**
	 * 商品コードのオフセットの定義
	 */
	private static final int OFF_ITEM_CODE = OFF_COLLECT_JOB_NO + LEN_COLLECT_JOB_NO;

	/**
	 * JANコードのオフセットの定義
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE;

	/**
	 * ボールITFのオフセットの定義
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE;

	/**
	 * ケースITFのオフセットの定義
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF;

	/**
	 * 商品名称のオフセットの定義
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF;

	/**
	 * 商品分類のオフセットの定義
	 */
	private static final int OFF_ITEM_CATEGORY_CODE = OFF_ITEM_NAME + LEN_ITEM_NAME;

	/**
	 * ボール入数のオフセットの定義
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_CATEGORY_CODE + LEN_ITEM_CATEGORY_CODE;

	/**
	 * ケース入数のオフセットの定義
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY;

	/**
	 * 単位のオフセットの定義
	 */
	private static final int OFF_UNIT = OFF_ENTERING_QTY + LEN_ENTERING_QTY;

	/**
	 * ロットNo.のオフセットの定義
	 */
	private static final int OFF_LOT_NO = OFF_UNIT + LEN_UNIT;

	/**
	 * 賞味期限のオフセットの定義
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO;

	/**
	 * 製造日のオフセットの定義
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE;

	/**
	 * 品質区分のオフセットの定義
	 */
	private static final int OFF_QUALITY = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE;

	/**
	 * 出荷予定数量のオフセットの定義
	 */
	private static final int OFF_SHIPPING_PLAN_QTY = OFF_QUALITY + LEN_QUALITY;

	/**
	 * 出荷実績数量のオフセットの定義
	 */
	private static final int OFF_SHIPPING_RESULT_QTY = OFF_SHIPPING_PLAN_QTY + LEN_PLAN_QTY;

	/**
	 * 保留のオフセットの定義
	 */
	private static final int OFF_RESERVE = OFF_SHIPPING_RESULT_QTY + LEN_RESULT_QTY;

	/**
	 * 総アイテム数のオフセットの定義
	 */
	private static final int OFF_TOTAL_ITEM_COUNT = OFF_RESERVE + LEN_RESERVE;

	/**
	 * 残アイテム数のオフセットの定義
	 */
	private static final int OFF_REMAINING_ITEM_COUNT = OFF_TOTAL_ITEM_COUNT + LEN_TOTAL_ITEM_COUNT;

	/**
	 * ID番号
	 */
	public static final String ID = "5530";

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
	public RFTId5530()
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
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	/**
	 * 荷主コードを設定します。
	 * @param  consignorCode  荷主コード
	 */
	public void setConsignorCode(String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE);
	}

	/**
	 * 荷主名を設定します。
	 * @param  consignorName  荷主名
	 */
	public void setConsignorName(String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME);
	}

	/**
	 * 出荷先コードを設定します。
	 * @param  customerCode  出荷先コード
	 */
	public void setCustomerCode(String customerCode)
	{
		setToBuffer(customerCode, OFF_CUSTOMER_CODE);
	}

	/**
	 * 出荷先名を設定します。
	 * @param  customerName  出荷先名
	 */
	public void setCustomerName(String customerName)
	{
		setToBuffer(customerName, OFF_CUSTOMER_NAME);
	}

	/**
	 * 予定日を設定します。
	 * @param  planDate  予定日
	 */
	public void setPlanDate(String planDate)
	{
		setToBuffer(planDate, OFF_PLAN_DATE);
	}

	/**
	 * バッチNo.を設定します。
	 * @param  batchNo  バッチNo.
	 */
	public void setBatchNo(String batchNo)
	{
		setToBuffer(batchNo, OFF_BATCH_NO);
	}

	/**
	 * 便No.を設定します。
	 * @param  batch2No  便No.
	 */
	public void setBatch2No(String batch2No)
	{
		setToBuffer(batch2No, OFF_BATCH2_NO);
	}

	/**
	 * 伝票番号をセットします。
	 * @param	ticketNo		伝票番号
	 */
	public void setTicketNo(String ticketNo)
	{
		setToBuffer(ticketNo, OFF_TICKET_NO);
	}

	/**
	 * 伝票行番号をセットします。
	 * @param	ticketLineNo		伝票行番号
	 */
	public void setTicketLineNo(String ticketLineNo)
	{
		setToBuffer(ticketLineNo, OFF_TICKET_LINE_NO);
	}

	/**
	 * 集約作業Noを設定します。
	 * @param  collectJobNo  集約作業No
	 */
	public void setCollectJobNo(String collectJobNo)
	{
		setToBuffer(collectJobNo, OFF_COLLECT_JOB_NO);
	}

	/**
	 * 商品コードを設定します。
	 * @param  itemCode  商品コード
	 */
	public void setItemCode(String itemCode)
	{
		setToBuffer(itemCode, OFF_ITEM_CODE);
	}

	/**
	 * JANコードを設定します。
	 * @param  janCode  JANコード
	 */
	public void setJanCode(String janCode)
	{
		setToBuffer(janCode, OFF_JAN_CODE);
	}

	/**
	 * ボールITFを設定します。
	 * @param  bundleItf  ボールITF
	 */
	public void setBundleItf(String bundleItf)
	{
		setToBuffer(bundleItf, OFF_BUNDLE_ITF);
	}

	/**
	 * ケースITFを設定します。
	 * @param  itf  ケースITF
	 */
	public void setItf(String itf)
	{
		setToBuffer(itf, OFF_ITF);
	}

	/**
	 * 商品分類を設定します。
	 * @param  itemCategoryCode  商品分類
	 */
	public void setItemCategoryCode(String itemCategoryCode)
	{
		setToBuffer(itemCategoryCode, OFF_ITEM_CATEGORY_CODE);
	}

	/**
	 * 商品名称を設定します。
	 * @param  itemName  商品名称
	 */
	public void setItemName(String itemName)
	{
		setToBuffer(itemName, OFF_ITEM_NAME);
	}

	/**
	 * 単位を設定します。
	 * @param  unit  単位
	 */
	public void setUnit(String unit)
	{
		setToBuffer(unit, OFF_UNIT);
	}

	/**
	 * ボール入数を設定します。
	 * @param  bundleEnteringQty  ボール入数
	 */
	public void setBundleEnteringQty(int bundleEnteringQty)
	{
		setToBufferRight(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}

	/**
	 * ケース入数を設定します。
	 * @param  enteringQty  ケース入数
	 */
	public void setEnteringQty(int enteringQty)
	{
		setToBufferRight(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}

	/**
	 * ロットNo.を設定します。
	 * @param  lotNo  ロットNo.
	 */
	public void setLotNo(String lotNo)
	{
		setToBuffer(lotNo, OFF_LOT_NO);
	}

	/**
	 * 賞味期限を設定します。
	 * @param  useByDate  賞味期限
	 */
	public void setUseByDate(String useByDate)
	{
		setToBuffer(useByDate, OFF_USE_BY_DATE);
	}

	/**
	 * 製造日を設定します。
	 * @param  manufactureDate  製造日
	 */
	public void setManufactureDate(String manufactureDate)
	{
		setToBuffer(manufactureDate, OFF_MANUFACTURE_DATE);
	}

	/**
	 * 品質区分を設定します。
	 * @param  quality  品質区分
	 */
	public void setQuality(String quality)
	{
		setToBuffer(quality, OFF_QUALITY);
	}

	/**
	 * 出荷予定数量を設定します。
	 * @param  shippingPlanQty  出荷予定数量
	 */
	public void setShippingPlanQty(int shippingPlanQty)
	{
		setToBufferRight(shippingPlanQty, OFF_SHIPPING_PLAN_QTY, LEN_PLAN_QTY);
	}

	/**
	 * 出荷実績数量を設定します。
	 * @param  shippingResultQty  出荷実績数量
	 */
	public void setShippingResultQty(int shippingResultQty)
	{
		setToBufferRight(shippingResultQty, OFF_SHIPPING_RESULT_QTY, LEN_RESULT_QTY);
	}

	/**
	 * 保留を設定します。
	 * @param  reserve  保留
	 */
	public void setReserve(String reserve)
	{
		setToBuffer(reserve, OFF_RESERVE);
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
	public void setRemainingItemCount(int remainingItemCount)
	{
		setToBufferRight(remainingItemCount, OFF_REMAINING_ITEM_COUNT, LEN_REMAINING_ITEM_COUNT);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
