// $Id: LoginUserMaintenanceBusiness.java,v 1.2 2006/11/07 06:22:13 suresh Exp $
//#CM664741
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.UserInfo;

//#CM664742
/**
 * The login user maintenance screen class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:22:13 $
 * @author  $Author: suresh $
 */
public class LoginUserMaintenanceBusiness extends LoginUserMaintenance
{
	//#CM664743
	// Class fields --------------------------------------------------

	//#CM664744
	// Class variables -----------------------------------------------

	//#CM664745
	// Class method --------------------------------------------------

	//#CM664746
	// Constructors --------------------------------------------------

	//#CM664747
	// Public methods ------------------------------------------------

	//#CM664748
	/**
	 * Generation condition : It is called before the call of each control event. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		
		btn_Help.setUrl("");
			
	}	
	//#CM664749
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		setUserInfo(new UserInfo());
		
		lbl_SettingName.setText("ログインユーザ一覧");

		//#CM664750
		// Acquire the final line.
		int count = lst_LoginUserMaintenance.getMaxRows();
		//#CM664751
		// Add line.
		lst_LoginUserMaintenance.addRow();
		lst_LoginUserMaintenance.setCurrentRow(count);
		lst_LoginUserMaintenance.setValue(1, Integer.toString(count));
		lst_LoginUserMaintenance.setValue(2, "0123456789:あいうえおかきくけこ");
		lst_LoginUserMaintenance.setValue(3, "0001");
		lst_LoginUserMaintenance.setValue(4, "入庫口");
		lst_LoginUserMaintenance.setValue(5, "172.123.156.141");
		lst_LoginUserMaintenance.setValue(6, "2004/06/08 15:16:45");

		count = lst_LoginUserMaintenance.getMaxRows();
		//#CM664752
		// Add line.
		lst_LoginUserMaintenance.addRow();
		lst_LoginUserMaintenance.setCurrentRow(count);
		lst_LoginUserMaintenance.setValue(1, Integer.toString(count));
		lst_LoginUserMaintenance.setValue(2, "0123456789:あいうえおかきくけこ");
		lst_LoginUserMaintenance.setValue(3, "0002");
		lst_LoginUserMaintenance.setValue(4, "出庫口");
		lst_LoginUserMaintenance.setValue(5, "172.123.156.141");
		lst_LoginUserMaintenance.setValue(6, "2004/06/08 15:16:45");

	}



	//#CM664753
	// Package methods -----------------------------------------------

	//#CM664754
	// Protected methods ---------------------------------------------

	//#CM664755
	// Private methods -----------------------------------------------

	//#CM664756
	// Event handler methods -----------------------------------------
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserMaintenance_EnterKey(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserMaintenance_TabKey(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserMaintenance_InputComplete(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserMaintenance_ColumClick(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserMaintenance_Server(ActionEvent e) throws Exception
	{
	}

	public void lst_LoginUserMaintenance_Change(ActionEvent e) throws Exception
	{
	}

	//#CM664757
	/**
	 * Processing when Maintenance button is pressed
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void lst_LoginUserMaintenance_Click(ActionEvent e) throws Exception
	{
		//#CM664758
		// A present line is set. 
		lst_LoginUserMaintenance.setCurrentRow(lst_LoginUserMaintenance.getActiveRow());
		System.out.println("ret="+lst_LoginUserMaintenance.getValue(1));	

	}

	public void btn_Refresh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM664759
	/**
	 * Processing when Refresh button is pressed
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void btn_Refresh_Click(ActionEvent e) throws Exception
	{
		message.setMsgResourceKey("6001001");
	}


	public void lbl_Msg01_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM664760
//end of class
