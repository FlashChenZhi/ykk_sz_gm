//$Id: MasterLoadDataEnvironmentSCH.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
package jp.co.daifuku.wms.master.schedule;
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;
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
 *
 * <CODE>MasterLoadDataEnvironmentSCH</CODE>クラスはマスタデータ取込みの環境設定処理を行うクラスです。<BR>
 * 予定ファイル格納フォルダ、及び予定ファイル名を設定します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.環境設定処理（<CODE>startSCH(Connection conn, Parameter[] startParams)</CODE>メソッド）<BR>
 * <BR>
 *   [パラメータ] *必須入力 +チェックボックスON(TRUE)時、必須入力<BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *     荷主マスタデータ <BR>
 *     <DIR>
 *       荷主マスタチェックボックス* <BR>
 *       荷主マスタ用フォルダー名+ <BR>
 *       荷主マスタ用ファイル名+ <BR>
 *     </DIR>
 *     仕入先マスタデータ <BR>
 *     <DIR>
 *       仕入先マスタチェックボックス* <BR>
 *       仕入先マスタデータ用フォルダー名+ <BR>
 *       仕入先マスタデータ用ファイル名+ <BR>
 *     </DIR>
 *     出荷先マスタデータ <BR>
 *     <DIR>
 *       出荷先マスタチェックボックス* <BR>
 *       出荷先マスタデータ用フォルダー名+ <BR>
 *       出荷先マスタデータ用ファイル名+ <BR>
 *     </DIR>
 *     商品マスタデータ <BR>
 *     <DIR>
 *       商品マスタチェックボックス* <BR>
 *       商品マスタデータ用フォルダー名+ <BR>
 *       商品マスタデータ用ファイル名+ <BR>
 *     </DIR>
 *   </DIR>
 *   [返却データ] <BR>
 *   <DIR>
 *     結果通知 <BR>
 *   </DIR>
 *   [更新処理] <BR>
 *   <DIR>
 *     1-1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR>
 *     <DIR>
 *       環境定義の変更が許可されている作業者であること。(<CODE>check()</CODE>メソッド) <BR>
 *     </DIR>
 *     1-2.入力されたフォルダの存在チェックを行います。<BR>
 *     1-3.入力されたフォルダの最後に"\"がない場合は、付加します。<BR>
 *     1-4.環境設定ファイル（EnvironmentInformation）に設定した格納フォルダ、ファイル名を更新します。<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.画面初期表示処理（<CODE>initFind(Connection conn, Parameter searchParam)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   データ取り込み（環境設定）画面のボタン初期表示処理を行います。<BR>
 *   予定データの格納フォルダ、ファイル名を、返却します。SETするデータは環境設定ファイル（EnvironmentInformation）より取得します。 <BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <DIR>
 *     なし <BR>
 *   </DIR>
 *   <BR>
 *   [返却データ]  <BR>
 *   <DIR>
 *     荷主マスタデータ <BR>
 *     <DIR>
 *       荷主マスタデータ用フォルダー名+ <BR>
 *       荷主マスタデータ用ファイル名+ <BR>
 *     </DIR>
 *     仕入先マスタデータ <BR>
 *     <DIR>
 *       仕入先マスタデータ用フォルダー名+ <BR>
 *       仕入先マスタデータ用ファイル名+ <BR>
 *     </DIR>
 *     出荷先マスタデータ <BR>
 *     <DIR>
 *       出荷先マスタデータ用フォルダー名+ <BR>
 *       出荷先マスタデータ用ファイル名+ <BR>
 *     </DIR>
 *     商品マスタデータ <BR>
 *     <DIR>
 *       商品マスタデータ用フォルダー名+ <BR>
 *       商品マスタデータ用ファイル名+ <BR>
 *     </DIR>
 *   </DIR>
 *   <BR>
 *   [情報取得処理] <BR>
 *   2-1.下記条件にて、情報を取得する。 <BR>
 *   <DIR>
 *     [フォルダー・ファイル名取得] <BR> 
 *     <DIR>
 *       環境設定ファイル（EnvironmentInformation）を参照し、データ取り込み用フォルダー名・ファイル名を取得します。 <BR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3.入力チェック処理（<CODE>check(Connection conn, Parameter checkParam)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   作業者コード、パスワード、権限のチェックを行います。（<CODE>check(Connection conn, Parameter checkParam)</CODE>メソッド） <BR>
 * </DIR>
 * <BR>
 * 本処理では、下記Methodは使用しません。 <BR>
 * 呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
 * <DIR>
 *   画面表示(<CODE>query()</CODE>メソッド) <BR> 
 *   スケジュール処理：返却情報あり(<CODE>startSCHgetParams()</CODE>メソッド) <BR> 
 *   次画面へ移行時のチェック処理(<CODE>nextCheck()</CODE>メソッド) <BR> 
 * </DIR>
* <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/24</TD><TD>T.Yamashita</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterLoadDataEnvironmentSCH extends AbstractSystemSCH
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	/**
	 * データ環境設定キー取得用のINDEX値を定義する。 <BR>
	 * データタイプ 荷主マスタ
	 */
	private static final int LOADTYPE_CONSIGNOR = 0;
	/**
	 * データ環境設定キー取得用のINDEX値を定義する。 <BR>
	 * データタイプ 仕入先マスタ
	 */
	private static final int LOADTYPE_SUPPLIER = 1;
	/**
	 * データ環境設定キー取得用のINDEX値を定義する。 <BR>
	 * データタイプ 出荷先マスタ
	 */
	private static final int LOADTYPE_CUSTOMER = 2;
	/**
	 * データ環境設定キー取得用のINDEX値を定義する。 <BR>
	 * データタイプ 商品マスタ
	 */
	private static final int LOADTYPE_ITEM = 3;


	/**
	 * データ環境設定キーをString配列にて、定義する。 <BR> 
	 */
	private static final String[] LOADTYPE_KEY =
		{
			"MASTER_CONSIGNOR",
			"MASTER_SUPPLIER",
			"MASTER_CUSTOMER",
			"MASTER_ITEM"
		};

	/**
	 * データ取り込みフォルダのセクション名を定義する。 <BR>
	 */
	private static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

	/**
	 * データ取り込みファイル名のセクション名を定義する。 <BR>
	 */
	private static final String DATALOAD_FILENAME = "DATALOAD_FILENAME";

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------
	public MasterLoadDataEnvironmentSCH()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 検索条件は<CODE>Parameter</CODE>クラスを継承したクラスを渡します。<BR>
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		SystemParameter param = new SystemParameter();
		// 環境情報ディスクリプタ取得
        IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

        // 環境設定ファイルから格納フォルダ、ファイル名を取得する。
        // 荷主マスタデータ

    	// 格納フォルダーをセット。
    	param.setFolder_LoadMasterConsignorData(
    		IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_CONSIGNOR]));
    	// ファイル名をセット。
    	param.setFileName_LoadMasterConsignorData(
    		IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LOADTYPE_CONSIGNOR]));


        // 仕入先マスタデータ

    	// 格納フォルダーをセット。
    	param.setFolder_LoadMasterSupplierData(
    		IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_SUPPLIER]));
    	// ファイル名をセット。
    	param.setFileName_LoadMasterSupplierData(
    		IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LOADTYPE_SUPPLIER]));


        // 出荷先マスタデータ
    	// 格納フォルダーをセット。
    	param.setFolder_LoadMasterCustomerData(
    		IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_CUSTOMER]));
    	// ファイル名をセット。
    	param.setFileName_LoadMasterCustomerData(
    		IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LOADTYPE_CUSTOMER]));


        // 商品マスタデータ

    	// 格納フォルダーをセット。
    	param.setFolder_LoadMasterItemjData(
    		IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_ITEM]));
    	// ファイル名をセット。
    	param.setFileName_LoadMasterItemData(
    		IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LOADTYPE_ITEM]));

		return param;
	}

	/**
	 * パラメータの内容が正しいかチェックを行います。<CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、<BR>
	 * パラメータ入力内容チェック処理を行います。チェック処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
	 * パラメータの内容が正しい場合はtrueを返します。<BR>
	 * パラメータの内容に問題がある場合はfalseを返します。<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
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
	 * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
	 * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
	 * スケジュール処理が成功した場合はtrueを返します。<BR>
	 * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		boolean resultFlag = false;

		try
		{
			// startParamsの型を変換
			SystemParameter param = (SystemParameter) startParams[0];

			// チェック処理			
			if (!check(conn, param))
			{
				return false;
			}

			// パラメータで指定されている格納フォルダの存在チェック
			// 荷主マスタ

			if (!chkDir(param.getFolder_LoadMasterConsignorData()))
			{
				wMessage = "6023030";
				return false;
			}

			// 仕入先マスタ

			if (!chkDir(param.getFolder_LoadMasterSupplierData()))
			{
				wMessage = "6023030";
				return false;
			}

			// 出荷先マスタ

			if (!chkDir(param.getFolder_LoadMasterCustomerData()))
			{
				wMessage = "6023030";
				return false;
			}

			// 商品マスタ

			if (!chkDir(param.getFolder_LoadMasterItemjData()))
			{
				wMessage = "6023030";
				return false;
			}


			// 環境設定ファイル（EnvironmentInformation）書き込み
			// 格納フォルダの最終に"\"が付いているかを確認し、ない場合に付加する。
			String wSetFolder = "";
			String wFolderSing = "\\";

			// 環境情報ディスクリプタ取得
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

			// 荷主マスタ

			wSetFolder = param.getFolder_LoadMasterConsignorData();
			if (!param
				.getFolder_LoadMasterConsignorData()
				.substring(param.getFolder_LoadMasterConsignorData().length() - 1)
				.equals(wFolderSing))
			{
				wSetFolder += wFolderSing;
			}
			// 格納フォルダを更新する。
			IO.set(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_CONSIGNOR], wSetFolder);
			// ファイル名を更新する。
			IO.set(
				DATALOAD_FILENAME,
				LOADTYPE_KEY[LOADTYPE_CONSIGNOR],
				param.getFileName_LoadMasterConsignorData());


			// 仕入先マスタ
			wSetFolder = param.getFolder_LoadMasterSupplierData();
			if (!param
				.getFolder_LoadMasterSupplierData()
				.substring(param.getFolder_LoadMasterSupplierData().length() - 1)
				.equals(wFolderSing))
			{
				wSetFolder += wFolderSing;
			}
			// 格納フォルダを更新する。
			IO.set(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_SUPPLIER], wSetFolder);
			// ファイル名を更新する。
			IO.set(
				DATALOAD_FILENAME,
				LOADTYPE_KEY[LOADTYPE_SUPPLIER],
				param.getFileName_LoadMasterSupplierData());


			// 出荷先マスタ
			wSetFolder = param.getFolder_LoadMasterCustomerData();
			if (!param
				.getFolder_LoadMasterCustomerData()
				.substring(param.getFolder_LoadMasterCustomerData().length() - 1)
				.equals(wFolderSing))
			{
				wSetFolder += wFolderSing;
			}
			// 格納フォルダを更新する。
			IO.set(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_CUSTOMER], wSetFolder);
			// ファイル名を更新する。
			IO.set(
				DATALOAD_FILENAME,
				LOADTYPE_KEY[LOADTYPE_CUSTOMER],
				param.getFileName_LoadMasterCustomerData());


			// 商品マスタ
			wSetFolder = param.getFolder_LoadMasterItemjData();
			if (!param
				.getFolder_LoadMasterItemjData()
				.substring(param.getFolder_LoadMasterItemjData().length() - 1)
				.equals(wFolderSing))
			{
				wSetFolder += wFolderSing;
			}
			// 格納フォルダを更新する。
			IO.set(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_ITEM], wSetFolder);
			// ファイル名を更新する。
			IO.set(
				DATALOAD_FILENAME,
				LOADTYPE_KEY[LOADTYPE_ITEM],
				param.getFileName_LoadMasterItemData());




			// 環境情報データの書込み。
			IO.flush();

			// 更新しました。			
			wMessage = "6001018";
			// 正常終了を通知します。
			resultFlag = true;
		}
		catch (IOException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		return resultFlag;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 指定されたフォルダが有効かどうかしらべる
	 *
	 * @param cheDirName	処理識別子
	 * @return boolean		有効なら true
	 * @exception java.io.exception
	 */
	private boolean chkDir(String cheDirName) throws java.io.IOException
	{
		// ファイルチェック
		File objFile = new File(cheDirName);
		return (objFile.exists() && objFile.isDirectory());
	}
}
