// $Id: Id5430DataFile.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

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
 * Designer : Konishi <BR>
 * Maker :  Y.Taki <BR>
 * <BR>
 * ID5430で送受信するデータファイルを操作するためのクラスです。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>ID5430のファイルの各行の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>行No</td>			<td> 4 byte</td><td></td></tr>
 * <tr><td>設定単位キー</td>	<td>16 byte</td><td>未使用</td></tr>
 * <tr><td>出庫No</td>			<td> 8 byte</td><td>未使用</td></tr>
 * <tr><td>出荷先コード</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>出荷先名称</td>		<td>40 byte</td><td></td></tr>
 * <tr><td>商品識別コード</td>	<td>16 byte</td><td>集約作業No</td></tr>
 * <tr><td>商品コード</td>		<td>16 byte</td><td>未使用</td></tr>
 * <tr><td>JANコード</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>ボールITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>ケースITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>商品名</td>			<td>40 byte</td><td></td></tr>
 * <tr><td>商品分類</td>		<td> 4 byte</td><td>未使用</td></tr>
 * <tr><td>ボール入数</td>		<td> 6 byte</td><td></td></tr>
 * <tr><td>ケース入数</td>		<td> 6 byte</td><td></td></tr>
 * <tr><td>単位</td>			<td> 6 byte</td><td>未使用</td></tr>
 * <tr><td>ロットNo</td>		<td>10 byte</td><td>未使用</td></tr>
 * <tr><td>賞味期限</td>		<td> 8 byte</td><td>未使用</td></tr>
 * <tr><td>製造日</td>			<td> 8 byte</td><td>未使用</td></tr>
 * <tr><td>エリアNo</td>		<td> 3 byte</td><td></td></tr>
 * <tr><td>ロケーション</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>仕分先割付順序</td>	<td> 4 byte</td><td>未使用</td></tr>
 * <tr><td>荷揃えNo</td>		<td> 8 byte</td><td>未使用</td></tr>
 * <tr><td>仕分予定数</td>		<td> 9 byte</td><td></td></tr>
 * <tr><td>仕分実績数</td>		<td> 9 byte</td><td></td></tr>
 * <tr><td>作業時間</td>		<td> 5 byte</td><td></td></tr>
 * <tr><td>完了フラグ</td>		<td> 1 byte</td><td>0:未仕分<BR>1:完了<BR>2:欠品完了</td></tr>
 * <tr><td>CRLF</td>			<td> 2 byte</td><td>CR + LF</td></tr>
 * </table>
 * </p>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author $Author: mori $
 */
public class Id5430DataFile extends WorkDataFile
{
	// Class fields----------------------------------------------------

	/**
	 * 設定単位キーのオフセットの定義
	 */
	private static final int OFF_SET_UNIT_KEY = OFF_LINE_NO + LEN_LINE_NO;

	/**
	 * 出庫Noのオフセットの定義
	 */
	private static final int OFF_PICKING_NO = OFF_SET_UNIT_KEY + LEN_SET_UNIT_KEY;

	/**
	 * 出荷先コードのオフセットの定義
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_PICKING_NO + LEN_PICKING_NO;

	/**
	 * 出荷先名称のオフセットの定義
	 */
	private static final int OFF_CUSTOMER_NAME = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;

	/**
	 * 商品識別コード（集約作業No）のオフセットの定義
	 */
	private static final int OFF_ITEM_ID = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;

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
	 * エリアNoのオフセットの定義
	 */
	private static final int OFF_AREA_NO = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE;

	/**
	 * ロケーションのオフセットの定義
	 */
	private static final int OFF_LOCATION = OFF_AREA_NO + LEN_AREA_NO;

	/**
	 * 仕分先割付順序のオフセットの定義
	 */
	private static final int OFF_ALLOTMENT_NO = OFF_LOCATION + LEN_LOCATION;

	/**
	 * 荷揃えNoのオフセットの定義
	 */
	private static final int OFF_GROUPING_NO = OFF_ALLOTMENT_NO + LEN_ALLOTMENT_NO;
	
	/**
	 * 仕分け予定数のオフセットの定義
	 */
	private static final int OFF_PLAN_QTY = OFF_GROUPING_NO + LEN_GROUPING_NO;

	/**
	 * 仕分実績数のオフセットの定義
	 */
	private static final int OFF_RESULT_QTY = OFF_PLAN_QTY + LEN_PLAN_QTY;
	
	/**
	 * 作業時間のオフセットの定義
	 */
	private static final int OFF_WORK_SECONDS = OFF_RESULT_QTY + LEN_RESULT_QTY;

	/**
	 * 完了フラグのオフセットの定義
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_WORK_SECONDS + LEN_WORK_TIME;

	/**
	 * 完了フラグ（未作業）
	 */
	public static final String COMPLETION_FLAG_UNWORK = "0";

	/**
	 * 完了フラグ（完了）
	 */
	public static final String COMPLETION_FLAG_COMPLETE = "1";

	/**
	 * 完了フラグ（欠品完了）
	 */
	public static final String COMPLETION_FLAG_SHORTAGE = "2";

	/**
	 * 仕分けファイル名を表すフィールド
	 */
	static final String FILE_NAME = "ID5430.txt" ;

	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public Id5430DataFile()
	{
		lineLength = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
	}

	/**
	 * コンストラクタ。
	 * @param filename ファイル名
	 */
	public Id5430DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
	}

	// Class method --------------------------------------------------
	// Public methods -----------------------------------------------
	/**
	 * FTPデータファイルからデータを読み取り、適切な型のEntity配列に入れて返します。
	 * 実際の配列の型はIDによって異なります。
	 * @return		ファイルから読み取ったデータ(Entity配列)
	 * @throws IOException		I/Oエラー発生
	 * @throws InvalidStatusException
	 */
    public Entity[] getCompletionData() throws IOException,
            InvalidStatusException
    {
        return getWorkDataFromFile(wFileName);
    }
    
	/**
	 * 作業中データファイルからデータを読み取り、適切な型のEntity配列に入れて返します。
	 * 実際の配列の型はIDによって異なります。
	 * @return		ファイルから読み取ったデータ(Entity配列)
	 * @throws IOException		I/Oエラー発生
	 * @throws InvalidStatusException
	 */
    public Entity[] getWorkingData() throws IOException,
            InvalidStatusException
    {
        return getWorkDataFromFile(workingDataFileName);
    }

	/**
	 * 指定されたファイルからデータを読み取り、適切な型のEntity配列に入れて返します。
	 * 実際の配列の型はIDによって異なります。
	 * @return		ファイルから読み取ったデータ(Entity配列)
	 * @throws IOException		I/Oエラー発生
	 * @throws InvalidStatusException
	 */
    public Entity[] getWorkDataFromFile(String filename) throws IOException,
            InvalidStatusException
    {
        Vector v = new Vector();
		
		openReadOnly(filename);
			
		for (next(); currentLine != null; next())
		{
			WorkingInformation workinfo = new WorkingInformation();
			
			workinfo.setCollectJobNo(getItemId());
			workinfo.setLocationNo(getLocation());
			workinfo.setCustomerCode(getCustomerCode());
			workinfo.setCustomerName1(getCustomerName());
			workinfo.setItemCode(getJanCode());
			workinfo.setItemName1(getItemName());
			workinfo.setPlanEnableQty(getPlanQty());
			workinfo.setResultQty(getResultQty());
			workinfo.setItf(getItf());
			workinfo.setBundleItf(getBundleItf());
			workinfo.setUseByDate(getUseByDate());
			workinfo.setResultLocationNo(getLocation());
			workinfo.setWorkTime(getWorkSeconds());
			workinfo.setStatusFlag(getCompletionFlag());

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
			setCustomerCode(workinfo[i].getCustomerCode());
			setCustomerName(workinfo[i].getCustomerName1());
			setItemId(workinfo[i].getCollectJobNo());
			setLocation(workinfo[i].getLocationNo());
			setPlanQty(workinfo[i].getPlanEnableQty());
			setResultQty(workinfo[i].getResultQty());
			setWorkSeconds(0);	
			setCompletionFlag("0");
			
			writeln();
		}
			
		closeWritable();
	}


	/**
	 * 行No.を取得します。
	 * @return 行No.
	 */
	public int getLineNo()
	{
		return getIntColumn(OFF_LINE_NO, LEN_LINE_NO);
	}
	
	/**
	 * 設定単位キーを取得します。
	 * @return 設定単位キー
	 */
	public String getSetUnitKey()
	{
		return getColumn(OFF_SET_UNIT_KEY, LEN_SET_UNIT_KEY);
	}

	/**
	 * 出庫Noを取得します。
	 * @return		出庫No
	 */
	public String getPickingNo()
	{
		return getColumn(OFF_PICKING_NO, LEN_PICKING_NO);
	}
	
	/**
	 * 出荷先コードを取得します。
	 * @return		出荷先コード
	 */
	public String getCustomerCode()
	{
		return getColumn(OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
	}

	/**
	 * 出荷先名称を取得します。
	 * @return		出荷先名称
	 */
	public String getCustomerName()
	{
		return getColumn(OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME);
	}

	/**
	 * 商品識別コード(集約作業No)を取得します。
	 * @return		商品識別コード
	 */
	public String getItemId()
	{
		return getColumn(OFF_ITEM_ID, LEN_ITEM_ID);
	}

	/**
	 * JANコードを取得します。
	 * @return		JANコード
	 */
	public String getJanCode()
	{
		return getColumn(OFF_JAN_CODE, LEN_JAN_CODE);
	}

	/**
	 * ボールITFを取得します。
	 * @return		ボールITF
	 */
	public String getBundleItf()
	{
		return getColumn(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF);
	}

	/**
	 * ケースITFを取得します。
	 * @return		ケースITF
	 */
	public String getItf()
	{
		return getColumn(OFF_ITF, LEN_ITF);
	}

	/**
	 * 商品名を取得します。
	 * @return		商品名
	 */
	public String getItemName()
	{
		return getColumn(OFF_ITEM_NAME, LEN_ITEM_NAME);
	}

	/**
	 * ボール入数を取得します。
	 * @return		ボール入数
	 */
	public int getBundleEnteringQty()
	{
		return getIntColumn(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}

	/**
	 * ケース入数を取得します。
	 * @return		ケース入数
	 */
	public int getEnteringQty()
	{
		return getIntColumn(OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}

	/**
	 * 賞味期限を取得します。
	 * @return		賞味期限
	 */
	public String getUseByDate()
	{
		return getColumn(OFF_USE_BY_DATE, LEN_USE_BY_DATE);
	}

	/**
	 * ロケーションを取得します。
	 * @return		ロケーション
	 */
	public String getLocation()
	{
		return getColumn(OFF_LOCATION, LEN_LOCATION);
	}

	/**
	 * 仕分予定数を取得します。
	 * @return		仕分予定数
	 */
	public int getPlanQty()
	{
		return getIntColumn(OFF_PLAN_QTY, LEN_PLAN_QTY);
	}

	/**
	 * 仕分実績数を取得します。
	 * @return		仕分実績数
	 */
	public int getResultQty()
	{
		return getIntColumn(OFF_RESULT_QTY, LEN_RESULT_QTY);
	}
	
	/**
	 * 作業時間を取得します。
	 * @return		作業時間
	 */
	public int getWorkSeconds ()
	{
		return getIntColumn(OFF_WORK_SECONDS, LEN_WORK_TIME);
	}

	/**
	 * 完了フラグを取得します。
	 * @return	pickingNo	完了フラグ
	 */
	public String getCompletionFlag()
	{
		return getColumn(OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG);
	}

	/**
	 * 行No.を取得します。
	 * @param lineNo
	 */
	public void setLineNo(int lineNo) 
	{
		setIntColumn(lineNo, OFF_LINE_NO, LEN_LINE_NO);
	}
	
	/**
	 * 出庫Noをセットします。
	 * @param	pickingNo	出庫No
	 */
	public void setPickingNo(String pickingNo)
	{
		setColumn(pickingNo, OFF_PICKING_NO, LEN_PICKING_NO);
	}
	
	/**
	 * 出荷先コードをセットします。
	 * @param	customerCode	出荷先コード
	 */
	public void setCustomerCode(String customerCode)
	{
		setColumn(customerCode, OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
	}

	/**
	 * 出荷先名称をセットします。
	 * @param	customerName	出荷先名称
	 */
	public void setCustomerName(String customerName)
	{
		setColumn(customerName, OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME);
	}

	/**
	 * 商品識別コードをセットします。
	 * @param	itemId		商品識別コード
	 */
	public void setItemId(String itemId)
	{
		setColumn(itemId, OFF_ITEM_ID, LEN_ITEM_ID);
	}

	/**
	 * JANコードをセットします。
	 * @param	janCode		JANコード
	 */
	public void setJanCode(String janCode)
	{
		setColumn(janCode, OFF_JAN_CODE, LEN_JAN_CODE);
	}

	/**
	 * ボールITFをセットします。
	 * @param	bundleItf	ボールITF
	 */
	public void setBundleItf(String bundleItf)
	{
		setColumn(bundleItf, OFF_BUNDLE_ITF, LEN_BUNDLE_ITF);
	}

	/**
	 * ケースITFをセットします。
	 * @param	caseItf		ケースITF
	 */
	public void setItf(String caseItf)
	{
		setColumn(caseItf, OFF_ITF, LEN_ITF);
	}

	/**
	 * 商品名をセットします。
	 * @param	itemName	商品名
	 */
	public void setItemName(String itemName)
	{
		setColumn(itemName, OFF_ITEM_NAME, LEN_ITEM_NAME);
	}

	/**
	 * ボール入数をセットします。
	 * @param	bundleEnteringQty	ボール入数
	 */
	public void setBundleEnteringQty(int bundleEnteringQty)
	{
		setIntColumn(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}

	/**
	 * ケース入数をセットします。
	 * @param	enteringQty			ケース入数
	 */
	public void setEnteringQty(int enteringQty)
	{
		setIntColumn(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}

	/**
	 * 賞味期限をセットします。
	 * @param	useByDate		賞味期限
	 */
	public void setUseByDate(String useByDate)
	{
		setColumn(useByDate, OFF_USE_BY_DATE, LEN_USE_BY_DATE);
	}

	/**
	 * ロケーションをセットします。
	 * @param	location		ロケーション
	 */
	public void setLocation(String location)
	{
		setColumn(location, OFF_LOCATION, LEN_LOCATION);
	}

	/**
	 * 仕分予定数をセットします。
	 * @param	planQty			仕分予定数
	 */
	public void setPlanQty(int planQty)
	{
		setIntColumn(planQty, OFF_PLAN_QTY, LEN_PLAN_QTY);
	}

	/**
	 * 仕分実績数をセットします。
	 * @param	resultQty		仕分実績数
	 */
	public void setResultQty(int resultQty)
	{
		setIntColumn(resultQty, OFF_RESULT_QTY, LEN_RESULT_QTY);
	}
	
	/**
	 * 作業時間をセットします。
	 * @param	
	 */
	public void setWorkSeconds(int resultQty)
	{
		setIntColumn(resultQty, OFF_WORK_SECONDS, LEN_WORK_TIME);
	}	
	
	/**
	 * 完了フラグをセットします。
	 * @param	completionFlag	完了フラグ
	 */
	public void setCompletionFlag(String completionFlag)
	{
		setColumn(completionFlag, OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG);
	}

}
//end of class
