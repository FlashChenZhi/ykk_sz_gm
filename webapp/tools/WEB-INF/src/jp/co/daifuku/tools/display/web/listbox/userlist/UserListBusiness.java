// $Id: UserListBusiness.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.listbox.userlist;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.authentication.AbstractAuthentication;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.dbhandler.ResultMap;
import jp.co.daifuku.dbhandler.ScrollHandler;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.ToolParam;

/** <jp>
 * ユーザ一覧の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </en> */
public class UserListBusiness extends UserList implements ToolConstants, TableColumns
{
	// Class fields --------------------------------------------------
    /**
     * SCROLLHANDLERをセッションに保持するキー
     */ 
    private final String KEY_SCROLLHANDLER = "KEY_SCROLLHANDLER";
    
	/** 
	 * ユーザIDの受け渡しに使用するキーです
	 */
	public static final String USERID_KEY = "USERID_KEY";
	
	/** 
	 * ロールIDの受け渡しに使用するキーです
	 */
	public static final String USER_ROLEID_KEY = "USER_ROLEID_KEY";
	
	/** 
	 * 親画面のメニュータイプの受け渡しに使用するキーです
	 */
	public static final String MENUTYPE_KEY = "MENUTYPE_KEY";	
	
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/** <jp>
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 </jp> */
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
	    //画面名をセットする
	    lbl_ListName.setText(DispResources.getText("TLE-T0015"));
	    
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
			String[] userid = new String[1]; 
			userid[0] = this.request.getParameter( USERID_KEY );
		    //ViewStateに保存
		    this.getViewState().setString("UserId",userid[0]);
		    
		    // 呼び出し元画面メニュータイプの取得
			String menutype = this.request.getParameter( MENUTYPE_KEY );
		    //ViewStateに保存
		    this.getViewState().setString("MenuType",menutype);
		    
		    
			int total = 0;
			//親画面のメニュータイプが客先の場合
			if(menutype.equals(MENU_CUSTOMER))
			{
				//LoginUser・Role表の検索条件
				String[] user = new String[2];
			    //システム定義のユーザ
				user[0] = AbstractAuthentication.ANONYMOUS_USER;
				
				//ユーザIDテキストボックスの値によって場合分け
				if(userid[0].equals(""))
				{
					//データ数合計
					total = baseHandle.count("_select-5108", user, conn);			    
				}
				else if(userid[0].indexOf("*") > -1)
				{
				    //「*」を「%」に置換
				    user[1]=userid[0].replace('*','%');
				    //データ数合計
				    total = baseHandle.count("_select-5109", user, conn);			    
				}
				else
				{	
				    //ユーザIDをセット
				    user[1]=userid[0];
					//データ数合計
					total = baseHandle.count("_select-5110", user, conn);
				}			 			    
			}
			//親画面のメニュータイプが設定者の場合
			else
			{
				//ユーザIDテキストボックスの値によって場合分け
				if(userid[0].equals(""))
				{
				    //データ数合計
				    total = baseHandle.count("_select-5101", null, conn);
				}
				else if(userid[0].indexOf("*") > -1)
				{
				    //「*」を「%」に置換
				    userid[0]=userid[0].replace('*','%');
				    //データ数合計
				    total = baseHandle.count("_select-5106", userid, conn);			    
				}
				else
				{
				    //データ数合計
				    total = baseHandle.count("_select-5107", userid, conn);
				}			    
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
				//Pagerに値をセット
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
				lst_UserList.setVisible(false);
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

		//呼び出し元画面でセットされたパラメータの取得
		String[] userid = new String[1]; 
		//ViewStateからユーザIDを取得
		userid[0] = this.getViewState().getString("UserId");
		//ViewStateからメニュータイプを取得
	    String menutype = this.getViewState().getString("MenuType");
		
		List list = null;
		//親画面のメニュータイプが客先の場合
		if(menutype.equals(MENU_CUSTOMER))
		{
			//LoginUser・Role表の検索条件
			String[] user = new String[2];
		    //システム定義のユーザ
			user[0] = AbstractAuthentication.ANONYMOUS_USER;
			
			//ユーザIDテキストボックスの値によって場合分け
			if(userid[0].equals(""))
			{
			    //データベースからリストデータを取得
				list = handle.find("_select-5111",user, next_index, next_end);
			}
			else if(userid[0].indexOf("*") > -1)
			{
			    //「*」を「%」に置換
			    user[1] = userid[0].replace('*','%');
			    //データベースからリストデータを取得
			    list = handle.find("_select-5112", user, next_index, next_end);
			}
			else
			{
			    //ユーザIDをセット
			    user[1] = userid[0];
			    //データベースからリストデータを取得
				list = handle.find("_select-5113",user, next_index, next_end);
			}		    		    
		}
		//親画面のメニュータイプが設定者の場合
		else
		{
			//ユーザIDテキストボックスの値によって場合分け
			if(userid[0].equals(""))
			{
			    //データベースからリストデータを取得
			    list = handle.find("_select-5100", null, next_index, next_end);
			}
			else if(userid[0].indexOf("*") > -1)
			{
			    //「*」を「%」に置換
			    userid[0] = userid[0].replace('*','%');
			    //データベースからリストデータを取得
			    list = handle.find("_select-5104", userid, next_index, next_end);
			}
			else
			{
			    //データベースからリストデータを取得
			    list = handle.find("_select-5105", userid, next_index, next_end);
			}		    
		}
		
		//表を削除
		lst_UserList.clearRow();

		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			ResultMap map = (ResultMap)itr.next();
			//最終行を取得
			int count = lst_UserList.getMaxRows();
			//行追加
			lst_UserList.setCurrentRow(count);
			lst_UserList.addRow();

			//表にデータをセット
			lst_UserList.setValue(1, Integer.toString(count + next_index));
			lst_UserList.setValue(2, (String)map.get(LOGINUSER_USERID));
			lst_UserList.setValue(3, (String)map.get(LOGINUSER_ROLEID));
			lst_UserList.setValue(4, (String)map.get(ROLE_ROLENAME));
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
	    // Handlerにセッションの値をセット
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
	public void lst_UserList_Click(ActionEvent e) throws Exception
	{
		//現在の行をセット
		lst_UserList.setCurrentRow(lst_UserList.getActiveRow());
		
		//呼び出し元の画面へ渡すパラメータ作成
		ForwardParameters param = new ForwardParameters();
		param.setParameter(USERID_KEY, lst_UserList.getValue(2));
		param.setParameter(USER_ROLEID_KEY, lst_UserList.getValue(3));	
		
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
