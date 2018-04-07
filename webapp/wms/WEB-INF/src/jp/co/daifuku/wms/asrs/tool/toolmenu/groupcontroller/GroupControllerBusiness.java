// $Id: GroupControllerBusiness.java,v 1.2 2006/10/30 04:09:37 suresh Exp $

//#CM52734
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.groupcontroller;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.GroupControllerParameter;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
//#CM52735
/**
 * Screen Class of the group controller. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:09:37 $
 * @author  $Author: suresh $
 */
public class GroupControllerBusiness extends GroupController implements WMSToolConstants
{
	//#CM52736
	// Class fields --------------------------------------------------

	//#CM52737
	// Class variables -----------------------------------------------

	//#CM52738
	// Class method --------------------------------------------------

	//#CM52739
	// Constructors --------------------------------------------------

	//#CM52740
	// Public methods ------------------------------------------------
	//#CM52741
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM52742
		//Set screen Name. 
		lbl_SettingName.setResourceKey("TMEN-W0003");
		//#CM52743
		//Set passing to the Help file. 
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0003-1", this.getHttpRequest()));
		setFocus(txt_AgcNumber);
	}
	//#CM52744
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
			//#CM52745
			//Clear processing
			btn_Clear_Click(null);
			//#CM52746
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.GROUPCONTROLLER, Creater.M_CREATE);
			//#CM52747
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);

			GroupControllerParameter[] array = (GroupControllerParameter[])factory.query(conn, locale, null);
			if(array != null)
			{
				for(int i = 0; i < array.length; i++)
				{
					factory.addParameter(conn, array[i]);
				}
			}
			//#CM52748
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
				//#CM52749
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52750
	// Package methods -----------------------------------------------

	//#CM52751
	// Protected methods ---------------------------------------------

	//#CM52752
	// Private methods -----------------------------------------------
	//#CM52753
	/** <en>
	 * Set data to preset area.
	 * @param conn Connection 
	 * @param factory ScheduleInterface 
	 * @throws Exception 
	 </en> */
	private void setList(Connection conn, ScheduleInterface factory) throws Exception
	{
		String controllernumber = "";
		String ipaddress = "";

		//#CM52754
		//Delete all the lines
		lst_GroupController.clearRow();

		//#CM52755
		//Parameter acquisition
		Parameter paramarray[] = factory.getAllParameters();
		for (int i = 0; i < paramarray.length; i++)
		{
			GroupControllerParameter param = (GroupControllerParameter) paramarray[i];
			//#CM52756
			//Parameter to be added to List
			controllernumber = Integer.toString(param.getControllerNumber());
			ipaddress        = param.getIPAddress();

			//#CM52757
			//Line addition
			//#CM52758
			//The final line is acquired. 
			int count = lst_GroupController.getMaxRows();
			lst_GroupController.setCurrentRow(count);
			lst_GroupController.addRow();
			lst_GroupController.setValue(3, controllernumber);
			lst_GroupController.setValue(4, ipaddress);
		}
		//#CM52759
		// Display the line under the correction highlighting. 
		int modindex = factory.changeLineNo();
		if(modindex > -1)
		{
			lst_GroupController.setHighlight(modindex + 1);
		}
	}
	//#CM52760
	// Event handler methods -----------------------------------------
	//#CM52761
	/**
	 * When the Menu button is clicked, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM52762
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
			//#CM52763
			//Input check
			txt_AgcNumber.validate();
			txt_HostName.validate();
			
			//#CM52764
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");

			//#CM52765
			//<en> Set the entered value in schedule parameter.</en>
			GroupControllerParameter param = new GroupControllerParameter();

			param.setControllerNumber(Integer.parseInt(txt_AgcNumber.getText()));
			param.setIPAddress(txt_HostName.getText());

			if (factory.addParameter(conn, param))
			{
				//#CM52766
				//<en> Set the preset data.</en>
				setList(conn, factory);
				//#CM52767
				//Area for display
				btn_Clear_Click(null);
			}
			//#CM52768
			//Session is maintained
			this.getSession().setAttribute("FACTORY", factory);
			//#CM52769
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
				//#CM52770
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52771
	/** 
	 * Mount the processing of a clear button. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		txt_AgcNumber.setText("");
		txt_HostName.setText("");
	}

	//#CM52772
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

			//#CM52773
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52774
			//<en> Start the scheduling.</en>
			factory.startScheduler(conn);
			//#CM52775
			//<en> Set the message.</en>
			message.setMsgResourceKey(factory.getMessage());
			//#CM52776
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
				//#CM52777
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52778
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
			//#CM52779
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52780
			//Delete all after filtering
			factory.removeAllParameters(conn);
			//#CM52781
			//<en> Set the preset data.</en>
			setList(conn, factory);
			//#CM52782
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
				//#CM52783
				//Close the Connection
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM52784
	/** <en>
	 * It is called when it clicks on the list.
	 * @param e ActionEvent 
	 * @throws Exception 
	 </en> */
	public void lst_GroupController_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			CreaterFactory factory = (CreaterFactory) this.getSession().getAttribute("FACTORY");
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM52785
			//Line where it corrects, and Deletion is done
			int index = lst_GroupController.getActiveRow();
			//**** 修正 ****
			if (lst_GroupController.getActiveCol() == 1)
			{
				//#CM52786
				//Current line is set. 
				lst_GroupController.setCurrentRow(index);
				//#CM52787
				//Display the highlight in yellow. 
				lst_GroupController.setHighlight(index);
				//#CM52788
				//Set parameter under the correction to factory. 
				factory.changeParameter(index - 1);
				//#CM52789
				//<en>Retrieve from factory only the parameters being modified.</en>
				GroupControllerParameter param = (GroupControllerParameter) factory.getUpdatingParameter();
				//#CM52790
				//AGCNumber
				txt_AgcNumber.setText(Integer.toString(param.getControllerNumber()));
				//#CM52791
				//Host name
				txt_HostName.setText(param.getIPAddress());
			}
			//**** 削除 ****
			else
			{
				//#CM52792
				//Delete 1 after filtering
				factory.removeParameter(conn, index - 1);
				//#CM52793
				//<en> Set the preset data.</en>
				setList(conn, factory);
			}
			//#CM52794
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
				//#CM52795
				//<en> Close the connection.</en>
				if (conn != null) conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM52796
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52797
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52798
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Create_Click(ActionEvent e) throws Exception
	{
	}

	//#CM52799
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52800
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_AgcNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52801
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_AgcNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52802
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_AgcNumber_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52803
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_AgcNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52804
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HostName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52805
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52806
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52807
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52808
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52809
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52810
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52811
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Commit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52812
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Cancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52813
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_GroupController_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM52814
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_GroupController_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM52815
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_GroupController_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM52816
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_GroupController_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM52817
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_GroupController_Server(ActionEvent e) throws Exception
	{
	}

	//#CM52818
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_GroupController_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM52819
//end of class
