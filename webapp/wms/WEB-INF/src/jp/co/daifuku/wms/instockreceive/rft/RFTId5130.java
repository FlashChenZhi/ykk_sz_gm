// $Id: RFTId5130.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 商品単位入荷検品(DC･クロス)開始応答 ID=5130 電文を作成します
 *
 * <p>
 * <table border="1">
 * <CAPTION>Id5130の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>5130</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>作業者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>入荷予定日</td>		<td>8 byte</td>	<td>YYYYMMDD</td></tr>
 * <tr><td>入力仕入先コード</td><td>16 byte</td></tr>
 * <tr><td>バッチNo</td>		<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>便No</td>			<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>荷主名</td>			<td>40 byte</td></tr>
 * <tr><td>仕入先コード</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>仕入先名</td>		<td>40 byte</td></tr>
 * <tr><td>伝票番号</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>伝票行番号</td>		<td>3 byte</td><td></td></tr>
 * <tr><td>集約作業No</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>商品コード</td>		<td>16 byte</td><td>未使用</td></tr>
 * <tr><td>JANコード</td>		<td>16 byte</td></tr>
 * <tr><td>ボールITF</td>		<td>16 byte</td></tr>
 * <tr><td>ケースITF</td>		<td>16 byte</td></tr>
 * <tr><td>商品名称</td>		<td>40 byte</td></tr>
 * <tr><td>商品分類</td>		<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ボール入数</td>		<td>6 byte</td></tr>
 * <tr><td>ケース入数</td>		<td>6 byte</td></tr>
 * <tr><td>単位</td>			<td>6 byte</td>	<td>未使用</td></tr>
 * <tr><td>ロットNo.</td>		<td>10 byte</td><td>未使用</td></tr>
 * <tr><td>賞味期限日</td>		<td>8 byte</td></tr>
 * <tr><td>製造日</td>			<td>8 byte</td>	<td>YYYYMMDD（未使用）</td></tr>
 * <tr><td>品質区分</td>		<td>1 byte</td>	<td>0:良品（未使用）</td></tr>
 * <tr><td>TC/DC区分</td>		<td>1 byte</td> <TD>0:DC<BR>1:クロスTC</TD></tr>
 * <tr><td>入荷予定数</td>		<td>9 byte</td></tr>
 * <tr><td>入荷実績数</td>		<td>9 byte</td></tr>
 * <tr><td>分納区分</td>		<td>1 byte</td>	<TD>0:分納無し<BR>1:分納（未使用）</TD></tr>
 * <tr><td>総アイテム数</td>	<td>9 byte</td>	<TD>全作業データ件数</TD></tr>
 * <tr><td>残アイテム数</td>	<td>9 byte</td>	<TD>集約後の未作業データ件数</TD></tr>
 * <tr><td>応答フラグ</td>		<td>1 byte</td>
 *     <TD>0:正常<BR>1:他端末で作業中<BR>2:作業完了済み<BR>5:日時更新処理中<BR>
 *         6:システムメンテナンス中<BR>7:複数仕入先該当<BR>8:該当データ無し<BR>9:エラー</TD></tr>
 * <tr><td>エラー詳細</td>		<td>2 byte</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
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

public class RFTId5130 extends SendIdMessage
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
	 * 入力仕入先コードのオフセット
	 */
	private static final int OFF_INPUT_SUPPLIER_CODE = OFF_PLAN_DATE + LEN_PLAN_DATE;
	
	/**
	 * バッチNoのオフセット
	 */
	private static final int OFF_BATCH_NO = OFF_INPUT_SUPPLIER_CODE + LEN_SUPPLIER_CODE;
	
	/**
	 * 便Noのオフセット
	 */
	private static final int OFF_BATCH2_NO = OFF_BATCH_NO + LEN_BATCH_NO;
	
	/**
	 * 荷主名のオフセット
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_BATCH2_NO + LEN_BATCH2_NO;
	
	/**
	 * 仕入先コードのオフセット
	 */
	private static final int OFF_SUPPLIER_CODE = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	
	/**
	 * 仕入先名のオフセット
	 */
	private static final int OFF_SUPPLIER_NAME = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;
	
	/**
	 * 伝票番号のオフセット
	 */
	private static final int OFF_TICKET_NO = OFF_SUPPLIER_NAME + LEN_SUPPLIER_NAME;

	/**
	 * 伝票行番号のオフセット
	 */
	private static final int OFF_TICKET_LINE_NO = OFF_TICKET_NO + LEN_TICKET_NO;

	/**
	 * 集約作業Noのオフセット
	 */
	private static final int OFF_COLLECT_JOB_NO = OFF_TICKET_LINE_NO + LEN_TICKET_LINE_NO;
	
	/**
	 * 商品コードのオフセット
	 */
	private static final int OFF_ITEM_CODE = OFF_COLLECT_JOB_NO + LEN_COLLECT_JOB_NO;
	
	/**
	 * JANコードのオフセット
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE;
	
	/**
	 * ボールITFのオフセット
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE;
	
	/**
	 * ケースITFのオフセット
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF;
	
	/**
	 * 商品名称のオフセット
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF;
	
	/**
	 * 商品分類のオフセット
	 */
	private static final int OFF_ITEM_CATEGORY_CODE = OFF_ITEM_NAME + LEN_ITEM_NAME;
	
	/**
	 * ボール入数のオフセット
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_CATEGORY_CODE + LEN_ITEM_CATEGORY_CODE;
	
	/**
	 * ケース入数のオフセット
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY;
	
	/**
	 * 単位のオフセット
	 */
	private static final int OFF_UNIT = OFF_ENTERING_QTY + LEN_ENTERING_QTY;
	
	/**
	 * ロットNo.のオフセット
	 */
	private static final int OFF_LOT_NO = OFF_UNIT + LEN_UNIT;
	
	/**
	 * 賞味期限のオフセット
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO;
	
	/**
	 * 製造日のオフセット
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
	
	/**
	 * 品質区分のオフセット
	 */
	private static final int OFF_QUALITY_CLASSIFICATION = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE;
	
	/**
	 * TC/DC区分のオフセット
	 */
	private static final int OFF_TCDC_FLAG = OFF_QUALITY_CLASSIFICATION + LEN_QUALITY;
	
	/**
	 * 入荷予定数のオフセット
	 */
	private static final int OFF_PLAN_QTY = OFF_TCDC_FLAG + LEN_TCDC_FLAG;
	
	/**
	 * 入荷実績数のオフセット
	 */
	private static final int OFF_RESULT_QTY = OFF_PLAN_QTY + LEN_PLAN_QTY;
	
	/**
	 * 分納区分のオフセット
	 */
	private static final int OFF_DIVIDE_FLAG = OFF_RESULT_QTY + LEN_RESULT_QTY;
	
	/**
	 * 総アイテム数のオフセット
	 */
	private static final int OFF_TOTAL_ITEM_COUNT = OFF_DIVIDE_FLAG + LEN_DIVIDE_FLAG;

	/**
	 * 残アイテム数のオフセット
	 */
	private static final int OFF_REMAINING_ITEM_COUNT = OFF_TOTAL_ITEM_COUNT + LEN_TOTAL_ITEM_COUNT;
	
	/**
	 * ID番号
	 */
	public static final String ID = "5130";

	/**
	 * 応答フラグの複数仕入先該当を表すフィールド
	 */
	public static final String ANS_CODE_SUPPLIERS = "7";
	
	/**
	 * 分納フラグ：分納無し
	 */
	public static final String DIVIDE_FLAG_NORMAL = "0";
	
	/**
	 * 分納フラグ：分納
	 */
	public static final String DIVIDE_FLAG_DIVIDE = "1";

	
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
	 * コンストラクタ
	 */
	public RFTId5130()
	{
		super();

		offAnsCode = OFF_REMAINING_ITEM_COUNT + LEN_REMAINING_ITEM_COUNT;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	// Public methods -----------------------------------------------
	/**
	 * 作業者コードを設定します。
	 * @param	workerCode	作業者コード
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}
	
	/**
	 * 荷主コードを設定します。
	 * @param	consignorCode	荷主コード
	 */
	public void setConsignorCode(String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE);
	}
	
	/**
	 * 荷主名を設定します。
	 * @param	consignorName	荷主名
	 */
	public void setConsignorName(String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME);
	}
	
	/**
	 * 入力仕入先コードを設定します。
	 * @param inputSupplierCode		入力仕入先コード
	 */
	public void setInputSupplierCode(String inputSupplierCode)
	{
	    setToBuffer(inputSupplierCode, OFF_INPUT_SUPPLIER_CODE);
	}
	
	/**
	 * 仕入先コードを設定します。
	 * @param	supplierCode	仕入先コード
	 */
	public void setSupplierCode(String supplierCode)
	{
		setToBuffer(supplierCode, OFF_SUPPLIER_CODE);
	}
	
	/**
	 * 仕入先名を設定します。
	 * @param	supplierName	仕入先名
	 */
	public void setSupplierName(String supplierName)
	{
		setToBuffer(supplierName, OFF_SUPPLIER_NAME);
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
	 * 入荷予定日を設定します。
	 * @param	planDate	入荷予定日
	 */
	public void setPlanDate(String planDate)
	{
		setToBuffer(planDate, OFF_PLAN_DATE);
	}
	
	/**
	 * バッチNo.を設定します。
	 * @param	batchNo		バッチNo.
	 */
	public void setBatchNo(String batchNo)
	{
		setToBuffer(batchNo, OFF_BATCH_NO);
	}
	
	/**
	 * 便No.を設定します。
	 * @param	batch2No	便No.
	 */
	public void setBatch2No(String batch2No)
	{
		setToBuffer(batch2No, OFF_BATCH2_NO);
	}
	
	/**
	 * 集約作業Noを設定します。
	 * @param	collectJobNo		集約作業No
	 */
	public void setCollectJobNo (String collectJobNo)
	{
		setToBuffer(collectJobNo, OFF_COLLECT_JOB_NO);
	}
	
	/**
	 * 商品コードを設定します。
	 * @param	itemCode	商品コード
	 */
	public void setItemCode(String itemCode)
	{
		setToBuffer(itemCode, OFF_ITEM_CODE);
	}
	
	/**
	 * JANコードを設定します。
	 * @param	JANCode		JANコード
	 */
	public void setJANCode (String JANCode)
	{
		setToBuffer(JANCode, OFF_JAN_CODE);
	}
	
	/**
	 * ボールITFを設定します。
	 * @param	bundleITF	ボールITF
	 */
	public void setBundleITF (String bundleITF)
	{
		setToBuffer(bundleITF, OFF_BUNDLE_ITF);
	}
	
	/**
	 * ケースITFを設定します。
	 * @param	ITF			ケースITF
	 */
	public void setITF(String ITF)
	{
		setToBuffer(ITF, OFF_ITF);
	}
	
	/**
	 * 商品分類を設定します。
	 * @param	itemCategoryCode	商品分類
	 */
	public void setItemCategoryCode (String itemCategoryCode)
	{
		setToBuffer(itemCategoryCode, OFF_ITEM_CATEGORY_CODE);
	}
	
	/**
	 * 商品名称を設定します。
	 * @param	itemName	商品名称
	 */
	public void setItemName (String itemName)
	{
		setToBuffer(itemName, OFF_ITEM_NAME);
	}
	
	/**
	 * 単位を設定します。
	 * @param	unit		単位
	 */
	public void setUnit(String unit)
	{
		setToBuffer(unit, OFF_UNIT);
	}
	
	/**
	 * ボール入数を設定します。
	 * @param	bundleEnteringQty	ボール入数
	 */
	public void setBundleEnteringQty (int bundleEnteringQty)
	{
		setToBufferRight(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}
	
	/**
	 * ケース入数を設定します。
	 * @param	enteringQty		ケース入数
	 */
	public void setEnteringQty (int enteringQty)
	{
		setToBufferRight(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}
	
	/**
	 * ロットNo.を設定します。
	 * @param	lotNo			ロットNo.
	 */
	public void setLotNo (String lotNo)
	{
		setToBuffer(lotNo, OFF_LOT_NO);
	}
	
	/**
	 * 賞味期限を設定します。
	 * @param	useByDate		賞味期限
	 */
	public void setUseByDate(String useByDate)
	{
		setToBuffer(useByDate, OFF_USE_BY_DATE);
	}
	
	/**
	 * 製造日を設定します。
	 * @param	manufactureDate	製造日
	 */
	public void setManufactureDate (String manufactureDate)
	{
		setToBuffer(manufactureDate, OFF_MANUFACTURE_DATE);
	}
	
	/**
	 * 品質区分を設定します。
	 * @param	qualityClassification	品質区分
	 */
	public void setQualityClassification (String qualityClassification)
	{
		setToBuffer(qualityClassification, OFF_QUALITY_CLASSIFICATION);
	}
	
	/**
	 * TC/DC区分を設定します。
	 * @param	TCDCFlag	TC/DC区分
	 */
	public void setTCDCFlag (String TCDCFlag)
	{
		setToBuffer(TCDCFlag, OFF_TCDC_FLAG);
	}
	
	/**
	 * 入荷予定数を設定します。
	 * @param	planQty	入荷予定数
	 */
	public void setPlanQty (int planQty)
	{
		setToBufferRight(planQty, OFF_PLAN_QTY, LEN_PLAN_QTY);
	}
	
	/**
	 * 入荷実績数を設定します。
	 * @param	resultQty	入荷実績数
	 */
	public void setResultQty (int resultQty)
	{
		setToBufferRight(resultQty, OFF_RESULT_QTY, LEN_RESULT_QTY);
	}
	
	/**
	 * 分納を設定します。
	 * @param	divideFlag	分納フラグ
	 */
	public void setDivideFlag (String divideFlag)
	{
		setToBuffer(divideFlag, OFF_DIVIDE_FLAG);
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
	 * @param	remainingItemCount	残りアイテム数
	 */
	public void setRemainingItemCount (int remainingItemCount)
	{
		setToBufferRight(remainingItemCount, OFF_REMAINING_ITEM_COUNT, LEN_REMAINING_ITEM_COUNT);
	}
	
	// Package methods ----------------------------------------------
	
	// Protected methods --------------------------------------------
	
	// Private methods ----------------------------------------------

}
//end of class
