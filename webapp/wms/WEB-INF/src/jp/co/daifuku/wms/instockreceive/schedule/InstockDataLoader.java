package jp.co.daifuku.wms.instockreceive.schedule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalDataLoader;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.CsvIllegalDataException;
import jp.co.daifuku.wms.base.utility.DataLoadCsvReader;
import jp.co.daifuku.wms.base.utility.DataLoadStatusCsvFileWriter;

// $Id: InstockDataLoader.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita <BR>
 * <BR>
 * <CODE>InstockDataLoader</CODE>クラスは、入荷データ取込み処理を行うクラスです。<BR>
 * <CODE>AbstractExternalDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * このクラスが持つメソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、<BR>
 * トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.データ取込み処理（<CODE>load(Connection conn, WareNaviSystem wns, Parameter searchParam)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   ConnectionオブジェクトとWareNaviSystemオブジェクト、Parameterオブジェクトをパラメータとして受け取り、HOST入荷予定ファイル(CSVファイル)から<BR>
 *   データベースにデータを登録します。(入荷予定情報、作業情報、在庫情報) <BR>
 *   メソッド内部でエラーが発生した場合はFalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   処理の開始時に作業情報、在庫情報を排他処理のためロックします。<BR>
 * <BR>
 *   ＜パラメータ＞<BR>
 *     Connectionオブジェクト <BR>
 *     WareNaviSystemオブジェクト <BR>
 *     SystemParameterオブジェクト <BR>
 * <BR>
 *   ＜処理詳細＞ <BR>
 *      1.取込履歴ファイル作成<BR>
 * <DIR>
 *        ファイル名は日付(YYYYMMDD)＋時刻（HHMISS）＋取込ファイル名となる<BR>
 * </DIR>
 *      2.データ件数初期化<BR>
 * <DIR>
 *        全読み取りデータ件数、登録データ件数、更新データ件数、スキップデータ件数<BR>
 *        登録フラグ False、上書きフラグ False、スキップフラグ False<BR>
 * </DIR>
 *      3.HOST入荷予定ファイルの読み込み<BR>
 * <DIR>
 *        環境ファイル（EnvironmentInformation）に定義された予定ファイルからデータを読み込みます。<BR>
 *        HOST入荷予定ファイルから画面で入力された予定日の行を取得します。<BR>
 * </DIR>
 *      4.読み取りデータの整合性を確認<BR>
 * <DIR>
 *        日付項目          ･･･ 桁数チェック、禁止文字チェック、日付変換チェック<BR>
 *        ASCII文字列項目   ･･･ 桁数チェック、ASCIIコードチェック,禁止文字チェック<BR>
 *        文字列項目        ･･･ 桁数チェック、禁止文字チェック<BR>
 *        数値項目          ･･･ 桁数チェック、日付変換チェック、マイナス値チェック<BR>
 *        異常が発生した場合は、取込みエラーファイルにエラー内容を追記、処理を終了しFalseを返します<BR>
 * </DIR>
 *      5.読み取りデータの仕様チェック<BR>
 * <BR>
 * <DIR>
 *        作業予定数に０より大きい数値がセットされているか<BR>
 *        TCDC区分チェック（0:DC品、1:クロスTC品，2:TC品）以外はエラー<BR>
 *        TC区分2：TC品の必須CHECK<BR>
 * <DIR>
 *             TC区分がTC品の場合は、出荷先コード、出荷先名が未入力の場合はエラー <BR>
 * </DIR>
 *      6.既存データ有無チェック<BR>
 * <DIR>
 *        作業情報を検索し、同一集約キーとなるデータが存在するかをチェックします。<BR>
 * 		  集約キー(予定日、荷主コード、仕入先コード、伝票No.、行No.)
 *        集約キーは、入荷予定日＋荷主コード＋仕入先コード＋入荷伝票No.＋入荷伝票行<BR>
 *        既存データの状態が未開始・削除以外の場合はスキップし、次のデータを読み込みます。(スキップ数カウントアップ)<BR>
 * <DIR>
 *        <CODE>InstockPlanOperator</CODE>オブジェクトの<CODE>findInstockPlanForUpdate</CODE>メソッドで実施します。
 * </DIR>
 *        データが存在した場合には、上書きフラグをTrueとします<BR>
 * </DIR>
 *      7.既存データ状態削除処理<BR>
 * <DIR>
 *        6.で見つかった既存データの状態が未開始の場合は状態を削除に変更します。
 *        更新対象テーブルは作業情報、入荷予定情報、在庫情報です。
 *        作業情報、入荷予定情報は状態を削除に変更、在庫情報は完了に変更します。
 *        更新順番は、作業情報->入荷予定情報->在庫情報となります。
 *        テーブルをロックするのは作業情報のみとなります。
 *        入荷予定一意キーを条件に次作業情報を検索し、存在する場合、NULL値をSETします。<BR>
 * <DIR>
 *        <CODE>InstockPlanOperator</CODE>オブジェクトの<CODE>updateInstockPlan</CODE>メソッドで実施します。
 * </DIR>
 * </DIR>
 *      8.登録処理を行う（入荷予定情報、作業情報、在庫情報、次作業情報）<BR>
 * <DIR>
 *        入荷予定情報、作業情報、在庫情報の関係は1：1：1となる。<BR>
 *        正常に登録した場合は、取込みエラーファイルに正常データとして追記します<BR>
 *        次作業情報はクロスドックパッケージありでTC区分がクロスTC,TCの場合のみ処理します。
 * <DIR>
 *        <CODE>InstockPlanOperator</CODE>オブジェクトの<CODE>createInstockPlan</CODE>メソッドで実施します。
 * </DIR>
 *        登録データがある場合に登録フラグをTrueとします<BR>
 * </DIR>
 *      9.メッセージのセット<BR>
 * <DIR>
 *        登録フラグがTrueで上書きフラグがTrue、スキップフラグがTrueの場合は<BR>
 *        「一部のデータをスキップし、データの取込は終了しました（２重登録あり）」<BR>
 *        登録フラグがTrueで上書きフラグがTrueの場合は<BR>
 *        「データの取込みは正常に終了しました（２重登録あり）」<BR>
 *        登録フラグがTrueでスキップフラグがTrueの場合は<BR>
 *        「一部スキップで終了しました。」<BR>
 *        登録フラグがTrue場合は<BR>
 *        「データの取込みは正常に終了しました。」<BR>
 *        登録フラグがFalseでスキップフラグがTrueの場合は<BR>
 *        「 スキップの結果、対象データはありませんでした。」<BR>
 *        登録フラグがFalseの場合は<BR>
 *        「 対象データはありませんでした。」<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/02</TD><TD>T.Yamashita</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class InstockDataLoader extends AbstractExternalDataLoader
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	private final String pName = "InstockDataLoader";

	// LOCAL変数宣言
	/**
	 * HOSTデータ（予定日）<BR>
	 */
	protected String externalPlanDate = "";
	/**
	 * HOSTデータ（発注日）<BR>
	 */
	protected String externalOrderingDate = "";
	/**
	 * HOSTデータ（荷主コード）<BR>
	 */
	protected String externalConsignorCode = "";
	/**
	 * HOSTデータ（荷主名称）<BR>
	 */
	protected String externalConsignorName = "";
	/**
	 * HOSTデータ（仕入先コード）<BR>
	 */
	protected String externalSupplierCode = "";
	/**
	 * HOSTデータ（仕入先名称）<BR>
	 */
	protected String externalSupplierName = "";
	/**
	 * HOSTデータ（入荷伝票No.）<BR>
	 */
	protected String externalInstockTicketNo = "";
	/**
	 * HOSTデータ（商品コード）<BR>
	 */
	protected String externalItemCode = "";
	/**
	 * HOSTデータ（ボールITF）<BR>
	 */
	protected String externalBundleITF = "";
	/**
	 * HOSTデータ（ITF）<BR>
	 */
	protected String externalITF = "";
	/**
	 * HOSTデータ（商品名称）<BR>
	 */
	protected String externalItemName = "";
	/**
	 * HOSTデータ（TC区分）<BR>
	 */
	protected String externalTcdcFlag = "";
	/**
	 * HOSTデータ（出荷先コード）<BR>
	 */
	protected String externalCustomerCode = "";
	/**
	 * HOSTデータ（出荷先名称）<BR>
	 */
	protected String externalCustomerName = "";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * ConnectionオブジェクトとWareNaviSystemオブジェクト、SystemParameterオブジェクトをパラメータとして受け取り、入荷予定データを取込みます。
	 * HOST入荷予定ファイル(CSVファイル)から、データベースにデータを登録します。(入荷予定情報、作業情報、在庫情報) <BR><BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param wns  WareNaviSystemオブジェクト
	 * @param searchParam <CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 登録が成功した場合trueを返します。失敗した場合、wMessageにメッセージ番号をセットし、falseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean load(Connection conn, WareNaviSystem wns, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		// パラメータより作業予定日を取得
		SequenceHandler SQ = new SequenceHandler(conn);
		SystemParameter param = (SystemParameter) searchParam;
		String PlanDate = param.getPlanDate();
		String batch_seqno = "";
		String wFileName = "";

		// 件数初期化(全読み取りデータ件数、登録データ件数、更新データ件数、スキップデータ件数)
		InitItemCount();

		// DataLoadCsvReaderインスタンス作成
		// 取込履歴ファイル作成はDataLoadCsvReaderクラスで行う
		DataLoadCsvReader DRR = null;

		// 取込みエラーファイル作成クラスインスタンス作成
		DataLoadStatusCsvFileWriter errlist = new DataLoadStatusCsvFileWriter();

		try
		{
			// InstockPlanOperatorインスタンス生成
			InstockPlanOperator instockoperator = new InstockPlanOperator(conn);

			// 作業区分：入荷の作業情報、在庫情報をlock
			instockoperator.lockWorkingInfoStockData();

			// 入荷予定ファイル名を取得
			wFileName = GetInstockFile();

			// CSVファイルを読み込みクラス設定
			DRR =
				new DataLoadCsvReader(
					DataLoadCsvReader.LOADTYPE_INSTOCKRECEIVE,
					PlanDate,
					"PLAN_DATE");

			// ファイルサイズチェック
			if (DRR.getFileSize() <= 0)
			{
				setMessage("6023289" + wDelim + wFileName);
				return false;
			}

			// バッチNo.取得
			if (batch_seqno.equals(""))
                batch_seqno = SQ.nextInstockPlanBatchNo();

			boolean update_flag = false;
			boolean record_check_flag = true;
			InstockPlan instockplan = null;
			InstockReceiveParameter parameter = null;

			// 予定ファイルから対応する予定日の次の１行を取得
			// while (対応する予定日の次の１行を取得){
			// 取得できなくなるまでLoopします
			while (DRR.MoveNext())
			{
				// 全読み取りデータ件数をカウントアップ
				wAllItemCount++;

				update_flag = false;
				parameter = new InstockReceiveParameter();
				instockplan = new InstockPlan();
				// エラー用ファイル名称の取得
				if (errlist.getFileName().equals(""))
				{
					errlist.setFileName(DRR.getFileName());
				}
				// InstockReceiveParameterに値をSET
				parameter = setInstockReceiveParameter(DRR, param, batch_seqno);

				// チェック
				// 項目チェックは全件行う
				if (!check(parameter, wns, DRR, errlist))
				{
					record_check_flag = false;
					continue;
				}
				if (!record_check_flag)
					continue;

				// 既存データチェック
				instockplan = instockoperator.findInstockPlan(parameter);
				if (instockplan != null)
				{
					if (instockplan.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART))
					{
						instockoperator.updateInstockPlan(instockplan.getInstockPlanUkey(), pName);
						update_flag = true;
						setOverWriteFlag(true);
					}
					else
					{
						// SKIPのログ
						// 6022003=２重取り込みのためスキップしました。ファイル:{0} 行:{1}
						String[] msg = { DRR.getFileName(), DRR.getLineNo()};
						RmiMsgLogClient.write(6022003, pName, msg);
						setSkipFlag(true);
						continue;
					}
				}
				instockoperator.createInstockPlan(parameter);
				setRegistFlag(true);
				if (update_flag == true)
				{
					//6022002=２重取り込みしました。ファイル:{0} 行:{1}
					String[] msg = { DRR.getFileName(), DRR.getLineNo()};
					RmiMsgLogClient.write(6022002, pName, msg);
					// 更新データ件数カウントアップ
					wUpdateItemCount++;
				}
				else
				{
					// 登録データ件数カウントアップ
					wInsertItemCount++;
				}
				// 取込みエラーファイル作成クラスに正常データも追記する。
				errlist.addStatusCsvFile(
					DataLoadStatusCsvFileWriter.STATUS_NORMAL,
					DRR.getLineData(),
					getMessage());
			}
			
			if (!record_check_flag)
			{
				// 6023326=異常なデータがあったため、処理を中止しました。ログを参照してください。
				setMessage("6023326");
				return false;
			}
			return true;
		}
		catch (FileNotFoundException e)
		{
			// ファイルが見つかりませんでした。{0}
			RmiMsgLogClient.write(new TraceHandler(6003009, e), pName);
			setMessage("6003009" + wDelim + wFileName);
			return false;
		}
		catch (IOException e)
		{
			//6006020 = ファイル入出力エラー
			RmiMsgLogClient.write(new TraceHandler(6006020, e), pName);
			throw new ScheduleException();
		}
		catch (ClassNotFoundException e)
		{
			throw new ScheduleException();
		}
		catch (IllegalAccessException e)
		{
			throw new ScheduleException();
		}
		catch (InstantiationException e)
		{
			throw new ScheduleException();
		}
		catch (CsvIllegalDataException e)
		{
			// 6023326 = 取り込み予定情報に異常なデータがあったため、処理を中止しました。
			setMessage("6023326");
			
			// 6026085 = 取り込み情報に異常なデータがありました。詳細:{0}
			Object[] msg = new Object[1]; 
			msg[0] = e.getMessage() ;
			RmiMsgLogClient.write(6026085, pName, msg);
			return false;
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException();
		}
		catch (InvalidDefineException e)
		{
			// 6023326 = 取り込み予定情報に異常なデータがあったため、処理を中止しました。
			setMessage("6023326");
			// 6026006=データフォーマットが不正なデータがありました。ファイル:{0} 行:{1}
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			RmiMsgLogClient.write(6026006, pName, msg);
			return false;
		}
		finally
		{
			// １件でも対象データがあった場合はエラーファイルに書き込む
			if (!errlist.getFileName().equals(""))
			{
				errlist.writeStatusCsvFile();
			}
		}
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入荷予定ファイルのパスとファイル名を取得します。
	 * 
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return 入荷予定ファイル名（パス＋ファイル名）
	 */
	protected String GetInstockFile() throws ScheduleException
	{
		try
		{
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			String DataLoadPath = IO.get("DATALOAD_FOLDER", "INSTOCK_RECEIVE");
			String DataLoadFile = IO.get("DATALOAD_FILENAME", "INSTOCK_RECEIVE");
			return DataLoadPath + DataLoadFile;
		}
		catch (ReadWriteException e)
		{
			//  環境情報ファイルが見つかりませんでした。{0}
			Object[] msg = new Object[1];
			msg[0] = WmsParam.ENVIRONMENT;
			RmiMsgLogClient.write(new TraceHandler(6026004, e), pName, msg);
			throw new ScheduleException();
		}
	}
	
	/**
	 * InstockReceiveParameterクラスのインスタンスを生成します。
	 * 
	 * @param DRR DataLoadCsvReaderオブジェクトを指定します。
	 * @param param SystemParameterオブジェクトを指定します。
	 * @param batno バッチNo.を指定します。
	 * @return InstockReceiveParameterクラスのインスタンス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws CsvIllegalDataException データ取り込み項目定義ファイルの読み込みが正しく行えなかった場合に通知されます。
	 * @throws InvalidStatusException
	 */
	protected InstockReceiveParameter setInstockReceiveParameter(
		DataLoadCsvReader DRR,
		SystemParameter param,
		String batno)
		throws ReadWriteException, CsvIllegalDataException, InvalidStatusException
	{
		InstockReceiveParameter parameter = new InstockReceiveParameter();

		// 速度UPのためDRR.get??Valueの使用を１回にするため、String変数に一旦格納する。
		InitString();

		// 入荷予定日
		if (DRR.getEnable("PLAN_DATE"))
		{
			externalPlanDate = DRR.getDateValue("PLAN_DATE");
			if (!externalPlanDate.equals(""))
			{
				parameter.setPlanDate(externalPlanDate);
			}
		}
		// 発注日
		if (DRR.getEnable("ORDERING_DATE"))
		{
			externalOrderingDate = DRR.getDateValue("ORDERING_DATE");
			if (!externalOrderingDate.equals(""))
			{
				parameter.setOrderingDate(externalOrderingDate);
			}
		}
		// 荷主コード
		if (DRR.getEnable("CONSIGNOR_CODE"))
		{
			externalConsignorCode = DRR.getAsciiValue("CONSIGNOR_CODE");
			if (!externalConsignorCode.equals(""))
				parameter.setConsignorCode(externalConsignorCode);
			else
				parameter.setConsignorCode("0");
		}
		else
		{
			parameter.setConsignorCode("0");
		}
		// 荷主名称
		if (DRR.getEnable("CONSIGNOR_NAME"))
		{
			externalConsignorName = DRR.getValue("CONSIGNOR_NAME");
			if (!externalConsignorName.equals(""))
			{
				parameter.setConsignorName(externalConsignorName);
			}
		}
		// 仕入先コード
		if (DRR.getEnable("SUPPLIER_CODE"))
		{
			externalSupplierCode = DRR.getAsciiValue("SUPPLIER_CODE");
			if (!externalSupplierCode.equals(""))
			{
				parameter.setSupplierCode(externalSupplierCode);
			}
		}
		// 仕入先名称
		if (DRR.getEnable("SUPPLIER_NAME1"))
		{
			externalSupplierName = DRR.getValue("SUPPLIER_NAME1");
			if (!externalSupplierName.equals(""))
			{
				parameter.setSupplierName(externalSupplierName);
			}
		}
		// 伝票No.
		if (DRR.getEnable("TICKET_NO"))
		{
			externalInstockTicketNo = DRR.getAsciiValue("TICKET_NO");
			if (!externalInstockTicketNo.equals(""))
			{
				parameter.setInstockTicketNo(externalInstockTicketNo);
			}
		}
		// 伝票行No.
		if (DRR.getEnable("LINE_NO"))
		{
			parameter.setInstockLineNo(DRR.getIntValue("LINE_NO"));
		}
		// 商品コード
		if (DRR.getEnable("ITEM_CODE"))
		{
			externalItemCode = DRR.getAsciiValue("ITEM_CODE");
			if (!externalItemCode.equals(""))
			{
				parameter.setItemCode(externalItemCode);
			}
		}
		// ボールITF
		if (DRR.getEnable("BUNDLE_ITF"))
		{
			externalBundleITF = DRR.getAsciiValue("BUNDLE_ITF");
			if (!externalBundleITF.equals(""))
			{
				parameter.setBundleITF(externalBundleITF);
			}
		}
		// ケースITF
		if (DRR.getEnable("ITF"))
		{
			externalITF = DRR.getAsciiValue("ITF");
			if (!externalITF.equals(""))
			{
				parameter.setITF(externalITF);
			}
		}
		// ボール入数
		if (DRR.getEnable("BUNDLE_ENTERING_QTY"))
		{
			parameter.setBundleEnteringQty(DRR.getIntValue("BUNDLE_ENTERING_QTY"));
		}
		// ケース入数
		if (DRR.getEnable("ENTERING_QTY"))
		{
			parameter.setEnteringQty(DRR.getIntValue("ENTERING_QTY"));
		}
		// 商品名称
		if (DRR.getEnable("ITEM_NAME1"))
		{
			externalItemName = DRR.getValue("ITEM_NAME1");
			if (!externalItemName.equals(""))
			{
				parameter.setItemName(externalItemName);
			}
		}
		// 入庫予定数
		if (DRR.getEnable("PLAN_QTY"))
		{
			parameter.setTotalPlanQty(DRR.getIntValue("PLAN_QTY"));
		}
		// TC区分
		if (DRR.getEnable("TCDC_FLAG"))
		{
			externalTcdcFlag = DRR.getAsciiValue("TCDC_FLAG");
			if (!externalTcdcFlag.equals(""))
			{
				parameter.setTcdcFlag(externalTcdcFlag);
			}
		}
		// 出荷先コード
		if (DRR.getEnable("CUSTOMER_CODE"))
		{
			externalCustomerCode = DRR.getAsciiValue("CUSTOMER_CODE");
			if (!externalCustomerCode.equals(""))
			{
				parameter.setCustomerCode(externalCustomerCode);
			}
		}
		// 出荷先名称
		if (DRR.getEnable("CUSTOMER_NAME1"))
		{
			externalCustomerName = DRR.getValue("CUSTOMER_NAME1");
			if (!externalCustomerName.equals(""))
			{
				parameter.setCustomerName(externalCustomerName);
			}
		}
		// バッチNo.
		parameter.setBatchNo(batno);
		// 登録区分
		parameter.setRegistKbn(InstockPlan.REGIST_KIND_HOST);
		// 端末No.
		parameter.setTerminalNumber(param.getTerminalNumber());
		// 作業者コード
		parameter.setWorkerCode(param.getWorkerCode());
		// 作業者名
		parameter.setWorkerName(param.getWorkerName());
		// 登録処理名
		parameter.setRegistPName(pName);
		// 最終更新処理名
		parameter.setLastUpdatePName(pName);

		return parameter;
	}
	/**
	 * 指定されたデータ内の項目チェック処理を行います。
	 * エラーメッセージのセット、取込みエラーファイルへの書き込みは呼び出し側で行います。<BR>
	 * (このメソッドのthrows節は、サブクラス化した際に、オーバーライドしたメソッド内で発生が予想される為、消さないでください。必要です。)
	 * @param parameter InstockReceiveParameterオブジェクトを指定します。
	 * @param wns  WareNaviSystemオブジェクトを指定します。
	 * @param DRR DataLoadCsvReaderオブジェクトを指定します。
	 * @param errlist DataLoadStatusCsvFileWriterオブジェクトを指定します。
	 * @return 正常はtrue, 異常発生時はfalseを返します。
	 * @throws ScheduleException 
	 * @throws ReadWriteException 
	 */
	protected boolean check(
		InstockReceiveParameter parameter,
		WareNaviSystem wns,
		DataLoadCsvReader DRR,
		DataLoadStatusCsvFileWriter errlist)
		throws ScheduleException, ReadWriteException
	{
		boolean checkFlag = true;
		int ErrorCode = 0;
		String Key = "";
		String Value = "";

		// 読み取ったデータの予定数が0以下はエラー
		if (parameter.getTotalPlanQty() <= 0)
		{
			// 6022005 = 予定数が0以下のデータがありました。ファイル:{0} 行:{1}
			ErrorCode = 6022005;
			// 処理の異常をログへ記録
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			setMessage(ErrorCode + wDelim + DRR.getLineNo());
			RmiMsgLogClient.write(ErrorCode, pName, msg);
			// 取込みエラーファイル作成クラスにエラー内容を追記する。
			errlist.addStatusCsvFile(
				DataLoadStatusCsvFileWriter.STATUS_ERROR,
				DRR.getLineData(),
				getMessage());
			return false;
		}
		// TCDC区分チェック（0:DC品、1:クロスTC品，2:TC品）以外はエラー
		if (!parameter.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_DC)
			&& !parameter.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_CROSSTC)
			&& !parameter.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_TC))
		{
			// 6023326 = 取り込み予定情報に異常なデータがあったため、処理を中止しました。
			ErrorCode = 6023326;
			Key = "TCDC_FLAG";
			Value = parameter.getTcdcFlag();
			checkFlag = false;
		}
		// TC区分１：クロスTC品の場合はクロスドック運用がありになっている事
		if (parameter.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_CROSSTC)
			&& !wns.getCrossdockOperation().equals(WareNaviSystem.OPERATION_FLAG_ADDON))
		{
			// 6023326 = 取り込み予定情報に異常なデータがあったため、処理を中止しました。
			ErrorCode = 6023326;
			Key = "TCDC_FLAG";
			Value = parameter.getTcdcFlag();
			checkFlag = false;
		}

		// TC区分２：TC品の場合はTC運用がありになっている事
		if (parameter.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_TC)
			&& !wns.getTcOperation().equals(WareNaviSystem.OPERATION_FLAG_ADDON))
		{
			// 6023326 = 取り込み予定情報に異常なデータがあったため、処理を中止しました。
			ErrorCode = 6023326;
			Key = "TCDC_FLAG";
			Value = parameter.getTcdcFlag();
			checkFlag = false;
		}

		// TC区分２：TC品の必須CHECK
		// TC区分がTCの場合は、出荷先コード
		// が未入力の場合はエラー
		if (parameter.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_TC))
		{
			if (parameter.getCustomerCode() == null || parameter.getCustomerCode().equals(""))
			{
				// 6023372=必須項目に値がないデータがあったため、処理を中止しました。
				ErrorCode = 6023372;
				Key = "CUSTOMER_CODE";
				Value = parameter.getCustomerCode();
				checkFlag = false;
			}
		}

		if (!checkFlag)
		{
			// 処理の異常をログへ記録
			String[] msg = { DRR.getFileName(), DRR.getLineNo(), Key, Value };
			setMessage(ErrorCode + wDelim + DRR.getLineNo());
			// 6026005=取り込み予定情報に異常なデータがありました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
			RmiMsgLogClient.write(6026005, pName, msg);
			// 取込みエラーファイル作成クラスにエラー内容を追記する。
			errlist.addStatusCsvFile(
				DataLoadStatusCsvFileWriter.STATUS_ERROR,
				DRR.getLineData(),
				getMessage());
		}

		return checkFlag;
	}
	/**
	 * 各String項目の初期化を行います。
	 */
	protected void InitString()
	{
		externalPlanDate = "";
		externalOrderingDate = "";
		externalConsignorCode = "";
		externalConsignorName = "";
		externalSupplierCode = "";
		externalSupplierName = "";
		externalInstockTicketNo = "";
		externalItemCode = "";
		externalBundleITF = "";
		externalITF = "";
		externalItemName = "";
		externalTcdcFlag = "";
		externalCustomerCode = "";
		externalCustomerName = "";
	}

	// Private methods -----------------------------------------------
}
//end of class
