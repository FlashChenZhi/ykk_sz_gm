// $Id: WareHouseBusiness.java,v 1.3 2006/10/30 06:31:01 suresh Exp $

//#CM54748
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.warehouse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.schedule.WarehouseParameter;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;

//#CM54749
/**
 * The screen class of the location setting (warehouse).
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/30 06:31:01 $
 * @author  $Author: suresh $
 */
public class WareHouseBusiness extends WareHouse implements WMSToolConstants
{
	//#CM54750
	// Class fields --------------------------------------------------

	//#CM54751
	// Class variables -----------------------------------------------

	//#CM54752
	// Class method --------------------------------------------------

	//#CM54753
	// Constructors --------------------------------------------------

	//#CM54754
	// Public methods ------------------------------------------------
	//#CM54755
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM54756
		//Set the screen name
		lbl_SettingName.setResourceKey("TMEN-W0004-1");
		//#CM54757
		//Set the path of help file.
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0004-1", this.getHttpRequest()));
		setFocus(txt_WareHouseNumber);
	}

	//#CM54758
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
			//#CM54759
			//Clear processing
			btn_Clear_Click(null);
			//#CM54760
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.WAREHOUSE, Creater.M_CREATE);
			//#CM54761
			//It maintains it in the session.
			this.getSession().setAttribute("FACTORY", factory);
			
			//#CM54762
			//<en>Set the name of the file the data will be saved.</en>
			WarehouseParameter searchParam = new WarehouseParameter();
			searchParam.setFilePath((String)this.getSession().getAttribute("WorkFolder"));

			WarehouseParameter[] array = (WarehouseParameter[])factory.query(conn, locale, searchParam);
			if(array != null)
			{
				for(int i = 0; i < array.length; i++)
				{
					((ToolScheduleInterface)factory).addInitialParameter(array[i]);
				}
			}
			//#CM54763
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
				//#CM54764
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54765
	// Package methods -----------------------------------------------

	//#CM54766
	// Protected methods ---------------------------------------------

	//#CM54767
	// Private methods -----------------------------------------------
	//#CM54768
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
		String warehousename = "";
		String warehousetype = "";
		String employmenttype = "";
		String maxmixedqty = "";

		//#CM54769
		//All lines are deleted.
		lst_WareHouse.clearRow();

		//#CM54770
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			WarehouseParameter param = (WarehouseParameter) paramarray[i];
			//#CM54771
			//Parameter added to list
			warehousenumber = Integer.toString(param.getWarehouseNumber());
			stationnumber   = param.getStationNumber();
			warehousename   = param.getWarehouseName();
			//#CM54772
			//<en> warehouse type</en>
			int warehousetypenum = param.getWarehouseType();
			if(warehousetypenum == 0)
			{
				warehousetypenum = Warehouse.AUTOMATID_WAREHOUSE ;
			}
			warehousetype = DisplayText.getText("WAREHOUSE", "WAREHOUSETYPE", Integer.toString(warehousetypenum));
			//#CM54773
			//<en> division of automated warehouse operation</en>
			int employmenttypenum = param.getEmploymentType();
			if(employmenttypenum == 0)
			{
				employmenttypenum = Warehouse.OPEN ;
			}
			if(warehousetypenum == Warehouse.CONVENTIONAL_WAREHOUSE)
			{
				employmenttype = DisplayText.getText("TLBL-W0069");
			}
			else
			{
				employmenttype = DisplayText.getText("WAREHOUSE", "EMPLOYMENTTYPE", Integer.toString(employmenttypenum));
			}

			maxmixedqty = Integer.toString(param.getMaxMixedQuantity());
			
			//#CM54774
			//Line addition
			//#CM54775
			//The final line is acquired.
			int count = lst_WareHouse.getMaxRows();
			lst_WareHouse.setCurrentRow(count);
			lst_WareHouse.addRow();
			lst_WareHouse.setValue(3, warehousenumber);
			lst_WareHouse.setValue(4, stationnumber);
			lst_WareHouse.setValue(5, warehousename);
			lst_WareHouse.setValue(6, warehousetype);
			lst_WareHouse.setValue(7, employmenttype);
			lst_WareHouse.setValue(8, maxmixedqty);
		}
		//#CM54776
		// Display the line under the correction highlighting.
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_WareHouse.setHighlight(modindex + 1);
		}
	}
	//#CM54777
	// Event handler methods -----------------------------------------
	//#CM54778
	/**
	 * Called when Menu button is clicked
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM54779
	/** 
	 * It is called when the automatic warehouse radiobutton is clicked.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AutomaticWareHouseSystem_Click(ActionEvent e) throws Exception
	{
		rdo_Open.setEnabled(true);
		rdo_Close.setEnabled(true);
		if (rdo_Open.getChecked())
		{
			rdo_Open.setChecked(true);
			rdo_Close.setChecked(false);
		}
		else if (rdo_Close.getChecked())
		{
			rdo_Open.setChecked(false);
			rdo_Close.setChecked(true);
		}
	}

	//#CM54780
	/** 
	 * It is called when a flat O radiobutton is clicked.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_FloorLoadingWareHouseSys_Click(ActionEvent e) throws Exception
	{
		rdo_Open.setEnabled(false);
		rdo_Close.setEnabled(false);
		if (rdo_Open.getChecked())
		{
			rdo_Open.setChecked(true);
			rdo_Close.setChecked(false);
		}
		else if (rdo_Close.getChecked())
		{
			rdo_Open.setChecked(false);
			rdo_Close.setChecked(true);
		}
	}

	//#CM54781
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
			//#CM54782
			//Input check
			txt_WareHouseNumber.validate();
			txt_StNumber.validate();
			txt_WareHouseName.validate();
			txt_MaxMixedQty.validate();
			
			//#CM54783
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");

			//#CM54784
			//<en> Set the entered value in schedule parameter.</en>
			WarehouseParameter param = new WarehouseParameter();

			param.setWarehouseNumber(Integer.parseInt(txt_WareHouseNumber.getText()));
			param.setStationNumber(txt_StNumber.getText());
			param.setWarehouseName(txt_WareHouseName.getText());

			int warehousetype = Warehouse.CONVENTIONAL_WAREHOUSE;
			int employmenttype = Warehouse.OPEN;
			RadioButton rdowarehousetype = rdo_FloorLoadingWareHouseSys.getSelectedItem();
			if (rdowarehousetype.getId().equals("rdo_AutomaticWareHouseSystem"))
			{
				warehousetype = Warehouse.AUTOMATID_WAREHOUSE;
				RadioButton rdoemploymenttype = rdo_Open.getSelectedItem();
				if (rdoemploymenttype.getId().equals("rdo_Close"))
				{
					employmenttype = Warehouse.CLOSE;
				}
			}
	
			param.setWarehouseType(warehousetype);
			param.setEmploymentType(employmenttype);
			param.setMaxMixedQuantity(txt_MaxMixedQty.getInt());

			if (factory.addParameter(conn, param))
			{
				//#CM54785
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM54786
				//Clear display area.
				btn_Clear_Click(null);
			}
			//#CM54787
			//Maintain it in the session.
			this.getSession().setAttribute("FACTORY", factory);
			//#CM54788
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
				//#CM54789
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54790
	/** 
	 * Mount the processing of a clear button.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		txt_WareHouseNumber.setText("");
		txt_StNumber.setText("");
		txt_WareHouseName.setText("");
		rdo_AutomaticWareHouseSystem.setChecked(true);
		rdo_FloorLoadingWareHouseSys.setChecked(false);
		rdo_Open.setChecked(true);
		rdo_Close.setChecked(false);
		txt_MaxMixedQty.setText("");
		rdo_Open.setEnabled(true);
		rdo_Close.setEnabled(true);
	}

	//#CM54791
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

			//#CM54792
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54793
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			
			rdo_AutomaticWareHouseSystem.setChecked(true);
			rdo_FloorLoadingWareHouseSys.setChecked(false);
			rdo_Open.setChecked(true);
			rdo_Close.setChecked(false);
			//#CM54794
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM54795
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
				//#CM54796
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54797
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
			//#CM54798
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54799
			//All deletions after filtering
			factory.removeAllParameters(conn);
			//#CM54800
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM54801
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
				//#CM54802
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54803
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_WareHouse_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54804
			//Line that is corrected, and deleted
			int index = lst_WareHouse.getActiveRow();
			//**** 修正 ****
			if (lst_WareHouse.getActiveCol() == 1)
			{
				//#CM54805
				//A present line is set.
				lst_WareHouse.setCurrentRow(index);
				//#CM54806
				//Display the highlight in yellow.
				lst_WareHouse.setHighlight(index);
				//#CM54807
				//Set the parameter under the correction to factory.
				factory.changeParameter(index - 1);
				//#CM54808
				//<en>Retrieve from factory only the parameters being modified.</en>
				WarehouseParameter param = (WarehouseParameter) factory.getUpdatingParameter();
				//#CM54809
				//Storage division
				txt_WareHouseNumber.setText(Integer.toString(param.getWarehouseNumber()));
				//#CM54810
				//Station No.
				txt_StNumber.setText(param.getStationNumber());
				//#CM54811
				//Warehouse name
				txt_WareHouseName.setText(param.getWarehouseName());
				rdo_Open.setEnabled(true);
				rdo_Close.setEnabled(true);
				//#CM54812
				//Warehouse type
				if(param.getWarehouseType() == Warehouse.AUTOMATID_WAREHOUSE)
				{
					rdo_AutomaticWareHouseSystem.setChecked(true);
					rdo_FloorLoadingWareHouseSys.setChecked(false);
					//#CM54813
					//Automatic warehouse operation type
					if(param.getEmploymentType() == Warehouse.OPEN)
					{
						rdo_Open.setChecked(true);
						rdo_Close.setChecked(false);
					}
					else
					{
						rdo_Open.setChecked(false);
						rdo_Close.setChecked(true);
					}
				}
				else
				{
					rdo_AutomaticWareHouseSystem.setChecked(false);
					rdo_FloorLoadingWareHouseSys.setChecked(true);
					//#CM54814
					//Automatic warehouse operation type
					rdo_Open.setChecked(true);
					rdo_Close.setChecked(false);
					rdo_Open.setEnabled(false);
					rdo_Close.setEnabled(false);
				}

				//#CM54815
				//Automatic warehouse operation type
				//#CM54816
				//Number of maximum consolidatio
				txt_MaxMixedQty.setText(Formatter.getNumFormat(param.getMaxMixedQuantity()));
			}
			//**** 削除 ****
			else
			{
				//#CM54817
				//Deleted one after filtering.
				factory.removeParameter(conn, index - 1);
				//#CM54818
				//<en> Set the preset data.</en>
				setList(conn, factory);
			}
			//#CM54819
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
				//#CM54820
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM54821
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54822
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54823
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Create_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54824
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54825
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54826
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54827
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WareHouseNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54828
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WareHouseNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54829
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54830
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54831
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54832
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54833
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54834
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54835
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WareHouseName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54836
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WareHouseName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54837
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WareHouseName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54838
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WareHouseName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54839
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54840
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_AutomaticWareHouseSystem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54841
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_FloorLoadingWareHouseSys_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54842
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AWCUseType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54843
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Open_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54844
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Open_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54845
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Close_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54846
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Close_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54847
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxMixedQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54848
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MaxMixedQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54849
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MaxMixedQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54850
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MaxMixedQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54851
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54852
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54853
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54854
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54855
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WareHouse_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54856
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WareHouse_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54857
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WareHouse_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54858
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WareHouse_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM54859
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54860
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WareHouse_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM54861
//end of class
