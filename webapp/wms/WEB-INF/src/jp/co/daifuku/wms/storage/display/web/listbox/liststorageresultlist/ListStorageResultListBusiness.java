// $Id: ListStorageResultListBusiness.java,v 1.2 2006/12/07 08:57:35 suresh Exp $

//#CM569661
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststorageresultlist;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragedate.ListStorageDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageQtyListRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM569662
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino <BR>
 * 
 * The storage results list screen class. <BR>
 * Retrieve it based on Consignor Code, Start Storage date, End Storage date, Item Code, and Case/Piece flag input from the parents screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Retrieve by making Consignor Code, Start Storage date, End Storage date, Item Code, and Case/Piece flag input from the parents screen as a key, and display it on the screen. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:35 $
 * @author  $Author: suresh $
 */
public class ListStorageResultListBusiness extends ListStorageResultList implements WMSConstants
{
	//#CM569663
	// Class fields --------------------------------------------------
	//#CM569664
	/** 
	 * The key used to hand over Case/Piece flag. 
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";
	//#CM569665
	// Class variables -----------------------------------------------

	//#CM569666
	// Class method --------------------------------------------------

	//#CM569667
	// Constructors --------------------------------------------------

	//#CM569668
	// Public methods ------------------------------------------------

	//#CM569669
	/**
	 * Initialize the screen. <BR>
	 * <DIR>
	 * Item <BR>
	 *	<DIR>
	 *    Storage date<BR>
	 *    Item Code<BR>
	 *    Flag<BR>
	 *    Storage Location<BR>
	 *    Packed qty per case<BR>
	 *    PlanCase qty<BR>
	 *    Result Case qty<BR>
	 *    Shortage Case qty<BR>
	 *    CaseITF<BR>
	 *    Expiry Date<BR>
	 *    Storage plan date<BR>
	 *    Item Name<BR>
	 *    Packed qty per bundle<BR>
	 *    PlanPiece qty<BR>
	 *    Result Piece qty<BR>
	 *    Shortage Piece qty<BR>
	 *    Bundle ITF<BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM569670
		// Set the Screen name
		//#CM569671
		// Storage results list
		lbl_ListName.setText(DisplayText.getText("TLE-W0070"));

		//#CM569672
		// Parameter is acquired. 
		//#CM569673
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM569674
		// Start Storage date
		String startstoragedate = request.getParameter(ListStorageDateBusiness.STARTSTORAGEDATE_KEY);		
		//#CM569675
		// End Storage date
		String endstoragedate = request.getParameter(ListStorageDateBusiness.ENDSTORAGEDATE_KEY);		
		//#CM569676
		// Item Code
		String itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM569677
		// Case/Piece flag
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		
		//#CM569678
		// Mandatory Input check and Restricted characters check of Consignor Code.
		if (!WmsCheckker
			.consignorCheck(consignorcode, lst_StorageQtyList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569679
		// Range check of Start and End Storage date
		if (!WmsCheckker
			.rangeStorageDateCheck(
				startstoragedate,
				endstoragedate,
				lst_StorageQtyList,
				pgr_U,
				pgr_D,
				lbl_InMsg))
		{
			return;
		}		
		//#CM569680
		// Restricted characters check of Item Code
		if (!WmsCheckker.charCheck(itemcode, lst_StorageQtyList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
						
		//#CM569681
		// Set Parameter and set Item which displays As it is. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDateStrt.setDate(WmsFormatter.toDate(startstoragedate));
		txt_FDateEd.setDate(WmsFormatter.toDate(endstoragedate));		
					
		//#CM569682
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM569683
			// Connection close
			sRet.closeConnection();
			//#CM569684
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}
		//#CM569685
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		//#CM569686
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM569687
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM569688
		// Start Storage date
		param.setFromStorageDate(WmsFormatter.toDate(startstoragedate));
		//#CM569689
		// End Storage date
		param.setToStorageDate(WmsFormatter.toDate(endstoragedate));		
		//#CM569690
		// Item Code
		param.setItemCode(itemcode);
		//#CM569691
		// Case/Piece flag
		//#CM569692
		//param.setCasePieceflgName(casepieceflag);	
		param.setCasePieceflg(casepieceflag);
		
		//#CM569693
		// Make SessionRet instance
		SessionStorageQtyListRet listbox = new SessionStorageQtyListRet(conn, param);
		//#CM569694
		// Listbox is maintained in Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
			
	}

	//#CM569695
	// Package methods -----------------------------------------------

	//#CM569696
	// Protected methods ---------------------------------------------

	//#CM569697
	// Private methods -----------------------------------------------
	//#CM569698
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired by the displayed page and data is set in List cell.  .<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * 		3.Set balloon information. <BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageQtyListRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setList(SessionStorageQtyListRet listbox, String actionName)
	{
	    Locale locale = this.getHttpRequest().getLocale();
		//#CM569699
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM569700
		// The retrieval result is acquired. 
		StorageSupportParameter[] storageparam = (StorageSupportParameter[])listbox.getEntities();

		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM569701
			// Consignor Name of the search condition is set. 
			lbl_JavaSetCnsgnrNm.setText(storageparam[0].getConsignorName());
			
			//#CM569702
			// Value set in pager
			//#CM569703
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM569704
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM569705
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM569706
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM569707
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM569708
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM569709
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM569710
			// Delete all lines
			lst_StorageQtyList.clearRow();
			
			//#CM569711
			//Use it with ToolTip
			String title_ItemName = DisplayText.getText("LBL-W0103");
			String title_ShortageQty = DisplayText.getText("LBL-W0208");
			String title_ShortagePieceQty = DisplayText.getText("LBL-W0209");			
			String title_CaseItf = DisplayText.getText("LBL-W0010");
			String title_BundleItf = DisplayText.getText("LBL-W0006");
			String title_UseByDate = DisplayText.getText("LBL-W0270");			
			
			String title_AreaTypeName = DisplayText.getText("LBL-W0569");
			
			for (int i = 0; i < len; i++)
			{
				//#CM569712
				// The final line is acquired
				int count = lst_StorageQtyList.getMaxRows();
				//#CM569713
				// Add row
				lst_StorageQtyList.addRow();

				//#CM569714
				// It moves to the final line. 
				lst_StorageQtyList.setCurrentRow(count);
				//#CM569715
				// Flag
				lst_StorageQtyList.setValue(0, storageparam[i].getCasePieceflg());
  				//#CM569716
  				// Storage date
				lst_StorageQtyList.setValue(1, WmsFormatter.toDispDate(storageparam[i].getStorageDate(),locale));
  				//#CM569717
  				// Item Code
				lst_StorageQtyList.setValue(2, storageparam[i].getItemCode());
				//#CM569718
				// Flag
				lst_StorageQtyList.setValue(3, storageparam[i].getCasePieceflgName());
				//#CM569719
				// Storage Location				
				lst_StorageQtyList.setValue(4, WmsFormatter.toDispLocation(
				        storageparam[i].getStorageLocation(),storageparam[i].getSystemDiscKey()));
				//#CM569720
				// Packed qty per case
				lst_StorageQtyList.setValue(5, WmsFormatter.getNumFormat(storageparam[i].getEnteringQty()));
				//#CM569721
				// PlanCase qty
				lst_StorageQtyList.setValue(6, WmsFormatter.getNumFormat(storageparam[i].getPlanCaseQty()));
				//#CM569722
				// Result Case qty
				lst_StorageQtyList.setValue(7, WmsFormatter.getNumFormat(storageparam[i].getResultCaseQty()));
				//#CM569723
				// Shortage Case qty			
				lst_StorageQtyList.setValue(8, WmsFormatter.getNumFormat(storageparam[i].getShortageQty()));
				//#CM569724
				// CaseITF					
				lst_StorageQtyList.setValue(9, storageparam[i].getITF());
				//#CM569725
				// Expiry Date		
				lst_StorageQtyList.setValue(10, storageparam[i].getUseByDate());
				//#CM569726
				// Storage plan date			
				lst_StorageQtyList.setValue(11, WmsFormatter.toDispDate(storageparam[i].getStoragePlanDate(), locale));
				//#CM569727
				// Item Name			
				lst_StorageQtyList.setValue(12, storageparam[i].getItemName());
				//#CM569728
				// Packed qty per bundle		
				lst_StorageQtyList.setValue(13, WmsFormatter.getNumFormat(storageparam[i].getBundleEnteringQty()));
				//#CM569729
				// PlanPiece qty			
				lst_StorageQtyList.setValue(14, WmsFormatter.getNumFormat(storageparam[i].getPlanPieceQty()));
				//#CM569730
				// Result Piece qty			
				lst_StorageQtyList.setValue(15, WmsFormatter.getNumFormat(storageparam[i].getResultPieceQty()));
				//#CM569731
				// Shortage Piece qty				
				lst_StorageQtyList.setValue(16, WmsFormatter.getNumFormat(storageparam[i].getShortagePieceQty()));
				//#CM569732
				// Bundle ITF								
				lst_StorageQtyList.setValue(17, storageparam[i].getBundleITF());
				
				//#CM569733
				//  Data for ToolTip is edited. 
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_ItemName, storageparam[i].getItemName());

				toolTip.add(title_ShortageQty, WmsFormatter.getNumFormat(storageparam[i].getShortageQty()));
				toolTip.add(title_ShortagePieceQty, WmsFormatter.getNumFormat(storageparam[i].getShortagePieceQty()));
				toolTip.add(title_CaseItf, storageparam[i].getITF());
				toolTip.add(title_BundleItf, storageparam[i].getBundleITF());
				toolTip.add(title_UseByDate, storageparam[i].getUseByDate());

				toolTip.add(title_AreaTypeName, storageparam[i].getRetrievalAreaName());
				
				//#CM569734
				// Set the tool tip
				lst_StorageQtyList.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM569735
			// Value set to Pager
			//#CM569736
			// The maximum qty
			pgr_U.setMax(0);
			//#CM569737
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM569738
			// Starting position
			pgr_U.setIndex(0);
			//#CM569739
			// The maximum qty
			pgr_D.setMax(0);
			//#CM569740
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM569741
			// Starting position
			pgr_D.setIndex(0);

			//#CM569742
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM569743
			// Conceal the header
			lst_StorageQtyList.setVisible(false);
			//#CM569744
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM569745
	// Event handler methods -----------------------------------------
	//#CM569746
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569747
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569748
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569749
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569750
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569751
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageDate_Server(ActionEvent e) throws Exception
	{
	}


	//#CM569752
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}


	//#CM569756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569760
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

	//#CM569761
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

	//#CM569762
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

	//#CM569763
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

	//#CM569764
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

	//#CM569765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageQtyList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageQtyList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569768
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageQtyList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM569769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageQtyList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM569770
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageQtyList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569771
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageQtyList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM569772
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageQtyList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM569773
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
		//#CM569774
		// Listbox is maintained in Session
		SessionStorageQtyListRet listbox = (SessionStorageQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM569775
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
		//#CM569776
		// Listbox is maintained in Session
		SessionStorageQtyListRet listbox = (SessionStorageQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM569777
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
		//#CM569778
		// Listbox is maintained in Session
		SessionStorageQtyListRet listbox = (SessionStorageQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM569779
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
		//#CM569780
		// Listbox is maintained in Session
		SessionStorageQtyListRet listbox = (SessionStorageQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM569781
	/** 
	 * 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569782
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
		//#CM569783
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM569784
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM569785
				// Close the statement.
				finder.close();
			}
			//#CM569786
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM569787
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM569788
		// Return to the parents screen
		parentRedirect(null);
	}

}
//#CM569789
//end of class
