// $Id: TerminalViewBusiness.java,v 1.5 2008/03/26 02:58:06 wms Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Popup;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.TerminalViewListProxy;
import jp.co.daifuku.wms.YkkGMAX.PageController.PageController;
import jp.co.daifuku.wms.YkkGMAX.PageController.TerminalViewPager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.base.common.WMSConstants;

/**
 * Ths screen business logic has to be implemented in this class.<BR>
 * This class is generated by ScreenGenerator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2008/03/26 02:58:06 $
 * @author  $Author: wms $
 */
public class TerminalViewBusiness extends TerminalView implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	private final String FNRANGE_ENTITY = "FNRANGE_ENTITY";
	private PageController pageController= new PageController(new TerminalViewPager(this,pgr_Up),new TerminalViewPager(this,pgr_Low),message);
	private TerminalViewListProxy listProxy = new TerminalViewListProxy(lst_TerminalViewList);
	/**
	 * Initializes the screen.
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		try
		{
			pageController.init();
			pageController.setCountPerPage(30);
			pageController.turnToFirstPage();

		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
	}

	/**
	 * Refered before calling each control event.
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Event handler methods -----------------------------------------
	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Up_First(ActionEvent e)
	{
		try
		{
			pageController.turnToFirstPage();
		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Up_Last(ActionEvent e)
	{
		try
		{
			pageController.turnToLastPage();
		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Up_Next(ActionEvent e)
	{
		try
		{
			pageController.turnToNextPage();
		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Up_Prev(ActionEvent e)
	{
		try
		{
			pageController.turnToPreviousPage();
		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Low_First(ActionEvent e) throws Exception
	{
		try
		{
			pageController.turnToFirstPage();
		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Low_Last(ActionEvent e) throws Exception
	{
		try
		{
			pageController.turnToLastPage();
		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Low_Next(ActionEvent e) throws Exception
	{
		try
		{
			pageController.turnToNextPage();
		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Low_Prev(ActionEvent e) throws Exception
	{
		try
		{
			pageController.turnToPreviousPage();
		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_TerminalViewList_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_TerminalViewList_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_TerminalViewList_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_TerminalViewList_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_TerminalViewList_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_TerminalViewList_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_TerminalViewList_Click(ActionEvent e) throws Exception
	{
		int row = Integer.parseInt(e.getEventArgs().get(0).toString());
		int col = Integer.parseInt(e.getEventArgs().get(1).toString());

		if (col == listProxy.getNO_COLUMN())
		{
			lst_TerminalViewList.setCurrentRow(row);

			session.setAttribute(FNRANGE_ENTITY, listProxy.getRowEntity());

			ForwardParameters param = new ForwardParameters();
			parentRedirect(param);
		}
	}
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Up_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Up_Click(ActionEvent e) throws Exception
	{
		this.closeWindow();
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Low_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Low_Click(ActionEvent e) throws Exception
	{
		this.closeWindow();
	}


}
//end of class
