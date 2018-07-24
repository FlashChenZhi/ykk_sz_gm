// $Id: FunctionBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.function;
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
import jp.co.daifuku.tools.display.web.listbox.functionlist.FunctionListBusiness;
import jp.co.daifuku.tools.display.web.listbox.mainmenulist.MainMenuListBusiness;
import jp.co.daifuku.tools.display.web.listbox.resourcelist.ResourceListBusiness;
import jp.co.daifuku.tools.util.DispResourceMap;
import jp.co.daifuku.ui.web.BusinessClassHelper;

/** <jp>
 * サブメニュー設定の画面クラスです。
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
public class FunctionBusiness extends Function implements ToolConstants, TableColumns
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
        lbl_SettingName.setResourceKey("TLE-T0005");
		//ヘルプファイルへのパスをセットする
        btn_Help.setUrl(BusinessClassHelper.getHelpPath("Function", this.getHttpRequest()) );
        
        setFocus(txt_MenuId);
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
	    
	    //機能リソースキーテキストボックスを読み取り専用にセット
		txt_FunctionResourceKey.setReadOnly(true);
		//メニューIDテキストボックスを読み取り専用にセット
		txt_MenuId.setReadOnly(true);
		//機能リソースキー検索ボタンを読み取り専用にセット
		btn_P_FunctionResouceKey.setEnabled(false);
		//メニューID検索ボタンを読み取り専用にセット
		btn_P_MenuId.setEnabled(false);
	}
	
	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
	 * @param e ActionEvent
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		//メイン機能IDを取得
		String functionkey = param.getParameter(FunctionListBusiness.MAINFUNCTIONID_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(functionkey))
		{
			//クリア処理
			btn_Clear_Click(e);
			txt_R_MainFunctionId.setText(functionkey);
		}		
		
		//メニューIDを取得
		String menukey = param.getParameter(MainMenuListBusiness.MENUID_KEY);
		String menuname = param.getParameter(MainMenuListBusiness.MENUNAME_KEY);
		String menuresourcekey = param.getParameter(MainMenuListBusiness.MENURESOURCE_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(menukey))
		{
			txt_MenuId.setText(menukey);
			//メニュー名がリソースファイルに未登録の場合
			if(menuname.equals(""))
			{
			    txt_R_MenuName.setText(menuresourcekey);
			}
			//メニュー名がリソースファイルに登録済みの場合
			else
			{
			    txt_R_MenuName.setText(menuname);    
			}
			
		}
		
		//リソースキーを取得
		String resourcekey = param.getParameter(ResourceListBusiness.RESOURCE_KEY);
		String resourcename = param.getParameter(ResourceListBusiness.RESOURCENAME_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(resourcekey))
		{
			txt_FunctionResourceKey.setText(resourcekey);
			txt_R_FunctionName.setText(resourcename);
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
	 * メイン機能ID検索ボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_Search_Click(ActionEvent e) throws Exception
	{
	    //サブメニュー一覧画面へ検索条件をセットする
	    ForwardParameters param = new ForwardParameters();
	    
	    //処理中画面->結果画面
	    redirect("/listbox/functionlist/FunctionList.do", param, "/Progress.do");
	}

	/** 
	 * 登録ボタンが押下された時の処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
	    setFocus(txt_MenuId);
	    
		Connection conn = null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);
			txt_R_MainFunctionId.setText("");
			
			//メイン機能IDと機能表示順は設定時に値がセットされるため、ここでは「*」を仮にセット
			txt_R_MainFunctionId2.setText("*****");
			txt_R_FunctionDispNumber.setText("***");

			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);								
			
			//メニューID検索ボタンを有効にする
			btn_P_MenuId.setEnabled(true);
			//メニューIDテキストボックスを有効にする
			txt_MenuId.setReadOnly(false);
			//機能リソースキー検索ボタンを有効にする
			btn_P_FunctionResouceKey.setEnabled(true);
			//機能リソースキーテキストボックスを有効にする
			txt_FunctionResourceKey.setReadOnly(false);
			
			//登録中を保持
			this.getViewState().setString(PROCESS_KEY, INSERT);
			
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
	 * 修正ボタンが押下された時の処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Modify_Click(ActionEvent e) throws Exception
	{   
	    setFocus(txt_MenuId);
	    
		Connection conn =null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);
			
			//メイン機能IDテキストボックスを入力チェック
			txt_R_MainFunctionId.validate(true);

			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		

			//すでに同一のメイン機能IDが登録済みかを確認 
			if(!isDefined(txt_R_MainFunctionId.getText(), conn))
			{
				//指定されたメイン機能IDは登録されていません。
				message.setMsgResourceKey("6403009");
				return;
			}
			
			//メニューID検索ボタンを有効にする
			btn_P_MenuId.setEnabled(true);
			//メニューIDテキストボックスを有効にする
			txt_MenuId.setReadOnly(false);
			//機能リソースキー検索ボタンを有効にする
			btn_P_FunctionResouceKey.setEnabled(true);
			//機能リソースキーテキストボックスを有効にする
			txt_FunctionResourceKey.setReadOnly(false);
			
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
			
			//メイン機能IDテキストボックスを入力チェック
			txt_R_MainFunctionId.validate(true);
			
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			if(!isDefined(txt_R_MainFunctionId.getText(), conn))
			{
				//指定されたメイン機能IDは登録されていません。
				message.setMsgResourceKey("6403009");
				return;
			}
			
			//メニューID検索ボタンを無効にする
			btn_P_MenuId.setEnabled(false);
			//メニューIDテキストボックスを読み取り専用にする
			txt_MenuId.setReadOnly(true);
			//機能リソースキー検索ボタンを無効にする
			btn_P_FunctionResouceKey.setEnabled(false);
			//機能リソースキーテキストボックスを読み取り専用にする
			txt_FunctionResourceKey.setReadOnly(true);
			
			//データベースのデータをセットする
			mapping(conn);
			//削除中を保持
			this.getViewState().setString(PROCESS_KEY, DELETE);
			
		    //FunctionMap表の関連項目も削除されますが、よろしいですか？
		    btn_Commit.setBeforeConfirm("MSG-T0005");
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
	 * メニューID検索ボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_MenuId_Click(ActionEvent e) throws Exception
	{
		//メインメニュー一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
	    param.setParameter(MainMenuListBusiness.MENUID_KEY, txt_MenuId.getText());
		param.setParameter(MainMenuListBusiness.BTNFLAG_KEY,btn_P_MenuId.getId());
		
		//処理中画面->結果画面
		redirect("/listbox/mainmenulist/MainMenuList.do", param, "/Progress.do");
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
		
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			BaseHandler handle = new BaseHandler();
						
			//メイン機能IDをセット
			String mainfunctionid = txt_R_MainFunctionId2.getText();
			//機能表示順をセット
			String functiondispno = txt_R_FunctionDispNumber.getText();
			
			//メニューIDをセット
			String[] menuid = new String[1]; 
			menuid[0] = txt_MenuId.getText();				
			//機能リソースキーをセット
			String resourcekey = txt_FunctionResourceKey.getText();
			
			    
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
				//機能表示順の採番
				int dispNoMax = 1;
				List dispNoRet = handle.find("_select-5704", menuid, conn);
				//登録済みのメイン機能IDがある場合
				if(dispNoRet != null && dispNoRet.size() != 0)
				{
					ResultMap map = (ResultMap)dispNoRet.get(0);
					dispNoMax = map.getInt("MAX") + 1;
					if(dispNoMax == 0)
					{
					    dispNoMax = 1;
					}
				}
				//メイン機能IDが未登録の場合
				else
				{
					dispNoMax = 1;
				}
				//機能表示順に採番した値をセット
				functiondispno =Integer.toString(dispNoMax);
				
				//DBに数値データ以外の値が入っていた場合はメッセージをセットに飛ぶ
				//メイン機能IDの採番 例：10100 = メニューID+機能表示順+00
				mainfunctionid = "";
				//メニューIDをセット
				mainfunctionid = menuid[0];			
				//機能表示順の番号変換
				//1 → 01に変換
				if(Integer.parseInt(functiondispno) > -1 && Integer.parseInt(functiondispno) < 10)
				{
				    functiondispno = "0" + functiondispno;
				}
				//機能表示順を加算
				mainfunctionid = mainfunctionid.concat(functiondispno);
				//"00"を加算
				mainfunctionid = mainfunctionid.concat("00");
			    
				String[] mainfunctionid_array = new String[1];
				mainfunctionid_array[0] = mainfunctionid;
				//登録しようとしているメイン機能IDがすでに登録済みか検索
				for(int i=0; i<20 ; i++)
				{
					int cnt = handle.count("_select-5708", mainfunctionid_array, conn);
					if(cnt > 0)
					{
					    if(mainfunctionid_array[0].length() == 5)
					    {
						    int tmp = Integer.parseInt(mainfunctionid_array[0].substring(2));
						    tmp = tmp + 100;
						    mainfunctionid_array[0] = mainfunctionid_array[0].substring(0,2) + Integer.toString(tmp);				    					        
					    }
					    else if(mainfunctionid_array[0].length() == 6)
					    {
						    int tmp = Integer.parseInt(mainfunctionid_array[0].substring(3));
						    tmp = tmp + 100;
						    mainfunctionid_array[0] = mainfunctionid_array[0].substring(0,3) + Integer.toString(tmp);				    					        					        
					    }
					}
					else
					{
					    break;
					}
				}
				mainfunctionid = mainfunctionid_array[0];
				
				//メニューIDテキストボックス入力チェック(必須入力項目)
				txt_MenuId.validate();
				//機能リソースキーテキストボックス入力チェック(必須入力項目)
				txt_FunctionResourceKey.validate();
				//入力チェック
				txt_R_MenuName.validate(false);
				txt_R_FunctionName.validate(false);
								
				handle.create("_insert-5700", new String[]{mainfunctionid, functiondispno, resourcekey, menuid[0]},conn);
				
				//6401001=メイン機能ID={0}を登録しました。
				comp_msg = "6401001" + "\t" + mainfunctionid;
			}
			//修正時
			else if(proc.equals(MODIFY))
			{
			    //メイン機能ID入力チェック(必須入力項目)
				txt_R_MainFunctionId2.validate(true);
				
				//機能表示順テキストボックス入力チェック(必須入力項目)
				txt_R_FunctionDispNumber.validate();
				//メニューIDテキストボックス入力チェック(必須入力項目)
				txt_MenuId.validate();
				//機能リソースキーテキストボックス入力チェック(必須入力項目)
				txt_FunctionResourceKey.validate();
				//入力チェック
				txt_R_MenuName.validate(false);
				txt_R_FunctionName.validate(false);
								
				handle.modify("_update-5700", new String[]{functiondispno, resourcekey, menuid[0], mainfunctionid},conn);
				//6001004=修正しました。
				comp_msg = "6001004";
			}
			//削除時
			else if(proc.equals(DELETE))
			{
			    //メイン機能ID入力チェック(必須入力項目)
				txt_R_MainFunctionId2.validate(true);
				
				//FunctionMap表を削除
				handle.drop("_delete-5802", new String[]{mainfunctionid}, conn);
				//Function表を削除
				handle.drop("_delete-5700", new String[]{mainfunctionid}, conn);				
				
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
	    //メイン機能IDテキストボックスをクリア
	    txt_R_MainFunctionId2.setText("");
	    
	    //機能表示順テキストボックスをクリア
		txt_R_FunctionDispNumber.setText("");
		
		//メニューIDテキストボックスをクリア
		txt_MenuId.setText("");
		//メニューIDテキストボックスを読み取り専用にセット
		txt_MenuId.setReadOnly(true);		
	    //メニューID検索ボタンを無効にする
		btn_P_MenuId.setEnabled(false);
		//メニュー名テキストボックスをクリア
		txt_R_MenuName.setText("");
		
		//機能リソースキーテキストボックスをクリア
		txt_FunctionResourceKey.setText("");
		//機能リソースキーテキストボックスを読み取り専用にセット
		txt_FunctionResourceKey.setReadOnly(true);
		//機能リソースキー検索ボタンを無効にする
		btn_P_FunctionResouceKey.setEnabled(false);
		//機能名テキストボックスをクリア
		txt_R_FunctionName.setText("");
	   
	}


	/** 
	 * 機能リソースキー検索ボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_FunctionResouceKey_Click(ActionEvent e) throws Exception
	{
		//リソース一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
	    param.setParameter(ResourceListBusiness.RESOURCE_KEY, txt_FunctionResourceKey.getText());
		param.setParameter(ResourceListBusiness.BTNFLAG_KEY,btn_P_FunctionResouceKey.getId());
		
		//処理中画面->結果画面
		redirect("/listbox/resourcelist/ResourceList.do", param, "/Progress.do");
	}

	/** 
	 * すでに同一のメイン機能IDが登録済みかを確認します。
	 * @param key  TerminalNumber
	 * @param conn Connection
	 * @throws Exception 
	 */
	private boolean isDefined(String key, Connection conn) throws Exception
	{
		BaseHandler handle = new BaseHandler();
		//すでに同一のメイン機能IDが存在する場合
		if(handle.count("_select-5702",new String[]{key}, conn) > 0)
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
		List ret = handle.find("_select-5703", new String[]{txt_R_MainFunctionId.getText()}, conn);
			
		//登録済みのメイン機能IDがある場合
		if(ret != null && ret.size() != 0)
		{
			ResultMap map = (ResultMap)ret.get(0);
			String dispno = Integer.toString(map.getInt(FUNCTION_FUNCTIONDISPNUMBER));
			String menuid = map.getString(FUNCTION_MENUID);
			String mainmenu_resourcekey = map.getString(MAINMENU_MENURESOURCEKEY);
			String function_resourcekey = map.getString(FUNCTION_FUNCTIONRESOURCEKEY);
				
			txt_R_MainFunctionId2.setText(txt_R_MainFunctionId.getText());				
			txt_R_FunctionDispNumber.setText(dispno);
			txt_MenuId.setText(menuid);
			
			if(mainmenu_resourcekey == null)
			{
			    mainmenu_resourcekey = "";
			}
			//メニュー名がリソースに登録済みか判定
			if(DispResourceMap.getText(mainmenu_resourcekey) == null)
			{
			    //メニューリソースをセット
			    txt_R_MenuName.setText(mainmenu_resourcekey);    
			}
			else
			{
			    //メニュー名をセット
			    txt_R_MenuName.setText(DispResourceMap.getText(mainmenu_resourcekey));    
			}
			
			txt_FunctionResourceKey.setText(function_resourcekey);
			txt_R_FunctionName.setText(DispResourceMap.getText(function_resourcekey));
		}			
	
	}
	
}
//end of class
