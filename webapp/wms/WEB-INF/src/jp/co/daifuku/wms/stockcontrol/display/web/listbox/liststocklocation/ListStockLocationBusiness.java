// $Id: ListStockLocationBusiness.java,v 1.2 2006/10/04 05:11:42 suresh Exp $

//#CM5719
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp
	.co
	.daifuku
	.wms
	.stockcontrol
	.display
	.web
	.listbox
	.liststockconsignor
	.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionLocationRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM5720
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is location search listbox class.<BR>
 * Search for the data using Consignor code<BR>
 * This class executes the following processes. <BR>
 * 
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor code<BR>
 * <BR>
 * </DIR>
 * 2.The button on the line selected(<CODE>lst_ListStockLocation_Click(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the Location No. of the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/04</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:11:42 $
 * @author  $Author: suresh $
 */
public class ListStockLocationBusiness extends ListStockLocation implements WMSConstants
{
	//#CM5721
	// Class fields --------------------------------------------------
	//#CM5722
	/** 
	 * Use this key to pass the location info.
	 */
	public static final String LOCATION_KEY = "LOCATION_KEY";

	//#CM5723
	/** 
	 * Use this key to pass the Start location info.
	 */
	public static final String STARTLOCATION_KEY = "STARTLOCATION_KEY";

	//#CM5724
	/** 
	 * Use this key to pass the End location info.
	 */
	public static final String ENDLOCATION_KEY = "ENDLOCATION_KEY";

	//#CM5725
	/** 
	 * Use this key to pass the Case/Piece division.
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM5726
	/** 
	 * Use this key to pass the location range flag.
	 */
	public static final String RANGELOCATION_KEY = "RANGELOCATION_KEY";

	//#CM5727
	// Class variables -----------------------------------------------

	//#CM5728
	// Class method --------------------------------------------------

	//#CM5729
	// Constructors --------------------------------------------------

	//#CM5730
	// Public methods ------------------------------------------------

	//#CM5731
	/**
	 * Initialize the screen
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Location <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM5732
		// Set the screen name
		//#CM5733
		// Search Location
		lbl_ListName.setText(DisplayText.getText("TLE-W0064"));

		//#CM5734
		// Obtain parameter
		//#CM5735
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM5736
		// Item code
		String itemcode = request.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM5737
		// Start item code
		String startitemcode = request.getParameter(ListStockItemBusiness.STARTITEMCODE_KEY);
		//#CM5738
		// End item code
		String enditemcode = request.getParameter(ListStockItemBusiness.ENDITEMCODE_KEY);
		//#CM5739
		// Case/PieceDivision
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		//#CM5740
		// Location
		String location = request.getParameter(LOCATION_KEY);
		//#CM5741
		// Start location
		String startlocation = request.getParameter(STARTLOCATION_KEY);
		//#CM5742
		// End location
		String endlocation = request.getParameter(ENDLOCATION_KEY);
		//#CM5743
		// Area flag
		String rangelocation = request.getParameter(RANGELOCATION_KEY);
		//#CM5744
		// Area type flag
		String areatypeflag = request.getParameter(ListStockConsignorBusiness.AREA_TYPE_KEY);

		viewState.setString(RANGELOCATION_KEY, rangelocation);

		//#CM5745
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM5746
			// Close the connection
			sRet.closeConnection();
			//#CM5747
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM5748
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM5749
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		//#CM5750
		// Consignor code
		param.setConsignorCode(consignorcode);
		//#CM5751
		// Item code
		param.setItemCode(itemcode);
		//#CM5752
		// Start item code
		param.setFromItemCode(startitemcode);
		//#CM5753
		// End item code
		param.setToItemCode(enditemcode);
		//#CM5754
		// Case/PieceDivision
		param.setCasePieceFlag(casepieceflag);
		//#CM5755
		// Location
		param.setLocationNo(location);
		//#CM5756
		// Start location
		param.setFromLocationNo(startlocation);
		//#CM5757
		// End location
		param.setToLocationNo(endlocation);
		//#CM5758
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);
		
		//#CM5759
		// Generate SessionLocationRet instance.
		SessionLocationRet listbox = new SessionLocationRet(conn, param);
		//#CM5760
		// Store listbox to the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");

	}

	//#CM5761
	// Package methods -----------------------------------------------

	//#CM5762
	// Protected methods ---------------------------------------------

	//#CM5763
	// Private methods -----------------------------------------------
	//#CM5764
	/**
	 * Method to change the page <BR>
	 * Search the table <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionLocationRet listbox, String actionName) throws Exception
	{
		//#CM5765
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM5766
		// Obtain search result
		StockControlParameter[] stcparam = listbox.getEntities();
		int len = 0;
		if (stcparam != null)
			len = stcparam.length;
		if (len > 0)
		{
			//#CM5767
			// Set the value for the Pager.
			//#CM5768
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM5769
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM5770
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM5771
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM5772
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM5773
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM5774
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM5775
			// Delete all lines.
			lst_ListStockLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM5776
				// Obtain the tailing line.
				int count = lst_ListStockLocation.getMaxRows();
				//#CM5777
				// Add line
				lst_ListStockLocation.addRow();

				//#CM5778
				// Move to the end line.
				lst_ListStockLocation.setCurrentRow(count);
				lst_ListStockLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStockLocation.setValue(2, stcparam[i].getLocationNo());
			}
		}
		else
		{
			//#CM5779
			// Set the value for the Pager.
			//#CM5780
			// Maximum Count
			pgr_U.setMax(0);
			//#CM5781
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM5782
			// Start Position
			pgr_U.setIndex(0);
			//#CM5783
			// Maximum Count
			pgr_D.setMax(0);
			//#CM5784
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM5785
			// Start Position
			pgr_D.setIndex(0);

			//#CM5786
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM5787
			// Hide the header.
			lst_ListStockLocation.setVisible(false);
			//#CM5788
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM5789
	// Event handler methods -----------------------------------------
	//#CM5790
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5791
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5792
	/** 
	 * Clicking Close button executes its process. <BR>
	 *  <BR>
	 * Close listbox, and<BR>
	 *  <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM5793
	/** 
	 * Execute processing when ">" button is pressed down. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM5794
	/** 
	 * Clicking on "< " button executes its process. <BR>
	 * <BR>
	 * Show prior one page<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM5795
	/** 
	 * Clicking ">> " button executes its process. <BR>
	 * <BR>
	 * Display the final page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM5796
	/** 
	 * Execute processing when "<<" button is pressed down. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM5797
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5798
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM5799
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM5800
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM5801
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM5802
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5803
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockLocation_Change(ActionEvent e) throws Exception
	{
	}

	//#CM5804
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *	To parent screen<BR>
	 *	<DIR>
	 *		Location <BR>
	 *	</DIR>
	 *	Pass it and close the list box. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_ListStockLocation_Click(ActionEvent e) throws Exception
	{
		//#CM5805
		// Obtain the range flag.
		String flug = viewState.getString(RANGELOCATION_KEY);

		//#CM5806
		// Set the current line.
		lst_ListStockLocation.setCurrentRow(lst_ListStockLocation.getActiveRow());
		lst_ListStockLocation.getValue(1);

		//#CM5807
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM5808
			// Location
			param.setParameter(LOCATION_KEY, lst_ListStockLocation.getValue(2));
		}
		else if (flug.equals(StockControlParameter.RANGE_START))
		{
			//#CM5809
			// Start location
			param.setParameter(STARTLOCATION_KEY, lst_ListStockLocation.getValue(2));
		}
		else if (flug.equals(StockControlParameter.RANGE_END))
		{
			//#CM5810
			// End location
			param.setParameter(ENDLOCATION_KEY, lst_ListStockLocation.getValue(2));
		}

		//#CM5811
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM5812
	/** 
	 * Execute processing when ">" button is pressed down. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM5813
		// Store listbox to the Session
		SessionLocationRet listbox = (SessionLocationRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM5814
	/** 
	 * Clicking on "< " button executes its process. <BR>
	 * <BR>
	 * Show prior one page<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM5815
		// Store listbox to the Session
		SessionLocationRet listbox = (SessionLocationRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM5816
	/** 
	 * Clicking ">> " button executes its process. <BR>
	 * <BR>
	 * Display the final page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM5817
		// Store listbox to the Session
		SessionLocationRet listbox = (SessionLocationRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM5818
	/** 
	 * Execute processing when "<<" button is pressed down. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM5819
		// Store listbox to the Session
		SessionLocationRet listbox = (SessionLocationRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM5820
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5821
	/** 
	 * Clicking Close button executes its process. <BR>
	 *  <BR>
	 * Close listbox, and<BR>
	 *  <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM5822
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM5823
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM5824
				// Close the statement
				finder.close();
			}
			//#CM5825
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM5826
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM5827
		// Return to the parent screen.
		parentRedirect(null);
	}
}
//#CM5828
//end of class
