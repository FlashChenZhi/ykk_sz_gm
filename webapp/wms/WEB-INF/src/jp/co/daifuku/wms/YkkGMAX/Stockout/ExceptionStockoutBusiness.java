// $Id: ExceptionStockoutBusiness.java,v 1.7 2008/01/02 02:35:13 administrator Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Stockout;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.util.ResourceHandler;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExceptionStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.YkkGMAX.resident.AfterStockoutRequestProcessor;
import jp.co.daifuku.wms.YkkGMAX.resident.ExceptionStockoutRequestProcessor;
import jp.co.daifuku.wms.YkkGMAX.resident.ProcessorInvoker;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;

/**
 * Ths screen business logic has to be implemented in this class.<BR>
 * This class is generated by ScreenGenerator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/02/13</TD>
 * <TD>N.Sawa(DFK)</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.7 $, $Date: 2008/01/02 02:35:13 $
 * @author $Author: administrator $
 */
public class ExceptionStockoutBusiness extends ExceptionStockout implements
	WMSConstants
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    private final String DIALOG_FLAG = "DIALOG_FLAG";

    private final String ITEM_CODE = "ITEM_CODE";

    private final String MODE_NO = "MODE_NO";

    private final String EXCEPTION_STOCKOUT_ENTITY = "EXCEPTION_STOCKOUT_ENTITY";

    private final String STOCKOUT_MODE = "STOCKOUT_MODE";

    private final String MANAGE_ITEM_FLAG = "MANAGE_ITEM_FLAG";

    private final ResourceHandler resourceHandler = new ResourceHandler(
	    StringUtils.DispResourceName);

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_Help_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_ItemBrowse1_Click(ActionEvent e) throws Exception
    {
	session.setAttribute(ITEM_CODE, txt_ItemNo1.getText());
	session.setAttribute(MODE_NO, "1");

	redirect("/YkkGMAX/Popup/ItemView.do", null, "/progress.do");
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_ItemBrowse1_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_ItemBrowse2_Click(ActionEvent e) throws Exception
    {
	session.setAttribute(ITEM_CODE, txt_ItemNo2.getText());
	session.setAttribute(MODE_NO, "2");

	redirect("/YkkGMAX/Popup/ItemView.do", null, "/progress.do");
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_ItemBrowse2_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_ItemBrowse3_Click(ActionEvent e) throws Exception
    {
	session.setAttribute(ITEM_CODE, txt_ItemNo3.getText());
	session.setAttribute(MODE_NO, "3");

	redirect("/YkkGMAX/Popup/ItemView.do", null, "/progress.do");
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_ItemBrowse3_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_Set_Click(ActionEvent e) throws Exception
    {
	ExceptionStockoutEntity entity = (ExceptionStockoutEntity) session
		.getAttribute(EXCEPTION_STOCKOUT_ENTITY);
	String stockoutCondition = "";

    setFocus(txt_Memo);
    txt_Memo.validate();
    setFocus(null);
	if (this.rdo_OverTimeStorageStockout.getChecked())
	{
	    entity.setStockoutMode(1);
	    stockoutCondition = resourceHandler.getText(lbl_StockinDateTime
		    .getResourceKey())
		    + resourceHandler.getText(lbl_BeforeThisDate
			    .getResourceKey());
	    entity.setStockinDate(StringUtils
		    .formatDateFromPageToDB(txt_StockinDate.getText()));
	    if (StringUtils.IsNullOrEmpty(txt_StockinTime.getText()))
	    {
		entity.setStockinTime("000000");
	    }
	    else
	    {
		entity.setStockinTime(StringUtils
			.formatTimeFormPageToDB(txt_StockinTime.getText()));
	    }
	}
	else if (this.rdo_BitsStorageStockout.getChecked())
	{
	    entity.setStockoutMode(2);
	    stockoutCondition = resourceHandler.getText(lbl_ItemNo1
		    .getResourceKey())
		    + " "
		    + resourceHandler
			    .getText(lbl_InstockCount.getResourceKey());
	    entity.setItemCode(txt_ItemNo1.getText());
	    entity.setInstockCount(txt_InstockCount.getInt());
	}
	else if (this.rdo_LocationStockout.getChecked())
	{
	    entity.setStockoutMode(3);
	    stockoutCondition += resourceHandler
		    .getText(this.rdo_LocationStockout.getResourceKey());
	    if (!rdo_ItemNo2.getChecked())
	    {
		stockoutCondition += " "
			+ resourceHandler.getText(rdo_LocationRange
				.getResourceKey());
		entity.setLocationNoFrom(StringUtils
			.formatLocationNoFromPageToDB(txt_LocationNoFrom
				.getText()));
		entity.setLocationNoTo(StringUtils
			.formatLocationNoFromPageToDB(txt_LocationNoTo
				.getText()));
		entity.setSearchItemCode(false);
	    }
	    else
	    {
		stockoutCondition += " "
			+ resourceHandler.getText(rdo_ItemNo2.getResourceKey());
		entity.setItemCode(txt_ItemNo2.getText());
        entity.setColorCode(txt_ColorCode2.getText());
		entity.setSearchItemCode(true);
	    }
	}
	else if (this.rdo_SearchStockout.getChecked())
	{
	    entity.setStockoutMode(4);
	    stockoutCondition = resourceHandler.getText(lbl_ItemNo3
		    .getResourceKey())+ " "
                + resourceHandler
                .getText(lbl_ColorCode.getResourceKey());
	    entity.setItemCode(txt_ItemNo3.getText());
        entity.setColorCode(txt_ColorCode.getText());
	}
	else
	{
	    entity.setStockoutMode(5);
	    stockoutCondition = resourceHandler.getText(lbl_LocationNo
		    .getResourceKey());
	    entity.setLocationNo(StringUtils
		    .formatLocationNoFromPageToDB(txt_LocationNo.getText()));
	    entity.setAfterThisLocation(chk_AfterThisLocation.getChecked());
	}
	entity.setStockoutCondition(stockoutCondition);
    entity.setMemo(txt_Memo.getText());
	session.setAttribute(MODE_NO, "4");
	redirect("/YkkGMAX/Stockout/ExceptionStockoutPopup.do", null,
		"/progress.do");

    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_Set_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_ToMenu_Click(ActionEvent e) throws Exception
    {
	forward(BusinessClassHelper.getSubMenuPath(this.getViewState()
		.getString(M_MENUID_KEY)));
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void btn_ToMenu_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void chk_AfterThisLocation_Change(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void chk_AfterThisLocation_Server(ActionEvent e) throws Exception
    {
    }

    private void InitPage()
    {
	session.setAttribute(ITEM_CODE, null);
	session.setAttribute(EXCEPTION_STOCKOUT_ENTITY,
		new ExceptionStockoutEntity());
	session.setAttribute(MANAGE_ITEM_FLAG, "0");
	session.setAttribute(MODE_NO, "0");

    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_BeforeThisDate_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_Date_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_FewerThanThis_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_InstockCount_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_ItemNo1_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_ItemNo3_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_LocationNo_Server(ActionEvent e) throws Exception
    {
    }

    // Event handler methods -----------------------------------------
    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_SettingName_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_StockinDateTime_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_StockoutCondition_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void lbl_Time_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
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
	String stockoutMode = "0";
	if (rdo_OverTimeStorageStockout.getChecked())
	{
	    stockoutMode = "1";
	}
	else if (rdo_BitsStorageStockout.getChecked())
	{
	    stockoutMode = "2";
	}
	else if (rdo_LocationStockout.getChecked())
	{
	    stockoutMode = "3";
	}
	else if (rdo_SearchStockout.getChecked())
	{
	    stockoutMode = "4";
	}
	else if (rdo_DesignateLocationStockout.getChecked())
	{
	    stockoutMode = "5";
	}

	ExceptionStockoutEntity entity = (ExceptionStockoutEntity) session
		.getAttribute(EXCEPTION_STOCKOUT_ENTITY);

	ProcessorInvoker pi = new ProcessorInvoker(message);
	if (entity != null)
	{
	    pi.addProcessor(new ExceptionStockoutRequestProcessor(entity,
		    getUserInfo().getUserId(), stockoutMode));
	}
	if (pi.run())
	{

	    pi = new ProcessorInvoker(message);

	    pi.addProcessor(new AfterStockoutRequestProcessor());
	    pi.run();
	}
	InitPage();
    }

    public void page_DlgBack(ActionEvent e) throws Exception
    {
	((DialogEvent) e).getDialogParameters();

	int modeNo = Integer.parseInt(session.getAttribute(MODE_NO).toString());

	if (session.getAttribute(ITEM_CODE) != null
		&& (modeNo == 1 || modeNo == 2 || modeNo == 3))
	{
	    if (modeNo == 1)
	    {
		txt_ItemNo1.setText((String) session.getAttribute(ITEM_CODE));
	    }
	    else if (modeNo == 2)
	    {
		txt_ItemNo2.setText((String) session.getAttribute(ITEM_CODE));
	    }
	    else
	    {
		txt_ItemNo3.setText((String) session.getAttribute(ITEM_CODE));
	    }
	    InitPage();
	}
	else if (modeNo == 4)
	{
	    setConfirm("YKK-LBL-SetConfirm");
	    getViewState().setBoolean(DIALOG_FLAG, true);
	}

    }

    /**
     * Refered before calling each control event.
     * 
     * @param e
     *                ActionEvent
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
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e) throws Exception
    {

	Connection conn = null;

	try
	{
	    conn = ConnectionManager.getConnection();
	    ASRSInfoCentre centre = new ASRSInfoCentre(conn);
	    String roleId = centre.getRoleIdByUserId(getUserInfo().getUserId());

	    boolean enabled = roleId.equals("1");
	    boolean readonly = !enabled;

	    rdo_OverTimeStorageStockout.setEnabled(enabled);
	    txt_StockinDate.setReadOnly(readonly);
	    txt_StockinTime.setReadOnly(readonly);

	    rdo_BitsStorageStockout.setEnabled(enabled);
	    txt_ItemNo1.setReadOnly(readonly);
	    btn_ItemBrowse1.setEnabled(enabled);
	    txt_InstockCount.setReadOnly(readonly);

	    rdo_SearchStockout.setEnabled(enabled);
	    txt_ItemNo3.setReadOnly(readonly);
	    btn_ItemBrowse3.setEnabled(enabled);

	    rdo_DesignateLocationStockout.setEnabled(enabled);
	    txt_LocationNo.setReadOnly(readonly);
	    chk_AfterThisLocation.setEnabled(enabled);

	    if (enabled)
	    {
		session.setAttribute(STOCKOUT_MODE, "1");
		rdo_OverTimeStorageStockout.setChecked(true);
	    }
	    else
	    {
		session.setAttribute(STOCKOUT_MODE, "3");
		rdo_LocationStockout.setChecked(true);
	    }
	    InitPage();

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
	finally
	{
	    if (conn != null)
	    {
		conn.close();
	    }
	}
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_BitsStorageStockout_Click(ActionEvent e) throws Exception
    {
	session.setAttribute(STOCKOUT_MODE, "2");
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_BitsStorageStockout_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_DesignateLocationStockout_Click(ActionEvent e)
	    throws Exception
    {
	session.setAttribute(STOCKOUT_MODE, "5");
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_DesignateLocationStockout_Server(ActionEvent e)
	    throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_ItemNo2_Click(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_ItemNo2_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_LocationRange_Click(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_LocationRange_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_LocationStockout_Click(ActionEvent e) throws Exception
    {
	session.setAttribute(STOCKOUT_MODE, "3");
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_LocationStockout_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_OverTimeStorageStockout_Click(ActionEvent e)
	    throws Exception
    {
	session.setAttribute(STOCKOUT_MODE, "1");
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_OverTimeStorageStockout_Server(ActionEvent e)
	    throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_SearchStockout_Click(ActionEvent e) throws Exception
    {
	session.setAttribute(STOCKOUT_MODE, "4");
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void rdo_SearchStockout_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_InstockCount_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_InstockCount_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_InstockCount_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo1_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo1_AutoCompleteItemClick(ActionEvent e)
	    throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo1_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo1_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo1_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo1_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo2_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo2_AutoCompleteItemClick(ActionEvent e)
	    throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo2_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo2_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo2_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo2_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo3_AutoComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo3_AutoCompleteItemClick(ActionEvent e)
	    throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo3_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo3_InputComplete(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo3_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_ItemNo3_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_LocationNo_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_LocationNo_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_LocationNo_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_LocationNoFrom_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_LocationNoFrom_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_LocationNoFrom_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_LocationNoTo_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_LocationNoTo_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_LocationNoTo_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_StockinDate_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_StockinDate_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_StockinDate_TabKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_StockinTime_EnterKey(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_StockinTime_Server(ActionEvent e) throws Exception
    {
    }

    /**
     * 
     * @param e
     *                ActionEvent
     * @throws Exception
     */
    public void txt_StockinTime_TabKey(ActionEvent e) throws Exception
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
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ColorCode2_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode2_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode2_AutoCompleteItemClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode2_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode2_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode2_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ColorCode2_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Memo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo_AutoCompleteItemClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Memo_InputComplete(ActionEvent e) throws Exception
	{
	}


}
// end of class
