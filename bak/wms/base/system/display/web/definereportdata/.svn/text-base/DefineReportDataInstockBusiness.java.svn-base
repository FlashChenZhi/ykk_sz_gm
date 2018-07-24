// $Id: DefineReportDataInstockBusiness.java,v 1.2 2006/11/13 08:18:48 suresh Exp $

//#CM688744
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.definereportdata;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.NumberTextBox;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.system.schedule.DefineDataParameter;
import jp.co.daifuku.wms.base.system.schedule.DefineReportDataSCH;

//#CM688745
/**
 * Designer :  S.Ishigane <BR>
 * Maker :     T.Uehata <BR>
 * <BR>
 * Allow this class to provide a screen to set a field item for reporting data (to set the Receiving Result field).<BR>
 * Set the searched value via the schedule for each field item.
 * Set the contents entered via screen for the parameter. Allow the schedule to set the Report Data field (set the Receiving Result field) based on the parameter.<BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Process by clicking on "Add" button (Dialog for confirming to add:OK)(btn_Submit_Click(ActionEvent e)method)<BR>
 * <BR>
 * <DIR>
 *   Display a dialog box to allow to confirm to add or not. If confirmed, add it using the information in the Preset area. <BR>
 * <BR>
 *   [Parameter]+ Require to input if the parameter is ticked to Enabled <BR>
 *   Length, Max Length, and Position of each field item<BR>
 * <BR>
 * <DIR>
 *   Planned Receiving Date +<BR>
 *   Order Date+<BR>
 * 	 Consignor Code +<BR>
 * 	 Consignor Name+<BR>
 * 	 Supplier Code+<BR>
 * 	 Supplier Name+<BR>
 * 	 Ticket No.+<BR>
 * 	 Ticket Line No.+<BR>
 * 	 Item Code +<BR>
 *   Bundle ITF+<BR>
 *   Case ITF+<BR>
 *   Packed qty per bundle+<BR>
 *   Packed Qty per Case+<BR>
 *   Item Name+<BR>
 *   Receiving Plan Qty (Total Bulk Qty) +<BR>
 *   TC/DC division+<BR>
 *   Customer Code+<BR>
 *   Customer Name+<BR>
 *   Receiving Qty (Total Bulk Qty) +<BR>
 *   Receiving Result Date +<BR>
 * 	 Result division+<BR>
 * 	 Expiry Date +<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:48 $
 * @author  $Author: suresh $
 */
public class DefineReportDataInstockBusiness extends DefineReportDataInstock implements WMSConstants
{
	private static final String DO_DEFINEREPORT = "/system/definereportdata/DefineReportData.do";

	//#CM688746
	// Class fields --------------------------------------------------

	//#CM688747
	// Class variables -----------------------------------------------

	//#CM688748
	// Class method --------------------------------------------------

	//#CM688749
	// Constructors --------------------------------------------------

	//#CM688750
	// Public methods ------------------------------------------------
	//#CM688751
	/**
	 * Invoke this before invoking each control event.<BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog.<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM688752
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM688753
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM688754
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM688755
			//Set the path to the help file.
			//#CM688756
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM688757
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	//#CM688758
	/**
	 * Show the Initial Display.<BR>
	 * <BR>
	 * Display a for setting the field items of the functions selected via the previous screen. <BR>
	 * <BR>
	 * <DIR>
	 *   Enabled/Disabled: Selected<BR> 
	 *   Length [read-only]<BR> 
	 *   Position<BR> 
	 *   <BR>
	 *   <DIR>
	 *    Obtain Enabled/Disabled field item, Length, and Position from EnvironmentInformation and display them. <BR>
	 *   </DIR>
	 *   <BR>
	 *   Set the cursor to Length of a field item. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM688759
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));
		
		//#CM688760
		// Check that the length is read-only. Length.
		txt_InstkPlanDateLen.setReadOnly(true);
		txt_OrderingDateLen.setReadOnly(true);
		txt_CnsgnrCdLen.setReadOnly(true);
		txt_CnsgnrNmLen.setReadOnly(true);
		txt_SupplierCdLen.setReadOnly(true);
		txt_SupplierNmLen.setReadOnly(true);
		txt_TicketNoLen.setReadOnly(true);
		txt_TicketLineNoLen.setReadOnly(true);
		txt_ItemCdLen.setReadOnly(true);
		txt_BundleItfLen.setReadOnly(true);
		txt_CaseItfLen.setReadOnly(true);
		txt_BundleEtrLen.setReadOnly(true);
		txt_CaseEtrLen.setReadOnly(true);
		txt_ItemNmLen.setReadOnly(true);
		txt_InstkPlanQtyPtlLen.setReadOnly(true);
		txt_TCDCfFlgLen.setReadOnly(true);
		txt_CustCdLen.setReadOnly(true);
		txt_CustNmLen.setReadOnly(true);
		txt_InstkQtyPtlLen.setReadOnly(true);
		txt_RsltFlgLen.setReadOnly(true);
		txt_UseByDateLen.setReadOnly(true);
		
		setInistView();
	}

	//#CM688761
	// Package methods -----------------------------------------------

	//#CM688762
	// Protected methods ---------------------------------------------

	//#CM688763
	// Private methods -----------------------------------------------

	//#CM688764
	/** 
	 * Allow this method to set the initial values for screen.<BR>
	 * <BR>
	 * Summary: Sets up the settings of each field item in the screen from property file.<BR>
	 * 
	 * @throws Exception Report all exceptions.
	 */
	private void setInistView() throws Exception
	{
		//#CM688765
		// Set the initial value of the focus to Planned Receiving Date Enabled.
		setFocus(chk_InstkPlanDate);

		Connection conn = null;
		try
		{
			//#CM688766
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(
				DefineDataParameter.SELECTDEFINEREPORTDATA_INSTOCK);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM688767
				// Planned Receiving Date: Enabled
				chk_InstkPlanDate.setChecked(param[0].getValid_PlanDate());
				//#CM688768
				// Planned Receiving Date: Length
				txt_InstkPlanDateLen.setText(param[0].getFigure_PlanDate());
				//#CM688769
				// Planned Receiving Date: Max Length
				lbl_MaxLenInstkPlanDate.setText(param[0].getMaxFigure_PlanDate());
				//#CM688770
				// Planned Receiving Date: Position
				txt_InstkPlanDatePst.setText(param[0].getPosition_PlanDate());
				
				//#CM688771
				// Order Date: Enabled
				chk_OrderingDate.setChecked(param[0].getValid_OrderingDate());
				//#CM688772
				// Order Date: Length
				txt_OrderingDateLen.setText(param[0].getFigure_OrderingDate());
				//#CM688773
				// Order Date: Max Length
				lbl_MaxLenOrderingDate.setText(param[0].getMaxFigure_OrderingDate());
				//#CM688774
				// Order Date: Position
				txt_OrderingDatePst.setText(param[0].getPosition_OrderingDate());
				
				//#CM688775
				// Consignor Code: Enabled
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM688776
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM688777
				// Consignor Code: Max Length
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM688778
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM688779
				// Consignor Name: Enabled
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM688780
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM688781
				// Consignor Name: Max Length
				lbl_MaxLenCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM688782
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM688783
				// Supplier Code: Enabled
				chk_SupplierCd.setChecked(param[0].getValid_SupplierCode());
				//#CM688784
				// Supplier Code: Length
				txt_SupplierCdLen.setText(param[0].getFigure_SupplierCode());
				//#CM688785
				// Supplier Code: Max Length
				lbl_MaxLenSupplierCd.setText(param[0].getMaxFigure_SupplierCode());
				//#CM688786
				// Supplier Code: Position
				txt_SupplierCdPst.setText(param[0].getPosition_SupplierCode());
				
				//#CM688787
				// Supplier Name: Enabled
				chk_SupplierNm.setChecked(param[0].getValid_SupplierName());
				//#CM688788
				// Supplier Name: Length
				txt_SupplierNmLen.setText(param[0].getFigure_SupplierName());
				//#CM688789
				// Supplier Name: Max Length
				lbl_MaxLenSupplierNm.setText(param[0].getMaxFigure_SupplierName());
				//#CM688790
				// Supplier Name: Position
				txt_SupplierNmPst.setText(param[0].getPosition_SupplierName());
				
				//#CM688791
				// Ticket No.: Enabled
				chk_TicketNo.setChecked(param[0].getValid_InstockTicketNo());
				//#CM688792
				// Ticket No.: Length
				txt_TicketNoLen.setText(param[0].getFigure_InstockTicketNo());
				//#CM688793
				// Ticket No.: Max Length
				lbl_MaxLenTicketNo.setText(param[0].getMaxFigure_InstockTicketNo());
				//#CM688794
				// Ticket No.: Position
				txt_TicketNoPst.setText(param[0].getPosition_InstockTicketNo());

				//#CM688795
				// Ticket Line No.: Enabled
				chk_TicketLineNo.setChecked(param[0].getValid_InstockLineNo());
				//#CM688796
				// Ticket Line No.: Length
				txt_TicketLineNoLen.setText(param[0].getFigure_InstockLineNo());
				//#CM688797
				// Ticket Line No.: Max Length
				lbl_MaxLenTicketLineNo.setText(param[0].getMaxFigure_InstockLineNo());
				//#CM688798
				// Ticket Line No.: Position
				txt_TicketLineNoPst.setText(param[0].getPosition_InstockLineNo());
				
				//#CM688799
				// Item Code: Enabled
				chk_ItemCd.setChecked(param[0].getValid_ItemCode());
				//#CM688800
				// Item Code: Length
				txt_ItemCdLen.setText(param[0].getFigure_ItemCode());
				//#CM688801
				// Item Code: Max Length
				lbl_MaxLenItemCd.setText(param[0].getMaxFigure_ItemCode());
				//#CM688802
				// Item Code: Position
				txt_ItemCdPst.setText(param[0].getPosition_ItemCode());
				
				//#CM688803
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM688804
				// Bundle ITF: Length
				txt_BundleItfLen.setText(param[0].getFigure_BundleItf());
				//#CM688805
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM688806
				// Bundle ITF: Position
				txt_BundleItfPst.setText(param[0].getPosition_BundleItf());
				
				//#CM688807
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM688808
				// Case ITF: Length
				txt_CaseItfLen.setText(param[0].getFigure_Itf());
				//#CM688809
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM688810
				// Case ITF: Position
				txt_CaseItfPst.setText(param[0].getPosition_Itf());
				
				//#CM688811
				// Packed qty per bundle: Enabled
				chk_BundleEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM688812
				// Packed qty per bundle: Length
				txt_BundleEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM688813
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM688814
				// Packed qty per bundle: Position
				txt_BundleEtrPst.setText(param[0].getPosition_BundleEnteringQty());
				
				//#CM688815
				// Packed Qty per Case: Enabled
				chk_CaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM688816
				// Packed Qty per Case: Length
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM688817
				// Packed Qty per Case: Max Length
				lbl_MaxLenCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM688818
				// Packed Qty per Case: Position
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());
				
				//#CM688819
				// Item Name: Enabled
				chk_ItemNm.setChecked(param[0].getValid_ItemName());
				//#CM688820
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM688821
				// Item Name: Max Length
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM688822
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				
				//#CM688823
				// Receiving Plan Qty (Total Bulk Qty): Enabled
				chk_InstkPlanQtyPtl.setChecked(param[0].getValid_PlanQty());
				//#CM688824
				// Receiving Plan Qty (Total Bulk Qty): Length
				txt_InstkPlanQtyPtlLen.setText(param[0].getFigure_PlanQty());
				//#CM688825
				// Receiving Plan Qty (Total Bulk Qty): Max Length
				lbl_MaxLenInstkPlanQtyPtl.setText(param[0].getMaxFigure_PlanQty());
				//#CM688826
				// Receiving Plan Qty (Total Bulk Qty): Position
				txt_InstkPlanQtyPtlPst.setText(param[0].getPosition_PlanQty());
				
				//#CM688827
				// TC/DC division: Enabled
				chk_TCDCfFlg.setChecked(param[0].getValid_TcDcFlag());
				//#CM688828
				// TC/DC division: Length
				txt_TCDCfFlgLen.setText(param[0].getFigure_TcDcFlag());
				//#CM688829
				// TC/DC division: Max Length
				lbl_MaxLenTCDCfFlg.setText(param[0].getMaxFigure_TcDcFlag());
				//#CM688830
				// TC/DC division: Position
				txt_TCDCFlgPst.setText(param[0].getPosition_TcDcFlag());
				
				//#CM688831
				// Customer Code: Enabled
				chk_CustCd.setChecked(param[0].getValid_CustomerCode());
				//#CM688832
				// Customer Code: Length
				txt_CustCdLen.setText(param[0].getFigure_CustomerCode());
				//#CM688833
				// Customer Code: Max Length
				lbl_MaxLenCustCd.setText(param[0].getMaxFigure_CustomerCode());
				//#CM688834
				// Customer Code: Position
				txt_CustCdPst.setText(param[0].getPosition_CustomerCode());
				
				//#CM688835
				// Customer Name: Enabled
				chk_CustNm.setChecked(param[0].getValid_CustomerName());
				//#CM688836
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM688837
				// Customer Name: Max Length
				lbl_MaxLenCustNm.setText(param[0].getMaxFigure_CustomerName());
				//#CM688838
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());
				
				//#CM688839
				// Receiving Qty (Total Bulk Qty): Enabled
				chk_InstkQtyPtl.setChecked(param[0].getValid_PieceResultQty());
				//#CM688840
				// Receiving Qty (Total Bulk Qty): Length
				txt_InstkQtyPtlLen.setText(param[0].getFigure_PieceResultQty());
				//#CM688841
				// Receiving Qty (Total Bulk Qty): Max Length
				lbl_MaxLenInstkQtyPtl.setText(param[0].getMaxFigure_PieceResultQty());
				//#CM688842
				// Receiving Qty (Total Bulk Qty): Position
				txt_InstkQtyPtlPst.setText(param[0].getPosition_PieceResultQty());
				
				//#CM688843
				// Receiving Result Date: Enabled
				chk_InstkRsltDate.setChecked(param[0].getValid_WorkDate());
				//#CM688844
				// Receiving Result Date: Length
				txt_InstkRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM688845
				// Receiving Result Date: Max Length
				lbl_MaxLenInstkRsltDate.setText(param[0].getMaxFigure_WorkDate());
				//#CM688846
				// Receiving Result Date: Position
				txt_InstkRsltDatePst.setText(param[0].getPosition_WorkDate());
				
				//#CM688847
				// Result division: Enabled
				chk_RsltFlg.setChecked(param[0].getValid_ResultFlag());
				//#CM688848
				// Result division: Length
				txt_RsltFlgLen.setText(param[0].getFigure_ResultFlag());
				//#CM688849
				// Result division: Max Length
				lbl_MaxLenRsltFlg.setText(param[0].getMaxFigure_ResultFlag());
				//#CM688850
				// Result division: Position
				txt_RsltFlgPst.setText(param[0].getPosition_ResultFlag());
				
				//#CM688851
				// Expiry Date: Enabled
				chk_UseByDate.setChecked(param[0].getValid_UseByDate());
				//#CM688852
				// Expiry Date: Length
				txt_UseByDateLen.setText(param[0].getFigure_UseByDate());
				//#CM688853
				// Expiry Date: Max Length
				lbl_MaxLenUseByDate.setText(param[0].getMaxFigure_UseByDate());
				//#CM688854
				// Expiry Date: Position
				txt_UseByDatePst.setText(param[0].getPosition_UseByDate());
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
				//#CM688855
				// Close the connection.
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
	


	//#CM688856
	/** 
	 * Allow this method to check wheter the position is duplicated or not. <BR>
	 * <BR>
	 * Summary: Checks for duplicate position. <BR>
	 * 
	 * @param position Position to check for double occupation.
	 * @return Return false if two or more data occupies a single position.
	 * @throws Exception Report all exceptions.
	 */
	private boolean positionCheck(NumberTextBox position) throws Exception
	{
		int checkCount = 0;

		//#CM688857
		// Planned Receiving Date: Position
		if (position.getText().equals(txt_InstkPlanDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688858
		// Order Date: Position
		if (position.getText().equals(txt_OrderingDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688859
		// Consignor Code: Position
		if (position.getText().equals(txt_CnsgnrCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688860
		// Consignor Name: Position
		if (position.getText().equals(txt_CnsgnrNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688861
		// Customer Code: Position
		if (position.getText().equals(txt_CustCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688862
		// Customer Name: Position
		if (position.getText().equals(txt_CustNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688863
		// Ticket No.: Position
		if (position.getText().equals(txt_TicketNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688864
		// Ticket Line No.: Position
		if (position.getText().equals(txt_TicketLineNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688865
		// Item Code: Position
		if (position.getText().equals(txt_ItemCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688866
		// Bundle ITF: Position
		if (position.getText().equals(txt_BundleItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688867
		// Case ITF: Position
		if (position.getText().equals(txt_CaseItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688868
		// Packed qty per bundle: Position
		if (position.getText().equals(txt_BundleEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688869
		// Packed Qty per Case: Position
		if (position.getText().equals(txt_CaseEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688870
		// Item Name: Position
		if (position.getText().equals(txt_ItemNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688871
		// Receiving Plan Qty (Total Bulk Qty): Position
		if (position.getText().equals(txt_InstkPlanQtyPtlPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688872
		// TC/DC division: Position
		if (position.getText().equals(txt_TCDCFlgPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688873
		// Supplier Code: Position
		if (position.getText().equals(txt_SupplierCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688874
		// Supplier Name: Position
		if (position.getText().equals(txt_SupplierNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688875
		// Receiving Qty (Total Bulk Qty): Position
		if (position.getText().equals(txt_InstkQtyPtlPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688876
		// Receiving Result Date: Position
		if (position.getText().equals(txt_InstkRsltDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688877
		// Result division: Position
		if (position.getText().equals(txt_RsltFlgPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM688878
		// Expiry Date: Position
		if (position.getText().equals(txt_UseByDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}

		return true;
	}

	//#CM688879
	/** 
	 * Allow this method to check wheter the position is duplicated or not in a sequential order.<BR>
	 * <BR>
	 * @return Return false if two or more data occupies a single position.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkRepeat() throws Exception
	{

		boolean repeatChkFlg = true;
		
		if (repeatChkFlg && chk_InstkPlanDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_InstkPlanDatePst);
		}

		if (repeatChkFlg && chk_OrderingDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_OrderingDatePst);
		}

		if (repeatChkFlg && chk_CnsgnrCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrCdPst);
		}

		if (repeatChkFlg && chk_CnsgnrNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrNmPst);
		}
		
		if (repeatChkFlg && chk_SupplierCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_SupplierCdPst);
		}

		if (repeatChkFlg && chk_SupplierNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_SupplierNmPst);
		}

		if (repeatChkFlg && chk_TicketNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_TicketNoPst);
		}

		if (repeatChkFlg && chk_TicketLineNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_TicketLineNoPst);
		}

		if (repeatChkFlg && chk_ItemCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ItemCdPst);
		}

		if (repeatChkFlg && chk_BundleItf.getChecked())
		{
			repeatChkFlg = positionCheck(txt_BundleItfPst);
		}

		if (repeatChkFlg && chk_CaseItf.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CaseItfPst);
		}

		if (repeatChkFlg && chk_BundleEtr.getChecked())
		{
			repeatChkFlg = positionCheck(txt_BundleEtrPst);
		}

		if (repeatChkFlg && chk_ItemNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ItemNmPst);
		}

		if (repeatChkFlg && chk_InstkPlanQtyPtl.getChecked())
		{
			repeatChkFlg = positionCheck(txt_InstkPlanQtyPtlPst);
		}

		if (repeatChkFlg && chk_TCDCfFlg.getChecked())
		{
			repeatChkFlg = positionCheck(txt_TCDCFlgPst);
		}

		if (repeatChkFlg && chk_CustCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CustCdPst);
		}

		if (repeatChkFlg && chk_CustNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CustNmPst);
		}

		if (repeatChkFlg && chk_InstkQtyPtl.getChecked())
		{
			repeatChkFlg = positionCheck(txt_InstkQtyPtlPst);
		}

		if (repeatChkFlg && chk_InstkRsltDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_InstkRsltDatePst);
		}

		if (repeatChkFlg && chk_RsltFlg.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RsltFlgPst);
		}

		if (repeatChkFlg && chk_UseByDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_UseByDatePst);
		}
		
		return repeatChkFlg;
	}

	//#CM688880
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_InstkPlanDate.getChecked()
			&& !chk_OrderingDate.getChecked()
			&& !chk_CnsgnrCd.getChecked()
			&& !chk_CnsgnrNm.getChecked()
			&& !chk_SupplierCd.getChecked()
			&& !chk_SupplierNm.getChecked()
			&& !chk_TicketNo.getChecked()
			&& !chk_TicketLineNo.getChecked()
			&& !chk_ItemCd.getChecked()
			&& !chk_BundleItf.getChecked()
			&& !chk_CaseItf.getChecked()
			&& !chk_BundleEtr.getChecked()
			&& !chk_CaseEtr.getChecked()
			&& !chk_ItemNm.getChecked()
			&& !chk_InstkPlanQtyPtl.getChecked()
			&& !chk_TCDCfFlg.getChecked()
			&& !chk_CustCd.getChecked()
			&& !chk_CustNm.getChecked()
			&& !chk_InstkQtyPtl.getChecked()
			&& !chk_InstkRsltDate.getChecked()
			&& !chk_RsltFlg.getChecked()
			&& !chk_UseByDate.getChecked())
		{
			return false;
		}
		return true;
	}
	//#CM688881
	// Event handler methods -----------------------------------------
	//#CM688882
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688883
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688884
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688885
	/** 
	 * Clicking on Return button invokes this.<BR>
	 * <BR>
	 * Summary: Return to the previous screen. <BR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//#CM688886
		// Shift to the original screen.
		forward(DO_DEFINEREPORT);
	}

	//#CM688887
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688888
	/** 
	 * Clicking on Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   Shift to the Menu screen.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM688889
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688890
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688891
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688892
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688893
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688894
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688895
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688896
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688897
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688898
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688899
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkPlanDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688900
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688901
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688903
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenInstkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688904
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688905
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688906
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688907
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688908
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688909
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688910
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688911
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688912
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688913
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688914
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688915
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688916
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688917
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OrderDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688918
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_OrderingDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_OrderingDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688920
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderingDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688921
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderingDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688922
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderingDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenOrderingDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderingDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderingDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderingDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstkPlanQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkPlanQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkPlanQtyPtl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanQtyPtlLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanQtyPtlLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanQtyPtlLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenInstkPlanQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanQtyPtlPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688935
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanQtyPtlPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanQtyPtlPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688946
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688947
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TCDCFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688948
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TCDCfFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688949
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TCDCfFlg_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688950
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TCDCfFlgLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688951
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TCDCfFlgLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TCDCfFlgLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenTCDCfFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688954
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TCDCFlgPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688955
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TCDCFlgPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TCDCFlgPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688957
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688958
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688959
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688960
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688961
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688962
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688963
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688964
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688965
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688967
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688968
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688969
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688970
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688973
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCustCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688974
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688975
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688976
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688977
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688978
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SupplierCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688979
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SupplierCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688980
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688981
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688982
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688983
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenSupplierCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688984
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688985
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688986
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688987
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688988
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688989
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM688990
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688991
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688992
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688993
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCustNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688994
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688995
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688996
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688997
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SupplierName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688998
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SupplierNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688999
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SupplierNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689000
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689001
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689002
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689003
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenSupplierNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689004
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689005
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689006
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689007
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstkQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689008
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689009
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkQtyPtl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689010
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkQtyPtlLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689011
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkQtyPtlLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689012
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkQtyPtlLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689013
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenInstkQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689014
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkQtyPtlPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689015
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkQtyPtlPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689016
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkQtyPtlPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689017
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689018
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689019
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TicketNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689021
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689022
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689023
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689024
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689025
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689026
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689027
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstockResultDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689028
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689029
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkRsltDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689030
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689031
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689032
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689033
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenInstkRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689034
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689035
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689036
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689037
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689038
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689039
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TicketLineNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689040
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689041
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689042
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689043
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenTicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689044
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689045
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689046
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689047
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689048
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689049
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltFlg_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689050
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689051
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689052
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689053
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689054
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689055
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689056
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689057
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689058
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689059
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689060
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689061
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689062
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689073
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenUseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689074
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689077
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689080
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689081
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689082
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689083
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689084
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689085
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689086
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689087
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689094
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689095
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689096
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689097
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689098
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689099
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689100
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689101
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689102
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689103
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689105
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689106
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689107
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689108
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689109
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689110
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689111
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689114
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689116
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689117
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689118
	/** 
	 * Clicking on Add button for field items set for reporting data (setting of Receiving Result field) invokes this.<BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to add it or not. <BR>
	 * <BR>
	 * 1.Check the input area. <BR>
	 *   <DIR>
	 *     -If no check is placed in each checkbox, display a message: "Select the target to be added". <BR>
	 *     -If one or more field items are ticked off: Shift to 2.<BR>
	 *   </DIR>
	 * Display the dialog box for confirming to add. ("Do you add this?") <BR>
	 * <BR>
	 *   <DIR>
	 *     [Dialog for confirming to start: Cancel] <BR>
	 *     <DIR>
	 *       Disable to do anything. <BR>
	 * 	  </DIR>
	 *     [Dialog for confirming to start: OK]
	 *     <DIR>
	 *       Add it using the information in the Preset area. <BR>
	 *       <DIR>
	 *         2.Check the input area. <BR>
	 *         <DIR>
	 *           -If no check is placed in each checkbox, display a message: "Select the target to be added". <BR>
	 *         </DIR>
	 *         3.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters) <BR>
	 *         <DIR>
	 *           -Ensure the length is not larger than the max length. <BR>
	 *           -Ensure that the Length is larger than 0. <BR>  
	 *           -Ensure that the Position is larger than 0. <BR>     
	 *           Note) Duplicated position values are acceptable (in such case, combine two or more data fields and report them together). <BR>
	 *         </DIR>
	 *         4.Start the schedule.<BR>
	 *         5.Set the cursor to Length of a field item. <BR>
	 *       </DIR>            
	 *     </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM689119
		// Set the initial value of the focus to Planned Receiving Date Enabled.
	    setFocus(chk_InstkPlanDate);
		
		//#CM689120
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM689121
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}
		
		//#CM689122
		// Check for input.
		WmsCheckker.checkDefine(lbl_InstockPlanDate, chk_InstkPlanDate, txt_InstkPlanDateLen, lbl_MaxLenInstkPlanDate, txt_InstkPlanDatePst);
		WmsCheckker.checkDefine(lbl_OrderDate, chk_OrderingDate, txt_OrderingDateLen, lbl_MaxLenOrderingDate, txt_OrderingDatePst);
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenCnsgnrNm, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_SupplierCode, chk_SupplierCd, txt_SupplierCdLen, lbl_MaxLenSupplierCd, txt_SupplierCdPst);
		WmsCheckker.checkDefine(lbl_SupplierName, chk_SupplierNm, txt_SupplierNmLen, lbl_MaxLenSupplierNm, txt_SupplierNmPst);
		WmsCheckker.checkDefine(lbl_SupplierName, chk_SupplierNm, txt_SupplierNmLen, lbl_MaxLenSupplierNm, txt_SupplierNmPst);
		WmsCheckker.checkDefine(lbl_TicketNo, chk_TicketNo, txt_TicketNoLen, lbl_MaxLenTicketNo, txt_TicketNoPst);
		WmsCheckker.checkDefine(lbl_TicketLineNo, chk_TicketLineNo, txt_TicketLineNoLen, lbl_MaxLenTicketLineNo, txt_TicketLineNoPst);
		WmsCheckker.checkDefine(lbl_ItemCode, chk_ItemCd, txt_ItemCdLen, lbl_MaxLenItemCd, txt_ItemCdPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLen, lbl_MaxLenBundleItf, txt_BundleItfPst);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLen, lbl_MaxLenCaseItf, txt_CaseItfPst);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEtr, txt_BundleEtrLen, lbl_MaxLenBundleEtr, txt_BundleEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEntering, chk_CaseEtr, txt_CaseEtrLen, lbl_MaxLenCaseEtr, txt_CaseEtrPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_ItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_InstkPlanQtyPtl, chk_InstkPlanQtyPtl, txt_InstkPlanQtyPtlLen, lbl_MaxLenInstkPlanQtyPtl, txt_InstkPlanQtyPtlPst);
		WmsCheckker.checkDefine(lbl_TCDCFlag, chk_TCDCfFlg, txt_TCDCfFlgLen, lbl_MaxLenTCDCfFlg, txt_TCDCFlgPst);
		WmsCheckker.checkDefine(lbl_CustomerCode, chk_CustCd, txt_CustCdLen, lbl_MaxLenCustCd, txt_CustCdPst);
		WmsCheckker.checkDefine(lbl_CustomerName, chk_CustNm, txt_CustNmLen, lbl_MaxLenCustNm, txt_CustNmPst);
		WmsCheckker.checkDefine(lbl_InstkQtyPtl, chk_InstkQtyPtl, txt_InstkQtyPtlLen, lbl_MaxLenInstkQtyPtl, txt_InstkQtyPtlPst);
		WmsCheckker.checkDefine(lbl_InstockResultDate, chk_InstkRsltDate, txt_InstkRsltDateLen, lbl_MaxLenInstkRsltDate, txt_InstkRsltDatePst);
		WmsCheckker.checkDefine(lbl_ResultFlag, chk_RsltFlg, txt_RsltFlgLen, lbl_MaxLenRsltFlg, txt_RsltFlgPst);
		WmsCheckker.checkDefine(lbl_UseByDate, chk_UseByDate, txt_UseByDateLen, lbl_MaxLenUseByDate, txt_UseByDatePst);
	
		Connection conn = null;
		
		try
		{
			//#CM689123
			// Check for duplicate Position.
			if (!checkRepeat())
			{
				//#CM689124
				// Set the Error Message.
				//#CM689125
				// 6023098=No duplicate of sequential order is allowed.
				message.setMsgResourceKey("6023098");
				return;
			}
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();
	
			WmsScheduler schedule = new DefineReportDataSCH();
	
			//#CM689126
			// Set the value for the parameter.
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_INSTOCK);
			//#CM689127
			// Planned Receiving Date: Enabled
			param[0].setValid_PlanDate(chk_InstkPlanDate.getChecked());
			//#CM689128
			// Planned Receiving Date: Length
			param[0].setFigure_PlanDate(txt_InstkPlanDateLen.getText());
			//#CM689129
			// Planned Receiving Date: Max Length
			param[0].setMaxFigure_PlanDate(lbl_MaxLenInstkPlanDate.getText());
			//#CM689130
			// Planned Receiving Date: Position
			param[0].setPosition_PlanDate(txt_InstkPlanDatePst.getText());
			
			//#CM689131
			// Order Date: Enabled
			param[0].setValid_OrderingDate(chk_OrderingDate.getChecked());
			//#CM689132
			// Order Date: Length
			param[0].setFigure_OrderingDate(txt_OrderingDateLen.getText());
			//#CM689133
			// Order Date: Max Length
			param[0].setMaxFigure_OrderingDate(lbl_MaxLenOrderingDate.getText());
			//#CM689134
			// Order Date: Position
			param[0].setPosition_OrderingDate(txt_OrderingDatePst.getText());
			
			//#CM689135
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM689136
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM689137
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM689138
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM689139
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM689140
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM689141
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenCnsgnrNm.getText());
			//#CM689142
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			
			//#CM689143
			// Supplier Code: Enabled
			param[0].setValid_SupplierCode(chk_SupplierCd.getChecked());
			//#CM689144
			// Supplier Code: Length
			param[0].setFigure_SupplierCode(txt_SupplierCdLen.getText());
			//#CM689145
			// Supplier Code: Max Length
			param[0].setMaxFigure_SupplierCode(lbl_MaxLenSupplierCd.getText());
			//#CM689146
			// Supplier Code: Position
			param[0].setPosition_SupplierCode(txt_SupplierCdPst.getText());
			
			//#CM689147
			// Supplier Name: Enabled
			param[0].setValid_SupplierName(chk_SupplierNm.getChecked());
			//#CM689148
			// Supplier Name: Length
			param[0].setFigure_SupplierName(txt_SupplierNmLen.getText());
			//#CM689149
			// Supplier Name: Max Length
			param[0].setMaxFigure_SupplierName(lbl_MaxLenSupplierNm.getText());
			//#CM689150
			// Supplier Name: Position
			param[0].setPosition_SupplierName(txt_SupplierNmPst.getText());

			//#CM689151
			// Ticket No.: Enabled
			param[0].setValid_InstockTicketNo(chk_TicketNo.getChecked());
			//#CM689152
			// Ticket No.: Length
			param[0].setFigure_InstockTicketNo(txt_TicketNoLen.getText());
			//#CM689153
			// Ticket No.: Max Length
			param[0].setMaxFigure_InstockTicketNo(lbl_MaxLenTicketNo.getText());
			//#CM689154
			// Ticket No.: Position
			param[0].setPosition_InstockTicketNo(txt_TicketNoPst.getText());

			//#CM689155
			// Ticket Line No.: Enabled
			param[0].setValid_InstockLineNo(chk_TicketLineNo.getChecked());
			//#CM689156
			// Ticket Line No.: Length
			param[0].setFigure_InstockLineNo(txt_TicketLineNoLen.getText());
			//#CM689157
			// Ticket Line No.: Max Length
			param[0].setMaxFigure_InstockLineNo(lbl_MaxLenTicketLineNo.getText());
			//#CM689158
			// Ticket Line No.: Position
			param[0].setPosition_InstockLineNo(txt_TicketLineNoPst.getText());
			
			//#CM689159
			// Item Code: Enabled
			param[0].setValid_ItemCode(chk_ItemCd.getChecked());
			//#CM689160
			// Item Code: Length
			param[0].setFigure_ItemCode(txt_ItemCdLen.getText());
			//#CM689161
			// Item Code: Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCd.getText());
			//#CM689162
			// Item Code: Position
			param[0].setPosition_ItemCode(txt_ItemCdPst.getText());
			
			//#CM689163
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM689164
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLen.getText());
			//#CM689165
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM689166
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPst.getText());
			
			//#CM689167
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM689168
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLen.getText());
			//#CM689169
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM689170
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPst.getText());
			
			//#CM689171
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEtr.getChecked());
			//#CM689172
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BundleEtrLen.getText());
			//#CM689173
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEtr.getText());
			//#CM689174
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BundleEtrPst.getText());
			
			//#CM689175
			// Packed Qty per Case: Enabled
			param[0].setValid_EnteringQty(chk_CaseEtr.getChecked());
			//#CM689176
			// Packed Qty per Case: Length
			param[0].setFigure_EnteringQty(txt_CaseEtrLen.getText());
			//#CM689177
			// Packed Qty per Case: Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtr.getText());
			//#CM689178
			// Packed Qty per Case: Position
			param[0].setPosition_EnteringQty(txt_CaseEtrPst.getText());
			
			//#CM689179
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_ItemNm.getChecked());
			//#CM689180
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM689181
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM689182
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());
			
			//#CM689183
			// Receiving Plan Qty (Total Bulk Qty): Enabled
			param[0].setValid_PlanQty(chk_InstkPlanQtyPtl.getChecked());
			//#CM689184
			// Receiving Plan Qty (Total Bulk Qty): Length
			param[0].setFigure_PlanQty(txt_InstkPlanQtyPtlLen.getText());
			//#CM689185
			// Receiving Plan Qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenInstkPlanQtyPtl.getText());
			//#CM689186
			// Receiving Plan Qty (Total Bulk Qty): Position
			param[0].setPosition_PlanQty(txt_InstkPlanQtyPtlPst.getText());
			
			//#CM689187
			// TC/DC division: Enabled
			param[0].setValid_TcDcFlag(chk_TCDCfFlg.getChecked());
			//#CM689188
			// TC/DC division: Length
			param[0].setFigure_TcDcFlag(txt_TCDCfFlgLen.getText());
			//#CM689189
			// TC/DC division: Max Length
			param[0].setMaxFigure_TcDcFlag(lbl_MaxLenTCDCfFlg.getText());
			//#CM689190
			// TC/DC division: Position
			param[0].setPosition_TcDcFlag(txt_TCDCFlgPst.getText());
			
			//#CM689191
			// Customer Code: Enabled
			param[0].setValid_CustomerCode(chk_CustCd.getChecked());
			//#CM689192
			// Customer Code: Length
			param[0].setFigure_CustomerCode(txt_CustCdLen.getText());
			//#CM689193
			// Customer Code: Max Length
			param[0].setMaxFigure_CustomerCode(lbl_MaxLenCustCd.getText());
			//#CM689194
			// Customer Code: Position
			param[0].setPosition_CustomerCode(txt_CustCdPst.getText());
			
			//#CM689195
			// Customer Name: Enabled
			param[0].setValid_CustomerName(chk_CustNm.getChecked());
			//#CM689196
			// Customer Name: Length
			param[0].setFigure_CustomerName(txt_CustNmLen.getText());
			//#CM689197
			// Customer Name: Max Length
			param[0].setMaxFigure_CustomerName(lbl_MaxLenCustNm.getText());
			//#CM689198
			// Customer Name: Position
			param[0].setPosition_CustomerName(txt_CustNmPst.getText());
			
			//#CM689199
			// Receiving Qty (Total Bulk Qty): Enabled
			param[0].setValid_PieceResultQty(chk_InstkQtyPtl.getChecked());
			//#CM689200
			// Receiving Qty (Total Bulk Qty): Length
			param[0].setFigure_PieceResultQty(txt_InstkQtyPtlLen.getText());
			//#CM689201
			// Receiving Qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_PieceResultQty(lbl_MaxLenInstkQtyPtl.getText());
			//#CM689202
			// Receiving Qty (Total Bulk Qty): Position
			param[0].setPosition_PieceResultQty(txt_InstkQtyPtlPst.getText());
			
			//#CM689203
			// Receiving Result Date: Enabled
			param[0].setValid_WorkDate(chk_InstkRsltDate.getChecked());
			//#CM689204
			// Receiving Result Date: Length
			param[0].setFigure_WorkDate(txt_InstkRsltDateLen.getText());
			//#CM689205
			// Receiving Result Date: Max Length
			param[0].setMaxFigure_WorkDate(lbl_MaxLenInstkRsltDate.getText());
			//#CM689206
			// Receiving Result Date: Position
			param[0].setPosition_WorkDate(txt_InstkRsltDatePst.getText());
			
			//#CM689207
			// Result division: Enabled
			param[0].setValid_ResultFlag(chk_RsltFlg.getChecked());
			//#CM689208
			// Result division: Length
			param[0].setFigure_ResultFlag(txt_RsltFlgLen.getText());
			//#CM689209
			// Result division: Max Length
			param[0].setMaxFigure_ResultFlag(lbl_MaxLenRsltFlg.getText());
			//#CM689210
			// Result division: Position
			param[0].setPosition_ResultFlag(txt_RsltFlgPst.getText());
			
			//#CM689211
			// Expiry Date: Enabled
			param[0].setValid_UseByDate(chk_UseByDate.getChecked());
			//#CM689212
			// Expiry Date: Length
			param[0].setFigure_UseByDate(txt_UseByDateLen.getText());
			//#CM689213
			// Expiry Date: Max Length
			param[0].setMaxFigure_UseByDate(lbl_MaxLenUseByDate.getText());
			//#CM689214
			// Expiry Date: Position
			param[0].setPosition_UseByDate(txt_UseByDatePst.getText());

			//#CM689215
			// Start the schedule.
			if (schedule.startSCH(conn, param))
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}
	
			if (schedule.getMessage() != null)
			{
				//#CM689216
				// Display a message.
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
				//#CM689217
				// Close the connection.
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

	//#CM689218
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689219
	/** 
	 * Clicking on Clear button for field items set for reporting data (setting of Receiving Result field) invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Reset the Input area to its initial display (the status as obtained from EnvironmentInformation). <BR>
	 *   2.Set the cursor to Length of a field item. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		setInistView();
	}
}
//#CM689220
//end of class
