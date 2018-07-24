package jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret;

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/*
 * Created on 2004/11/02
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * 入荷予定情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * <B>1.検索処理(<CODE>SessionInstockReceivePlanRet(Connection, InstockReceiveParameter)</CODE>メソッド)<BR></B>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(InstockReceiveParameter)</CODE>メソッドを呼び出し入荷予定情報の検索を行います。<BR>
 * <BR>
 *   ＜入力データ＞*必須項目
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th><tr>
 *     <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>荷主名称</td><td>：</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>仕入先名称</td><td>：</td><td>SupplierName</td></tr>
 *     <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td></td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>出荷先名称</td><td>：</td><td>CustomerName</td></tr>
 *     <tr><td></td><td>入荷伝票No</td><td>：</td><td>InstockTicketNo</td></tr>
 *     <tr><td></td><td>状態フラグ</td><td>：</td><td>StatusFlag</td></tr>
 *   </table>
 *   </DIR>
 *   ＜検索テーブル＞
 *   <DIR>
 *     DNINSTOCKPLAN<BR>
 *   </DIR>
 * </DIR>
 * 
 * <B>2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR></B>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を<CODE>InstockReceiveParameter</CODE>配列にセットし返します。<BR>
 * <BR>
 *   ＜返却データ＞
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th></tr>
 *     <tr><td></td><td>荷主ｺｰﾄﾞ</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>荷主名称</td><td>：</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>仕入先ｺｰﾄﾞ</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>仕入先名称</td><td>：</td><td>SupplierName</td></tr>
 *     <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td></td><td>TC/DC区分名称</td><td>：</td><td>TcdcName</td></tr>
 *     <tr><td></td><td>出荷先ｺｰﾄﾞ</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>出荷先名称</td><td>：</td><td>CustomerName</td></tr>
 *     <tr><td></td><td>入荷伝票No</td><td>：</td><td>InstockTicketNo</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/11/02</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * @author suresh kayamboo
 * @version 2004/11/02
 */
public class SessionInstockReceivePlanRet extends SessionRet
{
	/**
	 * 検索を行うための<CODE>find(InstockReceiveParameter param)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(InstockReceiveParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * 
	 * @@param conn       <code>Connection</code>
	 * @@param irParam      <code>InstockReceiveParameter</code> 検索条件を含むパラメータ
	 * @@throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionInstockReceivePlanRet(Connection conn, InstockReceiveParameter irParam) throws Exception
	{
		wConn = conn;
		find(irParam);
	}
	
	/**
	 * <CODE>DNINSTOCKPLAN</CODE>の検索結果を返します。
	 * <DIR>
	 * ＜検索結果＞
	 * 荷主コード<BR>
	 * 荷主名称<BR>
	 * 入荷予定日<BR>
	 * 仕入先コード<BR>
	 * 仕入先名称<BR>
	 * TC/DC区分<BR>
	 * TC/DC区分名称<BR>
	 * 出荷先コード<BR>
	 * 出荷先名称<BR>
	 * 入荷伝票No<BR>
	 * </DIR>
	 * @return DNINSTOCKPLANの検索結果
	 */
	public Parameter[] getEntities()
	{
		InstockReceiveParameter[] resultArray = null;
		InstockPlan[] instockPlan = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				instockPlan = (InstockPlan[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (InstockReceiveParameter[]) convertToInStockPlanParams(instockPlan);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//Public------------------------------------------------------
	/**
	 * 入力されたパラメータをもとにSQL文を発行します。<BR>
	 * 検索を行う<code>InSotckPlanFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @@param irParam      <code>InStockRetrievalParameter</code> 検索条件を含むパラメータ
	 */
	private void find(InstockReceiveParameter irParam) throws Exception
	{
		if ((irParam != null) || !irParam.equals(""))
		{
			InstockPlanSearchKey sKey = new InstockPlanSearchKey();
				
			// 条件をセットします
			if (!StringUtil.isBlank(irParam.getConsignorCode()))
			{					
				sKey.setConsignorCode(irParam.getConsignorCode()); //荷主コード
			}
			if (!StringUtil.isBlank(irParam.getConsignorName()))
			{
				sKey.setConsignorName(irParam.getConsignorName()); //荷主名
			}
			if (!StringUtil.isBlank(irParam.getPlanDate()))
			{
				sKey.setPlanDate(irParam.getPlanDate()); //予定日
			}
			if (!StringUtil.isBlank(irParam.getSupplierCode()))
			{
				sKey.setSupplierCode(irParam.getSupplierCode()); //仕入先コード
			}
			if (!StringUtil.isBlank(irParam.getSupplierName()))
			{
				sKey.setSupplierName1(irParam.getSupplierName()); //仕入先名
			}
			if (!StringUtil.isBlank(irParam.getTcdcFlag()))
			{
				if (irParam.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_CROSSTC))
				{
					sKey.setTcdcFlag(SystemDefine.TCDC_FLAG_CROSSTC); //クロスＴＣ
				}
				if (irParam.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_DC))
				{
					sKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC); //ＤＣ
				}
				if (irParam.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_TC))
				{
					sKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC); //ＴＣ
				}
			}
			if (!StringUtil.isBlank(irParam.getCustomerCode()))
			{
				sKey.setCustomerCode(irParam.getCustomerCode()); //出荷先コード
			}
			if (!StringUtil.isBlank(irParam.getCustomerName()))
			{
				sKey.setCustomerName1(irParam.getCustomerName()); //出荷先名称
			}
			if (!StringUtil.isBlank(irParam.getInstockTicketNo()))
			{
				sKey.setInstockTicketNo(irParam.getInstockTicketNo()); //伝票
			}
			
			// 作業状態
			if(irParam.getSearchStatus() != null && irParam.getSearchStatus().length > 0)
			{
				String[] search = new String[irParam.getSearchStatus().length];
				for(int i = 0; i < irParam.getSearchStatus().length; ++i)
				{
					if(irParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
					{
						search[i] = InstockPlan.STATUS_FLAG_UNSTART;
					}
					else if(irParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_STARTED))
					{
						search[i] = InstockPlan.STATUS_FLAG_START;
					}
					else if(irParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
					{
						search[i] = InstockPlan.STATUS_FLAG_NOWWORKING;
					}
					else if(irParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION))
					{
						search[i] = InstockPlan.STATUS_FLAG_COMPLETE_IN_PART;
					}
					else if(irParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
					{
						search[i] = InstockPlan.STATUS_FLAG_COMPLETION;
					}
					else
					{
						search[i] = "*";
					}
				}
				sKey.setStatusFlag(search);
			}
			else
			{
				sKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			}
			// グループ順をセットします
			sKey.setConsignorCodeGroup(1);
			sKey.setPlanDateGroup(2);
			sKey.setSupplierCodeGroup(3);
			sKey.setTcdcFlagGroup(4);
			sKey.setCustomerCodeGroup(5);
			sKey.setInstockTicketNoGroup(6);
				
			// 取得順をセットします
			sKey.setConsignorCodeCollect("");
			sKey.setPlanDateCollect("");
			sKey.setSupplierCodeCollect("");
			sKey.setTcdcFlagCollect("");
			sKey.setCustomerCodeCollect("");
			sKey.setInstockTicketNoCollect("");
			
			// ソート順をセットします
			sKey.setPlanDateOrder(1, true);
			sKey.setSupplierCodeOrder(2, true);
			sKey.setTcdcFlagOrder(3, false);
			sKey.setCustomerCodeOrder(4, true);
			sKey.setInstockTicketNoOrder(5, true);
				
			wFinder = new InstockPlanFinder(wConn);
			wFinder.open();
			int count = wFinder.search(sKey);
			wLength = count;
			wCurrent = 0;
		}
	}

	/**
	 * このクラスは InStockPlan エンティティ を InstockReceiveParameter パラメータに変換します。 <BR>
	 * 
	 * @param inStockPlan 入荷予定情報
	 * @return InstockReceiveParameter[] 入荷予定情報をセットした<CODE>InstockReceiveParameter</CODE>クラス
	 */
	private Parameter[] convertToInStockPlanParams(Entity[] ety) throws ReadWriteException
	{
		InstockPlan[] instockPlan = (InstockPlan[]) ety;
		InstockReceiveParameter[] resultParam = null;

		if ((instockPlan != null) && (instockPlan.length != 0))
		{
			// 名称を取得するために検索を行う
			InstockPlanReportFinder nameFinder = new InstockPlanReportFinder(wConn);
			InstockPlanSearchKey nameKey = new InstockPlanSearchKey();

			Vector vec = new Vector();
			
			for (int i = 0; i < instockPlan.length; i++)
			{
				String consignorName = "";
				String supplierName = "";
				String customerName = "";
				// 最新の名称を取得する
				nameKey.KeyClear();
				nameKey.setConsignorCode(instockPlan[i].getConsignorCode());
				nameKey.setPlanDate(instockPlan[i].getPlanDate());
				nameKey.setSupplierCode(instockPlan[i].getSupplierCode());
				nameKey.setTcdcFlag(instockPlan[i].getTcdcFlag());
				nameKey.setCustomerCode(instockPlan[i].getCustomerCode());
				nameKey.setInstockTicketNo(instockPlan[i].getInstockTicketNo());
				nameKey.setConsignorNameCollect("");
				nameKey.setSupplierName1Collect("");
				nameKey.setCustomerName1Collect("");
				nameKey.setRegistDateOrder(1, false);
				nameFinder.open();
				int nameCount = nameFinder.search(nameKey);
				if (nameCount > 0)
				{
					InstockPlan instPlan[] = (InstockPlan[]) nameFinder.getEntities(1);

					if (instPlan != null && instPlan.length != 0)
					{
						consignorName = instPlan[0].getConsignorName();
						supplierName = instPlan[0].getSupplierName1();
						customerName = instPlan[0].getCustomerName1();
					}
				}
				nameFinder.close();
				
				InstockReceiveParameter tempParam = new InstockReceiveParameter();
				
				//荷主コード
				tempParam.setConsignorCode(instockPlan[i].getConsignorCode());
				//荷主名	
				tempParam.setConsignorName(consignorName);
				//入荷予定日
				tempParam.setPlanDate(instockPlan[i].getPlanDate());
				//仕入先コード
				tempParam.setSupplierCode(instockPlan[i].getSupplierCode());
				//仕入先名
				tempParam.setSupplierName(supplierName);

				//TC/DC区分・TC/DC区分(名称)
				if (!StringUtil.isBlank(instockPlan[i].getTcdcFlag()))
				{
					if (instockPlan[i].getTcdcFlag().equals(InstockPlan.TCDC_FLAG_CROSSTC))
					{
						//クロス
						tempParam.setTcdcName(DisplayUtil.getTcDcValue(InstockPlan.TCDC_FLAG_CROSSTC));
						tempParam.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
					}
					else if (instockPlan[i].getTcdcFlag().equals(InstockPlan.TCDC_FLAG_DC))
					{
						//DC
						tempParam.setTcdcName(DisplayUtil.getTcDcValue(InstockPlan.TCDC_FLAG_DC));
						tempParam.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
					}
					else if (instockPlan[i].getTcdcFlag().equals(InstockPlan.TCDC_FLAG_TC))
					{
						//TC
						tempParam.setTcdcName(DisplayUtil.getTcDcValue(InstockPlan.TCDC_FLAG_TC));
						tempParam.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC);
					}
				}
				//出荷先コード
				tempParam.setCustomerCode(instockPlan[i].getCustomerCode());
				//出荷先名
				tempParam.setCustomerName(customerName);
				//伝票
				tempParam.setInstockTicketNo(instockPlan[i].getInstockTicketNo());
				
				vec.add(tempParam);
				
			}
			resultParam = new InstockReceiveParameter[vec.size()];
			vec.copyInto(resultParam);

		}
		return resultParam;
	}

}
//end of class
