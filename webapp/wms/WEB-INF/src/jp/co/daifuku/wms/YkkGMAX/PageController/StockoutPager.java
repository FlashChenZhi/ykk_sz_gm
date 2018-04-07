package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.StockoutListProxy;
import jp.co.daifuku.wms.YkkGMAX.Stockout.StockoutBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class StockoutPager implements IPageable
{

	private final String LINE_DIVISION = "LINE_DIVISION";
	private final String STOCKOUT_STATION_1 = "STOCKOUT_STATION_1";
	private final String LINE = "LINE";
	private final String STOCKOUT_STATION_2 = "STOCKOUT_STATION_2";
	private final String SEARCH_MODE = "SEARCH_MODE";
	private final String SECTION = "SECTION";
	private final String ORDER_MODE = "ORDER_MODE";
	private final String DISPLAY_FINISHED_RETRIEVAL = "DISPLAY_FINISHED_RETRIEVAL";
    private final String SHOW_SHORTAGE_CONDITION = "SHOW_SHORTAGE_CONDITION";
    private final String START_DATE = "START_DATE";
    private final String START_DATE_FLAG = "START_DATE_FLAG";

	private Page page = null;
	private Pager pager = null;

	public StockoutPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	private String getLine()
	{
		return (String) page.getSession().getAttribute(LINE);
	}

	private String getLineDivision()
	{
		return (String) page.getSession().getAttribute(LINE_DIVISION);
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List stockoutEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			stockoutEntityList = centre.getStockoutList(getSearchMode(),
					getOrderMode(), getSection(), getLine(), getLineDivision(),
					getStockoutStation1(), getStockoutStation2(),
					getDisplayFinishedRetrieval(),getShowShortageCondition(),getStartDate(),getStartDateFlag(), beginningPos, length);

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
		return stockoutEntityList;
	}

	public ListCell getListCell()
	{
		return ((StockoutBusiness) page).lst_Stockout;
	}

	private String getOrderMode()
	{
		return (String) page.getSession().getAttribute(ORDER_MODE);
	}

	public Pager getPager()
	{
		return pager;
	}

	private String getSearchMode()
	{
		return (String) page.getSession().getAttribute(SEARCH_MODE);
	}

	private List getSection()
	{
		return (List) page.getSession().getAttribute(SECTION);
	}

	private String getStockoutStation1()
	{
		return (String) page.getSession().getAttribute(STOCKOUT_STATION_1);
	}

	private String getStockoutStation2()
	{
		return (String) page.getSession().getAttribute(STOCKOUT_STATION_2);
	}

	private String getDisplayFinishedRetrieval()
	{
		return (String) page.getSession().getAttribute(
				DISPLAY_FINISHED_RETRIEVAL);
	}

    private String getShowShortageCondition()
    {
        return (String) page.getSession().getAttribute(
                SHOW_SHORTAGE_CONDITION);
    }

    private String getStartDate()
    {
        return (String) page.getSession().getAttribute(START_DATE);
    }

    private String getStartDateFlag()
    {
        return (String) page.getSession().getAttribute(START_DATE_FLAG);
    }

	public int getTotalCount() throws YKKDBException, YKKSQLException
	{
		Connection conn = null;
		int totalCount = 0;

		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			totalCount = centre.getStockoutCount(getSearchMode(), getSection(),
					getLine(), getLineDivision(), getStockoutStation1(),
					getStockoutStation2(), getDisplayFinishedRetrieval(),getShowShortageCondition(),getStartDate(),getStartDateFlag());
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
		StockoutListProxy listProxy = new StockoutListProxy(listCell, page);

		StockoutEntity entity = (StockoutEntity) object;

		listProxy.setRowValueByEntity(entity);
	}

}
