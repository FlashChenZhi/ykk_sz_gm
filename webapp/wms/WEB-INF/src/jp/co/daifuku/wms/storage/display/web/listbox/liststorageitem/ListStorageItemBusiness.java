// $Id: ListStorageItemBusiness.java,v 1.2 2006/12/07 08:57:41 suresh Exp $

//#CM568478
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem;
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
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragedate.ListStorageDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragelocation.ListStorageLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionInventoryItemRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionInventoryStockItemRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageMovementItemRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStoragePlanItemRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageResultItemRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageStockItemRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageWorkInfoItemRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM568479
/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * The item retrieval list box class. <BR>
 * Retrieve it based on the search condition input from the parents screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Retrieve by making the search condition input from the parents screen as a key, and display it on the screen. <BR>
 * </DIR>
 * <BR>
 * 2.Button of selected line (<CODE>lst_ItemSearch_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Pass Item Code and Item Name of the Selected line to the parents screen, and close the list box. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:41 $
 * @author  $Author: suresh $
 */
public class ListStorageItemBusiness extends ListStorageItem implements WMSConstants
{
	//#CM568480
	// Class fields --------------------------------------------------
	//#CM568481
	/** 
	 * The key used to hand over Item Code. 
	 */
	public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

	//#CM568482
	/** 
	 * The key used to hand over Item Name.
	 */
	public static final String ITEMNAME_KEY = "ITEMNAME_KEY";

	//#CM568483
	/** 
	 * The key used to hand over Retrieval flag. 
	 */
	public static final String SEARCHITEM_KEY = "SEARCHITEMR_KEY";

	//#CM568484
	/** 
	 * Work The key used to hand over Status. 
	 */
	public static final String WORKSTATUSITEM_KEY = "WORKSTATUSITEM_KEY";
	
	//#CM568485
	/** 
	 * The key used to hand over Area type flag. 
	 */
	public static final String AREA_TYPE_KEY = "AREA_TYPE_KEY";	

	//#CM568486
	// Class variables -----------------------------------------------

	//#CM568487
	// Class method --------------------------------------------------

	//#CM568488
	// Constructors --------------------------------------------------

	//#CM568489
	// Public methods ------------------------------------------------

	//#CM568490
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Item Code <BR>
	 *		Item Name <BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM568491
		// Set the Screen name
		//#CM568492
		// Item retrieval
		lbl_ListName.setText(DisplayText.getText("TLE-W0043"));

		//#CM568493
		// Parameter is acquired. 
		//#CM568494
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM568495
		// Storage plan date
		String storageplandate = request.getParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY);
		//#CM568496
		// Start Storage plan date
		String startstorageplandate = request.getParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY);
		//#CM568497
		// End Storage plan date
		String endstorageplandate = request.getParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY);
		//#CM568498
		// Start Storage date
		String startstoragedate = request.getParameter(ListStorageDateBusiness.STARTSTORAGEDATE_KEY);
		//#CM568499
		// End Storage date
		String endstoragedate = request.getParameter(ListStorageDateBusiness.ENDSTORAGEDATE_KEY);
		//#CM568500
		// Start Location
		String startlocation = request.getParameter(ListStorageLocationBusiness.STARTSTORAGELOCATION_KEY);
		//#CM568501
		// End Location
		String endlcation = request.getParameter(ListStorageLocationBusiness.ENDSTORAGELOCATION_KEY);
		//#CM568502
		// Item Code
		String itemcode = request.getParameter(ITEMCODE_KEY);
		//#CM568503
		// Work Status
		String[] search = request.getParameterValues(WORKSTATUSITEM_KEY);
		//#CM568504
		// Item Retrieval flag
		String searchitem = request.getParameter(SEARCHITEM_KEY);
		//#CM568505
		// Area type flag
		String areatypeflag = request.getParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY);

		viewState.setString(SEARCHITEM_KEY, searchitem);
		//#CM568506
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM568507
			// Connection close
			sRet.closeConnection();
			//#CM568508
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM568509
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM568510
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM568511
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM568512
		// Storage plan date
		param.setStoragePlanDate(storageplandate);
		//#CM568513
		// Start Storage plan date
		param.setFromStoragePlanDate(startstorageplandate);
		//#CM568514
		// End Storage plan date
		param.setToStoragePlanDate(endstorageplandate);
		//#CM568515
		// Start Storage date
		param.setFromStorageDate(WmsFormatter.toDate(startstoragedate));
		//#CM568516
		// End Storage date
		param.setToStorageDate(WmsFormatter.toDate(endstoragedate));
		//#CM568517
		// Start Location
		param.setFromLocation(startlocation);
		//#CM568518
		// End Location
		param.setToLocation(endlcation);
		//#CM568519
		// Item Code
		param.setItemCode(itemcode);
		//#CM568520
		// Work Status array
		param.setSearchStatus(search);
		//#CM568521
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);
		
		//#CM568522
		// The table retrieved with Retrieval flag is decided. 
		if (searchitem.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568523
			// SessionStoragePlanItemRet  instance generation
			SessionStoragePlanItemRet listbox = new SessionStoragePlanItemRet(conn, param);
			//#CM568524
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if (searchitem.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568525
			// SessionStoragePlanItemRet  instance generation
			SessionStorageResultItemRet listbox = new SessionStorageResultItemRet(conn, param);
			//#CM568526
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		else if (searchitem.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568527
			// SessionStorageStockItemRet  instance generation
			SessionStorageStockItemRet listbox = new SessionStorageStockItemRet(conn, param);
			//#CM568528
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setStockList(listbox, "first");
		}
		else if (searchitem.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568529
			// SessionInventoryItemRet  instance generation
			SessionInventoryItemRet listbox = new SessionInventoryItemRet(conn, param);
			//#CM568530
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setInventoryList(listbox, "first");
		}
		else if (searchitem.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568531
			// SessionInventoryStockItemRet  instance generation
			SessionInventoryStockItemRet listbox = new SessionInventoryStockItemRet(conn, param);
			//#CM568532
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setInventoryStockList(listbox, "first");
		}
		else if (searchitem.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568533
			// SessionStorageMovementItemRet  instance generation
			SessionStorageMovementItemRet listbox = new SessionStorageMovementItemRet(conn, param);
			//#CM568534
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setMoveList(listbox, "first");
		}
		else if (searchitem.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568535
			// SessionStorageWorkInfoItemRet  instance generation
			SessionStorageWorkInfoItemRet listbox = new SessionStorageWorkInfoItemRet(conn, param);
			//#CM568536
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0024");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
	}

	//#CM568537
	// Package methods -----------------------------------------------

	//#CM568538
	// Protected methods ---------------------------------------------

	//#CM568539
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell  from Work information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageWorkInfoItemRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setWorkList(SessionStorageWorkInfoItemRet listbox, String actionName)
	{
		//#CM568540
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568541
		// The retrieval result is acquired. 
		StorageSupportParameter[] workparam = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (workparam != null)
			len = workparam.length;
		if (len > 0)
		{
			//#CM568542
			// Value set to Pager
			//#CM568543
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568544
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568545
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568546
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568547
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568548
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568549
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568550
			// Delete all lines
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568551
				// The final line is acquired
				int count = lst_ItemSearch.getMaxRows();
				//#CM568552
				// Add row
				lst_ItemSearch.addRow();

				//#CM568553
				// It moves to the final line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, workparam[i].getItemCode());
				lst_ItemSearch.setValue(3, workparam[i].getItemName());
			}
		}
		else
		{
			//#CM568554
			// Value set to Pager
			//#CM568555
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568556
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568557
			// Starting position
			pgr_U.setIndex(0);
			//#CM568558
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568559
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568560
			// Starting position
			pgr_D.setIndex(0);

			//#CM568561
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568562
			// Conceal the header
			lst_ItemSearch.setVisible(false);
			//#CM568563
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}

	}

	//#CM568564
	// Private methods -----------------------------------------------
	//#CM568565
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell  from stock Plan information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStoragePlanItemRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setPlanList(SessionStoragePlanItemRet listbox, String actionName)
	{
		//#CM568566
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568567
		// The retrieval result is acquired. 
		StorageSupportParameter[] storageparam = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM568568
			// Value set to Pager
			//#CM568569
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568570
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568571
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568572
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568573
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568574
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568575
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568576
			// Delete all lines
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568577
				// The final line is acquired
				int count = lst_ItemSearch.getMaxRows();
				//#CM568578
				// Add row
				lst_ItemSearch.addRow();

				//#CM568579
				// It moves to the final line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, storageparam[i].getItemCode());
				lst_ItemSearch.setValue(3, storageparam[i].getItemName());
			}
		}
		else
		{
			//#CM568580
			// Value set to Pager
			//#CM568581
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568582
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568583
			// Starting position
			pgr_U.setIndex(0);
			//#CM568584
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568585
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568586
			// Starting position
			pgr_D.setIndex(0);

			//#CM568587
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568588
			// Conceal the header
			lst_ItemSearch.setVisible(false);
			//#CM568589
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568590
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell from results View information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageResultItemRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setResultList(SessionStorageResultItemRet listbox, String actionName)
	{
		//#CM568591
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568592
		// The retrieval result is acquired. 
		StorageSupportParameter[] ssparam = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (ssparam != null)
			len = ssparam.length;
		if (len > 0)
		{
			//#CM568593
			// Value set to Pager
			//#CM568594
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568595
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568596
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568597
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568598
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568599
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568600
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568601
			// Delete all lines
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568602
				// The final line is acquired
				int count = lst_ItemSearch.getMaxRows();
				//#CM568603
				// Add row
				lst_ItemSearch.addRow();

				//#CM568604
				// It moves to the final line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, ssparam[i].getItemCode());
				lst_ItemSearch.setValue(3, ssparam[i].getItemName());
			}
		}
		else
		{
			//#CM568605
			// Value set to Pager
			//#CM568606
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568607
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568608
			// Starting position
			pgr_U.setIndex(0);
			//#CM568609
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568610
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568611
			// Starting position
			pgr_D.setIndex(0);

			//#CM568612
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568613
			// Conceal the header
			lst_ItemSearch.setVisible(false);
			//#CM568614
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568615
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
	 * @param listbox SessionStorageStockItemRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setStockList(SessionStorageStockItemRet listbox, String actionName)
	{
		//#CM568616
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568617
		// The retrieval result is acquired. 
		StorageSupportParameter[] storageparam = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM568618
			// Value set to Pager
			//#CM568619
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568620
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568621
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568622
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568623
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568624
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568625
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568626
			// Delete all lines
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568627
				// The final line is acquired
				int count = lst_ItemSearch.getMaxRows();
				//#CM568628
				// Add row
				lst_ItemSearch.addRow();

				//#CM568629
				// It moves to the final line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, storageparam[i].getItemCode());
				lst_ItemSearch.setValue(3, storageparam[i].getItemName());
			}
		}
		else
		{
			//#CM568630
			// Value set to Pager
			//#CM568631
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568632
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568633
			// Starting position
			pgr_U.setIndex(0);
			//#CM568634
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568635
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568636
			// Starting position
			pgr_D.setIndex(0);

			//#CM568637
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568638
			// Conceal the header
			lst_ItemSearch.setVisible(false);
			//#CM568639
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568640
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
	 * @param listbox SessionInventoryItemRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setInventoryList(SessionInventoryItemRet listbox, String actionName)
	{
		//#CM568641
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568642
		// The retrieval result is acquired. 
		InventoryCheck[] inventoryparam = listbox.getEntities();
		int len = 0;
		if (inventoryparam != null)
			len = inventoryparam.length;
		if (len > 0)
		{
			//#CM568643
			// Value set to Pager
			//#CM568644
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568645
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568646
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568647
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568648
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568649
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568650
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568651
			// Delete all lines
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568652
				// The final line is acquired
				int count = lst_ItemSearch.getMaxRows();
				//#CM568653
				// Add row
				lst_ItemSearch.addRow();

				//#CM568654
				// It moves to the final line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, inventoryparam[i].getItemCode());
				lst_ItemSearch.setValue(3, inventoryparam[i].getItemName1());
			}
		}
		else
		{
			//#CM568655
			// Value set to Pager
			//#CM568656
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568657
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568658
			// Starting position
			pgr_U.setIndex(0);
			//#CM568659
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568660
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568661
			// Starting position
			pgr_D.setIndex(0);

			//#CM568662
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568663
			// Conceal the header
			lst_ItemSearch.setVisible(false);
			//#CM568664
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568665
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
	 * @param listbox SessionInventoryStockItemRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setInventoryStockList(SessionInventoryStockItemRet listbox, String actionName)
	{
		//#CM568666
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568667
		// The retrieval result is acquired. 
		StorageSupportParameter[] inventorystockparam = (StorageSupportParameter[])listbox.getEntities();
		int len = 0;
		if (inventorystockparam != null)
			len = inventorystockparam.length;
		if (len > 0)
		{
			//#CM568668
			// Value set to Pager
			//#CM568669
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568670
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568671
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568672
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568673
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568674
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568675
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568676
			// Delete all lines
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568677
				// The final line is acquired
				int count = lst_ItemSearch.getMaxRows();
				//#CM568678
				// Add row
				lst_ItemSearch.addRow();

				//#CM568679
				// It moves to the final line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, inventorystockparam[i].getItemCode());
				lst_ItemSearch.setValue(3, inventorystockparam[i].getItemName());
			}
		}
		else
		{
			//#CM568680
			// Value set to Pager
			//#CM568681
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568682
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568683
			// Starting position
			pgr_U.setIndex(0);
			//#CM568684
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568685
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568686
			// Starting position
			pgr_D.setIndex(0);

			//#CM568687
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568688
			// Conceal the header
			lst_ItemSearch.setVisible(false);
			//#CM568689
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568690
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell from movement Work information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageMovementItemRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setMoveList(SessionStorageMovementItemRet listbox, String actionName)
	{
		//#CM568691
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568692
		// The retrieval result is acquired. 
		Movement[] move = (Movement[]) listbox.getEntities();
		int len = 0;
		if (move != null)
			len = move.length;
		if (len > 0)
		{
			//#CM568693
			// Value set to Pager
			//#CM568694
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568695
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568696
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568697
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568698
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568699
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568700
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568701
			// Delete all lines
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568702
				// The final line is acquired
				int count = lst_ItemSearch.getMaxRows();
				//#CM568703
				// Add row
				lst_ItemSearch.addRow();

				//#CM568704
				// It moves to the final line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, move[i].getItemCode());
				lst_ItemSearch.setValue(3, move[i].getItemName1());
			}
		}
		else
		{
			//#CM568705
			// Value set to Pager
			//#CM568706
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568707
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568708
			// Starting position
			pgr_U.setIndex(0);
			//#CM568709
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568710
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568711
			// Starting position
			pgr_D.setIndex(0);

			//#CM568712
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568713
			// Conceal the header
			lst_ItemSearch.setVisible(false);
			//#CM568714
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568715
	// Event handler methods -----------------------------------------
	//#CM568716
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568717
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

	//#CM568718
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

	//#CM568719
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

	//#CM568720
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

	//#CM568721
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

	//#CM568722
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568723
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM568724
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM568725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM568726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM568727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568728
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM568729
	/** 
	 * Process when Selection button of List cell is pressed.  <BR>
	 * <BR>
	 *	Pass Item Code and Item Name to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_ItemSearch_Click(ActionEvent e) throws Exception
	{

		//#CM568730
		// A present line is set. 
		lst_ItemSearch.setCurrentRow(lst_ItemSearch.getActiveRow());
		lst_ItemSearch.getValue(1);

		//#CM568731
		// Set Parameter to be returned to parents screen
		ForwardParameters param = new ForwardParameters();
		//#CM568732
		// Item Code
		param.setParameter(ITEMCODE_KEY, lst_ItemSearch.getValue(2));
		//#CM568733
		// Item Name
		param.setParameter(ITEMNAME_KEY, lst_ItemSearch.getValue(3));

		//#CM568734
		// Changes to the parents screen. 
		parentRedirect(param);
	}

	//#CM568735
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
		//#CM568736
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568737
			// Listbox is maintained in Session
			SessionStoragePlanItemRet listbox = (SessionStoragePlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568738
			// Listbox is maintained in Session
			SessionStorageResultItemRet listbox = (SessionStorageResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568739
			// Listbox is maintained in Session
			SessionStorageStockItemRet listbox = (SessionStorageStockItemRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568740
			// Listbox is maintained in Session
			SessionInventoryItemRet listbox = (SessionInventoryItemRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568741
			// Listbox is maintained in Session
			SessionInventoryStockItemRet listbox = (SessionInventoryStockItemRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568742
			// Listbox is maintained in Session
			SessionStorageMovementItemRet listbox = (SessionStorageMovementItemRet) this.getSession().getAttribute("LISTBOX");
			setMoveList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568743
			// Listbox is maintained in Session
			SessionStorageWorkInfoItemRet listbox = (SessionStorageWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "next");
		}
	}

	//#CM568744
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
		//#CM568745
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568746
			// Listbox is maintained in Session
			SessionStoragePlanItemRet listbox = (SessionStoragePlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568747
			// Listbox is maintained in Session
			SessionStorageResultItemRet listbox = (SessionStorageResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568748
			// Listbox is maintained in Session
			SessionStorageStockItemRet listbox = (SessionStorageStockItemRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568749
			// Listbox is maintained in Session
			SessionInventoryItemRet listbox = (SessionInventoryItemRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568750
			// Listbox is maintained in Session
			SessionInventoryStockItemRet listbox = (SessionInventoryStockItemRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568751
			// Listbox is maintained in Session
			SessionStorageMovementItemRet listbox = (SessionStorageMovementItemRet) this.getSession().getAttribute("LISTBOX");
			setMoveList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568752
			// Listbox is maintained in Session
			SessionStorageWorkInfoItemRet listbox = (SessionStorageWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "previous");
		}
	}

	//#CM568753
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
		//#CM568754
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568755
			// Listbox is maintained in Session
			SessionStoragePlanItemRet listbox = (SessionStoragePlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568756
			// Listbox is maintained in Session
			SessionStorageResultItemRet listbox = (SessionStorageResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568757
			// Listbox is maintained in Session
			SessionStorageStockItemRet listbox = (SessionStorageStockItemRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568758
			// Listbox is maintained in Session
			SessionInventoryItemRet listbox = (SessionInventoryItemRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568759
			// Listbox is maintained in Session
			SessionInventoryStockItemRet listbox = (SessionInventoryStockItemRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568760
			// Listbox is maintained in Session
			SessionStorageMovementItemRet listbox = (SessionStorageMovementItemRet) this.getSession().getAttribute("LISTBOX");
			setMoveList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568761
			// Listbox is maintained in Session
			SessionStorageWorkInfoItemRet listbox = (SessionStorageWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "last");
		}
	}

	//#CM568762
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
		//#CM568763
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568764
			// Listbox is maintained in Session
			SessionStoragePlanItemRet listbox = (SessionStoragePlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568765
			// Listbox is maintained in Session
			SessionStorageResultItemRet listbox = (SessionStorageResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568766
			// Listbox is maintained in Session
			SessionStorageStockItemRet listbox = (SessionStorageStockItemRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568767
			// Listbox is maintained in Session
			SessionInventoryItemRet listbox = (SessionInventoryItemRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568768
			// Listbox is maintained in Session
			SessionInventoryStockItemRet listbox = (SessionInventoryStockItemRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568769
			// Listbox is maintained in Session
			SessionStorageMovementItemRet listbox = (SessionStorageMovementItemRet) this.getSession().getAttribute("LISTBOX");
			setMoveList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568770
			// Listbox is maintained in Session
			SessionStorageWorkInfoItemRet listbox = (SessionStorageWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "first");
		}
	}
	//#CM568771
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568772
	/** 
	 * Do the Processing when the Close button is pressed. <BR>
	 * <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM568773
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM568774
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM568775
				// Close the statement.
				finder.close();
			}
			//#CM568776
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM568777
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM568778
		// Return to the parents screen
		parentRedirect(null);
	}
}
//#CM568779
//end of class
