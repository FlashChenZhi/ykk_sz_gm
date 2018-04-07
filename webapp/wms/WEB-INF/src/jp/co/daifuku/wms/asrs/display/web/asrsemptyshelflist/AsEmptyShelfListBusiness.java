// $Id: AsEmptyShelfListBusiness.java,v 1.2 2006/10/26 04:13:33 suresh Exp $

//#CM35219
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsemptyshelflist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.schedule.AsEmptyShelfListSCH;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM35220
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * The screen class which issues an empty cell list. <BR>
 * Hand over the parameter to the schedule which issues an empty cell list. <BR>
 * <BR>
 * <DIR>
 * 		1.Processing when Print button is pressed(<CODE>btn_Print_Click<CODE>) <BR>
 * 		<BR>
 * 		<DIR>
 * 			Set the input contents from the screen to a parameter
 * 			The schedule retrieves and prints data based on the parameter. <BR>
 * 			The schedule must return true when it succeeds in the print and return false when failing. <BR>
 * 			<BR>
 * 			[parameter] *mandatory input<BR>
 * 			<DIR>
 * 				warehouse *<BR>
 * 				RM No.*<BR>
 * 			</DIR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:13:33 $
 * @author  $Author: suresh $
 */
public class AsEmptyShelfListBusiness extends AsEmptyShelfList implements WMSConstants
{
	//#CM35221
	// Class fields --------------------------------------------------
	//#CM35222
	/**
	 * Dialog call origin:Print button
	 */	
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	
	//#CM35223
	/**
	 * Name maintained in ViewState of RM Pulldown
	 */
	protected static final String PULL_RM = "PULL_RM";
	//#CM35224
	// Class variables -----------------------------------------------

	//#CM35225
	// Class method --------------------------------------------------

	//#CM35226
	// Constructors --------------------------------------------------

	//#CM35227
	// Public methods ------------------------------------------------
	//#CM35228
	/** 
	 * It is called before each control event call.<BR>
	 * <BR>
	 * @param e ActionEvent It is a class that stores information on the event.
	 * @throws Exception It reports on all exceptions.  
	  */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM35229
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			
			//#CM35230
			//save to viewstate
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM35231
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}		
		
	}

	//#CM35232
	/** 
	 * This screen is initialized.<BR>
	 * <BR>
	 * outline:This screen is initialized.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.The pull-down menu is initialized. <BR>
	 * 		2.The cursor is set in the warehouse. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent It is a class that stores information on the event.
	 * @throws Exception It reports on all exceptions.  
	  */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{			
			Locale locale = this.getHttpRequest().getLocale();
			//#CM35233
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM35234
			//set terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo)this.getUserInfo());
			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());
	
			//#CM35235
			// pull down display data(Warehouse)
			String[] whno = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM35236
			// pull down display data(RM)
			String[] rm = pull.getAislePulldownData(conn, "", "", true ,true);

			//#CM35237
			//set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, whno);
			PulldownHelper.setLinkedPullDown(pul_RMNo, rm);
			//#CM35238
			//add child pull down
			pul_WareHouse.addChild(pul_RMNo);
			//#CM35239
			// set initial focus to warehouse
			setFocus(pul_WareHouse);
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM35240
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM35241
	/**
	 * This method is called when returning from the dialog button.<BR>
	 * Override <CODE>page_ConfirmBack</CODE> defined in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Abstract :Execute the processing of the correspondence when [Yes] is selected by the dialog. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in warehouse
	 * 	    2.Check from which dialog return. <BR>
	 *      3.Check whether the selected item from dialog is [Yes] or [No].<BR>
	 *      4.Begin scheduling when yes is selected. <BR>
	 *      5.Display the result of the schedule in the message area. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
		    
			//#CM35242
			// Check from which dialog return. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM35243
			// set initial focus to warehouse
			setFocus(pul_WareHouse);
			
			//#CM35244
			// True when pushed button is [True]
			//#CM35245
			// False when [No] is pressed
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM35246
			// End processing when [No] is pressed. 
			//#CM35247
			// The set of the message here is unnecessary. 
			if (!isExecute)
			{

				return;
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM35248
			// Turn off the flag because the operation of the dialog ended. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM35249
		// Start the print schedule. 
		Connection conn = null;
		try
		{
			//#CM35250
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM35251
			// set input value to parameter
			AsScheduleParameter[] param = new AsScheduleParameter[1];
			param[0] = createParameter();

			//#CM35252
			// start schedule
			WmsScheduler schedule = new AsEmptyShelfListSCH();
			schedule.startSCH(conn, param);
			
			//#CM35253
			// set message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
	
		}
		finally
		{
			try
			{
				//#CM35254
				// close the connection object
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}
		
	}
	//#CM35255
	// Package methods -----------------------------------------------

	//#CM35256
	// Protected methods ---------------------------------------------
	//#CM35257
	/**
	 * Generate the parameter which sets the input value of input area. <BR>
	 * @return parameter object containing the input value
	 */
	protected AsScheduleParameter createParameter()
	{
	    AsScheduleParameter param = new AsScheduleParameter();
		//#CM35258
		// warehouse
	    param.setWareHouseNo(pul_WareHouse.getSelectedValue());
	    
	    //#CM35259
	    // 2005/12/16 In the specification of the framework
	    //#CM35260
	    // Because the value of child Pulldown is not set when it returns from Confirm
	    //#CM35261
	    // The value cannot be acquired from Pulldown. 
	    //#CM35262
	    // Therefore, spend by maintaining, and using the value for ViewState. 
	    //#CM35263
	    // RM
	    if (!StringUtil.isBlank(pul_RMNo.getSelectedValue()))
	    {
		    param.setRackmasterNo(pul_RMNo.getSelectedValue());
		    this.getViewState().setString(PULL_RM, pul_RMNo.getSelectedValue());
	    }
	    else
	    {
		    param.setRackmasterNo(this.getViewState().getString(PULL_RM));
	    }
	    
		return param;
	}
	//#CM35264
	// Private methods -----------------------------------------------

	//#CM35265
	// Event handler methods -----------------------------------------
	//#CM35266
	/** 
	 * called when menu button is clicked<BR>
	 * <BR>
	 * Abstract :go back to menu screen <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
	    forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM35267
	/** 
	 * When the print button is pressed, it is called.<BR>
	 * <BR>
	 * outline:Set the input data details in the parameter and retrieve data count to be printed.<BR>
	 * A confirmation dialog box is displayed asking whether to print or not.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.The cursor is set in the warehouse.<BR>
	 * 		2.The number of print objects is checked.<BR>
	 * 		3-1.The confirmation dialog is displayed when there is data for the print.<BR>
	 * 		3-2.The message is displayed when there is no data for the print.<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	  */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM35268
			// set initial focus to warehouse
			setFocus(pul_WareHouse);
			//#CM35269
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM35270
			// Set the schedule parameter. 
			AsScheduleParameter param = createParameter();
			
			//#CM35271
			//start schedule
			WmsScheduler schedule = new AsEmptyShelfListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM35272
				// MSG-W0061={0}The matter corresponded. <BR> Do print?
				setConfirm("MSG-W0061" + wDelim + reportCount, true);
				//#CM35273
				// Memorize the dialog display by Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM35274
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
		}		
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM35275
				// close the connection object
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}	
	}


	//#CM35276
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35277
	/** 
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * and set the cursor in warehouse<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
	    pul_WareHouse.setSelectedIndex(0);
	    pul_RMNo.setSelectedIndex(0);
		//#CM35278
		// set initial focus to warehouse
		setFocus(pul_WareHouse);
	}

}
//#CM35279
//end of class
