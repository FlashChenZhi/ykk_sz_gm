package jp.co.daifuku.wms.sorting.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.sorting.report.SortingPlanDeleteWriter;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * WEB仕分予定データ削除（一括）処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、仕分予定データ削除（一括）処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR> 
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド) <BR> 
 * <BR>
 * <DIR>
 *   仕分予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
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
 *   荷主コード、荷主名称、選択ボタン条件(ON/OFF)、仕分予定日を返却します。 <BR>
 *   パラメータ内容より、仕分予定情報を検索し、対象情報を取得します。 <BR>
 *   <BR>
 *   [パラメータ] <BR>
 *   <DIR>
 *     *必須入力<BR>
 *     <BR>
 *     作業者コード :WorkerCode    * <BR>
 *     パスワード   :PassWord      * <BR>
 *     荷主コード   :ConsignorCode * <BR>
 *     開始予定日   :FromPlanDate <BR>
 *     終了予定日   :ToPlanDate <BR>
 *   </DIR>
 *   <BR>
 *   [返却データ] <BR>
 *   <DIR>
 *     選択Box有効/無効フラグ :SelectBoxFlag <BR>
 *     荷主コード             :ConsignorCode <BR>
 *     荷主名称               :ConsignorName <BR>
 *     仕分予定日             :PlanDate <BR>
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
 *   2-3.仕分予定情報より、下記条件にて対象情報を取得する。 <BR>
 *   <DIR>
 *     注） Business側にて、ためうちエリアチェックボックスのDisable表示が可能となった場合　<BR>
 *          下記コメントが明記されているロジックの対応が必要となります。 <BR>
 *          2004.08.27 Disable対応策 <BR>
 *   </DIR>
 * 　<DIR>
 *     [検索条件] <BR> 
 *     <DIR>
 *       パラメータにて指定された条件 <BR>
 *       状態フラグが削除以外<BR>
 *     </DIR>
 *     <BR>
 *     [集約条件] <BR>
 *     <DIR>
 *       仕分予定日 <BR>
 *     </DIR>
 *   </DIR>
 *   <DIR>
 *     <BR>
 *     [選択ボタン条件の判定] <BR>
 *     <DIR>
 *       同一仕分予定日に対して、"未開始"及び"削除"以外の状態が存在する情報に関して <BR>
 *       選択ボタンのOFFを通知します。 <BR>
 *     </DIR>
 *     <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3.削除ボタン押下処理(<CODE>startSCHgetParams()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、仕分予定データの削除処理を行います。 <BR>
 *   処理完了時、削除リストの発行有無に従い、仕分予定削除リストの発行処理を起動します。 <BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   処理完了後　荷主コード、荷主名称、選択ボタン条件(ON/OFF)、仕分予定日を返却します。 <BR>
 *   パラメータ内容より、仕分予定情報を検索し、対象情報を取得します。 <BR>
 *   <BR>
 *   [パラメータ] <BR>
 *   <DIR>
 *     * 必須入力<BR>
 *     + どちらかが必須の入力項目 <BR>
 *     $ 検索に必要な項目 <BR>
 *     # 更新に必要な項目 <BR>
 *     <BR>
 *     作業者コード    :WorkerCode           *# <BR>
 *     パスワード      :PassWord             * <BR>
 *     荷主コード      :ConsignorCode        *$# <BR>
 *     開始予定日      :FromPlanDate         $ <BR>
 *     終了予定日      :ToPlanDate           $ <BR>
 *     リスト発行要求  :SortingDeleteListFlg *# <BR>
 *     選択BoxON/OFF  :SelectBoxCheck       *# <BR>
 *     仕分予定日      :PlanDate             *#<BR>
 *     ためうち行No    :RowNo                *<BR>
 *   </DIR>
 *   <BR>
 *   [返却データ] <BR>
 *   <DIR>
 *     選択Box有効/無効フラグ :SelectBoxFlag <BR>
 *     荷主コード             :ConsignorCode <BR>
 *     荷主名称               :ConsignorName <BR>
 *     仕分予定日             :PlanDate <BR>
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
 *   3-3.仕分予定情報より、下記条件にて対象情報を取得する。 <BR>
 * 　<DIR>
 *     [検索条件] <BR> 
 *     <DIR>
 *       選択ボタンがONの情報のみ対象とします。 <BR>
 *       パラメータの仕分予定日と一致 <BR>
 *       状態フラグが削除以外<BR>
 *       同一仕分予定日に対して、"未開始"及び"削除"以外の状態が存在する情報に関して <BR>
 *       削除不可と判断し、エラーメッセージをセットします。 <BR>
 *     </DIR>
 *   </DIR>
 *   <BR>
 *   [更新処理] <BR>
 *   -仕分予定削除リスト用にシステム日時を取得する。 <BR>
 *   <BR>
 *   -仕分予定情報テーブル(DNSORTINGPLAN)の更新処理 <BR>
 *   <DIR>
 *     A) 3-3処理にて取得した、仕分予定情報の状態フラグを削除状態に更新する。 <BR>
 *   </DIR>
 *   <BR>
 *   -作業情報テーブル(DNWORKINFO)の更新処理 <BR>
 *   <DIR>
 *     A) 3-3処理にて所得した、仕分予定情報の仕分予定一意キーが一致する <BR>
 *        作業情報の状態フラグを削除に更新する。 <BR>
 *   </DIR>
 *   <BR>
 *   -在庫情報(DMSTOCK)テーブルの更新処理 <BR>
 *   <DIR>
 *     A) 作業情報のSTOCK_IDが一致する　在庫情報を下記内容にて更新する。 <BR>
 *          在庫数量から、作業情報の予定数分　減算する。 <BR>
 *          引当数から、作業情報の予定数分　減算する。 <BR>
 *   </DIR>
 *   WARNING 上記処理の置換え予定あり <BR>
 *   -仕分予定情報テーブル(DNSORINGPLAN)の更新処理 <BR>
 *   -作業情報テーブル(DNWORKINFO)の更新処理 <BR>
 *   -在庫情報(DMSTOCK)テーブルの更新処理 <BR>
 *   <DIR>
 *     A) 3-3処理にて所得した、入荷予定情報の入荷予定一意キーにて、 <BR>
 *        SortingPlanOperatorのupdateSortingPlanメソッドを使用し、情報の更新を行う。 <BR>
 *        詳細は、SortingPlanOperatorのJavaDocを参照して下さい。 <BR>
 *   </DIR>
 *   <BR>
 *   [印刷処理] <BR>
 *   <BR>
 *   リスト発行要求がONである場合のみ　処理を行う。
 *   削除処理を行った情報が１件以上存在する場合は、<BR>
 *   <CODE>SortingWorkInfoDeleteWriter</CODE>クラスを使用して仕分予定削除リストの印刷処理を行います。<BR>
 *   <DIR>
 *     <BR>
 *     [パラメータ] *必須入力<BR>
 *     <DIR>
 *       -更新処理にて削除処理を行った情報のみ　セットを行います。 <BR>
 *       荷主コード :ConsignorCode[]  * <BR>
 *       仕分予定日 :PlanDate[]       * <BR>
 *       更新日時   :LastUpdateDate[] * (更新処理にて所得したシステム日時) <BR>
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
 * <TR><TD>2004/11/01</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:23:00 $
 * @author  $Author: suresh $
 */
public class SortingPlanDeleteSCH extends AbstractSortingSCH
{

	// Class variables -----------------------------------------------
	/**
	 * Class Name(Delete Sorting Plan)
	 */
	public static String wProcessName = "SortingPlanDeleteSCH";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/21 04:23:00 $");
	}
	
	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public SortingPlanDeleteSCH()
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
	 * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		SortingPlanHandler sortPlanHandler = new SortingPlanHandler(conn);
		SortingPlanSearchKey sortPlanSearchKey = new SortingPlanSearchKey();
		SortingParameter param = new SortingParameter();
		
		try
		{
			sortPlanSearchKey.KeyClear();
			// Status flag: Standby
			sortPlanSearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);
			// GROUP BY条件に荷主コード
			sortPlanSearchKey.setConsignorCodeGroup(1);
			sortPlanSearchKey.setConsignorCodeCollect("");
			
			if(sortPlanHandler.count(sortPlanSearchKey) == 1)
			{
				// 荷主コードを検索する
				SortingPlan sortPlan = (SortingPlan)sortPlanHandler.findPrimary(sortPlanSearchKey);

				// 検索結果を返却値にセット
				param.setConsignorCode(sortPlan.getConsignorCode());
			}

		}
		catch(NoPrimaryException pe)
		{
			return param;
		}
		return param;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。 <BR>
	 * @param locale 地域コード、画面表示用にローカライズした値を取得するために使用します。 <BR>
	 * @param searchParam 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。 <BR>
	 *         <CODE>SortingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。 <BR>
	 * @return 検索結果を持つ<CODE>SortingParameter</CODE>インスタンスの配列。 <BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。 <BR>
	 *          入力条件にエラーが発生した場合はnullを返します。 <BR>
	 *          nullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		SortingPlanHandler sortPlanHandler = new SortingPlanHandler(conn);
		SortingPlanSearchKey sortPlanSearchKey = new SortingPlanSearchKey();

		SortingParameter sparam = (SortingParameter)searchParam;

		// Check the Worker code and password
		if (!checkWorker(conn, sparam))
		{
			return null;
		}
		
		// 画面表示情報の取得を行います。
		// 仕分予定情報を下記条件にて取得します。
		// パラメータの荷主コードを一致。
		// パラメータの開始予定日以上(入力有時のみ)			
		// パラメータの終了予定日以上(入力有時のみ)			

		sortPlanSearchKey.KeyClear();
		// Consignor code
		if (!StringUtil.isBlank(sparam.getConsignorCode()))
		{
			sortPlanSearchKey.setConsignorCode(sparam.getConsignorCode());
		}

		// Planned start sorting date
		if (!StringUtil.isBlank(sparam.getFromPlanDate()))
		{
			sortPlanSearchKey.setPlanDate(sparam.getFromPlanDate(), ">=");
		}

		// Planned end sorting date
		if (!StringUtil.isBlank(sparam.getToPlanDate()))
		{
			sortPlanSearchKey.setPlanDate(sparam.getToPlanDate(), "<=");
		}
		
		// Status: Standby
		sortPlanSearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);


		// GROUP BY条件に荷主コード、荷主名称、予定日
		sortPlanSearchKey.setConsignorCodeGroup(1);
		sortPlanSearchKey.setPlanDateGroup(2);

		// ORDER By条件を指定する。
		sortPlanSearchKey.setConsignorCodeOrder(1, true);
		sortPlanSearchKey.setPlanDateOrder(2, true);
		
		// 取得情報をセットする。
		// Consignor code
		sortPlanSearchKey.setConsignorCodeCollect("");
		// Planned sorting date
		sortPlanSearchKey.setPlanDateCollect("");
		
		SortingPlan[] rSortingPlan = (SortingPlan[])sortPlanHandler.find(sortPlanSearchKey);

		// Modefication Business側にて、Disable表示が可能となった場合
		// 下記処理をコメントにして下さい。(又は行削除)
		int wNotDel_Count = 0 ;				
		for (int lc = 0; lc < rSortingPlan.length; lc++)
		{
			SortingPlanSearchKey ssKey = new SortingPlanSearchKey() ;
			ssKey.KeyClear();
			ssKey.setConsignorCode(rSortingPlan[lc].getConsignorCode());
			ssKey.setPlanDate(rSortingPlan[lc].getPlanDate());
			// other than Standby or Delete
			ssKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART, "!=");
			ssKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
	
			if (sortPlanHandler.count(ssKey) > 0)
			{
				wNotDel_Count++;
			}
		}
		// ここまで。

		// 取得情報の件数チェック
		// 表示Max件数との比較を行う。
		// Modefication Business側にて、Disable表示が可能となった場合
		// 下記処理をコメントにして下さい。(又は行削除)				
		if (rSortingPlan == null || rSortingPlan.length <= 0)
		{
			// 6003018=No target data was found.
			wMessage = "6003018";

			return new SortingParameter[0];			
		}

		// 表示Max件数との比較を行う。
		// Modefication Business側にて、Disable表示が可能となった場合
		// 下記処理のコメントを解除して下さい。				

		// Modefication Business側にて、Disable表示が可能となった場合
		// 下記処理をコメントにして下さい。(又は行削除)			
		if (rSortingPlan.length - wNotDel_Count > WmsParam.MAX_NUMBER_OF_DISP)
		// ここまで。
		{
			String delim = MessageResource.DELIM;
			// 6003012={0} data match. As the result data exceeds {1}, please narrow your search.
			String msg = "6003012" + delim + WmsFormatter.getNumFormat(rSortingPlan.length) + delim + MAX_NUMBER_OF_DISP_DISP;
			wMessage = msg;
			return null;			
		}
		
		Vector	vec = new Vector();
		
		boolean wContinueFlag = false;
				
		SortingPlanReportFinder finder = new SortingPlanReportFinder(conn);
		// 取得情報分処理をループする。
		for (int lc = 0; lc < rSortingPlan.length; lc++)
		{
			SortingParameter wparam = new SortingParameter();

			// 選択チェックBox(true:enable false:disnable)
			SortingPlanSearchKey ssKey = new SortingPlanSearchKey() ;
			ssKey.KeyClear();
			ssKey.setConsignorCode(rSortingPlan[lc].getConsignorCode());
			ssKey.setPlanDate(rSortingPlan[lc].getPlanDate());
			// other than Standby or Delete
			ssKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART, "!=");
			ssKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
			
			if (sortPlanHandler.count(ssKey) > 0)
			{
				wContinueFlag = true;				
				continue ;
			} 
			else 
			{
				wparam.setSelectBoxFlag(true);
			}

			// Consignor code
			wparam.setConsignorCode(rSortingPlan[lc].getConsignorCode());
			// Planned sorting date
			wparam.setPlanDate(rSortingPlan[lc].getPlanDate());

			SortingPlanSearchKey nameGetKey = new SortingPlanSearchKey() ;
			nameGetKey.KeyClear();
			nameGetKey.setConsignorCode(rSortingPlan[lc].getConsignorCode());
			nameGetKey.setPlanDate(rSortingPlan[lc].getPlanDate());
			nameGetKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
			nameGetKey.setLastUpdateDateOrder(1, false);
			if (finder.search(nameGetKey) > 0)
			{
				SortingPlan[] nameShipplan = (SortingPlan[]) finder.getEntities(1);
				// Consignor name
				wparam.setConsignorName(nameShipplan[0].getConsignorName());
			}
			 
			vec.addElement(wparam);
		}

		// 返却用Parameterエリア
		SortingParameter[] rparam = new SortingParameter[vec.size()];
		vec.copyInto(rparam);

		if (rparam != null && rparam.length > 0)
		{
			// 6001013=Data is shown.
			wMessage = "6001013";
		}
		else if (wContinueFlag)
		{
			// 6023373=Status flag other than Standby exists. Cannot display.
			wMessage = "6023373";
		}

		return rparam;
	}

	/** 
	 * 画面から入力された内容をパラメータとして受け取り、必要情報の更新処理及びリスト処理の起動を行います。 <BR>
	 * 本処理が正常完了後、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * 詳しい動作はクラス説明の項を参照してください。 <BR>
	 * 又、本処理が使用されない場合に、呼び出された時は、<CODE>ScheduleException</CODE>をスローします。 <BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// Check the Worker code and password
		SortingParameter workparam = (SortingParameter)startParams[0];
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

		// DBのcommit,rollbackの判断用
		boolean registFlag = false;
		// This flag determines whether "Processing Extraction" flag is updated in its own class.
		boolean updateLoadDataFlag = false;

		try
		{
			// Update the extraction flag: "Processing Extract"
			if (!updateLoadDataFlag(conn, true))
			{
				return null;
			}
			doCommit(conn,wProcessName);
			updateLoadDataFlag = true;
			
			// 仕分予定情報のHANDLER定義
			SortingPlanHandler sortPlanHandler = new SortingPlanHandler(conn);
			SortingPlanSearchKey sortPlanSearchKey = new SortingPlanSearchKey();
			SortingPlanSearchKey ssKey = new SortingPlanSearchKey();
			SortingPlanAlterKey sortPlanAlterKey = new SortingPlanAlterKey();
			
			// 作業情報のHANDLER定義
			WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey() ;
			WorkingInformationAlterKey workInfoAlterKey = new WorkingInformationAlterKey() ;
	
			// 在庫情報のHANDLER定義
			StockHandler stockHandler = new StockHandler(conn);
			StockSearchKey stockSearchKey = new StockSearchKey();
			StockAlterKey stockAlterKey = new StockAlterKey();
	
			// 次作業情報のHANDLER定義
			NextProcessInfoHandler nextHandler = new NextProcessInfoHandler(conn);
			NextProcessInfoAlterKey nextAlterKey = new NextProcessInfoAlterKey();
			NextProcessInfoSearchKey nextSearchKey = new NextProcessInfoSearchKey();
			
			Vector vecConsignorCode = new Vector();
			Vector vecPlanDate = new Vector();
			Vector vecLastUpdateDate = new Vector();
					
			// リスト発行の有無
			int wListFlg = 0;
			// For printing ticket
			Date wstartDate = new Date();
			
			// Initialize Process Counter
			int updateConuter = 0;
	
			// パラメータ入力件数分　処理を行う。
			for (int lc = 0; lc < startParams.length; lc++)
			{
				SortingParameter rparam = (SortingParameter)startParams[lc];
	
				// 選択BoxがＯＮの情報のみ　処理を行う。
				if (rparam.getSelectBoxCheck() == SortingParameter.SELECT_BOX_OFF)	continue;
				// 未開始又は削除以外の状態が存在する場合、エラー復帰を行う。
				ssKey.KeyClear();
				ssKey.setConsignorCode(rparam.getConsignorCode());
				ssKey.setPlanDate(rparam.getPlanDate());
				// other than Standby or Delete
				ssKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART, "!=");
				ssKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
				
				if (sortPlanHandler.count(ssKey) > 0)
				{
					String delim = MessageResource.DELIM;
					// 6023209=No.{0} The data has been updated via other terminal.
					String msg = "6023209" + delim + rparam.getRowNo();
					wMessage = msg;
					
					return null;
				}
	
				sortPlanSearchKey.KeyClear();
				sortPlanSearchKey.setConsignorCode(rparam.getConsignorCode());
				sortPlanSearchKey.setPlanDate(rparam.getPlanDate());
				sortPlanSearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);
			
				// 行ロック指定にて、対象情報を取得する。
				SortingPlan[] rSortingPlan = (SortingPlan[])sortPlanHandler.findForUpdate(sortPlanSearchKey);
	
				// 取得情報の件数チェック
				if (rSortingPlan != null && rSortingPlan.length > 0)
				{
					if (workparam.getSortingDeleteListFlg() == true)
					{
						// リスト発行ありの場合、リスト発行用のParameterにセットする。
						// リスト出力Parameter領域		
						vecConsignorCode.addElement(rparam.getConsignorCode());
						vecPlanDate.addElement(rparam.getPlanDate());
						vecLastUpdateDate.addElement(wstartDate);
						
						wListFlg++;			// リスト出力を記憶。
					}
				}
				
				// 仕分予定情報分、処理を行う。
				for (int slc = 0; slc < rSortingPlan.length; slc++)
				{
					nextSearchKey.KeyClear();
					
					nextSearchKey.setPlanUkey(rSortingPlan[slc].getSortingPlanUkey(),"=","","","OR");
					nextSearchKey.setSortingPlanUkey(rSortingPlan[slc].getSortingPlanUkey(),"=","","","AND") ;
				    
					NextProcessInfo[] nextProcInfo = (NextProcessInfo[])nextHandler.findForUpdate(nextSearchKey);
					if((nextProcInfo != null) && (nextProcInfo.length > 0))
					{
						try
						{
							for (int i = 0; i < nextProcInfo.length; i++)
							{
								//clear the alterkeyキーをクリアする。
								nextAlterKey.KeyClear();
								//WHERE CLAUSE
								//自作業予定一意キー
								nextAlterKey.setPlanUkey(rSortingPlan[slc].getSortingPlanUkey(),"=","(","","OR");
								nextAlterKey.setSortingPlanUkey(rSortingPlan[slc].getSortingPlanUkey(),"=","",")","AND");
	
								// ダミーKEYを設定
								if (nextProcInfo[i].getWorkKind().equals(NextProcessInfo.JOB_TYPE_INSTOCK))
								{
									// 入荷→仕分→出荷の削除処理
									// 仕分予定一意キー
									nextAlterKey.setWorkKind(NextProcessInfo.JOB_TYPE_INSTOCK);
									nextAlterKey.updateSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
								}
								else if (nextProcInfo[i].getWorkKind().equals(NextProcessInfo.JOB_TYPE_SORTING))
								{
									// 仕分→出荷の削除処理
									// 予定一意キー
									nextAlterKey.setWorkKind(NextProcessInfo.JOB_TYPE_SORTING);
									nextAlterKey.updatePlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
								}
								// 作業実績数
								nextAlterKey.updateResultQty(0);
								// 作業欠品数
								nextAlterKey.updateShortageQty(0);
								// 最終更新処理名
								nextAlterKey.updateLastUpdatePname(wProcessName);
					            
								// 次作業情報を更新する。
								nextHandler.modify(nextAlterKey);
							}
						}
						catch(NotFoundException ne)
						{
							throw new ReadWriteException("6006039" + wDelim + "DNNEXTPROC");
						}
						catch(InvalidDefineException ie)
						{
							throw new ReadWriteException("6006039" + wDelim + "DNNEXTPROC");
						}
					}
				    
					// 仕分予定情報より、対象となる作業情報を取得する。
					workInfoSearchKey.KeyClear();
					// 仕分予定一意キーの一致する情報を取得する。
					workInfoSearchKey.setPlanUkey(rSortingPlan[slc].getSortingPlanUkey());
					
					// 行ロック指定にて、対象情報を取得する。
					WorkingInformation[] rworkInfo = (WorkingInformation[])workInfoHandler.findForUpdate(workInfoSearchKey);
					
					// 作業情報に対象情報が存在している場合のみ、処理を行う。
					if (rworkInfo != null && rworkInfo.length > 0)
					{
						try
						{
							// 作業情報の状態フラグが削除に更新する。
							workInfoAlterKey.KeyClear();
							// 仕分予定一意キーの一致する情報を対象とする。
							workInfoAlterKey.setPlanUkey(rSortingPlan[slc].getSortingPlanUkey());
							// 状態フラグを削除に更新する。
							workInfoAlterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_DELETE);
							// 最終更新処理名を更新する。
							workInfoAlterKey.updateLastUpdatePname(wProcessName);
							
							workInfoHandler.modify(workInfoAlterKey);
						}
						catch (InvalidDefineException ei)
						{
							throw (new ReadWriteException("6006039" + wDelim + "DNWORKINGINFORMATION"));
						}
						catch (NotFoundException en)
						{
							throw (new ReadWriteException("6006039" + wDelim + "DNWORKINGINFORMATION"));
						}
					}
					
					// 仕分予定情報の状態フラグを削除状態に更新する。
					sortPlanAlterKey.KeyClear();
					// 仕分予定一意キーの一致する情報を対象とする。
					sortPlanAlterKey.setSortingPlanUkey(rSortingPlan[slc].getSortingPlanUkey());
					// 状態フラグを削除に更新する。
					sortPlanAlterKey.updateStatusFlag(SortingPlan.STATUS_FLAG_DELETE);
					// 最終更新処理名を更新する。
					sortPlanAlterKey.updateLastUpdatePname(wProcessName);
	
					try
					{				
						sortPlanHandler.modify(sortPlanAlterKey);
					}
					catch (InvalidDefineException ei)
					{
						throw (new ReadWriteException("6006039" + wDelim + "DNSHIPPINGPLAN"));
					}
					catch (NotFoundException en)
					{
						throw (new ReadWriteException("6006039" + wDelim + "DNSHIPPINGPLAN"));
					}
					
					// 作業情報の在庫IDより、対象となる在庫情報の更新を行う。
					for (int wlc = 0; wlc < rworkInfo.length; wlc++)
					{
						try
						{									
							stockSearchKey.KeyClear();
							// 在庫ＩＤの一致する情報を取得する。
							stockSearchKey.setStockId(rworkInfo[wlc].getStockId());
							
							// 行ロック指定にて、対象情報を取得する。
							Stock rstock = (Stock)stockHandler.findPrimaryForUpdate(stockSearchKey);
							
							stockAlterKey.KeyClear();
							// 在庫ＩＤの一致する情報を対象とする。
							stockAlterKey.setStockId(rworkInfo[wlc].getStockId());
							// 在庫数量より、作業情報の可能数分　減算する。
							stockAlterKey.updateStockQty(rstock.getStockQty() - rworkInfo[wlc].getPlanEnableQty());
							// 引当数量より、作業情報の可能数分　減算する。
							stockAlterKey.updateAllocationQty(rstock.getAllocationQty() - rworkInfo[wlc].getPlanEnableQty());
							// 在庫数が０になる場合は在庫の状態を完了に変更する。
							if (rstock.getStockQty() - rworkInfo[wlc].getPlanQty() <= 0)
							{
								stockAlterKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
							}
							
							// 最終更新処理名を更新する。
							stockAlterKey.updateLastUpdatePname(wProcessName);
	
							stockHandler.modify(stockAlterKey);
						}
						catch (NoPrimaryException ep)
						{
							throw (new ReadWriteException("6006039" + wDelim + "DMSTOCK"));
						}
						catch (InvalidDefineException ei)
						{
							throw (new ReadWriteException("6006039" + wDelim + "DMSTOCK"));
						}
						catch (NotFoundException en)
						{
							throw (new ReadWriteException("6006039" + wDelim + "DMSTOCK"));
						}				
					} // 作業情報より、在庫情報更新処理のFor文の終わり
					// 処理データカウンタのインクリメント
					updateConuter++;
				} // 出荷予定情報分の更新 For文の終わり
						
			} // Parameterにて通知された情報分の更新 For文の終わり
			
			registFlag = true;
			
			// スケジュール成功
			SortingParameter[] viewParam = (SortingParameter[])this.query(conn, workparam);
			
			// リスト発行要求あり時、出荷予定削除リスト(SortingDeleteWriter)を起動する。
			// リスト出力用Parameterエリア
			if (wListFlg > 0)
			{
				// 出荷予定削除リスト
				SortingPlanDeleteWriter sdWriter = new SortingPlanDeleteWriter(conn);
	
				// リスト出力Parameter領域		
				String[] wlConsignorCode = new String[vecConsignorCode.size()];	
				vecConsignorCode.copyInto(wlConsignorCode);
				String[] wlPlanDate = new String[vecPlanDate.size()];	
				vecPlanDate.copyInto(wlPlanDate);
				Date[] wlLastUpdateDate = new Date[vecLastUpdateDate.size()];	
				vecLastUpdateDate.copyInto(wlLastUpdateDate);
	
				sdWriter.setConsignorCode(wlConsignorCode);
				sdWriter.setPlanDate(wlPlanDate);
				sdWriter.setLastUpdateDate(wlLastUpdateDate);
	
				boolean wlStatus = sdWriter.startPrint();
				if (wlStatus == true)
				{
					// 6021013=After deletion, printing was successfully completed.
					wMessage = "6021013";
				}
				else
				{
					// 6007043=Printing failed after deletion. Please refer to log.
					wMessage = "6007043";
				}
			}
			else
			{
				// 一件も処理しなかった場合は、削除対象データが無かった事を通知する。
				if (updateConuter > 0)
				{
					// 6001005=Deleted.
					wMessage = "6001005";
				}
				else
				{
					// 6003014=There was no target data to delete.
					wMessage = "6003014";
				}
			}
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
				doRollBack(conn,wProcessName);
			}

			// If "Processing Extraction" flag was updated in its own class,
			// change the Processing Extract flag to 0: Stopping.
			if( updateLoadDataFlag )
			{
				updateLoadDataFlag(conn, false);
				doCommit(conn,wProcessName);
			}
			
		}

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
// end of class
