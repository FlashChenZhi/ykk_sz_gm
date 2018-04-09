package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.OvertimeStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.OvertimeStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Inquiry.OvertimeStorageInfoPopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.OvertimeStorageInfoListProxy;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class OvertimeStorageInfoPager implements IPageable
{
	private Page page = null;
	private Pager pager = null;
	private final String OVERTIME_STORAGE_INFO_HEAD = "OVERTIME_STORAGE_INFO_HEAD";

	public OvertimeStorageInfoPager(Page page, Pager pager)
	{
		this.page = page;
		this.pager = pager;
	}

	public List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		List overtimeStorageInfoList = new ArrayList();

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			overtimeStorageInfoList = centre.getOvertimeStorageInfoList(
					getOvertimeStorageInfoHead(), beginningPos, length);
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
		return overtimeStorageInfoList;
	}

	public OvertimeStorageInfoHead getOvertimeStorageInfoHead()
	{
		return (OvertimeStorageInfoHead) page.getSession().getAttribute(
				OVERTIME_STORAGE_INFO_HEAD);
	}

	public ListCell getListCell()
	{
		return ((OvertimeStorageInfoPopupBusiness) page).lst_OvertimeStorageInfoPopup;
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
					.getOvertimeStorageInfoCount(getOvertimeStorageInfoHead());
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
		OvertimeStorageInfoListProxy listProxy = new OvertimeStorageInfoListProxy(
				listCell);

		OvertimeStorageInfoEntity entity = (OvertimeStorageInfoEntity) object;

		listProxy.setRowValueByEntity(entity, rowNum);

	}

}
