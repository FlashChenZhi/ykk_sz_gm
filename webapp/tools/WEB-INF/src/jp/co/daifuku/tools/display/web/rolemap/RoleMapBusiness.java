// $Id: RoleMapBusiness.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.rolemap;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.CheckBox;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.dbhandler.ResultMap;
import jp.co.daifuku.dbhandler.ScrollHandler;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.DispResourceMap;
import jp.co.daifuku.tools.util.ToolPulldownHandler;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.PulldownHandler;

/** <jp>
 * ロールマップ登録の画面クラスです。
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
public class RoleMapBusiness extends RoleMap implements ToolConstants, TableColumns
{
	// Class fields --------------------------------------------------
    /**
     * SCROLLHANDLERをセッションに保持するキー
     */ 
    private final String KEY_SCROLLHANDLER = "KEY_SCROLLHANDLER";
    
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
	    lbl_SettingName.setResourceKey("TLE-T0013");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("RoleMap", this.getHttpRequest()) );
	    
	    btn_Commit.setBeforeConfirm("");
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
	    
	    Connection conn = null;			
		try
		{
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
	    
			//プルダウンデータの設定
			ToolPulldownHandler pull = new ToolPulldownHandler();
			PulldownHandler.PulldownParam param = pull.new PulldownParam();
			//初期表示するデータのindexを指定
			param.setSelectedIndex(0);
			//プルダウンの検索に使用するSQLリソースを指定
			param.setSqlRes("_select-5001");	
			//全ての項目を表示しない場合falseをセット
			param.setIsAll(false);
			//プルダウンの表示に必要なデータをArrayListにセット
			ArrayList lisp =pull.getPulldown(param, conn);
			//プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_Role, lisp );
						
			//表示条件パラメータのセット
		    String notuse = Integer.toString(SHOWTYPE_NOTUSE);
		    String notdispitem = Integer.toString(NOTDISPITEM);
			String[] disp = new String[2]; 
			//アイコン表示方法が未使用
			disp[0] = notuse;
			//機能表示順が未使用
			disp[1] = notdispitem;

			//データベースのデータ数合計
			int total = baseHandle.count("_select-5601", disp, conn);
			
			//最初のページの表示終了位置
			int end = 0;
			//データがある場合
			if(total > 0)
			{
				handle.open();
				end = total;

				//リストを表示
				lst_RoleMap.setVisible(true);
				//リストデータをセット
				setList(handle, 0, end);
				//セッションにHandlerの値をセット
				this.getSession().setAttribute(KEY_SCROLLHANDLER, handle);
			}
			//データがない場合
			else
			{
			    //チェックボックスを隠す
			    chk_All.setVisible(false);
				//リストを隠す
				lst_RoleMap.setVisible(false);
				//メインメニュー・サブメニュー・サブメニューボタンを登録してください。
				message.setMsgResourceKey("6403030"); 
				//セッションにHandlerの値をセット
				this.getSession().setAttribute(KEY_SCROLLHANDLER, handle);		
				
				btn_Cancel.setEnabled(false);
				btn_Commit.setEnabled(false);
				btn_View.setEnabled(false);
				pul_Role.setEnabled(false);
				return;
		    }
			
			//ロールが登録されていない場合
			if(lisp.size() == 0)
			{
				//ロールを登録してください。
				message.setMsgResourceKey("6403027");
				
				btn_Cancel.setEnabled(false);
				btn_Commit.setEnabled(false);
				btn_View.setEnabled(false);
				pul_Role.setEnabled(false);
				
				return;
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
		//表示条件パラメータのセット
	    String notuse = Integer.toString(SHOWTYPE_NOTUSE);
	    String notdispitem = Integer.toString(NOTDISPITEM);
		String[] disp = new String[2]; 
		//アイコン表示方法が未使用
		disp[0] = notuse;
		//機能表示順が未使用
		disp[1] = notdispitem;
	    
	    //データベースからリストデータを取得
		List list = handle.find("_select-5600", disp, next_index, next_end);
		//表を削除
		lst_RoleMap.clearRow();

		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			ResultMap map = (ResultMap)itr.next();
			int count = lst_RoleMap.getMaxRows();
			lst_RoleMap.setCurrentRow(count);
			lst_RoleMap.addRow();
			
		    //メニュー名を取得
		    String menuresource = DispResourceMap.getText((String)map.get(MAINMENU_MENURESOURCEKEY));
		    //リソースファイルにメニュー名が未登録の場合
		    if(menuresource == null)
		    {
		        //リソースキーをセット
		        menuresource = (String)map.get(MAINMENU_MENURESOURCEKEY);
		    }
			
		    //機能名を取得
		    String functionresource = DispResourceMap.getText((String)map.get(FUNCTION_FUNCTIONRESOURCEKEY));
		    //リソースファイルに機能名が未登録の場合
		    if(functionresource == null)
		    {
		        //リソースキーをセット
		        functionresource = (String)map.get(FUNCTION_FUNCTIONRESOURCEKEY);
		    }
		    
			 //ボタン名を取得
		    String buttonresource = DispResourceMap.getText((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY));
		    //リソースファイルにボタン名が未登録の場合
		    if(buttonresource== null)
		    {
		        //リソースキーをセット
		        buttonresource = (String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY);
		    }
			
			//表にデータをセット
		    lst_RoleMap.setValue(0, (String)map.get(FUNCTIONMAP_FUNCTIONID));
			lst_RoleMap.setValue(1, menuresource);
			lst_RoleMap.setValue(2, functionresource);
			lst_RoleMap.setValue(4, buttonresource);		
		}
				
	}
	
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
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);						
			BaseHandler handle = new BaseHandler();		
			
			//ロールIDを取得
			String[] roleid = new String[1];
			roleid[0] = pul_Role.getSelectedValue();
			if(roleid[0] == null)
			{
				//ロールを登録してください。
				message.setMsgResourceKey("6403027"); 
				return;
			}
			if(lst_RoleMap.getMaxRows() == 1)
			{
				//メインメニュー・サブメニュー・サブメニューボタンを登録してください。
				message.setMsgResourceKey("6403030"); 
				return;
			}
		    
		    chk_All.setEnabled(true);
		    //リストセル内のチェックボックスコントロールを取得
		    CheckBox checkbox = (CheckBox)lst_RoleMap.getListCellColumn(3).getControl();
		    //チェックボックスを有効にする
		    checkbox.setEnabled(true);
		    
		    //リストセル内のチェックをはずす
		    for(int k=1; k<lst_RoleMap.getMaxRows(); k++)
		    {
		        lst_RoleMap.setCurrentRow(k);
		        lst_RoleMap.setChecked(3,false);
		    }
		    			
			//RoleMap表の機能IDの一覧取得
			List functionid_ret = handle.find("_select-5602",roleid, conn);
			
			//登録済みの機能IDがある場合
			if(functionid_ret != null && functionid_ret.size() != 0)
			{			    
			    //RoleMap表を検索
			    for(int i = 0; i< functionid_ret.size(); i++)
			    {
			        //リストデータから値を取得
			        ResultMap map = (ResultMap)functionid_ret.get(i);
			        String rolemap_functionid = map.getString(ROLEMAP_FUNCTIONID);
			        
			        //リストセルを検索
			        for(int j=1; j<lst_RoleMap.getMaxRows(); j++)
			        {
			            //リストセル上の機能IDを取得
			            lst_RoleMap.setCurrentRow(j);
			            String buf = lst_RoleMap.getValue(0);
			    		String functionid_lst = CollectionUtils.getString(0, buf);

			    		//リストセル内の機能IDとRoleMap表の機能IDが一致しているか判定
			    		if(rolemap_functionid.equals(functionid_lst))
			            {
			    		    //リストセル内のチェックボックスにチェックを入れる
		    	            lst_RoleMap.setChecked(3,true);
			    	        break;
			            }
			        }
			    }			    
			}
			
		    int cnt_check = 1;
		    //リストセル内のチェック状態を取得
		    for(int m=1; m<lst_RoleMap.getMaxRows(); m++)
		    {
		        lst_RoleMap.setCurrentRow(m);
		        if(lst_RoleMap.getChecked(3) == true)
		        {
		           cnt_check = cnt_check + 1;
		        }
		    }
		    //リストセル内のチェックがすべてついているか判定
		    if(cnt_check == lst_RoleMap.getMaxRows())
		    {
		        //全て選択チェックをボックスにチェックを入れる
		        chk_All.setChecked(true);
		    }
		    else
		    {
		        //全て選択チェックをボックスにチェックをはずす
		        chk_All.setChecked(false);			        
		    }

		    //選択されているロールをViewStateに保存
		    this.getViewState().setString("Role",pul_Role.getSelectedItem().getText());

		    if(chk_All.getEnabled())
		    {
		        btn_Commit.setBeforeConfirm("MSG-9000");    
		    }

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
	 * 設定ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Click(ActionEvent e) throws Exception
	{
	    if(!chk_All.getEnabled())
	    {
			//ロールを選択してください。
			message.setMsgResourceKey("6403028"); 
			return;
	    }
	    
		Connection conn =null;		
		try
		{	
		    //ロールIDを取得
		    String[] roleid = new String[1];
		    roleid[0] = pul_Role.getSelectedValue(); 
		    		    
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			BaseHandler handle = new BaseHandler();
			
			//選択しているロールIDを削除
			handle.drop("_delete-5600", new String[]{roleid[0]},conn);    
		
		    //リストセル内のチェック状態を取得
		    for(int i=1; i<lst_RoleMap.getMaxRows(); i++)
		    {
		        lst_RoleMap.setCurrentRow(i);
		        if(lst_RoleMap.getChecked(3) == true)
		        {
		            //リストセル上の機能IDを取得
		            lst_RoleMap.setCurrentRow(i);
		            String buf = lst_RoleMap.getValue(0);
		    		String functionid_lst = CollectionUtils.getString(0, buf);
		    		
		    		//RoleMap表に登録
				    handle.create("_insert-5600", new String[]{roleid[0], functionid_lst},conn);	    		
		        }
		    }
			
			//設定
			conn.commit();
			//6001018	更新しました。
			message.setMsgResourceKey("6001018");

		    if(chk_All.getEnabled())
		    {
		        btn_Commit.setBeforeConfirm("MSG-9000");    
		    }

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
		//ViewStateより選択されているロールを取得
	    String role = this.getViewState().getString("Role"); 
	    if(role == null)
	    {
	        return;
	    }
	    //ロールが変更されているか判定
	    else if(role.equals(pul_Role.getSelectedItem().getText()))
	    {
	        //表示処理
	        btn_View_Click(e);
	    }			  
	}


	/** 
	 * 全てチェックボックスが変更されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_All_Change(ActionEvent e) throws Exception
	{
	    for(int i=1; i<lst_RoleMap.getMaxRows(); i++)
	    {
	        lst_RoleMap.setCurrentRow(i);
	        if(chk_All.getChecked())
	        {
	            lst_RoleMap.setChecked(3,true);
	        }
	        else
	        {
	            lst_RoleMap.setChecked(3,false);
	        }
	    }
	    
	    if(chk_All.getEnabled())
	    {
	        btn_Commit.setBeforeConfirm("MSG-9000");    
	    }

	}

}
//end of class
