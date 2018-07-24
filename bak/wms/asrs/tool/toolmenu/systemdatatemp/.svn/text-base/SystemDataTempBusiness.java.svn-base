// $Id: SystemDataTempBusiness.java,v 1.3 2006/10/30 06:30:46 suresh Exp $

//#CM54425
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.systemdatatemp;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.dataoperate.DataOperator;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

//#CM54426
/**
 * This class creates system definition file (temporary storage)
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/30 06:30:46 $
 * @author  $Author: suresh $
 */
public class SystemDataTempBusiness extends SystemDataTemp implements WMSToolConstants
{
	//#CM54427
	// Class fields --------------------------------------------------

	//#CM54428
	// Class variables -----------------------------------------------

	//#CM54429
	// Class method --------------------------------------------------

	//#CM54430
	// Constructors --------------------------------------------------

	//#CM54431
	// Public methods ------------------------------------------------

	//#CM54432
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM54433
		//set the screen name.
		lbl_SettingName.setResourceKey("TMEN-W0014-2");
		//#CM54434
		//Set the path of help file
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0014-2", this.getHttpRequest()));		
	}

	//#CM54435
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
	}

	//#CM54436
	// Package methods -----------------------------------------------

	//#CM54437
	// Protected methods ---------------------------------------------

	//#CM54438
	// Private methods -----------------------------------------------

	//#CM54439
	// Event handler methods -----------------------------------------
	//#CM54440
	/**
	 * It is called when the Menu button is pressed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM54441
	/**
	 * It is called when the Start button is pressed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM54442
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DataOperator dataope = new DataOperator(conn);
			dataope.createTextFiles(this.getHttpRequest(), false);

			message.setMsgResourceKey(dataope.getMessage());
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM54443
				//Connection close
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54444
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SystemDataMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM54450
//end of class
