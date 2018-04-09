// $Id: Id5532DataFile.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2004-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.io.IOException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.rft.IdMessage;
import jp.co.daifuku.wms.base.rft.WorkDataFile;
import jp.co.daifuku.wms.base.rft.WorkingInformation;

/**
 * ID0060、ID0061で送受信するデータファイルを操作するためのクラスです。
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author $Author: mori $
 */
public class Id5532DataFile extends WorkDataFile
{
	private static final int OFF_CUSTOMER_CODE = OFF_LINE_NO + LEN_LINE_NO;

	private static final int OFF_TICKET_NO = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;

	private static final int OFF_TICKET_LINE_NO = OFF_TICKET_NO + LEN_TICKET_NO;

	private static final int OFF_COLLECT_JOB_NO = OFF_TICKET_LINE_NO + LEN_TICKET_LINE_NO;

	private static final int OFF_ITEM_CODE = OFF_COLLECT_JOB_NO + LEN_ITEM_ID;

	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE;

	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE;

	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF;

	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF;

	private static final int OFF_ITEM_CATEGORY = OFF_ITEM_NAME + LEN_ITEM_NAME;

	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_CATEGORY + LEN_ITEM_CATEGORY_CODE;

	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY;

	private static final int OFF_UNIT_KEY = OFF_ENTERING_QTY + LEN_ENTERING_QTY;

	private static final int OFF_LOT_NO = OFF_UNIT_KEY + LEN_UNIT;

	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO;
	
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE;

	private static final int OFF_QUALITY = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE;

	private static final int OFF_PLAN_QTY = OFF_QUALITY + LEN_QUALITY;

	private static final int OFF_RESULT_QTY = OFF_PLAN_QTY + LEN_PLAN_QTY;
	
	private static final int OFF_RESERVE = OFF_RESULT_QTY + LEN_RESULT_QTY;

	/**
	 * 作業時間のオフセットの定義
	 */
	private static final int OFF_WORK_SECONDS = OFF_RESERVE + LEN_RESERVE;

	/**
	 * 出荷先単位出荷検品データファイル名を表すフィールド
	 */
	static final String ANS_FILE_NAME = "ID5532.txt" ;

	/**
	 * 保留のデフォルト値を表すフィールド
	 */
	static final String DEFAULT_PENDING_FLAG = "0" ;

	/**
	 * コンストラクタ。
	 */
	public Id5532DataFile()
	{
		lineLength = OFF_WORK_SECONDS + LEN_WORK_TIME;
	}

	public Id5532DataFile(String filename)
	{
		super(filename);
		lineLength = OFF_WORK_SECONDS + LEN_WORK_TIME;
	}

	/**
	 * FTPデータファイルからデータを読み取り、適切な型のEntity配列に入れて返します。
	 * 実際の配列の型はIDによって異なります。
	 * @return		ファイルから読み取ったデータ(Entity配列)
	 * @throws IOException		ファイルの入出力中にエラーが発生した時に通知されます。
	 * @throws InvalidStatusException 状態を持つオブジェクトに対して、範囲外の状態をセットした場合に通知されます。
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
	 * @throws IOException		ファイルの入出力中にエラーが発生した時に通知されます。
	 * @throws InvalidStatusException 状態を持つオブジェクトに対して、範囲外の状態をセットした場合に通知されます。
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
	 * @throws IOException		ファイルの入出力中にエラーが発生した時に通知されます。
	 * @throws InvalidStatusException 状態を持つオブジェクトに対して、範囲外の状態をセットした場合に通知されます。
	 */
    public Entity[] getWorkDataFromFile(String filename) throws IOException,
            InvalidStatusException
    {
        Vector v = new Vector();
		
		openReadOnly(filename);
			
		for (next(); currentLine != null; next())
		{
			WorkingInformation wi = new WorkingInformation();
			
			wi.setCustomerCode(getCustomerCode());
			wi.setCollectJobNo(getCollectJobNo());
			wi.setShippingTicketNo(getTicketNo());
			wi.setShippingLineNo(new Integer(getTicketLineNo()).intValue());
			wi.setItemCode(getJANCode());
			wi.setBundleItf(getBundleITF());
			wi.setItf(getITF());
			wi.setResultUseByDate(getUseByDate());
			wi.setPlanEnableQty(getShippingPlanQty());
			wi.setResultQty(getShippingResultQty());
			wi.setWorkTime(getWorkSeconds());
			v.addElement(wi);
		}
			
		closeReadOnly();

		WorkingInformation[] data = new WorkingInformation[v.size()];
		v.copyInto(data);
		return data;
	}

	/**
	 * 引数で指定されたWorkingInformation配列のデータをファイル書き込みます。
	 * 
	 * @throws IOException	ファイルの入出力中にエラーが発生した時に通知されます。
	 */
	public void write(Entity[] obj) throws IOException
	{
		WorkingInformation[] workinfo = (WorkingInformation[]) obj;
		
		openWritable();
			
		for (int i = 0; i < workinfo.length; i ++)
		{
		    setLineNo(i);
			setCustomerCode(workinfo[i].getCustomerCode());
			setTicketNo(workinfo[i].getShippingTicketNo());
			setTicketLineNo(workinfo[i].getShippingLineNo());
			setCollectJobNo(workinfo[i].getCollectJobNo());
			setJanCode(workinfo[i].getItemCode());
			setBundleItf(workinfo[i].getBundleItf());
			setItf(workinfo[i].getItf());
			setItemName(workinfo[i].getItemName1());
			setBundleEnteringQty(workinfo[i].getBundleEnteringQty());
			setEnteringQty(workinfo[i].getEnteringQty());
			setLotNo(workinfo[i].getLotNo());
			setUseByDate(workinfo[i].getUseByDate());
			setQuality("0");
			setShippingPlanQty(workinfo[i].getPlanEnableQty());
			setShippingResultQty(workinfo[i].getResultQty());
			setPendingFlag(DEFAULT_PENDING_FLAG);
			setWorkSeconds(0);
			
			writeln();
		}
			
		closeWritable();
	}
	
	/**
	 * 出荷先単位出荷検品開始応答電文から荷主、予定日を取得してエンティティ配列にセットします。
	 * @param	workinfo	作業中データ
	 * @param	request	出荷先単位出荷検品開始応答電文
	 */
	public void setRequestInfo(WorkingInformation[] workinfo, IdMessage request)
	{
	    RFTId0532 id0060 = (RFTId0532) request;

	    for (int i = 0; i < workinfo.length; i ++)
	    {
	        workinfo[i].setConsignorCode(id0060.getConsignorCode());
	        workinfo[i].setPlanDate(id0060.getShippingPlanDate());
	    }
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
	 * データバッファから集約作業Noを取得します。
	 * @return		集約作業No
	 */
	public String getCollectJobNo()
	{
		return getColumn(OFF_COLLECT_JOB_NO, LEN_ITEM_ID) ;
	}

	/**
	 * データバッファから伝票番号を取得します。
	 * @return		伝票番号
	 */
	public String getTicketNo()
	{
		return getColumn(OFF_TICKET_NO, LEN_TICKET_NO) ;
	}
	
	/**
	 * データバッファから伝票行No.を取得します。
	 * @return		伝票行No.
	 */
	public String getTicketLineNo()
	{
		return getColumn(OFF_TICKET_LINE_NO, LEN_TICKET_LINE_NO) ;
	}
	
	/**
	 * データバッファからJANコードを取得します。
	 * @return		JANコード
	 */
	public String getJANCode()
	{
		return getColumn(OFF_JAN_CODE, LEN_JAN_CODE) ;
	}
	
	/**
	 * データバッファからボールITFを取得します。
	 * @return		ボールITF
	 */
	public String getBundleITF()
	{
		return getColumn(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF) ;
	}
	
	/**
	 * データバッファからケースITFを取得します。
	 * @return		ケースITF
	 */
	public String getITF()
	{
		return getColumn(OFF_ITF, LEN_ITF) ;
	}
	
	/**
	 * データバッファから賞味期限を取得します。
	 * @return		賞味期限
	 */
	public String getUseByDate()
	{
		return getColumn(OFF_USE_BY_DATE, LEN_USE_BY_DATE) ;
	}
	
	/**
	 * データバッファから出荷予定数を取得します。
	 * @return		出荷予定数
	 */
	public int getShippingPlanQty()
	{
		return getIntColumn(OFF_PLAN_QTY, LEN_PLAN_QTY) ;
	}
	
	/**
	 * データバッファから出荷実績数を取得します。
	 * @return		出荷実績数
	 */
	public int getShippingResultQty()
	{
		return getIntColumn(OFF_RESULT_QTY, LEN_RESULT_QTY) ;
	}	

	/**
	 * データバッファから作業時間を取得します。
	 * @return		作業時間
	 */
	public int getWorkSeconds()
	{
		return getIntColumn(OFF_WORK_SECONDS, LEN_WORK_TIME) ;
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
	public void setTicketNo(String ticketNo)
	{
		setColumn(ticketNo, OFF_TICKET_NO, LEN_TICKET_NO);
	}
	
	/**
	 * データバッファに伝票行Noをセットします。
	 * @param	lineNo			伝票行No
	 */
	public void setTicketLineNo(int lineNo)
	{
		setIntColumn(lineNo, OFF_TICKET_LINE_NO, LEN_TICKET_LINE_NO);
	}

	/**
	 * データバッファに集約作業Noをセットします。
	 * @param	collectJobNo	集約作業No
	 */
	public void setCollectJobNo(String collectJobNo)
	{
		setColumn(collectJobNo, OFF_COLLECT_JOB_NO, LEN_COLLECT_JOB_NO);
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
		setColumn(itemCategoryCode, OFF_ITEM_CATEGORY, LEN_ITEM_CATEGORY_CODE);
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
		setColumn(unit, OFF_UNIT_KEY, LEN_UNIT);
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
	 * @param	planQty			出荷予定数
	 */
	public void setShippingPlanQty(int planQty)
	{
		setIntColumn(planQty, OFF_PLAN_QTY, LEN_PLAN_QTY);
	}
	
	/**
	 * データバッファに入荷実績数をセットします。
	 * @param	resultQty		出荷実績数
	 */
	public void setShippingResultQty(int resultQty)
	{
		setIntColumn(resultQty, OFF_RESULT_QTY, LEN_RESULT_QTY);
	}	

	/**
	 * データバッファに分納フラグをセットします。
	 * @param	pendingFlag		分納フラグ
	 */
	public void setPendingFlag(String pendingFlag)
	{
		setColumn(pendingFlag, OFF_RESERVE, LEN_RESERVE);
	}	

	/**
	 * データバッファに作業時間をセットします。
	 * @param	workSeconds		作業時間
	 */
	public void setWorkSeconds(int workSeconds)
	{
		setIntColumn(workSeconds, OFF_WORK_SECONDS, LEN_WORK_TIME);
	}
	
	/**
	 * 出荷先単位出荷検品開始応答電文から荷主、予定日を取得してエンティティ配列にセットします。
	 * @param	workinfo	作業中データ
	 * @param	response	出荷先単位出荷検品開始応答電文
	 */
	public void setRequestInfo(jp.co.daifuku.wms.base.entity.WorkingInformation[] workinfo, IdMessage response)
	{
	    RFTId5532 id5532 = (RFTId5532) response;

	    for (int i = 0; i < workinfo.length; i ++)
	    {
	        workinfo[i].setConsignorCode(id5532.getConsignorCode());
	        workinfo[i].setPlanDate(id5532.getShippingPlanDate());
	    }
	}
}
