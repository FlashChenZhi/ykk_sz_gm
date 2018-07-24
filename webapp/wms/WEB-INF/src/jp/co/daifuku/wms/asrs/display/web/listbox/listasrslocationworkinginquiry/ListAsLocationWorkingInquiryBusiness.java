// $Id: ListAsLocationWorkingInquiryBusiness.java,v 1.2 2006/10/26 05:31:54 suresh Exp $

//#CM39059
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocationworkinginquiry;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsLocationWorkingInquiryRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;

//#CM39060
/**
 * Designer :Y.Osawa<BR>
 * Maker : Y.Osawa <BR>
 * <BR>
 * The stock inquiry list box class according to the ASRS location. <BR>
 * Retrieve it based on Warehouse input from the parents screen, Consignor code, Start Location, end location, Item code, and Case/Piece flag.  <BR>
 * The following process are called in this class <BR>
 * <BR>
 * 1.initial display ( <CODE>page_Load(ActionEvent e)</CODE>  Method ) <BR>
 * <BR>
 * <DIR>Make Warehouse input from the parents screen, Consignor code, Start Location, end location, Item code, and Case/Piece flag as a key, retrieve, and display it on the screen. <BR>
 * <BR>
 * [parameter] *mandatory input <BR>
 * <BR>
 * warehouse * <BR>
 * consignor code *<BR>
 * start location <BR>
 * end location <BR>
 * item code <BR>
 * case/piece flag *<BR>
 * <BR>
 * [data for output use] <BR>
 * <BR>
 * warehouse <BR>
 * consignor code <BR>
 * consignor name <BR>
 * start location <BR>
 * end location <BR>
 * item code <BR>
 * location <BR>
 * item code <BR>
 * item name <BR>
 * packed qty per case <BR>
 * packed qty per piece <BR>
 * stock case qty <BR>
 * stock piece qty <BR>
 * available case qty <BR>
 * available piece qty <BR>
 * classification <BR>
 * Case ITF <BR>
 * bundle itf <BR>
 * storage date <BR>
 * storage time <BR>
 * expiry date <BR>
 * </DIR> </DIR> <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2006/02/27</TD>
 * <TD>Y.Osawa</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:31:54 $
 * @author $Author: suresh $
 */
public class ListAsLocationWorkingInquiryBusiness extends ListAsLocationWorkingInquiry implements WMSConstants
{
	//#CM39061
	// Class fields --------------------------------------------------
	//#CM39062
	// KEY when maintaining it in session
	//#CM39063
	// Contents of list cell
	private static final String LISTBOX = "LISTBOX";

	//#CM39064
	// Class variables -----------------------------------------------

	//#CM39065
	// Class method --------------------------------------------------

	//#CM39066
	// Constructors --------------------------------------------------

	//#CM39067
	// Public methods ------------------------------------------------

	//#CM39068
	/**
	 * initial screen display<BR>
	 * <DIR>item <BR>
	 * [other than listcell] <BR>
	 * <DIR>
	 * warehouse <BR>
	 * consignor code <BR>
	 * consignor name <BR>
	 * start location <BR>
	 * end location <BR>
	 * </DIR>
	 * [list cell] <BR>
	 * <DIR>
	 * location <BR>
	 * item code<BR>
	 * packed qty per case<BR>
	 *stock case qty <BR>
	 * available case qty<BR>
	 * classification <BR>
	 * Case ITF <BR>
	 * storage date <BR>
	 * item name <BR>
	 * packed qty per bundle <BR>
	 * Stock Piece qty <BR>
	 * Allocatable piece qty <BR>
	 * Bundle ITF <BR>
	 * storage time <BR>
	 * expiry date <BR>
	 * </DIR>
	 * <BR>
	 * balloon help <BR>
	 * item name <BR>
	 * storage date <BR>
	 * storage time <BR>
	 * expiry date <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM39069
		// set screen name
		//#CM39070
		// search location
		lbl_ListName.setText(DisplayText.getText("TLE-W0944"));
		
		//#CM39071
		// Acquire the parameter from the stock inquiry screen according to the AS/RS location. 
		//#CM39072
		// warehouse (area no.)
		String _areano = request.getParameter(ListAsLocationBusiness.AREANO_KEY);
		//#CM39073
		// consignor code
		String _consignorcode = request.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM39074
		// start location
		String _startlocation = request.getParameter(ListAsLocationBusiness.STARTLOCATION_KEY);
		//#CM39075
		// end location
		String _endlocation = request.getParameter(ListAsLocationBusiness.ENDLOCATION_KEY);
		//#CM39076
		// item code
		String _itemcode = request.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM39077
		// case piece flag(Code)
		String _cpfcode = request.getParameter(ListAsLocationBusiness.CASEPIECEFLAG_KEY);

		//#CM39078
		// input check
		//#CM39079
		// consignor code
		if (!WmsCheckker.consignorCheck(_consignorcode, lst_AsLocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM39080
		// start location
		if (!WmsCheckker.charCheck(_startlocation, lst_AsLocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM39081
		// end location
		if (!WmsCheckker.charCheck(_endlocation, lst_AsLocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM39082
		// Checked before and after Start Location and end location. 
		if (!WmsCheckker.rangeLocationCheck(_startlocation, _endlocation, lst_AsLocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM39083
		// item code
		if (!WmsCheckker.charCheck(_itemcode, lst_AsLocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM39084
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM39085
		// set header item other than listcell
		//#CM39086
		// warehouse name
		ASFindUtil util = new ASFindUtil(conn);
		lbl_JavaSetWareHouse.setText(util.getWareHouseName(_areano));
		//#CM39087
		// consignor code
		lbl_JavaSetCnsgnrCd.setText(_consignorcode);
		//#CM39088
		// start location
		if (!StringUtil.isBlank(_startlocation))
		{
			lbl_JavaSetStartLocation.setText(DisplayText.formatDispLocation(_startlocation));
		}
		//#CM39089
		// end location
		if (!StringUtil.isBlank(_endlocation))
		{
			lbl_JavaSetEndLocation.setText(DisplayText.formatDispLocation(_endlocation));
		}

		//#CM39090
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute(LISTBOX);
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM39091
			// close connection
			sRet.closeConnection();
			//#CM39092
			// delete from session
			this.getSession().removeAttribute(LISTBOX);
		}

		//#CM39093
		// Display information on the screen is acquired. 
		//#CM39094
		// The retrieval condition parameter is set. 
		AsScheduleParameter paramCont = new AsScheduleParameter();
		//#CM39095
		// warehouse (area no.)
		paramCont.setAreaNo(_areano);
		//#CM39096
		// consignor code
		paramCont.setConsignorCode(_consignorcode);
		//#CM39097
		// start location
		paramCont.setFromLocationNo(_startlocation);
		//#CM39098
		// end location
		paramCont.setToLocationNo(_endlocation);
		//#CM39099
		// item code
		paramCont.setItemCode(_itemcode);
		//#CM39100
		// case piece flag
		paramCont.setCasePieceFlag(_cpfcode);

		//#CM39101
		// Acquisition of result
		//#CM39102
		// instance generation
		SessionAsLocationWorkingInquiryRet listbox = new SessionAsLocationWorkingInquiryRet(conn, paramCont);
		//#CM39103
		// Maintain listbox in Session. 
		this.getSession().setAttribute(LISTBOX, listbox);
		setList(listbox, "first");
	}

	//#CM39104
	// Package methods -----------------------------------------------

	//#CM39105
	// Protected methods ---------------------------------------------

	//#CM39106
	// Private methods -----------------------------------------------
	//#CM39107
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsLocationWorkingInquiryRet listbox, String actionName) throws Exception
	{
		//#CM39108
		// fetch locale
		Locale locale = this.getHttpRequest().getLocale();

		//#CM39109
		// set page info
		listbox.setActionName(actionName);

		//#CM39110
		// fetch search result
		AsScheduleParameter[] stocks = listbox.getEntities();

		//#CM39111
		// Acquisition of total qty
		int len = 0;
		if (stocks != null)
		{
			len = stocks.length;
		}

		if (len > 0)
		{
			//#CM39112
			// set value to Pager
			//#CM39113
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM39114
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM39115
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM39116
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM39117
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM39118
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM39119
			// hide message
			lbl_InMsg.setVisible(false);

			lbl_JavaSetCnsgnrName.setText(stocks[0].getConsignorName());

			//#CM39120
			// delete all rows
			lst_AsLocationWorkingInquiry.clearRow();
			
			//#CM39121
			// item name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM39122
			// storage date
			String label_storagedate = DisplayText.getText("LBL-W0237");
			//#CM39123
			// storage time
			String label_storagetime = DisplayText.getText("LBL-W0368");
			//#CM39124
			// expiry date
			String label_usebydate = DisplayText.getText("LBL-W0270");
			
			//#CM39125
			// The content of the list cell is made. 
			for (int i = 0; i < len; i++)
			{
				//#CM39126
				// The final line number is acquired. 
				int count = lst_AsLocationWorkingInquiry.getMaxRows();
				//#CM39127
				// One line addition to List cell (first row use)
				lst_AsLocationWorkingInquiry.addRow();
				//#CM39128
				// move to last record
				lst_AsLocationWorkingInquiry.setCurrentRow(count);

				//#CM39129
				// Set search result to the list cell. 
				//#CM39130
				// first row
				//#CM39131
				// location
				lst_AsLocationWorkingInquiry.setValue(1, DisplayText.formatDispLocation(stocks[i].getLocationNo()));
				//#CM39132
				// item code
				lst_AsLocationWorkingInquiry.setValue(2, stocks[i].getItemCode());
				//#CM39133
				// packed qty per case
				lst_AsLocationWorkingInquiry.setValue(3, WmsFormatter.getNumFormat(stocks[i].getEnteringQty()));
				//#CM39134
				// stock case qty
				lst_AsLocationWorkingInquiry.setValue(4, WmsFormatter.getNumFormat(stocks[i].getStockCaseQty()));
				//#CM39135
				// available case qty
				lst_AsLocationWorkingInquiry.setValue(5, WmsFormatter.getNumFormat(stocks[i].getAllocateCaseQty()));
				//#CM39136
				// classification
				lst_AsLocationWorkingInquiry.setValue(6, stocks[i].getCasePieceFlagNameDisp());
				//#CM39137
				// Case ITF
				lst_AsLocationWorkingInquiry.setValue(7, stocks[i].getITF());
				//#CM39138
				// storage date/time (storage date)
				lst_AsLocationWorkingInquiry.setValue(8, WmsFormatter.toDispDate(WmsFormatter.toParamDate(stocks[i].getInStockDate()), locale));
				//#CM39139
				// expiry date
				lst_AsLocationWorkingInquiry.setValue(9, stocks[i].getUseByDate());

				//#CM39140
				// second row
				//#CM39141
				// item name
				lst_AsLocationWorkingInquiry.setValue(10, stocks[i].getItemName());
				//#CM39142
				// packed qty per piece
				lst_AsLocationWorkingInquiry.setValue(11, WmsFormatter.getNumFormat(stocks[i].getBundleEnteringQty()));
				//#CM39143
				// stock piece qty
				lst_AsLocationWorkingInquiry.setValue(12, WmsFormatter.getNumFormat(stocks[i].getStockPieceQty()));
				//#CM39144
				// available piece qty
				lst_AsLocationWorkingInquiry.setValue(13, WmsFormatter.getNumFormat(stocks[i].getAllocatePieceQty()));
				//#CM39145
				// bundle itf
				lst_AsLocationWorkingInquiry.setValue(14, stocks[i].getBundleITF());
				//#CM39146
				// Storage Date and time (storage time)
				lst_AsLocationWorkingInquiry.setValue(15, WmsFormatter.getTimeFormat(stocks[i].getInStockDate(), ""));

				//#CM39147
				// set tool tip
				ToolTipHelper tTip = new ToolTipHelper();
				//#CM39148
				// item name
				tTip.add(label_itemname, stocks[i].getItemName());
				//#CM39149
				// storage date
				tTip.add(label_storagedate, WmsFormatter.toDispDate(WmsFormatter.toParamDate(stocks[i].getInStockDate()), locale));
				//#CM39150
				// storage time
				tTip.add(label_storagetime, WmsFormatter.getTimeFormat(stocks[i].getInStockDate(), ""));
				//#CM39151
				// expiry date
				tTip.add(label_usebydate, stocks[i].getUseByDate());
			
				lst_AsLocationWorkingInquiry.setToolTip(count, tTip.getText());
			}
		}
		else
		{
			//#CM39152
			// set value to Pager
			//#CM39153
			// max. number
			pgr_U.setMax(0);
			//#CM39154
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM39155
			// start position
			pgr_U.setIndex(0);
			//#CM39156
			// max. number
			pgr_D.setMax(0);
			//#CM39157
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM39158
			// start position
			pgr_D.setIndex(0);

			//#CM39159
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM39160
			// hide the header
			lst_AsLocationWorkingInquiry.setVisible(false);
			//#CM39161
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM39162
	// Event handler methods -----------------------------------------
	//#CM39163
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39164
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39165
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39166
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39167
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetCnsgnrName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39168
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Location_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39169
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetStartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39170
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39171
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetEndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39172
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39173
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39174
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39175
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39176
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

	//#CM39177
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

	//#CM39178
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

	//#CM39179
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

	//#CM39180
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

	//#CM39181
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_AsLocationWorkingInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39182
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_AsLocationWorkingInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39183
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_AsLocationWorkingInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM39184
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_AsLocationWorkingInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM39185
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_AsLocationWorkingInquiry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39186
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_AsLocationWorkingInquiry_Change(ActionEvent e) throws Exception
	{
	}

	//#CM39187
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_AsLocationWorkingInquiry_Click(ActionEvent e) throws Exception
	{
	}

	//#CM39188
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
		//#CM39189
		//save the listbox in session
		SessionAsLocationWorkingInquiryRet listbox = (SessionAsLocationWorkingInquiryRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "next");
	}

	//#CM39190
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
		//#CM39191
		//save the listbox in session
		SessionAsLocationWorkingInquiryRet listbox = (SessionAsLocationWorkingInquiryRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "previous");
	}

	//#CM39192
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
		//#CM39193
		//save the listbox in session
		SessionAsLocationWorkingInquiryRet listbox = (SessionAsLocationWorkingInquiryRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "last");
	}

	//#CM39194
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
		//#CM39195
		//save the listbox in session
		SessionAsLocationWorkingInquiryRet listbox = (SessionAsLocationWorkingInquiryRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "first");
	}

	//#CM39196
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39197
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
		//#CM39198
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute(LISTBOX);
		//#CM39199
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM39200
				// close the statement object
				finder.close();
			}
			//#CM39201
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM39202
		// delete from session
		this.getSession().removeAttribute(LISTBOX);
		//#CM39203
		// return to origin screen
		parentRedirect(null);
	}

	//#CM39204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}
	//#CM39205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39206
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWareHouse_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM39207
//end of class
