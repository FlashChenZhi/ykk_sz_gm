// $Id: ListStorageLocationBusiness.java,v 1.2 2006/12/07 08:57:39 suresh Exp $

//#CM568904
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststoragelocation;

import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanmodify.ListStoragePlanModifyBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageCaseLocationPlanRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageLocationWorkInfoRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStoragePieceLocationPlanRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM568905
/**
 * Designer : M.Fujii <BR>
 * Maker : M.Fujii <BR>
 * <BR>
 * The Location retrieval list box class.  <BR>
 * Retrieve by making the search condition input from the parents screen as a key, and display it on the screen. <BR>
 * <BR>
 * Process it in this class as follows.  <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load(ActionEvent e)</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Retrieve by making the search condition input from the parents screen as a key, and display it on the screen. <BR>
 * </DIR>
 * 2.Button of selected line (<CODE>lst_ListStorageLocation_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Pass Location of the selected line to the parents screen, and close the list box. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:39 $
 * @author  $Author: suresh $
 */
public class ListStorageLocationBusiness extends ListStorageLocation implements WMSConstants
{
	//#CM568906
	// Class fields --------------------------------------------------

	//#CM568907
	// Delivery key
	//#CM568908
	/** 
	 * The key used to hand over Location information. 
	 */
	public static final String LOCATION_KEY = "LOCATION_KEY";

	//#CM568909
	/** 
	 * The key used to hand over Case/Piece Flag. 
	 */
	public static final String CASEPIECE_FLAG_KEY = "CASEPIECE_FLAG_KEY";
	
	//#CM568910
	/** 
	 * The key used to hand over Start Location. 
	 */
	public static final String STARTSTORAGELOCATION_KEY = "STARTSTORAGELOCATION_KEY";

	//#CM568911
	/** 
	 * The key used to hand over End Location. 
	 */
	public static final String ENDSTORAGELOCATION_KEY = "ENDSTORAGELOCATION_KEY";

	//#CM568912
	// Retrieval key
	//#CM568913
	/** 
	 * The key used to hand over Case/Piece Flag for retrieval. 
	 */
	public static final String SEARCH_CASEPIECE_FLAG_KEY = "SEARCH_CASEPIECE_FLAG_KEY";

	//#CM568914
	/** 
	 * The key used to hand over Retrieval flag. 
	 */
	public static final String SEARCH_STORAGE_LOCATION_KEY = "SEARCH_STORAGE_LOCATION_KEY";

	//#CM568915
	/** 
	 * Work The key used to hand over Status. 
	 */
	public static final String SEARCH_WORKSTATUSLOCATION_KEY = "SEARCH_WORKSTATUSLOCATION_KEY";

	//#CM568916
	// Class variables -----------------------------------------------

	//#CM568917
	// Class method --------------------------------------------------

	//#CM568918
	// Constructors --------------------------------------------------

	//#CM568919
	// Public methods ------------------------------------------------

	//#CM568920
	/**
	 * Initialize the screen. <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Location <BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM568921
		// Set the Screen name
		//#CM568922
		// Location retrieval
		lbl_ListName.setText(DisplayText.getText("TLE-W0064"));

		//#CM568923
		// Parameter is acquired. 
		//#CM568924
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM568925
		// Storage plan date
		String storageplandate =
			request.getParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY);
		//#CM568926
		// Item Code
		String itemcode = request.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM568927
		// Case/Piece flag
		String casepieceflag = request.getParameter(CASEPIECE_FLAG_KEY);
		//#CM568928
		// Storage Location
		String location = request.getParameter(LOCATION_KEY);
		//#CM568929
		// CaseStorage Location
		String casestoragelocation = request.getParameter(ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY);
		//#CM568930
		// PieceStorage Location
		String piecestoragelocation = request.getParameter(ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY);
		//#CM568931
		// Case/Piece flag for retrieval
		String searchcasepieceflag = request.getParameter(SEARCH_CASEPIECE_FLAG_KEY);
		//#CM568932
		// Storage LocationRetrieval flag
		String searchstoragelocation = request.getParameter(SEARCH_STORAGE_LOCATION_KEY);
		//#CM568933
		// Work Status
		String[] search = request.getParameterValues(SEARCH_WORKSTATUSLOCATION_KEY);

		viewState.setString(SEARCH_CASEPIECE_FLAG_KEY, searchcasepieceflag);
		viewState.setString(SEARCH_STORAGE_LOCATION_KEY, searchstoragelocation);

		//#CM568934
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM568935
			// Connection close
			sRet.closeConnection();
			//#CM568936
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM568937
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM568938
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM568939
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM568940
		// Storage plan date
		param.setStoragePlanDate(storageplandate);
		//#CM568941
		// Item Code
		param.setItemCode(itemcode);
		//#CM568942
		// Location
		param.setStorageLocation(location);
		//#CM568943
		// CaseStorage Location
		param.setCaseLocation(casestoragelocation);
		//#CM568944
		// PieceStorage Location
		param.setPieceLocation(piecestoragelocation);
		//#CM568945
		// Case/Piece flag
		param.setCasePieceflg(casepieceflag);
		//#CM568946
		// Work Status array
		param.setSearchStatus(search);

		//#CM568947
		// Divide Session called on the condition of retrieved Location. 
		//#CM568948
		// While retrieving from stock PlanAdd -> Work information retrieval
		if (searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_WORK)
			&& searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM568949
			// SessionStorageLocationWorkInfoRet  instance generation
			SessionStorageLocationWorkInfoRet listbox =
				new SessionStorageLocationWorkInfoRet(conn, param);
			//#CM568950
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkList(listbox, "first");
		}
		//#CM568951
		// Stock Plan correction , While retrieving from deletion -> CaseStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568952
			// SessionStorageCaseLocationPlanRet  instance generation
			SessionStorageCaseLocationPlanRet listbox =
				new SessionStorageCaseLocationPlanRet(conn, param);
			//#CM568953
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setCaseList(listbox, "first");
		}
		//#CM568954
		// Stock Plan correction , While retrieving from deletion -> PieceStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568955
			// SessionStoragePieceLocationPlanRet  instance generation
			SessionStoragePieceLocationPlanRet listbox =
				new SessionStoragePieceLocationPlanRet(conn, param);
			//#CM568956
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setPieceList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0024");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
	}

	//#CM568957
	// Package methods -----------------------------------------------

	//#CM568958
	// Protected methods ---------------------------------------------

	//#CM568959
	// Private methods -----------------------------------------------

	//#CM568960
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell  from Work information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageLocationWorkInfoRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setWorkList(SessionStorageLocationWorkInfoRet listbox, String actionName)
	{
		//#CM568961
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568962
		// The retrieval result is acquired. 
		WorkingInformation[] storageparam = listbox.getEntities();
		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM568963
			// Value set to Pager
			//#CM568964
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568965
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568966
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568967
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568968
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568969
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568970
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568971
			// Delete all lines
			lst_ListStorageLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568972
				// The final line is acquired
				int count = lst_ListStorageLocation.getMaxRows();
				//#CM568973
				// Add row
				lst_ListStorageLocation.addRow();

				//#CM568974
				// It moves to the final line. 
				lst_ListStorageLocation.setCurrentRow(count);
				lst_ListStorageLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStorageLocation.setValue(2, storageparam[i].getLocationNo());
			}
		}
		else
		{
			//#CM568975
			// Value set to Pager
			//#CM568976
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568977
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568978
			// Starting position
			pgr_U.setIndex(0);
			//#CM568979
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568980
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568981
			// Starting position
			pgr_D.setIndex(0);

			//#CM568982
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568983
			// Conceal the header
			lst_ListStorageLocation.setVisible(false);
			//#CM568984
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568985
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell  from stock Plan information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageCaseLocationPlanRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setCaseList(SessionStorageCaseLocationPlanRet listbox, String actionName)
	{
		//#CM568986
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568987
		// The retrieval result is acquired. 
		StoragePlan[] storageparam = listbox.getEntities();
		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM568988
			// Value set to Pager
			//#CM568989
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568990
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568991
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568992
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568993
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568994
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568995
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568996
			// Delete all lines
			lst_ListStorageLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568997
				// The final line is acquired
				int count = lst_ListStorageLocation.getMaxRows();
				//#CM568998
				// Add row
				lst_ListStorageLocation.addRow();

				//#CM568999
				// It moves to the final line. 
				lst_ListStorageLocation.setCurrentRow(count);
				lst_ListStorageLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStorageLocation.setValue(2, storageparam[i].getCaseLocation());
			}
		}
		else
		{
			//#CM569000
			// Value set to Pager
			//#CM569001
			// The maximum qty
			pgr_U.setMax(0);
			//#CM569002
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM569003
			// Starting position
			pgr_U.setIndex(0);
			//#CM569004
			// The maximum qty
			pgr_D.setMax(0);
			//#CM569005
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM569006
			// Starting position
			pgr_D.setIndex(0);

			//#CM569007
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM569008
			// Conceal the header
			lst_ListStorageLocation.setVisible(false);
			//#CM569009
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM569010
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell  from stock Plan information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStoragePieceLocationPlanRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setPieceList(SessionStoragePieceLocationPlanRet listbox, String actionName)
	{
		//#CM569011
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM569012
		// The retrieval result is acquired. 
		StoragePlan[] storageparam = listbox.getEntities();
		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM569013
			// Value set to Pager
			//#CM569014
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM569015
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM569016
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM569017
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM569018
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM569019
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM569020
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM569021
			// Delete all lines
			lst_ListStorageLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM569022
				// The final line is acquired
				int count = lst_ListStorageLocation.getMaxRows();
				//#CM569023
				// Add row
				lst_ListStorageLocation.addRow();

				//#CM569024
				// It moves to the final line. 
				lst_ListStorageLocation.setCurrentRow(count);
				lst_ListStorageLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStorageLocation.setValue(2, storageparam[i].getPieceLocation());
			}
		}
		else
		{
			//#CM569025
			// Value set to Pager
			//#CM569026
			// The maximum qty
			pgr_U.setMax(0);
			//#CM569027
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM569028
			// Starting position
			pgr_U.setIndex(0);
			//#CM569029
			// The maximum qty
			pgr_D.setMax(0);
			//#CM569030
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM569031
			// Starting position
			pgr_D.setIndex(0);

			//#CM569032
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM569033
			// Conceal the header
			lst_ListStorageLocation.setVisible(false);
			//#CM569034
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM569035
	// Event handler methods -----------------------------------------
	//#CM569036
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569037
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569038
	/** 
	 * Do the Processing when the Close button is pressed. <BR>
	 *  <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM569039
	/** 
	 * Do the Processing when the [>] button is pressed. <BR>
	 * <BR>
	 * Display the subsequent page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM569040
	/** 
	 * Do the Processing when the [<] button is pressed. <BR>
	 * <BR>
	 * Display the previous page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM569041
	/** 
	 * Do the Processing when the [>>] button is pressed. <BR>
	 * <BR>
	 * Display the last page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM569042
	/** 
	 * Do the Processing when the [<<] button is pressed. <BR>
	 * <BR>
	 * Display the first page. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}
	//#CM569043
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569044
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569045
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569046
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM569047
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageLocation_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM569048
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569049
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageLocation_Change(ActionEvent e) throws Exception
	{
	}

	//#CM569050
	/** 
	 * Process when Selection button of List cell is pressed.  <BR>
	 * <BR>
	 *	Pass Location to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_ListStorageLocation_Click(ActionEvent e) throws Exception
	{
		//#CM569051
		// Retrieval flag is acquired. 
		String searchcasepieceflag = viewState.getString(SEARCH_CASEPIECE_FLAG_KEY);
		String searchstoragelocation = viewState.getString(SEARCH_STORAGE_LOCATION_KEY);

		//#CM569052
		// A present line is set. 
		lst_ListStorageLocation.setCurrentRow(lst_ListStorageLocation.getActiveRow());
		lst_ListStorageLocation.getValue(1);

		//#CM569053
		// Set Parameter to be returned to parents screen
		ForwardParameters param = new ForwardParameters();
		if (searchcasepieceflag == null)
		{
			//#CM569054
			// Location is not retrieved. 
		}
		else if (searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM569055
			// CaseStorage Location
			param.setParameter(ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY, lst_ListStorageLocation.getValue(2));
		}
		else if (searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM569056
			// PieceStorage Location
			param.setParameter(ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY, lst_ListStorageLocation.getValue(2));
		}

		if (searchstoragelocation == null)
		{
			//#CM569057
			// Location is not retrieved. 
		}
		else if (searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM569058
			// Location
			param.setParameter(LOCATION_KEY, lst_ListStorageLocation.getValue(2));
		}

		//#CM569059
		// Changes to the parents screen. 
		parentRedirect(param);
	}

	//#CM569060
	/** 
	 * Do the Processing when the [>] button is pressed. <BR>
	 * <BR>
	 * Display the subsequent page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM569061
		// Retrieval flag is acquired. 
		String searchcasepieceflag = viewState.getString(SEARCH_CASEPIECE_FLAG_KEY);
		String searchstoragelocation = viewState.getString(SEARCH_STORAGE_LOCATION_KEY);

		//#CM569062
		// Divide Session called on the condition of retrieved Location. 
		//#CM569063
		// While retrieving from stock PlanAdd -> Work information retrieval	
		if (searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_WORK)
			&& searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM569064
			// Listbox is maintained in Session
			SessionStorageLocationWorkInfoRet listbox =
				(SessionStorageLocationWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "next");
		}
		//#CM569065
		// Stock Plan correction , While retrieving from deletion -> CaseStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569066
			// Listbox is maintained in Session
			SessionStorageCaseLocationPlanRet listbox =
				(SessionStorageCaseLocationPlanRet) this.getSession().getAttribute("LISTBOX");
			setCaseList(listbox, "next");
		}
		//#CM569067
		// Stock Plan correction , While retrieving from deletion -> PieceStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569068
			// Listbox is maintained in Session
			SessionStoragePieceLocationPlanRet listbox =
				(SessionStoragePieceLocationPlanRet) this.getSession().getAttribute("LISTBOX");
			setPieceList(listbox, "next");
		}
	}

	//#CM569069
	/** 
	 * Do the Processing when the [<] button is pressed. <BR>
	 * <BR>
	 * Display the previous page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM569070
		// Retrieval flag is acquired. 
		String searchcasepieceflag = viewState.getString(SEARCH_CASEPIECE_FLAG_KEY);
		String searchstoragelocation = viewState.getString(SEARCH_STORAGE_LOCATION_KEY);

		//#CM569071
		// Divide Session called on the condition of retrieved Location. 
		//#CM569072
		// While retrieving from stock PlanAdd -> Work information retrieval	
		if (searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_WORK)
			&& searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM569073
			// Listbox is maintained in Session
			SessionStorageLocationWorkInfoRet listbox =
				(SessionStorageLocationWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "previous");
		}
		//#CM569074
		// Stock Plan correction , While retrieving from deletion -> CaseStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569075
			// Listbox is maintained in Session
			SessionStorageCaseLocationPlanRet listbox =
				(SessionStorageCaseLocationPlanRet) this.getSession().getAttribute("LISTBOX");
			setCaseList(listbox, "previous");
		}
		//#CM569076
		// Stock Plan correction , While retrieving from deletion -> PieceStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569077
			// Listbox is maintained in Session
			SessionStoragePieceLocationPlanRet listbox =
				(SessionStoragePieceLocationPlanRet) this.getSession().getAttribute("LISTBOX");
			setPieceList(listbox, "previous");
		}
	}

	//#CM569078
	/** 
	 * Do the Processing when the [>>] button is pressed. <BR>
	 * <BR>
	 * Display the last page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM569079
		// Retrieval flag is acquired. 
		String searchcasepieceflag = viewState.getString(SEARCH_CASEPIECE_FLAG_KEY);
		String searchstoragelocation = viewState.getString(SEARCH_STORAGE_LOCATION_KEY);

		//#CM569080
		// Divide Session called on the condition of retrieved Location. 
		//#CM569081
		// While retrieving from stock PlanAdd -> Work information retrieval	
		if (searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_WORK)
			&& searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM569082
			// Listbox is maintained in Session
			SessionStorageLocationWorkInfoRet listbox =
				(SessionStorageLocationWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "last");
		}
		//#CM569083
		// Stock Plan correction , While retrieving from deletion -> CaseStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569084
			// Listbox is maintained in Session
			SessionStorageCaseLocationPlanRet listbox =
				(SessionStorageCaseLocationPlanRet) this.getSession().getAttribute("LISTBOX");
			setCaseList(listbox, "last");
		}
		//#CM569085
		// Stock Plan correction , While retrieving from deletion -> PieceStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569086
			// Listbox is maintained in Session
			SessionStoragePieceLocationPlanRet listbox =
				(SessionStoragePieceLocationPlanRet) this.getSession().getAttribute("LISTBOX");
			setPieceList(listbox, "last");
		}
	}

	//#CM569087
	/** 
	 * Do the Processing when the [<<] button is pressed. <BR>
	 * <BR>
	 * Display the first page. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM569088
		// Retrieval flag is acquired. 
		String searchcasepieceflag = viewState.getString(SEARCH_CASEPIECE_FLAG_KEY);
		String searchstoragelocation = viewState.getString(SEARCH_STORAGE_LOCATION_KEY);

		//#CM569089
		// Divide Session called on the condition of retrieved Location. 
		//#CM569090
		// While retrieving from stock PlanAdd -> Work information retrieval	
		if (searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_WORK)
			&& searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM569091
			// Listbox is maintained in Session
			SessionStorageLocationWorkInfoRet listbox =
				(SessionStorageLocationWorkInfoRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "first");
		}
		//#CM569092
		// Stock Plan correction , While retrieving from deletion -> CaseStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569093
			// Listbox is maintained in Session
			SessionStorageCaseLocationPlanRet listbox =
				(SessionStorageCaseLocationPlanRet) this.getSession().getAttribute("LISTBOX");
			setCaseList(listbox, "first");
		}
		//#CM569094
		// Stock Plan correction , While retrieving from deletion -> PieceStorage Location retrieval
		else if (
			searchcasepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE)
				&& searchstoragelocation.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569095
			// Listbox is maintained in Session
			SessionStoragePieceLocationPlanRet listbox =
				(SessionStoragePieceLocationPlanRet) this.getSession().getAttribute("LISTBOX");
			setPieceList(listbox, "first");
		}
	}

	//#CM569096
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569097
	/** 
	 * Do the Processing when the Close button is pressed. <BR>
	 *  <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM569098
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM569099
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM569100
				// Close the statement.
				finder.close();
			}
			//#CM569101
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM569102
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM569103
		// Return to the parents screen
		parentRedirect(null);
	}
}
//#CM569104
//end of class
