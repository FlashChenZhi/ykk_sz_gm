// $Id: UserBusiness.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.user;

import java.sql.Connection;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.AbstractAuthentication;
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
import jp.co.daifuku.tools.display.web.listbox.rolelist.RoleListBusiness;
import jp.co.daifuku.tools.display.web.listbox.userlist.UserListBusiness;
import jp.co.daifuku.tools.util.ToolParam;
import jp.co.daifuku.tools.util.ToolValidator;
import jp.co.daifuku.ui.web.BusinessClassHelper;

/** <jp>
 * ユーザ設定の画面クラスです。
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
public class UserBusiness extends User implements ToolConstants, TableColumns
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
	    lbl_SettingName.setResourceKey("TLE-T0014");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("User", this.getHttpRequest()) );
    
		setFocus(txt_UserId);
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
	    
    	//パスワードテキストボックスを読み取り専用にセット
		txt_Password.setReadOnly(true);
		//パスワード更新間隔テキストボックスを読み取り専用にセット
		txt_PwdChangeInterval.setReadOnly(true);
		//有効期限を無期限にするチェックボックスを読み取り専用にセット
		chk_PwdChangeInterval.setEnabled(false);
		//ロールIDテキストボックスを読み取り専用にセット
		txt_RoleId.setReadOnly(true);
		//ロールID検索ボタンを読み取り専用にセット
		btn_P_Search2.setEnabled(false);
		//同一ユーザログイン可能数テキストボックスを読み取り専用にセット
		txt_SameUserLoginMax.setReadOnly(true);
		//認証ミス猶予回数プルダウンを読み取り専用にセット
		pul_FailedLoginAttempts.setEnabled(false);
		//ユーザ名テキストボックスを読み取り専用にセット
		txt_UserName.setReadOnly(true);
		//所属テキストボックスを読み取り専用にセット
		txt_Belonging.setReadOnly(true);
		//生年月日テキストボックスを読み取り専用にセット
		txt_BirthDate.setReadOnly(true);
		//性別プルダウンを読み取り専用にセット
		pul_Sex.setEnabled(false);
		//備考テキストボックスを読み取り専用にセット
		txt_Note.setReadOnly(true);
		
		//認証ミス猶予回数プルダウンに値をセット
	    //value、resourceKey、text、selected
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_UNRESTRICTED),"PUL-T0001","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NOCOUNT),"PUL-T0002","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER1),"PUL-T0003","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER2),"PUL-T0004","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER3),"PUL-T0005","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER4),"PUL-T0006","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER5),"PUL-T0007","",false);	
	    
	    //性別プルダウンに値をセット
	    //value、resourceKey、text、selected
	    pul_Sex.addItem(Integer.toString(SEX_UNINPUT),"PUL-T0008","",false);
	    pul_Sex.addItem(Integer.toString(SEX_MALE),"PUL-T0009","",false);
	    pul_Sex.addItem(Integer.toString(SEX_FEMALE),"PUL-T0010","",false);
	    	    
	}

	
	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
	 * @param e ActionEvent
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//ユーザIDを取得
		String userkey = param.getParameter(UserListBusiness.USERID_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(userkey))
		{
			txt_UserId.setText(userkey);
		}
		
		//ロールIDを取得
		String rolekey = param.getParameter(RoleListBusiness.ROLEID_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(rolekey))
		{
			txt_RoleId.setText(rolekey);
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
	 * ユーザID検索ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_Search_Click(ActionEvent e) throws Exception
	{
	    //ユーザ一覧画面へ検索条件をセットする
	    ForwardParameters param = new ForwardParameters();
	    param.setParameter(UserListBusiness.USERID_KEY, txt_UserId.getText());
	    
		//ViewStateよりメニュータイプを取得
	    String menutype = this.getViewState().getString("MENUTYPE"); 
	    param.setParameter(UserListBusiness.MENUTYPE_KEY, menutype);
	    
	    //処理中画面->結果画面
	    redirect("/listbox/userlist/UserList.do", param, "/Progress.do");
	}

	/** 
	 * 登録ボタンが押下された時の処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
	    setFocus(txt_Password);
		Connection conn = null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);

			//ユーザIDテキストボックスを入力チェック
			txt_UserId.validate(true);
			
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			
			//すでに同一のユーザIDが登録済みかを確認
			if(isDefined(txt_UserId.getText(), conn))
			{
				//すでに同一のユーザIDが登録されています。
				message.setMsgResourceKey("6403007"); 
				return;
			}
			
			//ViewStateよりメニュータイプを取得
		    String menutype = this.getViewState().getString("MENUTYPE"); 
		    //メニュータイプが客先の場合
		    if(menutype.equals(MENU_CUSTOMER))
		    {
			    //MenuToolプロパティファイルより項目の有効・無効を判定
				if(ToolParam.getProperty("User.Password").equals("true"))
				{
					//パスワードテキストボックスを有効にする
					txt_Password.setReadOnly(false);			    
				}
			    if(ToolParam.getProperty("User.PwdChangeInterval").equals("true"))
			    {
					//パスワード更新間隔テキストボックスを有効にする
					txt_PwdChangeInterval.setReadOnly(false);		        
					//有効期限を無期限にするチェックボックスを有効にする
					chk_PwdChangeInterval.setEnabled(true);
			    }
			    if(ToolParam.getProperty("User.RoleId").equals("true"))
			    {
					//ロールIDテキストボックスを有効にする
					txt_RoleId.setReadOnly(false);
					//ロールID検索ボタンを有効にする
					btn_P_Search2.setEnabled(true);
			    }
			    if(ToolParam.getProperty("User.SameUserLoginMax").equals("true"))
			    {
					//同一ユーザログイン可能数テキストボックスを有効にする
					txt_SameUserLoginMax.setReadOnly(false);    
			    }
			    if(ToolParam.getProperty("User.FailedLoginAttempts").equals("true"))
			    {
					//認証ミス猶予回数プルダウンを有効にする
					pul_FailedLoginAttempts.setEnabled(true);		        
			    }
			    if(ToolParam.getProperty("User.UserName").equals("true"))
			    {
					//ユーザ名テキストボックスを有効にする
					txt_UserName.setReadOnly(false);		        
			    }
			    if(ToolParam.getProperty("User.Belonging").equals("true"))
			    {
					//所属テキストボックスを有効にする
					txt_Belonging.setReadOnly(false);		        
			    }
			    if(ToolParam.getProperty("User.BirthDate").equals("true"))
			    {
					//生年月日テキストボックスを有効にする
					txt_BirthDate.setReadOnly(false);		        
			    }
			    if(ToolParam.getProperty("User.Sex").equals("true"))
			    {
					//性別プルダウンを有効にする
					pul_Sex.setEnabled(true);		        
			    }
			    if(ToolParam.getProperty("User.Note").equals("true"))
			    {
					//備考テキストボックスを有効にする
					txt_Note.setReadOnly(false);		        
			    }
		    }
		    //メニュータイプが設定者の場合
		    else
		    {
				//パスワードテキストボックスを有効にする
				txt_Password.setReadOnly(false);			    
				//パスワード更新間隔テキストボックスを有効にする
				txt_PwdChangeInterval.setReadOnly(false);		        
				//有効期限を無期限にするチェックボックスを有効にする
				chk_PwdChangeInterval.setEnabled(true);
				//ロールIDテキストボックスを有効にする
				txt_RoleId.setReadOnly(false);
				//ロールID検索ボタンを有効にする
				btn_P_Search2.setEnabled(true);
				//同一ユーザログイン可能数テキストボックスを有効にする
				txt_SameUserLoginMax.setReadOnly(false);    
				//認証ミス猶予回数プルダウンを有効にする
				pul_FailedLoginAttempts.setEnabled(true);		        
				//ユーザ名テキストボックスを有効にする
				txt_UserName.setReadOnly(false);		        
				//所属テキストボックスを有効にする
				txt_Belonging.setReadOnly(false);		        
				//生年月日テキストボックスを有効にする
				txt_BirthDate.setReadOnly(false);		        
				//性別プルダウンを有効にする
				pul_Sex.setEnabled(true);		        
				//備考テキストボックスを有効にする
				txt_Note.setReadOnly(false);		        
		    }
			
			//ユーザIDをテキストボックスにセット
			txt_R_UserId.setText(txt_UserId.getText());
			
			//登録中を保持
			this.getViewState().setString(PROCESS_KEY, INSERT);
		
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
	    setFocus(txt_Password);
		Connection conn =null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);
			
			//ユーザIDテキストボックスを入力チェック
			txt_UserId.validate(true);

			//ViewStateよりメニュータイプを取得
		    String menutype = this.getViewState().getString("MENUTYPE"); 
			if(txt_UserId.getText().equals(AbstractAuthentication.ANONYMOUS_USER) && menutype.equals(MENU_CUSTOMER))
			{
				//指定されたユーザIDはシステム定義のユーザのため修正できません。
				message.setMsgResourceKey("6403014"); 
				return;
			}
			
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		

			//すでに同一のユーザIDが登録済みかを確認 
			if(!isDefined(txt_UserId.getText(), conn))
			{
				//指定されたユーザIDは登録されていません。
				message.setMsgResourceKey("6403008"); 
				return;
			}

			//データベースのデータをセットする
			mapping(conn);

			//ViewStateよりメニュータイプを取得
			menutype = this.getViewState().getString("MENUTYPE"); 
		    //メニュータイプが客先の場合
		    if(menutype.equals(MENU_CUSTOMER))
		    {
			    //MenuToolプロパティファイルより項目の有効・無効を判定
			    if(ToolParam.getProperty("User.Password").equals("true"))
			    {
					//パスワードテキストボックスを有効にする
					txt_Password.setReadOnly(false);		       
			    }

			    if(ToolParam.getProperty("User.PwdChangeInterval").equals("true"))
			    {
					//有効期限を無期限にするチェックボックスを有効にする
					chk_PwdChangeInterval.setEnabled(true);
					//パスワード更新間隔テキストボックスの値を判定
					if(txt_PwdChangeInterval.getText().equals(Integer.toString(PWDCHANGEINTERVAL_UNRESTRICTED)))
					{
					    //有効期限を無期限にするチェックボックスにチェックを入れる
					    chk_PwdChangeInterval.setChecked(true);
					}
					else
					{
					    //有効期限を無期限にするチェックボックスにチェックをはずす
					    chk_PwdChangeInterval.setChecked(false);			    
					}
					
					//有効期限を無期限にするチェックボックスを判別
					if(chk_PwdChangeInterval.getChecked() == true)
					{
					    //パスワード更新間隔テキストボックスを有効にする
						txt_PwdChangeInterval.setReadOnly(true);			    
					}
					else
					{
					    //パスワード更新間隔テキストボックスを無効にする
						txt_PwdChangeInterval.setReadOnly(false);			    			    
					}		        
			    }
				
			    if(ToolParam.getProperty("User.RoleId").equals("true"))
			    {
					//ロールIDテキストボックスを有効にする
					txt_RoleId.setReadOnly(false);
					//ロールID検索ボタンを有効にする
					btn_P_Search2.setEnabled(true);
			    }
			    if(ToolParam.getProperty("User.SameUserLoginMax").equals("true"))
			    {
					//同一ユーザログイン可能数テキストボックスを有効にする
					txt_SameUserLoginMax.setReadOnly(false);    
			    }
			    if(ToolParam.getProperty("User.FailedLoginAttempts").equals("true"))
			    {
					//認証ミス猶予回数プルダウンを有効にする
					pul_FailedLoginAttempts.setEnabled(true);		        
			    }
			    if(ToolParam.getProperty("User.UserName").equals("true"))
			    {
					//ユーザ名テキストボックスを有効にする
					txt_UserName.setReadOnly(false);		        
			    }
			    if(ToolParam.getProperty("User.Belonging").equals("true"))
			    {
					//所属テキストボックスを有効にする
					txt_Belonging.setReadOnly(false);		        
			    }
			    if(ToolParam.getProperty("User.BirthDate").equals("true"))
			    {
					//生年月日テキストボックスを有効にする
					txt_BirthDate.setReadOnly(false);		        
			    }
			    if(ToolParam.getProperty("User.Sex").equals("true"))
			    {
					//性別プルダウンを有効にする
					pul_Sex.setEnabled(true);		        
			    }
			    if(ToolParam.getProperty("User.Note").equals("true"))
			    {
					//備考テキストボックスを有効にする
					txt_Note.setReadOnly(false);		        
			    }
		    }
		    //メニュータイプが設定者の場合
			else
			{
				//パスワードテキストボックスを有効にする
				txt_Password.setReadOnly(false);		       
				//有効期限を無期限にするチェックボックスを有効にする
				chk_PwdChangeInterval.setEnabled(true);

				//パスワード更新間隔テキストボックスの値を判定
				if(txt_PwdChangeInterval.getText().equals(Integer.toString(PWDCHANGEINTERVAL_UNRESTRICTED)))
				{
				    //有効期限を無期限にするチェックボックスにチェックを入れる
				    chk_PwdChangeInterval.setChecked(true);
				}
				else
				{
				    //有効期限を無期限にするチェックボックスにチェックをはずす
				    chk_PwdChangeInterval.setChecked(false);			    
				}
					
				//有効期限を無期限にするチェックボックスを判別
				if(chk_PwdChangeInterval.getChecked() == true)
				{
				    //パスワード更新間隔テキストボックスを有効にする
					txt_PwdChangeInterval.setReadOnly(true);			    
				}
				else
				{
				    //パスワード更新間隔テキストボックスを無効にする
					txt_PwdChangeInterval.setReadOnly(false);			    			    
				}		        

				//ロールIDテキストボックスを有効にする
				txt_RoleId.setReadOnly(false);
				//ロールID検索ボタンを有効にする
				btn_P_Search2.setEnabled(true);
				//同一ユーザログイン可能数テキストボックスを有効にする
				txt_SameUserLoginMax.setReadOnly(false);    
				//認証ミス猶予回数プルダウンを有効にする
				pul_FailedLoginAttempts.setEnabled(true);		        
				//ユーザ名テキストボックスを有効にする
				txt_UserName.setReadOnly(false);		        
				//所属テキストボックスを有効にする
				txt_Belonging.setReadOnly(false);		        
				//生年月日テキストボックスを有効にする
				txt_BirthDate.setReadOnly(false);		        
				//性別プルダウンを有効にする
				pul_Sex.setEnabled(true);		        
				//備考テキストボックスを有効にする
				txt_Note.setReadOnly(false);		            
			}
			
			//ユーザIDをテキストボックスにセット
			txt_R_UserId.setText(txt_UserId.getText());
			
			//修正中を保持
			this.getViewState().setString(PROCESS_KEY, MODIFY);
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
			
			//ユーザIDテキストボックスを入力チェック
			txt_UserId.validate(true);
			if(txt_UserId.getText().equals(AbstractAuthentication.ANONYMOUS_USER))
			{
				//指定されたユーザIDはシステム定義のユーザのため削除できません。
				message.setMsgResourceKey("6403013"); 
				return;			    
			}
			
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			    
			//データベースのデータをセットする
			mapping(conn);
			
			//パスワード更新間隔テキストボックスの値を判定
			if(txt_PwdChangeInterval.getText().equals(Integer.toString(PWDCHANGEINTERVAL_UNRESTRICTED)))
			{
			    //有効期限を無期限にするチェックボックスにチェックを入れる
			    chk_PwdChangeInterval.setChecked(true);
			}
			else
			{
			    //有効期限を無期限にするチェックボックスにチェックをはずす
			    chk_PwdChangeInterval.setChecked(false);			    
			}
			
			//ユーザIDテキストボックスを入力チェック
			txt_UserId.validate(true);	
			
			//すでに同一のユーザIDが登録済みかを確認 
			if(!isDefined(txt_UserId.getText(), conn))
			{
				//指定されたユーザIDは登録されていません。
				message.setMsgResourceKey("6403008"); 
				return;
			}
			
			//削除中を保持
			this.getViewState().setString(PROCESS_KEY, DELETE);
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
	 * ロールID検索ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_Search2_Click(ActionEvent e) throws Exception
	{
	    //ロール一覧画面へ検索条件をセットする
	    ForwardParameters param = new ForwardParameters();
	    param.setParameter(RoleListBusiness.ROLEID_KEY, txt_RoleId.getText());
	    
	    //処理中画面->結果画面
	    redirect("/listbox/rolelist/RoleList.do", param, "/Progress.do");
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
		    //ユーザID入力チェック(必須入力項目)
			txt_R_UserId.validate();
		
			//ユーザIDをセット
			String userid = txt_R_UserId.getText();
			//パスワードをセット
			String password = txt_Password.getText();								
			//パスワード更新間隔をセット
			int pwdchangeinterval_int = txt_PwdChangeInterval.getInt();
			if(pwdchangeinterval_int < 0 && pwdchangeinterval_int != PWDCHANGEINTERVAL_UNRESTRICTED)
			{
				//パスワード更新間隔（日）には0以上の数値を入力してください。
				message.setMsgResourceKey("6403035"); 
				return;
			}
			String pwdchangeinterval = Integer.toString(pwdchangeinterval_int);
			
			//パスワード有効期限
			String pwdexpires = null;
			//有効期限が無期限か判別
			if(pwdchangeinterval.equals(Integer.toString(PWDCHANGEINTERVAL_UNRESTRICTED)))
			{
			    //パスワード有効期限にnullをセット
			    pwdexpires = null;
			}
			else if(!pwdchangeinterval.equals(""))
			{
			    //パスワード更新間隔の値をViewStateから取得
			    String tmp_PwdChangeInterval = this.getViewState().getString("PwdChangeInterval");
			    
			    //パスワード更新間隔がNULL以外の時処理を行う
			    if(tmp_PwdChangeInterval!=null)
			    {
				    //パスワード更新間隔の値を変更しているか判定
				    if(tmp_PwdChangeInterval.equals(Integer.toString(txt_PwdChangeInterval.getInt())))
				    {
				        //パスワード有効期限をViewStateから取得し、セットする
				        pwdexpires = this.getViewState().getString("PwdExpires");    
				    }
				    else
				    {
				        //現在時刻の取得
				        Calendar cal = Calendar.getInstance();
				        DateFormat dfm = DateFormat.getDateInstance();
				        Date dt = cal.getTime();

				        //PCのシステム日付とパスワード更新間隔を加算
				        cal.setTime(dt);
				        cal.add(Calendar.DATE,Integer.parseInt(pwdchangeinterval));
				        
				        pwdexpires = dfm.format(cal.getTime()); 
				    }    			        
			    }
			}			
			
			//ロールIDをセット
			String roleid = txt_RoleId.getText();			
			//同一ユーザログイン可能数
			int sameuserloginmax_int = txt_SameUserLoginMax.getInt();
			if(sameuserloginmax_int < 1)
			{
				//同一ユーザログイン可能数には1以上の数値を入力してください。
				message.setMsgResourceKey("6403034"); 
				return;
			}
			String sameuserloginmax = Integer.toString(sameuserloginmax_int);
			    
			//認証ミス猶予回数をセット
			int failedloginattempts_int = 0;
			if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_UNRESTRICTED))) failedloginattempts_int = FAILEDLOGINATTEMPTS_UNRESTRICTED;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NOCOUNT))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NOCOUNT;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER1))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER1;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER2))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER2;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER3))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER3;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER4))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER4;
			else if(pul_FailedLoginAttempts.getSelectedValue().equals(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER5))) failedloginattempts_int = FAILEDLOGINATTEMPTS_NUMBER5;
			String failedloginattempts = Integer.toString(failedloginattempts_int);
			
			//ユーザ名をセット
			String username = txt_UserName.getText();
			//所属をセット
			String belonging = txt_Belonging.getText();
			
			//生年月日をセット
			String birthdate = null;
			if(txt_BirthDate.getText().equals(""))
			{
			    birthdate = null;   
			}
			else
			{
			    birthdate = txt_BirthDate.getText();

			    //数値チェック
			    if(!ToolValidator.isString(birthdate, "0123456789"))
			    {
					//日付として有効ではありません。
					message.setMsgResourceKey("6403039"); 
			        return;
			    }
			    //範囲チェック
			    if(birthdate.length() != 8)
			    {
					//日付として有効ではありません。
					message.setMsgResourceKey("6403039"); 
			        return;
			    }
			    
			    //年
			    int birthdate_y = Integer.parseInt(birthdate.substring(0,4));
			    //月
			    int birthdate_m = Integer.parseInt(birthdate.substring(4,6)) - 1;
			    //日
			    int birthdate_d = Integer.parseInt(birthdate.substring(6,8));
			    
			    //年チェック
			    if(birthdate_y < 1900 || birthdate_y > 2100)
			    {
					//日付として有効ではありません。
					message.setMsgResourceKey("6403039"); 
			        return;
			    }		    
			    //月チェック
			    if(birthdate_m < 0 || birthdate_m > 11)
			    {
					//日付として有効ではありません。
					message.setMsgResourceKey("6403039"); 
			        return;
			    }
			    //日チェック
			    if(birthdate_d < 1 || birthdate_d > 31)
			    {
					//日付として有効ではありません。
					message.setMsgResourceKey("6403039"); 
			        return;
			    }
			    
		        Calendar cal = Calendar.getInstance();
		        cal.set(birthdate_y, birthdate_m, birthdate_d);
		        Date dt = cal.getTime();
		        
		        DateFormat dfm = DateFormat.getDateInstance();
		        birthdate = dfm.format(dt);
			    
			}
			
			//性別をセット
			int sex_int = 0;
			if(pul_Sex.getSelectedValue().equals(Integer.toString(SEX_UNINPUT))) sex_int = SEX_UNINPUT;
			else if(pul_Sex.getSelectedValue().equals(Integer.toString(SEX_MALE))) sex_int = SEX_MALE;
			else if(pul_Sex.getSelectedValue().equals(Integer.toString(SEX_FEMALE))) sex_int = SEX_FEMALE;
			String sex = Integer.toString(sex_int);
			//備考をセット
			String note = txt_Note.getText();
			
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
				//パスワード入力チェック(必須入力項目)
				txt_Password.validate();
				//パスワード更新間隔入力チェック(必須入力項目)
				txt_PwdChangeInterval.validate();
				//ロールID入力チェック(必須入力項目)
				txt_RoleId.validate();
				//同一ユーザログイン可能数入力チェック(必須入力項目)
				txt_SameUserLoginMax.validate();
				//認証ミス猶予回数入力チェック(必須入力項目)
				pul_FailedLoginAttempts.validate();	
				
				//入力チェック
				txt_UserName.validate(false);
				txt_Belonging.validate(false);
				txt_BirthDate.validate(false);
				txt_Note.validate(false);
				
				//LOGINUSER
				handle.create("_insert-5100", new String[]{userid, 
				        								  password,
				        								  pwdexpires,
				        								  pwdchangeinterval,
				        								  roleid,
				        								  sameuserloginmax,
				        								  failedloginattempts},conn);
				//USERATTRIBUTE	
				handle.create("_insert-5101", new String[]{userid, 
						  								   username,
						  								   belonging,
						  								   birthdate,
						  								   sex,
						  								   note},conn);
				
				//6001003=登録しました。
				comp_msg = "6001003";
			}
			//修正時
			else if(proc.equals(MODIFY))
			{
			    //ユーザIDをチェック
				if(txt_R_UserId.getText().equals(AbstractAuthentication.ANONYMOUS_USER))
				{
				    //パスワード入力チェック
					txt_Password.validate(false);				    
				}
				else
				{
				    //パスワード入力チェック(必須入力項目)
					txt_Password.validate();				    
				}
				
				//パスワード更新間隔入力チェック(必須入力項目)
				txt_PwdChangeInterval.validate();
				//ロールID入力チェック(必須入力項目)
				txt_RoleId.validate();
				//同一ユーザログイン可能数入力チェック(必須入力項目)
				txt_SameUserLoginMax.validate();
				//認証ミス猶予回数入力チェック(必須入力項目)
				pul_FailedLoginAttempts.validate();
				
				//入力チェック
				txt_UserName.validate(false);
				txt_Belonging.validate(false);
				txt_BirthDate.validate(false);
				txt_Note.validate(false);
								
				//LOGINUSER
				handle.modify("_update-5100", new String[]{userid, 
						  								  password,
						  								  pwdexpires,
						  								  pwdchangeinterval,
						  								  roleid,
						  								  sameuserloginmax,
						  								  failedloginattempts,
						  								  userid},conn);
				//USERATTRIBUTE
				handle.modify("_update-5101", new String[]{userid, 
				        								  username,
				        								  belonging,
				        								  birthdate,
				        								  sex,
				        								  note,
				        								  userid},conn);
				//6001004=修正しました。
				comp_msg = "6001004";
			}
			//削除時
			else if(proc.equals(DELETE))
			{
			    //LOGINUSER
				handle.drop("_delete-5100", new String[]{userid}, conn);
				//USERATTRIBUTE
				handle.drop("_delete-5101", new String[]{userid}, conn);
				
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
	    //ユーザIDテキストボックスをクリア
		txt_R_UserId.setText("");
		
		//パスワードテキストボックスをクリア
		txt_Password.setText("");
		//パスワードテキストボックスを読み取り専用にセット
		txt_Password.setReadOnly(true);

		//パスワード更新間隔テキストボックスをクリア
		txt_PwdChangeInterval.setText("");
		//パスワード更新間隔テキストボックスを読み取り専用にセット
		txt_PwdChangeInterval.setReadOnly(true);
		
	    //ロールIDテキストボックスをクリア
		txt_RoleId.setText("");
		//ロールIDテキストボックスを読み取り専用にセット
		txt_RoleId.setReadOnly(true);
		
	    //同一ユーザログイン可能数テキストボックスをクリア
		txt_SameUserLoginMax.setText("");
		//同一ユーザログイン可能数テキストボックスを読み取り専用にセット
		txt_SameUserLoginMax.setReadOnly(true);
				
		//認証ミス猶予回数プルダウンを無効にセット
		pul_FailedLoginAttempts.setEnabled(false);
		pul_FailedLoginAttempts.setSelectedIndex(0);
		
	    //ユーザ名テキストボックスをクリア
		txt_UserName.setText("");
		//ユーザ名テキストボックスを読み取り専用にセット
		txt_UserName.setReadOnly(true);
		
	    //所属テキストボックスをクリア
		txt_Belonging.setText("");
		//所属テキストボックスを読み取り専用にセット
		txt_Belonging.setReadOnly(true);
		
	    //生年月日テキストボックスをクリア
		txt_BirthDate.setText("");
		//生年月日テキストボックスを読み取り専用にセット
		txt_BirthDate.setReadOnly(true);

		//性別プルダウンを無効にセット
		pul_Sex.setEnabled(false);
		pul_Sex.setSelectedIndex(0);

		//備考テキストボックスをクリア
		txt_Note.setText("");
		//備考テキストボックスを読み取り専用にセット
		txt_Note.setReadOnly(true);

		//ロールID検索ボタンを無効にセット
		btn_P_Search2.setEnabled(false);
		
		//有効期限を無期限にするチェックボックスを無効にセット
		chk_PwdChangeInterval.setEnabled(false);
		chk_PwdChangeInterval.setChecked(false);
		
		//パスワード有効期限テキストボックスをクリア
		txt_PwdExpires.setText("");
	}
	
	
	/** 
	 * 有効期限を無期限にするチェックボックスの処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PwdChangeInterval_Change(ActionEvent e) throws Exception
	{
	    
	    if(txt_PwdChangeInterval.getReadOnly() == true)
	    {
 		    //パスワード更新間隔テキストボックスを有効にセット
			txt_PwdChangeInterval.setReadOnly(false);
			//フォーカスをパスワード更新間隔テキストボックスにセット
			setFocus(txt_PwdChangeInterval);
			txt_PwdChangeInterval.setText("");
			
	    }
	    else
	    {
		   //パスワード更新間隔テキストボックスを読み取り専用にセット
		   txt_PwdChangeInterval.setReadOnly(true);	
		   txt_PwdChangeInterval.setText(Integer.toString(PWDCHANGEINTERVAL_UNRESTRICTED));
	    }				
	}
	
	
	/** 
	 * すでに同一のユーザIDが登録済みかを確認します。
	 * @param key  TerminalNumber
	 * @param conn Connection
	 * @throws Exception 
	 */
	private boolean isDefined(String key, Connection conn) throws Exception
	{
		BaseHandler handle = new BaseHandler();
		//すでに同一のユーザIDが存在する場合
		if(handle.count("_select-5102",new String[]{key}, conn) > 0)
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
		List ret = handle.find("_select-5103", new String[]{txt_UserId.getText()}, conn);
			
		//登録済みのユーザIDがある場合
		if(ret != null && ret.size() != 0)
		{
			ResultMap map = (ResultMap)ret.get(0);
			//LoginUser表
			//パスワード
			String password = map.getString(LOGINUSER_PASSWORD);
			//パスワード有効期限
			Date pwdexpires = map.getDate(LOGINUSER_PWDEXPIRES);
			//パスワード更新間隔
			int pwdchangeinterval= map.getInt(LOGINUSER_PWDCHANGEINTERVAL);
			//ロールID
			String roleid = map.getString(LOGINUSER_ROLEID);
			//同一ユーザログイン可能数
			int sameuserloginmax = map.getInt(LOGINUSER_SAMEUSERLOGINMAX);
			//認証ミス猶予回数
			int failedloginattempts = map.getInt(LOGINUSER_FAILEDLOGINATTEMPTS);
				
			//UserAttribute表
			//ユーザ名
			String username = map.getString(USERATTRIBUTE_USERNAME);			
			//所属
			String belonging = map.getString(USERATTRIBUTE_BELONGING);
			//生年月日
			Date birthdate = map.getDate(USERATTRIBUTE_BIRTHDATE);
	        DateFormat dfm = DateFormat.getDateInstance();
			//文字列変換
	        String birthdate_str = dfm.format(birthdate);
			
			//性別
			int sex = map.getInt(USERATTRIBUTE_SEX);
			//備考
			String note = map.getString(USERATTRIBUTE_NOTE);
			
			//各コントロールへのセット
			//ユーザIDをセット
			txt_R_UserId.setText(txt_UserId.getText());
			//パスワードをセット
			txt_Password.setText(password);
			//パスワード更新間隔をセット
			txt_PwdChangeInterval.setText(Integer.toString(pwdchangeinterval));
			//ロールIDをセット
			txt_RoleId.setText(roleid);
			//同一ユーザログイン可能数をセット
			txt_SameUserLoginMax.setText(Integer.toString(sameuserloginmax));
			
			//認証ミス猶予回数をセット
			if(failedloginattempts == FAILEDLOGINATTEMPTS_UNRESTRICTED) pul_FailedLoginAttempts.setSelectedIndex(0);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NOCOUNT) pul_FailedLoginAttempts.setSelectedIndex(1);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER1) pul_FailedLoginAttempts.setSelectedIndex(2);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER2) pul_FailedLoginAttempts.setSelectedIndex(3);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER3) pul_FailedLoginAttempts.setSelectedIndex(4);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER4) pul_FailedLoginAttempts.setSelectedIndex(5);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER5) pul_FailedLoginAttempts.setSelectedIndex(6);		    
			
			//ユーザ名をセット
			txt_UserName.setText(username);
			//所属をセット
			txt_Belonging.setText(belonging);
			
			//生年月日をセット
			//「/」で分割
			StringTokenizer st = new StringTokenizer(birthdate_str, "/");
		    String birthdate_str2="";
			while (st.hasMoreTokens())
		    {
		        birthdate_str2 = birthdate_str2 + st.nextToken();
		    }
			txt_BirthDate.setText(birthdate_str2);
			
			//性別をセット
			if(sex == SEX_UNINPUT) pul_Sex.setSelectedIndex(0);
			else if(sex == SEX_MALE) pul_Sex.setSelectedIndex(1);
			else if(sex == SEX_FEMALE) pul_Sex.setSelectedIndex(2);
			//備考をセット
			txt_Note.setText(note);
			
		    //パスワード更新間隔をViewStateに保存
		    this.getViewState().setString("PwdChangeInterval",txt_PwdChangeInterval.getText());
		    
		    //パスワード有効期限がNULLか判定
		    if(pwdexpires != null)
		    {
			    //パスワード有効期限をViewStateに保存
			    this.getViewState().setString("PwdExpires",pwdexpires.toString());
				//パスワード有効期限をテキストボックスにセット
				txt_PwdExpires.setDate(pwdexpires);
		    }			
		}			
	}

}
//end of class
