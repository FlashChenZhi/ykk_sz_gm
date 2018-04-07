
package jp.co.daifuku.wms.master.schedule;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * <CODE>DefineDataParameter</CODE>
 * クラスは、各パッケージのデータ取り込み項目設定および、データ報告項目設定の画面～スケジュール間のパラメータの受渡しに使用します。 <BR>
 * このクラスではシステムパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。 <BR>
 * <BR>
 * <DIR><CODE>DefineShippingDataParameter</CODE> クラスが保持する項目 <BR>
 * <BR>
 * [テキストボックス or ラベル] <BR>
 * 作業者コード <BR>
 * パスワード <BR>
 * <BR>
 * 使用桁_荷主コード <BR>
 * 使用桁_荷主名称 <BR>
 * 使用桁_仕入先コード <BR>
 * 使用桁_仕入先名称 <BR>
 * 使用桁_出荷先コード <BR>
 * 使用桁_出荷先名称 <BR>
 * 使用桁_郵便番号 <BR>
 * 使用桁_県名 <BR>
 * 使用桁_住所 <BR>
 * 使用桁_ビル名等 <BR>
 * 使用桁_連絡先1 <BR>
 * 使用桁_連絡先2 <BR>
 * 使用桁_連絡先3 <BR>
 * 使用桁_商品コード <BR>
 * 使用桁_JANコード <BR>
 * 使用桁_商品名称 <BR>
 * 使用桁_ケース入数 <BR>
 * 使用桁_ボール入数 <BR>
 * 使用桁_ケースITF <BR>
 * 使用桁_ボールITF <BR>
 * 使用桁_商品分類コード <BR>
 * 使用桁_上限在庫数 <BR>
 * 使用桁_下限在庫数 <BR>
 * <BR>
 * 最大桁_荷主コード <BR>
 * 最大桁_荷主名称 <BR>
 * 最大桁_仕入先コード <BR>
 * 最大桁_仕入先名称 <BR>
 * 最大桁_出荷先コード <BR>
 * 最大桁_出荷先名称 <BR>
 * 最大桁_郵便番号 <BR>
 * 最大桁_県名 <BR>
 * 最大桁_住所 <BR>
 * 最大桁_ビル名等 <BR>
 * 最大桁_連絡先1 <BR>
 * 最大桁_連絡先2 <BR>
 * 最大桁_連絡先3 <BR>
 * 最大桁_商品コード <BR>
 * 最大桁_JANコード <BR>
 * 最大桁_商品名称 <BR>
 * 最大桁_ケース入数 <BR>
 * 最大桁_ボール入数 <BR>
 * 最大桁_ケースITF <BR>
 * 最大桁_ボールITF <BR>
 * 最大桁_商品分類コード <BR>
 * 最大桁_上限在庫数 <BR>
 * 最大桁_下限在庫数 <BR>
 * <BR>
 * 位置_荷主コード <BR>
 * 位置_荷主名称 <BR>
 * 位置_仕入先コード <BR>
 * 位置_仕入先名称 <BR>
 * 位置_出荷先コード <BR>
 * 位置_出荷先名称 <BR>
 * 位置_郵便番号 <BR>
 * 位置_県名 <BR>
 * 位置_住所 <BR>
 * 位置_ビル名等 <BR>
 * 位置_連絡先1 <BR>
 * 位置_連絡先2 <BR>
 * 位置_連絡先3 <BR>
 * 位置_商品名称 <BR>
 * 位置_ケース入数 <BR>
 * 位置_ボール入数 <BR>
 * 位置_ケースITF <BR>
 * 位置_ボールITF <BR>
 * 位置_商品分類コード <BR>
 * 位置_上限在庫数 <BR>
 * 位置_下限在庫数 <BR>
 * <BR>
 * [チェックボックス] <BR>
 * 有効_荷主コード <BR>
 * 有効_荷主名称 <BR>
 * 有効_仕入先コード <BR>
 * 有効_仕入先名称 <BR>
 * 有効_出荷先コード <BR>
 * 有効_出荷先名称 <BR>
 * 有効_郵便番号 <BR>
 * 有効_県名 <BR>
 * 有効_住所 <BR>
 * 有効_ビル名等 <BR>
 * 有効_連絡先1 <BR>
 * 有効_連絡先2 <BR>
 * 有効_連絡先3 <BR>
 * 有効_商品コード <BR>
 * 有効_JANコード <BR>
 * 有効_商品名称 <BR>
 * 有効_ケース入数 <BR>
 * 有効_ボール入数 <BR>
 * 有効_ケースITF <BR>
 * 有効_ボールITF <BR>
 * 有効_商品分類コード <BR>
 * 有効_上限在庫数 <BR>
 * 有効_下限在庫数 <BR>
 * 
 * 
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/08/05</TD>
 * <TD>K.Matsuda</TD>
 * <TD>新規作成</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author $Author: mori $
 */
public class MasterDefineDataParameter extends SystemParameter
{
    // Class variables -----------------------------------------------
    /**
     * 作業者コード
     */
    private String wWorkerCode = "";

    /**
     * パスワード
     */
    private String wPassword = "";

    /**
     * 使用桁_荷主コード
     */
    private String wFigure_ConsignorCode = "";

    /**
     * 使用桁_荷主名称
     */
    private String wFigure_ConsignorName = "";

    /**
     * 使用桁_仕入先コード
     */
    private String wFigure_SupplierCode = "";

    /**
     * 使用桁_仕入先名称
     */
    private String wFigure_SupplierName = "";

    /**
     * 使用桁_出荷先コード
     */
    private String wFigure_CustomerCode = "";

    /**
     * 使用桁_出荷先名称
     */
    private String wFigure_CustomerName = "";

    /**
     * 使用桁_郵便番号
     */
    private String wFigure_PostalCode = "";

    /**
     * 使用桁_県名
     */
    private String wFigure_Prefecture = "";

    /**
     * 使用桁_住所
     */
    private String wFigure_Adress1 = "";

    /**
     * 使用桁_ビル名称
     */
    private String wFigure_Adress2 = "";

    /**
     * 使用桁_連絡先1
     */
    private String wFigure_Contact1 = "";

    /**
     * 使用桁_連絡先2
     */
    private String wFigure_Contact2 = "";

    /**
     * 使用桁_連絡先3
     */
    private String wFigure_Contact3 = "";

    /**
     * 使用桁_商品コード
     */
    private String wFigure_ItemCode = "";

    /**
     * 使用桁_JANコード
     */
    private String wFigure_JanCode = "";

    /**
     * 使用桁_商品名称
     */
    private String wFigure_ItemName = "";

    /**
     * 使用桁_ケース入数
     */
    private String wFigure_EnteringQty = "";

    /**
     * 使用桁_ボール入数
     */
    private String wFigure_BundleEnteringQty = "";

    /**
     * 使用桁_ケースITF
     */
    private String wFigure_ITF = "";

    /**
     * 使用桁_ボールITF
     */
    private String wFigure_BundleITF = "";

    /**
     * 使用桁_商品分類コード
     */
    private String wFigure_ItemCategory = "";

    /**
     * 使用桁_上限在庫数
     */
    private String wFigure_UpperQty = "";

    /**
     * 使用桁_下限在庫数
     */
    private String wFigure_LOwerQty = "";

    /**
     * 最大桁_荷主コード
     */
    private String wMaxFigure_ConsignorCode = "";

    /**
     * 最大桁_荷主名称
     */
    private String wMaxFigure_ConsignorName = "";

    /**
     * 最大桁_仕入先コード
     */
    private String wMaxFigure_SupplierCode = "";

    /**
     * 最大桁_仕入先名称
     */
    private String wMaxFigure_SupplierName = "";

    /**
     * 最大桁_出荷先コード
     */
    private String wMaxFigure_CustomerCode = "";

    /**
     * 最大桁_出荷先名称
     */
    private String wMaxFigure_CustomerName = "";

    /**
     * 最大桁_郵便番号
     */
    private String wMaxFigure_PostalCode = "";

    /**
     * 最大桁_県名
     */
    private String wMaxFigure_Prefecture = "";

    /**
     * 最大桁_住所
     */
    private String wMaxFigure_Adress1 = "";

    /**
     * 最大桁_ビル名等
     */
    private String wMaxFigure_Adress2 = "";

    /**
     * 最大桁_連絡先1
     */
    private String wMaxFigure_Contact1 = "";

    /**
     * 最大桁_連絡先2
     */
    private String wMaxFigure_Contact2 = "";

    /**
     * 最大桁_連絡先3
     */
    private String wMaxFigure_Contact3 = "";

    /**
     * 最大桁_商品コード
     */
    private String wMaxFigure_ItemCode = "";

    /**
     * 最大桁_JANコード
     */
    private String wMaxFigure_JanCode = "";

    /**
     * 最大桁_商品名称
     */
    private String wMaxFigure_ItemName = "";

    /**
     * 最大桁_ケース入数
     */
    private String wMaxFigure_EnteringQty = "";

    /**
     * 最大桁_ボール入数
     */
    private String wMaxFigure_BundleEnteringQty = "";

    /**
     * 最大桁_ケースITF
     */
    private String wMaxFigure_ITF = "";

    /**
     * 最大桁_ボールITF
     */
    private String wMaxFigure_BundleITF = "";

    /**
     * 最大桁_商品分類コード
     */
    private String wMaxFigure_ItemCategory = "";

    /**
     * 最大桁_上限在庫数
     */
    private String wMaxFigure_UpperQty = "";

    /**
     * 最大桁_下限在庫数
     */
    private String wMaxFigure_LowerQty = "";

    /**
     * 位置_荷主コード
     */
    private String wPosition_ConsignorCode = "";

    /**
     * 位置_荷主名称
     */
    private String wPosition_ConsignorName = "";

    /**
     * 位置_仕入先コード
     */
    private String wPosition_SupplierCode = "";

    /**
     * 位置_仕入先名称
     */
    private String wPosition_SupplierName = "";

    /**
     * 位置_出荷先コード
     */
    private String wPosition_CustomerCode = "";

    /**
     * 位置_出荷先名称
     */
    private String wPosition_CustomerName = "";

    /**
     * 位置_郵便番号
     */
    private String wPosition_PostalCode = "";

    /**
     * 位置_県名
     */
    private String wPosition_Prefecture = "";

    /**
     * 位置_住所
     */
    private String wPosition_Adress1 = "";

    /**
     * 位置_ビル名等
     *  
     */
    private String wPosition_Adress2 = "";

    /**
     * 位置_連絡先1
     */
    private String wPosition_Contact1 = "";

    /**
     * 位置_連絡先2
     */
    private String wPosition_Contact2 = "";

    /**
     * 位置_連絡先3
     */
    private String wPosition_Contact3 = "";

    /**
     * 位置_商品コード
     */
    private String wPosition_ItemCode = "";

    /**
     * 位置_JANコード
     */
    private String wPosition_JanCode = "";

    /**
     * 位置_商品名称
     */
    private String wPosition_ItemName = "";

    /**
     * 位置_ケース入数
     */
    private String wPosition_EnteringQty = "";

    /**
     * 位置_ボール入数
     */
    private String wPosition_BundleEnteringQty = "";

    /**
     * 位置_ケースITF
     */
    private String wPosition_ITF = "";

    /**
     * 位置_ボールITF
     */
    private String wPosition_BundleITF = "";

    /**
     * 位置_商品分類コード
     */
    private String wPosition_ItemCategory = "";

    /**
     * 位置_上限在庫数
     */
    private String wPosition_UpperQty = "";

    /**
     * 位置_下限在庫数
     */
    private String wPosition_LowerQty = "";

    /**
     * 有効_荷主コード
     */
    private boolean wValid_ConsignorCode;

    /**
     * 有効_荷主名称
     */
    private boolean wValid_ConsignorName;

    /**
     * 有効_仕入先コード
     */
    private boolean wValid_SupplierCode;

    /**
     * 有効_仕入先名称
     */
    private boolean wValid_SupplierName;

    /**
     * 有効_出荷先コード
     */
    private boolean wValid_CustomerCode;

    /**
     * 有効_出荷先名称
     */
    private boolean wValid_CustomerName;

    /**
     * 有効_郵便番号
     */
    private boolean wValid_PostalCode;

    /**
     * 有効_県名
     */
    private boolean wValid_Prefecture;

    /**
     * 有効_住所
     */
    private boolean wValid_Adress1;

    /**
     * 有効_ビル名等
     */
    private boolean wValid_Adress2;

    /**
     * 有効_連絡先1
     */
    private boolean wValid_Contact1;

    /**
     * 有効_連絡先2
     */
    private boolean wValid_Contact2;

    /**
     * 有効_連絡先3
     */
    private boolean wValid_Contact3;

    /**
     * 有効_商品コード
     */
    private boolean wValid_ItemCode;

    /**
     * 有効_JANコード
     */
    private boolean wValid_JanCode;

    /**
     * 有効_商品名称
     */
    private boolean wValid_ItemName;

    /**
     * 有効_ケース入数
     */
    private boolean wValid_EnteringQty;

    /**
     * 有効_ボール入数
     */
    private boolean wValid_BundleEnteringQty;

    /**
     * 有効_ケースITF
     */
    private boolean wValid_ITF;

    /**
     * 有効_ボールITF
     */
    private boolean wValid_BundleITF;

    /**
     * 有効_商品分類コード
     */
    private boolean wValid_ItemCategory;

    /**
     * 有効_上限在庫数
     */
    private boolean wValid_UpperCode;

    /**
     * 有効_下限在庫数
     */
    private boolean wValid_LowerCode;

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * 
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
    public MasterDefineDataParameter()
    {
    }

    // Public methods ------------------------------------------------

    /**
     * 最大桁_商品分類コードを設定します。
     * 
     * @param wMaxFigure_ItemCategory
     *            最大桁_商品分類コード
     */
    public void setMaxFigure_ItemCategory(String wMaxFigure_ItemCategory)
    {
        this.wMaxFigure_ItemCategory = wMaxFigure_ItemCategory;
    }

    /**
     * 最大桁_商品分類コードを取得します。
     * 
     * @return 最大桁_商品分類コード
     */
    public String getMaxFigure_ItemCategory()
    {
        return wMaxFigure_ItemCategory;
    }

    /**
     * 有効_出荷先コードを設定します。
     * 
     * @param wValid_CustomerCode
     *            有効_出荷先コード
     */
    public void setValid_CustomerCode(boolean wValid_CustomerCode)
    {
        this.wValid_CustomerCode = wValid_CustomerCode;
    }

    /**
     * 有効_出荷先コードを取得します。
     * 
     * @return 有効_出荷先コード
     */
    public boolean getValid_CustomerCode()
    {
        return wValid_CustomerCode;
    }

    /**
     * 有効_仕入先名称を設定します。
     * 
     * @param wValid_SupplierName
     *            有効_仕入先名称
     */
    public void setValid_SupplierName(boolean wValid_SupplierName)
    {
        this.wValid_SupplierName = wValid_SupplierName;
    }

    /**
     * 有効_仕入先名称を取得します。
     * 
     * @return 有効_仕入先名称
     */
    public boolean getValid_SupplierName()
    {
        return wValid_SupplierName;
    }

    /**
     * 位置_連絡先3を設定します。
     * 
     * @param wPosition_Contact3
     *            位置_連絡先3
     */
    public void setPosition_Contact3(String wPosition_Contact3)
    {
        this.wPosition_Contact3 = wPosition_Contact3;
    }

    /**
     * 位置_連絡先3を取得します。
     * 
     * @return 位置_連絡先3
     */
    public String getPosition_Contact3()
    {
        return wPosition_Contact3;
    }

    /**
     * 位置_連絡先2を設定します。
     * 
     * @param wPosition_Contact2
     *            位置_連絡先2
     */
    public void setPosition_Contact2(String wPosition_Contact2)
    {
        this.wPosition_Contact2 = wPosition_Contact2;
    }

    /**
     * 位置_連絡先2を取得します。
     * 
     * @return 位置_連絡先2
     */
    public String getPosition_Contact2()
    {
        return wPosition_Contact2;
    }

    /**
     * 位置_連絡先1を設定します。
     * 
     * @param wPosition_Contact1
     *            位置_連絡先1
     */
    public void setPosition_Contact1(String wPosition_Contact1)
    {
        this.wPosition_Contact1 = wPosition_Contact1;
    }

    /**
     * 位置_連絡先1を取得します。
     * 
     * @return 位置_連絡先1
     */
    public String getPosition_Contact1()
    {
        return wPosition_Contact1;
    }

    /**
     * 位置_上限在庫数を設定します。
     * 
     * @param wPosition_UpperQty
     *            位置_上限在庫数
     */
    public void setPosition_UpperQty(String wPosition_UpperQty)
    {
        this.wPosition_UpperQty = wPosition_UpperQty;
    }

    /**
     * 位置_上限在庫数を取得します。
     * 
     * @return 位置_上限在庫数
     */
    public String getPosition_UpperQty()
    {
        return wPosition_UpperQty;
    }

    /**
     * 位置_仕入先コードを設定します。
     * 
     * @param wPosition_SupplierCode
     *            位置_仕入先コード
     */
    public void setPosition_SupplierCode(String wPosition_SupplierCode)
    {
        this.wPosition_SupplierCode = wPosition_SupplierCode;
    }

    /**
     * 位置_仕入先コードを取得します。
     * 
     * @return 位置_仕入先コード
     */
    public String getPosition_SupplierCode()
    {
        return wPosition_SupplierCode;
    }

    /**
     * 最大桁_ボールITFを設定します。
     * 
     * @param wMaxFigure_BundleITF
     *            最大桁_ボールITF
     */
    public void setMaxFigure_BundleITF(String wMaxFigure_BundleITF)
    {
        this.wMaxFigure_BundleITF = wMaxFigure_BundleITF;
    }

    /**
     * 最大桁_ボールITFを取得します。
     * 
     * @return 最大桁_ボールITF
     */
    public String getMaxFigure_BundleITF()
    {
        return wMaxFigure_BundleITF;
    }

    /**
     * 使用桁_郵便番号を設定します。
     * 
     * @param wFigure_PostalCode
     *            使用桁_郵便番号
     */
    public void setFigure_PostalCode(String wFigure_PostalCode)
    {
        this.wFigure_PostalCode = wFigure_PostalCode;
    }

    /**
     * 使用桁_郵便番号を取得します。
     * 
     * @return 使用桁_郵便番号
     */
    public String getFigure_PostalCode()
    {
        return wFigure_PostalCode;
    }

    /**
     * 使用桁_仕入先コードを設定します。
     * 
     * @param wFigure_SupplierCode
     *            使用桁_仕入先コード
     */
    public void setFigure_SupplierCode(String wFigure_SupplierCode)
    {
        this.wFigure_SupplierCode = wFigure_SupplierCode;
    }

    /**
     * 使用桁_仕入先コードを取得します。
     * 
     * @return 使用桁_仕入先コード
     */
    public String getFigure_SupplierCode()
    {
        return wFigure_SupplierCode;
    }

    /**
     * 位置_商品コードを設定します。
     * 
     * @param wPosition_ItemCode
     *            位置_商品コード
     */
    public void setPosition_ItemCode(String wPosition_ItemCode)
    {
        this.wPosition_ItemCode = wPosition_ItemCode;
    }

    /**
     * 位置_商品コードを取得します。
     * 
     * @return 位置_商品コード
     */
    public String getPosition_ItemCode()
    {
        return wPosition_ItemCode;
    }

    /**
     * 有効_ケース入数を設定します。
     * 
     * @param wValid_EnteringQty
     *            有効_ケース入数
     */
    public void setValid_EnteringQty(boolean wValid_EnteringQty)
    {
        this.wValid_EnteringQty = wValid_EnteringQty;
    }

    /**
     * 有効_ケース入数を取得します。
     * 
     * @return 有効_ケース入数
     */
    public boolean getValid_EnteringQty()
    {
        return wValid_EnteringQty;
    }

    /**
     * 位置_下限在庫数を設定します。
     * 
     * @param wPosition_LowerQty
     *            位置_下限在庫数
     */
    public void setPosition_LowerQty(String wPosition_LowerQty)
    {
        this.wPosition_LowerQty = wPosition_LowerQty;
    }

    /**
     * 位置_下限在庫数を取得します。
     * 
     * @return 位置_下限在庫数
     */
    public String getPosition_LowerQty()
    {
        return wPosition_LowerQty;
    }

    /**
     * 有効_荷主名称を設定します。
     * 
     * @param wValid_ConsignorName
     *            有効_荷主名称
     */
    public void setValid_ConsignorName(boolean wValid_ConsignorName)
    {
        this.wValid_ConsignorName = wValid_ConsignorName;
    }

    /**
     * 有効_荷主名称を取得します。
     * 
     * @return 有効_荷主名称
     */
    public boolean getValid_ConsignorName()
    {
        return wValid_ConsignorName;
    }

    /**
     * 使用桁_商品名称を設定します。
     * 
     * @param wFigure_ItemName
     *            使用桁_商品名称
     */
    public void setFigure_ItemName(String wFigure_ItemName)
    {
        this.wFigure_ItemName = wFigure_ItemName;
    }

    /**
     * 使用桁_商品名称を取得します。
     * 
     * @return 使用桁_商品名称
     */
    public String getFigure_ItemName()
    {
        return wFigure_ItemName;
    }

    /**
     * 位置_ビル名等を設定します。
     * 
     * @param wPosition_Adress2
     *            位置_ビル名等
     */
    public void setPosition_Adress2(String wPosition_Adress2)
    {
        this.wPosition_Adress2 = wPosition_Adress2;
    }

    /**
     * 位置_ビル名等を取得します。
     * 
     * @return 位置_ビル名等
     */
    public String getPosition_Adress2()
    {
        return wPosition_Adress2;
    }

    /**
     * 位置_住所を設定します。
     * 
     * @param wPosition_Adress1
     *            位置_住所
     */
    public void setPosition_Adress1(String wPosition_Adress1)
    {
        this.wPosition_Adress1 = wPosition_Adress1;
    }

    /**
     * 位置_住所を取得します。
     * 
     * @return 位置_住所
     */
    public String getPosition_Adress1()
    {
        return wPosition_Adress1;
    }

    /**
     * 位置_県名を設定します。
     * 
     * @param wPosition_Prefecture
     *            位置_県名
     */
    public void setPosition_Prefecture(String wPosition_Prefecture)
    {
        this.wPosition_Prefecture = wPosition_Prefecture;
    }

    /**
     * 位置_県名を取得します。
     * 
     * @return 位置_県名
     */
    public String getPosition_Prefecture()
    {
        return wPosition_Prefecture;
    }

    /**
     * 使用桁_荷主名称を設定します。
     * 
     * @param wFigure_ConsignorName
     *            使用桁_荷主名称
     */
    public void setFigure_ConsignorName(String wFigure_ConsignorName)
    {
        this.wFigure_ConsignorName = wFigure_ConsignorName;
    }

    /**
     * 使用桁_荷主名称を取得します。
     * 
     * @return 使用桁_荷主名称
     */
    public String getFigure_ConsignorName()
    {
        return wFigure_ConsignorName;
    }

    /**
     * 使用桁_ビル名等を設定します。
     * 
     * @param wFigure_Adress2
     *            使用桁_ビル名等
     */
    public void setFigure_Adress2(String wFigure_Adress2)
    {
        this.wFigure_Adress2 = wFigure_Adress2;
    }

    /**
     * 使用桁_ビル名等を取得します。
     * 
     * @return 使用桁_ビル名等
     */
    public String getFigure_Adress2()
    {
        return wFigure_Adress2;
    }

    /**
     * 使用桁_住所を設定します。
     * 
     * @param wFigure_Adress1
     *            使用桁_住所
     */
    public void setFigure_Adress1(String wFigure_Adress1)
    {
        this.wFigure_Adress1 = wFigure_Adress1;
    }

    /**
     * 使用桁_住所を取得します。
     * 
     * @return 使用桁_住所
     */
    public String getFigure_Adress1()
    {
        return wFigure_Adress1;
    }

    /**
     * 有効_出荷先名称を設定します。
     * 
     * @param wValid_CustomerName
     *            有効_出荷先名称
     */
    public void setValid_CustomerName(boolean wValid_CustomerName)
    {
        this.wValid_CustomerName = wValid_CustomerName;
    }

    /**
     * 有効_出荷先名称を取得します。
     * 
     * @return 有効_出荷先名称
     */
    public boolean getValid_CustomerName()
    {
        return wValid_CustomerName;
    }

    /**
     * 最大桁_荷主コードを設定します。
     * 
     * @param wMaxFigure_ConsignorCode
     *            最大桁_荷主コード
     */
    public void setMaxFigure_ConsignorCode(String wMaxFigure_ConsignorCode)
    {
        this.wMaxFigure_ConsignorCode = wMaxFigure_ConsignorCode;
    }

    /**
     * 最大桁_荷主コードを取得します。
     * 
     * @return 最大桁_荷主コード
     */
    public String getMaxFigure_ConsignorCode()
    {
        return wMaxFigure_ConsignorCode;
    }

    /**
     * 位置_ボールITFを設定します。
     * 
     * @param wPosition_BundleITF
     *            位置_ボールITF
     */
    public void setPosition_BundleITF(String wPosition_BundleITF)
    {
        this.wPosition_BundleITF = wPosition_BundleITF;
    }

    /**
     * 位置_ボールITFを取得します。
     * 
     * @return 位置_ボールITF
     */
    public String getPosition_BundleITF()
    {
        return wPosition_BundleITF;
    }

    /**
     * 使用桁_ケースITFを設定します。
     * 
     * @param wFigure_ITF
     *            使用桁_ケースITF
     */
    public void setFigure_ITF(String wFigure_ITF)
    {
        this.wFigure_ITF = wFigure_ITF;
    }

    /**
     * 使用桁_ケースITFを取得します。
     * 
     * @return 使用桁_ケースITF
     */
    public String getFigure_ITF()
    {
        return wFigure_ITF;
    }

    /**
     * 有効_商品分類コードを設定します。
     * 
     * @param wValid_ItemCategory
     *            有効_商品分類コード
     */
    public void setValid_ItemCategory(boolean wValid_ItemCategory)
    {
        this.wValid_ItemCategory = wValid_ItemCategory;
    }

    /**
     * 有効_商品分類コードを取得します。
     * 
     * @return 有効_商品分類コード
     */
    public boolean getValid_ItemCategory()
    {
        return wValid_ItemCategory;
    }

    /**
     * 位置_仕入先名称を設定します。
     * 
     * @param wPosition_SupplierName
     *            位置_仕入先名称
     */
    public void setPosition_SupplierName(String wPosition_SupplierName)
    {
        this.wPosition_SupplierName = wPosition_SupplierName;
    }

    /**
     * 位置_仕入先名称を取得します。
     * 
     * @return 位置_仕入先名称
     */
    public String getPosition_SupplierName()
    {
        return wPosition_SupplierName;
    }

    /**
     * 使用桁_JANコードを設定します。
     * 
     * @param wFigure_JanCode
     *            使用桁_JANコード
     */
    public void setFigure_JanCode(String wFigure_JanCode)
    {
        this.wFigure_JanCode = wFigure_JanCode;
    }

    /**
     * 使用桁_JANコードを取得します。
     * 
     * @return 使用桁_JANコード
     */
    public String getFigure_JanCode()
    {
        return wFigure_JanCode;
    }

    /**
     * 有効_上限在庫数を設定します。
     * 
     * @param wValid_UpperCode
     *            有効_上限在庫数
     */
    public void setValid_UpperCode(boolean wValid_UpperCode)
    {
        this.wValid_UpperCode = wValid_UpperCode;
    }

    /**
     * 有効_上限在庫数を取得します。
     * 
     * @return 有効_上限在庫数
     */
    public boolean getValid_UpperCode()
    {
        return wValid_UpperCode;
    }

    /**
     * 位置_荷主コードを設定します。
     * 
     * @param wPosition_ConsignorCode
     *            位置_荷主コード
     */
    public void setPosition_ConsignorCode(String wPosition_ConsignorCode)
    {
        this.wPosition_ConsignorCode = wPosition_ConsignorCode;
    }

    /**
     * 位置_荷主コードを取得します。
     * 
     * @return 位置_荷主コード
     */
    public String getPosition_ConsignorCode()
    {
        return wPosition_ConsignorCode;
    }

    /**
     * 有効_下限在庫数を設定します。
     * 
     * @param wValid_LowerCode
     *            有効_下限在庫数
     */
    public void setValid_LowerCode(boolean wValid_LowerCode)
    {
        this.wValid_LowerCode = wValid_LowerCode;
    }

    /**
     * 有効_下限在庫数を取得します。
     * 
     * @return 有効_下限在庫数
     */
    public boolean getValid_LowerCode()
    {
        return wValid_LowerCode;
    }

    /**
     * 位置_JANコードを設定します。
     * 
     * @param wPosition_JanCode
     *            位置_JANコード
     */
    public void setPosition_JanCode(String wPosition_JanCode)
    {
        this.wPosition_JanCode = wPosition_JanCode;
    }

    /**
     * 位置_JANコードを取得します。
     * 
     * @return 位置_JANコード
     */
    public String getPosition_JanCode()
    {
        return wPosition_JanCode;
    }

    /**
     * 使用桁_仕入先名称を設定します。
     * 
     * @param wFigure_SupplierName
     *            使用桁_仕入先名称
     */
    public void setFigure_SupplierName(String wFigure_SupplierName)
    {
        this.wFigure_SupplierName = wFigure_SupplierName;
    }

    /**
     * 使用桁_仕入先名称を取得します。
     * 
     * @return 使用桁_仕入先名称
     */
    public String getFigure_SupplierName()
    {
        return wFigure_SupplierName;
    }

    /**
     * 位置_商品名称を設定します。
     * 
     * @param wPosition_ItemName
     *            位置_商品名称
     */
    public void setPosition_ItemName(String wPosition_ItemName)
    {
        this.wPosition_ItemName = wPosition_ItemName;
    }

    /**
     * 位置_商品名称を取得します。
     * 
     * @return 位置_商品名称
     */
    public String getPosition_ItemName()
    {
        return wPosition_ItemName;
    }

    /**
     * 使用桁_ケース入数を設定します。
     * 
     * @param wFigure_EnteringQty
     *            使用桁_ケース入数
     */
    public void setFigure_EnteringQty(String wFigure_EnteringQty)
    {
        this.wFigure_EnteringQty = wFigure_EnteringQty;
    }

    /**
     * 使用桁_ケース入数を取得します。
     * 
     * @return 使用桁_ケース入数
     */
    public String getFigure_EnteringQty()
    {
        return wFigure_EnteringQty;
    }

    /**
     * 有効_商品コードを設定します。
     * 
     * @param wValid_ItemCode
     *            有効_商品コード
     */
    public void setValid_ItemCode(boolean wValid_ItemCode)
    {
        this.wValid_ItemCode = wValid_ItemCode;
    }

    /**
     * 有効_商品コードを取得します。
     * 
     * @return 有効_商品コード
     */
    public boolean getValid_ItemCode()
    {
        return wValid_ItemCode;
    }

    /**
     * 位置_ケースITFを設定します。
     * 
     * @param wPosition_ITF
     *            位置_ケースITF
     */
    public void setPosition_ITF(String wPosition_ITF)
    {
        this.wPosition_ITF = wPosition_ITF;
    }

    /**
     * 位置_ケースITFを取得します。
     * 
     * @return 位置_ケースITF
     */
    public String getPosition_ITF()
    {
        return wPosition_ITF;
    }

    /**
     * 位置_出荷先コードを設定します。
     * 
     * @param wPosition_CustomerCode
     *            位置_出荷先コード
     */
    public void setPosition_CustomerCode(String wPosition_CustomerCode)
    {
        this.wPosition_CustomerCode = wPosition_CustomerCode;
    }

    /**
     * 位置_出荷先コードを取得します。
     * 
     * @return 位置_出荷先コード
     */
    public String getPosition_CustomerCode()
    {
        return wPosition_CustomerCode;
    }

    /**
     * 有効_連絡先3を設定します。
     * 
     * @param wValid_Contact3
     *            有効_連絡先3
     */
    public void setValid_Contact3(boolean wValid_Contact3)
    {
        this.wValid_Contact3 = wValid_Contact3;
    }

    /**
     * 有効_連絡先3を取得します。
     * 
     * @return 有効_連絡先3
     */
    public boolean getValid_Contact3()
    {
        return wValid_Contact3;
    }

    /**
     * 有効_連絡先2を設定します。
     * 
     * @param wValid_Contact2
     *            有効_連絡先2
     */
    public void setValid_Contact2(boolean wValid_Contact2)
    {
        this.wValid_Contact2 = wValid_Contact2;
    }

    /**
     * 有効_連絡先2を取得します。
     * 
     * @return 有効_連絡先2
     */
    public boolean getValid_Contact2()
    {
        return wValid_Contact2;
    }

    /**
     * 有効_連絡先1を設定します。
     * 
     * @param wValid_Contact1
     *            有効_連絡先1
     */
    public void setValid_Contact1(boolean wValid_Contact1)
    {
        this.wValid_Contact1 = wValid_Contact1;
    }

    /**
     * 有効_連絡先1を取得します。
     * 
     * @return 有効_連絡先1
     */
    public boolean getValid_Contact1()
    {
        return wValid_Contact1;
    }

    /**
     * 使用桁_ボールITFを設定します。
     * 
     * @param wFigure_BundleITF
     *            使用桁_ボールITF
     */
    public void setFigure_BundleITF(String wFigure_BundleITF)
    {
        this.wFigure_BundleITF = wFigure_BundleITF;
    }

    /**
     * 使用桁_ボールITFを取得します。
     * 
     * @return 使用桁_ボールITF
     */
    public String getFigure_BundleITF()
    {
        return wFigure_BundleITF;
    }

    /**
     * 最大桁_仕入先コードを設定します。
     * 
     * @param wMaxFigure_SupplierCode
     *            最大桁_仕入先コード
     */
    public void setMaxFigure_SupplierCode(String wMaxFigure_SupplierCode)
    {
        this.wMaxFigure_SupplierCode = wMaxFigure_SupplierCode;
    }

    /**
     * 最大桁_仕入先コードを取得します。
     * 
     * @return 最大桁_仕入先コード
     */
    public String getMaxFigure_SupplierCode()
    {
        return wMaxFigure_SupplierCode;
    }

    /**
     * 有効_県名を設定します。
     * 
     * @param wValid_Prefecture
     *            有効_県名
     */
    public void setValid_Prefecture(boolean wValid_Prefecture)
    {
        this.wValid_Prefecture = wValid_Prefecture;
    }

    /**
     * 有効_県名を取得します。
     * 
     * @return 有効_県名
     */
    public boolean getValid_Prefecture()
    {
        return wValid_Prefecture;
    }

    /**
     * 最大桁_商品名称を設定します。
     * 
     * @param wMaxFigure_PieceLocation
     *            最大桁_商品名称
     */
    public void setMaxFigure_ItemName(String wMaxFigure_ItemName)
    {
        this.wMaxFigure_ItemName = wMaxFigure_ItemName;
    }

    /**
     * 最大桁_商品名称を取得します。
     * 
     * @return 最大桁_商品名称
     */
    public String getMaxFigure_ItemName()
    {
        return wMaxFigure_ItemName;
    }

    /**
     * 使用桁_出荷先コードを設定します。
     * 
     * @param wFigure_CustomerCode
     *            使用桁_出荷先コード
     */
    public void setFigure_CustomerCode(String wFigure_CustomerCode)
    {
        this.wFigure_CustomerCode = wFigure_CustomerCode;
    }

    /**
     * 使用桁_出荷先コードを取得します。
     * 
     * @return 使用桁_出荷先コード
     */
    public String getFigure_CustomerCode()
    {
        return wFigure_CustomerCode;
    }

    /**
     * 最大桁_郵便番号を設定します。
     * 
     * @param wMaxFigure_PostalCode
     *            最大桁_郵便番号
     */
    public void setMaxFigure_PostalCode(String wMaxFigure_PostalCode)
    {
        this.wMaxFigure_PostalCode = wMaxFigure_PostalCode;
    }

    /**
     * 最大桁_郵便番号を取得します。
     * 
     * @return 最大桁_郵便番号
     */
    public String getMaxFigure_PostalCode()
    {
        return wMaxFigure_PostalCode;
    }

    /**
     * 使用桁_ボール入数を設定します。
     * 
     * @param wFigure_BundleEnteringQty
     *            使用桁_ボール入数
     */
    public void setFigure_BundleEnteringQty(String wFigure_BundleEnteringQty)
    {
        this.wFigure_BundleEnteringQty = wFigure_BundleEnteringQty;
    }

    /**
     * 使用桁_ボール入数を取得します。
     * 
     * @return 使用桁_ボール入数
     */
    public String getFigure_BundleEnteringQty()
    {
        return wFigure_BundleEnteringQty;
    }

    /**
     * 位置_ケース入数を設定します。
     * 
     * @param wPosition_EnteringQty
     *            位置_ケース入数
     */
    public void setPosition_EnteringQty(String wPosition_EnteringQty)
    {
        this.wPosition_EnteringQty = wPosition_EnteringQty;
    }

    /**
     * 位置_ケース入数を取得します。
     * 
     * @return 位置_ケース入数
     */
    public String getPosition_EnteringQty()
    {
        return wPosition_EnteringQty;
    }

    /**
     * 最大桁_荷主名称を設定します。
     * 
     * @param wMaxFigure_ConsignorName
     *            最大桁_荷主名称
     */
    public void setMaxFigure_ConsignorName(String wMaxFigure_ConsignorName)
    {
        this.wMaxFigure_ConsignorName = wMaxFigure_ConsignorName;
    }

    /**
     * 最大桁_荷主名称を取得します。
     * 
     * @return 最大桁_荷主名称
     */
    public String getMaxFigure_ConsignorName()
    {
        return wMaxFigure_ConsignorName;
    }

    /**
     * 位置_ボール入数を設定します。
     * 
     * @param wPosition_BundleEnteringQty
     *            位置_ボール入数
     */
    public void setPosition_BundleEnteringQty(
            String wPosition_BundleEnteringQty)
    {
        this.wPosition_BundleEnteringQty = wPosition_BundleEnteringQty;
    }

    /**
     * 位置_ボール入数を取得します。
     * 
     * @return 位置_ボール入数
     */
    public String getPosition_BundleEnteringQty()
    {
        return wPosition_BundleEnteringQty;
    }

    /**
     * 有効_ボール入数を設定します。
     * 
     * @param wValid_BundleEnteringQty
     *            有効_ボール入数
     */
    public void setValid_BundleEnteringQty(boolean wValid_BundleEnteringQty)
    {
        this.wValid_BundleEnteringQty = wValid_BundleEnteringQty;
    }

    /**
     * 有効_ボール入数を取得します。
     * 
     * @return 有効_ボール入数
     */
    public boolean getValid_BundleEnteringQty()
    {
        return wValid_BundleEnteringQty;
    }

    /**
     * 位置_荷主名称を設定します。
     * 
     * @param wPosition_ConsignorName
     *            位置_荷主名称
     */
    public void setPosition_ConsignorName(String wPosition_ConsignorName)
    {
        this.wPosition_ConsignorName = wPosition_ConsignorName;
    }

    /**
     * 位置_荷主名称を取得します。
     * 
     * @return 位置_荷主名称
     */
    public String getPosition_ConsignorName()
    {
        return wPosition_ConsignorName;
    }

    /**
     * 使用桁_県名を設定します。
     * 
     * @param wFigure_Prefecture
     *            使用桁_県名
     */
    public void setFigure_Prefecture(String wFigure_Prefecture)
    {
        this.wFigure_Prefecture = wFigure_Prefecture;
    }

    /**
     * 使用桁_県名を取得します。
     * 
     * @return 使用桁_県名
     */
    public String getFigure_Prefecture()
    {
        return wFigure_Prefecture;
    }

    /**
     * パスワードを設定します。
     * 
     * @param wPassword
     *            パスワード
     */
    public void setPassword(String wPassword)
    {
        this.wPassword = wPassword;
    }

    /**
     * パスワードを取得します。
     * 
     * @return パスワード
     */
    public String getPassword()
    {
        return wPassword;
    }

    /**
     * 有効_ビル名等を設定します。
     * 
     * @param wValid_Adress2
     *            有効_ビル名等
     */
    public void setValid_Adress2(boolean wValid_Adress2)
    {
        this.wValid_Adress2 = wValid_Adress2;
    }

    /**
     * 有効_ビル名等を取得します。
     * 
     * @return 有効_ビル名等
     */
    public boolean getValid_Adress2()
    {
        return wValid_Adress2;
    }

    /**
     * 有効_住所を設定します。
     * 
     * @param wValid_Adress1
     *            有効_住所
     */
    public void setValid_Adress1(boolean wValid_Adress1)
    {
        this.wValid_Adress1 = wValid_Adress1;
    }

    /**
     * 有効_住所を取得します。
     * 
     * @return 有効_住所
     */
    public boolean getValid_Adress1()
    {
        return wValid_Adress1;
    }

    /**
     * 最大桁_商品コードを設定します。
     * 
     * @param wMaxFigure_ItemCode
     *            最大桁_商品コード
     */
    public void setMaxFigure_ItemCode(String wMaxFigure_ItemCode)
    {
        this.wMaxFigure_ItemCode = wMaxFigure_ItemCode;
    }

    /**
     * 最大桁_商品コードを取得します。
     * 
     * @return 最大桁_商品コード
     */
    public String getMaxFigure_ItemCode()
    {
        return wMaxFigure_ItemCode;
    }

    /**
     * 有効_商品名称を設定します。
     * 
     * @param wValid_ItemName
     *            有効_商品名称
     */
    public void setValid_ItemName(boolean wValid_ItemName)
    {
        this.wValid_ItemName = wValid_ItemName;
    }

    /**
     * 有効_商品名称を取得します。
     * 
     * @return 有効_商品名称
     */
    public boolean getValid_ItemName()
    {
        return wValid_ItemName;
    }

    /**
     * 位置_郵便番号を設定します。
     * 
     * @param wPosition_PostalCode
     *            位置_郵便番号
     */
    public void setPosition_PostalCode(String wPosition_PostalCode)
    {
        this.wPosition_PostalCode = wPosition_PostalCode;
    }

    /**
     * 位置_郵便番号を取得します。
     * 
     * @return 位置_郵便番号
     */
    public String getPosition_PostalCode()
    {
        return wPosition_PostalCode;
    }

    /**
     * 最大桁_連絡先3を設定します。
     * 
     * @param wMaxFigure_Contact3
     *            最大桁_連絡先3
     */
    public void setMaxFigure_Contact3(String wMaxFigure_Contact3)
    {
        this.wMaxFigure_Contact3 = wMaxFigure_Contact3;
    }

    /**
     * 最大桁_連絡先3を取得します。
     * 
     * @return 最大桁_連絡先3
     */
    public String getMaxFigure_Contact3()
    {
        return wMaxFigure_Contact3;
    }

    /**
     * 位置_出荷先名称を設定します。
     * 
     * @param wPosition_CustomerName
     *            位置_出荷先名称
     */
    public void setPosition_CustomerName(String wPosition_CustomerName)
    {
        this.wPosition_CustomerName = wPosition_CustomerName;
    }

    /**
     * 位置_出荷先名称を取得します。
     * 
     * @return 位置_出荷先名称
     */
    public String getPosition_CustomerName()
    {
        return wPosition_CustomerName;
    }

    /**
     * 最大桁_連絡先2を設定します。
     * 
     * @param wMaxFigure_Contact2
     *            最大桁_連絡先2
     */
    public void setMaxFigure_Contact2(String wMaxFigure_Contact2)
    {
        this.wMaxFigure_Contact2 = wMaxFigure_Contact2;
    }

    /**
     * 最大桁_連絡先2を取得します。
     * 
     * @return 最大桁_連絡先2
     */
    public String getMaxFigure_Contact2()
    {
        return wMaxFigure_Contact2;
    }

    /**
     * 最大桁_下限在庫数を設定します。
     * 
     * @param wMaxFigure_LowerQty
     *            最大桁_下限在庫数
     */
    public void setMaxFigure_LowerQty(String wMaxFigure_LowerQty)
    {
        this.wMaxFigure_LowerQty = wMaxFigure_LowerQty;
    }

    /**
     * 最大桁_下限在庫数を取得します。
     * 
     * @return 最大桁_下限在庫数
     */
    public String getMaxFigure_LowerQty()
    {
        return wMaxFigure_LowerQty;
    }

    /**
     * 最大桁_連絡先1を設定します。
     * 
     * @param wMaxFigure_Contact1
     *            最大桁_連絡先1
     */
    public void setMaxFigure_Contact1(String wMaxFigure_Contact1)
    {
        this.wMaxFigure_Contact1 = wMaxFigure_Contact1;
    }

    /**
     * 最大桁_連絡先1を取得します。
     * 
     * @return 最大桁_連絡先1
     */
    public String getMaxFigure_Contact1()
    {
        return wMaxFigure_Contact1;
    }

    /**
     * 最大桁_上限在庫数を設定します。
     * 
     * @param wMaxFigure_UpperQty
     *            最大桁_上限在庫数
     */
    public void setMaxFigure_UpperQty(String wMaxFigure_UpperQty)
    {
        this.wMaxFigure_UpperQty = wMaxFigure_UpperQty;
    }

    /**
     * 最大桁_上限在庫数を取得します。
     * 
     * @return 最大桁_上限在庫数
     */
    public String getMaxFigure_UpperQty()
    {
        return wMaxFigure_UpperQty;
    }

    /**
     * 最大桁_出荷先コードを設定します。
     * 
     * @param wMaxFigure_CustomerCode
     *            最大桁_出荷先コード
     */
    public void setMaxFigure_CustomerCode(String wMaxFigure_CustomerCode)
    {
        this.wMaxFigure_CustomerCode = wMaxFigure_CustomerCode;
    }

    /**
     * 最大桁_出荷先コードを取得します。
     * 
     * @return 最大桁_出荷先コード
     */
    public String getMaxFigure_CustomerCode()
    {
        return wMaxFigure_CustomerCode;
    }

    /**
     * 最大桁_仕入先名称を設定します。
     * 
     * @param wMaxFigure_SupplierName
     *            最大桁_仕入先名称
     */
    public void setMaxFigure_SupplierName(String wMaxFigure_SupplierName)
    {
        this.wMaxFigure_SupplierName = wMaxFigure_SupplierName;
    }

    /**
     * 最大桁_仕入先名称を取得します。
     * 
     * @return 最大桁_仕入先名称
     */
    public String getMaxFigure_SupplierName()
    {
        return wMaxFigure_SupplierName;
    }

    /**
     * 最大桁_ケースITFを設定します。
     * 
     * @param wMaxFigure_ITF
     *            最大桁_ケースITF
     */
    public void setMaxFigure_ITF(String wMaxFigure_ITF)
    {
        this.wMaxFigure_ITF = wMaxFigure_ITF;
    }

    /**
     * 最大桁_ケースITFを取得します。
     * 
     * @return 最大桁_ケースITF
     */
    public String getMaxFigure_ITF()
    {
        return wMaxFigure_ITF;
    }

    /**
     * 位置_商品分類コードを設定します。
     * 
     * @param wPosition_ItemCategory
     *            位置_商品分類コード
     */
    public void setPosition_ItemCategory(String wPosition_ItemCategory)
    {
        this.wPosition_ItemCategory = wPosition_ItemCategory;
    }

    /**
     * 位置_商品分類コードを取得します。
     * 
     * @return 位置_商品分類コード
     */
    public String getPosition_ItemCategory()
    {
        return wPosition_ItemCategory;
    }

    /**
     * 最大桁_ボール入数を設定します。
     * 
     * @param wMaxFigure_BundleEnteringQty
     *            最大桁_ボール入数
     */
    public void setMaxFigure_BundleEnteringQty(
            String wMaxFigure_BundleEnteringQty)
    {
        this.wMaxFigure_BundleEnteringQty = wMaxFigure_BundleEnteringQty;
    }

    /**
     * 最大桁_ボール入数を取得します。
     * 
     * @return 最大桁_ボール入数
     */
    public String getMaxFigure_BundleEnteringQty()
    {
        return wMaxFigure_BundleEnteringQty;
    }

    /**
     * 有効_仕入先コードを設定します。
     * 
     * @param wValid_SupplierCode
     *            有効_仕入先コード
     */
    public void setValid_SupplierCode(boolean wValid_SupplierCode)
    {
        this.wValid_SupplierCode = wValid_SupplierCode;
    }

    /**
     * 有効_仕入先コードを取得します。
     * 
     * @return 有効_仕入先コード
     */
    public boolean getValid_SupplierCode()
    {
        return wValid_SupplierCode;
    }

    /**
     * 使用桁_出荷先名称を設定します。
     * 
     * @param wFigure_CustomerName
     *            使用桁_出荷先名称
     */
    public void setFigure_CustomerName(String wFigure_CustomerName)
    {
        this.wFigure_CustomerName = wFigure_CustomerName;
    }

    /**
     * 使用桁_出荷先名称を取得します。
     * 
     * @return 使用桁_出荷先名称
     */
    public String getFigure_CustomerName()
    {
        return wFigure_CustomerName;
    }

    /**
     * 使用桁_下限在庫数を設定します。
     * 
     * @param wFigure_LOwerQty
     *            使用桁_下限在庫数
     */
    public void setFigure_LOwerQty(String wFigure_LOwerQty)
    {
        this.wFigure_LOwerQty = wFigure_LOwerQty;
    }

    /**
     * 使用桁_下限在庫数を取得します。
     * 
     * @return 使用桁_下限在庫数
     */
    public String getFigure_LOwerQty()
    {
        return wFigure_LOwerQty;
    }

    /**
     * 最大桁_ケース入数を設定します。
     * 
     * @param wMaxFigure_EnteringQty
     *            最大桁_ケース入数
     */
    public void setMaxFigure_EnteringQty(String wMaxFigure_EnteringQty)
    {
        this.wMaxFigure_EnteringQty = wMaxFigure_EnteringQty;
    }

    /**
     * 最大桁_ケース入数を取得します。
     * 
     * @return 最大桁_ケース入数
     */
    public String getMaxFigure_EnteringQty()
    {
        return wMaxFigure_EnteringQty;
    }

    /**
     * 使用桁_商品分類コードを設定します。
     * 
     * @param wFigure_ItemCategory
     *            使用桁_商品分類コード
     */
    public void setFigure_ItemCategory(String wFigure_ItemCategory)
    {
        this.wFigure_ItemCategory = wFigure_ItemCategory;
    }

    /**
     * 使用桁_商品分類コードを取得します。
     * 
     * @return 使用桁_商品分類コード
     */
    public String getFigure_ItemCategory()
    {
        return wFigure_ItemCategory;
    }

    /**
     * 最大桁_ビル名等を設定します。
     * 
     * @param wMaxFigure_Adress2
     *            最大桁_ビル名等
     */
    public void setMaxFigure_Adress2(String wMaxFigure_Adress2)
    {
        this.wMaxFigure_Adress2 = wMaxFigure_Adress2;
    }

    /**
     * 最大桁_ビル名等を取得します。
     * 
     * @return 最大桁_ビル名等
     */
    public String getMaxFigure_Adress2()
    {
        return wMaxFigure_Adress2;
    }

    /**
     * 最大桁_住所を設定します。
     * 
     * @param wMaxFigure_Adress1
     *            最大桁_住所
     */
    public void setMaxFigure_Adress1(String wMaxFigure_Adress1)
    {
        this.wMaxFigure_Adress1 = wMaxFigure_Adress1;
    }

    /**
     * 最大桁_住所を取得します。
     * 
     * @return 最大桁_住所
     */
    public String getMaxFigure_Adress1()
    {
        return wMaxFigure_Adress1;
    }

    /**
     * 有効_JANコードを設定します。
     * 
     * @param wValid_JanCode
     *            有効_JANコード
     */
    public void setValid_JanCode(boolean wValid_JanCode)
    {
        this.wValid_JanCode = wValid_JanCode;
    }

    /**
     * 有効_JANコードを取得します。
     * 
     * @return 有効_JANコード
     */
    public boolean getValid_JanCode()
    {
        return wValid_JanCode;
    }

    /**
     * 最大桁_JANコードを設定します。
     * 
     * @param wMaxFigure_JanCode
     *            最大桁_JANコード
     */
    public void setMaxFigure_JanCode(String wMaxFigure_JanCode)
    {
        this.wMaxFigure_JanCode = wMaxFigure_JanCode;
    }

    /**
     * 最大桁_JANコードを取得します。
     * 
     * @return 最大桁_JANコード
     */
    public String getMaxFigure_JanCode()
    {
        return wMaxFigure_JanCode;
    }

    /**
     * 作業者コードを設定します。
     * 
     * @param wWorkerCode
     *            作業者コード
     */
    public void setWorkerCode(String wWorkerCode)
    {
        this.wWorkerCode = wWorkerCode;
    }

    /**
     * 作業者コードを取得します。
     * 
     * @return 作業者コード
     */
    public String getWorkerCode()
    {
        return wWorkerCode;
    }

    /**
     * 使用桁_商品コードを設定します。
     * 
     * @param wFigure_itemCode
     *            使用桁_商品コード
     */
    public void setFigure_ItemCode(String wFigure_ItemCode)
    {
        this.wFigure_ItemCode = wFigure_ItemCode;
    }

    /**
     * 使用桁_商品コードを取得します。
     * 
     * @return 使用桁_商品コード
     */
    public String getFigure_ItemCode()
    {
        return wFigure_ItemCode;
    }

    /**
     * 有効_荷主コードを設定します。
     * 
     * @param wValid_ConsignorCode
     *            有効_荷主コード
     */
    public void setValid_ConsignorCode(boolean wValid_ConsignorCode)
    {
        this.wValid_ConsignorCode = wValid_ConsignorCode;
    }

    /**
     * 有効_荷主コードを取得します。
     * 
     * @return 有効_荷主コード
     */
    public boolean getValid_ConsignorCode()
    {
        return wValid_ConsignorCode;
    }

    /**
     * 有効_郵便番号を設定します。
     * 
     * @param wValid_PostalCode
     *            有効_郵便番号
     */
    public void setValid_PostalCode(boolean wValid_PostalCode)
    {
        this.wValid_PostalCode = wValid_PostalCode;
    }

    /**
     * 有効_郵便番号を取得します。
     * 
     * @return 有効_郵便番号
     */
    public boolean getValid_PostalCode()
    {
        return wValid_PostalCode;
    }

    /**
     * 最大桁_県名を設定します。
     * 
     * @param wMaxFigure_Prefecture
     *            最大桁_県名
     */
    public void setMaxFigure_Prefecture(String wMaxFigure_Prefecture)
    {
        this.wMaxFigure_Prefecture = wMaxFigure_Prefecture;
    }

    /**
     * 最大桁_県名を取得します。
     * 
     * @return 最大桁_県名
     */
    public String getMaxFigure_Prefecture()
    {
        return wMaxFigure_Prefecture;
    }

    /**
     * 使用桁_連絡先3を設定します。
     * 
     * @param wFigure_Contact3
     *            使用桁_連絡先3
     */
    public void setFigure_Contact3(String wFigure_Contact3)
    {
        this.wFigure_Contact3 = wFigure_Contact3;
    }

    /**
     * 使用桁_連絡先3を取得します。
     * 
     * @return 使用桁_連絡先3
     */
    public String getFigure_Contact3()
    {
        return wFigure_Contact3;
    }

    /**
     * 使用桁_連絡先2を設定します。
     * 
     * @param wFigure_Contact2
     *            使用桁_連絡先2
     */
    public void setFigure_Contact2(String wFigure_Contact2)
    {
        this.wFigure_Contact2 = wFigure_Contact2;
    }

    /**
     * 使用桁_連絡先2を取得します。
     * 
     * @return 使用桁_連絡先2
     */
    public String getFigure_Contact2()
    {
        return wFigure_Contact2;
    }

    /**
     * 有効_ケースITFを設定します。
     * 
     * @param wValid_ITF
     *            有効_ケースITF
     */
    public void setValid_ITF(boolean wValid_ITF)
    {
        this.wValid_ITF = wValid_ITF;
    }

    /**
     * 有効_ケースITFを取得します。
     * 
     * @return 有効_ケースITF
     */
    public boolean getValid_ITF()
    {
        return wValid_ITF;
    }

    /**
     * 使用桁_上限在庫数を設定します。
     * 
     * @param wFigure_UpperQty
     *            使用桁_上限在庫数
     */
    public void setFigure_UpperQty(String wFigure_UpperQty)
    {
        this.wFigure_UpperQty = wFigure_UpperQty;
    }

    /**
     * 使用桁_上限在庫数を取得します。
     * 
     * @return 使用桁_上限在庫数
     */
    public String getFigure_UpperQty()
    {
        return wFigure_UpperQty;
    }

    /**
     * 使用桁_連絡先1を設定します。
     * 
     * @param wFigure_Contact1
     *            使用桁_連絡先1
     */
    public void setFigure_Contact1(String wFigure_Contact1)
    {
        this.wFigure_Contact1 = wFigure_Contact1;
    }

    /**
     * 使用桁_連絡先1を取得します。
     * 
     * @return 使用桁_連絡先1
     */
    public String getFigure_Contact1()
    {
        return wFigure_Contact1;
    }

    /**
     * 使用桁_荷主コードを設定します。
     * 
     * @param wFigure_ConsignorCode
     *            使用桁_荷主コード
     */
    public void setFigure_ConsignorCode(String wFigure_ConsignorCode)
    {
        this.wFigure_ConsignorCode = wFigure_ConsignorCode;
    }

    /**
     * 使用桁_荷主コードを取得します。
     * 
     * @return 使用桁_荷主コード
     */
    public String getFigure_ConsignorCode()
    {
        return wFigure_ConsignorCode;
    }

    /**
     * 最大桁_出荷先名称を設定します。
     * 
     * @param wMaxFigure_CustomerName
     *            最大桁_出荷先名称
     */
    public void setMaxFigure_CustomerName(String wMaxFigure_CustomerName)
    {
        this.wMaxFigure_CustomerName = wMaxFigure_CustomerName;
    }

    /**
     * 最大桁_出荷先名称を取得します。
     * 
     * @return 最大桁_出荷先名称
     */
    public String getMaxFigure_CustomerName()
    {
        return wMaxFigure_CustomerName;
    }

    /**
     * 有効_ボールITFを設定します。
     * 
     * @param wValid_BundleITF
     *            有効_ボールITF
     */
    public void setValid_BundleITF(boolean wValid_BundleITF)
    {
        this.wValid_BundleITF = wValid_BundleITF;
    }

    /**
     * 有効_ボールITFを取得します。
     * 
     * @return 有効_ボールITF
     */
    public boolean getValid_BundleITF()
    {
        return wValid_BundleITF;
    }

}