// $Id: BackUpBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.backup;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.tools.ToolConstants;
import jp.co.daifuku.tools.util.ToolParam;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.BackupUtils;

/** <jp>
 * バックアップ作成の画面クラスです。
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
public class BackUpBusiness extends BackUp implements ToolConstants, TableColumns
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
	    lbl_SettingName.setResourceKey("TLE-T0020");
		//ヘルプファイルへのパスをセットする
	    btn_Help.setUrl(BusinessClassHelper.getHelpPath("BackUp", this.getHttpRequest()) );
  
		setFocus(txt_FileBrows);
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
	    
	    txt_FileBrows.setText(ToolParam.getProperty("Restore.Path"));
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
	 * 開始ボタンが押下されたときに呼ばれます
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
		    //保存先フォルダのパスを入力チェック
		    txt_FileBrows.validate();
		    
			String filePath = txt_FileBrows.getText();

			if(!filePath.endsWith("\\") || !filePath.endsWith("/") )
			{
			    filePath = filePath + File.separator;
			}
					
			//Get table names for backup.
			List tabeles = ToolParam.getList(ToolParam.getProperty("Backup.Tables"));
			//コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);								
			
			Iterator itr = tabeles.iterator();
			while(itr.hasNext())
			{
				String tableName = (String)itr.next();
				BackupUtils.mkFile(filePath, tableName, conn);
			}
			//メッセージをセット
			message.setMsgResourceKey("6001014");
		}
		catch(FileNotFoundException fnfe)
		{
		    //指定されたパスが見つかりません。
			message.setMsgResourceKey("6403032"); 
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
