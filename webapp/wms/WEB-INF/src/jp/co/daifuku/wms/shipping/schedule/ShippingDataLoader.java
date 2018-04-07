//$Id: ShippingDataLoader.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalDataLoader;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.CsvIllegalDataException;
import jp.co.daifuku.wms.base.utility.DataLoadCsvReader;
import jp.co.daifuku.wms.base.utility.DataLoadStatusCsvFileWriter;

/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita <BR>
 * <BR>
 * <CODE>ShippingDataLoader</CODE>クラスは、出荷予定データ取込み処理を行うクラスです。<BR>
 * <CODE>AbstractExternalDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * このクラスが持つメソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、<BR>
 * トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.データ取込み処理（<CODE>load(Connection conn, WareNaviSystem wns, Parameter searchParam)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   ConnectionオブジェクトとWareNaviSystemオブジェクト、Parameterオブジェクトをパラメータとして受け取り、HOST出荷予定ファイル(CSVファイル)から<BR>
 *   データベースにデータを登録します。(出荷予定情報、作業情報、在庫情報) <BR>
 *   メソッド内部でエラーが発生した場合はFalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   処理の開始時に作業情報、在庫情報を排他処理のためロックします。<BR>
 * <BR>
 *   ＜パラメータ＞ 必須入力<BR>
 *     Connectionオブジェクト <BR>
 *     WareNaviSystemオブジェクト <BR>
 *     SystemParameterオブジェクト <BR>
 *      作業予定日<BR>
 * <BR>
 *   ＜処理詳細＞ <BR>
 *      1.取込履歴ファイル作成<BR>
 * <DIR>
 *        ファイル名は日付(YYYYMMDD)＋時刻（HHMISS）＋取込ファイル名となる<BR>
 * </DIR>
 *      2.データ件数初期化<BR>
 * <DIR>
 *        全読み取りデータ件数、登録データ件数、更新データ件数、スキップデータ件数<BR>
 * </DIR>
 *      3.データ登録フラグ を宣言<BR>
 * <DIR>
 *        Falseで宣言します。１件でも登録データがある場合にTrueとします<BR>
 * </DIR>
 *      4.クラスインスタンス作成<BR>
 *      5.予定ファイルから対応する予定日の次の行を取得<BR>
 *      6.既存データの有無チェック<BR>
 *      7.登録処理を行う（出荷予定情報、作業情報、在庫情報）<BR>
 *      8.次作業情報の登録について<BR>
 * <DIR>
 *         0:DC品の場合は次作業情報の登録処理は行いません<BR>
 * <BR>
 *         1:クロスTC品、2:TC品の場合<BR>
 *           検索条件（出荷予定日、出荷先コード、出荷伝票No.、出荷行No.）で次作業情報を検索<BR>
 *           次作業情報があれば、次作業情報を更新（今回登録した出荷予定情報の一意キーをセットする）<BR>
 *           存在しなければ、次作業情報の登録を行う。入荷予定情報が存在しなければ、入荷予定一意キーは空白にする。<BR>
 *           存在する場合、その情報で入荷予定一意キーをセットする。<BR>
 *           但し、事前にWareNaviシステム情報を検索し、入荷パッケージが無い場合、入荷予定情報に関する処理は不要とする。<BR>
 * <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>T.Yamashita</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class ShippingDataLoader extends AbstractExternalDataLoader
{
	// Class fields --------------------------------------------------
	/**
	 * 処理名
	 */
	private final String wProcessName = "ShippingDataLoader";
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $");
	}

	/**
	 * コンストラクタ
	 */
	public ShippingDataLoader()
	{
		System.out.println("ShippingDataLoader_Constructor!");
	}

	/**
	 * ConnectionオブジェクトとLocaleオブジェクト、SystemParameterオブジェクトをパラメータとして受け取り、出荷予定データを取込みます。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param wns  WareNaviSystemオブジェクト
	 * @param searchParam <CODE>Parameter</CODE>クラスを継承したクラス
	 * @return Comment for return value
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean load(Connection conn, WareNaviSystem wns, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		// パラメータより作業予定日を取得
		SystemParameter param = (SystemParameter) searchParam;
		String PlanDate = param.getPlanDate();
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
			// 他との排他のため、処理の開始時に作業情報、在庫情報をロックします。
			WorkingInformationHandler wiObj = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey wskey = new WorkingInformationSearchKey();
			// 条件：（状態：未開始、作業区分：出荷）
			wskey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			wskey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
			wiObj.lock(wskey);

			// 出荷予定ファイル名を取得
			wFileName = getShippingFile();

			// CSVファイルを読み込みクラス設定
			DRR = new DataLoadCsvReader(DataLoadCsvReader.LOADTYPE_SHIPPINGINSPECTION, PlanDate, "SHIPPING_DAY");

			// ファイルサイズチェック
			if (DRR.getFileSize() <= 0)
			{
				setMessage("6023289" + wDelim + wFileName);
				return false;
			}

			// 登録パラメータ
			ShippingParameter shipParam = null;
			// SequenceHandlerインスタンス作成		
			SequenceHandler SQ = new SequenceHandler(conn);
			// バッチNo.
			String batch_seqno = SQ.nextShippingPlanBatchNo();

			// 予定情報処理クラス
			ShippingPlanOperator shipPlanOperator = new ShippingPlanOperator(conn);
			ShippingPlan shipPlan = null;

			boolean record_check_flag = true;
			
			// 予定ファイルから対応する予定日の次の１行を取得
			// while (対応する予定日の次の１行を取得){
			// 取得できなくなるまでLoopします
			while (DRR.MoveNext())
			{
				shipParam = new ShippingParameter();
				// 全読み取りデータ件数をカウントアップ
				wAllItemCount++;
				// エラー用ファイル名称の取得
				if (errlist.getFileName().equals(""))
				{
					errlist.setFileName(DRR.getFileName());
				}

				// 入力情報、登録に必要な情報をパラメータにセットする
				shipParam = setParameter(DRR, param, batch_seqno);

				// 読み取ったデータの出荷予定数が0以下はエラー
				// TCDC区分チェック（0:DC品、1:クロスTC品，2:TC品）以外はエラー
				// TC区分１：クロスTC品の必須CHECK
				// TC区分がクロスTCの場合は、仕入れ先コード、仕入先名、入荷伝票番号、入荷伝票行
				// が未入力の場合はエラー
				if (!check(shipParam, wns, DRR, errlist))
				{
					record_check_flag = false;
					continue;
				}
				if (!record_check_flag)
				{
					continue;
				}

				// 同一データの既存チェック
				shipPlan = shipPlanOperator.findShippingPlan(shipParam);

				// 既存データが存在した場合は、スキップし、次データを検索
				if (shipPlan != null)
				{
					// 取り込みをスキップするかどうか判定
					if (shipPlanOperator.findNextProcTc(shipParam, false))
					{
						// 既存データがありでデータの状態フラグが「０：未開始」の場合
						if (shipPlan.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_UNSTART))
						{
							// 既存データの削除を行う
							shipPlanOperator.updateShippingPlan(shipPlan.getShippingPlanUkey(), wProcessName);
						}
						else
						{
							// スキップ数カウントアップ
							wSkipItemCount++;
							// スキップフラグセット
							setSkipFlag(true);
							// 処理のスキップをログへ記録
							// メッセージの準備
							String[] msg = { DRR.getFileName(), DRR.getLineNo()};
							//6020004 = 取り込み予定情報をスキップしました
							RmiMsgLogClient.write(6020004, wProcessName, msg);
							// 取込みエラーファイル作成クラスにエラー内容を追記する。
							errlist.addStatusCsvFile(
								DataLoadStatusCsvFileWriter.STATUS_ERROR,
								DRR.getLineData(),
								getMessage());
							// LOOPをCONTINUEする
							continue;
						}

						// 更新データ件数をカウントアップ
						wUpdateItemCount++;
						// 上書きフラグをセット
						setOverWriteFlag(true);

						// 取込み情報を新規に登録する
						shipPlanOperator.createShippingPlan(shipParam);
					}
					else
					{
						// スキップ数カウントアップ
						wSkipItemCount++;
						// スキップフラグセット
						setSkipFlag(true);
						// 処理のスキップをログへ記録
						// メッセージの準備
						String[] msg = { DRR.getFileName(), DRR.getLineNo()};
						//6020004 = 取り込み予定情報をスキップしました
						RmiMsgLogClient.write(6020004, wProcessName, msg);
						// 取込みエラーファイル作成クラスにエラー内容を追記する。
						errlist.addStatusCsvFile(
							DataLoadStatusCsvFileWriter.STATUS_ERROR,
							DRR.getLineData(),
							getMessage());
						// LOOPをCONTINUEする
						continue;
					}

				}
				else
				{

					// 取り込みをスキップするかどうか判定
					if (shipPlanOperator.findNextProcTc(shipParam, true))
					{
						// 取込み情報を新規に登録する
						shipPlanOperator.createShippingPlan(shipParam);

						// 登録データ件数カウントアップ
						wInsertItemCount++;
					}
					else
					{
						// スキップ数カウントアップ
						wSkipItemCount++;
						// スキップフラグセット
						setSkipFlag(true);
						// 処理のスキップをログへ記録
						// メッセージの準備
						String[] msg = { DRR.getFileName(), DRR.getLineNo()};
						//6020004 = 取り込み予定情報をスキップしました
						RmiMsgLogClient.write(6020004, wProcessName, msg);
						// 取込みエラーファイル作成クラスにエラー内容を追記する。
						errlist.addStatusCsvFile(
							DataLoadStatusCsvFileWriter.STATUS_ERROR,
							DRR.getLineData(),
							getMessage());
						// LOOPをCONTINUEする
						continue;
					}

				}

				setRegistFlag(true);
				// 取込みエラーファイル作成クラスに正常データも追記する。
				errlist.addStatusCsvFile(DataLoadStatusCsvFileWriter.STATUS_NORMAL, DRR.getLineData(), getMessage());
			}

			if (!record_check_flag )
			{
				// 6023326=異常なデータがあったため、処理を中止しました。ログを参照してください。
				setMessage("6023326");
				return false;
			}
			return true;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			// ファイルが見つかりませんでした。{0}
			RmiMsgLogClient.write(new TraceHandler(6003009, e), wProcessName);
			setMessage("6003009" + wDelim + wFileName);
			return false;
		}
		catch (IOException e)
		{
			//6006020 = ファイル入出力エラー
			RmiMsgLogClient.write(new TraceHandler(6006020, e), wProcessName);
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
			RmiMsgLogClient.write(6026085, wProcessName, msg);
			return false;
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException();
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
	 * ファイルのレコードをパラメータにセットします。
	 * 
	 * @param reader   DataLoadCsvReaderクラス
	 * @param param    SystemParameterクラス
	 * @param pBatchNo バッチNo.
	 * @return ShippingParameterクラス
	 * @throws ReadWriteException       データベースとの接続で異常が発生した場合に通知されます。
	 * @throws CsvIllegalDataException  CSVファイル上の指定された項目の値が不正だった場合に通知されます。
	 */
	protected ShippingParameter setParameter(DataLoadCsvReader reader, SystemParameter param, String pBatchNo)
		throws ReadWriteException, CsvIllegalDataException
	{

		ShippingParameter retParam = new ShippingParameter();

		// 出荷予定日
		if (reader.getEnable("SHIPPING_DAY"))
			retParam.setPlanDate(reader.getDateValue("SHIPPING_DAY"));
		// 荷主コード
		if (reader.getEnable("CONSIGNOR_CODE"))
		{
			if (!reader.getAsciiValue("CONSIGNOR_CODE").equals(""))
			{
				retParam.setConsignorCode(reader.getAsciiValue("CONSIGNOR_CODE"));
			}
			else
			{
				retParam.setConsignorCode("0");
			}
		}
		else
		{
			retParam.setConsignorCode("0");
		}
		// 荷主名称
		if (reader.getEnable("CONSIGNOR_NAME"))
			retParam.setConsignorName(reader.getValue("CONSIGNOR_NAME"));
		// 出荷先コード
		if (reader.getEnable("CUSTOMER_CODE"))
			retParam.setCustomerCode(reader.getAsciiValue("CUSTOMER_CODE"));
		// 出荷先名
		if (reader.getEnable("CUSTOMER_NAME"))
			retParam.setCustomerName(reader.getValue("CUSTOMER_NAME"));
		// 出荷伝票No
		if (reader.getEnable("TICKET_NO"))
			retParam.setShippingTicketNo(reader.getAsciiValue("TICKET_NO"));
		// 出荷伝票行
		if (reader.getEnable("LINE_NO"))
			retParam.setShippingLineNo(reader.getIntValue("LINE_NO"));
		// 商品コード
		if (reader.getEnable("ITEM_CODE"))
			retParam.setItemCode(reader.getAsciiValue("ITEM_CODE"));
		// 商品名称
		if (reader.getEnable("ITEM_NAME"))
			retParam.setItemName(reader.getValue("ITEM_NAME"));
		// 出荷予定数
		if (reader.getEnable("PLAN_QTY"))
			retParam.setTotalPlanQty(reader.getIntValue("PLAN_QTY"));
		// ケース入数
		if (reader.getEnable("ENTERING_QTY"))
			retParam.setEnteringQty(reader.getIntValue("ENTERING_QTY"));
		// ボール入数
		if (reader.getEnable("BUNDLE_ENTERING_QTY"))
			retParam.setBundleEnteringQty(reader.getIntValue("BUNDLE_ENTERING_QTY"));
		// ケースITF
		if (reader.getEnable("ITF"))
			retParam.setITF(reader.getAsciiValue("ITF"));
		// ボールITF
		if (reader.getEnable("BUNDLE_ITF"))
			retParam.setBundleITF(reader.getAsciiValue("BUNDLE_ITF"));
		// 発注日
		if (reader.getEnable("ORDERING_DATE"))
			retParam.setOrderingDate(reader.getValue("ORDERING_DATE"));
		// TC/DC区分
		if (reader.getEnable("SHIPPING_TYPE"))
			retParam.setTcdcFlag(reader.getValue("SHIPPING_TYPE"));
		// 仕入先コード
		if (reader.getEnable("SUPPLIER_CODE"))
			retParam.setSupplierCode(reader.getAsciiValue("SUPPLIER_CODE"));
		// 仕入先名称
		if (reader.getEnable("SUPPLIER_NAME"))
			retParam.setSupplierName(reader.getValue("SUPPLIER_NAME"));
		// 入荷伝票No
		if (reader.getEnable("INSTOCK_TICKET_NO"))
			retParam.setInstockTicketNo(reader.getAsciiValue("INSTOCK_TICKET_NO"));
		// 入荷伝票行
		if (reader.getEnable("INSTOCK_LINE_NO"))
		{
			retParam.setInstockLineNo(reader.getIntValue("INSTOCK_LINE_NO"));
		}

		// 登録に必要な情報をパラメータにセットする

		// 状態フラグ
		retParam.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);
		// バッチNo（スケジュールNo）
		retParam.setBatchNo(pBatchNo);
		// 作業者コード
		retParam.setWorkerCode(param.getWorkerCode());
		// 作業者名称
		retParam.setWorkerName(param.getWorkerName());
		// 端末No
		retParam.setTerminalNumber(param.getTerminalNumber());
		// 登録区分
		retParam.setRegistKbn(ShippingPlan.REGIST_KIND_HOST);
		// 登録処理名
		retParam.setRegistPName(wProcessName);
		// 最終更新処理名
		retParam.setLastUpdatePName(wProcessName);
		// ケース／ピース区分
		retParam.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);

		return retParam;

	}

	/**
	 * 指定されたデータ内の項目チェック処理を行います。
	 * エラーメッセージのセット、取込みエラーファイルへの書き込みは呼び出し側で行います。
	 * @param param   ShippingParameterクラス
	 * @param wns     WareNaviSystemオブジェクト
	 * @param DRR     DataLoadCsvReaderオブジェクトを指定します。
	 * @param errlist データ取込処理の取込みエラーリスト
	 * @return 正常はtrue, 異常発生時はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean check(
		ShippingParameter param,
		WareNaviSystem wns,
		DataLoadCsvReader DRR,
		DataLoadStatusCsvFileWriter errlist)
		throws ReadWriteException, ScheduleException
	{
		boolean isCorrectData = true;
		// 読み取ったデータの出荷予定数が0以下はエラー
		if (param.getTotalPlanQty() <= 0)
		{
			isCorrectData = false;
		}

		// TCDC区分チェック（0:DC品、1:クロスTC品，2:TC品）以外はエラー
		if (!param.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_DC)
			&& !param.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_CROSSTC)
			&& !param.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_TC))
		{
			isCorrectData = false;
		}
		// TC区分１：クロスTC品の場合はクロスドック運用がありになっている事
		if (param.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_CROSSTC)
			&& !wns.getCrossdockOperation().equals(WareNaviSystem.OPERATION_FLAG_ADDON))
		{
			isCorrectData = false;
		}

		// TC区分２：TC品の場合はTC運用がありになっている事
		if (param.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_TC)
			&& !wns.getTcOperation().equals(WareNaviSystem.OPERATION_FLAG_ADDON))
		{
			isCorrectData = false;
		}

		// TC区分１：クロスTC品の必須CHECK
		// TC区分がクロスTCの場合は、仕入れ先コード、仕入先名、入荷伝票番号、入荷伝票行
		// が未入力の場合はエラー
		if (param.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_CROSSTC))
		{
			if (StringUtil.isBlank(param.getSupplierCode())
				|| StringUtil.isBlank(param.getInstockTicketNo())
				|| param.getInstockLineNo() <= 0)
			{
				isCorrectData = false;
			}
		}
		// TC区分２：TC品の必須CHECK
		// TC区分がTCの場合は、仕入れ先コード
		// が未入力の場合はエラー
		if (param.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_TC))
		{
			if (StringUtil.isBlank(param.getSupplierCode()))
			{
				isCorrectData = false;
			}
		}
		
		if (!isCorrectData)
		{
			// 6023326=異常なデータがあったため、処理を中止しました。ログを参照してください。
			setMessage("6023326");

			// 6026001=取り込み予定情報に異常なデータがありました。ファイル:{0} 行:{1}
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			RmiMsgLogClient.write(6026001, wProcessName, msg);

			// 取込みエラーファイル作成クラスにエラー内容を追記する。
			errlist.addStatusCsvFile(DataLoadStatusCsvFileWriter.STATUS_ERROR, DRR.getLineData(), getMessage());
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * 出荷予定ファイルのパスとファイル名を取得します。
	 * 
	 * @return 出荷予定ファイルのパスとファイル名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected String getShippingFile() throws ScheduleException
	{
		try
		{
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			String DataLoadPath = IO.get("DATALOAD_FOLDER", "SHIPPING_INSPECTION");
			String DataLoadFile = IO.get("DATALOAD_FILENAME", "SHIPPING_INSPECTION");
			return DataLoadPath + DataLoadFile;
		}
		catch (ReadWriteException e)
		{
			//  環境情報ファイルが見つかりませんでした。{0}
			Object[] msg = new Object[1];
			msg[0] = WmsParam.ENVIRONMENT;
			RmiMsgLogClient.write(new TraceHandler(6026004, e), "DataLoadCsvReader", msg);
			throw new ScheduleException();
		}

	}
	// Private methods -----------------------------------------------
}
//end of class
