package jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret;
/*
 * Created on 2004/09/27 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */

import java.sql.Connection;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR>
 * 入荷予定情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * <B>1.検索処理(<CODE>SessionInstockReceivePlanDateRet(Connection, InstockReceiveParameter)</CODE>メソッド)<BR></B>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(InstockReceiveParameter)</CODE>メソッドを呼び出し入荷予定情報の検索を行います。<BR>
 * <BR>
 *   ＜入力データ＞*必須項目
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th><tr>
 *     <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>開始入荷受付日</td><td>：</td><td>FromInstockReceiveDate</td></tr>
 *     <tr><td></td><td>終了入荷受付日</td><td>：</td><td>ToInstockReceiveDate</td></tr>
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
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
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/11/01</TD>
 * <TD>Muneendra</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @author $Author suresh kayamboo
 * @version $Revision 1.2 2004/09/27
 */
public class SessionInstockReceivePlanDateRet extends SessionRet
{
	/**
	 * 検索を行うための<CODE>find(InStockReceiveParameter param)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(InStockReceiveParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      <code>InStockReceiveParameter</code> 検索条件を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionInstockReceivePlanDateRet(Connection conn, InstockReceiveParameter stParam)throws Exception
	{
		wConn = conn;
		find(stParam);
	}
	
	
	/**
	 * <CODE>DNINSTOCKPLAN</CODE>の検索結果を返します。
	 * <DIR>
	 * ＜検索結果＞
	 * 入荷予定日<BR>
	 * </DIR>
	 * @return DNINSTOCKPLANの検索結果
	 */
	public Parameter[] getEntities()
	{
		InstockReceiveParameter[] resultArray = null;
		InstockPlan[] instock = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{			
			try
			{
				instock = (InstockPlan[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (InstockReceiveParameter[]) convertToInstockParams(instock);
			} catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	/**
	 * 入力されたパラメータをもとにSQL文を発行します。<BR>
	 * 検索を行う<code>InStockPlanFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param param      <code>InstockReceiveParameter</code> 検索条件を含むパラメータ
	 */
	public void find(InstockReceiveParameter stParam) throws Exception
	{
			InstockPlanSearchKey sKey = new InstockPlanSearchKey();
			//検索実行
			//荷主コード
			if (!StringUtil.isBlank(stParam.getConsignorCode()))
			{
				sKey.setConsignorCode(stParam.getConsignorCode());
			}
			// TC/DC区分
			if (!StringUtil.isBlank(stParam.getTcdcFlag()))
			{
				if(InstockReceiveParameter.TCDC_FLAG_TC.equals(stParam.getTcdcFlag()))
				{
					sKey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC);
				}
				else if(InstockReceiveParameter.TCDC_FLAG_DC.equals(stParam.getTcdcFlag()))
				{
					sKey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
				}
				else if(InstockReceiveParameter.TCDC_FLAG_CROSSTC.equals(stParam.getTcdcFlag()))
				{
					sKey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
				}
				else if(InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC.equals(stParam.getTcdcFlag()))
				{
					sKey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC,"!=");
				}
			
			}
			// 入荷予定日がセットされている場合
			if (!StringUtil.isBlank(stParam.getPlanDate()))
			{
				sKey.setPlanDate(stParam.getPlanDate());
			}
			else
			{
				if (!StringUtil.isBlank(stParam.getFromPlanDate()))
				{
					//開始入荷予定日をセット。
					sKey.setPlanDate(stParam.getFromPlanDate());
				}
				if (!StringUtil.isBlank(stParam.getToPlanDate()))
				{
					//終了入荷予定日
					sKey.setPlanDate(stParam.getToPlanDate());
				}
				sKey.setPlanDate("","IS NOT NULL");
			}
				
			
			// 取得順をセットします
			sKey.setPlanDateCollect("");
			// グループ順をセットします
			sKey.setPlanDateGroup(1);
			// ソート順をセットします
			sKey.setPlanDateOrder(1, true);
			
			if(stParam.getSearchStatus() != null && stParam.getSearchStatus().length > 0)
			{
				String[] search = new String[stParam.getSearchStatus().length];
				for(int i = 0; i < stParam.getSearchStatus().length; i++)
				{
					if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
					{
						search[i] = InstockPlan.STATUS_FLAG_UNSTART;
					}
					else if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_STARTED))
					{
						search[i] = InstockPlan.STATUS_FLAG_START;
					}
					else if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
					{
							search[i] = InstockPlan.STATUS_FLAG_NOWWORKING;
					}
					else if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION))
					{
						search[i] = InstockPlan.STATUS_FLAG_COMPLETE_IN_PART;
					}
					else if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
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
			wFinder = new InstockPlanFinder(wConn);
			//カーソルオープン
			wFinder.open();
			int count = wFinder.search(sKey);
			wLength = count;
			wCurrent = 0;

	}
	
	/**
	 * このクラスは InStockPlan エンティティ を InstockReceiveParameter パラメータに変換します。 <BR>
	 * 
	 * @param instockPlan 入荷予定情報
	 * @return InstockReceiveParameter[] 入荷予定情報をセットした<CODE>InstockReceiveParameter</CODE>クラス
	 */
	private Parameter[] convertToInstockParams(Entity[] ety)
	{
	
		InstockPlan[] instockPlan = (InstockPlan[]) ety;
		if (instockPlan == null || instockPlan.length==0)
		{	
		 	return null;
		}
		InstockReceiveParameter[] stParam = new InstockReceiveParameter[instockPlan.length];
			for (int i = 0; i < instockPlan.length; i++)
			{
				stParam[i] = new InstockReceiveParameter();				
				stParam[i].setPlanDate(instockPlan[i].getPlanDate()); //入荷予定日
			
			}
		
		return stParam;
	}
}
//end of class
