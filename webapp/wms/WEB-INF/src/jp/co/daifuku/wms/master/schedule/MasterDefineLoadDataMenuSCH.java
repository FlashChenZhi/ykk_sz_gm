
package jp.co.daifuku.wms.master.schedule;
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.system.schedule.AbstractSystemSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;


/**
 * Designer : hota <BR>
 * Maker : hota   <BR>
 * <BR>
 * <CODE>MasterDefineLoadDataMenuSCH</CODE>クラスはマスタデータ取込みの項目設定処理を行うクラスです。<BR>
 * 予定ファイルの各項目の有効無効、使用桁数、位置を設定します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.環境設定処理（<CODE>startSCH(Connection conn, Parameter[] startParams)</CODE>メソッド）<BR>
 * <BR>
 * マスタデータ取込み（データ項目設定）画面の２画面目の登録ボタンにて起動します。<BR>
 * 画面に設定された値（有効無効、使用桁数、位置）を環境設定ファイル（EnvironmentInformation）に<BR>
 * 反映します。<BR>
 * <BR>
 * 3.入力チェック処理（<CODE>check(Connection conn, Parameter checkParam)</CODE>メソッド）<BR>
 * <BR>
 * 作業者コード、パスワード、権限のチェックを行います。<BR>
 * 5.次画面遷移チェック処理で呼び出されます。<BR>
 * <BR>
 * 4.次画面遷移チェック処理（<CODE>nextCheck(Connection conn, Parameter checkParam)</CODE>メソッド）<BR>
 * <BR>
 * 作業者コード、パスワード、権限のチェックを行います。<BR>
 * <BR>
 * 5.2画面目初期表示処理（<CODE>query(Connection conn, Parameter searchParam)</CODE>メソッド）<BR>
 * <BR>
 * １画面目で選択されたパッケージに関連する項目値を環境設定ファイル（EnvironmentInformation）より取得します。<BR>
 * <BR>
 * その他のメソッドはeWareNavi-Basicでは使用していません<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/24</TD><TD>T.Yamashita</TD><TD>新規作成</TD></TR>
 * <TR><TD>2006/06/27</TD><TD>Y.Okamura</TD><TD>商品の項目設定から、JAN、商品分類、上限・下限数を削除</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterDefineLoadDataMenuSCH extends AbstractSystemSCH
{

	// Class fields --------------------------------------------------
	/**
	 * 各項目数
	 */
	private final int ITEM_COUNT = 19;
	
	/** 項目 */
	private final String Chk_Off = "0";

	/** 項目 */
	private final String Chk_On = "1";

	/** 各項目必須有効フラグ */
	private String[] ItemReq = new String[ITEM_COUNT];

	/** 各項目桁数 */
	private int[] ItemFigure = new int[ITEM_COUNT];

	/** 各項目最大桁数 */
	private int[] ItemMaxFigure = new int[ITEM_COUNT];

	/** 各項目位置 */
	private int[] ItemPos = new int[ITEM_COUNT];

	/** 項目荷主マスタ */
	private final int Type_Consignor = 0;

	/** 項目仕入先マスタ */
	private final int Type_Supplier = 1;

	/** 項目出荷先マスタ */
	private final int Type_Customer = 2;

	/** 項目商品マスタ */
	private final int Type_Item = 3;

	/** 環境変数セクション名 **/
	private final String[] Sec_Name =
		{
			"DATALOAD_MASTERCONSIGNOR",
			"DATALOAD_MASTERSUPPLIER",
			"DATALOAD_MASTERCUSTOMER",
			"DATALOAD_MASTERITEM"
			};

	/** 有効フラグセクション名 **/
	private final String Sec_Enable = "_ENABLE";

	/** 桁数セクション名 **/
	private final String Sec_Figure = "_FIGURE";

	/** 最大桁数セクション名 **/
	private final String Sec_Max = "_FIGURE_MAX";

	/** 位置セクション名 **/
	private final String Sec_Pos = "_POSITION";

	/** 環境情報項目名 **/
	private final String[][] Item_Name =
		{
			{
				"CONSIGNOR_CODE",
				"CONSIGNOR_NAME",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				""
			},
			{
                "CONSIGNOR_CODE",
				"",
				"SUPPLIER_CODE",
				"SUPPLIER_NAME",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				""
			},
			{
			    "CONSIGNOR_CODE",
			    "",
			    "",
			    "",
				"CUSTOMER_CODE",
				"CUSTOMER_NAME",
				"POSTAL_CODE",
				"PREFECTURE",
				"ADRESS1",
				"ADRESS2",
				"CONTACT1",
				"CONTACT2",
				"CONTACT3",
				"",
				"",
				"",
				"",
				"",
				""
			},
			{
			    "CONSIGNOR_CODE",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
				"ITEM_CODE",
				"ITEM_NAME",
				"ENTERING_QTY",
				"BUNDLE_ENTERING_QTY",
				"ITF",
				"BUNDLE_ITF"
			}
	};

	/** 必須項目名 **/
	private final String[][] Item_Request =
		{
			{
				"CONSIGNOR_CODE",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				""
			},
			{
                "CONSIGNOR_CODE",
				"",
				"SUPPLIER_CODE",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				""
			},
			{
			    "CONSIGNOR_CODE",
			    "",
			    "",
			    "",
				"CUSTOMER_CODE",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				""
			},
			{
			    "CONSIGNOR_CODE",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
			    "",
				"ITEM_CODE",
				"",
				"",
				"",
				"",
				""
			}
	};

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------
	public MasterDefineLoadDataMenuSCH()
	{
	}

	// Public methods ------------------------------------------------

	/**
	 * 画面へ表示するデータを取得します。表示ボタン押下時などの操作に対応するメソッドです。<BR>
	 * 検索条件は<CODE>Parameter</CODE>クラスを継承したクラスを渡します。<BR>
	 * 表示データは複数レコード存在する場合があるので、結果は配列で返します。<BR>
	 * 該当データが見つからなかった場合、要素数0の配列を返します。<BR>
	 * 入力条件エラーなどで検索処理が失敗した場合、nullを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		// searchParamの型を変換
		SystemParameter param = (SystemParameter) searchParam;
		
		MasterDefineDataParameter[] defParam = new MasterDefineDataParameter[1];

		String select = param.getSelectDefineLoadData();
		EnvironmentinformationLoad(getDataType(select));

		defParam[0] = (MasterDefineDataParameter) setItems();

		// 環境設定ファイル（EnvironmentInformation）より、指定された導入パッケージの項目情報を取得します。
		return defParam;
	}

	/**
	 * パラメータの内容が正しいかチェックを行います。<CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、<BR>
	 * パラメータ入力内容チェック処理を行います。チェック処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
	 * パラメータの内容が正しい場合はtrueを返します。<BR>
	 * パラメータの内容に問題がある場合はfalseを返します。<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		// checkParamの型を変換
		SystemParameter param = (SystemParameter) checkParam;

		// 作業者コード、パスワード、アクセス権限のチェックを行います。
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		return true;
	}
	
	/**
	 * パラメータの内容が正しいかチェックを行います。<CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、<BR>
	 * パラメータ入力内容チェック処理を行います。チェック処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
	 * パラメータの内容が正しい場合はtrueを返します。<BR>
	 * パラメータの内容に問題がある場合はfalseを返します。<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * このメソッドは2画面遷移の1画面目から2画面目への遷移時の入力チェックを実装します。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		// checkを呼び出し
		// チェック処理			
		return check(conn, checkParam);
	}

	/**
	 * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
	 * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
	 * スケジュール処理が成功した場合はtrueを返します。<BR>
	 * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		if (startParams.length == 0)
		{
			// 6004001 = データに不正がありました。
			wMessage = "6004001";
			return false;
		}
		
		// startParamsの型を変換
		MasterDefineDataParameter[] param = (MasterDefineDataParameter[]) startParams;

		// 登録処理
		String select = param[0].getSelectDefineLoadData();
		int dataType = getDataType(select);
		getItems(dataType, param[0]);
		EnvironmentinformationSave(dataType);

		// 6001006 = 設定しました。
		wMessage = "6001006";

		return true;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------


	/**
	 * 環境情報から指定された情報を読み込む。
	 */
	private void EnvironmentinformationLoad(int ReadType) throws ReadWriteException
	{

		// 環境情報操作用インスタンス作成
		IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

		String wFigure = null;
		String wMax = null;
		String wPos = null;

		// 各情報を取得
		for (int i = 0; i < ITEM_COUNT; i++)
		{

			// 使用する項目なら読み込み
			if (!Item_Name[ReadType][i].equals(""))
			{
				ItemReq[i] = IO.get(Sec_Name[ReadType] + Sec_Enable, Item_Name[ReadType][i]);
				wFigure = IO.get(Sec_Name[ReadType] + Sec_Figure, Item_Name[ReadType][i]);
				wMax = IO.get(Sec_Name[ReadType] + Sec_Max, Item_Name[ReadType][i]);
				wPos = IO.get(Sec_Name[ReadType] + Sec_Pos, Item_Name[ReadType][i]);

				if (wFigure != null && !wFigure.trim().equals(""))
				{
					ItemFigure[i] = Integer.parseInt(wFigure);
				}
				if (wMax != null && !wMax.trim().equals(""))
				{
					ItemMaxFigure[i] = Integer.parseInt(wMax);
				}
				if (wPos != null && !wPos.trim().equals(""))
				{
					ItemPos[i] = Integer.parseInt(wPos);
				}
			}
			else
			{
				ItemReq[i] = "";
				ItemFigure[i] = 0;
				ItemMaxFigure[i] = 0;
				ItemPos[i] = 0;
			}
		}
	}

	/**
	 * 環境情報に指定された情報を書き込む。
	 */
	private void EnvironmentinformationSave(int ReadType) throws ReadWriteException
	{

		// 環境情報操作用インスタンス作成
		IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

		// 各情報を取得
		for (int i = 0; i < ITEM_COUNT; i++)
		{

			// 使用する項目なら書き込む
			if (!Item_Name[ReadType][i].equals(""))
			{

				String ireq;
				if (!ItemReq[i].trim().equals(""))
					ireq = ItemReq[i];
				else
					ireq = "0";

				// 必須項目の場合は何も変更しない。
				if (Item_Request[ReadType][i].equals(""))
				{
					// 有効な項目ならセットする
					IO.set(Sec_Name[ReadType] + Sec_Enable, Item_Name[ReadType][i], ireq);
				}
				if (!ireq.equals("0"))
				{
					IO.set(
						Sec_Name[ReadType] + Sec_Figure,
						Item_Name[ReadType][i],
						String.valueOf(ItemFigure[i]));
					IO.set(
						Sec_Name[ReadType] + Sec_Pos,
						Item_Name[ReadType][i],
						String.valueOf(ItemPos[i]));
				}
				else
				{
					IO.set(Sec_Name[ReadType] + Sec_Pos, Item_Name[ReadType][i], "0");
					ItemPos[i] = 0;
				}
			}
		}

		IO.flush();

	}

	/**
	 * 選択されている作業タイプを返します
	 */
	private int getDataType(String select)
	{

		if (select.equals(SystemParameter.SELECTDEFINELOADDATA_CONSIGNOR))
			return Type_Consignor;
		else if (select.equals(SystemParameter.SELECTDEFINELOADDATA_SUPPLIER))
			return Type_Supplier;
		else if (select.equals(SystemParameter.SELECTDEFINELOADDATA_CUSTOMER))
			return Type_Customer;
		else
			return Type_Item;

	}

	/**
	 * 画面に表示するデータをセットし返します
	 */
	private Parameter setItems()
	{

		MasterDefineDataParameter param = new MasterDefineDataParameter();
		// 必須フラグのセット

		// 有効フラグの取得
		param.setValid_ConsignorCode(ItemReq[0].equals(Chk_On));
		param.setValid_ConsignorName(ItemReq[1].equals(Chk_On));
		param.setValid_SupplierCode(ItemReq[2].equals(Chk_On));
		param.setValid_SupplierName(ItemReq[3].equals(Chk_On));
		param.setValid_CustomerCode(ItemReq[4].equals(Chk_On));
		param.setValid_CustomerName(ItemReq[5].equals(Chk_On));
		param.setValid_PostalCode(ItemReq[6].equals(Chk_On));
		param.setValid_Prefecture(ItemReq[7].equals(Chk_On));
		param.setValid_Adress1(ItemReq[8].equals(Chk_On));
		param.setValid_Adress2(ItemReq[9].equals(Chk_On));
		param.setValid_Contact1(ItemReq[10].equals(Chk_On));
		param.setValid_Contact2(ItemReq[11].equals(Chk_On));
		param.setValid_Contact3(ItemReq[12].equals(Chk_On));
		param.setValid_ItemCode(ItemReq[13].equals(Chk_On));
		param.setValid_ItemName(ItemReq[14].equals(Chk_On));
		param.setValid_EnteringQty(ItemReq[15].equals(Chk_On));
		param.setValid_BundleEnteringQty(ItemReq[16].equals(Chk_On));
		param.setValid_ITF(ItemReq[17].equals(Chk_On));
		param.setValid_BundleITF(ItemReq[18].equals(Chk_On));

		// 桁数の取得
		for (int i = 0; i < ITEM_COUNT; i++)
		{
			if (ItemFigure[i] == 0)
			{
				continue;
			}
			switch (i)
			{
				case 0 :
					param.setFigure_ConsignorCode(Integer.toString(ItemFigure[i]));
					break;
				case 1 :
					param.setFigure_ConsignorName(Integer.toString(ItemFigure[i]));
					break;
				case 2 :
					param.setFigure_SupplierCode(Integer.toString(ItemFigure[i]));
					break;
				case 3 :
					param.setFigure_SupplierName(Integer.toString(ItemFigure[i]));
					break;
				case 4 :
					param.setFigure_CustomerCode(Integer.toString(ItemFigure[i]));
					break;
				case 5 :
					param.setFigure_CustomerName(Integer.toString(ItemFigure[i]));
					break;
				case 6 :
					param.setFigure_PostalCode(Integer.toString(ItemFigure[i]));
					break;
				case 7 :
					param.setFigure_Prefecture(Integer.toString(ItemFigure[i]));
					break;
				case 8 :
					param.setFigure_Adress1(Integer.toString(ItemFigure[i]));
					break;
				case 9 :
					param.setFigure_Adress2(Integer.toString(ItemFigure[i]));
					break;
				case 10 :
					param.setFigure_Contact1(Integer.toString(ItemFigure[i]));
					break;
				case 11 :
					param.setFigure_Contact2(Integer.toString(ItemFigure[i]));
					break;
				case 12 :
					param.setFigure_Contact3(Integer.toString(ItemFigure[i]));
					break;
				case 13 :
					param.setFigure_ItemCode(Integer.toString(ItemFigure[i]));
					break;
				case 14 :
					param.setFigure_ItemName(Integer.toString(ItemFigure[i]));
					break;
				case 15 :
					param.setFigure_EnteringQty(Integer.toString(ItemFigure[i]));
					break;
				case 16 :
					param.setFigure_BundleEnteringQty(Integer.toString(ItemFigure[i]));
					break;
				case 17 :
					param.setFigure_ITF(Integer.toString(ItemFigure[i]));
					break;
				case 18 :
					param.setFigure_BundleITF(Integer.toString(ItemFigure[i]));
					break;
			}
		}

		// 最大桁数の取得
		param.setMaxFigure_ConsignorCode(Integer.toString(ItemMaxFigure[0]));
		param.setMaxFigure_ConsignorName(Integer.toString(ItemMaxFigure[1]));
		param.setMaxFigure_SupplierCode(Integer.toString(ItemMaxFigure[2]));
		param.setMaxFigure_SupplierName(Integer.toString(ItemMaxFigure[3]));
		param.setMaxFigure_CustomerCode(Integer.toString(ItemMaxFigure[4]));
		param.setMaxFigure_CustomerName(Integer.toString(ItemMaxFigure[5]));
		param.setMaxFigure_PostalCode(Integer.toString(ItemMaxFigure[6]));
		param.setMaxFigure_Prefecture(Integer.toString(ItemMaxFigure[7]));
		param.setMaxFigure_Adress1(Integer.toString(ItemMaxFigure[8]));
		param.setMaxFigure_Adress2(Integer.toString(ItemMaxFigure[9]));
		param.setMaxFigure_Contact1(Integer.toString(ItemMaxFigure[10]));
		param.setMaxFigure_Contact2(Integer.toString(ItemMaxFigure[11]));
		param.setMaxFigure_Contact3(Integer.toString(ItemMaxFigure[12]));
		param.setMaxFigure_ItemCode(Integer.toString(ItemMaxFigure[13]));
		param.setMaxFigure_ItemName(Integer.toString(ItemMaxFigure[14]));
		param.setMaxFigure_EnteringQty(Integer.toString(ItemMaxFigure[15]));
		param.setMaxFigure_BundleEnteringQty(Integer.toString(ItemMaxFigure[16]));
		param.setMaxFigure_ITF(Integer.toString(ItemMaxFigure[17]));
		param.setMaxFigure_BundleITF(Integer.toString(ItemMaxFigure[18]));

		// 位置の取得
		for (int i = 0; i < ITEM_COUNT; i++)
		{
			if (ItemPos[i] == 0)
			{
				continue;
			}
			switch (i)
			{
				case 0 :
					param.setPosition_ConsignorCode(Integer.toString(ItemPos[0]));
					break;
				case 1 :
					param.setPosition_ConsignorName(Integer.toString(ItemPos[1]));
					break;
				case 2 :
					param.setPosition_SupplierCode(Integer.toString(ItemPos[2]));
					break;
				case 3 :
					param.setPosition_SupplierName(Integer.toString(ItemPos[3]));
					break;
				case 4 :
					param.setPosition_CustomerCode(Integer.toString(ItemPos[4]));
					break;
				case 5 :
					param.setPosition_CustomerName(Integer.toString(ItemPos[5]));
					break;
				case 6 :
					param.setPosition_PostalCode(Integer.toString(ItemPos[6]));
					break;
				case 7 :
					param.setPosition_Prefecture(Integer.toString(ItemPos[7]));
					break;
				case 8 :
					param.setPosition_Adress1(Integer.toString(ItemPos[8]));
					break;
				case 9 :
					param.setPosition_Adress2(Integer.toString(ItemPos[9]));
					break;
				case 10 :
					param.setPosition_Contact1(Integer.toString(ItemPos[10]));
					break;
				case 11 :
					param.setPosition_Contact2(Integer.toString(ItemPos[11]));
					break;
				case 12 :
					param.setPosition_Contact3(Integer.toString(ItemPos[12]));
					break;
				case 13 :
					param.setPosition_ItemCode(Integer.toString(ItemPos[13]));
					break;
				case 14 :
					param.setPosition_ItemName(Integer.toString(ItemPos[14]));
					break;
				case 15 :
					param.setPosition_EnteringQty(Integer.toString(ItemPos[15]));
					break;
				case 16 :
					param.setPosition_BundleEnteringQty(Integer.toString(ItemPos[16]));
					break;
				case 17 :
					param.setPosition_ITF(Integer.toString(ItemPos[17]));
					break;
				case 18 :
					param.setPosition_BundleITF(Integer.toString(ItemPos[18]));
					break;
			}
		}

		return param;

	}

	/**
	 * パラメータの内容を配列へセットするメソッドです。
	 */
	private void getItems(int ReadType, MasterDefineDataParameter param)
	{

		// 各情報を取得
		for (int i = 0; i < ITEM_COUNT; i++)
		{

			// 使用する項目ならセット
			if (!Item_Name[ReadType][i].equals(""))
			{
				ItemFigure[i] = 0;
				ItemPos[i] = 0;

				switch (i)
				{
					case 0 :
						ItemReq[i] = getReq(param.getValid_ConsignorCode());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ConsignorCode());
							ItemPos[i] = Integer.parseInt(param.getPosition_ConsignorCode());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ConsignorCode());
						break;
					case 1 :
						ItemReq[i] = getReq(param.getValid_ConsignorName());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ConsignorName());
							ItemPos[i] = Integer.parseInt(param.getPosition_ConsignorName());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ConsignorName());
						break;
					case 2 :
						ItemReq[i] = getReq(param.getValid_SupplierCode());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_SupplierCode());
							ItemPos[i] = Integer.parseInt(param.getPosition_SupplierCode());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_SupplierCode());
						break;
					case 3 :
						ItemReq[i] = getReq(param.getValid_SupplierName());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_SupplierName());
							ItemPos[i] = Integer.parseInt(param.getPosition_SupplierName());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_SupplierName());
						break;
					case 4 :
						ItemReq[i] = getReq(param.getValid_CustomerCode());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_CustomerCode());
							ItemPos[i] = Integer.parseInt(param.getPosition_CustomerCode());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_CustomerCode());
						break;
					case 5 :
						ItemReq[i] = getReq(param.getValid_CustomerName());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_CustomerName());
							ItemPos[i] = Integer.parseInt(param.getPosition_CustomerName());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_CustomerName());
						break;
					case 6 :
						ItemReq[i] = getReq(param.getValid_PostalCode());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_PostalCode());
							ItemPos[i] = Integer.parseInt(param.getPosition_PostalCode());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_PostalCode());
						break;
					case 7 :
						ItemReq[i] = getReq(param.getValid_Prefecture());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_Prefecture());
							ItemPos[i] = Integer.parseInt(param.getPosition_Prefecture());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_Prefecture());
						break;
					case 8 :
						ItemReq[i] = getReq(param.getValid_Adress1());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_Adress1());
							ItemPos[i] = Integer.parseInt(param.getPosition_Adress1());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_Adress1());
						break;
					case 9 :
						ItemReq[i] = getReq(param.getValid_Adress2());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_Adress2());
							ItemPos[i] = Integer.parseInt(param.getPosition_Adress2());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_Adress2());
						break;
					case 10 :
						ItemReq[i] = getReq(param.getValid_Contact1());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_Contact1());
							ItemPos[i] = Integer.parseInt(param.getPosition_Contact1());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_Contact1());
						break;
					case 11 :
						ItemReq[i] = getReq(param.getValid_Contact2());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_Contact2());
							ItemPos[i] = Integer.parseInt(param.getPosition_Contact2());
						}
						ItemMaxFigure[i] =	Integer.parseInt(param.getMaxFigure_Contact2());
						break;
					case 12 :
						ItemReq[i] = getReq(param.getValid_Contact3());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_Contact3());
							ItemPos[i] = Integer.parseInt(param.getPosition_Contact3());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_Contact3());
						break;
					case 13 :
						ItemReq[i] = getReq(param.getValid_ItemCode());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ItemCode());
							ItemPos[i] = Integer.parseInt(param.getPosition_ItemCode());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ItemCode());
						break;
					case 14 :
						ItemReq[i] = getReq(param.getValid_ItemName());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ItemName());
							ItemPos[i] = Integer.parseInt(param.getPosition_ItemName());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ItemName());
						break;
					case 15 :
						ItemReq[i] = getReq(param.getValid_EnteringQty());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_EnteringQty());
							ItemPos[i] = Integer.parseInt(param.getPosition_EnteringQty());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_EnteringQty());
						break;
					case 16 :
						ItemReq[i] = getReq(param.getValid_BundleEnteringQty());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_BundleEnteringQty());
							ItemPos[i] = Integer.parseInt(param.getPosition_BundleEnteringQty());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_BundleEnteringQty());
						break;
					case 17 :
						ItemReq[i] = getReq(param.getValid_ITF());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_ITF());
							ItemPos[i] = Integer.parseInt(param.getPosition_ITF());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_ITF());
						break;
					case 18 :
						ItemReq[i] = getReq(param.getValid_BundleITF());
						if (!ItemReq[i].equals(Chk_Off))
						{
							ItemFigure[i] = Integer.parseInt(param.getFigure_BundleITF());
							ItemPos[i] = Integer.parseInt(param.getPosition_BundleITF());
						}
						ItemMaxFigure[i] = Integer.parseInt(param.getMaxFigure_BundleITF());
						break;
					default :
						break;
				}

			}
		}

	}

	/**
	 * 必須フラグ、有効フラグから、必須有効フラグを返します
	 * @param		EnableFlg	有効フラグ (true,false)
	 * @return				必須有効フラグ (0,1,2)
	 */
	private String getReq(boolean EnableFlg)
	{
		String ret;
		if (EnableFlg == false)
		{
			ret = "0";
		}
		else if (EnableFlg == true)
		{
			ret = "1";
		}
		else
		{
			ret = "2";
		}

		return ret;

	}
}
//end of class
