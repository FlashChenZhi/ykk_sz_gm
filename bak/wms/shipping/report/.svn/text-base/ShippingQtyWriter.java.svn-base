// $Id: ShippingQtyWriter.java,v 1.4 2006/12/13 09:00:25 suresh Exp $
package jp.co.daifuku.wms.shipping.report;

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
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * <CODE>ShippingQtyWriter</CODE>クラスは、出荷実績リストの帳票用データファイルを作成し、印刷を実行します。<BR>
 * スケジュールクラスでセットされた内容で実績情報を検索し、帳票用データファイルを作成します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 帳票用データファイル作成処理(<CODE>startPrint()</CODE>メソッド)<BR>
 * <DIR>
 *   作業情報を検索します。<BR>
 *   該当データが存在しなかった場合、帳票用データファイルは作成しません。<BR>
 *   該当データが存在する場合、帳票用データファイルを作成し、印刷実行クラスを呼び出します。<BR>
 * <BR>
 *   ＜検索条件＞
 *   <DIR>
 *     荷主コード<BR>
 *     開始出荷日<BR>
 *     終了出荷日<BR>
 *     出荷先コード<BR>
 *     伝票No.<BR>
 *     商品コード<BR>
 *     区分：出荷<BR>
 *   </DIR>
 *    ＜抽出項目＞ <BR>
 * 	<DIR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     出荷日<BR>
 *     出荷予定日<BR>
 *     出荷先<BR>
 *     出荷先名称<BR>
 *     伝票No.<BR>
 *     行No.<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     作業予定ケース数<BR>
 *     作業予定ピース数<BR>
 *     実績総数<BR>
 *     実績ケース数<BR>
 *     実績ピース数<BR>
 *     欠品ケース数<BR>
 *     欠品ピース数<BR>
 *     賞味期限<BR>
 *  </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/19</TD><TD>M.Inoue</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 09:00:25 $
 * @author  $Author: suresh $
 */
public class ShippingQtyWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

	/**
	 * 開始出荷日
	 */
	private String wFromWorkDate ;

	/**
	 * 終了出荷日
	 */
	private String wToWorkDate ;

	/**
	 * 出荷先コード
	 */
	private String wCustomerCode ;

	/**
	 * 伝票No.
	 */
	private String wTicketNo ;

	/**
	 * 商品コード
	 */
	private String wItemCode  ;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 09:00:25 $") ;
	}

	// Constructors --------------------------------------------------
	/**
	 * ShippingQtyWriter オブジェクトを構築します。<BR>
	 * コネクションのセットを行います。<BR>
	 * @param conn <CODE>Connection</CODE> データベースとのコネクションオブジェクト
	 */
	public ShippingQtyWriter(Connection conn)
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
	 * 開始出荷日に検索値をセットします。
	 * @param pFromWorkDate セットする開始出荷日
	 */
	public void setFromWorkDate(String pFromWorkDate)
	{
		wFromWorkDate = pFromWorkDate;
	}

	/**
	 * 開始出荷日を取得します。
	 * @return 開始出荷日
	 */
	public String getFromWorkDate()
	{
		return wFromWorkDate;
	}

	/**
	 * 終了出荷日に検索値をセットします。
	 * @param pToWorkDate セットする終了出荷日
	 */
	public void setToWorkDate(String pToWorkDate)
	{
		wToWorkDate = pToWorkDate;
	}

	/**
	 * 終了出荷日を取得します。
	 * @return 終了出荷日
	 */
	public String getToWorkDate()
	{
		return wToWorkDate;
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
	 * 伝票No.に検索値をセットします。
	 * @param pTicketNo セットする伝票No.
	 */
	public void setTicketNo(String pTicketNo)
	{
		wTicketNo = pTicketNo;
	}

	/**
	 * 伝票No.を取得します。
	 * @return 伝票No.
	 */
	public String getTicketNo()
	{
		return wTicketNo;
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
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
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
	 * 出荷実績リスト用CSVファイルを作成し、印刷実行クラスを呼び出します。<BR>
	 * 画面で入力された検索条件より作業情報を検索します。
	 * 検索結果が1件未満の場合は印刷を行いません。<BR>
	 * 印刷に成功した場合true、失敗した場合はfalseを返します。<BR>
	 * 
	 * @return boolean 印刷が成功したかどうか。
	 */
	public boolean startPrint()
	{

		ResultViewReportFinder resultviewReportFinder = new ResultViewReportFinder(wConn);

		try
		{
			// 検索キーをセット
			ResultViewSearchKey resultviewSearchKey = new ResultViewSearchKey();		
			setResultViewSearchKey(resultviewSearchKey);

			// 表示順をセット
			resultviewSearchKey.setWorkDateOrder(1,true);
			resultviewSearchKey.setPlanDateOrder(2,true);
			resultviewSearchKey.setCustomerCodeOrder(3,true);
			resultviewSearchKey.setShippingTicketNoOrder(4,true);
			resultviewSearchKey.setShippingLineNoOrder(5,true);
			resultviewSearchKey.setItemCodeOrder(6,true);
			resultviewSearchKey.setRegistDateOrder(7,true);
			resultviewSearchKey.setResultQtyOrder(8,true);
			
			// 検索実行
			// データがない場合は印刷処理を行わない。
			if(resultviewReportFinder.search(resultviewSearchKey) <= 0 )
			{
				// 6003010 = 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}

			if(!super.createPrintWriter(CSVWriter.FNAME_SHIPPING_QTY))
			{
				return false;
			}
			
            // 出力ファイルのヘッダーを作成
            wStrText.append(CSVWriter.HEADER_SHIPPING_QTY);
            
			// 荷主名称を取得するため
			String consignorName = "";
			String customerName = "";

			// 検索結果がなくなるまでデータファイルの内容を作成する。
			ResultView[] resultview = null;
			while (resultviewReportFinder.isNext())
			{
				// 検索結果を100件づつ出力していく。
				resultview = (ResultView[])resultviewReportFinder.getEntities(100);

				// データファイルに出力する内容を作成する。
				for (int i = 0; i < resultview.length; i++)
				{
					wStrText.append(re + "");
					// 荷主コード
					wStrText.append(ReportOperation.format(resultview[i].getConsignorCode()) + tb);
					// 荷主名称
					// 荷主名称を取得する検索処理を毎回行わないために、荷主コードで比較している。
					if (i == 0 || !wConsignorCode.equals(resultview[i].getConsignorCode()))
					{
						// 荷主コードをかきかえる
						wConsignorCode = resultview[i].getConsignorCode();
						// 荷主名称を取得する
						consignorName = getConsignorName();
					}
					wStrText.append(ReportOperation.format(consignorName) + tb);
					// 出荷日
					wStrText.append(ReportOperation.format(resultview[i].getWorkDate()) + tb);
					// 出荷予定日
					wStrText.append(ReportOperation.format(resultview[i].getPlanDate()) + tb);
					// 出荷先コード
					wStrText.append(ReportOperation.format(resultview[i].getCustomerCode()) + tb);
					// 出荷先名称
					if (i == 0 || !wCustomerCode.equals(resultview[i].getCustomerCode()))
					{
						// 出荷先コードをかきかえる
						wCustomerCode = resultview[i].getCustomerCode();
						// 出荷先名称を取得する
						customerName = getCustomerName();
					}
					wStrText.append(ReportOperation.format(customerName) + tb);
					// 伝票No
					wStrText.append(ReportOperation.format(resultview[i].getShippingTicketNo()) + tb);
					// 行No
					wStrText.append(resultview[i].getShippingLineNo() + tb);
					// 商品コード
					wStrText.append(ReportOperation.format(resultview[i].getItemCode()) + tb);
					// 商品名称
					wStrText.append(ReportOperation.format(resultview[i].getItemName1()) + tb);
					// ケース入数
					wStrText.append(resultview[i].getEnteringQty() + tb);
					// ボール入数
					wStrText.append(resultview[i].getBundleEnteringQty() + tb);
					// 作業予定ケース数
					wStrText.append(DisplayUtil.getCaseQty(resultview[i].getPlanEnableQty(), resultview[i].getEnteringQty()) + tb);
					// 作業予定ピース数
					wStrText.append(DisplayUtil.getPieceQty(resultview[i].getPlanEnableQty(), resultview[i].getEnteringQty()) + tb);
					// 実績総数
					wStrText.append(resultview[i].getResultQty() +tb);
					// 実績ケース数
					wStrText.append( DisplayUtil.getCaseQty(resultview[i].getResultQty(), resultview[i].getEnteringQty()) + tb);
					// 実績予定ピース数
					wStrText.append(DisplayUtil.getPieceQty(resultview[i].getResultQty(), resultview[i].getEnteringQty()) + tb);
					// 欠品ケース数
					wStrText.append(DisplayUtil.getCaseQty(resultview[i].getShortageCnt(), resultview[i].getEnteringQty()) + tb);
					// 欠品ピース数
					wStrText.append(DisplayUtil.getPieceQty(resultview[i].getShortageCnt(), resultview[i].getEnteringQty()) + tb);
					// 賞味期限
					wStrText.append(ReportOperation.format(resultview[i].getResultUseByDate()));

					// データをファイルに出力する。
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}

			// ストリームを閉じる
			wPWriter.close();

			// UCXSingleを実行。（印刷実行）
			if (!executeUCX(JOBID_SHIPPING_QTY))
			{
				// 印刷に失敗しました。ログを参照してください。
				return false;
			}

			// 印刷に成功した場合、データファイルをバックアップフォルダへ移動する。
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
				resultviewReportFinder.close();
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
	 * 実績View検索キーに検索条件をセットするためのメソッドです。<BR>
	 * 
	 * @param searchKey ResultViewSearchKey 実績Viewの検索キー
	 * @return 実績Viewの検索キー
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private void setResultViewSearchKey(ResultViewSearchKey serachKey) throws ReadWriteException
	{
		// 荷主コード
		if(!StringUtil.isBlank(wConsignorCode))
		{
			serachKey.setConsignorCode(wConsignorCode);
		}
		// 開始出荷日
		if(!StringUtil.isBlank(wFromWorkDate))
		{
			serachKey.setWorkDate(wFromWorkDate, ">=");
		}
		// 終了出荷日
		if(!StringUtil.isBlank(wToWorkDate))
		{
			serachKey.setWorkDate(wToWorkDate, "<=");
		}
		// 出荷先コード
		if(!StringUtil.isBlank(wCustomerCode))
		{
			serachKey.setCustomerCode(wCustomerCode);
		}
		// 伝票No.
		if(!StringUtil.isBlank(wTicketNo))
		{
			serachKey.setShippingTicketNo(wTicketNo);
		}
		// 商品コード
		if(!StringUtil.isBlank(wItemCode))
		{
			serachKey.setItemCode(wItemCode);
		}
				
		// 出荷データ
		serachKey.setJobType(ResultView.JOB_TYPE_SHIPINSPECTION);

	}
	
	/**
	 * 荷主先名称を取得するためのメソッドです。<BR>
	 * <BR>
	 * 荷主コードに対して、複数の荷主先名称があることが考えられるので
	 * 登録日時が一番新しいレコードの荷主名称を返します。<BR>
	 * 
	 * @return 荷主名称
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private String getConsignorName() throws ReadWriteException
	{
		

		// 検索キーをセットする
		ResultViewSearchKey nameKey = new ResultViewSearchKey();
		ResultViewReportFinder nameFinder = new ResultViewReportFinder(wConn);

		this.setResultViewSearchKey(nameKey);
		nameKey.setConsignorNameCollect("");
		nameKey.setRegistDateOrder(1, false);
		// 検索を実行する
		nameFinder.open();
		int nameCount = nameFinder.search(nameKey);
		String consignorName = "";
		if (nameCount > 0)
		{
			ResultView winfo[] = (ResultView[]) nameFinder.getEntities(1);

			if (winfo != null && winfo.length != 0)
			{
				consignorName = winfo[0].getConsignorName();
			}
		}
		nameFinder.close();

		return consignorName;
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
		

		// 検索キーをセットする
		ResultViewSearchKey nameKey = new ResultViewSearchKey();
		ResultViewReportFinder nameFinder = new ResultViewReportFinder(wConn);

		this.setResultViewSearchKey(nameKey);
		nameKey.setCustomerName1Collect("");
		nameKey.setRegistDateOrder(1, false);
		// 検索を実行する
		nameFinder.open();
		int nameCount = nameFinder.search(nameKey);
		String customerName = "";
		if (nameCount > 0)
		{
			ResultView winfo[] = (ResultView[]) nameFinder.getEntities(1);

			if (winfo != null && winfo.length != 0)
			{
				customerName = winfo[0].getCustomerName1();
			}
		}
		nameFinder.close();

		return customerName;
	}

}
//end of class
