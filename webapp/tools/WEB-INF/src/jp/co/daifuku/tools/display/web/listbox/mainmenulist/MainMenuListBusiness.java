// $Id: MainMenuListBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.listbox.mainmenulist;
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
import jp.co.daifuku.tools.util.DispResourceHelper;
import jp.co.daifuku.tools.util.DispResourceMap;
import jp.co.daifuku.tools.util.ToolParam;

/** <jp>
 * メニュー一覧の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
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
public class MainMenuListBusiness extends MainMenuList implements ToolConstants, TableColumns
{
	// Class fields --------------------------------------------------
    /**
     * SCROLLHANDLERをセッションに保持するキー
     */ 
    private final String KEY_SCROLLHANDLER = "KEY_SCROLLHANDLER";
    
	/** 
	 * メニューIDの受け渡しに使用するキーです
	 */
	public static final String MENUID_KEY = "MENUID_KEY";
	/** 
	 * メニューリソースキーの受け渡しに使用するキーです
	 */
	public static final String MENURESOURCE_KEY = "MENURESOURCE_KEY";
	/** 
	 * メニュー名の受け渡しに使用するキーです
	 */
	public static final String MENUNAME_KEY = "MENUNAME_KEY";	
	/** 
	 * アイコン表示方法の受け渡しに使用するキーです
	 */
	public static final String SHOWTYPE_KEY = "SHOWTYPE_KEY";	
	
	/**
	 * 親画面が必要するリソースキーを判別するフラグです
	 */
    public static final String BTNFLAG_KEY = "BTNFLAG_KEY";


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
	    lbl_ListName.setText(DispResources.getText("TLE-T0002"));
	    
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
			
			//呼び出し元画面でセットされたパラメータの取得
		    String dispflag = request.getParameter(BTNFLAG_KEY);
		    if(dispflag == null)
		    {
		        dispflag = "";
		    }
		    
		    //ViewStateに保存
		    this.getViewState().setString("BTFLAG",dispflag);
		    
			int total = 0;
			//親画面のサブメニュー設定の場合
			if(dispflag.equals("btn_P_MenuId"))
			{
				//呼び出し元画面でセットされたパラメータの取得
				String[] menuid = new String[1]; 
				menuid[0] = this.request.getParameter( MENUID_KEY );
			    //ViewStateに保存
			    this.getViewState().setString("MenuId",menuid[0]);
						
				//メニューIDテキストボックスの値によって場合分け
				if(menuid[0].equals(""))
				{
				    //データ数合計
				    total = baseHandle.count("_select-5904", null, conn);
				}
				else if(menuid[0].indexOf("*") > -1)
				{
				    //「*」を「%」に置換
				    menuid[0]=menuid[0].replace('*','%');
				    //データ数合計
				    total = baseHandle.count("_select-5906", menuid, conn);			    
				}
				else
				{
				    //データ数合計
				    total = baseHandle.count("_select-5907", menuid, conn);
				}			    
			}
			//親画面がメインメニュー設定の場合
			else
			{
				//データ数合計
				total = baseHandle.count("_select-5904", null, conn);			    
			}
			
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
				lst_MainMenuList.setVisible(false);
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
		//ViewStateから取得
	    String dispflag = this.getViewState().getString("BTFLAG");
	    
		List list = null;
		//親画面のサブメニュー設定の場合
		if(dispflag.equals("btn_P_MenuId"))
		{
			//呼び出し元画面でセットされたパラメータの取得
			String[] menuid = new String[1]; 
			//ViewStateから取得
			menuid[0] = this.getViewState().getString("MenuId");		

			//メニューIDテキストボックスの値によって場合分け
			if(menuid[0].equals(""))
			{
			    //データベースからリストデータを取得
			    list = handle.find("_select-5903", null, next_index, next_end);
			}
			else if(menuid[0].indexOf("*") > -1)
			{
			    //「*」を「%」に置換
			    menuid[0] = menuid[0].replace('*','%');
			    //データベースからリストデータを取得
			    list = handle.find("_select-5908", menuid, next_index, next_end);
			}
			else
			{
			    //データベースからリストデータを取得
			    list = handle.find("_select-5909", menuid, next_index, next_end);
			}
		}
		//親画面がメインメニュー設定の場合
		else
		{
		    //データベースからリストデータを取得
			list = handle.find("_select-5903", null, next_index, next_end);   
		}	    
	    
		//表を削除
		lst_MainMenuList.clearRow();

		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			ResultMap map = (ResultMap)itr.next();
			int count = lst_MainMenuList.getMaxRows();
			lst_MainMenuList.setCurrentRow(count);
			lst_MainMenuList.addRow();

			//表にデータをセット
			lst_MainMenuList.setValue(1, Integer.toString(count + next_index));
			lst_MainMenuList.setValue(2, (String)map.get(MAINMENU_MENUID));
			lst_MainMenuList.setValue(3, (String)map.get(MAINMENU_MENURESOURCEKEY));
			lst_MainMenuList.setValue(4, DispResourceMap.getText((String)map.get(MAINMENU_MENURESOURCEKEY)));
			int showtype =  (int)map.getInt(MAINMENU_SHOWTYPE);
			lst_MainMenuList.setValue(5, DispResourceHelper.getText("MAINMENU", "SHOWTYPE", showtype));
			
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
	public void lst_MainMenuList_Click(ActionEvent e) throws Exception
	{
		//現在の行をセット
		lst_MainMenuList.setCurrentRow(lst_MainMenuList.getActiveRow());
		
		//呼び出し元の画面へ渡すパラメータ作成
		ForwardParameters param = new ForwardParameters();
		param.setParameter(MENUID_KEY, lst_MainMenuList.getValue(2));
		param.setParameter(MENURESOURCE_KEY, lst_MainMenuList.getValue(3));
		param.setParameter(MENUNAME_KEY, lst_MainMenuList.getValue(4));
		param.setParameter(SHOWTYPE_KEY, lst_MainMenuList.getValue(5));
		
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
