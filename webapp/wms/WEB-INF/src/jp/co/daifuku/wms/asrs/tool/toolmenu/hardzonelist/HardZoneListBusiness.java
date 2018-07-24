// $Id: HardZoneListBusiness.java,v 1.2 2006/10/30 04:56:45 suresh Exp $

//#CM53011
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.hardzonelist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.toolmenu.BusinessClassHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

//#CM53012
/**
 * Hard Zone inquiry screen Class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:56:45 $
 * @author  $Author: suresh $
 */
public class HardZoneListBusiness extends HardZoneList implements WMSToolConstants
{
	//#CM53013
	// Class fields --------------------------------------------------

	//#CM53014
	// Class variables -----------------------------------------------

	//#CM53015
	// Class method --------------------------------------------------

	//#CM53016
	// Constructors --------------------------------------------------

	//#CM53017
	// Public methods ------------------------------------------------

	//#CM53018
	/** <en>
	 * It is called before each control event call.
	 * @param e ActionEvent
	 </en> */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//#CM53019
		//Set screen Name. 
		lbl_SettingName.setResourceKey("TMEN-W0005-3");
		//#CM53020
		//Set passing to the Help file. 
		btn_Help.setUrl(BusinessClassHelper.getHelpPath("TMEN-0005-3", this.getHttpRequest()));		
	}

	//#CM53021
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
			//#CM53022
			//Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			ToolPulldownData pull = new ToolPulldownData(conn, locale, null);
	
			//#CM53023
			//<en> Set the pull-down data.</en>
			String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "", ToolPulldownData.ZONE_ALL);

			if (whno.length > 0)
			{
				String[] bank = pull.getwhBankPulldownData("", 0);

				//#CM53024
				//Set data in the pull down.
				ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
				ToolPulldownHelper.setLinkedPullDown(pul_Bank, bank);
				//#CM53025
				//Child Pulldown is registered. 
				pul_StoreAs.addChild(pul_Bank);
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
				//#CM53026
				//Close the Connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM53027
	// Package methods -----------------------------------------------

	//#CM53028
	// Protected methods ---------------------------------------------

	//#CM53029
	// Private methods -----------------------------------------------

	//#CM53030
	// Event handler methods -----------------------------------------
	//#CM53031
	/**
	 * When the Menu button is clicked, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/SubMenu.do");
	}

	//#CM53032
	/** 
	 * When the inquiry button is pressed, it is called. 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
		if (pul_StoreAs.getSelectedValue() == null)
		{
			//#CM53033
			//<en>6123100 = The warehouse information does not exist. Please register in warehouse setting screen.</en>
			message.setMsgResourceKey("6123100");
			return;			
		}
		if (pul_Bank.getSelectedValue() == null)
		{
			//#CM53034
			//<en>6123113 = The location control information does not exist. Please register in aisle setting screen.</en>
			message.setMsgResourceKey("6123113");
			return;			
		}
		
		this.getSession().setAttribute("wSelectedBank", pul_Bank.getSelectedValue());
		this.getSession().setAttribute("wSelectedWareHouseStationNumber", pul_StoreAs.getSelectedValue());		
	}

	//#CM53035
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53036
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53037
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM53038
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53039
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouseNumber_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53040
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53041
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StoreAs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53042
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Bank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53043
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Bank_Server(ActionEvent e) throws Exception
	{
	}

	//#CM53044
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Bank_Change(ActionEvent e) throws Exception
	{
	}

	//#CM53045
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Query_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM53046
//end of class
