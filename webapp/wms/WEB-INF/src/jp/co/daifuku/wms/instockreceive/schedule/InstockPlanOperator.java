package jp.co.daifuku.wms.instockreceive.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * 入荷の予定データを作成するためのデータ検索、更新などを行うクラスです。<BR>
 * 予定データの取り込み、登録、修正・削除などで使用します。<BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/29</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class InstockPlanOperator extends AbstractInstockReceiveSCH
{

	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 入荷予定情報ハンドラ
	 */
	protected InstockPlanHandler wPlanHandler = null;

	/**
	 * 入荷予定情報検索キー
	 */
	private InstockPlanSearchKey wPlanKey = null;

	/**
	 * 入荷予定情報更新キー
	 */
	private InstockPlanAlterKey wPlanAltKey = null;

	/**
	 * 作業情報ハンドラ
	 */
	private WorkingInformationHandler wWorkHandler = null;

	/**
	 * 作業情報検索キー
	 */
	private WorkingInformationSearchKey wWorkKey = null;

	/**
	 * 作業情報更新キー
	 */
	private WorkingInformationAlterKey wWorkAltKey = null;

	/**
	 * 在庫情報ハンドラ
	 */
	private StockHandler wStockHandler = null;

	/**
	 * 在庫情報検索キー
	 */
	private StockSearchKey wStockKey = null;

	/**
	 * 在庫情報更新キー
	 */
	private StockAlterKey wStockAltKey = null;

	/**
	 * 次作業情報ハンドラ
	 */
	private NextProcessInfoHandler wNextHandler = null;

	/**
	 * 次作業情報検索キー
	 */
	private NextProcessInfoSearchKey wNextKey = null;

	/**
	 * 次作業情報更新キー
	 */
	private NextProcessInfoAlterKey wNextAltKey = null;

	/**
	 * シーケンスハンドラー
	 */
	protected SequenceHandler wSequenceHandler = null;
	
	/**
	 * クロスドックありなし
	 */
	protected boolean isExistCross = false; 

	/**
	 * デリミタ
	 */
	protected String wDelim = MessageResource.DELIM;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:15 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。
	 * @param conn データベースへコネクション
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public InstockPlanOperator(Connection conn) throws ReadWriteException, ScheduleException
	{
		wPlanHandler = new InstockPlanHandler(conn);
		wPlanKey = new InstockPlanSearchKey();
		wPlanAltKey = new InstockPlanAlterKey();
		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
		wWorkAltKey = new WorkingInformationAlterKey();
		wStockHandler = new StockHandler(conn);
		wStockKey = new StockSearchKey();
		wStockAltKey = new StockAlterKey();
		wNextHandler = new NextProcessInfoHandler(conn);
		wNextKey = new NextProcessInfoSearchKey();
		wNextAltKey = new NextProcessInfoAlterKey();
		wSequenceHandler = new SequenceHandler(conn);
		isExistCross = isCrossDockPack(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * 入力または取り込んだ予定情報から作業情報、入荷予定情報を検索し該当データをロックします。<BR>
	 * DBに、指定条件に該当するデータがなかった場合はnullを、
	 * 該当するデータがあった場合、<CODE>InStockPlan</CODE>クラスにセットして返します。<BR>
	 * 排他チェック(最終更新日時のチェック)は本メソッドでは行いません。<BR>
	 * 検索情報は<CODE>InstockReceiveParameter</CODE>にセットしてください。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * 必須項目*<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
	 *   <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
	 *   <tr><td>*</td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
	 *   <tr><td>*</td><td>入荷伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
	 *   <tr><td>*</td><td>入荷行No.</td><td>：</td><td>InstockLineNo</td></tr>
	 * </table>
	 * ＊必須項目がセットされていなかった場合、禁止文字が含まれていた場合<CODE>ScheduleException</CODE>を返します。<BR>
	 * </DIR>
	 * <BR>
	 * 以下の順で処理を行います。<BR>
	 * <DIR>
	 *   <U>1.DnWorkInfoに同一情報が存在するかをチェックし、同一情報があればロックする。</U><BR>
	 *     同一情報が存在しない場合、nullを返す。<BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・仕入先コード<BR>
	 *     ・入荷伝票No.<BR>
	 *     ・入荷行No.<BR>
	 *     ・作業区分：入荷<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 *   <U>2.DnInstockPlanに同一情報が存在するかをチェック、そのインスタンスをロック・取得する。</U><BR>
	 *     同一情報が存在しない場合、nullを返す。同一情報が存在した場合取得データを返却する。<BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・仕入先コード<BR>
	 *     ・入荷伝票No.<BR>
	 *     ・入荷行No.<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param inputParam 入荷予定情報を作成するための情報を含む<CODE>InstockReceiveParameter</CODE>
	 * @return InStockPlan 入荷予定情報
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public InstockPlan findInstockPlanForUpdate(InstockReceiveParameter inputParam) throws ReadWriteException, ScheduleException
	{
		// 入力チェックを行う(必須チェック、禁止文字チェック)
		inputCheck(inputParam);

		// 検索条件をセットする
		setWorkKey(inputParam);

		// DnWorkInfoを検索し、ロックする
		WorkingInformation[] wInfo = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);

		// 該当データが存在しなかった場合、nullを返す
		if (wInfo == null || wInfo.length == 0)
		{
			return null;
		}

		// DnInstockPlanを検索し、ロックする
		InstockPlan instPlan = null;
		// 検索条件をセットする
		setPlanKey(inputParam);

		try
		{
			// インスタンスを取得する
			instPlan = (InstockPlan) wPlanHandler.findPrimaryForUpdate(wPlanKey);
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		// 該当データが存在しなかった場合、nullを返す
		if (instPlan == null)
		{
			return null;
		}

		return instPlan;

	}
	
	/**
	 * 入力または取り込んだ予定情報から作業情報、入荷予定情報を検索します。<BR>
	 * DBに、指定条件に該当するデータがなかった場合はnullを、
	 * 該当するデータがあった場合、<CODE>InStockPlan</CODE>クラスにセットして返します。<BR>
	 * 検索情報は<CODE>InstockReceiveParameter</CODE>にセットしてください。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * 必須項目*<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
	 *   <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
	 *   <tr><td>*</td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
	 *   <tr><td>*</td><td>入荷伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
	 *   <tr><td>*</td><td>入荷行No.</td><td>：</td><td>InstockLineNo</td></tr>
	 * </table>
	 * ＊必須項目がセットされていなかった場合、禁止文字が含まれていた場合<CODE>ScheduleException</CODE>を返します。<BR>
	 * </DIR>
	 * <BR>
	 * 以下の順で処理を行います。<BR>
	 * <DIR>
	 *   <U>1.DnWorkInfoに同一情報が存在するかをチェックする。</U><BR>
	 *     同一情報が存在しない場合、nullを返す。<BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・仕入先コード<BR>
	 *     ・入荷伝票No.<BR>
	 *     ・入荷行No.<BR>
	 *     ・作業区分：入荷<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 *   <U>2.DnInstockPlanに同一情報が存在するかをチェック、同一情報があればそのインスタンスを取得する。</U><BR>
	 *     同一情報が存在しない場合、nullを返す。同一情報が存在した場合取得データを返却する。<BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・仕入先コード<BR>
	 *     ・入荷伝票No.<BR>
	 *     ・入荷行No.<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param inputParam 入荷予定情報を作成するための情報を含む<CODE>InstockReceiveParameter</CODE>
	 * @return InStockPlan 入荷予定情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 */
	public InstockPlan findInstockPlan(InstockReceiveParameter inputParam) throws ReadWriteException, ScheduleException
	{
		// 入力チェックを行う(必須チェック、禁止文字チェック)
		inputCheck(inputParam);

		// 検索条件をセットする
		setWorkKey(inputParam);

		// DnWorkInfoを検索します
		WorkingInformation[] wInfo = (WorkingInformation[]) wWorkHandler.find(wWorkKey);

		// 該当データが存在しなかった場合、nullを返す
		if (wInfo == null || wInfo.length == 0)
		{
			return null;
		}

		// DnInstockPlanを検索する
		InstockPlan instPlan = null;
		// 検索条件をセットする
		setPlanKey(inputParam);
		
		if (!StringUtil.isBlank(inputParam.getInstockPlanUkey()))
		{
			wPlanKey.setInstockPlanUkey(inputParam.getInstockPlanUkey(), "!=");
		}

		try
		{
			// インスタンスを取得する
			instPlan = (InstockPlan) wPlanHandler.findPrimary(wPlanKey);
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		// 該当データが存在しなかった場合、nullを返す
		if (instPlan == null)
		{
			return null;
		}

		return instPlan;

	}

	/**
	 * 入荷予定一意キーを指定し、
	 * それに紐づく入荷予定情報、作業情報、在庫情報の状態を削除に更新し、紐づく次作業情報を更新します。<BR>
	 * このメソッドを呼び出す前に、本クラスの
	 * <CODE>findInstockPlanForUpdate</CODE>メソッドを使用し対象データのロックを行ってください。<BR>
	 * また、pPlanUkeyとpProcessNameに値がセットされていない場合、
	 * 禁止文字が含まれていた場合<CODE>ScheduleException</CODE>を返します。<BR>
	 * 本メソッドは以下の順で処理を行います。<BR>
	 * <BR>
	 *   <DIR>
	 *   <U>1.作業情報(DnWorkInfo)の更新</U><BR>
	 *     ・入荷予定一意キーに紐づく作業情報を更新する。<BR>
	 *       -状態フラグ：削除<BR>
	 *       -最終更新処理名：pProcessName<BR>
	 *   <U>2.入荷予定情報(DnInstockPlan)の更新</U><BR>
	 *     ・入荷予定一意キーに紐づく入荷予定情報を更新する。<BR>
	 *       -状態フラグ：削除<BR>
	 *       -最終更新処理名：pProcessName<BR>
	 *   <U>3.在庫情報(DmStock)の更新(更新した作業情報件数分、更新を行う)</U><BR>
	 *     ・更新を行った作業情報から取得した在庫IDに紐づく在庫情報をロックし、更新する。<BR>
	 *       -状態フラグ：完了<BR>
	 *       -作業予定数：更新作業情報の作業予定数(plan_qty)分減算<BR>
	 *       -最終更新処理名：pProcessName<BR>
	 *   <U>4.次作業情報(DnNextProc)の更新</U><BR>
	 *     ・更新を行った入荷予定一意キーがセットされているレコードをロックし、更新する。<BR>
	 *       -入荷予定一意キー：null<BR>
	 *       -最終更新処理名：pProcessName<BR>
	 *   </DIR>
	 * <BR>
	 * 
	 * @param pPlanUKey 処理対象となる入荷予定一意ーキ
	 * @param pProcessName 呼び出しもと処理名
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public void updateInstockPlan(String pPlanUKey, String pProcessName) throws ReadWriteException, ScheduleException
	{
		// 入力チェックを行う(必須チェック、禁止文字チェック)
		checkString(pPlanUKey);
		checkString(pProcessName);

		try
		{
			// 作業情報の更新を行う
			wWorkAltKey.KeyClear();
			wWorkAltKey.setPlanUkey(pPlanUKey);
			wWorkAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_DELETE);
			wWorkAltKey.updateLastUpdatePname(pProcessName);
			wWorkHandler.modify(wWorkAltKey);

			// 在庫更新用に更新した作業情報を取得する
			wWorkKey.KeyClear();
			wWorkKey.setPlanUkey(pPlanUKey);
			WorkingInformation[] workInfo = (WorkingInformation[]) wWorkHandler.find(wWorkKey);
			// 対象情報がなかった場合例外を通知する
			if (workInfo == null || workInfo.length == 0)
			{
				// 6006004=指定されたカラムがテーブルに存在しません。COLUMN={0} TABLE={1}
				RmiMsgLogClient.write("6006004" + wDelim + pPlanUKey + wDelim + "DnWorkInfo", this.getClass().getName());
				throw new ReadWriteException("6006004" + wDelim + pPlanUKey + wDelim + "DnWorkInfo");
			}

			// 入荷予定情報の更新を行う
			wPlanAltKey.KeyClear();
			wPlanAltKey.setInstockPlanUkey(pPlanUKey);
			wPlanAltKey.updateStatusFlag(InstockPlan.STATUS_FLAG_DELETE);
			wPlanAltKey.updateLastUpdatePname(pProcessName);
			wPlanHandler.modify(wPlanAltKey);

			// 更新した作業情報から取得した在庫IDで在庫情報の更新を行う
			for (int i = 0; i < workInfo.length; i++)
			{
				// 該当在庫情報を検索しロックする
				wStockKey.KeyClear();
				wStockKey.setStockId(workInfo[i].getStockId());
				Stock stock = (Stock) wStockHandler.findPrimaryForUpdate(wStockKey);
				if (stock == null)
				{
					// 6006004=指定されたカラムがテーブルに存在しません。COLUMN={0} TABLE={1}
					RmiMsgLogClient.write("6006004" + wDelim + pPlanUKey + wDelim + "DmStock", this.getClass().getName());
					throw new ReadWriteException("6006004" + wDelim + pPlanUKey + wDelim + "DmStock");
				}

				// 作業予定数を減算。在庫状態を完了に更新する
				wStockAltKey.KeyClear();
				wStockAltKey.setStockId(stock.getStockId());
				wStockAltKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				wStockAltKey.updateStockQty(0);
				wStockAltKey.updatePlanQty(0);
				wStockAltKey.updateLastUpdatePname(pProcessName);
				wStockHandler.modify(wStockAltKey);

			}
			   
			// 次作業情報を更新する
			// 該当次作業情報を検索・ロックする
			if (isExistCross)
			{
				wNextKey.KeyClear();
				wNextKey.setPlanUkey(pPlanUKey);
				NextProcessInfo[] nextProc = (NextProcessInfo[]) wNextHandler.findForUpdate(wNextKey);
				// 該当する次作業情報があった場合、次作業情報の更新を行う
				if (nextProc != null && nextProc.length > 0)
				{
					wNextAltKey.KeyClear();
					wNextAltKey.setPlanUkey(pPlanUKey);
					wNextAltKey.updateResultQty(0);
					wNextAltKey.updateShortageQty(0);
					wNextAltKey.updatePlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					wNextAltKey.updateLastUpdatePname(pProcessName);
					wNextHandler.modify(wNextAltKey);
				}
			}

		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

	}

	/**
	 * 入力または取り込んだ予定情報から、入荷予定情報、作業情報、在庫情報を登録します。<BR>
	 * また、入力された入荷伝票No.、入荷伝票行No.に一致する次作業情報があった場合、次作業情報の更新を行います。<BR>
	 * 各パラメータの必須チェックなどは各画面で行ってください。<BR>
	 * 以下の処理を行います。<BR>
	 * <BR>
	 *   <DIR>
	 *   <U>1.入荷予定情報(DnInstockPlan)の登録</U><BR>
	 *     ・入力データをもとに入荷予定情報を作成する。<BR>
	 *   <U>2.作業情報(DnWorkInfo)の登録</U><BR>
	 *     ・登録した入荷予定情報をもとに作業情報を作成する。<BR>
	 *   <U>3.在庫情報(DmStock)の登録(登録した作業情報分、登録を行う)</U><BR>
	 *     ・登録した作業情報をもとに在庫情報を登録する。<BR>
	 *   <U>4.次作業情報(DnNextProc)の登録または更新</U><BR>
	 *     WareNaviシステムのクロスドックパッケージが導入されており、TC/DC区分がTCまたはクロスの場合に以下の処理を行う。<BR>
	 *     ・仕入先コード、入荷伝票No.、入荷伝票行No.が一致する次作業情報を検索・ロックする。<BR>
	 *     ・該当データが存在しない場合、何も行わない(今後処理を追加していく予定)<BR>
	 *     ・該当データが存在した場合、入荷予定一意キーを今回登録したものに更新する。<BR>
	 *   </DIR>
	 * <BR>
	 * ＜入力データ＞
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>更新値</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
	 *   <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
	 *   <tr><td></td><td>荷主名称</td><td>：</td><td>ConsignorName</td></tr>
	 *   <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
	 *   <tr><td></td><td>仕入先名称</td><td>：</td><td>SupplierName</td></tr>
	 *   <tr><td></td><td>伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
	 *   <tr><td></td><td>行No.(入荷伝票行No.)</td><td>：</td><td>InstockLineNo</td></tr>
	 *   <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
	 *   <tr><td></td><td>商品名称</td><td>：</td><td>ItemName</td></tr>
	 *   <tr><td></td><td>入荷予定数(総数)</td><td>：</td><td>TotalPlanQty</td></tr>
	 *   <tr><td></td><td>ケース入数</td><td>：</td><td>EnteringQty</td></tr>
	 *   <tr><td></td><td>ボール入数</td><td>：</td><td>BundleEnteringQty</td></tr>
	 *   <tr><td></td><td>ケースITF</td><td>：</td><td>ITF</td></tr>
	 *   <tr><td></td><td>ボールITF</td><td>：</td><td>BundleITF</td></tr>
	 *   <tr><td></td><td>発注日</td><td>：</td><td>OrderingDate</td></tr>
	 *   <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
	 *   <tr><td></td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
	 *   <tr><td></td><td>出荷先名称</td><td>：</td><td>CustomerName</td></tr>
	 *   <tr><td></td><td>バッチNo.</td><td>：</td><td>BatchNo</td></tr>
	 *   <tr><td></td><td>作業者コード</td><td>：</td><td>WorkerCode</td></tr>
	 *   <tr><td></td><td>作業者名称</td><td>：</td><td>WorkerName</td></tr>
	 *   <tr><td></td><td>端末No.</td><td>：</td><td>TerminalNumber</td></tr>
	 *   <tr><td></td><td>登録区分</td><td>：</td><td>RegistKbn</td></tr>
	 *   <tr><td></td><td>処理名</td><td>：</td><td>RegistPName</td></tr>
	 * </table>
	 * </DIR>
	 * 
	 * @param inputParam 入荷予定情報を作成するための情報を含む<CODE>InstockReceiveParameter</CODE>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public void createInstockPlan(InstockReceiveParameter inputParam) throws ReadWriteException
	{
		// 入力データをもとに入荷予定情報を登録する
		InstockPlan instPlan = null;
		instPlan = createInstock(inputParam);

		// 作成した入荷予定情報をもとに作業情報の登録を行う。
		WorkingInformation[] workInfo = null;
		workInfo = createWorkInfo(instPlan, inputParam.getRegistPName());

		// 作成した作業情報分在庫情報を登録する
		// 現在は作業情報は必ず1件ですが、作業が別れる場合に対応するための作りです。
		for (int i = 0; i < workInfo.length; i++)
		{
			// 作成した作業情報をもとに在庫の登録を行う。
			createStock(workInfo[i], inputParam.getRegistPName());
		}

		// クロスドックパッケージありで、入荷予定情報のTC/DC区分がTCまたはクロスの場合に処理を行う
		if(isExistCross && 
		   (instPlan.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_TC) || instPlan.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_CROSSTC)))
		{
			// 次作業情報の登録または更新を行う
			createNextProc(instPlan, inputParam.getRegistPName());
		}

	}

	/**
	 * 他との排他のため、作業情報、在庫情報をロックします。
	 * 条件：（状態：未開始、作業区分：入荷）
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>状態区分（未開始）</LI>
	 *     <LI>作業区分（入荷）</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @throws ReadWriteException		データベースエラー発生
	 */
	public void lockWorkingInfoStockData()
		throws ReadWriteException
	{
		wWorkKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		wWorkKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		wWorkHandler.lock(wWorkKey);
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 作業情報を検索するための検索キーをセットします。
	 * @param searchParam 検索キー
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void setWorkKey(InstockReceiveParameter searchParam) throws ReadWriteException
	{
		wWorkKey.KeyClear();
		wWorkKey.setPlanDate(searchParam.getPlanDate());
		wWorkKey.setConsignorCode(searchParam.getConsignorCode());
		wWorkKey.setSupplierCode(searchParam.getSupplierCode());
		wWorkKey.setInstockTicketNo(searchParam.getInstockTicketNo());
		wWorkKey.setInstockLineNo(searchParam.getInstockLineNo());
		wWorkKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		wWorkKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
	}
	
	/**
	 * 予定情報を検索するための検索キーをセットします。
	 * @param searchParam 検索キー
	 * @throws ReadWriteException
	 */
	private void setPlanKey(InstockReceiveParameter searchParam) throws ReadWriteException
	{
		wPlanKey.KeyClear();
		wPlanKey.setPlanDate(searchParam.getPlanDate());
		wPlanKey.setConsignorCode(searchParam.getConsignorCode());
		wPlanKey.setSupplierCode(searchParam.getSupplierCode());
		wPlanKey.setInstockTicketNo(searchParam.getInstockTicketNo());
		wPlanKey.setInstockLineNo(searchParam.getInstockLineNo());
		wPlanKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
	}
	
	/**
	 * 入力データの必須チェック、禁止文字チェックを行います。<BR>
	 * @param inputParam 入力データを格納した<CODE>InstockReceiveParameter<CODE><BR>
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 */
	private void inputCheck(InstockReceiveParameter inputParam) throws ScheduleException
	{
		checkString(inputParam.getPlanDate());
		checkString(inputParam.getConsignorCode());
		checkString(inputParam.getSupplierCode());
		checkString(inputParam.getInstockTicketNo());
		checkString(String.valueOf(inputParam.getInstockLineNo()));
	}

	/**
	 * 入力文字列の入力必須チェック・禁止文字チェックを行うためのメソッドです。<BR>
	 * 入力文字列がnullまたは空の場合、禁止文字が含まれていた場合、ScheduleExceptionを返します。<BR>
	 * 
	 * @param str チェックを行う文字列
	 * @throws ScheduleException 入力文字列がnullまたは空の場合、禁止文字が含まれていた場合、ScheduleExceptionを返します。
	 */
	private void checkString(String str) throws ScheduleException
	{
		if (StringUtil.isBlank(str))
		{
			// ログを落とす
			// 6006023=更新条件がセットされていないため、データベースを更新できません。 TABLE={0}
			RmiMsgLogClient.write("6006023" + wDelim + "DnInstockPlan", this.getClass().getName());
			throw new ScheduleException("6006023" + wDelim + "DnInstockPlan");
		}
		// 文字列が禁止文字を含んでいる場合はExceptionをなげる
		if (str.indexOf(WmsParam.NG_PARAMETER_TEXT) != -1)
		{
			// ログを落とす
			// 6003106={0}にシステムで使用できない文字が含まれています
			RmiMsgLogClient.write("6003106" + wDelim + str, this.getClass().getName());
			throw new ScheduleException("6003106" + wDelim + str);
		}
	}

	/**
	 * 入荷予定情報から作業情報を作成します。<BR>
	 * また、作成した作業情報を返します。<BR>
	 * ＜検索条件＞<BR>
	 *   <DIR>
	 *   入荷予定一意キー：入荷予定情報の入荷予定一意キー
	 *   </DIR>
	 * @param pInstPlan 入荷予定情報
	 * @param pProcessName 処理名
	 * @return 登録した作業情報 配列
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected WorkingInformation[] createWorkInfo(InstockPlan pInstPlan, String pProcessName) throws ReadWriteException
	{
		WorkingInformation[] resultWinfo = null;

		try
		{
			String job_seqno = wSequenceHandler.nextJobNo();
			String stockId_seqno = wSequenceHandler.nextStockId();

			WorkingInformation workInfo = new WorkingInformation();
			
			// 作業No.
			workInfo.setJobNo(job_seqno);
			// 作業区分
			workInfo.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
			// 集約作業No.
			workInfo.setCollectJobNo(job_seqno);
			// 状態フラグ：未開始
			workInfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			// 作業開始フラグ：
			workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			// 予定一意キー
			workInfo.setPlanUkey(pInstPlan.getInstockPlanUkey());
			// 在庫ID
			workInfo.setStockId(stockId_seqno);
			// 予定日
			workInfo.setPlanDate(pInstPlan.getPlanDate());
			// 荷主コード
			workInfo.setConsignorCode(pInstPlan.getConsignorCode());
			// 荷主名称
			workInfo.setConsignorName(pInstPlan.getConsignorName());
			// 仕入先コード
			workInfo.setSupplierCode(pInstPlan.getSupplierCode());
			// 仕入先名称
			workInfo.setSupplierName1(pInstPlan.getSupplierName1());
			// 入荷伝票No.
			workInfo.setInstockTicketNo(pInstPlan.getInstockTicketNo());
			// 入荷伝票行No.
			workInfo.setInstockLineNo(pInstPlan.getInstockLineNo());
			// 出荷先コード
			workInfo.setCustomerCode(pInstPlan.getCustomerCode());
			// 出荷先名称
			workInfo.setCustomerName1(pInstPlan.getCustomerName1());
			// 商品コード
			workInfo.setItemCode(pInstPlan.getItemCode());
			// 商品名称
			workInfo.setItemName1(pInstPlan.getItemName1());
			// 作業予定数（ホスト予定数）
			workInfo.setHostPlanQty(pInstPlan.getPlanQty());
			// 作業予定数
			workInfo.setPlanQty(pInstPlan.getPlanQty());
			// 作業可能数
			workInfo.setPlanEnableQty(pInstPlan.getPlanQty());
			// 作業実績数
			workInfo.setResultQty(0);
			// 作業欠品数
			workInfo.setShortageCnt(0);
			// ケース入数
			workInfo.setEnteringQty(pInstPlan.getEnteringQty());
			// ボール入数
			workInfo.setBundleEnteringQty(pInstPlan.getBundleEnteringQty());
			// ケース/ピース区分（荷姿）
			workInfo.setCasePieceFlag(pInstPlan.getCasePieceFlag());
			// ケース/ピース区分（作業形態）
			workInfo.setWorkFormFlag(pInstPlan.getCasePieceFlag());
			// ケースITF
			workInfo.setItf(pInstPlan.getItf());
			// ボールITF
			workInfo.setBundleItf(pInstPlan.getBundleItf());
			// TC/DC区分
			workInfo.setTcdcFlag(pInstPlan.getTcdcFlag());
			// 賞味期限
			workInfo.setUseByDate(pInstPlan.getUseByDate());
			// ロットNo.
			workInfo.setLotNo(pInstPlan.getLotNo());
			// 予定情報コメント
			workInfo.setPlanInformation(pInstPlan.getPlanInformation());
			// 発注日
			workInfo.setOrderingDate(pInstPlan.getOrderingDate());
			// 未作業報告フラグ
			workInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			// バッチNo.
			workInfo.setBatchNo(pInstPlan.getBatchNo());
			// 予定情報登録日時
			workInfo.setPlanRegistDate(new Date());
			// 登録処理名
			workInfo.setRegistPname(pProcessName);
			// 最終更新処理名
			workInfo.setLastUpdatePname(pProcessName);
			// 作業情報の登録
			wWorkHandler.create(workInfo);

			// 取り込みで作業が別れるようになった場合のために取得キーを予定一意キーにしています。
			wWorkKey.KeyClear();
			wWorkKey.setPlanUkey(pInstPlan.getInstockPlanUkey());
			resultWinfo = (WorkingInformation[]) wWorkHandler.find(wWorkKey);

		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return resultWinfo;

	}

	/**
	 * 入力情報より入荷予定情報を作成します。
	 * 
	 * @param param 入力情報
	 * @return 作成した入荷予定情報(INSTOCKPLAN)のエンティティ
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private InstockPlan createInstock(InstockReceiveParameter param) throws ReadWriteException
	{
		InstockPlan instPlan = new InstockPlan();
		try
		{
			String planUkey_seqno = wSequenceHandler.nextInstockPlanKey();

			// 入荷予定一意キー	
			instPlan.setInstockPlanUkey(planUkey_seqno);
			// 状態フラグ：未開始
			instPlan.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
			// 入荷予定日
			instPlan.setPlanDate(param.getPlanDate());
			// 荷主コード
			instPlan.setConsignorCode(param.getConsignorCode());
			// 荷主名称
			instPlan.setConsignorName(param.getConsignorName());
			// 仕入先コード
			instPlan.setSupplierCode(param.getSupplierCode());
			// 仕入先名称
			instPlan.setSupplierName1(param.getSupplierName());
			// 入荷伝票No.
			instPlan.setInstockTicketNo(param.getInstockTicketNo());
			// 入荷伝票行No.
			instPlan.setInstockLineNo(param.getInstockLineNo());
			// 商品コード
			instPlan.setItemCode(param.getItemCode());
			// 商品名称
			instPlan.setItemName1(param.getItemName());
			// 入荷予定数
			instPlan.setPlanQty(param.getTotalPlanQty());
			// 入荷実績数
			instPlan.setResultQty(0);
			// 入荷欠品数
			instPlan.setShortageCnt(0);
			// ケース入数
			instPlan.setEnteringQty(param.getEnteringQty());
			// ボール入数
			instPlan.setBundleEnteringQty(param.getBundleEnteringQty());
			// ケース/ピース区分
			instPlan.setCasePieceFlag(InstockPlan.CASEPIECE_FLAG_NOTHING);
			// ケースITF
			instPlan.setItf(param.getITF());
			// ボールITF
			instPlan.setBundleItf(param.getBundleITF());
			// 発注日
			instPlan.setOrderingDate(param.getOrderingDate());
			// TC/DC区分
			instPlan.setTcdcFlag(param.getTcdcFlag());
			// 出荷先コード
			instPlan.setCustomerCode(param.getCustomerCode());
			// 出荷先名称
			instPlan.setCustomerName1(param.getCustomerName());
			// バッチNo.(スケジュールNo.)
			instPlan.setBatchNo(param.getBatchNo());
			// 作業者コード
			instPlan.setWorkerCode(param.getWorkerCode());
			// 作業者名
			instPlan.setWorkerName(param.getWorkerName());
			// 端末No.RFTNo.
			instPlan.setTerminalNo(param.getTerminalNumber());
			// 登録区分
			instPlan.setRegistKind(param.getRegistKbn());
			// 登録処理名
			instPlan.setRegistPname(param.getRegistPName());
			// 最終更新処理名
			instPlan.setLastUpdatePname(param.getRegistPName());
			// 出荷予定情報の登録
			wPlanHandler.create(instPlan);

		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return instPlan;
	}

	/**
	 * 作成した作業情報をもとに在庫情報を作成します。
	 * 
	 * @param workInfo 作業情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createStock(WorkingInformation workInfo, String pProcessName) throws ReadWriteException
	{
		try
		{
			Stock stock = new Stock();

			// 在庫ID
			stock.setStockId(workInfo.getStockId());
			// 予定一意キー
			stock.setPlanUkey(workInfo.getPlanUkey());
			// エリアNo.
			stock.setAreaNo(workInfo.getAreaNo());
			// ロケーションNo.
			stock.setLocationNo(workInfo.getLocationNo());
			// 商品コード
			stock.setItemCode(workInfo.getItemCode());
			// 商品名称
			stock.setItemName1(workInfo.getItemName1());
			// 在庫ステータス
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_INSTOCK);
			// 在庫数
			stock.setStockQty(0);
			// 引当数
			stock.setAllocationQty(0);
			// 予定数
			stock.setPlanQty(workInfo.getPlanQty());
			// ケース/ピース区分(荷姿)
			stock.setCasePieceFlag(workInfo.getCasePieceFlag());
			// 賞味期限
			stock.setUseByDate(workInfo.getUseByDate());
			// ロットNo.
			stock.setLotNo(workInfo.getLotNo());
			// 予定情報コメント
			stock.setPlanInformation(workInfo.getPlanInformation());
			// 荷主コード
			stock.setConsignorCode(workInfo.getConsignorCode());
			// 荷主名称
			stock.setConsignorName(workInfo.getConsignorName());
			// 仕入先コード
			stock.setSupplierCode(workInfo.getSupplierCode());
			// 仕入先名称
			stock.setSupplierName1(workInfo.getSupplierName1());
			// ケース入数
			stock.setEnteringQty(workInfo.getEnteringQty());
			// ボール入数
			stock.setBundleEnteringQty(workInfo.getBundleEnteringQty());
			// ケースITF
			stock.setItf(workInfo.getItf());
			// ボールITF
			stock.setBundleItf(workInfo.getBundleItf());
			// 登録処理名
			stock.setRegistPname(pProcessName);
			// 最終更新処理名
			stock.setLastUpdatePname(pProcessName);

			// 在庫情報の登録
			wStockHandler.create(stock);
		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
	}

	/**
	 * 作成した入荷予定情報より、次作業情報の登録または更新を行います。<BR>
	 * 入荷予定情報の、仕入先コード、入荷伝票No.、入荷伝票行No.を条件に
	 * 検索を行い、一致する情報があった場合は、更新を行う<BR>
	 * 
	 * @param instPlan 出荷予定情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createNextProc(InstockPlan instPlan, String pProcessName) throws ReadWriteException
	{
		NextProcessInfo[] nextProc = null;

		// 仕入先コードと入荷伝票No.と行No.が一致する次作業情報を検索・ロックする
		wNextKey.KeyClear();
		wNextKey.setShipPlanDate(instPlan.getPlanDate());
		wNextKey.setConsignorCode(instPlan.getConsignorCode());
		wNextKey.setSupplierCode(instPlan.getSupplierCode());
		wNextKey.setInstockTicketNo(instPlan.getInstockTicketNo());
		wNextKey.setInstockLineNo(instPlan.getInstockLineNo());
		wNextKey.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
		wNextKey.setItemCode(instPlan.getItemCode());
		wNextKey.setTcdcFlag(instPlan.getTcdcFlag());
		nextProc = (NextProcessInfo[]) wNextHandler.findForUpdate(wNextKey);

		try
		{
			// 該当データがあった場合、入荷予定一意キーを登録する
			if (nextProc != null && nextProc.length != 0)
			{
				wNextAltKey.KeyClear();
				wNextAltKey.setShipPlanDate(instPlan.getPlanDate());
				wNextAltKey.setConsignorCode(instPlan.getConsignorCode());
				wNextAltKey.setSupplierCode(instPlan.getSupplierCode());
				wNextAltKey.setInstockTicketNo(instPlan.getInstockTicketNo());
				wNextAltKey.setInstockLineNo(instPlan.getInstockLineNo());
				wNextAltKey.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
				wNextAltKey.setItemCode(instPlan.getItemCode());
				wNextAltKey.setTcdcFlag(instPlan.getTcdcFlag());
				wNextAltKey.updatePlanUkey(instPlan.getInstockPlanUkey());
				wNextAltKey.updateInstPlanDate(instPlan.getPlanDate());
				wNextAltKey.updateLastUpdatePname(pProcessName);
				wNextHandler.modify(wNextAltKey);
			}
		}
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

	}

} //end of class
