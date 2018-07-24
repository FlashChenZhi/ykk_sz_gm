package jp.co.daifuku.wms.sorting.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * WEB仕分予定データ修正・削除を行うためのクラスです。 <BR>
 * 画面から入力された内容を受け取り、仕分予定データの修正・削除処理を行います。 <BR>
 * このクラスは、データベースの更新処理をおこないますが、トランザクションの確定は行いません。 <BR>
 * トランザクションの確定は呼び出し元で行ってください。<BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * <B><U>1.初期表示処理（<CODE>initFind(Connection, Parameter)</CODE>メソッド）</U></B><BR>
 * 　仕分予定情報に荷主コードが1件のみ存在した場合、
 *   該当する荷主コードを含む<CODE>Parameter</CODE>インスタンスを返します。 <BR>
 * 　該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 *   <BR>
 * 　＜検索条件＞
 *   <DIR>
 *     状態フラグ：未開始
 *   </DIR>
 * <BR>
 * <B><U>2.次へボタン押下処理①（<CODE>nextCheck(Connection, Parameter)</CODE>メソッド）</U></B><BR>
 * 　画面から入力された内容をパラメータとして受け取り、入力情報のチェックを行います。<BR>
 * 　入力が正しい場合はtrue、入力が不正な場合はfalseを返します。<BR>
 * 　エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
 * 　以下のチェックを行います。<BR>
 *   <DIR>
 *     1.作業者コード・パスワードのチェック。<BR>
 *   </DIR>
 *   <BR>
 * 　＜入力データ＞<BR>
 * 　* 必須の入力項目 <BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th>		<td></td></tr>
 *     <tr><td>作業者コード</td><td>：</td><td>WorkerCode</td>		<td>*</td></tr>
 *     <tr><td>パスワード</td><td>：</td><td>WorkerName</td>		<td>*</td></tr>
 *   </table>
 *   </DIR>
 * <BR>
 * <B><U>3.次へボタン押下処理②（<CODE>query(Connection, Parameter)</CODE>メソッド）</U></B><BR>
 * 　画面から入力された内容を受け取り、
 *   ためうちエリア出力用のデータをデータベースから検索・取得して返します。<BR>
 * 　該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。
 *   また、条件エラーなどが発生した場合はnullを返します。<BR>
 * 　エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 * 　作業情報の状態フラグが未開始のデータのみを取得し、表示を行います。 <BR>
 * 　なお、このメソッドはnextCheckメソッドの返り値がtrueだった場合に呼び出してください。<BR>
 * 　以下のチェックを行います。<BR>
 *   <BR>
 *   <DIR>
 *     1.入力情報の該当データが最大表示件数をこえないこと<BR>
 *     2.入力情報の該当データが仕分予定情報テーブルに存在すること<BR>
 *   </DIR>
 *   <BR>
 * 　＜検索条件＞
 *   <DIR>
 *     ・状態フラグ：未開始<BR>
 *   </DIR>
 *   <BR>
 * 　＜検索順＞
 *   <DIR>
 *     ・クロス/DC区分<BR>
 *     ・出荷先コード<BR>
 *     ・出荷伝票No.<BR>
 *     ・出荷伝票行No.<BR>
 *   </DIR>
 *   <BR>
 * 　＜入力データ＞<BR>
 * 　* 必須入力<BR>
 * 　$ 検索に必要な項目<BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th>		<td></td></tr>
 *     <tr><td>荷主コード</td><td>：</td><td>ConsignorCode</td>		<td>*$</td></tr>
 *     <tr><td>仕分予定日</td><td>：</td><td>PlanDate</td>			<td>*$</td></tr>
 *     <tr><td>商品コード</td><td>：</td><td>ItemCode</td>			<td>*$</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 * 　＜返却データ＞
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th></tr>
 *     <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>荷主名称(登録日時が一番新しいもの)</td><td>：</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>仕分予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>商品名称(登録日時が一番新しいもの)</td><td>：</td><td>ItemName</td></tr>
 *     <tr><td></td><td>ケース入数(登録日時が一番新しいもの)</td><td>：</td><td>EnteringQty</td></tr>
 *     <tr><td></td><td>ボール入数(登録日時が一番新しいもの)</td><td>：</td><td>BundleEnteringQty</td></tr>
 *     <tr><td></td><td>ケースITF(登録日時が一番新しいもの)</td><td>：</td><td>ITF</td></tr>
 *     <tr><td></td><td>ボールITF(登録日時が一番新しいもの)</td><td>：</td><td>BundleITF</td></tr>
 *     <tr><td></td><td>TC/DC区分(名称)</td><td>：</td><td>TcdcName</td></tr>
 *     <tr><td></td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>出荷先名称</td><td>：</td><td>CustomerName</td></tr>
 *     <tr><td></td><td>出荷伝票No.</td><td>：</td><td>ShippingTicketNo</td></tr>
 *     <tr><td></td><td>出荷伝票行No.</td><td>：</td><td>ShippingLineNo</td></tr>
 *     <tr><td></td><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td></tr>
 *     <tr><td></td><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td></tr>
 *     <tr><td></td><td>ケース仕分場所</td><td>：</td><td>CaseSortingLocation</td></tr>
 *     <tr><td></td><td>ピース仕分場所</td><td>：</td><td>PieceSortingLocation</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>仕入先名称</td><td>：</td><td>SupplierName</td></tr>
 *     <tr><td></td><td>入荷伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
 *     <tr><td></td><td>入荷伝票行No.</td><td>：</td><td>InstockLineNo</td></tr>
 *     <tr><td></td><td>TC/DC区分(HIDEEN)</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td></td><td>ｹｰｽ/ﾋﾟｰｽ区分(HIDEEN)</td><td>：</td><td>CasePieceFlag</td></tr>
 *     <tr><td></td><td>仕分予定一意キー(HIDEEN)</td><td>：</td><td>SortingPlanUkey</td></tr>
 *     <tr><td></td><td>最終更新日時(HIDEEN)</td><td>：</td><td>LastUpdateDate</td></tr>
 *   </table>
 *   </DIR>
 * <BR>
 * <B><U>4.入力ボタン押下処理（<CODE>check(Connection, Parameter, Parameter[])</CODE>メソッド）</U></B><BR>
 * 　画面から入力された内容を受け取り、チェックを行い、チェックの結果を返します。 <BR>
 * 　正しい値が入力された場合、他端末で変更されていない場合、同一情報が既に存在しない場合はtrueを、 <BR>
 * 　条件エラーが発生した場合や排他エラーが発生した場合はfalseを返します。 <BR>
 * 　エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * 　必須項目に入力がなかった場合は、<CODE>ScheduleException</CODE>を通知します。<BR>
 * 　なお、呼び出し元の画面は、更新対象となるデータのみをパラメータにセットして渡してください。 <BR>
 * 　本メソッドは以下のチェックを行います。<BR>
 *   <DIR>
 *     1.必須項目に入力があること<BR>
 *     2.予定ｹｰｽ/ﾋﾟｰｽ数どちらかに1以上の入力があること<BR>
 *     3.ケース入数に入力がない場合、ホスト予定ケース数に入力がないこと<BR>
 *     4.ｸﾛｽ/DC区分がクロスの場合、仕入先コード、入荷伝票No.、入荷伝票行No.に入力があること<BR>
 *     5.総仕分数がオーバーフローしないこと<BR>
 *     6.入力ﾃﾞｰﾀが他端末で更新されていないこと<BR>
 *     <DIR>
 *      ・該当作業情報が存在すること<BR>
 *      ・該当仕分予定情報が存在すること<BR>
 *      ・該当仕分予定情報の最終更新日時が変更されていないこと<BR>
 *     </DIR>
 *     7.開始済・作業中・一部完了・完了いづれかの状態で同一情報のデータがためうち・DBに存在しないこと<BR>
 *     <DIR>
 *      ・荷主コード、仕分予定日、商品コード、ケースピース区分、クロス/DC区分、出荷先コード、仕分場所で同一情報をチェックします。<BR>
 *     </DIR>
 *   </DIR>
 *   <BR>
 * 　＜入力データ＞ <BR>
 * 　* 必須入力<BR>
 * 　+ 条件により必須<BR>
 * 　$ 検索に必要な項目<BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th><td></td></tr>
 *     <tr><td>荷主コード</td><td>：</td><td>ConsignorCode</td>				<td>*</td></tr>
 *     <tr><td>仕分予定日</td><td>：</td><td>PlanDate</td>					<td>*</td></tr>
 *     <tr><td>商品コード</td><td>：</td><td>ItemCode</td>					<td>*</td></tr>
 *     <tr><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td>					<td>*</td></tr>
 *     <tr><td>出荷先コード</td><td>：</td><td>CustomerCode</td>			<td>*</td></tr>
 *     <tr><td>ケース入数</td><td>：</td><td>EnteringQty</td>				<td>*</td></tr>
 *     <tr><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td>		<td>+</td></tr>
 *     <tr><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td>		<td>+</td></tr>
 *     <tr><td>ケース仕分場所</td><td>：</td><td>CaseSortingLocation</td>	<td></td></tr>
 *     <tr><td>ピース仕分場所</td><td>：</td><td>PieceSortingLoccation</td>	<td>*</td></tr>
 *     <tr><td>仕入先コード</td><td>：</td><td>SupplierCode</td>			<td>*</td></tr>
 *     <tr><td>入荷伝票No.</td><td>：</td><td>InstockTicketNo</td>			<td>*</td></tr>
 *     <tr><td>入荷伝票行No.</td><td>：</td><td>InstockLineNo</td>			<td>*</td></tr>
 *     <tr><td>仕分予定一意キー</td><td>：</td><td>SortingPlanUkey</td>		<td>*$</td></tr>
 *     <tr><td>最終更新日時</td><td>：</td><td>LastUpdateDate</td>			<td>*$</td></tr>
 *   </table>
 *   </DIR>
 * <BR>
 * <B><U>5.削除、全削除ボタン押下処理（<CODE>startSCH(Connection, Parameter[])</CODE>メソッド）</U></B><BR>
 * 　削除対象の情報を受け取り、仕分予定データ削除処理を行います。 <BR>
 * 　処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 * 　エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 * 　＜更新テーブル＞
 *   <DIR>
 *     ・作業情報<BR>
 *     ・仕分予定情報<BR>
 *     ・在庫情報<BR>
 *   </DIR>
 *   <BR>
 * 　＜入力データ＞<BR>
 * 　* 必須入力<BR>
 * 　$ 検索に必要な項目<BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th><td></td></tr>
 *     <tr><td>作業者コード</td><td>：</td><td>WorkerCode</td>		<td>*</td></tr>
 *     <tr><td>パスワード</td><td>：</td><td>WorkerName</td>		<td>*</td></tr>
 *     <tr><td>仕分予定一意キー</td><td>：</td><td>SortingPlanUkey</td>	<td>*$</td></tr>
 *     <tr><td>最終更新日時</td><td>：</td><td>LastUpdateDate</td>		<td>*</td></tr>
 *     <tr><td>ためうち行No.</td><td>：</td><td>RowNo</td>				<td>*</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 * 　＜処理条件チェック＞
 *   <DIR>
 *     1.作業者コード・パスワードのチェック。<BR>
 *     2.日次更新中でないこと<BR>
 *     3.作業情報テーブルに仕分予定一意キーのデータが存在すること(排他チェック)<BR>
 *     4.仕分予定情報テーブルに仕分予定一意キーのデータが存在すること(排他チェック)<BR>
 *     5.パラメータの最終更新日時と仕分予定情報の最終更新日時が一致すること。(排他チェック)<BR>
 *   </DIR>
 *   <BR>
 * 　＜更新処理＞
 *   <DIR>
 *     更新処理は<CODE>SortingPlanOperator</CODE>クラスの<CODE>updateSortingPlan</CODE>メソッドで行います。 <BR>
 *     詳しくは<CODE>updateSortingPlan</CODE>メソッドのJavaDocを参照してください。 <BR>
 *   </DIR>
 *   <BR>
 * <B><U>6.修正登録ボタン押下処理（<CODE>startSCHgetParams(Connection, Parameter[])</CODE>メソッド）</U></B><BR>
 * 　修正対象の情報を受け取り、仕分予定データ修正処理を行います。 <BR>
 * 　修正登録は、該当データ、また該当データと同一データを削除状態に更新してから、新たにデータを登録する処理を行います。<BR>
 * 　削除処理は<CODE>startSCH</CODE>と同じ処理を行います。 <BR>
 * 　処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 * 　条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 * 　エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * 　また、呼び出し元クラスは更新対象となるデータのみを本メソッドに渡してください。 <BR>
 *   <BR>
 * 　＜更新テーブル＞
 *   <DIR>
 *     ・作業情報<BR>
 *     ・仕分予定情報<BR>
 *     ・在庫情報<BR>
 *   </DIR>
 *   <BR>
 * 　＜入力データ＞<BR>
 * 　* 必須入力<BR>
 * 　+ 条件により必須<BR>
 * 　$ 検索に必要な項目<BR>
 * 　# 更新に必要な項目<BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th>				<td></td></tr>
 *     <tr><td>作業者コード</td><td>：</td><td>WorkerCode</td>				<td>*#</td></tr>
 *     <tr><td>パスワード</td><td>：</td><td>WorkerName</td>				<td>*</td></tr>
 *     <tr><td>荷主コード</td><td>：</td><td>ConsignorCode</td>				<td>*#</td></tr>
 *     <tr><td>荷主名称</td><td>：</td><td>ConsignorName</td>				<td>*#$</td></tr>
 *     <tr><td>仕分予定日</td><td>：</td><td>PlanDate</td>					<td>*#</td></tr>
 *     <tr><td>商品コード</td><td>：</td><td>ItemCode</td>					<td>*#</td></tr>
 *     <tr><td>商品名称</td><td>：</td><td>ItemName</td>					<td>*#</td></tr>
 *     <tr><td>ケース入数</td><td>：</td><td>EnteringQty</td>				<td>*#</td></tr>
 *     <tr><td>ボール入数</td><td>：</td><td>BundleEnteringQty</td>			<td>*#</td></tr>
 *     <tr><td>ケースITF</td><td>：</td><td>ITF</td>						<td>*#</td></tr>
 *     <tr><td>ボールITF</td><td>：</td><td>BundleITF</td>					<td>*#</td></tr>
 *     <tr><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td>					<td>*#$</td></tr>
 *     <tr><td>出荷先コード</td><td>：</td><td>CustomerCode</td>			<td>*#$</td></tr>
 *     <tr><td>出荷先名称</td><td>：</td><td>CustomerName</td>				<td>*#</td></tr>
 *     <tr><td>出荷伝票No.</td><td>：</td><td>InstockTicketNo</td>			<td>*#$</td></tr>
 *     <tr><td>出荷伝票行No.</td><td>：</td><td>InstockLineNo</td>			<td>*#</td></tr>
 *     <tr><td>ケース仕分場所</td><td>：</td><td>CaseSortingLocation</td>	<td>#</td></tr>
 *     <tr><td>ピース仕分場所</td><td>：</td><td>PieceSortingLocation</td>	<td>*#</td></tr>
 *     <tr><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td>		<td>*#</td></tr>
 *     <tr><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td>		<td>*#</td></tr>
 *     <tr><td>仕入先コード</td><td>：</td><td>SupplierCode</td>			<td>*#$</td></tr>
 *     <tr><td>仕入先名称</td><td>：</td><td>SupplierName</td>				<td>*#</td></tr>
 *     <tr><td>入荷伝票No.</td><td>：</td><td>InstockTicketNo</td>			<td>*#$</td></tr>
 *     <tr><td>入荷伝票行No.</td><td>：</td><td>InstockLineNo</td>			<td>*#</td></tr>
 *     <tr><td>仕分予定一意キー</td><td>：</td><td>SortingPlanUkey</td>		<td>*$</td></tr>
 *     <tr><td>最終更新日時</td><td>：</td><td>LastUpdateDate</td>			<td>*</td></tr>
 *     <tr><td>ためうち行No.</td><td>：</td><td>RowNo</td>					<td>*</td></tr>
 *     <tr><td>端末No.</td><td>：</td><td>TerminalNumber</td>				<td>*#</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 * 　＜返却データ＞
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th></tr>
 *     <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>荷主名称(登録日時が一番新しいもの)</td><td>：</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>仕分予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>商品名称(登録日時が一番新しいもの)</td><td>：</td><td>ItemName</td></tr>
 *     <tr><td></td><td>ケース入数(登録日時が一番新しいもの)</td><td>：</td><td>EnteringQty</td></tr>
 *     <tr><td></td><td>ボール入数(登録日時が一番新しいもの)</td><td>：</td><td>BundleEnteringQty</td></tr>
 *     <tr><td></td><td>ケースITF(登録日時が一番新しいもの)</td><td>：</td><td>ITF</td></tr>
 *     <tr><td></td><td>ボールITF(登録日時が一番新しいもの)</td><td>：</td><td>BundleITF</td></tr>
 *     <tr><td></td><td>TC/DC区分(名称)</td><td>：</td><td>TcdcName</td></tr>
 *     <tr><td></td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>出荷先名称</td><td>：</td><td>CustomerName</td></tr>
 *     <tr><td></td><td>出荷伝票No.</td><td>：</td><td>ShippingTicketNo</td></tr>
 *     <tr><td></td><td>出荷伝票行No.</td><td>：</td><td>ShippingLineNo</td></tr>
 *     <tr><td></td><td>ケース仕分場所</td><td>：</td><td>CaseSortingLocation</td></tr>
 *     <tr><td></td><td>ピース仕分場所</td><td>：</td><td>PieceSortingLocation</td></tr>
 *     <tr><td></td><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td></tr>
 *     <tr><td></td><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>仕入先名称</td><td>：</td><td>SupplierName</td></tr>
 *     <tr><td></td><td>入荷伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
 *     <tr><td></td><td>入荷伝票行No.</td><td>：</td><td>InstockLineNo</td></tr>
 *     <tr><td></td><td>TC/DC区分(HIDEEN)</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td></td><td>ｹｰｽ/ﾋﾟｰｽ区分(HIDEEN)</td><td>：</td><td>CasePieceFlag</td></tr>
 *     <tr><td></td><td>仕分予定一意キー(HIDEEN)</td><td>：</td><td>SortingPlanUkey</td></tr>
 *     <tr><td></td><td>最終更新日時(HIDEEN)</td><td>：</td><td>LastUpdateDate</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 * 　＜処理条件チェック＞
 *   <DIR>
 *     1.作業者コード・パスワードのチェック。<BR>
 *     2.日次更新中でないこと<BR>
 *     3.作業情報テーブルに仕分予定一意キーのデータが存在すること(排他チェック)<BR>
 *     4.仕分予定情報テーブルに仕分予定一意キーのデータが存在すること(排他チェック)<BR>
 *     5.パラメータの最終更新日時と仕分予定情報の最終更新日時が一致すること。(排他チェック)<BR>
 *     6.開始済・作業中・一部完了・完了いづれかの状態で同一情報のデータがDBに存在しないこと<BR>
 *     <DIR>
 *      ・荷主コード、仕分予定日、商品コード、ケースピース区分、クロス/DC区分、出荷先コード、仕分場所で同一情報をチェックします。<BR>
 *     </DIR>
 *   </DIR>
 *   <BR>
 * 　＜更新処理＞<BR>
 * 　更新対象の予定一意キーに紐づく情報を削除状態に更新します。<BR>
 * 　また、入力情報と同一情報の状態が未開始・削除ならば同様に削除状態に更新します。<BR>
 *   <DIR>
 *     更新処理は<CODE>SortingPlanOperator</CODE>クラスの<CODE>updateSortingPlan</CODE>メソッドで行います。 <BR>
 *     詳しくは<CODE>updateSortingPlan</CODE>メソッドのJavaDocを参照してください。 <BR>
 *   </DIR>
 * 　＜登録処理＞<BR>
 * 　入力情報をもとに、新規に情報を登録します。<BR>
 *   <DIR>
 *     登録処理は<CODE>SortingPlanOperator</CODE>クラスの<CODE>createSortingPlan</CODE>メソッドで行います。 <BR>
 *     詳しくは<CODE>createSortingPlan</CODE>メソッドのJavaDocを参照してください。 <BR>
 *   </DIR>
 * 
 * 
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/08</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:23:01 $
 * @author  $Author: suresh $
 */
public class SortingPlanModifySCH extends AbstractSortingSCH
{

	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	private static String wProcessName = "SortingPlanModifySCH";

	/**
	 * 仕分予定情報ハンドラ
	 */
	protected SortingPlanHandler wPlanHandler = null;

	/**
	 * 仕分予定検索キー
	 */
	protected SortingPlanSearchKey wPlanKey = null;

	/**
	 * 作業情報ハンドラ
	 */
	protected WorkingInformationHandler wWorkHandler = null;

	/**
	 * 作業情報検索キー
	 */
	private WorkingInformationSearchKey wWorkKey = null;

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
	public SortingPlanModifySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 仕分予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 返却データ
		SortingParameter resultParam = new SortingParameter();

		// 仕分予定情報ハンドラ類のインスタンス生成
		getSortingPlanHandler(conn);
		SortingPlan sorting = null;

		try
		{
			// 検索条件を設定する。
			// 状態フラグ：「未開始」
			wPlanKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);

			wPlanKey.setConsignorCodeGroup(1);
			wPlanKey.setConsignorCodeCollect("");

			if (wPlanHandler.count(wPlanKey) == 1)
			{
				// 該当する荷主コードの件数を取得する
				sorting = (SortingPlan) wPlanHandler.findPrimary(wPlanKey);

				// 件数が1件の場合
				if (sorting != null)
				{
					// 該当する荷主コードを取得し返却パラメータにセットする。
					resultParam.setConsignorCode(sorting.getConsignorCode());
				}
			}

		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return resultParam;
	}

	/**
	 * １画面目から入力された内容をパラメータとして受け取り、
	 * ２画面目のためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明を参照してください。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>SortingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>SortingParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が最大表示件数を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 返却データ
		SortingParameter[] resultParam = null;
		SortingParameter param = (SortingParameter) searchParam;

		// 仕分予定情報ハンドラ類のインスタンス生成
		getSortingPlanHandler(conn);
		SortingPlan[] sorting = null;

		// 検索条件をセットする
		wPlanKey = setConditionSearchKey(param);
		// 検索順をセットする
		wPlanKey.setTcdcFlagOrder(1, false);
		wPlanKey.setCustomerCodeOrder(2, true);
		wPlanKey.setShippingTicketNoOrder(3, true);
		wPlanKey.setShippingLineNoOrder(4, true);

		// ためうちエリアに対象データを表示できるかチェック
		if(!canLowerDisplay(wPlanHandler.count(wPlanKey)))
		{
			return returnNoDisplayParameter();
		}

		// 表示内容を取得する
		sorting = (SortingPlan[]) wPlanHandler.find(wPlanKey);

		// 荷主名称
		String consignorName = "";
		// 商品名称
		String itemName = "";
		// ケース入数
		int enteringQty = 0;
		// ボール入数
		int bundleEnteringQty = 0;
		// ケースITF
		String itf = "";
		// ボールITF
		String bundleItf = "";
			
		// 登録日時の最も新しい荷主、入荷先名称を取得する。
		SortingParameter nameParam = getDisplayName(conn, param);
		if (nameParam != null)
		{
			consignorName = nameParam.getConsignorName();
			itemName = nameParam.getItemName();
			enteringQty = nameParam.getEnteringQty();
			bundleEnteringQty = nameParam.getBundleEnteringQty();
			itf = nameParam.getITF();
			bundleItf = nameParam.getBundleITF();
		}

		// 返却データに検索結果をセットする
		Vector resultVec = new Vector();
		for (int i = 0; i < sorting.length; i++)
		{
			SortingParameter wTemp = new SortingParameter();
			// 荷主コード
			wTemp.setConsignorCode(sorting[i].getConsignorCode());
			// 荷主名称
			wTemp.setConsignorName(consignorName);
			// 仕分予定日
			wTemp.setPlanDate(sorting[i].getPlanDate());
			// 商品コード
			wTemp.setItemCode(sorting[i].getItemCode());
			// 商品名称
			wTemp.setItemName(itemName);
			// ケース入数
			wTemp.setEnteringQty(enteringQty);
			// ボール入数
			wTemp.setBundleEnteringQty(bundleEnteringQty);
			// ケースITF
			wTemp.setITF(itf);
			// ボールITF
			wTemp.setBundleITF(bundleItf);
			// TC/DC区分
			if (sorting[i].getTcdcFlag().equals(SortingPlan.TCDC_FLAG_DC))
			{
				wTemp.setTcdcFlag(SortingParameter.TCDC_FLAG_DC);
			}
			else if (sorting[i].getTcdcFlag().equals(SortingPlan.TCDC_FLAG_CROSSTC))
			{
				wTemp.setTcdcFlag(SortingParameter.TCDC_FLAG_CROSSTC);
			}
			else if (sorting[i].getTcdcFlag().equals(SortingPlan.TCDC_FLAG_TC))
			{
				wTemp.setTcdcFlag(SortingParameter.TCDC_FLAG_TC);
			}
			// TC/DC区分名称
			wTemp.setTcdcName(DisplayUtil.getTcDcValue(sorting[i].getTcdcFlag()));
			// 出荷先コード
			wTemp.setCustomerCode(sorting[i].getCustomerCode());
			// 出荷先名称
			wTemp.setCustomerName(sorting[i].getCustomerName1());
			// 出荷伝票No.
			wTemp.setShippingTicketNo(sorting[i].getShippingTicketNo());
			// 出荷伝票行No.
			wTemp.setShippingLineNo(sorting[i].getShippingLineNo());
			// ホスト予定ケース数
			wTemp.setPlanCaseQty(DisplayUtil.getCaseQty(sorting[i].getPlanQty(), sorting[i].getEnteringQty(), sorting[i].getCasePieceFlag()));
			// ホスト予定ピース数
			wTemp.setPlanPieceQty(DisplayUtil.getPieceQty(sorting[i].getPlanQty(), sorting[i].getEnteringQty(), sorting[i].getCasePieceFlag()));
			// ケース仕分場所
			wTemp.setCaseSortingLocation(sorting[i].getCaseLocation());
			// ピース仕分場所
			wTemp.setPieceSortingLocation(sorting[i].getPieceLocation());
			// 仕入先コード
			wTemp.setSupplierCode(sorting[i].getSupplierCode());
			// 仕入先名称
			wTemp.setSupplierName(sorting[i].getSupplierName1());
			// 入荷伝票No.
			wTemp.setInstockTicketNo(sorting[i].getInstockTicketNo());
			// 入荷伝票行No.
			wTemp.setInstockLineNo(sorting[i].getInstockLineNo());
			// ケースピース区分
			if(sorting[i].getCasePieceFlag().equals(SortingPlan.CASEPIECE_FLAG_CASE))
			{
				wTemp.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_CASE);
			}
			else if(sorting[i].getCasePieceFlag().equals(SortingPlan.CASEPIECE_FLAG_PIECE))
			{
				wTemp.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_PIECE);
			}
			else if(sorting[i].getCasePieceFlag().equals(SortingPlan.CASEPIECE_FLAG_MIX))
			{
				wTemp.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_MIXED);
			}
			// 仕分予定一意キー
			wTemp.setSortingPlanUkey(sorting[i].getSortingPlanUkey());
			// 最終更新日時
			wTemp.setLastUpdateDate(sorting[i].getLastUpdateDate());
			resultVec.addElement(wTemp);
		}
		resultParam = new SortingParameter[resultVec.size()];
		resultVec.copyInto(resultParam);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		
		return resultParam;
	}

	/** 
	 * パラメータの内容が正しいかチェックを行います。<BR>
	 * エラーがない場合はtrueを返します。<BR>
	 * エラーがある場合はfalseを返します。<BR>
	 * エラー内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * 
	 * @param	conn データベースとのコネクションを保持するインスタンス。
	 * @param	checkParam 入力チェックを行う内容が含まれた<CODE>SortingParameter</CODE>クラスのインスタンス。
	 *         <CODE>SortingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return	チェック結果
	 * @throws	ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws	ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean check(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{

		SortingParameter inputParam = (SortingParameter) checkParam;
		
		// 作業情報を検索する
		wWorkKey.KeyClear();
		wWorkKey.setPlanUkey(inputParam.getSortingPlanUkey());
		WorkingInformation[] workInfo = null;
		workInfo = (WorkingInformation[]) wWorkHandler.find(wWorkKey);

		// 作業情報に該当データが存在しない(他端末で更新された)
		if (workInfo == null || workInfo.length == 0)
		{
			return false;
		}

		// 仕分予定情報を検索する
		wPlanKey.KeyClear();
		wPlanKey.setSortingPlanUkey(inputParam.getSortingPlanUkey());
		SortingPlan sorting = null;
		try
		{
			sorting = (SortingPlan) wPlanHandler.findPrimary(wPlanKey);
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		// 仕分予定情報に該当データが存在しない(他端末で更新された)
		if (sorting == null)
		{
			return false;
		}

		// 仕分予定情報の最終更新日時が一致しない(他端末で更新された)
		if (!sorting.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
		{
			return false;
		}


		return true;
	}

	/** 
	 * ２画面目から入力データと、ためうちエリアの情報をパラメータとして受け取り、
	 * 入力チェック・オーバーフローチェック・排他チェック・ためうちとの重複チェック・DBとの重複チェックを行い、チェック結果を返します。 <BR>
	 * 入力が正しかった場合はtrueを、入力にエラーがあった場合はfalseを返します。 <BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。 <BR>
	 *        <CODE>SortingParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        <CODE>SortingParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @throws	ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @return スケジュール処理が正常終了した場合はtrue、スケジュール処理が失敗した場合はfalse
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ReadWriteException, ScheduleException
	{
		// 入力エリアの内容
		SortingParameter inputParam = (SortingParameter) checkParam;
		// ためうちエリアの内容
		SortingParameter[] listParam = (SortingParameter[]) inputParams;

		// 荷主コード・仕分予定日・仕入先コード・TC/DC区分・出荷先コード・伝票No.に入力があること
		if (StringUtil.isBlank(inputParam.getConsignorCode())
			|| StringUtil.isBlank(inputParam.getPlanDate())
			|| StringUtil.isBlank(inputParam.getItemCode()))
		{
			// 6006001=予期しないエラーが発生しました。{0}
			RmiMsgLogClient.write("6006001" + wDelim + wProcessName, this.getClass().getName());
			throw new ScheduleException("6006001" + wDelim + wProcessName);
		}

		// ﾎｽﾄ予定ｹｰｽ/ﾎｽﾄﾋﾟｰｽ数どちらかに1以上の入力があること
		if (inputParam.getPlanCaseQty() <= 0 && inputParam.getPlanPieceQty() <= 0)
		{
			// 6023323={0}または{1}を入力してください。
			// LBL-W0385=ホスト予定ケース数 LBL-W0386=ホスト予定ピース数
			wMessage = "6023323" + wDelim + DisplayText.getText("LBL-W0385") + wDelim + DisplayText.getText("LBL-W0386");
			return false;
		}

		// ケース数に値が入っていた場合、ケース入数に1以上の入力があること
		if (inputParam.getPlanCaseQty() > 0 && inputParam.getEnteringQty() <= 0)
		{
			// 6023347=ケース入数が0の場合、ホスト予定ケース数の入力はできません。
			wMessage = "6023347";
			return false;
		}
			
		// クロス/ＤＣ区分がクロスの場合
		if(inputParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			// 仕入先コードに入力があること
			if(StringUtil.isBlank(inputParam.getSupplierCode()))
			{
				// 6023005=仕入先コードを入力してください。
				wMessage = "6023005";
				return false;
			}
			// 入荷伝票No.に入力があること
			if(StringUtil.isBlank(inputParam.getInstockTicketNo()))
			{
				// 6023094=入荷伝票№を入力してください。
				wMessage = "6023094";
				return false;
			}
			// 入荷伝票行No.に入力があること
			if(StringUtil.isBlank(Integer.toString(inputParam.getInstockLineNo())))
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
			// LBL-W0379=予定数（バラ総数）
			wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0379") + wDelim + MAX_TOTAL_QTY_DISP;
			return false;
		}

		// ハンドラ類の生成を行う
		getSortingPlanHandler(conn);
		getWorkingInformationHandler(conn);
		// 排他チェックを行う。
		if (!check(conn, inputParam))
		{
			// 他端末エラーが発生した場合、処理終了
			// 6003006=このデータは、他の端末で更新されたため処理できません。
			wMessage = "6003006";
			return false;
		}

		// ためうちとの重複チェック(メッセージのセットも呼び出しメソッド内で行う)
		if (listParam != null)
		{
			if (!dupplicationCheck(conn, inputParam, listParam))
			{
				// 6023142=同一情報が既に入力されています。
				wMessage = "6023142";
				return false;
			}
		}

		// DBとの重複チェック(仕分予定情報をチェック)
		SortingPlanOperator planOperator = new SortingPlanOperator(conn);
		inputParam.setTotalPlanQty(inputParam.getEnteringQty() * inputParam.getPlanCaseQty() + inputParam.getPlanPieceQty());
		inputParam.setCasePieceFlag(planOperator.getCasePieceFlag(inputParam));
		SortingPlan[] sorting = planOperator.findSortingPlan(inputParam);
		if (sorting != null && sorting.length != 0)
		{
			for (int i = 0; i < sorting.length; i++)
			{
				// 一部完了の場合は登録できない
				if (sorting[i].getStatusFlag().equals(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART))
				{
					// 6023270 = 一部受付済みの同一データが存在するため、入力できません。
					wMessage = "6023270";
					return false;
				}
				// 開始済・作業中の場合は登録できない
				else if ((sorting[i].getStatusFlag().equals(SortingPlan.STATUS_FLAG_START)) 
					|| (sorting[i].getStatusFlag().equals(SortingPlan.STATUS_FLAG_NOWWORKING)))
				{
					// 6023269=作業中の同一データが存在するため、入力できません。
					wMessage = "6023269";
					return false;
				}
				// 完了の場合登録できない
				else if (sorting[i].getStatusFlag().equals(SortingPlan.STATUS_FLAG_COMPLETION))
				{
					// 6023337 = 完了の同一データが存在するため、入力できません。
					wMessage = "6023337";
					return false;
				}
				// 未開始の場合登録できない
				else if (sorting[i].getStatusFlag().equals(SortingPlan.STATUS_FLAG_UNSTART))
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
	 * １画面目で入力された内容をパラメータとして受け取り、チェックを行います。 <BR>
	 * 該当データが存在し、入力が正しい場合はtrueを返します。<BR>
	 * 入力が不正な場合はfalseを返します。<BR>
	 * エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。 <BR>
	 *        <CODE>SortingParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		SortingParameter param = (SortingParameter) checkParam;
		// 作業者コード、パスワードのチェックを行う。
		if (!checkWorker(conn, param))
		{
			return false;
		}

		return true;
	}

	/**
	 * 仕分予定データ削除スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         <CODE>SortingParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return boolean 削除できたかどうか
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// DBのcommit,rollbackの判断用
		boolean registFlag = false;
		
		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;
		
		try
		{
			// パラメータの入力情報
			SortingParameter[] inputParam = (SortingParameter[]) startParams;

			// 作業者コード、パスワードのチェックを行う。
			if (!checkWorker(conn, inputParam[0]))
			{
				return false;
			}
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
			// 取り込みフラグ更新：取り込み中
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn,wProcessName);
			updateLoadDataFlag = true;
			
			// 対象データ全件の排他チェックを行う。
			if (!lockPlanUkeyAll(conn, startParams))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return false;
			}
			// ハンドラ類の生成を行う
			getSortingPlanHandler(conn);
			getWorkingInformationHandler(conn);
			SortingPlanOperator planOperator = new SortingPlanOperator(conn);

			for (int i = 0; i < inputParam.length; i++)
			{
				// 排他チェックを行う。
				if (!lock(inputParam[i]))
				{
					// 他端末エラーが発生した場合、処理終了
					// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
					wMessage = "6023209" + wDelim + inputParam[i].getRowNo();
					return false;
				}

				// 削除処理を行う(作業情報、予定情報、在庫情報、次作業情報)
				planOperator.updateSortingPlan(inputParam[i].getSortingPlanUkey(), wProcessName);
			}

			// 6001005 = 削除しました。
			wMessage = "6001005";
			registFlag = true;
			return registFlag;
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

	/**
	 * 仕分予定データ修正登録スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 処理が正常に完了した場合は、ためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
	 * 条件エラーなどでスケジュールが完了しなかった場合はnullを返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         <CODE>SortingParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// DBのcommit,rollbackの判断用
		boolean ret = false;

		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;

		try
		{
			// パラメータの入力情報
			SortingParameter[] param = (SortingParameter[]) startParams;

			// 作業者コード、パスワードのチェックを行う。
			if (!checkWorker(conn, param[0]))
			{
				return null;
			}
			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return null;
			}
			// 取り込みフラグチェック処理 null：取り込み中
			if (isLoadingData(conn))
			{
				return null;
			}
			// 取り込みフラグ更新：取り込み中
			if (!updateLoadDataFlag(conn, true))
			{
				return null;
			}
			doCommit(conn, wProcessName);
			updateLoadDataFlag = true;

			// 対象データ全件の排他チェックを行う。
			if (!lockPlanUkeyAll(conn, startParams))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return null;
			}

			// バッチNo.、作業者名称を取得する
			SequenceHandler sequenceHandler = new SequenceHandler(conn);
			String batch_seqno = sequenceHandler.nextSortingPlanBatchNo();
			String workerName = getWorkerName(conn, param[0].getWorkerCode());

			// ハンドラ類の生成を行う
			getSortingPlanHandler(conn);
			getWorkingInformationHandler(conn);
			SortingPlanOperator planOperator = new SortingPlanOperator(conn);

			for (int i = 0; i < param.length; i++)
			{
				// 排他チェックを行う。
				if (!lock(param[i]))
				{
					// 他端末エラーが発生した場合、処理終了
					// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
					wMessage = "6023209" + wDelim + param[i].getRowNo();
					return null;
				}

				// 元情報の削除処理を行う(作業情報、予定情報、在庫情報)
				planOperator.updateSortingPlan(param[i].getSortingPlanUkey(), wProcessName);

				// DBとの重複チェック(仕分予定情報をチェック)
				param[i].setTotalPlanQty(param[i].getEnteringQty() * param[i].getPlanCaseQty() + param[i].getPlanPieceQty());
				param[i].setCasePieceFlag(planOperator.getCasePieceFlag(param[i]));
				SortingPlan[] sorting = planOperator.findSortingPlanForUpdate(param[i]);
				if (sorting != null && sorting.length != 0)
				{
					// 6023273=No.{0}{1}
					// 6006007=既に同一データが存在するため、登録できません。
					wMessage = "6023273" + wDelim + param[i].getRowNo() + wDelim + MessageResource.getMessage("6006007");
					return null;
				}
				
				// 仕分予定情報を作成用にデータをセットする
				// 仕分予定総数
				param[i].setTotalPlanQty(param[i].getEnteringQty() * param[i].getPlanCaseQty() + param[i].getPlanPieceQty());
				// バッチNo.
				param[i].setBatchNo(batch_seqno);
				// 作業者コード
				param[i].setWorkerCode(param[0].getWorkerCode());
				// 作業者名称
				param[i].setWorkerName(workerName);
				// 端末No.
				param[i].setTerminalNumber(param[0].getTerminalNumber());
				// 登録区分
				param[i].setRegistKbn(SortingPlan.REGIST_KIND_WMS);
				// 処理名
				param[i].setRegistPName(wProcessName);

				// 仕分予定情報を作成する(予定情報、作業情報、在庫情報)
				planOperator.createSortingPlan(param[i]);

			}

			// 仕分予定情報を再検索する。
			SortingParameter[] viewParam = (SortingParameter[]) this.query(conn, param[0]);

			ret = true;

			// 6001004 = 修正しました。
			wMessage = "6001004";

			// 最新の仕分予定情報を返す。
			return viewParam;

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
			if (!ret)
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
	 * 仕分予定情報のハンドラと検索キーの生成を行う
	 * 
	 * @param conn データベースへのコネクションオブジェクト
	 */
	protected void getSortingPlanHandler(Connection conn)
	{
		wPlanHandler = new SortingPlanHandler(conn);
		wPlanKey = new SortingPlanSearchKey();
	}

	/**
	 * 作業情報のハンドラと検索キーの生成を行う
	 * 
	 * @param conn データベースへのコネクションオブジェクト
	 */
	protected void getWorkingInformationHandler(Connection conn)
	{
		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
	}

	/**
	 * 更新対象となる作業情報・仕分予定情報を検索しロックする。<BR>
	 * このメソッドを呼ぶ前に、インスタンスの生成メソッドを呼ぶこと。<BR>
	 * 更新対象データを正常に検索・ロックできた場合true、他端末エラーなどで検索できなかった場合はfalseを返す。<BR>
	 * 以下の場合、他端末エラーとみなす。<BR>
	 * <DIR>
	 *   ・作業情報を検索し、検索結果がなかった場合<BR>
	 *   ・仕分予定情報を検索し、検索結果がなかった場合<BR>
	 *   ・仕分予定情報を検索し、画面のデータと最終更新日時がちがった場合
	 * </DIR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param inputParam 更新対象データを含む<CODE>SortingParameter</CODE>
	 * @return 更新対象データを検索・ロックできたかどうか
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected boolean lock(SortingParameter inputParam) throws ReadWriteException
	{
		
		// 作業情報を検索する
		wWorkKey.KeyClear();
		wWorkKey.setPlanUkey(inputParam.getSortingPlanUkey());
		WorkingInformation[] workInfo = null;
		workInfo = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);

		// 作業情報に該当データが存在しない(他端末で更新された)
		if (workInfo == null || workInfo.length == 0)
			return false;

		// 仕分予定情報を検索する
		wPlanKey.KeyClear();
		wPlanKey.setSortingPlanUkey(inputParam.getSortingPlanUkey());
		SortingPlan sorting = null;
		try
		{
			sorting = (SortingPlan) wPlanHandler.findPrimaryForUpdate(wPlanKey);
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		// 仕分予定情報に該当データが存在しない(他端末で更新された)
		if (sorting == null)
		{
			return false;
		}
		// 仕分予定情報の最終更新日時が一致しない(他端末で更新された)
		if (!sorting.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
		{	
			return false; 
		} 

		return true;

	}

	// Private methods -----------------------------------------------

	/**
	 * 1画面目から2画面目を表示する場合の検索条件を検索キーにセットします。<BR>
	 * 
	 * @param param 検索条件を含む<CODE>SortingParameter</CODE>
	 * @return 検索キー
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private SortingPlanSearchKey setConditionSearchKey(SortingParameter param) throws ReadWriteException
	{
		SortingPlanSearchKey planKey = new SortingPlanSearchKey();
		// 検索条件を設定する。
		// 荷主コード
		planKey.setConsignorCode(param.getConsignorCode());
		// 仕分予定日
		planKey.setPlanDate(param.getPlanDate());
		// 商品コード
		planKey.setItemCode(param.getItemCode());
		// 状態フラグ：未開始
		planKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);
		
		return planKey;
	}

	/**
	 * 入力条件から登録日時が一番新しい各名称を取得します。<BR>
	 * <BR>
	 * ＜取得名称＞
	 * <DIR>
	 *  ・荷主名称<BR>
	 *  ・仕入先名称<BR>
	 *  ・出荷先名称<BR>
	 * </DIR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param inputParam 検索条件を含む<CODE>SortingParameter</CODE>
	 * @return SortingParameter 検索結果を含む<CODE>SortingParameter</CODE>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private SortingParameter getDisplayName(Connection conn, SortingParameter inputParam) throws ReadWriteException
	{
		// 返却データ
		SortingParameter resultParam = null;

		SortingPlanReportFinder nameFinder = new SortingPlanReportFinder(conn);
		// 検索キーをセットする
		SortingPlanSearchKey nameKey = setConditionSearchKey(inputParam);
		nameKey.setConsignorNameCollect("");
		nameKey.setItemName1Collect("");
		nameKey.setEnteringQtyCollect("");
		nameKey.setBundleEnteringQtyCollect("");
		nameKey.setItfCollect("");
		nameKey.setBundleItfCollect("");
		nameKey.setRegistDateOrder(1, false);
		// 検索を実行する
		nameFinder.open();
		SortingPlan[] sorting = null;
		if (nameFinder.search(nameKey) > 0)
		{
			sorting = (SortingPlan[]) nameFinder.getEntities(1);
			if (sorting != null && sorting.length != 0)
			{
				// 名称を取得する
				resultParam = new SortingParameter();
				resultParam.setConsignorName(sorting[0].getConsignorName());
				resultParam.setItemName(sorting[0].getItemName1());
				resultParam.setEnteringQty(sorting[0].getEnteringQty());
				resultParam.setBundleEnteringQty(sorting[0].getBundleEnteringQty());
				resultParam.setITF(sorting[0].getItf());
				resultParam.setBundleITF(sorting[0].getBundleItf());
			}
		}
		nameFinder.close();
		return resultParam;
	}

	/**
	 * ためうちに入力データを入力できるかどうかをチェックします。<BR>
	 * 同一行No.が存在した場合、同一データがあったとみなします。<BR>
	 * 同一データがあった場合は、入力できないのでfalseを返します。<BR>
	 * 同一データがなく、入力できる場合はtrueを返します。
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param inputParam 入力エリアの入力情報
	 * @param listParam ためうちエリアの情報
	 * @return ためうちにデータを入力できるかどうか
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	private boolean dupplicationCheck(Connection conn, SortingParameter inputParam, SortingParameter[] listParam) throws ScheduleException, ReadWriteException
	{
		try
		{
			SortingPlanOperator planOperator = new SortingPlanOperator();
			// 入力データのケースピース区分を取得
			inputParam.setTotalPlanQty(inputParam.getEnteringQty() * inputParam.getPlanCaseQty() + inputParam.getPlanPieceQty());
			inputParam.setCasePieceFlag(planOperator.getCasePieceFlag(inputParam));
			
			// ためうち件数分チェックする
			for (int i = 0; i < listParam.length; i++)
			{
				// TC/DC区分・出荷先コードが一致
				if(inputParam.getTcdcFlag().equals(listParam[i].getTcdcFlag())
				&& inputParam.getCustomerCode().equals(listParam[i].getCustomerCode())
				&& inputParam.getSupplierCode().equals(listParam[i].getSupplierCode()))
				{
					// ためうちデータのケースピース区分を取得する
					listParam[i].setTotalPlanQty(listParam[i].getEnteringQty() * listParam[i].getPlanCaseQty() + listParam[i].getPlanPieceQty());
					listParam[i].setCasePieceFlag(planOperator.getCasePieceFlag(listParam[i]));
				
					// クロスドックパッケージありの場合
					if (isCrossDockPack(conn))
					{
						if (inputParam.getCasePieceFlag().equals(listParam[i].getCasePieceFlag())
						&& inputParam.getSupplierCode().equals(listParam[i].getSupplierCode())
						&& inputParam.getShippingTicketNo().equals(listParam[i].getShippingTicketNo())
						&& inputParam.getShippingLineNo() == listParam[i].getShippingLineNo())
						{
							return false;
						}
					}
					// クロスドックパッケージなしの場合
					else
					{
						// 入力エリアのｹｰｽﾋﾟｰｽ区分がケースまたは指定なしの場合
						if(inputParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE)
						|| inputParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
						{
							// ためうちエリアのｹｰｽﾋﾟｰｽ区分がケースまたは指定なしでケース棚が一致
							if((listParam[i].getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE)
							|| listParam[i].getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
							&& inputParam.getCaseSortingLocation().equals(listParam[i].getCaseSortingLocation()))
							{
								return false;
							}
						}
						// 入力エリアのｹｰｽﾋﾟｰｽ区分がピースまたは指定なしの場合
						if(inputParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE)
						|| inputParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
						{
							// ためうちエリアのｹｰｽﾋﾟｰｽ区分がピースまたは指定なしでピース棚が一致
							if((listParam[i].getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE)
							|| listParam[i].getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
							&& inputParam.getPieceSortingLocation().equals(listParam[i].getPieceSortingLocation()))
							{
								return false;
							}
						}
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

} //end of class
