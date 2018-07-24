package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalQtyMaintenanceEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.RetrievalQtyMaintenanceListProxy;
import jp.co.daifuku.wms.YkkGMAX.Stockout.RetrievalQtyMaintenanceBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class RetrievalQtyMaintenancePager implements IPageable
{
	private Page page;

	private Pager pager;

	private final String RETRIEVAL_QTY_MAINTENANCE_ENTITY = "RETRIEVAL_QTY_MAINTENANCE_ENTITY";

	public RetrievalQtyMaintenancePager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List retrievalQtyMaintenanceEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			retrievalQtyMaintenanceEntityList = centre
					.getRetrievalQtyMaintenanceList(
							getRetrievalQtyMaintenanceEntity(), beginningPos,
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
		return retrievalQtyMaintenanceEntityList;
	}

	public ListCell getListCell()
	{
		return ((RetrievalQtyMaintenanceBusiness) page).lst_RetrievalQtyMaintenance;
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
					.getRetrievalQtyMaintenanceCount(getRetrievalQtyMaintenanceEntity());
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

	private RetrievalQtyMaintenanceEntity getRetrievalQtyMaintenanceEntity()
	{
		return (RetrievalQtyMaintenanceEntity) page.getSession().getAttribute(
				RETRIEVAL_QTY_MAINTENANCE_ENTITY);
	}

	public void setRowValue(ListCell listCell, int rowNum, Object object)
	{
		RetrievalQtyMaintenanceListProxy listProxy = new RetrievalQtyMaintenanceListProxy(
				listCell, page);

		RetrievalQtyMaintenanceEntity entity = (RetrievalQtyMaintenanceEntity) object;

		listProxy.setRowValue(entity);

	}

}
