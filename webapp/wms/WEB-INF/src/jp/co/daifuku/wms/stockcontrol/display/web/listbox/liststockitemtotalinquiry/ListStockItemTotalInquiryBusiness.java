// $Id: ListStockItemTotalInquiryBusiness.java,v 1.2 2006/10/04 05:11:26 suresh Exp $

//#CM5495
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitemtotalinquiry;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
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
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionItemTotalInquiryRet;
import jp.co.daifuku.wms.stockcontrol.entity.StockControlStock;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM5496
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * This is stock inquiry per item (stock list per item) search listbox class.<BR>
 * Search for the data using Consignor code<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor code<BR>
 * <BR>
 * </DIR>
 * 2.The button on the line selected(<CODE>lst_ItemToralInquiry_Click(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the item code of the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/19</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:11:26 $
 * @author  $Author: suresh $
 */
public class ListStockItemTotalInquiryBusiness
	extends ListStockItemTotalInquiry
	implements WMSConstants
{
	//#CM5497
	// Class fields --------------------------------------------------

	//#CM5498
	// Class variables -----------------------------------------------

	//#CM5499
	// Class method --------------------------------------------------

	//#CM5500
	// Constructors --------------------------------------------------

	//#CM5501
	// Public methods ------------------------------------------------

	//#CM5502
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Item code <BR>
	 *		Packing qty per case <BR>
	 *		Stock Case Qty <BR>
	 *		allocation possible Case qty <BR>
	 *		Item name <BR>
	 *		packed qty per bundle <BR>
	 *		Stock piece qty <BR>
	 *		allocation possible Piece qty <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM5503
		// Set the screen name
		//#CM5504
		// Item stock inquiry
		lbl_ListName.setText(DisplayText.getText("TLE-W0061"));

		//#CM5505
		// Obtain parameter
		//#CM5506
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM5507
		// Start item code
		String startitemcode = request.getParameter(ListStockItemBusiness.STARTITEMCODE_KEY);
		//#CM5508
		// End item code
		String enditemcode = request.getParameter(ListStockItemBusiness.ENDITEMCODE_KEY);
		//#CM5509
		// Case/PieceDivision
		String casepieceflag = request.getParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY);

		//#CM5510
		// Check the Consignor code for mandatory input and forbidden character.
		if (!WmsCheckker
			.consignorCheck(consignorcode, lst_ItemToralInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5511
		// Check the Start item code for forbidden character.
		if (!WmsCheckker.charCheck(startitemcode, lst_ItemToralInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5512
		// Check the End item code for forbidden character.
		if (!WmsCheckker.charCheck(enditemcode, lst_ItemToralInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5513
		// Check the item code size.
		if (!WmsCheckker
			.rangeItemCodeCheck(
				startitemcode,
				enditemcode,
				lst_ItemToralInquiry,
				pgr_U,
				pgr_D,
				lbl_InMsg))
		{
			return;
		}

		//#CM5514
		// Show search conditions on the screen.
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		lbl_JavaSetStItem.setText(startitemcode);
		lbl_JavaSetEdItem.setText(enditemcode);
		if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0375"));
		}
		else if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0376"));
		}
		else if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0374"));
		}

		else if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_ALL))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0346"));
		}

		//#CM5515
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM5516
			// Close the connection
			sRet.closeConnection();
			//#CM5517
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM5518
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM5519
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		//#CM5520
		// Consignor code
		param.setConsignorCode(consignorcode);
		//#CM5521
		// Start item code
		param.setFromItemCode(startitemcode);
		//#CM5522
		// End item code
		param.setToItemCode(enditemcode);
		//#CM5523
		// Case/PieceDivision
		param.setCasePieceFlag(casepieceflag);

		//#CM5524
		// Generate SessionItemTotalInquiryRet instance.
		SessionItemTotalInquiryRet listbox = new SessionItemTotalInquiryRet(conn, param);
		//#CM5525
		// Store listbox to the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM5526
	// Package methods -----------------------------------------------

	//#CM5527
	// Protected methods ---------------------------------------------

	//#CM5528
	// Private methods -----------------------------------------------
	//#CM5529
	/**
	 * Method to change the page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionItemTotalInquiryRet listbox, String actionName) throws Exception
	{
		//#CM5530
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM5531
		// Obtain search result
		StockControlStock[] result = listbox.getEntities();

		int len = 0;
		if (result != null)
		{
			len = result.length;
		}
		
		if (len > 0)
		{
			//#CM5532
			// Set the Consignor name for search conditions.
			lbl_JavaSetCnsgnrNm.setText(result[0].getConsignorName());

			//#CM5533
			// Set the value for the Pager.
			//#CM5534
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM5535
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM5536
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM5537
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM5538
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM5539
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM5540
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM5541
			// Delete all lines.
			lst_ItemToralInquiry.clearRow();
			for (int i = 0; i < len; i++)
			{
				//#CM5542
				// Obtain the tailing line.
				int count = lst_ItemToralInquiry.getMaxRows();
				//#CM5543
				// Add line
				lst_ItemToralInquiry.addRow();

				//#CM5544
				// Move to the end line.
				lst_ItemToralInquiry.setCurrentRow(count);
				lst_ItemToralInquiry.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemToralInquiry.setValue(2, result[i].getItemCode());
				lst_ItemToralInquiry.setValue(
					3,
					WmsFormatter.getNumFormat(result[i].getEnteringQty()));
				lst_ItemToralInquiry.setValue(
					4,
					WmsFormatter.getNumFormat(result[i].getStockCaseQty()));
				lst_ItemToralInquiry.setValue(
					5,
					WmsFormatter.getNumFormat(result[i].getAllocationCaseQty()));
				lst_ItemToralInquiry.setValue(6, result[i].getItemName1());
				lst_ItemToralInquiry.setValue(
					7,
					WmsFormatter.getNumFormat(result[i].getBundleEnteringQty()));
				lst_ItemToralInquiry.setValue(8,WmsFormatter.getNumFormat(result[i].getStockPieceQty()));
				lst_ItemToralInquiry.setValue(
					9,
					WmsFormatter.getNumFormat(result[i].getAllocationPieceQty()));
			}
		}
		else
		{
			//#CM5545
			// Set the value for the Pager.
			//#CM5546
			// Maximum Count
			pgr_U.setMax(0);
			//#CM5547
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM5548
			// Start Position
			pgr_U.setIndex(0);
			//#CM5549
			// Maximum Count
			pgr_D.setMax(0);
			//#CM5550
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM5551
			// Start Position
			pgr_D.setIndex(0);

			//#CM5552
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM5553
			// Hide the header.
			lst_ItemToralInquiry.setVisible(false);
			//#CM5554
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM5555
	// Event handler methods -----------------------------------------
	//#CM5556
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5557
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5558
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5559
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5560
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5561
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5562
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5563
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5564
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetEdItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5565
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5566
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

	//#CM5567
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

	//#CM5568
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

	//#CM5569
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

	//#CM5570
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

	//#CM5571
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5572
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemToralInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM5573
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemToralInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM5574
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemToralInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM5575
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemToralInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM5576
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemToralInquiry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5577
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemToralInquiry_Change(ActionEvent e) throws Exception
	{
	}

	//#CM5578
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *	To parent screen<BR>
	 *	<DIR>
	 *		Item code <BR>
	 *	</DIR>
	 *	Pass it and close the list box. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_ItemToralInquiry_Click(ActionEvent e) throws Exception
	{
		//#CM5579
		// Set the current line.
		lst_ItemToralInquiry.setCurrentRow(lst_ItemToralInquiry.getActiveRow());
		lst_ItemToralInquiry.getValue(1);

		//#CM5580
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		//#CM5581
		// Start item code
		param.setParameter(
			ListStockItemBusiness.STARTITEMCODE_KEY,
			lst_ItemToralInquiry.getValue(2));
		//#CM5582
		// End item code
		param.setParameter(ListStockItemBusiness.ENDITEMCODE_KEY, lst_ItemToralInquiry.getValue(2));

		//#CM5583
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM5584
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
		//#CM5585
		// Store listbox to the Session
		SessionItemTotalInquiryRet listbox =
			(SessionItemTotalInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM5586
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
		//#CM5587
		// Store listbox to the Session
		SessionItemTotalInquiryRet listbox =
			(SessionItemTotalInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM5588
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
		//#CM5589
		// Store listbox to the Session
		SessionItemTotalInquiryRet listbox =
			(SessionItemTotalInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM5590
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
		//#CM5591
		// Store listbox to the Session
		SessionItemTotalInquiryRet listbox =
			(SessionItemTotalInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM5592
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5593
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
		//#CM5594
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM5595
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM5596
				// Close the statement
				finder.close();
			}
			//#CM5597
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM5598
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM5599
		// Return to the parent screen.
		parentRedirect(null);
	}
	//#CM5600
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5601
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCasePiece_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM5602
//end of class
