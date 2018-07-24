// $Id: Id5140DataFile.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.rft.WorkDataFile;
import jp.co.daifuku.wms.base.rft.WorkingInformation;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 出荷先単位入荷検品ファイル(ID5140.txt)を操作するためのクラスです。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>ID5140のファイルの各行の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>行No</td>			<td>4 byte</td><td></td></tr>
 * <tr><td>仕入先コード</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>出荷先コード</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>伝票番号</td>		<td>16 byte</td><td>入荷伝票No</td></tr>
 * <tr><td>伝票番号行No</td>	<td>3 byte</td><td>入荷伝票行No</td></tr>
 * <tr><td>集約作業No</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>商品コード</td>		<td>16 byte</td><td>未使用</td></tr>
 * <tr><td>JANコード</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>ボールITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>ケースITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>商品名</td>			<td>40 byte</td><td></td></tr>
 * <tr><td>商品分類</td>		<td>4 byte</td><td>未使用</td></tr>
 * <tr><td>ボール入数</td>		<td>6 byte</td><td></td></tr>
 * <tr><td>ケース入数</td>		<td>6 byte</td><td></td></tr>
 * <tr><td>単位</td>			<td>6 byte</td><td>未使用</td></tr>
 * <tr><td>ロットNo</td>		<td>10 byte</td><td>未使用</td></tr>
 * <tr><td>賞味期限</td>		<td>8 byte</td><td></td></tr>
 * <tr><td>製造日</td>			<td>8 byte</td><td>未使用</td></tr>
 * <tr><td>品質</td>			<td>1 byte</td><td>0:良品（未使用）</td></tr>
 * <tr><td>入荷予定数</td>		<td>9 byte</td><td></td></tr>
 * <tr><td>入荷実績数</td>		<td>9 byte</td><td></td></tr>
 * <tr><td>分納区分</td>		<td>1 byte</td><td>0:分納なし<BR>1:分納</td></tr>
 * <tr><td>作業時間</td>		<td>5 byte</td><td>サーバからの送信時は00000</td></tr>
 * <tr><td>CRLF</td>			<td>2 byte</td>	<td>CR + LF</td></tr>
 * </table>
 * </p>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author $Author: mori $
 */
public class Id5140DataFile extends WorkDataFile
{
	// Class fields----------------------------------------------------
	/**
	 * 仕入先コードのオフセットの定義
	 */
	private static final int OFF_SUPPLIER_CODE = OFF_LINE_NO + LEN_LINE_NO;

	/**
	 * 出荷先コードのオフセットの定義
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;

	/**
	 * 伝票番号のオフセットの定義
	 */
	private static final int OFF_INSTOCK_TICKET_NO = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;

	/**
	 * 伝票番号行Noのオフセットの定義
	 */
	private static final int OFF_INSTOCK_TICKET_LINE_NO = OFF_INSTOCK_TICKET_NO + LEN_TICKET_NO;

	/**
	 * 集約作業Noのオフセットの定義
	 */
	private static final int OFF_ITEM_ID = OFF_INSTOCK_TICKET_LINE_NO + LEN_TICKET_LINE_NO;

	/**
	 * 商品コード（未使用）のオフセットの定義
	 */
	private static final int OFF_ITEM_CODE = OFF_ITEM_ID + LEN_ITEM_ID;

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
	 * 商品分類区分のオフセットの定義
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
	 * ロットNoのオフセットの定義
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
	 * 入荷予定数のオフセットの定義
	 */
	private static final int OFF_PLAN_QTY = OFF_QUALITY + LEN_QUALITY;

	/**
	 * 入荷実績数のオフセットの定義
	 */
	private static final int OFF_RESULT_QTY = OFF_PLAN_QTY + LEN_PLAN_QTY;

	/**
	 * 分納区分のオフセットの定義
	 */
	private static final int OFF_DIVIDE_FLAG = OFF_RESULT_QTY + LEN_RESULT_QTY;

	/**
	 * 作業時間のオフセットの定義
	 */
	private static final int OFF_WORK_SECONDS = OFF_DIVIDE_FLAG + LEN_DIVIDE_FLAG;

	/**
	 * 出荷先単位入荷検品データファイル名を表すフィールド
	 */
	static final String ANS_FILE_NAME = "ID5140.txt" ;

	
	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 * @param filename ファイル名
	 */
	public Id5140DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_WORK_SECONDS + LEN_WORK_TIME;
	}
	
	/**
	 * インスタンスを生成します。
	 */
	public Id5140DataFile()
	{
		lineLength = OFF_WORK_SECONDS + LEN_WORK_TIME;
	}


	// Public methods ------------------------------------------------
	/**
	 * 指定されたファイルからデータを読み取り、適切な型のEntity配列に入れて返します。
	 * 実際の配列の型はIDによって異なります。
	 * @return		ファイルから読み取ったデータ(Entity配列)
	 * @throws IOException		I/Oエラー発生
	 * @throws InvalidStatusException
	 */
    public Entity[] getWorkDataFromFile(String filename) throws IOException, InvalidStatusException
    {
        Vector v = new Vector();
		
		openReadOnly(filename);
			
		for (next(); currentLine != null; next())
		{
			WorkingInformation workinfo = new WorkingInformation();
			
			workinfo.setSupplierCode(getSupplierCode());
			workinfo.setCustomerCode(getCustomerCode());
			workinfo.setInstockTicketNo(getInstockTicketNo());
			workinfo.setInstockLineNo(getInstockTicketLineNo());
			workinfo.setCollectJobNo(getCollectJobNo());
			workinfo.setItemCode(getJanCode());
			workinfo.setBundleItf(getBundleItf());
			workinfo.setItf(getItf());
			workinfo.setItemName1(getItemName());
			workinfo.setBundleEnteringQty(getBundleEnteringQty());
			workinfo.setEnteringQty(getEnteringQty());
			workinfo.setLotNo(getLotNo());
			workinfo.setResultUseByDate(getUseByDate());
			workinfo.setPlanEnableQty(getInstockPlanQty());
			workinfo.setResultQty(getInstockResultQty());
			workinfo.setWorkTime(getWorkSeconds());

			v.addElement(workinfo);
		}
			
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
    }

	/**
	 * 引数で指定されたWorkingInformation配列のデータをファイル書き込みます。
	 * 
	 * @param	obj				ファイルに書き込むデータ（WorkingInformation配列）
	 * @throws IOException		ファイルの入出力中にエラーが発生しました。
	 */
	public void write(Entity[] obj) throws IOException
	{
		WorkingInformation[] workinfo = (WorkingInformation[])obj;
		
		openWritable();
			
		for (int i = 0; i < workinfo.length; i ++)
		{
		    setLineNo(i);
			setSupplierCode(workinfo[i].getSupplierCode());
			setCustomerCode(workinfo[i].getCustomerCode());
			setInstockTicketNo(workinfo[i].getInstockTicketNo());
			setInstockTicketLineNo(workinfo[i].getInstockLineNo());
			setCollectJobNo(workinfo[i].getCollectJobNo());
			setJanCode(workinfo[i].getItemCode());
			setBundleItf(workinfo[i].getBundleItf());
			setItf(workinfo[i].getItf());
			setItemName(workinfo[i].getItemName1());
			setBundleEnteringQty(workinfo[i].getBundleEnteringQty());
			setEnteringQty(workinfo[i].getEnteringQty());
			setLotNo(workinfo[i].getLotNo());
			setUseByDate(workinfo[i].getUseByDate());
			setInstockPlanQty(workinfo[i].getPlanEnableQty());
			setInstockResultQty(workinfo[i].getResultQty());
			setQuality("0");
			setInstallmentDelivery("0");
			setWorkSeconds(0);
			
			writeln();
		}
			
		closeWritable();
	}

	/**
	 * データバッファから仕入先コードを取得します。
	 * @return		仕入先コード
	 */
	public String getSupplierCode()
	{
		return getColumn(OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE);
	}
	
	/**
	 * データバッファから出荷先コードを取得します。
	 * @return		出荷先コード
	 */
	public String getCustomerCode()
	{
		return getColumn(OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
	}
	
	/**
	 * データバッファから伝票番号を取得します。
	 * @return		伝票番号
	 */
	public String getInstockTicketNo()
	{
		return getColumn(OFF_INSTOCK_TICKET_NO, LEN_TICKET_NO);
	}
	
	/**
	 * データバッファから伝票行Noを取得します。
	 * @return		伝票行No
	 */
	public int getInstockTicketLineNo()
	{
		return getIntColumn(OFF_INSTOCK_TICKET_LINE_NO, LEN_TICKET_LINE_NO);
	}

	/**
	 * データバッファから集約作業No（商品識別コード）を取得します。
	 * @return		集約作業No
	 */
	public String getCollectJobNo()
	{
		return getColumn(OFF_ITEM_ID, LEN_ITEM_ID);
	}
	
	/**
	 * データバッファから商品コードを取得します。
	 * @return		商品コード
	 */
	public String getItemCode()
	{
		return getColumn(OFF_ITEM_CODE, LEN_ITEM_CODE);
	}

	/**
	 * データバッファからJANコードを取得します。
	 * @return		JANコード
	 */
	public String getJanCode()
	{
		return getColumn(OFF_JAN_CODE, LEN_JAN_CODE);
	}

	/**
	 * データバッファからボールITFを取得します。
	 * @return		ボールITF
	 */
	public String getBundleItf()
	{
		return getColumn(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF);
	}

	/**
	 * データバッファからケースITFを取得します。
	 * @return		ケースITF
	 */
	public String getItf()
	{
		return getColumn(OFF_ITF, LEN_ITF);
	}

	/**
	 * データバッファから商品名を取得します。
	 * @return		商品名
	 */
	public String getItemName()
	{
		return getColumn(OFF_ITEM_NAME, LEN_ITEM_NAME);
	}

	/**
	 * データバッファから商品分類を取得します。
	 * @return		商品分類
	 */
	public String getItemCategoryCode()
	{
		return getColumn(OFF_ITEM_CATEGORY_CODE, LEN_ITEM_CATEGORY_CODE);
	}

	/**
	 * データバッファからボール入り数を取得します。
	 * @return		ボール入り数
	 */
	public int getBundleEnteringQty()
	{
		return getIntColumn(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}

	/**
	 * データバッファからケース入り数を取得します。
	 * @return		ケース入り数
	 */
	public int getEnteringQty()
	{
		return getIntColumn(OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}

	/**
	 * データバッファから単位を取得します。
	 * @return		単位
	 */
	public String getUnit()
	{
		return getColumn(OFF_UNIT, LEN_UNIT);
	}

	/**
	 * データバッファからロットNoを取得します。
	 * @return		ロットNo
	 */
	public String getLotNo()
	{
		return getColumn(OFF_LOT_NO, LEN_LOT_NO);
	}

	/**
	 * データバッファから賞味期限を取得します。
	 * @return		賞味期限
	 */
	public String getUseByDate()
	{
		return getColumn(OFF_USE_BY_DATE, LEN_USE_BY_DATE);
	}

	/**
	 * データバッファから製造日を取得します。
	 * @return		製造日
	 */
	public String getManufactureDate()
	{
		return getColumn(OFF_MANUFACTURE_DATE, LEN_MANUFACTURE_DATE);
	}

	/**
	 * データバッファから品質を取得します。
	 * @return		品質
	 */
	public String getQuality()
	{
		return getColumn(OFF_QUALITY, LEN_QUALITY);
	}

	/**
	 * データバッファから入荷予定数を取得します。
	 * @return		入荷予定数
	 */
	public int getInstockPlanQty()
	{
		return getIntColumn(OFF_PLAN_QTY, LEN_PLAN_QTY);
	}
	
	/**
	 * データバッファから入荷実績数を取得します。
	 * @return		入荷実績数
	 */
	public int getInstockResultQty()
	{
		return getIntColumn(OFF_RESULT_QTY, LEN_RESULT_QTY);
	}	

	/**
	 * データバッファから分納区分を取得します。
	 * @return		分納区分
	 */
	public String getInstallmentDelivery()
	{
		return getColumn(OFF_DIVIDE_FLAG, LEN_DIVIDE_FLAG);
	}	

	/**
	 * データバッファから作業時間を取得します。
	 * @return		作業時間
	 */
	public int getWorkSeconds()
	{
		return getIntColumn(OFF_WORK_SECONDS, LEN_WORK_TIME);
	}	

	/**
	 * データバッファに仕入先コードをセットします。
	 * @param	supplierCode	仕入先コード
	 */
	public void setSupplierCode(String supplierCode)
	{
		setColumn(supplierCode, OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE);
	}
	
	/**
	 * データバッファに出荷先コードをセットします。
	 * @param	customerCode	出荷先コード
	 */
	public void setCustomerCode(String customerCode)
	{
		setColumn(customerCode, OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
	}
	
	/**
	 * データバッファに伝票番号をセットします。
	 * @param	ticketNo		伝票番号
	 */
	public void setInstockTicketNo(String ticketNo)
	{
		setColumn(ticketNo, OFF_INSTOCK_TICKET_NO, LEN_TICKET_NO);
	}
	
	/**
	 * データバッファに伝票行Noをセットします。
	 * @param	lineNo			伝票行No
	 */
	public void setInstockTicketLineNo(int lineNo)
	{
		setIntColumn(lineNo, OFF_INSTOCK_TICKET_LINE_NO, LEN_TICKET_LINE_NO);
	}

	/**
	 * データバッファに集約作業No（商品識別コード）をセットします。
	 * @param	collectJobNo	集約作業No
	 */
	public void setCollectJobNo(String collectJobNo)
	{
		setColumn(collectJobNo, OFF_ITEM_ID, LEN_ITEM_ID);
	}
	
	/**
	 * データバッファに商品コードをセットします。
	 * @param	itemCode		商品コード
	 */
	public void setItemCode(String itemCode)
	{
		setColumn(itemCode, OFF_ITEM_CODE, LEN_ITEM_CODE);
	}

	/**
	 * データバッファにJANコードをセットします。
	 * @param	janCode			JANコード
	 */
	public void setJanCode(String janCode)
	{
		setColumn(janCode, OFF_JAN_CODE, LEN_JAN_CODE);
	}

	/**
	 * データバッファにボールITFをセットします。
	 * @param	bundleItf		ボールITF
	 */
	public void setBundleItf(String bundleItf)
	{
		setColumn(bundleItf, OFF_BUNDLE_ITF, LEN_BUNDLE_ITF);
	}

	/**
	 * データバッファにケースITFをセットします。
	 * @param	itf				ケースITF
	 */
	public void setItf(String itf)
	{
		setColumn(itf, OFF_ITF, LEN_ITF);
	}

	/**
	 * データバッファに商品名をセットします。
	 * @param	itemName		商品名
	 */
	public void setItemName(String itemName)
	{
		setColumn(itemName, OFF_ITEM_NAME, LEN_ITEM_NAME);
	}

	/**
	 * データバッファに商品分類をセットします。
	 * @param	itemCategoryCode	商品分類
	 */
	public void setItemCategoryCode(String itemCategoryCode)
	{
		setColumn(itemCategoryCode, OFF_ITEM_CATEGORY_CODE, LEN_ITEM_CATEGORY_CODE);
	}

	/**
	 * データバッファにボール入り数をセットします。
	 * @param	bundleEnteringQty	ボール入り数
	 */
	public void setBundleEnteringQty(int bundleEnteringQty)
	{
		setIntColumn(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}

	/**
	 * データバッファにケース入り数をセットします。
	 * @param	enteringQty		ケース入り数
	 */
	public void setEnteringQty(int enteringQty)
	{
		setIntColumn(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}

	/**
	 * データバッファに単位をセットします。
	 * @param	unit			単位
	 */
	public void setUnit(String unit)
	{
		setColumn(unit, OFF_UNIT, LEN_UNIT);
	}

	/**
	 * データバッファにロットNoをセットします。
	 * @param	lotNo			ロットNo
	 */
	public void setLotNo(String lotNo)
	{
		setColumn(lotNo, OFF_LOT_NO, LEN_LOT_NO);
	}

	/**
	 * データバッファに賞味期限をセットします。
	 * @param	useByDate		賞味期限
	 */
	public void setUseByDate(String useByDate)
	{
		setColumn(useByDate, OFF_USE_BY_DATE, LEN_USE_BY_DATE);
	}

	/**
	 * データバッファに製造日をセットします。
	 * @param	manufactureDate	製造日
	 */
	public void setManufactureDate(String manufactureDate)
	{
		setColumn(manufactureDate, OFF_MANUFACTURE_DATE, LEN_MANUFACTURE_DATE);
	}

	/**
	 * データバッファに品質をセットします。
	 * @param	quality			品質
	 */
	public void setQuality(String quality)
	{
		setColumn(quality, OFF_QUALITY, LEN_QUALITY);
	}

	/**
	 * データバッファに入荷予定数をセットします。
	 * @param	planQty			入荷予定数
	 */
	public void setInstockPlanQty(int planQty)
	{
		setIntColumn(planQty, OFF_PLAN_QTY, LEN_PLAN_QTY);
	}
	
	/**
	 * データバッファに入荷実績数をセットします。
	 * @param	resultQty		入荷実績数
	 */
	public void setInstockResultQty(int resultQty)
	{
		setIntColumn(resultQty, OFF_RESULT_QTY, LEN_RESULT_QTY);
	}	

	/**
	 * データバッファに分納区分をセットします。
	 * @param	installmentDelivery	分納区分
	 */
	public void setInstallmentDelivery(String installmentDelivery)
	{
		setColumn(installmentDelivery, OFF_DIVIDE_FLAG, LEN_DIVIDE_FLAG);
	}

	/**
	 * データバッファに作業時間をセットします。
	 * @param	workSeconds		作業時間
	 */
	public void setWorkSeconds(int workSeconds)
	{
		setIntColumn(workSeconds, OFF_WORK_SECONDS, LEN_WORK_TIME);
	}	
}
//end of class
