package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutStartEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.StockoutStartListProxy;
import jp.co.daifuku.wms.YkkGMAX.Stockout.StockoutStartBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class StockoutStartPager implements IPageable
{
	private final String RETRIEVAL_NO = "RETRIEVAL_NO";
	private final String LINE_DIVISION = "LINE_DIVISION";
	private final String STOCKOUT_STATION_1 = "STOCKOUT_STATION_1";
	private final String LINE = "LINE";
	private final String STOCKOUT_STATION_2 = "STOCKOUT_STATION_2";
	private final String SEARCH_MODE = "SEARCH_MODE";
	private final String SECTION = "SECTION";
	private final String ORDER_MODE = "ORDER_MODE";

	private Page page = null;
	private Pager pager = null;

	public StockoutStartPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List stockoutStartEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			stockoutStartEntityList = centre.getStockoutStartList(
					getSearchMode(), getOrderMode(), getSection(), getLine(),
					getLineDivision(), getStockoutStation1(),
					getStockoutStation2(), getRetrievalNo(), beginningPos,
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
		return stockoutStartEntityList;
	}

	public ListCell getListCell()
	{
		return ((StockoutStartBusiness) page).lst_StockoutStart;
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
			totalCount = centre.getStockoutStartCount(getSearchMode(),
					getSection(), getLine(), getLineDivision(),
					getStockoutStation1(), getStockoutStation2(),
					getRetrievalNo());
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

	private String getLineDivision()
	{
		return (String) page.getSession().getAttribute(LINE_DIVISION);
	}

	private String getStockoutStation1()
	{
		return (String) page.getSession().getAttribute(STOCKOUT_STATION_1);
	}

	private String getLine()
	{
		return (String) page.getSession().getAttribute(LINE);
	}

	private String getStockoutStation2()
	{
		return (String) page.getSession().getAttribute(STOCKOUT_STATION_2);
	}

	private String getSearchMode()
	{
		return (String) page.getSession().getAttribute(SEARCH_MODE);
	}

	private String getSection()
	{
		return (String) page.getSession().getAttribute(SECTION);
	}

	private String getOrderMode()
	{
		return (String) page.getSession().getAttribute(ORDER_MODE);
	}

	private String getRetrievalNo()
	{
		return (String) page.getSession().getAttribute(RETRIEVAL_NO);
	}

	public void setRowValue(ListCell listCell, int rowNum, Object object)
	{
		StockoutStartListProxy listProxy = new StockoutStartListProxy(listCell,
				page);

		StockoutStartEntity entity = (StockoutStartEntity) object;

		listProxy.setRowValueByEntity(entity);
	}

}
