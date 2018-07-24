// $Id: RegistWorkerMasterBusiness.java,v 1.2 2006/11/13 08:21:30 suresh Exp $

//#CM693867
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.registworkermaster;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.system.display.web.listbox.listworker.ListWorkerBusiness;
import jp.co.daifuku.wms.base.system.schedule.RegisterWorkerMasterSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693868
/**
 * Designer : B.Shibayama <BR>
 * Maker    : B.Shibayama <BR>
 * <BR>
 * Allow this screen to add a Worker master.<BR>
 * Set the contents input via screen for the parameter and pass it to the schedule. Execute the process for adding.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc.<BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen.<BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process by clicking "Add" button(<CODE>btn_Submit_Click</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Check the field items. If proper, set them for the parameter.<BR>
 *   Set the result of schedule in the Message area and display it.<BR>
 *   
 * <BR>
 *   [Parameter] *Mandatory Input <BR>
 * <BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Added Worker Code* <BR>
 *     Person Name* <BR>
 *     Phonetic transcriptions in kana <BR>
 *     Sex* <BR>
 *     Job Title* <BR>
 *     Access Privileges* <BR>
 *     Password* <BR>
 *     Memo 1 <BR>
 *     Memo 2 <BR>
 *   </DIR>
 * <BR>
 *    [Returned data] <BR>
 * <BR>
 *    <DIR>
 *     Each message
 *    </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>B.Shibayama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:30 $
 * @author  $Author: suresh $
 */
public class RegistWorkerMasterBusiness extends RegistWorkerMaster implements WMSConstants
{
	//#CM693869
	// Class fields --------------------------------------------------
	
	//#CM693870
	// Class variables -----------------------------------------------

	//#CM693871
	// Class method --------------------------------------------------

	//#CM693872
	// Constructors --------------------------------------------------

	//#CM693873
	// Public methods ------------------------------------------------
	//#CM693874
	/**
	 * Completing reading a screen invokes this. <BR>
	 * <BR>
	 * Summary: Shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the screen title. <BR>
	 *    2.Move the cursor to the Worker Code. <BR>
	 * </DIR>
	 * <BR>
	 * Field item [Initial Value] <BR>
	 * <BR>
	 * Worker Code [None] <BR>
	 * Password [None] <BR>
	 * Added Worker Code [None] <BR>
	 * Person Name [None] <BR>
	 * Phonetic transcriptions in kana [None] <BR>
	 * Sex[Male] <BR>
	 * Job Title[Administrator] <BR>
	 * Access Privileges [System Administrator] <BR>
	 * Password [None] <BR>
	 * Memo 1 [None] <BR>
	 * Memo 2 [None] <BR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM693875
		// Show the Initial Display.
		txt_WorkerCode.setText("");
		txt_Name.setText("");
		txt_Furigana.setText("");
		//#CM693876
		// Set the Sex to Male.
		pul_Gender.setSelectedIndex(0);
		//#CM693877
		// Set the Job Title to Administrator.
		pul_Jobtype.setSelectedIndex(0);
		//#CM693878
		// Set the Access Privileges to System Administrator.
		pul_Access.setSelectedIndex(0);
		txt_Password.setText("");
		txt_Memo1.setText("");
		txt_Memo2.setText("");

		//#CM693879
		// Move the cursor to the Worker Code.
		setFocus(txt_WorkerCode_T);

	}

	//#CM693880
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog. <BR>
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
			//#CM693881
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM693882
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM693883
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM693884
			//Set the path to the help file.
			//#CM693885
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM693886
		//Display a dialog: MSG-0009= "Do you add it?"
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	//#CM693887
	// Package methods -----------------------------------------------

	//#CM693888
	// Protected methods ---------------------------------------------
	//#CM693889
	/**
	 * Check for input.
	 * Set the message if any error and return false.
	 * 
	 * @return true: Normal, false: Abnormal
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_WorkerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
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

	//#CM693890
	// Private methods -----------------------------------------------

	//#CM693891
	// Event handler methods -----------------------------------------
	//#CM693892
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693893
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693894
	/** 
	 * Clicking on Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Returns to the Menu screen. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM693895
		// Return to the Menu screen.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM693896
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693897
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693898
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693899
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693900
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693901
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693902
	/** 
	 * Clicking on Search button of Added Worker Code invokes this.<BR>
	 * <BR>
	 * Summary: Searches for added worker code.<BR>
	 * <BR>
	 * <DIR>
	 * 		�P.Check for input in the input item (count). <BR>
	 * </DIR>
	 * <DIR>
	 * [Parameter] <BR>
	 * <DIR> 
	 *      Added Worker Code <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearch_Click(ActionEvent e) throws Exception
	{
		//#CM693903
		// Set the search conditions into the Worker List screen.
		ForwardParameters param = new ForwardParameters();

		//#CM693904
		// Added Worker Code
		param.setParameter(ListWorkerBusiness.WORKERCODE_KEY, txt_WorkerCode.getText());
		//#CM693905
		// Search through the Worker table.
		param.setParameter(ListWorkerBusiness.SEARCHWORKER_KEY, SystemParameter.SEARCHFLAG_PLAN);

		//#CM693906
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listworker/ListWorker.do", param, "/progress.do");
	}

	//#CM693907
	/**
	 * Invoke this method when returning from the popup window.<BR>
	 * Override the page_DlgBack defined for Page.<BR>
	 * <BR>
	 * Summary: Obtains the Returned data in the Search screen and set it. <BR>
	 * <BR>
	 * <DIR>
	 *      1.Obtain the value returned from the Search screen. <BR>
	 *      2.Set the screen if the value is not empty. <BR>
	 * <BR></DIR>
	 * [Returned data] <BR>
	 * <DIR>
	 *     Added Worker Code <BR>
	 *     Person Name <BR>
	 *     Phonetic transcriptions in kana <BR>
	 *     Sex <BR>
	 *     Job Title <BR>
	 *     Access Privileges <BR>
	 *     Password <BR>
	 *     Memo 1 <BR>
	 *     Memo 2 <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM693908
		// Obtain the parameter selected in the listbox.
		String workercode = param.getParameter(ListWorkerBusiness.WORKERCODE_KEY);
		String name = param.getParameter(ListWorkerBusiness.NAME_KEY);
		String furigana = param.getParameter(ListWorkerBusiness.FURIGANA_KEY);
		String gender = param.getParameter(ListWorkerBusiness.SEX_KEY);
		String jobtype = param.getParameter(ListWorkerBusiness.WORKER_JOBTYPE_KEY);
		String access = param.getParameter(ListWorkerBusiness.ACCESS_AUTHORITY_KEY);
		String password = param.getParameter(ListWorkerBusiness.PASSWORD_KEY);
		String memo1 = param.getParameter(ListWorkerBusiness.MEMO1_KEY);
		String memo2 = param.getParameter(ListWorkerBusiness.MEMO2_KEY);

		//#CM693909
		// Set a value if not empty.
		//#CM693910
		// Worker Code
		if (!StringUtil.isBlank(workercode))
		{
			txt_WorkerCode.setText(workercode);
		}
		//#CM693911
		// Person Name
		if (!StringUtil.isBlank(name))
		{
			txt_Name.setText(name);
		}
		//#CM693912
		// Phonetic transcriptions in kana			
		if (!StringUtil.isBlank(furigana))
		{
			txt_Furigana.setText(furigana);
		}
		//#CM693913
		// Sex		
		if (!StringUtil.isBlank(gender))
		{
			if (gender.equals(SystemParameter.SELECTSEX_MALE))
			{
				//#CM693914
				// Male
				pul_Gender.setSelectedIndex(0);
			}
			else
			{
				//#CM693915
				// Female
				pul_Gender.setSelectedIndex(1);
			}

		}
		//#CM693916
		// Job Title		
		if (!StringUtil.isBlank(jobtype))
		{
			if (jobtype.equals(SystemParameter.SELECTWORKERJOBTYPE_ADMINISTRATOR))
			{
				//#CM693917
				// Administrator
				pul_Jobtype.setSelectedIndex(0);
			}
			else
			{
				//#CM693918
				// Worker
				pul_Jobtype.setSelectedIndex(1);
			}
		}
		//#CM693919
		// Access Privileges
		if (!StringUtil.isBlank(access))
		{
			if (access.equals(SystemParameter.SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR))
			{
				//#CM693920
				// System Administrator
				pul_Access.setSelectedIndex(0);
			}
			else if (access.equals(SystemParameter.SELECTACCESSAUTHORITY_ADMINISTRATOR))
			{
				//#CM693921
				// Administrator
				pul_Access.setSelectedIndex(1);
			}
			else
			{
				//#CM693922
				// Worker
				pul_Access.setSelectedIndex(2);
			}

		}
		//#CM693923
		// Password
		if (!StringUtil.isBlank(password))
		{
			txt_Password.setText(password);
		}
		//#CM693924
		// Memo 1
		if (!StringUtil.isBlank(memo1))
		{
			txt_Memo1.setText(memo1);
		}
		//#CM693925
		// Memo 2
		if (!StringUtil.isBlank(memo2))
		{
			txt_Memo2.setText(memo2);
		}
		//#CM693926
		// Move the cursor to the Worker Code.
		setFocus(txt_WorkerCode_T);

	}

	//#CM693927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Name_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Name_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Name_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Name_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Name_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Furigana_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Furigana_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Furigana_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693935
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Furigana_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Furigana_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Gender_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Gender_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Gender_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Jobtype_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Jobtype_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Jobtype_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Access_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Access_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Access_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693946
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693947
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693948
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693949
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693950
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Memo1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693951
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo1_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo1_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693954
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo1_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693955
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Memo2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693957
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo2_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693958
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo2_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693959
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo2_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693960
	/** 
	 *
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693961
	/** 
	 * Clicking on Add button invokes this.<BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to add it or not and then passes the parameter.<BR>
	 * <BR>
	 * <DIR>
	 *    -Display the dialog box for confirming to add. ("Do you add this?") <BR>
	 *    <DIR>
	 *        [Dialog for confirming to add: OK ]<BR>
	 *        <DIR>	
	 *            1.Set the cursor for Worker code.<BR>
	 *            2.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters)<BR>
	 * 	          3.Modify and add the data using the info in Input area.<BR>
	 *            4.Start the schedule.<BR>
	 *            5.Receive a value from the schedule.<BR>
	 * 			  6.Display a message.<BR>
	 *        </DIR>
	 *        [Dialog for confirming to add: Cancel] <BR>
	 *        <DIR>
	 *             Disable to do anything.<BR>
	 *        </DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception  Report all exceptions. 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM693962
		// Move the cursor to the Worker Code.
		setFocus(txt_WorkerCode_T);
		
		//#CM693963
		// Check for input / Check for the character subject to mandatory check.
		txt_WorkerCode_T.validate();
		txt_Password_T.validate();
		txt_WorkerCode.validate();
		txt_Name.validate();
		txt_Furigana.validate(false);		
		txt_Password.validate();
		txt_Memo1.validate(false);
		txt_Memo2.validate(false);

		//#CM693964
		// Check the input characters for eWareNavi.
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM693965
			//	Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM693966
			// Set for the schedule parameter.
			SystemParameter[] param = new SystemParameter[2];
			param[0] = new SystemParameter();
			
			//#CM693967
			// Parameter for checking Worker Code and password.
			param[1] = new SystemParameter();

			//#CM693968
			// Worker Code 
			param[1].setWorkerCode(txt_WorkerCode_T.getText());
			//#CM693969
			// Password
			param[1].setPassword(txt_Password_T.getText());

			//#CM693970
			// Worker Code
			param[0].setWorkerCode(txt_WorkerCode.getText());
			//#CM693971
			// Person Name 
			param[0].setWorkerName(txt_Name.getText());
			//#CM693972
			// Phonetic transcriptions in kana 
			param[0].setFurigana(txt_Furigana.getText());
			//#CM693973
			// Sex
			if (pul_Gender.getSelectedIndex() == 0)
			{
				//#CM693974
				// Male
				param[0].setSelectSex(SystemParameter.SELECTSEX_MALE);
			}
			else
			{
				//#CM693975
				// Female
				param[0].setSelectSex(SystemParameter.SELECTSEX_FEMALE);
			}
			//#CM693976
			// Job Title
			if (pul_Jobtype.getSelectedIndex() == 0)
			{
				//#CM693977
				// Administrator
				param[0].setSelectWorkerJobType(SystemParameter.SELECTWORKERJOBTYPE_ADMINISTRATOR);
			}
			else
			{
				//#CM693978
				// Worker
				param[0].setSelectWorkerJobType(SystemParameter.SELECTWORKERJOBTYPE_WORKER);
			}

			//#CM693979
			// Access Privileges
			if (pul_Access.getSelectedIndex() == 0 )
			{
				//#CM693980
				// System Administrator
				param[0].setSelectAccessAuthority(
					SystemParameter.SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR);

			}
			else if (pul_Access.getSelectedIndex() == 1 )
			{
				//#CM693981
				// Administrator
				param[0].setSelectAccessAuthority(SystemParameter.SELECTACCESSAUTHORITY_ADMINISTRATOR);
			}
			else
			{
				//#CM693982
				// Worker
				param[0].setSelectAccessAuthority(SystemParameter.SELECTACCESSAUTHORITY_WORKER);
			}

			//#CM693983
			// Password 
			param[0].setPassword(txt_Password.getText());
			//#CM693984
			// Memo 1
			param[0].setMemo1(txt_Memo1.getText());
			//#CM693985
			// Memo 2
			param[0].setMemo2(txt_Memo2.getText());

			

			//#CM693986
			// Declare the schedule.
			WmsScheduler schedule = new RegisterWorkerMasterSCH();

			if (schedule.startSCH(conn, param))
			{
				//#CM693987
				// Execute committing.
				conn.commit();
			}
			else
			{
				//#CM693988
				// Execute Roll-back.
				conn.rollback();
			}

			//#CM693989
			// Set the message
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
				//#CM693990
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

	//#CM693991
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693992
	/**
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * </BR>
	 * <DIR>
	 *        1.Return the item (count) in the input area to the initial value.<BR>
	 *        <DIR>
	 *             For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method.<BR>
	 *        </DIR>
	 *        2.Set the cursor for the Worker code.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM693993
		// Show the Initial Display.
		txt_WorkerCode.setText("");
		txt_Name.setText("");
		txt_Furigana.setText("");
		//#CM693994
		// Set the Sex to Male.
		pul_Gender.setSelectedIndex(0);
		//#CM693995
		// Set the Job Title to Administrator.
		pul_Jobtype.setSelectedIndex(0);
		//#CM693996
		// Set the Access Privileges to System Administrator.
		pul_Access.setSelectedIndex(0);
		txt_Password.setText("");
		txt_Memo1.setText("");
		txt_Memo2.setText("");

		//#CM693997
		// Move the cursor to the Worker Code.
		setFocus(txt_WorkerCode_T);
	}

	//#CM693998
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693999
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}
	//#CM694000
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Regist_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694001
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_T_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694002
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694003
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_T_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694004
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_T_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694005
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_T_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694006
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_T_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694007
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_T_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694008
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_T_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694009
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_T_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694010
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_T_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694011
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_T_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//#CM694012
//end of class
