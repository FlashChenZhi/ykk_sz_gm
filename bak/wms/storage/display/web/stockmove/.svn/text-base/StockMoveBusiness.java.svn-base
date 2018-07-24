// $Id: StockMoveBusiness.java,v 1.2 2006/12/07 08:57:20 suresh Exp $

//#CM571144
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.stockmove;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststockmoveusebydate.ListStockMoveUseByDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragestockmove.ListStorageStockMoveBusiness;
import jp.co.daifuku.wms.storage.schedule.StockMoveSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM571145
/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * The stock movement class. <BR>
 * Set the content input from the screen in Parameter.  The schedule does the stock movement setting based on the Parameter. <BR>
 * Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 * and false when the schedule does not do Completed because of the condition error etc.<BR>
 * The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * Moreover, do Commit  of Transaction and Rollback on this screen. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Input button is pressed (<CODE>btn_Input_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 *  Set the content input from the input area in Parameter, and the schedule checks the input condition based on the Parameter. <BR>
 *  Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 *  and false when the schedule does not do Completed because of the condition error etc.<BR>
 *  The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 *  Add information on the input area to Preset area when the result is true. <BR>
 *  Update the data of Preset for the modification in information on the input area when you press the input button after the modify button is pressed. <BR>
 *  <BR>
 * 	[Parameter] *Mandatory input<BR>
 * 	<BR>
 * 	<DIR>
 * 		Worker Code* <BR>
 * 		Password* <BR>
 * 		Consignor Code* <BR>
 * 		Consignor Name <BR>
 * 		Item Code* <BR>
 * 		Item Name <BR>
 * 		Location before movement* <BR>
 * 		Expiry Date <BR>
 * 		Packed qty per case*2 <BR>
 * 		Movement Case qty*3 <BR>
 * 		Movement Piece qty*3 <BR>
 * 		Location after Movement* <BR>
 *		Print the Movement Work list <BR>
 *		<BR>
 *		*2<BR>
 *		MandatoryItem when Movement Case qty is entered<BR>
 *		*3<BR>
 *		Movement Case qty *  Packed qty per case +  Movement Piece qty must be More than 1. <BR>
 * 	</DIR>
 *  <BR>
 *  [Output data] <BR>
 *  <BR>
 * 	<DIR>
 *  	Consignor Code <BR>
 *  	Consignor Name <BR>
 *  	Item Code <BR>
 *  	Item Name <BR>
 *  	Packed qty per case <BR>
 * 		Movement Case qty <BR>
 * 		Movement Piece qty <BR>
 *  	Location before movement <BR>
 * 		Location after movement <BR>
 *  	Expiry Date <BR>
 * 		Last updated date and time <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Movement button is pressed (<CODE>btn_Relocate_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 *  The content input from Preset area is set in Parameter, and the schedule movement stock is done it based on the Parameter. <BR>
 *  Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 *  and false when the schedule does not do Completed because of the condition error etc.<BR>
 *  The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * 	<DIR>
 *   [Parameter] *Mandatory input <BR>
 *   <BR>
 *   <DIR>
 * 		Worker Code*<BR>
 * 		Password*<BR>
 * 		Movement Work list print Flag<BR>
 * 		Preset.Consignor Code<BR>
 * 		Preset.Consignor Name<BR>
 * 		Preset.Item Code<BR>
 * 		Preset.Item Name<BR>
 * 		Preset.Packed qty per case<BR>
 * 		Preset.Movement Case qty<BR>
 * 		Preset.Movement Piece qty<BR>
 * 		Preset.Location before movement<BR>
 * 		Preset.Location after movement<BR>
 * 		Preset.Expiry Date<BR>
 * 		Preset Line No..<BR>
 * 		Terminal No.<BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:20 $
 * @author  $Author: suresh $
 */
public class StockMoveBusiness extends StockMove implements WMSConstants
{		
	//#CM571146
	// Class fields --------------------------------------------------

	//#CM571147
	// Class variables -----------------------------------------------

	//#CM571148
	// Class method --------------------------------------------------

	//#CM571149
	// Constructors --------------------------------------------------

	//#CM571150
	// Public methods ------------------------------------------------

	//#CM571151
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Set Preset Line No in ViewState information. <BR>
	 *    2.Invalidate button (Movement , Clear list) of Preset. <BR>
	 *    3.Initialize the input area. <BR>
	 * <BR>
	 *    Item Name[Initial value]<BR>
	 *    <DIR>
	 * 		Worker Code[None] <BR>
	 * 		Password[None] <BR>
	 * 		Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 		Consignor Name*[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 		Item Code[None] <BR>
	 * 		Item Name*[None] <BR>
	 * 		Location before movement[None] <BR>
	 * 		Expiry Date[None] <BR>
	 * 		Packed qty per case[None] <BR>
	 * 		Movement Case qty[None] <BR>
	 * 		Movement Piece qty[None] <BR>
	 * 		Location after movement[None] <BR>
	 * 		Print the Movement Work list[Checked] <BR>
	 * 		<BR>
	 * 		*<BR>
	 * 		Make it unuseable when there is a stock package. <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		//#CM571152
		// To decide whether Status is Modify when the Modify button is pressed, Preset Line No is set in ViewState.
		//#CM571153
		// (Default:-1)
		this.getViewState().setInt("LineNo", -1);

		//#CM571154
		// Button not pressed properly
		//#CM571155
		// Movement Button
		btn_Relocate.setEnabled(false);
		//#CM571156
		// Clear list Button
		btn_ListClear.setEnabled(false);

		//#CM571157
		// Display initial. 
		setFirstDisp();
	}

	//#CM571158
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * Outline:Display the Confirmation dialog.<BR>
	 * Or, Set the cursor in Worker Code. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM571159
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM571160
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM571161
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM571162
		// Confirmation Dialog when Movement Button is pressed "Do Movement?"
		btn_Relocate.setBeforeConfirm("MSG-W0025");

		//#CM571163
		// Confirmation Dialog when Start Clear list button is pressed  "Do you clear the list?"
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM571164
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM571165
	/**
	 * When returning from the pop up window, this Method is called. <BR>
	 * Override the <CODE>page_DlgBack</CODE> set in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline:Acquire and set Return Data of the retrieval screen. <BR>
	 * <BR>
	 * <DIR>
	 *      1.Acquire the value returned from the retrieval screen of pop up. <BR>
	 *      2.Set it on the screen when the value is not empty. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM571166
		// Parameter selected from list box is acquired. 
		//#CM571167
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM571168
		// Consignor Name
		String consignorname = param.getParameter(ListStorageConsignorBusiness.CONSIGNORNAME_KEY);
		//#CM571169
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM571170
		// Item Name
		String itemname = param.getParameter(ListStorageItemBusiness.ITEMNAME_KEY);
		//#CM571171
		// Location before movement
		String sourcelocationno = param.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		//#CM571172
		// Expiry Date
		String usebydate = param.getParameter(ListStockMoveUseByDateBusiness.USEBYDATE_KEY);	
		//#CM571173
		// Location after movement
		String destlocationno = param.getParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY);
		//#CM571174
		// Packed qty per case
		String enteringQty = param.getParameter(ListStorageStockMoveBusiness.ENTERING_KEY);
		//#CM571175
		// Possible Movement Case qty
		String caseQty = param.getParameter(ListStorageStockMoveBusiness.CASEQTY_KEY);
		//#CM571176
		// Possible Movement Piece qty
		String pieceQty = param.getParameter(ListStorageStockMoveBusiness.PIECEQTY_KEY);

		//#CM571177
		// Set the value when it is not empty. 
		//#CM571178
		// Consignor Code
		//#CM571179
		// Consignor Name
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
			txt_ConsignorName.setText(consignorname);
		}
		//#CM571180
		// Item Code
		//#CM571181
		// Item Name
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
			txt_ItemName.setText(itemname);
		}
		//#CM571182
		// Location before movement
		if (!StringUtil.isBlank(sourcelocationno))
		{
			txt_MoveFromLocation.setText(sourcelocationno);
		}
		//#CM571183
		// Expiry Date
		if (!StringUtil.isBlank(usebydate))
		{
			txt_UseByDate.setText(usebydate);
		}
		//#CM571184
		// Location after movement
		if (!StringUtil.isBlank(destlocationno))
		{
			txt_MoveToLocation.setText(destlocationno);
		}
		//#CM571185
		// Packed qty per case
		if (!StringUtil.isBlank(enteringQty))
		{
			txt_CaseEntering.setText(enteringQty);
		}
		//#CM571186
		// Possible Movement Case qty
		if (!StringUtil.isBlank(caseQty))
		{
		    lbl_JavaSetMoveCaseQty.setText(caseQty);
		}
		//#CM571187
		// Possible Movement Piece qty
		if (!StringUtil.isBlank(pieceQty))
		{
		    lbl_JavaSetMovePeaceQty.setText(pieceQty);
		}
	}

	//#CM571188
	// Package methods -----------------------------------------------

	//#CM571189
	// Protected methods ---------------------------------------------
	//#CM571190
	/**
	 * Do the Input Check of Input area.<BR>
	 * Set Message and Return false when abnormality is found. <BR>
	 * <BR>
	 * @return Result of Input Check (true:Normal false:Abnormal) 
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		//#CM571191
		//Use it because the Consignor Name input field is invalid at the time of having the stock control. 
		if (!txt_ConsignorName.getReadOnly())
		{
			if (!checker.checkContainNgText(txt_ConsignorCode))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_ConsignorName))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_ItemCode))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_ItemName))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_MoveFromLocation))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_UseByDate))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
			if (!checker.checkContainNgText(txt_MoveToLocation))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
		}
		else
		{
			if (!checker.checkContainNgText(txt_MoveToLocation))
			{
				message.setMsgResourceKey(checker.getMessage());
				return false;	
			}
		}
		return true;
		
	}

	//#CM571192
	// Private methods -----------------------------------------------
	//#CM571193
	/**
	 * Method to acquire Consignor from the schedule.  <BR>
	 * <BR>
	 * Outline : Set Consignor Code and Consignor Name acquired from the schedule in Input area.  <BR>
	 * Do not Set Consignor Code and Consignor Name when the schedule result is null and Clear. <BR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setConsignor() throws Exception
	{
		Connection conn = null;
		try
		{
			txt_ConsignorCode.setText("");
			txt_ConsignorName.setText("");
			
			//#CM571194
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM571195
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StockMoveSCH();
			param = (StorageSupportParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			else
			{
				txt_ConsignorCode.setText("");
				txt_ConsignorName.setText("");
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM571196
			// Close the connection
			if (conn != null)
			{
				conn.rollback();
				conn.close();
			}
		}
	}
	
	//#CM571197
	/**
	 * Initialize the input area. <BR>
	 * <BR>
	 * Outline : Set Initial value to each Item of Input area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set Initial value in each Item. <BR>
	 * 		<BR>
	 *    	Item Name[Initial value]<BR>
	 *		<BR>
	 *    	<DIR>
	 * 			Worker Code[As it is] <BR>
	 * 			Password[As it is] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Consignor Name*1[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Item Code[None] <BR>
	 * 			Item Name*1[None] <BR>
	 * 			Location before movement[None] <BR>
	 * 			Expiry Date[None] <BR>
	 * 			Packed qty per case[None] <BR>
	 * 			Movement Case qty[None] <BR>
	 * 			Movement Piece qty[None] <BR>
	 * 			Location after movement[None] <BR>
	 * 			Print the Movement Work list[Checked] <BR>
	 * 			<BR>
	 * 			*1<BR>
	 * 			<DIR>
	 * 				Make it unuseable when there is a stock package. <BR>
	 * 			</DIR>
	 *   	</DIR>
	 *   	<BR>
	 *   	2.Make Consignor Name and Item Name unuseable when there is a stock package. <BR>
	 *   	3.Set the Expiry Date management flag in ViewState information. <BR>
	 * </DIR>
	 * <BR>
	 * @throws Exception It reports on all exceptions.  
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM571198
			// Consignor Code, Consignor Name
			setConsignor();
			//#CM571199
			// Item Code
			txt_ItemCode.setText("");
			//#CM571200
			// Item Name
			txt_ItemName.setText("");
			//#CM571201
			// Location before movement
			txt_MoveFromLocation.setText("");
			//#CM571202
			// Expiry Date
			txt_UseByDate.setText("");
			//#CM571203
			// Packed qty per case
			txt_CaseEntering.setText("");
			//#CM571204
			// Movement Case qty
			txt_MovCaseQty.setText("");
			//#CM571205
			// Movement Piece qty
			txt_MovPieseQty.setText("");
			//#CM571206
			// Location after movement
			txt_MoveToLocation.setText("");
			//#CM571207
			// Print the Movement information list. 
			chk_CommonUse.setChecked(true);
			
			//#CM571208
			// Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StockMoveSCH();

			StorageSupportParameter param = (StorageSupportParameter) schedule.initFind(conn, null);

			//#CM571209
			// Make Consignor Name and Item Name unuseable when there is a stock package. 
			if (param.getStockPackageFlag())
			{
				txt_ConsignorName.setReadOnly(true);
				txt_ItemName.setReadOnly(true);
			}
			
			//#CM571210
			// The Expiry Date management flag is set in viewState. 
			this.viewState.setBoolean("USEBYDATE", param.getUseByDateFlag());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM571211
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

	//#CM571212
	/** Method which sets the data of Preset area in the Parameter array. <BR>
	 * <BR>
	 * Outline:Set the data of Preset area in the Parameter array, and return it. <BR>
	 * Return null when the set data does not exist. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set Preset data of All in case of new input or Movement Button pressing. (Preset Line No. for correction = -1)<BR>
	 * 		2.Set Preset data in case of the input data under the correction except the line for the correction. <BR>
	 * 		<DIR>
	 *   		[Return Data]<BR>
	 *   		<DIR>
	 * 				Worker Code<BR>
	 * 				Password<BR>
	 * 				Movement Work list print Flag<BR>
	 * 				Preset.Consignor Code<BR>
	 * 				Preset.Consignor Name<BR>
	 * 				Preset.Item Code<BR>
	 * 				Preset.Item Name<BR>
	 * 				Preset.Packed qty per case<BR>
	 * 				Preset.Movement Case qty<BR>
	 * 				Preset.Movement Piece qty<BR>
	 * 				Preset.Location before movement<BR>
	 * 				Preset.Location after movement<BR>
	 * 				Preset.Expiry Date<BR>
	 * 				Preset Line No..<BR>
	 * 				Terminal No.<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param lineno int Line No. for modification
	 * @return <CODE>StorageSupportParameter</CODE> array which stores information on Preset area. 
	 * @throws Exception It reports on all exceptions.  
	 */
	private StorageSupportParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_StockMove.getMaxRows(); i++)
		{
			//#CM571213
			// Exclude the line for the modification. 
			if (i == lineno)
			{
				continue;
			}

			//#CM571214
			// Line specification
			lst_StockMove.setCurrentRow(i);

			//#CM571215
			// Set in schedule Parameter.
			StorageSupportParameter param = new StorageSupportParameter();
			//#CM571216
			// Input area information
			//#CM571217
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM571218
			// Password
			param.setPassword(txt_Password.getText());
			//#CM571219
			// Right or wrong Flag of Work list issue
			param.setMoveWorkListFlag(chk_CommonUse.getChecked());
			//#CM571220
			// Preset area information
			//#CM571221
			// Consignor Code
			param.setConsignorCode(lst_StockMove.getValue(3));
			//#CM571222
			// Item Code
			param.setItemCode(lst_StockMove.getValue(4));
			//#CM571223
			// Packed qty per case
			param.setEnteringQty(Formatter.getInt(lst_StockMove.getValue(5)));
			//#CM571224
			// Movement Case qty
			param.setMovementCaseQty(Formatter.getLong(lst_StockMove.getValue(6)));
			//#CM571225
			// Location before movement
			param.setSourceLocationNo(lst_StockMove.getValue(7));
			//#CM571226
			// Location after movement
			param.setDestLocationNo(lst_StockMove.getValue(8));
			//#CM571227
			// Expiry Date
			param.setUseByDate(lst_StockMove.getValue(9));
			//#CM571228
			// Consignor Name
			param.setConsignorName(lst_StockMove.getValue(10));
			//#CM571229
			// Item Name
			param.setItemName(lst_StockMove.getValue(11));
			//#CM571230
			// Movement Piece qty
			param.setMovementPieceQty(Formatter.getLong(lst_StockMove.getValue(12)));

			//#CM571231
			// Line No. is maintained. 
			param.setRowNo(i);
			
			//#CM571232
			// Terminal No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM571233
			// The value is set if there is set Preset data. 
			StorageSupportParameter[] listparam = new StorageSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM571234
			// Null is set if there is no set Preset data. 
			return null;
		}
	}

	//#CM571235
	/**
	 * Method which sets the value in Preset area. <BR>
	 * <BR>
	 * Outline:Set the value in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set balloon information. <BR>
	 * 		2.Set data in each Item of the list cell. <BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			[List cell row number list]<BR>
	 * 			<DIR>
	 * 				3.Consignor Code<BR>
	 * 				4.Item Code<BR>
	 * 				5.Packed qty per case<BR>
	 * 				6.Movement Case qty<BR>
	 * 				7.Location before movement<BR>
	 * 				8.Location after movement<BR>
	 * 				9.Expiry Date<BR>
	 * 				10.Consignor Name<BR>
	 * 				11.Item Name<BR>
	 * 				12.Movement Piece qty<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param pConsignorName String Consignor Name up to date. 
	 * @param pItemName String Item Name up to date. 
	 * @throws Exception It reports on all exceptions.  
	 */
	private void setList(String pConsignorName, String pItemName) throws Exception
	{
		String consignorName = "";
		String itemName = "";
		//#CM571236
		// Consignor Name
		if (!StringUtil.isBlank(pConsignorName))
		{
			consignorName = pConsignorName;
		}
		else
		{
			consignorName = txt_ConsignorName.getText();
		}
		//#CM571237
		// Item Name
		if (!StringUtil.isBlank(pItemName))
		{
			itemName = pItemName;
		}
		else
		{
			itemName = txt_ItemName.getText();

		}
		//#CM571238
		// Data for ToolTip is edited. 
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM571239
		// Consignor Name
		toolTip.add(DisplayText.getText("LBL-W0026"), consignorName);
		//#CM571240
		// Item Name
		toolTip.add(DisplayText.getText("LBL-W0103"), itemName);
		//#CM571241
		// Location before movement
		toolTip.add(DisplayText.getText("LBL-W0073"), txt_MoveFromLocation.getText());
		//#CM571242
		// Location after movement
		toolTip.add(DisplayText.getText("LBL-W0268"), txt_MoveToLocation.getText());
		//#CM571243
		// Expiry Date
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());

		//#CM571244
		//Set the tool tip in Current line. 
		lst_StockMove.setToolTip(lst_StockMove.getCurrentRow(), toolTip.getText());

		//#CM571245
		// Consignor Code
		lst_StockMove.setValue(3, txt_ConsignorCode.getText());
		//#CM571246
		// Item Code
		lst_StockMove.setValue(4, txt_ItemCode.getText());
		//#CM571247
		// Packed qty per case
		lst_StockMove.setValue(5, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));
		//#CM571248
		// Movement Case qty
		lst_StockMove.setValue(6, WmsFormatter.getNumFormat(txt_MovCaseQty.getLong()));
		//#CM571249
		// Location before movement
		lst_StockMove.setValue(7, txt_MoveFromLocation.getText());
		//#CM571250
		// Location after movement
		lst_StockMove.setValue(8, txt_MoveToLocation.getText());
		//#CM571251
		// Expiry Date
		lst_StockMove.setValue(9, txt_UseByDate.getText());
		//#CM571252
		// Consignor Name
		lst_StockMove.setValue(10, consignorName);
		//#CM571253
		// Item Name
		lst_StockMove.setValue(11, itemName);
		//#CM571254
		// Movement Piece qty
		lst_StockMove.setValue(12, WmsFormatter.getNumFormat(txt_MovPieseQty.getLong()));
	}

	//#CM571255
	// Event handler methods -----------------------------------------
	//#CM571256
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571257
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571258
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571259
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
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM571260
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571261
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571262
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571263
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571264
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571265
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571266
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571267
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571268
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571269
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571270
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571271
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571272
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571273
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571274
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571275
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571276
	/** 
	 * It is called when the retrieval of Consignor Code button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Consignor list box by the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PSearchConsignor_Click(ActionEvent e) throws Exception
	{
		//#CM571277
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571278
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM571279
		// Retrieval flag
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STORAGE_STOCK);
		//#CM571280
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM571281
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do",
			param,
			"/progress.do");
	}

	//#CM571282
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571283
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571284
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571285
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571286
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571287
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571288
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571289
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571290
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571291
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571292
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571293
	/** 
	 * It is called when the retrieval button of Item Code is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the item list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Item Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{
		//#CM571294
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571295
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM571296
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM571297
		// Retrieval flag
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_STORAGE_STOCK);
		//#CM571298
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM571299
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageitem/ListStorageItem.do", param, "/progress.do");
	}

	//#CM571300
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PStockSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571301
	/** 
	 * When RetrievalButton of Stock List is pressed, it is called. <BR>
	 * <BR>
	 * Outline:Set Search condition in Parameter, and display the Stock List box with the Search condition. <BR>
	 * <BR>
	 * <DIR>
	 * 		[Parameter] <BR>
	 * 		<DIR>
	 * 			Consignor Code <BR>
	 * 			Consignor Name <BR>
	 * 			Item Code <BR>
	 * 			Location before movement <BR>
	 * 			Expiry Date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PStockSearch_Click(ActionEvent e) throws Exception
	{
		//#CM571302
		// Set the Search condition to the Stock List screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571303
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM571304
		// Consignor Name
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORNAME_KEY,
			txt_ConsignorName.getText());
		//#CM571305
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM571306
		// Location before movement
		param.setParameter(
			ListInventoryLocationBusiness.STARTLOCATION_KEY,
			txt_MoveFromLocation.getText());
		//#CM571307
		// Expiry Date
		param.setParameter(ListStockMoveUseByDateBusiness.USEBYDATE_KEY, txt_UseByDate.getText());
		//#CM571308
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM571309
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststoragestockmove/ListStorageStockMove.do",
			param,
			"/progress.do");
	}

	//#CM571310
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571311
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571313
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromMoveLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MoveFromLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MoveFromLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MoveFromLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MoveFromLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchFrmMovLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571321
	/** 
	 * When RetrievalButton of Location before movement is pressed, it is called. <BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Item Code <BR>
	 * 		 Location before movement <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchFrmMovLct_Click(ActionEvent e) throws Exception
	{
		//#CM571322
		// Set the Search condition to the Location list screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571323
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM571324
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM571325
		// Location before movement
		param.setParameter(
			ListInventoryLocationBusiness.LOCATION_KEY,
			txt_MoveFromLocation.getText());
		//#CM571326
		// Retrieval flag
		param.setParameter(
			ListInventoryLocationBusiness.SEARCHLOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_STOCK);
		//#CM571327
		// Retrieval flag
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
		StorageSupportParameter.RANGE_START);
		//#CM571328
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM571329
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do",
			param,
			"/progress.do");
	}

	//#CM571330
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571331
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571332
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571333
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571335
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchUseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571336
	/** 
	 * When Retrieval Button of Expiry Date is pressed, it is called. <BR>
	 * <BR>
	 * Outline:Set Search condition in Parameter, and display the Expiry Date list box with the Search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Item Code <BR>
	 * 		 Location before movement <BR>
	 * 		 Expiry Date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchUseByDate_Click(ActionEvent e) throws Exception
	{
		//#CM571337
		// Set the Search condition to the Expiry date list screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571338
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM571339
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM571340
		// Location before movement
		param.setParameter(
			ListInventoryLocationBusiness.STARTLOCATION_KEY,
			txt_MoveFromLocation.getText());
		//#CM571341
		// Expiry Date
		param.setParameter(ListStockMoveUseByDateBusiness.USEBYDATE_KEY, txt_UseByDate.getText());
		//#CM571342
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM571343
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststockmoveusebydate/ListStockMoveUseByDate.do",
			param,
			"/progress.do");
	}

	//#CM571344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MoveCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovCaseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovCaseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MovePieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovPieseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571354
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovPieseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MovPieseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571356
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ToMoveLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MoveToLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571358
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MoveToLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571359
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MoveToLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571360
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MoveToLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchToMovLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571362
	/** 
	 * When Retrieval Button of Location after movement is pressed, it is called. <BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 * 		 Location after movement <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchToMovLct_Click(ActionEvent e) throws Exception
	{
		//#CM571363
		// Set the Search condition to the Location list screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571364
		// Location after movement
		param.setParameter(
			ListInventoryLocationBusiness.LOCATION_KEY,
			txt_MoveToLocation.getText());
		//#CM571365
		// Retrieval flag
		param.setParameter(
			ListInventoryLocationBusiness.SEARCHLOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_STOCK);
		//#CM571366
		// Retrieval flag
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
		StorageSupportParameter.RANGE_END);
		//#CM571367
		// Retrieval flag
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
		StorageSupportParameter.RANGE_END);
		//#CM571368
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM571369
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do",
			param,
			"/progress.do");
	}

	//#CM571370
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MoveWorkingList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571371
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571372
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM571373
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571374
	/** 
	 * It is called when the input button is pressed.<BR>
	 * <BR>
	 * Outline : Check input Item of Input area, and display it in Preset. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Check Input area input Item. (Mandatory input, Number of characters, Character attribute)<BR>
	 *   2.Start Schedule.<BR>
	 * 	 <DIR>
	 *   	[Parameter] *Mandatory input<BR>
	 *   	<DIR>
	 * 			Worker Code* <BR>
	 * 			Password* <BR>
	 * 			Consignor Code* <BR>
	 * 			Consignor Name <BR>
	 * 			Item Code* <BR>
	 * 			Item Name <BR>
	 * 			Location before movement* <BR>
	 * 			Expiry Date <BR>
	 * 			Packed qty per case*2 <BR>
	 * 			Movement Case qty*3 <BR>
	 * 			Movement Piece qty*3 <BR>
	 * 			Location after movement* <BR>
	 *			<BR>
	 *			*2<BR>
	 *			MandatoryItem when Movement Case qty is entered<BR>
	 *			*3<BR>
	 *			Movement Case qty *  Packed qty per case +  Movement Piece qty must be More than 1. <BR>
	 *	 	</DIR>
	 *   </DIR>
	 *   <BR>
	 *   4.If the result of the schedule is true, Add it to Preset area. <BR>
	 *     Update the data of Preset for the modification in information on the input area when you press the input button after the modify button is pressed. <BR>
	 *   5.If the result of the schedule is true, Set Preset Line No. of ViewState to Default(Initial value:-1). <BR>
	 *   6.Make Movement Button and Clear list Button effective If the result of the schedule is true. <BR>
	 *   7.Output the Message acquired from the schedule to the screen. <BR>
	 *   <BR>
	 * </DIR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM571375
		// Mandatory Input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_ItemCode.validate();
		txt_MoveFromLocation.validate();
		txt_MoveToLocation.validate();

		//#CM571376
		// Pattern match character
		txt_ConsignorName.validate(false);
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_MovCaseQty.validate(false);
		txt_MovPieseQty.validate(false);
		
		//#CM571377
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM571378
			// Set in schedule Parameter.
			//#CM571379
			// Input area
			StorageSupportParameter param = new StorageSupportParameter();
			//#CM571380
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM571381
			// Password
			param.setPassword(txt_Password.getText());
			//#CM571382
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM571383
			// Consignor Name
			param.setConsignorName(txt_ConsignorName.getText());
			//#CM571384
			// Item Code
			param.setItemCode(txt_ItemCode.getText());
			//#CM571385
			// Item Name
			param.setItemName(txt_ItemName.getText());
			//#CM571386
			// Location before movement
			param.setSourceLocationNo(txt_MoveFromLocation.getText());
			//#CM571387
			// Expiry Date		
			param.setUseByDate(txt_UseByDate.getText());
			//#CM571388
			// Packed qty per case
			param.setEnteringQty(txt_CaseEntering.getInt());
			//#CM571389
			// Movement Case qty
			param.setMovementCaseQty(txt_MovCaseQty.getLong());
			//#CM571390
			// Movement Piece qty
			param.setMovementPieceQty(txt_MovPieseQty.getLong());
			//#CM571391
			// Location after movement
			param.setDestLocationNo(txt_MoveToLocation.getText());

			//#CM571392
			// Set in schedule Parameter.
			//#CM571393
			// Preset area
			StorageSupportParameter[] listparam = null;

			//#CM571394
			// Null is set if there is no data in Preset. 
			if (lst_StockMove.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM571395
				// If data exists, the value is set. 
				listparam = setListParam(this.getViewState().getInt("LineNo"));
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StockMoveSCH();
			//#CM571396
			// Do the input check
			if (schedule.check(conn, param, listparam))
			{
				String consignorName = "";
				String itemName = "";
				//#CM571397
				// Retrieves stock when there is a stock package. ( Qty check which can be drawn and acquired Description)
				if (txt_ConsignorName.getReadOnly() && txt_ItemName.getReadOnly())
				{
					StorageSupportParameter[] viewParam =
						(StorageSupportParameter[]) schedule.query(conn, param);

					//#CM571398
					// Do not process it when input data is illegal. 
					if(viewParam == null || viewParam.length == 0)
					{
						//#CM571399
						// Set Message
						message.setMsgResourceKey(schedule.getMessage());
						return;
					}
					//#CM571400
					//  Maintain Description. 
					consignorName = viewParam[0].getConsignorName();
					itemName = viewParam[0].getItemName();
				}

				//#CM571401
				// Data is set in Preset area. 
				if (this.getViewState().getInt("LineNo") == -1)
				{
					//#CM571402
					// Add to Preset in case of new input. 
					lst_StockMove.addRow();
					lst_StockMove.setCurrentRow(lst_StockMove.getMaxRows() - 1);
					setList(consignorName, itemName);
				}
				else
				{
					//#CM571403
					// The data of the line for the modification is updated in case of the input data under the modification.
					lst_StockMove.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList(consignorName, itemName);
					//#CM571404
					// Return it based on the color of the selection line. 
					lst_StockMove.resetHighlight();
				}

				//#CM571405
				// Return Correction Status to Default
				this.getViewState().setInt("LineNo", -1);

				//#CM571406
				// Enable the button pressing. 
				//#CM571407
				// Movement Button
				btn_Relocate.setEnabled(true);
				//#CM571408
				// Clear list Button
				btn_ListClear.setEnabled(true);
			}

			//#CM571409
			// Set Message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM571410
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

	//#CM571411
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571412
	/** 
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * Outline : Clear the Input area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set Initial value in each Item. <BR>
	 * 		<BR>
	 *    	Item Name[Initial value]<BR>
	 *		<BR>
	 *    	<DIR>
	 * 			Worker Code[As it is] <BR>
	 * 			Password[As it is] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Consignor Name*[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Item Code[None] <BR>
	 * 			Item Name*[None] <BR>
	 * 			Location before movement[None] <BR>
	 * 			Expiry Date[None] <BR>
	 * 			Packed qty per case[None] <BR>
	 * 			Movement Case qty[None] <BR>
	 * 			Movement Piece qty[None] <BR>
	 * 			Location after movement[None] <BR>
	 * 			Print the Movement Work list[Checked] <BR>
	 * 			<BR>
	 * 			*<BR>
	 * 			<DIR>
	 * 				Make it unuseable when there is a stock package. <BR>
	 * 			</DIR>
	 *   	</DIR>
	 *   	<BR>
	 *   	2.Make Consignor Name and Item Name unuseable when there is a stock package. <BR>
	 *   	3.Set the Expiry Date management flag in ViewState information. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM571413
		// Do the Clear Process.
		setFirstDisp();
	}

	//#CM571414
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Relocate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571415
	/** 
	 * When Movement Button is pressed, it is called. <BR>
	 * <BR>
	 * Outline:Do Stock Movement by information on Preset area. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box and confirm whether to do Movement. <BR>
	 *    <DIR>
	 * 		[Confirmation Dialog Cancel]<BR>
	 *			<DIR>
	 *				Do Nothing.
	 *			</DIR>
	 * 		[Confirmation Dialog OK]<BR>
	 *			<DIR>
	 *				1.Start Schedule.<BR>
	 *				<BR>
	 *				<DIR>
	 *   				[Parameter] *Mandatory input<BR>
	 * 					<DIR>
	 * 						Worker Code*<BR>
	 * 						Password*<BR>
	 * 						Movement Work list print Flag<BR>
	 * 						Preset.Consignor Code<BR>
	 * 						Preset.Consignor Name<BR>
	 * 						Preset.Item Code<BR>
	 * 						Preset.Item Name<BR>
	 * 						Preset.Packed qty per case<BR>
	 * 						Preset.Movement Case qty<BR>
	 * 						Preset.Movement Piece qty<BR>
	 * 						Preset.Location before movement<BR>
	 * 						Preset.Location after movement<BR>
	 * 						Preset.Expiry Date<BR>
	 * 						Preset Line No.<BR>
	 * 						Terminal No.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.When the result of the schedule is true, Clears Movement information on Input area and Preset. <BR>
	 *				3.Default in Preset Line No of ViewState : Set -1.<BR>
	 *    			Output the Message acquired from the schedule to the screen in case of false.  <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_Relocate_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM571416
			// Set in schedule Parameter.
			StorageSupportParameter[] param = null;
			//#CM571417
			// All data of Preset area is set. 
			param = setListParam(-1);

			//#CM571418
			// Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StockMoveSCH();

			if (!schedule.startSCH(conn, param))
			{
				conn.rollback();
				//#CM571419
				// Set Message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();
				//#CM571420
				// Set Message
				message.setMsgResourceKey(schedule.getMessage());
				//#CM571421
				// Clear list
				lst_StockMove.clearRow();

				//#CM571422
				// Return Correction Status to Default
				this.getViewState().setInt("LineNo", -1);

				//#CM571423
				// Button not pressed properly
				//#CM571424
				// Movement Button
				btn_Relocate.setEnabled(false);
				//#CM571425
				// Clear list Button
				btn_ListClear.setEnabled(false);

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM571426
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

	//#CM571427
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571428
	/** 
	 * It is called when the clear list button is pressed.<BR>
	 * <BR>
	 * Outline:Clears all display information on Preset. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box, and confirm whether to Clear Preset information. <BR>
	 *    <DIR>
	 * 		[Confirmation Dialog Cancel]<BR>
	 *			<DIR>
	 *				Do Nothing.
	 *			</DIR>
	 * 		[Confirmation Dialog OK]<BR>
	 *			<DIR>
	 *				1.Clears all display information on Preset. <BR>
	 *				2.Invalidate Movement Button and Clear list Button. <BR>
	 * 				3.Set Preset Line No. of ViewState to Default(Initial value:-1). <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM571429
		// Delete all rows
		lst_StockMove.clearRow();

		//#CM571430
		// Button not pressed properly
		//#CM571431
		// Movement Button
		btn_Relocate.setEnabled(false);
		//#CM571432
		// Clear list Button
		btn_ListClear.setEnabled(false);

		//#CM571433
		// Return Correction Status to Default
		this.getViewState().setInt("LineNo", -1);
	}

	//#CM571434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockMove_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockMove_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockMove_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571437
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockMove_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM571438
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockMove_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571439
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockMove_Change(ActionEvent e) throws Exception
	{
	}

	//#CM571440
	/** 
	 * It is called when the cancel and modify button are pressed.<BR>
	 * <BR>
	 * Cancel button Outline:Clears pertinent data of Preset. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box, and confirm whether to Clear Preset information. <BR>
	 *    <DIR>
	 * 		[Confirmation Dialog Cancel]<BR>
	 *			<DIR>
	 *				Do Nothing.
	 *			</DIR>
	 * 		[Confirmation Dialog OK]<BR>
	 *			<DIR>
	 *				1.Clears pertinent data of Preset. <BR>
	 * 				2.Set Preset Line No. of ViewState to Default(Initial value:-1). <BR>
	 *              3.Invalidate Movement Button and Clear list Button when Preset information does not exist. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * Modification button Outline:Make pertinent data of Preset modification Status. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Display Selection information in upper Input area. <BR>
	 * 		2.Make the selected information part light yellow. <BR>
	 * 		3.Set the line to Preset Line No of ViewState now. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void lst_StockMove_Click(ActionEvent e) throws Exception
	{
		//#CM571441
		// When Cancel button is clicked
		if (lst_StockMove.getActiveCol() == 1)
		{
			//#CM571442
			// List deletion
			lst_StockMove.removeRow(lst_StockMove.getActiveRow());

			//#CM571443
			// Invalidate Movement Button and Clear list Button when Preset information does not exist. 
			//#CM571444
			// Null is set if there is no data in Preset. 
			if (lst_StockMove.getMaxRows() == 1)
			{
				//#CM571445
				// Button not pressed properly
				//#CM571446
				// Movement Button
				btn_Relocate.setEnabled(false);
				//#CM571447
				// Clear list Button
				btn_ListClear.setEnabled(false);
			}

			//#CM571448
			// Return Correction Status to Default
			this.getViewState().setInt("LineNo", -1);

			//#CM571449
			// Return it based on the color of the selection line. 
			lst_StockMove.resetHighlight();
		}
		//#CM571450
		// When Modify button is clicked (Execute Modify)
		else if (lst_StockMove.getActiveCol() == 2)
		{
			//#CM571451
			// A present line is set. 
			lst_StockMove.setCurrentRow(lst_StockMove.getActiveRow());
			//#CM571452
			// Consignor Code
			txt_ConsignorCode.setText(lst_StockMove.getValue(3));
			//#CM571453
			// Item Code
			txt_ItemCode.setText(lst_StockMove.getValue(4));
			//#CM571454
			// Packed qty per case
			txt_CaseEntering.setText(lst_StockMove.getValue(5));
			//#CM571455
			// Movement Case qty
			txt_MovCaseQty.setText(lst_StockMove.getValue(6));
			//#CM571456
			// Location before movement
			txt_MoveFromLocation.setText(lst_StockMove.getValue(7));
			//#CM571457
			// Location after movement
			txt_MoveToLocation.setText(lst_StockMove.getValue(8));
			//#CM571458
			// Expiry Date
			txt_UseByDate.setText(lst_StockMove.getValue(9));
			//#CM571459
			// Consignor Name
			txt_ConsignorName.setText(lst_StockMove.getValue(10));
			//#CM571460
			// Item Name
			txt_ItemName.setText(lst_StockMove.getValue(11));
			//#CM571461
			// Movement Piece qty
			txt_MovPieseQty.setText(lst_StockMove.getValue(12));

			//#CM571462
			// Preserved in ViewState. 
			//#CM571463
			// To decide whether Status is Modify when the Modify button is pressed, Preset Line No is set in ViewState.
			this.getViewState().setInt("LineNo", lst_StockMove.getActiveRow());

			//#CM571464
			//Change the modification line to yellow. 
			lst_StockMove.setHighlight(lst_StockMove.getActiveRow());
		}
	}
	//#CM571465
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_StockMove_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MovePossibleCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571467
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetMovePossibleCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571468
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MovePossiblePeaceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571469
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetMovePossiblePeaceQt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571470
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetMoveCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571471
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetMovePeaceQty_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM571472
//end of class
