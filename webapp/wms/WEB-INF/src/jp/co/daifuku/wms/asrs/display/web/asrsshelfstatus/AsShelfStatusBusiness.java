// $Id: AsShelfStatusBusiness.java,v 1.2 2006/10/26 04:54:28 suresh Exp $

//#CM37289
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsshelfstatus;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.schedule.AsLocationLevelView;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.AsShelfStatusSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM37290
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * The class on ASRS location status inquiry screen. <BR>
 * Set the content of the screen in the parameter, and inquire schedule ASRS location status based on the parameter. <BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.display button click process(<CODE>btn_Setting_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  The content of the screen is set in the parameter, and the schedule inquires ASRS location status based on the parameter.<BR>
 *  Output it to the screen when the schedule is normally done. <BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		warehouse*<BR>
 * 		BankNo.*<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/07</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:54:28 $
 * @author  $Author: suresh $
 */
public class AsShelfStatusBusiness extends AsShelfStatus implements WMSConstants
{
	//#CM37291
	// Class fields --------------------------------------------------

	//#CM37292
	// Class variables -----------------------------------------------

	//#CM37293
	// Class method --------------------------------------------------

	//#CM37294
	// Constructors --------------------------------------------------

	//#CM37295
	// Public methods ------------------------------------------------

	//#CM37296
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize pulldown <BR>
	 * 		2.set the cursor in warehouse<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM37297
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM37298
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM37299
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		setFocus(pul_WareHouse);

		Connection conn = null;
		try
		{           
			//#CM37300
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM37301
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM37302
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			
			AsShelfStatusSCH schedule = new AsShelfStatusSCH();
			AsScheduleParameter parameter = (AsScheduleParameter)schedule.initFind(conn,null);
			String[] temp = 	parameter.getBankNoArray();
            
			//#CM37303
			//pull down display data(BankNo)
			String[] bank = new String[temp.length];

			for(int i = 0; i < temp.length; i++)
			{
				bank[i] = convertlinkPullData(temp[i], i);
			}

			//#CM37304
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);			
			PulldownHelper.setLinkedPullDown(pul_Bank, bank);

			//#CM37305
			// add child pull down
			pul_WareHouse.addChild(pul_Bank);

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
				//#CM37306
				// close connection
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		} 
	}

	//#CM37307
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}   

	//#CM37308
	/**
	 * Return the content displayed in the Pulldown menu of Bank by the character string. <BR>
	 * @param pullData Bank data
	 * @param count Number of array
	 * @return Bank Name
	 */
	private String convertlinkPullData(String pullData, int count)
	{
		StringBuffer sb = new StringBuffer();

		String wWhNumber = CollectionUtils.getString(0, pullData);        
		String wBank = CollectionUtils.getString(1, pullData);        

		if(count == 0)
		{
			sb.append(wBank).append(",").append(DisplayText.getText("LBL-W0589") + wBank).append(",").append(wWhNumber).append(",").append("0");
		}
		else 
		{
			sb.append(wBank).append(",").append(DisplayText.getText("LBL-W0589") + wBank).append(",").append(wWhNumber).append(",").append("0");
		}

		return sb.toString();
	}

	//#CM37309
	// Package methods -----------------------------------------------

	//#CM37310
	// Protected methods ---------------------------------------------

	//#CM37311
	// Private methods -----------------------------------------------

	//#CM37312
	// Event handler methods -----------------------------------------
	//#CM37313
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM37318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Warehouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Sokokbn_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Sokokbn_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37321
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Bank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37322
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Bank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37323
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Bank_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37324
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Query_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37325
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37326
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Query_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37329
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37330
	/** 
	 * It is called when Display button is pressed.<BR>
	 * <BR>
	 * Abstract :Display location status.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in warehouse<BR>
	 * 		2.start scheduler<BR>
	 * 		<DIR>
	 * 			[parameter]<BR>
	 * 			<DIR>
	 * 				warehouse<BR>
	 * 				BankNo.<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * 		3.Set Warehouse and Bank No. in the session. 
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try 
		{                
			if (StringUtil.isBlank(pul_Bank.getSelectedValue()))
			{
				return;
			}
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			AsScheduleParameter parameter = new AsScheduleParameter();
			parameter.setWareHouseNo(pul_WareHouse.getSelectedValue());
			parameter.setBankNo(pul_Bank.getSelectedValue());
            //#CM37331
            //set focus on warehouse (warehouse)pulldown
            setFocus(pul_WareHouse);
			AsShelfStatusSCH locationSchedule = new AsShelfStatusSCH();
			AsLocationLevelView[] levelViews =  locationSchedule.getLevelViewData(conn,parameter);
            
			//#CM37332
			// Set in the session. 
			this.getSession().setAttribute("result", levelViews);

			//#CM37333
			// 6001013 = Displayed.
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
        
		this.getSession().setAttribute("wSelectedWareHouseStationNumber", pul_WareHouse.getSelectedValue());
		this.getSession().setAttribute("wSelectedBank", pul_Bank.getSelectedValue());
	}


}
//#CM37334
//end of class
