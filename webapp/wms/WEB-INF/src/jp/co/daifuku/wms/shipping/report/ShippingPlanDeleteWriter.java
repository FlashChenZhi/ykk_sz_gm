// $Id: ShippingPlanDeleteWriter.java,v 1.4 2006/12/13 09:00:26 suresh Exp $
package jp.co.daifuku.wms.shipping.report;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * <CODE>ShippingPlanDeleteWriter</CODE>クラスは、出荷予定削除リストの帳票用データファイルを作成し、印刷を実行します。<BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.印刷編集処理(<CODE>startPrint()</CODE>メソッド) <BR>
 * <DIR>
 *   印刷処理を行う際に使用するＣＳＶファイルの作成を行います。 <BR>
 * <BR>
 *   <DIR>
 *   ＜パラメータ＞ <BR>
 *     <DIR>
 *     荷主コード <BR>
 *     出荷予定日 <BR>
 *     出荷先コード <BR>
 *     出荷先名称 <BR>
 *     更新日時 <BR>
 *     </DIR>
 *   ＜返却データ＞ <BR>
 *     <DIR>
 *     印刷状態 <BR>
 *     </DIR>
 *   </DIR>
 *   <DIR>
 * <BR>
 *   1.スケジュール処理<CODE>(ShippingPlanDeleteSCH)</CODE>クラスより得たパラメータより <BR>
 *   出荷予定情報<CODE>(Dnshippingplan)</CODE>を検索、 <BR>
 *   該当データが存在する場合、抽出した検索結果を元に帳票用データファイルの作成をし、 <BR>
 *   印刷実行クラスを呼び出します。 <BR>
 *   該当データが存在しない場合は、帳票データを作成せず印刷状態を不可として返却します。 <BR>
 * <BR>
 *     <DIR>
 *     ＜検索条件＞ <BR>
 *       <DIR>
 *       荷主コード <BR>
 *       出荷予定日 <BR>
 *       出荷先コード <BR>
 *       出荷先名称 <BR>
 *       更新日時 以降となるデータ <BR>
 *       </DIR>
 *     ＜抽出項目＞ <BR>
 *       <DIR>
 *       荷主コード <BR>
 *       荷主名称 <BR>
 *       出荷先コード <BR>
 *       出荷先名称 <BR>
 *       出荷予定日 <BR>
 *       伝票No <BR>
 *       行No <BR>
 *       商品コード <BR>
 *       商品名称 <BR>
 *       ケース入数 <BR>
 *       ボール入数 <BR>
 *       予定総数 <BR>
 *       予定ケース数 <BR>
 *       予定ピース数 <BR>
 *       区分 <BR>
 *       </DIR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 09:00:26 $
 * @author  $Author: suresh $
 */
public class ShippingPlanDeleteWriter extends CSVWriter
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode = null ;

	/**
	 * 開始出荷予定日
	 */
	private String wFromPlanDate = null ;

	/**
	 * 終了出荷予定日
	 */
	private String wToPlanDate = null ;

	/**
	 * 最終更新日時
	 */
	private Date wLastUpdateDate = null ;
	
	/**
	 * 最新の出荷先名称を取得するための出荷先コード
	 */
	private String wCustomerCode = null;
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 09:00:26 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * ShippingPlanDeleteWriter オブジェクトを構築します。
	 * @param conn <CODE>Connection</CODE>
	 */
	public ShippingPlanDeleteWriter(Connection conn)
	{
		super(conn);
	}

	// Public methods ------------------------------------------------
	/**
	 * 荷主コードに値をセットします。
	 * @param consignorCode セットする荷主コード
	 */
	public void setConsignorCode(String consignorCode)
	{
		wConsignorCode = consignorCode;
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
	 * 開始出荷予定日に値をセットします。
	 * @param fromPlanDate セットする開始出荷予定日
	 */
	public void setFromPlanDate(String fromPlanDate)
	{
		wFromPlanDate = fromPlanDate;
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
	 * 終了出荷予定日に値をセットします。
	 * @param toPlanDate セットする終了出荷予定日
	 */
	public void setToPlanDate(String toPlanDate)
	{
		wToPlanDate = toPlanDate;
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
	 * 最終更新日時に値をセットします。
	 * @param lastUpdateDate セットする最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate)
	{
		wLastUpdateDate = lastUpdateDate;
	}

	/**
	 * 最終更新日時を取得します。
	 * @return 最終更新日時
	 */
	public Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	/**
	 * 出荷予定削除用CSVファイルを作成し、印刷実行クラスを呼び出します。 <BR>
	 * パラメータで渡された検索条件より出荷予定情報<CODE>(Dnshippingplan)</CODE>を検索します。 <BR>
	 * 検索結果が1件未満の場合は印刷を行いません。 <BR>
	 * 印刷に成功した場合true、失敗した場合はfalseを返します。 <BR>
	 * <BR>
	 * @return boolean 印刷が成功したかどうか。
	 */
	public boolean startPrint()
	{
		ShippingPlanReportFinder shippingPlanReportFinder =  new ShippingPlanReportFinder(wConn);

		try
		{
			ShippingPlanSearchKey shippingPlanSearchKey = new ShippingPlanSearchKey();
			
			shippingPlanSearchKey = this.setSearchKey(shippingPlanSearchKey);

			// 印字順(出荷先コード、予定日、伝票No.、行No.)
			shippingPlanSearchKey.setCustomerCodeOrder(1,true);
			shippingPlanSearchKey.setPlanDateOrder(2,true);
			shippingPlanSearchKey.setShippingTicketNoOrder(3,true);
			shippingPlanSearchKey.setShippingLineNoOrder(4,true);

			// 検索実行
			int count = shippingPlanReportFinder.search(shippingPlanSearchKey);
			
			// 対象データなし
			if (count <= 0 )
			{
				// 印刷データはありませんでした。
				setMessage("6003010");
				return false;
			}

			// 最新名称取得用
			String consignorName = "";
			String customerName = "";
				
			// 最新の荷主名称を取得します
			consignorName = getDisplayConsignorName();
		
			if(!super.createPrintWriter(CSVWriter.FNAME_SHIPPING_DELETE))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_SHIPPING_DELETE);
			
			// 検索結果がなくなるまで、100件ごとにデータファイルを作成する。
			ShippingPlan[] shippingPlan = null ;
			while(shippingPlanReportFinder.isNext())
			{
				// 検索結果から、100件まで取得
				shippingPlan = (ShippingPlan[])shippingPlanReportFinder.getEntities(100);

				for (int i = 0; i < shippingPlan.length; i++)
				{

					wStrText.append(re + "");
	
					// 荷主コード
					wStrText.append(ReportOperation.format(shippingPlan[i].getConsignorCode()) + tb);
					// 荷主名称
					wStrText.append(ReportOperation.format(consignorName) + tb);
					// 出荷先コード
					wStrText.append(ReportOperation.format(shippingPlan[i].getCustomerCode()) + tb);
					// 出荷先コードが異なるときだけ検索し、出荷先名称を取得する
					if (i == 0 || !wCustomerCode.equals(shippingPlan[i].getCustomerCode()))
					{
						// 出荷先コードをかきかえる
						wCustomerCode = shippingPlan[i].getCustomerCode();
						// 出荷先名称を取得する
						customerName = getDisplayCustomerName();
					}
					wStrText.append(ReportOperation.format(customerName) + tb);
					// 出荷予定日
					wStrText.append(ReportOperation.format(shippingPlan[i].getPlanDate()) + tb);
					// 伝票No
					wStrText.append(ReportOperation.format(shippingPlan[i].getShippingTicketNo()) + tb);
					// 行No
					wStrText.append(shippingPlan[i].getShippingLineNo() + tb);
					// 商品コード
					wStrText.append(ReportOperation.format(shippingPlan[i].getItemCode()) + tb);
					// 商品名称
					wStrText.append(ReportOperation.format(shippingPlan[i].getItemName1()) + tb);
					// ケース入数
					wStrText.append(ReportOperation.format(Integer.toString(shippingPlan[i].getEnteringQty())) + tb);
					// ボール入数
					wStrText.append(shippingPlan[i].getBundleEnteringQty() + tb);
					// ホスト予定総数
					wStrText.append(shippingPlan[i].getPlanQty() + tb);
					// ホスト予定ケース数
					wStrText.append(DisplayUtil.getCaseQty(shippingPlan[i].getPlanQty(),shippingPlan[i].getEnteringQty()) + tb);
					// ホスト予定ピース数
					wStrText.append(DisplayUtil.getPieceQty(shippingPlan[i].getPlanQty(),shippingPlan[i].getEnteringQty()) + tb);
					// 区分
					wStrText.append(DisplayUtil.getTcDcValue(shippingPlan[i].getTcdcFlag()));
					// 書き込み
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
					
				}
			}

			// ストリームクローズ
			wPWriter.close();

			// UCXSingleを実行
			if (!executeUCX(JOBID_SHIPPING_DELETE))
			{
				// 印刷に失敗しました。ログを参照してください。
				return false;
			}

			// データファイルをバックアップフォルダへ移動
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{		
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				// オープンしたデータベースカーソルのクローズ処理を行う。
				shippingPlanReportFinder.close();
			}
			catch (ReadWriteException e)
			{	
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
	 * リストに表示するための荷主名称を取得します。<BR>
	 * 印刷データの検索条件で、登録日時が最新の出荷予定情報を検索し、<BR>
	 * 先頭のデータの荷主名称を返します。<BR>
	 * 
	 * @return	conignorName	荷主名称
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private String getDisplayConsignorName() throws ReadWriteException
	{
		String consignorName = "";
		
		// Finderインスタンス生成
		ShippingPlanReportFinder consignorFinder = new ShippingPlanReportFinder(wConn);
		ShippingPlanSearchKey consignorSearchKey = new ShippingPlanSearchKey();
		
		consignorSearchKey = this.setSearchKey(consignorSearchKey);	
		consignorSearchKey.setConsignorNameCollect("");
		consignorSearchKey.setRegistDateOrder(1, false);
		
		// 荷主名称検索
		int nameCount = consignorFinder.search(consignorSearchKey);
		if (nameCount > 0)
		{
			ShippingPlan[] shippingPlan = (ShippingPlan[]) consignorFinder.getEntities(1);

			if (shippingPlan != null && shippingPlan.length != 0)
			{
				consignorName = shippingPlan[0].getConsignorName();
			}
		}
		consignorFinder.close();
		
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
	private String getDisplayCustomerName() throws ReadWriteException
	{
		String customerName = "";

		// Finderインスタンス生成
		ShippingPlanReportFinder customerFinder = new ShippingPlanReportFinder(wConn);
		ShippingPlanSearchKey customerSearchKey = new ShippingPlanSearchKey();

		customerSearchKey = this.setSearchKey(customerSearchKey);	
		// 検索条件をセット		
		customerSearchKey.setCustomerCode(wCustomerCode);
		
		
		customerSearchKey.setCustomerName1Collect("");
		customerSearchKey.setRegistDateOrder(1, false);
		// 荷主名称検索
		customerFinder.open();
		int nameCount = customerFinder.search(customerSearchKey);
		if (nameCount > 0)
		{
			ShippingPlan[] shippingPlan = (ShippingPlan[]) customerFinder.getEntities(1);

			if (shippingPlan != null && shippingPlan.length != 0)
			{
				customerName = shippingPlan[0].getCustomerName1();
			}
		}
		customerFinder.close();

		return customerName;
	}
	
	/**
	 * 検索キーに検索条件をセットします。
	 * @param saerchKey 検索条件
	 * @return 検索キー
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private ShippingPlanSearchKey setSearchKey(ShippingPlanSearchKey saerchKey) throws ReadWriteException
	{
		// 検索条件をセット
		saerchKey.KeyClear();
		if(!StringUtil.isBlank(wConsignorCode))
		{
			saerchKey.setConsignorCode(wConsignorCode);
		}
		if (!StringUtil.isBlank(wFromPlanDate))
		{
			saerchKey.setPlanDate(wFromPlanDate, ">=");
		}
		if (!StringUtil.isBlank(wToPlanDate))
		{
			saerchKey.setPlanDate(wToPlanDate, "<=");
		}
		saerchKey.setLastUpdateDate(wLastUpdateDate, ">=");
		saerchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE);
		
		return saerchKey; 
		
	}
}
//end of class
