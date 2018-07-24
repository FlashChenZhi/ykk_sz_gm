package jp.co.daifuku.wms.idm.display.web.listbox.sessionret;
/*
 * Created on 2004/09/15
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.LocateFinder;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 空棚一覧リストボックス（移動ラック）用のデータを検索するためのクラスです。<BR>
 * 検索条件をパラメータとして受け取り、空棚一覧の検索を行います。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionIdmEmpLocationRet(Connection conn,IdmControlParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(IdmControlParameter param)</CODE>メソッドを呼び出し作業情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目なし<BR>
 *   <DIR>
 *     バンクNo<BR>
 *     ベイNo<BR>
 *     レベルNo<BR>
 *     状態フラグが空棚 <BR>
 *     エリアNoが移動ラックエリアNo <BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DMLOCATE <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を作業情報配列にセットし返します。<BR>
 * <BR>
 *   ＜表示項目＞
 *   <DIR>
 *     棚No<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/17</TD><TD>kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class SessionIdmEmpLocationRet extends SessionRet
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * 検索を行うための<CODE>find(IdmControlParameter stParam)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(IdmControlParameter stParam)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param conn       <code>Connection</code>
	 * @param stParam      <code>IdmControlParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionIdmEmpLocationRet(Connection conn, IdmControlParameter stParam) throws Exception
	{
		wConn = conn ;		
		find(stParam) ;
	}
	
	// Public methods ------------------------------------------------

	/**
	 * 在庫情報の検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから在庫情報を取得します。<BR>
	 *   3.在庫情報から表示データを取得し<CODE>IdmControlParameter</CODE>にセットします。<BR>
	 *   4.表示情報を返します。<BR>
	 * </DIR>
	 * @return 表示情報を含む<CODE>StockControlParameter</CODE>クラス
	 */
	public IdmControlParameter[] getEntities()
	{		
		IdmControlParameter[] resultArray = null ;
		Locate[] locate = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{
				locate = (Locate[])wFinder.getEntities(wStartpoint,wEndpoint) ;
				resultArray = convertToEmpLocateParams(locate) ;
			}
			catch (Exception e)
			{	    		
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		
		return resultArray;
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------		
	/**
	 * 入力されたパラメータをもとにSQL文を発行します。<BR>
	 * 検索を行う<code>StockFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param param      <code>IdmControlParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public void find(IdmControlParameter stParam) throws Exception
	{	
		String pBank = null;
		String pBay = null;
		String pLevel = null;
			
		LocateSearchKey stKey = new LocateSearchKey() ;
			
		if(!StringUtil.isBlank(stParam.getBankNo()))
		{
			pBank = stParam.getBankNo() ;
		}
		if(!StringUtil.isBlank(stParam.getBayNo()))
		{
			pBay = stParam.getBayNo() ;
		}
		if(!StringUtil.isBlank(stParam.getLevelNo()))
		{
			pLevel = stParam.getLevelNo() ;
		}
		// WmsParamより、移動ラック用のエリアNoを取得します。
		String w_AreaNo = WmsParam.IDM_AREA_NO;		
			
		// 検索条件をセットする
		stKey.KeyClear();
		stKey.setAreaNo(w_AreaNo);

		if (!StringUtil.isBlank(pBank))
		{
			stKey.setBankNo(pBank, "=");
		}
		if (!StringUtil.isBlank(pBay))
		{
			stKey.setBayNo(pBay, "=");
		}
		if (!StringUtil.isBlank(pLevel))
		{
			stKey.setLevelNo(pLevel, "=");
		}
		// 空棚のみ
		stKey.setStatusFlag(Locate.Locate_StatusFlag_EMPTY);
		// ロケーションNoの昇順に取得します。
		stKey.setLocationNoOrder(1, true);
						
		// LocateFinderオブジェクトを生成
		wFinder = new LocateFinder(wConn) ;
		// カーソルをオープン
		wFinder.open() ;
		
		// 検索実行
		int count = wFinder.search(stKey);
		wLength = count ;
		wCurrent = 0 ;
		
	}

	/**
	 * このメソッドは、<CODE>Stock</CODE>エンティティを<CODE>IdmControlParameter</CODE>パラメータに変換します。<BR>
	 * @param locate エンティティ配列
	 * @return IdmControlParameter[] 在庫情報をセットした<CODE>IdmControlParameter</CODE>パラメータ
	 */
	private IdmControlParameter[] convertToEmpLocateParams(Locate[] locate)
	{
		IdmControlParameter[] stParam = null ;
		if((locate != null) && (locate.length != 0))
		{
			stParam = new IdmControlParameter[locate.length] ;
			
			for(int i=0; i < locate.length; i++)
			{
				stParam[i] = new IdmControlParameter() ;
				stParam[i].setLocationNo(locate[i].getLocationNo()) ; // 棚No
				stParam[i].setBankNo(locate[i].getBankNo());	// バンクNo
				stParam[i].setBayNo(locate[i].getBayNo());	// ベイNo
				stParam[i].setLevelNo(locate[i].getLevelNo());	// レベルNo
			}
		}		
		return stParam ;
	}
}
//end of class
