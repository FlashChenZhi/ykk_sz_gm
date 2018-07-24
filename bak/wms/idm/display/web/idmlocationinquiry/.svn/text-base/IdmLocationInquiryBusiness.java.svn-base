// $Id: IdmLocationInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:09 mori Exp $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.idm.display.web.idmlocationinquiry;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;
import jp.co.daifuku.wms.idm.schedule.IdmLocationInquirySCH;
import jp.co.daifuku.wms.idm.schedule.IdmLocationLevelView;

/**
 * 棚状態問合せ(移動ラック)画面のクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/07</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:09 $
 * @author  $Author: mori $
 */
public class IdmLocationInquiryBusiness extends IdmLocationInquiry  implements WMSConstants
{
	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * <DIR>
	 *  概要:ダイアログを表示します。 <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			// パラメータを取得
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			// ViewStateへ保持する
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			// 画面名称をセットする
			lbl_SettingName.setResourceKey(title);
		}
		setFocus(pul_Bank);
	}

	/**
	 * 画面が読み込まれたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアを初期設定します。<BR>
	 *    2.スケジュールを開始します。 <BR>
	 *    3.プルダウンデータをプルダウンへセットします。<BR>
	 * </DIR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * バンク（プルダウン）[検索した値] <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{           
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			IdmLocationInquirySCH schedule = new IdmLocationInquirySCH();
			IdmControlParameter parameter = (IdmControlParameter)schedule.initFind(conn,null);
			String[] temp = 	parameter.getBankNoArray();
            
			// プルダウン表示データ（バンクNo）			
			String[] bank = new String[temp.length];
			
			for(int i = 0; i < temp.length; i++)
			{
				System.out.println("SET BANK NUMBER : "+temp[i]);
				bank[i] = convertlinkPullData(temp[i], i);
				System.out.println("SET BANK NUMBER .........: "+bank[i]);
			}

			// プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_Bank, bank);

		}
		catch (Exception ex)
		{
			System.out.println("PAGE LOAD");
			ex.printStackTrace();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		} 
	}   

	/**
	 * プルダウンに表示する文字列を設定します。<BR>
	 * <BR>
	 * @param pullData プルダウンにセットする値
	 * @param count プルダウンにセットする値の数
	 * @return プルダウンに表示する文字列
	 */
	private String convertlinkPullData(String pullData, int count)
	{
		StringBuffer sb = new StringBuffer();
        
		if(count == 1)
		{
			sb.append(pullData).append(",").append("").append(",").append(DisplayText.getText("LBL-W0589") + pullData).append(",").append("1");
		}
		else 
		{
			sb.append(pullData).append(",").append("").append(",").append(DisplayText.getText("LBL-W0589") + pullData).append(",").append("0");
		}

		return sb.toString();
	}

	// Event handler methods -----------------------------------------

	/**
	 * メニューボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:メニュー画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/** 
	 * 表示ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:プルダウンで選択されたバンクを条件に、ロケーションマスタ情報を検索し表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.スケジュールを開始します。<BR>
	 *   2.移動ラック棚情報を表示します。<BR>
	 *   3.入力エリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{       
		Connection conn = null;
		try 
		{                
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			IdmControlParameter parameter = new IdmControlParameter();
			parameter.setBankNo(pul_Bank.getSelectedValue());
			
			IdmLocationInquirySCH locationSchedule = new IdmLocationInquirySCH();
			IdmLocationLevelView [] levelViews =  locationSchedule.getLevelViewData(conn,parameter);
            
			// セッションにセット
			this.getSession().setAttribute("result", levelViews);

			// 6001013 = 表示しました。
			message.setMsgResourceKey("6001013");
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally 
		{
			try 
			{
				if (conn!=null) conn.close();
			} 
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
        
		this.getSession().setAttribute("wSelectedBank", pul_Bank.getSelectedValue());
	}
    
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Bank_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Bank_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Bank_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}
    
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class
