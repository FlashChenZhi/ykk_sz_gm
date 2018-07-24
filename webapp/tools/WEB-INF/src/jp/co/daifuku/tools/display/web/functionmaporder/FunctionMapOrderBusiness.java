// $Id: FunctionMapOrderBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.functionmaporder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
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
 * サブメニューボタン表示順変更の画面クラスです。
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
public class FunctionMapOrderBusiness extends FunctionMapOrder implements ToolConstants
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
	    lbl_SettingName.setResourceKey("TLE-T0010");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("FunctionMapOrder", this.getHttpRequest()) );
	    
		btn_Cancel.setEnabled(true);
		btn_Commit.setEnabled(true);
		img_ArrowDown.setVisible(true);
		img_ArrowUp.setVisible(true);
		ltb_FunctionMapOrder.setEnabled(true);		
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
				
				btn_Cancel.setEnabled(false);
				btn_Commit.setEnabled(false);
				btn_View.setEnabled(false);
				img_ArrowDown.setVisible(false);
				img_ArrowUp.setVisible(false);
				pul_MainMenu.setEnabled(false);
				pul_SubMenu.setEnabled(false);
				ltb_FunctionMapOrder.setEnabled(false);		
				
				return;
			}
			//プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_MainMenu, lisp );

			//連動プルダウンリスト情報が存在する場合、追加する
			pul_MainMenu.addChild(pul_SubMenu);	
			
			//Functionの機能表示順=未使用をセット
			String function_notuse = Integer.toString(ToolConstants.NOTDISPITEM);
			String[] functiondisp = new String[1];
			//未使用をセット
			functiondisp[0] = function_notuse; 
			param.setConditions(functiondisp);			
			//プルダウンの検索に使用するSQLリソースを指定
			param.setSqlRes("_select-5002");
			//プルダウンの表示に必要なデータをArrayListにセット
			lisp =pull.getPulldown(param, conn);
			//PulldownDataで取得したデータをLinkedPullDownコントロールへセット
			PulldownHelper.setLinkedPullDown(pul_SubMenu, lisp );
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
	    Connection conn = null;				
		try
		{
		    //リスボックスをクリア
		    ltb_FunctionMapOrder.clearItem();
		    
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//リストボックスデータの設定
			ToolListBoxHandler list = new ToolListBoxHandler();
			ListBoxHandler.ListBoxParam listparam = list.new ListBoxParam();
			//サブメニューに属するサブメニューボタンの一覧を表示させるために、
			//サブメニューのメイン機能IDをセットする
			String[] mainfunctionid = new String[1];
			mainfunctionid[0] = pul_SubMenu.getSelectedValue(); 
			if(mainfunctionid[0] == null)
			{
				//指定されたメインメニューのサブメニューは登録されていないか、未使用項目として設定されています。
				message.setMsgResourceKey("6403026"); 
				
				btn_Cancel.setEnabled(false);
				btn_Commit.setEnabled(false);
				img_ArrowDown.setVisible(false);
				img_ArrowUp.setVisible(false);
				ltb_FunctionMapOrder.setEnabled(false);		
				
				return;
			}
			
			listparam.setConditions(mainfunctionid);
			//リストボックスの検索に使用するSQLリソースを指定
			listparam.setSqlRes("_select-5004");		
			listparam.getSqlRes();
			//リストボックスの表示に必要なデータをArrayListにセット
			ArrayList listlisp = list.getListBox(listparam, conn);
			if(listlisp.size() == 0)
			{
				//指定されたサブメニューのボタンは登録されていません。
				message.setMsgResourceKey("6403025"); 
				return;
			}
			else
			{
				//リストボックスデータをリストボックスへセット
				ListBoxHelper.setListBox(ltb_FunctionMapOrder, listlisp);			    			    
			}
			
		    //選択されているサブメニューをViewStateに保存
			if(pul_SubMenu.getSelectedItem() != null)
			{
			    this.getViewState().setString("Submenu",pul_SubMenu.getSelectedItem().getText());    
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
	    List selectindex = ltb_FunctionMapOrder.getSelectedIndex();
		if(selectindex.size() != 0)
		{
		    String stindex = (String)selectindex.get(0);
		    
		    //選択した場所が一番上以外ならば処理
		    if(!stindex.equals("0"))
		    {
		        //リスト一覧を取得
		        List function_menulist = ltb_FunctionMapOrder.getListBoxItems();
		        //選択したアイテムを取得
		        List selectitem = ltb_FunctionMapOrder.getSelectedItems();
		        
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
	    List selectindex = ltb_FunctionMapOrder.getSelectedIndex();
		if(selectindex.size() != 0)
		{
		    String stindex = (String)selectindex.get(0);

	        //リスト一覧を取得
	        List function_menulist = ltb_FunctionMapOrder.getListBoxItems();
	        
		    //選択した場所が一番下以外ならば処理
		    if(function_menulist.size() != Integer.parseInt(stindex)+1)
		    {
		        //選択したアイテムを取得
		        List selectitem = ltb_FunctionMapOrder.getSelectedItems();
		        
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
	 * 設定ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Click(ActionEvent e) throws Exception
	{
		Connection conn =null;		
		try
		{		
		    //機能ID
			ArrayList functionid =  new ArrayList();
			//ボタン表示順
			String buttondispno = "";
		    
		    //リストデータを取得
		    List functionmaplist = ltb_FunctionMapOrder.getListBoxItems();
		    for(int i=0; i<functionmaplist.size(); i++)
		    {
		        String temp = ltb_FunctionMapOrder.getItem(i).getText();
		        //リストデータを":"で分割
		        StringTokenizer st = new StringTokenizer(temp,":");	
    	        //機能IDの取得
		        functionid.add(st.nextToken());
		    }
		    		    
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			BaseHandler handle = new BaseHandler();
			
			for(int j=0; j<functionmaplist.size(); j++)
			{
			    //ボタン表示順をセット
			    buttondispno = Integer.toString(j+1);
			    handle.modify("_update-5800", new String[]{buttondispno, (String)functionid.get(j)},conn);    
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
		//ViewStateより選択されているサブメニューを取得
	    String submenu = this.getViewState().getString("Submenu"); 
	    if(submenu == null)
	    {
	        return;
	    }
	    //サブメニューが変更されているか判定
	    else if(submenu.equals(pul_SubMenu.getSelectedItem().getText()))
	    {
	        //表示処理
	        btn_View_Click(e);
	    }			
	}


}
//end of class
