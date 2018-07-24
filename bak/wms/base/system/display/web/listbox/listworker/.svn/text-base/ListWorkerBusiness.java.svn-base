// $Id: ListWorkerBusiness.java,v 1.2 2006/11/13 08:20:45 suresh Exp $

//#CM692867
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.listworker;
import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.system.display.web.listbox.listworkday.ListWorkDayBusiness;
import jp.co.daifuku.wms.base.system.display.web.listbox.sessionret.SessionWorkerResultRet;
import jp.co.daifuku.wms.base.system.display.web.listbox.sessionret.SessionWorkerRet;
import jp.co.daifuku.wms.base.system.display.web.workerqtyinquiry.WorkerQtyInquiryBusiness;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM692868
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this class to provide a Listbox for searching the "Worker".<BR>
 * Search for the data using the Worker Code<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	  Search for the data using the Worker Code entered via parent screen as a key, and display it on the screen.<BR>
 *   <BR>
 * </DIR>
 * 2.Button for the selected line(<CODE>lst_WorkerSearch_Click</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Pass the Worker Code of the selected line to the parent screen and close the listbox.<BR>
 *   <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:20:45 $
 * @author  $Author: suresh $
 */
public class ListWorkerBusiness extends ListWorker implements WMSConstants
{
	//#CM692869
	// Class fields --------------------------------------------------
	//#CM692870
	/** 
	 * Use this key to pass Worker Code.
	 */
	public static final String WORKERCODE_KEY = "WORKERCODE_KEY";

	//#CM692871
	/** 
	 * Use this key to pass Person Name.
	 */
	public static final String NAME_KEY = "NAME_KEY";

	//#CM692872
	/** 
	 * Use this key to pass phonetic transcriptions in kana.
	 */
	public static final String FURIGANA_KEY = "FURIGANA_KEY";

	//#CM692873
	/** 
	 * Use this key to pass job title.
	 */
	public static final String WORKER_JOBTYPE_KEY = "WORKER_JOBTYPE_KEY";

	//#CM692874
	/** 
	 * Use this key to pass Sex.
	 */
	public static final String SEX_KEY = "SEX_KEY";

	//#CM692875
	/** 
	 * Use this key to pass Access Privileges.
	 */
	public static final String ACCESS_AUTHORITY_KEY = "ACCESS_AUTHORITY_KEY";

	//#CM692876
	/** 
	 * Use this key to pass password
	 */
	public static final String PASSWORD_KEY = "PASSWORD_KEY";

	//#CM692877
	/** 
	 * Use this key to pass Memo 1.
	 */
	public static final String MEMO1_KEY = "MEMO1_KEY";

	//#CM692878
	/** 
	 * Use this key to pass Memo 2.
	 */
	public static final String MEMO2_KEY = "MEMO2_KEY";
	
	//#CM692879
	/** 
	 * Use this key to pass Use this key to pass the search flag.
	 */
	public static final String SEARCHWORKER_KEY = "SEARCHWORKER_KEY";

	//#CM692880
	// Class variables -----------------------------------------------
	//#CM692881
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM692882
	// Class method --------------------------------------------------

	//#CM692883
	// Constructors --------------------------------------------------

	//#CM692884
	// Public methods ------------------------------------------------

	//#CM692885
	/**
	 * Show the Initial Display. <BR>
	 * <BR>
	 *	search conditions <BR>
	 *	<DIR>
	 *		Worker Code<BR>
	 *		Start work date<BR>
	 *		End Work Date<BR>
	 *		Work type<BR>
	 *	</DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM692886
		// Set the screen name.
		//#CM692887
		// Search for a worker.
		lbl_ListName.setText(DisplayText.getText("TLE-W0017"));

		//#CM692888
		// Obtain the parameter.
		//#CM692889
		// Work type
		String jobType = request.getParameter(WorkerQtyInquiryBusiness.WORKDETAILS_KEY);
		//#CM692890
		// Start work date
		String strWorkDate = request.getParameter(ListWorkDayBusiness.STARTWORKDAY_KEY);
		//#CM692891
		// End Work Date
		String endWorkDate = request.getParameter(ListWorkDayBusiness.ENDWORKDAY_KEY);
		//#CM692892
		// Worker Code
		String workercode = request.getParameter(WORKERCODE_KEY);
		//#CM692893
		// Search flag (Plan or Result)
		String searchType = request.getParameter(SEARCHWORKER_KEY);

		viewState.setString(SEARCHWORKER_KEY, searchType);

		//#CM692894
		// Close the connection of object remained in the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM692895
			// Close the connection.
			sRet.closeConnection();
			//#CM692896
			// Delete from the session.
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM692897
		// Obtain the connection.
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM692898
		// Set the search conditions. (Work Type, Start Work Date, End Work Date, Worker Code)
		SystemParameter param = new SystemParameter();
		//#CM692899
		// Work type
		param.setSelectWorkDetail(jobType);
		//#CM692900
		// Start work date
		param.setFromWorkDate(strWorkDate);
		//#CM692901
		// End Work Date
		param.setToWorkDate(endWorkDate);
		//#CM692902
		// Worker Code
		param.setWorkerCode(workercode);

		//#CM692903
		// Determine wheter the consignor to be searched is plan info or result info.
		if(searchType.equals(SystemParameter.SEARCHFLAG_PLAN))
		{
			//#CM692904
			// Generate a SessionShippingPlanConsignorRet instance
			SessionWorkerRet listbox = new SessionWorkerRet(conn, param);
			//#CM692905
			// Maintain the list box in the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if(searchType.equals(SystemParameter.SEARCHFLAG_RESULT))
		{
			//#CM692906
			// Generate a SessionShippingResultConsignorRet instance
			SessionWorkerResultRet listbox = new SessionWorkerResultRet(conn, param);
			//#CM692907
			// Maintain the list box in the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");			
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0397");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			//#CM692908
			// 6007039={0} search failed. See log.
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}

	}

	//#CM692909
	// Package methods -----------------------------------------------

	//#CM692910
	// Protected methods ---------------------------------------------

	//#CM692911
	// Private methods -----------------------------------------------

	//#CM692912
	/**
	 * Allow this method to change a page. <BR>
	 * <BR>
	 *	Field items to be displayed<BR>
	 *	<DIR>
	 *		Worker Code<BR>
	 *	</DIR>
	 * @param listbox Session of this listbox
	 * @param actionName actionName for a clicked button
	 * @throws Exception Report all exceptions.
	 */
	private void setPlanList(SessionWorkerRet listbox, String actionName) throws Exception
	{
		//#CM692913
		// Set the Set the Page info.
		listbox.setActionName(actionName);

		//#CM692914
		// Obtain the search result.
		Worker[] worker = listbox.getEntities();
		
		int len = 0;
		if (worker != null)
			len = worker.length;
			
		if (len > 0)
		{
			//#CM692915
			// Set a value for Pager.
			//#CM692916
			// Max count
			pgr_U.setMax(listbox.getLength());
			//#CM692917
			// Count of displayed data per Page
			pgr_U.setPage(listbox.getCondition());
			//#CM692918
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM692919
			// Max count
			pgr_D.setMax(listbox.getLength());
			//#CM692920
			// Count of displayed data per Page
			pgr_D.setPage(listbox.getCondition());
			//#CM692921
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM692922
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM692923
			// Delete all lines.
			lst_WorkerSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM692924
				// Obtain the end line.
				int count = lst_WorkerSearch.getMaxRows();
				//#CM692925
				// Add a line.
				lst_WorkerSearch.addRow();

				//#CM692926
				// Move to the end line.
				lst_WorkerSearch.setCurrentRow(count);
				lst_WorkerSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_WorkerSearch.setValue(2, worker[i].getWorkerCode());
				lst_WorkerSearch.setValue(3, worker[i].getName());

				//#CM692927
				// Hidden field items (Phonetic transcriptions in kana, Job Title, Sex, Access Privileges, Password, Memo 1, and Memo 2)
				ArrayList list = new ArrayList(7);
				
				list.add(0, worker[i].getFurigana());
				if(worker[i].getWorkerJobType().equals(Worker.JOB_TYPE_ADMINISTRATOR))
				{
					list.add(1, SystemParameter.SELECTWORKERJOBTYPE_ADMINISTRATOR);
				}
				else if(worker[i].getWorkerJobType().equals(Worker.JOB_TYPE_WORKER))
				{
					list.add(1, SystemParameter.SELECTWORKERJOBTYPE_WORKER);
				}
				
				if(worker[i].getSex().equals(Worker.MALE))
				{
					list.add(2, SystemParameter.SELECTSEX_MALE);
				}
				else if(worker[i].getSex().equals(Worker.FEMALE))
				{
					list.add(2, SystemParameter.SELECTSEX_FEMALE);
				}
				
				if(worker[i].getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_SYSTEMADMINISTRATOR))
				{
					list.add(3, SystemParameter.SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR);
				}
				else if(worker[i].getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_ADMINISTRATOR))
				{
					list.add(3, SystemParameter.SELECTACCESSAUTHORITY_ADMINISTRATOR);
				}
				else if(worker[i].getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_WORKER))
				{
					list.add(3, SystemParameter.SELECTACCESSAUTHORITY_WORKER);
				}
				
				list.add(4, worker[i].getPassword());
				list.add(5, worker[i].getMemo1());
				list.add(6, worker[i].getMemo2());
				String param = CollectionUtils.getConnectedString(list);

				//#CM692928
				// Maintain the combined parameters in ListCell.
				lst_WorkerSearch.setValue(0, param);
			}
		}
		else
		{
			//#CM692929
			// Set a value for Pager.
			//#CM692930
			// Max count
			pgr_U.setMax(0);
			//#CM692931
			// Count of displayed data per Page
			pgr_U.setPage(0);
			//#CM692932
			// Start Position
			pgr_U.setIndex(0);
			//#CM692933
			// Max count
			pgr_D.setMax(0);
			//#CM692934
			// Count of displayed data per Page
			pgr_D.setPage(0);
			//#CM692935
			// Start Position
			pgr_D.setIndex(0);

			//#CM692936
			// Check the count of the search results.
			String errorMsg = listbox.checkLength();
			//#CM692937
			// Hide the header.
			lst_WorkerSearch.setVisible(false);
			//#CM692938
			// Display the error message.
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM692939
	/**
	 * Allow this method to change a page. <BR>
	 * <BR>
	 *	Field items to be displayed<BR>
	 *	<DIR>
	 *		Worker Code<BR>
	 *	</DIR>
	 * @param listbox Session of this listbox
	 * @param actionName actionName for a clicked button
	 * @throws Exception Report all exceptions.
	 */
	private void setResultList(SessionWorkerResultRet listbox, String actionName) throws Exception
	{
		//#CM692940
		// Set the Set the Page info.
		listbox.setActionName(actionName);

		//#CM692941
		// Obtain the search result.
		WorkerResult[] worker = listbox.getEntities();
		
		int len = 0;
		if (worker != null)
			len = worker.length;
			
		if (len > 0)
		{
			//#CM692942
			// Set a value for Pager.
			//#CM692943
			// Max count
			pgr_U.setMax(listbox.getLength());
			//#CM692944
			// Count of displayed data per Page
			pgr_U.setPage(listbox.getCondition());
			//#CM692945
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM692946
			// Max count
			pgr_D.setMax(listbox.getLength());
			//#CM692947
			// Count of displayed data per Page
			pgr_D.setPage(listbox.getCondition());
			//#CM692948
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM692949
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM692950
			// Delete all lines.
			lst_WorkerSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM692951
				// Obtain the end line.
				int count = lst_WorkerSearch.getMaxRows();
				//#CM692952
				// Add a line.
				lst_WorkerSearch.addRow();

				//#CM692953
				// Move to the end line.
				lst_WorkerSearch.setCurrentRow(count);
				lst_WorkerSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_WorkerSearch.setValue(2, worker[i].getWorkerCode());
				lst_WorkerSearch.setValue(3, worker[i].getWorkerName());

			}
		}
		else
		{
			//#CM692954
			// Set a value for Pager.
			//#CM692955
			// Max count
			pgr_U.setMax(0);
			//#CM692956
			// Count of displayed data per Page
			pgr_U.setPage(0);
			//#CM692957
			// Start Position
			pgr_U.setIndex(0);
			//#CM692958
			// Max count
			pgr_D.setMax(0);
			//#CM692959
			// Count of displayed data per Page
			pgr_D.setPage(0);
			//#CM692960
			// Start Position
			pgr_D.setIndex(0);

			//#CM692961
			// Check the count of the search results.
			String errorMsg = listbox.checkLength();
			//#CM692962
			// Hide the header.
			lst_WorkerSearch.setVisible(false);
			//#CM692963
			// Display the error message.
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM692964
	// Event handler methods -----------------------------------------

	//#CM692965
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692967
	/** 
	 * Clicking on "Close" button executes its process. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen. <BR>
	 *  <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM692968
	/** 
	 * Execute the process defined for clicking on ">" button. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM692969
	/** 
	 * Execute the process defined for clicking on "<" button. <BR>
	 * <BR>
	 * Display the previous one page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM692970
	/** 
	 * Clicking ">>" button. <BR>
	 * <BR>
	 * Display the end page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM692971
	/** 
	 * Execute the process defined for clicking on "<<" button. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM692972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692973
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkerSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692974
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkerSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692975
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkerSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM692976
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkerSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM692977
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkerSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692978
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkerSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692979
	/** 
	 * Execute the process defined for clicking on Select List Cell button. <BR>
	 * Pass the Worker Code to the parent screen and close the listbox. <BR>
	 * <BR>
	 *	Field items to be displayed <BR>
	 *	<DIR>
	 *	  Worker Code<BR>
	 *	  Person Name<BR>
	 *	  Phonetic transcriptions in kana<BR>
	 *	  Job Title<BR>
	 *	  Sex<BR>
	 *	  Access Privileges<BR>
	 *    Password<BR>
	 *	  Memo 1<BR>
	 *	  Memo 2<BR>
	 *	</DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void lst_WorkerSearch_Click(ActionEvent e) throws Exception
	{
		//#CM692980
		// Set the current line.
		int count = lst_WorkerSearch.getActiveRow();
		lst_WorkerSearch.setCurrentRow(count);
		lst_WorkerSearch.getValue(1);

		//#CM692981
		// Obtain the combined parameters from the list cell.
		String buf = lst_WorkerSearch.getValue(0);

		//#CM692982
		// Set the Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		//#CM692983
		// Worker Code
		param.setParameter(WORKERCODE_KEY, lst_WorkerSearch.getValue(2));
		//#CM692984
		// Person Name
		param.setParameter(NAME_KEY, lst_WorkerSearch.getValue(3));
		//#CM692985
		// Phonetic transcriptions in kana
		param.setParameter(FURIGANA_KEY, CollectionUtils.getString(0, buf));
		//#CM692986
		// Job Title
		param.setParameter(WORKER_JOBTYPE_KEY, CollectionUtils.getString(1, buf));
		//#CM692987
		// Sex
		param.setParameter(SEX_KEY, CollectionUtils.getString(2, buf));
		//#CM692988
		// Access Privileges
		param.setParameter(ACCESS_AUTHORITY_KEY, CollectionUtils.getString(3, buf));
		//#CM692989
		// Password
		param.setParameter(PASSWORD_KEY, CollectionUtils.getString(4, buf));
		//#CM692990
		// Memo 1
		param.setParameter(MEMO1_KEY, CollectionUtils.getString(5, buf));
		//#CM692991
		// Memo 2
		param.setParameter(MEMO2_KEY, CollectionUtils.getString(6, buf));

		//#CM692992
		// Shift to the parent screen.
		parentRedirect(param);
	}

	//#CM692993
	/** 
	 * Execute the process defined for clicking on ">" button. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM692994
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHWORKER_KEY);
		
		if(flag.equals(SystemParameter.SEARCHFLAG_PLAN))
		{
			//#CM692995
			// Maintain the list box in the Session
			SessionWorkerRet listbox = (SessionWorkerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if(flag.equals(SystemParameter.SEARCHFLAG_RESULT))
		{
			//#CM692996
			// Maintain the list box in the Session
			SessionWorkerResultRet listbox = (SessionWorkerResultRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
	}

	//#CM692997
	/** 
	 * Execute the process defined for clicking on "<" button. <BR>
	 * <BR>
	 * Display the previous one page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM692998
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHWORKER_KEY);
		
		if(flag.equals(SystemParameter.SEARCHFLAG_PLAN))
		{
			//#CM692999
			// Maintain the list box in the Session
			SessionWorkerRet listbox = (SessionWorkerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if(flag.equals(SystemParameter.SEARCHFLAG_RESULT))
		{
			//#CM693000
			// Maintain the list box in the Session
			SessionWorkerResultRet listbox = (SessionWorkerResultRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
	}

	//#CM693001
	/** 
	 * Clicking ">>" button. <BR>
	 * <BR>
	 * Display the end page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM693002
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHWORKER_KEY);
		
		if(flag.equals(SystemParameter.SEARCHFLAG_PLAN))
		{
			//#CM693003
			// Maintain the list box in the Session
			SessionWorkerRet listbox = (SessionWorkerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if(flag.equals(SystemParameter.SEARCHFLAG_RESULT))
		{
			//#CM693004
			// Maintain the list box in the Session
			SessionWorkerResultRet listbox = (SessionWorkerResultRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
	}

	//#CM693005
	/** 
	 * Execute the process defined for clicking on "<<" button. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM693006
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHWORKER_KEY);
		
		if(flag.equals(SystemParameter.SEARCHFLAG_PLAN))
		{
			//#CM693007
			// Maintain the list box in the Session
			SessionWorkerRet listbox = (SessionWorkerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if(flag.equals(SystemParameter.SEARCHFLAG_RESULT))
		{
			//#CM693008
			// Maintain the list box in the Session
			SessionWorkerResultRet listbox = (SessionWorkerResultRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}
	}

	//#CM693009
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693010
	/** 
	 * Clicking on "Close" button executes its process. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen. <BR>
	 *  <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM693011
		// Maintain the list box in the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM693012
		// If there is any value in the Session:
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM693013
				// Close the statement.
				finder.close();
			}
			//#CM693014
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM693015
		// Delete from the session.
		this.getSession().removeAttribute("LISTBOX");
		//#CM693016
		// Return to the parent screen.
		parentRedirect(null);
	}
}
//#CM693017
//end of class
