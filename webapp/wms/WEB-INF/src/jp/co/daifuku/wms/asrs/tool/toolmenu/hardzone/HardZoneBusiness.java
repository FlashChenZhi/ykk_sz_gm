// $Id: HardZoneBusiness.java,v 1.2 2006/10/30 04:10:35 suresh Exp $

//#CM52824
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.hardzone;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.HardZoneParameter;
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

//#CM52825
/**
 * Screen Class of the Hard Zone setting (range of Hard Zone). 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:10:35 $
 * @author  $Author: suresh $
 */
public class HardZoneBusiness extends HardZone implements WMSToolConstants
{
	//#CM52826
	// Class fields --------------------------------------------------

	//#CM52827
	// Class variables -----------------------------------------------

	//#CM52828
	// Class method --------------------------------------------------

	//#CM52829
	// Constructors --------------------------------------------------

	//#CM52830
	// Public methods ------------------------------------------------
	//#CM52831
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM52832
		//Set screen Name. 
		lbl_SettingName.setResourceKey("TMEN-W0005-1");
		//#CM52833
		//Set passing to the Help file. 
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0005-1", this.getHttpRequest()));
		setFocus(txt_HardZoneId);
	}

	//#CM52834
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
			//#CM52835
			//Clear processing
			btn_Clear_Click(null);
			//#CM52836
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.HARDZONE, Creater.M_CREATE);
			//#CM52837
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			
			//#CM52838
			//<en> Display the pull-down list.</en>
			ToolPulldownData pull = new ToolPulldownData(conn,locale,null);

			//#CM52839
			// Pull down display data (Storage Flag)
			String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO,"", ToolPulldownData.ZONE_ALL);
			
			//#CM52840
			//Set data in the pull down.
			ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
			
			HardZoneParameter[] array = (HardZoneParameter[])factory.query(conn, locale, null);
			if(array != null)
			{
				for(int i = 0; i < array.length; i++)
				{
					((ToolScheduleInterface)factory).addInitialParameter(array[i]);
				}
			}
			//#CM52841
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
				//#CM52842
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52843
	// Package methods -----------------------------------------------

	//#CM52844
	// Protected methods ---------------------------------------------

	//#CM52845
	// Private methods -----------------------------------------------
	//#CM52846
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
		String height = "";
		String loadsizename = "";
		String priority = "";
		String bank = "";
		String bay = "";
		String level = "";
		String prioritychar = "";
		String fbank = "";
		String fbay = "";
		String flevel = "";
		String tbank = "";
		String tbay = "";
		String tlevel = "";
		
		//#CM52847
		///Plaque of TOOL TIP
		String title_loadsizename = DisplayText.getText("TLBL-W0009");
		String title_priority     = DisplayText.getText("TLBL-W0094");

		//#CM52848
		//Delete all the lines
		lst_HardZone.clearRow();

		//#CM52849
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			HardZoneParameter param = (HardZoneParameter) paramarray[i];
			//#CM52850
			//Parameter to be added to List
			warehousenumber = getWHNumber(conn, param.getWareHouseStationNumber());
			zoneid          = Integer.toString(param.getZoneID());
			height          = Integer.toString(param.getHeight());
			loadsizename    = param.getZoneName();
			fbank           = Integer.toString(param.getStartBank());
			fbay            = Integer.toString(param.getStartBay());
			flevel          = Integer.toString(param.getStartLevel());
			tbank           = Integer.toString(param.getEndBank());
			tbay            = Integer.toString(param.getEndBay());
			tlevel          = Integer.toString(param.getEndLevel());
			bank            = fbank  + " - " + tbank;
			bay             = fbay   + " - " + tbay;
			level           = flevel + " - " + tlevel;
			priority        = param.getPriority();
			if (priority != null)
			{
				priority = priority.trim();
				String castpriority = "";
				for (int count = 0; count < priority.length() ; count++)
				{
					if (count != 0)
					{
						castpriority = castpriority + "-";
					}
					castpriority = castpriority + priority.substring(count,count + 1);
				}
				prioritychar = castpriority;
			}

			//#CM52851
			//Line addition
			//#CM52852
			//The final line is acquired. 
			int count = lst_HardZone.getMaxRows();
			lst_HardZone.setCurrentRow(count);
			lst_HardZone.addRow();
			lst_HardZone.setValue(0, prioritychar);
			lst_HardZone.setValue(3, warehousenumber);
			lst_HardZone.setValue(4, zoneid);
			lst_HardZone.setValue(5, height);
			lst_HardZone.setValue(6, loadsizename);
			lst_HardZone.setValue(7, bank);
			lst_HardZone.setValue(8, bay);
			lst_HardZone.setValue(9, level);
			lst_HardZone.setValue(10, prioritychar);

			//#CM52853
			//The character string displayed in TOOL TIP is made. 
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_loadsizename, loadsizename);
			toolTip.add(title_priority, prioritychar);

			
			//#CM52854
			//Set TOOL TIP. 	
			lst_HardZone.setToolTip(count, toolTip.getText());

		}
		//#CM52855
		// Display the line under the correction highlighting. 
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_HardZone.setHighlight(modindex + 1);
		}
	}
	//#CM52856
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
	//#CM52857
	/** 
	 * Acquire Warehouse Station Number from specified Warehouse Number. 
	 * @param  conn Connection
	 * @param  whNo Warehouse Number
	 * @return Warehouse Station Number
	 * @throws Exception 
	 */
	private String getWHSTNumber(Connection conn, String whNo) throws Exception
	{
		ToolWarehouseSearchKey	wareKey = new ToolWarehouseSearchKey();
		ToolWarehouseHandler	warehd  = new ToolWarehouseHandler(conn);
		if (whNo == null)
		{
			return "9999";
		}
		wareKey.setWarehouseNumber(Integer.parseInt(whNo));
		Warehouse house [] = (Warehouse[])warehd.find(wareKey);
		if (house.length <= 0 )
		{
			return "9999";
		}
		return house[0].getNumber();
	}
	//#CM52858
	// Event handler methods -----------------------------------------
	//#CM52859
	/**
	 * When the Menu button is clicked, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM52860
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
			//#CM52861
			//When Pulldown is not normally displayed
			if (pul_StoreAs.getSelectedValue() == null)
			{
				//#CM52862
				//6123100 = There is no Warehouse information. Register on the Warehouse setting screen. 
				message.setMsgResourceKey("6123100");
				return;
			}
			//#CM52863
			//Input check
			txt_HardZoneId.validate();
			txt_Height.validate();
			txt_LoadSizeName.validate();
			txt_FBank.validate();
			txt_TBank.validate();
			txt_FBay.validate();
			txt_TBay.validate();
			txt_FLevel.validate();
			txt_TLevel.validate();
			txt_ZonePriority2.validate(false);
			txt_ZonePriority3.validate(false);
			txt_ZonePriority4.validate(false);
			txt_ZonePriority5.validate(false);
			txt_ZonePriority6.validate(false);
			txt_ZonePriority7.validate(false);
			txt_ZonePriority8.validate(false);
			txt_ZonePriority9.validate(false);
			txt_ZonePriority10.validate(false);
			
			//#CM52864
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");

			//#CM52865
			//<en> Set the entered value in schedule parameter.</en>
			HardZoneParameter param = new HardZoneParameter();

			param.setZoneID(Integer.parseInt(txt_HardZoneId.getText()));
			param.setHeight(Integer.parseInt(txt_Height.getText()));
			param.setZoneName(txt_LoadSizeName.getText());
			param.setWareHouseStationNumber(getWHSTNumber(conn, pul_StoreAs.getSelectedValue()));
			param.setStartBank(Integer.parseInt(txt_FBank.getText()));
			param.setStartBay(Integer.parseInt(txt_FBay.getText()));
			param.setStartLevel(Integer.parseInt(txt_FLevel.getText()));
			param.setEndBank(Integer.parseInt(txt_TBank.getText()));
			param.setEndBay(Integer.parseInt(txt_TBay.getText()));
			param.setEndLevel(Integer.parseInt(txt_TLevel.getText()));
			
			//#CM52866
			//<en> Set teh priority. (Any blanks should be ommitted by closing up )</en>
			String priority = txt_HardZoneId.getText();
			priority += txt_ZonePriority2.getText().trim();
			priority += txt_ZonePriority3.getText().trim();
			priority += txt_ZonePriority4.getText().trim();
			priority += txt_ZonePriority5.getText().trim();
			priority += txt_ZonePriority6.getText().trim();
			priority += txt_ZonePriority7.getText().trim();
			priority += txt_ZonePriority8.getText().trim();
			priority += txt_ZonePriority9.getText().trim();
			priority += txt_ZonePriority10.getText().trim();
			param.setPriority(priority);
			
			if (factory.addParameter(conn, param))
			{
				//#CM52867
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM52868
				//Area for display
				btn_Clear_Click(null);
			}
			//#CM52869
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			//#CM52870
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
				//#CM52871
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52872
	/** 
	 * Mount the processing of a clear button. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		pul_StoreAs.setSelectedIndex(0);
		txt_HardZoneId.setText("");
		txt_Height.setText("");
		txt_LoadSizeName.setText("");
		txt_FBank.setText("");
		txt_TBank.setText("");
		txt_FBay.setText("");
		txt_TBay.setText("");
		txt_FLevel.setText("");
		txt_TLevel.setText("");
		txt_ZonePriority2.setText("");
		txt_ZonePriority3.setText("");
		txt_ZonePriority4.setText("");
		txt_ZonePriority5.setText("");
		txt_ZonePriority6.setText("");
		txt_ZonePriority7.setText("");
		txt_ZonePriority8.setText("");
		txt_ZonePriority9.setText("");
		txt_ZonePriority10.setText("");
	}

	//#CM52873
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

			//#CM52874
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52875
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			
			//#CM52876
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM52877
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
				//#CM52878
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52879
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
			//#CM52880
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52881
			//Delete all after filtering
			factory.removeAllParameters(conn);
			//#CM52882
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM52883
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
				//#CM52884
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}


	//#CM52885
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_HardZone_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52886
			//Line where it corrects, and Deletion is done
			int index = lst_HardZone.getActiveRow();
			//**** 修正 ****
			if (lst_HardZone.getActiveCol() == 1)
			{
				//#CM52887
				//Clear processing
				btn_Clear_Click(null);
				//#CM52888
				//Current line is set. 
				lst_HardZone.setCurrentRow(index);
				//#CM52889
				//Display the highlight in yellow. 
				lst_HardZone.setHighlight(index);
				//#CM52890
				//Set parameter under the correction to factory. 
				factory.changeParameter(index - 1);
				//#CM52891
				//<en>Retrieve from factory only the parameters being modified.</en>
				HardZoneParameter param = (HardZoneParameter) factory.getUpdatingParameter();
				
				//#CM52892
				//Storage Flag
				String whst = getWHNumber(conn, param.getWareHouseStationNumber());
				pul_StoreAs.setSelectedItem(whst);
				
				//#CM52893
				//Set the value maintained in parameter on the screen. 
				txt_HardZoneId.setText(Integer.toString(param.getZoneID()));
				txt_Height.setText(Integer.toString(param.getHeight()));
				txt_LoadSizeName.setText(param.getZoneName());
				txt_FBank.setText(Integer.toString(param.getStartBank()));
				txt_FBay.setText(Integer.toString(param.getStartBay()));
				txt_FLevel.setText(Integer.toString(param.getStartLevel()));
				txt_TBank.setText(Integer.toString(param.getEndBank()));
				txt_TBay.setText(Integer.toString(param.getEndBay()));
				txt_TLevel.setText(Integer.toString(param.getEndLevel()));

				//#CM52894
				//Set the priority level. 
				String priority = param.getPriority();
				if (priority != null)
				{
					priority = priority.trim();
					String castpriority = "";
					for (int l = 0; l < priority.length() ; l++)
					{
						if (l != 0)
						{
							castpriority = priority.substring(l,l + 1);
							switch (l+1)
							{
								case 2:
									txt_ZonePriority2.setText(castpriority);
									break ;
								case 3:
									txt_ZonePriority3.setText(castpriority);
									break ;
								case 4:
									txt_ZonePriority4.setText(castpriority);
									break ;
								case 5:
									txt_ZonePriority5.setText(castpriority);
									break ;
								case 6:
									txt_ZonePriority6.setText(castpriority);
									break ;
								case 7:
									txt_ZonePriority7.setText(castpriority);
									break ;
								case 8:
									txt_ZonePriority8.setText(castpriority);
									break ;
								case 9:
									txt_ZonePriority9.setText(castpriority);
									break ;
								case 10:
									txt_ZonePriority10.setText(castpriority);
									break ;
								default:
							}
						}
					}
				}
			}
			//**** 削除 ****
			else
			{
				//#CM52895
				//Delete 1 after filtering
				factory.removeParameter(conn, index - 1);
				//#CM52896
				//<en> Set the preset data.</en>
				setList(conn, factory);
			}
			//#CM52897
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
				//#CM52898
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM52899
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52900
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52901
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Create_Click(ActionEvent e) throws Exception
	{
	}

	//#CM52902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52903
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52904
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52905
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM52906
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZoneId_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52907
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HardZoneId_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52908
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HardZoneId_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52909
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HardZoneId_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52910
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Height_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52911
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Height_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52912
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Height_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52913
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Height_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52914
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CarryName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52915
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LoadSizeName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52916
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LoadSizeName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52917
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LoadSizeName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52918
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LoadSizeName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Range_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52920
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Bank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52921
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52922
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBank_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBank_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBank_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBank_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBank_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBank_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Bay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBay_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBay_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBay_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52935
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBay_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBay_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBay_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Level_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FLevel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FLevel_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FLevel_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FLevel_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen3_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52946
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TLevel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52947
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TLevel_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52948
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TLevel_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52949
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TLevel_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52950
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52951
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority2_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52954
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority2_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52955
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority2_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority3_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52957
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority3_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52958
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority3_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52959
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority3_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52960
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority3_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52961
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority4_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52962
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority4_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52963
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority4_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52964
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority4_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52965
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority4_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority5_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52967
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority5_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52968
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority5_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52969
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority5_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52970
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority5_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority6_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority6_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52973
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority6_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52974
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority6_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52975
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority6_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52976
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority7_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52977
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority7_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52978
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority7_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52979
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority7_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52980
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority7_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52981
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority8_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52982
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority8_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52983
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority8_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52984
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority8_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52985
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority8_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52986
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority9_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52987
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority9_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52988
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority9_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52989
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority9_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52990
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority9_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52991
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ZonePriority10_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52992
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority10_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52993
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority10_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52994
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority10_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52995
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ZonePriority10_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52996
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52997
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52998
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52999
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53000
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZone_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53001
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZone_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53002
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZone_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53003
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZone_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM53004
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZone_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53005
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_HardZone_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM53006
//end of class
