// $Id: RFTState2Business.java,v 1.4 2006/11/21 04:22:36 suresh Exp $

//#CM694831
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.rftstate;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.rft.IdDataFile;
import jp.co.daifuku.wms.base.system.schedule.RFTStateSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM694832
/**
 * Designer :<BR>
 * Maker :   <BR>
 * <BR>
 * Allow this class to provide a screen for RFT Work Status Maintenance. <BR>
 * Display the RFT status from the schedule. <BR>
 * Allow the schedule to update the data to Not Worked or Completed. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display <BR>
 * <BR>
 * <DIR>
 *   Show the Initial Display. <BR>
 *   <DIR>
 *     Disable to display any work code with RFT status "Not Worked". <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Process by clicking "Setup" button(btn_Submit_Click(ActionEvent e)method)  <BR>
 * <BR>
 * <DIR>
 *   Update the RFT status.<BR>
 * 	 <DIR>
 *     Disable to display any work code with RFT status "Not Worked". <BR>
 *   </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/11/21 04:22:36 $
 * @author  $Author: suresh $
 */
public class RFTState2Business extends RFTState2 implements WMSConstants
{
	//#CM694833
	// Class fields --------------------------------------------------
	//#CM694834
	/**
	 * Use this key to pass RFT Machine No.
	 */
	public static final String RFTNO_KEY = "RFTNO_KEY";

	//#CM694835
	/**
	 * Use this key to pass Terminal Type.
	 */
	public static final String TERMINALTYPE_KEY = "TERMINALTYPE_KEY";

	//#CM694836
	/**
	 * Use this key to pass RFT status.
	 */
	public static final String RFTSTATUS_KEY = "RFTSTATUS_KEY";

	//#CM694837
	/**
	 * Use this key to pass Worker Code.
	 */
	public static final String WORKERCODE_KEY = "WORKERCODE_KEY";

	//#CM694838
	/**
	 * Use this key to pass Worker Name.
	 */
	public static final String WORKERNAME_KEY = "WORKERNAME_KEY";

	//#CM694839
	// Class variables -----------------------------------------------

	//#CM694840
	// Class method --------------------------------------------------

	//#CM694841
	// Constructors --------------------------------------------------

	//#CM694842
	// Public methods ------------------------------------------------

	//#CM694843
	/**
	 * Initialize the screen.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM694844
		// Set the initial value of the Worker Code.
		txt_WorkerCode.setText("");
		//#CM694845
		// Set the initial value of the Password.
		txt_Password.setText("");

		//#CM694846
		// Obtain the parameter.
		//#CM694847
		// RFT Machine No.
		String rftNo = request.getParameter(RFTNO_KEY);
		//#CM694848
		// Terminal Type
		String terminalType = request.getParameter(TERMINALTYPE_KEY);
		//#CM694849
		// RFT status.
		String rftStatus = request.getParameter(RFTSTATUS_KEY);
		//#CM694850
		// Worker Code
		String workerCode = request.getParameter(WORKERCODE_KEY);
		//#CM694851
		// Worker Name
		String workerName = request.getParameter(WORKERNAME_KEY);

		lbl_RftNo.setText(rftNo);
		lbl_TerminalCategory.setText(URLDecoder.decode(terminalType,"UTF-8"));
		lbl_RftStatus.setText(URLDecoder.decode(rftStatus,"UTF-8"));
		lbl_WorkerCode.setText(workerCode);
		lbl_WorkerName.setText(URLDecoder.decode(workerName,"UTF-8"));

		btn_Refresh_Click(e);

		//#CM694852
		// Initialize the radio button.
		btn_Clear_Click(e);
	}

	//#CM694853
	/**
	 * Invoke this before invoking each control event.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM694854
		//Set the screen name.
		lbl_SettingName.setResourceKey("MTLE-W0711");
		// Confirmation dialog when you press a set button MSG-9000 = Do you want to set it?
		btn_Submit.setBeforeConfirm("MSG-9000");
	}

	//#CM694855
	// Package methods -----------------------------------------------

	//#CM694856
	// Protected methods ---------------------------------------------

	//#CM694857
	// Private methods -----------------------------------------------

	//#CM694858
	// Event handler methods -----------------------------------------
	//#CM694859
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694860
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694861
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_RftNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694862
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_TerminalCategory_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694863
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_RftState_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694864
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_RftStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694865
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Worker_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694866
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694867
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_WorkerName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694868
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694869
	/**
	 * Clicking on "Close" button executes its process. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen. <BR>
	 *  <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Close_Click(ActionEvent e) throws Exception
	{
		//#CM694870
		// Return to the parent screen.
		parentRedirect(null);
	}

	//#CM694871
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694872
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694873
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694874
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694875
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694876
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694877
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694878
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694879
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694880
	/**
	 * Clicking on Setup button invokes this.
	 * <BR>
	 * Summary: Start the schedule using the contents selected via screen. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM694881
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		//#CM694882
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;
		try
		{
			SystemParameter[] startParams = new SystemParameter[1];

			startParams[0] = new SystemParameter();

			startParams[0].setWorkerCode(txt_WorkerCode.getText());
			startParams[0].setDisplayWorkerCode(lbl_WorkerCode.getText());
			startParams[0].setPassword(txt_Password.getText());
			startParams[0].setRftNo(lbl_RftNo.getText());

			if (rdo_Cancel.getChecked())
			{
				startParams[0].setWorkOnTheWay(SystemParameter.RFT_WORK_CANSEL);
			}
			else
			{
				startParams[0].setWorkOnTheWay(SystemParameter.RFT_WORK_COMFIRM);
			}

			if (rdo_Shortage.getChecked())
			{
				startParams[0].setLackAmount(SystemParameter.LACK_AMOUNT_SHORTAGE);
			}
			else
			{
				startParams[0].setLackAmount(SystemParameter.LACK_AMOUNT_REMNANT);
			}

			//#CM694883
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM694884
			// Start the schedule.
			WmsScheduler schedule = new RFTStateSCH();
			SystemParameter[] viewParam = (SystemParameter[]) schedule.startSCHgetParams(conn, startParams);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM694885
				// Process for Roll-back
				conn.rollback();

				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			else
			{
				//#CM694886
				// Process for committing.
				conn.commit();
				message.setMsgResourceKey(schedule.getMessage());
			}

			for (int i = 0; i < viewParam.length; i++)
			{
				if (lbl_RftNo.getText().equals(viewParam[i].getRftNo()))
				{
					//#CM694887
					// Set the data in the Display area.
					lbl_RftNo.setText(viewParam[i].getRftNo());
					lbl_TerminalCategory.setText(viewParam[i].getTerminalType());
					lbl_RftStatus.setText(viewParam[i].getRftStatus());
					lbl_WorkerCode.setText(viewParam[i].getWorkerCode());
					lbl_WorkerName.setText(viewParam[i].getWorkerName());

					setInputAreaView(viewParam[i].getJobType(), viewParam[i].getRftNo(), conn);
				}
				else
				{
					continue;
				}
			}
		}
		catch (Exception ex)
		{
			//#CM694888
			//Process for Roll-back
			conn.rollback();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM694889
				// Close the connection.
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM694890
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694891
	/**
	 * Clicking on Clear button invokes this.<BR>
	 * Summary: Clears the input area.<BR>
	 * <DIR>Initialize the screen.
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM694892
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		rdo_Cancel.setChecked(true);
		rdo_Commit.setChecked(false);
		rdo_Installment.setChecked(false);
		rdo_Shortage.setChecked(false);
	}

	//#CM694893
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_RftNoLabel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694894
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_TerminalCategoryLabel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694895
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_RftStatusLabel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694896
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_WorkerCodeLabel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694897
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694898
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694899
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cancel_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694900
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694901
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Commit_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694902
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Installment_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694903
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Installment_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694904
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Shortage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694905
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Shortage_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694906
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Refresh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694907
	/**
	 * Clicking on Redisplay button invokes this.<BR>
	 * Summary: Obtain the screen info again.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Refresh_Click(ActionEvent e) throws Exception
	{
		//#CM694908
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			SystemParameter param = new SystemParameter();
			//#CM694909
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM694910
			// Start the schedule.
			WmsScheduler schedule = new RFTStateSCH();
			SystemParameter[] viewParam = (SystemParameter[])schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			message.setMsgResourceKey(schedule.getMessage());

			for (int i = 0; i < viewParam.length; i++)
			{
				if (lbl_RftNo.getText().equals(viewParam[i].getRftNo()))
				{
					//#CM694911
					// Set the data in the Display area.
					lbl_RftNo.setText(viewParam[i].getRftNo());
					lbl_TerminalCategory.setText(viewParam[i].getTerminalType());
					lbl_RftStatus.setText(viewParam[i].getRftStatus());
					lbl_WorkerCode.setText(viewParam[i].getDisplayWorkerCode());
					lbl_WorkerName.setText(viewParam[i].getWorkerName());

					setInputAreaView(viewParam[i].getJobType(), viewParam[i].getRftNo(), conn);
				}
				else
				{
					continue;
				}
			}
		 }
		 catch (Exception ex)
		 {
			 message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		 }
		 finally
		 {
			 try
			 {
				 //#CM694912
				 // Close the connection.
				 if (conn != null)
					 conn.close();
			 }
			 catch (SQLException se)
			 {
				 message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			 }
		 }
	}

	//#CM694913
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694914
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_WorkOnTheWay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694915
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_LachAmount_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694916
	/**
	 * Set the screen status after setting process.
	 * @param jobType Work division
	 * @param rftNo   Terminal No.
	 * @param p_conn  Connection
	 * @throws SQLException           Announce when database access error or other error occurs.
	 * @throws ReadWriteException     Announce when error occurs on the database connection.
	 */
	protected void setInputAreaView(String jobType, String rftNo, Connection p_conn) throws SQLException, ReadWriteException
	{
		if (jobType.equals(Rft.JOB_TYPE_UNSTART))
		{
		    txt_WorkerCode.setReadOnly(true);
		    txt_Password.setReadOnly(true);
		    rdo_Cancel.setEnabled(false);
			rdo_Commit.setEnabled(false);
			rdo_Installment.setEnabled(false);
			rdo_Shortage.setEnabled(false);
			btn_Clear.setEnabled(false);
			btn_Submit.setEnabled(false);

		}
		else
		{
		    txt_WorkerCode.setReadOnly(false);
		    txt_Password.setReadOnly(false);
		    rdo_Cancel.setEnabled(true);
	        rdo_Installment.setEnabled(false);
			rdo_Shortage.setEnabled(false);
			btn_Clear.setEnabled(true);
			btn_Submit.setEnabled(true);

			if (IdDataFile.getWorkingDataId(rftNo, p_conn) == null)
		    {
	            //#CM694917
	            // Disable to submit any work with no processing data.
			    rdo_Commit.setEnabled(false);
		    }
	        else
	        {
			    rdo_Commit.setEnabled(true);

			    if (jobType.equals(Rft.JOB_TYPE_INSTOCK) || jobType.equals(Rft.JOB_TYPE_SHIPINSPECTION))
			    {
			        rdo_Installment.setEnabled(true);
					rdo_Shortage.setEnabled(true);
			    }
	        }
		}
	}
}
//#CM694918
//end of class
