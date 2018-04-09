// $Id: ShippingPlanWriter.java,v 1.4 2006/12/13 09:00:26 suresh Exp $

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.report;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * 
 * <CODE>ShippingPlanWriter</CODE>クラスは、出荷予定リストの帳票用データファイルを作成し、印刷を実行します。<BR>
 * <CODE>ShippingPlanListSCH</CODE>クラスでセットされた条件にもとづき、出荷予定情報を検索し、<BR>
 * 結果を出荷予定リスト用の帳票ファイルとして作成します。<BR>
 * このクラスは、以下の処理を行います。<BR>
 * <BR>
 * 帳票用データファイル作成処理(<CODE>startPrint()</CODE>メソッド)<BR>
 *	<DIR>
 *	1.<CODE>ShippingPlanListSCH</CODE>クラスからセットされた条件で出荷予定情報の件数を検索します。<BR>
 *	2.結果が1件以上なら、帳票ファイルを作成します。0件だった場合はfalseを返して終了します。<BR>
 *	3.印刷処理を実行します。<BR>
 *  4.印刷処理が正常だった場合はtrueを返します。<BR>
 *    印刷処理中にエラーが発生した場合はfalseを返します。<BR>
 *<BR>
 * 	＜パラメータ＞*必須入力<BR>
 *  <DIR>
 * 	荷主コード*<BR>
 * 	開始出荷予定日<BR>
 * 	終了出荷予定日<BR>
 * 	出荷先コード<BR>
 * 	伝票No.<BR>
 *  商品コード<BR>
 *  </DIR>
 * <BR>
 * 	＜検索条件＞<BR>
 *  <DIR>
 * 	荷主コード*<BR>
 * 	開始出荷予定日<BR>
 * 	終了出荷予定日<BR>
 * 	出荷先コード<BR>
 * 	伝票No.<BR>
 *  商品コード<BR>
 *  状態＝削除以外<BR>
 *  </DIR>
 * <BR>
 *	＜印刷データ＞<BR>
 *  </DIR>
 *	印刷項目：ＤＢ項目<BR>
 *	荷主：荷主コード + 荷主名称<BR>
 *	出荷予定日：出荷予定日<BR>
 *	出荷先：出荷先コード + 出荷先名称<BR>
 *	伝票No.：出荷伝票No.<BR>
 *	行No.：出荷伝票行No.<BR>
 *	商品コード：商品コード<BR>
 *	商品名称：商品名称<BR>
 *	ケース入数：ケース入数<BR>
 *	ボール入数：ボール入数<BR>
 *	ホスト予定総数：出荷予定数<BR>
 *	ホスト予定ケース数：出荷予定数／ケース入数<BR>
 *	ホスト予定ピース数：出荷予定数％ケース入数<BR>
 *	実績ケース数：出荷実績数／ケース入数<BR>
 *	実績ピース数：出荷実績数％ケース入数<BR>
 *  <DIR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/12/13 09:00:26 $
 * @author  $Author: suresh $
 */
public class ShippingPlanWriter extends CSVWriter
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
	 * 出荷先コード	
	 */
	private String wCustomerCode = null ;

	/**
	 * 伝票No.
	 */
	private String wShippingTicketNo = null ;

	/**
	 * 商品コード
	 */
	private String wItemCode = null ;
	
	/**
	 * 状態フラグ
	 */
	protected String wStatusFlag = "";
	
	/**
	 * 表示する荷主名称
	 */
	private String wConsignorName = null ;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/12/13 09:00:26 $") ;
	}

	// Constructors --------------------------------------------------
	/**
	 * ShippingPlanWriter オブジェクトを構築します。
	 * @param conn <CODE>Connection</CODE>
	 */
	public ShippingPlanWriter(Connection conn)
	{
		super(conn);
	}
	
	// Public methods ------------------------------------------------
	/**
	 * 荷主コードに値をセットします。
	 * @param consignorcode セットする荷主コード
	 */
	public void setConsignorCode(String consignorcode)
	{
		wConsignorCode = consignorcode;
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
	 * 荷主名称に値をセットします。
	 * @param consignorname セットする荷主名称
	 */
	public void setConsignorName(String consignorname)
	{
		wConsignorName = consignorname;
	}

	/**
	 * 荷主名称を取得します。
	 * @return 荷主名称
	 */
	public String getConsignorName()
	{
		return wConsignorName;
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
	 * 出荷先コードに値をセットします。
	 * @param customercode セットする出荷先コード
	 */
	public void setCustomerCode(String customercode)
	{
		wCustomerCode = customercode;
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
	 * 伝票No.に値をセットします。
	 * @param shippingTicketNo セットする伝票No.
	 */
	public void setShippingTicketNo(String shippingTicketNo)
	{
		wShippingTicketNo = shippingTicketNo;
	}

	/**
	 * 伝票No.を取得します。
	 * @return 伝票No.
	 */
	public String getShippingTicketNo()
	{
		return wShippingTicketNo;
	}

	/**
	 * 商品コードに値をセットします。
	 * @param itemcode セットする商品コード
	 */
	public void setItemCode(String itemcode)
	{
		wItemCode = itemcode;
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
		ShippingPlanHandler handler = new ShippingPlanHandler(wConn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();
		// 検索条件をセットし、件数取得を行う
		setSearchKey(searchKey);
		return handler.count(searchKey);

	}
	
	/**
	 * 出荷予定リスト用CSVファイルを作成し、印刷を実行します。<BR>
	 * セットされた条件で出荷予定情報の件数を検索します。<BR>
	 * 結果が1件以上なら、帳票ファイルを作成します。0件だった場合はfalseを返して終了します。<BR>
	 * 印刷処理を実行します。<BR>
	 * 印刷処理が正常に終了したら、trueを返します。<BR>
	 * 印刷処理中にエラーが発生した場合はfalseを返します。<BR>
	 * @return boolean 印刷が成功したかどうか。
	 */
	public boolean startPrint()
	{	
		ShippingPlanReportFinder shippingPlanReportFinder = null;	
		try
		{
			ShippingPlanSearchKey shippingPlanSearchKey = new ShippingPlanSearchKey(); 
		
			this.setSearchKey(shippingPlanSearchKey);
			
			shippingPlanSearchKey.setPlanDateOrder(1, true);
			shippingPlanSearchKey.setCustomerCodeOrder(2, true);
			shippingPlanSearchKey.setShippingTicketNoOrder(3, true);
			shippingPlanSearchKey.setShippingLineNoOrder(4, true);		
			
			shippingPlanReportFinder =  new ShippingPlanReportFinder(wConn);
			
			// 検索実行
			int count = shippingPlanReportFinder.search(shippingPlanSearchKey);

			// 対象データなし
			if (count <= 0 )
			{
				// 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}
			
			// 表示する荷主名称確定
			getDisplayConsignorName();

			if(wConsignorName == null)
			{
				// 印刷データはありませんでした。
				wMessage = "6003010";
				return false;
			}
			
			if(!super.createPrintWriter(CSVWriter.FNAME_SHIPPING_PLAN))
			{
				return false;
			}
            
            // 出力ファイルのヘッダーを作成
            wStrText.append(CSVWriter.HEADER_SHIPPING_PLAN);
			
			ShippingPlan[] shippingPlan = null ;	
			// 検索結果がなくなるまで、100件ごとにデータファイルを作成する。
			while(shippingPlanReportFinder.isNext())
			{
				// 検索結果から、100件まで取得
				shippingPlan = (ShippingPlan[])shippingPlanReportFinder.getEntities(100);

				for (int i = 0; i < shippingPlan.length; i++)
				{
					wStrText.append(re + "");
	
					// 荷主コード
					wStrText.append(ReportOperation.format(shippingPlan[i].getConsignorCode()) + tb);
					// 荷主名
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					// 出荷予定日
					wStrText.append(ReportOperation.format(shippingPlan[i].getPlanDate()) + tb);
					// 出荷先コード
					wStrText.append(ReportOperation.format(shippingPlan[i].getCustomerCode()) + tb);
					// 出荷先名称
					wStrText.append(ReportOperation.format(shippingPlan[i].getCustomerName1()) + tb);
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
					// 実績数ケース
					wStrText.append(DisplayUtil.getCaseQty(shippingPlan[i].getResultQty(),shippingPlan[i].getEnteringQty()) + tb);
					// 実績数ピース
					wStrText.append(DisplayUtil.getPieceQty(shippingPlan[i].getResultQty(),shippingPlan[i].getEnteringQty()) + tb);
					// 状態
					wStrText.append(ReportOperation.format(DisplayUtil.getShippingPlanStatusValue(shippingPlan[i].getStatusFlag())));	
					// 書き込み
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}
			// ストリームクローズ
			wPWriter.close();
			// UCXSingleを実行
			if (!executeUCX(JOBID_SHIPPING_PLAN))
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
				shippingPlanReportFinder.close();
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
	 * リストに表示するための荷主名称を取得します。<BR>
	 * 印刷データの検索条件で、登録日時が最新の出荷予定情報を検索し、<BR>
	 * 先頭のデータの荷主名称をセットします。。<BR>
	 * 
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private void getDisplayConsignorName() throws ReadWriteException
	{
		
		// Finderインスタンス生成
		ShippingPlanReportFinder consignorFinder = new ShippingPlanReportFinder(wConn);
		ShippingPlanSearchKey shippingPlanSearchKey = new ShippingPlanSearchKey();
		
		setSearchKey(shippingPlanSearchKey);
		shippingPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		shippingPlanSearchKey.setRegistDateOrder(1, false);
		
		// 荷主名称検索
		int nameCount = consignorFinder.search(shippingPlanSearchKey);
		if (nameCount > 0)
		{
			ShippingPlan[] shippingPlan = (ShippingPlan[]) consignorFinder.getEntities(1);

			if (shippingPlan != null && shippingPlan.length != 0)
			{
				wConsignorName = shippingPlan[0].getConsignorName();
			}
		}
		consignorFinder.close();
		
	}
	
	/**
	 * 検索キーに検索条件をセットします。
	 * @param saerchKey 検索条件
	 * @return 検索キー
	 * @throws ReadWriteException データベースへのアクセスにおいてエラーがおきた場合に通知されます。
	 */
	private void setSearchKey(ShippingPlanSearchKey saerchKey) throws ReadWriteException
	{
		// 検索条件をセット
		saerchKey.KeyClear();
		if(!StringUtil.isBlank(wConsignorCode))
		{
			saerchKey.setConsignorCode(wConsignorCode);
		}
		if(!StringUtil.isBlank(wFromPlanDate))
		{
			saerchKey.setPlanDate(wFromPlanDate, ">=");
		}
		if(!StringUtil.isBlank(wToPlanDate))
		{
			saerchKey.setPlanDate(wToPlanDate, "<=");
		}
		if(!StringUtil.isBlank(wCustomerCode))
		{
			saerchKey.setCustomerCode(wCustomerCode);
		}
		if(!StringUtil.isBlank(wShippingTicketNo))
		{
			saerchKey.setShippingTicketNo(wShippingTicketNo);
		}
		if(!StringUtil.isBlank(wItemCode))
		{
			saerchKey.setItemCode(wItemCode);
		}
		// 作業状態
		if (!StringUtil.isBlank(this.getStatusFlag()))
		{
			if (this.getStatusFlag().equals(ShippingParameter.WORKSTATUS_UNSTARTING))
			{
				// 未開始
				saerchKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
			}
			else if (this.getStatusFlag().equals(ShippingParameter.WORKSTATUS_NOWWORKING))
			{
				// 作業中
				saerchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
			}
			else if (this.getStatusFlag().equals(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART))
			{
				// 保留
				saerchKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if (this.getStatusFlag().equals(ShippingParameter.WORKSTATUS_FINISH))
			{
				// 完了
				saerchKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
			}
			else if (this.getStatusFlag().equals(ShippingParameter.WORKSTATUS_ALL))
			{
				//全ての場合は削除以外
				saerchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
			}
		} 
		
	}

}
//end of class
