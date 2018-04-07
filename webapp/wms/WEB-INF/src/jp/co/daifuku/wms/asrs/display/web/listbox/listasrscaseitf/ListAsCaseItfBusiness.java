// $Id: ListAsCaseItfBusiness.java,v 1.2 2006/10/26 05:18:28 suresh Exp $

//#CM38289
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrscaseitf;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsCaseItfStockRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM38290
/**
 * <FONT COLOR="BLUE">
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * class to search Case ITF<BR>
 * Warehouse No.,Consignor code,Item code,case piece flag entered from originating screen<BR>
 * Retrieve it based on Start Location, end location, and Case ITF. <BR>
 * <BR>
 * 1.initial screen(<CODE>page_Load(ActionEvent e)</CODE> Method )<BR>
 * <BR>
 * <DIR>
 *  Retrieve it based on Warehouse No,Consignor code,Item code,Start Location,end location,case piece flag,Case ITF input from the screen.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * 2.Button of selected line(<CODE>lst_StockCaseItf_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass Case ITF of the selection line to the parents screen, and close the list box.<BR>
 * <BR>
 * </FONT>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/06</TD><TD>K.Toda</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:18:28 $
 * @author  $Author: suresh $
 */
public class ListAsCaseItfBusiness extends ListAsCaseItf implements WMSConstants
{
	//#CM38291
	// Class fields --------------------------------------------------
	//#CM38292
	/** 
	 * key to transfer case/piece flag
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM38293
	/** 
	 * key to transfer case itf
	 */
	public static final String CASEITF_KEY = "CASEITF_KEY";

	//#CM38294
	/** 
	 * key to transfer search flag
	 */
	public static final String SEARCHITEM_KEY = "SEARCHITEM_KEY";


	//#CM38295
	// Class variables -----------------------------------------------

	//#CM38296
	// Class method --------------------------------------------------

	//#CM38297
	// Constructors --------------------------------------------------

	//#CM38298
	// Public methods ------------------------------------------------
	//#CM38299
	/**
	 * initial screen display<BR>
	 * <DIR>
	 *	item <BR>
	 *	<DIR>
	 *		select <BR>
	 *		Case ITF <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM38300
		// set screen name
		//#CM38301
		// search Case ITF
		lbl_ListName.setText(DisplayText.getText("TLE-W0062"));

		//#CM38302
		// fetch parameter
		//#CM38303
		// area no.
		String areano = request.getParameter(ListAsLocationBusiness.AREANO_KEY);
		//#CM38304
		// consignor code
		String consignorcode = request.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM38305
		// item code
		String itemcode = request.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM38306
		// case piece flag
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		//#CM38307
		// start location
		String startlocation = request.getParameter(ListAsLocationBusiness.STARTLOCATION_KEY);
		//#CM38308
		// end location
		String endlocation = request.getParameter(ListAsLocationBusiness.ENDLOCATION_KEY);
		//#CM38309
		// Case ITF
		String caseitf = request.getParameter(CASEITF_KEY);
		//#CM38310
		// search flag (stock)
		String searchitem = request.getParameter(SEARCHITEM_KEY);
		this.getViewState().setString(SEARCHITEM_KEY, searchitem);

		//#CM38311
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM38312
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM38313
			// close connection
			sRet.closeConnection();
			//#CM38314
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM38315
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM38316
		// area no.
		param.setAreaNo(areano);
		//#CM38317
		// consignor code
		param.setConsignorCode(consignorcode);
		//#CM38318
		// item code
		param.setItemCode(itemcode);
		//#CM38319
		// case piece flag
		param.setCasePieceFlag(casepieceflag);
		//#CM38320
		// start location
		param.setFromLocationNo(startlocation);
		//#CM38321
		// end location
		param.setToLocationNo(endlocation);
		//#CM38322
		// Case ITF
		param.setITF(caseitf);

		//#CM38323
		// Whether the stock of retrieved location is judged. 
		if (searchitem.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38324
			// generate SessionRet instance
			SessionAsCaseItfStockRet listbox = new SessionAsCaseItfStockRet(conn, param);
			//#CM38325
			//save the listbox in session
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
		}
	}

	//#CM38326
	// Package methods -----------------------------------------------

	//#CM38327
	// Protected methods ---------------------------------------------

	//#CM38328
	// Private methods -----------------------------------------------
	//#CM38329
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsCaseItfStockRet listbox, String actionName)
	{
		//#CM38330
		// set page info
		listbox.setActionName(actionName);

		//#CM38331
		// fetch search result
		AsScheduleParameter[] stockparam = listbox.getEntities();

		int len = 0;
		if (stockparam != null)
			len = stockparam.length;
		if (len > 0)
		{
			//#CM38332
			// set value in pager
			//#CM38333
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM38334
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM38335
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM38336
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM38337
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM38338
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM38339
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM38340
			// delete all rows
			lst_ListStockCaseItf.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM38341
				// fetch last row
				int count = lst_ListStockCaseItf.getMaxRows();
				//#CM38342
				// add row
				lst_ListStockCaseItf.addRow();

				//#CM38343
				// move to last record
				lst_ListStockCaseItf.setCurrentRow(count);
				lst_ListStockCaseItf.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStockCaseItf.setValue(2, stockparam[i].getITF());
			}
		}
		else
		{
			//#CM38344
			// set value to Pager
			//#CM38345
			// max. number
			pgr_U.setMax(0);
			//#CM38346
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM38347
			// start position
			pgr_U.setIndex(0);
			//#CM38348
			// max. number
			pgr_D.setMax(0);
			//#CM38349
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM38350
			// start position
			pgr_D.setIndex(0);

			//#CM38351
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM38352
			// hide the header
			lst_ListStockCaseItf.setVisible(false);
			//#CM38353
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM38354
	// Event handler methods -----------------------------------------
	//#CM38355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38356
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38357
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

	//#CM38358
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

	//#CM38359
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

	//#CM38360
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

	//#CM38361
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

	//#CM38362
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38363
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM38364
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM38365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM38366
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM38367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38368
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM38369
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	Pass Case ITF to the parents screen, and close the list box. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_ListStockCaseItf_Click(ActionEvent e) throws Exception
	{
		//#CM38370
		// set the current row
		lst_ListStockCaseItf.setCurrentRow(lst_ListStockCaseItf.getActiveRow());
		lst_ListStockCaseItf.getValue(1);

		//#CM38371
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM38372
		// Case ITF
		param.setParameter(CASEITF_KEY, lst_ListStockCaseItf.getValue(2));

		//#CM38373
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM38374
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
		//#CM38375
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38376
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38377
			//save the listbox in session
			SessionAsCaseItfStockRet listbox = (SessionAsCaseItfStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}
	}

	//#CM38378
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
		//#CM38379
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38380
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38381
			//save the listbox in session
			SessionAsCaseItfStockRet listbox = (SessionAsCaseItfStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
	}

	//#CM38382
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
		//#CM38383
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38384
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38385
			//save the listbox in session
			SessionAsCaseItfStockRet listbox = (SessionAsCaseItfStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
	}

	//#CM38386
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
		//#CM38387
		// fetch search flag
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		//#CM38388
		// stock
		if (flag.equals(AsScheduleParameter.SEARCHFLAG_STOCK))
		{
			//#CM38389
			//save the listbox in session
			SessionAsCaseItfStockRet listbox = (SessionAsCaseItfStockRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}
	}

	//#CM38390
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38391
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
		//#CM38392
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM38393
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM38394
				// close the statement object
				finder.close();
			}
			//#CM38395
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM38396
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM38397
		// return to origin screen
		parentRedirect(null);
	}

}
//#CM38398
//end of class
