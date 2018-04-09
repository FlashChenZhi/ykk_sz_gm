// $Id: InstockReceiveWriter.java,v 1.4 2006/12/13 08:56:44 suresh Exp $
package jp.co.daifuku.wms.instockreceive.report;

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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * 
 * <CODE>InstockReceiveWriter</CODE>クラスは、クロス/ＤＣ入荷作業リストの帳票用データファイルを作成し、印刷を実行します。<BR>
 * <CODE>InstockReceiveListSCH</CODE>クラスでセットされた条件にもとづき、作業情報を検索し、<BR>
 * 結果をクロス/ＤＣ入荷指図書用の帳票ファイルとして作成します。<BR>
 * このクラスは、以下の処理を行います。<BR>
 * <BR>
 * 帳票用データファイル作成処理(<CODE>startPrint()</CODE>メソッド)<BR>
 *	<DIR>
 *	1.<CODE>InstockReceiveListSCH</CODE>クラスからセットされた条件で作業情報の件数を検索します。<BR>
 *	2.結果が1件以上なら、帳票ファイルを作成します。0件だった場合はfalseを返して終了します。<BR>
 *	3.印刷処理を実行します。<BR>
 *  4.印刷処理が正常だった場合はtrueを返します。<BR>
 *    印刷処理中にエラーが発生した場合はfalseを返します。<BR>
 *<BR>
 * 	＜パラメータ＞*必須入力<BR>
 *	<DIR>
 * 	荷主コード*<BR>
 * 	入荷予定日*<BR>
 * 	仕入先コード<BR>
 *  商品コード<BR>
 *  クロス/ＤＣ区分*<BR>
 *  表示順*<BR>
 *  作業状態*<BR>
 *	</DIR>
 * <BR>
 * 	＜検索条件＞<BR>
 *	<DIR>
 * 	荷主コード<BR>
 * 	入荷予定日<BR>
 * 	仕入先コード<BR>
 *  商品コード<BR>
 *  クロス/ＤＣ区分<BR>
 *  表示順<BR>
 *  作業状態<BR>
 *	</DIR>
 * <BR>
 *	＜印刷データ＞<BR>
 *	印刷項目：ＤＢ項目<BR>
 *	荷主：荷主コード + 荷主名称<BR>
 *	入荷予定日：入荷予定日<BR>
 *	仕入先：仕入先コード + 仕入先名称<BR>
 *	伝票No.：伝票No.<BR>
 *	行No.：行No.<BR>
 *	商品コード：商品コード<BR>
 *	商品名称：商品名称<BR>
 *	入荷総数：作業可能数<BR>
 *	ケース入数：ケース入数<BR>
 *	ボール入数：ボール入数<BR>
 *	作業予定ケース数：作業可能数／ケース入数<BR>
 *	作業予定ピース数：作業可能数％ケース入数<BR>
 *	実績ケース数：作業実績数／ケース入数<BR>
 *	実績ピース数：作業実績数％ケース入数<BR>
 *	賞味期限：賞味期限<BR>
 *	クロス/ＤＣ：クロス/ＤＣ区分（クロス/ＤＣ）<BR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/04</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 08:56:44 $
 * @author  $Author: suresh $
 */
public class InstockReceiveWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	protected String wConsignorCode;

	/**
	 * 入荷予定日
	 */
	protected String wInstockPlanDay;

	/**
	 * 仕入先コード
	 */
	protected String wSupplierCode;

	/**
	 * 商品コード
	 */
	protected String wItemCode;

	/**
	 * クロス/ＤＣ区分
	 */
	protected String wCrossDc = null;

	/**
	 * 表示順
	 */
	protected String wDisplayOrder;

	/**
	 * 作業状態フラグ
	 */
	protected String wStatusFlag = null;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 08:56:44 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * InstockReceiveWriter オブジェクトを構築します。<BR>
	 * コネクションとロケールのセットを行います。<BR>
	 * @param conn <CODE>Connection</CODE> データベースとのコネクションオブジェクト
	 */
	public InstockReceiveWriter(Connection conn)
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
	 * 入荷予定日に検索値をセットします。
	 * @param pFromPlanDate セットする入荷予定日
	 */
	public void setInstockPlanDay(String pInstockPlanDay)
	{
		wInstockPlanDay = pInstockPlanDay;
	}

	/**
	 * 入荷予定日を取得します。
	 * @return 入荷予定日
	 */
	public String getInstockPlanDay()
	{
		return wInstockPlanDay;
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
	 * クロス/ＤＣ区分をセットします。
	 * @param crossDc セットするクロス/ＤＣ区分
	 */
	public void setCrossDc(String crossDc)
	{
		wCrossDc = crossDc;
	}

	/**
	 * クロス/ＤＣ区分を取得します。
	 * @return クロス/ＤＣ区分
	 */
	public String getCrossDc()
	{
		return wCrossDc;
	}

	/**
	 * 表示順をセットします。
	 * @param displayOrder セットする表示順
	 */
	public void setDisplayOrder(String displayOrder)
	{
		wDisplayOrder = displayOrder;
	}

	/**
	 * 表示順を取得します。
	 * @return 表示順
	 */
	public String getDisplayOrder()
	{
		return wDisplayOrder;
	}

	/**
	 * 状態フラグに値をセットします。
	 * @param statusFlag セットする状態フラグ
	 */
	public void setStatusFlag(String statusFlag)
	{
		wStatusFlag = statusFlag;
	}

	/**
	 * 状態フラグを取得します。
	 * @return 状態フラグ
	 */
	public String getStatusFlag()
	{
		return wStatusFlag;
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
		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		// 検索条件をセットし、件数取得を行う
		setWorkInfoSearchKey(searchKey);
		return handler.count(searchKey);

	}
	
	/**
	 * クロス/ＤＣ入荷作業リスト用のCSVファイルを作成し、印刷実行クラスを呼び出します。<BR>
	 * 画面で入力された検索条件より作業情報を検索します。<BR>
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

		WorkingInformationReportFinder winfoFinder = new WorkingInformationReportFinder(wConn);

		try
		{
			// 検索実行
			WorkingInformationSearchKey winfoKey = new WorkingInformationSearchKey();

			// 検索条件セットを行う
			setWorkInfoSearchKey(winfoKey);

			// 表示順の指定によって検索順のセットを変える
			if (getDisplayOrder().equals(InstockReceiveParameter.DISPLAY_ORDER_TICKET))
			{
				// 伝票No.順指定時
				// 検索順のセットを行う
				winfoKey.setConsignorCodeOrder(1, true);
				winfoKey.setPlanDateOrder(2, true);
				winfoKey.setSupplierCodeOrder(3, true);
				winfoKey.setInstockTicketNoOrder(4, true);
				winfoKey.setInstockLineNoOrder(5, true);
				winfoKey.setItemCodeOrder(6, true);
				winfoKey.setResultUseByDateOrder(7, true);
				winfoKey.setTcdcFlagOrder(8, false);
			}
			if (getDisplayOrder().equals(InstockReceiveParameter.DISPLAY_ORDER_ITEM))
			{
				// 商品コード順指定時
				// 検索順のセットを行う
				winfoKey.setConsignorCodeOrder(1, true);
				winfoKey.setPlanDateOrder(2, true);
				winfoKey.setSupplierCodeOrder(3, true);
				winfoKey.setItemCodeOrder(4, true);
				winfoKey.setInstockTicketNoOrder(5, true);
				winfoKey.setInstockLineNoOrder(6, true);
				winfoKey.setResultUseByDateOrder(7, true);
				winfoKey.setTcdcFlagOrder(8, false);
			}

			// データがない場合は印刷処理を行わない。
			if (winfoFinder.search(winfoKey) <= 0)
			{
				// 6003010 = 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}

			// 出力ファイルを作成します。
			if (!createPrintWriter(FNAME_INSTOCK_INSPECTION))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_INSTOCK_INSPECTION);

			// 名称を取得する
			String consignorName = "";
			String supplierName = "";
			// 検索結果がなくなるまでデータファイルの内容を作成する。
			WorkingInformation[] workInfo = null;
			while (winfoFinder.isNext())
			{
				// 検索結果を100件づつ出力していく。
				workInfo = (WorkingInformation[]) winfoFinder.getEntities(100);

				// データファイルに出力する内容を作成する。
				for (int i = 0; i < workInfo.length; i++)
				{
					wStrText.append( re + "");

					// 荷主コード
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					// 荷主名
					if (i == 0 || !wConsignorCode.equals(workInfo[i].getConsignorCode()))
					{
						// 荷主コードをかきかえる
						wConsignorCode = workInfo[i].getConsignorCode();
						// 荷主名称を取得する
						consignorName = getConsignorName();
					}
					wStrText.append(ReportOperation.format(consignorName) + tb);
					// 入荷予定日
					wStrText.append(ReportOperation.format(workInfo[i].getPlanDate()) + tb);
					// 仕入先コード
					wStrText.append(ReportOperation.format(workInfo[i].getSupplierCode()) + tb);
					// 仕入先名
					if (i == 0 || !wConsignorCode.equals(workInfo[i].getSupplierCode()))
					{
						// 仕入先コードをかきかえる
						wSupplierCode = workInfo[i].getSupplierCode();
						// 仕入先名称を取得する
						supplierName = getSupplierName();
					}
					wStrText.append(ReportOperation.format(supplierName) + tb);
					// 伝票No.
					wStrText.append(ReportOperation.format(workInfo[i].getInstockTicketNo()) + tb);
					// 行No.
					wStrText.append(workInfo[i].getInstockLineNo() + tb);
					// 商品コード
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					// 商品名称
					wStrText.append(ReportOperation.format(workInfo[i].getItemName1()) + tb);
					// 入荷総数
					wStrText.append(workInfo[i].getPlanEnableQty() + tb);
					// ケース入数
					wStrText.append(workInfo[i].getEnteringQty() + tb);
					// ボール入数
					wStrText.append(workInfo[i].getBundleEnteringQty() + tb);

					//作業予定ケース数
					wStrText.append(
						 DisplayUtil.getCaseQty(
							workInfo[i].getPlanEnableQty(),
							workInfo[i].getEnteringQty(),
							workInfo[i].getWorkFormFlag())
						+ tb);
					// 作業予定ピース数
					wStrText.append(
						 DisplayUtil.getPieceQty(
							workInfo[i].getPlanEnableQty(),
							workInfo[i].getEnteringQty(),
							workInfo[i].getWorkFormFlag())
						+ tb);
					// 作業実績ケース数
					wStrText.append(
						 DisplayUtil.getCaseQty(
							workInfo[i].getResultQty(),
							workInfo[i].getEnteringQty(),
							workInfo[i].getWorkFormFlag())
						+ tb);
					// 作業実績ピース数
					wStrText.append(
						 DisplayUtil.getPieceQty(
							workInfo[i].getResultQty(),
							workInfo[i].getEnteringQty(),
							workInfo[i].getWorkFormFlag())
						+ tb);

					// 賞味期限
					wStrText.append(workInfo[i].getResultUseByDate() + tb);
					// クロス/ＤＣ区分名称
					// クロス/ＤＣ区分からクロス/ＤＣ区分名称を取得します。
					wStrText.append(ReportOperation.format(
									DisplayUtil.getTcDcValue(workInfo[i].getTcdcFlag())) + tb);

					// データをファイルに出力する。
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}

			// ストリームを閉じる
			wPWriter.close();

			// UCXSingleを実行。（印刷実行）
			if (!executeUCX(JOBID_INSTOCK_INSPECTION))
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
				winfoFinder.close();
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
	 * @param searchKey WorkingInformationSearchKey 作業情報の検索キー
	 * @return 作業情報の検索キー
	 */
	private void setWorkInfoSearchKey(WorkingInformationSearchKey searchKey)
		throws ReadWriteException
	{
		// 検索キーをセットする。
		// クロス/ＤＣ入荷作業リスト発行画面からの呼び出し
		// 荷主コード、入荷予定日、仕入先コード、商品コード、クロス/ＤＣ区分(配列)、作業状態区分(配列)
		// 荷主コード
		if (!StringUtil.isBlank(wConsignorCode))
		{
			searchKey.setConsignorCode(wConsignorCode);
		}
		// 入荷予定日
		if (!StringUtil.isBlank(wInstockPlanDay))
		{
			searchKey.setPlanDate(wInstockPlanDay);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(wSupplierCode))
		{
			searchKey.setSupplierCode(wSupplierCode);
		}
		// 商品コード
		if (!StringUtil.isBlank(wItemCode))
		{
			searchKey.setItemCode(wItemCode);
		}
		// クロス/ＤＣ区分
		if (!StringUtil.isBlank(wCrossDc))
		{
			// クロス
			if (wCrossDc.equals(InstockReceiveParameter.TCDC_FLAG_CROSSTC))
				searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
			// ＤＣ
			else if (wCrossDc.equals(InstockReceiveParameter.TCDC_FLAG_DC))
				searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			// 全て
			else if (wCrossDc.equals(InstockReceiveParameter.TCDC_FLAG_ALL))
			{
				String[] tcdcflag =
					{ WorkingInformation.TCDC_FLAG_CROSSTC, WorkingInformation.TCDC_FLAG_DC, };
				searchKey.setTcdcFlag(tcdcflag);
			}
		}
		// 作業状態
		if (!StringUtil.isBlank(wStatusFlag))
		{
			// 未開始
			if (wStatusFlag.equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
				searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			// 作業中
			else if (wStatusFlag.equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
				searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			// 一部完了
			else if (wStatusFlag.equals(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART);
			// 完了
			else if (wStatusFlag.equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
				searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			// 全て
			else if (wStatusFlag.equals(InstockReceiveParameter.STATUS_FLAG_ALL))
			{
				searchKey.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_DELETE, "!=");
			}
		}

		searchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
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
		WorkingInformationSearchKey nameKey = new WorkingInformationSearchKey();
		WorkingInformationReportFinder nameFinder = new WorkingInformationReportFinder(wConn);

		setWorkInfoSearchKey(nameKey);
		nameKey.setConsignorNameCollect();
		nameKey.setRegistDateOrder(1, false);
		// 検索を実行する
		nameFinder.open();
		
		if (((WorkingInformationReportFinder) nameFinder).search(nameKey) > 0)
		{
			WorkingInformation winfo[] =
				(WorkingInformation[]) ((WorkingInformationReportFinder) nameFinder).getEntities(1);

			if (winfo != null && winfo.length != 0)
			{
				consignorName = winfo[0].getConsignorName();
			}
		}
		nameFinder.close();

		return consignorName;
	}

	/**
	 * 仕入先名称を取得するためのメソッドです。<BR>
	 * <BR>
	 * 仕入先コードに対して、複数の仕入先名称があることが考えられるので
	 * 登録日時が一番新しいレコードの仕入先名称を返します。<BR>
	 * 
	 * @return 仕入先名称
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private String getSupplierName() throws ReadWriteException
	{
		String supplierName = "";

		// 検索キーをセットする
		WorkingInformationSearchKey nameKey = new WorkingInformationSearchKey();
		WorkingInformationReportFinder nameFinder = new WorkingInformationReportFinder(wConn);

		setWorkInfoSearchKey(nameKey);
		nameKey.setSupplierName1Collect("");
		nameKey.setRegistDateOrder(1, false);
		// 検索を実行する
		nameFinder.open();
		
		if (((WorkingInformationReportFinder) nameFinder).search(nameKey) > 0)
		{
			WorkingInformation winfo[] =
				(WorkingInformation[]) ((WorkingInformationReportFinder) nameFinder).getEntities(1);

			if (winfo != null && winfo.length != 0)
			{
				supplierName = winfo[0].getSupplierName1();
			}
		}
		nameFinder.close();

		return supplierName;
	}

}
