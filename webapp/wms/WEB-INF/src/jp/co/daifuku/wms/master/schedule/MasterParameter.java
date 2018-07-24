package jp.co.daifuku.wms.master.schedule;

import jp.co.daifuku.wms.base.common.Parameter;

/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : E.Takeda <BR>
 * Maker : E.Takeda <BR>
 * <BR>
 * <CODE>MasterParameter</CODE>クラスは、マスタパッケージ内の画面～スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスではマスタパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>MasterParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 * [テキストボックス or ラベル] <BR>
 *     作業者コード <BR>
 *     パスワード <BR>
 *     荷主コード <BR>
 *     荷主名称<BR>
 *     仕入先コード <BR>
 *     仕入先名称<BR>
 *     出荷先コード <BR>
 *     出荷先名称<BR>
 *     郵便番号 <BR>
 *     県名 <BR>
 *     住所 <BR>
 *     ビル名等 <BR>
 *     連絡先1 <BR>
 *     連絡先2 <BR>
 *     連絡先3 <BR>
 *     商品コード <BR>
 *     JANコード <BR>
 *     商品名称 <BR>
 *     ｹｰｽ入数 <BR>
 *     ｹｰｽITF <BR>
 *     ﾎﾞｰﾙ入数 <BR>
 *     ﾎﾞｰﾙITF <BR>
 *     商品分類コード <BR>
 *     上限在庫数 <BR>
 *     下限在庫数 <BR>
 *     最終更新日 <BR>
 *     最終更新日(String型)<BR>
 *     最終更新処理名 <BR>
 *     最終使用日<BR>
 *     最終使用日(String型) <BR>
 * <BR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/10</TD><TD>E.Takeda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterParameter extends Parameter
{
    
	/** 処理結果 (OK) */
	public static final int RET_OK = 0 ;

	/** 処理結果 (NG) */
	public static final int RET_NG = -1 ;

	/** 処理結果 (整合性エラー: 名称の重複) */
	public static final int RET_CONSIST_NAME_EXIST = 3 ;

	/**
	 * 検索フラグ(荷主マスタ)
	 */
	public static final String SEARCHFLAG_CONSIGNOR = "0";
	
	/**
	 * 検索フラグ(仕入先マスタ)
	 */
	public static final String SEARCHFLAG_SUPPLIER = "1";
	
	/**
	 * 検索フラグ(出荷先マスタ)
	 */
	public static final String SEARCHFLAG_CUSTOMER = "2";
	
	/**
	 * 検索フラグ(商品マスタ)
	 */
	public static final String SEARCHFLAG_ITEM = "3";
	
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
	 * 仕入先コード
	 */
	private String wSupplierCode;

	/**
	 * 仕入先名称
	 */
	private String wSupplierName;
	
	/**
	 * 出荷先コード
	 */
	private String wCustomerCode;

	/**
	 * 出荷先名称
	 */
	private String wCustomerName;
	
	/**
	 * 郵便番号
	 */
	private String wPostalCode;
	
	/**
	 * 県名
	 */
	private String wPrefecture;
	
	/**
	 * 住所
	 */
	private String wAddress;
	
	/**
	 * ビル名等
	 */
	private String wAddress2;
	
	/**
	 * 連絡先1
	 */
	private String wContact1;
	
	/**
	 * 連絡先2
	 */
	private String wContact2;
	
	/**
	 * 連絡先3
	 */
	private String wContact3;
		
	/**
	 * 商品コード
	 */
	private String wItemCode;
	
	/**
	 * JANコード
	 */
	private String wJanCode;

	/**
	 * 商品名称
	 */
	private String wItemName;

	
	/**
	 * ケース入数
	 */
	private int wEnteringQty;

	/**
	 * ボール入数
	 */
	private int wBundleEnteringQty;
	
	/**
	 * ケースITF
	 */
	private String wITF;

	/**
	 * ボールITF
	 */
	private String wBundleITF;

	/**
	 * 商品分類コード
	 */
	private String wItemCategory;
	
	/**
	 * 上限在庫数
	 */
	private int wUpperQty;
	
	/**
	 * 下限在庫数
	 */
	private int wLowerQty;
	
	/**
	 * 最終更新日
	 */
	private java.util.Date wLastUpdateDate;
	
	/**
	 * 最終更新日(String型)
	 */
	private String wLastUpdateDateString;
	
	/**
	 * 最終使用日
	 */
	private java.util.Date wLastUseDate;
	
	/**
	 * 最終使用日(String型)
	 */
	private String wLastUseDateString;
	
	/**
	 * 最終更新処理名
	 */
	private String wLastUpdatePName;
	
	/**
	 * 荷主マスタチェックボックス
	 */
	private boolean wChkConsignorMster;

	/**
	 * 仕入先マスタチェックボックス
	 */
	private boolean wChkSupplierMster;

	/**
	 * 商品マスタチェックボックス
	 */
	private boolean wChkItemMster;

	// Class method --------------------------------------------------
	
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $");
	}
	
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public MasterParameter()
	{
	}
	
	// Public methods ------------------------------------------------


	/**
	 * 住所を取得します。
	 * @return 住所
	 */
	public String getAddress()
	{
		return wAddress;
	}

	/**
	 * 住所をセットします。
	 * @param address セットする住所
	 */
	public void setAddress(String address)
	{
		wAddress = address;
	}

	/**
	 * ビル名等を取得します。
	 * @return ビル名等
	 */
	public String getAddress2()
	{
		return wAddress2;
	}

	/**
	 * ビル名等をセットします。
	 * @param address2 セットするビル名等
	 */
	public void setAddress2(String address2)
	{
		wAddress2 = address2;
	}

	/**
	 * ﾎﾞｰﾙ入数を取得します。
	 * @return ﾎﾞｰﾙ入数
	 */
	public int getBundleEnteringQty()
	{
		return wBundleEnteringQty;
	}

	/**
	 * ﾎﾞｰﾙ入数をセットします。
	 * @param bundleEnteringQty セットするﾎﾞｰﾙ入数
	 */
	public void setBundleEnteringQty(int bundleEnteringQty)
	{
		wBundleEnteringQty = bundleEnteringQty;
	}

	/**
	 * ﾎﾞｰﾙITFを取得します。
	 * @return ﾎﾞｰﾙITF
	 */
	public String getBundleITF()
	{
		return wBundleITF;
	}

	/**
	 * ﾎﾞｰﾙITFをセットします。
	 * @param bundleITF セットするﾎﾞｰﾙITF
	 */
	public void setBundleITF(String bundleITF)
	{
		wBundleITF = bundleITF;
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
	 * 荷主コードをセットします。
	 * @param consignorCode セットする荷主コード
	 */
	public void setConsignorCode(String consignorCode)
	{
		wConsignorCode = consignorCode;
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
	 * 荷主名称をセットします。
	 * @param consignorName  セットする荷主名称
	 */
	public void setConsignorName(String consignorName)
	{
		wConsignorName = consignorName;
	}

	/**
	 * 連絡先1を取得します。
	 * @return 連絡先1
	 */
	public String getContact1()
	{
		return wContact1;
	}

	/**
	 * 連絡先1をセットします。
	 * @param contact1 セットする連絡先1
	 */
	public void setContact1(String contact1)
	{
		wContact1 = contact1;
	}

	/**
	 * 連絡先2を取得します。
	 * @return 連絡先2
	 */
	public String getContact2()
	{
		return wContact2;
	}

	/**
	 * 連絡先2をセットします。
	 * @param contact2 セットする連絡先2
	 */
	public void setContact2(String contact2)
	{
		wContact2 = contact2;
	}

	/**
	 * 連絡先3を取得します。
	 * @return 連絡先3
	 */
	public String getContact3()
	{
		return wContact3;
	}

	/**
	 * 連絡先3をセットします。
	 * @param contact3 セットする連絡先3
	 */
	public void setContact3(String contact3)
	{
		wContact3 = contact3;
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
	 * 出荷先コードをセットします。
	 * @param customerCode セットする出荷先コード
	 */
	public void setCustomerCode(String customerCode)
	{
		wCustomerCode = customerCode;
	}

	/**
	 * 出荷先名称を取得します。
	 * @return 出荷先名称
	 */
	public String getCustomerName()
	{
		return wCustomerName;
	}

	/**
	 * 出荷先名称をセットします。
	 * @param customerName セットする出荷先名称
	 */
	public void setCustomerName(String customerName)
	{
		wCustomerName = customerName;
	}

	/**
	 * ｹｰｽ入数を取得します。
	 * @return ｹｰｽ入数
	 */
	public int getEnteringQty()
	{
		return wEnteringQty;
	}

	/**
	 * ｹｰｽ入数をセットします。
	 * @param enteringQty セットするｹｰｽ入数
	 */
	public void setEnteringQty(int enteringQty)
	{
		wEnteringQty = enteringQty;
	}

	/**
	 * 商品分類コードを取得します。
	 * @return 商品分類コード
	 */
	public String getItemCategory()
	{
		return wItemCategory;
	}

	/**
	 * 商品分類コードをセットします。
	 * @param itemCategory セットする商品分類コード
	 */
	public void setItemCategory(String itemCategory)
	{
		wItemCategory = itemCategory;
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
	 * 商品コードをセットします
	 * @param itemCode セットする商品コード
	 */
	public void setItemCode(String itemCode)
	{
		wItemCode = itemCode;
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
	 * 商品名称をセットします。
	 * @param itemName セットする商品名称
	 */
	public void setItemName(String itemName)
	{
		wItemName = itemName;
	}

	/**
	 * ｹｰｽITFを取得します。
	 * @return ｹｰｽITF
	 */
	public String getITF()
	{
		return wITF;
	}

	/**
	 * ｹｰｽITFをセットします。
	 * @param witf セットするｹｰｽITF
	 */
	public void setITF(String witf)
	{
		wITF = witf;
	}

	/**
	 * JANコードを取得します。
	 * @return JANコード
	 */
	public String getJanCode()
	{
		return wJanCode;
	}

	/**
	 * JANコードをセットします。
	 * @param janCode セットするJANコード
	 */
	public void setJanCode(String janCode)
	{
		wJanCode = janCode;
	}

	/**
	 * 最終更新日（String)を取得します。
	 * @return 最終更新日
	 */
	public String getLastUpdateDateString()
	{
		return wLastUpdateDateString;
	}

	/**
	 * 最終更新日（String)をセットします。
	 * @param lastUpdateDateString セットする最終更新日
	 */
	public void setLastUpdateDateString(String lastUpdateDateString)
	{
		wLastUpdateDateString = lastUpdateDateString;
	}
	
	/**
	 * 最終更新日を取得します。
	 * @return 最終更新日
	 */
	public java.util.Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}
	
	/**
	 * 最終更新日をセットします。
	 * @param　lastUpdateDate セットする最終更新日
	 */
	public void setLastUpdateDate(java.util.Date lastUpdateDate)
	{
		wLastUpdateDate = lastUpdateDate;
	}

	/**
	 * 最終更新処理名を取得します。
	 * @return 最終更新処理名
	 */
	public String getLastUpdatePName()
	{
		return wLastUpdatePName;
	}

	/**
	 * 最終更新処理名をセットします。
	 * @param lastUpdatePName セットする最終更新処理名
	 */
	public void setLastUpdatePName(String lastUpdatePName)
	{
		wLastUpdatePName = lastUpdatePName;
	}

	/**
	 * 最終使用日を取得します。
	 * @return 最終使用日
	 */
	public java.util.Date getLastUseDate()
	{
		return wLastUseDate;
	}

	/**
	 * 最終使用日をセットします。
	 * @param lastUseDate 設定する wLastUseDate。
	 */
	public void setLastUseDate(java.util.Date lastUseDate)
	{
		wLastUseDate = lastUseDate;
	}

	/**
	 * 最終使用日（String)を取得します。
	 * @return 最終使用日
	 */
	public String getLastUseDateString()
	{
		return wLastUseDateString;
	}

	/**
	 * 最終使用日(String)をセットします。
	 * @param lastUseDateString セットする最終使用日
	 */
	public void setLastUseDateString(String lastUseDateString)
	{
		wLastUseDateString = lastUseDateString;
	}

	/**
	 * 下限在庫数を取得します。
	 * @return 下限在庫数
	 */
	public int getLowerQty()
	{
		return wLowerQty;
	}

	/**
	 * 下限在庫数をセットします。
	 * @param lowerQty セットする下限在庫数
	 */
	public void setLowerQty(int lowerQty)
	{
		wLowerQty = lowerQty;
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
	 * パスワードをセットします。
	 * @param password セットするパスワード
	 */
	public void setPassword(String password)
	{
		wPassword = password;
	}

	/**
	 * 郵便番号を取得します。
	 * @return 郵便番号
	 */
	public String getPostalCode()
	{
		return wPostalCode;
	}

	/**
	 * 郵便番号をセットします。
	 * @param postalCode セットする郵便番号
	 */
	public void setPostalCode(String postalCode)
	{
		wPostalCode = postalCode;
	}

	/**
	 * 県名を取得します。
	 * @return 県名
	 */
	public String getPrefecture()
	{
		return wPrefecture;
	}

	/**
	 * 県名をセットします。
	 * @param prefecture セットする県名
	 */
	public void setPrefecture(String prefecture)
	{
		wPrefecture = prefecture;
	}

	/**
	 * 仕入先コードを取得します。
	 * @return 仕入先コード
	 */
	public String getSupplierCode()
	{
		return wSupplierCode;
	}

	/**
	 * 仕入先コードをセットします。
	 * @param supplierCode セットする仕入先コード
	 */
	public void setSupplierCode(String supplierCode)
	{
		wSupplierCode = supplierCode;
	}

	/**
	 * 仕入先名称を取得します。
	 * @return 仕入先名称
	 */
	public String getSupplierName()
	{
		return wSupplierName;
	}

	/**
	 * 仕入先名称をセットします。
	 * @param supplierName セットする仕入先名称
	 */
	public void setSupplierName(String supplierName)
	{
		wSupplierName = supplierName;
	}

	/**
	 * 上限在庫数を取得します。
	 * @return 上限在庫数
	 */
	public int getUpperQty()
	{
		return wUpperQty;
	}

	/**
	 * 上限在庫数をセットします。
	 * @param upperQty セットする上限在庫数
	 */
	public void setUpperQty(int upperQty)
	{
		wUpperQty = upperQty;
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
	 * 作業者コードをセットします。
	 * @param workerCode セットする作業者コード
	 */
	public void setWorkerCode(String workerCode)
	{
		wWorkerCode = workerCode;
	}

	/**
	 * 荷主マスタチェックボックスのチェック状態を取得します。
	 * @return 荷主マスタチェックボックスのチェック状態(True:チェック有 False:チェック無)
	 */
	public boolean getChkConsignorMaster()
	{
		return wChkConsignorMster;
	}

	/**
	 * 荷主マスタチェックボックスのチェック状態をセットします。
	 * @param chk セットする荷主マスタチェックボックスのチェック状態(True:チェック有 False:チェック無)
	 */
	public void setChkConsignorMaster(boolean chk)
	{
		wChkConsignorMster = chk;
	}

	/**
	 * 仕入先マスタチェックボックスのチェック状態を取得します。
	 * @return 仕入先マスタチェックボックスのチェック状態(True:チェック有 False:チェック無)
	 */
	public boolean getChkSupplierMaster()
	{
		return wChkSupplierMster;
	}

	/**
	 * 仕入先マスタチェックボックスのチェック状態をセットします。
	 * @param chk セットする仕入先マスタチェックボックスのチェック状態(True:チェック有 False:チェック無)
	 */
	public void setChkSupplierMaster(boolean chk)
	{
		wChkSupplierMster = chk;
	}

	/**
	 * 商品マスタチェックボックスのチェック状態を取得します。
	 * @return 商品マスタチェックボックスのチェック状態(True:チェック有 False:チェック無)
	 */
	public boolean getChkItemMaster()
	{
		return wChkItemMster;
	}

	/**
	 * 商品マスタチェックボックスのチェック状態をセットします。
	 * @param chk セットする商品マスタチェックボックスのチェック状態(True:チェック有 False:チェック無)
	 */
	public void setChkItemMaster(boolean chk)
	{
		wChkItemMster = chk;
	}

}
