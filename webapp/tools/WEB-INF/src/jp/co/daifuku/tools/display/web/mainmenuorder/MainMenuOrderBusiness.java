// $Id: MainMenuOrderBusiness.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.mainmenuorder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.ToolListBoxHandler;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.ui.web.ListBoxHelper;
import jp.co.daifuku.util.ListBoxHandler;


/** <jp>
 * メインメニュー表示順変更の画面クラスです。
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
public class MainMenuOrderBusiness extends MainMenuOrder implements ToolConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
    
	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

    /** 
	 * 各コントロールイベント呼び出し前に呼び出されます。
	 * @param e ActionEvent
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//画面名称をセットする
	    lbl_SettingName.setResourceKey("TLE-T0004");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("MainMenuOrder", this.getHttpRequest()) );
	}
   
    /**
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	    //プロダクトタイプをViewStateに保存
	    this.getViewState().setString("PRODUCTTYPE",this.request.getParameter(PRODUCTTYPE));
	    //メニュータイプをViewStateに保存
	    this.getViewState().setString("MENUTYPE",this.request.getParameter(MENUTYPE));
	    
	    Connection conn =null;		
		try
		{		
		    //コネクション取得
		     conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		     //リストボックスデータの設定
		     ToolListBoxHandler list = new ToolListBoxHandler();
		     ListBoxHandler.ListBoxParam listparam = list.new ListBoxParam();
			
		     //アイコン表示方法　未使用をセット
			 String notuse = Integer.toString(ToolConstants.SHOWTYPE_NOTUSE);
			 String[] showtype = new String[1];
			 showtype[0] = notuse; 
			 listparam.setConditions(showtype);
		     //リストボックスの検索に使用するSQLリソースを指定
		     listparam.setSqlRes("_select-5003");		
		     //リストボックスの表示に必要なデータをArrayListにセット
		     ArrayList listlisp = list.getListBox(listparam, conn);
			if(listlisp.size() == 0)
			{
				//メインメニューを登録してください。
				message.setMsgResourceKey("6403022"); 
				btn_Cancel.setEnabled(false);
				btn_Commit.setEnabled(false);
				img_ArrowDown.setVisible(false);
				img_ArrowUp.setVisible(false);
				ltb_MainMenuOrder.setEnabled(false);
				return;
			}
		     //リストボックスデータをリストボックスへセット
		     ListBoxHelper.setListBox(ltb_MainMenuOrder, listlisp);
		}
		finally
		{
			if(conn != null)
			{
				conn.close();
			}
		}
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Event handler methods -----------------------------------------
	/** 
	 * メニューへボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
	    //メニュー画面へ遷移
	    forward("/menu/SubMenu.do?Product="+ this.getViewState().getString("PRODUCTTYPE")
	            						   + "&MenuType=" + this.getViewState().getString("MENUTYPE"));    
	}

	/** 
	 * ↑ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void img_ArrowUp_Click(ActionEvent e) throws Exception
	{
	    //選択した場所のIndexを取得
	    List selectindex = ltb_MainMenuOrder.getSelectedIndex();
		if(selectindex.size() != 0)
		{
		    String stindex = (String)selectindex.get(0);
		    
		    //選択した場所が一番上以外ならば処理
		    if(!stindex.equals("0"))
		    {
		        //リスト一覧を取得
		        List menulist = ltb_MainMenuOrder.getListBoxItems();
		        //選択したアイテムを取得
		        List selectitem = ltb_MainMenuOrder.getSelectedItems();
		        
		        for(int i=0;i<menulist.size();i++)
		        {
		            //選択したアイテムをリスト内で検索
		            if(menulist.get(i).equals(selectitem.get(0)))
		            {
		                //選択したアイテムを手前のアイテムと入れ替える処理
		                menulist.set(i,menulist.get(i-1));
		                menulist.set(i-1,selectitem.get(0));
		                break;
		            }
		        }
		    }	    
		}
	}


	/** 
	 * ↓ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void img_ArrowDown_Click(ActionEvent e) throws Exception
	{
	    //選択した場所のIndexを取得
	    List selectindex = ltb_MainMenuOrder.getSelectedIndex();
		if(selectindex.size() != 0)
		{
		    String stindex = (String)selectindex.get(0);

	        //リスト一覧を取得
	        List menulist = ltb_MainMenuOrder.getListBoxItems();
	        
		    //選択した場所が一番下以外ならば処理
		    if(menulist.size() != Integer.parseInt(stindex)+1)
		    {
		        //選択したアイテムを取得
		        List selectitem = ltb_MainMenuOrder.getSelectedItems();
		        
		        for(int i=0;i<menulist.size();i++)
		        {
		            //選択したアイテムをリスト内で検索
		            if(menulist.get(i).equals(selectitem.get(0)))
		            {
		                //選択したアイテムを後ろのアイテムと入れ替える処理
		                menulist.set(i,menulist.get(i+1));
		                menulist.set(i+1,selectitem.get(0));
		                break;
		            }
		        }
		    }
		}
	}


	/** 
	 * 設定ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Click(ActionEvent e) throws Exception
	{
		Connection conn =null;		
		try
		{		
		    //メニューID
			ArrayList menuid =  new ArrayList();
			//メニュー表示順
			String menudispno = "";
		    
		    //リストデータを取得
		    List menulist = ltb_MainMenuOrder.getListBoxItems();
		    for(int i=0; i<menulist.size(); i++)
		    {
		        String temp = ltb_MainMenuOrder.getItem(i).getText();
		        //リストデータを":"で分割
		        StringTokenizer st = new StringTokenizer(temp,":");	
    	        //メニューIDの取得
		        menuid.add(st.nextToken());
		    }
		    		    
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			BaseHandler handle = new BaseHandler();
			
			for(int j=0; j<menulist.size(); j++)
			{
			    //メニュー表示順をセット
			    menudispno = Integer.toString(j+1);
			    handle.modify("_update-5901", new String[]{menudispno, (String)menuid.get(j)},conn);    
			}
			
			String nodispitem = Integer.toString(ToolConstants.NOTDISPITEM);
			String notuse = Integer.toString(ToolConstants.SHOWTYPE_NOTUSE);
			
			//未使用項目のメニュー表示順に-1をセット
			handle.modify("_update-5902", new String[]{nodispitem, notuse},conn);    

			//設定
			conn.commit();
			//6001018	更新しました。
			message.setMsgResourceKey("6001018");
		}
		catch(Exception ex)
		{
			if(conn != null)
			{
				conn.rollback();
			}
			throw ex;
		}
		finally
		{
			if(conn != null)
			{
				conn.close();
			}
		}
	}


	/** 
	 * 取消ボタンの処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Click(ActionEvent e) throws Exception
	{
	    //リストを削除
	    ltb_MainMenuOrder.clearItem();
	    
	    Connection conn = null;				
		try
		{		
		    //コネクション取得
		    conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		    //リストボックスデータの設定
		    ToolListBoxHandler list = new ToolListBoxHandler();
		    ListBoxHandler.ListBoxParam listparam = list.new ListBoxParam();
			
		    //アイコン表示方法　未使用をセット
			String notuse = Integer.toString(ToolConstants.SHOWTYPE_NOTUSE);
			String[] showtype = new String[1];
			showtype[0] = notuse; 
		    listparam.setConditions(showtype);
		    //リストボックスの検索に使用するSQLリソースを指定
		    listparam.setSqlRes("_select-5003");		
		    //リストボックスの表示に必要なデータをArrayListにセット
		    ArrayList listlisp = list.getListBox(listparam, conn);
		    //リストボックスデータをリストボックスへセット
		    ListBoxHelper.setListBox(ltb_MainMenuOrder, listlisp);
		}
		finally
		{
			if(conn != null)
			{
				conn.close();
			}
		}
	}


}
//end of class
