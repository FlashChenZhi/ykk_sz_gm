package jp.co.daifuku.wms.sorting.schedule;

import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.Parameter;

/*
 * Created on 2004/10/26
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * クラスは、仕分パッケージ内の画面～スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは仕分パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 *     作業者コード <BR>
 *     パスワード <BR>
 *     作業者名称 <BR>
 *     荷主コード <BR>
 *     荷主名称 <BR>     
 *     出荷先コード <BR>
 *     出荷先名称 <BR>
 *     商品コード <BR>
 *     商品コード(リストセル) <BR>
 *     商品名称 <BR>
 *     表示順１ <BR>
 * 	   出荷伝票No.<BR>
 *     出荷行No.<BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     予定ケース数<BR>
 *     予定ピース数<BR>
 *     実績ケース数<BR>
 * 	   実績ピース数<BR>
 *     欠品区分<BR>
 *     賞味期限 <BR>
 *     開始伝票No.<BR>
 *     終了伝票No.<BR>
 *     作業状態<BR>
 *     作業状態(リストセル)<BR>
 *     作業状態名称<BR>
 *     仕入先コード<BR>
 * 	   仕入先名<BR>
 *     入荷伝票No.<BR>
 *     入荷行No.<BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     欠品ケース数 <BR>
 *     欠品ピース数 <BR> 
 *     ＴＣ/ＤＣ区分<BR>
 *     ＴＣ/ＤＣ区分(名称)<BR>
 *     仕分作業リスト発行区分<BR>
 *     仕分予定削除一覧リスト発行区分<BR>
 *     予定総数<BR>
 *     実績総数<BR>
 *     仕分先数<BR>
 *     ためうち行No.<BR>
 *     ケース/ピース区分<BR>
 *     ケース/ピース区分(名称)<BR>
 *     バッチNo.(スケジュールNo.)<BR>
 *     登録区分<BR>
 *     登録日時<BR>
 *     登録処理名<BR>
 *     最終更新日時<BR>
 *     最終更新処理名<BR>
 *     作業No.<BR>
 *     画面機能フラグ<BR>
 *     検索フラグ<BR>
 *     選択Box有効/無効フラグ<BR>
 *     選択BoxON/OFF<BR>
 *     作業状態配列<BR>
 *     開始仕分予定日<BR>
 *	   終了仕分予定日<BR>
 *	   仕分予定日<BR>
 *     開始仕分日<BR>
 *     終了仕分日<BR>
 *     仕分日<BR>
 *     仕分場所<BR>
 *     開始仕分場所<BR>
 *     終了仕分場所<BR>
 *     ケース仕分場所<BR>
 *     ピース仕分場所<BR>
 *     ボタン種別<BR>
 *     報告フラグ<BR>
 * 
 * 
 * <CODE>SortingParameter.java</CODE><BR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/26</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * @author suresh kayamboo
 * @version 2004/10/26
 */
public class SortingParameter extends Parameter
{
    //SortingParameter
    //Constants-----------------------------------
    /**
	 * ケース/ピース区分 ケース
	 */
	public static final String CASEPIECE_FLAG_CASE = "1" ;
	/**
	 * ケース/ピース区分 ピース
	 */
	public static final String CASEPIECE_FLAG_PIECE = "2" ;

	/**
	 * ケース/ピース区分 指定なし
	 */
	public static final String CASEPIECE_FLAG_NOTHING = "3" ;

	/**
	 * ケース/ピース区分 混合
	 */
	public static final String CASEPIECE_FLAG_MIXED = "9" ;

	/**
	 * ケース/ピース 区分 全て(ケースピース同一)
	 */
	public static final String CASEPIECE_FLAG_ALL_SAME = "97" ;

	/**
	 * ケース/ピース 区分 全て(ケースピース別)
	 */
	public static final String CASEPIECE_FLAG_ALL_DISTINCTION = "98" ;

	/**
	 * ケース/ピース 区分 全て
	 */
	public static final String CASEPIECE_FLAG_ALL = "99" ;

	/**
	 * 作業状態フラグ 未開始
	 */
	public static final String STATUS_FLAG_UNSTARTED = "0" ;

	/**
	 * 作業状態フラグ 開始
	 */
	public static final String STATUS_FLAG_STARTED = "1" ;

	/**
	 * 作業状態フラグ 作業中
	 */
	public static final String STATUS_FLAG_WORKING = "2" ;

	/**
	 * 作業状態フラグ 一部完了
	 */
	public static final String STATUS_FLAG_PARTIAL_COMPLETION = "3" ;

	/**
	 * 作業状態フラグ 完了
	 */
	public static final String STATUS_FLAG_COMPLETION = "4" ;

	/**
	 * 作業状態フラグ 削除
	 */
	public static final String STATUS_FLAG_DELETE = "9" ;

	/**
	 * 作業状態フラグ 全て
	 */
	public static final String STATUS_FLAG_ALL = "99" ;
	
	/**
	 * TC/DC区分 DC
	 */
	public static final String TCDC_FLAG_DC = "0" ;
	/**
	 * TC/DC区分 クロスドック
	 */
	public static final String TCDC_FLAG_CROSSTC = "1" ;
	/**
	 * TC/DC区分 TC
	 */
	public static final String TCDC_FLAG_TC = "2" ;
	/**
	 * TC/DC区分 全て
	 */
	public static final String TCDC_FLAG_ALL = "99" ;

	/**
	 * 検索フラグ（仕分用）：予定
	 */
	public static final String SEARCH_SORTING_PLAN = "0" ;

	/**
	 * 検索フラグ（仕分用）：作業
	 */
	public static final String SEARCH_SORTING_WORK = "1" ;

	/**
	 * 検索フラグ（仕分用）：実績
	 */
	public static final String SEARCH_SORTING_QTY = "2" ;

	/**
	 * 範囲フラグ（開始）
	 */
	public static final String RANGE_START = "0";

	/**
	 * 範囲フラグ（終了）
	 */
	public static final String RANGE_END = "1";
	
	/**
	 * 範囲フラグ（ケース）
	 */
	public static final String RANGE_CASE = "2";

	/**
	 * 範囲フラグ（ピース）
	 */
	public static final String RANGE_PIECE = "3";

	/**
	 * ボタン種別(修正登録)
	 */
	public static final String BUTTON_MODIFYSUBMIT = "0";

	/**
	 * ボタン種別(一括作業中解除)
	 */
	public static final String BUTTON_ALLWORKINGCLEAR = "1";

	/**
	 * 選択BoxON/OFF（ON）
	 */
	public static final boolean SELECT_BOX_ON = true;

	/**
	 * 選択BoxON/OFF（OFF）
	 */
	public static final boolean SELECT_BOX_OFF = false;

	/**
	 * 実績報告フラグ（未報告）
	 */
	public static final String REPORT_FLAG_NOT_SENT = "0";

	/**
	 * 実績報告フラグ（報告済）
	 */
	public static final String REPORT_FLAG_SENT = "1";

	/**
	 * データ変更フラグ（変更なし）
	 */
	public static final String UPDATEFLAG_NO = "0";

	/**
	 * データ変更フラグ（変更あり）
	 */
	public static final String UPDATEFLAG_YES = "1";

    //Attributes----------------------------------
    /**
	 * 作業者コード
	 */
	private String wWorkerCode;

	/**
	 * パスワード
	 */
	private String wPassword;

	/**
	 * 作業者名称
	 */
	private String wWorkerName;

	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

	/**
	 * 荷主名称
	 */
	private String wConsignorName;
	
	/**
	 * 出荷先コード
	 */
	private String wCustomerCode;

	/**
	 * 出荷先名称
	 */
	private String wCustomerName;
	
	/**
	 * 商品コード
	 */
	private String wItemCode;

	/**
	 * 商品コード(リストセル)
	 */
	private String wItemCodeL;

	/**
	 * 商品名称
	 */
	private String wItemName;

	/**
	 * 表示順１
	 */
	private String wDspOrder;
	/**
	 * 出荷伝票No.
	 */
	private String wShippingTicketNo;

	/**
	 * 出荷行No.
	 */
	private int wShippingLineNo;

	/**
	 * ケース入数
	 */
	private int wEnteringQty;

	/**
	 * ボール入数
	 */
	private int wBundleEnteringQty;

	/**
	 * 予定ケース数
	 */
	private int wPlanCaseQty;

	/**
	 * 予定ピース数
	 */
	private int wPlanPieceQty;

	/**
	 * 実績ケース数
	 */
	private int wResultCaseQty;

	/**
	 * 実績ピース数
	 */
	private int wResultPieceQty;

	/**
	 * 欠品区分
	 */
	private boolean wShortage = false;

	/**
	 * 賞味期限
	 */
	private String wUseByDate;

	
	/**
	 * 開始伝票No.
	 */
	private String wFromTicketNo;

	/**
	 * 終了伝票No.
	 */
	private String wToTicketNo;

	/**
	 * 作業状態
	 */
	private String wStatusFlag;

	/**
	 * 作業状態(リストセル)
	 */
	private String wStatusFlagL;

	/**
	 * 作業状態名称
	 */
	private String wStatusName;

	/**
	 * 仕入先コード
	 */
	private String wSupplierCode;

	/**
	 * 仕入先名称
	 */
	private String wSupplierName;

	/**
	 * 入荷伝票No.
	 */
	private String wInstockTicketNo;

	/**
	 * 入荷行No.
	 */
	private int wInstockLineNo;

	/**
	 * ケースITF
	 */
	private String wITF;

	/**
	 * ボールITF
	 */
	private String wBundleITF;

	/**
	 * 欠品ケース数
	 */
	private int wShortageCaseQty;

	/**
	 * 欠品ピース数
	 */
	private int wShortagePieceQty;

	/**
	 * ＴＣ/ＤＣ区分
	 */
	private String wTcdcFlag;

	/**
	 * ＴＣ/ＤＣ区分(名称)
	 */
	private String wTcdcName;

	/**
	 * 仕分予定削除一覧リスト発行区分
	 */
	private boolean wSortingDeleteListFlg = false;

	/**
	 * 仕分作業リスト発行区分
	 */
	private boolean wSortingWorkListFlg = false;

	/**
	 * 予定総数
	 */
	private int wTotalPlanQty;

	/**
	 * 実績総数
	 */
	private int wTotalResultQty;

	/**
	 * 仕分先数
	 */
	private int wSortingQty;

	/**
	 * ためうち行No.
	 */
	private int wRowNo;

	/**
	 * 仕分予定一意キー
	 */
	private String wSortingPlanUkey;

	/**
	 * ケース/ピース区分
	 */
	private String wCasePieceFlag;
	
	/**
	 * ケース/ピース区分(名称)
	 */
	private String wCasePieceName;

	/**
	 * バッチNo.(スケジュールNo.)
	 */
	private String wBatchNo;

	/**
	 * 登録区分
	 */
	private String wRegistKbn;

	/**
	 * 登録日時
	 */
	private java.util.Date wRegistDate;

	/**
	 * 登録処理名
	 */
	private String wRegistPName;

	/**
	 * 最終更新日時
	 */
	private java.util.Date wLastUpdateDate;

	/**
	 * 最終更新日時(Vector)
	 */
	private Vector wLastUpdateDateList;

	/**
	 * 最終更新処理名
	 */
	private String wLastUpdatePName;

	/**
	 * 作業No.
	 */
	private String wJobNo;

	/**
	 * 作業No.(Vector)
	 */
	private Vector wJobNoList;

	/**
	 * 検索フラグ(予定／実績)
	 */
	private String wSearchFlag;

	/**
	 * 選択Box有効/無効フラグ
	 */
	private boolean wSelectBoxFlag = true;

	/**
	 * 選択BoxON/OFF
	 */
	private boolean wSelectBoxCheck = true;

	/**
	 * 作業状態配列
	 */
	private String[] wSearchStatus;
	
	/**
	 * 開始仕分予定日
	 */
	private String wFromPlanDate;

	/**
	 * 終了仕分予定日
	 */
	private String wToPlanDate;

	/**
	 * 仕分予定日
	 */
	private String wPlanDate;

	/**
	 * 開始仕分日
	 */
	private String wFromSortingDate;

	/**
	 * 終了仕分日
	 */
	private String wToSortingDate;

	/**
	 * 仕分日
	 */
	private String wSortingDate;
	
	/**
	 * 仕分場所
	 *
	 */
	private String wSortingLocation ;
	
	/**
	 * 開始仕分場所
	 */
	private String wFromSortingLocation ;
	
	/**
	 * 終了仕分場所
	 *
	 */
	private String wToSortingLocation ;
	
	/**
	 * ケース仕分場所
	 *
	 */
	private String wCaseSortingLocation ;
	
	/**
	 * ピース仕分場所
	 *
	 */
	private String wPieceSortingLocation ;
	
	/**
	 * ボタン種別
	 */
	private String wButtonType;
	
	/**
	 * 報告フラグ
	 */
	private String wReportFlag;
	
	/**
	 * 報告フラグ名称
	 */
	private String wReportFlagName;
	
	private boolean wLineCheckFlag = true;
    //Static--------------------------------------
    
    
    //Constructor----------------------------------
    public SortingParameter()
    {
        
    }
    //Public---------------------------------------
    /**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:34 $");
	}
    
	
    //Protected------------------------------------
    //Private--------------------------------------
    
    //InnerClasses---------------------------------
    
    /**
     * バッチNo.(スケジュールNo.) を　取得します。
     * @return Returns the wBatchNo.
     */
    public String getBatchNo()
    {
        return wBatchNo;
    }
    /**
     * バッチNo.(スケジュールNo.)をセットする。
     * @param batchNo The wBatchNo to set.
     */
    public void setBatchNo(String batchNo)
    {
        wBatchNo = batchNo;
    }
    /**
     * ボール入数を取得する。
     * @return Returns the wBundleEnteringQty.
     */
    public int getBundleEnteringQty()
    {
        return wBundleEnteringQty;
    }
    /**
     * ボール入数をセットする。
     * @param bundleEnteringQty The wBundleEnteringQty to set.
     */
    public void setBundleEnteringQty(int bundleEnteringQty)
    {
        wBundleEnteringQty = bundleEnteringQty;
    }
    /**
     * ボールＩＴＦ　を取得する。
     * @return Returns the wBundleITF.
     */
    public String getBundleITF()
    {
        return wBundleITF;
    }
    /**
     * ボールＩＴＦをセットする。
     * @param bundleITF The wBundleITF to set.
     */
    public void setBundleITF(String bundleITF)
    {
        wBundleITF = bundleITF;
    }
    /**
     * ケース/ピース区分を取得する。
     * @return Returns the wCasePieceFlag.
     */
    public String getCasePieceFlag()
    {
        return wCasePieceFlag;
    }
    /**
     * ケース/ピース区分をセットする。
     * @param casePieceFlag The wCasePieceFlag to set.
     */
    public void setCasePieceFlag(String casePieceFlag)
    {
        wCasePieceFlag = casePieceFlag;
    }
    
	/**
	 * ケース/ピース区分(名称)を取得する。
	 * @return Returns the wCasePieceName.
	 */
	public String getCasePieceName()
	{
		return wCasePieceName;
	}
	/**
	 * ケース/ピース区分(名称)をセットする。
	 * @param casePieceName The wCasePieceName to set.
	 */
	public void setCasePieceName(String casePieceName)
	{
		wCasePieceName = casePieceName;
	}

    /**
     * ケース仕分場所を取得する。
     * @return Returns the wCaseSortingLocation.
     */
    public String getCaseSortingLocation()
    {
        return wCaseSortingLocation;
    }
    /**
     * ケース仕分場所をセットする。
     * @param caseSortingLocation The wCaseSortingLocation to set.
     */
    public void setCaseSortingLocation(String caseSortingLocation)
    {
        wCaseSortingLocation = caseSortingLocation;
    }
    /**
     * 荷主コードを取得する。
     * @return Returns the wConsignorCode.
     */
    public String getConsignorCode()
    {
        return wConsignorCode;
    }
    /**
     * 荷主コードをセットする。
     * @param consignorCode The wConsignorCode to set.
     */
    public void setConsignorCode(String consignorCode)
    {
        wConsignorCode = consignorCode;
    }
    /**
     * 荷主名を取得する。
     * @return Returns the wConsignorName.
     */
    public String getConsignorName()
    {
        return wConsignorName;
    }
    /**
     * 荷主名をセットする。
     * @param consignorName The wConsignorName to set.
     */
    public void setConsignorName(String consignorName)
    {
        wConsignorName = consignorName;
    }
    /**
     * 出荷先コードを取得する。
     * @return Returns the wCustomerCode.
     */
    public String getCustomerCode()
    {
        return wCustomerCode;
    }
    /**
     * 出荷先コードをセットする。
     * @param customerCode The wCustomerCode to set.
     */
    public void setCustomerCode(String customerCode)
    {
        wCustomerCode = customerCode;
    }
    /**
     * 出荷先名を取得する。
     * @return Returns the wCustomerName.
     */
    public String getCustomerName()
    {
        return wCustomerName;
    }
    /**
     * 出荷先名をセットする。
     * @param customerName The wCustomerName to set.
     */
    public void setCustomerName(String customerName)
    {
        wCustomerName = customerName;
    }
    /**
     * 表示順１を取得する。
     * @return Returns the wDspOrder.
     */
    public String getDspOrder()
    {
        return wDspOrder;
    }
    /**
     * 表示順１をセットする。
     * @param dspOrder The wDspOrder to set.
     */
    public void setDspOrder(String dspOrder)
    {
        wDspOrder = dspOrder;
    }
    /**
     * ケース入数を取得する。
     * @return Returns the wEnteringQty.
     */
    public int getEnteringQty()
    {
        return wEnteringQty;
    }
    /**
     * ケース入数をセットする。
     * @param enteringQty The wEnteringQty to set.
     */
    public void setEnteringQty(int enteringQty)
    {
        wEnteringQty = enteringQty;
    }
    /**
     * 開始仕分予定日を取得する。
     * @return Returns the wFromPlanDate.
     */
    public String getFromPlanDate()
    {
        return wFromPlanDate;
    }
    /**
     * 開始仕分予定日をセットする。
     * @param fromPlanDate The wFromPlanDate to set.
     */
    public void setFromPlanDate(String fromPlanDate)
    {
        wFromPlanDate = fromPlanDate;
    }
    /**
     * 開始仕分日を取得する。
     * @return Returns the wFromSortingDate.
     */
    public String getFromSortingDate()
    {
        return wFromSortingDate;
    }
    /**
     * 開始仕分日をセットする。
     * @param fromSortingDate The wFromSortingDate to set.
     */
    public void setFromSortingDate(String fromSortingDate)
    {
        wFromSortingDate = fromSortingDate;
    }
    /**
     * 開始仕分場所を取得する。
     * @return Returns the wFromSortingLocation.
     */
    public String getFromSortingLocation()
    {
        return wFromSortingLocation;
    }
    /**
     * 開始仕分場所をセットする。
     * @param fromSortingLocation The wFromSortingLocation to set.
     */
    public void setFromSortingLocation(String fromSortingLocation)
    {
        wFromSortingLocation = fromSortingLocation;
    }
    /**
     * 開始伝票No.を取得する。
     * @return Returns the wFromTicketNo.
     */
    public String getFromTicketNo()
    {
        return wFromTicketNo;
    }
    /**
     * 開始伝票No.をセットする。
     * @param fromTicketNo The wFromTicketNo to set.
     */
    public void setFromTicketNo(String fromTicketNo)
    {
        wFromTicketNo = fromTicketNo;
    }
    /**
     * 入荷行No.を取得する。
     * @return Returns the wInstockLineNo.
     */
    public int getInstockLineNo()
    {
        return wInstockLineNo;
    }
    /**
     * 入荷行No.をセットする。
     * @param instockLineNo The wInstockLineNo to set.
     */
    public void setInstockLineNo(int instockLineNo)
    {
        wInstockLineNo = instockLineNo;
    }
    /**
     * 入荷伝票No.を取得する。
     * @return Returns the wInstockTicketNo.
     */
    public String getInstockTicketNo()
    {
        return wInstockTicketNo;
    }
    /**
     * 入荷伝票No.をセットする。
     * @param instockTicketNo The wInstockTicketNo to set.
     */
    public void setInstockTicketNo(String instockTicketNo)
    {
        wInstockTicketNo = instockTicketNo;
    }
    /**
     * 荷主コードを取得する。
     * @return Returns the wItemCode.
     */
    public String getItemCode()
    {
        return wItemCode;
    }
    /**
     * 荷主コードをセットする。
     * @param itemCode The wItemCode to set.
     */
    public void setItemCode(String itemCode)
    {
        wItemCode = itemCode;
    }
    /**
     * 荷主コードを取得する。（リスト）
     * @return Returns the wItemCodeL.
     */
    public String getItemCodeL()
    {
        return wItemCodeL;
    }
    /**
     * 荷主コードをセットする。（リスト）
     * @param itemCodeL The wItemCodeL to set.
     */
    public void setItemCodeL(String itemCodeL)
    {
        wItemCodeL = itemCodeL;
    }
    /**
     * 荷主名を取得する。
     * @return Returns the wItemName.
     */
    public String getItemName()
    {
        return wItemName;
    }
    /**
     * 荷主名をセットする。
     * @param itemName The wItemName to set.
     */
    public void setItemName(String itemName)
    {
        wItemName = itemName;
    }
    /**
     * ケースITFを取得する。
     * @return Returns the wITF.
     */
    public String getITF()
    {
        return wITF;
    }
    /**
     * ケースITFをセットする。
     * @param witf The wITF to set.
     */
    public void setITF(String witf)
    {
        wITF = witf;
    }
    /**
     * 作業No.を取得する。
     * @return Returns the wJobNo.
     */
    public String getJobNo()
    {
        return wJobNo;
    }
    /**
     * 作業No.をセットする。
     * @param jobNo The wJobNo to set.
     */
    public void setJobNo(String jobNo)
    {
        wJobNo = jobNo;
    }
	/**
	 * 作業No.(Vector)をセットします。
	 * @param arg[] セットする作業No.(Vector)
	 */
	public void setJobNoList(Vector arg)
	{
		wJobNoList = arg;
	}

	/**
	 * 作業No.(Vector)を取得します。
	 * @return 作業No.(Vector)
	 */
	public Vector getJobNoList()
	{
		return wJobNoList;
	}

    /**
     * 最終更新日時を取得する。
     * @return Returns the wLastUpdateDate.
     */
    public java.util.Date getLastUpdateDate()
    {
        return wLastUpdateDate;
    }
    /**
     * 最終更新日時をセットする。
     * @param lastUpdateDate The wLastUpdateDate to set.
     */
    public void setLastUpdateDate(java.util.Date lastUpdateDate)
    {
        wLastUpdateDate = lastUpdateDate;
    }

	/**
	 * 最終更新日時(Vector)をセットします。
	 * @param arg セットする最終更新日時(Vector)
	 */
	public void setLastUpdateDateList(Vector arg)
	{
		wLastUpdateDateList = arg;
	}

	/**
	 * 最終更新日時(Vector)を取得します。
	 * @return 最終更新日時(Vector)
	 */
	public Vector getLastUpdateDateList()
	{
		return wLastUpdateDateList;
	}

    /**
     * 最終更新処理名を取得する。
     * @return Returns the wLastUpdatePName.
     */
    public String getLastUpdatePName()
    {
        return wLastUpdatePName;
    }
    /**
     * 最終更新処理名をセットする。
     * @param lastUpdatePName The wLastUpdatePName to set.
     */
    public void setLastUpdatePName(String lastUpdatePName)
    {
        wLastUpdatePName = lastUpdatePName;
    }
    /**
     * パースワードを取得する。
     * @return Returns the wPassword.
     */
    public String getPassword()
    {
        return wPassword;
    }
    /**
     * パースワードをセットする。
     * @param password The wPassword to set.
     */
    public void setPassword(String password)
    {
        wPassword = password;
    }
    /**
     * ピース仕分場所を取得する。
     * @return Returns the wPieceSortingLocation.
     */
    public String getPieceSortingLocation()
    {
        return wPieceSortingLocation;
    }
    /**
     * ピース仕分場所をセットする。
     * @param pieceSortingLocation The wPieceSortingLocation to set.
     */
    public void setPieceSortingLocation(String pieceSortingLocation)
    {
        wPieceSortingLocation = pieceSortingLocation;
    }
    /**
     * 予定ケース数を取得する。
     * @return Returns the wPlanCaseQty.
     */
    public int getPlanCaseQty()
    {
        return wPlanCaseQty;
    }
    /**
     * 予定ケース数をセットする。
     * @param planCaseQty The wPlanCaseQty to set.
     */
    public void setPlanCaseQty(int planCaseQty)
    {
        wPlanCaseQty = planCaseQty;
    }
    /**
     * 仕分予定日を取得する。
     * @return Returns the wPlanDate.
     */
    public String getPlanDate()
    {
        return wPlanDate;
    }
    /**
     * 仕分予定日をセットする。
     * @param planDate The wPlanDate to set.
     */
    public void setPlanDate(String planDate)
    {
        wPlanDate = planDate;
    }
    /**
     * 予定ピース数を取得する。
     * @return Returns the wPlanPieceQty.
     */
    public int getPlanPieceQty()
    {
        return wPlanPieceQty;
    }
    /**
     * 予定ピース数をセットする。
     * @param planPieceQty The wPlanPieceQty to set.
     */
    public void setPlanPieceQty(int planPieceQty)
    {
        wPlanPieceQty = planPieceQty;
    }
    /**
     * 登録日時を取得する。
     * @return Returns the wRegistDate.
     */
    public java.util.Date getRegistDate()
    {
        return wRegistDate;
    }
    /**
     * 登録日時をセットする。
     * @param registDate The wRegistDate to set.
     */
    public void setRegistDate(java.util.Date registDate)
    {
        wRegistDate = registDate;
    }
    /**
     * 登録区分を取得する。
     * @return Returns the wRegistKbn.
     */
    public String getRegistKbn()
    {
        return wRegistKbn;
    }
    /**
     * 登録区分をセットする。
     * @param registKbn The wRegistKbn to set.
     */
    public void setRegistKbn(String registKbn)
    {
        wRegistKbn = registKbn;
    }
    /**
     * 登録処理名を取得する。
     * @return Returns the wRegistPName.
     */
    public String getRegistPName()
    {
        return wRegistPName;
    }
    /**
     * 登録処理名をセットする。
     * @param registPName The wRegistPName to set.
     */
    public void setRegistPName(String registPName)
    {
        wRegistPName = registPName;
    }
    /**
     * 実績ケース数を取得する。
     * @return Returns the wResultCaseQty.
     */
    public int getResultCaseQty()
    {
        return wResultCaseQty;
    }
    /**
     * 実績ケース数をセットする。
     * @param resultCaseQty The wResultCaseQty to set.
     */
    public void setResultCaseQty(int resultCaseQty)
    {
        wResultCaseQty = resultCaseQty;
    }
    /**
     * 実績ピース数を取得する。
     * @return Returns the wResultPieceQty.
     */
    public int getResultPieceQty()
    {
        return wResultPieceQty;
    }
    /**
     * 実績ピース数をセットする。
     * @param resultPieceQty The wResultPieceQty to set.
     */
    public void setResultPieceQty(int resultPieceQty)
    {
        wResultPieceQty = resultPieceQty;
    }
    /**
     * ためうち行No.を取得する。
     * @return Returns the wRowNo.
     */
    public int getRowNo()
    {
        return wRowNo;
    }
    /**
     * ためうち行No.をセットする。
     * @param rowNo The wRowNo to set.
     */
    public void setRowNo(int rowNo)
    {
        wRowNo = rowNo;
    }
    /**
     * 検索フラグ(予定／実績)を取得する。
     * @return Returns the wSearchFlag.
     */
    public String getSearchFlag()
    {
        return wSearchFlag;
    }
    /**
     * 検索フラグ(予定／実績)をセットする。
     * @param searchFlag The wSearchFlag to set.
     */
    public void setSearchFlag(String searchFlag)
    {
        wSearchFlag = searchFlag;
    }
    /**
     * 作業状態配列を取得する。
     * @return Returns the wSearchStatus.
     */
    public String[] getSearchStatus()
    {
        return wSearchStatus;
    }
    /**
     * 作業状態配列をセットする。
     * @param searchStatus The wSearchStatus to set.
     */
    public void setSearchStatus(String[] searchStatus)
    {
        wSearchStatus = searchStatus;
    }
    /**
     * 
     * @return Returns the wSelectBoxCheck.
     */
    public boolean getSelectBoxCheck()
    {
        return wSelectBoxCheck;
    }
    /**
     * @param selectBoxCheck The wSelectBoxCheck to set.
     */
    public void setSelectBoxCheck(boolean selectBoxCheck)
    {
        wSelectBoxCheck = selectBoxCheck;
    }
    /**
     * 選択Box有効/無効フラグを取得する。
     * @return Returns the wSelectBoxFlag.
     */
    public boolean getSelectBoxFlag()
    {
        return wSelectBoxFlag;
    }
    /**
     * 選択Box有効/無効フラグをセットする。
     * @param selectBoxFlag The wSelectBoxFlag to set.
     */
    public void setSelectBoxFlag(boolean selectBoxFlag)
    {
        wSelectBoxFlag = selectBoxFlag;
    }
    /**
     * 出荷行No.を取得する。
     * @return Returns the wShippingLineNo.
     */
    public int getShippingLineNo()
    {
        return wShippingLineNo;
    }
    /**
     * 出荷行No.をセットする。
     * @param shippingLineNo The wShippingLineNo to set.
     */
    public void setShippingLineNo(int shippingLineNo)
    {
        wShippingLineNo = shippingLineNo;
    }
    /**
     * 仕分予定一意キーを取得する。
     * @return Returns the wSortingPlanUkey.
     */
    public String getSortingPlanUkey()
    {
        return wSortingPlanUkey;
    }
    /**
     * 仕分予定一意キーをセットする。
     * @param sortingPlanUkey The wSortingPlanUkey to set.
     */
    public void setSortingPlanUkey(String sortingPlanUkey)
    {
        wSortingPlanUkey = sortingPlanUkey;
    }
    /**
     * 出荷伝票No.を取得する。
     * @return Returns the wShippingTicketNo.
     */
    public String getShippingTicketNo()
    {
        return wShippingTicketNo;
    }
    /**
     * 出荷伝票No.をセットする。
     * @param shippingTicketNo The wShippingTicketNo to set.
     */
    public void setShippingTicketNo(String shippingTicketNo)
    {
        wShippingTicketNo = shippingTicketNo;
    }
    /**
     * 欠品区分を取得する。
     * @return Returns the wShortage.
     */
    public boolean getShortage()
    {
        return wShortage;
    }
    /**
     * 欠品区分をセットする。
     * @param shortage The wShortage to set.
     */
    public void setShortage(boolean shortage)
    {
        wShortage = shortage;
    }
    /**
     * 欠品ケース数を取得する。
     * @return Returns the wShortageCaseQty.
     */
    public int getShortageCaseQty()
    {
        return wShortageCaseQty;
    }
    /**
     * 欠品ケース数をセットする。
     * @param shortageCaseQty The wShortageCaseQty to set.
     */
    public void setShortageCaseQty(int shortageCaseQty)
    {
        wShortageCaseQty = shortageCaseQty;
    }
    /**
     * 欠品ピース数を取得する。
     * @return Returns the wShortagePieceQty.
     */
    public int getShortagePieceQty()
    {
        return wShortagePieceQty;
    }
    /**
     * 欠品ピース数をセットする。
     * @param shortagePieceQty The wShortagePieceQty to set.
     */
    public void setShortagePieceQty(int shortagePieceQty)
    {
        wShortagePieceQty = shortagePieceQty;
    }
    /**
     * 仕分日を取得する。
     * @return Returns the wSortingDate.
     */
    public String getSortingDate()
    {
        return wSortingDate;
    }
    /**
     * 仕分日をセットする。
     * @param sortingDate The wSortingDate to set.
     */
    public void setSortingDate(String sortingDate)
    {
        wSortingDate = sortingDate;
    }
    /**
     * 仕分予定削除一覧リスト発行区分を取得する。
     * @return Returns the wSortingDeleteListFlg.
     */
    public boolean getSortingDeleteListFlg()
    {
        return wSortingDeleteListFlg;
    }
    /**
     * 仕分予定削除一覧リスト発行区分をセットする。
     * @param sortingDeleteListFlg The wSortingDeleteListFlg to set.
     */
    public void setSortingDeleteListFlg(boolean sortingDeleteListFlg)
    {
        wSortingDeleteListFlg = sortingDeleteListFlg;
    }
    /**
     * 仕分作業リスト発行区分を取得する。
     * @return 仕分作業リスト発行区分
     */
    public boolean getSortingWorkListFlg()
    {
        return wSortingWorkListFlg;
    }
    /**
     * 仕分作業リスト発行区分をセットする。
     * @param sortingDeleteListFlg セットする仕分作業リスト発行区分
     */
    public void setSortingWorkListFlg(boolean sortingWorkListFlg)
    {
        wSortingWorkListFlg = sortingWorkListFlg;
    }
    /**
     * 仕分場所を取得する。
     * @return Returns the wSortingLocation.
     */
    public String getSortingLocation()
    {
        return wSortingLocation;
    }
    /**
     * 仕分場所をセットする。
     * @param sortingLocation The wSortingLocation to set.
     */
    public void setSortingLocation(String sortingLocation)
    {
        wSortingLocation = sortingLocation;
    }
    /**
     * 作業状態を取得する。
     * @return Returns the wStatusFlag.
     */
    public String getStatusFlag()
    {
        return wStatusFlag;
    }
    /**
     * 作業状態をセットする。
     * @param statusFlag The wStatusFlag to set.
     */
    public void setStatusFlag(String statusFlag)
    {
        wStatusFlag = statusFlag;
    }
    /**
     * 作業状態を取得する。（リスト）
     * @return Returns the wStatusFlagL.
     */
    public String getStatusFlagL()
    {
        return wStatusFlagL;
    }
    /**
     * 作業状態をセットする。（リスト）
     * @param statusFlagL The wStatusFlagL to set.
     */
    public void setStatusFlagL(String statusFlagL)
    {
        wStatusFlagL = statusFlagL;
    }
    /**
     * 作業状態名を取得する。
     * @return Returns the wStatusName.
     */
    public String getStatusName()
    {
        return wStatusName;
    }
    /**
     * 作業状態名をセットする。
     * @param statusName The wStatusName to set.
     */
    public void setStatusName(String statusName)
    {
        wStatusName = statusName;
    }
    /**
     * 仕入先コードを取得する。
     * @return Returns the wSupplierCode.
     */
    public String getSupplierCode()
    {
        return wSupplierCode;
    }
    /**
     * 仕入先コードをセットする。
     * @param supplierCode The wSupplierCode to set.
     */
    public void setSupplierCode(String supplierCode)
    {
        wSupplierCode = supplierCode;
    }
    /**
     * 仕入先名を取得する。
     * @return Returns the wSupplierName.
     */
    public String getSupplierName()
    {
        return wSupplierName;
    }
    /**
     * 仕入先名をセットする。
     * @param supplierName The wSupplierName to set.
     */
    public void setSupplierName(String supplierName)
    {
        wSupplierName = supplierName;
    }
    /**
     * TC/DC区分を取得する。
     * @return Returns the wTcdcFlag.
     */
    public String getTcdcFlag()
    {
        return wTcdcFlag;
    }
    /**
     * TC/DC区分をセットする。
     * @param tcdcFlag The wTcdcFlag to set.
     */
    public void setTcdcFlag(String tcdcFlag)
    {
        wTcdcFlag = tcdcFlag;
    }
    
	/**
	 * TC/DC区分(名称)を取得する。
	 * @return Returns the wTcdcName.
	 */
	public String getTcdcName()
	{
		return wTcdcName;
	}
	/**
	 * TC/DC区分(名称)をセットする。
	 * @param tcdcName The wTcdcName to set.
	 */
	public void setTcdcName(String tcdcName)
	{
		wTcdcName = tcdcName;
	}

    /**
     * 終了仕分予定日を取得する。
     * @return Returns the wToPlanDate.
     */
    public String getToPlanDate()
    {
        return wToPlanDate;
    }
    /**
     * 終了仕分予定日をセットする。
     * @param toPlanDate The wToPlanDate to set.
     */
    public void setToPlanDate(String toPlanDate)
    {
        wToPlanDate = toPlanDate;
    }
    /**
     * 終了仕分日を取得する。
     * @return Returns the wToSortingDate.
     */
    public String getToSortingDate()
    {
        return wToSortingDate;
    }
    /**
     * 終了仕分日をセットする。
     * @param toSortingDate The wToSortingDate to set.
     */
    public void setToSortingDate(String toSortingDate)
    {
        wToSortingDate = toSortingDate;
    }
    /**
     * 終了仕分場所を取得する。
     * @return Returns the wToSortingLocation.
     */
    public String getToSortingLocation()
    {
        return wToSortingLocation;
    }
    /**
     * 終了仕分場所をセットする。
     * @param toSortingLocation The wToSortingLocation to set.
     */
    public void setToSortingLocation(String toSortingLocation)
    {
        wToSortingLocation = toSortingLocation;
    }
    /**
     * 予定総数を取得する。
     * @return Returns the wTotalPlanQty.
     */
    public int getTotalPlanQty()
    {
        return wTotalPlanQty;
    }
    /**
     * 予定総数をセットする。
     * @param totalPlanQty The wTotalPlanQty to set.
     */
    public void setTotalPlanQty(int totalPlanQty)
    {
        wTotalPlanQty = totalPlanQty;
    }
    /**
     * 実績総数を取得する。
     * @return Returns the wTotalResultQty.
     */
    public int getTotalResultQty()
    {
        return wTotalResultQty;
    }
    /**
     * 実績総数をセットする。
     * @param totalResultQty The wTotalResultQty to set.
     */
    public void setTotalResultQty(int totalResultQty)
    {
        wTotalResultQty = totalResultQty;
    }
    /**
     * 仕分先数を取得する。
     * @return 仕分先数
     */
    public int getSortingQty()
    {
        return wSortingQty;
    }
    /**
     * 仕分先数をセットする。
     * @param totalResultQty セットする仕分先数
     */
    public void setSortingQty(int sortingQty)
    {
        wSortingQty = sortingQty;
    }
    /**
     * 終了伝票No.を取得する。
     * @return Returns the wToTicketNo.
     */
    public String getToTicketNo()
    {
        return wToTicketNo;
    }
    /**
     * 終了伝票No.をセットする。
     * @param toTicketNo The wToTicketNo to set.
     */
    public void setToTicketNo(String toTicketNo)
    {
        wToTicketNo = toTicketNo;
    }
    /**
     * 賞味期限を取得する。
     * @return Returns the wUseByDate.
     */
    public String getUseByDate()
    {
        return wUseByDate;
    }
    /**
     * 賞味期限をセットする。
     * @param useByDate The wUseByDate to set.
     */
    public void setUseByDate(String useByDate)
    {
        wUseByDate = useByDate;
    }
    /**
     * 作業者コードを取得する。
     * @return Returns the wWorkerCode.
     */
    public String getWorkerCode()
    {
        return wWorkerCode;
    }
    /**
     * 作業者コードをセットする。
     * @param workerCode The wWorkerCode to set.
     */
    public void setWorkerCode(String workerCode)
    {
        wWorkerCode = workerCode;
    }
    /**
     * 作業者名を取得する。
     * @return Returns the wWorkerName.
     */
    public String getWorkerName()
    {
        return wWorkerName;
    }
    /**
     * 作業者名をセットする。
     * @param workerName The wWorkerName to set.
     */
    public void setWorkerName(String workerName)
    {
        wWorkerName = workerName;
    }
	
	/**
	 * ボタン種別をセットします。
	 * @param arg セットするボタン種別
	 * @throws InvalidStatusException 定義されていないボタン種別がセットされた場合に通知します。
	 */
	public void setButtonType(String arg) throws InvalidStatusException
	{
		if ((arg.equals(BUTTON_MODIFYSUBMIT)) || (arg.equals(BUTTON_ALLWORKINGCLEAR)))
		{
			wButtonType = arg;
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wBeginningFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6006009" + MessageResource.DELIM + tObj[0] + MessageResource.DELIM + tObj[1] + MessageResource.DELIM + tObj[2]));
		}
	}

	/**
	 * ボタン種別を取得します。
	 * @return ボタン種別
	 */
	public String getButtonType()
	{
		return wButtonType;
	}
	
	/**
	 * 報告フラグをセットします。
	 * @param arg セットする報告フラグ
	 */
	public void setReportFlag(String arg)
	{
		wReportFlag = arg;
	}

	/**
	 * 報告フラグを取得します。
	 * @return 報告フラグ
	 */
	public String getReportFlag()
	{
		return wReportFlag;
	}

	/**
	 * 報告フラグ名称をセットします。
	 * @param arg セットする報告フラグ名称
	 */
	public void setReportFlagName(String arg)
	{
		wReportFlagName = arg;
	}

	/**
	 * 報告フラグ名称を取得します。
	 * @return 報告フラグ名称
	 */
	public String getReportFlagName()
	{
		return wReportFlagName;
	}
    /**
     * 桁数チェック有無フラグを取得する。
     * @return Returns the wLineCheckFlag.
     */
    public boolean getLineCheckFlag()
    {
        return wLineCheckFlag;
    }
    
    /**
     * 桁数チェック有無フラグをセットする。
     * @param lineCheckFlag The wLineCheckFlag to set.
     */
    public void setLineCheckFlag(boolean lineCheckFlag)
    {
        wLineCheckFlag = lineCheckFlag;
    }
}
