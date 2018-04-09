package jp.co.daifuku.wms.YkkGMAX.PageController;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalOrderPrintEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalOrderPrintHead;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Inquiry.RetrievalOrderPrintPopupBusiness;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.RetrievalOrderPrintListProxy;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-5-18
 * Time: 下午1:29
 * To change this template use File | Settings | File Templates.
 */
public class RetrievalOrderPrintPager implements IPageable
{
    private Page page = null;
    private Pager pager = null;
    private final String RETRIEVAL_ORDER_PRINT_HEAD = "RETRIEVAL_ORDER_PRINT_HEAD";

    public RetrievalOrderPrintPager(Page page, Pager pager)
    {
        this.page = page;
        this.pager = pager;
    }

    public Pager getPager()
    {
        return pager;
    }

    public List getList(int beginningPos, int length) throws YKKDBException, YKKSQLException
    {
        Connection conn = null;
        List ioHistoryInfoList = new ArrayList();

        try
        {
            conn = ConnectionManager.getConnection();

            ASRSInfoCentre centre = new ASRSInfoCentre(conn);

            ioHistoryInfoList = centre.getRetrievalOrderPrintList(
                    getRetrievalOrderPrintHead(), beginningPos, length);

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

    public ListCell getListCell()
    {
        return ((RetrievalOrderPrintPopupBusiness) page).lst_RetrievalOrderPrintPopup1;
    }

    public void setRowValue(ListCell listCell, int rowNum, Object object)
    {
        RetrievalOrderPrintListProxy listProxy = new RetrievalOrderPrintListProxy(listCell);

        RetrievalOrderPrintEntity entity = (RetrievalOrderPrintEntity) object;

        listProxy.setHeadRowValueByEntity(entity);
    }

    public int getTotalCount() throws YKKDBException, YKKSQLException
    {
        Connection conn = null;
        int totalCount = 0;

        try
        {
            conn = ConnectionManager.getConnection();
            ASRSInfoCentre centre = new ASRSInfoCentre(conn);
            totalCount = centre.getRetrievalOrderPrintCount(getRetrievalOrderPrintHead());
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

    public RetrievalOrderPrintHead getRetrievalOrderPrintHead()
    {
        return (RetrievalOrderPrintHead) page.getSession().getAttribute(
                RETRIEVAL_ORDER_PRINT_HEAD);
    }
}
