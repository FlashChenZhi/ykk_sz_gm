package jp.co.daifuku.wms.instockreceive.schedule;

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
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * WEB入荷予定データ修正・削除を行うためのクラスです。 <BR>
 * 画面から入力された内容を受け取り、入荷予定データの修正・削除処理を行います。 <BR>
 * このクラスは、データベースの更新処理をおこないますが、トランザクションの確定は行いません。 <BR>
 * トランザクションの確定は呼び出し元で行ってください。<BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * <B><U>1.初期表示処理（<CODE>initFind(Connection, Parameter)</CODE>メソッド）</U></B><BR>
 *   入荷予定情報に荷主コードが1件のみ存在した場合、
 *   該当する荷主コードを含む<CODE>Parameter</CODE>インスタンスを返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 *   <BR>
 *   ＜検索条件＞
 *   <DIR>
 *     状態フラグ：未開始
 *   </DIR>
 * <BR>
 * <B><U>2.次へボタン押下処理①（<CODE>nextCheck(Connection, Parameter)</CODE>メソッド）</U></B><BR>
 *   画面から入力された内容をパラメータとして受け取り、入力情報のチェックを行います。<BR>
 *   入力が正しい場合はtrue、入力が不正な場合はfalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
 *   以下のチェックを行います。<BR>
 *   <DIR>
 *     1.作業者コード・パスワードのチェック。<BR>
 *     2.TC/DC区分がTCの時、出荷先コードが入力されていること。<BR>
 *   </DIR>
 *   <BR>
 *   ＜入力データ＞<BR>
 *   * 必須の入力項目 <BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th>		<td></td></tr>
 *     <tr><td>作業者コード</td><td>：</td><td>WorkerCode</td>		<td>*</td></tr>
 *     <tr><td>パスワード</td><td>：</td><td>WorkerName</td>		<td>*</td></tr>
 *     <tr><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td>			<td>*</td></tr>
 *     <tr><td>出荷先コード</td><td>：</td><td>CustomerCode</td>	<td>*</td></tr>
 *   </table>
 *   </DIR>
 * <BR>
 * <B><U>3.次へボタン押下処理②（<CODE>query(Connection, Parameter)</CODE>メソッド）</U></B><BR>
 *   画面から入力された内容を受け取り、
 *   ためうちエリア出力用のデータをデータベースから検索・取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。
 *   また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   作業情報の状態フラグが未開始のデータのみを取得し、行No.順に表示を行います。 <BR>
 *   なお、このメソッドはnextCheckメソッドの返り値がtrueだった場合に呼び出してください。<BR>
 *   以下のチェックを行います。<BR>
 *   <BR>
 *   <DIR>
 *     1.入力情報の該当データが最大表示件数をこえないこと<BR>
 *     2.入力情報の該当データが入荷予定情報テーブルに存在すること<BR>
 *   </DIR>
 *   <BR>
 *   ＜検索条件＞
 *   <DIR>
 *     ・状態フラグ：未開始<BR>
 *   </DIR>
 *   <BR>
 *   ＜入力データ＞<BR>
 *   * 必須入力<BR>
 *   + 条件によっては必須入力<BR>
 *   $ 検索に必要な項目<BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th>			<td></td></tr>
 *     <tr><td>荷主コード</td><td>：</td><td>ConsignorCode</td>			<td>*$</td></tr>
 *     <tr><td>入荷受付日</td><td>：</td><td>InstockReceiveDate</td>	<td>*$</td></tr>
 *     <tr><td>仕入先コード</td><td>：</td><td>SupplierCode</td>		<td>*$</td></tr>
 *     <tr><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td>				<td>*$</td></tr>
 *     <tr><td>出荷先コード</td><td>：</td><td>CustomerCode</td>		<td>+$</td></tr>
 *     <tr><td>伝票No.</td><td>：</td><td>InstockTicketNo</td>			<td>*$</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 *   ＜返却データ＞
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th></tr>
 *     <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>荷主名称(登録日時が一番新しいもの)</td><td>：</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>仕入先名称(登録日時が一番新しいもの)</td><td>：</td><td>SupplierName</td></tr>
 *     <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td></td><td>TC/DC区分名称</td><td>：</td><td>TcdcName</td></tr>
 *     <tr><td></td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>出荷先名称(登録日時が一番新しいもの)</td><td>：</td><td>CustomerName</td></tr>
 *     <tr><td></td><td>伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
 *     <tr><td></td><td>行No.</td><td>：</td><td>InstockLineNo</td></tr>
 *     <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>商品名称</td><td>：</td><td>ItemName</td></tr>
 *     <tr><td></td><td>ケース入数</td><td>：</td><td>EnteringQty</td></tr>
 *     <tr><td></td><td>ボール入数</td><td>：</td><td>BundleEnteringQty</td></tr>
 *     <tr><td></td><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td></tr>
 *     <tr><td></td><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td></tr>
 *     <tr><td></td><td>ケースITF</td><td>：</td><td>ITF</td></tr>
 *     <tr><td></td><td>ボールITF</td><td>：</td><td>BundleITF</td></tr>
 *     <tr><td></td><td>入荷予定一意キー</td><td>：</td><td>InstockPlanUkey</td></tr>
 *     <tr><td></td><td>最終更新日時</td><td>：</td><td>LastUpdateDate</td></tr>
 *   </table>
 *   </DIR>
 * <BR>
 * <B><U>4.入力ボタン押下処理（<CODE>check(Connection, Parameter, Parameter[])</CODE>メソッド）</U></B><BR>
 *   画面から入力された内容を受け取り、チェックを行い、チェックの結果を返します。 <BR>
 *   正しい値が入力された場合、他端末で変更されていない場合、同一情報が既に存在しない場合はtrueを、 <BR>
 *   条件エラーが発生した場合や排他エラーが発生した場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   荷主コード・入荷予定日・仕入先コード・TC/DC区分・出荷先コード・伝票No.に入力がなかった場合は、
 *   <CODE>ScheduleException</CODE>を通知します。<BR>
 *   なお、呼び出し元の画面は、更新対象となるデータのみをパラメータにセットして渡してください。 <BR>
 *   本メソッドは以下のチェックを行います。<BR>
 *   <DIR>
 *     1.荷主コード・入荷予定日・仕入先コード・TC/DC区分・出荷先コード・伝票No.に入力があること<BR>
 *     2.予定ｹｰｽ/ﾋﾟｰｽ数どちらかに1以上の入力があること<BR>
 *     3.ケース数に値が入っていた場合、ケース入数に1以上の入力があること<BR>
 *     4.総入荷数がオーバーフローしないこと<BR>
 *     5.入力ﾃﾞｰﾀが他端末で更新されていないこと<BR>
 *     <DIR>
 *      ・該当作業情報が存在すること<BR>
 *      ・該当入荷予定情報が存在すること<BR>
 *      ・該当入荷予定情報の最終更新日時が変更されていないこと<BR>
 *     </DIR>
 *     6.開始済・作業中・一部完了・完了いづれかの状態で同一情報のデータがためうち・DBに存在しないこと<BR>
 *     <DIR>
 *     同一情報の条件は以下のようになります。<BR>
 *      ・荷主コード<BR>
 *      ・入荷予定日<BR>
 *      ・仕入先コード<BR>
 *      ・伝票No.<BR>
 *      ・行No.<BR>
 *     </DIR>
 *     7.各項目の入力データが、環境設定ファイルで定義されている使用桁数を超えていないこと<BR>
 *   </DIR>
 *   <BR>
 *   ＜入力データ＞ <BR>
 *   * 必須入力<BR>
 *   + 条件により必須<BR>
 *   $ 検索に必要な項目<BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th><td></td></tr>
 *     <tr><td>荷主コード</td><td>：</td><td>ConsignorCode</td>			<td>*</td></tr>
 *     <tr><td>入荷予定日</td><td>：</td><td>PlanDate</td>				<td>*</td></tr>
 *     <tr><td>仕入先コード</td><td>：</td><td>SupplierCode</td>		<td>*</td></tr>
 *     <tr><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td>				<td>*</td></tr>
 *     <tr><td>出荷先コード</td><td>：</td><td>CustomerCode</td>		<td>*</td></tr>
 *     <tr><td>伝票No.</td><td>：</td><td>InstockTicketNo</td>			<td>*</td></tr>
 *     <tr><td>行No.</td><td>：</td><td>InstockLineNo</td>				<td>*</td></tr>
 *     <tr><td>商品コード</td><td>：</td><td>ItemCode</td>				<td>*</td></tr>
 *     <tr><td>ケース入数</td><td>：</td><td>EnteringQty</td>			<td>*</td></tr>
 *     <tr><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td>	<td>+</td></tr>
 *     <tr><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td>	<td>+</td></tr>
 *     <tr><td>入荷予定一意キー</td><td>：</td><td>InstockPlanUkey</td>	<td>*$</td></tr>
 *     <tr><td>最終更新日時</td><td>：</td><td>LastUpdateDate</td>		<td>*$</td></tr>
 *   </table>
 *   </DIR>
 * <BR>
 * <B><U>5.削除、全削除ボタン押下処理（<CODE>startSCH(Connection, Parameter[])</CODE>メソッド）</U></B><BR>
 *   削除対象の情報を受け取り、入荷予定データ削除処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   ＜更新テーブル＞
 *   <DIR>
 *     ・作業情報<BR>
 *     ・入荷予定情報<BR>
 *     ・在庫情報<BR>
 *     ・次作業情報<BR>
 *   </DIR>
 *   <BR>
 *   ＜入力データ＞<BR>
 *   * 必須入力<BR>
 *   $ 検索に必要な項目<BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th><td></td></tr>
 *     <tr><td>入荷予定一意キー</td><td>：</td><td>InstockPlanUkey</td>	<td>*$</td></tr>
 *     <tr><td>最終更新日時</td><td>：</td><td>LastUpdateDate</td>		<td>*</td></tr>
 *     <tr><td>ためうち行No.</td><td>：</td><td>RowNo</td>				<td>*</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 *   ＜処理条件チェック＞
 *   <DIR>
 *     1.日次更新中でないこと<BR>
 *     2.作業情報テーブルに入荷予定一意キーのデータが存在すること(排他チェック)<BR>
 *     3.入荷予定情報テーブルに入荷予定一意キーのデータが存在すること(排他チェック)<BR>
 *     4.パラメータの最終更新日時と入荷予定情報の最終更新日時が一致すること。(排他チェック)<BR>
 *   </DIR>
 *   <BR>
 *   ＜更新処理＞
 *   <DIR>
 *     更新処理は<CODE>InstockPlanOperator</CODE>クラスの<CODE>updateInstockPlan</CODE>メソッドで行います。 <BR>
 *     詳しくは<CODE>updateInstockPlan</CODE>メソッドのJavaDocを参照してください。 <BR>
 *   </DIR>
 *   <BR>
 * <B><U>6.修正登録ボタン押下処理（<CODE>startSCHgetParams(Connection, Parameter[])</CODE>メソッド）</U></B><BR>
 *   修正対象の情報を受け取り、入荷予定データ修正処理を行います。 <BR>
 *   修正登録は、該当するデータを削除状態に更新してから、新たにデータを登録する処理を行います。<BR>
 *   また、入力データと同一の情報がDBに存在し、そのデータが未開始または削除だった場合は
 *   その情報を削除状態に更新し、新たにデータを登録します。<BR>
 *   削除処理は<CODE>startSCH</CODE>と同じ処理を行います。 <BR>
 *   処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   また、呼び出し元クラスは更新対象となるデータのみを本メソッドに渡してください。 <BR>
 *   <BR>
 *   ＜更新テーブル＞
 *   <DIR>
 *     ・作業情報<BR>
 *     ・入荷予定情報<BR>
 *     ・在庫情報<BR>
 *     ・次作業情報<BR>
 *   </DIR>
 *   <BR>
 *   ＜入力データ＞<BR>
 *   * 必須入力<BR>
 *   + 条件により必須<BR>
 *   $ 検索に必要な項目<BR>
 *   # 更新に必要な項目<BR>
 *   <DIR>
 *   <table>
 *     <tr><th>画面名称</th><td>：</td><th>パラメータ名</th>			<td></td></tr>
 *     <tr><td>作業者コード</td><td>：</td><td>WorkerCode</td>			<td>*#</td></tr>
 *     <tr><td>パスワード</td><td>：</td><td>WorkerName</td>			<td>*</td></tr>
 *     <tr><td>荷主コード</td><td>：</td><td>ConsignorCode</td>			<td>*#</td></tr>
 *     <tr><td>荷主名称</td><td>：</td><td>ConsignorName</td>			<td>*#$</td></tr>
 *     <tr><td>入荷予定日</td><td>：</td><td>PlanDate</td>				<td>*#</td></tr>
 *     <tr><td>仕入先コード</td><td>：</td><td>SupplierCode</td>		<td>*#$</td></tr>
 *     <tr><td>仕入先名称</td><td>：</td><td>SupplierName</td>			<td>*#</td></tr>
 *     <tr><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td>				<td>*#$</td></tr>
 *     <tr><td>出荷先コード</td><td>：</td><td>CustomerCode</td>		<td>*#$</td></tr>
 *     <tr><td>出荷先名称</td><td>：</td><td>CustomerName</td>			<td>*#</td></tr>
 *     <tr><td>伝票No.</td><td>：</td><td>InstockTicketNo</td>			<td>*#$</td></tr>
 *     <tr><td>行No.</td><td>：</td><td>InstockLineNo</td>				<td>*#</td></tr>
 *     <tr><td>商品コード</td><td>：</td><td>ItemCode</td>				<td>*#</td></tr>
 *     <tr><td>商品名称</td><td>：</td><td>ItemName</td>				<td>*#</td></tr>
 *     <tr><td>ケース入数</td><td>：</td><td>EnteringQty</td>			<td>*#</td></tr>
 *     <tr><td>ボール入数</td><td>：</td><td>BundleEnteringQty</td>		<td>*#</td></tr>
 *     <tr><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td>	<td>*#</td></tr>
 *     <tr><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td>	<td>*#</td></tr>
 *     <tr><td>ケースITF</td><td>：</td><td>ITF</td>					<td>*#</td></tr>
 *     <tr><td>ボールITF</td><td>：</td><td>BundleITF</td>				<td>*#</td></tr>
 *     <tr><td>入荷予定一意キー</td><td>：</td><td>InstockPlanUkey</td>	<td>*$</td></tr>
 *     <tr><td>最終更新日時</td><td>：</td><td>LastUpdateDate</td>		<td>*</td></tr>
 *     <tr><td>ためうち行No.</td><td>：</td><td>RowNo</td>				<td>*</td></tr>
 *     <tr><td>端末No.</td><td>：</td><td>TerminalNumber</td>			<td>*#</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 *   ＜返却データ＞
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th></tr>
 *     <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>荷主名称(登録日時が一番新しいもの)</td><td>：</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>仕入先名称(登録日時が一番新しいもの)</td><td>：</td><td>SupplierName</td></tr>
 *     <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td></td><td>TC/DC区分名称</td><td>：</td><td>TcdcName</td></tr>
 *     <tr><td></td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>出荷先名称(登録日時が一番新しいもの)</td><td>：</td><td>CustomerName</td></tr>
 *     <tr><td></td><td>伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
 *     <tr><td></td><td>行No.</td><td>：</td><td>InstockLineNo</td></tr>
 *     <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>商品名称</td><td>：</td><td>ItemName</td></tr>
 *     <tr><td></td><td>ケース入数</td><td>：</td><td>EnteringQty</td></tr>
 *     <tr><td></td><td>ボール入数</td><td>：</td><td>BundleEnteringQty</td></tr>
 *     <tr><td></td><td>ホスト予定ケース数</td><td>：</td><td>PlanCaseQty</td></tr>
 *     <tr><td></td><td>ホスト予定ピース数</td><td>：</td><td>PlanPieceQty</td></tr>
 *     <tr><td></td><td>ケースITF</td><td>：</td><td>ITF</td></tr>
 *     <tr><td></td><td>ボールITF</td><td>：</td><td>BundleITF</td></tr>
 *     <tr><td></td><td>入荷予定一意キー</td><td>：</td><td>InstockPlanUkey</td></tr>
 *     <tr><td></td><td>最終更新日時</td><td>：</td><td>LastUpdateDate</td></tr>
 *   </table>
 *   </DIR>
 *   <BR>
 *   ＜処理条件チェック＞
 *   <DIR>
 *     1.日次更新中でないこと<BR>
 *     2.作業情報テーブルに入荷予定一意キーのデータが存在すること(排他チェック)<BR>
 *     3.入荷予定情報テーブルに入荷予定一意キーのデータが存在すること(排他チェック)<BR>
 *     4.パラメータの最終更新日時と入荷予定情報の最終更新日時が一致すること。(排他チェック)<BR>
 *     5.開始済・作業中・一部完了・完了いづれかの状態で同一情報のデータがDBに存在しないこと<BR>
 *     <DIR>
 *     同一情報の条件は以下のようになります。<BR>
 *      ・荷主コード<BR>
 *      ・入荷予定日<BR>
 *      ・仕入先コード<BR>
 *      ・伝票No.<BR>
 *      ・行No.<BR>
 *     </DIR>
 *   </DIR>
 *   <BR>
 *   ＜更新処理＞
 *   <DIR>
 *     更新処理は<CODE>InstockPlanOperator</CODE>クラスの<CODE>updateInstockPlan</CODE>メソッドで行います。 <BR>
 *     詳しくは<CODE>updateInstockPlan</CODE>メソッドのJavaDocを参照してください。 <BR>
 *   </DIR>
 *   ＜登録処理＞
 *   <DIR>
 *     登録処理は<CODE>InstockPlanOperator</CODE>クラスの<CODE>createInstockPlan</CODE>メソッドで行います。 <BR>
 *     詳しくは<CODE>createInstockPlan</CODE>メソッドのJavaDocを参照してください。 <BR>
 *   </DIR>
 * 
 * 
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/27</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:22:45 $
 * @author  $Author: suresh $
 */
public class InstockReceivePlanModifySCH extends AbstractInstockReceiveSCH
{

	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	private static String wProcessName = "InstockPlanModifySCH";

	/**
	 * 入荷予定情報ハンドラ
	 */
	private InstockPlanHandler wPlanHandler = null;

	/**
	 * 入荷予定検索キー
	 */
	private InstockPlanSearchKey wPlanKey = null;

	/**
	 * 作業情報ハンドラ
	 */
	private WorkingInformationHandler wWorkHandler = null;

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
		return ("$Revision: 1.2 $,$Date: 2006/11/21 04:22:45 $");
	}
	
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public InstockReceivePlanModifySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 入荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// 該当する荷主コードがセットされます。
		InstockReceiveParameter wParam = new InstockReceiveParameter();

		// 出庫予定情報ハンドラ類のインスタンス生成
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();
		InstockPlanReportFinder instockFinder = new InstockPlanReportFinder(conn);
		InstockPlan[] wInstock = null;

		try
		{
			// 検索条件を設定する。
			// データの検索
			// 状態フラグ：「未開始」
			searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (instockFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// 状態フラグ：「未開始」
				searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
				// ソート順に登録日時を設定
				searchKey.setRegistDateOrder(1, false);
				
				searchKey.setConsignorCodeCollect("");

				if (instockFinder.search(searchKey) > 0)
				{
					// 登録日時が最も新しい荷主名称を取得します。
					wInstock = (InstockPlan[]) instockFinder.getEntities(1);
					wParam.setConsignorCode(wInstock[0].getConsignorCode());
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			// 必ずConnectionをクローズする
			instockFinder.close();
		}
		
		return wParam;
		
	}

	/**
	 * １画面目から入力された内容をパラメータとして受け取り、
	 * ２画面目のためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明を参照してください。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>InstockReceiveParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が最大表示件数を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// 返却データ
		InstockReceiveParameter[] resultParam = null;
		
		InstockReceiveParameter param = (InstockReceiveParameter) searchParam;

		// DnInstockPlanから該当情報を検索する
		InstockPlanHandler planHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey planKey = new InstockPlanSearchKey();
		InstockPlan[] instock = null;

		// 検索条件をセットする
		planKey = setConditionSearchKey(param);
		// 検索順をセットする
		planKey.setInstockLineNoOrder(1, true);

		// 表示件数を取得する
		if (!canLowerDisplay(planHandler.count(planKey)))
		{
			return returnNoDisplayParameter();
		}

		// 表示内容を取得する
		instock = (InstockPlan[]) planHandler.find(planKey);

		// 荷主名称
		String consignorName = "";
		// 仕入先名称
		String supplierName = "";
		// 出荷先名称
		String customerName = "";
		
		// 登録日時の最も新しい荷主、入荷先名称を取得する。
		InstockReceiveParameter nameParam = getDisplayName(conn, param);
		if (nameParam != null)
		{
			consignorName = nameParam.getConsignorName();
			supplierName = nameParam.getSupplierName();
			customerName = nameParam.getCustomerName();
		}

		// 返却データに検索結果をセットする
		Vector resultVec = new Vector();
		for (int i = 0; i < instock.length; i++)
		{
			InstockReceiveParameter wTemp = new InstockReceiveParameter();

			// 荷主コード
			wTemp.setConsignorCode(instock[i].getConsignorCode());
			// 荷主名称
			wTemp.setConsignorName(consignorName);
			// 入荷予定日
			wTemp.setPlanDate(instock[i].getPlanDate());
			// 仕入先コード
			wTemp.setSupplierCode(instock[i].getSupplierCode());
			// 仕入先名称
			wTemp.setSupplierName(supplierName);
			// TC/DC区分
			if (instock[i].getTcdcFlag().equals(InstockPlan.TCDC_FLAG_DC))
			{
				wTemp.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
			}
			else if (instock[i].getTcdcFlag().equals(InstockPlan.TCDC_FLAG_CROSSTC))
			{
				wTemp.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
			}
			else if (instock[i].getTcdcFlag().equals(InstockPlan.TCDC_FLAG_TC))
			{
				wTemp.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC);
			}
			// TC/DC区分名称
			wTemp.setTcdcName(DisplayUtil.getTcDcValue(instock[i].getTcdcFlag()));
			// 出荷先コード
			wTemp.setCustomerCode(instock[i].getCustomerCode());
			// 出荷先名称
			wTemp.setCustomerName(customerName);
			// 伝票No.(入荷伝票No.)
			wTemp.setInstockTicketNo(instock[i].getInstockTicketNo());
			// 行No.(入荷伝票行No.)
			wTemp.setInstockLineNo(instock[i].getInstockLineNo());
			// 商品コード
			wTemp.setItemCode(instock[i].getItemCode());
			// 商品名称
			wTemp.setItemName(instock[i].getItemName1());
			// ケース入数
			wTemp.setEnteringQty(instock[i].getEnteringQty());
			// ボール入数
			wTemp.setBundleEnteringQty(instock[i].getBundleEnteringQty());
			// ホスト予定ケース数
			wTemp.setPlanCaseQty(DisplayUtil.getCaseQty(instock[i].getPlanQty(), instock[i].getEnteringQty(), instock[i].getCasePieceFlag()));
			// ホスト予定ピース数
			wTemp.setPlanPieceQty(DisplayUtil.getPieceQty(instock[i].getPlanQty(), instock[i].getEnteringQty(), instock[i].getCasePieceFlag()));
			// ケースITF
			wTemp.setITF(instock[i].getItf());
			// ボールITF
			wTemp.setBundleITF(instock[i].getBundleItf());
			// 入荷予定一意キー
			wTemp.setInstockPlanUkey(instock[i].getInstockPlanUkey());
			// 最終更新日時
			wTemp.setLastUpdateDate(instock[i].getLastUpdateDate());
			resultVec.addElement(wTemp);
		}
		resultParam = new InstockReceiveParameter[resultVec.size()];
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
	 * @param	checkParam 入力チェックを行う内容が含まれた<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return	チェック結果(エラーがない場合はtrue.  エラーがある場合はfalse.)
	 * @throws	ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws	ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean check(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		InstockReceiveParameter inputParam = (InstockReceiveParameter) checkParam;
		
		// 作業情報を検索する
		wWorkKey.KeyClear();
		wWorkKey.setPlanUkey(inputParam.getInstockPlanUkey());
		WorkingInformation[] workInfo = null;
		workInfo = (WorkingInformation[]) wWorkHandler.find(wWorkKey);

		// 作業情報に該当データが存在しない(他端末で更新された)
		if (workInfo == null || workInfo.length == 0)
			return false;

		// 入荷予定情報を検索する
		wPlanKey.KeyClear();
		wPlanKey.setInstockPlanUkey(inputParam.getInstockPlanUkey());
		InstockPlan instPlan = null;
		try
		{
			instPlan = (InstockPlan) wPlanHandler.findPrimary(wPlanKey);
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		// 入荷予定情報に該当データが存在しない(他端末で更新された)
		if (instPlan == null)
			return false;

		// 入荷予定情報の最終更新日時が一致しない(他端末で更新された)
		if (!instPlan.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
			return false;

		return true;
	}

	/** 
	 * ２画面目から入力データと、ためうちエリアの情報をパラメータとして受け取り、
	 * 入力チェック・オーバーフローチェック・排他チェック・ためうちとの重複チェック・DBとの重複チェックを行い、チェック結果を返します。 <BR>
	 * 入力が正しかった場合はtrueを、入力にエラーがあった場合はfalseを返します。 <BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。 <BR>
	 *        <CODE>InstockReceiveParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        <CODE>InstockReceiveParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常終了した場合はtrue、スケジュール処理が失敗した場合はfalse
	 * @throws	ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ReadWriteException, ScheduleException
	{
		// 入力エリアの内容
		InstockReceiveParameter inputParam = (InstockReceiveParameter) checkParam;
		// ためうちエリアの内容
		InstockReceiveParameter[] listParam = (InstockReceiveParameter[]) inputParams;

		// 荷主コード・入荷予定日・仕入先コード・TC/DC区分・出荷先コード・伝票No.に入力があること
		if (StringUtil.isBlank(inputParam.getConsignorCode())
			|| StringUtil.isBlank(inputParam.getPlanDate())
			|| StringUtil.isBlank(inputParam.getSupplierCode())
			|| StringUtil.isBlank(inputParam.getTcdcFlag())
			|| StringUtil.isBlank(inputParam.getInstockTicketNo()))
		{
			// 6006001=予期しないエラーが発生しました。{0}
			RmiMsgLogClient.write("6006001" + wDelim + wProcessName, this.getClass().getName());
			throw new ScheduleException("6006001" + wDelim + wProcessName);
		}
		if (inputParam.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_TC))
		{
			if (StringUtil.isBlank(inputParam.getCustomerCode()))
			{
				// 6006001=予期しないエラーが発生しました。{0}
				RmiMsgLogClient.write("6006001" + wDelim + wProcessName, this.getClass().getName());
				throw new ScheduleException("6006001" + wDelim + wProcessName);
			}
		}

		// ホスト予定ケース/ホストピース数どちらかに1以上の入力があること
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
			// 6023019 = ケース入数を入力してください。
			wMessage = "6023019";
			return false;
		}

		// オーバーフローチェック
		if ((long) inputParam.getEnteringQty() * (long) inputParam.getPlanCaseQty() + (long) inputParam.getPlanPieceQty() > WmsParam.MAX_TOTAL_QTY)
		{
			// 6023058 = {0}には{1}以下の値を入力してください。
			wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0379") + wDelim + MAX_TOTAL_QTY_DISP;
			return false;
		}

		// ハンドラ類の生成を行う
		getInstockPlanHandler(conn);
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
			if (!dupplicationCheck(inputParam, listParam))
				return false;
		}

		// DBとの重複チェック(入荷予定情報をチェック)
		InstockPlanOperator planOperator = new InstockPlanOperator(conn);
		InstockPlan instock = planOperator.findInstockPlan(inputParam);
		if (instock != null)
		{
			// 一部完了の場合は登録できない
			if (instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART))
			{
				// 6023270 = 一部受付済みの同一データが存在するため、入力できません。
				wMessage = "6023270";
				return false;
			}
			// 開始済・作業中の場合は登録できない
			else if ((instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_START)) || (instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_NOWWORKING)))
			{
				// 6023269=作業中の同一データが存在するため、入力できません。
				wMessage = "6023269";
				return false;
			}
			// 完了の場合登録できない
			else if (instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETION))
			{
				// 6023337 = 完了の同一データが存在するため、入力できません。
				wMessage = "6023337";
				return false;
			}
			// 未開始の場合登録できない
			else if (instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART))
			{
				// 6023090 = 既に同一データが存在するため、入力できません。
				wMessage = "6023090";
				return false;
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
	 * @param checkParam 入力内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。 <BR>
	 *        <CODE>InstockReceiveParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		InstockReceiveParameter param = (InstockReceiveParameter) checkParam;
		// 作業者コード、パスワードのチェックを行う。
		if (!checkWorker(conn, param))
		{
			return false;
		}

		// TC/DC区分がTCの時、出荷先コードが入力されていること。
		if (param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_TC))
		{
			if (StringUtil.isBlank(param.getCustomerCode()))
			{
				// 6023007=出荷先コードを入力してください。
				wMessage = "6023007";
				return false;
			}
		}

		return true;
	}

	/**
	 * 入荷予定データ削除スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return boolean 削除できたかどうか(正常終了した場合はtrue、失敗した場合はfalse)
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// DBのcommit,rollbackの判断用
		boolean registFlag = false;
		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;
		
		try
		{
			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return false;
			}
			// 取り込みフラグチェック処理 null：取り込み中
			if (isLoadingData(conn))
			{
				return false;
			}
			// 取り込みフラグ更新：取り込み中
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn, wProcessName);
			updateLoadDataFlag = true;
			
			// パラメータの入力情報
			InstockReceiveParameter[] inputParam = (InstockReceiveParameter[]) startParams;

			// ハンドラ類の生成を行う
			getInstockPlanHandler(conn);
			getWorkingInformationHandler(conn);
			InstockPlanOperator planOperator = new InstockPlanOperator(conn);

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
				planOperator.updateInstockPlan(inputParam[i].getInstockPlanUkey(), wProcessName);

			}

			// 6001005 = 削除しました。
			wMessage = "6001005";
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
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
			
		}
	}

	/**
	 * 入荷予定データ修正登録スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 処理が正常に完了した場合は、ためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
	 * 条件エラーなどでスケジュールが完了しなかった場合はnullを返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュールが正常終了した場合は、最新の入荷予定情報<CODE>InstockReceiveParameter</CODE>インスタンスの配列。<BR>
	 *          失敗した場合はnullを返します。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// DBのcommit,rollbackの判断用
		boolean registFlag = false;
		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;
		
		try
		{
			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return null;
			}
			// 取り込みフラグチェック
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

			// パラメータの入力情報
			InstockReceiveParameter[] param = (InstockReceiveParameter[]) startParams;

			// バッチNo.、作業者名称を取得する
			SequenceHandler sequenceHandler = new SequenceHandler(conn);
			String batch_seqno = sequenceHandler.nextInstockPlanBatchNo();
			String workerName = getWorkerName(conn, param[0].getWorkerCode());

			// ハンドラ類の生成を行う
			getInstockPlanHandler(conn);
			getWorkingInformationHandler(conn);
			InstockPlanOperator planOperator = new InstockPlanOperator(conn);

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

				// 元情報の削除処理を行う(作業情報、予定情報、在庫情報、次作業情報)
				planOperator.updateInstockPlan(param[i].getInstockPlanUkey(), wProcessName);

				// DBとの重複チェック(入荷予定情報をチェック)
				InstockPlan instock = planOperator.findInstockPlanForUpdate(param[i]);
				if (instock != null)
				{
					// 削除以外は登録できない
					if (instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART)
					  || instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_START) 
					  || instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_NOWWORKING)
					  || instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART)
					  || instock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETION))
					{
						// 6023273=No.{0}{1}
						// 6006007=既に同一データが存在するため、登録できません。
						wMessage = "6023273" + wDelim + param[i].getRowNo() + wDelim + MessageResource.getMessage("6006007");
						return null;
					}
					
					// 元情報の削除処理を行う(作業情報、予定情報、在庫情報、次作業情報)
					planOperator.updateInstockPlan(instock.getInstockPlanUkey(), wProcessName);
					
				}

				// 入荷予定情報を作成用にデータをセットする
				// 入荷予定総数
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
				param[i].setRegistKbn(InstockPlan.REGIST_KIND_WMS);
				// 処理名
				param[i].setRegistPName(wProcessName);

				// 入荷予定情報を作成する(予定情報、作業情報、在庫情報、次作業情報)
				planOperator.createInstockPlan(param[i]);

			}

			// 入荷予定情報を再検索する。
			InstockReceiveParameter[] viewParam = (InstockReceiveParameter[]) this.query(conn, param[0]);

			// 6001004 = 修正しました。
			wMessage = "6001004";
			registFlag = true;
			// 最新の入荷予定情報を返す。
			return viewParam;

		}
		catch (ReadWriteException e)
		{
			// ロールバックします
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			// ロールバックします
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
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	/**
	 * 入荷予定情報のハンドラと検索キーの生成を行う
	 * 
	 * @param conn データベースへのコネクションオブジェクト
	 */
	protected void getInstockPlanHandler(Connection conn)
	{
		wPlanHandler = new InstockPlanHandler(conn);
		wPlanKey = new InstockPlanSearchKey();
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
	 * 更新対象となる作業情報・入荷予定情報を検索しロックする。<BR>
	 * このメソッドを呼ぶ前に、インスタンスの生成メソッドを呼ぶこと。<BR>
	 * 更新対象データを正常に検索・ロックできた場合true、他端末エラーなどで検索できなかった場合はfalseを返す。<BR>
	 * 以下の場合、他端末エラーとみなす。<BR>
	 * <DIR>
	 *   ・作業情報を検索し、検索結果がなかった場合<BR>
	 *   ・入荷予定情報を検索し、検索結果がなかった場合<BR>
	 *   ・入荷予定情報を検索し、画面のデータと最終更新日時がちがった場合
	 * </DIR>
	 * 
	 * @param inputParam 更新対象データを含む<CODE>InstockReceiveParameter</CODE>
	 * @return 更新対象データを検索・ロックできたかどうか
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected boolean lock(InstockReceiveParameter inputParam) throws ReadWriteException
	{
		// 作業情報を検索する
		wWorkKey.KeyClear();
		wWorkKey.setPlanUkey(inputParam.getInstockPlanUkey());
		WorkingInformation[] workInfo = null;
		workInfo = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);

		// 作業情報に該当データが存在しない(他端末で更新された)
		if (workInfo == null || workInfo.length == 0)
			return false;

		// 入荷予定情報を検索する
		wPlanKey.KeyClear();
		wPlanKey.setInstockPlanUkey(inputParam.getInstockPlanUkey());
		InstockPlan instPlan = null;
		try
		{
			instPlan = (InstockPlan) wPlanHandler.findPrimaryForUpdate(wPlanKey);
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		// 入荷予定情報に該当データが存在しない(他端末で更新された)
		if (instPlan == null)
			return false;

		// 入荷予定情報の最終更新日時が一致しない(他端末で更新された)
		if (!instPlan.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
			return false;

		return true;

	}

	// Private methods -----------------------------------------------

	/**
	 * 1画面目から2画面目を表示する場合の検索条件を検索キーにセットします。<BR>
	 * 
	 * @param param 検索条件を含む<CODE>InstockReceiveParameter</CODE>
	 * @return 検索キー
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private InstockPlanSearchKey setConditionSearchKey(InstockReceiveParameter param) throws ReadWriteException
	{
		InstockPlanSearchKey planKey = new InstockPlanSearchKey();
		// 検索条件を設定する。
		// 荷主コード
		planKey.setConsignorCode(param.getConsignorCode());
		// 入荷予定日
		planKey.setPlanDate(param.getPlanDate());
		// 仕入先コード
		planKey.setSupplierCode(param.getSupplierCode());
		// TC/DC区分
		if (param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_DC))
		{
			planKey.setTcdcFlag(InstockPlan.TCDC_FLAG_DC);
		}
		else if (param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_CROSSTC))
		{
			planKey.setTcdcFlag(InstockPlan.TCDC_FLAG_CROSSTC);
		}
		else if (param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_TC))
		{
			planKey.setTcdcFlag(InstockPlan.TCDC_FLAG_TC);
		}
		// 出荷先コード(TCの場合必須)
		if(!StringUtil.isBlank(param.getCustomerCode()))
		{
			planKey.setCustomerCode(param.getCustomerCode());
		}
		// 伝票No.
		planKey.setInstockTicketNo(param.getInstockTicketNo());
		// 状態フラグ：未開始
		planKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
		
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
	 * @param inputParam 検索条件を含む<CODE>InstockReceiveParameter</CODE>
	 * @return 検索結果を含む<CODE>InstockReceiveParameter</CODE>インスタンス。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private InstockReceiveParameter getDisplayName(Connection conn, InstockReceiveParameter inputParam) throws ReadWriteException
	{
		// 返却データ
		InstockReceiveParameter resultParam = null;
		InstockPlanReportFinder nameFinder = new InstockPlanReportFinder(conn);
		// 検索キーをセットする
		InstockPlanSearchKey nameKey = setConditionSearchKey(inputParam);
		nameKey.setConsignorNameCollect("");
		nameKey.setSupplierName1Collect("");
		nameKey.setCustomerName1Collect("");
		nameKey.setRegistDateOrder(1, false);
		// 検索を実行する
		nameFinder.open();
		InstockPlan[] instock = null;
		if (nameFinder.search(nameKey) > 0)
		{
			instock = (InstockPlan[]) nameFinder.getEntities(1);
			if (instock != null && instock.length != 0)
			{
				// 名称を取得する
				resultParam = new InstockReceiveParameter();
				resultParam.setConsignorName(instock[0].getConsignorName());
				resultParam.setSupplierName(instock[0].getSupplierName1());
				resultParam.setCustomerName(instock[0].getCustomerName1());
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
	 * @param inputParam 入力エリアの入力情報
	 * @param listParam ためうちエリアの情報
	 * @return ためうちにデータを入力できるかどうか
	 */
	private boolean dupplicationCheck(InstockReceiveParameter inputParam, InstockReceiveParameter[] listParam)
	{
		// 荷主、入荷予定日、仕入先コード、TC/DC区分、出荷先コード、伝票No.まで同一なので行No.のチェックのみ
		for (int i = 0; i < listParam.length; i++)
		{
			// ためうちと入力に同一行No.が存在した場合エラー
			if (listParam[i].getInstockLineNo() == inputParam.getInstockLineNo())
			{
				// 6023091 = 既に同一の行No．が存在するため、入力できません。
				wMessage = "6023091";
				return false;
			}
		}

		return true;

	}

} //end of class
