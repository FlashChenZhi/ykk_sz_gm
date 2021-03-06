// $Id: skeltenBusiness.java,v 1.2 2007/03/07 07:45:23 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Stockout;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.SystemIdSortableHandler;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.StockoutPopupListProxy;
import jp.co.daifuku.wms.YkkGMAX.PageController.PageController;
import jp.co.daifuku.wms.YkkGMAX.PageController.StockoutDetailPager;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
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
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:45:23 $
 * @author  $Author: suresh $
 */
public class StockoutPopupBusiness extends StockoutPopup implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	
	private final String ENABLE_TO_STOCKOUT_COUNT = "ENABLE_TO_STOCKOUT_COUNT";
	private final String DESIGNATE_LOCATION_LIST = "DESIGNATE_LOCATION_LIST";
	private final String DESIGNATE_LOCATION_ENTITY = "DESIGNATE_LOCATION_ENTITY";
	private final String DESIGNATE_LOCATION_INDEX = "DESIGNATE_LOCATION_INDEX";
	private final String STOCKOUT_DETAIL_LIST = "STOCKOUT_DETAIL_LIST";
	
	private StockoutPopupListProxy listProxy = new StockoutPopupListProxy(lst_StockoutPopup,this);
	private final PageController pageController = new PageController(
			new StockoutDetailPager(this, pgr_Up),new StockoutDetailPager(this,pgr_Low),message);
	/**
	 * Initializes the screen.
	 * @param e ActionEvent
	 * @throws Exception
	 */

	public void page_Load(ActionEvent e) throws Exception
	{
		session.setAttribute(DESIGNATE_LOCATION_INDEX, "0");
		ReshowPage();

	}

	private void ReshowPage() throws Exception
	{
		session.setAttribute(ENABLE_TO_STOCKOUT_COUNT, "0");
		ArrayList designateStockoutEntityList = (ArrayList) session
		.getAttribute(DESIGNATE_LOCATION_LIST);	
		int index = Integer.parseInt(session.getAttribute(DESIGNATE_LOCATION_INDEX).toString());
		
		if (designateStockoutEntityList.size() > index)
		{
			session.setAttribute(DESIGNATE_LOCATION_ENTITY, designateStockoutEntityList.get(index));
			
			StockoutEntity designateStockoutEntity = (StockoutEntity)designateStockoutEntityList.get(index);
			session.setAttribute(STOCKOUT_DETAIL_LIST, new ArrayList());

			txt_WhenNextWorkBegin.setText(StringUtils.formatDateFromDBToPage(designateStockoutEntity.getWhenNextWorkBegin()));
			txt_WhenThisWorkFinishInPlan.setText(StringUtils.formatDateFromDBToPage(designateStockoutEntity.getWhenThisWorkFinishInPlan()));
			txt_ItemNo.setText(designateStockoutEntity.getItemCode());
			txt_ItemName.setText(designateStockoutEntity.getItemName1());
			txt_ColorCode.setText(designateStockoutEntity.getColor());
			txt_EnableToStockoutCount.setLong(0);
			txt_StockoutCount.setLong(designateStockoutEntity.getStockoutCount());
			
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
		else
		{
			ForwardParameters param = new ForwardParameters();
			parentRedirect(param);
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
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WhenNextWorkBegin_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WhenNextWorkBegin_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WhenNextWorkBegin_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WhenNextWorkBegin_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WhenThisWorkFinishInPlan_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WhenThisWorkFinishInPlan_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WhenThisWorkFinishInPlan_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WhenThisWorkFinishInPlan_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNo_AutoCompleteItemClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNo_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_AutoCompleteItemClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ColorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode_AutoCompleteItemClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *                ActionEvent
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
	 *                ActionEvent
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
	 *                ActionEvent
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
	 * @param e ActionEvent 
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
	 * @param e ActionEvent 
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
	 * @param e ActionEvent 
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
	 * @param e ActionEvent 
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
	public void lst_StockoutPopup_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockoutPopup_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockoutPopup_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockoutPopup_ColumClick(ActionEvent e) throws Exception
	{

		ArrayList list = (ArrayList) session
				.getAttribute(STOCKOUT_DETAIL_LIST);
		list.clear();
		session.setAttribute(ENABLE_TO_STOCKOUT_COUNT, "0");
		txt_EnableToStockoutCount.setInt(0);
		for (int i = 1; i < lst_StockoutPopup.getMaxRows(); ++i)
		{
			lst_StockoutPopup.setCurrentRow(i);
			listProxy.setAll(false);
		}
		
		session.setAttribute(ENABLE_TO_STOCKOUT_COUNT, "0");
		txt_EnableToStockoutCount.setInt(0);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockoutPopup_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockoutPopup_Change(ActionEvent e) throws Exception
	{
		int row = Integer.parseInt(e.getEventArgs().get(0).toString());
		int col = Integer.parseInt(e.getEventArgs().get(1).toString());
		ArrayList list = (ArrayList) session
		.getAttribute(STOCKOUT_DETAIL_LIST);
		if(col == listProxy.getALL_COLUMN())
		{
			lst_StockoutPopup.setCurrentRow(row);
			if(listProxy.getAll())
			{
				SystemIdSortableHandler.insert(list, listProxy.getStockoutDetailEntity());
			}
			else
			{
				SystemIdSortableHandler.remove(list, listProxy.getStockoutDetailEntity());
			}
			
			long enableToStockout = Long.parseLong((String)session.getAttribute(ENABLE_TO_STOCKOUT_COUNT));
			if(listProxy.getAll())
			{
				enableToStockout += Long.parseLong(String.valueOf(listProxy.getEnableToStockoutCount()));
			}
			else
			{
				enableToStockout -= Long.parseLong(String.valueOf(listProxy.getEnableToStockoutCount()));
			}
			session.setAttribute(ENABLE_TO_STOCKOUT_COUNT, String.valueOf(enableToStockout));
			txt_EnableToStockoutCount.setLong(enableToStockout);

		}
		
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StockoutPopup_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Set_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Set_Click(ActionEvent e) throws Exception
	{
		ArrayList designageLocationList = (ArrayList)session.getAttribute(DESIGNATE_LOCATION_LIST);
		int index = Integer.parseInt(session.getAttribute(DESIGNATE_LOCATION_INDEX).toString());
		
		ArrayList stockoutDetailList = (ArrayList)session.getAttribute(STOCKOUT_DETAIL_LIST);
		
		if(stockoutDetailList.size() <= 0)
		{
			message.setMsgResourceKey("7000033");
			return;
		}
		
		((StockoutEntity) designageLocationList.get(index)).setStockoutDetailList(stockoutDetailList);
		
		session.setAttribute(DESIGNATE_LOCATION_INDEX, String.valueOf(index + 1));
		ReshowPage();
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EnableToStockoutCount_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Slash1_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockoutCount_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EnableToStockoutCount_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EnableToStockoutCount_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EnableToStockoutCount_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Slash2_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockoutCount_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockoutCount_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockoutCount_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reshow_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reshow_Click(ActionEvent e) throws Exception
	{
		ReshowPage();
	}
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Click(ActionEvent e) throws Exception
	{
		this.closeWindow();
	}


}
//end of class
