package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutDetailEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.StockoutPopupListProxy;
import jp.co.daifuku.wms.YkkGMAX.Stockout.StockoutPopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class StockoutDetailPager implements IPageable
{
	private Page page = null;
	private Pager pager = null;
	private final String DESIGNATE_LOCATION_ENTITY = "DESIGNATE_LOCATION_ENTITY";

	public StockoutDetailPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List stockoutDetailEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			stockoutDetailEntityList = centre.getStockoutDetailList(
					getStockoutEntity().getItemCode(), getStockoutEntity()
							.getColor(), beginningPos, length);

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
		return stockoutDetailEntityList;
	}

	public ListCell getListCell()
	{
		return ((StockoutPopupBusiness) page).lst_StockoutPopup;
	}

	public Pager getPager()
	{
		return pager;
	}

	public StockoutEntity getStockoutEntity()
	{
		return (StockoutEntity) page.getSession().getAttribute(
				DESIGNATE_LOCATION_ENTITY);
	}

	public int getTotalCount() throws YKKDBException, YKKSQLException
	{
		Connection conn = null;
		int totalCount = 0;

		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			totalCount = centre.getStockoutDetailCount(getStockoutEntity()
					.getItemCode(), getStockoutEntity().getColor());
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
		StockoutPopupListProxy listProxy = new StockoutPopupListProxy(listCell,
				page);

		StockoutDetailEntity entity = (StockoutDetailEntity) object;

		listProxy.setRowValueByEntity(entity);
	}

}
