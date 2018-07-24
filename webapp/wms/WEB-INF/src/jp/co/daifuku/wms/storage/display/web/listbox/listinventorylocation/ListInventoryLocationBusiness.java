// $Id: ListInventoryLocationBusiness.java,v 1.2 2006/12/07 08:57:44 suresh Exp $

//#CM567807
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionInventoryLocationRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionInventoryStockLocationRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStockLocationRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM567808
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * The Location retrieval list box class.  <BR>
 * Retrieve the location entered in parent screen as a key and display on the screen.<BR>
 * <BR>
 * Process it in this class as follows.  <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Retrieve the location entered in parent screen as a key and display on the screen.<BR>
 * </DIR>
 * <BR>
 * 2.Button of selected line (<CODE>lst_ListStockLocation_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Pass Location of the selected line to the parents screen, and close the list box. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:44 $
 * @author  $Author: suresh $
 */
public class ListInventoryLocationBusiness extends ListInventoryLocation implements WMSConstants
{
	//#CM567809
	// Class fields --------------------------------------------------
	//#CM567810
	/** 
	 * The key used to hand over Location information. 
	 */
	public static final String LOCATION_KEY = "LOCATION_KEY";

	//#CM567811
	/** 
	 * The key used to hand over Start Location information. 
	 */
	public static final String STARTLOCATION_KEY = "STARTLOCATION_KEY";

	//#CM567812
	/** 
	 * The key used to hand over End Location information. 
	 */
	public static final String ENDLOCATION_KEY = "ENDLOCATION_KEY";

	//#CM567813
	/** 
	 * The key used to hand over Retrieval flag. 
	 */
	public static final String SEARCHLOCATION_KEY = "SEARCHLOCATION_KEY";

	//#CM567814
	/** 
	 * The key used to hand over Location Range flag. 
	 */
	public static final String RANGELOCATION_KEY = "RANGELOCATION_KEY";

	//#CM567815
	// Class variables -----------------------------------------------

	//#CM567816
	// Class method --------------------------------------------------

	//#CM567817
	// Constructors --------------------------------------------------

	//#CM567818
	// Public methods ------------------------------------------------

	//#CM567819
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Location <BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM567820
		// Set the Screen name
		//#CM567821
		// Location retrieval
		lbl_ListName.setText(DisplayText.getText("TLE-W0064"));

		//#CM567822
		// Parameter is acquired. 
		//#CM567823
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM567824
		// Item Code
		String itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM567825
		// Location
		String location = request.getParameter(LOCATION_KEY);
		//#CM567826
		// Start Location
		String startlocation = request.getParameter(STARTLOCATION_KEY);
		//#CM567827
		// End Location
		String endlocation = request.getParameter(ENDLOCATION_KEY);
		//#CM567828
		// Retrieval flag
		String searchlocation = request.getParameter(SEARCHLOCATION_KEY);
		//#CM567829
		// Range flag
		String rangelocation = request.getParameter(RANGELOCATION_KEY);
		//#CM567830
		// Area type flag
		String areatypeflag = request.getParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY);
		
		viewState.setString(SEARCHLOCATION_KEY, searchlocation);
		viewState.setString(RANGELOCATION_KEY, rangelocation);
		
		//#CM567831
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM567832
			// Connection close
			sRet.closeConnection();
			//#CM567833
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM567834
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM567835
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM567836
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM567837
		// Item Code
		param.setItemCode(itemcode);
		//#CM567838
		// Location
		param.setLocation(location);
		//#CM567839
		// Start Location
		param.setFromLocation(startlocation);
		//#CM567840
		// End Location
		param.setToLocation(endlocation);
		//#CM567841
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);

		//#CM567842
		// The table retrieved with Retrieval flag is decided. 
		if (searchlocation.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM567843
			// SessionStockLocationRet  instance generation
			SessionStockLocationRet listbox = new SessionStockLocationRet(conn, param);
			//#CM567844
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setStockList(listbox, "first");
		}
		else if (searchlocation.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM567845
			// SessionInventoryLocationRet  instance generation
			SessionInventoryLocationRet listbox = new SessionInventoryLocationRet(conn, param);
			//#CM567846
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setInventoryList(listbox, "first");
		}
		else if (searchlocation.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM567847
			// SessionInventoryLocationRet  instance generation
			SessionInventoryStockLocationRet listbox = new SessionInventoryStockLocationRet(conn, param);
			//#CM567848
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setInventoryStockList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0110");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			//#CM567849
			// 6007039={0} search failed. See log.
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
	}

	//#CM567850
	// Package methods -----------------------------------------------

	//#CM567851
	// Protected methods ---------------------------------------------

	//#CM567852
	// Private methods -----------------------------------------------
	//#CM567853
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell from the inventory information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStockLocationRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setStockList(SessionStockLocationRet listbox, String actionName) throws Exception
	{
		//#CM567854
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM567855
		// The retrieval result is acquired. 
		Stock[] stock = listbox.getEntities();
		int len = 0;
		if (stock != null)
			len = stock.length;
		if (len > 0)
		{
			//#CM567856
			// Value set to Pager
			//#CM567857
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM567858
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM567859
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM567860
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM567861
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM567862
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM567863
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM567864
			// Delete all lines
			lst_ListStockLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM567865
				// The final line is acquired
				int count = lst_ListStockLocation.getMaxRows();
				//#CM567866
				// Add row
				lst_ListStockLocation.addRow();

				//#CM567867
				// It moves to the final line. 
				lst_ListStockLocation.setCurrentRow(count);
				lst_ListStockLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStockLocation.setValue(2, stock[i].getLocationNo());
			}
		}
		else
		{
			//#CM567868
			// Value set to Pager
			//#CM567869
			// The maximum qty
			pgr_U.setMax(0);
			//#CM567870
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM567871
			// Starting position
			pgr_U.setIndex(0);
			//#CM567872
			// The maximum qty
			pgr_D.setMax(0);
			//#CM567873
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM567874
			// Starting position
			pgr_D.setIndex(0);

			//#CM567875
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM567876
			// Conceal the header
			lst_ListStockLocation.setVisible(false);
			//#CM567877
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM567878
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired from the displayed page and Set data in List cell from Inventory Work information<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionInventoryLocationRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setInventoryList(SessionInventoryLocationRet listbox, String actionName) throws Exception
	{
		//#CM567879
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM567880
		// The retrieval result is acquired. 
		InventoryCheck[] invcheck = listbox.getEntities();
		int len = 0;
		if (invcheck != null)
			len = invcheck.length;
		if (len > 0)
		{
			//#CM567881
			// Value set to Pager
			//#CM567882
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM567883
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM567884
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM567885
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM567886
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM567887
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM567888
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM567889
			// Delete all lines
			lst_ListStockLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM567890
				// The final line is acquired
				int count = lst_ListStockLocation.getMaxRows();
				//#CM567891
				// Add row
				lst_ListStockLocation.addRow();

				//#CM567892
				// It moves to the final line. 
				lst_ListStockLocation.setCurrentRow(count);
				lst_ListStockLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStockLocation.setValue(2, invcheck[i].getLocationNo());
			}
		}
		else
		{
			//#CM567893
			// Value set to Pager
			//#CM567894
			// The maximum qty
			pgr_U.setMax(0);
			//#CM567895
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM567896
			// Starting position
			pgr_U.setIndex(0);
			//#CM567897
			// The maximum qty
			pgr_D.setMax(0);
			//#CM567898
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM567899
			// Starting position
			pgr_D.setIndex(0);

			//#CM567900
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM567901
			// Conceal the header
			lst_ListStockLocation.setVisible(false);
			//#CM567902
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM567903
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired from the displayed page and Set data in List cell from Inventory Work + Inventory Work information<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionInventoryStockLocationRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setInventoryStockList(SessionInventoryStockLocationRet listbox, String actionName) throws Exception
	{
		//#CM567904
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM567905
		// The retrieval result is acquired. 
		StorageSupportParameter[] inventoryStock = (StorageSupportParameter[])listbox.getEntities();
		int len = 0;
		if (inventoryStock != null)
			len = inventoryStock.length;
		if (len > 0)
		{
			//#CM567906
			// Value set to Pager
			//#CM567907
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM567908
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM567909
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM567910
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM567911
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM567912
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM567913
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM567914
			// Delete all lines
			lst_ListStockLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM567915
				// The final line is acquired
				int count = lst_ListStockLocation.getMaxRows();
				//#CM567916
				// Add row
				lst_ListStockLocation.addRow();

				//#CM567917
				// It moves to the final line. 
				lst_ListStockLocation.setCurrentRow(count);
				lst_ListStockLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStockLocation.setValue(2, inventoryStock[i].getLocation());
			}
		}
		else
		{
			//#CM567918
			// Value set to Pager
			//#CM567919
			// The maximum qty
			pgr_U.setMax(0);
			//#CM567920
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM567921
			// Starting position
			pgr_U.setIndex(0);
			//#CM567922
			// The maximum qty
			pgr_D.setMax(0);
			//#CM567923
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM567924
			// Starting position
			pgr_D.setIndex(0);

			//#CM567925
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM567926
			// Conceal the header
			lst_ListStockLocation.setVisible(false);
			//#CM567927
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	
	//#CM567928
	// Event handler methods -----------------------------------------
	//#CM567929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567931
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

	//#CM567932
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

	//#CM567933
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

	//#CM567934
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

	//#CM567935
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
	//#CM567936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM567941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_Change(ActionEvent e) throws Exception
	{
	}

	//#CM567943
	/** 
	 * Process when Selection button of List cell is pressed.  <BR>
	 * <BR>
	 *	Pass Location to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_ListStockLocation_Click(ActionEvent e) throws Exception
	{
		//#CM567944
		// Range flag is acquired.
		String flug = viewState.getString(RANGELOCATION_KEY);

		//#CM567945
		// A present line is set. 
		lst_ListStockLocation.setCurrentRow(lst_ListStockLocation.getActiveRow());
		lst_ListStockLocation.getValue(1);

		//#CM567946
		// Set Parameter to be returned to parents screen
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM567947
			// Location
			param.setParameter(LOCATION_KEY, lst_ListStockLocation.getValue(2));
		}
		else if (flug.equals(StorageSupportParameter.RANGE_START))
		{
			//#CM567948
			// Start Location
			param.setParameter(STARTLOCATION_KEY, lst_ListStockLocation.getValue(2));
		}
		else if (flug.equals(StorageSupportParameter.RANGE_END))
		{
			//#CM567949
			// End Location
			param.setParameter(ENDLOCATION_KEY, lst_ListStockLocation.getValue(2));
		}

		//#CM567950
		// Changes to the parents screen. 
		parentRedirect(param);
	}

	//#CM567951
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
		//#CM567952
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHLOCATION_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM567953
			// Listbox is maintained in Session
			SessionStockLocationRet listbox =
				(SessionStockLocationRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM567954
			// Listbox is maintained in Session
			SessionInventoryLocationRet listbox =
				(SessionInventoryLocationRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM567955
			// Listbox is maintained in Session
			SessionInventoryStockLocationRet listbox =
				(SessionInventoryStockLocationRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "next");
		}
	}

	//#CM567956
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
		//#CM567957
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHLOCATION_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM567958
			// Listbox is maintained in Session
			SessionStockLocationRet listbox =
				(SessionStockLocationRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM567959
			// Listbox is maintained in Session
			SessionInventoryLocationRet listbox =
				(SessionInventoryLocationRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM567960
			// Listbox is maintained in Session
			SessionInventoryStockLocationRet listbox =
				(SessionInventoryStockLocationRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "previous");
		}
	}

	//#CM567961
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
		//#CM567962
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHLOCATION_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM567963
			// Listbox is maintained in Session
			SessionStockLocationRet listbox =
				(SessionStockLocationRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM567964
			// Listbox is maintained in Session
			SessionInventoryLocationRet listbox =
				(SessionInventoryLocationRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM567965
			// Listbox is maintained in Session
			SessionInventoryStockLocationRet listbox =
				(SessionInventoryStockLocationRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "last");
		}
	}

	//#CM567966
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
		//#CM567967
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHLOCATION_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM567968
			// Listbox is maintained in Session
			SessionStockLocationRet listbox =
				(SessionStockLocationRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM567969
			// Listbox is maintained in Session
			SessionInventoryLocationRet listbox =
				(SessionInventoryLocationRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM567970
			// Listbox is maintained in Session
			SessionInventoryStockLocationRet listbox =
				(SessionInventoryStockLocationRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "first");
		}
	}
	//#CM567971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567972
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
		//#CM567973
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM567974
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM567975
				// Close the statement.
				finder.close();
			}
			//#CM567976
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM567977
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM567978
		// Return to the parents screen
		parentRedirect(null);
	}
}
//#CM567979
//end of class
