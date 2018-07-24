// $Id: DummyStationBusiness.java,v 1.2 2006/10/30 04:08:48 suresh Exp $

//#CM52626
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.dummystation;
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
import jp.co.daifuku.wms.asrs.tool.schedule.StationParameter;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
//#CM52627
/**
 * Screen Class of the Station setting (dummy Station). 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:08:48 $
 * @author  $Author: suresh $
 */
public class DummyStationBusiness extends DummyStation implements WMSToolConstants
{
	//#CM52628
	// Class fields --------------------------------------------------

	//#CM52629
	// Class variables -----------------------------------------------

	//#CM52630
	// Class method --------------------------------------------------

	//#CM52631
	// Constructors --------------------------------------------------

	//#CM52632
	// Public methods ------------------------------------------------
	//#CM52633
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM52634
		//Set screen Name. 
		lbl_SettingName.setResourceKey("TMEN-W0007-2");
		//#CM52635
		//Set passing to the Help file. 
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0007-2", this.getHttpRequest()));
		setFocus(txt_StNumber);
	}

	//#CM52636
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
			//#CM52637
			//Clear processing
			btn_Clear_Click(null);
			//#CM52638
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.DUMMYSTATION,Creater.M_CREATE);
			//#CM52639
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			
			//#CM52640
			//<en> Display the pull-down list.</en>
			ToolPulldownData pull = new ToolPulldownData(conn,locale,null);

			//#CM52641
			// Pull down display data (Storage Flag)
			String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "");
			//#CM52642
			// Pull down display data (AGCNo.)
			String[] agc = pull.getGroupControllerPulldownData("");

			//#CM52643
			//Set data in the pull down.
			ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
			ToolPulldownHelper.setPullDown(pul_AGCNo, agc);

			//#CM52644
			//<en>Set the name of the file the data will be saved.</en>
			StationParameter searchParam = new StationParameter();
			searchParam.setFilePath((String)this.getSession().getAttribute("WorkFolder"));

			StationParameter[] array = (StationParameter[])factory.query(conn, locale, searchParam);
			if(array != null)
			{
				for(int i = 0; i < array.length; i++)
				{
					((ToolScheduleInterface)factory).addInitialParameter(array[i]) ;
				}
			}
			//#CM52645
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
				//#CM52646
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52647
	// Package methods -----------------------------------------------

	//#CM52648
	// Protected methods ---------------------------------------------

	//#CM52649
	// Private methods -----------------------------------------------
	//#CM52650
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
		String agcnumber = "";

		//#CM52651
		//Delete all the lines
		lst_DummyStation.clearRow();

		//#CM52652
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			StationParameter param = (StationParameter) paramarray[i];
			
			//#CM52653
			//Parameter to be added to List
			warehousenumber = getWHNumber(conn, param.getWareHouseStationNumber());
			stationnumber   = param.getNumber();
			stationname     = param.getName();
			agcnumber       = Integer.toString(param.getControllerNumber());

			//#CM52654
			//Line addition
			//#CM52655
			//The final line is acquired. 
			int count = lst_DummyStation.getMaxRows();
			lst_DummyStation.setCurrentRow(count);
			lst_DummyStation.addRow();
			lst_DummyStation.setValue(3, warehousenumber);
			lst_DummyStation.setValue(4, stationnumber);
			lst_DummyStation.setValue(5, stationname);
			lst_DummyStation.setValue(6, agcnumber);
		}
		//#CM52656
		// Display the line under the correction highlighting. 
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_DummyStation.setHighlight(modindex + 1);
		}
	}
	//#CM52657
	/**
	 * Acquire Warehouse Number from specified Warehouse Station Number. 
	 * @param  whStNo Warehouse Station Number
	 * @param  conn Connection
	 * @return Warehouse Number
	 * @throws Exception 
	 */
	private String getWHNumber(Connection conn, String whStNo) throws Exception
	{
		ToolWarehouseSearchKey	wareKey = new ToolWarehouseSearchKey();
		ToolWarehouseHandler	warehd  = new ToolWarehouseHandler(conn) ;
		wareKey.setWarehouseStationNumber(whStNo);
		Warehouse house [] = (Warehouse[])warehd.find(wareKey);
		if (house.length <= 0 )
		{
			return "" ;
		}
		return Integer.toString( house[0].getWarehouseNumber()) ;
	}

	//#CM52658
	/**
	 * Acquire Warehouse Station Number from specified Warehouse Number. 
	 * @param  whNo Warehouse Number
	 * @param  conn Connection
	 * @return Warehouse Station Number
	 * @throws Exception 
	 */
	private String getWHSTNumber(Connection conn, String whNo) throws Exception
	{
		ToolWarehouseSearchKey	wareKey = new ToolWarehouseSearchKey() ;
		ToolWarehouseHandler	warehd  = new ToolWarehouseHandler(conn) ;
		wareKey.setWarehouseNumber( Integer.parseInt(whNo) ) ;
		Warehouse house [] = (Warehouse[])warehd.find(wareKey) ;
		if (house.length <= 0 )
		{
			return "" ;
		}
		return house[0].getNumber() ;
	}
	//#CM52659
	// Event handler methods -----------------------------------------
	//#CM52660
	/**
	 * When the Menu button is clicked, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM52661
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
			//#CM52662
			//Input check
			//#CM52663
			//When Pulldown is not normally displayed
			if (pul_StoreAs.getSelectedValue() == null)
			{
				//#CM52664
				//6123100 = There is no Warehouse information. Register on the Warehouse setting screen. 
				message.setMsgResourceKey("6123100");
				return;
			}
			txt_StNumber.validate();
			txt_StationName.validate();
			if (pul_AGCNo.getSelectedValue() == null)
			{
				//#CM52665
				//6123078 = There is no information of the controller of the group. Register on the setting of the controller of the group screen. 
				message.setMsgResourceKey("6123078");
				return;
			}

			//#CM52666
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			StationParameter param = new StationParameter();
			param.setWareHouseStationNumber( getWHSTNumber(conn, pul_StoreAs.getSelectedValue()) ) ;
			param.setNumber(txt_StNumber.getText()) ;
			param.setName(txt_StationName.getText()) ;
			param.setControllerNumber( Integer.parseInt(pul_AGCNo.getSelectedValue()) ) ;

			if (factory.addParameter(conn, param))
			{
				//#CM52667
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM52668
				//Area for display
				btn_Clear_Click(null);
			}
			//#CM52669
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			//#CM52670
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
				//#CM52671
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52672
	/** 
	 * Mount the processing of a clear button. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		txt_StNumber.setText("");
		txt_StationName.setText("");
		pul_StoreAs.setSelectedIndex(0);
		pul_AGCNo.setSelectedIndex(0);
	}

	//#CM52673
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

			//#CM52674
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52675
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			//#CM52676
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM52677
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
				//#CM52678
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52679
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
			//#CM52680
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52681
			//Delete all after filtering
			factory.removeAllParameters(conn);
			//#CM52682
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM52683
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
				//#CM52684
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52685
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_DummyStation_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52686
			//Line where it corrects, and Deletion is done
			int index = lst_DummyStation.getActiveRow();
			//**** 修正 ****
			if (lst_DummyStation.getActiveCol() == 1)
			{
				//#CM52687
				//Current line is set. 
				lst_DummyStation.setCurrentRow(index);
				//#CM52688
				//Display the highlight in yellow. 
				lst_DummyStation.setHighlight(index);
				//#CM52689
				//Set parameter under the correction to factory. 
				factory.changeParameter(index - 1);
				//#CM52690
				//<en>Retrieve from factory only the parameters being modified.</en>
				StationParameter param = (StationParameter) factory.getUpdatingParameter();
				//#CM52691
				//Storage Flag
				String whst = getWHNumber(conn, param.getWareHouseStationNumber());
				pul_StoreAs.setSelectedItem(whst);
				//#CM52692
				//Station No.
				txt_StNumber.setText(param.getNumber());
				//#CM52693
				//StationName
				txt_StationName.setText(param.getName());
				//#CM52694
				//AGCNo.
				String agcnumber = Integer.toString(param.getControllerNumber());
				pul_AGCNo.setSelectedItem(agcnumber);
			}
			//**** 削除 ****
			else
			{
				//#CM52695
				//Delete 1 after filtering
				factory.removeParameter(conn, index - 1);
				//#CM52696
				//<en> Set the preset data.</en>
				setList(conn, factory);
			}
			//#CM52697
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
				//#CM52698
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM52699
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52700
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52701
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Create_Click(ActionEvent e) throws Exception
	{
	}

	//#CM52702
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52703
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52704
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52705
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM52706
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52707
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52708
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52709
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52710
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52711
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52712
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StationName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52713
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StationName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52714
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StationName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52715
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StationName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52716
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AgcNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52717
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_AGCNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52718
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_AGCNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM52719
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52720
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52721
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52722
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52723
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DummyStation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52724
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DummyStation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DummyStation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DummyStation_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM52727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DummyStation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52728
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DummyStation_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM52729
//end of class
