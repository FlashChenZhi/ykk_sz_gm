// $Id: LoginUserListBusiness.java,v 1.2 2006/11/07 06:22:33 suresh Exp $
//#CM664711
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;
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
import jp.co.daifuku.wms.base.common.WMSConstants;


//#CM664712
/**
 * The log in user list screen class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:22:33 $
 * @author  $Author: suresh $
 */
public class LoginUserListBusiness extends LoginUserList implements WMSConstants, TableColumns
{
	//#CM664713
	// Class fields --------------------------------------------------

	//#CM664714
	// Class variables -----------------------------------------------

	//#CM664715
	// Class method --------------------------------------------------

	//#CM664716
	// Constructors --------------------------------------------------

	//#CM664717
	// Public methods ------------------------------------------------
	//#CM664718
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
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
				//#CM664719
				// Plaque used with Tip
				String title_Number = DisplayText.getText("LBL-9002");
				String title_User = DisplayText.getText("LBL-9005");
				String title_TerminalNo = DisplayText.getText("LBL-9006");
				String title_TerminalName = DisplayText.getText("LBL-9007");
				String title_IP = DisplayText.getText("LBL-9008");
				String title_Date = DisplayText.getText("LBL-9009");
				Iterator itr = userList.iterator();
				while(itr.hasNext())
				{
					AccountUserData userData = (AccountUserData)itr.next();
					String user = userData.getUserID();
					Date loginDate = userData.getLoginDate();
					String ipAddress = userData.getIPaddress();
					//#CM664720
					// select-0003a=SELECT * FROM TERMINAL WHERE IPADDRESS = {0};
					BaseHandler handle = new BaseHandler();
					List list = handle.find("_select-0003a", new String[]{ipAddress}, conn );
					
					//#CM664721
					// Retrieved again for the terminal of unregistration by the unregistered identification character string. 
					if (list == null || list.size() == 0)
					{
						list = handle.find("_select-0003a", new String[] { AbstractAuthentication.UNDEFINED_TERMINAL }, conn);
					}

					ResultMap datamap = (ResultMap)list.get(0);

					String terminalNo = (String)datamap.getTrimmedString(TERMINAL_TERMINALNUMBER);
					String terminalName = (String)datamap.getTrimmedString(TERMINAL_TERMINALNAME);

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

					//#CM664722
					// Set ToolTip
					ToolTipHelper toolTip = new ToolTipHelper();
					toolTip.add(title_Number, count);
					toolTip.add(title_User, user);
					toolTip.add(title_TerminalNo, terminalNo);
					toolTip.add(title_TerminalName, terminalName);
					toolTip.add(title_IP, ipAddress);
					toolTip.add(title_Date, strDate);
					
					lst_LoginUserList.setToolTip(count, toolTip.getText());				
				}
			}
		}
		catch(Exception ex)
		{
			//#CM664723
			// 6006001=The error not anticipated occurred. {0}
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
			//#CM664724
			// MSG-9109=The error occurred. {0}
			this.setAlert("MSG-9109" + "\t" + discription);
			//#CM664725
			// Rollback
			if(conn != null)conn.rollback();
		}
		finally
		{
			if(conn != null)conn.close();
		}
	}
	
	//#CM664726
	/**
	 * Override the login check. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM664727
	// Package methods -----------------------------------------------

	//#CM664728
	// Protected methods ---------------------------------------------

	//#CM664729
	// Private methods -----------------------------------------------

	//#CM664730
	// Event handler methods -----------------------------------------

	
	//#CM664731
	/**
	 * Processing when Close button is pressed
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
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

	//#CM664732
	/**
	 * Processing when Close button is pressed
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		btn_Close_U_Click(e);
	}


	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM664733
	/**
	 * Add to List cell.
	 * @param listcell List cell
	 * @param listIndex Row No. of List cell.
	 * @param paramVec Value added to List cell
	 * @param toolTip ToolTip
	 */
	private void addListItem(ListCell listcell, int listIndex, Vector paramVec, String toolTip)
	{
		int count = listcell.getMaxRows();
		listcell.addRow();
		modifyListItem(listcell, count, listIndex, paramVec, toolTip);
	}
		
	//#CM664734
	/**
	 * Change List cell. 
	 * @param listcell List cell
	 * @param index The maximum line of List cell
	 * @param listIndex Row No. of List cell.
	 * @param paramVec Value added to List cell
	 * @param toolTip ToolTip
	 */
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
		//#CM664735
		// Set in ToolTip.
		listcell.setToolTip(index, toolTip);
	}

}
//#CM664736
//end of class
