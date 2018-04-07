// $Id: ItemMasterMaintenanceBusiness.java,v 1.2 2008/01/02 02:35:12 administrator Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Maintenance;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.YkkGMAX.Entities.ItemViewEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
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
 * @version $Revision: 1.2 $, $Date: 2008/01/02 02:35:12 $
 * @author $Author: administrator $
 */
public class ItemMasterMaintenanceBusiness extends ItemMasterMaintenance
		implements WMSConstants
{

	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	private final String MAINTENANCE_MODE = "MAINTENANCE_MODE";
	private final String MANAGE_ITEM_FLAG = "MANAGE_ITEM_FLAG";
	private final String ITEM_CODE = "ITEM_CODE";
	private final String DIALOG_FLAG = "DIALOG_FLAG";

	/**
	 * Initializes the screen.
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		InitSession();
		btn_Add.setEnabled(false);
		btn_Delete.setEnabled(false);
	}

	private void InitSession()
	{
		session.setAttribute(MAINTENANCE_MODE, null);
		session.setAttribute(ITEM_CODE, null);
		session.setAttribute(MANAGE_ITEM_FLAG, DBFlags.ManageItemFlag.INMANAGE);
	}

	/**
	 * Refered before calling each control event.
	 * 
	 * @param e
	 *            ActionEvent
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
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
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
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_AutoCompleteItemClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ItemCodeBrowse_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ItemCodeBrowse_Click(ActionEvent e) throws Exception
	{
		session.setAttribute(ITEM_CODE, txt_ItemCode.getText());

		setManageItemFlag();

		redirect("/YkkGMAX/Popup/ItemView.do", null, "/progress.do");
	}

	private void setManageItemFlag()
	{
		if (rdo_ItemInManage.getChecked())
		{
			session.setAttribute(MANAGE_ITEM_FLAG,
					DBFlags.ManageItemFlag.INMANAGE);
		}
		else
		{
			session.setAttribute(MANAGE_ITEM_FLAG,
					DBFlags.ManageItemFlag.WITHOUTMANAGE);
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_ManageDivision_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemInManage_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemInManage_Click(ActionEvent e) throws Exception
	{
		ClearControls();
		btn_Add.setEnabled(false);
		btn_Delete.setEnabled(false);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemOutOfManage_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemOutOfManage_Click(ActionEvent e) throws Exception
	{
		ClearControls();
		btn_Add.setEnabled(true);
		btn_Delete.setEnabled(true);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_DealDivision_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Add_Server(ActionEvent e) throws Exception
	{
	}

	private void setDeleteMode()
	{
		setManageItemFlag();
		txt_ItemName1.setReadOnly(true);
		txt_ItemName2.setReadOnly(true);
		txt_ItemName3.setReadOnly(true);
		txt_MasterFormerUnitCount.setReadOnly(true);
		txt_AutoDepoMaxCount.setReadOnly(true);
		txt_AutoDepoMinCount.setReadOnly(true);
		rdo_WithAccount.setEnabled(false);
		rdo_WithOutAccount.setEnabled(false);
		rdo_AutoDepoPickOut.setEnabled(false);
		rdo_FlatDepoPickOut.setEnabled(false);
		rdo_HasBagWeightDivisi.setEnabled(false);
		rdo_NoBagWeightDivisi.setEnabled(false);

		session.setAttribute(MAINTENANCE_MODE, "DELETE");
	}

	private void setAddMode()
	{
		setManageItemFlag();
		if (rdo_ItemInManage.getChecked())
		{
			txt_ItemName1.setReadOnly(true);
			txt_ItemName2.setReadOnly(true);
			txt_ItemName3.setReadOnly(true);
			txt_MasterFormerUnitCount.setReadOnly(false);
			txt_AutoDepoMaxCount.setReadOnly(false);
			txt_AutoDepoMinCount.setReadOnly(false);
			rdo_WithAccount.setEnabled(true);
			rdo_WithOutAccount.setEnabled(true);
			rdo_AutoDepoPickOut.setEnabled(true);
			rdo_FlatDepoPickOut.setEnabled(true);
			rdo_HasBagWeightDivisi.setEnabled(true);
			rdo_NoBagWeightDivisi.setEnabled(true);
		}
		else
		{
			txt_ItemName1.setReadOnly(false);
			txt_ItemName2.setReadOnly(false);
			txt_ItemName3.setReadOnly(false);
			txt_MasterFormerUnitCount.setReadOnly(true);
			txt_AutoDepoMaxCount.setReadOnly(true);
			txt_AutoDepoMinCount.setReadOnly(true);
			rdo_WithAccount.setEnabled(false);
			rdo_WithOutAccount.setEnabled(false);
			rdo_AutoDepoPickOut.setEnabled(false);
			rdo_FlatDepoPickOut.setEnabled(false);
			rdo_HasBagWeightDivisi.setEnabled(false);
			rdo_NoBagWeightDivisi.setEnabled(false);
		}

		session.setAttribute(MAINTENANCE_MODE, "ADD");
	}

	private void setModiMode()
	{
		setManageItemFlag();
		if (rdo_ItemInManage.getChecked())
		{
			txt_ItemName1.setReadOnly(true);
			txt_ItemName2.setReadOnly(true);
			txt_ItemName3.setReadOnly(true);
			txt_MasterFormerUnitCount.setReadOnly(false);
			txt_AutoDepoMaxCount.setReadOnly(false);
			txt_AutoDepoMinCount.setReadOnly(false);
			rdo_WithAccount.setEnabled(true);
			rdo_WithOutAccount.setEnabled(true);
			rdo_AutoDepoPickOut.setEnabled(true);
			rdo_FlatDepoPickOut.setEnabled(true);
			rdo_HasBagWeightDivisi.setEnabled(true);
			rdo_NoBagWeightDivisi.setEnabled(true);
		}
		else
		{
			txt_ItemName1.setReadOnly(false);
			txt_ItemName2.setReadOnly(false);
			txt_ItemName3.setReadOnly(false);
			txt_MasterFormerUnitCount.setReadOnly(true);
			txt_AutoDepoMaxCount.setReadOnly(true);
			txt_AutoDepoMinCount.setReadOnly(true);
			rdo_WithAccount.setEnabled(false);
			rdo_WithOutAccount.setEnabled(false);
			rdo_AutoDepoPickOut.setEnabled(false);
			rdo_FlatDepoPickOut.setEnabled(false);
			rdo_HasBagWeightDivisi.setEnabled(false);
			rdo_NoBagWeightDivisi.setEnabled(false);
		}

		session.setAttribute(MAINTENANCE_MODE, "MODIFY");
	}

	private void ValidateCheck1() throws ValidateException
	{
		setFocus(txt_ItemCode);
		txt_ItemCode.validate();
		setFocus(null);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Add_Click(ActionEvent e) throws Exception
	{
		ValidateCheck1();
		setAddMode();
		ClearControls();
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			if (centre.getItemMasterCount(txt_ItemCode.getText()) > 0)
			{
				message.setMsgResourceKey("7000004");
				return;
			}

			ClearControls();

			txt_ItemCode_RO.setText(txt_ItemCode.getText());

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
				catch (SQLException sqlex)
				{
					DebugPrinter.print(DebugLevel.ERROR, sqlex.getMessage());
					message.setMsgResourceKey("7200002");
				}
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Modify_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Modify_Click(ActionEvent e) throws Exception
	{
		ValidateCheck1();
		setModiMode();
		ClearControls();
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			List itemMasterList = centre.getItemMasterList(txt_ItemCode
					.getText(), rdo_ItemInManage.getChecked());

			if (itemMasterList.size() <= 0)
			{
				message.setMsgResourceKey("7000005");
				return;
			}

			ItemViewEntity entity = (ItemViewEntity) itemMasterList.get(0);

			ClearControls();
			txt_ItemCode_RO.setText(entity.getItemCode());
			txt_ItemName1.setText(entity.getItemName1());
			txt_ItemName2.setText(entity.getItemName2());
			txt_ItemName3.setText(entity.getItemName3());
			txt_MasterUnit.setText(entity.getMasterUnit().toString());
			txt_MasterFormerUnitCount.setInt(entity.getUnitQty());
			txt_FullCount.setInt(entity.getLimitQty());
			txt_AutoDepoMaxCount.setInt(entity.getAutoDepoMaxCount());
			txt_AutoDepoMinCount.setInt(entity.getAutoDepoMinCount());
			if (entity.getUnitFlag().equals(DBFlags.UnitFlag.WITH_ACCOUNT))
			{
				rdo_WithAccount.setChecked(true);
			}
			else
			{
				rdo_WithOutAccount.setChecked(true);
			}
			if (entity.getBagFlag().equals(DBFlags.BagFlag.YES))
			{
				rdo_HasBagWeightDivisi.setChecked(true);
			}
			else
			{
				this.rdo_NoBagWeightDivisi.setChecked(true);
			}
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
				catch (SQLException sqlex)
				{
					DebugPrinter.print(DebugLevel.ERROR, sqlex.getMessage());
					message.setMsgResourceKey("7200002");
				}
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Delete_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		ValidateCheck1();
		setDeleteMode();
		ClearControls();
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			List itemMasterList = centre.getItemMasterList(txt_ItemCode
					.getText(), rdo_ItemInManage.getChecked());

			if (itemMasterList.size() <= 0)
			{
				message.setMsgResourceKey("7000005");
				return;
			}

			int count = centre.getItemInstockCount(txt_ItemCode.getText(),rdo_ItemInManage.getChecked());
			
			if(count > 0)
			{
				message.setMsgResourceKey("7000045");
				return;
			}
			
			ItemViewEntity entity = (ItemViewEntity) itemMasterList.get(0);
			ClearControls();

			txt_ItemCode_RO.setText(entity.getItemCode());
			txt_ItemName1.setText(entity.getItemName1());
			txt_ItemName2.setText(entity.getItemName2());
			txt_ItemName3.setText(entity.getItemName3());
			txt_MasterUnit.setText(entity.getMasterUnit().toString());
			txt_MasterFormerUnitCount.setInt(entity.getUnitQty());
			txt_FullCount.setInt(entity.getLimitQty());
			txt_AutoDepoMaxCount.setInt(entity.getAutoDepoMaxCount());
			txt_AutoDepoMinCount.setInt(entity.getAutoDepoMinCount());
			if (entity.getUnitFlag().equals(DBFlags.UnitFlag.WITH_ACCOUNT))
			{
				rdo_WithAccount.setChecked(true);
			}
			else
			{
				rdo_WithOutAccount.setChecked(true);
			}
			if (entity.getRemoveConventFlag().equals(
					DBFlags.RemoveConventFlag.AUTO_DEPO))
			{
				rdo_AutoDepoPickOut.setChecked(true);
			}
			else
			{
				rdo_FlatDepoPickOut.setChecked(true);
			}
			if (entity.getBagFlag().equals(DBFlags.BagFlag.YES))
			{
				rdo_HasBagWeightDivisi.setChecked(true);
			}
			else
			{
				this.rdo_NoBagWeightDivisi.setChecked(true);
			}
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
				catch (SQLException sqlex)
				{
					DebugPrinter.print(DebugLevel.ERROR, sqlex.getMessage());
					message.setMsgResourceKey("7200002");
				}
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemCode_RO_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_RO_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_RO_AutoCompleteItemClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_RO_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_RO_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_RO_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_RO_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName1_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName1_AutoCompleteItemClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName1_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName1_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName1_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName1_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName2_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName2_AutoCompleteItemClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName2_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName2_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName2_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName2_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName3_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName3_AutoCompleteItemClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName3_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName3_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName3_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName3_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_MasterUnit_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_MasterUnit_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_MasterUnit_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_MasterUnit_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_FullCount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_FullCount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_FullCount_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_FullCount_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_AutoDepoMaxCount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_AutoDepoMaxCount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_AutoDepoMaxCount_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_AutoDepoMaxCount_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_MasterFormerUnitCount_Server(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_MasterFormerUnitCount_Server(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_MasterFormerUnitCount_EnterKey(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_MasterFormerUnitCount_TabKey(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_AutoDepoMinCount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_AutoDepoMinCount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_AutoDepoMinCount_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_AutoDepoMinCount_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_AccountDivision_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_WithAccount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_WithAccount_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_WithOutAccount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_WithOutAccount_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_PickOutDivision_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_AutoDepoPickOut_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_AutoDepoPickOut_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_FlatDepoPickOut_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_FlatDepoPickOut_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Set_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Set_Click(ActionEvent e) throws Exception
	{
		ValidateCheck2();
		setConfirm("YKK-LBL-SetConfirm");
		getViewState().setBoolean(DIALOG_FLAG, true);
	}

	private void ValidateCheck2() throws ValidateException
	{
		String manageItemFlag = (String) session.getAttribute(MANAGE_ITEM_FLAG);

		setFocus(txt_ItemCode_RO);
		txt_ItemCode_RO.validate();

		if (manageItemFlag.equals(DBFlags.ManageItemFlag.INMANAGE))
		{
			setFocus(txt_MasterFormerUnitCount);
			txt_MasterFormerUnitCount.validate();
			setFocus(txt_AutoDepoMaxCount);
			txt_AutoDepoMaxCount.validate();
			setFocus(txt_AutoDepoMinCount);
			txt_AutoDepoMaxCount.validate();
		}
		// else
		// {
		// setFocus(txt_ItemName1);
		// txt_ItemName1.validate();
		// setFocus(txt_ItemName2);
		// txt_ItemName2.validate();
		// setFocus(txt_ItemName3);
		// txt_ItemName3.validate();
		// }
		setFocus(null);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		ClearControls();
	}

	private void ClearControls()
	{
		txt_ItemCode_RO.setText("");
		txt_ItemName1.setText("");
		txt_ItemName2.setText("");
		txt_ItemName3.setText("");
		txt_MasterUnit.setText("");
		txt_FullCount.setText("");
		txt_AutoDepoMaxCount.setText("");
		txt_AutoDepoMinCount.setText("");
		txt_MasterFormerUnitCount.setText("");
		rdo_WithAccount.setChecked(true);
		rdo_AutoDepoPickOut.setChecked(true);
		rdo_HasBagWeightDivisi.setChecked(true);

	}

	public void page_DlgBack(ActionEvent e) throws Exception
	{
		((DialogEvent) e).getDialogParameters();

		if (session.getAttribute(ITEM_CODE) != null)
		{
			txt_ItemCode.setText((String) session.getAttribute(ITEM_CODE));

			session.setAttribute(ITEM_CODE, null);
		}
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

			String maintenanceMode = (String) session
					.getAttribute(MAINTENANCE_MODE);

			if (maintenanceMode.equals("ADD"))
			{
				centre.addItem(getItemViewEntity());
			}
			else if (maintenanceMode.equals("MODIFY"))
			{
				centre.modiItem(getItemViewEntity());
			}
			else if (maintenanceMode.equals("DELETE"))
			{
				int count = centre.getItemInstockCount(txt_ItemCode.getText(),rdo_ItemInManage.getChecked());
				
				if(count > 0)
				{
					message.setMsgResourceKey("7000045");
					return;
				}
				
				centre.deleteItem(getItemViewEntity());
			}
			message.setMsgResourceKey("7400002");
			conn.commit();
			ClearControls();
		}
		catch (YKKDBException dbEx)
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
			}
			catch (SQLException ex)
			{
				DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
				message.setMsgResourceKey("7200002");
			}
		}
		catch (YKKSQLException sqlEx)
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
			}
			catch (SQLException ex)
			{
				DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
				message.setMsgResourceKey("7200002");
			}
		}
		finally
		{
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (SQLException sqlex)
				{
					DebugPrinter.print(DebugLevel.ERROR, sqlex.getMessage());
					message.setMsgResourceKey("7200002");
				}
			}
		}
	}

	private ItemViewEntity getItemViewEntity()
	{
		ItemViewEntity entity = new ItemViewEntity();
		entity.setManageItemFlag((String) session
				.getAttribute(MANAGE_ITEM_FLAG));
		entity.setItemCode(txt_ItemCode_RO.getText());
		if (!StringUtils.IsNullOrEmpty(txt_ItemName1.getText()))
		{
			entity.setItemName1(txt_ItemName1.getText());
		}
		else
		{
			entity.setItemName1(" ");
		}
		if (!StringUtils.IsNullOrEmpty(txt_ItemName2.getText()))
		{
			entity.setItemName2(txt_ItemName2.getText());
		}
		else
		{
			entity.setItemName2(" ");
		}
		if (!StringUtils.IsNullOrEmpty(txt_ItemName3.getText()))
		{
			entity.setItemName3(txt_ItemName3.getText());
		}
		else
		{
			entity.setItemName3(" ");
		}
		try
		{
			entity.setMasterUnit(new BigDecimal(txt_MasterUnit.getText()));
		}
		catch (Exception ex)
		{
			entity.setMasterUnit(new BigDecimal(0));
		}
		entity.setUnitQty(txt_MasterFormerUnitCount.getInt());
		entity.setLimitQty(txt_FullCount.getInt());
		entity.setAutoDepoMaxCount(txt_AutoDepoMaxCount.getInt());
		entity.setAutoDepoMinCount(txt_AutoDepoMinCount.getInt());
		if (rdo_WithAccount.getChecked())
		{
			entity.setUnitFlag(DBFlags.UnitFlag.WITH_ACCOUNT);
		}

		else
		{
			entity.setUnitFlag(DBFlags.UnitFlag.WITHOUT_ACCOUNT);
		}
		if (rdo_AutoDepoPickOut.getChecked())
		{
			entity.setRemoveConventFlag(DBFlags.RemoveConventFlag.AUTO_DEPO);
		}
		else
		{
			entity.setRemoveConventFlag(DBFlags.RemoveConventFlag.FLAT_DEPO);
		}
		if (this.rdo_HasBagWeightDivisi.getChecked())
		{
			entity.setBagFlag(DBFlags.BagFlag.YES);
		}
		else
		{
			entity.setBagFlag(DBFlags.BagFlag.NO);
		}
		return entity;
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_Star_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_BagWeightDivisi_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_HasBagWeightDivisi_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_HasBagWeightDivisi_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_NoBagWeightDivisi_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_NoBagWeightDivisi_Click(ActionEvent e) throws Exception
	{
	}

}
// end of class