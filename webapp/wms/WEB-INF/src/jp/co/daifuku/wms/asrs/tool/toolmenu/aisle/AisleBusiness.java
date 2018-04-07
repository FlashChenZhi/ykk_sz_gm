// $Id: AisleBusiness.java,v 1.2 2006/10/30 04:06:45 suresh Exp $

//#CM52359
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.aisle;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.schedule.AisleParameter;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;

//#CM52360
/**
 * Aisle setting screen class
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:06:45 $
 * @author  $Author: suresh $
 */
public class AisleBusiness extends Aisle implements WMSToolConstants
{
	//#CM52361
	// Class fields --------------------------------------------------

	//#CM52362
	// Class variables -----------------------------------------------

	//#CM52363
	// Class method --------------------------------------------------

	//#CM52364
	// Constructors --------------------------------------------------

	//#CM52365
	// Public methods ------------------------------------------------

	//#CM52366
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM52367
		//Set screen Name. 
		lbl_SettingName.setResourceKey("TMEN-W0004-2");
		//#CM52368
		//Set passing to the Help file. 
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0004-2", this.getHttpRequest()));
		
		setFocus(txt_StNumber);
	}

	//#CM52369
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
			//#CM52370
			//Clear processing
			btn_Clear_Click(null);
			//#CM52371
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			ScheduleInterface factory = new CreaterFactory(conn, CreaterFactory.AISLE,Creater.M_CREATE);
			//#CM52372
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);

			ToolPulldownData pull = new ToolPulldownData(conn, locale, null);
	
			//#CM52373
			//<en> Set the pull-down data.</en>
			String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "");
			String[] agcno = pull.getGroupControllerPulldownData("");

			//#CM52374
			//Set data in the pull down.
			ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
			ToolPulldownHelper.setPullDown(pul_AGCNo, agcno);

			//#CM52375
			//<en>Set the name of saving file.</en>
			AisleParameter searchParam = new AisleParameter();
			searchParam.setFilePath((String)this.getSession().getAttribute("WorkFolder"));

			AisleParameter[] array = (AisleParameter[])factory.query(conn, locale, searchParam);
			if(array != null)
			{
				for(int i = 0; i < array.length; i++)
				{
					((ToolScheduleInterface)factory).addInitialParameter(array[i]);
				}
			}
			
			setList(conn, factory);

		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM52376
				//Close the Connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52377
	// Package methods -----------------------------------------------

	//#CM52378
	// Protected methods ---------------------------------------------

	//#CM52379
	// Private methods -----------------------------------------------
	//#CM52380
	/** <en>
	 * Set data to preset area.
	 * @param conn Connection 
	 * @param factoryScheduleInterface 
	 * @throws Exception 
	 </en> */
	private void setList(Connection conn, ScheduleInterface factory) throws Exception
	{
		String whno = "";
		String stno = "";
		String aisleno = "";
		String agcno = "";
		String bank = "";
		String bay = "";
		String level = "";
		String aisleposition = "";

		//#CM52381
		//Delete all the lines
		lst_Aisle.clearRow();

		//#CM52382
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length ; i++)
		{
			AisleParameter param = (AisleParameter)paramarray[i];
			//#CM52383
			//Parameter to be added to List
			if (param.getWarehouseNumber() == 9999)
			{
				whno = "*";
			}
			else
			{
				whno = Integer.toString(param.getWarehouseNumber());
			}
			stno = param.getAisleStationNumber();
			aisleno = Integer.toString(param.getAisleNumber());
			if (param.getAGCNumber() == 9999)
			{
				agcno = "*";
			}
			else
			{
				agcno = Integer.toString(param.getAGCNumber());
			}
			bank = Integer.toString(param.getSBank()) +" - " + Integer.toString(param.getEBank());
			bay = Integer.toString(param.getSBay()) +" - " + Integer.toString(param.getEBay());
			level = Integer.toString(param.getSLevel()) +" - " + Integer.toString(param.getELevel());
			if (param.getSAislePosition() == 0)
			{
				aisleposition = "-";
			}
			else
			{
				aisleposition = Integer.toString(param.getSAislePosition()) +" - " + Integer.toString(param.getEAislePosition());
			}
			//#CM52384
			//Line addition
			//#CM52385
			//The final line is acquired. 
			int count = lst_Aisle.getMaxRows();
			lst_Aisle.setCurrentRow(count);
			lst_Aisle.addRow();
			lst_Aisle.setValue(3, whno);
			lst_Aisle.setValue(4, stno);
			lst_Aisle.setValue(5, aisleno);
			lst_Aisle.setValue(6, agcno);
			lst_Aisle.setValue(7, bank);
			lst_Aisle.setValue(8, bay);
			lst_Aisle.setValue(9, level);			
			lst_Aisle.setValue(10, aisleposition);			
		}

		//#CM52386
		// Display the line under the correction highlighting. 
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_Aisle.setHighlight(modindex + 1);
		}
	}

	//#CM52387
	// Event handler methods -----------------------------------------
	//#CM52388
	/**
	 * When the Menu button is clicked, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM52389
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
			//#CM52390
			//<en>In case the pull-down is not correctly displayed,</en>
			if (pul_StoreAs.getSelectedValue() == null)
			{
				//#CM52391
				//<en>6123117 = There is no warehouse control information. </en>
				//<en>Please register in warehouse setting screen.</en>
				message.setMsgResourceKey("6123117");
				return;
			}
			if (pul_AGCNo.getSelectedValue() == null)
			{
				//#CM52392
				//<en>6123078 = There is no group controller information. </en>
				//<en>Please register in group controller setting screen.</en>
				message.setMsgResourceKey("6123078");
				return;
			}
			
			//#CM52393
			//Input check
			txt_StNumber.validate();
			txt_AisleNumber.validate();
			txt_FBank.validate();
			txt_TBank.validate();
			txt_FBay.validate();
			txt_TBay.validate();
			txt_FLevel.validate();
			txt_TLevel.validate();
			
			//#CM52394
			// Aisle No. Check
			if(Integer.parseInt(txt_AisleNumber.getText()) < 1)
			{
				//#CM52395
				// 6123120 = Specify the value of one or more for Aisle No. 
				message.setMsgResourceKey("6123120");
				return;
			}		

			int sbank = Integer.parseInt(txt_FBank.getText());
			int ebank = Integer.parseInt(txt_TBank.getText());
			//#CM52396
			//Check whether Aisle position is input for double deep. 
			if (ebank - sbank > 1)
			{
				txt_FAislePosition.validate();
				txt_TAislePosition.validate();
			}
						
			//#CM52397
			//Parameter is set.
			AisleParameter param = new AisleParameter();
			param.setWarehouseNumber(Integer.parseInt(pul_StoreAs.getSelectedValue()));
			param.setAisleStationNumber(txt_StNumber.getText());
			param.setAisleNumber(txt_AisleNumber.getInt());
			param.setAGCNumber(Integer.parseInt(pul_AGCNo.getSelectedValue()));
			param.setSBank(sbank);
			param.setEBank(ebank);
			param.setSBay(Integer.parseInt(txt_FBay.getText()));
			param.setEBay(Integer.parseInt(txt_TBay.getText()));
			param.setSLevel(Integer.parseInt(txt_FLevel.getText()));
			param.setELevel(Integer.parseInt(txt_TLevel.getText()));
			if (ebank - sbank > 1)
			{
				param.setSAislePosition(Integer.parseInt(txt_FAislePosition.getText()));
				param.setEAislePosition(Integer.parseInt(txt_TAislePosition.getText()));
			}
			
			//#CM52398
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM52399
			//<en>Retrieve the scheduler object.</en>
			ScheduleInterface factory = (ScheduleInterface)this.getSession().getAttribute("FACTORY");

			//#CM52400
			//<en>Process the pooled entered data.</en>
			//#CM52401
			//<en>If all data has been entered in pooling area successfully, clear the input area.</en>
			if (factory.addParameter(conn, param))
			{			
				//#CM52402
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM52403
				//Area for display
				btn_Clear_Click(null);				
			}
			
			//#CM52404
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			//#CM52405
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM52406
				//Close the Connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52407
	/** <en>
	 * It is called when a clear button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		pul_StoreAs.setSelectedIndex(0);
		txt_StNumber.setText("");
		txt_AisleNumber.setText("");
		pul_AGCNo.setSelectedIndex(0);
		txt_FBank.setText("");
		txt_TBank.setText("");
		txt_FBay.setText("");
		txt_TBay.setText("");
		txt_FLevel.setText("");
		txt_TLevel.setText("");	
		txt_FAislePosition.setText("");
		txt_TAislePosition.setText("");	
	}

	//#CM52408
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
			ScheduleInterface factory = (ScheduleInterface)this.getSession().getAttribute("FACTORY");
			//#CM52409
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52410
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			//#CM52411
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM52412
			//<en> Set the preset data.</en>
			setList(conn, factory);		
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM52413
				//Close the Connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52414
	/**
	 * When it straightens, it strikes, and a clear button is pressed, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			ScheduleInterface factory = (ScheduleInterface)this.getSession().getAttribute("FACTORY");
			//#CM52415
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52416
			//Delete all after filtering
			factory.removeAllParameters(conn);
			//#CM52417
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM52418
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM52419
				//Close the Connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52420
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_Aisle_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			ScheduleInterface factory = (ScheduleInterface)this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52421
			//Line where it corrects, and Deletion is done
			int index = lst_Aisle.getActiveRow();
			//**** 修正 ****
			if(lst_Aisle.getActiveCol() == 1)
			{
				//#CM52422
				//Current line is set. 
				lst_Aisle.setCurrentRow(index);
				//#CM52423
				//Display the highlight in yellow. 
				lst_Aisle.setHighlight(index);
				//#CM52424
				//Set parameter under the correction to factory. 
				factory.changeParameter(index-1);

				//#CM52425
				//<en>Retrieve from factory only the parameters being modified.</en>
				AisleParameter param = (AisleParameter)factory.getUpdatingParameter();

				//#CM52426
				//Storage Flag
				pul_StoreAs.setSelectedItem(Integer.toString(param.getWarehouseNumber()));
				//#CM52427
				//Station No
				txt_StNumber.setText(param.getAisleStationNumber());
				//#CM52428
				//Aisle No
				txt_AisleNumber.setInt(param.getAisleNumber());
				//#CM52429
				//AGCNo
				pul_AGCNo.setSelectedItem(Integer.toString(param.getAGCNumber()));
				//#CM52430
				//Bank(Start)
				txt_FBank.setText(Integer.toString(param.getSBank()));
				//#CM52431
				//Bank(End)
				txt_TBank.setText(Integer.toString(param.getEBank()));
				//#CM52432
				//Bay(Start)
				txt_FBay.setText(Integer.toString(param.getSBay()));
				//#CM52433
				//Bay(End)
				txt_TBay.setText(Integer.toString(param.getEBay()));
				//#CM52434
				//Level(Start)
				txt_FLevel.setText(Integer.toString(param.getSLevel()));
				//#CM52435
				//Level(End)
				txt_TLevel.setText(Integer.toString(param.getELevel()));
				//#CM52436
				//Aisle position(Start)
				int sAislePos = param.getSAislePosition();
				if ( sAislePos == 0)
				{
					txt_FAislePosition.setText("");
				}
				else
				{
					txt_FAislePosition.setText(Integer.toString(sAislePos));
				}
				//#CM52437
				//Aisle position(End)
				int eAislePos = param.getEAislePosition();
				if ( eAislePos == 0)
				{
					txt_TAislePosition.setText("");
				}
				else
				{
					txt_TAislePosition.setText(Integer.toString(eAislePos));
				}
			}
			//**** 削除 ****
			else
			{	
				//#CM52438
				//Delete 1 after filtering
				factory.removeParameter(conn, index-1);
				
				//#CM52439
				//<en> Set the preset data.</en>
				setList(conn, factory);			
			}
			//#CM52440
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM52441
				//<en> Close the connection.</en>
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52442
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52443
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52444
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM52445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM52449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52450
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52452
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52453
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StNumber_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52454
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AisleNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52455
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_AisleNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52456
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_AisleNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52457
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_AisleNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52458
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AgcNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52459
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_AGCNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52460
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_AGCNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM52461
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Range_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52462
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Bank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52463
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52464
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBank_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52465
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBank_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBank_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52467
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52468
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52469
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBank_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52470
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBank_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52471
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBank_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52472
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AislePosition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52473
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FAislePosition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52474
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FAislePosition_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52475
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FAislePosition_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52476
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FAislePosition_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52477
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_And_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52478
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TAislePosition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52479
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TAislePosition_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52480
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TAislePosition_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52481
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TAislePosition_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52482
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Space_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52483
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Bay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52484
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52485
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBay_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52486
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBay_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52487
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FBay_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52488
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52489
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52490
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBay_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52491
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBay_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52492
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TBay_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52493
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AislePositionMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Level_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52495
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FLevel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52496
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FLevel_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FLevel_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52498
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FLevel_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52499
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen3_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52500
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TLevel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52501
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TLevel_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52502
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TLevel_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52503
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TLevel_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52504
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52505
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52506
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52507
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52508
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Aisle_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52509
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Aisle_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52510
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Aisle_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Aisle_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM52512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Aisle_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_Aisle_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM52514
//end of class
