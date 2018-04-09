// $Id: StationBusiness.java,v 1.3 2006/10/30 06:30:29 suresh Exp $

//#CM54107
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.station;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.StationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.StationParameter;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;

//#CM54108
/**
 * Screen Class of Station setting (Station). 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/30 06:30:29 $
 * @author  $Author: suresh $
 */
public class StationBusiness extends Station implements WMSToolConstants
{
	//#CM54109
	// Class fields --------------------------------------------------
	//#CM54110
	/** 
	 * The key which maintains the value of parents Station. 
	 */
	public static final String PARENTSTATIONNUMBER_KEY = "PARENTSTATIONNUMBER_KEY";
	//#CM54111
	// Class variables -----------------------------------------------

	//#CM54112
	// Class method --------------------------------------------------

	//#CM54113
	// Constructors --------------------------------------------------

	//#CM54114
	// Public methods ------------------------------------------------
	//#CM54115
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM54116
		//Set screen Name. 
		lbl_SettingName.setResourceKey("TMEN-W0007-1");
		//#CM54117
		//Set passing to the Help file. 
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0007-1", this.getHttpRequest()));
		setFocus(txt_StNumber);
	}
	//#CM54118
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			Locale locale = this.getHttpRequest().getLocale();
			//#CM54119
			//Clear processing
			btn_Clear_Click(null);
			//#CM54120
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.STATION, Creater.M_CREATE);
			//#CM54121
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			
			//#CM54122
			//<en> Display the pull-down list.</en>
			ToolPulldownData pull = new ToolPulldownData(conn,locale,null);

			//#CM54123
			// Pull down display data (Storage Flag)
			String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "");
			//#CM54124
			// Pull down display data (AGCNo.)
			String[] agc = pull.getGroupControllerPulldownData("");

			//#CM54125
			//Set data in the pull down.
			ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
			ToolPulldownHelper.setPullDown(pul_AGCNo, agc);

			//#CM54126
			//<en>Set the name of the file the data will be saved.</en>
			StationParameter searchParam = new StationParameter();
			searchParam.setFilePath((String)this.getSession().getAttribute("WorkFolder"));

			StationParameter[] array = (StationParameter[])factory.query(conn, locale, searchParam);
			if(array != null)
			{
				for(int i = 0; i < array.length; i++)
				{
					((ToolScheduleInterface)factory).addInitialParameter(array[i]);
				}
			}
			//#CM54127
			//<en> Set the preset data.</en>
			setList(conn, factory);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM54128
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54129
	// Package methods -----------------------------------------------

	//#CM54130
	// Protected methods ---------------------------------------------

	//#CM54131
	// Private methods -----------------------------------------------
	//#CM54132
	/** <en>
	 * Set data to preset area.
	 * @param conn Connection 
	 * @param factory ScheduleInterface 
	 * @throws Exception 
	 </en> */
	private void setList(Connection conn, ScheduleInterface factory) throws Exception
	{
		String warehousenumber = "";
		String stationnumber = "";
		String stationname = "";
		String type = "";
		String aislenumber = "";
		String agcnumber = "";
		String settingtype = "";
		String arrivalcheck = "";
		String loadsizecheck = "";
		String operationdisplay = "";
		String restoringoperation = "";
		String restoringinstruction = "";
		String remove = "";
		String modetype = "";
		String maxinstruction = "";
		String maxpalettequantity = "";
		String parentstationnumber = "";

		//#CM54133
		///Plaque of TOOL TIP
		String title_StationName          = DisplayText.getText("TLBL-W0025");
		String title_SettingType          = DisplayText.getText("TLBL-W0028");
		String title_ArrivalCheck         = DisplayText.getText("TLBL-W0029");
		String title_LoadSizeCheck        = DisplayText.getText("TLBL-W0030");
		String title_OperationDisplay     = DisplayText.getText("TLBL-W0031");
		String title_ReStoringOperation   = DisplayText.getText("TLBL-W0032");
		String title_ReStoringInstruction = DisplayText.getText("TLBL-W0033");
		String title_Remove               = DisplayText.getText("TLBL-W0120");
		
		//#CM54134
		//Delete all the lines
		lst_Station.clearRow();

		//#CM54135
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			StationParameter param = (StationParameter) paramarray[i];
			
			//#CM54136
			//Parameter to be added to List
			warehousenumber = getWHNumber(conn, param.getWareHouseStationNumber());
			stationnumber   = param.getNumber();
			stationname     = param.getName();
			type            = DisplayText.getText("TSTATION", "STATIONTYPE", Integer.toString(param.getType()));
			
			//#CM54137
			//<en>In case the aisle station no. has not been set, set 0 for RM machine no.</en>
			if( param.getAisleStationNumber() == "" )
			{
				aislenumber = "0";
			}
			else{
				aislenumber = getALNumber(conn, param.getAisleStationNumber());
			}
			agcnumber       = Integer.toString(param.getControllerNumber());
			int settype = param.getSettingType();
			if(settype == 512)
			{
				settype = 0;
			}
			else
			{
				settype = 1;
			}
			settingtype          = DisplayText.getText("TSTATION", "SETTINGTYPE", Integer.toString(settype));
			arrivalcheck         = DisplayText.getText("TSTATION", "FLUG", Integer.toString(param.getArrivalCheck()));			
			loadsizecheck        = DisplayText.getText("TSTATION", "FLUG", Integer.toString(param.getLoadSizeCheck()));
			operationdisplay     = DisplayText.getText("TSTATION", "OPERATIONDISPLAY", Integer.toString(param.getOperationDisplay()));
			restoringoperation   = DisplayText.getText("TSTATION", "FLUG", Integer.toString(param.getReStoringOperation()));
			restoringinstruction = DisplayText.getText("TSTATION", "FLUG", Integer.toString(param.getReStoringInstruction()));
			remove               = DisplayText.getText("TSTATION", "REMOVE", Integer.toString(param.getRemove()));
			modetype             = DisplayText.getText("TSTATION", "MODETYPE", Integer.toString(param.getModeType()));
			maxinstruction       = Integer.toString(param.getMaxInstruction());
			maxpalettequantity   = Integer.toString(param.getMaxPaletteQuantity());
			parentstationnumber  = param.getParentNumber();
			
			//#CM54138
			//Line addition
			//#CM54139
			//The final line is acquired. 
			int count = lst_Station.getMaxRows();
			lst_Station.setCurrentRow(count);
			lst_Station.addRow();
			lst_Station.setValue(0, parentstationnumber);
			lst_Station.setValue(3, warehousenumber);
			lst_Station.setValue(4, stationnumber);
			lst_Station.setValue(5, stationname);
			lst_Station.setValue(6, type);
			lst_Station.setValue(7, aislenumber);
			lst_Station.setValue(8, agcnumber);
			lst_Station.setValue(9, modetype);
			lst_Station.setValue(10, maxinstruction);
			lst_Station.setValue(11, maxpalettequantity);

			//#CM54140
			//The character string displayed in TOOL TIP is made. 
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_StationName, stationname);
			toolTip.add(title_SettingType, settingtype);
			toolTip.add(title_ArrivalCheck, arrivalcheck);
			toolTip.add(title_LoadSizeCheck, loadsizecheck);
			toolTip.add(title_OperationDisplay, operationdisplay);
			toolTip.add(title_ReStoringOperation, restoringoperation);
			toolTip.add(title_ReStoringInstruction, restoringinstruction);
			toolTip.add(title_Remove, remove);
			
			//#CM54141
			//Set TOOL TIP. 	
			lst_Station.setToolTip(count, toolTip.getText());
		}
		//#CM54142
		// Display the line under the correction highlighting. 
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_Station.setHighlight(modindex + 1);
		}
	}

	//#CM54143
	/** 
	 * Acquire Warehouse Number from specified Warehouse Station Number. 
	 * @param  conn Connection
	 * @param  whStNo Warehouse Station Number
	 * @return Warehouse Number
	 * @throws Exception
	 */
	private String getWHNumber(Connection conn, String whStNo) throws Exception
	{
		ToolWarehouseSearchKey	wareKey = new ToolWarehouseSearchKey();
		ToolWarehouseHandler	warehd  = new ToolWarehouseHandler(conn);
		wareKey.setWarehouseStationNumber(whStNo);
		Warehouse house [] = (Warehouse[])warehd.find(wareKey);
		if (house.length <= 0 )
		{
			return "*";
		}
		return Integer.toString( house[0].getWarehouseNumber());
	}

	//#CM54144
	/** 
	 * Acquire Aisle Number from specified Aisle Station Number. 
	 * @param  conn Connection
	 * @param  alStNo Aisle Station Number
	 * @return Aisle Number
	 * @throws Exception
	 */
	private String getALNumber(Connection conn, String alStNo) throws Exception
	{
		ToolAisleSearchKey	alKey = new ToolAisleSearchKey();
		ToolAisleHandler	aislehd  = new ToolAisleHandler(conn);
		alKey.setNumber(alStNo);
		Aisle aisle [] = (Aisle[])aislehd.find(alKey);
		if (aisle.length <= 0 )
		{
			return "";
		}
		return Integer.toString( aisle[0].getAisleNumber());
	}

	//#CM54145
	/** 
	 * Acquire Warehouse Station Number from specified Warehouse Number. 
	 * @param  Connection
	 * @param  whNo Warehouse Number
	 * @return Warehouse Station Number
	 * @throws Exception
	 */
	private String getWHSTNumber(Connection conn, String whNo) throws Exception
	{
		ToolWarehouseSearchKey	wareKey = new ToolWarehouseSearchKey();
		ToolWarehouseHandler	warehd  = new ToolWarehouseHandler(conn);
		wareKey.setWarehouseNumber(Integer.parseInt(whNo));
		Warehouse house [] = (Warehouse[])warehd.find(wareKey);
		if (house.length <= 0 )
		{
			return "";
		}
		return house[0].getNumber();
	}

	//#CM54146
	/** 
	 * Acquire Aisle Station Number from specified storage Flag and Aisle Number. 
	 * @param  Connection
	 * @param  whStNo Warehouse Station Number
	 * @param  alNo Aisle Number
	 * @return Aisle Station Number
	 * @throws Exception
	 */
	private String getALSTNumber(Connection conn, String whStNo, String alNo) throws Exception
	{
		ToolAisleSearchKey	alKey = new ToolAisleSearchKey();
		ToolAisleHandler	aislehd  = new ToolAisleHandler(conn);
		alKey.setWareHouseStationNumber(whStNo);
		alKey.setAisleNumber(alNo);
		Aisle aisle [] = (Aisle[])aislehd.find(alKey);
		if (aisle.length <= 0 )
		{
			return "";
		}
		return aisle[0].getNumber();
	}

	//#CM54147
	/** 
	 * Set the value of the radio button when the correction button is pressed. 
	 * @param  param StationParameter
	 * @throws Exception
	 */
	private void setRadioButton(StationParameter param) throws Exception
	{
		//#CM54148
		//Type
		rdo_PrivateStorage.setChecked(false);
		rdo_PrivateRetrieval.setChecked(false);
		rdo_StandShuttleCart.setChecked(false);
		rdo_ConveyorStorageSide.setChecked(false);
		rdo_ConveyorRetrievalSide.setChecked(false);
		switch(param.getType())
		{
			//#CM54149
			//Stock exclusive use
			case StationCreater.TYPE_STORAGE:
				rdo_PrivateStorage.setChecked(true);
				break;
			//#CM54150
			//Picking exclusive use
			case StationCreater.TYPE_RETRIEVAL:
				rdo_PrivateRetrieval.setChecked(true);
				break;
			//#CM54151
			//Self-propelled fixed load receiving stand-truck
			case StationCreater.TYPE_INOUTSTATION:
				rdo_StandShuttleCart.setChecked(true);
				break;
			//#CM54152
			//Character of piece(storage side)
			case StationCreater.TYPE_FREESTORAGE:
				rdo_ConveyorStorageSide.setChecked(true);
				break;
			//#CM54153
			//Character of piece(picking side)
			case StationCreater.TYPE_FREERETRIEVAL:
				rdo_ConveyorRetrievalSide.setChecked(true);
				break;
		}
		//#CM54154
		//Confirmation flag
		rdo_LoadConfirmationSetup.setChecked(false);
		rdo_PrecedenceSetup.setChecked(false);
		switch(param.getSettingType())
		{
			//#CM54155
			//Stock confirmation setting
			case 512:
				rdo_LoadConfirmationSetup.setChecked(true);
				break;
			//#CM54156
			//Initial setting
			case 256:
				rdo_PrecedenceSetup.setChecked(true);
				break;
		}
		//#CM54157
		//Arrival report
		rdo_NotAssignedsArrive.setChecked(false);
		rdo_AssignedArrive.setChecked(false);
		switch(param.getArrivalCheck())
		{
			//#CM54158
			//Not available
			case jp.co.daifuku.wms.asrs.tool.location.Station.NOT_ARRIVALCHECK:
				rdo_NotAssignedsArrive.setChecked(true);
				break;
			//#CM54159
			//Available
			case jp.co.daifuku.wms.asrs.tool.location.Station.ARRIVALCHECK:
				rdo_AssignedArrive.setChecked(true);
				break;
		}
		//#CM54160
		//Mode of packing detector
		rdo_NotAssignedsCarryStyle.setChecked(false);
		rdo_AssignedCarryStyle.setChecked(false);
		switch(param.getLoadSizeCheck())
		{
			//#CM54161
			//Not available
			case jp.co.daifuku.wms.asrs.tool.location.Station.NOT_LOADSIZECHECK:
				rdo_NotAssignedsCarryStyle.setChecked(true);
				break;
			//#CM54162
			//Available
			case jp.co.daifuku.wms.asrs.tool.location.Station.LOADSIZECHECK:
				rdo_AssignedCarryStyle.setChecked(true);
				break;
		}
		//#CM54163
		//Work instruction
		rdo_NotAssignedsWork.setChecked(false);
		rdo_JobsDisplay.setChecked(false);
		rdo_OperationDisplay.setChecked(false);
		switch(param.getOperationDisplay())
		{
			//#CM54164
			//Not available
			case jp.co.daifuku.wms.asrs.tool.location.Station.NOT_OPERATIONDISPLAY:
				rdo_NotAssignedsWork.setChecked(true);
				break;
			//#CM54165
			//Work display
			case jp.co.daifuku.wms.asrs.tool.location.Station.OPERATIONDISPLAY:
				rdo_JobsDisplay.setChecked(true);
				break;
			//#CM54166
			//Work instruction
			case jp.co.daifuku.wms.asrs.tool.location.Station.OPERATIONINSTRUCTION:
				rdo_OperationDisplay.setChecked(true);
				break;
		}
		//#CM54167
		//Restorage work
		rdo_NotAssignedsReStoring.setChecked(false);
		rdo_AssignedReStoring.setChecked(false);
		switch(param.getReStoringOperation())
		{
			//#CM54168
			//It is not.
			case jp.co.daifuku.wms.asrs.tool.location.Station.NOT_CREATE_RESTORING:
				rdo_NotAssignedsReStoring.setChecked(true);
				break;
			//#CM54169
			//It is.
			case jp.co.daifuku.wms.asrs.tool.location.Station.CREATE_RESTORING:
				rdo_AssignedReStoring.setChecked(true);
				break;
		}
		//#CM54170
		//Transportation of re-stock instruction
		rdo_NotAssignedsReStoringCarry.setChecked(false);
		rdo_AssignedReStoringCarry.setChecked(false);
		switch(param.getReStoringInstruction())
		{
			//#CM54171
			//It is not.
			case jp.co.daifuku.wms.asrs.tool.location.Station.AGC_STORAGE_SEND:
				rdo_NotAssignedsReStoringCarry.setChecked(true);
				break;
			//#CM54172
			//It is.
			case jp.co.daifuku.wms.asrs.tool.location.Station.AWC_STORAGE_SEND:
				rdo_AssignedReStoringCarry.setChecked(true);
				break;
		}
		//#CM54173
		//Disbursement
		rdo_NotAssignedsPayOut.setChecked(false);
		rdo_AssignedPayOut.setChecked(false);
		switch(param.getRemove())
		{
			//#CM54174
			//Disbursement is improper.
			case jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_OK:
				rdo_AssignedPayOut.setChecked(true);
				break;
			//#CM54175
			//Disbursement is improper.
			case jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_NG:
				rdo_NotAssignedsPayOut.setChecked(true);
				break;
		}
		//#CM54176
		//Mode switch
		rdo_AWCModeChange.setChecked(false);
		rdo_EquipmentModeChange.setChecked(false);
		rdo_AutoModeChange.setChecked(false);
		rdo_NotAssignedsMode.setChecked(false);
		switch(param.getModeType())
		{
			//#CM54177
			//AWC mode switch
			case jp.co.daifuku.wms.asrs.tool.location.Station.AWC_MODE_CHANGE:
				rdo_AWCModeChange.setChecked(true);
				break;
			//#CM54178
			//Equipment mode switch
			case jp.co.daifuku.wms.asrs.tool.location.Station.AGC_MODE_CHANGE:
				rdo_EquipmentModeChange.setChecked(true);
				break;
			//#CM54179
			//Automatic mode of operation switch
			case jp.co.daifuku.wms.asrs.tool.location.Station.AUTOMATIC_MODE_CHANGE:
				rdo_AutoModeChange.setChecked(true);
				break;
			//#CM54180
			//It is not.
			case jp.co.daifuku.wms.asrs.tool.location.Station.NO_MODE_CHANGE:
				rdo_NotAssignedsMode.setChecked(true);
				break;
		}
	}

	//#CM54181
	/** 
	 * Acquire the value of the selected radiobutton.
	 * @param  param StationParameter
	 * @throws Exception
	 */
	private void getRadioButton(StationParameter param) throws Exception
	{
		//#CM54182
		//Type
		//#CM54183
		//Stock exclusive use
		int type = StationCreater.TYPE_STORAGE;
		RadioButton stationtype = rdo_PrivateStorage.getSelectedItem();
		if (stationtype.getId().equals("rdo_PrivateRetrieval"))
		{
			//#CM54184
			//Delivery exclusive use
			type = StationCreater.TYPE_RETRIEVAL;
		}
		else if(stationtype.getId().equals("rdo_StandShuttleCart"))
		{
			//#CM54185
			//Fixed load receiving stand and self-propelled truck
			type = StationCreater.TYPE_INOUTSTATION;
		}
		else if(stationtype.getId().equals("rdo_ConveyorStorageSide"))
		{
			//#CM54186
			//Receiving of character
			type = StationCreater.TYPE_FREESTORAGE;
		}
		else if(stationtype.getId().equals("rdo_ConveyorRetrievalSide"))
		{
			//#CM54187
			//Delivery of character
			type = StationCreater.TYPE_FREERETRIEVAL;
		}
		param.setType(type);
		//#CM54188
		//Commit flag
		int settingtype = 512;
		RadioButton settingtypenum = rdo_LoadConfirmationSetup.getSelectedItem();
		if (settingtypenum.getId().equals("rdo_PrecedenceSetup"))
		{
			settingtype = 256;
		}
		param.setSettingType(settingtype);
		//#CM54189
		//Arrival report
		//#CM54190
		//Arrival check
		int arrivalcheck = jp.co.daifuku.wms.asrs.tool.location.Station.NOT_ARRIVALCHECK;
		RadioButton arrivalchecknum = rdo_NotAssignedsArrive.getSelectedItem();
		if (arrivalchecknum.getId().equals("rdo_AssignedArrive"))
		{
			//#CM54191
			//Arrival check having
			arrivalcheck = jp.co.daifuku.wms.asrs.tool.location.Station.ARRIVALCHECK;
		}
		param.setArrivalCheck(arrivalcheck);
		//#CM54192
		//Mode of packing detector
		//#CM54193
		//Mode of packing check
		int loadsizecheck = jp.co.daifuku.wms.asrs.tool.location.Station.NOT_LOADSIZECHECK;
		RadioButton loadsizechecknum = rdo_NotAssignedsCarryStyle.getSelectedItem();
		if (loadsizechecknum.getId().equals("rdo_AssignedCarryStyle"))
		{
			//#CM54194
			//Mode of packing check having
			loadsizecheck = jp.co.daifuku.wms.asrs.tool.location.Station.LOADSIZECHECK;
		}
		param.setLoadSizeCheck(loadsizecheck);
		//#CM54195
		//Work instruction
		//#CM54196
		//No work instruction screen
		int operationdisplay = jp.co.daifuku.wms.asrs.tool.location.Station.NOT_OPERATIONDISPLAY;
		RadioButton operationdisplaynum = rdo_NotAssignedsWork.getSelectedItem();
		if (operationdisplaynum.getId().equals("rdo_JobsDisplay"))
		{
			//#CM54197
			//It operates the work display, and there is no End button.
			operationdisplay = jp.co.daifuku.wms.asrs.tool.location.Station.OPERATIONDISPLAY;
		}
		else if (operationdisplaynum.getId().equals("rdo_OperationDisplay"))
		{
			//#CM54198
			//It operates the work instruction, and there is a End button.
			operationdisplay = jp.co.daifuku.wms.asrs.tool.location.Station.OPERATIONINSTRUCTION;
		}
		param.setOperationDisplay(operationdisplay);
		//#CM54199
		//Work of re-stock
		//#CM54200
		//There is not a data of the re-stock making.
		int restoringoperation = jp.co.daifuku.wms.asrs.tool.location.Station.NOT_CREATE_RESTORING;
		RadioButton restoringoperationnum = rdo_NotAssignedsReStoring.getSelectedItem();
		if (restoringoperationnum.getId().equals("rdo_AssignedReStoring"))
		{
			//#CM54201
			//Data of re-stock making
			restoringoperation = jp.co.daifuku.wms.asrs.tool.location.Station.CREATE_RESTORING;
		}
		param.setReStoringOperation(restoringoperation);
		//#CM54202
		//Transportation of re-stock instruction
		//#CM54203
		//It the return automatically stocks it on the AGC side. The transportation instruction is unnecessary.
		int restringinstruction = jp.co.daifuku.wms.asrs.tool.location.Station.AGC_STORAGE_SEND;
		RadioButton restringinstructionnum = rdo_NotAssignedsReStoringCarry.getSelectedItem();
		if (restringinstructionnum.getId().equals("rdo_AssignedReStoringCarry"))
		{
			//#CM54204
			//The transportation instruction necessity on the AWC side.
			restringinstruction = jp.co.daifuku.wms.asrs.tool.location.Station.AWC_STORAGE_SEND;
		}
		param.setReStoringInstruction(restringinstruction);
		//#CM54205
		//Mode switch
		//#CM54206
		//There is no mode switch.
		int modetype = jp.co.daifuku.wms.asrs.tool.location.Station.NO_MODE_CHANGE;
		RadioButton modetypenum = rdo_AWCModeChange.getSelectedItem();
		if (modetypenum.getId().equals("rdo_AWCModeChange"))
		{
			//#CM54207
			//AWC mode switch
			modetype = jp.co.daifuku.wms.asrs.tool.location.Station.AWC_MODE_CHANGE;;
		}
		else if(modetypenum.getId().equals("rdo_EquipmentModeChange"))
		{
			//#CM54208
			//Equipment mode switch
			modetype = jp.co.daifuku.wms.asrs.tool.location.Station.AGC_MODE_CHANGE;;
		}
		else if(modetypenum.getId().equals("rdo_AutoModeChange"))
		{
			//#CM54209
			//Automatic mode of operation switch
			modetype = jp.co.daifuku.wms.asrs.tool.location.Station.AUTOMATIC_MODE_CHANGE;;
		}
		param.setModeType(modetype);
	}
	//#CM54210
	// Event handler methods -----------------------------------------
	//#CM54211
	/**
	 * It is called when Menu button is pressed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM54212
	/** <en>
	 * It is called when an Add button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Add_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM54213
			//Input check
			//#CM54214
			//When pulldown is not normally displayed
			if (pul_StoreAs.getSelectedValue() == null)
			{
				//#CM54215
				//6123100 = There is no warehouse information. Register on the warehouse setting screen.
				message.setMsgResourceKey("6123100");
				return;
			}
			txt_StNumber.validate();
			txt_StationName.validate();
			txt_AisleNumber.validate();
			if (pul_AGCNo.getSelectedValue() == null)
			{
				//#CM54216
				//<en>6123078 = There is no group controller information. </en>
				//<en>Please register in group controller setting screen.</en>
				message.setMsgResourceKey("6123078");
				return;
			}
			txt_MaxInstrucion.validate(false);
			txt_MaxPaletteQuantity.validate(false);
			
			if(Integer.parseInt(txt_AisleNumber.getText()) < 0)
			{
				//#CM54217
				//6123282 = Specify the value of 0 or more for aisle No.
				message.setMsgResourceKey("6123282");
				return;
			}		
			
			//#CM54218
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			
			StationParameter param = new StationParameter();
			String whno = "";
			int employmenttype = Warehouse.OPEN ;
			int contno = 0;
			String alstno = "";
			ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
			ToolWarehouseSearchKey warehousekey = new ToolWarehouseSearchKey();

			if (pul_StoreAs.getSelectedValue() != null )
			{
				//#CM54219
				//<en> If the data exists in pull-down list of strage type,</en>
				whno = getWHSTNumber(conn, pul_StoreAs.getSelectedValue());
				//#CM54220
				//<en>Set the warehouse station no. and conduct the search.</en>
				//<en>Retrieve the operation type from the warehouse instance. If the type is 'closed operation',</en>
				//<en>set 'unavailable for removal' as default setting.</en>
				//#CM54221
				//<en>Acquire the operation type from the CMENJP9321$CM warehouse instance, and set disburse improper in default when the operation type is a close. </en>
			warehousekey.setWarehouseStationNumber(whno);
				Warehouse[] whArray = (Warehouse[])warehousehandle.find(warehousekey);
				employmenttype = whArray[0].getEmploymentType() ;
				if(employmenttype == Warehouse.CLOSE)
				{
					//#CM54222
					//It is possible to disburse it.
					param.setRemove(jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_NG);
				}
				else
				{
					//#CM54223
					//It is possible to disburse it.
					int payout = jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_NG;
					RadioButton rdo = rdo_NotAssignedsPayOut.getSelectedItem();
					if (rdo.getId().equals("rdo_AssignedPayOut"))
					{
						//#CM54224
						//It is possible to disburse it.
						payout = jp.co.daifuku.wms.asrs.tool.location.Station.PAYOUT_OK;
					}
					//#CM54225
					//<en>Input value</en>
					param.setRemove(payout);
				}
			}	
			if (pul_AGCNo.getSelectedValue() != null )
			{
				//#CM54226
				//<en> If the data exists in AGCNo.pull-down list,</en>
				contno = Integer.parseInt(pul_AGCNo.getSelectedValue());
			}
			//#CM54227
			//<en> In case of aisle connected stations:</en>
			if (Integer.parseInt(txt_AisleNumber.getText()) == 0 )
			{
				//#CM54228
				//<en> Set blank for the aisle station no.</en>
				param.setAisleStationNumber(alstno);
			}
			//#CM54229
			//<en> In case of stand alone stations:</en>
			else
			{
				alstno = getALSTNumber(conn, whno, txt_AisleNumber.getText());
				//#CM54230
				//<en> Check to see if the station is registered in AISLE table.</en>
				if ( alstno.equals("") )
				{
					//#CM54231
					//<en>Entered aisle no. does not exist in table. Please register on aisle setting screen.</en>
					message.setMsgResourceKey("6123101");
					return;
				}
				//#CM54232
				//<en> If the station exists in AISLE table,</en>
				else
				{
					//#CM54233
					//<en>aisle station no.</en>
					param.setAisleStationNumber(alstno);
				}
			}

			//#CM54234
			//<en>warehouse station no.</en>
			param.setWareHouseStationNumber(whno);
			//#CM54235
			//AGCNo.
			param.setControllerNumber(contno);
			//#CM54236
			//<en>station no.</en>
			param.setNumber(txt_StNumber.getText());
			//#CM54237
			//<en>station name.</en>
			param.setName(txt_StationName.getText());
			//#CM54238
			//<en>max number of carry insruction sendable</en>
			int maxInst = 0;
			try
			{
				maxInst = Integer.parseInt(txt_MaxInstrucion.getText());
			}
			catch(NumberFormatException ne)
			{
			}
			param.setMaxInstruction(maxInst);
			//#CM54239
			//<en>max number of retrieval insruction sendable</en>
			int maxQnt = 0;
			try
			{
				maxQnt = Integer.parseInt(txt_MaxPaletteQuantity.getText());
			}
			catch(NumberFormatException ne)
			{
			}
			param.setMaxPaletteQuantity(maxQnt);
			//#CM54240
			//<en>parent station no.</en>	
			param.setParentNumber(this.getViewState().getString(PARENTSTATIONNUMBER_KEY));
			
			//#CM54241
			//Acquire the value of the radio button and set it in Param.
			getRadioButton(param);

			if (factory.addParameter(conn, param))
			{
				//#CM54242
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM54243
				//Clear display area
				btn_Clear_Click(null);
			}
			//#CM54244
			//Maintain it in the session.
			this.getSession().setAttribute("FACTORY", factory);
			//#CM54245
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM54246
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54247
	/** 
	 * Mount the processing of a clear button.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		txt_StNumber.setText("");
		txt_StationName.setText("");
		txt_AisleNumber.setText("");
		//#CM54248
		//Type
		rdo_PrivateStorage.setChecked(true);
		rdo_PrivateRetrieval.setChecked(false);
		rdo_StandShuttleCart.setChecked(false);
		rdo_ConveyorStorageSide.setChecked(false);
		rdo_ConveyorRetrievalSide.setChecked(false);
		//#CM54249
		//Set division
		rdo_LoadConfirmationSetup.setChecked(true);
		rdo_PrecedenceSetup.setChecked(false);
		//#CM54250
		//Arrival report
		rdo_NotAssignedsArrive.setChecked(true);
		rdo_AssignedArrive.setChecked(false);
		//#CM54251
		//Mode of packing detector
		rdo_NotAssignedsCarryStyle.setChecked(true);
		rdo_AssignedCarryStyle.setChecked(false);
		//#CM54252
		//Work instruction
		rdo_NotAssignedsWork.setChecked(true);
		rdo_JobsDisplay.setChecked(false);
		rdo_OperationDisplay.setChecked(false);
		//#CM54253
		//Work of re-stock
		rdo_NotAssignedsReStoring.setChecked(true);
		rdo_AssignedReStoring.setChecked(false);
		//#CM54254
		//Transportation of re-stock instruction
		rdo_NotAssignedsReStoringCarry.setChecked(true);
		rdo_AssignedReStoringCarry.setChecked(false);
		//#CM54255
		//Disbursement
		rdo_NotAssignedsPayOut.setChecked(true);
		rdo_AssignedPayOut.setChecked(false);
		//#CM54256
		//Mode switch
		rdo_AWCModeChange.setChecked(false);
		rdo_EquipmentModeChange.setChecked(false);
		rdo_AutoModeChange.setChecked(false);
		rdo_NotAssignedsMode.setChecked(true);
		txt_MaxInstrucion.setText("");
		txt_MaxPaletteQuantity.setText("");
		pul_StoreAs.setSelectedIndex(0);
		pul_AGCNo.setSelectedIndex(0);
	}

	//#CM54257
	/** <en>
	 * It is called when a cancel button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Cancel_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			//#CM54258
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54259
			//All deletions after filtering
			factory.removeAllParameters(conn);
			//#CM54260
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM54261
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM54262
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54263
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_Station_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54264
			//Line that is corrected, and deleted
			int index = lst_Station.getActiveRow();
			//**** 修正 ****
			if (lst_Station.getActiveCol() == 1)
			{
				//#CM54265
				//A present line is set.
				lst_Station.setCurrentRow(index);
				//#CM54266
				//Display the highlight in yellow.
				lst_Station.setHighlight(index);
				//#CM54267
				//Set the parameter under the correction to factory.
				factory.changeParameter(index - 1);
				//#CM54268
				//<en>Retrieve from factory only the parameters being modified.</en>
				StationParameter param = (StationParameter) factory.getUpdatingParameter();
				//#CM54269
				//Storage division
				String whst = getWHNumber(conn, param.getWareHouseStationNumber());
				pul_StoreAs.setSelectedItem(whst);
				//#CM54270
				//Station No.
				txt_StNumber.setText(param.getNumber());
				//#CM54271
				//Station name
				txt_StationName.setText(param.getName());

				//#CM54272
				//<en>In case the aisle station no. has not been set, set 0 for RM machine no.</en>
				if( param.getAisleStationNumber().equals("") )
				{
					txt_AisleNumber.setText("0");
				}
				else
				{
					txt_AisleNumber.setText(getALNumber(conn, param.getAisleStationNumber()));
				}
				//#CM54273
				//AGCNo.
				String agcnumber = Integer.toString(param.getControllerNumber());
				pul_AGCNo.setSelectedItem(agcnumber);
				//#CM54274
				//Number in which transportation can be directed
				txt_MaxInstrucion.setText(Integer.toString(param.getMaxInstruction()));
				//#CM54275
				//Number in which delivery can be directed
				txt_MaxPaletteQuantity.setText(Integer.toString(param.getMaxPaletteQuantity()));
				//#CM54276
				//Parent station
				getViewState().setString(PARENTSTATIONNUMBER_KEY, param.getParentNumber());

				//#CM54277
				//Set radio button
				setRadioButton(param);
			}
			//**** 削除 ****
			else
			{
				//#CM54278
				//Deleting one after filtering
				factory.removeParameter(conn, index - 1);
				//#CM54279
				//<en> Set the preset data.</en>
				setList(conn, factory);
			}
			//#CM54280
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM54281
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54282
	/** <en>
	 * It is called when a commit button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Commit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");

			//#CM54283
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54284
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			//#CM54285
			//Commit flag
			rdo_LoadConfirmationSetup.setChecked(true);
			rdo_PrecedenceSetup.setChecked(false);
			//#CM54286
			//Disbursement
			rdo_NotAssignedsPayOut.setChecked(true);
			rdo_AssignedPayOut.setChecked(false);
			//#CM54287
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM54288
			//<en> Set the preset data.</en>
			setList(conn, factory);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM54289
				//connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM54290
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54291
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54292
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Create_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54293
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54294
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54295
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54296
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM54297
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54298
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54299
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54300
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54301
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54302
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54303
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StationName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54304
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StationName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54305
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StationName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54306
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StationName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54307
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Type_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54308
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_PrivateStorage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54309
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_PrivateStorage_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54310
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_PrivateRetrieval_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54311
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_PrivateRetrieval_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StandShuttleCart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54313
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StandShuttleCart_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ConveyorStorageSide_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ConveyorStorageSide_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ConveyorRetrievalSide_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ConveyorRetrievalSide_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AisleNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_AisleNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_AisleNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54321
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_AisleNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54322
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AisleStationMessage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54323
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AgcNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54324
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_AGCNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54325
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_AGCNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM54326
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CreateClass_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_LoadConfirmationSetup_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_LoadConfirmationSetup_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54329
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_PrecedenceSetup_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54330
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_PrecedenceSetup_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54331
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Arrival_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54332
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsArrive_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54333
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsArrive_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedArrive_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54335
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedArrive_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54336
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StyleInspection_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54337
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsCarryStyle_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54338
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsCarryStyle_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54339
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedCarryStyle_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54340
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedCarryStyle_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkIndication_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsWork_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsWork_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_JobsDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_JobsDisplay_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_OperationDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_OperationDisplay_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ReStoringWork_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsReStoring_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsReStoring_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedReStoring_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedReStoring_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ReStoringRouteIndication_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54354
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsReStoringCarry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsReStoringCarry_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54356
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedReStoringCarry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedReStoringCarry_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54358
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PayOut_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54359
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsPayOut_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54360
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsPayOut_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedPayOut_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54362
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AssignedPayOut_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54363
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ModeChange_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54364
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AWCModeChange_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AWCModeChange_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54366
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_EquipmentModeChange_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_EquipmentModeChange_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54368
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AutoModeChange_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54369
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AutoModeChange_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54370
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsMode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54371
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_NotAssignedsMode_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54372
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxInstruction_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54373
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MaxInstrucion_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54374
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MaxInstrucion_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54375
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MaxInstrucion_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54376
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxPaletteAQuantity_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54377
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MaxPaletteQuantity_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54378
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MaxPaletteQuantity_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54379
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MaxPaletteQuantity_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54380
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54381
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54382
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54383
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54384
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Station_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54385
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Station_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54386
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Station_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54387
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Station_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM54388
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Station_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54389
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Station_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM54390
//end of class
