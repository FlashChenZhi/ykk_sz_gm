// $Id: ShippingWriter.java,v 1.4 2006/12/13 09:00:25 suresh Exp $
package jp.co.daifuku.wms.shipping.report;

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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * <CODE>ShippingWriter</CODE>クラスは、出荷作業リストの帳票用データファイルを作成し、印刷を実行します。<BR>
 * スケジュールクラスでセットされた内容で作業情報を検索し、帳票用データファイルを作成します。<BR>
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
 *     開始出荷予定日<BR>
 *     終了出荷予定日<BR>
 *     出荷先コード<BR>
 *     伝票No.<BR>
 *     商品コード<BR>
 *     作業区分：出荷<BR>
 *     作業開始フラグ：未開始<BR>
 *   </DIR>
 *    ＜抽出項目＞ <BR>
 * 	<DIR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     出荷予定日<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     伝票No.<BR>
 *     伝票行No.<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     作業予定ケース数<BR>
 *     作業予定ピース数<BR>
 *     実績総数<BR>
 *     実績ケース数<BR>
 *     実績ピース数<BR>
 *  </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 09:00:25 $
 * @author  $Author: suresh $
 */
public class ShippingWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

	/**
	 * 開始出荷予定日
	 */
	private String wFromPlanDate;

	/**
	 * 終了出荷予定日
	 */
	private String wToPlanDate;

	/**
	 * 出荷先コード
	 */
	private String wCustomerCode;

	/**
	 * 伝票No.
	 */
	private String wTicketNo;

	/**
	 * 商品コード
	 */
	private String wItemCode;
	
	/**
	 * 状態フラグ
	 */
	protected String wStatusFlag = "";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 09:00:25 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * ShippingWriter オブジェクトを構築します。<BR>
	 * コネクションとロケールのセットを行います。<BR>
	 * @param conn <CODE>Connection</CODE> データベースとのコネクションオブジェクト
	 */
	public ShippingWriter(Connection conn)
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
	 * 開始出荷予定日に検索値をセットします。
	 * @param pFromPlanDate セットする開始出荷予定日
	 */
	public void setFromPlanDate(String pFromPlanDate)
	{
		wFromPlanDate = pFromPlanDate;
	}

	/**
	 * 開始出荷予定日を取得します。
	 * @return 開始出荷予定日
	 */
	public String getFromPlanDate()
	{
		return wFromPlanDate;
	}

	/**
	 * 終了出荷予定日に検索値をセットします。
	 * @param pToPlanDate セットする終了出荷予定日
	 */
	public void setToPlanDate(String pToPlanDate)
	{
		wToPlanDate = pToPlanDate;
	}

	/**
	 * 終了出荷予定日を取得します。
	 * @return 終了出荷予定日
	 */
	public String getToPlanDate()
	{
		return wToPlanDate;
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
	 * 状態フラグをセットする。
	 * 
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
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
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
	 * 出荷作業リスト用CSVファイルを作成し、印刷実行クラスを呼び出します。<BR>
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

		WorkingInformationReportFinder wInfoReportFinder = new WorkingInformationReportFinder(wConn);

		try
		{
			// 検索実行
			WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();

			// 検索条件セットを行う
			this.setWorkInfoSearchKey(workInfoSearchKey);
			// 検索順のセットを行う
			workInfoSearchKey.setConsignorCodeOrder(1, true);
			workInfoSearchKey.setPlanDateOrder(2, true);
			workInfoSearchKey.setCustomerCodeOrder(3, true);
			workInfoSearchKey.setShippingTicketNoOrder(4, true);
			workInfoSearchKey.setShippingLineNoOrder(5, true);

			// データがない場合は印刷処理を行わない。
			if (wInfoReportFinder.search(workInfoSearchKey) <= 0)
			{
				// 6003010 = 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}
			
			if(!super.createPrintWriter(CSVWriter.FNAME_SHIPPING_LIST))
			{
				return false;
			}

            // 出力ファイルにヘッダーを作成
            wStrText.append(CSVWriter.HEADER_SHIPPING_LIST);
            
			// 名称を取得する
			String consignorName = "";
			String customerName = "";
			// 検索結果がなくなるまでデータファイルの内容を作成する。
			WorkingInformation[] workInfo = null;
			while (wInfoReportFinder.isNext())
			{
				// FTTB 検索結果を100件づつ出力していく。
				workInfo = (WorkingInformation[]) wInfoReportFinder.getEntities(100);

				// データファイルに出力する内容を作成する。
				for (int i = 0; i < workInfo.length; i++)
				{
					wStrText.append(re + "");

					// 荷主コード
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					// 荷主名
					if (i == 0 || !wConsignorCode.equals(workInfo[i].getConsignorCode()))
					{
						// 出荷先コードをかきかえる
						wConsignorCode = workInfo[i].getConsignorCode();
						// 出荷先名称を取得する
						consignorName = getConsignorName();
					}
					wStrText.append(ReportOperation.format(consignorName) + tb);
					// 出荷予定日
					wStrText.append(ReportOperation.format(workInfo[i].getPlanDate()) + tb);
					// 出荷先コード
					wStrText.append(ReportOperation.format(workInfo[i].getCustomerCode()) + tb);
					if (i == 0 || !wCustomerCode.equals(workInfo[i].getCustomerCode()))
					{
						// 出荷先コードを書き換える
						wCustomerCode = workInfo[i].getCustomerCode();
						// 出荷先名称を取得する
						customerName = getCustomerName();
					}
					// 出荷先名称
					wStrText.append(ReportOperation.format(customerName) + tb);
					// 伝票No
					wStrText.append(ReportOperation.format(workInfo[i].getShippingTicketNo()) + tb);
					// 行No
					wStrText.append(workInfo[i].getShippingLineNo() + tb);
					// 商品コード
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					// 商品名称
					wStrText.append(ReportOperation.format(workInfo[i].getItemName1()) + tb);
					// ケース入数
					wStrText.append(workInfo[i].getEnteringQty() + tb);
					// ボール入数
					wStrText.append(workInfo[i].getBundleEnteringQty() + tb);
					// 作業予定総数
					wStrText.append(workInfo[i].getPlanQty() + tb);
					// 作業予定ケース数
					wStrText.append(DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty()) + tb);
					// 作業予定ピース数
					wStrText.append(DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty()));

					// データをファイルに出力する。
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}

			// ストリームを閉じる
			wPWriter.close();

			// UCXSingleを実行。（印刷実行）
			if (!executeUCX(JOBID_SHIPPING_LIST))
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
				wInfoReportFinder.close();
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
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private void setWorkInfoSearchKey(WorkingInformationSearchKey serachKey) throws ReadWriteException
	{
		// 検索キーをセットする。
		if (!StringUtil.isBlank(wConsignorCode))
		{
			serachKey.setConsignorCode(wConsignorCode);
		}
		if (!StringUtil.isBlank(wFromPlanDate))
		{
			serachKey.setPlanDate(wFromPlanDate, ">=");
		}
		if (!StringUtil.isBlank(wToPlanDate))
		{
			serachKey.setPlanDate(wToPlanDate, "<=");
		}
		if (!StringUtil.isBlank(wCustomerCode))
		{
			serachKey.setCustomerCode(wCustomerCode);
		}
		if (!StringUtil.isBlank(wTicketNo))
		{
			serachKey.setShippingTicketNo(wTicketNo);
		}
		if (!StringUtil.isBlank(wItemCode))
		{
			serachKey.setItemCode(wItemCode);
		}
		serachKey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		// 作業状態
		if (!StringUtil.isBlank(wStatusFlag))
		{
			if (wStatusFlag.equals(ShippingParameter.WORKSTATUS_UNSTARTING))
			{
				// 未開始
				serachKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);
			}
			else if (wStatusFlag.equals(ShippingParameter.WORKSTATUS_NOWWORKING))
			{
				// 作業中
				serachKey.setStatusFlag(ShippingPlan.STATUS_FLAG_NOWWORKING);
			}
			else if (wStatusFlag.equals(ShippingParameter.WORKSTATUS_FINISH))
			{
				// 完了
				serachKey.setStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETION);
			}
			else if (wStatusFlag.equals(ShippingParameter.WORKSTATUS_ALL))
			{
				// 全ての場合、削除以外を検索
				serachKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
			}
		}
	}

	/**
	 * 荷主先名称を取得するためのメソッドです。<BR>
	 * <BR>
	 * 荷主コードに対して、複数の荷主先名称があることが考えられるので
	 * 登録日時が一番新しいレコードの出荷先名称を返します。<BR>
	 * 
	 * @return 荷主名称
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private String getConsignorName() throws ReadWriteException
	{
		String consignorName = "";

		// 検索キーをセットする
		
		WorkingInformationReportFinder nameFinder = new WorkingInformationReportFinder(wConn);
		
		WorkingInformationSearchKey nameKey = new WorkingInformationSearchKey();
		this.setWorkInfoSearchKey(nameKey);
		nameKey.setConsignorNameCollect();
		nameKey.setRegistDateOrder(1, false);

		int nameCount =  nameFinder.search(nameKey);
		if (nameCount > 0)
		{
			WorkingInformation winfo[] = (WorkingInformation[])nameFinder.getEntities(1);

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
		String customerName = "";

		// 検索キーをセットする		
		WorkingInformationReportFinder nameFinder = new WorkingInformationReportFinder(wConn);
		
		WorkingInformationSearchKey nameKey = new WorkingInformationSearchKey();
		this.setWorkInfoSearchKey(nameKey);
		
		nameKey.setCustomerName1Collect();
		nameKey.setRegistDateOrder(1, false);

		int nameCount = nameFinder.search(nameKey);
		if (nameCount > 0)
		{
			WorkingInformation winfo[] = (WorkingInformation[]) nameFinder.getEntities(1);

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
