// $Id: ListStoragePlanListBusiness.java,v 1.2 2006/12/07 08:57:37 suresh Exp $

//#CM569257
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanlist;
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
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragelist.ListStorageListBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStoragePlanListRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM569258
/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * Stock Plan list retrieval list box class. <BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:37 $
 * @author  $Author: suresh $
 */
public class ListStoragePlanListBusiness extends ListStoragePlanList implements WMSConstants
{
	//#CM569259
	// Class fields --------------------------------------------------
	//#CM569260
	/** 
	 * The key used to hand over Case/Piece flag. 
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";
	
	//#CM569261
	/** 
	 * Work The key used to hand over Status. 
	 */
	public static final String STATUS_FLAG_KEY = "STATUS_FLAG_KEY";
	//#CM569262
	// Class variables -----------------------------------------------

	//#CM569263
	// Class method --------------------------------------------------

	//#CM569264
	// Constructors --------------------------------------------------

	//#CM569265
	// Public methods ------------------------------------------------

	//#CM569266
	/**
	 * Initialize the screen.  <BR>
	 * <BR>
	 * <DIR>
	 * Item <BR>
	 *	<DIR>
	 *	  Storage plan date<BR>
	 *	  Item Code<BR>
	 *	  Flag<BR>
	 *    Storage Location<BR>
	 *	  Packed qty per case<BR>
	 *	  CaseITF<BR>
	 *    Item Name<BR>
	 *	  Packed qty per bundle<BR>
	 *    Bundle ITF<BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM569267
		// Set the Screen name
		//#CM569268
		// Storage Plan list
		lbl_ListName.setText(DisplayText.getText("TLE-W0069"));
		
		//#CM569269
		// Parameter is acquired. 
		//#CM569270
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM569271
		// Start Storage plan date
		String startstorageplandate = request.getParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY);
		//#CM569272
		// End Storage plan date
		String endstorageplandate = request.getParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY);
		//#CM569273
		// Item Code
		String itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM569274
		// Case/Piece flag
		String casepieceflag = request.getParameter(ListStorageListBusiness.CASEPIECEFLAG_KEY);
		//#CM569275
		// Work Status
		String statusflag = request.getParameter(ListStorageListBusiness.STATUS_FLAG_KEY);
		
		//#CM569276
		// Mandatory Input check and Restricted characters check of Consignor Code.
		if (!WmsCheckker
			.consignorCheck(consignorcode, lst_StoragePlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569277
		// Restricted characters check of Item Code
		if (!WmsCheckker.charCheck(itemcode, lst_StoragePlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569278
		// Big and small comparisons of Storage plan date
		if(!WmsCheckker.rangeStoragePlanDateCheck(
			startstorageplandate, 
			endstorageplandate,
			lst_StoragePlanList,
			pgr_U, 
			pgr_D, 
			lbl_InMsg
				))
		{
			return;
		}


		//#CM569279
		// The search condition is displayed on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDateStrt.setDate(WmsFormatter.toDate(startstorageplandate));
		txt_FDateEd.setDate(WmsFormatter.toDate(endstorageplandate));
		
		//#CM569280
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM569281
			// Connection close
			sRet.closeConnection();
			//#CM569282
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM569283
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM569284
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM569285
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM569286
		// Start Storage plan date
		param.setFromStoragePlanDate(startstorageplandate);
		//#CM569287
		// End Storage plan date
		param.setToStoragePlanDate(endstorageplandate);
		//#CM569288
		// Item Code
		param.setItemCode(itemcode);
		//#CM569289
		// Case/Piece flag
		param.setCasePieceflg(casepieceflag);
		//#CM569290
		// Work Status
		param.setWorkStatus(statusflag);
		
		//#CM569291
		// SessionStoragePlanListRet  instance generation
		SessionStoragePlanListRet listbox = new SessionStoragePlanListRet(conn, param);
		//#CM569292
		// Listbox is maintained in Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM569293
	// Package methods -----------------------------------------------

	//#CM569294
	// Protected methods ---------------------------------------------

	//#CM569295
	// Private methods -----------------------------------------------

	//#CM569296
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
	 * @param listbox SessionStoragePlanListRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setList(SessionStoragePlanListRet listbox, String actionName)
	{
		//#CM569297
		// The locale is acquired. 
		Locale locale = this.getHttpRequest().getLocale();
		
		//#CM569298
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM569299
		// The retrieval result is acquired. 
		StorageSupportParameter[] sparam = (StorageSupportParameter[])listbox.getEntities();
		int len = 0;
		if (sparam != null)
			len = sparam.length;
		if (len > 0)
		{
			//#CM569300
			// Consignor Name of the search condition is set. 
			lbl_JavaSetCnsgnrNm.setText(sparam[0].getConsignorName());
			
			//#CM569301
			// Value set to Pager
			//#CM569302
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM569303
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM569304
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM569305
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM569306
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM569307
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM569308
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM569309
			// Delete all lines
			lst_StoragePlanList.clearRow();

			//#CM569310
			//Use it with ToolTip
			String title_ItemName = DisplayText.getText("LBL-W0103");
			String title_CaseItf = DisplayText.getText("LBL-W0010");
			String title_BundleItf = DisplayText.getText("LBL-W0006");
			
			for (int i = 0; i < len; i++)
			{
				//#CM569311
				// The final line is acquired
				int count = lst_StoragePlanList.getMaxRows();
				//#CM569312
				// Add row
				lst_StoragePlanList.addRow();

				//#CM569313
				// It moves to the final line. 
				lst_StoragePlanList.setCurrentRow(count);
				lst_StoragePlanList.setValue(0, sparam[i].getCasePieceflg());
				lst_StoragePlanList.setValue(1, WmsFormatter.toDispDate(sparam[i].getStoragePlanDate(), locale));
				lst_StoragePlanList.setValue(2, sparam[i].getItemCode());
				lst_StoragePlanList.setValue(3, sparam[i].getCasePieceflgName());
				lst_StoragePlanList.setValue(4, sparam[i].getCaseLocation());
				lst_StoragePlanList.setValue(5, WmsFormatter.getNumFormat(sparam[i].getEnteringQty()));
				lst_StoragePlanList.setValue(6, WmsFormatter.getNumFormat(sparam[i].getPlanCaseQty()));
				lst_StoragePlanList.setValue(7, WmsFormatter.getNumFormat(sparam[i].getResultCaseQty()));
				lst_StoragePlanList.setValue(8, sparam[i].getWorkStatusName());
				lst_StoragePlanList.setValue(9, sparam[i].getITF());
				lst_StoragePlanList.setValue(10, sparam[i].getItemName());
				lst_StoragePlanList.setValue(11, sparam[i].getPieceLocation());
				lst_StoragePlanList.setValue(12, WmsFormatter.getNumFormat(sparam[i].getBundleEnteringQty()));
				lst_StoragePlanList.setValue(13, WmsFormatter.getNumFormat(sparam[i].getPlanPieceQty()));
				lst_StoragePlanList.setValue(14, WmsFormatter.getNumFormat(sparam[i].getResultPieceQty()));
				lst_StoragePlanList.setValue(15, sparam[i].getBundleITF());
				
				//#CM569314
				//  Data for ToolTip is edited. 
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_ItemName, sparam[i].getItemName());
				toolTip.add(title_CaseItf, sparam[i].getITF());
				toolTip.add(title_BundleItf, sparam[i].getBundleITF());

				//#CM569315
				// Set the tool tip
				lst_StoragePlanList.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM569316
			// Value set to Pager
			//#CM569317
			// The maximum qty
			pgr_U.setMax(0);
			//#CM569318
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM569319
			// Starting position
			pgr_U.setIndex(0);
			//#CM569320
			// The maximum qty
			pgr_D.setMax(0);
			//#CM569321
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM569322
			// Starting position
			pgr_D.setIndex(0);

			//#CM569323
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM569324
			// Conceal the header
			lst_StoragePlanList.setVisible(false);
			//#CM569325
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
		
	}

	//#CM569326
	// Event handler methods -----------------------------------------
	//#CM569327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569329
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569330
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569331
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569332
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStrtStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569333
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetEdStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569335
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569336
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

	//#CM569337
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

	//#CM569338
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

	//#CM569339
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

	//#CM569340
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


	//#CM569341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM569345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM569346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM569348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM569349
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
		//#CM569350
		// Listbox is maintained in Session
		SessionStoragePlanListRet listbox = (SessionStoragePlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM569351
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
		//#CM569352
		// Listbox is maintained in Session
		SessionStoragePlanListRet listbox = (SessionStoragePlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM569353
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
		//#CM569354
		// Listbox is maintained in Session
		SessionStoragePlanListRet listbox = (SessionStoragePlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM569355
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
		//#CM569356
		// Listbox is maintained in Session
		SessionStoragePlanListRet listbox = (SessionStoragePlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM569357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569358
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
		//#CM569359
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM569360
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM569361
				// Close the statement.
				finder.close();
			}
			//#CM569362
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM569363
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM569364
		// Return to the parents screen
		parentRedirect(null);
	}
	//#CM569365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569366
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569368
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569369
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569370
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569371
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

}
//#CM569372
//end of class
