// $Id: EndInventoryCheckBusiness.java,v 1.2 2006/10/26 05:16:52 suresh Exp $

//#CM38076
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.endinventorycheck;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.EndInventoryCheckSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;

//#CM38077
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * The ASRSstock confirmation end setting screen class. <BR>
 * The content of the screen is set in the parameter, and the schedule does the stock confirmation end setting based on the parameter. <BR>
 * output the schedule result, schedule message to screen<BR>
 * and transaction commit and rollback are done in this screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.set button click event(<CODE>btn_Setting_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  The content input from input area is set in the parameter, and the schedule does the stock confirmation end setting based on the parameter. <BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code <BR>
 * 		password <BR>
 * 		preset area.batch no.<BR>
 *		preset area.warehouse <BR>
 *		preset area.workplace <BR>
 *		preset area.consignor code <BR>
 *		preset area.consignor name <BR>
 *		preset area.start location <BR>
 *		preset area.end location <BR>
 *		preset area.start item code <BR>
 *		preset area.end item code <BR>
 *		terminal no.<BR>
 *		preset area line no.<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/08</TD><TD>toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:16:52 $
 * @author  $Author: suresh $
 */
public class EndInventoryCheckBusiness extends EndInventoryCheck implements WMSConstants
{
	//#CM38078
	// Class fields --------------------------------------------------

	//#CM38079
	// Class variables -----------------------------------------------

	//#CM38080
	// Class method --------------------------------------------------

	//#CM38081
	// Constructors --------------------------------------------------

	//#CM38082
	// Public methods ------------------------------------------------

	//#CM38083
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Invalidate all buttons of the filtering area. <BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			worker code[nil] <BR>
	 * 			password[nil] <BR>
	 * 		</DIR>
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
			//#CM38084
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM38085
			// disable button click
			//#CM38086
			// Picking start button
			btn_Setting.setEnabled(false);
			//#CM38087
			// Select all button
			btn_AllCheck.setEnabled(false);
			//#CM38088
			// Cancel all button
			btn_AllchekClear.setEnabled(false);
		}
		catch (Exception ex)
		{
			//#CM38089
			// rollback connection
			if (conn != null)
			{
				conn.rollback();
			}
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM38090
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

	//#CM38091
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * overview ::display confirmation dialog<BR>
	 * and set the cursor in worker code<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{		
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM38092
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);

			//#CM38093
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);

			//#CM38094
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		
		//#CM38095
		// Confirmation dialog when Start Retrieval button is pressedMSG-9000=Do you want to confirm?
		btn_Setting.setBeforeConfirm("MSG-9000");

		//#CM38096
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);

	}

	//#CM38097
	// Package methods -----------------------------------------------

	//#CM38098
	// Protected methods ---------------------------------------------

	//#CM38099
	// Private methods -----------------------------------------------
	//#CM38100
	/**
	 * The method of filtering and setting the value in the area. <BR>
	 * <BR>
	 * overview ::Filter and set the value in the area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set data in each item of the list cell. <BR>
	 * 		2.Edit data for the balloon display. <BR>
	 * 		3.Make all buttons of the filtering area effective. <BR>
	 * </DIR>
	 * <DIR>
	 * 		[list cell line no. list]
	 * 		<DIR>
	 * 			0.batch no.(hidden item) <BR>
	 * 			2.No. <BR>
	 * 			3.warehouse <BR>
	 * 			4.workplace <BR>
	 * 			5.start location <BR>
	 * 			6.end location <BR>
	 * 			7.consignor code <BR>
	 * 			8.start item code <BR>
	 * 			9.end item code <BR>
	 * 			10.consignor name <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @param listParams Parameter which stores set information
	 * @throws Exception report all the exceptions
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM38101
		// eliminate all lines
		lst_EndInventoryCheck.clearRow();

		AsScheduleParameter[] viewParam = (AsScheduleParameter[]) listParams;

		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM38102
			// add row
			lst_EndInventoryCheck.setCurrentRow(i + 1);
			lst_EndInventoryCheck.addRow();
			lst_EndInventoryCheck.setValue(0, viewParam[i].getBatchNo());
			lst_EndInventoryCheck.setValue(2, Integer.toString(i + 1));
			lst_EndInventoryCheck.setValue(3, viewParam[i].getWareHouseNo());
			lst_EndInventoryCheck.setValue(4, viewParam[i].getStationNo());
			lst_EndInventoryCheck.setValue(5, DisplayText.formatLocation(viewParam[i].getFromLocationNo()));
			lst_EndInventoryCheck.setValue(6, DisplayText.formatLocation(viewParam[i].getToLocationNo()));
			lst_EndInventoryCheck.setValue(7, viewParam[i].getConsignorCode());
			lst_EndInventoryCheck.setValue(8, viewParam[i].getStartItemCode());
			lst_EndInventoryCheck.setValue(9, viewParam[i].getEndItemCode());
			lst_EndInventoryCheck.setValue(10, viewParam[i].getConsignorName());
			
			//#CM38103
			//set tool tip
			ToolTipHelper tTip = new ToolTipHelper();
			//#CM38104
			// consignor name
			tTip.add(DisplayText.getText("LBL-W0026"), viewParam[i].getConsignorName());
			//#CM38105
			// start item code
			tTip.add(DisplayText.getText("LBL-W0213"), viewParam[i].getStartItemCode());
			//#CM38106
			// end item code
			tTip.add(DisplayText.getText("LBL-W0051"), viewParam[i].getEndItemCode());
			
			lst_EndInventoryCheck.setToolTip((i + 1), tTip.getText());
		}
		//#CM38107
		// enable button click
		//#CM38108
		// Picking start button
		btn_Setting.setEnabled(true);
		//#CM38109
		// Select all button
		btn_AllCheck.setEnabled(true);
		//#CM38110
		// Cancel all button
		btn_AllchekClear.setEnabled(true);
	}

	//#CM38111
	// Event handler methods -----------------------------------------
	//#CM38112
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
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM38113
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * overview ::Acquire, filter the display data from the schedule, and display it in the area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Check input item of input area. (mandatory input, character count, character attribute)<BR>
	 *   2.start scheduler<BR>
	 *   <BR>
	 *   <DIR>
	 *   	[parameter] *mandatory input<BR>
	 *   	<DIR>
	 *   		worker code<BR>
	 *   		password<BR>
	 *   	</DIR>
	 *   </DIR>
	 *   <BR>
	 *   3.Straighten and display it in the area. <BR>
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
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM38114
			// input check 
			txt_WorkerCode.validate();
			txt_PassWord.validate();

			//#CM38115
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();
			
			//#CM38116
			// worker code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM38117
			// password
			param.setPassword(txt_PassWord.getText());

			//#CM38118
			// Display schedule processing
			WmsScheduler schedule = new EndInventoryCheckSCH();
			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM38119
				// set message
				message.setMsgResourceKey(schedule.getMessage());

				//#CM38120
				// eliminate all lines
				lst_EndInventoryCheck.clearRow();

				//#CM38121
				// disable button click
				//#CM38122
				// Picking start button
				btn_Setting.setEnabled(false);
				//#CM38123
				// Select all button
				btn_AllCheck.setEnabled(false);
				//#CM38124
				// Cancel all button
				btn_AllchekClear.setEnabled(false);

				return;
			}

			//#CM38125
			// set listcell
			setList(viewParam);

			//#CM38126
			// set message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM38127
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

	//#CM38128
	/** 
	 * called when "Set" button is clicked<BR>
	 * <BR>
	 * overview ::Do the stock confirmation end setting by information on the filtering area. <BR>
	 * <BR>
	 * <DIR>
	 *	  1.set cursor in worker code<BR>
	 *    2.Display the dialog box, and confirm whether to set it. <BR>
	 *    <DIR>
	 * 		[confirmation dialog CANCEL]<BR>
	 *			<DIR>
	 *				do nothing
	 *			</DIR>
	 * 		[confirmation dialog OK]<BR>
	 *			<DIR>
	 *				1.start scheduler<BR>
	 *				<DIR>
	 *   				[parameter]<BR>
	 * 					<DIR>
	 * 						worker code <BR>
	 * 						password <BR>
	 * 						preset area.batch no.<BR>
	 *						preset area.warehouse <BR>
	 *						preset area.workplace <BR>
	 *						preset area.consignor code <BR>
	 *						preset area.consignor name <BR>
	 *						preset area.start location <BR>
	 *						preset area.end location <BR>
	 *						preset area.start item code <BR>
	 *						preset area.end item code <BR>
	 *						terminal no.<BR>
	 *						preset area line no.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.Clear the Input area and filtering area when the result of the schedule is true. <BR>
	 *				3.Display filtered information again. <BR>
	 *              4.Complete and partial setting buttons cannot be used <br>
	 *                if filtering area has 0 records. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		//#CM38129
		// input check
		txt_WorkerCode.validate();
		txt_PassWord.validate();

		//#CM38130
		//fetch max. line no
		int index = lst_EndInventoryCheck.getMaxRows();

		for (int i = 1; i < index; i++)
		{
			try
			{
				//#CM38131
				// specify line
				lst_EndInventoryCheck.setCurrentRow(i);
				//#CM38132
				// Check pattern matching character of specified row (expiry date). 
				lst_EndInventoryCheck.validate(8, false);
			}
			catch (ValidateException ve)
			{
				//#CM38133
				// set message
				String errorMessage =
					MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray());
				//#CM38134
				// 6023273 = No.{0}{1}
				throw new ValidateException("6023273", Integer.toString(i), errorMessage);
			}
		}

		Connection conn = null;
		try
		{
			Vector vecParam = new Vector(index);
			for (int i = 1; i < index; i++)
			{
				//#CM38135
				// specify line
				lst_EndInventoryCheck.setCurrentRow(i);

				//#CM38136
				// Process only the line where the check starts selecting. 
				if (lst_EndInventoryCheck.getChecked(1) == true)
				{
					//#CM38137
					// set schedule parameter
					AsScheduleParameter param = new AsScheduleParameter();
					//#CM38138
					// worker code
					param.setWorkerCode(txt_WorkerCode.getText());
					//#CM38139
					// password
					param.setPassword(txt_PassWord.getText());
	
					//#CM38140
					// terminal no.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());
					
					//#CM38141
					// schedule no.
					param.setBatchNo(lst_EndInventoryCheck.getValue(0));
					//#CM38142
					// warehouse
					param.setWareHouseNo(lst_EndInventoryCheck.getValue(3));
					//#CM38143
					// workplace
					param.setStationNo(lst_EndInventoryCheck.getValue(4));
					//#CM38144
					// start location
					param.setFromLocationNo(lst_EndInventoryCheck.getValue(5));
					//#CM38145
					// end location 
					param.setToLocationNo(lst_EndInventoryCheck.getValue(6));
					//#CM38146
					// consignor code
					param.setConsignorCode(lst_EndInventoryCheck.getValue(7));
					//#CM38147
					// start item code
					param.setStartItemCode(lst_EndInventoryCheck.getValue(8));
					//#CM38148
					// end item code
					param.setEndItemCode(lst_EndInventoryCheck.getValue(9));
					//#CM38149
					// consignor name
					param.setConsignorName(lst_EndInventoryCheck.getValue(10));
	
					//#CM38150
					// save the line no.
					param.setRowNo(i);
	
					vecParam.addElement(param);
				}
			}
			if (vecParam.size() <= 0)
			{
				//#CM38151
				// 6023154 = modification target data does not exist
				message.setMsgResourceKey("6023154");
				return;
			}

			AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			//#CM38152
			// set schedule process
			WmsScheduler schedule = new EndInventoryCheckSCH();
			//#CM38153
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM38154
			// start schedule
			AsScheduleParameter[] viewParam =
				(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM38155
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();
				//#CM38156
				// set message
				message.setMsgResourceKey(schedule.getMessage());

				if (viewParam.length != 0)
				{
					//#CM38157
					// set listcell
					setList(viewParam);
				}
				else
				{
					//#CM38158
					// eliminate all lines
					lst_EndInventoryCheck.clearRow();
					
					//#CM38159
					// disable button click
					//#CM38160
					// Picking start button
					btn_Setting.setEnabled(false);
					//#CM38161
					// Select all button
					btn_AllCheck.setEnabled(false);
					//#CM38162
					// Cancel all button
					btn_AllchekClear.setEnabled(false);

				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			conn.rollback();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM38163
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

	//#CM38164
	/**
	 * This is called when "Select All" button is clicked <BR>
	 * <BR>
	 * overview ::check all the contents of list cell<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM38165
		// set listcell
		for (int i = 1; i < lst_EndInventoryCheck.getMaxRows(); i++)
		{
			lst_EndInventoryCheck.setCurrentRow(i);
			lst_EndInventoryCheck.setChecked(1, true);
		}
	}

	//#CM38166
	/** 
	 * This is called when "All cancel" button is clicked <BR>
	 * <BR>
	 * overview ::remove the check from listcell content checkbox<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllchekClear_Click(ActionEvent e) throws Exception
	{
		//#CM38167
		// set listcell
		for (int i = 1; i < lst_EndInventoryCheck.getMaxRows(); i++)
		{
			lst_EndInventoryCheck.setCurrentRow(i);
			lst_EndInventoryCheck.setChecked(1, false);
		}
	}

}
//#CM38168
//end of class
