package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.ForbidItemViewEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.ForbidItemViewListProxy;
import jp.co.daifuku.wms.YkkGMAX.Popup.ForbidItemViewBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class ForbidItemViewPager implements IPageable
{
	private final String ITEM_CODE = "ITEM_CODE";
	private final String LOOSE_SEARCH_MODE = "LOOSE_SEARCH_MODE";
	private Page page = null;
	private Pager pager = null;

	public ForbidItemViewPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List itemViewEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			itemViewEntityList = centre.getForbidItemViewList(getItemCode(),
					getLooseSearchMode(), beginningPos, length);

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
		return itemViewEntityList;
	}

	public String getItemCode()
	{
		return (String) page.getSession().getAttribute(ITEM_CODE);
	}

	public String getLooseSearchMode()
	{
		return (String) page.getSession().getAttribute(LOOSE_SEARCH_MODE);
	}

	public ListCell getListCell()
	{
		return ((ForbidItemViewBusiness) page).lst_ForbidItemView;
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
			totalCount = centre.getForbidItemViewCount(getItemCode(),
					getLooseSearchMode());
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
		ForbidItemViewListProxy listProxy = new ForbidItemViewListProxy(
				listCell);

		ForbidItemViewEntity entity = (ForbidItemViewEntity) object;

		listProxy.setRowValueByEntity(entity, rowNum);

	}
}
