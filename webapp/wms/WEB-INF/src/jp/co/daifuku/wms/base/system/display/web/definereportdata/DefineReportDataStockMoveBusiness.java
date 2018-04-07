// $Id: DefineReportDataStockMoveBusiness.java,v 1.2 2006/11/13 08:18:40 suresh Exp $

//#CM691675
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

//#CM691676
/**
 * Designer :  D.Hakui <BR>
 * Maker :     D.Hakui <BR>
 * <BR>
 * Allow this class to set a field for reporting data (to set the Relocation Result field).<BR>
 * Set the searched value via the schedule for each field item.
 * Set the contents entered via screen for the parameter. Allow the schedule to set the Report Data field (set the Relocation Result field) based on the parameter.<BR>
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
 * 	 Consignor Code +<BR>
 * 	 Consignor Name+<BR>
 * 	 Item Code +<BR>
 * 	 Item Name+<BR>
 * 	 Packed Qty per Case+<BR>
 *   Relocated qty (Total Bulk Qty) +<BR>
 *   Relocation Source Location+<BR>
 * 	 Relocation Target Location+<BR>
 * 	 Relocation Result Date+<BR>
 * 	 Expiry Date+<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:40 $
 * @author  $Author: suresh $
 */
public class DefineReportDataStockMoveBusiness extends DefineReportDataStockMove implements WMSConstants
{
	private static final String DO_DEFINEREPORT = "/system/definereportdata/DefineReportData.do";
	
	//#CM691677
	// Class fields --------------------------------------------------

	//#CM691678
	// Class variables -----------------------------------------------

	//#CM691679
	// Class method --------------------------------------------------

	//#CM691680
	// Constructors --------------------------------------------------

	//#CM691681
	// Public methods ------------------------------------------------

	//#CM691682
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
			//#CM691683
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM691684
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM691685
			//Set the path to the help file.
			btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM691686
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	//#CM691687
	/**
	 * Show the Initial Display.<BR>
	 * <BR>
	 * Display a for setting the field items of the functions selected via the previous screen. <BR>
	 * <BR>
	 * <DIR>
	 *   Enabled/Disabled: Selected<BR>
	 *   Length [read only] <BR>
	 *   Position<BR> 
	 *   <BR>
	 *   <DIR>
	 *     Obtain Enabled/Disabled field item, Length, and Position from EnvironmentInformation and display them. <BR>
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
		//#CM691688
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		//#CM691689
		// Check that the length is read-only. Length.
		txt_CnsgnrCdLen.setReadOnly(true);
		txt_CnsgnrNmLen.setReadOnly(true);
		txt_ItemCdLen.setReadOnly(true);
		txt_ItemNmLen.setReadOnly(true);
		txt_CaseEtrLen.setReadOnly(true);
		txt_MovQtyPtlLen.setReadOnly(true);
		txt_FromMovLctLen.setReadOnly(true);
		txt_ToMovLctLen.setReadOnly(true);
		txt_MovRsltDateLen.setReadOnly(true);
		txt_UseByDateLen.setReadOnly(true);

		setInistView();
	}

	//#CM691690
	/** 
	 * Allow this method to set the initial values for screen.<BR>
	 * <BR>
	 * Summary: Sets up the settings of each field item in the screen from property file.<BR>
	 * 
	 * @throws Exception Report all exceptions.
	 */
	private void setInistView() throws Exception
	{
		//#CM691691
		// Set the initial value of the focus to Consignor Code Enabled.
		setFocus(chk_CnsgnrCd);

		Connection conn = null;
		try
		{
			//#CM691692
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_MOVEMENT);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM691693
				//	Consignor Code (Enabled)
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM691694
				//	Consignor Code (Length)
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM691695
				//	Consignor Code (Max Length)
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM691696
				//	Consignor Code (Position)
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM691697
				//	Consignor Name (Enabled)
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM691698
				//	Consignor Name (Length)
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM691699
				//	Consignor Name (Max Length)
				lbl_MaxLenCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM691700
				//	Consignor Name (Position)
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM691701
				//	Item Code (Enabled)
				chk_ItemCd.setChecked(param[0].getValid_ItemCode());
				//#CM691702
				//	Item Code (Length)
				txt_ItemCdLen.setText(param[0].getFigure_ItemCode());
				//#CM691703
				//	Item Code (Max Length)
				lbl_MaxLenItemCd.setText(param[0].getMaxFigure_ItemCode());
				//#CM691704
				//	Item Code (Position)
				txt_ItemCdPst.setText(param[0].getPosition_ItemCode());
				
				//#CM691705
				//	Item Name (Enabled)
				chk_ItemNm.setChecked(param[0].getValid_ItemName());
				//#CM691706
				//	Item Name (Length)
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM691707
				//	Item Name (Max Length)
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM691708
				//	Item Name (Position)
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				
				//#CM691709
				//	Packed Qty per Case (Enabled)
				chk_CaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM691710
				//	Packed Qty per Case (Length)
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM691711
				//	Packed Qty per Case (Max Length)
				lbl_MaxLenCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM691712
				//	Packed Qty per Case (Position)
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());

				//#CM691713
				//	Relocated qty (Total Bulk Qty) (Enabled)
				chk_MovQtyPtl.setChecked(param[0].getValid_CaseResultQty());
				//#CM691714
				//	Relocated qty (Total Bulk Qty)(Length)
				txt_MovQtyPtlLen.setText(param[0].getFigure_CaseResultQty());
				//#CM691715
				//	Relocated qty (Total Bulk Qty) (Max Length)
				lbl_MaxLenMovQtyPtl.setText(param[0].getMaxFigure_CaseResultQty());
				//#CM691716
				//	Relocated qty (Total Bulk Qty) (Position)
				txt_MovQtyPtlPst.setText(param[0].getPosition_CaseResultQty());
				
				//#CM691717
				//	Relocation Source Location (Enabled)
				chk_FromMovLct.setChecked(param[0].getValid_PieceLocation());
				//#CM691718
				//	Relocation Source Location (Length)
				txt_FromMovLctLen.setText(param[0].getFigure_PieceLocation());
				//#CM691719
				//	Relocation Source Location (Max Length)
				lbl_MaxLenFromMovLct.setText(param[0].getMaxFigure_PieceLocation());
				//#CM691720
				//	Relocation Source Location (Position)
				txt_FromMovLctPst.setText(param[0].getPosition_PieceLocation());
				
				//#CM691721
				//	Relocation Target Location (Enabled)
				chk_ToMovLct.setChecked(param[0].getValid_CaseLocation());
				//#CM691722
				//	Relocation Target Location (Length)
				txt_ToMovLctLen.setText(param[0].getFigure_CaseLocation());
				//#CM691723
				//	Relocation Target Location (Max Length)
				lbl_MaxLenToMovLct.setText(param[0].getMaxFigure_CaseLocation());
				//#CM691724
				//	Relocation Target Location (Position)
				txt_ToMovLctPst.setText(param[0].getPosition_CaseLocation());
				
				//#CM691725
				//	Relocation Result Date (Enabled)
				chk_MovRsltDate.setChecked(param[0].getValid_WorkDate());
				//#CM691726
				//	Relocation Result Date (Length)
				txt_MovRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM691727
				//	Relocation Result Date (Max Length)
				lbl_MaxLenMovRsltDate.setText(param[0].getMaxFigure_WorkDate());
				//#CM691728
				//	Relocation Result Date (Position)
				txt_MovRsltDatePst.setText(param[0].getPosition_WorkDate());
				
				//#CM691729
				//	Expiry Date (Enabled)
				chk_UseByDate.setChecked(param[0].getValid_UseByDate());
				//#CM691730
				//	Expiry Date (Length)
				txt_UseByDateLen.setText(param[0].getFigure_UseByDate());
				//#CM691731
				//	Expiry Date (Max Length)
				lbl_MaxLenUseByDate.setText(param[0].getMaxFigure_UseByDate());
				//#CM691732
				//	Expiry Date (Position)
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
				//#CM691733
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

	//#CM691734
	// Package methods -----------------------------------------------

	//#CM691735
	// Protected methods ---------------------------------------------

	//#CM691736
	// Private methods -----------------------------------------------

	//#CM691737
	// Event handler methods -----------------------------------------
	//#CM691738
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691739
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691740
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691741
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
		//#CM691742
		// Shift to the original screen.
		forward(DO_DEFINEREPORT);
	}

	//#CM691743
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691744
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

	//#CM691745
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691746
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691747
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691748
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691749
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691750
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691751
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691752
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691760
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691761
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691762
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691763
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691764
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691768
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691770
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691771
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691772
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691773
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691774
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691775
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691777
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691779
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691780
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691781
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691783
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691784
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691785
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691786
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691787
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691788
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691789
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691790
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691791
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691792
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691793
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691794
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691795
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691796
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691797
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691798
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691799
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MoveQtyPieceTotal_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691800
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MovQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691801
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MovQtyPtl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691802
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovQtyPtlLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691803
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovQtyPtlLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691804
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovQtyPtlLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691805
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenMovQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691806
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovQtyPtlPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691807
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovQtyPtlPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691808
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovQtyPtlPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691809
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromMoveLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691810
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_FromMovLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691811
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_FromMovLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691812
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromMovLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691813
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromMovLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691814
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromMovLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691815
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenFromMovLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691816
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromMovLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691817
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromMovLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691818
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromMovLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691819
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ToMoveLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691820
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ToMovLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691821
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ToMovLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691822
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToMovLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691823
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToMovLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691824
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToMovLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691825
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenToMovLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691826
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToMovLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691827
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToMovLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691828
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToMovLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691829
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MoveResultDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691830
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MovRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691831
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MovRsltDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691832
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovRsltDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691833
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovRsltDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691834
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovRsltDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691835
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenMovRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691836
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovRsltDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691837
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovRsltDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691838
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovRsltDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691839
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691840
	/** 
	 * Clicking on Add button for field items set for reporting data (setting of Relocation Result field) invokes this.<BR>
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
	 * 	   </DIR>
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
		//#CM691841
		// Set the initial value of the focus to Consignor Code Enabled.
		setFocus(chk_CnsgnrCd);

		//#CM691842
		// Check for input.
		//#CM691843
		// Display error if no check is placed in all checkboxes.
		//#CM691844
		// Consignor Code
		if (!chk_CnsgnrCd.getChecked() &&
				//#CM691845
				//	Consignor Name
				!chk_CnsgnrNm.getChecked() &&
				//#CM691846
				//	Item Code
				!chk_ItemCd.getChecked() &&
				//#CM691847
				//	Item Name
				!chk_ItemNm.getChecked() &&
				//#CM691848
				//	Packed Qty per Case
				!chk_CaseEtr.getChecked() &&
				//#CM691849
				//	Relocated qty (Total Bulk Qty)
				!chk_MovQtyPtl.getChecked() &&
				//#CM691850
				//	Relocation Source Location
				!chk_FromMovLct.getChecked() &&
				//#CM691851
				//	Relocation Target Location
				!chk_ToMovLct.getChecked() &&
				//#CM691852
				//	Relocation Result Date
				!chk_MovRsltDate.getChecked() &&
				//#CM691853
				//	Expiry Date
				!chk_UseByDate.getChecked()				 
				)
		{
			//#CM691854
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}
		
		//#CM691855
		// Check for input.
		//#CM691856
		// 	Consignor Code
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		//#CM691857
		// 	Consignor Name
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenCnsgnrNm, txt_CnsgnrNmPst);
		//#CM691858
		// 	Item Code
		WmsCheckker.checkDefine(lbl_ItemCode, chk_ItemCd, txt_ItemCdLen, lbl_MaxLenItemCd, txt_ItemCdPst);
		//#CM691859
		// 	Item Name
		WmsCheckker.checkDefine(lbl_ItemName, chk_ItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		//#CM691860
		// 	Packed Qty per Case: Length
		WmsCheckker.checkDefine(lbl_CaseEntering, chk_CaseEtr, txt_CaseEtrLen, lbl_MaxLenCaseEtr, txt_CaseEtrPst);
		//#CM691861
		// 	Relocated qty (Total Bulk Qty): Length
		WmsCheckker.checkDefine(lbl_MoveQtyPieceTotal, chk_MovQtyPtl, txt_MovQtyPtlLen, lbl_MaxLenMovQtyPtl, txt_MovQtyPtlPst);
		//#CM691862
		//	Relocation Source Location
		WmsCheckker.checkDefine(lbl_FromMoveLocation, chk_FromMovLct, txt_FromMovLctLen, lbl_MaxLenFromMovLct, txt_FromMovLctPst);
		//#CM691863
		//	Relocation Target Location
		WmsCheckker.checkDefine(lbl_ToMoveLocation, chk_ToMovLct, txt_ToMovLctLen, lbl_MaxLenToMovLct, txt_ToMovLctPst);
		//#CM691864
		//	Relocation Result Date
		WmsCheckker.checkDefine(lbl_MoveResultDate, chk_MovRsltDate, txt_MovRsltDateLen, lbl_MaxLenMovRsltDate, txt_MovRsltDatePst);
		//#CM691865
		//	Expiry Date
		WmsCheckker.checkDefine(lbl_UseByDate, chk_UseByDate, txt_UseByDateLen, lbl_MaxLenUseByDate, txt_UseByDatePst);

		//#CM691866
		// Check for duplication.
		boolean positionCheckFlag = true;

		if (positionCheckFlag && chk_CnsgnrCd.getChecked())
		{
			positionCheckFlag = positionCheck(txt_CnsgnrCdPst);
		}
		
		if (positionCheckFlag && chk_CnsgnrNm.getChecked())
		{
			positionCheckFlag = positionCheck(txt_CnsgnrNmPst);
		}
		
		if (positionCheckFlag && chk_ItemCd.getChecked())
		{
			positionCheckFlag = positionCheck(txt_ItemCdPst);
		}
		
		if (positionCheckFlag && chk_ItemNm.getChecked())
		{
			positionCheckFlag = positionCheck(txt_ItemNmPst);
		}
		
		if (positionCheckFlag && chk_CaseEtr.getChecked())
		{
			positionCheckFlag = positionCheck(txt_CaseEtrPst);
		}
		
		if (positionCheckFlag && chk_MovQtyPtl.getChecked())
		{
			positionCheckFlag = positionCheck(txt_MovQtyPtlPst);
		}
		
		if (positionCheckFlag && chk_FromMovLct.getChecked())
		{
			positionCheckFlag = positionCheck(txt_FromMovLctPst);
		}
		
		if (positionCheckFlag && chk_ToMovLct.getChecked())
		{
			positionCheckFlag = positionCheck(txt_ToMovLctPst);
		}
		
		if (positionCheckFlag && chk_MovRsltDate.getChecked())
		{
			positionCheckFlag = positionCheck(txt_MovRsltDatePst);
		}
		
		if (positionCheckFlag && chk_UseByDate.getChecked())
		{
			positionCheckFlag = positionCheck(txt_UseByDatePst);
		}
				
		//#CM691867
		// Return error if duplicate "Position" exist.
		if (!positionCheckFlag)
		{
			//#CM691868
			// Display the error message and close it.
			//#CM691869
			// 6023098=No duplicate of sequential order is allowed.
			message.setMsgResourceKey("6023098");
			return;
		}
		
		Connection conn = null;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();

			WmsScheduler schedule = new DefineReportDataSCH();

			//#CM691870
			// Set the value for the parameter.
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_MOVEMENT);
			//#CM691871
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM691872
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM691873
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM691874
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM691875
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM691876
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM691877
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenCnsgnrNm.getText());
			//#CM691878
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			
			//#CM691879
			// Item Code: Enabled
			param[0].setValid_ItemCode(chk_ItemCd.getChecked());
			//#CM691880
			// Item Code: Length
			param[0].setFigure_ItemCode(txt_ItemCdLen.getText());
			//#CM691881
			// Item Code: Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCd.getText());
			//#CM691882
			// Item Code: Position
			param[0].setPosition_ItemCode(txt_ItemCdPst.getText());

			//#CM691883
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_ItemNm.getChecked());
			//#CM691884
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM691885
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM691886
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());

			//#CM691887
			// Packed Qty per Case: Enabled
			param[0].setValid_EnteringQty(chk_CaseEtr.getChecked());
			//#CM691888
			// Packed Qty per Case: Length
			param[0].setFigure_EnteringQty(txt_CaseEtrLen.getText());
			//#CM691889
			// Packed Qty per Case: Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtr.getText());
			//#CM691890
			// Packed Qty per Case: Position
			param[0].setPosition_EnteringQty(txt_CaseEtrPst.getText());
			
			//#CM691891
			// Relocated qty (Total Bulk Qty): Enabled
			param[0].setValid_CaseResultQty(chk_MovQtyPtl.getChecked());
			//#CM691892
			// Relocated qty (Total Bulk Qty): Length
			param[0].setFigure_CaseResultQty(txt_MovQtyPtlLen.getText());
			//#CM691893
			// Relocated qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_CaseResultQty(lbl_MaxLenMovQtyPtl.getText());
			//#CM691894
			// Relocated qty (Total Bulk Qty): Position
			param[0].setPosition_CaseResultQty(txt_MovQtyPtlPst.getText());
						
			//#CM691895
			// Relocation Source Location: Enabled
			param[0].setValid_PieceLocation(chk_FromMovLct.getChecked());
			//#CM691896
			// Relocation Source Location: Length
			param[0].setFigure_PieceLocation(txt_FromMovLctLen.getText());
			//#CM691897
			// Relocation Source Location: Max Length
			param[0].setMaxFigure_PieceLocation(lbl_MaxLenFromMovLct.getText());
			//#CM691898
			// Relocation Source Location: Position
			param[0].setPosition_PieceLocation(txt_FromMovLctPst.getText());
			
			//#CM691899
			// Relocation Target Location: Enabled
			param[0].setValid_CaseLocation(chk_ToMovLct.getChecked());
			//#CM691900
			// Relocation Target Location: Length
			param[0].setFigure_CaseLocation(txt_ToMovLctLen.getText());
			//#CM691901
			// Relocation Target Location: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenToMovLct.getText());
			//#CM691902
			// Relocation Target Location: Position
			param[0].setPosition_CaseLocation(txt_ToMovLctPst.getText());
			
			//#CM691903
			// Relocation Result Date: Enabled
			param[0].setValid_WorkDate(chk_MovRsltDate.getChecked());
			//#CM691904
			// Relocation Result Date: Length
			param[0].setFigure_WorkDate(txt_MovRsltDateLen.getText());
			//#CM691905
			// Relocation Result Date: Max Length
			param[0].setMaxFigure_WorkDate(lbl_MaxLenMovRsltDate.getText());
			//#CM691906
			// Relocation Result Date: Position
			param[0].setPosition_WorkDate(txt_MovRsltDatePst.getText());

			//#CM691907
			// Expiry Date: Enabled
			param[0].setValid_UseByDate(chk_UseByDate.getChecked());
			//#CM691908
			// Expiry Date: Length
			param[0].setFigure_UseByDate(txt_UseByDateLen.getText());
			//#CM691909
			// Expiry Date: Max Length
			param[0].setMaxFigure_UseByDate(lbl_MaxLenUseByDate.getText());
			//#CM691910
			// Expiry Date: Position
			param[0].setPosition_UseByDate(txt_UseByDatePst.getText());

			//#CM691911
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
				//#CM691912
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
				//#CM691913
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

	//#CM691914
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691915
	/** 
	 * Clicking on Clear button for field items set for reporting data (setting of Relocation Result field) invokes this.<BR>
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

	//#CM691916
	/** 
	 * Allow this method to check wheter the position is duplicated or not. <BR>
	 * <BR>
	 * Summary: Checks for duplicate position. <BR>
	 * 
	 * @param position Position to check for double occupation.
	 * @return Return true if two or more data occupies a single position.
	 * @throws Exception Report all exceptions.
	 */
	private boolean positionCheck(NumberTextBox position) throws Exception
	{
		int checkCount = 0;

		//#CM691917
		// Consignor Code: Position
		if (position.getText().equals(txt_CnsgnrCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691918
		// Consignor Name: Position
		if (position.getText().equals(txt_CnsgnrNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691919
		// Item Code: Position
		if (position.getText().equals(txt_ItemCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691920
		// Item Name: Position
		if (position.getText().equals(txt_ItemNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691921
		// Packed Qty per Case: Position
		if (position.getText().equals(txt_CaseEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691922
		// Relocated qty (Total Bulk Qty): Position
		if (position.getText().equals(txt_MovQtyPtlPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691923
		// Relocation Source Location: Position
		if (position.getText().equals(txt_FromMovLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691924
		// Relocation Target Location: Position
		if (position.getText().equals(txt_ToMovLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691925
		// Relocation Result Date: Position
		if (position.getText().equals(txt_MovRsltDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691926
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

	//#CM691927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_RptClmSetStockMov_Click(ActionEvent e) throws Exception
	{
	}

	//#CM691928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenUseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691935
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_TabKey(ActionEvent e) throws Exception
	{
	}
}
//#CM691938
//end of class
