// $Id: ListStoragePlanRegistBusiness.java,v 1.2 2006/12/07 08:57:36 suresh Exp $

//#CM569516
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanregist;
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
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragelocation.ListStorageLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStoragePlanRegistRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM569517
/**
 * Designer :T.Uehata<BR>
 * Maker : T.Uehata <BR>
 * <BR>
 * Storage Plan list (storage Plan Add) list box class.  <BR>
 * Retrieve it based on Consignor Code, Storage plan date, and Item Code input from the parents screen.  <BR>
 * <BR>
 * Process it in this class as follows.  <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method)  <BR>
 * <BR>
 * <DIR>
 * 		Retrieve by making Consignor Code, Storage plan date, and Item Code input from the parents screen as a key, and display it on the screen.  <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/16</TD><TD>T.Uehata</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:36 $
 * @author  $Author: suresh $
 */
public class ListStoragePlanRegistBusiness extends ListStoragePlanRegist implements WMSConstants
{	
	//#CM569518
	// KEY when maintaining it in session
	//#CM569519
	/**
	 * Content of List cell
	 */
	private static final String LISTBOX = "LISTBOX";
	
	
	//#CM569520
	/** 
	 * The key used to hand over Status. 
	 */
	public static final String STATUS_KEY = "STATUS_KEY";
	
	//#CM569521
	/** 
	 * The key used to hand over Case ITF. 
	 */
	public static final String CASEITF_KEY = "CASEITF_KEY";
	
	//#CM569522
	/** 
	 * The key used to hand over Bundle ITF. 
	 */
	public static final String BUNDLEITF_KEY = "BUNDLEITF_KEY";
	
	//#CM569523
	/** 
	 * The key used to hand over Packed qty per case. 
	 */
	public static final String CASEETR_KEY = "CASEETR_KEY";
	
	//#CM569524
	/** 
	 * The key used to hand over Packed qty per bundle. 
	 */
	public static final String BUNDLEETR_KEY = "BUNDLEETR_KEY";

	//#CM569525
	/** 
	 * The key used to hand over Case/Piece flag. 
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM569526
	// Class fields --------------------------------------------------

	//#CM569527
	// Class variables -----------------------------------------------

	//#CM569528
	// Class method --------------------------------------------------

	//#CM569529
	// Constructors --------------------------------------------------

	//#CM569530
	// Public methods ------------------------------------------------

	//#CM569531
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
	 *	  Item Name<BR>
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
		//#CM569532
		// Set the Screen name
		//#CM569533
		// TLE-W0069=Storage Plan list
		lbl_ListName.setText(DisplayText.getText("TLE-W0069"));
		
		//#CM569534
		// Parameter is acquired from Storage Plan correction deletion screen
		//#CM569535
		// Consignor Code
		String _consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM569536
		// Storage plan date
		String _storageplandate = request.getParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY);
		//#CM569537
		// Item Code
		String _itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM569538
		// Status
		String[] _status = request.getParameterValues(STATUS_KEY);
		
		//#CM569539
		// Do Input Check. 
		//#CM569540
		// Consignor Code
		if (!WmsCheckker.consignorCheck(_consignorcode, lst_ListStoragePlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569541
		// Storage plan date
		if (!WmsCheckker.charCheck(_storageplandate, lst_ListStoragePlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM569542
		// Item Code
		if (!WmsCheckker.charCheck(_itemcode, lst_ListStoragePlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		
		//#CM569543
		// Set Parameter and set Item which displays As it is. 
		//#CM569544
		// Consignor Code
		lbl_JavaSetCnsgnrCd.setText(_consignorcode);

		//#CM569545
		// Close the connection of the object left in Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute(LISTBOX);
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM569546
			// Connection close
			sRet.closeConnection();
			//#CM569547
			// Delete it from the session. 
			this.getSession().removeAttribute(LISTBOX);
		}
		
		//#CM569548
		// Acquire the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
		//#CM569549
		// Acquire display information on the screen. 
		//#CM569550
		// Set search condition Parameter. 
		StorageSupportParameter paramCont = new StorageSupportParameter();
		//#CM569551
		// Consignor Code
		paramCont.setConsignorCode(_consignorcode);
		//#CM569552
		// Storage plan date
		paramCont.setStoragePlanDate(_storageplandate);
		//#CM569553
		// Item Code
		paramCont.setItemCode(_itemcode);
		//#CM569554
		// Status
		paramCont.setSearchStatus(_status);

		//#CM569555
		// Acquisition of result
		//#CM569556
		//  instance generation
		SessionStoragePlanRegistRet listbox = new SessionStoragePlanRegistRet(conn, paramCont);
		//#CM569557
		// Listbox is maintained in Session
		this.getSession().setAttribute(LISTBOX, listbox);
		setList(listbox, "first");
	}

	//#CM569558
	// Package methods -----------------------------------------------

	//#CM569559
	// Protected methods ---------------------------------------------

	//#CM569560
	// Private methods -----------------------------------------------

	//#CM569561
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
	 * @param listbox SessionStoragePlanRegistRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setList(SessionStoragePlanRegistRet listbox, String actionName) throws Exception
	{
		//#CM569562
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM569563
		// The retrieval result is acquired. 
		StorageSupportParameter[] param = listbox.getEntities();
		
		//#CM569564
		// Acquisition of number of elements
		int len = 0;
		if (param != null)
		{
			len = param.length;
		}
		
		if (len > 0)
		{
			//#CM569565
			// Value set to Pager
			//#CM569566
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM569567
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM569568
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM569569
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM569570
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM569571
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM569572
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM569573
			// Set Item other than List cell. 
			lbl_JavaSetCnsgnrNm.setText(param[0].getConsignorName());

			//#CM569574
			// Delete all lines
			lst_ListStoragePlanRegist.clearRow();

			//#CM569575
			// Acquire the location. 
			Locale locale = this.httpRequest.getLocale();
			
			//#CM569576
			// Acquire the Tool tip label. 
			String ttLabel = DisplayText.getText("LBL-W0103");
			
			//#CM569577
			// The inside of List cell is made. 
			for (int i = 0; i < len; i++)
			{
				//#CM569578
				// The final line number is acquired. 
				int count = lst_ListStoragePlanRegist.getMaxRows();
				//#CM569579
				// Add 1 row to List cell (For the first line)
				lst_ListStoragePlanRegist.addRow();
				//#CM569580
				// It moves to the final line. 
				lst_ListStoragePlanRegist.setCurrentRow(count);
				
				//#CM569581
				// Set the retrieval result in List cell. 
				//#CM569582
				// Case/Piece flag(Concealed Item)
				lst_ListStoragePlanRegist.setValue(0, param[i].getCasePieceflg());
				//#CM569583
				// First line
				//#CM569584
				// Selection button (The line number is given to the label. )
				lst_ListStoragePlanRegist.setValue(1, Integer.toString(count + listbox.getCurrent()));
				//#CM569585
				// Storage plan date
				lst_ListStoragePlanRegist.setValue(2, WmsFormatter.toDispDate(param[i].getStoragePlanDate(), locale));
				//#CM569586
				// Item Code
				lst_ListStoragePlanRegist.setValue(3, param[i].getItemCode());
				//#CM569587
				// Case/Piece flag description
				lst_ListStoragePlanRegist.setValue(4, param[i].getCasePieceflgName());
				//#CM569588
				// Storage Location
				lst_ListStoragePlanRegist.setValue(5, param[i].getStorageLocation());
				//#CM569589
				// Packed qty per case
				lst_ListStoragePlanRegist.setValue(6, String.valueOf(param[i].getEnteringQty()));
				//#CM569590
				// CaseITF
				lst_ListStoragePlanRegist.setValue(7, param[i].getITF());
				//#CM569591
				// Item Name
				lst_ListStoragePlanRegist.setValue(8, param[i].getItemName());
				//#CM569592
				// Packed qty per bundle
				lst_ListStoragePlanRegist.setValue(9, String.valueOf(param[i].getBundleEnteringQty()));
				//#CM569593
				// Bundle ITF
				lst_ListStoragePlanRegist.setValue(10, param[i].getBundleITF());		
								
				//#CM569594
				//ToolTip
				ToolTipHelper tTip = new ToolTipHelper();
				//#CM569595
				// Item Name
				tTip.add(ttLabel, param[i].getItemName());
				lst_ListStoragePlanRegist.setToolTip(count, tTip.getText());
				
			}
		}
		else
		{
			//#CM569596
			// Value set to Pager
			//#CM569597
			// The maximum qty
			pgr_U.setMax(0);
			//#CM569598
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM569599
			// Starting position
			pgr_U.setIndex(0);
			//#CM569600
			// The maximum qty
			pgr_D.setMax(0);
			//#CM569601
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM569602
			// Starting position
			pgr_D.setIndex(0);

			//#CM569603
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM569604
			// Conceal the header
			lst_ListStoragePlanRegist.setVisible(false);
			//#CM569605
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	
	//#CM569606
	// Event handler methods -----------------------------------------
	//#CM569607
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569608
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569609
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569610
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569611
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569612
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569613
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

	//#CM569614
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

	//#CM569615
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

	//#CM569616
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

	//#CM569617
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

	//#CM569618
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569619
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanRegist_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569620
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanRegist_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569621
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanRegist_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM569622
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanRegist_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM569623
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanRegist_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569624
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStoragePlanRegist_Change(ActionEvent e) throws Exception
	{
	}

	//#CM569625
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
	 * Storage Location <BR>
	 * Packed qty per case <BR>
	 * Packed qty per bundle <BR>
	 * CaseITF <BR>
	 * Bundle ITF <BR>
	 * Case/Piece flag <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_ListStoragePlanRegist_Click(ActionEvent e) throws Exception
	{
		if (lst_ListStoragePlanRegist.getActiveCol() == 1)
		{
			//#CM569626
			// A present line is set. 
			lst_ListStoragePlanRegist.setCurrentRow(lst_ListStoragePlanRegist.getActiveRow());
			lst_ListStoragePlanRegist.getValue(1);

			//#CM569627
			// Set Parameter to be returned to parents screen
			ForwardParameters param = new ForwardParameters();
			//#CM569628
			// Consignor Code
			param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
			//#CM569629
			// Consignor Name
			param.setParameter(ListStorageConsignorBusiness.CONSIGNORNAME_KEY, lbl_JavaSetCnsgnrNm.getText());
			//#CM569630
			// Storage plan date
			param.setParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY, lst_ListStoragePlanRegist.getValue(2));
			//#CM569631
			// Item Code
			param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, lst_ListStoragePlanRegist.getValue(3));
			//#CM569632
			// Item Name
			param.setParameter(ListStorageItemBusiness.ITEMNAME_KEY, lst_ListStoragePlanRegist.getValue(8));
			//#CM569633
			// Storage Location
			param.setParameter(ListStorageLocationBusiness.LOCATION_KEY, lst_ListStoragePlanRegist.getValue(5));
			//#CM569634
			// Packed qty per case
			param.setParameter(CASEETR_KEY, lst_ListStoragePlanRegist.getValue(6));
			//#CM569635
			// Packed qty per bundle
			param.setParameter(BUNDLEETR_KEY, lst_ListStoragePlanRegist.getValue(9));
			//#CM569636
			// CaseITF
			param.setParameter(CASEITF_KEY, lst_ListStoragePlanRegist.getValue(7));
			//#CM569637
			// Bundle ITF
			param.setParameter(BUNDLEITF_KEY, lst_ListStoragePlanRegist.getValue(10));
			//#CM569638
			// Case , PieceFlag
			param.setParameter(CASEPIECEFLAG_KEY, lst_ListStoragePlanRegist.getValue(0));

			//#CM569639
			// Changes to the parents screen. 
			parentRedirect(param);
		}
	}


	//#CM569640
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
		//#CM569641
		// Listbox is maintained in Session
		SessionStoragePlanRegistRet listbox =
			(SessionStoragePlanRegistRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "next");
	}

	//#CM569642
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
		//#CM569643
		// Listbox is maintained in Session
		SessionStoragePlanRegistRet listbox =
			(SessionStoragePlanRegistRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "previous");
	}

	//#CM569644
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
		//#CM569645
		// Listbox is maintained in Session
		SessionStoragePlanRegistRet listbox =
			(SessionStoragePlanRegistRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "last");
	}

	//#CM569646
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
		//#CM569647
		// Listbox is maintained in Session
		SessionStoragePlanRegistRet listbox =
			(SessionStoragePlanRegistRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "first");
	}

	//#CM569648
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569649
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
		//#CM569650
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute(LISTBOX);
		//#CM569651
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM569652
				// Close the statement.
				finder.close();
			}
			//#CM569653
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM569654
		// Delete it from the session. 
		this.getSession().removeAttribute(LISTBOX);
		//#CM569655
		// Return to the parents screen
		parentRedirect(null);
	}

}
//#CM569656
//end of class
