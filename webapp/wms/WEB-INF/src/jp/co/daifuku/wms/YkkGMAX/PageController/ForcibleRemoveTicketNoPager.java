package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.ForcibleRemoveTicketNoEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.ForcibleRemoveTicketNoUpListProxy;
import jp.co.daifuku.wms.YkkGMAX.Stockout.ForcibleRemoveTicketNoBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class ForcibleRemoveTicketNoPager implements IPageable
{
	private Page page = null;
	private Pager pager = null;
	private final String ITEM_CODE = "ITEM_CODE";
	private final String COLOR_CODE = "COLOR_CODE";

	public ForcibleRemoveTicketNoPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List forcibleRemoveTicketNoUpEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			forcibleRemoveTicketNoUpEntityList = centre
					.getForcibleRemoveTicketNoUpList(getItemCode(),
							getColorCode(), beginningPos, length);
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
		return forcibleRemoveTicketNoUpEntityList;
	}

	private String getItemCode()
	{
		return (String) page.getSession().getAttribute(ITEM_CODE);
	}

	private String getColorCode()
	{
		return (String) page.getSession().getAttribute(COLOR_CODE);
	}

	public ListCell getListCell()
	{
		return ((ForcibleRemoveTicketNoBusiness) page).lst_ForcibleRemoveTicketNo_Up;
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
			totalCount = centre.getForcibleRemoveTicketNoUpCount(getItemCode(),
					getColorCode());
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
		ForcibleRemoveTicketNoUpListProxy listProxy = new ForcibleRemoveTicketNoUpListProxy(
				listCell, page);

		ForcibleRemoveTicketNoEntity entity = (ForcibleRemoveTicketNoEntity) object;

		listProxy.setRowValueByEntity(entity);

	}

}
