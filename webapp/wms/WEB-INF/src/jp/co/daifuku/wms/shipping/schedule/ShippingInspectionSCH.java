//$Id: ShippingInspectionSCH.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

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
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * 出荷先別出荷検品設定処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷検品処理を行います。<BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、<BR>
 * トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *   作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *   <BR>
 *   [検索条件] <BR> 
 *   <DIR>
 *   作業区分が出荷検品<BR> 
 *   状態フラグが未開始<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   表示順の値が伝票No.順の場合、伝票No.、行No.順に表示を行います。 <BR>
 *   表示順の値が商品コード順の場合、商品コード、伝票No.、行No.順に表示を行います。 <BR>
 *   検索するテーブルは作業情報テーブル(DNWORKINFO)。<BR>
 *   検索対象が1000件(WMSParamに定義されたMAX_NUMBER_OF_DISP)を超えた場合、表示は行いません。<BR>
 *   リストセルのヘッダに表示する荷主名称は登録日時の新しい値を取得します。<BR>
 * </DIR>
 * <BR>
 * 3.登録ボタン押下処理(<CODE>startSCHgetParams()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、出荷検品処理を行います。 <BR>
 *   処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   -排他チェック<BR>
 *   作業No.、作業区分(出荷検品)、状態フラグ(未開始)にてデータを検索し、データをロックする。<BR>
 *   <BR>
 *   -データの更新<BR>
 *   <BR>
 *   デッドロックを防ぐため、作業情報、出荷予定情報、在庫情報の順番でテーブルの更新を行う。<BR>
 *   1設定分の出荷完了処理までが終わってから、作業者実績情報の登録または更新を行う。<BR>
 *   <BR>
 *   -作業情報テーブル(DNWORKINFO)の更新<BR> 
 *   <BR>
 *   1.作業情報の状態フラグを完了に更新する。(完了、欠品どちらの場合も完了とする。)<BR>
 *   2.最終更新処理名を更新する。<BR>  
 *   3.受け取ったパラメータの内容をもとに作業実績数、作業欠品数、賞味期限を更新する。<BR> 
 *     <DIR>
 *     パラメータの出荷数を作業情報の作業実績数に更新する。<BR> 
 *     欠品の場合、作業欠品数を作業可能数からパラメータの出荷数を減算した値に更新する。<BR>
 *     パラメータの賞味期限を作業情報の賞味期限(result_use_by_date)に更新する。<BR>
 *   4.受け取ったパラメータの内容をもとに作業者コード、作業者名、端末No.を更新する。<BR>
 *     作業者名の検索時、削除区分が使用可能かどうかは検索条件には含みません。<BR> 
 *     </DIR>
 *   <BR>
 *   -出荷予定情報テーブル(DNSHIPPINGPLAN)の更新 <BR>
 *   <BR>
 *   状態フラグ(未開始、一部完了)にてデータを検索すること。
 *   1.出荷予定情報の状態フラグを完了に更新する。(完了、欠品どちらの場合も完了とする。)<BR>
 *   2.最終更新処理名を更新する。<BR> 
 *   3.受け取ったパラメータの内容をもとに出荷実績数、出荷欠品数を更新する。<BR> 
 *     <DIR>
 *     パラメータの出荷数を出荷予定情報の出荷実績数に加算する。<BR>
 *     欠品の場合、出荷欠品数を作業情報の出荷可能数からパラメータの出荷数を減算した値に更新する。<BR> 
 *     </DIR>
 *   <BR>
 *   -出荷完了処理-(<CODE>ShippingCompleteOperator()</CODE>クラス)<BR>  
 *   <BR>
 *   作業No.、作業区分(出荷検品)、処理名、作業者コード、作業者名、端末No.をセットする。<BR>
 *   作業No.と作業区分(出荷検品)をもとに作業情報の検索を行い、在庫情報の更新を行う。<BR>
 *   処理名、作業者コード、作業者名、端末No.は各テーブル登録用に使用する。<BR> 
 *   <BR>
 *   -作業者実績情報テーブル(DNWORKERRESULT)の登録または更新 <BR>
 *   <BR>
 *   作業者コード、作業日、端末No.、作業区分をもとに作業者実績情報の検索を行う。<BR>
 *   データが存在する場合、作業終了日時、作業者名称、作業数量、作業回数を更新する。<BR>
 *     <DIR>
 *     作業終了日時に、現在日時をセットする。<BR>
 *     作業者名称を更新する。<BR>
 *     作業数量 = 作業数量 + 作業情報の実績数(1設定分の作業情報の実績数をトータルする)<BR>
 *     作業回数 = 作業回数 + 1<BR>
 *     </DIR>
 *     <BR>
 *   データが存在しない場合、作業者実績情報を登録する。<BR>
 *     <DIR>
 *     作業日は、システム定義日付をセットする。<BR>
 *     作業開始日時、作業終了日時の両方に、現在日時をセットする。<BR>
 *     作業数量は、1設定分の作業情報の実績数をトータルしてセットする。<BR>
 *     作業回数は、1をセットする。<BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class ShippingInspectionSCH extends AbstractShippingSCH
{
	/**
	 * クラス名(出荷検品)
	 */
	public static String CLASS_SHIPPING = "ShippingInspectionSCH";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingInspectionSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 表示データ取得条件を持つ<CODE>ShippingPlanParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>ShippingPlanParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WorkingInformationReportFinder wRFinder = new WorkingInformationReportFinder(conn);
		
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// データの検索
		// 作業区分(出荷検品)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		// 状態フラグ(未開始)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		ShippingParameter dispData = new ShippingParameter();

		try
		{
			int count = wRFinder.search(searchKey) ;		
			WorkingInformation[] working = null ;
			if (count == 1)
			{
				    working = (WorkingInformation[])wRFinder.getEntities(1) ;
					dispData.setConsignorCode(working[0].getConsignorCode());		
			}
   
		}
		catch(ReadWriteException e)
		{
		    //6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), CLASS_SHIPPING );
			throw ( new ReadWriteException( "6006002" + wDelim + "DnWorkInfo" ) ) ;
		    
		}
		finally
		{
			wRFinder.close();
		}
		
		return dispData;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。<BR>
	 * @param searchParam 表示データ取得条件を持つ<CODE>ShippingPlanParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>ShippingPlanParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>ShippingPlanParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が1000件を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。<BR>
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 作業者コード、パスワードをチェック
		ShippingParameter param = (ShippingParameter)searchParam;
		if (!super.checkWorker(conn, param))
		{
			return null;
		}

		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey namesearchKey = new WorkingInformationSearchKey();

		// データの検索
		// 作業区分(出荷検品)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		namesearchKey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		// 状態フラグ(未開始)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		namesearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		
		// 荷主コード
		searchKey.setConsignorCode(param.getConsignorCode());
		namesearchKey.setConsignorCode(param.getConsignorCode());
		// 出荷予定日
		searchKey.setPlanDate(param.getPlanDate());
		namesearchKey.setPlanDate(param.getPlanDate());
		// 出荷先コード
		searchKey.setCustomerCode(param.getCustomerCode());
		namesearchKey.setCustomerCode(param.getCustomerCode());
		// 商品コード
		String itemcode = param.getItemCode();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
			namesearchKey.setItemCode(itemcode);
		}
		// 表示順
		String dsporder = param.getDspOrder();
		// 伝票No.順
		if (dsporder.equals(ShippingParameter.DSPORDER_TICKET))
		{
			//伝票No.、行No.の昇順でソート
			searchKey.setShippingTicketNoOrder(1, true);
			searchKey.setShippingLineNoOrder(2, true);
		}
		// 商品コード順
		else if (dsporder.equals(ShippingParameter.DSPORDER_ITEM))
		{
			// 商品コード、伝票No.、行No.の昇順でソート
			searchKey.setItemCodeOrder(1, true);
			searchKey.setShippingTicketNoOrder(2, true);
			searchKey.setShippingLineNoOrder(3, true);
		}
		
		if(!super.canLowerDisplay(workingHandler.count(searchKey)))
		{
			return super.returnNoDiaplayParameter();
		}
			
		WorkingInformation[] resultEntity = (WorkingInformation[])workingHandler.find(searchKey);

		
		// 登録日時の新しい荷主名称と出荷先名称を取得します。
		namesearchKey.setRegistDateOrder(1, false);
		WorkingInformation[] working = (WorkingInformation[])workingHandler.find(namesearchKey);
		String consignorname = "";
		String customername = "";
		if(working != null && working.length != 0)
		{
			consignorname = working[0].getConsignorName();
			customername = working[0].getCustomerName1();
		}	

		Vector vec = new Vector();

		for (int i = 0; i < resultEntity.length; i++)
		{
			ShippingParameter dispData = new ShippingParameter();
			// 荷主コード
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			// 荷主名称(登録日時の新しい荷主名称)
			dispData.setConsignorName(consignorname);
			// 出荷予定日
			dispData.setPlanDate(resultEntity[i].getPlanDate());
			// 出荷先コード
			dispData.setCustomerCode(resultEntity[i].getCustomerCode());
			// 出荷先名称(登録日時の新しい出荷先名称)
			dispData.setCustomerName(customername);
			// 商品コード
			dispData.setItemCodeL(resultEntity[i].getItemCode());
			// 商品名称
			dispData.setItemName(resultEntity[i].getItemName1());
			// 表示順
			dispData.setDspOrder(dsporder);
			// 出荷伝票No.
			dispData.setShippingTicketNo(resultEntity[i].getShippingTicketNo());
			// 出荷伝票行No.
			dispData.setShippingLineNo(resultEntity[i].getShippingLineNo());
			// ケース入数
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			// ボール入数
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());
			// 作業予定ケース数
			// 作業可能数をケースで割った商がケース数になります。
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty()));
			// 作業予定ピース数
			// 作業可能数をケースで割った余りがピース数になります。
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty()));
			// 作業No.
			dispData.setJobNo(resultEntity[i].getJobNo());
			// 最終更新日時
			dispData.setLastUpdateDate(resultEntity[i].getLastUpdateDate());

			vec.addElement(dispData);
		}

		ShippingParameter[] paramArray = new ShippingParameter[vec.size()];
		vec.copyInto(paramArray);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		return paramArray;
	}
	/**
	 * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
	 * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
	 * このメソッドはスケジュールの結果をもとに、画面表示内容を再表示する場合に使用します。
	 * 条件エラーなどでスケジュール処理が失敗した場合はnullを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams データベースとのコネクションオブジェクト
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
		
			// 作業者コード、パスワードのチェック
			ShippingParameter workparam = (ShippingParameter)startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return null;
			}
			
			// 日次更新処理中のチェック			
			if (isDailyUpdate(conn))
			{
				return null;
			}
	
			// 対象データ全件の排他チェック・ロックを行う。
			if (!lockAll(conn, startParams))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return null;
			}

			// ためうちの入力チェック
			if (!checkList(startParams))
			{
				return null;
			}
			
			// 作業区分(出荷検品)
			String jobtype = WorkingInformation.JOB_TYPE_SHIPINSPECTION;
			// 処理名
			String pname = CLASS_SHIPPING;
			// 作業者コード
			String workercode = workparam.getWorkerCode();
			// 作業者名称
			String workername = super.getWorkerName(conn,workercode);
			// 端末№
			String terminalno = workparam.getTerminalNumber();
						
			int workqty = 0;
			
			// デッドロックを防ぐため、作業情報、出荷予定情報、在庫情報の順番で更新を行う。
			// 1設定分の出荷完了処理までが終わってから、作業者実績情報の登録または更新を行う。
			for (int i = 0; i < startParams.length; i++)
			{
				ShippingParameter param = (ShippingParameter)startParams[i];
	
				// 作業No.
				String jobno = param.getJobNo();
				// 最終更新日時
				Date lastupdate = param.getLastUpdateDate();
				// 行No.
				int rowno = param.getRowNo();
				
				String usebydate = param.getUseByDate();
				int enteringqty = param.getEnteringQty();
				int planqty = param.getPlanCaseQty() * enteringqty + param.getPlanPieceQty();
				int inputqty = param.getResultCaseQty() * enteringqty + param.getResultPieceQty();
				// 作業数量(1設定分の作業情報の実績数をトータルする)
				workqty = addWorkQty(workqty, inputqty);
				
				WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
				WorkingInformationSearchKey worksearchKey = new WorkingInformationSearchKey();
			
				worksearchKey.setJobNo(param.getJobNo());
								
				WorkingInformation resultEntity = (WorkingInformation) workingHandler.findPrimary(worksearchKey);															
				// 作業開始フラグが未開始の時
				if(resultEntity.getBeginningFlag().equals(WorkingInformation.BEGINNING_FLAG_NOT_STARTED))
				{
					// 前工程の処理が完了していないため、完了処理できません。
					wMessage = "6023376" + wDelim + rowno;
					return null;
				}
				
				// 排他チェック
				if (!this.lock(conn, jobno, lastupdate))
				{
					// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
					wMessage = "6023209" + wDelim + rowno;
					return null;
				}
	
				// 作業情報テーブル(DNWORKINFO)の更新
				if (!this.updateWorkinginfo(conn, jobno, planqty, inputqty, usebydate, workercode, workername, terminalno))
				{
					return null;
				}
	
				// 出荷予定情報テーブル(DNSHIPPINGPLAN)の更新
				if (!this.updateShippingPlan(conn, jobno, planqty, inputqty))
				{
					return null;
				}
	
				// 出荷完了処理(DMSTOCK, DNHOSTSEND)
				ShippingCompleteOperator shipping = new ShippingCompleteOperator();
				if (!shipping.complete(conn, jobno, jobtype, pname, workercode, workername, terminalno))
				{
					return null;
				}
								
			}
			
			updateWorkerResult(conn,workercode,workername, getWorkDate(conn),terminalno,jobtype,workqty);
		
			// スケジュール成功
			ShippingParameter[] viewParam = (ShippingParameter[])this.query(conn, workparam);
	
			// 6001003=登録しました。
			wMessage = "6001003";
	
			return viewParam;
			
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}

	}

	/**
	 * ためうち入力チェックを行います。<BR>
	 * @param searchParams 表示データ取得条件を持つ<CODE>ShippingPlanParameter</CODE>クラスのインスタンス。<BR>
	 * @return 入力チェックが正しければTrueを、不正ならFalseを返します。
	 */
	protected boolean checkList(Parameter[] searchParams)
	{
		for (int i = 0; i < searchParams.length; i++)
		{
			ShippingParameter param = (ShippingParameter)searchParams[i];

			// パラメータからケース入数、出荷ケース数、出荷ピース数、欠品区分を取得する。
			int enteringqty = param.getEnteringQty();
			int planqty = param.getPlanCaseQty() * enteringqty + param.getPlanPieceQty();
			int caseqty = param.getResultCaseQty();
			int pieceqty = param.getResultPieceQty();
			long inputqty = (long)caseqty * (long)enteringqty + pieceqty;
			// 行No.
			int rowno = param.getRowNo();			

			// 出荷数のオーバーフローチェック
			if (inputqty > WmsParam.MAX_TOTAL_QTY)
			{
				// 6023272 = No.{0} {1}には{2}以下の値を入力してください。
				wMessage = "6023272" + wDelim + rowno + wDelim + DisplayText.getText("LBL-W0198") + wDelim + AbstractSCH.MAX_TOTAL_QTY_DISP;
				return false;
			}
			
			// ケース入数が0、ケース数が0以外の場合
			if (enteringqty == 0 && caseqty !=0)
			{
				// 6023271 = No.{0} ケース入数が0の場合は、出荷ケース数は入力できません。
				wMessage = "6023271" + wDelim + rowno;
				return false;		
			}
			// ケース数 * ケース入数 + ピース数 == 0、欠品フラグ(なし)
			else if (inputqty == 0 && param.getShortage() == false)
			{
				// 6023117 = No.{0} 予定数と完了数に差異があります。確認後再登録を行って下さい。
				wMessage = "6023117" + wDelim + rowno;
				return false;	
			}
			// ケース数 * ケース入数 + ピース数 < 0
			else if (inputqty != 0)
			{
				// 入力値が予定数より少ない、欠品フラグ(なし)
				if (inputqty < planqty && param.getShortage() == false)
				{
					// 6023117 = No.{0} 予定数と完了数に差異があります。確認後再登録を行って下さい。
					wMessage = "6023117" + wDelim + rowno;
					return false;
				}
				// 入力値と予定数が等しい、欠品フラグ(あり)
				else if (inputqty == planqty && param.getShortage() == true)
				{
					// 6023119 = No.{0} 欠品数のない行に欠品が指定されています。確認後再登録を行って下さい。
					wMessage = "6023119" + wDelim + rowno;
					return false;
				}
				// 入力値が予定数より多い
				else if (inputqty > planqty)
				{
					// 6023155 = No.{0} 完了数が予定数を超えています。確認後再登録を行って下さい。
					wMessage = "6023155" + wDelim + rowno;
					return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * 他の端末で既に変更されたかどうかの確認を行います。
	 * パラメータにセットされている最終更新日時と現在のDBから取得した最終更新日時を比較します。
	 * 比較の結果、双方の最終更新日時が等しい場合は他の端末で変更されていないとし、
	 * 等しくない場合は他の端末で既に変更されていると見なします。
	 * 対象データは、作業情報テーブル(DNWORKINFO)とします。
	 * @param conn       データベースとのコネクションオブジェクト
	 * @param jobno      作業No.
	 * @param lastupdate 最終更新日時
	 * @return 他の端末ですでに変更されている場合はtrue、まだ変更されていない場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected boolean lock(Connection conn, String jobno, Date lastupdate) throws ReadWriteException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// データのロック
		// 作業No.
		searchKey.setJobNo(jobno);
		// 作業区分(出荷検品)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
				
		WorkingInformation[] working = (WorkingInformation[])workingHandler.findForUpdate(searchKey);

		// 作業No.がデータに存在しない(データが削除された場合)
		if (working == null || working.length == 0)
		{
			return false;
		}
		// 状態フラグが未開始以外(取込が行われた場合／メンテナンスで状態がかわる場合)
		if (!(working[0].getStatusFlag()).equals(WorkingInformation.STATUS_FLAG_UNSTART))
		{
			return false;
		}

		// パラメータにセットされている最終更新日時と現在のDBから取得した最終更新日時を比較します。
		// 等しくない場合は他の端末で既に変更されていると見なします。
		if (!working[0].getLastUpdateDate().equals(lastupdate))
		{
			return false;
		}

		return true;
	}

	/**
	 * 作業情報テーブルの更新を行う。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param jobno      作業No.
	 * @param planqty    作業可能数
	 * @param inputqty   作業実績数
	 * @param usebydate  賞味期限
	 * @param workercode 作業者コード
	 * @param workername 作業者名
	 * @param terminalno 端末No.
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean updateWorkinginfo(Connection conn, String jobno, int planqty, int inputqty, String usebydate, 
											String workercode, String workername, String terminalno) throws 
											ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationAlterKey alterKey = new WorkingInformationAlterKey();

			// 作業No.、作業区分(出荷検品)をセット
			alterKey.setJobNo(jobno);
			alterKey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);

			// 1.作業情報の状態フラグを完了に更新する。(完了、欠品どちらの場合も完了とする。)
			alterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);

			// 2.最終更新処理名を更新する。  
			alterKey.updateLastUpdatePname(CLASS_SHIPPING);

			// 3.受け取ったパラメータの内容をもとに作業実績数、作業欠品数、賞味期限を更新する。 
			// パラメータの出荷数を作業情報の作業実績数に更新する。 
			alterKey.updateResultQty(inputqty);

			// 欠品の場合、作業欠品数を作業可能数からパラメータの出荷数を減算した値に更新する。
			if (planqty != inputqty)
			{
				int shortage = planqty - inputqty;
				alterKey.updateShortageCnt(shortage);
			}
			// パラメータの賞味期限を作業情報の賞味期限(result_use_by_date)に更新する。(完了、欠品どちらの場合もセットする。) 
			alterKey.updateResultUseByDate(usebydate);

			// 4.受け取ったパラメータの内容をもとに作業者コード、作業者名、端末No.を更新する。
			alterKey.updateWorkerCode(workercode);
			alterKey.updateWorkerName(workername);
			alterKey.updateTerminalNo(terminalno); 

			// データの更新
			workingHandler.modify(alterKey);
			
			return true;

		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	/**
	 * 出荷予定情報テーブルの更新を行う。<BR>
	 * @param conn データベースとのコネクションオブジェクト<BR>
	 * @param jobno     作業No.
	 * @param planqty   作業可能数
	 * @param inputqty  作業実績数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean updateShippingPlan(Connection conn, String jobno, int planqty, int inputqty) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workingsearchKey = new WorkingInformationSearchKey();

			// データの検索
			// 作業No.
			workingsearchKey.setJobNo(jobno);
			// 作業区分(出荷検品)
			workingsearchKey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
			
			WorkingInformation working = (WorkingInformation)workingHandler.findPrimary(workingsearchKey);
			
			if (working != null)
			{
				String planukey = working.getPlanUkey();
							
				ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
				ShippingPlanSearchKey shippingsearchKey = new ShippingPlanSearchKey();
				ShippingPlanAlterKey alterKey = new ShippingPlanAlterKey();
	
				// データの検索
				// 出荷予定一意キー
				shippingsearchKey.setShippingPlanUkey(planukey);
				// 状態フラグ(未開始、一部完了)にてデータを検索すること。
				String[] statusflg = { ShippingPlan.STATUS_FLAG_UNSTART, ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART };
				shippingsearchKey.setStatusFlag(statusflg);
				
				ShippingPlan shipping = (ShippingPlan)shippingHandler.findPrimary(shippingsearchKey);
				
				if (shipping != null)
				{
					// 出荷予定一意キーをセット
					alterKey.setShippingPlanUkey(planukey);
	
					// 1.最終更新処理名を更新する。 
					alterKey.updateLastUpdatePname(CLASS_SHIPPING);
		
					// 2.受け取ったパラメータの内容をもとに出荷実績数、出荷欠品数を更新する。 
					// パラメータの出荷数を出荷予定情報の出荷実績数に加算する。
					int resultqty = shipping.getResultQty() + inputqty;
					alterKey.updateResultQty(resultqty);
		
					// 欠品の場合、出荷欠品数を作業情報の出荷可能数からパラメータの出荷数を減算した値に更新する。 
					int shortage = shipping.getShortageCnt();
					if (planqty != inputqty)
					{
						// 作業情報の作業可能数から入力数を減算した数を欠品数とした上で
						// 現在の欠品数も加算して更新する。
						shortage = planqty - inputqty;
						shortage = shortage + shipping.getShortageCnt();
						alterKey.updateShortageCnt(shortage);
					}
					
					// 予定情報の実績数+欠品数が予定数以上であれば完了とする。
					// 予定数未満であれば一部完了とする。
					if (shipping.getPlanQty() <= resultqty + shortage)
					{
						// 3.出荷予定情報の状態フラグを完了に更新する。
						alterKey.updateStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETION);						
					}
					else
					{
						// 3.出荷予定情報の状態フラグを一部完了に更新する。
						alterKey.updateStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART);						
					}
		
					// データの更新
					shippingHandler.modify(alterKey);
	
					return true;
				}
				else
				{
					// 6006040 = データの不整合が発生しました。ログを参照してください。{0}
					RmiMsgLogClient.write("6006040" + wDelim + planukey, CLASS_SHIPPING);
					// ここで、ScheduleExceptionをthrowする。(エラーメッセージはセット不要です)
					throw (new ScheduleException());
				}
			}
			else
			{
				// 6006040 = データの不整合が発生しました。ログを参照してください。{0}
				RmiMsgLogClient.write("6006040" + wDelim + jobno, CLASS_SHIPPING);
				// ここで、ScheduleExceptionをthrowする。(エラーメッセージはセット不要です)
				throw (new ScheduleException());
			}
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
}
//end of class
