// $Id: ListAsConsignorBusiness.java,v 1.2 2006/10/26 05:19:16 suresh Exp $

//#CM38403
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsConsignorStockRet;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsConsignorWorkInfoRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM38404
/**
 * <FONT COLOR="BLUE">
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * The Consignor Retrieval list box class. <BR>
 * Retrieve it based on Warehouse No and Consignor code input from the parents screen.<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Make Warehouse No and Consignor code input from the parents screen as a key, retrieve, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_ConsignorSearch_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass consignor code of the selection line and consignor name to the parents screen, and close the list box.<BR>
 * <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/05</TD><TD>K.Toda</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:19:16 $
 * @author  $Author: suresh $
 */
public class ListAsConsignorBusiness extends ListAsConsignor implements WMSConstants
{
	//#CM38405
	// Class fields --------------------------------------------------
	//#CM38406
	/** 
	 * key to transfer consignor code
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	//#CM38407
	/** 
	 * key to transfer consignor name
	 */
	public static final String CONSIGNORNAME_KEY = "CONSIGNORNAME_KEY";

	//#CM38408
	/** 
	 * key to transfer search flag
	 */
	public static final String SEARCHITEM_KEY = "SEARCHITEM_KEY";


	//#CM38409
	// Class variables -----------------------------------------------

	//#CM38410
	// Class method --------------------------------------------------

	//#CM38411
	// Constructors --------------------------------------------------

	//#CM38412
	// Public methods ------------------------------------------------

	//#CM38413
	/**
	 * initial screen display<BR>
	 * <DIR>
	 *	item <BR>
	 *	<DIR>
	 *		select <BR>
	 *		consignor code <BR>
	 *		consignor name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM38414
		// set screen name
		//#CM38415
		// Consignor Retrieval
		lbl_ListName.setText(DisplayText.getText("TLE-W0012"));

		//#CM38416
		// fetch parameter
		//#CM38417
		// warehouse (area no.)
		String areano = request.getParameter(ListAsLocationBusiness.AREANO_KEY);
		//#CM38418
		// consignor code
		String consignorcode = request.getParameter(CONSIGNORCODE_KEY);
		//#CM38419
		// Retrieval flag(Stock or Work)
		String searchitem = request.getParameter(SEARCHITEM_KEY);
		this.getViewState().setString(SEARCHITEM_KEY, searchitem);

		//#CM38420
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM38421
			// close connection
			sRet.closeConnection();
			//#CM38422
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM38423
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM38424
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM38425
		// warehouse (area no.)
		param.setAreaNo(areano);
		//#CM38426
		// consignor code
		param.setConsignorCode(consignorcode);

		//#CM38427
		// Decide the work whether retrieved Consignor code is stock or not.
		if (searchitem.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38428
			// SessionAsConsignorStockRet instance generation
			SessionAsConsignorStockRet listbox = new SessionAsConsignorStockRet(conn, param);
			//#CM38429
			//save the listbox in session
			this.getSession().setAttribute("LISTBOX", listbox);
			setStockList(listbox, "first");
		}
		else if (searchitem.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38430
			// SessionAsConsignorWorkInfoRet instance generation
			SessionAsConsignorWorkInfoRet listbox = new SessionAsConsignorWorkInfoRet(conn, param);
			//#CM38431
			//save the listbox in session
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkList(listbox, "first");
		}
	}

	//#CM38432
	// Package methods -----------------------------------------------

	//#CM38433
	// Protected methods ---------------------------------------------

	//#CM38434
	// Private methods -----------------------------------------------
	//#CM38435
	/**
	 * method to change page <BR>
	 * Retrieve Stock Table. <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setStockList(SessionAsConsignorStockRet listbox, String actionName)
		throws Exception
	{
		//#CM38436
		// set page info
		listbox.setActionName(actionName);

		//#CM38437
		// fetch search result
		AsScheduleParameter[] rstock = (AsScheduleParameter[]) listbox.getEntities();
		int len = 0;

		if (rstock != null)
			len = rstock.length;
		if (len > 0)
		{
			//#CM38438
			// set value to Pager
			//#CM38439
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM38440
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM38441
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM38442
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM38443
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM38444
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM38445
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM38446
			// delete all rows
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM38447
				// fetch last row
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM38448
				// add row
				lst_ConsignorSearch.addRow();

				//#CM38449
				// move to last record
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, rstock[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, rstock[i].getConsignorName());
			}
		}
		else
		{
			//#CM38450
			// set value to Pager
			//#CM38451
			// max. number
			pgr_U.setMax(0);
			//#CM38452
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM38453
			// start position
			pgr_U.setIndex(0);
			//#CM38454
			// max. number
			pgr_D.setMax(0);
			//#CM38455
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM38456
			// start position
			pgr_D.setIndex(0);

			//#CM38457
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM38458
			// hide the header
			lst_ConsignorSearch.setVisible(false);
			//#CM38459
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM38460
	/**
	 * method to change page <BR>
	 * Retrieve the Work table.<BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setWorkList(SessionAsConsignorWorkInfoRet listbox, String actionName)
		throws Exception
	{
		//#CM38461
		// set page info
		listbox.setActionName(actionName);

		//#CM38462
		// fetch search result
		AsScheduleParameter[] rview = (AsScheduleParameter[])listbox.getEntities();
		int len = 0;
		if (rview != null)
			len = rview.length;
		if (len > 0)
		{
			//#CM38463
			// set value to Pager
			//#CM38464
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM38465
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM38466
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM38467
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM38468
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM38469
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM38470
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM38471
			// delete all rows
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM38472
				// fetch last row
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM38473
				// add row
				lst_ConsignorSearch.addRow();

				//#CM38474
				// move to last record
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, rview[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, rview[i].getConsignorName());
			}
		}
		else
		{
			//#CM38475
			// set value to Pager
			//#CM38476
			// max. number
			pgr_U.setMax(0);
			//#CM38477
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM38478
			// start position
			pgr_U.setIndex(0);
			//#CM38479
			// max. number
			pgr_D.setMax(0);
			//#CM38480
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM38481
			// start position
			pgr_D.setIndex(0);

			//#CM38482
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM38483
			// hide the header
			lst_ConsignorSearch.setVisible(false);
			//#CM38484
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM38485
	// Event handler methods -----------------------------------------
	//#CM38486
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38487
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38488
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

	//#CM38489
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

	//#CM38490
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

	//#CM38491
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

	//#CM38492
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

	//#CM38493
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM38495
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM38496
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM38497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM38498
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38499
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM38500
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	pass consignor code, consignor name to the caller screen and close the list box <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_ConsignorSearch_Click(ActionEvent e) throws Exception
	{

		//#CM38501
		// set the current row
		lst_ConsignorSearch.setCurrentRow(lst_ConsignorSearch.getActiveRow());
		lst_ConsignorSearch.getValue(1);

		//#CM38502
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM38503
		// consignor code
		param.setParameter(CONSIGNORCODE_KEY, lst_ConsignorSearch.getValue(2));
		//#CM38504
		// consignor name
		param.setParameter(CONSIGNORNAME_KEY, lst_ConsignorSearch.getValue(3));

		//#CM38505
		// move to the caller screen
		parentRedirect(param);
		
	}

	//#CM38506
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
		//#CM38507
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38508
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38509
			//save the listbox in session
			SessionAsConsignorStockRet listbox = (SessionAsConsignorStockRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "next");
		}
		//#CM38510
		// work
		else if (flag.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38511
			//save the listbox in session
			SessionAsConsignorWorkInfoRet listbox = (SessionAsConsignorWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "next");
		}
	}

	//#CM38512
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
		//#CM38513
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38514
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38515
			//save the listbox in session
			SessionAsConsignorStockRet listbox = (SessionAsConsignorStockRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "previous");
		}
		//#CM38516
		// work
		else if (flag.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38517
			//save the listbox in session
			SessionAsConsignorWorkInfoRet listbox = (SessionAsConsignorWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "previous");
		}
	}

	//#CM38518
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
		//#CM38519
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38520
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38521
			//save the listbox in session
			SessionAsConsignorStockRet listbox = (SessionAsConsignorStockRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "last");
		}
		//#CM38522
		// work
		else if (flag.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38523
			//save the listbox in session
			SessionAsConsignorWorkInfoRet listbox = (SessionAsConsignorWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "last");
		}
	}

	//#CM38524
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
		//#CM38525
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38526
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38527
			//save the listbox in session
			SessionAsConsignorStockRet listbox = (SessionAsConsignorStockRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "first");
		}
		//#CM38528
		// work
		else if (flag.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38529
			//save the listbox in session
			SessionAsConsignorWorkInfoRet listbox = (SessionAsConsignorWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "first");
		}
	}

	//#CM38530
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38531
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
		//#CM38532
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM38533
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM38534
				// close the statement object
				finder.close();
			}
			//#CM38535
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM38536
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM38537
		// return to origin screen
		parentRedirect(null);
	}

}
//#CM38538
//end of class
