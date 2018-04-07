// $Id: AuthenticationSystemBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.authenticationsystem;

import java.sql.Connection;
import java.util.List;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.dbhandler.ResultMap;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.ui.web.BusinessClassHelper;

/** <jp>
 * システム設定の画面クラスです。
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
public class AuthenticationSystemBusiness extends AuthenticationSystem implements ToolConstants, TableColumns
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
	    lbl_SettingName.setResourceKey("TLE-T0018");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("AuthenticationSystem", this.getHttpRequest()) );
	    setFocus(txt_LoginMax);
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
	    
		//認証ミス猶予回数プルダウンに値をセット
	    //value、resourceKey、text、selected
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_UNRESTRICTED),"PUL-T0001","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER1),"PUL-T0003","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER2),"PUL-T0004","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER3),"PUL-T0005","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER4),"PUL-T0006","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER5),"PUL-T0007","",false);	
	    	    
	    Connection conn =null;		
		try
		{		
		    //コネクション取得
		    conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		     
			BaseHandler handle = new BaseHandler();
			List ret = handle.find("_select-5300", null, conn);
					
			//登録済みのデータがある場合
			if(ret != null && ret.size() != 0)
			{
				ResultMap map = (ResultMap)ret.get(0);

				//同時ログイン数
				int loginmax = map.getInt(AUTHENTICATIONSYSTEM_LOGINMAX);
				
				//同一ユーザログイン
				int sameloginuser = map.getInt(AUTHENTICATIONSYSTEM_SAMELOGINUSER);

				//場所の制約チェック
				int restrictionsplace = map.getInt(AUTHENTICATIONSYSTEM_RESTRICTIONSPLACE);
				
				//メインメニューの種類
				int mainmenutype = map.getInt(AUTHENTICATIONSYSTEM_MAINMENUTYPE);
				
				//認証ミス猶予回数
				int failedloginattempts = map.getInt(AUTHENTICATIONSYSTEM_FAILEDLOGINATTEMPTS);
				
				//認証ミスカウント保持期間
				int failedtime = map.getInt(AUTHENTICATIONSYSTEM_FAILEDTIME);
					
				//各コントロールへのセット
				//同時ログイン数が無制限になっているか判定
				if(loginmax == LOGINMAX_UNRESTRICTED)
				{
					//同時ログイン数をセット
					txt_LoginMax.setText(Integer.toString(loginmax));
				    txt_LoginMax.setReadOnly(true);
				    chk_Unrestriced.setChecked(true);
				}
				else
				{
					//同時ログイン数をセット
					txt_LoginMax.setText(Integer.toString(loginmax));
				    txt_LoginMax.setReadOnly(false);
					chk_Unrestriced.setChecked(false);				    
				}
				
				//同一ユーザログインをセット
				if(sameloginuser == 0) rdo_Deny.setChecked(true);
				else if(sameloginuser == 1) rdo_Permission.setChecked(true);
				
				//場所の制約チェックをセット
				if(restrictionsplace == 0) rdo_DisableCheck.setChecked(true);
				else if(restrictionsplace == 1) rdo_EnableCheck.setChecked(true);
				
				//メインメニューの種類をセット
				if(mainmenutype == 0) rdo_SmallIcon.setChecked(true);
				else if(mainmenutype == 1) rdo_LargeIcon.setChecked(true);

				//認証ミス猶予回数をセット
				if(failedloginattempts == FAILEDLOGINATTEMPTS_UNRESTRICTED) pul_FailedLoginAttempts.setSelectedIndex(0);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER1) pul_FailedLoginAttempts.setSelectedIndex(1);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER2) pul_FailedLoginAttempts.setSelectedIndex(2);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER3) pul_FailedLoginAttempts.setSelectedIndex(3);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER4) pul_FailedLoginAttempts.setSelectedIndex(4);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER5) pul_FailedLoginAttempts.setSelectedIndex(5);

				//認証ミスカウント保持期間が無制限になっているか判定
				if(failedtime == FAILEDTIME_UNRESTRICTED)
				{
					//認証ミスカウント保持期間をセット
					txt_FailedTime.setText(Integer.toString(failedtime));
				    txt_FailedTime.setReadOnly(true);
				    chk_Unrestriced2.setChecked(true);
				}
				else
				{
					//認証ミスカウント保持期間をセット
					txt_FailedTime.setText(Integer.toString(failedtime));
				    txt_FailedTime.setReadOnly(false);
				    chk_Unrestriced2.setChecked(false);				    
				}
				
				//修正中を保持
				this.getViewState().setString(PROCESS_KEY, MODIFY);
			}
			//登録済みのデータがない場合
			else
			{
				//同一ユーザログインラジオボタンにチェックを入れる
				rdo_Permission.setChecked(true);				
				//場所の制約チェックラジオボタンにチェックを入れる
				rdo_EnableCheck.setChecked(true);
				//メインメニューの種類ラジオボタンにチェックを入れる
				rdo_LargeIcon.setChecked(true);
				
				//登録中を保持
				this.getViewState().setString(PROCESS_KEY, INSERT);
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
	 * 無制限チェックボックス(同時ログイン数)が押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Unrestriced_Change(ActionEvent e) throws Exception
	{
	    if(txt_LoginMax.getReadOnly() == true)
	    {
 		    //同時ログイン数テキストボックスを有効にセット
			txt_LoginMax.setReadOnly(false);
			//フォーカスを同時ログイン数テキストボックスにセット
			setFocus(txt_LoginMax);
			txt_LoginMax.setText("");
			
	    }
	    else
	    {
		   //同時ログイン数テキストボックスを読み取り専用にセット
	        txt_LoginMax.setReadOnly(true);	
	        txt_LoginMax.setText(Integer.toString(LOGINMAX_UNRESTRICTED));
	    }				
	}

	/** 
	 * 無制限チェックボックス(認証ミスカウント保持期間)が押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Unrestriced2_Change(ActionEvent e) throws Exception
	{
	    if(txt_FailedTime.getReadOnly() == true)
	    {
 		    //認証ミスカウント保持期間テキストボックスを有効にセット
	        txt_FailedTime.setReadOnly(false);
			//フォーカスを認証ミスカウント保持期テキストボックスにセット
			setFocus(txt_FailedTime);
			txt_FailedTime.setText("");
			
	    }
	    else
	    {
		   //認証ミスカウント保持期間テキストボックスを読み取り専用にセット
	        txt_FailedTime.setReadOnly(true);	
	        txt_FailedTime.setText(Integer.toString(FAILEDTIME_UNRESTRICTED));
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
		    //同時ログイン数入力チェック(必須入力項目)
			txt_LoginMax.validate(true);
		    //認証ミス猶予保持期間入力チェック(必須入力項目)
			txt_FailedTime.validate(true);
			
			//同時ログイン数			
			int loginmax_int = txt_LoginMax.getInt();
			if(loginmax_int < 1 && loginmax_int != LOGINMAX_UNRESTRICTED)
			{
				//同時ログイン数には1以上の数値を入力してください（ただし、-1は除きます）。
				message.setMsgResourceKey("6403036"); 
				return;
			}
			String loginmax = Integer.toString(loginmax_int);
			
			//認証ミスカウント保持期間
			int failedtime_int = txt_FailedTime.getInt();
			if(failedtime_int < 0 && failedtime_int != FAILEDTIME_UNRESTRICTED)
			{
				//認証ミスカウント保持期間(分)には0以上の数値を入力してください（ただし、-1は除きます）。
				message.setMsgResourceKey("6403037"); 
				return;
			}		
			String failedtime = Integer.toString(failedtime_int);

			//同一ユーザログイン
			int sameloginuser_int = 0;
			if(rdo_Deny.getChecked()) sameloginuser_int = SAMELOGINUSER_DENY;
			else if(rdo_Permission.getChecked()) sameloginuser_int = SAMELOGINUSER_PERMISSION;
			String  sameloginuser = Integer.toString(sameloginuser_int);
			
			//場所の制約チェック
			int restrictionsplace_int = 0;
			if(rdo_EnableCheck.getChecked()) restrictionsplace_int = RESTRICTIONSPLACE_ENABLE;
			else if(rdo_DisableCheck.getChecked()) restrictionsplace_int = RESTRICTIONSPLACE_DISABLE;
			String restrictionsplace = Integer.toString(restrictionsplace_int);
			
			//メインメニューの種類
			int mainmenutype_int = 0;
			if(rdo_LargeIcon.getChecked()) mainmenutype_int = MAINMENUTYPE_LARGEICON;
			else if(rdo_SmallIcon.getChecked()) mainmenutype_int = MAINMENUTYPE_SMALLICON;
			String mainmenutype = Integer.toString(mainmenutype_int);
			
			//認証ミス猶予回数をセット
			int failedloginattempts_int = 0;
			if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_UNRESTRICTED))) failedloginattempts_int = FAILEDLOGINATTEMPTS_UNRESTRICTED;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER1))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER1;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER2))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER2;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER3))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER3;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER4))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER4;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER5))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER5;
			String failedloginattempts = Integer.toString(failedloginattempts_int);
			
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
				handle.create("_insert-5300", new String[]{loginmax, sameloginuser, restrictionsplace, mainmenutype, failedloginattempts, failedtime},conn);
				
				//6001003=登録しました。
				comp_msg = "6001003";
				
				//修正モードに移行
				//修正中を保持
				this.getViewState().setString(PROCESS_KEY, MODIFY);
			}
			//更新時
			else if(proc.equals(MODIFY))
			{
				handle.modify("_update-5300", new String[]{loginmax, sameloginuser, restrictionsplace, mainmenutype, failedloginattempts, failedtime},conn);
				
				//6001018	更新しました。
				comp_msg = "6001018";
			    
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
	 * 取消ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Click(ActionEvent e) throws Exception
	{   
	    Connection conn =null;		
		try
		{		
		    //コネクション取得
		    conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		     
			BaseHandler handle = new BaseHandler();
			List ret = handle.find("_select-5300", null, conn);
					
			//登録済みのデータがある場合
			if(ret != null && ret.size() != 0)
			{
				ResultMap map = (ResultMap)ret.get(0);

				//同時ログイン数
				int loginmax = map.getInt(AUTHENTICATIONSYSTEM_LOGINMAX);
				
				//同一ユーザログイン
				int sameloginuser = map.getInt(AUTHENTICATIONSYSTEM_SAMELOGINUSER);

				//場所の制約チェック
				int restrictionsplace = map.getInt(AUTHENTICATIONSYSTEM_RESTRICTIONSPLACE);
				
				//メインメニューの種類
				int mainmenutype = map.getInt(AUTHENTICATIONSYSTEM_MAINMENUTYPE);
				
				//認証ミス猶予回数
				int failedloginattempts = map.getInt(AUTHENTICATIONSYSTEM_FAILEDLOGINATTEMPTS);
				
				//認証ミスカウント保持期間
				int failedtime = map.getInt(AUTHENTICATIONSYSTEM_FAILEDTIME);
					
				//各コントロールへのセット

				//同時ログイン数が無制限になっているか判定
				if(loginmax == LOGINMAX_UNRESTRICTED)
				{
					//同時ログイン数をセット
					txt_LoginMax.setText(Integer.toString(loginmax));
				    txt_LoginMax.setReadOnly(true);
				    chk_Unrestriced.setChecked(true);
				}
				else
				{
					//同時ログイン数をセット
					txt_LoginMax.setText(Integer.toString(loginmax));
				    txt_LoginMax.setReadOnly(false);
					chk_Unrestriced.setChecked(false);				    
				}

				//同一ユーザログインをセット
				if(sameloginuser == 0) 
				{
				    rdo_Deny.setChecked(true);
				    rdo_Permission.setChecked(false);
				}
				else if(sameloginuser == 1) 
				{
				    rdo_Deny.setChecked(false);
				    rdo_Permission.setChecked(true);
				}
				
				//場所の制約チェックをセット
				if(restrictionsplace == 0)
				{
				    rdo_DisableCheck.setChecked(true);
				    rdo_EnableCheck.setChecked(false);
				}
				else if(restrictionsplace == 1)
				{
				    rdo_DisableCheck.setChecked(false);
				    rdo_EnableCheck.setChecked(true);
				}
				
				//メインメニューの種類をセット
				if(mainmenutype == 0)
				{
				    rdo_SmallIcon.setChecked(true);
				    rdo_LargeIcon.setChecked(false);
				}
				else if(mainmenutype == 1)
				{
				    rdo_SmallIcon.setChecked(false);
				    rdo_LargeIcon.setChecked(true);   
				}

				//認証ミス猶予回数をセット
				if(failedloginattempts == FAILEDLOGINATTEMPTS_UNRESTRICTED) pul_FailedLoginAttempts.setSelectedIndex(0);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER1) pul_FailedLoginAttempts.setSelectedIndex(1);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER2) pul_FailedLoginAttempts.setSelectedIndex(2);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER3) pul_FailedLoginAttempts.setSelectedIndex(3);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER4) pul_FailedLoginAttempts.setSelectedIndex(4);
				else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER5) pul_FailedLoginAttempts.setSelectedIndex(5);

				//認証ミスカウント保持期間が無制限になっているか判定
				if(failedtime == FAILEDTIME_UNRESTRICTED)
				{
					//認証ミスカウント保持期間をセット
					txt_FailedTime.setText(Integer.toString(failedtime));
				    txt_FailedTime.setReadOnly(true);
				    chk_Unrestriced2.setChecked(true);
				}
				else
				{
					//認証ミスカウント保持期間をセット
					txt_FailedTime.setText(Integer.toString(failedtime));
				    txt_FailedTime.setReadOnly(false);
				    chk_Unrestriced2.setChecked(false);				    
				}
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


}
//end of class
