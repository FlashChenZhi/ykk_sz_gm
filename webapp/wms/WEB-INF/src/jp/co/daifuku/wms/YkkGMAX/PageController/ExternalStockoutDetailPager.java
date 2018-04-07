package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutDetailEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.ExternalStockoutPopupListProxy;
import jp.co.daifuku.wms.YkkGMAX.Stockout.ExternalStockoutPopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class ExternalStockoutDetailPager implements IPageable
{
	private Page page = null;
	private Pager pager = null;
	private final String DESIGNATE_LOCATION_ENTITY = "DESIGNATE_LOCATION_ENTITY";

	public ExternalStockoutDetailPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List externalStockoutDetailEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			externalStockoutDetailEntityList = centre
					.getExternalStockoutDetailList(getExternalStockoutEntity()
							.getItemCode(), getExternalStockoutEntity()
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
		return externalStockoutDetailEntityList;
	}

	public ListCell getListCell()
	{
		return ((ExternalStockoutPopupBusiness) page).lst_ExternalStockoutPopup;
	}

	public Pager getPager()
	{
		return pager;
	}

	public ExternalStockoutEntity getExternalStockoutEntity()
	{
		return (ExternalStockoutEntity) page.getSession().getAttribute(
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
			totalCount = centre.getExternalStockoutDetailCount(
					getExternalStockoutEntity().getItemCode(),
					getExternalStockoutEntity().getColor());
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
		ExternalStockoutPopupListProxy listProxy = new ExternalStockoutPopupListProxy(
				listCell, page);

		ExternalStockoutDetailEntity entity = (ExternalStockoutDetailEntity) object;

		listProxy.setRowValueByEntity(entity);
	}

}
