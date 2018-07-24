// $Id: StorageWorkingInquiryBusiness.java,v 1.2 2006/12/07 08:57:10 suresh Exp $

//#CM574028
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageworkinginquiry;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.PulldownHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;
import jp.co.daifuku.wms.storage.schedule.StorageWorkingInquiryParameter;
import jp.co.daifuku.wms.storage.schedule.StorageWorkingInquirySCH;

//#CM574029
/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * The Stock Status inquiry screen class. <BR>
 * The content input from the screen is set in Parameter, and the schedule retrieves data for the display based on the Parameter. <BR>
 * Receive display area Output data from the schedule, and output it to the display area. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed(<CODE>btn_View_Click</CODE> Method)<BR>
 * <BR>
 * <DIR>
 * 		Retrieve stock schedule information contingent on input Item of Input area, and display the Work progress in the list. <BR>
 * 		<BR>
 *    	[Parameter] *Mandatory input<BR>
 * 		<BR>
 * 		<DIR>
 * 			Consignor Code <BR>
 * 			Storage plan date* <BR>
 * 		</DIR>
 * 		<BR>
 * 		[Return Data] <BR>
 * 		<BR>
 * 		<DIR>
 * 			Work qty(Total, Unprocessed, Working, Processed, Progress rate)<BR>
 * 			Item qty(Total, Unprocessed, Working, Processed, Progress rate)<BR>
 * 			Case qty(Total, Unprocessed, Working, Processed, Progress rate)<BR>
 * 			Piece qty(Total, Unprocessed, Working, Processed, Progress rate)<BR>
 * 			Consignor qty(Total, Unprocessed, Working, Processed, Progress rate)<BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:10 $
 * @author  $Author: suresh $
 */
public class StorageWorkingInquiryBusiness extends StorageWorkingInquiry implements WMSConstants
{
	//#CM574030
	// Class fields --------------------------------------------------
	//#CM574031
	/** 
	 * For Storage plan date blank
	 */
	public static final String BLANK_DATE = "        ";

	//#CM574032
	// Class variables -----------------------------------------------

	//#CM574033
	// Class method --------------------------------------------------

	//#CM574034
	// Constructors --------------------------------------------------

	//#CM574035
	// Public methods ------------------------------------------------

	//#CM574036
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize input Item. <BR>
	 * 		2.Initially set the cursor in Pulldown. <BR>
	 * 		3.Set All0 (Set small number of qty for Progress rate upto one digit.) for list display Item and display initial. <BR>
	 * 		<BR>
	 * 		Item Name[Initial value]<BR>
	 * 		<DIR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available]<BR>
	 * 			Storage plan date[The first date of retrieval Storage plan date]<BR>
	 * 			List cell[Set All0(Set small number of qty for Progress rate upto one digit.)]
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{		
			//#CM574037
			// Make the Pulldown unuseable.
			pul_StoragePlanDate.setEnabled(false);
			
			//#CM574038
			// Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			StorageWorkingInquiryParameter initParam = new StorageWorkingInquiryParameter();
			WmsScheduler schedule = new StorageWorkingInquirySCH();
			
			StorageWorkingInquiryParameter param =
				(StorageWorkingInquiryParameter) schedule.initFind(conn, initParam);

			//#CM574039
			// Display initially when Consignor Code is one. 
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}

			//#CM574040
			// Default of Storage plan date
			if (param.getPlanDateP() != null)
			{
				//#CM574041
				// Make the Pulldown useable.
				pul_StoragePlanDate.setEnabled(true);
			
				//#CM574042
				// Acquire how many blanks have entered in Storage plan date. 
				int blankCount = 0;
				for (int i = 0; i < param.getPlanDateP().length; i++)
				{
					if (StringUtil.isBlank(param.getPlanDateP()[i]))
					{
						blankCount++;
					}
				}
				
				String planDate[] = new String[param.getPlanDateP().length - blankCount];
				
				//#CM574043
				// Set the value in Storage plan date. 
				blankCount = 0;				
				for (int i = 0; i < param.getPlanDateP().length; i++)
				{
					if (!StringUtil.isBlank(param.getPlanDateP()[i]))
					{
						planDate[blankCount] =
							WmsFormatter.toDispDate(
								param.getPlanDateP()[i],
								this.getHttpRequest().getLocale());
						blankCount++;
					}
				}
				PulldownHelper.setPullDown(pul_StoragePlanDate, planDate);
				pul_StoragePlanDate.setSelectedIndex(0);
				//#CM574044
				// Set the cursor in Pulldown. 
				setFocus(pul_StoragePlanDate);
			}

			//#CM574045
			// Make the Pulldown of Storage plan date when the data is not (after it updates it at the date) Storage plan
			//#CM574046
			// Disable
			else if (param.getPlanDateP() == null || param.getPlanDateP().length == 0)
			{
				//#CM574047
				// Generate item Pulldown. 
				PullDownItem pItem = new PullDownItem();
				//#CM574048
				// Set the blank in item Pulldown. 
				pItem.setValue(BLANK_DATE);
				//#CM574049
				// Add it to Pulldown. 
				pul_StoragePlanDate.addItem(pItem);
				//#CM574050
				// Display the first item in Pulldown. 
				pul_StoragePlanDate.setSelectedIndex(0);
				//#CM574051
				// Set the cursor in Consignor Code. 
				setFocus(txt_ConsignorCode);

			}

			//#CM574052
			// Default of List cell
			clearListCell();
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM574053
				// Close Connection
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
					
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	
	//#CM574054
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM574055
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM574056
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM574057
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM574058
	/**
	 * When returning from the pop up window, this Method is called. <BR>
	 * Override the <CODE>page_DlgBack</CODE> set in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline:Acquire and set Return Data of the retrieval screen. <BR>
	 * <BR>
	 * <DIR>
	 *      1.Acquire the value returned from the retrieval screen of pop up. <BR>
	 *      2.Set it on the screen when the value is not empty. <BR>
	 *      3.Set the cursor in Consignor Code. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM574059
		// Parameter selected from list box is acquired. 
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);

		//#CM574060
		// Set the value when it is not empty. 
		//#CM574061
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
			//#CM574062
			// Set focus in Consignor Code
			setFocus(txt_ConsignorCode);
		}

	}


	//#CM574063
	// Package methods -----------------------------------------------

	//#CM574064
	// Protected methods ---------------------------------------------
	//#CM574065
	/**
	 * Method to acquire Storage plan date selected from Pulldown. <BR>
	 * <BR>
	 * Outline : Return Storage plan date acquired from Pulldown. <BR>
	 * Return null when not from Pulldown. <BR>
	 * <BR>
	 * @return Storage plan date
	 */
	protected String getSelectedDateString()
	{
		//#CM574066
		// Return null when not from Pulldown. 
		if (!pul_StoragePlanDate.getEnabled())
		{
			return null;
		}
		
		//#CM574067
		// Acquire Storage plan date from Pulldown. 
		String dateText = null;
		dateText = pul_StoragePlanDate.getSelectedItem().getValue();

		String selectedDate = WmsFormatter.toParamDate(dateText, this.getHttpRequest().getLocale());
		
		return selectedDate;
	}
	//#CM574068
	// Private methods -----------------------------------------------

	//#CM574069
	// Event handler methods -----------------------------------------
	//#CM574070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574073
	/**
	 * It is called when the menu button is pressed.<BR>
	 * <BR>
	 * Outline:Change to the menu panel.<BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM574074
		// Change to the menu panel.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM574075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574077
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM574080
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574081
	/** 
	 * It is called when the retrieval of Consignor Code button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Consignor list box by the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Storage plan date <BR>
	 *       Consignor Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM574082
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM574083
		// Storage plan date
		param.setParameter(
			ListStorageConsignorBusiness.STORAGEPLANDATE_KEY, 
			getSelectedDateString());		
		//#CM574084
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM574085
		// Retrieval flag(Plan)
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STORAGE_WORK);
		
		//#CM574086
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do",
			param,
			"/progress.do");
	}

	//#CM574087
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoragePlanDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM574090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStrgStuIqr_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStrgStuIqr_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStrgStuIqr_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM574093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStrgStuIqr_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM574094
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStrgStuIqr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574095
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStrgStuIqr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM574096
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStrgStuIqr_Click(ActionEvent e) throws Exception
	{
	}

	//#CM574097
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574098
	/**
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline : Retrieve stock schedule information contingent on input Item of Input area, and display the Work progress in the list. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Consignor Code. <BR>
	 * 		2.Check Input area input Item.  (Mandatory input, Number of characters, Character attribute) <BR>
	 * 		3.Start Schedule.<BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			[Parameter] *Mandatory input<BR>
	 * 			<DIR>
	 * 				Consignor Code <BR>
	 * 				Storage plan date* <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * 		<BR>
	 * 		4.Display it in the list. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM574099
		// Clear the list cell. 
		clearListCell();

		//#CM574100
		// Set the cursor in Consignor Code. 
		setFocus(txt_ConsignorCode);

		//#CM574101
		// Pattern match character
		txt_ConsignorCode.validate(false);

		Connection conn = null;

		try
		{
			//#CM574102
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM574103
			// Set schedule Parameter. 
			StorageWorkingInquiryParameter param = new StorageWorkingInquiryParameter();
			//#CM574104
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM574105
			// Storage plan date
			param.setPlanDate(
				WmsFormatter.toParamDate(
					pul_StoragePlanDate.getSelectedValue(),
					this.getHttpRequest().getLocale()));

			//#CM574106
			// Start scheduling.
			WmsScheduler schedule = new StorageWorkingInquirySCH();
			StorageWorkingInquiryParameter[] viewParam =
				(StorageWorkingInquiryParameter[]) schedule.query(conn, param);

			//#CM574107
			// End processing when some errors occur or there is no display data. 		
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM574108
			// Set the retrieval result in each cell. 
			dispCell(lst_SStrgStuIqr, viewParam[0]);

			if (!StringUtil.isBlank(schedule.getMessage()))
			{
				//#CM574109
				// Display Message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM574110
				// Close the connection
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}
	
	//#CM574111
	/**
	 * Method which sets the value of Parameter in the list cell. <BR>
	 * <BR>
	 * Outline:Set the value of Parameter in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		List of row number of list cell<BR>
	 * 		<DIR>
	 * 			1 : Total<BR>
	 * 			2 : Unprocessed<BR>
	 * 			3 : Working<BR>
	 * 			4 : Processed<BR>
	 * 			5 : Progress rate<BR>
	 * 		</DIR>
	 * 		<BR>
	 * 		Row number list of list cell<BR>
	 * 		<DIR>
	 * 			1 : Work qty<BR>
	 * 			2 : Case qty<BR>
	 * 			3 : Piece qty<BR>
	 * 			4 : Consignor qty<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param cellName FixedListCell List cell which sets it. 
	 * @param parameter StorageWorkingInquiryParameter Information displayed to list cell. 
	 */
	private void dispCell(FixedListCell cellName, StorageWorkingInquiryParameter parameter)
	{
		StorageWorkingInquiryParameter viewParam = parameter;
		
		//#CM574112
		// Set value in the first line of the list cell. 
		cellName.setCurrentRow(1);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getTotalWorkCount()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartWorkCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowWorkCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishWorkCount()));
		cellName.setValue(5, viewParam.getWorkRate());

		//#CM574113
		// Set value in the second line of the list cell. 
		cellName.setCurrentRow(2);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getTotalCaseCount()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartCaseCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowCaseCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishCaseCount()));
		cellName.setValue(5, viewParam.getCaseProgressiveRate());

		//#CM574114
		// Set value in the third line of the list cell. 
		cellName.setCurrentRow(3);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getTotalPieceCount()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartPieceCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowPieceCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishPieceCount()));
		cellName.setValue(5, viewParam.getPieceProgressiveRate());

		//#CM574115
		// Set value in the fourth line of the list cell. 
		cellName.setCurrentRow(4);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getTotalConsignorCount()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartConsignorCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowConsignorCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishConsignorCount()));
		cellName.setValue(5, viewParam.getConsignorProgressiveRate());
	}


	//#CM574116
	/** 
	 * Method which clears information on the list cell.<BR>
	 * <BR>
	 * Outline : Set the Default value in Item in the list cell. <BR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	public void clearListCell() throws Exception
	{
		//#CM574117
		// Stock Status inquiry (Storage) 
		for (int i = 1; i < 5; i++)
		{
			lst_SStrgStuIqr.setCurrentRow(i);
			for (int j = 1; j < 5; j++)
			{
				lst_SStrgStuIqr.setValue(j, "0");
			}
			//#CM574118
			// Small number of progress rates set (0.0) up to one digit. 
			lst_SStrgStuIqr.setValue(5, WmsFormatter.changeProgressRate(0));
		}
		
	}

}
//#CM574119
//end of class
