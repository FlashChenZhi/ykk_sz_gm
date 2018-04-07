// $Id: ListStockItemWorkingInquiryBusiness.java,v 1.2 2006/10/04 05:11:34 suresh Exp $

//#CM5607
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitemworkinginquiry;
import java.sql.Connection;
import java.util.Locale;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionItemWorkingInquiryRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM5608
/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * This is stock inquiry per item (display) listbox class.
 * Search for the data using Consignor code<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *  Search for the data using Consignor code<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>A.nagsawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:11:34 $
 * @author  $Author: suresh $
 */
public class ListStockItemWorkingInquiryBusiness extends ListStockItemWorkingInquiry implements WMSConstants
{
	//#CM5609
	// Class fields --------------------------------------------------

	//#CM5610
	// Class variables -----------------------------------------------

	//#CM5611
	// Class method --------------------------------------------------

	//#CM5612
	// Constructors --------------------------------------------------

	//#CM5613
	// Public methods ------------------------------------------------

	//#CM5614
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 * Item <BR>
	 *	<DIR>
	 *    Item code<BR>
	 *	  Packing qty per Case<BR>
	 *	  Stock Case qty<BR>
	 *	  allocation possible case qty<BR>
	 *	  Division<BR>
	 *	  Location<BR>
	 *	  Case ITF<BR>
	 *	  Storage Date<BR>
	 *	  Expiry Date<BR>
	 *	  Item name<BR>
	 *	  Packing qty per Bundle<BR>
	 *	  Stock Piece qty<BR>
	 *	  allocation possible case qty<BR>
	 *	  Bundle ITF<BR>
	 *	  Storage Time<BR>
	 *	</DIR>
	 *</DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM5615
		// Set screen name
		//#CM5616
		// Item stock inquiry
		lbl_ListName.setText(DisplayText.getText("TLE-W0061"));

		//#CM5617
		// Obtain parameter
		//#CM5618
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM5619
		// Start item code
		String startitemcode = request.getParameter(ListStockItemBusiness.STARTITEMCODE_KEY);
		//#CM5620
		// End item code
		String enditemcode = request.getParameter(ListStockItemBusiness.ENDITEMCODE_KEY);
		//#CM5621
		// Case/Piece division
		String cpflag = request.getParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY);

		//#CM5622
		// Check the Consignor code for mandatory input and forbidden character.
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ItemWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5623
		// Check the Start item code for forbidden character.
		if (!WmsCheckker.charCheck(startitemcode, lst_ItemWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM5624
		// Check the End item code for forbidden character.
		if (!WmsCheckker.charCheck(enditemcode, lst_ItemWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM5625
		// Compare the item code size
		if (!WmsCheckker
			.rangeItemCodeCheck(startitemcode, enditemcode, lst_ItemWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5626
		// Set the Consignor code for the search conditions.
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		lbl_JavaSetStrtItenCd.setText(startitemcode);
		lbl_JavaSetEndItemCd.setText(enditemcode);
		if (cpflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0375"));
		}
		else if (cpflag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0376"));
		}
		else if (cpflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0374"));
		}
		else if (cpflag.equals(StockControlParameter.CASEPIECE_FLAG_ALL))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0346"));
		}

		//#CM5627
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM5628
			// Close the connection
			sRet.closeConnection();
			//#CM5629
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM5630
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM5631
		// Set the parameter.
		StockControlParameter stockparam = new StockControlParameter();
		//#CM5632
		// Consignor code
		stockparam.setConsignorCode(consignorcode);
		//#CM5633
		// Start item code
		stockparam.setFromItemCode(startitemcode);
		//#CM5634
		// End item code
		stockparam.setToItemCode(enditemcode);
		//#CM5635
		// Case/Piece division
		stockparam.setCasePieceFlag(cpflag);

		//#CM5636
		// Generate SessionItemWorkingInquiryRet instance.
		SessionItemWorkingInquiryRet listbox = new SessionItemWorkingInquiryRet(conn, stockparam);
		//#CM5637
		// Store listbox to the Session
		this.getSession().setAttribute("LISTBOX", listbox);

		setList(listbox, "first");
	}

	//#CM5638
	// Package methods -----------------------------------------------

	//#CM5639
	// Protected methods ---------------------------------------------

	//#CM5640
	// Private methods -----------------------------------------------

	//#CM5641
	/**
	 * Method to change the page <BR>
	 * Search Stock info table <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionItemWorkingInquiryRet listbox, String actionName) throws Exception
	{
		//#CM5642
		// Obtain the locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM5643
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM5644
		// Obtain search result
		StockControlParameter[] stockparam = listbox.getEntities();

		int len = 0;
		if (stockparam != null)
		{
			len = stockparam.length;
		}
		if (len > 0)
		{
			//#CM5645
			// Set the Consignor name for the search conditions.
			lbl_JavaSetCnsgnrNm.setText(stockparam[0].getConsignorName());

			//#CM5646
			// Set the value for the pager.
			//#CM5647
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM5648
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM5649
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM5650
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM5651
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM5652
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM5653
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM5654
			// Delete all lines.
			lst_ItemWorkingInquiry.clearRow();

			//#CM5655
			// Use in ToolTip
			String title_ItemName = DisplayText.getText("LBL-W0103");
			String title_StrageDate = DisplayText.getText("LBL-W0237");
			String title_StrageTime = DisplayText.getText("LBL-W0368");
			String title_UseByDate = DisplayText.getText("LBL-W0270");
			String title_AreaTypeName = DisplayText.getText("LBL-W0569");

			for (int i = 0; i < len; i++)
			{
				//#CM5656
				// Obtain the tailing line.
				int count = lst_ItemWorkingInquiry.getMaxRows();
				//#CM5657
				// Add line
				lst_ItemWorkingInquiry.addRow();

				//#CM5658
				// Move to the end line.
				lst_ItemWorkingInquiry.setCurrentRow(count);
				lst_ItemWorkingInquiry.setValue(0, stockparam[i].getCasePieceFlag());
				lst_ItemWorkingInquiry.setValue(1, stockparam[i].getItemCode());
				lst_ItemWorkingInquiry.setValue(2, WmsFormatter.getNumFormat(stockparam[i].getEnteringQty()));
				lst_ItemWorkingInquiry.setValue(3, WmsFormatter.getNumFormat(stockparam[i].getStockCaseQty()));
				lst_ItemWorkingInquiry.setValue(4, WmsFormatter.getNumFormat(stockparam[i].getAllocateCaseQty()));
				lst_ItemWorkingInquiry.setValue(5, stockparam[i].getCasePieceFlagName());
				//#CM5659
				// 
				lst_ItemWorkingInquiry.setValue(
					6,
					WmsFormatter.toDispLocation(
						stockparam[i].getLocationNo(),
						stockparam[i].getAreaNo(),
						stockparam[i].getAreaNoArray()));

				lst_ItemWorkingInquiry.setValue(7, stockparam[i].getITF());
				lst_ItemWorkingInquiry.setValue(
					8,
					WmsFormatter.toDispDate(WmsFormatter.toParamDate(stockparam[i].getStorageDate()), locale));
				lst_ItemWorkingInquiry.setValue(9, stockparam[i].getUseByDate());
				lst_ItemWorkingInquiry.setValue(10, stockparam[i].getItemName());
				lst_ItemWorkingInquiry.setValue(11, WmsFormatter.getNumFormat(stockparam[i].getBundleEnteringQty()));
				lst_ItemWorkingInquiry.setValue(12, WmsFormatter.getNumFormat(stockparam[i].getStockPieceQty()));
				lst_ItemWorkingInquiry.setValue(13, WmsFormatter.getNumFormat(stockparam[i].getAllocatePieceQty()));
				lst_ItemWorkingInquiry.setValue(14, stockparam[i].getBundleITF());
				lst_ItemWorkingInquiry.setValue(15, WmsFormatter.getTimeFormat(stockparam[i].getStorageDate(), ""));

				//#CM5660
				// Compile the data for ToolTip
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_ItemName, stockparam[i].getItemName());
				toolTip.add(title_StrageDate, WmsFormatter.getDateFormat(stockparam[i].getStorageDate(), ""));
				toolTip.add(title_StrageTime, WmsFormatter.getTimeFormat(stockparam[i].getStorageDate(), ""));
				toolTip.add(title_UseByDate, stockparam[i].getUseByDate());
				toolTip.add(title_AreaTypeName, stockparam[i].getAreaName());

				//#CM5661
				// Set ToolTip	
				lst_ItemWorkingInquiry.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM5662
			// Set the value for the Pager.
			//#CM5663
			// Maximum Count
			pgr_U.setMax(0);
			//#CM5664
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM5665
			// Start Position
			pgr_U.setIndex(0);
			//#CM5666
			// Maximum Count
			pgr_D.setMax(0);
			//#CM5667
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM5668
			// Start Position
			pgr_D.setIndex(0);

			//#CM5669
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM5670
			// Hide the header.
			lst_ItemWorkingInquiry.setVisible(false);
			//#CM5671
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM5672
	// Event handler methods -----------------------------------------
	//#CM5673
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5674
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5675
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5676
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5677
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5678
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5679
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStrtItenCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5680
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5681
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetEndItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5682
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5683
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

	//#CM5684
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

	//#CM5685
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

	//#CM5686
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

	//#CM5687
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

	//#CM5688
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5689
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemWorkingInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM5690
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemWorkingInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM5691
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemWorkingInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM5692
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemWorkingInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM5693
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemWorkingInquiry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5694
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemWorkingInquiry_Change(ActionEvent e) throws Exception
	{
	}

	//#CM5695
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemWorkingInquiry_Click(ActionEvent e) throws Exception
	{
	}

	//#CM5696
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
		//#CM5697
		// Maintain the listbox in the Session
		SessionItemWorkingInquiryRet listbox = (SessionItemWorkingInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM5698
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
		//#CM5699
		// Maintain the listbox in the Session
		SessionItemWorkingInquiryRet listbox = (SessionItemWorkingInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM5700
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
		//#CM5701
		// Maintain the listbox in the Session
		SessionItemWorkingInquiryRet listbox = (SessionItemWorkingInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM5702
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
		//#CM5703
		// Maintain the listbox in the Session
		SessionItemWorkingInquiryRet listbox = (SessionItemWorkingInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM5704
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5705
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
		//#CM5706
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM5707
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM5708
				// Close the statement
				finder.close();
			}
			//#CM5709
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM5710
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM5711
		// Return to the parent screen.
		parentRedirect(null);
	}
	//#CM5712
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5713
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCasePiece_Server(ActionEvent e) throws Exception
	{
	}

}
//#CM5714
//end of class
