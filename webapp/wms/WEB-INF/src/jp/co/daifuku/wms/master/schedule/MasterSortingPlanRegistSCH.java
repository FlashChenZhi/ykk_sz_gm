package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.merger.SortingPlanMGWrapper;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.CustomerOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.master.operator.SupplierOperator;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura  <BR>
 * <BR>
 * WEB仕分予定データ登録を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、仕分予定データ登録処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * <U><B>1.初期表示処理（<CODE>initFind()</CODE>メソッド）</B></U><BR>
 * <DIR>
 *   入荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 *   <BR>
 *   ＜検索条件＞ <BR>
 *   <DIR>
 *     状態フラグ：削除(9)以外
 *   </DIR>
 * </DIR>
 * <BR>
 * 
 * <U><B>2.次へボタン押下処理 (<CODE>nextCheck()</CODE>メソッド）</B></U><BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、入力チェックの結果を返します。 <BR>
 *   問題がない場合はtrueを返します。パラメータの内容に問題がある場合はfalseを返します。
 *   荷主マスタ、出荷先マスタ、仕入先マスタの存在チェックを行い<BR>
 *   データが存在しなければFalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
 *   また、必須項目が未入力の場合(数値項目は0未満の場合)、<CODE>ScheduleException</CODE>を返します。<BR>
 *   <BR>
 *   ＜入力データ＞ <BR>
 *   * 必須の入力項目 <BR>
 *   + どちらかが必須の入力項目 <BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th>		<td></td></tr>
 *     <tr><td>作業者コード</td><td>：</td><td>WorkerCode</td>		<td>*</td></tr>
 *     <tr><td>パスワード</td><td>：</td><td>WorkerName</td>		<td>*</td></tr>
 *     <tr><td>荷主コード</td><td>：</td><td>ConsignorCode</td>		<td>*</td></tr>
 *     <tr><td>仕分予定日</td><td>：</td><td>PlanDate</td>			<td>*</td></tr>
 *     <tr><td>商品コード</td><td>：</td><td>ItemCode</td>			<td>*</td></tr>
 *     <tr><td>ケース入数</td><td>：</td><td>EnteringQty</td>		<td>*</td></tr>
 *     <tr><td>ボール入数</td><td>：</td><td>BundleEnteringQty</td>		<td>*</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 *   ＜チェック内容＞ <BR>
 *   <DIR>
 *     1.作業者コードとパスワードがDmWorkerに登録されているものと一致すること <BR>
 *   </DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 
 * <U><B>3.入力ボタン押下処理（<CODE>check()</CODE>メソッド）</B></U><BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、
 *   入力チェック・オーバーフローチェック・重複(ためうち・DB)チェックを行い、チェック結果を返します。 <BR>
 *   荷主マスタ、出荷先マスタ、仕入先マスタの存在チェックを行い<BR>
 *   データが存在しなければFalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   荷主コード、仕分予定日、商品コード、ケースピース区分、TC/DC区分、出荷先コード、
 *   仕分場所をキーに重複チェックを行います。  <BR>
 *   状態フラグが未開始または削除の同一情報が存在した場合は重複エラーとせず、trueを返します。 <BR>
 *   また、必須項目が未入力の場合、<CODE>ScheduleException</CODE>を返します。<BR>
 *   <BR>
 *   ＜入力データ＞ <BR>
 *   * 必須の入力項目 <BR>
 *   + 条件によって必須項目<BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th>			<td></td></tr>
 *     <tr><td>荷主コード</td><td>：</td><td>ConsignorCode</td>			<td>*</td></tr>
 *     <tr><td>仕分予定日</td><td>：</td><td>PlanDate</td>				<td>*</td></tr>
 *     <tr><td>商品コード</td><td>：</td><td>ItemCode</td>				<td>*</td></tr>
 *     <tr><td>ケース入数</td><td>：</td><td>EnteringQty</td>			<td>*</td></tr>
 *     <tr><td>ケースピース区分</td><td>：</td><td>CasePieceFlag</td>	<td>*</td></tr>
 *     <tr><td>クロス/DC</td><td>：</td><td>TcdcFlag</td>				<td>*</td></tr>
 *     <tr><td>出荷先コード</td><td>：</td><td>CustomerCode</td>		<td>*</td></tr>
 *     <tr><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td>	<td>+</td></tr>
 *     <tr><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td>	<td>+</td></tr>
 *     <tr><td>仕分場所</td><td>：</td><td>SortingLocation</td>			<td>*</td></tr>
 *     <tr><td>仕入先コード</td><td>：</td><td>SupplierCode</td>		<td>+</td></tr>
 *     <tr><td>入荷伝票No.</td><td>：</td><td>InstockTicketNo</td>		<td>+</td></tr>
 *     <tr><td>入荷伝票行No.</td><td>：</td><td>InstockLineNo</td>		<td>+</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 *   ＜チェック内容＞ <BR>
 *   <DIR>
 *     1.表示件数がためうちエリアの最大表示件数をこえないこと。<BR>
 *     2.ケース入数が0以下の場合、ケース品は登録できないこと。(ケースピース区分) <BR>
 *     3.ケース区分の場合、ホスト予定ピース数は0であること。<BR>
 *     4.ケース区分の場合、ホスト予定ケース数は0より大きいこと。<BR>
 *     5.ピース区分の場合、ホスト予定ケース数は0であること。<BR>
 *     6.ピース区分の場合、ホスト予定ピース数は0より大きいこと。<BR>
 *     7.TC/DC区分がクロスTCの場合、仕入先コード、入荷伝票No.、入荷伝票行No.が入力されていること。 <BR>
 *     8.総仕分数がオーバーフローしないこと。<BR>
 *     9.同一情報がためうちエリアに存在しないこと。<BR>
 *     10.状態フラグが未開始または削除以外の同一情報が仕分予定情報に存在しないこと。<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 
 * <U><B>4.登録ボタン押下処理（<CODE>startSCH()</CODE>メソッド）</B></U><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、仕分予定データ登録処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   荷主コード、仕分予定日、商品コード、ケースピース区分、TC/DC区分、出荷先コード、
 *   仕分場所をキーに重複チェックを行います。  <BR>
 *   状態フラグが未開始または削除の同一情報が存在した場合は排他エラーとせず、処理を行います。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜入力データ＞ <BR>
 *   * 必須の入力項目 <BR>
 *   + どちらかが必須の入力項目 <BR>
 *   # 更新に必要な項目 <BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th>			<td></td></tr>
 *     <tr><td>作業者コード</td><td>：</td><td>WorkerCode</td>			<td>*#</td></tr>
 *     <tr><td>パスワード</td><td>：</td><td>WorkerName</td>			<td>*</td></tr>
 *     <tr><td>荷主コード</td><td>：</td><td>ConsignorCode</td>			<td>*#</td></tr>
 *     <tr><td>荷主名称</td><td>：</td><td>ConsignorName</td>			<td>#</td></tr>
 *     <tr><td>仕分予定日</td><td>：</td><td>PlanDate</td>				<td>*#</td></tr>
 *     <tr><td>商品コード</td><td>：</td><td>ItemCode</td>				<td>*#</td></tr>
 *     <tr><td>商品名称</td><td>：</td><td>ItemName</td>				<td>#</td></tr>
 *     <tr><td>ケース入数</td><td>：</td><td>EnteringQty</td>			<td>*#</td></tr>
 *     <tr><td>ボール入数</td><td>：</td><td>BundleEnteringQty</td>		<td>#</td></tr>
 *     <tr><td>ケースITF</td><td>：</td><td>ITF</td>					<td>#</td></tr>
 *     <tr><td>ボールITF</td><td>：</td><td>BundleITF</td>				<td>#</td></tr>
 *     <tr><td>ケースピース区分</td><td>：</td><td>CasePieceFlag</td>	<td>*#</td></tr>
 *     <tr><td>クロス/DC</td><td>：</td><td>TcdcFlag</td>				<td>*#</td></tr>
 *     <tr><td>出荷先コード</td><td>：</td><td>CustomerCode</td>		<td>*#</td></tr>
 *     <tr><td>出荷先名称</td><td>：</td><td>CustomerName</td>			<td>#</td></tr>
 *     <tr><td>出荷伝票No.</td><td>：</td><td>ShippingTicketNo</td>		<td>#</td></tr>
 *     <tr><td>出荷伝票行No.</td><td>：</td><td>ShippingLineNo</td>		<td>#</td></tr>
 *     <tr><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td>	<td>+#</td></tr>
 *     <tr><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td>	<td>+#</td></tr>
 *     <tr><td>仕分場所</td><td>：</td><td>SortingLocation</td>			<td>*#</td></tr>
 *     <tr><td>仕入先コード</td><td>：</td><td>SupplierCode</td>		<td>+#</td></tr>
 *     <tr><td>仕入先名称</td><td>：</td><td>SupplierNode</td>			<td>#</td></tr>
 *     <tr><td>入荷伝票No.</td><td>：</td><td>InstockTicketNo</td>		<td>+#</td></tr>
 *     <tr><td>入荷伝票行No.</td><td>：</td><td>InstockLineNo</td>		<td>+#</td></tr>
 *     <tr><td>端末No.</td><td>：</td><td>TerminalNumber</td>			<td>+#</td></tr>
 *     <tr><td>ためうち行No.</td><td>：</td><td>RowNo</td>				<td>+#</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 *   ＜処理条件チェック＞ <BR>
 *   <DIR>
 *     1.作業者コードとパスワードがDmWorkerに登録されているものと一致すること <BR>
 *     2.日次更新処理中でないこと。 <BR>
 *     3.状態フラグが未開始または削除以外の同一情報が仕分予定情報に存在しないこと。<BR>
 *   </DIR>
 *   <BR>
 *   ＜更新処理＞<BR>
 *   <DIR>
 *     更新処理は<CODE>InstockPlanOperator</CODE>クラスの<CODE>updateSortingPlan</CODE>メソッドで行います。 <BR>
 *     詳しくは<CODE>updateSortingPlan</CODE>メソッドのJavaDocを参照してください。 <BR>
 *   </DIR>
 *   ＜登録処理＞
 *   <DIR>
 *     登録処理は<CODE>SortingPlanOperator</CODE>クラスの<CODE>createSortingPlan</CODE>メソッドで行います。 <BR>
 *     詳しくは<CODE>createSortingPlan</CODE>メソッドのJavaDocを参照してください。 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/05</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterSortingPlanRegistSCH extends jp.co.daifuku.wms.sorting.schedule.SortingPlanRegistSCH
{
	/**
	 * 荷主マスタオペレータクラス
	 */
	private ConsignorOperator wConsignorOperator = null;
	
	/**
	 * 仕入先マスタオペレータクラス
	 */
	private SupplierOperator wSupplierOperator = null;
	
	/**
	 * 出荷先マスタオペレータクラス
	 */
	private CustomerOperator wCustomerOperator = null;
	
	/**
	 * 商品マスタオペレータクラス
	 */
	private ItemOperator wItemOperator = null; 
	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	private static String wProcessName = "MasterSortingPlanRegistSCH";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
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
	public MasterSortingPlanRegistSCH()
	{
		wMessage = null;
	}

	/**
	 * このクラスを使用してDBの検索・更新を行う場合はこのコンストラクタを使用してください。 <BR>
	 * 各DBの検索・更新に必要なインスタンスを作成します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public MasterSortingPlanRegistSCH(Connection conn) throws ReadWriteException, ScheduleException
	{
		wMessage = null;
		wConsignorOperator = new ConsignorOperator(conn);
		wSupplierOperator = new SupplierOperator(conn);
		wCustomerOperator = new CustomerOperator(conn);
		wItemOperator = new ItemOperator(conn);
	}
	
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 仕分予定情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>SortingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// 荷主初期表示設定を行います。
		SortingParameter tempParam = (SortingParameter) super.initFind(conn, searchParam);
		// 取得した荷主を返却パラメータにセットします。
		MasterSortingParameter wParam = new MasterSortingParameter();
		if (tempParam != null)
		{
			wParam.setConsignorCode(tempParam.getConsignorCode());
			wParam.setConsignorName(tempParam.getConsignorName());
		}
		else
		{
			wParam.setConsignorCode("");
			wParam.setConsignorName("");
		}

		// 補完タイプをセットします
		MasterPrefs masterPrefs = new MasterPrefs();
		if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_NONE)
		{
			wParam.setMergeType(MasterShippingParameter.MERGE_TYPE_NONE);
		}
		else if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE)
		{
			wParam.setMergeType(MasterShippingParameter.MERGE_TYPE_OVERWRITE);
		}
		else
		{
			wParam.setMergeType(MasterShippingParameter.MERGE_TYPE_FILL_EMPTY);
		}
		return wParam;
	}
	
	/**
	 * 画面へ表示するデータを取得します。表示ボタン押下時などの操作に対応するメソッドです。<BR>
	 * 必要に応じて、各継承クラスで実装してください。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>クラスを実装したインスタンス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		SortingPlan sortingPlan = new SortingPlan();
		
		MasterSortingParameter param = (MasterSortingParameter) searchParam;
		
		// 入力値から補完処理を行う
		sortingPlan.setConsignorCode(param.getConsignorCode());
		sortingPlan.setConsignorName(param.getConsignorName());
		sortingPlan.setCustomerCode(param.getCustomerCode());
		sortingPlan.setCustomerName1(param.getCustomerName());
		sortingPlan.setItemCode(param.getItemCode());
		sortingPlan.setItemName1(param.getItemName());
		sortingPlan.setSupplierCode(param.getSupplierCode());
		sortingPlan.setSupplierName1(param.getSupplierName());
		sortingPlan.setEnteringQty(param.getEnteringQty());
		sortingPlan.setBundleEnteringQty(param.getBundleEnteringQty());
		sortingPlan.setItf(param.getITF());
		sortingPlan.setBundleItf(param.getBundleITF());
		
		MergerWrapper merger = new SortingPlanMGWrapper(conn);
		merger.complete(sortingPlan);

		// 補完結果を返却パラメータにセットする
		ArrayList tempList = new ArrayList();
		if (sortingPlan != null)
		{
			MasterSortingParameter tempParam = (MasterSortingParameter) searchParam;
			
			tempParam.setConsignorName(sortingPlan.getConsignorName());
			tempParam.setCustomerName(sortingPlan.getCustomerName1());
			tempParam.setSupplierName(sortingPlan.getSupplierName1());
			tempParam.setItemName(sortingPlan.getItemName1());
			tempParam.setEnteringQty(sortingPlan.getEnteringQty());
			tempParam.setBundleEnteringQty(sortingPlan.getBundleEnteringQty());
			tempParam.setITF(sortingPlan.getItf());
			tempParam.setBundleITF(sortingPlan.getBundleItf());
			
			// 返却するマスタ情報の最終更新日時をセットする
			if (!StringUtil.isBlank(sortingPlan.getConsignorCode()))
			{
				MasterParameter mstParam = new MasterParameter();
				mstParam.setConsignorCode(sortingPlan.getConsignorCode());
				mstParam.setCustomerCode(sortingPlan.getCustomerCode());
				mstParam.setItemCode(sortingPlan.getItemCode());
				mstParam.setSupplierCode(sortingPlan.getSupplierCode());
				tempParam.setConsignorLastUpdateDate(wConsignorOperator.getLastUpdateDate(mstParam));
				tempParam.setCustomerLastUpdateDate(wCustomerOperator.getLastUpdateDate(mstParam));
				tempParam.setSupplierLastUpdateDate(wSupplierOperator.getLastUpdateDate(mstParam));
				tempParam.setItemLastUpdateDate(wItemOperator.getLastUpdateDate(mstParam));
			}
			tempList.add(tempParam);
		}
		MasterSortingParameter[] result = new MasterSortingParameter[tempList.size()];
		tempList.toArray(result);
		return result;
	}
	
	/** 
	 * 画面から入力された溜めうちエリアの内容をパラメータとして受け取り、 <BR>
	 * 必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
	 * 仕分予定情報に同一行No.の該当データが存在しなかった場合はtrueを、 <BR>
	 * 条件エラーが発生した場合や該当データが存在した場合は排他エラーとし、falseを返します。 <BR>
	 * 状態フラグが未開始または完了の同一行No.が存在した場合は排他エラーとせず、trueを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。 <BR>
	 *        SortingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        SortingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ScheduleException, ReadWriteException
	{
	    int iRet = 0;
		// 入力エリアの内容
	    MasterSortingParameter wParam = (MasterSortingParameter) checkParam;
		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(wParam.getConsignorCode());
		masterParam.setConsignorName (wParam.getConsignorName());
		masterParam.setItemCode(wParam.getItemCode());
		masterParam.setItemName(wParam.getItemName());

		// 荷主、商品に変更がないかをチェックする
		if (!checkModifyLastUpdateDate(masterParam, wParam, 0))
		{
			return false;
		}
		
		masterParam.setCustomerCode(wParam.getCustomerCode());
		masterParam.setCustomerName (wParam.getCustomerName());
		masterParam.setSupplierCode(wParam.getSupplierCode());
		masterParam.setSupplierName (wParam.getSupplierName());
		iRet = 	wConsignorOperator.checkConsignorMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if(iRet == MasterParameter.RET_NG){
				// 6023456 = 荷主コードがマスタに登録されていません。
				wMessage = "6023456";
				return false;
		    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023470=指定された荷主名称はすでに使用されているため登録できません。
				wMessage = "6023470";
				return false; 
		    }
		}
		
		iRet = 	wItemOperator.checkItemMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if(iRet == MasterParameter.RET_NG){
				// 6023459 = 商品コードがマスタに登録されていません。
				wMessage = "6023459";
				return false;
		    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023473=指定された商品名称はすでに使用されているため登録できません。
				wMessage = "6023473";
				return false; 
		    }
		}
		
		iRet = 	wCustomerOperator.checkCustomerMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if(iRet == MasterParameter.RET_NG){
				// 6023458 = 出荷先コードがマスタに登録されていません。
				wMessage = "6023458";
				return false;
		    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023472=指定された出荷先名称はすでに使用されているため登録できません。
				wMessage = "6023472";
				return false; 
		    }
		}
		iRet = 	wSupplierOperator.checkSupplierMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if(iRet == MasterParameter.RET_NG){
				// 6023457 = 仕入先コードがマスタに登録されていません。
				wMessage = "6023457";
				return false;
		    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023471=指定された仕入先名称はすでに使用されているため登録できません。
				wMessage = "6023471";
				return false; 
		    }
		}
		
		MasterPrefs masterPrefs = new MasterPrefs();
		SortingParameter wCheckParam = (SortingParameter) checkParam;

		if (masterPrefs.getMergeType() == MasterPrefs.MERGE_REGIST_TYPE_OVERWRITE)
		{
			wCheckParam.setLineCheckFlag(false);
		}
		if(!super.check(conn, wCheckParam, inputParams))
		{
			return false;
		}
		
		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		return true;
	}
	
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、作業者コード、パスワードのチェック、必須チェック結果を返します。 <BR>
	 * 作業者コード、パスワードの内容が正しい場合はtrueを返します。<BR>
	 * パラメータの内容に問題がある場合はfalseを返します。<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。 <BR>
	 *        SortingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
	    int iRet = 0;
	    SortingParameter wParam = (SortingParameter) checkParam;

		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(wParam.getConsignorCode());
		masterParam.setConsignorName (wParam.getConsignorName());
		masterParam.setItemCode(wParam.getItemCode());
		masterParam.setItemName(wParam.getItemName());
		
		iRet = wConsignorOperator.checkConsignorMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if(iRet == MasterParameter.RET_NG){
				// 6023456 = 荷主コードがマスタに登録されていません。
				wMessage = "6023456";
				return false;
		    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023470=指定された荷主名称はすでに使用されているため登録できません。
				wMessage = "6023470";
				return false; 
		    }
		}
		
		iRet = 	wItemOperator.checkItemMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
		    if(iRet == MasterParameter.RET_NG){
				// 6023459 = 商品コードがマスタに登録されていません。
				wMessage = "6023459";
				return false;
		    } else if(iRet == MasterParameter.RET_CONSIST_NAME_EXIST){
				// 6023473=指定された商品名称はすでに使用されているため登録できません。
				wMessage = "6023473";
				return false; 
		    }
		}
		
		// 既存のチェックを行う
		if (!super.nextCheck(conn, checkParam))
		{
			return false;
		}		

		return true;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、仕分予定データ登録スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        SortingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// 入力エリアの内容
		MasterSortingParameter[] params = (MasterSortingParameter[]) startParams;

		// 荷主、出荷先、商品、仕入先の排他チェックを行う
		for (int i = 0; i < params.length; i++)
		{
			MasterParameter masterParam = new MasterParameter();
			masterParam.setConsignorCode(params[i].getConsignorCode());
			masterParam.setConsignorName (params[i].getConsignorName());
			masterParam.setCustomerCode(params[i].getCustomerCode());
			masterParam.setCustomerName (params[i].getCustomerName());
			masterParam.setSupplierCode(params[i].getSupplierCode());
			masterParam.setSupplierName (params[i].getSupplierName());
			masterParam.setItemCode(params[i].getItemCode());
			masterParam.setItemName(params[i].getItemName());
			
			if (!checkModifyLastUpdateDate(masterParam, params[i], params[i].getRowNo()))
			{
				return false;
			}
		}
		
		// 既存の処理
		return super.startSCH(conn, startParams);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 他端末で、マスタ情報の更新が行われたかをチェックします。
	 * 
	 * @param param チェックする各コード・名称のセットされた
	 * @param inputParam 画面からの入力値（最終更新日時を取得するために使用）
	 * @param rowNo 行No.
	 * @return 他端末で更新されていない：true。他端末で更新された：false
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected boolean checkModifyLastUpdateDate(MasterParameter param, MasterSortingParameter inputParam, int rowNo)
							throws ReadWriteException
	{
		// 検索回数を減らすため、行No.が０か１の場合のみチェックを行います。
		// rowNo = 0 : 入力ボタン押下時のチェック
		// rowNo = 1 : 登録ボタン押下時のチェック
		if (rowNo == 0 || rowNo == 1)
		{
			// 荷主チェック
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				param.setLastUpdateDate(inputParam.getConsignorLastUpdateDate());
				if (wConsignorOperator.isModify(param))
				{
     		        // 6023489=指定された{0}は、他の端末で更新されたため入力できません。
					wMessage = "6023489" + wDelim + DisplayText.getText("CHK-W0025");
					return false;
				}
			}
			// 商品チェック
			if (!StringUtil.isBlank(param.getConsignorCode())
			 && !StringUtil.isBlank(param.getItemCode()))
			{
				param.setLastUpdateDate(inputParam.getItemLastUpdateDate());
				if (wItemOperator.isModify(param))
				{
     		        // 6023489=指定された{0}は、他の端末で更新されたため入力できません。
					wMessage = "6023489" + wDelim + DisplayText.getText("CHK-W0023");
					return false;
				}
			}
		}
		
		if (!StringUtil.isBlank(param.getConsignorCode())
		 && !StringUtil.isBlank(param.getCustomerCode()))
		{
			// 出荷先チェック
			param.setLastUpdateDate(inputParam.getCustomerLastUpdateDate());
			if (wCustomerOperator.isModify(param))
			{
 		        // 6023490=No.{0} 指定された{1}は、他の端末で更新されたため登録できません。
				wMessage = "6023490" + wDelim + rowNo + wDelim + DisplayText.getText("CHK-W0027");
				return false;
			}
		}
		
		if (!StringUtil.isBlank(param.getConsignorCode())
		 && !StringUtil.isBlank(param.getSupplierCode()))
		{
			// 仕入先チェック
			param.setLastUpdateDate(inputParam.getSupplierLastUpdateDate());
			if (wSupplierOperator.isModify(param))
			{
 		        // 6023490=No.{0} 指定された{1}は、他の端末で更新されたため登録できません。
				wMessage = "6023490" + wDelim + rowNo + wDelim + DisplayText.getText("CHK-W0026");
				return false;
			}
		}
		return true;
	}

}
//end of class
