// $Id: ListAsStockDetailNoBtnBusiness.java,v 1.2 2006/10/26 05:35:36 suresh Exp $

//#CM39688
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetailnobtn;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsStockDetailRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM39689
/**
 * <FONT COLOR="BLUE">
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * The stock list information display class. <BR>
 * Retrieve stock info based on location no(stationNo) input from the parents screen. <BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Retrieve it based on location no(stationNo) input from the parents screen. <BR>
 * <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/31</TD><TD>M.Koyama</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:35:36 $
 * @author  $Author: suresh $
 */
public class ListAsStockDetailNoBtnBusiness extends ListAsStockDetailNoBtn implements WMSConstants
{
	//#CM39690
	// Class fields --------------------------------------------------
	//#CM39691
	/** 
	 * key to transfer warehouse
	 */
	public static final String WAREHOUSE_KEY = "WAREHOUSE_KEY";

	//#CM39692
	/** 
	 * key to transfer warehouse name
	 */
	public static final String WAREHOUSENAME_KEY = "WAREHOUSENAME_KEY";

	//#CM39693
	/** 
	 * key to transfer location no
	 */
	public static final String LOCATION_KEY = "LOCATION_KEY";

	//#CM39694
	// Class variables -----------------------------------------------

	//#CM39695
	// Class method --------------------------------------------------

	//#CM39696
	// Constructors --------------------------------------------------

	//#CM39697
	// Public methods ------------------------------------------------

	//#CM39698
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM39699
		// set screen name
		//#CM39700
		//search stock list
		lbl_ListName.setText(DisplayText.getText("TLE-W0905"));

		//#CM39701
		// Set the screen size, and display in the center. 
		setResize(1023,638);
		
		//#CM39702
		// fetch parameter
		//#CM39703
		// warehouse station no.
		String warehouse = request.getParameter(WAREHOUSE_KEY);
		//#CM39704
		// warehouse name
		String whName = request.getParameter(WAREHOUSENAME_KEY);
		//#CM39705
		// location no.
		String locationno = request.getParameter(LOCATION_KEY);

		//#CM39706
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM39707
		// set header item other than listcell
		//#CM39708
		// warehouse name
		if (StringUtil.isBlank(whName))
		{
			ASFindUtil util = new ASFindUtil(conn);
			lbl_JavaSetWareHouse.setText(util.getWareHouseName(warehouse));
		}
		else
		{
			lbl_JavaSetWareHouse.setText(whName);
		}
		//#CM39709
		// location no.
		lbl_JavaSetLocationNo.setText(DisplayText.formatDispLocation(locationno));

		//#CM39710
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM39711
			// close connection
			sRet.closeConnection();
			//#CM39712
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM39713
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM39714
		// warehouse station no.
		param.setWareHouseNo(warehouse);
		//#CM39715
		// Location No (stationNo.)
		param.setLocationNo(locationno);

		//#CM39716
		// generate SessionRet instance
		SessionAsStockDetailRet listbox = new SessionAsStockDetailRet(conn, param);
		//#CM39717
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM39718
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM39719
	// Package methods -----------------------------------------------
	//#CM39720
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsStockDetailRet listbox, String actionName)
	{
		//#CM39721
		// set page info
		listbox.setActionName(actionName);

		//#CM39722
		// fetch search result
		AsScheduleParameter[] rparam = (AsScheduleParameter[])listbox.getEntities();

		int len = 0;
		if (rparam != null)
			len = rparam.length;
		if (len > 0)
		{
			//#CM39723
			// set value in pager
			//#CM39724
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM39725
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM39726
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM39727
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM39728
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM39729
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM39730
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM39731
			// delete all rows
			lst_LocationDetilNoBtn.clearRow();

			//#CM39732
			// used in tool tip
			String title_ConsignorName = DisplayText.getText("LBL-W0026");
			String title_ItemName = DisplayText.getText("LBL-W0103");
			String title_StorageDate = DisplayText.getText("LBL-W0237");
			String title_StorageTime = DisplayText.getText("LBL-W0368");
			String title_UseByDate = DisplayText.getText("LBL-W0270");

			//#CM39733
			// used in date display
			Locale locale = this.getHttpRequest().getLocale();

			for (int i = 0; i < len; i++)
			{
				//#CM39734
				// fetch last row
				int count = lst_LocationDetilNoBtn.getMaxRows();
				//#CM39735
				// add row
				lst_LocationDetilNoBtn.addRow();

				//#CM39736
				// move to last record
				lst_LocationDetilNoBtn.setCurrentRow(count);
				lst_LocationDetilNoBtn.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_LocationDetilNoBtn.setValue(2, rparam[i].getConsignorCode());
				lst_LocationDetilNoBtn.setValue(3, rparam[i].getItemCode());
				lst_LocationDetilNoBtn.setValue(4, WmsFormatter.getNumFormat(rparam[i].getEnteringQty()));
				lst_LocationDetilNoBtn.setValue(5, WmsFormatter.getNumFormat(rparam[i].getStockCaseQty())); 
				lst_LocationDetilNoBtn.setValue(6, rparam[i].getITF());
				lst_LocationDetilNoBtn.setValue(7, DisplayUtil.getPieceCaseValue(rparam[i].getCasePieceFlag()));
				lst_LocationDetilNoBtn.setValue(8, WmsFormatter.toDispDate(WmsFormatter.toParamDate(rparam[i].getInStockDate()), locale));
				lst_LocationDetilNoBtn.setValue(9, rparam[i].getUseByDate());
				lst_LocationDetilNoBtn.setValue(10, rparam[i].getConsignorName());
				lst_LocationDetilNoBtn.setValue(11, rparam[i].getItemName());
				lst_LocationDetilNoBtn.setValue(12, WmsFormatter.getNumFormat(rparam[i].getBundleEnteringQty()));
				lst_LocationDetilNoBtn.setValue(13, WmsFormatter.getNumFormat(rparam[i].getStockPieceQty())); 
				lst_LocationDetilNoBtn.setValue(14, rparam[i].getBundleITF());
				lst_LocationDetilNoBtn.setValue(15, WmsFormatter.getTimeFormat(rparam[i].getInStockDate(), ""));

				//#CM39737
				// add data to tool tip
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_ConsignorName, rparam[i].getConsignorName());
				toolTip.add(title_ItemName, rparam[i].getItemName());
				toolTip.add(title_StorageDate, WmsFormatter.getDateFormat(rparam[i].getInStockDate(), ""));
				toolTip.add(title_StorageTime, WmsFormatter.getTimeFormat(rparam[i].getInStockDate(), ""));
				toolTip.add(title_UseByDate, rparam[i].getUseByDate());

				//#CM39738
				// set tool tip	
				lst_LocationDetilNoBtn.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM39739
			// set value to Pager
			//#CM39740
			// max. number
			pgr_U.setMax(0);
			//#CM39741
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM39742
			// start position
			pgr_U.setIndex(0);
			//#CM39743
			// max. number
			pgr_D.setMax(0);
			//#CM39744
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM39745
			// start position
			pgr_D.setIndex(0);

			//#CM39746
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM39747
			// hide the header
			lst_LocationDetilNoBtn.setVisible(false);
			//#CM39748
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM39749
	// Protected methods ---------------------------------------------

	//#CM39750
	// Private methods -----------------------------------------------

	//#CM39751
	// Event handler methods -----------------------------------------
	//#CM39752
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetLocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39759
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

	//#CM39760
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

	//#CM39761
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

	//#CM39762
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

	//#CM39763
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

	//#CM39764
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetilNoBtn_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetilNoBtn_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetilNoBtn_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM39768
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetilNoBtn_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM39769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetilNoBtn_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39770
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetilNoBtn_Change(ActionEvent e) throws Exception
	{
	}

	//#CM39771
	/** 
	 * call the button click event process of list cell.
	 * 
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_LocationDetilNoBtn_Click(ActionEvent e) throws Exception
	{

		btn_Close_D_Click(e);
	}

	//#CM39772
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
		//#CM39773
		//save the listbox in session
		SessionAsStockDetailRet listbox = (SessionAsStockDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM39774
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
		//#CM39775
		//save the listbox in session
		SessionAsStockDetailRet listbox = (SessionAsStockDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM39776
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
		//#CM39777
		//save the listbox in session
		SessionAsStockDetailRet listbox = (SessionAsStockDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM39778
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
		//#CM39779
		//save the listbox in session
		SessionAsStockDetailRet listbox = (SessionAsStockDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM39780
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39781
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

		//#CM39782
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM39783
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM39784
				// close the statement object
				finder.close();
			}
			//#CM39785
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM39786
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM39787
		// return to origin screen
		this.closeWindow();

	}


}
//#CM39788
//end of class
