// $Id: LoginUserMaintenanceBusiness.java,v 1.2 2006/10/30 05:09:46 suresh Exp $
//#CM53814
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login.toollogin;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.UserInfo;

//#CM53815
/** <en>
 * This screen class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:46 $
 * @author  $Author: suresh $
 </en> */
public class LoginUserMaintenanceBusiness extends LoginUserMaintenance
{
	//#CM53816
	// Class fields --------------------------------------------------

	//#CM53817
	// Class variables -----------------------------------------------

	//#CM53818
	// Class method --------------------------------------------------

	//#CM53819
	// Constructors --------------------------------------------------

	//#CM53820
	// Public methods ------------------------------------------------

	//#CM53821
	/** <en>
	 * Generating conditions: It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		
		btn_Help.setUrl("");
			
	}	
	//#CM53822
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		setUserInfo(new UserInfo());
		
		lbl_SettingName.setText("ログインユーザ一覧");

		//#CM53823
		//The final line is acquired. 
		int count = lst_LoginUserMaintenance.getMaxRows();
		//#CM53824
		//Line addition
		lst_LoginUserMaintenance.addRow();
		lst_LoginUserMaintenance.setCurrentRow(count);
		lst_LoginUserMaintenance.setValue(1, Integer.toString(count));
		lst_LoginUserMaintenance.setValue(2, "0123456789:あいうえおかきくけこ");
		lst_LoginUserMaintenance.setValue(3, "0001");
		lst_LoginUserMaintenance.setValue(4, "入庫口");
		lst_LoginUserMaintenance.setValue(5, "172.123.156.141");
		lst_LoginUserMaintenance.setValue(6, "2004/06/08 15:16:45");

		count = lst_LoginUserMaintenance.getMaxRows();
		//#CM53825
		//Line addition
		lst_LoginUserMaintenance.addRow();
		lst_LoginUserMaintenance.setCurrentRow(count);
		lst_LoginUserMaintenance.setValue(1, Integer.toString(count));
		lst_LoginUserMaintenance.setValue(2, "0123456789:あいうえおかきくけこ");
		lst_LoginUserMaintenance.setValue(3, "0002");
		lst_LoginUserMaintenance.setValue(4, "出庫口");
		lst_LoginUserMaintenance.setValue(5, "172.123.156.141");
		lst_LoginUserMaintenance.setValue(6, "2004/06/08 15:16:45");

	}



	//#CM53826
	// Package methods -----------------------------------------------

	//#CM53827
	// Protected methods ---------------------------------------------

	//#CM53828
	// Private methods -----------------------------------------------

	//#CM53829
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

	public void lst_LoginUserMaintenance_Click(ActionEvent e) throws Exception
	{
		//#CM53830
		//Current line is set. 
		lst_LoginUserMaintenance.setCurrentRow(lst_LoginUserMaintenance.getActiveRow());
		System.out.println("ret="+lst_LoginUserMaintenance.getValue(1));	

	}

	public void btn_Refresh_Server(ActionEvent e) throws Exception
	{
	}

	public void btn_Refresh_Click(ActionEvent e) throws Exception
	{
		message.setMsgResourceKey("6001001");
	}


	public void lbl_Msg01_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM53831
//end of class
