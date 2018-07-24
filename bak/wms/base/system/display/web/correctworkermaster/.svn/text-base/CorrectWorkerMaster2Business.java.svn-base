// $Id: CorrectWorkerMaster2Business.java,v 1.2 2006/11/13 08:18:07 suresh Exp $

//#CM687219
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.correctworkermaster;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.system.schedule.CorrectWorkerMasterSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM687220
/**
 * Designer : B.Shibayama <BR>
 * Maker    : B.Shibayama <BR>
 * <BR>
 * Allow this screen to modify or delete Worker master (input a value to be modified).<BR>
 * Display the data passed from the original screen. <BR>
 * Set the contents input via screen for the parameter and then pass it to the schedule. <BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen. <BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.for loading a page (<CODE>page_Load</CODE> method) <BR>
 *   
 * <BR>
 * <DIR>
 *  Execute search using the value maintained in the ViewState. <BR>
 *  Display the schedule result in the Input area.<BR>
 *  <BR>
 *   [Parameter] *Mandatory Input <BR>
 *   <DIR>
 *        Worker Code* <BR>
 *   </DIR>
 *  <BR>
 *  [Returned data]  <BR>
 *  <BR>
 *   <DIR>
 * 		   Worker Code <BR>
 *         Person Name <BR>
 *         Phonetic transcriptions in kana <BR>
 *         Sex <BR>
 *         Job Title <BR>
 *         Access Privileges <BR>
 *         Password <BR>
 *         Memo 1 <BR>
 *         Memo 2 <BR>
 *         Add ON/OFF Status <BR>
 *         Added date <BR>
 *         Last Update Date <BR>
 *    </DIR>
 * 
 * </DIR>
 * 
 * 2.Process by clicking "Modify/Add" button (<CODE>btn_ModifySubmit_Click</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Check the field items. If proper, set them for the parameter.<BR>
 *   Set the result of schedule in the Message area and display it.<BR>
 * <BR>
 *   [Parameter] *Mandatory Input <BR>
 * <BR>
 *   <DIR>
 * 		   Worker Code* <BR>
 *         Person Name* <BR>
 *         Phonetic transcriptions in kana <BR>
 *         Sex* <BR>
 *         Job Title* <BR>
 *         Access Privileges* <BR>
 *         Password* <BR>
 *         Memo 1 <BR>
 *         Memo 2 <BR>
 *         Add ON/OFF Status* <BR>
 *         Added date <BR>
 *         Last Update Date <BR>
 *    </DIR>
 * <BR>
 *   [Returned data] <BR>
 *  <BR><DIR>
 *         Each message <BR>
 *         Worker Code <BR>
 *         Person Name <BR>
 *         Phonetic transcriptions in kana <BR>
 *         Sex <BR>
 *         Job Title <BR>
 *         Access <BR>
 *         Password <BR>
 *         Memo 1 <BR>
 *         Memo 2 <BR>
 *         Add ON/OFF Status <BR>
 *         Added date <BR>
 *         Last Update Date <BR>
 *    </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/10</TD><TD>B.Shibayama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:07 $
 * @author  $Author: suresh $
 */
public class CorrectWorkerMaster2Business extends CorrectWorkerMaster2 implements WMSConstants
{
	//#CM687221
	// Class fields --------------------------------------------------

	//#CM687222
	// Class variables -----------------------------------------------

	//#CM687223
	// Class method --------------------------------------------------

	//#CM687224
	// Constructors --------------------------------------------------

	//#CM687225
	// Public methods ------------------------------------------------

	//#CM687226
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Summary: Shows the Initial Display.<BR>
	 * <BR><DIR>
	 *       1.Display the screen title. <BR>
	 *       2.Set the value brought from the original screen for the parameter. <BR>
	 *       3.Start the schedule. <BR>
	 *       4.Set the Returned data on the screen. <BR>
	 *       5.Set the cursor to Person Name.<BR>
	 * <BR></DIR>
	 * <BR>
	 * Field item [Initial Value] <BR>
	 * <BR>
	 * Worker Code [Search Screen] <BR>
	 * Person Name [Search Value] <BR>
	 * Phonetic transcriptions in kana [Search Value] <BR>
	 * Sex [Search Value] <BR>
	 * Job Title [Search Value] <BR>
	 * Access Privileges [Search Value] <BR>
	 * Password [Search Value] <BR>
	 * Memo 1 [Search Value] <BR>
	 * Memo 2 [Search Value] <BR>
	 * Add ON/OFF Status [Search Value] <BR>
	 * Added Date [Search Value] <BR>
	 * Last Update Date [Search Value] <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM687227
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		Connection conn = null;
		try
		{
			//#CM687228
			// Display the tab of the second screen foreground.
			tab_WkrMsrIptMdfy.setSelectedIndex(2);

			//#CM687229
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM687230
			// Declare the schedule. 
			WmsScheduler schedule = new CorrectWorkerMasterSCH();

			//#CM687231
			// Declare the Schedule parameter.
			SystemParameter param = new SystemParameter();
			//#CM687232
			// Set the value brought from the original screen for the parameter.
			param.setWorkerCode(getViewState().getString("Workercode"));
			//#CM687233
			// Start the schedule. 
			SystemParameter initParam = (SystemParameter) schedule.initFind(conn, param);

			//#CM687234
			// Set the Returned data on the screen. 
			//#CM687235
			// Worker Code
			lbl_JavaSet.setText(initParam.getWorkerCode());
			//#CM687236
			// Person Name
			txt_Name.setText(initParam.getWorkerName());
			//#CM687237
			// Phonetic transcriptions in kana
			txt_Furigana.setText(initParam.getFurigana());

			//#CM687238
			//Sex
			if (initParam.getSelectSex().equals(SystemParameter.SELECTSEX_MALE))
			{
				//#CM687239
				// Male
				pul_Gender.setSelectedIndex(0);
			}
			else
			{
				//#CM687240
				// Female
				pul_Gender.setSelectedIndex(1);
			}

			//#CM687241
			//Job Title
			if (initParam.getSelectWorkerJobType().equals(SystemParameter.SELECTWORKERJOBTYPE_ADMINISTRATOR))
			{
				//#CM687242
				// Administrator
				pul_Jobtype.setSelectedIndex(0);
			}
			else
			{
				//#CM687243
				// Worker
				pul_Jobtype.setSelectedIndex(1);
			}

			//#CM687244
			//Access Privileges
			if (initParam.getSelectAccessAuthority().equals(SystemParameter.SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR))
			{
				//#CM687245
				// System Administrator
				pul_Access.setSelectedIndex(0);
			}
			else if (initParam.getSelectAccessAuthority().equals(SystemParameter.SELECTACCESSAUTHORITY_ADMINISTRATOR))
			{
				//#CM687246
				// Administrator
				pul_Access.setSelectedIndex(1);
			}
			else
			{
				//#CM687247
				// Worker
				pul_Access.setSelectedIndex(2);
			}

			txt_Password.setText(initParam.getPassword());
			txt_Memo1.setText(initParam.getMemo1());
			txt_Memo2.setText(initParam.getMemo2());

			//#CM687248
			// Add ON/OFF Status
			if (initParam.getSelectStatus().equals(SystemParameter.SELECTSTATUS_ENABLE))
			{
				//#CM687249
				// Available
				rdo_UseOn.setChecked(true);
				rdo_UseOff.setChecked(false);
			}

			else
			{
				//#CM687250
				// Suspended.
				rdo_UseOff.setChecked(true);
				rdo_UseOn.setChecked(false);
			}
			//#CM687251
			// Added date	
			txt_FDateRst.setDate(initParam.getRegistDate());
			
			//#CM687252
			// Last Update Date
			txt_FDateLastUpdateDate.setDate(initParam.getLastUpdateDate());
            //#CM687253
            // Last update date/time
			this.getViewState().setString("LastUpDate",initParam.getLastUpdateDate().toString()); 
			
			//#CM687254
			// Move the cursor to the Person Name.
			setFocus(txt_Name);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM687255
				// Close the connection.
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

   //#CM687256
   /**
	* Invoke this before invoking each control event.<BR>
	* <BR>
	* <DIR>
	*  Summary: Displays a dialog.<BR>
	* </DIR>
	* 
	* @param e ActionEvent A class to store event information.
	* @throws Exception Report all exceptions.
	*/
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM687257
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM687258
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM687259
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM687260
			//Set the path to the help file.
			//#CM687261
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM687262
		// Display a dialog: "Do you modify and add it?"
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");
	}
	//#CM687263
	// Package methods -----------------------------------------------

	//#CM687264
	// Protected methods ---------------------------------------------
	//#CM687265
	/**
	 * Check for input.
	 * Set the message if any error and return false.
	 * 
	 * @return true: Normal, false: Abnormal
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_Name))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_Furigana))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_Password))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_Memo1))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_Memo2))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
	
		return true;
		
	}

	//#CM687266
	// Private methods -----------------------------------------------

	//#CM687267
	// Event handler methods -----------------------------------------
	//#CM687268
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687269
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687270
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687271
	/** 
	 * Clicking on Return button invokes this. <BR>
	 * <BR>
	 * Summary: Return to the previous screen.<BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//#CM687272
		//Shift the screen.
		forward("/system/correctworkermaster/CorrectWorkerMaster.do");
	}

	//#CM687273
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687274
	/** 
	 * Clicking on Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Returns to the Menu screen.<BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM687275
		// Return to the FTTB Menu screen.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM687276
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687277
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSet_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687278
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Name_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687279
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Name_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687280
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Name_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687281
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Name_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687282
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Name_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687283
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Furigana_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687284
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Furigana_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687285
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Furigana_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687286
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Furigana_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687287
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Furigana_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687288
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Gender_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687289
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Gender_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687290
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Gender_Change(ActionEvent e) throws Exception
	{
	}

	//#CM687291
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Jobtype_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687292
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Jobtype_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687293
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Jobtype_Change(ActionEvent e) throws Exception
	{
	}

	//#CM687294
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Access_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687295
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Access_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687296
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Access_Change(ActionEvent e) throws Exception
	{
	}

	//#CM687297
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687298
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687299
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687300
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687301
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Memo1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687302
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687303
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo1_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687304
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo1_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687305
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo1_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687306
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Memo2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687307
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687308
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo2_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687309
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo2_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687310
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo2_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687311
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RegistStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_UseOn_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687313
	/** 
	 * @param e ActionEvent 
	 * @throws Exception  
	 */
	public void rdo_UseOn_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687314
	/** 
	 *
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_UseOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687315
	/** 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_UseOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RegistDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateRst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateRst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateRst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LastUpdateDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687321
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateLastUpdateDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687322
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateLastUpdateDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687323
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateLastUpdateDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687324
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687325
	/** 
	 * Clicking on Modify/Add button invokes this.<BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to modify and add it or not and then passes the parameter.<BR>
	 * <BR>
	 * <DIR>
	 *    -Display a dialog box to allow to confirm to modify it ("Do you modify and add it?"). <BR>
	 *    <DIR>
	 *        [Dialog for confirming to modify/add: OK ]<BR>
	 *        <DIR>	
	 *            1.Move the cursor to the Person Name. <BR>
	 *            2.Check for input in the input item (count) in the input area. <BR>
	 * 	          3.Modify and add the data using the info in Input area.<BR>
	 *            4.Start the schedule.<BR>
	 *            5.Receive a value from the schedule.<BR>
	 * 			  6.Display a message.<BR>
	 *        </DIR>
	 *        [Dialog for confirming to modify/add: Cancel] <BR>
	 *        <DIR>
	 *             Disable to do anything.<BR>
	 *        </DIR>
	 *    </DIR>
	 * </DIR><BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception  Report all exceptions.
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		//#CM687326
		// Move the cursor to the Person Name.
		setFocus(txt_Name);
		//#CM687327
		// Check for input.
		txt_Name.validate();
		txt_Furigana.validate(false);
		txt_Password.validate();
		txt_Memo1.validate(false);
		txt_Memo2.validate(false);

		//#CM687328
		// Check the input characters for eWareNavi.
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{

			//#CM687329
			//	Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM687330
			// Set for the schedule parameter.
			SystemParameter[] param = new SystemParameter[1];
			param[0] =new SystemParameter();
			//#CM687331
			// Worker Code
			param[0].setWorkerCode(lbl_JavaSet.getText());
			//#CM687332
			// Person Name 
			param[0].setWorkerName(txt_Name.getText());
			//#CM687333
			// Phonetic transcriptions in kana 
			param[0].setFurigana(txt_Furigana.getText());

			//#CM687334
			// Sex
			if (pul_Gender.getSelectedIndex() ==0)
			{
				//#CM687335
				// Male
				param[0].setSelectSex(SystemParameter.SELECTSEX_MALE);
			}
			else
			{
				//#CM687336
				// Female
				param[0].setSelectSex(SystemParameter.SELECTSEX_FEMALE);
			}

			//#CM687337
			// Job Title
			if (pul_Jobtype.getSelectedIndex() ==0 )
			{
				//#CM687338
				// Administrator
				param[0].setSelectWorkerJobType(SystemParameter.SELECTWORKERJOBTYPE_ADMINISTRATOR);
			}
			else
			{
				//#CM687339
				// Worker
				param[0].setSelectWorkerJobType(SystemParameter.SELECTWORKERJOBTYPE_WORKER);
			}

			//#CM687340
			// Access Privileges
			if (pul_Access.getSelectedIndex() ==0)
			{
				//#CM687341
				// System Administrator
				param[0].setSelectAccessAuthority(
					SystemParameter.SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR);

			}
			else if (pul_Access.getSelectedIndex() == 1)
			{
				//#CM687342
				// Administrator
				param[0].setSelectAccessAuthority(SystemParameter.SELECTACCESSAUTHORITY_ADMINISTRATOR);
			}
			else
			{
				//#CM687343
				// Worker
				param[0].setSelectAccessAuthority(SystemParameter.SELECTACCESSAUTHORITY_WORKER);
			}

			//#CM687344
			// Password 
			param[0].setPassword(txt_Password.getText());
			//#CM687345
			// Memo 1
			param[0].setMemo1(txt_Memo1.getText());
			//#CM687346
			// Memo 2
			param[0].setMemo2(txt_Memo2.getText());

			//#CM687347
			// Add ON/OFF Status 
			if (rdo_UseOn.getChecked())
			{
				param[0].setSelectStatus(SystemParameter.SELECTSTATUS_ENABLE);
			}
			else
			{
				param[0].setSelectStatus(SystemParameter.SELECTSTATUS_DISABLE);
			}
			//#CM687348
			// Last Update Date 
			param[0].setWLastUpdateDateString(this.getViewState().getString("LastUpDate"));
			
			//#CM687349
			// Declare the schedule.
			WmsScheduler schedule = new CorrectWorkerMasterSCH();

			//#CM687350
			// Start the schedule.
			SystemParameter[] modifyParam = (SystemParameter[]) schedule.startSCHgetParams(conn, param);
			
			if (modifyParam == null || modifyParam.length == 0)
			{
				//#CM687351
				// Roll-back
				conn.rollback() ;
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}else
			{
				//#CM687352
				// Commit
				conn.commit() ;
			}

			//#CM687353
			// Set the message: "Modified and added".
			message.setMsgResourceKey(schedule.getMessage());

			//#CM687354
			// Update the Last Update Date.
			txt_FDateLastUpdateDate.setDate(modifyParam[0].getLastUpdateDate());
			//#CM687355
			// Update the last update date/time of viewState.
			this.getViewState().setString("LastUpDate",modifyParam[0].getLastUpdateDate().toString()); 

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM687356
				// Close the connection.
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

	//#CM687357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687358
	/**
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * </BR>
	 * <DIR>
	 * 1.Set the field item in Input area as below.<BR>
	 * <DIR>
	 * Field item [Value] <BR>
	 * Person Name [None] <BR>
	 * Phonetic transcriptions in kana [None] <BR>
	 * Sex[Male] <BR>
	 * Job Title[Administrator] <BR>
	 * Access Privileges [System Administrator] <BR>
	 * Password [None] <BR>
	 * Memo 1 [None] <BR>
	 * Memo 2 [None] <BR>
	 * </DIR>
	 * 2.Set the cursor to Person Name. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM687359
		// Invoke the method to display the initial display.
		page_Load(e);
		//#CM687360
		// Move the cursor to the Person Name.
		setFocus(txt_Name);
	}

	//#CM687361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}
	
}
//#CM687362
//end of class
