// $Id: RFTId0531.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 商品単位出荷検品実績送信 ID=0531 電文を処理します。
 * 
 * <p>
 * <TABLE border="1">
 * <CAPTION>Id0531の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>				<TH>長さ</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>ハンディ送信時間</td>	<td>6 byte</td></tr>
 * <tr><td>サーバー送信時間</td>	<td>6 byte</td></tr>
 * <tr><td>ハンディ号機No</td>		<td>3 byte</td></tr>
 * <tr><td>作業者コード</td>		<td>4 byte</td></tr>
 * <tr><td>荷主コード</td>			<td>16 byte</td></tr>
 * <tr><td>荷主名称</td>			<td>40 byte</td></tr>
 * <tr><td>出荷先コード</td>		<td>16 byte</td></tr>
 * <tr><td>出荷先名称</td>			<td>40 byte</td></tr>
 * <tr><td>予定日</td>				<td>8 byte</td></tr>
 * <tr><td>バッチNo.</td>			<td>3 byte</td></tr>
 * <tr><td>便No.</td>           	<td>3 byte</td></tr>
 * <tr><td>集約作業No.</td>			<td>16 byte</td></tr>
 * <tr><td>商品コード</td>      	<td>16 byte</td></tr>
 * <tr><td>JANコード</td>       	<td>16 byte</td></tr>
 * <tr><td>ボールITF</td>       	<td>16 byte</td></tr>
 * <tr><td>ケースITF</td>       	<td>16 byte</td></tr>
 * <tr><td>商品分類</td>        	<td>16 byte</td></tr>
 * <tr><td>商品名称</td>        	<td>40 byte</td></tr>
 * <tr><td>単位</td>            	<td>6 byte</td></tr>
 * <tr><td>ボール入数</td>      	<td>6 byte</td></tr>
 * <tr><td>ケース入数</td>      	<td>6 byte</td></tr>
 * <tr><td>ロットNo.</td>       	<td>10 byte</td></tr>
 * <tr><td>賞味期限</td>        	<td>8 byte</td></tr>
 * <tr><td>製造日</td>          	<td>8 byte</td></tr>
 * <tr><td>品質区分</td>        	<td>1 byte</td></tr>
 * <tr><td>出荷予定数量</td>    	<td>9 byte</td></tr>
 * <tr><td>出荷実績数量</td>		<td>9 byte</td></tr>
 * <tr><td>作業時間</td>			<td>1 byte</td></tr>
 * <tr><td>誤検回数</td>			<td>5 byte</td></tr>
 * <tr><td>完了区分</td>			<td>1 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
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
public class RFTId0531 extends RecvIdMessage
{
	// Class fields --------------------------------------------------
	/**
	 * 担当者コードのオフセットの定義
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	/**
	 * 荷主コードのオフセットの定義
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	/**
	 * 荷主名称のオフセットの定義
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	/**
	 * 出荷先コードのオフセットの定義
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;

	/**
	 * 出荷先名称のオフセットの定義
	 */
	private static final int OFF_CUSTOMER_NAME = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;

	/**
	 * 出荷予定日のオフセットの定義
	 */
	private static final int OFF_SHIPPING_PLAN_DATE = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;

	/**
	 * バッチNo.のオフセットの定義
	 */
	private static final int OFF_BATCH_NO = OFF_SHIPPING_PLAN_DATE + LEN_PLAN_DATE;

	/**
	 * 便No.のオフセットの定義
	 */
	private static final int OFF_BATCH2_NO = OFF_BATCH_NO + LEN_BATCH_NO;

	/**
	 * 集約作業Noのオフセットの定義
	 */
	private static final int OFF_COLLECT_JOB_NO = OFF_BATCH2_NO + LEN_BATCH2_NO;

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
	 * 商品分類のオフセットの定義
	 */
	private static final int OFF_ITEM_CATEGORY_CODE = OFF_ITF + LEN_ITF;

	/**
	 * 商品名称のオフセットの定義
	 */
	private static final int OFF_ITEM_NAME = OFF_ITEM_CATEGORY_CODE + LEN_ITEM_CATEGORY_CODE;

	/**
	 * 単位のオフセットの定義
	 */
	private static final int OFF_UNIT = OFF_ITEM_NAME + LEN_ITEM_NAME;

	/**
	 * ボール入数のオフセットの定義
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_UNIT + LEN_UNIT;

	/**
	 * ケース入数のオフセットの定義
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY;

	/**
	 * ロットNo.のオフセットの定義
	 */
	private static final int OFF_LOT_NO = OFF_ENTERING_QTY + LEN_ENTERING_QTY;

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
	 * 作業時間のオフセットの定義
	 */
	private static final int OFF_WORK_SECONDS = OFF_SHIPPING_RESULT_QTY + LEN_RESULT_QTY;

	/**
	 * 誤検回数のオフセットの定義
	 */
	private static final int OFF_INSPECTION_ERR_COUNT = OFF_WORK_SECONDS + LEN_WORK_TIME;

	/**
	 * 完了区分のオフセットの定義
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_INSPECTION_ERR_COUNT + LEN_INSPECTION_ERR_COUNT;
		
	/**
	 * ID番号
	 */
	public static final String ID = "0531";

	/**
	 * 完了区分の正常を表すフィールド
	 */
	public static final String COMPLETION_FLAG_NORMAL = "0" ;
	
	/**
	 * 完了区分の欠品を表すフィールド
	 */
	public static final String COMPLETION_FLAG_LACK = "1" ;
	
	/**
	 * 完了区分の保留を表すフィールド
	 */
	public static final String COMPLETION_FLAG_RESERVE = "2" ;
	
	/**
	 * 完了区分のキャンセルを表すフィールド
	 */
	public static final String COMPLETION_FLAG_CANCEL = "9" ;


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
	public RFTId0531 ()
	{
		super() ;

		offEtx = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	/**
	 * RFTから受信した電文をコンストラクタに渡します。
	 * @param rftId0531 商品単位出荷検品実績送信 ID=0531 電文
	 */
	public RFTId0531 (byte[] rftId0531)
	{
		super();

		setReceiveMessage(rftId0531) ;
	}

	// Public methods ------------------------------------------------
	/**
	 * 商品単位出荷検品実績送信電文から担当者コードを取得します。
	 * @return		担当者コード
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から荷主コードを取得します。
	 * @return		荷主コード
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から荷主名を取得します。
	 * @return		荷主名
	 */
	public String getConsignorName ()
	{
		String consignorName = getFromBuffer(OFF_CONSIGNOR_NAME, LEN_CONSIGNOR_NAME) ;
		return consignorName.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から出荷先コードを取得します。
	 * @return		出荷先コード
	 */
	public String getCustomerCode ()
	{
		String customerCode = getFromBuffer(OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE) ;
		return customerCode.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から出荷先名を取得します。
	 * @return		出荷先名
	 */
	public String getCustomerName ()
	{
		String customerName = getFromBuffer(OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME) ;
		return customerName.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から出荷予定日を取得します。
	 * @return		出荷予定日
	 */
	public String getShippingPlanDate ()
	{
		String shippingPlanDate = getFromBuffer(OFF_SHIPPING_PLAN_DATE, LEN_PLAN_DATE) ;
		return shippingPlanDate;
	}
	
	/**
	 * 商品単位出荷検品実績送信電文からバッチNo.を取得します。
	 * @return		バッチNo.
	 */
	public String getBatchNo ()
	{
		String batchNo = getFromBuffer(OFF_BATCH_NO, LEN_BATCH_NO) ;
		return batchNo.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から便No.を取得します。
	 * @return		便No.
	 */
	public String getBatch2No ()
	{
		String batch2No = getFromBuffer(OFF_BATCH2_NO, LEN_BATCH2_NO) ;
		return batch2No.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から商品識別コードを取得します。
	 * @return		商品識別コード
	 */
	public String getItemId ()
	{
		String itemId = getFromBuffer(OFF_COLLECT_JOB_NO, LEN_ITEM_ID) ;
		return itemId.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から商品コードを取得します。
	 * @return		商品コード
	 */
	public String getItemCode ()
	{
		String itemCode = getFromBuffer(OFF_ITEM_CODE, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文からJANコードを取得します。
	 * @return		JANコード
	 */
	public String getJanCode ()
	{
		String janCode = getFromBuffer(OFF_JAN_CODE, LEN_JAN_CODE) ;
		return janCode.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文からボールITFを取得します。
	 * @return		ボールITF
	 */
	public String getBundleItf ()
	{
		String bundleItf = getFromBuffer(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF) ;
		return bundleItf.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文からケースITFを取得します。
	 * @return		ケースITF
	 */
	public String getItf ()
	{
		String itf = getFromBuffer(OFF_ITF, LEN_ITF) ;
		return itf.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から商品分類を取得します。
	 * @return		商品分類
	 */
	public String getItemCategoryCode ()
	{
		String itemCategoryCode = getFromBuffer(OFF_ITEM_CATEGORY_CODE, LEN_ITEM_CATEGORY_CODE) ;
		return itemCategoryCode.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から商品名称を取得します。
	 * @return		商品名称
	 */
	public String getItemName ()
	{
		String itemName = getFromBuffer(OFF_ITEM_NAME, LEN_ITEM_NAME) ;
		return itemName.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から単位を取得します。
	 * @return		単位
	 */
	public String getUnit ()
	{
		String unit = getFromBuffer(OFF_UNIT, LEN_UNIT) ;
		return unit.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文からボール入数を取得します。
	 * @return		ボール入数
	 */
	public int getBundleEnteringQty ()
	{
		int bundleEnteringQty = getIntFromBuffer(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
		return bundleEnteringQty;
	}
	
	/**
	 * 商品単位出荷検品実績送信電文からケース入数を取得します。
	 * @return		ケース入数
	 */
	public int getEnteringQty ()
	{
		int enteringQty = getIntFromBuffer(OFF_ENTERING_QTY, LEN_ENTERING_QTY);
		return enteringQty;
	}
	
	/**
	 * 商品単位出荷検品実績送信電文からロットNo.を取得します。
	 * @return		ロットNo.
	 */
	public String getLotNo ()
	{
		String lotNo = getFromBuffer(OFF_LOT_NO, LEN_LOT_NO);
		return lotNo.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から賞味期限を取得します。
	 * @return		賞味期限
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE, LEN_USE_BY_DATE);
		return useByDate.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から製造日を取得します。
	 * @return		製造日
	 */
	public String getManufactureDate ()
	{
		String manufactureDate = getFromBuffer(OFF_MANUFACTURE_DATE, LEN_MANUFACTURE_DATE);
		return manufactureDate.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から品質区分を取得します。
	 * @return		品質区分
	 */
	public String getQuality ()
	{
		String quality = getFromBuffer(OFF_QUALITY, LEN_QUALITY);
		return quality.trim();
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から出荷予定数量を取得します。
	 * @return		出荷予定数量
	 */
	public int getShippingPlanQty ()
	{
		int shippingPlanQty = getIntFromBuffer(OFF_SHIPPING_PLAN_QTY, LEN_PLAN_QTY);
		return shippingPlanQty;
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から出荷実績数量を取得します。
	 * @return		出荷実績数量
	 */
	public int getShippingResultQty ()
	{
		int shippingResultQty = getIntFromBuffer(OFF_SHIPPING_RESULT_QTY, LEN_RESULT_QTY);
		return shippingResultQty;
	}
	
	/**
	 * 商品単位出荷検品実績送信電文から作業時間を取得します。
	 * @return		作業時間
	 */
	public int getWorkSeconds ()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}

	/**
	 * 商品単位出荷検品実績送信電文から誤検回数を取得します。
	 * @return		誤検回数
	 */
	public int getInspectionErrCount ()
	{
		int count = getIntFromBuffer(OFF_INSPECTION_ERR_COUNT, LEN_INSPECTION_ERR_COUNT);
		return count;
	}

	/**
	 * 商品単位出荷検品実績送信電文から完了区分を取得します。
	 * @return		完了区分
	 * 				0:完了
	 * 				1:欠品
	 * 				2:保留
	 * 				9:キャンセル
	 */
	public String getCompletionFlag ()
	{
		String completionFlag = getFromBuffer(OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG);
		return completionFlag;
	}
	

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

