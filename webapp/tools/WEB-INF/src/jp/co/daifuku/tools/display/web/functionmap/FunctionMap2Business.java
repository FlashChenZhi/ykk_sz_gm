// $Id: FunctionMap2Business.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.functionmap;
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
import jp.co.daifuku.tools.display.web.listbox.resourcelist.ResourceListBusiness;
import jp.co.daifuku.tools.util.DispResourceMap;
import jp.co.daifuku.ui.web.BusinessClassHelper;

/** <jp>
 * サブメニューボタン設定2画面目の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
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
 * <TR><TD><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </en> */
public class FunctionMap2Business extends FunctionMap2 implements ToolConstants, TableColumns
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
	    lbl_SettingName.setResourceKey("TLE-T0008");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("FunctionMap", this.getHttpRequest()) );
	
		setFocus(txt_ButtonResourceKey);
	 }

	/**
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	    // タブを選択状態に
		tab.setSelectedIndex(2);

		Connection conn = null;		
		try
		{
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);						
			BaseHandler handle = new BaseHandler();		
		    
		    //機能IDの値を判定
		    //１画面目で未登録のボタンを押下した場合
		    if(this.getViewState().getString("FunctionId") == null || this.getViewState().getString("FunctionId").equals(""))
		    {	
		        btn_BC_Delete.setEnabled(false);

		        //メイン機能IDを取得
			    String mainfunctionid = this.getViewState().getString("MainFunctionId");
			    //メイン機能IDを数値に変換
			    int int_mainfunctionid = Integer.parseInt(mainfunctionid);

			    //選択列を取得
				String col = this.getViewState().getString("ActiveCol");
				//数値に変換
				int int_col = Integer.parseInt(col) -1;
							    
			    //機能IDを生成
			    //10101 = メイン機能ID + 選択列
			    int int_functionid = int_mainfunctionid + int_col;
			    //文字列に変換
			    String[] functionid = new String[1];
				functionid[0] = Integer.toString(int_functionid);
			    
				//登録しようとしている機能IDがすでに登録済みか検索
				for(int i=0; i<10 ; i++)
				{
					int cnt = handle.count("_select-5809", functionid, conn);
					//すでに登録済みの場合
					if(cnt > 0)
					{
					    //機能IDに１加算
					    int_functionid = int_functionid + 1;
					    //下１桁が４を超えていた場合
					    if((int_functionid - int_mainfunctionid) > 4)
					    {
					        //機能IDの下１桁を１にする
					        int_functionid = int_mainfunctionid + 1;
					    }
					    functionid[0] = Integer.toString(int_functionid);				    					        
					}
					else
					{
					    break;
					}
				}
			    
			    //ラベルにセット
			    lbl_In_FunctionId.setText(functionid[0]);
			    lbl_In_FunctionName.setText(this.getViewState().getString("FunctionName"));
				// Frame名に"_self"をセット
				txt_FrameName.setText("_self");
				
				//登録中を保持
				this.getViewState().setString(PROCESS_KEY, INSERT);
				
		    }
		    //１画面目で登録済みボタンを押下した場合
		    else
		    {	
		        //機能IDを取得
		        String[] functionid = new String[1];
		        functionid[0] = this.getViewState().getString("FunctionId");
		        
			    //ラベルに１画面目からのパラメータをセット
			    lbl_In_FunctionId.setText(functionid[0]);
			    lbl_In_FunctionName.setText(this.getViewState().getString("FunctionName"));

				//FunctionMap表を取得
				List ret = handle.find("_select-5802", functionid, conn);
				if(ret != null && ret.size() != 0)
				{
					ResultMap map = (ResultMap)ret.get(0);
					
					//ボタンリソースキーを取得
					String buttonResourcekey = (String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY);
					//ボタン名を取得
					String buttonName = DispResourceMap.getText((String)map.get(FUNCTIONMAP_BUTTONRESOURCEKEY));
					//ページ名リソースキーを取得
					String pageNameResourcekey = (String)map.get(FUNCTIONMAP_PAGENAMERESOURCEKEY);
					//ページ名を取得
					String pageName = DispResourceMap.getText((String)map.get(FUNCTIONMAP_PAGENAMERESOURCEKEY));
					//URIを取得
					String uri = (String)map.get(FUNCTIONMAP_URI);
					//Frame名を取得
					String frameName = (String)map.get(FUNCTIONMAP_FRAMENAME);
					
					
					//各テキストボックスにセット
					txt_ButtonResourceKey.setText(buttonResourcekey);
					txt_R_ButtonName.setText(buttonName);
					txt_PageNameResouceKey.setText(pageNameResourcekey);
					txt_R_PageName.setText(pageName);
					txt_URI.setText(uri);
					txt_FrameName.setText(frameName);
					
					//修正中を保持
					this.getViewState().setString(PROCESS_KEY, MODIFY);
				}
		    }   
		}
		catch (NumberFormatException ex) 
		{
			//FUNCTION表のMAINFUNCTIONIDに数値以外のデータがセットされているため、機能IDを採番できません。
			message.setMsgResourceKey("6407002"); 
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
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
	 * @param e ActionEvent
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		//ボタンリソースキーを取得
		String button_resourcekey = param.getParameter(ResourceListBusiness.BUTTONRESOURCE_KEY);
		String button_resourcename = param.getParameter(ResourceListBusiness.BUTTONNAME_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(button_resourcekey))
		{
			txt_ButtonResourceKey.setText(button_resourcekey);
			txt_R_ButtonName.setText(button_resourcename);
		}
		
		//ページリソースキーを取得
		String page_resourcekey = param.getParameter(ResourceListBusiness.PAGENAMERESOURCE_KEY);
		String page_resourcename = param.getParameter(ResourceListBusiness.PAGENAME_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(page_resourcekey))
		{
			txt_PageNameResouceKey.setText(page_resourcekey);
			txt_R_PageName.setText(page_resourcename);
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
	    if(this.getViewState().getString("PRODUCTTYPE") != null)
	    {
		    //メニュー画面へ遷移
		    forward("/menu/SubMenu.do?Product="+ this.getViewState().getString("PRODUCTTYPE")
		            						   + "&MenuType=" + this.getViewState().getString("MENUTYPE"));	        
	    }
	    else
	    {
		    //メニュー画面へ遷移
		    forward("/menu/SubMenu.do?Product="+ this.getViewState().getString("ProductType2")
		            						   + "&MenuType=" + this.getViewState().getString("MenuType2"));	        
	    }
	}


	/** 
	 * ボタン名検索ボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_ButtonResouceKey_Click(ActionEvent e) throws Exception
	{
		//リソース一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
	    param.setParameter(ResourceListBusiness.RESOURCE_KEY, txt_ButtonResourceKey.getText());
		param.setParameter(ResourceListBusiness.BTNFLAG_KEY,btn_P_ButtonResouceKey.getId());
		
		//処理中画面->結果画面
		redirect("/listbox/resourcelist/ResourceList.do", param, "/Progress.do");
	}



	/** 
	 * ページ名検索ボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_PageResouceKey_Click(ActionEvent e) throws Exception
	{
		//リソース一覧画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
	    param.setParameter(ResourceListBusiness.RESOURCE_KEY, txt_PageNameResouceKey.getText());
		param.setParameter(ResourceListBusiness.BTNFLAG_KEY,btn_P_PageResouceKey.getId());
		
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
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			BaseHandler handle = new BaseHandler();
		    
		    //機能ID
		    String functionid = lbl_In_FunctionId.getText();
		    
		    //メイン機能ID
		    String mainfunctionid = this.getViewState().getString("MainFunctionId");

		    //ボタン表示順
		    String buttondispnumber = this.getViewState().getString("ActiveCol");
			int int_buttondispnumber = Integer.parseInt(buttondispnumber) -1;
			buttondispnumber = Integer.toString(int_buttondispnumber);
		    
		    //ボタンリソースキー
			String buttonresourcekey = txt_ButtonResourceKey.getText();
			//ボタン名を取得
			String buttonName = DispResourceMap.getText(buttonresourcekey);
			txt_R_ButtonName.setText(buttonName);
			
			//ページ名リソースキー
			String pagenameresourcekey = txt_PageNameResouceKey.getText();
			//ページ名を取得
			String pageName = DispResourceMap.getText(pagenameresourcekey);
			txt_R_PageName.setText(pageName);
			
			
			//認証チェック(現在は未実装)
			int doauthentication_int = DOAUTHENTICATION_OFF;
			String doauthentication = Integer.toString(doauthentication_int);
			//URI
			String uri = txt_URI.getText();
			//Frame名
			String framename = txt_FrameName.getText();
			
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
				//ボタンリソースキー入力チェック(必須入力項目)
				txt_ButtonResourceKey.validate();
				//ページ名リソースキー入力チェック(必須入力項目)
				txt_PageNameResouceKey.validate();
				//URI入力チェック(必須入力項目)
				txt_URI.validate();
				//Frame名入力チェック(必須入力項目)
				txt_FrameName.validate();
				
				handle.create("_insert-5800", new String[]{functionid, 
				        								  mainfunctionid,
				        								  buttondispnumber,
				        								  buttonresourcekey,
				        								  pagenameresourcekey,
				        								  doauthentication,
				        								  uri,
				        								  framename},conn);
				//6001003=登録しました。
				comp_msg = "6001003";
				
				//修正モードに移行
				//修正中を保持
				this.getViewState().setString(PROCESS_KEY, MODIFY);
				btn_BC_Delete.setEnabled(true);
			}
			//修正時
			else if(proc.equals(MODIFY))
			{
				//ボタンリソースキー入力チェック(必須入力項目)
				txt_ButtonResourceKey.validate();
				//ページ名リソースキー入力チェック(必須入力項目)
				txt_PageNameResouceKey.validate();
				//URI入力チェック(必須入力項目)
				txt_URI.validate();
				//Frame名入力チェック(必須入力項目)
				txt_FrameName.validate();
				
				handle.modify("_update-5801", new String[]{buttonresourcekey,
				        								  pagenameresourcekey,
				        								  uri,
				        								  framename,
				        								  functionid},conn);
				//6001004=修正しました。
				comp_msg = "6001004";
			}
			//削除時
			else if(proc.equals(DELETE))
			{
			    //機能IDを取得
			    String[] delete_functionid = new String[1];
			    delete_functionid[0] = lbl_In_FunctionId.getText();
			    
			    int cnt = handle.count("_select-5803", delete_functionid, conn);	
			    if(cnt == 0)
			    {
					//指定された機能IDは登録されていません。
					message.setMsgResourceKey("6403015");
					return;
			    }
			    
				handle.drop("_delete-5800", new String[]{functionid}, conn);
				//6001005=削除しました。
				comp_msg = "6001005";
				//クリア処理
				btn_Clear_Click(e);
				
				//登録モードに移行
				//登録中を保持
				this.getViewState().setString(PROCESS_KEY, INSERT);
				btn_BC_Delete.setEnabled(false);
			}
			//設定
			conn.commit();

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
		//ボタンリソースキーテキストボックスをクリア
		txt_ButtonResourceKey.setText("");
		//ボタン名テキストボックスをクリア
		txt_R_ButtonName.setText("");
		//ページ名リソースキーテキストボックスをクリア
		txt_PageNameResouceKey.setText("");
		//ページ名テキストボックスをクリア
		txt_R_PageName.setText("");
		//URIテキストボックスをクリア
		txt_URI.setText("");
		// Frame名に"_self"をセット
		txt_FrameName.setText("_self");	
	}

	/** 
	 * 削除ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_BC_Delete_Click(ActionEvent e) throws Exception
	{
		//削除中を保持
		this.getViewState().setString(PROCESS_KEY, DELETE);
		
		//設定処理
		btn_Commit_Click(e);
	}


	/** 
	 * 戻るボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//ViewStateよりプロダクトタイプを取得
	    String ProductType = this.getViewState().getString("PRODUCTTYPE"); 
		//ViewStateよりメニュータイプを取得
	    String MenuType = this.getViewState().getString("MENUTYPE"); 

	    //プロダクトタイプをViewStateに保存
	    this.getViewState().setString("ProductType2",ProductType);
	    //メニュータイプをViewStateに保存
	    this.getViewState().setString("MenuType2",MenuType);	    

	    
	    //１画面目に遷移
	    forward("/functionmap/FunctionMap1.do");
	}




}
//end of class
