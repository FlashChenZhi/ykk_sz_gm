// $Id: SortingPlanWriter.java,v 1.4 2006/12/13 09:01:13 suresh Exp $
package jp.co.daifuku.wms.sorting.report;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * 仕分予定リストの帳票用データファイルを作成し、印刷実行クラスを呼び出すためのクラスです。<BR>
 * スケジュールクラスでセットされた内容で仕分予定情報を検索し、帳票用データファイルを作成します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 帳票用データファイル作成処理(<CODE>startPrint()</CODE>メソッド)<BR>
 * <DIR>
 *   仕分予定情報を検索します。<BR>
 *   該当データが存在しなかった場合、帳票用データファイルは作成しません。<BR>
 *   該当データが存在する場合、帳票用データファイルを作成し、印刷実行クラスを呼び出します。<BR>
 * <BR>
 *   [検索条件]*必須項目
 *   <DIR>
 *     荷主コード 		: ConsignorCode * <BR>
 *     仕分予定日 		: PlanDate * <BR>
 *     商品コード 		: ItemCode <BR>
 *     ケース／ピース区分: CasePieceFlag * <BR>
 *     クロス／ＤＣ		: TcdcFlag * <BR>
 *     作業状態			: StatusFlag * <BR>
 *   </DIR>
 *   <BR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/08</TD><TD>K.Mukai</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 09:01:13 $
 * @author  $Author: suresh $
 */
public class SortingPlanWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

	/**
	 * 仕分予定日
	 */
	private String wPlanDate;

	/**
	 * 商品コード
	 */
	private String wItemCode;

	/**
	 * ケース/ピース区分
	 */
	private String wCasePieceFlag;

	/**
	 * クロス/ＤＣ区分
	 */
	private String wTcdcFlag;

	/**
	 * 作業状態
	 */
	private String wWorkStatus;

	/**
	 * 仕分予定レポート検索クラス(名称等用)
	 */
	private SortingPlanReportFinder wPlanNameFinder = null;

	/**
	 * 仕分予定検索キー
	 */
	private SortingPlanSearchKey wPlanKey = null;

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
	 * SortingPlanWriter オブジェクトを構築します。<BR>
	 * コネクションとロケールのセットを行います。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 */
	public SortingPlanWriter(Connection conn)
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
	 * 仕分予定日に検索値をセットします。
	 * 
	 * @param PlanDate 仕分予定日
	 */
	public void setPlanDate(String pPlanDate)
	{
		wPlanDate = pPlanDate;
	}

	/**
	 * 仕分予定日を取得します。
	 * @return 仕分予定日
	 */
	public String getPlanDate()
	{
		return wPlanDate;
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
	 * ケース/ピース区分に検索値をセットします。
	 * @param pCasePieceFlag ケース/ピース区分
	 */
	public void setCasePieceFlag(String pCasePieceFlag)
	{
		wCasePieceFlag = pCasePieceFlag;
	}

	/**
	 * ケース/ピース区分を取得します。
	 * @return ケース/ピース区分
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	/**
	 * クロス/DCに検索値をセットします。
	 * @param pTcdcFlag クロス/DC
	 */
	public void setTcdcFlag(String pTcdcFlag)
	{
		wTcdcFlag = pTcdcFlag;
	}

	/**
	 * クロス/DCを取得します。
	 * @return クロス/DC
	 */
	public String getTcdcFlag()
	{
		return wTcdcFlag;
	}

	/**
	 * 作業状態に検索値をセットします。
	 * @param pWorkStatus 作業状態
	 */
	public void setWorkStatus(String pWorkStatus)
	{
		wWorkStatus = pWorkStatus;
	}

	/**
	 * 作業状態を取得します。
	 * @return 作業状態
	 */
	public String getWorkStatus()
	{
		return wWorkStatus;
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
		SortingPlanHandler handler = new SortingPlanHandler(wConn);
		SortingPlanSearchKey searchKey = new SortingPlanSearchKey();
		// 検索条件をセットし、件数取得を行う
		setSortingPlanSearchKey(searchKey);
		return handler.count(searchKey);

	}
	
	/**
	 * 仕分予定リスト用CSVファイルを作成し、印刷実行クラスを呼び出します。<BR>
	 * 画面で入力された検索条件より仕分予定情報を検索します。
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
		SortingPlanReportFinder planFinder = new SortingPlanReportFinder(wConn);
		SortingPlanSearchKey planKey = new SortingPlanSearchKey();
		try
		{
			// 検索条件セットを行う
			setSortingPlanSearchKey(planKey);
			// 検索順のセットを行う
			planKey.setItemCodeOrder(1, true);
			planKey.setTcdcFlagOrder(2, false);
			planKey.setCasePieceFlagOrder(3, true);
			planKey.setCustomerCodeOrder(4, true);
			planKey.setCaseLocationOrder(5, true);
			planKey.setPieceLocationOrder(6, true);

			// データがない場合は印刷処理を行わない。
			if (planFinder.search(planKey) <= 0)
			{
				// 6003010 = 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}

			// 出力ファイルを作成			
			if(!createPrintWriter(FNAME_PICKING_PLAN))
			{
				return false;	
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_PICKING_PLAN);
			
			// 名称検索用のインスタンスを起こす
			setSortingPlanHandler(wConn);
			// 名称を取得する
			String consignorName = "";
			String itemName = "";
			// 予定総数を取得する
			int totalQty = 0;
			// 検索結果がなくなるまでデータファイルの内容を作成する。
			SortingPlan[] sortingPlan = null;
			while (planFinder.isNext())
			{
				// 検索結果を100件づつ出力していく。
				sortingPlan = (SortingPlan[]) planFinder.getEntities(100);

				// データファイルに出力する内容を作成する。
				for (int i = 0; i < sortingPlan.length; i++)
				{
					wStrText.append(re + "");

					// 荷主コード
					wStrText.append(ReportOperation.format(sortingPlan[i].getConsignorCode()) + tb);
					// 荷主名
					if (i == 0 || !wConsignorCode.equals(sortingPlan[i].getConsignorCode()))
					{
						// 荷主コードをかきかえる
						wConsignorCode = sortingPlan[i].getConsignorCode();
						// 荷主名称を取得する
						consignorName = getConsignorName();
					}
					wStrText.append(ReportOperation.format(consignorName) + tb);
					// 入荷予定日
					wStrText.append(ReportOperation.format(wPlanDate) + tb);
					// 商品コード
					wStrText.append(ReportOperation.format(sortingPlan[i].getItemCode()) + tb);
					// 商品名称
					if (i == 0 || !wItemCode.equals(sortingPlan[i].getItemCode()))
					{
						// 商品コードを書き換える
						wItemCode = sortingPlan[i].getItemCode();
						
						// 商品コードと作業区分が同時に変更した場合
						// 商品名称の検索が上手くいかないため
						if (!wTcdcFlag.equals(sortingPlan[i].getTcdcFlag()))
						{
							// 作業区分を書き換える
							wTcdcFlag = sortingPlan[i].getTcdcFlag();
						}

						// 商品名称を取得する
						itemName = getItemName();
						// 予定総数を求める
						totalQty = getDisplayTotalQty();
					}
					wStrText.append(ReportOperation.format(itemName) + tb);
					
					// 作業区分
					if (i == 0 || !wTcdcFlag.equals(sortingPlan[i].getTcdcFlag()))
					{
						// 作業区分を書き換える
						wTcdcFlag = sortingPlan[i].getTcdcFlag();
						
						// 予定総数を求める
						totalQty = getDisplayTotalQty();
					}
					if(wTcdcFlag != null)
					{
						if (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_DC))
						{
							wStrText.append(ReportOperation.format(DisplayUtil.getTcDcValue(SortingPlan.TCDC_FLAG_DC)) + tb);
						}
						else if  (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_CROSSTC))
						{
							wStrText.append(ReportOperation.format(DisplayUtil.getTcDcValue(SortingPlan.TCDC_FLAG_CROSSTC)) + tb);
						}
					}
					else
					{
						wStrText.append(ReportOperation.format(wTcdcFlag) + tb);
					}
					
					// 予定総数
					wStrText.append(totalQty + tb);
					// 作業状態
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(sortingPlan[i].getCasePieceFlag())) + tb);
					// 出荷先コード
					wStrText.append(ReportOperation.format(sortingPlan[i].getCustomerCode()) + tb);
					// 出荷先名称
					wStrText.append(ReportOperation.format(sortingPlan[i].getCustomerName1()) + tb);
					// ケース仕分場所
					wStrText.append(ReportOperation.format(sortingPlan[i].getCaseLocation()) + tb);
					// ピース仕分場所
					wStrText.append(ReportOperation.format(sortingPlan[i].getPieceLocation()) + tb);
					// ケース入数
					wStrText.append(sortingPlan[i].getEnteringQty() + tb);
					// ボール入数
					wStrText.append(sortingPlan[i].getBundleEnteringQty() + tb);
					// ホスト予定ケース数
					wStrText.append(DisplayUtil.getCaseQty(sortingPlan[i].getPlanQty(), sortingPlan[i].getEnteringQty(), sortingPlan[i].getCasePieceFlag()) + tb);
					// ホスト予定ピース数
					wStrText.append(DisplayUtil.getPieceQty(sortingPlan[i].getPlanQty(), sortingPlan[i].getEnteringQty(), sortingPlan[i].getCasePieceFlag()) + tb);
					// 実績ケース数
					wStrText.append(DisplayUtil.getCaseQty(sortingPlan[i].getResultQty(), sortingPlan[i].getEnteringQty(), sortingPlan[i].getCasePieceFlag()) + tb);
					// 実績ピース数
					wStrText.append(DisplayUtil.getPieceQty(sortingPlan[i].getResultQty(), sortingPlan[i].getEnteringQty(), sortingPlan[i].getCasePieceFlag()) + tb);
					// 状態
					wStrText.append(ReportOperation.format(DisplayUtil.getRetrievalPlanStatusValue(sortingPlan[i].getStatusFlag())) + tb);
					// 仕入先コード
					wStrText.append(ReportOperation.format(sortingPlan[i].getSupplierCode()) + tb);
					// 仕入先名称
					wStrText.append(ReportOperation.format(sortingPlan[i].getSupplierName1()) + tb);

					// データをファイルに出力する。
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}

			// ストリームを閉じる
			wPWriter.close();

			// UCXSingleを実行。（印刷実行）
			if (!executeUCX(JOBID_PICKING_PLAN))
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
				planFinder.close();
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
	 * 仕分予定情報の検索キーに検索条件をセットするためのメソッドです。<BR>
	 * 
	 * @param searchKey 仕分予定情報の検索キー
	 * @return 仕分予定情報の検索キー
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void setSortingPlanSearchKey(SortingPlanSearchKey searchKey) throws ReadWriteException
	{
		// 検索キーをセットする。
		// 荷主コード
		if (!StringUtil.isBlank(this.getConsignorCode()))
		{
			searchKey.setConsignorCode(this.getConsignorCode());
		}
		// 仕分予定日
		if (!StringUtil.isBlank(this.getPlanDate()))
		{
			searchKey.setPlanDate(this.getPlanDate());
		}
		// 商品コード
		if (!StringUtil.isBlank(this.getItemCode()))
		{
			searchKey.setItemCode(this.getItemCode());
		}
		// ケース/ピース区分
		if (!StringUtil.isBlank(this.getCasePieceFlag()))
		{
			if (this.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				// ケース/ピース区分 ケース
				searchKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE);
			}
			else if (this.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				// ケース/ピース区分 ピース
				searchKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
			else if (this.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
			{
				// ケース/ピース区分 ケース/ピース
				searchKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_MIX);
			}
		}
		// クロス/ＤＣ
		if (!StringUtil.isBlank(wTcdcFlag))
		{
			if (this.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
			{
				// クロス
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_CROSSTC);
			}
			else if (this.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
			{
				// DC
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
			}
			else if (this.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_ALL))
			{
				// 全ての場合はＴＣ以外を検索する
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC, "<>");
			}
		}
		// 作業状態
		if (!StringUtil.isBlank(this.getWorkStatus()))
		{
			if (this.getWorkStatus().equals(SortingParameter.STATUS_FLAG_UNSTARTED))
			{
				// 未開始
				searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
			}
			else if (this.getWorkStatus().equals(SortingParameter.STATUS_FLAG_WORKING))
			{
				// 作業中
				searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
			}
			else if (this.getWorkStatus().equals(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				// 一部完了
				searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if (this.getWorkStatus().equals(SortingParameter.STATUS_FLAG_COMPLETION))
			{
				// 完了
				searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
			}
			else if (this.getWorkStatus().equals(SortingParameter.TCDC_FLAG_ALL))
			{
				//全ての場合ば削除以外
				searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
			}
		}
	}

	/**
	 * 検索に必要なインスタンスをおこします。
	 * ReportFinder、SearchKey
	 * @param conn データベースへのコネクションオブジェクト
	 */
	private void setSortingPlanHandler(Connection conn)
	{
		wPlanNameFinder = new SortingPlanReportFinder(conn);
		wPlanKey = new SortingPlanSearchKey();
	}

	/**
	 * 荷主名称を取得するためのメソッドです。<BR>
	 * <BR>
	 * 荷主コードに対して、複数の荷主名称があることが考えられるので
	 * 登録日時が一番新しいレコードの荷主名称を返します。<BR>
	 * 
	 * @return 荷主名称
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private String getConsignorName() throws ReadWriteException
	{
		String consignorName = "";

		// 検索キーをセットする
		wPlanKey = new SortingPlanSearchKey();
		setSortingPlanSearchKey(wPlanKey);
		wPlanKey.setConsignorNameCollect("");
		wPlanKey.setRegistDateOrder(1, false);

		// 検索を実行する
		wPlanNameFinder.open();
		int nameCount = wPlanNameFinder.search(wPlanKey);
		if (nameCount > 0)
		{
			SortingPlan[] sortingPlan = (SortingPlan[]) wPlanNameFinder.getEntities(1);

			if (sortingPlan != null && sortingPlan.length != 0)
			{
				consignorName = sortingPlan[0].getConsignorName();
			}
		}
		wPlanNameFinder.close();

		return consignorName;
	}

	/**
	 * 商品名称を取得するためのメソッドです。<BR>
	 * <BR>
	 * 商品コードに対して、複数の商品名称があることが考えられるので
	 * 登録日時が一番新しいレコードの商品名称を返します。<BR>
	 * 
	 * @return 商品名称
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private String getItemName() throws ReadWriteException
	{
		String itemName = "";

		// 検索条件をセットする
		wPlanKey = new SortingPlanSearchKey();
		setSortingPlanSearchKey(wPlanKey);
		wPlanKey.setItemName1Collect("");
		wPlanKey.setRegistDateOrder(1, false);

		// 検索を実行する
		wPlanNameFinder.open();
		int nameCount = wPlanNameFinder.search(wPlanKey);
		if (nameCount > 0)
		{
			SortingPlan[] sortingPlan = (SortingPlan[]) wPlanNameFinder.getEntities(1);

			if (sortingPlan != null && sortingPlan.length != 0)
			{
				itemName = sortingPlan[0].getItemName1();
			}
		}
		wPlanNameFinder.close();

		return itemName;
	}

	/**
	 * 荷主・予定日・商品単位で予定総数を取得します
	 * @return 予定総数
	 * @throws ReadWriteException
	 */
	private int getDisplayTotalQty() throws ReadWriteException
	{
		int totalQty = 0;

		// 検索キーをセットする        
		wPlanKey = new SortingPlanSearchKey();
		setSortingPlanSearchKey(wPlanKey);
		wPlanKey.setPlanQtyCollect("SUM");

		// 検索を実行する
		wPlanNameFinder.open();
		int nameCount = wPlanNameFinder.search(wPlanKey);
		if (nameCount > 0)
		{
			SortingPlan[] sortingPlan = (SortingPlan[]) wPlanNameFinder.getEntities(1);

			if (sortingPlan != null && sortingPlan.length != 0)
			{
				totalQty = sortingPlan[0].getPlanQty();
			}
		}
		wPlanNameFinder.close();

		return totalQty;
	}

}
//end of class
