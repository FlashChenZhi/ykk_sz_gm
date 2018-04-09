// $Id: ListAsBundleItfBusiness.java,v 1.2 2006/10/26 05:17:43 suresh Exp $

//#CM38173
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsbundleitf;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrscaseitf.ListAsCaseItfBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsBundleItfStockRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM38174
/**
 * <FONT COLOR="BLUE">
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * The search bundle ITF class. <BR>
 * Retrieve it based on Warehouse No, Consignor code, Item code, case piece flag, Start Location, end location, <BR>
 * Case ITF, and ball ITF input from the parents screen.<BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Retrieve it based on Warehouse No, Consignor code, Item code, Start Location, end location, case piece flag, Case ITF, and ball ITF input from the parents screen. <BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_StockBundleItf_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass Bundle ITF of the selection line to the parents screen, and close the list box.<BR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/07</TD><TD>K.Toda</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:17:43 $
 * @author  $Author: suresh $
 */
public class ListAsBundleItfBusiness extends ListAsBundleItf implements WMSConstants
{
	//#CM38175
	// Class fields --------------------------------------------------
	//#CM38176
	/** 
	 * key to transfer case/piece flag
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM38177
	/** 
	 * The key used to hand over Bundle ITF. 
	 */
	public static final String BUNDLEITF_KEY = "BUNDLEITF_KEY";

	//#CM38178
	/** 
	 * key to transfer search flag
	 */
	public static final String SEARCHITEM_KEY = "SEARCHITEM_KEY";


	//#CM38179
	// Class variables -----------------------------------------------

	//#CM38180
	// Class method --------------------------------------------------

	//#CM38181
	// Constructors --------------------------------------------------

	//#CM38182
	// Public methods ------------------------------------------------

	//#CM38183
	/**
	 * screen initialization<BR>
	 * <DIR>
	 *	item <BR>
	 *	<DIR>
	 *		select <BR>
	 *		bundle itf <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM38184
		// set screen name
		//#CM38185
		// search bundle ITF
		lbl_ListName.setText(DisplayText.getText("TLE-W0063"));

		//#CM38186
		// fetch parameter
		//#CM38187
		// area no.
		String areano = request.getParameter(ListAsLocationBusiness.AREANO_KEY);
		//#CM38188
		// consignor code
		String consignorcode = request.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM38189
		// item code
		String itemcode = request.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM38190
		// case piece flag
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		//#CM38191
		// start location
		String startlocation = request.getParameter(ListAsLocationBusiness.STARTLOCATION_KEY);
		//#CM38192
		// end location
		String endlocation = request.getParameter(ListAsLocationBusiness.ENDLOCATION_KEY);
		//#CM38193
		// Case ITF
		String caseitf = request.getParameter(ListAsCaseItfBusiness.CASEITF_KEY);
		//#CM38194
		// bundle itf
		String bundleitf = request.getParameter(BUNDLEITF_KEY);
		//#CM38195
		// search flag (stock)
		String searchitem = request.getParameter(SEARCHITEM_KEY);
		this.getViewState().setString(SEARCHITEM_KEY, searchitem);

		//#CM38196
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM38197
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM38198
			// close connection
			sRet.closeConnection();
			//#CM38199
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM38200
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM38201
		// area no.
		param.setAreaNo(areano);
		//#CM38202
		// consignor code
		param.setConsignorCode(consignorcode);
		//#CM38203
		// item code
		param.setItemCode(itemcode);
		//#CM38204
		// case piece flag
		param.setCasePieceFlag(casepieceflag);
		//#CM38205
		// start location
		param.setFromLocationNo(startlocation);
		//#CM38206
		// end location
		param.setToLocationNo(endlocation);
		//#CM38207
		// Case ITF
		param.setITF(caseitf);
		//#CM38208
		// bundle itf
		param.setBundleITF(bundleitf);

		//#CM38209
		// check whether the bundle ITF exists in stock
		if (searchitem.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38210
			// generate SessionRet instance
			SessionAsBundleItfStockRet listbox = new SessionAsBundleItfStockRet(conn, param);
			//#CM38211
			//save the listbox in session
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
		}
	}

	//#CM38212
	// Package methods -----------------------------------------------

	//#CM38213
	// Protected methods ---------------------------------------------

	//#CM38214
	// Private methods -----------------------------------------------
	//#CM38215
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsBundleItfStockRet listbox, String actionName)
	{
		//#CM38216
		// set page info
		listbox.setActionName(actionName);

		//#CM38217
		// fetch search result
		AsScheduleParameter[] stockparam = listbox.getEntities();

		int len = 0;
		if (stockparam != null)
			len = stockparam.length;
		if (len > 0)
		{
			//#CM38218
			// set value in pager
			//#CM38219
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM38220
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM38221
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM38222
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM38223
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM38224
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM38225
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM38226
			// delete all rows
			lst_ListStockBundleItf.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM38227
				// fetch last row
				int count = lst_ListStockBundleItf.getMaxRows();
				//#CM38228
				// add row
				lst_ListStockBundleItf.addRow();

				//#CM38229
				// move to last record
				lst_ListStockBundleItf.setCurrentRow(count);
				lst_ListStockBundleItf.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStockBundleItf.setValue(2, stockparam[i].getBundleITF());
			}
		}
		else
		{
			//#CM38230
			// set value to Pager
			//#CM38231
			// max. number
			pgr_U.setMax(0);
			//#CM38232
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM38233
			// start position
			pgr_U.setIndex(0);
			//#CM38234
			// max. number
			pgr_D.setMax(0);
			//#CM38235
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM38236
			// start position
			pgr_D.setIndex(0);

			//#CM38237
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM38238
			// hide the header
			lst_ListStockBundleItf.setVisible(false);
			//#CM38239
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM38240
	// Event handler methods -----------------------------------------
	//#CM38241
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38242
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38243
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

	//#CM38244
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

	//#CM38245
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

	//#CM38246
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

	//#CM38247
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

	//#CM38248
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38249
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM38250
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM38251
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM38252
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM38253
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38254
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM38255
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	Pass Bundle ITF to the parents screen, and close the list box.<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_ListStockBundleItf_Click(ActionEvent e) throws Exception
	{
		//#CM38256
		// set the current row
		lst_ListStockBundleItf.setCurrentRow(lst_ListStockBundleItf.getActiveRow());
		lst_ListStockBundleItf.getValue(1);

		//#CM38257
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM38258
		// bundle itf
		param.setParameter(BUNDLEITF_KEY, lst_ListStockBundleItf.getValue(2));

		//#CM38259
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM38260
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
		//#CM38261
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38262
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38263
			//save the listbox in session
			SessionAsBundleItfStockRet listbox = (SessionAsBundleItfStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}
	}

	//#CM38264
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
		//#CM38265
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38266
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38267
			//save the listbox in session
			SessionAsBundleItfStockRet listbox = (SessionAsBundleItfStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
	}

	//#CM38268
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
		//#CM38269
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38270
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38271
			//save the listbox in session
			SessionAsBundleItfStockRet listbox = (SessionAsBundleItfStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
	}

	//#CM38272
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
		//#CM38273
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38274
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38275
			//save the listbox in session
			SessionAsBundleItfStockRet listbox = (SessionAsBundleItfStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}
	}

	//#CM38276
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38277
	/** 
	 * call the [Close] button click event process <BR>
	 * <BR>
	 * close the listbox and return to the caller screen<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM38278
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM38279
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM38280
				// close the statement object
				finder.close();
			}
			//#CM38281
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM38282
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM38283
		// return to origin screen
		parentRedirect(null);
	}

}
//#CM38284
//end of class
