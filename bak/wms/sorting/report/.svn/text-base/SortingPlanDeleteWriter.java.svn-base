//$Id: SortingPlanDeleteWriter.java,v 1.4 2006/12/13 09:01:14 suresh Exp $
package jp.co.daifuku.wms.sorting.report;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 仕分予定削除リストの帳票用データファイルを作成し、印刷するためのクラスです。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.印刷編集処理(<CODE>startPrint()</CODE>メソッド) <BR>
 * <DIR>
 *   印刷処理を行う際に使用するＣＳＶファイルの作成を行います。 <BR>
 * <BR>
 *   <DIR>
 *   ＜入力データ＞ <BR>
 *     <DIR>
 *     * 必須入力 <BR>
 *     <BR>
 *     荷主コード :ConsignorCode[]  * <BR>
 *     仕分予定日 :PlanDate[]       * <BR>
 *     更新日時   :LastUpdateDate[] * <BR>
 *     </DIR>
 *   ＜返却データ＞ <BR>
 *     <DIR>
 *     印刷状態 <BR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.スケジュール処理<CODE>(SortingWorkInfoPlanDeleteSCH)</CODE>クラスより得たパラメータより <BR>
 * 仕分予定情報<CODE>(Dnsortingplan)</CODE>を検索、 <BR>
 * 該当データが存在する場合、抽出した検索結果を元に帳票用データファイルの作成をし、 <BR>
 * 印刷実行クラスを呼び出します。 <BR>
 * 該当データが存在しない場合は、帳票データを作成せず印刷状態を不可として返却します。 <BR>
 * <BR>
 * <DIR>
 *   ＜検索条件＞ <BR>
 *   <DIR>
 *     荷主コード <BR>
 *     仕分予定日 <BR>
 *     更新日時 以降となるデータ <BR>
 *   </DIR>
 *   ＜抽出項目＞ <BR>
 *   <DIR>
 *     荷主コード <BR>
 *     荷主名称 <BR>
 *     仕分予定日 <BR>
 *     クロス/DC区分 <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     仕分先数 <BR>
 *     予定総数 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     予定ケース数 <BR>
 *     予定ピース数 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/01</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 09:01:14 $
 * @author  $Author: suresh $
 */
public class SortingPlanDeleteWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode[] = null ;

	/**
	 * 仕分予定日
	 */
	private String wPlanDate[] = null ;
	
	/**
	 * 更新日時
	 */
	private Date wLastUpdateDate[] = null ;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 09:01:14 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * RetrievalItemStartWriter オブジェクトを構築します。
	 * @param wConn <CODE>Connection</CODE>
	 * @param locale <CODE>Locale</CODE>
	 */
	public SortingPlanDeleteWriter(Connection conn)
	{
		super(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * 荷主コードに値をセットします。
	 * @param consignorcode セットする荷主コード
	 */
	public void setConsignorCode(String[] consignorcode)
	{
		wConsignorCode = consignorcode;
	}

	/**
	 * 荷主コードを取得します。
	 * @return 荷主コード
	 */
	public String[] getConsignorCode()
	{
		return wConsignorCode;
	}

	/**
	 * 仕分予定日に値をセットします。
	 * @param plandate セットする仕分予定日
	 */
	public void setPlanDate(String[] plandate)
	{
		wPlanDate = plandate;
	}

	/**
	 * 仕分予定日を取得します。
	 * @return 仕分予定日
	 */
	public String[] getPlanDate()
	{
		return wPlanDate;
	}

	/**
	 * 更新日時に値をセットします。
	 * @param lastupdatedate セットする更新日時
	 */
	public void setLastUpdateDate(Date[] lastupdatedate)
	{
		wLastUpdateDate = lastupdatedate;
	}

	/**
	 * 更新日時を取得します。
	 * @return 更新日時
	 */
	public Date[] getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	/**
	 * 仕分予定削除用CSVファイルを作成し、印刷実行クラスを呼び出します。 <BR>
	 * パラメータで渡された検索条件より仕分予定情報<CODE>(Dnsortingplan)</CODE>を検索します。 <BR>
	 * 検索結果が1件未満の場合は印刷を行いません。 <BR>
	 * 印刷に成功した場合true、失敗した場合はfalseを返します。 <BR>
	 * <BR>
	 * @return boolean 印刷が成功したかどうか。
	 */
	public boolean startPrint()
	{
		SortingPlanReportFinder sortingPlanReportFinder =  new SortingPlanReportFinder(wConn);

		try
		{
			SortingPlanSearchKey sortingPlanSearchKey = new SortingPlanSearchKey(); 
			boolean pHeader_Flag = true ;
			boolean pPrintData_Flag = false ;
			Date wcheckDate = wLastUpdateDate[0];
			
			// 検索条件獲得
			// パラメータで受け取ったデータ分、処理を繰り返します。
			for (int cdt_cnt = 0; cdt_cnt < wConsignorCode.length ; cdt_cnt++)
			{
				// 検索条件をセット
				sortingPlanSearchKey.KeyClear() ;
				// 荷主コード
				sortingPlanSearchKey.setConsignorCode(wConsignorCode[cdt_cnt]);
				// 仕分予定日
				sortingPlanSearchKey.setPlanDate(wPlanDate[cdt_cnt]);
				// 更新日時
				sortingPlanSearchKey.setLastUpdateDate(wLastUpdateDate[cdt_cnt], ">=");
				// 状態フラグ（削除のみ）
				sortingPlanSearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE);
				
				// 印字順(荷主コード、予定日、商品コード、TC/DC区分)
				sortingPlanSearchKey.setConsignorCodeOrder(1,true);
				sortingPlanSearchKey.setPlanDateOrder(2,true);
				sortingPlanSearchKey.setItemCodeOrder(3,true);
				sortingPlanSearchKey.setTcdcFlagOrder(4,false);
				
				// 集約処理
				sortingPlanSearchKey.setConsignorCodeGroup(1);
				sortingPlanSearchKey.setPlanDateGroup(2);
				sortingPlanSearchKey.setItemCodeGroup(3);
				sortingPlanSearchKey.setTcdcFlagGroup(4);
				
				// 取得項目
				sortingPlanSearchKey.setConsignorCodeCollect("");
				sortingPlanSearchKey.setPlanDateCollect("");
				sortingPlanSearchKey.setTcdcFlagCollect("");
				sortingPlanSearchKey.setItemCodeCollect("");
				sortingPlanSearchKey.setPlanQtyCollect("SUM");
				
				// カーソルオープン
				sortingPlanReportFinder.open();
			
				// 検索実行
				int count = sortingPlanReportFinder.search(sortingPlanSearchKey);
				
				// 対象データなし
				if (count <= 0 )
				{
					continue;
				}
				
				// 初回のみ処理を行います。
				if (pHeader_Flag == true)
				{
					// 出力ファイルを作成			
					if(!createPrintWriter(FNAME_PICKING_DELETE))
					{
						return false;	
					}

					pHeader_Flag = false ;
                    
                    // 出力ファイルにヘッダーを作成
                    wStrText.append(HEADER_PICKING_DELETE);
				}

				// 検索結果がなくなるまで、100件ごとにデータファイルを作成する。
				SortingPlan[] sortingPlan = null ;
				while(sortingPlanReportFinder.isNext())
				{
					// 検索結果から、100件まで取得
					sortingPlan = (SortingPlan[])sortingPlanReportFinder.getEntities(100);

					for (int i = 0; i < sortingPlan.length; i++)
					{						
						SortingPlanSearchKey recSearchKey = new SortingPlanSearchKey();
						SortingPlanSearchKey countSearchKey = new SortingPlanSearchKey();
						SortingPlanHandler sortingPlanHandler = new SortingPlanHandler(wConn);
						
						// 荷主名称・商品名称・ケース入数・ボール入数を取得する。
						// 仕分予定情報内の登録日時の最新情報より。
						// 荷主コード＋仕分予定日＋TC/DC区分・商品コードが一致。
						recSearchKey.KeyClear();
						recSearchKey.setConsignorCode(sortingPlan[i].getConsignorCode());
						recSearchKey.setPlanDate(sortingPlan[i].getPlanDate());
						recSearchKey.setTcdcFlag(sortingPlan[i].getTcdcFlag());
						recSearchKey.setItemCode(sortingPlan[i].getItemCode());
						recSearchKey.setLastUpdateDate(wcheckDate, ">=");
						recSearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE);
						
						// 登録日時の降順に取得する。
						recSearchKey.setRegistDateOrder(1, false);
						
						SortingPlan[] recsortingPlan = (SortingPlan[])sortingPlanHandler.find(recSearchKey);

						// 荷主コード＋仕分予定日＋TC/DC区分・商品コードが一致。
						// 出荷先コードの件数を取得する。
						countSearchKey.KeyClear();
						countSearchKey.setConsignorCode(sortingPlan[i].getConsignorCode());
						countSearchKey.setPlanDate(sortingPlan[i].getPlanDate());
						countSearchKey.setTcdcFlag(sortingPlan[i].getTcdcFlag());
						countSearchKey.setItemCode(sortingPlan[i].getItemCode());
						countSearchKey.setLastUpdateDate(wcheckDate, ">=");
						countSearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE);
						
						countSearchKey.setCustomerCodeGroup(1);
						countSearchKey.setCustomerCodeCollect("");
						
						int wPlanCount = sortingPlanHandler.count(countSearchKey);

						wStrText.append(re + "");

						// 荷主コード
						wStrText.append(ReportOperation.format(sortingPlan[i].getConsignorCode()) + tb);
						// 荷主名
						wStrText.append(ReportOperation.format(recsortingPlan[0].getConsignorName()) + tb);
						// 仕分予定日
						wStrText.append(ReportOperation.format(sortingPlan[i].getPlanDate()) + tb);
						// 商品コード
						wStrText.append(ReportOperation.format(sortingPlan[i].getItemCode()) + tb);
						// 商品名称
						wStrText.append(ReportOperation.format(recsortingPlan[0].getItemName1()) + tb);
						// TC/DC区分名称
						wStrText.append(ReportOperation.format(DisplayUtil.getTcDcValue(sortingPlan[i].getTcdcFlag())) + tb);
						// ホスト予定総数
						wStrText.append(sortingPlan[i].getPlanQty() + tb);
						// ケース入数
						wStrText.append(ReportOperation.format(Integer.toString(recsortingPlan[0].getEnteringQty())) + tb);
						// ボール入数
						wStrText.append(recsortingPlan[0].getBundleEnteringQty() + tb);
						// 予定ケース数
						wStrText.append(DisplayUtil.getCaseQty(sortingPlan[i].getPlanQty(),recsortingPlan[0].getEnteringQty()) + tb);
						// 予定ピース数
						wStrText.append(DisplayUtil.getPieceQty(sortingPlan[i].getPlanQty(),recsortingPlan[0].getEnteringQty()) + tb);
						// 仕分先数
						wStrText.append(wPlanCount);
						// 書き込み
						wPWriter.print(wStrText);
						
						wStrText.setLength(0);

						// １件でもデータ書き込みがあった場合はフラグ解除
						pPrintData_Flag = true;
					}
				}
			}

			if (wPWriter != null)
			{
				// ストリームクローズ
				wPWriter.close();
			}

			if (pPrintData_Flag == false)
			{
				// 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}

			// UCXSingleを実行
			if (!executeUCX(JOBID_PICKING_DELETE))
			{
				// 印刷に失敗しました。ログを参照してください。
				return false;
			}


			// データファイルをバックアップフォルダへ移動
			ReportOperation.createBackupFile(wFileName);

		}

		catch (ReadWriteException e)
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
				sortingPlanReportFinder.close();
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

	// Private methods -----------------------------------------------

}
//end of class
