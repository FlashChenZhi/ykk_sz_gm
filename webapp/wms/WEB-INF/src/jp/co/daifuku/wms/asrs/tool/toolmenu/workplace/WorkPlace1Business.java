// $Id: WorkPlace1Business.java,v 1.3 2006/10/30 06:31:15 suresh Exp $

//#CM54866
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.workplace;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationTypeHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationTypeSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.StationType;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.schedule.WorkPlaceParameter;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.workplacelist.WorkPlaceListBusiness;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;

//#CM54867
/**
 * The screen class of station setting (workshop) first screen.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/30 06:31:15 $
 * @author  $Author: suresh $
 */
public class WorkPlace1Business extends WorkPlace1 implements WMSToolConstants
{
	//#CM54868
	// Class fields --------------------------------------------------
	//#CM54869
	/** 
	 * The key which maintains the value of the storage division.
	 */
	public static final String WAREHOUSENUMBER_KEY = "WAREHOUSENUMBER_KEY";
	//#CM54870
	/** 
	 * The key which maintains the value of workshop No..
	 */
	public static final String PARENTSTATIONNUMBER_KEY = "PARENTSTATIONNUMBER_KEY";
	//#CM54871
	/** 
	 * The key which maintains the value of the workshop name.
	 */
	public static final String PARENTSTATIONNAME_KEY = "PARENTSTATIONNAME_KEY";
	//#CM54872
	/** 
	 * The key which maintains the value of the work type.
	 */
	public static final String WORKPLACETYPE_KEY = "WORKPLACETYPE_KEY";
	//#CM54873
	/** 
	 * The key which maintains the value of the representative station.
	 */
	public static final String MAINSTATION_KEY = "MAINSTATION_KEY";
	//#CM54874
	/** 
	 * The key which maintains the value of the Product folder.
	 */
	public static final String FILEPATH_KEY = "FILEPATH_KEY";
	//#CM54875
	/** 
	 * Class name (Warehouse)
	 */
	private String CLASS_WAREHOUSE  = "jp.co.daifuku.wms.base.dbhandler.WareHouseHandler" ;
	//#CM54876
	/** 
	 * Class name (Aisle)
	 */
	private String CLASS_AISLE  = "jp.co.daifuku.wms.base.dbhandler.AisleHandler" ;
	//#CM54877
	// Class variables -----------------------------------------------
	
	//#CM54878
	// Class method --------------------------------------------------

	//#CM54879
	// Constructors --------------------------------------------------

	//#CM54880
	// Public methods ------------------------------------------------
	//#CM54881
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM54882
		//Set the screen name
		lbl_SettingName.setResourceKey("TMEN-W0007-3");
		//#CM54883
		//Set the path of help file.
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0007-3", this.getHttpRequest()));
		setFocus(txt_ParentStNumber);
	}

	//#CM54884
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
			//#CM54885
			//Clear processing
			btn_Clear_Click(null);
			//#CM54886
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.WORKPLACE,Creater.M_CREATE);				

			//#CM54887
			//Maintained in the session.
			this.getSession().setAttribute("FACTORY", factory);
			//#CM54888
			//<en> Display the pull-down list.</en>
			ToolPulldownData pull = new ToolPulldownData(conn,locale,null);

			//#CM54889
			// Pull down display data(storage division)
			String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "");

			//#CM54890
			//Set the data in pull down.
			ToolPulldownHelper.setPullDown(pul_StoreAs, whno);

			//#CM54891
			//<en>Set the name of the file the data will be saved.</en>
			WorkPlaceParameter searchParam = new WorkPlaceParameter();
			searchParam.setFilePath((String)this.getSession().getAttribute("WorkFolder"));
			//#CM54892
			//Maintain the value of the Product folder. 
			getViewState().setString(FILEPATH_KEY, (String)this.getSession().getAttribute("WorkFolder"));
	
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM54893
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM54894
	/** <en>
	 * When it is returned, this method is called from Popup window.
	 * @param e ActionEvent
	 </en> */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		String stationno = param.getParameter(WorkPlaceListBusiness.STATIONNO_KEY);
		//#CM54895
		//Set the value when it is not empty.
		if (!StringUtil.isBlank(stationno))
		{
			txt_ParentStNumber.setText(stationno);
			txt_ParentStationName.setText(param.getParameter(WorkPlaceListBusiness.STATIONNAME_KEY));
			int worktype = Integer.parseInt(param.getParameter(WorkPlaceListBusiness.WORKPLACETYPE_KEY));
			switch(worktype)
			{ 
				//#CM54896
				// Representative station
				case Station.MAIN_STATIONS:
					chk_MainStation.setChecked(true);
					break;
				//#CM54897
				// Aisle independence
				case Station.STAND_ALONE_STATIONS:
					chk_MainStation.setChecked(false);
					rdo_StandAloneType.setChecked(true);
					rdo_AisleConnectedType.setChecked(false);
					break;
				//#CM54898
				// Aisle uniting
				case Station.AISLE_CONMECT_STATIONS:
					chk_MainStation.setChecked(false);
					rdo_StandAloneType.setChecked(false);
					rdo_AisleConnectedType.setChecked(true);
					break;
			}
			chk_MainStation_Change(null);
										
		}
	}
	//#CM54899
	// Package methods -----------------------------------------------

	//#CM54900
	// Protected methods ---------------------------------------------

	//#CM54901
	// Private methods -----------------------------------------------
	//#CM54902
	/** 
	 * Acquire the number of warehouse stations from the specified number of warehouses.
	 * @param  whNo Number of warehouses
	 * @return Number of warehouse stations
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
	//#CM54903
	// Event handler methods -----------------------------------------
	//#CM54904
	/**
	 * Called when Menu button is pressed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM54905
	/** <en>
	 * It is called when a reference button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Search_Click(ActionEvent e) throws Exception
	{
		//#CM54906
		//It changes to the workshop list screen.
		ForwardParameters param = new ForwardParameters();
		param.setParameter(WorkPlaceListBusiness.STATIONNO_KEY, txt_ParentStNumber.getText());
		param.setParameter(WorkPlaceListBusiness.WHSTATIONNO_KEY, pul_StoreAs.getSelectedValue());
		//#CM54907
		//Processing screen -> Result screen
		redirect("/asrs/tool/listbox/workplacelist/WorkPlaceList.do", param, "/progress.do");
	}

	//#CM54908
	/**
	 * Called when Next button is pressed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM54909
			//Input check
			txt_ParentStNumber.validate();

			//#CM54910
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54911
			//<en> Check to see if the data is registered in WAREHOUSE table.</en>
			if (pul_StoreAs.getSelectedValue() == null )
			{
				//#CM54912
				//<en> There is no information of the warheouse. Please register in warehouse setting screen.</en>
				message.setMsgResourceKey("6123100");
				return;
			}
	
			//#CM54913
			//<en> Check to see if the data is registered in STATION table.</en>
			ToolStationSearchKey  skey  = new ToolStationSearchKey() ;
			ToolWorkPlaceHandler  shdle = new ToolWorkPlaceHandler(conn) ;
			Station st[] = (Station[])shdle.find(skey);
			if(st.length <= 0)
			{
				//#CM54914
				//<en> There is no information of the station. Please register in station setting screen.</en>
				message.setMsgResourceKey("6123079");
				return;
			}
	
			//#CM54915
			//<en>*** For workshop ***</en>
			if (!chk_MainStation.getChecked())
			{
				//#CM54916
				//<en>Check to see if there are any settable stations, workshops or main stations in registeration.</en>
				//#CM54917
				//<en>Determine by station type.</en>
				int[] temp_stationtype = {Station.STATION_TYPE_IN, Station.STATION_TYPE_OUT, Station.STATION_TYPE_INOUT};
				ToolStationSearchKey  wokey  = new ToolStationSearchKey() ;
				ToolWorkPlaceHandler  wohdle = new ToolWorkPlaceHandler(conn) ;
				wokey.setStationType(temp_stationtype);
				Station wo[] = (Station[])wohdle.find(wokey);
				if(wo.length <= 0)
				{
					//#CM54918
					//<en> There is no information of the station/warehouse.</en>
					//<en> Please register the station in station setting screen.</en>
					message.setMsgResourceKey("6123126");
					return;
				}
			}
			//#CM54919
			//<en>*** For maiin stations ***</en>
			else
			{
				//#CM54920
				//<en>Check to see if there are any settable stations or main stations in registeration.</en>
				//#CM54921
				//<en>Determine by type of sendability.</en>
				ToolStationSearchKey  wskey  = new ToolStationSearchKey() ;
				ToolWorkPlaceHandler  wshdle = new ToolWorkPlaceHandler(conn) ;
				wskey.setSendable(1);
				Station ws[] = (Station[])wshdle.find(wskey);
				if(ws.length <= 0)
				{
					//#CM54922
					//<en> There is no information of the station.</en>
					//<en> Please register in station setting screen.</en>
					message.setMsgResourceKey("6123079");
					return;
				}
			}

			ToolStationSearchKey  wstkey  = new ToolStationSearchKey() ;
			ToolWorkPlaceHandler  wsthdle = new ToolWorkPlaceHandler(conn) ;
			wstkey.setNumber(txt_ParentStNumber.getText());
			Station wst[] = (Station[])wsthdle.find(wstkey);
			//#CM54923
			//<en>*** For a new workshop ***</en>

			//#CM54924
			//<en> Name of the workshop must be entered.</en>
			if(wst.length <= 0)
			{
				if (txt_ParentStationName.getText().trim().equals(""))
				{
					//#CM54925
					//Input the workshop name to the workshop newly registered.
					message.setMsgResourceKey("6123108");
					return;					
				}
				txt_ParentStationName.validate();
			}
	
			//#CM54926
			//<en>***If entered workshop is already registered in STATION table ***</en>
			if(wst.length > 0)
			{
				//#CM54927
				//<en> Unable to modify the storage type of registered workshop/main station.</en>
				String whno = getWHSTNumber(conn, pul_StoreAs.getSelectedValue());
				if(!wst[0].getWarehouseStationNumber().equals(whno))
				{
					//#CM54928
					//<en>6413170 = Entered workshop is set with different storage type.</en>
					message.setMsgResourceKey("6123170");
					return;
				}
				//#CM54929
				//<en>*** For workshop ***</en>

				if(!chk_MainStation.getChecked())
				{
					//#CM54930
					//<en> Unable to modify the registered workshop to the main station.</en>
					if(wst[0].getWorkPlaceType() == 0 || wst[0].getWorkPlaceType() == 3)
					{
						//#CM54931
						//<en>6413133 = Entered workshop is set as a station or a main station.</en>
						message.setMsgResourceKey("6123133");
						return;
					}
					
					//#CM54932
					//Workshop type
					//#CM54933
					//Aisle independent type
					int workplacetype = 1;
					RadioButton rdo = rdo_StandAloneType.getSelectedItem();
					if (rdo.getId().equals("rdo_AisleConnectedType"))
					{
						//#CM54934
						//Aisle uniting type
						workplacetype = 2;
					}
										
					//#CM54935
					//<en> Unable to modify the workshop type of registered workshop.</en>
					if(wst[0].getWorkPlaceType() != workplacetype)
					{
						if(wst[0].getWorkPlaceType() == 1)
						{
							//#CM54936
							//<en>6413132 = Entered workshop is set as a stand alone type workshop.</en>
							message.setMsgResourceKey("6123132");
							return;
						}
						else if(wst[0].getWorkPlaceType() == 2)
						{
							//#CM54937
							//<en>6413131 = Entered workshop is set as an aisle connected type workshop.</en>
							message.setMsgResourceKey("6123131");
							return;
						}
					}
				}
				//#CM54938
				//<en>*** For the main station ***</en>
				else
				{
					//#CM54939
					//<en> Unable to modify the main station ion registration to a workshop.</en>
					if(wst[0].getWorkPlaceType() != 3)
					{
						//#CM54940
						//<en>6413136 = Entered main station no. is set as a station or a workshop.</en>
						message.setMsgResourceKey("6123136");
						return;
					}
				}
			}
			//#CM54941
			//<en>*** If entered workshop is already registered in STATIONTYPE table ***</en>
			//#CM54942
			//<en>Unable to select the warehouse station or the aisle station.</en>
			String[] temp_class = {CLASS_WAREHOUSE, CLASS_AISLE};
			ToolStationTypeSearchKey  tskey  = new ToolStationTypeSearchKey() ;
			ToolStationTypeHandler  thdle = new ToolStationTypeHandler(conn) ;
			tskey.setNumber(txt_ParentStNumber.getText());
			tskey.setHandlerClass(temp_class);
			StationType stp[] = (StationType[])thdle.find(tskey);
			if(stp.length > 0)
			{
				//#CM54943
				//<en>6413149 Entered workshop no. is registered as a warehouse station or an aisle station.</en>
				message.setMsgResourceKey("6123149");
				return;
			}

			//#CM54944
			//Scheduler for opening
			CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
			WorkPlaceParameter searchParam = new WorkPlaceParameter();
			
			String whno = getWHSTNumber(conn, pul_StoreAs.getSelectedValue());
			
			//#CM54945
			//<en>warehouse station no.</en>
			searchParam.setWareHouseStationNumber(whno);
			//#CM54946
			//<en>workshop no.</en>
			searchParam.setParentNumber(txt_ParentStNumber.getText());
			//#CM54947
			//<en>main station</en>
			int mainstno = 0;
			if(chk_MainStation.getChecked())
			{
				mainstno = 1;
			}
			searchParam.setMainStation(mainstno);
			//#CM54948
			//<en>Set the name of the file the data will be saved.</en>
			searchParam.setFilePath(this.getViewState().getString(WorkPlace1Business.FILEPATH_KEY));
			
			WorkPlaceParameter[] array = (WorkPlaceParameter[])factory.query(conn, this.getHttpRequest().getLocale(), searchParam);

			//#CM54949
			//<en>In case the data could not be obtained,</en>
			if(array == null)
			{
				message.setMsgResourceKey(factory.getMessage());
				return;
			}
			else
			{
				for(int i = 0; i < array.length; i++)
				{
					((ToolScheduleInterface)factory).addInitialParameter(array[i]);
				}				
			}

			//#CM54950
			//Maintain the value of the storage division.
			getViewState().setString(WAREHOUSENUMBER_KEY, pul_StoreAs.getSelectedValue());
			//#CM54951
			//Maintain the value of workshop No..
			getViewState().setString(PARENTSTATIONNUMBER_KEY, txt_ParentStNumber.getText());
			//#CM54952
			//Maintain the value of the workshop name.
			getViewState().setString(PARENTSTATIONNAME_KEY, txt_ParentStationName.getText());
			//#CM54953
			//Workshop type
			//#CM54954
			//Aisle independent type
			int workplacetypenum = Station.STAND_ALONE_STATIONS;
			RadioButton rdo = rdo_StandAloneType.getSelectedItem();
			if (rdo.getId().equals("rdo_AisleConnectedType"))
			{
				//#CM54955
				//Aisle uniting type
				workplacetypenum = Station.AISLE_CONMECT_STATIONS;
			}
			//#CM54956
			//Maintain the value of the workshop type. 
			getViewState().setString(WORKPLACETYPE_KEY, Integer.toString(workplacetypenum));
			//#CM54957
			//Maintain the value of the workshop name.
			getViewState().setString(MAINSTATION_KEY, Integer.toString(mainstno));

			//#CM54958
			//It maintains it in the session.
			this.getSession().setAttribute("FACTORY", factory);
			forward("/asrs/tool/workplace/WorkPlace2.do");
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM54959
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54960
	/**
	 * It is called when a clear button is pressed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		pul_StoreAs.setSelectedIndex(0);
		txt_ParentStNumber.setText("");
		txt_ParentStationName.setText("");
		rdo_StandAloneType.setEnabled(true);
		rdo_AisleConnectedType.setEnabled(true);
		rdo_StandAloneType.setChecked(true);
		rdo_AisleConnectedType.setChecked(false);
		chk_MainStation.setChecked(false);
	}
	//#CM54961
	/** 
	 * Mount processing when the check box is checked or unchecked.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MainStation_Change(ActionEvent e) throws Exception
	{
		if (chk_MainStation.getChecked())
		{
			rdo_StandAloneType.setEnabled(false);
			rdo_AisleConnectedType.setEnabled(false);
		}
		else if (!chk_MainStation.getChecked())
		{
			rdo_StandAloneType.setEnabled(true);
			rdo_AisleConnectedType.setEnabled(true);
		}
	}
	//#CM54962
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54963
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54964
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_WorkPlace_Create_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54965
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54967
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54968
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM54969
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ParentStnumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54970
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ParentStNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ParentStNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ParentStNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54973
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ParentStNumber_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54974
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Search_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54975
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ParentstationName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54976
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ParentStationName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54977
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ParentStationName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54978
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ParentStationName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54979
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ParentStationName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54980
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkPlaceTypeChar_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54981
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StandAloneType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54982
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StandAloneType_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54983
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AisleConnectedType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54984
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AisleConnectedType_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54985
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MainStation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54986
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54987
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM54988
//end of class
