//#CM39793
//$Id: ListAsStoragePlanBusiness.java,v 1.2 2006/10/26 05:36:30 suresh Exp $

//#CM39794
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstorageplan;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstorageplandate.ListAsStoragePlanDateBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsStoragePlanRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM39795
/**
 * <FONT COLOR="BLUE">
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * The ASRSStorage schedule information retrieval class. <BR>
 * Retrieve it based on Consignor code, Storage Plan Date, Item code, and case piece flag input <BR>
 * from the parents screen. <BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Retrieve it based on Consignor code, Storage Plan Date, Item code, and case piece flag input from the parents screen. <BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_AsrsPlanList_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass case piece flag of the selection line, Storage Plan Date, Item code, packed qty per case, remaining case qty,<BR>
 *   packed qty per bundle, remaining piece qty, Work No, and Last updated date and time to the parents screen, and close the list box.<BR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/26</TD><TD>M.Koyama</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:36:30 $
 * @author  $Author: suresh $
 */
public class ListAsStoragePlanBusiness extends ListAsStoragePlan implements WMSConstants
{
	//#CM39796
	// Class fields --------------------------------------------------
	//#CM39797
	/** 
	 * key to transfer case/piece flag
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM39798
	/** 
	 * The key to transfer case entering qty
	 */
	public static final String ENTERINGQTY_KEY = "ENTERINGQTY_KEY";

	//#CM39799
	/** 
	 * The key used to hand over the piece entering qty.
	 */
	public static final String BUNDLEENTERINGQTY_KEY = "BUNDLEENTERINGQTY_KEY";

	//#CM39800
	/** 
	 * The key used to hand over the remaining case qty. 
	 */
	public static final String CASEQTY_KEY = "CASEQTY_KEY";

	//#CM39801
	/** 
	 * The key used to hand over the remaining piece qty. 
	 */
	public static final String PIECEQTY_KEY = "PIECEQTY_KEY";

	//#CM39802
	/** 
	 * The key used to hand over Work No. 
	 */
	public static final String JOBNO_KEY = "JOBNO_KEY";

	//#CM39803
	/** 
	 * The key used to hand over Last updated date and time. 
	 */
	public static final String LASTUPDATE_KEY = "LASTUPDATE_KEY";

	//#CM39804
	// Class variables -----------------------------------------------

	//#CM39805
	// Class method --------------------------------------------------

	//#CM39806
	// Constructors --------------------------------------------------

	//#CM39807
	// Public methods ------------------------------------------------

	//#CM39808
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM39809
		// set screen name
		//#CM39810
		// search bundle ITF
		lbl_ListName.setText(DisplayText.getText("TLE-W0055"));

		//#CM39811
		// fetch parameter
		//#CM39812
		// consignor code
		String consignorcode = request.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM39813
		// storage plan date
		String plandate = request.getParameter(ListAsStoragePlanDateBusiness.PLANDATE_KEY);
		//#CM39814
		// item code
		String itemcode = request.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM39815
		// case piece flag
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		//#CM39816
		// input check
		//#CM39817
		// consignor code
		if (!WmsCheckker.consignorCheck(consignorcode, lst_AsrsPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM39818
		// item code
		if (!WmsCheckker.charCheck(itemcode, lst_AsrsPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM39819
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM39820
		// set header item other than listcell
		//#CM39821
		// consignor code
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		//#CM39822
		// storage plan date
		txt_FDateStrt.setDate(WmsFormatter.toDate(plandate));
		//#CM39823
		// item code
		lbl_JavaSetItemCode.setText(itemcode);
		//#CM39824
		// case piece flag
		if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			lbl_JavaSetCasePieceFlag.setText(DisplayText.getText("LBL-W0375"));
		}
		else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			lbl_JavaSetCasePieceFlag.setText(DisplayText.getText("LBL-W0376"));
		}
		else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			lbl_JavaSetCasePieceFlag.setText(DisplayText.getText("LBL-W0374"));
		}

		else if (casepieceflag.equals(AsScheduleParameter.CASEPIECE_FLAG_ALL))
		{
			lbl_JavaSetCasePieceFlag.setText(DisplayText.getText("LBL-W0346"));
		}

		//#CM39825
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM39826
			// close connection
			sRet.closeConnection();
			//#CM39827
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM39828
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM39829
		// consignor code
		param.setConsignorCode(consignorcode);
		//#CM39830
		// storage plan date
		param.setPlanDate(plandate);
		//#CM39831
		// item code
		param.setItemCode(itemcode);
		//#CM39832
		// case piece flag
		param.setCasePieceFlag(casepieceflag);

		//#CM39833
		// generate SessionRet instance
		SessionAsStoragePlanRet listbox = new SessionAsStoragePlanRet(conn, param);
		//#CM39834
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM39835
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM39836
	// Package methods -----------------------------------------------

	//#CM39837
	// Protected methods ---------------------------------------------

	//#CM39838
	// Private methods -----------------------------------------------
	//#CM39839
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsStoragePlanRet listbox, String actionName)
	{
		//#CM39840
		// fetch locale
		Locale locale = this.getHttpRequest().getLocale();
		
		//#CM39841
		// set page info
		listbox.setActionName(actionName);

		//#CM39842
		// fetch search result
		AsScheduleParameter[] rparam = listbox.getEntities();

		int len = 0;
		if (rparam != null)
			len = rparam.length;
		if (len > 0)
		{
			//#CM39843
			// set value in pager
			//#CM39844
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM39845
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM39846
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM39847
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM39848
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM39849
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM39850
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM39851
			// consignor name
			lbl_JavaSetCnsgnrNm.setText(rparam[0].getConsignorNameDisp());
			//#CM39852
			// item name
			lbl_JavaSetItemName.setText(rparam[0].getItemNameDisp());

			//#CM39853
			// delete all rows
			lst_AsrsPlanList.clearRow();

			//#CM39854
			// used in tool tip
			//#CM39855
			// storage location
			String title_Location = DisplayText.getText("LBL-W0238");
			//#CM39856
			// LBL-W0103=Item description
			String titleItemName = DisplayText.getText("LBL-W0103");

			for (int i = 0; i < len; i++)
			{
				//#CM39857
				// fetch last row
				int count = lst_AsrsPlanList.getMaxRows();
				//#CM39858
				// add row
				lst_AsrsPlanList.addRow();

				//#CM39859
				// move to last record
				lst_AsrsPlanList.setCurrentRow(count);
				lst_AsrsPlanList.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_AsrsPlanList.setValue(2, WmsFormatter.toDispDate(rparam[i].getPlanDate(), locale));
				lst_AsrsPlanList.setValue(3, rparam[i].getItemCode());
				lst_AsrsPlanList.setValue(4, rparam[i].getCasePieceFlagNameDisp());
				lst_AsrsPlanList.setValue(5, WmsFormatter.getNumFormat(rparam[i].getEnteringQty()));
				lst_AsrsPlanList.setValue(6, WmsFormatter.getNumFormat(rparam[i].getStorageCaseQty()));
				lst_AsrsPlanList.setValue(7, rparam[i].getItemName());
				lst_AsrsPlanList.setValue(8, WmsFormatter.getNumFormat(rparam[i].getBundleEnteringQty()));
				lst_AsrsPlanList.setValue(9, WmsFormatter.getNumFormat(rparam[i].getStoragePieceQty()));
				//#CM39860
				// Keep case piece flag+Work No+Last updated date and time. 
				List Hiden = new Vector();
				Hiden.add(rparam[i].getCasePieceFlag());
				Hiden.add(rparam[i].getWorkingNo());
				Hiden.add(DateOperator.changeDateTime(rparam[i].getLastUpdateDate()));

				lst_AsrsPlanList.setValue(0, CollectionUtils.getConnectedString(Hiden));

				//#CM39861
				// add data to tool tip
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_Location, rparam[i].getLocationNo());
				toolTip.add(titleItemName, rparam[i].getItemName());

				//#CM39862
				// set tool tip	
				lst_AsrsPlanList.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM39863
			// set value to Pager
			//#CM39864
			// max. number
			pgr_U.setMax(0);
			//#CM39865
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM39866
			// start position
			pgr_U.setIndex(0);
			//#CM39867
			// max. number
			pgr_D.setMax(0);
			//#CM39868
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM39869
			// start position
			pgr_D.setIndex(0);

			//#CM39870
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM39871
			// hide the header
			lst_AsrsPlanList.setVisible(false);
			//#CM39872
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM39873
	// Event handler methods -----------------------------------------
	//#CM39874
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

	//#CM39875
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

	//#CM39876
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

	//#CM39877
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

	//#CM39878
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

	//#CM39879
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	Pass Storage schedule information to the parents screen, and close the list box. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_AsrsPlanList_Click(ActionEvent e) throws Exception
	{
		//#CM39880
		// set the current row
		lst_AsrsPlanList.setCurrentRow(lst_AsrsPlanList.getActiveRow());
		lst_AsrsPlanList.getValue(1);

		//#CM39881
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM39882
		// case piece flag
		param.setParameter(CASEPIECEFLAG_KEY,  CollectionUtils.getString(0,lst_AsrsPlanList.getValue(0)));
		//#CM39883
		// storage plan date
		param.setParameter(ListAsStoragePlanDateBusiness.PLANDATE_KEY, lst_AsrsPlanList.getValue(2));
		//#CM39884
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, lst_AsrsPlanList.getValue(3));
		//#CM39885
		// packed qty per case
		param.setParameter(ENTERINGQTY_KEY, lst_AsrsPlanList.getValue(5));
		//#CM39886
		// remaining qty per case
		param.setParameter(CASEQTY_KEY, lst_AsrsPlanList.getValue(6));
		//#CM39887
		// packed qty per piece
		param.setParameter(BUNDLEENTERINGQTY_KEY, lst_AsrsPlanList.getValue(8));
		//#CM39888
		// remaining qty per piece
		param.setParameter(PIECEQTY_KEY, lst_AsrsPlanList.getValue(9));
		//#CM39889
		// job no.
		param.setParameter(JOBNO_KEY,  CollectionUtils.getString(1,lst_AsrsPlanList.getValue(0)));
		//#CM39890
		// update date/time
		param.setParameter(LASTUPDATE_KEY,  CollectionUtils.getString(2,lst_AsrsPlanList.getValue(0)));
		//#CM39891
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM39892
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
		//#CM39893
		//save the listbox in session
		SessionAsStoragePlanRet listbox = (SessionAsStoragePlanRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM39894
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
		//#CM39895
		//save the listbox in session
		SessionAsStoragePlanRet listbox = (SessionAsStoragePlanRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM39896
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
		//#CM39897
		//save the listbox in session
		SessionAsStoragePlanRet listbox = (SessionAsStoragePlanRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM39898
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
		//#CM39899
		//save the listbox in session
		SessionAsStoragePlanRet listbox = (SessionAsStoragePlanRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM39900
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
		//#CM39901
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM39902
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM39903
				// close the statement object
				finder.close();
			}
			//#CM39904
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM39905
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM39906
		// return to origin screen
		parentRedirect(null);
	}

	//#CM39907
	/** 
	 * Acquire Consignor description from Consignor code. <BR>
	 * @param pconn database connection
	 * @param pconsignorcode consignor code
	 * @return consignor name
	 * @throws Exception report all the exceptions
	 */
	public String getName(Connection pconn, String pconsignorcode) throws Exception
	{
		String consignorName = null;
		
		WorkingInformationReportFinder wWIFinder = new WorkingInformationReportFinder(pconn);
		WorkingInformationSearchKey wWISearchKey = new WorkingInformationSearchKey();
		
		wWISearchKey.KeyClear();
		//#CM39908
		// Status flag(Storage)
		wWISearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE) ;
		//#CM39909
		// Work Status(Stand by)
		wWISearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		//#CM39910
		// Acquire new Consignor description of the latest registration date. 
		wWISearchKey.setConsignorCode(pconsignorcode);
		
		//#CM39911
		// Descending order at the latest registration date
		wWISearchKey.setRegistDateOrder(1, false);
		
		wWISearchKey.setConsignorNameCollect();

		if (wWIFinder.search(wWISearchKey) > 0)
		{				
			WorkingInformation[] rData = (WorkingInformation[]) wWIFinder.getEntities(1);
			consignorName = rData[0].getConsignorName();
		}
		wWIFinder.close();
		
		return consignorName;
	}

	//#CM39912
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39913
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39914
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39915
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39916
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39917
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_7_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39918
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39920
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39921
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Item_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39922
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsPlanList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsPlanList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsPlanList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM39931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsPlanList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM39932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsPlanList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsPlanList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM39934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM39935
//end of class
