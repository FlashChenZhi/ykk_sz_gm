// $Id: ListAsLocationDetailStatusListBusiness.java,v 1.2 2006/10/26 05:30:58 suresh Exp $

//#CM38950
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocationdetailstatuslist;
import java.sql.Connection;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsLocationDetailRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM38951
/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * The stock list list box class according to location no.<BR>
 * Make shelf status and WarehouseStationNo input from the parents screen as a key, retrieve, and display it on the screen.<BR>
 * Process it in this class as follows. <BR>
 * 
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Make shelf status and WarehouseStationNo input from the parents screen as a key, retrieve, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_WLocationDetail_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass Location No of the selection line to the parents screen, and close the list box.<BR>
 * <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/06</TD><TD>C.Kaminishizono</TD><TD>new</TD></TR>
 * <TR><TD>2006/01/17</TD><TD>Y.Okamura</TD><TD>Composition change</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:30:58 $
 * @author  $Author: suresh $
 */
public class ListAsLocationDetailStatusListBusiness extends ListAsLocationDetailStatusList implements WMSConstants
{
	//#CM38952
	// Class fields --------------------------------------------------

	//#CM38953
	/** 
	 * key to transfer warehouse
	 */
	public static final String WAREHOUSE_KEY = "WAREHOUSE_KEY";

	//#CM38954
	/** 
	 * The key used to hand over Warehouse description (For display).
	 */
	public static final String WAREHOUSENAME_KEY = "WAREHOUSENAME_KEY";

	//#CM38955
	/** 
	 * The key used to hand over location status. 
	 */
	public static final String LOCATIONSTATUS_KEY = "LOCATIONSTATUS_KEY";

	//#CM38956
	/** 
	 * key to transfer location no
	 */
	public static final String LOCATION_KEY = "LOCATION_KEY";

	//#CM38957
	/**
	 * type of date format:Edit date(Example:YYYY/MM/DD)
	 */
	protected final int DATE_DISPTYPE_DATE = 0;
	
	//#CM38958
	/**
	 * type of date format:Edit time(Example:hh:mm:ss)
	 */
	protected final int DATE_DISPTYPE_TIME = 1;
	
	//#CM38959
	// Class variables -----------------------------------------------

	//#CM38960
	// Class method --------------------------------------------------

	//#CM38961
	// Constructors --------------------------------------------------

	//#CM38962
	// Public methods ------------------------------------------------

	//#CM38963
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM38964
		// set screen name
		//#CM38965
		// search location
		lbl_ListName.setText(DisplayText.getText("TLE-W0913"));

		//#CM38966
		// fetch parameter
		//#CM38967
		// warehouse No.
		String warehouse = request.getParameter(WAREHOUSE_KEY);
		String warehouseName = request.getParameter(WAREHOUSENAME_KEY);
		//#CM38968
		// location status
		String[] status = request.getParameterValues(LOCATIONSTATUS_KEY);

		//#CM38969
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM38970
		// set header item other than listcell
		//#CM38971
		// warehouse name
		lbl_JavaSetWareHouse.setText(warehouseName);
		String wStatusDisp = "";
		if (status != null)
		{
			//#CM38972
			// location status
			for (int lc=0; lc<status.length; lc++)
			{
				if (lc != 0)
				{
					wStatusDisp = wStatusDisp + ",";
				}
				wStatusDisp = wStatusDisp + getStatusName(status[lc]);
			}
		}
		lbl_JavaSetStatus.setText(wStatusDisp);

		//#CM38973
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM38974
			// close connection
			sRet.closeConnection();
			//#CM38975
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM38976
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM38977
		// warehouse No
		param.setWareHouseNo(warehouse);
		//#CM38978
		// location status
		param.setSearchStatus(status);
		
		//#CM38979
		// create SessionLocationRet instance
		SessionAsLocationDetailRet listbox = new SessionAsLocationDetailRet(conn, param);
		//#CM38980
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM38981
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM38982
	// Package methods -----------------------------------------------

	//#CM38983
	// Protected methods ---------------------------------------------
	//#CM38984
	/**
	 * Set a numeric item in the list cell. 
	 * @param columnNo line no. of item to be set
	 * @param value value to be set
	 */
	protected void setList(int columnNo, int value)
	{
		setList(columnNo, WmsFormatter.getNumFormat(value));
	}

	//#CM38985
	/**
	 * Set the date item in the list cell. 
	 * @param columnNo line no. of item to be set
	 * @param value value to be set
	 * @param locale Locale
	 * @param dispType type of date format
	 */
	protected void setList(int columnNo, Date value, Locale locale, int dispType)
	{
		//#CM38986
		// edit date
		if (dispType == DATE_DISPTYPE_DATE)
		{
			setList(columnNo, WmsFormatter.toDispDate(WmsFormatter.toParamDate(value), locale));
		}
		//#CM38987
		// edit time
		else if (dispType == DATE_DISPTYPE_TIME)
		{
			setList(columnNo, WmsFormatter.getTimeFormat(value, ""));

		}
	}
	
	//#CM38988
	/**
	 * Set the character string item in the list cell. 
	 * @param columnNo line no. of item to be set
	 * @param value value to be set
	 */
	protected void setList(int columnNo, String value)
	{
		lst_WLocationDetail.setValue(columnNo, value);
	}
	
	//#CM38989
	/**
	 * Method which acquires Location status description  <BR>
	 * @param location status
	 * @return Location status description
	 * @throws Exception report all the exceptions
	 */
	protected String getStatusName(String p_Status) throws Exception
	{
		String r_DispName = "";
		
		//#CM38990
		// result location
		if (p_Status.equals(Integer.toString(AsScheduleParameter.STATUS_STORAGED)))
		{
			r_DispName = DisplayText.getText("LBL-A0086");
		}
		//#CM38991
		// empty location
		else if (p_Status.equals(Integer.toString(AsScheduleParameter.STATUS_EMPTY)))
		{
			r_DispName = DisplayText.getText("LBL-A0081");
		}
		//#CM38992
		// Error location
		else if (p_Status.equals(Integer.toString(AsScheduleParameter.STATUS_IRREGULAR)))
		{
			r_DispName = DisplayText.getText("LBL-A0087");
		}
		//#CM38993
		// Empty Palette
		else if (p_Status.equals(Integer.toString(AsScheduleParameter.STATUS_EMPTYPALETTE)))
		{
			r_DispName = DisplayText.getText("LBL-A0091");
		}
		//#CM38994
		// restricted location
		else if (p_Status.equals(Integer.toString(AsScheduleParameter.STATUS_UNAVAILABLE)))
		{
			r_DispName = DisplayText.getText("LBL-A0088");
		}
		
		return r_DispName;
	}


	//#CM38995
	// Private methods -----------------------------------------------
	//#CM38996
	/**
	 * method to change page <BR>
	 * Retrieve the table.<BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsLocationDetailRet listbox, String actionName) throws Exception
	{
		//#CM38997
		// set page info
		listbox.setActionName(actionName);

		//#CM38998
		// fetch search result
		AsScheduleParameter[] param = listbox.getEntities();
		int len = 0;
		if (param != null)
			len = param.length;
		if (len > 0)
		{
			//#CM38999
			// set value to Pager
			//#CM39000
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM39001
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM39002
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM39003
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM39004
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM39005
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM39006
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM39007
			// delete all rows
			lst_WLocationDetail.clearRow();

			//#CM39008
			// used in tool tip
			String consinogName = DisplayText.getText("LBL-W0026");
			String itemName = DisplayText.getText("LBL-W0103");
			String instockDay = DisplayText.getText("LBL-W0237");
			String instockTime = DisplayText.getText("LBL-W0368");
			String useByDate = DisplayText.getText("LBL-W0270");

			//#CM39009
			// used in date display
			Locale locale = this.getHttpRequest().getLocale();

			for (int i = 0; i < len; i++)
			{
				//#CM39010
				// fetch last row
				int count = lst_WLocationDetail.getMaxRows();
				//#CM39011
				// add row
				lst_WLocationDetail.addRow();

				//#CM39012
				// move to last record
				lst_WLocationDetail.setCurrentRow(count);
				//#CM39013
				// HIDDEN
				setList(0, param[i].getLocationNo());
				//#CM39014
				// first row
				setList(1, Integer.toString(count + listbox.getCurrent()));
				setList(2, DisplayText.formatDispLocation(param[i].getLocationNo()));
				setList(3, param[i].getLocationStatusName());
				if (param[i].getEnteringQty() * param[i].getStockCaseQty() + param[i].getStockPieceQty() != 0)
				{
					setList(4, param[i].getConsignorCode());
					setList(5, param[i].getItemCode());
					setList(6, param[i].getEnteringQty());
					setList(7, param[i].getStockCaseQty());
					setList(8, param[i].getITF());
					setList(9, param[i].getCasePieceFlagNameDisp());
					setList(10, param[i].getInStockDate(), locale, DATE_DISPTYPE_DATE);
					setList(11, param[i].getUseByDate());
					//#CM39015
					// second row
					setList(12, param[i].getConsignorName());
					setList(13, param[i].getItemName());
					setList(14, param[i].getBundleEnteringQty());
					setList(15, param[i].getStockPieceQty());
					setList(16, param[i].getBundleITF());
					setList(17, param[i].getInStockDate(), locale, DATE_DISPTYPE_TIME);
			
					//#CM39016
					// add data to tool tip
					ToolTipHelper toolTip = new ToolTipHelper();
					toolTip.add(consinogName, param[i].getConsignorName());
					toolTip.add(itemName, param[i].getItemName());
					toolTip.add(instockDay, lst_WLocationDetail.getValue(10));
					toolTip.add(instockTime, lst_WLocationDetail.getValue(17));
					toolTip.add(useByDate, param[i].getUseByDate());
			
					//#CM39017
					// set tool tip	
					lst_WLocationDetail.setToolTip(count, toolTip.getText());
				}
			}
		}
		else
		{
			//#CM39018
			// set value to Pager
			//#CM39019
			// max. number
			pgr_U.setMax(0);
			//#CM39020
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM39021
			// start position
			pgr_U.setIndex(0);
			//#CM39022
			// max. number
			pgr_D.setMax(0);
			//#CM39023
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM39024
			// start position
			pgr_D.setIndex(0);

			//#CM39025
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM39026
			// hide the header
			lst_WLocationDetail.setVisible(false);
			//#CM39027
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM39028
	// Event handler methods -----------------------------------------
	//#CM39029
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

	//#CM39030
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

	//#CM39031
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

	//#CM39032
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

	//#CM39033
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

	//#CM39034
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	Pass location no to the parents screen, and close the list box.<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_WLocationDetail_Click(ActionEvent e) throws Exception
	{
		//#CM39035
		// set the current row
		lst_WLocationDetail.setCurrentRow(lst_WLocationDetail.getActiveRow());

		//#CM39036
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM39037
		// location no.
		param.setParameter(LOCATION_KEY, lst_WLocationDetail.getValue(0));

		//#CM39038
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM39039
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
		//#CM39040
		//save the listbox in session
		SessionAsLocationDetailRet listbox = (SessionAsLocationDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM39041
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
		//#CM39042
		//save the listbox in session
		SessionAsLocationDetailRet listbox = (SessionAsLocationDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM39043
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
		//#CM39044
		//save the listbox in session
		SessionAsLocationDetailRet listbox = (SessionAsLocationDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM39045
	/** 
	 * call the [<<] button click event process<BR>
	 * <BR>
	 *display the first page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM39046
		//save the listbox in session
		SessionAsLocationDetailRet listbox = (SessionAsLocationDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM39047
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
		//#CM39048
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM39049
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM39050
				// close the statement object
				finder.close();
			}
			//#CM39051
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM39052
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM39053
		// return to origin screen
		parentRedirect(null);
	}

}
//#CM39054
//end of class
