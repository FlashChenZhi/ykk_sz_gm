// $Id: AsStoringStatusBusiness.java,v 1.2 2006/10/26 08:09:57 suresh Exp $

//#CM41694
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.storingstatus;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.AsStoringStatusSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.PulldownHelper;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM41695
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * This is ASRS storage status screen class<BR>
 * set the input contents from the screen to a parameter
 * inquire ASRS storage status using that parameter in a scheduler<BR>
 * The schedule result is output to the preset area<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.display button click process(<CODE>btn_Query_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  set the contents of input area to a parameter and schedule inquires ASRS storage status using that<BR>
 *  The schedule result is output to the preset area<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code *<BR>
 * 		password *<BR>
 * 		warehouse *<BR>
 * 		location no. *<BR>
 * 		terminal no.<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:09:57 $
 * @author  $Author: suresh $
 */
public class AsStoringStatusBusiness extends AsStoringStatus implements WMSConstants
{
	//#CM41696
	// Class fields --------------------------------------------------

	//#CM41697
	// Class variables -----------------------------------------------

	//#CM41698
	// Class method --------------------------------------------------

	//#CM41699
	// Constructors --------------------------------------------------

	//#CM41700
	// Public methods ------------------------------------------------

	//#CM41701
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in warehouse<BR>
	 * 		2.initialize pulldown<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM41702
			// set the cursor in warehouse input
			setFocus(pul_WareHouse);
			
			//#CM41703
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM41704
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM41705
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM41706
			// pull down display data(RMNo)
			String[] rmno = pull.getAislePulldownData(conn, "", "", true ,true);
			
			//#CM41707
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_RMNo, rmno);
			
			pul_WareHouse.addChild(pul_RMNo);
			
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM41708
				// close connection
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
					
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM41709
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM41710
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM41711
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM41712
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM41713
	// Package methods -----------------------------------------------

	//#CM41714
	// Protected methods ---------------------------------------------

	//#CM41715
	// Private methods -----------------------------------------------

	//#CM41716
	// Event handler methods -----------------------------------------
	//#CM41717
	/**
	 * called when menu button is clicked<BR>
	 * <BR>
	 * overview ::go back to menu screen<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM41718
		// go back to menu screen
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM41719
	/** 
	 * called if display button is clicked<BR>
	 * <BR>
	 * Abstract :set the input area info in parameter and inquire ASRS storage status<BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in warehouse<BR>
	 * 		2.start scheduler<BR>
	 * 		<DIR>
	 * 			[parameter] *mandatory items<BR>
	 * 			<DIR>
	 * 				warehouse *<BR>
	 * 				RM No.*<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * 		3.display the result in preset area<BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			[list cell line no. list]
	 * 			<DIR>
	 * 				1.RM No. <BR>
	 * 				2.load size <BR>
	 * 				3.result location <BR>
	 * 				4.empty location <BR>
	 * 				5.Empty Palette <BR>
	 * 				6.Error location <BR>
	 * 				7.restricted location <BR>
	 * 				8.in accessible location <BR>
	 * 				9.total shelf qty <BR>
	 * 				10.storage rate <BR>
	 *	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
		//#CM41720
		// set cursor in warehouse
		setFocus(pul_WareHouse);

		Connection conn = null;

		try
		{
			if (StringUtil.isBlank(pul_RMNo.getSelectedValue()))
			{
				return;
			}
			
			//#CM41721
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM41722
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();
			//#CM41723
			// warehouse
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			//#CM41724
			// RMNo
			param.setStationNo(pul_RMNo.getSelectedValue());
			
			//#CM41725
			// start schedule
			WmsScheduler schedule = new AsStoringStatusSCH();
			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);
			//#CM41726
			// display parameter
			AsScheduleParameter dispData = null;
			
			//#CM41727
			// if there is error or there is no display data, close the process		
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM41728
			//eliminate all lines
			lst_AsStoringStatus.clearRow();
			
			for(int i = 0; i < viewParam.length; i++)
			{
				dispData = viewParam[i];

				//#CM41729
				//add row
				//#CM41730
				//fetch last row
				int count = lst_AsStoringStatus.getMaxRows();
				lst_AsStoringStatus.setCurrentRow(count);
				lst_AsStoringStatus.addRow();
				lst_AsStoringStatus.setValue(1, dispData.getRackmasterNo());
				lst_AsStoringStatus.setValue(2, dispData.getHardZone());
				lst_AsStoringStatus.setValue(3, Formatter.getNumFormat(dispData.getRealLocationCount()));
				lst_AsStoringStatus.setValue(4, Formatter.getNumFormat(dispData.getVacantLocationCount()));
				lst_AsStoringStatus.setValue(5, Formatter.getNumFormat(dispData.getEmptyPBLocationCount()));
				lst_AsStoringStatus.setValue(6, Formatter.getNumFormat(dispData.getAbnormalLocationCount()));
				lst_AsStoringStatus.setValue(7, Formatter.getNumFormat(dispData.getProhibitionLocationCount()));

				lst_AsStoringStatus.setValue(8, Formatter.getNumFormat(dispData.getNotAccessLocationCount()));
				
				lst_AsStoringStatus.setValue(9, Formatter.getNumFormat(dispData.getTotalLocationCount()));
				lst_AsStoringStatus.setValue(10, dispData.getLocationRate());			
			}

			if (!StringUtil.isBlank(schedule.getMessage()))
			{
				//#CM41731
				// display message
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM41732
				// close the connection object
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}


}
//#CM41733
//end of class
