package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.UnmanagedStockoutDetailEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.UnmanagedStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.UnmanagedStockoutPopupListProxy;
import jp.co.daifuku.wms.YkkGMAX.Stockout.UnmanagedStockoutPopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class UnmanagedStockoutDetailPager implements IPageable
{
	private Page page = null;
	private Pager pager = null;
	private final String UNMANAGED_STOCKOUT_ENTITY = "UNMANAGED_STOCKOUT_ENTITY";

	public UnmanagedStockoutDetailPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List unmanagedStockoutDetailEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			unmanagedStockoutDetailEntityList = centre
					.getUnmanagedStockoutDetailList(
							getUnmanagedStockoutEntity(), beginningPos, length);
			;

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
		return unmanagedStockoutDetailEntityList;
	}

	public UnmanagedStockoutEntity getUnmanagedStockoutEntity()
	{
		return (UnmanagedStockoutEntity) page.getSession().getAttribute(
				UNMANAGED_STOCKOUT_ENTITY);
	}

	public ListCell getListCell()
	{
		return ((UnmanagedStockoutPopupBusiness) page).lst_UnmanagedStockoutPopup;
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
					.getUnmanagedStockoutDetailCount(getUnmanagedStockoutEntity());
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
		UnmanagedStockoutPopupListProxy listProxy = new UnmanagedStockoutPopupListProxy(
				listCell, page);

		UnmanagedStockoutDetailEntity entity = (UnmanagedStockoutDetailEntity) object;

		listProxy.setRowValueByEntity(entity);

	}

}
