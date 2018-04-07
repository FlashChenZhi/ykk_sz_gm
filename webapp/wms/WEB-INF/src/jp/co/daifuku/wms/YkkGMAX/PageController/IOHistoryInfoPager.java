package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistoryInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistoryInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Inquiry.IOHistoryInfoPopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.IOHistoryInfoListProxy;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class IOHistoryInfoPager implements IPageable
{
	private Page page = null;
	private Pager pager = null;
	private final String IO_HISTORY_INFO_HEAD = "IO_HISTORY_INFO_HEAD";

	public IOHistoryInfoPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List ioHistoryInfoList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			ioHistoryInfoList = centre.getIOHistoryInfoList(
					getIOHistoryInfoHead(), beginningPos, length);
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
		return ioHistoryInfoList;
	}

	public IOHistoryInfoHead getIOHistoryInfoHead()
	{
		return (IOHistoryInfoHead) page.getSession().getAttribute(
				IO_HISTORY_INFO_HEAD);
	}

	public ListCell getListCell()
	{
		return ((IOHistoryInfoPopupBusiness) page).lst_IOHistoryInfoPopup;
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
			totalCount = centre.getIOHistoryInfoCount(getIOHistoryInfoHead());
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
		IOHistoryInfoListProxy listProxy = new IOHistoryInfoListProxy(listCell);

		IOHistoryInfoEntity entity = (IOHistoryInfoEntity) object;

		listProxy.setRowValueByEntity(entity, rowNum);

	}

}
