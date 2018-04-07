// $Id: ListAsStoragePlanDateBusiness.java,v 1.2 2006/10/26 05:37:23 suresh Exp $

//#CM39940
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstorageplandate;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsStoragePlanDateRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM39941
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * The Storage Plan Date retrieval list box class. <BR>
 * Retrieve it based on Consignor code and Storage Plan Date input from the parents screen. <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Make Consignor code and the receipt of arrival of goods day input from the parents screen a key, retrieve, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_InstkAcceptDateSrch_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass Day of Storage acceptance of the selection line to the parents screen, and close the list box.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/28</TD><TD>M.Koyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:37:23 $
 * @author  $Author: suresh $
 */
public class ListAsStoragePlanDateBusiness extends ListAsStoragePlanDate implements WMSConstants
{
	//#CM39942
	// Class fields --------------------------------------------------
	//#CM39943
	/** 
	 * this key is used to transfer storage plan date
	 */
	public static final String PLANDATE_KEY = "PLANDATE_KEY";

	//#CM39944
	// Class variables -----------------------------------------------

	//#CM39945
	// Class method --------------------------------------------------

	//#CM39946
	// Constructors --------------------------------------------------

	//#CM39947
	// Public methods ------------------------------------------------

	//#CM39948
	/**
	 * initial screen display<BR>
	 * <DIR>
	 *	item <BR>
	 *	<DIR>
	 *		select <BR>
	 *		Day of Storage acceptance <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM39949
		// set screen name
		//#CM39950
		// search storage plan date
		lbl_ListName.setText(DisplayText.getText("TLE-W0067"));

		//#CM39951
		// fetch parameter
		//#CM39952
		// consignor code
		String consignorcode = request.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM39953
		// storage plan date
		String wplandate = request.getParameter(PLANDATE_KEY);

		//#CM39954
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM39955
			//close connection
			sRet.closeConnection();
			//#CM39956
			//delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM39957
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM39958
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM39959
		// consignor code
		param.setConsignorCode(consignorcode);
		//#CM39960
		// storage plan date
		param.setPlanDate(wplandate);

		//#CM39961
		// SessionInstockReceiveResultDateRet instance generation
		SessionAsStoragePlanDateRet listbox = new SessionAsStoragePlanDateRet(conn, param);
		//#CM39962
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM39963
	// Package methods -----------------------------------------------

	//#CM39964
	// Protected methods ---------------------------------------------

	//#CM39965
	// Private methods -----------------------------------------------

	//#CM39966
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsStoragePlanDateRet listbox, String actionName)
	{
		//#CM39967
		// fetch locale
		Locale locale = this.getHttpRequest().getLocale();
		
		//#CM39968
		// set page info
		listbox.setActionName(actionName);
		
		//#CM39969
		// fetch search result
		AsScheduleParameter[] splan = (AsScheduleParameter[]) listbox.getEntities();
		int len = 0;
		if (splan != null)
			len = splan.length;
			if (len > 0)
			{
				//#CM39970
				// set value to Pager
				//#CM39971
				// max. number
				pgr_U.setMax(listbox.getLength());
				//#CM39972
				// variables for 1 Page display
				pgr_U.setPage(listbox.getCondition());
				//#CM39973
				// start position
				pgr_U.setIndex(listbox.getCurrent() + 1);
				//#CM39974
				// max. number
				pgr_D.setMax(listbox.getLength());
				//#CM39975
				// variables for 1 Page display
				pgr_D.setPage(listbox.getCondition());
				//#CM39976
				// start position
				pgr_D.setIndex(listbox.getCurrent() + 1);
				
				//#CM39977
				// hide message
				lbl_InMsg.setVisible(false);
				
				//#CM39978
				// delete all rows
				lst_InstkAcceptDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM39979
				// fetch last row
				int count = lst_InstkAcceptDateSrch.getMaxRows();
				//#CM39980
				// add row
				lst_InstkAcceptDateSrch.addRow();
	
				//#CM39981
				// move to last record
				lst_InstkAcceptDateSrch.setCurrentRow(count);
				lst_InstkAcceptDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_InstkAcceptDateSrch.setValue(2, WmsFormatter.toDispDate(splan[i].getPlanDate(), locale));
			}
		}
		else
		{
			//#CM39982
			// set value to Pager
			//#CM39983
			// max. number
			pgr_U.setMax(0);
			//#CM39984
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM39985
			// start position
			pgr_U.setIndex(0);
			//#CM39986
			// max. number
			pgr_D.setMax(0);
			//#CM39987
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM39988
			// start position
			pgr_D.setIndex(0);
	
			//#CM39989
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM39990
			// hide the header
			lst_InstkAcceptDateSrch.setVisible(false);
			//#CM39991
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
		
	}

	//#CM39992
	// Event handler methods -----------------------------------------
	//#CM39993
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39994
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39995
	/** 
	 * call the [Close] button click event process <BR>
	 *  <BR>
	 * close the listbox and return to the caller screen<BR>
	 *  <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM39996
	/** 
	 * call the [>] button click event process <BR>
	 * <BR>
	 * display the next page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM39997
	/** 
	 * call the [<] button click event process <BR>
	 * <BR>
	 * display the previous page<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM39998
	/** 
	 * call the [>>] button click event process <BR>
	 * <BR>
	 * display the last page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM39999
	/** 
	 * call the [<<] button click event process<BR>
	 * <BR>
	 *display the first page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}


	//#CM40000
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40001
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM40002
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM40003
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM40004
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM40005
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40006
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM40007
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	Pass the Storage acceptance day to the parents screen, and close the list box. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_InstkAcceptDateSrch_Click(ActionEvent e) throws Exception
	{
		//#CM40008
		// set the current row
		lst_InstkAcceptDateSrch.setCurrentRow(lst_InstkAcceptDateSrch.getActiveRow());
		lst_InstkAcceptDateSrch.getValue(1);

		//#CM40009
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM40010
		// storage plan date
		param.setParameter(PLANDATE_KEY, lst_InstkAcceptDateSrch.getValue(2));
		//#CM40011
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM40012
	/** 
	 * call the [>] button click event process <BR>
	 * <BR>
	 * display the next page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM40013
		//save the listbox in session
		SessionAsStoragePlanDateRet listbox = (SessionAsStoragePlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM40014
	/** 
	 * call the [<] button click event process <BR>
	 * <BR>
	 * display the previous page<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM40015
		//save the listbox in session
		SessionAsStoragePlanDateRet listbox = (SessionAsStoragePlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM40016
	/** 
	 * call the [>>] button click event process <BR>
	 * <BR>
	 * display the last page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM40017
		//save the listbox in session
		SessionAsStoragePlanDateRet listbox = (SessionAsStoragePlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM40018
	/** 
	 * call the [<<] button click event process <BR>
	 * <BR>
	 *display the first page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM40019
		//save the listbox in session
		SessionAsStoragePlanDateRet listbox = (SessionAsStoragePlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}


	//#CM40020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40021
	/** 
	 * call the [Close] button click event process <BR>
	 *  <BR>
	 * close the listbox and return to the caller screen<BR>
	 *  <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM40022
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM40023
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM40024
				// close the statement object
				finder.close();
			}
			//#CM40025
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM40026
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM40027
		// return to origin screen
		parentRedirect(null);
	}


}
//#CM40028
//end of class
