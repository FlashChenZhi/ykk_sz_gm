// $Id: FunctionListBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.listbox.functionlist;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.dbhandler.ResultMap;
import jp.co.daifuku.dbhandler.ScrollHandler;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.DispResourceMap;
import jp.co.daifuku.tools.util.ToolParam;

/** <jp>
 * サブメニュー一覧の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * This screen class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </en> */
public class FunctionListBusiness extends FunctionList implements ToolConstants, TableColumns
{
	// Class fields --------------------------------------------------
    /**
     * SCROLLHANDLERをセッションに保持するキー
     */ 
    private final String KEY_SCROLLHANDLER = "KEY_SCROLLHANDLER";
    
	/** 
	 * メイン機能IDの受け渡しに使用するキーです
	 */
	public static final String MAINFUNCTIONID_KEY = "MAINFUNCTIONID_KEY";
	
	/** 
	 * メニュー名の受け渡しに使用するキーです
	 */
	public static final String MENUNAME_KEY = "MENUNAME_KEY";
	
	/** 
	 * 機能リソースキーの受け渡しに使用するキーです
	 */
	public static final String FUNCTIONRESOURCE_KEY = "FUNCTIONRESOURCE_KEY";
	
	/** 
	 * 機能名の受け渡しに使用するキーです
	 */
	public static final String FUNCTIONNAME_KEY = "FUNCTIONNAME_KEY";
	

	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	    
	    //画面名をセットする
	    lbl_ListName.setText(DispResources.getText("TLE-T0006"));
	    
		Connection conn = null;		
		try
		{
			//Pager初期化
			setPagerValue(0, 0, 0);
			//前回保持したインスタンスを初期化する
			ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
			if(handle != null)
			{
				handle.close();
			}

			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			handle = new ScrollHandler(conn);
			BaseHandler baseHandle = new BaseHandler();
			
			//メインメニュー表のアイコン表示方法・未使用を取得
			String notuse = Integer.toString(SHOWTYPE_NOTUSE);
			String[] showtype = new String[1];
			showtype[0] = notuse;
			//データ数合計
			int total = baseHandle.count("_select-5701", showtype, conn);
			
			//1ページの表示件数
			int page = Integer.parseInt(ToolParam.getProperty("ListboxSearchCount"));
			//最初のページの表示終了位置
			int end = 0;
			//データがある場合
			if(total > 0)
			{
				handle.open();
				if(total <= page)
				{
					end = total;
				}
				else
				{
					end = page;
				}
				//リストデータをセット
				setList(handle, 0, end);
				//pagerに値をセット
				setPagerValue(1, total, page);
				//セッションにHandlerの値をセット
				this.getSession().setAttribute(KEY_SCROLLHANDLER, handle);
			}
			else
			{
				//Pagerへの値セット
				pgr_U.setMax(0);	//最大件数
				pgr_U.setPage(0);	//1Page表示件数
				pgr_U.setIndex(0);	//開始位置				
				pgr_D.setMax(0);	//最大件数
				pgr_D.setPage(0);	//1Page表示件数
				pgr_D.setIndex(0);	//開始位置
				//ヘッダーを隠します
				lst_FunctionList.setVisible(false);
				//対象データはありませんでした
				lbl_InMsg.setResourceKey("MSG-9016"); 
				//セッションにHandlerの値をセット
				this.getSession().setAttribute(KEY_SCROLLHANDLER, handle);   
			}
		}
		finally
		{

		
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/** 
	 * リストセルに値をセットします
	 * @param handle ScrollHandler
	 * @param next_index int
	 * @param next_end int 
	 * @throws Exception 
	 */
	private void setList(ScrollHandler handle, int next_index, int next_end) throws Exception
	{
		//メインメニュー表のアイコン表示方法・未使用を取得
		String notuse = Integer.toString(SHOWTYPE_NOTUSE);
		String[] showtype = new String[1];
		showtype[0] = notuse;
	    //データベースからリストデータを取得
		List list = handle.find("_select-5700", showtype, next_index, next_end);
		//表を削除
		lst_FunctionList.clearRow();

		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			ResultMap map = (ResultMap)itr.next();
			int count = lst_FunctionList.getMaxRows();
			lst_FunctionList.setCurrentRow(count);
			lst_FunctionList.addRow();

			String menuresouce = "";
			if((String)map.get(MAINMENU_MENURESOURCEKEY) != null)
			{
				//メニュー名を取得
				menuresouce = DispResourceMap.getText((String)map.get(MAINMENU_MENURESOURCEKEY));
				if(menuresouce == null)
				{
				    //メニューリソースキーを取得
				    menuresouce = (String)map.get(MAINMENU_MENURESOURCEKEY);
				}			    
			}
			
			//表にデータをセット
			lst_FunctionList.setValue(1, menuresouce);
			lst_FunctionList.setValue(2, Integer.toString(count + next_index));
			lst_FunctionList.setValue(3, (String)map.get(FUNCTION_MAINFUNCTIONID));
			lst_FunctionList.setValue(4, (String)map.get(FUNCTION_FUNCTIONRESOURCEKEY));
			lst_FunctionList.setValue(5, DispResourceMap.getText((String)map.get(FUNCTION_FUNCTIONRESOURCEKEY)));			
		}
				
	}
	
	/** 
	 * ページャーに値をセットします
	 * @param index int
	 * @param total int
	 * @param page int
	 * @throws Exception 
	 */
	private void setPagerValue(int index, int total, int page)
	{
		pgr_U.setIndex(index);
		pgr_U.setMax(total);
		pgr_U.setPage(page);
		pgr_D.setIndex(index);
		pgr_D.setMax(total);
		pgr_D.setPage(page);
	}
	// Event handler methods -----------------------------------------
	/** 
	 * 閉じるボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		//終了処理
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		if(handle != null)
		{
			handle.close();
		}
		this.getSession().removeAttribute(KEY_SCROLLHANDLER);

		//呼び出し元の画面へ遷移します
		parentRedirect(null);
	}
	
	/** 
	 * Pagerの次へボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		//Handlerにセッションの値をセット
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		int total = pgr_U.getMax();
		int page = pgr_U.getPage();
		int index = pgr_U.getIndex();
		int next_index = 0;
		int next_end = 0;
		
		if(index + page*2 <= total)
		{
			next_index = index + page -1;
			next_end = index + page*2 -1;
		}
		else
		{
			next_index =index + page -1;
			next_end = total ;
		}
		//リストデータをセット
		setList(handle, next_index, next_end);
		
		//Pagerに値をセット
		setPagerValue(next_index+1, total, page);	
	}


	/** 
	 * Pagerの前へボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
	    // Handlerにセッションの値をセット
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		int total = pgr_U.getMax();
		int page = pgr_U.getPage();
		int index = pgr_U.getIndex();
		int next_index = 0;
		int next_end = 0;
		
		if(index - page <= 0)
		{
			next_index = 0;
			next_end = page;
		}
		else
		{
			next_index = index - page - 1;
			next_end = index - 1;
		}
	
		//リストデータをセット
		setList(handle, next_index, next_end);
		
		//Pagerに値をセット
		setPagerValue(next_index + 1, total, page);	
	}

	/** 
	 * Pagerの最後ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		//Handlerにセッションの値をセット
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		int total = pgr_U.getMax();
		int page = pgr_U.getPage();

		int next_index = 0;
		int next_end = 0;
		if(total%page == 0)
		{
			next_index = total-page;
			next_end = total;
		}
		else
		{
			next_index = total - (total%page);
			next_end = total;
		}
		
		//リストデータをセット
		setList(handle, next_index, next_end);
		
		//Pagerに値をセット
		setPagerValue(next_index+1, total, page);	
	}

	/** 
	 * Pagerの最初ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		//Handlerにセッションの値をセット
		ScrollHandler handle = (ScrollHandler)this.getSession().getAttribute(KEY_SCROLLHANDLER);
		int total = pgr_U.getMax();
		int page = pgr_U.getPage();

		//リストデータをセット
		setList(handle, 0, page);
		
		//Pagerに値をセット
		setPagerValue(1, total, page);		
	}


	/** 
	 * リストが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_FunctionList_Click(ActionEvent e) throws Exception
	{
		//現在の行をセット
		lst_FunctionList.setCurrentRow(lst_FunctionList.getActiveRow());
		
		//呼び出し元の画面へ渡すパラメータ作成
		ForwardParameters param = new ForwardParameters();
		param.setParameter(MENUNAME_KEY, lst_FunctionList.getValue(1));
		param.setParameter(MAINFUNCTIONID_KEY, lst_FunctionList.getValue(3));
		param.setParameter(FUNCTIONRESOURCE_KEY, lst_FunctionList.getValue(4));
		param.setParameter(FUNCTIONNAME_KEY, lst_FunctionList.getValue(5));
		
		//終了処理
		btn_Close_U_Click(null);
		
		//呼び出し元の画面へ遷移します
		parentRedirect(param);
	}

	/** 
	 * Pagerの次へボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		pgr_U_Next(e);
	}

	/** 
	 * Pagerの前へボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		pgr_U_Prev(e);
	}

	/** 
	 * Pagerの最後ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		pgr_U_Last(e);
	}

	/** 
	 * Pagerの最初ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		pgr_U_First(e);
	}


	/** 
	 * 閉じるボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		btn_Close_U_Click(e);
	}


}
//end of class