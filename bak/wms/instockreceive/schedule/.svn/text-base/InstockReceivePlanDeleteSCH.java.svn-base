package jp.co.daifuku.wms.instockreceive.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.instockreceive.report.InstockReceivePlanDeleteWriter;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * WEB入荷予定データ削除（一括）処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、入荷予定データ削除（一括）処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR> 
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド) <BR> 
 * <BR>
 * <DIR>
 *   入荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *   <BR>
 *   [検索条件] <BR>
 *   <DIR>
 *     状態フラグが未開始<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   荷主コード、荷主名称、選択ボタン条件(ON/OFF)、出荷予定日、出荷先コード、出荷先名称を返却します。 <BR>
 *   パラメータ内容より、出荷予定情報を検索し、対象情報を取得します。 <BR>
 *   <BR>
 *   [入力データ] <BR>
 *   <DIR>
 *     * 必須入力<BR>
 *     <BR>
 *     作業者コード       :WorkerCode    * <BR>
 *     パスワード         :PassWord      * <BR>
 *     荷主コード         :ConsignorCode * <BR>
 *     開始入荷予定日     :FromPlanDate <BR>
 *     終了入荷予定日     :ToPlanDate <BR>
 *     選択ＴＣ／ＤＣ区分 :SelectTcdcFlag * <BR>
 *   </DIR>
 *   <BR>
 *   [返却データ] <BR>
 *   <DIR>
 *     選択Box有効/無効フラグ:SelectBoxFlag <BR>
 *     荷主コード         :ConsignorCode <BR>
 *     荷主名称           :ConsignorName <BR>
 *     入荷予定日         :PlanDate <BR>
 *     ＴＣ／ＤＣ区分     :TcdcFlag <BR>
 *     ＴＣ／ＤＣ区分名称 :TcdcName <BR>
 *     仕入先コード       :SupplierCode <BR>
 *     仕入先名           :SupplierName <BR>
 *   </DIR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   2-1.作業者コードとパスワードが作業者マスターに定義されていること。(<CODE>check()</CODE>メソッド) <BR>
 *   <DIR>
 *     作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR>
 *   </DIR>
 *   2-2.日次処理中でないこと。 <BR>
 *   <DIR>
 *     WareNaviシステム定義情報の「日次更新中フラグ」を参照して、チェックを行う。 <BR>
 *   </DIR>
 *   2-3.入荷予定情報より、下記条件にて対象情報を取得する。 <BR>
 *   <DIR>
 *     注） Business側にて、ためうちエリアチェックボックスのDisable表示が可能となった場合<BR>
 *          下記コメントが明記されているロジックの対応が必要となります。 <BR>
 *          2004.08.27 Disable対応策 <BR>
 *   </DIR>
 *   <DIR>
 *     [検索条件] <BR> 
 *     <DIR>
 *       パラメータにて指定された条件 <BR>
 *       状態フラグが削除以外<BR>
 *     </DIR>
 *     <BR>
 *     [集約条件] <BR>
 *     <DIR>
 *       入荷予定日、ＴＣ／ＤＣ区分、仕入先コード <BR>
 *     </DIR>
 *   </DIR>
 *   <DIR>
 *     <BR>
 *     [選択ボタン条件の判定] <BR>
 *     <DIR>
 *       同一入荷予定日、ＴＣ／ＤＣ区分、仕入先コードに対して、"未開始"及び"削除"以外の状態が存在する情報に関して <BR>
 *       選択ボタンのOFFを通知します。 <BR>
 *     </DIR>
 *     <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3.削除ボタン押下処理(<CODE>startSCHgetParams()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、入荷予定データの削除処理を行います。 <BR>
 *   処理完了時、削除リストの発行有無に従い、入荷予定削除リストの発行処理を起動します。 <BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   処理完了後 荷主コード、荷主名称、選択ボタン条件(ON/OFF)、入荷予定日、ＴＣ／ＤＣ区分、ＴＣ／ＤＣ区分名称、仕入先コード、仕入先名称を返却します。 <BR>
 *   パラメータ内容より、入荷予定情報を検索し、対象情報を取得します。 <BR>
 *   <BR>
 *   [入力データ] <BR>
 *   <DIR>
 *     * 必須入力<BR>
 *     + どちらかが必須の入力項目 <BR>
 *     $ 検索に必要な項目 <BR>
 *     # 更新に必要な項目 <BR>
 *     <BR>
 *     作業者コード       :WorkerCode                  *# <BR>
 *     パスワード         :PassWord                    *# <BR>
 *     荷主コード         :ConsignorCode               *$# <BR>
 *     開始入荷予定日     :FromPlanDate                 $ <BR>
 *     終了入荷予定日     :ToPlanDate                   $ <BR>
 *     選択ＴＣ／ＤＣ区分 :SelectTcdcFlag              *$ <BR>
 *     リスト発行要求     :InstockReceiveDeleteListFlg *# <BR>
 *     選択BoxON/OFF      :SelectBoxCheck              *# <BR>
 *     入荷予定日         :PlanDate                    *# <BR>
 *     ＴＣ／ＤＣ区分     :TcdcFlag                    *# <BR>
 *     仕入先コード       :SupplierCode                *# <BR>
 *     仕入先名称         :SupplierName                *# <BR>
 *     ためうち行No       :RowNo                       * <BR>
 *   </DIR>
 *   <BR>
 *   [返却データ] <BR>
 *   <DIR>
 *     選択Box有効/無効フラグ :SelectBoxFlag <BR>
 *     荷主コード             :ConsignorCode <BR>
 *     荷主名称               :ConsignorName <BR>
 *     入荷予定日             :PlanDate <BR>
 *     ＴＣ／ＤＣ区分         :TcdcFlag <BR>
 *     ＴＣ／ＤＣ区分名称     :TcdcName <BR>
 *     仕入先コード           :SupplierCode <BR>
 *     仕入先名               :SupplierName <BR>
 *   </DIR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   3-1.作業者コードとパスワードが作業者マスターに定義されていること。(<CODE>check()</CODE>メソッド) <BR>
 *   <DIR>
 *     作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR>
 *   </DIR>
 *   3-2.日次処理中でないこと。 <BR>
 *   <DIR>
 *     WareNaviシステム定義情報の「日次更新中フラグ」を参照して、チェックを行う。 <BR>
 *   </DIR>
 *   3-3.入荷予定情報より、下記条件にて対象情報を取得する。 <BR>
 *   <DIR>
 *     [検索条件] <BR> 
 *     <DIR>
 *       選択ボタンがONの情報のみ対象とします。 <BR>
 *       パラメータの入荷予定日と一致 <BR>
 *       パラメータのＴＣ／ＤＣ区分と一致 <BR>
 *       パラメータの仕入先コードと一致 <BR>
 *       状態フラグが削除以外<BR>
 *       同一入荷予定日、ＴＣ／ＤＣ区分、仕入先コードに対して、"未開始"及び"削除"以外の状態が存在する情報に関して <BR>
 *       削除不可と判断し、エラーメッセージをセットします。 <BR>
 *     </DIR>
 *   </DIR>
 *   <BR>
 *   [更新処理] <BR>
 *   -入荷予定削除リスト用にシステム日時を取得する。 <BR>
 *   <BR>
 *   -入荷予定情報テーブル(DNINSTOCKPLAN)の更新処理 <BR>
 *   -作業情報テーブル(DNWORKINFO)の更新処理 <BR>
 *   -在庫情報(DMSTOCK)テーブルの更新処理 <BR>
 *   -次工程情報(DNNEXTPROC)の更新処理 <BR>
 *   <DIR>
 *     A) 3-3処理にて所得した、入荷予定情報の入荷予定一意キーにて、 <BR>
 *        InstockPlanOperatorのupdateInstockPlanメソッドを使用し、情報の更新を行う。 <BR>
 *        詳細は、InstockPlanOperatorのJavaDocを参照して下さい。 <BR>
 *   </DIR>
 *   <BR>
 *   [印刷処理] <BR>
 *   <BR>
 *   リスト発行要求がONである場合のみ処理を行う。
 *   削除処理を行った情報が１件以上存在する場合は、<BR>
 *   <CODE>InStockPlanDeleteWriter</CODE>クラスを使用して入荷予定削除リストの印刷処理を行います。<BR>
 *   <DIR>
 *     <BR>
 *     [入力データ] <BR>
 *     <DIR>
 *      * 必須入力<BR>
 *      <BR>
 *       -更新処理にて削除処理を行った情報のみセットを行います。 <BR>
 *       荷主コード     :ConsignorCode[]  * <BR>
 *       入荷予定日     :PlanDate[]       * <BR>
 *       ＴＣ／ＤＣ区分 :TcdcFlag[]       * <BR>
 *       仕入先コード   :SupplierCode[]   * <BR>
 *       更新日時       :LastUpdateDate[] * (更新処理にて所得したシステム日時) <BR>
 *     </DIR>
 *     <BR>
 *     [返却データ] <BR>
 *     <DIR>
 *       印刷結果* <BR>
 *     </DIR>
 *   </DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 本処理では、下記Methodは使用しません。 <BR>
 * 呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
 * <DIR>
 *   情報更新及びリスト処理起動メソッド：返却情報なし(<CODE>startSCH()</CODE>メソッド) <BR> 
 *   次画面へ移行時のチェック用メソッド(<CODE>nextCheck()</CODE>メソッド) <BR> 
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:22:45 $
 * @author  $Author: suresh $
 */
public class InstockReceivePlanDeleteSCH extends AbstractInstockReceiveSCH
{

	// Class variables -----------------------------------------------
	/**
	 * Class Name(Delete Receving Plan)
	 */
	public static String wProcessName = "InspectionPlanDeleteSCH";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/21 04:22:45 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public InstockReceivePlanDeleteSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。 <BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		// 該当する荷主コードがセットされます。
		InstockReceiveParameter wParam = new InstockReceiveParameter();

		// 出庫予定情報ハンドラ類のインスタンス生成
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();
		InstockPlanReportFinder instockFinder = new InstockPlanReportFinder(conn);
		InstockPlan[] wInstock = null;

		try
		{
			// 検索条件を設定する
			// データの検索
			// Status flag: Standby
			searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (instockFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する
				searchKey.KeyClear();
				// Status flag: Standby
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
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。 <BR>
	 * @param searchParam 表示データ取得条件を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。 <BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。 <BR>
	 * @return 検索結果を持つ<CODE>InstockReceiveParameter</CODE>インスタンスの配列。 <BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。 <BR>
	 *          入力条件にエラーが発生した場合はnullを返します。 <BR>
	 *          nullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey instockKey = new InstockPlanSearchKey();

		InstockReceiveParameter sparam = (InstockReceiveParameter) searchParam;

		// Check the Worker code and password
		if (!checkWorker(conn, sparam))
		{
			return new InstockReceiveParameter[0];
		}
		
		// 画面表示情報の取得を行います。
		// 入荷予定情報を下記条件にて取得します。
		// パラメータの荷主コードを一致。
		// パラメータの開始入荷予定日以上(入力有時のみ)			
		// パラメータの終了入荷予定日以下(入力有時のみ)			
		instockKey.KeyClear();
		// Consignor code
		if (!StringUtil.isBlank(sparam.getConsignorCode()))
		{
			instockKey.setConsignorCode(sparam.getConsignorCode());
		}
		// Planned start receiving Date
		if (!StringUtil.isBlank(sparam.getFromPlanDate()))
		{
			instockKey.setPlanDate(sparam.getFromPlanDate(), ">=");
		}
		// Planned end receiving Date
		if (!StringUtil.isBlank(sparam.getToPlanDate()))
		{
			instockKey.setPlanDate(sparam.getToPlanDate(), "<=");
		}
		// TD/DC flag
		if (!sparam.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_ALL))
		{
			instockKey.setTcdcFlag(sparam.getTcdcFlag());
		}
		// Status other than Standby
		instockKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
		// GROUP BY条件に荷主コード、荷主名称、入荷予定日、ＴＣ／ＤＣ区分、仕入先コード
		instockKey.setConsignorCodeGroup(1);
		instockKey.setPlanDateGroup(2);
		instockKey.setTcdcFlagGroup(3);
		instockKey.setSupplierCodeGroup(4);
		// ORDER By条件を指定する。
		instockKey.setConsignorCodeOrder(1, true);
		instockKey.setPlanDateOrder(2, true);
		instockKey.setTcdcFlagOrder(3, false);
		instockKey.setSupplierCodeOrder(4, true);
		// 取得情報をセットする。
		instockKey.setConsignorCodeCollect("");
		instockKey.setPlanDateCollect("");
		instockKey.setTcdcFlagCollect("");
		instockKey.setSupplierCodeCollect("");

		InstockPlan[] rInstockPlan = (InstockPlan[]) instockHandler.find(instockKey);

		int wNotDel_Count = 0;
		for (int lc = 0; lc < rInstockPlan.length; lc++)
		{
			InstockPlanSearchKey ssKey = new InstockPlanSearchKey();
			ssKey.KeyClear();
			ssKey.setConsignorCode(rInstockPlan[lc].getConsignorCode());
			ssKey.setPlanDate(rInstockPlan[lc].getPlanDate());
			ssKey.setTcdcFlag(rInstockPlan[lc].getTcdcFlag());
			ssKey.setSupplierCode(rInstockPlan[lc].getSupplierCode());
			// other than Standby or Delete
			ssKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART, "!=");
			ssKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");

			if (instockHandler.count(ssKey) > 0)
			{
				wNotDel_Count++;
			}
		}

		// 取得情報の件数チェック			
		if (rInstockPlan == null || rInstockPlan.length <= 0)
		{
			// No target data was found.
			wMessage = "6003018";

			return new InstockReceiveParameter[0];
		}

		// 表示Max件数との比較を行う。			
		if (rInstockPlan.length - wNotDel_Count >= WmsParam.MAX_NUMBER_OF_DISP)
		{
			String delim = MessageResource.DELIM;
			// {0} data match. As the result data exceeds {1}, please narrow your search.
			String msg =
				"6003012"
					+ delim
					+ WmsFormatter.getNumFormat(rInstockPlan.length)
					+ delim
					+ MAX_NUMBER_OF_DISP_DISP;
			wMessage = msg;
			return new InstockReceiveParameter[0];
		}

		Vector vec = new Vector();

		boolean wContinueFlag = false;

		InstockPlanReportFinder finder = new InstockPlanReportFinder(conn);
		// 取得情報分処理をループする。
		for (int lc = 0; lc < rInstockPlan.length; lc++)
		{
			InstockReceiveParameter wparam = new InstockReceiveParameter();

			// 選択チェックBox(true:enable false:disnable)
			InstockPlanSearchKey ssKey = new InstockPlanSearchKey();
			ssKey.KeyClear();
			ssKey.setConsignorCode(rInstockPlan[lc].getConsignorCode());
			ssKey.setPlanDate(rInstockPlan[lc].getPlanDate());
			ssKey.setTcdcFlag(rInstockPlan[lc].getTcdcFlag());
			ssKey.setSupplierCode(rInstockPlan[lc].getSupplierCode());
			// other than Standby or Delete
			ssKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART, "!=");
			ssKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");

			if (instockHandler.count(ssKey) > 0)
			{
				wContinueFlag = true;
				continue;
			}
			else
			{
				wparam.setSelectBoxFlag(true);
			}

			// Consignor code
			wparam.setConsignorCode(rInstockPlan[lc].getConsignorCode());
			// Planned receiving date
			wparam.setPlanDate(rInstockPlan[lc].getPlanDate());
			// TC/DC flag
			wparam.setTcdcFlag(rInstockPlan[lc].getTcdcFlag());
			// TC/DC name
			wparam.setTcdcName(DisplayUtil.getTcDcValue(rInstockPlan[lc].getTcdcFlag()));
			// Supplier code
			wparam.setSupplierCode(rInstockPlan[lc].getSupplierCode());

			InstockPlanSearchKey nameGetKey = new InstockPlanSearchKey();
			nameGetKey.KeyClear();
			nameGetKey.setConsignorCode(rInstockPlan[lc].getConsignorCode());
			nameGetKey.setPlanDate(rInstockPlan[lc].getPlanDate());
			nameGetKey.setSupplierCode(rInstockPlan[lc].getSupplierCode());
			nameGetKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			nameGetKey.setLastUpdateDateOrder(1, false);
			if (finder.search(nameGetKey) > 0)
			{
				InstockPlan[] name = (InstockPlan[]) finder.getEntities(1);
				// Consignor name
				wparam.setConsignorName(name[0].getConsignorName());
				// Supplier name
				wparam.setSupplierName(name[0].getSupplierName1());
			}

			vec.addElement(wparam);
		}

		// 返却用Parameterエリア
		InstockReceiveParameter[] rparam = new InstockReceiveParameter[vec.size()];
		vec.copyInto(rparam);

		if (rparam != null && rparam.length > 0)
		{
			// 6001013 = Data is shown.
			wMessage = "6001013";
		}
		else if (wContinueFlag)
		{
			// 6023373 = Status flag other than Standby exists. Cannot display.
			wMessage = "6023373";
			return new InstockReceiveParameter[0];
		}

		return rparam;
	}

	/** 
	 * 画面から入力された内容をパラメータとして受け取り、入荷予定情報の削除処理及びリスト処理の起動を行います。 <BR>
	 * 本処理が正常完了後、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュールが正常終了した場合は、最新の入荷予定情報<CODE>InstockReceiveParameter</CODE>インスタンスの配列。<BR>
	 *          失敗した場合はnullを返します。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。<BR>
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		// Check the Worker code and password
		InstockReceiveParameter workparam = (InstockReceiveParameter) startParams[0];
		if (!checkWorker(conn, workparam))
		{
			return null;
		}
		// Check the Daily Update Processing.
		if (isDailyUpdate(conn))
		{
			return null;
		}
		// Check Extraction Processing
		if (isLoadingData(conn))
		{
			return null;
		}
		
		// For deciding DB commit and rollback
		boolean registFlag = false;
		// This flag determines whether "Processing Extraction" flag is updated in its own class.
		boolean updateLoadDataFlag = false;
		
		Vector vecConsignorCode = new Vector();
		Vector vecPlanDate = new Vector();
		Vector vecTcdcFlag = new Vector();
		Vector vecSupplierCode = new Vector();
		Vector vecLastUpdateDate = new Vector();

		try
		{
			// Update the extraction flag: "Processing Extract"
			if (!updateLoadDataFlag(conn, true))
			{
				return null;
			}
			doCommit(conn, wProcessName);
			updateLoadDataFlag = true;
			
			// Define HANDLER for Receiving Plan information
			InstockPlanHandler instockPlanHandler = new InstockPlanHandler(conn);
			InstockPlanSearchKey instockPlanSearchKey = new InstockPlanSearchKey();
			InstockPlanSearchKey ssKey = new InstockPlanSearchKey();
	
			// リスト発行の有無
			int listDataCnt = 0;
			// For printing ticket
			Date wstartDate = new Date();
	
			// Initialize Process Counter
			int updateConuter = 0;
	
			// Count of records that have been selected
			for (int lc = 0; lc < startParams.length; lc++)
			{
				InstockReceiveParameter rparam = (InstockReceiveParameter) startParams[lc];
	
				// 選択BoxがＯＮの情報のみ処理を行う。
				if (rparam.getSelectBoxCheck() == InstockReceiveParameter.SELECT_BOX_OFF)
				{
					continue;
				}
				
				// 未開始又は削除以外の状態が存在する場合、エラー復帰を行う。
				ssKey.KeyClear();
				ssKey.setConsignorCode(rparam.getConsignorCode());
				ssKey.setPlanDate(rparam.getPlanDate());
				ssKey.setTcdcFlag(rparam.getTcdcFlagL());
				ssKey.setSupplierCode(rparam.getSupplierCode());
				// other than Standby or Delete
				ssKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART, "!=");
				ssKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
	
				if (instockPlanHandler.count(ssKey) > 0)
				{
					String delim = MessageResource.DELIM;
					// No.{0} Unable to process this data. It has been updated via other work station.
					String msg = "6023209" + delim + rparam.getRowNo();
					wMessage = msg;
	
					return null;
				}
	
				instockPlanSearchKey.KeyClear();
				instockPlanSearchKey.setConsignorCode(rparam.getConsignorCode());
				instockPlanSearchKey.setPlanDate(rparam.getPlanDate());
				instockPlanSearchKey.setTcdcFlag(rparam.getTcdcFlagL());
				instockPlanSearchKey.setSupplierCode(rparam.getSupplierCode());
				instockPlanSearchKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
	
				// 行ロック指定にて、対象情報を取得する。
				InstockPlan[] rInstockPlan =
					(InstockPlan[]) instockPlanHandler.findForUpdate(instockPlanSearchKey);
	
				// 取得情報の件数チェック
				if (rInstockPlan != null && rInstockPlan.length > 0)
				{
					if (workparam.getInstockReceiveDeleteListFlg())
					{
						// リスト発行ありの場合、リスト発行用のParameterにセットする。
						// リスト出力Parameter領域		
						vecConsignorCode.addElement(rparam.getConsignorCode());
						vecPlanDate.addElement(rparam.getPlanDate());
						vecTcdcFlag.addElement(rparam.getTcdcFlagL());
						vecSupplierCode.addElement(rparam.getSupplierCode());
						vecLastUpdateDate.addElement(wstartDate);
						// リスト出力を記憶。					
						listDataCnt++;
	
					}
				}
	
				// 入荷予定情報分、処理を行う。
				for (int slc = 0; slc < rInstockPlan.length; slc++)
				{
					// 入荷予定情報更新処理のインスタンスを形成する。
					InstockPlanOperator uObj = new InstockPlanOperator(conn);
	
					// 入荷予定情報削除処理メソッドを起動する。
					uObj.updateInstockPlan(rInstockPlan[slc].getInstockPlanUkey(), wProcessName);
	
					// 処理データカウンタのインクリメント
					updateConuter++;
				} // 出荷予定情報分の更新 For文の終わり
	
			} // Parameterにて通知された情報分の更新 For文の終わり
	
			// スケジュール成功
			InstockReceiveParameter[] viewParam =
				(InstockReceiveParameter[]) this.query(conn, workparam);
	
			// リスト発行要求あり時、出荷予定削除リスト(InStockDeleteWriter)を起動する。
			// リスト出力用Parameterエリア
			if (listDataCnt > 0)
			{
				// 出荷予定削除リスト
				InstockReceivePlanDeleteWriter sdWriter = new InstockReceivePlanDeleteWriter(conn);
	
				// リスト出力Parameter領域		
				String[] wlConsignorCode = new String[vecConsignorCode.size()];
				vecConsignorCode.copyInto(wlConsignorCode);
				String[] wlPlanDate = new String[vecPlanDate.size()];
				vecPlanDate.copyInto(wlPlanDate);
				String[] wlTcdcFlag = new String[vecTcdcFlag.size()];
				vecTcdcFlag.copyInto(wlTcdcFlag);
				String[] wlSupplierCode = new String[vecSupplierCode.size()];
				vecSupplierCode.copyInto(wlSupplierCode);
				Date[] wlLastUpdateDate = new Date[vecLastUpdateDate.size()];
				vecLastUpdateDate.copyInto(wlLastUpdateDate);
	
				sdWriter.setConsignorCode(wlConsignorCode);
				sdWriter.setPlanDate(wlPlanDate);
				sdWriter.setTcdcFlag(wlTcdcFlag);
				sdWriter.setSupplierCode(wlSupplierCode);
				sdWriter.setLastUpdateDate(wlLastUpdateDate);
	
				boolean wlStatus = sdWriter.startPrint();
				if (wlStatus == true)
				{
					// Printing has been normally completed after deleted.
					wMessage = "6021013";
				}
				else
				{
					// Failed to print it after deleted. See log.
					wMessage = "6007043";
				}
			}
			else
			{
				// 一件も処理しなかった場合は、削除対象データが無かった事を通知する。
				if (updateConuter > 0)
				{
					// 6001005 = Deleted.
					wMessage = "6001005";
				}
				else
				{
					// No delete target data found.
					wMessage = "6003014";
				}
			}
			registFlag = true;
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
			// If failed to delete, Roll-back the transaction.
			if (!registFlag)
			{
				doRollBack(conn, wProcessName);
			}
			
			// If "Processing Extraction" flag was updated in its own class,
			// change the Processing Extract flag to 0: Stopping.
			if( updateLoadDataFlag )
			{
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
			
		}

	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	
}
// end of class
