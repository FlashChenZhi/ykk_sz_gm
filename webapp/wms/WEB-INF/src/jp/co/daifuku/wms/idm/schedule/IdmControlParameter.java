package jp.co.daifuku.wms.idm.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.common.Parameter;

/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * <CODE>IdmControlParameter</CODE>クラスは、移動ラックパッケージ内の画面～スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは移動ラックパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>IdmControlParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     作業者コード <BR>
 *     パスワード <BR>
 *     荷主コード <BR>
 *     荷主名称 <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     ケース/ピース区分 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     入庫ケース数 <BR>
 *     入庫ピース数 <BR>
 *     出庫ケース数 <BR>
 *     出庫ピース数 <BR>
 *     在庫ケース数 <BR>
 *     在庫ピース数 <BR>
 *     引当可能ケース数 <BR>
 *     引当可能ピース数 <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     賞味期限 <BR>
 *     リスト発行区分 <BR>
 *     エリアNo <BR>
 *     棚No. <BR>
 *     開始棚No. <BR>
 *     終了棚No. <BR>
 *     バンクNo. <BR>
 *     ベイNo. <BR>
 *     レベルNo. <BR>
 *     開始バンクNo. <BR>
 *     開始ベイNo. <BR>
 *     開始レベルNo. <BR>
 *     終了バンクNo. <BR>
 *     終了ベイNo. <BR>
 *     終了レベルNo. <BR>
 *     出荷先コード <BR>
 *     出荷先名称 <BR>
 *     全数区分 <BR>
 *     ためうち行No. <BR>
 *     在庫ID <BR>
 *     最終更新日時 <BR>
 *     在庫数 <BR>
 *     引当数 <BR>
 *     荷主コード(表示用) <BR>
 *     商品コード(表示用) <BR>
 *     ケースピース区分(表示用) <BR>
 *     ケース/ピース区分名称（表示用）<BR>
 *     ケースITF(表示用) <BR>
 *     ボールITF(表示用) <BR>
 *     賞味期限(表示用) <BR>
 *     入庫日時 <BR>
 * <BR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/07</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class IdmControlParameter extends Parameter
{
	// Class feilds ------------------------------------------------
	/**
	 * ケース/ピース区分(全て)
	 */
	public static final String CASEPIECE_FLAG_ALL = "99";

	/**
	 * ケース/ピース区分(ケース)
	 */
	public static final String CASEPIECE_FLAG_CASE = "1";

	/**
	 * ケース/ピース区分(ピース)
	 */
	public static final String CASEPIECE_FLAG_PIECE = "2";

	/**
	 * ケース/ピース区分(指定なし)
	 */
	public static final String CASEPIECE_FLAG_NOTHING = "3";

	/**
	 * 選択BoxON/OFF（ON）
	 */
	public static final boolean SELECT_BOX_ON = true;

	/**
	 * 選択BoxON/OFF（OFF）
	 */
	public static final boolean SELECT_BOX_OFF = false;

	/**
	 * 範囲フラグ（開始）
	 */
	public static final String RANGE_START = "0";

	/**
	 * 範囲フラグ（終了）
	 */
	public static final String RANGE_END = "1";

	// Class variables -----------------------------------------------
	/**
	 * 作業者コード
	 */
	private String wWorkerCode;

	/**
	 * パスワード
	 */
	private String wPassword;

	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

	/**
	 * 荷主名称
	 */
	private String wConsignorName;

	/**
	 * 商品コード
	 */
	private String wItemCode;

	/**
	 * 商品名称
	 */
	private String wItemName;

	/**
	 * ケース/ピース区分
	 */
	private String wCasePieceFlag;

	/**
	 * ケース入数
	 */
	private int wEnteringQty;

	/**
	 * ボール入数
	 */
	private int wBundleEnteringQty;

	/**
	 * 入庫ケース数
	 */
	private int wStorageCaseQty;

	/**
	 * 入庫ピース数
	 */
	private int wStoragePieceQty;

	/**
	 * 出庫ケース数
	 */
	private int wRetrievalCaseQty;

	/**
	 * 出庫ピース数
	 */
	private int wRetrievalPieceQty;

	/**
	 * 在庫ケース数
	 */
	private int wStockCaseQty;
	
	/**
	 * 在庫ピース数
	 */
	private int wStockPieceQty;

	/**
	 * 引当可能ケース数
	 */
	private int wAllocateCaseQty;

	/**
	 * 引当可能ピース数
	 */
	private int wAllocatePieceQty;

	/**
	 * ケースITF
	 */
	private String wITF;

	/**
	 * ボールITF
	 */
	private String wBundleITF;

	/**
	 * 賞味期限
	 */
	private String wUseByDate;

	/**
	 * リスト発行区分
	 */
	private boolean wListFlg = false;

	/**
	 * エリアNo.
	 */
	private String wAreaNo;

	/**
	 * 棚No.
	 */
	private String wLocationNo;

	/**
	 * 開始棚No.
	 */
	private String wFromLocationNo;

	/**
	 * 終了棚No.
	 */
	private String wToLocationNo;

	/**
	 * バンクNo.
	 */
	private String wBankNo;
	
	/**
	 * バンクNo.配列
	 */
	private String[] wBankNoArray;	

	/**
	 * ベイNo.
	 */
	private String wBayNo;

	/**
	 * レベルNo.
	 */
	private String wLevelNo;

	/**
	 * 開始バンクNo.
	 */
	private String wStartBankNo;

	/**
	 * 開始ベイNo.
	 */
	private String wStartBayNo;

	/**
	 * 開始レベルNo.
	 */
	private String wStartLevelNo;

	/**
	 * 終了バンクNo.
	 */
	private String wEndBankNo;

	/**
	 * 終了ベイNo.
	 */
	private String wEndBayNo;

	/**
	 * 終了レベルNo.
	 */
	private String wEndLevelNo;

	/**
	 * 出荷先コード
	 */
	private String wCustomerCode;

	/**
	 * 出荷先名称
	 */
	private String wCustomerName;

	/**
	 * 全数区分
	 */
	private boolean wTotalFlag;

	/**
	 * ためうち行No.
	 */
	private int wRowNo;

	/**
	 * 在庫ID
	 */
	private String wStockId;

	/**
	 * 最終更新日時
	 */
	private java.util.Date wLastUpdateDate;

	/**
	 * 在庫数
	 */
    private int wStockQty ;
    
    /**
     * 引当数
     */
    private int wAllocateQty ;
    
	/**
	 * 荷主コード(表示用)
	 */
	private String wConsignorCodeDisp ;
   
	/**
	 * 商品コード(表示用)
	 */
	private String wItemCodeDisp ;
    
	/**
	 * ケースピース区分(表示用)
	 */
	private String wCasePieceFlagDisp ;

	/**
	 * ケース/ピース区分名称（表示用）
	 */
	private String wCasePieceFlagNameDisp;
    
	/**
	 * ケースITF(表示用)
	 */
	private String wITFDisp ;
    
	/**
	 * ボールITF(表示用)
	 */
	private String wBundleITFDisp ;

	/**
	 * 賞味期限(表示用)
	 */
	private String wUseByDateDisp;
	
	/**
	 * 入庫日時
	 */
	private java.util.Date wStorageDate;
    
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $" );
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public IdmControlParameter()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * 作業者コードをセットします。
	 * @param arg セットする作業者コード
	 */
	public void setWorkerCode(String arg)
	{
		wWorkerCode = arg;
	}

	/**
	 * 作業者コードを取得します。
	 * @return 作業者コード
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	/**
	 * パスワードをセットします。
	 * @param arg セットするパスワード
	 */
	public void setPassword(String arg)
	{
		wPassword = arg;
	}

	/**
	 * パスワードを取得します。
	 * @return パスワード
	 */
	public String getPassword()
	{
		return wPassword;
	}

	/**
	 * 荷主コードをセットします。
	 * @param arg セットする荷主コード
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg;
	}

	/**
	 * 荷主コードを取得します。
	 * @return 荷主コード
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	/**
	 * 荷主名称をセットします。
	 * @param arg セットする荷主名称
	 */
	public void setConsignorName(String arg)
	{
		wConsignorName = arg;
	}

	/**
	 * 荷主名称を取得します。
	 * @return 荷主名称
	 */
	public String getConsignorName()
	{
		return wConsignorName;
	}

	/**
	 * 商品コードをセットします。
	 * @param arg セットする商品コード
	 */
	public void setItemCode(String arg)
	{
		wItemCode = arg;
	}

	/**
	 * 商品コードを取得します。
	 * @return 商品コード
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	/**
	 * 商品名称をセットします。
	 * @param arg セットする商品名称
	 */
	public void setItemName(String arg)
	{
		wItemName = arg;
	}

	/**
	 * 商品名称を取得します。
	 * @return 商品名称
	 */
	public String getItemName()
	{
		return wItemName;
	}

	/**
	 * ケース/ピース区分をセットします。
	 * @param セットするケース/ピース区分
	 */
	public void setCasePieceFlag(String arg)
	{
		wCasePieceFlag = arg;
	}

	/**
	 * ケース/ピース区分を取得します。
	 * @return ケース/ピース区分
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	/**
	 * ケース入数をセットします。
	 * @param arg セットするケース入数
	 */
	public void setEnteringQty(int arg)
	{
		wEnteringQty = arg;
	}

	/**
	 * ケース入数を取得します。
	 * @return ケース入数
	 */
	public int getEnteringQty()
	{
		return wEnteringQty;
	}

	/**
	 * ボール入数をセットします。
	 * @param arg セットするボール入数
	 */
	public void setBundleEnteringQty(int arg)
	{
		wBundleEnteringQty = arg;
	}

	/**
	 * ボール入数を取得します。
	 * @return ボール入数
	 */
	public int getBundleEnteringQty()
	{
		return wBundleEnteringQty;
	}

	/**
	 * 入庫ケース数をセットします。
	 * @param arg セットする入庫ケース数
	 */
	public void setStorageCaseQty(int arg)
	{
		wStorageCaseQty = arg;
	}

	/**
	 * 入庫ケース数を取得します。
	 * @return 入庫ケース数
	 */
	public int getStorageCaseQty()
	{
		return wStorageCaseQty;
	}

	/**
	 * 入庫ピース数をセットします。
	 * @param arg セットする入庫ピース数
	 */
	public void setStoragePieceQty(int arg)
	{
		wStoragePieceQty = arg;
	}

	/**
	 * 入庫ピース数を取得します。
	 * @return 入庫ピース数
	 */
	public int getStoragePieceQty()
	{
		return wStoragePieceQty;
	}

	/**
	 * 出庫ケース数をセットします。
	 * @param arg セットする出庫ケース数
	 */
	public void setRetrievalCaseQty(int arg)
	{
		wRetrievalCaseQty = arg;
	}

	/**
	 * 出庫ケース数を取得します。
	 * @return 出庫ケース数
	 */
	public int getRetrievalCaseQty()
	{
		return wRetrievalCaseQty;
	}

	/**
	 * 出庫ピース数をセットします。
	 * @param arg セットする出庫ピース数
	 */
	public void setRetrievalPieceQty(int arg)
	{
		wRetrievalPieceQty = arg;
	}

	/**
	 * 出庫ピース数を取得します。
	 * @return 出庫ピース数
	 */
	public int getRetrievalPieceQty()
	{
		return wRetrievalPieceQty;
	}

	/**
	 * 在庫ケース数をセットします。
	 * @param arg セットする在庫ケース数
	 */
	public void setStockCaseQty(int arg)
	{
		wStockCaseQty = arg;
	}

	/**
	 * 在庫ケース数を取得します。
	 * @return 在庫ケース数
	 */
	public int getStockCaseQty()
	{
		return wStockCaseQty;
	}

	/**
	 * 在庫ピース数をセットします。
	 * @param arg セットする在庫ピース数
	 */
	public void setStockPieceQty(int arg)
	{
		wStockPieceQty = arg;
	}

	/**
	 * 在庫ピース数を取得します。
	 * @return 在庫ピース数
	 */
	public int getStockPieceQty()
	{
		return wStockPieceQty;
	}

	/**
	 * 引当可能ケース数をセットします。
	 * @param arg セットする引当可能ケース数
	 */
	public void setAllocateCaseQty(int arg)
	{
		wAllocateCaseQty = arg;
	}

	/**
	 * 引当可能ケース数を取得します。
	 * @return 引当可能ケース数
	 */
	public int getAllocateCaseQty()
	{
		return wAllocateCaseQty;
	}

	/**
	 * 引当可能ピース数をセットします。
	 * @param arg セットする引当可能ピース数
	 */
	public void setAllocatePieceQty(int arg)
	{
		wAllocatePieceQty = arg;
	}

	/**
	 * 引当可能ピース数を取得します。
	 * @return 引当可能ピース数
	 */
	public int getAllocatePieceQty()
	{
		return wAllocatePieceQty;
	}

	/**
	 * ケースITFをセットします。
	 * @param arg セットするケースITF
	 */
	public void setITF(String arg)
	{
		wITF = arg;
	}

	/**
	 * ケースITFを取得します。
	 * @return ケースITF
	 */
	public String getITF()
	{
		return wITF;
	}

	/**
	 * ボールITFをセットします。
	 * @param arg セットするボールITF
	 */
	public void setBundleITF(String arg)
	{
		wBundleITF = arg;
	}

	/**
	 * ボールITFを取得します。
	 * @return ボールITF
	 */
	public String getBundleITF()
	{
		return wBundleITF;
	}

	/**
	 * 賞味期限をセットします。
	 * @param arg セットする賞味期限
	 */
	public void setUseByDate(String arg)
	{
		wUseByDate = arg;
	}

	/**
	 * 賞味期限を取得します。
	 * @return 賞味期限
	 */
	public String getUseByDate()
	{
		return wUseByDate;
	}

	/**
	 * リスト発行区分をセットします。
	 * @param arg セットするリスト発行区分
	 */
	public void setListFlg(boolean arg)
	{
		wListFlg = arg;
	}

	/**
	 * リスト発行区分を取得します。
	 * @return リスト発行区分
	 */
	public boolean getListFlg()
	{
		return wListFlg;
	}

	/**
	 * エリアNo.をセットします。
	 * @param arg セットするエリアNo.
	 */
	public void setAreaNo(String arg)
	{
		wAreaNo = arg;
	}

	/**
	 * エリアNo.を取得します。
	 * @return エリアNo.
	 */
	public String getAreaNo()
	{
		return wAreaNo;
	}

	/**
	 * 棚No.をセットします。
	 * @param arg セットする棚No.
	 */
	public void setLocationNo(String arg)
	{
		wLocationNo = arg;
	}

	/**
	 * 棚No.を取得します。
	 * @return 棚No.
	 */
	public String getLocationNo()
	{
		return wLocationNo;
	}

	/**
	 * 開始棚No.をセットします。
	 * @param arg セットする開始棚No.
	 */
	public void setFromLocationNo(String arg)
	{
		wFromLocationNo = arg;
	}

	/**
	 * 開始棚No.を取得します。
	 * @return 開始棚No.
	 */
	public String getFromLocationNo()
	{
		return wFromLocationNo;
	}

	/**
	 * 終了棚No.をセットします。
	 * @param arg セットする終了棚No.
	 */
	public void setToLocationNo(String arg)
	{
		wToLocationNo = arg;
	}

	/**
	 * 終了棚No.を取得します。
	 * @return 終了棚No.
	 */
	public String getToLocationNo()
	{
		return wToLocationNo;
	}

	/**
	 * バンクNo.をセットします。
	 * @param arg セットするバンクNo.
	 */
	public void setBankNo(String arg)
	{
		wBankNo = arg;
	}

	/**
	 * バンクNo.を取得します。
	 * @return バンクNo.
	 */
	public String getBankNo()
	{
		return wBankNo;
	}

	/**
	 * ベイNo.をセットします。
	 * @param arg セットするベイNo.
	 */
	public void setBayNo(String arg)
	{
		wBayNo = arg;
	}

	/**
	 * ベイNo.を取得します。
	 * @return ベイNo.
	 */
	public String getBayNo()
	{
		return wBayNo;
	}

	/**
	 * レベルNo.をセットします。
	 * @param arg セットするレベルNo.
	 */
	public void setLevelNo(String arg)
	{
		wLevelNo = arg;
	}

	/**
	 * レベルNo.を取得します。
	 * @return レベルNo.
	 */
	public String getLevelNo()
	{
		return wLevelNo;
	}

	/**
	 * 開始バンクNo.をセットします。
	 * @param arg セットする開始バンクNo.
	 */
	public void setStartBankNo(String arg)
	{
		wStartBankNo = arg;
	}

	/**
	 * 開始バンクNo.を取得します。
	 * @return 開始バンクNo.
	 */
	public String getStartBankNo()
	{
		return wStartBankNo;
	}

	/**
	 * 開始ベイNo.をセットします。
	 * @param arg セットする開始ベイNo.
	 */
	public void setStartBayNo(String arg)
	{
		wStartBayNo = arg;
	}

	/**
	 * 開始ベイNo.を取得します。
	 * @return 開始ベイNo.
	 */
	public String getStartBayNo()
	{
		return wStartBayNo;
	}

	/**
	 * 開始レベルNo.をセットします。
	 * @param arg セットする開始レベルNo.
	 */
	public void setStartLevelNo(String arg)
	{
		wStartLevelNo = arg;
	}

	/**
	 * 開始レベルNo.を取得します。
	 * @return 開始レベルNo.
	 */
	public String getStartLevelNo()
	{
		return wStartLevelNo;
	}

	/**
	 * 終了バンクNo.をセットします。
	 * @param arg セットする終了バンクNo.
	 */
	public void setEndBankNo(String arg)
	{
		wEndBankNo = arg;
	}

	/**
	 * 終了バンクNo.を取得します。
	 * @return 終了バンクNo.
	 */
	public String getEndBankNo()
	{
		return wEndBankNo;
	}

	/**
	 * 終了ベイNo.をセットします。
	 * @param arg セットする終了ベイNo.
	 */
	public void setEndBayNo(String arg)
	{
		wEndBayNo = arg;
	}

	/**
	 * 終了ベイNo.を取得します。
	 * @return 終了ベイNo.
	 */
	public String getEndBayNo()
	{
		return wEndBayNo;
	}

	/**
	 * 終了レベルNo.をセットします。
	 * @param arg セットする終了レベルNo.
	 */
	public void setEndLevelNo(String arg)
	{
		wEndLevelNo = arg;
	}

	/**
	 * 終了レベルNo.を取得します。
	 * @return 終了レベルNo.
	 */
	public String getEndLevelNo()
	{
		return wEndLevelNo;
	}

	/**
	 * 出荷先コードをセットします。
	 * @param arg セットする出荷先コード
	 */
	public void setCustomerCode(String arg)
	{
		wCustomerCode = arg;
	}

	/**
	 * 出荷先コードを取得します。
	 * @return 出荷先コード
	 */
	public String getCustomerCode()
	{
		return wCustomerCode;
	}

	/**
	 * 出荷先名称をセットします。
	 * @param arg セットする荷主名称
	 */
	public void setCustomerName(String arg)
	{
		wCustomerName = arg;
	}

	/**
	 * 出荷先名称を取得します。
	 * @return 荷主名称
	 */
	public String getCustomerName()
	{
		return wCustomerName;
	}

	/**
	 * 全数区分をセットします。
	 * @param arg セットする全数区分
	 */
	public void setTotalFlag(boolean arg)
	{
		wTotalFlag = arg;
	}

	/**
	 * 全数区分を取得します。
	 * @return 全数区分
	 */
	public boolean getTotalFlag()
	{
		return wTotalFlag;
	}

	/**
	 * ためうち行No.をセットします。
	 * @param arg セットするためうち行No.
	 */
	public void setRowNo(int arg)
	{
		wRowNo = arg;
	}

	/**
	 * ためうち行No.を取得します。
	 * @return ためうち行No.
	 */
	public int getRowNo()
	{
		return wRowNo;
	}

	/**
	 * 在庫IDをセットします。
	 * @param セットする在庫ID
	 */
	public void setStockId(String arg)
	{
		wStockId = arg;
	}

	/**
	 * 在庫IDを取得します。
	 * @return 在庫ID
	 */
	public String getStockId()
	{
		return wStockId;
	}

	/**
	 * 最終更新日時をセットします。
	 * @param セットする最終更新日時
	 */
	public void setLastUpdateDate(java.util.Date arg)
	{
		wLastUpdateDate = arg;
	}

	/**
	 * 最終更新日時を取得します。
	 * @return 最終更新日時
	 */
	public java.util.Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	/**
	 * 在庫数をセットします。
	 * @param セットする在庫数
	 */
   public void setAllocateQty(int allocateQty)
	{
		wAllocateQty = allocateQty;
	}
	
	/**
	 * 在庫数を取得します。
	 * @return 在庫数
	 */
    public int getAllocateQty()
    {
        return wAllocateQty;
    }
    
	/**
	 * 引当数をセットします。
	 * @param セットする引当数
	 */
	public void setStockQty(int stockQty)
	{
		wStockQty = stockQty;
	}
    
	/**
	 * 引当数を取得します。
	 * @return 引当数
	 */
    public int getStockQty()
    {
        return wStockQty;
    }

	/**
	 * 荷主コード(表示用)をセットします。
	 * @param arg セットする荷主コード(表示用)
	 */
	public void setConsignorCodeDisp(String arg)
	{
		wConsignorCodeDisp = arg;
	}

	/**
	 * 荷主コード(表示用)を取得します。
	 * @return 荷主コード(表示用)
	 */
	public String getConsignorCodeDisp()
	{
		return wConsignorCodeDisp;
	}
	
	/**
	 * 商品コード(表示用)をセットします。
	 * @param arg セットする商品コード(表示用)
	 */
	public void setItemCodeDisp(String arg)
	{
		wItemCodeDisp = arg;
	}

	/**
	 * 商品コード(表示用)を取得します。
	 * @return 商品コード(表示用)
	 */
	public String getItemCodeDisp()
	{
		return wItemCodeDisp;
	}
	
	/**
	 * ケースピース区分(表示用)をセットします。
	 * @param arg セットするケースピース区分(表示用)
	 */
	public void setCasePieceFlagDisp(String arg)
	{
		wCasePieceFlagDisp = arg;
	}

	/**
	 * ケースピース区分(表示用)を取得します。
	 * @return ケースピース区分(表示用)
	 */
	public String getCasePieceFlagDisp()
	{
		return wCasePieceFlagDisp;
	}

	/**
	 * ケース/ピース区分名称(表示用)をセットします。
	 * @param セットするケース/ピース区分名称(表示用)
	 */
	public void setCasePieceFlagNameDisp(String arg)
	{
		wCasePieceFlagNameDisp = arg;
	}

	/**
	 * ケース/ピース区分名称(表示用)を取得します。
	 * @return ケース/ピース区分名称(表示用)
	 */
	public String getCasePieceFlagNameDisp()
	{
		return wCasePieceFlagNameDisp;
	}
	
	/**
	 * ケースITF(表示用)をセットします。
	 * @param arg セットするケースITF(表示用)
	 */
	public void setITFDisp(String arg)
	{
		wITFDisp = arg;
	}

	/**
	 * ケースITF(表示用)を取得します。
	 * @return ケースITF(表示用)
	 */
	public String getITFDisp()
	{
		return wITFDisp;
	}
	
	/**
	 * ボールITF(表示用)をセットします。
	 * @param arg セットするボールITF(表示用)
	 */
	public void setBundleITFDisp(String arg)
	{
		wBundleITFDisp = arg;
	}

	/**
	 * ボールITF(表示用)を取得します。
	 * @return ボールITF(表示用)
	 */
	public String getBundleITFDisp()
	{
		return wBundleITFDisp;
	}
	
	/**
	 * 賞味期限(表示用)をセットします。
	 * @param arg セットする賞味期限(表示用)
	 */
	public void setUseByDateDisp(String arg)
	{
		wUseByDateDisp = arg;
	}

	/**
	 * 賞味期限(表示用)を取得します。
	 * @return 賞味期限(表示用)
	 */
	public String getUseByDateDisp()
	{
		return wUseByDateDisp;
	}

	/**
	 * バンクNo.配列を取得します。
	 * @return バンクNo配列
	 */
	public String[] getBankNoArray()
	{
		return wBankNoArray;
	}
	/**
	 * バンクNo.配列をセットします。
	 * @param bankNoArray セットするバンクNo.配列
	 */
	public void setBankNoArray(String[] bankNoArray)
	{
		wBankNoArray = bankNoArray;
	}
	
	/**
	 * 入庫日時をセットします。
	 * @param arg セットする入庫日時
	 */
	public void setStorageDate(java.util.Date arg)
	{
		wStorageDate = arg;
	}

	/**
	 * 入庫日時を取得します。
	 * @return 入庫日時
	 */
	public java.util.Date getStorageDate()
	{
		return wStorageDate;
	}
}
//end of class
