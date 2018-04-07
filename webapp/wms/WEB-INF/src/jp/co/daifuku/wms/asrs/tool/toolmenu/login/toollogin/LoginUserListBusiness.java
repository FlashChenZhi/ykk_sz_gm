// $Id: LoginUserListBusiness.java,v 1.2 2006/10/30 05:09:47 suresh Exp $
//#CM53780
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login.toollogin;
import java.sql.Connection;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.AbstractAuthentication;
import jp.co.daifuku.authentication.session.AccountTable;
import jp.co.daifuku.authentication.session.AccountUserData;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.dbhandler.ResultMap;
import jp.co.daifuku.dbhandler.TableColumns;
import jp.co.daifuku.logging.AppLogger;
import jp.co.daifuku.ui.web.ToolTipHelper;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;


//#CM53781
/** <en>
 * This is a login user list screen class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:47 $
 * @author  $Author: suresh $
 </en> */
public class LoginUserListBusiness extends LoginUserList implements WMSToolConstants, TableColumns
{
	//#CM53782
	// Class fields --------------------------------------------------

	//#CM53783
	// Class variables -----------------------------------------------

	//#CM53784
	// Class method --------------------------------------------------

	//#CM53785
	// Constructors --------------------------------------------------

	//#CM53786
	// Public methods ------------------------------------------------
	//#CM53787
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		lbl_ListName.setText(DispResources.getText("TLE-9000"));
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			List userList = AccountTable.getUserList();
			if(userList != null && userList.size() != 0)
			{		
				//#CM53788
				//Add 2005/03/17 kawashima
				//#CM53789
				//Message used for Tip
				String title_Number = DisplayText.getText("LBL-9002");
				String title_User = DisplayText.getText("LBL-9005");
				String title_TerminalNo = DisplayText.getText("LBL-9006");
				String title_TerminalName = DisplayText.getText("LBL-9007");
				String title_IP = DisplayText.getText("LBL-9008");
				String title_Date = DisplayText.getText("LBL-9009");
				//#CM53790
				//End 2005/03/17 kawashima
				Iterator itr = userList.iterator();
				while(itr.hasNext())
				{
					AccountUserData userData = (AccountUserData)itr.next();
					String user = userData.getUserID();
					Date loginDate = userData.getLoginDate();
					String ipAddress = userData.getIPaddress();
					//#CM53791
					//select-0003a=SELECT * FROM TERMINAL WHERE IPADDRESS = {0};
					BaseHandler handle = new BaseHandler();
					List list = handle.find("_select-0003a", new String[]{ipAddress}, conn );
					
					//#CM53792
					//<en>It is looked up with a un-registration distinction string again in case of a terminal of the un-registration.</en>
					if (list == null || list.size() == 0)
					{
						list = handle.find("_select-0003a", new String[] { AbstractAuthentication.UNDEFINED_TERMINAL }, conn);
					}

					ResultMap datamap = (ResultMap)list.get(0);

					String terminalNo = (String)datamap.getString(TERMINAL_TERMINALNUMBER);
					String terminalName = (String)datamap.getString(TERMINAL_TERMINALNAME);

					String strDate = Formatter.getDateFormat(loginDate, F_DATE_TIME, this.getHttpRequest().getLocale());
					
					int count = lst_LoginUserList.getMaxRows();
					lst_LoginUserList.addRow();
					lst_LoginUserList.setCurrentRow(count);
					lst_LoginUserList.setValue(1, Integer.toString(count));
					lst_LoginUserList.setValue(2, user);
					lst_LoginUserList.setValue(3, terminalNo);
					lst_LoginUserList.setValue(4, terminalName);
					lst_LoginUserList.setValue(5, ipAddress);
					lst_LoginUserList.setValue(6, strDate);
					//#CM53793
					//Add 2005/03/17 kawashima
					//#CM53794
					//Set the TOOL TIP
					ToolTipHelper toolTip = new ToolTipHelper();
					toolTip.add(title_Number, count);
					toolTip.add(title_User, user);
					toolTip.add(title_TerminalNo, terminalNo);
					toolTip.add(title_TerminalName, terminalName);
					toolTip.add(title_IP, ipAddress);
					toolTip.add(title_Date, strDate);
					
					lst_LoginUserList.setToolTip(count, toolTip.getText());				
					//#CM53795
					//End 2005/03/17 kawashima
				}
			}
		}
		catch(Exception ex)
		{
			//#CM53796
			//6006001=Unexpected error occurred.{0}
			AppLogger.write(6006001, ex, this.getClass());
			String discription = "";
			if(ex.getCause() != null)
			{
				discription = ex.getCause().toString();
			}
			else
			{ 
				discription = ex.getMessage();
			}
			//#CM53797
			//MSG-9109=Error occurred. {0}
			this.setAlert("MSG-9109" + "\t" + discription);
			//#CM53798
			//Rollback
			if(conn != null)conn.rollback();
		}
		finally
		{
			if(conn != null)conn.close();
		}
	}
	
	//#CM53799
	/** <en>
	 * Override the page_LoginCheck method.
	 * @param e ActionEvent
	 </en> */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM53800
	// Package methods -----------------------------------------------

	//#CM53801
	// Protected methods ---------------------------------------------

	//#CM53802
	// Private methods -----------------------------------------------

	//#CM53803
	// Event handler methods -----------------------------------------

	
	//#CM53804
	/** <en>
	 * The process when the close button was pushed.
	 * @param e ActionEvent
	 </en> */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		closeWindow();
	}

	public void lst_LoginUserList_EnterKey(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserList_TabKey(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserList_InputComplete(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserList_ColumClick(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserList_Server(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserList_Change(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserList_Click(ActionEvent e) throws Exception
	{
	}

	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53805
	/** <en>
	 * The process when the close button was pushed.
	 * @param e ActionEvent
	 </en> */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		btn_Close_U_Click(e);
	}


	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53806
	//<en>Add Method of List cell</en>
	private void addListItem(ListCell listcell, int listIndex, Vector paramVec, String toolTip)
	{
		int count = listcell.getMaxRows();
		listcell.addRow();
		modifyListItem(listcell, count, listIndex, paramVec, toolTip);
	}
		
	//#CM53807
	//<en>Modify Method of List cell</en>
	private void modifyListItem(ListCell listcell, int index, int listIndex, Vector paramVec, String toolTip)
	{
		listcell.setCurrentRow(index);
		listcell.setValue(1, Integer.toString(index+listIndex));

		for(int i = 0; i < paramVec.size(); i++)
		{
			if(!Validator.isEmptyCheck((String)paramVec.get(i)))
			{
				listcell.setValue(i+2, (String)paramVec.get(i));
			}
		}
		//#CM53808
		//Set TOOL TIP
		listcell.setToolTip(index, toolTip);
	}

}
//#CM53809
//end of class
