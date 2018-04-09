package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenanceEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenancePopupEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.WorkMaintenanceListProxy;
import jp.co.daifuku.wms.YkkGMAX.Maintenance.WorkMaintenanceBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class WorkMaintenancePager implements IPageable
{
	private final String WORK_MAINTENANCE_ENTITY = "WORK_MAINTENANCE_ENTITY";
	private Page page = null;
	private Pager pager = null;

	public WorkMaintenancePager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List workMaintenanceEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			workMaintenanceEntityList = centre.getWorkMaintenanceList(
					getWorkMaintenancePopupEntity().getMckey(), beginningPos,
					length);

		} finally
		{
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
		return workMaintenanceEntityList;
	}

	public WorkMaintenancePopupEntity getWorkMaintenancePopupEntity()
	{
		return (WorkMaintenancePopupEntity) page.getSession().getAttribute(
				WORK_MAINTENANCE_ENTITY);
	}

	public ListCell getListCell()
	{
		return ((WorkMaintenanceBusiness) page).lst_WorkMaintenance;
	}

	public Pager getPager()
	{
		return pager;
	}

	public int getTotalCount() throws YKKDBException, YKKSQLException
	{
		Connection conn = null;
		int totalCount = 0;

		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			totalCount = centre
					.getWorkMaintenanceCount(getWorkMaintenancePopupEntity()
							.getMckey());
		} finally
		{
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
		return totalCount;
	}

	public void setRowValue(ListCell listCell, int rowNum, Object object)
	{
		WorkMaintenanceListProxy listProxy = new WorkMaintenanceListProxy(
				listCell);

		WorkMaintenanceEntity entity = (WorkMaintenanceEntity) object;

		listProxy.setRowValueByEntity(entity, rowNum);

	}

}
