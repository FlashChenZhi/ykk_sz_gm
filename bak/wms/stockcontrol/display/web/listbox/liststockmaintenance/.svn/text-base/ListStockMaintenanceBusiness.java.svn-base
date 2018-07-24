// $Id: ListStockMaintenanceBusiness.java,v 1.2 2006/10/04 05:12:00 suresh Exp $

//#CM5982
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockmaintenance;
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
import jp
	.co
	.daifuku
	.wms
	.stockcontrol
	.display
	.web
	.listbox
	.liststocklocation
	.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionListStockMaintenanceRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM5983
/**
 * Designer : K.Mukai <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * This is stock inquiry list search listbox class.<BR>
 * Search for the data by making Consignor code entered from parent screen, Item code, Case/Piece division and Location as a key and sisplay the result on the screen<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor code<BR>
 * </DIR>
 * <BR>
 * 2.The button on the line selected(<CODE>lst_ConsignorSearch_Click(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	on the line selected <BR>
 * 	<DIR>
 * 		Consignor code <BR>
 * 		Consignor name <BR>
 * 		Item code <BR>
 * 		Item name <BR>
 * 		Case/PieceDivision <BR>
 * 		Location <BR>
 *		Packing qty per case <BR>
 *		packed qty per bundle <BR>
 *		Case ITF <BR>
 *		Bundle ITF <BR>
 *		Expiry Date <BR>
 *		Storage Date <BR>
 *		Storage Time <BR>
 * 	</DIR>
 * <BR>
 * Pass them to parent screen and close list box.<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:00 $
 * @author  $Author: suresh $
 */
public class ListStockMaintenanceBusiness extends ListStockMaintenance implements WMSConstants
{
	//#CM5984
	// Class fields --------------------------------------------------
	//#CM5985
	/** 
	 * Use this key to pass the Packed qty. per case.
	 */
	public static final String ENTERINGQTY_KEY = "ENTERINGQTY_KEY";
	
	//#CM5986
	/** 
	 * Use this key to pass the Packed qty. per bundle.
	 */
	public static final String BUNDLE_ENTERINGQTY_KEY = "BUNDLE_ENTERINGQTY_KEY";
	
	//#CM5987
	/** 
	 * Use this key to pass the Case ITF.
	 */
	public static final String CASEITF_KEY = "CASEITF_KEY";
	
	//#CM5988
	/** 
	 * Use this key to pass the bundle ITF.
	 */
	public static final String BUNDLEITF_KEY = "BUNDLEITF_KEY";
	
	//#CM5989
	/** 
	 * Use this key to pass the expiry date.
	 */
	public static final String USEBYDATE_KEY = "USEBYDATE_KEY";
	
	//#CM5990
	/** 
	 * Use this key to pass the storage date.
	 */
	public static final String INSTOCKDAY_KEY = "INSTOCKDAY_KEY";
	
	//#CM5991
	/** 
	 * Use this key to pass the storage time.
	 */
	public static final String INSTOCKTIME_KEY = "INSTOCKTIME_KEY";
	
	//#CM5992
	// Class variables -----------------------------------------------

	//#CM5993
	// Class method --------------------------------------------------

	//#CM5994
	// Constructors --------------------------------------------------

	//#CM5995
	// Public methods ------------------------------------------------

	//#CM5996
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Item code <BR>
	 *		Division <BR>
	 *		Location <BR>
	 *		Packing qty per case <BR>
	 *		Case ITF <BR>
	 *		Storage Date <BR>
	 *		Expiry Date <BR>
	 *		Item name <BR>
	 *		packed qty per bundle <BR>
	 *		Bundle ITF <BR>
	 *		Storage Time <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM5997
		// Set the screen name
		//#CM5998
		// stock inquiry list
		lbl_ListName.setText(DisplayText.getText("TLE-W0014"));

		//#CM5999
		// Obtain parameter
		//#CM6000
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM6001
		// Item code
		String itemcode = request.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM6002
		// Case/PieceDivision
		String casepieceflag = request.getParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY);
		//#CM6003
		// Location
		String location = request.getParameter(ListStockLocationBusiness.LOCATION_KEY);
		//#CM6004
		// Area type flag
		String areatypeflag = request.getParameter(ListStockConsignorBusiness.AREA_TYPE_KEY);

		//#CM6005
		// Check the Consignor code for mandatory input and forbidden character.
		if (!WmsCheckker
			.consignorCheck(consignorcode, lst_ListStockMaintenance, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM6006
		// Check the Item code for forbidden character.
		if (!WmsCheckker.charCheck(itemcode, lst_ListStockMaintenance, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM6007
		// Check for forbidden character in the Location
		if (!WmsCheckker.charCheck(location, lst_ListStockMaintenance, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM6008
		// Set the Consignor code for the search conditions.
		lbl_JavaSetCnsgnrCd.setText(consignorcode);

		//#CM6009
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM6010
			// Close the connection
			sRet.closeConnection();
			//#CM6011
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM6012
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM6013
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		//#CM6014
		// Consignor code
		param.setConsignorCode(consignorcode);
		//#CM6015
		// Item code
		param.setItemCode(itemcode);
		//#CM6016
		// Case/PieceDivision
		param.setCasePieceFlag(casepieceflag);
		//#CM6017
		// Location
		param.setLocationNo(location);
		//#CM6018
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);

		//#CM6019
		// Generate SessionListStockMaintenanceRet instance.
		SessionListStockMaintenanceRet listbox = new SessionListStockMaintenanceRet(conn, param);
		//#CM6020
		// Store listbox to the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM6021
	// Package methods -----------------------------------------------

	//#CM6022
	// Protected methods ---------------------------------------------

	//#CM6023
	// Private methods -----------------------------------------------
	//#CM6024
	/**
	 * Method to change the page <BR>
	 * Search Stock info table <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionListStockMaintenanceRet listbox, String actionName)
		throws Exception
	{
		//#CM6025
		// Obtain the locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM6026
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM6027
		// Obtain search result
		StockControlParameter[] scparam = listbox.getEntities();

		int len = 0;
		if (scparam != null)
			len = scparam.length;
		if (len > 0)
		{
			//#CM6028
			// Set the Consignor name for the search conditions.
			lbl_JavaSetCnsgnrNm.setText(scparam[0].getConsignorName());

			//#CM6029
			// Set the value for the Pager.
			//#CM6030
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM6031
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM6032
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM6033
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM6034
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM6035
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM6036
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM6037
			// Delete all lines.
			lst_ListStockMaintenance.clearRow();

			//#CM6038
			// Use it in ToolTip
			String title_StrageDate = DisplayText.getText("LBL-W0237");
			String title_StrageTime = DisplayText.getText("LBL-W0368");
			String title_UseByDate = DisplayText.getText("LBL-W0270");

			for (int i = 0; i < len; i++)
			{
				//#CM6039
				// Obtain the tailing line.
				int count = lst_ListStockMaintenance.getMaxRows();
				//#CM6040
				// Add line
				lst_ListStockMaintenance.addRow();

				//#CM6041
				// Move to the end line.
				lst_ListStockMaintenance.setCurrentRow(count);
				lst_ListStockMaintenance.setValue(0, scparam[i].getCasePieceFlag());
				lst_ListStockMaintenance.setValue(
					1,
					Integer.toString(count + listbox.getCurrent()));
				lst_ListStockMaintenance.setValue(2, scparam[i].getItemCode());
				lst_ListStockMaintenance.setValue(3, scparam[i].getCasePieceFlagName());
				lst_ListStockMaintenance.setValue(4, scparam[i].getLocationNo());
				lst_ListStockMaintenance.setValue(
					5,
					WmsFormatter.getNumFormat(scparam[i].getEnteringQty()));
				lst_ListStockMaintenance.setValue(6, scparam[i].getITF());
				lst_ListStockMaintenance.setValue(
					7,
					WmsFormatter.toDispDate(
						WmsFormatter.toParamDate(scparam[i].getStorageDate()),
						locale));
				lst_ListStockMaintenance.setValue(8, scparam[i].getUseByDate());
				lst_ListStockMaintenance.setValue(9, scparam[i].getItemName());
				lst_ListStockMaintenance.setValue(
					10,
					WmsFormatter.getNumFormat(scparam[i].getBundleEnteringQty()));
				lst_ListStockMaintenance.setValue(11, scparam[i].getBundleITF());
				lst_ListStockMaintenance.setValue(
					12,
					WmsFormatter.getTimeFormat(scparam[i].getStorageDate(), ""));

				//#CM6042
				// Compile the data for ToolTip
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(
					title_StrageDate,
					WmsFormatter.getDateFormat(scparam[i].getStorageDate(), ""));
				toolTip.add(
					title_StrageTime,
					WmsFormatter.getTimeFormat(scparam[i].getStorageDate(), ""));
				toolTip.add(title_UseByDate, scparam[i].getUseByDate());

				//#CM6043
				// Set ToolTip	
				lst_ListStockMaintenance.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM6044
			// Set the value for the Pager.
			//#CM6045
			// Maximum Count
			pgr_U.setMax(0);
			//#CM6046
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM6047
			// Start Position
			pgr_U.setIndex(0);
			//#CM6048
			// Maximum Count
			pgr_D.setMax(0);
			//#CM6049
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM6050
			// Start Position
			pgr_D.setIndex(0);

			//#CM6051
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM6052
			// Hide the header.
			lst_ListStockMaintenance.setVisible(false);
			//#CM6053
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM6054
	// Event handler methods -----------------------------------------
	//#CM6055
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6056
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6057
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6058
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6059
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6060
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6061
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

	//#CM6062
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

	//#CM6063
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

	//#CM6064
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

	//#CM6065
	/** 
	 * Execute processing when "<<" button is pressed down. <BR>
	 * <BR>
	 * Display the top page.<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM6066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMaintenance_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM6068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMaintenance_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM6069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMaintenance_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM6070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMaintenance_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM6071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMaintenance_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockMaintenance_Change(ActionEvent e) throws Exception
	{
	}

	//#CM6073
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *	To parent screen <BR>
	 *	<DIR>
	 *		Consignor code <BR>
	 *		Consignor name <BR>
	 *		Item code <BR>
	 *		Item name <BR>
	 *		Case/PieceDivision <BR>
	 *		Location <BR>
	 *		Packing qty per case <BR>
	 *		packed qty per bundle <BR>
	 *		Case ITF <BR>
	 *		Bundle ITF <BR>
	 *		Expiry Date <BR>
	 *		Storage Date <BR>
	 *		Storage Time <BR>
	 *	</DIR>
	 *	Pass it and close the list box. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_ListStockMaintenance_Click(ActionEvent e) throws Exception
	{
		//#CM6074
		// Set the current line.
		lst_ListStockMaintenance.setCurrentRow(lst_ListStockMaintenance.getActiveRow());
		lst_ListStockMaintenance.getValue(1);

		//#CM6075
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		//#CM6076
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetCnsgnrCd.getText());
		//#CM6077
		// Consignor name
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORNAME_KEY,
			lbl_JavaSetCnsgnrNm.getText());
		//#CM6078
		// Item code
		param.setParameter(
			ListStockItemBusiness.ITEMCODE_KEY,
			lst_ListStockMaintenance.getValue(2));
		//#CM6079
		// Item name
		param.setParameter(
			ListStockItemBusiness.ITEMNAME_KEY,
			lst_ListStockMaintenance.getValue(9));
		//#CM6080
		// Case/PieceDivision
		param.setParameter(
			ListStockLocationBusiness.CASEPIECEFLAG_KEY,
			lst_ListStockMaintenance.getValue(0));
		//#CM6081
		// Location
		param.setParameter(
			ListStockLocationBusiness.LOCATION_KEY,
			lst_ListStockMaintenance.getValue(4));
		//#CM6082
		// Packing qtt of Case
		param.setParameter(ENTERINGQTY_KEY, lst_ListStockMaintenance.getValue(5));
		//#CM6083
		// Packing qtt of Bundle
		param.setParameter(BUNDLE_ENTERINGQTY_KEY, lst_ListStockMaintenance.getValue(10));
		//#CM6084
		// Case ITF
		param.setParameter(CASEITF_KEY, lst_ListStockMaintenance.getValue(6));
		//#CM6085
		// Bundle ITF
		param.setParameter(BUNDLEITF_KEY, lst_ListStockMaintenance.getValue(11));
		//#CM6086
		// Expiry Date
		param.setParameter(USEBYDATE_KEY, lst_ListStockMaintenance.getValue(8));
		//#CM6087
		// Storage Date
		param.setParameter(INSTOCKDAY_KEY, lst_ListStockMaintenance.getValue(7));
		//#CM6088
		// Storage Time
		param.setParameter(INSTOCKTIME_KEY, lst_ListStockMaintenance.getValue(12));
		//#CM6089
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM6090
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
		//#CM6091
		// Store listbox to the Session
		SessionListStockMaintenanceRet listbox =
			(SessionListStockMaintenanceRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM6092
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
		//#CM6093
		// Store listbox to the Session
		SessionListStockMaintenanceRet listbox =
			(SessionListStockMaintenanceRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM6094
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
		//#CM6095
		// Store listbox to the Session
		SessionListStockMaintenanceRet listbox =
			(SessionListStockMaintenanceRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM6096
	/** 
	 * Execute processing when "<<" button is pressed down.<BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM6097
		// Store listbox to the Session
		SessionListStockMaintenanceRet listbox =
			(SessionListStockMaintenanceRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM6098
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6099
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
		//#CM6100
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM6101
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM6102
				// Close the statement
				finder.close();
			}
			//#CM6103
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM6104
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM6105
		// Return to the parent screen.
		parentRedirect(null);
	}
}
//#CM6106
//end of class
