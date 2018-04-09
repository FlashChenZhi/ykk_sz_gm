package jp.co.daifuku.wms.instockreceive.schedule;
/*
 * Created on 2004/10/27
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.common.Parameter;

/**
 * 
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
 * 	   入荷伝票No.<BR>
 *     入荷行No.<BR>
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
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     欠品ケース数 <BR>
 *     欠品ピース数 <BR> 
 *     ＴＣ/ＤＣ区分<BR>
 *     ＴＣ/ＤＣ区分名称<BR>
 *     ＴＣ/ＤＣ区分(リストセル)<BR>
 *     入荷作業リスト発行区分<BR>
 *     入荷予定削除一覧リスト発行区分<BR>
 *     予定総数<BR>
 *     実績総数<BR>
 *     入荷数<BR>
 *     ためうち行No.<BR>
 *     ケース/ピース区分<BR>
 *     バッチNo.(スケジュールNo.)<BR>
 *     登録区分<BR>
 *     登録日時<BR>
 *     登録処理名<BR>
 *     最終更新日時<BR>
 *     最終更新処理名<BR>
 *     作業No.<BR>
 *     検索フラグ<BR>
 *     選択Box有効/無効フラグ<BR>
 *     選択BoxON/OFF<BR>
 *     作業状態配列<BR>
 *     開始入荷予定日<BR>
 *	   終了入荷予定日<BR>
 *	   入荷予定日<BR>
 *     開始入荷日<BR>
 *     終了入荷日<BR>
 *     入荷日<BR>
 *     発注日<BR>
 *     表示順 <BR>
 *     表示順(日付別) <BR>
 *     表示順(伝票OR商品) <BR>
 *     ボタン種別<BR>
 *     分納区分<BR>
 *     残ケース数<BR>
 *     残ピース数<BR>
 *     入荷残数初期入力を行う<BR>
 *     実績報告区分 <BR>
 *     実績報告区分名称 <BR>
 *
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * 
 * <CODE>InstockReceiveParameter.java</CODE><BR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/27</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * @author $Author: mori $
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 */
public class InstockReceiveParameter extends Parameter
{
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
	 * 欠品区分 有り
	 */
	public static final String SHORTAGE_FLAG_CHECKED = "1" ;
	
	/**
	 * 欠品区分 無し
	 */
	public static final String SHORTAGE_FLAG_UNCHECKED = "0" ;
	
	/**
	 * 分納区分 有り
	 */
	public static final String REMNANT_FLAG_CHECKED = "1" ;
	
	/**
	 * 分納区分 無し
	 */
	public static final String REMNANT_FLAG_UNCHECKED = "0" ;
	
	/**
	 * 表示順：伝票No.順
	 */
	public static final String DISPLAY_ORDER_TICKET = "0" ;
	
	/**
	 * 表示順：商品コード順
	 */
	public static final String DISPLAY_ORDER_ITEM = "1" ;
	
	/**
	 * 表示順：入荷受付日順
	 */
	public static final String DISPLAY_ORDER_INSTOCK_DAY = "2" ;
	
	/**
	 * 表示順：入荷予定日順
	 */
	public static final String DISPLAY_ORDER_INSTOCK_PLAN_DAY = "3" ;
	
	/**
	 * 入荷残数初期入力を行う 有り
	 */
	public static final String REMNANT_DISPLAY_CHECKED = "1" ;
	
	/**
	 * 入荷残数初期入力を行う 無し
	 */
    public static final String REMNANT_DISPLAY_UNCHECKED = "0" ;
    
	/**
	 * TC/DC区分（DC）
	 */
	public static final String TCDC_FLAG_DC = "0" ;
	
	/**
	 * TC/DC区分（クロスTC）
	 */
	public static final String  TCDC_FLAG_CROSSTC = "1" ;
	
	/**
	 * TC/DC区分（TC）
	 */
	public static final String TCDC_FLAG_TC = "2" ;

	/**
	 * TC/DC区分（クロス/DC）
	 */
	public static final String TCDC_FLAG_CROSS_AND_DC = "88" ;

	/**
	 * TC/DC区分（全て）
	 */
	public static final String TCDC_FLAG_ALL = "99" ;
	
	/**
	 * 実績報告フラグ（未報告）
	 */
	public static final String REPORT_FLAG_NOT_SENT = "0";

	/**
	 * 実績報告フラグ（報告済）
	 */
	public static final String REPORT_FLAG_SENT = "1";
	
	/**
	 * ボタン種別(修正登録)
	 */
	public static final String BUTTON_MODIFYSUBMIT = "0";

	/**
	 * ボタン種別(一括作業中解除)
	 */
	public static final String BUTTON_ALLWORKINGCLEAR = "1";

	/**
	 * 登録区分（ホスト）
	 */
	public static final String REGIST_KIND_HOST = "0" ;

	/**
	 * 登録区分（端末）
	 */
	public static final String REGIST_KIND_WMS = "1" ;
	
	/**
	 * 検索フラグ(予定)
	 */
	public static final String SEARCHFLAG_PLAN = "1";

	/**
	 * 検索フラグ(実績)
	 */
	public static final String SEARCHFLAG_RESULT = "2";
	
	/**
	 * 範囲フラグ（開始）
	 */
	public static final String RANGE_START = "0";

	/**
	 * 範囲フラグ（終了）
	 */
	public static final String RANGE_END = "1";
	
	/**
	 * 選択BoxON/OFF（ON）
	 */
	public static final boolean SELECT_BOX_ON = true;

	/**
	 * 選択BoxON/OFF（OFF）
	 */
	public static final boolean SELECT_BOX_OFF = false;
	
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
	 * 表示順
	 */
	private String wDspOrder;

	/**
	 *  表示順(日付別)
	 */
	private String wDspOrderDate;

	/**
	 * 表示順(伝票OR商品)
	 */
	private String wDspOrderItem;
	
	/**
	 * 入荷伝票No.
	 */
	private String wInstockTicketNo;

	/**
	 * 入荷行No.
	 */
	private int wInstockLineNo;

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
	private boolean wShortageFlag = false;

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
	 * ＴＣ/ＤＣ区分名称
	 */
	private String wTcdcName;
	
	/**
	 * ＴＣ/ＤＣ区分(リストセル)
	 */
	private String wTcdcFlagL;

	/**
	 * 入荷予定削除一覧リスト発行区分
	 */
	private boolean wInstockReceiveDeleteListFlg = false;

	/**
	 * 予定総数
	 */
	private int wTotalPlanQty;

	/**
	 * 実績総数
	 */
	private int wTotalResultQty;

	/**
	 * 入荷数
	 */
	private int wInstockReceiveQty;

	/**
	 * ためうち行No.
	 */
	private int wRowNo;

	/**
	 * 入荷予定一意キー
	 */
	private String wInstockPlanUkey;

	/**
	 * ケース/ピース区分
	 */
	private String wCasePieceFlag;

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
	 * 最終更新処理名
	 */
	private String wLastUpdatePName;

	

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
	 * 開始入荷予定日
	 */
	private String wFromPlanDate;

	/**
	 * 終了入荷予定日
	 */
	private String wToPlanDate;

	/**
	 * 入荷予定日
	 */
	private String wPlanDate;
	
	/**
	 * 開始入荷日
	 */
	private String wFromInstockReceiveDate;

	/**
	 * 終了入荷日
	 */
	private String wToInstockReceiveDate;

	/**
	 * 入荷日
	 */
	private String wInstockReceiveDate;
	
	/**
	 * 発注日
	 */
	private String wOrderingDate;
	
	/**
	 * ボタン種別
	 */
	private String wButtonType;
	
	/**
	 * 分納区分
	 */
	private boolean wRemnantFlag = false ;
	
	/**
	 * 残ケース数 
	 */
	private String wRemnantCase ;
	
	/**
	 * 残ピース数
	 */
	private String wRemnantPiece ;
	
	/**
	 * 入荷残数初期入力を行う区分
	 */
	private boolean wRemnantDisplayFlag = false ;
	
	/**
	 * 作業No.
	 */
	private String wJobNo;
	
	/**
	 * 実績報告区分
	 */
	private String wReportFlag;

	/**
	 * 実績報告区分名称
	 */
	private String wReportFlagName;

	/**
	 * 桁数チェック有無フラグ
	 */
	private boolean wLineCheckFlag = true;
	
    //Static--------------------------------------
    
    //Constructors--------------------------------
    
    //Public--------------------------------------
    
    //Protected-----------------------------------
    
    //Private-------------------------------------
    
    
    //InnerClasses--------------------------------
    
    /**
     * バッチNo.(スケジュールNo.)を取得する。
     * @return Returns the wBatchNo.
     */
    public String getBatchNo()
    {
        return wBatchNo;
    }
    
    /**
     *バッチNo.(スケジュールNo.)をセットする。
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
     * ボールＩＴＦを取得する。
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
     * ボタン種別を取得する。
     * @return Returns the wButtonType.
     */
    public String getButtonType()
    {
        return wButtonType;
    }
    
    /**
     * ボタン種別をセットする。
     * @param buttonType The wButtonType to set.
     */
    public void setButtonType(String buttonType)
    {
        wButtonType = buttonType;
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
     * 荷主名称を取得する。
     * @return Returns the wConsignorName.
     */
    public String getConsignorName()
    {
        return wConsignorName;
    }
    
    /**
     * 荷主名称をセットする。
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
     * 出荷先名称を取得する。
     * @return Returns the wCustomerName.
     */
    public String getCustomerName()
    {
        return wCustomerName;
    }
    
    /**
     * 出荷先名称をセットする。
     * @param customerName The wCustomerName to set.
     */
    public void setCustomerName(String customerName)
    {
        wCustomerName = customerName;
    }
    
    /**
     * 表示順を取得する。
     * @return Returns the wDspOrder.
     */
    public String getDspOrder()
    {
        return wDspOrder;
    }
    
    /**
     * 表示順をセットする。
     * @param dspOrder The wDspOrder to set.
     */
    public void setDspOrder(String dspOrder)
    {
        wDspOrder = dspOrder;
    }
    
	/**
	 * 表示順(日付)を取得する。
	 * @return Returns the wDspOrderDate.
	 */
   	public String getDspOrderDate()
   	{
   		return wDspOrderDate;
   	}
   
   	/**
   	 * 表示順(日付)をセットする。
	 * @param dspOrder The wDspOrderDate to set.
	 */
   	public void setDspOrderDate(String dspOrder)
   	{
   		wDspOrderDate = dspOrder;
   	}
   	
	/**
	 * 表示順(伝票OR商品)を取得する。
	 * @return Returns the wDspOrderItem.
	 */
    public String getDspOrderItem()
    {
	    return wDspOrderItem;
    }
    
    /**
	 * 表示順(伝票OR商品)をセットする。
	 * @param dspOrder The wDspOrderItem to set.
	 */
    public void setDspOrderItem(String dspOrder)
    {
	    wDspOrderItem = dspOrder;
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
     * 開始入荷日を取得する。
     * @return Returns the wFromInstockReceiveDate.
     */
    public String getFromInstockReceiveDate()
    {
        return wFromInstockReceiveDate;
    }
    
    /**
     * 開始入荷日をセットする。
     * @param fromInstockReceiveDate The wFromInstockReceiveDate to set.
     */
    public void setFromInstockReceiveDate(String fromInstockReceiveDate)
    {
        wFromInstockReceiveDate = fromInstockReceiveDate;
    }
    
    /**
     * 開始入荷予定日を取得する。
     * @return Returns the wFromPlanDate.
     */
    public String getFromPlanDate()
    {
        return wFromPlanDate;
    }
    
    /**
     * 開始入荷予定日をセットする。
     * @param fromPlanDate The wFromPlanDate to set.
     */
    public void setFromPlanDate(String fromPlanDate)
    {
        wFromPlanDate = fromPlanDate;
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
     * 入荷予定一意キーを取得する。
     * @return Returns the wInstockPlanUkey.
     */
    public String getInstockPlanUkey()
    {
        return wInstockPlanUkey;
    }
    
    /**
     * 入荷予定一意キーをセットする。
     * @param instockPlanUkey The wInstockPlanUkey to set.
     */
    public void setInstockPlanUkey(String instockPlanUkey)
    {
        wInstockPlanUkey = instockPlanUkey;
    }
    
    /**
     * 入荷日を取得する。
     * @return Returns the wInstockReceiveDate.
     */
    public String getInstockReceiveDate()
    {
        return wInstockReceiveDate;
    }
    
    /**
     * 入荷日をセットする。
     * @param instockReceiveDate The wInstockReceiveDate to set.
     */
    public void setInstockReceiveDate(String instockReceiveDate)
    {
        wInstockReceiveDate = instockReceiveDate;
    }
    
	/**
	 * 発注日を取得する。
	 * @return Returns the wOrderingDate.
	 */
	public String getOrderingDate()
	{
		return wOrderingDate;
	}
	
	/**
	 * 発注日をセットする。
	 * @param instockReceiveDate The wOrderingDate to set.
	 */
	public void setOrderingDate(String orderingDate)
	{
		wOrderingDate = orderingDate;
	}
	
    /**
     * 入荷予定削除一覧リスト発行区分を取得する。
     * @return Returns the wInstockReceiveDeleteListFlg.
     */
    public boolean getInstockReceiveDeleteListFlg()
    {
        return wInstockReceiveDeleteListFlg;
    }
    
    /**
     * 入荷予定削除一覧リスト発行区分をセットする。
     * @param instockReceiveDeleteListFlg The wInstockReceiveDeleteListFlg to set.
     */
    public void setInstockReceiveDeleteListFlg(boolean instockReceiveDeleteListFlg)
    {
        wInstockReceiveDeleteListFlg = instockReceiveDeleteListFlg;
    }
    
    /**
     * 入荷数を取得する。
     * @return Returns the wInstockReceiveQty.
     */
    public int getInstockReceiveQty()
    {
        return wInstockReceiveQty;
    }
    
    /**
     * 入荷数をセットする。
     * @param instockReceiveQty The wInstockReceiveQty to set.
     */
    public void setInstockReceiveQty(int instockReceiveQty)
    {
        wInstockReceiveQty = instockReceiveQty;
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
     * 商品コードを取得する。
     * @return Returns the wItemCode.
     */
    public String getItemCode()
    {
        return wItemCode;
    }
    
    /**
     * 商品コードをセットする。
     * @param itemCode The wItemCode to set.
     */
    public void setItemCode(String itemCode)
    {
        wItemCode = itemCode;
    }
    
    /**
     * 商品コードリストを取得する。
     * @return Returns the wItemCodeL.
     */
    public String getItemCodeL()
    {
        return wItemCodeL;
    }
    
    /**
     * 商品コードリストをセットする。
     * @param itemCodeL The wItemCodeL to set.
     */
    public void setItemCodeL(String itemCodeL)
    {
        wItemCodeL = itemCodeL;
    }
    
    /**
     * 商品名称を取得する。
     * @return Returns the wItemName.
     */
    public String getItemName()
    {
        return wItemName;
    }
    
    /**
     * 商品名称をセットする。
     * @param itemName The wItemName to set.
     */
    public void setItemName(String itemName)
    {
        wItemName = itemName;
    }
    
    /**
     * ケースＩＴＦを取得する。
     * @return Returns the wITF.
     */
    public String getITF()
    {
        return wITF;
    }
    
    /**
     * ケースＩＴＦをセットする。
     * @param witf The wITF to set.
     */
    public void setITF(String witf)
    {
        wITF = witf;
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
     * 入荷予定日を取得する。
     * @return Returns the wPlanDate.
     */
    public String getPlanDate()
    {
        return wPlanDate;
    }
    
    /**
     * 入荷予定日をセットする。
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
     * 実績数を取得する。
     * @return Returns the wResultCaseQty.
     */
    public int getResultCaseQty()
    {
        return wResultCaseQty;
    }
    
    /**
     * 実績数をセットする。
     * @param resultCaseQty The wResultCaseQty to set.
     */
    public void setResultCaseQty(int resultCaseQty)
    {
        wResultCaseQty = resultCaseQty;
    }
    
    /**
     * 実績数を取得する。
     * @return Returns the wResultPieceQty.
     */
    public int getResultPieceQty()
    {
        return wResultPieceQty;
    }
    
    /**
     * 実績数をセットする。
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
     * 選択BoxON/OFFを取得する。
     * @return Returns the wSelectBoxCheck.
     */
    public boolean getSelectBoxCheck()
    {
        return wSelectBoxCheck;
    }
    
    /**
     * 選択BoxON/OFFをセットする。
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
     * 欠品区分を取得する。
     * @return Returns the wShortage.
     */
    public boolean getShortageFlag()
    {
        return wShortageFlag;
    }
    
    /**
     * 欠品区分をセットする。
     * @param shortage The wShortage to set.
     */
    public void setShortageFlag(boolean shortageFlag)
    {
        wShortageFlag = shortageFlag;
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
     * 作業状態(リストセル)を取得する。
     * @return Returns the wStatusFlagL.
     */
    public String getStatusFlagL()
    {
        return wStatusFlagL;
    }
    
    /**
     * 作業状態(リストセル)をセットする。
     * @param statusFlagL The wStatusFlagL to set.
     */
    public void setStatusFlagL(String statusFlagL)
    {
        wStatusFlagL = statusFlagL;
    }
    
    /**
     * 作業状態名称を取得する。
     * @return Returns the wStatusName.
     */
    public String getStatusName()
    {
        return wStatusName;
    }
    
    /**
     * 作業状態名称をセットする。
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
     * 仕入先名称を取得する。
     * @return Returns the wSupplierName.
     */
    public String getSupplierName()
    {
        return wSupplierName;
    }
    
    /**
     * 仕入先名称をセットする。
     * @param supplierName The wSupplierName to set.
     */
    public void setSupplierName(String supplierName)
    {
        wSupplierName = supplierName;
    }
    
    /**
     * ＴＣ/ＤＣ区分を取得する。
     * @return Returns the wTcdcFlag.
     */
    public String getTcdcFlag()
    {
        return wTcdcFlag;
    }
    
    /**
     * ＴＣ/ＤＣ区分をセットする。
     * @param tcdcFlag The wTcdcFlag to set.
     */
    public void setTcdcFlag(String tcdcFlag)
    {
        wTcdcFlag = tcdcFlag;
    }
    
	/**
	 * ＴＣ/ＤＣ区分名称を取得する。
	 * @return Returns the wTcdcName.
	 */
	public String getTcdcName()
	{
		return wTcdcName;
	}
	
	/**
	 * ＴＣ/ＤＣ区分名称をセットする。
	 * @param tcdcFlag The wTcdcFlag to set.
	 */
	public void setTcdcName(String tcdcName)
	{
		wTcdcName = tcdcName;
	}
	
	/**
	 * ＴＣ／ＤＣ区分(リストセル)を取得します。
	 * @return ＴＣ／ＤＣ区分(リストセル)
	 */
	public String getTcdcFlagL()
	{
		return wTcdcFlagL;
	}

	/**
	 * ＴＣ／ＤＣ区分(リストセル)をセットします。
	 * @param arg セットするＴＣ／ＤＣ区分(リストセル)
	 */
	public void setTcdcFlagL(String arg)
	{
		wTcdcFlagL = arg;
	}
	
    /**
     * 終了入荷日を取得する。
     * @return Returns the wToInstockReceiveDate.
     */
    public String getToInstockReceiveDate()
    {
        return wToInstockReceiveDate;
    }
    
    /**
     * 終了入荷日をセットする。
     * @param toInstockReceiveDate The wToInstockReceiveDate to set.
     */
    public void setToInstockReceiveDate(String toInstockReceiveDate)
    {
        wToInstockReceiveDate = toInstockReceiveDate;
    }
    
    /**
     * 終了入荷日を取得する。
     * @return Returns the wToPlanDate.
     */
    public String getToPlanDate()
    {
        return wToPlanDate;
    }
    
    /**
     * 終了入荷日をセットする。
     * @param toPlanDate The wToPlanDate to set.
     */
    public void setToPlanDate(String toPlanDate)
    {
        wToPlanDate = toPlanDate;
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
     * 残ケース数を取得する。
     * @return Returns the wRemnantCase.
     */
    public String getRemnantCase()
    {
        return wRemnantCase;
    }
    
    /**
     * 残ケース数をセットする。
     * @param remnantCase The wRemnantCase to set.
     */
    public void setRemnantCase(String remnantCase)
    {
        wRemnantCase = remnantCase;
    }
    
    /**
     * 分納区分を取得する。
     * @return Returns the wRemnantFlag.
     */
    public boolean getRemnantFlag()
    {
        return wRemnantFlag;
    }
    
    /**
     * 分納区分をセットする。
     * @param remnantFlag The wRemnantFlag to set.
     */
    public void setRemnantFlag(boolean remnantFlag)
    {
        wRemnantFlag = remnantFlag;
    }
    
    /**
     * 残ピース数を取得する。
     * @return Returns the wRemnantPiece.
     */
    public String getRemnantPiece()
    {
        return wRemnantPiece;
    }
    
    /**
     * 残ピース数をセットする。
     * @param remnantPiece The wRemnantPiece to set.
     */
    public void setRemnantPiece(String remnantPiece)
    {
        wRemnantPiece = remnantPiece;
    }
    
    /**
     * 入荷残数初期入力を行うを取得する。
     * @return Returns the wRemnantDisplayFlag.
     */
    public boolean getRemnantDisplayFlag()
    {
        return wRemnantDisplayFlag;
    }
    
    /**
     * 入荷残数初期入力を行うをセットする。
     * @param remnantDisplayFlag The wRemnantDisplayFlag to set.
     */
    public void setRemnantDisplayFlag(boolean remnantDisplayFlag)
    {
        wRemnantDisplayFlag = remnantDisplayFlag;
    }
    
	/**
	 * 作業No.をセットします。
	 * @param セットする作業No.
	 */
	public void setJobNo(String arg)
	{
		wJobNo = arg;
	}

	/**
	 * 作業No.を取得します。
	 * @return 作業No.
	 */
	public String getJobNo()
	{
		return wJobNo;
	}
	
	/**
	 * 実績報告区分をセットします。
	 * @param arg セットする実績報告区分
	 */
	public void setReportFlag(String arg)
	{
		wReportFlag = arg;
	}

	/**
	 * 実績報告区分を取得します。
	 * @return 実績報告区分
	 */
	public String getReportFlag()
	{
		return wReportFlag;
	}

	/**
	 * 実績報告区分名称をセットします。
	 * @param arg セットする実績報告区分名称
	 */
	public void setReportFlagName(String arg)
	{
		wReportFlagName = arg;
	}

	/**
	 * 実績報告区分名称を取得します。
	 * @return 実績報告区分名称
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
