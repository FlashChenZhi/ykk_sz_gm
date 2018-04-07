//$Id: FunctionOrderBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.functionorder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.ListBoxItem;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.ToolListBoxHandler;
import jp.co.daifuku.tools.util.ToolPulldownHandler;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.ui.web.ListBoxHelper;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.ListBoxHandler;
import jp.co.daifuku.util.PulldownHandler;



/** <jp>
 * サブメニュー表示順変更の画面クラスです。
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
public class FunctionOrderBusiness extends FunctionOrder implements ToolConstants
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
        lbl_SettingName.setResourceKey("TLE-T0007");
		//ヘルプファイルへのパスをセットする
        btn_Help.setUrl(BusinessClassHelper.getHelpPath("FunctionOrder", this.getHttpRequest()) );
        
		btn_Cancel.setEnabled(true);
		btn_Commit.setEnabled(true);					
		img_ArrowDown.setVisible(true);
		img_ArrowUp.setVisible(true);
		img_ArrowLeft.setVisible(true);
		img_ArrowRight.setVisible(true);
		lbl_Add.setVisible(true);
		lbl_Delete.setVisible(true);
		ltb_FunctionOrder.setEnabled(true);
		ltb_FunctionOrder2.setEnabled(true);
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
			//プルダウンデータの設定
			ToolPulldownHandler pull = new ToolPulldownHandler();
			PulldownHandler.PulldownParam param = pull.new PulldownParam();

			//初期表示するデータのindexを指定
			param.setSelectedIndex(0);
				
			//メインメニューのアイコン表示方法をセットする
			String notuse = Integer.toString(ToolConstants.SHOWTYPE_NOTUSE);
			String[] showtype = new String[1];
			//未使用をセット
			showtype[0] = notuse; 
			param.setConditions(showtype);
			//プルダウンの検索に使用するSQLリソースを指定
			param.setSqlRes("_select-5000");	

			//全ての項目を表示しない場合falseをセット
			param.setIsAll(false);

			//プルダウンの表示に必要なデータをArrayListにセット
			ArrayList lisp =pull.getPulldown(param, conn);
			if(lisp.size() == 0)
			{
				//メインメニューを登録してください。
				message.setMsgResourceKey("6403022"); 
				
				//コントロール無効化
				btn_Cancel.setEnabled(false);
				btn_Commit.setEnabled(false);
				btn_View.setEnabled(false);
				img_ArrowDown.setVisible(false);
				img_ArrowUp.setVisible(false);
				img_ArrowLeft.setVisible(false);
				img_ArrowRight.setVisible(false);
				pul_MainMenu.setEnabled(false);
				lbl_Add.setVisible(false);
				lbl_Delete.setVisible(false);
				ltb_FunctionOrder.setEnabled(false);
				ltb_FunctionOrder2.setEnabled(false);
				
				return;
			}

			//プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_MainMenu, lisp );
		    		    
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
	 * 表示ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn =null;		
		try
		{		
		    //リスボックスをクリア
		    ltb_FunctionOrder.clearItem();
		    ltb_FunctionOrder2.clearItem();
		    
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//リストボックスデータの設定
			ToolListBoxHandler list = new ToolListBoxHandler();
			ListBoxHandler.ListBoxParam listparam = list.new ListBoxParam();
			
			//表示項目リストボックス
			//リストボックスの表示項目の表示条件
			String[] conditions = new String[2];
			//メインメニューに属するサブメニューの一覧を表示させるために、
			//メインメニューのメニューIDをセットする		
			conditions[0] = pul_MainMenu.getSelectedValue(); 
			if(conditions[0] == null)
			{
				//メインメニューを登録してください。
				message.setMsgResourceKey("6403022"); 
				return;
			}
			    
			//未使用項目の値をセット
			conditions[1] = Integer.toString(ToolConstants.NOTDISPITEM);
			listparam.setConditions(conditions);
			//リストボックスの検索に使用するSQLリソースを指定
			listparam.setSqlRes("_select-5005");		
			//リストボックスの表示に必要なデータをArrayListにセット
			ArrayList listlisp = list.getListBox(listparam, conn);
			boolean flg = true;
			if(listlisp.size() == 0)
			{
			    flg = false;
			}
			else
			{
				//リストボックスデータをリストボックスへセット
				ListBoxHelper.setListBox(ltb_FunctionOrder, listlisp);			    
			}
			
			//未使用項目リストボックス
			//リストボックスの表示項目の表示条件
			String[] notuse_conditions = new String[2];
			//メインメニューに属するサブメニューの一覧を表示させるために、
			//メインメニューのメニューIDをセットする		
			notuse_conditions[0] = pul_MainMenu.getSelectedValue(); 
			//未使用項目の値をセット
			notuse_conditions[1] = Integer.toString(ToolConstants.NOTDISPITEM);
			listparam.setConditions(notuse_conditions);
			//リストボックスの検索に使用するSQLリソースを指定
			listparam.setSqlRes("_select-5006");		
			//リストボックスの表示に必要なデータをArrayListにセット
			ArrayList notuse_listlisp = list.getListBox(listparam, conn);
			if(notuse_listlisp.size() == 0)
			{
			    if(!flg)
			    {
					//指定されたメインメニューのサブメニューは登録されていません。
					message.setMsgResourceKey("6403031"); 

					//コントロール無効化
					btn_Cancel.setEnabled(false);
					btn_Commit.setEnabled(false);					
					img_ArrowDown.setVisible(false);
					img_ArrowUp.setVisible(false);
					img_ArrowLeft.setVisible(false);
					img_ArrowRight.setVisible(false);
					lbl_Add.setVisible(false);
					lbl_Delete.setVisible(false);
					ltb_FunctionOrder.setEnabled(false);
					ltb_FunctionOrder2.setEnabled(false);

					return;			        
			    }
			}
			else
			{				
				//リストボックスデータをリストボックスへセット
				ListBoxHelper.setListBox(ltb_FunctionOrder2, notuse_listlisp); 
			}
			
			if(pul_MainMenu.getSelectedItem() != null)
			{
			    //選択されているメインメニューをViewStateに保存
			    this.getViewState().setString("Mainmenu",pul_MainMenu.getSelectedItem().getText());			    
			}
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
	 * ↑ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void img_ArrowUp_Click(ActionEvent e) throws Exception
	{
	    //選択した場所のIndexを取得
	    List selectindex = ltb_FunctionOrder.getSelectedIndex();
		if(selectindex.size() != 0)
		{
		    String stindex = (String)selectindex.get(0);
		    
		    //選択した場所が一番上以外ならば処理
		    if(!stindex.equals("0"))
		    {
		        //リスト一覧を取得
		        List function_menulist = ltb_FunctionOrder.getListBoxItems();
		        //選択したアイテムを取得
		        List selectitem = ltb_FunctionOrder.getSelectedItems();
		        
		        for(int i=0;i<function_menulist.size();i++)
		        {
		            //選択したアイテムをリスト内で検索
		            if(function_menulist.get(i).equals(selectitem.get(0)))
		            {
		                //選択したアイテムを手前のアイテムと入れ替える処理
		                function_menulist.set(i,function_menulist.get(i-1));
		                function_menulist.set(i-1,selectitem.get(0));
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
		List selectindex = ltb_FunctionOrder.getSelectedIndex();

		if(selectindex.size() != 0)
		{
			String stindex = (String)selectindex.get(0);	        

	        //リスト一覧を取得
	        List function_menulist = ltb_FunctionOrder.getListBoxItems();
		        
		    //選択した場所が一番下以外ならば処理
		    if(function_menulist.size() != Integer.parseInt(stindex)+1)
		    {
		        //選択したアイテムを取得
		        List selectitem = ltb_FunctionOrder.getSelectedItems();
			        
		        for(int i=0;i<function_menulist.size();i++)
		        {
		            //選択したアイテムをリスト内で検索
		            if(function_menulist.get(i).equals(selectitem.get(0)))
		            {
		                //選択したアイテムを後ろのアイテムと入れ替える処理
		                function_menulist.set(i,function_menulist.get(i+1));
		                function_menulist.set(i+1,selectitem.get(0));
		                break;
		            }
		        }
		    }	        	        		    
		}
	}


	/** 
	 * ←ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void img_ArrowLeft_Click(ActionEvent e) throws Exception
	{
	    //選択されている項目を取得
	    List selected = ltb_FunctionOrder2.getSelectedItems();
	    Iterator iterator = selected.iterator();
	    while(iterator.hasNext())
	    {
	        ListBoxItem item = (ListBoxItem)iterator.next();
	        //表示項目のリストボックスへ追加
	        ltb_FunctionOrder.addItem(item);
	        //未使用項目のリストボックスから削除
	        ltb_FunctionOrder2.removeItem(item);
	    }
	}

	/** 
	 * →ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void img_ArrowRight_Click(ActionEvent e) throws Exception
	{
	    //選択されている項目を取得
	    List selected = ltb_FunctionOrder.getSelectedItems();
	    Iterator iterator = selected.iterator();
	    while(iterator.hasNext())
	    {
	        ListBoxItem item = (ListBoxItem)iterator.next();
	        //未使用項目のリストボックスへ追加
	        ltb_FunctionOrder2.addItem(item);
	        //表示項目のリストボックスから削除
	        ltb_FunctionOrder.removeItem(item);
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
		    //メイン機能ID
			ArrayList mainfunctionid =  new ArrayList();
			//未使用項目のメイン機能ID
			ArrayList notuse_mainfunctionid =  new ArrayList();
			//機能表示順
			String functiondispno = "";
			
		    //表示項目リストデータを取得
		    List function_menulist = ltb_FunctionOrder.getListBoxItems();
		    for(int i=0; i<function_menulist.size(); i++)
		    {
		        String temp = ltb_FunctionOrder.getItem(i).getText();
		        //リストデータを":"で分割
		        StringTokenizer st = new StringTokenizer(temp,":");	
    	        //メイン機能IDの取得
		        mainfunctionid.add(st.nextToken());
		    }
		    		    
		    //未使用項目リストデータを取得
		    List notuse_function_menulist = ltb_FunctionOrder2.getListBoxItems();
		    for(int m=0; m<notuse_function_menulist.size(); m++)
		    {
		        String temp2 = ltb_FunctionOrder2.getItem(m).getText();
		        //リストデータを":"で分割
		        StringTokenizer st = new StringTokenizer(temp2,":");	
    	        //未使用項目のメイン機能IDの取得
		        notuse_mainfunctionid.add(st.nextToken());
		    }
		    
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			BaseHandler handle = new BaseHandler();
			
			//表示項目の機能表示順を登録
			for(int j=0; j<function_menulist.size(); j++)
			{
			    //機能表示順をセット
			    functiondispno = Integer.toString(j+1);
			    handle.modify("_update-5701", new String[]{functiondispno, (String)mainfunctionid.get(j)},conn);    
			}
			
			//未使用項目の機能表示順を登録
			for(int k=0; k<notuse_function_menulist.size(); k++)
			{
				//未使用項目のメニュー表示順に-1をセット
				handle.modify("_update-5701", new String[]{Integer.toString(ToolConstants.NOTDISPITEM), (String)notuse_mainfunctionid.get(k)},conn);        
			}
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
	 * 取消ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Click(ActionEvent e) throws Exception
	{
		//ViewStateより選択されているメインメニューを取得
	    String mainmenu = this.getViewState().getString("Mainmenu"); 
		    
	    if(mainmenu == null)
	    {
	        return;
	    }
	    //メインメニューが変更されているか判定
	    else if(mainmenu.equals(pul_MainMenu.getSelectedItem().getText()))
	    {
	        //表示処理
	        btn_View_Click(e);
	    }
	}


}
//end of class
