//$Id: ShippingPlanDeleteSCH.java,v 1.2 2006/11/21 04:22:57 suresh Exp $
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
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.shipping.report.ShippingPlanDeleteWriter;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * WEB出荷予定データ削除（一括）処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷予定データ削除（一括）処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR> 
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド) <BR> 
 * <BR>
 * <DIR>
 *   出荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
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
 *   [処理条件チェック] <BR>
 *   2-1.作業者コードとパスワードが作業者マスターに定義されていること。(<CODE>check()</CODE>メソッド) <BR>
 *   <DIR>
 *     作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR>
 *   </DIR>
 *   2-2.日次処理中でないこと。 <BR>
 *   <DIR>
 *     WareNaviシステム定義情報の「日次更新中フラグ」を参照して、チェックを行う。 <BR>
 *   </DIR>
 *   2-3.出荷予定情報より対象情報を取得する。 <BR>
 * </DIR>
 * <BR>
 * 3.削除ボタン押下処理(<CODE>startSCHgetParams()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、出荷予定データの削除処理を行います。 <BR>
 *   処理完了時、削除リストの発行有無に従い、出荷予定削除リストの発行処理を起動します。 <BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   処理完了後、荷主コード、荷主名称、選択ボタン条件(ON/OFF)、出荷予定日、出荷先コード、出荷先名称を返却します。 <BR>
 *   パラメータ内容より、出荷予定情報を検索し、対象情報を取得します。 <BR>
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
 *   3-3.出荷予定情報より対象情報を取得する。 <BR>
 *   <BR>
 *   [更新処理] <BR>
 *   -出荷予定削除リスト用にシステム日時を取得する。 <BR>
 *   <BR>
 *   -出荷予定情報テーブル(DNSHIPPINGPLAN)の更新処理 <BR>
 *   <DIR>
 *     A) 3-3処理にて取得した、出荷予定情報の状態フラグを削除状態に更新する。 <BR>
 *   </DIR>
 *   <BR>
 *   -作業情報テーブル(DNWORKINFO)の更新処理 <BR>
 *   <DIR>
 *     A) 3-3処理にて所得した、出荷予定情報の出荷予定一意キーが一致する <BR>
 *        作業情報の状態フラグを削除に更新する。 <BR>
 *   </DIR>
 *   <BR>
 *   -在庫情報(DMSTOCK)テーブルの更新処理 <BR>
 *   <DIR>
 *     A) 作業情報のSTOCK_IDが一致する、在庫情報を下記内容にて更新する。 <BR>
 *          在庫数量から、作業情報の予定数分 減算する。 <BR>
 *          引当数から、作業情報の予定数分 減算する。 <BR>
 *   </DIR>
 *   <BR>
 *   -次工程情報(DNNEXTPROC)の更新処理 <BR>
 *   <DIR>
 *     A) 3-3処理にて所得した、出荷予定情報の出荷予定一意キーが一致する(出荷予定一意キー項目) <BR>
 *        次工程情報の状態フラグを削除に更新する。 <BR>
 *   </DIR>
 *   <BR>
 *   [印刷処理] <BR>
 *   <DIR>
 *   リスト発行要求がONである場合のみ処理を行う。
 *   削除処理を行った情報が１件以上存在する場合は、<BR>
 *   <CODE>ShippingDeleteWriter</CODE>クラスを使用して出荷予定削除リストの印刷処理を行います。<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:22:57 $
 * @author  $Author: suresh $
 */
public class ShippingPlanDeleteSCH extends AbstractShippingSCH
{
	// Class variables -----------------------------------------------
	/**
	 * Class Name(Delete Shipping Plan)
	 */
	public static String wProcessName = "ShippingPlanDeleteSCH";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/21 04:22:57 $");
	}
	
	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public ShippingPlanDeleteSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。 <BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		
	    ShippingPlanReportFinder sRFinder = new ShippingPlanReportFinder(conn) ;
		ShippingPlanSearchKey shipPlanSearchKey = new ShippingPlanSearchKey();
		ShippingParameter param = new ShippingParameter();
		shipPlanSearchKey.KeyClear();
		// Status flag: Standby
		shipPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);
		// GROUP BY条件に荷主コード
		shipPlanSearchKey.setConsignorCodeGroup(1);
		shipPlanSearchKey.setConsignorCodeCollect("");
		
		try
		{   
		    if(sRFinder.search(shipPlanSearchKey) == 1)
		    {
				ShippingPlan[] sPlan = null ;
		        sPlan = (ShippingPlan[])sRFinder.getEntities(1) ;
		        param.setConsignorCode(sPlan[0].getConsignorCode());
		    }
		}
		catch(ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		    
		}
		finally
		{
			sRFinder.close();
		}
		return param;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。 <BR>
	 * @param searchParam 表示データ取得条件を持つ<CODE>ShippingParameter</CODE>クラスのインスタンス。 <BR>
	 *         <CODE>ShippingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。 <BR>
	 * @return 検索結果を持つ<CODE>ShippingParameter</CODE>インスタンスの配列。 <BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。 <BR>
	 *          入力条件にエラーが発生した場合はnullを返します。 <BR>
	 *          nullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		ShippingPlanHandler shipPlanHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey shipPlanSearchKey = new ShippingPlanSearchKey();

		ShippingParameter sparam = (ShippingParameter)searchParam;

		// Check the Worker code and password
		if (!checkWorker(conn, sparam))
		{
			return null;
		}

		// 画面表示情報の取得を行います。
		// 出荷予定情報を下記条件にて取得します。
		// パラメータの荷主コードを一致。
		// パラメータの開始出荷予定日以上(入力有時のみ)
		// パラメータの終了出荷予定日以上(入力有時のみ)
		shipPlanSearchKey.KeyClear();
		// Consignor code
		if (!StringUtil.isBlank(sparam.getConsignorCode()))
		{
			shipPlanSearchKey.setConsignorCode(sparam.getConsignorCode());
		}

		// Planned start shipping date
		if (!StringUtil.isBlank(sparam.getFromPlanDate()))
		{
			shipPlanSearchKey.setPlanDate(sparam.getFromPlanDate(), ">=");
		}

		// Planned end shipping date
		if (!StringUtil.isBlank(sparam.getToPlanDate()))
		{
			shipPlanSearchKey.setPlanDate(sparam.getToPlanDate(), "<=");
		}
		
		// Status flag: Standby
		shipPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);


		// GROUP BY条件に荷主コード、荷主名称、出荷予定日、出荷先コード、出荷先名称
		shipPlanSearchKey.setConsignorCodeGroup(1);
		shipPlanSearchKey.setPlanDateGroup(2);
		shipPlanSearchKey.setCustomerCodeGroup(3);

		// ORDER By条件を指定する。
		shipPlanSearchKey.setConsignorCodeOrder(1, true);
		shipPlanSearchKey.setPlanDateOrder(2, true);
		shipPlanSearchKey.setCustomerCodeOrder(3, true);
		
		// 取得情報をセットする。
		// Consignor code
		shipPlanSearchKey.setConsignorCodeCollect("");
		// Planned shipping date
		shipPlanSearchKey.setPlanDateCollect("");
		// Customer code
		shipPlanSearchKey.setCustomerCodeCollect("");
		
		ShippingPlan[] rShipplan = (ShippingPlan[])shipPlanHandler.find(shipPlanSearchKey);

		int wNotDel_Count = 0 ;				
		for (int lc = 0; lc < rShipplan.length; lc++)
		{
			ShippingPlanSearchKey ssKey = new ShippingPlanSearchKey() ;
			ssKey.KeyClear();
			ssKey.setConsignorCode(rShipplan[lc].getConsignorCode());
			ssKey.setPlanDate(rShipplan[lc].getPlanDate());
			ssKey.setCustomerCode(rShipplan[lc].getCustomerCode());
			// other than Standby or Delete
			ssKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART, "!=");
			ssKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
	
			int rCount = shipPlanHandler.count(ssKey);
			if (rCount > 0)
			{
				wNotDel_Count++;
			}
		}
		
		if (rShipplan == null || rShipplan.length <= 0)
		{
			// No target data was found.
			wMessage = "6003018";

			return new ShippingParameter[0];			
		}
		
		if (rShipplan.length - wNotDel_Count >= WmsParam.MAX_NUMBER_OF_DISP)
		// ここまで。
		{
			// 6003012={0} data match. As the result data exceeds {1}, please narrow your search.
			wMessage = "6003012" + wDelim + WmsFormatter.getNumFormat(rShipplan.length) + wDelim +AbstractSCH.MAX_NUMBER_OF_DISP_DISP;
			return null;			
		}
		
		Vector	vec = new Vector();
		
		boolean wContinueFlag = false;
				
		ShippingPlanReportFinder finder = new ShippingPlanReportFinder(conn);
		// 取得情報分処理をループする。
		for (int lc = 0; lc < rShipplan.length; lc++)
		{
			ShippingParameter wparam = new ShippingParameter();

			// 選択チェックBox(true:enable false:disnable)
			ShippingPlanSearchKey ssKey = new ShippingPlanSearchKey() ;
			ssKey.KeyClear();
			ssKey.setConsignorCode(rShipplan[lc].getConsignorCode());
			ssKey.setPlanDate(rShipplan[lc].getPlanDate());
			ssKey.setCustomerCode(rShipplan[lc].getCustomerCode());
			// other than Standby or Delete
			ssKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART, "!=");
			ssKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
			
			int rCount = shipPlanHandler.count(ssKey);
			if (rCount > 0)
			{
				wContinueFlag = true;
				continue ;
			} 
			else
			{
				wparam.setSelectBoxFlag(true);
			}

			// Consignor
			wparam.setConsignorCode(rShipplan[lc].getConsignorCode());
			// Planned shipping date
			wparam.setPlanDate(rShipplan[lc].getPlanDate());
			// Customer code
			wparam.setCustomerCode(rShipplan[lc].getCustomerCode());

			ShippingPlanSearchKey nameGetKey = new ShippingPlanSearchKey() ;
			nameGetKey.KeyClear();
			nameGetKey.setConsignorCode(rShipplan[lc].getConsignorCode());
			nameGetKey.setPlanDate(rShipplan[lc].getPlanDate());
			nameGetKey.setCustomerCode(rShipplan[lc].getCustomerCode());
			nameGetKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
			
			nameGetKey.setLastUpdateDateOrder(1, false);

			if (finder.search(nameGetKey) > 0)
			{
				ShippingPlan[] name = (ShippingPlan[]) finder.getEntities(1);
				// Consignor name
				wparam.setConsignorName(name[0].getConsignorName());
				// Customer name
				wparam.setCustomerName(name[0].getCustomerName1());
			}
			 
			vec.addElement(wparam);
		}

		// 返却用Parameterエリア
		ShippingParameter[] rparam = new ShippingParameter[vec.size()];
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
		}

		return rparam;
	}

	/** 
	 * 画面から入力された内容をパラメータとして受け取り、必要情報の更新処理及びリスト処理の起動を行います。 <BR>
	 * 本処理が正常完了後、ためうちエリア出力用のデータをデータベースから取得して返します。 <BR>
	 * @param conn        データベースとのコネクションを保持するインスタンス。
	 * @param startParams ShippingParameterクラスのエンティティ
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  このメソッドが呼び出された場合に通知されます。 <BR>
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		
		// Check the Worker code and password
		ShippingParameter workparam = (ShippingParameter)startParams[0];
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
			
			// 出荷予定情報のHANDLER定義
			ShippingPlanHandler shipPlanHandler = new ShippingPlanHandler(conn);
			ShippingPlanSearchKey shipPlanSearchKey = new ShippingPlanSearchKey();
			ShippingPlanSearchKey ssKey = new ShippingPlanSearchKey();
			ShippingPlanAlterKey shipPlanAlterKey = new ShippingPlanAlterKey();
	
			// 作業情報のHANDLER定義
			WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey() ;
			WorkingInformationAlterKey workInfoAlterKey = new WorkingInformationAlterKey() ;
	
			// 次工程情報のHANDLER定義
			NextProcessInfoHandler nextProcHandler = new NextProcessInfoHandler(conn);
			NextProcessInfoSearchKey nextProcSearchKey = new NextProcessInfoSearchKey() ;
			NextProcessInfoAlterKey nextProcAlterKey = new NextProcessInfoAlterKey() ;
	
			// 在庫情報のHANDLER定義
			StockHandler stockHandler = new StockHandler(conn);
			StockSearchKey stockSearchKey = new StockSearchKey();
			StockAlterKey stockAlterKey = new StockAlterKey();
	
			// For printing ticket
			Date startDate = new Date();
			
			// Initialize Process Counter
			int updateConuter = 0;
	
			// パラメータ入力件数分処理を行う。
			for (int lc = 0; lc < startParams.length; lc++)
			{
				ShippingParameter rparam = (ShippingParameter)startParams[lc];
	
				// 選択BoxがＯＮの情報のみ処理を行う。
				if (rparam.getSelectBoxCheck() == ShippingParameter.SELECT_BOX_OFF)	continue;
				// 未開始又は削除以外の状態が存在する場合、エラー復帰を行う。
				ssKey.KeyClear();
				ssKey.setConsignorCode(rparam.getConsignorCode());
				ssKey.setPlanDate(rparam.getPlanDate());
				ssKey.setCustomerCode(rparam.getCustomerCode());
				// other than Standby or Delete
				ssKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART, "!=");
				ssKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
				
				if (shipPlanHandler.count(ssKey) > 0)
				{
					// No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + rparam.getRowNo();
					return null;
				}
	
				shipPlanSearchKey.KeyClear();
				shipPlanSearchKey.setConsignorCode(rparam.getConsignorCode());
				shipPlanSearchKey.setPlanDate(rparam.getPlanDate());
				shipPlanSearchKey.setCustomerCode(rparam.getCustomerCode());
				shipPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);
			
				// 行ロック指定にて、対象情報を取得する。
				ShippingPlan[] rShipplan = (ShippingPlan[])shipPlanHandler.findForUpdate(shipPlanSearchKey);
	
				// 出荷予定情報分、処理を行う。
				for (int slc = 0; slc < rShipplan.length; slc++)
				{
					// 出荷予定情報より、対象となる作業情報を取得する。
					workInfoSearchKey.KeyClear();
					// 出荷予定一意キーの一致する情報を取得する。
					workInfoSearchKey.setPlanUkey(rShipplan[slc].getShippingPlanUkey());
					
					// 行ロック指定にて、対象情報を取得する。
					WorkingInformation[] rworkInfo = (WorkingInformation[])workInfoHandler.findForUpdate(workInfoSearchKey);
					
					// 作業情報に対象情報が存在している場合のみ、処理を行う。
					if (rworkInfo != null && rworkInfo.length > 0)
					{
						try
						{
							// 作業情報の状態フラグが削除に更新する。
							workInfoAlterKey.KeyClear();
							// 出荷予定一意キーの一致する情報を対象とする。
							workInfoAlterKey.setPlanUkey(rShipplan[slc].getShippingPlanUkey());
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
					
					// 出荷予定情報の状態フラグを削除状態に更新する。
					shipPlanAlterKey.KeyClear();
					// 出荷予定一意キーの一致する情報を対象とする。
					shipPlanAlterKey.setShippingPlanUkey(rShipplan[slc].getShippingPlanUkey());
					// 状態フラグを削除に更新する。
					shipPlanAlterKey.updateStatusFlag(ShippingPlan.STATUS_FLAG_DELETE);
					// 最終更新処理名を更新する。
					shipPlanAlterKey.updateLastUpdatePname(wProcessName);
	
					try
					{				
						shipPlanHandler.modify(shipPlanAlterKey);
					}
					catch (InvalidDefineException ei)
					{
						throw (new ReadWriteException("6006039" + wDelim + "DNSHIPPINGPLAN"));
					}
					catch (NotFoundException en)
					{
						throw (new ReadWriteException("6006039" + wDelim + "DNSHIPPINGPLAN"));
					}
	
					//出荷予定情報より、対象となる次工程情報を取得する。
					nextProcSearchKey.KeyClear();
					// 出荷予定一意キーの一致する情報を取得する。
					nextProcSearchKey.setShippingPlanUkey(rShipplan[slc].getShippingPlanUkey());
					
					// 行ロック指定にて、対象情報を取得する。
					NextProcessInfo[] rnextProc = (NextProcessInfo[])nextProcHandler.findForUpdate(nextProcSearchKey);
					//次工程情報に対象情報が存在している場合のみ、処理を行う。
					if (rnextProc != null && rnextProc.length > 0)
					{	
					    //キーをクリアする。
					    nextProcAlterKey.KeyClear();
					    //サーチパラメータをセットする。
					    nextProcAlterKey.setShippingPlanUkey(rShipplan[slc].getShippingPlanUkey());
					    //ダミーを更新する。
					    nextProcAlterKey.updateShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						// 作業実績数
						nextProcAlterKey.updateResultQty(0);
						// 作業欠品数
						nextProcAlterKey.updateShortageQty(0);
					    //処理名を更新する。
					    nextProcAlterKey.updateLastUpdatePname(wProcessName);
					    try
					    {
					        nextProcHandler.modify(nextProcAlterKey);
					    }
					    catch(InvalidDefineException ide)
					    {
					        throw (new ReadWriteException("6006039" + wDelim + "DNNEXTPROCESS"));
					    }
						catch (NotFoundException en)
						{
							throw (new ReadWriteException("6006039" + wDelim + "DNNEXTPROCESS"));
						}
					    
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
							// 在庫数量より、作業情報の可能数分減算する。
							stockAlterKey.updateStockQty(rstock.getStockQty() - rworkInfo[wlc].getPlanEnableQty());
							// 引当数量より、作業情報の可能数分減算する。
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
					}
					// 処理データカウンタのインクリメント
					updateConuter++;
				} // 出荷予定情報分の更新 For文の終わり
						
			}
			
			registFlag = true;
			
			ShippingParameter[] viewParam = (ShippingParameter[])query(conn, workparam);
			
			// 1件も削除対象データがなかった場合、メッセージを通知
			if (updateConuter == 0)
			{
				// 6003014=There was no target data to delete.
				wMessage = "6003014";
			}
			// リスト発行要求あり時、出荷予定削除リストを発行する
			else if (workparam.getStorageListFlg())
			{
				// 出荷予定削除リスト
				ShippingPlanDeleteWriter writer = new ShippingPlanDeleteWriter(conn);
	
				writer.setConsignorCode(workparam.getConsignorCode());
				writer.setFromPlanDate(workparam.getFromPlanDate());
				writer.setToPlanDate(workparam.getToPlanDate());
				writer.setLastUpdateDate(startDate);
	
				if (writer.startPrint())
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
			// 対象データありでリスト発行要求がない場合
			else
			{
				// 6001005=Deleted.
				wMessage = "6001005";
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

}
// end of class
