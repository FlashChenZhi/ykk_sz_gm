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
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR>
 * 実績情報Viewを検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * <B>1.検索処理(<CODE>SessionInstockReceiveResultDateRet(Connection, InstockReceiveParameter)</CODE>メソッド)<BR></B>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(InstockReceiveParameter)</CODE>メソッドを呼び出し実績情報Viewの検索を行います。<BR>
 * <BR>
 *   ＜入力データ＞*必須項目
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th><tr>
 *     <tr><td>*</td><td>作業区分</td><td>：</td><td>01(入荷)</td></tr>
 *     <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>開始入荷日</td><td>：</td><td>FromInstockReceiveDate</td></tr>
 *     <tr><td></td><td>終了入荷日</td><td>：</td><td>ToInstockReceiveDate</td></tr>
 *     <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td></td><td>状態フラグ</td><td>：</td><td>StatusFlag</td></tr>
 *   </table>
 *   </DIR>
 *   ＜検索テーブル＞
 *   <DIR>
 *     DVRESULTVIEW<BR>
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
 *     <tr><td></td><td>入荷日</td><td>：</td><td>InstockReceiveDate</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:13 $
 * @author  $Author: mori $
 */
public class SessionInstockReceiveResultDateRet extends SessionRet
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
	public SessionInstockReceiveResultDateRet(Connection conn, InstockReceiveParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}
	
	/**
	 * <CODE>DNRESULTVIEW</CODE>の検索結果を返します。
	 * <DIR>
	 * ＜検索結果＞
	 * 入荷日<BR>
	 * </DIR>
	 * @return DNRESULTVIEWの検索結果
	 */
	public Parameter[] getEntities()
	{
		if (wEndpoint >= getLength())
		{
			wFraction = getLength() - (wEndpoint - wCondition); // 最終ページでの件数の端数
			wEndpoint = getLength();
		}
		InstockReceiveParameter[] resultArray = null;
		ResultView[] temp = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{			
			try
			{
				temp = (ResultView[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (InstockReceiveParameter[]) convertToInstockParams(temp);
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
	 * 検索を行う<code>ResultViewFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param stParam      <code>InstockReceiveParameter</code> 検索条件を含むパラメータ
	 */
	private void find(InstockReceiveParameter stParam) throws Exception
	{
		ResultViewSearchKey sKey = new ResultViewSearchKey();
		//検索条件をセットします
		
		//作業区分
		sKey.setJobType(ResultView.JOB_TYPE_INSTOCK);
		
		//荷主コード				
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}
			
		if (!StringUtil.isBlank(stParam.getFromInstockReceiveDate()))
		{
			//開始入荷日をセット。
			sKey.setWorkDate(stParam.getFromInstockReceiveDate());
		}
		if (!StringUtil.isBlank(stParam.getToInstockReceiveDate()))
		{
			//終了入荷日
			sKey.setWorkDate(stParam.getToInstockReceiveDate());
		}
		
		sKey.setWorkDate("","IS NOT NULL");
		
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
		
		
		//取得順をセットします
		sKey.setWorkDateCollect("");
		//グループ順をセットします
		sKey.setWorkDateGroup(1);
		//ソート順をセットします
		sKey.setWorkDateOrder(1, true);
		
		if(stParam.getSearchStatus() != null && stParam.getSearchStatus().length > 0)
		{
			String[] search = new String[stParam.getSearchStatus().length];
			for(int i = 0; i < stParam.getSearchStatus().length; i++)
			{
				if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = ResultView.STATUS_FLAG_UNSTART;
				}
				else if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_STARTED))
				{
					search[i] = ResultView.STATUS_FLAG_START;
				}
				else if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
				{
					search[i] = ResultView.STATUS_FLAG_NOWWORKING;
				}
				else if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = ResultView.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if(stParam.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = ResultView.STATUS_FLAG_COMPLETION;
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
			sKey.setStatusFlag(ResultView.STATUS_FLAG_DELETE, "!=");
		}
		wFinder = new ResultViewFinder(wConn);
		//カーソルオープン
		wFinder.open();
		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;
		
	}
		
	/**
	 * このクラスは ResultView エンティティ を InstockReceiveParameter パラメータに設定する。 <BR>
	 * 
	 * @param instockVIew 実績情報View
	 * @return InstockReceiveParameter[] 実績情報Viewをセットした<CODE>InstockReceiveParameter</CODE>クラス
	 */
	private Parameter[] convertToInstockParams(Entity[] ety)
	{
	
		ResultView[] instockresult = (ResultView[]) ety;
		if (instockresult == null || instockresult.length==0)
		{	
		 	return null;
		}
			InstockReceiveParameter[] stParam = new InstockReceiveParameter[instockresult.length];
			for (int i = 0; i < instockresult.length; i++)
			{
				stParam[i] = new InstockReceiveParameter();
				stParam[i].setInstockReceiveDate(instockresult[i].getWorkDate()); //入荷受付日
				
			}
		
		return stParam;
	}
	
}
//end of class
