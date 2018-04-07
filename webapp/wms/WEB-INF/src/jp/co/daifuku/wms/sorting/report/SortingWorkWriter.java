// $Id: SortingWorkWriter.java,v 1.4 2006/12/13 09:01:13 suresh Exp $
package jp.co.daifuku.wms.sorting.report;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * 仕分作業リストの帳票用データファイルを作成し、印刷実行クラスを呼び出すためのクラスです。<BR>
 * スケジュールクラスでセットされた内容で作業情報を検索し、帳票用データファイルを作成します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 帳票用データファイル作成処理(<CODE>startPrint()</CODE>メソッド)<BR>
 * <DIR>
 *   作業情報を検索します。<BR>
 *   該当データが存在しなかった場合、帳票用データファイルは作成しません。<BR>
 *   該当データが存在する場合、帳票用データファイルを作成し、印刷実行クラスを呼び出します。<BR>
 * <BR>
 *   ＜検索条件＞ <DIR>
 * 		荷主コード <BR>
 * 		仕分予定日<BR>
 * 		作業区分 仕分 :04<BR>
 * 		状態フラグ 未開始 : 0<BR>
 * <BR>
 *   ＜抽出項目＞ <DIR>
 * 		荷主コード <BR>
 * 		荷主名 <BR>
 * 		商品コード <BR>
 * 		商品名 <BR>
 * 		ケース/ピース区分 <BR>
 * 		作業区分<BR>
 * 		予定総数<BR>
 * 		仕分場所<BR>
 * 		出荷先コード<BR>
 * 		出荷先名称<BR>
 * 		作業<BR>
 * 		総数<BR>
 * 		ケース入数<BR>
 * 		ボール入数<BR>
 * 		予定ケース数<BR>
 * 		予定ピース数<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 09:01:13 $
 * @author  $Author: suresh $
 */
public class SortingWorkWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	// 荷主コード
	private String wConsignorCode = "";

	// 仕分予定日
	private String wPlanDate = "";

	// 商品コード
	private String wItemCode = "";

	// ケース/ピース区分
	private String wCasePieceFlag = "";

	// クロス/ＤＣ　フラグ
	private String wTcdcFlag = "";

	// 作業状態
	private String wStatusFlag = "";

	// 作業No
	private String wJobNo[] = null;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 09:01:13 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * SortingWorkWriter オブジェクトを構築します。<BR>
	 * コネクションとロケールのセットを行います。<BR>
	 * @param conn <CODE>Connection</CODE> データベースとのコネクションオブジェクト
	 */
	public SortingWorkWriter(Connection conn)
	{
		super(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * 荷主コードをセットする。
	 * 
	 * @param consignorCode
	 */
	public void setConsignorCode(String consignorCode)
	{
		wConsignorCode = consignorCode;
	}

	/**
	 * @return 荷主コード
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	/**
	 * @return 仕分予定日
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	/**
	 * 仕分予定日をセットする。
	 * 
	 * @param PlanDate
	 */
	public void setPlanDate(String planDate)
	{
		wPlanDate = planDate;
	}

	/**
	 * 商品コードをセットする。
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode)
	{
		wItemCode = itemCode;
	}

	/**
	 * @return 商品コード
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	/**
	 * ケース/ピース区分をセットする。
	 * 
	 * @param casePieceFlag
	 */
	public void setCasePieceFlag(String casePieceFlag)
	{
		wCasePieceFlag = casePieceFlag;
	}

	/**
	 * @return ケース/ピース区分
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	/**
	 * クロス/ＤＣをセットする。
	 * 
	 * @param statusFlag
	 */
	public void setTcdcFlag(String tcdcFlag)
	{
		wTcdcFlag = tcdcFlag;
	}

	/**
	 * @return クロス/ＤＣ
	 */
	public String getTcdcFlag()
	{
		return wTcdcFlag;
	}

	/**
	 * 作業状態をセットする。
	 * @param statusFlag
	 */
	public void setStatusFlag(String statusFlag)
	{
		wStatusFlag = statusFlag;
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
	 * 作業No.に値をセットします。
	 * @param jobNo セットする作業No.
	 */
	public void setJobNo(String[] jobNo)
	{
		wJobNo = jobNo;
	}

	/**
	 * 作業No.を取得します。
	 * @return 作業No.
	 */
	public String[] getJobNo()
	{
		return wJobNo;
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
		WorkingInformationHandler instockHandle = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		// 検索条件をセットし、件数取得を行う
		searchKey = setSortingPlanWorkInfoSearchKey(searchKey);
		return instockHandle.count(searchKey);

	}
	
	/**
	 * 仕分作業リスト用CSVファイルを作成し、印刷実行クラスを呼び出します。<BR>
	 * 画面で入力された検索条件より作業情報を検索します。
	 * 検索結果が1件未満の場合は印刷を行いません。<BR>
	 * 印刷に成功した場合true、失敗した場合はfalseを返します。<BR>
	 * 以下の順で処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.検索条件をセットし、検索処理を行います。<BR>
	 *   2.検索結果が1件未満の場合は印刷を行いません。<BR>
	 *   3.100件づつ検索結果を取得し、ファイルに出力していきます。<BR>
	 *   4.印刷を行います。<BR>
	 *   5.印刷が正常に行われた場合、データファイルをバックアップフォルダに移動します。<BR>
	 *   6.印刷が成功したかどうかを返します。<BR>
	 * </DIR>
	 * 
	 * @return boolean 印刷が成功したかどうか。
	 */
	public boolean startPrint()
	{
		WorkingInformationReportFinder sFinder = new WorkingInformationReportFinder(wConn);
		try
		{
			// 検索実行
			WorkingInformationSearchKey sKey = new WorkingInformationSearchKey();

			// 検索キーをセット
			sKey = setSortingPlanWorkInfoSearchKey(sKey);
			
			// 検索順のセットを行う
			sKey.setItemCodeOrder(1, true);
			sKey.setTcdcFlagOrder(2, false);
			sKey.setLocationNoOrder(3, true);
			sKey.setCustomerCodeOrder(4, true);
			sKey.setCasePieceFlagOrder(5, true);
					 
			// サーチキーを使ってデータチェック
			// データがない場合は印刷処理を行わない。
			if (sFinder.search(sKey) <= 0)
			{
				// 6003010 = 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}
			
			// 出力ファイルを作成			
			if(!createPrintWriter(FNAME_PICKING_SETCUSTOMER))
			{
				return false;	
			}
			
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_PICKING_SETCUSTOMER);
            
			// 荷主名称を取得する。
			String consignorName = getDisplayConsignorName();
			String itemCode = "";
			String itemName = "";
			String tcdcFlag = "";
			int totalQty = 0;

			WorkingInformation[] workInfo = null;
			// 検索結果がなくなるまでデータファイルの内容を作成する。
			while (sFinder.isNext())
			{
				// 検索結果を100件づつ出力していく。
				workInfo = (WorkingInformation[]) sFinder.getEntities(100);

				// データファイルに出力する内容を作成する。
				for (int i = 0; i < workInfo.length; i++)
				{

					// 商品コード・TC/DC区分が前と変わった場合
					if (!itemCode.equals(workInfo[i].getItemCode()) 
							&& !tcdcFlag.equals(workInfo[i].getTcdcFlag()))
					{						
						// 商品名称を取得する
						itemCode = workInfo[i].getItemCode();
						itemName = getDisplayItemName(itemCode, workInfo[i].getTcdcFlag());
						tcdcFlag = workInfo[i].getTcdcFlag();
						totalQty = getDisplayTotalQty(itemCode, tcdcFlag);
					}

					// 商品コードが前とかわった場合
					else if (!itemCode.equals(workInfo[i].getItemCode()))
					{
						// 商品名称を取得する
						itemCode = workInfo[i].getItemCode();
						itemName = getDisplayItemName(itemCode, workInfo[i].getTcdcFlag());
						totalQty = getDisplayTotalQty(itemCode, workInfo[i].getTcdcFlag());		
					}

					// TC/DC区分が前と変わった場合
					else if (!tcdcFlag.equals(workInfo[i].getTcdcFlag()))
					{
						// 予定総数を取得する
						tcdcFlag = workInfo[i].getTcdcFlag();
						totalQty = getDisplayTotalQty(itemCode, tcdcFlag);
					}
					
					wStrText.append(re + "");

					// 荷主コード
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					// 荷主名
					wStrText.append(ReportOperation.format(consignorName) + tb);
					// 仕分予定日
					wStrText.append(ReportOperation.format(workInfo[i].getPlanDate()) + tb);
					// 商品コード
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					// 商品名称
					wStrText.append(ReportOperation.format(itemName) + tb);
					// 作業形態(全て・ケース・ピース)
					if (wCasePieceFlag.equals(SortingParameter.CASEPIECE_FLAG_CASE))
					{
						// ケース
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_CASE)) + tb);
					}
					else if (wCasePieceFlag.equals(SortingParameter.CASEPIECE_FLAG_PIECE))
					{
						// ピース
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_PIECE)) + tb);
					}
					else
					{
						// LBL-W0346=全て
						wStrText.append(ReportOperation.format(DisplayText.getText("LBL-W0346")) + tb);
					}
					// 作業区分(クロス・DC)
					wStrText.append(ReportOperation.format(DisplayUtil.getTcDcValue(workInfo[i].getTcdcFlag())) + tb);
					// 予定総数
					wStrText.append(totalQty + tb);
					// 仕分場所
					wStrText.append(ReportOperation.format(workInfo[i].getLocationNo()) + tb);
					// 出荷先コード
					wStrText.append(ReportOperation.format(workInfo[i].getCustomerCode()) + tb);
					// 出荷先名称
					wStrText.append(ReportOperation.format(workInfo[i].getCustomerName1()) + tb);
					// ケース/ピース
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(workInfo[i].getCasePieceFlag())) + tb);
					// 仕分総数
					wStrText.append(workInfo[i].getPlanEnableQty() + tb);
					// ケース入数
					wStrText.append(workInfo[i].getEnteringQty() + tb);
					// ピース入数
					wStrText.append(workInfo[i].getBundleEnteringQty() + tb);
					// 仕分ケース数
					wStrText.append(DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					// 仕分ピース数
					wStrText.append(DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);

					// データをファイルに出力する。
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}
			// ストリームを閉じる
			wPWriter.close();

			// UCXSingleを実行。（印刷実行）
			if (!executeUCX(JOBID_PICKING_SETCUSTOMER))
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
				sFinder.close();
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
	/**
	 * 作業情報検索キーに検索条件をセットするためのメソッドです。<BR>
	 * 
	 * @param sKey WorkingInformationSearchKey サーチキー
	 * @return WorkingInformationSearchKey サーチキー
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private WorkingInformationSearchKey setSortingPlanWorkInfoSearchKey(WorkingInformationSearchKey sKey) throws ReadWriteException
	{
		if (wJobNo != null)
		{
			if (wJobNo.length > 0)
			{
				sKey.setJobNo(wJobNo);
			}
		}
		else
		{
			sKey = setSortingPlanWorkInfoSearchKey("", "");
		}
		
		// 情報取得順をセット
		// 荷主コード
		sKey.setConsignorCodeCollect("");
		// 荷主名
		sKey.setConsignorNameCollect("");
		// 仕分予定日
		sKey.setPlanDateCollect("");
		// 商品コード
		sKey.setItemCodeCollect("");
		// 商品名
		sKey.setItemName1Collect("");
		// ケース/ピース区分(作業形態)
		sKey.setCasePieceFlagCollect("");
		// クロス/DC
		sKey.setTcdcFlagCollect("") ;
		// 仕分場所
		sKey.setLocationNoCollect("");
		// 出荷先コード
		sKey.setCustomerCodeCollect("");
		// 出荷先名称
		sKey.setCustomerName1Collect("");
		// 予定数(仕分総数)
		sKey.setPlanQtyCollect("");
		// ケース入数
		sKey.setEnteringQtyCollect("");
		// ピース入数
		sKey.setBundleEnteringQtyCollect("");
		// 作業可能数
		sKey.setPlanEnableQtyCollect("");
		//作業区分　仕分(04)
		sKey.setJobType(SortingPlan.JOB_TYPE_SORTING);

		// ソート順をセット
		// 商品コード
		sKey.setItemCodeOrder(1, true);
		// 仕分場所
		sKey.setLocationNoOrder(2, true);
		// 出荷先コード
		sKey.setCustomerCodeOrder(3, true);
		// 区分
		sKey.setWorkFormFlagOrder(4, true);

		return sKey;
	}

	/**
	 * 登録日時の一番新しい荷主名称を取得します。
	 * @return 荷主名称
	 * @throws ReadWriteException データベースとの接続に異常が発生した場合に通知されます。
	 */
	private String getDisplayConsignorName() throws ReadWriteException
	{
		// Finderインスタンス生成
		WorkingInformationReportFinder consignorFinder = new WorkingInformationReportFinder(wConn);
		WorkingInformationSearchKey sKey = new WorkingInformationSearchKey();
		
		sKey = setSortingPlanWorkInfoSearchKey(sKey);

		//Collect Condition
		sKey.setConsignorNameCollect("");
		sKey.setRegistDateOrder(1, false);

		String consignorName = "";
		// 荷主名称を取得する
		consignorFinder.open();

		if (consignorFinder.search(sKey) > 0)
		{
			WorkingInformation[] wInfo = (WorkingInformation[]) consignorFinder.getEntities(1);

			if (wInfo != null && wInfo.length != 0)
			{
				consignorName = wInfo[0].getConsignorName();
			}
		}
		consignorFinder.close();
		
		return consignorName;
	}

	/**
	 * 登録日時の最も新しい商品名称を取得します。
	 * 
	 * @param pItemCode 商品コード
	 * @param pCrossDc TC/DC区分
	 * @return 商品名称
	 * @throws ReadWriteException データベースとの接続に異常が発生した場合に通知されます。
	 */
	private String getDisplayItemName(String pItemCode, String pCrossDc) throws ReadWriteException
	{
		// Finderインスタンス生成
		WorkingInformationReportFinder consignorFinder = new WorkingInformationReportFinder(wConn);
		WorkingInformationSearchKey sKey = setSortingPlanWorkInfoSearchKey(pItemCode, pCrossDc);

		//Collect Condition
		sKey.setItemName1Collect("");
		sKey.setRegistDateOrder(1, false);

		String itemName = "";
		// 商品名称を取得する
		consignorFinder.open();

		if (consignorFinder.search(sKey) > 0)
		{
			WorkingInformation[] wInfo = (WorkingInformation[]) consignorFinder.getEntities(1);

			if (wInfo != null && wInfo.length != 0)
			{
				itemName = wInfo[0].getItemName1();
			}
		}
		consignorFinder.close();
		
		return itemName;
	}

	/**
	 * 荷主コード、仕分予定日、作業形態(全て・ケース・ピース)、商品コード、作業区分(クロス・DC)単位で
	 * 作業数の合計を求めます。
	 * 
	 * @param pItemCode 商品コード
	 * @param pCrossDc TC/DC区分
	 * @throws ReadWriteException データベースとの接続に異常が発生した場合に通知されます。
	 */
	private int getDisplayTotalQty(String pItemCode, String pCrossDc) throws ReadWriteException
	{
		// Finderインスタンス生成
		WorkingInformationReportFinder totalQtyFinder = new WorkingInformationReportFinder(wConn);
		WorkingInformationSearchKey sKey = setSortingPlanWorkInfoSearchKey(pItemCode, pCrossDc);

		//Collect Condition
		sKey.setPlanQtyCollect("SUM");

		int totalQty = 0;
		// 予定総数を取得
		totalQtyFinder.open();

		if (totalQtyFinder.search(sKey) > 0)
		{
			WorkingInformation[] wInfo = (WorkingInformation[]) totalQtyFinder.getEntities(1);

			if (wInfo != null && wInfo.length != 0)
			{
				totalQty = wInfo[0].getPlanQty();
			}
		}
		totalQtyFinder.close();
		
		return totalQty;
		
	}
	
	/**
	 * 作業情報検索キーをセットします。<BR>
	 * 商品コード、クロス/DC区分に入力があった場合は、そちらを検索キーとして使用します。<BR>
	 * 
	 * @param pItemCode 商品コード
	 * @param pCrossDc クロス/DC
	 * @return 作業情報検索キー
	 * @throws ReadWriteException データベースとの接続に異常が発生した場合に通知されます。
	 */
	private WorkingInformationSearchKey setSortingPlanWorkInfoSearchKey(String pItemCode, String pCrossDc) throws ReadWriteException
	{
		WorkingInformationSearchKey workKey = new WorkingInformationSearchKey();
		
		if (wJobNo != null)
		{
			if (wJobNo.length > 0)
			{
				workKey.setJobNo(wJobNo);
				workKey.setItemCode(pItemCode);
				workKey.setTcdcFlag(pCrossDc);
			}
		}
		else
		{
			// 荷主コード
			if (!StringUtil.isBlank(wConsignorCode))
			{
				workKey.setConsignorCode(this.getConsignorCode());
			}
			// 仕分予定日
			if (!StringUtil.isBlank(wPlanDate))
			{
				workKey.setPlanDate(this.getPlanDate());
			}
			// 商品コード
			if (!StringUtil.isBlank(pItemCode))
			{
				workKey.setItemCode(pItemCode);
			}
			else if (!StringUtil.isBlank(wItemCode))
			{
				workKey.setItemCode(this.getItemCode());
			}
			// ケース/ピース区分
			if (this.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_NOTHING))
			{
				// ケース/ピース区分 指定なし
				workKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
			}
			else if (this.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				// ケース/ピース区分 ケース
				workKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE);
			}
			else if (this.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				// ケース/ピース区分 ピース
				workKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
			else if (this.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_ALL))
			{
				// ケース/ピース区分 全て
				//do nothing
			}
			// クロス/ＤＣ
			if (!StringUtil.isBlank(pCrossDc))
			{
				// クロス/DC
				workKey.setTcdcFlag(pCrossDc);
			}
			else if (!StringUtil.isBlank(wTcdcFlag))
			{
				if (this.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
				{
					// クロス
					workKey.setTcdcFlag(SystemDefine.TCDC_FLAG_CROSSTC);
				}
				else if (this.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
				{
					// DC
					workKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
				}
				else if (this.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_ALL))
				{
					// 全ての場合はＴＣ以外を検索(仕分はTCは存在しないため)
					workKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC, "!=");
				}
			}
			// 作業状態
			if (!StringUtil.isBlank(wStatusFlag))
			{
				if (wStatusFlag.equals(SortingParameter.STATUS_FLAG_UNSTARTED))
				{
					// 未開始
					workKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);
				}
				else if (wStatusFlag.equals(SortingParameter.STATUS_FLAG_WORKING))
				{
					// 作業中
					workKey.setStatusFlag(SortingPlan.STATUS_FLAG_NOWWORKING);
				}
				else if (wStatusFlag.equals(SortingParameter.STATUS_FLAG_COMPLETION))
				{
					// 完了
					workKey.setStatusFlag(SortingPlan.STATUS_FLAG_COMPLETION);
				}
				else if (wStatusFlag.equals(SortingParameter.STATUS_FLAG_ALL))
				{
					// 全ての場合、削除以外を検索
					workKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
				}
			}
			//作業区分　仕分(04)
			workKey.setJobType(SortingPlan.JOB_TYPE_SORTING);
		}
		
		return workKey;

	}
	
}
