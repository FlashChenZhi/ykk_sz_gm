// $Id: LoginBusiness.java,v 1.2 2006/10/30 05:05:27 suresh Exp $

//#CM53559
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.toolmenu.dataoperate.DataOperator;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.productionlist.ProductionListBusiness;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;

//#CM53560
/**
 * Customization class
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="Table		HeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:05:27 $
 * @author  $Author: suresh $
 */

public class LoginBusiness extends Login implements WMSToolConstants
{
	//#CM53561
	// Class fields --------------------------------------------------

	//#CM53562
	// Class variables -----------------------------------------------

	//#CM53563
	// Class method --------------------------------------------------

	//#CM53564
	// Constructors --------------------------------------------------

	//#CM53565
	// Public methods ------------------------------------------------
	//#CM53566
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		setFocus(txt_ProductionNumber);
	}

	//#CM53567
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
	}

	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM53568
	/** <en>
	 * When it is returned, this method is called from Popup window.
	 * @param e ActionEvent
	 </en> */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		String procno = param.getParameter(ProductionListBusiness.PRODUCTIONNO_KEY);
		//#CM53569
		//Set the value when it is not empty. 
		if (!StringUtil.isBlank(procno))
		{
			txt_ProductionNumber.setText(procno);
		}
	}	

	//#CM53570
	// Package methods -----------------------------------------------

	//#CM53571
	// Protected methods ---------------------------------------------

	//#CM53572
	// Private methods -----------------------------------------------

	//#CM53573
	// Event handler methods -----------------------------------------
	//#CM53574
	/**
	 * When the Job No. list button is pressed, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ProductionNumber_Click(ActionEvent e) throws Exception
	{
		//#CM53575
		//Set the search condition in the . 
		ForwardParameters param = new ForwardParameters();
		
		//#CM53576
		//Processing Screen -> Result screen
		redirect("/asrs/tool/listbox/productionlist/ProductionList.do", param, "/progress.do");
	}

	//#CM53577
	/**
	 * When the button is pressed as follows, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			txt_ProductionNumber.validate();

			//#CM53578
			// Check the Job No. restriction character. 
			ArrayList chklist = new ArrayList();
			chklist.add("\\");
			chklist.add("/");
			chklist.add(":");
			chklist.add("*");
			chklist.add("?");
			chklist.add("\"");
			chklist.add("<");
			chklist.add(">");
			chklist.add("|");
			
			String prdNumber = txt_ProductionNumber.getText();	
			for (int i = 0; i < prdNumber.length(); i ++)
			{
				if (chklist.contains(prdNumber.substring(i, i + 1)))
				{
					//#CM53579
					// 6003101=Makes {0} and the character which cannot be used is included. 
					message.setMsgResourceKey("6003101" + MSG_DELIM + DisplayText.getText("TLBL-W0121"));							
					return;
				}
			}
				
			//#CM53580
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM53581
			//Authorisation
			setUserInfo(new DfkUserInfo());

			//#CM53582
			//
			session.setMaxInactiveInterval(-1);

			DataOperator dataope = new DataOperator(conn);
			//#CM53583
			//<en> Login will be processed.</en>
			if (!dataope.login(getHttpRequest(), prdNumber))
			{
				//#CM53584
				//<en> Set the message.</en>
				message.setMsgResourceKey(dataope.getMessage());
			}
			else
			{
				//#CM53585
				//Menu screen display when succeeding in log in
				forward("/jsp/asrs/tool/login/frame.html");
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM53586
				//Close the Connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53587
	/**
	 * When 'Enter' is pressed in the Job No. text box, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ProductionNumber_EnterKey(ActionEvent e) throws Exception
	{
		btn_Next_Click(null);
	}
	//#CM53588
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Production_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53589
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ProductionNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53590
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ProductionNumber_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM53591
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ProductionNumber_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM53592
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ProductionNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53593
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM53594
//end of class
