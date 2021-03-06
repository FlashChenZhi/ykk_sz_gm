package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutStartEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.ExternalStockoutStartListProxy;
import jp.co.daifuku.wms.YkkGMAX.Stockout.ExternalStockoutStartBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class ExternalStockoutStartPager implements IPageable
{
	private Page page;

	private Pager pager;

	private final String SECTION = "SECTION";

	private final String ORDER_MODE = "ORDER_MODE";

	private final String RETRIEVAL_NO = "RETRIEVAL_NO";

	private final String ORDER_NO = "ORDER_NO";

	public ExternalStockoutStartPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List externalStockoutStartEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			externalStockoutStartEntityList = centre
					.getExternalStockoutStartList(getOrderMode(), getSection(),
							getRetrievalNo(), getOrderNo(), beginningPos,
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
		return externalStockoutStartEntityList;
	}

	public ListCell getListCell()
	{
		return ((ExternalStockoutStartBusiness) page).lst_ExternalStockoutStart;
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
			totalCount = centre.getExternalStockoutStartCount(getSection(),
					getRetrievalNo(), getOrderNo());
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

	private String getSection()
	{
		return (String) page.getSession().getAttribute(SECTION);
	}

	private String getOrderMode()
	{
		return (String) page.getSession().getAttribute(ORDER_MODE);
	}

	private String getOrderNo()
	{
		return (String) page.getSession().getAttribute(ORDER_NO);
	}

	private String getRetrievalNo()
	{
		return (String) page.getSession().getAttribute(RETRIEVAL_NO);
	}

	public void setRowValue(ListCell listCell, int rowNum, Object object)
	{
		ExternalStockoutStartListProxy listProxy = new ExternalStockoutStartListProxy(
				listCell, page);

		ExternalStockoutStartEntity entity = (ExternalStockoutStartEntity) object;

		listProxy.setRowValueByEntity(entity);

	}

}
