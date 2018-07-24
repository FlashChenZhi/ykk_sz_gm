//$Id: ShippingReportDataCreator.java,v 1.2 2006/11/21 04:22:58 suresh Exp $
package jp.co.daifuku.wms.shipping.schedule;

// $Id: ShippingReportDataCreator.java,v 1.2 2006/11/21 04:22:58 suresh Exp $
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

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.HostSendView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalReportDataCreator;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.CSVItemNotFoundException;
import jp.co.daifuku.wms.base.utility.DataReportCsvWriter;
import jp.co.daifuku.wms.shipping.dbhandler.ShippingHostSendViewFinder;
import jp.co.daifuku.wms.shipping.dbhandler.ShippingHostSendViewSearchKey;
import jp.co.daifuku.wms.shipping.dbhandler.ShippingWorkingInformationHandler;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai <BR>
 * <BR>
 * <CODE>ShippingReportDataCreator</CODE>クラスは、出荷実績データ報告処理を行うクラスです。<BR>
 * <CODE>AbstractExternalReportDataCreator</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * このクラスが持つメソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、<BR>
 * トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.データ報告処理（<CODE>report(Connection conn, Parameter startParam)</CODE>メソッド）<BR>
 * <BR>
 *   <DIR>
 *   ConnectionオブジェクトとParameterオブジェクトをパラメータとして受け取り、データベースの実績送信情報と作業情報を<BR>
 *   結合したVIEW から出荷検品実績データファイル(CSVファイル)を作成します。<BR>
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
 *         ロットNo(lot_no, result_lot_no)、作業結果ロケーション、作業報告フラグ、作業者コード、端末No･RFTNo、<BR>
 *         登録日時、登録処理名、最終更新日時、最終更新処理名<BR>
 *         (注記)<BR>
 *           <DIR>
 *           a.作業情報には作業日が無いので、作業情報から取得するView情報の作業日にはNullがセットされます。<BR>
 *           b.賞味期限について、作業情報は賞味期限(use_by_date)、実績送信情報は賞味期限(result_use_by_date)から取得します。<BR>
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
 *      4.VIEWを検索するための検索キーをSearchKeyにセットします。<BR>
 *          <DIR>
 *          ･検索条件<BR>
 *             <DIR>
 *             作業区分 = 出荷<BR>
 *             状態フラグ = 完了<BR>
 *             作業日 = 画面で入力された作業日<BR>
 *             ･[作業途中の報告を行う]、 が指定された時、以下の条件を追加します。<BR>
 *                 <DIR>
 *                 状態フラグ = 開始済 or 作業中 or 未開始<BR>
 *                 作業日 = ""<BR>
 *                 </DIR>
 *             </DIR>
 *          ･順序<BR>
 *             <DIR>
 *             出荷予定日+荷主コード+出荷先コード+出荷伝票No+出荷伝票行+登録日時+作業実績数+賞味期限<BR>
 *             </DIR>
 *          </DIR>
 *      5.VIEW Handlerインスタンスのfindを使用してVIEW検索を実行し、実績送信VIEW Entityに取得します。<BR>
 *      6.ローカル変数を作成します。<BR>
 *      7.クラスインスタンス作成<BR>
 *      8.VIEWから取得した実績送信VIEW Entityの終了までLoopを行います。<BR>
 *        <DIR>
 *          9.実績送信VIEWの集約キー(作業日を除く)が次の集約キーに変わったのかを確認し、次の集約キーに変わった場合は、<BR>
 *            以下の処理を行います。<BR>
 *            <DIR>
 *            9-1.報告対象となるデータが有る場合。<BR>
 *               <DIR>
 *               ･出荷検品実績ファイル(CSV)の出力情報を設定します。<BR>
 *               ･集約キー中で存在した賞味期限単位分の出荷検品実績ファイル(CSV)を出力します。<BR>
 *                (ＣＳＶファイルがオープンされていない場合は、出荷検品実績ＣＳＶファイルをオープンします)<BR>
 *               </DIR>
 *            9-2.ローカル変数を更新します。<BR>
 *               <DIR>
 *               ･集約キーに次の集約キーをセットします。<BR>
 *               ･次の賞味期限をセットします。<BR>
 *               ･実績区分 = 未出荷<BR>
 *               ･作業実績数 = 0<BR>
 *               </DIR>
 *            <BR>
 *            集約キーは同じで、賞味期限のみが変わった場合は、以下の処理を行います。<BR>
 *               <DIR>
 *               ･実績送信情報と、作業実績数を記憶します。<BR>
 *               ･次の賞味期限をセットします。<BR>
 *               ･作業実績数 = 0<BR>
 *               </DIR>
 *            </DIR>
 *        </DIR>
 *     10.実績送信VIEWの状態フラグを参照します。<BR>
 *            <DIR>
 *            ･出荷検品完了データ(状態フラグ = 完了)の場合。<BR>
 *               <DIR>
 *               ･作業実績数を集計します。<BR>
 *               ･集約キー単位で出荷検品完了した場合は、出荷検品実績の実績区分を編集します。<BR>
 *               </DIR>
 *            ･未開始データ(状態フラグ = 未開始)の場合。<BR>
 *               <DIR>
 *               未開始だけれど、集約キーの中で保留となったものが有る場合は作業中として扱い、作業中の報告を行うが指定<BR>
 *               されている場合は、集約キー毎の実績出力フラグにtrueをセットします。<BR>
 *               </DIR>
 *            </DIR>
 * <BR>
 *     11.Loop処理で集計途中の出荷検品実績データが有れば、出荷検品実績ファイル(CSV)に1行出力します。<BR>
 *     12.出荷検品実績ファイル(CSV)をクローズします。<BR>
 *     13.メッセージのセットを行います。<BR>
 * <BR>
 *   ＜出荷検品実績ファイル(CSV)出力 処理概要＞ <BR>
 *      <DIR>
 *      出荷検品実績ファイル(CSV)の各項目の編集を行い、CSVファイルに出力します。<BR>
 *      </DIR>
 *   ＜出荷検品実績ファイル(CSV)出力 処理詳細＞ <BR>
 *      1.出荷検品実績ファイル(CSV)の1行分の各項目を編集します。<BR>
 *        <DIR>
 *        実績区分、実績数、出荷実績日、賞味期限以外は実績送信情報の内容をセットします。<BR>
 *        <DIR>
 *      2.出荷検品実績ファイル(CSV)を1行出力します。<BR>
 *      3.報告フラグの更新を行います。<BR>
 *        <DIR>
 *        実績区分が全数出荷完了、又は、欠品確定の場合。<BR>
 *          <DIR>
 *          3-1.実績送信情報を更新するための検索キー、更新データをAlterKeyにセットします。<BR>
 *          3-2.実績送信情報のmodifyを実行し、更新します。<BR>
 *          3-3.作業情報を更新するための検索キー、更新データをAlterKeyにセットします。<BR>
 *          3-4.作業情報のmodifyを実行し、更新します。<BR>
 *          </DIR>
 *        </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>T.Nakai</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:22:58 $
 * @author  $Author: suresh $
 */
public class ShippingReportDataCreator extends AbstractExternalReportDataCreator
{
	// Class fields --------------------------------------------------

	// 実績区分 (未出荷)
	static final String UN_SHIPPING = "0";
	// 実績区分 (全数出荷完了)
	static final String SHIPPING_COMPLETE = "1";
	// 実績区分 (欠品確定)
	static final String SHORTAGE = "2";
	// 実績区分（作業途中）
	static final String WORK_MIDDLE = "9";
	
	private final String pName = "ShippingReportData";

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
		return ("$Revision: 1.2 $,$Date: 2006/11/21 04:22:58 $");
	}
	/**
	 * コンストラクタ
	 */
	public ShippingReportDataCreator()
	{
	}
	/**
	 * Connectionオブジェクトをパラメータとして受け取り、出荷実績報告データを作成します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParam <CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 報告データ作成処理に成功した場合はtrue、報告データが無い、又は、作成に失敗した場合はfalseを返します。
	 * @throws IOException 入出力の例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public boolean report(Connection conn, Parameter startParam) throws IOException, ReadWriteException
	{
		SystemParameter sparam = (SystemParameter) startParam;

		// 報告データ件数を初期化します。
		setReportCount(0);

		// 報告フラグの初期値をFalseで宣言をします。
		boolean reportFlag = false;

		// VIEWを検索する為のクラスインスタンスを作成します。
		ShippingHostSendViewFinder vfinder = new ShippingHostSendViewFinder(conn);
		ShippingHostSendViewSearchKey vkey = new ShippingHostSendViewSearchKey();

		// 取得するカラムをセットします。
		// 予定一意キー
		vkey.setPlanUkeyCollect("");

		// 検索条件をセットします。

		// 保留により、作業情報が分かれ、同一集約キー内で完了と未開始の状態になったものが[作業途中]、
		// 同一集約キーで未開始しか無いものが[未開始]として扱うので、検索対象は
		//   作業区分 = 出荷 and
		//   ( 状態フラグ = 完了 and 作業日 = 画面で指定された作業日 ) or
		//   ( ( 状態フラグ = 未開始 又は 開始 又は 作業中 ) and 作業日 = "" and 予定日 <= 画面で指定された作業日 ) 
		vkey.setJobType(SystemDefine.JOB_TYPE_SHIPINSPECTION, "=", "", "", "and");
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

		//---- 実績送信情報から出荷検品実績データを抽出する条件を作成します。 ----

		// テーブル結合条件をセットします。
		// 予定一意キーの結合値をセット
		vkey.setPlanUkeyJoin();

		// VIEWの検索順序をセットします。
		// 予定一意キー+賞味期限+登録日時+作業実績数 順
		vkey.setPlanUkeyOrder(1, true);
		vkey.setUseByDateOrder(2, true);
		vkey.setRegistDateOrder(3,true);
		vkey.setResultQtyOrder(4, true);

		// 実績情報の検索を実行します。
		int j = vfinder.search(vkey, subSql);
		if( j <= 0 )
		{
			// 対象データはありませんでした。
			setMessage("6003018");
			return false;
		}

		// 報告データ編集用のローカル変数を宣言し、初期化します。
		// 集約キー(予定一意キー、賞味期限)
		String planUkey = "";
		String useByDate = "";

		// 出荷実績数集計(「同一予定一意キー＋賞味期限」の元での実績の和)
		int resultQty = 0;
		// 欠品数集計(「同一予定一意キー＋賞味期限」の元での欠品数の和)
		int shortageCnt = 0;
		// 出荷実績数(「同一予定一意キー」の元での実績の和)
		int resultTotalQty = 0;
		// １集約の欠品数 集計(「同一予定一意キー」の元での欠品数の和)
		int shortageTotalCnt = 0;

		// 状態フラグが未開始のデータ有り(「同一予定一意キー」内に未開始が有れば、trueになる)
		boolean unStarting = false;
		// 状態フラグが完了のデータ有り(「同一予定一意キー」内に完了が有れば、trueになる)
		boolean completion = false;

		// 集約キーに賞味期限を含んだ単位で報告データを作成する為、実績送信情報と、を一時記憶する領域。
		Vector hostSendViewVec = new Vector();
		Vector resultQtyVec = new Vector();
		Vector shortageCntVec = new Vector();
		// CSV出力クラスのインスタンスを作成します。
		DataReportCsvWriter csvw = null;
		// 最終更新日時(作業報告フラグの更新に使用します)
		Date lastUpdate = null;

		HostSendView[] hostSendView = null;
		HostSendView hostSendViewSv = null;

		try
		{
			while (vfinder.isNext())
			{
				// 検索結果を100件づつ出力していく。
				hostSendView = (HostSendView[]) vfinder.getEntityes(100);

				// 最初の1件目なのか確認します。
				if(StringUtil.isBlank(planUkey))
				{
					// 最初の1件目の場合、ローカル変数に1件目の集約キーの値をセットします。
					// 集約キー(予定一意キー、賞味期限)
					//  (予定一意キー)
					planUkey = hostSendView[0].getPlanUkey();
					//  (賞味期限)
					useByDate = hostSendView[0].getUseByDate();
					// 最終更新日時
					lastUpdate = hostSendView[0].getLastUpdateDate();

					hostSendViewSv = hostSendView[0];
				}

				for( int count = 0 ; count < hostSendView.length ; count++ )
				{
					// 予定一意キーが変わったか？
					if( !(hostSendView[count].getPlanUkey().equals(planUkey)) )
					{
						// 実績送信View情報、出荷実績数をVectorに記憶します。
						resultVectorSet(hostSendViewSv, resultQty, shortageCnt, hostSendViewVec, resultQtyVec, shortageCntVec);

						// 実績区分を取得します。
						String resultKind = getResultKind(conn, hostSendViewSv, unStarting, completion, resultTotalQty, shortageTotalCnt);
						// 報告の可否を決定します。
						if( reportDetermin(startParam, resultKind) )
						{
							// 出荷検品実績データ(CSV)に出力します。
							// また、報告を行う実績送信情報の報告フラグも報告済に更新します。
							if (!(csvw instanceof DataReportCsvWriter))
							{
								csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_SHIPPINGINSPECTION);
							}

							int i = shippingCsvWrite(conn, sparam, csvw, hostSendViewVec, resultQtyVec
								 							, shortageCntVec, resultKind, lastUpdate);
							if( i > 0 )
							{
								// 報告データ件数を加算します。
								setReportCount(getReportCount() + i);
								// 報告データ有りをセットします。
								reportFlag = true;
							}
						}

						// 集約キーの更新を行います。
						//   予定一意キー
						planUkey = hostSendView[count].getPlanUkey();
						//   賞味期限
						useByDate = hostSendView[count].getUseByDate();

						// 出荷実績数集計
						resultQty = 0;
						// １集約の出荷実績数 集計
						resultTotalQty = 0;
						// 作業欠品数集計
						shortageCnt = 0;
						// １集約の欠品数 集計
						shortageTotalCnt = 0;
						// 最終更新日時
						lastUpdate = hostSendView[count].getLastUpdateDate();

						// 状態フラグの一時記憶領域を初期化します。
						unStarting = false;
						completion = false;

						hostSendViewVec.clear();
						resultQtyVec.clear();
						shortageCntVec.clear();
					}
					else
					{
						// 賞味期限が変わったか？
						if (!(hostSendView[count].getUseByDate().equals(useByDate)))
						{
							// 出荷実績数, 欠品数をVectorに記憶します。
							resultVectorSet(hostSendViewSv, resultQty, shortageCnt, hostSendViewVec, resultQtyVec, shortageCntVec);

							// 新たな賞味期限を設定し、実績数をクリアします。
							useByDate = hostSendView[count].getUseByDate();
							resultQty = 0;
							shortageCnt = 0;
						}
					}

					// 完了したデータから、画面の作業日で指定されたもののみの実績数、欠品数を集計します。
					if( ( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION) ) &&
						( hostSendView[count].getWorkDate().equals(sparam.getWorkDate())) )
					{
						// 出荷実績数の加算をします。「同一予定一意キー＋賞味期限」
						resultQty = resultQty + hostSendView[count].getResultQty();
						// 欠品数の加算「同一予定一意キー＋賞味期限」
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

					// 出荷実績数の加算をします。「同一予定一意キー」
					resultTotalQty = resultTotalQty + hostSendView[count].getResultQty();
					// 欠品数の加算「同一予定一意キー」
					shortageTotalCnt = shortageTotalCnt + hostSendView[count].getShortageCnt();
					// 集約グループ内でより新しい最終更新日時を記録します。
					if(  lastUpdate.compareTo(hostSendView[count].getLastUpdateDate()) < 0 )
					{
						lastUpdate = hostSendView[count].getLastUpdateDate();
					}

					hostSendViewSv = hostSendView[count];
				}
			}

			// 実績送信View情報、出荷実績数をVectorに記憶します。
			resultVectorSet(hostSendViewSv, resultQty, shortageCnt, hostSendViewVec, resultQtyVec, shortageCntVec);

			// 実績区分を取得します。
			String resultKind = getResultKind(conn, hostSendViewSv, unStarting, completion, resultTotalQty, shortageTotalCnt);
			// 報告の可否を決定します。
			if( reportDetermin(startParam, resultKind) )
			{
				// 出荷検品実績データ(CSV)に出力します。
				// また、報告を行う実績送信情報の報告フラグも報告済に更新します。
				if (!(csvw instanceof DataReportCsvWriter))
				{
					csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_SHIPPINGINSPECTION);
				}

				int i = shippingCsvWrite(conn, sparam, csvw, hostSendViewVec, resultQtyVec
					 							, shortageCntVec, resultKind, lastUpdate);
				if( i > 0 )
				{
					// 報告データ件数を加算します。
					setReportCount(getReportCount() + i);
					// 報告データ有りをセットします。
					reportFlag = true;
				}
			}

			if (getReportCount() == 0)
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

	// Private methods -----------------------------------------------

	/**
	 * 実績区分を求めます。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param hostSendView 同一予定一意キーでグループした最新の実績送信VIEW情報
	 * @param unStarting 予定一意キー内の未開始データ有無
	 * @param completion 予定一意キー内の完了データ有無
	 * @param resultQty 同一予定一意キーでグループした実績総数
	 * @param shortageQty 同一予定一意キーでグループした欠品総数
	 * @return 実績区分
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private String getResultKind(Connection conn, HostSendView hostSendView, boolean unStarting, boolean completion
													, int resultQty,int shortageCnt)throws ReadWriteException
	{
		// 未開始のみは未処理、又は、報告済みのデータが有るかも
		if( unStarting && !completion )
		{
			// 一意キーには未開始のみなのに、作業予定数(ホスト予定数) と 作業予定数が等しくない
			// と言う事は、すでに報告済みのデータが有るはずなので、実績送信情報を参照します。
			if( hostSendView.getHostPlanQty() != hostSendView.getPlanQty() )
			{
				HostSendSearchKey skey = new HostSendSearchKey();
				HostSendHandler handle = new HostSendHandler(conn);
				skey.setPlanUkey(hostSendView.getPlanUkey());
				skey.setReportFlag(HostSend.REPORT_FLAG_SENT);
				int sendCount = handle.count(skey);
				if( sendCount > 0 )
				{
					// すでに一部の実績データが実績報告済み (作業途中)
					return WORK_MIDDLE;
				}
			}
			return UN_SHIPPING;
		}

		// 未開始と完了が存在する
		if( unStarting && completion )
		{
			// (作業途中)
			return WORK_MIDDLE;
		}

		// 完了のみ
		// 実績総数がホスト予定数と等しい場合は(全数出荷完了)
		if (hostSendView.getHostPlanQty() <= resultQty)
		{
			return SHIPPING_COMPLETE;
		}
		else
		{
			// 欠品数が1以上の場合は (欠品確定)
			if (shortageCnt > 0)
			{
				return SHORTAGE;
			}
			else
			{
				// 完了のみで、ホスト予定数と実績数が等しくなく、欠品でも無いと言う事は、
				// すでに報告済みが有るはずなので、実績送信情報を参照する。
				HostSendSearchKey skey = new HostSendSearchKey();
				HostSendHandler handle = new HostSendHandler(conn);
				skey.setShortageCntCollect("SUM");
				skey.setPlanUkey(hostSendView.getPlanUkey());
				skey.setReportFlag(HostSend.REPORT_FLAG_SENT);
				HostSend[] hostSend = (HostSend[]) handle.find(skey);
				// 報告済みに欠品が有れば「欠品確定」とします。
				if( hostSend[0].getShortageCnt() > 0 )
				{
					return SHORTAGE;
				}
				// 報告済みにも欠品が無かったので、「全数出荷完了」とします。
				return SHIPPING_COMPLETE;
			}
		}
	}

	/**
	 * 集約キーデータ報告の可否を決定します。<BR>
	 * 
	 * @param startParam <CODE>Parameter</CODE>クラスを継承したクラス
	 * @param resultKind 実績区分
	 * @return データ報告の可否を返します。
	 */
	private boolean reportDetermin(Parameter startParam, String resultKind)
	{
		SystemParameter sparam = (SystemParameter) startParam;

		// 実績区分 = 未出荷で、「未作業分の報告を行う」がチェックされていない？
		if( (resultKind.equals(UN_SHIPPING)) &&
			(!sparam.getSelectReportShippingData_Unworking()) )
		{
			// 未処理で、「未作業分の報告を行う」がチェックされいないので、報告を行いません。
			return false;
		}

		// 実績区分 = 作業途中で、「作業途中の報告を行う」がチェックされていない？
		if( (resultKind.equals(WORK_MIDDLE)) &&
			(!sparam.getSelectReportShippingData_InProgress()) )
		{
			// 一部完了で、「作業途中の報告を行う」がチェックされていないので、報告を行いません。
			return false;
		}
		return true;
	}

	/**
	 * 実績情報をVectorにセットします。<BR>
	 * 
	 * @param hostSendView 実績送信View情報
	 * @param resultQty 出荷実績数
	 * @param shortageCnt 欠品数
	 * @param hostSendViewVec 実績送信View情報(予定一意キー+賞味期限分)
	 * @param resultQtyVec 出荷実績数(予定一意キー+賞味期限分)
	 * @param shortageCntVec 欠品数(予定一意キー+賞味期限分)
	 */
	private void resultVectorSet(HostSendView hostSendView, int resultQty, int shortageCnt
											, Vector hostSendViewVec, Vector resultQtyVec, Vector shortageCntVec)
	{
		if( resultQty != 0 || shortageCnt != 0 || hostSendView.getStatusFlag().equals(SystemDefine.STATUS_FLAG_UNSTART) )
		{
			// 実績送信情報を記憶します。
			hostSendViewVec.addElement(hostSendView);
			// 入荷実績数
			resultQtyVec.addElement(new Integer(resultQty));
			// 欠品数
			shortageCntVec.addElement(new Integer(shortageCnt));
		}
	}

	/**
	 * 出荷実績の集約と出力を行います。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param param <CODE>Parameter</CODE>クラスを継承したクラス
	 * @param csvw CSV Writer オブジェクト
	 * @param hostSendViewVec 実績送信VIEW情報(予定一意キー+賞味期限分)
	 * @param resultQtyVec 出荷実績数(１集約キー分)
	 * @param shortageCntVec 欠品数(１集約キー分)
	 * @param resultKind 実績区分
	 * @param lastUpdate この最新更新日時以前のﾃﾞｰﾀの送信フラグを変更します。
	 * @return 報告データに出力した件数を返します。
	 * @throws IOException  I/Oエラーが発生した場合に通知されます。
	 * @throws CSVItemNotFoundException CSVファイルまたは環境情報に指定された項目が存在しない場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int shippingCsvWrite(Connection conn, SystemParameter sparam, DataReportCsvWriter csvw
												, Vector hostSendViewVec, Vector resultQtyVec, Vector shortageCntVec, String resultKind,  Date lastUpdate)
													throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		// 「作業途中の報告を行う」をチェックしなかった場合
		if(!sparam.getSelectReportShippingData_InProgress())
		{
			if (resultKind.equals(WORK_MIDDLE))
			{
				return 0;
			}
		}
		// 「未作業分の報告を行う」をチェックしなかった場合
		if(!sparam.getSelectReportShippingData_Unworking())
		{
			if (resultKind.equals(UN_SHIPPING))
			{
				return 0;
			}
		}

		// 実績送信View
		HostSendView[] hostSendViewAry = new HostSendView[hostSendViewVec.size()];
		hostSendViewVec.copyInto(hostSendViewAry);
		// 実績数
		Integer[] resultQtyAry = new Integer[resultQtyVec.size()];
		resultQtyVec.copyInto(resultQtyAry);
		// 欠品数
		Integer[] shortageCntAry = new Integer[shortageCntVec.size()];
		shortageCntVec.copyInto(shortageCntAry);

		int reportCount = 0;
		// 実績出力を行います。
		for( int i = 0 ; i < hostSendViewAry.length ; i++ )
		{
			// 作業途中分の未作業ﾃﾞｰﾀは報告しない。
			if (resultKind.equals(WORK_MIDDLE))
			{
				if (resultQtyAry[i].intValue() <= 0 && shortageCntAry[i].intValue() <= 0)
				{
					continue;
				}
			}
			// 報告データ出力
			CsvWriteLine(conn, csvw, sparam.getWorkDate(), hostSendViewAry[i]
							, resultQtyAry[i].intValue(), shortageCntAry[i].intValue(), resultKind, lastUpdate);
			reportCount++;
		}
		return(reportCount);
	}

	/**
	 * １行分の出荷検品実績出力を行います。<BR>
	 * 賞味期限は出荷実績情報から取得します。<BR>
	 * 出荷実績情報は集約キー(出荷予定日+作業日+荷主コード+出荷先コード+出荷伝票No+出荷伝票行)<BR>
	 * で集約された出荷データの完了データ(最終更新日時となる)にのみ賞味期限がセットされています。<BR>
	 * 出力後、出荷実績情報の報告フラグと最終更新日時の更新を行います。
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param csvw CSV Writer オブジェクト
	 * @param workDate 作業日
	 * @param hostSendView 実績送信VIEW情報
	 * @param resultQty 集約キーの作業実績数の集計値
	 * @param shortageCnt 欠品数
	 * @param resultKind 実績区分
	 * @param lastUpdate 最終更新日時
	 * @throws IOException  I/Oエラーが発生した場合に通知されます。
	 * @throws CSVItemNotFoundException CSVファイルまたは環境情報に指定された項目が存在しない場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void CsvWriteLine( Connection conn, DataReportCsvWriter csvw, String workDate, HostSendView hostSendView,
													 int resultQty, int shortageCnt, String resultKind, Date lastUpdate )
														throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		try
		{
			// 出荷検品実績データを項目にセットします。
			// 出荷予定日
			csvw.setValue("SHIPPING_DAY", hostSendView.getPlanDate());
			// 発注日
			csvw.setValue("ORDERING_DATE", hostSendView.getOrderingDate());
			// 荷主コード
			csvw.setValue("CONSIGNOR_CODE", hostSendView.getConsignorCode());
			// 荷主名称
			csvw.setValue("CONSIGNOR_NAME", hostSendView.getConsignorName());
			// 出荷先コード
			csvw.setValue("SUPPLIER_CODE", hostSendView.getSupplierCode());
			// 出荷先名
			csvw.setValue("SUPPLIER_NAME", hostSendView.getSupplierName1());
			// 伝票No
			csvw.setValue("TICKET_NO", hostSendView.getShippingTicketNo());
			// 行No
			csvw.setValue("LINE_NO", new Integer(hostSendView.getShippingLineNo()));
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
			csvw.setValue("ITEM_NAME", hostSendView.getItemName1());
			// 出荷予定数
			csvw.setValue("PLAN_QTY", new Integer(hostSendView.getHostPlanQty()));
			// TC区分
			csvw.setValue("SHIPPING_TYPE", hostSendView.getTcdcFlag());
			// 仕入先コード
			csvw.setValue("CUSTOMER_CODE", hostSendView.getCustomerCode());
			// 仕入先名
			csvw.setValue("CUSTOMER_NAME", hostSendView.getCustomerName1());
			// 入荷伝票番号
			csvw.setValue("INSTOCK_TICKET_NO", hostSendView.getInstockTicketNo());
			// 入荷伝票行
			csvw.setValue("INSTOCK_LINE_NO", new Integer(hostSendView.getInstockLineNo()));
			// 出荷実績数
			csvw.setValue("RESULT_QTY", new Integer(resultQty));

			// 実績区分が未出荷
			if (resultKind.equals(UN_SHIPPING))
			{
				// 出荷実績日には""をセットします。
				csvw.setValue("COMPLETE_DATE", "");
			}
			else
			{
				// 出荷実績日
				csvw.setValue("COMPLETE_DATE", workDate);
			}

			// 賞味期限
			if (resultQty != 0 || shortageCnt != 0)
			{
				csvw.setValue("USE_BY_DATE", hostSendView.getUseByDate());
			}
			else
			{
				csvw.setValue("USE_BY_DATE", "");
			}

			// 実績区分
			csvw.setValue("RESULT_FLAG", resultKind);

			// 出荷検品実績データをCSVに出力します。
			csvw.writeLine();

			// 実績区分が全数出荷完了、又は、欠品確定ならば、報告フラグと最新更新日時を更新します。
			if (resultKind.equals(SHIPPING_COMPLETE) || resultKind.equals(SHORTAGE) || resultKind.equals(WORK_MIDDLE))
			{
				/** 実績送信情報の報告フラグの更新を行います。*/
				HostSendHandler hhandle = new HostSendHandler(conn);
				HostSendSearchKey hsKey = new HostSendSearchKey();
				hsKey.setPlanUkey(hostSendView.getPlanUkey());
				hsKey.setWorkDate(workDate);
				hsKey.setLastUpdateDate(lastUpdate, "<=");
				if (hhandle.count(hsKey) > 0)
				{
					// 	実績送信情報 更新キー インスタンス
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

				// 作業情報の報告フラグの更新を行います。*/
				ShippingWorkingInformationHandler swhandle = new ShippingWorkingInformationHandler(conn); 
				swhandle.updateReportFlag( hostSendView.getPlanUkey(), workDate, pName ) ;
			}
		}
		catch(NotFoundException e)
		{
			throw (new ReadWriteException("6006002" + wDelim + "DNHOSTSEND"));
		}
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException("6006002" + wDelim + "DNHOSTSEND"));
		}

		return;
	}
}
//end of class