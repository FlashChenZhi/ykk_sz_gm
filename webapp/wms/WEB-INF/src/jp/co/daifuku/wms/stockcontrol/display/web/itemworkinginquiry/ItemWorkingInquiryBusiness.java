// $Id: ItemWorkingInquiryBusiness.java,v 1.2 2006/10/04 05:04:12 suresh Exp $

//#CM4236
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.itemworkinginquiry;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.ItemWorkingInquirySCH;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
//#CM4237
/**
 * Designer : A.Nagasawa<BR>
 * Maker : A.Nagasawa<BR>
 * <BR>
 * This is the screen class to execute stock inquiry per item.<BR>
 * Pass the parameter to the schedule that execute stock inquiry process per item.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * <DIR>
 * 1.Display button press down process(<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 * 	Set the contents input from the screen to the parameter, and
 *  The schedule searches for the data for display based on the parameter and displays the result on the popup screen.<BR>
 *  <BR>
 *  [parameter] *Mandatory Input<BR>
 *  <DIR>
 * 	 Consignor code *<BR>
 * 	 Start item code<BR>
 * 	 End item code<BR>
 * 	 Case/Piece division *<BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * 2.Print button press down process(<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents input from the screen to the parameter, and
 *  schedule search the data and print based on the parameter<BR>
 *  Return true when the schedule completed the print successfully<BR>
 *  <BR>
 *  [parameter] *Mandatory Input<BR>
 *  <DIR>
 *   Consignor code *<BR>
 * 	 Start item code<BR>
 * 	 End item code<BR>
 * 	 Case/Piece division *<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/28</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:04:12 $
 * @author  $Author: suresh $
 */
public class ItemWorkingInquiryBusiness extends ItemWorkingInquiry implements WMSConstants
{
	//#CM4238
	// Class fields --------------------------------------------------
	
	
	//#CM4239
	// Maintain the control that invokes the dialog: Print button
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM4240
	// Class variables -----------------------------------------------

	//#CM4241
	// Class method --------------------------------------------------

	//#CM4242
	// Constructors --------------------------------------------------

	//#CM4243
	// Public methods ------------------------------------------------
	//#CM4244
	/**
	 * Initialize the screen
	 * <BR>
	 * <DIR>
	 *  Subject name[Initial value]
	 *  <DIR>
	 *  Consignor code [Execute initial/default display when there is only one corresponding Consignor]<BR>
	 * 	Start item code [None]<BR>
	 *  End item code [None]<BR>
	 * 	Case/Piece division [All]<BR>
	 * 	<BR>
	 * 	Initialize the focus for the Consignor code.<BR>
	 *  </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		//#CM4245
		// Set the initial value in the input field.
		//#CM4246
		// Consignor code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM4247
		// Start item code
		txt_StartItemCode.setText("");
		//#CM4248
		// End item code
		txt_EndItemCode.setText("");
		//#CM4249
		// Case/Piece division
		rdo_CpfAll.setChecked(true);
		rdo_CptCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM4250
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}

	//#CM4251
	/**
	* Invoke this before invoking each control event. <BR>
	* <BR>
	* <DIR>
	*  Summary: Displays a dialog. <BR>
	* </DIR>
	* 
	* @param e ActionEvent This is the class to store event info.
	* @throws Exception Reports all the exceptions.
	*/
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM4252
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM4253
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM4254
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM4255
	/**
	 * Returning from the Dialog button invokes this method.
	 * Override page_ConfirmBack defined on the page.
	 * <BR>
	 * Summary: When select "yes" in the dialog<BR>
	 * <BR>
	 * 1.Set the focus for the Consignor code.<BR>
	 * 2.Check which dialog returns it.<BR>
	 * 3.Check for selection of "Yes " or "No " clicked in the dialog box.<BR>
	 * 4.Selecting "Yes " starts the schedule.<BR>
	 * 5.In the case of print dialog<BR>
	 *   <DIR>
	 *   5-1.Set the parameter in the input field.<BR>
	 *   5-2.Start print schedule.<BR>
	 *   5-3.Show schedule result to the message area.<BR>
	 *	 </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM4256
			// Set the focus for the Consignor code.
			setFocus(txt_ConsignorCode);

			//#CM4257
			// Check which dialog returns it.
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM4258
			// Clicking "Yes" in the dialog box returns true.
			//#CM4259
			// Clicking "No" in the dialog box returns false.
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM4260
			// Clicking "NO " button closes the process.
			//#CM4261
			// Message set here is not necessary
			if (!isExecute)
			{
				return;
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM4262
			// Turn the flag off after closed the operation of the dialog.
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM4263
		// Start print schedule
		Connection conn = null;
		try
		{
			//#CM4264
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM4265
			// Set the input value for the parameter.
			StockControlParameter[] param = new StockControlParameter[1];
			param[0] = createParameter();

			//#CM4266
			// Start schedule
			WmsScheduler schedule = new ItemWorkingInquirySCH();
			schedule.startSCH(conn, param);
			
			//#CM4267
			// Set the message
			message.setMsgResourceKey(schedule.getMessage());
	
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
	
		}
		finally
		{
			try
			{
				//#CM4268
				// Close the connection
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}
		
	}

	//#CM4269
	/**
	 * Invoke this method when returning from the popup window.<BR>
	 * Override page_DlgBack defined on the page.
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM4270
		// Obtain the parameter selected in the listbox.
		//#CM4271
		// Consignor code
		String consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM4272
		// Start item code
		String startitem = param.getParameter(ListStockItemBusiness.STARTITEMCODE_KEY);

		//#CM4273
		// End item code
		String enditem = param.getParameter(ListStockItemBusiness.ENDITEMCODE_KEY);

		//#CM4274
		// Set the value if not empty.
		//#CM4275
		// Consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM4276
		// Start item code
		if (!StringUtil.isBlank(startitem))
		{
			txt_StartItemCode.setText(startitem);
		}
		//#CM4277
		// Start item code
		if (!StringUtil.isBlank(enditem))
		{
			txt_EndItemCode.setText(enditem);
		}
		//#CM4278
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}


	//#CM4279
	// Package methods -----------------------------------------------

	//#CM4280
	// Protected methods ---------------------------------------------
	
	//#CM4281
	/**
	 * Generate parameter object that set input value of the input area<BR>
	 * 
	 * @return Parameter object including input value of Input area.
	 */
	protected StockControlParameter createParameter()
	{
		StockControlParameter param = new StockControlParameter();
		
		param.setConsignorCode(txt_ConsignorCode.getText());
		param.setFromItemCode(txt_StartItemCode.getText());
		param.setToItemCode(txt_EndItemCode.getText());
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CptCase.getChecked())
		{
			param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_CASE);
		
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_PIECE);
		
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		return param;
	}
	
	//#CM4282
	// Private methods -----------------------------------------------
	//#CM4283
	/**
	 * This method obtains the Consignor code from the schedule on the initial display. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * 
	 * @return Consignor code <BR>
	 *         Return the string of the Consignor code when one corresponding data exists. <BR>
	 *         Return null if 0 or multiple corresponding data exist. <BR>
	 * 
	 * @throws Exception
	 *             Reports all the exceptions.
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM4284
			// Obtain the connection	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StockControlParameter param = new StockControlParameter();

			//#CM4285
			// Obtain the Consignor code from the schedule.
			WmsScheduler schedule = new ItemWorkingInquirySCH();
			param = (StockControlParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				return param.getConsignorCode();
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
				//#CM4286
				// Close the connection
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		return null;
	}

	//#CM4287
	// Event handler methods -----------------------------------------
	//#CM4288
	/**                 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4289
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4290
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4291
	/** 
	 * Clicking on the Clicking the Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Move to menu screen. 
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM4292
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4293
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4294
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4295
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4296
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4297
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4298
	/** 
	 *  Clicking Consignor search button invokes this.<BR>
	 * <BR>
	 * Summary: This Method set search conditions to the parameter, and, 
	 * Display the Consignor search listbox using the search conditions.<BR>
	 * <BR>
	 * [parameter]*Mandatory Input
	 *  <DIR>
	 *  Consignor code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM4299
		// Set the search condition for the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4300
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM4301
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do",
			param,
			"/progress.do");
	}

	//#CM4302
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4303
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4304
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4305
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4306
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4307
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4308
	/** 
	 * Clicking on the Start Search Item button invokes this.<BR>
	 * <BR>
	 * Summary: This Method set search conditions to the parameter, and, 
	 * Display the item search listbox using the search conditions.<BR>
	 * <BR>
	 * [parameter]*Mandatory Input
	 *  <DIR>
	 * 	Consignor code<BR>
	 *  Start item code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchStartItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM4309
		// Set the search conditions for the Start Search Item screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4310
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM4311
		// Start item code
		param.setParameter(ListStockItemBusiness.STARTITEMCODE_KEY, txt_StartItemCode.getText());
		//#CM4312
		// Set start flag
		param.setParameter(
			ListStockItemBusiness.RANGEITEMCODE_KEY,
			StockControlParameter.RANGE_START);

		//#CM4313
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockitem/ListStockItem.do", param, "/progress.do");
	}

	//#CM4314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4321
	/** 
	 * Clicking on the Search End Item button invokes this.<BR>
	 * <BR>
	 * Summary: This Method set search conditions to the parameter, and, 
	 * Display the item search listbox using the search conditions.<BR>
	 * <BR>
	 * [parameter]*Mandatory Input
	 *  <DIR>
	 * 	Consignor code<BR>
	 *  End item code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchEndItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM4322
		// Set the search conditions for the Search End Item screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4323
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM4324
		// End item code
		param.setParameter(ListStockItemBusiness.ENDITEMCODE_KEY, txt_EndItemCode.getText());
		//#CM4325
		// Set the Close flag.
		param.setParameter(
			ListStockItemBusiness.RANGEITEMCODE_KEY,
			StockControlParameter.RANGE_END);

		//#CM4326
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockitem/ListStockItem.do", param, "/progress.do");
	}

	//#CM4327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ItemStockList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4328
	/** 
	 * Clicking on the Search Stock List by-Item button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the "stock list by item " listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor code* <BR>
	 *       Start item code <BR>
	 *       End item code <BR>
	 *		 Case/Piece division <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_ItemStockList_Click(ActionEvent e) throws Exception
	{
		//#CM4329
		// Set the search conditions for the Stock List by-Item screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4330
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM4331
		// Start item code
		param.setParameter(ListStockItemBusiness.STARTITEMCODE_KEY, txt_StartItemCode.getText());
		//#CM4332
		// End item code
		param.setParameter(ListStockItemBusiness.ENDITEMCODE_KEY, txt_EndItemCode.getText());
		//#CM4333
		// Case/PieceDivision
		//#CM4334
		// All
		if (rdo_CpfAll.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM4335
		// Case
		else if (rdo_CptCase.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM4336
		// Piece
		else if (rdo_CpfPiece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM4337
		// None
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM4338
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockitemtotalinquiry/ListStockItemTotalInquiry.do",
			param,
			"/progress.do");
	}

	//#CM4339
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4340
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM4342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CptCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CptCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM4344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM4346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM4348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4349
	/** 
	 * Clicking on the Display button invokes this.<BR>
	 * <BR>
	 * Summary:set input item of the Input area to the parameter, and display Item stock inquiry list by item listbox on the separate screen.<BR>
	 * <BR>
	 * [parameter] *Mandatory Input
	 * <DIR>
	 * 	 Consignor code *<BR>
	 * 	 Start item code<BR>
	 * 	 End item code<BR>
	 * 	 Case/Piece division *<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM4350
		// Declare the instance to set the search conditions.
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM4351
		// Set Consignor code
		forwardParam.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM4352
		// Set the Start Item code.
		forwardParam.setParameter(
			ListStockItemBusiness.STARTITEMCODE_KEY,
			txt_StartItemCode.getText());
		//#CM4353
		// Set the End item code.
		forwardParam.setParameter(ListStockItemBusiness.ENDITEMCODE_KEY, txt_EndItemCode.getText());
		//#CM4354
		// Set Case/Piece division
		//#CM4355
		// All
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM4356
		// Case
		else if (rdo_CptCase.getChecked())
		{
			forwardParam.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM4357
		// Piece
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM4358
		// None
		else if (rdo_CpfAppointOff.getChecked())
		{
			forwardParam.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM4359
		// Display the work list search listbox.
		redirect(
			"/stockcontrol/listbox/liststockitemworkinginquiry/ListStockItemWorkingInquiry.do",
			forwardParam,
			"/progress.do");
	}

	//#CM4360
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4361
	/** 
	 * Clicking on the Print button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the input item (count) in the input area for the parameter and obtains the print count
	 * Displays the dialog box to allow to confirm to print it or not.<BR>
	 * <BR>
	 * 1.Set the focus for the Consignor code.<BR>
	 * 2.Check for input and the count of print targets.<BR>
	 * 3-1.Displays the dialog box to allow to confirm it if the print target data found.<BR>
	 * <DIR>
	 *   "There are xxx (number) target data. Do you print it?"<BR>
	 * </DIR>
	 * 3-2.If no print target data<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM4362
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
		//#CM4363
		// Input check
		txt_ConsignorCode.validate();
		txt_StartItemCode.validate(false);
		txt_EndItemCode.validate(false);

		//#CM4364
		// the Start Item code is smaller than the End item code
		if (!StringUtil.isBlank(txt_StartItemCode.getText())
			&& !StringUtil.isBlank(txt_EndItemCode.getText()))
		{
			if (txt_StartItemCode.getText().compareTo(txt_EndItemCode.getText()) > 0)
			{
				//#CM4365
				// 6023109=Starting item code must be smaller than the end item code.
				message.setMsgResourceKey("6023109");
				return;
			}
		}

		Connection conn = null;
		try
		{
			//#CM4366
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM4367
			// Set the schedule parameter
			StockControlParameter param = createParameter();

			//#CM4368
			// Start schedule
			WmsScheduler schedule = new ItemWorkingInquirySCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM4369
				// Hit MSG-W0061={0} count(s)<BR>Do you print it out?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM4370
				// Store the fact that the dialog was displayed via the Print button.
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM4371
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM4372
				// Close the connection
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}
	
	}

	//#CM4373
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4374
	/** 
	 * Clicking on the Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 *  <DIR>
	 *  Refer to <CODE>page_Load(ActionEvent e)</CODE>Method in regard with the initial value.
	 *  </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM4375
		// Set the initial value in the input field.
		//#CM4376
		// Consignor code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM4377
		// Start item code
		txt_StartItemCode.setText("");
		//#CM4378
		// End item code
		txt_EndItemCode.setText("");
		//#CM4379
		// Case/Piece division
		rdo_CpfAll.setChecked(true);
		rdo_CptCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM4380
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}
}
//#CM4381
//end of class
