package jp.co.daifuku.wms.idm.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Stock;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 移動ラックデータを検索、更新などを行うクラスです。<BR>
 * 空棚検索・補充棚検索などで使用します。<BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/13</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class IdmOperate extends Object
{

	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * デリミタ
	 */
	protected String wDelim = MessageResource.DELIM;

	/**
	 * 移動ラックバンクNo レングス
	 */
	private int IDM_BANK_LENGTH = 2;
	private int IDM_BANK_STARTIDX = 0;
	private int IDM_BANK_ENDIDX = 2;

	/**
	 * 移動ラックベイNo レングス
	 */
	private int IDM_BAY_LENGTH = 2;
	private int IDM_BAY_STARTIDX = 3;
	private int IDM_BAY_ENDIDX = 5;

	/**
	 * 移動ラックレベルNo レングス
	 */
	private int IDM_LEVEL_LENGTH = 2;
	private int IDM_LEVEL_STARTIDX = 6;
	private int IDM_LEVEL_ENDIDX = 8;

	/**
	 * 移動ラックロケーション レングス
	 */
	private int IDM_LOCATION_LENGTH = IDM_BANK_LENGTH + IDM_BAY_LENGTH + IDM_LEVEL_LENGTH + 2;

	/**
	 * 移動ラックロケーション 区切り文字
	 */
	private String IDM_LOCATION_DELIM = "-";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスを使用してDBの検索・更新を行わない場合はこのコンストラクタを使用してください。
	 */
	public IdmOperate()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * パラメータの条件にてロケーション情報から移動ラックの空棚情報を検索する条件定義をセットします。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * 必須項目*<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>バンクNo</td><td>：</td><td>pBank</td></tr>
	 *   <tr><td>*</td><td>ベイNo</td><td>：</td><td>pBay</td></tr>
	 *   <tr><td>*</td><td>レベルNo</td><td>：</td><td>pLevel</td></tr>
	 * </table>
	 * </DIR>
	 * <BR>
	 * 以下の順で処理を行います。<BR>
	 * <DIR>
	 *   <U>1.WmsParamより、移動ラックのエリアNoを取得します。</U><BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・エリアNoが移動ラックのエリアである事。 <BR>
	 *     ・対象バンクNoが指定されている場合、バンクNoが一致する事。 <BR>
	 *     ・対象ベイNoが指定されている場合、ベイNoが一致する事。 <BR>
	 *     ・対象レベルNoが指定されている場合、レベルNoが一致する事。 <BR>
	 *     ・状態フラグが空棚である事。 <BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param sKey 検索条件をセットするロケーション情報のSearchKey
	 * @param pBank 取得対象のバンクNo
	 * @param pBay 取得対象のベイNo
	 * @param pLevel 取得対象のレベルNo
	 * @return LocateSearchKey ロケーション情報のSearchKey
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public LocateSearchKey setVacantLocationKey(LocateSearchKey sKey, String pBank, String pBay, String pLevel) throws ReadWriteException
	{
		// WmsParamより、移動ラック用のエリアNoを取得します。
		String w_AreaNo = WmsParam.IDM_AREA_NO;
		
		// 検索条件をセットする
		sKey.KeyClear();
		sKey.setAreaNo(w_AreaNo);
		if (!StringUtil.isBlank(pBank))
		{
			sKey.setBankNo(pBank);
		}
		if (!StringUtil.isBlank(pBay))
		{
			sKey.setBayNo(pBay);
		}
		if (!StringUtil.isBlank(pLevel))
		{
			sKey.setLevelNo(pLevel);
		}
		// 空棚のみ
		sKey.setStatusFlag(Locate.Locate_StatusFlag_EMPTY);

		// ロケーションNoの昇順に取得します。
		sKey.setLocationNoOrder(1, true);

		return sKey;

	}

	/**
	 * パラメータの条件にてロケーション情報から移動ラックの補充棚情報を検索する条件定義をセットします。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * 必須項目*<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>バンクNo</td><td>：</td><td>pBank</td></tr>
	 *   <tr><td>*</td><td>ベイNo</td><td>：</td><td>pBay</td></tr>
	 *   <tr><td>*</td><td>レベルNo</td><td>：</td><td>pLevel</td></tr>
	 * </table>
	 * </DIR>
	 * <BR>
	 * 以下の順で処理を行います。<BR>
	 * <DIR>
	 *   <U>1.WmsParamより、移動ラックのエリアNoを取得します。</U><BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・エリアNoが移動ラックのエリアである事。 <BR>
	 *     ・対象バンクNoが指定されている場合、バンクNoが一致する事。 <BR>
	 *     ・対象ベイNoが指定されている場合、ベイNoが一致する事。 <BR>
	 *     ・対象レベルNoが指定されている場合、レベルNoが一致する事。 <BR>
	 *     ・状態フラグが空棚以外である事。 <BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param sKey 検索条件をセットするロケーション情報のSearchKey
	 * @param pBank 取得対象のバンクNo
	 * @param pBay 取得対象のベイNo
	 * @param pLevel 取得対象のレベルNo
	 * @return StockSearchKey ロケーション情報のSearchKey
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public StockSearchKey setReplenishLocationKey(StockSearchKey sKey, String pBank, String pBay, String pLevel) throws ReadWriteException
	{
		return setReplenishLocationKey(sKey, pBank, pBay, pLevel, null, null, null);
	}


	/**
	 * パラメータの条件にて在庫情報から移動ラックの補充棚情報を検索する条件定義をセットします。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * 必須項目*<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>バンクNo</td><td>：</td><td>pBank</td></tr>
	 *   <tr><td>*</td><td>ベイNo</td><td>：</td><td>pBay</td></tr>
	 *   <tr><td>*</td><td>レベルNo</td><td>：</td><td>pLevel</td></tr>
	 *   <tr><td></td><td>荷主コード</td><td>：</td><td>pConsignorCode</td></tr>
	 *   <tr><td></td><td>商品コード</td><td>：</td><td>pItemCode</td></tr>
	 *   <tr><td></td><td>ケース/ピース区分</td><td>：</td><td>pCasePieceFlag</td></tr>
	 * </table>
	 * </DIR>
	 * <BR>
	 * 以下の順で処理を行います。<BR>
	 * <DIR>
	 *   <U>1.WmsParamより、移動ラックのエリアNoを取得します。</U><BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・エリアNoが移動ラックのエリアである事。 <BR>
	 *     ・対象バンクNoが指定されている場合、バンクNoが一致する事。 <BR>
	 *     ・対象ベイNoが指定されている場合、ベイNoが一致する事。 <BR>
	 *     ・対象レベルNoが指定されている場合、レベルNoが一致する事。 <BR>
	 *     ・対象荷主コードが指定されている場合、荷主コードが一致する事。 <BR>
	 *     ・対象商品コードが指定されている場合、商品コードが一致する事。 <BR>
	 *     ・対象ケースピース区分が指定されている場合、ケースピース区分が一致する事。 <BR>
	 *     ・状態フラグが在庫である事。 <BR>
	 *     ・在庫数が１以上である事。 <BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param sKey 検索条件をセットするロケーション情報のSearchKey
	 * @param pBank 取得対象のバンクNo
	 * @param pBay 取得対象のベイNo
	 * @param pLevel 取得対象のレベルNo
	 * @param pConsignorCode 荷主コード
	 * @param pItemCode 商品コード
	 * @param pCasePieceFlag ケース/ピース区分
	 * @return LocateSearchKey ロケーション情報のSearchKey
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public StockSearchKey setReplenishLocationKey(StockSearchKey sKey, String pBank, String pBay, String pLevel,
						String pConsignorCode, String pItemCode, String pCasePieceFlag) throws ReadWriteException
	{
		// WmsParamより、移動ラック用のエリアNoを取得します。
		String w_AreaNo = WmsParam.IDM_AREA_NO;
		
		// 検索条件をセットする
		sKey.KeyClear();
		sKey.setAreaNo(w_AreaNo);
		String pLocation = "";
		if (!StringUtil.isBlank(pBank))
		{
			pLocation += pBank + '-';
		}
		else
		{
			pLocation += "__-";
		}
		if (!StringUtil.isBlank(pBay))
		{
			pLocation += pBay + '-';
		}
		else
		{
			pLocation += "__-";
		}
		if (!StringUtil.isBlank(pLevel))
		{
			pLocation += pLevel + '-';
		}
		else
		{
			pLocation += "__";
		}
		sKey.setLocationNo(pLocation, "LIKE");
		if (!StringUtil.isBlank(pConsignorCode))
		{
			sKey.setConsignorCode(pConsignorCode);
		}
		if (!StringUtil.isBlank(pItemCode))
		{
			sKey.setItemCode(pItemCode);
		}
		if (!StringUtil.isBlank(pCasePieceFlag))
		{
			sKey.setCasePieceFlag(pCasePieceFlag);
		}
		// 状態フラグが在庫。
		sKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 数量が１以上
		sKey.setStockQty(0, ">");
		
		// ロケーションNoの昇順に取得します。
		sKey.setLocationNoOrder(1, true);
		// ロケーションNoをユニークにて取得します。
		sKey.setLocationNoCollect("DISTINCT");

		return sKey;

	}
	
	/**
	 * バンクNo、ベイNo、レベルNoを移動ラック用ロケーションNo形式に変換します。<BR>
	 * @param pBank  バンクNo
	 * @param pBay   ベイNo
	 * @param pLevel レベルNo
	 * @return String 移動ラックロケーションNo形式 
	 */
	public String importFormatIdmLocation(String pBank, String pBay, String pLevel)
	{
		String w_Bank = "";
		String w_Bay = "";
		String w_Level = "";
		
		// 入力バンクNoを保管します。
		if (!StringUtil.isBlank(pBank))
		{
			if (pBank.length() >= IDM_BANK_LENGTH)
			{
				w_Bank = pBank.substring(0, IDM_BANK_LENGTH);
			}
			else
			{
				w_Bank += pBank;
			}
		}
		for (int lc=(IDM_BANK_LENGTH - w_Bank.length()); lc>0; lc--)
		{
			w_Bank += " ";
		}
		
		// 入力ベイNoを保管します。
		if (!StringUtil.isBlank(pBay))
		{
			if (pBay.length() >= IDM_BAY_LENGTH)
			{
				w_Bay = pBay.substring(0, IDM_BAY_LENGTH);
			}
			else
			{
				w_Bay += pBay;
			}
		}
		for (int lc=(IDM_BAY_LENGTH - w_Bay.length()); lc>0; lc--)
		{
			w_Bay += " ";
		}
		
		// 入力レベルNoを保管します。
		if (!StringUtil.isBlank(pLevel))
		{
			if (pLevel.length() >= IDM_LEVEL_LENGTH)
			{
				w_Level = pLevel.substring(0, IDM_LEVEL_LENGTH);
			}
			else
			{
				w_Level += pLevel;
			}
		}
		for (int lc=(IDM_LEVEL_LENGTH - w_Level.length()); lc>0; lc--)
		{
			w_Level += " ";
		}
				
		return (w_Bank + IDM_LOCATION_DELIM + w_Bay + IDM_LOCATION_DELIM + w_Level);
	}
	
	/**
	 * 移動ラックのロケーション形式か否かのチェックを行います。<BR>
	 * @param pLocationNo チェックを行うロケーションNo
	 * @return String[] バンクNo, ベイNo, レベルNo 
	 */
	public String[] exportFormatIdmLocation(String pLocation)
	{
		// ロケーションNoがNull又はBlank時エラー復帰します。
		if (StringUtil.isBlank(pLocation))		return null;
		
		// ロケーションのレングスが一致しない場合、エラー復帰します。
		if (pLocation.length() != IDM_LOCATION_LENGTH)	return null;
		
		String[] rObj = new String[3];
		rObj[0] = pLocation.substring(IDM_BANK_STARTIDX, IDM_BANK_ENDIDX);
		rObj[1] = pLocation.substring(IDM_BAY_STARTIDX, IDM_BAY_ENDIDX);
		rObj[2] = pLocation.substring(IDM_LEVEL_STARTIDX, IDM_LEVEL_ENDIDX);
		
		return rObj;
	}
	
	/**
	 * バンクNo、ベイNo、レベルNoを検索移動ラック用ロケーションNo形式に変換します。<BR>
	 * @param pBank  バンクNo
	 * @param pBay   ベイNo
	 * @param pLevel レベルNo
	 * @return String 移動ラックロケーションNo形式 
	 */
	public String listSearchFormatIdmLocation(String pBank, String pBay, String pLevel)
	{
		String r_Location = "";
		
		// 入力バンクNoを保管します。
		if (!StringUtil.isBlank(pBank))
		{
			r_Location += pBank;
		}
		else
		{
			return r_Location;
		}
		
		// 入力ベイNoを保管します。
		if (!StringUtil.isBlank(pBay))
		{
			r_Location += IDM_LOCATION_DELIM + pBay;
		}
		else
		{
			return r_Location;
		}
		
		// 入力レベルNoを保管します。
		if (!StringUtil.isBlank(pLevel))
		{
			r_Location += IDM_LOCATION_DELIM + pLevel;
		}

		return r_Location;
	}
	
	/**
	 * 移動ラックのロケーション形式か否かのチェックを行います。<BR>
	 * @param bank チェックを行うバンクNo
	 * @param bay チェックを行うベイNo
	 * @param level チェックを行うレベルNo
	 * @return フォーマットが正しい場合はtrue、正しくない場合はfalse 
	 */
	public boolean checkFormatIdmLocation(String bank, String bay, String level)
	{
		// ロケーションのレングスが一致しない場合、エラー復帰します。
		if (bank.length() != IDM_BANK_LENGTH)
			return false;
						
		if (bay.length() != IDM_BAY_LENGTH)
			return false;
			
		if (level.length() != IDM_LEVEL_LENGTH)
			return false;
			
		// バンク№、ベイ№、レベル№に数値以外が入っていた場合、エラー復帰します。
		try
		{
			Integer.parseInt(bank);
			Integer.parseInt(bay);
			Integer.parseInt(level);
		}
		catch (NumberFormatException e)
		{
			return false;
		}

		return true;
	}

} 
//end of class
