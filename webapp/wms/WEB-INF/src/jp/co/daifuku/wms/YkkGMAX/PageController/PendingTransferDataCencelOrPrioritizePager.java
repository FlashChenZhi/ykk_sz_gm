package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.PendingTransferDataCencelOrPrioritizeEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.PendingTransferDataCencelOrPrioritizeListProxy;
import jp.co.daifuku.wms.YkkGMAX.Maintenance.PendingTransferDataCencelOrPrioritizeBusiness;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class PendingTransferDataCencelOrPrioritizePager implements IPageable
{

	private final String LINE = "LINE";
	private final String RETRIEVAL_NO = "RETRIEVAL_NO";
	private final String SECTION = "SECTION";
	private final String ORDER_MODE = "ORDER_MODE";

	private Page page = null;
	private Pager pager = null;

	public PendingTransferDataCencelOrPrioritizePager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	private String getLine()
	{
		return (String) page.getSession().getAttribute(LINE);
	}

	private String getRetrievalNo()
	{
		return (String) page.getSession().getAttribute(RETRIEVAL_NO);
	}

	private String getSection()
	{
		return (String) page.getSession().getAttribute(SECTION);
	}

	private String getOrderMode()
	{
		return (String) page.getSession().getAttribute(ORDER_MODE);
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List pendingTransferDataCencelOrPrioritizeEntityList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			pendingTransferDataCencelOrPrioritizeEntityList = centre
					.getPendingTransferDataCencelOrPrioritizeEntityList(
							getSection(), getLine(), getRetrievalNo(),
							getOrderMode(), beginningPos, length);

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
		return pendingTransferDataCencelOrPrioritizeEntityList;
	}

	public ListCell getListCell()
	{
		return ((PendingTransferDataCencelOrPrioritizeBusiness) page).lst_PendingTransferDataCencelO;
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
					.getPendingTransferDataCencelOrPrioritizeEntityCount(
							getSection(), getLine(), getRetrievalNo());
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
		PendingTransferDataCencelOrPrioritizeListProxy listProxy = new PendingTransferDataCencelOrPrioritizeListProxy(
				listCell, page);

		PendingTransferDataCencelOrPrioritizeEntity entity = (PendingTransferDataCencelOrPrioritizeEntity) object;

		listProxy.setRowValueByEntity(entity);

	}

}
