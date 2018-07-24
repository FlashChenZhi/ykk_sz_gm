// $Id: InstockReceivePlanWriter.java,v 1.4 2006/12/13 08:56:45 suresh Exp $
package jp.co.daifuku.wms.instockreceive.report;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR> 
 * <CODE>InstockReceivePlanWriter</CODE>クラスは、クロス/DC入荷予定リストの帳票用データファイルを作成し、印刷を実行します。<BR>
 * <CODE>InstockReceivePlanListSCH</CODE>クラスでセットされた条件にもとづき、入荷予定情報を検索し、<BR>
 * 結果をクロス/DC入荷予定リスト用の帳票ファイルとして作成します。<BR>
 * このクラスは、以下の処理を行います。<BR>
 * <BR>
 * 帳票用データファイル作成処理(<CODE>startPrint()</CODE>メソッド)<BR>
 *	<DIR>
 *	1.<CODE>InstockReceivePlanListSCH</CODE>クラスからセットされた条件で入荷予定情報の件数を検索します。<BR>
 *	2.結果が1件以上なら、帳票ファイルを作成します。0件だった場合はfalseを返して終了します。<BR>
 *	3.印刷処理を実行します。<BR>
 *  4.印刷処理が正常だった場合はtrueを返します。<BR>
 *    印刷処理中にエラーが発生した場合はfalseを返します。<BR>
 *<BR>
 * 	＜パラメータ＞*必須入力<BR>
 *	 	荷主コード*<BR>
 *      開始入荷予定日<BR>
 * 		終了入荷予定日<BR>
 * 		仕入先コード<BR>
 * 		開始伝票№<BR>
 * 		終了伝票№<BR>
 * 		商品コード<BR>
 * 		クロス/DC(全て、クロス、DC)<BR>	
 * 		作業状態 <BR>
 *     <BR>		
 *	＜印刷データ＞<BR>
 * 		荷主コード <BR>
 * 		荷主名称 <BR> 			
 *      入荷予定日  <BR>
 * 		仕入先コード<BR>
 * 		仕入先名称<BR>
 * 		伝票№<BR>
 * 		伝票行№<BR>
 * 		商品コード  <BR>
 * 		商品名称 <BR>
 * 		ケース入数 <BR>
 * 		ボール入数<BR>
 *		ホスト予定ケース数：入荷予定数／ケース入数<BR>
 *		ホスト予定ピース数：入荷予定数％ケース入数<BR>
 *		実績ケース数：入荷実績数／ケース入数<BR>
 *		実績ピース数：入荷実績数％ケース入数<BR>
 * 		クロス/DC <BR>
 * 		状態 <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/02</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 08:56:45 $
 * @author  $Author: suresh $
 */

public class InstockReceivePlanWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/** 
	 * 荷主コード
	 */
	private String wConsignorCode = null;

	/**
	 * 開始入荷受付日
	 */
	private String wFromDate;

	/**
	 * 終了入荷受付日
	 */
	private String wToDate;

	/**
	 * 仕入先コード
	 */
	private String wSupplierCode;

	/**
	 * 開始伝票No.
	 */
	private String wFromTicketNo;

	/**
	 * 終了伝票No.
	 */
	private String wToTicketNo;

	/**
	 * 商品コード
	 */
	private String wItemCode;

	/**
	 * 作業状態
	 */
	private String wStatusFlag;
	/**
	 * ＴＣ/ＤＣ区分
	 */
	private String wTcdcFlag;
	
	/**
	 * 表示する荷主名称
	 */
	private String wConsignorName = null ;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 08:56:45 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * InstockReceivePlanWriter オブジェクトを構築します。
	 * 
	 * @param conn<CODE>Connection</CODE>
	 * @param locale<CODE>Locale</CODE>
	 */
	public InstockReceivePlanWriter(Connection conn)
	{
		super(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * 荷主コードに検索値をセットします。
	 * @param pConsignorcode セットする荷主コード
	 */
	public void setConsignorCode(String pConsignorcode)
	{
		wConsignorCode = pConsignorcode;
	}

	/**
	 * 荷主コードを取得します。
	 * @return 荷主コード
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}
	/**
	 * 荷主名称に値をセットします。
	 * @param consignorcode セットする荷主名称
	 */
	public void setConsignorName(String consignorname)
	{
		wConsignorName = consignorname;
	}

	/**
	 * 荷主名称を取得します。
	 * @return 荷主名称
	 */
	public String getConsignorName()
	{
		return wConsignorName;
	}
	
	/**
	 * 開始入荷受付日に検索値をセットします。
	 * @param pFromDate セットする開始入荷受付日
	 */
	public void setFromDate(String pFromDate)
	{
		wFromDate = pFromDate;
	}

	/**
	 * 開始入荷受付日を取得します。
	 * @return 開始入荷受付日
	 */
	public String getFromDate()
	{
		return wFromDate;
	}

	/**
	 * 終了入荷受付日に検索値をセットします。
	 * @param pToDate セットする終了入荷受付日
	 */
	public void setToDate(String pToDate)
	{
		wToDate = pToDate;
	}

	/**
	 * 終了入荷受付日を取得します。
	 * @return 終了入荷受付日
	 */
	public String getToDate()
	{
		return wToDate;
	}

	/**
	 * 仕入先コードに検索値をセットします。
	 * @param pSupplierCode セットする仕入先コード
	 */
	public void setSupplierCode(String pSupplierCode)
	{
		wSupplierCode = pSupplierCode;
	}

	/**
	 * 仕入先コードを取得します。
	 * @return 仕入先コード
	 */
	public String getSupplierCode()
	{
		return wSupplierCode;
	}

	/**
	 * 開始伝票No.に検索値をセットします。
	 * @param pFromTicketNo セットする開始伝票No.
	 */
	public void setFromTicketNo(String pFromTicketNo)
	{
		wFromTicketNo = pFromTicketNo;
	}

	/**
	 * 開始伝票No.を取得します。
	 * @return 開始伝票No.
	 */
	public String getFromTicketNo()
	{
		return wFromTicketNo;
	}

	/**
	 * 終了伝票No.に検索値をセットします。
	 * @param pToTicketNo セットする終了伝票No.
	 */
	public void setToTicketNo(String pToTicketNo)
	{
		wToTicketNo = pToTicketNo;
	}

	/**
	 * 終了伝票No.を取得します。
	 * @return 終了伝票No.
	 */
	public String getToTicketNo()
	{
		return wToTicketNo;
	}

	/**
	 * 商品コードに検索値をセットします。
	 * @param pItemCode セットする商品コード
	 */
	public void setItemCode(String pItemCode)
	{
		wItemCode = pItemCode;
	}

	/**
	 * 商品コードを取得します。
	 * @return 商品コード
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	/**
	* 作業状態を取得する。
	* @return 作業状態
	*/
	public String getStatusFlag()
	{
		return wStatusFlag;
	}
	/**
	 * 作業状態をセットする。
	 * @param statusFlag セットする作業状態
	 */
	public void setStatusFlag(String statusFlag)
	{
		wStatusFlag = statusFlag;
	}

	/**
	 * ＴＣ/ＤＣ区分を取得する。
	 * @return ＴＣ/ＤＣ区分
	 */
	public String getTcdcFlag()
	{
		return wTcdcFlag;
	}
	/**
	 * ＴＣ/ＤＣ区分をセットする。
	 * @param tcdcFlag セットするＴＣ/ＤＣ区分
	 */
	public void setTcdcFlag(String tcdcFlag)
	{
		wTcdcFlag = tcdcFlag;
	}
	
	/**
	 * 印刷データの件数を取得します。<BR>
	 * この検索結果により、印刷処理を行うかどうかを判断するために使用します。<BR>
	 * 本メソッドは、画面処理を行うスケジュールクラスから使用されます。<BR>
	 * 
	 * @return 印刷データ件数
	 * @throws ReadWriteException
	 */
	public int count() throws ReadWriteException
	{
		InstockPlanHandler handler = new InstockPlanHandler(wConn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();
		// 検索条件をセットし、件数取得を行う
		setSeachKey(searchKey);
		return handler.count(searchKey);

	}
	
	/**
	 * クロス/DC入荷予定リスト用CSVファイルを作成し、印刷を実行します。 <BR>
	 * 結果が1件以上なら、帳票ファイルを作成します。0件だった場合はfalseを返して終了します。 <BR>
	 * 印刷処理を実行します。 <BR>
	 * 印刷処理が正常に終了したら、trueを返します。 <BR>
	 * 印刷処理中にエラーが発生した場合はfalseを返します。 <BR>
	 */
	public boolean startPrint()
	{
		InstockPlanReportFinder reportFinder = null;

		try
		{
			InstockPlanSearchKey searchKey = new InstockPlanSearchKey();
			
			// 検索条件をセット
			setSeachKey(searchKey);

			// 情報取得順をセット		
			searchKey.setConsignorCodeCollect("");
			searchKey.setConsignorNameCollect("");
			searchKey.setPlanDateCollect("");
			searchKey.setSupplierCodeCollect("");
			searchKey.setSupplierName1Collect("");
			searchKey.setInstockTicketNoCollect("");
			searchKey.setInstockLineNoCollect("");
			searchKey.setItemCodeCollect("");
			searchKey.setItemName1Collect("");
			searchKey.setEnteringQtyCollect("");
			searchKey.setBundleEnteringQtyCollect("");
			searchKey.setCasePieceFlagCollect("");
			searchKey.setPlanQtyCollect("");
			searchKey.setResultQtyCollect("");
			searchKey.setUseByDateCollect("");
			searchKey.setTcdcFlagCollect("");
			searchKey.setStatusFlagCollect("");
			
			// ソート順をセット		
			searchKey.setPlanDateOrder(1, true);
			searchKey.setSupplierCodeOrder(2, true);
			searchKey.setInstockTicketNoOrder(3, true);
			searchKey.setInstockLineNoOrder(4, true);
			searchKey.setItemCodeOrder(5, true);

			reportFinder = new InstockPlanReportFinder(wConn);

			// データがない場合は印刷処理を行わない。
			if (reportFinder.search(searchKey) <= 0)
			{
				// 6003010 = 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}
			
			// 表示する荷主名称確定
			getDisplayConsignorName();
			
			// 出力ファイルを作成
			if (!createPrintWriter(FNAME_INSTOCK_PLAN))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_INSTOCK_PLAN);

			// 検索結果がなくなるまでデータファイルの内容を作成する。
			InstockPlan[] instockPlan = null;
			while (reportFinder.isNext())
			{
				// 検索を100件ずつ表示していきます。
				instockPlan = (InstockPlan[]) reportFinder.getEntities(100);

				// データファイルに出力する内容を作成する。
				for (int i = 0; i < instockPlan.length; i++)
				{
					wStrText.append(re + "");

					// 荷主コード
					wStrText.append(ReportOperation.format(instockPlan[i].getConsignorCode()) + tb);
					// 荷主名称
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					// 入荷予定日
					wStrText.append(ReportOperation.format(instockPlan[i].getPlanDate()) + tb);
					// 仕入先コード
					wStrText.append(ReportOperation.format(instockPlan[i].getSupplierCode()) + tb);
					// 仕入先名称
					wStrText.append(ReportOperation.format(instockPlan[i].getSupplierName1()) + tb);
					// 伝票No
					wStrText.append(ReportOperation.format(instockPlan[i].getInstockTicketNo()) + tb);
					// 行No
					wStrText.append(instockPlan[i].getInstockLineNo() + tb);
					// 商品コード
					wStrText.append(ReportOperation.format(instockPlan[i].getItemCode()) + tb);
					// 商品名称
					wStrText.append(ReportOperation.format(instockPlan[i].getItemName1()) + tb);
					// ケース入数
					wStrText.append(instockPlan[i].getEnteringQty() + tb);
					// ボール入数
					wStrText.append(instockPlan[i].getBundleEnteringQty() + tb);
					
					// 予定ケース数
					wStrText.append(DisplayUtil.getCaseQty(instockPlan[i].getPlanQty(), instockPlan[i].getEnteringQty(), instockPlan[i].getCasePieceFlag()) + tb);
					// 予定ピース数
					wStrText.append(DisplayUtil.getPieceQty(instockPlan[i].getPlanQty(), instockPlan[i].getEnteringQty(), instockPlan[i].getCasePieceFlag()) + tb);
					// 実績ケース数
					wStrText.append(DisplayUtil.getCaseQty(instockPlan[i].getResultQty(), instockPlan[i].getEnteringQty(), instockPlan[i].getCasePieceFlag()) + tb);
					// 実績ピース数
					wStrText.append(DisplayUtil.getPieceQty(instockPlan[i].getResultQty(), instockPlan[i].getEnteringQty(), instockPlan[i].getCasePieceFlag()) + tb);
					// 区分
					wStrText.append(ReportOperation.format(DisplayUtil.getTcDcValue(instockPlan[i].getTcdcFlag())) + tb);
					// 状態
					wStrText.append(ReportOperation.format(DisplayUtil.getInstockPlanStatusValue(instockPlan[i].getStatusFlag())));					

					// データをファイルに出力する。
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}

			// ストリームを閉じる
			wPWriter.close();

			// UCXSingleを実行。（印刷実行）
			if (!executeUCX(JOBID_INSTOCK_PLAN))
			{
				// 印刷に失敗しました。ログを参照してください。
				return false;
			}

			// 印刷に成功した場合、データファイルをバックアップフォルダへ移動する。
			ReportOperation.createBackupFile(wFileName);
		}
		catch (ReadWriteException re)
		{
			// 6007034 = 印刷に失敗しました。ログを参照してください。
			setMessage("6007034");

			return false;
		}
		finally
		{
			try
			{
				// オープンしたデータベースカーソルのクローズ処理を行う。
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				// データベースエラーが発生しました。ログを参照してください。
				setMessage("6007002");
				return false;
			}
		}
		return true;
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 検索条件をセットします。<BR>
	 * @param searchKey 検索キー
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void setSeachKey(InstockPlanSearchKey searchKey) throws ReadWriteException
	{
		// 荷主コード
		searchKey.setConsignorCode(wConsignorCode);
		
		// 作業状態 削除以外
		searchKey.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_DELETE, "!=");
		
		// 開始入荷予定日
		if (!StringUtil.isBlank(wFromDate))
		{
			searchKey.setPlanDate(wFromDate, ">=");
		}
		// 終了入荷予定日		
		if (!StringUtil.isBlank(wToDate))
		{
			searchKey.setPlanDate(wToDate, "<=");
		}
		// 仕入先コード
		if (!StringUtil.isBlank(wSupplierCode))
		{
			searchKey.setSupplierCode(wSupplierCode);
		}
		// 入荷伝票№
		if (!StringUtil.isBlank(wFromTicketNo))
		{
			searchKey.setInstockTicketNo(wFromTicketNo, ">=");
		}
		// 入荷伝票行№
		if (!StringUtil.isBlank(wToTicketNo))
		{
			searchKey.setInstockTicketNo(wToTicketNo, "<=");
		}
		// 商品コード
		if (!StringUtil.isBlank(wItemCode))
		{
			searchKey.setItemCode(wItemCode);
		}
		// 作業状態
		if (!StringUtil.isBlank(wStatusFlag))
		{
			if (!InstockReceiveParameter.STATUS_FLAG_ALL.equals(wStatusFlag))
			{
				searchKey.setStatusFlag(wStatusFlag);
			}
		}
		
		// TC/DCフラグ
		if (!StringUtil.isBlank(wTcdcFlag))
		{
			if (InstockReceiveParameter.TCDC_FLAG_TC.equals(wTcdcFlag))
			{
				searchKey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC);
			}
			else if (InstockReceiveParameter.TCDC_FLAG_DC.equals(wTcdcFlag))
			{
				searchKey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
			}
			else if (InstockReceiveParameter.TCDC_FLAG_CROSSTC.equals(wTcdcFlag))
			{
				searchKey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
			}
			else if (InstockReceiveParameter.TCDC_FLAG_ALL.equals(wTcdcFlag))
			{
				searchKey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC, "!=");
			}
		}
	}

	// Private methods -----------------------------------------------
	/**
	 * 荷主名称を取得するためのメソッドです。<BR>
	 * <BR>
	 * 荷主コードに対して、複数の荷主名称があることが考えられるので
	 * 登録日時が一番新しいレコードの荷主名称を取得します。<BR>
	 * 
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private void getDisplayConsignorName() throws ReadWriteException
	{
		// Finderインスタンス生成
		InstockPlanReportFinder consignorFinder = new InstockPlanReportFinder(wConn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();
		
		// 検索条件をセットします
		setSeachKey(searchKey);
		
		searchKey.setRegistDateOrder(1, false);
	
		// 荷主名称検索
		consignorFinder.open();
		
		if (consignorFinder.search(searchKey) > 0)
		{
			InstockPlan[] InstockPlan = (InstockPlan[]) consignorFinder.getEntities(1);

			if (InstockPlan != null && InstockPlan.length != 0)
			{
				wConsignorName = InstockPlan[0].getConsignorName();
			}
		}
		consignorFinder.close();
	}
}
