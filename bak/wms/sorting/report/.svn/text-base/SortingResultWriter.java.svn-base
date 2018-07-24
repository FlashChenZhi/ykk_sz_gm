// $Id: SortingResultWriter.java,v 1.4 2006/12/13 09:01:13 suresh Exp $
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
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * 
 * 仕分実績リストの帳票用データファイルを作成し、印刷実行クラスを呼び出すためのクラスです。<BR>
 * スケジュールクラスでセットされた内容で実績情報を検索し、帳票用データファイルを作成します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 帳票用データファイル作成処理(<CODE>startPrint()</CODE>メソッド)<BR>
 * <DIR>
 *   実績情報(ResultView)を検索します。<BR>
 *   該当データが存在しなかった場合、帳票用データファイルは作成しません。<BR>
 *   該当データが存在する場合、帳票用データファイルを作成し、印刷実行クラスを呼び出します。<BR>
 * <BR>
 *   ＜検索条件＞
 *   <DIR>
 *     荷主コード <BR>
 *     仕分実績日 <BR>
 *     商品コード <BR>
 *     ケースピース区分 <BR>
 *     クロス／ＤＣ区分 <BR>
 *     区分：『仕分』<BR>
 *   </DIR>
 *    ＜抽出項目＞ <BR>
 * 	<DIR>
 *     荷主コード <BR>
 *     荷主名称 <BR>
 *     仕分実績日 <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     ケースピース区分名称 <BR>
 *     クロス／ＤＣ区分名称 <BR>
 *     総仕分ケース数 <BR>
 *     総仕分ピース数 <BR>
 *     仕分場所 <BR>
 *     出荷先コード <BR>
 *     出荷先名称 <BR>
 *     ケースピース区分名称（詳細） <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     作業予定ケース数 <BR>
 *     作業予定ピース数 <BR>
 *     実績ケース数 <BR>
 *     実績ピース数 <BR>
 *     欠品ケース数 <BR>
 *     欠品ピース数 <BR>
 *     仕入先コード <BR>
 *     仕入先名称 <BR>　
 *  </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/5</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 09:01:13 $
 * @author  $Author: suresh $
 */
public class SortingResultWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

	/**
	 * 仕分実績日
	 */
	private String wSortingDate ;

	/**
	 * 商品コード
	 */
	private String wItemCode ;

	/**
	 * ケースピース区分
	 */
	private String wCasePieceFlag ;

	/**
	 * クロス／ＤＣ区分
	 */
	private String wTcdcFlag ;


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
	 * SortingResultWriter オブジェクトを構築します。<BR>
	 * コネクションのセットを行います。<BR>
	 * @param conn <CODE>Connection</CODE> データベースとのコネクションオブジェクト
	 * @param locale <CODE>Locale</CODE>  地域コードがセットされた<CODE>Locale</CODE>オブジェクト
	 */
	public SortingResultWriter(Connection conn)
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
	 * 仕分実績日に検索値をセットします。
	 * @param pFromPlanDate セットする仕分実績日
	 */
	public void setSortingDate(String pSortingDate)
	{
		wSortingDate = pSortingDate;
	}

	/**
	 * 仕分実績日を取得します。
	 * @return 仕分実績日
	 */
	public String getSortingDate()
	{
		return wSortingDate;
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
	 * ケースピース区分に検索値をセットします。
	 * @param pCasePieceFlag セットするケースピース区分
	 */
	public void setCasePieceFlag(String pCasePieceFlag)
	{
		wCasePieceFlag = pCasePieceFlag;
	}

	/**
	 * ケースピース区分を取得します。
	 * @return ケースピース区分
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	/**
	 * クロス／ＤＣ区分に検索値をセットします。
	 * @param pTcdcFlag セットするクロス／ＤＣ区分
	 */
	public void setTcdcFlag(String pTcdcFlag)
	{
		wTcdcFlag = pTcdcFlag;
	}

	/**
	 * クロス／ＤＣ区分を取得します。
	 * @return クロス／ＤＣ区分
	 */
	public String getTcdcFlag()
	{
		return wTcdcFlag;
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
	 * 仕分実績リスト用CSVファイルを作成し、印刷実行クラスを呼び出します。<BR>
	 * 画面で入力された検索条件より実績情報を検索します。
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
			// 荷主コード、仕分日、商品コード、クロスDC区分、
			// 仕分場所（ロケーションNo）、出荷先コード、作業形態（ケースピース区分）の順で出力します。
			resultviewSearchKey.setItemCodeOrder(1,true);
			resultviewSearchKey.setTcdcFlagOrder(2,false);
			resultviewSearchKey.setLocationNoOrder(3,true);
			resultviewSearchKey.setCustomerCodeOrder(4,true);
			resultviewSearchKey.setCasePieceFlagOrder(5,true);
			resultviewSearchKey.setRegistDateOrder(6,true);
			resultviewSearchKey.setResultQtyOrder(7,true);
			
			// 検索実行
			// データがない場合は印刷処理を行わない。
			if(resultviewReportFinder.search(resultviewSearchKey) <= 0 )
			{
				// 6003010 = 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}

			// 出力ファイルを作成			
			if(!createPrintWriter(FNAME_PICKING_QTY))
			{
				return false;	
			}
			
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_PICKING_QTY);
            
			// 荷主名称を取得するため
			String consignorName = "";
			// 商品名称を取得するため
			String itemName = "";
			int totalCaseQty = 0;
			int totalPieceQty = 0;
			
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
						// 荷主コードを書き換える
						wConsignorCode = resultview[i].getConsignorCode();
						// 荷主名称を取得する
						consignorName = getConsignorName();
					}
					wStrText.append(ReportOperation.format(consignorName) + tb);
					
					// 仕分日
					wStrText.append(ReportOperation.format(resultview[i].getWorkDate()) + tb);
					// 商品コード
					wStrText.append(ReportOperation.format(resultview[i].getItemCode()) + tb);
					// 商品名称
					if (i == 0 || !wItemCode.equals(resultview[i].getItemCode()))
					{
						// 商品コードをかきかえる
						wItemCode = resultview[i].getItemCode();
						
						// 商品コードと作業区分が同時に変更した場合
						// 商品名称の検索が上手くいかないため
						if (!wTcdcFlag.equals(resultview[i].getTcdcFlag()))
						{
							// 作業区分を書き換える
							wTcdcFlag = resultview[i].getTcdcFlag();
						}

						// 商品名称を取得する
						itemName = getItemName();
						// 予定総数を求める
						int[] totalQty = getDisplayTotalCaseQty();
						totalCaseQty = totalQty[0];
						totalPieceQty = totalQty[1] + getDisplayTotalPieceQty();
					}
					wStrText.append(ReportOperation.format(itemName) + tb);
					// 作業形態(入力情報)
					if (wCasePieceFlag != null)
					{
						//指定なし
						if (wCasePieceFlag.equals(SortingParameter.CASEPIECE_FLAG_NOTHING))
						{
							wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_NOTHING)) + tb);
						} 
						// ケース
						else if (wCasePieceFlag.equals(SortingParameter.CASEPIECE_FLAG_CASE))
						{
							wStrText.append(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_CASE) + tb); 
						} 
						// ピース
						else if (wCasePieceFlag.equals(SortingParameter.CASEPIECE_FLAG_PIECE))
						{
							wStrText.append(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_PIECE) + tb);
						} 
						// 全て
						else
						{
							wStrText.append(DisplayText.getText("LBL-W0346") + tb);
						}
					} 
					else
					{
						wStrText.append(ReportOperation.format(wCasePieceFlag) + tb);
					}
					
					// クロス/DC区分
					// クロス/DC区分が異なるとき、予定総数を求める
					if (i == 0 || !wTcdcFlag.equals(resultview[i].getTcdcFlag()))
					{
						// クロス/DC区分を書き換える
						wTcdcFlag = resultview[i].getTcdcFlag();
						
						// 予定総数を求める
						int[] totalQty = getDisplayTotalCaseQty();
						totalCaseQty = totalQty[0];
						totalPieceQty = totalQty[1] + getDisplayTotalPieceQty();
					}
					if (wTcdcFlag != null)
					{
						// クロスＴＣ
						if (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_CROSSTC))
						{
							wStrText.append(DisplayUtil.getTcDcValue(SystemDefine.TCDC_FLAG_CROSSTC) + tb);
						}
						// ＤＣ
						else if (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_DC))
						{
							wStrText.append(DisplayUtil.getTcDcValue(SystemDefine.TCDC_FLAG_DC) + tb);
						}
					}
					else
					{
						wStrText.append(ReportOperation.format(wTcdcFlag) + tb);
					}
					
					// 総仕分数 ケース数 
					wStrText.append(totalCaseQty + tb);
					// 総仕分数 ピース数
					wStrText.append(totalPieceQty + tb);
					// 仕分場所
					wStrText.append(ReportOperation.format(resultview[i].getLocationNo()) + tb);
					// 出荷先コード
					wStrText.append(ReportOperation.format(resultview[i].getCustomerCode()) + tb);
					// 出荷先名称
					wStrText.append(ReportOperation.format(resultview[i].getCustomerName1()) + tb);
					// 作業形態(DBから取得)
					if (resultview[i].getCasePieceFlag() != null)
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(resultview[i].getCasePieceFlag())) + tb);
					} 
					else
					{
						wStrText.append(ReportOperation.format(resultview[i].getCasePieceFlag()) + tb);
					}
					// ケース入数
					wStrText.append(resultview[i].getEnteringQty() + tb);
					// ボール入数
					wStrText.append(resultview[i].getBundleEnteringQty() + tb);
					// 作業予定ケース数
					wStrText.append(DisplayUtil.getCaseQty(resultview[i].getPlanEnableQty(), resultview[i].getEnteringQty(), resultview[i].getCasePieceFlag()) + tb);
					// 作業予定ピース数
					wStrText.append(DisplayUtil.getPieceQty(resultview[i].getPlanEnableQty(), resultview[i].getEnteringQty(), resultview[i].getCasePieceFlag()) + tb);
					// 実績ケース数
					wStrText.append( DisplayUtil.getCaseQty(resultview[i].getResultQty(), resultview[i].getEnteringQty(), resultview[i].getCasePieceFlag()) + tb);
					// 実績ピース数
					wStrText.append(DisplayUtil.getPieceQty(resultview[i].getResultQty(), resultview[i].getEnteringQty(), resultview[i].getCasePieceFlag()) + tb);
					// 欠品ケース数
					wStrText.append(DisplayUtil.getCaseQty(resultview[i].getShortageCnt(), resultview[i].getEnteringQty(), resultview[i].getCasePieceFlag()) + tb);
					// 欠品ピース数
					wStrText.append(DisplayUtil.getPieceQty(resultview[i].getShortageCnt(), resultview[i].getEnteringQty(), resultview[i].getCasePieceFlag()) + tb);
					// 仕入先コード
					wStrText.append(ReportOperation.format(resultview[i].getSupplierCode()) + tb);
					// 仕入先名称
					wStrText.append(ReportOperation.format(resultview[i].getSupplierName1()) + tb);

					// データをファイルに出力する。
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);

					
				}
			}

			// ストリームを閉じる 
			wPWriter.close();

			// UCXSingleを実行。（印刷実行）
			if (!executeUCX(JOBID_PICKING_QTY))
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
	 * 作業情報検索キーに検索条件をセットするためのメソッドです。<BR>
	 * 
	 * @param searchKey ResultViewSearchKey 作業情報の検索キー
	 * @return 作業情報の検索キー
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void setResultViewSearchKey(ResultViewSearchKey searchKey) throws ReadWriteException
	{
		// 荷主コード
		if(!StringUtil.isBlank(wConsignorCode))
		{
			searchKey.setConsignorCode(wConsignorCode);
		}
		// 仕分実績日
		if(!StringUtil.isBlank(wSortingDate))
		{
			searchKey.setWorkDate(wSortingDate);
		}
		// 商品コード
		if(!StringUtil.isBlank(wItemCode))
		{
			searchKey.setItemCode(wItemCode);
		}
		// ケースピース区分
		if(!StringUtil.isBlank(wCasePieceFlag))
		{
			if (wCasePieceFlag.equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setCasePieceFlag(ResultView.CASEPIECE_FLAG_CASE);
			}
			else if (wCasePieceFlag.equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setCasePieceFlag(ResultView.CASEPIECE_FLAG_PIECE);
			}
		}
		// ＴＣ／ＤＣ区分
		if(!StringUtil.isBlank(wTcdcFlag))
		{
			if (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_DC))
			{
				searchKey.setTcdcFlag(ResultView.TCDC_FLAG_DC);
			}
			else if (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_CROSSTC))
			{
				searchKey.setTcdcFlag(ResultView.TCDC_FLAG_CROSSTC);
			}
			else
			{
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC,"!=");
			}
		}
				
		// 作業区分『仕分』
		searchKey.setJobType(ResultView.JOB_TYPE_SORTING);

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
		String consignorName = "";

		// 検索キーをセットする
		ResultViewSearchKey nameKey = new ResultViewSearchKey();
		ResultViewReportFinder nameFinder = new ResultViewReportFinder(wConn);

		setResultViewSearchKey(nameKey);
		nameKey.setConsignorNameCollect("");
		nameKey.setRegistDateOrder(1, false);
		// 検索を実行する
		nameFinder.open();
		int nameCount = ((ResultViewReportFinder) nameFinder).search(nameKey);
		if (nameCount > 0)
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

		// 検索キーをセットする
		ResultViewSearchKey nameKey = new ResultViewSearchKey();
		ResultViewReportFinder nameFinder = new ResultViewReportFinder(wConn);

		setResultViewSearchKey(nameKey);
		nameKey.setItemName1Collect("");
		nameKey.setRegistDateOrder(1, false);
		
		// 検索を実行する
		nameFinder.open();
		int nameCount = ((ResultViewReportFinder) nameFinder).search(nameKey);
		if (nameCount > 0)
		{
			ResultView[] resultView = (ResultView[]) ((ResultViewReportFinder) nameFinder).getEntities(1);

			if (resultView != null && resultView.length != 0)
			{
				itemName = resultView[0].getItemName1();
			}
		}
		nameFinder.close();
		
		return itemName;
	}
	
	/**
	 * 荷主・作業日・商品コードからケース作業の実績を求めます
	 * 端数があった場合、ピース数として返します。
	 * @return 0 ケース数 1 ピース数
	 * @throws ReadWriteException
	 */
	private int[] getDisplayTotalCaseQty() throws ReadWriteException
	{
		// 画面表示がピースの場合、ケース数は求めない
		if (wCasePieceFlag.equals(SortingParameter.CASEPIECE_FLAG_PIECE))
		{
			int[] resultQty = {0, 0};
			return resultQty;
		}
		
		int totalCaseQty = 0;
		int totalPieceQty = 0;
		
		ResultViewSearchKey resultKey = new ResultViewSearchKey();
		ResultViewHandler resultHandler = new ResultViewHandler(wConn);
		ResultView[] resultView = null;
        
		// 検索キーをセットする
		// 荷主コード
		resultKey.setConsignorCode(wConsignorCode);
		// 作業日
		resultKey.setWorkDate(wSortingDate);
		// 商品コード
		resultKey.setItemCode(wItemCode);
		// ケースピースフラグ
		resultKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
		// TC/DC区分
		if (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_DC))
		{
			resultKey.setTcdcFlag(ResultView.TCDC_FLAG_DC);
		}
		else if (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			resultKey.setTcdcFlag(ResultView.TCDC_FLAG_CROSSTC);
		}
		else
		{
			resultKey.setTcdcFlag(ResultView.TCDC_FLAG_TC, "!=");
		}
		// 作業区分：仕分
		resultKey.setJobType(ResultView.JOB_TYPE_SORTING);
		
		// 検索を実行する
		resultView = (ResultView[]) resultHandler.find(resultKey);
		
		for (int i = 0; i < resultView.length; i++)
		{
			totalCaseQty += DisplayUtil.getCaseQty(resultView[i].getResultQty(), resultView[i].getEnteringQty());
			totalPieceQty += DisplayUtil.getPieceQty(resultView[i].getResultQty(), resultView[i].getEnteringQty());
		}
		
		int[] resultQty = {totalCaseQty, totalPieceQty};
		
		return resultQty;
	}

	/**
	 * 荷主・作業日・商品コードからピース作業の実績を求めます
	 * 
	 * @return int 作業実績数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int getDisplayTotalPieceQty() throws ReadWriteException
	{
		// 画面表示がケースの場合、ピース数は求めない
		if (wCasePieceFlag.equals(SortingParameter.CASEPIECE_FLAG_CASE))
		{
			return 0;
		}

		int totalPieceQty = 0;
		
		ResultViewSearchKey resultKey = new ResultViewSearchKey();
		ResultViewHandler resultHandler = new ResultViewHandler(wConn);
		ResultView[] resultView = null;
        
		// 検索キーをセットする
		// 荷主コード
		resultKey.setConsignorCode(wConsignorCode);
		// 作業日
		resultKey.setWorkDate(wSortingDate);
		// 商品コード
		resultKey.setItemCode(wItemCode);
		// ケースピースフラグ
		resultKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
		// TC/DC区分
		if (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_DC))
		{
			resultKey.setTcdcFlag(ResultView.TCDC_FLAG_DC);
		}
		else if (wTcdcFlag.equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			resultKey.setTcdcFlag(ResultView.TCDC_FLAG_CROSSTC);
		}
		else
		{
			resultKey.setTcdcFlag(ResultView.TCDC_FLAG_TC, "!=");
		}
		// 作業区分：仕分
		resultKey.setJobType(ResultView.JOB_TYPE_SORTING);
		
		// 検索を実行する
		resultView = (ResultView[]) resultHandler.find(resultKey);
		
		for (int i = 0; i < resultView.length; i++)
		{
			totalPieceQty += resultView[i].getResultQty();
		}
		
		return totalPieceQty;
	}

}
//end of class
