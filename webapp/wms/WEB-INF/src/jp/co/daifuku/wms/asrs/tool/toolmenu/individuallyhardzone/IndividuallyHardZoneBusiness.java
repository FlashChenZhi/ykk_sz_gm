// $Id: IndividuallyHardZoneBusiness.java,v 1.2 2006/10/30 04:57:36 suresh Exp $

//#CM53051
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.individuallyhardzone;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.IndividuallyHardZoneParameter;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;

//#CM53052
/** 
 * Screen Class of the Hard Zone setting (individual Hard Zone). 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:57:36 $
 * @author  $Author: suresh $
 */
public class IndividuallyHardZoneBusiness extends IndividuallyHardZone implements WMSToolConstants
{
	//#CM53053
	// Class fields --------------------------------------------------

	//#CM53054
	// Class variables -----------------------------------------------

	//#CM53055
	// Class method --------------------------------------------------

	//#CM53056
	// Constructors --------------------------------------------------

	//#CM53057
	// Public methods ------------------------------------------------
	//#CM53058
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM53059
		//Set screen Name. 
		lbl_SettingName.setResourceKey("TMEN-W0005-2");
		//#CM53060
		//Set passing to the Help file. 
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0005-2", this.getHttpRequest()));
		setFocus(txt_Bank);
	}

	//#CM53061
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
			//#CM53062
			//Clear processing
			btn_Clear_Click(null);
			//#CM53063
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.INDIVIDUALLYHARDZONE,Creater.M_CREATE);
			//#CM53064
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			
			//#CM53065
			//<en> Display the pull-down list.</en>
			ToolPulldownData pull = new ToolPulldownData(conn,locale,null);

			//#CM53066
			// Pull down display data (Storage Flag)
			String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "",ToolPulldownData.ZONE_ALL);
			//#CM53067
			// Pull down display data (Zone ID)
			String[] zone = pull.getHardZonePulldownData("");

			//#CM53068
			//Set data in the pull down.
			ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
			ToolPulldownHelper.setPullDown(pul_ZoneId, zone);

			//#CM53069
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
				//#CM53070
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53071
	// Package methods -----------------------------------------------

	//#CM53072
	// Protected methods ---------------------------------------------

	//#CM53073
	// Private methods -----------------------------------------------
	//#CM53074
	/** <en>
	 * Set data to preset area.
	 * @param conn Connection 
	 * @param factory ScheduleInterface 
	 * @throws Exception 
	 </en> */
	private void setList(Connection conn, ScheduleInterface factory) throws Exception
	{
		String warehousenumber = "";
		String zoneid = "";
		String locationnumber = "";
		String bank = "";
		String bay = "";
		String level = "";
		
		//#CM53075
		//Delete all the lines
		lst_HardZoneIndividual.clearRow();

		//#CM53076
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			IndividuallyHardZoneParameter param = (IndividuallyHardZoneParameter) paramarray[i];
			//#CM53077
			//Parameter to be added to List
			warehousenumber = Integer.toString(param.getWarehouseNumber());
			zoneid          = Integer.toString(param.getZoneID());
			bank            = Integer.toString(param.getBank());
			bay             = Integer.toString(param.getBay());
			level           = Integer.toString(param.getLevel());
			locationnumber  = bank + "-" + bay + "-" + level;
			
			//#CM53078
			//Line addition
			//#CM53079
			//The final line is acquired. 
			int count = lst_HardZoneIndividual.getMaxRows();
			lst_HardZoneIndividual.setCurrentRow(count);
			lst_HardZoneIndividual.addRow();
			lst_HardZoneIndividual.setValue(3, warehousenumber);
			lst_HardZoneIndividual.setValue(4, zoneid);
			lst_HardZoneIndividual.setValue(5, locationnumber);
		}
		//#CM53080
		// Display the line under the correction highlighting. 
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_HardZoneIndividual.setHighlight(modindex + 1);
		}
	}
	//#CM53081
	// Event handler methods -----------------------------------------
	//#CM53082
	/**
	 * When the Menu button is clicked, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM53083
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
			//#CM53084
			//When Pulldown is not normally displayed
			if (pul_StoreAs.getSelectedValue() == null)
			{
				//#CM53085
				//6123100 = There is no Warehouse information. Register on the Warehouse setting screen. 
				message.setMsgResourceKey("6123100");
				return;
			}
			if (pul_ZoneId.getSelectedValue() == null)
			{
				//#CM53086
				//6123267 = There is no zone information. Register by the zone setting (range). 
				message.setMsgResourceKey("6123267");
				return;
			}

			//#CM53087
			//Input check
			txt_Bank.validate();
			txt_Bay.validate();
			txt_Level.validate();
			
			//#CM53088
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");

			//#CM53089
			//<en> Set the entered value in schedule parameter.</en>
			IndividuallyHardZoneParameter param = new IndividuallyHardZoneParameter();

			if(pul_StoreAs.getSelectedValue() == null)
			{
				//#CM53090
				//<en> 6123117 = There is no information on the warheouse. Please set in warehouse setting screen.</en>
				message.setMsgResourceKey("6123117");
				return;
			}
			param.setWarehouseNumber(Integer.parseInt(pul_StoreAs.getSelectedValue()));
			param.setZoneID(Integer.parseInt(pul_ZoneId.getSelectedValue()));
			String bank  = txt_Bank.getText();
			String bay   = txt_Bay.getText();
			String level = txt_Level.getText();
			
			param.setBank(Integer.parseInt(bank));
			param.setBay(Integer.parseInt(bay));
			param.setLevel(Integer.parseInt(level));

			if (factory.addParameter(conn, param))
			{
				//#CM53091
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM53092
				//Area for display
				btn_Clear_Click(null);
			}
			//#CM53093
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			//#CM53094
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
				//#CM53095
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53096
	/** 
	 * Mount the processing of a clear button. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		txt_Bank.setText("");
		txt_Bay.setText("");
		txt_Level.setText("");
		pul_StoreAs.setSelectedIndex(0);
		pul_ZoneId.setSelectedIndex(0);
	}

	//#CM53097
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

			//#CM53098
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM53099
			//<en> Start the scheduling.</en>
			if(factory.startScheduler(conn))
			{
				//#CM53100
				//<en> Set the message.</en>
				message.setMsgResourceKey(factory.getMessage());
				//#CM53101
				//<en>Succeeded to delete all data.</en>
				factory.removeAllParameters(conn);
			}
			else
			{
				//#CM53102
				//<en> Set the message.</en>
				message.setMsgResourceKey(factory.getMessage());
			}
			//#CM53103
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
				//#CM53104
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53105
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
			//#CM53106
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM53107
			//Delete all after filtering
			factory.removeAllParameters(conn);
			//#CM53108
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM53109
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
				//#CM53110
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53111
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_HardZoneIndividual_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM53112
			//Line where it corrects, and Deletion is done
			int index = lst_HardZoneIndividual.getActiveRow();
			//**** 修正 ****
			if (lst_HardZoneIndividual.getActiveCol() == 1)
			{
				//#CM53113
				//Current line is set. 
				lst_HardZoneIndividual.setCurrentRow(index);
				//#CM53114
				//Display the highlight in yellow. 
				lst_HardZoneIndividual.setHighlight(index);
				//#CM53115
				//Set parameter under the correction to factory. 
				factory.changeParameter(index - 1);
				//#CM53116
				//<en>Retrieve from factory only the parameters being modified.</en>
				IndividuallyHardZoneParameter param = (IndividuallyHardZoneParameter) factory.getUpdatingParameter();
				//#CM53117
				//Storage Flag
				String whst = Integer.toString(param.getWarehouseNumber());
				pul_StoreAs.setSelectedItem(whst);
				//#CM53118
				//Zone ID
				String zoneid = Integer.toString(param.getZoneID());
				pul_ZoneId.setSelectedItem(zoneid);
				txt_Bank.setText(Integer.toString(param.getBank()));
				txt_Bay.setText(Integer.toString(param.getBay()));
				txt_Level.setText(Integer.toString(param.getLevel()));
			}
			//**** 削除 ****
			else
			{
				//#CM53119
				//Delete 1 after filtering
				factory.removeParameter(conn, index - 1);
				//#CM53120
				//<en> Set the preset data.</en>
				setList(conn, factory);
			}
			//#CM53121
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
				//#CM53122
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM53123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Create_Click(ActionEvent e) throws Exception
	{
	}

	//#CM53126
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53127
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53128
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53129
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53130
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZoneId_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53131
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_ZoneId_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53132
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_ZoneId_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53133
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bank_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53136
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bank_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53137
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bank_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53138
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53140
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bay_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53141
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bay_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53142
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bay_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53143
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53144
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Level_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53145
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Level_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53146
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Level_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53147
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Level_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53149
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53150
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53151
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53152
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZoneIndividual_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53153
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZoneIndividual_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53154
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZoneIndividual_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53155
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZoneIndividual_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM53156
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZoneIndividual_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53157
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZoneIndividual_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM53158
//end of class
