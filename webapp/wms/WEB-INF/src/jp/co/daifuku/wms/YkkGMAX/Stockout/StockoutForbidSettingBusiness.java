// $Id: StockoutForbidSettingBusiness.java,v 1.3 2007/12/30 05:17:36 administrator Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Stockout;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutForbidEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.YkkGMAX.resident.ProcessorInvoker;
import jp.co.daifuku.wms.YkkGMAX.resident.StockoutForbidReleaseRequestProcessor;
import jp.co.daifuku.wms.YkkGMAX.resident.StockoutForbidSetRequestProcessor;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;

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
 * @version $Revision: 1.3 $, $Date: 2007/12/30 05:17:36 $
 * @author  $Author: administrator $
 */
public class StockoutForbidSettingBusiness extends StockoutForbidSetting implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	private final String STOCKIN_DATE_FROM = "STOCKIN_DATE_FROM";
	private final String STOCKIN_DATE_TO = "STOCKIN_DATE_TO";
	private final String ITEM_CODE = "ITEM_CODE";
	private final String TICKET_NO_FROM = "TICKET_NO_FROM";
	private final String TICKET_NO_TO = "TICKET_NO_TO";
	private final String COLOR_CODE = "COLOR_CODE";
	private final String DIALOG_FLAG = "DIALOG_FLAG";

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ItemBrowse_Click(ActionEvent e) throws Exception
	{
		session.setAttribute(ITEM_CODE, txt_ItemNo.getText());
		if(rdo_Forbid.getChecked())
		{
			redirect("/YkkGMAX/Popup/ItemView.do", null, "/progress.do");
		}
		else
		{
			redirect("/YkkGMAX/Popup/ForbidItemView.do", null, "/progress.do");
		}
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ItemBrowse_Server(ActionEvent e) throws Exception
	{
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Set_Click(ActionEvent e) throws Exception
	{
		ValidateCheck();
		setConfirm("YKK-LBL-SetConfirm");
		getViewState().setBoolean(DIALOG_FLAG, true);
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
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState()
				.getString(M_MENUID_KEY)));
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	private void ClearControls()
	{
		txt_ItemNo.setText("");
		txt_TicketNoFrom.setText("");
		txt_TicketNoTo.setText("");
		txt_ColorCode.setText("");
		txt_StockinDateFrom.setText("");
		txt_StockinTimeFrom.setText("");
		txt_StockinDateTo.setText("");
		txt_StockinTimeTo.setText("");
		
	}

	private void InitSession()
	{
		session.setAttribute(ITEM_CODE, null);
		session.setAttribute(TICKET_NO_FROM, null);
		session.setAttribute(TICKET_NO_TO, null);
		session.setAttribute(COLOR_CODE, null);
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
	public void lbl_Division_Server(ActionEvent e) throws Exception
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

	// Event handler methods -----------------------------------------
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Star1_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_to_Server(ActionEvent e) throws Exception
	{
	}

	public void page_ConfirmBack(ActionEvent e) throws Exception
	{

		if (!this.getViewState().getBoolean(DIALOG_FLAG))
		{
			return;
		}
		boolean isExecute = new Boolean(e.getEventArgs().get(0).toString())
				.booleanValue();
		if (!isExecute)
		{
			return;
		}
		message.setMsgResourceKey("7000034");
		ProcessorInvoker pi = new ProcessorInvoker(message);
		StockoutForbidEntity entity = new StockoutForbidEntity();
		entity.setItemCode(txt_ItemNo.getText());
		entity.setTicketNoFrom(txt_TicketNoFrom.getText());
		entity.setTicketNoTo(txt_TicketNoTo.getText());
		entity.setColorCode(txt_ColorCode.getText());
		entity.setStockInDateFrom(txt_StockinDateFrom.getText().replaceAll("/", ""));
		entity.setStockInTimeFrom(txt_StockinTimeFrom.getText().replaceAll(":",""));
		entity.setStockInDateTo(txt_StockinDateTo.getText().replaceAll("/", ""));
		entity.setStockInTimeTo(txt_StockinTimeTo.getText().replaceAll(":", ""));
		
		if(rdo_Forbid.getChecked())
		{
			pi.addProcessor(new StockoutForbidSetRequestProcessor(entity));
		}
		else
		{
			pi.addProcessor(new StockoutForbidReleaseRequestProcessor(entity));
		}
		pi.run();
		
		ClearControls();
	}

	public void page_DlgBack(ActionEvent e) throws Exception
	{
		((DialogEvent) e).getDialogParameters();

		if(session.getAttribute(ITEM_CODE) != null)
		{
			txt_ItemNo.setText((String) session.getAttribute(ITEM_CODE));
		}
		if(session.getAttribute(TICKET_NO_FROM) != null)
		{
			txt_TicketNoFrom.setText((String) session.getAttribute(TICKET_NO_FROM));
		}
		if(session.getAttribute(TICKET_NO_TO) != null)
		{
			txt_TicketNoTo.setText((String) session.getAttribute(TICKET_NO_TO));
		}
		if(session.getAttribute(COLOR_CODE) != null)
		{
			txt_ColorCode.setText((String) session.getAttribute(COLOR_CODE));
		}
		if(session.getAttribute(STOCKIN_DATE_FROM) != null)
		{
			txt_StockinDateFrom.setText(((String) session.getAttribute(STOCKIN_DATE_FROM)).split(" ")[0]);
			txt_StockinTimeFrom.setText(((String) session.getAttribute(STOCKIN_DATE_FROM)).split(" ")[1]);
		}
		if(session.getAttribute(STOCKIN_DATE_TO) != null)
		{
			txt_StockinDateTo.setText(((String) session.getAttribute(STOCKIN_DATE_TO)).split(" ")[0]);
			txt_StockinTimeTo.setText(((String) session.getAttribute(STOCKIN_DATE_TO)).split(" ")[1]);
		}
		InitSession();
	}

	/**
	 * Refered before calling each control event.
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
		    // #CM37828
		    // fetch parameter
		    String title = CollectionUtils.getMenuParam(0, menuparam);
		    String functionID = CollectionUtils.getMenuParam(1, menuparam);
		    String menuID = CollectionUtils.getMenuParam(2, menuparam);
		    // #CM37829
		    // save to viewstate
		    this.getViewState().setString(M_TITLE_KEY, title);
		    this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
		    this.getViewState().setString(M_MENUID_KEY, menuID);
		    // #CM37830
		    // set screen name
		    // lbl_SettingName.setResourceKey(title);
		}
	}

	/**
	 * Initializes the screen.
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		InitSession();
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Forbid_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Forbid_Server(ActionEvent e) throws Exception
	{
	}
	
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Release_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Release_Server(ActionEvent e) throws Exception
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
	public void txt_ColorCode_AutoCompleteItemClick(ActionEvent e) throws Exception
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
	public void txt_ColorCode_InputComplete(ActionEvent e) throws Exception
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
	public void txt_ColorCode_TabKey(ActionEvent e) throws Exception
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
	public void txt_ItemNo_AutoCompleteItemClick(ActionEvent e) throws Exception
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
	public void txt_ItemNo_InputComplete(ActionEvent e) throws Exception
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
	public void txt_ItemNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoFrom_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoFrom_AutoCompleteItemClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoFrom_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoFrom_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoFrom_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoFrom_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoTo_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoTo_AutoCompleteItemClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoTo_EnterKey(ActionEvent e) throws Exception
	{
	}
	
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoTo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoTo_Server(ActionEvent e) throws Exception
	{
	}
		/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoTo_TabKey(ActionEvent e) throws Exception
	{
	}

	private void ValidateCheck() throws ValidateException
	{
		setFocus(txt_ItemNo);
		txt_ItemNo.validate();
		if(rdo_Release.getChecked())
		{
			setFocus(txt_TicketNoFrom);
			txt_TicketNoFrom.validate();
			setFocus(txt_TicketNoTo);
			txt_TicketNoTo.validate();
			
		}
		setFocus(null);
		
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockinDateTime_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DateFrom_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinDateFrom_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinDateFrom_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinDateFrom_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TimeFrom_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinTimeFrom_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinTimeFrom_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinTimeFrom_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_to_2_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DateTo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinDateTo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinDateTo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinDateTo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TimeTo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinTimeTo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinTimeTo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockinTimeTo_TabKey(ActionEvent e) throws Exception
	{
	}


}
//end of class
