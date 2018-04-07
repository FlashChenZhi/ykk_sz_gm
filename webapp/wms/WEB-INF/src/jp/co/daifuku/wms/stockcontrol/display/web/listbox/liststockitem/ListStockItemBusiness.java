// $Id: ListStockItemBusiness.java,v 1.2 2006/10/04 05:11:19 suresh Exp $

//#CM5333
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplanretrievaldate.ListNoPlanRetrievalDateBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplanstoragedate.ListNoPlanStorageDateBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionNoPlanItemRet;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionStockItemRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM5334
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is item search listbox class.<BR>
 * Search for the data using Consignor code<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor code<BR>
 * <BR>
 * </DIR>
 * 2.The button on the line selected(<CODE>lst_ItemSearch_Click</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the item code<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:11:19 $
 * @author  $Author: suresh $
 */
public class ListStockItemBusiness extends ListStockItem implements WMSConstants
{
	//#CM5335
	// Class fields --------------------------------------------------
	//#CM5336
	/** 
	 * Use this key to pass the item code.
	 */
	public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

	//#CM5337
	/** 
	 * Use this key to pass the the Start Item code.
	 */
	public static final String STARTITEMCODE_KEY = "STARTITEMCODE_KEY";

	//#CM5338
	/** 
	 * Use this key to pass the End item code.
	 */
	public static final String ENDITEMCODE_KEY = "ENDITEMCODE_KEY";

	//#CM5339
	/** 
	 * Use this key to pass the item name.
	 */
	public static final String ITEMNAME_KEY = "ITEMNAME_KEY";
	
	//#CM5340
	/** 
	 * Use this key to pass the item code range flag.
	 */
	public static final String RANGEITEMCODE_KEY = "RANGEITEMCODE_KEY";

	//#CM5341
	/** 
	 * Use this key to pass the search flag.
	 */
	public static final String SEARCHITEM_KEY = "SEARCHITEM_KEY";	

	//#CM5342
	// Class variables -----------------------------------------------

	//#CM5343
	// Class method --------------------------------------------------

	//#CM5344
	// Constructors --------------------------------------------------

	//#CM5345
	// Public methods ------------------------------------------------
	//#CM5346
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *      Selection <BR>
	 *      Item code <BR>
	 *      Item name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM5347
		// Set the screen name
		//#CM5348
		// Search item search
		lbl_ListName.setText(DisplayText.getText("TLE-W0043"));

		//#CM5349
		// Obtain parameter
		//#CM5350
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM5351
		// Start Storage Date
		String startstoragedate = request.getParameter(ListNoPlanStorageDateBusiness.STARTSTORAGEDATE_KEY);
		//#CM5352
		// End Storage Date
		String endstoragedate = request.getParameter(ListNoPlanStorageDateBusiness.ENDSTORAGEDATE_KEY);
		//#CM5353
		// Start picking date
		String startretrievaldate = request.getParameter(ListNoPlanRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM5354
		// End picking date
		String endretrievaldate = request.getParameter(ListNoPlanRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM5355
		// Item code
		String itemcode = request.getParameter(ITEMCODE_KEY);
		//#CM5356
		// Start item code
		String startitemcode = request.getParameter(STARTITEMCODE_KEY);
		//#CM5357
		// End item code
		String enditemcode = request.getParameter(ENDITEMCODE_KEY);
		//#CM5358
		// Start location
		String startlocation = request.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);
		//#CM5359
		// End location
		String endlocation = request.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		//#CM5360
		// Area flag
		String rangeitem = request.getParameter(RANGEITEMCODE_KEY);
		//#CM5361
		// Search flag
		String searchitem = request.getParameter(SEARCHITEM_KEY);
		//#CM5362
		// Area type flag
		String areatypeflag = request.getParameter(ListStockConsignorBusiness.AREA_TYPE_KEY);
		
		viewState.setString(RANGEITEMCODE_KEY, rangeitem);
		viewState.setString(SEARCHITEM_KEY, searchitem);

		//#CM5363
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM5364
			// Close the connection
			sRet.closeConnection();
			//#CM5365
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM5366
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM5367
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		
		//#CM5368
		// Consignor code
		param.setConsignorCode(consignorcode);
		param.setSearchFlag(searchitem);
		param.setAreaTypeFlag(areatypeflag);
		
		if(searchitem == null)
		{
			
		}
		
		else if(searchitem.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5369
			// Start Storage Date
			param.setFromWorkDate(startstoragedate);
			//#CM5370
			// End Storage Date
			param.setToWorkDate(endstoragedate);
		}
		else if(searchitem.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5371
			// Start picking date
			param.setFromWorkDate(startretrievaldate);
			//#CM5372
			// End picking date
			param.setToWorkDate(endretrievaldate);	
		}
		//#CM5373
		// Item code
		param.setItemCode(itemcode);
		//#CM5374
		// Start item code
		param.setFromItemCode(startitemcode);
		//#CM5375
		// End item code
		param.setToItemCode(enditemcode);
		//#CM5376
		// Start location
		param.setFromLocationNo(startlocation);
		//#CM5377
		// End location
		param.setToLocationNo(endlocation);
		
		//#CM5378
		// Determine if search Consignor is stock or result.
		if(searchitem == null)
		{
			//#CM5379
			// Generate SessionRetrievalPlanConsignorRet instance.
			SessionStockItemRet listbox =
				new SessionStockItemRet(conn, param);
			//#CM5380
			// Store listbox to the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
		}
		else if (searchitem.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5381
			// Generate SessionRetrievalResultConsignorRet Instance
			SessionNoPlanItemRet listbox =
				new SessionNoPlanItemRet(conn, param);
			//#CM5382
			// Store listbox to the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		else if (searchitem.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5383
			// Generate SessionRetrievalResultConsignorRet Instance
			SessionNoPlanItemRet listbox =
				new SessionNoPlanItemRet(conn, param);
			//#CM5384
			// Store listbox to the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}

	}

	//#CM5385
	// Package methods -----------------------------------------------

	//#CM5386
	// Protected methods ---------------------------------------------

	//#CM5387
	// Private methods -----------------------------------------------
	//#CM5388
	/**
	 * Method to change the page <BR>
	 * Search the table <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionStockItemRet listbox, String actionName) throws Exception
	{
		//#CM5389
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM5390
		// Obtain search result
		Stock[] stock = listbox.getEntities();
		int len = 0;
		if (stock != null)
			len = stock.length;
		if (len > 0)
		{
			//#CM5391
			// Set the value for the Pager.
			//#CM5392
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM5393
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM5394
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM5395
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM5396
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM5397
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM5398
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM5399
			// Delete all lines.
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM5400
				// Obtain the tailing line.
				int count = lst_ItemSearch.getMaxRows();
				//#CM5401
				// Add line
				lst_ItemSearch.addRow();

				//#CM5402
				// Move to the end line.
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, stock[i].getItemCode());
				lst_ItemSearch.setValue(3, stock[i].getItemName1());
			}
		}
		else
		{
			//#CM5403
			// Set the value for the Pager.
			//#CM5404
			// Maximum Count
			pgr_U.setMax(0);
			//#CM5405
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM5406
			// Start Position
			pgr_U.setIndex(0);
			//#CM5407
			// Maximum Count
			pgr_D.setMax(0);
			//#CM5408
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM5409
			// Start Position
			pgr_D.setIndex(0);

			//#CM5410
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM5411
			// Hide the header.
			lst_ItemSearch.setVisible(false);
			//#CM5412
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM5413
	/**
	 * Method to change the page <BR>
	 * Search the result View. <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setResultList(SessionNoPlanItemRet listbox, String actionName) throws Exception
	{
		//#CM5414
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM5415
		// Obtain search result
		ResultView[] result = listbox.getEntities();
		int len = 0;
		if (result != null)
			len = result.length;
		if (len > 0)
		{
			//#CM5416
			// Set the value for the Pager.
			//#CM5417
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM5418
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM5419
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM5420
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM5421
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM5422
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM5423
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM5424
			// Delete all lines.
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM5425
				// Obtain the tailing line.
				int count = lst_ItemSearch.getMaxRows();
				//#CM5426
				// Add line
				lst_ItemSearch.addRow();

				//#CM5427
				// Move to the end line.
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, result[i].getItemCode());
				lst_ItemSearch.setValue(3, result[i].getItemName1());
			}
		}
		else
		{
			//#CM5428
			// Set the value for the Pager.
			//#CM5429
			// Maximum Count
			pgr_U.setMax(0);
			//#CM5430
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM5431
			// Start Position
			pgr_U.setIndex(0);
			//#CM5432
			// Maximum Count
			pgr_D.setMax(0);
			//#CM5433
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM5434
			// Start Position
			pgr_D.setIndex(0);

			//#CM5435
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM5436
			// Hide the header.
			lst_ItemSearch.setVisible(false);
			//#CM5437
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM5438
	// Event handler methods -----------------------------------------
	//#CM5439
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5440
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5441
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

	//#CM5442
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

	//#CM5443
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

	//#CM5444
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

	//#CM5445
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

	//#CM5446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM5448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM5449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM5450
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM5451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5452
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM5453
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *	Pass the item code and item name to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_ItemSearch_Click(ActionEvent e) throws Exception
	{
		//#CM5454
		// Obtain the range flag.
		String flug = viewState.getString(RANGEITEMCODE_KEY);

		//#CM5455
		// Set the current line.
		lst_ItemSearch.setCurrentRow(lst_ItemSearch.getActiveRow());
		lst_ItemSearch.getValue(1);

		//#CM5456
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM5457
			// Item code
			param.setParameter(ITEMCODE_KEY, lst_ItemSearch.getValue(2));
		}
		else if (flug.equals(StockControlParameter.RANGE_START))
		{
			//#CM5458
			// Start item code
			param.setParameter(STARTITEMCODE_KEY, lst_ItemSearch.getValue(2));
		}
		else if (flug.equals(StockControlParameter.RANGE_END))
		{
			//#CM5459
			// End item code
			param.setParameter(ENDITEMCODE_KEY, lst_ItemSearch.getValue(2));
		}

		//#CM5460
		// Item name
		param.setParameter(ITEMNAME_KEY, lst_ItemSearch.getValue(3));

		//#CM5461
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM5462
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
		//#CM5463
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		if (flag == null)
		{
			//#CM5464
			// Store listbox to the Session
			SessionStockItemRet listbox =
				(SessionStockItemRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5465
			// Store listbox to the Session
			SessionNoPlanItemRet listbox =
				(SessionNoPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");		
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5466
			// Store listbox to the Session
			SessionNoPlanItemRet listbox =
				(SessionNoPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");		
		}
	}

	//#CM5467
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
		//#CM5468
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag == null)
		{
			//#CM5469
			// Store listbox to the Session
			SessionStockItemRet listbox =
				(SessionStockItemRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5470
			// Store listbox to the Session
			SessionNoPlanItemRet listbox =
				(SessionNoPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5471
			// Store listbox to the Session
			SessionNoPlanItemRet listbox =
				(SessionNoPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
	}

	//#CM5472
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
		//#CM5473
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag == null)
		{
			//#CM5474
			// Store listbox to the Session
			SessionStockItemRet listbox =
				(SessionStockItemRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5475
			// Store listbox to the Session
			SessionNoPlanItemRet listbox =
				(SessionNoPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5476
			// Store listbox to the Session
			SessionNoPlanItemRet listbox =
				(SessionNoPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}				
	}

	//#CM5477
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
		//#CM5478
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHITEM_KEY);
		
		if (flag == null)
		{
			//#CM5479
			// Store listbox to the Session
			SessionStockItemRet listbox =
				(SessionStockItemRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5480
			// Store listbox to the Session
			SessionNoPlanItemRet listbox =
				(SessionNoPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5481
			// Store listbox to the Session
			SessionNoPlanItemRet listbox =
				(SessionNoPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}				
	}

	//#CM5482
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5483
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
		//#CM5484
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM5485
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM5486
				// Close the statement
				finder.close();
			}
			//#CM5487
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM5488
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM5489
		// Return to the parent screen.
		parentRedirect(null);
	}
}
//#CM5490
//end of class
