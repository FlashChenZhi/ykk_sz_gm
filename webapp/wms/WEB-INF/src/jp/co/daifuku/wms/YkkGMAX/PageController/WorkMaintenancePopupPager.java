package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenancePopupEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.WorkMaintenancePopupListProxy;
import jp.co.daifuku.wms.YkkGMAX.Maintenance.WorkMaintenancePopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class WorkMaintenancePopupPager implements IPageable
{
	private final String TRANSFER_TYPE = "TRANSFER_TYPE";
	private final String STATION = "STATION";
	private final String DIVISION = "DIVISION";
	private Page page = null;
	private Pager pager = null;

	public WorkMaintenancePopupPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List workMaintenancePopupEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			workMaintenancePopupEntityList = centre
					.getWorkMaintenancePopupList(getTransferType(),
							getStation(), getDivision(), beginningPos, length);

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
		return workMaintenancePopupEntityList;
	}

	public String getTransferType()
	{
		return (String) page.getSession().getAttribute(TRANSFER_TYPE);
	}

	public String getStation()
	{
		return (String) page.getSession().getAttribute(STATION);
	}

	public String getDivision()
	{
		return (String) page.getSession().getAttribute(DIVISION);
	}

	public ListCell getListCell()
	{
		return ((WorkMaintenancePopupBusiness) page).lst_WorkMaintenancePopup;
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
			totalCount = centre.getWorkMaintenancePopupCount(getTransferType(),
					getStation(), getDivision());
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
		WorkMaintenancePopupListProxy listProxy = new WorkMaintenancePopupListProxy(
				listCell);

		WorkMaintenancePopupEntity entity = (WorkMaintenancePopupEntity) object;

		listProxy.setRowValueByEntity(entity, rowNum);

	}

}
