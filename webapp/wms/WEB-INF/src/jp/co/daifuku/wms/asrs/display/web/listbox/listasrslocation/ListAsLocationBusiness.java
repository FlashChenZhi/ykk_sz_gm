// $Id: ListAsLocationBusiness.java,v 1.2 2006/10/26 05:29:00 suresh Exp $

//#CM38700
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsLocationStockRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM38701
/**
 * <FONT COLOR="BLUE">
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * The location retrieval list box class.<BR>
 * Make Warehouse No input from the parents screen, Consignor code, Item code, case piece flag, Start LocationNo, and end locationNo as a key, retrieve, and display it on the screen. <BR>
 * Process it in this class as follows. <BR>
 * 
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Make Warehouse No input from the parents screen, Consignor code, Item code, case piece flag, Start LocationNo, and end locationNo as a key, retrieve, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_ListStockLocation_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass Location No, BankNo, bay No, and level No of the selection line to the parents screen, and close the list box.<BR>
 * <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/06</TD><TD>K.Toda</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:29:00 $
 * @author  $Author: suresh $
 */
public class ListAsLocationBusiness extends ListAsLocation implements WMSConstants
{
	//#CM38702
	// Class fields --------------------------------------------------
	//#CM38703
	/** 
	 * The key used to hand over area No information. 
	 */
	public static final String AREANO_KEY = "AREANO_KEY";

	//#CM38704
	/** 
	 * The key used to hand over beginning location info. 
	 */
	public static final String STARTLOCATION_KEY = "STARTLOCATION_KEY";

	//#CM38705
	/** 
	 * The key used to hand over beginning Bank information. 
	 */
	public static final String STARTBANK_KEY = "STARTBANK_KEY";

	//#CM38706
	/** 
	 * The key used to hand over beginning bay information. 
	 */
	public static final String STARTBAY_KEY = "STARTBAY_KEY";

	//#CM38707
	/** 
	 * The key used to hand over beginning level information. 
	 */
	public static final String STARTLEVEL_KEY = "STARTLEVEL_KEY";

	//#CM38708
	/** 
	 * The key used to hand over end location information. 
	 */
	public static final String ENDLOCATION_KEY = "ENDLOCATION_KEY";

	//#CM38709
	/** 
	 * The key used to hand over end Bank information. 
	 */
	public static final String ENDBANK_KEY = "ENDBANK_KEY";

	//#CM38710
	/** 
	 * The key used to hand over end bay information. 
	 */
	public static final String ENDBAY_KEY = "ENDBAY_KEY";

	//#CM38711
	/** 
	 * The key used to hand over end level information. 
	 */
	public static final String ENDLEVEL_KEY = "ENDLEVEL_KEY";

	//#CM38712
	/** 
	 * The key used to hand over Case/Piece flag. 
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM38713
	/** 
	 * The key used to hand over the shelf range flag. 
	 */
	public static final String RANGELOCATION_KEY = "RANGELOCATION_KEY";

	//#CM38714
	/** 
	 * key to transfer search flag
	 */
	public static final String SEARCHITEM_KEY = "SEARCHITEM_KEY";


	//#CM38715
	// Class variables -----------------------------------------------

	//#CM38716
	// Class method --------------------------------------------------

	//#CM38717
	// Constructors --------------------------------------------------

	//#CM38718
	// Public methods ------------------------------------------------

	//#CM38719
	/**
	 * screen initialization
	 * <DIR>
	 *	item <BR>
	 *	<DIR>
	 *		select <BR>
	 *		location <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM38720
		// set screen name
		//#CM38721
		// search location
		lbl_ListName.setText(DisplayText.getText("TLE-W0064"));

		//#CM38722
		// fetch parameter
		//#CM38723
		// area no.
		String areano = request.getParameter(AREANO_KEY);
		//#CM38724
		// consignor code
		String consignorcode = request.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM38725
		// item code
		String itemcode = request.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM38726
		// case piece flag
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		//#CM38727
		// start location
		String startlocation = request.getParameter(STARTLOCATION_KEY);
		//#CM38728
		// end location
		String endlocation = request.getParameter(ENDLOCATION_KEY);
		//#CM38729
		// range flag
		String rangelocation = request.getParameter(RANGELOCATION_KEY);
		//#CM38730
		// search flag (stock)
		String searchitem = request.getParameter(SEARCHITEM_KEY);

		this.getViewState().setString(SEARCHITEM_KEY, searchitem);
		this.getViewState().setString(RANGELOCATION_KEY, rangelocation);

		//#CM38731
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM38732
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM38733
			// close connection
			sRet.closeConnection();
			//#CM38734
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM38735
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM38736
		// area no.
		param.setAreaNo(areano);
		//#CM38737
		// consignor code
		param.setConsignorCode(consignorcode);
		//#CM38738
		// item code
		param.setItemCode(itemcode);
		//#CM38739
		// case piece flag
		param.setCasePieceFlag(casepieceflag);
		//#CM38740
		// start location
		param.setFromLocationNo(startlocation);
		//#CM38741
		// end location
		if (rangelocation.equals(AsScheduleParameter.RANGE_END))
		{
			param.setToLocationNo(endlocation);
		}

		//#CM38742
		// Whether the stock of retrieved location is judged. 
		if (searchitem.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38743
			// create SessionLocationRet instance
			SessionAsLocationStockRet listbox = new SessionAsLocationStockRet(conn, param);
			//#CM38744
			//save the listbox in session
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
		}
	}

	//#CM38745
	// Package methods -----------------------------------------------

	//#CM38746
	// Protected methods ---------------------------------------------

	//#CM38747
	// Private methods -----------------------------------------------
	//#CM38748
	/**
	 * method to change page <BR>
	 * Retrieve the table.<BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsLocationStockRet listbox, String actionName) throws Exception
	{
		//#CM38749
		// set page info
		listbox.setActionName(actionName);

		//#CM38750
		// fetch search result
		AsScheduleParameter[] stcparam = listbox.getEntities();
		int len = 0;
		if (stcparam != null)
			len = stcparam.length;
		if (len > 0)
		{
			//#CM38751
			// set value to Pager
			//#CM38752
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM38753
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM38754
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM38755
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM38756
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM38757
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM38758
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM38759
			// delete all rows
			lst_ListLocationSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM38760
				// fetch last row
				int count = lst_ListLocationSearch.getMaxRows();
				//#CM38761
				// add row
				lst_ListLocationSearch.addRow();

				//#CM38762
				// move to last record
				lst_ListLocationSearch.setCurrentRow(count);
				lst_ListLocationSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListLocationSearch.setValue(2, DisplayText.formatLocation(stcparam[i].getLocationNo()));
				//#CM38763
				// Location no in the HIDDEN item (Before editing) is set.
				lst_ListLocationSearch.setValue(0, stcparam[i].getLocationNo());
			}
		}
		else
		{
			//#CM38764
			// set value to Pager
			//#CM38765
			// max. number
			pgr_U.setMax(0);
			//#CM38766
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM38767
			// start position
			pgr_U.setIndex(0);
			//#CM38768
			// max. number
			pgr_D.setMax(0);
			//#CM38769
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM38770
			// start position
			pgr_D.setIndex(0);

			//#CM38771
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM38772
			// hide the header
			lst_ListLocationSearch.setVisible(false);
			//#CM38773
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM38774
	// Event handler methods -----------------------------------------
	//#CM38775
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38777
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

	//#CM38778
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

	//#CM38779
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

	//#CM38780
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

	//#CM38781
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

	//#CM38782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38783
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM38784
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM38785
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM38786
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM38787
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38788
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM38789
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	Pass location no (StationNo form) to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_ListLocationSearch_Click(ActionEvent e) throws Exception
	{
		//#CM38790
		// fetch range flag
		String flug = viewState.getString(RANGELOCATION_KEY);

		//#CM38791
		// set the current row
		lst_ListLocationSearch.setCurrentRow(lst_ListLocationSearch.getActiveRow());
		lst_ListLocationSearch.getValue(1);

		//#CM38792
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		if (flug.equals(AsScheduleParameter.RANGE_START))
		{
			//#CM38793
			// start location
			param.setParameter(STARTLOCATION_KEY, lst_ListLocationSearch.getValue(0));
		}
		else if (flug.equals(AsScheduleParameter.RANGE_END))
		{
			//#CM38794
			// end location
			param.setParameter(ENDLOCATION_KEY, lst_ListLocationSearch.getValue(0));
		}

		//#CM38795
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM38796
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
		//#CM38797
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38798
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38799
			//save the listbox in session
			SessionAsLocationStockRet listbox = (SessionAsLocationStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}
	}

	//#CM38800
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
		//#CM38801
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38802
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38803
			//save the listbox in session
			SessionAsLocationStockRet listbox = (SessionAsLocationStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
	}

	//#CM38804
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
		//#CM38805
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38806
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38807
			//save the listbox in session
			SessionAsLocationStockRet listbox = (SessionAsLocationStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
	}

	//#CM38808
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
		//#CM38809
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38810
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38811
			//save the listbox in session
			SessionAsLocationStockRet listbox = (SessionAsLocationStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}	
	}

	//#CM38812
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38813
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
		//#CM38814
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM38815
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM38816
				// close the statement object
				finder.close();
			}
			//#CM38817
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM38818
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM38819
		// return to origin screen
		parentRedirect(null);
	}

}
//#CM38820
//end of class
