package jp.co.daifuku.wms.sorting.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.sorting.dbhandler.SortingWorkingInformationHandler;

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
 * 　＜登録処理＞
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
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:23:01 $
 * @author  $Author: suresh $
 */
public class SortingPlanRegistSCH extends AbstractSortingSCH
{

	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	private static String wProcessName = "SortingPlanRegistSCH";
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/21 04:23:01 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public SortingPlanRegistSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
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

		// 返却データ
		SortingParameter resultParam = new SortingParameter();

		// 仕分予定情報ハンドラ類のインスタンス生成
		SortingPlanReportFinder planFinder = new SortingPlanReportFinder(conn);
		SortingPlanSearchKey planKey = new SortingPlanSearchKey();
		SortingPlan[] sortPlan = null;

		// 検索条件を設定する。
		// 状態フラグ＝「削除」以外
		planKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
		// グルーピング条件に荷主コードをセット
		planKey.setConsignorCodeGroup(1);
		planKey.setConsignorCodeCollect("");

		planFinder.open();
		if (planFinder.search(planKey) == 1)
		{
			// 検索条件を設定する。				
			planKey.KeyClear();
			// 状態フラグ＝「削除」以外
			planKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
			// ソート順に登録日時を設定
			planKey.setRegistDateOrder(1, false);
			
			planKey.setConsignorCodeCollect("");
			planKey.setConsignorNameCollect("");

			if (planFinder.search(planKey) >= 0)
			{
				// 登録日時が最も新しい荷主名称を取得します。
				sortPlan = (SortingPlan[]) planFinder.getEntities(1);
				resultParam.setConsignorCode(sortPlan[0].getConsignorCode());
				resultParam.setConsignorName(sortPlan[0].getConsignorName());
			}
		}
		planFinder.close();

		return resultParam;
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

		// 入力エリアの内容
		SortingParameter inputParam = (SortingParameter) checkParam;
		// ためうちエリアの内容
		SortingParameter[] listParam = (SortingParameter[]) inputParams;

		// ためうちエリア表示件数のチェック
		if (listParam != null && listParam.length == WmsParam.MAX_NUMBER_OF_DISP)
		{
			// 6023096 = 件数が{0}件を超えるため、入力できません。
			wMessage = "6023096" + wDelim + MAX_NUMBER_OF_DISP_DISP;
			return false;
		}

		// ケースピース区分にケースまたはピース以外がセットされていた場合はエラー
		if (!inputParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE)
			&& !inputParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
		{
			// 6006009=範囲外の値を指定されました。セットできません。Class={0} Variable={1} Value={2}
			// LBL-W0015=ケース／ピース区分
			RmiMsgLogClient.write("6006009" + wDelim + wProcessName + wDelim + DisplayText.getText("LBL-W0015") + wDelim + inputParam.getCasePieceFlag(), this.getClass().getName());
			// 6023145 = ケース／ピース区分は、指定範囲内の値を入力してください。
			throw new ScheduleException("6023145");
		}

		// TC/DC区分にDCまたはクロスTC以外がセットされていた場合はエラー
		if (!inputParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC)
			&& !inputParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			// 6006009=範囲外の値を指定されました。セットできません。Class={0} Variable={1} Value={2}
			// LBL-W0259=ＴＣ／ＤＣ区分
			RmiMsgLogClient.write("6006009" + wDelim + wProcessName + wDelim + DisplayText.getText("LBL-W0259") + wDelim + inputParam.getTcdcFlag(), this.getClass().getName());
			throw new ScheduleException("6006009" + wDelim + wProcessName + wDelim + DisplayText.getText("LBL-W0259") + wDelim + inputParam.getTcdcFlag());
		}

		// ケース入数が0の場合、ケース区分は選択できない
		if (inputParam.getEnteringQty() <= 0 && inputParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
		{
			// 6023345=ケース入数が0の場合、ケース／ピース区分にケースは選択できません。
			wMessage = "6023345";
			return false;
		}

		// ケース区分の場合
		if(inputParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
		{
			// ホスト予定ピース数に入力できない
			if(inputParam.getPlanPieceQty() > 0)
			{
				// 6023346=ケース／ピース区分が{0}の場合、{1}の入力はできません。
				// LBL-W0375=ケース LBL-W0386=ホスト予定ピース数
				wMessage = "6023346" + wDelim + DisplayText.getText("LBL-W0375") + wDelim + DisplayText.getText("LBL-W0386");
				return false;
			}
				
			// ホスト予定ケース数に入力があること
			if(inputParam.getPlanCaseQty() <= 0)
			{
				// 6023057={0}には{1}以上の値を入力してください。
				// LBL-W0385=ホスト予定ケース数
				wMessage = "6023057" + wDelim + DisplayText.getText("LBL-W0385") + wDelim + 1;
				return false;
			}
		}

		// ピース区分の場合
		if(inputParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
		{
			// ホスト予定ケース数に入力にできない
			if(inputParam.getPlanCaseQty() > 0)
			{
				// 6023346=ケース／ピース区分が{0}の場合、{1}の入力はできません。
				// LBL-W0376=ピース LBL-W0385=ホスト予定ケース数
				wMessage = "6023346" + wDelim + DisplayText.getText("LBL-W0376") + wDelim + DisplayText.getText("LBL-W0385");
				return false;
			}
				
			// ホスト予定ピース数に入力があること
			if(inputParam.getPlanPieceQty() <= 0)
			{
				// 6023057={0}には{1}以上の値を入力してください。
				// LBL-W0386=ホスト予定ピース数
				wMessage = "6023057" + wDelim + DisplayText.getText("LBL-W0386") + wDelim + 1;
				return false;
			}
		}

		// TC/DC区分にクロスTC指定時、仕入先コード、入荷伝票No.、入荷伝票行No.は必須
		if (inputParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			if (StringUtil.isBlank(inputParam.getSupplierCode()))
			{
				// 6023005=仕入先コードを入力してください。
				wMessage = "6023005";
				return false;
			}
			if (StringUtil.isBlank(inputParam.getInstockTicketNo()))
			{
				// 6023094=入荷伝票№を入力してください。
				wMessage = "6023094";
				return false;
			}
			if (StringUtil.isBlank(Integer.toString(inputParam.getInstockLineNo())))
			{
				// 6023095=入荷伝票行№を入力してください。
				wMessage = "6023095";
				return false;
			}
		}

		// オーバーフローチェック
		if ((long) inputParam.getEnteringQty() * (long) inputParam.getPlanCaseQty() + (long) inputParam.getPlanPieceQty() > WmsParam.MAX_TOTAL_QTY)
		{
			// 6023058 = {0}には{1}以下の値を入力してください。
			// LBL-W0138=仕分予定数（バラ総数）
			wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0138") + wDelim + MAX_TOTAL_QTY_DISP;
			return false;
		}

		// ためうちとの重複チェックを行う
		if (listParam != null)
		{
			if (!dupplicationCheck(conn, inputParam, listParam))
			{
				// 6023142=同一情報が既に入力されています。
				wMessage = "6023142";
				return false;
			}
		}

		// 状態フラグが未開始または削除以外の同一情報が仕分予定情報に存在しないこと
		SortingPlan[] sortPlan = null;
		SortingPlanOperator sortPlanOperator = new SortingPlanOperator(conn);
		inputParam.setCaseSortingLocation(inputParam.getSortingLocation());
		inputParam.setPieceSortingLocation(inputParam.getSortingLocation());
		sortPlan = sortPlanOperator.findSortingPlan(inputParam);
		if (sortPlan != null && sortPlan.length != 0)
		{
			for (int i = 0; i < sortPlan.length; i++)
			{
				// 作業中
				if (sortPlan[i].getStatusFlag().equals(SortingPlan.STATUS_FLAG_NOWWORKING))
				{
					// 6023269 = 作業中の同一データが存在するため、入力できません。
					wMessage = "6023269";
					return false;
				}
				// 一部受付
				if (sortPlan[i].getStatusFlag().equals(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART))
				{
					// 6023270 = 一部受付済みの同一データが存在するため、入力できません。
					wMessage = "6023270";
					return false;
				}
				// 完了
				if (sortPlan[i].getStatusFlag().equals(SortingPlan.STATUS_FLAG_COMPLETION))
				{
					// 6023337 = 完了の同一データが存在するため、入力できません。
					wMessage = "6023337";
					return false;
				}
					
				if (sortPlan[i].getStatusFlag().equals(SortingPlan.STATUS_FLAG_START))
				{
					// 6023090 = 既に同一データが存在するため、入力できません。
					wMessage = "6023090";
					return false;
				}
			}
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

		SortingParameter inputParam = (SortingParameter) checkParam;

		// 必須入力チェック
		if (StringUtil.isBlank(inputParam.getWorkerCode())
			|| StringUtil.isBlank(inputParam.getPassword())
			|| StringUtil.isBlank(inputParam.getConsignorCode())
			|| StringUtil.isBlank(inputParam.getPlanDate())
			|| inputParam.getEnteringQty() < 0
			|| inputParam.getBundleEnteringQty() < 0)
		{
			// 6006001=予期しないエラーが発生しました。{0}
			RmiMsgLogClient.write("6006001" + wDelim + wProcessName, this.getClass().getName());
			throw (new ScheduleException("mandatory error!"));
		}

		// 作業者コードとパスワードのチェック
		if (!checkWorker(conn, inputParam))
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
		// パラメータの入力情報
		SortingParameter[] inputParam = (SortingParameter[]) startParams;

		// 日次更新処理中のチェック
		if (isDailyUpdate(conn))
		{
			return false;
		}
		// 取り込みフラグチェック処理 false：取り込み中	
		if (isLoadingData(conn))
		{
			return false;
		}

		// DBのcommit,rollbackの判断用
		boolean registFlag = false;
		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;
		
		try
		{
			// 取り込みフラグ更新：取り込み中
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn, wProcessName);
			updateLoadDataFlag = true;

			SortingWorkingInformationHandler wiHandle = new SortingWorkingInformationHandler(conn);
			if (!wiHandle.lockPlanData(startParams))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return false;
			}

			// 作業者名を取得する。
			String workerName = "";
			workerName = getWorkerName(conn, inputParam[0].getWorkerCode());
			// バッチNo.を取得する
			SequenceHandler sequenceHandler = new SequenceHandler(conn);
			String batch_seqno = "";
			batch_seqno = sequenceHandler.nextSortingPlanBatchNo();

			SortingPlanOperator sortPlanOperator = new SortingPlanOperator(conn);
			SortingPlan[] sortPlan = null;
			
			for (int i = 0; i < inputParam.length; i++)
			{
				// 仕分場所をセットする
				inputParam[i].setCaseSortingLocation(inputParam[i].getSortingLocation());
				inputParam[i].setPieceSortingLocation(inputParam[i].getSortingLocation());

				// 既に登録されている仕分予定情報を取得する。
				sortPlan = sortPlanOperator.findSortingPlanForUpdate(inputParam[i]);
				
				// 既に登録されている場合は、既存のデータを削除状態にしてから登録する
				if (sortPlan != null && sortPlan.length != 0)
				{
					for (int j = 0; j < sortPlan.length; j++)
					{
						// 一部完了の場合は登録できない
						if (sortPlan[j].getStatusFlag().equals(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART))
						{
							// 6023273=No.{0}{1}
							// 6023270 = 一部受付済みの同一データが存在するため、入力できません。
							wMessage = "6023273" + wDelim + inputParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023270");
							return false;
						}
						// 開始済・作業中の場合は登録できない
						else if ((sortPlan[j].getStatusFlag().equals(SortingPlan.STATUS_FLAG_START)) || (sortPlan[j].getStatusFlag().equals(SortingPlan.STATUS_FLAG_NOWWORKING)))
						{
							// 6023273=No.{0}{1}
							// 6023269=作業中の同一データが存在するため、入力できません。
							wMessage = "6023273" + wDelim + inputParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023269");
							return false;
						}
						// 完了の場合登録できない
						else if (sortPlan[j].getStatusFlag().equals(SortingPlan.STATUS_FLAG_COMPLETION))
						{
							// 6023273=No.{0}{1}
							// 6023090 = 既に同一データが存在するため、入力できません。
							wMessage = "6023273" + wDelim + inputParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023090");
							return false;
						}
					
						// 仕分予定情報、作業情報、在庫情報の状態を削除に更新する。
						sortPlanOperator.updateSortingPlan(sortPlan[j].getSortingPlanUkey(), wProcessName);
					}
				}

				// パラメータに登録する値をセットする。
				// 仕分予定数(バラ総数)
				inputParam[i].setTotalPlanQty(inputParam[i].getPlanCaseQty() * inputParam[i].getEnteringQty() + inputParam[i].getPlanPieceQty());
				// 作業者コード
				inputParam[i].setWorkerCode(inputParam[0].getWorkerCode());
				// 作業者名
				inputParam[i].setWorkerName(workerName);
				// バッチNo.
				inputParam[i].setBatchNo(batch_seqno);
				// 端末No.
				inputParam[i].setTerminalNumber(inputParam[0].getTerminalNumber());
				// 登録区分
				inputParam[i].setRegistKbn(SystemDefine.REGIST_KIND_WMS);
				// 登録処理名
				inputParam[i].setRegistPName(wProcessName);

				// 入力情報を新規に登録する(仕分予定情報、作業情報、在庫情報)
				sortPlanOperator.createSortingPlan(inputParam[i]);

			}

			// 6001003 = 登録しました。
			wMessage = "6001003";
			registFlag = true;
			return true;
		}
		catch (ReadWriteException e)
		{
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			doRollBack(conn, wProcessName);
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			// 登録に失敗した場合はトランザクションをロールバックする
			if (!registFlag)
			{
				doRollBack(conn, wProcessName);
			}
			// 取り込み中フラグが自クラスで更新されたものであるならば、
			// 取り込み中フラグを、0：停止中にする
			if( updateLoadDataFlag )
			{
				// 取り込み中フラグ更新：停止中
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * ためうちに入力データを入力できるかどうかをチェックします。<BR>
	 * 荷主コード、仕分予定日、商品コード、ｹｰｽﾋﾟｰｽ区分、クロス/DC、
	 * 出荷先コード、仕分場所が同一チェックのキーになります。<BR>
	 * 同一データがあった場合は、入力できないのでfalseを返します。<BR>
	 * 同一データがなく、入力できる場合はtrueを返します。
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param inputParam 入力エリアの入力情報
	 * @param listParam ためうちエリアの情報
	 * @return ためうちにデータを入力できるかどうか
	 */
	protected boolean dupplicationCheck(Connection conn, SortingParameter inputParam, SortingParameter[] listParam) throws ReadWriteException
	{
		try
		{
			// システム定義情報ハンドラ類のインスタンス生成
			WareNaviSystemHandler wHandler = new WareNaviSystemHandler(conn);
			WareNaviSystemSearchKey wKey = new WareNaviSystemSearchKey();
			WareNaviSystem[] warenavi = (WareNaviSystem[]) wHandler.find(wKey);
		
		
			for (int i = 0; i < listParam.length; i++)
			{
				// クロスドックパッケージありの場合
				if (warenavi[0].getCrossdockPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON) && listParam[i].getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
				{
					// 同一情報があった場合、エラーとなる
					if (listParam[i].getCasePieceFlag().equals(inputParam.getCasePieceFlag())
						&& listParam[i].getTcdcFlag().equals(inputParam.getTcdcFlag())
						&& listParam[i].getCustomerCode().equals(inputParam.getCustomerCode())
						&& listParam[i].getSupplierCode().equals(inputParam.getSupplierCode())
						&& listParam[i].getShippingTicketNo().equals(inputParam.getShippingTicketNo())
						&& listParam[i].getShippingLineNo() == inputParam.getShippingLineNo())
					{
						return false;
					}
				}
				// クロスドックパッケージなしの場合
				else
				{
					// 同一情報があった場合、エラーとなる
					if (listParam[i].getCasePieceFlag().equals(inputParam.getCasePieceFlag())
						&& listParam[i].getTcdcFlag().equals(inputParam.getTcdcFlag())
						&& listParam[i].getCustomerCode().equals(inputParam.getCustomerCode())
						&& listParam[i].getSupplierCode().equals(inputParam.getSupplierCode())
						&& listParam[i].getSortingLocation().equals(inputParam.getSortingLocation()))
					{
						return false;
					}
				}
			}

			return true;
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	// Private methods -----------------------------------------------
}
//end of class
