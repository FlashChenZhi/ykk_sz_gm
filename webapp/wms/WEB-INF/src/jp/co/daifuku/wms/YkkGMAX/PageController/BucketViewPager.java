package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.BucketViewEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.BucketViewListProxy;
import jp.co.daifuku.wms.YkkGMAX.Popup.BucketViewBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class BucketViewPager implements IPageable
{
	private final String BUCKET_NO = "BUCKET_NO";
	private final String LOOSE_SEARCH_MODE = "LOOSE_SEARCH_MODE";
	private Page page = null;
	private Pager pager = null;

	public BucketViewPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public String getBucketNo()
	{
		return (String) page.getSession().getAttribute(BUCKET_NO);
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List bucketViewEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			bucketViewEntityList = centre.getBucketViewList(getBucketNo(),
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
		return bucketViewEntityList;
	}

	public ListCell getListCell()
	{
		return ((BucketViewBusiness) page).lst_BucketView;
	}

	public String getLooseSearchMode()
	{
		return (String) page.getSession().getAttribute(LOOSE_SEARCH_MODE);
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
			totalCount = centre.getBucketViewCount(getBucketNo(),
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
		BucketViewListProxy listProxy = new BucketViewListProxy(listCell);

		BucketViewEntity entity = (BucketViewEntity) object;

		listProxy.setRowValueByEntity(entity, rowNum);

	}

}
