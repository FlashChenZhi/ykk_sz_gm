// $Id: ListAsStockDetailBusiness.java,v 1.2 2006/10/26 05:34:45 suresh Exp $

//#CM39548
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetail;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsStockDetailRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM39549
/**
 * <FONT COLOR="BLUE">
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * The location details retrieval class. <BR>
 * Retrieve it based on Warehouse and Location No input from the parents screen. <BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Retrieve it based on Warehouse and Location No input from the parents screen. <BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_LocationDetil_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass the following item of the selection line to the parents screen, and close the list box. <BR>
 *  <DIR>
 *    consignor code <BR>
 *    consignor name <BR>
 *    item code <BR>
 *    item name <BR>
 *    case stock qty <BR>
 *    packed qty per case <BR>
 *    piece stock qty <BR>
 *    packed qty per piece <BR>
 *    Case ITF <BR>
 *    bundle itf <BR>
 *    case piece flag <BR>
 *    storage date/time <BR>
 *    expiry date <BR>
 *    stock id <BR>
 *    storage type<BR>
 *    update date/time<BR>
 *    job type<BR>
 *  </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/31</TD><TD>M.Koyama</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:34:45 $
 * @author  $Author: suresh $
 */
public class ListAsStockDetailBusiness extends ListAsStockDetail implements WMSConstants
{
	//#CM39550
	// Class fields --------------------------------------------------
	//** 検索用 **//
	//#CM39551
	/** 
	 * key to transfer warehouse
	 */
	public static final String WAREHOUSE_KEY = "WAREHOUSE_KEY";

	//#CM39552
	/** 
	 * key to transfer warehouse name
	 */
	public static final String WAREHOUSENAME_KEY = "WAREHOUSENAME_KEY";

	//#CM39553
	/** 
	 * key to transfer location no
	 */
	public static final String LOCATION_KEY = "LOCATION_KEY";
	
	
	//** 表示用 **//
	//#CM39554
	/** 
	 * The key used to hand over stock case qty.
	 */
	public static final String CASEQTY_KEY = "CASEQTY_KEY";

	//#CM39555
	/** 
	 * key to transfer entering qty
	 */
	public static final String ENTERING_KEY = "ENTERING_KEY";

	//#CM39556
	/** 
	 * key to transfer case itf
	 */
	public static final String ITF_KEY = "ITF_KEY";

	//#CM39557
	/** 
	 * The key used to hand over stock piece qty.
	 */
	public static final String PIECEQTY_KEY = "PIECEQTY_KEY";

	//#CM39558
	/** 
	 * The key used to hand over packed qty per piece.
	 */
	public static final String BUNDLEENTERING_KEY = "BUNDLEENTERING_KEY";

	//#CM39559
	/** 
	 * The key used to hand over Bundle ITF. 
	 */
	public static final String BUNDLEITF_KEY = "BUNDLEITF_KEY";

	//#CM39560
	/** 
	 * key to transfer case/piece flag
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM39561
	/** 
	 * The key used for the delivery at the Storage date. 
	 */
	public static final String STORINGDATE_KEY = "STORINGDATE_KEY";

	//#CM39562
	/** 
	 * The key used to hand over Expiry date.
	 */
	public static final String USEBYDATE_KEY = "USEBYDATE_KEY";

	//#CM39563
	/** 
	 * The key used to hand over stockID. 
	 */
	public static final String STOCKID_KEY = "STOCKID_KEY";

	//#CM39564
	/** 
	 * The key used to hand over Last updated date and time. 
	 */
	public static final String LASTUPDATE_KEY = "LASTUPDATE_KEY";

	//#CM39565
	/** 
	 * The key used to hand over the Storage flag.
	 */
	public static final String RESTORING_KEY = "RESTORING_KEY";

	//#CM39566
	/** 
	 * The key used to hand over the button. 
	 */
	public static final String PROCESSTYPE_KEY = "PROCESSTYPE_KEY";

	//#CM39567
	// Class variables -----------------------------------------------

	//#CM39568
	// Class method --------------------------------------------------

	//#CM39569
	// Constructors --------------------------------------------------

	//#CM39570
	// Public methods ------------------------------------------------

	//#CM39571
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM39572
		// set screen name
		//#CM39573
		//search stock list
		lbl_ListName.setText(DisplayText.getText("TLE-W0905"));

		//#CM39574
		// fetch parameter
		//#CM39575
		// warehouse station no.
		String warehouse = request.getParameter(WAREHOUSE_KEY);
		//#CM39576
		// warehouse name
		String whName = request.getParameter(WAREHOUSENAME_KEY);
		//#CM39577
		// Location No (stationNo.)
		String locationno = request.getParameter(LOCATION_KEY);
		
		//#CM39578
		// Retain the button type in ViewState. 
		this.getViewState().setString(PROCESSTYPE_KEY, request.getParameter(PROCESSTYPE_KEY));

		//#CM39579
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM39580
		// set header item other than listcell
		//#CM39581
		// warehouse name
		if (StringUtil.isBlank(whName))
		{
			ASFindUtil util = new ASFindUtil(conn);
			lbl_JavaSetWareHouse.setText(util.getWareHouseName(warehouse));
		}
		else
		{
			lbl_JavaSetWareHouse.setText(whName);
		}
		//#CM39582
		// location no.
		lbl_JavaSetLocationNo.setText(DisplayText.formatDispLocation(locationno));

		//#CM39583
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM39584
			// close connection
			sRet.closeConnection();
			//#CM39585
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM39586
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM39587
		// warehouse
		param.setWareHouseNo(warehouse);
		//#CM39588
		// location no.
		param.setLocationNo(locationno);

		//#CM39589
		// generate SessionRet instance
		SessionAsStockDetailRet listbox = new SessionAsStockDetailRet(conn, param);
		//#CM39590
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM39591
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM39592
	// Package methods -----------------------------------------------

	//#CM39593
	// Protected methods ---------------------------------------------
	//#CM39594
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsStockDetailRet listbox, String actionName)
	{
		//#CM39595
		// set page info
		listbox.setActionName(actionName);

		//#CM39596
		// fetch search result
		AsScheduleParameter[] rparam = (AsScheduleParameter[])listbox.getEntities();

		int len = 0;
		if (rparam != null)
			len = rparam.length;
		if (len > 0)
		{
			//#CM39597
			// set value in pager
			//#CM39598
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM39599
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM39600
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM39601
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM39602
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM39603
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM39604
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM39605
			// delete all rows
			lst_LocationDetil.clearRow();

			//#CM39606
			// used in tool tip
			String title_ConsignorName = DisplayText.getText("LBL-W0026");
			String title_ItemName = DisplayText.getText("LBL-W0103");
			String title_StorageDate = DisplayText.getText("LBL-W0237");
			String title_StorageTime = DisplayText.getText("LBL-W0368");
			String title_UseByDate = DisplayText.getText("LBL-W0270");

			//#CM39607
			// used in date display
			Locale locale = this.getHttpRequest().getLocale();

			for (int i = 0; i < len; i++)
			{
				//#CM39608
				// fetch last row
				int count = lst_LocationDetil.getMaxRows();
				//#CM39609
				// add row
				lst_LocationDetil.addRow();

				//#CM39610
				// move to last record
				lst_LocationDetil.setCurrentRow(count);
				lst_LocationDetil.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_LocationDetil.setValue(2, rparam[i].getConsignorCode());
				lst_LocationDetil.setValue(3, rparam[i].getItemCode());
				lst_LocationDetil.setValue(4, WmsFormatter.getNumFormat(rparam[i].getEnteringQty()));
				lst_LocationDetil.setValue(5, WmsFormatter.getNumFormat(rparam[i].getStockCaseQty())); 
				lst_LocationDetil.setValue(6, rparam[i].getITF());
				lst_LocationDetil.setValue(7, DisplayUtil.getPieceCaseValue(rparam[i].getCasePieceFlag()));
				lst_LocationDetil.setValue(8, WmsFormatter.toDispDate(WmsFormatter.toParamDate(rparam[i].getInStockDate()), locale));
				lst_LocationDetil.setValue(9, rparam[i].getUseByDate());
				lst_LocationDetil.setValue(10, rparam[i].getConsignorName());
				lst_LocationDetil.setValue(11, rparam[i].getItemName());
				lst_LocationDetil.setValue(12, WmsFormatter.getNumFormat(rparam[i].getBundleEnteringQty()));
				lst_LocationDetil.setValue(13, WmsFormatter.getNumFormat(rparam[i].getStockPieceQty())); 
				lst_LocationDetil.setValue(14, rparam[i].getBundleITF());
				lst_LocationDetil.setValue(15, WmsFormatter.getTimeFormat(rparam[i].getInStockDate(), ""));

				//#CM39611
				// set hidden item
				//#CM39612
				// store case piece flag + stock id
				List Hiden = new Vector();
				Hiden.add(rparam[i].getCasePieceFlag());
				Hiden.add(rparam[i].getStockId());					
				Hiden.add(rparam[i].getStoringStatus());					
				Hiden.add(WmsFormatter.getTimeStampString(rparam[i].getLastUpdateDate()));
				Hiden.add(WmsFormatter.getTimeStampString(rparam[i].getInStockDate()));

				lst_LocationDetil.setValue(0, CollectionUtils.getConnectedString(Hiden));

				//#CM39613
				// add data to tool tip
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_ConsignorName, rparam[i].getConsignorName());
				toolTip.add(title_ItemName, rparam[i].getItemName());
				toolTip.add(title_StorageDate, WmsFormatter.getDateFormat(rparam[i].getInStockDate(), ""));
				toolTip.add(title_StorageTime, WmsFormatter.getTimeFormat(rparam[i].getInStockDate(), ""));
				toolTip.add(title_UseByDate, rparam[i].getUseByDate());

				//#CM39614
				// set tool tip	
				lst_LocationDetil.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM39615
			// set value to Pager
			//#CM39616
			// max. number
			pgr_U.setMax(0);
			//#CM39617
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM39618
			// start position
			pgr_U.setIndex(0);
			//#CM39619
			// max. number
			pgr_D.setMax(0);
			//#CM39620
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM39621
			// start position
			pgr_D.setIndex(0);

			//#CM39622
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM39623
			// hide the header
			lst_LocationDetil.setVisible(false);
			//#CM39624
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM39625
	// Private methods -----------------------------------------------

	//#CM39626
	// Event handler methods -----------------------------------------
	//#CM39627
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39628
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39629
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39630
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39631
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39632
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39633
	/**
	 * call the [Close] button click event process <BR>
	 *  <BR>
	 * close the listbox and return to the caller screen<BR>
	 *  <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM39634
	/**
	 * call the [>] button click event process <BR>
	 * <BR>
	 * display the next page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM39635
	/**
	 * call the [<] button click event process <BR>
	 * <BR>
	 * display the previous page<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM39636
	/**
	 * call the [>>] button click event process <BR>
	 * <BR>
	 * display the last page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM39637
	/**
	 * call the [<<] button click event process<BR>
	 * <BR>
	 *display the first page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM39638
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39639
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetil_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39640
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetil_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39641
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetil_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM39642
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetil_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM39643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetil_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationDetil_Change(ActionEvent e) throws Exception
	{
	}

	//#CM39645
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	pass the detailed contents to caller screen and close the list box <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_LocationDetil_Click(ActionEvent e) throws Exception
	{
		//#CM39646
		// set the current row
		lst_LocationDetil.setCurrentRow(lst_LocationDetil.getActiveRow());
		lst_LocationDetil.getValue(1);

		//#CM39647
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM39648
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, lst_LocationDetil.getValue(2));
		//#CM39649
		// consignor name
		param.setParameter(ListAsConsignorBusiness.CONSIGNORNAME_KEY, lst_LocationDetil.getValue(10));
		//#CM39650
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, lst_LocationDetil.getValue(3));
		//#CM39651
		// item name
		param.setParameter(ListAsItemBusiness.ITEMNAME_KEY, lst_LocationDetil.getValue(11));
		//#CM39652
		// packed qty per case
		param.setParameter(ENTERING_KEY, lst_LocationDetil.getValue(4));
		//#CM39653
		// case stock qty
		param.setParameter(CASEQTY_KEY, lst_LocationDetil.getValue(5));
		//#CM39654
		// Case ITF
		param.setParameter(ITF_KEY, lst_LocationDetil.getValue(6));
		//#CM39655
		// expiry date
		param.setParameter(USEBYDATE_KEY, lst_LocationDetil.getValue(9));
		//#CM39656
		// packed qty per piece
		param.setParameter(BUNDLEENTERING_KEY, lst_LocationDetil.getValue(12));
		//#CM39657
		// stock piece qty
		param.setParameter(PIECEQTY_KEY, lst_LocationDetil.getValue(13));
		//#CM39658
		// bundle itf
		param.setParameter(BUNDLEITF_KEY, lst_LocationDetil.getValue(14));
		//#CM39659
		// case piece flag
		param.setParameter(CASEPIECEFLAG_KEY,  CollectionUtils.getString(0,lst_LocationDetil.getValue(0)));
		//#CM39660
		// stock id
		param.setParameter(STOCKID_KEY,  CollectionUtils.getString(1,lst_LocationDetil.getValue(0)));
		//#CM39661
		// storage type
		param.setParameter(RESTORING_KEY,  CollectionUtils.getString(2, lst_LocationDetil.getValue(0)));
		//#CM39662
		// update date/time
		param.setParameter(LASTUPDATE_KEY,  CollectionUtils.getString(3, lst_LocationDetil.getValue(0)));
		//#CM39663
		// storage date/time
		param.setParameter(STORINGDATE_KEY, CollectionUtils.getString(4, lst_LocationDetil.getValue(0)));
		//#CM39664
		// job type
		param.setParameter(PROCESSTYPE_KEY, this.getViewState().getString(PROCESSTYPE_KEY));

		//#CM39665
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM39666
	/** 
	 * call the [>] button click event process <BR>
	 * <BR>
	 * display the next page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM39667
		//save the listbox in session
		SessionAsStockDetailRet listbox = (SessionAsStockDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM39668
	/** 
	 * call the [<] button click event process <BR>
	 * <BR>
	 * display the previous page<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM39669
		//save the listbox in session
		SessionAsStockDetailRet listbox = (SessionAsStockDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM39670
	/** 
	 * call the [>>] button click event process <BR>
	 * <BR>
	 * display the last page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM39671
		//save the listbox in session
		SessionAsStockDetailRet listbox = (SessionAsStockDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM39672
	/** 
	 * call the [<<] button click event process<BR>
	 * <BR>
	 *display the first page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM39673
		//save the listbox in session
		SessionAsStockDetailRet listbox = (SessionAsStockDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM39674
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39675
	/** 
	 * call the [Close] button click event process <BR>
	 *  <BR>
	 * close the listbox and return to the caller screen<BR>
	 *  <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		
		//#CM39676
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM39677
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM39678
				// close the statement object
				finder.close();
			}
			//#CM39679
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM39680
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM39681
		// return to origin screen
		parentRedirect(null);
	}


	//#CM39682
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetLocationNo_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM39683
//end of class
