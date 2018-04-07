package jp.co.daifuku.wms.sorting.schedule;

// $Id: SortingReportDataCreator.java,v 1.2 2006/11/21 04:23:02 suresh Exp $

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.utility.CSVItemNotFoundException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.DataReportCsvWriter;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.HostSendView;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalReportDataCreator;
import jp.co.daifuku.wms.sorting.dbhandler.SortingHostSendViewFinder;
import jp.co.daifuku.wms.sorting.dbhandler.SortingHostSendViewSearchKey;
import jp.co.daifuku.wms.sorting.dbhandler.SortingWorkingInformationHandler;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * <CODE>SortingReportDataCreator</CODE>クラスは、仕分実績データ報告処理を行うクラスです。<BR>
 * <CODE>AbstractExternalReportDataCreator</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * このクラスが持つメソッドは、コネクションオブジェクトを受け取りデータベースの更新処理を<BR>
 * おこないますが、トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.データ報告処理（<CODE>report(Connection conn, Parameter startParam)</CODE>メソッド）<BR>
 * <BR>
 *   <DIR>
 *   ConnectionオブジェクトとParameterオブジェクトをパラメータとして受け取り、データベースの<BR>
 *   実績送信情報と作業情報を結合したVIEW から仕分け実績データファイル(CSVファイル)を作成します。<BR>
 *   <BR>
 *   (注記)<BR>
 *   作成するデータには「未処理」と「仕分け完了」が有ります。<BR>
 *     <DIR>
 *     未処理データとは以下の状態のものをいいます。<BR>
 *       <DIR>
 *       a.状態が未開始のデータ。<BR>
 *       b.同一予定一意キーで未開始データと、完了データが混在しているデータ。<BR>
 *           <DIR>
 *           現在の仕様では取込み時にケース／ピース区分が混合となったデータの場合が対象となります。<BR>
 *           例えば、ケース作業のみ完了して、ピース作業が未開始の状態は、未処理データとみなします。<BR>
 *           </DIR>
 *       </DIR>
 *     </DIR>
 * <BR>
 *     <DIR>
 *     VIEW作成条件<BR>
 *       <DIR>
 *       ･作業情報(dnworkinfo)<BR>
 *          <DIR>
 *          状態フラグ <> 削除<BR>
 *          状態フラグ <> 完了<BR>
 *          未作業報告フラグ = 未送信<BR>
 *          </DIR>
 *       ･実績送信情報(dnhostsend)<BR>
 *          <DIR>
 *          作業報告フラグ = 未送信<BR>
 *          </DIR>
 *       VIEWは作業情報、実績送信情報から以下を除いた項目で作成します。<BR>
 *         <DIR>
 *         ロットNo(lot_no, result_lot_no)、作業報告フラグ、作業者コード、<BR>
 *         端末No･RFTNo、登録処理名、最終更新日時、最終更新処理名<BR>
 *         (注記)<BR>
 *           <DIR>
 *           a.作業情報には作業日が無いので、作業情報から取得するView情報の作業日にはNullがセットされます。<BR>
 *           b.賞味期限<BR>
 *             <DIR>
 *             作業情報は賞味期限(use_by_date)、実績送信情報は賞味期限(result_use_by_date)から取得します。<BR>
 *             </DIR>
 *           </DIR>
 *         </DIR>
 *       </DIR>
 *     </DIR>
 * <BR>
 *   メソッド内部でエラーが発生した場合はFalseを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 * <BR>
 *   ＜パラメータ＞ 必須入力<BR>
 *     <DIR>
 *     Connectionオブジェクト <BR>
 *     SystemParameterオブジェクト <BR>
 *     </DIR>
 * <BR>
 *   ＜処理詳細＞ <BR>
 *      1.データ件数を初期化します。<BR>
 *        <DIR>
 *        報告データ件数=0<BR>
 *        </DIR>
 *      2.データ報告フラグを宣言します。<BR>
 *        <DIR>
 *        Falseで宣言します。１件でも報告データがある場合にTrueとします。<BR>
 *        </DIR>
 *      3.VIEW検索のクラスインスタンス作成<BR>
 *        <DIR>
 *        ･VIEWのHandlerインスタンスを作成します。<BR>
 *        ･VIEWのSearchKeyインスタンスを作成します。<BR>
 *        </DIR>
 *      4.VIEWを検索するための検索キーをSearchKeyにセットします。<BR>
 *          <DIR>
 *          ･検索条件<BR>
 *             <DIR>
 *             作業区分 = 仕分け<BR>
 *             状態フラグ = 完了 or 未開始<BR>
 *             作業日 = 画面で入力された作業日 or ""<BR>
 *             </DIR>
 *          ･順序<BR>
 *             <DIR>
 *             予定一意キー+登録日時+作業実績数+賞味期限+ケース/ピース区分+ロケーションNo<BR>
 *             </DIR>
 *          </DIR>
 *      5.VIEW Handlerインスタンスのfindを使用してVIEW検索を実行し、実績送信VIEW Entityに取得します。<BR>
 *        <DIR>
 *        実績送信VIEW の取得件数がゼロの場合、以下の通りとします。<BR>
 *          <DIR>
 *          メッセージ = 対象データはありませんでした。<BR>
 *          データ報告無し(false)で終了します。<BR>
 *          </DIR>
 *        </DIR>
 *      6.ローカル変数を作成します。<BR>
 *        <DIR>
 *        以下の領域を作成し、初期値を設定します。<BR>
 *          <DIR>
 *          ･実績送信VIEWの1件目の各項目の内容をセットします。<BR>
 *             <DIR>
 *             予定一意キー = 実績送信VIEWの１件目の予定一意キー<BR>
 *             賞味期限 = 実績送信VIEWの１件目の賞味期限<BR>
 *             ケース/ピース区分 = 実績送信VIEWの１件目のケース/ピース区分<BR>
 *             ロケーションNo = 実績送信VIEWの１件目のロケーションNo<BR>
 *             </DIR>
 *          ･実績区分 = 完了<BR>
 *          ･仕分け実績数 = 0<BR>
 *          ･実績送信VIEW情報、仕分け実績数 のVector変数<BR>
 *          ･集約キー毎の実績出力フラグ = false<BR>
 *          </DIR>
 *        の領域を作成します。<BR>
 *        </DIR>
 *      7.クラスインスタンス作成<BR>
 *        <DIR>
 *        ･DataReportCsvWriterインスタンス作成<BR>
 *           <DIR>
 *           データ報告ＣＳＶを作成するクラスです<BR>
 *           </DIR>
 *        </DIR>
 *      8.VIEWから取得した実績送信VIEW Entityの終了までLoopを行います。<BR>
 *        <DIR>
 * <BR>
 *        - LOOP開始 -<BR>
 * <BR>
 *          <DIR>
 *          9.実績送信VIEWの予定一意キーが次の予定一意キーに変わった場合は、以下の処理を行います。<BR>
 *            <DIR>
 *            9-1.報告対象となるデータが有る(予定一意キー毎の実績出力フラグがtrue)場合。<BR>
 *               <DIR>
 *               但し、[作業途中の報告を行う] が指定されておらず、かつ、実績区分 = 未処理 ならば<BR>
 *               その予定一意キーデータの実績の出力は行いません。<BR>
 *               出力する場合は、下記の処理を行います。<BR>
 *                 <DIR>
 *                 ･Vector変数に記憶した仕分け実績データを仕分け実績ファイル(CSV)を出力します。<BR>
 *                 (ＣＳＶファイルがオープンされていない場合は、仕分け実績ＣＳＶファイルをオープンします)<BR>
 *                 <BR>
 *                 (注記)<BR>
 *                    <DIR>
 *                    ケース仕分け実績数、ピース仕分け実績数が共にゼロのデータは報告データに出力しません。<BR>
 *                    </DIR>
 *                 ･報告データ件数を加算します。<BR>
 *                 ･データ報告フラグにtrueをセットします。<BR>
 *                 </DIR>
 *               </DIR>
 *            9-2.ローカル変数を更新します。<BR>
 *               <DIR>
 *               ･実績送信VIEWの次データの各項目の内容をセットします。<BR>
 *                  <DIR>
 *                  予定一意キー = 実績送信VIEWの次の予定一意キー<BR>
 *                  賞味期限 = 実績送信VIEWの次の賞味期限<BR>
 *                  ケース/ピース区分 = 実績送信VIEWの次のケース/ピース区分<BR>
 *                  ロケーションNo = 実績送信VIEWの次のロケーションNo<BR>
 *                  </DIR>
 *               ･実績区分 = 仕分け完了<BR>
 *               ･仕分け実績数 = 0<BR>
 *               ･実績送信VIEW情報、仕分け実績数 のVector変数は初期化します。<BR>
 *               ･予定一意キー毎の実績出力フラグにfalseをセットします。<BR>
 *               </DIR>
 *            </DIR>
 *         10.実績送信VIEWの予定一意キーが次の予定キーに変わらなかった場合は、実績送信VIEWと以下の<BR>
 *            ローカル変数を比較します。<BR>
 *            <DIR>
 *            賞味期限、ケース/ピース区分、ロケーションNo<BR>
 *            <BR>
 *            いずれかが等しくない場合は実績送信VIEW情報を記憶し、ローカル変数を更新します。<BR>
 *              <DIR>
 *              10-1.実績送信VIEW情報、仕分け実績数 をVector変数に記憶します。<BR>
 *              10-2.実績送信VIEWの次データの各項目の内容をセットします。<BR>
 *                  <DIR>
 *                  賞味期限 = 実績送信VIEWの次の賞味期限<BR>
 *                  ケース/ピース区分 = 実績送信VIEWの次のケース/ピース区分<BR>
 *                  ロケーションNo = 実績送信VIEWの次のロケーションNo<BR>
 *                  仕分け実績数 = 0<BR>
 *                  </DIR>
 *              </DIR>
 *            </DIR>
 *         11.仕分け実績数を加算します。<BR>
 *            <DIR>
 *            仕分け実績数 = 仕分け実績数 + 実績数(実績送信VIEW)<BR>
 *            </DIR>
 *         12.状態フラグを参照します。<BR>
 *            <DIR>
 *            ･未開始データ(状態フラグ = 未開始)の場合。<BR>
 *               <DIR>
 *               実績区分 = 未処理<BR>
 *               </DIR>
 *            </DIR>
 *         13.予定一意キー毎の実績出力フラグにtrueをセットします。<BR>
 *          </DIR>
 * <BR>
 *        - LOOP終了 -<BR>
 *        </DIR>
 * <BR>
 *     14.Loop処理で集計途中の仕分け実績データが有れば、仕分け実績ファイル(CSV)に出力します。<BR>
 *        <DIR>
 *        但し、[作業途中の報告を行う] が指定されておらず、かつ、実績区分 = 未処理 ならば<BR>
 *        その予定一意データの実績の出力は行いません。<BR>
 *        出力する場合は、下記の処理を行います。<BR>
 *          <DIR>
 *          ･Vector変数に記憶した仕分け実績データを仕分け実績ファイル(CSV)を出力します。<BR>
 *          (ＣＳＶファイルがオープンされていない場合は、仕分け実績ＣＳＶファイルをオープンします)<BR>
 *          <BR>
 *          (注記)<BR>
 *             <DIR>
 *             ケース仕分け実績数、ピース仕分け実績数が共にゼロのデータは報告データに出力しません。<BR>
 *             </DIR>
 *          ･報告データ件数を加算します。<BR>
 *          ･データ報告フラグにtrueをセットします。<BR>
 *          </DIR>
 *        </DIR>
 *     15.仕分け実績ファイル(CSV)をクローズします。<BR>
 *     16.メッセージのセットを行います。<BR>
 *        <DIR>
 *        仕分け実績ファイル(CSV)を1件でも作成した場合は、メッセージ保持エリアに<BR>
 *          <DIR>
 *          報告データの作成が終了しました。<BR>
 *          </DIR>
 *        をセットします。<BR>
 *        1件も作成しなかった場合は、<BR>
 *          <DIR>
 *          対象データはありませんでした。<BR>
 *          </DIR>
 *        をセットします。<BR>
 *        </DIR>
 * <BR>
 *   ＜仕分け実績の集約と出力 処理概要＞ <BR>
 *      <DIR>
 *      仕分け実績の集約を行います。<BR>
 *      集約は賞味期限+ケース品仕分け場所 or ピース品仕分け場所です。<BR>
 *      </DIR>
 *   ＜仕分け実績ファイル(CSV)出力 処理詳細＞ <BR>
 *      <DIR>
 *      1.仕分け実績データの集約を行います。<BR>
 *        <DIR>
 *        賞味期限 + ケース/ピース区分 + ロケーション で集約されている実績データを、さらに<BR>
 *        賞味期限 + ケース品仕分け場所 or ピース品仕分け場所 で集約します。<BR>
 *        （同一賞味期限でケース品とピース品を１行に集約します。）<BR>
 *          <DIR>
 *          集約の条件は以下の通りです。<BR>
 *            <DIR>
 *            ･同一賞味期限で、ケース品とピース品が続けて出力されている場合は、ケース品とピース品は１行に<BR>
 *             集約出来ます。<BR>
 *            ･同一ケース/ピース区分が続いた場合(ケース品、ケース品 等)は１行に集約は出来ません。<BR>
 *            ･ケース/ピース区分が指定無しはピース品の実績として扱います。<BR>
 *             但し、指定無しのみの実績データで１行の報告データを作成します。(ケース品との集約は出来ません。)<BR>
 *            </DIR>
 *          </DIR>
 *        </DIR>
 *      2.集約した仕分け実績データを１行づつ仕分け実績ファイル(CSV)に出力します。<BR>
 * <BR>
 *   ＜仕分け実績ファイル(CSV)出力 処理概要＞ <BR>
 *      <DIR>
 *      仕分け実績ファイル(CSV)の各項目の編集を行い、CSVファイルに出力します。<BR>
 *      出力した行の実績区分=完了の場合、完了した仕分けデータの実績送信情報と作業情報の報告フラグを<BR>
 *      作業日+予定一意キーで、報告済に更新します。<BR>
 *      1.仕分け実績ファイル(CSV)の1行分の各項目を編集します。<BR>
 *        <DIR>
 *        実績区分、ピース品仕分け場所、ケース品仕分け場所、ケース仕分け実績数、ピース仕分け実績数 以外は<BR>
 *        実績送信情報の内容をセットします。<BR>
 *        (編集内容)<BR>
 *           <DIR>
 *           仕分け予定日   = 予定日<BR>
 *           荷主コード   = 荷主コード<BR>
 *           荷主名称     = 荷主名称<BR>
 *           出荷先コード = 出荷先コード<BR>
 *           出荷先名称   = 出荷先名称<BR>
 *           伝票No       = 出荷伝票No<BR>
 *           行No		  = 出荷行No<BR>
 *           JAN          = 商品コード<BR>
 *           ボールITF    = ボールITF<BR>
 *           ケースITF    = ケースITF<BR>
 *           ボール入数   = ボール入数<BR>
 *           ケース入数   = ケース入数<BR>
 *           品名         = 商品名称<BR>
 *           仕分け数(バラ総量) = 作業予定数(ホスト予定数)<BR>
 *           <BR>
 *           ケース品仕分け場所 = 予定一意キー+賞味期限+ケース品仕分け場所で集約されたロケーション<BR>
 *           ピース品仕分け場所 = 予定一意キー+賞味期限+ピース品仕分け場所で集約されたロケーション<BR>
 *           <BR>
 * 			 TC区分		  = DC/DC区分<BR>
 * 			 仕入先コード = 仕入先コード<BR>
 *           仕入先名     = 仕入先名称<BR>
 *           入荷伝票番号 = 入荷伝票No<BR>
 *           入荷行No     = 入荷行No<BR>
 *           ケース仕分け実績数 = 予定一意キー+賞味期限+ケース品仕分け場所で集約されたケース品の合計数<BR>
 *           ピース仕分け実績数 = 予定一意キー+賞味期限+ケース品仕分け場所で集約されたピース品の合計数<BR>
 *           <BR>
 *           仕分け実績日 は実績区分の内容により、以下の通りとします。<BR>
 *             <DIR>
 *             実績区分<BR>
 *               <DIR>
 *               0:未処理<BR>
 *                 <DIR>
 *                 ･実績日 = ""<BR>
 *                 </DIR>
 *               1:仕分け完了<BR>
 *                 <DIR>
 *                 ･仕分け実績日 = 作業日(画面で入力された作業日)<BR>
 *                 </DIR>
 *               </DIR>
 *             </DIR>
 *           </DIR>
 *        </DIR>
 *      2.仕分け実績ファイル(CSV)を1行出力します。<BR>
 *        <DIR>
 *        DataReportCsvWriterのwriteLineメソッドを使用して仕分け実績データに出力します。<BR>
 *        </DIR>
 *      3.報告フラグの更新を行います。(報告したﾃﾞｰﾀの一番新しい最終更新日時よりも古いﾃﾞｰﾀを更新します。)<BR>
 *        <DIR>
 *          <DIR>
 *          3-1.実績送信情報のHandlerインスタンス作成します。<BR>
 *          3-2.実績送信情報のAlterKeyインスタンスを作成します。<BR>
 *          3-3.作業情報のHandlerインスタンスを作成します。<BR>
 *          3-4.作業情報のAlterKeyインスタンスを作成します。<BR>
 *          3-5.実績送信情報を更新するための検索キー、更新データをAlterKeyにセットします。<BR>
 *              <DIR>
 *              ･検索条件<BR>
 *                 <DIR>
 *                 作業日、予定一意キー<BR>
 *                 </DIR>
 *              ･更新データ<BR>
 *                 <DIR>
 *                 報告フラグ = 報告済<BR>
 *                 </DIR>
 *              </DIR>
 *          3-6.実績送信情報のmodifyを実行し、更新します。<BR>
 *          3-7.作業情報を更新するための検索キー、更新データをAlterKeyにセットします。<BR>
 *              <DIR>
 *              ･検索条件<BR>
 *                 <DIR>
 *                 作業日、予定一意キー<BR>
 *                 </DIR>
 *              ･更新データ<BR>
 *                 <DIR>
 *                 報告フラグ = 報告済<BR>
 *                 </DIR>
 *              </DIR>
 *          3-8.作業情報のmodifyを実行し、更新します。<BR>
 *              <DIR>
 *              システムパッケージの作業情報handlerのmodifyを使用し更新を行います。<BR>
 *              </DIR>
 *          </DIR>
 *        </DIR>
 *      </DIR>
 *   </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/08</TD><TD>M.INoue</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:23:02 $
 * @author  $Author: suresh $
 */
public class SortingReportDataCreator extends AbstractExternalReportDataCreator
{
	// Class fields --------------------------------------------------

	// 実績区分 (未処理)
	static final String UN_PROCESSING = "0";
	// 実績区分 (仕分け完了)
	static final String SORTING_COMPLETE = "1";
	// 実績区分 (一部完了)
	static final String PART_COMPLETE = "2";
	// Class variables -----------------------------------------------
	private final String pName = "SortingReportData";

	// Class method --------------------------------------------------
	/**
	 * デリミタ
	 */
	protected String wDelim = MessageResource.DELIM;

	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/21 04:23:02 $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * ConnectionオブジェクトとLocaleオブジェクトをパラメータとして受け取り、仕分け報告データを作成します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParam <CODE>Parameter</CODE>クラスを継承したクラス
	 * @throws IOException 入出力の例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @return 報告データ作成処理に成功した場合はtrue、報告データが無い、又は、作成に失敗した場合はfalseを返します。
	 */
	public boolean report(Connection conn, Parameter startParam) throws IOException, ReadWriteException
	{
		SystemParameter sparam = (SystemParameter) startParam;

		// 報告データ件数を初期化します。
		setReportCount(0);

		// 報告フラグの初期値をFalseで宣言をします。
		boolean reportFlag = false;

		// VIEWを検索する為のクラスインスタンスを作成します。
		SortingHostSendViewFinder vfinder = new SortingHostSendViewFinder(conn);
		SortingHostSendViewSearchKey vkey = new SortingHostSendViewSearchKey();

		//---- 実績送信情報から仕分実績データを抽出する為のFROM句の副問合せ文を作成します。 ----

		// 取得するカラムをセットします。
		// 予定一意キー
		vkey.setPlanUkeyCollect("");

		// 検索条件をセットします。

		// 保留により、作業情報が分かれ、同一集約キー内で完了と未開始の状態になったものが[作業途中]、
		// 同一集約キーで未開始しか無いものが[未開始]として扱うので、
		// 検索対象は
		//   作業区分 = 仕分 and
		//   ( 状態フラグ = 完了 and 作業日 = 画面で指定された作業日 ) or
		//   ( ( 状態フラグ = 未開始 又は 開始 又は 作業中 ) and 作業日 = "" and 予定日 <= 画面で指定された作業日 ) 
		vkey.setJobType(SystemDefine.JOB_TYPE_SORTING, "=", "", "", "and");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION, "=", "((", "", "and");
		vkey.setWorkDate(sparam.getWorkDate(), "=", "", ")", "or");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART, "=", "((", "", "or");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_START, "=", "", "", "or");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING, "=", "", ")", "and");
		vkey.setWorkDate("", "=", "", "", "and");
		vkey.setPlanDate(sparam.getWorkDate(), "<=", "", "))", "or");

		// グループ順をセットします。
		vkey.setPlanUkeyGroup(1);

		// 副問合せ文作成メソッドを実行します。
		String subSql = vfinder.createFindSql(vkey);

		vkey.KeyClear();

		//---- 実績送信情報から仕分実績データを抽出する条件を作成します。 ----

		// テーブル結合条件をセットします。
		// 予定一意キーの結合値をセット
		vkey.setPlanUkeyJoin();

		// VIEWの検索順序をセットします。
		//予定一意キー+ロケーションNo+ケース/ピース区分+登録日時+作業実績数 順	
		vkey.setPlanUkeyOrder(1, true);
		vkey.setWorkFormFlagOrder(2, true);
		vkey.setLocationNoOrder(3,true);
		vkey.setRegistDateOrder(4, true);
		vkey.setResultQtyOrder(5, true);
		// VIEW検索
		HostSendView[] hostSendView = null;

		if( vfinder.search(vkey, subSql) <= 0 )
		{
			// 対象データはありませんでした。
			setMessage("6003018");
			return false;
		}

		String planUkey = "";
		String casePieceFlag = "";
		String locationNo = "";
		// 実績数
		int resultQty = 0;
		// 欠品数
		int shortageCnt = 0;

		// 状態フラグが未開始のデータ有り(「同一予定一意キー」内に未開始が有れば、trueになる)
		boolean unStarting = false;
		// 状態フラグが完了のデータ有り(「同一予定一意キー」内に完了が有れば、trueになる)
		boolean completion = false;

		// 集約キー単位で報告データを作成する為、実績送信View情報、実績数、欠品数を一時記憶する領域。
		Vector hostSendViewVec = new Vector();
		Vector resultQtyVec = new Vector();
		Vector shortageCntVec = new Vector();

		// CSV出力クラスのインスタンスを作成します。
		DataReportCsvWriter csvw = null;
		// 最終更新日時(作業報告フラグの更新に使用します)
		Date lastUpdate = null;
		HostSendView hostSendViewSv = null;
		try
		{
			boolean isFirst = true;
			while (vfinder.isNext())
			{
				// 検索結果を100件づつ出力していく。
				hostSendView = (HostSendView[]) vfinder.getEntities(100);

				// 検索された一番初めの値を保持します。
				if (isFirst)
				{
					// 報告データ編集用のローカル変数を宣言し、1件目の集約キーをローカル変数に取得します。
					// 集約キー(予定一意キー)
					// 予定一意キー
					planUkey = hostSendView[0].getPlanUkey();
					// ケース/ピース区分
					casePieceFlag = hostSendView[0].getWorkFormFlag();
					// ロケーション
					locationNo = hostSendView[0].getLocationNo();
					// 最終更新日時
					lastUpdate = hostSendView[0].getLastUpdateDate();

					hostSendViewSv = hostSendView[0];

					isFirst = false;
				}

				for( int count = 0 ; count < hostSendView.length ; count++ )
				{
					// 予定一意キーが変わったか？
					if( !(hostSendView[count].getPlanUkey().equals(planUkey)) )
					{
						// 実績送信View情報、仕分け実績数をVectorに記憶します。
						resultVectorSet(hostSendViewSv, resultQty, shortageCnt, hostSendViewVec, resultQtyVec, shortageCntVec);
						// 実績区分を取得します。
						String resultKind = getResultKind(conn, planUkey, unStarting, completion);
						// 報告の可否を決定します。
						if( reportDetermin(startParam, resultKind) )
						{
							// 仕分け実績データ(CSV)に出力します。
							// また、報告を行う実績送信情報の報告フラグも報告済に更新します。
							if(!(csvw instanceof DataReportCsvWriter))
							{
								csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_PICKINGSUPPORT);
							}
							int i = sortingCsvWrite(conn, csvw, sparam.getWorkDate(), hostSendViewVec
														 , resultQtyVec, shortageCntVec, resultKind, lastUpdate);

							if( i > 0 )
							{
								// 報告データ件数を加算します。
								setReportCount(getReportCount() + i);
								// 報告データ有りをセットします。
								reportFlag = true;
							}
						}
						// 予定一意キーを次にします。
						planUkey = hostSendView[count].getPlanUkey();

						// 予定一意キーが変わったので、ケース/ピース区分、ロケーション、仕分け実績数
						// のローカル変数も更新します。
						casePieceFlag = hostSendView[count].getWorkFormFlag();
						locationNo = hostSendView[count].getLocationNo();
						// 最終更新日時
						lastUpdate = hostSendView[count].getLastUpdateDate();
						resultQty = 0;
						shortageCnt = 0;

						// 状態フラグの一時記憶領域を初期化します。
						unStarting = false;
						completion = false;

						// 実績送信View情報、実績数、欠品数の一時記憶領域を初期化します。
						hostSendViewVec.clear();
						resultQtyVec.clear();
						shortageCntVec.clear();
					}
					else
					{
						// ケース/ピース区分、ロケーションが変わったか？
						if( !(hostSendView[count].getWorkFormFlag().equals(casePieceFlag)) ||
							!(hostSendView[count].getLocationNo().equals(locationNo)) )
						{
							// ケース/ピース区分、仕分け場所、仕分け実績数をVectorに記憶します。
							resultVectorSet(hostSendViewSv, resultQty, shortageCnt, hostSendViewVec, resultQtyVec, shortageCntVec);
							// 新たなケース/ピース区分、ロケーションを設定し、実績数をクリアします。
							casePieceFlag = hostSendView[count].getWorkFormFlag();
							locationNo = hostSendView[count].getLocationNo();
							resultQty = 0;
							shortageCnt = 0;
						}
					}
					// 送信フラグを変更する最も新しい更新日時を保持します
					if(  lastUpdate.compareTo(hostSendView[count].getLastUpdateDate()) < 0 )
					{
						lastUpdate = hostSendView[count].getLastUpdateDate();
					}

					// 完了したデータから、画面の作業日で指定されたもののみの実績数、欠品数を集計します。
					if( ( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION) ) &&
						( hostSendView[count].getWorkDate().equals(sparam.getWorkDate())) )
					{
						// 仕分け実績数の加算をします。
						resultQty = resultQty + hostSendView[count].getResultQty();
						shortageCnt = shortageCnt + hostSendView[count].getShortageCnt();
					}

					// 状態フラグが未開始？
					if( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_UNSTART) )
					{
						// 状態フラグが未開始のデータが有った事を記憶する為に、trueにします
						unStarting = true;
					}
					// 状態フラグが完了？
					if( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION) )
					{
						// 状態フラグが完了のデータが有った事を記憶する為に、trueにします
						completion = true;
					}

					hostSendViewSv = hostSendView[count];
				}
			}

			// 最後の集約データの処理を行います。
			// 実績送信View情報、仕分け実績数をVectorに記憶します。
			resultVectorSet(hostSendViewSv, resultQty, shortageCnt
											, hostSendViewVec, resultQtyVec, shortageCntVec);
			// 実績区分を取得します。
			String resultKind = getResultKind(conn, planUkey, unStarting, completion);
			// 報告の可否を決定します。
			if( reportDetermin(startParam, resultKind) )
			{
				// 仕分け実績データ(CSV)に出力します。
				// また、報告を行う実績送信情報の報告フラグも報告済に更新します。
				if(!(csvw instanceof DataReportCsvWriter))
				{
					csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_PICKINGSUPPORT);
				}
				int i = sortingCsvWrite(conn, csvw, sparam.getWorkDate()
											 , hostSendViewVec, resultQtyVec, shortageCntVec, resultKind, lastUpdate);
				if( i > 0 )
				{
					// 報告データ件数を加算します。
					setReportCount(getReportCount() + i);
					// 報告データ有りをセットします。
					reportFlag = true;
				}
			}
			if( getReportCount() == 0 )
			{
				// 対象データはありませんでした。
				setMessage("6003018");
			}
			else
			{
				// 報告データの作成が終了しました。
				setMessage("6001009");
			}

			return reportFlag;
		}
		catch (FileNotFoundException e)
		{
			// 6006020=ファイルの入出力エラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006020, e), this.getClass().getName() ) ;
			// 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
			setMessage("6007031");
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			// 6006001=予期しないエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006001, e), this.getClass().getName() ) ;
			// 6027009=予期しないエラーが発生しました。ログを参照してください。
			setMessage("6027009");
			
			// ファイルの削除処理
			if (csvw != null)
			{
				csvw.delete();
			}
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			try
			{
				// クローズ処理
				if (csvw != null)
				{
					csvw.close();
				}
			}
			catch (IOException e)
			{
				// 6006020=ファイルの入出力エラーが発生しました。{0}
				RmiMsgLogClient.write( new TraceHandler(6006020, e), this.getClass().getName() ) ;
				// 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
				setMessage("6007031");
				// ファイルの削除処理
				csvw.delete();
				throw new ReadWriteException(e.getMessage());
			}
			finally
			{
				vfinder.close();
			}
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 実績情報をVectorにセットします。<BR>
	 * 
	 * @param hostSendView 実績送信View情報
	 * @param resultQty 仕分け実績数
	 * @param shortageCnt 欠品
	 * @param hostSendViewVec 実績送信View情報(予定一意キー+ケース/ピース区分+ロケーション 分)
	 * @param resultQtyVec 仕分け実績数(予定一意キー+ケース/ピース区分+ロケーション 分)
	 * @param shortageCntVec 欠品数(予定一意キー+ケース/ピース区分+ロケーション 分)
	 */
	protected void resultVectorSet(HostSendView hostSendView, int resultQty, int shortageCnt
											, Vector hostSendViewVec, Vector resultQtyVec, Vector shortageCntVec)
	{
		// 実績送信情報を記憶します。
		hostSendViewVec.addElement(hostSendView);
		// 実績数
		resultQtyVec.addElement(new Integer(resultQty));
		// 欠品数
		shortageCntVec.addElement(new Integer(shortageCnt));
	}

	/**
	 * 実績区分を求めます。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param planUkey 予定一意キー
	 * @param unStarting 予定一意キー内の未開始データ有無
	 * @param completion 予定一意キー内の完了データ有無
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected String getResultKind(Connection conn, String planUkey, boolean unStarting, boolean completion)
																						throws ReadWriteException
	{
		// 完了のみは仕分け完了
		if( !unStarting && completion )
		{
			// 実績区分 (仕分け完了)
			return SORTING_COMPLETE;
		}

		// 未開始のみは未処理、又は、報告済みのデータが有るかも
		HostSendSearchKey skey = new HostSendSearchKey();
		HostSendHandler handle = new HostSendHandler(conn);
		skey.setPlanUkey(planUkey);
		skey.setReportFlag(HostSend.REPORT_FLAG_SENT);
		int sendCount = handle.count(skey);

		// 未開始のみは未処理
		if( unStarting && !completion && sendCount == 0 )
		{
			return UN_PROCESSING;
		}

		// (一部完了)
		return PART_COMPLETE;
	}

	/**
	 * 集約キーデータ報告の可否を決定します。<BR>
	 * 
	 * @param startParam <CODE>Parameter</CODE>クラスを継承したクラス
	 * @param resultKind 実績区分
	 * @return データ報告の可否を返します。
	 */
	protected boolean reportDetermin(Parameter startParam, String resultKind)
	{
		SystemParameter sparam = (SystemParameter) startParam;

		// [未作業分の報告を行う]を指定した場合のみ未開始ﾃﾞｰﾀを報告する。
		if( !sparam.getSelectReportSortingData_Unworking() &&
			resultKind.equals(UN_PROCESSING) )
		{
			return false;
		}
		// [作業途中の報告を行う]が指定された場合のみ一部完了ﾃﾞｰﾀを報告する。
		if( !sparam.getSelectReportSortingData_InProgress() &&
			resultKind.equals(PART_COMPLETE) )
		{
			return false;
		}

		return true;
	}

	/**
	 * 仕分け実績の集約と出力を行います。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param csvw CSV Writer オブジェクト
	 * @param workDate 作業日
	 * @param hostSendViewVec 実績送信VIEW情報(予定一意キー+ケース/ピース区分+ロケーション 分)
	 * @param resultQtyVec 仕分け実績数(１集約キー分)
	 * @param shortageCntVec 欠品数(１集約キー分)
	 * @param resultKind 実績区分
	 * @param lastUpdate この最新更新日時以前のﾃﾞｰﾀの送信フラグを変更します。
	 * @return 報告データに出力した件数を返します。
	 * @throws IOException  I/Oエラーが発生した場合に通知されます。
	 * @throws CSVItemNotFoundException CSVファイルまたは環境情報に指定された項目が存在しない場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected int sortingCsvWrite(Connection conn, DataReportCsvWriter csvw, String workDate
													, Vector hostSendViewVec, Vector resultQtyVec, Vector shortageCntVec
													, String resultKind, Date lastUpdate)
													throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		// 実績送信情報
		HostSendView[] hostSendViewTempAry = new HostSendView[hostSendViewVec.size()];
		hostSendViewVec.copyInto(hostSendViewTempAry);
		// 実績数
		Integer[] resultQtyTempAry = new Integer[resultQtyVec.size()];
		resultQtyVec.copyInto(resultQtyTempAry);
		// 欠品数
		Integer[] shortageCntTempAry = new Integer[shortageCntVec.size()];
		shortageCntVec.copyInto(shortageCntTempAry);

		// 集約キー単位で報告データを作成する為、実績送信View情報、実績数、欠品数を一時記憶する領域。
		Vector hostSendViewTempVec = new Vector();
		Vector resultQtyTempVec = new Vector();

		for( int i = 0 ; i < hostSendViewTempAry.length ; i++ )
		{
			if( (resultQtyTempAry[i].intValue() == 0) && (shortageCntTempAry[i].intValue() == 0) &&
				(!resultKind.equals(UN_PROCESSING)) )
			{
				// 実績数、欠品数共にゼロの場合、未処理以外は実績を出力しません。
				continue;
			}
			// 実績送信情報
			hostSendViewTempVec.addElement(hostSendViewTempAry[i]);
			// 仕分実績数
			resultQtyTempVec.addElement(resultQtyTempAry[i]);
		}

		if( hostSendViewTempVec.size() <= 0 )
		{
			// 実績報告データ無し。
			return 0;
		}

		//  報告対象となる実績データを配列にコピーします。
		// 実績送信View情報
		HostSendView[] hostSendViewAry = new HostSendView[hostSendViewTempVec.size()];
		hostSendViewTempVec.copyInto(hostSendViewAry);
		// 実績数
		Integer[] resultQtyAry = new Integer[resultQtyTempVec.size()];
		resultQtyTempVec.copyInto(resultQtyAry);

		// 入力されてきたhostSendViewAry配列の中にケースとピースのﾃﾞｰﾀが混在している場合はTRUEになる
		boolean CasePieceMix = false;
		boolean existCase = false;
		boolean existPiece = false;
		for( int i = 0 ; i < hostSendViewAry.length ; i++ )
		{
			// ケース/ピース区分が 指定無し
			if( hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING) )
			{
				CasePieceMix = false;
				break;
			}
			else
			{
				if (hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE))
				{
					existCase = true;
					if (existPiece)
					{
						CasePieceMix = true;
						break;
					}
				}
				else if (hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
				{
					existPiece = true;
					if (existCase)
					{
						CasePieceMix = true;
						break;
					}
				}
			}
		}

		String caseLocation = "";
		String pieceLocation = "";
		int caseQty = 0;
		int pieceQty = 0;
		int reportCount = 0;
		//ケース・ピースで集約できないとき
		boolean printOut = false;
		// ケース・ピースで集約できるとき
		boolean printOutCasePiece = false;
		// ケース/ピース区分を参照して作成した ケース品仕分け場所 と ピース品仕分け場所 の集約情報を基に
		// 実績出力を行います。
		for( int i = 0 ; i < hostSendViewAry.length ; i++ )
		{
			if (!CasePieceMix)
			{
				// ケース/ピース区分が 指定無し 又は、ピース ならば、実績をピース情報にセットします。
				if( (hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING)) ||
					(hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE)) )
				{
					//未完了分のﾃﾞｰﾀの棚は報告しない
					if (hostSendViewAry[i].getStatusFlag().equals(HostSendView.STATUS_FLAG_UNSTART))
					{
						pieceLocation = "";
					}
					else
					{
						pieceLocation = hostSendViewAry[i].getLocationNo();
					}
					int qty = resultQtyAry[i].intValue();
					pieceQty = qty;
					caseLocation = "";
					caseQty = 0;
				}
				else
				{
					//未完了分のﾃﾞｰﾀの棚は報告しない
					if (hostSendViewAry[i].getStatusFlag().equals(HostSendView.STATUS_FLAG_UNSTART))
					{
						caseLocation = "";
					}
					else
					{
						caseLocation = hostSendViewAry[i].getLocationNo();
					}
					caseQty = resultQtyAry[i].intValue();
					pieceLocation = "";
					pieceQty = 0;
				}
				printOut = true;
			}
			else
			{
				// ケース/ピース区分を参照し、ケース品仕分け場所、ピース品仕分け場所を取得します。
				if( hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE) )
				{
					// 未完了分のﾃﾞｰﾀの棚は報告しない
					if (hostSendViewAry[i].getStatusFlag().equals(HostSendView.STATUS_FLAG_UNSTART))
					{
						caseLocation = "";
					}
					else
					{
						caseLocation = hostSendViewAry[i].getLocationNo();
					}
					caseQty = resultQtyAry[i].intValue();
				}
				else
				{
					//未完了分のﾃﾞｰﾀの棚は報告しない
					if (hostSendViewAry[i].getStatusFlag().equals(HostSendView.STATUS_FLAG_UNSTART))
					{
						pieceLocation = "";
					}
					else
					{
						pieceLocation = hostSendViewAry[i].getLocationNo();
					}
					pieceQty = resultQtyAry[i].intValue();
				}
				printOutCasePiece = true;
				printOut = false;
			}

			// 報告データ出力
			if( printOut )
			{
				CsvWriteLine(conn, csvw, workDate, hostSendViewAry[i], caseLocation, caseQty
									   , pieceLocation, pieceQty, resultKind, lastUpdate);
				reportCount++;

				caseLocation = "";
				pieceLocation = "";
				caseQty = 0;
				pieceQty = 0;
				printOut = false;
			}
		}
		// 報告データ出力
		if( printOutCasePiece )
		{
			CsvWriteLine(conn, csvw, workDate, hostSendViewAry[hostSendViewAry.length-1]
									, caseLocation, caseQty, pieceLocation, pieceQty, resultKind, lastUpdate);
			reportCount++;

			caseLocation = "";
			pieceLocation = "";
			caseQty = 0;
			pieceQty = 0;
			printOut = false;
		}
		return(reportCount);
	}

	/**
	 * １行分の仕分け実績出力を行います。<BR>
	 * 出力後、実績送信情報と、作業情報の報告フラグと最終更新日時の更新を行います。
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param csvw CSV Writer オブジェクト
	 * @param workDate 作業日
	 * @param hostSendView 実績送信VIEW情報
	 * @param caseLocation ケース品仕分け場所
	 * @param caseQty ケース仕分け実績数
	 * @param pieceLocation ピース品仕分け場所
	 * @param pieceQty ピース仕分け実績数
	 * @param resultKind 実績区分
	 * @param lastUpdate この最新更新日時以前のﾃﾞｰﾀの送信フラグを変更します。
	 * @throws IOException  I/Oエラーが発生した場合に通知されます。
	 * @throws CSVItemNotFoundException CSVファイルまたは環境情報に指定された項目が存在しない場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void CsvWriteLine(Connection conn, DataReportCsvWriter csvw, String workDate, HostSendView hostSendView
											 , String caseLocation, int caseQty
											 , String pieceLocation, int pieceQty, String resultKind, Date lastUpdate)
															throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		try
		{
			// 仕分け実績データを項目にセットします。
			// 仕分け予定日
			csvw.setValue("PLAN_DATE", hostSendView.getPlanDate());
			// 荷主コード
			csvw.setValue("CONSIGNOR_CODE", hostSendView.getConsignorCode());
			// 荷主名称
			csvw.setValue("CONSIGNOR_NAME", hostSendView.getConsignorName());
			// 出荷先コード
			csvw.setValue("CUSTOMER_CODE", hostSendView.getCustomerCode());
			// 出荷先名
			csvw.setValue("CUSTOMER_NAME", hostSendView.getCustomerName1());
			// 伝票No
			csvw.setValue("SHIPPING_TICKET_NO", hostSendView.getShippingTicketNo());
			// 行No
			csvw.setValue("SHIPPING_LINE_NO", new Integer(hostSendView.getShippingLineNo()));
			// JAN
			csvw.setValue("ITEM_CODE", hostSendView.getItemCode());
			// ボールITF
			csvw.setValue("BUNDLE_ITF", hostSendView.getBundleItf());
			// ケースITF
			csvw.setValue("ITF", hostSendView.getItf());
			// ボール入数
			csvw.setValue("BUNDLE_ENTERING_QTY", new Integer(hostSendView.getBundleEnteringQty()));
			// ケース入数
			csvw.setValue("ENTERING_QTY", new Integer(hostSendView.getEnteringQty()));
			// 品名
			csvw.setValue("ITEM_NAME1", hostSendView.getItemName1());
			// 仕分け数(バラ総数)
			csvw.setValue("PLAN_QTY", new Integer(hostSendView.getHostPlanQty()));
			// ピース品仕分け場所
			csvw.setValue("JOB_LOCATION_PIECE", pieceLocation);
			// ケース品仕分け場所
			csvw.setValue("JOB_LOCATION_CASE", caseLocation);	
			// TC区分
			csvw.setValue("TCDC_FLAG", hostSendView.getTcdcFlag());
			// 仕入先コード
			csvw.setValue("SUPPLIER_CODE", hostSendView.getSupplierCode());
			// 仕入先名
			csvw.setValue("SUPPLIER_NAME1", hostSendView.getSupplierName1());
			// 入荷伝票No
			csvw.setValue("INSTOCK_TICKET_NO", hostSendView.getInstockTicketNo());
			// 入荷行No
			csvw.setValue("INSTOCK_LINE_NO", new Integer(hostSendView.getInstockLineNo()));

			// ピース仕分け実績数
			csvw.setValue("RESULT_QTY_PIECE", new Integer(pieceQty));
			// ケース仕分け実績数
			csvw.setValue("RESULT_QTY_CASE", new Integer(caseQty));

			// 実績区分が未処理
			if( resultKind.equals(UN_PROCESSING) )
			{
				// 仕分け実績日にはスペースをセットします。
				csvw.setValue("COMPLETE_DATE", "");
			}
			else
			{
				// 仕分け実績日
				csvw.setValue("COMPLETE_DATE", workDate);
			}

			// 実績区分
			csvw.setValue("RESULT_FLAG", resultKind);

			// 仕分け実績データをCSVに出力します。
			csvw.writeLine();

			// 実績区分が仕分完了又は、一部完了ならば、報告フラグと最新更新日時を更新します。
			// 実績区分が未処理ならば、報告フラグと最新更新日時を更新はしません。
			if( !(resultKind.equals(UN_PROCESSING)) )
			{
				/** 実績送信情報の報告フラグの更新を行います。*/
				HostSendHandler hhandle = new HostSendHandler(conn);
				HostSendSearchKey hsKey = new HostSendSearchKey();
				hsKey.setPlanUkey(hostSendView.getPlanUkey());
				hsKey.setWorkDate(workDate);
				hsKey.setLastUpdateDate(lastUpdate, "<=");
				if (hhandle.count(hsKey) > 0)
				{
					HostSendAlterKey hakey = new HostSendAlterKey();
					// 実績送信情報の報告フラグの更新を行います。
					// 集約キーを更新キーに設定します。
					hakey.setPlanUkey(hostSendView.getPlanUkey());
					hakey.setWorkDate(workDate);
					hakey.setLastUpdateDate(lastUpdate, "<=");

					// 更新項目を設定します。
					// 実績送信情報の報告フラグを報告済へ更新します。
					hakey.updateReportFlag(SystemDefine.REPORT_FLAG_SENT);
					hakey.updateLastUpdatePname(pName);
					// 実績送信情報を更新します
					hhandle.modify(hakey);
				}

				/** 作業情報の報告フラグの更新を行います。*/
				SortingWorkingInformationHandler swhandle = new SortingWorkingInformationHandler(conn); 
				swhandle.updateReportFlag( hostSendView.getPlanUkey(), workDate, pName ) ;
			}
		}
		catch(NotFoundException e)
		{
			throw (new ReadWriteException("6006002" + wDelim + "DNHOSTSEND"));
		}
		catch(InvalidDefineException e)
		{
			throw (new ReadWriteException("6006002" + wDelim + "DNHOSTSEND"));
		}

		return;
	}
	// Private methods -----------------------------------------------
}
//end of class