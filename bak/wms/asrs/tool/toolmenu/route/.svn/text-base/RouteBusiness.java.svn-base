// $Id: RouteBusiness.java,v 1.2 2006/10/30 05:11:10 suresh Exp $

//#CM53986
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.route;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.RouteParameter;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;

//#CM53987
/**
 * Station setting (traveling route) screen Class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:11:10 $
 * @author  $Author: suresh $
 */
public class RouteBusiness extends Route implements WMSToolConstants
{
	//#CM53988
	// Class fields --------------------------------------------------

	//#CM53989
	// Class variables -----------------------------------------------

	//#CM53990
	// Class method --------------------------------------------------

	//#CM53991
	// Constructors --------------------------------------------------

	//#CM53992
	// Public methods ------------------------------------------------

	//#CM53993
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM53994
		//Set screen Name. 
		lbl_SettingName.setResourceKey("TMEN-W0007-4");
		//#CM53995
		//Set passing to the Help file. 
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0007-4", this.getHttpRequest()));
		
		setFocus(txt_ConnectStnumber);
	}

	//#CM53996
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
			//#CM53997
			//Clear processing
			btn_Clear_Click(null);
			//#CM53998
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.ROUTE, Creater.M_CREATE);
			//#CM53999
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);

			ToolPulldownData pull = new ToolPulldownData(conn, locale, null);
			//#CM54000
			// Pull down display data (Warehouse)
			String[] whno = pull.getAllStationPulldownData("", 1);
			//#CM54001
			//Set data in the pull down.
			ToolPulldownHelper.setPullDown(pul_ConveyanceOriginStationNo, whno);

			//#CM54002
			//<en> Retrieve from ToolParam the default path of Route.txt.</en>
			String defaultRouteText = ToolParam.getParam("DEFAULT_ROUTETEXT_PATH");
			File routepath = new File(defaultRouteText);

			//#CM54003
			//<en> REtrieve the path to create the Route.txt.</en>
			String filePath = (String)this.getSession().getAttribute("WorkFolder") + "/" + routepath.getName();
	
			RouteParameter param = new RouteParameter();
			param.setRouteTextPath(filePath);

			RouteParameter[] array = (RouteParameter[])factory.query(conn, locale,param);

			if(array != null)
			{
				for(int i = 0; i < array.length; i++)
				{
					((ToolScheduleInterface)factory).addInitialParameter(array[i]);
				}
			}

			//#CM54004
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
				//#CM54005
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54006
	// Package methods -----------------------------------------------

	//#CM54007
	// Protected methods ---------------------------------------------

	//#CM54008
	// Private methods -----------------------------------------------
	//#CM54009
	/** <en>
	 * Set data to preset area.
	 * @param conn Connection 
	 * @param factory ScheduleInterface 
	 * @throws Exception 
	 </en> */
	private void setList(Connection conn, ScheduleInterface factory) throws Exception
	{
		String number = "";
		String stationnumber = "";
		String connectstnumber = "";

		//#CM54010
		///Plaque of TOOL TIP
		String title_ConnectSTNumber = DisplayText.getText("TLBL-W0039");

		//#CM54011
		//Delete all the lines
		lst_Route.clearRow();

		//#CM54012
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			RouteParameter param = (RouteParameter)paramarray[i];
			//#CM54013
			//Parameter to be added to List
			number = Integer.toString(i+1);
			stationnumber = param.getStationNumber();
			connectstnumber = param.getConnectStNumber();
			
			//#CM54014
			//Line addition
			//#CM54015
			//The final line is acquired. 
			int count = lst_Route.getMaxRows();
			lst_Route.setCurrentRow(count);
			lst_Route.addRow();
			lst_Route.setValue(3, number);
			lst_Route.setValue(4, stationnumber);
			lst_Route.setValue(5, connectstnumber);
			
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_ConnectSTNumber, connectstnumber);

			//#CM54016
			//Set TOOL TIP. 	
			lst_Route.setToolTip(count, toolTip.getText());
		}
		//#CM54017
		// Display the line under the correction highlighting. 
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_Route.setHighlight(modindex + 1);
		}
	}

	//#CM54018
	// Event handler methods -----------------------------------------
	//#CM54019
	/**
	 * When the Menu button is clicked, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM54020
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
			//#CM54021
			//Input check
			txt_ConnectStnumber.validate();
			
			//#CM54022
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");

			//#CM54023
			//<en> Set the entered value in schedule parameter.</en>
			RouteParameter param = new RouteParameter();

			param.setStationNumber(pul_ConveyanceOriginStationNo.getSelectedValue());
			param.setConnectStNumber(txt_ConnectStnumber.getText());

			if (factory.addParameter(conn, param))
			{
				//#CM54024
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM54025
				//Area for display
				btn_Clear_Click(null);
			}
			//#CM54026
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			//#CM54027
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
				//#CM54028
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54029
	/** <en>
	 * It is called when a clear button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		pul_ConveyanceOriginStationNo.setSelectedIndex(0);
		txt_ConnectStnumber.setText("");
	}

	//#CM54030
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

			//#CM54031
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54032
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			
			//#CM54033
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM54034
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
				//#CM54035
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54036
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
			//#CM54037
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54038
			//Delete all after filtering
			factory.removeAllParameters(conn);
			//#CM54039
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM54040
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
				//#CM54041
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54042
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_Route_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54043
			//Line where it corrects, and Deletion is done
			int index = lst_Route.getActiveRow();
			//**** 修正 ****
			if (lst_Route.getActiveCol() == 1)
			{
				//#CM54044
				//Current line is set. 
				lst_Route.setCurrentRow(index);
				//#CM54045
				//Display the highlight in yellow. 
				lst_Route.setHighlight(index);
				//#CM54046
				//Set parameter under the correction to factory. 
				factory.changeParameter(index - 1);
				//#CM54047
				//<en>Retrieve from factory only the parameters being modified.</en>
				RouteParameter param = (RouteParameter)factory.getUpdatingParameter();
				//#CM54048
				//Transportation originStation No.
				pul_ConveyanceOriginStationNo.setSelectedItem(param.getStationNumber());
				//#CM54049
				//Connection Station No. 
				txt_ConnectStnumber.setText(param.getConnectStNumber());

			}
			//**** 削除 ****
			else
			{
				//#CM54050
				//Delete 1 after filtering
				factory.removeParameter(conn, index - 1);
				//#CM54051
				//<en> Set the preset data.</en>
				setList(conn, factory);
			}
			//#CM54052
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
				//#CM54053
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM54054
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54055
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54056
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54057
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54058
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConnectionBeforeStation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54059
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_ConveyanceOriginStationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54060
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_ConveyanceOriginStationNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM54061
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConnectStnumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54062
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConnectStnumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConnectStnumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConnectStnumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConnectStnumber_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Route_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Route_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Route_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54073
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Route_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM54074
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Route_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Route_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM54076
//end of class
