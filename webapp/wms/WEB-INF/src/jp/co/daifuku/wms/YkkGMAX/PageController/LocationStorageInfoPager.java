package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Inquiry.LocationStorageInfoPopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.LocationStorageInfoListProxy;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class LocationStorageInfoPager implements IPageable
{
	private Page page = null;
	private Pager pager = null;
	private final String LOCATION_STORAGE_INFO_HEAD = "LOCATION_STORAGE_INFO_HEAD";

	public LocationStorageInfoPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List locationStorageInfoList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			locationStorageInfoList = centre.getLocationStorageInfoList(
					getLocationStorageInfoHead(), beginningPos, length);
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
		return locationStorageInfoList;
	}

	public LocationStorageInfoHead getLocationStorageInfoHead()
	{
		return (LocationStorageInfoHead) page.getSession().getAttribute(
				LOCATION_STORAGE_INFO_HEAD);
	}

	public ListCell getListCell()
	{
		return ((LocationStorageInfoPopupBusiness) page).lst_LocationStorageInfoPopup;
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
					.getLocationStorageInfoCount(getLocationStorageInfoHead());
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
		LocationStorageInfoListProxy listProxy = new LocationStorageInfoListProxy(
				listCell);

		LocationStorageInfoEntity entity = (LocationStorageInfoEntity) object;

		listProxy.setRowValueByEntity(entity, rowNum);
	}

}
