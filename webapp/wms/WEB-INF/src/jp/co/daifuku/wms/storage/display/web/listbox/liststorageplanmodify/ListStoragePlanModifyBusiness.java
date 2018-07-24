// $Id: ListStoragePlanModifyBusiness.java,v 1.2 2006/12/07 08:57:37 suresh Exp $

//#CM569377
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanmodify;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
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
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStoragePlanModifyRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;


//#CM569378
/**
 * Designer :T.Uehata<BR>
 * Maker : T.Uehata <BR>
 * <BR>
 * Storage Plan list (Storage Plan correction deletion) list box class.  <BR>
 * Retrieve it based on Consignor Code, Storage plan date, Item Code, CaseStorage Location, and PieceStorage Location input from the parents screen.  <BR>
 * <BR>
 * Process it in this class as follows.  <BR>
 * <BR>
 * 1.Initial screen ( <CODE>page_Load</CODE>  Method)  <BR>
 * <BR>
 * <DIR>
 * 		Make Consignor Code, Storage plan date, Item Code, CaseStorage Location, and PieceStorage Location input from the parents screen as a key, retrieve, and display it on the screen.  <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/16</TD><TD>T.Uehata</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:37 $
 * @author  $Author: suresh $
 */
public class ListStoragePlanModifyBusiness extends ListStoragePlanModify implements WMSConstants
{	

	//#CM569379
	/**
	 * Content of List cell
	 */
	private static final String LISTBOX = "LISTBOX";
	
	
	//#CM569380
	/** 
	 * The key used to hand over Case Storage Location. 
	 */
	public static final String CASESTRGLOCATION_KEY = "CASESTRGLOCATION_KEY";
	
	//#CM569381
	/** 
	 * The key used to hand over Piece Storage Location. 
	 */
	public static final String PIECESTRGLOCATION_KEY = "PIECESTRGLOCATION_KEY";
	
	//#CM569382
	/** 
	 * The key used to hand over Status. 
	 */
	public static final String STATUS_KEY = "STATUS_KEY";
	
	//#CM569383
	// Class fields --------------------------------------------------

	//#CM569384
	// Class variables -----------------------------------------------

	//#CM569385
	// Class method --------------------------------------------------

	//#CM569386
	// Constructors --------------------------------------------------

	//#CM569387
	// Public methods ------------------------------------------------

	//#CM569388
	/**
	 * Initialize the screen.  <BR>
	 * <BR>
	 * <DIR>
	 * Item <BR>
	 *	<DIR>
	 *	  Storage plan date<BR>
	 *	  Item Code<BR>
	 *	  Item Name<BR>
	 *    CaseStorage Location<BR>
	 *	  PieceStorage Location<BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM569389
		// Set the Screen name
		//#CM569390
		// TLE-W0069=Storage Plan list
		lbl_ListName.setText(DisplayText.getText("TLE-W0069"));
		
		//#CM569391
		// Parameter is acquired from Storage Plan correction deletion screen
		//#CM569392
		// Consignor Code
		String _consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM569393
		// Storage plan date
		String _storageplandate = request.getParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY);
		//#CM569394
		// Item Code
		String _itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM569395
		// CaseStorage Location
		String _casestoragelocation = request.getParameter(CASESTRGLOCATION_KEY);
		//#CM569396
		// PieceStorage Location
		String _piecestoragelocation = request.getParameter(PIECESTRGLOCATION_KEY);
		//#CM569397
		// Status
		String[] _status = request.getParameterValues(STATUS_KEY);
		
		//#CM569398
		// Do Input Check. 
		//#CM569399
		// Consignor Code
		if (!WmsCheckker.consignorCheck(_consignorcode, lst_ListStoragePlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569400
		// Storage plan date
		if (!WmsCheckker.charCheck(_storageplandate, lst_ListStoragePlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569401
		// Item Code
		if (!WmsCheckker.charCheck(_itemcode, lst_ListStoragePlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569402
		// CaseStorage Location
		if (!WmsCheckker.charCheck(_casestoragelocation, lst_ListStoragePlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569403
		// PieceStorage Location
		if (!WmsCheckker.charCheck(_piecestoragelocation, lst_ListStoragePlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		
		//#CM569404
		// Set Parameter and set Item which displays As it is. 
		//#CM569405
		// Consignor Code
		lbl_JavaSetCnsgnrCd.setText(_consignorcode);

		//#CM569406
		// Close the connection of the object left in Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute(LISTBOX);
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM569407
			// Connection close
			sRet.closeConnection();
			//#CM569408
			// Delete it from the session. 
			this.getSession().removeAttribute(LISTBOX);
		}
		
		//#CM569409
		// Acquire the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
		//#CM569410
		// Acquire display information on the screen. 
		//#CM569411
		// Set search condition Parameter. 
		StorageSupportParameter paramCont = new StorageSupportParameter();
		//#CM569412
		// Consignor Code
		paramCont.setConsignorCode(_consignorcode);
		//#CM569413
		// Storage plan date
		paramCont.setStoragePlanDate(_storageplandate);
		//#CM569414
		// Item Code
		paramCont.setItemCode(_itemcode);
		//#CM569415
		// CaseStorage Location
		paramCont.setCaseLocation(_casestoragelocation);
		//#CM569416
		// PieceStorage Location
		paramCont.setPieceLocation(_piecestoragelocation);
		//#CM569417
		// Status
		paramCont.setSearchStatus(_status);

		//#CM569418
		// Acquisition of result
		//#CM569419
		//  Generate instance. 
		SessionStoragePlanModifyRet listbox = new SessionStoragePlanModifyRet(conn, paramCont);
		//#CM569420
		// Listbox is maintained in Session
		this.getSession().setAttribute(LISTBOX, listbox);
		setList(listbox, "first");
		
	}

	//#CM569421
	// Package methods -----------------------------------------------

	//#CM569422
	// Protected methods ---------------------------------------------

	//#CM569423
	// Private methods -----------------------------------------------

	//#CM569424
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
	 * @param listbox SessionStoragePlanModifyRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setList(SessionStoragePlanModifyRet listbox, String actionName) throws Exception
	{
		//#CM569425
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM569426
		// The retrieval result is acquired. 
		StorageSupportParameter[] param = listbox.getEntities();
		
		//#CM569427
		// Acquisition of number of elements
		int len = 0;
		if (param != null)
		{
			len = param.length;
		}
		
		if (len > 0)
		{
			//#CM569428
			// Value set to Pager
			//#CM569429
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM569430
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM569431
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM569432
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM569433
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM569434
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM569435
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM569436
			// Consignor Code
			lbl_JavaSetCnsgnrCd.setText(param[0].getConsignorCode());
			//#CM569437
			// Consignor Name
			lbl_JavaSetCnsgnrNm.setText(param[0].getConsignorName());

			//#CM569438
			// Delete all lines
			lst_ListStoragePlanModify.clearRow();
			
			//#CM569439
			// Acquire the location. 
			Locale locale = this.httpRequest.getLocale();
			
			//#CM569440
			// The inside of List cell is made. 
			for (int i = 0; i < len; i++)
			{
				//#CM569441
				// The final line number is acquired. 
				int count = lst_ListStoragePlanModify.getMaxRows();
				//#CM569442
				// Add 1 row to List cell (For the first line)
				lst_ListStoragePlanModify.addRow();
				//#CM569443
				// It moves to the final line. 
				lst_ListStoragePlanModify.setCurrentRow(count);
				
				//#CM569444
				// Set the retrieval result in List cell. 
				//#CM569445
				// First line
				//#CM569446
				// Selection button (The line number is given to the label. )
				lst_ListStoragePlanModify.setValue(1, Integer.toString(count + listbox.getCurrent()));
				//#CM569447
				// Storage plan date
				lst_ListStoragePlanModify.setValue(2, WmsFormatter.toDispDate(param[i].getStoragePlanDate(), locale));
				//#CM569448
				// Item Code
				lst_ListStoragePlanModify.setValue(3, param[i].getItemCode());
				//#CM569449
				// Item Name
				lst_ListStoragePlanModify.setValue(4, param[i].getItemName());
				//#CM569450
				// CaseStorage Location
				lst_ListStoragePlanModify.setValue(5, param[i].getCaseLocation());
				//#CM569451
				// PieceStorage Location
				lst_ListStoragePlanModify.setValue(6, param[i].getPieceLocation());		
								
				//#CM569452
				// Data for ToolTip is edited. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM569453
				// PieceStorage Location
				toolTip.add(DisplayText.getText("LBL-W0154"), param[i].getPieceLocation());

				//#CM569454
				//Set the tool tip in Current line. 
				lst_ListStoragePlanModify.setToolTip(lst_ListStoragePlanModify.getCurrentRow(), toolTip.getText());
			}
		}
		else
		{
			//#CM569455
			// Value set to Pager
			//#CM569456
			// The maximum qty
			pgr_U.setMax(0);
			//#CM569457
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM569458
			// Starting position
			pgr_U.setIndex(0);
			//#CM569459
			// The maximum qty
			pgr_D.setMax(0);
			//#CM569460
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM569461
			// Starting position
			pgr_D.setIndex(0);

			//#CM569462
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM569463
			// Conceal the header
			lst_ListStoragePlanModify.setVisible(false);
			//#CM569464
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM569465
	// Event handler methods -----------------------------------------
	//#CM569466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569467
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569468
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569469
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569470
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569471
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}


	//#CM569472
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

	//#CM569473
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

	//#CM569474
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

	//#CM569475
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

	//#CM569476
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

	//#CM569477
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569478
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanModify_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569479
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanModify_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569480
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanModify_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM569481
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanModify_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM569482
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanModify_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569483
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanModify_Change(ActionEvent e) throws Exception
	{
	}

	//#CM569484
	/** 
	 * Process when Selection button of List cell is pressed.  <BR>
	 * <BR>
	 *	Pass selected information to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * [Return Data]<BR>
	 * <DIR>
	 * Consignor Code <BR>
	 * Consignor Name <BR>
	 * Storage plan date <BR>
	 * Item Code <BR>
	 * Item Name <BR>
	 * CaseStorage Location <BR>
	 * PieceStorage Location <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_ListStoragePlanModify_Click(ActionEvent e) throws Exception
	{
		if (lst_ListStoragePlanModify.getActiveCol() == 1)
		{
			//#CM569485
			// A present line is set. 
			lst_ListStoragePlanModify.setCurrentRow(lst_ListStoragePlanModify.getActiveRow());
			lst_ListStoragePlanModify.getValue(1);

			//#CM569486
			// Set Parameter to be returned to parents screen
			ForwardParameters param = new ForwardParameters();
			//#CM569487
			// Consignor Code
			param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
			//#CM569488
			// Consignor Name
			param.setParameter(ListStorageConsignorBusiness.CONSIGNORNAME_KEY, lbl_JavaSetCnsgnrNm.getText());
			//#CM569489
			// Storage plan date
			param.setParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY, lst_ListStoragePlanModify.getValue(2));
			//#CM569490
			// Item Code
			param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, lst_ListStoragePlanModify.getValue(3));
			//#CM569491
			// Item Name
			param.setParameter(ListStorageItemBusiness.ITEMNAME_KEY, lst_ListStoragePlanModify.getValue(4));
			//#CM569492
			// CaseStorage Location
			param.setParameter(CASESTRGLOCATION_KEY, lst_ListStoragePlanModify.getValue(5));
			//#CM569493
			// PieceStorage Location
			param.setParameter(PIECESTRGLOCATION_KEY, lst_ListStoragePlanModify.getValue(6));

			//#CM569494
			// Changes to the parents screen. 
			parentRedirect(param);
		}
	}

	//#CM569495
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
		//#CM569496
		// Listbox is maintained in Session
		SessionStoragePlanModifyRet listbox =
			(SessionStoragePlanModifyRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "next");
	}

	//#CM569497
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
		//#CM569498
		// Listbox is maintained in Session
		SessionStoragePlanModifyRet listbox =
			(SessionStoragePlanModifyRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "previous");
	}

	//#CM569499
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
		//#CM569500
		// Listbox is maintained in Session
		SessionStoragePlanModifyRet listbox =
			(SessionStoragePlanModifyRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "last");
	}

	//#CM569501
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
		//#CM569502
		// Listbox is maintained in Session
		SessionStoragePlanModifyRet listbox =
			(SessionStoragePlanModifyRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "first");
	}

	//#CM569503
	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569504
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
		//#CM569505
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute(LISTBOX);
		//#CM569506
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM569507
				// Close the statement.
				finder.close();
			}
			//#CM569508
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM569509
		// Delete it from the session. 
		this.getSession().removeAttribute(LISTBOX);
		//#CM569510
		// Return to the parents screen
		parentRedirect(null);
	}
}
//#CM569511
//end of class
