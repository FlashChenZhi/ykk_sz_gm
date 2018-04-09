package jp.co.daifuku.wms.sorting.schedule;

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
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * 仕分の予定データを作成するためのデータ検索、更新などを行うクラスです。<BR>
 * 予定データの取り込み、登録、修正・削除などで使用します。<BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/04</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingPlanOperator extends AbstractSortingSCH
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
	 * 出荷予定情報ハンドラ
	 */
	private ShippingPlanHandler wShipPlanHandler = null;

	/**
	 * 出荷予定情報検索キー
	 */
	private ShippingPlanSearchKey wShipPlanKey = null;

	/**
	 * 仕分予定情報ハンドラ
	 */
	protected SortingPlanHandler wPlanHandler = null;

	/**
	 * 仕分予定情報検索キー
	 */
	private SortingPlanSearchKey wPlanKey = null;

	/**
	 * 仕分予定情報更新キー
	 */
	private SortingPlanAlterKey wPlanAltKey = null;

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
	protected StockHandler wStockHandler = null;

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
	 * 出荷パッケージありなし
	 */
	private boolean isExistShippingPack = false;

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
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:34 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public SortingPlanOperator(Connection conn) throws ReadWriteException, ScheduleException
	{
		// 入荷予定情報
		wInstPlanHandler = new InstockPlanHandler(conn);
		wInstPlanKey = new InstockPlanSearchKey();

		// 出荷予定情報
		wShipPlanHandler = new ShippingPlanHandler(conn);
		wShipPlanKey = new ShippingPlanSearchKey();
		
		// 仕分予定情報
		wPlanHandler = new SortingPlanHandler(conn);
		wPlanKey = new SortingPlanSearchKey();
		wPlanAltKey = new SortingPlanAlterKey();
		
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
	public SortingPlanOperator()
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
	 * 出荷パッケージのありなしを取得します<BR>
	 * <DIR>
	 * true：出荷パッケージあり<BR>
	 * false：出荷パッケージなし<BR>
	 * </DIR>
	 * 
	 * @return 出荷パッケージありなし
	 */
	public boolean getIsExistShippingPack()
	{
		return isExistShippingPack;
	}

	/**
	 * 仕分予定情報を作成する際に、予定情報の作業区分を求めるためのメソッドです。<BR>
	 * 仕分予定情報に定められたケースピース区分を返します。<BR>
	 * 判別方法は以下のようになります。<BR>
	 * <BR>
	 * ・ケース仕分場所に入力がない場合：ピース品<BR>
	 * ・ケース仕分場所にも入力があった場合
	 * <DIR>
	 *   ・ケース数＞0 ピース数＞0の場合：混合<BR>
	 *   ・ケース数＞0 ピース数=＜0の場合：ケース<BR>
	 *   ・ケース数=＜0 ピース数＞0の場合：ピース<BR>
	 * </DIR>
	 * なお、ケース数・ピース数両方が0以下だった場合は、ScheduleExceptionを通知します。<BR>
	 * <BR>
	 * ＜パラメータ＞<BR>
	 * * 必須入力<BR>
	 * <DIR>
	 *   ・予定総数*<BR>
	 *   ・ケース入数*<BR>
	 *   ・ケース棚<BR>
	 *   ・ピース棚*<BR>
	 * </DIR>
	 * 
	 * @param param ケースピース区分を求めるための情報を含む<CODE>RetrievalSupportParameter</CODE
	 * @return String ケースピース区分
	 * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知されるます。
	 */
	public String getCasePieceFlag(SortingParameter param) throws ScheduleException
	{
		int planCaseQty = DisplayUtil.getCaseQty(param.getTotalPlanQty(), param.getEnteringQty());
		int planPieceQty = DisplayUtil.getPieceQty(param.getTotalPlanQty(), param.getEnteringQty());
		String caseLocation = param.getCaseSortingLocation();
		String pieceLocation = param.getPieceSortingLocation();

		// 入力チェックを行う
		checkString(pieceLocation);

		if (param.getEnteringQty() <= 0 && planCaseQty > 0)
		{
			// 6004001=データに不正がありました。
			RmiMsgLogClient.write("6004001", this.getClass().getName());
			throw new ScheduleException("6004001");
		}

		if (planCaseQty <= 0 && planPieceQty <= 0)
		{
			// 6004001=データに不正がありました。
			RmiMsgLogClient.write("6004001", this.getClass().getName());
			throw new ScheduleException("6004001");
		}

		String resultFlag = "";

		// ケース品仕分場所が空白の場合、ピース品
		if (StringUtil.isBlank(caseLocation))
		{
			resultFlag = SortingParameter.CASEPIECE_FLAG_PIECE;
		}
		// ケース品入力ありの場合
		else
		{
			// 予定ケース数、予定ピース数ともに0より大きい場合、混合品
			if (planCaseQty > 0 && planPieceQty > 0)
			{
				resultFlag = SortingParameter.CASEPIECE_FLAG_MIXED;
			}
			// 予定ケース数のみ0より大きい場合、ケース品
			else if (planCaseQty > 0 && planPieceQty <= 0)
			{
				resultFlag = SortingParameter.CASEPIECE_FLAG_CASE;
			}
			// 予定ピース数のみ0より大きい場合、ピース品
			else if (planCaseQty <= 0 && planPieceQty > 0)
			{
				resultFlag = SortingParameter.CASEPIECE_FLAG_PIECE;
			}
		}

		return resultFlag;

	}

	/**
	 * 入力または取り込んだ予定情報から作業情報、仕分予定情報を検索し該当データをロックします。<BR>
	 * DBに、指定条件に該当するデータがなかった場合はnullを、
	 * 該当するデータがあった場合、<CODE>SortingPlan</CODE>クラスにセットして返します。<BR>
	 * 排他チェック(最終更新日時のチェック)は本メソッドでは行いません。<BR>
	 * 検索情報は<CODE>SortingParameter</CODE>にセットしてください。<BR>
	 * また、ケースピース区分が不明な画面の場合は、本クラスの<CODE>getCasePieceFlag</CODE>メソッドから区分を求めてください。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * * 必須項目<BR>
	 * + 条件によって必須項目<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>仕分予定日</td><td>：</td><td>PlanDate</td></tr>
	 *   <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
	 *   <tr><td>*</td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
	 *   <tr><td>*</td><td>ケースピース区分</td><td>：</td><td>CasePieceFlag</td></tr>
	 *   <tr><td>*</td><td>クロス/DC</td><td>：</td><td>TcdcFlag</td></tr>
	 *   <tr><td>*</td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
	 *   <tr><td></td><td>ケース仕分場所</td><td>：</td><td>CaseSortingLocation</td></tr>
	 *   <tr><td>*</td><td>ピース仕分場所</td><td>：</td><td>PieceSortingLocation</td></tr>
	 * </table>
	 * ＊必須項目がセットされていなかった場合、禁止文字が含まれていた場合<CODE>ScheduleException</CODE>を返します。<BR>
	 * </DIR>
	 * <BR>
	 * 以下の順で処理を行います。<BR>
	 * <DIR>
	 *   <U>1.DnWorkInfoに同一情報が存在するかをチェックし、同一情報があればロックする。</U><BR>
	 *   　同一情報が存在しない場合、nullを返す。<BR>
	 *   　一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・仕分予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・商品コード<BR>
	 *     ・ケースピース区分<BR>
	 *     ・クロス/DC<BR>
	 *     ・出荷先コード<BR>
	 *     ・仕分場所<BR>
	 *     ・作業区分：仕分<BR>
	 *     ・作業状態：削除以外<BR>
	 *   </DIR>
	 *   <U>2.DnSortingPlanに同一情報が存在するかをチェック、そのインスタンスをロック・取得する。</U><BR>
	 *   　同一情報が存在しない場合、nullを返す。同一情報が存在した場合取得データを返却する。<BR>
	 *   　一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・仕分予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・商品コード<BR>
	 *     ・ケースピース区分<BR>
	 *     ・クロス/DC<BR>
	 *     ・出荷先コード<BR>
	 *     ・仕分場所<BR>
	 *     ・作業状態：削除以外<BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param inputParam 仕分予定情報を作成するための情報を含む<CODE>SortingParameter</CODE>
	 * @return SortingPlan 仕分予定情報
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public SortingPlan[] findSortingPlanForUpdate(SortingParameter inputParam) throws ReadWriteException, ScheduleException
	{
		// ケースピース区分がセットされていない場合、取得する
		if (StringUtil.isBlank(inputParam.getCasePieceFlag()))
		{
			inputParam.setCasePieceFlag(getCasePieceFlag(inputParam));
		}

		// 入力チェックを行う(必須チェック、禁止文字チェック)
		checkString(inputParam.getPlanDate());
		checkString(inputParam.getConsignorCode());
		checkString(inputParam.getItemCode());
		checkString(inputParam.getCasePieceFlag());
		checkString(inputParam.getTcdcFlag());
		checkString(inputParam.getCustomerCode());
		checkString(inputParam.getPieceSortingLocation());
		checkString(inputParam.getCaseSortingLocation(), false);

		// DnWorkInfoを検索し、ロックする
		WorkingInformation[] wInfo = null;
		// 検索条件をセットする
		setWorkinfoSearchKey(inputParam);
		// 作業情報のインスタンスを取得する
		wInfo = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);

		// 該当データが存在しなかった場合、nullを返す
		if (wInfo == null || wInfo.length == 0)
		{
			return null;
		}

		// DnSortingPlanを検索し、ロックする
		SortingPlan[] instPlan = null;
		// 検索条件をセットする
		setSortingPlanSearchKey(inputParam);

		// 仕分予定情報インスタンスを取得する
		instPlan = (SortingPlan[]) wPlanHandler.findForUpdate(wPlanKey);

		// 該当データが存在しなかった場合、nullを返す
		if (instPlan == null)
		{
			return null;
		}

		return instPlan;

	}

	/**
	 * 入力または取り込んだ予定情報から作業情報、仕分予定情報を検索します。<BR>
	 * DBに、指定条件に該当するデータがなかった場合はnullを、
	 * 該当するデータがあった場合、<CODE>SortingPlan</CODE>クラスにセットして返します。<BR>
	 * 排他チェック(最終更新日時のチェック)は本メソッドでは行いません。<BR>
	 * 検索情報は<CODE>SortingParameter</CODE>にセットしてください。<BR>
	 * また、ケースピース区分が不明な画面の場合は、本クラスの<CODE>getCasePieceFlag</CODE>メソッドから区分を求めてください。<BR>
	 * <BR>
	 * ＜入力データ＞<BR>
	 * * 必須項目<BR>
	 * + 条件によって必須項目<BR>
	 * <DIR>
	 * <table>
	 *   <tr><td></td><th>検索条件</th><td>：</td><th>セットするパラメータ</th></tr>
	 *   <tr><td>*</td><td>仕分予定日</td><td>：</td><td>PlanDate</td></tr>
	 *   <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
	 *   <tr><td>*</td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
	 *   <tr><td>*</td><td>ケースピース区分</td><td>：</td><td>CasePieceFlag</td></tr>
	 *   <tr><td>*</td><td>クロス/DC</td><td>：</td><td>TcdcFlag</td></tr>
	 *   <tr><td>*</td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
	 *   <tr><td></td><td>ケース仕分場所</td><td>：</td><td>CaseSortingLocation</td></tr>
	 *   <tr><td>*</td><td>ピース仕分場所</td><td>：</td><td>PieceSortingLocation</td></tr>
	 * </table>
	 * ＊必須項目がセットされていなかった場合、禁止文字が含まれていた場合<CODE>ScheduleException</CODE>を返します。<BR>
	 * </DIR>
	 * <BR>
	 * 以下の順で処理を行います。<BR>
	 * <DIR>
	 *   <U>1.DnWorkInfoに同一情報が存在するかをチェックし、同一情報があればロックする。</U><BR>
	 *   　同一情報が存在しない場合、nullを返す。<BR>
	 *   　一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・仕分予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・商品コード<BR>
	 *     ・ケースピース区分<BR>
	 *     ・クロス/DC<BR>
	 *     ・出荷先コード<BR>
	 *     ・仕分場所<BR>
	 *     ・作業区分：仕分<BR>
	 *     ・作業状態：削除以外<BR>
	 *   </DIR>
	 *   <U>2.DnSortingPlanに同一情報が存在するかをチェック、そのインスタンスをロック・取得する。</U><BR>
	 *   　同一情報が存在しない場合、nullを返す。同一情報が存在した場合取得データを返却する。<BR>
	 *   　一致条件は以下のようになります。<BR>
	 *   <DIR>
	 *     ・仕分予定日<BR>
	 *     ・荷主コード<BR>
	 *     ・商品コード<BR>
	 *     ・ケースピース区分<BR>
	 *     ・クロス/DC<BR>
	 *     ・出荷先コード<BR>
	 *     ・仕分場所<BR>
	 *     ・作業状態：削除以外<BR>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param inputParam 仕分予定情報を作成するための情報を含む<CODE>SortingParameter</CODE>
	 * @return SortingPlan 仕分予定情報
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public SortingPlan[] findSortingPlan(SortingParameter inputParam) throws ReadWriteException, ScheduleException
	{
		// ケースピース区分がセットされていない場合、取得する
		if (StringUtil.isBlank(inputParam.getCasePieceFlag()))
		{
			inputParam.setCasePieceFlag(getCasePieceFlag(inputParam));
		}

		// 入力チェックを行う(必須チェック、禁止文字チェック)
		checkString(inputParam.getPlanDate());
		checkString(inputParam.getConsignorCode());
		checkString(inputParam.getItemCode());
		checkString(inputParam.getCasePieceFlag());
		checkString(inputParam.getTcdcFlag());
		checkString(inputParam.getCustomerCode());
		checkString(inputParam.getPieceSortingLocation());
		checkString(inputParam.getCaseSortingLocation(), false);

		// DnWorkInfoを検索し、ロックする
		WorkingInformation[] wInfo = null;
		// 検索条件をセットする
		setWorkinfoSearchKey(inputParam);
		// 作業情報のインスタンスを取得する
		wInfo = (WorkingInformation[]) wWorkHandler.find(wWorkKey);

		// 該当データが存在しなかった場合、nullを返す
		if (wInfo == null || wInfo.length == 0)
		{
			return null;
		}

		// DnSortingPlanを検索し、ロックする
		SortingPlan[] instPlan = null;
		// 検索条件をセットする
		setSortingPlanSearchKey(inputParam);

		// 仕分予定情報インスタンスを取得する
		instPlan = (SortingPlan[]) wPlanHandler.find(wPlanKey);

		// 該当データが存在しなかった場合、nullを返す
		if (instPlan == null)
		{
			return null;
		}

		return instPlan;

	}

	/**
	 * 仕分予定情報の検索キーをセットする
	 * 
	 * @param searchParam 検索条件を含むパラメータ
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void setSortingPlanSearchKey(SortingParameter searchParam) throws ReadWriteException
	{
		// 検索条件をクリアしてからセットする
		wPlanKey.KeyClear();
		// 仕分予定日
		wPlanKey.setPlanDate(searchParam.getPlanDate());
		// 荷主コード
		wPlanKey.setConsignorCode(searchParam.getConsignorCode());
		// 商品コード
		wPlanKey.setItemCode(searchParam.getItemCode());
		// TC/DC区分
		if (searchParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
		{
			wPlanKey.setTcdcFlag(SortingPlan.TCDC_FLAG_DC);
		}
		else if (searchParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			wPlanKey.setTcdcFlag(SortingPlan.TCDC_FLAG_CROSSTC);
		}
		else if (searchParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_TC))
		{
			wPlanKey.setTcdcFlag(SortingPlan.TCDC_FLAG_TC);
		}
		// 出荷先コード
		wPlanKey.setCustomerCode(searchParam.getCustomerCode());
		// 仕入先コード
		wPlanKey.setSupplierCode(searchParam.getSupplierCode());
		// 作業状態：削除以外
		wPlanKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
		// 仕分予定一意キー：自分以外の情報を検索したい場合はセットする
		if(!StringUtil.isBlank(searchParam.getSortingPlanUkey()))
		{	
			wPlanKey.setSortingPlanUkey(searchParam.getSortingPlanUkey(), "!=");
		}
		// クロスドックパッケージありでかつ、TC/DC区分がクロスの場合
		if (this.getIsExistCrossDockPack() && searchParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			// 入力情報のケースピース区分がケースの時
			if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				// (区分：混合 OR 区分：ケース)
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "OR");
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_CASE, "=", "", ")", "AND");
			}
			// 入力情報のケースピース区分がピースの時
			else if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				// (区分：混合 OR 区分：ピース)
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "OR");
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_PIECE, "=", "", ")", "AND");
			}
			// 出荷伝票No.
			wPlanKey.setShippingTicketNo(searchParam.getShippingTicketNo());
			// 出荷伝票行No.
			wPlanKey.setShippingLineNo(searchParam.getShippingLineNo());
		}
		else
		{
			// 入力情報のケースピース区分が混合の時
			if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
			{
				// (((区分：混合 OR 区分：ケース)  AND 作業棚：ケース棚) OR
				// ((区分：混合 OR 区分：ピース)  AND 作業棚：ピース棚)) AND
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_MIX, "=", "(((", "", "OR");
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_CASE, "=", "", ")", "AND");
				wPlanKey.setCaseLocation(searchParam.getCaseSortingLocation(), "=", "", ")", "OR");
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_MIX, "=", "((", "", "OR");
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_PIECE, "=", "", ")", "AND");
				wPlanKey.setPieceLocation(searchParam.getPieceSortingLocation(), "=", "", "))", "AND");
			}
			// 入力情報のケースピース区分がケースの時
			else if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				// ((区分：混合 OR 区分：ケース)  AND 作業棚：ケース棚)
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_MIX, "=", "((", "", "OR");
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_CASE, "=", "", ")", "AND");
				wPlanKey.setCaseLocation(searchParam.getCaseSortingLocation(), "=", "", ")", "AND");
			}
			// 入力情報のケースピース区分がピースの時
			else if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				// ((区分：混合 OR 区分：ピース)  AND 作業棚：ピース棚)
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_MIX, "=", "((", "", "OR");
				wPlanKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_PIECE, "=", "", ")", "AND");
				wPlanKey.setPieceLocation(searchParam.getPieceSortingLocation(), "=", "", ")", "AND");
			}
		}

	}

	/**
	 * 作業情報の検索キーをセットする
	 * 
	 * @param searchParam 検索条件を含むパラメータ
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void setWorkinfoSearchKey(SortingParameter searchParam) throws ReadWriteException
	{
		// 検索条件をクリアしてからセットする
		wWorkKey.KeyClear();
		// 仕分予定日
		wWorkKey.setPlanDate(searchParam.getPlanDate());
		// 荷主コード
		wWorkKey.setConsignorCode(searchParam.getConsignorCode());
		// 商品コード
		wWorkKey.setItemCode(searchParam.getItemCode());
		// TC/DC区分
		if (searchParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
		{
			wWorkKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
		}
		else if (searchParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			wWorkKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
		}
		else if (searchParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_TC))
		{
			wWorkKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
		}
		// 出荷先コード
		wWorkKey.setCustomerCode(searchParam.getCustomerCode());
		// 仕入先コード
		wWorkKey.setSupplierCode(searchParam.getSupplierCode());
		
		// クロスドックパッケージありでかつ、TC/DC区分がクロスの場合
		if (this.getIsExistCrossDockPack() && searchParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			// 入力情報のケースピース区分が混合の時
			if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
			{
				// (区分：ケース OR 区分：ピース)
				wWorkKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE, "=", "(", "", "OR");
				wWorkKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE, "=", "", ")", "AND");
			}
			// 入力情報のケースピース区分がケースの時
			else if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				// 区分：ケース
				wWorkKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			// 入力情報のケースピース区分がピースの時
			else if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				// 区分：ピース
				wWorkKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			
			// 出荷伝票No.
			wWorkKey.setShippingTicketNo(searchParam.getShippingTicketNo());
			// 出荷伝票行No.
			wWorkKey.setShippingLineNo(searchParam.getShippingLineNo());
		}
		// クロスドックパッケージなしの場合
		else
		{
			// 入力情報のケースピース区分が混合の時
			if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
			{
				// ((区分：ケース AND 作業棚：ケース棚) OR (区分：ピース AND 作業棚：ピース棚)) AND
				wWorkKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE, "=", "((", "", "AND");
				wWorkKey.setLocationNo(searchParam.getCaseSortingLocation(), "=", "", ")", "OR");
				wWorkKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE, "=", "(", "", "AND");
				wWorkKey.setLocationNo(searchParam.getPieceSortingLocation(), "=", "", "))", "AND");
			}
			// 入力情報のケースピース区分がケースの時
			else if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				// 区分：ケース AND 作業棚：ケース棚
				wWorkKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				wWorkKey.setLocationNo(searchParam.getCaseSortingLocation());
			}
			// 入力情報のケースピース区分がピースの時
			else if (searchParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				// 区分：ピース AND 作業棚：ピース棚
				wWorkKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				wWorkKey.setLocationNo(searchParam.getPieceSortingLocation());
			}
		}
		// 作業状態：削除以外
		wWorkKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		// 作業区分：仕分
		wWorkKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 仕分予定一意キー：自分以外の情報を検索したい場合はセットする
		if(!StringUtil.isBlank(searchParam.getSortingPlanUkey()))
		{	
			wWorkKey.setPlanUkey(searchParam.getSortingPlanUkey(), "!=");
		}


	}

	/**
	 * 仕分予定一意キーを指定し、
	 * それに紐づく仕分予定情報、作業情報、在庫情報の状態を削除に更新し、紐づく次作業情報を更新します。<BR>
	 * このメソッドを呼び出す前に、本クラスの
	 * <CODE>findSortingPlanForUpdate</CODE>メソッドを使用し対象データのロックを行ってください。<BR>
	 * また、pPlanUkeyとpProcessNameに値がセットされていない場合、
	 * 禁止文字が含まれていた場合<CODE>ScheduleException</CODE>を返します。<BR>
	 * 本メソッドは以下の順で処理を行います。<BR>
	 * <BR>
	 *   <DIR>
	 *   <U>1.作業情報(DnWorkInfo)の更新</U><BR>
	 *   　・仕分予定一意キーに紐づく作業情報を更新する。<BR>
	 *   　　-状態フラグ：削除<BR>
	 *   　　-最終更新処理名：pProcessName<BR>
	 *   <U>2.仕分予定情報(DnSortingPlan)の更新</U><BR>
	 *   　・仕分予定一意キーに紐づく仕分予定情報を更新する。<BR>
	 *   　　-状態フラグ：削除<BR>
	 *   　　-最終更新処理名：pProcessName<BR>
	 *   <U>3.在庫情報(DmStock)の更新(更新した作業情報件数分、更新を行う)</U><BR>
	 *   　・更新を行った作業情報から取得した在庫IDに紐づく在庫情報をロックし、更新する。<BR>
	 *   　　-状態フラグ：完了<BR>
	 *   　　-在庫数：更新作業情報の作業予定数(plan_qty)分減算<BR>
	 *   　　-引当数：更新作業情報の作業予定数(plan_qty)分減算<BR>
	 *   　　-最終更新処理名：pProcessName<BR>
	 *      ※クロスドックパッケージありの場合、以下の処理を行う
	 *   <U>4.次作業情報(DnNextproc)の更新または削除</U><BR>
	 *   　・仕分予定一意キーに紐づく次作業情報をロックし、検索する。<BR>
	 *     ・クロスドックパッケージありの場合、更新を行う <BR>
	 *   　　-仕分予定一意キー：NULL<BR>
	 *   　　-最終更新処理名：pProcessName<BR>
	 *     ・クロスドックパッケージなしの場合、削除を行う <BR>
	 *   </DIR>
	 * <BR>
	 * 
	 * @param pPlanUKey 処理対象となる仕分予定一意ーキ
	 * @param pProcessName 呼び出しもと処理名
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public void updateSortingPlan(String pPlanUKey, String pProcessName) throws ReadWriteException, ScheduleException
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

			// 仕分予定情報の更新を行う
			wPlanAltKey.KeyClear();
			wPlanAltKey.setSortingPlanUkey(pPlanUKey);
			wPlanAltKey.updateStatusFlag(SortingPlan.STATUS_FLAG_DELETE);
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
				// 引当数、在庫数を減算。在庫状態を完了に更新する
				wStockAltKey.KeyClear();
				wStockAltKey.setStockId(stock.getStockId());
				wStockAltKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				wStockAltKey.updateStockQty(stock.getStockQty() - workInfo[i].getPlanEnableQty());
				wStockAltKey.updateAllocationQty(stock.getAllocationQty() - workInfo[i].getPlanEnableQty());
				wStockAltKey.updateLastUpdatePname(pProcessName);
				wStockHandler.modify(wStockAltKey);

			}
			
			// 仕分予定一意キーより次作業情報の更新を行う
			wNextKey.KeyClear();
			// 予定一意キー
			wNextKey.setPlanUkey(pPlanUKey, "=", "", "", "OR");
			// 仕分予定一意キー
			wNextKey.setSortingPlanUkey(pPlanUKey, "=", "", "", "OR");
			// 次作業情報を取得する。
			NextProcessInfo wNextInfo[] = (NextProcessInfo[]) wNextHandler.findForUpdate(wNextKey);

			if (wNextInfo != null && wNextInfo.length > 0)
			{
				
				for (int i = 0; i < wNextInfo.length; i++)
				{
					// 更新条件を設定する。
					wNextAltKey.KeyClear();
					// 予定一意キー
					wNextAltKey.setPlanUkey(pPlanUKey, "=", "(", "", "OR");
					// 仕分予定一意キー
					wNextAltKey.setSortingPlanUkey(pPlanUKey, "=", "", ")", "AND");
					
					// 更新値を設定する。
					// ダミーKEYを設定
					if (wNextInfo[i].getWorkKind().equals(NextProcessInfo.JOB_TYPE_INSTOCK))
					{
						// 入荷→仕分→出荷の削除処理
						// 自作業区分
						wNextAltKey.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
						// 仕分予定一意キー
						wNextAltKey.updateSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					}
					else if (wNextInfo[i].getWorkKind().equals(NextProcessInfo.JOB_TYPE_SORTING))
					{
						// 仕分→出荷の削除処理
						// 自作業区分
						wNextAltKey.setWorkKind(NextProcessInfo.JOB_TYPE_SORTING);
						// 予定一意キー
						wNextAltKey.updatePlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					}
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
	 * 入力または取り込んだ予定情報から、仕分予定情報、作業情報、在庫情報を登録します。<BR>
	 * 各パラメータの必須チェックなどは各画面で行ってください。<BR>
	 * 本メソッドは、以下の処理を行います。<BR>
	 * <BR>
	 *   <DIR>
	 *   <U>1.仕分予定情報(DnSortingPlan)の登録</U><BR>
	 *   　・入力データをもとに仕分予定情報を作成する。<BR>
	 *   <U>2.作業情報(DnWorkInfo)の登録</U><BR>
	 *   　・登録した仕分予定情報をもとに作業情報を作成する。<BR>
	 *   <U>3.在庫情報(DmStock)の登録(登録した作業情報分、登録を行う)</U><BR>
	 *   　・登録した作業情報をもとに在庫情報を登録する。<BR>
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
	 * @param inputParam 仕分予定情報を作成するための情報を含む<CODE>SortingParameter</CODE>
	 * @throws ScheduleException 処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public void createSortingPlan(SortingParameter inputParam) throws ReadWriteException, ScheduleException
	{
		// ケースピース区分がセットされていない場合、取得する
		if (StringUtil.isBlank(inputParam.getCasePieceFlag()))
		{
			inputParam.setCasePieceFlag(getCasePieceFlag(inputParam));
		}

		// 入力データをもとに仕分予定情報を登録する
		SortingPlan sortPlan = null;
		sortPlan = createSorting(inputParam);

		// 作業情報と在庫情報を登録する
		createStockWorkInfo(sortPlan, inputParam.getRegistPName());

		// クロスドックパッケージありの場合
		if (this.getIsExistCrossDockPack()
		&&  inputParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			// 次作業情報の登録または更新を行う
			createNextProc(sortPlan, inputParam.getRegistPName());
		}

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 他との排他のため、作業情報、在庫情報をロックします。
	 * 条件：（状態：未開始、作業区分：仕分）
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>状態区分（未開始）</LI>
	 *     <LI>作業区分（仕分）</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @throws ReadWriteException		データベースエラー発生
	 */
	public void lockWorkingInfoStockData()
		throws ReadWriteException
	{
		wWorkKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		wWorkKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		wWorkHandler.lock(wWorkKey);
	}


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
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	private void setAddOnPackage(Connection conn) throws ReadWriteException, ScheduleException
	{
		
		// クロスドックパッケージが導入されているか
		if (isCrossDockPack(conn))
			isExistCrossDocPack = true;

		// 入荷パッケージが導入されているか
		if (isInstockPack(conn))
			isExistInstockPack = true;

		// 出荷パッケージが導入されているか
		if (isShippingPack(conn))
			isExistShippingPack = true;
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
	 * 入力文字列の入力必須チェック・禁止文字チェックを行うためのメソッドです。<BR>
	 * 入力文字列がnullまたは空の場合、禁止文字が含まれていた場合、ScheduleExceptionを返します。<BR>
	 * チェックフラグがtrueの場合、両方のチェックを行います。<BR>
	 * チェックフラグがfalseの場合、禁止文字のチェックのみを行います。<BR>
	 * 
	 * @param str チェックを行う文字列
	 * @param check 必須チェックを行うか否か
	 * @throws ScheduleException 入力文字列がnullまたは空の場合、禁止文字が含まれていた場合、ScheduleExceptionを返します。
	 */
	private void checkString(String str, boolean check) throws ScheduleException
	{
		if (check)
		{
			checkString(str);
			return;
		}

		// 文字列が禁止文字を含んでいる場合はExceptionをなげる
		if (StringUtil.isBlank(str))
		{
			return;
		}
		if (str.indexOf(WmsParam.PATTERNMATCHING) != -1)
		{
			// ログを落とす
			// 6003106={0}にシステムで使用できない文字が含まれています
			RmiMsgLogClient.write("6003106" + wDelim + str, this.getClass().getName());
			throw new ScheduleException("6003106" + wDelim + str);
		}
	}

	/**
	 * 仕分予定情報から作業情報と在庫情報を作成する。<BR>
	 * 仕分予定情報が混合だった場合、ケース作業とピース作業を作成する。<BR>
	 * 
	 * @param pSortPlan 作成した仕分予定情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createStockWorkInfo(SortingPlan pSortPlan, String pProcessName) throws ReadWriteException
	{
		String casePieceFlg = "";
		String locationNo = "";
		int planQty = 0;

		WorkingInformation workInfo = null;

		for (int i = 0; i < 2; i++)
		{
			// 予定情報が混合以外の場合(ケースとピースのみ。指定なしはない)
			if (!pSortPlan.getCasePieceFlag().equals(SortingPlan.CASEPIECE_FLAG_MIX))
			{
				casePieceFlg = pSortPlan.getCasePieceFlag();
				planQty = pSortPlan.getPlanQty();
				// ケース作業の場合、ケース品仕分場所をセット
				if (casePieceFlg.equals(SortingPlan.CASEPIECE_FLAG_CASE))
				{
					locationNo = pSortPlan.getCaseLocation();
				}
				// ピース作業の場合、ピース品仕分場所をセット
				else if (casePieceFlg.equals(SortingPlan.CASEPIECE_FLAG_PIECE))
				{
					locationNo = pSortPlan.getPieceLocation();
				}

			}
			// 予定情報が混合で1回目の処理(ケース処理を行う)
			else if (i == 0)
			{
				casePieceFlg = SortingPlan.CASEPIECE_FLAG_CASE;
				planQty = DisplayUtil.getCaseQty(pSortPlan.getPlanQty(), pSortPlan.getEnteringQty()) * pSortPlan.getEnteringQty();
				locationNo = pSortPlan.getCaseLocation();
			}
			// 予定情報が混合で2回目の処理(ピース処理を行う)
			else
			{
				casePieceFlg = SortingPlan.CASEPIECE_FLAG_PIECE;
				planQty = DisplayUtil.getPieceQty(pSortPlan.getPlanQty(), pSortPlan.getEnteringQty());
				locationNo = pSortPlan.getPieceLocation();
			}

			// 作業情報を作成する
			workInfo = createWorkInfo(pSortPlan, locationNo, planQty, casePieceFlg, pProcessName);

			// 在庫情報を作成する
			createStock(workInfo, pProcessName);

			// 混合以外の場合、ループは1回でぬける
			if (!pSortPlan.getCasePieceFlag().equals(SortingPlan.CASEPIECE_FLAG_MIX))
			{
				break;
			}

		}

	}

	/**
	 * 入力情報より仕分予定情報を作成します。
	 * 
	 * @param param 入力情報
	 * @return 作成した仕分予定一意キー
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected SortingPlan createSorting(SortingParameter param) throws ReadWriteException
	{
		SortingPlan sortPlan = new SortingPlan();
		try
		{
			String planUkey_seqno = wSequenceHandler.nextSortingPlanKey();

			// 仕分予定一意キー	
			sortPlan.setSortingPlanUkey(planUkey_seqno);
			// 状態フラグ：未開始
			sortPlan.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);
			// 仕分予定日
			sortPlan.setPlanDate(param.getPlanDate());
			// 荷主コード
			sortPlan.setConsignorCode(param.getConsignorCode());
			// 荷主名称
			sortPlan.setConsignorName(param.getConsignorName());
			// 出荷先コード
			sortPlan.setCustomerCode(param.getCustomerCode());
			// 出荷先名称
			sortPlan.setCustomerName1(param.getCustomerName());
			// 出荷伝票No.
			sortPlan.setShippingTicketNo(param.getShippingTicketNo());
			// 出荷伝票行No.
			sortPlan.setShippingLineNo(param.getShippingLineNo());
			// 商品コード
			sortPlan.setItemCode(param.getItemCode());
			// 商品名称
			sortPlan.setItemName1(param.getItemName());
			// 仕分予定数
			sortPlan.setPlanQty(param.getTotalPlanQty());
			// 仕分実績数
			sortPlan.setResultQty(0);
			// 仕分欠品数
			sortPlan.setShortageCnt(0);
			// ケース入数
			sortPlan.setEnteringQty(param.getEnteringQty());
			// ボール入数
			sortPlan.setBundleEnteringQty(param.getBundleEnteringQty());
			// ケース/ピース区分
			if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				sortPlan.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_CASE);
			}
			else if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				sortPlan.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_PIECE);
			}
			else if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
			{
				sortPlan.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_MIX);
			}
			// ケースITF
			sortPlan.setItf(param.getITF());
			// ボールITF
			sortPlan.setBundleItf(param.getBundleITF());
			// ピース品仕分場所
			sortPlan.setPieceLocation(param.getPieceSortingLocation());
			// ケース品仕分場所
			sortPlan.setCaseLocation(param.getCaseSortingLocation());
			// TC/DC区分
			sortPlan.setTcdcFlag(param.getTcdcFlag());
			// 仕入先コード
			sortPlan.setSupplierCode(param.getSupplierCode());
			// 仕入先名称
			sortPlan.setSupplierName1(param.getSupplierName());
			// 入荷伝票No.
			sortPlan.setInstockTicketNo(param.getInstockTicketNo());
			// 入荷伝票行No.
			sortPlan.setInstockLineNo(param.getInstockLineNo());
			// 賞味期限
			// 初期値
			// ロットNo.
			// 初期値
			// 予定情報コメント
			// 初期値
			// バッチNo.(スケジュールNo.)
			sortPlan.setBatchNo(param.getBatchNo());
			// 作業者コード
			sortPlan.setWorkerCode(param.getWorkerCode());
			// 作業者名
			sortPlan.setWorkerName(param.getWorkerName());
			// 端末No.RFTNo.
			sortPlan.setTerminalNo(param.getTerminalNumber());
			// 登録区分
			sortPlan.setRegistKind(param.getRegistKbn());
			// 登録処理名
			sortPlan.setRegistPname(param.getRegistPName());
			// 最終更新処理名
			sortPlan.setLastUpdatePname(param.getRegistPName());
			// 出荷予定情報の登録
			wPlanHandler.create(sortPlan);

		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return sortPlan;
	}

	/**
	 * 仕分予定情報から作業情報を作成します。<BR>
	 * また、作成した作業情報を返します。<BR>
	 * ＜検索条件＞<BR>
	 *   <DIR>
	 *   仕分予定一意キー：仕分予定情報の仕分予定一意キー
	 *   </DIR>
	 * @param pInstPlan 仕分予定情報
	 * @param pProcessName 処理名
	 * @return 登録した作業情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private WorkingInformation createWorkInfo(SortingPlan pSortPlan, String pLocationNo, int pPlanQty, String pCasePieceFlg, String pProcessName)
		throws ReadWriteException
	{
		WorkingInformation workInfo = new WorkingInformation();

		try
		{
			String job_seqno = wSequenceHandler.nextJobNo();
			String stockId_seqno = wSequenceHandler.nextStockId();

			// 作業No.
			workInfo.setJobNo(job_seqno);
			// 作業区分
			workInfo.setJobType(WorkingInformation.JOB_TYPE_SORTING);
			// 集約作業No.
			workInfo.setCollectJobNo(job_seqno);
			// 状態フラグ：未開始
			workInfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			
			// クロスドックパッケージあり、かつ、TC/DC区分がDC以外の場合
			if (this.getIsExistCrossDockPack()
			&&  !pSortPlan.getTcdcFlag().equals(SortingPlan.TCDC_FLAG_DC))
			{
				// 入荷パッケージありの場合
				if (this.getIsExistInstockPack())
				{
					// 検索条件を設定する
					wInstPlanKey.KeyClear();
					// 荷主コード
					wInstPlanKey.setConsignorCode(pSortPlan.getConsignorCode());
					// 入荷予定日
					wInstPlanKey.setPlanDate(pSortPlan.getPlanDate());
					// 仕入先コード
					wInstPlanKey.setSupplierCode(pSortPlan.getSupplierCode());
					// 入荷伝票No.
					wInstPlanKey.setInstockTicketNo(pSortPlan.getInstockTicketNo());
					// 入荷伝票行No.
					wInstPlanKey.setInstockLineNo(pSortPlan.getInstockLineNo());
					// 商品コード
					wInstPlanKey.setItemCode(pSortPlan.getItemCode());
					// TC/DC区分
					wInstPlanKey.setTcdcFlag(pSortPlan.getTcdcFlag());
					// 状態フラグ：削除以外
					wInstPlanKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");

				
					if (wInstPlanHandler.count(wInstPlanKey) != 0)
					{
						// 入荷予定情報を取得
						InstockPlan wInstock = (InstockPlan) wInstPlanHandler.findPrimary(wInstPlanKey);
						if ( !wInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART) && 
							 !wInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_DELETE))
						{
							// 作業開始フラグ：開始済
							workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
						}
						else
						{
							// 作業開始フラグ：未開始
							workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_NOT_STARTED);
						}
					}
					else
					{
						// 作業開始フラグ：未開始
						workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_NOT_STARTED);
					}
				}
				else
				{
					// 作業開始フラグ：開始済
					workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
				}
			}
			else
			{
				// 作業開始フラグ：開始済
				workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			}
			
			// 予定一意キー
			workInfo.setPlanUkey(pSortPlan.getSortingPlanUkey());
			// 在庫ID
			workInfo.setStockId(stockId_seqno);
			// エリアNo.
			// 初期値
			// ロケーションNo.
			workInfo.setLocationNo(pLocationNo);
			// 予定日
			workInfo.setPlanDate(pSortPlan.getPlanDate());
			// 荷主コード
			workInfo.setConsignorCode(pSortPlan.getConsignorCode());
			// 荷主名称
			workInfo.setConsignorName(pSortPlan.getConsignorName());
			// 仕入先コード
			workInfo.setSupplierCode(pSortPlan.getSupplierCode());
			// 仕入先名称
			workInfo.setSupplierName1(pSortPlan.getSupplierName1());
			// 入荷伝票No.
			workInfo.setInstockTicketNo(pSortPlan.getInstockTicketNo());
			// 入荷伝票行No.
			workInfo.setInstockLineNo(pSortPlan.getInstockLineNo());
			// 出荷先コード
			workInfo.setCustomerCode(pSortPlan.getCustomerCode());
			// 出荷先名称
			workInfo.setCustomerName1(pSortPlan.getCustomerName1());
			// 出荷伝票No.
			workInfo.setShippingTicketNo(pSortPlan.getShippingTicketNo());
			// 出荷伝票行No.
			workInfo.setShippingLineNo(pSortPlan.getShippingLineNo());
			// 商品コード
			workInfo.setItemCode(pSortPlan.getItemCode());
			// 商品名称
			workInfo.setItemName1(pSortPlan.getItemName1());
			// 作業予定数（ホスト予定数）：仕分予定数
			workInfo.setHostPlanQty(pSortPlan.getPlanQty());
			// 作業予定数：仕分予定数
			workInfo.setPlanQty(pPlanQty);
			// 作業可能数：仕分予定数
			workInfo.setPlanEnableQty(pPlanQty);
			// 作業実績数
			workInfo.setResultQty(0);
			// 作業欠品数
			workInfo.setShortageCnt(0);
			// ケース入数
			workInfo.setEnteringQty(pSortPlan.getEnteringQty());
			// ボール入数
			workInfo.setBundleEnteringQty(pSortPlan.getBundleEnteringQty());
			// ケース/ピース区分（荷姿）
			workInfo.setCasePieceFlag(pCasePieceFlg);
			// ケース/ピース区分（作業形態）
			workInfo.setWorkFormFlag(pCasePieceFlg);
			// ケースITF
			workInfo.setItf(pSortPlan.getItf());
			// ボールITF
			workInfo.setBundleItf(pSortPlan.getBundleItf());
			// TC/DC区分
			workInfo.setTcdcFlag(pSortPlan.getTcdcFlag());
			// 賞味期限
			workInfo.setUseByDate(pSortPlan.getUseByDate());
			// ロットNo.
			workInfo.setLotNo(pSortPlan.getLotNo());
			// 予定情報コメント
			workInfo.setPlanInformation(pSortPlan.getPlanInformation());
			// オーダーNo.
			// 初期値
			// 発注日
			// 初期値
			// 賞味期限（実績）
			// 初期値
			// ロットNo.（実績）
			// 初期値
			// 作業結果ロケーション
			// 初期値
			// 未作業報告フラグ
			workInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			// バッチNo.
			workInfo.setBatchNo(pSortPlan.getBatchNo());
			// 作業者コード
			// 初期値
			// 作業者名称
			// 初期値
			// 端末No.
			// 初期値
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
		catch (NoPrimaryException e)
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
			// 在庫ステータス：仕分待ち
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_PICKING);
			// 在庫数
			stock.setStockQty(workInfo.getPlanQty());
			// 引当数
			stock.setAllocationQty(workInfo.getPlanQty());
			// 予定数
			stock.setPlanQty(0);
			// ケース/ピース区分(荷姿)
			stock.setCasePieceFlag(workInfo.getCasePieceFlag());
			// 入庫日
			// 初期値
			// 最終出庫日
			// 初期値
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
	 * 作成した仕分予定情報より、次作業情報の登録または更新を行います。<BR>
	 * 
     * ○入荷→仕分→出荷データの更新登録 <BR>
     *  1.荷主コード、予定日、仕入先コード、入荷伝票No.、入荷伝票行No.、自作業区分：入荷をキーに次作業情報を検索する。 <BR>
     *  2.次作業情報が存在した場合、次作業情報の更新を行う。 <BR><DIR>
     *    仕分予定一意キーをパラメータの一意キーに更新する。 <BR>
     *    最終更新日時、最終更新処理名を更新する。 <BR></DIR>
     *  3.次作業情報が存在せず、かつ、出荷パッケージなしの場合、パラメータの内容をもとに次作業情報の登録を行う。 <BR><DIR>
     *    荷主コード、入荷予定日、仕入先コード、入荷伝票No.、入荷伝票行No.をキーに入荷予定情報を検索する。 <BR>
     *    入荷予定情報が存在した場合、その情報で予定一意キー、入荷予定一意キーをセットする。 <BR>
     *    入荷予定情報が存在しない場合、予定一意キー、入荷予定一意キーにはnullをセットする。 <BR></DIR>
     * 
     * ○仕分→出荷データの更新登録 <BR>
     *  1.荷主コード、予定日、仕入先コード、入荷伝票No.、入荷伝票行No.、出荷先コード、出荷伝票No.、出荷伝票行No.、自作業区分：仕分をキーに次作業情報を検索する。 <BR>
     *  2.次作業情報が存在した場合、次作業情報の更新を行う。 <BR><DIR>
     *    仕分予定一意キーをパラメータの一意キーに更新する。 <BR>
     *    最終更新日時、最終更新処理名を更新する。 <BR></DIR>
	 * 
	 * @param sortPlan 仕分予定情報
	 * @param pProcessName 処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createNextProc(SortingPlan sortPlan, String pProcessName) throws ReadWriteException
	{
		
		String shipping_plan_ukey = SystemDefine.PLAN_UKEY_DUMMY;		
		
		// 入荷パッケージありの場合入荷→仕分→出荷データの登録または更新登録を行う
		if (this.getIsExistInstockPack())
		{
			shipping_plan_ukey = createInstockNextProc(sortPlan, pProcessName);
		}

		// 出荷パッケージありの場合仕分→出荷データの登録または更新登録を行う 
		if (this.getIsExistShippingPack())
		{
			createSortingNextProc(sortPlan, shipping_plan_ukey, pProcessName);
		}

	}
	
	/**
	 * 入荷→仕分→出荷データの更新登録を行う
	 * @param sortPlan 仕分予定データ
	 * @param pProcessName 登録処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private String createInstockNextProc(SortingPlan sortPlan, String pProcessName) throws ReadWriteException
	{
		String shipping_plan_ukey = "";
		
		try
		{
			NextProcessInfo nextInfo = null;
				
			// 検索条件を設定する
			wNextKey.KeyClear();
			// 自作業区分：入荷
			wNextKey.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
			// 荷主コード
			wNextKey.setConsignorCode(sortPlan.getConsignorCode());
			// 仕入先コード
			wNextKey.setSupplierCode(sortPlan.getSupplierCode());
			// 入荷伝票No.
			wNextKey.setInstockTicketNo(sortPlan.getInstockTicketNo());
			// 入荷伝票行No.
			wNextKey.setInstockLineNo(sortPlan.getInstockLineNo());
			// 商品コード
			wNextKey.setItemCode(sortPlan.getItemCode());
			// TC/DC区分
			wNextKey.setTcdcFlag(sortPlan.getTcdcFlag());
			// 出荷予定日
			wNextKey.setShipPlanDate(sortPlan.getPlanDate());
			// 出荷先コード
			wNextKey.setCustomerCode(sortPlan.getCustomerCode(), "=", "", "", "AND");
			// 出荷伝票No.
			wNextKey.setShippingTicketNo(sortPlan.getShippingTicketNo(), "=", "", "", "AND");
			// 出荷伝票行No.
			wNextKey.setShippingLineNo(sortPlan.getShippingLineNo(), "=", "", "", "AND");
				
			// 検索結果を取得
			nextInfo = (NextProcessInfo) wNextHandler.findPrimaryForUpdate(wNextKey);
			
			// 次作業情報が存在する場合
			if (nextInfo != null)
			{
				// 次作業情報が未開始の場合更新を行う
				if (nextInfo.getStatusFlag().equals(NextProcessInfo.STATUS_FLAG_UNPROCESSING))
				{
					// 検索された次作業情報の入荷予定一意キーで入荷予定情報を検索し
					// 該当するデータが一部完了または完了の場合、前作業が開始されているので
					// 次作業情報の仕分予定一意キーには、ダミーキーをセットし、入荷→仕分の作業の
					// 紐付きを解除する
					
					String sortingPlanUkey = sortPlan.getSortingPlanUkey();
					
					if (nextInfo.getPlanUkey().equals((SystemDefine.PLAN_UKEY_DUMMY)))
					{
						// ダミーKeyをセットし、入荷→仕分の紐付きを解除
						sortingPlanUkey = SystemDefine.PLAN_UKEY_DUMMY;
					}
					else
					{
						// 検索条件を設定する
						wInstPlanKey.KeyClear();
						// 入荷予定一意キー
						wInstPlanKey.setInstockPlanUkey(nextInfo.getPlanUkey());
						// 入荷予定情報を取得
						InstockPlan wInstock = (InstockPlan) wInstPlanHandler.findPrimary(wInstPlanKey);
					
						// 次作業情報が存在する場合
						if (wInstock != null)
						{
							// 該当する前作業（入荷）が、一部完了または完了の場合
							if (wInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART) || 
								 wInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETION))
							{
								// ダミーKeyをセットし、入荷→仕分の紐付きを解除
								sortingPlanUkey = SystemDefine.PLAN_UKEY_DUMMY;
							}
						}
					}

					
					// 更新条件を設定する
					wNextAltKey.KeyClear();
					// 次作業情報一意キー
					wNextAltKey.setNextProcUkey(nextInfo.getNextProcUkey());
					
					// 更新値を設定する
					// 仕分予定一意キー
					wNextAltKey.updateSortingPlanUkey(sortingPlanUkey);
					// 作業予定数
					wNextAltKey.updatePlanQty(sortPlan.getPlanQty());
					// 最終更新処理名
					wNextAltKey.updateLastUpdatePname(pProcessName);
						
					// 次作業情報を更新する
					wNextHandler.modify(wNextAltKey);
				}
			}
			// 次作業情報が存在しない場合
			else
			{
				nextInfo = new NextProcessInfo();
					
				// 入荷予定情報を検索する
				// 検索条件を設定する
				wInstPlanKey.KeyClear();
				// 荷主コード
				wInstPlanKey.setConsignorCode(sortPlan.getConsignorCode());
				// 入荷予定日
				wInstPlanKey.setPlanDate(sortPlan.getPlanDate());
				// 仕入先コード
				wInstPlanKey.setSupplierCode(sortPlan.getSupplierCode());
				// 入荷伝票No.
				wInstPlanKey.setInstockTicketNo(sortPlan.getInstockTicketNo());
				// 入荷伝票行No.
				wInstPlanKey.setInstockLineNo(sortPlan.getInstockLineNo());
				// 商品コード
				wInstPlanKey.setItemCode(sortPlan.getItemCode());
				// TC/DC区分
				wInstPlanKey.setTcdcFlag(sortPlan.getTcdcFlag());
				// 状態フラグ：削除以外
				wInstPlanKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
					
				// 入荷予定情報を取得
				InstockPlan wInstock = (InstockPlan) wInstPlanHandler.findPrimary(wInstPlanKey);
				
				
				// 前作業（入荷）がある場合、
				// 該当するデータが一部完了または完了の場合、前作業が開始されているので
				// 次作業情報の仕分予定一意キーには、ダミーキーをセットし、入荷→仕分の作業の
				// 紐付きを解除する
				
				// 予定一意キー
				nextInfo.setPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
				// 仕分予定一意キー
				nextInfo.setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);

				// 次作業情報を作成する
				// 入荷予定情報が存在する場合
				if (wInstock != null)
				{
					if (wInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART))
					{
						// 予定一意キー
						nextInfo.setPlanUkey(wInstock.getInstockPlanUkey());
						// 仕分予定一意キー
						nextInfo.setSortingPlanUkey(sortPlan.getSortingPlanUkey());
					}
					// 入荷予定日
					nextInfo.setInstPlanDate(sortPlan.getPlanDate());
					
				}
				// 入荷予定情報が存在しない場合
				else
				{
					// 仕分予定一意キー
					nextInfo.setSortingPlanUkey(sortPlan.getSortingPlanUkey());
				}

					
				// 出荷パッケージありの場合
				if (this.getIsExistShippingPack())
				{
					// 出荷予定情報を検索する
					// 検索条件を設定する
					wShipPlanKey.KeyClear();
					// 荷主コード
					wShipPlanKey.setConsignorCode(sortPlan.getConsignorCode());
					// 入荷予定日
					wShipPlanKey.setPlanDate(sortPlan.getPlanDate());
					// 出荷先コード
					wShipPlanKey.setCustomerCode(sortPlan.getCustomerCode());
					// 出荷伝票No.
					wShipPlanKey.setShippingTicketNo(sortPlan.getShippingTicketNo());
					// 出荷伝票行No.
					wShipPlanKey.setShippingLineNo(sortPlan.getShippingLineNo());
					// TC/DC区分
					wInstPlanKey.setTcdcFlag(sortPlan.getTcdcFlag());
					// 仕入先コード
					wShipPlanKey.setSupplierCode(sortPlan.getSupplierCode());
					// 状態フラグ：未開始
					wShipPlanKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);
					
					// 出荷予定情報を取得
					ShippingPlan wShipping = (ShippingPlan) wShipPlanHandler.findPrimary(wShipPlanKey);
						
					// 出荷予定情報が存在する場合
					if (wShipping != null)
					{
						// 出荷予定一意キー
						nextInfo.setShippingPlanUkey(wShipping.getShippingPlanUkey());
					}
					else
					{
						// 出荷予定一意キー（ダミー）
						nextInfo.setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					}
						
					// 出荷予定一意キーを取得
					shipping_plan_ukey = nextInfo.getShippingPlanUkey();
						
				}
				// 出荷パッケージなしの場合
				else
				{
					// 入荷予定日
					nextInfo.setInstPlanDate(sortPlan.getPlanDate());
				}
	
				// 自作業区分
				nextInfo.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
					
				createNextProc(pProcessName, sortPlan, nextInfo);
	
			}
			
			return shipping_plan_ukey;
			
		}
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

	}
	
	/**
	 * 仕分→出荷データを作成する
	 * 
	 * @param pShippingPlanUkey 出荷予定一意キー
	 * @param pProcessName 登録処理名
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void createSortingNextProc(SortingPlan sortPlan, String pShippingPlanUkey, String pProcessName) throws ReadWriteException
	{
		try
		{
			// 次作業情報を検索する
			// 検索条件を設定する
			wNextKey.KeyClear();
			// 自作業区分：仕分
			wNextKey.setWorkKind(NextProcessInfo.JOB_TYPE_SORTING);
			// 荷主コード
			wNextKey.setConsignorCode(sortPlan.getConsignorCode());
			// 出荷予定日
			wNextKey.setShipPlanDate(sortPlan.getPlanDate());
			// 出荷先コード
			wNextKey.setCustomerCode(sortPlan.getCustomerCode());
			// 出荷伝票No.
			wNextKey.setShippingTicketNo(sortPlan.getShippingTicketNo());
			// 出荷伝票行No.
			wNextKey.setShippingLineNo(sortPlan.getShippingLineNo());
			// 仕入先コード
			wNextKey.setSupplierCode(sortPlan.getSupplierCode());
			// 入荷伝票No.
			wNextKey.setInstockTicketNo(sortPlan.getInstockTicketNo());
			// 入荷伝票行No.
			wNextKey.setInstockLineNo(sortPlan.getInstockLineNo());
			// 商品コード
			wNextKey.setItemCode(sortPlan.getItemCode());
			// TC/DC区分
			wNextKey.setTcdcFlag(sortPlan.getTcdcFlag());
	
			// 検索結果を取得
			NextProcessInfo nextInfo = (NextProcessInfo) wNextHandler.findPrimaryForUpdate(wNextKey);
		
			// 次作業情報が存在する場合
			if (nextInfo != null)
			{
				// 更新条件を設定する
				wNextAltKey.KeyClear();
				// 次作業情報一意キー
				wNextAltKey.setNextProcUkey(nextInfo.getNextProcUkey());
			
				// 更新値を設定する
				// 仕分予定一意キー
				wNextAltKey.updatePlanUkey(sortPlan.getSortingPlanUkey());
				// 最終更新処理名
				wNextAltKey.updateLastUpdatePname(pProcessName);
			
				// 次作業情報を更新する
				wNextHandler.modify(wNextAltKey);
			}
			else
			{
				// 登録用の処理
				nextInfo = new NextProcessInfo();
				
				// 入荷予定情報を検索する
				// 検索条件を設定する
				wInstPlanKey.KeyClear();
				// 荷主コード
				wInstPlanKey.setConsignorCode(sortPlan.getConsignorCode());
				// 入荷予定日
				wInstPlanKey.setPlanDate(sortPlan.getPlanDate());
				// 仕入先コード
				wInstPlanKey.setSupplierCode(sortPlan.getSupplierCode());
				// 入荷伝票No.
				wInstPlanKey.setInstockTicketNo(sortPlan.getInstockTicketNo());
				// 入荷伝票行No.
				wInstPlanKey.setInstockLineNo(sortPlan.getInstockLineNo());
				// 商品コード
				wInstPlanKey.setItemCode(sortPlan.getItemCode());
				// TC/DC区分
				wInstPlanKey.setTcdcFlag(sortPlan.getTcdcFlag());
				// 状態フラグ：削除以外
				wInstPlanKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
					
				// 入荷予定情報を取得
				InstockPlan wInstock = (InstockPlan) wInstPlanHandler.findPrimary(wInstPlanKey);

				// 次作業情報を作成する
				// 入荷予定情報が存在する場合
				if (wInstock != null)
				{
					// 入荷予定日
					nextInfo.setInstPlanDate(sortPlan.getPlanDate());
				}
				else
				{
					// 入荷予定日
					nextInfo.setInstPlanDate(null);
				}
				
				// 予定一意キー
				nextInfo.setPlanUkey(sortPlan.getSortingPlanUkey());
				// 仕分予定一意キー
				nextInfo.setSortingPlanUkey(null);
				// 出荷予定一意キー
				nextInfo.setShippingPlanUkey(pShippingPlanUkey);
				// 自作業区分
				nextInfo.setWorkKind(NextProcessInfo.JOB_TYPE_SORTING);

				
				
				createNextProc(pProcessName, sortPlan, nextInfo);
			}
		}
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
	}
	
	/**
	 * 次作業情報を作成する
	 * 
	 * @param pProcessName 登録処理名
	 * @param instPlan 仕分予定データ
	 * @param wNextInfoParam 一意キー
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void createNextProc(String pProcessName, SortingPlan sortPlan, 
	                              NextProcessInfo wNextInfoParam) throws ReadWriteException
	{
		
		try
		{
			NextProcessInfo wNextInfo =  new NextProcessInfo();
			//次作業シーキエンス
            String nextUkey_seqno = wSequenceHandler.nextNextProcKey();
			// 次作業情報一意キー
			wNextInfo.setNextProcUkey(nextUkey_seqno);
			// 予定一意キー
			wNextInfo.setPlanUkey(wNextInfoParam.getPlanUkey());
			// 仕分予定一意キー
			wNextInfo.setSortingPlanUkey(wNextInfoParam.getSortingPlanUkey());
			// 出荷予定一意キー
			wNextInfo.setShippingPlanUkey(wNextInfoParam.getShippingPlanUkey());
			// 自作業区分
			wNextInfo.setWorkKind(wNextInfoParam.getWorkKind());
			// 作業予定数
			wNextInfo.setPlanQty(sortPlan.getPlanQty());
			// 作業実績数
			wNextInfo.setResultQty(0);
			// 状態フラグ：未処理
			wNextInfo.setStatusFlag(NextProcessInfo.STATUS_FLAG_UNPROCESSING);
			// 荷主コード
			wNextInfo.setConsignorCode(sortPlan.getConsignorCode());
			// 入荷予定日
			wNextInfo.setInstPlanDate(wNextInfoParam.getInstPlanDate());
			// 商品コード
			wNextInfo.setItemCode(sortPlan.getItemCode());
			// TC/DC区分
			wNextInfo.setTcdcFlag(sortPlan.getTcdcFlag());
			// 仕入先コード
			wNextInfo.setSupplierCode(sortPlan.getSupplierCode());
			// 入荷伝票No.
			wNextInfo.setInstockTicketNo(sortPlan.getInstockTicketNo());
			// 入荷伝票行No.
			wNextInfo.setInstockLineNo(sortPlan.getInstockLineNo());
			// 出荷予定日
			wNextInfo.setShipPlanDate(sortPlan.getPlanDate());
			// 出荷先コード
			wNextInfo.setCustomerCode(sortPlan.getCustomerCode());
			// 出荷先伝票No.
			wNextInfo.setShippingTicketNo(sortPlan.getShippingTicketNo());
			// 出荷先行No.
			wNextInfo.setShippingLineNo(sortPlan.getShippingLineNo());
			// 登録処理名
			wNextInfo.setRegistPname(pProcessName);
			// 最終更新処理名
			wNextInfo.setLastUpdatePname(pProcessName);
			
			// 次作業情報を登録する
			wNextHandler.create(wNextInfo);
			
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

}
//end of class
