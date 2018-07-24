// $Id: ListAsItemBusiness.java,v 1.2 2006/10/26 05:20:08 suresh Exp $

//#CM38543
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsItemStockRet;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsItemWorkInfoRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;

//#CM38544
/**
 * <FONT COLOR="BLUE">
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * item search listbox class<BR>
 * Retrieve it based on Warehouse No, Consignor code, Location No , Storage Plan Date, and Item code input from the parents screen.<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Make Warehouse No, Consignor code, Location No , Storage Plan Date, and Item code input from the parents screen as a key, retrieve, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_ItemSearch_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass Item code and Item description of the selection line to the parents screen, and close the list box.<BR>
 * <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/05</TD><TD>K.Toda</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:20:08 $
 * @author  $Author: suresh $
 */
public class ListAsItemBusiness extends ListAsItem implements WMSConstants
{
	//#CM38545
	// Class fields --------------------------------------------------
	//#CM38546
	/** 
	 * key to transfer item code
	 */
	public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

	//#CM38547
	/** 
	 * key to transfer item name
	 */
	public static final String ITEMNAME_KEY = "ITEMNAME_KEY";

	//#CM38548
	/** 
	 * key to transfer search flag
	 */
	public static final String SEARCHITEM_KEY = "SEARCHITEM_KEY";

	//#CM38549
	/** 
	 * The key used to hand over Start Item code information. 
	 */
	public static final String STARTITEM_KEY = "STARTITEM_KEY";

	//#CM38550
	/** 
	 * The key used to hand over End Item code information. 
	 */
	public static final String ENDITEM_KEY = "ENDITEM_KEY";
	
	//#CM38551
	/** 
	 * The key used to hand over the commodity range flag. 
	 */
	public static final String RANGEITEM_KEY = "RANGEITEM_KEY";
	
	//#CM38552
	/** 
	 * this key is used to transfer storage plan date
	 */
	public static final String PLANDATE_KEY = "PLANDATE_KEY";

	//#CM38553
	// Class variables -----------------------------------------------

	//#CM38554
	// Class method --------------------------------------------------

	//#CM38555
	// Constructors --------------------------------------------------

	//#CM38556
	// Public methods ------------------------------------------------

	//#CM38557
	/**
	 * initial screen display<BR>
	 * <DIR>
	 *	item <BR>
	 *	<DIR>
	 *		select <BR>
	 *		item code <BR>
	 *		item name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM38558
		// set screen name
		//#CM38559
		// Commodity retrieval
		lbl_ListName.setText(DisplayText.getText("TLE-W0043"));

		//#CM38560
		// fetch parameter
		//#CM38561
		// warehouse (area no.)
		String areano = request.getParameter(ListAsLocationBusiness.AREANO_KEY);
		//#CM38562
		// consignor code
		String consignorcode = request.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM38563
		// start location
		String startlocation = request.getParameter(ListAsLocationBusiness.STARTLOCATION_KEY);
		//#CM38564
		// end location
		String endlocation = request.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		//#CM38565
		// item code
		String itemcode = request.getParameter(ITEMCODE_KEY);
		//#CM38566
		// start item code
		String startitemcode = request.getParameter(STARTITEM_KEY);
		//#CM38567
		// end item code
		String enditemcode = request.getParameter(ENDITEM_KEY);
		//#CM38568
		// storage plan date
		String plandate = request.getParameter(PLANDATE_KEY);
		//#CM38569
		// range flag
		String rangeitem = request.getParameter(RANGEITEM_KEY);

		//#CM38570
		// Retrieval flag(stock or Work)
		String searchitem = request.getParameter(SEARCHITEM_KEY);

		this.getViewState().setString(RANGEITEM_KEY, rangeitem);
		this.getViewState().setString(SEARCHITEM_KEY, searchitem);

		//#CM38571
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM38572
			// close connection
			sRet.closeConnection();
			//#CM38573
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM38574
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM38575
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM38576
		// warehouse (area no.)
		param.setAreaNo(areano);
		//#CM38577
		// consignor code
		param.setConsignorCode(consignorcode);
		//#CM38578
		// start location
		param.setFromLocationNo(startlocation);
		//#CM38579
		// end location
		param.setToLocationNo(endlocation);
		//#CM38580
		// item code
		param.setItemCode(itemcode);
		//#CM38581
		// storage plan date
		param.setPlanDate(plandate);
		
		if (!StringUtil.isBlank(rangeitem))
		{
			//#CM38582
			// start item code
			if (rangeitem.equals(AsScheduleParameter.RANGE_START))
			{
				param.setStartItemCode(startitemcode);
			}
			
			//#CM38583
			// end item code
			if (rangeitem.equals(AsScheduleParameter.RANGE_END))
			{
				param.setEndItemCode(enditemcode);
			}
		}
		
		//#CM38584
		// Whether stock or the work of retrieved Item code is judged. 
		if (searchitem.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38585
			// SessionAsItemStockRet instance generation
			SessionAsItemStockRet listbox = new SessionAsItemStockRet(conn, param);
			//#CM38586
			//save the listbox in session
			this.getSession().setAttribute("LISTBOX", listbox);
			setStockList(listbox, "first");
		}
		else if (searchitem.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38587
			// SessionAsItemWorkInfoRet instance generation
			SessionAsItemWorkInfoRet listbox = new SessionAsItemWorkInfoRet(conn, param);
			//#CM38588
			//save the listbox in session
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkList(listbox, "first");
		}

	}

	//#CM38589
	// Package methods -----------------------------------------------

	//#CM38590
	// Protected methods ---------------------------------------------

	//#CM38591
	// Private methods -----------------------------------------------
	//#CM38592
	/**
	 * method to change page <BR>
	 * Retrieve Stock Table. <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setStockList(SessionAsItemStockRet listbox, String actionName)
		throws Exception
	{
		//#CM38593
		// set page info
		listbox.setActionName(actionName);

		//#CM38594
		// fetch search result
		AsScheduleParameter[] irparam = (AsScheduleParameter[]) listbox.getEntities();
		int len = 0;
		if (irparam != null)
			len = irparam.length;
		if (len > 0)
		{
			//#CM38595
			// set value to Pager
			//#CM38596
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM38597
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM38598
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM38599
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM38600
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM38601
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM38602
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM38603
			// delete all rows
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM38604
				// fetch last row
				int count = lst_ItemSearch.getMaxRows();
				//#CM38605
				// add row
				lst_ItemSearch.addRow();

				//#CM38606
				// move to last record
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, irparam[i].getItemCode());
				lst_ItemSearch.setValue(3, irparam[i].getItemName());
			}
		}
		else
		{
			//#CM38607
			// set value to Pager
			//#CM38608
			// max. number
			pgr_U.setMax(0);
			//#CM38609
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM38610
			// start position
			pgr_U.setIndex(0);
			//#CM38611
			// max. number
			pgr_D.setMax(0);
			//#CM38612
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM38613
			// start position
			pgr_D.setIndex(0);

			//#CM38614
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM38615
			// hide the header
			lst_ItemSearch.setVisible(false);
			//#CM38616
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM38617
	/**
	 * method to change page <BR>
	 * Retrieve the work table.<BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setWorkList(SessionAsItemWorkInfoRet listbox, String actionName)
		throws Exception
	{
		//#CM38618
		// set page info
		listbox.setActionName(actionName);

		//#CM38619
		// fetch search result
		AsScheduleParameter[] irparam = (AsScheduleParameter[]) listbox.getEntities();
		int len = 0;
		if (irparam != null)
			len = irparam.length;
		if (len > 0)
		{
			//#CM38620
			// set value to Pager
			//#CM38621
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM38622
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM38623
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM38624
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM38625
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM38626
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM38627
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM38628
			// delete all rows
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM38629
				// fetch last row
				int count = lst_ItemSearch.getMaxRows();
				//#CM38630
				// add row
				lst_ItemSearch.addRow();

				//#CM38631
				// move to last record
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, irparam[i].getItemCode());
				lst_ItemSearch.setValue(3, irparam[i].getItemName());
			}
		}
		else
		{
			//#CM38632
			// set value to Pager
			//#CM38633
			// max. number
			pgr_U.setMax(0);
			//#CM38634
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM38635
			// start position
			pgr_U.setIndex(0);
			//#CM38636
			// max. number
			pgr_D.setMax(0);
			//#CM38637
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM38638
			// start position
			pgr_D.setIndex(0);

			//#CM38639
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM38640
			// hide the header
			lst_ItemSearch.setVisible(false);
			//#CM38641
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM38642
	// Event handler methods -----------------------------------------
	//#CM38643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38645
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

	//#CM38646
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

	//#CM38647
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

	//#CM38648
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

	//#CM38649
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

	//#CM38650
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38651
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM38652
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM38653
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM38654
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM38655
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38656
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM38657
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	Pass Item code and Item description to the parents screen, and close the list box. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_ItemSearch_Click(ActionEvent e) throws Exception
	{
		//#CM38658
		// The range flag is acquired. 
		String flug = viewState.getString(RANGEITEM_KEY);

		//#CM38659
		// set the current row
		lst_ItemSearch.setCurrentRow(lst_ItemSearch.getActiveRow());
		lst_ItemSearch.getValue(1);

		//#CM38660
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM38661
		// item code
		param.setParameter(ITEMCODE_KEY, lst_ItemSearch.getValue(2));
		//#CM38662
		// item name
		param.setParameter(ITEMNAME_KEY, lst_ItemSearch.getValue(3));
		
		//#CM38663
		// Start Item code ~ End Item code
		if (!StringUtil.isBlank(flug))
		{
			if (flug.equals(AsScheduleParameter.RANGE_START))
			{
				//#CM38664
				// start item code
				param.setParameter(STARTITEM_KEY, lst_ItemSearch.getValue(2));
			}
			else if (flug.equals(AsScheduleParameter.RANGE_END))
			{
				//#CM38665
				// end item code
				param.setParameter(ENDITEM_KEY, lst_ItemSearch.getValue(2));
			}
		}
		
		//#CM38666
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM38667
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
		//#CM38668
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38669
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38670
			//save the listbox in session
			SessionAsItemStockRet listbox = (SessionAsItemStockRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "next");
		}
		else if (flag.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38671
			//save the listbox in session
			SessionAsItemWorkInfoRet listbox = (SessionAsItemWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "next");
		}
	}

	//#CM38672
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
		//#CM38673
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38674
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38675
			//save the listbox in session
			SessionAsItemStockRet listbox = (SessionAsItemStockRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "previous");
		}
		else if (flag.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38676
			//save the listbox in session
			SessionAsItemWorkInfoRet listbox = (SessionAsItemWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "previous");
		}
	}

	//#CM38677
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
		//#CM38678
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38679
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38680
			//save the listbox in session
			SessionAsItemStockRet listbox = (SessionAsItemStockRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "last");
		}
		else if (flag.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38681
			//save the listbox in session
			SessionAsItemWorkInfoRet listbox = (SessionAsItemWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "last");
		}
	}

	//#CM38682
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
		//#CM38683
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38684
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38685
			//save the listbox in session
			SessionAsItemStockRet listbox = (SessionAsItemStockRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "first");
		}
		else if (flag.equals(AsScheduleParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM38686
			//save the listbox in session
			SessionAsItemWorkInfoRet listbox = (SessionAsItemWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "first");
		}
	}

	//#CM38687
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38688
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
		//#CM38689
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM38690
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM38691
				// close the statement object
				finder.close();
			}
			//#CM38692
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM38693
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM38694
		// return to origin screen
		parentRedirect(null);
	}
}
//#CM38695
//end of class
