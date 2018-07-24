// $Id: MainMenuBusiness.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.mainmenu;
import java.sql.Connection;
import java.util.List;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.dbhandler.ResultMap;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.display.web.listbox.mainmenulist.MainMenuListBusiness;
import jp.co.daifuku.tools.display.web.listbox.resourcelist.ResourceListBusiness;
import jp.co.daifuku.tools.util.DispResourceMap;
import jp.co.daifuku.ui.web.BusinessClassHelper;

/** <jp>
 * メインメニュー設定の画面クラスです。
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
public class MainMenuBusiness extends MainMenu implements ToolConstants, TableColumns
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
	    lbl_SettingName.setResourceKey("TLE-T0001");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("MainMenu", this.getHttpRequest()) );
	    setFocus(txt_MenuResourceKey);
	}

    
	/**
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	    //プロダクトタイプをViewStateに保存
	    this.getViewState().setString("PRODUCTTYPE", this.request.getParameter(PRODUCTTYPE));
	    //メニュータイプをViewStateに保存
	    this.getViewState().setString("MENUTYPE", this.request.getParameter(MENUTYPE));
	    
	    //メニューリソースキーテキストボックスを読み取り専用にセット
		txt_MenuResourceKey.setReadOnly(true);
		//メニューリソースキー検索ボタンを読み取り専用にセット
		btn_P_MenuResouceKey.setEnabled(false);
		//アイコン表示方法ラジオボタンを読み取り専用にセット
		rdo_EnableClick.setEnabled(false);
		rdo_DisableClick.setEnabled(false);
		rdo_NotUse.setEnabled(false);

	}
	
	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
	 * @param e ActionEvent
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//メニューIDを取得
		String menukey = param.getParameter(MainMenuListBusiness.MENUID_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(menukey))
		{
			//クリア処理
			btn_Clear_Click(e);
			txt_R_MenuId.setText(menukey);
		}
		
		//リソースキーを取得
		String resourcekey = param.getParameter(ResourceListBusiness.RESOURCE_KEY);
		String resourcename = param.getParameter(ResourceListBusiness.RESOURCENAME_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(resourcekey))
		{
			txt_MenuResourceKey.setText(resourcekey);
			txt_R_MenuName.setText(resourcename);
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
	 * メニューID検索ボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_Search_Click(ActionEvent e) throws Exception
	{
		//メインメニュー一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		//処理中画面->結果画面
		redirect("/listbox/mainmenulist/MainMenuList.do", param, "/Progress.do");
	}


	/** 
	 * 登録ボタンが押下された時の処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btm_Submit_Click(ActionEvent e) throws Exception
	{
	    setFocus(txt_MenuResourceKey);
	    
		Connection conn = null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);
			txt_R_MenuId.setText("");			

			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);						
			BaseHandler handle = new BaseHandler();		
			
			//DBに数値データ以外の値が入っていた場合はメッセージをセットに飛ぶ
			//メニューIDの採番
			String menuIdMax = "";
			//メニューIDの一覧取得
			List menuIdRet = handle.find("_select-5905", null, conn);			
			//登録済みのメニューIDがある場合
			if(menuIdRet != null && menuIdRet.size() != 0)
			{			    			    
			    int temp = 0;
			    for(int i = 0; i< menuIdRet.size(); i++)
			    {
			        //リストデータから値を取得
			        ResultMap map = (ResultMap)menuIdRet.get(i);
			        //メニューIDの最大値検索
			        if(Integer.parseInt(map.getString(MAINMENU_MENUID)) > temp )
			        {
			            temp = Integer.parseInt(map.getString(MAINMENU_MENUID));
			            //メニューIDの最大値をセット
						menuIdMax = Integer.toString(temp + 1);
			        }					    
			    }		    

			}
			//メニューIDが未登録の場合
			else
			{
			    menuIdMax = "1";
			}		
			
			//メニューIDをテキストボックスにセット
			txt_R_MenuId2.setText(menuIdMax);

			//メニュー表示順テキストボックスに文字をセット
			txt_R_MenuDispNumber.setText("***");
		
			//メニューリソースキー検索ボタンを有効にする
			btn_P_MenuResouceKey.setEnabled(true);
			//メニューリソースキーテキストボックスを有効にする
			txt_MenuResourceKey.setReadOnly(false);
			//クリック可能ラジオボタンを有効にする
			rdo_EnableClick.setEnabled(true);			
			//クリック不可ラジオボタンを有効にする
			rdo_DisableClick.setEnabled(true);
			//未使用ラジオボタンを有効にする
			rdo_NotUse.setEnabled(true);
			//クリック可能ラジオボタンをチェックを入れる
			rdo_EnableClick.setChecked(true);
			
			//登録中を保持
			this.getViewState().setString(PROCESS_KEY, INSERT);
			
		    //設定しますか？
		    btn_Commit.setBeforeConfirm("MSG-9000");
		
		}
		catch (NumberFormatException ex) 
		{
		    //MAINMENU表のMENUID項目に数値以外のデータがセットされているため、
		    //メニューIDを採番できません。
			message.setMsgResourceKey("6407001"); 
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
	 * 修正ボタンが押下された時の処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Modify_Click(ActionEvent e) throws Exception
	{
	    setFocus(txt_MenuResourceKey);
	    
		Connection conn =null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);
			
			//メニューIDテキストボックスを入力チェック
			txt_R_MenuId.validate(true);

			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		

			//すでに同一のメニューIDが登録済みかを確認 
			if(!isDefined(txt_R_MenuId.getText(), conn))
			{
				//指定されたメニューIDは登録されていません。
				message.setMsgResourceKey("6403010"); 
				return;
			}
			
			//メニューリソースキー検索ボタンを有効にする
			btn_P_MenuResouceKey.setEnabled(true);
			//メニューリソースキーテキストボックスを有効にする
			txt_MenuResourceKey.setReadOnly(false);
			//クリック可能ラジオボタンを有効にする
			rdo_EnableClick.setEnabled(true);
			//クリック不可ラジオボタンを有効にする
			rdo_DisableClick.setEnabled(true);
			//未使用ラジオボタンを有効にする
			rdo_NotUse.setEnabled(true);
			
			//データベースのデータをセットする
			mapping(conn);
			//修正中を保持
			this.getViewState().setString(PROCESS_KEY, MODIFY);
			
		    //設定しますか？
		    btn_Commit.setBeforeConfirm("MSG-9000");
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
	 * 削除ボタンが押下された時の処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{

		Connection conn =null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);
			
			//メニューIDテキストボックスを入力チェック
			txt_R_MenuId.validate(true);
			
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			if(!isDefined(txt_R_MenuId.getText(), conn))
			{
				//指定されたメニューIDは登録されていません。
				message.setMsgResourceKey("6403010");
				return;
			}
			
			//メニューリソースキー検索ボタンを無効にする
			btn_P_MenuResouceKey.setEnabled(false);
			//メニューリソースキーテキストボックスを読み取り専用にする
			txt_MenuResourceKey.setReadOnly(true);
			//クリック可能ラジオボタンを有効にする
			rdo_EnableClick.setEnabled(false);
			//クリック不可ラジオボタンを有効にする
			rdo_DisableClick.setEnabled(false);
			//未使用ラジオボタンを有効にする
			rdo_NotUse.setEnabled(false);
			
			//データベースのデータをセットする
			mapping(conn);
			//削除中を保持
			this.getViewState().setString(PROCESS_KEY, DELETE);
			
		    //Function表・FunctionMap表の関連項目も削除されますが、よろしいですか？
		    btn_Commit.setBeforeConfirm("MSG-T0004");
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
	 * メニューリソースキー検索ボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_MenuResouceKey_Click(ActionEvent e)throws Exception
	{
		//リソース一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
	    param.setParameter(ResourceListBusiness.RESOURCE_KEY, txt_MenuResourceKey.getText());
		param.setParameter(ResourceListBusiness.BTNFLAG_KEY,btn_P_MenuResouceKey.getId());
		
		//処理中画面->結果画面
		redirect("/listbox/resourcelist/ResourceList.do", param, "/Progress.do");
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
		    //メニューID入力チェック(必須入力項目)
			txt_R_MenuId2.validate(true);
			
			String menuid = txt_R_MenuId2.getText();				
			String resourcekey = txt_MenuResourceKey.getText();
			int showtype_int = 0;
			if(rdo_EnableClick.getChecked()) showtype_int = SHOWTYPE_ENABLE;
			else if(rdo_DisableClick.getChecked()) showtype_int = SHOWTYPE_DISABLE;
			else if(rdo_NotUse.getChecked()) showtype_int = SHOWTYPE_NOTUSE;
			String showtype = Integer.toString(showtype_int);
			
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			BaseHandler handle = new BaseHandler();
						
			//処理区分
			String proc =this.getViewState().getString(PROCESS_KEY);
			
			//完了時のメッセージ
			String comp_msg = "";
			
			//処理区分が押下されていない場合
			if(proc == null)
			{
				return;
			}
			//登録時
			else if(proc.equals(INSERT))
			{			    
				//メニュー表示順の採番
				int menudispno_int = 0;
				//アイコン表示方法が未使用の場合
				if(showtype_int == SHOWTYPE_NOTUSE)
				{
				    menudispno_int = NOTDISPITEM;
				}
				//アイコン表示方法が未使用以外の場合
				else
				{
				    //メニュー表示順を自動採番
					List dispNoRet = handle.find("_select-5901", null, conn);
					//登録済みのメニューIDがある場合
					if(dispNoRet != null && dispNoRet.size() != 0)
					{
						ResultMap map = (ResultMap)dispNoRet.get(0);
						menudispno_int = map.getInt("MAX") + 1;
						if(menudispno_int == 0)
						{
						    menudispno_int = 1;
						}
					}
					//メニューIDが未登録の場合
					else
					{
					    menudispno_int = 1;
					}
				}
				String menudispno = Integer.toString(menudispno_int); 
			    
				//メニューリソースキー入力チェック(必須入力項目)
				txt_MenuResourceKey.validate();
				//ラジオボタン入力チェック(必須入力項目)
				rdo_EnableClick.validate();
				rdo_DisableClick.validate();
				rdo_NotUse.validate();
				//入力チェック
				txt_R_MenuName.validate(false);
				
				handle.create("_insert-5900", new String[]{menuid, menudispno, resourcekey, showtype},conn);

				//6401002=メニューID={0}を登録しました。
				comp_msg = "6401002" + "\t" + menuid;
			}
			//修正時
			else if(proc.equals(MODIFY))
			{
				//メニュー表示順の採番
				int menudispno_int = txt_R_MenuDispNumber.getInt();
				//アイコン表示方法が未使用の場合
				if(showtype_int == SHOWTYPE_NOTUSE)
				{
				    menudispno_int = NOTDISPITEM;
				}
				//アイコン表示方法が未使用以外の場合
				else
				{
				    if(menudispno_int == NOTDISPITEM)
				    {
					    //メニュー表示順を自動採番
						List dispNoRet = handle.find("_select-5901", null, conn);
						//登録済みのメニューIDがある場合
						if(dispNoRet != null && dispNoRet.size() != 0)
						{
							ResultMap map = (ResultMap)dispNoRet.get(0);
							menudispno_int = map.getInt("MAX") + 1;
							if(menudispno_int == 0)
							{
							    menudispno_int = 1;
							}
						}
						//メニューIDが未登録の場合
						else
						{
						    menudispno_int = 1;
						}				        
				    }
				}
				String menudispno = Integer.toString(menudispno_int); 

				//メニューリソースキー入力チェック(必須入力項目)
				txt_MenuResourceKey.validate();
				//ラジオボタン入力チェック(必須入力項目)
				rdo_EnableClick.validate();
				rdo_DisableClick.validate();
				rdo_NotUse.validate();
				//メニュー表示順入力チェック(必須入力項目)
				txt_R_MenuDispNumber.validate();
				//入力チェック
				txt_R_MenuName.validate(false);
				
				handle.modify("_update-5900", new String[]{menudispno, resourcekey, showtype, menuid},conn);
				//6001004=修正しました。
				comp_msg = "6001004";
			}
			//削除時
			else if(proc.equals(DELETE))
			{
			    String[] menuid_array = new String[1];
			    menuid_array[0] = menuid;
			    
			    //Function表のメイン機能IDの一覧を取得
				List mainfunctionid_ret = handle.find("_select-5707", menuid_array, conn);
				if(mainfunctionid_ret != null && mainfunctionid_ret.size() != 0)
				{
					for(int i=0; i<mainfunctionid_ret.size(); i++)
					{
					    //メイン機能IDを取得
					    ResultMap map = (ResultMap)mainfunctionid_ret.get(i);
					    String mainfunctionid = (String)map.getString(FUNCTION_MAINFUNCTIONID);				    
						//FunctionMap表削除
						handle.drop("_delete-5801", new String[]{mainfunctionid}, conn);								    
					}				    
				}
			    //Function表削除
				handle.drop("_delete-5701", new String[]{menuid}, conn);
				//MainMenu表削除
				handle.drop("_delete-5900", new String[]{menuid}, conn);
			    
				//6001005=削除しました。
				comp_msg = "6001005";
			}
			
			//設定
			conn.commit();
			//クリア処理
			btn_Clear_Click(e);
			//メッセージをセット
			message.setMsgResourceKey(comp_msg);
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
	 * クリアボタンの処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
	    //メニュー名テキストボックスをクリア
		txt_R_MenuName.setText("");
	    //メニュー表示順テキストボックスをクリア
		txt_R_MenuDispNumber.setText("");
	    //メニューIDテキストボックスをクリア
		txt_R_MenuId2.setText("");
	    //メニューリソースキーテキストボックスをクリア
		txt_MenuResourceKey.setText("");
	    //メニューリソースキー検索ボタンを無効にする
		btn_P_MenuResouceKey.setEnabled(false);
		//メニューリソースキーテキストボックスを読み取り専用にセット
		txt_MenuResourceKey.setReadOnly(true);
		//クリック可能ラジオボタンを無効にセット
		rdo_EnableClick.setEnabled(false);
		//クリック不可ラジオボタンを無効にセット
		rdo_DisableClick.setEnabled(false);
		//未使用ラジオボタンを無効にセット		
		rdo_NotUse.setEnabled(false);
		
		//ラジオボタンのチェックをはずす
		rdo_EnableClick.setChecked(false);
		rdo_DisableClick.setChecked(false);
		rdo_NotUse.setChecked(false);
		
	}

	/** 
	 * すでに同一のメニューIDが登録済みかを確認します。
	 * @param key  TerminalNumber
	 * @param conn Connection
	 * @throws Exception 
	 */
	private boolean isDefined(String key, Connection conn) throws Exception
	{
		BaseHandler handle = new BaseHandler();
		//すでに同一のメニューIDが存在する場合
		if(handle.count("_select-5900",new String[]{key}, conn) > 0)
		{
			return true;
		}
		return false;
	}
	
	/** 
	 * 修正、登録ボタン押下時に、DBの項目を各コントロールへマッピングします。
	 * @param conn Connection
	 * @throws Exception 
	 */
	private void mapping(Connection conn) throws Exception
	{
		BaseHandler handle = new BaseHandler();
		List ret = handle.find("_select-5902", new String[]{txt_R_MenuId.getText()}, conn);
			
		//登録済みのメニューIDがある場合
		if(ret != null && ret.size() != 0)
		{
			ResultMap map = (ResultMap)ret.get(0);
			String dispno = Integer.toString(map.getInt(MAINMENU_MENUDISPNUMBER));
			String resourcekey = map.getString(MAINMENU_MENURESOURCEKEY);
			int showtype = map.getInt(MAINMENU_SHOWTYPE);
				
			txt_R_MenuId2.setText(txt_R_MenuId.getText());				
			txt_R_MenuDispNumber.setText(dispno);
			txt_MenuResourceKey.setText(resourcekey);
			txt_R_MenuName.setText(DispResourceMap.getText(resourcekey));
				
			if(showtype == SHOWTYPE_ENABLE) rdo_EnableClick.setChecked(true);
			else if(showtype == SHOWTYPE_DISABLE) rdo_DisableClick.setChecked(true);
			else if(showtype == SHOWTYPE_NOTUSE) rdo_NotUse.setChecked(true);

		}			
	
	}

}
//end of class
