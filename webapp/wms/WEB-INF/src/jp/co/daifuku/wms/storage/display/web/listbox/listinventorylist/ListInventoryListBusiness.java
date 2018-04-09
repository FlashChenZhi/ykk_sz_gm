// $Id: ListInventoryListBusiness.java,v 1.2 2006/12/07 08:57:45 suresh Exp $

//#CM567678
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.listinventorylist;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionInventoryListRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM567679
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * The Inventory Work list box screen class.<BR>
 * Retrieve it based on Consignor Code, Start Location, End Location, Item Code, and Status input from the parents screen<BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 		Make Consignor Code input from the keys of parents screen, Start Location, End Location, Item Code, and Status a key, retrieve and display it on the screen. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:45 $
 * @author  $Author: suresh $
 */
public class ListInventoryListBusiness extends ListInventoryList implements WMSConstants
{
	//#CM567680
	// Class fields --------------------------------------------------
	//#CM567681
	/** 
	 * The key used to hand over Status. 
	 */
	public static final String DISP_STATUS_KEY = "DISP_STATUS_KEY";
	
	//#CM567682
	// Class variables -----------------------------------------------

	//#CM567683
	// Class method --------------------------------------------------

	//#CM567684
	// Constructors --------------------------------------------------

	//#CM567685
	// Public methods ------------------------------------------------

	//#CM567686
	/**
	 * Initialize the screen.  <BR>
	 * <BR>
	 * <DIR>
	 * Item <BR>
	 *	<DIR>
	 *	  Location<BR>
	 *	  Item Code<BR>
	 *	  Packed qty per case<BR>
	 *    Inventory Case qty<BR>
	 *	  Stock Case qty<BR>
	 *	  Expiry Date<BR>
	 *    Worker Code<BR>
	 *	  Item Name<BR>
	 *	  Packed qty per bundle<BR>
	 *    Inventory Piece qty<BR>
	 *	  Stock Piece qty<BR>
	 *    Worker Name<BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM567687
		// Set the Screen name
		//#CM567688
		// Inventory Work list
		lbl_ListName.setText(DisplayText.getText("TLE-W0066"));

		//#CM567689
		// Parameter is acquired. 
		//#CM567690
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM567691
		// Start Location
		String startlocation = request.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		//#CM567692
		// End Location
		String endlocation = request.getParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY);
		//#CM567693
		// Item Code
		String itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM567694
		// Status
		String status = request.getParameter(DISP_STATUS_KEY);

		//#CM567695
		// Mandatory Input check and Restricted characters check of Consignor Code.
		if (!WmsCheckker.consignorCheck(consignorcode, lst_StrgInvntryLst, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM567696
		// Range check of Start and End Location
		if (!WmsCheckker.rangeLocationCheck(startlocation, endlocation, lst_StrgInvntryLst, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM567697
		// Restricted characters check of Start Location
		if (!WmsCheckker.charCheck(startlocation, lst_StrgInvntryLst, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM567698
		// Restricted characters check of End Location
		if (!WmsCheckker.charCheck(endlocation, lst_StrgInvntryLst, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM567699
		// Restricted characters check of Item Code
		if (!WmsCheckker.charCheck(itemcode, lst_StrgInvntryLst, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM567700
		// Set Parameter and set Item which displays As it is. 
		//#CM567701
		// Consignor Code
		lbl_JavaSetCnsgnrCd.setText(consignorcode);

		//#CM567702
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM567703
			// Connection close
			sRet.closeConnection();
			//#CM567704
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM567705
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		//#CM567706
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM567707
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM567708
		// Start Location
		param.setFromLocation(startlocation);
		//#CM567709
		// End Location
		param.setToLocation(endlocation);
		//#CM567710
		// Item Code
		param.setItemCode(itemcode);
		//#CM567711
		// Status
		param.setDispStatus(status);

		//#CM567712
		// Make SessionRet instance
		SessionInventoryListRet listbox = new SessionInventoryListRet(conn, param);
		//#CM567713
		// Listbox is maintained in Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");

	}

	//#CM567714
	// Package methods -----------------------------------------------

	//#CM567715
	// Protected methods ---------------------------------------------

	//#CM567716
	// Private methods -----------------------------------------------
	//#CM567717
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired by the displayed page and data is set in List cell. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell.<BR>
	 * 		3.Set balloon information. <BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionInventoryListRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setList(SessionInventoryListRet listbox, String actionName)
	{
		//#CM567718
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM567719
		// The retrieval result is acquired. 
		StorageSupportParameter[] storageparam = listbox.getEntities();

		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM567720
			// Value set in pager
			//#CM567721
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM567722
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM567723
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM567724
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM567725
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM567726
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM567727
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM567728
			// Delete all lines
			lst_StrgInvntryLst.clearRow();

			//#CM567729
			// Consignor Code
			lbl_JavaSetCnsgnrCd.setText(storageparam[0].getConsignorCode());
			//#CM567730
			// Consignor Name
			lbl_JavaSetCnsgnrNm.setText(storageparam[0].getConsignorName());
			
			//#CM567731
			//Use it with ToolTip
			String title_Workercode = DisplayText.getText("LBL-W0274");
			String title_Workername = DisplayText.getText("LBL-W0276");
			
			for (int i = 0; i < len; i++)
			{
				//#CM567732
				// The final line is acquired
				int count = lst_StrgInvntryLst.getMaxRows();
				//#CM567733
				// Add row
				lst_StrgInvntryLst.addRow();

				//#CM567734
				// It moves to the final line. 
				lst_StrgInvntryLst.setCurrentRow(count);
				//#CM567735
				// Location				
				lst_StrgInvntryLst.setValue(1, storageparam[i].getLocation());
				//#CM567736
				// Item Code
				lst_StrgInvntryLst.setValue(2, storageparam[i].getItemCode());
				//#CM567737
				// Packed qty per case
				lst_StrgInvntryLst.setValue(3, WmsFormatter.getNumFormat(storageparam[i].getEnteringQty()));
				//#CM567738
				// Inventory Case qty
				lst_StrgInvntryLst.setValue(4, WmsFormatter.getNumFormat(storageparam[i].getInventoryCheckCaseQty()));
				//#CM567739
				// Stock Case qty
				lst_StrgInvntryLst.setValue(5, WmsFormatter.getNumFormat(storageparam[i].getStockCaseQty()));
				//#CM567740
				// Expiry Date		
				lst_StrgInvntryLst.setValue(6, storageparam[i].getUseByDate());
				//#CM567741
				// Worker Code		
				lst_StrgInvntryLst.setValue(7, storageparam[i].getWorkerCode());
				//#CM567742
				// Item Name			
				lst_StrgInvntryLst.setValue(8, storageparam[i].getItemName());
				//#CM567743
				// Packed qty per bundle		
				lst_StrgInvntryLst.setValue(9, WmsFormatter.getNumFormat(storageparam[i].getBundleEnteringQty()));
				//#CM567744
				// PlanPiece qty			
				lst_StrgInvntryLst.setValue(10, WmsFormatter.getNumFormat(storageparam[i].getInventoryCheckPieceQty()));
				//#CM567745
				// Result Piece qty			
				lst_StrgInvntryLst.setValue(11, WmsFormatter.getNumFormat(storageparam[i].getStockPieceQty()));
				//#CM567746
				// Worker Name			
				lst_StrgInvntryLst.setValue(12, storageparam[i].getWorkerName());
				
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_Workercode, storageparam[i].getWorkerCode());
				toolTip.add(title_Workername, storageparam[i].getWorkerName());
				
				//#CM567747
				// Set the tool tip
				lst_StrgInvntryLst.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM567748
			// Value set to Pager
			//#CM567749
			// The maximum qty
			pgr_U.setMax(0);
			//#CM567750
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM567751
			// Starting position
			pgr_U.setIndex(0);
			//#CM567752
			// The maximum qty
			pgr_D.setMax(0);
			//#CM567753
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM567754
			// Starting position
			pgr_D.setIndex(0);

			//#CM567755
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM567756
			// Conceal the header
			lst_StrgInvntryLst.setVisible(false);
			//#CM567757
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM567758
	// Event handler methods -----------------------------------------
	//#CM567759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567760
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567761
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567762
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567763
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567764
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567768
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567770
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567771
	/** 
	 * Do the Processing when the Close button is pressed. <BR>
	 * <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM567772
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

	//#CM567773
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

	//#CM567774
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

	//#CM567775
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

	//#CM567776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567777
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgInvntryLst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgInvntryLst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567779
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgInvntryLst_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567780
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgInvntryLst_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM567781
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgInvntryLst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgInvntryLst_Change(ActionEvent e) throws Exception
	{
	}

	//#CM567783
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgInvntryLst_Click(ActionEvent e) throws Exception
	{
	}

	//#CM567784
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
		//#CM567785
		// Listbox is maintained in Session
		SessionInventoryListRet listbox = (SessionInventoryListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM567786
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
		//#CM567787
		// Listbox is maintained in Session
		SessionInventoryListRet listbox = (SessionInventoryListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM567788
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
		//#CM567789
		// Listbox is maintained in Session
		SessionInventoryListRet listbox = (SessionInventoryListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM567790
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
		//#CM567791
		// Listbox is maintained in Session
		SessionInventoryListRet listbox = (SessionInventoryListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM567792
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567793
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
		//#CM567794
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM567795
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM567796
				// Close the statement.
				finder.close();
			}
			//#CM567797
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM567798
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM567799
		// Return to the parents screen
		parentRedirect(null);
	}

	//#CM567800
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567801
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

}
//#CM567802
//end of class
