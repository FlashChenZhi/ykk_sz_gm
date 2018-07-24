// $Id: WorkPlace2Business.java,v 1.3 2006/10/30 06:31:07 suresh Exp $

//#CM54993
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.workplace;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.StationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.WorkPlaceParameter;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.stationlist.StationListBusiness;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;

//#CM54994
/**
 * The screen class of station setting (workshop) second screen.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/30 06:31:07 $
 * @author  $Author: suresh $
 */
public class WorkPlace2Business extends WorkPlace2 implements WMSToolConstants
{
	//#CM54995
	// Class fields --------------------------------------------------
	//#CM54996
	/** 
	 * The key which maintains the value of the workshop type.
	 */
	public static final String WORKPLACETYPE_KEY = "WORKPLACETYPE_KEY";
	//#CM54997
	// Class variables -----------------------------------------------

	//#CM54998
	// Class method --------------------------------------------------

	//#CM54999
	// Constructors --------------------------------------------------

	//#CM55000
	// Public methods ------------------------------------------------
	//#CM55001
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM55002
		//Set the screen name.
		lbl_SettingName.setResourceKey("TMEN-W0007-3");
		//#CM55003
		//Set the path of help file.
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0007-3", this.getHttpRequest()));
		//#CM55004
		//To delete the value of setFocus(txt_ParentStNumber) set with page_Initialize of WorkPlace1Business is described.
		httpRequest.setAttribute(jp.co.daifuku.bluedog.webapp.Constants.KEY_FOCUS_TAG_SUPPORT, null);
	}

	//#CM55005
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM55006
			// The tab in the state of the selection
			tab_WorkPlace_Create.setSelectedIndex(2);
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			ToolStationSearchKey  wstkey  = new ToolStationSearchKey() ;
			ToolWorkPlaceHandler  wsthdle = new ToolWorkPlaceHandler(conn) ;
			wstkey.setNumber(this.getViewState().getString(WorkPlace1Business.PARENTSTATIONNUMBER_KEY));
			Station wst[] = (Station[])wsthdle.find(wstkey);
			
			ToolFindUtil futil = new ToolFindUtil(conn);

			//#CM55007
			//<en>workshop no.</en>
			lbl_In_WareHouseName.setText(futil.getWarehouseName(Integer.parseInt(this.getViewState().getString(WorkPlace1Business.WAREHOUSENUMBER_KEY))));
			//#CM55008
			//<en>workshop no.</en>
			lbl_In_ParentStationNumber.setText(this.getViewState().getString(WorkPlace1Business.PARENTSTATIONNUMBER_KEY));
			//#CM55009
			//<en>workshop name</en>
			//#CM55010
			//<en>In case of new workshop, retrieve data from entered value on the screen.</en>
			if(wst.length <= 0)
			{
				lbl_In_ParentStationName.setText(this.getViewState().getString(WorkPlace1Business.PARENTSTATIONNAME_KEY));
			}
			//#CM55011
			//<en>Retrieve from database.</en>
			else
			{
				lbl_In_ParentStationName.setText(wst[0].getName());
			}
			//#CM55012
			//<en>workshop type name</en>
			//#CM55013
			//<en>In case of main station,</en>
			if(Integer.parseInt(this.getViewState().getString(WorkPlace1Business.MAINSTATION_KEY)) == 1)
			{
				lbl_In_WorkPlaceType.setText(DisplayText.getText("TSTATION", "WORKPLACETYPE", Integer.toString(3)));
				//#CM55014
				//Maintain the value of the workshop type.
				getViewState().setString(WORKPLACETYPE_KEY, "3");
			}
			//#CM55015
			//<en>In case of workshop,</en>
			else
			{
				lbl_In_WorkPlaceType.setText(DisplayText.getText("TSTATION", "WORKPLACETYPE", this.getViewState().getString(WorkPlace1Business.WORKPLACETYPE_KEY)));
				//#CM55016
				//Maintain the value of the workshop type.
				getViewState().setString(WORKPLACETYPE_KEY, this.getViewState().getString(WorkPlace1Business.WORKPLACETYPE_KEY));
			}
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			//#CM55017
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
				//#CM55018
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM55019
	/** <en>
	 * When it is returned, this method is called from Popup window.
	 * @param e ActionEvent
	 </en> */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		String stationworkplaceno = param.getParameter(StationListBusiness.STATIONNO_KEY);
		String stationworkplacename = param.getParameter(StationListBusiness.STATIONNAME_KEY);
		String stationype = param.getParameter(StationListBusiness.WORKPLACETYPE_KEY);
		String aislestationno = param.getParameter(StationListBusiness.AISLESTATIONNO_KEY);
		String agcnumber = param.getParameter(StationListBusiness.AGCNO_KEY);
		//#CM55020
		//Set the value when it is not empty.
		if (!StringUtil.isBlank(stationworkplaceno))
		{
			lbl_In_StationNoWorkPlaceNo.setText(stationworkplaceno);
			lbl_In_StNameWorkPlaceName.setText(stationworkplacename);
			lbl_In_StationType.setText(stationype);
			lbl_In_AisleStationNumber.setText(aislestationno);
			lbl_In_AGCNumber.setText(agcnumber);
		}
	}
	//#CM55021
	// Package methods -----------------------------------------------

	//#CM55022
	// Protected methods ---------------------------------------------

	//#CM55023
	// Private methods -----------------------------------------------
	//#CM55024
	/** <en>
	 * Set data to preset area.
	 * @param conn Connection 
	 * @param factory ScheduleInterface 
	 * @throws Exception 
	 </en> */
	private void setList(Connection conn, ScheduleInterface factory) throws Exception
	{
		String stationnoworkplaceno = "";
		String stationnameworkplacename = "";
		String stationtype = "";
		String aislestationnumber = "";
		String agcnumber = "";

		//#CM55025
		//All lines are deleted.
		lst_WorkPlace.clearRow();

		//#CM55026
		//Name used with Tip
		String title_stationworkplace = DisplayText.getText("TLBL-W0091");
		String title_workplacetype    = DisplayText.getText("TLBL-W0077");
		
		ToolFindUtil futil = new ToolFindUtil(conn);
		//#CM55027
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			WorkPlaceParameter param = (WorkPlaceParameter) paramarray[i];
			//#CM55028
			//Parameter which adds to the list
			//#CM55029
			//<en>station no.</en>
			stationnoworkplaceno = param.getNumber();
			//#CM55030
			//<en>station name</en>
			stationnameworkplacename = futil.getStationName(param.getNumber());
			//#CM55031
			//<en>station type</en>
			if(param.getNumber().equals(""))
			{
				stationtype = "";
			}
			else
			{
				String cl = getClassName(conn, param.getNumber());
				if(cl.equals(""))
				{
					stationtype = DisplayText.getText("TLBL-W0069");
				}
				else
				{
					stationtype = DisplayText.getText("TSTATION", "STATIONTYPE", cl);
				}
			}
			//#CM55032
			//<en>aisle station no.</en>
			if(param.getAisleNumber().equals(""))
			{
				aislestationnumber = DisplayText.getText("TLBL-W0069");
			}
			else
			{
				aislestationnumber = param.getAisleNumber();
			}
			//#CM55033
			//AGCNo.
			agcnumber = Integer.toString(param.getControllerNumber());
			
			//#CM55034
			//Line addition
			//#CM55035
			//The final line is acquired.
			int count = lst_WorkPlace.getMaxRows();
			lst_WorkPlace.setCurrentRow(count);
			lst_WorkPlace.addRow();
			lst_WorkPlace.setValue(2, stationnoworkplaceno);
			lst_WorkPlace.setValue(3, stationnameworkplacename);
			lst_WorkPlace.setValue(4, stationtype);
			lst_WorkPlace.setValue(5, aislestationnumber);
			lst_WorkPlace.setValue(6, agcnumber);
			
			//#CM55036
			//The character string displayed in TOOL TIP is made. 
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_stationworkplace, stationnameworkplacename);
			toolTip.add(title_workplacetype, stationtype);
			
			//#CM55037
			//Set the tool tip
			lst_WorkPlace.setToolTip(count, toolTip.getText());
		}
	}

	//#CM55038
	/** 
	 * Acquire the number of warehouse stations from the specified number of warehouses.
	 * @param  conn Connection
	 * @param  whNo Warehouse No.
	 * @return Warehouse station No.
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

	//#CM55039
	/** 
	 * Make station No. a retrieval key and return the station type.
	 * @param  Connection
	 * @param  stno Station No.
	 * @return Station type
	 * @throws Exception 
	 */
	private String getClassName(Connection conn, String stno) throws Exception
	{
		if (StringUtil.isBlank(stno))
		{
			return "";
		}

		ToolStationSearchKey  skey  = new ToolStationSearchKey() ;
		ToolWorkPlaceHandler  shdle = new ToolWorkPlaceHandler(conn) ;
		skey.setNumber(stno);
		
		Station[] st = (Station[])shdle.find(skey);

		if ( st != null && st.length > 0 )
		{
			//#CM55040
			//<en>dedicated for storage</en>
			if(st[0].getClassName().equals(StationCreater.CLASS_STORAGE))
			{
				return "0";
			}
			//#CM55041
			//<en>dedicated for retrieval</en>
			else if(st[0].getClassName().equals(StationCreater.CLASS_RETRIEVAL))
			{
				return "1";
			}
			//#CM55042
			//<en>P&D stand, self-drive cart</en>
			else if(st[0].getClassName().equals(StationCreater.CLASS_INOUTSTATION))
			{
				return "2";
			}
			//#CM55043
			//<en>U-shaped (storage)</en>
			else if(st[0].getClassName().equals(StationCreater.CLASS_FREESTORAGE))
			{
				return "3";
			}
			//#CM55044
			//<en>U-shaped (retrieval)</en>
			else if(st[0].getClassName().equals(StationCreater.CLASS_FREERETRIEVAL))
			{
				return "4";
			}
			else{
				return "";
			}
		}
		return "";
	}
	//#CM55045
	// Event handler methods -----------------------------------------
	//#CM55046
	/**
	 * It is called when the Menu button is clicked.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM55047
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
			//#CM55048
			//Input Check
			if(StringUtil.isBlank(lbl_In_StationNoWorkPlaceNo.getText()))
			{
				//#CM55049
				//6123112 = Enter Station No. or Workshop No.
				message.setMsgResourceKey("6123112");
				return;
			}

			//#CM55050
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");

			WorkPlaceParameter param = new WorkPlaceParameter();

			//#CM55051
			//<en>warehouse station no.</en>
			param.setWareHouseStationNumber(getWHSTNumber(conn, this.getViewState().getString(WorkPlace1Business.WAREHOUSENUMBER_KEY)));
			//#CM55052
			//<en>workshop no.</en>
			param.setParentNumber(lbl_In_ParentStationNumber.getText());
			//#CM55053
			//<en>workshop name</en>
			param.setParentName(lbl_In_ParentStationName.getText());
			//#CM55054
			//<en>workshop type</en>
			//#CM55055
			//<en>workshop</en>
			if(Integer.parseInt(this.getViewState().getString(WorkPlace1Business.MAINSTATION_KEY)) == 0)
			{
				param.setWorkPlaceType(Integer.parseInt((this.getViewState().getString(WorkPlace1Business.WORKPLACETYPE_KEY))));
			}
			//#CM55056
			//<en>main station</en>
			else
			{
				param.setWorkPlaceType(3);
			}
			//#CM55057
			//<en>main station</en>
			param.setMainStation(Integer.parseInt(this.getViewState().getString(WorkPlace1Business.MAINSTATION_KEY)));
			//#CM55058
			//<en> station no.</en>
			param.setNumber(lbl_In_StationNoWorkPlaceNo.getText());

			ToolStationSearchKey  skey  = new ToolStationSearchKey() ;
			ToolWorkPlaceHandler  shdle = new ToolWorkPlaceHandler(conn) ;
			skey.setNumber(lbl_In_StationNoWorkPlaceNo.getText());
			Station st[] = (Station[])shdle.find(skey);
			if (st.length > 0)
			{
				//#CM55059
				//<en>max number of retrieval instruction</en>
				param.setMaxPaletteQuantity(st[0].getMaxPaletteQuantity());
				//#CM55060
				//<en>max number of carry instruction</en>
				param.setMaxInstruction(st[0].getMaxInstruction());
				//#CM55061
				//AGCNo.
				param.setControllerNumber(st[0].getGroupController().getControllerNumber());
				//#CM55062
				//<en>type</en>
				param.setType(st[0].getType());
				//#CM55063
				//<en>aisle station no.</en>
				param.setAisleNumber(st[0].getAisleStationNumber());
				//#CM55064
				//<en>class name</en>
				param.setClassName(st[0].getClassName());
			}
			else
			{
				//#CM55065
				//<en>max number of retrievaly instruction</en>
				param.setMaxPaletteQuantity(0);
				//#CM55066
				//<en>max number of carry instruction</en>
				param.setMaxInstruction(0);
				//#CM55067
				//AGCNo.
				param.setControllerNumber(0);
				//#CM55068
				//<en>type</en>
				param.setType(0);
				//#CM55069
				//<en>aisle station no.</en>
				param.setAisleNumber("");
				//#CM55070
				//<en>class name</en>
				param.setClassName("");
			}
			//#CM55071
			//<en>Process the presetting of data.</en>
			//#CM55072
			//<en>Clear the data input area when presetting of data succeeded.</en>
			if (factory.addParameter(conn, param))
			{
				//#CM55073
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM55074
				//Clear processing
				lbl_In_StationNoWorkPlaceNo.setText("");
				lbl_In_StNameWorkPlaceName.setText("");
				lbl_In_StationType.setText("");
				lbl_In_AisleStationNumber.setText("");
				lbl_In_AGCNumber.setText("");
			}
			//#CM55075
			//Maintain it in the session.
			this.getSession().setAttribute("FACTORY", factory);
			//#CM55076
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
				//#CM55077
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM55078
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

			//#CM55079
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM55080
			//<en>If the there is no data in preset area, set the warehouse station no. and workshop.</en>
			Parameter paramarray[] = factory.getParameters();
			if(paramarray.length == 0)
			{
				WorkPlaceParameter Param = new WorkPlaceParameter();
				//#CM55081
				//<en>warehouse station no.</en>
				Param.setWareHouseStationNumber(getWHSTNumber(conn, this.getViewState().getString(WorkPlace1Business.WAREHOUSENUMBER_KEY)));
				//#CM55082
				//<en>workshop no.</en>
				Param.setParentNumber(lbl_In_ParentStationNumber.getText());
				factory.addParameter(conn, Param);
			}
			//#CM55083
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			//#CM55084
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM55085
			//<en>If the workshop has been set for the preset area with no data, </en>
			//<en>clear this workshop after the scheduling complete.</en>
			if(paramarray.length == 0)
			{
				factory.removeAllParameters(conn);
			}
			//#CM55086
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
				//#CM55087
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM55088
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
			//#CM55089
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM55090
			//All deletions after filtering
			factory.removeAllParameters(conn);
			//#CM55091
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM55092
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
				//#CM55093
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM55094
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_WorkPlace_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM55095
			//Line that is corrected, and deleted
			int index = lst_WorkPlace.getActiveRow();
			//#CM55096
			//one is deleted after filtering.
			factory.removeParameter(conn, index - 1);
			//#CM55097
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM55098
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
				//#CM55099
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM55100
	/** <en>
	 * It is called when a reference button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Search_Click(ActionEvent e) throws Exception
	{
		//#CM55101
		//Set the search condition on the station list list screen.
		ForwardParameters param = new ForwardParameters();
		param.setParameter(StationListBusiness.MAINSTATION_KEY, this.getViewState().getString(WorkPlace1Business.MAINSTATION_KEY));
		param.setParameter(StationListBusiness.WHSTATIONNO_KEY, this.getViewState().getString(WorkPlace1Business.WAREHOUSENUMBER_KEY));
		param.setParameter(StationListBusiness.PARENTSTNO_KEY, this.getViewState().getString(WorkPlace1Business.PARENTSTATIONNUMBER_KEY));
		param.setParameter(StationListBusiness.WORKPLACETYPE_KEY, this.getViewState().getString(WORKPLACETYPE_KEY));
		param.setParameter(StationListBusiness.STATIONNO_KEY, "");

		//#CM55102
		//Processing Screen -> Result Screen
		redirect("/asrs/tool/listbox/stationlist/StationList.do", param, "/progress.do");
	}

	//#CM55103
	/** <en>
	 * It is called when a back button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/workplace/WorkPlace1.do");
	}

	//#CM55104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55105
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55106
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_WorkPlace_Create_Click(ActionEvent e) throws Exception
	{
	}

	//#CM55107
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55108
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55109
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55110
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_In_WareHouseName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55111
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ParentStnumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_In_ParentStationNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ParentstationName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55114
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_In_ParentStationName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkPlaceTypeChar_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55116
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_In_WorkPlaceType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55117
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationNumberWorkPlaceNum_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55118
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_In_StationNoWorkPlaceNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55119
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Search_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationNameWorkPlaceName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55121
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_In_StNameWorkPlaceName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationTypeChar_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_In_StationType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AisleStationNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_In_AisleStationNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55126
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AgcNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55127
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_In_AGCNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55128
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55129
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55130
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55131
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkPlace_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM55132
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkPlace_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM55133
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkPlace_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM55134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkPlace_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM55135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM55136
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkPlace_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM55137
//end of class
