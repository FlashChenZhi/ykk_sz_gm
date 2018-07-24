// $Id: UnavailableLocationBusiness.java,v 1.3 2006/10/30 06:30:53 suresh Exp $

//#CM54649
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.unavailablelocation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.UnavailableLocationParameter;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;

//#CM54650
/**
<kt> * Location setting (shelf which cannot be used) screen class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/30 06:30:53 $
 * @author  $Author: suresh $
 */
public class UnavailableLocationBusiness extends UnavailableLocation implements WMSToolConstants
{
	//#CM54651
	// Class fields --------------------------------------------------

	//#CM54652
	// Class variables -----------------------------------------------

	//#CM54653
	// Class method --------------------------------------------------

	//#CM54654
	// Constructors --------------------------------------------------

	//#CM54655
	// Public methods ------------------------------------------------

	//#CM54656
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM54657
		//Set the screen name.
		lbl_SettingName.setResourceKey("TMEN-W0004-3");
		//#CM54658
		//Set the path for help file.
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0004-3", this.getHttpRequest()));
		
		setFocus(txt_Bank);
	}

	//#CM54659
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
			//#CM54660
			//Clear processing
			btn_Clear_Click(null);
			//#CM54661
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.UNAVAILABLELOCATION, Creater.M_CREATE);
			//#CM54662
			//It maintains it in the session.
			this.getSession().setAttribute("FACTORY", factory);

			//#CM54663
			//<en>Set the name of the file the data will be saved.</en>
			UnavailableLocationParameter searchParam = new UnavailableLocationParameter();

			searchParam.setFileName((String)this.getSession().getAttribute("WorkFolder"));

			UnavailableLocationParameter[] array 
				= (UnavailableLocationParameter[])factory.query(conn, locale, searchParam);
			if(array != null)
			{
				for(int i = 0; i < array.length; i++)
				{
					factory.addParameter(conn, array[i]);
				}
			}
			//#CM54664
			//<en>*****Set the items to show in initial display.*****</en>
			ToolPulldownData pull = new ToolPulldownData(conn, locale,null);
			//#CM54665
			// Pull down display data (warehouse)
			String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "");
			//#CM54666
			//Data is set in pull down.
			ToolPulldownHelper.setPullDown(pul_StoreAs, whno);

			//#CM54667
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
				//#CM54668
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54669
	// Package methods -----------------------------------------------

	//#CM54670
	// Protected methods ---------------------------------------------

	//#CM54671
	// Private methods -----------------------------------------------
	//#CM54672
	/** <en>
	 * Set data to preset area.
	 * @param conn Connection 
	 * @param factory ScheduleInterface 
	 * @throws Exception 
	 </en> */
	private void setList(Connection conn, ScheduleInterface factory) throws Exception
	{
		String warehousenumber = "";
		String bank = "";
		String bay = "";
		String level = "";
		String locno = "";

		//#CM54673
		//All lines are deleted.
		lst_UnUsableShelf.clearRow();

		//#CM54674
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			UnavailableLocationParameter param = (UnavailableLocationParameter)paramarray[i];
			//#CM54675
			//Parameter added to list
			warehousenumber = Integer.toString(param.getWarehouseNumber());
			bank = Integer.toString(param.getBank());
			bay = Integer.toString(param.getBay());
			level = Integer.toString(param.getLevel());
			locno = bank + "-" + bay + "-" + level;
			
			//#CM54676
			//Line addition
			//#CM54677
			//The final line is acquired.
			int count = lst_UnUsableShelf.getMaxRows();
			lst_UnUsableShelf.setCurrentRow(count);
			lst_UnUsableShelf.addRow();
			lst_UnUsableShelf.setValue(2, warehousenumber);
			lst_UnUsableShelf.setValue(3, locno);
		}
		//#CM54678
		// Display the line under the correction highlighting.
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_UnUsableShelf.setHighlight(modindex + 1);
		}
	}

	//#CM54679
	// Event handler methods -----------------------------------------
	//#CM54680
	/**
	 * It is called when the Menu button is clicked.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM54681
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
			//#CM54682
			//<en>In case the pull-down is not correctly displayed,</en>
			if (pul_StoreAs.getSelectedValue() == null)
			{
				//#CM54683
				//<en>6123117 = There is no warehouse control information. </en>
				//<en>Please register in warehouse setting screen.</en>
				message.setMsgResourceKey("6123117");
				return;
			}
			//#CM54684
			//Input check
			txt_Bank.validate();
			txt_Bay.validate();
			txt_Level.validate();
			
			//#CM54685
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");

			//#CM54686
			//<en> Set the entered value in schedule parameter.</en>
			UnavailableLocationParameter param = new UnavailableLocationParameter();

			param.setWarehouseNumber(Integer.parseInt(pul_StoreAs.getSelectedValue()));
			param.setBank(Integer.parseInt(txt_Bank.getText()));
			param.setBay(Integer.parseInt(txt_Bay.getText()));
			param.setLevel(Integer.parseInt(txt_Level.getText()));

			if (factory.addParameter(conn, param))
			{
				//#CM54687
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM54688
				//Clear display area
				btn_Clear_Click(null);
				pul_StoreAs.setSelectedIndex(0);
			}
			//#CM54689
			//Maintain it in the session.
			this.getSession().setAttribute("FACTORY", factory);
			//#CM54690
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
				//#CM54691
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54692
	/** <en>
	 * It is called when a clear button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		txt_Bank.setText("");
		txt_Bay.setText("");
		txt_Level.setText("");
	}

	//#CM54693
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

			//#CM54694
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54695
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			
			pul_StoreAs.setSelectedIndex(0);
			
			//#CM54696
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM54697
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
				//#CM54698
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54699
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
			//#CM54700
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54701
			//All deletions after filtering
			factory.removeAllParameters(conn);
			//#CM54702
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM54703
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
				//#CM54704
				//Connection close
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54705
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_UnUsableShelf_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM54706
			//Deleted line
			int index = lst_UnUsableShelf.getActiveRow();
			//**** 削除 ****
			if (lst_UnUsableShelf.getActiveCol() == 1)
			{
				//#CM54707
				//Deleting one after filtering.
				factory.removeParameter(conn, index - 1);
				//#CM54708
				//<en> Set the preset data.</en>
				setList(conn, factory);
			}
			//#CM54709
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
				//#CM54710
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM54711
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54712
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54713
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54714
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54715
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54716
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54717
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM54718
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ImpossibleLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54719
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54720
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bank_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54721
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bank_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54722
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bank_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54723
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54724
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bay_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bay_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Bay_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54728
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphen2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Level_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54730
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Level_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54731
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Level_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54732
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Level_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54733
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54734
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54735
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54736
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54737
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_UnUsableShelf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM54738
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_UnUsableShelf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM54739
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_UnUsableShelf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM54740
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_UnUsableShelf_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM54741
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_UnUsableShelf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54742
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_UnUsableShelf_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM54743
//end of class
