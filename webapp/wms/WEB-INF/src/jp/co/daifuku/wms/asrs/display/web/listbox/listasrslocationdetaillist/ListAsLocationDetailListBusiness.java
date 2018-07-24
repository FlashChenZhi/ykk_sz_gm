// $Id: ListAsLocationDetailListBusiness.java,v 1.2 2006/10/26 05:30:02 suresh Exp $

//#CM38825
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocationdetaillist;
import java.sql.Connection;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsLocationDetailRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM38826
/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * The stock list list box class according to location no.<BR>
 * Make Warehouse No. input from the parents screen, Consignor code, Item code, case piece flag, and shelf status as a key, retrieve, and display it on the screen.<BR>
 * Process it in this class as follows. <BR>
 * 
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Make Warehouse No. input from the parents screen, Consignor code, Item code, case piece flag, and shelf status as a key, retrieve, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_WLocationDetail_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass Location No of the selection line to the parents screen, and close the list box.<BR>
 * <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/06</TD><TD>C.Kaminishizono</TD><TD>new</TD></TR>
 * <TR><TD>2006/01/17</TD><TD>Y.Okamura</TD><TD>Composition change</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:30:02 $
 * @author  $Author: suresh $
 */
public class ListAsLocationDetailListBusiness extends ListAsLocationDetailList implements WMSConstants
{
	//#CM38827
	// Class fields --------------------------------------------------
	//#CM38828
	/** 
	 * key to transfer warehouse
	 */
	public static final String WAREHOUSE_KEY = "WAREHOUSE_KEY";

	//#CM38829
	/** 
	 * key to transfer warehouse name
	 */
	public static final String WAREHOUSENAME_KEY = "WAREHOUSENAME_KEY";

	//#CM38830
	/** 
	 * The key used to hand over location no. 
	 */
	public static final String LOCATION_KEY = "LOCATION_KEY";

	//#CM38831
	/** 
	 * key to transfer case/piece flag
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";
	
	//#CM38832
	/**
	 * The key used to hand over status.
	 */
	public static final String STATUS_KEY = "STATUS_KEY";

	//#CM38833
	/**
	 * type of date format:Edit date(Example:YYYY/MM/DD)
	 */
	protected final int DATE_DISPTYPE_DATE = 0;
	
	//#CM38834
	/**
	 * type of date format:Edit time(Example:hh:mm:ss)
	 */
	protected final int DATE_DISPTYPE_TIME = 1;
	
	//#CM38835
	// Class variables -----------------------------------------------

	//#CM38836
	// Class method --------------------------------------------------

	//#CM38837
	// Constructors --------------------------------------------------

	//#CM38838
	// Public methods ------------------------------------------------

	//#CM38839
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM38840
		// set screen name
		//#CM38841
		// search location
		lbl_ListName.setText(DisplayText.getText("TLE-W0913"));

		//#CM38842
		// fetch parameter
		String whStno = request.getParameter(WAREHOUSE_KEY);
		String consignorCode = request.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		String itemCode = request.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		String casePieceFlag = request.getParameter(CASEPIECEFLAG_KEY);
		String warehouseName = request.getParameter(WAREHOUSENAME_KEY);
		String[] status = request.getParameterValues(STATUS_KEY);

		//#CM38843
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM38844
		// set header item other than listcell
		//#CM38845
		// warehouse name
		lbl_JavaSetWareHouse.setText(warehouseName);
		//#CM38846
		// consignor code
		lbl_JavaSetConsignorCode.setText(consignorCode);
		//#CM38847
		// item code
		lbl_JavaSetItemCode.setText(itemCode);
		//#CM38848
		// case piece flag
		if (casePieceFlag.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0375"));
		}
		else if (casePieceFlag.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0376"));
		}
		else if (casePieceFlag.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0374"));
		}

		else if (casePieceFlag.equals(AsScheduleParameter.CASEPIECE_FLAG_ALL))
		{
			lbl_JavaSetCasePiece.setText(DisplayText.getText("LBL-W0346"));
		}
		

		//#CM38849
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM38850
			// close connection
			sRet.closeConnection();
			//#CM38851
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM38852
		// set search condition in parameter
		AsScheduleParameter param = new AsScheduleParameter();
		param.setWareHouseNo(whStno);
		param.setConsignorCode(consignorCode);
		param.setItemCode(itemCode);
		param.setCasePieceFlag(casePieceFlag);
		param.setSearchStatus(status);
		
		//#CM38853
		// create SessionLocationRet instance
		SessionAsLocationDetailRet listbox = new SessionAsLocationDetailRet(conn, param);
		//#CM38854
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM38855
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM38856
	// Package methods -----------------------------------------------

	//#CM38857
	// Protected methods ---------------------------------------------

	//#CM38858
	// Private methods -----------------------------------------------
	//#CM38859
	/**
	 * method to change page <BR>
	 * Retrieve the table.<BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsLocationDetailRet listbox, String actionName) throws Exception
	{
		//#CM38860
		// set page info
		listbox.setActionName(actionName);

		//#CM38861
		// fetch search result
		AsScheduleParameter[] param = listbox.getEntities();
		int len = 0;
		if (param != null)
			len = param.length;
		if (len > 0)
		{
			//#CM38862
			// set value to Pager
			//#CM38863
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM38864
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM38865
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM38866
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM38867
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM38868
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM38869
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM38870
			// consignor name
			lbl_JavaSetConsignorName.setText(param[0].getConsignorNameDisp());
			//#CM38871
			// item name
			lbl_JavaSetItemName.setText(param[0].getItemNameDisp());

			//#CM38872
			// delete all rows
			lst_WLocationDetail.clearRow();

			//#CM38873
			// used in tool tip
			String consinogName = DisplayText.getText("LBL-W0026");
			String itemName = DisplayText.getText("LBL-W0103");
			String instockDay = DisplayText.getText("LBL-W0237");
			String instockTime = DisplayText.getText("LBL-W0368");
			String useByDate = DisplayText.getText("LBL-W0270");
			
			//#CM38874
			// used in date display
			Locale locale = this.getHttpRequest().getLocale();

			for (int i = 0; i < len; i++)
			{
				//#CM38875
				// fetch last row
				int count = lst_WLocationDetail.getMaxRows();
				//#CM38876
				// add row
				lst_WLocationDetail.addRow();

				//#CM38877
				// move to last record
				lst_WLocationDetail.setCurrentRow(count);
				//#CM38878
				// HIDDEN
				setList(0, param[i].getLocationNo());
				//#CM38879
				// first row
				setList(1, Integer.toString(count + listbox.getCurrent()));
				setList(2, DisplayText.formatDispLocation(param[i].getLocationNo()));
				if (param[i].getEnteringQty() * param[i].getStockCaseQty() + param[i].getStockPieceQty() != 0)
				{
					setList(3, param[i].getConsignorCode());
					setList(4, param[i].getItemCode());
					setList(5, param[i].getEnteringQty());
					setList(6, param[i].getStockCaseQty());
					setList(7, param[i].getITF());
					setList(8, param[i].getCasePieceFlagNameDisp());
					setList(9, param[i].getInStockDate(), locale, DATE_DISPTYPE_DATE);
					setList(10, param[i].getUseByDate());
					//#CM38880
					// second row
					setList(11, param[i].getConsignorName());
					setList(12, param[i].getItemName());
					setList(13, param[i].getBundleEnteringQty());
					setList(14, param[i].getStockPieceQty());
					setList(15, param[i].getBundleITF());
					setList(16, param[i].getInStockDate(), locale, DATE_DISPTYPE_TIME);
				
					//#CM38881
					// add data to tool tip
					ToolTipHelper toolTip = new ToolTipHelper();
					toolTip.add(consinogName, param[i].getConsignorName());
					toolTip.add(itemName, param[i].getItemName());
					toolTip.add(instockDay, lst_WLocationDetail.getValue(9));
					toolTip.add(instockTime, lst_WLocationDetail.getValue(16));
					toolTip.add(useByDate, param[i].getUseByDate());

					//#CM38882
					// set tool tip	
					lst_WLocationDetail.setToolTip(count, toolTip.getText());
				}
				
			}
		}
		else
		{
			//#CM38883
			// set value to Pager
			//#CM38884
			// max. number
			pgr_U.setMax(0);
			//#CM38885
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM38886
			// start position
			pgr_U.setIndex(0);
			//#CM38887
			// max. number
			pgr_D.setMax(0);
			//#CM38888
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM38889
			// start position
			pgr_D.setIndex(0);

			//#CM38890
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM38891
			// hide the header
			lst_WLocationDetail.setVisible(false);
			//#CM38892
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	
	//#CM38893
	/**
	 * Set a numeric item in the list cell. 
	 * @param columnNo line no. of item to be set
	 * @param value value to be set
	 */
	protected void setList(int columnNo, int value)
	{
		setList(columnNo, WmsFormatter.getNumFormat(value));
	}

	//#CM38894
	/**
	 * Set the date item in the list cell. 
	 * @param columnNo line no. of item to be set
	 * @param value value to be set
	 * @param locale Locale
	 * @param dispType type of date format
	 */
	protected void setList(int columnNo, Date value, Locale locale, int dispType)
	{
		//#CM38895
		// edit date
		if (dispType == DATE_DISPTYPE_DATE)
		{
			setList(columnNo, WmsFormatter.toDispDate(WmsFormatter.toParamDate(value), locale));
		}
		//#CM38896
		// edit time
		else if (dispType == DATE_DISPTYPE_TIME)
		{
			setList(columnNo, WmsFormatter.getTimeFormat(value, ""));

		}
	}
	
	//#CM38897
	/**
	 * Set the character string item in the list cell. 
	 * @param columnNo line no. of item to be set
	 * @param value value to be set
	 */
	protected void setList(int columnNo, String value)
	{
		lst_WLocationDetail.setValue(columnNo, value);
	}
	
	//#CM38898
	// Event handler methods -----------------------------------------
	//#CM38899
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

	//#CM38900
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

	//#CM38901
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

	//#CM38902
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

	//#CM38903
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

	//#CM38904
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
		//#CM38905
		//save the listbox in session
		SessionAsLocationDetailRet listbox = (SessionAsLocationDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM38906
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
		//#CM38907
		//save the listbox in session
		SessionAsLocationDetailRet listbox = (SessionAsLocationDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM38908
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
		//#CM38909
		//save the listbox in session
		SessionAsLocationDetailRet listbox = (SessionAsLocationDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM38910
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
		//#CM38911
		//save the listbox in session
		SessionAsLocationDetailRet listbox = (SessionAsLocationDetailRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM38912
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
		//#CM38913
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM38914
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM38915
				// close the statement object
				finder.close();
			}
			//#CM38916
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM38917
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM38918
		// return to origin screen
		parentRedirect(null);
	}

	//#CM38919
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	Pass location no to the parents screen, and close the list box.<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_WLocationDetail_Click(ActionEvent e) throws Exception
	{
		//#CM38920
		// set the current row
		lst_WLocationDetail.setCurrentRow(lst_WLocationDetail.getActiveRow());

		//#CM38921
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM38922
		// location no.
		param.setParameter(LOCATION_KEY, lst_WLocationDetail.getValue(0));

		//#CM38923
		// move to the caller screen
		parentRedirect(param);
	}
	//#CM38924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Item_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38935
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCasePiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WLocationDetail_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM38939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WLocationDetail_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM38940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WLocationDetail_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM38941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WLocationDetail_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM38942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WLocationDetail_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WLocationDetail_Change(ActionEvent e) throws Exception
	{
	}

	//#CM38944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM38945
//end of class
