package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExceptionStockoutDetailEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExceptionStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.ExceptionStockoutPopupListProxy;
import jp.co.daifuku.wms.YkkGMAX.Stockout.ExceptionStockoutPopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class ExceptionStockoutDetailPager implements IPageable
{
	private Page page = null;
	private Pager pager = null;
	private final String EXCEPTION_STOCKOUT_ENTITY = "EXCEPTION_STOCKOUT_ENTITY";

	public ExceptionStockoutDetailPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List exceptionStockoutDetailEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			exceptionStockoutDetailEntityList = centre
					.getExceptionStockoutDetailList(
							getExceptionStockoutEntity(), beginningPos, length);
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
		return exceptionStockoutDetailEntityList;
	}

	public ListCell getListCell()
	{
		return ((ExceptionStockoutPopupBusiness) page).lst_ExceptionStockoutPopup;
	}

	public Pager getPager()
	{
		return pager;
	}

	public ExceptionStockoutEntity getExceptionStockoutEntity()
	{
		return (ExceptionStockoutEntity) page.getSession().getAttribute(
				EXCEPTION_STOCKOUT_ENTITY);
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
					.getExceptionStockoutDetailCount(getExceptionStockoutEntity());
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
		ExceptionStockoutPopupListProxy listProxy = new ExceptionStockoutPopupListProxy(
				listCell, page);

		ExceptionStockoutDetailEntity entity = (ExceptionStockoutDetailEntity) object;

		listProxy.setRowValueByEntity(entity);

	}

}
