package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationViewEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.LocationViewListProxy;
import jp.co.daifuku.wms.YkkGMAX.Popup.LocationViewBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class EmptyLocationViewPager implements IPageable
{
	private final String LOCATION_STATUS = "LOCATION_STATUS";
	private final String MANAGE_ITEM_FLAG = "MANAGE_ITEM_FLAG";

	private Page page = null;
	private Pager pager = null;

	public EmptyLocationViewPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List locationViewEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			locationViewEntityList = centre.getEmptyLocationViewList(
					getLocationStatus(), getManageItemFlag(), beginningPos,
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
		return locationViewEntityList;
	}

	public List getLocationStatus()
	{
		return (List) page.getSession().getAttribute(LOCATION_STATUS);
	}

	public String getManageItemFlag()
	{
		return (String) page.getSession().getAttribute(MANAGE_ITEM_FLAG);
	}

	public ListCell getListCell()
	{
		return ((LocationViewBusiness) page).lst_EmptyLocationView;
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
			totalCount = centre.getEmptyLocationViewCount(getLocationStatus(),
					getManageItemFlag());
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
		LocationViewListProxy listProxy = new LocationViewListProxy(listCell,
				null);

		LocationViewEntity entity = (LocationViewEntity) object;

		listProxy.setEmptyRowValue(entity, rowNum);

	}
}
