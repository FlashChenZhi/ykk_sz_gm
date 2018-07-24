// $Id: MachineBusiness.java,v 1.2 2006/10/30 05:10:25 suresh Exp $

//#CM53836
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.machine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.MachineParameter;
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

//#CM53837
/**
 * Equipment information setting screen Class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:10:25 $
 * @author  $Author: suresh $
 */
public class MachineBusiness extends Machine implements WMSToolConstants
{
	//#CM53838
	// Class fields --------------------------------------------------

	//#CM53839
	// Class variables -----------------------------------------------

	//#CM53840
	// Class method --------------------------------------------------

	//#CM53841
	// Constructors --------------------------------------------------

	//#CM53842
	// Public methods ------------------------------------------------

	//#CM53843
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM53844
		//Set screen Name. 
		lbl_SettingName.setResourceKey("TMEN-W0010-1");
		//#CM53845
		//Set passing to the Help file. 
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0010-1", this.getHttpRequest()));
		
		setFocus(txt_MachineNumber);
	}

	//#CM53846
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
			//#CM53847
			//Clear processing
			btn_Clear_Click(null);
			//#CM53848
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.MACHINE, Creater.M_CREATE);
			//#CM53849
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);

			ToolPulldownData pull = new ToolPulldownData(conn, locale, null);
			//#CM53850
			// Pull down display data (AGCNo)
			String[] agcno = pull.getGroupControllerPulldownData("");
			//#CM53851
			// Pull down display data (Model code)
			String[] machinetype = pull.getMachineTypePulldownData("");
			//#CM53852
			// Pull down display data (Station No)
			String[] stno = pull.getAllStationPulldownData("", 2);
			//#CM53853
			//Set data in the pull down.
			ToolPulldownHelper.setPullDown(pul_AGCNo, agcno);
			ToolPulldownHelper.setPullDown(pul_MachineTypeCode, machinetype);
			ToolPulldownHelper.setPullDown(pul_StationNumber, stno);

			MachineParameter[] array = (MachineParameter[])factory.query(conn, locale,null);

			if(array != null)
			{
				for(int i = 0; i < array.length; i++)
				{
					((ToolScheduleInterface)factory).addInitialParameter(array[i]);
				}
			}

			//#CM53854
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
				//#CM53855
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53856
	// Package methods -----------------------------------------------

	//#CM53857
	// Protected methods ---------------------------------------------

	//#CM53858
	// Private methods -----------------------------------------------
	//#CM53859
	/** <en>
	 * Set data to preset area.
	 * @param conn Connection 
	 * @param factory ScheduleInterface 
	 * @throws Exception 
	 </en> */
	private void setList(Connection conn, ScheduleInterface factory) throws Exception
	{
		String agcno = "";
		String machinetypename = "";
		String machineno = "";
		String stno = "";
		String stname = "";

		//#CM53860
		///Plaque of TOOL TIP
		String title_STName = DisplayText.getText("TLBL-W0025");

		ToolFindUtil findutil = new ToolFindUtil(conn);

		//#CM53861
		//Delete all the lines
		lst_MachineStatus.clearRow();

		//#CM53862
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			MachineParameter param = (MachineParameter)paramarray[i];
			//#CM53863
			//Parameter to be added to List
			agcno = Integer.toString(param.getControllerNumber());
			machinetypename = findutil.getMachineTypeName(param.getMachineType());
			machineno = Integer.toString(param.getMachineNumber());
			stno = param.getStationNumber();
			stname = findutil.getStationName(param.getStationNumber());

			//#CM53864
			//Line addition
			//#CM53865
			//The final line is acquired. 
			int count = lst_MachineStatus.getMaxRows();
			lst_MachineStatus.setCurrentRow(count);
			lst_MachineStatus.addRow();
			lst_MachineStatus.setValue(3, agcno);
			lst_MachineStatus.setValue(4, machinetypename);
			lst_MachineStatus.setValue(5, machineno);
			lst_MachineStatus.setValue(6, stno);
			lst_MachineStatus.setValue(7, stname);

			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_STName, stname);

			//#CM53866
			//Set TOOL TIP. 	
			lst_MachineStatus.setToolTip(count, toolTip.getText());
		}
		//#CM53867
		// Display the line under the correction highlighting. 
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_MachineStatus.setHighlight(modindex + 1);
		}
	}

	//#CM53868
	// Event handler methods -----------------------------------------
	//#CM53869
	/**
	 * When the Menu button is clicked, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM53870
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
			//#CM53871
			//Input check
			txt_MachineNumber.validate();
			
			//#CM53872
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");

			//#CM53873
			//<en> Set the entered value in schedule parameter.</en>
			MachineParameter param = new MachineParameter();
			int agcno = 0;

			if (pul_AGCNo.getSelectedValue() != null )
			{
				//#CM53874
				//<en> If data is found in pull-down,</en>
				agcno = Integer.parseInt(pul_AGCNo.getSelectedValue());
			}		

			param.setControllerNumber(agcno);
			param.setMachineType(Integer.parseInt(pul_MachineTypeCode.getSelectedValue()));
			param.setMachineNumber(Integer.parseInt(txt_MachineNumber.getText()));
			param.setStationNumber(pul_StationNumber.getSelectedValue());
			param.setStationName(pul_StationNumber.getSelectedValue());

			if (factory.addParameter(conn, param))
			{
				//#CM53875
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM53876
				//Area for display
				btn_Clear_Click(null);
			}
			//#CM53877
			//Session is maintained.
			this.getSession().setAttribute("FACTORY", factory);
			//#CM53878
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
				//#CM53879
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53880
	/** <en>
	 * It is called when a clear button is pushed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		pul_AGCNo.setSelectedIndex(0);
		pul_MachineTypeCode.setSelectedIndex(0);
		txt_MachineNumber.setText("");
		pul_StationNumber.setSelectedIndex(0);
	}

	//#CM53881
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

			//#CM53882
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM53883
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			
			//#CM53884
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM53885
			//<en> Set the preset data.</en>
			setList(conn, factory);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM53886
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53887
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
			//#CM53888
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM53889
			//Delete all after filtering
			factory.removeAllParameters(conn);
			//#CM53890
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM53891
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
				//#CM53892
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53893
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_MachineStatus_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM53894
			//Line where it corrects, and Deletion is done
			int index = lst_MachineStatus.getActiveRow();
			//**** 修正 ****
			if (lst_MachineStatus.getActiveCol() == 1)
			{
				//#CM53895
				//Current line is set. 
				lst_MachineStatus.setCurrentRow(index);
				//#CM53896
				//Display the highlight in yellow. 
				lst_MachineStatus.setHighlight(index);
				//#CM53897
				//Set parameter under the correction to factory. 
				factory.changeParameter(index - 1);
				//#CM53898
				//<en>Retrieve from factory only the parameters being modified.</en>
				MachineParameter param = (MachineParameter)factory.getUpdatingParameter();
				//#CM53899
				//AGCNo
				pul_AGCNo.setSelectedItem(Integer.toString(param.getControllerNumber()));
				//#CM53900
				//Model code
				pul_MachineTypeCode.setSelectedItem(Integer.toString(param.getMachineType()));
				//#CM53901
				//Title machine No
				txt_MachineNumber.setText(Integer.toString(param.getMachineNumber()));
				//#CM53902
				//Station No
				pul_StationNumber.setSelectedItem(param.getStationNumber());
			}
			//**** 削除 ****
			else
			{
				//#CM53903
				//Delete 1 after filtering
				factory.removeParameter(conn, index - 1);
				//#CM53904
				//<en> Set the preset data.</en>
				setList(conn, factory);
			}
			//#CM53905
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
				//#CM53906
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM53907
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53908
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53909
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM53910
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53911
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AgcNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53912
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_AGCNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53913
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_AGCNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53914
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MachineCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53915
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_MachineTypeCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53916
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_MachineTypeCode_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53917
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MachineNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53918
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MachineNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MachineNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53920
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MachineNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53921
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MachineNumber_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53922
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StationNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StationNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StationNumber_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_MachineStatus_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM53930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_MachineStatus_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_MachineStatus_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_MachineStatus_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM53933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_MachineStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_MachineStatus_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM53935
//end of class
