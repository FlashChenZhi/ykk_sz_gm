// $Id: TerminalBusiness.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.terminal;
import java.sql.Connection;
import java.util.List;

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
import jp.co.daifuku.tools.display.web.listbox.terminallist.TerminalListBusiness;
import jp.co.daifuku.ui.web.BusinessClassHelper;

/** <jp>
 * 端末設定の画面クラスです。
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
public class TerminalBusiness extends Terminal implements ToolConstants, TableColumns
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
        lbl_SettingName.setResourceKey("TLE-T0016");
		//ヘルプファイルへのパスをセットする
        btn_Help.setUrl(BusinessClassHelper.getHelpPath("Terminal", this.getHttpRequest()) );
	    
	    setFocus(txt_TerminalNumber);
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
	    	    
    	//端末名テキストボックスを読み取り専用にセット
		txt_TerminalName.setReadOnly(true);
    	//端末IPアドレステキストボックスを読み取り専用にセット
		txt_IpAddress.setReadOnly(true);
    	//ロールIDテキストボックスを読み取り専用にセット
		txt_RoleId.setReadOnly(true);
    	//ロールID検索ボタンを読み取り専用にセット
		btn_P_Search2.setEnabled(false);
		//プリンタ名テキストボックスを読み取り専用にセット
		txt_PrinterName.setReadOnly(true);
		
	}
	

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
	 * @param e ActionEvent
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//端末№を取得
		String terminalkey = param.getParameter(TerminalListBusiness.TERMINALNUMBER_KEY);
		//空ではないときに値をセットする
		if(!Validator.isEmptyCheck(terminalkey))
		{
			txt_TerminalNumber.setText(terminalkey);
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
	 * 端末№検索ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_P_Search_Click(ActionEvent e) throws Exception
	{
	    //端末一覧画面へ検索条件をセットする
	    ForwardParameters param = new ForwardParameters();
	    param.setParameter(TerminalListBusiness.TERMINALNUMBER_KEY, txt_TerminalNumber.getText());
	    
		//ViewStateよりメニュータイプを取得
	    String menutype = this.getViewState().getString("MENUTYPE"); 
	    param.setParameter(TerminalListBusiness.MENUTYPE_KEY, menutype);
	    
	    //処理中画面->結果画面
	    redirect("/listbox/terminallist/TerminalList.do", param, "/Progress.do");
	}

	/** 
	 * 登録ボタンが押下された時の処理を実装します
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
	    setFocus(txt_TerminalName);
		Connection conn = null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);

			//端末№テキストボックスを入力チェック
			txt_TerminalNumber.validate(true);
			
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			
			//すでに同一の端末№が登録済みかを確認
			if(isDefined(txt_TerminalNumber.getText(), conn))
			{
				//すでに同一の端末№が登録されています。
				message.setMsgResourceKey("6403001"); 
				return;
			}
			
			//ロールID検索ボタンを有効にする
			btn_P_Search2.setEnabled(true);
			//端末名テキストボックスを有効にする
			txt_TerminalName.setReadOnly(false);
			//端末IPアドレステキストボックスを有効にする
			txt_IpAddress.setReadOnly(false);
			//ロールIDアドレステキストボックスを有効にする
			txt_RoleId.setReadOnly(false);
			//プリンタ名テキストボックスを有効にする
			txt_PrinterName.setReadOnly(false);
			
			//端末№をテキストボックスにセット
			txt_R_TerminalNumber.setText(txt_TerminalNumber.getText());
			
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
	    setFocus(txt_TerminalName);
		Connection conn =null;		
		try
		{			
			//クリア処理
			btn_Clear_Click(e);
			
			//端末№テキストボックスを入力チェック
			txt_TerminalNumber.validate(true);

			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		

			//すでに同一の端末№が登録済みかを確認 
			if(!isDefined(txt_TerminalNumber.getText(), conn))
			{
				//指定された端末№は登録されていません。
				message.setMsgResourceKey("6403002"); 
				return;
			}

			//データベースのデータをセットする
			mapping(conn);
			
			//ViewStateよりメニュータイプを取得
		    String menutype = this.getViewState().getString("MENUTYPE"); 
			if(txt_IpAddress.getText().equals(AbstractAuthentication.UNDEFINED_TERMINAL) && menutype.equals(MENU_CUSTOMER))
			{
				//指定された端末№はシステム定義の端末のため修正できません。
				message.setMsgResourceKey("6403012"); 
				//クリア処理
				btn_Clear_Click(e);
				return;
			}

			//ロールID検索ボタンを有効にする
			btn_P_Search2.setEnabled(true);
			//端末名テキストボックスを有効にする
			txt_TerminalName.setReadOnly(false);

			//IPアドレスをチェック			
			if(!txt_IpAddress.getText().equals(AbstractAuthentication.UNDEFINED_TERMINAL))
			{
				//端末IPアドレステキストボックスを有効にする
				txt_IpAddress.setReadOnly(false);			    
			}
			
			//ロールIDアドレステキストボックスを有効にする
			txt_RoleId.setReadOnly(false);
			//プリンタ名テキストボックスを有効にする
			txt_PrinterName.setReadOnly(false);

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
						
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);		
			//端末№テキストボックスを入力チェック
			txt_TerminalNumber.validate(true);			

			//すでに同一の端末№が登録済みかを確認 
			if(!isDefined(txt_TerminalNumber.getText(), conn))
			{
				//指定された端末№は登録されていません。
				message.setMsgResourceKey("6403002"); 
				return;
			}
				
			//データベースのデータをセットする
			mapping(conn);

			//IPアドレスをチェック
			if(txt_IpAddress.getText().equals(AbstractAuthentication.UNDEFINED_TERMINAL))
			{
				//指定された端末№はシステム定義の端末のため削除できません。
				message.setMsgResourceKey("6403011"); 
				//クリア処理
				btn_Clear_Click(e);
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
		    //端末№入力チェック(必須入力項目)
			txt_R_TerminalNumber.validate();
		
			String terminalnumber = txt_R_TerminalNumber.getText();				
			String terminalname = txt_TerminalName.getText();
			String ipaddress = txt_IpAddress.getText();
			String roleid = txt_RoleId.getText();
			String printername = txt_PrinterName.getText();
			
			
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
			    //ロールID入力チェック(必須入力項目)
				txt_RoleId.validate();
				//入力チェック
				txt_TerminalName.validate(false);
				txt_IpAddress.validate(false);
				txt_PrinterName.validate(false);
				//IPアドレスをチェック
				if(ipaddress.equals(AbstractAuthentication.UNDEFINED_TERMINAL))
				{
					//入力された端末IPアドレスはシステム定義で使用されているため登録できません。
					message.setMsgResourceKey("6403038"); 
					return;		 
				}
								
				handle.create("_insert-5400", new String[]{terminalnumber, terminalname, ipaddress, roleid, printername},conn);
				//6001003=登録しました。
				comp_msg = "6001003";
			}
			//修正時
			else if(proc.equals(MODIFY))
			{
			    //ロールID入力チェック(必須入力項目)
				txt_RoleId.validate();
				//入力チェック
				txt_TerminalName.validate(false);
				txt_IpAddress.validate(false);
				txt_PrinterName.validate(false);
				//IPアドレスをチェック
				if(ipaddress.equals(AbstractAuthentication.UNDEFINED_TERMINAL) && !txt_IpAddress.getReadOnly())
				{
					//入力された端末IPアドレスはシステム定義で使用されているため登録できません。
					message.setMsgResourceKey("6403038"); 
					return;		 
				}
								
				handle.modify("_update-5400", new String[]{terminalname, ipaddress, roleid, printername, terminalnumber},conn);
				//6001004=修正しました。
				comp_msg = "6001004";
			}
			//削除時
			else if(proc.equals(DELETE))
			{
				handle.drop("_delete-5400", new String[]{terminalnumber}, conn);
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
	    //端末№テキストボックスをクリア
		txt_R_TerminalNumber.setText("");
		//端末名テキストボックスをクリア
		txt_TerminalName.setText("");
		//端末IPアドレステキストボックスをクリア		
		txt_IpAddress.setText("");
		//ロールIDテキストボックスをクリア		
		txt_RoleId.setText("");
		//プリンタ名テキストボックスをクリア		
		txt_PrinterName.setText("");
		//ロールID検索ボタンを無効
		btn_P_Search2.setEnabled(false);
		//端末名テキストボックスを読み取り専用にセット
		txt_TerminalName.setReadOnly(true);
		//端末IPアドレステキストボックスを読み取り専用にセット
		txt_IpAddress.setReadOnly(true);
		//ロールIDテキストボックスを読み取り専用にセット
		txt_RoleId.setReadOnly(true);
		//プリンタ名テキストボックスを読み取り専用にセット
		txt_PrinterName.setReadOnly(true);
		
	}
	
	/** 
	 * すでに同一の端末№が登録済みかを確認します。
	 * @param key  TerminalNumber
	 * @param conn Connection
	 * @throws Exception 
	 */
	private boolean isDefined(String key, Connection conn) throws Exception
	{
		BaseHandler handle = new BaseHandler();
		//すでに同一の端末№が存在する場合
		if(handle.count("_select-5402",new String[]{key}, conn) > 0)
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
		List ret = handle.find("_select-5403", new String[]{txt_TerminalNumber.getText()}, conn);
			
		//登録済みの端末№がある場合
		if(ret != null && ret.size() != 0)
		{
			ResultMap map = (ResultMap)ret.get(0);
			String terminalname = map.getString(TERMINAL_TERMINALNAME);
			String ipaddress = map.getString(TERMINAL_IPADDRESS);
			String roleid = map.getString(TERMINAL_ROLEID);
			String printername = map.getString(TERMINAL_PRINTERNAME);		
				
			txt_R_TerminalNumber.setText(txt_TerminalNumber.getText());				
			txt_TerminalName.setText(terminalname);
			txt_IpAddress.setText(ipaddress);
			txt_RoleId.setText(roleid);
			txt_PrinterName.setText(printername);

		}			
	
	}

}
//end of class
