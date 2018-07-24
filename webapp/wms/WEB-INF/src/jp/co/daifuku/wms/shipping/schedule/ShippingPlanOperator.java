//$Id: ShippingPlanOperator.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

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
import jp.co.daifuku.common.LogMessage;
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
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * 出荷の予定データを作成するためのデータ検索、更新、排他などを行うクラスです。<BR>
 * 予定データの取り込み、登録、修正・削除などで使用します。<BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/20</TD><TD>T.Yamashita</TD><TD>新規作成</TD></TR>
 * <TR><TD>2004/12/10</TD><TD>Y.Okamura</TD><TD>登録・削除処理追記</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ShippingPlanOperator extends AbstractShippingSCH
{

	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 入荷予定情報ハンドラ
	 */
	private InstockPlanHandler wInstPlanHandler = null;

	/**
	 * 入荷予定情報検索キー
	 */
	private InstockPlanSearchKey wInstPlanKey = null;

	/**
	 * 入荷予定情報更新キー
	 */
	private InstockPlanAlterKey wInstPlanAltKey = null;

	/**
	 * 仕分予定情報ハンドラ
	 */
	private SortingPlanHandler wSortPlanHandler = null;

	/**
	 * 仕分予定情報検索キー
	 */
	private SortingPlanSearchKey wSortPlanKey = null;

	/**
	 * 仕分予定情報更新キー
	 */
	private SortingPlanAlterKey wSortPlanAltKey = null;

	/**
	 * 出荷予定情報ハンドラ
	 */
	protected ShippingPlanHandler wShipPlanHandler = null;

	/**
	 * 出荷予定情報検索キー
	 */
	private ShippingPlanSearchKey wShipPlanKey = null;

	/**
	 * 出荷予定情報更新キー
	 */
	private ShippingPlanAlterKey wShipPlanAltKey = null;

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
	private boolean isExistCrossDocPack = false;

	/**
	 * 入荷パッケージありなし
	 */
	private boolean isExistInstockPack = false;

	/**
	 * 仕分パッケージありなし
	 */
	private boolean isExistPickingPack = false;

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
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:31 $");
	}
	
	// Constructors --------------------------------------------------
	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。<BR>
	 * 各検索クラスの初期化、導入パッケージの取得を行います。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public ShippingPlanOperator(Connection conn) throws ReadWriteException,ScheduleException
	{
		// 検索クラスの初期化を行う
		// 入荷予定情報
		wInstPlanHandler = new InstockPlanHandler(conn);
		wInstPlanKey = new InstockPlanSearchKey();
		wInstPlanAltKey = new InstockPlanAlterKey();

		// 仕分予定情報
		wSortPlanHandler = new SortingPlanHandler(conn);
		wSortPlanKey = new SortingPlanSearchKey();
		wSortPlanAltKey = new SortingPlanAlterKey();
		
		// 出荷予定情報
		wShipPlanHandler = new ShippingPlanHandler(conn);
		wShipPlanKey = new ShippingPlanSearchKey();
		wShipPlanAltKey = new ShippingPlanAlterKey();

		// 作業情報
		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
		wWorkAltKey = new WorkingInformationAlterKey();

		// 在庫情報
		wStockHandler = new StockHandler(conn);
		wStockKey = new StockSearchKey();
		wStockAltKey = new StockAlterKey();

		// 次作業情報
		wNextHandler = new NextProcessInfoHandler(conn);
		wNextKey = new NextProcessInfoSearchKey();
		wNextAltKey = new NextProcessInfoAlterKey();

		// シーケンスオブジェクト
		wSequenceHandler = new SequenceHandler(conn);

		// 導入パッケージをセットする
		setAddOnPackage(conn);
	}

	/**
	 * このクラスを使用してDBの検索・更新を行わない場合はこのコンストラクタを使用してください。
	 */
	public ShippingPlanOperator()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * クロスドックパッケージのありなしを取得します<BR>
	 * <DIR>
	 * true：クロスドックパッケージあり<BR>
	 * false：クロスドックパッケージなし<BR>
	 * </DIR>
	 * 
	 * @return クロスドックパッケージありなし
	 */
	public boolean getIsExistCrossDockPack()
	{
		return isExistCrossDocPack;
	}

	/**
	 * 入荷パッケージのありなしを取得します<BR>
	 * <DIR>
	 * true：入荷パッケージあり<BR>
	 * false：入荷パッケージなし<BR>
	 * </DIR>
	 * 
	 * @return 入荷パッケージありなし
	 */
	public boolean getIsExistInstockPack()
	{
		return isExistInstockPack;
	}

	/**
	 * 仕分パッケージのありなしを取得します<BR>
	 * <DIR>
	 * true：仕分パッケージあり<BR>
	 * false：仕分パッケージなし<BR>
	 * </DIR>
	 * 
	 * @return 仕分パッケージありなし
	 */
	public boolean getIsExistPickingPack()
	{
		return isExistPickingPack;
	}

	/**
	 * 入力または取り込んだ予定情報から作業情報、出荷予定情報を検索し該当データをロックします。<BR>
	 * DBに、指定条件に該当するデータがなかった場合はnullを、
	 * 該当するデータがあった場合、<CODE>ShippingPlan</CODE>クラスにセットして返します。<BR>
	 * 排他チェック(最終更新日時のチェック)は本メソッドでは行いません。<BR>
	 * 検索情報は<CODE>ShippingParameter</CODE>にセットしてください。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * 必須項目*<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>出荷予定日</td><td>：</td><td>PlanDate</td></tr>
	 *   <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
	 *   <tr><td>*</td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
	 *   <tr><td>*</td><td>出荷伝票No.</td><td>：</td><td>ShippingTicketNo</td></tr>
	 *   <tr><td>*</td><td>出荷行No.</td><td>：</td><td>ShippingLineNo</td></tr>
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
	 *     ・出荷予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・出荷先コード<BR>
	 *     ・出荷伝票No.<BR>
	 *     ・出荷行No.<BR>
	 *     ・作業区分：出荷<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 *   <U>2.DnShippingPlanに同一情報が存在するかをチェック、そのインスタンスをロック・取得する。</U><BR>
	 *     同一情報が存在しない場合、nullを返す。同一情報が存在した場合取得データを返却する。<BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・出荷予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・出荷先コード<BR>
	 *     ・出荷伝票No.<BR>
	 *     ・出荷行No.<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param inputParam 出荷予定情報を作成するための情報を含む<CODE>ShippingParameter</CODE>
	 * @return ShippingPlan 出荷予定情報
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public ShippingPlan findShippingPlanForUpdate(ShippingParameter inputParam) throws ReadWriteException, ScheduleException
	{
		// 検索時にロックを行う
		return findShippingPlanProc(inputParam, true);

	}

	/**
	 * 入力または取り込んだ予定情報から作業情報、出荷予定情報を検索します。<BR>
	 * DBに、指定条件に該当するデータがなかった場合はnullを、
	 * 該当するデータがあった場合、<CODE>ShippingPlan</CODE>クラスにセットして返します。<BR>
	 * 検索情報は<CODE>ShippingParameter</CODE>にセットしてください。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * 必須項目*<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>出荷予定日</td><td>：</td><td>PlanDate</td></tr>
	 *   <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
	 *   <tr><td>*</td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
	 *   <tr><td>*</td><td>出荷伝票No.</td><td>：</td><td>ShippingTicketNo</td></tr>
	 *   <tr><td>*</td><td>出荷行No.</td><td>：</td><td>ShippingLineNo</td></tr>
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
	 *     ・出荷予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・出荷先コード<BR>
	 *     ・出荷伝票No.<BR>
	 *     ・出荷行No.<BR>
	 *     ・作業区分：出荷<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 *   <U>2.DnShippingPlanに同一情報が存在するかをチェック、同一情報があればそのインスタンスを取得する。</U><BR>
	 *     同一情報が存在しない場合、nullを返す。同一情報が存在した場合取得データを返却する。<BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・出荷予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・出荷先コード<BR>
	 *     ・出荷伝票No.<BR>
	 *     ・出荷行No.<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param inputParam 出荷予定情報を作成するための情報を含む<CODE>ShippingParameter</CODE>
	 * @return ShippingPlan 出荷予定情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public ShippingPlan findShippingPlan(ShippingParameter inputParam) throws ReadWriteException, ScheduleException
	{
		// 検索時にロックを行わない
		return findShippingPlanProc(inputParam, false);
	}

	/**
	 * 入力または取り込んだ予定情報から作業情報、出荷予定情報を検索します。<BR>
	 * DBに、指定条件に該当するデータがなかった場合はnullを、
	 * 該当するデータがあった場合、<CODE>ShippingPlan</CODE>クラスにセットして返します。<BR>
	 * また、検索時にロックを行うかどうかをパラメータによって指定できます。<BR>
	 * 検索情報は<CODE>ShippintParameter</CODE>にセットしてください。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * 必須項目*<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>出荷予定日</td><td>：</td><td>PlanDate</td></tr>
	 *   <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
	 *   <tr><td>*</td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
	 *   <tr><td>*</td><td>出荷伝票No.</td><td>：</td><td>ShippingTicketNo</td></tr>
	 *   <tr><td>*</td><td>出荷行No.</td><td>：</td><td>ShippingLineNo</td></tr>
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
	 *     ・出荷予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・出荷先コード<BR>
	 *     ・出荷伝票No.<BR>
	 *     ・出荷行No.<BR>
	 *     ・作業区分：出荷<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 *   <U>2.DnShippingPlanに同一情報が存在するかをチェック、同一情報があればそのインスタンスを取得する。</U><BR>
	 *     同一情報が存在しない場合、nullを返す。同一情報が存在した場合取得データを返却する。<BR>
	 *     一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・出荷予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・出荷先コード<BR>
	 *     ・出荷伝票No.<BR>
	 *     ・出荷行No.<BR>
	 *     ・状態フラグ：削除以外<BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param inputParam 出荷予定情報を作成するための情報を含む<CODE>ShippingParameter</CODE>
	 * @param pUpdateFlag 検索時にロックをするかどうか true：ロックする false：ロックしない
	 * @return ShippingPlan 出荷予定情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 */
	public ShippingPlan findShippingPlanProc(ShippingParameter inputParam, boolean pUpdateFlag) throws ReadWriteException, ScheduleException
	{
		// 入力チェックを行う(必須チェック、禁止文字チェック)
		checkString(inputParam.getPlanDate());
		checkString(inputParam.getConsignorCode());
		checkString(inputParam.getCustomerCode());
		checkString(inputParam.getShippingTicketNo());
		checkString(String.valueOf(inputParam.getShippingLineNo()));

		// DnWorkInfoを検索する
		WorkingInformation[] wInfo = null;
		// 検索条件をセットする
		wWorkKey.KeyClear();
		// 予定日
		wWorkKey.setPlanDate(inputParam.getPlanDate());
		// 荷主コード
		wWorkKey.setConsignorCode(inputParam.getConsignorCode());
		// 出荷先コード
		wWorkKey.setCustomerCode(inputParam.getCustomerCode());
		// 出荷伝票No.
		wWorkKey.setShippingTicketNo(inputParam.getShippingTicketNo());
		// 出荷行No.
		wWorkKey.setShippingLineNo(inputParam.getShippingLineNo());
		// 作業種別：出荷
		wWorkKey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		// 作業状態：削除以外
		wWorkKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");

		// インスタンスを取得する
		if (pUpdateFlag)
		{
			wInfo = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);
		}
		else
		{
			wInfo = (WorkingInformation[]) wWorkHandler.find(wWorkKey);
		}


		// 該当データが存在しなかった場合、nullを返す
		if (wInfo == null || wInfo.length == 0)
		{
			return null;
		}

		// DnShippingPlanを検索する
		ShippingPlan shipPlan = null;
		// 検索条件をセットする
		wShipPlanKey.KeyClear();
		// 予定日
		wShipPlanKey.setPlanDate(inputParam.getPlanDate());
		// 荷主コード
		wShipPlanKey.setConsignorCode(inputParam.getConsignorCode());
		// 出荷先コード
		wShipPlanKey.setCustomerCode(inputParam.getCustomerCode());
		// 出荷伝票No.
		wShipPlanKey.setShippingTicketNo(inputParam.getShippingTicketNo());
		// 出荷伝票行No.
		wShipPlanKey.setShippingLineNo(inputParam.getShippingLineNo());
		// 作業状態：削除以外
		wShipPlanKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		// 出荷予定一意キーに入力がある場合、自データ以外を検索する
		if (!StringUtil.isBlank(inputParam.getShippingPlanUkey()))
		{
			wShipPlanKey.setShippingPlanUkey(inputParam.getShippingPlanUkey(), "!=");
		}

		try
		{
			// インスタンスを取得する
			// 取得時にデータをロックする
			if (pUpdateFlag)
			{
				shipPlan = (ShippingPlan) wShipPlanHandler.findPrimaryForUpdate(wShipPlanKey);
			}
			// 取得時にデータをロックしない
			else
			{
				shipPlan = (ShippingPlan) wShipPlanHandler.findPrimary(wShipPlanKey);
			}


		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		// 該当データが存在しなかった場合、nullを返す
		if (shipPlan == null)
		{
			return null;
		}

		return shipPlan;

	}

	/**
	 * 入力または取り込んだ出荷予定情報から次作業情報を検索し、読み込んだ出荷予定情報を<BR>
	 * 更新するか、スキップして読み飛ばすか判定します。<BR>
	 * クロスドックパッケージがありで、TCDC区分がTCの場合、<BR>
	 * ひとつの入荷データに対して複数の出荷データを作成するのを防ぐ為の処理です。<BR>
	 * 
	 * @param inputParam 出荷予定情報を作成するための情報を含む<CODE>ShippingParameter</CODE>
	 * @param loadFlg 新規登録か更新か true：新規登録 false：更新
	 * @return true：更新対象 false：スキップ対象
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public boolean findNextProcTc(ShippingParameter inputParam, boolean loadFlg) throws ReadWriteException 
	{
		try
		{
			// クロスドックパッケージありのでTCの場合
			if (isExistCrossDocPack && inputParam.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_TC))
			{
				// 検索条件を設定する
				wNextKey.KeyClear();

				// 荷主コード
				wNextKey.setConsignorCode(inputParam.getConsignorCode());
				// 予定日
				wNextKey.setShipPlanDate(inputParam.getPlanDate());
				// 仕入先コード
				wNextKey.setSupplierCode(inputParam.getSupplierCode());
				// 入荷伝票No.
				wNextKey.setInstockTicketNo(inputParam.getInstockTicketNo());
				// 入荷伝票行No.
				wNextKey.setInstockLineNo(inputParam.getInstockLineNo());
				// 商品コード
				wNextKey.setItemCode(inputParam.getItemCode());
				// 出荷先コード
				wNextKey.setCustomerCode(inputParam.getCustomerCode());
				// TC/DC区分
				wNextKey.setTcdcFlag(inputParam.getTcdcFlag());
				// 出荷予定一意キーに入力がある場合、自データ以外を検索する
				if (!StringUtil.isBlank(inputParam.getShippingPlanUkey()))
				{
					wNextKey.setShippingPlanUkey(inputParam.getShippingPlanUkey(), "!=");
				}
				wNextKey.setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY, "!=");
				
				// 検索結果を取得
				NextProcessInfo wNextInfo = (NextProcessInfo) wNextHandler.findPrimaryForUpdate(wNextKey);
				
				// loadFlgによりデータを更新するかスキップするか判定
				if (loadFlg)
				{
					// 新規登録の場合
					// 同一の入荷伝票No、行Noがなければ新規データとして取り込む
					if (wNextInfo == null)
					{
						// 取り込み
						return true;
					}
					else
					{
						// スキップ
						return false;
					}
				}
				else
				{
					// 更新処理の場合
					if (wNextInfo == null)
					{
						// 同一の入荷伝票No、行Noがなければ更新対象
						// 取り込み
						return true;
					}
					else
					{
						// 同一の入荷伝票No、行Noが次作業情報にあり、そのデータの出荷伝票No、行Noが
						// 今回の更新対象と異なる場合、スキップし
						// 同一の場合は、更新する
						if (wNextInfo.getShippingTicketNo().equals(inputParam.getShippingTicketNo()) && 
						     wNextInfo.getShippingLineNo() == inputParam.getShippingLineNo())
						{
							// 取り込み
							return true;
						}
						else
						{
							// スキップ
							return false;
						}
					}
				}
			}
			else
			{
				// 取り込み
				return true;
			}
		}

		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

	/**
	 * 出荷予定一意キーを指定し、
	 * それに紐づく出荷予定情報、作業情報、在庫情報の状態を削除に更新し、紐づく次作業情報を削除します。<BR>
	 * このメソッドを呼び出す前に、本クラスの
	 * <CODE>findShippingPlanForUpdate</CODE>メソッドを使用し対象データのロックを行ってください。<BR>
	 * また、pPlanUkeyとpProcessNameに値がセットされていない場合、
	 * 禁止文字が含まれていた場合<CODE>ScheduleException</CODE>を返します。<BR>
	 * 本メソッドは以下の順で処理を行います。<BR>
	 * <BR>
	 *   <DIR>
	 *   <U>1.作業情報(DnWorkInfo)の更新</U><BR>
	 *     ・出荷予定一意キーに紐づく作業情報を更新する。<BR>
	 *       -状態フラグ：削除<BR>
	 *       -最終更新処理名：pProcessName<BR>
	 *   <U>2.出荷予定情報(DnSortingPlan)の更新</U><BR>
	 *     ・出荷予定一意キーに紐づく出荷予定情報を更新する。<BR>
	 *       -状態フラグ：削除<BR>
	 *       -最終更新処理名：pProcessName<BR>
	 *   <U>3.在庫情報(DmStock)の更新(更新した作業情報件数分、更新を行う)</U><BR>
	 *     ・更新を行った作業情報から取得した在庫IDに紐づく在庫情報をロックし、更新する。<BR>
	 *       -状態フラグ：完了<BR>
	 *       -在庫数：更新作業情報の作業予定数(plan_qty)分減算<BR>
	 *       -引当数：更新作業情報の作業予定数(plan_qty)分減算<BR>
	 *       -最終更新処理名：pProcessName<BR>
	 *   <U>4.次作業情報(DnNextProc)の更新</U><BR>
	 *     ・クロスドックパッケージが導入されていて処理対象となる出荷予定情報がDC以外の場合、
	 *       出荷予定一意キーに紐づく次作業情報をロックし、DB上から削除する。<BR>
	 *   </DIR>
	 * <BR>
	 * 
	 * @param pPlanUKey 処理対象となる出荷予定一意ーキ
	 * @param pProcessName 呼び出しもと処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 */
	public void updateShippingPlan(String pPlanUKey, String pProcessName) throws ReadWriteException, ScheduleException
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

			// 出荷予定情報の更新を行う
			wShipPlanAltKey.KeyClear();
			wShipPlanAltKey.setShippingPlanUkey(pPlanUKey);
			wShipPlanAltKey.updateStatusFlag(ShippingPlan.STATUS_FLAG_DELETE);
			wShipPlanAltKey.updateLastUpdatePname(pProcessName);
			wShipPlanHandler.modify(wShipPlanAltKey);

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
				// 引当数、在庫数を減算。在庫状態を完了に更新する
				wStockAltKey.KeyClear();
				wStockAltKey.setStockId(stock.getStockId());
				wStockAltKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				wStockAltKey.updateStockQty(stock.getStockQty() - workInfo[i].getPlanEnableQty());
				wStockAltKey.updateAllocationQty(stock.getAllocationQty() - workInfo[i].getPlanEnableQty());
				wStockAltKey.updateLastUpdatePname(pProcessName);
				wStockHandler.modify(wStockAltKey);

			}
			
			// クロスドックパッケージがあって、DC区分以外の場合、次作業情報を更新する
			if (isExistCrossDocPack && !workInfo[0].getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_DC))
			{
				// 該当次作業情報を検索・ロックする
				wNextKey.KeyClear();
				wNextKey.setShippingPlanUkey(pPlanUKey);
				NextProcessInfo[] nextProc = (NextProcessInfo[]) wNextHandler.findForUpdate(wNextKey);

				// 該当する次作業情報があった場合、次作業情報の削除を行う
				if (nextProc != null && nextProc.length > 0)
				{
					wNextAltKey.KeyClear();
					wNextAltKey.setShippingPlanUkey(pPlanUKey);
					// 出荷予定一意キー
					wNextAltKey.updateShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					// 作業実績数
					wNextAltKey.updateResultQty(0);
					// 作業欠品数
					wNextAltKey.updateShortageQty(0);
					// 最終更新処理名
					wNextAltKey.updateLastUpdatePname(pProcessName);
					// 次作業情報を更新する
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
	 * 入力または取り込んだ予定情報から、出荷予定情報、作業情報、在庫情報を登録します。<BR>
	 * 各パラメータの必須チェックなどは各画面で行ってください。<BR>
	 * 本メソッドは、以下の処理を行います。<BR>
	 * <BR>
	 *   <DIR>
	 *   <U>1.出荷予定情報(DnShippingPlan)の登録</U><BR>
	 *     ・入力データをもとに出荷予定情報を作成する。<BR>
	 *   <U>2.作業情報(DnWorkInfo)の登録</U><BR>
	 *     ・登録した出荷予定情報をもとに作業情報を作成する。<BR>
	 *   <U>3.在庫情報(DmStock)の登録(登録した作業情報分、登録を行う)</U><BR>
	 *     ・登録した作業情報をもとに在庫情報を登録する。<BR>
	 *   <U>4.次作業情報(DnNextProc)の登録する。</U><BR>
	 *     ・登録した出荷予定情報をもとに次作業情報を登録する。<BR>
	 *   </DIR>
	 * 設定情報の詳細は、ファイル遷移図を参照してください。<BR>
	 * <BR>
	 * ＜入力データ＞
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>更新値</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td></td><td>仕分予定日</td><td>：</td><td>PlanDate</td></tr>
	 *   <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
	 *   <tr><td></td><td>荷主名称</td><td>：</td><td>ConsignorName</td></tr>
	 *   <tr><td></td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
	 *   <tr><td></td><td>出荷先名称</td><td>：</td><td>CustomerName</td></tr>
	 *   <tr><td></td><td>伝票No.(出荷伝票No.)</td><td>：</td><td>ShippingTicketNo</td></tr>
	 *   <tr><td></td><td>行No.(出荷伝票行No.)</td><td>：</td><td>ShippigLineNo</td></tr>
	 *   <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
	 *   <tr><td></td><td>商品名称</td><td>：</td><td>ItemName</td></tr>
	 *   <tr><td></td><td>仕分予定数(総数)</td><td>：</td><td>TotalPlanQty</td></tr>
	 *   <tr><td></td><td>ケース入数</td><td>：</td><td>EnteringQty</td></tr>
	 *   <tr><td></td><td>ボール入数</td><td>：</td><td>BundleEnteringQty</td></tr>
	 *   <tr><td></td><td>ケースITF</td><td>：</td><td>ITF</td></tr>
	 *   <tr><td></td><td>ボールITF</td><td>：</td><td>BundleITF</td></tr>
	 *   <tr><td></td><td>ケース品仕分場所</td><td>：</td><td>CaseSortingLocation</td></tr>
	 *   <tr><td></td><td>ピース品仕分場所</td><td>：</td><td>PieceSortingLocation</td></tr>
	 *   <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
	 *   <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
	 *   <tr><td></td><td>仕入先名称</td><td>：</td><td>SupplierName</td></tr>
	 *   <tr><td></td><td>伝票No.(入荷伝票No.)</td><td>：</td><td>InstockTicketNo</td></tr>
	 *   <tr><td></td><td>行No.(入荷伝票行No.)</td><td>：</td><td>InstockLineNo</td></tr>
	 *   <tr><td></td><td>ケースピース区分</td><td>：</td><td>CasePieceFlag</td></tr>
	 *   <tr><td></td><td>バッチNo.</td><td>：</td><td>BatchNo</td></tr>
	 *   <tr><td></td><td>作業者コード</td><td>：</td><td>WorkerCode</td></tr>
	 *   <tr><td></td><td>作業者名称</td><td>：</td><td>WorkerName</td></tr>
	 *   <tr><td></td><td>端末No.</td><td>：</td><td>TerminalNumber</td></tr>
	 *   <tr><td></td><td>登録区分</td><td>：</td><td>RegistKbn</td></tr>
	 *   <tr><td></td><td>処理名</td><td>：</td><td>RegistPName</td></tr>
	 * </table>
	 * </DIR>
	 * 
	 * @param inputParam 出荷予定情報を作成するための情報を含む<CODE>ShippingParameter</CODE>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 */
	public void createShippingPlan(ShippingParameter inputParam) throws ReadWriteException, ScheduleException
	{
		// 入力データをもとに出荷予定情報を登録する
		ShippingPlan shipPlan = null;
		shipPlan = createShipping(inputParam);

		// 作業情報と在庫情報を登録する
		createStockWorkInfo(shipPlan, inputParam.getRegistPName());

		// クロスドックパッケージありの場合、次作業情報の登録または更新を行う
		if (isExistCrossDocPack && !shipPlan.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_DC))
		{
			createNextProc(shipPlan, inputParam.getRegistPName());
		}

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * システム定義表(WARENAVI_SYSTEM)を参照し、パッケージのありなしを取得します。<BR>
	 * 入荷、仕分、クロスドックが導入されているかをセットします。<BR>
	 * クロスドックパッケージありの場合trueを、なしの場合falseを返します。
	 * 入荷パッケージありの場合trueを、なしの場合falseを返します。
	 * 仕分パッケージありの場合trueを、なしの場合falseを返します。
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 */
	private void setAddOnPackage(Connection conn) throws ReadWriteException,ScheduleException
	{
		WareNaviSystem wms = new WareNaviSystem();

		WareNaviSystemSearchKey wmsKey = new WareNaviSystemSearchKey();
		WareNaviSystemHandler wmsHandler = new WareNaviSystemHandler(conn);

		try
		{
			wms = (WareNaviSystem) wmsHandler.findPrimary(wmsKey);
			
			if (wms == null)
			{
				//6006002 = データベースエラーが発生しました。{0}
				RmiMsgLogClient.write(6006039, LogMessage.F_ERROR, "ShippingPlanOperator.setAddOnPackage", null);
				throw (new ReadWriteException("6007039" + wDelim + "WARENAVI_SYSTEM"));
			}

			// クロスドックパッケージが導入されているか
			if (super.isCrossDockPack(conn))
				isExistCrossDocPack = true;

			// 入荷パッケージが導入されているか
			if (super.isInstockPack(conn))
				isExistInstockPack = true;

			// 仕分パッケージが導入されているか
			if (super.isSortingPack(conn))
				isExistPickingPack = true;

		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
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
			RmiMsgLogClient.write("6006023" + wDelim + "DnSortingPlan", this.getClass().getName());
			throw new ScheduleException("6006023" + wDelim + "DnSortingPlan");
		}
		// 文字列が禁止文字を含んでいる場合はExceptionをなげる
		if (str.indexOf(WmsParam.PATTERNMATCHING) != -1)
		{
			// ログを落とす
			// 6003106={0}にシステムで使用できない文字が含まれています
			RmiMsgLogClient.write("6003106" + wDelim + str, this.getClass().getName());
			throw new ScheduleException("6003106" + wDelim + str);
		}
	}

	/**
	 * 入力情報より出荷予定情報を作成します。
	 * 
	 * @param param 入力情報
	 * @return 作成したに出荷予定クラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected ShippingPlan createShipping(ShippingParameter param) throws ReadWriteException
	{
		ShippingPlan shipPlan = new ShippingPlan();

		try
		{
			String planUkey_seqno = wSequenceHandler.nextShippingPlanKey();

			// 出荷予定一意キー	
			shipPlan.setShippingPlanUkey(planUkey_seqno);
			// 状態フラグ
			shipPlan.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);
			// 出荷予定日
			shipPlan.setPlanDate(param.getPlanDate());
			// 荷主コード
			shipPlan.setConsignorCode(param.getConsignorCode());
			// 荷主名称
			shipPlan.setConsignorName(param.getConsignorName());
			// 出荷先コード
			shipPlan.setCustomerCode(param.getCustomerCode());
			// 出荷先名称
			shipPlan.setCustomerName1(param.getCustomerName());
			// 出荷伝票No.
			shipPlan.setShippingTicketNo(param.getShippingTicketNo());
			// 出荷伝票行No.
			shipPlan.setShippingLineNo(param.getShippingLineNo());
			// 商品コード
			shipPlan.setItemCode(param.getItemCode());
			// 商品名称
			shipPlan.setItemName1(param.getItemName());
			// 出荷予定数
			shipPlan.setPlanQty(param.getTotalPlanQty());
			// 出荷実績数
			shipPlan.setResultQty(0);
			// 出荷欠品数
			shipPlan.setShortageCnt(0);
			// ケース入数
			shipPlan.setEnteringQty(param.getEnteringQty());
			// ボール入数
			shipPlan.setBundleEnteringQty(param.getBundleEnteringQty());
			// ケース/ピース区分
			shipPlan.setCasePieceFlag(ShippingPlan.CASEPIECE_FLAG_NOTHING);
			// ケースITF
			shipPlan.setItf(param.getITF());
			// ボールITF
			shipPlan.setBundleItf(param.getBundleITF());
			// TC/DC区分
			if (param.getTcdcFlag().equals(ShippingParameter.TCDC_FLAG_DC))
			{
				shipPlan.setTcdcFlag(ShippingPlan.TCDC_FLAG_DC);
			}
			else if (param.getTcdcFlag().equals(ShippingParameter.TCDC_FLAG_CROSSTC))
			{
				shipPlan.setTcdcFlag(ShippingPlan.TCDC_FLAG_CROSSTC);
			}
			else
			{
				shipPlan.setTcdcFlag(ShippingPlan.TCDC_FLAG_TC);
			}
			// 仕入先コード
			shipPlan.setSupplierCode(param.getSupplierCode());
			// 仕入先名称
			shipPlan.setSupplierName1(param.getSupplierName());
			// 入荷伝票No.
			shipPlan.setInstockTicketNo(param.getInstockTicketNo());
			// 入荷伝票行No.
			shipPlan.setInstockLineNo(param.getInstockLineNo());
			// バッチNo.(スケジュールNo.)
			shipPlan.setBatchNo(param.getBatchNo());
			// 作業者コード
			shipPlan.setWorkerCode(param.getWorkerCode());
			// 作業者名
			shipPlan.setWorkerName(param.getWorkerName());
			// 端末No.RFTNo.
			shipPlan.setTerminalNo(param.getTerminalNumber());
			// 登録区分
			shipPlan.setRegistKind(param.getRegistKbn());
			// 登録処理名
			shipPlan.setRegistPname(param.getRegistPName());
			// 最終更新処理名
			shipPlan.setLastUpdatePname(param.getRegistPName());

			// 出荷予定情報の登録
			wShipPlanHandler.create(shipPlan);

		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return shipPlan;
	}

	/**
	 * 出荷予定情報から作業情報と在庫情報を作成する。<BR>
	 * 
	 * @param pShipPlan 作成した出荷予定情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createStockWorkInfo(ShippingPlan pShipPlan, String pProcessName) throws ReadWriteException
	{
		// 作業情報を作成する
		WorkingInformation workInfo = createWorkInfo(pShipPlan, pProcessName);

		// 在庫情報を作成する
		createStock(workInfo, pProcessName);

	}

	/**
	 * 出荷予定情報から作業情報を作成します。<BR>
	 * 
	 * @param pShipPlan 出荷予定情報
	 * @param pProcessName 処理名
	 * @return 登録した作業情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected WorkingInformation createWorkInfo(ShippingPlan pShipPlan, String pProcessName) throws ReadWriteException
	{
		WorkingInformation workInfo = new WorkingInformation();

		try
		{
			String job_seqno = wSequenceHandler.nextJobNo();
			String stockId_seqno  = wSequenceHandler.nextStockId();

			// 作業No.
			workInfo.setJobNo(job_seqno);
			// 作業区分
			workInfo.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
			// 集約作業No.
			workInfo.setCollectJobNo(job_seqno);
			// 状態フラグ：未開始
			workInfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			// クロスドックパッケージありで、DC区分以外の場合、未開始で登録する
			if (isExistCrossDocPack && !pShipPlan.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_DC))
			{
				// 作業開始フラグ：未開始
				workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_NOT_STARTED);
			}
			else
			{
				// 作業開始フラグ：開始済
				workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			}
			// 予定一意キー
			workInfo.setPlanUkey(pShipPlan.getShippingPlanUkey());
			// 在庫ID
			workInfo.setStockId(stockId_seqno);
			// 予定日
			workInfo.setPlanDate(pShipPlan.getPlanDate());
			// 荷主コード
			workInfo.setConsignorCode(pShipPlan.getConsignorCode());
			// 荷主名称
			workInfo.setConsignorName(pShipPlan.getConsignorName());
			// 仕入先コード
			workInfo.setSupplierCode(pShipPlan.getSupplierCode());
			// 仕入先名称
			workInfo.setSupplierName1(pShipPlan.getSupplierName1());
			// 入荷伝票No.
			workInfo.setInstockTicketNo(pShipPlan.getInstockTicketNo());
			// 入荷伝票行No.
			workInfo.setInstockLineNo(pShipPlan.getInstockLineNo());
			// 出荷先コード
			workInfo.setCustomerCode(pShipPlan.getCustomerCode());
			// 出荷先名称
			workInfo.setCustomerName1(pShipPlan.getCustomerName1());
			// 出荷伝票No.
			workInfo.setShippingTicketNo(pShipPlan.getShippingTicketNo());
			// 出荷伝票行No.
			workInfo.setShippingLineNo(pShipPlan.getShippingLineNo());
			// 商品コード
			workInfo.setItemCode(pShipPlan.getItemCode());
			// 商品名称
			workInfo.setItemName1(pShipPlan.getItemName1());
			// 作業予定数（ホスト予定数）
			workInfo.setHostPlanQty(pShipPlan.getPlanQty());
			// 作業予定数
			workInfo.setPlanQty(pShipPlan.getPlanQty());
			// 作業可能数
			workInfo.setPlanEnableQty(pShipPlan.getPlanQty());
			// 作業実績数
			workInfo.setResultQty(0);
			// 作業欠品数
			workInfo.setShortageCnt(0);
			// ケース入数
			workInfo.setEnteringQty(pShipPlan.getEnteringQty());
			// ボール入数
			workInfo.setBundleEnteringQty(pShipPlan.getBundleEnteringQty());
			// ケース/ピース区分（荷姿）
			workInfo.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			// ケース/ピース区分（作業形態）
			workInfo.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			// ケースITF
			workInfo.setItf(pShipPlan.getItf());
			// ボールITF
			workInfo.setBundleItf(pShipPlan.getBundleItf());
			// TC/DC区分
			workInfo.setTcdcFlag(pShipPlan.getTcdcFlag());
			// 賞味期限
			workInfo.setUseByDate(pShipPlan.getUseByDate());
			// ロットNo.
			workInfo.setLotNo(pShipPlan.getLotNo());
			// 予定情報コメント
			workInfo.setPlanInformation(pShipPlan.getPlanInformation());
			// 発注日
			workInfo.setOrderingDate(pShipPlan.getOrderingDate());
			// 未作業報告フラグ
			workInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			// バッチNo.
			workInfo.setBatchNo(pShipPlan.getBatchNo());
			// 予定情報登録日時
			workInfo.setPlanRegistDate(new Date());
			// 登録処理名
			workInfo.setRegistPname(pProcessName);
			// 最終更新処理名
			workInfo.setLastUpdatePname(pProcessName);

			// 作業情報の登録
			wWorkHandler.create(workInfo);

		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return workInfo;
	}

	/**
	 * 作成した作業情報をもとに在庫情報を作成します。
	 * 
	 * @param workInfo 作業情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void createStock(WorkingInformation workInfo, String pProcessName) throws ReadWriteException
	{
		try
		{
			Stock stock = new Stock();

			// 在庫ID
			stock.setStockId(workInfo.getStockId());
			// 一意キー
			stock.setPlanUkey(workInfo.getPlanUkey());
			// エリアNo.
			stock.setAreaNo("");
			// ロケーションNo.
			stock.setLocationNo("");
			// 商品コード
			stock.setItemCode(workInfo.getItemCode());
			// 商品名称
			stock.setItemName1(workInfo.getItemName1());
			// 在庫ステータス
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_SHIPPING);
			// 在庫数
			stock.setStockQty(workInfo.getPlanQty());
			// 引当数
			stock.setAllocationQty(workInfo.getPlanQty());
			// 予定数
			stock.setPlanQty(0);
			// ケース/ピース区分(荷姿)
			stock.setCasePieceFlag(workInfo.getCasePieceFlag());
			// 入庫日
			stock.setInstockDate(null);
			// 最終出庫日
			stock.setLastShippingDate("");
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
	 * 作成した出荷予定情報より、次作業情報の登録を行います。<BR>
	 * 
	 * @param pShipPlan 出荷予定情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected void createNextProc(ShippingPlan pShipPlan, String pProcessName) throws ReadWriteException, ScheduleException
	{
		// 画面入力がTC区分だった場合
		if (pShipPlan.getTcdcFlag().equals(ShippingPlan.TCDC_FLAG_TC))
		{
			// TC用の入荷->出荷データの登録を行う
			createTcNextProc(pShipPlan, pProcessName);
			
		}
		
		// 画面入力がクロス区分だった場合
		else
		{
			// クロス用の入荷->仕分⇒出荷、仕分->出荷データの登録を行う
			createCrossNextProc(pShipPlan, pProcessName);
		}

	}
	
	/**
	 * 画面入力からTC区分の出荷予定情報が登録された場合に次作業情報を作成するためのクラスです。<BR>
	 * 入荷→出荷の次作業情報を作成します。<BR>
	 * 入荷パッケージが導入されていない場合、ScheduleExceptionを通知する。
	 * 
	 * @param pShipPlan 画面入力情報より作成された出荷予定情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 */
	private void createTcNextProc(ShippingPlan pShipPlan, String pProcessName) throws ReadWriteException, ScheduleException
	{
		// 入荷パッケージが導入されていない場合、登録を行わない
		if (!isExistInstockPack)
		{
			// 6006021=内部エラーが発生しました。{0}
			RmiMsgLogClient.write(6006021, LogMessage.F_ERROR, "WareNaviSystem", null);
			throw new ScheduleException("6006021" + wDelim + "WareNaviSystem");
		}
		
		try
		{
			// 次作業情報(DnNextProc)から入荷->出荷の同一情報を検索する
			// 検索条件を設定する
			wNextKey.KeyClear();
			// 自作業区分：入荷
			wNextKey.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
			// 荷主コード
			wNextKey.setConsignorCode(pShipPlan.getConsignorCode());
			// 商品コード
			wNextKey.setItemCode(pShipPlan.getItemCode());
			// TC/DC区分
			wNextKey.setTcdcFlag(pShipPlan.getTcdcFlag());
			// 仕入先コード
			wNextKey.setSupplierCode(pShipPlan.getSupplierCode());
			// 入荷伝票No.
			wNextKey.setInstockTicketNo(pShipPlan.getInstockTicketNo());
			// 入荷伝票行No.
			wNextKey.setInstockLineNo(pShipPlan.getInstockLineNo());
			// 出荷予定日
			wNextKey.setShipPlanDate(pShipPlan.getPlanDate());
			// 出荷先コード
			wNextKey.setCustomerCode(pShipPlan.getCustomerCode());
			// 出荷伝票No.
			wNextKey.setShippingTicketNo(pShipPlan.getShippingTicketNo());
			// 出荷伝票行No.
			wNextKey.setShippingLineNo(pShipPlan.getShippingLineNo());
			// 検索結果を取得
			NextProcessInfo wNextInfo = (NextProcessInfo) wNextHandler.findPrimaryForUpdate(wNextKey);
			
			// 入荷予定情報の検索を行う
			wInstPlanKey.KeyClear();
			// 検索条件をセットする
			// 荷主コード
			wInstPlanKey.setConsignorCode(pShipPlan.getConsignorCode());
			// 予定日
			wInstPlanKey.setPlanDate(pShipPlan.getPlanDate());
			// 仕入先コード
			wInstPlanKey.setSupplierCode(pShipPlan.getSupplierCode());
			// 入荷伝票No.
			wInstPlanKey.setInstockTicketNo(pShipPlan.getInstockTicketNo());
			// 入荷伝票行No.
			wInstPlanKey.setInstockLineNo(pShipPlan.getInstockLineNo());
			// 出荷先コード
			wInstPlanKey.setCustomerCode(pShipPlan.getCustomerCode());
			// 商品コード
			wInstPlanKey.setItemCode(pShipPlan.getItemCode());
			// TC/DC区分
			wInstPlanKey.setTcdcFlag(pShipPlan.getTcdcFlag());
			// 作業状態：削除以外
			wInstPlanKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			// 検索を行う
			InstockPlan instPlan = null;
			instPlan = (InstockPlan) wInstPlanHandler.findPrimary(wInstPlanKey);
				
			// 同一の次作業情報が既に存在する場合更新を行う
			if (wNextInfo != null)
			{
				// 次作業情報が未処理の場合のみ更新を行う
				if (wNextInfo.getStatusFlag().equals(NextProcessInfo.STATUS_FLAG_UNPROCESSING))
				{
					// 入荷予定情報が存在しない、または未開始の場合のみ次作業情報の更新を行う
					if (instPlan == null 
					|| (instPlan != null && instPlan.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART)))
					{
						// 更新条件を設定する
						wNextAltKey.KeyClear();
						// 次作業情報一意キー
						wNextAltKey.setNextProcUkey(wNextInfo.getNextProcUkey());
				
						// 更新値を設定する
						// 出荷予定一意キー
						wNextAltKey.updateShippingPlanUkey(pShipPlan.getShippingPlanUkey());
						// 作業予定数
						wNextAltKey.updatePlanQty(pShipPlan.getPlanQty());
						// 最終更新処理名
						wNextAltKey.updateLastUpdatePname(pProcessName);
		
						// 次作業情報を更新する
						wNextHandler.modify(wNextAltKey);
					}
					// 入荷予定情報が未開始以外の場合は、作業情報の作業フラグを開始済に更新する。
					else
					{
						updateBeginningFlag(pShipPlan.getShippingPlanUkey(), WorkingInformation.BEGINNING_FLAG_STARTED);
					}
				}
				// 未開始でない場合、今回作成した出荷の作業情報を開始済に更新する
				else
				{
					updateBeginningFlag(pShipPlan.getShippingPlanUkey(), WorkingInformation.BEGINNING_FLAG_STARTED);
				}
				
			}
			// 同一の次作業情報が存在しない場合、新規に作成する
			else
			{				
				//次作業情報を作成する
				NextProcessInfo nextProc = new NextProcessInfo();
                //次作業情報 シーキエンス
                String nextUkey_seqno = wSequenceHandler.nextNextProcKey();
				//次作業一意キー
				nextProc.setNextProcUkey(nextUkey_seqno);
				//予定一意キー：入荷
				nextProc.setPlanUkey(null);
				//出荷予定一意キー
				nextProc.setShippingPlanUkey(pShipPlan.getShippingPlanUkey());
				//自作業区分：入荷
				nextProc.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
				//作業予定数：出荷予定数
				nextProc.setPlanQty(pShipPlan.getPlanQty());
				//作業実績数：出荷実績数
				nextProc.setResultQty(pShipPlan.getResultQty());
				//状態フラグ
				nextProc.setStatusFlag(NextProcessInfo.STATUS_FLAG_UNPROCESSING);
				//荷主コード
				nextProc.setConsignorCode(pShipPlan.getConsignorCode());
				//仕入先コード
				nextProc.setSupplierCode(pShipPlan.getSupplierCode());
				//入荷伝票No.
				nextProc.setInstockTicketNo(pShipPlan.getInstockTicketNo());
				//入荷伝票行No.
				nextProc.setInstockLineNo(pShipPlan.getInstockLineNo());
				//商品コード
				nextProc.setItemCode(pShipPlan.getItemCode());
				//TC/DC区分
				nextProc.setTcdcFlag(pShipPlan.getTcdcFlag());
				//出荷予定日
				nextProc.setShipPlanDate(pShipPlan.getPlanDate());
				//出荷先コード
				nextProc.setCustomerCode(pShipPlan.getCustomerCode());
				//出荷伝票No.
				nextProc.setShippingTicketNo(pShipPlan.getShippingTicketNo());
				//出荷伝票行No.
				nextProc.setShippingLineNo(pShipPlan.getShippingLineNo());
				//登録日時
				nextProc.setRegistDate(new Date());
				//登録処理名
				nextProc.setRegistPname(pProcessName);
				//最終更新処理名
				nextProc.setLastUpdatePname(pProcessName);
				
				// 入荷予定情報に一致する情報があった場合
				if (instPlan != null)
				{
					// 入荷予定情報が未開始ならば予定一意キーをセットする
					if (instPlan.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART))
					{
						// 予定一意キー：入荷予定情報の一意キー
						nextProc.setPlanUkey(instPlan.getInstockPlanUkey());
					}
					// 入荷予定情報が未開始以外ならば
					// 次作業情報の予定一意キーにダミーをセット、
					// 今回作成した出荷の作業情報を開始済に更新する
					else
					{
						// 予定一意キー：ダミー
						nextProc.setPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						
						// 出荷の作業情報の更新を行う
						updateBeginningFlag(pShipPlan.getShippingPlanUkey(), WorkingInformation.BEGINNING_FLAG_STARTED);
					}
					
					// 入荷予定日
					nextProc.setInstPlanDate(pShipPlan.getPlanDate());
					
				}
				// 一致する情報がなかった場合は、予定一意キーにダミーをセットする
				else
				{
					// 予定一意キー：ダミー
					nextProc.setPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					// 入荷予定日
					nextProc.setInstPlanDate(null);
				}
				
				// 次作業情報を作成する
				wNextHandler.create(nextProc);
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
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		
	}
	
	/**
	 * クロス用の次作業情報を作成するためのクラスです。<BR>
	 * 入荷パッケージがある場合、入荷->仕分->出荷、仕分->出荷の次作業情報を、
	 * 入荷パッケージがない場合、仕分->出荷の次作業情報を作成します。<BR>
	 * 仕分パッケージが導入されていない場合、ScheduleExceptionを通知する。
	 * 
	 * @param pShipPlan 今回登録された出荷予定情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。 
	 */
	private void createCrossNextProc(ShippingPlan pShipPlan, String pProcessName) throws ReadWriteException, ScheduleException
	{
		// 仕分パッケージがない場合、登録を行わない
		if (!isExistPickingPack)
		{
			// 6026007=パッケージの不整合が発生しました。
			RmiMsgLogClient.write(6026007, LogMessage.F_ERROR, "WareNaviSystem", null);
			// 6006021=内部エラーが発生しました。{0}
			throw new ScheduleException("6006021" + wDelim + "WareNaviSystem");
		}
		
		try
		{
			// 仕分予定情報の検索を行う
			wSortPlanKey.KeyClear();
			// 荷主コード
			wSortPlanKey.setConsignorCode(pShipPlan.getConsignorCode());
			// 予定日
			wSortPlanKey.setPlanDate(pShipPlan.getPlanDate());
			// 仕入先コード
			wSortPlanKey.setSupplierCode(pShipPlan.getSupplierCode());
			// 入荷伝票No.
			wSortPlanKey.setInstockTicketNo(pShipPlan.getInstockTicketNo());
			// 入荷伝票行No.
			wSortPlanKey.setInstockLineNo(pShipPlan.getInstockLineNo());
			// 出荷先コード
			wSortPlanKey.setCustomerCode(pShipPlan.getCustomerCode());
			// 出荷伝票No.
			wSortPlanKey.setShippingTicketNo(pShipPlan.getShippingTicketNo());
			// 出荷伝票行No.
			wSortPlanKey.setShippingLineNo(pShipPlan.getShippingLineNo());
			// 商品コード
			wSortPlanKey.setItemCode(pShipPlan.getItemCode());
			// TC/DC区分
			wSortPlanKey.setTcdcFlag(pShipPlan.getTcdcFlag());
			// 作業状態：削除以外
			wSortPlanKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
			// 仕分予定情報を検索する
			SortingPlan sortPlan = null;
			sortPlan = (SortingPlan) wSortPlanHandler.findPrimary(wSortPlanKey);

			// 入荷パッケージありの場合、入荷->仕分->出荷の次作業情報を作成する
			if (isExistInstockPack)
			{
				// 入荷予定情報の検索を行う
				wInstPlanKey.KeyClear();
				// 荷主コード
				wInstPlanKey.setConsignorCode(pShipPlan.getConsignorCode());
				// 予定日
				wInstPlanKey.setPlanDate(pShipPlan.getPlanDate());
				// 仕入先コード
				wInstPlanKey.setSupplierCode(pShipPlan.getSupplierCode());
				// 入荷伝票No.
				wInstPlanKey.setInstockTicketNo(pShipPlan.getInstockTicketNo());
				// 入荷伝票行No.
				wInstPlanKey.setInstockLineNo(pShipPlan.getInstockLineNo());
				// 商品コード
				wInstPlanKey.setItemCode(pShipPlan.getItemCode());
				// TC/DC区分
				wInstPlanKey.setTcdcFlag(pShipPlan.getTcdcFlag());
				// 作業状態：削除以外
				wInstPlanKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
				// 入荷予定情報を検索する
				InstockPlan instPlan = null;
				instPlan = (InstockPlan) wInstPlanHandler.findPrimary(wInstPlanKey);
			
				// 入荷->仕分->出荷次作業情報を作成する
				createCrossNextProc(pShipPlan, instPlan, sortPlan, pProcessName);
			
			}
			
			// 仕分->出荷の次作業情報を作成する
			createCrossNextProc(pShipPlan, sortPlan, pProcessName);

		}
		catch(NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
	}
	
	/**
	 * 入荷->仕分->出荷の次作業情報を作成する。
	 * 
	 * @param pShipPlan 今回登録した出荷予定情報
	 * @param pInstPlan 今回登録した出荷予定情報に紐づく入荷予定情報
	 * @param pSortPlan 今回登録した出荷予定情報に紐づく仕分予定情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void createCrossNextProc(ShippingPlan pShipPlan, InstockPlan pInstPlan, SortingPlan pSortPlan, String pProcessName) throws ReadWriteException
	{
		NextProcessInfo nextProc = new NextProcessInfo();

		try
		{
			// 同一の次作業情報が存在するか検索する
			// 検索条件を設定する
			wNextKey.KeyClear();
			// 自作業区分：入荷
			wNextKey.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
			// 荷主コード
			wNextKey.setConsignorCode(pShipPlan.getConsignorCode());
			// 商品コード
			wNextKey.setItemCode(pShipPlan.getItemCode());
			// TC/DC区分
			wNextKey.setTcdcFlag(pShipPlan.getTcdcFlag());
			// 仕入先コード
			wNextKey.setSupplierCode(pShipPlan.getSupplierCode());
			// 入荷伝票No.
			wNextKey.setInstockTicketNo(pShipPlan.getInstockTicketNo());
			// 入荷伝票行No.
			wNextKey.setInstockLineNo(pShipPlan.getInstockLineNo());
			// 出荷予定日
			wNextKey.setShipPlanDate(pShipPlan.getPlanDate());
			// 出荷先コード
			wNextKey.setCustomerCode(pShipPlan.getCustomerCode());
			// 出荷伝票No.
			wNextKey.setShippingTicketNo(pShipPlan.getShippingTicketNo());
			// 出荷伝票行No.
			wNextKey.setShippingLineNo(pShipPlan.getShippingLineNo());
			// 次作業情報の検索結果を取得
			NextProcessInfo wNextInfo = (NextProcessInfo) wNextHandler.findPrimaryForUpdate(wNextKey);
			
			// 同一情報の次作業情報が存在する場合更新を行う
			if (wNextInfo != null)
			{
				// 次作業情報が未開始の場合のみ紐付けを行う
				if (wNextInfo.getStatusFlag().equals(NextProcessInfo.STATUS_FLAG_UNPROCESSING))
				{
					// 紐づく入荷予定情報が存在しない、または未開始の場合のみ紐付けを行う
					if (pInstPlan == null 
					|| (pInstPlan != null && pInstPlan.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART)))
					{
						// 更新条件を設定する
						wNextAltKey.KeyClear();
						// 次作業情報一意キー
						wNextAltKey.setNextProcUkey(wNextInfo.getNextProcUkey());
				
						// 更新値を設定する
						// 出荷予定一意キー
						wNextAltKey.updateShippingPlanUkey(pShipPlan.getShippingPlanUkey());
						// 最終更新処理名
						wNextAltKey.updateLastUpdatePname(pProcessName);
		
						// 次作業情報を更新する
						wNextHandler.modify(wNextAltKey);
					}
				}
				
			}
			// 同一情報の次作業情報が存在しない場合、新規に作成する
			else
			{
			
                //次作業シーキエンス
                String nextUkey_seqno = wSequenceHandler.nextNextProcKey();
				// 次作業一意キー
				nextProc.setNextProcUkey(nextUkey_seqno);
				// 予定一意キー：入荷
				nextProc.setPlanUkey(null);
				// 仕分予定一意キー
				nextProc.setSortingPlanUkey(null);
				// 出荷予定一意キー
				nextProc.setShippingPlanUkey(pShipPlan.getShippingPlanUkey());
				// 自作業区分：入荷
				nextProc.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
				// 作業予定数：仕分予定数
				nextProc.setPlanQty(pShipPlan.getPlanQty());
				// 作業実績数：仕分実績数
				nextProc.setResultQty(pShipPlan.getResultQty());
				// 状態フラグ：未開始
				nextProc.setStatusFlag(NextProcessInfo.STATUS_FLAG_UNPROCESSING);
				// 荷主コード
				nextProc.setConsignorCode(pShipPlan.getConsignorCode());
				// 仕入先コード
				nextProc.setSupplierCode(pShipPlan.getSupplierCode());
				// 入荷伝票No.
				nextProc.setInstockTicketNo(pShipPlan.getInstockTicketNo());
				// 入荷伝票行No.
				nextProc.setInstockLineNo(pShipPlan.getInstockLineNo());
				// 商品コード
				nextProc.setItemCode(pShipPlan.getItemCode());
				// TC/DC区分
				nextProc.setTcdcFlag(pShipPlan.getTcdcFlag());
				// 出荷予定日
				nextProc.setShipPlanDate(pShipPlan.getPlanDate());
				// 出荷先コード
				nextProc.setCustomerCode(pShipPlan.getCustomerCode());
				// 出荷伝票No.
				nextProc.setShippingTicketNo(pShipPlan.getShippingTicketNo());
				// 出荷伝票行No.
				nextProc.setShippingLineNo(pShipPlan.getShippingLineNo());
				// 登録日時
				nextProc.setRegistDate(new Date());
				// 登録処理名
				nextProc.setRegistPname(pProcessName);
				// 最終更新処理名
				nextProc.setLastUpdatePname(pProcessName);
				
				// 入荷予定情報がある場合、情報をセットする
				if (pInstPlan != null && pInstPlan.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART))
				{
					// 予定一意キー：入荷の予定一意キー
					nextProc.setPlanUkey(pInstPlan.getInstockPlanUkey());
					// 入荷予定日
					nextProc.setInstPlanDate(pShipPlan.getPlanDate());
				}
				else
				{
					// 予定一意キー：ダミー
					nextProc.setPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					// 入荷予定日
					nextProc.setInstPlanDate(null);
				}
				
				// 仕分予定情報がある場合、情報をセットする
				if (pSortPlan != null && pSortPlan.getStatusFlag().equals(SortingPlan.STATUS_FLAG_UNSTART))
				{
					// 仕分予定一意キー：仕分の予定一意キー
					nextProc.setSortingPlanUkey(pSortPlan.getSortingPlanUkey());
				}
				else
				{
					// 仕分予定一意キー：ダミー
					nextProc.setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
				}
				
				// 次作業情報を作成する
				wNextHandler.create(nextProc);
				
			}
			
		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}		
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		
	}
	
	/**
	 * 仕分->出荷の次作業情報を作成します。
	 * 
	 * @param pShipPlan 今回登録した出荷予定情報
	 * @param pSortPlan 今回登録した出荷予定情報に紐づく仕分予定情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void createCrossNextProc(ShippingPlan pShipPlan, SortingPlan pSortPlan, String pProcessName) throws ReadWriteException
	{
		NextProcessInfo nextProc = new NextProcessInfo();

		try
		{
			// 同一の次作業情報が存在するか検索する
			// 検索条件を設定する
			wNextKey.KeyClear();
			// 自作業区分：仕分
			wNextKey.setWorkKind(NextProcessInfo.JOB_TYPE_SORTING);
			// 荷主コード
			wNextKey.setConsignorCode(pShipPlan.getConsignorCode());
			// 商品コード
			wNextKey.setItemCode(pShipPlan.getItemCode());
			// TC/DC区分
			wNextKey.setTcdcFlag(pShipPlan.getTcdcFlag());
			// 仕入先コード
			wNextKey.setSupplierCode(pShipPlan.getSupplierCode());
			// 入荷伝票No.
			wNextKey.setInstockTicketNo(pShipPlan.getInstockTicketNo());
			// 入荷伝票行No.
			wNextKey.setInstockLineNo(pShipPlan.getInstockLineNo());
			// 出荷予定日
			wNextKey.setShipPlanDate(pShipPlan.getPlanDate());
			// 出荷先コード
			wNextKey.setCustomerCode(pShipPlan.getCustomerCode());
			// 出荷伝票No.
			wNextKey.setShippingTicketNo(pShipPlan.getShippingTicketNo());
			// 出荷伝票行No.
			wNextKey.setShippingLineNo(pShipPlan.getShippingLineNo());
			// 検索結果を取得
			NextProcessInfo wNextInfo = (NextProcessInfo) wNextHandler.findPrimaryForUpdate(wNextKey);
			
			// 同一条件の次作業情報が存在する場合
			if (wNextInfo != null)
			{
				// 次作業情報が未開始の場合、更新を行う
				if (wNextInfo.getStatusFlag().equals(NextProcessInfo.STATUS_FLAG_UNPROCESSING))
				{
					// 仕分予定情報が未開始の場合にのみ更新を行う
					if (pSortPlan == null
					|| (pSortPlan != null && pSortPlan.getStatusFlag().equals(SortingPlan.STATUS_FLAG_UNSTART)))
					{
						// 更新条件を設定する
						wNextAltKey.KeyClear();
						// 次作業情報一意キー
						wNextAltKey.setNextProcUkey(wNextInfo.getNextProcUkey());
				
						// 更新値を設定する
						// 出荷予定一意キー
						wNextAltKey.updateShippingPlanUkey(pShipPlan.getShippingPlanUkey());
					
						// 作業予定数
						wNextAltKey.updatePlanQty(pShipPlan.getPlanQty());
	
						// 最終更新処理名
						wNextAltKey.updateLastUpdatePname(pProcessName);
		
						// 次作業情報を更新する
						wNextHandler.modify(wNextAltKey);
					}
					else
					{
						// 出荷の作業情報を開始済に更新する
						updateBeginningFlag(pShipPlan.getShippingPlanUkey(), WorkingInformation.BEGINNING_FLAG_STARTED);
					}
				}
				// 未開始でない場合、出荷の作業情報を開始済に更新する
				else
				{
					updateBeginningFlag(pShipPlan.getShippingPlanUkey(), WorkingInformation.BEGINNING_FLAG_STARTED);
				}
				
			}
			else
			{
				//次作業シーキエンス
                String nextUkey_seqno = wSequenceHandler.nextNextProcKey();
				// 次作業一意キー
				nextProc.setNextProcUkey(nextUkey_seqno);
				// 予定一意キー：仕分
				nextProc.setPlanUkey(null);
				// 出荷予定一意キー
				nextProc.setShippingPlanUkey(pShipPlan.getShippingPlanUkey());
				// 自作業区分：仕分
				nextProc.setWorkKind(NextProcessInfo.JOB_TYPE_SORTING);
				// 作業予定数：出荷予定数
				nextProc.setPlanQty(pShipPlan.getPlanQty());
				// 作業実績数：出荷実績数
				nextProc.setResultQty(pShipPlan.getResultQty());
				// 状態フラグ：未開始
				nextProc.setStatusFlag(NextProcessInfo.STATUS_FLAG_UNPROCESSING);
				// 荷主コード
				nextProc.setConsignorCode(pShipPlan.getConsignorCode());
				// 仕入先コード
				nextProc.setSupplierCode(pShipPlan.getSupplierCode());
				// 入荷伝票No.
				nextProc.setInstockTicketNo(pShipPlan.getInstockTicketNo());
				// 入荷伝票行No.
				nextProc.setInstockLineNo(pShipPlan.getInstockLineNo());
				// 商品コード
				nextProc.setItemCode(pShipPlan.getItemCode());
				// TC/DC区分
				nextProc.setTcdcFlag(pShipPlan.getTcdcFlag());
				// 出荷予定日
				nextProc.setShipPlanDate(pShipPlan.getPlanDate());
				// 出荷先コード
				nextProc.setCustomerCode(pShipPlan.getCustomerCode());
				// 出荷伝票No.
				nextProc.setShippingTicketNo(pShipPlan.getShippingTicketNo());
				// 出荷伝票行No.
				nextProc.setShippingLineNo(pShipPlan.getShippingLineNo());
				// 登録日時
				nextProc.setRegistDate(new Date());
				// 登録処理名
				nextProc.setRegistPname(pProcessName);
				// 最終更新処理名
				nextProc.setLastUpdatePname(pProcessName);
				
				// 入荷予定情報がある場合、情報をセットする
				wInstPlanKey.KeyClear();
				// 荷主コード
				wInstPlanKey.setConsignorCode(pShipPlan.getConsignorCode());
				// 予定日
				wInstPlanKey.setPlanDate(pShipPlan.getPlanDate());
				// 仕入先コード
				wInstPlanKey.setSupplierCode(pShipPlan.getSupplierCode());
				// 入荷伝票No.
				wInstPlanKey.setInstockTicketNo(pShipPlan.getInstockTicketNo());
				// 入荷伝票行No.
				wInstPlanKey.setInstockLineNo(pShipPlan.getInstockLineNo());
				// 商品コード
				wInstPlanKey.setItemCode(pShipPlan.getItemCode());
				// TC/DC区分
				wInstPlanKey.setTcdcFlag(pShipPlan.getTcdcFlag());
				// 作業状態：削除以外
				wInstPlanKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
				// 入荷予定情報を検索する
				InstockPlan instPlan = null;
				instPlan = (InstockPlan) wInstPlanHandler.findPrimary(wInstPlanKey);
				if (instPlan != null)
				{
					// 入荷予定日
					nextProc.setInstPlanDate(pShipPlan.getPlanDate());
				}
				else
				{
					// 入荷予定日
					nextProc.setInstPlanDate(null);
				}
				
				
				// 仕分予定情報がある場合、情報をセットする
				if (pSortPlan != null)
				{
					// 仕分予定情報が未開始の場合
					if (pSortPlan.getStatusFlag().equals(SortingPlan.STATUS_FLAG_UNSTART))
					{
						// 予定一意キー：仕分情報の予定一意キー
						nextProc.setPlanUkey(pSortPlan.getSortingPlanUkey());
					}
					// 仕分予定情報が未開始以外ならば
					// 次作業情報の予定一意キーにダミーをセット、
					// 今回作成した出荷の作業情報を開始済に更新する
					else
					{
						// 予定一意キー：ダミー
						nextProc.setPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						
						// 出荷の作業情報を開始済に更新する
						updateBeginningFlag(pShipPlan.getShippingPlanUkey(), WorkingInformation.BEGINNING_FLAG_STARTED);
					}
				}
				else
				{
					// 予定一意キー：ダミー
					nextProc.setPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
				}
				
				// 次作業情報を作成する
				wNextHandler.create(nextProc);
			}
			
		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}		
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		
	}
	
	/**
	 * 指定された予定一意キーと一致する作業情報を、指定された作業開始フラグの状態に更新します。<BR>
	 * 
	 * @param pPlanUkey 更新対象となる予定一意キー
	 * @param pStatus 作業開始フラグ
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void updateBeginningFlag(String pPlanUkey, String pStatus) throws ReadWriteException
	{
		try
		{
			// 出荷の作業情報の更新を行う
			wWorkAltKey.KeyClear();
			
			// 検索条件をセットする
			// 予定一意キー
			wWorkAltKey.setPlanUkey(pPlanUkey);
			
			// 更新条件をセットする
			// 作業開始フラグ
			wWorkAltKey.updateBeginningFlag(pStatus);
			
			// 更新を行う
			wWorkHandler.modify(wWorkAltKey);
			
		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		
	}

} //end of class
