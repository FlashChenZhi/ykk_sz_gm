// $Id: SystemDataBusiness.java,v 1.3 2006/10/30 06:30:37 suresh Exp $

//#CM54395
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.systemdata;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.dataoperate.DataOperator;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

//#CM54396
/**
<kt> * The system definition file generation screen class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/30 06:30:37 $
 * @author  $Author: suresh $
 */
public class SystemDataBusiness extends SystemData implements WMSToolConstants
{
	//#CM54397
	// Class fields --------------------------------------------------

	//#CM54398
	// Class variables -----------------------------------------------

	//#CM54399
	// Class method --------------------------------------------------

	//#CM54400
	// Constructors --------------------------------------------------

	//#CM54401
	// Public methods ------------------------------------------------

	//#CM54402
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM54403
		//Set the screen name.
		lbl_SettingName.setResourceKey("TMEN-W0014-1");
		//#CM54404
		//Set the path of help file.
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0014-1", this.getHttpRequest()));		
	}

	//#CM54405
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
	}

	//#CM54406
	// Package methods -----------------------------------------------

	//#CM54407
	// Protected methods ---------------------------------------------

	//#CM54408
	// Private methods -----------------------------------------------

	//#CM54409
	// Event handler methods -----------------------------------------
	//#CM54410
	/**
	 * It is called when Menu button is pressed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM54411
	/**
	 * It is called when Start button is pressed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM54412
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DataOperator dataope = new DataOperator(conn);
			dataope.createTextFiles(this.getHttpRequest());

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
				//#CM54413
				//Connection close
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM54414
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54415
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54416
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM54417
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54418
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SystemDataMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM54419
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM54420
//end of class
