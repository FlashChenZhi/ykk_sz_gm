package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.TicketNoItemStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.TicketNoItemStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Inquiry.TicketNoItemStorageInfoPopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.TicketNoItemStorageInfoListProxy;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class TicketNoItemStorageInfoPager implements IPageable
{
	private Page page = null;
	private Pager pager = null;
	private final String TICKET_NO_ITEM_STORAGE_INFO_HEAD = "TICKET_NO_ITEM_STORAGE_INFO_HEAD";

	public TicketNoItemStorageInfoPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List ticketNoItemStorageInfoList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			ticketNoItemStorageInfoList = centre
					.getTicketNoItemStorageInfoList(
							getTicketNoItemStorageInfoHead(), beginningPos,
							length);
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
		return ticketNoItemStorageInfoList;
	}

	public TicketNoItemStorageInfoHead getTicketNoItemStorageInfoHead()
	{
		return (TicketNoItemStorageInfoHead) page.getSession().getAttribute(
				TICKET_NO_ITEM_STORAGE_INFO_HEAD);
	}

	public ListCell getListCell()
	{
		return ((TicketNoItemStorageInfoPopupBusiness) page).lst_TicketNoItemStorageInfoPop;
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
					.getTicketNoItemStorageInfoCount(getTicketNoItemStorageInfoHead());
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
		TicketNoItemStorageInfoListProxy listProxy = new TicketNoItemStorageInfoListProxy(
				listCell);

		TicketNoItemStorageInfoEntity entity = (TicketNoItemStorageInfoEntity) object;

		listProxy.setRowValueByEntity(entity, rowNum);
	}

}
