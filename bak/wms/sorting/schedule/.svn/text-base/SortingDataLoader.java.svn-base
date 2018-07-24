package jp.co.daifuku.wms.sorting.schedule;

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
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalDataLoader;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.CsvIllegalDataException;
import jp.co.daifuku.wms.base.utility.DataLoadCsvReader;
import jp.co.daifuku.wms.base.utility.DataLoadStatusCsvFileWriter;

// $Id: SortingDataLoader.java,v 1.1.1.1 2006/08/17 09:34:34 mori Exp $
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * <BR>
 * <CODE>SortingDataLoader</CODE>クラスは、仕分データ取込み処理を行うクラスです。<BR>
 * <CODE>AbstractExternalDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * このクラスが持つメソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、<BR>
 * トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.データ取込み処理（<CODE>load(Connection conn, WareNaviSystem wns, Parameter searchParam)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   ConnectionオブジェクトとWareNaviSystemオブジェクト、Parameterオブジェクトをパラメータとして受け取り、HOST仕分予定ファイル(CSVファイル)から<BR>
 *   データベースにデータを登録します。(仕分予定情報、作業情報、在庫情報) <BR>
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
 *      3.HOST仕分予定ファイルの読み込み<BR>
 * <DIR>
 *        環境ファイル（EnvironmentInformation）に定義された予定ファイルからデータを読み込みます。<BR>
 *        HOST仕分予定ファイルから画面で入力された予定日の行を取得します。<BR>
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
 * <DIR>
 *        作業予定数に０より大きい数値がセットされているか<BR>
 *        TCDC区分チェック（0:DC品、1:クロスTC品）以外はエラー<BR>
 * 		  TC区分1：クロスTC品の場合はクロスドック運用がありになっている事<BR>
 *        TC区分1：クロスTC品の必須CHECK<BR>
 * 		<DIR>
 *      	TC区分がクロスTC品の場合は、仕入先コード、入荷伝票No.、入荷伝票行が未入力の場合はエラー <BR>
 * 		</DIR>
 * </DIR>
 *      6.既存データ有無チェック<BR>
 * <DIR>
 *        作業情報を検索し、同一集約キーとなるデータが存在するかをチェックします。<BR>
 * 		  集約キーは、<BR>
 * 		  ケース品仕分場所入力ありの時<BR>
 * 		  <DIR>
 * 			仕分予定日、荷主コード、出荷先コード、商品コード、ピース品仕分場所、ケース品仕分場所<BR>
 * 		  </DIR>
 * 		  ケース品仕分場所入力なしの時<BR>
 * 		  <DIR>
 * 			仕分予定日、荷主コード、出荷先コード、商品コード、ピース品仕分場所<BR>
 * 		  </DIR>
 *        既存データの状態が未開始・削除以外の場合はスキップし、次のデータを読み込みます。(スキップ数カウントアップ)<BR>
 * <DIR>
 *        <CODE>SortingPlanOperator</CODE>オブジェクトの<CODE>findSortingPlanForUpdate</CODE>メソッドで実施します。
 * </DIR>
 *        データが存在した場合には、上書きフラグをTrueとします<BR>
 * </DIR>
 *      7.既存データ状態削除処理<BR>
 * <DIR>
 *        6.で見つかった既存データの状態が未開始の場合は状態を削除に変更します。<BR>
 *        更新対象テーブルは作業情報、仕分予定情報、在庫情報です。<BR>
 *        作業情報、仕分予定情報は状態を削除に変更、在庫情報は完了に変更します。<BR>
 *        更新順番は、作業情報->仕分予定情報->在庫情報となります。<BR>
 *        テーブルをロックするのは作業情報のみとなります。<BR>
 * <DIR>
 *        <CODE>SortingPlanOperator</CODE>オブジェクトの<CODE>updateSortingPlan</CODE>メソッドで実施します。
 * </DIR>
 * </DIR>
 *      8.登録処理を行う（仕分予定情報、作業情報、在庫情報）<BR>
 * <DIR>
 *        基本的には仕分予定情報、作業情報、在庫情報の関係は1：1：1となるが、<BR>
 *        混在の場合には仕分予定情報、作業情報、在庫情報の関係は1：2：2となる。<BR>
 *        正常に登録した場合は、取込みエラーファイルに正常データとして追記します<BR>
 *        登録データがある場合に登録フラグをTrueとします<BR>
 * <DIR>
 *        <CODE>SortingPlanOperator</CODE>オブジェクトの<CODE>createSortingPlan</CODE>メソッドで実施します。
 * </DIR>
 *        登録データがある場合に登録フラグをTrueとします<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/02</TD><TD>H.Akiyama</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingDataLoader extends AbstractExternalDataLoader
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	protected final String pName = "SortingDataLoader";

	// LOCAL変数宣言
	/**
	 * HOSTデータ（仕分予定日）<BR>
	 */
	protected String externalPlanDate = "";
	/**
	 * HOSTデータ（荷主コード）<BR>
	 */
	protected String externalConsignorCode = "";
	/**
	 * HOSTデータ（荷主名称）<BR>
	 */
	protected String externalConsignorName = "";
	/**
	 * HOSTデータ（出荷先コード）<BR>
	 */
	protected String externalCustomerCode = "";
	/**
	 * HOSTデータ（出荷先名称）<BR>
	 */
	protected String externalCustomerName = "";
	/**
	 * HOSTデータ（出荷伝票No.）<BR>
	 */
	protected String externalShippingTicketNo = "";
	/**
	 * HOSTデータ（商品コード）<BR>
	 */
	protected String externalItemCode = "";
	/**
	 * HOSTデータ（ボールITF）<BR>
	 */
	protected String externalBundleITF = "";
	/**
	 * HOSTデータ（ケースITF）<BR>
	 */
	protected String externalITF = "";
	/**
	 * HOSTデータ（商品名称）<BR>
	 */
	protected String externalItemName = "";
	/**
	 * HOSTデータ（ピース品仕分け場所）<BR>
	 */
	protected String externalPieceLocation = "";
	/**
	 * HOSTデータ（ケース品仕分け場所）<BR>
	 */
	protected String externalCaseLocation = "";
	/**
	 * HOSTデータ（TC区分）<BR>
	 */
	protected String externalTcdcFlag = "";
	/**
	 * HOSTデータ（仕入先コード）<BR>
	 */
	protected String externalSupplierCode = "";
	/**
	 * HOSTデータ（仕入先名称）<BR>
	 */
	protected String externalSupplierName = "";
	/**
	 * HOSTデータ（入荷伝票番号）<BR>
	 */
	protected String externalInstockTicketNo = "";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:34 $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * ConnectionオブジェクトとWareNaviSystemオブジェクト、SystemParameterオブジェクトをパラメータとして受け取り、仕分予定データを取込みます。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param wns  WareNaviSystemオブジェクト
	 * @param searchParam <CODE>Parameter</CODE>クラスを継承したクラス
	 * @return Comment for return value
	 * @throws ReadWriteException
	 * @throws ScheduleException
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
			// SortingPlanOperatorインスタンス生成
			SortingPlanOperator sortingoperator = new SortingPlanOperator(conn);

			// 作業区分：仕分の作業情報、在庫情報をlock
			sortingoperator.lockWorkingInfoStockData();

			// 仕分予定ファイル名を取得
			wFileName = GetSortingFile();

			// CSVファイルを読み込みクラス設定
			DRR =
				new DataLoadCsvReader(
					DataLoadCsvReader.LOADTYPE_PICKINGSUPPORT,
					PlanDate,
					"PLAN_DATE");

			// ファイルサイズチェック
			if (DRR.getFileSize() <= 0)
			{
				// 6023289=レコード数が0件です。ファイル名:{0}
				setMessage("6023289" + wDelim + wFileName);
				return false;
			}

			// バッチNo.取得
			if (batch_seqno.equals(""))
                batch_seqno = SQ.nextSortingPlanBatchNo();

			boolean update_flag = false;
			boolean record_check_flag = true;
			SortingPlan[] sortingplan = null;
			SortingParameter parameter = null;

			// 予定ファイルから対応する予定日の次の１行を取得
			// while (対応する予定日の次の１行を取得){
			// 取得できなくなるまでLoopします
			while (DRR.MoveNext())
			{
				// 全読み取りデータ件数をカウントアップ
				wAllItemCount++;

				update_flag = false;
				parameter = new SortingParameter();
				// エラー用ファイル名称の取得
				if (errlist.getFileName().equals(""))
				{
					errlist.setFileName(DRR.getFileName());
				}
				// SortingParameterに値をSET
				parameter = setSortingParameter(DRR, param, batch_seqno);

				// チェック
				// 項目チェックは全件行う
				if (!check(parameter, wns, DRR, errlist))
				{
					record_check_flag = false;
					continue;
				}
				if (!record_check_flag)
				{
					continue;
				}

				// 既存データチェック
				// 処理開始時にロックするため、
				//	findInstockPlanForUpdate → findInstockPlanForUpdateに変更
				sortingplan = sortingoperator.findSortingPlan(parameter);
				if (sortingplan != null && sortingplan.length != 0)
				{
					// 混合データの場合取得データが複数ある
					// 複数あった場合、1件でも未開始以外のものがあった場合スキップ
					boolean existData = false;
					for (int j = 0; j < sortingplan.length; j++)
					{
						if (!sortingplan[j].getStatusFlag().equals(SortingPlan.STATUS_FLAG_UNSTART))
						{
							existData = true;
						}
					}
					// 1件でも未開始以外のものがあった場合、スキップしログをおとす
					if (existData)
					{
						// SKIPのログ
						// 6022003=２重取り込みのためスキップしました。ファイル:{0} 行:{1}
						String[] msg = { DRR.getFileName(), DRR.getLineNo()};
						RmiMsgLogClient.write(6022003, pName, msg);
						setSkipFlag(true);
						continue;
					}
					// 未開始のデータのみだった場合、削除処理を行う
					for (int j = 0; j < sortingplan.length; j++)
					{
						sortingoperator.updateSortingPlan(
							sortingplan[j].getSortingPlanUkey(),
							pName);
						update_flag = true;
						setOverWriteFlag(true);
					
					}

				}
				sortingoperator.createSortingPlan(parameter);
				setRegistFlag(true);
				if (update_flag)
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
			throw new ScheduleException(e.getMessage());
		}
		catch (ClassNotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InstantiationException e)
		{
			throw new ScheduleException(e.getMessage());
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
			throw new ScheduleException(e.getMessage());
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
	 * 仕分予定ファイルのパスとファイル名を取得します。
	 * 
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return 仕分予定ファイル名（パス＋ファイル名）
	 */
	protected String GetSortingFile() throws ScheduleException
	{
		try
		{
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			String DataLoadPath = IO.get("DATALOAD_FOLDER", "PICKING_SUPPORT");
			String DataLoadFile = IO.get("DATALOAD_FILENAME", "PICKING_SUPPORT");
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
	 * <CODE>SortingParameter</CODE>に値をセットします。<BR>
	 * @param DRR DataLoadCsvReaderオブジェクトを指定します。
	 * @param SystemParameter paramオブジェクトを指定します。
	 * @param String batno バッチNo.を指定します。
	 * @return SortingParameter
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws CvsIllegalDataException 
	 * @throws InvalidStatusException 
	 */
	protected SortingParameter setSortingParameter(
		DataLoadCsvReader DRR,
		SystemParameter param,
		String batno)
		throws ReadWriteException, CsvIllegalDataException, InvalidStatusException
	{
		SortingParameter parameter = new SortingParameter();

		// 速度UPのためDRR.get??Valueの使用を１回にするため、String変数に一旦格納する。
		InitString();

		// 仕分予定日
		if (DRR.getEnable("PLAN_DATE"))
		{
			externalPlanDate = DRR.getDateValue("PLAN_DATE");
			if (!externalPlanDate.equals(""))
			{
				parameter.setPlanDate(externalPlanDate);
			}
		}
		// 荷主コード
		if (DRR.getEnable("CONSIGNOR_CODE"))
		{
			externalConsignorCode = DRR.getAsciiValue("CONSIGNOR_CODE");
			if (!externalConsignorCode.equals(""))
			{
				parameter.setConsignorCode(externalConsignorCode);
			}
			else
			{
				// 荷主コード省略時は、"0"をセット
				parameter.setConsignorCode("0");
			}
		}
		else
		{
			// 荷主コード省略時は、"0"をセット
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
		if (DRR.getEnable("CUSTOMER_NAME"))
		{
			externalCustomerName = DRR.getValue("CUSTOMER_NAME");
			if (!externalCustomerName.equals(""))
			{
				parameter.setCustomerName(externalCustomerName);
			}
		}
		// 出荷伝票No.
		if (DRR.getEnable("SHIPPING_TICKET_NO"))
		{
			externalShippingTicketNo = DRR.getAsciiValue("SHIPPING_TICKET_NO");
			if (!externalShippingTicketNo.equals(""))
			{
				parameter.setShippingTicketNo(externalShippingTicketNo);
			}
		}
		// 出荷行No.
		if (DRR.getEnable("SHIPPING_LINE_NO"))
		{
			parameter.setShippingLineNo(DRR.getIntValue("SHIPPING_LINE_NO"));
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
		// 仕分予定数
		if (DRR.getEnable("PLAN_QTY"))
		{
			parameter.setTotalPlanQty(DRR.getIntValue("PLAN_QTY"));
		}
		// ピース品仕分け場所
		if (DRR.getEnable("JOB_LOCATION_PIECE"))
		{
			externalPieceLocation = DRR.getAsciiValue("JOB_LOCATION_PIECE");
			if (!externalPieceLocation.equals(""))
			{
				parameter.setPieceSortingLocation(externalPieceLocation);
			}
		}
		// ケース品仕分け場所
		if (DRR.getEnable("JOB_LOCATION_CASE"))
		{
			externalCaseLocation = DRR.getAsciiValue("JOB_LOCATION_CASE");
			if (!externalCaseLocation.equals(""))
			{
				parameter.setCaseSortingLocation(externalCaseLocation);
			}
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
		// 入荷伝票No.
		if (DRR.getEnable("INSTOCK_TICKET_NO"))
		{
			externalInstockTicketNo = DRR.getAsciiValue("INSTOCK_TICKET_NO");
			if (!externalInstockTicketNo.equals(""))
			{
				parameter.setInstockTicketNo(externalInstockTicketNo);
			}
		}
		// 入荷伝票行No.
		if (DRR.getEnable("INSTOCK_LINE_NO"))
		{
			parameter.setInstockLineNo(DRR.getIntValue("INSTOCK_LINE_NO"));
		}

		// バッチNo.
		parameter.setBatchNo(batno);
		// 登録区分
		parameter.setRegistKbn(SortingPlan.REGIST_KIND_HOST);
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
	 * エラーメッセージのセット、取込みエラーファイルへの書き込みは呼び出し側で行います。
	 * @param SortingParameter parameterオブジェクトをを指定します。
	 * @param wns  WareNaviSystemオブジェクト
	 * @param DRR DataLoadCsvReaderオブジェクトを指定します。
	 * @param errlist DataLoadStatusCsvFileWriterオブジェクトを指定します。
	 * @return 正常はtrue, 異常発生時はfalseを返します。
	 */
	protected boolean check(
		SortingParameter parameter,
		WareNaviSystem wns,
		DataLoadCsvReader DRR,
		DataLoadStatusCsvFileWriter errlist)
		throws ReadWriteException
	{
		boolean registFlag = true;
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
		// TCDC区分チェック（0:DC品、1:クロスTC品）以外はエラー
		if (!parameter.getTcdcFlag().equals(SortingPlan.TCDC_FLAG_DC)
			&& !parameter.getTcdcFlag().equals(SortingPlan.TCDC_FLAG_CROSSTC))
		{
			// 6023326 = 取り込み予定情報に異常なデータがあったため、処理を中止しました。
			ErrorCode = 6023326;
			Key = "TCDC_FLAG";
			Value = parameter.getTcdcFlag();
			registFlag = false;
		}
		// TC区分１：クロスTC品の場合はクロスドック運用がありになっている事
		if (parameter.getTcdcFlag().equals(SortingPlan.TCDC_FLAG_CROSSTC)
			&& !wns.getCrossdockOperation().equals(WareNaviSystem.OPERATION_FLAG_ADDON))
		{
			// 6023326 = 取り込み予定情報に異常なデータがあったため、処理を中止しました。
			ErrorCode = 6023326;
			Key = "TCDC_FLAG";
			Value = parameter.getTcdcFlag();
			registFlag = false;
		}
		// TC区分１：クロスTC品の必須CHECK
		// TC区分がクロスTCの場合は、仕入先コード、入荷伝票No.、入荷伝票行
		// が未入力の場合はエラー
		if (parameter.getTcdcFlag().equals(SortingPlan.TCDC_FLAG_CROSSTC))
		{
			if ((parameter.getSupplierCode() == null || parameter.getSupplierCode().equals("")))
			{
				// 6023372=必須項目に値がないデータがあったため、処理を中止しました。
				ErrorCode = 6023372;
				Key = "SUPPLIER_CODE";
				Value = parameter.getSupplierCode();
				registFlag = false;
			}
			if ((parameter.getInstockTicketNo() == null
				|| parameter.getInstockTicketNo().equals("")))
			{
				// 6023372=必須項目に値がないデータがあったため、処理を中止しました。
				ErrorCode = 6023372;
				Key = "INSTOCK_TICKET_NO";
				Value = parameter.getInstockTicketNo();
				registFlag = false;
			}
			if (parameter.getInstockLineNo() <= 0)
			{
				// 6023372=必須項目に値がないデータがあったため、処理を中止しました。
				ErrorCode = 6023372;
				Key = "INSTOCK_LINE_NO";
				Value = Integer.toString(parameter.getInstockLineNo());
				registFlag = false;
			}
		}

		if (!registFlag)
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

		return registFlag;
	}
	/**
	 * 各String項目の初期化を行います。
	 */
	protected void InitString()
	{
		externalPlanDate = "";
		externalConsignorCode = "";
		externalConsignorName = "";
		externalCustomerCode = "";
		externalCustomerName = "";
		externalShippingTicketNo = "";
		externalItemCode = "";
		externalBundleITF = "";
		externalITF = "";
		externalItemName = "";
		externalPieceLocation = "";
		externalCaseLocation = "";
		externalTcdcFlag = "";
		externalSupplierCode = "";
		externalSupplierName = "";
		externalInstockTicketNo = "";
	}

	// Private methods -----------------------------------------------
}
//end of class
