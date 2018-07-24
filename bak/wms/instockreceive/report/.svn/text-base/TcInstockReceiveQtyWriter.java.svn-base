// $Id: TcInstockReceiveQtyWriter.java,v 1.4 2006/12/13 08:56:43 suresh Exp $
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
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * TC入荷実績リストの帳票用データファイルを作成し、印刷実行クラスを呼び出すためのクラスです。<BR>
 * スケジュールクラスでセットされた内容で実績情報Viewを検索し、帳票用データファイルを作成します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 帳票用データファイル作成処理(<CODE>startPrint()</CODE>メソッド)<BR>
 * <DIR>
 *   実績情報Viewを検索します。<BR>
 *   該当データが存在しなかった場合、帳票用データファイルは作成しません。<BR>
 *   該当データが存在する場合、帳票用データファイルを作成し、印刷実行クラスを呼び出します。<BR>
 * <BR>
 *   ＜検索条件＞*必須項目
 *   <DIR>
 *     作業区分：01(入荷)* <BR>
 *     TC/DC区分：TC* <BR>
 *     荷主コード* <BR>
 *     開始入荷受付日 <BR>
 *     終了入荷受付日 <BR>
 *     仕入先コード <BR>
 *     出荷先コード <BR>
 *     開始伝票No. <BR>
 *     終了伝票No. <BR>
 *     商品コード <BR>
 *   </DIR>
 *   <BR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/27</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 08:56:43 $
 * @author  $Author: suresh $
 */
public class TcInstockReceiveQtyWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

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
	 * 出荷先コード
	 */
	private String wCustomerCode;

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

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 08:56:43 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * TcInstockReceiveQtyWriter オブジェクトを構築します。<BR>
	 * コネクションとロケールのセットを行います。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 */
	public TcInstockReceiveQtyWriter(Connection conn)
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
	 * 出荷先コードに検索値をセットします。
	 * @param pCustomerCode セットする出荷先コード
	 */
	public void setCustomerCode(String pCustomerCode)
	{
		wCustomerCode = pCustomerCode;
	}

	/**
	 * 出荷先コードを取得します。
	 * @return 出荷先コード
	 */
	public String getCustomerCode()
	{
		return wCustomerCode;
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
	 * 印刷データの件数を取得します。<BR>
	 * この検索結果により、印刷処理を行うかどうかを判断するために使用します。<BR>
	 * 本メソッドは、画面処理を行うスケジュールクラスから使用されます。<BR>
	 * 
	 * @return 印刷データ件数
	 * @throws ReadWriteException
	 */
	public int count() throws ReadWriteException
	{
		ResultViewHandler handler = new ResultViewHandler(wConn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		// 検索条件をセットし、件数取得を行う
		setResultViewSearchKey(searchKey);
		return handler.count(searchKey);

	}

	/**
	 * TC入荷実績リスト用CSVファイルを作成し、印刷実行クラスを呼び出します。<BR>
	 * 画面で入力された検索条件より実績情報Viewを検索します。
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

		ResultViewReportFinder viewFinder = new ResultViewReportFinder(wConn);

		try
		{
			// 検索実行
			ResultViewSearchKey viewKey = new ResultViewSearchKey();

			// 検索条件セットを行う
			setResultViewSearchKey(viewKey);
			// 検索順のセットを行う
			viewKey.setWorkDateOrder(1, true);
			viewKey.setPlanDateOrder(2, true);
			viewKey.setSupplierCodeOrder(3, true);
			viewKey.setCustomerCodeOrder(4, true);
			viewKey.setInstockTicketNoOrder(5, true);
			viewKey.setInstockLineNoOrder(6, true);
			viewKey.setItemCodeOrder(7, true);
			// 登録日
			viewKey.setRegistDateOrder(8,true);
			// 実績数
			viewKey.setResultQtyOrder(9,true);

			// データがない場合は印刷処理を行わない。
			if (viewFinder.search(viewKey) <= 0)
			{
				// 6003010 = 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}

			if (!createPrintWriter(FNAME_INSTOCK_QTY_TC))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_INSTOCK_QTY_TC);
            
			// 名称を取得する
			String consignorName = "";
			String supplierName = "";
			String customerName = "";
			// 検索結果がなくなるまでデータファイルの内容を作成する。
			ResultView[] resultView = null;
			while (viewFinder.isNext())
			{
				// 検索結果を100件づつ出力していく。
				resultView = (ResultView[]) viewFinder.getEntities(100);

				// データファイルに出力する内容を作成する。
				for (int i = 0; i < resultView.length; i++)
				{
					wStrText.append(re + "");

					// 荷主コード
					wStrText.append(ReportOperation.format(resultView[i].getConsignorCode()) + tb);
					// 荷主名
					if (i == 0 || !wConsignorCode.equals(resultView[i].getConsignorCode()))
					{
						// 荷主コードをかきかえる
						wConsignorCode = resultView[i].getConsignorCode();
						// 荷主名称を取得する
						consignorName = getConsignorName();
					}
					wStrText.append(ReportOperation.format(consignorName) + tb);
					// 入荷日
					wStrText.append(ReportOperation.format(resultView[i].getWorkDate()) + tb);
					// 入荷予定日
					wStrText.append(ReportOperation.format(resultView[i].getPlanDate()) + tb);
					// 仕入先コード
					wStrText.append(ReportOperation.format(resultView[i].getSupplierCode()) + tb);
					if (i == 0 || !wSupplierCode.equals(resultView[i].getSupplierCode()))
					{
						// 仕入先コードと出荷先コードが同時に変更した場合
						//仕入先名称の検索が上手くいかないため
						// 仕入先コードをかきかえる
						wSupplierCode = resultView[i].getSupplierCode();
						if (!wCustomerCode.equals(resultView[i].getCustomerCode()))
						{
							// 出荷先コードをかきかえる
							wCustomerCode = resultView[i].getCustomerCode();
							// 出荷先名称を取得する
							customerName = getCustomerName();
						}
						// 仕入先名称を取得する
						supplierName = getSupplierName();
					}
					// 仕入先名称
					wStrText.append(ReportOperation.format(supplierName) + tb);
					// 出荷先コード
					wStrText.append(ReportOperation.format(resultView[i].getCustomerCode()) + tb);
					if (i == 0 || !wCustomerCode.equals(resultView[i].getCustomerCode()))
					{
						// 出荷先コードをかきかえる
						wCustomerCode = resultView[i].getCustomerCode();
						// 出荷先名称を取得する
						customerName = getCustomerName();
					}
					// 出荷先名称
					wStrText.append(ReportOperation.format(customerName) + tb);
					// 伝票No
					wStrText.append(ReportOperation.format(resultView[i].getInstockTicketNo()) + tb);
					// 行No
					wStrText.append(resultView[i].getInstockLineNo() + tb);
					// 商品コード
					wStrText.append(ReportOperation.format(resultView[i].getItemCode()) + tb);
					// 商品名称
					wStrText.append(ReportOperation.format(resultView[i].getItemName1()) + tb);
					// ケース入数
					wStrText.append(resultView[i].getEnteringQty() + tb);
					// ボール入数
					wStrText.append(resultView[i].getBundleEnteringQty() + tb);
					// 作業予定ケース数
					wStrText.append(DisplayUtil.getCaseQty(resultView[i].getPlanEnableQty(), resultView[i].getEnteringQty(), resultView[i].getCasePieceFlag()) + tb);
					// 作業予定ピース数
					wStrText.append(DisplayUtil.getPieceQty(resultView[i].getPlanEnableQty(), resultView[i].getEnteringQty(), resultView[i].getCasePieceFlag()) + tb);
					// 実績ケース数
					wStrText.append(DisplayUtil.getCaseQty(resultView[i].getResultQty(), resultView[i].getEnteringQty(), resultView[i].getCasePieceFlag()) + tb);
					// 実績ピース数
					wStrText.append(DisplayUtil.getPieceQty(resultView[i].getResultQty(), resultView[i].getEnteringQty(), resultView[i].getCasePieceFlag()) + tb);
					// 欠品ケース数
					wStrText.append(DisplayUtil.getCaseQty(resultView[i].getShortageCnt(), resultView[i].getEnteringQty(), resultView[i].getCasePieceFlag()) + tb);
					// 欠品ピース数
					wStrText.append(DisplayUtil.getPieceQty(resultView[i].getShortageCnt(), resultView[i].getEnteringQty(), resultView[i].getCasePieceFlag()) + tb);
					// 賞味期限
					wStrText.append(ReportOperation.format(resultView[i].getResultUseByDate()));

					// データをファイルに出力する。
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}

			// ストリームを閉じる
			wPWriter.close();

			// UCXSingleを実行。（印刷実行）
			if (!executeUCX(JOBID_INSTOCK_QTY_TC))
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
				viewFinder.close();
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
	 * 実績情報Viewの検索キーに検索条件をセットするためのメソッドです。<BR>
	 * 
	 * @param searchKey 実績情報Viewの検索キー
	 * @return 実績情報Viewの検索キー
	 */
	private void setResultViewSearchKey(ResultViewSearchKey searchKey) throws ReadWriteException
	{
		// 検索キーをセットする。
		if (!StringUtil.isBlank(wConsignorCode))
		{
			searchKey.setConsignorCode(wConsignorCode);
		}
		if (!StringUtil.isBlank(wFromDate))
		{
			searchKey.setWorkDate(wFromDate, ">=");
		}
		
		if (!StringUtil.isBlank(wToDate))
		{
			searchKey.setWorkDate(wToDate, "<=");
		}
		if (!StringUtil.isBlank(wSupplierCode))
		{
			searchKey.setSupplierCode(wSupplierCode);
		}
		if (!StringUtil.isBlank(wCustomerCode))
		{
			searchKey.setCustomerCode(wCustomerCode);
		}
		if (!StringUtil.isBlank(wFromTicketNo))
		{
			searchKey.setInstockTicketNo(wFromTicketNo, ">=");
		}
		if (!StringUtil.isBlank(wToTicketNo))
		{
			searchKey.setInstockTicketNo(wToTicketNo, "<=");
		}
		if (!StringUtil.isBlank(wItemCode))
		{
			searchKey.setItemCode(wItemCode);
		}
		searchKey.setJobType(ResultView.JOB_TYPE_INSTOCK);
		searchKey.setTcdcFlag(ResultView.TCDC_FLAG_TC);

	}

	/**
	 * 荷主名称を取得するためのメソッドです。<BR>
	 * <BR>
	 * 荷主コードに対して、複数の荷主名称があることが考えられるので
	 * 登録日時が一番新しいレコードの入荷先名称を返します。<BR>
	 * 
	 * @return 荷主名称
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private String getConsignorName() throws ReadWriteException
	{
		String consignorName = "";

		// 検索キーをセットする
		ResultViewSearchKey nameKey = new ResultViewSearchKey();
		ResultViewReportFinder nameFinder = new ResultViewReportFinder(wConn);

		setResultViewSearchKey(nameKey);
		nameKey.setConsignorNameCollect("");
		nameKey.setRegistDateOrder(1, false);
		
		// 検索を実行する
		nameFinder.open();
		
		if (((ResultViewReportFinder) nameFinder).search(nameKey) > 0)
		{
			ResultView resultView[] = (ResultView[]) ((ResultViewReportFinder) nameFinder).getEntities(1);

			if (resultView != null && resultView.length != 0)
			{
				consignorName = resultView[0].getConsignorName();
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
		ResultViewSearchKey nameKey = new ResultViewSearchKey();
		ResultViewReportFinder nameFinder = new ResultViewReportFinder(wConn);

		setResultViewSearchKey(nameKey);
		nameKey.setSupplierName1Collect("");
		nameKey.setRegistDateOrder(1, false);
		
		// 検索を実行する
		nameFinder.open();
		
		if (((ResultViewReportFinder) nameFinder).search(nameKey) > 0)
		{
			ResultView resultView[] = (ResultView[]) ((ResultViewReportFinder) nameFinder).getEntities(1);

			if (resultView != null && resultView.length != 0)
			{
				supplierName = resultView[0].getSupplierName1();
			}
		}
		nameFinder.close();
		
		return supplierName;
	}

	/**
	 * 出荷先名称を取得するためのメソッドです。<BR>
	 * <BR>
	 * 出荷先コードに対して、複数の出荷先名称があることが考えられるので
	 * 登録日時が一番新しいレコードの出荷先名称を返します。<BR>
	 * 
	 * @return 出荷先名称
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private String getCustomerName() throws ReadWriteException
	{
		String customerName = "";

		// 検索キーをセットする
		ResultViewSearchKey nameKey = new ResultViewSearchKey();
		ResultViewReportFinder nameFinder = new ResultViewReportFinder(wConn);

		setResultViewSearchKey(nameKey);
		nameKey.setCustomerName1Collect("");
		nameKey.setRegistDateOrder(1, false);
		
		// 検索を実行する
		nameFinder.open();
		
		if (((ResultViewReportFinder) nameFinder).search(nameKey) > 0)
		{
			ResultView resultView[] = (ResultView[]) ((ResultViewReportFinder) nameFinder).getEntities(1);

			if (resultView != null && resultView.length != 0)
			{
				customerName = resultView[0].getCustomerName1();
			}
		}
		nameFinder.close();
		
		return customerName;
	}

}
//end of class
