// $Id: ListStorageStockMoveBusiness.java,v 1.2 2006/12/07 08:57:34 suresh Exp $

//#CM569794
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststoragestockmove;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststockmoveusebydate.ListStockMoveUseByDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageStockMoveRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM569795
/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * Stock list (stock movement) list box class. <BR>
 * Retrieve it based on Consignor Code, Item Code, Location before movement, and Expiry Date input from the parents screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load(ActionEvent e)</CODE> Method) <BR>
 * <BR>
 * <DIR>
 *   Retrieve Consignor Code, Item Code, Location before movement, and Expiry Date input from the parents screen to the key, and display it on the screen. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:34 $
 * @author  $Author: suresh $
 */
public class ListStorageStockMoveBusiness extends ListStorageStockMove implements WMSConstants
{
	//#CM569796
	// Class fields --------------------------------------------------
	//#CM569797
	/** 
	 * The key used to hand over Packed qty per case. 
	 */
	public static final String ENTERING_KEY = "ENTERING_KEY";
	//#CM569798
	/** 
	 * The key used to hand over Case qty. 
	 */
	public static final String CASEQTY_KEY = "CASEQTY_KEY";
	//#CM569799
	/** 
	 * The key used to hand over Piece qty. 
	 */
	public static final String PIECEQTY_KEY = "PIECEQTY_KEY";
	

	//#CM569800
	// Class variables -----------------------------------------------

	//#CM569801
	// Class method --------------------------------------------------

	//#CM569802
	// Constructors --------------------------------------------------

	//#CM569803
	// Public methods ------------------------------------------------

	//#CM569804
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Item Code <BR>
	 *		Item Name <BR>
	 *		Location <BR>
	 *		Packed qty per case <BR>
	 *		Packed qty per bundle <BR>
	 *		Allocated Case qty <BR>
	 *		Allocated Piece qty <BR>
	 * 		Expiry Date <BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM569805
		// Set the Screen name
		//#CM569806
		// TLE-W0086=Stock List
		lbl_ListName.setText(DisplayText.getText("TLE-W0086"));

		//#CM569807
		// Parameter is acquired. 
		//#CM569808
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM569809
		// Item Code
		String itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM569810
		// Location before movement
		String sourcelocationno =
			request.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		//#CM569811
		// Expiry Date
		String usebydate = request.getParameter(ListStockMoveUseByDateBusiness.USEBYDATE_KEY);
		//#CM569812
		// Area type flag
		String areatypeflag = request.getParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY);

		//#CM569813
		// Mandatory Input check and Restricted characters check of Consignor Code.
		if (!WmsCheckker
			.consignorCheck(consignorcode, lst_ListStorageStockMove, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569814
		// Restricted characters check of Item Code
		if (!WmsCheckker.charCheck(itemcode, lst_ListStorageStockMove, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569815
		// Restricted characters check of Location before movement
		if (!WmsCheckker
			.charCheck(sourcelocationno, lst_ListStorageStockMove, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569816
		// Restricted characters check of Expiry Date
		if (!WmsCheckker
			.charCheck(usebydate, lst_ListStorageStockMove, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM569817
		// The search condition is displayed on the screen. 
		//#CM569818
		// Consignor Code
		lbl_JavaSetCnsgnrCd.setText(consignorcode);

		//#CM569819
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM569820
			// Connection close
			sRet.closeConnection();
			//#CM569821
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM569822
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM569823
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM569824
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM569825
		// Item Code
		param.setItemCode(itemcode);
		//#CM569826
		// Location before movement
		param.setSourceLocationNo(sourcelocationno);
		//#CM569827
		// Expiry Date
		param.setUseByDate(usebydate);
		//#CM569828
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);

		//#CM569829
		// SessionItemTotalInquiryRet  instance generation
		SessionStorageStockMoveRet listbox =
			new SessionStorageStockMoveRet(conn, param);
		//#CM569830
		// Listbox is maintained in Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM569831
	// Package methods -----------------------------------------------

	//#CM569832
	// Protected methods ---------------------------------------------

	//#CM569833
	// Private methods -----------------------------------------------

	//#CM569834
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
	 * @param listbox SessionStorageStockMoveRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setList(SessionStorageStockMoveRet listbox, String actionName)
		throws Exception
	{
		//#CM569835
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM569836
		// The retrieval result is acquired. 
		StorageSupportParameter[] stcparam = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (stcparam != null)
			len = stcparam.length;
		if (len > 0)
		{
			//#CM569837
			// Consignor
			//#CM569838
			// Set Consignor Code
			lbl_JavaSetCnsgnrNm.setText(stcparam[0].getConsignorName());
			//#CM569839
			// Set Consignor Name
			lbl_JavaSetCnsgnrNm.setText(stcparam[0].getConsignorName());
			
			//#CM569840
			// Value set to Pager
			//#CM569841
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM569842
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM569843
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM569844
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM569845
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM569846
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM569847
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM569848
			// Delete all lines
			lst_ListStorageStockMove.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM569849
				// The final line is acquired
				int count = lst_ListStorageStockMove.getMaxRows();
				//#CM569850
				// Add row
				lst_ListStorageStockMove.addRow();

				//#CM569851
				// It moves to the final line. 
				lst_ListStorageStockMove.setCurrentRow(count);
				lst_ListStorageStockMove.setValue(
					1,
					Integer.toString(count + listbox.getCurrent()));
				lst_ListStorageStockMove.setValue(2, stcparam[i].getItemCode());
				lst_ListStorageStockMove.setValue(3, stcparam[i].getLocation());
				lst_ListStorageStockMove.setValue(
					4,
					WmsFormatter.getNumFormat(stcparam[i].getEnteringQty()));
				lst_ListStorageStockMove.setValue(
					5,
					WmsFormatter.getNumFormat(stcparam[i].getTotalStockCaseQty()));
				lst_ListStorageStockMove.setValue(6, stcparam[i].getUseByDate());
				lst_ListStorageStockMove.setValue(7, stcparam[i].getItemName());
				lst_ListStorageStockMove.setValue(
					8,
					WmsFormatter.getNumFormat(stcparam[i].getBundleEnteringQty()));
				lst_ListStorageStockMove.setValue(
					9,
					WmsFormatter.getNumFormat(stcparam[i].getTotalStockPieceQty()));
			}
		}
		else
		{
			//#CM569852
			// Value set to Pager
			//#CM569853
			// The maximum qty
			pgr_U.setMax(0);
			//#CM569854
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM569855
			// Starting position
			pgr_U.setIndex(0);
			//#CM569856
			// The maximum qty
			pgr_D.setMax(0);
			//#CM569857
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM569858
			// Starting position
			pgr_D.setIndex(0);

			//#CM569859
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM569860
			// Conceal the header
			lst_ListStorageStockMove.setVisible(false);
			//#CM569861
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM569862
	// Event handler methods -----------------------------------------
	//#CM569863
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569864
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569865
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569866
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569867
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569868
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569869
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

	//#CM569870
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

	//#CM569871
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

	//#CM569872
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

	//#CM569873
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

	//#CM569874
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageStockMove_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569876
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageStockMove_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569877
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageStockMove_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM569878
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageStockMove_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM569879
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageStockMove_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569880
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageStockMove_Change(ActionEvent e) throws Exception
	{
	}

	//#CM569881
	/** 
	 * Process when Selection button of List cell is pressed.  <BR>
	 * <BR>
	 * Pass selected information to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * <DIR>
	 *	[Return Data] <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Consignor Name <BR>
	 *		Item Code <BR>
	 *		Item Name <BR>
	 *		Location before movement <BR>
	 *		Packed qty per case <BR>
	 *		Expiry Date <BR>
	 *		Allocated Case qty <BR>
	 *		Allocated Piece qty <BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_ListStorageStockMove_Click(ActionEvent e) throws Exception
	{
		//#CM569882
		// A present line is set. 
		lst_ListStorageStockMove.setCurrentRow(lst_ListStorageStockMove.getActiveRow());
		lst_ListStorageStockMove.getValue(1);

		//#CM569883
		// Set Parameter to be returned to parents screen
		ForwardParameters param = new ForwardParameters();
		//#CM569884
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetCnsgnrCd.getText());
		//#CM569885
		// Consignor Name
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORNAME_KEY,
			lbl_JavaSetCnsgnrNm.getText());
		//#CM569886
		// Item Code
		param.setParameter(
			ListStorageItemBusiness.ITEMCODE_KEY,
			lst_ListStorageStockMove.getValue(2));
		//#CM569887
		// Item Name
		param.setParameter(
			ListStorageItemBusiness.ITEMNAME_KEY,
			lst_ListStorageStockMove.getValue(7));
		//#CM569888
		// Location before movement
		param.setParameter(
			ListInventoryLocationBusiness.STARTLOCATION_KEY,
			lst_ListStorageStockMove.getValue(3));
		//#CM569889
		// Packed qty per case
		param.setParameter(
			ListStorageStockMoveBusiness.ENTERING_KEY,
			lst_ListStorageStockMove.getValue(4));
		//#CM569890
		// Expiry Date
		param.setParameter(
			ListStockMoveUseByDateBusiness.USEBYDATE_KEY,
			lst_ListStorageStockMove.getValue(6));
		//#CM569891
		// Allocated Case qty
		param.setParameter(
			ListStorageStockMoveBusiness.CASEQTY_KEY,
			lst_ListStorageStockMove.getValue(5));
		//#CM569892
		// Allocated Piece qty
		param.setParameter(
			ListStorageStockMoveBusiness.PIECEQTY_KEY,
			lst_ListStorageStockMove.getValue(9));

		//#CM569893
		// Changes to the parents screen. 
		parentRedirect(param);
	}

	//#CM569894
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
		//#CM569895
		// Listbox is maintained in Session
		SessionStorageStockMoveRet listbox =
			(SessionStorageStockMoveRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM569896
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
		//#CM569897
		// Listbox is maintained in Session
		SessionStorageStockMoveRet listbox =
			(SessionStorageStockMoveRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM569898
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
		//#CM569899
		// Listbox is maintained in Session
		SessionStorageStockMoveRet listbox =
			(SessionStorageStockMoveRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM569900
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
		//#CM569901
		// Listbox is maintained in Session
		SessionStorageStockMoveRet listbox =
			(SessionStorageStockMoveRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM569902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569903
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
		//#CM569904
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM569905
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM569906
				// Close the statement.
				finder.close();
			}
			//#CM569907
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM569908
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM569909
		// Return to the parents screen
		parentRedirect(null);
	}

}
//#CM569910
//end of class
