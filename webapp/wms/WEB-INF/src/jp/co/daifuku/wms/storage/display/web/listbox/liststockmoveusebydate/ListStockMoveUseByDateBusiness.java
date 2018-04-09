// $Id: ListStockMoveUseByDateBusiness.java,v 1.2 2006/12/07 08:57:43 suresh Exp $

//#CM567984
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststockmoveusebydate;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStockUseByDateRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM567985
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * Expiry Date list list box (Movement inventory) screen class. <BR>
 * Retrieve Consignor Code, Item Code, Location before movement, and Expiry Date based on input from the parents screen and display it on the screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Retrieve by making Consignor Code, Item Code, Location before movement, and Expiry Date input from the parents screen a key, and display it on the screen. <BR>
 * </DIR>
 * <BR>
 * 2.Button of selected line (<CODE>lst_ListStockMoveUseByDate_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Pass Expiry Date of the Selected line to the parents screen, and close the list box. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:43 $
 * @author  $Author: suresh $
 */
public class ListStockMoveUseByDateBusiness extends ListStockMoveUseByDate implements WMSConstants
{
	//#CM567986
	// Class fields --------------------------------------------------
	//#CM567987
	/** 
	 * The key used to hand over Expiry Date. 
	 */
	public static final String USEBYDATE_KEY = "USEBYDATE_KEY";
	//#CM567988
	// Class variables -----------------------------------------------

	//#CM567989
	// Class method --------------------------------------------------

	//#CM567990
	// Constructors --------------------------------------------------

	//#CM567991
	// Public methods ------------------------------------------------

	//#CM567992
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 * Item <BR>
	 *	<DIR>
	 *    Selection<BR>
	 *	  Expiry Date<BR>
	 *	</DIR>
	 *</DIR>
	 *<BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM567993
		// Set the Screen name
		//#CM567994
		// Expiry Date retrieval
		lbl_ListName.setText(DisplayText.getText("TLE-W0065"));

		//#CM567995
		// Parameter is acquired. 
		//#CM567996
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM567997
		// Item Code
		String itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM567998
		// Location before movement
		String inventorylocation = request.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		//#CM567999
		// Expiry Date
		String usebydate = request.getParameter(ListStockMoveUseByDateBusiness.USEBYDATE_KEY);
		//#CM568000
		// Area type flag
		String areatypeflag = request.getParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY);
				
		//#CM568001
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM568002
			// Connection close
			sRet.closeConnection();
			//#CM568003
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM568004
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM568005
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM568006
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM568007
		// Item Code
		param.setItemCode(itemcode);
		//#CM568008
		// Location before movement
		param.setLocation(inventorylocation);
		//#CM568009
		// Expiry Date
		param.setUseByDate(usebydate);
		//#CM568010
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);

		//#CM568011
		// Make SessionRet instance
		SessionStockUseByDateRet listbox = new SessionStockUseByDateRet(conn, param);
		//#CM568012
		// Listbox is maintained in Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM568013
	// Package methods -----------------------------------------------

	//#CM568014
	// Protected methods ---------------------------------------------

	//#CM568015
	// Private methods -----------------------------------------------
	//#CM568016
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired by the displayed page and data is set in List cell.  .<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionInventoryListRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setList(SessionStockUseByDateRet listbox, String actionName)
	{
		//#CM568017
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568018
		// The retrieval result is acquired. 
		Stock[] storageparam = (Stock[])listbox.getEntities();

		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM568019
			// Value set in pager
			//#CM568020
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568021
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568022
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568023
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568024
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568025
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568026
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568027
			// Delete all lines
			lst_ListStockMoveUseByDate.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568028
				// The final line is acquired
				int count = lst_ListStockMoveUseByDate.getMaxRows();
				//#CM568029
				// Add row
				lst_ListStockMoveUseByDate.addRow();

				//#CM568030
				// It moves to the final line. 
				lst_ListStockMoveUseByDate.setCurrentRow(count);
				lst_ListStockMoveUseByDate.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStockMoveUseByDate.setValue(2, storageparam[i].getUseByDate());
			}
		}
		else
		{
			//#CM568031
			// Value set to Pager
			//#CM568032
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568033
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568034
			// Starting position
			pgr_U.setIndex(0);
			//#CM568035
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568036
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568037
			// Starting position
			pgr_D.setIndex(0);

			//#CM568038
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568039
			// Conceal the header
			lst_ListStockMoveUseByDate.setVisible(false);
			//#CM568040
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM568041
	// Event handler methods -----------------------------------------
	//#CM568042
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568043
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568044
	/** 
	 * Do the Processing when the Close button is pressed. <BR>
	 *  <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM568045
	/** 
	 * Do the Processing when the [>] button is pressed. <BR>
	 * <BR>
	 * Display the subsequent page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM568046
	/** 
	 * Do the Processing when the [<] button is pressed. <BR>
	 * <BR>
	 * Display the previous page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM568047
	/** 
	 * Do the Processing when the [>>] button is pressed. <BR>
	 * <BR>
	 * Display the last page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM568048
	/** 
	 * Do the Processing when the [<<] button is pressed. <BR>
	 * <BR>
	 * Display the first page. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM568049
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568050
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMoveUseByDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM568051
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMoveUseByDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM568052
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMoveUseByDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM568053
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMoveUseByDate_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM568054
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMoveUseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568055
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMoveUseByDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM568056
	/** 
	 * Process when Selection button of List cell is pressed.  <BR>
	 * <BR>
	 *	Pass Expiry Date to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_ListStockMoveUseByDate_Click(ActionEvent e) throws Exception
	{
		//#CM568057
		// A present line is set. 
		lst_ListStockMoveUseByDate.setCurrentRow(lst_ListStockMoveUseByDate.getActiveRow());
		lst_ListStockMoveUseByDate.getValue(1);

		//#CM568058
		// Set Parameter to be returned to parents screen
		ForwardParameters param = new ForwardParameters();
		//#CM568059
		// Expiry Date
		param.setParameter(USEBYDATE_KEY, lst_ListStockMoveUseByDate.getValue(2));

		//#CM568060
		// Changes to the parents screen. 
		parentRedirect(param);
	}
	
	//#CM568061
	/** 
	 * Do the Processing when the [>] button is pressed. <BR>
	 * <BR>
	 * Display the subsequent page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM568062
		// Listbox is maintained in Session
		SessionStockUseByDateRet listbox = (SessionStockUseByDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");	
	}

	//#CM568063
	/** 
	 * Do the Processing when the [<] button is pressed. <BR>
	 * <BR>
	 * Display the previous page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM568064
		// Listbox is maintained in Session
		SessionStockUseByDateRet listbox = (SessionStockUseByDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM568065
	/** 
	 * Do the Processing when the [>>] button is pressed. <BR>
	 * <BR>
	 * Display the last page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM568066
		// Listbox is maintained in Session
		SessionStockUseByDateRet listbox = (SessionStockUseByDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM568067
	/** 
	 * Do the Processing when the [<<] button is pressed. <BR>
	 * <BR>
	 * Display the first page. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM568068
		// Listbox is maintained in Session
		SessionStockUseByDateRet listbox = (SessionStockUseByDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM568069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568070
	/** 
	 * Do the Processing when the Close button is pressed. <BR>
	 *  <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM568071
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM568072
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM568073
				// Close the statement.
				finder.close();
			}
			//#CM568074
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM568075
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM568076
		// Return to the parents screen
		parentRedirect(null);
	}


}
//#CM568077
//end of class
