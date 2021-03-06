// $Id: SystemMaintenanceBusiness.java,v 1.1 2007/12/13 06:25:10 administrator Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.System;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalStationBufferSettingEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.SystemMaintenanceEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
 * @version $Revision: 1.1 $, $Date: 2007/12/13 06:25:10 $
 * @author  $Author: administrator $
 */
public class SystemMaintenanceBusiness extends SystemMaintenance implements WMSConstants
{
	// Class fields --------------------------------------------------
    private final String DIALOG_FLAG = "DIALOG_FLAG";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * Initializes the screen.
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
        Connection conn = null;
        try
        {
            conn = ConnectionManager.getConnection();

            ASRSInfoCentre centre = new ASRSInfoCentre(conn);

            int fifoDay = centre.getSystemData();

            txt_FIFODays.setInt(fifoDay);
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
                try
                {
                    conn.close();
                }
                catch (SQLException ex)
                {
                    DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
                    message.setMsgResourceKey("7200002");
                }
            }
        }
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

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

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
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
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
        setFocus(txt_FIFODays);
        txt_FIFODays.validate();
        setFocus(null);
        setConfirm("YKK-LBL-SetConfirm");
        getViewState().setBoolean(DIALOG_FLAG, true);
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
        Connection conn = null;
        try
        {
            conn = ConnectionManager.getConnection();

            ASRSInfoCentre centre = new ASRSInfoCentre(conn);

            SystemMaintenanceEntity entity = new SystemMaintenanceEntity();
            entity.setFifoDays(txt_FIFODays.getInt());
            centre.systemMaintenance(entity);

            message.setMsgResourceKey("7400002");
            conn.commit();
            btn_Clear_Click(null);
        } catch (YKKDBException dbEx)
        {
            String msgString = MessageResources.getText(dbEx.getResourceKey());
            DebugPrinter.print(DebugLevel.ERROR, msgString);
            message.setMsgResourceKey("7200001");
            List paramList = new ArrayList();
            paramList.add(msgString);
            message.setMsgParameter(paramList);
            try
            {
                if (conn != null)
                {
                    conn.rollback();
                }
            } catch (SQLException ex)
            {
                DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
                message.setMsgResourceKey("7200002");
            }
        } catch (YKKSQLException sqlEx)
        {
            String msgString = MessageResources.getText(sqlEx.getResourceKey());
            DebugPrinter.print(DebugLevel.ERROR, msgString);
            message.setMsgResourceKey("7300001");
            List paramList = new ArrayList();
            paramList.add(msgString);
            message.setMsgParameter(paramList);
            try
            {
                if (conn != null)
                {
                    conn.rollback();
                }
            } catch (SQLException ex)
            {
                DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
                message.setMsgResourceKey("7200002");
            }
        } finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                } catch (SQLException sqlex)
                {
                    DebugPrinter.print(DebugLevel.ERROR, sqlex.getMessage());
                    message.setMsgResourceKey("7200002");
                }
            }
        }
    }

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FIFODays_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FIFODays_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FIFODays_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FIFODays_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Day_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
        page_Load(null);
	}


}
//end of class
