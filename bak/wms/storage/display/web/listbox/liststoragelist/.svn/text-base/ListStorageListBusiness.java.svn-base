// $Id: ListStorageListBusiness.java,v 1.2 2006/12/07 08:57:40 suresh Exp $

//#CM568784
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststoragelist;
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
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageListRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM568785
/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR> 
 * Storage Work list retrieval list box class. <BR>
 * Retrieve it based on Consignor Code, Start Storage plan date, End Storage plan date, Item Code, Case/Piece flag, and Work Status input from the parents screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Retrieve by making Consignor Code, Start Storage plan date, End Storage plan date, Item Code, Case/Piece flag, and Work Status input from the parents screen as a key, and display it on the screen. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:40 $
 * @author  $Author: suresh $
 */
public class ListStorageListBusiness extends ListStorageList implements WMSConstants
{
	//#CM568786
	// Class fields --------------------------------------------------
	//#CM568787
	/** 
	 * The key used to hand over Case/Piece flag. 
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";
	
	//#CM568788
	/** 
	 * Work The key used to hand over Status. 
	 */
	public static final String STATUS_FLAG_KEY = "STATUS_FLAG_KEY";
	//#CM568789
	// Class variables -----------------------------------------------

	//#CM568790
	// Class method --------------------------------------------------

	//#CM568791
	// Constructors --------------------------------------------------

	//#CM568792
	// Public methods ------------------------------------------------

	//#CM568793
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Storage plan date <BR>
	 *		Item Code <BR>
	 *		Flag <BR>
	 *		Storage Location <BR>
	 *		Packed qty per case <BR>
	 *		Plan total qty<BR>
	 *		PlanCase qty <BR>
	 *		CaseITF <BR>
	 *		Expiry Date <BR>
	 *		Item Name <BR>
	 *		Packed qty per bundle <BR>
	 *		PlanPiece qty <BR>
	 *		Bundle ITF <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM568794
		// Set the Screen name
		//#CM568795
		// Storage Work list
		lbl_ListName.setText(DisplayText.getText("TLE-W0068"));
		
		//#CM568796
		// Parameter is acquired. 
		//#CM568797
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM568798
		// Start Storage plan date
		String startstorageplandate = request.getParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY);
		//#CM568799
		// End Storage plan date
		String endstorageplandate = request.getParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY);
		//#CM568800
		// Item Code
		String itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM568801
		// Case/Piece flag
		String casepieceflag = request.getParameter(ListStorageListBusiness.CASEPIECEFLAG_KEY);
		//#CM568802
		// Work Status
		String statusflag = request.getParameter(ListStorageListBusiness.STATUS_FLAG_KEY);
		
		//#CM568803
		// Mandatory Input check and Restricted characters check of Consignor Code.
		if (!WmsCheckker
			.consignorCheck(consignorcode, lst_StorageList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM568804
		// Restricted characters check of Item Code
		if (!WmsCheckker.charCheck(itemcode, lst_StorageList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM568805
		// Big and small comparisons of Storage plan date
		if(!WmsCheckker.rangeStoragePlanDateCheck(
			startstorageplandate, 
			endstorageplandate,
			lst_StorageList,
			pgr_U, 
			pgr_D, 
			lbl_InMsg
				))
		{
			return;
		}


		//#CM568806
		// The search condition is displayed on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDateStrt.setDate(WmsFormatter.toDate(startstorageplandate));
		txt_FDateEd.setDate(WmsFormatter.toDate(endstorageplandate));
		
		//#CM568807
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM568808
			// Connection close
			sRet.closeConnection();
			//#CM568809
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM568810
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM568811
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM568812
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM568813
		// Start Storage plan date
		param.setFromStoragePlanDate(startstorageplandate);
		//#CM568814
		// End Storage plan date
		param.setToStoragePlanDate(endstorageplandate);
		//#CM568815
		// Item Code
		param.setItemCode(itemcode);
		//#CM568816
		// Case/Piece flag
		param.setCasePieceflg(casepieceflag);
		//#CM568817
		// Work Status
		param.setStatusFlagL(statusflag);
		
		//#CM568818
		// SessionShippingListRet  instance generation
		SessionStorageListRet listbox = new SessionStorageListRet(conn, param);
		//#CM568819
		// Listbox is maintained in Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM568820
	// Package methods -----------------------------------------------

	//#CM568821
	// Protected methods ---------------------------------------------

	//#CM568822
	// Private methods -----------------------------------------------
	//#CM568823
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
	 * @param listbox SessionInventoryListRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setList(SessionStorageListRet listbox, String actionName)
	{
		//#CM568824
		// The locale is acquired. 
		Locale locale = this.getHttpRequest().getLocale();
		
		//#CM568825
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568826
		// The retrieval result is acquired. 
		StorageSupportParameter[] sparam = (StorageSupportParameter[])listbox.getEntities();
		int len = 0;
		if (sparam != null)
			len = sparam.length;
		if (len > 0)
		{
			//#CM568827
			// Consignor Name of the search condition is set. 
			lbl_JavaSetCnsgnrNm.setText(sparam[0].getConsignorName());
			
			//#CM568828
			// Value set to Pager
			//#CM568829
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568830
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568831
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568832
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568833
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568834
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568835
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568836
			// Delete all lines
			lst_StorageList.clearRow();

			//#CM568837
			//Use it with ToolTip
			String title_ItemName = DisplayText.getText("LBL-W0103");
			String title_CaseItf = DisplayText.getText("LBL-W0010");
			String title_BundleItf = DisplayText.getText("LBL-W0006");
			String title_UseByDate = DisplayText.getText("LBL-W0270");
			
			for (int i = 0; i < len; i++)
			{
				//#CM568838
				// The final line is acquired
				int count = lst_StorageList.getMaxRows();
				//#CM568839
				// Add row
				lst_StorageList.addRow();

				//#CM568840
				// It moves to the final line. 
				lst_StorageList.setCurrentRow(count);
				lst_StorageList.setValue(0, sparam[i].getCasePieceflg());
				lst_StorageList.setValue(1, WmsFormatter.toDispDate(sparam[i].getStoragePlanDate(), locale));
				lst_StorageList.setValue(2, sparam[i].getItemCode());
				lst_StorageList.setValue(3, sparam[i].getCasePieceflgName());
				lst_StorageList.setValue(4, sparam[i].getStorageLocation());
				lst_StorageList.setValue(5, WmsFormatter.getNumFormat(sparam[i].getEnteringQty()));
				lst_StorageList.setValue(6, WmsFormatter.getNumFormat(sparam[i].getTotalPlanQty()));
				lst_StorageList.setValue(7, WmsFormatter.getNumFormat(sparam[i].getPlanCaseQty()));
				lst_StorageList.setValue(8, sparam[i].getITF());
				lst_StorageList.setValue(9, sparam[i].getUseByDate());
				lst_StorageList.setValue(10, sparam[i].getItemName());
				lst_StorageList.setValue(11, WmsFormatter.getNumFormat(sparam[i].getBundleEnteringQty()));
				lst_StorageList.setValue(12, WmsFormatter.getNumFormat(sparam[i].getPlanPieceQty()));
				lst_StorageList.setValue(13, sparam[i].getBundleITF());
				
				//#CM568841
				//  Data for ToolTip is edited. 
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_ItemName, sparam[i].getItemName());
				toolTip.add(title_CaseItf, sparam[i].getITF());
				toolTip.add(title_BundleItf, sparam[i].getBundleITF());
				toolTip.add(title_UseByDate, sparam[i].getUseByDate());

				//#CM568842
				// Set the tool tip
				lst_StorageList.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM568843
			// Value set to Pager
			//#CM568844
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568845
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568846
			// Starting position
			pgr_U.setIndex(0);
			//#CM568847
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568848
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568849
			// Starting position
			pgr_D.setIndex(0);

			//#CM568850
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568851
			// Conceal the header
			lst_StorageList.setVisible(false);
			//#CM568852
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
		
	}

	//#CM568853
	// Event handler methods -----------------------------------------
	//#CM568854
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}


	//#CM568855
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568856
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568857
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568858
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568859
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void W_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568860
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStrtStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568861
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568862
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetEdStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568863
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568864
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

	//#CM568865
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

	//#CM568866
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

	//#CM568867
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

	//#CM568868
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


	//#CM568869
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568870
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM568871
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM568872
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM568873
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM568874
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM568876
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StorageList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM568877
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
		//#CM568878
		// Listbox is maintained in Session
		SessionStorageListRet listbox = (SessionStorageListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM568879
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
		//#CM568880
		// Listbox is maintained in Session
		SessionStorageListRet listbox = (SessionStorageListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM568881
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
		//#CM568882
		// Listbox is maintained in Session
		SessionStorageListRet listbox = (SessionStorageListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM568883
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
		//#CM568884
		// Listbox is maintained in Session
		SessionStorageListRet listbox = (SessionStorageListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM568885
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568886
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
		//#CM568887
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM568888
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM568889
				// Close the statement.
				finder.close();
			}
			//#CM568890
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM568891
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM568892
		// Return to the parents screen
		parentRedirect(null);
	}


	//#CM568893
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568894
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM568895
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM568896
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568897
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM568898
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_TabKey(ActionEvent e) throws Exception
	{
	}


}
//#CM568899
//end of class
