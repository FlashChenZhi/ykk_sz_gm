// $Id: RoleBusiness.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.role;

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
import jp.co.daifuku.tools.display.web.listbox.rolelist.RoleListBusiness;
import jp.co.daifuku.ui.web.BusinessClassHelper;


/** <jp>
 * ロール設定の画面クラスです。
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
public class RoleBusiness extends Role implements ToolConstants, TableColumns
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
	    lbl_SettingName.setResourceKey("TLE-T0011");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("Role", this.getHttpRequest()) );
    
		setFocus(txt_RoleId);
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

	    //ロール名テキストボックスを読み取り専用にセット
	    txt_RoleName.setReadOnly(true);
	    //認証ミス猶予回数プルダウンを無効にセット
	    pul_FailedLoginAttempts.setEnabled(false);
		    
		//認証ミス猶予回数プルダウンに値をセット
	    //value、resourceKey、text、selected
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_UNRESTRICTED),"PUL-T0001","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NOCOUNT),"PUL-T0012","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER1),"PUL-T0003","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER2),"PUL-T0004","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER3),"PUL-T0005","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER4),"PUL-T0006","",false);
	    pul_FailedLoginAttempts.addItem(Integer.toString(FAILEDLOGINATTEMPTS_NUMBER5),"PUL-T0007","",false);	

	}
	

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
	 * @param e ActionEvent
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
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
	 * ロールID検索ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_Search_Click(ActionEvent e) throws Exception
	{
	    //ロール一覧画面へ検索条件をセットする
	    ForwardParameters param = new ForwardParameters();
	    param.setParameter(RoleListBusiness.ROLEID_KEY, txt_RoleId.getText());
	    
	    //処理中画面->結果画面
	    redirect("/listbox/rolelist/RoleList.do", param, "/Progress.do");
	}


	/** 
	 * 登録ボタンが押下された時の処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
	    setFocus(txt_RoleName);
		Connection conn = null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);

			//ロールIDテキストボックスを入力チェック
			txt_RoleId.validate(true);
			
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			
			//すでに同一のロールIDが登録済みかを確認
			if(isDefined(txt_RoleId.getText(), conn))
			{
				//すでに同一のロールIDが登録されています。
				message.setMsgResourceKey("6403005"); 
				return;
			}
			
			//ロール名テキストボックスを有効にする
			txt_RoleName.setReadOnly(false);
			//認証ミス猶予回数プルダウンを有効にする
			pul_FailedLoginAttempts.setEnabled(true);
						
			//ロールIDをテキストボックスにセット
			txt_R_RoleId.setText(txt_RoleId.getText());
			
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
	    setFocus(txt_RoleName);
		Connection conn =null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);
			
			//ロールIDテキストボックスを入力チェック
			txt_RoleId.validate(true);

			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		

			//すでに同一のロールIDが登録済みかを確認 
			if(!isDefined(txt_RoleId.getText(), conn))
			{
				//指定されたロールIDは登録されていません。
				message.setMsgResourceKey("6403006"); 
				return;
			}
			
			//ロール名テキストボックスを有効にする
			txt_RoleName.setReadOnly(false);
			//認証ミス猶予回数プルダウンを有効にする
			pul_FailedLoginAttempts.setEnabled(true);
						
			//ロールIDをテキストボックスにセット
			txt_R_RoleId.setText(txt_RoleId.getText());

			//データベースのデータをセットする
			mapping(conn);
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
			
			//ロールIDテキストボックスを入力チェック
			txt_RoleId.validate(true);
			
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			
			//すでに同一のロールIDが登録済みかを確認 
			if(!isDefined(txt_RoleId.getText(), conn))
			{
				//指定されたロールIDは登録されていません。
				message.setMsgResourceKey("6403006"); 
				return;
			}
				
			//データベースのデータをセットする
			mapping(conn);
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
	 * 設定ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Click(ActionEvent e) throws Exception
	{
		Connection conn =null;		
		try
		{			
		    //ロールID入力チェック(必須入力項目)
			txt_R_RoleId.validate();
		
			String roleid = txt_R_RoleId.getText();				
			String rolename = txt_RoleName.getText();			
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
				//ロール名入力チェック(必須入力項目)
				txt_RoleName.validate();
				//認証ミス猶予回数入力チェック(必須入力項目)
				pul_FailedLoginAttempts.validate();
				
				handle.create("_insert-5500", new String[]{roleid, rolename, failedloginattempts},conn);
				//6001003=登録しました。
				comp_msg = "6001003";
			}
			//修正時
			else if(proc.equals(MODIFY))
			{
				//ロール名入力チェック(必須入力項目)
				txt_RoleName.validate();
				//認証ミス猶予回数入力チェック(必須入力項目)
				pul_FailedLoginAttempts.validate();
				
				handle.modify("_update-5500", new String[]{rolename, failedloginattempts, roleid},conn);
				//6001004=修正しました。
				comp_msg = "6001004";
			}
			//削除時
			else if(proc.equals(DELETE))
			{
			    //Role表の削除
				handle.drop("_delete-5500", new String[]{roleid}, conn);

				//RoleMap表の削除
				handle.drop("_delete-5600", new String[]{roleid}, conn);
				
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
	    //ロールIDテキストボックスをクリア
		txt_R_RoleId.setText("");
	    //ロール名テキストボックスをクリア
		txt_RoleName.setText("");
		//ロール名テキストボックスを読み取り専用にセット
		txt_RoleName.setReadOnly(true);
		//認証ミス猶予回数プルダウンを無効にセット
		pul_FailedLoginAttempts.setEnabled(false);
		pul_FailedLoginAttempts.setSelectedIndex(0);
		
	}

	/** 
	 * すでに同一のロールIDが登録済みかを確認します。
	 * @param key  TerminalNumber
	 * @param conn Connection
	 * @throws Exception 
	 */
	private boolean isDefined(String key, Connection conn) throws Exception
	{
		BaseHandler handle = new BaseHandler();
		//すでに同一のロールIDが存在する場合
		if(handle.count("_select-5506",new String[]{key}, conn) > 0)
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
		List ret = handle.find("_select-5507", new String[]{txt_RoleId.getText()}, conn);
			
		//登録済みのロールIDがある場合
		if(ret != null && ret.size() != 0)
		{
			ResultMap map = (ResultMap)ret.get(0);
			String rolename = map.getString(ROLE_ROLENAME);
			int failedloginattempts = map.getInt(ROLE_FAILEDLOGINATTEMPTS);
				
			txt_R_RoleId.setText(txt_RoleId.getText());				
			txt_RoleName.setText(rolename);
			
			if(failedloginattempts == FAILEDLOGINATTEMPTS_UNRESTRICTED) pul_FailedLoginAttempts.setSelectedIndex(0);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NOCOUNT) pul_FailedLoginAttempts.setSelectedIndex(1);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER1) pul_FailedLoginAttempts.setSelectedIndex(2);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER2) pul_FailedLoginAttempts.setSelectedIndex(3);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER3) pul_FailedLoginAttempts.setSelectedIndex(4);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER4) pul_FailedLoginAttempts.setSelectedIndex(5);
			else if(failedloginattempts == FAILEDLOGINATTEMPTS_NUMBER5) pul_FailedLoginAttempts.setSelectedIndex(6);

		}			
	
	}
}
//end of class
