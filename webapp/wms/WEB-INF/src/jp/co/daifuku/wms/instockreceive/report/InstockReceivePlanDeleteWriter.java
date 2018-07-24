// $Id: InstockReceivePlanDeleteWriter.java,v 1.4 2006/12/13 08:56:45 suresh Exp $
package jp.co.daifuku.wms.instockreceive.report;

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
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * 
 * <CODE>InstockReceiveDeleteWriter</CODE>クラスは、入荷予定削除リストの帳票用データファイルを作成し、印刷を実行します。<BR>
 * <CODE>InstockReceivePlanDeleteSCH</CODE>クラスでセットされた条件にもとづき、入荷予定情報を検索し、<BR>
 * 結果を入荷予定削除リスト用の帳票ファイルとして作成します。<BR>
 * このクラスは、以下の処理を行います。<BR>
 * <BR>
 * 帳票用データファイル作成処理(<CODE>startPrint()</CODE>メソッド)<BR>
 *	<DIR>
 *	1.<CODE>InstockReceivePlanDeleteSCH</CODE>クラスからセットされた条件で入荷予定情報の件数を検索します。<BR>
 *	2.結果が1件以上なら、帳票ファイルを作成します。0件だった場合はfalseを返して終了します。<BR>
 *	3.印刷処理を実行します。<BR>
 *  4.印刷処理が正常だった場合はtrueを返します。<BR>
 *    印刷処理中にエラーが発生した場合はfalseを返します。<BR>
 *<BR>
 * 	＜パラメータ＞*必須入力<BR>
 * 	荷主コード*<BR>
 * 	入荷予定日*<BR>
 * 	仕入先コード*<BR>
 * 	TCDC区分*<BR>
 * <BR>
 * 	＜検索条件＞<BR>
 * 	荷主コード<BR>
 * 	入荷予定日<BR>
 * 	仕入先コード<BR>
 * 	TCDC区分<BR>
 * <BR>
 *	＜印刷データ＞<BR>
 *	印刷項目：ＤＢ項目<BR>
 *	荷主：荷主コード + 荷主名称<BR>
 *	入荷予定日：入荷予定日<BR>
 *  ＴＣ／ＤＣ：ＴＣ／ＤＣフラグ名称<BR>
 *	仕入先：仕入先コード + 仕入先名称<BR>
 *	伝票No.：伝票No.<BR>
 *	行No.：行No.<BR>
 *	商品コード：商品コード<BR>
 *	商品名称：商品名称<BR>
 *	予定総数：入荷予定数<BR>
 *	ケース入数：ケース入数<BR>
 *	ボール入数：ボール入数<BR>
 *	ホスト予定ケース数：入荷予定数／ケース入数<BR>
 *	ホスト予定ピース数：入荷予定数％ケース入数<BR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 08:56:45 $
 * @author  $Author: suresh $
 */
public class InstockReceivePlanDeleteWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode[] = null;

	/**
	 * 入荷予定日
	 */
	private String wPlanDate[] = null;

	/**
	 * 仕入先コード
	 */
	private String wSupplierCode[] = null;

	/**
	 * TCDC区分
	 */
	private String wTcdcFlag[] = null;

	/**
	 * 更新日時
	 */
	private Date wLastUpdateDate[] = null;

	/**
	 * 表示する荷主名称
	 */
	private String wConsignorName = null;

	/**
	 * 表示する仕入先名称
	 */
	private String wSupplierName = null;

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
	 * InstockReceiveDeleteWriter オブジェクトを構築します。
	 * @param conn <CODE>Connection</CODE>
	 */
	public InstockReceivePlanDeleteWriter(Connection conn)
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
	 * 入荷予定日に値をセットします。
	 * @param plandate セットする入荷予定日
	 */
	public void setPlanDate(String[] plandate)
	{
		wPlanDate = plandate;
	}

	/**
	 * 入荷予定日を取得します。
	 * @return 入荷予定日
	 */
	public String[] getPlanDate()
	{
		return wPlanDate;
	}

	/**
	 * 仕入先コードに値をセットします。
	 * @param suppliercode セットする仕入先コード
	 */
	public void setSupplierCode(String[] suppliercode)
	{
		wSupplierCode = suppliercode;
	}

	/**
	 * 仕入先コードを取得します。
	 * @return 仕入先コード
	 */
	public String[] getSupplierCode()
	{
		return wSupplierCode;
	}

	/**
	 * TCDC区分に値をセットします。
	 * @param tcdcflag セットするTCDC区分
	 */
	public void setTcdcFlag(String[] tcdcflag)
	{
		wTcdcFlag = tcdcflag;
	}

	/**
	 * TCDC区分を取得します。
	 * @return TCDC区分
	 */
	public String[] getTcdcFlag()
	{
		return wTcdcFlag;
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
	 * 入荷予定削除リスト用CSVファイルを作成し、印刷を実行します。<BR>
	 * セットされた条件で入荷予定情報の件数を検索します。<BR>
	 * 結果が1件以上なら、帳票ファイルを作成します。0件だった場合はfalseを返して終了します。<BR>
	 * 印刷処理を実行します。<BR>
	 * 印刷処理が正常に終了したら、trueを返します。<BR>
	 * 印刷処理中にエラーが発生した場合はfalseを返します。<BR>
	 */
	public boolean startPrint()
	{
		InstockPlanReportFinder instockPlanReportFinder = new InstockPlanReportFinder(wConn);

		try
		{
			InstockPlanSearchKey instockPlanSearchKey = new InstockPlanSearchKey();
			InstockPlan[] instockPlan = null;

			boolean pHeader_Flag = true;
			// パラメータで受け取ったデータ分、処理を繰り返します。
			for (int cdt_cnt = 0; cdt_cnt < wConsignorCode.length; cdt_cnt++)
			{

				// 検索キークリア
				instockPlanSearchKey.KeyClear();

				// 検索条件をセット
				setSearchKey(instockPlanSearchKey, cdt_cnt);

				// 表示順をセット
				instockPlanSearchKey.setPlanDateOrder(1, true);
				instockPlanSearchKey.setTcdcFlagOrder(2, false);
				instockPlanSearchKey.setSupplierCodeOrder(3, true);
				instockPlanSearchKey.setInstockTicketNoOrder(4, true);
				instockPlanSearchKey.setInstockLineNoOrder(5, true);
				instockPlanSearchKey.setItemCodeOrder(6, true);

				// カーソルオープン
				instockPlanReportFinder.open();

				// 検索実行
				if (instockPlanReportFinder.search(instockPlanSearchKey) <= 0)
				{
					// 対象データなし
					// 印刷データはありませんでした。
					wMessage = "6003010";
					return false;
				}

				// 表示する名称確定
				getDisplayName(cdt_cnt);

				// 初回のみ処理を行います。
				if (pHeader_Flag == true)
				{
					// 出力ファイルを作成します
					if (!createPrintWriter(FNAME_INSTOCK_DELETE))
					{
						return false;
					}

					pHeader_Flag = false;
                    
                    // 出力ファイルにヘッダーを作成
                    wStrText.append(HEADER_INSTOCK_DELETE);
				}
				// 検索結果がなくなるまで、100件ごとにデータファイルを作成する。
				while (instockPlanReportFinder.isNext())
				{
					// 検索結果から、100件まで取得
					instockPlan = (InstockPlan[]) instockPlanReportFinder.getEntities(100);

					for (int i = 0; i < instockPlan.length; i++)
					{
						wStrText.append(re+"");

						// 荷主コード
						wStrText.append(ReportOperation.format(instockPlan[i].getConsignorCode()) + tb);
						// 荷主名
						wStrText.append(ReportOperation.format(wConsignorName) + tb);
						// 入荷予定日
						wStrText.append(ReportOperation.format(instockPlan[i].getPlanDate()) + tb);
						// ＴＣ／ＤＣフラグ名称
						wStrText.append(
							 ReportOperation.format(
								DisplayUtil.getTcDcValue(instockPlan[i].getTcdcFlag()))
							+ tb);
						// 仕入先コード
						wStrText.append(ReportOperation.format(instockPlan[i].getSupplierCode()) + tb);
						// 仕入先名
						wStrText.append(ReportOperation.format(wSupplierName) + tb);
						// 伝票No.
						wStrText.append(ReportOperation.format(instockPlan[i].getInstockTicketNo())
							+ tb);
						// 行No.
						wStrText.append(instockPlan[i].getInstockLineNo() + tb);
						// 商品コード
						wStrText.append(ReportOperation.format(instockPlan[i].getItemCode()) + tb);
						// 商品名称
						wStrText.append(ReportOperation.format(instockPlan[i].getItemName1()) + tb);
						// 予定総数
						wStrText.append(instockPlan[i].getPlanQty() + tb);
						// ケース入数
						wStrText.append(instockPlan[i].getEnteringQty() + tb);
						// ボール入数
						wStrText.append(instockPlan[i].getBundleEnteringQty() + tb);
						// ホスト予定ケース数
						wStrText.append(
							 DisplayUtil.getCaseQty(
								instockPlan[i].getPlanQty(),
								instockPlan[i].getEnteringQty(),
								instockPlan[i].getCasePieceFlag())
							+ tb);
						// ホスト予定ピース数
						wStrText.append(
							 DisplayUtil.getPieceQty(
								instockPlan[i].getPlanQty(),
								instockPlan[i].getEnteringQty(),
								instockPlan[i].getCasePieceFlag()));

						// 書き込み
						wPWriter.print(wStrText);
						wStrText.setLength(0);
					}
				}

			}
			// ストリームクローズ
			wPWriter.close();

			// UCXSingleを実行
			if (!executeUCX(JOBID_INSTOCK_DELETE))
			{
				return false;
			}

			// データファイルをバックアップフォルダへ移動
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			// 印刷に失敗しました。ログを参照してください。
			setMessage("6007034");

			return false;
		}
		finally
		{
			try
			{
				// オープンしたデータベースカーソルのクローズ処理を行う。
				instockPlanReportFinder.close();
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
	 * @param cnt 添え字
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void setSearchKey(InstockPlanSearchKey searchKey, int cnt) throws ReadWriteException
	{
		// 荷主コード
		searchKey.setConsignorCode(wConsignorCode[cnt]);
		// 入庫予定日
		if (wPlanDate[cnt] != null)
		{
			searchKey.setPlanDate(wPlanDate[cnt]);
		}
		// 仕入先コード
		if (wSupplierCode[cnt] != null)
		{
			searchKey.setSupplierCode(wSupplierCode[cnt]);
		}
		// TC/DC区分
		if (wTcdcFlag[cnt] != null)
		{
			searchKey.setTcdcFlag(wTcdcFlag[cnt]);
		}
		// 更新日時
		if (wLastUpdateDate[cnt] != null)
		{
			searchKey.setLastUpdateDate(wLastUpdateDate[cnt], ">=");
		}
	}

	// Private methods -----------------------------------------------
	/**
	 * リストに表示するための荷主名称、仕入先名称を取得します。<BR>
	 * 印刷データの検索条件で、登録日時が最新の入荷予定情報を検索し、<BR>
	 * 先頭のデータの荷主名称を取得します。<BR>
	 * @param cnt 添え字
	 */
	private void getDisplayName(int cnt) throws ReadWriteException
	{
		// Finderインスタンス生成
		InstockPlanHandler consignorHandler = new InstockPlanHandler(wConn);
		InstockPlanSearchKey instockPlanSearchKey = new InstockPlanSearchKey();

		// 検索条件をセット
		setSearchKey(instockPlanSearchKey, cnt);
		
		instockPlanSearchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE);
		instockPlanSearchKey.setRegistDateOrder(1, false);

		wConsignorName = "";
		wSupplierName = "";

		// 荷主名称検索
		InstockPlan[] instockPlan = (InstockPlan[])consignorHandler.find(instockPlanSearchKey);
		if (instockPlan != null && instockPlan.length != 0)
		{
			wConsignorName = instockPlan[0].getConsignorName();
			wSupplierName = instockPlan[0].getSupplierName1();
		}
	}

}
//end of class
